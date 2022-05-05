package com.report.model;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;
import com.DBConnection.DBConnection;
import com.inventory.model.ItemNameModel;
import com.inventory.tableClasses.ItemName;
import com.report.bean.InventoryReport;
import java.io.File;
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
public class InventoryReportModel {

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
            System.out.println("InventoryReportModel setConnection() Error: " + e);
        }
    }

    public List<Integer> getIdList(String searchItemName, String searchOrgOffice, String search_manufacturer, String search_item_code,
            String search_model, String searchKeyPerson, String search_by_date) throws SQLException {
        List<Integer> list = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        try {
            String query = " select itn.item_names_id "
                    + " from item_names itn, item_type itt,manufacturer_item_map mim,model m,inventory_basic ib,inventory inv, "
                    + " key_person kp,org_office oo,manufacturer mr "
                    + " where itt.item_type_id=itn.item_type_id and mim.item_names_id=itn.item_names_id "
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
            if (!search_by_date.equals("") && search_by_date != null) {
                query += " and inv.date_time='" + search_by_date + "' ";
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
            System.out.println("com.inventory.model.InventoryReportModel.getIdList() -" + e);
        }
        return list;
    }

    public int getParent_Item_id(String item_name) {
        String query = " SELECT item_names_id FROM item_names WHERE item_name = '" + item_name + "' and active='Y' ";
        int p_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            p_id = rset.getInt("item_names_id");
        } catch (Exception e) {
            System.out.println("InventoryReportModel Error: getParent_Item_id--" + e);
        }
        return p_id;
    }

    public List<InventoryReport> showData(String searchItemName, String searchOrgOffice, String search_manufacturer, String search_item_code,
            String search_model, String searchKeyPerson, String search_by_date) {
        List<InventoryReport> list = new ArrayList<InventoryReport>();
        List<Integer> desig_map_list = new ArrayList<>();
        List<Integer> desig_map_listAll = new ArrayList<>();
        List<Integer> desig_map_listAllFinal = new ArrayList<>();
        List<Integer> desig_map_listUnmatched = new ArrayList<>();
        List<String> manufacturer_name_list = new ArrayList<>();
        List<String> parent_id_list = new ArrayList<>();

        String search_item_name = "";
        String search_item_type = "";
        String search_item_codee = "";
        String search_super_child = "";
        String search_generation = "";
        int p_item_id = 0;

        try {
            desig_map_list = getIdList(searchItemName, searchOrgOffice, search_manufacturer, search_item_code, search_model, searchKeyPerson, search_by_date);
            ItemNameModel model = new ItemNameModel();
            List<ItemName> allIdList = model.showData(search_item_name, search_item_type, search_item_codee, search_super_child, search_generation);
            for (int k = 0; k < allIdList.size(); k++) {
                desig_map_listAll.add(allIdList.get(k).getItem_names_id());
            }
//            if (!searchItemName.equals("")) {
//                p_item_id = getParent_Item_id(searchItemName);
//                desig_map_listAllFinal = getAllParentChildList(searchItemName);
//            }

            desig_map_listAllFinal.addAll(desig_map_listAll);
            desig_map_listAll.removeAll(desig_map_list);
            desig_map_listUnmatched.addAll(desig_map_listAll);
            desig_map_listAllFinal.removeAll(desig_map_listUnmatched);

            String query4 = " select max(item_names_id) as item_names_id from item_names ";
            PreparedStatement pstmt1 = connection.prepareStatement(query4);
            ResultSet rset1 = pstmt1.executeQuery();
            while (rset1.next()) {
                item_id1 = rset1.getInt("item_names_id");
            }

            String query = "  select itn.item_names_id,itn.item_name,itn.description,itn.item_code,itt.item_type,itn.quantity,itn.parent_id, "
                    + " itn.generation,itn.is_super_child,itn.prefix "
                    + " from item_names itn, item_type itt where itt.item_type_id=itn.item_type_id and itn.active='Y' and itt.active='y' ";
            query += "  and itn.item_names_id in(" + desig_map_listAllFinal.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ") "
                    + " order by field(itn.item_names_id," + desig_map_listAllFinal.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ") ";
//                    + "  limit 10  ";

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
                InventoryReport bean = new InventoryReport();

                bean.setItem_name((rset.getString("item_name")));
                bean.setItem_names_id((rset.getInt("item_names_id")));
                bean.setParent_item_id((rset.getString("parent_id")));
                bean.setGeneration((rset.getInt("generation")));
                bean.setItem_code((rset.getString("item_code")));
                String is_super_child = rset.getString("is_super_child");
                bean.setModel_no("");
                bean.setPart_no("");
                bean.setStock_quantity(0);
                bean.setInward_quantity(0);
                bean.setOutward_quantity(0);
                bean.setReference_document_type("");
                bean.setReference_document_id("");
                bean.setDate_time("");
                bean.setOrg_office("");
                bean.setKey_person("");
                list.add(bean);

                String item_name = "";
                if (is_super_child.equals("Y")) {

                    int item_id = rset.getInt("item_names_id");
                    String query2 = " select mr.manufacturer_name  "
                            + " from item_names itn, item_type itt,manufacturer_item_map mim,model m,inventory_basic ib,inventory inv,key_person kp, "
                            + " org_office oo,manufacturer mr "
                            + " where itt.item_type_id=itn.item_type_id and itn.active='Y' and itt.active='y' and mim.item_names_id=itn.item_names_id "
                            + " and mim.manufacturer_item_map_id=m.manufacturer_item_map_id "
                            + " and ib.model_id=m.model_id and ib.inventory_basic_id=inv.inventory_basic_id and kp.key_person_id=inv.key_person_id "
                            + " and oo.org_office_id=ib.org_office_id and mim.active='Y' "
                            + " and m.active='Y' and ib.active='Y' and inv.active='Y' and kp.active='Y' and oo.active='Y' "
                            + " and ib.item_names_id=itn.item_names_id and mr.manufacturer_id=mim.manufacturer_id and mr.active='Y' and "
                            + " itn.item_names_id='" + item_id + "'  ";

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
                    if (!search_by_date.equals("") && search_by_date != null) {
                        query2 += " and inv.date_time='" + search_by_date + "' ";
                    }
                    query2 += " group by mr.manufacturer_name order by mr.manufacturer_name  ";

                    PreparedStatement pstmt2 = connection.prepareStatement(query2);
                    ResultSet rset2 = pstmt2.executeQuery();
                    int i = 0;
                    manufacturer_name_list.clear();
                    parent_id_list.clear();
                    while (rset2.next()) {
                        item_id1 += 1;
                        InventoryReport bean2 = new InventoryReport();
                        bean2.setItem_name(rset2.getString("manufacturer_name"));
                        bean2.setItem_names_id(item_id1);
                        bean2.setGeneration(rset.getInt("generation") + 1);
                        bean2.setParent_item_id(rset.getString("item_names_id"));
                        bean2.setItem_code("");
                        bean2.setModel_no("");
                        bean2.setPart_no("");
                        bean2.setStock_quantity(0);
                        bean2.setInward_quantity(0);
                        bean2.setOutward_quantity(0);
                        bean2.setReference_document_type("");
                        bean2.setReference_document_id("");
                        bean2.setDate_time("");
                        bean2.setOrg_office("");
                        bean2.setKey_person("");
                        bean2.setCounting("Manufacturer");
                        bean2.setColor("#FF57EC");
                        i++;
                        list.add(bean2);
                        manufacturer_name_list.add(rset2.getString("manufacturer_name"));
                        parent_id_list.add(String.valueOf(item_id1));
                    }

                    for (int j = 0; j < manufacturer_name_list.size(); j++) {
                        String query3 = " select m.model,m.model_no,m.part_no,inv.stock_quantity,inv.inward_quantity,inv.outward_quantity, "
                                + " inv.reference_document_type,inv.reference_document_id,inv.date_time,oo.org_office_name,kp.key_person_name "
                                + " from model m,manufacturer_item_map mim,manufacturer mr,item_names itn,inventory inv,inventory_basic ib, "
                                + " key_person kp,"
                                + " org_office oo "
                                + " where ib.inventory_basic_id=inv.inventory_basic_id and "
                                + " ib.model_id=m.model_id and ib.item_names_id=itn.item_names_id and ib.active='Y' and inv.active='Y' "
                                + " and mim.manufacturer_item_map_id=m.manufacturer_item_map_id and kp.key_person_id=inv.key_person_id "
                                + " and oo.org_office_id=ib.org_office_id "
                                + " and mr.manufacturer_id=mim.manufacturer_id  and mr.active='Y' and itn.active='Y' "
                                + " and itn.item_names_id=mim.item_names_id  and m.active='Y' and mim.active='Y' and kp.active='Y' and oo.active='Y' "
                                + " and mr.manufacturer_name='" + manufacturer_name_list.get(j) + "' "
                                + " and itn.item_names_id='" + rset.getString("item_names_id") + "' ";
                        if (!search_model.equals("") && search_model != null) {
                            query3 += " and m.model='" + search_model + "' ";
                        }
                        if (!searchKeyPerson.equals("") && searchKeyPerson != null) {
                            query3 += " and kp.key_person_name='" + searchKeyPerson + "' ";
                        }
                        if (!search_by_date.equals("") && search_by_date != null) {
                            query3 += " and inv.date_time='" + search_by_date + "' ";
                        }
                        // query3 += " group by m.model ";

                        PreparedStatement pstmt3 = connection.prepareStatement(query3);
                        ResultSet rset3 = pstmt3.executeQuery();
                        while (rset3.next()) {
                            item_id1 += 1;
                            InventoryReport bean3 = new InventoryReport();
                            bean3.setItem_name(rset3.getString("model"));
                            bean3.setItem_names_id(item_id1);
                            bean3.setGeneration(rset.getInt("generation") + 1 + 1);
                            bean3.setParent_item_id(parent_id_list.get(j));
                            bean3.setItem_code("");
                            bean3.setModel_no(rset3.getString("model_no"));
                            bean3.setPart_no(rset3.getString("part_no"));
                            bean3.setStock_quantity(rset3.getInt("stock_quantity"));
                            bean3.setInward_quantity(rset3.getInt("inward_quantity"));
                            bean3.setOutward_quantity(rset3.getInt("outward_quantity"));
                            bean3.setReference_document_type(rset3.getString("reference_document_type"));
                            bean3.setReference_document_id(rset3.getString("reference_document_id"));
                            bean3.setDate_time(rset3.getString("date_time"));
                            bean3.setOrg_office(rset3.getString("org_office_name"));
                            bean3.setKey_person(rset3.getString("key_person_name"));
                            bean3.setCounting("Model");
                            bean3.setColor("#FF5221");

                            list.add(bean3);
                        }
                    }

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
                // list.add(bean);

            }
        } catch (Exception e) {
            System.err.println("InventoryReportModel Exception in showData---------" + e);
        }

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

        String qry = " select item_names_id from item_names where active='Y' and item_name='" + searchItemName + "' ";
        try {
            PreparedStatement pst = connection.prepareStatement(qry);
            ResultSet rstt = pst.executeQuery();
            while (rstt.next()) {
                item_names_id = rstt.getInt(1);
                list.add(item_names_id);
            }
        } catch (Exception e) {
            System.out.println("InventoryReportModel.getAllParentChildList() -" + e);
        }

        String qry1 = " select item_names_id from item_names where active='Y' and parent_id='" + item_names_id + "' limit 1 ";
        try {
            PreparedStatement pst = connection.prepareStatement(qry1);
            ResultSet rstt = pst.executeQuery();
            while (rstt.next()) {
                parent_id = rstt.getInt(1);
                list.add(parent_id);
            }
        } catch (Exception e) {
            System.out.println("InventoryReportModel.getAllParentChildList() -" + e);
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
            System.out.println("Error:--InventoryReportModel--- getAllParentChildList--" + e);
        }
        String qry2 = "select item_names_id from item_names where active='Y' and parent_id='" + item_names_id + "' ";
        try {
            PreparedStatement pst = connection.prepareStatement(qry2);
            ResultSet rstt = pst.executeQuery();
            while (rstt.next()) {
                list.add(rstt.getInt(1));
            }
        } catch (Exception e) {
            System.out.println("InventoryReportModel.getAllParentChildList() -" + e);
        }

        list.removeAll(Arrays.asList(0));

        return list;
    }

    public String getItemName(int item_name_id) {
        String query = " SELECT item_name FROM item_names WHERE item_names_id = ? and active='Y' ";
        String name = "";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, item_name_id);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            name = rset.getString("item_name");
        } catch (Exception e) {
            System.out.println("InventoryReportModel getItemName Error: " + e);
        }
        return name;
    }

    public List<String> getItemName(String q, String manufacturer) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT itn.item_name FROM item_names itn,manufacturer mr,manufacturer_item_map mim where "
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
            System.out.println("Error:InventoryReportModel--getItemName()-- " + e);
        }
        return list;
    }

    public List<String> getItemCode(String q, String manufacturer) {
        List<String> list = new ArrayList<String>();
        String query = "";
        //  if (!manufacturer.equals("") && manufacturer != null) {
        query = " SELECT concat(itn.item_name,' - ',itn.item_code) as item_code FROM item_names itn,manufacturer mr,manufacturer_item_map mim where "
                + " mr.manufacturer_id=mim.manufacturer_id and itn.item_names_id=mim.item_names_id and itn.active='Y' "
                + " and mr.active='Y' and mim.active='Y' and itn.is_super_child='Y' ";
        if (!manufacturer.equals("") && manufacturer != null) {
            query += " and mr.manufacturer_name='" + manufacturer + "' ";
        }
        query += " group by itn.item_code ORDER BY itn.item_code ";
//        } else {
//            query = " select  concat(itn.item_name,' - ',itn.item_code) as item_code from item_names itn where itn.active='Y' ";
//        }
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
            System.out.println("Error:InventoryReportModel--getItemCode()-- " + e);
        }
        return list;
    }

    public List<String> getOrgOffice(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT oo.org_office_name FROM org_office oo where "
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
            System.out.println("Error:InventoryReportModel--getOrgOffice()-- " + e);
        }
        return list;
    }

    public List<String> getManufacturer(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT manufacturer_name FROM manufacturer where "
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
            System.out.println("Error:InventoryReportModel--getManufacturer()-- " + e);
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
                + " mr.manufacturer_id=mim.manufacturer_id and mim.item_names_id=itn.item_names_id and mim.active='Y' and mr.active='Y' "
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
            System.out.println("Error:InventoryReportModel--getModelName()-- " + e);
        }
        return list;
    }

    public List<String> getKeyPerson(String q, String org_office) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT kp.key_person_name FROM key_person kp,org_office oo where "
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
            System.out.println("Error:InventoryReportModel--getKeyPerson()-- " + e);
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
            System.out.println("Error:InventoryReportModel--getLeadTime()-- " + e);
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
            System.out.println("InventoryReportModel closeConnection() Error: " + e);
        }
    }
}
