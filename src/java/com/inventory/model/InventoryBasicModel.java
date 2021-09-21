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
import com.inventory.tableClasses.InventoryBasic;
import com.inventory.tableClasses.ItemName;
import static com.organization.model.KeypersonModel.getRevisionnoForImage;
import com.organization.tableClasses.KeyPerson;
import java.io.File;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import org.apache.commons.fileupload.FileItem;
import org.json.simple.JSONArray;

/**
 *
 * @author Komal
 */
public class InventoryBasicModel {

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
            System.out.println("InventoryBasicModel setConnection() Error: " + e);
        }
    }

    public List<InventoryBasic> showData(String searchItemName, String searchOrgOffice, String search_item_code) {
        List<InventoryBasic> list = new ArrayList<InventoryBasic>();

        if (searchItemName == null) {
            searchItemName = "";
        }
        if (searchOrgOffice == null) {
            searchOrgOffice = "";
        }
        String query = "select ib.inventory_basic_id,inn.item_name,inn.item_code,oo.org_office_name,ib.min_quantity,ib.daily_req,ib.opening_balance,ib.description "
                + " from item_names inn,org_office oo,inventory_basic ib where inn.item_names_id=ib.item_names_id and "
                + " oo.org_office_id=ib.org_office_id and inn.active='Y' and oo.active='Y' and ib.active='Y' ";

        if (!searchItemName.equals("") && searchItemName != null) {
            query += " and inn.item_name='" + searchItemName + "' ";
        }
        if (!searchOrgOffice.equals("") && searchOrgOffice != null) {
            query += " and oo.org_office_name='" + searchOrgOffice + "' ";
        }
        if (!search_item_code.equals("") && search_item_code != null) {
            query += " and inn.item_code='" + search_item_code + "' ";
        }

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                InventoryBasic bean = new InventoryBasic();
                bean.setInventory_basic_id(rset.getInt("inventory_basic_id"));
                bean.setItem_name((rset.getString("item_name")));
                bean.setItem_code((rset.getString("item_code")));
                bean.setOrg_office(rset.getString("org_office_name"));
                bean.setMin_quantity(rset.getInt("min_quantity"));
                bean.setDaily_req(rset.getInt("daily_req"));
                bean.setOpening_balance(rset.getString("opening_balance"));
                bean.setDescription(rset.getString("description"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: InventoryBasicModel showdata-" + e);
        }
        return list;
    }

    public int insertRecord(InventoryBasic bean) throws SQLException {
        String query = "INSERT INTO inventory_basic(item_names_id,org_office_id,description,"
                + " revision_no,active,remark,min_quantity,daily_req,opening_balance) VALUES(?,?,?,?,?,?,?,?,?) ";

        int rowsAffected = 0;
        int item_name_id = getItemNamesId(bean.getItem_code());
        int org_office_id = getOrgOfficeId(bean.getOrg_office());
        int map_count = 0;
        try {
            String query4 = "SELECT count(*) as count FROM inventory_basic WHERE "
                    + " item_names_id='" + item_name_id + "' and org_office_id='" + org_office_id + "'"
                    + " and active='Y'  ";

            PreparedStatement pstmt1 = connection.prepareStatement(query4);
            ResultSet rs1 = pstmt1.executeQuery();
            while (rs1.next()) {
                map_count = rs1.getInt("count");
            }
            if (map_count > 0) {
                message = "Item has already mapped with this Office!..";
                msgBgColor = COLOR_ERROR;
            } else {
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setInt(1, item_name_id);
                pstmt.setInt(2, org_office_id);
                pstmt.setString(3, bean.getDescription());
                pstmt.setInt(4, bean.getRevision_no());
                pstmt.setString(5, "Y");
                pstmt.setString(6, "OK");
                pstmt.setInt(7, bean.getMin_quantity());
                pstmt.setInt(8, bean.getDaily_req());
                pstmt.setString(9, bean.getOpening_balance());
                rowsAffected = pstmt.executeUpdate();
            }

        } catch (Exception e) {
            System.out.println("InventoryBasicModel insertRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        if (map_count > 0) {
            message = "Item has already mapped with this Office!..";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int updateRecord(InventoryBasic bean, int inventory_basic_id) {
        int revision = InventoryBasicModel.getRevisionno(bean, inventory_basic_id);
        int updateRowsAffected = 0;
        int item_name_id = getItemNamesId(bean.getItem_code());
        int org_office_id = getOrgOfficeId(bean.getOrg_office());
        int map_count = 0;
        String query1 = "SELECT max(revision_no) revision_no FROM inventory_basic WHERE inventory_basic_id = " + inventory_basic_id + "  && active='Y' ";
        String query2 = "UPDATE inventory_basic SET active=? WHERE inventory_basic_id=? and revision_no=? ";
        String query3 = "INSERT INTO inventory_basic(inventory_basic_id,item_names_id,org_office_id,description,"
                + " revision_no,active,remark,min_quantity,daily_req,opening_balance) VALUES(?,?,?,?,?,?,?,?,?,?)";

        int rowsAffected = 0;
        try {
            String query4 = "SELECT count(*) as count FROM inventory_basic WHERE "
                    + " item_names_id='" + item_name_id + "' and org_office_id='" + org_office_id + "'"
                    + " and active='Y'  ";

            PreparedStatement pstmt1 = connection.prepareStatement(query4);
            ResultSet rs1 = pstmt1.executeQuery();
            while (rs1.next()) {
                map_count = rs1.getInt("count");
            }
            if (map_count > 0) {
                message = "Item has already mapped with this Office!..";
                msgBgColor = COLOR_ERROR;
            } else {
                PreparedStatement pstmt = connection.prepareStatement(query1);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    PreparedStatement pstm = connection.prepareStatement(query2);

                    pstm.setString(1, "n");
                    pstm.setInt(2, inventory_basic_id);
                    pstm.setInt(3, revision);
                    updateRowsAffected = pstm.executeUpdate();
                    if (updateRowsAffected >= 1) {
                        revision = rs.getInt("revision_no") + 1;
                        PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                        psmt.setInt(1, (bean.getInventory_basic_id()));
                        psmt.setInt(2, item_name_id);
                        psmt.setInt(3, org_office_id);
                        psmt.setString(4, (bean.getDescription()));
                        psmt.setInt(5, revision);
                        psmt.setString(6, "Y");
                        psmt.setString(7, "OK");
                        psmt.setInt(8, bean.getMin_quantity());
                        psmt.setInt(9, bean.getDaily_req());
                        psmt.setString(10, bean.getOpening_balance());

                        rowsAffected = psmt.executeUpdate();

                    }

                }
            }
        } catch (Exception e) {
            System.out.println("InventoryBasicModel updateRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record updated successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        if (map_count > 0) {
            message = "Item has already mapped with this Office!..";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public static int getRevisionno(InventoryBasic bean, int inventory_basic_id) {
        int revision = 0;
        try {
            String query = " SELECT max(revision_no) as revision_no FROM inventory_basic "
                    + " WHERE inventory_basic_id =" + inventory_basic_id + "  and active='Y' ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
            System.err.println("getRevisionno error:" + e);
        }
        return revision;
    }

    public int deleteRecord(int inventory_basic_id) {
        String query = "DELETE FROM inventory_basic WHERE inventory_basic_id = " + inventory_basic_id;
        int child_item_count = 0;
        int rowsAffected = 0;
        try {
            // PreparedStatement pstmt1 = connection.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            // pstmt1.executeUpdate();
            rowsAffected = connection.prepareStatement(query).executeUpdate();

        } catch (Exception e) {
            System.out.println("InventoryBasicModel deleteRecord() Error: " + e);
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

    public int getItemNamesId(String item_code) {
        String query = "SELECT item_names_id FROM item_names WHERE item_code = '" + item_code + "' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("item_names_id");
        } catch (Exception e) {
            System.out.println("getItemNamesId Error: " + e);
        }
        return id;
    }

    public int getOrgOfficeId(String org_office) {

        String query = "SELECT org_office_id FROM org_office WHERE org_office_name = '" + org_office + "' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("org_office_id");
        } catch (Exception e) {
            System.out.println("getOrgOfficeId Error: " + e);
        }
        return id;
    }

    public String getItemName(int item_name_id) {
        String query = "SELECT item_name FROM item_names WHERE item_names_id = ? and active='Y' ";
        String name = "";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, item_name_id);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            name = rset.getString("item_name");
        } catch (Exception e) {
            System.out.println("getItemName Error: " + e);
        }
        return name;
    }

    public List<String> getItemName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT itn.item_name FROM item_names itn where"
                + " itn.active='Y' and itn.is_super_child='Y'  ";

        query += " group by itn.item_name ORDER BY itn.item_name ";
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
                list.add("No such item_name  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:InventoryBasicModel--getItemName()-- " + e);
        }
        return list;
    }

    public List<String> getItemCode(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT itn.item_code FROM item_names itn where"
                + " itn.active='Y' and itn.is_super_child='Y'  ";

        query += " group by itn.item_code ORDER BY itn.item_code ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String item_code = (rset.getString("item_code"));
                if (item_code.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(item_code);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such item_code  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:InventoryBasicModel--getItemCode()-- " + e);
        }
        return list;
    }

    public List<String> getOrgOffice(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT oo.org_office_name FROM org_office oo where"
                + " oo.active='Y' ";

        query += " group by oo.org_office_name ORDER BY oo.org_office_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String org_office_name = (rset.getString("org_office_name"));
                if (org_office_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(org_office_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such org_office_name  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:InventoryBasicModel--getOrgOffice()-- " + e);
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
