package com.inventory.model;

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
import com.inventory.tableClasses.DeliverItem;
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
public class DeliverItemModel {

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

    public List<DeliverItem> showData(String logged_designation) {
        List<DeliverItem> list = new ArrayList<DeliverItem>();
        String query = "";
        if (logged_designation.equals("Store Incharge")) {
            query = " select indt.indent_no,indt.date_time,indt.description,itn.item_name,p.purpose,indi.required_qty,indi.expected_date_time "
                    + " ,s.status,kp1.key_person_name as requested_by,kp2.key_person_name as requested_to,indt.indent_table_id,indi.indent_item_id,indi.approved_qty,indi.deliver_qty "
                    + " from indent_table indt,indent_item indi,key_person kp1,key_person kp2, item_names itn,purpose p, "
                    + " status s,designation d where indt.indent_table_id=indi.indent_table_id  and indt.requested_to=kp2.key_person_id "
                    + " and indt.requested_by=kp1.key_person_id and indi.item_names_id=itn.item_names_id and indi.purpose_id=p.purpose_id "
                    + " and indi.status_id=s.status_id and indt.active='Y' and indi.active='Y' and itn.active='Y' "
                    + " and kp1.active='Y' and kp2.active='Y' and d.active='Y' and indi.status_id in(6,7,9) and d.designation_id='5' ";
        }

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                DeliverItem bean = new DeliverItem();
                bean.setIndent_no(rset.getString("indent_no"));
                bean.setDate_time(rset.getString("date_time"));
                bean.setItem_name((rset.getString("item_name")));
                bean.setPurpose((rset.getString("purpose")));
                bean.setRequired_qty(rset.getInt("required_qty"));
                bean.setApproved_qty(rset.getInt("approved_qty"));
                bean.setDelivered_qty(rset.getInt("deliver_qty"));
                bean.setExpected_date_time(rset.getString("expected_date_time"));
                String status = rset.getString("status");
//                if (status.equals("Request Sent")) {
//                    status = "Request Received";
//                }
                bean.setStatus(status);
                bean.setRequested_by(rset.getString("requested_by"));
                bean.setRequested_to(rset.getString("requested_to"));
                bean.setDescription(rset.getString("description"));
                bean.setIndent_table_id(rset.getInt("indent_table_id"));
                bean.setIndent_item_id(rset.getInt("indent_item_id"));

                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: InventoryModel showdata-" + e);
        }
        return list;
    }

    public String updateRecord(DeliverItem bean, int indent_table_id, int indent_item_id) {
        int updateRowsAffected = 0;
        int status_id = getStatusId(bean.getStatus());

        String query2 = " UPDATE indent_item SET status_id=?,deliver_qty=? WHERE indent_item_id=? ";

        int rowsAffected = 0;
        int map_count = 0;
        try {

            PreparedStatement pstm = connection.prepareStatement(query2);
            pstm.setInt(1, status_id);
            if (bean.getStatus().equals("Delivered")) {
                pstm.setInt(2, bean.getDelivered_qty());
            } else {
                pstm.setInt(2, 0);
            }

            pstm.setInt(3, indent_item_id);
            updateRowsAffected = pstm.executeUpdate();

        } catch (Exception e) {
            System.out.println("ApproveIndentModel updateRecord() Error: " + e);
        }
        if (updateRowsAffected > 0) {
            message = "Record updated successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return message;
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
        String query = "SELECT status from status WHERE status_id in(7,9) ";

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
