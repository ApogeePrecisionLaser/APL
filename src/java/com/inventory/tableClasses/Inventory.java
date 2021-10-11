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
public class Inventory {

    private int inventory_id;
    private int inventory_basic_id;
    private String item_name;
    private String item_code;
    private int item_names_id;
    private String description;
    private String org_office;
    private int org_office_id;
    private int key_person_id;
    private String key_person;
    private String active;
    private String remark;
    private int revision_no;
    private int inward_quantity;
    private int outward_quantity;
    private int stock_quantity;
    private String date_time;
    private String reference_document_type;
    private String reference_document_id;
    private int manufacturer_id;
    private int model_id;
    private int manufacturer_item_map_id;
    private String manufacturer_name;
    private String model;

    /**
     * @return the inventory_id
     */
    public int getInventory_id() {
        return inventory_id;
    }

    /**
     * @param inventory_id the inventory_id to set
     */
    public void setInventory_id(int inventory_id) {
        this.inventory_id = inventory_id;
    }

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
     * @return the key_person_id
     */
    public int getKey_person_id() {
        return key_person_id;
    }

    /**
     * @param key_person_id the key_person_id to set
     */
    public void setKey_person_id(int key_person_id) {
        this.key_person_id = key_person_id;
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
     * @return the inward_quantity
     */
    public int getInward_quantity() {
        return inward_quantity;
    }

    /**
     * @param inward_quantity the inward_quantity to set
     */
    public void setInward_quantity(int inward_quantity) {
        this.inward_quantity = inward_quantity;
    }

    /**
     * @return the outward_quantity
     */
    public int getOutward_quantity() {
        return outward_quantity;
    }

    /**
     * @param outward_quantity the outward_quantity to set
     */
    public void setOutward_quantity(int outward_quantity) {
        this.outward_quantity = outward_quantity;
    }

    /**
     * @return the date_time
     */
    public String getDate_time() {
        return date_time;
    }

    /**
     * @param date_time the date_time to set
     */
    public void setDate_time(String date_time) {
        this.date_time = date_time;
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
     * @return the reference_document_type
     */
    public String getReference_document_type() {
        return reference_document_type;
    }

    /**
     * @param reference_document_type the reference_document_type to set
     */
    public void setReference_document_type(String reference_document_type) {
        this.reference_document_type = reference_document_type;
    }

    /**
     * @return the reference_document_id
     */
    public String getReference_document_id() {
        return reference_document_id;
    }

    /**
     * @param reference_document_id the reference_document_id to set
     */
    public void setReference_document_id(String reference_document_id) {
        this.reference_document_id = reference_document_id;
    }

    /**
     * @return the stock_quantity
     */
    public int getStock_quantity() {
        return stock_quantity;
    }

    /**
     * @param stock_quantity the stock_quantity to set
     */
    public void setStock_quantity(int stock_quantity) {
        this.stock_quantity = stock_quantity;
    }

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
     * @return the manufacturer_item_map_id
     */
    public int getManufacturer_item_map_id() {
        return manufacturer_item_map_id;
    }

    /**
     * @param manufacturer_item_map_id the manufacturer_item_map_id to set
     */
    public void setManufacturer_item_map_id(int manufacturer_item_map_id) {
        this.manufacturer_item_map_id = manufacturer_item_map_id;
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

}
