package com.inventory.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.DBConnection.DBConnection;
import com.inventory.tableClasses.Inventory;
import com.inventory.tableClasses.ItemAuthorization;
import com.inventory.tableClasses.ItemName;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;

/**
 *
 * @author Komal
 */
public class ItemAuthorizationModel {

    private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "#a2a220";
    private final String COLOR_ERROR = "red";
    int item_id = 0;
    int item_id1 = 0;

    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("ItemAuthorizationModel setConnection() Error: " + e);
        }
    }

    public List<ItemAuthorization> showData(String searchItemName, String search_designation) {
        List<ItemAuthorization> list = new ArrayList<ItemAuthorization>();

        if (searchItemName == null) {
            searchItemName = "";
        }
        if (search_designation == null) {
            search_designation = "";
        }

        String query = "select d.designation,itn.item_name,ia.item_authorization_id,ia.qty,ia.description,ia.monthly_limit from item_names itn,item_authorization ia, "
                + " designation d where itn.active='Y' and ia.active='Y' and d.active='Y' and ia.item_names_id=itn.item_names_id"
                + "  and ia.designation_id=d.designation_id ";

        if (!searchItemName.equals("") && searchItemName != null) {
            query += " and itn.item_name='" + searchItemName + "' ";
        }
        if (!search_designation.equals("") && search_designation != null) {
            query += " and d.designation='" + search_designation + "' ";
        }
        query += " order by d.designation ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                ItemAuthorization bean = new ItemAuthorization();
                bean.setItem_authorization_id(rset.getInt("item_authorization_id"));
                bean.setItem_name((rset.getString("item_name")));
                bean.setDesignation((rset.getString("designation")));
                bean.setQuantity(rset.getInt("qty"));
                bean.setMonthly_limit(rset.getInt("monthly_limit"));
                bean.setDescription(rset.getString("description"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: ItemAuthorizationModel showdata-" + e);
        }
        return list;
    }

    public List<Integer> getIdList(String logged_designation, String org_office, String searchItemName) {
        List<Integer> list = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        try {
            String query = " select itn.item_names_id "
                    + " from item_names itn, item_type itt,item_authorization ia,designation d,org_office oo,org_office_designation_map oodm "
                    + " where ia.item_names_id=itn.item_names_id "
                    //                    + " and d.designation='" + logged_designation + "' and oo.org_office_name='" + org_office + "' "
                    + " and itt.item_type_id=itn.item_type_id  and itn.active='Y' and oodm.designation_id=d.designation_id "
                    + " and oodm.org_office_id=oo.org_office_id  and ia.designation_id=d.designation_id "
                    + " and oodm.org_office_designation_map_id=ia.org_office_designation_map_id "
                    + " and itt.active='y' and ia.active='Y' and d.active='Y' and oo.active='y' and oodm.active='y' ";
            if (!org_office.equals("") && org_office != null) {
                query += " and oo.org_office_name='" + org_office + "' ";
            }
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

    public List<ItemName> getItemsList(String logged_designation, String org_office, String search_item_name) {
        List<ItemName> list = new ArrayList<ItemName>();
        List<ItemName> list1 = new ArrayList<ItemName>();
        List<Integer> desig_map_list = new ArrayList<Integer>();
        List<Integer> desig_map_listAll = new ArrayList<>();
        List<Integer> desig_map_listAllFinal = new ArrayList<>();
        List<Integer> desig_map_listUnmatched = new ArrayList<>();
//        String search_item_name = "";
        String search_item_type = "";
        String search_item_codee = "";
        String search_super_child = "";
        String search_generation = "";
        try {
//            desig_map_list = getIdList(logged_designation, org_office, search_item_name);
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
            query += "  and itn.item_names_id in(" + desig_map_listAll.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ") "
                    + " order by field(itn.item_names_id," + desig_map_listAll.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ")  ";

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
                int item_name_id = (rset.getInt("item_names_id"));
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
//                if (rset.getString("is_super_child").equals("Y")) {
//                    bean1.setSuperp("N");
//                }

//                list.add(bean1);
                if (rset.getString("is_super_child").equals("Y")) {
                    String query1 = " select distinct oo.org_office_name from item_authorization ia,org_office oo,designation d, "
                            + " org_office_designation_map oodm,item_names itn "
                            + " where oo.active='Y' and d.active='Y' and itn.active='Y' and oodm.active='Y' and ia.active='Y' "
                            + " and ia.org_office_designation_map_id=oodm.org_office_designation_map_id and ia.item_names_id=itn.item_names_id "
                            + " and oo.org_office_id=oodm.org_office_id and d.designation_id=oodm.designation_id and itn.item_names_id='" + item_name_id + "'  ";
                    if (!org_office.equals("") && org_office != null) {
                        query1 += " and oo.org_office_name='" + org_office + "' ";
                    }
                    //    System.err.println("query------" + query);   
                    PreparedStatement pstmt2 = connection.prepareStatement(query1);
                    ResultSet rset2 = pstmt2.executeQuery();
                    String org_office_name = "";
                    String prev_org_office_name = "";
                    String designation = "";
                    while (rset2.next()) {
                        org_office_name = org_office_name + "," + rset2.getString("org_office_name");
                        bean1.setOrg_office(org_office_name);
//                        designation = designation + "," + rset2.getString("designation");
//                        bean1.setDesignation(designation);
                    }

                } else {
                    bean1.setOrg_office("");
                    bean1.setDesignation("");
                }

                list.add(bean1);
            }
        } catch (Exception e) {
            System.err.println("IndentModel Exception in getItemsList---------" + e);
        }

        return list;

    }

    public int insertRecord(ItemAuthorization bean, String[] des_checkbox) throws SQLException {
        String query = "INSERT INTO item_authorization(item_names_id,designation_id,description,"
                + " revision_no,active,remark,qty,monthly_limit,org_office_designation_map_id) "
                + " VALUES(?,?,?,?,?,?,?,?,?) ";

        int rowsAffected = 0;
        int item_name_id = getItemNamesId(bean.getItem_name());
        int designation_id = 0;
        int org_office_des_map_id = 0;
        String query2 = " select  item_names_id, "
                + " item_name, "
                + " parent_id "
                + "from (select * from item_names where active='Y' "
                + " order by parent_id, item_names_id ) item_names, "
                + " (select @pv := '" + item_name_id + "') initialisation "
                + " where   find_in_set(parent_id, @pv) > 0 "
                + " and  @pv := concat(@pv, ',', item_names_id) ";

        int map_count = 0;
        try {

            PreparedStatement pstmt2 = connection.prepareStatement(query2);
            ResultSet rs2 = pstmt2.executeQuery();
            int item_names_id = 0;
            List<Integer> item_names_id_list = new ArrayList<>();
            while (rs2.next()) {
                item_names_id = rs2.getInt("item_names_id");
                item_names_id_list.add(item_names_id);
            }
            if (item_names_id_list.size() == 0) {
                for (int k = 0; k < des_checkbox.length; k++) {
                    org_office_des_map_id = getOrgOfficeDesignationMapId(bean.getOrg_office(), des_checkbox[k]);
                    String query4 = "SELECT count(*) as count FROM item_authorization WHERE "
                            + " item_names_id='" + item_name_id + "' and org_office_designation_map_id='" + org_office_des_map_id + "'"
                            + " and active='Y'  ";
                    designation_id = getDesignationId(des_checkbox[k]);

                    PreparedStatement pstmt1 = connection.prepareStatement(query4);
                    ResultSet rs1 = pstmt1.executeQuery();
                    while (rs1.next()) {
                        map_count = rs1.getInt("count");
                    }
                    if (map_count > 0) {
                        message = "Designation has already mapped to this item!..";
                        msgBgColor = COLOR_ERROR;
                    } else {
                        PreparedStatement pstmt = connection.prepareStatement(query);
                        pstmt.setInt(1, item_name_id);
                        pstmt.setInt(2, designation_id);
                        pstmt.setString(3, bean.getDescription());
                        pstmt.setInt(4, bean.getRevision_no());
                        pstmt.setString(5, "Y");
                        pstmt.setString(6, "OK");
                        pstmt.setInt(7, bean.getQuantity());
                        pstmt.setInt(8, bean.getMonthly_limit());
                        pstmt.setInt(9, org_office_des_map_id);
                        rowsAffected = pstmt.executeUpdate();
                    }
                }
            }

            String query5 = " select item_names_id from item_names where item_names_id"
                    + " in(" + item_names_id_list.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ") and is_super_child='Y'"
                    + "  and active='Y' ";

            PreparedStatement pstmt5 = connection.prepareStatement(query5);
            ResultSet rs5 = pstmt5.executeQuery();
            item_names_id_list.clear();
            while (rs5.next()) {
                item_name_id = rs5.getInt("item_names_id");
                item_names_id_list.add(item_name_id);
            }

            for (int j = 0; j < item_names_id_list.size(); j++) {
                for (int k = 0; k < des_checkbox.length; k++) {
                    org_office_des_map_id = getOrgOfficeDesignationMapId(bean.getOrg_office(), des_checkbox[k]);
                    item_name_id = item_names_id_list.get(j);
                    String query4 = " SELECT count(*) as count FROM item_authorization WHERE "
                            + " item_names_id='" + item_name_id + "' and designation_id='" + designation_id + "' "
                            + " and active='Y'  ";
                    PreparedStatement pstmt1 = connection.prepareStatement(query4);
                    ResultSet rs1 = pstmt1.executeQuery();
                    while (rs1.next()) {
                        map_count = rs1.getInt("count");
                    }
                    if (map_count > 0) {
                        message = "Designation has already mapped to this item!..";
                        msgBgColor = COLOR_ERROR;
                    } else {
                        PreparedStatement pstmt = connection.prepareStatement(query);
                        pstmt.setInt(1, item_name_id);
                        pstmt.setInt(2, designation_id);
                        pstmt.setString(3, bean.getDescription());
                        pstmt.setInt(4, bean.getRevision_no());
                        pstmt.setString(5, "Y");
                        pstmt.setString(6, "OK");
                        pstmt.setInt(7, bean.getQuantity());
                        pstmt.setInt(8, bean.getMonthly_limit());
                        pstmt.setInt(9, org_office_des_map_id);

                        rowsAffected = pstmt.executeUpdate();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("ItemAuthorizationModel insertRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        if (map_count > 0) {
            message = "Designation has already mapped to this item!..";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int getOrgOfficeDesignationMapId(String org_office, String designation) {
        String query = " SELECT org_office_designation_map_id "
                + " FROM org_office_designation_map oodm,org_office oo,designation d WHERE d.active='Y' and oo.active='Y' "
                + " and oodm.active='Y' and oodm.designation_id=d.designation_id and oodm.org_office_id=oo.org_office_id "
                + " and oo.org_office_name='" + org_office + "' and d.designation='" + designation + "' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //// pstmt.setString(1, item_type);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("org_office_designation_map_id");
        } catch (Exception e) {
            System.out.println("ItemNameModel getOrgOfficeDesignationMapId Error: " + e);
        }
        return id;
    }

    public int updateRecord(ItemAuthorization bean, int item_authorization_id) {
        int revision = ItemAuthorizationModel.getRevisionno(bean, item_authorization_id);
        int updateRowsAffected = 0;
        int item_name_id = getItemNamesId(bean.getItem_name());
        int designation_id = getDesignationId(bean.getDesignation());

        String query1 = "SELECT max(revision_no) revision_no FROM item_authorization WHERE item_authorization_id = " + item_authorization_id + " "
                + " and active='Y' ";
        String query2 = "UPDATE item_authorization SET active=? WHERE item_authorization_id=? and revision_no=? ";
        String query3 = "INSERT INTO item_authorization(item_authorization_id,item_names_id,designation_id,description,"
                + " revision_no,active,remark,qty,monthly_limit) "
                + " VALUES(?,?,?,?,?,?,?,?,?)";

        int rowsAffected = 0;
        int map_count = 0;
        try {
            String query4 = "SELECT count(*) as count FROM item_authorization WHERE "
                    + " item_names_id='" + item_name_id + "' and designation_id='" + designation_id + "'"
                    + " and active='Y'  ";

            PreparedStatement pstmt1 = connection.prepareStatement(query4);
            ResultSet rs1 = pstmt1.executeQuery();
            while (rs1.next()) {
                map_count = rs1.getInt("count");
            }
            if (map_count > 0) {
                message = "Designation has already mapped to this item!..";
                msgBgColor = COLOR_ERROR;
            } else {
                PreparedStatement pstmt = connection.prepareStatement(query1);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    PreparedStatement pstm = connection.prepareStatement(query2);
                    pstm.setString(1, "n");
                    pstm.setInt(2, item_authorization_id);
                    pstm.setInt(3, revision);
                    updateRowsAffected = pstm.executeUpdate();
                    if (updateRowsAffected >= 1) {
                        revision = rs.getInt("revision_no") + 1;
                        PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                        psmt.setInt(1, item_authorization_id);
                        psmt.setInt(2, item_name_id);
                        psmt.setInt(3, designation_id);
                        psmt.setString(4, (bean.getDescription()));
                        psmt.setInt(5, revision);
                        psmt.setString(6, "Y");
                        psmt.setString(7, "OK");
                        psmt.setInt(8, bean.getQuantity());
                        psmt.setInt(9, bean.getMonthly_limit());
                        rowsAffected = psmt.executeUpdate();

                    }

                }
            }
        } catch (Exception e) {
            System.out.println("ItemAuthorizationModel updateRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record updated successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
//        if (map_count > 0) {
//            message = "Item has already assigned to this person!..";
//            msgBgColor = COLOR_ERROR;
//        }
        return rowsAffected;
    }

    public static int getRevisionno(ItemAuthorization bean, int item_authorization_id) {
        int revision = 0;
        try {
            String query = " SELECT max(revision_no) as revision_no FROM item_authorization "
                    + " WHERE item_authorization_id =" + item_authorization_id + "  and active='Y' ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
            System.err.println("ItemAuthorizationModel getRevisionno error:" + e);
        }
        return revision;
    }

    public int deleteRecord(int item_authorization_id) {
        String query = "DELETE FROM item_authorization WHERE item_authorization_id = " + item_authorization_id;
        int rowsAffected = 0;
        try {

            rowsAffected = connection.prepareStatement(query).executeUpdate();

        } catch (Exception e) {
            System.out.println("ItemAuthorizationModel deleteRecord() Error: " + e);
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

    public int getItemNamesId(String item_name) {
        String query = "SELECT item_names_id FROM item_names WHERE item_name = '" + item_name + "' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("item_names_id");
        } catch (Exception e) {
            System.out.println("ItemAuthorizationModel getItemNamesId Error: " + e);
        }
        return id;
    }

    public int getDesignationId(String designation) {

        String query = "SELECT designation_id FROM designation WHERE designation = '" + designation + "' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("designation_id");
        } catch (Exception e) {
            System.out.println("ItemAuthorizationModel getDesignationId Error: " + e);
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
            System.out.println("ItemAuthorizationModel getItemName Error: " + e);
        }
        return name;
    }

    public List<String> getDesignation(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT designation from designation where active='Y' ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String designation = (rset.getString("designation"));
                if (designation.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(designation);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such designation  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:ItemAuthorizationModel--getDesignation()-- " + e);
        }
        return list;
    }

    public List<String> getItemName(String q) {
        List<String> list = new ArrayList<String>();
//        String query = "SELECT item_name from item_names where active='Y' and is_super_child='Y' ";
        String query = "SELECT item_name from item_names where active='Y' ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String item_name = (rset.getString("item_name"));
                if (item_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(item_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such ItemName  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:ItemAuthorizationModel--getItemName()-- " + e);
        }
        return list;
    }

    public List<String> getAllChild(String item_names_id) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT item_names_id from item_names where active='Y' ";

        try {
            List<Integer> idList = new ArrayList<Integer>();
            List<String> parentItemNameList = new ArrayList<String>();
            int count = 0;

            idList = getAllParentChildList(item_names_id);
            query = "select itn.item_names_id,itn.item_name,itn.description,itn.item_code,itt.item_type,itn.quantity,itn.parent_id,"
                    + "itn.generation,itn.is_super_child,itn.prefix,itn.HSNCode  "
                    + " from item_names itn, item_type itt where itt.item_type_id=itn.item_type_id and itn.active='Y' and itt.active='y' ";
            if (!item_names_id.equals("")) {
                query += "and itn.item_names_id in(" + idList.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ") ";

            }
            if (!item_names_id.equals("")) {
                query += " order by field(itn.item_names_id," + idList.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ") ";
            } else {
                query += "order by generation ";
            }
            ResultSet rset = connection.prepareStatement(query).executeQuery();

            while (rset.next()) {
                list.add(rset.getString("item_names_id"));
            }

        } catch (Exception e) {
            System.out.println("Error:ItemAuthorizationModel--getItemName()-- " + e);
        }
        return list;
    }

    public List<Integer> getAllParentChildList(String item_id) {
        PreparedStatement pstmt;
        String query = "";
        List<Integer> list = new ArrayList<Integer>();

        if (item_id == null) {
            item_id = "";
        }
        int item_names_id = 0, parent_id = 0;

        String qry = "select item_names_id from item_names where active='Y' and item_names_id='" + item_id + "' ";
        try {
            PreparedStatement pst = connection.prepareStatement(qry);
            ResultSet rstt = pst.executeQuery();
            while (rstt.next()) {
                item_names_id = rstt.getInt(1);
                list.add(item_names_id);
            }
        } catch (Exception e) {
            System.out.println("ItemNameModel.getAllParentChildList() -" + e);
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
            System.out.println("ItemNameModel.getAllParentChildList() -" + e);
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
            System.out.println("Error:--ItemNameModel--- getAllParentChildList--" + e);
        }
        String qry2 = "select item_names_id from item_names where active='Y' and parent_id='" + item_names_id + "' ";
        try {
            PreparedStatement pst = connection.prepareStatement(qry2);
            ResultSet rstt = pst.executeQuery();
            while (rstt.next()) {
                list.add(rstt.getInt(1));
            }
        } catch (Exception e) {
            System.out.println("ItemNameModel.getAllParentChildList() -" + e);
        }

        list.removeAll(Arrays.asList(0));

        return list;
    }

    public List<ItemAuthorization> getMappedItemDesignation(String item_names_id, String org_office) {
        List<ItemAuthorization> list = new ArrayList<ItemAuthorization>();

        if (item_names_id == null) {
            item_names_id = "";
        }
        if (org_office == null) {
            org_office = "";
        }

        String query = " select distinct d.designation,itn.item_name,ia.item_authorization_id,ia.qty,ia.description,ia.monthly_limit "
                + " from item_names itn,item_authorization ia, "
                + " designation d,org_office oo,org_office_designation_map oodm  "
                + " where itn.active='Y' and ia.active='Y' and d.active='Y' and ia.item_names_id=itn.item_names_id"
                + " and ia.designation_id=d.designation_id and oodm.designation_id=d.designation_id and oodm.org_office_id=oo.org_office_id "
                + " and oodm.org_office_designation_map_id=ia.org_office_designation_map_id ";

        if (!org_office.equals("") && org_office != null) {
            query += " and oo.org_office_name='" + org_office + "' ";
        }
        if (!item_names_id.equals("") && item_names_id != null) {
            query += " and itn.item_names_id='" + item_names_id + "' ";
        }
        query += " order by d.designation ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                ItemAuthorization bean = new ItemAuthorization();
                bean.setItem_authorization_id(rset.getInt("item_authorization_id"));
                bean.setItem_name((rset.getString("item_name")));
                bean.setDesignation((rset.getString("designation")));
                bean.setQuantity(rset.getInt("qty"));
                bean.setMonthly_limit(rset.getInt("monthly_limit"));
                bean.setDescription(rset.getString("description"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: ItemAuthorizationModel showdata-" + e);
        }
        return list;
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
            System.out.println("ItemAuthorizationModel closeConnection() Error: " + e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
