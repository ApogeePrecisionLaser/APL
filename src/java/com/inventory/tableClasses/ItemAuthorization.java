/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.tableClasses;

import java.util.List;

/**
 *
 * @author Komal
 */
public class ItemAuthorization {

    private int item_authorization_id;
    private int org_office_designation_map_id;
    private String item_name;
    private int item_names_id;
    private String description;
    private String designation;
    private int designation_id;
    private String active;
    private String remark;
    private String org_office;
    private String key_person;
    private int revision_no;
    private int quantity;
    private int monthly_limit;

    /**
     * @return the item_authorization_id
     */
    public int getItem_authorization_id() {
        return item_authorization_id;
    }

    /**
     * @param item_authorization_id the item_authorization_id to set
     */
    public void setItem_authorization_id(int item_authorization_id) {
        this.item_authorization_id = item_authorization_id;
    }

    /**
     * @return the item_name
     */
    public String getItem_name() {
        return item_name;
    }

    /**
     * @param item_name the item_name to set
     */
    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    /**
     * @return the item_names_id
     */
    public int getItem_names_id() {
        return item_names_id;
    }

    /**
     * @param item_names_id the item_names_id to set
     */
    public void setItem_names_id(int item_names_id) {
        this.item_names_id = item_names_id;
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
     * @return the designation_id
     */
    public int getDesignation_id() {
        return designation_id;
    }

    /**
     * @param designation_id the designation_id to set
     */
    public void setDesignation_id(int designation_id) {
        this.designation_id = designation_id;
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
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the monthly_limit
     */
    public int getMonthly_limit() {
        return monthly_limit;
    }

    /**
     * @param monthly_limit the monthly_limit to set
     */
    public void setMonthly_limit(int monthly_limit) {
        this.monthly_limit = monthly_limit;
    }

    /**
     * @return the org_office_designation_map_id
     */
    public int getOrg_office_designation_map_id() {
        return org_office_designation_map_id;
    }

    /**
     * @param org_office_designation_map_id the org_office_designation_map_id to
     * set
     */
    public void setOrg_office_designation_map_id(int org_office_designation_map_id) {
        this.org_office_designation_map_id = org_office_designation_map_id;
    }

    /**
     * @return the org_office
     */
    public String getOrg_office() {
        return org_office;
    }

    /**
     * @param org_office the org_office to set
     */
    public void setOrg_office(String org_office) {
        this.org_office = org_office;
    }

    /**
     * @return the key_person
     */
    public String getKey_person() {
        return key_person;
    }

    /**
     * @param key_person the key_person to set
     */
    public void setKey_person(String key_person) {
        this.key_person = key_person;
    }

}
