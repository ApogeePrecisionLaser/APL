
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
import com.report.bean.DailyEnquiryReport;
import com.report.bean.DealersReport;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
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

        String total_query_till_date = "";
        String open_query_till_date = "";
        String closed_query_till_date = "";
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
                total_query_of_current_date += " and (assigned_to='" + key_person_id + "') ";
            }
            total_query_of_current_date += " group by enquiry_table_id   order by enquiry_table_id desc ";

            if (user_role.equals("Sales")) {
                total_query_till_date = " select * "
                        + " from enquiry_table et,enquiry_status es,city ct,tehsil th,district dt,division dv,state st,salesmanager_state_mapping ssm,key_person kp, "
                        + " org_office oo,enquiry_source_table est  "
                        + " where et.active='Y' and ct.active='Y' and st.active='Y' and dt.active='Y'  and th.active='Y'  and dv.active='Y' and ssm.active='Y' and kp.active='Y' "
                        + " and kp.key_person_id=et.assigned_to and ct.tehsil_id=th.tehsil_id and th.district_id=dt.district_id and ssm.state_id=st.state_id "
                        + " and et.enquiry_status_id=es.enquiry_status_id and (dt.district_name=et.description or et.description='Others') "
                        + " and es.active='Y' and oo.active='Y' "
                        + " and oo.org_office_id=kp.org_office_id  and dt.division_id=dv.division_id and dv.state_id=st.state_id  "
                        + " and (ssm.salesman_id=et.assigned_to  or ssm.salesman_id=et.assigned_by)  "
                        + " and est.active='Y' and et.enquiry_source_table_id=est.enquiry_source_table_id ";
                if (user_role.equals("Sales")) {
                    total_query_till_date += " and ssm.salesman_id='" + key_person_id + "' ";
                }

                total_query_till_date += " group by et.enquiry_table_id ";
                total_query_till_date += " order by et.enquiry_table_id desc ";
            }
            if (user_role.equals("Admin")) {
                total_query_till_date = " select * from enquiry_table where active='Y' ";
                if (!user_role.equals("Admin")) {
                    total_query_till_date += " and (assigned_to='" + key_person_id + "') ";
                }
                total_query_till_date += " group by enquiry_table_id   order by enquiry_table_id desc ";
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
                open_query_of_current_date += " and (assigned_to='" + key_person_id + "' ) ";

            }
            open_query_of_current_date += " group by enquiry_table_id   order by enquiry_table_id desc ";

            if (user_role.equals("Sales")) {
                open_query_till_date = " select * "
                        + " from enquiry_table et,enquiry_status es,city ct,tehsil th,district dt,division dv,state st, "
                        + " salesmanager_state_mapping ssm,key_person kp, "
                        + " org_office oo,enquiry_source_table est  "
                        + " where et.active='Y' and ct.active='Y' and st.active='Y' and dt.active='Y'  and th.active='Y'  and dv.active='Y' "
                        + " and ssm.active='Y' and kp.active='Y' "
                        + " and kp.key_person_id=et.assigned_to and ct.tehsil_id=th.tehsil_id and th.district_id=dt.district_id"
                        + " and ssm.state_id=st.state_id "
                        + " and et.enquiry_status_id=es.enquiry_status_id and (dt.district_name=et.description or et.description='Others') "
                        + " and es.active='Y' and oo.active='Y' "
                        + " and oo.org_office_id=kp.org_office_id  and dt.division_id=dv.division_id and dv.state_id=st.state_id  "
                        + " and (ssm.salesman_id=et.assigned_to  or ssm.salesman_id=et.assigned_by) and et.enquiry_status_id in(1,2,3,4,13,14,15)  "
                        + " and est.active='Y' and et.enquiry_source_table_id=est.enquiry_source_table_id ";
                if (user_role.equals("Sales")) {
                    open_query_till_date += " and ssm.salesman_id='" + key_person_id + "' ";
                }

                open_query_till_date += " group by et.enquiry_table_id ";
                open_query_till_date += " order by et.enquiry_table_id desc ";
            }
            if (user_role.equals("Admin")) {
                open_query_till_date = " select * from enquiry_table where active='Y' "
                        + " and enquiry_status_id in(1,2,3,4,13,14,15) ";

                if (!user_role.equals("Admin")) {
                    open_query_till_date += " and (assigned_to='" + key_person_id + "' ) ";

                }
                open_query_till_date += " group by enquiry_table_id   order by enquiry_table_id desc ";
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
                closed_query_of_current_date += " and (assigned_to='" + key_person_id + "' ) ";

            }
            closed_query_of_current_date += " group by enquiry_table_id   order by enquiry_table_id desc ";

            if (user_role.equals("Sales")) {
                closed_query_till_date = " select * "
                        + " from enquiry_table et,enquiry_status es,city ct,tehsil th,district dt,division dv,state st,salesmanager_state_mapping ssm,key_person kp, "
                        + " org_office oo,enquiry_source_table est "
                        + " where et.active='Y' and ct.active='Y' and st.active='Y' and dt.active='Y'  and th.active='Y'  and dv.active='Y' and ssm.active='Y' and kp.active='Y' "
                        + " and kp.key_person_id=et.assigned_to and ct.tehsil_id=th.tehsil_id and th.district_id=dt.district_id and ssm.state_id=st.state_id "
                        + " and et.enquiry_status_id=es.enquiry_status_id and (dt.district_name=et.description or et.description='Others') "
                        + " and es.active='Y' and oo.active='Y' "
                        + " and oo.org_office_id=kp.org_office_id  and dt.division_id=dv.division_id and dv.state_id=st.state_id  "
                        + " and (ssm.salesman_id=et.assigned_to  or ssm.salesman_id=et.assigned_by) and et.enquiry_status_id in(16,17,18,19,20)  "
                        + " and est.active='Y' and et.enquiry_source_table_id=est.enquiry_source_table_id ";
                if (user_role.equals("Sales")) {
                    closed_query_till_date += " and ssm.salesman_id='" + key_person_id + "' ";
                }

                closed_query_till_date += " group by et.enquiry_table_id ";
                closed_query_till_date += " order by et.enquiry_table_id desc ";
            }
            if (user_role.equals("Admin")) {
                closed_query_till_date = " select * from enquiry_table where active='Y' "
                        + " and enquiry_status_id in(16,17,18,19,20) ";
                if (!user_role.equals("Admin")) {
                    closed_query_till_date += " and (assigned_to='" + key_person_id + "') ";

                }
                closed_query_till_date += " group by enquiry_table_id   order by enquiry_table_id desc ";
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
                sold_query_of_current_date += " and (assigned_to='" + key_person_id + "') ";

            }
            sold_query_of_current_date += " group by enquiry_table_id   order by enquiry_table_id desc ";

            String sold_query_till_date = " select * from enquiry_table where active='Y' "
                    + " and enquiry_status_id in(16) ";
            if (!user_role.equals("Admin")) {
                sold_query_till_date += " and (assigned_to='" + key_person_id + "') ";

            }
            sold_query_till_date += " group by enquiry_table_id   order by enquiry_table_id desc ";

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

    public static List<DailyEnquiryReport> getDynamicImagesData() {
        List<DailyEnquiryReport> list = new ArrayList<DailyEnquiryReport>();

        try {
            String path1 = "C:\\Users\\Apogee\\Desktop\\REAL DATA FOR APL\\package1.jpg";
            String path2 = "C:\\Users\\Apogee\\Desktop\\REAL DATA FOR APL\\package2.jpg";
            String path3 = "C:\\Users\\Apogee\\Desktop\\REAL DATA FOR APL\\data1.jpg";
            DailyEnquiryReport bean1 = new DailyEnquiryReport();
            DailyEnquiryReport bean2 = new DailyEnquiryReport();
            DailyEnquiryReport bean3 = new DailyEnquiryReport();
            File initialFile1 = new File(path1);
            InputStream targetStream1 = new FileInputStream(initialFile1);

            File initialFile2 = new File(path2);
            InputStream targetStream2 = new FileInputStream(initialFile2);

            File initialFile3 = new File(path3);
            InputStream targetStream3 = new FileInputStream(initialFile3);
            for (int i = 0; i < 3; i++) {
                bean1.setImages(targetStream1);
                bean2.setImages(targetStream2);
                bean3.setImages(targetStream3);
                bean1.setCurrent_date("image1");
                bean2.setCurrent_date("image2");
                bean3.setCurrent_date("image3");
            }
            list.add(bean1);
            list.add(bean2);
            list.add(bean3);
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
