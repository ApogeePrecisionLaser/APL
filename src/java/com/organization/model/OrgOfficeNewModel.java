/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.organization.model;

import com.lowagie.text.pdf.PdfWriter;
import com.organization.tableClasses.Org_Office;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
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
public class OrgOfficeNewModel {

    private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "lightyellow";
    private final String COLOR_ERROR = "red";

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

    public int getNoOfRows(String org_name, String office_code_search, String office_name_search) {
        int noOfRowNUM = 0;
        // int org_id = getOrganisation_id(org_name);
        try {
            String query = "  SELECT COUNT(*) as count "
                    + " FROM  org_office AS o LEFT JOIN org_office_type AS oft ON oft.office_type_id = o.office_type_id , "
                    + " city AS c, organisation_name AS org "
                    + " WHERE  org.organisation_id=o.organisation_id "
                    // + " AND o.city_id = c.city_id "
                    //  + " AND c.state_id = s.state_id "
                    + " AND IF ('" + org_name + "' = '' , organisation_name LIKE '%%',organisation_name= ?) "
                    //+ " AND IF (" + office_code_search + " = '' , o.org_office_code LIKE '%%',o.org_office_code LIKE  "+office_code_search+".%' OR o.org_office_code like ?) "
                    + " AND IF('" + office_code_search + "'='' ,o.org_office_code LIKE '%%',o.org_office_code LIKE '" + office_code_search + ".%' OR o.org_office_code like ?)"
                    + " AND IF ('" + office_name_search + "' = '' , o.org_office_name LIKE '%%',o.org_office_name = ?) "
                    + " ORDER BY org.organisation_name,o.org_office_name ";
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setString(1, org_name);
            psmt.setString(2, office_code_search);
            psmt.setString(3, office_name_search);
            ResultSet rset = psmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            noOfRowNUM = Integer.parseInt(rset.getString("count"));
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return noOfRowNUM;
    }

    public List<Org_Office> showData(int lowerLimit, int noOfRowsToDisplay, String org_name, String office_code_search, String office_name_search) {
        PreparedStatement pstmt;
        String query;
        List<Org_Office> list = new ArrayList<Org_Office>();
        try {
            // Use DESC or ASC for descending or ascending order respectively of fetched data.

            query = " select o.org_office_code,o.org_office_id ,o.org_office_name,og.organisation_name,c.city_name,oft.office_type, "
                    + " o.address_line1,o.address_line2, o.address_line3, o.email_id1, o.email_id2, o.mobile_no1, o.mobile_no2, o.landline_no1,"
                    + " o.landline_no2, o.landline_no3,o.serial_no,o.super "
                    + " FROM org_office o, organisation_name og, city c, org_office_type oft "
                    + " where o.organisation_id = og.organisation_id and c.city_id = o.city_id and oft.office_type_id = o.office_type_id and "
                    + " o.active='Y' and og.active='Y' and c.active='Y'"
                    + " AND IF ('" + org_name + "' = '' , organisation_name LIKE '%%',organisation_name= ?) "
                    //+ " AND IF (" + office_code_search + " = '' , o.org_office_code LIKE '%%',o.org_office_code LIKE  "+office_code_search+".%' OR o.org_office_code like ?) "
                    + " AND IF('" + office_code_search + "'='' ,o.org_office_code LIKE '%%',o.org_office_code LIKE '" + office_code_search + ".%' OR o.org_office_code like ?)"
                    + " AND IF ('" + office_name_search + "' = '' , o.org_office_name LIKE '%%',o.org_office_name = ?) "
                    + "LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, org_name);
            pstmt.setString(2, office_code_search);
            pstmt.setString(3, office_name_search);
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
                list.add(organisation);
            }
        } catch (Exception e) {
            System.out.println("Error:--organisation--- showData--" + e);
        }
        return list;
    }

    public int getIdOfParent(String number) {
        int id = 0;
        String query1 = "SELECT org_office_id FROM org_office WHERE serial_no =?  && active=? ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
            pstmt.setString(1, number);
            pstmt.setString(2, "Y");

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                id = rset.getInt("org_office_id");

            }

        } catch (Exception ex) {

        }

        return id;
    }

    public int insertRecord(Org_Office orgOffice) {
        String superstatus = orgOffice.getSuperp();
        String serialnumber = orgOffice.getSerialnumber();
        int key = 0;
        String query = "INSERT INTO "
                + "org_office( org_office_name,org_office_code,office_type_id, address_line1, "
                + "address_line2, address_line3, city_id, email_id1, email_id2, mobile_no1, mobile_no2, "
                + "landline_no1, landline_no2, landline_no3,revision_no,active,remark,organisation_id,serial_no,super) "
                + "VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?)";
        int rowsAffected = 0;
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

            pstmt.setString(19, serialnumber);
            pstmt.setString(20, superstatus);

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

            //insert
            if (superstatus.equals("yes")) {
                String query2 = "INSERT INTO  org_office_hierarchy(org_office_id,parent_id,active,serial_no) values(?,?,?,?) ";
                int rowsAffected2 = 0;
                try {
                    PreparedStatement pstmt2 = connection.prepareStatement(query2);

                    pstmt2.setInt(1, key);
                    pstmt2.setString(3, "Y");
                    pstmt2.setString(4, serialnumber);
                    if (serialnumber.contains(".")) {
                        //set for super child
                        int lst = serialnumber.lastIndexOf(".");
                        String parentsrno = serialnumber.substring(0, lst);
                        int parentid = getIdOfParent(parentsrno);
                        pstmt2.setInt(2, parentid);
                    } else {
                        //set for super parent
                        pstmt2.setInt(2, 0);

                    }

                    rowsAffected2 = pstmt2.executeUpdate();

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
                String query2 = "INSERT INTO  org_office_hierarchy(org_office_id,parent_id,active,serial_no) values(?,?,?,?) ";
                int rowsAffected2 = 0;
                try {
                    PreparedStatement pstmt2 = connection.prepareStatement(query2);

                    pstmt2.setInt(1, key);
                    pstmt2.setString(3, "Y");
                    pstmt2.setString(4, serialnumber);

                    //set for super child
                    int lst = serialnumber.lastIndexOf(".");
                    String parentsrno = serialnumber.substring(0, lst);
                    int parentid = getIdOfParent(parentsrno);
                    pstmt2.setInt(2, parentid);

                    //set for super parent
                    rowsAffected2 = pstmt2.executeUpdate();

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
            //insert finished 

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

    public static int getRevisionno(Org_Office orgOffice, int org_office_id) {
        int revision = 0;
        try {

            String query = " SELECT max(revision_no) as revision_no FROM organisation_type WHERE org_office_id =" + org_office_id + "  && active='Y';";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
        }
        return revision;
    }

    public int deleteRecord(int org_office_id) {
        String query = "DELETE FROM org_office WHERE org_office_id = " + org_office_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
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

    public List<String> organisation_Name(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT org.organisation_name FROM organisation_name  AS org where org.active='Y' ORDER BY organisation_name ";
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

    public List<String> getOrganisation_Name(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT org.organisation_name FROM organisation_name  AS org,org_office as oft where org.organisation_id=oft.organisation_id and org.active='Y' and oft.active='Y' ORDER BY organisation_name ";
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
            System.out.println("Error: OrganisationMapModel--" + e);
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
            System.out.println("Error:OrganisationMapModel--office code()-- " + e);
        }
        return list;
    }

    public List<String> getOrgOfficeNameSearch(String q, String org_name, String code) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT o.org_office_name FROM org_office AS o ,organisation_name as om "
                + " WHERE IF('" + org_name + "'='', om.organisation_name LIKE '%%' , om.organisation_name='" + org_name + "') "
                + " AND if('" + code + "' = '' , o.org_office_code like '%%' , o.org_office_code = ? ) "
                + "ORDER BY o.org_office_name";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setString(1, org_name);
            pstmt.setString(2, code);

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
                list.add("No Such Office Code Exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:OrganisationMapModel--office code()-- " + e);
        }
        return list;
    }

    public List<String> getOrgOfficeCodeSearch(String q, String code) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT oo.org_office_code FROM org_office as oo where oo.active='Y'"
                + " AND if('" + code + "' = '' , oo.org_office_code like '%%' , oo.org_office_code = ? ) ";
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
            System.out.println("Error:OrganisationMapModel--office code()-- " + e);
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
            System.out.println("Error: " + e);
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
            System.out.println("Error: " + e);
        }
        return list;
    }

    public byte[] generateOrganisationOfficeList(String jrxmlFilePath, int organisation_id) {
        byte[] reportInbytes = null;
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        HashMap mymap = new HashMap();
        mymap.put("org_id", organisation_id);
        Connection con = connection;
        try {
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, mymap, con);
//            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, mymap, con);
//            JRPdfExporter export = new JRPdfExporter();
//            JRPdfSaveContributor pdf = new JRPdfSaveContributor(Locale.ENGLISH, null);
//            pdf.save(jasperPrint, new File("E:\\NetBeansProjects\\TrafficPoliceNew/Office_report.pdf"));////////////
//            export.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//            export.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArray);
//            export.exportReport();
        } catch (Exception e) {
            System.out.println("Error: in OrgOfficeModel generatReport() JRException: " + e);
        }
        return reportInbytes;
    }

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
