/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.purchase.module;


/**
 *
 * @author komal
 */
public class PurchaseOrdersBean {

    private int purchase_order_id, item_names_id, model_id, vendor_id, inventory_id, revision_no, stock_qty, min_qty,status_id;
    private String order_no, item_name, model, vendor, qty, rate, vendor_lead_time, payment, follow_up_frequency,
            active, order_document_path, description, remark, date_time, time_ago, mobile, model_no, price, total_qty, total_price;
    private String image_path;
    private String image_name;
    private String org_office_name;
    private String org_office_code;
    private String org_office_id;
    private String color;
    private String vendor_mobile;
    private String vendor_email;
    private String vendor_address;
    private String customer_mobile;
    private String customer_email;
    private String customer_address;
    private String customer_name;
    private String status;
    private String customer_office_name;

    /**
     * @return the purchase_order_id
     */
    public int getPurchase_order_id() {
        return purchase_order_id;
    }

    /**
     * @param purchase_order_id the purchase_order_id to set
     */
    public void setPurchase_order_id(int purchase_order_id) {
        this.purchase_order_id = purchase_order_id;
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
     * @return the vendor_id
     */
    public int getVendor_id() {
        return vendor_id;
    }

    /**
     * @param vendor_id the vendor_id to set
     */
    public void setVendor_id(int vendor_id) {
        this.vendor_id = vendor_id;
    }

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
     * @return the order_no
     */
    public String getOrder_no() {
        return order_no;
    }

    /**
     * @param order_no the order_no to set
     */
    public void setOrder_no(String order_no) {
        this.order_no = order_no;
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
     * @return the vendor
     */
    public String getVendor() {
        return vendor;
    }

    /**
     * @param vendor the vendor to set
     */
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    /**
     * @return the qty
     */
    public String getQty() {
        return qty;
    }

    /**
     * @param qty the qty to set
     */
    public void setQty(String qty) {
        this.qty = qty;
    }

    /**
     * @return the rate
     */
    public String getRate() {
        return rate;
    }

    /**
     * @param rate the rate to set
     */
    public void setRate(String rate) {
        this.rate = rate;
    }

    /**
     * @return the vendor_lead_time
     */
    public String getVendor_lead_time() {
        return vendor_lead_time;
    }

    /**
     * @param vendor_lead_time the vendor_lead_time to set
     */
    public void setVendor_lead_time(String vendor_lead_time) {
        this.vendor_lead_time = vendor_lead_time;
    }

    /**
     * @return the payment
     */
    public String getPayment() {
        return payment;
    }

    /**
     * @param payment the payment to set
     */
    public void setPayment(String payment) {
        this.payment = payment;
    }

    /**
     * @return the follow_up_frequency
     */
    public String getFollow_up_frequency() {
        return follow_up_frequency;
    }

    /**
     * @param follow_up_frequency the follow_up_frequency to set
     */
    public void setFollow_up_frequency(String follow_up_frequency) {
        this.follow_up_frequency = follow_up_frequency;
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
     * @return the order_document_path
     */
    public String getOrder_document_path() {
        return order_document_path;
    }

    /**
     * @param order_document_path the order_document_path to set
     */
    public void setOrder_document_path(String order_document_path) {
        this.order_document_path = order_document_path;
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
     * @return the time_ago
     */
    public String getTime_ago() {
        return time_ago;
    }

    /**
     * @param time_ago the time_ago to set
     */
    public void setTime_ago(String time_ago) {
        this.time_ago = time_ago;
    }

    /**
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return the model_no
     */
    public String getModel_no() {
        return model_no;
    }

    /**
     * @param model_no the model_no to set
     */
    public void setModel_no(String model_no) {
        this.model_no = model_no;
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
     * @return the stock_qty
     */
    public int getStock_qty() {
        return stock_qty;
    }

    /**
     * @param stock_qty the stock_qty to set
     */
    public void setStock_qty(int stock_qty) {
        this.stock_qty = stock_qty;
    }

    /**
     * @return the min_qty
     */
    public int getMin_qty() {
        return min_qty;
    }

    /**
     * @param min_qty the min_qty to set
     */
    public void setMin_qty(int min_qty) {
        this.min_qty = min_qty;
    }

    /**
     * @return the org_office_name
     */
    public String getOrg_office_name() {
        return org_office_name;
    }

    /**
     * @param org_office_name the org_office_name to set
     */
    public void setOrg_office_name(String org_office_name) {
        this.org_office_name = org_office_name;
    }

    /**
     * @return the price
     */
    public String getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * @return the total_qty
     */
    public String getTotal_qty() {
        return total_qty;
    }

    /**
     * @param total_qty the total_qty to set
     */
    public void setTotal_qty(String total_qty) {
        this.total_qty = total_qty;
    }

    /**
     * @return the total_price
     */
    public String getTotal_price() {
        return total_price;
    }

    /**
     * @param total_price the total_price to set
     */
    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    /**
     * @return the org_office_code
     */
    public String getOrg_office_code() {
        return org_office_code;
    }

    /**
     * @param org_office_code the org_office_code to set
     */
    public void setOrg_office_code(String org_office_code) {
        this.org_office_code = org_office_code;
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @return the org_office_id
     */
    public String getOrg_office_id() {
        return org_office_id;
    }

    /**
     * @param org_office_id the org_office_id to set
     */
    public void setOrg_office_id(String org_office_id) {
        this.org_office_id = org_office_id;
    }

    /**
     * @return the vendor_mobile
     */
    public String getVendor_mobile() {
        return vendor_mobile;
    }

    /**
     * @param vendor_mobile the vendor_mobile to set
     */
    public void setVendor_mobile(String vendor_mobile) {
        this.vendor_mobile = vendor_mobile;
    }

    /**
     * @return the vendor_email
     */
    public String getVendor_email() {
        return vendor_email;
    }

    /**
     * @param vendor_email the vendor_email to set
     */
    public void setVendor_email(String vendor_email) {
        this.vendor_email = vendor_email;
    }

    /**
     * @return the vendor_address
     */
    public String getVendor_address() {
        return vendor_address;
    }

    /**
     * @param vendor_address the vendor_address to set
     */
    public void setVendor_address(String vendor_address) {
        this.vendor_address = vendor_address;
    }

    /**
     * @return the customer_mobile
     */
    public String getCustomer_mobile() {
        return customer_mobile;
    }

    /**
     * @param customer_mobile the customer_mobile to set
     */
    public void setCustomer_mobile(String customer_mobile) {
        this.customer_mobile = customer_mobile;
    }

    /**
     * @return the customer_email
     */
    public String getCustomer_email() {
        return customer_email;
    }

    /**
     * @param customer_email the customer_email to set
     */
    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    /**
     * @return the customer_address
     */
    public String getCustomer_address() {
        return customer_address;
    }

    /**
     * @param customer_address the customer_address to set
     */
    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }

    /**
     * @return the customer_name
     */
    public String getCustomer_name() {
        return customer_name;
    }

    /**
     * @param customer_name the customer_name to set
     */
    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    /**
     * @return the customer_office_name
     */
    public String getCustomer_office_name() {
        return customer_office_name;
    }

    /**
     * @param customer_office_name the customer_office_name to set
     */
    public void setCustomer_office_name(String customer_office_name) {
        this.customer_office_name = customer_office_name;
    }

    /**
     * @return the status_id
     */
    public int getStatus_id() {
        return status_id;
    }

    /**
     * @param status_id the status_id to set
     */
    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

}
