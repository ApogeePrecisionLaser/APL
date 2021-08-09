/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.organization.tableClasses;

/**
 *
 * @author Soft_Tech
 */
public class OrganisationType {
    private int organisation_type_id;
    private String org_type_name;
    private String description;
    private String active;
    private int revision_no;
    private String remark;
    private int p_ot_id;
    private String p_ot;
    private String supper;
    private int generation;

    public String getP_ot() {
        return p_ot;
    }

    public void setP_ot(String p_ot) {
        this.p_ot = p_ot;
    }

    
    
    
    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }
    
    

    public String getSupper() {
        return supper;
    }

    public void setSupper(String supper) {
        this.supper = supper;
    }
    
    
    

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
    
    

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrg_type_name() {
        return org_type_name;
    }

    public void setOrg_type_name(String org_type_name) {
        this.org_type_name = org_type_name;
    }

    public int getOrganisation_type_id() {
        return organisation_type_id;
    }

    public void setOrganisation_type_id(int organisation_type_id) {
        this.organisation_type_id = organisation_type_id;
    }

    public int getP_ot_id() {
        return p_ot_id;
    }

    public void setP_ot_id(int p_ot_id) {
        this.p_ot_id = p_ot_id;
    }
    
    
    
}
