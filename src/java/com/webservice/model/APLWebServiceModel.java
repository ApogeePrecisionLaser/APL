/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webservice.model;

import com.organization.tableClasses.KeyPerson;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.codehaus.jettison.json.JSONArray;
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

    public JSONArray getOrganisationType() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select organisation_type_id,org_type_name,description,remark,parent_org_id,super "
                + " from organisation_type ot "
                + " where ot.active='Y'";
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

    public JSONArray getOrganisationName() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select organisation_id,organisation_name,organisation_type_id,organisation_code,description,remark "
                + " from organisation_name onn "
                + " where onn.active='Y'";
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

    public JSONArray getOrgOfficeType() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select office_type_id,office_type,description,remark "
                + " from org_office_type oot "
                + " where oot.active='Y'";
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

    public JSONArray getOrgOffice() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = " select org_office_id,org_office_name,organisation_id,office_type_id,city_id,address_line1,address_line2,address_line3,email_id1, "
                + " email_id2,mobile_no1,mobile_no2,landline_no1,landline_no2,landline_no3,service_tax_reg_no,vat_reg_no,org_office_code,remark,"
                + " building_id,serial_no,super,parent_org_office_id,latitude,longitude "
                + " from org_office oo "
                + " where oo.active='Y'";
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

    public JSONArray getOrgOfficeDesignationMap() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select org_office_designation_map_id,org_office_id,designation_id,remark,serial_no "
                + " from org_office_designation_map oodt "
                + " where oodt.active='Y'";
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

    public JSONArray getDesignation() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select designation_id,designation,description, designation_code,remark "
                + " from designation d "
                + " where d.active='Y'";
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

    public JSONArray getCity() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select city_id,city_name,pin_code,std_code,city_description,remark "
                + " from city c "
                + " where c.active='Y'";
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

    public JSONArray getKeyPerson() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select key_person_id,salutation,key_person_name,org_office_id,city_id,address_line1,address_line2,address_line3,mobile_no1,"
                + " mobile_no2,landline_no1,landline_no2,email_id1,email_id2,designation_id,org_office_designation_map_id,emp_code,father_name,"
                + " date_of_birth,latitude,longitude,remark,password,isVarified,bloodgroup,emergency_contact_name,emergency_contact_mobile,gender,"
                + " id_type_id,id_no,relation,family_office,family_designation "
                + " from key_person kp "
                + " where kp.active='Y'";
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

    public JSONArray getItemType() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select item_type_id,item_type,description,remark "
                + " from item_type itemt "
                + " where itemt.active='Y'";
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

    public JSONArray getItemNames() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select item_names_id,item_name,item_type_id,item_code,quantity,description,remark "
                + " from item_names itemn "
                + " where itemn.active='Y'";
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

    public JSONArray getGeneralImageDetails() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select general_image_details_id,image_name,image_destination_id,key_person_id,description,date_time,remark "
                + " from general_image_details gid "
                + " where gid.active='Y' ";
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

    public JSONArray getImageDestination() {
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

    public JSONArray getImageUploadedFor() {
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

    public JSONArray getItemImageDetails() {
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

    public JSONArray getIdType() {
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
                System.err.println("b2--------"+b2);
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
