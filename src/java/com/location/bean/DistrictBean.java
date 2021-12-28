
package com.location.bean;

public class DistrictBean {
    private int districtId,divisionId,revision_no,state_id;
   private String districtName,districtDescription,divisionName,stateName,utName,remark,active;
   
   
   public int getState_id() {
        return state_id;
    }

    public void setState_id(int state_id) {
        this.state_id = state_id;
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

    /**
     * @return the districtId
     */
    public void setActive(String active) {
        this.active = active;
    }

    public int getDistrictId() {
        return districtId;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

  
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @param districtId the districtId to set
     */
    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    /**
     * @return the districtName
     */
    public String getDistrictName() {
        return districtName;
    }

    /**
     * @param districtName the districtName to set
     */
    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    /**
     * @return the districtDescription
     */
    public String getDistrictDescription() {
        return districtDescription;
    }

    /**
     * @param districtDescription the districtDescription to set
     */
    public void setDistrictDescription(String districtDescription) {
        this.districtDescription = districtDescription;
    }

    /**
     * @return the divisionName
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * @param divisionName the divisionName to set
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * @return the stateName
     */
    public String getStateName() {
        return stateName;
    }

    /**
     * @param stateName the stateName to set
     */
    public void setStateName(String stateName) {
        this.stateName = stateName;
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
