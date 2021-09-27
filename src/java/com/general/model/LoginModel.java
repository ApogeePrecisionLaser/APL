/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Komal
 */
public class LoginModel {

    private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
    private String driverClass;
    private String connectionString;
    private String myRoleName, myDbUserName, myDbUserPass;
    // HttpSession session = request.getSession();

//public String checkLogin(String user_name,String password){
//    int login_id=0;
//    String designation="";
//     String query = " select login_id,d.designation,l.key_person_id from login l,key_person kp,designation as d where l.user_name='"+user_name+"' and l.password='"+password+"' "
//             + " AND kp.key_person_id=l.key_person_id and kp.designation_id=d.designation_id ";
//        try {
//            ResultSet rs = connection.prepareStatement(query).executeQuery();
//            if (rs.next()) {
//                login_id = rs.getInt("login_id");
//                designation=rs.getString("designation");
//                int  key_person_id=rs.getInt("key_person_id");
//            //   session.setAttribute("key_person_id", key_person_id);
//            }
//        } catch (Exception ex) {
//            System.out.println(ex);
//        }
//
//
//    return designation;
//
//}
    
    public void setUserFullDetail(String myUserName, String myUserPass) {
        String query = " SELECT u.user_id, user_name, user_password, ur.user_role_id, role_name, db_user_name, db_user_pass "
                + "  FROM `user` AS u, user_roles AS ur, user_role_map AS urm "
                + "  WHERE u.user_id = urm.user_id AND ur.user_role_id = urm.user_role_id "
                + "  AND user_name = ? AND user_password = ? ";
        try {
            Class.forName(driverClass);
            //Connection con = DriverManager.getConnection(connectionString, "guest", "guest");
            Connection con = DriverManager.getConnection(connectionString, "jpss", "jpss");
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, myUserName);
            pst.setString(2, myUserPass);
            ResultSet rst = pst.executeQuery();
            while (rst.next()) {
                myRoleName = rst.getString("role_name");
                myDbUserName = rst.getString("db_user_name");
                myDbUserPass = rst.getString("db_user_pass");
            }
            con.close();
        } catch (Exception e) {
            System.out.println("LoginModel getUseName() Error: " + e);
        }
    }
    
    
    public int checkLogin(String user_name, String password) {
        int login_id = 0;
        String designation = "";
        int count = 0;
//     String query = " select login_id,d.designation,l.key_person_id from login l,key_person kp,designation as d where l.user_name='"+user_name+"' and l.password='"+password+"' "
//             + " AND kp.key_person_id=l.key_person_id and kp.designation_id=d.designation_id ";
        //String query = " select user_name,password from login where user_name='" + user_name + "' and password='" + password + "' ";
        String query = " select user_name,user_password from user where user_name='" + user_name + "' and user_password='" + password + "' ";
        try {
            Connection con = DriverManager.getConnection(connectionString, myDbUserName, myDbUserPass);
            ResultSet rs = con.prepareStatement(query).executeQuery();
            if (rs.next()) {
                designation = rs.getString(2);
                count++;
                //   session.setAttribute("key_person_id", key_person_id);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return count;

    }

    public String getDesignation(String user_name, String password) {
        String str = "";
        PreparedStatement pstmt;
        ResultSet rst;
        String query = "select d.designation from designation d, key_person kp, login l "
                + " where l.user_name='" + user_name + "' and l.password='" + password + "' "
                + " and l.key_person_id=kp.key_person_id and kp.designation_id=d.designation_id and d.active='Y' ";
        try {
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(query);
            rst = pstmt.executeQuery();
            while (rst.next()) {
                str = rst.getString(1);
            }
        } catch (Exception e) {
            System.out.println("getDesignation ERROR inside orderMgmtModel - " + e);
        }
        return str;
    }

    public int getKeyPersonId(String user_name, String password) {
        int str = 0;
        PreparedStatement pstmt;
        ResultSet rst;
        String query = "select distinct kp.key_person_id from designation d, key_person kp, login l "
                + " where l.user_name='" + user_name + "' and l.password='" + password + "' "
                + " and l.key_person_id=kp.key_person_id and d.active='Y' ";
        try {
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(query);
            rst = pstmt.executeQuery();
            while (rst.next()) {
                str = rst.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("getKeyPersonId ERROR inside LoginModel - " + e);
        }
        return str;
    }

    public int getOrgOfficeId(String user_name, String password) {
        int str = 0;
        PreparedStatement pstmt;
        ResultSet rst;
        String query = "select oo.org_office_id from org_office oo, key_person kp, login l "
                + " where l.user_name='" + user_name + "' and l.password='" + password + "' "
                + " and l.key_person_id=kp.key_person_id and kp.org_office_id=oo.org_office_id and oo.active='Y'; ";
        try {
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(query);
            rst = pstmt.executeQuery();
            while (rst.next()) {
                str = rst.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("getOrgOfficeId ERROR inside LoginModel - " + e);
        }
        return str;
    }

    public String getOrgOffice(String user_name, String password) {
        String str = "";
        PreparedStatement pstmt;
        ResultSet rst;
        String query = "select oo.org_office_name from org_office oo, key_person kp, login l "
                + " where l.user_name='" + user_name + "' and l.password='" + password + "' "
                + " and l.key_person_id=kp.key_person_id and kp.org_office_id=oo.org_office_id and oo.active='Y'; ";
        try {
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(query);
            rst = pstmt.executeQuery();
            while (rst.next()) {
                str = rst.getString(1);
            }
        } catch (Exception e) {
            System.out.println("getOrgOfficeId ERROR inside LoginModel - " + e);
        }
        return str;
    }
    
    public String getOfficeAdmin(String user_name, String password, int logged_org_office_id) {
        String str = "";
        PreparedStatement pstmt;
        ResultSet rst;
        String query = " select kp.key_person_name from org_office oo, key_person kp,designation d "
                + " where kp.org_office_id=oo.org_office_id and  oo.org_office_id='"+logged_org_office_id+"' and kp.designation_id=d.designation_id "
                + " and d.designation='Owner' and  oo.active='Y' and kp.active='Y' ";
        try {
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(query);
            rst = pstmt.executeQuery();
            while (rst.next()) {
                str = rst.getString(1);
            }
        } catch (Exception e) {
            System.out.println("getOfficeAdmin ERROR inside LoginModel - " + e);
        }
        return str;
    }

    public int getOrgNameId(String user_name, String password) {
        int str = 0;
        PreparedStatement pstmt;
        ResultSet rst;
        String query = "select oo.organisation_id from org_office oo, key_person kp, login l "
                + " where l.user_name='" + user_name + "' and l.password='" + password + "' "
                + " and l.key_person_id=kp.key_person_id and kp.org_office_id=oo.org_office_id and oo.active='Y'; ";
        try {
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(query);
            rst = pstmt.executeQuery();
            while (rst.next()) {
                str = rst.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("getDesignation ERROR inside LoginModel - " + e);
        }
        return str;
    }

    public String getOrgName(String user_name, String password) {
        String str = "";
        PreparedStatement pstmt;
        ResultSet rst;
        String query = "select orgn.organisation_name from org_office oo, key_person kp, login l,organisation_name orgn "
                + " where l.user_name='" + user_name + "' and l.password='" + password + "' "
                + " and l.key_person_id=kp.key_person_id and kp.org_office_id=oo.org_office_id and orgn.organisation_id=oo.organisation_id "
                + " and oo.active='Y' and orgn.active='Y' ";
        System.err.println("query" + query);
        try {
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(query);
            rst = pstmt.executeQuery();
            while (rst.next()) {
                str = rst.getString(1);
            }
        } catch (Exception e) {
            System.out.println("getOrgName ERROR inside LoginModel - " + e);
        }
        return str;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public void setMsgBgColor(String msgBgColor) {
        this.msgBgColor = msgBgColor;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println(" closeConnection() Error: " + e);
        }
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }
    
    public String getMyDbUserName() {
        return myDbUserName;
    }

    public void setMyDbUserName(String myDbUserName) {
        this.myDbUserName = myDbUserName;
    }

    public String getMyDbUserPass() {
        return myDbUserPass;
    }

    public void setMyDbUserPass(String myDbUserPass) {
        this.myDbUserPass = myDbUserPass;
    }

    public String getMyRoleName() {
        return myRoleName;
    }

    public void setMyRoleName(String myRoleName) {
        this.myRoleName = myRoleName;
    }
    
}
