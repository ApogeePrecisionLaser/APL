/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.organization.model;

import com.lowagie.text.pdf.PdfWriter;
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
 * @author Soft_Tech
 */
public class OrgOfficeModel {

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
            System.out.println("OrgOfficeModel setConnection() Error: " + e);
        }
    }

    public List<Org_Office> showPDF(String org_name, String office_code_search, String office_name_search, String mobile, String generation, String active, String searchhierarchy) {
        PreparedStatement pstmt;
        String query;

        list1.clear();
        count = 0;
        List<Org_Office> list = new ArrayList<Org_Office>();
        int org_id = OrgOfficeModel.getOrgid(searchhierarchy);
        int count = 0;
        int o_id = 0;
        if (searchhierarchy.isEmpty()) {
            count = 0;
        } else {
            count++;
        }
        // Use DESC or ASC for descending or ascending order respectively of fetched data.
        if (count > 0) {

            list1 = OrgOfficeModel.showHierarchyParentData(org_id, org_name, office_code_search, office_name_search, mobile, generation, active, searchhierarchy, o_id);
            return list1;
        }

        try {
            org_name = (org_name);
            office_name_search = (office_name_search);

            // Use DESC or ASC for descending or ascending order respectively of fetched data.
//            query = " select o.org_office_code,o.org_office_id ,o.org_office_name,og.organisation_name,c.city_name,oft.office_type, "
//                    + " o.address_line1,o.address_line2, o.address_line3, o.email_id1, o.email_id2, o.mobile_no1, o.mobile_no2, o.landline_no1,"
//                    + " o.landline_no2, o.landline_no3,o.serial_no,o.super,o.generation,o.parent_org_office_id,o.latitude,o.longitude"
//                    + " FROM org_office o, organisation_name og, city c, org_office_type oft "
//                    + " where o.organisation_id = og.organisation_id and c.city_id = o.city_id and oft.office_type_id = o.office_type_id and "
//                    + "  og.active='Y' and c.active='Y'"
//                    + " AND IF ('" + org_name + "' = '' , og.organisation_name LIKE '%%',og.organisation_name= ?) "
//                    + " AND IF ('" + mobile + "' = '' , o.mobile_no1 LIKE '%%',o.mobile_no1= ?) "
//                    + " AND IF ('" + office_code_search + "' = '' , o.org_office_code LIKE '%%',o.org_office_code= ?) "
//                    + " AND IF ('" + office_name_search + "' = '' , o.org_office_name LIKE '%%',o.org_office_name= ?) "
//                    + " AND IF ('" + generation + "' = '' , o.generation LIKE '%%',o.generation= ?) "
//                    + " AND IF ('" + active + "' = '' , o.active LIKE '%%',o.active= ?)  "
//                    + " group by o.org_office_name order by og.organisation_name asc  ";
            query = " select o.org_office_code,o.org_office_id ,o.org_office_name,og.organisation_name,c.city_name,oft.office_type, "
                    + " o.address_line1,o.address_line2, o.address_line3, o.email_id1, o.email_id2, o.mobile_no1, o.mobile_no2, o.landline_no1,"
                    + " o.landline_no2, o.landline_no3,o.serial_no,o.super,o.generation,o.parent_org_office_id,o.latitude,o.longitude"
                    + " FROM org_office o, organisation_name og, city c, org_office_type oft "
                    + " where o.organisation_id = og.organisation_id and c.city_id = o.city_id and oft.office_type_id = o.office_type_id and "
                    + "  og.active='Y' and c.active='Y'";

            if (!org_name.equals("") && org_name != null) {
                query += " and og.organisation_name='" + org_name + "' ";
            }
            if (!mobile.equals("") && mobile != null) {
                query += " and o.mobile_no1='" + mobile + "' ";
            }
            if (!office_code_search.equals("") && office_code_search != null) {
                query += " and o.org_office_code='" + office_code_search + "' ";
            }
            if (!office_name_search.equals("") && office_name_search != null) {
                query += " and o.org_office_name='" + office_name_search + "' ";
            }
            if (!generation.equals("") && generation != null) {
                query += " and o.generation='" + generation + "' ";
            }
            query += " ORDER BY by og.organisation_name asc ";

            pstmt = connection.prepareStatement(query);

//            pstmt.setString(1, org_name);
//            pstmt.setString(2, mobile);
//            pstmt.setString(3, office_code_search);
//            pstmt.setString(4, office_name_search);
//            pstmt.setString(5, generation);
//            pstmt.setString(6, active);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                Org_Office organisation = new Org_Office();
                organisation.setOrg_office_id(rset.getInt("org_office_id"));
                organisation.setOrg_office_code(rset.getString("org_office_code"));
                organisation.setOrg_office_name((rset.getString("org_office_name")));
                organisation.setOrganisation_name((rset.getString("organisation_name")));
                organisation.setOffice_type((rset.getString("office_type")));
                organisation.setAddress_line1((rset.getString("address_line1")));
                organisation.setAddress_line2((rset.getString("address_line2")));
                organisation.setAddress_line3((rset.getString("address_line3")));
                // organisation.setState_name(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("state_name")));
                organisation.setCity_name((rset.getString("city_name")));
                organisation.setEmail_id1(rset.getString("email_id1"));
                organisation.setEmail_id2(rset.getString("email_id2"));
                organisation.setMobile_no1(rset.getString("mobile_no1"));
                organisation.setMobile_no2(rset.getString("mobile_no2"));
                organisation.setLandline_no1(rset.getString("landline_no1"));
                organisation.setLandline_no2(rset.getString("landline_no2"));
                organisation.setLandline_no3(rset.getString("landline_no3"));
                organisation.setSerialnumber(rset.getString("serial_no"));
                organisation.setSuperp(rset.getString("super"));
                organisation.setGeneration(rset.getString("generation"));
                String p_org = Orgofficename((rset.getString("parent_org_office_id")));
                organisation.setP_org(p_org);
                organisation.setLatitude(rset.getString("latitude"));
                organisation.setLongitude(rset.getString("longitude"));

                list.add(organisation);
                organisation.setList(list);
            }
        } catch (Exception e) {
            System.out.println("Error:--organisation--- showPDF--" + e);
        }
        return list;
    }

    public List<Org_Office> showData(String org_name, String office_code_search, String office_name_search, String mobile, String generation, String active, String searchhierarchy) {

        PreparedStatement pstmt;
        String query;
        list1.clear();
        count = 0;
        List<Org_Office> list = new ArrayList<Org_Office>();
        int org_id = OrgOfficeModel.getOrgid(searchhierarchy);
        int count = 0;
        int o_id = 0;
        if (searchhierarchy.isEmpty()) {
            count = 0;
        } else {
            count++;
        }
        // Use DESC or ASC for descending or ascending order respectively of fetched data.
        if (count > 0) {

            list1 = OrgOfficeModel.showHierarchyParentData(org_id, org_name, office_code_search, office_name_search, mobile, generation, active, searchhierarchy, o_id);
            return list1;
        }

        try {
            org_name = (org_name);
            office_name_search = (office_name_search);

            // Use DESC or ASC for descending or ascending order respectively of fetched data.
//            query = " select o.org_office_code,o.org_office_id ,o.org_office_name,og.organisation_name,c.city_name,oft.office_type, "
//                    + " o.address_line1,o.address_line2, o.address_line3, o.email_id1, o.email_id2, o.mobile_no1, o.mobile_no2, o.landline_no1,"
//                    + " o.landline_no2, o.landline_no3,o.serial_no,o.super,o.generation,o.parent_org_office_id,o.latitude,o.longitude"
//                    + " FROM org_office o, organisation_name og, city c, org_office_type oft "
//                    + " where o.organisation_id = og.organisation_id and c.city_id = o.city_id and oft.office_type_id = o.office_type_id and "
//                    + "  og.active='Y' and c.active='Y'"
//                    + " AND IF ('" + org_name + "' = '' , og.organisation_name LIKE '%%',og.organisation_name= ?) "
//                    + " AND IF ('" + mobile + "' = '' , o.mobile_no1 LIKE '%%',o.mobile_no1= ?) "
//                    + " AND IF ('" + office_code_search + "' = '' , o.org_office_code LIKE '%%',o.org_office_code= ?) "
//                    + " AND IF ('" + office_name_search + "' = '' , o.org_office_name LIKE '%%',o.org_office_name= ?) "
//                    + " AND IF ('" + generation + "' = '' , o.generation LIKE '%%',o.generation= ?) "
//                    + " AND IF ('" + active + "' = '' , o.active LIKE '%%',o.active= ?) group by o.org_office_name "
//                    + "order by og.organisation_name asc ";
            query = " select o.org_office_code,o.org_office_id ,o.org_office_name,og.organisation_name,c.city_name,oft.office_type, "
                    + " o.address_line1,o.address_line2, o.address_line3, o.email_id1, o.email_id2, o.mobile_no1, o.mobile_no2, o.landline_no1,"
                    + " o.landline_no2, o.landline_no3,o.serial_no,o.super,o.generation,o.parent_org_office_id,o.latitude,o.longitude"
                    + " FROM org_office o, organisation_name og, city c, org_office_type oft "
                    + " where o.organisation_id = og.organisation_id and c.city_id = o.city_id and oft.office_type_id = o.office_type_id and "
                    + "  og.active='Y' and c.active='Y' and o.active='Y' ";

            if (!org_name.equals("") && org_name != null) {
                query += " and og.organisation_name='" + org_name + "' ";
            }
            if (!mobile.equals("") && mobile != null) {
                query += " and o.mobile_no1='" + mobile + "' ";
            }
            if (!office_code_search.equals("") && office_code_search != null) {
                query += " and o.org_office_code='" + office_code_search + "' ";
            }
            if (!office_name_search.equals("") && office_name_search != null) {
                query += " and o.org_office_name='" + office_name_search + "' ";
            }
            if (!generation.equals("") && generation != null) {
                query += " and o.generation='" + generation + "' ";
            }
            query += " ORDER BY og.organisation_name asc ";

           // System.err.println("query--------------" + query);
            pstmt = connection.prepareStatement(query);

//            pstmt.setString(1, org_name);
//            pstmt.setString(2, mobile);
//            pstmt.setString(3, office_code_search);
//            pstmt.setString(4, office_name_search);
//            pstmt.setString(5, generation);
//            pstmt.setString(6, active);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                Org_Office organisation = new Org_Office();
                organisation.setOrg_office_id(rset.getInt("org_office_id"));
                organisation.setOrg_office_code(rset.getString("org_office_code"));
                organisation.setOrg_office_name((rset.getString("org_office_name")));
                organisation.setOrganisation_name((rset.getString("organisation_name")));
                organisation.setOffice_type((rset.getString("office_type")));
                organisation.setAddress_line1((rset.getString("address_line1")));
                organisation.setAddress_line2((rset.getString("address_line2")));
                organisation.setAddress_line3((rset.getString("address_line3")));
                // organisation.setState_name(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("state_name")));
                organisation.setCity_name((rset.getString("city_name")));
                organisation.setEmail_id1(rset.getString("email_id1"));
                organisation.setEmail_id2(rset.getString("email_id2"));
                organisation.setMobile_no1(rset.getString("mobile_no1"));
                organisation.setMobile_no2(rset.getString("mobile_no2"));
                organisation.setLandline_no1(rset.getString("landline_no1"));
                organisation.setLandline_no2(rset.getString("landline_no2"));
                organisation.setLandline_no3(rset.getString("landline_no3"));
                organisation.setSerialnumber(rset.getString("serial_no"));
                organisation.setSuperp(rset.getString("super"));
                organisation.setGeneration(rset.getString("generation"));
                String p_org = Orgofficename((rset.getString("parent_org_office_id")));
                organisation.setP_org(p_org);
                organisation.setLatitude(rset.getString("latitude"));
                organisation.setLongitude(rset.getString("longitude"));
                list.add(organisation);
              //  System.err.println("org-------------"+list.size());
            }
        } catch (Exception e) {
            System.out.println("Error:--organisation--- showData--" + e);
        }
        return list;
    }

    public List<Org_Office> showDataa(String org_name, String office_code_search, String office_name_search, String mobile, String generation, String active, String searchhierarchy) {
        PreparedStatement pstmt;
        String query;
        list1.clear();
        List<Org_Office> list = new ArrayList<Org_Office>();
        int org_id = OrgOfficeModel.getOrgid(searchhierarchy);
        int count = 0;
        int o_id = 0;
        if (searchhierarchy.isEmpty()) {
            count = 0;
        } else {
            count++;
        }
        // Use DESC or ASC for descending or ascending order respectively of fetched data.
        if (count > 0) {

            list1 = OrgOfficeModel.showHierarchyParentData(org_id, org_name, office_code_search, office_name_search, mobile, generation, active, searchhierarchy, o_id);
            return list1;
        }

        try {
            org_name = (org_name);
            office_name_search = (office_name_search);

            // Use DESC or ASC for descending or ascending order respectively of fetched data.
//            query = " select o.org_office_code,o.org_office_id ,o.org_office_name,og.organisation_name,c.city_name,oft.office_type, "
//                    + " o.address_line1,o.address_line2, o.address_line3, o.email_id1, o.email_id2, o.mobile_no1, o.mobile_no2, o.landline_no1,"
//                    + " o.landline_no2, o.landline_no3,o.serial_no,o.super,o.generation,o.parent_org_office_id,o.latitude,o.longitude"
//                    + " FROM org_office o, organisation_name og, city c, org_office_type oft "
//                    + " where o.organisation_id = og.organisation_id and c.city_id = o.city_id and oft.office_type_id = o.office_type_id and "
//                    + "  og.active='Y' and c.active='Y'"
//                    + " AND IF ('" + org_name + "' = '' , og.organisation_name LIKE '%%',og.organisation_name= ?) "
//                    + " AND IF ('" + mobile + "' = '' , o.mobile_no1 LIKE '%%',o.mobile_no1= ?) "
//                    + " AND IF ('" + office_code_search + "' = '' , o.org_office_code LIKE '%%',o.org_office_code= ?) "
//                    + " AND IF ('" + office_name_search + "' = '' , o.org_office_name LIKE '%%',o.org_office_name= ?) "
//                    + " AND IF ('" + generation + "' = '' , o.generation LIKE '%%',o.generation= ?) "
//                    + " AND IF ('" + active + "' = '' , o.active LIKE '%%',o.active= ?)  group by o.org_office_name order by"
//                    + " og.organisation_name asc ";
//            
//            
            query = " select o.org_office_code,o.org_office_id ,o.org_office_name,og.organisation_name,c.city_name,oft.office_type, "
                    + " o.address_line1,o.address_line2, o.address_line3, o.email_id1, o.email_id2, o.mobile_no1, o.mobile_no2, o.landline_no1,"
                    + " o.landline_no2, o.landline_no3,o.serial_no,o.super,o.generation,o.parent_org_office_id,o.latitude,o.longitude"
                    + " FROM org_office o, organisation_name og, city c, org_office_type oft "
                    + " where o.organisation_id = og.organisation_id and c.city_id = o.city_id and oft.office_type_id = o.office_type_id and "
                    + "  og.active='Y' and c.active='Y'";

            if (!org_name.equals("") && org_name != null) {
                query += " and og.organisation_name='" + org_name + "' ";
            }
            if (!mobile.equals("") && mobile != null) {
                query += " and o.mobile_no1='" + mobile + "' ";
            }
            if (!office_code_search.equals("") && office_code_search != null) {
                query += " and o.org_office_code='" + office_code_search + "' ";
            }
            if (!office_name_search.equals("") && office_name_search != null) {
                query += " and o.org_office_name='" + office_name_search + "' ";
            }
            if (!generation.equals("") && generation != null) {
                query += " and o.generation='" + generation + "' ";
            }
            query += " ORDER BY og.organisation_name asc ";

            pstmt = connection.prepareStatement(query);

//            pstmt.setString(1, org_name);
//            pstmt.setString(2, mobile);
//            pstmt.setString(3, office_code_search);
//            pstmt.setString(4, office_name_search);
//            pstmt.setString(5, generation);
//            pstmt.setString(6, active);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                Org_Office organisation = new Org_Office();
                organisation.setOrg_office_id(rset.getInt("org_office_id"));
                organisation.setOrg_office_code(rset.getString("org_office_code"));
                organisation.setOrg_office_name((rset.getString("org_office_name")));
                organisation.setOrganisation_name((rset.getString("organisation_name")));
                organisation.setOffice_type((rset.getString("office_type")));
                organisation.setAddress_line1((rset.getString("address_line1")));
                organisation.setAddress_line2((rset.getString("address_line2")));
                organisation.setAddress_line3((rset.getString("address_line3")));
                // organisation.setState_name(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("state_name")));
                organisation.setCity_name((rset.getString("city_name")));
                organisation.setEmail_id1(rset.getString("email_id1"));
                organisation.setEmail_id2(rset.getString("email_id2"));
                organisation.setMobile_no1(rset.getString("mobile_no1"));
                organisation.setMobile_no2(rset.getString("mobile_no2"));
                organisation.setLandline_no1(rset.getString("landline_no1"));
                organisation.setLandline_no2(rset.getString("landline_no2"));
                organisation.setLandline_no3(rset.getString("landline_no3"));
                organisation.setSerialnumber(rset.getString("serial_no"));
                organisation.setSuperp(rset.getString("super"));
                organisation.setGeneration(rset.getString("generation"));
                String p_org = Orgofficename((rset.getString("parent_org_office_id")));
                organisation.setP_org(p_org);
                organisation.setLatitude(rset.getString("latitude"));
                organisation.setLongitude(rset.getString("longitude"));
                list.add(organisation);
            }
        } catch (Exception e) {
            System.out.println("Error:--organisation--- showDataa--" + e);
        }
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
//            pstmt.setString(1, org_id);
//
//            pstmt.setString(2, "Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("org_office_id");
        } catch (Exception e) {
            System.out.println("Error: getOrgid--" + e);
        }
        return organisation_id;
    }

    //show data parent office hierarchy
    public static List<Org_Office> showHierarchyParentData(int org_id, String org_name, String office_code_search, String office_name_search, String mobile, String generation, String active, String searchhierarchy, int o_id) {
        int id = 0;
        String p_idd = "";

//        String query = " select o.org_office_code,o.org_office_id ,o.org_office_name,og.organisation_name,c.city_name,oft.office_type, "
//                + " o.address_line1,o.address_line2, o.address_line3, o.email_id1, o.email_id2, o.mobile_no1, o.mobile_no2, o.landline_no1,"
//                + " o.landline_no2, o.landline_no3,o.serial_no,o.super,o.generation,o.parent_org_office_id,o.latitude,o.longitude"
//                + " FROM org_office o, organisation_name og, city c, org_office_type oft "
//                + " where o.organisation_id = og.organisation_id and c.city_id = o.city_id and oft.office_type_id = o.office_type_id and "
//                + "  og.active='Y' and c.active='Y'"
//                + " AND IF ('" + active + "' = '' , o.active LIKE '%%',o.active= ?) and "
//                + " IF('" + org_id + "' = '', o.org_office_id LIKE '%%',o.org_office_id =?) order by generation  ";
        String query = " select o.org_office_code,o.org_office_id ,o.org_office_name,og.organisation_name,c.city_name,oft.office_type, "
                + " o.address_line1,o.address_line2, o.address_line3, o.email_id1, o.email_id2, o.mobile_no1, o.mobile_no2, o.landline_no1, "
                + " o.landline_no2, o.landline_no3,o.serial_no,o.super,o.generation,o.parent_org_office_id,o.latitude,o.longitude "
                + " FROM org_office o, organisation_name og, city c, org_office_type oft "
                + " where o.organisation_id = og.organisation_id and c.city_id = o.city_id and oft.office_type_id = o.office_type_id and "
                + "  og.active='Y' and c.active='Y' and o.org_office_id='" + org_id + "' order by generation  ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

//            pstmt.setString(1, active);
//            pstmt.setInt(2, org_id);
//                pstmt.setInt(3, o_id);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {

//                int id =rset.getInt("organisation_type_id");
//                organisationType.setOrganisation_type_id(rset.getInt("organisation_type_id"));
//                o_id =OrganisationTypeModel.getParentOrgid(id);
//               
//               
//                
//                organisationType.setOrg_type_name((rset.getString("org_type_name")));
//                organisationType.setDescription((rset.getString("description")));
//                String p_ot_id=rset.getString("parent_org_id");
//                organisationType.setP_ot(getParentOrgname(Integer.parseInt(p_ot_id)));
//                              
//                  organisationType.setSupper(rset.getString("super"));
//                  organisationType.setGeneration(rset.getInt("generation"));
//                   list1.add(organisationType);
                Org_Office organisation = new Org_Office();

                id = rset.getInt("org_office_id");

                organisation.setOrg_office_id(rset.getInt("org_office_id"));
                o_id = OrgOfficeModel.getParentOrgid(id);
                off_id = OrgOfficeModel.getcheckorgid(id);

                organisation.setOrg_office_code(rset.getString("org_office_code"));
                organisation.setOrg_office_name((rset.getString("org_office_name")));
                organisation.setOrganisation_name((rset.getString("organisation_name")));
                organisation.setOffice_type((rset.getString("office_type")));
                organisation.setAddress_line1((rset.getString("address_line1")));
                organisation.setAddress_line2((rset.getString("address_line2")));
                organisation.setAddress_line3((rset.getString("address_line3")));
                // organisation.setState_name(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("state_name")));
                organisation.setCity_name((rset.getString("city_name")));
                organisation.setEmail_id1(rset.getString("email_id1"));
                organisation.setEmail_id2(rset.getString("email_id2"));
                organisation.setMobile_no1(rset.getString("mobile_no1"));
                organisation.setMobile_no2(rset.getString("mobile_no2"));
                organisation.setLandline_no1(rset.getString("landline_no1"));
                organisation.setLandline_no2(rset.getString("landline_no2"));
                organisation.setLandline_no3(rset.getString("landline_no3"));
                organisation.setSerialnumber(rset.getString("serial_no"));
                organisation.setSuperp(rset.getString("super"));
                organisation.setGeneration(rset.getString("generation"));
                String p_org = Orgofficename((rset.getString("parent_org_office_id")));
                organisation.setP_org(p_org);
                organisation.setLatitude(rset.getString("latitude"));
                organisation.setLongitude(rset.getString("longitude"));

                list1.add(organisation);
                count++;
            }
        } catch (Exception e) {
            System.out.println("showHierarchyParentData Error: " + e);
        }
        if (count > 1) {
            showHierarchyData2(o_id, org_name, office_code_search, office_name_search, mobile, generation, active, searchhierarchy, o_id);
        }

        showHierarchyData(id, org_name, office_code_search, office_name_search, mobile, generation, active, searchhierarchy, o_id);

        //   }
        return list1;
    }

    public static List<Org_Office> showHierarchyData(int org_id, String org_name, String office_code_search, String office_name_search, String mobile, String generation, String active, String searchhierarchy, int o_id) {
        int id = 0;
        HashSet hs = new HashSet();
        //   int org_id  = OrgOfficeModel.getOrgid(searchhierarchy);
        String p_idd = "";

        List<Integer> idList = new ArrayList<Integer>();

//        String query = " select o.org_office_code,o.org_office_id ,o.org_office_name,og.organisation_name,c.city_name,oft.office_type, "
//                + " o.address_line1,o.address_line2, o.address_line3, o.email_id1, o.email_id2, o.mobile_no1, o.mobile_no2, o.landline_no1,"
//                + " o.landline_no2, o.landline_no3,o.serial_no,o.super,o.generation,o.parent_org_office_id,o.latitude,o.longitude"
//                + " FROM org_office o, organisation_name og, city c, org_office_type oft "
//                + " where o.organisation_id = og.organisation_id and c.city_id = o.city_id and oft.office_type_id = o.office_type_id and "
//                + "  og.active='Y' and c.active='Y' and o.parent_org_office_id=" + org_id + "  "
//                + " AND IF ('" + active + "' = '' , o.active LIKE '%%',o.active= ?) order by generation ";
        String query = " select o.org_office_code,o.org_office_id ,o.org_office_name,og.organisation_name,c.city_name,oft.office_type, "
                + " o.address_line1,o.address_line2, o.address_line3, o.email_id1, o.email_id2, o.mobile_no1, o.mobile_no2, o.landline_no1, "
                + " o.landline_no2, o.landline_no3,o.serial_no,o.super,o.generation,o.parent_org_office_id,o.latitude,o.longitude "
                + " FROM org_office o, organisation_name og, city c, org_office_type oft "
                + " where o.organisation_id = og.organisation_id and c.city_id = o.city_id and oft.office_type_id = o.office_type_id and "
                + "  og.active='Y' and c.active='Y' and o.org_office_id='" + org_id + "' order by generation  ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

//            pstmt.setString(1, active);
            //   pstmt.setString(1, searchhierarchy);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
//                OrganisationType organisationType = new OrganisationType();
//                int id =rset.getInt("organisation_type_id");
//                organisationType.setOrganisation_type_id(rset.getInt("organisation_type_id"));
//                o_id =OrganisationTypeModel.getParentOrgid(id);
//               
//               
//                
//                organisationType.setOrg_type_name((rset.getString("org_type_name")));
//                organisationType.setDescription((rset.getString("description")));
//                String p_ot_id=rset.getString("parent_org_id");
//                organisationType.setP_ot(getParentOrgname(Integer.parseInt(p_ot_id)));
//                              
//                  organisationType.setSupper(rset.getString("super"));
//                  organisationType.setGeneration(rset.getInt("generation"));
//                   list1.add(organisationType);

                Org_Office organisation = new Org_Office();
                id = rset.getInt("org_office_id");

                organisation.setOrg_office_id(rset.getInt("org_office_id"));
                o_id = OrgOfficeModel.getParentOrgid(id);
                prev_parent_id = o_id;
                off_id = OrgOfficeModel.getcheckorgid(id);
                prev_parent_id = off_id;

                if (o_id == 0) {
                    break;

                }
                //        idList = getAllParentChild(searchhierarchy);
                organisation.setOrg_office_code(rset.getString("org_office_code"));
                organisation.setOrg_office_name((rset.getString("org_office_name")));
                organisation.setOrganisation_name((rset.getString("organisation_name")));
                organisation.setOffice_type((rset.getString("office_type")));
                organisation.setAddress_line1((rset.getString("address_line1")));
                organisation.setAddress_line2((rset.getString("address_line2")));
                organisation.setAddress_line3((rset.getString("address_line3")));
                // organisation.setState_name(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("state_name")));
                organisation.setCity_name((rset.getString("city_name")));
                organisation.setEmail_id1(rset.getString("email_id1"));
                organisation.setEmail_id2(rset.getString("email_id2"));
                organisation.setMobile_no1(rset.getString("mobile_no1"));
                organisation.setMobile_no2(rset.getString("mobile_no2"));
                organisation.setLandline_no1(rset.getString("landline_no1"));
                organisation.setLandline_no2(rset.getString("landline_no2"));
                organisation.setLandline_no3(rset.getString("landline_no3"));
                organisation.setSerialnumber(rset.getString("serial_no"));
                organisation.setSuperp(rset.getString("super"));
                organisation.setGeneration(rset.getString("generation"));
                String p_org = Orgofficename((rset.getString("parent_org_office_id")));
                organisation.setP_org(p_org);
                organisation.setLatitude(rset.getString("latitude"));
                organisation.setLongitude(rset.getString("longitude"));

                list1.add(organisation);
                if (off_id != 0) {
                    showHierarchyData2(id, org_name, office_code_search, office_name_search, mobile, generation, active, searchhierarchy, o_id);
                }
            }

        } catch (Exception e) {
            System.out.println("showHierarchyData Error: " + e);
        }

        if (o_id != 0) {
            //  showHierarchyData(lowerLimit,noOfRowsToDisplay,id,org_name,office_code_search,office_name_search,mobile,generation,active,searchhierarchy,o_id);
        }
//}
        return list1;
    }

    public static List<Org_Office> showHierarchyData2(int org_id, String org_name, String office_code_search, String office_name_search, String mobile, String generation, String active, String searchhierarchy, int o_id) {
        int id = 0;

        String p_idd = "";
//            
//        String query = " select o.org_office_code,o.org_office_id ,o.org_office_name,og.organisation_name,c.city_name,oft.office_type, "
//                + " o.address_line1,o.address_line2, o.address_line3, o.email_id1, o.email_id2, o.mobile_no1, o.mobile_no2, o.landline_no1,"
//                + " o.landline_no2, o.landline_no3,o.serial_no,o.super,o.generation,o.parent_org_office_id,o.latitude,o.longitude"
//                + " FROM org_office o, organisation_name og, city c, org_office_type oft "
//                + " where o.organisation_id = og.organisation_id and c.city_id = o.city_id and oft.office_type_id = o.office_type_id and "
//                + "  og.active='Y' and c.active='Y' and o.parent_org_office_id=" + org_id + "  "
//                + " AND IF ('" + active + "' = '' , o.active LIKE '%%',o.active= ?) order by generation  ";

        String query = " select o.org_office_code,o.org_office_id ,o.org_office_name,og.organisation_name,c.city_name,oft.office_type, "
                + " o.address_line1,o.address_line2, o.address_line3, o.email_id1, o.email_id2, o.mobile_no1, o.mobile_no2, o.landline_no1, "
                + " o.landline_no2, o.landline_no3,o.serial_no,o.super,o.generation,o.parent_org_office_id,o.latitude,o.longitude "
                + " FROM org_office o, organisation_name og, city c, org_office_type oft "
                + " where o.organisation_id = og.organisation_id and c.city_id = o.city_id and oft.office_type_id = o.office_type_id and "
                + "  og.active='Y' and c.active='Y' and o.org_office_id='" + org_id + "' order by generation  ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

//            pstmt.setString(1, active);
            //   pstmt.setString(1, searchhierarchy);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
//                OrganisationType organisationType = new OrganisationType();
//                int id =rset.getInt("organisation_type_id");
//                organisationType.setOrganisation_type_id(rset.getInt("organisation_type_id"));
//                o_id =OrganisationTypeModel.getParentOrgid(id);
//               
//               
//                
//                organisationType.setOrg_type_name((rset.getString("org_type_name")));
//                organisationType.setDescription((rset.getString("description")));
//                String p_ot_id=rset.getString("parent_org_id");
//                organisationType.setP_ot(getParentOrgname(Integer.parseInt(p_ot_id)));
//                              
//                  organisationType.setSupper(rset.getString("super"));
//                  organisationType.setGeneration(rset.getInt("generation"));
//                   list1.add(organisationType);

                Org_Office organisation = new Org_Office();
                id = rset.getInt("org_office_id");

                organisation.setOrg_office_id(rset.getInt("org_office_id"));
                o_id = OrgOfficeModel.getParentOrgid(id);
                off_id = OrgOfficeModel.getcheckorgid(id);

                if (id == 0) {
                    break;
                }
                organisation.setOrg_office_code(rset.getString("org_office_code"));
                organisation.setOrg_office_name((rset.getString("org_office_name")));
                organisation.setOrganisation_name((rset.getString("organisation_name")));
                organisation.setOffice_type((rset.getString("office_type")));
                organisation.setAddress_line1((rset.getString("address_line1")));
                organisation.setAddress_line2((rset.getString("address_line2")));
                organisation.setAddress_line3((rset.getString("address_line3")));
                // organisation.setState_name(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("state_name")));
                organisation.setCity_name((rset.getString("city_name")));
                organisation.setEmail_id1(rset.getString("email_id1"));
                organisation.setEmail_id2(rset.getString("email_id2"));
                organisation.setMobile_no1(rset.getString("mobile_no1"));
                organisation.setMobile_no2(rset.getString("mobile_no2"));
                organisation.setLandline_no1(rset.getString("landline_no1"));
                organisation.setLandline_no2(rset.getString("landline_no2"));
                organisation.setLandline_no3(rset.getString("landline_no3"));
                organisation.setSerialnumber(rset.getString("serial_no"));
                organisation.setSuperp(rset.getString("super"));
                organisation.setGeneration(rset.getString("generation"));
                String p_org = Orgofficename((rset.getString("parent_org_office_id")));
                organisation.setP_org(p_org);
                organisation.setLatitude(rset.getString("latitude"));
                organisation.setLongitude(rset.getString("longitude"));
                list1.add(organisation);
//                if(off_id!=0 )
//                {
//                    count++;
//                  showHierarchyParentData(lowerLimit,noOfRowsToDisplay,off_id,org_name,office_code_search,office_name_search,mobile,generation,active,searchhierarchy,o_id);
//                }
                if (off_id != 0) {
                    showHierarchyData2(id, org_name, office_code_search, office_name_search, mobile, generation, active, searchhierarchy, o_id);
                }
            }
        } catch (Exception e) {
            System.out.println("showHierarchyData2 Error: " + e);
        }

        if (off_id != 0) {
            showHierarchyData2(id, org_name, office_code_search, office_name_search, mobile, generation, active, searchhierarchy, o_id);
        }
        // }
        return list1;
    }

    public static int getParentOrgid(int org_id) {
        String query = "SELECT parent_org_office_id FROM org_office WHERE  org_office_id=? and active=? ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setInt(1, org_id);
//
//            pstmt.setString(2, "Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("parent_org_office_id");
        } catch (Exception e) {
            System.out.println("Error: getParentOrgid--" + e);
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
            System.out.println("Error: getcheckorgid--" + e);
        }
        return organisation_id;
    }

    public List<Org_Office> showData3(int lowerLimit, int noOfRowsToDisplay, String org_name, String office_code_search, String office_name_search, String mobile, String generation, String active, String searchhierarchy) {
        PreparedStatement pstmt;
        String query = "";
        int o_id = 0;
        list1.clear();
        if (searchhierarchy == null) {
            searchhierarchy = "";
        }
        int org_id = OrgOfficeModel.getOrgid(searchhierarchy);
        List<Org_Office> list = new ArrayList<Org_Office>();
        List<Integer> idList = new ArrayList<Integer>();
        idList = getAllParentChild(searchhierarchy);

        //    int id1 = (int) id[0];
        try {
            //query = "select wt.work_type_id,org.organisation_name,wt.work_name,wt.remark,wt.is_super_child,wt.parent_id, "
//            query = " select o.org_office_code,o.org_office_id ,o.org_office_name,og.organisation_name,c.city_name,oft.office_type, "
//                    + " o.address_line1,o.address_line2, o.address_line3, o.email_id1, o.email_id2, o.mobile_no1, o.mobile_no2, o.landline_no1,"
//                    + " o.landline_no2, o.landline_no3,o.serial_no,o.super,o.generation,o.parent_org_office_id,o.latitude,o.longitude"
//                    + " FROM org_office o, organisation_name og, city c, org_office_type oft "
//                    + " where o.organisation_id = og.organisation_id and c.city_id = o.city_id and oft.office_type_id = o.office_type_id and "
//                    + "  og.active='Y' and c.active='Y'"
//                    + " AND IF ('" + org_name + "' = '' , og.organisation_name LIKE '%%',og.organisation_name= ?) "
//                    + " AND IF ('" + mobile + "' = '' , o.mobile_no1 LIKE '%%',o.mobile_no1= ?) "
//                    + " AND IF ('" + office_code_search + "' = '' , o.org_office_code LIKE '%%',o.org_office_code= ?) "
//                    + " AND IF ('" + office_name_search + "' = '' , o.org_office_name LIKE '%%',o.org_office_name= ?) "
//                    + " AND IF ('" + generation + "' = '' , o.generation LIKE '%%',o.generation= ?) "
//                    + " AND IF ('" + active + "' = '' , o.active LIKE '%%',o.active= ?) ";

            query = " select o.org_office_code,o.org_office_id ,o.org_office_name,og.organisation_name,c.city_name,oft.office_type, "
                    + " o.address_line1,o.address_line2, o.address_line3, o.email_id1, o.email_id2, o.mobile_no1, o.mobile_no2, o.landline_no1,"
                    + " o.landline_no2, o.landline_no3,o.serial_no,o.super,o.generation,o.parent_org_office_id,o.latitude,o.longitude"
                    + " FROM org_office o, organisation_name og, city c, org_office_type oft "
                    + " where o.organisation_id = og.organisation_id and c.city_id = o.city_id and oft.office_type_id = o.office_type_id and "
                    + "  og.active='Y' and c.active='Y'";

            if (!org_name.equals("") && org_name != null) {
                query += " and og.organisation_name='" + org_name + "' ";
            }
            if (!mobile.equals("") && mobile != null) {
                query += " and o.mobile_no1='" + mobile + "' ";
            }
            if (!office_code_search.equals("") && office_code_search != null) {
                query += " and o.org_office_code='" + office_code_search + "' ";
            }
            if (!office_name_search.equals("") && office_name_search != null) {
                query += " and o.org_office_name='" + office_name_search + "' ";
            }
            if (!generation.equals("") && generation != null) {
                query += " and o.generation='" + generation + "' ";
            }

            if (!searchhierarchy.equals("")) {
                //query += "and wt.work_name='" + searchWorkname + "' ";
                query += "and o.org_office_id in(" + idList.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ") ";
            }
            if (!org_name.equals("")) {
                query += "and o.org_office_id='" + org_id + "' ";
            }

            if (!searchhierarchy.equals("")) {
                query += " order by field(o.org_office_id," + idList.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ") ";

            } else {
                query += " order by generation ";
            }

            pstmt = connection.prepareStatement(query);

//            pstmt.setString(1, org_name);
//            pstmt.setString(2, mobile);
//            pstmt.setString(3, office_code_search);
//            pstmt.setString(4, office_name_search);
//            pstmt.setString(5, generation);
//            pstmt.setString(6, active);
            ResultSet rset = pstmt.executeQuery();
            System.out.println("work type query -" + pstmt);
            while (rset.next()) {

                Org_Office organisation = new Org_Office();
                organisation.setOrg_office_id(rset.getInt("org_office_id"));
                organisation.setOrg_office_code(rset.getString("org_office_code"));
                organisation.setOrg_office_name((rset.getString("org_office_name")));
                organisation.setOrganisation_name((rset.getString("organisation_name")));
                organisation.setOffice_type((rset.getString("office_type")));
                organisation.setAddress_line1((rset.getString("address_line1")));
                organisation.setAddress_line2((rset.getString("address_line2")));
                organisation.setAddress_line3((rset.getString("address_line3")));
                // organisation.setState_name(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("state_name")));
                organisation.setCity_name((rset.getString("city_name")));
                organisation.setEmail_id1(rset.getString("email_id1"));
                organisation.setEmail_id2(rset.getString("email_id2"));
                organisation.setMobile_no1(rset.getString("mobile_no1"));
                organisation.setMobile_no2(rset.getString("mobile_no2"));
                organisation.setLandline_no1(rset.getString("landline_no1"));
                organisation.setLandline_no2(rset.getString("landline_no2"));
                organisation.setLandline_no3(rset.getString("landline_no3"));
                organisation.setSerialnumber(rset.getString("serial_no"));
                organisation.setSuperp(rset.getString("super"));
                organisation.setGeneration(rset.getString("generation"));
                String p_org = Orgofficename((rset.getString("parent_org_office_id")));
                organisation.setP_org(p_org);
                organisation.setLatitude(rset.getString("latitude"));
                organisation.setLongitude(rset.getString("longitude"));
                list.add(organisation);

            }
            list1.addAll(list);
            list1 = OrgOfficeModel.showHierarchyData(org_id, org_name, office_code_search, office_name_search, mobile, generation, active, searchhierarchy, o_id);
        } catch (Exception e) {
            System.out.println("Error:--organisation--- showData3--" + e);
        }
        return list1;
    }

    public static List<Integer> getAllParentChild(String searchofficename) {
        PreparedStatement pstmt;
        String query = "";
        List<Integer> list = new ArrayList<Integer>();
        //TreeSet<Integer> treeList=new TreeSet<Integer>();
        //dataList.clear();
        if (searchofficename == null) {
            searchofficename = "";
        }
        int work_type_id = 0, parent_id = 0;

        String qry = "select org_office_id from org_office where active='Y' and org_office_name='" + searchofficename + "' ";
        try {
            PreparedStatement pst = connection.prepareStatement(qry);
            ResultSet rstt = pst.executeQuery();
            while (rstt.next()) {
                work_type_id = rstt.getInt(1);
                list.add(work_type_id);
                //treeList.add(work_type_id);
            }
        } catch (Exception e) {
            System.out.println("com.ipms.ePass.model.WorkTypeModel.getAllParentChild() -" + e);
        }

        //int orgid = getOrganisation_id(org_name);        
        try {
//            query = " select distinct org_office_id,org_office_name,parent_org_office_id,generation " +
//                    " from    (select * from org_office where active='Y' " +
//                    "  order by parent_org_office_id, org_office_id) products_sorted, " +
//                    "   (select @pv := '"+work_type_id+"') initialisation " +
//                    "  where   find_in_set(parent_org_office_id, @pv) " +
//                    "  and  length(@pv := concat(@pv, ',', org_office_id))  ";

            query = "SELECT distinct t2.org_office_id as lev2, t3.org_office_id as lev3 FROM org_office AS t1 "
                    + " LEFT JOIN org_office AS t2 ON t2.parent_org_office_id = t1.org_office_id "
                    + " LEFT JOIN org_office AS t3 ON t3.parent_org_office_id = t2.org_office_id "
                    + " WHERE t1.org_office_name = '" + searchofficename + "' and t1.active='Y' and t3.active='Y' ";

            System.out.println("query parent child data -" + query);

            pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                list.add(rset.getInt(1));
                list.add(rset.getInt(2));
                //treeList.add(rset.getInt(1));
                //treeList.add(rset.getInt(2));
            }

        } catch (Exception e) {
            System.out.println("Error:--organisation--- getAllParentChild--" + e);
        }

        System.out.println("list data -" + list.toString());
        //System.out.println("hash list data -" + treeList.toString());
        return list;
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
            System.err.println("Orgofficename error---------" + ex);
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
            System.err.println("getIdOfParent error---------" + ex);
        }

        return id;
    }
   
    
    public int getOrg_Office_id(String organisation_name) {
        String query = "SELECT org_office_id FROM org_office WHERE org_office_name = '"+organisation_name+"' and active='Y' ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setString(1, organisation_name);
//            pstmt.setString(2, "Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("org_office_id");
        } catch (Exception e) {
            System.out.println("Error: v--" + e);
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
            System.out.println("Error: getParentGeneration--" + e);
        }
        return organisation_id;
    }

    public int insertRecord(Org_Office orgOffice) {
        String is_child = "", active = "";
        int rowsAffected = 0;
        int count = 0;
        is_child = orgOffice.getSuperp();
        String p_org_office = orgOffice.getSerialnumber();
        // active = orgOffice.getIsActive();
        int key = 0;

        if (is_child != null) {
            if (is_child.equals("yes") || is_child.equals("Yes") || is_child.equals("YES") || is_child.equals("Y") || is_child.equals("y")) {
                is_child = "Y";
            } else {
                is_child = "N";
            }
        }

        int orgid = orgOffice.getOrganisation_id();
        int org_officeid1 = getOrg_Office_id(orgOffice.getOrg_office_name());
        int org_officeid2 = getOrg_Office_id(orgOffice.getP_org());
        int generation = 0;
        if (org_officeid2 == 0) {
            generation = 1;
        } else {

            generation = getParentGeneration(orgid, org_officeid2) + 1;

        }
        if (org_officeid1 != 0) {
            if (org_officeid1 == org_officeid2) {
                message = "Sorry! Parent-Child cannot be same!";
                msgBgColor = COLOR_ERROR;
                return rowsAffected;
            }
        }
        // to check if parent exist or not
        String qry2 = "select count(*) from org_office where organisation_id='" + orgid + "' and "
                + " org_office_id='" + org_officeid1 + "' and active='Y' ";
        try {
            PreparedStatement pst1 = connection.prepareStatement(qry2);
            System.out.println("query for check -" + pst1);
            ResultSet rst1 = pst1.executeQuery();
            while (rst1.next()) {
                count = rst1.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("error in insertRecord model -" + e);
        }
        if (count > 0) {
            message = "Cannot save the record, already mapped!";
            msgBgColor = COLOR_ERROR;
            return rowsAffected;
        }

        //
        String query1 = "select count(*) "
                + " from org_office where organisation_id='" + orgid + "' "
                + " and org_office_id='" + org_officeid1 + "' and "
                + " parent_org_office_id='" + org_officeid2 + "' and active='Y' ";

        try {
            PreparedStatement pst = connection.prepareStatement(query1);
            System.out.println("query for check -" + pst);
            ResultSet rst = pst.executeQuery();
            while (rst.next()) {
                count = rst.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("error in insertRecord model -" + e);
        }
        if (count > 0) {
            message = "Cannot save the record, already mapped!";
            msgBgColor = COLOR_ERROR;
            return rowsAffected;
        }

        String query = "INSERT INTO "
                + "org_office( org_office_name,org_office_code,office_type_id, address_line1, "
                + "address_line2, address_line3, city_id, email_id1, email_id2, mobile_no1, mobile_no2, "
                + "landline_no1, landline_no2, landline_no3,revision_no,active,remark,organisation_id,parent_org_office_id,super,generation,latitude,longitude) "
                + "VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?)";
        //  int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            //   pstmt.setInt(1, orgOffice.getOrganisation_id());
            pstmt.setString(1, (orgOffice.getOrg_office_name()));
            pstmt.setString(2, orgOffice.getOrg_office_code());
            pstmt.setInt(3, orgOffice.getOffice_type_id());
            pstmt.setString(4, (orgOffice.getAddress_line1()));
            pstmt.setString(5, (orgOffice.getAddress_line2()));
            pstmt.setString(6, (orgOffice.getAddress_line3()));
            pstmt.setInt(7, orgOffice.getCity_id());
            pstmt.setString(8, orgOffice.getEmail_id1());
            pstmt.setString(9, orgOffice.getEmail_id2());
            pstmt.setString(10, orgOffice.getMobile_no1());
            pstmt.setString(11, orgOffice.getMobile_no2());
            pstmt.setString(12, orgOffice.getLandline_no1());
            pstmt.setString(13, orgOffice.getLandline_no2());
            pstmt.setString(14, orgOffice.getLandline_no3());
            pstmt.setInt(15, orgOffice.getRevision_no());
            pstmt.setString(16, "Y");
            pstmt.setString(17, "ok");
            pstmt.setInt(18, orgOffice.getOrganisation_id());

            pstmt.setInt(19, org_officeid2);
            pstmt.setString(20, is_child);
            pstmt.setInt(21, generation);
            pstmt.setString(22, orgOffice.getLatitude());
            pstmt.setString(23, orgOffice.getLongitude());

            rowsAffected = pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs != null && rs.next()) {
                key = rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("Error: organisation---insertRecord" + e);
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

    public int updateRecord(Org_Office orgOffice, int org_office_id) {
        int revision = OrgOfficeModel.getRevisionno(orgOffice, org_office_id);
        String oldserial, oldsuper;
        String newserial = orgOffice.getSerialnumber();
        String newsuper = orgOffice.getSuperp();
        String superstatus = orgOffice.getSuperp();
        String serialnumber = orgOffice.getSerialnumber();
        int key = orgOffice.getOrg_office_id();
        int updateRowsAffected = 0;
        boolean status = false;
        String query1 = "SELECT max(revision_no),serial_no,super  FROM org_office WHERE org_office_id = " + org_office_id + "  && active=? ";
        String query2 = "UPDATE org_office SET active=? WHERE org_office_id=? and revision_no=?";
        String query3 = "INSERT INTO "
                + "org_office(org_office_id,org_office_name,org_office_code,office_type_id, address_line1, "
                + "address_line2, address_line3, city_id, email_id1, email_id2, mobile_no1, mobile_no2, "
                + "landline_no1, landline_no2, landline_no3,revision_no,active,remark,organisation_id,serial_no,super) "
                + "VALUES(?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?)";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
//           pstmt.setInt(1,organisation_type_id);
            pstmt.setString(1, "Y");

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                PreparedStatement pstm = connection.prepareStatement(query2);
                revision = rs.getInt("max(revision_no)");
                oldserial = rs.getString("serial_no");
                oldsuper = rs.getString("super");

                pstm.setString(1, "n");

                pstm.setInt(2, org_office_id);
                pstm.setInt(3, revision);
                updateRowsAffected = pstm.executeUpdate();
                if (updateRowsAffected >= 1) {
                    revision = revision + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                    psmt.setInt(1, orgOffice.getOrg_office_id());
                    psmt.setString(2, (orgOffice.getOrg_office_name()));
                    psmt.setString(3, orgOffice.getOrg_office_code());
                    psmt.setInt(4, orgOffice.getOffice_type_id());
                    psmt.setString(5, (orgOffice.getAddress_line1()));
                    psmt.setString(6, (orgOffice.getAddress_line2()));
                    psmt.setString(7, (orgOffice.getAddress_line3()));
                    psmt.setInt(8, orgOffice.getCity_id());
                    psmt.setString(9, orgOffice.getEmail_id1());
                    psmt.setString(10, orgOffice.getEmail_id2());
                    psmt.setString(11, orgOffice.getMobile_no1());
                    psmt.setString(12, orgOffice.getMobile_no2());
                    psmt.setString(13, orgOffice.getLandline_no1());
                    psmt.setString(14, orgOffice.getLandline_no2());
                    psmt.setString(15, orgOffice.getLandline_no3());
                    psmt.setInt(16, revision);
                    psmt.setString(17, "Y");
                    psmt.setString(18, "OK");
                    psmt.setInt(19, orgOffice.getOrganisation_id());
                    psmt.setString(20, orgOffice.getSerialnumber());
                    psmt.setString(21, orgOffice.getSuperp());

                    rowsAffected = psmt.executeUpdate();
                    if (rowsAffected > 0) {
                        status = true;

                        //delete record
                        if (!(oldserial.equals(newserial) && oldsuper.equals(newsuper))) {
                            String querydelete = "delete from org_office_hierarchy where serial_no=? and active=? ";

                            PreparedStatement pstmt4 = connection.prepareStatement(querydelete);
                            pstmt4.setString(1, oldserial);
                            pstmt4.setString(2, "Y");

                            int deletedrows = pstmt4.executeUpdate();

                            if (deletedrows > 0) {

                                if (superstatus.equals("yes")) {
                                    String queryinsert = "INSERT INTO  org_office_hierarchy(org_office_id,parent_id,active,serial_no) values(?,?,?,?) ";
                                    int rowsAffected2 = 0;
                                    try {
                                        PreparedStatement pstmt6 = connection.prepareStatement(queryinsert);

                                        pstmt6.setInt(1, key);
                                        pstmt6.setString(3, "Y");
                                        pstmt6.setString(4, serialnumber);
                                        if (serialnumber.contains(".")) {
                                            //set for super child
                                            int lst = serialnumber.lastIndexOf(".");
                                            String parentsrno = serialnumber.substring(0, lst);
                                            int parentid = getIdOfParent(parentsrno);
                                            pstmt6.setInt(2, parentid);
                                        } else {
                                            //set for super parent
                                            pstmt6.setInt(2, 0);

                                        }

                                        rowsAffected2 = pstmt6.executeUpdate();

                                        if (rowsAffected2 > 0) {
                                            message = "Record saved successfully.";
                                            msgBgColor = COLOR_OK;
                                        } else {
                                            message = "Cannot save the record, some error.";
                                            msgBgColor = COLOR_ERROR;
                                        }

                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }

                                } else {

                                    //code for non super
                                    String queryinsert1 = "INSERT INTO  org_office_hierarchy(org_office_id,parent_id,active,serial_no) values(?,?,?,?) ";
                                    int rowsAffected2 = 0;
                                    try {
                                        PreparedStatement pstmt7 = connection.prepareStatement(queryinsert1);

                                        pstmt7.setInt(1, key);
                                        pstmt7.setString(3, "Y");
                                        pstmt7.setString(4, serialnumber);

                                        //set for super child
                                        int lst = serialnumber.lastIndexOf(".");
                                        String parentsrno = serialnumber.substring(0, lst);
                                        int parentid = getIdOfParent(parentsrno);
                                        pstmt7.setInt(2, parentid);

                                        //set for super parent
                                        rowsAffected2 = pstmt7.executeUpdate();

                                        if (rowsAffected2 > 0) {
                                            message = "Record saved successfully.";
                                            msgBgColor = COLOR_OK;
                                        } else {
                                            message = "Cannot save the record, some error.";
                                            msgBgColor = COLOR_ERROR;
                                        }

                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }

                                }

                            }
                        }

                    } //end if
                    else {
                        status = false;
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("Error: updateRecord---updateRecord" + e);
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
            System.out.println("Error: getOrgofficeid--" + e);
        }
        return organisation_id;
    }
    
    //new update
    public int updateRecordd(Org_Office orgOffice, int org_office_id) throws SQLException {
        int revision = OrgOfficeModel.getRevisionno(orgOffice, org_office_id);
        int officetypeid = getOfficetype_id(orgOffice.getOffice_type());
        int Orgid2 = 0;
        int Orgoffid = getOrgofficeid(orgOffice.getOrg_office_name());
        String oldserial, oldsuper;
        String newserial = orgOffice.getSerialnumber();
        String newsuper = orgOffice.getSuperp();
        String superstatus = orgOffice.getSuperp();
        String serialnumber = orgOffice.getSerialnumber();
        //  int key = orgOffice.getId();
        int updateRowsAffected = 0;
        int rowsAffected = 0;
        int count = 0;
        int count1 = 0;
        String is_child = "", active = "", prev_is_child = "";
        int prev_mapId1 = 0;
        is_child = orgOffice.getSuperp().trim();
        if (is_child != null) {
            if (is_child.equals("yes") || is_child.equals("Yes") || is_child.equals("YES") || is_child.equals("Y") || is_child.equals("y")) {
                is_child = "Y";
            } else {
                is_child = "N";
            }
        }
        // int org_desig_id = orgOffice.getId();
        int orgid = getOrganisation_id(orgOffice.getOrganisation_name());
        int orgid1 = getOrg_Office_id(orgOffice.getOrg_office_name());
        Orgid2 = getOrg_Office_id(orgOffice.getP_org());
        int generation = 0;
        if (Orgid2 == 0) {
            generation = 1;
        } else {

            generation = getParentGeneration(orgid, Orgid2) + 1;

        }
        if (orgid1 != 0) {
            if (orgid1 == Orgid2) {
                message = "Sorry! Parent-Child cannot be same!";
                msgBgColor = COLOR_ERROR;
                return rowsAffected;
            }
        }
        // to check if child present or not for update logic
        String qry3 = "select count(*),org_office_id from org_office where "
                + " org_office_id='" + Orgoffid + "' and organisation_id='" + orgid + "'  "
                //+ " organisation_id='" + orgid + "' and organisation_designation_map_id_2='"+prev_mapId1+"' "
                + " and active='Y' ";
        try {
            PreparedStatement pst2 = connection.prepareStatement(qry3);
            System.out.println("query for check -" + pst2);
            ResultSet rst2 = pst2.executeQuery();
            while (rst2.next()) {
                count = rst2.getInt(1);
                prev_mapId1 = rst2.getInt(2);

            }
        } catch (Exception e) {
            System.out.println("error in updateRecordd model -" + e);
        }
        if (prev_mapId1 != Orgoffid) {
            int c = 0;
            String query3 = "select count(*),org_office_id from org_office where "
                    //+ " organisation_designation_id='" + org_desig_id + "' and organisation_id='" + orgid + "'  "
                    + " organisation_id='" + orgid + "' and parent_org_office_id='" + prev_mapId1 + "' "
                    + " and active='Y' ";
            try {
                PreparedStatement pst3 = connection.prepareStatement(query3);
                System.out.println("query for check -" + pst3);
                ResultSet rst3 = pst3.executeQuery();
                while (rst3.next()) {
                    c = rst3.getInt(1);
                    prev_mapId1 = rst3.getInt(2);

                }
            } catch (Exception e) {
                System.out.println("error in v model -" + e);
            }

            if (c > 0) {
                message = "Cannot save the record, already mapped!";
                msgBgColor = COLOR_ERROR;
                return rowsAffected;
            }
        }

        // to check if child exist or not for duplicate entry of child
        String qry2 = "select count(*) from org_office where organisation_id='" + orgid + "' and "
                + " org_office_id='" + Orgoffid + "' and active='Y' ";
        try {
            PreparedStatement pst1 = connection.prepareStatement(qry2);
            System.out.println("query for check -" + pst1);
            ResultSet rst1 = pst1.executeQuery();
            while (rst1.next()) {
                count = rst1.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("error in updateRecordd model -" + e);
        }
        if (count > 1) {
            message = "Cannot save the record, already existed!";
            msgBgColor = COLOR_ERROR;
            return rowsAffected;
        }

        boolean status = false;

        // to check child - parent mapping
        //int count = 0;
        String qry = "select count(*) from org_office "
                + " where organisation_id='" + orgid + "' and "
                + " parent_org_office_id='" + Orgid2 + "' and active='Y' ";

        String qry1 = "select super from org_office where org_office_id='" + orgid1 + "'  "
                + " and active='Y' ";

        //
        //String query1 = "SELECT max(revision),serial_no,super  FROM organisation_designation_map WHERE organisation_designation_map_id = " + org_office_id + "  && active=? ";
        String query1 = "SELECT max(revision_no),super FROM org_office WHERE org_office_id = " + org_office_id + "  && active='Y' ";
        //String query2 = "UPDATE organisation_designation_map SET active=? WHERE organisation_designation_map_id=? and revision=?";
        String query2 = "UPDATE org_office SET active=? WHERE org_office_id=? and revision_no=?";

//        String query3 = "INSERT INTO "
//                + "organisation_designation_map(organisation_designation_map_id,organisation_id,designation_id,serial_no, super,revision,active,parent_designation) "
//                + "VALUES(?,?,?,?,?,?,?,?)";
        String query3 = "INSERT INTO "
                + "org_office(org_office_id,org_office_name,org_office_code,office_type_id, address_line1, "
                + "address_line2, address_line3, city_id, email_id1, email_id2, mobile_no1, mobile_no2, "
                + "landline_no1, landline_no2, landline_no3,revision_no,active,remark,organisation_id,parent_org_office_id,super,generation,latitude,longitude) "
                + "VALUES(?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?)";

        //int rowsAffected = 0;
        try {
            connection.setAutoCommit(false);

            // for child-parent mapping
            PreparedStatement pst1 = connection.prepareStatement(qry1);
            ResultSet rst1 = pst1.executeQuery();
            while (rst1.next()) {
                prev_is_child = rst1.getString(1);
            }
            if (!is_child.equals(prev_is_child)) {

                PreparedStatement pst = connection.prepareStatement(qry);
                ResultSet rst = pst.executeQuery();
                if (rst.next()) {
                    count = rst.getInt(1);
                }

            }

            if (count <= 1 || count1 >= 1) {

                PreparedStatement pstmt = connection.prepareStatement(query1);
//           pstmt.setInt(1,organisation_type_id);
                //pstmt.setString(1, "Y");

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    revision = rs.getInt("max(revision_no)");
                    oldsuper = rs.getString("super");

                    PreparedStatement pstm = connection.prepareStatement(query2);
                    pstm.setString(1, "N");

                    pstm.setInt(2, org_office_id);
                    pstm.setInt(3, revision);
                    updateRowsAffected = pstm.executeUpdate();
                    if (updateRowsAffected >= 1) {
                        revision = rs.getInt("max(revision_no)") + 1;
                        PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);

                        psmt.setInt(1, orgOffice.getOrg_office_id());
                        psmt.setString(2, (orgOffice.getOrg_office_name()));
                        psmt.setString(3, orgOffice.getOrg_office_code());
                        psmt.setInt(4, officetypeid);
                        psmt.setString(5, (orgOffice.getAddress_line1()));
                        psmt.setString(6, (orgOffice.getAddress_line2()));
                        psmt.setString(7, (orgOffice.getAddress_line3()));
                        psmt.setInt(8, orgOffice.getCity_id());
                        psmt.setString(9, orgOffice.getEmail_id1());
                        psmt.setString(10, orgOffice.getEmail_id2());
                        psmt.setString(11, orgOffice.getMobile_no1());
                        psmt.setString(12, orgOffice.getMobile_no2());
                        psmt.setString(13, orgOffice.getLandline_no1());
                        psmt.setString(14, orgOffice.getLandline_no2());
                        psmt.setString(15, orgOffice.getLandline_no3());
                        psmt.setInt(16, revision);
                        psmt.setString(17, "Y");
                        psmt.setString(18, "OK");
                        psmt.setInt(19, orgOffice.getOrganisation_id());
                        psmt.setInt(20, Orgid2);
                        psmt.setString(21, orgOffice.getSuperp());
                        psmt.setInt(22, generation);
                        psmt.setString(23, orgOffice.getLatitude());
                        psmt.setString(24, orgOffice.getLongitude());
                        //pstmt.setString(9, "0");
                        System.out.println("insert query -" + psmt);
                        rowsAffected = psmt.executeUpdate();
                        if (rowsAffected > 0) {
                            status = true;

                            updateallRecorf(orgid);

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

                }

            } else {
                message = "Key already mapped with some child!";
                msgBgColor = COLOR_ERROR;
            }

        } catch (Exception e) {
            System.out.println("Error: updateRecordd---updateRecord" + e);
        } finally {

        }

//        if (rowsAffected > 0) {
//            message = "Record updated successfully.";
//            msgBgColor = COLOR_OK;
//        } else {
//            message = "Cannot update the record, some error.";
//            msgBgColor = COLOR_ERROR;
//        }
        return rowsAffected;
    }

    public Boolean updateallRecorf(int id) throws SQLException {
        Boolean status = false;

        String querynw = "select * from org_office where organisation_id=?  and active=?  order by generation";
        PreparedStatement psmtnw = (PreparedStatement) connection.prepareStatement(querynw);
        psmtnw.setInt(1, id);

        psmtnw.setString(2, "Y");

        ResultSet rstnw = psmtnw.executeQuery();
        while (rstnw.next()) {
            int generation = 0;
            int selfdesig = rstnw.getInt("org_office_id");
            int desig = rstnw.getInt("parent_org_office_id");

            if (desig == 0) {

                generation = 1;
            } else {
                generation = getParentGeneration(id, desig) + 1;

            }

            String querynw2 = "update org_office SET generation=?  where organisation_id=?  and org_office_id=? and active=?  order by generation";
            PreparedStatement psmtnw2 = (PreparedStatement) connection.prepareStatement(querynw2);
            psmtnw2.setInt(1, generation);
            psmtnw2.setInt(2, id);
            psmtnw2.setInt(3, selfdesig);
            psmtnw2.setString(4, "Y");

            int n = psmtnw2.executeUpdate();

        }

        return status;
    }

    //all in one
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
            System.out.println("Error: getMobilevalidty-" + e);
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
            System.err.println("getRevisionno error------------" + e);
        }
        return revision;
    }
//    public int deleteRecord(int org_office_id) {
//        String query = "DELETE FROM org_office WHERE org_office_id = "+org_office_id;
//        int rowsAffected = 0;
//        try {
//            rowsAffected = connection.prepareStatement(query).executeUpdate();
//        } catch (Exception e) {
//            System.out.println("Error: " + e);
//        }
//        if (rowsAffected > 0) {
//            message = "Record deleted successfully.";
//            msgBgColor = COLOR_OK;
//        } else {
//            message = "Cannot delete the record, some error.";
//            msgBgColor = COLOR_ERROR;
//        }
//        return rowsAffected;
//    }

    public int deleteRecord(int org_office_id) throws SQLException {

        int org_id = 0, org_map1 = 0, org_map2 = 0, count = 0;
        int rowsAffected = 0;
        PreparedStatement psmt;
        ResultSet rst;
        String query = "select organisation_id,org_office_id,parent_org_office_id "
                + " from org_office where org_office_id='" + org_office_id + "' "
                + " and active='Y' ";

        try {
            psmt = connection.prepareStatement(query);
            rst = psmt.executeQuery();
            while (rst.next()) {
                org_id = rst.getInt(1);
                org_map1 = rst.getInt(2);
                org_map2 = rst.getInt(3);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            psmt = null;
            rst = null;
            rowsAffected = 0;
            query = null;
        }
        query = "select count(*) from org_office where "
                + " organisation_id='" + org_id + "' and parent_org_office_id='" + org_map1 + "' and active='Y' ";
        try {
            psmt = connection.prepareStatement(query);
            rst = psmt.executeQuery();
            while (rst.next()) {
                count = rst.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            psmt = null;
            rst = null;
            rowsAffected = 0;
            query = null;
        }

        if (count > 0) {
            message = "Sorry!, cannot delete because child already mapped as Parent!";
            msgBgColor = COLOR_ERROR;

            return rowsAffected;
        }

//        if (rowsAffected > 0) {
//            message = "Record deleted successfully.";
//            msgBgColor = COLOR_OK;
//        } else {
//            message = "Cannot delete the record, some error.";
//            msgBgColor = COLOR_ERROR;
//        }
        query = "DELETE FROM org_office WHERE org_office_id = '" + org_office_id + "' and "
                + " active='Y' ";
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
            System.out.println("getPointLatLong error-------" + e);
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
            System.out.println("Error:OrganisationMapModel--organisation_Name()-- " + e);
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
            System.out.println("getHierarchsearch ERROR - " + e);
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
            System.out.println("Error:OrganisationMapModel--searchMobile()-- " + e);
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
            System.out.println("Error:OrganisationMapModel--getOrganisation_Name()-- " + e);
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
            System.out.println("Error:OrganisationMapModel--getGeneration()-- " + e);
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
            System.out.println("Error: getOrganisation_id--" + e);
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
            System.out.println("Error: getOfficetype_id--" + e);
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
            System.out.println("Error:OrganisationMapModel--OrgOfficeType-- " + e);
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
            System.out.println("Error:OrganisationMapModel--OrgOfficeType-- " + e);
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
            System.out.println("Error:OrganisationMapModel--OrgOfficeType-- " + e);
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
            System.out.println("getDesgn_id Error: " + e);
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
            System.out.println("get paernt -" + query);
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
            System.out.println("getParentOrgOffice Error: " + e);
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
            System.out.println("Error:OrganisationMapModel--getOrgOfficeNameSearch-- " + e);
        }
        return list;
    }

    public List<String> getOrgOfficeCodeSearch(String q, String code) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT oo.org_office_code FROM org_office as oo where oo.active='Y'"
                + " AND if('" + code + "' = '' , oo.org_office_name like '%%' , oo.org_office_name = ? ) ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            int count = 0;
            pstmt.setString(1, code);
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
            System.out.println("Error:OrganisationMapModel--getOrgOfficeCodeSearch-- " + e);
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
            System.out.println("Error: getOrgOfficeType_id--" + e);
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
            System.out.println("getCity_id Error: " + e);
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
            System.out.println("getCityName Error: " + e);
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
            System.out.println("getStateName Error: " + e);
        }
        return list;
    }

    public byte[] generateSiteList(String jrxmlFilePath, List listAll) {
        byte[] reportInbytes = null;
        HashMap mymap = new HashMap();
        try {
            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, beanColDataSource);
        } catch (Exception e) {
            System.out.println("Error: in OrganisationNameModel generatReport() JRException: " + e);
        }
        return reportInbytes;
    }

//    public byte[] generateOrganisationOfficeList(String jrxmlFilePath) {
//        byte[] reportInbytes = null;
//        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
//        HashMap mymap = new HashMap();
//        mymap.put("org_id", organisation_id);
//        Connection con = connection;
//        try {
//            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
//            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, mymap, con);
////            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, mymap, con);
////            JRPdfExporter export = new JRPdfExporter();
////            JRPdfSaveContributor pdf = new JRPdfSaveContributor(Locale.ENGLISH, null);
////            pdf.save(jasperPrint, new File("E:\\NetBeansProjects\\TrafficPoliceNew/Office_report.pdf"));////////////
////            export.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
////            export.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArray);
////            export.exportReport();
//        } catch (Exception e) {
//            System.out.println("Error: in OrgOfficeModel generatReport() JRException: " + e);
//        }
//        return reportInbytes;
//    }
    public ByteArrayOutputStream generateOrganisationOfficeExcelList(String jrxmlFilePath, int organisation_id) {
        ByteArrayOutputStream reportInbytes = new ByteArrayOutputStream();
        HashMap mymap = new HashMap();
        mymap.put("org_id", organisation_id);
        Connection con = connection;
        try {
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            //reportInbytes = JasperRunManager.runReportToPdf(compiledReport, mymap, con);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, mymap, con);
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, reportInbytes);
            exporter.exportReport();
        } catch (Exception e) {
            System.out.println("Error: in OrgOfficeModel generatReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public byte[] generateofficeAddressList(String jrxmlFilePath, int org_office_id) {
        byte[] reportInbytes = null;
        HashMap mymap = new HashMap();
        mymap.put("org_office_id", org_office_id);
        Connection con = connection;
        try {
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, mymap, con);
        } catch (Exception e) {
            System.out.println("Error: in OrgOfficeModel generatReport() JRException: " + e);
        }
        return reportInbytes;
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
