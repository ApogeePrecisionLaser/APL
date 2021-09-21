/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.organization.model;

import com.organization.tableClasses.EmergencyBean;
import com.organization.tableClasses.FormDataOfficeBean;
import com.organization.tableClasses.KeyPerson;
import com.organization.tableClasses.Org_Office;
import com.organization.tableClasses.OrganisationName;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author SoftTechinsert
 */
public class KeypersonModel {

    private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "#a2a220";
    private final String COLOR_ERROR = "red";
    private String connectionString;
    private String db_username;
    private String db_password;
    private String driverClass;
    List<String> list = new ArrayList<String>();

    public List<KeyPerson> showData(String designation, String person, String office_code, String searchEmpCode, String searchDesignation, String mobile, String active, String familysearch) {

        List<KeyPerson> list = new ArrayList<KeyPerson>();
        String query;
        byte[] imgData = null;
        //InputStream binaryStream = null;
        try {
//            // Use DESC or ASC for descending or ascending order respectively of fetched data.

//            
//         String   query2=" select k.key_person_id,ot.org_office_code ,oft.office_type,org.organisation_name, ot.org_office_name,k.key_person_name,d.designation," +
//                          "c.city_name,k.address_line1, k.address_line2,k.address_line3,k.image_path, k.mobile_no1," +
//                          "k.mobile_no2, k.landline_no1, k.landline_no2, k.email_id1, k.email_id2, k.salutation,k.emp_code, k.father_name, k.date_of_birth," +
//                          "k.id_no, k.id_type_id ,k.password ,k.bloodgroup,k.emergency_contact_name,k.emergency_contact_mobile,k.latitude,k.longitude,k.gender,k.relation,ot2.org_office_name as family_org_name,d2.designation  as family_designation " +
//                          "from key_person AS k " +
//                          "left join org_office AS ot  on k.org_office_id=ot.org_office_id " +
//                          "left join organisation_name AS org on ot.organisation_id = org.organisation_id " +
//                          "left join  org_office_type as oft on oft.office_type_id = ot.office_type_id " +
//                          "left join org_office AS ot2 on k.family_office=ot2.org_office_id " +
//                          "left join  designation as d2 on k.family_designation = d2.designation_id " +
//                          "left join  city AS c on k.city_id=c.city_id " +
//                          "left join  designation as d on k.designation_id = d.designation_id " +
//                          " where  if('" + active + "' = '' , k.active like '%%' , k.active= ? ) " 
//                          + " OR if('" + office_code + "' = '' , org.organisation_name like '%%' , org.organisation_name = ? )  "
//                           + " AND if('" + mobile + "' = '' , k.mobile_no1 like '%%' , k.mobile_no1= ? )  "
//                         + "AND if('" + person + "' = '' , k.key_person_name like '%%' , k.key_person_name = ? )  "
//                        + " AND ( if('" + familysearch + "' = '' , ot2.org_office_code like '%%' , ot2.org_office_code = ? ) "
//                            + " OR if('" + familysearch + "' = '' , ot.org_office_code like '%%' , ot.org_office_code = ? ) ) "
////                          + "AND if('" + searchEmpCode + "' = '' , emp_code like '%%' , emp_code = ? )  "
////                           + "AND if('" + searchDesignation + "' = '' ,d.designation like '%%' ,d.designation = ? ) "
//                          
//                          + "GROUP BY k.key_person_id ORDER BY key_person_name "
            String query1 = " select k.key_person_id,ot.org_office_code ,oft.office_type,org.organisation_name,org2.organisation_name as family_organisation, ot.org_office_name, "
                    + " k.key_person_name,d.designation,c.city_name,k.address_line1, k.address_line2,k.address_line3, "
                    + " k.image_path, k.mobile_no1,k.mobile_no2, k.landline_no1, k.landline_no2, k.email_id1, k.email_id2,  "
                    + " k.salutation,k.emp_code, k.father_name, k.date_of_birth,k.id_no, k.id_type_id ,k.password , "
                    + " k.bloodgroup,k.emergency_contact_name,k.emergency_contact_mobile,k.latitude,k.longitude, "
                    + " k.gender,k.relation,ot2.org_office_name as family_org_name,d2.designation  as family_designation "
                    + " from key_person AS k left join org_office AS ot  on k.org_office_id=ot.org_office_id "
                    + " left join  org_office_type as oft on oft.office_type_id = ot.office_type_id "
                    + " left join org_office AS ot2 on k.family_office=ot2.org_office_id "
                    + " left join  designation as d2 on k.family_designation = d2.designation_id "
                    + " left join  city AS c on k.city_id=c.city_id "
                    + " left join  designation as d on k.designation_id = d.designation_id "
                    + " left join organisation_name AS org on ot.organisation_id = org.organisation_id "
                    + " left join organisation_name AS org2 on ot2.organisation_id = org2.organisation_id  "
                    + " where (d.active='Y' or d2.active='Y') and k.active='Y' ";

            if (!office_code.equals("") && office_code != null) {
                query1 += " and (org.organisation_name='" + office_code + "' or org2.organisation_name='" + office_code + "') ";
            }
            if (!mobile.equals("") && mobile != null) {
                query1 += " and k.mobile_no1='" + mobile + "' ";
            }
            if (!person.equals("") && person != null) {
                query1 += " and k.key_person_name='" + person + "' ";
            }
            if (!familysearch.equals("") && familysearch != null) {
                query1 += " and (ot2.org_office_code='" + familysearch + "' or  ot.org_office_code='" + familysearch + "') ";
            }

            query1 += " GROUP BY k.key_person_id ORDER BY key_person_name ";

            System.err.println("query1=--------------------" + query1);

//            +" AND (if('" + office_code + "' = '' , org.organisation_name like '%%' , org.organisation_name = ? ) "
//                    + " OR if('" + office_code + "' = '' , org2.organisation_name like '%%' , org2.organisation_name = ? ) ) "
//                    + " AND if('" + mobile + "' = '' , k.mobile_no1 like '%%' , k.mobile_no1= ? ) "
//                    + " AND if('" + person + "' = '' , k.key_person_name like '%%' , k.key_person_name = ? ) "
//                    + " AND ( if('" + familysearch + "' = '' , ot2.org_office_code like '%%' , ot2.org_office_code = ? ) "
//                    + " OR if('" + familysearch + "' = '' , ot.org_office_code like '%%' , ot.org_office_code = ? ) ) "
//                    + " GROUP BY k.key_person_id ORDER BY key_person_name  ";
            PreparedStatement pstmt = connection.prepareStatement(query1);
//            pstmt.setString(1, active);
//            pstmt.setString(2, office_code);
//            pstmt.setString(3, office_code);
//            pstmt.setString(4, mobile);
//            pstmt.setString(5, (person));
//            pstmt.setString(6, familysearch);
//            pstmt.setString(7, familysearch);
//            pstmt.setString(5, searchEmpCode);
//            pstmt.setString(6,(searchDesignation));
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                KeyPerson key = new KeyPerson();
                key.setKey_person_id(rset.getInt("key_person_id"));
                key.setOrg_office_code(rset.getString("org_office_code"));
                key.setOffice_type(rset.getString("office_type"));
                key.setOrganisation_name((rset.getString("organisation_name")));
                key.setOrg_office_name((rset.getString("org_office_name")));
                key.setKey_person_name((rset.getString("key_person_name")));
                key.setImage_path(rset.getString("image_path"));
                key.setDesignation((rset.getString("designation")));
                // key.setState_name(rset.getString("state_name"));
                key.setCity_name((rset.getString("city_name")));
                key.setAddress_line1((rset.getString("address_line1")));
                key.setAddress_line2((rset.getString("address_line2")));
                key.setAddress_line3((rset.getString("address_line3")));
                key.setMobile_no1(rset.getString("mobile_no1"));
                key.setMobile_no2(rset.getString("mobile_no2"));
                key.setLandline_no1(rset.getString("landline_no1"));
                key.setLandline_no2(rset.getString("landline_no2"));
                key.setEmail_id1(rset.getString("email_id1"));
                key.setEmail_id2(rset.getString("email_id2"));
                key.setSalutation(rset.getString("salutation"));
                key.setEmp_code(rset.getString("emp_code"));
                // key.setGeneral_image_details_id(rset.getInt("general_image_details_id"));
                key.setFather_name((rset.getString("father_name")));
                key.setDate_of_birth(rset.getString("date_of_birth"));
                key.setId_no(rset.getString("id_no"));
                String id_type = KeypersonModel.getid_type_id((rset.getString("id_type_id")));
                key.setId_type((id_type));
                key.setPassword(rset.getString("password"));
                key.setBlood(rset.getString("bloodgroup"));
                key.setEmergency_name(rset.getString("emergency_contact_name"));
                key.setEmergency_number(rset.getString("emergency_contact_mobile"));
                key.setLatitude(rset.getString("latitude"));
                key.setLongitude(rset.getString("longitude"));
                key.setGender(rset.getString("gender"));
                key.setFamilyName(rset.getString("family_org_name"));
                key.setRelation(rset.getString("relation"));
                key.setFamilyDesignation(rset.getString("family_designation"));

                //key.setImage_name(rset.getString("image_name"));
                list.add(key);
            }
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--showData " + e);
        }
        return list;
    }

    public List<EmergencyBean> showEmergency(String kp_id) {
        List list = new ArrayList();
        String query = " select distinct emer.id,emer.name,emer.number,emer.key_person_id,emer.emergency_kp_id from"
                + " emergency_contact_details emer,key_person k"
                + " where  emer.key_person_id=" + kp_id + " and  k.active='Y' and emer.active='Y' ";
        System.err.println("quer--------------------" + query);
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = ps.executeQuery();
            while (rset.next()) {
                EmergencyBean bean = new EmergencyBean();
                int id = rset.getInt("id");
                String name = rset.getString("name");
                String number = rset.getString("number");
                int k_id = rset.getInt("key_person_id");
                String k_name = getkp_name(k_id);
                int emer_kp_id = rset.getInt("emergency_kp_id");
                String emer_kp_name = getkp_name(emer_kp_id);

                bean.setId(id);
                bean.setName(name);
                bean.setNumber(number);
                bean.setK_name(k_name);
                bean.setK_emer_name(emer_kp_name);
                list.add(bean);
            }

        } catch (Exception e) {
            System.out.println("error: " + e);
        }

        return list;
    }

    public byte[] generateKeyperSonList(String jrxmlFilePath, int organisation_id) {
        byte[] reportInbytes = null;
        HashMap mymap = new HashMap();
        mymap.put("organisation_id", organisation_id);
        Connection con = connection;
        try {
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, mymap, con);
        } catch (Exception e) {
            System.out.println("Error: in KeypersonModel generatReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public static ByteArrayOutputStream generateXlsRecordList(String jrxmlFilePath, List list) {
        String reportInbytes = null;
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        HashMap mymap = new HashMap();
        try {
            JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(list);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, mymap, jrBean);
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArray);
            exporter.exportReport();
        } catch (Exception e) {
            System.out.println("CityModel generatXlsReportList() JRException: " + e);
        }
        return byteArray;
    }

    public int getOrganisation_id(String organisation_name) {
        String query = "SELECT organisation_id FROM organisation_name WHERE organisation_name = ? ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, organisation_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("organisation_id");
        } catch (Exception e) {
            System.out.println("Error: keypersonModel--" + e);
        }
        return organisation_id;
    }

    public int insertRecord(KeyPerson key, Iterator itr, String photo_destination, String iD_destination) throws SQLException {
        System.err.println("insert -----------------------");
        int rowsAffected = 0;
        DateFormat dateFormat1 = new SimpleDateFormat("dd.MMMMM.yyyy");
        DateFormat dateFormat = new SimpleDateFormat("dd.MMMMM.yyyy/ hh:mm:ss aaa");
        Date date = new Date();
        String current_date = dateFormat.format(date);
        String image_uploaded_for = "key_person";
        String query1 = "", query2 = "", updateQuery = "";
        PreparedStatement pstmt = null;
        int kp_id = 0;
        try {
            query1 = "INSERT INTO key_person( salutation, key_person_name, designation_id, org_office_id, city_id, address_line1, "
                    + "address_line2, address_line3,"
                    + " mobile_no1, mobile_no2, landline_no1, landline_no2, email_id1, email_id2,emp_code, father_name,"
                    + " date_of_birth,revision_no,active,remark,latitude,longitude,id_type_id,id_no,password,bloodgroup,"
                    + "emergency_contact_name,emergency_contact_mobile,isVarified,gender,family_office,family_designation,relation,org_office_designation_map_id) "
                    + "VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            query2 = "INSERT INTO general_image_details (image_name, image_destination_id, date_time, description,key_person_id,revision_no,active,remark) "
                    + " VALUES(?,?,?,?,?,?,?,?)";

            pstmt = connection.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
            //    pstmt.setString(16, photo_destination + imageName);
            //   pstmt.setInt(17,Integer.parseInt(key.getImage_id()));
            //}
            pstmt.setString(1, key.getSalutation());
            pstmt.setString(2, (key.getKey_person_name()));
            if (key.getDesignation_id() == 0) {
                pstmt.setString(3, null);
            } else {
                pstmt.setInt(3, key.getDesignation_id());
            }
            if (key.getOrg_office_id() == 0) {
                pstmt.setString(4, null);
            } else {
                pstmt.setInt(4, key.getOrg_office_id());
            }

            pstmt.setInt(5, key.getCity_id());
            pstmt.setString(6, (key.getAddress_line1()));
            pstmt.setString(7, (key.getAddress_line2()));
            pstmt.setString(8, (key.getAddress_line3()));
            pstmt.setString(9, key.getMobile_no1());
            pstmt.setString(10, key.getMobile_no2());
            pstmt.setString(11, key.getLandline_no1());
            pstmt.setString(12, key.getLandline_no2());
            pstmt.setString(13, key.getEmail_id1());
            pstmt.setString(14, key.getEmail_id2());
            pstmt.setString(15, key.getEmp_code());
            pstmt.setString(16, (key.getFather_name()));
            pstmt.setString(17, key.getDate_of_birth());
            pstmt.setInt(18, key.getRevision_no());
            pstmt.setString(19, "Y");
            pstmt.setString(20, "good");
            pstmt.setString(21, key.getLatitude());
            pstmt.setString(22, key.getLongitude());
            pstmt.setInt(23, key.getId_type_d());
            pstmt.setString(24, key.getId_no());
            pstmt.setString(25, key.getPassword());
            pstmt.setString(26, key.getBlood());
            pstmt.setString(27, key.getEmergency_name());
            pstmt.setString(28, key.getEmergency_number());
            pstmt.setString(29, "No");
            pstmt.setString(30, key.getGender());
            pstmt.setInt(31, key.getFamilyofiiceid());
            pstmt.setInt(32, key.getFamilydesignationid());
            pstmt.setString(33, key.getRelation());
            pstmt.setInt(34, key.getOrg_office_des_map_id());
            rowsAffected = pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                kp_id = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--insertRecord-- " + e);
        }
        if (rowsAffected > 0 && (!key.getId_proof().isEmpty() || !key.getImage_path().isEmpty())) {
            String[] image_name = {key.getImage_path(), key.getId_proof()};

            for (int i = 0; i < image_name.length; i++) {
                String tempExt = image_name[i];

                if (!tempExt.isEmpty()) {
                    String middleName = "";
                    String destination = "";
                    String fieldName = "";
                    if (tempExt.equals(image_name[0])) {
                        middleName = "img_Key_person_";
                        destination = photo_destination;
                        fieldName = "design_name";
                        image_uploaded_for = "key_person_photo";
                    } else if (tempExt.equals(image_name[1])) {
                        middleName = "img_Id_";
                        destination = iD_destination;
                        fieldName = "id_proof";
                        image_uploaded_for = "key_person_ID";
                    }

                    int index = tempExt.lastIndexOf(".");
                    int index1 = tempExt.length();
                    String Extention = tempExt.substring(index + 1, index1);
                    tempExt = "." + Extention;
                    String imageName = middleName + kp_id + tempExt;
                    key.setImage_name(imageName);
                    //        rowsAffected = insertImageRecord(KeyPerson key,imageName, image_uploaded_for, current_date, kp_id);
                    if (rowsAffected > 0) {
                        WirteImage(key, itr, destination, imageName, fieldName);
                    }
                }
                pstmt = connection.prepareStatement(query2);
                pstmt.setString(1, key.getImage_name());
                pstmt.setInt(2, getimage_destination_id(image_uploaded_for));
                pstmt.setString(3, current_date);
                pstmt.setString(4, "this image is for site");
                pstmt.setInt(5, kp_id);
                pstmt.setString(6, "0");
                pstmt.setString(7, "Y");
                pstmt.setString(8, "ok");
                rowsAffected = pstmt.executeUpdate();
                pstmt.close();
            }

        }
        if (rowsAffected > 0) {
//                GeneralModel generalModel = new GeneralModel();
//                generalModel.sendSmsToAssignedFor(key.getMobile_no1(), message);
        }
        // rowsAffected = writeImage(key, itr, destination);
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return kp_id;
    }

    public int getOrgOfficeDesignationMapId(int designation_id, int org_office_id) {
        int org_office_des_map_id = 0;
        String query = "SELECT org_office_designation_map_id FROM org_office_designation_map "
                + "WHERE designation_id = '" + designation_id + "' and org_office_id='" + org_office_id + "'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //  pstmt.setString(1, designation);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            org_office_des_map_id = rset.getInt("org_office_designation_map_id");
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--getOrgOfficeDesignationMapId-- " + e);
        }

        return org_office_des_map_id;
    }

    public int updateRecord(KeyPerson key, Iterator itr, int key_id, String photo_destination, String iD_destination) {
        int revision = KeypersonModel.getRevisionno(key, key_id);
        int familyofficeid = getOrgOfficeid(key.getFamilyName());
        int famdesignationid = getDesignation_id(key.getFamilyDesignation());
        int updateRowsAffected = 0;
        boolean status = false;
        DateFormat dateFormat1 = new SimpleDateFormat("dd.MMMMM.yyyy");
        DateFormat dateFormat = new SimpleDateFormat("dd.MMMMM.yyyy/ hh:mm:ss aaa");
        Date date = new Date();
        String current_date = dateFormat.format(date);
        int kp_id = 0;
        String query1 = "SELECT max(revision_no) revision_no FROM key_person WHERE key_person_id = " + key_id + "  && active=? ";
        String query2 = "UPDATE key_person SET active=? WHERE key_person_id=? and revision_no=?";
        String query3 = "INSERT INTO key_person(key_person_id,salutation, key_person_name, designation_id, org_office_id, city_id, address_line1, "
                + "address_line2, address_line3,"
                + " mobile_no1, mobile_no2, landline_no1, landline_no2, email_id1, email_id2,emp_code, father_name,"
                + " date_of_birth,revision_no,active,remark,latitude,longitude,id_type_id,id_no,password,bloodgroup,"
                + "emergency_contact_name,emergency_contact_mobile,isVarified,gender,family_office,family_designation,relation,"
                + "org_office_designation_map_id) "//image_path,
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String query4 = " select count(*) from general_image_details where key_person_id='" + key_id + "' ";

        String query5 = " INSERT INTO general_image_details (image_name, image_destination_id, date_time, description,key_person_id,"
                + "revision_no,active,remark) "
                + " VALUES(?,?,?,?,?,?,?,?)";

        // String query6=" UPDATE general_image_details SET active=? WHERE key_person_id=? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
//           pstmt.setInt(1,organisation_type_id);
            pstmt.setString(1, "Y");

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                PreparedStatement pstm = connection.prepareStatement(query2);

                pstm.setString(1, "n");

                pstm.setInt(2, key_id);
                pstm.setInt(3, revision);
                updateRowsAffected = pstm.executeUpdate();
                if (updateRowsAffected >= 1) {
                    revision = rs.getInt("revision_no") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                    psmt.setInt(1, key_id);
                    psmt.setString(2, (key.getSalutation()));
                    psmt.setString(3, (key.getKey_person_name()));
                    psmt.setInt(4, key.getDesignation_id());
                    psmt.setInt(5, key.getOrg_office_id());
                    psmt.setInt(6, key.getCity_id());
                    psmt.setString(7, (key.getAddress_line1()));
                    psmt.setString(8, (key.getAddress_line2()));
                    psmt.setString(9, (key.getAddress_line3()));
                    psmt.setString(10, key.getMobile_no1());
                    psmt.setString(11, key.getMobile_no2());
                    psmt.setString(12, key.getLandline_no1());
                    psmt.setString(13, key.getLandline_no2());
                    psmt.setString(14, key.getEmail_id1());
                    psmt.setString(15, key.getEmail_id2());
                    psmt.setString(16, key.getEmp_code());
                    psmt.setString(17, (key.getFather_name()));
                    psmt.setString(18, key.getDate_of_birth());
                    psmt.setInt(19, revision);
                    psmt.setString(20, "Y");
                    psmt.setString(21, "good");
                    psmt.setString(22, key.getLatitude());
                    psmt.setString(23, key.getLongitude());
                    psmt.setInt(24, key.getId_type_d());
                    psmt.setString(25, key.getId_no());
                    psmt.setString(26, key.getPassword());
                    psmt.setString(27, key.getBlood());
                    psmt.setString(28, key.getEmergency_name());
                    psmt.setString(29, key.getEmergency_number());
                    psmt.setString(30, "No");
                    psmt.setString(31, key.getGender());
                    psmt.setInt(32, familyofficeid);
                    psmt.setInt(33, famdesignationid);
                    psmt.setString(34, key.getRelation());
                    psmt.setInt(35, key.getOrg_office_des_map_id());
                    rowsAffected = psmt.executeUpdate();

                    if (rowsAffected > 0) {
                        status = true;
                        kp_id = key_id;
                    } else {
                        status = false;
                    }

                    if (rowsAffected > 0 && (!key.getId_proof().isEmpty() || !key.getImage_path().isEmpty())) {

                        String[] image_name = {key.getImage_path(), key.getId_proof()};
                        String revision_no = getRevisionnoForImage(key, key_id);
                       if (revision_no == null) {
                            revision_no = "0";

                        }
                        for (int i = 0; i < image_name.length; i++) {
                            String tempExt = image_name[i];
                            String image_uploaded_for = "";
                            if (!tempExt.isEmpty()) {
                                String middleName = "";
                                String destination = "";
                                String fieldName = "";
                                String update_image_query = "";

                                if (tempExt.equals(image_name[0])) {
                                    middleName = "img_Key_person_";
                                    destination = photo_destination;
                                    fieldName = "design_name";
                                    image_uploaded_for = "key_person_photo";

                                    update_image_query = " UPDATE general_image_details SET active=? "
                                            + "WHERE key_person_id=? and revision_no=?  and image_destination_id=1 ";

                                } else if (tempExt.equals(image_name[1])) {
                                    middleName = "img_Id_";
                                    destination = iD_destination;
                                    fieldName = "id_proof";
                                    image_uploaded_for = "key_person_ID";

                                    update_image_query = " UPDATE general_image_details SET active=? "
                                            + "WHERE key_person_id=? and revision_no=? and image_destination_id=2  ";

                                }

                                PreparedStatement pstmt1 = (PreparedStatement) connection.prepareStatement(query4);

                                ResultSet rset = pstmt1.executeQuery();
                                if (rset.next()) {
                                    System.err.print("UPDATE--------------");

                                    pstmt1 = connection.prepareStatement(update_image_query);
                                    pstmt1.setString(1, "N");
                                    pstmt1.setInt(2, key_id);
                                    pstmt1.setString(3, revision_no);
                                    int rowsAffectedImage = pstmt1.executeUpdate();
                                    if (rowsAffectedImage >= 1) {
                                        revision_no = revision_no + 1;
                                    }
                                }

                                int index = tempExt.lastIndexOf(".");
                                int index1 = tempExt.length();
                                String Extention = tempExt.substring(index + 1, index1);
                                tempExt = "." + Extention;
                                String imageName = middleName + kp_id + tempExt;
                                key.setImage_name(imageName);
                                //        rowsAffected = insertImageRecord(KeyPerson key,imageName, image_uploaded_for, current_date, kp_id);
                                if (rowsAffected > 0) {
                                    WirteImage(key, itr, destination, imageName, fieldName);
                                }
                            }
                            if(revision_no==null){
                                revision_no="";
                            }
                            pstmt = connection.prepareStatement(query5);
                            pstmt.setString(1, key.getImage_name());
                            pstmt.setInt(2, getimage_destination_id(image_uploaded_for));
                            pstmt.setString(3, current_date);
                            pstmt.setString(4, "this image is for site");
                            pstmt.setInt(5, kp_id);
                            pstmt.setString(6, revision_no);
                            pstmt.setString(7, "Y");
                            pstmt.setString(8, "ok");
                            rowsAffected = pstmt.executeUpdate();

                        }
                        pstmt.close();

                    }
                }

            }
        } catch (Exception e) {
            System.out.println("Error:OrganisationSubTypeModel-" + e);
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

    public int insertImageRecord(KeyPerson key, String image_name, String image_uploaded_for, String current_date, int kp_id) {
        int rowsAffected = 0;
        String imageQuery = "INSERT INTO general_image_details (image_name, image_destination_id, date_time, description,key_person_id,revision_no,active,remark) "
                + " VALUES(?, ?, ?, ?,?,?,?,?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(imageQuery);
            pstmt.setString(1, image_name);
            pstmt.setInt(2, getimage_destination_id(image_uploaded_for));
            pstmt.setString(3, current_date);
            pstmt.setString(4, "this image is for site");
            pstmt.setInt(5, kp_id);
            pstmt.setInt(6, key.getRevision_no());
            pstmt.setString(7, "Y");
            pstmt.setString(8, "good");
            rowsAffected = pstmt.executeUpdate();
            pstmt.close();
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--insertRecord-- " + e);
        }
        return rowsAffected;
    }

    public void WirteImage(KeyPerson key, Iterator itr, String destination, String imageName, String fieldName) {
        int count = 0;
        try {
            while (itr.hasNext()) {
                FileItem item = (FileItem) itr.next();
                makeDirectory(destination);
                try {
                    if (!item.isFormField()) {
                        if (item.getFieldName().equals(fieldName)) {
                            File file = new File(destination + imageName);
                            String image = item.getName();
                            if (image.isEmpty() || image.equals(destination)) {
                            } else {
                                item.write(file);
                                message = "Image Uploaded Successfully.";
                                //  updateImageName(imageName,id);
                                count++;
                            }
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Image upload error: " + e);
                }
            }
            //}
        } catch (Exception ex) {
        }
    }

    public static int getRevisionno(KeyPerson key, int key_id) {
        int revision = 0;
        try {

            String query = " SELECT max(revision_no) as revision_no FROM key_person WHERE key_person_id =" + key_id + "  && active='Y';";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
        }
        return revision;
    }

    public static String getRevisionnoForImage(KeyPerson key, int key_id) {
        String revision = "";
        try {

            String query = " SELECT max(revision_no) as revision_no FROM general_image_details"
                    + " WHERE key_person_id =" + key_id + "  && active='Y';";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getString("revision_no");

            }
        } catch (Exception e) {
        }
        return revision;
    }

    public int getimage_destination_id(String image_uploaded_for) {
        String query;
        int image_destination_id = 0;
        query = " SELECT image_destination_id, destination_path from image_destination AS id , image_uploaded_for As i "
                + " WHERE id.image_uploaded_for_id=i.image_uploaded_for_id AND i.image_uploaded_for= '" + image_uploaded_for + "' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1, image_uploaded_for);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                String destination_path = rset.getString("destination_path");
                System.out.println(destination_path);
                rset.getInt("image_destination_id");
                System.out.println("id = " + rset.getInt("image_destination_id"));
                image_destination_id = rset.getInt("image_destination_id");
                System.out.println(image_destination_id);
            }

        } catch (Exception ex) {
            System.out.println("Error: UploadBillImageModel-getimage_destination_id-" + ex);
        }
        return image_destination_id;
    }

    public int getkey_person_id(String emp_code) {
        String query;
        int key_person_id = 0;
        query = "select key_person_id from key_person where emp_code='" + emp_code + "' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {

                key_person_id = rset.getInt("key_person_id");

            }

        } catch (Exception ex) {
            System.out.println("Error: UploadBillImageModel-getkey_person_id-" + ex);
        }
        return key_person_id;
    }

    public int getgeneral_image_details_id(int id) {
        String query;
        int key_person_id = 0;
        query = "select general_image_details_id from general_image_details where key_person_id='" + id + "' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {

                key_person_id = rset.getInt("general_image_details_id");

            }

        } catch (Exception ex) {
            System.out.println("Error: UploadBillImageModel-getgeneral_image_details_id-" + ex);
        }
        return key_person_id;
    }

    public boolean makeDirectory(String dirPathName) {
        boolean result = false;
        System.out.println("dirPathName---" + dirPathName);
        //dirPathName = "C:/ssadvt/sor/organisation" ;
        File directory = new File(dirPathName);
        if (!directory.exists()) {
            try {
                result = directory.mkdirs();
            } catch (Exception e) {
                System.out.println("Error - " + e);
            }
        }
        return result;
    }

    public int updateRecord(KeyPerson key, Iterator itr, String photo_destination, String iD_destination) {

        String image_uploaded_for = "key_person";
        String imageQuery = "", updateQuery = "";
        DateFormat dateFormat = new SimpleDateFormat("dd.MMMMM.yyyy/ hh:mm:ss aaa");
        Date date = new Date();
        String current_date = dateFormat.format(date);
        //String tempExt="";
        int count = 0;

        String query = "UPDATE key_person SET  salutation=?, key_person_name=?, designation_id=?, org_office_id=?, city_id=?, address_line1=?, "
                + "address_line2=?, address_line3=?,"
                + " mobile_no1=?, mobile_no2=?, landline_no1=?, landline_no2=?, email_id1=?, email_id2=?, father_name=?, date_of_birth=? "
                + "WHERE key_person_id=?";
        updateQuery = "update general_image_details set image_name=?,image_destination_id=?, date_time=?,key_person_id=? where general_image_details_id=?";
        imageQuery = "INSERT INTO general_image_details (image_name, image_destination_id, date_time, description,key_person_id) "
                + " VALUES(?, ?, ?, ?,?)";
        String updateQuery1 = "UPDATE key_person set general_image_details_id=? where key_person_id=?";

        int rowsAffected = 0;
        try {
            int key_person_id = key.getKey_person_id();
            PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setString(1, key.getSalutation());
            pstmt.setString(2, key.getKey_person_name());
            pstmt.setInt(3, key.getDesignation_id());
            pstmt.setInt(4, key.getOrg_office_id());
            pstmt.setInt(5, key.getCity_id());
            pstmt.setString(6, key.getAddress_line1());
            pstmt.setString(7, key.getAddress_line2());
            pstmt.setString(8, key.getAddress_line3());
            pstmt.setString(9, key.getMobile_no1());
            pstmt.setString(10, key.getMobile_no2());
            pstmt.setString(11, key.getLandline_no1());
            pstmt.setString(12, key.getLandline_no2());
            pstmt.setString(13, key.getEmail_id1());
            pstmt.setString(14, key.getEmail_id2());
            pstmt.setString(15, key.getFather_name());
            pstmt.setString(16, key.getDate_of_birth());
            pstmt.setInt(17, key_person_id);
            rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0 && (!key.getId_proof().isEmpty() || !key.getImage_path().isEmpty())) {
                String[] image_name = {key.getImage_path(), key.getId_proof()};

                for (int i = 0; i < image_name.length; i++) {
                    String tempExt = image_name[i];

                    if (!tempExt.isEmpty()) {
                        String middleName = "";
                        String destination = "";
                        String fieldName = "";
                        if (tempExt.equals(image_name[0])) {
                            middleName = "pic";
                            destination = photo_destination;
                            fieldName = "design_name";
                            image_uploaded_for = "key_person_photo";
                        } else if (tempExt.equals(image_name[1])) {
                            middleName = "proof";
                            destination = iD_destination;
                            fieldName = "id_proof";
                            image_uploaded_for = "key_person_ID";
                        }
                        String img_name = imageExist(image_uploaded_for, key_person_id);
                        String rev = "";
                        if (!img_name.isEmpty()) {
                            img_name = img_name.replace(".", "#");
                            String imgArray = img_name.split("#")[0];
                            String[] id = imgArray.split(key_person_id + "");
                            if (id.length < 2) {
                                rev = "_1";
                            } else {
                                id[1] = id[1].replace("_", "");
                                rev = "_" + (Integer.parseInt(id[1]) + 1);
                            }

                        }

                        int index = tempExt.lastIndexOf(".");
                        int index1 = tempExt.length();
                        String Extention = tempExt.substring(index + 1, index1);
                        tempExt = "." + Extention;
                        String imageName = key.getDesignation() + "_" + middleName + "_" + key_person_id + rev + tempExt;
                        key.setImage_name(imageName);
                        // rowsAffected = insertImageRecord(imageName, image_uploaded_for, current_date, key_person_id);
                        if (rowsAffected > 0) {
                            WirteImage(key, itr, destination, imageName, fieldName);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error:keypersonModel-updateRecord-- " + e);
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

    public String imageExist(String image_uploaded_for, int key_person_id) {
        String image_name = "";
        String query = "SELECT gid.general_image_details_id, gid.image_name "
                + " FROM general_image_details gid, image_destination id, image_uploaded_for uf "
                + " WHERE gid.image_destination_id=id.image_destination_id AND id.image_uploaded_for_id=uf.image_uploaded_for_id "
                + " AND uf.image_uploaded_for='" + image_uploaded_for + "' AND gid.key_person_id=" + key_person_id + " "
                + " ORDER BY gid.general_image_details_id DESC";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                image_name = rs.getString("image_name");
            }

        } catch (Exception ex) {
            System.out.println("ERROR : in imageExist() in keypersonModel : " + ex);
        }
        return image_name;
    }

    public int deleteRecord(int key_person_id) {
        // String query = "DELETE FROM key_person WHERE key_person_id=" + key_person_id;
        String query2 = "UPDATE key_person SET active=? WHERE key_person_id=? and revision_no=?";
        int rowsAffected = 0;
        try {

            PreparedStatement pstm = connection.prepareStatement(query2);

            pstm.setString(1, "n");

            pstm.setInt(2, key_person_id);
            pstm.setInt(3, 0);
            rowsAffected = pstm.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error:keypersonModel-deleteRecord-- " + e);
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

    public int getCity_id(String city_name) {
        String query = "SELECT city_id FROM city WHERE city_name = '" + city_name + "'";
        int city_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1, city_name);

            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) // move cursor from BOR to valid record.
            {
                city_id = rset.getInt("city_id");
            }
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--getCity_id-- " + e);
        }
        return city_id;
    }

    static public String getid_type_id(String type_id) {
        String query = "SELECT id_type FROM id_type WHERE id_type_id ='" + type_id + "'";
        String city_id = "";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) // move cursor from BOR to valid record.
            {
                city_id = rset.getString("id_type");
            }
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--getid_type_id-- " + e);
        }
        return city_id;
    }

    public List<String> getOffice_type(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT oft.office_type FROM org_office_type AS oft ORDER BY office_type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String office_type = rset.getString("office_type");
                if (office_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(office_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such office type Type exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:OrganisationMapModel--getOrganationNameList()-- " + e);
        }
        return list;
    }

    public List<String> getOrgPersonNameListForAll(String q, String code, String org) {
        List<String> list = new ArrayList<String>();
        String query = "";
//        String query = "SELECT k.key_person_name FROM key_person  As k, org_office AS oft  WHERE k.org_office_id = oft.org_office_id and k.active='Y' "
//                         + " AND if('"+ code +"' = '' , oft.org_office_name like '%%' , oft.org_office_name = ? ) "
//                + " GROUP BY key_person_name ";

        if (org.equals("Family")) {

            query = " select distinct o.organisation_id,o.organisation_name,ogf.org_office_name,kp.key_person_name from organisation_name o, "
                    + " org_office ogf, key_person kp  where kp.family_office=ogf.org_office_id and o.organisation_id=ogf.organisation_id and "
                    + " o.active='Y' and ogf.active='Y'and kp.active='Y' "
                    + " and IF('" + org + "'='' ,o.organisation_name LIKE '%%',o.organisation_name = '" + org + "') and  "
                    + " IF('" + code + "'='' ,ogf.org_office_name LIKE '%%',ogf.org_office_name = '" + code + "') ";
        } else {
            query = " select distinct o.organisation_id,o.organisation_name,ogf.org_office_name,kp.key_person_name from organisation_name o, "
                    + " org_office ogf, key_person kp  where kp.org_office_id=ogf.org_office_id and o.organisation_id=ogf.organisation_id and "
                    + " o.active='Y' and ogf.active='Y'and kp.active='Y' "
                    + " and IF('" + org + "'='' ,o.organisation_name LIKE '%%',o.organisation_name = '" + org + "') and  "
                    + " IF('" + code + "'='' ,ogf.org_office_name LIKE '%%',ogf.org_office_name = '" + code + "') ";
        }

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            q = q.trim();
            int count = 0;

            while (rset.next()) {    // move cursor from BOR to valid record.
                String key_person_name = (rset.getString("key_person_name"));
                if (key_person_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(key_person_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such key person Type exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:OrganisationMapModel--getOrgPersonNameList()-- " + e);
        }
        return list;
    }

    public List<String> getOrgPersonNameList(String q, String code) {
        List<String> list = new ArrayList<String>();
//        String query = "SELECT k.key_person_name FROM key_person  As k, org_office AS oft  WHERE k.org_office_id = oft.org_office_id and k.active='Y' "
//                         + " AND if('"+ code +"' = '' , oft.org_office_code like '%%' , oft.org_office_code = ? ) ";
        //  + " GROUP BY key_person_name ";
        String query = " SELECT k.key_person_name FROM key_person  As k, org_office AS oft  WHERE"
                + " k.org_office_id = oft.org_office_id and k.active='Y'  ";
        if (!code.equals("") && code != null) {
            query += " and oft.org_office_code='" + code + "' ";
        }
        query += " group by k.key_person_name ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            q = q.trim();
            int count = 0;

            while (rset.next()) {    // move cursor from BOR to valid record.
                String key_person_name = (rset.getString("key_person_name"));
                if (key_person_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(key_person_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such key person Type exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:OrganisationMapModel--getOrgPersonNameList()-- " + e);
        }
        return list;
    }

    public static List<String> getIdtypeList() {
        List<String> list = new ArrayList<String>();
        String query = "select * from id_type where active='Y'";
        //  + " GROUP BY key_person_name ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            int count = 0;

            while (rset.next()) {    // move cursor from BOR to valid record.
                String id_type = (rset.getString("id_type"));

                list.add(id_type);
                count++;

            }
            if (count == 0) {
                list.add("No such key person Type exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:OrganisationMapModel--getOrgPersonNameList()-- " + e);
        }
        return list;
    }

    public List<String> getOrgOfficeName(String q, String office_code) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT ot.org_office_name FROM org_office AS ot , org_office_type as oft,organisation_name AS o "
                + "WHERE oft.office_type_id = ot.office_type_id  AND o.organisation_id=ot.organisation_id AND ot.org_office_code ='" + office_code + "' and ot.active='Y' and oft.active='Y' and o.active='Y'"
                //                + " AND if('"+ office_code +"' = '' , ot.org_office_code like '%%' , ot.org_office_code = ? ) "
                + " GROUP BY org_office_name ";
        System.err.println("org office query----------------" + query);
        try {
            int count = 0;

            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1, office_code);
            ResultSet rset = pstmt.executeQuery();
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String office_name = (rset.getString("org_office_name"));
                if (office_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(office_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such org office  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:KeypersonModel--getOrganationNameList()-- " + e);
        }
        return list;
    }

    public int insertemergency(List list1, List list2, int key_id, String k, String mobile_no1) {
        List<String> list = new ArrayList<String>();
        String query = "INSERT INTO emergency_contact_details(key_person_id,name,number,emergency_kp_id,confirmation,revision_no,active)"
                + " VALUES( ?, ?, ?,?,?,?,?)";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            for (int i = 0; i <= Integer.parseInt(k); i++) {
                pstmt.setInt(1, (key_id));
                pstmt.setObject(2, list1.get(i));
                pstmt.setObject(3, list2.get(i));
                pstmt.setInt(4, getemergencykp_id(mobile_no1));
                pstmt.setString(5, "Yes");
                pstmt.setInt(6, 0);
                pstmt.setString(7, "Y");
                rowsAffected = pstmt.executeUpdate();
            }

//            pstmt.setString(2, (designation.getDescription()));
//            pstmt.setString(3, designation.getDesignation_code());
//              pstmt.setInt(4,designation.getRevision_no());
//            pstmt.setString(5,"Y");
//            pstmt.setString(6,"OK");
        } catch (Exception e) {
            System.out.println("designationModel Error: " + e);
        }
//        if (rowsAffected > 0) {
//            message = "Record saved successfully.";
//            msgBgColor = COLOR_OK;
//        } else {
//            message = "Cannot save the record, some error.";
//            msgBgColor = COLOR_ERROR;
//        }
        return rowsAffected;
    }

    public int getOrgOffice_id(String org_office_name, String office_code) {
        String query = "SELECT ot.org_office_id "
                + " FROM org_office AS ot , "
                + " organisation_name AS o "
                + " WHERE o.organisation_id=ot.organisation_id AND "
                + "ot.org_office_name = '" + org_office_name + "' AND ot.org_office_code = '" + office_code + "'";

        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

//            pstmt.setString(1, org_office_name);
//            pstmt.setString(2, office_code);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                organisation_id = rset.getInt("org_office_id");
            }
        } catch (Exception e) {
            System.out.println("Error:keypersonModel-- getOrgOffice_id--" + e);
        }
        return organisation_id;
    }

    public int getOrgOfficeid(String org_office_name) {
        String query = "SELECT ot.org_office_id "
                + " FROM org_office AS ot , "
                + " organisation_name AS o "
                + " WHERE o.organisation_id=ot.organisation_id AND ot.org_office_name = '" + org_office_name + "' AND ot.active='Y'";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            //pstmt.setString(1, org_office_name);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                organisation_id = rset.getInt("org_office_id");
            }
        } catch (Exception e) {
            System.out.println("Error:keypersonModel-- getOrganization_id--" + e);
        }
        return organisation_id;
    }

    public List<String> getMobilevalidty(String q) {
        List<String> list = new ArrayList<String>();
//        String query = "SELECT city_name FROM city AS c ,state AS s WHERE c.state_id=s.state_id AND s.state_name=? "
//                + "  ORDER BY city_name";
        String AdvertiseName = "";
        String query = "SELECT mobile_no1 FROM key_person where key_person.active='Y' and mobile_no1='" + q + "'";
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
            System.out.println("Error: KeypersonModel-" + e);
        }
        return list;
    }

    public List<String> getsearchMobile(String q, String person) {
        List<String> list = new ArrayList<String>();
//        String query = "SELECT city_name FROM city AS c ,state AS s WHERE c.state_id=s.state_id AND s.state_name=? "
//                + "  ORDER BY city_name";
        String AdvertiseName = "";
        String query = "SELECT mobile_no1 FROM key_person where key_person.active='Y' ";
        if (!person.equals("") && person != null) {
            query += " and key_person_name='" + person + "' ";
        }
        System.err.println("query-----------" + query);
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //pstmt.setString(1, krutiToUnicode.convert_to_unicode(state_name));

            ResultSet rset = pstmt.executeQuery();
            q = q.trim();
            int count = 0;

            while (rset.next()) {    // move cursor from BOR to valid record.
                AdvertiseName = (rset.getString("mobile_no1"));
                if (AdvertiseName.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(AdvertiseName);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Mobile exists.......");
            }

        } catch (Exception e) {
            System.out.println("Error: KeypersonModel-" + e);
        }
        return list;
    }

    public List<String> getCityName(String q) {
        List<String> list = new ArrayList<String>();
//        String query = "SELECT city_name FROM city AS c ,state AS s WHERE c.state_id=s.state_id AND s.state_name=? "
//                + "  ORDER BY city_name";
        String query = "SELECT city_name FROM city AS c where c.active='Y' ORDER BY city_name";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //pstmt.setString(1, krutiToUnicode.convert_to_unicode(state_name));
            q = q.trim();
            ResultSet rset = pstmt.executeQuery();
            int count = 0;

            while (rset.next()) {    // move cursor from BOR to valid record.
                String AdvertiseName = (rset.getString("city_name"));
                if (AdvertiseName.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(AdvertiseName);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such city exists.......");
            }

        } catch (Exception e) {
            System.out.println("Error: KeypersonModel-" + e);
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
                String state_name = ("state_name");
                if (state_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(state_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such State Name exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:KeypersonModel- " + e);
        }
        return list;
    }

    public String getPointLatLong(String person_id) {
        String lat = "";
        String longi = "";
        String query = " select latitude,longitude "
                + " from key_person "
                + " where key_person_id='" + person_id + "' ";

        try {
            PreparedStatement pst = connection.prepareStatement(query);
            //  pst.setString(1, person_id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                lat = rs.getString("latitude");
                longi = rs.getString("longitude");

            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return lat + "," + longi;
    }

    public int getIdtype_id(String id_type) {
        String query = "SELECT id_type_id FROM id_type WHERE id_type = '" + id_type + "'";
        int designation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //  pstmt.setString(1, id_type);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            designation_id = rset.getInt("id_type_id");
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--getIdtype_id-- " + e);
        }
        return designation_id;
    }

    public String getkp_name(int id_type) {
        String query = "SELECT key_person_name FROM key_person WHERE key_person_id = '" + id_type + "'";
        String designation_id = "";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setInt(1, id_type);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            designation_id = rset.getString("key_person_name");
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--getkp_name-- " + e);
        }
        return designation_id;
    }

    public int getDesignation_id(String designation) {
        String query = "SELECT designation_id FROM designation WHERE designation = '" + designation + "'";
        int designation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //  pstmt.setString(1, designation);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            designation_id = rset.getInt("designation_id");
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--getDesignation_id-- " + e);
        }
        return designation_id;
    }

    public int getemergencykp_id(String designation) {
        String query = "SELECT key_person_id FROM key_person WHERE mobile_no1 = '" + designation + "'";
        int designation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1, designation);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            designation_id = rset.getInt("key_person_id");
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--getemergencykp_id"
                    + "-- " + e);
        }
        return designation_id;
    }

    public List<String> getDesignation(String q, String code, String key_person) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT designation from designation as d, org_office AS oft,key_person as k where k.designation_id=d.designation_id and oft.org_office_id=k.org_office_id and k.active='Y' "
                + " AND if('" + code + "' = '' , oft.org_office_code like '%%' , oft.org_office_code = ? ) "
                + " AND if('" + key_person + "' = '' , k.key_person_name like '%%' , k.key_person_name = ? )  ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            int count = 0;
            q = q.trim();
//            pstmt.setString(1, code);
//            pstmt.setString(2, key_person);

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String designation = (rset.getString("designation"));
                if (designation.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(designation);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Designation exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--getDesignationList()-- " + e);
        }
        return list;
    }

    public List<String> getDesgn(String q, String code, int parent_check) {
        List<String> list = new ArrayList<String>();
        //  String query = "SELECT designation from designation as d where d.active='Y' ";
        //   + " AND if('"+ code +"' = '' , oft.org_office_code like '%%' , oft.org_office_code = ? ) ";
        String query1 = "  select distinct o.organisation_name,d.designation from organisation_name o,org_office oft,organisation_designation od,designation d "
                + "  where oft.org_office_name='" + code + "' and od.organisation_id=o.organisation_id and "
                + " d.designation_id=od.organisation_designation_map_id_1 and "
                + "  oft.organisation_id=o.organisation_id and o.active='Y' and oft.active='Y' and od.active='Y' and d.active='Y'  ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
            int count = 0;

            q = q.trim();
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {    // move cursor from BOR to valid record.

                //  String organisation_name = (rset.getString("organisation_name"));
                String designation = (rset.getString("designation"));
                if (designation.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(designation);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Designation exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--getDesignationList()-- " + e);
        }
        return list;
    }

    public int getOrgtype_id(String emp_code) {
        String query;
        int key_person_id = 0;
        query = " select om.organisation_id,ot.organisation_type_id from org_office oo,organisation_name om ,organisation_type ot "
                + " where org_office_name='" + emp_code + "' and om.organisation_id=oo.organisation_id and om.organisation_type_id=ot.organisation_type_id and oo.active='Y' "
                + "  and om.active='Y' and ot.active='Y'";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {

                key_person_id = rset.getInt("organisation_type_id");

            }

        } catch (Exception ex) {
            System.out.println("Error: UploadBillImageModel-getOrgtype_id-" + ex);
        }
        return key_person_id;
    }

    public int getRealOrganisationType_id(String office_type) {
        String query = "SELECT organisation_type_id FROM organisation_type WHERE  org_type_name = ?  and active=? ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //  pstmt.setString(1, office_code);
            // pstmt.setString(1, office_type);
            // pstmt.setString(2, "Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("organisation_type_id");
        } catch (Exception e) {
            System.out.println("Error: OrganisationMapModel--" + e);
        }
        return organisation_id;
    }

    public List<String> getunDesgnation(String q, String code, int parent_check) {

//        List<String> list = new ArrayList<String>();
        List<String> list1 = null;
        String org_type = "";
        int org_type_id = 0;

        if (parent_check == 0) {
            org_type_id = getRealOrganisationType_id(code);

        } else {
            org_type_id = parent_check;
        }

        int parent_org_id = 0;
        //  String query = "SELECT designation from designation as d where d.active='Y' ";
        //   + " AND if('"+ code +"' = '' , oft.org_office_code like '%%' , oft.org_office_code = ? ) ";
//        String query1 = "  select  o.organisation_name,d.designation from organisation_name o,org_office oft,organisation_designation od,designation d "
//                        + "  where oft.org_office_name='"+code+"' and od.organisation_id=o.organisation_id and "
//                        + " d.designation_id=od.organisation_designation_map_id_1 and "                   
//                        + "  oft.organisation_id=o.organisation_id and o.active='Y' and oft.active='Y' and od.active='Y' and d.active='Y'  ";
//            String query = " select  o.organisation_name,ot.org_type_name,ot.parent_org_id,d.designation from organisation_name o,org_office oft, " +
//            " organisation_designation od,designation d, organisation_type ot, designation_organisation_type_map dotm " +
//            " where oft.org_office_name='"+code+"' and od.organisation_id=o.organisation_id and dotm.designation_id=d.designation_id and " +
//            " d.designation_id=od.organisation_designation_map_id_1 and o.organisation_type_id=ot.organisation_type_id and " +
//            " oft.organisation_id=o.organisation_id and o.active='Y' and oft.active='Y' and od.active='Y' and d.active='Y' ";
        String query = "select distinct ot.org_type_name,ot.organisation_type_id,ot.parent_org_id,d.designation"
                + " from organisation_type ot,"
                + " designation d, designation_organisation_type_map dotm"
                + " where ot.organisation_type_id = " + org_type_id + " and dotm.organisation_type_id = ot.organisation_type_id and dotm.designation_id = d.designation_id"
                + " and ot.active='Y' and dotm.active='Y' and d.active='Y' ;";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            int count = 0;

            q = q.trim();
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {    // move cursor from BOR to valid record.                 
                //  String organisation_name = (rset.getString("organisation_name"));
                org_type = (rset.getString("org_type_name"));
                parent_org_id = (rset.getInt("parent_org_id"));
                String designation = rset.getString("designation");
                if (parent_org_id == 0) {
//                     parent = getParentName(parent_org_id);
//                    getDesgnation(q,code);
                    if (org_type.toUpperCase().startsWith(q.toUpperCase())) {
                        list.add(designation);
                        count++;
                    }
                }
                list.add(designation);
                count++;
            }

            if (count == 0) {
                // org_type = (rset.getString("org_type_name"));
                parent_check = getParentName(org_type_id);
                if (parent_check != 0) {
                    getDesgnation(q, code, parent_check);
                } else {
                    //         parent_check=0;
                    list1 = getDesgn(q, code, parent_check);
                    list = list1;
                }
                //   list.add(list1);
            }

        } catch (Exception e) {
            System.out.println("Error:keypersonModel--getDesignationList()-- " + e);
        }

        return list;
    }

    public List<String> getDesgnation(String q, String code, int parent_check) {
        List<String> list1 = null;
        String org_type = "";
        int org_type_id = 0;
        int org_office_id = getOrgOfficeId(code);
        String designation = "";
        String query = " select d.designation from org_office_designation_map map,designation d where map.org_office_id='" + org_office_id + "'"
                + " and d.designation_id=map.designation_id ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            int count = 0;
            q = q.trim();
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {    // move cursor from BOR to valid record.                 
                designation = (rset.getString("designation"));
                list.add(designation);
            }

        } catch (Exception e) {
            System.out.println("Error:keypersonModel--getDesignationList()-- " + e);
        }

        return list;
    }

    public int getOrgOfficeId(String org_office) {
        int org_office_id = 0;
        String query = "SELECT org_office_id FROM org_office WHERE  org_office_name = '" + org_office + "'  and active='Y' ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            org_office_id = rset.getInt("org_office_id");
        } catch (Exception e) {
            System.out.println("Error: OrganisationMapModel--" + e);
        }

        return org_office_id;
    }

    public int getParentName(int code) {
        int parent_org_id = 0;
        //  String query = "SELECT designation from designation as d where d.active='Y' ";
        //   + " AND if('"+ code +"' = '' , oft.org_office_code like '%%' , oft.org_office_code = ? ) ";
//        String query1 = "  select  o.organisation_name,d.designation from organisation_name o,org_office oft,organisation_designation od,designation d "
//                        + "  where oft.org_office_name='"+code+"' and od.organisation_id=o.organisation_id and "
//                        + " d.designation_id=od.organisation_designation_map_id_1 and "                   
//                        + "  oft.organisation_id=o.organisation_id and o.active='Y' and oft.active='Y' and od.active='Y' and d.active='Y'  ";
        String query = "select ot.parent_org_id from organisation_type ot where organisation_type_id=" + code;

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            int count = 0;

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {    // move cursor from BOR to valid record.

                //  String organisation_name = (rset.getString("organisation_name"));
                // String org_type = (rset.getString("org_type_name"));
                parent_org_id = (rset.getInt("parent_org_id"));
                // String designation = rset.getString("designation");

                count++;

            }
            if (count == 0) {
                System.out.println("No such Designation exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--getDesignationList()-- " + e);
        }
        return parent_org_id;
    }

    public List<String> searchOrg(String q) {
        List<String> list = new ArrayList<String>();
        //   String query = "SELECT org.org_office_code FROM org_office AS org , org_office_type as oft "
        //      + " WHERE org.office_type_id=oft.office_type_id group by org.org_office_code where org.active='Y' and oft.active='Y' ";
        String query = "select distinct org.organisation_name from organisation_name  as org,org_office off where  off.organisation_id=org.organisation_id and org.active='Y' and  off.active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1,office_type);
            ResultSet rset = pstmt.executeQuery();

            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String org_office_code = rset.getString("organisation_name");
                if (org_office_code.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(org_office_code);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such Office code Exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:OrganisationMapModel--office code()-- " + e);
        }
        return list;
    }

    public int getOrganisationType_id(String office_type) {
        String query = "SELECT organisation_id FROM organisation_name WHERE  organisation_name = ?  and active=? ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //  pstmt.setString(1, office_code);
//            pstmt.setString(1, office_type);
//            pstmt.setString(2, "Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("organisation_id");
        } catch (Exception e) {
            System.out.println("Error: OrganisationMapModel--" + e);
        }
        return organisation_id;
    }

    public int insertRecordOrganisation(OrganisationName organisation_name) {
        int orgnameid = 0;
        String query = "INSERT INTO organisation_name(organisation_name,organisation_type_id,description,"
                + " revision_no,active,remark,organisation_code) VALUES(?,?,?,?,?,?,?) ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
//            pstmt.setInt(1,(organisation_name.getOrganisation_id()));
            pstmt.setString(1, (organisation_name.getOrganisation_name()));
            pstmt.setInt(2, (organisation_name.getOrganisation_type_id()));
            pstmt.setString(3, (organisation_name.getDescription()));
            pstmt.setInt(4, organisation_name.getRevision_no());
            pstmt.setString(5, "Y");
            pstmt.setString(6, "OK");
            pstmt.setString(7, organisation_name.getOrganisation_code());
            rowsAffected = pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                orgnameid = rs.getInt(1);
            }

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
        return orgnameid;
    }

    public int insertRecordOrgOffice(Org_Office orgOffice) {
        int orgnameid = 0;
        String query = "INSERT INTO "
                + "org_office( org_office_name,org_office_code,office_type_id, address_line1, "
                + "address_line2, address_line3, city_id, email_id1, email_id2, mobile_no1, mobile_no2, "
                + "landline_no1, landline_no2, landline_no3,revision_no,active,remark,organisation_id,parent_org_office_id,super,generation,latitude,longitude) "
                + "VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?)";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
//            pstmt.setInt(1,(organisation_name.getOrganisation_id()));
            pstmt.setString(1, (orgOffice.getOrg_office_name()));
            pstmt.setString(2, orgOffice.getOrg_office_code());
            pstmt.setInt(3, 1);
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
            pstmt.setInt(15, 0);
            pstmt.setString(16, "Y");
            pstmt.setString(17, "ok");
            pstmt.setInt(18, orgOffice.getOrganisation_id());

            pstmt.setInt(19, 0);
            pstmt.setString(20, "Y");
            pstmt.setInt(21, 1);
            pstmt.setString(22, orgOffice.getLatitude());
            pstmt.setString(23, orgOffice.getLongitude());
            rowsAffected = pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                orgnameid = rs.getInt(1);
            }

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
        return orgnameid;
    }

    public int getorgtypeidfromname(String id_type) {
        String query = "SELECT organisation_type_id FROM organisation_type WHERE org_type_name = ? and active='Y'";
        int designation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1, id_type);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            designation_id = rset.getInt("organisation_type_id");
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--getorgtypeidfromname-- " + e);
        }
        return designation_id;
    }

    public int getofficetypeidfromname(String id_type) {
        String query = "SELECT office_type_id FROM org_office_type WHERE office_type = ? and active='Y'";
        int designation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //pstmt.setString(1, id_type);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            designation_id = rset.getInt("office_type_id");
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--getorgtypeidfromname-- " + e);
        }
        return designation_id;
    }

    public int makeorgoffice(FormDataOfficeBean fd) {
        int officeid = 0;
        int orgnameid = 0;
        int orgtypeid = getorgtypeidfromname(fd.getOrgtype());
        int officetypeid = getofficetypeidfromname(fd.getOfficetype());
        int cityid = getCity_id(fd.getOffcity());

        String query1 = "insert into organisation_name(organisation_name,organisation_type_id,organisation_code) values(?,?,?)";
        String query2 = "insert into org_office(org_office_name,organisation_id,office_type_id,city_id,address_line1,email_id1,mobile_no1,landline_no1,org_office_code,revision_no,remark,generation,super)"
                + " values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, fd.getOrgname());
            pstmt.setInt(2, orgtypeid);
            pstmt.setString(3, fd.getOrgcode());
            int i = pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                orgnameid = rs.getInt(1);
            }

            if (i > 0) {
                PreparedStatement pstmt2 = connection.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);
                pstmt2.setString(1, fd.getOffname());
                pstmt2.setInt(2, orgnameid);
                pstmt2.setInt(3, officetypeid);
                pstmt2.setInt(4, cityid);
                pstmt2.setString(5, fd.getOffaddress());
                pstmt2.setString(6, fd.getOffemail());
                pstmt2.setString(7, fd.getOffnumber());
                pstmt2.setString(8, fd.getOfflandline());
                pstmt2.setString(9, fd.getOffcode());
                pstmt2.setInt(10, 0);
                pstmt2.setString(11, "Tarun");
                pstmt2.setInt(12, 1);
                pstmt2.setString(13, "N");
                int j = pstmt2.executeUpdate();
                ResultSet rs2 = pstmt2.getGeneratedKeys();
                if (rs2.next()) {
                    officeid = rs2.getInt(1);

                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(KeypersonModel.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return officeid;
    }

    public int getdesignationId(String office_type) {
        String query = "SELECT * FROM designation WHERE  designation = ?  and active=? ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //  pstmt.setString(1, office_code);
            // pstmt.setString(1, office_type);
            // pstmt.setString(2, "Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("designation_id");
        } catch (Exception e) {
            System.out.println("Error: OrganisationMapModel--" + e);
        }
        return organisation_id;
    }

    public int insertFamily(KeyPerson key) throws SQLException {

        int rowsAffected = 0;
        DateFormat dateFormat1 = new SimpleDateFormat("dd.MMMMM.yyyy");
        DateFormat dateFormat = new SimpleDateFormat("dd.MMMMM.yyyy/ hh:mm:ss aaa");
        Date date = new Date();
        String current_date = dateFormat.format(date);
        String image_uploaded_for = "key_person";
        String query1 = "", query2 = "", updateQuery = "";
        PreparedStatement pstmt = null;
        int kp_id = 0;
        try {
            query1 = "INSERT INTO key_person( salutation, key_person_name, designation_id, org_office_id, city_id, address_line1, "
                    + " mobile_no1, landline_no1,  email_id1 ,emp_code, father_name, date_of_birth,revision_no,active,remark,latitude,longitude,id_type_id,id_no,password,bloodgroup,emergency_contact_name,emergency_contact_mobile,isVarified,gender,family_office,family_designation,relation) "//image_path,
                    + "VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            pstmt = connection.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
            //    pstmt.setString(16, photo_destination + imageName);
            //   pstmt.setInt(17,Integer.parseInt(key.getImage_id()));
            //}
            pstmt.setString(1, key.getSalutation());
            pstmt.setString(2, (key.getKey_person_name()));
            if (key.getDesignation_id() == 0) {
                pstmt.setString(3, null);
            } else {
                pstmt.setInt(3, key.getDesignation_id());
            }
            if (key.getOrg_office_id() == 0) {
                pstmt.setString(4, null);
            } else {
                pstmt.setInt(4, key.getOrg_office_id());
            }

            pstmt.setInt(5, key.getCity_id());
            pstmt.setString(6, (key.getAddress_line1()));

            pstmt.setString(7, key.getMobile_no1());

            pstmt.setString(8, key.getLandline_no1());

            pstmt.setString(9, key.getEmail_id1());

            pstmt.setString(10, key.getEmp_code());
            pstmt.setString(11, (key.getFather_name()));
            pstmt.setString(12, key.getDate_of_birth());
            pstmt.setInt(13, key.getRevision_no());
            pstmt.setString(14, "Y");
            pstmt.setString(15, "good");
            pstmt.setString(16, key.getLatitude());
            pstmt.setString(17, key.getLongitude());
            pstmt.setInt(18, key.getId_type_d());
            pstmt.setString(19, key.getId_no());
            pstmt.setString(20, "1234");
            pstmt.setString(21, key.getBlood());
            pstmt.setString(22, key.getEmergency_name());
            pstmt.setString(23, key.getEmergency_number());
            pstmt.setString(24, "No");
            pstmt.setString(25, key.getGender());
            pstmt.setInt(26, key.getFamilyofiiceid());
            pstmt.setInt(27, key.getFamilydesignationid());
            pstmt.setString(28, key.getRelation());
            rowsAffected = pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                kp_id = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--insertRecord-- " + e);
        }

        if (rowsAffected > 0) {
//                GeneralModel generalModel = new GeneralModel();
//                generalModel.sendSmsToAssignedFor(key.getMobile_no1(), message);
        }
        // rowsAffected = writeImage(key, itr, destination);
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return kp_id;
    }

    public List<String> getOrgOfficeCode(String q) {
        List<String> list = new ArrayList<String>();
        //   String query = "SELECT org.org_office_code FROM org_office AS org , org_office_type as oft "
        //      + " WHERE org.office_type_id=oft.office_type_id group by org.org_office_code where org.active='Y' and oft.active='Y' ";
        String query = "select org.org_office_code from org_office as org where org.active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1,office_type);
            ResultSet rset = pstmt.executeQuery();

            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String org_office_code = rset.getString("org_office_code");
                if (org_office_code.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(org_office_code);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such Office code Exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:OrganisationMapModel--office code()-- " + e);
        }
        return list;
    }

    public List<String> searchOrgOfficeCode(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select org.org_office_code from org_office as org,key_person as kp where org.org_office_id=kp.org_office_id and kp.active='Y'";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1,office_type);
            ResultSet rset = pstmt.executeQuery();

            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String org_office_code = rset.getString("org_office_code");
                if (org_office_code.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(org_office_code);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such Office code Exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:OrganisationMapModel--office code()-- " + e);
        }
        return list;
    }

    public List<String> searchfamilyOfficeCode(String q, String org_name) {
        List<String> list = new ArrayList<String>();
        String query = "select distinct org.org_office_code from org_office as org ,organisation_name onn where org.active='Y'"
                + " and onn.active='Y' and org.organisation_id=onn.organisation_id ";
        if (!org_name.equals("") && org_name != null) {
            query += " and onn.organisation_name='" + org_name + "' ";
        }

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1,office_type);
            ResultSet rset = pstmt.executeQuery();

            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String org_office_code = rset.getString("org_office_code");
                if (org_office_code.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(org_office_code);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such Office code Exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:OrganisationMapModel--office code()-- " + e);
        }
        return list;
    }

    public List<String> getImagePath(String q) {

        List<String> list = new ArrayList<String>();
        String query = "SELECT image_name from general_image_details where key_person_id='" + q + "' and active='Y' ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String image_name = rset.getString("image_name");

                list.add(image_name);
                count++;

            }
            if (count == 0) {
                list.add("No image_name of Exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--getImagePath()-- " + e);
        }
        return list;
    }

    public List<String> getsearchOrganisation(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT org.org_office_code FROM org_office AS org , org_office_type as oft "
                + " WHERE org.office_type_id=oft.office_type_id group by org.org_office_code ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String org_office_code = rset.getString("org_office_code");
                if (org_office_code.startsWith(q)) {
                    list.add(org_office_code);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Person of such Office Code Exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--getDesignationList()-- " + e);
        }
        return list;
    }

    public List<String> getSearchEmpCode(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select kp.emp_code from key_person kp "
                + "left join org_office as of on kp.org_office_id = of.org_office_id "
                + " GROUP BY kp.emp_code ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String emp_code = rset.getString("emp_code");
                if (emp_code.startsWith(q)) {
                    list.add(emp_code);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Person of such Office Code Exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--getDesignationList()-- " + e);
        }
        return list;
    }

    public byte[] generateKeyperSonList(String jrxmlFilePath, List list) {
        byte[] reportInbytes = null;
        HashMap mymap = new HashMap();
        try {
            JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(list);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, jrBean);
        } catch (Exception e) {
            System.out.println("OfficerBookModel generatReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public String exist_image(String emp_code) {
        String image_name = null;
        try {
            String query = " SELECT image_path FROM key_person "
                    + " where emp_code = ? ";
            PreparedStatement psmt = connection.prepareStatement(query);
            // psmt.setString(1, emp_code);
            ResultSet rst = psmt.executeQuery();
            if (rst.next()) {
                image_name = rst.getString("image_path");
            }
        } catch (Exception e) {
            System.out.println("ERROR keyPerson Model exist_image" + e);
        }
        return image_name;
    }
    
    public String getImagePath(String key_person_id, String uploadedFor) {
        
        String img_name = "";
        if (uploadedFor.equals("ph")) {
            uploadedFor = "key_person_photo";
        } else {
            uploadedFor = "key_person_ID";
        }
        String destination_path = "";
//     String query = "SELECT image_name, destination_path FROM general_image_details gid, key_person kp, image_destination dp "
//             + " WHERE dp.image_destination_id=gid.image_destination_id "
//             + " AND gid.general_image_details_id = kp.general_image_details_id "
//             + " AND kp.emp_code = '" + emp_code + "'";
        String query = "SELECT general_image_details_id,image_name, destination_path "
                + " FROM general_image_details gid, image_destination dp, image_uploaded_for iuf "
                + " WHERE dp.image_destination_id=gid.image_destination_id AND iuf.image_uploaded_for_id=dp.image_uploaded_for_id "
                + " AND iuf.image_uploaded_for='" + uploadedFor + "' AND gid.key_person_id=" + key_person_id + " "
                + " and gid.active='Y' and dp.active='Y' and iuf.active='Y' "
                + " ORDER BY general_image_details_id DESC";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                img_name = rs.getString("image_name");
                destination_path = rs.getString("destination_path");
            }
            //String[] img_path = img_name.split("-");
            destination_path = destination_path + img_name;
        } catch (Exception ex) {
            System.out.println("ERROR: in getImagePath in TrafficPoliceSearchModel : " + ex);
        }
        return destination_path;
    }

    public String getDestinationPath(String key_person_name) {
        String image_path = null;
        try {
            String query = " SELECT image_path FROM key_person "
                    + " where key_person_name = ? ";
            PreparedStatement psmt = connection.prepareStatement(query);
            // psmt.setString(1, key_person_name);
            ResultSet rst = psmt.executeQuery();
            if (rst.next()) {
                image_path = rst.getString("image_path");
            }
        } catch (Exception e) {
            System.out.println("ERROR keyPerson Model exist_image" + e);
        }
        return image_path;
    }

    public String getDestination_Path(String image_uploaded_for) {
        String destination_path = "";
        String query = " SELECT destination_path FROM image_destination id, image_uploaded_for  iuf "
                + " WHERE id.image_uploaded_for_id = iuf.image_uploaded_for_id "
                + " AND iuf.image_uploaded_for = '" + image_uploaded_for + "' ";//traffic_police
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                destination_path = rs.getString("destination_path");
            }
        } catch (Exception ex) {
            System.out.println("ERROR: in getTrafficPoliceId in TraffiPoliceSearchModel : " + ex);
        }
        return destination_path;
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
            System.out.println("keypersonModel closeConnection() Error: " + e);
        }
    }

    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("OrganisationTypeModel setConnection() Error: " + e);
        }
    }
}
