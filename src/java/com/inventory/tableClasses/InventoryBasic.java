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
public class InventoryBasic {

    private int inventory_basic_id;
    private String item_name;
    private String item_code;
    private int item_names_id;
    private String description;
    private String org_office;
    private int org_office_id;
    private String active;
    private String remark;
    private int revision_no;
    private int min_quantity;
    private int daily_req;  
    private String opening_balance;

    /**
     * @return the inventory_basic_id
     */
    public int getInventory_basic_id() {
        return inventory_basic_id;
    }

    /**
     * @param inventory_basic_id the inventory_basic_id to set
     */
    public void setInventory_basic_id(int inventory_basic_id) {
        this.inventory_basic_id = inventory_basic_id;
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
     * @return the min_quantity
     */
    public int getMin_quantity() {
        return min_quantity;
    }

    /**
     * @param min_quantity the min_quantity to set
     */
    public void setMin_quantity(int min_quantity) {
        this.min_quantity = min_quantity;
    }

    /**
     * @return the daily_req
     */
    public int getDaily_req() {
        return daily_req;
    }

    /**
     * @param daily_req the daily_req to set
     */
    public void setDaily_req(int daily_req) {
        this.daily_req = daily_req;
    }

    /**
     * @return the opening_balance
     */
    public String getOpening_balance() {
        return opening_balance;
    }

    /**
     * @param opening_balance the opening_balance to set
     */
    public void setOpening_balance(String opening_balance) {
        this.opening_balance = opening_balance;
    }

    /**
     * @return the item_code
     */
    public String getItem_code() {
        return item_code;
    }

    /**
     * @param item_code the item_code to set
     */
    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

}
