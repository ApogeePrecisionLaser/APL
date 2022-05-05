package com.inventory.model;

import com.inventory.model.ItemNameModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.DBConnection.DBConnection;
import com.inventory.tableClasses.Inventory;
import com.inventory.tableClasses.ItemName;
import java.sql.SQLException;
import java.util.Collections;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;

/**
 *
 * @author Komal
 */
public class InventoryModel {

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
            System.out.println("InventoryModel setConnection() Error: " + e);
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
            System.out.println("com.inventory.model.InventoryModel.getIdList() -" + e);
        }
        return list;
    }

    public List<Inventory> showData(String searchItemName, String searchOrgOffice, String search_manufacturer, String search_item_code,
            String search_model, String searchKeyPerson, String search_by_date, String user_role) {
        List<Inventory> list = new ArrayList<Inventory>();
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

        try {

            desig_map_list = getIdList(searchItemName, searchOrgOffice, search_manufacturer, search_item_code, search_model, searchKeyPerson, search_by_date);

            //  System.err.println("desig list --" + desig_map_list.size());
            //  System.err.println("desig list ele--" + desig_map_list);
            ItemNameModel model = new ItemNameModel();
            List<ItemName> allIdList = model.showData(search_item_name, search_item_type, search_item_codee, search_super_child, search_generation);
            //  System.err.println("all item id --" + allIdList);
            for (int k = 0; k < allIdList.size(); k++) {
                desig_map_listAll.add(allIdList.get(k).getItem_names_id());
            }
            // System.err.println("id lissst  -----" + desig_map_listAll.size());
            //  System.err.println("id lissst  -----" + desig_map_listAll);

            desig_map_listAllFinal.addAll(desig_map_listAll);
            desig_map_listAll.removeAll(desig_map_list);
            desig_map_listUnmatched.addAll(desig_map_listAll);

            desig_map_listAllFinal.removeAll(desig_map_listUnmatched);

            //   System.err.println("final list --" + desig_map_listUnmatched.size());
            //  System.err.println("final string --- " + desig_map_listUnmatched.toString());
            String query = "  select itn.item_names_id,itn.item_name,itn.description,itn.item_code,itt.item_type,itn.quantity,itn.parent_id, "
                    + " itn.generation,itn.is_super_child,itn.prefix "
                    + " from item_names itn, item_type itt where itt.item_type_id=itn.item_type_id and itn.active='Y' and itt.active='y' ";

//            query += "  and itn.item_names_id in(" + desig_map_list.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ") "
//                    + " order by field(itn.item_names_id," + desig_map_list.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ")  ";
            query += "  and itn.item_names_id in(" + desig_map_listAllFinal.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ") "
                    + " order by field(itn.item_names_id," + desig_map_listAllFinal.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ")  ";

            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                Inventory bean = new Inventory();

                bean.setItem_name((rset.getString("item_name")));
                bean.setItem_names_id((rset.getInt("item_names_id")));
                bean.setParent_item_id((rset.getString("parent_id")));
                bean.setGeneration((rset.getInt("generation")));
                bean.setItem_code((rset.getString("item_code")));
                String is_super_child = rset.getString("is_super_child");
                bean.setStock_quantity(0);
                bean.setInward_quantity(0);
                bean.setOutward_quantity(0);
                bean.setModel_no("");
                bean.setPart_no("");
                bean.setMin_quantity(0);
                bean.setDate_time("");
                bean.setOrg_office("");
                bean.setKey_person("");
                bean.setManufacturer_name("");
                bean.setReference_document_type("");
                bean.setReference_document_id("");
                bean.setIs_super_child(is_super_child);
                bean.setPopupval("No");
                bean.setUser_role("");
                list.add(bean);

                String item_name = "";
                if (is_super_child.equals("Y")) {
                    int item_id = rset.getInt("item_names_id");
                    String query2 = " select mr.manufacturer_name  "
                            + " from item_names itn, item_type itt,manufacturer_item_map mim,model m,inventory_basic ib,inventory inv,key_person kp,"
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
                        Inventory bean2 = new Inventory();
                        bean2.setInventory_id(0);
                        bean2.setInventory_basic_id(0);
                        bean2.setOrg_office("");
                        bean2.setKey_person("");
                        bean2.setStock_quantity(0);
                        bean2.setInward_quantity(0);
                        bean2.setOutward_quantity(0);
                        bean2.setModel_no("");
                        bean2.setPart_no("");
                        bean2.setDate_time("");
                        bean2.setDescription("");
                        bean2.setManufacturer_name("");
                        bean2.setItem_code("");
                        bean2.setMin_quantity(0);
                        bean2.setItem_name(rset2.getString("manufacturer_name"));
                        bean2.setItem_names_id(item_id1);
                        bean2.setGeneration(rset.getInt("generation") + 1);
                        bean2.setParent_item_id(rset.getString("item_names_id"));
                        bean2.setReference_document_type("");
                        bean2.setReference_document_id("");
                        bean2.setPopupval("No");
                        bean2.setUser_role("");

                        i++;
                        list.add(bean2);
                        manufacturer_name_list.add(rset2.getString("manufacturer_name"));
                        parent_id_list.add(String.valueOf(item_id1));
                    }

                    for (int j = 0; j < manufacturer_name_list.size(); j++) {
                        String query3 = " select itn.item_names_id,itn.item_name,itn.description,itn.item_code,itn.quantity,itn.parent_id, "
                                + " itn.generation,itn.is_super_child,itn.prefix,inv.inventory_id,ib.inventory_basic_id,oo.org_office_name,kp.key_person_name, "
                                + " inv.inward_quantity,inv.outward_quantity,inv.stock_quantity,inv.date_time,inv.reference_document_type, "
                                + " inv.reference_document_id,inv.description,m.model,mr.manufacturer_name,m.model_no,m.part_no,ib.daily_req, "
                                + " ib.opening_balance "
                                + " ,ib.min_quantity,inv.key_person_id "
                                + " from model m,manufacturer_item_map mim,manufacturer mr,item_names itn,inventory inv,inventory_basic ib, "
                                + " key_person kp, "
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
                        if (!searchOrgOffice.equals("") && searchOrgOffice != null) {
                            query3 += " and oo.org_office_name='" + searchOrgOffice + "' ";
                        }
                        if (search_by_date.equals("") && search_by_date == null) {
                            query3 += " and kp.designation_id=5  ";
                        }
                        if (!search_by_date.equals("") && search_by_date != null) {
                            query3 += " and inv.date_time='" + search_by_date + "' ";
                        }

                        // query3 += " group by m.model ";
                        PreparedStatement pstmt3 = connection.prepareStatement(query3);
                        ResultSet rset3 = pstmt3.executeQuery();
                        while (rset3.next()) {
                            item_id1 += 1;
                            Inventory bean3 = new Inventory();
                            bean3.setInventory_id(rset3.getInt("inventory_id"));
                            bean3.setInventory_basic_id(rset3.getInt("inventory_basic_id"));
                            bean3.setOrg_office(rset3.getString("org_office_name"));
                            bean3.setKey_person(rset3.getString("key_person_name"));
                            int stock_quantity = (rset3.getInt("stock_quantity"));
                            bean3.setStock_quantity(stock_quantity);
                            bean3.setInward_quantity(rset3.getInt("inward_quantity"));
                            bean3.setOutward_quantity(rset3.getInt("outward_quantity"));
                            bean3.setModel_no(rset3.getString("model_no"));
                            bean3.setPart_no(rset3.getString("part_no"));
                            bean3.setDate_time(rset3.getString("date_time"));
                            bean3.setDescription(rset3.getString("description"));
                            bean3.setReference_document_type(rset3.getString("reference_document_type"));
                            bean3.setReference_document_id(rset3.getString("reference_document_id"));
                            bean3.setManufacturer_name(manufacturer_name_list.get(j));
                            bean3.setMin_quantity(rset3.getInt("min_quantity"));
                            bean3.setManufacturer_name(rset3.getString("item_name"));
                            bean3.setItem_code(rset.getString("item_name") + " - " + rset.getString("item_code"));
                            bean3.setModel(rset3.getString("model"));
                            bean3.setItem_name(rset3.getString("model"));
                            bean3.setItem_names_id(item_id1);
                            bean3.setGeneration(rset.getInt("generation") + 1 + 1);
                            bean3.setParent_item_id(parent_id_list.get(j));
                            bean3.setPopupval("Yes");

                            user_role = getUserRole(rset3.getString("key_person_id"));
                            bean3.setUser_role(user_role);

                            list.add(bean3);
                        }
                    }

                }

                //  list.add(bean);
            }
        } catch (Exception e) {
            System.err.println("InventoryModel Exception in getItemsList---------" + e);
        }

        return list;
    }

    public List<Inventory> getAllDetails(int item_names_id, int logged_key_person_id, String user_role) {
        List<Inventory> list = new ArrayList<Inventory>();
        try {
            String query = " select itn.item_names_id,itn.item_name,itn.description,itn.item_code,itt.item_type,itn.quantity,itn.parent_id, "
                    + " itn.generation,itn.is_super_child,itn.prefix,inv.inventory_id,ib.inventory_basic_id,oo.org_office_name,kp.key_person_name, "
                    + " inv.inward_quantity,inv.outward_quantity,inv.stock_quantity,inv.date_time,inv.reference_document_type, "
                    + " inv.reference_document_id,inv.description,m.model,mr.manufacturer_name,m.model_no,m.part_no "
                    + " from item_names itn, item_type itt,manufacturer_item_map mim,model m,inventory_basic ib,inventory inv,key_person kp, "
                    + " org_office oo,manufacturer mr "
                    + " where itt.item_type_id=itn.item_type_id and itn.active='Y' and itt.active='y' and mim.item_names_id=itn.item_names_id "
                    + " and mim.manufacturer_item_map_id=m.manufacturer_item_map_id "
                    + " and ib.model_id=m.model_id and ib.inventory_basic_id=inv.inventory_basic_id and kp.key_person_id=inv.key_person_id "
                    + " and oo.org_office_id=ib.org_office_id and mim.active='Y' "
                    + " and m.active='Y' and ib.active='Y' and inv.active='Y' and kp.active='Y' and oo.active='Y' "
                    + " and ib.item_names_id=itn.item_names_id and mr.manufacturer_id=mim.manufacturer_id and mr.active='Y' and "
                    + " itn.item_names_id='" + item_names_id + "' ";
            if (!user_role.equals("Super Admin")) {
                query += " and kp.key_person_id ='" + logged_key_person_id + "'  ";
            }

            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                Inventory bean = new Inventory();
                bean.setItem_name((rset.getString("item_name")));
                bean.setItem_code((rset.getString("item_code")));
                bean.setInventory_id(rset.getInt("inventory_id"));
                bean.setInventory_basic_id(rset.getInt("inventory_basic_id"));
                bean.setOrg_office(rset.getString("org_office_name"));
                bean.setKey_person(rset.getString("key_person_name"));
                bean.setInward_quantity(rset.getInt("inward_quantity"));
                bean.setOutward_quantity(rset.getInt("outward_quantity"));
                int stock_quantity = (rset.getInt("stock_quantity"));
                bean.setStock_quantity(stock_quantity);
                bean.setDate_time(rset.getString("date_time"));
                bean.setDescription(rset.getString("description"));
                bean.setManufacturer_name(rset.getString("manufacturer_name"));
                bean.setModel(rset.getString("model"));
                bean.setModel_no(rset.getString("model_no"));
                bean.setPart_no(rset.getString("part_no"));
                bean.setReference_document_type(rset.getString("reference_document_type"));
                bean.setReference_document_id(rset.getString("reference_document_id"));
                //  bean.setDelivery_challan_img(rset.getString("delivery_challan_img"));

                list.add(bean);

            }
        } catch (Exception e) {
            System.err.println("InventoryModel Exception in getAllDetails---------" + e);
        }

        return list;

    }

    public String getUserRole(String key_person_id) {
        String str = "";
        PreparedStatement pstmt;
        ResultSet rst;
        String query = " select ur.role_name from user u,user_roles ur,user_role_map urm where u.active='Y' and ur.active='Y' and urm.active='Y' "
                + " and u.user_id=urm.user_id and urm.user_role_id=ur.user_role_id ";
        if (!key_person_id.equals("") && key_person_id != null) {
            query += " and u.key_person_id='" + key_person_id + "' ";
        }
        try {
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(query);
            rst = pstmt.executeQuery();
            while (rst.next()) {
                str = rst.getString(1);
            }

        } catch (Exception e) {
            System.out.println("getUserRole ERROR inside LoginModel - " + e);
        }
        return str;
    }

    public String getImagePath(String inventory_id, String uploadedFor) {
        String img_name = "";
        String destination_path = "";
        String reference_document_type = "";

        String query = "";

        try {
            String query2 = " select reference_document_type from inventory where inventory_id='" + inventory_id + "' and active='Y' ";

            ResultSet rs2 = connection.prepareStatement(query2).executeQuery();
            while (rs2.next()) {
                reference_document_type = rs2.getString("reference_document_type");
            }
            if (!reference_document_type.equals("")) {
                if (reference_document_type.equals("Indent")) {
                    query = " SELECT dc.description as delivery_challan_img "
                            + " FROM delivery_challan dc, inventory inv, indent_table indt "
                            + " WHERE dc.indent_table_id=indt.indent_table_id AND inv.reference_document_id=indt.indent_no "
                            + " and inv.inventory_id='" + inventory_id + "' "
                            + " and dc.active='Y' and indt.active='Y' and inv.active='Y' and dc.description!='' ";
                } else {
                    query = " SELECT dc.description as delivery_challan_img "
                            + " FROM order_delivery_challan dc, inventory inv, order_table odt "
                            + " WHERE dc.order_table_id=odt.order_table_id AND inv.reference_document_id=odt.order_no "
                            + " and inv.inventory_id='" + inventory_id + "' "
                            + " and dc.active='Y' and odt.active='Y' and inv.active='Y' and dc.description!='' ";
                }
            }
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                destination_path = rs.getString("delivery_challan_img");
            }
        } catch (Exception ex) {
            System.out.println("ERROR: in getImagePath in TrafficPoliceSearchModel : " + ex);
        }
        return destination_path;
    }

    public int insertRecord(Inventory bean) throws SQLException {
        String query = " INSERT INTO inventory(inventory_basic_id,key_person_id,description, "
                + " revision_no,active,remark,inward_quantity,outward_quantity,date_time,reference_document_type,reference_document_id,stock_quantity) "
                + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?) ";

        int rowsAffected = 0;
        String item_code = bean.getItem_code();
        if (!item_code.equals("")) {
            String item_code_arr[] = item_code.split(" - ");
            item_code = item_code_arr[1];
        }

        int item_name_id = getItemNamesId(item_code);
        int org_office_id = getOrgOfficeId(bean.getOrg_office());
        int inventory_basic_id = getInventoryBasicId(org_office_id, item_name_id);
        int key_person_id = getKeyPersonId(bean.getKey_person());
        int stock_quantity = getStockQuantity(item_name_id);
        int map_count = 0;
        try {
            String query4 = " SELECT count(*) as count FROM inventory WHERE "
                    + " inventory_basic_id='" + inventory_basic_id + "' and key_person_id='" + key_person_id + "' "
                    + " and active='Y'  ";

            PreparedStatement pstmt1 = connection.prepareStatement(query4);
            ResultSet rs1 = pstmt1.executeQuery();
            while (rs1.next()) {
                map_count = rs1.getInt("count");
            }
            if (map_count > 0) {
                message = "Item has already assigned to this person!..";
                msgBgColor = COLOR_ERROR;
            } else {
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setInt(1, inventory_basic_id);
                pstmt.setInt(2, key_person_id);
                pstmt.setString(3, bean.getDescription());
                pstmt.setInt(4, bean.getRevision_no());
                pstmt.setString(5, "Y");
                pstmt.setString(6, "OK");
                pstmt.setInt(7, stock_quantity);
                pstmt.setInt(8, 0);
                pstmt.setString(9, bean.getDate_time());
                pstmt.setString(10, bean.getReference_document_type());
                pstmt.setString(11, bean.getReference_document_id());
                pstmt.setInt(12, stock_quantity);
                rowsAffected = pstmt.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("InventoryModel insertRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        if (map_count > 0) {
            message = "Item has already assigned to this person!..";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int updateRecord(Inventory bean, int inventory_id) {
        int revision = InventoryModel.getRevisionno(bean, inventory_id);
        int updateRowsAffected = 0;
        String item_code = bean.getItem_code();
        if (!item_code.equals("")) {
            String item_code_arr[] = item_code.split(" - ");
            item_code = item_code_arr[1];
        }

        int item_name_id = getItemNamesId(item_code);
        int org_office_id = getOrgOfficeId(bean.getOrg_office());
        int inventory_basic_id = getInventoryBasicId(org_office_id, item_name_id);
        int key_person_id = getKeyPersonId(bean.getKey_person());
        int stock_quantity = getStockQuantity(item_name_id);

        String query1 = " SELECT max(revision_no) revision_no FROM inventory WHERE inventory_id = " + inventory_id + "  and active='Y' ";
        String query2 = " UPDATE inventory SET active=? WHERE inventory_id=? and revision_no=? ";
        String query3 = " INSERT INTO inventory(inventory_id,inventory_basic_id,key_person_id,description, "
                + " revision_no,active,remark,inward_quantity,outward_quantity,date_time,reference_document_type,reference_document_id,stock_quantity) "
                + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?) ";

        int rowsAffected = 0;
        int map_count = 0;
        try {
            String query4 = " SELECT count(*) as count FROM inventory WHERE "
                    + " inventory_basic_id='" + inventory_basic_id + "' and key_person_id='" + key_person_id + "' "
                    + " and active='Y'  ";

            PreparedStatement pstmt1 = connection.prepareStatement(query4);
            ResultSet rs1 = pstmt1.executeQuery();
            while (rs1.next()) {
                map_count = rs1.getInt("count");
            }
            if (map_count > 0) {
                message = "Item has already assigned to this person!..";
                msgBgColor = COLOR_ERROR;
            } else {
                PreparedStatement pstmt = connection.prepareStatement(query1);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    PreparedStatement pstm = connection.prepareStatement(query2);
                    pstm.setString(1, "n");
                    pstm.setInt(2, inventory_id);
                    pstm.setInt(3, revision);
                    updateRowsAffected = pstm.executeUpdate();
                    if (updateRowsAffected >= 1) {
                        revision = rs.getInt("revision_no") + 1;
                        PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                        psmt.setInt(1, inventory_id);
                        psmt.setInt(2, inventory_basic_id);
                        psmt.setInt(3, key_person_id);
                        psmt.setString(4, (bean.getDescription()));
                        psmt.setInt(5, revision);
                        psmt.setString(6, "Y");
                        psmt.setString(7, "OK");
                        psmt.setInt(8, stock_quantity);
                        psmt.setInt(9, 0);
                        psmt.setString(10, bean.getDate_time());
                        psmt.setString(11, bean.getReference_document_type());
                        psmt.setString(12, bean.getReference_document_id());
                        psmt.setInt(13, stock_quantity);
                        rowsAffected = psmt.executeUpdate();

                    }

                }
            }
        } catch (Exception e) {
            System.out.println("InventoryModel updateRecord() Error: " + e);
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
            System.err.println("InventoryModel getRevisionno error:" + e);
        }
        return revision;
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
            System.err.println("InventoryModel getStockQuantity error:" + e);
        }
        return quantity;
    }

    public int deleteRecord(int inventory_id) {
        String query = " DELETE FROM inventory WHERE inventory_id = " + inventory_id;
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

    public int getItemNamesId(String item_code) {
        String query = " SELECT item_names_id FROM item_names WHERE item_code = '" + item_code + "' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("item_names_id");
        } catch (Exception e) {
            System.out.println("InventoryModel getItemNamesId Error: " + e);
        }
        return id;
    }

    public int getOrgOfficeId(String org_office) {

        String query = " SELECT org_office_id FROM org_office WHERE org_office_name = '" + org_office + "' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("org_office_id");
        } catch (Exception e) {
            System.out.println("InventoryModel getOrgOfficeId Error: " + e);
        }
        return id;
    }

    public int getKeyPersonId(String key_person_name) {

        String query = " SELECT key_person_id FROM key_person WHERE key_person_name = '" + key_person_name + "' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("key_person_id");
        } catch (Exception e) {
            System.out.println("InventoryModel getKeyPersonId Error: " + e);
        }
        return id;
    }

    public int getInventoryBasicId(int org_office_id, int item_names_id) {
        String query = " SELECT inventory_basic_id FROM inventory_basic WHERE org_office_id = '" + org_office_id + "' "
                + " and item_names_id='" + item_names_id + "' and active='Y' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("inventory_basic_id");
        } catch (Exception e) {
            System.out.println("InventoryModel getInventoryBasicId Error: " + e);
        }
        return id;
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
            System.out.println("InventoryModel getItemName Error: " + e);
        }
        return name;
    }

    public List<String> getItemName(String q, String org_office) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT itn.item_name FROM item_names itn,inventory_basic ib,org_office oo where "
                + " itn.item_names_id=ib.item_names_id and oo.org_office_id=ib.org_office_id and itn.active='Y' and ib.active='Y' "
                + " and itn.is_super_child='Y' ";

        if (!org_office.equals("") && org_office != null) {
            query += " and oo.org_office_name='" + org_office + "' ";
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
            System.out.println("Error:InventoryModel--getItemName()-- " + e);
        }
        return list;
    }

    public List<String> getItemCode(String q, String manufacturer, String searchOrgOffice, String searchKeyPerson, String user_role) {
        List<Integer> list = new ArrayList<Integer>();
        List<Integer> list2 = new ArrayList<>();
        List<String> list3 = new ArrayList<>();
        if (user_role.equals("Super Admin")) {
            String query = " SELECT concat(itn.item_name,' - ',itn.item_code) as item_code FROM item_names itn,manufacturer mr,manufacturer_item_map mim where "
                    + " mr.manufacturer_id=mim.manufacturer_id and itn.item_names_id=mim.item_names_id and itn.active='Y' "
                    + " and mr.active='Y' and mim.active='Y' and itn.is_super_child='Y'  ";

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
                        list3.add(item_code);
                        count++;
                    }
                }
                if (count == 0) {
                    list3.add("No such item_code  exists.");
                }
            } catch (Exception e) {
                System.out.println("Error:InventoryModel--getItemCode()-- " + e);
            }
        } else {
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

//            if (!q.equals("") && q != null) {
//                query += " and itn.item_code='" + q + "' ";
//            }
                if (!searchOrgOffice.equals("") && searchOrgOffice != null) {
                    query += " and oo.org_office_name='" + searchOrgOffice + "' ";
                }
                if (!searchKeyPerson.equals("") && searchKeyPerson != null) {
                    query += " and kp.key_person_name='" + searchKeyPerson + "' ";
                }

                if (!manufacturer.equals("") && manufacturer != null) {
                    query += " and mr.manufacturer_name='" + manufacturer + "' ";
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
                    String qry = " SELECT T2.item_names_id,concat(T2.item_name,' - ',T2.item_code) AS item_code,T2.is_super_child FROM (SELECT @r AS _id, "
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
                        int count = 0;
                        q = q.trim();
                        list.add(rst2.getInt(1));
                        String is_super_child = rst2.getString("is_super_child");
                        if (is_super_child.equals("Y")) {
                            list3.add(rst2.getString(2));
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Exception--" + e);
            }
        }
        return list3;
    }

    public List<String> getItem(String q, String manufacturer, String searchOrgOffice, String searchKeyPerson, String user_role) {
        List<Integer> list = new ArrayList<Integer>();
        List<Integer> list2 = new ArrayList<>();
        List<String> list3 = new ArrayList<>();
        if (user_role.equals("Super Admin")) {
            String query = " SELECT itn.item_name FROM item_names itn,manufacturer mr,manufacturer_item_map mim where "
                    + " mr.manufacturer_id=mim.manufacturer_id and itn.item_names_id=mim.item_names_id and itn.active='Y' "
                    + " and mr.active='Y' and mim.active='Y' and itn.is_super_child='Y'  ";

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
                        list3.add(item_code);
                        count++;
                    }
                }
                if (count == 0) {
                    list3.add("No such item_code  exists.");
                }
            } catch (Exception e) {
                System.out.println("Error:InventoryModel--getItemCode()-- " + e);
            }
        } else {
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

//            if (!q.equals("") && q != null) {
//                query += " and itn.item_code='" + q + "' ";
//            }
                if (!searchOrgOffice.equals("") && searchOrgOffice != null) {
                    query += " and oo.org_office_name='" + searchOrgOffice + "' ";
                }
                if (!searchKeyPerson.equals("") && searchKeyPerson != null) {
                    query += " and kp.key_person_name='" + searchKeyPerson + "' ";
                }

                if (!manufacturer.equals("") && manufacturer != null) {
                    query += " and mr.manufacturer_name='" + manufacturer + "' ";
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
                    String qry = " SELECT T2.item_names_id,T2.item_name,T2.is_super_child FROM (SELECT @r AS _id, "
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
                        String is_super_child = rst2.getString("is_super_child");
                        if (is_super_child.equals("Y")) {
                            list3.add(rst2.getString(2));
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Exception--" + e);
            }
        }
        return list3;
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
            System.out.println("Error:InventoryModel--getModelName()-- " + e);
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
            System.out.println("Error:InventoryModel--getManufacturer()-- " + e);
        }
        return list;
    }

    public List<String> getOrgOffice(String q, String search_org_office, String logged_key_person, String user_role) {
        List<String> list = new ArrayList<String>();
        if (!user_role.equals("Super Admin")) {
            list.add(search_org_office);
        } else {
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
                System.out.println("Error:InventoryModel--getOrgOffice()-- " + e);
            }
        }
        return list;
    }

    public List<String> getKeyPerson(String q, String org_office, String logged_key_person, String user_role) {
        List<String> list = new ArrayList<String>();
        if (!user_role.equals("Super Admin")) {
            list.add(logged_key_person);
        } else {
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
                System.out.println("Error:InventoryModel--getKeyPerson()-- " + e);
            }
        }
        return list;
    }

    public List<String> getVendorName(String item_name) {
        List<String> list = new ArrayList<String>();

        String query = " SELECT oo.org_office_name FROM vendor_item_map vim,org_office oo,item_names itn where "
                + " vim.org_office_id=oo.org_office_id and itn.active='Y' and vim.active='Y' and oo.active='Y' "
                + " and vim.item_names_id=itn.item_names_id ";

        if (!item_name.equals("") && item_name != null) {
            query += " and itn.item_name='" + item_name + "' ";
        }

        query += " group by oo.org_office_name ORDER BY oo.org_office_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;

            while (rset.next()) {
                String org_office_name = (rset.getString("org_office_name"));

                list.add(org_office_name);
                count++;

            }
            if (count == 0) {
                list.add("");
            }
        } catch (Exception e) {
            System.out.println("Error:InventoryModel--getVendorName()-- " + e);
        }

        return list;
    }

    public List<String> getAllVendorName(String q) {
        List<String> list = new ArrayList<String>();

        String query = " SELECT oo.org_office_name FROM org_office oo where "
                + " oo.active='Y' and oo.office_type_id=7 ";

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
                list.add("No such vendor exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:InventoryModel--getVendorName()-- " + e);
        }

        return list;
    }

    public int getCounting() {
        int counting = 100000;
        int count = 0;
        String query = " SELECT order_no FROM purchase_order order by purchase_order_id desc limit 1 ";
        try {
            PreparedStatement psmt = connection.prepareStatement(query);
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                String order_no = rs.getString("order_no");
//                String order_no_arr[] = order_no.split("_");
//                int length = (order_no_arr.length) - 1;
                count = Integer.parseInt(order_no.substring(5));
                counting = count;
            }
        } catch (Exception ex) {
            System.out.println("ERROR: in getCounting() in DealersOrderModel : " + ex);
        }
        return counting + 1;
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
            System.out.println("InventoryModel closeConnection() Error: " + e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
