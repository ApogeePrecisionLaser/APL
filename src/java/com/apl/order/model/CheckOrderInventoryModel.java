package com.apl.order.model;

import com.inventory.model.*;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.simple.JSONObject;
import com.DBConnection.DBConnection;
import com.inventory.tableClasses.CheckInventory;
import java.io.File;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import static java.time.LocalDateTime.now;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

/**
 *
 * @author Akash
 */
public class CheckOrderInventoryModel {

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
            System.out.println("CheckOrderInventoryModel setConnection() Error: " + e);
        }
    }

    public List<CheckInventory> showIndents(String logged_designation, String indent_status, String search_by_date) {
        List<CheckInventory> list = new ArrayList<CheckInventory>();
        if (indent_status.equals("All")) {
            indent_status = "";
        }
        String query = "";
        if (logged_designation.equals("Store Incharge")) {
            query = " select indt.order_no,indt.date_time,indt.description "
                    + " ,s.status,kp1.key_person_name as requested_by,kp2.key_person_name as requested_to,indt.order_table_id "
                    + " from order_table indt,key_person kp1,key_person kp2, "
                    + " status s,designation d where indt.requested_to=kp2.key_person_id "
                    + " and indt.requested_by=kp1.key_person_id "
                    + " and indt.status_id=s.status_id and indt.active='Y' "
                    + " and kp1.active='Y' and kp2.active='Y' and d.active='Y' and indt.status_id in(6,7,9,3,11,13,14) and d.designation_id='5' ";
            if (!indent_status.equals("") && indent_status != null) {
                query += " and s.status='" + indent_status + "' ";
            }
            if (!search_by_date.equals("") && search_by_date != null) {
                query += " and indt.date_time like '" + search_by_date + "%' ";
            }
            query += " order by indt.order_table_id desc ";
        }

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                CheckInventory bean = new CheckInventory();
                bean.setIndent_no(rset.getString("order_no"));
                bean.setDate_time(rset.getString("date_time"));
                String status = rset.getString("status");
                bean.setStatus(status);
                bean.setRequested_by(rset.getString("requested_by"));
                bean.setRequested_to(rset.getString("requested_to"));
                bean.setDescription(rset.getString("description"));
                bean.setIndent_table_id(rset.getInt("order_table_id"));
//                bean.setIndent_item_id(rset.getInt("indent_item_id"));

                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: CheckOrderInventoryModel showIndents-" + e);
        }
        return list;
    }

    public List<CheckInventory> getStatus() {
        List<CheckInventory> list = new ArrayList<CheckInventory>();

        String query = " select status,status_id from status order by status ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                CheckInventory bean = new CheckInventory();
                String status = rset.getString("status");
                bean.setStatus_id(rset.getInt("status_id"));
                bean.setStatus(status);

                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: CheckOrderInventoryModel getStatus-" + e);
        }
        return list;
    }

    public List<CheckInventory> getIndentItems(int indent_table_id, int logged_key_person_id) {
        List<CheckInventory> list = new ArrayList<CheckInventory>();

        String query = " select indt.order_no,itn.item_name,indi.required_qty,indi.expected_date_time,indi.approved_qty  , m.model,  s1.status as indent_status,s2.status as item_status,\n"
                + "  indi.order_item_id,indt.order_table_id,inv.stock_quantity,indi.deliver_qty,indt.requested_by ,indt.requested_to  \n"
                + "  from order_table indt,order_item indi, item_names itn, manufacturer_item_map mim ,model m , status s1,status s2,inventory inv,inventory_basic ib\n"
                + "  where indt.order_table_id=indi.order_table_id and mim.active='Y' \n"
                + " and m.active='Y' and m.model_id=ib.model_id  and mim.item_names_id=itn.item_names_id  and m.manufacturer_item_map_id=mim.manufacturer_item_map_id and \n"
                + " indi.model_id=m.model_id and ib.inventory_basic_id=inv.inventory_basic_id and ib.item_names_id=itn.item_names_id and ib.active='Y' \n"
                + " and inv.active='Y'  and indt.status_id=s1.status_id and indi.status_id=s2.status_id and indt.active='Y' and indi.active='Y' and itn.active='Y'  \n"
                + " and indt.order_table_id='" + indent_table_id + "' and inv.key_person_id='" + logged_key_person_id + "' ";
//        String query = " select indt.order_no,itn.item_name,indi.required_qty,indi.expected_date_time,indi.approved_qty "
//                + " ,s1.status as indent_status,s2.status as item_status,indi.order_item_id,indt.order_table_id,inv.stock_quantity,indi.deliver_qty,indt.requested_by ,indt.requested_to "
//                + " from order_table indt,order_item indi, item_names itn, "
//                + " status s1,status s2,inventory inv,inventory_basic ib where indt.order_table_id=indi.order_table_id and indi.item_names_id=itn.item_names_id "
//                + " and ib.inventory_basic_id=inv.inventory_basic_id and ib.item_names_id=itn.item_names_id and ib.active='Y' "
//                + " and inv.active='Y' "
//                + " and indt.status_id=s1.status_id and indi.status_id=s2.status_id and indt.active='Y' and indi.active='Y' and itn.active='Y' "
//                + " and indt.order_table_id='" + indent_table_id + "' and inv.key_person_id='" + logged_key_person_id + "' ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                CheckInventory bean = new CheckInventory();
                bean.setIndent_no(rset.getString("order_no"));
                bean.setItem_name((rset.getString("item_name")));
                bean.setModel((rset.getString("model")));
                bean.setPurpose("Test");
                bean.setRequired_qty(rset.getInt("required_qty"));
                bean.setApproved_qty(rset.getInt("approved_qty"));
                bean.setStock_qty(rset.getInt("stock_quantity"));
                bean.setDelivered_qty(rset.getInt("deliver_qty"));
                bean.setExpected_date_time(rset.getString("expected_date_time"));
                String status = rset.getString("indent_status");
                bean.setStatus(status);
                bean.setItem_status(rset.getString("item_status"));
                bean.setIndent_item_id(rset.getInt("order_item_id"));
                bean.setIndent_table_id(rset.getInt("order_table_id"));
                bean.setRequested_by(rset.getString("requested_by"));
                bean.setRequested_to(rset.getString("requested_to"));

                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: CheckOrderInventoryModel getIndentItems-" + e);
        }
        return list;
    }

    public List<CheckInventory> getIndentItemsForDeliveryChallan(int indent_table_id, int logged_key_person_id) {
        List<CheckInventory> list = new ArrayList<CheckInventory>();

//        String query = " select indt.order_no,itn.item_name,indi.required_qty,indi.expected_date_time,indi.approved_qty , m.model"
//                + " ,s.status,indi.order_item_id,indt.order_table_id,itn.quantity as stock_qty,indi.deliver_qty,indt.requested_by ,indt.requested_to "
//                + " from order_table indt,order_item indi, item_names itn,manufacturer_item_map mim ,model m , "
//                + " status s where indt.order_table_id=indi.order_table_id   and indi.model_id=m.model_id "
//                + " and m.active='Y' and mim.item_names_id=itn.item_names_id and mim.active='Y' and m.manufacturer_item_map_id=mim.manufacturer_item_map_id  and indi.status_id=s.status_id and indt.active='Y' and indi.active='Y' and itn.active='Y' "
//                + " and indt.order_table_id='" + indent_table_id + "' ";
        String query = " select indt.order_no,itn.item_name,indi.required_qty,indi.expected_date_time,indi.approved_qty "
                + " ,s1.status as indent_status,s2.status as item_status,indi.order_item_id,indt.order_table_id,inv.stock_quantity, "
                + " indi.deliver_qty,indt.requested_by ,indt.requested_to,m.model "
                + " from order_table indt,order_item indi, item_names itn, "
                + " status s1,status s2,inventory inv,inventory_basic ib,model m,manufacturer_item_map mim "
                + " where indt.order_table_id=indi.order_table_id and indi.item_names_id=itn.item_names_id "
                + " and ib.inventory_basic_id=inv.inventory_basic_id and ib.item_names_id=itn.item_names_id and ib.active='Y' "
                + " and inv.active='Y' "
                + " and indt.status_id=s1.status_id and indi.status_id=s2.status_id and indt.active='Y' and indi.active='Y' and itn.active='Y' "
                + " and m.active='Y' and mim.active='Y' and m.manufacturer_item_map_id=mim.manufacturer_item_map_id and ib.model_id=m.model_id "
                + " and mim.item_names_id=itn.item_names_id and indi.model_id=m.model_id "
                + " and indt.order_table_id='" + indent_table_id + "' and inv.key_person_id='" + logged_key_person_id + "' ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                CheckInventory bean = new CheckInventory();
                bean.setIndent_no(rset.getString("order_no"));
                bean.setItem_name((rset.getString("item_name")));
                bean.setModel((rset.getString("model")));
                bean.setPurpose("Test");
                bean.setRequired_qty(rset.getInt("required_qty"));
                bean.setApproved_qty(rset.getInt("approved_qty"));
                bean.setStock_qty(rset.getInt("stock_quantity"));
                bean.setDelivered_qty(rset.getInt("deliver_qty"));
                bean.setExpected_date_time(rset.getString("expected_date_time"));
                String status = rset.getString("item_status");
                bean.setStatus(status);
                bean.setIndent_item_id(rset.getInt("order_item_id"));
                bean.setIndent_table_id(rset.getInt("order_table_id"));
                bean.setRequested_by(rset.getString("requested_by"));
                bean.setRequested_to(rset.getString("requested_to"));

                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: CheckOrderInventoryModel getIndentItemsForDeliveryChallan-" + e);
        }
        return list;
    }

    public List<CheckInventory> getIndentItemsForDeliveryChallanAfterPayment(int indent_table_id) {
        List<CheckInventory> list = new ArrayList<CheckInventory>();

        String query = " select indt.order_no,itn.item_name,indi.required_qty,indi.expected_date_time,indi.approved_qty "
                + " ,s.status,indi.order_item_id,indt.order_table_id,itn.quantity as stock_qty,indi.deliver_qty,indt.requested_by ,indt.requested_to "
                + " from order_table indt,order_item indi, item_names itn, "
                + " status s where indt.order_table_id=indi.order_table_id and indi.item_names_id=itn.item_names_id "
                + " and indi.status_id=s.status_id and indt.active='Y' and indi.active='Y' and itn.active='Y' "
                + " and indt.order_table_id='" + indent_table_id + "' and indt.status_id=6 ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                CheckInventory bean = new CheckInventory();
                bean.setIndent_no(rset.getString("order_no"));
                bean.setItem_name((rset.getString("item_name")));
                bean.setPurpose("Test");
                bean.setRequired_qty(rset.getInt("required_qty"));
                bean.setApproved_qty(rset.getInt("approved_qty"));
                bean.setStock_qty(rset.getInt("stock_qty"));
                bean.setDelivered_qty(rset.getInt("deliver_qty"));
                bean.setExpected_date_time(rset.getString("expected_date_time"));
                String status = rset.getString("status");
                bean.setStatus(status);
                bean.setIndent_item_id(rset.getInt("order_item_id"));
                bean.setIndent_table_id(rset.getInt("order_table_id"));
                bean.setRequested_by(rset.getString("requested_by"));
                bean.setRequested_to(rset.getString("requested_to"));

                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: CheckOrderInventoryModel getIndentItemsForDeliveryChallanAfterPayment-" + e);
        }
        return list;
    }

    public String updateRecord(CheckInventory bean, int indent_item_id, int indent_table_id, String task, String logged_org_office,
            String logged_user_name) {
        int updateRowsAffected = 0;
        if (task.equals("Generate Delivery Challan")) {
            task = "Approved";
        }

        int indent_status_id = getStatusId(task);
        String item_status = bean.getItem_status();
        if (task.equals("Less Stock")) {
            item_status = "Less Stock";
        }
        if (task.equals("Denied")) {
            item_status = "Denied";
        } else {
            item_status = "Approved";
        }
        int status_id = getStatusId(item_status);

        String query = " UPDATE order_item SET status_id=?,deliver_qty=? WHERE order_item_id=? ";

        int rowsAffected = 0;
        int updateRowsAffected2 = 0;
        int updateRowsAffected3 = 0;
        int updateRowsAffected4 = 0;
        int map_count = 0;
        try {
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setInt(1, status_id);
            //  if (item_status.equals("Delivery Challan Generated")) {
            pstm.setInt(2, bean.getDelivered_qty());
            //  } else {
            //  pstm.setInt(2, 0);
            //  }

            pstm.setInt(3, indent_item_id);
            updateRowsAffected = pstm.executeUpdate();

            String query2 = " update order_table set status_id=? where order_table_id=? ";
            PreparedStatement pstm2 = connection.prepareStatement(query2);
            pstm2.setInt(1, indent_status_id);
            pstm2.setInt(2, indent_table_id);
            updateRowsAffected2 = pstm2.executeUpdate();

        } catch (Exception e) {
            System.out.println("CheckOrderInventoryModel updateRecord() Error: " + e);
        }
        if (updateRowsAffected2 > 0) {
            message = "Record updated successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return message + "&" + task;
    }

    public String updateStatus(String order_no) {
        int updateRowsAffected = 0;
        String status = "fail";
        String query = " UPDATE order_table SET status_id='13' WHERE order_no='" + order_no + "' ";

        int updateRowsAffected2 = 0;

        try {
            PreparedStatement pstm = connection.prepareStatement(query);

            updateRowsAffected = pstm.executeUpdate();

        } catch (Exception e) {
            System.out.println("CheckOrderInventoryModel updateStatus() Error: " + e);
        }
        if (updateRowsAffected > 0) {
            status = "success";
            message = "Record updated successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return status;
    }

    public String getIndentNo(int indent_table_id) {
        String indent_no = "";

        String query = " SELECT indt.order_no,s.status FROM order_table indt,status s WHERE indt.status_id=s.status_id and "
                + " indt.order_table_id = '" + indent_table_id + "' and indt.active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            indent_no = rset.getString("order_no");

        } catch (Exception e) {
            System.out.println("getIndentNo Error CheckOrderInventoryModel: " + e);
        }

        return indent_no;

    }

    public int getRequestedKeyPersonId(String person_name) {
        String query = " SELECT key_person_id FROM key_person WHERE key_person_name = '" + person_name + "' and active='Y' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("key_person_id");
        } catch (Exception e) {
            System.out.println("getRequestedKeyPersonId Error CheckOrderInventoryModel: " + e);
        }
        return id;
    }

    public int getStatusId(String status) {

        String query = " SELECT status_id FROM status WHERE status = '" + status + "' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("status_id");
        } catch (Exception e) {
            System.out.println("getStatusId Error: CheckOrderInventoryModel " + e);
        }
        return id;
    }

    public int getKeyPersonId(String key_person_name) {

        String query = " SELECT key_person_id FROM key_person WHERE key_person_name = '" + key_person_name + "' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("key_person_id");
        } catch (Exception e) {
            System.out.println("getKeyPersonId Error: CheckOrderInventoryModel" + e);
        }
        return id;
    }

    public List<String> getStatus(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT status from status WHERE status_id in(11,12,10) ";

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
            System.out.println("Error:CheckOrderInventoryModel--getStatus()-- " + e);
        }
        return list;
    }

    public List<String> getRequestedByKeyPerson(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT key_person_name from key_person where active='Y' ";

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
            System.out.println("Error:CheckOrderInventoryModel--getRequestedByKeyPerson()-- " + e);
        }
        return list;
    }

    public List<String> getRequestedToKeyPerson(String q, String requested_by) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT key_person_name from key_person where active='Y' and key_person_name!='" + requested_by + "' ";

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
            System.out.println("Error:CheckOrderInventoryModel--getRequestedToKeyPerson()-- " + e);
        }
        return list;
    }

    public int getCounting() {
        int counting = 100;
        int count = 0;
        String query = " SELECT order_delivery_challan_no FROM order_delivery_challan order by order_delivery_challan_id desc limit 1 ";
        try {
            PreparedStatement psmt = connection.prepareStatement(query);
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                String delivery_challan_no = rs.getString("order_delivery_challan_no");
                String delivery_challan_no_arr[] = delivery_challan_no.split("_");
                int length = (delivery_challan_no_arr.length) - 1;
                count = Integer.parseInt(delivery_challan_no_arr[length]);

                counting = count;
            }
        } catch (Exception ex) {
            System.out.println("ERROR: in getCounting in CheckOrderInventoryModel : " + ex);
        }
        return counting + 1;
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
            System.out.println("CheckOrderInventoryModel closeConnection() Error: " + e);
        }
    }
}
