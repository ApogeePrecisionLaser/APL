package com.dashboard.model;

import com.dashboard.bean.Enquiry;
import java.io.ByteArrayOutputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import static java.time.LocalDateTime.now;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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

    public int insertEnquiries(Enquiry bean) {
        String query = "INSERT INTO enquiry_table(enquiry_source_table_id,marketing_vertical_id,enquiry_status_id,enquiry_no,sender_name,sender_email, "
                + " sender_mob,sender_company_name,enquiry_address,enquiry_city,enquiry_state,country,enquiry_message,enquiry_date_time,enquiry_call_duration,"
                + " enquiry_reciever_mob,sender_alternate_email,sender_alternate_mob, "
                + " revision_no,active,description,assigned_to) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
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
            pstmt.setInt(3, 2);
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
            pstmt.setString(21, "OK");
            pstmt.setInt(22, 117);
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

    public int getSourceTableId(String enquiry_source) {

        String query = " SELECT enquiry_source_table_id from enquiry_source_table where active='Y' ";
        if (!enquiry_source.equals("") && enquiry_source != null) {
            query += " and enquiry_source='" + enquiry_source + "' ";
        }
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
        if (!marketing_vertical.equals("") && marketing_vertical != null) {
            query += " and marketing_vertical_name='" + marketing_vertical + "' ";
        }
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
            String query = " select et.enquiry_table_id,est.enquiry_source,mv.marketing_vertical_name,es.status,et.enquiry_no, "
                    + " et.sender_name,et.sender_email,et.sender_mob,et.sender_company_name, "
                    + " et.enquiry_address,et.enquiry_city,et.enquiry_state,et.country,et.enquiry_message,et.enquiry_date_time, "
                    + " et.enquiry_call_duration,et.enquiry_reciever_mob,et.sender_alternate_email, "
                    + " et.sender_alternate_mob "
                    + " from enquiry_table et,enquiry_source_table est,marketing_vertical mv,enquiry_status es "
                    + " where et.active='Y' and est.active='Y' and mv.active='Y' and es.active='Y' "
                    + " and et.enquiry_source_table_id=est.enquiry_source_table_id "
                    + " and et.marketing_vertical_id=mv.marketing_vertical_id and et.enquiry_status_id=es.enquiry_status_id ";
            
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                Enquiry bean = new Enquiry();
                bean.setEnquiry_table_id(rst.getInt("enquiry_table_id"));
                bean.setEnquiry_source(rst.getString("enquiry_source"));
                bean.setMarketing_vertical_name(rst.getString("marketing_vertical_name"));
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
                bean.setEnquiry_date_time(rst.getString("enquiry_date_time"));
                bean.setEnquiry_call_duration(rst.getString("enquiry_call_duration"));
                bean.setEnquiry_reciever_mob(rst.getString("enquiry_reciever_mob"));
                bean.setSender_alternate_email(rst.getString("sender_alternate_email"));
                bean.setSender_alternate_mob(rst.getString("sender_alternate_mob"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.err.println("Exception------------" + e);
        }

        return list;
    }

    public ArrayList<Enquiry> getAllEnquiriesDetails(String enquiry_table_id) {
        ArrayList<Enquiry> list = new ArrayList<Enquiry>();

        try {
            String query = " select et.enquiry_table_id,est.enquiry_source,mv.marketing_vertical_name,es.status,et.enquiry_no, "
                    + " et.sender_name,et.sender_email,et.sender_mob,et.sender_company_name, "
                    + " et.enquiry_address,et.enquiry_city,et.enquiry_state,et.country,et.enquiry_message,et.enquiry_date_time, "
                    + " et.enquiry_call_duration,et.enquiry_reciever_mob,et.sender_alternate_email, "
                    + " et.sender_alternate_mob "
                    + " from enquiry_table et,enquiry_source_table est,marketing_vertical mv,enquiry_status es "
                    + " where et.active='Y' and est.active='Y' and mv.active='Y' and es.active='Y' "
                    + " and et.enquiry_source_table_id=est.enquiry_source_table_id "
                    + " and et.marketing_vertical_id=mv.marketing_vertical_id and et.enquiry_status_id=es.enquiry_status_id ";
            if (!enquiry_table_id.equals("") && enquiry_table_id != null) {
                query += " and et.enquiry_table_id='" + enquiry_table_id + "' ";
            }

            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                Enquiry bean = new Enquiry();
                bean.setEnquiry_table_id(rst.getInt("enquiry_table_id"));
                bean.setEnquiry_source(rst.getString("enquiry_source"));
                bean.setMarketing_vertical_name(rst.getString("marketing_vertical_name"));
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
                bean.setEnquiry_date_time(rst.getString("enquiry_date_time"));
                bean.setEnquiry_call_duration(rst.getString("enquiry_call_duration"));
                bean.setEnquiry_reciever_mob(rst.getString("enquiry_reciever_mob"));
                bean.setSender_alternate_email(rst.getString("sender_alternate_email"));
                bean.setSender_alternate_mob(rst.getString("sender_alternate_mob"));
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
