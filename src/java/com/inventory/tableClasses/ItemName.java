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
public class ItemName {

    private int item_names_id;
    private String item_name;
    private String item;
    private String model;
    private int model_id;
    private String description;
    private String item_type;
    private int item_type_id;
    private String active;
    private String remark;
    private int revision_no;
    private String item_code;
    private int quantity;
    private List<String> org;
    private int item_image_details_id;
    private String image_path;
    private String image_name;
    private String destination_path;
    private String parent_item;
    private String parent_item_code;
    private String parent_item_id;
    private int generation;
    private String superp;
    private String prefix;
    private String checked_req_qty;
    private String checked_purpose;
    private String checked_item_name;
    private String checked_model;
    private String checked_expected_date_time;
    private String checkedValue;
    private String HSNCode;
    
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
     * @return the item_type
     */
    public String getItem_type() {
        return item_type;
    }

    /**
     * @param item_type the item_type to set
     */
    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    /**
     * @return the item_type_id
     */
    public int getItem_type_id() {
        return item_type_id;
    }

    /**
     * @param item_type_id the item_type_id to set
     */
    public void setItem_type_id(int item_type_id) {
        this.item_type_id = item_type_id;
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
     * @return the org
     */
    public List<String> getOrg() {
        return org;
    }

    /**
     * @param org the org to set
     */
    public void setOrg(List<String> org) {
        this.org = org;
    }

    /**
     * @return the item_image_details_id
     */
    public int getItem_image_details_id() {
        return item_image_details_id;
    }

    /**
     * @param item_image_details_id the item_image_details_id to set
     */
    public void setItem_image_details_id(int item_image_details_id) {
        this.item_image_details_id = item_image_details_id;
    }

    /**
     * @return the image_path
     */
    public String getImage_path() {
        return image_path;
    }

    /**
     * @param image_path the image_path to set
     */
    public void setImage_path(String image_path) {
        this.image_path = image_path;
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

    /**
     * @return the destination_path
     */
    public String getDestination_path() {
        return destination_path;
    }

    /**
     * @param destination_path the destination_path to set
     */
    public void setDestination_path(String destination_path) {
        this.destination_path = destination_path;
    }

    /**
     * @return the parent_item
     */
    public String getParent_item() {
        return parent_item;
    }

    /**
     * @param parent_item the parent_item to set
     */
    public void setParent_item(String parent_item) {
        this.parent_item = parent_item;
    }

    /**
     * @return the generation
     */
    public int getGeneration() {
        return generation;
    }

    /**
     * @param generation the generation to set
     */
    public void setGeneration(int generation) {
        this.generation = generation;
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
     * @return the parent_item_id
     */
    public String getParent_item_id() {
        return parent_item_id;
    }

    /**
     * @param parent_item_id the parent_item_id to set
     */
    public void setParent_item_id(String parent_item_id) {
        this.parent_item_id = parent_item_id;
    }

    /**
     * @return the prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * @param prefix the prefix to set
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * @return the parent_item_code
     */
    public String getParent_item_code() {
        return parent_item_code;
    }

    /**
     * @param parent_item_code the parent_item_code to set
     */
    public void setParent_item_code(String parent_item_code) {
        this.parent_item_code = parent_item_code;
    }

    /**
     * @return the checked_req_qty
     */
    public String getChecked_req_qty() {
        return checked_req_qty;
    }

    /**
     * @param checked_req_qty the checked_req_qty to set
     */
    public void setChecked_req_qty(String checked_req_qty) {
        this.checked_req_qty = checked_req_qty;
    }

    /**
     * @return the checked_purpose
     */
    public String getChecked_purpose() {
        return checked_purpose;
    }

    /**
     * @param checked_purpose the checked_purpose to set
     */
    public void setChecked_purpose(String checked_purpose) {
        this.checked_purpose = checked_purpose;
    }

    /**
     * @return the checked_item_name
     */
    public String getChecked_item_name() {
        return checked_item_name;
    }

    /**
     * @param checked_item_name the checked_item_name to set
     */
    public void setChecked_item_name(String checked_item_name) {
        this.checked_item_name = checked_item_name;
    }

    /**
     * @return the checked_expected_date_time
     */
    public String getChecked_expected_date_time() {
        return checked_expected_date_time;
    }

    /**
     * @param checked_expected_date_time the checked_expected_date_time to set
     */
    public void setChecked_expected_date_time(String checked_expected_date_time) {
        this.checked_expected_date_time = checked_expected_date_time;
    }

    /**
     * @return the checkedValue
     */
    public String getCheckedValue() {
        return checkedValue;
    }

    /**
     * @param checkedValue the checkedValue to set
     */
    public void setCheckedValue(String checkedValue) {
        this.checkedValue = checkedValue;
    }

    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * @return the item
     */
    public String getItem() {
        return item;
    }

    /**
     * @param item the item to set
     */
    public void setItem(String item) {
        this.item = item;
    }

    /**
     * @return the checked_model
     */
    public String getChecked_model() {
        return checked_model;
    }

    /**
     * @param checked_model the checked_model to set
     */
    public void setChecked_model(String checked_model) {
        this.checked_model = checked_model;
    }

    /**
     * @return the model_id
     */
    public int getModel_id() {
        return model_id;
    }

    /**
     * @param model_id the model_id to set
     */
    public void setModel_id(int model_id) {
        this.model_id = model_id;
    }

    /**
     * @return the HSNCode
     */
    public String getHSNCode() {
        return HSNCode;
    }

    /**
     * @param HSNCode the HSNCode to set
     */
    public void setHSNCode(String HSNCode) {
        this.HSNCode = HSNCode;
    }

}
