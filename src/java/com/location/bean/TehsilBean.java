/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.location.bean;

/**
 *
 * @author DELL
 */
public class TehsilBean {
    private int tehsilId,revision_no,districtId;
    private String tehsilName,tehsilDescription,districtName,active;

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public int getRevision_no() {
        return revision_no;
    }

    public void setRevision_no(int revision_no) {
        this.revision_no = revision_no;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    
    
    public int getTehsilId() {
        return tehsilId;
    }

    public void setTehsilId(int tehsilId) {
        this.tehsilId = tehsilId;
    }

    public String getTehsilName() {
        return tehsilName;
    }

    public void setTehsilName(String tehsilName) {
        this.tehsilName = tehsilName;
    }

    public String getTehsilDescription() {
        return tehsilDescription;
    }

    public void setTehsilDescription(String tehsilDescription) {
        this.tehsilDescription = tehsilDescription;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }
    
    
}
