
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.model;

import com.google.gson.JsonObject;
import static com.lowagie.text.pdf.PdfFileSpecification.url;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Komal
 */
public class LoginModel {

    public static String otpMap = null;
    private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "green";
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
            connection = DriverManager.getConnection(connectionString, "jpss", "jpss");
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, myUserName);
            pst.setString(2, myUserPass);
            ResultSet rst = pst.executeQuery();
            while (rst.next()) {
                myRoleName = rst.getString("role_name");
                myDbUserName = rst.getString("db_user_name");
                myDbUserPass = rst.getString("db_user_pass");
            }
            //connection.close();
        } catch (Exception e) {
            System.out.println("LoginModel setUserFullDetail() Error: " + e);
        }
    }

    public int checkLogin(String user_name, String password) {
        int login_id = 0;
        String designation = "";
        int count = 0;
//     String query = " select login_id,d.designation,l.key_person_id from login l,key_person kp,designation as d where l.user_name='"+user_name+"' and l.password='"+password+"' "
//             + " AND kp.key_person_id=l.key_person_id and kp.designation_id=d.designation_id ";
        //String query = " select user_name,password from login where user_name='" + user_name + "' and password='" + password + "' ";
        String query = " select user_name,user_password from user where user_name='" + user_name + "' and user_password='" + password + "' "
                + " and active='Y'";
        try {
            //Connection con = DriverManager.getConnection(connectionString, myDbUserName, myDbUserPass);
            Connection con = DriverManager.getConnection(connectionString, "jpss", "jpss");
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

    public String getUserName(String email) {
        String str = "";
        PreparedStatement pstmt;
        ResultSet rst;
        String query = " select distinct kp.key_person_name from key_person kp where kp.active='Y' ";
//        if (!mobile.equals("") && mobile != null) {
//            query += " and kp.mobile_no1='" + mobile + "' ";
//        }
        if (!email.equals("") && email != null) {
            query += " and kp.email_id1='" + email + "' or kp.mobile_no1='" + email + "' ";
        }
        try {
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(query);
            rst = pstmt.executeQuery();
            while (rst.next()) {
                str = rst.getString(1);
            }

        } catch (Exception e) {
            System.out.println("getUserName ERROR inside LoginModel - " + e);
        }
        return str;
    }

    public String getDesignation(String user_name, String password) {
        String str = "";
        PreparedStatement pstmt;
        ResultSet rst;
//        String query = "select d.designation from designation d, key_person kp, login l "
//                + " where l.user_name='" + user_name + "' and l.password='" + password + "' "
//                + " and l.key_person_id=kp.key_person_id and kp.designation_id=d.designation_id and d.active='Y' ";

        String query = " select  d.designation from designation d, key_person kp, user l "
                + " where l.user_name='" + user_name + "' and l.user_password='" + password + "' and kp.active='y' "
                + " and l.key_person_id=kp.key_person_id and kp.designation_id=d.designation_id and d.active='Y' ";
        try {
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(query);
            rst = pstmt.executeQuery();
            while (rst.next()) {
                str = rst.getString(1);
            }
        } catch (Exception e) {
            System.out.println("getDesignation ERROR inside LoginModel - " + e);
        }
        return str;
    }

    public int getKeyPersonId(String user_name, String password) {
        int str = 0;
        PreparedStatement pstmt;
        ResultSet rst;
        String query = "select distinct kp.key_person_id from designation d, key_person kp, user l "
                + " where l.user_name='" + user_name + "' and l.user_password='" + password + "' "
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

    public int getUserId(String user_name, String password) {
        int str = 0;
        PreparedStatement pstmt;
        ResultSet rst;
        String query = " select distinct l.user_id from user l "
                + " where l.user_name='" + user_name + "' and l.user_password='" + password + "' and "
                + " l.active='Y' ";
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
        String query = "select oo.org_office_id from org_office oo, key_person kp, user l "
                + " where l.user_name='" + user_name + "' and l.user_password='" + password + "' "
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
        String query = "select oo.org_office_name from org_office oo, key_person kp, user l "
                + " where l.user_name='" + user_name + "' and l.user_password='" + password + "' "
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

    public String getMobile(String user_name, String password) {
        String str = "";
        PreparedStatement pstmt;
        ResultSet rst;
        String query = "select distinct kp.mobile_no1 from designation d, key_person kp, user l "
                + " where l.user_name='" + user_name + "' and l.user_password='" + password + "' "
                + " and l.key_person_id=kp.key_person_id and d.active='Y' ";
        try {
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(query);
            rst = pstmt.executeQuery();
            while (rst.next()) {
                str = rst.getString(1);
            }
        } catch (Exception e) {
            System.out.println("getMobile ERROR inside LoginModel - " + e);
        }
        return str;
    }

    public String getOfficeAdmin(String user_name, String password, int logged_org_office_id, String designation) {
        String str = "";
        String query = "";
        PreparedStatement pstmt;
        ResultSet rst;
        if (designation.equals("technician")) {
            query = " select kp.key_person_name from org_office oo, key_person kp,designation d "
                    + " where kp.org_office_id=oo.org_office_id and  oo.org_office_id='" + logged_org_office_id + "' and kp.designation_id=d.designation_id "
                    + " and d.designation='Embedded Developer' and  oo.active='Y' and kp.active='Y' ";
        } else {
            query = " select kp.key_person_name from org_office oo, key_person kp,designation d "
                    + " where kp.org_office_id=oo.org_office_id and  oo.org_office_id='" + logged_org_office_id + "' and kp.designation_id=d.designation_id "
                    + " and d.designation='Owner' and  oo.active='Y' and kp.active='Y' ";
        }

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

//    public String getOfficeEmbeddedDeveloper(String user_name, String password, int logged_org_office_id) {
//        String str = "";
//        PreparedStatement pstmt;
//        ResultSet rst;
//        String query = " select kp.key_person_name from org_office oo, key_person kp,designation d "
//                + " where kp.org_office_id=oo.org_office_id and  oo.org_office_id='" + logged_org_office_id + "' "
//                + " and kp.designation_id=d.designation_id "
//                + " and d.designation='Embeded Software Developer' and  oo.active='Y' and kp.active='Y' ";
//        try {
//            connection.setAutoCommit(false);
//            pstmt = connection.prepareStatement(query);
//            rst = pstmt.executeQuery();
//            while (rst.next()) {
//                str = rst.getString(1);
//            }
//        } catch (Exception e) {
//            System.out.println("getOfficeAdmin ERROR inside LoginModel - " + e);
//        }
//        return str;
//    }
    public int getOrgNameId(String user_name, String password) {
        int str = 0;
        PreparedStatement pstmt;
        ResultSet rst;
        String query = "select oo.organisation_id from org_office oo, key_person kp, user l "
                + " where l.user_name='" + user_name + "' and l.user_password='" + password + "' "
                + " and l.key_person_id=kp.key_person_id and kp.org_office_id=oo.org_office_id and oo.active='Y'; ";
        try {
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(query);
            rst = pstmt.executeQuery();
            while (rst.next()) {
                str = rst.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("getOrgNameId ERROR inside LoginModel - " + e);
        }
        return str;
    }

    public String getOrgName(String user_name, String password) {
        String str = "";
        PreparedStatement pstmt;
        ResultSet rst;
        String query = "select orgn.organisation_name from org_office oo, key_person kp, user l,organisation_name orgn "
                + " where l.user_name='" + user_name + "' and l.user_password='" + password + "' "
                + " and l.key_person_id=kp.key_person_id and kp.org_office_id=oo.org_office_id and orgn.organisation_id=oo.organisation_id "
                + " and oo.active='Y' and orgn.active='Y' ";
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

    public int getCount(int logged_key_person_id) {
        int count = 0;
        PreparedStatement pstmt;
        ResultSet rst;
        String query = " select count(*) as count from user where key_person_id='" + logged_key_person_id + "' ";
        try {
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(query);
            rst = pstmt.executeQuery();
            while (rst.next()) {
                count = rst.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("getCount ERROR inside LoginModel - " + e);
        }
        return count;
    }

    public static int getRevisionno(int key_id) {
        int revision = 0;
        try {

            String query = " SELECT max(rev_no) as rev_no FROM user WHERE key_person_id =" + key_id + "  && active='Y';";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("rev_no");

            }
        } catch (Exception e) {
            System.out.println("LoginModel getRevisionno error: " + e);

        }
        return revision;
    }

    public static int getOtpRevisionno(String user_id) {
        int revision = 0;
        try {

            String query = " SELECT max(rev_no) as rev_no FROM otp WHERE user_id =" + user_id + "  && active='Y';";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("rev_no");

            }
        } catch (Exception e) {
            System.out.println("LoginModel getOtpRevisionno error: " + e);

        }
        return revision;
    }

    public int updateUser(int logged_key_person_id, String user_name, String password, int user_id) throws SQLException {
        int revision = LoginModel.getRevisionno(logged_key_person_id);
        int updateRowsAffected = 0;
        boolean status = false;

        String query1 = " SELECT max(rev_no) rev_no FROM user WHERE key_person_id = " + logged_key_person_id + "  && active=? ";
        String query2 = " UPDATE user SET active=? WHERE key_person_id=? and rev_no=? ";
        String query3 = " INSERT INTO user(user_id,user_name, user_password, registration_date, key_person_id,active,rev_no) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?)";

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        String date_time = sdf.format(date);
        try {
            connection.setAutoCommit(false);

            PreparedStatement pstmt = connection.prepareStatement(query1);
            pstmt.setString(1, "Y");
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                PreparedStatement pstm = connection.prepareStatement(query2);
                pstm.setString(1, "n");
                pstm.setInt(2, logged_key_person_id);
                pstm.setInt(3, revision);
                updateRowsAffected = pstm.executeUpdate();
                if (updateRowsAffected >= 1) {
                    revision = rs.getInt("rev_no") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                    psmt.setInt(1, user_id);
                    psmt.setString(2, user_name);
                    psmt.setString(3, password);
                    psmt.setString(4, date_time);
                    psmt.setInt(5, logged_key_person_id);
                    psmt.setString(6, "Y");
                    psmt.setInt(7, revision);

                    updateRowsAffected = psmt.executeUpdate();
                    if (updateRowsAffected > 0) {
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

            }
        } catch (Exception e) {
            System.out.println("Error:KeypersonModel updateRecord-" + e);
        }
        if (updateRowsAffected > 0) {
            message = "Record updated successfully.";
            msgBgColor = COLOR_OK;

        } else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;

        }
        return updateRowsAffected;
    }

    public String sendOTP(String mobile_no, String user_id) throws Exception {
        String result = "";
        String otp = "";
        otp = random(6);
        otpMap = (otp);
        int rowsAffected = 0;
        int updateRowsAffected = 0;
        int count = 0;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        System.err.println("your otp is----" + otpMap);
        try {
            connection.setAutoCommit(false);

            int revision = getOtpRevisionno(user_id);

            String query1 = " SELECT max(rev_no) rev_no,count(*) as count  FROM otp WHERE user_id = " + user_id + "  && active=? ";
            String query2 = " UPDATE otp SET active=? WHERE user_id=? and rev_no=? ";
            String query_insert = " insert into otp(user_id,otp,active,rev_no) values(?,?,?,?) ";

            psmt = connection.prepareStatement(query1);
            psmt.setString(1, "Y");
            rs = psmt.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");

                if (count > 0) {
                    PreparedStatement pstm = connection.prepareStatement(query2);
                    pstm.setString(1, "n");
                    pstm.setString(2, user_id);
                    pstm.setInt(3, revision);
                    updateRowsAffected = pstm.executeUpdate();
                    if (updateRowsAffected >= 1) {
                        revision = rs.getInt("rev_no") + 1;
                        psmt = null;
                        psmt = (PreparedStatement) connection.prepareStatement(query_insert);
                        psmt.setString(1, user_id);
                        psmt.setString(2, otp);
                        psmt.setString(3, "Y");
                        psmt.setInt(4, revision);

                        rowsAffected = psmt.executeUpdate();
                    }

                } else {
                    psmt = null;
                    psmt = (PreparedStatement) connection.prepareStatement(query_insert);
                    psmt.setString(1, user_id);
                    psmt.setString(2, otp);
                    psmt.setString(3, "Y");
                    psmt.setInt(4, 0);

                    rowsAffected = psmt.executeUpdate();
                }
            }
            if (rowsAffected > 0) {
                result = otpMap;
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (Exception e) {
            System.err.println("exception---" + e);
        }

//        result = otpMap;
//        result = callURL(mobile_no, "Your OTP is:" + otp);
        System.out.println("Data Retrived : " + mobile_no);
        return result;
    }

    public String random(int size) {
        StringBuilder generatedToken = new StringBuilder();
        try {
            SecureRandom number = SecureRandom.getInstance("SHA1PRNG");
            // Generate 20 integers 0..20
            for (int i = 0; i < size; i++) {
                generatedToken.append(number.nextInt(9));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return generatedToken.toString();
    }

    private String callURL(String numberStr1, String messageStr1) {
        String status = "";

        URLConnection myURLConnection = null;
        URL myURL = null;
        BufferedReader reader = null;

        String encoded_message = URLEncoder.encode("JP SAFTEK SYSTEMS: Dear Partner, This is the confirmation message of your query." + messageStr1);
//        String decoded_message = URLDecoder.decode(encoded_message);

        String mainUrl = "https://www.smsgatewayhub.com/api/mt/SendSMS?";

        StringBuilder sbPostData = new StringBuilder(mainUrl);
        sbPostData.append("APIKey=" + "WIOg7OdIzkmYTrqTsw262w");
        sbPostData.append("&senderid=" + "JPSCRM");
        sbPostData.append("&channel=" + 2);
        sbPostData.append("&DCS=" + 0);
        sbPostData.append("&flashsms=" + 0);
        sbPostData.append("&number=" + numberStr1);
        sbPostData.append("&text=" + encoded_message);
        sbPostData.append("&route=" + 1);

        mainUrl = sbPostData.toString();
        try {
            myURL = new URL(mainUrl);
            myURLConnection = myURL.openConnection();
            myURLConnection.connect();
            reader = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
            String line = null;
            String response = new String();
            while ((line = reader.readLine()) != null) //print response
            {
                response += line;;

            }
            org.codehaus.jettison.json.JSONObject jsonObj = new org.codehaus.jettison.json.JSONObject(response);
            status = jsonObj.get("ErrorMessage").toString();

            reader.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        }
        return status;
    }

    public String verifyOTP(String mobile_no_otp, String user_id) throws Exception {
        org.codehaus.jettison.json.JSONObject jsonObj = new org.codehaus.jettison.json.JSONObject();
        String result = "";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String current_date_time = sdf.format(date);
        String otp = "";
        String created_date = "";

        String query = " select otp,created_date from otp where active='Y' and user_id='" + user_id + "' ";
        PreparedStatement psmt = connection.prepareStatement(query);
        ResultSet rs = psmt.executeQuery();
        while (rs.next()) {
            otp = rs.getString("otp");
            created_date = rs.getString("created_date");
        }
        int minutes = 0;
        psmt = null;
        rs = null;

        String query1 = " SELECT TIMESTAMPDIFF(MINUTE, '" + created_date + "', '" + current_date_time + "') as minutes";
        psmt = connection.prepareStatement(query1);
        rs = psmt.executeQuery();
        while (rs.next()) {
            minutes = rs.getInt("minutes");
        }

        if (minutes > 3) {
            result = "OTP Expired. Please resend the OTP.";
        } else {
            String mobile_no = mobile_no_otp.split("_")[0];
//        if (mobile_no.equals(otpMap)) {
            if (mobile_no.equals(otp)) {
                result = "success";
            } else {
                result = "fail";
            }
        }

        System.out.println("Data Retrived : " + jsonObj);
        return result;
    }

    public int verifyMobile(String mobile) {
        int login_id = 0;
        String designation = "";
        int count = 0;
        String query = " select user_name,user_password from user u,key_person kp "
                + " where u.key_person_id=kp.key_person_id and kp.active='Y' and u.active='Y' and kp.mobile_no1='" + mobile + "' ";
        try {
            //Connection con = DriverManager.getConnection(connectionString, myDbUserName, myDbUserPass);
            Connection con = DriverManager.getConnection(connectionString, "jpss", "jpss");
            ResultSet rs = con.prepareStatement(query).executeQuery();
            if (rs.next()) {
                designation = rs.getString(2);
                count++;
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return count;

    }

    public String getKeyPersonData(String mobile) {
        String str = "";
        PreparedStatement pstmt;
        ResultSet rst;
        String query = "select u.key_person_id,u.user_id,u.user_name from user u,key_person kp "
                + " where u.key_person_id=kp.key_person_id and kp.active='Y' and u.active='Y' and kp.mobile_no1='" + mobile + "'";
        try {
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(query);
            rst = pstmt.executeQuery();
            while (rst.next()) {
                int key_person_id = rst.getInt(1);
                int user_id = rst.getInt(2);
                String user_name = rst.getString(3);
                str = key_person_id + "&" + user_id + "&" + user_name;
            }
        } catch (Exception e) {
            System.out.println("getKeyPersonId ERROR inside LoginModel - " + e);
        }
        return str;
    }

    public int updatePassword(String mobile, String newPassword) throws SQLException {

        int rowsAffected = 0;
        int count = 0;
        int updateRowsAffected = 0;
        Boolean status = false;
        String key_person_data = getKeyPersonData(mobile);
        String kp_arr[] = key_person_data.split("&");
        int key_person_id = Integer.parseInt(kp_arr[0]);
        int user_id = Integer.parseInt(kp_arr[1]);
        String user_name = kp_arr[2];

        int revision = getRevisionno(key_person_id);

        String query1 = " SELECT max(rev_no) rev_no FROM user WHERE key_person_id = " + key_person_id + "  && active=? ";
        String query2 = " UPDATE user SET active=? WHERE key_person_id=? and rev_no=? ";
        String query3 = " INSERT INTO user(user_id,user_name, user_password, registration_date, key_person_id,active,rev_no) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?)";

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        String date_time = sdf.format(date);
        try {
            connection.setAutoCommit(false);

            PreparedStatement pstmt = connection.prepareStatement(query1);
            pstmt.setString(1, "Y");
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                PreparedStatement pstm = connection.prepareStatement(query2);
                pstm.setString(1, "n");
                pstm.setInt(2, key_person_id);
                pstm.setInt(3, revision);
                updateRowsAffected = pstm.executeUpdate();
                if (updateRowsAffected >= 1) {
                    revision = rs.getInt("rev_no") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                    psmt.setInt(1, user_id);
                    psmt.setString(2, user_name);
                    psmt.setString(3, newPassword);
                    psmt.setString(4, date_time);
                    psmt.setInt(5, key_person_id);
                    psmt.setString(6, "Y");
                    psmt.setInt(7, revision);

                    updateRowsAffected = psmt.executeUpdate();
                    if (updateRowsAffected > 0) {
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

            }
        } catch (Exception e) {
            System.out.println("Error:KeypersonModel updateRecord-" + e);
        } finally {

        }
        return updateRowsAffected;
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
