/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.model;

import com.inventory.tableClasses.ManufacturerItemMap;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author komal
 */
public class ManufacturerItemMapModel {

    private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "#a2a220";
    private final String COLOR_ERROR = "red";
    private static List<ManufacturerItemMap> list1 = new ArrayList<ManufacturerItemMap>();
    static int count = 0;
    static int prev_parent_id = 0;
    static int off_id = 0;

    public void setConnection(Connection con) {
        try {
            connection = con;
        } catch (Exception e) {
            System.out.println("ManufacturerItemMapModel setConnection() Error: " + e);
        }
    }

    public List<ManufacturerItemMap> showData(String searchManufacturer, String searchItem) {
        PreparedStatement pstmt = null;
        String query;
        List<ManufacturerItemMap> list = new ArrayList<ManufacturerItemMap>();

        try {
            query = " select mimm.manufacturer_item_map_id,mr.manufacturer_name,inn.item_name,mimm.serial_no from "
                    + " manufacturer_item_map mimm,manufacturer mr,item_names inn "
                    + " where mimm.manufacturer_id=mr.manufacturer_id and mimm.item_names_id = inn.item_names_id  "
                    + " and mimm.active = 'Y' and mr.active='Y' and inn.active='Y'  ";
            if (!searchManufacturer.equals("") && searchManufacturer != null) {
                query += " and mr.manufacturer_name='" + searchManufacturer + "' ";
            }
            if (!searchItem.equals("") && searchItem != null) {
                query += " and inn.item_name='" + searchItem + "' ";
            }
//            if (!searchModel.equals("") && searchModel != null) {
//                query += " and m.model='" + searchModel + "' ";
//            }
            pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                int manufacturer_item_map_id = rset.getInt("manufacturer_item_map_id");
                String manufacturer_name = rset.getString("manufacturer_name");
                String item_name = rset.getString("item_name");
//                String model = rset.getString("model");
                String description = rset.getString("serial_no");

                ManufacturerItemMap bean = new ManufacturerItemMap();
                bean.setManufacturer_item_map_id(manufacturer_item_map_id);
                bean.setManufacturer_name(manufacturer_name);
                bean.setItem_name(item_name);
//                bean.setModel_name(model);
                bean.setDescription(description);
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error:--ManufacturerItemMapModel--- showData--" + e);
        }
        return list;
    }

    public int insertRecord(ManufacturerItemMap bean) {
        int rowsAffected = 0;
        int count = 0;
        int manufacturer_id = getManufacturerId(bean.getManufacturer_name());
        int item_id = getItemId(bean.getItem_name());
        int model_id = getModelId(bean.getModel_name());

        String query = "insert into manufacturer_item_map(manufacturer_id,item_names_id,"
                + " active,revision,remark,created_by,serial_no,created_at) "
                + " values (?,?,?,?,?,?,?,now()) ";
        int map_count = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            String query4 = "SELECT count(*) as count FROM manufacturer_item_map WHERE "
                    + " manufacturer_id='" + manufacturer_id + "' and item_names_id='" + item_id + "'"
                    + " and active='Y'  ";

            PreparedStatement pstmt1 = connection.prepareStatement(query4);
            ResultSet rs1 = pstmt1.executeQuery();
            while (rs1.next()) {
                map_count = rs1.getInt("count");
            }
            if (map_count > 0) {
                message = "Item has already mapped with this manufacturer!..";
                msgBgColor = COLOR_ERROR;
            } else {
                pstmt.setInt(1, manufacturer_id);
                pstmt.setInt(2, item_id);
                //  pstmt.setInt(3, model_id);
                pstmt.setString(3, "Y");
                pstmt.setString(4, "0");
                pstmt.setString(5, bean.getRemark());
                pstmt.setString(6, "Komal");
                pstmt.setString(7, bean.getDescription());
                rowsAffected = pstmt.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Error while inserting record in insertRecord ManufacturerItemMapModel...." + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        if (map_count > 0) {
            message = "Item has already mapped with this manufacturer!..";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int updateRecord(ManufacturerItemMap bean, int manufacturer_item_map_id) throws SQLException {
        int revision = ManufacturerItemMapModel.getRevisionno(bean, manufacturer_item_map_id);
        int rowsAffected = 0;
        int count = 0;
        int updateRowsAffected = 0;
        Boolean status = false;
        int manufacturer_id = getManufacturerId(bean.getManufacturer_name());
        int item_id = getItemId(bean.getItem_name());
//        int model_id = getModelId(bean.getModel_name());

        String query1 = "SELECT max(revision) FROM manufacturer_item_map WHERE "
                + " manufacturer_item_map_id = " + manufacturer_item_map_id + " and active='Y' ";

        String query2 = "UPDATE manufacturer_item_map SET active=? WHERE manufacturer_item_map_id=? and revision=? ";

        String query3 = "insert into manufacturer_item_map(manufacturer_item_map_id,manufacturer_id,item_names_id, "
                + " active,revision,remark,created_by,serial_no,created_at) "
                + " values (?,?,?,?,?,?,?,?,now()) ";
        String query4 = "SELECT count(*) as count FROM manufacturer_item_map WHERE "
                + " manufacturer_id='" + manufacturer_id + "' and item_names_id='" + item_id + "'"
                + " and active='Y'  ";

        int map_count = 0;
        try {
            connection.setAutoCommit(false);

            PreparedStatement pstmt1 = connection.prepareStatement(query4);
            ResultSet rs1 = pstmt1.executeQuery();
            while (rs1.next()) {
                map_count = rs1.getInt("count");
            }
            if (map_count > 0) {
                status = false;
                message = "Item has already mapped with this manufacturer!..";
                msgBgColor = COLOR_ERROR;
            } else {

                PreparedStatement pstmt = connection.prepareStatement(query1);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    revision = rs.getInt("max(revision)");
                    PreparedStatement pstm = connection.prepareStatement(query2);
                    pstm.setString(1, "N");
                    pstm.setInt(2, manufacturer_item_map_id);
                    pstm.setInt(3, revision);
                    updateRowsAffected = pstm.executeUpdate();
                    if (updateRowsAffected >= 1) {
                        revision = rs.getInt("max(revision)") + 1;
                        PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                        psmt.setInt(1, manufacturer_item_map_id);
                        psmt.setInt(2, manufacturer_id);
                        psmt.setInt(3, item_id);
//                    psmt.setInt(4, model_id);
                        psmt.setString(4, "Y");
                        psmt.setInt(5, revision);
                        psmt.setString(6, bean.getRemark());
                        psmt.setString(7, "Komal");
                        psmt.setString(8, bean.getDescription());
                        rowsAffected = psmt.executeUpdate();
                        if (rowsAffected > 0) {
                            status = true;
                            message = "Record updated successfully.";
                            msgBgColor = COLOR_OK;
                            connection.commit();
                        } else {
                            status = false;
                            message = "Cannot update the record, some error.";
                            msgBgColor = COLOR_ERROR;
                            connection.rollback();
                        }
                    }
                } else {
                    message = "Key already mapped with some child!";
                    msgBgColor = COLOR_ERROR;
                }
            }
        } catch (Exception e) {
            System.out.println("Error: ManufacturerItemMapModel---updateRecord" + e);
        } finally {

        }
        return rowsAffected;
    }

    public static int getRevisionno(ManufacturerItemMap orgOffice, int manufacturer_item_map_id) {
        int revision = 0;
        try {
            String query = " SELECT max(revision) as revision_no FROM manufacturer_item_map"
                    + " WHERE manufacturer_item_map_id =" + manufacturer_item_map_id + "  && active='Y' ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");
            }
        } catch (Exception e) {
            System.out.println("Error: ManufacturerItemMapModel---getRevisionno" + e);
        }
        return revision;
    }

    public int deleteRecord(int manufacturer_item_map_id) throws SQLException {
        int rowsAffected = 0;
        PreparedStatement psmt;
        ResultSet rst;

        String query = "DELETE FROM manufacturer_item_map WHERE manufacturer_item_map_id = '" + manufacturer_item_map_id + "' "
                + "and active='Y' ";
        try {
            psmt = connection.prepareStatement(query);
            rowsAffected = psmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("ManufacturerItemMapModel deleteRecord Error: " + e);
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

    public List<String> getManufacturer(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT manufacturer_name FROM manufacturer where active='Y' ORDER BY manufacturer_name ";
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
                list.add("No such manufacturer_name exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:ManufacturerItemMapModel--getManufacturer()-- " + e);
        }
        return list;
    }

    public List<String> getItem(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT item_name FROM item_names where active='Y' ORDER BY item_name ";
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
                list.add("No such item_name exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:ManufacturerItemMapModel--getItem()-- " + e);
        }
        return list;
    }

    public List<String> getModel(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT model FROM model where active='Y' ORDER BY model ";
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
                list.add("No such model exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:ManufacturerItemMapModel--getModel()-- " + e);
        }
        return list;
    }

    public int getManufacturerId(String manufacturer_name) {
        String query = "SELECT manufacturer_id FROM manufacturer WHERE manufacturer_name = ? and active=?";
        int manufacturer_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, manufacturer_name);
            pstmt.setString(2, "Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            manufacturer_id = rset.getInt("manufacturer_id");
        } catch (Exception e) {
            System.out.println("Error: ManufacturerItemMapModel getManufacturerId--" + e);
        }
        return manufacturer_id;
    }

    public int getItemId(String item_name) {
        String query = "SELECT item_names_id FROM item_names WHERE item_name = ? and active=?";
        int item_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, item_name);
            pstmt.setString(2, "Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            item_id = rset.getInt("item_names_id");
        } catch (Exception e) {
            System.out.println("Error: ManufacturerItemMapModel getItemId--" + e);
        }
        return item_id;
    }

    public int getModelId(String model_name) {
        String query = "SELECT model_id FROM model WHERE model = ? and active=?";
        int model_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, model_name);
            pstmt.setString(2, "Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            model_id = rset.getInt("model_id");
        } catch (Exception e) {
            System.out.println("Error: ManufacturerItemMapModel getModelId--" + e);
        }
        return model_id;
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
            System.out.println("ManufacturerItemMapModel closeConnection() Error: " + e);
        }
    }
}
