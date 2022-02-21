
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.report.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.BasicAuthentication;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ClearValuesRequest;
import com.google.api.services.sheets.v4.model.ClearValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.report.bean.DailyEnquiryReport;
import com.report.bean.DealersReport;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.function.Supplier;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import org.json.simple.JSONObject;

/**
 *
 * @author Komal
 */
public class DailyEnquiryReportModel {

    private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "#a2a220";
    private final String COLOR_ERROR = "red";
    int kp_id;

    public static void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("DealersReportModel setConnection() Error: " + e);
        }
    }

//    public List<DailyEnquiryReport> showData(String task) {
//        List<DailyEnquiryReport> list = new ArrayList<DailyEnquiryReport>();
//        ArrayList<DailyEnquiryReport> dataList = new ArrayList<DailyEnquiryReport>();
//
//        try {
////            PreparedStatement pstmt = connection.prepareStatement(query);
////            ResultSet rset = pstmt.executeQuery();
////            while (rset.next()) {
//            DailyEnquiryReport bean = new DailyEnquiryReport();
//            bean.setCurrent_date("19-02-2022");
//            bean.setTotal_query_of_current_date("10");
//            bean.setOpen_query_of_current_date("3");
//            bean.setClosed_query_of_current_date("7");
//            bean.setSold_query_of_current_date("1");
//            bean.setTotal_query_till_date("50");
//            bean.setOpen_query_till_date("8");
//            bean.setClosed_query_till_date("42");
//            bean.setSold_query_till_date("2");
//            dataList.add(bean);
////            }
//
//        } catch (Exception e) {
//            System.out.println("Error in DealersReportModel showData -- " + e);
//
//        }
//
//        return dataList;
//
//    }
    public static List<DailyEnquiryReport> getData(String from_date, String to_date, String user_role, int key_person_id) {
        List<DailyEnquiryReport> list = new ArrayList<DailyEnquiryReport>();
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String current_date = "";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");

        if (from_date.equals("")) {
            from_date = sdf.format(date);

            current_date = sdf.format(date);
        }
        if (to_date.equals("")) {
            to_date = sdf.format(date);
        }
        if (!from_date.equals("") || !to_date.equals("")) {
            current_date = "(" + from_date + " to " + to_date + ")";
        }
        int count_of_total_query_of_current_date = 0;
        int count_of_total_query_till_date = 0;
        int count_of_open_query_of_current_date = 0;
        int count_of_open_query_till_date = 0;
        int count_of_closed_query_of_current_date = 0;
        int count_of_closed_query_till_date = 0;
        int count_of_sold_query_of_current_date = 0;
        int count_of_sold_query_till_date = 0;

        Date d1 = null;
        Date d2 = null;

        try {
            String total_query_of_current_date = " select * from enquiry_table where active='Y' ";
            if (!"".equals(from_date)) {
                total_query_of_current_date += " and date(created_at)>='" + from_date + "' ";
            }
            if (!"".equals(to_date)) {
                total_query_of_current_date += " and date(created_at)<='" + to_date + "' ";
            }
            if (!user_role.equals("Admin")) {
                total_query_of_current_date += " and (assigned_to='" + key_person_id + "' or assigned_by='" + key_person_id + "') ";
            }

            String total_query_till_date = " select * from enquiry_table where active='Y' ";
            if (!user_role.equals("Admin")) {
                total_query_till_date += " and (assigned_to='" + key_person_id + "' or assigned_by='" + key_person_id + "') ";
            }

            String open_query_of_current_date = " select * from enquiry_table where active='Y' "
                    + " and enquiry_status_id in(1,2,3,4,13,14,15) ";
            if (!"".equals(from_date)) {
                open_query_of_current_date += " and date(created_at)>='" + from_date + "' ";
            }
            if (!"".equals(to_date)) {
                open_query_of_current_date += " and date(created_at)<='" + to_date + "' ";
            }
            if (!user_role.equals("Admin")) {
                open_query_of_current_date += " and (assigned_to='" + key_person_id + "' or assigned_by='" + key_person_id + "') ";

            }

            String open_query_till_date = " select * from enquiry_table where active='Y' "
                    + " and enquiry_status_id in(1,2,3,4,13,14,15) ";

            if (!user_role.equals("Admin")) {
                open_query_till_date += " and (assigned_to='" + key_person_id + "' or assigned_by='" + key_person_id + "') ";

            }

            String closed_query_of_current_date = " select * from enquiry_table where active='Y' "
                    + " and enquiry_status_id in(16,17,18,19,20) ";
            if (!"".equals(from_date)) {
                closed_query_of_current_date += " and date(created_at)>='" + from_date + "' ";
            }
            if (!"".equals(to_date)) {
                closed_query_of_current_date += " and date(created_at)<='" + to_date + "' ";
            }
            if (!user_role.equals("Admin")) {
                closed_query_of_current_date += " and (assigned_to='" + key_person_id + "' or assigned_by='" + key_person_id + "') ";

            }

            String closed_query_till_date = " select * from enquiry_table where active='Y' "
                    + " and enquiry_status_id in(16,17,18,19,20) ";
            if (!user_role.equals("Admin")) {
                closed_query_till_date += " and (assigned_to='" + key_person_id + "' or assigned_by='" + key_person_id + "') ";

            }
            String sold_query_of_current_date = " select * from enquiry_table where active='Y' "
                    + " and enquiry_status_id in(16) ";
            if (!"".equals(from_date)) {
                sold_query_of_current_date += " and date(created_at)>='" + from_date + "' ";
            }
            if (!"".equals(to_date)) {
                sold_query_of_current_date += " and date(created_at)<='" + to_date + "' ";
            }
            if (!user_role.equals("Admin")) {
                sold_query_of_current_date += " and (assigned_to='" + key_person_id + "' or assigned_by='" + key_person_id + "') ";

            }

            String sold_query_till_date = " select * from enquiry_table where active='Y' "
                    + " and enquiry_status_id in(16) ";
            if (!user_role.equals("Admin")) {
                sold_query_till_date += " and (assigned_to='" + key_person_id + "' or assigned_by='" + key_person_id + "') ";

            }

            pstmt = connection.prepareStatement(total_query_of_current_date);
            rset = pstmt.executeQuery();
            rset.last();
            count_of_total_query_of_current_date = rset.getRow();
            rset.beforeFirst();

            pstmt = null;
            rset = null;
            pstmt = connection.prepareStatement(total_query_till_date);
            rset = pstmt.executeQuery();
            rset.last();
            count_of_total_query_till_date = rset.getRow();
            rset.beforeFirst();

            pstmt = null;
            rset = null;
            pstmt = connection.prepareStatement(open_query_of_current_date);
            rset = pstmt.executeQuery();
            rset.last();
            count_of_open_query_of_current_date = rset.getRow();
            rset.beforeFirst();

            pstmt = null;
            rset = null;
            pstmt = connection.prepareStatement(open_query_till_date);
            rset = pstmt.executeQuery();
            rset.last();
            count_of_open_query_till_date = rset.getRow();
            rset.beforeFirst();

            pstmt = null;
            rset = null;
            pstmt = connection.prepareStatement(closed_query_of_current_date);
            rset = pstmt.executeQuery();
            rset.last();
            count_of_closed_query_of_current_date = rset.getRow();
            rset.beforeFirst();

            pstmt = null;
            rset = null;
            pstmt = connection.prepareStatement(closed_query_till_date);
            rset = pstmt.executeQuery();
            rset.last();
            count_of_closed_query_till_date = rset.getRow();
            rset.beforeFirst();

            pstmt = null;
            rset = null;
            pstmt = connection.prepareStatement(sold_query_of_current_date);
            rset = pstmt.executeQuery();
            rset.last();
            count_of_sold_query_of_current_date = rset.getRow();
            rset.beforeFirst();

            pstmt = null;
            rset = null;
            pstmt = connection.prepareStatement(sold_query_till_date);
            rset = pstmt.executeQuery();
            rset.last();
            count_of_sold_query_till_date = rset.getRow();
            rset.beforeFirst();
            DailyEnquiryReport bean = new DailyEnquiryReport();
            bean.setCurrent_date(current_date);
            bean.setTotal_query_of_current_date(String.valueOf(count_of_total_query_of_current_date));
            bean.setTotal_query_till_date(String.valueOf(count_of_total_query_till_date));
            bean.setOpen_query_of_current_date(String.valueOf(count_of_open_query_of_current_date));
            bean.setOpen_query_till_date(String.valueOf(count_of_open_query_till_date));
            bean.setClosed_query_of_current_date(String.valueOf(count_of_closed_query_of_current_date));
            bean.setClosed_query_till_date(String.valueOf(count_of_closed_query_till_date));
            bean.setSold_query_of_current_date(String.valueOf(count_of_sold_query_of_current_date));
            bean.setSold_query_till_date(String.valueOf(count_of_sold_query_till_date));

            list.add(bean);

        } catch (Exception e) {
            System.err.println("Exception-----" + e);
        }
        return list;
    }

    public static String getMailId(String key_person_id) {
        String email = "";
        int count = 0;
        String query = " select email_id1 from key_person where key_person_id='" + key_person_id + "' and active='Y' ";

        try {

            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {    // move cursor from BOR to valid record.
                email = rs.getString("email_id1");

                count++;
            }

            if (count == 0) {
                System.out.println("No such email id exists");
            }
        } catch (Exception e) {
            System.out.println("getMailId() ERROR inside mailSchedular " + e);
        }
        return email;
    }

    public static String getPersonName(String key_person_id) {
        String key_person_name = "";
        int count = 0;
        String query = " select key_person_name,gender from key_person where key_person_id='" + key_person_id + "' and active='Y' ";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {    // move cursor from BOR to valid record.
                String gender = rs.getString("gender");
                if (gender.equals("F")) {
                    key_person_name = "Ms. " + rs.getString("key_person_name");
                }
                if (gender.equals("M")) {
                    key_person_name = "Mr " + rs.getString("key_person_name");
                } else {
                    key_person_name = "Mr. " + rs.getString("key_person_name");
                }

                count++;
            }

            if (count == 0) {
                System.out.println("No such email id exists");
            }
        } catch (Exception e) {
            System.out.println("getMailId() ERROR inside mailSchedular " + e);
        }
        return key_person_name;
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
            System.out.println("DealersReportModel closeConnection() Error: " + e);
        }
    }
}
