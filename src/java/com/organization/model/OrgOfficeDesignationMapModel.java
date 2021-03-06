/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.organization.model;

import com.organization.tableClasses.Org_Office;
import com.organization.tableClasses.OrganisationDesignationBean;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

/**
 *
 * @author komal
 */
public class OrgOfficeDesignationMapModel {

    private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "#a2a220";
    private final String COLOR_ERROR = "red";
    private static List<OrganisationDesignationBean> list1 = new ArrayList<OrganisationDesignationBean>();
    static int count = 0;
    static int prev_parent_id = 0;
    static int off_id = 0;

    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("OrgOfficeModel setConnection() Error: " + e);
        }
    }

    public int getNoOfRows() {
        int noOfRows = 0;
        try {
            ResultSet rset = connection.prepareStatement("select count(*) from org_office").executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return noOfRows;
    }

    public static int getDesgnid(String org_id) {
        String query = "SELECT designation_id FROM designation WHERE  designation=? and active=? ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, org_id);

            pstmt.setString(2, "Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("designation_id");
        } catch (Exception e) {
            System.out.println("Error: OrganisationMapModel--" + e);
        }
        return organisation_id;
    }

    public static int getParentOrgid(int org_id) {
        String query = "SELECT organisation_designation_map_id_2 FROM organisation_designation WHERE  organisation_designation_map_id_1=? and active=? ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, org_id);

            pstmt.setString(2, "Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("organisation_designation_map_id_2");
        } catch (Exception e) {
            System.out.println("Error: OrganisationMapModel--" + e);
        }
        return organisation_id;
    }

    public static int getcheckorgid(int org_id) {
        String query = "SELECT organisation_designation_map_id_1 FROM organisation_designation WHERE  organisation_designation_map_id_2=? and active=? ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, org_id);

            pstmt.setString(2, "Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("organisation_designation_map_id_1");
        } catch (Exception e) {
            System.out.println("Error: OrganisationMapModel--" + e);
        }
        return organisation_id;
    }

    public List<OrganisationDesignationBean> showData(String searchOrgOffice, String designation) {
        PreparedStatement pstmt = null;
        String query;
        List<OrganisationDesignationBean> list = new ArrayList<OrganisationDesignationBean>();

        try {
            query = " select oodm.org_office_designation_map_id,oo.org_office_name,d.designation,oodm.serial_no from "
                    + " org_office_designation_map oodm,org_office oo,designation d "
                    + "where oodm.org_office_id=oo.org_office_id and oodm.designation_id = d.designation_id and oodm.active = 'Y' and oo.active='Y' and d.active='Y' ";

            if (!searchOrgOffice.equals("") && searchOrgOffice != null) {
                query += " and oo.org_office_name='" + searchOrgOffice + "' ";
            }
            if (!designation.equals("") && designation != null) {
                query += " and d.designation='" + designation + "' ";
            }

            pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                int id = rset.getInt("org_office_designation_map_id");
                String desig = rset.getString("designation");
                String org_office_name = rset.getString("org_office_name");
                String serial_no = rset.getString("serial_no");

                OrganisationDesignationBean organisation = new OrganisationDesignationBean();
                organisation.setId(id);
                organisation.setOrganisation(org_office_name);
                organisation.setDesignation(desig);
                organisation.setSerialnumber(serial_no);
                list.add(organisation);
            }
        } catch (Exception e) {
            System.out.println("Error:--organisation--- showData--" + e);
        }
        // System.err.println("list--------" + list.size());
        return list;
    }

    public int getIdOfParent(String number) {
        int id = 0;
        String query1 = "SELECT organisation_designation_map_id FROM organisation_designation_map WHERE serial_no =?  && active=? ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
            pstmt.setString(1, number);
            pstmt.setString(2, "Y");

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                id = rset.getInt("organisation_designation_map_id");

            }

        } catch (Exception ex) {

        }

        return id;
    }

    public String getDesigNameFromId(int id) {
        String name = "";
        String query1 = "SELECT designation FROM designation WHERE designation_id =?  && active=? ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
            pstmt.setInt(1, id);
            pstmt.setString(2, "Y");

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                name = rset.getString("designation");

            }

        } catch (Exception ex) {

        }

        return name;
    }

    public String getOrgNameFromId(int id) {
        String name = "";
        String query1 = "SELECT organisation_name FROM organisation_name WHERE organisation_id =?  && active=? ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
            pstmt.setInt(1, id);
            pstmt.setString(2, "Y");

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                name = rset.getString("organisation_name");

            }

        } catch (Exception ex) {

        }

        return name;
    }

    // edited by Vikrant
    public int insertRecord(OrganisationDesignationBean bean) {

        String is_child = "", active = "";
        int rowsAffected = 0;
        int count = 0;

        int orgid = getOrganisationOfficeId(bean.getOrganisation());
        int desigid = getDesignation_id(bean.getDesignation());

        String query = "insert into org_office_designation_map(org_office_id,designation_id,"
                + " active,revision,remark,created_by,serial_no,created_at) "
                + " values (?,?,?,?,?,?,?,now()) ";

        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, orgid);
            pstmt.setInt(2, desigid);
            pstmt.setString(3, "Y");
            pstmt.setString(4, "0");
            pstmt.setString(5, bean.getRemark());
            pstmt.setString(6, "Komal");
            pstmt.setString(7, bean.getSerialnumber());

            // System.out.println("insert query -" + pstmt);
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error while inserting record in insertRecord...." + e);
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

    public int updateRecord(OrganisationDesignationBean bean, int org_office_designation_map_id) throws SQLException {
        int revision = OrgOfficeDesignationMapModel.getRevisionno(bean, org_office_designation_map_id);
        int rowsAffected = 0;
        int count = 0;
        int updateRowsAffected = 0;
        Boolean status = false;

        int org_office_id = getOrganisationOfficeId(bean.getOrganisation());
        int desigid = getDesignation_id(bean.getDesignation());

        String query1 = "SELECT max(revision) FROM org_office_designation_map WHERE "
                + "org_office_designation_map_id = " + org_office_designation_map_id + " and active='Y' ";

        String query2 = "UPDATE org_office_designation_map SET active=? WHERE org_office_designation_map_id=? and revision=? ";

        String query3 = "insert into org_office_designation_map(org_office_id,designation_id,"
                + " active,revision,remark,created_by,serial_no,created_at) "
                + " values (?,?,?,?,?,?,?,now()) ";

        try {
            connection.setAutoCommit(false);

            PreparedStatement pstmt = connection.prepareStatement(query1);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                revision = rs.getInt("max(revision)");

                PreparedStatement pstm = connection.prepareStatement(query2);
                pstm.setString(1, "N");

                pstm.setInt(2, org_office_designation_map_id);
                pstm.setInt(3, revision);
                updateRowsAffected = pstm.executeUpdate();
                if (updateRowsAffected >= 1) {
                    revision = rs.getInt("max(revision)") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);

                    psmt.setInt(1, org_office_id);
                    psmt.setInt(2, desigid);
                    psmt.setString(3, "Y");
                    psmt.setInt(4, revision);
                    psmt.setString(5, bean.getRemark());
                    psmt.setString(6, "Komal");
                    psmt.setString(7, bean.getSerialnumber());

                    //   System.out.println("insert query -" + psmt);
                    rowsAffected = psmt.executeUpdate();
                    if (rowsAffected > 0) {
                        status = true;
                        message = "Record updated successfully.";
                        msgBgColor = COLOR_OK;
                        connection.commit();
                        //delete record
                    } else {
                        status = false;
                        message = "Cannot update the record, some error.";
                        msgBgColor = COLOR_ERROR;
                        connection.rollback();
                    }
                }

            } else {
                message = "Key already mapped with some child!";
                msgBgColor = COLOR_ERROR;
            }

        } catch (Exception e) {
            System.out.println("Error: updateRecord---updateRecord" + e);
        } finally {

        }
        return rowsAffected;
    }

    public static int getRevisionno(OrganisationDesignationBean orgOffice, int org_office_designation_map_id) {
        int revision = 0;
        try {

            String query = " SELECT max(revision) as revision_no FROM org_office_designation_map"
                    + " WHERE org_office_designation_map_id =" + org_office_designation_map_id + "  && active='Y' ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
        }
        return revision;
    }

    public int deleteRecord(int org_office_designation_map_id) throws SQLException {

        int org_id = 0, org_map1 = 0, org_map2 = 0, count = 0;
        int rowsAffected = 0;
        PreparedStatement psmt;
        ResultSet rst;

        String query = "DELETE FROM org_office_designation_map WHERE org_office_designation_map_id = '" + org_office_designation_map_id + "' "
                + "and active='Y' ";
        try {
            psmt = connection.prepareStatement(query);
            rowsAffected = psmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error: " + e);
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

    public List<String> getOrganisation_Name(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT org.organisation_name FROM organisation_name AS org  where org.active='Y' ORDER BY organisation_name ";
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
            System.out.println("Error:OrganisationMapModel--getOrganationNameList()-- " + e);
        }
        return list;
    }

    public List<String> searchOrganisation_Name(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT distinct org.organisation_name FROM organisation_name AS org,organisation_designation odm where org.organisation_id = odm.organisation_id and odm.active='Y' and org.active='Y' ";
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
            System.out.println("Error:OrganisationMapModel--getOrganationNameList()-- " + e);
        }
        return list;
    }

    public List<String> searchDesignation(String q, String searchOrgOffice) {
        String query = "";
        // int id = getDesgn_id(code);
        // int id1 = getDesgn_idd(id);
        List<String> list = new ArrayList<String>();

        query = "SELECT distinct d.designation FROM designation d,org_office_designation_map oodm,org_office oo"
                + " where d.designation_id=oodm.designation_id and oodm.org_office_id=oo.org_office_id and oodm.active='Y' "
                + " and d.active='Y' and oo.active='Y' ";

        if (!searchOrgOffice.equals("") && searchOrgOffice != null) {
            query += " and oo.org_office_name='" + searchOrgOffice + "' ";
        }

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;

            while (rset.next()) {    // move cursor from BOR to valid record.
                String state_name = (rset.getString("designation"));

                list.add(state_name);
                count++;

            }
            if (count == 0) {
                list.add("No such Designation exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public int getOrganisation_id(String organisation_name) {
        String query = "SELECT organisation_id FROM organisation_name WHERE organisation_name = ? and active=?";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, organisation_name);
            pstmt.setString(2, "Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("organisation_id");
        } catch (Exception e) {
            System.out.println("Error: OrganisationMapModel--" + e);
        }
        return organisation_id;
    }

    public int getOrganisationOfficeId(String organisation_name) {
        String query = "SELECT org_office_id FROM org_office WHERE org_office_name = '" + organisation_name + "' and active='Y'";
        int org_office_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setString(1, organisation_name);
//            pstmt.setString(2, "Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            org_office_id = rset.getInt("org_office_id");
        } catch (Exception e) {
            System.out.println("Error: getOrganisationOfficeId--" + e);
        }
        return org_office_id;
    }

    public int getParentGeneration(int org_id, int desig_id) {
        String query = "SELECT * FROM organisation_designation WHERE organisation_id = ? and organisation_designation_map_id_1=? and active=? ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, org_id);
            pstmt.setInt(2, desig_id);
            pstmt.setString(3, "Y");
            //    System.out.println("generation query --" + pstmt);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("generation");
        } catch (Exception e) {
            System.out.println("Error: OrganisationMapModel--" + e);
        }
        return organisation_id;
    }

    public int getDesignation_id(String organisation_name) {
        String query = "SELECT designation_id FROM designation WHERE designation = '" + organisation_name + "' and active='Y' ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setString(1, organisation_name);
//            pstmt.setString(2, "Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("designation_id");
        } catch (Exception e) {
            System.out.println("Error: OrganisationMapModel--" + e);
        }
        return organisation_id;
    }

    public static String getDesignation_name(String designation_id) {
        String query = "SELECT designation FROM designation WHERE designation_id = ? and active='Y' ";
        String designation_name = "";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, designation_id);
            //pstmt.setString(2, "Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            designation_name = rset.getString("designation");
        } catch (Exception e) {
            System.out.println("Error: OrganisationMapModel--" + e);
        }
        return designation_name;
    }

    public List<String> OrgOffice(String q) {
        List<String> list = new ArrayList<String>();
        //String query = "SELECT org.office_type FROM org_office_type AS org WHERE org.org_office_code = ? ORDER BY office_type ";
        String query = "SELECT org_office_name from org_office where active='Y' order by org_office_name";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1, office_code);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String OrgOffice = (rset.getString("org_office_name"));
                if (OrgOffice.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(OrgOffice);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such Organisation Name Exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:OrganisationMapModel--OrgOffice()-- " + e);
        }
        return list;
    }

    public List<String> getOrgOfficeNameSearch(String q, String office_code) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT o.org_office_name FROM org_office AS o  "
                + " WHERE IF('" + office_code + "'='', o.org_office_code LIKE '%%' , o.org_office_code='" + office_code + "') "
                + "ORDER BY o.org_office_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String org_office = rset.getString("org_office_name");
                if (org_office.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(org_office);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such Office Code Exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:OrganisationMapModel--office code()-- " + e);
        }
        return list;
    }

    public List<String> getOrgOfficeCodeSearch(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT oo.org_office_code FROM org_office as oo GROUP BY oo.org_office_code";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String org_office_code = rset.getString("org_office_code");
                {
                    list.add(org_office_code);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such Office Code Exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:OrganisationMapModel--office code()-- " + e);
        }
        return list;
    }

    public List<String> getGeneration(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT distinct org.generation FROM organisation_designation  AS org   where org.active='Y'  ORDER BY generation ";
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
            System.out.println("Error:OrganisationMapModel--getOrganationNameList()-- " + e);
        }
        return list;
    }

    public int getOrgOfficeType_id(String office_type) {
        String query = "SELECT office_type_id FROM org_office_type WHERE  office_type = ? ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //  pstmt.setString(1, office_code);
            pstmt.setString(1, office_type);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("office_type_id");
        } catch (Exception e) {
            System.out.println("Error: OrganisationMapModel--" + e);
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
            System.out.println("Error: " + e);
        }
        return city_id;
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
            System.out.println("Error: " + e);
        }
        return city_id;
    }

    public int getDesgn_idd(int office) {
        String query = "SELECT designation_id FROM organisation_designation_map WHERE organisation_id = ?";
        int city_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, office);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            city_id = rset.getInt("designation_id");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return city_id;
    }

    public List<String> getCityName(String q) {
        List<String> list = new ArrayList<String>();
//        String query = "SELECT city_name FROM city AS c ,state AS s WHERE c.state_id=s.state_id AND s.state_name=? "
//                + "  ORDER BY city_name";
        String query = "SELECT city_name FROM city AS c ORDER BY city_name ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //    pstmt.setString(1, krutiToUnicode.convert_to_unicode(state_name));

            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String AdvertiseName = rset.getString("city_name");
                if (AdvertiseName.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(AdvertiseName);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such City Name exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public List<String> getHierarchy(String q) {
        List<String> list = new ArrayList<String>();
//        String query = "SELECT city_name FROM city AS c ,state AS s WHERE c.state_id=s.state_id AND s.state_name=? "
//                + "  ORDER BY city_name";
        String query = "SELECT designation FROM designation AS d,organisation_designation as od where d.designation_id=od.organisation_designation_map_id_1 and d.active='Y' and od.active='Y' ORDER BY designation ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //    pstmt.setString(1, krutiToUnicode.convert_to_unicode(state_name));

            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String AdvertiseName = (rset.getString("designation"));
                if (AdvertiseName.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(AdvertiseName);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Designation Name exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public List<String> getStateName(String q, String code) {
        //  int id = getDesgn_id(code);
        // int id1 = getDesgn_idd(id);
        List<String> list = new ArrayList<String>();
        String query = "SELECT designation FROM designation where designation.active='Y' ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String state_name = (rset.getString("designation"));
                if (state_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(state_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Designation exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public List<String> getParentdesignation(String q, String code, String edit, String generationS, String currdesig) {
        int id = getDesgn_id(code);
        // int id1 = getDesgn_idd(id);
        List<String> list = new ArrayList<String>();

        String query = "SELECT * FROM designation d,organisation_designation o where "
                + " d.designation_id=o.organisation_designation_map_id_1 "
                + " and  o.organisation_id='" + id + "' and  o.active='Y' and d.active='Y' and o.is_super_child='N' ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            //  System.out.println("get paernt -" + query);
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String state_name = (rset.getString("designation"));
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
            System.out.println("Error: " + e);
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
            System.out.println("OrgOfficeModel closeConnection() Error: " + e);
        }
    }
}
