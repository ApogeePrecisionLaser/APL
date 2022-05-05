package com.apl.order.model;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.lowagie.text.pdf.PdfWriter;
import com.organization.model.OrgOfficeModel;
import com.organization.tableClasses.Org_Office;
import com.organization.tableClasses.OrganisationDesignationBean;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterContext;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporterTagHelper;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.view.save.JRPdfSaveContributor;

/**
 *
 * @author Akash
 */
public class DealerMapModel {

    private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "#a2a220";
    private final String COLOR_ERROR = "red";
    private static List<Org_Office> list1 = new ArrayList<Org_Office>();
    static int count = 0;
    static int prev_parent_id = 0;
    static int off_id = 0;

    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("DealerMapModel setConnection() Error: " + e);
        }
    }

    public List<Org_Office> showData(String org_name, String office_code_search, String office_name_search, String mobile, String generation, String active) {

        PreparedStatement pstmt;
        String query;
        //  list1.clear();
        //  count = 0;
        List<Org_Office> list = new ArrayList<Org_Office>();
        //  int org_id = OrgOfficeModel.getOrgid(searchhierarchy);
        int count = 0;
        int o_id = 0;
        try {
            query = " select distinct kp.key_person_name as d,kp1.key_person_name as s,dealer_salesmanager_table_id,dsm.remark "
                    + " from dealer_salesmanager_mapping as dsm,key_person as kp ,key_person as kp1 where dsm.dealer_id=kp.key_person_id and dsm.salesman_id=kp1.key_person_id and kp.active='Y' and dsm.active='y' ";

            if (!org_name.equals("") && org_name != null) {
                query += " and kp.key_person_name='" + org_name + "' ";
            }

            if (!office_code_search.equals("") && office_code_search != null) {
                query += " and kp1.key_person_name='" + office_code_search + "' ";
            }

            pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                Org_Office organisation = new Org_Office();

                organisation.setDealername(rset.getString("d"));
                organisation.setSalesmanname(rset.getString("s"));
                organisation.setRemark(rset.getString("remark"));
                organisation.setMap_id(rset.getInt("dealer_salesmanager_table_id"));

                list.add(organisation);
            }
        } catch (Exception e) {
            System.out.println("Error:--DealerMapModel--- showData--" + e);
        }
        return list;
    }

    public int insertRecord(int did, int sid, String remark) throws SQLException {
        String is_child = "", active = "";
        int rowsAffected = 0;
        int count = 0;

        String query = " insert into dealer_salesmanager_mapping(dealer_id,salesman_id,remark) "
                + " VALUES( ?, ?, ?) ";
//  int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            //   pstmt.setInt(1, orgOffice.getOrganisation_id());
            pstmt.setInt(1, did);
            pstmt.setInt(2, sid);
            pstmt.setString(3, remark);

            rowsAffected = pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs != null && rs.next()) {
                int key = rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("Error: DealerMapModel---insertRecord" + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;

        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int updateRecordd(int did, int sid, String remark, int org_office_id) throws SQLException {

        int updateRowsAffected = 0;
        int revision = 0;
        boolean status;
        String query1 = " SELECT max(revision_no) as revision_no FROM dealer_salesmanager_mapping WHERE dealer_salesmanager_table_id = " + org_office_id + "  && active='Y' ";
        String query2 = " UPDATE dealer_salesmanager_mapping SET active=? WHERE dealer_salesmanager_table_id=? ";
        String query3 = " insert into dealer_salesmanager_mapping(dealer_id,salesman_id,remark) "
                + " VALUES( ?, ?, ?) ";

        int rowsAffected = 0;
        try {
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement(query1);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                PreparedStatement pstm = connection.prepareStatement(query2);

                pstm.setString(1, "n");
                pstm.setInt(2, org_office_id);

                updateRowsAffected = pstm.executeUpdate();
                if (updateRowsAffected >= 1) {
                    revision = rs.getInt("revision_no") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);

                    psmt.setInt(1, did);
                    psmt.setInt(2, sid);
                    psmt.setString(3, remark);

                    rowsAffected = psmt.executeUpdate();

                    if (rowsAffected > 0) {
                        status = true;
                        message = "Record updated successfully.";
                        msgBgColor = COLOR_OK;
                        connection.commit();
                    } else {
                        status = false;
                        message = "Cannot update the record, some error.";
                        msgBgColor = COLOR_ERROR;
                        connection.rollback();
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("DealerMapModel updateRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record updated successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int insertRecordForSSM(int state_id, int sid, String remark) throws SQLException {
        String is_child = "", active = "";
        int rowsAffected = 0;
        int count = 0;

        String query = " insert into salesmanager_state_mapping(state_id,salesman_id,remark) "
                + " VALUES( ?, ?, ?) ";
//  int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            //   pstmt.setInt(1, orgOffice.getOrganisation_id());
            pstmt.setInt(1, state_id);
            pstmt.setInt(2, sid);
            pstmt.setString(3, remark);

            rowsAffected = pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs != null && rs.next()) {
                int key = rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("Error: DealerMapModel---insertRecord" + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;

        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int updateRecordForSSM(int state_id, int sid, String remark, int salesmanager_state_mapping_id) throws SQLException {

        int updateRowsAffected = 0;
        int revision = 0;
        boolean status;
        String query1 = " SELECT max(revision_no) as revision_no FROM salesmanager_state_mapping WHERE salesmanager_state_mapping_id = " + salesmanager_state_mapping_id + "  && active='Y' ";
        String query2 = " UPDATE salesmanager_state_mapping SET active=? WHERE salesmanager_state_mapping_id=? ";
        String query3 = " insert into salesmanager_state_mapping(state_id,salesman_id,remark) "
                + " VALUES( ?, ?, ?) ";

        int rowsAffected = 0;
        try {
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement(query1);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                PreparedStatement pstm = connection.prepareStatement(query2);

                pstm.setString(1, "n");
                pstm.setInt(2, salesmanager_state_mapping_id);

                updateRowsAffected = pstm.executeUpdate();
                if (updateRowsAffected >= 1) {
                    revision = rs.getInt("revision_no") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);

                    psmt.setInt(1, state_id);
                    psmt.setInt(2, sid);
                    psmt.setString(3, remark);

                    rowsAffected = psmt.executeUpdate();

                    if (rowsAffected > 0) {
                        status = true;
                        message = "Record updated successfully.";
                        msgBgColor = COLOR_OK;
                        connection.commit();
                    } else {
                        status = false;
                        message = "Cannot update the record, some error.";
                        msgBgColor = COLOR_ERROR;
                        connection.rollback();
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("DealerMapModel updateRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record updated successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int deleteRecord(int org_office_id) throws SQLException {

        String query = " DELETE FROM dealer_salesmanager_mapping WHERE dealer_salesmanager_table_id = " + org_office_id;
        int child_item_count = 0;
        int rowsAffected = 0;
        try {

            PreparedStatement pstmt1 = connection.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            pstmt1.executeUpdate();
            rowsAffected = connection.prepareStatement(query).executeUpdate();

        } catch (Exception e) {
            System.out.println("DealerMapModel deleteRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record deleted successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot delete the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        if (child_item_count > 0) {
            message = "Can't delete because it's child items exists!..";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int deleteRecordForSSM(int salesmanager_state_mapping_id) throws SQLException {

        String query = " DELETE FROM salesmanager_state_mapping WHERE salesmanager_state_mapping_id = " + salesmanager_state_mapping_id;
        int child_item_count = 0;
        int rowsAffected = 0;
        try {

            PreparedStatement pstmt1 = connection.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            pstmt1.executeUpdate();
            rowsAffected = connection.prepareStatement(query).executeUpdate();

        } catch (Exception e) {
            System.out.println("DealerMapModel deleteRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record deleted successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot delete the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        if (child_item_count > 0) {
            message = "Can't delete because it's child items exists!..";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public List<String> getDealer(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT distinct key_person_name FROM apl.key_person as kp ,designation as dg where kp.designation_id=dg.designation_id and dg.designation='Owner' and kp.active='Y' ";

        //  System.err.println("query-----------" + query);
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
//                String district_type = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("district_name"));
                String key_person_name = rset.getString("key_person_name");
                if (key_person_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(key_person_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such key_person_name exists.......");
            }
        } catch (Exception e) {
            System.out.println("Error:DealerMapModel--getDealer-- " + e);
        }
        return list;
    }

    public List<String> getState(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT distinct state_name FROM state where active='Y' group by state_name ";

        //  System.err.println("query-----------" + query);
        try {

            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String state_name = rset.getString("state_name");
                if (state_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(state_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such state_name exists.......");
            }
        } catch (Exception e) {
            System.out.println("Error:DealerMapModel--getState-- " + e);
        }
        return list;
    }

    public List<String> getSalesDealer(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT distinct key_person_name FROM apl.key_person as kp ,designation as dg where kp.designation_id=dg.designation_id and dg.designation='Sales' and kp.active='Y' ";

        //  System.err.println("query-----------" + query);
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
//                String district_type = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("district_name"));
                String key_person_name = rset.getString("key_person_name");
                if (key_person_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(key_person_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such key_person_name exists.......");
            }
        } catch (Exception e) {
            System.out.println("Error:DealerMapModel--getSalesDealer-- " + e);
        }
        return list;
    }

    public int getKp_id(String city_name) {
        String query = " SELECT key_person_id FROM key_person WHERE key_person_name = ? and active='Y' ";
        int city_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, city_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            city_id = rset.getInt("key_person_id");
        } catch (Exception e) {
            System.out.println("DealerMapModel getKp_id Error: " + e);
        }
        return city_id;
    }

    public int getStateId(String state) {
        String query = " SELECT state_id FROM state WHERE state_name = ? and active='Y' ";
        int state_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, state);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            state_id = rset.getInt("state_id");
        } catch (Exception e) {
            System.out.println("DealerMapModel getKp_id Error: " + e);
        }
        return state_id;
    }

    public List<Org_Office> showSalesManStateMapData(String searchstate, String sales_seacrh) {

        PreparedStatement pstmt;
        String query;

        List<Org_Office> list = new ArrayList<Org_Office>();
        int count = 0;
        int o_id = 0;
        try {
            query = " select distinct kp.key_person_name as salesman,st.state_name,ssm.salesmanager_state_mapping_id,ssm.remark "
                    + " from salesmanager_state_mapping as ssm,key_person as kp ,state as st "
                    + " where ssm.state_id=st.state_id and ssm.salesman_id=kp.key_person_id and kp.active='Y' and ssm.active='y' ";

            if (!searchstate.equals("") && searchstate != null) {
                query += " and st.state_name='" + searchstate + "' ";
            }

            if (!sales_seacrh.equals("") && sales_seacrh != null) {
                query += " and kp.key_person_name='" + sales_seacrh + "' ";
            }

            pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                Org_Office organisation = new Org_Office();

                organisation.setState_name(rset.getString("state_name"));
                organisation.setSalesmanname(rset.getString("salesman"));
                organisation.setRemark(rset.getString("remark"));
                organisation.setMap_id(rset.getInt("salesmanager_state_mapping_id"));

                list.add(organisation);
            }
        } catch (Exception e) {
            System.out.println("Error:--DealerMapModel--- showData--" + e);
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
            System.out.println("DealerMapModel closeConnection() Error: " + e);
        }
    }
}
