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
public class PurchaseOrdersModel {

    static private Connection connection;
    private String driver, url, user, password;
    static private String message, messageBGColor = "#a2a220";
    static private final String COLOR_OK = "green";
    static private final String COLOR_ERROR = "red";

    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    PdfTemplate total;

    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("PurchaseOrdersModel setConnection() Error: " + e);
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("PurchaseOrdersModel closeConnection: " + e);
        }
    }

    public void setConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("PurchaseOrdersModel setConnection error: " + e);
        }
    }

    public List<PurchaseOrdersBean> getAllExistingOrders(int key_person_id, String role) {
        List<PurchaseOrdersBean> list = new ArrayList<PurchaseOrdersBean>();
        String query = "";
        List<String> status_list = new ArrayList<>();
        if (role.equals("Admin") || role.equals("Super Admin")) {
            query = " select distinct po.order_no,oo1.org_office_name as vendor_name,oo2.org_office_name as org_office_name, "
                    + " po.date_time,oo1.mobile_no1,sum(po.rate) as rate,s.status,kp.key_person_name "
                    + " from purchase_order po,item_names itn,model m, "
                    + " org_office oo1,org_office oo2,inventory inv,status s,key_person kp "
                    + " where po.active='Y' and itn.active='Y' and m.active='Y' and oo1.active='Y' and oo2.active='Y' and inv.active='Y' and "
                    + " po.item_names_id=itn.item_names_id and po.model_id=m.model_id and po.vendor_id=oo1.org_office_id "
                    + " and po.org_office_id=oo2.org_office_id and kp.active='Y' and kp.key_person_id=po.key_person_id "
                    + " and po.inventory_id=inv.inventory_id and po.status_id=s.status_id "
                    + " group by po.order_no order by purchase_order_id desc ";
        } else {
            query = " select distinct po.order_no,oo1.org_office_name as vendor_name,oo2.org_office_name as org_office_name, "
                    + " po.date_time,oo1.mobile_no1,sum(po.rate) as rate,s.status,kp.key_person_name "
                    + " from purchase_order po,item_names itn,model m, "
                    + " org_office oo1,org_office oo2,inventory inv,status s,key_person kp "
                    + " where po.active='Y' and itn.active='Y' and m.active='Y' and oo1.active='Y' and oo2.active='Y' and inv.active='Y' and "
                    + " po.item_names_id=itn.item_names_id and po.model_id=m.model_id and po.vendor_id=oo1.org_office_id "
                    + " and po.org_office_id=oo2.org_office_id and kp.active='Y' and kp.key_person_id=po.key_person_id "
                    + " and po.inventory_id=inv.inventory_id and po.key_person_id='" + key_person_id + "' and po.status_id=s.status_id "
                    + " group by po.order_no order by purchase_order_id desc ";
        }
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {
                PurchaseOrdersBean bean = new PurchaseOrdersBean();
                bean.setOrder_no(rset.getString("order_no"));
                bean.setVendor(rset.getString("vendor_name"));

                String order_date = rset.getString("date_time");
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
                Date date = new Date();
                String currentDateString = dateFormatter.format(date);
                Date currentDate = dateFormatter.parse(currentDateString);
                String pastTimeInSecond = order_date;
                Date pastDate = dateFormatter.parse(pastTimeInSecond);
                String time_ago = timeAgo(currentDate, pastDate);
                bean.setTime_ago(time_ago);
                bean.setMobile(rset.getString("mobile_no1"));
                bean.setPrice(rset.getString("rate"));
                bean.setStatus(rset.getString("status"));
                bean.setOrg_office_name(rset.getString("org_office_name"));
                bean.setCustomer_name(rset.getString("key_person_name"));

                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error:PurchaseOrdersModel--getAllExistingOrders()-- " + e);
        }
        return list;
    }

    public List<PurchaseOrdersBean> getAllPendingOrders(int key_person_id, String role) {
        List<PurchaseOrdersBean> list = new ArrayList<PurchaseOrdersBean>();
        String query = "";
        if (role.equals("Admin") || role.equals("Super Admin")) {
            query = " select distinct po.order_no,oo1.org_office_name as vendor_name,oo2.org_office_name as org_office_name, "
                    + " po.date_time,oo1.mobile_no1,sum(po.rate) as rate,s.status,kp.key_person_name "
                    + " from purchase_order po,item_names itn,model m, "
                    + " org_office oo1,org_office oo2,inventory inv,status s,key_person kp "
                    + " where po.active='Y' and itn.active='Y' and m.active='Y' and oo1.active='Y' and oo2.active='Y' and kp.active='Y' "
                    + " and inv.active='Y' and po.org_office_id=oo2.org_office_id and kp.key_person_id=po.key_person_id  and "
                    + " po.item_names_id=itn.item_names_id and po.model_id=m.model_id and po.vendor_id=oo1.org_office_id "
                    + " and po.inventory_id=inv.inventory_id and s.status_id=po.status_id and s.status_id=2 "
                    + " group by po.order_no order by purchase_order_id desc ";
        } else {
            query = " select distinct po.order_no,oo1.org_office_name as vendor_name,oo2.org_office_name as org_office_name, "
                    + " po.date_time,oo1.mobile_no1,sum(po.rate) as rate,s.status,kp.key_person_name "
                    + " from purchase_order po,item_names itn,model m, "
                    + " org_office oo1,org_office oo2,inventory inv,status s,key_person kp "
                    + " where po.active='Y' and itn.active='Y' and m.active='Y' and oo1.active='Y' and oo2.active='Y' and inv.active='Y' and "
                    + " po.org_office_id=oo2.org_office_id and kp.key_person_id=po.key_person_id  and "
                    + " po.item_names_id=itn.item_names_id and po.model_id=m.model_id and po.vendor_id=oo1.org_office_id and s.status_id=2 "
                    + " and po.inventory_id=inv.inventory_id and po.key_person_id='" + key_person_id + "' and s.status_id=po.status_id "
                    + " group by po.order_no order by purchase_order_id desc ";
        }
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;

            while (rset.next()) {
                PurchaseOrdersBean bean = new PurchaseOrdersBean();
                bean.setOrder_no(rset.getString("order_no"));
                bean.setVendor(rset.getString("vendor_name"));
                String order_date = rset.getString("date_time");
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
                Date date = new Date();
                String currentDateString = dateFormatter.format(date);
                Date currentDate = dateFormatter.parse(currentDateString);
                String pastTimeInSecond = order_date;
                Date pastDate = dateFormatter.parse(pastTimeInSecond);
                String time_ago = timeAgo(currentDate, pastDate);
                bean.setTime_ago(time_ago);
                bean.setMobile(rset.getString("mobile_no1"));
                bean.setPrice(rset.getString("rate"));
                bean.setStatus(rset.getString("status"));
                bean.setOrg_office_name(rset.getString("org_office_name"));
                bean.setCustomer_name(rset.getString("key_person_name"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error:PurchaseOrdersModel--getAllPendingOrders()-- " + e);
        }
        return list;
    }

    public List<PurchaseOrdersBean> getOrderDetail(String order_no) {
        List<PurchaseOrdersBean> list = new ArrayList<PurchaseOrdersBean>();

        String query = " select po.order_no,itn.item_name,m.model,m.model_no,m.part_no,oo1.org_office_name as vendor_name, "
                + " po.qty,m.model_id,po.rate,po.purchase_order_id,po.vendor_lead_time, "
                + " kp.key_person_name,oo2.org_office_name as org_office_name,"
                + " po.date_time,oo1.mobile_no1 as vendor_mobile,oo1.email_id1 as vendor_email,oo1.address_line1 "
                + " as vendor_add1,oo1.address_line2 "
                + " as vendor_add2,oo1.address_line3 as vendor_add3,ct1.city_name as vendor_city,ct2.city_name as customer_city "
                + " ,oo2.mobile_no1 as customer_mobile,oo2.email_id1 as customer_email,oo2.address_line1 as customer_add1,oo2.address_line2 "
                + " as customer_add2,oo2.address_line3 as customer_add3,s.status "
                + " from purchase_order po,item_names itn,model m,org_office oo1,org_office oo2,inventory inv,city ct1,city ct2,key_person kp,status s "
                + " where po.active='Y' "
                + " and ct1.active='Y' and ct1.city_id=oo1.city_id  and itn.active='Y' and m.active='Y' and oo1.active='Y' and inv.active='Y' "
                + " and  po.item_names_id=itn.item_names_id  and po.model_id=m.model_id and po.vendor_id=oo1.org_office_id and kp.active='Y' "
                + " and ct2.active='Y' and ct2.city_id=oo2.city_id and s.status_id=po.status_id"
                + " and po.inventory_id=inv.inventory_id  and oo2.active='Y' and oo2.org_office_id=po.org_office_id "
                + " and kp.key_person_id=po.key_person_id ";
        if (!order_no.equals("") && order_no != null) {
            query += " and po.order_no='" + order_no + "' ";
        }

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {
                PurchaseOrdersBean bean = new PurchaseOrdersBean();
                bean.setOrder_no(rset.getString("order_no"));
                bean.setVendor(rset.getString("vendor_name"));
                bean.setItem_name(rset.getString("item_name"));
                bean.setModel(rset.getString("model"));
                bean.setDate_time(rset.getString("date_time"));
                bean.setVendor_mobile(rset.getString("vendor_mobile"));
                bean.setVendor_email(rset.getString("vendor_email"));
                bean.setVendor_address(rset.getString("vendor_add1") + " " + rset.getString("vendor_add2") + " " + rset.getString("vendor_add3") + "," + rset.getString("vendor_city"));
                bean.setCustomer_mobile(rset.getString("customer_mobile"));
                bean.setCustomer_email(rset.getString("customer_email"));
                bean.setCustomer_address(rset.getString("customer_add1") + " " + rset.getString("customer_add2") + " " + rset.getString("customer_add3") + "," + rset.getString("customer_city"));
                bean.setCustomer_name(rset.getString("key_person_name"));
                bean.setCustomer_office_name(rset.getString("org_office_name"));
                bean.setStatus(rset.getString("status"));
                bean.setPurchase_order_id(rset.getInt("purchase_order_id"));
                bean.setPrice(rset.getString("rate"));
                bean.setLead_time(rset.getInt("vendor_lead_time"));

                String model_no = rset.getString("model_no");
                String part_no = rset.getString("part_no");
                if (model_no.equals("")) {
                    bean.setModel_no(part_no);
                }
                if (part_no.equals("")) {
                    bean.setModel_no(model_no);
                }
                bean.setQty(rset.getString("qty"));
                String query_img = " select iid.image_path,iid.image_name  "
                        + " from model m,item_image_details iid "
                        + " where m.active='Y' and iid.active='Y'  "
                        + " and m.model_id=iid.model_id and m.model_id='" + rset.getString("model_id") + "' ";
                ResultSet rset2 = connection.prepareStatement(query_img).executeQuery();

                while (rset2.next()) {
                    if (rset2.getString("image_path").equals("")) {
                        bean.setImage_path("");
                        bean.setImage_name("");
                    }
                    bean.setImage_path(rset2.getString("image_path"));
                    bean.setImage_name(rset2.getString("image_name"));
                }
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error:PurchaseOrdersModel--getOrderDetail()-- " + e);
        }
        return list;
    }

    public static int getRevisionno(int model_id, int item_names_id, int vendor_id, int logged_key_person_id) {
        int revision = 0;
        try {
            String query = " SELECT max(revision_no) as revision_no FROM purchase_order_cart_table where "
                    + "  key_person_id='" + logged_key_person_id + "' "
                    + " and model_id='" + model_id + "' "
                    + " and item_names_id='" + item_names_id + "' and vendor_id='" + vendor_id + "' and cart_status_id=1 and active='Y' ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
            System.err.println("PurchaseOrdersModel getRevisionno() error--------" + e);
        }
        return revision;
    }
   
    
    public static int getRevisionnoForPurchaseOrder(String order_no) {
        int revision = 0;
        try {
            String query = " SELECT max(revision_no) as revision_no FROM purchase_order where "
                    + " order_no='" + order_no + "' and active='Y' ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            
            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
            System.err.println("PurchaseOrdersModel getRevisionno() error--------" + e);
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
//        int item_id = getItemId(bean.getItem_name());
//        int model_id = getModelId(bean.getModel());
//        int vendor_id = getVendorId(bean.getVendor(), item_id);
//        if (logged_org_office_id == 0) {
//            logged_org_office_id = 70;
//        }
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
            psmt.setString(7, "0");
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
                int revision = PurchaseOrdersModel.getRevisionno(bean.getModel_id(), bean.getItem_names_id(), bean.getVendor_id(), logged_key_person_id);

                String query1 = " SELECT max(revision_no) as revision_no,quantity,price,key_person_id "
                        + " FROM purchase_order_cart_table WHERE org_office_id='" + logged_org_office_id + "' "
                        + " and model_id='" + bean.getModel_id() + "' "
                        + " and item_names_id='" + bean.getItem_names_id() + "' and vendor_id='" + bean.getVendor_id() + "' "
                        + " and cart_status_id=1 and active='Y' ";

                String query_update = " UPDATE purchase_order_cart_table SET active =? "
                        + " WHERE key_person_id=? and model_id=? and item_names_id=? "
                        + " and cart_status_id=? and revision_no=? and vendor_id=? ";

                PreparedStatement pstmt = connection.prepareStatement(query1);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    revision = rs.getInt("revision_no");
                    int qty = rs.getInt("quantity");
                    String price = rs.getString("price");
                    String key_person_id = rs.getString("key_person_id");

                    PreparedStatement pstm = connection.prepareStatement(query_update);
                    pstm.setString(1, "N");
                    pstm.setString(2, key_person_id);
                    pstm.setInt(3, bean.getModel_id());
                    pstm.setInt(4, bean.getItem_names_id());
                    pstm.setInt(5, 1);
                    pstm.setInt(6, revision);
                    pstm.setInt(7, bean.getVendor_id());
                    updateRowsAffected = pstm.executeUpdate();

                    if (updateRowsAffected >= 1) {
                        String query_insert = " INSERT INTO purchase_order_cart_table(key_person_id,model_id,item_names_id,"
                                + " cart_status_id,quantity,price,active,description,revision_no,remark,vendor_id,org_office_id) "
                                + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?) ";
                        revision = rs.getInt("revision_no") + 1;
                        PreparedStatement psmt1 = (PreparedStatement) connection.prepareStatement(query_insert);
                        psmt1.setString(1, key_person_id);
                        psmt1.setInt(2, bean.getModel_id());
                        psmt1.setInt(3, bean.getItem_names_id());
                        psmt1.setInt(4, 2);
                        psmt1.setInt(5, qty);
                        psmt1.setString(6, price);
                        psmt1.setString(7, "Y");
                        psmt1.setString(8, "");
                        psmt1.setInt(9, 0);
                        psmt1.setString(10, "");
                        psmt1.setInt(11, bean.getVendor_id());
                        psmt1.setInt(12, logged_org_office_id);
                        rowsAffected2 = psmt1.executeUpdate();
                    }
                }

                message = "Record saved successfully.";
                messageBGColor = COLOR_OK;
            } else {
                message = "Cannot save the record, some error.";
                messageBGColor = COLOR_ERROR;
            }
        } catch (Exception e) {
            System.err.println("PurchaseOrdersModel insertRecord() Exception---" + e);
        }

        return rowsAffected;
    }

    public int getItemId(String item_name) {
        String query = " SELECT item_names_id FROM item_names WHERE item_name = '" + item_name + "' and active='Y' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("item_names_id");
        } catch (Exception e) {
            System.out.println("PurchaseOrdersModel getItemId Error: " + e);
        }
        return id;
    }

    public int getModelId(String model) {
        String query = " SELECT model_id FROM model WHERE model = '" + model + "' and active='Y' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("model_id");
        } catch (Exception e) {
            System.out.println("PurchaseOrdersModel getModelId Error: " + e);
        }
        return id;
    }

    public int getVendorId(String vendor, int item_id) {
        String query = " SELECT oo.org_office_id,count(*) as count FROM vendor_item_map vim,item_names itn,org_office oo "
                + " WHERE  oo.org_office_name = '" + vendor + "' and itn.item_names_id='" + item_id + "' "
                + " and oo.active='Y' and vim.active='Y' and itn.active='Y' "
                + " and vim.org_office_id=oo.org_office_id  and vim.item_names_id=itn.item_names_id  ";
        int id = 0;
        int rowsAffected = 0;
        int count = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                count = rset.getInt("count");
                id = rset.getInt("org_office_id");
            }

            int org_office_id = getOrgOfficeId(vendor);
            if (count == 0) {
                String item_auth_query = " INSERT INTO vendor_item_map(item_names_id,description, "
                        + " revision_no,active,remark,org_office_id) "
                        + " VALUES(?,?,?,?,?,?) ";
                PreparedStatement pstmt_auth = connection.prepareStatement(item_auth_query, Statement.RETURN_GENERATED_KEYS);
                pstmt_auth.setInt(1, item_id);
                pstmt_auth.setString(2, "");
                pstmt_auth.setInt(3, 0);
                pstmt_auth.setString(4, "Y");
                pstmt_auth.setString(5, "OK");
                pstmt_auth.setInt(6, org_office_id);
                rowsAffected = pstmt_auth.executeUpdate();
                id = org_office_id;
            }
        } catch (Exception e) {
            System.out.println("PurchaseOrdersModel getVendorId Error: " + e);
        }
        return id;
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
            System.out.println("PurchaseOrdersModel getInventoryId Error: " + e);
        }
        return id;
    }

    public int getOrgOfficeId(String org_office) {
        String query = " SELECT org_office_id FROM org_office WHERE org_office_name = '" + org_office + "' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("org_office_id");
        } catch (Exception e) {
            System.out.println("PurchaseOrdersModel getOrgOfficeId Error: " + e);
        }
        return id;
    }

    public List<String> getVendorName(String q) {
        List<String> list = new ArrayList<String>();

        String query = " SELECT oo.org_office_name FROM org_office oo where "
                + " oo.active='Y' and oo.office_type_id=7 group by oo.org_office_name ORDER BY oo.org_office_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {
                String org_office_name = (rset.getString("org_office_name"));
                list.add(org_office_name);
                count++;
            }
        } catch (Exception e) {
            System.out.println("Error:PurchaseOrdersModel--getVendorName()-- " + e);
        }

        return list;
    }

    public List<String> getOrgOffice(String q) {
        List<String> list = new ArrayList<String>();

        String query = " SELECT oo.org_office_name FROM org_office oo where "
                + " oo.active='Y' and oo.office_type_id not in(7,3) group by oo.org_office_name ORDER BY oo.org_office_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {
                String org_office_name = (rset.getString("org_office_name"));
                list.add(org_office_name);
                count++;
            }
        } catch (Exception e) {
            System.out.println("Error:PurchaseOrdersModel--getOrgOffice()-- " + e);
        }

        return list;
    }

    public String mapToVendor(String item_names_id, String vendor) {
        int rowsAffected = 0;

        int map_count = 0;
        try {
            int org_office_id = getOrgOfficeId(vendor);
            String item_auth_query = " INSERT INTO vendor_item_map(item_names_id,description, "
                    + " revision_no,active,remark,org_office_id) "
                    + " VALUES(?,?,?,?,?,?) ";

            String query4_count = " SELECT count(*) as count FROM vendor_item_map WHERE "
                    + " item_names_id='" + item_names_id + "' and org_office_id='" + org_office_id + "' "
                    + " and active='Y' ";
            int auth_map_count = 0;
            PreparedStatement pstmt1 = connection.prepareStatement(query4_count);
            ResultSet rs1 = pstmt1.executeQuery();
            while (rs1.next()) {
                auth_map_count = rs1.getInt("count");
            }

            if (auth_map_count > 0) {
                message = "Vendor has already mapped to this item!..";
                messageBGColor = COLOR_ERROR;
            } else {
                PreparedStatement pstmt_auth = connection.prepareStatement(item_auth_query);
                pstmt_auth.setString(1, item_names_id);
                pstmt_auth.setString(2, "");
                pstmt_auth.setInt(3, 0);
                pstmt_auth.setString(4, "Y");
                pstmt_auth.setString(5, "OK");
                pstmt_auth.setInt(6, org_office_id);
                rowsAffected = pstmt_auth.executeUpdate();
            }

        } catch (Exception e) {
            System.out.println("PurchaseOrdersModel mapToVendor() Error: " + e);
        }
        if (rowsAffected
                > 0) {
            message = "Record saved successfully.";
            messageBGColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            messageBGColor = COLOR_ERROR;
        }
        return message;

    }

    public JSONArray getData(String user_role, int key_person_id, String vendor_name, String org_office) {
        JSONObject obj = new JSONObject();
        JSONArray arrayObj = new JSONArray();
        String query = "";
        if (user_role.equals("Incharge")) {
            query = "  SELECT itn.item_name,m.model,inv.stock_quantity,ib.min_quantity,m.model_id,oo.org_office_name,itn.item_names_id "
                    + " FROM item_names itn,manufacturer mr,manufacturer_item_map mim,model m,org_office oo,vendor_item_map vim "
                    + " ,item_authorization ia,designation d,key_person kp,inventory_basic ib,inventory inv  where oo.active='Y' "
                    + " and vim.active='Y' and vim.org_office_id=oo.org_office_id and itn.item_names_id=vim.item_names_id  and "
                    + " mr.manufacturer_id=mim.manufacturer_id and itn.item_names_id=mim.item_names_id and itn.active='Y' "
                    + " and ib.active='Y'  and inv.active='Y'  and mr.active='Y' and mim.active='Y' and itn.is_super_child='Y' "
                    + " and ia.active='Y'  and d.active='Y'  and kp.active='Y' and kp.designation_id=d.designation_id and "
                    + " ia.key_person_id=kp.key_person_id and ib.inventory_basic_id=inv.inventory_basic_id "
                    + " and ib.item_names_id=itn.item_names_id and "
                    + " inv.key_person_id=kp.key_person_id and m.active='Y' and m.manufacturer_item_map_id=mim.manufacturer_item_map_id "
                    + " and ib.model_id=m.model_id "
                    + " and itn.item_names_id=ia.item_names_id  and  kp.key_person_id='" + key_person_id + "'  ";
            if (!vendor_name.equals("") && vendor_name != null) {
                query += " and oo.org_office_name='" + vendor_name + "' ";
            }
            query += " group by itn.item_code ORDER BY itn.item_code ";
        } else {
            query = "  SELECT itn.item_name,m.model,inv.stock_quantity,ib.min_quantity,m.model_id,oo1.org_office_name,itn.item_names_id "
                    + " FROM item_names itn,manufacturer mr,manufacturer_item_map mim,model m,org_office oo1,org_office oo2,vendor_item_map vim "
                    + " ,item_authorization ia,designation d,key_person kp,inventory_basic ib,inventory inv  where oo1.active='Y' and oo2.active='Y' "
                    + " and vim.active='Y' and vim.org_office_id=oo1.org_office_id and itn.item_names_id=vim.item_names_id  and "
                    + " mr.manufacturer_id=mim.manufacturer_id and itn.item_names_id=mim.item_names_id and itn.active='Y' "
                    + " and ib.active='Y'  and inv.active='Y'  and mr.active='Y' and mim.active='Y' and itn.is_super_child='Y' "
                    + " and ia.active='Y'  and d.active='Y'  and kp.active='Y' and kp.designation_id=d.designation_id and "
                    + " ia.key_person_id=kp.key_person_id and ib.inventory_basic_id=inv.inventory_basic_id "
                    + " and ib.item_names_id=itn.item_names_id and ib.org_office_id=oo2.org_office_id and "
                    + " inv.key_person_id=kp.key_person_id and m.active='Y' and m.manufacturer_item_map_id=mim.manufacturer_item_map_id "
                    + " and ib.model_id=m.model_id "
                    + " and itn.item_names_id=ia.item_names_id and d.designation_id=5  ";
            if (!vendor_name.equals("") && vendor_name != null) {
                query += " and oo1.org_office_name='" + vendor_name + "' ";
            }
            if (!org_office.equals("") && org_office != null) {
                query += " and oo2.org_office_name='" + org_office + "' ";
            }

            query += " group by itn.item_code ORDER BY itn.item_code ";
        }

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;

            while (rset.next()) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("item_name", rset.getString("item_name"));
                jsonObj.put("model", rset.getString("model"));
                jsonObj.put("stock_quantity", rset.getString("stock_quantity"));
                jsonObj.put("min_quantity", rset.getString("min_quantity"));
                jsonObj.put("model_id", rset.getString("model_id"));
                jsonObj.put("item_names_id", rset.getString("item_names_id"));
                jsonObj.put("vendor", rset.getString("org_office_name"));

                int stock_qty = rset.getInt("stock_quantity");
                int min_quantity = rset.getInt("min_quantity");
                if (stock_qty == 0) {
                    jsonObj.put("color", "#ff000091");
                }
                if (stock_qty <= min_quantity) {
                    jsonObj.put("color", "#ffff0080");
                }
                arrayObj.add(jsonObj);
            }

        } catch (Exception e) {
            System.err.println("PurchaseOrdersModel getData() Exception--" + e);
        }
        return arrayObj;
    }

    public List<String> getModelName(String q, String item_name, String vendor_name) {
        List<String> list = new ArrayList<String>();

        String query = " select m.model from manufacturer_item_map mim,model m,manufacturer mr,item_names itn "
                + " where mim.manufacturer_item_map_id=m.manufacturer_item_map_id and "
                + " mr.manufacturer_id=mim.manufacturer_id and mim.item_names_id=itn.item_names_id and mim.active='Y' and mr.active='Y' "
                + " and m.active='Y' and itn.active='Y' ";

        if (!item_name.equals("") && item_name != null) {
            query += " and itn.item_name='" + item_name + "' ";
        }

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String model = (rset.getString("model"));
                if (model.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(model);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such model  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:PurchaseOrdersModel--getModelName()-- " + e);
        }
        return list;
    }

    public List<PurchaseOrdersBean> getNewOrderData(String user_role, int key_person_id, String org_office) {
        List<PurchaseOrdersBean> list = new ArrayList<>();
        String query = "";
        if (user_role.equals("Incharge")) {
            query = "  SELECT itn.item_names_id,itn.item_name,m.model,m.model_id,inv.stock_quantity,ib.min_quantity,oo.org_office_name "
                    + " FROM item_names itn,manufacturer mr,manufacturer_item_map mim,model m,org_office oo "
                    + " ,item_authorization ia,designation d,key_person kp,inventory_basic ib,inventory inv  where oo.active='Y' "
                    + " and "
                    + " mr.manufacturer_id=mim.manufacturer_id and itn.item_names_id=mim.item_names_id and itn.active='Y' "
                    + " and ib.active='Y'  and inv.active='Y'  and mr.active='Y' and mim.active='Y' and itn.is_super_child='Y' "
                    + " and ia.active='Y'  and d.active='Y'  and kp.active='Y' and kp.designation_id=d.designation_id and "
                    + " ia.key_person_id=kp.key_person_id and ib.inventory_basic_id=inv.inventory_basic_id "
                    + " and ib.item_names_id=itn.item_names_id and "
                    + " inv.key_person_id=kp.key_person_id and m.active='Y' and m.manufacturer_item_map_id=mim.manufacturer_item_map_id "
                    + " and ib.model_id=m.model_id "
                    + " and itn.item_names_id=ia.item_names_id  and  kp.key_person_id='" + key_person_id + "'  ";
            query += " group by itn.item_code ORDER BY itn.item_code ";
        } else {
            query = "  SELECT itn.item_names_id,itn.item_name,m.model,m.model_id,inv.stock_quantity,ib.min_quantity,oo.org_office_name "
                    + " FROM item_names itn,manufacturer mr,manufacturer_item_map mim,model m,org_office oo "
                    + " ,item_authorization ia,designation d,key_person kp,inventory_basic ib,inventory inv  where oo.active='Y' "
                    + " and "
                    + " mr.manufacturer_id=mim.manufacturer_id and itn.item_names_id=mim.item_names_id and itn.active='Y' "
                    + " and ib.active='Y'  and inv.active='Y'  and mr.active='Y' and mim.active='Y' and itn.is_super_child='Y' "
                    + " and ia.active='Y'  and d.active='Y'  and kp.active='Y' and kp.designation_id=d.designation_id and "
                    + " ia.key_person_id=kp.key_person_id and ib.inventory_basic_id=inv.inventory_basic_id "
                    + " and ib.item_names_id=itn.item_names_id and "
                    + " inv.key_person_id=kp.key_person_id and m.active='Y' and m.manufacturer_item_map_id=mim.manufacturer_item_map_id "
                    + " and ib.model_id=m.model_id  and ib.org_office_id=oo.org_office_id  "
                    + " and itn.item_names_id=ia.item_names_id  and  d.designation_id='5' ";
            if (!org_office.equals("") && org_office != null) {
                query += " and oo.org_office_name='" + org_office + "' ";
            }

        }

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;

            while (rset.next()) {

                PurchaseOrdersBean bean = new PurchaseOrdersBean();
                bean.setItem_name(rset.getString("item_name"));
                bean.setModel(rset.getString("model"));
                bean.setStock_qty(rset.getInt("stock_quantity"));
                bean.setMin_qty(rset.getInt("min_quantity"));
                bean.setItem_names_id(rset.getInt("item_names_id"));
                bean.setOrg_office_name(rset.getString("org_office_name"));
                bean.setModel_id(rset.getInt("model_id"));

                int stock_qty = rset.getInt("stock_quantity");
                int min_quantity = rset.getInt("min_quantity");
                if (stock_qty == 0) {
                    bean.setColor("#ff000091");
                }
                if (stock_qty <= min_quantity) {
                    bean.setColor("#ffff0080");
                }
                String query_vendor = " select oo.org_office_name from vendor_item_map vim,item_names itn,org_office oo "
                        + " where vim.active='Y' and itn.active='Y' and oo.active='Y' and vim.item_names_id=itn.item_names_id and "
                        + " vim.org_office_id=oo.org_office_id and oo.office_type_id=7 and itn.item_names_id='" + rset.getString("item_names_id") + "' ";

                String vendor = "";
                ResultSet rset2 = connection.prepareStatement(query_vendor).executeQuery();
                while (rset2.next()) {

                    vendor = rset2.getString("org_office_name");
                    bean.setVendor(vendor);
                }
                if (vendor.equals("")) {
                    bean.setVendor("");
                }
                list.add(bean);
            }

        } catch (Exception e) {
            System.err.println("PurchaseOrdersModel getNewOrderData Exception--" + e);
        }
        return list;
    }

    public int getCounting() {
        int counting = 100000;
        int count = 0;
        String query = " SELECT order_no FROM purchase_order order by purchase_order_id desc limit 1 ";
        try {
            PreparedStatement psmt = connection.prepareStatement(query);
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                String order_no = rs.getString("order_no");
//                String order_no_arr[] = order_no.split("_");
//                int length = (order_no_arr.length) - 1;
                count = Integer.parseInt(order_no.substring(2));
                counting = count;
            }
        } catch (Exception ex) {
            System.out.println("ERROR: in getCounting() in PurchaseOrdersModel : " + ex);
        }
        return counting + 1;
    }

    public int getCurrentQuantity(String item_names_id, String model_id, String vendor, int logged_key_person_id) {
        int current_qty = 0;
        int vendor_id = getOrgOfficeId(vendor);
        try {
            String query = " SELECT quantity FROM purchase_order_cart_table WHERE key_person_id='" + logged_key_person_id + "' "
                    + " and model_id='" + model_id + "' and vendor_id='" + vendor_id + "' "
                    + " and item_names_id='" + item_names_id + "' and cart_status_id=1 and active='Y' ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                current_qty = rset.getInt("quantity");
            }
        } catch (Exception e) {
            System.err.println("PurchaseOrdersModel getCurrentQuantity() error--------" + e);
        }
        return current_qty;
    }

    public static int getRevisionno(PurchaseOrdersBean bean, int logged_key_person_id, int item_id, int vendor_id) {
        int revision = 0;
        try {

            String query = " SELECT max(revision_no) as revision_no FROM purchase_order_cart_table where "
                    + "  key_person_id='" + logged_key_person_id + "' "
                    + " and model_id='" + bean.getModel_id() + "' and vendor_id='" + vendor_id + "' "
                    + " and item_names_id='" + item_id + "' and cart_status_id=1 and active='Y' ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
            System.err.println("PurchaseOrdersModel getRevisionno() error--------" + e);
        }
        return revision;
    }

    public int addToCart(PurchaseOrdersBean bean, int logged_key_person_id, String org_office_name) throws SQLException {
        String query = " INSERT INTO purchase_order_cart_table(key_person_id,model_id,item_names_id, "
                + " cart_status_id,quantity,price,active,description,revision_no,remark,vendor_id,org_office_id) "
                + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?) ";
        int rowsAffected = 0;
        int updateRowsAffected = 0;
        int count = 0;
        int old_quantity = 0;
        int old_price = 0;
        int vendor_id = getOrgOfficeId(bean.getVendor());
        int org_office_id = getOrgOfficeId(org_office_name);
        int revision = PurchaseOrdersModel.getRevisionno(bean, logged_key_person_id, bean.getItem_names_id(), vendor_id);
        String query2 = " select quantity,price from purchase_order_cart_table where key_person_id='" + logged_key_person_id + "' "
                + " and model_id='" + bean.getModel_id() + "' and vendor_id='" + vendor_id + "' "
                + " and item_names_id='" + bean.getItem_names_id() + "' and cart_status_id=1 and active='Y' ";

        try {
            PreparedStatement pstmt1 = connection.prepareStatement(query2);
            ResultSet rs1 = pstmt1.executeQuery();
            while (rs1.next()) {
                old_quantity = rs1.getInt("quantity");
                old_price = rs1.getInt("price");
            }

            if (old_quantity > 0) {
                String query1 = " SELECT max(revision_no) as revision_no FROM purchase_order_cart_table WHERE key_person_id='" + logged_key_person_id + "' "
                        + " and model_id='" + bean.getModel_id() + "' and vendor_id='" + vendor_id + "' "
                        + " and item_names_id='" + bean.getItem_names_id() + "' and cart_status_id=1 and active='Y' ";

                String query_update = " UPDATE purchase_order_cart_table SET active =? WHERE key_person_id=? and model_id=? and item_names_id=? "
                        + " and cart_status_id=? and revision_no=? and vendor_id=? ";

                PreparedStatement pstmt = connection.prepareStatement(query1);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    revision = rs.getInt("revision_no");

                    PreparedStatement pstm = connection.prepareStatement(query_update);
                    pstm.setString(1, "N");
                    pstm.setInt(2, logged_key_person_id);
                    pstm.setInt(3, bean.getModel_id());
                    pstm.setInt(4, bean.getItem_names_id());
                    pstm.setInt(5, 1);
                    pstm.setInt(6, revision);
                    pstm.setInt(7, vendor_id);

                    updateRowsAffected = pstm.executeUpdate();
                    if (updateRowsAffected >= 1) {
                        revision = rs.getInt("revision_no") + 1;
                        PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query);
                        psmt.setInt(1, logged_key_person_id);
                        psmt.setInt(2, bean.getModel_id());
                        psmt.setInt(3, bean.getItem_names_id());
                        psmt.setInt(4, 1);
                        psmt.setInt(5, old_quantity + Integer.parseInt(bean.getQty()));
                        psmt.setInt(6, old_price + Integer.parseInt(bean.getPrice()));
                        psmt.setString(7, "Y");
                        psmt.setString(8, "");
                        psmt.setInt(9, 0);
                        psmt.setString(10, "");
                        psmt.setInt(11, vendor_id);
                        psmt.setInt(12, org_office_id);
                        rowsAffected = psmt.executeUpdate();
                    }
                }

            } else {
                PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, logged_key_person_id);
                pstmt.setInt(2, bean.getModel_id());
                pstmt.setInt(3, bean.getItem_names_id());
                pstmt.setInt(4, 1);
                pstmt.setString(5, bean.getQty());
                pstmt.setString(6, bean.getPrice());
                pstmt.setString(7, "Y");
                pstmt.setString(8, "");
                pstmt.setInt(9, 0);
                pstmt.setString(10, "");
                pstmt.setInt(11, vendor_id);
                pstmt.setInt(12, org_office_id);

                rowsAffected = pstmt.executeUpdate();
            }

            if (rowsAffected > 0) {
                message = "Record saved successfully.";
                messageBGColor = COLOR_OK;
            } else {
                message = "Cannot save the record, some error.";
                messageBGColor = COLOR_ERROR;

            }
        } catch (Exception e) {
            System.err.println("PurchaseOrdersModel addToCart() Exception---" + e);
        }

        return rowsAffected;
    }

    public ArrayList<PurchaseOrdersBean> viewCart(int logged_key_person_id, String user_role) {
        ArrayList<PurchaseOrdersBean> list = new ArrayList<PurchaseOrdersBean>();
        String query = "";
        String query_id = "";
        try {
            if (user_role.equals("Admin") || user_role.equals("Super Admin")) {
                query_id = "   select oo2.org_office_id "
                        + "  as org_office_id "
                        + "  from purchase_order_cart_table poct,key_person kp,org_office oo1,org_office oo2,model m,item_names "
                        + "  itn,cart_status cs,vendor_item_map vim  where poct.active='Y' and kp.active='Y' "
                        + "  and oo1.active='Y' and oo2.active='Y' and m.active='Y' and itn.active='Y'  and vim.active='Y' and  "
                        + "  poct.key_person_id=kp.key_person_id and poct.vendor_id=oo1.org_office_id and "
                        + "  poct.model_id=m.model_id  and poct.item_names_id=itn.item_names_id and vim.item_names_id=itn.item_names_id "
                        + "  and vim.org_office_id=oo1.org_office_id  and poct.org_office_id=oo2.org_office_id and "
                        + " cs.cart_status_id=poct.cart_status_id "
                        + "  and poct.cart_status_id=1 ";
                query_id += " group by oo2.org_office_name ";
            } else {
                query_id = "   select oo2.org_office_id "
                        + "  as org_office_id "
                        + "  from purchase_order_cart_table poct,key_person kp,org_office oo1,org_office oo2,model m,item_names "
                        + "  itn,cart_status cs,vendor_item_map vim  where poct.active='Y' and kp.active='Y' "
                        + "  and oo1.active='Y' and oo2.active='Y' and m.active='Y' and itn.active='Y'  and vim.active='Y' and  "
                        + "  poct.key_person_id=kp.key_person_id and poct.vendor_id=oo1.org_office_id and "
                        + "  poct.model_id=m.model_id  and poct.item_names_id=itn.item_names_id and vim.item_names_id=itn.item_names_id "
                        + "  and vim.org_office_id=oo1.org_office_id  and poct.org_office_id=oo2.org_office_id and "
                        + " cs.cart_status_id=poct.cart_status_id "
                        + "  and poct.cart_status_id=1 ";
                if (logged_key_person_id != 0) {
                    query_id += " and kp.key_person_id='" + logged_key_person_id + "' ";
                }
                query_id += "  group by oo2.org_office_name ";

            }
            ResultSet rst1 = connection.prepareStatement(query_id).executeQuery();
            while (rst1.next()) {
                int org_office_id = rst1.getInt("org_office_id");
                if (user_role.equals("Admin") || user_role.equals("Super Admin")) {
                    query = "  select count(poct.quantity) as qty,sum(poct.price) as price,kp.key_person_name,oo1.org_office_name as vendor_name, "
                            + " oo2.org_office_name "
                            + "  as org_office_name, "
                            + "  m.model,itn.item_name,cs.cart_status,oo1.org_office_code,oo1.org_office_id as vendor_id,oo2.org_office_id as org_office_id "
                            + "  from purchase_order_cart_table poct,key_person kp,org_office oo1,org_office oo2,model m,item_names "
                            + "  itn,cart_status cs,vendor_item_map vim  where poct.active='Y' and kp.active='Y' "
                            + "  and oo1.active='Y' and oo2.active='Y' and m.active='Y' and itn.active='Y'  and vim.active='Y' and "
                            + "  poct.key_person_id=kp.key_person_id and poct.vendor_id=oo1.org_office_id and "
                            + "  poct.model_id=m.model_id  and poct.item_names_id=itn.item_names_id and vim.item_names_id=itn.item_names_id "
                            + "  and vim.org_office_id=oo1.org_office_id  and poct.org_office_id=oo2.org_office_id "
                            + " and cs.cart_status_id=poct.cart_status_id "
                            + "  and poct.cart_status_id=1 and poct.org_office_id='" + org_office_id + "' ";
                    query += "  group by oo1.org_office_name ";
                } else {
                    query = "   select count(poct.quantity) as qty,sum(poct.price) as price,kp.key_person_name,oo1.org_office_name as vendor_name, "
                            + " oo2.org_office_name "
                            + "  as org_office_name, "
                            + "  m.model,itn.item_name,cs.cart_status,oo1.org_office_code,oo1.org_office_id as vendor_id,oo2.org_office_id as org_office_id "
                            + "  from purchase_order_cart_table poct,key_person kp,org_office oo1,org_office oo2,model m,item_names "
                            + "  itn,cart_status cs,vendor_item_map vim  where poct.active='Y' and kp.active='Y' "
                            + "  and oo1.active='Y' and oo2.active='Y' and m.active='Y' and itn.active='Y'  and vim.active='Y' and "
                            + "  poct.key_person_id=kp.key_person_id and poct.vendor_id=oo1.org_office_id and "
                            + "  poct.model_id=m.model_id  and poct.item_names_id=itn.item_names_id and vim.item_names_id=itn.item_names_id "
                            + "  and vim.org_office_id=oo1.org_office_id  and poct.org_office_id=oo2.org_office_id "
                            + " and cs.cart_status_id=poct.cart_status_id "
                            + "  and poct.cart_status_id=1 and poct.org_office_id='" + org_office_id + "' ";
                    if (logged_key_person_id != 0) {
                        query += " and kp.key_person_id='" + logged_key_person_id + "' ";
                    }
                    query += "  group by oo1.org_office_name ";

                }

                ResultSet rst = connection.prepareStatement(query).executeQuery();
                while (rst.next()) {
                    PurchaseOrdersBean bean = new PurchaseOrdersBean();
                    bean.setVendor(rst.getString("vendor_name"));
                    bean.setOrg_office_name(rst.getString("org_office_name"));
                    bean.setOrg_office_code(rst.getString("org_office_code"));
                    bean.setQty(rst.getString("qty"));
                    bean.setPrice(rst.getString("price"));
                    bean.setVendor_id(rst.getInt("vendor_id"));
                    bean.setOrg_office_id(rst.getString("org_office_id"));
                    list.add(bean);
                }
            }

        } catch (Exception e) {
            System.err.println("PurchaseOrdersModel viewCart() Exception------------" + e);
        }

        return list;
    }

    public ArrayList<PurchaseOrdersBean> viewCartItems(int logged_key_person_id, String user_role, String vendor_id, String org_office_id) {
        ArrayList<PurchaseOrdersBean> list = new ArrayList<PurchaseOrdersBean>();
        String query = "";
        try {
            if (user_role.equals("Admin") || user_role.equals("Super Admin")) {
                query = " select poct.quantity,poct.price,kp.key_person_name,oo.org_office_name, "
                        + " m.model,m.model_id,itn.item_name,itn.item_names_id,cs.cart_status,oo.org_office_code,oo.org_office_id "
                        + " from purchase_order_cart_table poct,key_person kp,org_office oo,model m,item_names itn,cart_status cs,vendor_item_map vim "
                        + " where poct.active='Y' and kp.active='Y' and oo.active='Y' and m.active='Y' and itn.active='Y'  and vim.active='Y' and "
                        + " poct.key_person_id=kp.key_person_id and poct.vendor_id=oo.org_office_id and poct.model_id=m.model_id "
                        + " and poct.item_names_id=itn.item_names_id and vim.item_names_id=itn.item_names_id and vim.org_office_id=oo.org_office_id "
                        + " and cs.cart_status_id=poct.cart_status_id and poct.cart_status_id=1 ";
                if (!vendor_id.equals("")) {
                    query += " and poct.vendor_id='" + vendor_id + "' ";
                }
                if (!org_office_id.equals("")) {
                    query += " and poct.org_office_id='" + org_office_id + "' ";
                }
            } else {
                query = " select poct.quantity,poct.price,kp.key_person_name,oo.org_office_name, "
                        + " m.model,m.model_id,itn.item_name,itn.item_names_id,cs.cart_status,oo.org_office_code,oo.org_office_id "
                        + " from purchase_order_cart_table poct,key_person kp,org_office oo,model m,item_names itn,cart_status cs,vendor_item_map vim "
                        + " where poct.active='Y' and kp.active='Y' and oo.active='Y' and m.active='Y' and itn.active='Y'  and vim.active='Y' and "
                        + " poct.key_person_id=kp.key_person_id and poct.vendor_id=oo.org_office_id and poct.model_id=m.model_id "
                        + " and poct.item_names_id=itn.item_names_id and vim.item_names_id=itn.item_names_id and vim.org_office_id=oo.org_office_id "
                        + " and cs.cart_status_id=poct.cart_status_id and poct.cart_status_id=1 ";
                if (!vendor_id.equals("")) {
                    query += " and poct.vendor_id='" + vendor_id + "' ";
                }
                if (!org_office_id.equals("")) {
                    query += " and poct.org_office_id='" + org_office_id + "' ";
                }
                if (logged_key_person_id != 0) {
                    query += " and kp.key_person_id='" + logged_key_person_id + "' ";
                }
            }

            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                PurchaseOrdersBean bean = new PurchaseOrdersBean();
                bean.setVendor(rst.getString("org_office_name"));
                bean.setOrg_office_code(rst.getString("org_office_code"));
                bean.setQty(rst.getString("quantity"));
                bean.setPrice(rst.getString("price"));
                bean.setVendor_id(rst.getInt("org_office_id"));
                bean.setModel(rst.getString("model"));
                bean.setItem_name(rst.getString("item_name"));
                bean.setItem_names_id(rst.getInt("item_names_id"));
                bean.setModel_id(rst.getInt("model_id"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.err.println("PurchaseOrdersModel viewCartItems() Exception------------" + e);
        }

        return list;
    }

    public int cancelOrder(String vendor_id, String org_office_id) {
        int rowsAffected = 0;
        try {

            String query = " update purchase_order_cart_table set active='N' where vendor_id='" + vendor_id + "' and org_office_id='" + org_office_id + "' and active='Y' ";
            PreparedStatement psmt = connection.prepareStatement(query);
            rowsAffected = psmt.executeUpdate();

        } catch (Exception e) {
            System.err.println("PurchaseOrdersModel cancelOrder Error---------" + e);
        }
        return rowsAffected;
    }

    public int removeItem(String vendor_id, String item_names_id, String model_id, String org_office_id) {
        int rowsAffected = 0;
        try {
            String query = " update purchase_order_cart_table set active='N' where vendor_id='" + vendor_id + "' "
                    + " and model_id='" + model_id + "' and item_names_id='" + item_names_id + "' and org_office_id='" + org_office_id + "' and active='Y' ";
            PreparedStatement psmt = connection.prepareStatement(query);
            rowsAffected = psmt.executeUpdate();

        } catch (Exception e) {
            System.err.println("PurchaseOrdersModel removeItem Error---------" + e);
        }
        return rowsAffected;
    }

    public int getStatusId(String status) {

        String query = "SELECT status_id FROM status WHERE status = '" + status + "' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("status_id");
        } catch (Exception e) {
            System.out.println("PurchaseOrdersModel getStatusId Error: " + e);
        }
        return id;
    }

    public String approvePurchaseOrder(PurchaseOrdersBean bean, int purchase_order_id, int i, int size) throws SQLException {
        int updateRowsAffected = 0;
        String status = bean.getStatus();
        if (status.equals("Confirm")) {
            status = "Approved";
        }
        if (status.equals("Denied All")) {
            status = "Denied";
        }

        int status_id = getStatusId(status);

        String query2 = " UPDATE purchase_order SET status_id=? WHERE purchase_order_id=? ";

        int rowsAffected = 0;
        int updateRowsAffected2 = 0;
        int updateRowsAffected3 = 0;
        int map_count = 0;
        try {
            PreparedStatement pstm = connection.prepareStatement(query2);
            pstm.setInt(1, status_id);
            pstm.setInt(2, purchase_order_id);
            updateRowsAffected = pstm.executeUpdate();

        } catch (Exception e) {
            System.out.println("PurchaseOrdersModel approvePurchaseOrder() Error: " + e);
        }
        if (updateRowsAffected > 0) {
            message = "Your Order has been approved ";
            messageBGColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            messageBGColor = COLOR_ERROR;
        }
        return message + "&" + status;
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

    public static void addMetaData(Document document) {
        document.addTitle("Order PDF");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("Lars Vogel");
        document.addCreator("Lars Vogel");
    }

    public static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    public void addContent(Document document, String logo_path, String order_no) throws DocumentException, MalformedURLException, BadElementException, IOException {

        List<PurchaseOrdersBean> list = getOrderDetail(order_no);

        Paragraph preface = new Paragraph();
        // Creating an ImageData object       
        Image img = Image.getInstance(logo_path);
        img.setAlignment(Element.ALIGN_LEFT);
        // Start Header
        float[] colWidths1 = {1, 20, 8};
        PdfPTable tables = new PdfPTable(colWidths1);
        tables.setWidthPercentage(100);
        Font bold12 = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        bold12.setColor(BaseColor.WHITE);

        Font bold22 = new Font(Font.FontFamily.HELVETICA, 22, Font.BOLD);
        bold22.setColor(BaseColor.DARK_GRAY);
        Font normal = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);
        Font normal8 = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
        Font bold10 = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);

        Paragraph preface3 = new Paragraph();
        Chunk chunk3 = new Chunk("Purchase Order", bold22);
        Paragraph p = new Paragraph();
        p.add(chunk3);

        preface3.add(new Paragraph(p));

        PdfPCell cellOne = new PdfPCell(img);
        PdfPCell cellTwo = new PdfPCell(preface3);

        cellOne.setColspan(2);
        cellOne.setBorder(Rectangle.NO_BORDER);
        cellTwo.setBorder(Rectangle.NO_BORDER);

        tables.addCell(cellOne);
        tables.addCell(cellTwo);

        document.add(tables);

        //End header
        //Add order date and no. 
        float[] colWidths = {1, 20, 8};
        float[] colWidths2 = {1, 1};
        PdfPTable table = new PdfPTable(colWidths);
        table.setWidthPercentage(100);

        PdfPTable table1 = new PdfPTable(colWidths2);
        table1.setWidthPercentage(25);
        table1.addCell(new Phrase("Date", bold10));
        table1.addCell(new Phrase(" " + list.get(0).getDate_time(), normal));
        table1.addCell(new Phrase("P.O. Number", bold10));
        table1.addCell(new Phrase(" " + order_no, normal));
//        table1.addCell(new Phrase("Customer ID", bold10));
//        table1.addCell(new Phrase("122", normal));

        PdfPCell cellOne1 = new PdfPCell();
        PdfPCell cellTwo1 = new PdfPCell(table1);

        cellOne1.setColspan(2);
        cellOne1.setBorder(Rectangle.NO_BORDER);
        cellTwo1.setBorder(Rectangle.NO_BORDER);

        table.addCell(cellOne1);
        table.addCell(cellTwo1);

        document.add(table);
        //End Add order date and no. 

        addEmptyLine(preface, 1);
        document.add(preface);

        //Add vendor and customer detail 
        float[] colWid = {1, 20, 8};
        float[] colWid1 = {5, 10};
        PdfPTable table2 = new PdfPTable(colWid);
        table2.setWidthPercentage(100);
        table2.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        PdfPTable table4 = new PdfPTable(colWid1);
        table4.setWidthPercentage(10);
        table4.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        PdfPCell c1 = new PdfPCell();
        c1 = new PdfPCell(new Phrase(" Vendor:", bold12));
        c1.setBackgroundColor(BaseColor.DARK_GRAY);
        c1.setBorder(0);

        PdfPCell c1_blank = new PdfPCell();
        c1_blank = new PdfPCell(new Phrase("", bold12));
        c1_blank.setBackgroundColor(BaseColor.DARK_GRAY);
        c1_blank.setBorder(0);

        table4.addCell(c1);
        table4.addCell(c1_blank);
        table4.addCell(new Phrase("Name", bold10));
        table4.addCell(new Phrase(" " + list.get(0).getVendor(), normal));
        table4.addCell(new Phrase("Company Name", bold10));
        table4.addCell(new Phrase(" " + list.get(0).getVendor(), normal));
        table4.addCell(new Phrase("Address", bold10));
        table4.addCell(new Phrase(" " + list.get(0).getVendor_address(), normal));
        table4.addCell(new Phrase("Phone", bold10));
        table4.addCell(new Phrase(" +91-" + list.get(0).getVendor_mobile(), normal));
        table4.addCell(new Phrase("Email", bold10));
        table4.addCell(new Phrase(" " + list.get(0).getVendor_email(), normal));

        PdfPTable table5 = new PdfPTable(colWid1);
        table5.setWidthPercentage(45);
        table5.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        PdfPCell c2 = new PdfPCell();
        c2 = new PdfPCell(new Phrase(" Customer:", bold12));
        c2.setBackgroundColor(BaseColor.DARK_GRAY);
        c2.setBorder(0);

        table5.addCell(c2);
        table5.addCell(c1_blank);
        table5.addCell(new Phrase("Name", bold10));
        table5.addCell(new Phrase(" " + list.get(0).getCustomer_name(), normal));
        table5.addCell(new Phrase("Company Name", bold10));
        table5.addCell(new Phrase(" " + list.get(0).getCustomer_office_name(), normal));
        table5.addCell(new Phrase("Address", bold10));
        table5.addCell(new Phrase(" " + list.get(0).getCustomer_address(), normal));
        table5.addCell(new Phrase("Phone", bold10));
        table5.addCell(new Phrase(" +91-" + list.get(0).getCustomer_mobile(), normal));
        table5.addCell(new Phrase("Email", bold10));
        table5.addCell(new Phrase(" " + list.get(0).getCustomer_email(), normal));

        PdfPCell cellOne2 = new PdfPCell(table4);
        PdfPCell cellTwo2 = new PdfPCell(table5);

        cellOne2.setColspan(2);
        cellOne2.setBorder(Rectangle.NO_BORDER);
        cellTwo2.setBorder(Rectangle.NO_BORDER);

        table2.addCell(cellOne2);
        table2.addCell(cellTwo2);

        document.add(table2);
        //End Add vendor and customer detail 

        addEmptyLine(preface, 2);
        document.add(preface);

        //Add product table
        float[] colproduct = {1, 5, 5, 2, 2, 3, 3};
        PdfPTable table_product = new PdfPTable(colproduct);
        table_product.setWidthPercentage(100);

        PdfPCell c_s_no = new PdfPCell();
        c_s_no = new PdfPCell(new Phrase(" S.No.", bold12));
        c_s_no.setBackgroundColor(BaseColor.DARK_GRAY);

        PdfPCell c_product = new PdfPCell();
        c_product = new PdfPCell(new Phrase(" Product", bold12));
        c_product.setBackgroundColor(BaseColor.DARK_GRAY);

        PdfPCell c_model = new PdfPCell();
        c_model = new PdfPCell(new Phrase(" Model", bold12));
        c_model.setBackgroundColor(BaseColor.DARK_GRAY);

        PdfPCell c_lead_time = new PdfPCell();
        c_lead_time = new PdfPCell(new Phrase(" Lead Time", bold12));
        c_lead_time.setBackgroundColor(BaseColor.DARK_GRAY);

        PdfPCell c_qty = new PdfPCell();
        c_qty = new PdfPCell(new Phrase(" Qty", bold12));
        c_qty.setBackgroundColor(BaseColor.DARK_GRAY);

        PdfPCell c_price = new PdfPCell();
        c_price = new PdfPCell(new Phrase(" Unit Price", bold12));
        c_price.setBackgroundColor(BaseColor.DARK_GRAY);

        PdfPCell c_total = new PdfPCell();
        c_total = new PdfPCell(new Phrase(" Total", bold12));
        c_total.setBackgroundColor(BaseColor.DARK_GRAY);

        table_product.addCell(c_s_no);
        table_product.addCell(c_product);
        table_product.addCell(c_model);
        table_product.addCell(c_lead_time);
        table_product.addCell(c_qty);
        table_product.addCell(c_price);
        table_product.addCell(c_total);

        table_product.addCell("");
        table_product.addCell("");
        table_product.addCell("");
        table_product.addCell("");
        table_product.addCell("");
        table_product.addCell("");
        table_product.addCell("");

        int total_price = 0;
        for (int i = 0; i < list.size(); i++) {
            table_product.addCell(new Phrase(String.valueOf(i + 1), bold10));
            table_product.addCell(new Phrase(" " + list.get(i).getItem_name(), normal));
            table_product.addCell(new Phrase(" " + list.get(i).getModel(), normal));
            table_product.addCell(new Phrase(" " + list.get(i).getLead_time(), normal));
            table_product.addCell(new Phrase(" " + list.get(i).getQty(), normal));
            table_product.addCell(new Phrase(String.valueOf(" " + Integer.parseInt(list.get(i).getPrice()) / Integer.parseInt(list.get(i).getQty())), normal));
            table_product.addCell(new Phrase(" " + list.get(i).getPrice(), normal));
            total_price = total_price + Integer.parseInt(list.get(i).getPrice());
        }

        PdfPCell c_subtotal = new PdfPCell();
        c_subtotal = new PdfPCell(new Phrase(" Subtotal", bold10));

        PdfPCell c_subtotal_val = new PdfPCell();
        c_subtotal_val = new PdfPCell(new Phrase(String.valueOf(" " + total_price), normal));

        table_product.addCell(" ");
        table_product.addCell(" ");
        table_product.addCell(" ");
        table_product.addCell(" ");
        table_product.addCell(" ");
        table_product.addCell(c_subtotal);
        table_product.addCell(c_subtotal_val);

        document.add(table_product);
        //End add product table
        addEmptyLine(preface, 1);
        document.add(preface);

        //Add notes AND SUBTOTAL
        float[] colnotes = {10};
        PdfPTable table_notes = new PdfPTable(colnotes);
        table_notes.setWidthPercentage(60);
        table_notes.setHorizontalAlignment(Element.ALIGN_LEFT);

        PdfPCell c_notes = new PdfPCell();
        c_notes = new PdfPCell(new Phrase(" Notes And Instructions", bold12));
        c_notes.setBackgroundColor(BaseColor.DARK_GRAY);

        PdfPCell c_blank = new PdfPCell();
        c_blank = new PdfPCell(new Phrase(" "));
        table_notes.addCell(c_notes);

        table_notes.addCell(c_blank);
        table_notes.addCell(c_blank);
        table_notes.addCell(c_blank);

        document.add(table_notes);
        //End Add notes AND SUBTOTAL

        addEmptyLine(preface, 2);
        document.add(preface);

        //DATE AND SIGNATURE
        float[] col_date_sign = {1, 20, 8};
        PdfPTable table_date_sign = new PdfPTable(col_date_sign);
        table_date_sign.setWidthPercentage(100);

        preface3.add(new Paragraph(p));

        PdfPCell cellOne_date_sign = new PdfPCell(new Phrase("Date"));
        PdfPCell cellTwo_date_sign = new PdfPCell(new Phrase("Authorized Signature"));

        cellOne_date_sign.setColspan(2);
        cellOne_date_sign.setBorder(Rectangle.NO_BORDER);
        cellTwo_date_sign.setBorder(Rectangle.NO_BORDER);

        table_date_sign.addCell(cellOne_date_sign);
        table_date_sign.addCell(cellTwo_date_sign);

        document.add(table_date_sign);
        //EndDATE AND SIGNATURE

        addEmptyLine(preface, 2);
        document.add(preface);
        Paragraph pre2 = new Paragraph();
        pre2.add(new LineSeparator());
        addEmptyLine(pre2, 1);
        document.add(pre2);

        //Add footer
        float[] col_footer = {10};
        PdfPTable table_footer = new PdfPTable(col_footer);
        table_footer.setWidthPercentage(40);
        table_footer.setHorizontalAlignment(Element.ALIGN_CENTER);

        Paragraph preface_footer = new Paragraph();
        Chunk chunk_footer = new Chunk("2021 © Apogee GNSS. All Rights Reserved\n", normal);

        Paragraph p_footer = new Paragraph();
        p_footer.add(chunk_footer);

        preface_footer.add(new Paragraph(p_footer));

        PdfPCell cell_footer = new PdfPCell(preface_footer);

        cell_footer.setBorder(0);
        table_footer.addCell(cell_footer);

        document.add(table_footer);

        float[] col_footer2 = {10};
        PdfPTable table_footer2 = new PdfPTable(col_footer2);
        table_footer2.setWidthPercentage(60);
        table_footer2.setHorizontalAlignment(Element.ALIGN_CENTER);

        Paragraph preface_footer2 = new Paragraph();

//         Rectangle rect = new Rectangle(0, 0);       
//      PdfLinkAnnotation annotation = new PdfLinkAnnotation(rect);              
//      
//      // Setting action of the annotation       
//      PdfAction action = PdfAction.createURI("http:// www.tutorialspoint.com/");       
//      annotation.setAction(action);             
//      
//      // Creating a link       
//      Link link = new Link("Click here", annotation);              
//      
//      // Creating a paragraph       
//      Paragraph paragraph = new Paragraph("Hi welcome to Tutorialspoint ");              
//      
//      // Adding link to paragraph       
//      paragraph.add(link.setUnderline());              
//      
//      // Adding paragraph to document       
//      document.add(paragraph);  
//        Link link = new Link("European Business Awards",
//                PdfAction.createURI("http://www.apogeeprecision.com"));
        Chunk chunk_footer2 = new Chunk("tel:+91 7624009260  Email: mailto:marketing@apogeeprecision.com Web: http://www.apogeeprecision.com", normal);

        Paragraph p_footer2 = new Paragraph();
        p_footer2.add(chunk_footer2);

        preface_footer2.add(new Paragraph(p_footer2));
        PdfPCell cell_footer2 = new PdfPCell(preface_footer2);
        cell_footer2.setBorder(0);
        table_footer2.addCell(cell_footer2);

        document.add(table_footer2);

        //End Add footer
    }

    public String sentMail(String destination, String order_no) {
        // SMTP server information

        String host = "smtp.gmail.com";
        String port = "587";
        String mailFrom = "smartmeter.apogee@gmail.com";
        String password = "jpss1277";

        // outgoing message information
        String mailTo = "komal.apogee@gmail.com";
//        String mailTo = mail_id;
        String subject = "Purchase Order";
        String message = "Hello Sir/Mam, Please find attached pdf file of purchase order.";
        String result = "";
        int updateRowsAffected = 0;
        int rowsAffected = 0;
        try {
            result = sendPlainTextEmail(host, port, mailFrom, password, mailTo,
                    subject, message, destination);

            int revision = PurchaseOrdersModel.getRevisionnoForPurchaseOrder(order_no);

            String query1 = " SELECT max(revision_no) as revision_no,item_names_id,model_id,vendor_id,org_office_id,key_person_id,qty, "
                    + " rate,vendor_lead_time,inventory_id,purchase_order_id,date_time "
                    + " FROM purchase_order WHERE order_no='" + order_no + "' and active='Y' ";

            String query_update = " UPDATE purchase_order SET active =? "
                    + " WHERE order_no=? and revision_no=? ";

            PreparedStatement pstmt = connection.prepareStatement(query1);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                revision = rs.getInt("revision_no");
                int item_names_id = rs.getInt("item_names_id");
                int model_id = rs.getInt("model_id");
                int org_office_id = rs.getInt("org_office_id");
                int vendor_id = rs.getInt("vendor_id");
                int key_person_id = rs.getInt("key_person_id");
                String qty = rs.getString("qty");
                String rate = rs.getString("rate");
                String vendor_lead_time = rs.getString("vendor_lead_time");
                String inventory_id = rs.getString("inventory_id");
                String purchase_order_id = rs.getString("purchase_order_id");
                String date_time = rs.getString("date_time");

                PreparedStatement pstm = connection.prepareStatement(query_update);
                pstm.setString(1, "N");
                pstm.setString(2, order_no);
                pstm.setInt(3, revision);

                updateRowsAffected = pstm.executeUpdate();

                if (updateRowsAffected >= 1) {
                    String query_insert = " INSERT INTO purchase_order(purchase_order_id,order_no,item_names_id,"
                            + " model_id,vendor_id,org_office_id,key_person_id,status_id,qty,rate,vendor_lead_time,payment,follow_up_frequency,"
                            + " active,order_document_path,inventory_id,revision_no,date_time,created_by,description,remark) "
                            + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
                    revision = rs.getInt("revision_no") + 1;
                    PreparedStatement psmt1 = (PreparedStatement) connection.prepareStatement(query_insert);
                    psmt1.setString(1, purchase_order_id);
                    psmt1.setString(2, order_no);
                    psmt1.setInt(3, item_names_id);
                    psmt1.setInt(4, model_id);
                    psmt1.setInt(5, vendor_id);
                    psmt1.setInt(6, org_office_id);
                    psmt1.setInt(7, key_person_id);
                    psmt1.setInt(8, 17);
                    psmt1.setString(9, qty);
                    psmt1.setString(10, rate);
                    psmt1.setString(11, vendor_lead_time);
                    psmt1.setInt(12, 0);
                    psmt1.setInt(13, 0);
                    psmt1.setString(14, "Y");
                    psmt1.setString(15, "");
                    psmt1.setString(16, inventory_id);
                    psmt1.setInt(17, revision);
                    psmt1.setString(18, date_time);
                    psmt1.setString(19, "");
                    psmt1.setString(20, "");
                    psmt1.setString(21, "");
                    rowsAffected = psmt1.executeUpdate();
                }
                
            }
        } catch (Exception ex) {

            System.out.println("Failed to sent email.");
            ex.printStackTrace();
        }
        return result;
    }

    public String sendPlainTextEmail(String host, String port,
            final String userName, final String password, String toAddress,
            String subject, String messag, String destination) throws AddressException,
            MessagingException {

        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };

        Session session = Session.getInstance(properties, auth);

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(userName));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
            msg.setSubject(subject);

            //3) create MimeBodyPart object and set your message text     
            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText(messag);

            //4) create new MimeBodyPart object and set DataHandler object to this object      
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();
            MimeBodyPart messageBodyPart3 = new MimeBodyPart();

            String filename = "C:\\ssadvt_repository\\APL\\Report\\sm.pdf";//change accordingly  

            filename = destination;
            Multipart multipart = new MimeMultipart();
            DataSource source = new FileDataSource(filename);
            messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName(filename.substring(10));

            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart2);

            msg.setContent(multipart);

            Transport.send(msg);
            message = "Email Sent successfully.";
            messageBGColor = COLOR_OK;
            System.out.println("message sent....");

        } catch (MessagingException ex) {
            ex.printStackTrace();
            message = "Something Went Wrong.";
            messageBGColor = COLOR_ERROR;
        }
        return message;
    }
}
