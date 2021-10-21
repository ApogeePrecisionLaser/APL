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
import com.inventory.tableClasses.DeliverItem;
import java.io.File;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

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

    public List<DeliverItem> showIndents(String logged_designation) {
        List<DeliverItem> list = new ArrayList<DeliverItem>();
        String query = "";
        if (logged_designation.equals("Store Incharge")) {
            query = " select indt.indent_no,indt.date_time,indt.description "
                    + " ,s.status,kp1.key_person_name as requested_by,kp2.key_person_name as requested_to,indt.indent_table_id "
                    + " from indent_table indt,key_person kp1,key_person kp2,"
                    + " status s,designation d where indt.requested_to=kp2.key_person_id "
                    + " and indt.requested_by=kp1.key_person_id "
                    + " and indt.status_id=s.status_id and indt.active='Y' "
                    + " and kp1.active='Y' and kp2.active='Y' and d.active='Y' and indt.status_id in(6,7,9,3) and d.designation_id='5'";
        }

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                DeliverItem bean = new DeliverItem();
                bean.setIndent_no(rset.getString("indent_no"));
                bean.setDate_time(rset.getString("date_time"));
                String status = rset.getString("status");
                bean.setStatus(status);
                bean.setRequested_by(rset.getString("requested_by"));
                bean.setRequested_to(rset.getString("requested_to"));
                bean.setDescription(rset.getString("description"));
                bean.setIndent_table_id(rset.getInt("indent_table_id"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: InventoryModel showdata-" + e);
        }
        return list;
    }

    public List<DeliverItem> getIndentItems(int indent_table_id) {
        List<DeliverItem> list = new ArrayList<DeliverItem>();

        String query = " select indt.indent_no,itn.item_name,p.purpose,indi.required_qty,indi.expected_date_time,indi.approved_qty "
                + " ,s.status,indi.indent_item_id,indt.indent_table_id,itn.quantity as stock_qty,indi.deliver_qty,indt.requested_by ,indt.requested_to "
                + " from indent_table indt,indent_item indi, item_names itn,purpose p, "
                + " status s where indt.indent_table_id=indi.indent_table_id and indi.item_names_id=itn.item_names_id "
                + " and indi.purpose_id=p.purpose_id "
                + " and indi.status_id=s.status_id and indt.active='Y' and indi.active='Y' and itn.active='Y' "
                + " and indt.indent_table_id='" + indent_table_id + "' ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                DeliverItem bean = new DeliverItem();
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
            System.out.println("Error: InventoryModel showdata-" + e);
        }
        return list;
    }

    public String updateRecord(DeliverItem bean, int indent_item_id, int indent_table_id, String task, String logged_org_office,
            String logged_user_name) {
        int updateRowsAffected = 0;
        if (task.equals("Deliver Items")) {
            task = "Delivered";
        }
        int indent_status_id = getStatusId(task);
        String item_status=bean.getItem_status();
        if(item_status.equals("Delivery Challan Generated")){
            item_status="Delivered";
        }
        int status_id = getStatusId(item_status);

        int rowsAffected = 0;
        int updateRowsAffected2 = 0;
        int updateRowsAffected3 = 0;
        int updateRowsAffected4 = 0;
        int map_count = 0;
        try {
            String query = "INSERT INTO delivery_challan(delivery_challan_no,challan_date,signature_of_sender,"
                    + " transportation_mode,active,remark,vehicle_no,description,revision_no,indent_table_id,indent_item_id,status_id) "
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?) ";

            PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, bean.getDelivery_challan_no());
            pstmt.setString(2, bean.getChallan_date());
            pstmt.setString(3, "");
            pstmt.setString(4, "");
            pstmt.setString(5, "Y");
            pstmt.setString(6, "OK");
            pstmt.setString(7, "");
            pstmt.setString(8, bean.getDescription());
            pstmt.setInt(9, bean.getRevision_no());
            pstmt.setInt(10, bean.getIndent_table_id());
            pstmt.setInt(11, bean.getIndent_item_id());
            pstmt.setInt(12, status_id);
            rowsAffected = pstmt.executeUpdate();

            String query2 = " UPDATE indent_item SET status_id=?,deliver_qty=? WHERE indent_item_id=? ";
            PreparedStatement pstm = connection.prepareStatement(query2);
            pstm.setInt(1, status_id);
            if (bean.getItem_status().equals("Delivered")) {
                pstm.setInt(2, bean.getDelivered_qty());
            } else {
                pstm.setInt(2, 0);
            }
            

            pstm.setInt(3, indent_item_id);
            updateRowsAffected = pstm.executeUpdate();

            String query3 = " update indent_table set status_id=? where indent_table_id=? ";
            PreparedStatement pstm2 = connection.prepareStatement(query3);
            pstm2.setInt(1, indent_status_id);
            pstm2.setInt(2, indent_table_id);
            updateRowsAffected2 = pstm2.executeUpdate();

            String inventory_inward_query1 = " select inv.inventory_id,inv.inward_quantity,inv.stock_quantity from inventory_basic ib,inventory inv,item_names itn,key_person kp,"
                    + " indent_item indi,org_office oo,indent_table indt "
                    + " where indi.item_names_id=itn.item_names_id  and indt.requested_by=kp.key_person_id "
                    + " and indt.indent_table_id=indi.indent_table_id and itn.item_names_id=indi.item_names_id and "
                    + " itn.item_names_id=ib.item_names_id  and ib.org_office_id=oo.org_office_id and ib.inventory_basic_id=inv.inventory_basic_id "
                    + "and kp.key_person_id=inv.key_person_id  and ib.active='Y' and inv.active='Y' and itn.active='Y' and indt.active='Y' "
                    + "and kp.active='Y' and oo.active='Y'  and indi.active='Y' and itn.item_name='" + bean.getItem_name() + "' and oo.org_office_name='" + logged_org_office + "' "
                    + "and kp.key_person_id='" + bean.getRequested_by_id() + "' group by inv.inventory_id ";

            PreparedStatement psmt1 = connection.prepareStatement(inventory_inward_query1);
            ResultSet rs1 = psmt1.executeQuery();
            int inventory_id = 0;
            int inward_quantity=0;
            int stock_quantity=0;
            while (rs1.next()) {
                inventory_id = rs1.getInt("inventory_id");
                inward_quantity=rs1.getInt("inward_quantity");
                stock_quantity=rs1.getInt("stock_quantity");
            }
            if (inventory_id != 0) {
                String update_query = " update inventory set inward_quantity=?,stock_quantity=? where inventory_id=? ";
                PreparedStatement pstm3 = connection.prepareStatement(update_query);
                pstm3.setInt(1, inward_quantity+bean.getDelivered_qty());
                pstm3.setInt(2, stock_quantity+bean.getDelivered_qty());
                pstm3.setInt(3, inventory_id);
                updateRowsAffected3 = pstm3.executeUpdate();

            } else {
                String inventory_inward_query2 = " select ib.inventory_basic_id from inventory_basic ib,inventory inv,item_names itn,key_person kp,"
                        + " indent_item indi,org_office oo,indent_table indt "
                        + " where indi.item_names_id=itn.item_names_id  and indt.requested_by=kp.key_person_id "
                        + " and indt.indent_table_id=indi.indent_table_id and itn.item_names_id=indi.item_names_id and "
                        + " itn.item_names_id=ib.item_names_id  and ib.org_office_id=oo.org_office_id and ib.inventory_basic_id=inv.inventory_basic_id "
                        + " and ib.active='Y' and inv.active='Y' and itn.active='Y' and indt.active='Y' "
                        + "and kp.active='Y' and oo.active='Y'  and indi.active='Y' and itn.item_name='" + bean.getItem_name() + "' and oo.org_office_name='" + logged_org_office + "' "
                        + " group by ib.inventory_basic_id ";

                PreparedStatement psmt = connection.prepareStatement(inventory_inward_query2);
                ResultSet rs = psmt.executeQuery();
                int inventory_basic_id = 0;
                while (rs.next()) {
                    inventory_basic_id = rs.getInt("inventory_basic_id");
                }

                String query_insert = "INSERT INTO inventory(inventory_basic_id,key_person_id,description,"
                        + " revision_no,active,remark,inward_quantity,outward_quantity,date_time,reference_document_type,reference_document_id,stock_quantity) "
                        + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?) ";

                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String cur_date = sdf.format(date);

                PreparedStatement pstmt_ins = connection.prepareStatement(query_insert);
                pstmt_ins.setInt(1, inventory_basic_id);
                pstmt_ins.setInt(2, bean.getRequested_by_id());
                pstmt_ins.setString(3, bean.getDescription());
                pstmt_ins.setInt(4, bean.getRevision_no());
                pstmt_ins.setString(5, "Y");
                pstmt_ins.setString(6, "OK");
                pstmt_ins.setInt(7, bean.getDelivered_qty());
                pstmt_ins.setInt(8, 0);
                pstmt_ins.setString(9, cur_date);
                pstmt_ins.setString(10, "");
                pstmt_ins.setString(11, "");
                pstmt_ins.setInt(12,bean.getDelivered_qty());
                rowsAffected = pstmt_ins.executeUpdate();
            }

            String inventory_outward_query = " select inv.inventory_id,inv.stock_quantity,inv.outward_quantity from inventory_basic ib,inventory inv,item_names itn,key_person kp,"
                    + " indent_item indi,org_office oo,indent_table indt "
                    + " where indi.item_names_id=itn.item_names_id "
                    + " and indt.indent_table_id=indi.indent_table_id and itn.item_names_id=indi.item_names_id and "
                    + " itn.item_names_id=ib.item_names_id  and ib.org_office_id=oo.org_office_id and ib.inventory_basic_id=inv.inventory_basic_id "
                    + "and kp.key_person_id=inv.key_person_id  and ib.active='Y' and inv.active='Y' and itn.active='Y' and indt.active='Y' "
                    + "and kp.active='Y' and oo.active='Y'  and indi.active='Y' and itn.item_name='" + bean.getItem_name() + "' and oo.org_office_name='" + logged_org_office + "' "
                    + "and kp.key_person_name='" + logged_user_name + "' group by inv.inventory_id ";

            PreparedStatement psmt2 = connection.prepareStatement(inventory_outward_query);
            ResultSet rs2 = psmt2.executeQuery();
            int inventory_id2 = 0;
            int outward_quantity=0;
            int stock_quantity2=0;
            while (rs2.next()) {
                inventory_id2 = rs2.getInt("inventory_id");
                outward_quantity = rs2.getInt("outward_quantity");
                stock_quantity2 = rs2.getInt("stock_quantity");
            }

            String query5 = " update inventory set outward_quantity=?,stock_quantity=? where inventory_id=? ";
            PreparedStatement pstm4 = connection.prepareStatement(query5);
            pstm4.setInt(1, outward_quantity+bean.getDelivered_qty());
            pstm4.setInt(2, stock_quantity2-bean.getDelivered_qty());
            pstm4.setInt(3, inventory_id2);
            updateRowsAffected4 = pstm4.executeUpdate();

        } catch (Exception e) {
            System.out.println("DeliverItemModel updateRecord() Error: " + e);
        }
        if (updateRowsAffected4 > 0) {
            message = "Record updated successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return message + "&" + task;
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
        String query = "SELECT status from status WHERE status_id in(7,12,10) ";

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
