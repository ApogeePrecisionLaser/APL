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

    public List<Inventory> showData(String searchItemName, String searchOrgOffice, String searchKeyPerson, String search_item_code) {
        List<Inventory> list = new ArrayList<Inventory>();

        if (searchItemName == null) {
            searchItemName = "";
        }
        if (searchOrgOffice == null) {
            searchOrgOffice = "";
        }
        if (searchKeyPerson == null) {
            searchKeyPerson = "";
        }
        if (search_item_code == null) {
            search_item_code = "";
        }

        String query = "select inv.inventory_id,inv.inventory_basic_id,inn.item_name,inn.item_code,oo.org_office_name,kp.key_person_name,"
                + " inv.inward_quantity,inv.outward_quantity, "
                + " inv.date_time,inv.reference_document_type,inv.reference_document_id,inv.description "
                + " from item_names inn,org_office oo,inventory_basic ib,key_person kp,inventory inv where inn.item_names_id=ib.item_names_id and "
                + " oo.org_office_id=ib.org_office_id and kp.key_person_id=inv.key_person_id and ib.inventory_basic_id=inv.inventory_basic_id and"
                + " inn.active='Y' and oo.active='Y' and ib.active='Y' and inv.active='Y' and kp.active='Y' ";

        if (!searchItemName.equals("") && searchItemName != null) {
            query += " and inn.item_name='" + searchItemName + "' ";
        }
        if (!search_item_code.equals("") && search_item_code != null) {
            query += " and inn.item_code='" + search_item_code + "' ";
        }
        if (!searchOrgOffice.equals("") && searchOrgOffice != null) {
            query += " and oo.org_office_name='" + searchOrgOffice + "' ";
        }
        if (!searchKeyPerson.equals("") && searchKeyPerson != null) {
            query += " and kp.key_person_name='" + searchKeyPerson + "' ";
        }
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                Inventory bean = new Inventory();
                bean.setInventory_id(rset.getInt("inventory_id"));
                bean.setInventory_basic_id(rset.getInt("inventory_basic_id"));
                bean.setItem_name((rset.getString("item_name")));
                bean.setItem_code((rset.getString("item_code")));
                bean.setOrg_office(rset.getString("org_office_name"));
                bean.setKey_person(rset.getString("key_person_name"));
                bean.setInward_quantity(rset.getInt("inward_quantity"));
                bean.setOutward_quantity(rset.getInt("outward_quantity"));
                bean.setDate_time(rset.getString("date_time"));
                bean.setReference_document_type(rset.getString("reference_document_type"));
                bean.setReference_document_id(rset.getString("reference_document_id"));
                bean.setDescription(rset.getString("description"));
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

        int rowsAffected = 0;
        int requested_by_id = getRequestedKeyPersonId(logged_user_name);
        int requested_to_id = getRequestedKeyPersonId(office_admin);
        int count = 0;

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
                    pstmt.setString(7, now().toString());
                    pstmt.setString(8, bean.getDescription());
                    pstmt.setInt(9, bean.getRevision_no());
                    rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        rowsAffected = pstmt.executeUpdate();
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

            int rowsAffected2 = 0;
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
        if (rowsAffected > 0) {
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

        return rowsAffected;
    }

//    public int updateRecord(Indent bean, int inventory_id) {
//        int revision = InventoryModel.getRevisionno(bean, inventory_id);
//        int updateRowsAffected = 0;
//        int item_name_id = getItemNamesId(bean.getItem_code());
//        int org_office_id = getOrgOfficeId(bean.getOrg_office());
//        int inventory_basic_id = getInventoryBasicId(org_office_id, item_name_id);
//        int key_person_id = getKeyPersonId(bean.getKey_person());
//
//        String query1 = "SELECT max(revision_no) revision_no FROM inventory WHERE inventory_id = " + inventory_id + "  and active='Y' ";
//        String query2 = "UPDATE inventory SET active=? WHERE inventory_id=? and revision_no=? ";
//        String query3 = "INSERT INTO inventory(inventory_id,inventory_basic_id,key_person_id,description,"
//                + " revision_no,active,remark,inward_quantity,outward_quantity,date_time,reference_document_type,reference_document_id) "
//                + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
//
//        int rowsAffected = 0;
//        int map_count = 0;
//        try {
//            String query4 = "SELECT count(*) as count FROM inventory WHERE "
//                    + " inventory_basic_id='" + inventory_basic_id + "' and key_person_id='" + key_person_id + "'"
//                    + " and active='Y'  ";
//
//            PreparedStatement pstmt1 = connection.prepareStatement(query4);
//            ResultSet rs1 = pstmt1.executeQuery();
//            while (rs1.next()) {
//                map_count = rs1.getInt("count");
//            }
//            if (map_count > 0) {
//                message = "Item has already assigned to this person!..";
//                msgBgColor = COLOR_ERROR;
//            } else {
//                PreparedStatement pstmt = connection.prepareStatement(query1);
//                ResultSet rs = pstmt.executeQuery();
//                if (rs.next()) {
//                    PreparedStatement pstm = connection.prepareStatement(query2);
//                    pstm.setString(1, "n");
//                    pstm.setInt(2, inventory_id);
//                    pstm.setInt(3, revision);
//                    updateRowsAffected = pstm.executeUpdate();
//                    if (updateRowsAffected >= 1) {
//                        revision = rs.getInt("revision_no") + 1;
//                        PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
//                        psmt.setInt(1, inventory_id);
//                        psmt.setInt(2, inventory_basic_id);
//                        psmt.setInt(3, key_person_id);
//                        psmt.setString(4, (bean.getDescription()));
//                        psmt.setInt(5, revision);
//                        psmt.setString(6, "Y");
//                        psmt.setString(7, "OK");
//                        psmt.setInt(8, bean.getInward_quantity());
//                        psmt.setInt(9, bean.getOutward_quantity());
//                        psmt.setString(10, bean.getDate_time());
//                        psmt.setString(11, bean.getReference_document_type());
//                        psmt.setString(12, bean.getReference_document_id());
//                        rowsAffected = psmt.executeUpdate();
//
//                    }
//
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("InventoryModel updateRecord() Error: " + e);
//        }
//        if (rowsAffected > 0) {
//            message = "Record updated successfully.";
//            msgBgColor = COLOR_OK;
//        } else {
//            message = "Cannot update the record, some error.";
//            msgBgColor = COLOR_ERROR;
//        }
////        if (map_count > 0) {
////            message = "Item has already assigned to this person!..";
////            msgBgColor = COLOR_ERROR;
////        }
//        return rowsAffected;
//    }
    public static int getRevisionno(Inventory bean, int inventory_id) {
        int revision = 0;
        try {
            String query = " SELECT max(revision_no) as revision_no FROM inventory "
                    + " WHERE inventory_id =" + inventory_id + "  and active='Y' ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
            System.err.println("getRevisionno error:" + e);
        }
        return revision;
    }

    public int deleteRecord(int inventory_id) {
        String query = "DELETE FROM inventory WHERE inventory_id = " + inventory_id;
        int rowsAffected = 0;
        try {

            rowsAffected = connection.prepareStatement(query).executeUpdate();

        } catch (Exception e) {
            System.out.println("InventoryModel deleteRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record deleted successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot delete the record, some error.";
            msgBgColor = COLOR_ERROR;
        }

        return rowsAffected;
    }

    public int insertItemRecord(Indent bean) throws SQLException {
        String query = "INSERT INTO indent_item(indent_table_id,item_names_id,purpose_id, required_qty,"
                + " status_id,active,remark,expected_date_time,description,revision_no) "
                + " VALUES(?,?,?,?,?,?,?,?,?,?) ";

        int rowsAffected = 0;
        int item_name_id = getItemNameId(bean.getItem_name());
        int purpose_id = getPurposeId(bean.getPurpose());
        int count = 0;
        try {

            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, bean.getIndent_table_id());
            pstmt.setInt(2, item_name_id);
            pstmt.setInt(3, purpose_id);
            pstmt.setInt(4, bean.getRequired_qty());
            pstmt.setInt(5, 5);
            pstmt.setString(6, "Y");
            pstmt.setString(7, "OK");
            pstmt.setString(8, bean.getExpected_date_time());
            pstmt.setString(9, bean.getDescription());
            pstmt.setInt(10, bean.getRevision_no());
            rowsAffected = pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("IndentModel insertRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }

        return rowsAffected;
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

    public int getOrgOfficeId(String org_office) {

        String query = "SELECT org_office_id FROM org_office WHERE org_office_name = '" + org_office + "' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("org_office_id");
        } catch (Exception e) {
            System.out.println("getOrgOfficeId Error: " + e);
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

    public int getKeyPersonId(String key_person_name) {

        String query = "SELECT key_person_id FROM key_person WHERE key_person_name = '" + key_person_name + "' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("key_person_id");
        } catch (Exception e) {
            System.out.println("getKeyPersonId Error: " + e);
        }
        return id;
    }

    public int getInventoryBasicId(int org_office_id, int item_names_id) {

        String query = "SELECT inventory_basic_id FROM inventory_basic WHERE org_office_id = '" + org_office_id + "' "
                + " and item_names_id='" + item_names_id + "' and active='Y' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("inventory_basic_id");
        } catch (Exception e) {
            System.out.println("getInventoryBasicId Error: " + e);
        }
        return id;
    }

    public String getItemName(int item_name_id) {
        String query = "SELECT item_name FROM item_names WHERE item_names_id = ? and active='Y'";
        String name = "";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, item_name_id);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            name = rset.getString("item_name");
        } catch (Exception e) {
            System.out.println("getItemName Error: " + e);
        }
        return name;
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

    public JSONArray getItemsList(String logged_designation) {
        JSONObject obj = new JSONObject();
        JSONArray arrayObj = new JSONArray();
        String data = "";

        List list = new ArrayList();
        String item_name = "";
        try {
//            String query = " SELECT  item_name from item_names itn where itn.active='Y' "
//                    + " and itn.is_super_child='Y' order by itn.item_name ";
            String query = " SELECT  itn.item_name from item_names itn,designation d,item_authorization ia where itn.active='Y' "
                    + " and d.active='Y' and ia.active='Y' and itn.item_names_id=ia.item_names_id and "
                    + " d.designation_id=ia.designation_id and d.designation='Java Developer' "
                    + " order by itn.item_name ";

            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                JSONObject jsonObj = new JSONObject();
                item_name = rset.getString("item_name");

                jsonObj.put("item_name", item_name);
                arrayObj.add(jsonObj);
            }

        } catch (Exception e) {
            System.err.println("Exception in getItemsList---------" + e);
        }

        return arrayObj;

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
