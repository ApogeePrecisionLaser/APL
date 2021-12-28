/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.tableClasses;

/**
 *
 * @author Komal
 */
public class ItemType {

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

    private int item_type_id;
    private String item_type;
    private String description;
    private String remark;
    private String active;
    private int revision_no;
    private String superp;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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


}
