/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.website.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author komal
 */
public class ContactUsModel {

    private Connection connection;
    private String driver, url, user, password;
    private String message, messageBGColor;
    static List<String> printerList;
    List<String> list = new ArrayList<String>();

//    public List<String> getDealersList(String latitude, String longitude) {
//        List<String> list = new ArrayList<String>();
//
//        int count = 0;
//        String query = " SELECT org_office_name from org_office where office_type_id=3 ";
//        try {
//            PreparedStatement pstmt = connection.prepareStatement(query);
//            ResultSet rset = pstmt.executeQuery();
//            while (rset.next()) {
//                String org_office_name = (rset.getString("org_office_name"));
//
//                list.add(org_office_name);
//                count++;
//
//            }
//            if (count == 0) {
//                list.add("No such org_office_name exists.");
//            }
//        } catch (Exception e) {
//            System.out.println("Error:ContactUsModel --getDealersList-- " + e);
//        }
//        return list;
//    }
    public JSONArray getDealersList(String latitude, String longitude) {
        JSONObject obj = new JSONObject();
        JSONArray arrayObj = new JSONArray();
        String data = "";

        List list = new ArrayList();
        String dealer_name = "";
        try {
//            String query = " SELECT org_office_name from org_office where office_type_id=3 group by org_office_name order by org_office_name";
            String query = " SELECT org_office_id, org_office_name, latitude, longitude,"
                    + " 111.045 * DEGREES(ACOS(COS(RADIANS(" + latitude + ")) * COS(RADIANS(latitude)) * COS(RADIANS(longitude) - RADIANS(" + longitude + ")) + SIN(RADIANS(" + latitude + ")) * SIN(RADIANS(latitude)))) "
                    + " AS distance_in_km "
                    + " FROM org_office where active='Y' "
                    + " ORDER BY distance_in_km ASC limit 10";
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                JSONObject jsonObj = new JSONObject();
                dealer_name = rset.getString("org_office_name");

                jsonObj.put("dealer_name", dealer_name);
                arrayObj.add(jsonObj);
            }

        } catch (Exception e) {
            System.err.println("Exception in getDealersList---------" + e);
        }

        return arrayObj;

    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("ContactUsModel closeConnection: " + e);
        }
    }

    public void setConnection(Connection con) {
        try {
            connection = con;
        } catch (Exception e) {
            System.out.println("DesignationModel setConnection() Error: " + e);
        }
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
