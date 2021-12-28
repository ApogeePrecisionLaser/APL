/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webservice.controller;

import com.organization.tableClasses.KeyPerson;
import com.webservice.model.APLWebServiceModel;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONException;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
import com.DBConnection.DBConnection;
import com.dashboard.bean.Enquiry;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.ws.rs.GET;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import java.util.*;
import javax.servlet.ServletContext;

public class IndiamartEnquiriesService implements ServletContextListener {

    int scheduler_count = 0;
    String path = "";
    Connection connection = null;
    @Context
    ServletContext servletContext;

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {

    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        servletContext = servletContextEvent.getServletContext();
        TimerTask vodTimer = new VodTimerTask();
        Timer timer = new Timer();

        Date date = new Date();

        timer.schedule(vodTimer, 20 * 1000, (6 * 60 * 60 * 1000));
//        timer.schedule(vodTimer, 20 * 1000, (6 * 60 * 1000));

    }

    class VodTimerTask extends TimerTask {

        @GET
        @Path("/getEnquiriesFromIndiaMart")
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        public String getEnquiriesFromIndiaMart() throws JSONException {
            APLWebServiceModel model = new APLWebServiceModel();
            String msg = "";
            try {
                model.setConnection(DBConnection.getConnectionForUtf(servletContext));
            } catch (Exception e) {
                System.out.println("com.webservice.controller.APLWebServiceController.getCalendarData() -" + e);
            }
            String result = "";
            JSONObject obj = new JSONObject();
            try {

                String start_date = "";
                String end_date = "";
                Date date = new Date();
                Date yesterday_date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy");
                yesterday_date.setTime(date.getTime() - 24 * 60 * 60 * 1000);
                start_date = sdf.format(yesterday_date);
                end_date = sdf.format(date);

//                String webPage = "https://mapi.indiamart.com/wservce/enquiry/listing/GLUSR_MOBILE/7624002261/GLUSR_MOBILE_KEY/MTYzODM1NTQzMy44NzIxIzIzNzczOTYz/Start_Time/" + start_date + "12:00:00/End_Time/" + end_date + "12:00:00/";
                String webPage = "https://mapi.indiamart.com/wservce/enquiry/listing/GLUSR_MOBILE/7624002261/GLUSR_MOBILE_KEY/MTYzODM1NTQzMy44NzIxIzIzNzczOTYz/Start_Time/" + start_date + "/End_Time/" + end_date + "/";
                URL url = new URL(webPage);
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.addRequestProperty("User-Agent", "Mozilla/4.76");
                urlConnection.setRequestProperty("Cookie", "foo=bar");
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setDoOutput(true);

                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }

                    result = response.toString();
                }

                JSONArray jsonArr = new JSONArray(result);

                for (int i = jsonArr.length() - 1; i >= 0; i--) {
                    JSONObject jsonObj = jsonArr.getJSONObject(i);

                    int enquiry_table_id = 0;

                    Enquiry bean = new Enquiry();
                    bean.setEnquiry_table_id(enquiry_table_id);
                    bean.setMarketing_vertical_name("Agricultural");
                    bean.setEnquiry_no(jsonObj.get("QUERY_ID").toString());
                    bean.setSender_name(jsonObj.get("SENDERNAME").toString());
                    bean.setSender_email(jsonObj.get("SENDEREMAIL").toString());
                    bean.setSender_alternate_email(jsonObj.get("EMAIL_ALT").toString());
                    bean.setSender_mob(jsonObj.get("MOB").toString());
                    bean.setSender_alternate_mob(jsonObj.get("MOBILE_ALT").toString());
                    bean.setSender_company_name(jsonObj.get("GLUSR_USR_COMPANYNAME").toString());
                    bean.setEnquiry_address(jsonObj.get("ENQ_ADDRESS").toString());
                    bean.setEnquiry_city(jsonObj.get("ENQ_CITY").toString());
                    bean.setEnquiry_state(jsonObj.get("ENQ_STATE").toString());
                    bean.setCountry(jsonObj.get("COUNTRY_ISO").toString());
                    bean.setEnquiry_message(jsonObj.get("ENQ_MESSAGE").toString());
                    bean.setDate_time(jsonObj.get("DATE_TIME_RE").toString());

                    if (enquiry_table_id == 0) {
                        model.insertEnquiries(bean);
                    }

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        public void run() {
            try {

                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String current_time = sdf.format(date);

                String start_time = "09:00:00";
                String end_time = "09:05:00";

                SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
                Date current_time1 = sdf1.parse(current_time);
                Date start_time1 = sdf1.parse(start_time);
                Date end_time1 = sdf1.parse(end_time);
                System.err.println("current_time1-----" + current_time1);

                getEnquiriesFromIndiaMart();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
