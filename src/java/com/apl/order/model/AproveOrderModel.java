package com.apl.order.model;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import org.json.simple.JSONObject;
import com.DBConnection.DBConnection;
import com.inventory.tableClasses.ApproveIndent;
import java.io.File;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import static java.time.LocalDateTime.now;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import org.apache.commons.fileupload.FileItem;
import org.json.simple.JSONArray;

/**
 *
 * @author Komal
 */
public class AproveOrderModel {

    private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "#a2a220";
    private final String COLOR_ERROR = "red";
    int item_id = 0;
    int indent_table_id = 0;

    public void setConnection(Connection con) {
        try {
            connection = con;
        } catch (Exception e) {
            System.out.println("InventoryModel setConnection() Error: " + e);
        }
    }

    public List<ApproveIndent> showIndents(String logged_key_person, String indent_status) {
        List<ApproveIndent> list = new ArrayList<ApproveIndent>();
        if (indent_status.equals("All")) {
            indent_status = "";
        }
        String query = " select indt.order_no,indt.date_time,indt.description "
                + " ,s.status,kp1.key_person_name as requested_by,indt.order_table_id  from "
                + " order_table indt,key_person kp1,key_person kp2, "
                + " status s where indt.requested_to=kp2.key_person_id "
                + " and indt.requested_by=kp1.key_person_id  "
                + " and indt.status_id=s.status_id and indt.active='Y' and kp1.active='Y' and kp2.active='Y'  ";

        if (!logged_key_person.equals("") && logged_key_person != null) {
            query += " and kp2.key_person_name='" + logged_key_person + "' ";
        }
        if (!indent_status.equals("") && indent_status != null) {
            query += " and s.status='" + indent_status + "' ";
        }

        query += " order by indt.order_no desc ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                ApproveIndent bean = new ApproveIndent();
                bean.setIndent_no(rset.getString("order_no"));
                bean.setDate_time(rset.getString("date_time"));

                String status = rset.getString("status");

                bean.setStatus(status);
                bean.setRequested_by(rset.getString("requested_by"));
                bean.setDescription(rset.getString("description"));
                bean.setIndent_table_id(rset.getInt("order_table_id"));

                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: InventoryModel showdata-" + e);
        }
        return list;
    }

    public List<ApproveIndent> getStatus() {
        List<ApproveIndent> list = new ArrayList<ApproveIndent>();

        String query = " select status,status_id from status  order by status";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                ApproveIndent bean = new ApproveIndent();
                String status = rset.getString("status");

                bean.setStatus_id(rset.getInt("status_id"));
                bean.setStatus(status);

                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: InventoryModel getStatus-" + e);
        }
        return list;
    }

    public List<ApproveIndent> getIndentItems(int indent_table_id) {
        List<ApproveIndent> list = new ArrayList<ApproveIndent>();

//        String query = "select inv.stock_quantity,indt.order_no,itn.item_name,indi.required_qty,indi.expected_date_time,indi.approved_qty  ,s1.status as indent_status,\n" +
//" s2.status as item_status,indi.order_item_id,indt.order_table_id,pm.payment_mode \n" +
//" from order_table indt,order_item indi, item_names itn,inventory inv,inventory_basic invbs,\n" +
//"  status s1,status s2,payment_mode as pm \n" +
//"  where indt.order_table_id=indi.order_table_id and  pm.order_id=indt.order_table_id and indi.item_names_id=itn.item_names_id   \n" +
//"  and indt.status_id=s1.status_id  and indi.status_id=s2.status_id and indt.active='Y' and indi.active='Y' and itn.active='Y'  \n" +
//"  and invbs.item_names_id=itn.item_names_id and inv.inventory_basic_id=invbs.inventory_basic_id and inv.active='Y'and invbs.active='Y'"
//                + " and indt.order_table_id='" + indent_table_id + "' ";
//        String query = "select indt.order_no,itn.item_name,indi.required_qty,indi.expected_date_time,indi.approved_qty  ,s1.status as indent_status,\n"
//                + " s2.status as item_status,indi.order_item_id,indt.order_table_id,pm.payment_mode  from order_table indt,order_item indi, item_names itn, \n"
//                + " status s1,status s2,payment_mode as pm where indt.order_table_id=indi.order_table_id and "
//                + " pm.order_id=indt.order_table_id and indi.item_names_id=itn.item_names_id  "
//                + " and indt.status_id=s1.status_id  and indi.status_id=s2.status_id and indt.active='Y' and indi.active='Y' and itn.active='Y'  \n"
//                + " and indt.order_table_id='" + indent_table_id + "' ";
String query ="select indt.order_no,itn.item_name,indi.required_qty,indi.expected_date_time,indi.approved_qty "
                + " ,s1.status as indent_status,s2.status as item_status,indi.order_item_id,indt.order_table_id,inv.stock_quantity,indi.deliver_qty,indt.requested_by ,indt.requested_to,pm.payment_mode "
                + " from order_table indt,order_item indi, item_names itn,payment_mode as pm, "
                + " status s1,status s2,inventory inv,inventory_basic ib where indt.order_table_id=indi.order_table_id and indi.item_names_id=itn.item_names_id "
                + " and ib.inventory_basic_id=inv.inventory_basic_id and  pm.order_id=indt.order_table_id  and ib.item_names_id=itn.item_names_id and ib.active='Y' "
                + " and inv.active='Y' "
                + " and indt.status_id=s1.status_id and indi.status_id=s2.status_id and indt.active='Y' and indi.active='Y' and itn.active='Y' "
                + " and indt.order_table_id='" + indent_table_id + "' and inv.key_person_id='115'  ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                ApproveIndent bean = new ApproveIndent();
                bean.setIndent_no(rset.getString("order_no"));
                bean.setItem_name((rset.getString("item_name")));
                bean.setPurpose("Test");
                bean.setStock_qty(rset.getInt("stock_quantity"));
                bean.setRequired_qty(rset.getInt("required_qty"));
                bean.setApproved_qty(rset.getInt("approved_qty"));
                bean.setExpected_date_time(rset.getString("expected_date_time"));
                String indent_status = rset.getString("indent_status");
                bean.setStatus(indent_status);
                String item_status = rset.getString("item_status");
                bean.setItem_status(item_status);
                bean.setIndent_item_id(rset.getInt("order_item_id"));
                bean.setIndent_table_id(rset.getInt("order_table_id"));
                bean.setPaymentmode(rset.getString("payment_mode"));

                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: InventoryModel showdata-" + e);
        }
        return list;
    }

    public String updateRecord(ApproveIndent bean, int indent_item_id, int indent_table_id) {
        int updateRowsAffected = 0;
        System.err.println("indent_item_id------------" + indent_item_id);
        System.err.println("indent_table_id------------" + indent_table_id);
        String status = bean.getStatus();
        if (status.equals("Approve")) {
            status = "Approved";
        }
        String item_status = bean.getItem_status();

        int status_id = getStatusId(status);
        int item_status_id = getStatusId(item_status);

        String query2 = " UPDATE order_item SET status_id=?,approved_qty=? WHERE order_item_id=? ";

        int rowsAffected = 0;
        int updateRowsAffected2 = 0;
        int map_count = 0;
        try {

            PreparedStatement pstm = connection.prepareStatement(query2);
            pstm.setInt(1, item_status_id);
            if (item_status.equals("Approved")) {
                pstm.setInt(2, bean.getApproved_qty());
            } else {
                pstm.setInt(2, 0);
            }

            pstm.setInt(3, indent_item_id);
            updateRowsAffected = pstm.executeUpdate();

            String query = " update order_table set status_id=? where order_table_id=? ";
            PreparedStatement pstm2 = connection.prepareStatement(query);
            pstm2.setInt(1, status_id);
            pstm2.setInt(2, indent_table_id);
            updateRowsAffected2 = pstm2.executeUpdate();

        } catch (Exception e) {
            System.out.println("ApproveIndentModel updateRecord() Error: " + e);
        }
        if (updateRowsAffected2 > 0) {
            message = "Your Indent is '" + status + "'!.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return message + "&" + status;
    }

    public String insertPrice(int orderid, int orderitemid, String price) {
        int updateRowsAffected = 0;
        String status = "error";
        String query2 = "insert into orders_sales_pricing (order_id,order_item_id,prices) values(?,?,?) ";
        try {
            PreparedStatement pstm = connection.prepareStatement(query2);
            pstm.setInt(1, orderid);
            pstm.setInt(2, orderitemid);
            pstm.setString(3, price);
            updateRowsAffected = pstm.executeUpdate();

            if (updateRowsAffected > 0) {
                status = "success";
            }

        } catch (Exception e) {
            System.out.println("ApproveIndentModel updateRecord() Error: " + e);
        }

        return status;
    }

    public int getRequestedKeyPersonId(String person_name) {
        String query = "SELECT key_person_id FROM key_person WHERE key_person_name = '" + person_name + "' and active='Y' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("key_person_id");
        } catch (Exception e) {
            System.out.println("getRequestedByKeyPersonId Error: " + e);
        }
        return id;
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
            System.out.println("getStatusId Error: " + e);
        }
        return id;
    }

    public int getKeyPersonId(String key_person_name) {

        String query = "SELECT key_person_id FROM key_person WHERE key_person_name = '" + key_person_name + "' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("key_person_id");
        } catch (Exception e) {
            System.out.println("getKeyPersonId Error: " + e);
        }
        return id;
    }

    public List<String> getStatus(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT status from status WHERE status_id in(3,6) ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String status = (rset.getString("status"));
                if (status.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(status);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such status  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:IndentModel--getStatus()-- " + e);
        }
        return list;
    }

    public List<String> getRequestedByKeyPerson(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT key_person_name from key_person where active='Y' ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String key_person_name = (rset.getString("key_person_name"));
                if (key_person_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(key_person_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such key_person_name  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:IndentModel--getRequestedByKeyPerson()-- " + e);
        }
        return list;
    }

    public List<String> getRequestedToKeyPerson(String q, String requested_by) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT key_person_name from key_person where active='Y' and key_person_name!='" + requested_by + "' ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String key_person_name = (rset.getString("key_person_name"));
                if (key_person_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(key_person_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such key_person_name  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:IndentModel--getRequestedByKeyPerson()-- " + e);
        }
        return list;
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
            System.out.println("ItemNameModel closeConnection() Error: " + e);
        }
    }
}
