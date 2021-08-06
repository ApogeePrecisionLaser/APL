/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.organization.tableClasses;

public class OrganisationSubType {

    private int organisation_sub_type_id;
    private String organisation_sub_type_name;
    private int organisation_type_id;
    private String org_type_name;
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
    
    

    public String getOrg_type_name() {
        return org_type_name;
    }

    public void setOrg_type_name(String org_type_name) {
        this.org_type_name = org_type_name;
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

    public int getOrganisation_type_id() {
        return organisation_type_id;
    }

    public void setOrganisation_type_id(int organisation_type_id) {
        this.organisation_type_id = organisation_type_id;
    }


}
