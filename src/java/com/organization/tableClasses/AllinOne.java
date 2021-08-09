/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.organization.tableClasses;

import java.util.List;

/**
 *
 * @author DELL
 */
public class AllinOne {
    
    
    private int organisation_id;
    private String organisation_name;
    private String description;
    private String organisation_type;
    private int organisation_type_id;
    private int organisation_sub_type_id;
    private String organisation_sub_type_name;
    private String active;
    private String remark;
    private int revision_no;
    private String organisation_code;
    private List<String> org;
   
   //-------------------------------------------------------------Org office-------------------------------------------------------------------------------------------------
    private int org_office_id;
    private String org_office_name;
    private int office_type_id;
    private String office_type;
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
    // Following fields are NOT the part of the "organisation" table, it used by EL just for view (virtual table).
    private String city_name;
    private String org_office_code;
    private String serialnumber;
    private String superp;
    private String p_org;
    private String generation;
    private String latitude;
    private String longitude;
   List<Org_Office> list;
   
    //-------------------------------------------------------------key person-----------------------------------------------------------------------------------------
    
     private int key_person_id;
    private String key_person_name;
    private int designation_id;
    private String kpdesignation;
    private int kporg_office_id;
    private String kporg_office_name;
    private int kporganisation_id;
    private String kpstate_name;
    private int kpcity_id;
    private String kpaddress_line1;
    private String kpaddress_line2;
    private String kpaddress_line3;
    private String kpmobile_no1;
    private String kpmobile_no2;
    private String kplandline_no1;
    private String kplandline_no2;
    private String kpemail_id1;
    private String kpemail_id2;
    private  String kporganisation_name;
    private  String kpcity_name;
    private String salutation;
    private String role_name;
    private int role_id;
    private String kporg_office_code;
    private String kpoffice_type;
    private String emp_code;
   private  String desgin_name;
   private  String image_path;
    private String image_id;
    private String image_name;
    private String kplanguage_type;
    private int general_image_details_id;
    private String father_name;
    private String date_of_birth;
    private String id_proof;
   
    private String kplatitude;
    private String kplongitude;
    private String isVarified;
    private int id_type_d;
    private String id_type;
    private String id_no;
    private String password;
    private String blood;
    private String emergency_name;
    private String emergency_number;
    private String gender;
 
   private int familyofiiceid;
    private String familyName;
   private int familydesignationid;
    private String familyDesignation;
    private String relation;

    public int getOrganisation_id() {
        return organisation_id;
    }

    public void setOrganisation_id(int organisation_id) {
        this.organisation_id = organisation_id;
    }

    public String getOrganisation_name() {
        return organisation_name;
    }

    public void setOrganisation_name(String organisation_name) {
        this.organisation_name = organisation_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrganisation_type() {
        return organisation_type;
    }

    public void setOrganisation_type(String organisation_type) {
        this.organisation_type = organisation_type;
    }

    public int getOrganisation_type_id() {
        return organisation_type_id;
    }

    public void setOrganisation_type_id(int organisation_type_id) {
        this.organisation_type_id = organisation_type_id;
    }

    public int getOrganisation_sub_type_id() {
        return organisation_sub_type_id;
    }

    public void setOrganisation_sub_type_id(int organisation_sub_type_id) {
        this.organisation_sub_type_id = organisation_sub_type_id;
    }

    public String getOrganisation_sub_type_name() {
        return organisation_sub_type_name;
    }

    public void setOrganisation_sub_type_name(String organisation_sub_type_name) {
        this.organisation_sub_type_name = organisation_sub_type_name;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getRevision_no() {
        return revision_no;
    }

    public void setRevision_no(int revision_no) {
        this.revision_no = revision_no;
    }

    public String getOrganisation_code() {
        return organisation_code;
    }

    public void setOrganisation_code(String organisation_code) {
        this.organisation_code = organisation_code;
    }

    public List<String> getOrg() {
        return org;
    }

    public void setOrg(List<String> org) {
        this.org = org;
    }

    public int getOrg_office_id() {
        return org_office_id;
    }

    public void setOrg_office_id(int org_office_id) {
        this.org_office_id = org_office_id;
    }

    public String getOrg_office_name() {
        return org_office_name;
    }

    public void setOrg_office_name(String org_office_name) {
        this.org_office_name = org_office_name;
    }

    public int getOffice_type_id() {
        return office_type_id;
    }

    public void setOffice_type_id(int office_type_id) {
        this.office_type_id = office_type_id;
    }

    public String getOffice_type() {
        return office_type;
    }

    public void setOffice_type(String office_type) {
        this.office_type = office_type;
    }

    public String getAddress_line1() {
        return address_line1;
    }

    public void setAddress_line1(String address_line1) {
        this.address_line1 = address_line1;
    }

    public String getAddress_line2() {
        return address_line2;
    }

    public void setAddress_line2(String address_line2) {
        this.address_line2 = address_line2;
    }

    public String getAddress_line3() {
        return address_line3;
    }

    public void setAddress_line3(String address_line3) {
        this.address_line3 = address_line3;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getEmail_id1() {
        return email_id1;
    }

    public void setEmail_id1(String email_id1) {
        this.email_id1 = email_id1;
    }

    public String getEmail_id2() {
        return email_id2;
    }

    public void setEmail_id2(String email_id2) {
        this.email_id2 = email_id2;
    }

    public String getMobile_no1() {
        return mobile_no1;
    }

    public void setMobile_no1(String mobile_no1) {
        this.mobile_no1 = mobile_no1;
    }

    public String getMobile_no2() {
        return mobile_no2;
    }

    public void setMobile_no2(String mobile_no2) {
        this.mobile_no2 = mobile_no2;
    }

    public String getLandline_no1() {
        return landline_no1;
    }

    public void setLandline_no1(String landline_no1) {
        this.landline_no1 = landline_no1;
    }

    public String getLandline_no2() {
        return landline_no2;
    }

    public void setLandline_no2(String landline_no2) {
        this.landline_no2 = landline_no2;
    }

    public String getLandline_no3() {
        return landline_no3;
    }

    public void setLandline_no3(String landline_no3) {
        this.landline_no3 = landline_no3;
    }

    public String getLanguage_type() {
        return language_type;
    }

    public void setLanguage_type(String language_type) {
        this.language_type = language_type;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getOrg_office_code() {
        return org_office_code;
    }

    public void setOrg_office_code(String org_office_code) {
        this.org_office_code = org_office_code;
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }

    public String getSuperp() {
        return superp;
    }

    public void setSuperp(String superp) {
        this.superp = superp;
    }

    public String getP_org() {
        return p_org;
    }

    public void setP_org(String p_org) {
        this.p_org = p_org;
    }

    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public List<Org_Office> getList() {
        return list;
    }

    public void setList(List<Org_Office> list) {
        this.list = list;
    }

    public int getKey_person_id() {
        return key_person_id;
    }

    public void setKey_person_id(int key_person_id) {
        this.key_person_id = key_person_id;
    }

    public String getKey_person_name() {
        return key_person_name;
    }

    public void setKey_person_name(String key_person_name) {
        this.key_person_name = key_person_name;
    }

    public int getDesignation_id() {
        return designation_id;
    }

    public void setDesignation_id(int designation_id) {
        this.designation_id = designation_id;
    }

    public String getKpdesignation() {
        return kpdesignation;
    }

    public void setKpdesignation(String kpdesignation) {
        this.kpdesignation = kpdesignation;
    }

    public int getKporg_office_id() {
        return kporg_office_id;
    }

    public void setKporg_office_id(int kporg_office_id) {
        this.kporg_office_id = kporg_office_id;
    }

    public String getKporg_office_name() {
        return kporg_office_name;
    }

    public void setKporg_office_name(String kporg_office_name) {
        this.kporg_office_name = kporg_office_name;
    }

    public int getKporganisation_id() {
        return kporganisation_id;
    }

    public void setKporganisation_id(int kporganisation_id) {
        this.kporganisation_id = kporganisation_id;
    }

    public String getKpstate_name() {
        return kpstate_name;
    }

    public void setKpstate_name(String kpstate_name) {
        this.kpstate_name = kpstate_name;
    }

    public int getKpcity_id() {
        return kpcity_id;
    }

    public void setKpcity_id(int kpcity_id) {
        this.kpcity_id = kpcity_id;
    }

    public String getKpaddress_line1() {
        return kpaddress_line1;
    }

    public void setKpaddress_line1(String kpaddress_line1) {
        this.kpaddress_line1 = kpaddress_line1;
    }

    public String getKpaddress_line2() {
        return kpaddress_line2;
    }

    public void setKpaddress_line2(String kpaddress_line2) {
        this.kpaddress_line2 = kpaddress_line2;
    }

    public String getKpaddress_line3() {
        return kpaddress_line3;
    }

    public void setKpaddress_line3(String kpaddress_line3) {
        this.kpaddress_line3 = kpaddress_line3;
    }

    public String getKpmobile_no1() {
        return kpmobile_no1;
    }

    public void setKpmobile_no1(String kpmobile_no1) {
        this.kpmobile_no1 = kpmobile_no1;
    }

    public String getKpmobile_no2() {
        return kpmobile_no2;
    }

    public void setKpmobile_no2(String kpmobile_no2) {
        this.kpmobile_no2 = kpmobile_no2;
    }

    public String getKplandline_no1() {
        return kplandline_no1;
    }

    public void setKplandline_no1(String kplandline_no1) {
        this.kplandline_no1 = kplandline_no1;
    }

    public String getKplandline_no2() {
        return kplandline_no2;
    }

    public void setKplandline_no2(String kplandline_no2) {
        this.kplandline_no2 = kplandline_no2;
    }

    public String getKpemail_id1() {
        return kpemail_id1;
    }

    public void setKpemail_id1(String kpemail_id1) {
        this.kpemail_id1 = kpemail_id1;
    }

    public String getKpemail_id2() {
        return kpemail_id2;
    }

    public void setKpemail_id2(String kpemail_id2) {
        this.kpemail_id2 = kpemail_id2;
    }

    public String getKporganisation_name() {
        return kporganisation_name;
    }

    public void setKporganisation_name(String kporganisation_name) {
        this.kporganisation_name = kporganisation_name;
    }

    public String getKpcity_name() {
        return kpcity_name;
    }

    public void setKpcity_name(String kpcity_name) {
        this.kpcity_name = kpcity_name;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getKporg_office_code() {
        return kporg_office_code;
    }

    public void setKporg_office_code(String kporg_office_code) {
        this.kporg_office_code = kporg_office_code;
    }

    public String getKpoffice_type() {
        return kpoffice_type;
    }

    public void setKpoffice_type(String kpoffice_type) {
        this.kpoffice_type = kpoffice_type;
    }

    public String getEmp_code() {
        return emp_code;
    }

    public void setEmp_code(String emp_code) {
        this.emp_code = emp_code;
    }

    public String getDesgin_name() {
        return desgin_name;
    }

    public void setDesgin_name(String desgin_name) {
        this.desgin_name = desgin_name;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getKplanguage_type() {
        return kplanguage_type;
    }

    public void setKplanguage_type(String kplanguage_type) {
        this.kplanguage_type = kplanguage_type;
    }

    public int getGeneral_image_details_id() {
        return general_image_details_id;
    }

    public void setGeneral_image_details_id(int general_image_details_id) {
        this.general_image_details_id = general_image_details_id;
    }

    public String getFather_name() {
        return father_name;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getId_proof() {
        return id_proof;
    }

    public void setId_proof(String id_proof) {
        this.id_proof = id_proof;
    }

    public String getKplatitude() {
        return kplatitude;
    }

    public void setKplatitude(String kplatitude) {
        this.kplatitude = kplatitude;
    }

    public String getKplongitude() {
        return kplongitude;
    }

    public void setKplongitude(String kplongitude) {
        this.kplongitude = kplongitude;
    }

    public String getIsVarified() {
        return isVarified;
    }

    public void setIsVarified(String isVarified) {
        this.isVarified = isVarified;
    }

    public int getId_type_d() {
        return id_type_d;
    }

    public void setId_type_d(int id_type_d) {
        this.id_type_d = id_type_d;
    }

    public String getId_type() {
        return id_type;
    }

    public void setId_type(String id_type) {
        this.id_type = id_type;
    }

    public String getId_no() {
        return id_no;
    }

    public void setId_no(String id_no) {
        this.id_no = id_no;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getEmergency_name() {
        return emergency_name;
    }

    public void setEmergency_name(String emergency_name) {
        this.emergency_name = emergency_name;
    }

    public String getEmergency_number() {
        return emergency_number;
    }

    public void setEmergency_number(String emergency_number) {
        this.emergency_number = emergency_number;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getFamilyofiiceid() {
        return familyofiiceid;
    }

    public void setFamilyofiiceid(int familyofiiceid) {
        this.familyofiiceid = familyofiiceid;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public int getFamilydesignationid() {
        return familydesignationid;
    }

    public void setFamilydesignationid(int familydesignationid) {
        this.familydesignationid = familydesignationid;
    }

    public String getFamilyDesignation() {
        return familyDesignation;
    }

    public void setFamilyDesignation(String familyDesignation) {
        this.familyDesignation = familyDesignation;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
    
    
    
    
    
    
    
}
