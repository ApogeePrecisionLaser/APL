package com.dashboard.model;

import com.dashboard.bean.Profile;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.text.DateFormat;
import org.apache.commons.fileupload.FileItem;

public class ProfileModel {

    static private Connection connection;
    private String driver, url, user, password;
    private String message, messageBGColor = "#a2a220";
    private final String COLOR_OK = "green";
    private final String COLOR_ERROR = "red";
    int item_id = 0;
    int item_id1 = 0;
    int order_table_id = 0;

    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("ProfileModel setConnection() Error: " + e);
        }
    }

    public static ArrayList<Profile> getAllDetails(String logged_user_name, String logged_org_office) {
        ArrayList<Profile> list = new ArrayList<Profile>();
        try {
            String query = " select oo.org_office_name,kp.key_person_name,oo.email_id1,oo.landline_no1,oo.mobile_no1 as office_mobile, "
                    + " kp.mobile_no1 as person_mobile,oo.service_tax_reg_no,oo.address_line1,oo.address_line2,oo.address_line3, "
                    + " c.city_name,c.pin_code "
                    + " from key_person kp,org_office oo,city c where kp.active='Y' and oo.active='Y' and c.active='Y' "
                    + " and c.city_id=oo.city_id and kp.org_office_id=oo.org_office_id  ";

            if (!logged_user_name.equals("") && logged_user_name != null) {
                query += " and kp.key_person_name='" + logged_user_name + "' ";
            }
            if (!logged_org_office.equals("") && logged_org_office != null) {
                query += " and oo.org_office_name='" + logged_org_office + "' ";
            }

            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                Profile bean = new Profile();
                bean.setOrg_office_name(rst.getString("org_office_name"));
                bean.setKey_person_name(rst.getString("key_person_name"));
                bean.setEmail_id1(rst.getString("email_id1"));
                bean.setLandline_no1(rst.getString("landline_no1"));
                bean.setMobile_no1(rst.getString("office_mobile"));
                bean.setMobile_no2(rst.getString("person_mobile"));
                bean.setGst_number(rst.getString("service_tax_reg_no"));
                bean.setAddress_line1(rst.getString("address_line1"));
                bean.setAddress_line2(rst.getString("address_line2"));
                bean.setAddress_line3(rst.getString("address_line3"));
                bean.setCity_name(rst.getString("city_name"));
//                bean.setImage_name(rst.getString("image_name"));

                list.add(bean);
            }
        } catch (Exception e) {
            System.err.println("ProfileModel Exception------------" + e);
        }

        return list;
    }

    public static ArrayList<Profile> getAllDealers() {
        ArrayList<Profile> list = new ArrayList<Profile>();
        String query = " select distinct "
                + " kp.key_person_id, "
                + " kp.key_person_name,kp.address_line1,kp.address_line2,kp.address_line3,kp.mobile_no1,kp.email_id1,kp.emp_code, "
                + " kp.father_name,kp.date_of_birth,kp.emergency_contact_name,kp.emergency_contact_mobile, "
                + " onn.organisation_name,onn.organisation_code,oo.org_office_name,oo.address_line1,oo.email_id1, "
                + " oo.mobile_no1,oo.org_office_code, "
                + " d.designation,d.designation_code ,oot.office_type,oo.address_line2,kp.mobile_no2,oo.service_tax_reg_no,oo.org_office_id  "
                + " from key_person kp, organisation_name onn, org_office oo, designation d, "
                + " org_office_designation_map oodm, org_office_type oot"
                + " where kp.active='y' and oo.active='y' and onn.active='y' and d.active='y' and oodm.active='Y' and oot.active='Y' "
                + " and oo.organisation_id=onn.organisation_id and kp.org_office_id=oo.org_office_id "
                + " and oodm.designation_id=d.designation_id and oodm.org_office_id=oo.org_office_id "
                + " and kp.org_office_designation_map_id=oodm.org_office_designation_map_id and oo.office_type_id=oot.office_type_id"
                + " and oo.office_type_id=3 order by oo.org_office_name ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                Profile bean = new Profile();
                bean.setKey_person_id(rset.getInt(1));
                bean.setKey_person_name(rset.getString(2));
                bean.setKp_address_line1(rset.getString(3) + ", " + rset.getString(4) + ", " + rset.getString(5));
//                bean.setKp_address_line2(rset.getString(4));
//                bean.setKp_address_line3(rset.getString(5));
                bean.setKp_mobile_no1(rset.getString(6) + ", " + rset.getString(24));
                bean.setKp_email_id1(rset.getString(7));
                bean.setEmp_code(rset.getInt(8));
                bean.setKp_father_name(rset.getString(9));
                bean.setKp_date_of_birth(rset.getString(10));
                bean.setEmergency_contact_name(rset.getString(11));
                bean.setEmergency_contact_mobile(rset.getString(12));
                bean.setOrganisation_name(rset.getString(13));
                bean.setOrganisation_code(rset.getString(14));
                bean.setOrg_office_name(rset.getString(15));
                bean.setOff_address_line1(rset.getString(16) + ", " + rset.getString(23));
                bean.setOff_email_id1(rset.getString(17));
                bean.setOff_mobile_no1(rset.getString(18));
                bean.setOrg_office_code(rset.getString(19));
                bean.setDesignation(rset.getString(20));
                bean.setDesignation_code(rset.getInt(21));
                bean.setOrg_office_type(rset.getString(22));
                bean.setGst_number(rset.getString(25));
                bean.setOrg_office_id(rset.getInt(26));
                list.add(bean);

            }

        } catch (Exception e) {
            System.out.println("Error in ProfileModel getData -- " + e);

        }

        return list;
    }

    public JSONArray getAllDealerList() {

        JSONObject obj = new JSONObject();
        JSONArray arrayObj = new JSONArray();
        String query = " select distinct "
                + " kp.key_person_id, "
                + " kp.key_person_name,kp.address_line1,kp.address_line2,kp.address_line3,kp.mobile_no1,kp.email_id1,kp.emp_code, "
                + " kp.father_name,kp.date_of_birth,kp.emergency_contact_name,kp.emergency_contact_mobile, "
                + " onn.organisation_name,onn.organisation_code,oo.org_office_name,oo.address_line1,oo.email_id1, "
                + " oo.mobile_no1,oo.org_office_code,oo.org_office_id, "
                + " d.designation,d.designation_code ,oot.office_type,oo.address_line2,kp.mobile_no2,oo.service_tax_reg_no,oo.latitude,oo.longitude "
                + " from key_person kp, organisation_name onn, org_office oo, designation d, "
                + " org_office_designation_map oodm, org_office_type oot"
                + " where kp.active='y' and oo.active='y' and onn.active='y' and d.active='y' and oodm.active='Y' and oot.active='Y' "
                + " and oo.organisation_id=onn.organisation_id and kp.org_office_id=oo.org_office_id "
                + " and oodm.designation_id=d.designation_id and oodm.org_office_id=oo.org_office_id "
                + " and kp.org_office_designation_map_id=oodm.org_office_designation_map_id and oo.office_type_id=oot.office_type_id"
                + " and oo.office_type_id=3 ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {

                JSONObject jsonObj = new JSONObject();
                jsonObj.put("dealer_office_name", rset.getString("org_office_name"));
                jsonObj.put("latitude", rset.getString("latitude"));
                jsonObj.put("longitude", rset.getString("longitude"));
                jsonObj.put("email", rset.getString("email_id1"));
                jsonObj.put("mobile", rset.getString("mobile_no1"));
                jsonObj.put("person_name", rset.getString("key_person_name"));
                jsonObj.put("org_office_id", rset.getString("org_office_id"));
                jsonObj.put("key_person_id", rset.getString("key_person_id"));

                arrayObj.add(jsonObj);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return arrayObj;
    }

    public ArrayList<Profile> viewDealerDetails(String key_person_id, String org_office_id) {
        ArrayList<Profile> list = new ArrayList<Profile>();
        String query = " select distinct "
                + " kp.key_person_id, "
                + " kp.key_person_name,kp.address_line1,kp.address_line2,kp.address_line3,kp.mobile_no1,kp.email_id1,kp.emp_code, "
                + " kp.father_name,kp.date_of_birth,kp.emergency_contact_name,kp.emergency_contact_mobile, "
                + " onn.organisation_name,onn.organisation_code,oo.org_office_name,oo.address_line1,oo.email_id1, "
                + " oo.mobile_no1,oo.org_office_code, "
                + " d.designation,d.designation_code ,oot.office_type,oo.address_line2,kp.mobile_no2,oo.service_tax_reg_no,oo.org_office_id  "
                + " from key_person kp, organisation_name onn, org_office oo, designation d, "
                + " org_office_designation_map oodm, org_office_type oot"
                + " where kp.active='y' and oo.active='y' and onn.active='y' and d.active='y' and oodm.active='Y' and oot.active='Y' "
                + " and oo.organisation_id=onn.organisation_id and kp.org_office_id=oo.org_office_id "
                + " and oodm.designation_id=d.designation_id and oodm.org_office_id=oo.org_office_id "
                + " and kp.org_office_designation_map_id=oodm.org_office_designation_map_id and oo.office_type_id=oot.office_type_id"
                + " and oo.office_type_id=3 ";

        if (!key_person_id.equals("") && key_person_id != null) {
            query += " and kp.key_person_id='" + key_person_id + "' ";
        }
        if (!org_office_id.equals("") && org_office_id != null) {
            query += " and oo.org_office_id='" + org_office_id + "' ";
        }
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                Profile bean = new Profile();
                bean.setKey_person_id(rset.getInt(1));
                bean.setKey_person_name(rset.getString(2));
                bean.setKp_address_line1(rset.getString(3) + ", " + rset.getString(4) + ", " + rset.getString(5));
//                bean.setKp_address_line2(rset.getString(4));
//                bean.setKp_address_line3(rset.getString(5));
                bean.setKp_mobile_no1(rset.getString(6) + ", " + rset.getString(24));
                bean.setKp_email_id1(rset.getString(7));
                bean.setEmp_code(rset.getInt(8));
                bean.setKp_father_name(rset.getString(9));
                bean.setKp_date_of_birth(rset.getString(10));
                bean.setEmergency_contact_name(rset.getString(11));
                bean.setEmergency_contact_mobile(rset.getString(12));
                bean.setOrganisation_name(rset.getString(13));
                bean.setOrganisation_code(rset.getString(14));
                bean.setOrg_office_name(rset.getString(15));
                bean.setOff_address_line1(rset.getString(16) + ", " + rset.getString(23));
                bean.setOff_email_id1(rset.getString(17));
                bean.setOff_mobile_no1(rset.getString(18));
                bean.setOrg_office_code(rset.getString(19));
                bean.setDesignation(rset.getString(20));
                bean.setDesignation_code(rset.getInt(21));
                bean.setOrg_office_type(rset.getString(22));
                bean.setGst_number(rset.getString(25));
                bean.setOrg_office_id(rset.getInt(26));
                list.add(bean);

            }

        } catch (Exception e) {
            System.out.println("Error in ProfileModel getData -- " + e);

        }

        return list;
    }

    public List<String> getCities(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT city_name from city where active='Y'";
        if (!q.equals("") && q != null) {
            query += " and city_name!='" + q + "' ";
        }
        query += " group by city_name order by city_name  ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String city_name = (rset.getString("city_name"));
                if (city_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(city_name);
                    count++;
                }
            }

            if (count == 0) {
                list.add("No such city_name  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:ProfileModel--getCities()-- " + e);
        }
        return list;
    }

    public List<String> getStates(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT state_name from state where active='Y'";
        if (!q.equals("") && q != null) {
            query += " and state_name!='" + q + "' ";
        }
        query += " group by state_name order by state_name  ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String state_name = (rset.getString("state_name"));
                if (state_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(state_name);
                    count++;
                }
            }

            if (count == 0) {
                list.add("No such state_name  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:ProfileModel--getStates()-- " + e);
        }
        return list;
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
            System.out.println("ERROR: in getDestination_Path in ProfileModel : " + ex);
        }
        return destination_path;
    }

    public int getCityId(String city_name) {
        int city_id = 0;
        String query = " select city_id from city  where city_name='" + city_name + "' and active='Y' ";//traffic_police
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                city_id = rs.getInt("city_id");
            }
        } catch (Exception ex) {
            System.out.println("ERROR: in getCityId in ProfileModel : " + ex);
        }
        return city_id;
    }

    public int updateRecord(Profile key, Iterator itr, int key_id, int oo_id, String photo_destination) {
        int revision = ProfileModel.getRevisionno(key, oo_id);
        DateFormat dateFormat1 = new SimpleDateFormat("dd.MMMMM.yyyy");
        DateFormat dateFormat = new SimpleDateFormat("dd.MMMMM.yyyy/ hh:mm:ss aaa");
        java.util.Date date = new java.util.Date();
        String current_date = dateFormat.format(date);
        int updateRowsAffected = 0;
        boolean status = false;
        int kp_id = 0;
        String query1 = " SELECT max(revision_no) revision_no FROM key_person WHERE key_person_id = " + key_id + "  && active=? ";
        String query2 = " UPDATE org_office SET email_id1=?,mobile_no1=?,mobile_no2=?,service_tax_reg_no=?,address_line1=?,address_line2=?, "
                + " address_line3=?,city_id=? "
                + " WHERE org_office_id=? and revision_no=? ";
        String query4 = " select count(*) from general_image_details where key_person_id='" + key_id + "' ";

        String query5 = " INSERT INTO general_image_details (image_name, image_destination_id, date_time, description,key_person_id,"
                + "revision_no,active,remark) "
                + " VALUES(?,?,?,?,?,?,?,?)";
        // String query6=" UPDATE general_image_details SET active=? WHERE key_person_id=? ";
        int rowsAffected = 0;
        try {
            int city_id = getCityId(key.getCity_name());
            PreparedStatement pstmt = connection.prepareStatement(query1);
//           pstmt.setInt(1,organisation_type_id);
            pstmt.setString(1, "Y");

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                PreparedStatement pstm = connection.prepareStatement(query2);

                pstm.setString(1, key.getEmail_id1());
                pstm.setString(2, key.getMobile_no1());
                pstm.setString(3, key.getMobile_no2());
                pstm.setString(4, key.getGst_number());
                pstm.setString(5, key.getAddress_line1());
                pstm.setString(6, key.getAddress_line2());
                pstm.setString(7, key.getAddress_line3());
                pstm.setInt(8, city_id);
                pstm.setInt(9, oo_id);
                pstm.setInt(10, revision);

                updateRowsAffected = pstm.executeUpdate();
                if (updateRowsAffected >= 1) {

                    if (updateRowsAffected > 0 && (!key.getImage_name().isEmpty())) {
                        String[] image_name = {key.getImage_name()};
                        String revision_no = getRevisionnoForImage(key, key_id);
                        if (revision_no == null) {
                            revision_no = "0";
                        }
                        for (int i = 0; i < image_name.length; i++) {
                            String tempExt = image_name[i];
                            String image_uploaded_for = "";
                            // if (!tempExt.isEmpty()) {
                            String middleName = "";
                            String destination = "";
                            String fieldName = "";
                            String update_image_query = "";

                            if (i == 0 && tempExt.equals("")) {
                                i = 1;
                                tempExt = image_name[i];
                            }

                            //if (tempExt.equals(image_name[0])) {
                            if (i == 0 && !tempExt.equals("")) {
                                middleName = "img_Key_person_";
                                destination = photo_destination;
                                fieldName = "image";
                                image_uploaded_for = "key_person_photo";
                                update_image_query = " UPDATE general_image_details SET active=? "
                                        + " WHERE key_person_id=? and revision_no=? "
                                        + " and image_destination_id=1 ";

                            }
                            PreparedStatement pstmt1 = (PreparedStatement) connection.prepareStatement(query4);

                            ResultSet rset = pstmt1.executeQuery();
                            if (rset.next()) {
                                //   System.err.print("UPDATE--------------");

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
                            String imageName = middleName + key_id + tempExt;
                            key.setImage_name(imageName);
                            if (updateRowsAffected > 0) {
                                WirteImage(key, itr, destination, imageName, fieldName);
                            }

                            //}
                            if (revision_no == null) {
                                revision_no = "";
                            }
                            pstmt = connection.prepareStatement(query5);
                            pstmt.setString(1, key.getImage_name());
                            pstmt.setInt(2, 1);
                            pstmt.setString(3, current_date);
                            pstmt.setString(4, "this image is for site");
                            pstmt.setInt(5, key_id);
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
            System.out.println("Error:ProfileModel updateRecord-" + e);
        }
        if (rowsAffected > 0) {
            message = "Record updated successfully.";
            messageBGColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            messageBGColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int insertImageRecord(Profile key, String image_name, String image_uploaded_for, String current_date, int kp_id) {
        int rowsAffected = 0;
        String imageQuery = "INSERT INTO general_image_details (image_name, image_destination_id, date_time, description,key_person_id,revision_no,active,remark) "
                + " VALUES(?, ?, ?, ?,?,?,?,?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(imageQuery);
            pstmt.setString(1, image_name);
            pstmt.setInt(2, 1);
            pstmt.setString(3, current_date);
            pstmt.setString(4, "this image is for site");
            pstmt.setInt(5, kp_id);
            pstmt.setInt(6, key.getRevision_no());
            pstmt.setString(7, "Y");
            pstmt.setString(8, "good");
            rowsAffected = pstmt.executeUpdate();
            pstmt.close();
        } catch (Exception e) {
            System.out.println("Error:ProfileModel--insertImageRecord-- " + e);
        }
        return rowsAffected;
    }

    public void WirteImage(Profile key, Iterator itr, String destination, String imageName, String fieldName) {
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
                    System.out.println("ProfileModel WirteImage error: " + e);
                }
            }
            //}
        } catch (Exception ex) {
        }
    }

    public boolean makeDirectory(String dirPathName) {
        boolean result = false;
        // System.out.println("dirPathName---" + dirPathName);
        //dirPathName = "C:/ssadvt/sor/organisation" ;
        File directory = new File(dirPathName);
        if (!directory.exists()) {
            try {
                result = directory.mkdirs();
            } catch (Exception e) {
                System.out.println("ProfileModel makeDirectory Error - " + e);
            }
        }
        return result;
    }

    public static int getRevisionno(Profile key, int oo_id) {
        int revision = 0;
        try {

            String query = " SELECT max(revision_no) as revision_no FROM org_office WHERE org_office_id =" + oo_id + "  && active='Y';";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
            System.out.println("ProfileModel getRevisionno error: " + e);

        }
        return revision;
    }

    public static String getRevisionnoForImage(Profile key, int key_id) {
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
            System.out.println("KeypersonModel getRevisionnoForImage error: " + e);

        }
        return revision;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("ProfileModel closeConnection: " + e);
        }
    }

    public void setConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("ProfileModel setConnection error: " + e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageBGColor() {
        return messageBGColor;
    }

}
