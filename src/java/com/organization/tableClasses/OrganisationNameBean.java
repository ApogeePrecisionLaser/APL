/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.organization.tableClasses;

import java.util.List;

/**
 *
 * @author Vikrant
 */
public class OrganisationNameBean {

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

    public List<String> getOrg() {
        return org;
    }

    public void setOrg(List<String> org) {
        this.org = org;
    }
    
    

    public String getOrganisation_sub_type_name() {
        return organisation_sub_type_name;
    }

    public void setOrganisation_sub_type_name(String organisation_sub_type_name) {
        this.organisation_sub_type_name = organisation_sub_type_name;
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

    public String getOrganisation_code() {
        return organisation_code;
    }

    public void setOrganisation_code(String organisation_code) {
        this.organisation_code = organisation_code;
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
    
    

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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
    
}
