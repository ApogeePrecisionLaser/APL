/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.purchase.module;

import static com.dashboard.model.EnquiryModel.timeAgo;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author komal
 */
public class ComparisonChartModel {

    static private Connection connection;
    private String driver, url, user, password;
    static private String message, messageBGColor = "#a2a220";
    static private final String COLOR_OK = "green";
    static private final String COLOR_ERROR = "red";

    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("ComparisonChartModel setConnection() Error: " + e);
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("ComparisonChartModel closeConnection: " + e);
        }
    }

    public void setConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("ComparisonChartModel setConnection error: " + e);
        }
    }

    public static int getCounting() {
        int counting = 100000;
        int count = 0;
        String query = " SELECT comparative_charts_no FROM comparative_charts order by comparative_charts_id desc limit 1 ";
        try {
            PreparedStatement psmt = connection.prepareStatement(query);
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                String comparative_charts_no = rs.getString("comparative_charts_no");
                count = Integer.parseInt(comparative_charts_no.substring(3));
                counting = count;
            }
        } catch (Exception ex) {
            System.out.println("ERROR: in getCounting() in ComparisonChartModel : " + ex);
        }
        return counting + 1;
    }

    public List<PurchaseOrdersBean> getAllQuotations(int key_person_id, String role, int logged_org_office_id) {
        List<PurchaseOrdersBean> list = new ArrayList<PurchaseOrdersBean>();
        String query = "";
        List<String> status_list = new ArrayList<>();

        if (role.equals("Incharge")) {
            query = " select distinct po.quotation_no,po.quotation_id,oo2.org_office_name as org_office_name,oo1.org_office_name as vendor_name, "
                    + " po.date_time,oo1.mobile_no1,s.status,kp.key_person_name,po.req_qty,po.rate,po.lead_time,itn.item_name,m.model "
                    + " from quotation po,item_names itn,model m, "
                    + " org_office oo1,org_office oo2,inventory inv,status s,key_person kp  where po.active='Y' and itn.active='Y' "
                    + " and m.active='Y' and oo2.active='Y' and inv.active='Y' and  po.item_names_id=itn.item_names_id and po.model_id=m.model_id "
                    + " and oo1.org_office_id=po.vendor_id  and po.org_office_id=oo2.org_office_id and kp.active='Y' "
                    + " and kp.key_person_id=po.key_person_id "
                    + " and po.inventory_id=inv.inventory_id and po.key_person_id='" + key_person_id + "' "
                    + " and oo2.org_office_id='" + logged_org_office_id + "' "
                    + " and po.status_id=s.status_id "
                    + " and po.status_id=15  group by itn.item_name order by po.quotation_id ";
        }
        if (!role.equals("Incharge")) {
            query = " select distinct po.quotation_no,po.quotation_id,oo2.org_office_name as org_office_name,oo1.org_office_name as vendor_name, "
                    + " po.date_time,oo1.mobile_no1,s.status,kp.key_person_name,po.req_qty,po.rate,po.lead_time,itn.item_name,m.model "
                    + " from quotation po,item_names itn,model m, "
                    + " org_office oo1,org_office oo2,inventory inv,status s,key_person kp  where po.active='Y' and itn.active='Y' "
                    + " and m.active='Y' and oo2.active='Y' and inv.active='Y' and  po.item_names_id=itn.item_names_id and po.model_id=m.model_id "
                    + " and oo1.org_office_id=po.vendor_id  and po.org_office_id=oo2.org_office_id and kp.active='Y' "
                    + " and kp.key_person_id=po.key_person_id "
                    + " and po.inventory_id=inv.inventory_id and po.key_person_id='" + key_person_id + "' "
                    + " and po.status_id=s.status_id "
                    + " and po.status_id=15  group by itn.item_name order by po.quotation_id ";
        }
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {
                PurchaseOrdersBean bean = new PurchaseOrdersBean();
                bean.setItem_name(rset.getString("item_name"));
                bean.setOrg_office_name(rset.getString("org_office_name"));
                bean.setModel(rset.getString("model"));
                bean.setQty(rset.getString("req_qty"));
                bean.setQuotation_id(rset.getInt("quotation_id"));
                list.add(bean);

            }
        } catch (Exception e) {
            System.out.println("Error:ComparisonChartModel--getAllQuotations()-- " + e);
        }
        return list;
    }

    public List<PurchaseOrdersBean> getAllQuotationDetail(int key_person_id, String role, int logged_org_office_id,
            String item_name, String model) {
        List<PurchaseOrdersBean> list = new ArrayList<PurchaseOrdersBean>();
        String query = "";
        if (role.equals("Incharge")) {
            query = " select distinct po.quotation_no,po.quotation_id,oo2.org_office_name as org_office_name,oo1.org_office_name as vendor_name, "
                    + " po.date_time,oo1.mobile_no1,s.status,kp.key_person_name,po.req_qty,po.rate,po.lead_time,itn.item_name,m.model "
                    + " from quotation po,item_names itn,model m, "
                    + " org_office oo1,org_office oo2,inventory inv,status s,key_person kp  where po.active='Y' and itn.active='Y' "
                    + " and m.active='Y' and oo2.active='Y' and inv.active='Y' and  po.item_names_id=itn.item_names_id and po.model_id=m.model_id "
                    + " and oo1.org_office_id=po.vendor_id  and po.org_office_id=oo2.org_office_id and kp.active='Y' "
                    + " and kp.key_person_id=po.key_person_id "
                    + " and po.inventory_id=inv.inventory_id and po.key_person_id='" + key_person_id + "' "
                    + " and oo2.org_office_id='" + logged_org_office_id + "' "
                    + " and po.status_id=s.status_id and itn.item_name='" + item_name + "' "
                    + " and m.model='" + model + "' "
                    + " and po.status_id=15 group by po.quotation_no ";
        }
        if (!role.equals("Incharge")) {
            query = " select distinct po.quotation_no,po.quotation_id,oo2.org_office_name as org_office_name,oo1.org_office_name as vendor_name, "
                    + " po.date_time,oo1.mobile_no1,s.status,kp.key_person_name,po.req_qty,po.rate,po.lead_time,itn.item_name,m.model "
                    + " from quotation po,item_names itn,model m, "
                    + " org_office oo1,org_office oo2,inventory inv,status s,key_person kp  where po.active='Y' and itn.active='Y' "
                    + " and m.active='Y' and oo2.active='Y' and inv.active='Y' and  po.item_names_id=itn.item_names_id and po.model_id=m.model_id "
                    + " and oo1.org_office_id=po.vendor_id  and po.org_office_id=oo2.org_office_id and kp.active='Y' "
                    + " and kp.key_person_id=po.key_person_id "
                    + " and po.inventory_id=inv.inventory_id and po.key_person_id='" + key_person_id + "' "
                    + " and po.status_id=s.status_id and itn.item_name='" + item_name + "' "
                    + " and m.model='" + model + "' "
                    + " and po.status_id=15 group by po.quotation_no ";
        }
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {
                PurchaseOrdersBean bean = new PurchaseOrdersBean();
                bean.setRate(rset.getString("rate"));
                bean.setModel(rset.getString("model"));
                bean.setItem_name(rset.getString("item_name"));
                bean.setVendor(rset.getString("vendor_name"));
                bean.setLead_time(rset.getInt("lead_time"));
                bean.setQuotation_id(rset.getInt("quotation_id"));
                bean.setQty(rset.getString("req_qty"));
                bean.setPrice(String.valueOf((rset.getInt("req_qty")) * (rset.getInt("rate"))));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error:ComparisonChartModel--getAllQuotations()-- " + e);
        }
        return list;
    }

    public List<PurchaseOrdersBean> getQuotationDetail(int key_person_id, String role, int logged_org_office_id,
            String quotation_id) {
        List<PurchaseOrdersBean> list = new ArrayList<PurchaseOrdersBean>();
        String query = "";
        if (role.equals("Incharge")) {
            query = " select distinct po.quotation_no,po.quotation_id,oo2.org_office_name as org_office_name, "
                    + " oo2.org_office_id as org_office_id,oo1.org_office_name as vendor_name,"
                    + " oo1.org_office_id as vendor_id, "
                    + " po.date_time,oo1.mobile_no1,s.status,kp.key_person_name,po.req_qty,po.rate,po.lead_time,itn.item_name,m.model "
                    + " ,itn.item_names_id,m.model_id "
                    + " from quotation po,item_names itn,model m, "
                    + " org_office oo1,org_office oo2,inventory inv,status s,key_person kp  where po.active='Y' and itn.active='Y' "
                    + " and m.active='Y' and oo2.active='Y' and inv.active='Y' and  po.item_names_id=itn.item_names_id and po.model_id=m.model_id "
                    + " and oo1.org_office_id=po.vendor_id  and po.org_office_id=oo2.org_office_id and kp.active='Y' "
                    + " and kp.key_person_id=po.key_person_id "
                    + " and po.inventory_id=inv.inventory_id and po.key_person_id='" + key_person_id + "' "
                    + " and oo2.org_office_id='" + logged_org_office_id + "' "
                    + " and po.status_id=s.status_id and po.quotation_id='" + quotation_id + "' "
                    + " and po.status_id=15 group by po.quotation_no ";
        }
        if (!role.equals("Incharge")) {
            query = " select distinct po.quotation_no,po.quotation_id,oo2.org_office_name as org_office_name, "
                    + " oo2.org_office_id as org_office_id,oo1.org_office_name as vendor_name, "
                    + " oo1.org_office_id as vendor_id,po.date_time,oo1.mobile_no1,s.status,kp.key_person_name,po.req_qty,po.rate,po.lead_time,itn.item_name,m.model "
                    + " ,itn.item_names_id,m.model_id "
                    + " from quotation po,item_names itn,model m, "
                    + " org_office oo1,org_office oo2,inventory inv,status s,key_person kp  where po.active='Y' and itn.active='Y' "
                    + " and m.active='Y' and oo2.active='Y' and inv.active='Y' and  po.item_names_id=itn.item_names_id and po.model_id=m.model_id "
                    + " and oo1.org_office_id=po.vendor_id  and po.org_office_id=oo2.org_office_id and kp.active='Y' "
                    + " and kp.key_person_id=po.key_person_id "
                    + " and po.inventory_id=inv.inventory_id and po.key_person_id='" + key_person_id + "' "
                    + " and po.status_id=s.status_id and po.quotation_id='" + quotation_id + "' "
                    + " and po.status_id=15 group by po.quotation_no ";
        }
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {
                PurchaseOrdersBean bean = new PurchaseOrdersBean();
                bean.setRate(rset.getString("rate"));
                bean.setModel_id(rset.getInt("model_id"));
                bean.setItem_names_id(rset.getInt("item_names_id"));
                bean.setVendor_id(rset.getInt("vendor_id"));
                bean.setOrg_office_id(rset.getString("org_office_id"));
                bean.setLead_time(rset.getInt("lead_time"));
                bean.setQuotation_id(rset.getInt("quotation_id"));
                bean.setQty(rset.getString("req_qty"));
                bean.setStatus(rset.getString("status"));
                bean.setPrice(String.valueOf((rset.getInt("req_qty")) * (rset.getInt("rate"))));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error:ComparisonChartModel--getAllQuotations()-- " + e);
        }
        return list;
    }

    public int getInventoryId(int item_names_id, int model_id, int logged_org_office_id) {
        String query = " select inv.inventory_id from item_names itn,model m,inventory_basic ib,inventory inv "
                + " where itn.active='Y' and m.active='Y' and ib.active='Y' and inv.active='Y' "
                + " and itn.item_names_id=ib.item_names_id and m.model_id=ib.model_id and ib.inventory_basic_id=inv.inventory_basic_id "
                + " and itn.item_names_id='" + item_names_id + "' "
                + "and m.model_id='" + model_id + "' and ib.org_office_id='" + logged_org_office_id + "' ";
        int id = 0;
        int rowsAffected = 0;
        int count = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                id = rset.getInt("inventory_id");
            }

        } catch (Exception e) {
            System.out.println("ComparisonChartModel getInventoryId Error: " + e);
        }
        return id;
    }

    public static int getRevisionno(int model_id, int item_names_id, int vendor_id, int logged_key_person_id, int quotation_id) {
        int revision = 0;
        try {
            String query = " SELECT max(revision_no) as revision_no FROM quotation where "
                    + "  key_person_id='" + logged_key_person_id + "' "
                    + " and model_id='" + model_id + "' "
                    + " and item_names_id='" + item_names_id + "' and vendor_id='" + vendor_id + "' and quotation_id='" + quotation_id + "' "
                    + " and status_id=15 and active='Y' ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
            System.err.println("ComparisonChartModel getRevisionno() error--------" + e);
        }
        return revision;
    }

    public int insertRecord(PurchaseOrdersBean bean, int logged_org_office_id, String role, int logged_key_person_id) throws SQLException {
        String query = " INSERT INTO purchase_order(order_no,item_names_id,model_id,"
                + " vendor_id,qty,rate,vendor_lead_time,payment,follow_up_frequency,active,order_document_path,inventory_id, "
                + " revision_no,description, "
                + " remark,date_time,org_office_id,key_person_id,status_id) "
                + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
        int rowsAffected = 0;
        int rowsAffected2 = 0;
        int updateRowsAffected = 0;
        int updateRowsAffected2 = 0;

        String query2 = "";
        int inventory_id = getInventoryId(bean.getItem_names_id(), bean.getModel_id(), logged_org_office_id);
        int count = 0;
        int old_quantity = 0;
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
        String date_time = sdf.format(date);
        try {
            PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query);
            psmt.setString(1, bean.getOrder_no());
            psmt.setInt(2, bean.getItem_names_id());
            psmt.setInt(3, bean.getModel_id());
            psmt.setInt(4, bean.getVendor_id());
            psmt.setString(5, bean.getQty());
            psmt.setString(6, bean.getPrice());
            psmt.setInt(7, bean.getLead_time());
            psmt.setString(8, "0");
            psmt.setString(9, "0");
            psmt.setString(10, "Y");
            psmt.setString(11, "");
            psmt.setInt(12, inventory_id);
            psmt.setInt(13, bean.getRevision_no());
            psmt.setString(14, "");
            psmt.setString(15, "");
            psmt.setString(16, date_time);
            psmt.setInt(17, logged_org_office_id);
            psmt.setInt(18, logged_key_person_id);
            psmt.setInt(19, 2);
            rowsAffected = psmt.executeUpdate();

            if (rowsAffected > 0) {
                int revision = ComparisonChartModel.getRevisionno(bean.getModel_id(), bean.getItem_names_id(), bean.getVendor_id(),
                        logged_key_person_id, bean.getQuotation_id());

                String query1 = " SELECT max(revision_no) as revision_no,req_qty,rate,key_person_id,lead_time,quotation_no,inventory_id, "
                        + " scheduled_date,date_time,quotation_id "
                        + " FROM quotation WHERE org_office_id='" + logged_org_office_id + "' "
                        + " and model_id='" + bean.getModel_id() + "' "
                        + " and item_names_id='" + bean.getItem_names_id() + "' and vendor_id='" + bean.getVendor_id() + "' "
                        + " and quotation_id='" + bean.getQuotation_id() + "' "
                        + " and status_id=15 and active='Y' ";

                String query_update = " UPDATE quotation SET active =? "
                        + " WHERE key_person_id=? and model_id=? and item_names_id=? "
                        + " and status_id=? and revision_no=? and vendor_id=?  and quotation_id='" + bean.getQuotation_id() + "' ";

                PreparedStatement pstmt = connection.prepareStatement(query1);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    revision = rs.getInt("revision_no");
                    int qty = rs.getInt("req_qty");
                    String price = rs.getString("rate");
                    String quotation_no = rs.getString("quotation_no");
                    String lead_time = rs.getString("lead_time");
                    String key_person_id = rs.getString("key_person_id");
                    String scheduled_date = rs.getString("scheduled_date");
                    inventory_id = rs.getInt("inventory_id");
                    int quotation_id = rs.getInt("quotation_id");

                    PreparedStatement pstm = connection.prepareStatement(query_update);
                    pstm.setString(1, "N");
                    pstm.setString(2, key_person_id);
                    pstm.setInt(3, bean.getModel_id());
                    pstm.setInt(4, bean.getItem_names_id());
                    pstm.setInt(5, 15);
                    pstm.setInt(6, revision);
                    pstm.setInt(7, bean.getVendor_id());
                    updateRowsAffected = pstm.executeUpdate();

                    if (updateRowsAffected >= 1) {
                        String query_insert = " INSERT INTO quotation(key_person_id,model_id,item_names_id,"
                                + " status_id,req_qty,rate,active,description,revision_no,remark,vendor_id,org_office_id,quotation_no,"
                                + " inventory_id,lead_time,scheduled_date,date_time,quotation_id) "
                                + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
                        revision = rs.getInt("revision_no") + 1;
                        PreparedStatement psmt1 = (PreparedStatement) connection.prepareStatement(query_insert);
                        psmt1.setString(1, key_person_id);
                        psmt1.setInt(2, bean.getModel_id());
                        psmt1.setInt(3, bean.getItem_names_id());
                        psmt1.setInt(4, 16);
                        psmt1.setInt(5, qty);
                        psmt1.setString(6, price);
                        psmt1.setString(7, "Y");
                        psmt1.setString(8, "");
                        psmt1.setInt(9, revision);
                        psmt1.setString(10, "");
                        psmt1.setInt(11, bean.getVendor_id());
                        psmt1.setInt(12, logged_org_office_id);
                        psmt1.setString(13, quotation_no);
                        psmt1.setInt(14, inventory_id);
                        psmt1.setString(15, lead_time);
                        psmt1.setString(16, scheduled_date);
                        psmt1.setString(17, date_time);
                        psmt1.setInt(18, quotation_id);
                        rowsAffected2 = psmt1.executeUpdate();
                    }
                    if (rowsAffected2 > 0) {

                        if (role.equals("Incharge")) {
                            query2 = " select distinct po.quotation_no,po.quotation_id,oo2.org_office_name as org_office_name,oo1.org_office_name as vendor_name, "
                                    + " po.date_time,oo1.mobile_no1,s.status,kp.key_person_name,po.req_qty,po.rate,po.lead_time,itn.item_name,m.model "
                                    + " from quotation po,item_names itn,model m, "
                                    + " org_office oo1,org_office oo2,inventory inv,status s,key_person kp  where po.active='Y' and itn.active='Y' "
                                    + " and m.active='Y' and oo2.active='Y' and inv.active='Y' and  po.item_names_id=itn.item_names_id and po.model_id=m.model_id "
                                    + " and oo1.org_office_id=po.vendor_id  and po.org_office_id=oo2.org_office_id and kp.active='Y' "
                                    + " and kp.key_person_id=po.key_person_id "
                                    + " and po.inventory_id=inv.inventory_id and po.key_person_id='" + key_person_id + "' "
                                    + " and oo2.org_office_id='" + logged_org_office_id + "' "
                                    + " and po.status_id=s.status_id and itn.item_names_id='" + bean.getItem_names_id() + "' "
                                    + " and m.model_id='" + bean.getModel_id() + "' "
                                    + " and po.status_id=15 and po.quotation_id not in('" + bean.getQuotation_id() + "') group by po.quotation_no ";
                        }
                        if (!role.equals("Incharge")) {
                            query2 = " select distinct po.quotation_no,po.quotation_id,oo2.org_office_name as org_office_name,oo1.org_office_name as vendor_name, "
                                    + " po.date_time,oo1.mobile_no1,s.status,kp.key_person_name,po.req_qty,po.rate,po.lead_time,itn.item_name,m.model "
                                    + " from quotation po,item_names itn,model m, "
                                    + " org_office oo1,org_office oo2,inventory inv,status s,key_person kp  where po.active='Y' and itn.active='Y' "
                                    + " and m.active='Y' and oo2.active='Y' and inv.active='Y' and  po.item_names_id=itn.item_names_id and po.model_id=m.model_id "
                                    + " and oo1.org_office_id=po.vendor_id  and po.org_office_id=oo2.org_office_id and kp.active='Y' "
                                    + " and kp.key_person_id=po.key_person_id "
                                    + " and po.inventory_id=inv.inventory_id and po.key_person_id='" + key_person_id + "' "
                                    + " and po.status_id=s.status_id and itn.item_names_id='" + bean.getItem_names_id() + "' "
                                    + " and m.model_id='" + bean.getModel_id() + "' "
                                    + " and po.status_id=15 and po.quotation_id not in('" + bean.getQuotation_id() + "') group by po.quotation_no ";
                        }

                        ResultSet rset2 = connection.prepareStatement(query2).executeQuery();
                        while (rset2.next()) {

                            String query_update2 = " UPDATE quotation SET active ='N' "
                                    + " WHERE quotation_id='" + rset2.getString("quotation_id") + "' ";
                            PreparedStatement pstm2 = connection.prepareStatement(query_update2);
                            updateRowsAffected2 = pstm2.executeUpdate();
                        }

                    }
                   
                }

                message = "Record saved successfully.";
                messageBGColor = COLOR_OK;
            } else {
                message = "Cannot save the record, some error.";
                messageBGColor = COLOR_ERROR;
            }
        } catch (Exception e) {
            System.err.println("ComparisonChartModel insertRecord() Exception---" + e);
        }

        return updateRowsAffected2;
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

    public static String getMessage() {
        return message;
    }

    public static String getMessageBGColor() {
        return messageBGColor;
    }

}
