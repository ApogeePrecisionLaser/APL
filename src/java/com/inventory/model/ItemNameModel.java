package com.inventory.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.simple.JSONObject;
import com.DBConnection.DBConnection;
import com.inventory.tableClasses.ItemName;
import java.io.File;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.fileupload.FileItem;
import org.json.simple.JSONArray;

/**
 *
 * @author Komal
 */
public class ItemNameModel {

    private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "#a2a220";
    private final String COLOR_ERROR = "red";
    int item_id = 0;

    public void setConnection(Connection con) {
        try {
            connection = con;
        } catch (Exception e) {
            System.out.println("ItemNameModel setConnection() Error: " + e);
        }
    }

    public List<ItemName> showData(String searchItemName, String searchItemType, String itemCode, String search_super_child, String search_generation) {
        List<ItemName> list = new ArrayList<ItemName>();

        if (searchItemName == null) {
            searchItemName = "";
        }
        if (searchItemType == null) {
            searchItemType = "";
        }
        if (itemCode == null) {
            itemCode = "";
        }
        if (search_super_child == null) {
            search_super_child = "";
        }
        if (search_generation == null) {
            search_generation = "";
        }
        int p_item_id = 0;
        if (!searchItemName.equals("")) {
            p_item_id = getParent_Item_id(searchItemName);
        }
        List<Integer> idList = new ArrayList<Integer>();
        List<String> parentItemNameList = new ArrayList<String>();
        String query = "";

        try {
            if (searchItemName.equals("")) {
                parentItemNameList = getParentItemNameList();
                for (int i = 0; i < parentItemNameList.size(); i++) {
                    searchItemName = parentItemNameList.get(i);
                    idList = getAllParentChildList(searchItemName);
                    query = "select itn.item_names_id,itn.item_name,itn.description,itn.item_code,itt.item_type,itn.quantity,itn.parent_id,"
                            + "itn.generation,itn.is_super_child,itn.prefix "
                            + " from item_names itn, item_type itt where itt.item_type_id=itn.item_type_id and itn.active='Y' and itt.active='y' ";

                    if (!searchItemType.equals("") && searchItemType != null) {
                        query += " and itt.item_type='" + searchItemType + "' ";
                    }

                    if (!search_super_child.equals("") && search_super_child != null) {
                        query += " and itn.is_super_child='" + search_super_child + "' ";
                    }
                    if (!search_generation.equals("") && search_generation != null) {
                        query += " and itn.generation='" + search_generation + "' ";
                    }
                    if (!itemCode.equals("") && itemCode != null) {
                        query += " and itn.item_code='" + itemCode + "' ";
                    }

                    query += "and itn.item_names_id in(" + idList.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ") "
                            + " order by field(itn.item_names_id," + idList.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ") ";

                    ResultSet rset = connection.prepareStatement(query).executeQuery();
                    while (rset.next()) {
                        ItemName bean = new ItemName();
                        bean.setItem_names_id(rset.getInt("item_names_id"));
                        bean.setItem_name((rset.getString("item_name")));
                        bean.setDescription(rset.getString("description"));
                        bean.setItem_code(rset.getString("item_code"));
                        bean.setItem_type(rset.getString("item_type"));
                        bean.setQuantity(rset.getInt("quantity"));
                        String parent_id = rset.getString("parent_id");
                        int generation = rset.getInt("generation");
                        if (parent_id == null) {
                            parent_id = "";
                        }
                        String p_item = "";
                        String p_item_code = "";
                        if (!parent_id.equals("")) {
                            p_item = getparentItemName(parent_id);
                            p_item_code = getparentItemCode(parent_id);
                        }
                        bean.setParent_item_code(p_item_code);
                        bean.setParent_item(p_item);
                        bean.setParent_item_id(parent_id);
                        bean.setGeneration(generation);
                        bean.setSuperp(rset.getString("is_super_child"));
                        bean.setPrefix(rset.getString("prefix"));
                        list.add(bean);
                    }
                }
            } else {
                idList = getAllParentChildList(searchItemName);
                query = "select itn.item_names_id,itn.item_name,itn.description,itn.item_code,itt.item_type,itn.quantity,itn.parent_id,"
                        + "itn.generation,itn.is_super_child,itn.prefix "
                        + " from item_names itn, item_type itt where itt.item_type_id=itn.item_type_id and itn.active='Y' and itt.active='y' ";

                if (!searchItemType.equals("") && searchItemType != null) {
                    query += " and itt.item_type='" + searchItemType + "' ";
                }
                if (!search_super_child.equals("") && search_super_child != null) {
                    query += " and itn.is_super_child='" + search_super_child + "' ";
                }
                if (!search_generation.equals("") && search_generation != null) {
                    query += " and itn.generation='" + search_generation + "' ";
                }
                if (!itemCode.equals("") && itemCode != null) {
                    query += " and itn.item_code='" + itemCode + "' ";
                }
                if (!searchItemName.equals("")) {
                    query += "and itn.item_names_id in(" + idList.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ") ";

                }
                if (!searchItemName.equals("")) {
                    query += " order by field(itn.item_names_id," + idList.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ") ";
                } else {
                    query += "order by generation ";
                }

                ResultSet rset = connection.prepareStatement(query).executeQuery();
                while (rset.next()) {
                    ItemName bean = new ItemName();
                    bean.setItem_names_id(rset.getInt("item_names_id"));
                    bean.setItem_name((rset.getString("item_name")));
                    bean.setDescription(rset.getString("description"));
                    bean.setItem_code(rset.getString("item_code"));
                    bean.setItem_type(rset.getString("item_type"));
                    bean.setQuantity(rset.getInt("quantity"));
                    String parent_id = rset.getString("parent_id");
                    int generation = rset.getInt("generation");
                    if (parent_id == null) {
                        parent_id = "";
                    }
                    String p_item = "";
                    String p_item_code = "";
                    if (!parent_id.equals("")) {
                        p_item = getparentItemName(parent_id);
                        p_item_code = getparentItemCode(parent_id);
                    }
                    bean.setParent_item_code(p_item_code);
                    bean.setParent_item(p_item);
                    bean.setParent_item_id(parent_id);
                    bean.setGeneration(generation);
                    bean.setSuperp(rset.getString("is_super_child"));
                    bean.setPrefix(rset.getString("prefix"));
                    list.add(bean);
                }
            }
        } catch (Exception e) {
            System.err.println("Error:--ItemNameModel--- showData--" + e);
        }
        return list;
    }

    public JSONArray getItems(String searchItemName) {
        JSONObject obj = new JSONObject();
        JSONArray arrayObj = new JSONArray();
        List<Integer> idList = new ArrayList<Integer>();
        List<String> parentItemNameList = new ArrayList<String>();
        String query = "";
        try {

            // parentItemNameList = getParentItemNameList();
            // for (int i = 0; i < parentItemNameList.size(); i++) {
            //  searchItemName = parentItemNameList.get(i);
            //  idList = getAllParentChildList(searchItemName);
//            query = "select itn.item_names_id,itn.item_name,itn.description,itn.item_code,itt.item_type,itn.quantity,itn.parent_id,"
//                    + "itn.generation,itn.is_super_child,itn.prefix "
//                    + " from item_names itn, item_type itt where itt.item_type_id=itn.item_type_id and itn.active='Y' and itt.active='y' ";
//
//            if (!searchItemName.equals("")) {
//                query += "and itn.item_names_id in(" + idList.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ") ";
//
//            }
//            if (!searchItemName.equals("")) {
//                query += " order by field(itn.item_names_id," + idList.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ") ";
//            } else {
//                query += "order by generation ";
//            }
//            query += "and itn.item_names_id in(" + idList.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ") "
//                    + " order by field(itn.item_names_id," + idList.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ") ";
            idList = getAllParentChildList(searchItemName);
            query = "select itn.item_names_id,itn.item_name,itn.description,itn.item_code,itt.item_type,itn.quantity,itn.parent_id,"
                    + "itn.generation,itn.is_super_child,itn.prefix "
                    + " from item_names itn, item_type itt where itt.item_type_id=itn.item_type_id and itn.active='Y' and itt.active='y' ";

            if (!searchItemName.equals("")) {
                query += "and itn.item_names_id in(" + idList.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ") ";

            }
            if (!searchItemName.equals("")) {
                query += " order by field(itn.item_names_id," + idList.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ") ";
            } else {
                query += "order by generation ";
            }

            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                String item_name = rset.getString("item_name");
                item_name = item_name.replaceAll("\\\\", "/");
                int item_name_id = rset.getInt("item_names_id");
                String parent_id = rset.getString("parent_id");
                int generation = rset.getInt("generation");
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("item_name", item_name);
                jsonObj.put("item_name_id", item_name_id);
                jsonObj.put("parent_id", parent_id);
                jsonObj.put("generation", generation);
                arrayObj.add(jsonObj);
            }
            //  }
        } catch (Exception e) {
            System.err.println("exception---" + e);
        }
        return arrayObj;
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
            System.out.println("Error:--ItemNameModel--- getParentItemNameList Modified ****************--" + e);
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

    public static String getparentItemName(String id) {
        String name = "";
        String query1 = " SELECT item_name FROM item_names WHERE  active='Y' ";

        if (!id.equals("") && id != null) {
            query1 += " and item_names_id='" + id + "' ";
        }
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                name = rset.getString("item_name");
            }
        } catch (Exception ex) {
            System.err.println("getparentItemName error---------" + ex);
        }

        return name;
    }

    public static String getparentItemCode(String id) {
        String item_code = "";
        String query1 = " SELECT item_code FROM item_names WHERE  active='Y' ";

        if (!id.equals("") && id != null) {
            query1 += " and item_names_id='" + id + "' ";
        }
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                item_code = rset.getString("item_code");
            }
        } catch (Exception ex) {
            System.err.println("getparentItemCode error---------" + ex);
        }

        return item_code;
    }

    public int insertRecord(ItemName item_name, Iterator itr) throws SQLException {
        String query = "INSERT INTO item_names(item_name,item_type_id,description,"
                + " revision_no,active,remark,item_code,quantity,parent_id,generation,is_super_child,prefix) VALUES(?,?,?,?,?,?,?,?,?,?,?,?) ";

        int rowsAffected = 0;
        int p_item_id = 0;
        int p_item_id_for_code = 0;
        int count = 0;
        String is_child = item_name.getSuperp();
        if (is_child != null) {
            if (is_child.equals("yes") || is_child.equals("Yes") || is_child.equals("YES") || is_child.equals("Y") || is_child.equals("y")) {
                is_child = "Y";
            } else {
                is_child = "N";
            }
        }
        if (!item_name.getParent_item().equals("")) {
            String parent_item_name_arr[] = item_name.getParent_item().split(" - ");
            String p_item_name = parent_item_name_arr[0];
            String item_code = parent_item_name_arr[1];
            p_item_id_for_code = getParent_Item_id_for_code(p_item_name, item_code);
            //  p_item_id = getParent_Item_id(p_item_name);
        }

        int generation = 0;
        if (p_item_id_for_code == 0) {
            generation = 1;
        } else {
            generation = getParentGeneration(p_item_id_for_code) + 1;
        }
        String query2 = " select count(*) as count from item_names where "
                + " prefix='" + item_name.getPrefix() + "' and parent_id='" + p_item_id_for_code + "' ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, item_name.getItem_name());
            pstmt.setInt(2, item_name.getItem_type_id());
            pstmt.setString(3, item_name.getDescription());
            pstmt.setInt(4, item_name.getRevision_no());
            pstmt.setString(5, "Y");
            pstmt.setString(6, "OK");

            List<String> list = new ArrayList<String>();
            String parent_prefix = "";
            String item_code = "";

            StringBuilder item_code_builder = new StringBuilder();
            item_code_builder.delete(0, item_code_builder.length());

            if (p_item_id_for_code != 0) {
                list = getParentPrefix(p_item_id_for_code);

                if (list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        parent_prefix = list.get(i);
                        item_code_builder.append(parent_prefix).toString();
                    }
                    item_code = (item_code_builder.toString() + item_name.getPrefix()).toString();
                }
            } else {
                item_code = item_name.getPrefix();
            }

            pstmt.setString(7, item_code);
            pstmt.setInt(8, item_name.getQuantity());
            if (p_item_id_for_code == 0) {
                pstmt.setString(9, null);
            } else {
                pstmt.setInt(9, p_item_id_for_code);
            }

            pstmt.setInt(10, generation);
            pstmt.setString(11, is_child);
            pstmt.setString(12, item_name.getPrefix());

            ResultSet rset = connection.prepareStatement(query2).executeQuery();

            while (rset.next()) {
                count = rset.getInt("count");
            }
            if (count > 0) {
                message = "This Prefix Already Exists for another item for this parent ";
                msgBgColor = COLOR_ERROR;
            } else {
                rowsAffected = pstmt.executeUpdate();
            }
            // rowsAffected = insertImageRecord(rowsAffected, item_name, itr, destination, item_id, image_name, image_count);

        } catch (Exception e) {
            System.out.println("ItemNameModel insertRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        if (count > 0) {
            message = "This Prefix Already Exists for another item for this parent ";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int getParentGeneration(int p_id) {
        String query = "SELECT * FROM item_names WHERE item_names_id='" + p_id + "' and active='Y' ";
        int generation = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            generation = rset.getInt("generation");
        } catch (Exception e) {
            System.out.println("Error: getParentGeneration--" + e);
        }
        return generation;
    }

    public List<String> getParentPrefix(int p_id) {
        List<String> list = new ArrayList<String>();
//        String query = "SELECT prefix FROM item_names WHERE item_names_id='" + p_id + "' and active='Y' ";

        String query = " SELECT T2.prefix FROM (SELECT @r AS _id,"
                + " (SELECT @r := parent_id FROM item_names WHERE item_names_id = _id) AS parent_id, "
                + " @l := @l + 1 AS lvl "
                + " FROM"
                + " (SELECT @r := '" + p_id + "', @l := 0) vars, "
                + " item_names h "
                + " WHERE @r <> 0) T1 "
                + " JOIN item_names T2 "
                + " ON T1._id = T2.item_names_id where T2.active='y'  "
                + " ORDER BY T1.lvl DESC ";
        String prefix = "";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                prefix = rset.getString("prefix");
                list.add(prefix);
            }
        } catch (Exception e) {
            System.out.println("Error: getParentPrefix--" + e);
        }
        return list;
    }

    public int getParent_Item_id(String item_name) {
        String query = "SELECT item_names_id FROM item_names WHERE item_name = '" + item_name + "' and active='Y' ";
        int p_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            p_id = rset.getInt("item_names_id");
        } catch (Exception e) {
            System.out.println("Error: getParent_Item_id--" + e);
        }
        return p_id;
    }

    public int getParent_Item_id_for_code(String item_name, String item_code) {

        String query = "SELECT item_names_id FROM item_names WHERE item_name = '" + item_name + "' and item_code='" + item_code + "' and active='Y' ";
        int p_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            p_id = rset.getInt("item_names_id");
        } catch (Exception e) {
            System.out.println("Error: getParent_Item_id--" + e);
        }
        return p_id;
    }

//    public int insertImageRecord(int rowsAffected, ItemName item_name, Iterator itr, String destination, int item_id, String image_name, int image_count) {
//        String img_message = "";
//        DateFormat dateFormat1 = new SimpleDateFormat("dd.MMMMM.yyyy");
//        DateFormat dateFormat = new SimpleDateFormat("dd.MMMMM.yyyy/ hh:mm:ss aaa");
//        Date date = new Date();
//        String middleName = "";
//        String current_date = dateFormat.format(date);
//        String query3 = "INSERT INTO item_image_details (image_name, image_path, date_time, description,item_names_id,revision_no,active,remark) "
//                + " VALUES(?,?,?,?,?,?,?,?)";
//        String imageName = "";
//        try {
//
//            if ((!item_name.getImage_path().isEmpty())) {
//                if ((!item_name.getImage_path().isEmpty())) {
//                    String tempExt = image_name;
//                    if (!tempExt.isEmpty()) {
//
//                        String fieldName = "";
//                        if (tempExt.equals(image_name)) {
//                            String item_names = item_name.getItem_name().replaceAll("[^a-zA-Z0-9]", "_");
//                            middleName = "img_Item_" + item_names + "_" + image_count;
//                            destination = "C:\\ssadvt_repository\\APL\\item\\" + item_names + "\\";
//                            fieldName = "item_image";
//                        }
//
//                        int index = tempExt.lastIndexOf(".");
//                        int index1 = tempExt.length();
//                        String Extention = tempExt.substring(index + 1, index1);
//                        tempExt = "." + Extention;
//                        imageName = middleName + tempExt;
//
//                        imageName = imageName.replaceAll("[^a-zA-Z0-9]", "_");
//
//                        WirteImage(item_name, itr, destination, imageName, fieldName);
//                    }
//                    PreparedStatement pstmt1 = connection.prepareStatement(query3);
//                    pstmt1.setString(1, imageName);
//                    pstmt1.setString(2, destination);
//                    pstmt1.setString(3, current_date);
//                    pstmt1.setString(4, "this image is for site");
//                    pstmt1.setInt(5, item_id);
//                    pstmt1.setString(6, "0");
//                    pstmt1.setString(7, "Y");
//                    pstmt1.setString(8, "ok");
//                    rowsAffected = pstmt1.executeUpdate();
//                    pstmt1.close();
//                }
//            }
//        } catch (Exception e) {
//            System.err.println("insertImageRecord exception-----" + e);
//        }
//        return rowsAffected;
//    }
    public String getImagePath(String item_image_details_id) {
        List<String> list = new ArrayList<String>();
        String img_name = "";

        String destination_path = "";
        String query = "SELECT item_image_details_id,image_name, image_path "
                + " FROM item_image_details iid "
                + " where iid.item_image_details_id=" + item_image_details_id + " ORDER BY item_image_details_id ";

        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                img_name = rs.getString("image_name");
                destination_path = rs.getString("image_path");
                destination_path = destination_path + img_name;
            }

        } catch (Exception ex) {
            System.out.println("ERROR: in getImagePath in ItemNameModel : " + ex);
        }
        return destination_path;
    }

    public List<ItemName> getImageList(String item_names_id) {
        List<ItemName> list = new ArrayList<ItemName>();

        String data = "";
        String img_name = "";
        String destination_path = "";
        String query = "SELECT item_image_details_id,image_name, image_path,item_names_id "
                + " FROM item_image_details iid "
                + " where iid.item_names_id=" + item_names_id + " and active='Y'  ORDER BY item_image_details_id ";

        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                ItemName bean = new ItemName();
                int item_image_details_id = rs.getInt("item_image_details_id");
                img_name = rs.getString("image_name");
                destination_path = rs.getString("image_path");
                destination_path = destination_path + img_name;
                bean.setItem_names_id(Integer.parseInt(item_names_id));
                bean.setItem_image_details_id(item_image_details_id);
                bean.setImage_name(img_name);
                bean.setDestination_path(destination_path);
                list.add(bean);
            }

        } catch (Exception e) {
            System.err.println("Exception in getImageList---------" + e);
        }
        return list;
    }

    public int updateRecord(ItemName item_name, Iterator itr, int item_names_id) {
        int revision = ItemNameModel.getRevisionno(item_name, item_names_id);
        int updateRowsAffected = 0;
        String is_child = item_name.getSuperp();
        if (is_child != null) {
            if (is_child.equals("yes") || is_child.equals("Yes") || is_child.equals("YES") || is_child.equals("Y") || is_child.equals("y")) {
                is_child = "Y";
            } else {
                is_child = "N";
            }
        }
        boolean status = false;
        int item_id = 0;
        DateFormat dateFormat1 = new SimpleDateFormat("dd.MMMMM.yyyy");
        DateFormat dateFormat = new SimpleDateFormat("dd.MMMMM.yyyy/ hh:mm:ss aaa");
        Date date = new Date();
        String current_date = dateFormat.format(date);
        int p_item_id = 0;
        int p_item_id_for_code = 0;

        if (!item_name.getParent_item().equals("")) {
            String parent_item_name_arr[] = item_name.getParent_item().split(" - ");
            String p_item_name = parent_item_name_arr[0];
            String item_code = parent_item_name_arr[1];
            p_item_id_for_code = getParent_Item_id_for_code(p_item_name, item_code);
            p_item_id = getParent_Item_id(p_item_name);
        }

        int generation = 0;

        if (p_item_id_for_code == 0) {
            generation = 1;
        } else {
            generation = getParentGeneration(p_item_id_for_code) + 1;
        }

        String query1 = "SELECT max(revision_no) revision_no FROM item_names WHERE item_names_id = " + item_names_id + "  && active='Y' ";
        String query2 = "UPDATE item_names SET active=? WHERE item_names_id=? and revision_no=? ";
        String query3 = "INSERT INTO item_names(item_names_id,item_name,item_type_id,description,revision_no, "
                + " active,remark,item_code,quantity,parent_id,generation,is_super_child,prefix) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        int rowsAffected = 0;
        try {
//            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement(query1);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                PreparedStatement pstm = connection.prepareStatement(query2);

                pstm.setString(1, "n");
                pstm.setInt(2, item_names_id);
                pstm.setInt(3, revision);
                updateRowsAffected = pstm.executeUpdate();
                if (updateRowsAffected >= 1) {
                    PreparedStatement pstmt1 = connection.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
                    pstmt1.executeUpdate();

                    revision = rs.getInt("revision_no") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                    psmt.setInt(1, (item_name.getItem_names_id()));
                    psmt.setString(2, (item_name.getItem_name()));
                    psmt.setInt(3, (item_name.getItem_type_id()));
                    psmt.setString(4, (item_name.getDescription()));
                    psmt.setInt(5, revision);
                    psmt.setString(6, "Y");
                    psmt.setString(7, "OK");

                    List<String> list = new ArrayList<String>();
                    String parent_prefix = "";
                    String item_code = "";

                    StringBuilder item_code_builder = new StringBuilder();
                    item_code_builder.delete(0, item_code_builder.length());
                    if (p_item_id_for_code != 0) {
                        list = getParentPrefix(p_item_id_for_code);

                        if (list != null) {
                            for (int i = 0; i < list.size(); i++) {
                                parent_prefix = list.get(i);
                                item_code_builder.append(parent_prefix).toString();
                            }
                            item_code = (item_code_builder.toString() + item_name.getPrefix()).toString();
                        }
                    } else {
                        item_code = item_name.getPrefix();
                    }

                    psmt.setString(8, item_code);
                    psmt.setInt(9, item_name.getQuantity());
                    psmt.setInt(10, p_item_id_for_code);
                    psmt.setInt(11, generation);
                    psmt.setString(12, is_child);
                    psmt.setString(13, item_name.getPrefix());
                    rowsAffected = psmt.executeUpdate();

//                    if (rowsAffected > 0) {
//                        status = true;
//                        item_id = item_names_id;
//                    } else {
//                        status = false;
//                    }
//                    //  rowsAffected = insertImageRecord(rowsAffected, item_name, itr, destination, item_id, image_name, image_count);
//
//                    if (rowsAffected > 0) {
//                        status = true;
//                        message = "Record updated successfully.";
//                        msgBgColor = COLOR_OK;
//                        connection.commit();
//                    } else {
//                        status = false;
//                        message = "Cannot update the record, some error.";
//                        msgBgColor = COLOR_ERROR;
//                        connection.rollback();
//                    }
                }

            }
        } catch (Exception e) {
            System.out.println("ItemNameModel updateRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record updated successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int getImageCount(int item_name_id) {
        int image_count = 0;
        String image_name = "";

        try {
            String query = " SELECT image_name FROM item_image_details"
                    + " WHERE item_names_id =" + item_name_id + " order by item_image_details_id desc limit 1 ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                image_name = rset.getString("image_name");
                String img_name_arr[] = image_name.split(".");
                int indexOfDecimal = image_name.indexOf(".");
                String img_count_arr[] = image_name.substring(0, indexOfDecimal).split("_");
                image_count = Integer.parseInt(img_count_arr[3]);
            }
        } catch (Exception e) {
            System.err.println("getImageCount Exception-----" + e);
        }
        return image_count;
    }

    public void WirteImage(ItemName key, Iterator itr, String destination, String imageName, String fieldName) {
        int count = 0;

        try {
            while (itr.hasNext()) {
                FileItem item = (FileItem) itr.next();
                makeDirectory(destination);
                try {
                    if (!item.isFormField()) {
                        if (item.getFieldName().equals(fieldName)) {
                            File file = new File(destination + imageName);
                            String image = item.getName();
                            if (image.isEmpty() || image.equals(destination)) {
                            } else {
                                item.write(file);
                                message = "Image Uploaded Successfully.";
                                count++;
                            }
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("WirteImage error: " + e);
                }
            }
            //}
        } catch (Exception ex) {
        }
    }

    public boolean makeDirectory(String dirPathName) {
        boolean result = false;
        File directory = new File(dirPathName);
        if (!directory.exists()) {
            try {
                result = directory.mkdirs();
            } catch (Exception e) {
                System.out.println("makeDirectory Error - " + e);
            }
        }
        return result;
    }

    public static int getRevisionno(ItemName itemName, int item_names_id) {
        int revision = 0;
        try {
            String query = " SELECT max(revision_no) as revision_no FROM item_names WHERE item_names_id =" + item_names_id + "  && active='Y' ";
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

    public static String getRevisionnoForImage(ItemName key, int item_names_id) {
        String revision = "";
        try {
            String query = " SELECT max(revision_no) as revision_no FROM item_image_details"
                    + " WHERE item_names_id =" + item_names_id + "  && active='Y';";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getString("revision_no");
            }
        } catch (Exception e) {
            System.err.println("getRevisionnoForImage error:--" + e);
        }
        return revision;
    }

    public int deleteRecord(int item_names_id) {
        String query1 = " select count(*) as count from item_names where parent_id='" + item_names_id + "' ";

        String query = "DELETE FROM item_names WHERE item_names_id = " + item_names_id;
        int child_item_count = 0;
        int rowsAffected = 0;
        try {
            PreparedStatement psmt = connection.prepareStatement(query1);
            ResultSet rst = psmt.executeQuery();
            while (rst.next()) {
                child_item_count = rst.getInt("count");
            }
            if (child_item_count > 0) {
                message = "Can't delete because it's child items exists!..";
                msgBgColor = COLOR_ERROR;
            } else {
                PreparedStatement pstmt1 = connection.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
                pstmt1.executeUpdate();
                rowsAffected = connection.prepareStatement(query).executeUpdate();
            }

        } catch (Exception e) {
            System.out.println("ItemNameModel deleteRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record deleted successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot delete the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        if (child_item_count > 0) {
            message = "Can't delete because it's child items exists!..";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int deleteImageRecord(String item_image_detail_id) throws SQLException {
        int rowsAffected = 0;
        ResultSet rst;
        int count = 0;
        int updateRowsAffected = 0;

        String query2 = "UPDATE item_image_details SET active=? WHERE item_image_details_id=? ";

        try {
            PreparedStatement pstm = connection.prepareStatement(query2);
            pstm.setString(1, "n");
            pstm.setString(2, item_image_detail_id);
            rowsAffected = pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println("deleteImageRecord Error: " + e);
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

    public static int getImageRevisionno(ItemName itemName, int item_names_id) {
        int revision = 0;
        try {
            String query = " SELECT max(revision_no) as revision_no FROM item_names WHERE item_names_id =" + item_names_id + "  && active='Y' ";
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");
            }
        } catch (Exception e) {
            System.err.println("getImageRevisionno error:--" + e);
        }
        return revision;
    }

    public int getItemNamesId(String item_name) {
        String query = "SELECT item_names_id FROM item_name WHERE item_name = ?";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, item_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("item_names_id");
        } catch (Exception e) {
            System.out.println("getItemNamesId Error: " + e);
        }
        return id;
    }

    public int getItemTypeID(String item_type) {
        String query = "SELECT item_type_id FROM item_type WHERE item_type = '" + item_type + "' and active='Y' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //// pstmt.setString(1, item_type);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("item_type_id");
        } catch (Exception e) {
            System.out.println("getItemTypeID Error: " + e);
        }
        return id;
    }

    public String getItemTypeName(int item_type) {
        String query = "SELECT item_type FROM item_type WHERE item_type_id = ? and active='Y'";
        String name = "";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, item_type);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            name = rset.getString("item_type");
        } catch (Exception e) {
            System.out.println("getItemTypeName Error: " + e);
        }
        return name;
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

    public List<String> getItemType(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT item_type FROM item_type where active='Y' ORDER BY item_type";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String item_type = (rset.getString("item_type"));
                if (item_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(item_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such item_type exists.......");
            }
        } catch (Exception e) {
            System.out.println("Error:ItemNameModel--getItemType()-- " + e);
        }
        return list;
    }

    public List<String> getItemName(String q, String item_type) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT itn.item_name FROM item_names itn,item_type itt where itn.item_type_id=itt.item_type_id and"
                + " itn.active='Y' and itt.active='Y'  ";
        if (!item_type.equals("") && item_type != null) {
            query += " and itt.item_type='" + item_type + "' ";
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
            System.out.println("Error:ItemNameModel--getItemName()-- " + e);
        }
        return list;
    }

    public List<String> getItemCode(String q, String item_type, String item_name) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT itn.item_code FROM item_names itn,item_type itt where itn.item_type_id=itt.item_type_id and"
                + " itn.active='Y' and itt.active='Y' and itn.is_super_child='Y' ";
        if (!item_type.equals("") && item_type != null) {
            query += " and itt.item_type='" + item_type + "' ";
        }
        if (!item_name.equals("") && item_name != null) {
            query += " and itn.item_name='" + item_name + "' ";
        }
        query += " group by itn.item_name ORDER BY itn.item_names_id desc ";
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
                list.add("No such item_code exists.......");
            }
        } catch (Exception e) {
            System.out.println("Error:ItemNameModel--getItemCode()-- " + e);
        }
        return list;
    }

    public List<String> getParentItemName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT concat(itn.item_name,' - ',itn.item_code) as parent_item_name  FROM item_names itn where"
                + " itn.active='Y' and itn.is_super_child='N' ORDER BY itn.item_name ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String item_name = (rset.getString("parent_item_name"));
                if (item_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(item_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such item_name exists.......");
            }
        } catch (Exception e) {
            System.out.println("Error:ItemNameModel--getParentItemName()-- " + e);
        }
        return list;
    }

    public List<String> getGeneration(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT generation  FROM item_names itn where"
                + " itn.active='Y' group by itn.generation ORDER BY itn.generation ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String generation = (rset.getString("generation"));
                if (generation.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(generation);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such generation exists.......");
            }
        } catch (Exception e) {
            System.out.println("Error:ItemNameModel--getGeneration()-- " + e);
        }
        return list;
    }

    public List<String> getSuperChild(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT is_super_child  FROM item_names itn where"
                + " itn.active='Y' group by itn.is_super_child ORDER BY itn.is_super_child ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String is_super_child = (rset.getString("is_super_child"));
                if (is_super_child.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(is_super_child);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such is_super_child exists.......");
            }
        } catch (Exception e) {
            System.out.println("Error:ItemNameModel--getSuperChild()-- " + e);
        }
        return list;
    }

    public String getDestination_Path(String image_uploaded_for) {
        String destination_path = "";
        String query = " SELECT destination_path FROM image_destination id, image_uploaded_for  iuf "
                + " WHERE id.image_uploaded_for_id = iuf.image_uploaded_for_id "
                + " AND iuf.image_uploaded_for = '" + image_uploaded_for + "' ";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                destination_path = rs.getString("destination_path");
            }
        } catch (Exception ex) {
            System.out.println("ERROR: in getDestination_Path in ItemNameModel : " + ex);
        }
        return destination_path;
    }

//    public int getCounting() {
//        int counting = 100;
//        int count = 0;
//        String query = " SELECT item_code FROM item_names order by item_names_id desc limit 1 ";
//        try {
//            PreparedStatement psmt = connection.prepareStatement(query);
//            ResultSet rs = psmt.executeQuery();
//            while (rs.next()) {
//                String item_code = rs.getString("item_code");
//                String item_code_arr[] = item_code.split("_");
//                int length = (item_code_arr.length) - 1;
//                count = Integer.parseInt(item_code_arr[length]);
//
//                counting = count;
//            }
//        } catch (Exception ex) {
//            System.out.println("ERROR: in getCounting in TraffiPoliceSearchModel : " + ex);
//        }
//        return counting + 1;
//    }
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
