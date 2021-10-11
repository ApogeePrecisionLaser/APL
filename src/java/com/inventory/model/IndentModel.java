package com.inventory.model;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import org.json.simple.JSONObject;
import com.DBConnection.DBConnection;
import com.inventory.tableClasses.Indent;
import com.inventory.tableClasses.Inventory;
import com.inventory.tableClasses.InventoryBasic;
import com.inventory.tableClasses.ItemName;
import static com.organization.model.KeypersonModel.getRevisionnoForImage;
import com.organization.tableClasses.KeyPerson;
import java.io.File;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import static java.time.LocalDateTime.now;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import org.apache.commons.fileupload.FileItem;
import org.json.simple.JSONArray;

/**
 *
 * @author Komal
 */
public class IndentModel {

    private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "#a2a220";
    private final String COLOR_ERROR = "red";
    int item_id = 0;
    int indent_table_id = 0;

    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("InventoryModel setConnection() Error: " + e);
        }
    }

    public List<Indent> showData(String logged_key_person, String office_admin) {
        List<Indent> list = new ArrayList<Indent>();

        String query = " select indt.indent_no,indt.date_time,indt.description,itn.item_name,p.purpose,indi.required_qty,indi.expected_date_time "
                + " ,s.status,kp2.key_person_name as requested_to,indt.indent_table_id,indi.indent_item_id,indi.approved_qty from indent_table indt,indent_item indi,key_person kp1,key_person kp2,"
                + " item_names itn,purpose p,status s where indt.indent_table_id=indi.indent_table_id "
                + " and indt.requested_by=kp1.key_person_id and indt.requested_to=kp2.key_person_id and indi.item_names_id=itn.item_names_id"
                + " and indi.purpose_id=p.purpose_id "
                + " and indi.status_id=s.status_id and indt.active='Y' and indi.active='Y' and itn.active='Y' "
                + " and kp1.active='Y' and kp2.active='Y' ";

        if (!logged_key_person.equals("") && logged_key_person != null) {
            query += " and kp1.key_person_name='" + logged_key_person + "' ";
        }
        if (!office_admin.equals("") && office_admin != null) {
            query += " and kp2.key_person_name='" + office_admin + "' ";
        }
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                Indent bean = new Indent();
                bean.setIndent_no(rset.getString("indent_no"));
                bean.setDate_time(rset.getString("date_time"));
                bean.setItem_name((rset.getString("item_name")));
                bean.setPurpose((rset.getString("purpose")));
                bean.setRequired_qty(rset.getInt("required_qty"));
                bean.setApproved_qty(rset.getInt("approved_qty"));
                bean.setExpected_date_time(rset.getString("expected_date_time"));
                String status = rset.getString("status");
                if (status.equals("Request Sent")) {
                    status = "Confirmation Awaited";
                }
                bean.setStatus(status);
                bean.setRequested_to(rset.getString("requested_to"));
                bean.setDescription(rset.getString("description"));
                bean.setIndent_table_id(rset.getInt("indent_table_id"));
                bean.setIndent_item_id(rset.getInt("indent_item_id"));

                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: InventoryModel showdata-" + e);
        }
        return list;
    }

    public int insertRecord(Indent bean, String logged_user_name, String office_admin, int i) throws SQLException {
        String query = "INSERT INTO indent_table(indent_no,requested_by,requested_to,"
                + " status_id,active,remark,date_time,description,revision_no) "
                + " VALUES(?,?,?,?,?,?,?,?,?) ";
        int rowsAffected2 = 0;
        int rowsAffected = 0;
        int requested_by_id = getRequestedKeyPersonId(logged_user_name);
        int requested_to_id = getRequestedKeyPersonId(office_admin);
        int count = 0;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date_time = sdf.format(date);

        try {
            if (i == 0) {
                String query4 = "SELECT count(*) as count FROM indent_table WHERE "
                        + " indent_no='" + bean.getIndent_no() + "' "
                        + " and active='Y'  ";

                PreparedStatement pstmt1 = connection.prepareStatement(query4);
                ResultSet rs1 = pstmt1.executeQuery();
                while (rs1.next()) {
                    count = rs1.getInt("count");
                }
                if (count > 0) {
                    message = "Indent No. Already Exists!..";
                    msgBgColor = COLOR_ERROR;
                } else {
                    PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    pstmt.setString(1, bean.getIndent_no());
                    pstmt.setInt(2, requested_by_id);
                    pstmt.setInt(3, requested_to_id);
                    pstmt.setInt(4, 5);
                    pstmt.setString(5, "Y");
                    pstmt.setString(6, "OK");
                    pstmt.setString(7, date_time);
                    pstmt.setString(8, bean.getDescription());
                    pstmt.setInt(9, bean.getRevision_no());
                    rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        //rowsAffected = pstmt.executeUpdate();
                        ResultSet rs = pstmt.getGeneratedKeys();
                        while (rs.next()) {
                            indent_table_id = rs.getInt(1);
                        }
                    }
                }
            }
            
            String query2 = "INSERT INTO indent_item(indent_table_id,item_names_id,purpose_id, required_qty,"
                    + " status_id,active,remark,expected_date_time,description,revision_no) "
                    + " VALUES(?,?,?,?,?,?,?,?,?,?) ";

            int item_name_id2 = getItemNameId(bean.getItem_name());
            int purpose_id2 = getPurposeId(bean.getPurpose());
            int count2 = 0;

            PreparedStatement pstmt2 = connection.prepareStatement(query2);
            pstmt2.setInt(1, indent_table_id);
            pstmt2.setInt(2, item_name_id2);
            pstmt2.setInt(3, purpose_id2);
            pstmt2.setInt(4, bean.getRequired_qty());
            pstmt2.setInt(5, 5);
            pstmt2.setString(6, "Y");
            pstmt2.setString(7, "OK");
            pstmt2.setString(8, bean.getExpected_date_time());
            pstmt2.setString(9, bean.getDescription());
            pstmt2.setInt(10, bean.getRevision_no());
            rowsAffected2 = pstmt2.executeUpdate();

        } catch (Exception e) {
            System.out.println("IndentModel insertRecord() Error: " + e);
        }
        if (rowsAffected2 > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        if (count > 0) {
            message = "Indent No. Already Exists!..";
            msgBgColor = COLOR_ERROR;
        }

        return rowsAffected2;
    }

    public int getRequestedKeyPersonId(String person_name) {
        String query = "SELECT key_person_id FROM key_person WHERE key_person_name = '" + person_name + "' and active='Y' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("key_person_id");
        } catch (Exception e) {
            System.out.println("getRequestedByKeyPersonId Error: " + e);
        }
        return id;
    }

    public int getItemNameId(String item_name) {

        String query = "SELECT item_names_id FROM item_names WHERE item_name = '" + item_name + "' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("item_names_id");
        } catch (Exception e) {
            System.out.println("getItemNameId Error: " + e);
        }
        return id;
    }

    public int getPurposeId(String purpose) {

        String query = "SELECT purpose_id FROM purpose WHERE purpose = '" + purpose + "' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("purpose_id");
        } catch (Exception e) {
            System.out.println("getPurposeId Error: " + e);
        }
        return id;
    }

    public List<String> getStatus(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT status from status  ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String status = (rset.getString("status"));
                if (status.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(status);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such status  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:IndentModel--getStatus()-- " + e);
        }
        return list;
    }

    public List<String> getPurpose(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT purpose from purpose  ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String purpose = (rset.getString("purpose"));
                if (purpose.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(purpose);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such purpose  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:IndentModel--getPurpose()-- " + e);
        }
        return list;
    }

    public List<String> getRequestedByKeyPerson(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT key_person_name from key_person where active='Y' ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String key_person_name = (rset.getString("key_person_name"));
                if (key_person_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(key_person_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such key_person_name  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:IndentModel--getRequestedByKeyPerson()-- " + e);
        }
        return list;
    }

    public List<String> getRequestedToKeyPerson(String q, String requested_by) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT key_person_name from key_person where active='Y' and key_person_name!='" + requested_by + "' ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String key_person_name = (rset.getString("key_person_name"));
                if (key_person_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(key_person_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such key_person_name  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:IndentModel--getRequestedByKeyPerson()-- " + e);
        }
        return list;
    }

    public List<ItemName> getItemsList(String logged_designation, String checkedValue, int checked_req_qty, String checked_purpose, String checked_item_name, String checked_expected_date_time) {
        List<ItemName> list = new ArrayList<ItemName>();

        JSONObject obj = new JSONObject();
        JSONArray arrayObj = new JSONArray();
        String data = "";

//        List list = new ArrayList();
        String item_name = "";
        List<Integer> idList = new ArrayList<Integer>();
        List<String> parentItemNameList = new ArrayList<String>();
        try {
            parentItemNameList = getParentItemNameList();
            for (int i = 0; i < parentItemNameList.size(); i++) {
                String searchItemName = parentItemNameList.get(i);
                idList = getAllParentChildList(searchItemName);
                String query = "select itn.item_names_id,itn.item_name,itn.description,itn.item_code,itt.item_type,itn.quantity,itn.parent_id,"
                        + "itn.generation,itn.is_super_child,itn.prefix "
                        + " from item_names itn, item_type itt where itt.item_type_id=itn.item_type_id "
                        + " and itn.active='Y' and itt.active='y' ";

                query += "and itn.item_names_id in(" + idList.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ") "
                        + " order by field(itn.item_names_id," + idList.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ") ";

                PreparedStatement pstmt = connection.prepareStatement(query);
                ResultSet rset = pstmt.executeQuery();
                while (rset.next()) {
                    ItemName bean = new ItemName();
                    int checked_id = 0;
                    int item_name_id = (rset.getInt("item_names_id"));
                    if (checkedValue.equals("")) {
                        checked_id = Integer.parseInt("0");
                    } else {
                        checked_id = Integer.parseInt(checkedValue);
                    }
                    String checked_qty = "";
                    if (checked_req_qty == 0) {
                        checked_qty = "";
                    } else {
                        checked_qty = String.valueOf(checked_req_qty);
                    }

                    if (item_name_id == checked_id) {
                        bean.setChecked_item_name(checked_item_name);
                        bean.setCheckedValue(checkedValue);
                        bean.setChecked_purpose(checked_purpose);
                        bean.setChecked_req_qty(checked_qty);
                        bean.setChecked_expected_date_time(checked_expected_date_time);
                    } else {
                        bean.setChecked_item_name(checked_item_name);
                        bean.setCheckedValue(checkedValue);
                        bean.setChecked_purpose(checked_purpose);
                        bean.setChecked_req_qty(checked_qty);
                        bean.setChecked_expected_date_time(checked_expected_date_time);
                    }

                    bean.setItem_names_id(rset.getInt("item_names_id"));
                    bean.setItem_name((rset.getString("item_name")));
                    String parent_id = rset.getString("parent_id");
                    int generation = rset.getInt("generation");
                    if (parent_id == null) {
                        parent_id = "";
                    }
                    bean.setParent_item_id(parent_id);
                    bean.setGeneration(generation);
                    bean.setSuperp(rset.getString("is_super_child"));
                    list.add(bean);
                }
            }

        } catch (Exception e) {
            System.err.println("Exception in getItemsList---------" + e);
        }

        return list;

    }

    public int getCounting() {
        int counting = 100;
        int count = 0;
        String query = " SELECT indent_no FROM indent_table order by indent_table_id desc limit 1 ";
        try {
            PreparedStatement psmt = connection.prepareStatement(query);
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                String indent_no = rs.getString("indent_no");
                String indent_no_arr[] = indent_no.split("_");
                int length = (indent_no_arr.length) - 1;
                count = Integer.parseInt(indent_no_arr[length]);

                counting = count;
            }
        } catch (Exception ex) {
            System.out.println("ERROR: in getCounting in IndentModel : " + ex);
        }
        return counting + 1;
    }

    public List<String> getParentItemNameList() {
        PreparedStatement pstmt;
        List<String> list = new ArrayList<String>();
        String searchItemName = "";
        try {
            String query = " select item_name from item_names where (parent_id=0 or parent_id is null) and active='Y' ";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                searchItemName = rst.getString("item_name");
                list.add(searchItemName);
            }
        } catch (Exception e) {
            System.out.println("Error:--ItemNameModel--- getParentItemNameList--" + e);
        }

        list.removeAll(Arrays.asList(0));

        return list;
    }

    public List<Integer> getAllParentChildList(String searchItemName) {
        PreparedStatement pstmt;
        String query = "";
        List<Integer> list = new ArrayList<Integer>();

        if (searchItemName == null) {
            searchItemName = "";
        }
        int item_names_id = 0, parent_id = 0;

        String qry = "select item_names_id from item_names where active='Y' and item_name='" + searchItemName + "' ";
        try {
            PreparedStatement pst = connection.prepareStatement(qry);
            ResultSet rstt = pst.executeQuery();
            while (rstt.next()) {
                item_names_id = rstt.getInt(1);
                list.add(item_names_id);
            }
        } catch (Exception e) {
            System.out.println("ItemNameModel.getAllParentChild() -" + e);
        }

        String qry1 = "select item_names_id from item_names where active='Y' and parent_id='" + item_names_id + "' limit 1 ";
        try {
            PreparedStatement pst = connection.prepareStatement(qry1);
            ResultSet rstt = pst.executeQuery();
            while (rstt.next()) {
                parent_id = rstt.getInt(1);
                list.add(parent_id);
            }
        } catch (Exception e) {
            System.out.println("ItemNameModel.getAllParentChild() -" + e);
        }

        try {
            query = " SELECT distinct t2.item_names_id as lev2, t3.item_names_id as lev3, "
                    + " t4.item_names_id as lev4,t5.item_names_id as lev5,t6.item_names_id as lev6, "
                    + " t7.item_names_id as lev7,t8.item_names_id as lev8,t9.item_names_id as lev9,t10.item_names_id as lev10 "
                    + " FROM item_names AS t1 "
                    + " LEFT JOIN item_names AS t2 ON t2.parent_id = t1.item_names_id and t2.active='Y' "
                    + " LEFT JOIN item_names AS t3 ON t3.parent_id = t2.item_names_id and t3.active='Y' "
                    + " LEFT JOIN item_names AS t4 ON t4.parent_id = t3.item_names_id and t4.active='Y' "
                    + " LEFT JOIN item_names AS t5 ON t5.parent_id = t4.item_names_id and t5.active='Y' "
                    + " LEFT JOIN item_names AS t6 ON t6.parent_id = t5.item_names_id and t6.active='Y' "
                    + " LEFT JOIN item_names AS t7 ON t7.parent_id = t6.item_names_id and t7.active='Y' "
                    + " LEFT JOIN item_names AS t8 ON t8.parent_id = t7.item_names_id and t8.active='Y' "
                    + " LEFT JOIN item_names AS t9 ON t9.parent_id = t8.item_names_id and t9.active='Y' "
                    + " LEFT JOIN item_names AS t10 ON t10.parent_id = t9.item_names_id and t10.active='Y' "
                    + "  WHERE '" + item_names_id + "' in (t1.parent_id,t2.parent_id) ";

            pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                list.add(rset.getInt(1));
                list.add(rset.getInt(2));
                list.add(rset.getInt(3));
                list.add(rset.getInt(4));
                list.add(rset.getInt(5));
                list.add(rset.getInt(6));
                list.add(rset.getInt(7));
                list.add(rset.getInt(8));
                list.add(rset.getInt(9));
            }
        } catch (Exception e) {
            System.out.println("Error:--ItemNameModel--- showData--" + e);
        }
        String qry2 = "select item_names_id from item_names where active='Y' and parent_id='" + item_names_id + "' ";
        try {
            PreparedStatement pst = connection.prepareStatement(qry2);
            ResultSet rstt = pst.executeQuery();
            while (rstt.next()) {
                list.add(rstt.getInt(1));
            }
        } catch (Exception e) {
            System.out.println("ItemNameModel.getAllParentChild() -" + e);
        }

        list.removeAll(Arrays.asList(0));

        return list;
    }

    public static int getLastIndentTableId() {
        int indent_table_id = 0;
        try {
            String query = " SELECT indent_table_id FROM indent_table "
                    + " WHERE active='Y' order by indent_table_id desc limit 1 ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                indent_table_id = rset.getInt("indent_table_id");

            }
        } catch (Exception e) {
            System.err.println("getLastIndentTableId error:" + e);
        }
        return indent_table_id;
    }

    public String getMessage() {
        return message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("ItemNameModel closeConnection() Error: " + e);
        }
    }
}
