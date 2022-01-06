package com.dashboard.bean;

import java.util.List;

public class DealersOrder {

    /**
     * @return the discount_price
     */
    public String getDiscount_price() {
        return discount_price;
    }

    /**
     * @param discount_price the discount_price to set
     */
    public void setDiscount_price(String discount_price) {
        this.discount_price = discount_price;
    }

    /**
     * @return the order_table_id
     */
    public int getOrder_table_id() {
        return order_table_id;
    }

    /**
     * @param order_table_id the order_table_id to set
     */
    public void setOrder_table_id(int order_table_id) {
        this.order_table_id = order_table_id;
    }

    /**
     * @return the order_item_id
     */
    public int getOrder_item_id() {
        return order_item_id;
    }

    /**
     * @param order_item_id the order_item_id to set
     */
    public void setOrder_item_id(int order_item_id) {
        this.order_item_id = order_item_id;
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
     * @return the dealer
     */
    public String getDealer() {
        return dealer;
    }

    /**
     * @param dealer the dealer to set
     */
    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    /**
     * @return the salesperson
     */
    public String getSalesperson() {
        return salesperson;
    }

    /**
     * @param salesperson the salesperson to set
     */
    public void setSalesperson(String salesperson) {
        this.salesperson = salesperson;
    }

    private int item_names_id;
    private String item_name;
    private String item;
    private String manufacturer_name;
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
    private String basic_price;
    private String discount_price;
    private String stock_quantity;
    private int cart_table_id;
    private int cart_status_id;
    private String cart_status;

    private int order_table_id;
    private int order_item_id;
    private String order_no;
    private int requested_by_id;
    private int requested_to_id;
    private String requested_by;
    private String requested_by_office;
    private String officeGSTNo;
    private String partyGSTNo;
    private String officeMobNo;
    private String requested_to;
    private String requested_to_mobile;
    private String dealer;
    private String salesperson;
    private String delivery_challan_no;
    private String delivery_challan_date;
    private String itemname;
    private String rate;
    private String value;
    private String total_assessable_value;
    private String total_amount;
    private String required_qty;
    private String approved_qty;
    private String delivered_qty;
    private String status;
    private String date_time;
    private String order_status;
    private String item_status;
    private String discount_percent;
    private String approved_price;
    private int order_checkout_id;
    private int payment_type_id;
    private String transaction_no;
    private String payment_type;
    private String mobile_no;
    private String billing_address;
    private String shipping_address;
    private String org_office;
    private String delivery_charge;
    private String email;

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
     * @return the basic_price
     */
    public String getBasic_price() {
        return basic_price;
    }

    /**
     * @param basic_price the basic_price to set
     */
    public void setBasic_price(String basic_price) {
        this.basic_price = basic_price;
    }

    /**
     * @return the stock_quantity
     */
    public String getStock_quantity() {
        return stock_quantity;
    }

    /**
     * @param stock_quantity the stock_quantity to set
     */
    public void setStock_quantity(String stock_quantity) {
        this.stock_quantity = stock_quantity;
    }

    /**
     * @return the cart_table_id
     */
    public int getCart_table_id() {
        return cart_table_id;
    }

    /**
     * @param cart_table_id the cart_table_id to set
     */
    public void setCart_table_id(int cart_table_id) {
        this.cart_table_id = cart_table_id;
    }

    /**
     * @return the cart_status_id
     */
    public int getCart_status_id() {
        return cart_status_id;
    }

    /**
     * @param cart_status_id the cart_status_id to set
     */
    public void setCart_status_id(int cart_status_id) {
        this.cart_status_id = cart_status_id;
    }

    /**
     * @return the cart_status
     */
    public String getCart_status() {
        return cart_status;
    }

    /**
     * @param cart_status the cart_status to set
     */
    public void setCart_status(String cart_status) {
        this.cart_status = cart_status;
    }

    /**
     * @return the delivery_challan_no
     */
    public String getDelivery_challan_no() {
        return delivery_challan_no;
    }

    /**
     * @param delivery_challan_no the delivery_challan_no to set
     */
    public void setDelivery_challan_no(String delivery_challan_no) {
        this.delivery_challan_no = delivery_challan_no;
    }

    /**
     * @return the delivery_challan_date
     */
    public String getDelivery_challan_date() {
        return delivery_challan_date;
    }

    /**
     * @param delivery_challan_date the delivery_challan_date to set
     */
    public void setDelivery_challan_date(String delivery_challan_date) {
        this.delivery_challan_date = delivery_challan_date;
    }

    /**
     * @return the itemname
     */
    public String getItemname() {
        return itemname;
    }

    /**
     * @param itemname the itemname to set
     */
    public void setItemname(String itemname) {
        this.itemname = itemname;
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
     * @return the total_amount
     */
    public String getTotal_amount() {
        return total_amount;
    }

    /**
     * @param total_amount the total_amount to set
     */
    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    /**
     * @return the required_qty
     */
    public String getRequired_qty() {
        return required_qty;
    }

    /**
     * @param required_qty the required_qty to set
     */
    public void setRequired_qty(String required_qty) {
        this.required_qty = required_qty;
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
     * @return the approved_qty
     */
    public String getApproved_qty() {
        return approved_qty;
    }

    /**
     * @param approved_qty the approved_qty to set
     */
    public void setApproved_qty(String approved_qty) {
        this.approved_qty = approved_qty;
    }

    /**
     * @return the delivered_qty
     */
    public String getDelivered_qty() {
        return delivered_qty;
    }

    /**
     * @param delivered_qty the delivered_qty to set
     */
    public void setDelivered_qty(String delivered_qty) {
        this.delivered_qty = delivered_qty;
    }

    /**
     * @return the order_status
     */
    public String getOrder_status() {
        return order_status;
    }

    /**
     * @param order_status the order_status to set
     */
    public void setOrder_status(String order_status) {
        this.order_status = order_status;
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
     * @return the discount_percent
     */
    public String getDiscount_percent() {
        return discount_percent;
    }

    /**
     * @param discount_percent the discount_percent to set
     */
    public void setDiscount_percent(String discount_percent) {
        this.discount_percent = discount_percent;
    }

    /**
     * @return the approved_price
     */
    public String getApproved_price() {
        return approved_price;
    }

    /**
     * @param approved_price the approved_price to set
     */
    public void setApproved_price(String approved_price) {
        this.approved_price = approved_price;
    }

    /**
     * @return the order_checkout_id
     */
    public int getOrder_checkout_id() {
        return order_checkout_id;
    }

    /**
     * @param order_checkout_id the order_checkout_id to set
     */
    public void setOrder_checkout_id(int order_checkout_id) {
        this.order_checkout_id = order_checkout_id;
    }

    /**
     * @return the payment_type_id
     */
    public int getPayment_type_id() {
        return payment_type_id;
    }

    /**
     * @param payment_type_id the payment_type_id to set
     */
    public void setPayment_type_id(int payment_type_id) {
        this.payment_type_id = payment_type_id;
    }

    /**
     * @return the transaction_no
     */
    public String getTransaction_no() {
        return transaction_no;
    }

    /**
     * @param transaction_no the transaction_no to set
     */
    public void setTransaction_no(String transaction_no) {
        this.transaction_no = transaction_no;
    }

    /**
     * @return the payment_type
     */
    public String getPayment_type() {
        return payment_type;
    }

    /**
     * @param payment_type the payment_type to set
     */
    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    /**
     * @return the mobile_no
     */
    public String getMobile_no() {
        return mobile_no;
    }

    /**
     * @param mobile_no the mobile_no to set
     */
    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    /**
     * @return the billing_address
     */
    public String getBilling_address() {
        return billing_address;
    }

    /**
     * @param billing_address the billing_address to set
     */
    public void setBilling_address(String billing_address) {
        this.billing_address = billing_address;
    }

    /**
     * @return the shipping_address
     */
    public String getShipping_address() {
        return shipping_address;
    }

    /**
     * @param shipping_address the shipping_address to set
     */
    public void setShipping_address(String shipping_address) {
        this.shipping_address = shipping_address;
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
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the delivery_charge
     */
    public String getDelivery_charge() {
        return delivery_charge;
    }

    /**
     * @param delivery_charge the delivery_charge to set
     */
    public void setDelivery_charge(String delivery_charge) {
        this.delivery_charge = delivery_charge;
    }

    /**
     * @return the requested_to_mobile
     */
    public String getRequested_to_mobile() {
        return requested_to_mobile;
    }

    /**
     * @param requested_to_mobile the requested_to_mobile to set
     */
    public void setRequested_to_mobile(String requested_to_mobile) {
        this.requested_to_mobile = requested_to_mobile;
    }

}
