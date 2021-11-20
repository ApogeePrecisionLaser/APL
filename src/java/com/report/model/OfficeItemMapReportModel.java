package com.report.model;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.simple.JSONObject;
import com.DBConnection.DBConnection;
import com.inventory.model.InventoryBasicModel;
import com.inventory.model.ItemNameModel;
import com.inventory.tableClasses.Inventory;
import com.inventory.tableClasses.InventoryBasic;
import com.inventory.tableClasses.ItemName;
import com.report.bean.OfficeItemMapReport;
import java.io.File;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;

/**
 *
 * @author Komal
 */
public class OfficeItemMapReportModel {

    private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "#a2a220";
    private final String COLOR_ERROR = "red";
    int item_id = 0;
    int item_id1 = 0;
    int prev_item_name_id1 = 0;
    int prev_item_name_id2 = 0;
    int prev_item_name_id3 = 0;
    int prev_item_name_id4 = 0;
    int prev_item_name_id5 = 0;
    int prev_item_name_id6 = 0;
    int prev_item_name_id7 = 0;
    int prev_item_name_id8 = 0;
    int prev_item_name_id9 = 0;

    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("InventoryBasicModel setConnection() Error: " + e);
        }
    }

    public List<Integer> getIdList(String searchItemName, String searchOrgOffice, String search_manufacturer, String search_item_code, String search_model, String searchKeyPerson) throws SQLException {
        List<Integer> list = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        try {
            String query = " select itn.item_names_id "
                    + " from item_names itn, item_type itt,manufacturer_item_map mim,model m,inventory_basic ib,inventory inv, "
                    + " key_person kp,org_office oo,manufacturer mr "
                    + " where itt.item_type_id=itn.item_type_id and mim.item_names_id=itn.item_names_id"
                    + " and mim.manufacturer_item_map_id=m.manufacturer_item_map_id "
                    + " and ib.model_id=m.model_id and ib.inventory_basic_id=inv.inventory_basic_id and kp.key_person_id=inv.key_person_id "
                    + " and oo.org_office_id=ib.org_office_id "
                    + " and ib.item_names_id=itn.item_names_id and mr.manufacturer_id=mim.manufacturer_id "
                    + " and m.active='Y' and ib.active='Y' and inv.active='Y' and kp.active='Y' and oo.active='Y' and itn.active='Y' "
                    + " and itt.active='y' and mim.active='Y' and mr.active='Y' ";

            if (!search_item_code.equals("") && search_item_code != null) {
                query += " and itn.item_code='" + search_item_code + "' ";
            }
            if (!searchOrgOffice.equals("") && searchOrgOffice != null) {
                query += " and oo.org_office_name='" + searchOrgOffice + "' ";
            }
            if (!searchKeyPerson.equals("") && searchKeyPerson != null) {
                query += " and kp.key_person_name='" + searchKeyPerson + "' ";
            }

            if (!search_manufacturer.equals("") && search_manufacturer != null) {
                query += " and mr.manufacturer_name='" + search_manufacturer + "' ";
            }
            if (!search_model.equals("") && search_model != null) {
                query += " and m.model='" + search_model + "' ";

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

                for (int v = 0; v < ee.get(0).size(); v++) {
                    list4.add((Integer) ee.get(0).get(v));
                }
            }

            //  System.err.println("list 4 size-" + list4.size());
            //  System.err.println("list 4 sttring-" + list4);
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
                        + " ORDER BY T1.lvl DESC ";

                ResultSet rst2 = connection.prepareStatement(qry).executeQuery();
                while (rst2.next()) {
                    list.add(rst2.getInt(1));
                }
            }

        } catch (Exception e) {
            System.out.println("com.inventory.model.IndentModel.getIdList() -" + e);
        }
        return list;
    }

    public List<OfficeItemMapReport> showData(String searchItemName, String searchOrgOffice, String search_manufacturer, String search_item_code, String search_model, String searchKeyPerson) {
        List<OfficeItemMapReport> list = new ArrayList<OfficeItemMapReport>();
        List<Integer> desig_map_list = new ArrayList<>();
        List<Integer> desig_map_listAll = new ArrayList<>();
        List<Integer> desig_map_listAllFinal = new ArrayList<>();
        List<Integer> desig_map_listUnmatched = new ArrayList<>();

        String search_item_name = "";
        String search_item_type = "";
        String search_item_codee = "";
        String search_super_child = "";
        String search_generation = "";

        try {
            desig_map_list = getIdList(searchItemName, searchOrgOffice, search_manufacturer, search_item_code, search_model, searchKeyPerson);
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

            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            int count = 1;
            int count1 = 1;
            int count2 = 1;
            int count3 = 1;
            int count4 = 1;
            int count5 = 1;
            int count6 = 1;
            int count7 = 1;
            int count8 = 1;
            int count9 = 1;
            while (rset.next()) {
                OfficeItemMapReport bean = new OfficeItemMapReport();

                bean.setItem_name((rset.getString("item_name")));
                bean.setItem_names_id((rset.getInt("item_names_id")));
                bean.setParent_item_id((rset.getString("parent_id")));
                bean.setGeneration((rset.getInt("generation")));
                bean.setItem_code((rset.getString("item_code")));
                String is_super_child = rset.getString("is_super_child");

                String item_name = "";
                if (is_super_child.equals("Y")) {
                    int item_id = rset.getInt("item_names_id");

                    String query2 = " select itn.item_names_id,itn.item_name,itn.description,itn.item_code,itt.item_type,itn.quantity,itn.parent_id,"
                            + " itn.generation,itn.is_super_child,itn.prefix,inv.inventory_id,ib.inventory_basic_id,oo.org_office_name,"
                            + " kp.key_person_name,mr.manufacturer_name,m.model,inv.reference_document_id, "
                            + " inv.inward_quantity,inv.outward_quantity,inv.stock_quantity,inv.date_time,inv.reference_document_type"
                            + " from item_names itn, item_type itt,manufacturer_item_map mim,model m,inventory_basic ib,inventory inv,key_person kp,"
                            + " org_office oo,manufacturer mr "
                            + " where itt.item_type_id=itn.item_type_id and itn.active='Y' and itt.active='y' and mim.item_names_id=itn.item_names_id "
                            + " and mim.manufacturer_item_map_id=m.manufacturer_item_map_id "
                            + " and ib.model_id=m.model_id and ib.inventory_basic_id=inv.inventory_basic_id and kp.key_person_id=inv.key_person_id "
                            + " and oo.org_office_id=ib.org_office_id and mim.active='Y' "
                            + " and m.active='Y' and ib.active='Y' and inv.active='Y' and kp.active='Y' and oo.active='Y' "
                            + " and ib.item_names_id=itn.item_names_id and mr.manufacturer_id=mim.manufacturer_id and mr.active='Y' and "
                            + " itn.item_names_id='" + item_id + "'  and kp.designation_id=5 ";

                    if (!search_item_code.equals("") && search_item_code != null) {
                        query2 += " and itn.item_code='" + search_item_code + "' ";
                    }
                    if (!searchOrgOffice.equals("") && searchOrgOffice != null) {
                        query2 += " and oo.org_office_name='" + searchOrgOffice + "' ";
                    }
                    if (!searchKeyPerson.equals("") && searchKeyPerson != null) {
                        query2 += " and kp.key_person_name='" + searchKeyPerson + "' ";
                    }

                    if (!search_manufacturer.equals("") && search_manufacturer != null) {
                        query2 += " and mr.manufacturer_name='" + search_manufacturer + "' ";
                    }
                    if (!search_model.equals("") && search_model != null) {
                        query2 += " and m.model='" + search_model + "' ";
                    }

                    PreparedStatement pstmt2 = connection.prepareStatement(query2);
                    ResultSet rset2 = pstmt2.executeQuery();
                    int i = 0;
                    while (rset2.next()) {

                        bean.setInventory_id(rset2.getInt("inventory_id"));
                        bean.setInventory_basic_id(rset2.getInt("inventory_basic_id"));
                        bean.setOrg_office(rset2.getString("org_office_name"));
                        bean.setKey_person(rset2.getString("key_person_name"));
                        bean.setItem_name(rset2.getString("item_name"));
                        item_name = rset2.getString("item_name");
                        bean.setItem_code(rset2.getString("item_code"));
                        bean.setInward_quantity(rset2.getInt("inward_quantity"));
                        bean.setOutward_quantity(rset2.getInt("outward_quantity"));
                        int stock_quantity = (rset2.getInt("stock_quantity"));
                        bean.setStock_quantity(stock_quantity);
                        bean.setDate_time(rset2.getString("date_time"));
                        bean.setDescription(rset2.getString("description"));
                        //  bean.setCounting(Integer.toString(i));

                        String prev_manufacturer_name = bean.getManufacturer_name();
                        String prev_manufacturer_name1 = "";
                        String prev_manufacturer_name2 = "";
                        if (prev_manufacturer_name == null) {
                            prev_manufacturer_name = "";
                        }
                        if (!prev_manufacturer_name.equals("")) {
                            String arr[] = prev_manufacturer_name.split(".  ");
                            prev_manufacturer_name1 = arr[1];
                            prev_manufacturer_name2 = arr[0];
                        }
                        if ((!prev_manufacturer_name.equals("")) && (!prev_manufacturer_name1.equals(rset2.getString("manufacturer_name")))) {
                            if (i == 0) {
                                bean.setManufacturer_name((i + 1) + ".  " + prev_manufacturer_name + "<br/>" + (i + 1) + ".  " + rset2.getString("manufacturer_name"));

                            } else {
                                bean.setManufacturer_name(prev_manufacturer_name + "<br/>" + (i + 1) + ". " + rset2.getString("manufacturer_name"));
                            }
                        } else {
                            bean.setManufacturer_name("1.  " + rset2.getString("manufacturer_name"));
                        }

                        String prev_model = bean.getModel();
                        String prev_model1 = "";
                        if (prev_model == null) {
                            prev_model = "";
                        }
                        if (!prev_model.equals("")) {
                            String arr[] = prev_model.split(".  ");
                            prev_model1 = arr[1];
                        }
                        if ((!prev_model.equals("")) && (!prev_model1.equals(rset2.getString("model")))) {
                            if (i == 0) {
                                bean.setModel((i + 1) + ".  " + prev_model + "<br/>" + prev_manufacturer_name2 + ". " + (i + 1) + ".  " + rset2.getString("model"));

                            } else {
                                bean.setModel(prev_model + "<br/>" + prev_manufacturer_name2 + ". " + (i + 1) + ". " + rset2.getString("model"));
                            }
                        } else {
                            bean.setModel("1. 1.  " + rset2.getString("model"));

                        }
//                        OfficeItemMapReport bean1 = new OfficeItemMapReport();
//                        bean1.setModel(rset2.getString("model"));
//                        List<OfficeItemMapReport> list1 = new ArrayList<OfficeItemMapReport>();
//                        list1.add(bean1);
//                       
//                        bean.setList(list1);
                        bean.setReference_document_type(rset2.getString("reference_document_type"));
                        bean.setReference_document_id(rset2.getString("reference_document_id"));
                        bean.setPopupval("");
                        i++;
                    }
                } else {
                    bean.setInventory_id(0);
                    bean.setInventory_basic_id(0);
                    bean.setOrg_office("");
                    bean.setKey_person("");
                    bean.setInward_quantity(0);
                    bean.setOutward_quantity(0);
                    bean.setStock_quantity(0);
                    bean.setDate_time("");
                    bean.setDescription("");
                    bean.setManufacturer_name("");
                    bean.setModel("");
                    bean.setReference_document_type("");
                    bean.setReference_document_id("");
                    bean.setPopupval("");
                    // bean.setCounting(Integer.toString(0));

                }

                /*              Start  Generate Serial No.          */
                if ((rset.getString("parent_id")).equals("0")) {
                    bean.setCounting(Integer.toString(count));
                    count++;
                    prev_item_name_id1 = rset.getInt("item_names_id");
                    count1 = 1;
                    bean.setColor("yellow");
                }
                if ((rset.getInt("generation") == 2) && (prev_item_name_id1 == rset.getInt("parent_id"))) {

                    bean.setCounting((count - 1) + "." + Integer.toString(count1));
                    count1++;
                    prev_item_name_id2 = rset.getInt("item_names_id");
                    count2 = 1;
                    bean.setColor("red");
                }
                if ((rset.getInt("generation") == 3) && (prev_item_name_id2 == rset.getInt("parent_id"))) {
                    bean.setCounting((count - 1) + "." + Integer.toString(count1 - 1) + "." + Integer.toString(count2));
                    count2++;
                    prev_item_name_id3 = rset.getInt("item_names_id");
                    count3 = 1;
                    bean.setColor("green");
                }
                if ((rset.getInt("generation") == 4) && (prev_item_name_id3 == rset.getInt("parent_id"))) {
                    bean.setCounting((count - 1) + "." + Integer.toString(count1 - 1) + "." + Integer.toString(count2 - 1) + "." + Integer.toString(count3));
                    count3++;
                    prev_item_name_id4 = rset.getInt("item_names_id");
                    count4 = 1;
                    bean.setColor("pink");
                }
                if ((rset.getInt("generation") == 5) && (prev_item_name_id4 == rset.getInt("parent_id"))) {
                    bean.setCounting((count - 1) + "." + Integer.toString(count1 - 1) + "." + Integer.toString(count2 - 1) + "." + Integer.toString(count3 - 1) + "." + Integer.toString(count4));
                    count4++;
                    prev_item_name_id5 = rset.getInt("item_names_id");
                    count5 = 1;
                    bean.setColor("#CCCCFF");
                }
                if ((rset.getInt("generation") == 6) && (prev_item_name_id5 == rset.getInt("parent_id"))) {
                    bean.setCounting((count - 1) + "." + Integer.toString(count1 - 1) + "." + Integer.toString(count2 - 1) + "." + Integer.toString(count3 - 1) + "." + Integer.toString(count4 - 1) + "." + Integer.toString(count5));
                    count5++;
                    prev_item_name_id6 = rset.getInt("item_names_id");
                    count6 = 1;
                    bean.setColor("orange");
                }
                if ((rset.getInt("generation") == 7) && (prev_item_name_id6 == rset.getInt("parent_id"))) {
                    bean.setCounting((count - 1) + "." + Integer.toString(count1 - 1) + "." + Integer.toString(count2 - 1) + "." + Integer.toString(count3 - 1) + "." + Integer.toString(count4 - 1) + "." + Integer.toString(count5 - 1) + "." + Integer.toString(count6));
                    count6++;
                    prev_item_name_id7 = rset.getInt("item_names_id");
                    count7 = 1;
                    bean.setColor("#99FFCC");
                }
                if ((rset.getInt("generation") == 8) && (prev_item_name_id7 == rset.getInt("parent_id"))) {
                    bean.setCounting((count - 1) + "." + Integer.toString(count1 - 1) + "." + Integer.toString(count2 - 1) + "." + Integer.toString(count3 - 1) + "." + Integer.toString(count4 - 1) + "." + Integer.toString(count5 - 1) + "." + Integer.toString(count6 - 1) + "." + Integer.toString(count7));
                    count7++;
                    prev_item_name_id8 = rset.getInt("item_names_id");
                    count8 = 1;
                    bean.setColor("#CCCCCC");
                }
                if ((rset.getInt("generation") == 9) && (prev_item_name_id8 == rset.getInt("parent_id"))) {
                    bean.setCounting((count - 1) + "." + Integer.toString(count1 - 1) + "." + Integer.toString(count2 - 1) + "." + Integer.toString(count3 - 1) + "." + Integer.toString(count4 - 1) + "." + Integer.toString(count5 - 1) + "." + Integer.toString(count6 - 1) + "." + Integer.toString(count7 - 1) + "." + Integer.toString(count8));
                    count8++;
                    prev_item_name_id9 = rset.getInt("item_names_id");
                    count9 = 1;
                    bean.setColor("#33B444");
                }

                /*              End  Generate Serial No.     */
                if (!searchOrgOffice.equals("") && searchOrgOffice != null) {
                    bean.setSearchOrgOffice(searchOrgOffice);
                } else {
                    bean.setSearchOrgOffice("");
                }
                list.add(bean);

            }
        } catch (Exception e) {
            System.err.println("Exception in getItemsList---------" + e);
        }

        return list;
    }

    public int insertRecord(InventoryBasic bean) throws SQLException {
        String query = "INSERT INTO inventory_basic(item_names_id,org_office_id,description,"
                + " revision_no,active,remark,min_quantity,daily_req,opening_balance,model_id) VALUES(?,?,?,?,?,?,?,?,?,?) ";
        int rowsAffected2 = 0;
        int rowsAffected = 0;
        int inventory_basic_id = 0;
        String item_code = bean.getItem_code();
        if (!item_code.equals("")) {
            String item_code_arr[] = item_code.split(" - ");
            item_code = item_code_arr[1];
        }

        int item_name_id = getItemNamesId(item_code);
        int org_office_id = getOrgOfficeId(bean.getOrg_office());
        int model_id = getModelId(bean.getModel());
        int key_person_id = getKeyPersonId(bean.getKey_person());
        int stock_quantity = getStockQuantity(item_name_id);

        int map_count = 0;
        try {
            String query4 = " SELECT count(*) as count FROM inventory_basic ib,inventory inv,model m WHERE "
                    + " ib.item_names_id='" + item_name_id + "' and ib.org_office_id='" + org_office_id + "' "
                    + " and inv.key_person_id='" + key_person_id + "' and ib.model_id='" + model_id + "' "
                    + " and ib.active='Y' and inv.active='Y' and inv.inventory_basic_id=ib.inventory_basic_id and m.model_id=ib.model_id "
                    + " and m.active='Y' ";

            System.err.println("query4----------" + query4);

            PreparedStatement pstmt1 = connection.prepareStatement(query4);
            ResultSet rs1 = pstmt1.executeQuery();
            while (rs1.next()) {
                map_count = rs1.getInt("count");
            }
            if (map_count > 0) {
                message = "Item Model has already mapped with this Office and person!..";
                msgBgColor = COLOR_ERROR;
            } else {
                PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, item_name_id);
                pstmt.setInt(2, org_office_id);
                pstmt.setString(3, bean.getDescription());
                pstmt.setInt(4, bean.getRevision_no());
                pstmt.setString(5, "Y");
                pstmt.setString(6, "OK");
                pstmt.setInt(7, bean.getMin_quantity());
                pstmt.setInt(8, bean.getDaily_req());
                pstmt.setString(9, bean.getOpening_balance());
                pstmt.setInt(10, model_id);
                rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    ResultSet rs = pstmt.getGeneratedKeys();
                    while (rs.next()) {
                        inventory_basic_id = rs.getInt(1);
                    }

                    String query2 = " INSERT INTO inventory(inventory_basic_id,key_person_id,description,"
                            + " revision_no,active,remark,inward_quantity,outward_quantity,date_time,"
                            + " reference_document_type,reference_document_id,stock_quantity) "
                            + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?) ";

                    PreparedStatement pstmt2 = connection.prepareStatement(query2);
                    pstmt2.setInt(1, inventory_basic_id);
                    pstmt2.setInt(2, key_person_id);
                    pstmt2.setString(3, bean.getDescription());
                    pstmt2.setInt(4, bean.getRevision_no());
                    pstmt2.setString(5, "Y");
                    pstmt2.setString(6, "OK");
                    pstmt2.setInt(7, stock_quantity);
                    pstmt2.setInt(8, 0);
                    pstmt2.setString(9, bean.getDate_time());
                    pstmt2.setString(10, "");
                    pstmt2.setString(11, "");
                    pstmt2.setInt(12, stock_quantity);
                    rowsAffected2 = pstmt2.executeUpdate();

                }
            }

        } catch (Exception e) {
            System.out.println("InventoryBasicModel insertRecord() Error: " + e);
        }
        if (rowsAffected2 > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        if (map_count > 0) {
            message = "Item Model has already mapped with this Office and person!..";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
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

    public static int getStockQuantity(int item_names_id) {
        int quantity = 0;
        try {
            String query = " SELECT quantity FROM item_names "
                    + " WHERE item_names_id =" + item_names_id + "  and active='Y' ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                quantity = rset.getInt("quantity");

            }
        } catch (Exception e) {
            System.err.println("getStockQuantity error:" + e);
        }
        return quantity;
    }

    public int updateRecord(InventoryBasic bean, int inventory_basic_id, int inventory_id) {
        int revision = InventoryBasicModel.getRevisionno(bean, inventory_basic_id);
        int revision2 = InventoryBasicModel.getRevisionno2(bean, inventory_id);
        int updateRowsAffected = 0;
        int updateRowsAffected2 = 0;
        String item_code = bean.getItem_code();
        if (!item_code.equals("")) {
            String item_code_arr[] = item_code.split(" - ");
            item_code = item_code_arr[1];
        }

        int item_name_id = getItemNamesId(item_code);
        int org_office_id = getOrgOfficeId(bean.getOrg_office());
        int model_id = getModelId(bean.getModel());
        int key_person_id = getKeyPersonId(bean.getKey_person());
        int stock_quantity = getStockQuantity(item_name_id);

        int map_count = 0;
        String query1 = "SELECT max(revision_no) revision_no FROM inventory_basic WHERE inventory_basic_id = " + inventory_basic_id + "  && active='Y' ";
        String query2 = "UPDATE inventory_basic SET active=? WHERE inventory_basic_id=? and revision_no=? ";
        String query3 = "INSERT INTO inventory_basic(inventory_basic_id,item_names_id,org_office_id,description,"
                + " revision_no,active,remark,min_quantity,daily_req,opening_balance,model_id) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        int rowsAffected = 0;
        int rowsAffected2 = 0;
        try {
            String query4 = " SELECT count(*) as count FROM inventory_basic ib,inventory inv,model m WHERE "
                    + " ib.item_names_id='" + item_name_id + "' and ib.org_office_id='" + org_office_id + "'"
                    + " and ib.model_id='" + model_id + "' and inv.key_person_id='" + key_person_id + "' and ib.model_id='" + model_id + "' "
                    + " and ib.active='Y' and inv.active='Y' and ib.inventory_basic_id=inv.inventory_basic_id and m.model_id=ib.model_id "
                    + " and m.active='Y' ";

            PreparedStatement pstmt1 = connection.prepareStatement(query4);
            ResultSet rs1 = pstmt1.executeQuery();
            while (rs1.next()) {
                map_count = rs1.getInt("count");
            }
            if (map_count > 0) {
                message = "Item Model has already mapped with this Office and person!..";
                msgBgColor = COLOR_ERROR;
            } else {
                PreparedStatement pstmt = connection.prepareStatement(query1);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    PreparedStatement pstm = connection.prepareStatement(query2);

                    pstm.setString(1, "n");
                    pstm.setInt(2, inventory_basic_id);
                    pstm.setInt(3, revision);
                    updateRowsAffected = pstm.executeUpdate();
                    if (updateRowsAffected >= 1) {
                        revision = rs.getInt("revision_no") + 1;
                        PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                        psmt.setInt(1, (bean.getInventory_basic_id()));
                        psmt.setInt(2, item_name_id);
                        psmt.setInt(3, org_office_id);
                        psmt.setString(4, (bean.getDescription()));
                        psmt.setInt(5, revision);
                        psmt.setString(6, "Y");
                        psmt.setString(7, "OK");
                        psmt.setInt(8, bean.getMin_quantity());
                        psmt.setInt(9, bean.getDaily_req());
                        psmt.setString(10, bean.getOpening_balance());
                        psmt.setInt(11, model_id);

                        rowsAffected = psmt.executeUpdate();
                    }
                }
            }

            String query1_inventory = "SELECT max(revision_no) revision_no FROM inventory WHERE inventory_id = " + inventory_id + "  and active='Y' ";
            String query2_inventory = "UPDATE inventory SET active=? WHERE inventory_id=? and revision_no=? ";
            String query3_inventory = "INSERT INTO inventory(inventory_id,inventory_basic_id,key_person_id,description,"
                    + " revision_no,active,remark,inward_quantity,outward_quantity,date_time,reference_document_type,reference_document_id,stock_quantity) "
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt2 = connection.prepareStatement(query1_inventory);
            ResultSet rs_inventory = pstmt2.executeQuery();
            if (rs_inventory.next()) {
                PreparedStatement pstm2 = connection.prepareStatement(query2_inventory);
                pstm2.setString(1, "n");
                pstm2.setInt(2, inventory_id);
                pstm2.setInt(3, revision2);
                updateRowsAffected2 = pstm2.executeUpdate();
                if (updateRowsAffected2 >= 1) {
                    revision2 = rs_inventory.getInt("revision_no") + 1;
                    PreparedStatement psmt2 = (PreparedStatement) connection.prepareStatement(query3_inventory);
                    psmt2.setInt(1, inventory_id);
                    psmt2.setInt(2, inventory_basic_id);
                    psmt2.setInt(3, key_person_id);
                    psmt2.setString(4, (bean.getDescription()));
                    psmt2.setInt(5, revision2);
                    psmt2.setString(6, "Y");
                    psmt2.setString(7, "OK");
                    psmt2.setInt(8, stock_quantity);
                    psmt2.setInt(9, 0);
                    psmt2.setString(10, bean.getDate_time());
                    psmt2.setString(11, "");
                    psmt2.setString(12, "");
                    psmt2.setInt(13, stock_quantity);
                    rowsAffected2 = psmt2.executeUpdate();

                }

            }

        } catch (Exception e) {
            System.out.println("InventoryBasicModel updateRecord() Error: " + e);
        }
        if (rowsAffected2 > 0) {
            message = "Record updated successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
        }

        return rowsAffected;
    }

    public static int getRevisionno(InventoryBasic bean, int inventory_basic_id) {
        int revision = 0;
        try {
            String query = " SELECT max(revision_no) as revision_no FROM inventory_basic "
                    + " WHERE inventory_basic_id =" + inventory_basic_id + "  and active='Y' ";

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

    public static int getRevisionno2(InventoryBasic bean, int inventory_id) {
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

    public int deleteRecord(int inventory_basic_id) {
        String query = "DELETE FROM inventory_basic WHERE inventory_basic_id = " + inventory_basic_id;
        int child_item_count = 0;
        int rowsAffected = 0;
        try {
            // PreparedStatement pstmt1 = connection.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            // pstmt1.executeUpdate();
            rowsAffected = connection.prepareStatement(query).executeUpdate();

        } catch (Exception e) {
            System.out.println("InventoryBasicModel deleteRecord() Error: " + e);
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

    public int getItemNamesId(String item_code) {
        String query = "SELECT item_names_id FROM item_names WHERE item_code = '" + item_code + "' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("item_names_id");
        } catch (Exception e) {
            System.out.println("getItemNamesId Error: " + e);
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

    public int getModelId(String model_name) {
        String query = "SELECT model_id FROM model WHERE model = '" + model_name + "' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("model_id");
        } catch (Exception e) {
            System.out.println("getModelId Error: " + e);
        }
        return id;
    }

    public String getItemName(int item_name_id) {
        String query = "SELECT item_name FROM item_names WHERE item_names_id = ? and active='Y' ";
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

    public List<String> getItemName(String q, String manufacturer) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT itn.item_name FROM item_names itn,manufacturer mr,manufacturer_item_map mim where"
                + " mr.manufacturer_id=mim.manufacturer_id and itn.item_names_id=mim.item_names_id and itn.active='Y' "
                + " and mr.active='Y' and mim.active='Y' and itn.is_super_child='Y'  ";

        if (!manufacturer.equals("") && manufacturer != null) {
            query += " and mr.manufacturer_name='" + manufacturer + "' ";
        }
        query += " group by itn.item_name ORDER BY itn.item_name ";
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
                list.add("No such item_name  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:InventoryBasicModel--getItemName()-- " + e);
        }
        return list;
    }

    public List<String> getItemCode(String q, String manufacturer) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT concat(itn.item_name,' - ',itn.item_code) as item_code FROM item_names itn,manufacturer mr,manufacturer_item_map mim where"
                + " mr.manufacturer_id=mim.manufacturer_id and itn.item_names_id=mim.item_names_id and itn.active='Y' "
                + " and mr.active='Y' and mim.active='Y' and itn.is_super_child='Y' ";

//        String query = "SELECT concat(itn.item_name,' - ',itn.item_code) as item_code FROM item_names itn "
//                + " where itn.active='Y' ";
        if (!manufacturer.equals("") && manufacturer != null) {
            query += " and mr.manufacturer_name='" + manufacturer + "' ";
        }
        query += " group by itn.item_code ORDER BY itn.item_code ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String item_code = (rset.getString("item_code"));
                if (item_code.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(item_code);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such item_code  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:InventoryBasicModel--getItemCode()-- " + e);
        }
        return list;
    }

//    public List<String> getItemCode(String q, String org_office) {
//        List<String> list = new ArrayList<String>();
//        String query = "";
//        String query2 = " select itn.item_names_id from item_names itn,org_office oo,inventory_basic ib where"
//                + "  ib.org_office_id=oo.org_office_id"
//                + "  and itn.item_names_id=ib.item_names_id and oo.active='Y' and ib.active='Y' and itn.active='Y' ";
//        if (!org_office.equals("") && org_office != null) {
//            query2 += " and oo.org_office_name='" + org_office + "' ";
//        }
//
//        try {
//            ResultSet rset2 = connection.prepareStatement(query2).executeQuery();
//            List<Integer> list2 = new ArrayList<Integer>();
//            while (rset2.next()) {
//                int item_names_id = rset2.getInt("item_names_id");
//                list2.add(item_names_id);
//            }
//
//            query = " SELECT inn.item_code FROM item_names inn "
//                    + " where inn.active='Y' and inn.is_super_child='Y' ";
//            if (list2.size() > 0) {
//                query += " and inn.item_names_id  not in(" + list2.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ") ";
//            }
//
//            query += " ORDER BY inn.item_code  ";
//
//            ResultSet rset = connection.prepareStatement(query).executeQuery();
//            int count = 0;
//            q = q.trim();
//            while (rset.next()) {    // move cursor from BOR to valid record.
//                String item_code = (rset.getString("item_code"));
//                if (item_code.toUpperCase().startsWith(q.toUpperCase())) {
//                    list.add(item_code);
//                    count++;
//                }
//            }
//            if (count == 0) {
//                list.add("No such item_code exists.");
//            }
//        } catch (Exception e) {
//            System.out.println("ModelNameModel getItem ERROR - " + e);
//        }
//        return list;
//    }
    public List<String> getOrgOffice(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT oo.org_office_name FROM org_office oo where"
                + " oo.active='Y' ";

        query += " group by oo.org_office_name ORDER BY oo.org_office_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String org_office_name = (rset.getString("org_office_name"));
                if (org_office_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(org_office_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such org_office_name  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:InventoryBasicModel--getOrgOffice()-- " + e);
        }
        return list;
    }

    public List<String> getManufacturer(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT manufacturer_name FROM manufacturer where"
                + " active='Y' ";

        query += " group by manufacturer_name ORDER BY manufacturer_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String manufacturer_name = (rset.getString("manufacturer_name"));
                if (manufacturer_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(manufacturer_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such manufacturer_name  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:InventoryBasicModel--getManufacturer()-- " + e);
        }
        return list;
    }

    public List<String> getModelName(String q, String manufacturer_name, String item_code) {
        List<String> list = new ArrayList<String>();
        String item_name = "";
        if (!item_code.equals("")) {
            String item_code_arr[] = item_code.split(" - ");
            item_name = item_code_arr[0];
            item_code = item_code_arr[1];
        }

        String query = " select m.model from manufacturer_item_map mim,model m,manufacturer mr,item_names itn "
                + " where mim.manufacturer_item_map_id=m.manufacturer_item_map_id and "
                + "mr.manufacturer_id=mim.manufacturer_id and mim.item_names_id=itn.item_names_id and mim.active='Y' and mr.active='Y' "
                + " and m.active='Y' and itn.active='Y' ";

        if (!manufacturer_name.equals("") && manufacturer_name != null) {
            query += " and mr.manufacturer_name='" + manufacturer_name + "' ";
        }

        if (!item_name.equals("") && item_name != null) {
            query += " and itn.item_name='" + item_name + "' ";
        }
        if (!item_code.equals("") && item_code != null) {
            query += " and itn.item_code='" + item_code + "' ";
        }

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String model = (rset.getString("model"));
                if (model.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(model);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such model  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:InventoryBasicModel--getModelName()-- " + e);
        }
        return list;
    }

    public List<String> getKeyPerson(String q, String org_office) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT kp.key_person_name FROM key_person kp,org_office oo where"
                + " kp.org_office_id=oo.org_office_id and kp.active='Y' and oo.active='Y' ";

        if (!org_office.equals("") && org_office != null) {
            query += " and oo.org_office_name='" + org_office + "' ";
        }

        query += " group by kp.key_person_name ORDER BY kp.key_person_name ";
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
            System.out.println("Error:InventoryModel--getKeyPerson()-- " + e);
        }
        return list;
    }

    public List<String> getLeadTime(String model_name) {
        List<String> list = new ArrayList<String>();

        String query = " select lead_time from model where active='Y' ";

        if (!model_name.equals("") && model_name != null) {
            query += " and model='" + model_name + "' ";
        }

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {
                String lead_time = (rset.getString("lead_time"));

                list.add(lead_time);
                count++;
            }
            if (count == 0) {
                list.add("No such model  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:InventoryBasicModel--getModelName()-- " + e);
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
            System.out.println("ItemNameModel closeConnection() Error: " + e);
        }
    }
}