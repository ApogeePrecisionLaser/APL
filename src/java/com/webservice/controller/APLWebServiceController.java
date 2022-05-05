
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webservice.controller;

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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.net.ssl.HttpsURLConnection;
import javax.ws.rs.GET;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author Vikrant
 */
@Path("/aplService")
public class APLWebServiceController {

    @Context
    ServletContext serveletContext;
    Connection connection = null;
    static Map<String, String> otpMap = new HashMap<String, String>();

    @POST
    @Path("/sendOTP")
    @Produces(MediaType.APPLICATION_JSON)//http://192.168.1.15:8084/trafficSignals_new/api/service/hello
    @Consumes(MediaType.APPLICATION_JSON)
    public String sendOTP(String number) {
        APLWebServiceModel model = new APLWebServiceModel();
        try {
            model.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception e) {
            System.out.println("error in APLWebServiceController setConnection() calling try block" + e);
        }
        String tosend = "Failure";
        String otp = model.random(4);
        System.out.println("OTP is :" + otp);
        if (otpMap.containsKey(number)) {
            otpMap.remove(number);
        }
        org.codehaus.jettison.json.JSONObject json = new org.codehaus.jettison.json.JSONObject();
        otpMap.put(number, otp);
        System.err.println("otp map--------" + otpMap);
        try {

            String unknum = model.sendSmsToAssignedFor(number, otp);
            if (unknum.equals("OK")) {
                System.out.println("OTP HAS BEEN SENT");
                tosend = "Success";
                json.put("result", "Success");
                json.put("otp", otp);
            } else {
                json.put("result", "failure");
            }

        } catch (JSONException e) {
            System.out.println("com.webservice.controller.APLWebServiceController.sendOTP() -" + e);
        }

        tosend = json.toString();
        System.out.println("Response on mobile number :" + tosend);
        return tosend;
    }

    @POST
    @Path("/verifyOTP")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String verifyOTP(org.codehaus.jettison.json.JSONObject jsnobject) {
        APLWebServiceModel model = new APLWebServiceModel();
        try {
            // organisationModel.setConnection(DBConnection.getConnection(ctx, session));
            model.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception e) {
            System.out.println("error in APLWebServiceController setConnection() calling try block" + e);
        }
        Boolean status = false;
        System.out.println("inside method");
        String resp = "";
        //System.out.println(body);

        //JSONObject jsnobject = new JSONObject(body);
        //org.codehaus.jettison.json.JSONObject tosend = new org.codehaus.jettison.json.JSONObject();
        try {
            String number = jsnobject.get("number").toString();
            String otp = jsnobject.get("otp").toString();

            System.err.println("otp---------" + otp);
            System.err.println("otpMap.get(number)-------" + otpMap.get(number));
            if (otp.equals(otpMap.get(number))) {
                otpMap.remove(number);
                //tosend.put("result", "Success");
                resp = "Success";

                //tosend.put("result", "success");
                //KeyPerson man = model.checkExisting(number);
                //status = model.UpdateRecord(number, man.getKey_person_id());
//                if (status == true) {
//                    tosend.put("result", "success");
//
//                } else {
//                    tosend.put("result", "failure");
//
//                }
            } else {
                //tosend.put("result", "Failure");
                resp = "Failure";
            }
        } catch (JSONException ex) {
            Logger.getLogger(APLWebServiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resp;
    }

    @POST
    @Path("/getAllTableRecords")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject getAllTableRecords(String number) {
        System.err.println("aplService--------------------");
        APLWebServiceModel model = new APLWebServiceModel();
        try {
            model.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception e) {
            System.out.println("error in APLWebServiceController setConnection() calling try block" + e);
        }
        JSONObject obj = new JSONObject();

        try {

            //JSONObject obj = new JSONObject();
            JSONArray json = null;

            json = model.getOrganisationType(number);
            obj.put("organisation_type", json);
            json = model.getOrganisationName(number);
            obj.put("organisation_name", json);
            json = model.getOrgOfficeType(number);
            obj.put("org_office_type", json);
            json = model.getOrgOffice(number);
            obj.put("org_office", json);
            json = model.getOrgOfficeDesignationMap(number);
            obj.put("org_office_designation_map", json);
            json = model.getDesignation(number);
            obj.put("designation", json);
            json = model.getCity(number);
            obj.put("city", json);
            json = model.getKeyPerson(number);
            obj.put("key_person", json);

            json = model.getImageData(number);
            obj.put("image_data", json);

            json = model.getAttendanceData(number);
            obj.put("attendance", json);
//            json = model.getItemType(number);
//            obj.put("item_type", json);
//            json = model.getItemNames(number);
//            obj.put("item_names", json);
//            json = model.getGeneralImageDetails(number);
//            obj.put("general_image_details", json);
//            json = model.getImageDestination(number);
//            obj.put("image_destination", json);
//            json = model.getImageUploadedFor(number);
//            obj.put("image_uploaded_for", json);
//            json = model.getItemImageDetails(number);
//            obj.put("item_image_details", json);
//            json = model.getIdType(number);
//            obj.put("id_type", json);
        } catch (Exception e) {
            System.out.println("Error in APLWebServiceController getAllTableRecords()..." + e);
        }
        return obj;
    }

    @POST
    @Path("/saveAttendanceInOut")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String saveAttendanceInOut(JSONObject jObj) {
        String result = "Failure";

        // Start check date time 
//        ZoneId zoneid1 = ZoneId.of("Asia/Kolkata");
//        ZoneId zoneid2 = ZoneId.of("Asia/Tokyo");
//        LocalTime id1 = LocalTime.now(zoneid1);
//        LocalTime id2 = LocalTime.now(zoneid2);
//        System.out.println("Kolkata --------"+id1);
//        System.out.println("Japan --------"+id2);
//        System.out.println(id1.isBefore(id2));
        // End check date time 
        APLWebServiceModel model = new APLWebServiceModel();
        try {
            model.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception e) {
            System.out.println("error in APLWebServiceController setConnection() calling try block" + e);
        }
        JSONObject obj = new JSONObject();
        try {
            String type = jObj.get("type").toString();
            String number = jObj.get("number").toString();
            String current_time = jObj.get("current_time").toString();
            String latitude = jObj.get("latitude").toString();
            String longitude = jObj.get("longitude").toString();

            int count = model.saveAttendance(type, number, current_time, latitude, longitude);
            if (count == 60) {
                result = "You are already IN";
            }
            if (count == 50) {
                result = "You are already OUT";
            }
            if (count == 111) {
                result = "Attendance Marked";
            }
        } catch (JSONException e) {
            System.out.println("com.webservice.controller.APLWebServiceController.saveAttendanceInOut()- " + e);
            result = e.toString();
        }
        return result;
    }

    @POST
    @Path("/getCalendarData")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject getCalendarData(String number) {
        APLWebServiceModel model = new APLWebServiceModel();
        try {
            model.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception e) {
            System.out.println("com.webservice.controller.APLWebServiceController.getCalendarData() -" + e);
        }
        JSONObject obj = new JSONObject();

        try {
            JSONArray json = null;

            json = model.getHolidayData(number);
            obj.put("holiday_data", json);
            json = model.getLeaveData(number);
            obj.put("leave_data", json);
            json = model.getLeaveTypeData(number);
            obj.put("leave_type", json);
            json = model.getStatusTypeData(number);
            obj.put("status_type", json);

        } catch (Exception e) {
            System.out.println("com.webservice.controller.APLWebServiceController.getCalendarData() -" + e);
        }
        return obj;
    }

    @POST
    @Path("/sendMail")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String sendMail(String message) {
        APLWebServiceModel model = new APLWebServiceModel();
        String msg = "";
        try {
            model.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception e) {
            System.out.println("com.webservice.controller.APLWebServiceController.getCalendarData() -" + e);
        }

        try {
            msg = model.sentMail(message);
        } catch (Exception e) {
            System.err.println("exception-----" + e);
        }

        return msg;

    }

    @GET
    @Path("/getEnquiriesFromIndiaMart")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getEnquiriesFromIndiaMart(String message) throws JSONException, SQLException {
        APLWebServiceModel model = new APLWebServiceModel();
        String msg = "";
        try {
            model.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception e) {
            System.out.println("com.webservice.controller.APLWebServiceController.getCalendarData() -" + e);
        }

        String result = "";
        JSONObject obj = new JSONObject();
        try {

            String start_date = "";
            String end_date = "";

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy");
            start_date = sdf.format(date);
            end_date = sdf.format(date);

            String webPage = "https://mapi.indiamart.com/wservce/enquiry/listing/GLUSR_MOBILE/7624002261/GLUSR_MOBILE_KEY/MTYzODM1NTQzMy44NzIxIzIzNzczOTYz/Start_Time/" + start_date + "13:00:00/End_Time/" + end_date + "16:00:00/";

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
            
            for (int i = 0; i < jsonArr.length(); i++) {
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

//    @POST
//    @Path("/sendSmsToAssignedFor")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public String sendSmsToAssignedFor(String numberStr1, String messageStr1) {
//        String result = "";
//        numberStr1 = "8882349596";
//        messageStr1 = "Hello";
//
//        try {
//            String host_url = "http://login.smsgatewayhub.com/api/mt/SendSMS?";;
//            messageStr1 = java.net.URLEncoder.encode(messageStr1, "UTF-8");
//            String queryString = "APIKey=WIOg7OdIzkmYTrqTsw262w&senderid=JPSOFT&channel=2&DCS=8&flashsms=0&number=" + numberStr1 + "&text=" + messageStr1 + "&route=";
//            String url = host_url + queryString;
//            result = callURL(url);
//            System.out.println("SMS URL: " + url);
//        } catch (Exception e) {
//            result = e.toString();
//            System.out.println("SMSModel sendSMS() Error: " + e);
//        }
//        return result;
//    }
//
//    private String callURL(String strURL) {
//        String status = "";
//        try {
//            java.net.URL obj = new java.net.URL(strURL);
//            HttpURLConnection httpReq = (HttpURLConnection) obj.openConnection();
//            httpReq.setDoOutput(true);
//            httpReq.setInstanceFollowRedirects(true);
//            httpReq.setRequestMethod("GET");
//            status = httpReq.getResponseMessage();
//        } catch (MalformedURLException me) {
//            status = me.toString();
//        } catch (IOException ioe) {
//            status = ioe.toString();
//        } catch (Exception e) {
//            status = e.toString();
//        }
//        return status;
//    }
}
