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
            System.out.println("error in OrgOfficeController setConnection() calling try block" + e);
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
        try{
        
        String unknum = model.sendSmsToAssignedFor(number, otp);
        if (unknum.equals("OK")) {
            System.out.println("OTP HAS BEEN SENT");
            tosend = "Success";
            json.put("result", "Success");
            json.put("otp",otp);
        }else{
            json.put("result", "failure");
        }
        
        }catch(JSONException e){
            System.out.println("com.webservice.controller.APLWebServiceController.sendOTP() -"+e);
        }

        tosend=json.toString();
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
            System.out.println("error in OrgOfficeController setConnection() calling try block" + e);
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
                resp="Success";

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
                resp="Failure";
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
            System.out.println("error in OrgOfficeController setConnection() calling try block" + e);
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
            
            json=model.getImageData(number);
            obj.put("image_data", json);
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
            System.out.println("Error in aplService getAllTableRecords()..." + e);
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
            System.out.println("error in OrgOfficeController setConnection() calling try block" + e);
        }
        JSONObject obj = new JSONObject();
        try {
            String type = jObj.get("type").toString();
            String number = jObj.get("number").toString();
            String current_time = jObj.get("current_time").toString();
            String latitude = jObj.get("latitude").toString();
            String longitude = jObj.get("longitude").toString();

            int count = model.saveAttendance(type, number, current_time, latitude, longitude);
            if (count > 0) {
                result = "Success";
            }
        } catch (JSONException e) {
            System.out.println("com.webservice.controller.APLWebServiceController.saveAttendanceInOut()- " + e);
            result = e.toString();
        }
        return result;
    }

}
