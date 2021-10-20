package com.inventory.model;

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
 * @author Komal
 */
public class CheckInventoryModel {

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
            System.out.println("CheckInventoryModel setConnection() Error: " + e);
        }
    }

    public List<CheckInventory> showIndents(String logged_designation, String indent_status) {
        List<CheckInventory> list = new ArrayList<CheckInventory>();
        if (indent_status.equals("All")) {
            indent_status = "";
        }
        String query = "";
        if (logged_designation.equals("Store Incharge")) {
            query = " select indt.indent_no,indt.date_time,indt.description "
                    + " ,s.status,kp1.key_person_name as requested_by,kp2.key_person_name as requested_to,indt.indent_table_id "
                    + " from indent_table indt,key_person kp1,key_person kp2,"
                    + " status s,designation d where indt.requested_to=kp2.key_person_id "
                    + " and indt.requested_by=kp1.key_person_id "
                    + " and indt.status_id=s.status_id and indt.active='Y' "
                    + " and kp1.active='Y' and kp2.active='Y' and d.active='Y' and indt.status_id in(6,7,9,3,11) and d.designation_id='5'";
            if (!indent_status.equals("") && indent_status != null) {
                query += " and s.status='" + indent_status + "' ";
            }
        }

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                CheckInventory bean = new CheckInventory();
                bean.setIndent_no(rset.getString("indent_no"));
                bean.setDate_time(rset.getString("date_time"));
                String status = rset.getString("status");
                bean.setStatus(status);
                bean.setRequested_by(rset.getString("requested_by"));
                bean.setRequested_to(rset.getString("requested_to"));
                bean.setDescription(rset.getString("description"));
                bean.setIndent_table_id(rset.getInt("indent_table_id"));
//                bean.setIndent_item_id(rset.getInt("indent_item_id"));

                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: CheckInventoryModel showdata-" + e);
        }
        return list;
    }

    public List<CheckInventory> getStatus() {
        List<CheckInventory> list = new ArrayList<CheckInventory>();

        String query = " select status,status_id from status  order by status";

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
            System.out.println("Error: InventoryModel getStatus-" + e);
        }
        return list;
    }

    public List<CheckInventory> getIndentItems(int indent_table_id, int logged_key_person_id) {
        List<CheckInventory> list = new ArrayList<CheckInventory>();

        String query = " select indt.indent_no,itn.item_name,p.purpose,indi.required_qty,indi.expected_date_time,indi.approved_qty "
                + " ,s1.status as indent_status,s2.status as item_status,indi.indent_item_id,indt.indent_table_id,inv.stock_quantity,indi.deliver_qty,indt.requested_by ,indt.requested_to "
                + " from indent_table indt,indent_item indi, item_names itn,purpose p, "
                + " status s1,status s2,inventory inv,inventory_basic ib where indt.indent_table_id=indi.indent_table_id and indi.item_names_id=itn.item_names_id "
                + " and indi.purpose_id=p.purpose_id and ib.inventory_basic_id=inv.inventory_basic_id and ib.item_names_id=itn.item_names_id and ib.active='Y' "
                + " and inv.active='Y' "
                + " and indt.status_id=s1.status_id and indi.status_id=s2.status_id and indt.active='Y' and indi.active='Y' and itn.active='Y' "
                + " and indt.indent_table_id='" + indent_table_id + "' and inv.key_person_id='" + logged_key_person_id + "' ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                CheckInventory bean = new CheckInventory();
                bean.setIndent_no(rset.getString("indent_no"));
                bean.setItem_name((rset.getString("item_name")));
                bean.setPurpose((rset.getString("purpose")));
                bean.setRequired_qty(rset.getInt("required_qty"));
                bean.setApproved_qty(rset.getInt("approved_qty"));
                bean.setStock_qty(rset.getInt("stock_quantity"));
                bean.setDelivered_qty(rset.getInt("deliver_qty"));
                bean.setExpected_date_time(rset.getString("expected_date_time"));
                String status = rset.getString("indent_status");
                bean.setStatus(status);
                bean.setItem_status(rset.getString("item_status"));
                bean.setIndent_item_id(rset.getInt("indent_item_id"));
                bean.setIndent_table_id(rset.getInt("indent_table_id"));
                bean.setRequested_by(rset.getString("requested_by"));
                bean.setRequested_to(rset.getString("requested_to"));

                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: CheckInventoryModel showdata-" + e);
        }
        return list;
    }

    public List<CheckInventory> getIndentItemsForDeliveryChallan(int indent_table_id) {
        List<CheckInventory> list = new ArrayList<CheckInventory>();

        String query = " select indt.indent_no,itn.item_name,p.purpose,indi.required_qty,indi.expected_date_time,indi.approved_qty "
                + " ,s.status,indi.indent_item_id,indt.indent_table_id,itn.quantity as stock_qty,indi.deliver_qty,indt.requested_by ,indt.requested_to "
                + " from indent_table indt,indent_item indi, item_names itn,purpose p, "
                + " status s where indt.indent_table_id=indi.indent_table_id and indi.item_names_id=itn.item_names_id "
                + " and indi.purpose_id=p.purpose_id "
                + " and indi.status_id=s.status_id and indt.active='Y' and indi.active='Y' and itn.active='Y' "
                + " and indt.indent_table_id='" + indent_table_id + "' and indt.status_id=11 ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                CheckInventory bean = new CheckInventory();
                bean.setIndent_no(rset.getString("indent_no"));
                bean.setItem_name((rset.getString("item_name")));
                bean.setPurpose((rset.getString("purpose")));
                bean.setRequired_qty(rset.getInt("required_qty"));
                bean.setApproved_qty(rset.getInt("approved_qty"));
                bean.setStock_qty(rset.getInt("stock_qty"));
                bean.setDelivered_qty(rset.getInt("deliver_qty"));
                bean.setExpected_date_time(rset.getString("expected_date_time"));
                String status = rset.getString("status");
                bean.setStatus(status);
                bean.setIndent_item_id(rset.getInt("indent_item_id"));
                bean.setIndent_table_id(rset.getInt("indent_table_id"));
                bean.setRequested_by(rset.getString("requested_by"));
                bean.setRequested_to(rset.getString("requested_to"));

                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: CheckInventoryModel showdata-" + e);
        }
        return list;
    }

    public String updateRecord(CheckInventory bean, int indent_item_id, int indent_table_id, String task, String logged_org_office,
            String logged_user_name) {
        int updateRowsAffected = 0;
        if (task.equals("Generate Delivery Challan")) {
            task = "Delivery Challan Generated";
        }

        int indent_status_id = getStatusId(task);
        String item_status = bean.getItem_status();
        if (task.equals("Less Stock")) {
            item_status = "Less Stock";
        }
        if (task.equals("Denied")) {
            item_status = "Denied";
        } else {
            item_status = "Delivery Challan Generated";
        }
        int status_id = getStatusId(item_status);

        String query = " UPDATE indent_item SET status_id=?,deliver_qty=? WHERE indent_item_id=? ";

        int rowsAffected = 0;
        int updateRowsAffected2 = 0;
        int updateRowsAffected3 = 0;
        int updateRowsAffected4 = 0;
        int map_count = 0;
        try {
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setInt(1, status_id);
            if (item_status.equals("Delivery Challan Generated")) {
                pstm.setInt(2, bean.getDelivered_qty());
            } else {
                pstm.setInt(2, 0);
            }

            pstm.setInt(3, indent_item_id);
            updateRowsAffected = pstm.executeUpdate();

            String query2 = " update indent_table set status_id=? where indent_table_id=? ";
            PreparedStatement pstm2 = connection.prepareStatement(query2);
            pstm2.setInt(1, indent_status_id);
            pstm2.setInt(2, indent_table_id);
            updateRowsAffected2 = pstm2.executeUpdate();

        } catch (Exception e) {
            System.out.println("CheckInventoryModel updateRecord() Error: " + e);
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

    public String getIndentNo(int indent_table_id) {
        String indent_no = "";

        String query = "SELECT indt.indent_no,s.status FROM indent_table indt,status s WHERE indt.status_id=s.status_id and"
                + " indt.indent_table_id = '" + indent_table_id + "' and indt.active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            indent_no = rset.getString("indent_no");

        } catch (Exception e) {
            System.out.println("getRequestedByKeyPersonId Error CheckInventoryModel: " + e);
        }

        return indent_no;

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
            System.out.println("getRequestedByKeyPersonId Error CheckInventoryModel: " + e);
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
            System.out.println("getStatusId Error: CheckInventoryModel " + e);
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
            System.out.println("getKeyPersonId Error: CheckInventoryModel" + e);
        }
        return id;
    }

    public List<String> getStatus(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT status from status WHERE status_id in(11,12,10) ";

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
            System.out.println("Error:CheckInventoryModel--getStatus()-- " + e);
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
            System.out.println("Error:CheckInventoryModel--getRequestedByKeyPerson()-- " + e);
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
            System.out.println("Error:CheckInventoryModel--getRequestedByKeyPerson()-- " + e);
        }
        return list;
    }

    public int getCounting() {
        int counting = 100;
        int count = 0;
        String query = " SELECT delivery_challan_no FROM delivery_challan order by delivery_challan_id desc limit 1 ";
        try {
            PreparedStatement psmt = connection.prepareStatement(query);
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                String delivery_challan_no = rs.getString("delivery_challan_no");
                String delivery_challan_no_arr[] = delivery_challan_no.split("_");
                int length = (delivery_challan_no_arr.length) - 1;
                count = Integer.parseInt(delivery_challan_no_arr[length]);

                counting = count;
            }
        } catch (Exception ex) {
            System.out.println("ERROR: in getCounting in CheckInventoryModel : " + ex);
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
            System.out.println("CheckInventoryModel closeConnection() Error: " + e);
        }
    }
}
