/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.organization.tableClasses;

/**
 *
 * @author Dell
 */
public class OrgOfficeHierarchyBean {
     private int id;
     private String officename;
     private String parentofficename;
     private String serialnumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOfficename() {
        return officename;
    }

    public void setOfficename(String officename) {
        this.officename = officename;
    }

    public String getParentofficename() {
        return parentofficename;
    }

    public void setParentofficename(String parentofficename) {
        this.parentofficename = parentofficename;
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }
  
    
    
    
    
}
