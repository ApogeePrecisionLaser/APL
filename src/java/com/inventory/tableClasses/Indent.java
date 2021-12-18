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
public class Indent {

    /**
     * @return the office_address
     */
    public String getOffice_address() {
        return office_address;
    }

    /**
     * @param office_address the office_address to set
     */
    public void setOffice_address(String office_address) {
        this.office_address = office_address;
    }

    private int indent_table_id;
    private int indent_item_id;
    private String item_name;
    private int item_names_id;
    private String description;
    private String indent_no;
    private int requested_by_id;
    private int requested_to_id;
    private String requested_by;
    private String requested_by_office;
    private String officeGSTNo;
    private String partyGSTNo;
    private String officeMobNo;
    private String requested_to;
    private String active;
    private String dealer;
    private String salesperson;
    private String delivery_challan_no;
    private String delivery_challan_date;
    private String orderno;
    private String itemname;
    private String HSNCode;
    private String rate;
    private String value;
    private String total_assessable_value;
    private String amount_in_words;
    private String model;
    private int model_id;

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getDelivery_challan_no() {
        return delivery_challan_no;
    }

    public void setDelivery_challan_no(String delivery_challan_no) {
        this.delivery_challan_no = delivery_challan_no;
    }

    public String getDelivery_challan_date() {
        return delivery_challan_date;
    }

    public void setDelivery_challan_date(String delivery_challan_date) {
        this.delivery_challan_date = delivery_challan_date;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    private String remark;
    private int revision_no;
    private String date_time;
    private String status;
    private int status_id;
    private String item_status;
    private String indent_status;
    private int item_status_id;
    private String purpose;
    private String office_address;
    private String key_person_address;
    private int purpose_id;
    private int required_qty;
    private int stock_qty;
    private int approved_qty;
    private int delivered_qty;
    private int balance_qty;

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public String getSalesperson() {
        return salesperson;
    }

    public void setSalesperson(String salesperson) {
        this.salesperson = salesperson;
    }

    public int getBalance_qty() {
        return balance_qty;
    }

    public void setBalance_qty(int balance_qty) {
        this.balance_qty = balance_qty;
    }
    private String expected_date_time;

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

    /**
     * @return the indent_item_id
     */
    public int getIndent_item_id() {
        return indent_item_id;
    }

    /**
     * @param indent_item_id the indent_item_id to set
     */
    public void setIndent_item_id(int indent_item_id) {
        this.indent_item_id = indent_item_id;
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
     * @return the indent_no
     */
    public String getIndent_no() {
        return indent_no;
    }

    /**
     * @param indent_no the indent_no to set
     */
    public void setIndent_no(String indent_no) {
        this.indent_no = indent_no;
    }

    /**
     * @return the requested_by_id
     */
    public int getRequested_by_id() {
        return requested_by_id;
    }

    /**
     * @param requested_by_id the requested_by_id to set
     */
    public void setRequested_by_id(int requested_by_id) {
        this.requested_by_id = requested_by_id;
    }

    /**
     * @return the requested_to_id
     */
    public int getRequested_to_id() {
        return requested_to_id;
    }

    /**
     * @param requested_to_id the requested_to_id to set
     */
    public void setRequested_to_id(int requested_to_id) {
        this.requested_to_id = requested_to_id;
    }

    /**
     * @return the requested_by
     */
    public String getRequested_by() {
        return requested_by;
    }

    /**
     * @param requested_by the requested_by to set
     */
    public void setRequested_by(String requested_by) {
        this.requested_by = requested_by;
    }

    /**
     * @return the requested_to
     */
    public String getRequested_to() {
        return requested_to;
    }

    /**
     * @param requested_to the requested_to to set
     */
    public void setRequested_to(String requested_to) {
        this.requested_to = requested_to;
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
     * @return the item_status
     */
    public String getItem_status() {
        return item_status;
    }

    /**
     * @param item_status the item_status to set
     */
    public void setItem_status(String item_status) {
        this.item_status = item_status;
    }

    /**
     * @return the item_status_id
     */
    public int getItem_status_id() {
        return item_status_id;
    }

    /**
     * @param item_status_id the item_status_id to set
     */
    public void setItem_status_id(int item_status_id) {
        this.item_status_id = item_status_id;
    }

    /**
     * @return the purpose
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * @param purpose the purpose to set
     */
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    /**
     * @return the purpose_id
     */
    public int getPurpose_id() {
        return purpose_id;
    }

    /**
     * @param purpose_id the purpose_id to set
     */
    public void setPurpose_id(int purpose_id) {
        this.purpose_id = purpose_id;
    }

    /**
     * @return the required_qty
     */
    public int getRequired_qty() {
        return required_qty;
    }

    /**
     * @param required_qty the required_qty to set
     */
    public void setRequired_qty(int required_qty) {
        this.required_qty = required_qty;
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
     * @return the expected_date_time
     */
    public String getExpected_date_time() {
        return expected_date_time;
    }

    /**
     * @param expected_date_time the expected_date_time to set
     */
    public void setExpected_date_time(String expected_date_time) {
        this.expected_date_time = expected_date_time;
    }

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

    /**
     * @return the delivered_qty
     */
    public int getDelivered_qty() {
        return delivered_qty;
    }

    /**
     * @param delivered_qty the delivered_qty to set
     */
    public void setDelivered_qty(int delivered_qty) {
        this.delivered_qty = delivered_qty;
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
     * @return the indent_status
     */
    public String getIndent_status() {
        return indent_status;
    }

    /**
     * @param indent_status the indent_status to set
     */
    public void setIndent_status(String indent_status) {
        this.indent_status = indent_status;
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

    /**
     * @return the key_person_address
     */
    public String getKey_person_address() {
        return key_person_address;
    }

    /**
     * @param key_person_address the key_person_address to set
     */
    public void setKey_person_address(String key_person_address) {
        this.key_person_address = key_person_address;
    }

    /**
     * @return the requested_by_office
     */
    public String getRequested_by_office() {
        return requested_by_office;
    }

    /**
     * @param requested_by_office the requested_by_office to set
     */
    public void setRequested_by_office(String requested_by_office) {
        this.requested_by_office = requested_by_office;
    }

    /**
     * @return the officeGSTNo
     */
    public String getOfficeGSTNo() {
        return officeGSTNo;
    }

    /**
     * @param officeGSTNo the officeGSTNo to set
     */
    public void setOfficeGSTNo(String officeGSTNo) {
        this.officeGSTNo = officeGSTNo;
    }

    /**
     * @return the partyGSTNo
     */
    public String getPartyGSTNo() {
        return partyGSTNo;
    }

    /**
     * @param partyGSTNo the partyGSTNo to set
     */
    public void setPartyGSTNo(String partyGSTNo) {
        this.partyGSTNo = partyGSTNo;
    }

    /**
     * @return the officeMobNo
     */
    public String getOfficeMobNo() {
        return officeMobNo;
    }

    /**
     * @param officeMobNo the officeMobNo to set
     */
    public void setOfficeMobNo(String officeMobNo) {
        this.officeMobNo = officeMobNo;
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
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the total_assessable_value
     */
    public String getTotal_assessable_value() {
        return total_assessable_value;
    }

    /**
     * @param total_assessable_value the total_assessable_value to set
     */
    public void setTotal_assessable_value(String total_assessable_value) {
        this.total_assessable_value = total_assessable_value;
    }

    /**
     * @return the amount_in_words
     */
    public String getAmount_in_words() {
        return amount_in_words;
    }

    /**
     * @param amount_in_words the amount_in_words to set
     */
    public void setAmount_in_words(String amount_in_words) {
        this.amount_in_words = amount_in_words;
    }

   

}
