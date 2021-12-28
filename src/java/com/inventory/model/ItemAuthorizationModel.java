package com.inventory.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.DBConnection.DBConnection;
import com.inventory.tableClasses.Inventory;
import com.inventory.tableClasses.ItemAuthorization;
import com.inventory.tableClasses.ItemName;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;

/**
 *
 * @author Komal
 */
public class ItemAuthorizationModel {

    private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "#a2a220";
    private final String COLOR_ERROR = "red";
    int item_id = 0;

    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("ItemAuthorizationModel setConnection() Error: " + e);
        }
    }

    public List<ItemAuthorization> showData(String searchItemName, String search_designation) {
        List<ItemAuthorization> list = new ArrayList<ItemAuthorization>();

        if (searchItemName == null) {
            searchItemName = "";
        }
        if (search_designation == null) {
            search_designation = "";
        }

        String query = "select d.designation,itn.item_name,ia.item_authorization_id,ia.qty,ia.description,ia.monthly_limit from item_names itn,item_authorization ia, "
                + " designation d where itn.active='Y' and ia.active='Y' and d.active='Y' and ia.item_names_id=itn.item_names_id"
                + "  and ia.designation_id=d.designation_id ";

        if (!searchItemName.equals("") && searchItemName != null) {
            query += " and itn.item_name='" + searchItemName + "' ";
        }
        if (!search_designation.equals("") && search_designation != null) {
            query += " and d.designation='" + search_designation + "' ";
        }
        query += " order by d.designation ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                ItemAuthorization bean = new ItemAuthorization();
                bean.setItem_authorization_id(rset.getInt("item_authorization_id"));
                bean.setItem_name((rset.getString("item_name")));
                bean.setDesignation((rset.getString("designation")));
                bean.setQuantity(rset.getInt("qty"));
                bean.setMonthly_limit(rset.getInt("monthly_limit"));
                bean.setDescription(rset.getString("description"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: ItemAuthorizationModel showdata-" + e);
        }
        return list;
    }

    public int insertRecord(ItemAuthorization bean) throws SQLException {
        String query = "INSERT INTO item_authorization(item_names_id,designation_id,description,"
                + " revision_no,active,remark,qty,monthly_limit) "
                + " VALUES(?,?,?,?,?,?,?,?) ";

        int rowsAffected = 0;
        int item_name_id = getItemNamesId(bean.getItem_name());
        int designation_id = getDesignationId(bean.getDesignation());

        String query2 = " select  item_names_id, "
                + " item_name, "
                + " parent_id "
                + "from (select * from item_names where active='Y' "
                + " order by parent_id, item_names_id ) item_names, "
                + " (select @pv := '" + item_name_id + "') initialisation "
                + " where   find_in_set(parent_id, @pv) > 0 "
                + " and  @pv := concat(@pv, ',', item_names_id) ";

        int map_count = 0;
        try {
            PreparedStatement pstmt2 = connection.prepareStatement(query2);
            ResultSet rs2 = pstmt2.executeQuery();
            int item_names_id = 0;
            List<Integer> item_names_id_list = new ArrayList<>();
            while (rs2.next()) {
                item_names_id = rs2.getInt("item_names_id");
                item_names_id_list.add(item_names_id);
            }
            if (item_names_id_list.size() == 0) {
                String query4 = "SELECT count(*) as count FROM item_authorization WHERE "
                        + " item_names_id='" + item_name_id + "' and designation_id='" + designation_id + "'"
                        + " and active='Y'  ";

                PreparedStatement pstmt1 = connection.prepareStatement(query4);
                ResultSet rs1 = pstmt1.executeQuery();
                while (rs1.next()) {
                    map_count = rs1.getInt("count");
                }
                if (map_count > 0) {
                    message = "Designation has already mapped to this item!..";
                    msgBgColor = COLOR_ERROR;
                } else {
                    PreparedStatement pstmt = connection.prepareStatement(query);
                    pstmt.setInt(1, item_name_id);
                    pstmt.setInt(2, designation_id);
                    pstmt.setString(3, bean.getDescription());
                    pstmt.setInt(4, bean.getRevision_no());
                    pstmt.setString(5, "Y");
                    pstmt.setString(6, "OK");
                    pstmt.setInt(7, bean.getQuantity());
                    pstmt.setInt(8, bean.getMonthly_limit());
                    rowsAffected = pstmt.executeUpdate();
                }
            }

            String query5 = " select item_names_id from item_names where item_names_id"
                    + " in(" + item_names_id_list.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ") and is_super_child='Y'"
                    + "  and active='Y' ";

            PreparedStatement pstmt5 = connection.prepareStatement(query5);
            ResultSet rs5 = pstmt5.executeQuery();
            item_names_id_list.clear();
            while (rs5.next()) {
                item_name_id = rs5.getInt("item_names_id");
                item_names_id_list.add(item_name_id);
            }

            for (int j = 0; j < item_names_id_list.size(); j++) {
                item_name_id = item_names_id_list.get(j);
                String query4 = " SELECT count(*) as count FROM item_authorization WHERE "
                        + " item_names_id='" + item_name_id + "' and designation_id='" + designation_id + "' "
                        + " and active='Y'  ";
                PreparedStatement pstmt1 = connection.prepareStatement(query4);
                ResultSet rs1 = pstmt1.executeQuery();
                while (rs1.next()) {
                    map_count = rs1.getInt("count");
                }
                if (map_count > 0) {
                    message = "Designation has already mapped to this item!..";
                    msgBgColor = COLOR_ERROR;
                } else {
                    PreparedStatement pstmt = connection.prepareStatement(query);
                    pstmt.setInt(1, item_name_id);
                    pstmt.setInt(2, designation_id);
                    pstmt.setString(3, bean.getDescription());
                    pstmt.setInt(4, bean.getRevision_no());
                    pstmt.setString(5, "Y");
                    pstmt.setString(6, "OK");
                    pstmt.setInt(7, bean.getQuantity());
                    pstmt.setInt(8, bean.getMonthly_limit());
                    rowsAffected = pstmt.executeUpdate();
                }
            }

        } catch (Exception e) {
            System.out.println("ItemAuthorizationModel insertRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        if (map_count > 0) {
            message = "Designation has already mapped to this item!..";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int updateRecord(ItemAuthorization bean, int item_authorization_id) {
        int revision = ItemAuthorizationModel.getRevisionno(bean, item_authorization_id);
        int updateRowsAffected = 0;
        int item_name_id = getItemNamesId(bean.getItem_name());
        int designation_id = getDesignationId(bean.getDesignation());

        String query1 = "SELECT max(revision_no) revision_no FROM item_authorization WHERE item_authorization_id = " + item_authorization_id + " "
                + " and active='Y' ";
        String query2 = "UPDATE item_authorization SET active=? WHERE item_authorization_id=? and revision_no=? ";
        String query3 = "INSERT INTO item_authorization(item_authorization_id,item_names_id,designation_id,description,"
                + " revision_no,active,remark,qty,monthly_limit) "
                + " VALUES(?,?,?,?,?,?,?,?,?)";

        int rowsAffected = 0;
        int map_count = 0;
        try {
            String query4 = "SELECT count(*) as count FROM item_authorization WHERE "
                    + " item_names_id='" + item_name_id + "' and designation_id='" + designation_id + "'"
                    + " and active='Y'  ";

            PreparedStatement pstmt1 = connection.prepareStatement(query4);
            ResultSet rs1 = pstmt1.executeQuery();
            while (rs1.next()) {
                map_count = rs1.getInt("count");
            }
            if (map_count > 0) {
                message = "Designation has already mapped to this item!..";
                msgBgColor = COLOR_ERROR;
            } else {
                PreparedStatement pstmt = connection.prepareStatement(query1);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    PreparedStatement pstm = connection.prepareStatement(query2);
                    pstm.setString(1, "n");
                    pstm.setInt(2, item_authorization_id);
                    pstm.setInt(3, revision);
                    updateRowsAffected = pstm.executeUpdate();
                    if (updateRowsAffected >= 1) {
                        revision = rs.getInt("revision_no") + 1;
                        PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                        psmt.setInt(1, item_authorization_id);
                        psmt.setInt(2, item_name_id);
                        psmt.setInt(3, designation_id);
                        psmt.setString(4, (bean.getDescription()));
                        psmt.setInt(5, revision);
                        psmt.setString(6, "Y");
                        psmt.setString(7, "OK");
                        psmt.setInt(8, bean.getQuantity());
                        psmt.setInt(9, bean.getMonthly_limit());
                        rowsAffected = psmt.executeUpdate();

                    }

                }
            }
        } catch (Exception e) {
            System.out.println("ItemAuthorizationModel updateRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record updated successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
//        if (map_count > 0) {
//            message = "Item has already assigned to this person!..";
//            msgBgColor = COLOR_ERROR;
//        }
        return rowsAffected;
    }

    public static int getRevisionno(ItemAuthorization bean, int item_authorization_id) {
        int revision = 0;
        try {
            String query = " SELECT max(revision_no) as revision_no FROM item_authorization "
                    + " WHERE item_authorization_id =" + item_authorization_id + "  and active='Y' ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
            System.err.println("ItemAuthorizationModel getRevisionno error:" + e);
        }
        return revision;
    }

    public int deleteRecord(int item_authorization_id) {
        String query = "DELETE FROM item_authorization WHERE item_authorization_id = " + item_authorization_id;
        int rowsAffected = 0;
        try {

            rowsAffected = connection.prepareStatement(query).executeUpdate();

        } catch (Exception e) {
            System.out.println("ItemAuthorizationModel deleteRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record deleted successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot delete the record, some error.";
            msgBgColor = COLOR_ERROR;
        }

        return rowsAffected;
    }

    public int getItemNamesId(String item_name) {
        String query = "SELECT item_names_id FROM item_names WHERE item_name = '" + item_name + "' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("item_names_id");
        } catch (Exception e) {
            System.out.println("ItemAuthorizationModel getItemNamesId Error: " + e);
        }
        return id;
    }

    public int getDesignationId(String designation) {

        String query = "SELECT designation_id FROM designation WHERE designation = '" + designation + "' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("designation_id");
        } catch (Exception e) {
            System.out.println("ItemAuthorizationModel getDesignationId Error: " + e);
        }
        return id;
    }

    public String getItemName(int item_name_id) {
        String query = "SELECT item_name FROM item_names WHERE item_names_id = ? and active='Y'";
        String name = "";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, item_name_id);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            name = rset.getString("item_name");
        } catch (Exception e) {
            System.out.println("ItemAuthorizationModel getItemName Error: " + e);
        }
        return name;
    }

    public List<String> getDesignation(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT designation from designation where active='Y' ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String designation = (rset.getString("designation"));
                if (designation.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(designation);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such designation  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:ItemAuthorizationModel--getDesignation()-- " + e);
        }
        return list;
    }

    public List<String> getItemName(String q) {
        List<String> list = new ArrayList<String>();
//        String query = "SELECT item_name from item_names where active='Y' and is_super_child='Y' ";
        String query = "SELECT item_name from item_names where active='Y' ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String item_name = (rset.getString("item_name"));
                if (item_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(item_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such ItemName  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:ItemAuthorizationModel--getItemName()-- " + e);
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
            System.out.println("ItemAuthorizationModel closeConnection() Error: " + e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
