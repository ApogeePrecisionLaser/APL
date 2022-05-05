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

    public ArrayList<PurchaseOrdersBean> viewCart(int logged_key_person_id, String user_role) {
        ArrayList<PurchaseOrdersBean> list = new ArrayList<PurchaseOrdersBean>();
        String query = "";
        String query_id = "";
        try {
           

        } catch (Exception e) {
            System.err.println("PurchaseOrdersModel viewCart() Exception------------" + e);
        }
        return list;
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
