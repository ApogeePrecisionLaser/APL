/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.apogee.util;

/**
 *
 * @author Vikrant
 */
public class UrlPrivileges {

    String tableUrl;
    String privilegeType;
    String privilegeTypeId;
    String privilegeTypeValue;
    String privilege;
    String select;

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public String getPrivilegeType() {
        return privilegeType;
    }

    public void setPrivilegeType(String privilegeType) {
        this.privilegeType = privilegeType;
    }

    public String getPrivilegeTypeId() {
        return privilegeTypeId;
    }

    public void setPrivilegeTypeId(String privilegeTypeId) {
        this.privilegeTypeId = privilegeTypeId;
    }

    public String getPrivilegeTypeValue() {
        return privilegeTypeValue;
    }

    public void setPrivilegeTypeValue(String privilegeTypeValue) {
        this.privilegeTypeValue = privilegeTypeValue;
    }

    public String getTableUrl() {
        return tableUrl;
    }

    public void setTableUrl(String tableUrl) {
        this.tableUrl = tableUrl;
    }

    
    
}
