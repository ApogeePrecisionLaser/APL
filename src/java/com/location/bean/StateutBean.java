
package com.location.bean;

public class StateutBean {
    private int stateutId,country_id,revision_no;
    private String stateName,utName, stateutDescription,countryName,active;

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

    
    
    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    
    public int getStateutId() {
        return stateutId;
    }

    public void setStateutId(int stateutId) {
        this.stateutId = stateutId;
    }
   
    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
   
    public String getUtName() {
        return utName;
    }

    public void setUtName(String utName) {
        this.utName = utName;
    }

    public String getStateutDescription() {
        return stateutDescription;
    }

    public void setStateutDescription(String stateutDescription) {
        this.stateutDescription = stateutDescription;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    
}
