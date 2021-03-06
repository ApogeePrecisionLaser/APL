/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webservice.model;

import com.organization.tableClasses.KeyPerson;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;

/**
 *
 * @author Vikrant
 */
public class APLWebServiceModel {

    private Connection connection;
    private String driver, url, user, password;
    private String message, messageBGColor;

    public JSONArray getOrganisationType(String number) {
        JSONArray rowData = new JSONArray();
        String query = null;
//        query = "select organisation_type_id,org_type_name,description,remark,parent_org_id,super "
//                + " from organisation_type ot "
//                + " where ot.active='Y'";

        query = " select ot.organisation_type_id,ot.org_type_name,ot.description,ot.remark,ot.parent_org_id,ot.super "
                + " from key_person kp,organisation_name onn, organisation_type ot,org_office oo,org_office_type oot,"
                + " org_office_designation_map oodm,designation d "
                + " where kp.active='y'and onn.active='y' and ot.active='y' and oo.active='y' and oodm.active='y' "
                + " and d.active='y' and oot.active='y' and oodm.org_office_id=oo.org_office_id and oodm.designation_id=d.designation_id "
                + " and kp.org_office_designation_map_id=oodm.org_office_designation_map_id "
                + " and oo.office_type_id=oot.office_type_id and oo.organisation_id=onn.organisation_id "
                + " and ot.organisation_type_id=onn.organisation_type_id and kp.org_office_id=oo.org_office_id "
                + " and " + number + " in(kp.mobile_no1,kp.mobile_no2) ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("organisation_type_id", rset.getInt("organisation_type_id"));
                obj.put("org_type_name", rset.getString("org_type_name"));
                obj.put("description", rset.getString("description"));
                obj.put("remark", rset.getString("remark"));
                obj.put("parent_org_id", rset.getInt("parent_org_id"));
                obj.put("super", rset.getString("super"));
                rowData.put(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside getOrganisationType of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getOrganisationName(String number) {
        JSONArray rowData = new JSONArray();
        String query = null;
//        query = "select organisation_id,organisation_name,organisation_type_id,organisation_code,description,remark "
//                + " from organisation_name onn "
//                + " where onn.active='Y'";

        query = " select onn.organisation_id,onn.organisation_name,onn.organisation_type_id,onn.organisation_code,onn.description,onn.remark "
                + " from key_person kp,organisation_name onn, organisation_type ot,org_office oo,org_office_type oot,"
                + " org_office_designation_map oodm,designation d "
                + " where kp.active='y'and onn.active='y' and ot.active='y' and oo.active='y' and oodm.active='y' "
                + " and d.active='y' and oot.active='y' and oodm.org_office_id=oo.org_office_id and oodm.designation_id=d.designation_id "
                + " and kp.org_office_designation_map_id=oodm.org_office_designation_map_id "
                + " and oo.office_type_id=oot.office_type_id and oo.organisation_id=onn.organisation_id "
                + " and ot.organisation_type_id=onn.organisation_type_id and kp.org_office_id=oo.org_office_id "
                + " and " + number + " in(kp.mobile_no1,kp.mobile_no2) ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("organisation_id", rset.getInt("organisation_id"));
                obj.put("organisation_name", rset.getString("organisation_name"));
                obj.put("organisation_type_id", rset.getInt("organisation_type_id"));
                obj.put("organisation_code", rset.getString("organisation_code"));
                obj.put("description", rset.getString("description"));
                obj.put("remark", rset.getString("remark"));
                rowData.put(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside getOrganisationName of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getOrgOfficeType(String number) {
        JSONArray rowData = new JSONArray();
        String query = null;
//        query = "select office_type_id,office_type,description,remark "
//                + " from org_office_type oot "
//                + " where oot.active='Y'";

        query = " select oot.office_type_id,oot.office_type,oot.description,oot.remark "
                + " from key_person kp,organisation_name onn, organisation_type ot,org_office oo,org_office_type oot,"
                + " org_office_designation_map oodm,designation d "
                + " where kp.active='y'and onn.active='y' and ot.active='y' and oo.active='y' and oodm.active='y' "
                + " and d.active='y' and oot.active='y' and oodm.org_office_id=oo.org_office_id and oodm.designation_id=d.designation_id "
                + " and kp.org_office_designation_map_id=oodm.org_office_designation_map_id "
                + " and oo.office_type_id=oot.office_type_id and oo.organisation_id=onn.organisation_id "
                + " and ot.organisation_type_id=onn.organisation_type_id and kp.org_office_id=oo.org_office_id "
                + " and " + number + " in(kp.mobile_no1,kp.mobile_no2) ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("office_type_id", rset.getInt("office_type_id"));
                obj.put("office_type", rset.getString("office_type"));
                obj.put("description", rset.getString("description"));
                obj.put("remark", rset.getString("remark"));
                rowData.put(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside getOrgOfficeType of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getOrgOffice(String number) {
        JSONArray rowData = new JSONArray();
        String query = null;
//        query = " select org_office_id,org_office_name,organisation_id,office_type_id,city_id,address_line1,address_line2,address_line3,email_id1, "
//                + " email_id2,mobile_no1,mobile_no2,landline_no1,landline_no2,landline_no3,service_tax_reg_no,vat_reg_no,org_office_code,remark,"
//                + " building_id,serial_no,super,parent_org_office_id,latitude,longitude "
//                + " from org_office oo "
//                + " where oo.active='Y'";

        query = " select oo.org_office_id,oo.org_office_name,oo.organisation_id,oo.office_type_id,oo.city_id,oo.address_line1,"
                + " oo.address_line2,oo.address_line3,oo.email_id1,oo.email_id2,oo.mobile_no1,oo.mobile_no2,oo.landline_no1,"
                + " oo.landline_no2,oo.landline_no3,oo.service_tax_reg_no,oo.vat_reg_no,oo.org_office_code,oo.remark,oo.building_id,"
                + " oo.serial_no,oo.super,oo.parent_org_office_id,oo.latitude,oo.longitude "
                + " from key_person kp,organisation_name onn, organisation_type ot,org_office oo,org_office_type oot,"
                + " org_office_designation_map oodm,designation d "
                + " where kp.active='y'and onn.active='y' and ot.active='y' and oo.active='y' and oodm.active='y' "
                + " and d.active='y' and oot.active='y' and oodm.org_office_id=oo.org_office_id and oodm.designation_id=d.designation_id "
                + " and kp.org_office_designation_map_id=oodm.org_office_designation_map_id "
                + " and oo.office_type_id=oot.office_type_id and oo.organisation_id=onn.organisation_id "
                + " and ot.organisation_type_id=onn.organisation_type_id and kp.org_office_id=oo.org_office_id "
                + " and " + number + " in(kp.mobile_no1,kp.mobile_no2) ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("org_office_id", rset.getInt("org_office_id"));
                obj.put("org_office_name", rset.getString("org_office_name"));
                obj.put("organisation_id", rset.getInt("organisation_id"));
                obj.put("office_type_id", rset.getInt("office_type_id"));
                obj.put("city_id", rset.getInt("city_id"));
                obj.put("address_line1", rset.getString("address_line1"));
                obj.put("address_line2", rset.getString("address_line2"));
                obj.put("address_line3", rset.getString("address_line3"));
                obj.put("email_id1", rset.getString("email_id1"));
                obj.put("email_id2", rset.getString("email_id2"));
                obj.put("mobile_no1", rset.getString("mobile_no1"));
                obj.put("mobile_no2", rset.getString("mobile_no2"));
                obj.put("landline_no1", rset.getString("landline_no1"));
                obj.put("landline_no2", rset.getString("landline_no2"));
                obj.put("landline_no3", rset.getString("landline_no3"));
                obj.put("service_tax_reg_no", rset.getString("service_tax_reg_no"));
                obj.put("vat_reg_no", rset.getString("vat_reg_no"));
                obj.put("org_office_code", rset.getString("org_office_code"));
                obj.put("remark", rset.getString("remark"));
                obj.put("building_id", rset.getInt("building_id"));
                obj.put("serial_no", rset.getString("serial_no"));
                obj.put("super", rset.getString("super"));
                obj.put("parent_org_office_id", rset.getInt("parent_org_office_id"));
                obj.put("latitude", rset.getString("latitude"));
                obj.put("longitude", rset.getString("longitude"));

                rowData.put(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside getOrgOffice of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getOrgOfficeDesignationMap(String number) {
        JSONArray rowData = new JSONArray();
        String query = null;
//        query = "select org_office_designation_map_id,org_office_id,designation_id,remark,serial_no "
//                + " from org_office_designation_map oodt "
//                + " where oodt.active='Y'";

        query = " select oodm.org_office_designation_map_id,oodm.org_office_id,oodm.designation_id,oodm.remark,oodm.serial_no "
                + " from key_person kp,organisation_name onn, organisation_type ot,org_office oo,org_office_type oot,"
                + " org_office_designation_map oodm,designation d "
                + " where kp.active='y'and onn.active='y' and ot.active='y' and oo.active='y' and oodm.active='y' "
                + " and d.active='y' and oot.active='y' and oodm.org_office_id=oo.org_office_id and oodm.designation_id=d.designation_id "
                + " and kp.org_office_designation_map_id=oodm.org_office_designation_map_id "
                + " and oo.office_type_id=oot.office_type_id and oo.organisation_id=onn.organisation_id "
                + " and ot.organisation_type_id=onn.organisation_type_id and kp.org_office_id=oo.org_office_id "
                + " and " + number + " in(kp.mobile_no1,kp.mobile_no2) ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("org_office_designation_map_id", rset.getInt("org_office_designation_map_id"));
                obj.put("org_office_id", rset.getInt("org_office_id"));
                obj.put("designation_id", rset.getInt("designation_id"));
                obj.put("remark", rset.getString("remark"));
                obj.put("serial_no", rset.getString("serial_no"));

                rowData.put(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside getOrgOfficeDesignationMap of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getDesignation(String number) {
        JSONArray rowData = new JSONArray();
        String query = null;
//        query = "select designation_id,designation,description, designation_code,remark "
//                + " from designation d "
//                + " where d.active='Y'";

        query = " select d.designation_id,d.designation,d.description,d.designation_code,d.remark "
                + " from key_person kp,organisation_name onn, organisation_type ot,org_office oo,org_office_type oot,"
                + " org_office_designation_map oodm,designation d "
                + " where kp.active='y'and onn.active='y' and ot.active='y' and oo.active='y' and oodm.active='y' "
                + " and d.active='y' and oot.active='y' and oodm.org_office_id=oo.org_office_id and oodm.designation_id=d.designation_id "
                + " and kp.org_office_designation_map_id=oodm.org_office_designation_map_id "
                + " and oo.office_type_id=oot.office_type_id and oo.organisation_id=onn.organisation_id "
                + " and ot.organisation_type_id=onn.organisation_type_id and kp.org_office_id=oo.org_office_id "
                + " and " + number + " in(kp.mobile_no1,kp.mobile_no2) ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("designation_id", rset.getInt("designation_id"));
                obj.put("designation", rset.getString("designation"));
                obj.put("description", rset.getString("description"));
                obj.put("designation_code", rset.getInt("designation_code"));
                obj.put("remark", rset.getString("remark"));
                rowData.put(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside getDesignation of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getCity(String number) {
        JSONArray rowData = new JSONArray();
        String query = null;
//        query = "select city_id,city_name,pin_code,std_code,city_description,remark "
//                + " from city c "
//                + " where c.active='Y'";

        query = " select c.city_id,c.city_name,c.pin_code,c.std_code,c.city_description,c.remark "
                + " from key_person kp,organisation_name onn, organisation_type ot,org_office oo,org_office_type oot,"
                + " org_office_designation_map oodm,designation d,city c "
                + " where kp.active='y'and onn.active='y' and ot.active='y' and oo.active='y' and oodm.active='y' "
                + " and d.active='y' and oot.active='y' and oodm.org_office_id=oo.org_office_id and oodm.designation_id=d.designation_id "
                + " and kp.org_office_designation_map_id=oodm.org_office_designation_map_id "
                + " and oo.office_type_id=oot.office_type_id and oo.organisation_id=onn.organisation_id "
                + " and ot.organisation_type_id=onn.organisation_type_id and kp.org_office_id=oo.org_office_id "
                + " and c.city_id=oo.city_id "
                + " and " + number + " in(kp.mobile_no1,kp.mobile_no2) ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("city_id", rset.getInt("city_id"));
                obj.put("city_name", rset.getString("city_name"));
                obj.put("pin_code", rset.getInt("pin_code"));
                obj.put("std_code", rset.getString("std_code"));
                obj.put("city_description", rset.getString("city_description"));
                obj.put("remark", rset.getString("remark"));
                rowData.put(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside getCity of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getKeyPerson(String number) {
        JSONArray rowData = new JSONArray();
        String query = null;
//        query = "select key_person_id,salutation,key_person_name,org_office_id,city_id,address_line1,address_line2,address_line3,mobile_no1,"
//                + " mobile_no2,landline_no1,landline_no2,email_id1,email_id2,designation_id,org_office_designation_map_id,emp_code,father_name,"
//                + " date_of_birth,latitude,longitude,remark,password,isVarified,bloodgroup,emergency_contact_name,emergency_contact_mobile,gender,"
//                + " id_type_id,id_no,relation,family_office,family_designation "
//                + " from key_person kp "
//                + " where kp.active='Y'";

        query = " select kp.key_person_id,kp.salutation,kp.key_person_name,kp.org_office_id,kp.city_id,kp.address_line1,kp.address_line2,"
                + " kp.address_line3, "
                + " kp.mobile_no1,kp.mobile_no2,kp.landline_no1,kp.landline_no2,kp.email_id1,kp.email_id2,kp.designation_id, "
                + " kp.org_office_designation_map_id,kp.emp_code,kp.father_name,kp.date_of_birth,kp.latitude,kp.longitude, "
                + " kp.remark,kp.password,kp.isVarified,kp.bloodgroup,kp.emergency_contact_name,kp.emergency_contact_mobile, "
                + " kp.id_type_id,kp.id_no,kp.relation,kp.family_office,kp.family_designation "
                + " from key_person kp,organisation_name onn, organisation_type ot,org_office oo,org_office_type oot, "
                + " org_office_designation_map oodm,designation d "
                + " where kp.active='y'and onn.active='y' and ot.active='y' and oo.active='y' and oodm.active='y' "
                + " and d.active='y' and oot.active='y' and oodm.org_office_id=oo.org_office_id and oodm.designation_id=d.designation_id "
                + " and kp.org_office_designation_map_id=oodm.org_office_designation_map_id "
                + " and oo.office_type_id=oot.office_type_id and oo.organisation_id=onn.organisation_id "
                + " and ot.organisation_type_id=onn.organisation_type_id and kp.org_office_id=oo.org_office_id "
                + " and " + number + " in(kp.mobile_no1,kp.mobile_no2) ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("key_person_id", rset.getInt("key_person_id"));
                obj.put("salutation", rset.getString("salutation"));
                obj.put("key_person_name", rset.getString("key_person_name"));
                obj.put("org_office_id", rset.getInt("org_office_id"));
                obj.put("city_id", rset.getInt("city_id"));
                obj.put("address_line1", rset.getString("address_line1"));
                obj.put("address_line2", rset.getString("address_line2"));
                obj.put("address_line3", rset.getString("address_line3"));
                obj.put("mobile_no1", rset.getString("mobile_no1"));
                obj.put("mobile_no2", rset.getString("mobile_no2"));
                obj.put("landline_no1", rset.getString("landline_no1"));
                obj.put("landline_no2", rset.getString("landline_no2"));
                obj.put("email_id1", rset.getString("email_id1"));
                obj.put("email_id2", rset.getString("email_id2"));
                obj.put("designation_id", rset.getInt("designation_id"));
                obj.put("org_office_designation_map_id", rset.getInt("org_office_designation_map_id"));
                obj.put("emp_code", rset.getInt("emp_code"));
                obj.put("father_name", rset.getString("father_name"));
                obj.put("date_of_birth", rset.getString("date_of_birth"));
                obj.put("latitude", rset.getString("latitude"));
                obj.put("longitude", rset.getString("longitude"));
                obj.put("remark", rset.getString("remark"));
                obj.put("password", rset.getString("password"));
                obj.put("isVarified", rset.getString("isVarified"));
                obj.put("bloodgroup", rset.getString("bloodgroup"));
                obj.put("emergency_contact_name", rset.getString("emergency_contact_name"));
                obj.put("emergency_contact_mobile", rset.getString("emergency_contact_mobile"));
                obj.put("id_type_id", rset.getInt("id_type_id"));
                obj.put("id_no", rset.getString("id_no"));
                obj.put("relation", rset.getString("relation"));
                obj.put("family_office", rset.getInt("family_office"));
                obj.put("family_designation", rset.getInt("family_designation"));
                rowData.put(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside getKeyPerson of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getImageData(String number) {
        JSONArray rowData = new JSONArray();
        String query = null;
        JSONObject obj = new JSONObject();
        String key_person_id = "", key_person_name = "", image_name = "", person_photo = "", person_id_photo = "";
        try {
            query = " select distinct kp.key_person_name,gid.image_name,idd.destination_path,idd.image_uploaded_for_id, "
                    + " iuf.image_uploaded_for,kp.key_person_id,concat(idd.destination_path,gid.image_name) as image_path "
                    + " from key_person kp,general_image_details gid,image_destination idd,image_uploaded_for iuf "
                    + " where kp.active='y' and gid.active='y' and idd.active='y' and iuf.active='y' and "
                    + " kp.key_person_id=gid.key_person_id and gid.image_destination_id=idd.image_destination_id "
                    //+ " and iuf.image_uploaded_for_id=idd.image_uploaded_for_id "
                    + " and 9758128792 in(kp.mobile_no1,kp.mobile_no2) ";
            PreparedStatement psmt = connection.prepareStatement(query);
            ResultSet rst = psmt.executeQuery();
            while (rst.next()) {
//                obj.put("key_person_id", rst.getString("key_person_id"));
//                obj.put("key_person_name", rst.getString("key_person_name"));
//                obj.put("image_name", rst.getString("image_name"));
                key_person_id = rst.getString("key_person_id");
                key_person_name = rst.getString("key_person_name");
                image_name = rst.getString("image_name");
                //obj.put("destination_path", rst.getString("destination_path"));
                //obj.put("image_uploaded_for", rst.getString("image_uploaded_for"));
                //obj.put("image_uploaded_for_id", rst.getString("image_uploaded_for_id"));
                //obj.put("image_path", rst.getString("image_path"));

                // for image
                String encodedString = "";
                byte[] fileContent = null;
                java.nio.file.Path path = Paths.get(rst.getString("image_path"));
                //

                try {
                    fileContent = Files.readAllBytes(path);
                } catch (IOException ex) {
                    System.out.println("com.webservice.model.APLWebServiceModel.getImageData() -" + ex);
                }

                try {
                    encodedString = Base64.getEncoder().encodeToString(fileContent);
                    //obj.put(rst.getString("image_uploaded_for"), encodedString); 
                    if (rst.getInt("image_uploaded_for_id") == 1) {
                        person_photo = encodedString;
                    } else {
                        person_id_photo = encodedString;
                    }
                } catch (Exception ex) {
                    System.out.println("com.webservice.model.APLWebServiceModel.getImageData() -" + ex);
                }
                // end for image
                //rowData.put(obj);
            }

            obj.put("key_person_id", key_person_id);
            obj.put("key_person_name", key_person_name);
            obj.put("image_name", image_name);
            obj.put("person_photo", person_photo);
            obj.put("person_id_photo", person_id_photo);
            rowData.put(obj);

        } catch (Exception e) {
            System.out.println("com.webservice.model.APLWebServiceModel.getImageData() -" + e);
        }
        return rowData;
    }

    public JSONArray getItemType(String number) {
        JSONArray rowData = new JSONArray();
        String query = null;
//        query = "select item_type_id,item_type,description,remark "
//                + " from item_type itemt "
//                + " where itemt.active='Y'";

        query = " select onn.organisation_id,onn.organisation_name,onn.organisation_type_id,onn.organisation_code,onn.description,onn.remark "
                + " from key_person kp,organisation_name onn, organisation_type ot,org_office oo,org_office_type oot,"
                + " org_office_designation_map oodm,designation d "
                + " where kp.active='y'and onn.active='y' and ot.active='y' and oo.active='y' and oodm.active='y' "
                + " and d.active='y' and oot.active='y' and oodm.org_office_id=oo.org_office_id and oodm.designation_id=d.designation_id "
                + " and kp.org_office_designation_map_id=oodm.org_office_designation_map_id "
                + " and oo.office_type_id=oot.office_type_id and oo.organisation_id=onn.organisation_id "
                + " and ot.organisation_type_id=onn.organisation_type_id and kp.org_office_id=oo.org_office_id "
                + " and " + number + " in(kp.mobile_no1,kp.mobile_no2) ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("item_type_id", rset.getInt("item_type_id"));
                obj.put("item_type", rset.getString("item_type"));
                obj.put("description", rset.getString("description"));
                obj.put("remark", rset.getString("remark"));
                rowData.put(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside getItemType of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getItemNames(String number) {
        JSONArray rowData = new JSONArray();
        String query = null;
//        query = "select item_names_id,item_name,item_type_id,item_code,quantity,description,remark "
//                + " from item_names itemn "
//                + " where itemn.active='Y'";

        query = " select onn.organisation_id,onn.organisation_name,onn.organisation_type_id,onn.organisation_code,onn.description,onn.remark "
                + " from key_person kp,organisation_name onn, organisation_type ot,org_office oo,org_office_type oot,"
                + " org_office_designation_map oodm,designation d "
                + " where kp.active='y'and onn.active='y' and ot.active='y' and oo.active='y' and oodm.active='y' "
                + " and d.active='y' and oot.active='y' and oodm.org_office_id=oo.org_office_id and oodm.designation_id=d.designation_id "
                + " and kp.org_office_designation_map_id=oodm.org_office_designation_map_id "
                + " and oo.office_type_id=oot.office_type_id and oo.organisation_id=onn.organisation_id "
                + " and ot.organisation_type_id=onn.organisation_type_id and kp.org_office_id=oo.org_office_id "
                + " and " + number + " in(kp.mobile_no1,kp.mobile_no2) ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("item_names_id", rset.getInt("item_names_id"));
                obj.put("item_name", rset.getString("item_name"));
                obj.put("item_type_id", rset.getInt("item_type_id"));
                obj.put("item_code", rset.getString("item_code"));
                obj.put("quantity", rset.getInt("quantity"));
                obj.put("description", rset.getString("description"));
                obj.put("remark", rset.getString("remark"));
                rowData.put(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside getItemNames of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getGeneralImageDetails(String number) {
        JSONArray rowData = new JSONArray();
        String query = null;
//        query = "select general_image_details_id,image_name,image_destination_id,key_person_id,description,date_time,remark "
//                + " from general_image_details gid "
//                + " where gid.active='Y' ";

        query = " select gid.general_image_details_id,gid.image_name,gid.image_destination_id,gid.key_person_id, "
                + " gid.description,gid.date_time,gid.remark "
                + " from key_person kp,organisation_name onn, organisation_type ot,org_office oo,org_office_type oot,"
                + " org_office_designation_map oodm,designation d,general_image_details gid "
                + " where kp.active='y'and onn.active='y' and ot.active='y' and oo.active='y' and oodm.active='y' "
                + " and d.active='y' and oot.active='y' and oodm.org_office_id=oo.org_office_id and oodm.designation_id=d.designation_id "
                + " and kp.org_office_designation_map_id=oodm.org_office_designation_map_id "
                + " and oo.office_type_id=oot.office_type_id and oo.organisation_id=onn.organisation_id "
                + " and ot.organisation_type_id=onn.organisation_type_id and kp.org_office_id=oo.org_office_id"
                + " and gid.key_person_id=kp.key_person_id "
                + " and " + number + " in(kp.mobile_no1,kp.mobile_no2) ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("general_image_details_id", rset.getInt("general_image_details_id"));
                obj.put("image_name", rset.getString("image_name"));
                obj.put("image_destination_id", rset.getInt("image_destination_id"));
                obj.put("key_person_id", rset.getInt("key_person_id"));
                obj.put("description", rset.getString("description"));
                obj.put("date_time", rset.getString("date_time"));
                obj.put("remark", rset.getString("remark"));
                rowData.put(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside getGeneralImageDetails of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getImageDestination(String number) {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select image_destination_id,destination_path,image_uploaded_for_id,remark"
                + " from image_destination idd "
                + " where idd.active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("image_destination_id", rset.getInt("image_destination_id"));
                obj.put("destination_path", rset.getString("destination_path"));
                obj.put("image_uploaded_for_id", rset.getInt("image_uploaded_for_id"));
                obj.put("remark", rset.getString("remark"));
                rowData.put(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside getImageDestination of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getImageUploadedFor(String number) {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select Image_uploaded_for_id,uploaded_table,image_uploaded_for,view_label,remark "
                + " from image_uploaded_for iuf "
                + " where iuf.active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("Image_uploaded_for_id", rset.getInt("Image_uploaded_for_id"));
                obj.put("uploaded_table", rset.getString("uploaded_table"));
                obj.put("image_uploaded_for", rset.getString("image_uploaded_for"));
                obj.put("view_label", rset.getString("view_label"));
                obj.put("remark", rset.getString("remark"));

                rowData.put(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside getImageUploadedFor of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getItemImageDetails(String number) {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = " select item_image_details_id,image_name,image_path,date_time,description,item_names_id,remark "
                + " from item_image_details iid "
                + " where iid.active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("item_image_details_id", rset.getInt("item_image_details_id"));
                obj.put("image_name", rset.getString("image_name"));
                obj.put("image_path", rset.getString("image_path"));
                obj.put("date_time", rset.getString("date_time"));
                obj.put("description", rset.getString("description"));
                obj.put("item_names_id", rset.getInt("item_names_id"));
                obj.put("remark", rset.getString("remark"));

                rowData.put(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside getItemImageDetails of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getIdType(String number) {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = " select id_type_id,id_type,remark "
                + " from id_type idt "
                + " where idt.active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("id_type_id", rset.getInt("id_type_id"));
                obj.put("id_type", rset.getString("id_type"));
                obj.put("remark", rset.getString("remark"));

                rowData.put(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside getIdType of survey: " + e);
        }
        return rowData;
    }

    public static String random(int size) {
        StringBuilder generatedToken = new StringBuilder();
        try {
            SecureRandom number = SecureRandom.getInstance("SHA1PRNG");
            // Generate 20 integers 0..20
            for (int i = 0; i < size; i++) {
                generatedToken.append(number.nextInt(9));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return generatedToken.toString();
    }

    public String sendSmsToAssignedFor(String numberStr1, String messageStr1) {
        String result = "";
        try {
            //String query = "SELECT si.user_name, si.user_password, si.sender_id FROM sms_info si ";
//            PreparedStatement pstmt = connection.prepareStatement(query);
//            ResultSet rset = pstmt.executeQuery();
//            rset.next();
            String host_url = "http://login.smsgatewayhub.com/api/mt/SendSMS?";;
//            String user_name = rset.getString("user_name");        // e.g. "jpss1277@gmail.com"
//            String user_password = rset.getString("user_password"); // e.g. "8826887606"
//            String sender_id = java.net.URLEncoder.encode(rset.getString("sender_id"), "UTF-8");         // e.g. "TEST+SMS"
            messageStr1 = java.net.URLEncoder.encode(messageStr1, "UTF-8");
            //String hex = calstr(messageStr1);
            // e.g. "TEST+SMS"
            //String msg = String.format("%x", new BigInteger(1, messageStr1.getBytes()));
            String queryString = "APIKey=WIOg7OdIzkmYTrqTsw262w&senderid=JPSOFT&channel=2&DCS=8&flashsms=0&number=" + numberStr1 + "&text=" + messageStr1 + "&route=";
            String url = host_url + queryString;
            result = callURL(url);
            System.out.println("SMS URL: " + url);
        } catch (Exception e) {
            result = e.toString();
            System.out.println("SMSModel sendSMS() Error: " + e);
        }
        return result;
    }

    private String callURL(String strURL) {
        String status = "";
        try {
            java.net.URL obj = new java.net.URL(strURL);
            HttpURLConnection httpReq = (HttpURLConnection) obj.openConnection();
            httpReq.setDoOutput(true);
            httpReq.setInstanceFollowRedirects(true);
            httpReq.setRequestMethod("GET");
            status = httpReq.getResponseMessage();
        } catch (MalformedURLException me) {
            status = me.toString();
        } catch (IOException ioe) {
            status = ioe.toString();
        } catch (Exception e) {
            status = e.toString();
        }
        return status;
    }

    public KeyPerson checkExisting(String number) {

        KeyPerson b2 = null;

        String query = "select * from key_person  where mobile_no1=? and active=?";
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ps.setString(1, number);
            ps.setString(2, "Y");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KeyPerson b = new KeyPerson();
                b.setMobile_no1(rs.getString("mobile_no1"));
                b.setIsVarified(rs.getString("isVarified"));
                b.setKey_person_id(rs.getInt("key_person_id"));
                b.setRevision_no(rs.getInt("revision_no"));
                b.setKey_person_name(rs.getString("key_person_name"));
                b.setOrg_office_id(rs.getInt("org_office_id"));
                b.setCity_id(rs.getInt("city_id"));
                b.setAddress_line1(rs.getString("address_line1"));
                b.setEmail_id1(rs.getString("email_id1"));
                b.setDesignation_id(rs.getInt("designation_id"));
                b.setFather_name(rs.getString("father_name"));
                b.setDate_of_birth(rs.getString("date_of_birth"));
                b.setLatitude(rs.getString("latitude"));
                b.setLongitude(rs.getString("longitude"));
                b.setFamilyofiiceid(rs.getInt("family_office"));
                b2 = b;
                // message = "Record saved successfully.";
                // msgBgColor = COLOR_OK;
                System.err.println("b2--------" + b2);
            }
        } catch (Exception e) {
            //  message = "Cannot save the record, some error.";
            // msgBgColor = COLOR_ERROR;
            System.out.println("error in Check Existing - " + e);
        }
        return b2;
    }

    public boolean UpdateRecord(String number, int id) {
        boolean b = false;
        String query = "update key_person set isVarified='Yes' where mobile_no1=" + number + " and key_person_id=? and active='Y'";
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                message = "Record Update successfully.";
                b = true;

            } else {
                message = "Cannot update the record, some error.";

            }
        } catch (Exception e) {
            message = "Cannot update the record, some error.";

            System.out.println("UpdateRecord ERROR UpdateRecord - " + e);
        }

        return b;
    }

    public int getKeyPersonId(String number) {
        int id = 0;
        PreparedStatement psmt;
        ResultSet rst;
        try {
            String query = " select key_person_id from key_person where active='y' and "
                    + " mobile_no1=" + number + " or mobile_no2=" + number + " ";
            psmt = connection.prepareStatement(query);
            rst = psmt.executeQuery();
            while (rst.next()) {
                id = rst.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("com.webservice.model.APLWebServiceModel.getKeyPersonId()- " + e);
        }
        return id;
    }

    public int saveAttendance(String type, String number, String current_time, String latitude, String longitude) {
        int count = 0;
        PreparedStatement psmt;
        ResultSet rst;
        String coming_time = null;
        String going_time = null;
        int key_person_id = getKeyPersonId(number);
        String query = "";
        int prev_id = 0;
        try {
            connection.setAutoCommit(false);
            int i = 0;
            if (type.equals("coming")) {

                String query1 = " select * from attendance a, key_person kp  where date(a.created_at)=curdate() "
                        + " and '"+number+"' in(kp.mobile_no1,kp.mobile_no2)  and a.key_person_id=kp.key_person_id and kp.active='y' "
                        + " and a.status_type_id=2 and a.going_time is null ";

                psmt = connection.prepareStatement(query1);
                rst = psmt.executeQuery();
                while (rst.next()) {
                    count = rst.getInt(1);
                }

                if (count == 0) {
                    count = 0;
                } else {
                    count = 60;
                    return count;
                }

                coming_time = current_time;
                query = "insert into attendance(key_person_id,coming_time,going_time,latitude,longitude,remark,status_type_id) "
                        + " values(?,?,?,?,?,?,?) ";
                psmt = connection.prepareStatement(query);
                psmt.setInt(++i, key_person_id);
                psmt.setString(++i, coming_time);
                psmt.setString(++i, going_time);
                psmt.setString(++i, latitude);
                psmt.setString(++i, longitude);
                psmt.setString(++i, "attendance data");
                psmt.setString(++i, "2");

                count = psmt.executeUpdate();
            } else {

                String query1 = " select * from attendance a, key_person kp  where date(a.created_at)=curdate() "
                        + " and '"+number+"' in(kp.mobile_no1,kp.mobile_no2)  and a.key_person_id=kp.key_person_id and kp.active='y' "
                        + " and a.status_type_id=2 and a.going_time is null ";

                psmt = connection.prepareStatement(query1);
                rst = psmt.executeQuery();
                while (rst.next()) {
                    count = rst.getInt(1);
                }

                if (count != 0) {
                    count = 0;
                } else {
                    count = 50;
                    return count;
                }

                query = " select a.attendance_id "
                        + " from attendance a,key_person kp where a.active='y' and kp.active='y' "
                        //+ " and kp.key_person_id=a.key_person_id and a.status='incomplete' "
                        + " and kp.key_person_id=a.key_person_id and a.status_type_id=2 "
                        + " and " + number + " in(mobile_no1,mobile_no2)";
                psmt = connection.prepareStatement(query);
                rst = psmt.executeQuery();
                while (rst.next()) {
                    prev_id = rst.getInt(1);
                }
                psmt = null;
                rst = null;
                query = "";
                query = " update attendance set going_time=?, status_type_id=?, updated_at=? where attendance_id=? ";
                psmt = connection.prepareStatement(query);
                psmt.setString(1, current_time);
                psmt.setString(2, "1");
                psmt.setTimestamp(3, new java.sql.Timestamp(new java.util.Date().getTime()));
                psmt.setInt(4, prev_id);
                count = psmt.executeUpdate();
            }

            if (count > 0) {
                count = 111;
                connection.commit();
            }

        } catch (SQLException e) {
            System.out.println("com.webservice.model.APLWebServiceModel.saveAttendance()- " + e);
        }
        return count;
    }

    public JSONArray getHolidayData(String number) {
        JSONArray rowData = new JSONArray();
        String query = null;

        query = " select holiday_id,holiday_name,date(holiday_date)as holiday_date,remark from holiday where active='y' ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("holiday_id", rset.getInt(1));
                obj.put("holiday_name", rset.getString(2));
                obj.put("holiday_date", rset.getString(3));
                obj.put("remark", rset.getString(4));
                rowData.put(obj);
            }
        } catch (Exception e) {
            System.out.println("com.webservice.model.APLWebServiceModel.getHolidayData() -" + e);
        }
        return rowData;
    }

    public JSONArray getLeaveData(String number) {
        JSONArray rowData = new JSONArray();
        String query = null;

        query = " select la.leave_application_id,la.key_person_id,la.leave_reason,la.leave_type_id, "
                + " la.status_type_id,la.leave_start_date,la.leave_end_date,la.remark "
                + " from leave_application la, key_person kp where la.active='y' and kp.active='y' "
                + " and kp.key_person_id=la.key_person_id and " + number + " in(kp.mobile_no1,kp.mobile_no2) ";

        try {
            //System.err.println("queryy  leave data ---"+query);
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("leave_application_id", rset.getInt(1));
                obj.put("key_person_id", rset.getString("key_person_id"));
                obj.put("leave_reason", rset.getString("leave_reason"));
                obj.put("leave_type_id", rset.getString("leave_type_id"));
                obj.put("status_type_id", rset.getString("status_type_id"));
                obj.put("leave_start_date", rset.getString("leave_start_date"));
                obj.put("leave_end_date", rset.getString("leave_end_date"));
                obj.put("remark", rset.getString("remark"));
                rowData.put(obj);
            }
        } catch (Exception e) {
            System.out.println("com.webservice.model.APLWebServiceModel.getLeaveData() -" + e);
        }
        return rowData;
    }

    public JSONArray getLeaveTypeData(String number) {
        JSONArray rowData = new JSONArray();
        String query = null;

        query = " select * from leave_type where active='y' ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("leave_type_id", rset.getInt("leave_type_id"));
                obj.put("leave_type", rset.getString("leave_type"));
                obj.put("remark", rset.getString("remark"));
                rowData.put(obj);
            }
        } catch (Exception e) {
            System.out.println("com.webservice.model.APLWebServiceModel.getLeaveTypeData() -" + e);
        }
        return rowData;
    }

    public JSONArray getStatusTypeData(String number) {
        JSONArray rowData = new JSONArray();
        String query = null;

        query = " select * from status_type where active='y' ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("status_type_id", rset.getInt("status_type_id"));
                obj.put("status_type", rset.getString("status_type"));
                obj.put("remark", rset.getString("remark"));
                rowData.put(obj);
            }
        } catch (Exception e) {
            System.out.println("com.webservice.model.APLWebServiceModel.getStatusTypeData()-" + e);
        }
        return rowData;
    }

    public JSONArray getAttendanceData(String number) {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = " Select a.attendance_id,a.coming_time,a.going_time,a.latitude,a.longitude,st.status_type,st.status_type_id "
                + " from attendance a, key_person kp, status_type st "
                + " where a.active='y' and kp.active='y' and st.active='y' and st.status_type_id=a.status_type_id "
                + " and kp.key_person_id=a.key_person_id and " + number + " in(kp.mobile_no1,kp.mobile_no2) "
                + " and date(a.created_at)=curdate() ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("attendance_id", rset.getInt(1));
                obj.put("coming_time", rset.getString(2));
                obj.put("going_time", rset.getString(3));
                obj.put("latitude", rset.getString(4));
                obj.put("longitude", rset.getString(5));
                obj.put("status_type_id", rset.getString(6));
                obj.put("status_type", rset.getString(7));
                rowData.put(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside getCity of survey: " + e);
        }
        return rowData;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("APLWebServiceModel closeConnection: " + e);
        }
    }

    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("OrgOfficeModel setConnection() Error: " + e);
        }
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
