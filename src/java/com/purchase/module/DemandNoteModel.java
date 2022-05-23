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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author komal
 */
public class DemandNoteModel {

    static private Connection connection;
    private String driver, url, user, password;
    static private String message, messageBGColor = "#a2a220";
    static private final String COLOR_OK = "green";
    static private final String COLOR_ERROR = "red";

    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("DemandNoteModel setConnection() Error: " + e);
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("DemandNoteModel closeConnection: " + e);
        }
    }

    public void setConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("DemandNoteModel setConnection error: " + e);
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
            System.out.println("DemandNoteModel getItemId Error: " + e);
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
            System.out.println("DemandNoteModel getModelId Error: " + e);
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
            System.out.println("DemandNoteModel getVendorId Error: " + e);
        }
        return id;
    }

    public String getVendor(int item_id) {
        String query = " SELECT oo.org_office_id,count(*) as count,oo.org_office_name "
                + " FROM vendor_item_map vim,item_names itn,org_office oo "
                + " WHERE itn.item_names_id='" + item_id + "' "
                + " and oo.active='Y' and vim.active='Y' and itn.active='Y' "
                + " and vim.org_office_id=oo.org_office_id  and vim.item_names_id=itn.item_names_id  ";
        String vendor = "";
        int rowsAffected = 0;
        int count = 0;
        int org_office_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                count = rset.getInt("count");
                org_office_id = rset.getInt("org_office_id");
                vendor = rset.getString("org_office_name");
            }

        } catch (Exception e) {
            System.out.println("DemandNoteModel getVendor Error: " + e);
        }
        return vendor;
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
            System.out.println("DemandNoteModel getInventoryId Error: " + e);
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
            System.out.println("DemandNoteModel getOrgOfficeId Error: " + e);
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
            System.out.println("Error:DemandNoteModel--getVendorName()-- " + e);
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
            System.out.println("Error:DemandNoteModel--getOrgOffice()-- " + e);
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
            System.err.println("DemandNoteModel getNewOrderData Exception--" + e);
        }
        return list;
    }

    public int getCounting() {
        int counting = 100000;
        int count = 0;
        String query = " SELECT order_no FROM demand_note order by demand_note_id desc limit 1 ";
        try {
            PreparedStatement psmt = connection.prepareStatement(query);
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                String order_no = rs.getString("order_no");
//                String order_no_arr[] = order_no.split("_");
//                int length = (order_no_arr.length) - 1;
                count = Integer.parseInt(order_no.substring(3));
                counting = count;
            }
        } catch (Exception ex) {
            System.out.println("ERROR: in getCounting() in DemandNoteModel : " + ex);
        }
        return counting + 1;
    }

    public ArrayList<PurchaseOrdersBean> viewCart(int logged_key_person_id, String user_role) {
        ArrayList<PurchaseOrdersBean> list = new ArrayList<PurchaseOrdersBean>();
        String query = "";
        String query_id = "";
        try {
            if (user_role.equals("Admin") || user_role.equals("Super Admin")) {
                query_id = " select oo2.org_office_id "
                        + " as org_office_id "
                        + " from demand_note_cart_table poct,key_person kp,org_office oo2,model m,item_names "
                        + " itn,cart_status cs,vendor_item_map vim  where poct.active='Y' and kp.active='Y' "
                        + " and oo2.active='Y' and m.active='Y' and itn.active='Y'  and vim.active='Y' and  "
                        + " poct.key_person_id=kp.key_person_id  and "
                        + " poct.model_id=m.model_id  and poct.item_names_id=itn.item_names_id and vim.item_names_id=itn.item_names_id "
                        + " and poct.org_office_id=oo2.org_office_id and "
                        + " cs.cart_status_id=poct.cart_status_id "
                        + " and poct.cart_status_id=1 ";
                query_id += " group by oo2.org_office_name ";
            } else {
                query_id = " select oo2.org_office_id "
                        + " as org_office_id "
                        + " from demand_note_cart_table poct,key_person kp,org_office oo2,model m,item_names "
                        + " itn,cart_status cs,vendor_item_map vim  where poct.active='Y' and kp.active='Y' "
                        + " and oo2.active='Y' and m.active='Y' and itn.active='Y'  and vim.active='Y' and  "
                        + " poct.key_person_id=kp.key_person_id  and "
                        + " poct.model_id=m.model_id  and poct.item_names_id=itn.item_names_id and vim.item_names_id=itn.item_names_id "
                        + " and poct.org_office_id=oo2.org_office_id and "
                        + " cs.cart_status_id=poct.cart_status_id "
                        + " and poct.cart_status_id=1 ";
                if (logged_key_person_id != 0) {
                    query_id += " and kp.key_person_id='" + logged_key_person_id + "' ";
                }
                query_id += " group by oo2.org_office_name ";
            }
            ResultSet rst1 = connection.prepareStatement(query_id).executeQuery();
            while (rst1.next()) {
                int org_office_id = rst1.getInt("org_office_id");
                if (user_role.equals("Admin") || user_role.equals("Super Admin")) {
                    query = "  select count(poct.quantity) as qty,kp.key_person_name, "
                            + " oo2.org_office_name "
                            + " as org_office_name, "
                            + " m.model,itn.item_name,cs.cart_status,oo2.org_office_id as org_office_id "
                            + " from demand_note_cart_table poct,key_person kp,org_office oo2,model m,item_names "
                            + " itn,cart_status cs,vendor_item_map vim  where poct.active='Y' and kp.active='Y' "
                            + " and oo2.active='Y' and m.active='Y' and itn.active='Y'  and vim.active='Y' and "
                            + " poct.key_person_id=kp.key_person_id and "
                            + " poct.model_id=m.model_id  and poct.item_names_id=itn.item_names_id and vim.item_names_id=itn.item_names_id "
                            + " and poct.org_office_id=oo2.org_office_id "
                            + " and cs.cart_status_id=poct.cart_status_id "
                            + " and poct.cart_status_id=1 and poct.org_office_id='" + org_office_id + "' ";
                    query += " group by oo2.org_office_name ";
                } else {
                    query = " select count(poct.quantity) as qty,kp.key_person_name, "
                            + " oo2.org_office_name "
                            + " as org_office_name, "
                            + " m.model,itn.item_name,cs.cart_status,oo2.org_office_id as org_office_id "
                            + " from demand_note_cart_table poct,key_person kp,org_office oo2,model m,item_names "
                            + " itn,cart_status cs,vendor_item_map vim  where poct.active='Y' and kp.active='Y' "
                            + " and oo2.active='Y' and m.active='Y' and itn.active='Y'  and vim.active='Y' and "
                            + " poct.key_person_id=kp.key_person_id  and "
                            + " poct.model_id=m.model_id  and poct.item_names_id=itn.item_names_id and vim.item_names_id=itn.item_names_id "
                            + " and poct.org_office_id=oo2.org_office_id "
                            + " and cs.cart_status_id=poct.cart_status_id "
                            + "  and poct.cart_status_id=1 and poct.org_office_id='" + org_office_id + "' ";
                    if (logged_key_person_id != 0) {
                        query += " and kp.key_person_id='" + logged_key_person_id + "' ";
                    }
                    query += "  group by oo2.org_office_name ";
                }

                ResultSet rst = connection.prepareStatement(query).executeQuery();
                while (rst.next()) {
                    PurchaseOrdersBean bean = new PurchaseOrdersBean();
                    bean.setOrg_office_name(rst.getString("org_office_name"));
                    bean.setQty(rst.getString("qty"));
                    bean.setOrg_office_id(rst.getString("org_office_id"));
                    list.add(bean);
                }
            }

        } catch (Exception e) {
            System.err.println("DemandNoteModel viewCart() Exception------------" + e);
        }

        return list;
    }

    public int getCurrentQuantity(String item_names_id, String model_id, int logged_key_person_id) {
        int current_qty = 0;
        try {
            String query = " SELECT quantity FROM demand_note_cart_table WHERE key_person_id='" + logged_key_person_id + "' "
                    + " and model_id='" + model_id + "' "
                    + " and item_names_id='" + item_names_id + "' and cart_status_id=1 and active='Y' ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                current_qty = rset.getInt("quantity");
            }
        } catch (Exception e) {
            System.err.println("DemandNoteModel getCurrentQuantity() error--------" + e);
        }
        return current_qty;
    }

    public static int getRevisionno(PurchaseOrdersBean bean, int logged_key_person_id, int item_id) {
        int revision = 0;
        try {

            String query = " SELECT max(revision_no) as revision_no FROM demand_note_cart_table where "
                    + "  key_person_id='" + logged_key_person_id + "' "
                    + " and model_id='" + bean.getModel_id() + "'  "
                    + " and item_names_id='" + item_id + "' and cart_status_id=1 and active='Y' ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
            System.err.println("DemandNoteModel getRevisionno() error--------" + e);
        }
        return revision;
    }

    public int insertRecord(PurchaseOrdersBean bean, int logged_org_office_id, String role, int logged_key_person_id) throws SQLException {
        String query = " INSERT INTO demand_note(order_no,item_names_id,model_id,"
                + " org_office_id,key_person_id,status_id,req_qty,approved_qty,inventory_id,active, "
                + " revision_no,description, "
                + " remark,date_time) "
                + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
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
            psmt.setString(1, bean.getDemand_note_no());
            psmt.setInt(2, bean.getItem_names_id());
            psmt.setInt(3, bean.getModel_id());
            psmt.setInt(4, logged_org_office_id);
            psmt.setInt(5, logged_key_person_id);
            psmt.setInt(6, 2);
            psmt.setString(7, bean.getQty());
            psmt.setInt(8, 0);
            psmt.setInt(9, inventory_id);
            psmt.setString(10, "Y");
            psmt.setInt(11, bean.getRevision_no());
            psmt.setString(12, "");
            psmt.setString(13, "");
            psmt.setString(14, date_time);

            rowsAffected = psmt.executeUpdate();

            if (rowsAffected > 0) {
                message = "Record saved successfully.";
                messageBGColor = COLOR_OK;
            } else {
                message = "Cannot save the record, some error.";
                messageBGColor = COLOR_ERROR;
            }
        } catch (Exception e) {
            System.err.println("DemandNoteModel insertRecord() Exception---" + e);
        }

        return rowsAffected;
    }

    public List<PurchaseOrdersBean> getAllExistingDemandNotes(int key_person_id, String role) {
        List<PurchaseOrdersBean> list = new ArrayList<PurchaseOrdersBean>();
        String query = "";
        List<String> status_list = new ArrayList<>();
        if (role.equals("Admin") || role.equals("Super Admin")) {
            query = " select distinct po.order_no,oo2.org_office_name as org_office_name, "
                    + " po.date_time,oo1.mobile_no1,s.status,kp.key_person_name "
                    + " from demand_note po,item_names itn,model m, "
                    + " org_office oo1,org_office oo2,inventory inv,status s,key_person kp "
                    + " where po.active='Y' and itn.active='Y' and m.active='Y' and oo2.active='Y' and inv.active='Y' and "
                    + " po.item_names_id=itn.item_names_id and po.model_id=m.model_id  "
                    + " and po.org_office_id=oo2.org_office_id and kp.active='Y' and kp.key_person_id=po.key_person_id "
                    + " and po.inventory_id=inv.inventory_id and po.status_id=s.status_id "
                    + " group by po.order_no order by demand_note_id desc ";
        } else {
            query = " select distinct po.order_no,oo2.org_office_name as org_office_name, "
                    + " po.date_time,oo1.mobile_no1,s.status,kp.key_person_name "
                    + " from demand_note po,item_names itn,model m, "
                    + " org_office oo1,org_office oo2,inventory inv,status s,key_person kp "
                    + " where po.active='Y' and itn.active='Y' and m.active='Y' and oo2.active='Y' and inv.active='Y' and "
                    + " po.item_names_id=itn.item_names_id and po.model_id=m.model_id  "
                    + " and po.org_office_id=oo2.org_office_id and kp.active='Y' and kp.key_person_id=po.key_person_id "
                    + " and po.inventory_id=inv.inventory_id and po.key_person_id='" + key_person_id + "' and po.status_id=s.status_id "
                    + " group by po.order_no order by demand_note_id desc ";
        }

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {
                PurchaseOrdersBean bean = new PurchaseOrdersBean();
                bean.setDemand_note_no(rset.getString("order_no"));
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
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error:DemandNoteModel--getAllExistingDemandNotes()-- " + e);
        }
        return list;
    }

    public List<PurchaseOrdersBean> getOrderDetail(String order_no, int logged_key_person_id, String role) {
        List<PurchaseOrdersBean> list = new ArrayList<PurchaseOrdersBean>();

        String query = " select po.order_no,itn.item_name,itn.item_names_id,m.model,m.model_no,m.part_no, "
                + " po.req_qty,po.approved_qty,m.model_id,po.demand_note_id, "
                + " kp.key_person_name,kp.key_person_id,oo2.org_office_name as org_office_name,"
                + " po.date_time, "
                + " oo2.address_line1 as customer_add1,oo2.address_line2 "
                + " as customer_add2,oo2.address_line3 as customer_add3,s.status "
                + " from demand_note po,item_names itn,model m,org_office oo2,inventory inv,city ct2, "
                + " key_person kp,status s "
                + " where po.active='Y' "
                + " and itn.active='Y' and m.active='Y' and inv.active='Y' "
                + " and  po.item_names_id=itn.item_names_id  and po.model_id=m.model_id  and kp.active='Y' "
                + " and ct2.active='Y' and ct2.city_id=oo2.city_id and s.status_id=po.status_id"
                + " and po.inventory_id=inv.inventory_id  and oo2.active='Y' and oo2.org_office_id=po.org_office_id "
                + " and kp.key_person_id=po.key_person_id ";
        if (!order_no.equals("") && order_no != null) {
            query += " and po.order_no='" + order_no + "' ";
        }
        if (role.equals("Incharge")) {
            query += " and po.key_person_id = '" + logged_key_person_id + "' ";
        }

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {
                PurchaseOrdersBean bean = new PurchaseOrdersBean();
                bean.setDemand_note_no(rset.getString("order_no"));
                bean.setItem_name(rset.getString("item_name"));
                bean.setItem_names_id(rset.getInt("item_names_id"));
                bean.setModel_id(rset.getInt("model_id"));
                bean.setModel(rset.getString("model"));
                bean.setDate_time(rset.getString("date_time"));
                bean.setCustomer_name(rset.getString("key_person_name"));
                bean.setKey_person_id(rset.getInt("key_person_id"));
                bean.setCustomer_office_name(rset.getString("org_office_name"));
                bean.setStatus(rset.getString("status"));
                bean.setDemand_note_id(rset.getInt("demand_note_id"));
                String model_no = rset.getString("model_no");
                String part_no = rset.getString("part_no");
                bean.setApproved_qty(rset.getInt("approved_qty"));
                if (model_no.equals("")) {
                    bean.setModel_no(part_no);
                }
                if (part_no.equals("")) {
                    bean.setModel_no(model_no);
                }
                bean.setQty(rset.getString("req_qty"));
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
            System.out.println("Error:DemandNoteModel--getOrderDetail()-- " + e);
        }
        return list;
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
            System.out.println("DemandNoteModel getStatusId Error: " + e);
        }
        return id;
    }

    public String approveDemandNote(PurchaseOrdersBean bean, int demand_note_id, int i, int size) throws SQLException {
        int updateRowsAffected = 0;
        String status = bean.getStatus();
        if (status.equals("Confirm")) {
            status = "Approved";
        }
        if (status.equals("Denied All")) {
            status = "Denied";
        }

        int status_id = getStatusId(status);

        String query2 = " UPDATE demand_note SET status_id=?,approved_qty=? WHERE demand_note_id=? ";

        int rowsAffected = 0;
        int updateRowsAffected2 = 0;
        int updateRowsAffected3 = 0;
        int map_count = 0;
        try {
            PreparedStatement pstm = connection.prepareStatement(query2);
            pstm.setInt(1, status_id);
            pstm.setInt(2, bean.getApproved_qty());
            pstm.setInt(3, demand_note_id);
            updateRowsAffected = pstm.executeUpdate();

        } catch (Exception e) {
            System.out.println("DemandNoteModel approveDemandNote() Error: " + e);
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

}
