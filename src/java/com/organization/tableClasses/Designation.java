/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.organization.tableClasses;

/**
 *
 * @author komal
 */
public class Designation {

    private int designation_id;
    private String designation;
    private String description;
    private String designation_code;
    private String language_type;
    private String active;
    private int revision_no;
    private String remark;

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public int getRevision_no() {
        return revision_no;
    }

    public void setRevision_no(int revision_no) {
        this.revision_no = revision_no;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    

    public String getLanguage_type() {
        return language_type;
    }

    public void setLanguage_type(String language_type) {
        this.language_type = language_type;
    }

    public String getDesignation_code() {
        return designation_code;
    }

    public void setDesignation_code(String designation_code) {
        this.designation_code = designation_code;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getDesignation_id() {
        return designation_id;
    }

    public void setDesignation_id(int designation_id) {
        this.designation_id = designation_id;
    }
    

}
