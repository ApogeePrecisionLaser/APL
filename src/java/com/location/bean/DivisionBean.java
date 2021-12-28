
package com.location.bean;

public class DivisionBean {
    private int divisionId,stateId,revision_no;
    private String divisionName,divisionDescription,stateName,utName,zoneName,active;

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

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

  
    public int getDivisionId() {
        return divisionId;
    }

   
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

   public String getDivisionName() {
        return divisionName;
    }

    
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    
    public String getDivisionDescription() {
        return divisionDescription;
    }

   
    public void setDivisionDescription(String divisionDescription) {
        this.divisionDescription = divisionDescription;
    }

   
    public String getStateName() {
        return stateName;
    }

   
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

   
    public String getZoneName() {
        return zoneName;
    }

   
    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    /**
     * @return the utName
     */
    public String getUtName() {
        return utName;
    }

    /**
     * @param utName the utName to set
     */
    public void setUtName(String utName) {
        this.utName = utName;
    }

    

}
