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
public class QuotationModel {

    static private Connection connection;
    private String driver, url, user, password;
    static private String message, messageBGColor = "#a2a220";
    static private final String COLOR_OK = "green";
    static private final String COLOR_ERROR = "red";

    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("QuotationModel setConnection() Error: " + e);
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("QuotationModel closeConnection: " + e);
        }
    }

    public void setConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("QuotationModel setConnection error: " + e);
        }
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
            System.out.println("QuotationModel getItemId Error: " + e);
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
            System.out.println("QuotationModel getModelId Error: " + e);
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
            System.out.println("QuotationModel getVendorId Error: " + e);
        }
        return id;
    }

    public static int getInventoryId(int item_names_id, int model_id, int logged_org_office_id) {
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
            System.out.println("QuotationModel getInventoryId Error: " + e);
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
            System.out.println("QuotationModel getOrgOfficeId Error: " + e);
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
            System.out.println("Error:QuotationModel--getVendorName()-- " + e);
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
            System.out.println("Error:QuotationModel--getOrgOffice()-- " + e);
        }

        return list;
    }

    public static int getCounting() {
        int counting = 100000;
        int count = 0;
        String query = " SELECT quotation_no FROM quotation order by quotation_id desc limit 1 ";
        try {
            PreparedStatement psmt = connection.prepareStatement(query);
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                String order_no = rs.getString("quotation_no");
//                String order_no_arr[] = order_no.split("_");
//                int length = (order_no_arr.length) - 1;
                count = Integer.parseInt(order_no.substring(3));
                counting = count;
            }
        } catch (Exception ex) {
            System.out.println("ERROR: in getCounting() in QuotationModel : " + ex);
        }
        return counting + 1;
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
            System.err.println("QuotationModel getNewOrderData Exception--" + e);
        }
        return list;
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
            System.err.println("QuotationModel getData() Exception--" + e);
        }
        return arrayObj;
    }

    public static int getRevisionno(int model_id, int item_names_id, int vendor_id, int logged_key_person_id) {
        int revision = 0;
        try {
            String query = " SELECT max(revision_no) as revision_no FROM quotation where "
                    + "  key_person_id='" + logged_key_person_id + "' "
                    + " and model_id='" + model_id + "' "
                    + " and item_names_id='" + item_names_id + "' and vendor_id='" + vendor_id + "' and active='Y' ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
            System.err.println("QuotationModel getRevisionno() error--------" + e);
        }
        return revision;
    }

    public static int insertRecord(PurchaseOrdersBean bean, int logged_org_office_id, String role, int logged_key_person_id) throws SQLException {
        String query = " INSERT INTO quotation(quotation_no,item_names_id,model_id,"
                + " vendor_id,req_qty,rate,lead_time,scheduled_date,active,inventory_id, "
                + " revision_no,description, "
                + " remark,date_time,org_office_id,key_person_id,status_id) "
                + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
        int rowsAffected = 0;
        int rowsAffected2 = 0;
        int updateRowsAffected = 0;
        int inventory_id = getInventoryId(bean.getItem_names_id(), bean.getModel_id(), logged_org_office_id);
        int count = 0;
        int old_quantity = 0;
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
        String date_time = sdf.format(date);
        try {
            PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query);
            psmt.setString(1, bean.getQuotation_no());
            psmt.setInt(2, bean.getItem_names_id());
            psmt.setInt(3, bean.getModel_id());
            psmt.setInt(4, bean.getVendor_id());
            psmt.setString(5, bean.getQty());
            psmt.setString(6, "0");
            psmt.setString(7, "0");
            psmt.setString(8, bean.getScheduled_date());
            psmt.setString(9, "Y");
            psmt.setInt(10, inventory_id);
            psmt.setInt(11, bean.getRevision_no());
            psmt.setString(12, bean.getDescription());
            psmt.setString(13, "");
            psmt.setString(14, bean.getDate_time());
            psmt.setInt(15, logged_org_office_id);
            psmt.setInt(16, logged_key_person_id);
            psmt.setInt(17, 2);
            rowsAffected = psmt.executeUpdate();

            if (rowsAffected > 0) {
                int revision = DemandNoteModel.getRevisionno(bean, logged_key_person_id, bean.getItem_names_id());

                String query1 = " SELECT max(revision_no) as revision_no,req_qty,approved_qty,key_person_id,order_no,demand_note_id "
                        + " FROM demand_note WHERE org_office_id='" + logged_org_office_id + "' "
                        + " and model_id='" + bean.getModel_id() + "' "
                        + " and item_names_id='" + bean.getItem_names_id() + "' "
                        + " and status_id=6 and active='Y' ";

                String query_update = " UPDATE demand_note SET active =? "
                        + " WHERE key_person_id=? and model_id=? and item_names_id=? "
                        + " and status_id=? and revision_no=?  ";

                PreparedStatement pstmt = connection.prepareStatement(query1);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    revision = rs.getInt("revision_no");
                    int req_qty = rs.getInt("req_qty");
                    int approve_qty = rs.getInt("approved_qty");
                    String demand_no = rs.getString("order_no");
                    String key_person_id = rs.getString("key_person_id");
                    String demand_note_id = rs.getString("demand_note_id");

                    PreparedStatement pstm = connection.prepareStatement(query_update);
                    pstm.setString(1, "N");
                    pstm.setString(2, key_person_id);
                    pstm.setInt(3, bean.getModel_id());
                    pstm.setInt(4, bean.getItem_names_id());
                    pstm.setInt(5, 6);
                    pstm.setInt(6, revision);
                    updateRowsAffected = pstm.executeUpdate();

                    if (updateRowsAffected >= 1) {
                        String query_insert = " INSERT INTO demand_note(order_no,item_names_id,model_id,"
                                + " org_office_id,key_person_id,status_id,req_qty,approved_qty,inventory_id,active, "
                                + " revision_no,description, "
                                + " remark,date_time,demand_note_id) "
                                + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
                        revision = rs.getInt("revision_no") + 1;
                        PreparedStatement psmt1 = (PreparedStatement) connection.prepareStatement(query_insert);
                        psmt1.setString(1, demand_no);
                        psmt1.setInt(2, bean.getItem_names_id());
                        psmt1.setInt(3, bean.getModel_id());
                        psmt1.setInt(4, logged_org_office_id);
                        psmt1.setString(5, key_person_id);
                        psmt1.setInt(6, 14);
                        psmt1.setInt(7, req_qty);
                        psmt1.setInt(8, approve_qty);
                        psmt1.setInt(9, inventory_id);
                        psmt1.setString(10, "Y");
                        psmt1.setInt(11, revision);
                        psmt1.setString(12, "");
                        psmt1.setString(13, "");
                        psmt1.setString(14, date_time);
                        psmt1.setString(15, demand_note_id);
                        rowsAffected2 = psmt1.executeUpdate();
                    }
                }
                
                message = "Record saved successfully.";
                messageBGColor = COLOR_OK;

//                Connection conn = null;
//                try {
//                    Class.forName("com.mysql.cj.jdbc.Driver");
//                    conn = DriverManager.getConnection("jdbc:mysql://208.91.199.121:3306/apogeele_leveller",
//                            "apogeele_leveller", "apogeele_leveller");
//                    String status_query = " INSERT INTO status(status,created_by,remark) "
//                            + " VALUES(?,?,?) ";
//                    PreparedStatement pstm = conn.prepareStatement(status_query);
//                    int rows = psmt.executeUpdate();
//
//                } catch (Exception e) {
//                    System.out.println("DBConncetion getConnection() Error: " + e);
//                }
            } else {
                message = "Cannot save the record, some error.";
                messageBGColor = COLOR_ERROR;
            }
        } catch (Exception e) {
            System.err.println("QuotationModel insertRecord() Exception---" + e);
        }
        return rowsAffected2;
    }

    public int updateQuotation(PurchaseOrdersBean bean, int logged_key_person_id, String role) {
        int rowsAffected = 0;
        int updateRowsAffected = 0;
        List<PurchaseOrdersBean> list = getQuotationDetail(bean.getQuotation_no(), logged_key_person_id, role);
        try {
            int revision = QuotationModel.getRevisionno(bean.getModel_id(), bean.getItem_names_id(),
                    list.get(0).getVendor_id(), list.get(0).getKey_person_id());

            String query1 = " SELECT max(revision_no) as revision_no,inventory_id,req_qty,scheduled_date,date_time,quotation_id "
                    + " FROM quotation WHERE org_office_id='" + list.get(0).getOrg_office_id() + "' "
                    + " and model_id='" + bean.getModel_id() + "' "
                    + " and item_names_id='" + bean.getItem_names_id() + "' and vendor_id='" + list.get(0).getVendor_id() + "' "
                    + " and quotation_no='" + bean.getQuotation_no() + "' and active='Y' ";

            String query_update = " UPDATE quotation SET active =? "
                    + " WHERE org_office_id=? and model_id=? and item_names_id=? "
                    + " and vendor_id=? and revision_no=? and quotation_no=? ";

            PreparedStatement pstmt = connection.prepareStatement(query1);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                revision = rs.getInt("revision_no");
                int inventory_id = rs.getInt("inventory_id");
                int req_qty = rs.getInt("req_qty");
                String scheduled_date = rs.getString("scheduled_date");
                String date_time = rs.getString("date_time");
                String quotation_id = rs.getString("quotation_id");

                PreparedStatement pstm = connection.prepareStatement(query_update);
                pstm.setString(1, "N");
                pstm.setString(2, list.get(0).getOrg_office_id());
                pstm.setInt(3, bean.getModel_id());
                pstm.setInt(4, bean.getItem_names_id());
                pstm.setInt(5, list.get(0).getVendor_id());
                pstm.setInt(6, revision);
                pstm.setString(7, bean.getQuotation_no());
                updateRowsAffected = pstm.executeUpdate();

                if (updateRowsAffected >= 1) {
                    String query_insert = " INSERT INTO quotation(quotation_no,item_names_id,model_id,"
                            + " org_office_id,key_person_id,vendor_id,status_id,req_qty,rate,lead_time,inventory_id,active,scheduled_date, "
                            + " revision_no,description, "
                            + " remark,date_time,quotation_id) "
                            + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
                    revision = rs.getInt("revision_no") + 1;
                    PreparedStatement psmt1 = (PreparedStatement) connection.prepareStatement(query_insert);
                    psmt1.setString(1, bean.getQuotation_no());
                    psmt1.setInt(2, bean.getItem_names_id());
                    psmt1.setInt(3, bean.getModel_id());
                    psmt1.setString(4, list.get(0).getOrg_office_id());
                    psmt1.setInt(5, list.get(0).getKey_person_id());
                    psmt1.setInt(6, list.get(0).getVendor_id());
                    psmt1.setInt(7, 15);
                    psmt1.setInt(8, req_qty);
                    psmt1.setString(9, bean.getRate());
                    psmt1.setInt(10, bean.getLead_time());
                    psmt1.setInt(11, inventory_id);
                    psmt1.setString(12, "Y");
                    psmt1.setString(13, scheduled_date);
                    psmt1.setInt(14, revision);
                    psmt1.setString(15, "");
                    psmt1.setString(16, "");
                    psmt1.setString(17, date_time);
                    psmt1.setString(18, quotation_id);
                    rowsAffected = psmt1.executeUpdate();
                }

            }
        } catch (Exception e) {
            System.out.println("com.purchase.module.QuotationModel.updateQuotation()" + e);
        }

        return rowsAffected;
    }

    public List<PurchaseOrdersBean> getAllQuotations(int key_person_id, String role) {
        List<PurchaseOrdersBean> list = new ArrayList<PurchaseOrdersBean>();
        String query = "";
        List<String> status_list = new ArrayList<>();
        if (role.equals("Admin") || role.equals("Super Admin")) {
            query = " select distinct po.quotation_no,oo2.org_office_name as org_office_name,oo1.org_office_name as vendor_name, "
                    + " po.date_time,oo1.mobile_no1,s.status,kp.key_person_name "
                    + " from quotation po,item_names itn,model m, "
                    + " org_office oo1,org_office oo2,inventory inv,status s,key_person kp "
                    + " where po.active='Y' and itn.active='Y' and m.active='Y' and oo2.active='Y' and inv.active='Y' and "
                    + " po.item_names_id=itn.item_names_id and po.model_id=m.model_id and oo1.org_office_id=po.vendor_id  "
                    + " and po.org_office_id=oo2.org_office_id and kp.active='Y' and kp.key_person_id=po.key_person_id "
                    + " and po.inventory_id=inv.inventory_id and po.status_id=s.status_id "
                    + " group by po.quotation_no order by quotation_id desc ";
        } else {
            query = " select distinct po.quotation_no,oo2.org_office_name as org_office_name,oo1.org_office_name as vendor_name, "
                    + " po.date_time,oo1.mobile_no1,s.status,kp.key_person_name "
                    + " from quotation po,item_names itn,model m, "
                    + " org_office oo1,org_office oo2,inventory inv,status s,key_person kp "
                    + " where po.active='Y' and itn.active='Y' and m.active='Y' and oo2.active='Y' and inv.active='Y' and "
                    + " po.item_names_id=itn.item_names_id and po.model_id=m.model_id and oo1.org_office_id=po.vendor_id "
                    + " and po.org_office_id=oo2.org_office_id and kp.active='Y' and kp.key_person_id=po.key_person_id "
                    + " and po.inventory_id=inv.inventory_id and po.key_person_id='" + key_person_id + "' and po.status_id=s.status_id "
                    + " group by po.quotation_no order by quotation_id desc ";
        }

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {
                PurchaseOrdersBean bean = new PurchaseOrdersBean();
                bean.setQuotation_no(rset.getString("quotation_no"));
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
                bean.setStatus(rset.getString("status"));
                bean.setOrg_office_name(rset.getString("org_office_name"));
                bean.setCustomer_name(rset.getString("key_person_name"));
                bean.setVendor(rset.getString("vendor_name"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error:QuotationModel--getAllQuotations()-- " + e);
        }
        return list;
    }

    public List<PurchaseOrdersBean> getQuotationDetail(String quotation_no, int logged_key_person_id, String role) {
        List<PurchaseOrdersBean> list = new ArrayList<PurchaseOrdersBean>();

        String query = " select po.quotation_no,itn.item_name,itn.item_names_id,m.model,m.model_id,m.model_no,m.part_no, "
                + " oo1.org_office_name as vendor_name,po.lead_time, "
                + " oo1.org_office_id as vendor_id, "
                + " po.req_qty,m.model_id,po.rate,po.quotation_id,po.description, "
                + " kp.key_person_name,kp.key_person_id,oo2.org_office_name as org_office_name,oo2.org_office_id as org_office_id, "
                + " po.date_time,oo1.mobile_no1 as vendor_mobile,oo1.email_id1 as vendor_email,oo1.address_line1 "
                + " as vendor_add1,oo1.address_line2 "
                + " as vendor_add2,oo1.address_line3 as vendor_add3,ct1.city_name as vendor_city,ct2.city_name as customer_city "
                + " ,oo2.mobile_no1 as customer_mobile,oo2.email_id1 as customer_email,oo2.address_line1 as customer_add1,oo2.address_line2 "
                + " as customer_add2,oo2.address_line3 as customer_add3,s.status "
                + " from quotation po,item_names itn,model m,org_office oo1,org_office oo2,inventory inv,city ct1,city ct2,key_person kp,status s "
                + " where po.active='Y' "
                + " and ct1.active='Y' and ct1.city_id=oo1.city_id  and itn.active='Y' and m.active='Y' and oo1.active='Y' and inv.active='Y' "
                + " and  po.item_names_id=itn.item_names_id  and po.model_id=m.model_id and po.vendor_id=oo1.org_office_id and kp.active='Y' "
                + " and ct2.active='Y' and ct2.city_id=oo2.city_id and s.status_id=po.status_id"
                + " and po.inventory_id=inv.inventory_id  and oo2.active='Y' and oo2.org_office_id=po.org_office_id "
                + " and kp.key_person_id=po.key_person_id ";
        if (!quotation_no.equals("") && quotation_no != null) {
            query += " and po.quotation_no='" + quotation_no + "' ";
        }
        if (role.equals("Incharge")) {
            query += " and po.key_person_id = '" + logged_key_person_id + "' ";
        }

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {
                PurchaseOrdersBean bean = new PurchaseOrdersBean();
                bean.setOrder_no(rset.getString("quotation_no"));
                bean.setVendor(rset.getString("vendor_name"));
                bean.setVendor_id(rset.getInt("vendor_id"));
                bean.setItem_name(rset.getString("item_name"));
                bean.setItem_names_id(rset.getInt("item_names_id"));
                bean.setModel(rset.getString("model"));
                bean.setModel_id(rset.getInt("model_id"));
                bean.setDate_time(rset.getString("date_time"));
                bean.setVendor_mobile(rset.getString("vendor_mobile"));
                bean.setVendor_email(rset.getString("vendor_email"));
                bean.setVendor_address(rset.getString("vendor_add1") + " " + rset.getString("vendor_add2") + " " + rset.getString("vendor_add3") + "," + rset.getString("vendor_city"));
                bean.setCustomer_mobile(rset.getString("customer_mobile"));
                bean.setCustomer_email(rset.getString("customer_email"));
                bean.setCustomer_address(rset.getString("customer_add1") + " " + rset.getString("customer_add2") + " " + rset.getString("customer_add3") + "," + rset.getString("customer_city"));
                bean.setCustomer_name(rset.getString("key_person_name"));
                bean.setKey_person_id(rset.getInt("key_person_id"));
                bean.setCustomer_office_name(rset.getString("org_office_name"));
                bean.setOrg_office_id(rset.getString("org_office_id"));
                bean.setStatus(rset.getString("status"));
                bean.setPurchase_order_id(rset.getInt("quotation_id"));
                bean.setRate(rset.getString("rate"));
                bean.setLead_time(rset.getInt("lead_time"));
                bean.setQty(rset.getString("req_qty"));
                bean.setDescription(rset.getString("description"));

                String model_no = rset.getString("model_no");
                String part_no = rset.getString("part_no");
                if (model_no.equals("")) {
                    bean.setModel_no(part_no);
                }
                if (part_no.equals("")) {
                    bean.setModel_no(model_no);
                }
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
            System.out.println("Error:QuotationModel--getQuotationDetail()-- " + e);
        }
        return list;
    }

    public int discardQuotation(String quotation_no) {
        int rowsAffected = 0;
        try {

            String query = " update quotation set active='N' where quotation_no='" + quotation_no + "' and active='Y' ";
            PreparedStatement psmt = connection.prepareStatement(query);
            rowsAffected = psmt.executeUpdate();

            if (rowsAffected > 0) {
                message = "Quotation Discarded Successfully!..";
                messageBGColor = "green";
            } else {
                message = "Something went Wrong!..";
                messageBGColor = "red";
            }
        } catch (Exception e) {
            message = "Something went Wrong!..";
            messageBGColor = "red";
            System.err.println("QuotationModel discardQuotation Error---------" + e);
        }
        return rowsAffected;
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
        document.addTitle("RFQ");
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

    public void addContent(Document document, String logo_path, String quotation_no, int logged_key_person_id, String role) throws DocumentException, MalformedURLException, BadElementException, IOException {
        List<PurchaseOrdersBean> list = getQuotationDetail(quotation_no, logged_key_person_id, role);

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
        Chunk chunk3 = new Chunk("Quotation", bold22);
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
        table1.addCell(new Phrase("RFQ Number", bold10));
        table1.addCell(new Phrase(" " + quotation_no, normal));
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
        float[] colproduct = {1, 5, 5, 2, 3, 3};
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
        table_product.addCell(c_qty);
        table_product.addCell(c_price);
        table_product.addCell(c_total);

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
            table_product.addCell(new Phrase(" " + list.get(i).getQty(), normal));
            table_product.addCell(new Phrase(String.valueOf(" " + Integer.parseInt(list.get(i).getRate())), normal));
            table_product.addCell(new Phrase(" " + (Integer.parseInt(list.get(i).getRate()) * Integer.parseInt(list.get(i).getQty())), normal));
            total_price = total_price + (Integer.parseInt(list.get(i).getRate()) * Integer.parseInt(list.get(i).getQty()));
        }

        PdfPCell c_subtotal = new PdfPCell();
        c_subtotal = new PdfPCell(new Phrase(" Subtotal", bold10));

        PdfPCell c_subtotal_val = new PdfPCell();
        c_subtotal_val = new PdfPCell(new Phrase(String.valueOf(" " + total_price), normal));

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
        c_notes = new PdfPCell(new Phrase(" Terms And Conditions", bold12));
        c_notes.setBackgroundColor(BaseColor.DARK_GRAY);

        PdfPCell c_blank = new PdfPCell();
        c_blank = new PdfPCell(new Phrase(" "));
        table_notes.addCell(c_notes);

        table_notes.addCell(list.get(0).getDescription());
//        table_notes.addCell(c_blank);
//        table_notes.addCell(c_blank);

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

    public String sentMail(String destination) {
        // SMTP server information

        String host = "smtp.gmail.com";
        String port = "587";
        String mailFrom = "smartmeter.apogee@gmail.com";
        String password = "jpss1277";

        // outgoing message information
        String mailTo = "komal.apogee@gmail.com";
//        String mailTo = mail_id;
        String subject = "Quotation";
        String message = "Hello Sir/Mam, Please find attached pdf file of Quotation.";
        String result = "";
        try {
            result = sendPlainTextEmail(host, port, mailFrom, password, mailTo,
                    subject, message, destination);

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
