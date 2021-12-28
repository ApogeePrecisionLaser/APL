/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.model;

import com.inventory.tableClasses.ItemType;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Komal
 */
public class ItemTypeModel {

    private static Connection connection;

    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "#a2a220";
    private final String COLOR_ERROR = "red";

    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("ItemTypeModel setConnection() Error: " + e);
        }
    }

    public List<ItemType> showData(String searchItemType, String active) {
        List<ItemType> list = new ArrayList<ItemType>();
        String query = " SELECT item_type_id, item_type ,description FROM item_type where active='Y' ";

        if (!searchItemType.equals("") && searchItemType != null) {
            query += " and item_type='" + searchItemType + "' ";
        }

        try {
            System.err.println("--query ---" + query);
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                ItemType bean = new ItemType();
                bean.setItem_type_id(rset.getInt("item_type_id"));
                bean.setItem_type((rset.getString("item_type")));
                bean.setDescription((rset.getString("description")));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("ItemTypeModel showData() Error: " + e);
        }
        return list;
    }

    public int insertRecord(ItemType item_Type) {
        String query = "INSERT INTO item_type(item_type, description,revision_no,active,remark) VALUES(?,?,?,?,?) ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, (item_Type.getItem_type()));
            pstmt.setString(2, (item_Type.getDescription()));
            pstmt.setInt(3, item_Type.getRevision_no());
            pstmt.setString(4, "Y");
            pstmt.setString(5, "OK");

            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("ItemTypeModel insertRecord() Error: " + e);
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

    public int updateRecord(ItemType item_type, int item_type_id) {
        int revision = ItemTypeModel.getRevisionno(item_type, item_type_id);
        int updateRowsAffected = 0;
//        String is_child = item_type.getSuperp();
//        if (is_child != null) {
//            if (is_child.equals("yes") || is_child.equals("Yes") || is_child.equals("YES") || is_child.equals("Y") || is_child.equals("y")) {
//                is_child = "Y";
//            } else {
//                is_child = "N";
//            }
//        }
        int item_count = 0;
        boolean status = false;
        String query1 = "SELECT max(revision_no) revision_no FROM item_type WHERE item_type_id = " + item_type_id + "  && active=? ";
        String query2 = "UPDATE item_type SET active =? WHERE item_Type_id =? and revision_no=? ";
        // String query3 = "INSERT INTO item_type(item_type_id,item_type,description,revision_no,active,remark,is_super_child) VALUES(?,?,?,?,?,?,?)";
        String query3 = "INSERT INTO item_type(item_type_id,item_type,description,revision_no,active,remark) VALUES(?,?,?,?,?,?)";
//        String query4 = " select is_super_child from item_type where item_type_id='" + item_type_id + "' ";
//        String query5 = " select count(*) as count from item_names inn,item_type itt where inn.item_type_id=itt.item_type_id and"
//                + " inn.item_type_id='" + item_type_id + "' and itt.is_super_child='N'  ";
        int rowsAffected = 0;
        //String is_super_child = "";
        try {
//            PreparedStatement pstmnt = connection.prepareStatement(query4);
//            ResultSet rst = pstmnt.executeQuery();
//            while (rst.next()) {
//                is_super_child = rst.getString("is_super_child");
//                System.err.println("is_super_child----" + is_super_child);
//            }
//            if (is_super_child.equals('Y')) {
            //System.err.println("if is_super_child y----");
            PreparedStatement pstmt = connection.prepareStatement(query1);
            pstmt.setString(1, "Y");
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                PreparedStatement pstm = connection.prepareStatement(query2);
                pstm.setString(1, "n");
                pstm.setInt(2, item_type_id);
                pstm.setInt(3, revision);
                updateRowsAffected = pstm.executeUpdate();
                if (updateRowsAffected >= 1) {
                    revision = rs.getInt("revision_no") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                    psmt.setInt(1, (item_type_id));
                    psmt.setString(2, item_type.getItem_type());
                    psmt.setString(3, item_type.getDescription());
                    psmt.setInt(4, revision);
                    psmt.setString(5, "Y");
                    psmt.setString(6, "OK");
//                    psmt.setString(7, is_child);
                    rowsAffected = psmt.executeUpdate();
                }
            }

            if (rowsAffected > 0) {
                status = true;
            } else {
                status = false;
            }
        } catch (Exception e) {
            System.out.println("ItemTypeModel updateRecord() Error: " + e);
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

    public static int getRevisionno(ItemType bean, int item_type_id) {
        int revision = 0;
        try {

            String query = " SELECT max(revision_no) as revision_no FROM item_type WHERE item_type_id =" + item_type_id + "  && active='Y';";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
            System.out.println("ItemTypeModel getRevisionno() Error: " + e);
        }
        return revision;
    }

    public int deleteRecord(int item_type_id) {
//        String query4 = " select is_super_child from item_type where item_type_id='" + item_type_id + "' ";
//        String query5 = " select count(*) as count from item_names inn,item_type itt where inn.item_type_id=itt.item_type_id and"
//                + " inn.item_type_id='" + item_type_id + "' and itt.is_super_child='N'  ";

        String query = "DELETE FROM item_type WHERE active='Y' and item_type_id = " + item_type_id;
        int rowsAffected = 0;
        String is_super_child = "";
        int item_count = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();

        } catch (Exception e) {
            System.out.println("ItemTypeModel deleteRecord() Error: " + e);
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

    public List<String> getItemType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT item_type_id, item_type FROM item_type i where i.active='Y' ORDER BY item_type ";
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
                list.add("No such Item Type exists.");
            }
        } catch (Exception e) {
            System.out.println("ItemTypeModel getItemType ERROR - " + e);
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
            System.out.println("ItemTypeModel closeConnection() Error: " + e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
