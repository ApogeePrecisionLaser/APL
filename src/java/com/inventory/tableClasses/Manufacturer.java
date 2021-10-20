/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.tableClasses;

/**
 *
 * @author Komal
 */
public class Manufacturer {

    private int manufacturer_id;
    private String manufacturer_name;
    private String description;
    private String remark;
    private String active;
    private int revision_no;

    /**
     * @return the manufacturer_id
     */
    public int getManufacturer_id() {
        return manufacturer_id;
    }

    /**
     * @param manufacturer_id the manufacturer_id to set
     */
    public void setManufacturer_id(int manufacturer_id) {
        this.manufacturer_id = manufacturer_id;
    }

    /**
     * @return the manufacturer_name
     */
    public String getManufacturer_name() {
        return manufacturer_name;
    }

    /**
     * @param manufacturer_name the manufacturer_name to set
     */
    public void setManufacturer_name(String manufacturer_name) {
        this.manufacturer_name = manufacturer_name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
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



}
