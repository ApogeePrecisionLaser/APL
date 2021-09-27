/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.model;

import com.inventory.tableClasses.Manufacturer;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;

/**
 *
 * @author Komal
 */
public class ManufacturerModel {

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

    public List<Manufacturer> showData(String searchManufacturer, String active) {
        List<Manufacturer> list = new ArrayList<Manufacturer>();

        
        String query = " SELECT manufacturer_id, manufacturer_name,description FROM manufacturer where active='Y' ";

        if (!searchManufacturer.equals("") && searchManufacturer != null) {
            query += " and manufacturer_name='" + searchManufacturer + "' ";
        }

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                Manufacturer bean = new Manufacturer();
                bean.setManufacturer_id(rset.getInt("manufacturer_id"));
                bean.setManufacturer_name((rset.getString("manufacturer_name")));
                bean.setDescription((rset.getString("description")));

                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("ManufacturerModel showData() Error: " + e);
        }
        return list;
    }

    public int insertRecord(Manufacturer manufacturer) {
        String query = "INSERT INTO manufacturer(manufacturer_name, description,revision_no,active,remark) VALUES(?,?,?,?,?) ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, (manufacturer.getManufacturer_name()));
            pstmt.setString(2, (manufacturer.getDescription()));
            pstmt.setInt(3, manufacturer.getRevision_no());
            pstmt.setString(4, "Y");
            pstmt.setString(5, "OK");

            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("ManufacturerModel insertRecord() Error: " + e);
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

    public int updateRecord(Manufacturer manufacturer, int manufacturer_id) {
        int revision = ManufacturerModel.getRevisionno(manufacturer, manufacturer_id);
        int updateRowsAffected = 0;
        boolean status = false;
        String query1 = "SELECT max(revision_no) revision_no FROM manufacturer WHERE manufacturer_id = " + manufacturer_id + "  && active='Y' ";
        String query2 = "UPDATE manufacturer SET active =? WHERE manufacturer_id =? and revision_no=? ";
        String query3 = "INSERT INTO manufacturer(manufacturer_id,manufacturer_name,description,revision_no,active,remark) VALUES(?,?,?,?,?,?)";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                PreparedStatement pstm = connection.prepareStatement(query2);
                pstm.setString(1, "n");
                pstm.setInt(2, manufacturer_id);
                pstm.setInt(3, revision);
                updateRowsAffected = pstm.executeUpdate();
                if (updateRowsAffected >= 1) {
                    revision = rs.getInt("revision_no") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                    psmt.setInt(1, (manufacturer_id));
                    psmt.setString(2, manufacturer.getManufacturer_name());
                    psmt.setString(3, manufacturer.getDescription());
                    psmt.setInt(4, revision);
                    psmt.setString(5, "Y");
                    psmt.setString(6, "OK");
                    rowsAffected = psmt.executeUpdate();
                    if (rowsAffected > 0) {
                        status = true;
                    } else {
                        status = false;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("ManufacturerModel updateRecord() Error: " + e);
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

    public static int getRevisionno(Manufacturer bean, int manufacturer_id) {
        int revision = 0;
        try {

            String query = " SELECT max(revision_no) as revision_no FROM manufacturer WHERE manufacturer_id =" + manufacturer_id + " "
                    + " && active='Y';";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
        }
        return revision;
    }

    public int deleteRecord(int manufacturer_id) {
        String query = "DELETE FROM manufacturer WHERE active='Y' and manufacturer_id = " + manufacturer_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("ManufacturerModel deleteRecord() Error: " + e);
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
        String query = " SELECT manufacturer_id, manufacturer_name FROM manufacturer  where active='Y' ORDER BY manufacturer_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
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
            System.out.println("ManufacturerModel getManufacturer ERROR - " + e);
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
}
