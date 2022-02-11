package com.inventory.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;
import com.inventory.tableClasses.Indent;
import com.inventory.tableClasses.ItemName;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.json.simple.JSONArray;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;

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
    int item_id1 = 0;
    int indent_table_id = 0;
    List<Integer> allIdList = new ArrayList<Integer>();

    public void setConnection(Connection con) {
        try {
            connection = con;
        } catch (Exception e) {
            System.out.println("IndentModel setConnection() Error: " + e);
        }
    }

    public List<Indent> showData(String logged_key_person, String office_admin, String indent_status, String search_by_date) {
        List<Indent> list = new ArrayList<Indent>();
        if (indent_status.equals("All")) {
            indent_status = "";
        }
        String query = " select indt.indent_no,indt.date_time,indt.description "
                + " ,s.status,kp2.key_person_name as requested_to,indt.indent_table_id  from "
                + " indent_table indt,key_person kp1,key_person kp2, "
                + " status s where indt.requested_to=kp2.key_person_id "
                + " and indt.requested_by=kp1.key_person_id  "
                + " and indt.status_id=s.status_id and indt.active='Y' and kp1.active='Y' and kp2.active='Y'  ";

        if (!logged_key_person.equals("") && logged_key_person != null && !logged_key_person.equals("jpss")) {
            query += " and kp1.key_person_name='" + logged_key_person + "' ";
        }
        if (!office_admin.equals("") && office_admin != null) {
            query += " and kp2.key_person_name='" + office_admin + "' ";
        }

        if (!indent_status.equals("") && indent_status != null) {
            query += " and s.status='" + indent_status + "' ";
        }

        if (!search_by_date.equals("") && search_by_date != null) {
            query += " and indt.date_time like '" + search_by_date + "%' ";
        }
        query += " order by indt.indent_no desc ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                Indent bean = new Indent();
                bean.setIndent_no(rset.getString("indent_no"));
                bean.setDate_time(rset.getString("date_time"));
                String status = rset.getString("status");
                bean.setStatus(status);
                bean.setRequested_to(rset.getString("requested_to"));
                bean.setDescription(rset.getString("description"));
                bean.setIndent_table_id(rset.getInt("indent_table_id"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: IndentModel showdata-" + e);
        }
        return list;
    }

    public List<Indent> getIndentData(String indent_table_id) {
        List<Indent> list = new ArrayList<Indent>();

        String query = " select indt.indent_no,indt.date_time,indt.description "
                + " ,s1.status as indent_status,s2.status as item_status,kp1.key_person_name as requested_by,"
                + " kp2.key_person_name as requested_to, "
                + " indt.indent_table_id,p.purpose,itn.item_name,m.model,indi.required_qty,indi.approved_qty,indi.deliver_qty"
                + " ,indi.expected_date_time,oo.address_line1,oo.address_line2,oo.address_line3,c.city_name,dc.challan_date  from "
                + " indent_table indt,key_person kp1,key_person kp2,item_names itn,model m,manufacturer mr,manufacturer_item_map mim, "
                + " status s1,status s2,indent_item indi,purpose p,org_office oo,city c,delivery_challan dc "
                + " where indt.requested_to=kp2.key_person_id and mr.active='Y' and mim.active='Y' and dc.active='Y' "
                + " and dc.indent_table_id=indt.indent_table_id and dc.indent_item_id=indi.indent_item_id "
                + " and indt.requested_by=kp1.key_person_id and indi.status_id=s2.status_id and itn.active='Y' and m.active='Y' "
                + " and indi.item_names_id=itn.item_names_id and m.model_id=indi.model_id and mr.manufacturer_id=mim.manufacturer_id "
                + " and mim.item_names_id=itn.item_names_id and mim.manufacturer_item_map_id=m.manufacturer_item_map_id "
                + " and indt.status_id=s1.status_id and indt.active='Y' and kp1.active='Y' and kp2.active='Y'and indi.active='Y'"
                + " and indt.indent_table_id=indi.indent_table_id and p.purpose_id=indi.purpose_id and oo.active='Y' and c.active='Y' "
                + " and kp1.org_office_id=oo.org_office_id and oo.city_id=c.city_id ";

        if (!indent_table_id.equals("") && indent_table_id != null) {
            query += " and indt.indent_table_id='" + indent_table_id + "' ";
        }

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                Indent bean = new Indent();
                bean.setItem_name(rset.getString("item_name"));
                bean.setModel(rset.getString("model"));
                String item_status = rset.getString("item_status");
                bean.setItem_status(item_status);
                if (item_status.equals("Delivered")) {
                    bean.setDelivery_challan_date(rset.getString("challan_date"));
                } else {
                    bean.setDelivery_challan_date("");

                }
                bean.setRequired_qty(rset.getInt("required_qty"));
                bean.setApproved_qty(rset.getInt("approved_qty"));
                bean.setDelivered_qty(rset.getInt("deliver_qty"));
                bean.setExpected_date_time(rset.getString("expected_date_time"));
                bean.setPurpose(rset.getString("purpose"));
                bean.setIndent_no(rset.getString("indent_no"));
                bean.setDate_time(rset.getString("date_time"));
                bean.setRequested_by(rset.getString("requested_by"));
                bean.setRequested_to(rset.getString("requested_to"));

                bean.setIndent_status(rset.getString("indent_status"));
                String address_line1 = rset.getString("address_line1");
                String address_line2 = rset.getString("address_line2");
                String address_line3 = rset.getString("address_line3");
                String city_name = rset.getString("city_name");

                String office_address = address_line1 + ", " + address_line2 + ", " + address_line3 + ", " + city_name;
                bean.setOffice_address(office_address);

                list.add(bean);
            }

        } catch (Exception e) {
            System.out.println("Error: IndentModel getIndentData-" + e);
        }
        return list;
    }

    public List<Indent> getStatus() {
        List<Indent> list = new ArrayList<Indent>();

        String query = " select status,status_id from status  order by status";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                Indent bean = new Indent();
                String status = rset.getString("status");
//                if (status.equals("Request Sent")) {
//                    status = "Pending";
//                }
                bean.setStatus_id(rset.getInt("status_id"));
                bean.setStatus(status);

                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: IndentModel getStatus-" + e);
        }
        return list;
    }

    public int insertRecord(Indent bean, String logged_user_name, String office_admin, int i, int logged_org_office_id) throws SQLException {
        String query = "INSERT INTO indent_table(indent_no,requested_by,requested_to,"
                + " status_id,active,remark,date_time,description,revision_no) "
                + " VALUES(?,?,?,?,?,?,?,?,?) ";
        int rowsAffected2 = 0;
        int rowsAffected = 0;
        int requested_by_id = getRequestedKeyPersonId(logged_user_name, logged_org_office_id);
        int requested_to_id = getRequestedKeyPersonId(office_admin, logged_org_office_id);
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
                    pstmt.setInt(4, 2);
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
                    + " status_id,active,remark,expected_date_time,description,revision_no,model_id) "
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?) ";

            int model_id = getModelId(bean.getModel());
            int item_names_id2 = getItemNameId(model_id);

            int purpose_id2 = getPurposeId(bean.getPurpose());
            int count2 = 0;

            PreparedStatement pstmt2 = connection.prepareStatement(query2);
            pstmt2.setInt(1, indent_table_id);
            pstmt2.setInt(2, item_names_id2);
            pstmt2.setInt(3, purpose_id2);
            pstmt2.setInt(4, bean.getRequired_qty());
            pstmt2.setInt(5, 2);
            pstmt2.setString(6, "Y");
            pstmt2.setString(7, "OK");
            pstmt2.setString(8, bean.getExpected_date_time());
            pstmt2.setString(9, bean.getDescription());
            pstmt2.setInt(10, bean.getRevision_no());
            pstmt2.setInt(11, model_id);
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

    public int getRequestedKeyPersonId(String person_name, int logged_org_office_id) {
        String query = "SELECT key_person_id FROM key_person WHERE key_person_name = '" + person_name + "' and active='Y' and org_office_id='" + logged_org_office_id + "' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("key_person_id");
        } catch (Exception e) {
            System.out.println("IndentModel getRequestedKeyPersonId Error: " + e);
        }
        return id;
    }

    public int getModelId(String model) {

        String query = "SELECT model_id FROM model WHERE model = '" + model + "' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("model_id");
        } catch (Exception e) {
            System.out.println("IndentModel getModelId Error: " + e);
        }
        return id;
    }

    public int getItemNameId(int model_id) {

        String query = " SELECT itn.item_names_id FROM model m,manufacturer_item_map mim,item_names itn"
                + "  WHERE m.model_id = '" + model_id + "'  and m.manufacturer_item_map_id=mim.manufacturer_item_map_id  "
                + " and itn.item_names_id=mim.item_names_id and m.active='Y' and mim.active='Y' and itn.active='Y' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("item_names_id");
        } catch (Exception e) {
            System.out.println("IndentModel getItemNameId Error: " + e);
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
            System.out.println("IndentModel getPurposeId Error: " + e);
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
            System.out.println("Error:IndentModel--getRequestedToKeyPerson()-- " + e);
        }
        return list;
    }

    public List<Integer> getIdList(String logged_designation, String org_office) {
        List<Integer> list = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        try {
            String query = " select itn.item_names_id "
                    + " from item_names itn, item_type itt,item_authorization ia,designation d,org_office oo,org_office_designation_map oodm "
                    + " where ia.item_names_id=itn.item_names_id "
                    + " and d.designation='" + logged_designation + "' and oo.org_office_name='" + org_office + "' "
                    + " and itt.item_type_id=itn.item_type_id  and itn.active='Y' and oodm.designation_id=d.designation_id "
                    + " and oodm.org_office_id=oo.org_office_id  and ia.designation_id=d.designation_id "
                    + " and oodm.org_office_designation_map_id=ia.org_office_designation_map_id "
                    + " and itt.active='y' and ia.active='Y' and d.active='Y' and oo.active='y' and oodm.active='y' ";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                list2.add(rst.getInt(1));
            }

            // Start Sorted Array for Parent Child Hierarchy
            List<Integer> list3_value = new ArrayList<>();
            List<Integer> list3_key = new ArrayList<>();
            List<Integer> list4 = new ArrayList<>();
            List<Integer> sorted_list = new ArrayList<>();
            List<Integer> sorted_list_noDuplicacy = new ArrayList<>();

            MultiMap map = new MultiValueMap();

            for (int k = 0; k < list2.size(); k++) {
                String qry_order = " SELECT T2.item_names_id,T2.item_name FROM (SELECT @r AS _id, "
                        + " (SELECT @r := parent_id FROM item_names WHERE item_names_id = _id and active='Y') AS parent_id, "
                        + " @l := @l + 1 AS lvl "
                        + " FROM "
                        + " (SELECT @r := '" + list2.get(k) + "', @l := 0) vars, "
                        + " item_names h "
                        + " WHERE @r <> 0) T1 "
                        + " JOIN item_names T2 "
                        + " ON T1._id = T2.item_names_id where T2.active='y'  "
                        + " ORDER BY T1.lvl DESC limit 1 ";

                ResultSet rst3 = connection.prepareStatement(qry_order).executeQuery();
                while (rst3.next()) {
                    map.put(rst3.getInt("item_names_id"), list2.get(k));
                    list3_key.add(list2.get(k));
                    list3_value.add(rst3.getInt("item_names_id"));
                }
            }

            Collections.sort(list3_value);

            for (int k = 0; k < list3_value.size(); k++) {
                if (sorted_list.contains(list3_value.get(k))) {
                } else {
                    sorted_list.add(list3_value.get(k));
                }
            }

            List<Integer> intArr = new ArrayList<Integer>();

            for (int k = 0; k < sorted_list.size(); k++) {

                map.values();
                List<ArrayList> ee = new ArrayList<>();
                ee.add((ArrayList) map.get(sorted_list.get(k)));
                // System.err.println("eee " + ee.size());

                for (int v = 0; v < ee.get(0).size(); v++) {
                    list4.add((Integer) ee.get(0).get(v));
                }
            }

            // END Sorted Array for Parent Child Hierarchy
            for (int k = 0; k < list4.size(); k++) {
                String qry = " SELECT T2.item_names_id,T2.item_name FROM (SELECT @r AS _id, "
                        + " (SELECT @r := parent_id FROM item_names WHERE item_names_id = _id and active='Y') AS parent_id, "
                        + " @l := @l + 1 AS lvl "
                        + " FROM "
                        + " (SELECT @r := '" + list4.get(k) + "', @l := 0) vars, "
                        + " item_names h "
                        + " WHERE @r <> 0) T1 "
                        + " JOIN item_names T2 "
                        + " ON T1._id = T2.item_names_id where T2.active='y'  "
                        + " ORDER BY T1.lvl DESC";

                ResultSet rst2 = connection.prepareStatement(qry).executeQuery();
                while (rst2.next()) {
                    list.add(rst2.getInt(1));
                }

            }

        } catch (Exception e) {
            System.out.println("IndentModel getIdList() -" + e);
        }
        return list;
    }

    public List<ItemName> getItemsList(String logged_designation, String checkedValue, int checked_req_qty, String checked_purpose,
            String checked_item_name, String checked_expected_date_time, String checked_model, String org_office) {
        List<ItemName> list = new ArrayList<ItemName>();
        List<ItemName> list1 = new ArrayList<ItemName>();
        List<Integer> desig_map_list = new ArrayList<Integer>();
        List<Integer> desig_map_listAll = new ArrayList<>();
        List<Integer> desig_map_listAllFinal = new ArrayList<>();
        List<Integer> desig_map_listUnmatched = new ArrayList<>();
        String search_item_name = "";
        String search_item_type = "";
        String search_item_codee = "";
        String search_super_child = "";
        String search_generation = "";
        try {
            desig_map_list = getIdList(logged_designation, org_office);
            ItemNameModel model = new ItemNameModel();
            List<ItemName> allIdList = model.showData(search_item_name, search_item_type, search_item_codee, search_super_child, search_generation);
            for (int k = 0; k < allIdList.size(); k++) {
                desig_map_listAll.add(allIdList.get(k).getItem_names_id());
            }

            desig_map_listAllFinal.addAll(desig_map_listAll);
            desig_map_listAll.removeAll(desig_map_list);
            desig_map_listUnmatched.addAll(desig_map_listAll);

            desig_map_listAllFinal.removeAll(desig_map_listUnmatched);

            String query = "  select itn.item_names_id,itn.item_name,itn.description,itn.item_code,itt.item_type,itn.quantity,itn.parent_id, "
                    + " itn.generation,itn.is_super_child,itn.prefix "
                    + " from item_names itn, item_type itt where itt.item_type_id=itn.item_type_id and itn.active='Y' and itt.active='y' ";
            query += "  and itn.item_names_id in(" + desig_map_listAllFinal.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ") "
                    + " order by field(itn.item_names_id," + desig_map_listAllFinal.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ")  ";

            String query2 = " select max(item_names_id) as item_names_id from item_names ";
            PreparedStatement pstmt1 = connection.prepareStatement(query2);
            ResultSet rset1 = pstmt1.executeQuery();
            while (rset1.next()) {
                item_id1 = rset1.getInt("item_names_id");
            }
            //    System.err.println("query------" + query);
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {

                ItemName bean1 = new ItemName();
                int checked_id = 0;
                int item_name_id = (rset.getInt("item_names_id"));
                int model_id = getModelId(checked_model);
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

                if (model_id == checked_id) {
                    bean1.setChecked_item_name(checked_item_name);
                    bean1.setChecked_model(checked_model);
                    bean1.setCheckedValue(checkedValue);
                    bean1.setChecked_purpose(checked_purpose);
                    bean1.setChecked_req_qty(checked_qty);
                    bean1.setChecked_expected_date_time(checked_expected_date_time);
                } else {
                    bean1.setChecked_item_name(checked_item_name);
                    bean1.setChecked_model(checked_model);
                    bean1.setCheckedValue(checkedValue);
                    bean1.setChecked_purpose(checked_purpose);
                    bean1.setChecked_req_qty(checked_qty);
                    bean1.setChecked_expected_date_time(checked_expected_date_time);
                }
                bean1.setItem_names_id(rset.getInt("item_names_id"));
                bean1.setItem_name((rset.getString("item_name")));
                String parent_id = rset.getString("parent_id");
                int generation = rset.getInt("generation");
                if (parent_id == null) {
                    parent_id = "";
                }
                bean1.setParent_item_id(parent_id);
                bean1.setGeneration(generation);
                bean1.setSuperp(rset.getString("is_super_child"));
                // item_id1++;
                if (rset.getString("is_super_child").equals("Y")) {
                    bean1.setSuperp("N");
                }

                list.add(bean1);
                if (rset.getString("is_super_child").equals("Y")) {
                    String query1 = " select m.model_id,m.model from item_names itn,manufacturer_item_map mim,model m "
                            + " where itn.item_names_id=mim.item_names_id and m.manufacturer_item_map_id=mim.manufacturer_item_map_id"
                            + " and mim.active='Y' and m.active='Y' and itn.active='Y' and "
                            + " itn.item_names_id='" + rset.getInt("item_names_id") + "' ";

                    //    System.err.println("query------" + query);   
                    PreparedStatement pstmt2 = connection.prepareStatement(query1);
                    ResultSet rset2 = pstmt2.executeQuery();
                    while (rset2.next()) {
                        ItemName bean = new ItemName();
                        item_id1 += 1;
                        bean.setItem_names_id(item_id1);
                        bean.setItem_name(rset2.getString("model"));
                        bean.setModel_id(rset2.getInt("model_id"));
                        bean.setParent_item_id(String.valueOf(rset.getInt("item_names_id")));
                        bean.setGeneration(rset.getInt("generation") + 1);
                        bean.setSuperp("Y");
                        bean.setItem(rset.getString("item_name"));
                        list.add(bean);

                    }

                }

                //   list.addAll(list1);
            }
        } catch (Exception e) {
            System.err.println("IndentModel Exception in getItemsList---------" + e);
        }

        return list;

    }

    public List<Indent> getIndentItems(int indent_table_id) {
        List<Indent> list = new ArrayList<Indent>();

        String query1 = " select status_id from indent_table where indent_table_id='" + indent_table_id + "' ";

        try {
            ResultSet rset1 = connection.prepareStatement(query1).executeQuery();
            int status_id = 0;
            while (rset1.next()) {
                status_id = rset1.getInt("status_id");
            }
            String query = "";
//            if (status_id == 7) {
            query = "   select indt.indent_no,itn.item_name,p.purpose,indi.required_qty,indi.expected_date_time,indi.approved_qty,"
                    + "  indi.deliver_qty,itn.quantity as stock_qty  ,s.status,indi.indent_item_id,m.model "
                    + " from indent_table indt,indent_item indi, item_names itn,purpose p, "
                    + " status s,model m,manufacturer_item_map mim where indt.indent_table_id=indi.indent_table_id "
                    + " and indi.item_names_id=itn.item_names_id "
                    + " and m.manufacturer_item_map_id=mim.manufacturer_item_map_id   and mim.item_names_id=itn.item_names_id "
                    + " and indi.purpose_id=p.purpose_id "
                    + " and indi.status_id=s.status_id and indt.active='Y' and indi.active='Y' and itn.active='Y' and m.active='Y' and mim.active='Y' "
                    + " and indt.indent_table_id='" + indent_table_id + "' and indi.model_id=m.model_id ";
//            } else {
//                query = "   select indt.indent_no,itn.item_name,p.purpose,indi.required_qty,indi.expected_date_time,indi.approved_qty,"
//                        + " indi.deliver_qty,itn.quantity as stock_qty  ,s.status,indi.indent_item_id,m.model "
//                        + " from indent_table indt,indent_item indi, item_names itn,purpose p, "
//                        + " status s,model m,manufacturer_item_map mim where indt.indent_table_id=indi.indent_table_id "
//                        + " and indi.item_names_id=itn.item_names_id "
//                        + " and m.manufacturer_item_map_id=mim.manufacturer_item_map_id   and mim.item_names_id=itn.item_names_id "
//                        + " and indi.purpose_id=p.purpose_id "
//                        + " and indi.status_id=s.status_id and indt.active='Y' and indi.active='Y' and itn.active='Y' and m.active='Y' and mim.active='Y' "
//                        + " and indt.indent_table_id='" + indent_table_id + "' ";
//            }

            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                Indent bean = new Indent();
                bean.setIndent_no(rset.getString("indent_no"));
                bean.setItem_name((rset.getString("item_name")));
                bean.setModel((rset.getString("model")));
                bean.setPurpose((rset.getString("purpose")));
                bean.setRequired_qty(rset.getInt("required_qty"));
                bean.setApproved_qty(rset.getInt("approved_qty"));
                bean.setDelivered_qty(rset.getInt("deliver_qty"));
                bean.setStock_qty(rset.getInt("stock_qty"));
                bean.setExpected_date_time(rset.getString("expected_date_time"));
                String status = rset.getString("status");
//                if(status.equals("Request Sent")){
//                    status="Pending";
//                }
                bean.setStatus(status);
                bean.setIndent_item_id(rset.getInt("indent_item_id"));

                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: IndentModel getIndentItems-" + e);
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

    public List<Indent> getBlankIndentData(String logged_user_name) {
        List<Indent> list = new ArrayList<Indent>();
        int counting = 100;
        int count = 0;
        String query = " SELECT oo.address_line1,oo.address_line2,oo.address_line3,c.city_name "
                + " FROM key_person kp,org_office oo,city c where c.city_id=oo.city_id and c.active='Y' and oo.active='Y' "
                + " and kp.active='Y' and kp.org_office_id=oo.org_office_id ";
        if (!logged_user_name.equals("") && logged_user_name != null) {
            query += " and kp.key_person_name='" + logged_user_name + "' ";
        }

        String query1 = " SELECT indt.indent_no from indent_table indt where indt.active='Y' order by indt.indent_table_id desc limit 1 ";
        try {
            PreparedStatement psmt = connection.prepareStatement(query1);
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                Indent bean = new Indent();
                String indent_no = rs.getString("indent_no");
                String indent_no_arr[] = indent_no.split("_");
                int length = (indent_no_arr.length) - 1;
                count = Integer.parseInt(indent_no_arr[length]);
                counting = count;
                bean.setIndent_no("Indent_" + (counting + 1));

                PreparedStatement psmt2 = connection.prepareStatement(query);
                ResultSet rs2 = psmt2.executeQuery();
                while (rs2.next()) {
                    String address_line1 = rs2.getString("address_line1");
                    String address_line2 = rs2.getString("address_line2");
                    String address_line3 = rs2.getString("address_line3");
                    String city_name = rs2.getString("city_name");

                    String office_address = address_line1 + ", " + address_line2 + ", " + address_line3 + ", " + city_name;

                    bean.setOffice_address(office_address);
                }
                list.add(bean);
            }

        } catch (Exception e) {
            System.out.println("Error: IndentModel getBlankIndentData-" + e);
        }
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
            System.err.println("IndentModel getLastIndentTableId error:" + e);
        }
        return indent_table_id;
    }

    public byte[] generateMapReport(String jrxmlFilePath, List<Indent> listAll) {
        byte[] reportInbytes = null;
        Connection c;
        //     HashMap mymap = new HashMap();
        try {
            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, beanColDataSource);
        } catch (Exception e) {
            System.out.println("IndentModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
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
            System.out.println("IndentModel closeConnection() Error: " + e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
