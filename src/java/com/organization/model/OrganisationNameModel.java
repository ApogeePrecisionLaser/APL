package com.organization.model;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//import com.organization.tableClasses.AllinOne;
import com.organization.tableClasses.OrganisationName;
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

/**
 *
 * @author Vikrant
 */
public class OrganisationNameModel {

    private static Connection connection;

    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "#a2a220";
    private final String COLOR_ERROR = "red";

    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("OrganisationNameModel setConnection() Error: " + e);
        }
    }

    public int getNoOfRows(String active) {
        int noOfRows = 0;
        try {
            ResultSet rset = connection.prepareStatement("SELECT COUNT(*) FROM organisation_name where "
                    + " IF('" + active + "'='' , active LIKE '%%', active = '" + active + "') ").executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("OrganisationNameModel getNoOfRows() 1 Error: " + e);
        }
        return noOfRows;
    }

    public int getNoOfRows(String organisation_name, String active) {
        int noOfRows = 0;
        //organisation_name = krutiToUnicode.convert_to_unicode(organisation_name);
        try {
            String query = " select  COUNT(*) from organisation_name  where   organisation_name like ?  and "
                    + " IF('" + active + "'='' , active LIKE '%%', active = '" + active + "') ";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, organisation_name + '%');

            ResultSet rset = pstmt.executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("OrganisationNameModel getNoOfRows()2 Error: " + e);
        }
        return noOfRows;
    }

//    public List<OrganisationName> showData(int lowerLimit, int noOfRowsToDisplay, String org_name) {
//        List<OrganisationName> list = new ArrayList<OrganisationName>();
//        org_name = (org_name);
//        String query;
//        PreparedStatement pstmt = null;
//        // Use DESC or ASC for descending or ascending order respectively of fetched data.
//        try {
//            if (org_name == null || org_name.isEmpty()) {
//                query = "SELECT * FROM organisation_name  ORDER BY organisation_name "
//                        + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
//                pstmt = connection.prepareStatement(query);
//            } else {
//                query = "SELECT * FROM organisation_name where  organisation_name like ? ORDER BY organisation_name "
//                        + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
//                pstmt = connection.prepareStatement(query);
//                pstmt.setString(1, org_name + '%');
//            }
//            ResultSet rset = pstmt.executeQuery();
//            while (rset.next()) {
//                OrganisationName orgName = new OrganisationName();
//                orgName.setOrganisation_id(rset.getInt("organisation_id"));
//                orgName.setOrganisation_sub_type_id(rset.getString("organisation_name"));
//                orgName.setOrganisation_name((rset.getString("organisation_name")));
//                orgName.setDescription((rset.getString("description")));
//                list.add(orgName);
//            }
//        } catch (Exception e) {
//            System.out.println("OrganisationNameModel showData() Error: " + e);
//        }
//        return list;
//    }
    public List<OrganisationName> showData(String org_name, String searchOrgSubType) {
        List<OrganisationName> list = new ArrayList<OrganisationName>();

        if (org_name == null) {
            org_name = "";
        }
        if (searchOrgSubType == null) {
            searchOrgSubType = "";
        }

        String query = "select org.organisation_id,org.organisation_name,org.description,org.organisation_code,orgT.org_type_name "
                + " from organisation_name org, organisation_type orgT where orgT.organisation_type_id=org.organisation_type_id ";
        if (!org_name.equals("") && org_name != null) {
            query += " and org.organisation_name='" + org_name + "' ";
        }
        query += "  and orgT.active='Y' and org.active='y' order by org.organisation_id desc ";

        try {
            System.out.println("query----- -" + query);
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            System.err.println("connectipn---------" + connection);
            while (rset.next()) {
                OrganisationName organisationSubType = new OrganisationName();
                organisationSubType.setOrganisation_id(rset.getInt("organisation_id"));
                //organisationSubType.setOrganisation_type_id(rset.getInt("organisation_type_id"));
                //organisationSubType.setOrganisation_sub_type_id((rset.getInt("organisation_sub_type_id")));
                organisationSubType.setOrganisation_type((rset.getString("org_type_name")));
                organisationSubType.setOrganisation_name((rset.getString("organisation_name")));
                //organisationSubType.setOrganisation_sub_type_name((rset.getString("organisation_sub_type_name")));
                organisationSubType.setDescription(rset.getString("description"));
                organisationSubType.setOrganisation_code(rset.getString("organisation_code"));
                list.add(organisationSubType);
            }
        } catch (Exception e) {
            System.out.println("Error: OrganisationNameModel showData-" + e);
        }
        return list;
    }

    public List<OrganisationName> showPDF(String searchOrgType, String searchOrgSubType, String active) {
        List<OrganisationName> list = new ArrayList<OrganisationName>();
        searchOrgType = (searchOrgType);
        //   searchOrgSubType =(searchOrgSubType);
        // Use DESC or ASC for descending or ascending order respectively of fetched data.
//        String query =     "Select o.organisation_id,os.organisation_sub_type_id,ot.organisation_type_id,o.organisation_name," +
//                           "o.description,o.organisation_code,os.organisation_sub_type_name,ot.org_type_name from organisation_name o,organisation_sub_type os,organisation_type ot" +
//                          " where o.active='Y'  and o.organisation_sub_type_id=os.organisation_sub_type_id and " +
//                           " ot.organisation_type_id=os.organisation_type_id and ot.active='Y'"
//                   + "AND if('" + searchOrgType + "' = '', o.organisation_name like '%%', o.organisation_name like  '" + searchOrgType + "') "
//                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;

        String query = "select org.organisation_id,org.organisation_name,org.description,org.organisation_code,orgT.org_type_name "
                + " from organisation_name org, organisation_type orgT where orgT.organisation_type_id=org.organisation_type_id and"
                + " IF('" + active + "'='' ,org.active LIKE '%%',org.active = '" + active + "') ";
        if (!searchOrgType.equals("") && searchOrgType != null) {
            query += " and org.organisation_name='" + searchOrgType + "' ";
        }
        query += "  and orgT.active='Y' ";

        try {
            System.out.println("query -" + query);
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            System.out.println("org name show data -" + query);
            while (rset.next()) {
                OrganisationName organisationSubType = new OrganisationName();
                organisationSubType.setOrganisation_id(rset.getInt("organisation_id"));
                //organisationSubType.setOrganisation_type_id(rset.getInt("organisation_type_id"));
                //organisationSubType.setOrganisation_sub_type_id((rset.getInt("organisation_sub_type_id")));
                organisationSubType.setOrganisation_type((rset.getString("org_type_name")));
                organisationSubType.setOrganisation_name((rset.getString("organisation_name")));
                //organisationSubType.setOrganisation_sub_type_name((rset.getString("organisation_sub_type_name")));
                organisationSubType.setDescription(rset.getString("description"));
                organisationSubType.setOrganisation_code(rset.getString("organisation_code"));
                list.add(organisationSubType);
            }
        } catch (Exception e) {
            System.out.println("Error: OrganisationNameModel showPDF-" + e);
        }
        return list;
    }

    public int insertRecord(OrganisationName organisation_name) {
        String query = "INSERT INTO organisation_name(organisation_name,organisation_type_id,description,"
                + " revision_no,active,remark,organisation_code) VALUES(?,?,?,?,?,?,?) ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setInt(1,(organisation_name.getOrganisation_id()));
            pstmt.setString(1, (organisation_name.getOrganisation_name()));
            pstmt.setInt(2, (organisation_name.getOrganisation_type_id()));
            pstmt.setString(3, (organisation_name.getDescription()));
            pstmt.setInt(4, organisation_name.getRevision_no());
            pstmt.setString(5, "Y");
            pstmt.setString(6, "OK");
            pstmt.setString(7, organisation_name.getOrganisation_code());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("OrganisationNameModel insertRecord() Error: " + e);
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

    public int updateRecord(OrganisationName organisation_name, int organisation_id) {
        int revision = OrganisationNameModel.getRevisionno(organisation_name, organisation_id);
        int updateRowsAffected = 0;
        boolean status = false;
        String query1 = "SELECT max(revision_no) revision_no FROM organisation_name WHERE organisation_id = " + organisation_id + "  && active=? ";
        String query2 = "UPDATE organisation_name SET active=? WHERE organisation_id=? and revision_no=?";
        String query3 = "INSERT INTO organisation_name(organisation_id,organisation_name,organisation_type_id,description,revision_no,active,remark,organisation_code) VALUES(?,?,?,?,?,?,?,?) ";
        int rowsAffected = 0;
        try {
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement(query1);
//           pstmt.setInt(1,organisation_type_id);
            pstmt.setString(1, "Y");

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                PreparedStatement pstm = connection.prepareStatement(query2);

                pstm.setString(1, "n");

                pstm.setInt(2, organisation_id);
                pstm.setInt(3, revision);
                updateRowsAffected = pstm.executeUpdate();
                if (updateRowsAffected >= 1) {
                    revision = rs.getInt("revision_no") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                    psmt.setInt(1, (organisation_name.getOrganisation_id()));
                    psmt.setString(2, (organisation_name.getOrganisation_name()));
                    psmt.setInt(3, (organisation_name.getOrganisation_type_id()));
                    psmt.setString(4, (organisation_name.getDescription()));
                    psmt.setInt(5, revision);
                    psmt.setString(6, "Y");
                    psmt.setString(7, "OK");
                    psmt.setString(8, organisation_name.getOrganisation_code());
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
            System.out.println("OrganisationNameModel updateRecord() Error: " + e);
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

    public static int getRevisionno(OrganisationName organisationName, int organisation_id) {
        int revision = 0;
        try {

            String query = " SELECT max(revision_no) as revision_no FROM organisation_name WHERE organisation_id =" + organisation_id + "  && active='Y';";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
            System.out.println("OrganisationNameModel getRevisionno() Error: " + e);

        }
        return revision;
    }

    public int deleteRecord(int organisation_id) {
        //  int id = getOrganisationID(organisation_id);
        String query = "DELETE FROM organisation_name WHERE organisation_id = " + organisation_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("OrganisationNameModel deleteRecord() Error: " + e);
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

    public int getOrganisationID(String org_name) {
        org_name = (org_name);
        String query = "SELECT organisation_id FROM organisation_name WHERE organisation_name = ?";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, org_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            id = rset.getInt("organisation_id");
        } catch (Exception e) {
            System.out.println("OrganisationNameModel getOrganisationID Error: " + e);
        }
        return id;
    }

    public int getOrganisationTypeID(String org_type_name) {
        org_type_name = (org_type_name);
        String query = "SELECT organisation_type_id FROM organisation_type WHERE org_type_name = ?";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, org_type_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            id = rset.getInt("organisation_type_id");
        } catch (Exception e) {
            System.out.println("OrganisationNameModel getOrganisationTypeID Error: " + e);
        }
        return id;
    }

    public int getOrganisationSubTypeID(String org_sub_type_name) {
        org_sub_type_name = (org_sub_type_name);
        String query = "SELECT organisation_sub_type_id FROM organisation_sub_type WHERE organisation_sub_type_name = ?";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, org_sub_type_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            id = rset.getInt("organisation_sub_type_id");
        } catch (Exception e) {
            System.out.println("OrganisationNameModel getOrganisationSubTypeID Error: " + e);
        }
        return id;
    }

    public String getOrganisationTypeName(int org_type_name) {
        org_type_name = (org_type_name);
        String query = "SELECT org_type_name FROM organisation_type WHERE organisation_type_id = ? and active='Y'";
        String name = "";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, org_type_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            name = rset.getString("org_type_name");
        } catch (Exception e) {
            System.out.println("OrganisationNameModel getOrganisationTypeName Error: " + e);
        }
        return name;
    }

    public JSONObject getselectedOrg(String q) {
        List<OrganisationName> list = new ArrayList<OrganisationName>();
        JSONObject json = new JSONObject();
        String query = "SELECT * FROM organisation_name where organisation_name=?  AND active='Y' ";
        try {
            PreparedStatement ptst = connection.prepareStatement(query);
            ptst.setString(1, q);
            ResultSet rset = ptst.executeQuery();

            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                int id = rset.getInt("organisation_id");
                String OrgName = (rset.getString("organisation_name"));
                int typeid = rset.getInt("organisation_type_id");
                String orgtypename = getOrganisationTypeName(typeid);
                String orgcode = (rset.getString("organisation_code"));
                String desp = (rset.getString("description"));

                json.put("id", id);
                json.put("orgname", OrgName);
                json.put("orgtype", orgtypename);
                json.put("orgcode", orgcode);
                json.put("desc", desp);

            }
        } catch (Exception e) {
            System.out.println("Error:OrganisationNameModel--getselectedOrg()-- " + e);
        }
        return json;
    }

    public String getOfficeTypeName(int org_type_name) {
        org_type_name = (org_type_name);
        String query = "SELECT office_type FROM org_office_type WHERE office_type_id = ? and active='Y'";
        String name = "";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, org_type_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            name = rset.getString("office_type");
        } catch (Exception e) {
            System.out.println("OrganisationNameModel getOfficeTypeName Error: " + e);
        }
        return name;
    }

    public String getOrganisationName(int org_type_name) {
        org_type_name = (org_type_name);
        String query = "SELECT organisation_name FROM organisation_name WHERE organisation_id = ? and active='Y'";
        String name = "";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, org_type_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            name = rset.getString("organisation_name");
        } catch (Exception e) {
            System.out.println("OrganisationNameModel getOrganisationName Error: " + e);
        }
        return name;
    }

    public String getOfficeName(int org_type_name) {
        org_type_name = (org_type_name);
        String query = "SELECT org_office_name FROM org_office WHERE org_office_id = ? and active='Y'";
        String name = "";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, org_type_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            name = rset.getString("org_office_name");
        } catch (Exception e) {
            System.out.println("OrganisationNameModel getOfficeName Error: " + e);
        }
        return name;
    }

    public String getidtypename(int org_type_name) {
        org_type_name = (org_type_name);
        String query = "SELECT id_type FROM id_type WHERE id_type_id = ? and active='Y'";
        String name = "";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, org_type_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            name = rset.getString("id_type");
        } catch (Exception e) {
            System.out.println("OrganisationNameModel getidtypename Error: " + e);
        }
        return name;
    }

    public String getdesignationName(int org_type_name) {
        org_type_name = (org_type_name);
        String query = "SELECT designation FROM designation WHERE designation_id = ? and active='Y'";
        String name = "";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, org_type_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            name = rset.getString("designation");
        } catch (Exception e) {
            System.out.println("OrganisationNameModel getdesignationName Error: " + e);
        }
        return name;
    }

    public String getfamilykpid(int org_type_name) {
        org_type_name = (org_type_name);
        String query = "SELECT designation FROM designation WHERE designation_id = ? and active='Y'";
        String name = "";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, org_type_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            name = rset.getString("designation");
        } catch (Exception e) {
            System.out.println("OrganisationNameModel getfamilykpid Error: " + e);
        }
        return name;
    }

    public String getCityName(int id) {
        String name = "";
//        String query = "SELECT city_name FROM city AS c ,state AS s WHERE c.state_id=s.state_id AND s.state_name=? "
//                + "  ORDER BY city_name";
        String query = "SELECT city_name FROM city AS c where c.active='Y'and c.city_id=? ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //    pstmt.setString(1, krutiToUnicode.convert_to_unicode(state_name));
            pstmt.setInt(1, id);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {    // move cursor from BOR to valid record.
                name = (rset.getString("city_name"));

            }

        } catch (Exception e) {
            System.out.println("OrganisationNameModel getCityName Error: " + e);
        }
        return name;
    }

    public JSONObject getselectedOffice(String q) {
        List<OrganisationName> list = new ArrayList<OrganisationName>();
        JSONObject json = new JSONObject();
        String query = "SELECT * FROM org_office where org_office_name=?  AND active='Y' ";
        try {
            PreparedStatement ptst = connection.prepareStatement(query);
            ptst.setString(1, q);
            ResultSet rset = ptst.executeQuery();

            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                int id = rset.getInt("org_office_id");
                String OrgOfficeName = (rset.getString("org_office_name"));

                int offtypeid = rset.getInt("office_type_id");
                String offtyp = getOfficeTypeName(offtypeid);

                int orgid = rset.getInt("organisation_id");
                String orgname = getOrganisationName(orgid);

                int cityid = rset.getInt("city_id");
                String cityname = getCityName(cityid);

                int parentorgoffice = rset.getInt("parent_org_office_id");
                String parentoffname = getOfficeName(parentorgoffice);

                String address = (rset.getString("address_line1"));
                String email = (rset.getString("email_id1"));

                String mobilenumber = (rset.getString("mobile_no1"));
                String landline = (rset.getString("landline_no1"));
                String officecode = (rset.getString("org_office_code"));
                String generation = (rset.getString("generation"));
                String latitude = (rset.getString("latitude"));
                String longitude = (rset.getString("longitude"));

                json.put("id", id);
                json.put("orgname", orgname);
                json.put("offtype", offtyp);
                json.put("offcode", officecode);
                json.put("offname", OrgOfficeName);
                json.put("address", address);
                json.put("city", cityname);
                json.put("email", email);
                json.put("mobile", mobilenumber);
                json.put("landline", landline);
                json.put("latitude", latitude);
                json.put("longitude", longitude);
                json.put("serial", parentoffname);
                json.put("generation", generation);

            }
        } catch (Exception e) {
            System.out.println("Error:OrganisationNameModel--getselectedOffice()-- " + e);
        }
        return json;
    }

    public JSONObject getselectedPerson(String q) {
        List<OrganisationName> list = new ArrayList<OrganisationName>();
        JSONObject json = new JSONObject();
        String query = "SELECT * FROM key_person where key_person_name=?  AND active='Y' ";
        try {
            PreparedStatement ptst = connection.prepareStatement(query);
            ptst.setString(1, q);
            ResultSet rset = ptst.executeQuery();

            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                int id = rset.getInt("key_person_id");
                String personname = (rset.getString("key_person_name"));

                String OrgOfficeName = "";
                int offid = rset.getInt("org_office_id");
                if (offid != 0) {
                    OrgOfficeName = getOfficeName(offid);
                }

                String famOrgOfficeName = "";
                int famoffid = rset.getInt("family_office");
                if (famoffid != 0) {
                    famOrgOfficeName = getOfficeName(famoffid);
                }

                int cityid = rset.getInt("city_id");
                String cityname = getCityName(cityid);

                String latitude = (rset.getString("latitude"));
                String longitude = (rset.getString("longitude"));
                String address = (rset.getString("address_line1"));
                String mobilenumber = (rset.getString("mobile_no1"));
                String landline = (rset.getString("landline_no1"));
                String email = (rset.getString("email_id1"));
                String empcode = (rset.getString("emp_code"));
                String fathername = (rset.getString("father_name"));
                String dob = (rset.getString("date_of_birth"));
                String pass = (rset.getString("password"));
                String blood = (rset.getString("bloodgroup"));
                String emername = (rset.getString("emergency_contact_name"));
                String emernumber = (rset.getString("emergency_contact_mobile"));
                String gender = (rset.getString("gender"));
                String id_no = (rset.getString("id_no"));
                String relation = (rset.getString("relation"));

                String idtype = "";
                int idtypeid = rset.getInt("id_type_id");
                if (offid != 0) {
                    idtype = getidtypename(idtypeid);
                }

                String designation = "";
                int designationid = rset.getInt("designation_id");
                if (offid != 0) {
                    designation = getdesignationName(designationid);
                }

                String famdesignation = "";
                int famdesignationid = rset.getInt("family_designation");
                if (offid != 0) {
                    famdesignation = getdesignationName(famdesignationid);
                }

                json.put("id", id);
                json.put("offname", OrgOfficeName);
                json.put("emp", empcode);
                json.put("name", personname);
                json.put("password", pass);
                json.put("fathername", fathername);
                json.put("blood", blood);
                json.put("dob", dob);
                json.put("desig", designation);
                json.put("emername", emername);
                json.put("emernumber", emernumber);
                json.put("idtype", idtype);
                json.put("idnumber", id_no);
                json.put("gender", gender);
                json.put("address", address);
                json.put("city", cityname);
                json.put("mobile", mobilenumber);
                json.put("landline", landline);
                json.put("email", email);
                json.put("famoffice", famOrgOfficeName);
                json.put("famdesig", famdesignation);
                json.put("relation", relation);
                json.put("lat2", latitude);
                json.put("long2", longitude);

            }
        } catch (Exception e) {
            System.out.println("Error:OrganisationNameModel--getselectedPerson()-- " + e);
        }
        return json;
    }

    public List<String> getOrganisationTypeName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT org_type_name FROM organisation_type where organisation_type.active='Y' ORDER BY org_type_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String orgTypeName = (rset.getString("org_type_name"));
                if (orgTypeName.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(orgTypeName);
                    count++;
                }
                if (count == 0) {
                    list.add("No such Status exists.......");
                }
            }
        } catch (Exception e) {
            System.out.println("Error:OrganisationNameModel--getOrganisationTypeName()-- " + e);
        }
        return list;
    }

    public List<String> getOrganisationSubTypeName(String q, String organisation_type) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT distinct organisation_sub_type_name FROM organisation_sub_type,organisation_type where organisation_type.org_type_name='" + organisation_type + "' and organisation_sub_type.active='Y' ORDER BY organisation_sub_type_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String orgTypeName = (rset.getString("organisation_sub_type_name"));
                if (orgTypeName.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(orgTypeName);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println("Error:OrganisationNameModel--getOrganisationSubTypeName()-- " + e);
        }
        return list;
    }

    public List<String> getOrganisationName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT organisation_name FROM organisation_name where organisation_name.active='Y' ORDER BY organisation_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String orgTypeName = (rset.getString("organisation_name"));
                if (orgTypeName.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(orgTypeName);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println("Error:OrganisationNameModel--getOrganisationName()-- " + e);
        }
        return list;
    }

    public List<String> getOrganisationName(String q, String organisation_type, String organisation_sub_type) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT organisation_name FROM organisation_name ORDER BY organisation_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String orgTypeName = rset.getString("organisation_name");
                if (orgTypeName.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(orgTypeName);
                    count++;
                }
                if (count == 0) {
                    list.add("No such Status exists.......");
                }
            }
        } catch (Exception e) {
            System.out.println("Error:OrganisationNameModel--getOrganisationName()-- " + e);
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
            System.out.println("Error: in OrganisationNameModel generateSiteList() JRException: " + e);
        }
        return reportInbytes;
    }

    public ByteArrayOutputStream generateOrginisationXlsRecordList(String jrxmlFilePath) {
        ByteArrayOutputStream bytArray = new ByteArrayOutputStream();
        //  HashMap mymap = new HashMap();
        try {
            // JRBeanCollectionDataSource jrBean=new JRBeanCollectionDataSource(list);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, connection);
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, bytArray);
            exporter.exportReport();
        } catch (Exception e) {
            System.out.println("OrganisationNameModel generateOrginisationXlsRecordList() JRException: " + e);
        }
        return bytArray;
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
            System.out.println("OrganisationNameModel closeConnection() Error: " + e);
        }
    }
}
