/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apogee.admin;
import java.util.List;

/**
 *
 * @author Vikrant
 */
public class AttendanceBean {

    private String kp_id;
    private String kp_name;
    private String latitude;
    private String longitude;
    private String coming_time;
    private String going_time;
    private String distance_between;
    private String date;
    private String contact;

    /**
     * @return the kp_id
     */
    public String getKp_id() {
        return kp_id;
    }

    /**
     * @param kp_id the kp_id to set
     */
    public void setKp_id(String kp_id) {
        this.kp_id = kp_id;
    }

    /**
     * @return the kp_name
     */
    public String getKp_name() {
        return kp_name;
    }

    /**
     * @param kp_name the kp_name to set
     */
    public void setKp_name(String kp_name) {
        this.kp_name = kp_name;
    }

    /**
     * @return the latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the coming_time
     */
    public String getComing_time() {
        return coming_time;
    }

    /**
     * @param coming_time the coming_time to set
     */
    public void setComing_time(String coming_time) {
        this.coming_time = coming_time;
    }

    /**
     * @return the going_time
     */
    public String getGoing_time() {
        return going_time;
    }

    /**
     * @param going_time the going_time to set
     */
    public void setGoing_time(String going_time) {
        this.going_time = going_time;
    }

    /**
     * @return the distance_between
     */
    public String getDistance_between() {
        return distance_between;
    }

    /**
     * @param distance_between the distance_between to set
     */
    public void setDistance_between(String distance_between) {
        this.distance_between = distance_between;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the contact
     */
    public String getContact() {
        return contact;
    }

    /**
     * @param contact the contact to set
     */
    public void setContact(String contact) {
        this.contact = contact;
    }
    
    
    
}
