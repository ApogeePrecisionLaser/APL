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
 * @author Tarun
 */
public class ItemTypeModel {

    private static Connection connection;
    private Connection connection2;     
    private String driver, url, user, password;

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

        // Use DESC or ASC for descending or ascending order respectively of fetched data.
        String query = " SELECT item_type_id, item_type,  description FROM item_type where active='Y' ";

        if (!searchItemType.equals("") && searchItemType != null) {
            query += " and item_type='" + searchItemType + "' ";
        }

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setString(1, active);
//            pstmt.setString(2, searchOrgOfficeType);
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
        boolean status = false;
        String query1 = "SELECT max(revision_no) revision_no FROM item_type WHERE item_type_id = " + item_type_id + "  && active=? ";
        String query2 = "UPDATE item_type SET active =? WHERE item_Type_id =? and revision_no=? ";
        String query3 = "INSERT INTO item_type(item_type_id,item_type,description,revision_no,active,remark) VALUES(?,?,?,?,?,?)";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
//           pstmt.setInt(1,organisation_type_id);
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
                    rowsAffected = psmt.executeUpdate();
                    if (rowsAffected > 0) {
                        status = true;
                    } else {
                        status = false;
                    }
                }

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
        }
        return revision;
    }

    public int deleteRecord(int item_type_id) {
        String query = "DELETE FROM item_type WHERE active='Y' and item_type_id = " + item_type_id;
        int rowsAffected = 0;
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
            while (rset.next()) {    // move cursor from BOR to valid record.
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
    
    public void setConnection2() {
        try {
            Class.forName(driver);
            connection2 = DriverManager.getConnection(url, user, password);
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("CityModel setConnection error: " + e);
        }
    }

    public Connection getConnection2() {
        connection2=connection;
        return connection2;
    }

    /**
     * @return the driver
     */
    public String getDriver() {
        return driver;
    }

    /**
     * @param driver the driver to set
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
}
