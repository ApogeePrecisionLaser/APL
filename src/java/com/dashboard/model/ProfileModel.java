package com.dashboard.model;

import com.dashboard.bean.Profile;
import static com.organization.model.KeypersonModel.getRevisionnoForImage;
import com.organization.tableClasses.KeyPerson;
import com.organization.tableClasses.Org_Office;
import com.organization.tableClasses.OrganisationDesignationBean;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.*;
import java.util.Date;
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

        int key_person_id = getKeyPersonId(logged_user_name);

        try {
            int count_map = 0;
            String query = "";
            String query_image = " select count(*) as count from general_image_details where active='Y' and key_person_id='" + key_person_id + "'  ";
            ResultSet rst1 = connection.prepareStatement(query_image).executeQuery();
            while (rst1.next()) {
                count_map = rst1.getInt("count");
            }

            if (count_map > 0) {
                query = " select oo.org_office_name,kp.key_person_name,oo.email_id1,oo.landline_no1,oo.mobile_no1 as office_mobile, "
                        + " kp.mobile_no1 as person_mobile,oo.service_tax_reg_no,oo.address_line1,oo.address_line2,oo.address_line3, "
                        + " c.city_name,c.pin_code,kp.bloodgroup,kp.gender,kp.id_no,idt.id_type,kp.date_of_birth,oo.org_office_id,kp.key_person_id, "
                        + " oodm.org_office_designation_map_id,gid.general_image_details_id,kp.latitude,kp.longitude,gid.image_destination_id,"
                        + " gid.image_name,kp.salutation "
                        + " from key_person kp,org_office oo,city c,id_type idt,org_office_designation_map oodm,general_image_details gid "
                        + " where kp.active='Y' and oo.active='Y' and c.active='Y' and oodm.active='Y' and gid.active='Y' "
                        + " and oodm.org_office_id=oo.org_office_id and oodm.designation_id=8 and gid.key_person_id=kp.key_person_id "
                        + " and c.city_id=oo.city_id and kp.org_office_id=oo.org_office_id and idt.id_type_id=kp.id_type_id ";

                if (!logged_user_name.equals("") && logged_user_name != null) {
                    query += " and kp.key_person_name='" + logged_user_name + "' ";
                }
                if (!logged_org_office.equals("") && logged_org_office != null) {
                    query += " and oo.org_office_name='" + logged_org_office + "' ";
                }
            } else {
                query = " select oo.org_office_name,kp.key_person_name,oo.email_id1,oo.landline_no1,oo.mobile_no1 as office_mobile, "
                        + " kp.mobile_no1 as person_mobile,oo.service_tax_reg_no,oo.address_line1,oo.address_line2,oo.address_line3, "
                        + " c.city_name,c.pin_code,kp.bloodgroup,kp.gender,kp.id_no,kp.date_of_birth,oo.org_office_id,kp.key_person_id, "
                        + " oodm.org_office_designation_map_id,kp.latitude,kp.longitude,kp.salutation "
                        + " from key_person kp,org_office oo,city c,org_office_designation_map oodm "
                        + " where kp.active='Y' and oo.active='Y' and c.active='Y' and oodm.active='Y' "
                        + " and oodm.org_office_id=oo.org_office_id and oodm.designation_id=8 "
                        + " and c.city_id=oo.city_id and kp.org_office_id=oo.org_office_id  ";

                if (!logged_user_name.equals("") && logged_user_name != null) {
                    query += " and kp.key_person_name='" + logged_user_name + "' ";
                }
                if (!logged_org_office.equals("") && logged_org_office != null) {
                    query += " and oo.org_office_name='" + logged_org_office + "' ";
                }
            }

            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                Profile bean = new Profile();
                bean.setOrg_office_name(rst.getString("org_office_name"));
                bean.setKey_person_name(rst.getString("key_person_name"));
                bean.setEmail_id1(rst.getString("email_id1"));
                bean.setSalutation(rst.getString("salutation"));
                bean.setLandline_no1(rst.getString("landline_no1"));
                bean.setMobile_no1(rst.getString("office_mobile"));
                bean.setMobile_no2(rst.getString("person_mobile"));
                bean.setGst_number(rst.getString("service_tax_reg_no"));
                bean.setAddress_line1(rst.getString("address_line1"));
                bean.setAddress_line2(rst.getString("address_line2"));
                bean.setAddress_line3(rst.getString("address_line3"));
                bean.setCity_name(rst.getString("city_name"));
                bean.setBlood(rst.getString("bloodgroup"));
                bean.setGender(rst.getString("gender"));
                bean.setDate_of_birth(rst.getString("date_of_birth"));
                bean.setId_no(rst.getString("id_no"));
                bean.setLatitude(rst.getString("latitude"));
                bean.setLongitude(rst.getString("longitude"));
                bean.setOrg_office_id(rst.getInt("org_office_id"));
                bean.setKey_person_id(rst.getInt("key_person_id"));
                bean.setOrg_office_des_map_id(rst.getInt("org_office_designation_map_id"));
                if (count_map > 0) {
                    bean.setGeneral_image_details_id(rst.getInt("general_image_details_id"));
                    bean.setImage_path(rst.getString("image_destination_id"));
                    bean.setImage_name(rst.getString("image_name"));
                    bean.setId_type(rst.getString("id_type"));
                } else {
                    bean.setGeneral_image_details_id(0);
                    bean.setImage_path("");
                    bean.setImage_name("");
                    bean.setId_type("");
                }

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

    public static ArrayList<Profile> getAllLatestDealers() {
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
                + " and oo.office_type_id=3  ORDER BY kp.created_at desc limit 10 ";
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
                + " d.designation,d.designation_code ,oot.office_type,oo.address_line2,kp.mobile_no2,oo.service_tax_reg_no,oo.org_office_id, "
                + " kp.bloodgroup,kp.gender,kp.id_no,idt.id_type,ct.city_name,kp.latitude,kp.longitude "
                + " from key_person kp, organisation_name onn, org_office oo, designation d, "
                + " org_office_designation_map oodm, org_office_type oot,id_type idt,city ct "
                + " where kp.active='y' and oo.active='y' and onn.active='y' and d.active='y' and oodm.active='Y' and oot.active='Y' "
                + " and oo.organisation_id=onn.organisation_id and kp.org_office_id=oo.org_office_id  and kp.id_type_id=idt.id_type_id "
                + " and oodm.designation_id=d.designation_id and oodm.org_office_id=oo.org_office_id and ct.city_id=kp.city_id "
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
                String gst = rset.getString(25);
                if (gst == null) {
                    gst = "";
                }
                bean.setGst_number(gst);
                bean.setOrg_office_id(rset.getInt(26));
                bean.setBlood(rset.getString(27));
                bean.setGender(rset.getString(28));
                bean.setId_no(rset.getString(29));
                bean.setId_type(rset.getString(30));
                bean.setCity_name(rset.getString(31));
                bean.setLatitude(rset.getString(32));
                bean.setLongitude(rset.getString(33));
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

    public static int getKeyPersonId(String person_name) {
        int key_person_id = 0;
        String query = " select key_person_id from key_person  where key_person_name='" + person_name + "' and active='Y' ";//traffic_police
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                key_person_id = rs.getInt("key_person_id");
            }
        } catch (Exception ex) {
            System.out.println("ERROR: in getKeyPersonId in ProfileModel : " + ex);
        }
        return key_person_id;
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

    public List<String> getMobilevalidty(String q) {
        List<String> list = new ArrayList<String>();
        String AdvertiseName = "";
        String query = "SELECT mobile_no1 FROM key_person where active='Y' and mobile_no1='" + q + "'";
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
            System.out.println("Error:ProfileModel getMobilevalidty-" + e);
        }
        return list;
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
            System.out.println("ProfileModel getCity_id Error: " + e);
        }
        return city_id;
    }

    public int insertRecord(Org_Office orgOffice) throws SQLException {
        String is_child = "", active = "";
        int rowsAffected = 0;
        int count = 0;
        int key = 0;

        try {
            String query = "INSERT INTO "
                    + "org_office( org_office_name,org_office_code,office_type_id, address_line1, "
                    + "address_line2, address_line3, city_id, email_id1, email_id2, mobile_no1, mobile_no2, "
                    + "landline_no1, landline_no2, landline_no3,revision_no,active,remark,organisation_id,parent_org_office_id,super,"
                    + "generation,latitude,longitude,service_tax_reg_no) "
                    + "VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?)";
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

            pstmt.setInt(19, 0);
            pstmt.setString(20, "Y");
            pstmt.setInt(21, 1);
            pstmt.setString(22, orgOffice.getLatitude());
            pstmt.setString(23, orgOffice.getLongitude());
            pstmt.setString(24, orgOffice.getGst_number());

            rowsAffected = pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs != null && rs.next()) {
                key = rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("Error: ProfileModel---insertRecord" + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            messageBGColor = COLOR_OK;

        } else {
            message = "Cannot save the record, some error.";
            messageBGColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public static int getRevisionnoForOrgOffice(Org_Office orgOffice, int org_office_id) {
        int revision = 0;
        try {

            String query = " SELECT max(revision_no) as revision_no FROM org_office WHERE org_office_id =" + org_office_id + "  && active='Y' ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
            System.err.println("OrgOfficeModel getRevisionno error------------" + e);
        }
        return revision;
    }

    public int updateRecordForOrgOffice(Org_Office orgOffice, int org_office_id) throws SQLException {
        int revision = ProfileModel.getRevisionnoForOrgOffice(orgOffice, org_office_id);
        int officetypeid = 3;
        int updateRowsAffected = 0;
//        String is_child = orgOffice.getSuperp();
//        if (is_child != null) {
//            if (is_child.equals("yes") || is_child.equals("Yes") || is_child.equals("YES") || is_child.equals("Y") || is_child.equals("y")) {
//                is_child = "Y";
//            } else {
//                is_child = "N";
//            }
//        }
        boolean status = false;
        int office_id = 0;

        int orgid = 5;
        // int orgoffice_id = getOrg_Office_id(orgOffice.getOrg_office_name());
        //  int p_prg_office_id = getOrg_Office_id(orgOffice.getP_org());

//        int generation = 0;
//        if (p_prg_office_id == 0) {
//            generation = 1;
//        } else {
//            generation = getParentGeneration(orgid, p_prg_office_id) + 1;
//        }
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
                    psmt.setInt(20, 0);
                    psmt.setString(21, orgOffice.getSuperp());
                    psmt.setInt(22, 1);
                    psmt.setString(23, orgOffice.getLatitude());
                    psmt.setString(24, orgOffice.getLongitude());
                    psmt.setString(25, orgOffice.getGst_number());

                    rowsAffected = psmt.executeUpdate();

                    if (rowsAffected > 0) {
                        status = true;
                        message = "Record updated successfully.";
                        messageBGColor = COLOR_OK;
                        connection.commit();
                    } else {
                        status = false;
                        message = "Cannot update the record, some error.";
                        messageBGColor = COLOR_ERROR;
                        connection.rollback();
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("OrgOfficeModel updateRecord() Error: " + e);
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

    public static int getRevisionnoForOrgOfficeMap(OrganisationDesignationBean orgOffice, int org_office_designation_map_id) {
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
            System.out.println("Error: OrgOfficeDesignationMapModel---getRevisionno" + e);

        }
        return revision;
    }

    public int updatemapOrganisationDesignation(OrganisationDesignationBean bean, int org_office_designation_map_id, int logged_org_office_id) throws SQLException {
        int revision = ProfileModel.getRevisionnoForOrgOfficeMap(bean, org_office_designation_map_id);
        int rowsAffected = 0;
        int count = 0;
        int updateRowsAffected = 0;
        Boolean status = false;

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

                    psmt.setInt(1, logged_org_office_id);
                    psmt.setInt(2, 8);
                    psmt.setString(3, "Y");
                    psmt.setInt(4, revision);
                    psmt.setString(5, "");
                    psmt.setString(6, "Komal");
                    psmt.setString(7, "");

                    //   System.out.println("insert query -" + psmt);
                    rowsAffected = psmt.executeUpdate();
                    if (rowsAffected > 0) {
                        status = true;
                        message = "Record updated successfully.";
                        messageBGColor = COLOR_OK;
                        connection.commit();
                        //delete record
                    } else {
                        status = false;
                        message = "Cannot update the record, some error.";
                        messageBGColor = COLOR_ERROR;
                        connection.rollback();
                    }
                }

            } else {
                message = "Key already mapped with some child!";
                messageBGColor = COLOR_ERROR;
            }

        } catch (Exception e) {
            System.out.println("Error: OrgOfficeDesignationMapModel---updateRecord" + e);
        } finally {

        }
        return rowsAffected;
    }

    public static int getRevisionnoForKeyPerson(Profile key, int key_id) {
        int revision = 0;
        try {

            String query = " SELECT max(revision_no) as revision_no FROM key_person WHERE key_person_id =" + key_id + "  && active='Y';";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
            System.out.println("KeypersonModel getRevisionno error: " + e);

        }
        return revision;
    }

    public int updateKeyPersonRecord(Profile key, Iterator itr, int key_id, String photo_destination, String iD_destination) throws SQLException {
        int revision = ProfileModel.getRevisionnoForKeyPerson(key, key_id);
        int updateRowsAffected = 0;
        int rowsAffected = 0;
        boolean status = false;
        DateFormat dateFormat1 = new SimpleDateFormat("dd.MMMMM.yyyy");
        DateFormat dateFormat = new SimpleDateFormat("dd.MMMMM.yyyy/ hh:mm:ss aaa");

        Date date = new Date();
        String current_date = dateFormat.format(date);
        int kp_id = 0;
        String query1 = "SELECT max(revision_no) revision_no FROM key_person WHERE key_person_id = " + key_id + "  && active=? ";
        String query2 = " UPDATE key_person SET active=? WHERE key_person_id=? and revision_no=? ";
        String query3 = " INSERT INTO key_person(key_person_id,salutation, key_person_name, designation_id, org_office_id, city_id, address_line1, "
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
        String query6 = " select count(*) as count from user where key_person_id='" + key_id + "' and user_name='" + key.getKey_person_name() + "' and active='Y' ";
//        String user_update = " UPDATE user SET active=? WHERE key_person_id=? and revision_no=? ";
//        String user_insert = " INSERT INTO user (user_name, user_password, registration_date, key_person_id,"
//                + "rev_no,active) "
//                + " VALUES(?,?,?,?,?,?) ";

        try {
            connection.setAutoCommit(false);

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
                    psmt.setInt(16, key.getEmp_code());
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
                    psmt.setInt(32, 0);
                    psmt.setInt(33, 0);
                    psmt.setString(34, key.getRelation());
                    psmt.setInt(35, key.getOrg_office_des_map_id());
//                    psmt.setString(36, key.getSalutation());
                    rowsAffected = psmt.executeUpdate();

                    if (rowsAffected > 0) {
                        status = true;
                        kp_id = key_id;

//                        PreparedStatement pstmt2 = connection.prepareStatement(query6);
//                        ResultSet rs6 = pstmt2.executeQuery();
//                        int user_count = 0;
//                        while (rs6.next()) {
//                            user_count = rs6.getInt("count");
//                        }
//                        if (user_count == 0) {
//
//                        }
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
                                fieldName = "design_name";
                                image_uploaded_for = "key_person_photo";

                                update_image_query = " UPDATE general_image_details SET active=? "
                                        + "WHERE key_person_id=? and revision_no=? "
                                        + " and image_destination_id=1 ";

                            } else if (i == 1 && !tempExt.equals("")) {
                                middleName = "img_Id_";
                                destination = iD_destination;
                                fieldName = "id_proof";
                                image_uploaded_for = "key_person_ID";

                                update_image_query = " UPDATE general_image_details SET active=? "
                                        + "WHERE key_person_id=? and revision_no=? "
                                        + " and image_destination_id=2 ";
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
                            String imageName = middleName + kp_id + tempExt;
                            key.setImage_name(imageName);
                            //        rowsAffected = insertImageRecord(KeyPerson key,imageName, image_uploaded_for, current_date, kp_id);
                            if (rowsAffected > 0) {
                                WirteImage(key, itr, destination, imageName, fieldName);
                            }
                            //}
                            if (revision_no == null) {
                                revision_no = "";
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
            System.out.println("Error:KeypersonModel updateRecord-" + e);
        }
        if (rowsAffected > 0) {
            status = true;
            message = "Record updated successfully.";
            messageBGColor = COLOR_OK;
            connection.commit();
        } else {
            status = false;
            message = "Cannot update the record, some error.";
            messageBGColor = COLOR_ERROR;
            connection.rollback();
        }

        return rowsAffected;
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
            System.out.println("Error:ProfileModel--getIdtype_id-- " + e);
        }
        return designation_id;
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
            System.out.println("Error:ProfileModel-- getOrgOffice_id--" + e);
        }
        return organisation_id;
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
            System.out.println("OrgOfficeDesignationMapModel Error: getOrganisationOfficeId--" + e);
        }
        return org_office_id;
    }

    public int mapOrganisationDesignation(OrganisationDesignationBean bean) {

        String is_child = "", active = "";
        int rowsAffected = 0;
        int count = 0;

        int orgid = getOrganisationOfficeId(bean.getOrganisation());
        //  int desigid = getDesignation_id(bean.getDesignation());

        String query = "insert into org_office_designation_map(org_office_id,designation_id,"
                + " active,revision,remark,created_by,serial_no,created_at) "
                + " values (?,?,?,?,?,?,?,now()) ";

        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, orgid);
            pstmt.setInt(2, 8);
            pstmt.setString(3, "Y");
            pstmt.setString(4, "0");
            pstmt.setString(5, "");
            pstmt.setString(6, "Komal");
            pstmt.setString(7, "");

            // System.out.println("insert query -" + pstmt);
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("OrgOfficeDesignationMapModel Error while inserting record in insertRecord...." + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            messageBGColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            messageBGColor = COLOR_ERROR;
        }
        return rowsAffected;

    }

    public int insertKeyPersonRecord(Profile key, Iterator itr, String photo_destination, String iD_destination) throws SQLException {
        //  System.err.println("insert -----------------------");
        int rowsAffected = 0;
        DateFormat dateFormat1 = new SimpleDateFormat("dd.MMMMM.yyyy");
        DateFormat dateFormat = new SimpleDateFormat("dd.MMMMM.yyyy/ hh:mm:ss aaa");
        java.util.Date date = new java.util.Date();
        String current_date = dateFormat.format(date);
        String image_uploaded_for = "key_person";
        String query1 = "", query2 = "", updateQuery = "";
        PreparedStatement pstmt = null;
        int kp_id = 0;
        try {
            connection.setAutoCommit(false);
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
            pstmt.setInt(15, key.getEmp_code());
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
                        WirteImage(key, itr, destination, imageName, fieldName
                        );
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
            messageBGColor = COLOR_OK;
            connection.commit();
        } else {
            message = "Cannot update the record, some error.";
            messageBGColor = COLOR_ERROR;
            connection.rollback();
        }
        return kp_id;
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
                // System.out.println(destination_path);
                rset.getInt("image_destination_id");
                // System.out.println("id = " + rset.getInt("image_destination_id"));
                image_destination_id = rset.getInt("image_destination_id");
                // System.out.println(image_destination_id);
            }

        } catch (Exception ex) {
            System.out.println("Error: KeypersonModel- getimage_destination_id-" + ex);
        }
        return image_destination_id;
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
            System.out.println("Error:KeypersonModel--getIdtypeList()-- " + e);
        }
        return list;
    }

    public static int getRevisionnoForUser(int key_id) {
        int revision = 0;
        try {

            String query = " SELECT max(rev_no) as revision_no FROM user WHERE key_person_id =" + key_id + "  && active='Y';";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
            System.out.println("KeypersonModel getRevisionnoForUser error: " + e);

        }
        return revision;
    }

    public int updatePassword(int logged_key_person_id, String newPassword, String logged_user) throws SQLException {
//        int revision = ProfileModel.getRevisionnoForUser(logged_key_person_id);
        int rowsAffected = 0;
        int count = 0;
        int updateRowsAffected = 0;
        Boolean status = false;

        String query2 = "UPDATE user SET user_password=? WHERE key_person_id=? and active='Y' ";

        try {
            connection.setAutoCommit(false);

            PreparedStatement pstm = connection.prepareStatement(query2);
            pstm.setString(1, newPassword);
            pstm.setInt(2, logged_key_person_id);
            updateRowsAffected = pstm.executeUpdate();
            if (updateRowsAffected > 0) {
                status = true;
                message = "Password Changed successfully Please Login with new password.";
                messageBGColor = COLOR_OK;
                connection.commit();
                //delete record
            } else {
                status = false;
                message = "Cannot update the record, some error.";
                messageBGColor = COLOR_ERROR;
                connection.rollback();
            }
        } catch (Exception e) {
            System.out.println("Error: ProfileModel---updatePassword" + e);
        } finally {

        }
        return updateRowsAffected;
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
