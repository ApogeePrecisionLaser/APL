package com.dashboard.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.DBConnection.DBConnection;
import com.dashboard.bean.DealerItemMap;
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
public class DealerItemMapModel {

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

    public List<DealerItemMap> showData(String org_office_id) {
        List<DealerItemMap> list = new ArrayList<DealerItemMap>();

        String query = "  select m.model,dim.dealer_item_map_id,itn.item_name,oo.org_office_name,dim.description "
                + " from item_authorization ia,designation d,item_names itn,manufacturer_item_map mim,model m,"
                + " manufacturer mr,org_office oo,dealer_item_map dim "
                + " where ia.active='Y' and d.active='Y' and mim.active='Y' and m.active='Y'  and mr.active='Y' and oo.active='Y' and dim.active='Y'  "
                + " and mim.item_names_id=itn.item_names_id and mim.manufacturer_id=mr.manufacturer_id "
                + " and m.manufacturer_item_map_id=mim.manufacturer_item_map_id "
                + " and itn.active='Y' and ia.designation_id=d.designation_id "
                + " and itn.item_names_id=ia.item_names_id and ia.designation_id=8 and dim.item_authorization_id=ia.item_authorization_id "
                + " and dim.org_office_id=oo.org_office_id and dim.model_id=m.model_id ";

        if (!org_office_id.equals("") && org_office_id != null) {
            query += " and oo.org_office_id='" + org_office_id + "' ";
        }

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                DealerItemMap bean = new DealerItemMap();
                bean.setDealer_item_map_id(rset.getInt("dealer_item_map_id"));
                bean.setItem_name((rset.getString("item_name")));
                bean.setModel(rset.getString("model"));
                bean.setOrg_office_name(rset.getString("org_office_name"));
                bean.setDescription(rset.getString("description"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: DealerItemMapModel showdata-" + e);
        }
        return list;
    }

    public List<String> getItemName(String q, String org_office_id) {
        List<String> list = new ArrayList<String>();
        String query = " select itn.item_name from item_authorization ia,designation d,item_names itn "
                + " where ia.active='Y' and d.active='Y' "
                + " and itn.active='Y' and ia.designation_id=d.designation_id "
                + " and itn.item_names_id=ia.item_names_id and ia.designation_id=8 ";

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
            System.out.println("Error:DealerItemMapModel--getItemName()-- " + e);
        }
        return list;
    }

    public List<String> getModel(String q, String item) {
        List<String> list = new ArrayList<String>();
        String query = "  select m.model from item_names itn,manufacturer_item_map mim,model m,manufacturer mr "
                + " where mim.active='Y' and m.active='Y'  and mr.active='Y' "
                + " and mim.item_names_id=itn.item_names_id and mim.manufacturer_id=mr.manufacturer_id "
                + " and m.manufacturer_item_map_id=mim.manufacturer_item_map_id "
                + " and itn.active='Y' ";
        if (!item.equals("") && item != null) {
            query += " and itn.item_name='" + item + "' ";
        }

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String model = (rset.getString("model"));
                if (model.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(model);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such model  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:DealerItemMapModel--getmodel()-- " + e);
        }
        return list;
    }

    public int getItemNamesId(String item_name) {
        String query = "SELECT item_names_id FROM item_names WHERE item_name = '" + item_name + "' and active='Y'  ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("item_names_id");
        } catch (Exception e) {
            System.out.println("DealerItemMapModel getItemNamesId Error: " + e);
        }
        return id;
    }

    public int getModelId(String model) {
        String query = "SELECT model_id FROM model WHERE model = '" + model + "' and active='Y' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("model_id");
        } catch (Exception e) {
            System.out.println("DealerItemMapModel getModelId Error: " + e);
        }
        return id;
    }

    public int getItemAuthorizationId(int item_name_id) {
        String query = " SELECT item_authorization_id FROM item_authorization WHERE item_names_id = '" + item_name_id + "' "
                + " and active='Y' and designation_id=8 ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("item_authorization_id");
        } catch (Exception e) {
            System.out.println("DealerItemMapModel getItemAuthorizationId Error: " + e);
        }
        return id;
    }

    public String getOrgOfficeName(String org_office_id) {
        String query = " SELECT org_office_name FROM org_office WHERE org_office_id = '" + org_office_id + "' "
                + " and active='Y' ";
        String org_office_name = "";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            org_office_name = rset.getString("org_office_name");
        } catch (Exception e) {
            System.out.println("DealerItemMapModel getOrgOfficeName Error: " + e);
        }
        return org_office_name;
    }

    public int insertRecord(DealerItemMap bean, String org_office_id) throws SQLException {
        String query = " INSERT INTO dealer_item_map(item_authorization_id,model_id,org_office_id,description,"
                + " revision_no,active,remark) "
                + " VALUES(?,?,?,?,?,?,?) ";

        int rowsAffected = 0;
        int item_name_id = getItemNamesId(bean.getItem_name());
        int model_id = getModelId(bean.getModel());
        int item_authorization_id = getItemAuthorizationId(item_name_id);
        
        int map_count = 0;
        try {
            String query1 = "SELECT count(*) as count FROM dealer_item_map WHERE "
                    + " item_authorization_id='" + item_authorization_id + "' and model_id='" + model_id + "' and org_office_id='" + org_office_id + "' "
                    + " and active='Y'  ";

            PreparedStatement pstmt1 = connection.prepareStatement(query1);
            ResultSet rs1 = pstmt1.executeQuery();
            while (rs1.next()) {
                map_count = rs1.getInt("count");
            }
            if (map_count > 0) {
                message = "Dealer has already mapped to this item!..";
                msgBgColor = COLOR_ERROR;
            } else {
                PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query);
                psmt.setInt(1, item_authorization_id);
                psmt.setInt(2, model_id);
                psmt.setString(3, org_office_id);
                psmt.setString(4, (bean.getDescription()));
                psmt.setInt(5, 0);
                psmt.setString(6, "Y");
                psmt.setString(7, "OK");

                rowsAffected = psmt.executeUpdate();

            }
        } catch (Exception e) {
            System.out.println("DealerItemMapModel insertRecord() Error: " + e);
        }
        if (rowsAffected
                > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        if (map_count
                > 0) {
            message = "Dealer has already mapped to this item!..";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
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
