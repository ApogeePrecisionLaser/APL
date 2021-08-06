/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.healthDepartment.organization.tableClasses;

/**
 *
 * @author Dell
 */
public class OrganisationDesignationBean {
    private int id;
    private String designation;
    private String organisation;
    private String serialnumber;
    private String superp;
    private String p_designation;
    private String remark;
    private String isActive;
    private String revision_no;
    private String generation;
    private String organisation_type;

    public String getOrganisation_type() {
        return organisation_type;
    }

    public void setOrganisation_type(String organisation_type) {
        this.organisation_type = organisation_type;
    }

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
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

    public String getP_designation() {
        return p_designation;
    }

    public void setP_designation(String p_designation) {
        this.p_designation = p_designation;
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
     * @return the isActive
     */
    public String getIsActive() {
        return isActive;
    }

    /**
     * @param isActive the isActive to set
     */
    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    /**
     * @return the revision_no
     */
    public String getRevision_no() {
        return revision_no;
    }

    /**
     * @param revision_no the revision_no to set
     */
    public void setRevision_no(String revision_no) {
        this.revision_no = revision_no;
    }

    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }
    
    
}
