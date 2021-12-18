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
 * @author komal
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
        List<Integer> idList = new ArrayList<Integer>();
        idList = getAllParentChildList(office_name_search);
        try {
            query = " select distinct kp.key_person_name as d,kp1.key_person_name as s,dealer_salesmanager_table_id,dsm.remark "
                    + "from dealer_salesmanager_mapping as dsm,key_person as kp ,key_person as kp1 where dsm.dealer_id=kp.key_person_id and dsm.salesman_id=kp1.key_person_id and kp.active='Y' and dsm.active='y'";

            if (!org_name.equals("") && org_name != null) {
                query += " and kp.key_person_name='" + org_name + "' ";
            }

            if (!office_code_search.equals("") && office_code_search != null) {
                query += " and kp1.key_person_name='" + office_code_search + "' ";
            }
////            if (!office_name_search.equals("") && office_name_search != null) {
////                query += " and o.org_office_name='" + office_name_search + "' ";
////            }
//            if (!generation.equals("") && generation != null) {
//                query += " and o.generation='" + generation + "' ";
//            }
//
//            if (!office_name_search.equals("")) {
//                query += "and o.org_office_id in(" + idList.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ") ";
//            }
//            if (!office_name_search.equals("")) {
//                query += " order by field(o.org_office_id," + idList.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ") ";
//
//            } else {
//                query += "order by generation ";
//
//            }   

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

    public List<Integer> getAllParentChildList(String office_name_search) {
        PreparedStatement pstmt;
        String query = "";
        List<Integer> list = new ArrayList<Integer>();

        if (office_name_search == null) {
            office_name_search = "";
        }
        int org_office_id = 0, parent_id = 0;

        String qry = "select org_office_id from org_office where active='Y' and org_office_name='" + office_name_search + "' ";
        try {
            PreparedStatement pst = connection.prepareStatement(qry);
            ResultSet rstt = pst.executeQuery();
            while (rstt.next()) {
                org_office_id = rstt.getInt(1);
                list.add(org_office_id);
            }
        } catch (Exception e) {
            System.out.println("DealerMapModel.getAllParentChildList() -" + e);
        }

        String qry1 = "select org_office_id from org_office where active='Y' and parent_org_office_id='" + org_office_id + "' limit 1 ";
        try {
            PreparedStatement pst = connection.prepareStatement(qry1);
            ResultSet rstt = pst.executeQuery();
            while (rstt.next()) {
                parent_id = rstt.getInt(1);
                list.add(parent_id);
            }
        } catch (Exception e) {
            System.out.println("DealerMapModel.getAllParentChildList() -" + e);
        }

        try {
            query = "SELECT distinct t2.org_office_id as lev2, t3.org_office_id as lev3, "
                    + " t4.org_office_id as lev4,t5.org_office_id as lev5,t6.org_office_id as lev6, "
                    + " t7.org_office_id as lev7,t8.org_office_id as lev8,t9.org_office_id as lev9,t10.org_office_id as lev10 "
                    + " FROM org_office AS t1 "
                    + " LEFT JOIN org_office AS t2 ON t2.parent_org_office_id = t1.org_office_id and t2.active='Y' "
                    + " LEFT JOIN org_office AS t3 ON t3.parent_org_office_id = t2.org_office_id and t3.active='Y' "
                    + " LEFT JOIN org_office AS t4 ON t4.parent_org_office_id = t3.org_office_id and t4.active='Y' "
                    + " LEFT JOIN org_office AS t5 ON t5.parent_org_office_id = t4.org_office_id and t5.active='Y' "
                    + " LEFT JOIN org_office AS t6 ON t6.parent_org_office_id = t5.org_office_id and t6.active='Y' "
                    + " LEFT JOIN org_office AS t7 ON t7.parent_org_office_id = t6.org_office_id and t7.active='Y' "
                    + " LEFT JOIN org_office AS t8 ON t8.parent_org_office_id = t7.org_office_id and t8.active='Y' "
                    + " LEFT JOIN org_office AS t9 ON t9.parent_org_office_id = t8.org_office_id and t9.active='Y' "
                    + " LEFT JOIN org_office AS t10 ON t10.parent_org_office_id = t9.org_office_id and t10.active='Y' "
                    + "  WHERE '" + org_office_id + "' in (t1.parent_org_office_id,t2.parent_org_office_id,t3.parent_org_office_id) ";

            pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                list.add(rset.getInt(1));
                list.add(rset.getInt(2));
                list.add(rset.getInt(3));
                list.add(rset.getInt(4));
                list.add(rset.getInt(5));
                list.add(rset.getInt(6));
                list.add(rset.getInt(7));
                list.add(rset.getInt(8));
                list.add(rset.getInt(9));

            }
        } catch (Exception e) {
            System.out.println("Error:--DealerMapModel--- getAllParentChildList--" + e);
        }
        String qry2 = "select org_office_id from org_office where active='Y' and parent_org_office_id='" + org_office_id + "' ";
        try {
            PreparedStatement pst = connection.prepareStatement(qry2);
            ResultSet rstt = pst.executeQuery();
            while (rstt.next()) {
                list.add(rstt.getInt(1));
            }
        } catch (Exception e) {
            System.out.println("DealerMapModel.getAllParentChildList() -" + e);
        }

        list.removeAll(Arrays.asList(0));

        return list;
    }

    public static int getOrgid(String org_id) {
        String query = " SELECT org_office_id FROM org_office WHERE  active='Y' ";

        if (!org_id.equals("") && org_id != null) {
            query += " and org_office_name='" + org_id + "' ";
        }
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("org_office_id");
        } catch (Exception e) {
            System.out.println("DealerMapModel Error: getOrgid--" + e);
        }
        return organisation_id;
    }

    public static int getParentOrgid(int org_id) {
        String query = "SELECT parent_org_office_id FROM org_office WHERE  org_office_id=? and active=? ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("parent_org_office_id");
        } catch (Exception e) {
            System.out.println("DealerMapModel Error: getParentOrgid--" + e);
        }
        return organisation_id;
    }

    public static int getcheckorgid(int org_id) {
        String query = "SELECT org_office_id FROM org_office WHERE  parent_org_office_id=? and active=? ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setInt(1, org_id);
//
//            pstmt.setString(2, "Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("org_office_id");
        } catch (Exception e) {
            System.out.println("DealerMapModel Error: getcheckorgid--" + e);
        }
        return organisation_id;
    }

    public static String Orgofficename(String number) {
        String id = "";
        //String query1 = "SELECT org_office_name FROM org_office WHERE org_office_id =?  && active=? ";
        String query1 = " SELECT org_office_name FROM org_office WHERE  active='Y' ";

        if (!number.equals("") && number != null) {
            query1 += " and org_office_id='" + number + "' ";
        }
        try {

            PreparedStatement pstmt = connection.prepareStatement(query1);
//            pstmt.setString(1, number);
//            pstmt.setString(2, "Y");

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                id = rset.getString("org_office_name");

            }

        } catch (Exception ex) {
            System.err.println("DealerMapModel Orgofficename error---------" + ex);
        }

        return id;
    }

    public int getIdOfParent(String number) {
        int id = 0;
        String query1 = "SELECT org_office_id FROM org_office WHERE serial_no =?  && active=? ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
//            pstmt.setString(1, number);
//            pstmt.setString(2, "Y");

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                id = rset.getInt("org_office_id");

            }

        } catch (Exception ex) {
            System.err.println("DealerMapModel getIdOfParent error---------" + ex);
        }

        return id;
    }

    public int getOrg_Office_id(String organisation_name) {
        String query = "SELECT org_office_id FROM org_office WHERE org_office_name = '" + organisation_name + "' and active='Y' ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setString(1, organisation_name);
//            pstmt.setString(2, "Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("org_office_id");
        } catch (Exception e) {
            System.out.println("DealerMapModel Error: getOrg_Office_id--" + e);
        }
        return organisation_id;
    }

    public int getParentGeneration(int org_id, int desig_id) {
        String query = "SELECT * FROM org_office WHERE organisation_id = ? and org_office_id=? and active=? ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setInt(1, org_id);
//            pstmt.setInt(2, desig_id);
//            pstmt.setString(3, "Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("generation");
        } catch (Exception e) {
            System.out.println("DealerMapModel Error: getParentGeneration--" + e);
        }
        return organisation_id;
    }

    public int insertRecord(int did, int sid, String remark) throws SQLException {
        String is_child = "", active = "";
        int rowsAffected = 0;
        int count = 0;

        String query = "insert into dealer_salesmanager_mapping(dealer_id,salesman_id,remark) "
                + "VALUES( ?, ?, ?)";
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

    public int getOrgofficeid(String organisation_name) {
        String query = "SELECT org_office_id FROM org_office WHERE org_office_name = ? and active=? ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, organisation_name);
            pstmt.setString(2, "Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("org_office_id");
        } catch (Exception e) {
            System.out.println("DealerMapModel Error: getOrgofficeid--" + e);
        }
        return organisation_id;
    }

    public int updateRecordd(int did, int sid, String remark, int org_office_id) throws SQLException {

        int updateRowsAffected = 0;
        int revision = 0;
        boolean status;
        String query1 = "SELECT max(revision_no) as revision_no FROM dealer_salesmanager_mapping WHERE dealer_salesmanager_table_id = " + org_office_id + "  && active='Y' ";
        String query2 = "UPDATE dealer_salesmanager_mapping SET active=? WHERE dealer_salesmanager_table_id=? ";
        String query3 = "insert into dealer_salesmanager_mapping(dealer_id,salesman_id,remark) "
                + "VALUES( ?, ?, ?)";

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

    public List<String> getMobilevalidty(String q) {
        List<String> list = new ArrayList<String>();
//        String query = "SELECT city_name FROM city AS c ,state AS s WHERE c.state_id=s.state_id AND s.state_name=? "
//                + "  ORDER BY city_name";
        String AdvertiseName = "";
        String query = "SELECT mobile_no1 FROM org_office where org_office.active='Y' and mobile_no1='" + q + "'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //pstmt.setString(1, krutiToUnicode.convert_to_unicode(state_name));

            ResultSet rset = pstmt.executeQuery();
            int count = 0;

            while (rset.next()) {    // move cursor from BOR to valid record.
                AdvertiseName = (rset.getString("mobile_no1"));

                count++;

            }
            if (count >= 1) {
                list.add("Mobile no exist");
            } else {
                list.add(q);
            }

        } catch (Exception e) {
            System.out.println("DealerMapModel Error: getMobilevalidty-" + e);
        }
        return list;
    }

    public static int getRevisionno(Org_Office orgOffice, int org_office_id) {
        int revision = 0;
        try {

            String query = " SELECT max(revision_no) as revision_no FROM org_office WHERE org_office_id =" + org_office_id + "  && active='Y' ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
            System.err.println("DealerMapModel getRevisionno error------------" + e);
        }
        return revision;
    }

    public int deleteRecord(int org_office_id) throws SQLException {

        String query = "DELETE FROM dealer_salesmanager_mapping WHERE dealer_salesmanager_table_id = " + org_office_id;
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

    public String getPointLatLong(String person_id) {
        String lat = "";
        String longi = "";
        String query = " select latitude,longitude "
                + " from org_office "
                + " where org_office_id=? ";

        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, person_id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                lat = rs.getString("latitude");
                longi = rs.getString("longitude");

            }
        } catch (Exception e) {
            System.out.println("DealerMapModel getPointLatLong error-------" + e);
        }

        return lat + "," + longi;
    }

    public List<String> organisation_Name(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT  org.organisation_name FROM organisation_name  AS org where org.active='Y' ORDER BY organisation_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String organisation_name = (rset.getString("organisation_name"));
                if (organisation_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(organisation_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Organisation Type exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:DealerMapModel--organisation_Name()-- " + e);
        }
        return list;
    }

    public List<String> getHierarchsearch(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT distinct  org_office_name FROM org_office o where o.active = 'Y' ORDER BY org_office_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String org_type_name = (rset.getString("org_office_name"));
                if (org_type_name.toUpperCase().startsWith(q.toUpperCase())) {

                    list.add(org_type_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Organisation Type exists.");
            }
        } catch (Exception e) {
            System.out.println("DealerMapModel getHierarchsearch ERROR - " + e);
        }
        return list;
    }

    public List<String> searchMobile(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT distinct mobile_no1 FROM org_office  AS org where org.active='Y' ORDER BY mobile_no1 ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String organisation_name = (rset.getString("mobile_no1"));
                if (organisation_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(organisation_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Organisation Type exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:DealerMapModel--searchMobile()-- " + e);
        }
        return list;
    }

    public List<String> getOrganisation_Name(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT distinct org.organisation_name FROM organisation_name  AS org,org_office as oft where org.organisation_id=oft.organisation_id and org.active='Y' and oft.active='Y' ORDER BY organisation_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String organisation_name = (rset.getString("organisation_name"));
                if (organisation_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(organisation_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Organisation Type exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:DealerMapModel--getOrganisation_Name()-- " + e);
        }
        return list;
    }

    public List<String> getGeneration(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT distinct org.generation FROM org_office  AS org   where org.active='Y'  ORDER BY generation ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String organisation_name = (rset.getString("generation"));
                if (organisation_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(organisation_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Generation exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:DealerMapModel--getGeneration()-- " + e);
        }
        return list;
    }

    public int getOrganisation_id(String organisation_name) {
        String query = "SELECT organisation_id FROM organisation_name WHERE organisation_name = ? and active=? ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, organisation_name);
            pstmt.setString(2, "Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("organisation_id");

        } catch (Exception e) {
            System.out.println("DealerMapModel Error: getOrganisation_id--" + e);
        }
        return organisation_id;
    }

    public int getOfficetype_id(String organisation_name) {
        String query = "SELECT office_type_id FROM org_office_type WHERE office_type = ? and active=? ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, organisation_name);
            pstmt.setString(2, "Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("office_type_id");
        } catch (Exception e) {
            System.out.println("DealerMapModel Error: getOfficetype_id--" + e);
        }
        return organisation_id;
    }

    public List<String> OrgOfficeType(String q) {
        List<String> list = new ArrayList<String>();
        //String query = "SELECT org.office_type FROM org_office_type AS org WHERE org.org_office_code = ? ORDER BY office_type ";
        String query = "SELECT office_type from org_office_type where org_office_type.active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1, office_code);

            int count = 0;
            q = q.trim();
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String office_type = (rset.getString("office_type"));
                if (office_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(office_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such Office Name Exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:DealerMapModel--OrgOfficeType-- " + e);
        }
        return list;
    }

    public List<String> getMobile(String q) {
        List<String> list = new ArrayList<String>();
        //String query = "SELECT org.office_type FROM org_office_type AS org WHERE org.org_office_code = ? ORDER BY office_type ";
        String query = "SELECT mobile_no1 from org_office where active='Y' group by mobile_no1";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1, office_code);

            int count = 0;
            q = q.trim();
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String mobile_no1 = (rset.getString("mobile_no1"));
                if (mobile_no1.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(mobile_no1);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such Office Name Exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:DealerMapModel--getMobile-- " + e);
        }
        return list;
    }

    public List<String> gethierarchysearch(String q) {
        List<String> list = new ArrayList<String>();
        //String query = "SELECT org.office_type FROM org_office_type AS org WHERE org.org_office_code = ? ORDER BY office_type ";
        String query = "SELECT mobile_no1 from org_office where active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1, office_code);

            int count = 0;
            q = q.trim();
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String mobile_no1 = (rset.getString("mobile_no1"));
                if (mobile_no1.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(mobile_no1);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such Office Name Exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:DealerMapModel--gethierarchysearch-- " + e);
        }
        return list;
    }

    public int getDesgn_id(String office) {
        String query = "SELECT organisation_id FROM organisation_name WHERE organisation_name = ?";
        int city_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, office);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            city_id = rset.getInt("organisation_id");
        } catch (Exception e) {
            System.out.println("DealerMapModel getDesgn_id Error: " + e);
        }
        return city_id;
    }

    public List<String> getParentOrgOffice(String q, String code, String edit, String generationS, String currdesig) {
        int id = getDesgn_id(code);
        // int id1 = getDesgn_idd(id);
        List<String> list = new ArrayList<String>();
//        String query = " SELECT designation FROM designation,organisation_designation_map where designation.designation_id=organisation_designation_map.designation_id and "
//                + " organisation_designation_map.organisation_id='" + id + "' and "
//                + " organisation_designation_map.active='Y' and designation.active='Y'";

        String query = "SELECT * FROM org_office d where "
                + "   d.organisation_id='" + id + "' and  d.active='Y'  and d.super='N' ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            //  System.out.println("get paernt -" + query);
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String state_name = (rset.getString("org_office_name"));
                if (edit.equals("true")) {
                    int generation = (rset.getInt("generation"));
                    int generationP = Integer.parseInt(generationS);
                    if ((state_name.toUpperCase().startsWith(q.toUpperCase())) && (generationP >= generation)) {
                        if (!(state_name.equals(currdesig))) {
                            list.add(state_name);
                        }
                        count++;
                    }

                } else {
                    if (state_name.toUpperCase().startsWith(q.toUpperCase())) {
                        list.add(state_name);
                        count++;
                    }

                }

            }
            if (count == 0) {
                list.add("No such Designation exists.");
            }
        } catch (Exception e) {
            System.out.println("DealerMapModel getParentOrgOffice Error: " + e);
        }
        return list;
    }

    public List<String> getOrgOfficeNameSearch(String q, String org_name, String code) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT distinct o.org_office_name FROM org_office AS o ,organisation_name as om "
                + " WHERE IF('" + org_name + "'='', om.organisation_name LIKE '%%' , om.organisation_name='" + org_name + "')  and o.organisation_id=om.organisation_id "
                + " AND if('" + code + "' = '' , o.org_office_code like '%%' , o.org_office_code = ? ) and o.active='Y' and om.active='Y' "
                + "ORDER BY o.org_office_name";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setString(1, code);

            int count = 0;
            q = q.trim();
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String org_office = (rset.getString("org_office_name"));
                if (org_office.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(org_office);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such Office Name Exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:DealerMapModel--getOrgOfficeNameSearch-- " + e);
        }
        return list;
    }

    public List<String> getOrgOfficeCodeSearch(String q, String code) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT oo.org_office_code FROM org_office as oo,organisation_name onn where oo.active='Y' and onn.active='Y' "
                + "and oo.organisation_id=onn.organisation_id and onn.organisation_name='" + code + "'";

        //  System.err.println("query-----------" + query);
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            int count = 0;
            q = q.trim();
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String org_office_code = rset.getString("org_office_code");
                if (org_office_code.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(org_office_code);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such Office Code Exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:DealerMapModel--getOrgOfficeCodeSearch-- " + e);
        }
        return list;
    }

    public List<String> getDealer() {
        List<String> list = new ArrayList<String>();
        String query = "SELECT distinct key_person_name FROM apl.key_person as kp ,designation as dg where kp.designation_id=dg.designation_id and dg.designation='Owner' and kp.active='Y'";

        //  System.err.println("query-----------" + query);
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            int count = 0;

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String org_office_code = rset.getString("key_person_name");

                list.add(org_office_code);
                count++;

            }
            if (count == 0) {
                list.add("No Such Office Code Exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:DealerMapModel--getDealer-- " + e);
        }
        return list;
    }

    public List<String> getSalesDealer() {
        List<String> list = new ArrayList<String>();
        String query = "SELECT distinct key_person_name FROM apl.key_person as kp ,designation as dg where kp.designation_id=dg.designation_id and dg.designation='Sales' and kp.active='Y'";

        //  System.err.println("query-----------" + query);
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            int count = 0;

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String org_office_code = rset.getString("key_person_name");

                list.add(org_office_code);
                count++;

            }
            if (count == 0) {
                list.add("No Such Office Code Exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:DealerMapModel--getSalesDealer-- " + e);
        }
        return list;
    }

    public int getOrgOfficeType_id(String office_type) {
        String query = "SELECT office_type_id FROM org_office_type WHERE  office_type = ?  and active=? ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //  pstmt.setString(1, office_code);
            pstmt.setString(1, office_type);
            pstmt.setString(2, "Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("office_type_id");
        } catch (Exception e) {
            System.out.println("DealerMapModel Error: getOrgOfficeType_id--" + e);
        }
        return organisation_id;
    }

    public int getCity_id(String city_name) {
        String query = "SELECT city_id FROM city WHERE city_name = ?";
        int city_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, city_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            city_id = rset.getInt("city_id");
        } catch (Exception e) {
            System.out.println("DealerMapModel getCity_id Error: " + e);
        }
        return city_id;
    }

    public int getKp_id(String city_name) {
        String query = "SELECT key_person_id FROM key_person WHERE key_person_name = ? and active='Y'";
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

    public List<String> getCityName(String q) {
        List<String> list = new ArrayList<String>();
//        String query = "SELECT city_name FROM city AS c ,state AS s WHERE c.state_id=s.state_id AND s.state_name=? "
//                + "  ORDER BY city_name";
        String query = "SELECT city_name FROM city AS c where c.active='Y' ORDER BY city_name ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //    pstmt.setString(1, krutiToUnicode.convert_to_unicode(state_name));

            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String AdvertiseName = (rset.getString("city_name"));
                if (AdvertiseName.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(AdvertiseName);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such City Name exists.");
            }
        } catch (Exception e) {
            System.out.println("DealerMapModel getCityName Error: " + e);
        }
        return list;
    }

    public List<String> getStateName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT state_name FROM state ORDER BY state_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String state_name = rset.getString("state_name");
                if (state_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(state_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such State Name exists.");
            }
        } catch (Exception e) {
            System.out.println("DealerMapModel getStateName Error: " + e);
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
