/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.report.bean;

import java.io.InputStream;

/**
 *
 * @author komal
 */
public class DailyEnquiryReport {

    private String current_date, total_query_of_current_date, total_query_till_date, open_query_of_current_date, open_query_till_date,
            closed_query_of_current_date, closed_query_till_date, sold_query_of_current_date, sold_query_till_date;
    private InputStream images;

    /**
     * @return the current_date
     */
    public String getCurrent_date() {
        return current_date;
    }

    /**
     * @param current_date the current_date to set
     */
    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }

    /**
     * @return the total_query_of_current_date
     */
    public String getTotal_query_of_current_date() {
        return total_query_of_current_date;
    }

    /**
     * @param total_query_of_current_date the total_query_of_current_date to set
     */
    public void setTotal_query_of_current_date(String total_query_of_current_date) {
        this.total_query_of_current_date = total_query_of_current_date;
    }

    /**
     * @return the total_query_till_date
     */
    public String getTotal_query_till_date() {
        return total_query_till_date;
    }

    /**
     * @param total_query_till_date the total_query_till_date to set
     */
    public void setTotal_query_till_date(String total_query_till_date) {
        this.total_query_till_date = total_query_till_date;
    }

    /**
     * @return the open_query_of_current_date
     */
    public String getOpen_query_of_current_date() {
        return open_query_of_current_date;
    }

    /**
     * @param open_query_of_current_date the open_query_of_current_date to set
     */
    public void setOpen_query_of_current_date(String open_query_of_current_date) {
        this.open_query_of_current_date = open_query_of_current_date;
    }

    /**
     * @return the open_query_till_date
     */
    public String getOpen_query_till_date() {
        return open_query_till_date;
    }

    /**
     * @param open_query_till_date the open_query_till_date to set
     */
    public void setOpen_query_till_date(String open_query_till_date) {
        this.open_query_till_date = open_query_till_date;
    }

    /**
     * @return the closed_query_of_current_date
     */
    public String getClosed_query_of_current_date() {
        return closed_query_of_current_date;
    }

    /**
     * @param closed_query_of_current_date the closed_query_of_current_date to
     * set
     */
    public void setClosed_query_of_current_date(String closed_query_of_current_date) {
        this.closed_query_of_current_date = closed_query_of_current_date;
    }

    /**
     * @return the closed_query_till_date
     */
    public String getClosed_query_till_date() {
        return closed_query_till_date;
    }

    /**
     * @param closed_query_till_date the closed_query_till_date to set
     */
    public void setClosed_query_till_date(String closed_query_till_date) {
        this.closed_query_till_date = closed_query_till_date;
    }

    /**
     * @return the sold_query_of_current_date
     */
    public String getSold_query_of_current_date() {
        return sold_query_of_current_date;
    }

    /**
     * @param sold_query_of_current_date the sold_query_of_current_date to set
     */
    public void setSold_query_of_current_date(String sold_query_of_current_date) {
        this.sold_query_of_current_date = sold_query_of_current_date;
    }

    /**
     * @return the sold_query_till_date
     */
    public String getSold_query_till_date() {
        return sold_query_till_date;
    }

    /**
     * @param sold_query_till_date the sold_query_till_date to set
     */
    public void setSold_query_till_date(String sold_query_till_date) {
        this.sold_query_till_date = sold_query_till_date;
    }

    /**
     * @return the images
     */
    public InputStream getImages() {
        return images;
    }

    /**
     * @param images the images to set
     */
    public void setImages(InputStream images) {
        this.images = images;
    }
}
