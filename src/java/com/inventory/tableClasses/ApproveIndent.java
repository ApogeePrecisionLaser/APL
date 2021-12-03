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
public class ApproveIndent {

    /**
     * @return the approved_qty
     */
    public int getApproved_qty() {
        return approved_qty;
    }

    /**
     * @param approved_qty the approved_qty to set
     */
    public void setApproved_qty(int approved_qty) {
        this.approved_qty = approved_qty;
    }

    private int indent_table_id;
    private int indent_item_id;
    private String item_name;
    private int item_names_id;
    private String model;
    private int model_id;
    private String description;
    private String indent_no;
    private int requested_by_id;
    private int requested_to_id;
    private String requested_by;
    private String paymentmode;
    private String requested_to;
    private String active;
    private String remark;
    private int revision_no;
    private String date_time;
    private String status;
    private int status_id;
    private String item_status;
    private int item_status_id;
    private String purpose;
    private int purpose_id;
    private int required_qty;
    private int approved_qty;
    private int stock_qty;
    private int delivered_qty;
    private String expected_date_time;
    private String price;

    /**
     * @return the indent_table_id
     */
    public int getIndent_table_id() {
        return indent_table_id;
    }

    /**
     * @param indent_table_id the indent_table_id to set
     */
    public void setIndent_table_id(int indent_table_id) {
        this.indent_table_id = indent_table_id;
    }

    public String getPaymentmode() {
        return paymentmode;
    }

    public void setPaymentmode(String paymentmode) {
        this.paymentmode = paymentmode;

    }

    public int getIndent_item_id() {
        return indent_item_id;
    }

    public void setIndent_item_id(int indent_item_id) {
        this.indent_item_id = indent_item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getItem_names_id() {
        return item_names_id;
    }

    public void setItem_names_id(int item_names_id) {
        this.item_names_id = item_names_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIndent_no() {
        return indent_no;
    }

    public void setIndent_no(String indent_no) {
        this.indent_no = indent_no;
    }

    public int getRequested_by_id() {
        return requested_by_id;
    }

    public void setRequested_by_id(int requested_by_id) {
        this.requested_by_id = requested_by_id;
    }

    public int getRequested_to_id() {
        return requested_to_id;
    }

    public void setRequested_to_id(int requested_to_id) {
        this.requested_to_id = requested_to_id;
    }

    public String getRequested_by() {
        return requested_by;
    }

    public void setRequested_by(String requested_by) {
        this.requested_by = requested_by;
    }

    public String getRequested_to() {
        return requested_to;
    }

    public void setRequested_to(String requested_to) {
        this.requested_to = requested_to;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getRevision_no() {
        return revision_no;
    }

    public void setRevision_no(int revision_no) {
        this.revision_no = revision_no;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public String getItem_status() {
        return item_status;
    }

    public void setItem_status(String item_status) {
        this.item_status = item_status;
    }

    public int getItem_status_id() {
        return item_status_id;
    }

    public void setItem_status_id(int item_status_id) {
        this.item_status_id = item_status_id;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public int getPurpose_id() {
        return purpose_id;
    }

    public void setPurpose_id(int purpose_id) {
        this.purpose_id = purpose_id;
    }

    public int getRequired_qty() {
        return required_qty;
    }

    public void setRequired_qty(int required_qty) {
        this.required_qty = required_qty;
    }

    public int getStock_qty() {
        return stock_qty;
    }

    public void setStock_qty(int stock_qty) {
        this.stock_qty = stock_qty;
    }

    public int getDelivered_qty() {
        return delivered_qty;
    }

    public void setDelivered_qty(int delivered_qty) {
        this.delivered_qty = delivered_qty;
    }

    public String getExpected_date_time() {
        return expected_date_time;
    }

    public void setExpected_date_time(String expected_date_time) {
        this.expected_date_time = expected_date_time;
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
}
