/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.tableClasses;

/**
 *
 * @author Administrator
 */
public class MobileDao {
    
    private int id;
	
	private String mobilenumber;
	private String name;
	private String emailid;
	private String password;
	private String gender;
	private String bloodgroup;
	private String remark;
	private String emergencyMobileNo;
	private String emergencyContactName;
        private String latitude;
        private String longitude;
        private int cityid;
        private String address;
        private int desigid;
        private int officeid;
        private String fathername;
        private String dob;
        private int familydesig;
        private int familyoffice;
        private String relation;

    public int getFamilydesig() {
        return familydesig;
    }

    public void setFamilydesig(int familydesig) {
        this.familydesig = familydesig;
    }

    public int getFamilyoffice() {
        return familyoffice;
    }

    public void setFamilyoffice(int familyoffice) {
        this.familyoffice = familyoffice;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
        
        

    public int getDesigid() {
        return desigid;
    }

    public void setDesigid(int desigid) {
        this.desigid = desigid;
    }

    public int getOfficeid() {
        return officeid;
    }

    public void setOfficeid(int officeid) {
        this.officeid = officeid;
    }

    public String getFathername() {
        return fathername;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

        
        
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getCityid() {
        return cityid;
    }

    public void setCityid(int cityid) {
        this.cityid = cityid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
        
	
        
        
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public MobileDao() {
		
	}
	public String getMobilenumber() {
		return mobilenumber;
	}
	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBloodgroup() {
		return bloodgroup;
	}
	public void setBloodgroup(String bloodgroup) {
		this.bloodgroup = bloodgroup;
	}

	public String getEmergencyMobileNo() {
		return emergencyMobileNo;
	}
	public void setEmergencyMobileNo(String emergencyMobileNo) {
		this.emergencyMobileNo = emergencyMobileNo;
	}
	public String getEmergencyContactName() {
		return emergencyContactName;
	}
	public void setEmergencyContactName(String emergencyContactName) {
		this.emergencyContactName = emergencyContactName;
	}
}
