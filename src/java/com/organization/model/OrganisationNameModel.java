package com.organization.model;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.organization.tableClasses.OrganisationNameBean;

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

/**
 *
 * @author Vikrant
 */
public class OrganisationNameModel {

    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "green";
    private final String COLOR_ERROR = "red";

    public void setConnection() {
        try {
            Class.forName(driverClass);
            connection = (Connection) DriverManager.getConnection(connectionString, db_username, db_password);
        } catch (Exception e) {
            System.out.println("CommandModel setConnection() Error: " + e);
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
            System.out.println("OrganisationNameModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
    }

    public List<OrganisationNameBean> showData() {
        List<OrganisationNameBean> list = new ArrayList<OrganisationNameBean>();

        String query = " select * from organisation_name where active='Y' order by organisation_id desc ";
        try {
            //System.out.println("query  -" + query2);
            int count = 0;
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                count++;
                OrganisationNameBean bean = new OrganisationNameBean();
                bean.setOrganisation_id(rset.getInt(1));
                bean.setOrganisation_name(rset.getString(2));
                bean.setDescription(rset.getString(3));
                bean.setOrganisation_code(rset.getString(11));
                list.add(bean);
                //list.add(deviceBean2);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public int saveData(OrganisationNameBean bean) {
        int result = 0;
        PreparedStatement psmt;
        int count = 0;
        try {
            connection.setAutoCommit(true);
            int i = 0;
            String query = "insert into organisation_name(organisation_name,description,organisation_type_id,created_by,remark,organisation_code)"
                    + " values(?,?,?,?,?,?) ";
            psmt = connection.prepareStatement(query);
            psmt.setString(++i, bean.getOrganisation_name());
            psmt.setString(++i, bean.getDescription());
            psmt.setString(++i, bean.getOrganisation_type());
            psmt.setString(++i, "Vikrant");
            psmt.setString(++i, bean.getOrganisation_code());
            psmt.setString(++i, bean.getRemark());
            count = psmt.executeUpdate();
            if (count > 0) {
                message = "Record saved successfully.";
                msgBgColor = COLOR_OK;
                connection.commit();                
            }else{                
                message = "Record not saved.";
                msgBgColor = COLOR_ERROR;
            }

        } catch (Exception e) {

        }
        return result;
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
            System.out.println("Error: " + e);
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
            System.out.println("Error: " + e);
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
            System.out.println("Error: " + e);
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
            System.out.println("Error: " + e);
        }
        return name;
    }

    public JSONObject getselectedOrg(String q) {
        List<OrganisationNameBean> list = new ArrayList<OrganisationNameBean>();
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
            System.out.println("Error:OrganisationNameModel--getOrganationNameList()-- " + e);
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
            System.out.println("Error: " + e);
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
            System.out.println("Error: " + e);
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
            System.out.println("Error: " + e);
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
            System.out.println("Error: " + e);
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
            System.out.println("Error: " + e);
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
            System.out.println("Error: " + e);
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
            System.out.println("Error: " + e);
        }
        return name;
    }

    public JSONObject getselectedOffice(String q) {
        List<OrganisationNameBean> list = new ArrayList<OrganisationNameBean>();
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
            System.out.println("Error:OrganisationNameModel--getOrganationNameList()-- " + e);
        }
        return json;
    }

    public JSONObject getselectedPerson(String q) {
        List<OrganisationNameBean> list = new ArrayList<OrganisationNameBean>();
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
            System.out.println("Error:OrganisationNameModel--getOrganationNameList()-- " + e);
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
            System.out.println("Error:OrganisationNameModel--getOrganationNameList()-- " + e);
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
            System.out.println("Error:OrganisationNameModel--getOrganationNameList()-- " + e);
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
            System.out.println("Error:OrganisationNameModel--getOrganationNameList()-- " + e);
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
            System.out.println("Error:OrganisationNameModel--getOrganationNameList()-- " + e);
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

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Error inside closeConnection CommandModel:" + e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getDb_password() {
        return db_password;
    }

    public void setDb_password(String db_password) {
        this.db_password = db_password;
    }

    public String getDb_username() {
        return db_username;
    }

    public void setDb_username(String db_username) {
        this.db_username = db_username;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public void setMsgBgColor(String msgBgColor) {
        this.msgBgColor = msgBgColor;
    }
}
