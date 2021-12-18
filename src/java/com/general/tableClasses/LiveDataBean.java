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
public class LiveDataBean {
    
    String latitude;
    String longitude;
    String distance;
    String vehicle_no;
    String toDate;
    String fromDate;
    String initial_fuel_level;
    String final_fuel_level;
    int difference;
    String Exception;
    int exception_id;
    String created_at;
    String status;
    String speed;
    String door_status;
    String overspeed;
    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    
    
    

    public int getException_id() {
        return exception_id;
    }

    public void setException_id(int exception_id) {
        this.exception_id = exception_id;
    }
    
    
    

    public String getInitial_fuel_level() {
        return initial_fuel_level;
    }

    public void setInitial_fuel_level(String initial_fuel_level) {
        this.initial_fuel_level = initial_fuel_level;
    }

    public String getFinal_fuel_level() {
        return final_fuel_level;
    }

    public void setFinal_fuel_level(String final_fuel_level) {
        this.final_fuel_level = final_fuel_level;
    }

    public int getDifference() {
        return difference;
    }

    public void setDifference(int difference) {
        this.difference = difference;
    }

    public String getException() {
        return Exception;
    }

    public void setException(String Exception) {
        this.Exception = Exception;
    }
   
    
  

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public void setVehicle_no(String vehicle_no) {
        this.vehicle_no = vehicle_no;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getDoor_status() {
        return door_status;
    }

    public void setDoor_status(String door_status) {
        this.door_status = door_status;
    }

    public String getOverspeed() {
        return overspeed;
    }

    public void setOverspeed(String overspeed) {
        this.overspeed = overspeed;
    }
    
    
    
}
