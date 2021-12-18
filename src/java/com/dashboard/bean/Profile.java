/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashboard.bean;

/**
 *
 * @author DELL
 */
public class Profile {

    private int org_office_id;
    private String org_office_name;
    private String key_person_name;
    private int office_type_id;
    private String office_type;
    private int organisation_id;
    private String organisation_name;
    private int map_id;
    private int organisation_sub_type_id;
    private String address_line1;
    private String address_line2;
    private String address_line3;
    private int city_id;
    private String state_name;
    private String email_id1;
    private String email_id2;
    private String mobile_no1;
    private String mobile_no2;
    private String landline_no1;
    private String landline_no2;
    private String landline_no3;
    private String language_type;
    private String city_name;
    private String organisation_sub_type_name;
    private String org_office_code;
    private String active;
    private int revision_no;
    private String remark;
    private String serialnumber;
    private String superp;
    private String p_org;
    private String generation;
    private String latitude;
    private String longitude;
    private String gst_number;

    private int key_person_id, emp_code, designation_code;
    private String  kp_address_line1, kp_address_line2, kp_address_line3, kp_mobile_no1, kp_email_id1, kp_father_name, kp_date_of_birth,
            emergency_contact_name, emergency_contact_mobile, off_address_line1, off_email_id1,
            off_mobile_no1, designation, organisation_code, org_office_type,image_name;

    /**
     * @return the org_office_id
     */
    public int getOrg_office_id() {
        return org_office_id;
    }

    /**
     * @param org_office_id the org_office_id to set
     */
    public void setOrg_office_id(int org_office_id) {
        this.org_office_id = org_office_id;
    }

    /**
     * @return the org_office_name
     */
    public String getOrg_office_name() {
        return org_office_name;
    }

    /**
     * @param org_office_name the org_office_name to set
     */
    public void setOrg_office_name(String org_office_name) {
        this.org_office_name = org_office_name;
    }

    /**
     * @return the office_type_id
     */
    public int getOffice_type_id() {
        return office_type_id;
    }

    /**
     * @param office_type_id the office_type_id to set
     */
    public void setOffice_type_id(int office_type_id) {
        this.office_type_id = office_type_id;
    }

    /**
     * @return the office_type
     */
    public String getOffice_type() {
        return office_type;
    }

    /**
     * @param office_type the office_type to set
     */
    public void setOffice_type(String office_type) {
        this.office_type = office_type;
    }

    /**
     * @return the organisation_id
     */
    public int getOrganisation_id() {
        return organisation_id;
    }

    /**
     * @param organisation_id the organisation_id to set
     */
    public void setOrganisation_id(int organisation_id) {
        this.organisation_id = organisation_id;
    }

    /**
     * @return the organisation_name
     */
    public String getOrganisation_name() {
        return organisation_name;
    }

    /**
     * @param organisation_name the organisation_name to set
     */
    public void setOrganisation_name(String organisation_name) {
        this.organisation_name = organisation_name;
    }

    /**
     * @return the map_id
     */
    public int getMap_id() {
        return map_id;
    }

    /**
     * @param map_id the map_id to set
     */
    public void setMap_id(int map_id) {
        this.map_id = map_id;
    }

    /**
     * @return the organisation_sub_type_id
     */
    public int getOrganisation_sub_type_id() {
        return organisation_sub_type_id;
    }

    /**
     * @param organisation_sub_type_id the organisation_sub_type_id to set
     */
    public void setOrganisation_sub_type_id(int organisation_sub_type_id) {
        this.organisation_sub_type_id = organisation_sub_type_id;
    }

    /**
     * @return the address_line1
     */
    public String getAddress_line1() {
        return address_line1;
    }

    /**
     * @param address_line1 the address_line1 to set
     */
    public void setAddress_line1(String address_line1) {
        this.address_line1 = address_line1;
    }

    /**
     * @return the address_line2
     */
    public String getAddress_line2() {
        return address_line2;
    }

    /**
     * @param address_line2 the address_line2 to set
     */
    public void setAddress_line2(String address_line2) {
        this.address_line2 = address_line2;
    }

    /**
     * @return the address_line3
     */
    public String getAddress_line3() {
        return address_line3;
    }

    /**
     * @param address_line3 the address_line3 to set
     */
    public void setAddress_line3(String address_line3) {
        this.address_line3 = address_line3;
    }

    /**
     * @return the city_id
     */
    public int getCity_id() {
        return city_id;
    }

    /**
     * @param city_id the city_id to set
     */
    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    /**
     * @return the state_name
     */
    public String getState_name() {
        return state_name;
    }

    /**
     * @param state_name the state_name to set
     */
    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    /**
     * @return the email_id1
     */
    public String getEmail_id1() {
        return email_id1;
    }

    /**
     * @param email_id1 the email_id1 to set
     */
    public void setEmail_id1(String email_id1) {
        this.email_id1 = email_id1;
    }

    /**
     * @return the email_id2
     */
    public String getEmail_id2() {
        return email_id2;
    }

    /**
     * @param email_id2 the email_id2 to set
     */
    public void setEmail_id2(String email_id2) {
        this.email_id2 = email_id2;
    }

    /**
     * @return the mobile_no1
     */
    public String getMobile_no1() {
        return mobile_no1;
    }

    /**
     * @param mobile_no1 the mobile_no1 to set
     */
    public void setMobile_no1(String mobile_no1) {
        this.mobile_no1 = mobile_no1;
    }

    /**
     * @return the mobile_no2
     */
    public String getMobile_no2() {
        return mobile_no2;
    }

    /**
     * @param mobile_no2 the mobile_no2 to set
     */
    public void setMobile_no2(String mobile_no2) {
        this.mobile_no2 = mobile_no2;
    }

    /**
     * @return the landline_no1
     */
    public String getLandline_no1() {
        return landline_no1;
    }

    /**
     * @param landline_no1 the landline_no1 to set
     */
    public void setLandline_no1(String landline_no1) {
        this.landline_no1 = landline_no1;
    }

    /**
     * @return the landline_no2
     */
    public String getLandline_no2() {
        return landline_no2;
    }

    /**
     * @param landline_no2 the landline_no2 to set
     */
    public void setLandline_no2(String landline_no2) {
        this.landline_no2 = landline_no2;
    }

    /**
     * @return the landline_no3
     */
    public String getLandline_no3() {
        return landline_no3;
    }

    /**
     * @param landline_no3 the landline_no3 to set
     */
    public void setLandline_no3(String landline_no3) {
        this.landline_no3 = landline_no3;
    }

    /**
     * @return the language_type
     */
    public String getLanguage_type() {
        return language_type;
    }

    /**
     * @param language_type the language_type to set
     */
    public void setLanguage_type(String language_type) {
        this.language_type = language_type;
    }

    /**
     * @return the city_name
     */
    public String getCity_name() {
        return city_name;
    }

    /**
     * @param city_name the city_name to set
     */
    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    /**
     * @return the organisation_sub_type_name
     */
    public String getOrganisation_sub_type_name() {
        return organisation_sub_type_name;
    }

    /**
     * @param organisation_sub_type_name the organisation_sub_type_name to set
     */
    public void setOrganisation_sub_type_name(String organisation_sub_type_name) {
        this.organisation_sub_type_name = organisation_sub_type_name;
    }

    /**
     * @return the org_office_code
     */
    public String getOrg_office_code() {
        return org_office_code;
    }

    /**
     * @param org_office_code the org_office_code to set
     */
    public void setOrg_office_code(String org_office_code) {
        this.org_office_code = org_office_code;
    }

    /**
     * @return the active
     */
    public String getActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(String active) {
        this.active = active;
    }

    /**
     * @return the revision_no
     */
    public int getRevision_no() {
        return revision_no;
    }

    /**
     * @param revision_no the revision_no to set
     */
    public void setRevision_no(int revision_no) {
        this.revision_no = revision_no;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the serialnumber
     */
    public String getSerialnumber() {
        return serialnumber;
    }

    /**
     * @param serialnumber the serialnumber to set
     */
    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }

    /**
     * @return the superp
     */
    public String getSuperp() {
        return superp;
    }

    /**
     * @param superp the superp to set
     */
    public void setSuperp(String superp) {
        this.superp = superp;
    }

    /**
     * @return the p_org
     */
    public String getP_org() {
        return p_org;
    }

    /**
     * @param p_org the p_org to set
     */
    public void setP_org(String p_org) {
        this.p_org = p_org;
    }

    /**
     * @return the generation
     */
    public String getGeneration() {
        return generation;
    }

    /**
     * @param generation the generation to set
     */
    public void setGeneration(String generation) {
        this.generation = generation;
    }

    /**
     * @return the latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the gst_number
     */
    public String getGst_number() {
        return gst_number;
    }

    /**
     * @param gst_number the gst_number to set
     */
    public void setGst_number(String gst_number) {
        this.gst_number = gst_number;
    }

    /**
     * @return the key_person_name
     */
    public String getKey_person_name() {
        return key_person_name;
    }

    /**
     * @param key_person_name the key_person_name to set
     */
    public void setKey_person_name(String key_person_name) {
        this.key_person_name = key_person_name;
    }

    /**
     * @return the key_person_id
     */
    public int getKey_person_id() {
        return key_person_id;
    }

    /**
     * @param key_person_id the key_person_id to set
     */
    public void setKey_person_id(int key_person_id) {
        this.key_person_id = key_person_id;
    }

    /**
     * @return the emp_code
     */
    public int getEmp_code() {
        return emp_code;
    }

    /**
     * @param emp_code the emp_code to set
     */
    public void setEmp_code(int emp_code) {
        this.emp_code = emp_code;
    }

    /**
     * @return the designation_code
     */
    public int getDesignation_code() {
        return designation_code;
    }

    /**
     * @param designation_code the designation_code to set
     */
    public void setDesignation_code(int designation_code) {
        this.designation_code = designation_code;
    }

    /**
     * @return the kp_address_line1
     */
    public String getKp_address_line1() {
        return kp_address_line1;
    }

    /**
     * @param kp_address_line1 the kp_address_line1 to set
     */
    public void setKp_address_line1(String kp_address_line1) {
        this.kp_address_line1 = kp_address_line1;
    }

    /**
     * @return the kp_address_line2
     */
    public String getKp_address_line2() {
        return kp_address_line2;
    }

    /**
     * @param kp_address_line2 the kp_address_line2 to set
     */
    public void setKp_address_line2(String kp_address_line2) {
        this.kp_address_line2 = kp_address_line2;
    }

    /**
     * @return the kp_address_line3
     */
    public String getKp_address_line3() {
        return kp_address_line3;
    }

    /**
     * @param kp_address_line3 the kp_address_line3 to set
     */
    public void setKp_address_line3(String kp_address_line3) {
        this.kp_address_line3 = kp_address_line3;
    }

    /**
     * @return the kp_mobile_no1
     */
    public String getKp_mobile_no1() {
        return kp_mobile_no1;
    }

    /**
     * @param kp_mobile_no1 the kp_mobile_no1 to set
     */
    public void setKp_mobile_no1(String kp_mobile_no1) {
        this.kp_mobile_no1 = kp_mobile_no1;
    }

    /**
     * @return the kp_email_id1
     */
    public String getKp_email_id1() {
        return kp_email_id1;
    }

    /**
     * @param kp_email_id1 the kp_email_id1 to set
     */
    public void setKp_email_id1(String kp_email_id1) {
        this.kp_email_id1 = kp_email_id1;
    }

    /**
     * @return the kp_father_name
     */
    public String getKp_father_name() {
        return kp_father_name;
    }

    /**
     * @param kp_father_name the kp_father_name to set
     */
    public void setKp_father_name(String kp_father_name) {
        this.kp_father_name = kp_father_name;
    }

    /**
     * @return the kp_date_of_birth
     */
    public String getKp_date_of_birth() {
        return kp_date_of_birth;
    }

    /**
     * @param kp_date_of_birth the kp_date_of_birth to set
     */
    public void setKp_date_of_birth(String kp_date_of_birth) {
        this.kp_date_of_birth = kp_date_of_birth;
    }

    /**
     * @return the emergency_contact_name
     */
    public String getEmergency_contact_name() {
        return emergency_contact_name;
    }

    /**
     * @param emergency_contact_name the emergency_contact_name to set
     */
    public void setEmergency_contact_name(String emergency_contact_name) {
        this.emergency_contact_name = emergency_contact_name;
    }

    /**
     * @return the emergency_contact_mobile
     */
    public String getEmergency_contact_mobile() {
        return emergency_contact_mobile;
    }

    /**
     * @param emergency_contact_mobile the emergency_contact_mobile to set
     */
    public void setEmergency_contact_mobile(String emergency_contact_mobile) {
        this.emergency_contact_mobile = emergency_contact_mobile;
    }

    /**
     * @return the off_address_line1
     */
    public String getOff_address_line1() {
        return off_address_line1;
    }

    /**
     * @param off_address_line1 the off_address_line1 to set
     */
    public void setOff_address_line1(String off_address_line1) {
        this.off_address_line1 = off_address_line1;
    }

    /**
     * @return the off_email_id1
     */
    public String getOff_email_id1() {
        return off_email_id1;
    }

    /**
     * @param off_email_id1 the off_email_id1 to set
     */
    public void setOff_email_id1(String off_email_id1) {
        this.off_email_id1 = off_email_id1;
    }

    /**
     * @return the off_mobile_no1
     */
    public String getOff_mobile_no1() {
        return off_mobile_no1;
    }

    /**
     * @param off_mobile_no1 the off_mobile_no1 to set
     */
    public void setOff_mobile_no1(String off_mobile_no1) {
        this.off_mobile_no1 = off_mobile_no1;
    }

    /**
     * @return the designation
     */
    public String getDesignation() {
        return designation;
    }

    /**
     * @param designation the designation to set
     */
    public void setDesignation(String designation) {
        this.designation = designation;
    }

    /**
     * @return the organisation_code
     */
    public String getOrganisation_code() {
        return organisation_code;
    }

    /**
     * @param organisation_code the organisation_code to set
     */
    public void setOrganisation_code(String organisation_code) {
        this.organisation_code = organisation_code;
    }

    /**
     * @return the org_office_type
     */
    public String getOrg_office_type() {
        return org_office_type;
    }

    /**
     * @param org_office_type the org_office_type to set
     */
    public void setOrg_office_type(String org_office_type) {
        this.org_office_type = org_office_type;
    }

    /**
     * @return the image_name
     */
    public String getImage_name() {
        return image_name;
    }

    /**
     * @param image_name the image_name to set
     */
    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }
}
