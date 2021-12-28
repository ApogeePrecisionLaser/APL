package com.dashboard.model;

import com.dashboard.bean.Enquiry;
import java.io.ByteArrayOutputStream;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import static java.time.LocalDateTime.now;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;

public class EnquiryModel {

    static private Connection connection;
    private String driver, url, user, password;
    private String message, messageBGColor = "#a2a220";
    private final String COLOR_OK = "green";
    private final String COLOR_ERROR = "red";
    int item_id = 0;
    int item_id1 = 0;
    int order_table_id = 0;

    public void setConnection(Connection con) {
        try {
            connection = con;
        } catch (Exception e) {
            System.out.println("EnquiryModel setConnection() Error: " + e);
        }
    }

    public ArrayList<Enquiry> getEnquirySources() {
        ArrayList<Enquiry> list = new ArrayList<Enquiry>();

        try {
            String query = " select * from  enquiry_source_table where active='Y' ";
            query += " group by enquiry_source ";

            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                Enquiry bean = new Enquiry();
                bean.setEnquiry_source_table_id(rst.getInt("enquiry_source_table_id"));
                bean.setEnquiry_source(rst.getString("enquiry_source"));
                bean.setDescription(rst.getString("description"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.err.println("Exception------------" + e);
        }

        return list;
    }

    public int insertEnquirySources(Enquiry bean) {
        String query = "INSERT INTO enquiry_source_table(enquiry_source,description,"
                + " revision_no,active,remark) VALUES(?,?,?,?,?) ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, (bean.getEnquiry_source()));
            pstmt.setString(2, (bean.getDescription()));
            pstmt.setInt(3, bean.getRevision_no());
            pstmt.setString(4, "Y");
            pstmt.setString(5, "OK");
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("EnquiryModel insertRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            messageBGColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            messageBGColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int updateEnquirySources(Enquiry bean, int enquiry_source_table_id) {
        int revision = EnquiryModel.getRevisionnoForEnquirySources(bean, enquiry_source_table_id);
        int updateRowsAffected = 0;
        boolean status = false;
        String query1 = "SELECT max(revision_no) revision_no FROM enquiry_source_table WHERE enquiry_source_table_id = " + enquiry_source_table_id + "  && active=? ";
        String query2 = "UPDATE enquiry_source_table SET active=? WHERE enquiry_source_table_id=? and revision_no=?";
        String query3 = "INSERT INTO enquiry_source_table(enquiry_source_table_id,enquiry_source,description,"
                + " revision_no,active,remark) VALUES(?,?,?,?,?,?) ";
        int rowsAffected = 0;
        try {
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement(query1);
            pstmt.setString(1, "Y");

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                PreparedStatement pstm = connection.prepareStatement(query2);

                pstm.setString(1, "n");
                pstm.setInt(2, enquiry_source_table_id);
                pstm.setInt(3, revision);
                updateRowsAffected = pstm.executeUpdate();
                if (updateRowsAffected >= 1) {
                    revision = rs.getInt("revision_no") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                    psmt.setInt(1, (bean.getEnquiry_source_table_id()));
                    psmt.setString(2, (bean.getEnquiry_source()));
                    psmt.setString(3, (bean.getDescription()));
                    psmt.setInt(4, revision);
                    psmt.setString(5, "Y");
                    psmt.setString(6, "OK");
                    rowsAffected = psmt.executeUpdate();
                    if (rowsAffected > 0) {
                        status = true;
                        message = "Record updated successfully.";
                        messageBGColor = COLOR_OK;
                        connection.commit();
                    } else {
                        status = false;
                        message = "Cannot update the record, some error.";
                        messageBGColor = COLOR_ERROR;
                        connection.rollback();
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("EnquiryModel updateRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record updated successfully.";
            messageBGColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            messageBGColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public static int getRevisionnoForEnquirySources(Enquiry bean, int enquiry_source_table_id) {
        int revision = 0;
        try {

            String query = " SELECT max(revision_no) as revision_no FROM enquiry_source_table WHERE "
                    + " enquiry_source_table_id =" + enquiry_source_table_id + "  && active='Y';";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
            System.out.println("EnquiryModel getRevisionno() Error: " + e);

        }
        return revision;
    }

    public int deleteEnquirySources(int enquiry_source_table_id) {
        String query = " Update enquiry_source_table set active='N' WHERE enquiry_source_table_id = '" + enquiry_source_table_id + "' "
                + " and active='Y' ";
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("EnquiryModel deleteRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record deleted successfully.";
            messageBGColor = COLOR_OK;
        } else {
            message = "Cannot delete the record, some error.";
            messageBGColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public ArrayList<Enquiry> getMarketingVerticals() {
        ArrayList<Enquiry> list = new ArrayList<Enquiry>();

        try {
            String query = " select * from  marketing_vertical where active='Y' ";

            query += " group by marketing_vertical_name ";

            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                Enquiry bean = new Enquiry();
                bean.setMarketing_vertical_id(rst.getInt("marketing_vertical_id"));
                bean.setMarketing_vertical_name(rst.getString("marketing_vertical_name"));
                bean.setDescription(rst.getString("description"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.err.println("Exception------------" + e);
        }

        return list;
    }

    public int insertMarketingVerticals(Enquiry bean) {
        String query = "INSERT INTO marketing_vertical(marketing_vertical_name,description,"
                + " revision_no,active,remark) VALUES(?,?,?,?,?) ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, (bean.getMarketing_vertical_name()));
            pstmt.setString(2, (bean.getDescription()));
            pstmt.setInt(3, bean.getRevision_no());
            pstmt.setString(4, "Y");
            pstmt.setString(5, "OK");
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("EnquiryModel insertMarketingVerticals() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            messageBGColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            messageBGColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int updateMarketingVerticals(Enquiry bean, int marketing_vertical_id) {
        int revision = EnquiryModel.getRevisionnoForMarketingVerticals(bean, marketing_vertical_id);
        int updateRowsAffected = 0;
        boolean status = false;
        String query1 = "SELECT max(revision_no) revision_no FROM marketing_vertical WHERE marketing_vertical_id = " + marketing_vertical_id + "  && active=? ";
        String query2 = "UPDATE marketing_vertical SET active=? WHERE marketing_vertical_id=? and revision_no=?";
        String query3 = "INSERT INTO marketing_vertical(marketing_vertical_id,marketing_vertical_name,description,"
                + " revision_no,active,remark) VALUES(?,?,?,?,?,?) ";
        int rowsAffected = 0;
        try {
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement(query1);
            pstmt.setString(1, "Y");

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                PreparedStatement pstm = connection.prepareStatement(query2);

                pstm.setString(1, "n");
                pstm.setInt(2, marketing_vertical_id);
                pstm.setInt(3, revision);
                updateRowsAffected = pstm.executeUpdate();
                if (updateRowsAffected >= 1) {
                    revision = rs.getInt("revision_no") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                    psmt.setInt(1, (bean.getMarketing_vertical_id()));
                    psmt.setString(2, (bean.getMarketing_vertical_name()));
                    psmt.setString(3, (bean.getDescription()));
                    psmt.setInt(4, revision);
                    psmt.setString(5, "Y");
                    psmt.setString(6, "OK");
                    rowsAffected = psmt.executeUpdate();
                    if (rowsAffected > 0) {
                        status = true;
                        message = "Record updated successfully.";
                        messageBGColor = COLOR_OK;
                        connection.commit();
                    } else {
                        status = false;
                        message = "Cannot update the record, some error.";
                        messageBGColor = COLOR_ERROR;
                        connection.rollback();
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("EnquiryModel updateMarketingVerticals() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record updated successfully.";
            messageBGColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            messageBGColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public static int getRevisionnoForMarketingVerticals(Enquiry bean, int marketing_vertical_id) {
        int revision = 0;
        try {

            String query = " SELECT max(revision_no) as revision_no FROM marketing_vertical WHERE "
                    + " marketing_vertical_id =" + marketing_vertical_id + "  && active='Y';";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
            System.out.println("EnquiryModel getRevisionnoForMarketingVerticals() Error: " + e);

        }
        return revision;
    }

    public int deleteMarketingVerticals(int marketing_vertical_id) {
        String query = " Update marketing_vertical set active='N' WHERE marketing_vertical_id = '" + marketing_vertical_id + "' "
                + " and active='Y' ";
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("EnquiryModel deleteMarketingVerticals() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record deleted successfully.";
            messageBGColor = COLOR_OK;
        } else {
            message = "Cannot delete the record, some error.";
            messageBGColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public List<String> getEnquirySource(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select * from enquiry_source_table where active='Y' group by enquiry_source  ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String enquiry_source = (rset.getString("enquiry_source"));
                if (enquiry_source.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(enquiry_source);
                    count++;
                }
            }

            if (count == 0) {
                list.add("No such enquiry_source  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:EnquiryModel--getEnquirySource()-- " + e);
        }
        return list;
    }

    public List<String> getMarketingVertical(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select * from marketing_vertical where active='Y' group by marketing_vertical_name ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String marketing_vertical_name = (rset.getString("marketing_vertical_name"));
                if (marketing_vertical_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(marketing_vertical_name);
                    count++;
                }
            }

            if (count == 0) {
                list.add("No such marketing_vertical_name  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:EnquiryModel--getMarketingVertical()-- " + e);
        }
        return list;
    }

    public List<String> getDistrict(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select district_name from district where active='Y' group by district_name ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String district_name = (rset.getString("district_name"));
                if (district_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(district_name);
                    count++;
                }
            }

            if (count == 0) {
                list.add("No such district_name  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:EnquiryModel--getDistrict()-- " + e);
        }
        return list;
    }

    public List<String> getCities(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT city_name from city where active='Y'";

        query += " group by city_name order by city_name  ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String city_name = (rset.getString("city_name"));
                if (city_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(city_name);
                    count++;
                }
            }

            if (count == 0) {
                list.add("No such city_name  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:ProfileModel--getCities()-- " + e);
        }
        return list;
    }

    public List<String> getStates(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT state_name from state where active='Y'";

        query += " group by state_name order by state_name  ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String state_name = (rset.getString("state_name"));
                if (state_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(state_name);
                    count++;
                }
            }

            if (count == 0) {
                list.add("No such state_name  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:ProfileModel--getStates()-- " + e);
        }
        return list;
    }

    public List<String> getCountry(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT country_name from country where active='Y'";

        query += " group by country_name order by country_name  ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String country_name = (rset.getString("country_name"));
                if (country_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(country_name);
                    count++;
                }
            }

            if (count == 0) {
                list.add("No such country_name  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:ProfileModel--getStates()-- " + e);
        }
        return list;
    }

    public int insertEnquiries(Enquiry bean, String enquiry_type) {
        String query = "";
        if (enquiry_type.equals("Sales")) {
            query = " INSERT INTO enquiry_table(enquiry_source_table_id,marketing_vertical_id,enquiry_status_id,enquiry_no,sender_name,sender_email, "
                    + " sender_mob,sender_company_name,enquiry_address,enquiry_city,enquiry_state,country,enquiry_message,enquiry_date_time,enquiry_call_duration,"
                    + " enquiry_reciever_mob,sender_alternate_email,sender_alternate_mob, "
                    + " revision_no,active,description,assigned_to) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
        }
        if (enquiry_type.equals("complaint")) {
            query = "INSERT INTO complaint_table(enquiry_source_table_id,marketing_vertical_id,enquiry_status_id,enquiry_no,sender_name,sender_email, "
                    + " sender_mob,sender_company_name,enquiry_address,enquiry_city,enquiry_state,country,enquiry_message,enquiry_date_time,enquiry_call_duration,"
                    + " enquiry_reciever_mob,sender_alternate_email,sender_alternate_mob, "
                    + " revision_no,active,description,assigned_to) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
        }

        int rowsAffected = 0;

        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date_time = sdf.format(date);

        int enquiry_source_table_id = getSourceTableId(bean.getEnquiry_source());
        int marketing_vertical_id = getMarketingVerticalId(bean.getMarketing_vertical_name());
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, enquiry_source_table_id);
            pstmt.setInt(2, marketing_vertical_id);
            pstmt.setInt(3, 1);
            pstmt.setString(4, bean.getEnquiry_no());
            pstmt.setString(5, bean.getSender_name());
            pstmt.setString(6, bean.getSender_email());
            pstmt.setString(7, bean.getSender_mob());
            pstmt.setString(8, bean.getSender_company_name());
            pstmt.setString(9, bean.getEnquiry_address());
            pstmt.setString(10, bean.getEnquiry_city());
            pstmt.setString(11, bean.getEnquiry_state());
            pstmt.setString(12, bean.getCountry());
            pstmt.setString(13, bean.getEnquiry_message());
            pstmt.setString(14, date_time);
            String enquiry_call_duration = "";
            enquiry_call_duration = bean.getEnquiry_call_duration();
            if (enquiry_call_duration == null) {
                enquiry_call_duration = "";
            }
            pstmt.setString(15, enquiry_call_duration);
            String enquiry_reciever_mob = "";
            enquiry_reciever_mob = bean.getEnquiry_reciever_mob();
            if (enquiry_reciever_mob == null) {
                enquiry_reciever_mob = "";
            }
            pstmt.setString(15, enquiry_call_duration);
            pstmt.setString(16, enquiry_reciever_mob);
            pstmt.setString(17, bean.getSender_alternate_email());
            pstmt.setString(18, bean.getSender_alternate_mob());
            pstmt.setInt(19, bean.getRevision_no());
            pstmt.setString(20, "Y");
            pstmt.setString(21, bean.getDescription());
            pstmt.setInt(22, 129);// 129 key_person admin 
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("EnquiryModel insertEnquiries() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            messageBGColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            messageBGColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public String assignToSalesPerson(String enquiry_table_id, String sales_person_name) {
        int revision = EnquiryModel.getRevisionno(enquiry_table_id);
        int updateRowsAffected = 0;
        boolean status = false;
        String query1 = " SELECT max(revision_no) revision_no,enquiry_source_table_id,marketing_vertical_id,enquiry_status_id,enquiry_no,sender_name, "
                + " sender_email,sender_mob,sender_company_name,enquiry_address,enquiry_city,enquiry_state,country,enquiry_message,enquiry_date_time, "
                + " enquiry_call_duration, "
                + " enquiry_reciever_mob,sender_alternate_email,sender_alternate_mob,description,assigned_to "
                + " FROM enquiry_table WHERE enquiry_table_id = " + enquiry_table_id + "  && active=? ";
        String query2 = " UPDATE enquiry_table SET active=? WHERE enquiry_table_id=? and revision_no=?";
        String query3 = " INSERT INTO enquiry_table(enquiry_table_id,enquiry_source_table_id,marketing_vertical_id,enquiry_status_id,enquiry_no,sender_name,sender_email, "
                + " sender_mob,sender_company_name,enquiry_address,enquiry_city,enquiry_state,country,enquiry_message,enquiry_date_time,enquiry_call_duration,"
                + " enquiry_reciever_mob,sender_alternate_email,sender_alternate_mob, "
                + " revision_no,active,description,assigned_to) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        int rowsAffected = 0;
        int assigned_to = getKeyPersonId(sales_person_name);
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
            pstmt.setString(1, "Y");
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                revision = rs.getInt("revision_no");
                String enquiry_source_table_id = rs.getString("enquiry_source_table_id");
                String marketing_vertical_id = rs.getString("marketing_vertical_id");
                String enquiry_no = rs.getString("enquiry_no");
                String sender_name = rs.getString("sender_name");
                String sender_email = rs.getString("sender_email");
                String sender_mob = rs.getString("sender_mob");
                String sender_company_name = rs.getString("sender_company_name");
                String enquiry_address = rs.getString("enquiry_address");
                String enquiry_city = rs.getString("enquiry_city");
                String enquiry_state = rs.getString("enquiry_state");
                String country = rs.getString("country");
                String enquiry_message = rs.getString("enquiry_message");
                String enquiry_date_time = rs.getString("enquiry_date_time");
                String enquiry_call_duration = rs.getString("enquiry_call_duration");
                String enquiry_reciever_mob = rs.getString("enquiry_reciever_mob");
                String sender_alternate_email = rs.getString("sender_alternate_email");
                String sender_alternate_mob = rs.getString("sender_alternate_mob");
                String description = rs.getString("description");

                PreparedStatement pstm = connection.prepareStatement(query2);
                pstm.setString(1, "n");
                pstm.setString(2, enquiry_table_id);
                pstm.setInt(3, revision);
                updateRowsAffected = pstm.executeUpdate();
                if (updateRowsAffected >= 1) {
                    revision = rs.getInt("revision_no") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                    psmt.setString(1, enquiry_table_id);
                    psmt.setString(2, enquiry_source_table_id);
                    psmt.setString(3, marketing_vertical_id);
                    psmt.setInt(4, 2);
                    psmt.setString(5, enquiry_no);
                    psmt.setString(6, sender_name);
                    psmt.setString(7, sender_email);
                    psmt.setString(8, sender_mob);
                    psmt.setString(9, sender_company_name);
                    psmt.setString(10, enquiry_address);
                    psmt.setString(11, enquiry_city);
                    psmt.setString(12, enquiry_state);
                    psmt.setString(13, country);
                    psmt.setString(14, enquiry_message);
                    psmt.setString(15, enquiry_date_time);
                    psmt.setString(16, enquiry_call_duration);
                    psmt.setString(17, enquiry_reciever_mob);
                    psmt.setString(18, sender_alternate_email);
                    psmt.setString(19, sender_alternate_mob);
                    psmt.setInt(20, revision);
                    psmt.setString(21, "Y");
                    psmt.setString(22, description);
                    psmt.setInt(23, assigned_to);

                    rowsAffected = psmt.executeUpdate();
                    if (rowsAffected > 0) {
                        status = true;
                    } else {
                        status = false;
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("Error:EnquiryModel assignToSalesPerson-" + e);
        }
        if (rowsAffected > 0) {
            message = " Your Enquiry successfully assigend !.";
            messageBGColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            messageBGColor = COLOR_ERROR;
        }
        return message + "&" + status;
    }

    public static int getRevisionno(String enquiry_table_id) {
        int revision = 0;
        try {

            String query = " SELECT max(revision_no) as revision_no FROM enquiry_table WHERE enquiry_table_id =" + enquiry_table_id + "  && active='Y';";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
            System.out.println("Error:EnquiryModel getRevisionno-" + e);

        }
        return revision;
    }

    public static int getRevisionnoForComplaint(String complaint_table_id) {
        int revision = 0;
        try {

            String query = " SELECT max(revision_no) as revision_no FROM complaint_table WHERE complaint_table_id =" + complaint_table_id + "  && active='Y';";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
            System.out.println("Error:EnquiryModel getRevisionnoForComplaint-" + e);

        }
        return revision;
    }

    public String assignComplaintToSalesPerson(String complaint_table_id, String sales_person_name) {
        int revision = EnquiryModel.getRevisionnoForComplaint(complaint_table_id);
        int updateRowsAffected = 0;
        boolean status = false;
        String query1 = " SELECT max(revision_no) revision_no,enquiry_source_table_id,marketing_vertical_id,enquiry_status_id,enquiry_no,sender_name, "
                + " sender_email,sender_mob,sender_company_name,enquiry_address,enquiry_city,enquiry_state,country,enquiry_message,enquiry_date_time, "
                + " enquiry_call_duration, "
                + " enquiry_reciever_mob,sender_alternate_email,sender_alternate_mob,description,assigned_to "
                + " FROM complaint_table WHERE complaint_table_id = " + complaint_table_id + "  && active=? ";
        String query2 = " UPDATE complaint_table SET active=? WHERE complaint_table_id=? and revision_no=?";
        String query3 = " INSERT INTO complaint_table(complaint_table_id,enquiry_source_table_id,marketing_vertical_id,enquiry_status_id,enquiry_no,sender_name,sender_email, "
                + " sender_mob,sender_company_name,enquiry_address,enquiry_city,enquiry_state,country,enquiry_message,enquiry_date_time,enquiry_call_duration,"
                + " enquiry_reciever_mob,sender_alternate_email,sender_alternate_mob, "
                + " revision_no,active,description,assigned_to) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        int rowsAffected = 0;
        int assigned_to = getKeyPersonId(sales_person_name);
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
            pstmt.setString(1, "Y");
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                revision = rs.getInt("revision_no");
                String enquiry_source_table_id = rs.getString("enquiry_source_table_id");
                String marketing_vertical_id = rs.getString("marketing_vertical_id");
                String enquiry_no = rs.getString("enquiry_no");
                String sender_name = rs.getString("sender_name");
                String sender_email = rs.getString("sender_email");
                String sender_mob = rs.getString("sender_mob");
                String sender_company_name = rs.getString("sender_company_name");
                String enquiry_address = rs.getString("enquiry_address");
                String enquiry_city = rs.getString("enquiry_city");
                String enquiry_state = rs.getString("enquiry_state");
                String country = rs.getString("country");
                String enquiry_message = rs.getString("enquiry_message");
                String enquiry_date_time = rs.getString("enquiry_date_time");
                String enquiry_call_duration = rs.getString("enquiry_call_duration");
                String enquiry_reciever_mob = rs.getString("enquiry_reciever_mob");
                String sender_alternate_email = rs.getString("sender_alternate_email");
                String sender_alternate_mob = rs.getString("sender_alternate_mob");
                String description = rs.getString("description");

                PreparedStatement pstm = connection.prepareStatement(query2);
                pstm.setString(1, "n");
                pstm.setString(2, complaint_table_id);
                pstm.setInt(3, revision);
                updateRowsAffected = pstm.executeUpdate();
                if (updateRowsAffected >= 1) {
                    revision = rs.getInt("revision_no") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                    psmt.setString(1, complaint_table_id);
                    psmt.setString(2, enquiry_source_table_id);
                    psmt.setString(3, marketing_vertical_id);
                    psmt.setInt(4, 2);
                    psmt.setString(5, enquiry_no);
                    psmt.setString(6, sender_name);
                    psmt.setString(7, sender_email);
                    psmt.setString(8, sender_mob);
                    psmt.setString(9, sender_company_name);
                    psmt.setString(10, enquiry_address);
                    psmt.setString(11, enquiry_city);
                    psmt.setString(12, enquiry_state);
                    psmt.setString(13, country);
                    psmt.setString(14, enquiry_message);
                    psmt.setString(15, enquiry_date_time);
                    psmt.setString(16, enquiry_call_duration);
                    psmt.setString(17, enquiry_reciever_mob);
                    psmt.setString(18, sender_alternate_email);
                    psmt.setString(19, sender_alternate_mob);
                    psmt.setInt(20, revision);
                    psmt.setString(21, "Y");
                    psmt.setString(22, description);
                    psmt.setInt(23, assigned_to);

                    rowsAffected = psmt.executeUpdate();
                    if (rowsAffected > 0) {
                        status = true;
                    } else {
                        status = false;
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("Error:EnquiryModel assignComplaintToSalesPerson-" + e);
        }
        if (rowsAffected > 0) {
            message = " Your Enquiry successfully assigend !.";
            messageBGColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            messageBGColor = COLOR_ERROR;
        }
        return message + "&" + status;
    }

    public int getKeyPersonId(String sales_person_name) {

        String query = " SELECT key_person_id from key_person where active='Y' ";

        query += " and key_person_name='" + sales_person_name + "' ";

        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("key_person_id");
        } catch (Exception e) {
            System.out.println("EnquiryModel getKeyPersonId Error: " + e);
        }
        return id;
    }

    public int getSourceTableId(String enquiry_source) {

        String query = " SELECT enquiry_source_table_id from enquiry_source_table where active='Y' ";

        query += " and enquiry_source='" + enquiry_source + "' ";

        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("enquiry_source_table_id");
        } catch (Exception e) {
            System.out.println("EnquiryModel getSourceTableId Error: " + e);
        }
        return id;
    }

    public int getMarketingVerticalId(String marketing_vertical) {

        String query = " SELECT marketing_vertical_id from marketing_vertical where active='Y' ";

        query += " and marketing_vertical_name='" + marketing_vertical + "' ";

        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("marketing_vertical_id");
        } catch (Exception e) {
            System.out.println("EnquiryModel getMarketingVerticalId Error: " + e);
        }
        return id;
    }

    public static ArrayList<Enquiry> getAllEnquiries() {
        ArrayList<Enquiry> list = new ArrayList<Enquiry>();
        try {
            String query = " select et.enquiry_table_id,es.status,et.enquiry_no, et.sender_name,et.sender_email,et.sender_mob,et.sender_company_name, "
                    + " et.enquiry_address,et.enquiry_city,et.enquiry_state,et.country,et.enquiry_message,et.enquiry_date_time,  "
                    + " et.enquiry_call_duration,et.enquiry_reciever_mob,et.sender_alternate_email,  et.sender_alternate_mob,et.description, "
                    + " kp.key_person_name  from enquiry_table et,enquiry_status es,city ct,tehsil th,district dt,division dv,state st, "
                    + " salesmanager_state_mapping ssm,key_person kp  "
                    + " where et.active='Y' and ct.active='Y' and st.active='Y' and dt.active='Y'  and th.active='Y'  and dv.active='Y' "
                    + " and ssm.active='Y' and kp.active='Y'  "
                    + " and kp.key_person_id=ssm.salesman_id and ct.tehsil_id=th.tehsil_id and th.district_id=dt.district_id and "
                    + " ssm.state_id=st.state_id and dt.division_id=dv.division_id and dv.state_id=st.state_id "
                    + " and et.enquiry_status_id=es.enquiry_status_id and dt.district_name=et.description and es.active='Y' ";

            query += " group by et.enquiry_table_id  ";
            query += " order by et.enquiry_table_id desc  ";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                Enquiry bean = new Enquiry();
                bean.setEnquiry_table_id(rst.getInt("enquiry_table_id"));
//                bean.setEnquiry_source(rst.getString("enquiry_source"));
//                bean.setMarketing_vertical_name(rst.getString("marketing_vertical_name"));
                bean.setStatus(rst.getString("status"));
                bean.setEnquiry_no(rst.getString("enquiry_no"));
                bean.setSender_name(rst.getString("sender_name"));
                bean.setSender_email(rst.getString("sender_email"));
                bean.setSender_mob(rst.getString("sender_mob"));
                bean.setSender_company_name(rst.getString("sender_company_name"));
                bean.setEnquiry_address(rst.getString("enquiry_address"));
                bean.setEnquiry_city(rst.getString("enquiry_city"));
                bean.setEnquiry_state(rst.getString("enquiry_state"));
                bean.setCountry(rst.getString("country"));
                bean.setEnquiry_message(rst.getString("enquiry_message"));
//                bean.setEnquiry_date_time(rst.getString("enquiry_date_time"));
                bean.setEnquiry_call_duration(rst.getString("enquiry_call_duration"));
                bean.setEnquiry_reciever_mob(rst.getString("enquiry_reciever_mob"));
                bean.setSender_alternate_email(rst.getString("sender_alternate_email"));
                bean.setSender_alternate_mob(rst.getString("sender_alternate_mob"));
                bean.setDescription(rst.getString("description"));
                bean.setAssigned_to(rst.getString("key_person_name"));

                String enquiry_date_time = rst.getString("enquiry_date_time");
//                String split_arr[] = enquiry_date_time.split(" ");
//                enquiry_date_time = split_arr[0] + " " + split_arr[1];

//                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
                Date date = new Date();
                String currentDateString = dateFormatter.format(date);
                Date currentDate = dateFormatter.parse(currentDateString);
                String pastTimeInSecond = enquiry_date_time;
                Date pastDate = dateFormatter.parse(pastTimeInSecond);
                String time_ago = timeAgo(currentDate, pastDate);
                bean.setEnquiry_date_time(time_ago);
                list.add(bean);
            }
        } catch (Exception e) {
            System.err.println("getAllEnquiries Exception------------" + e);
        }

        return list;
    }

    public static ArrayList<Enquiry> getAllComplaints() {
        ArrayList<Enquiry> list = new ArrayList<Enquiry>();
        try {
            String query = " select comt.complaint_table_id,es.status,comt.enquiry_no, comt.sender_name,comt.sender_email,comt.sender_mob, "
                    + " comt.sender_company_name, "
                    + " comt.enquiry_address,comt.enquiry_city,comt.enquiry_state,comt.country,comt.enquiry_message,comt.enquiry_date_time,  "
                    + " comt.enquiry_call_duration,comt.enquiry_reciever_mob,comt.sender_alternate_email,comt.sender_alternate_mob,comt.description, "
                    + " kp.key_person_name  from complaint_table comt,enquiry_status es,city ct,tehsil th,district dt,division dv,state st, "
                    + " salesmanager_state_mapping ssm,key_person kp  "
                    + " where comt.active='Y' and ct.active='Y' and st.active='Y' and dt.active='Y'  and th.active='Y'  and dv.active='Y' "
                    + " and ssm.active='Y' and kp.active='Y'  "
                    + " and kp.key_person_id=ssm.salesman_id and ct.tehsil_id=th.tehsil_id and th.district_id=dt.district_id and "
                    + " ssm.state_id=st.state_id and dt.division_id=dv.division_id and dv.state_id=st.state_id "
                    + " and comt.enquiry_status_id=es.enquiry_status_id and dt.district_name=comt.description and es.active='Y' ";
            query += " group by comt.complaint_table_id  ";
            query += " order by comt.complaint_table_id desc  ";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                Enquiry bean = new Enquiry();
                bean.setEnquiry_table_id(rst.getInt("complaint_table_id"));
//                bean.setEnquiry_source(rst.getString("enquiry_source"));
//                bean.setMarketing_vertical_name(rst.getString("marketing_vertical_name"));
                bean.setStatus(rst.getString("status"));
                bean.setEnquiry_no(rst.getString("enquiry_no"));
                bean.setSender_name(rst.getString("sender_name"));
                bean.setSender_email(rst.getString("sender_email"));
                bean.setSender_mob(rst.getString("sender_mob"));
                bean.setSender_company_name(rst.getString("sender_company_name"));
                bean.setEnquiry_address(rst.getString("enquiry_address"));
                bean.setEnquiry_city(rst.getString("enquiry_city"));
                bean.setEnquiry_state(rst.getString("enquiry_state"));
                bean.setCountry(rst.getString("country"));
                bean.setEnquiry_message(rst.getString("enquiry_message"));
//                bean.setEnquiry_date_time(rst.getString("enquiry_date_time"));
                bean.setEnquiry_call_duration(rst.getString("enquiry_call_duration"));
                bean.setEnquiry_reciever_mob(rst.getString("enquiry_reciever_mob"));
                bean.setSender_alternate_email(rst.getString("sender_alternate_email"));
                bean.setSender_alternate_mob(rst.getString("sender_alternate_mob"));
                bean.setDescription(rst.getString("description"));
                bean.setAssigned_to(rst.getString("key_person_name"));

                String enquiry_date_time = rst.getString("enquiry_date_time");
//                String split_arr[] = enquiry_date_time.split(" ");
//                enquiry_date_time = split_arr[0] + " " + split_arr[1];

//                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
                Date date = new Date();
                String currentDateString = dateFormatter.format(date);
                Date currentDate = dateFormatter.parse(currentDateString);
                String pastTimeInSecond = enquiry_date_time;
                Date pastDate = dateFormatter.parse(pastTimeInSecond);
                String time_ago = timeAgo(currentDate, pastDate);
                bean.setEnquiry_date_time(time_ago);

                list.add(bean);
            }
        } catch (Exception e) {
            System.err.println("getAllComplaints Exception------------" + e);
        }

        return list;
    }

    public static String timeAgo(Date currentDate, Date pastDate) {
        long milliSecPerMinute = 60 * 1000; //Milliseconds Per Minute
        long milliSecPerHour = milliSecPerMinute * 60; //Milliseconds Per Hour
        long milliSecPerDay = milliSecPerHour * 24; //Milliseconds Per Day
        long milliSecPerMonth = milliSecPerDay * 30; //Milliseconds Per Month
        long milliSecPerYear = milliSecPerDay * 365; //Milliseconds Per Year
        //Difference in Milliseconds between two dates
        long msExpired = currentDate.getTime() - pastDate.getTime();
        //Second or Seconds ago calculation
        if (msExpired < milliSecPerMinute) {
            if (Math.round(msExpired / 1000) == 1) {
                return String.valueOf(Math.round(msExpired / 1000)) + " second ago... ";
            } else {
                return String.valueOf(Math.round(msExpired / 1000) + " seconds ago...");
            }
        } //Minute or Minutes ago calculation
        else if (msExpired < milliSecPerHour) {
            if (Math.round(msExpired / milliSecPerMinute) == 1) {
                return String.valueOf(Math.round(msExpired / milliSecPerMinute)) + " minute ago... ";
            } else {
                return String.valueOf(Math.round(msExpired / milliSecPerMinute)) + " minutes ago... ";
            }
        } //Hour or Hours ago calculation
        else if (msExpired < milliSecPerDay) {
            if (Math.round(msExpired / milliSecPerHour) == 1) {
                return String.valueOf(Math.round(msExpired / milliSecPerHour)) + " hour ago... ";
            } else {
                return String.valueOf(Math.round(msExpired / milliSecPerHour)) + " hours ago... ";
            }
        } //Day or Days ago calculation
        else if (msExpired < milliSecPerMonth) {
            if (Math.round(msExpired / milliSecPerDay) == 1) {
                return String.valueOf(Math.round(msExpired / milliSecPerDay)) + " day ago... ";
            } else {
                return String.valueOf(Math.round(msExpired / milliSecPerDay)) + " days ago... ";
            }
        } //Month or Months ago calculation 
        else if (msExpired < milliSecPerYear) {
            if (Math.round(msExpired / milliSecPerMonth) == 1) {
                return String.valueOf(Math.round(msExpired / milliSecPerMonth)) + "  month ago... ";
            } else {
                return String.valueOf(Math.round(msExpired / milliSecPerMonth)) + "  months ago... ";
            }
        } //Year or Years ago calculation 
        else {
            if (Math.round(msExpired / milliSecPerYear) == 1) {
                return String.valueOf(Math.round(msExpired / milliSecPerYear)) + " year ago...";
            } else {
                return String.valueOf(Math.round(msExpired / milliSecPerYear)) + " years ago...";
            }
        }
    }

    public ArrayList<Enquiry> getAllEnquiriesDetails(String enquiry_table_id) {
        ArrayList<Enquiry> list = new ArrayList<Enquiry>();

        try {
            String query = " select et.enquiry_table_id,es.status,et.enquiry_no, et.sender_name,et.sender_email,et.sender_mob,et.sender_company_name, "
                    + " et.enquiry_address,et.enquiry_city,et.enquiry_state,et.country,et.enquiry_message,et.enquiry_date_time,  "
                    + " et.enquiry_call_duration,et.enquiry_reciever_mob,et.sender_alternate_email,  et.sender_alternate_mob,et.description, "
                    + " kp.key_person_name  from enquiry_table et,enquiry_status es,city ct,tehsil th,district dt,division dv,state st, "
                    + " salesmanager_state_mapping ssm,key_person kp  "
                    + " where et.active='Y' and ct.active='Y' and st.active='Y' and dt.active='Y'  and th.active='Y'  and dv.active='Y' "
                    + " and ssm.active='Y' and kp.active='Y'  "
                    + " and kp.key_person_id=ssm.salesman_id and ct.tehsil_id=th.tehsil_id and th.district_id=dt.district_id and "
                    + " ssm.state_id=st.state_id and dt.division_id=dv.division_id and dv.state_id=st.state_id "
                    + " and et.enquiry_status_id=es.enquiry_status_id and dt.district_name=et.description and es.active='Y'  ";
            if (!enquiry_table_id.equals("") && enquiry_table_id != null) {
                query += " and et.enquiry_table_id='" + enquiry_table_id + "' ";
            }
            query += " group by et.enquiry_table_id ";
            query += " order by et.enquiry_table_id desc ";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                Enquiry bean = new Enquiry();
                bean.setEnquiry_table_id(rst.getInt("enquiry_table_id"));
//                bean.setEnquiry_source(rst.getString("enquiry_source"));
//                bean.setMarketing_vertical_name(rst.getString("marketing_vertical_name"));
                bean.setStatus(rst.getString("status"));
                bean.setEnquiry_no(rst.getString("enquiry_no"));
                bean.setSender_name(rst.getString("sender_name"));
                bean.setSender_email(rst.getString("sender_email"));
                bean.setSender_mob(rst.getString("sender_mob"));
                bean.setSender_company_name(rst.getString("sender_company_name"));
                bean.setEnquiry_address(rst.getString("enquiry_address"));
                bean.setEnquiry_city(rst.getString("enquiry_city"));
                bean.setEnquiry_state(rst.getString("enquiry_state"));
                bean.setCountry("IN");
                bean.setEnquiry_message(rst.getString("enquiry_message"));
                bean.setEnquiry_date_time(rst.getString("enquiry_date_time"));
                bean.setEnquiry_call_duration(rst.getString("enquiry_call_duration"));
                bean.setEnquiry_reciever_mob(rst.getString("enquiry_reciever_mob"));
                bean.setSender_alternate_email(rst.getString("sender_alternate_email"));
                bean.setSender_alternate_mob(rst.getString("sender_alternate_mob"));
                bean.setDescription(rst.getString("description"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.err.println("Exception------------" + e);
        }

        return list;
    }

    public ArrayList<Enquiry> getAllComplaintDetails(String enquiry_table_id) {
        ArrayList<Enquiry> list = new ArrayList<Enquiry>();

        try {
            String query = " select et.complaint_table_id,es.status,et.enquiry_no, et.sender_name,et.sender_email,et.sender_mob,et.sender_company_name, "
                    + " et.enquiry_address,et.enquiry_city,et.enquiry_state,et.country,et.enquiry_message,et.enquiry_date_time,  "
                    + " et.enquiry_call_duration,et.enquiry_reciever_mob,et.sender_alternate_email,  et.sender_alternate_mob,et.description, "
                    + " kp.key_person_name  from complaint_table et,enquiry_status es,city ct,tehsil th,district dt,division dv,state st, "
                    + " salesmanager_state_mapping ssm,key_person kp  "
                    + " where et.active='Y' and ct.active='Y' and st.active='Y' and dt.active='Y'  and th.active='Y'  and dv.active='Y' "
                    + " and ssm.active='Y' and kp.active='Y'  "
                    + " and kp.key_person_id=ssm.salesman_id and ct.tehsil_id=th.tehsil_id and th.district_id=dt.district_id and "
                    + " ssm.state_id=st.state_id and dt.division_id=dv.division_id and dv.state_id=st.state_id "
                    + " and et.enquiry_status_id=es.enquiry_status_id and dt.district_name=et.description and es.active='Y'  ";
            if (!enquiry_table_id.equals("") && enquiry_table_id != null) {
                query += " and et.complaint_table_id='" + enquiry_table_id + "' ";
            }
            query += " group by et.complaint_table_id ";

            query += " order by et.complaint_table_id desc ";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                Enquiry bean = new Enquiry();
                bean.setEnquiry_table_id(rst.getInt("complaint_table_id"));
//                bean.setEnquiry_source(rst.getString("enquiry_source"));
//                bean.setMarketing_vertical_name(rst.getString("marketing_vertical_name"));
                bean.setStatus(rst.getString("status"));
                bean.setEnquiry_no(rst.getString("enquiry_no"));
                bean.setSender_name(rst.getString("sender_name"));
                bean.setSender_email(rst.getString("sender_email"));
                bean.setSender_mob(rst.getString("sender_mob"));
                bean.setSender_company_name(rst.getString("sender_company_name"));
                bean.setEnquiry_address(rst.getString("enquiry_address"));
                bean.setEnquiry_city(rst.getString("enquiry_city"));
                bean.setEnquiry_state(rst.getString("enquiry_state"));
                bean.setCountry("IN");
                bean.setEnquiry_message(rst.getString("enquiry_message"));
                bean.setEnquiry_date_time(rst.getString("enquiry_date_time"));
                bean.setEnquiry_call_duration(rst.getString("enquiry_call_duration"));
                bean.setEnquiry_reciever_mob(rst.getString("enquiry_reciever_mob"));
                bean.setSender_alternate_email(rst.getString("sender_alternate_email"));
                bean.setSender_alternate_mob(rst.getString("sender_alternate_mob"));
                bean.setDescription(rst.getString("description"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.err.println("Exception------------" + e);
        }

        return list;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("DealersOrderModel closeConnection: " + e);
        }
    }

    public void setConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("DealersOrderModel setConnection error: " + e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageBGColor() {
        return messageBGColor;
    }

}
