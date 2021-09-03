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
            query = " select o.org_office_code,o.org_office_id ,o.org_office_name,og.organisation_name,c.city_name,oft.office_type, "
                    + " o.address_line1,o.address_line2, o.address_line3, o.email_id1, o.email_id2, o.mobile_no1, o.mobile_no2, o.landline_no1,"
                    + " o.landline_no2, o.landline_no3,o.serial_no,o.super,o.generation,o.parent_org_office_id,o.latitude,o.longitude,o.service_tax_reg_no"
                    + " FROM org_office o, organisation_name og, city c, org_office_type oft "
                    + " where o.organisation_id = og.organisation_id and c.city_id = o.city_id and oft.office_type_id = o.office_type_id and "
                    + "  og.active='Y' and c.active='Y' and o.active='Y' and oft.active='Y' ";

            if (!org_name.equals("") && org_name != null) {
                query += " and og.organisation_name='" + org_name + "' ";
            }
            if (!mobile.equals("") && mobile != null) {
                query += " and o.mobile_no1='" + mobile + "' ";
            }
            if (!office_code_search.equals("") && office_code_search != null) {
                query += " and o.org_office_code='" + office_code_search + "' ";
            }
//            if (!office_name_search.equals("") && office_name_search != null) {
//                query += " and o.org_office_name='" + office_name_search + "' ";
//            }
            if (!generation.equals("") && generation != null) {
                query += " and o.generation='" + generation + "' ";
            }

            if (!office_name_search.equals("")) {
                query += "and o.org_office_id in(" + idList.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ") ";
            }
            if (!office_name_search.equals("")) {
                query += " order by field(o.org_office_id," + idList.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ") ";

            } else {
                query += "order by generation ";

            }

            pstmt = connection.prepareStatement(query);

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
                organisation.setGst_number(rset.getString("service_tax_reg_no"));
                list.add(organisation);
            }
        } catch (Exception e) {
            System.out.println("Error:--OrgOfficeModel--- showData--" + e);
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
            System.out.println("OrgOfficeModel.getAllParentChild() -" + e);
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
            System.out.println("OrgOfficeModel.getAllParentChild() -" + e);
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
            System.out.println("Error:--organisation--- showData--" + e);
        }
        String qry2 = "select org_office_id from org_office where active='Y' and parent_org_office_id='" + org_office_id + "' ";
        try {
            PreparedStatement pst = connection.prepareStatement(qry2);
            ResultSet rstt = pst.executeQuery();
            while (rstt.next()) {
                list.add(rstt.getInt(1));
            }
        } catch (Exception e) {
            System.out.println("OrgOfficeModel.getAllParentChild() -" + e);
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
            System.out.println("Error: getOrgid--" + e);
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

    public int insertRecord(Org_Office orgOffice) throws SQLException {
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
//                return rowsAffected;
            }

        }

        String query1 = "select count(*) "
                + " from org_office where organisation_id='" + orgid + "' "
                + " and org_office_id='" + org_officeid1 + "' and "
                + " parent_org_office_id='" + org_officeid2 + "' and active='Y' ";
        try {
            PreparedStatement pst = connection.prepareStatement(query1);
            //  System.out.println("query for check -" + pst);
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
//            return rowsAffected;
        }
        String query = "INSERT INTO "
                + "org_office( org_office_name,org_office_code,office_type_id, address_line1, "
                + "address_line2, address_line3, city_id, email_id1, email_id2, mobile_no1, mobile_no2, "
                + "landline_no1, landline_no2, landline_no3,revision_no,active,remark,organisation_id,parent_org_office_id,super,"
                + "generation,latitude,longitude,service_tax_reg_no) "
                + "VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?)";
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
            pstmt.setString(24, orgOffice.getGst_number());

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

    public int updateRecordd(Org_Office orgOffice, int org_office_id) throws SQLException {
        int revision = OrgOfficeModel.getRevisionno(orgOffice, org_office_id);
        int officetypeid = getOfficetype_id(orgOffice.getOffice_type());
        int updateRowsAffected = 0;
        String is_child = orgOffice.getSuperp();
        if (is_child != null) {
            if (is_child.equals("yes") || is_child.equals("Yes") || is_child.equals("YES") || is_child.equals("Y") || is_child.equals("y")) {
                is_child = "Y";
            } else {
                is_child = "N";
            }
        }
        boolean status = false;
        int office_id = 0;

        int orgid = getOrganisation_id(orgOffice.getOrganisation_name());
        int orgoffice_id = getOrg_Office_id(orgOffice.getOrg_office_name());
        int p_prg_office_id = getOrg_Office_id(orgOffice.getP_org());

        int generation = 0;
        if (p_prg_office_id == 0) {
            generation = 1;
        } else {
            generation = getParentGeneration(orgid, p_prg_office_id) + 1;
        }

        String query1 = "SELECT max(revision_no) as revision_no FROM org_office WHERE org_office_id = " + org_office_id + "  && active='Y' ";
        String query2 = "UPDATE org_office SET active=? WHERE org_office_id=? and revision_no=? ";
        String query3 = "INSERT INTO "
                + " org_office(org_office_id,org_office_name,org_office_code,office_type_id, address_line1, "
                + " address_line2, address_line3, city_id, email_id1, email_id2, mobile_no1, mobile_no2, "
                + " landline_no1, landline_no2, landline_no3,revision_no,active,remark,organisation_id,parent_org_office_id,super, "
                + " generation,latitude,longitude,service_tax_reg_no) "
                + " VALUES(?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?) ";

        int rowsAffected = 0;
        try {
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement(query1);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                PreparedStatement pstm = connection.prepareStatement(query2);

                pstm.setString(1, "n");
                pstm.setInt(2, org_office_id);
                pstm.setInt(3, revision);
                updateRowsAffected = pstm.executeUpdate();
                if (updateRowsAffected >= 1) {
                    revision = rs.getInt("revision_no") + 1;
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
                    psmt.setInt(20, p_prg_office_id);
                    psmt.setString(21, orgOffice.getSuperp());
                    psmt.setInt(22, generation);
                    psmt.setString(23, orgOffice.getLatitude());
                    psmt.setString(24, orgOffice.getLongitude());
                    psmt.setString(25, orgOffice.getGst_number());

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
            System.out.println("ItemNameModel updateRecord() Error: " + e);
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

    public int deleteRecord(int org_office_id) throws SQLException {
        String query1 = " select count(*) as count from org_office where parent_org_office_id='" + org_office_id + "' ";

        String query = "DELETE FROM org_office WHERE org_office_id = " + org_office_id;
        int child_item_count = 0;
        int rowsAffected = 0;
        try {
            PreparedStatement psmt = connection.prepareStatement(query1);
            ResultSet rst = psmt.executeQuery();
            while (rst.next()) {
                child_item_count = rst.getInt("count");
            }
            if (child_item_count > 0) {
                message = "Can't delete because it's child items exists!..";
                msgBgColor = COLOR_ERROR;
            } else {
                PreparedStatement pstmt1 = connection.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
                pstmt1.executeUpdate();
                rowsAffected = connection.prepareStatement(query).executeUpdate();
            }

        } catch (Exception e) {
            System.out.println("OrgOfficeModel deleteRecord() Error: " + e);
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
