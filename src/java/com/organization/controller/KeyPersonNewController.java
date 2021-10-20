/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.organization.controller;

import com.organization.model.KeypersonModel;
import com.DBConnection.DBConnection;
import com.organization.tableClasses.KeyPerson;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONObject;

/**
 *
 * @author SoftTech
 */
public class KeyPersonNewController extends HttpServlet {

    private File tmpDir;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        Map<String, String> map = new HashMap<String, String>();
        ArrayList<String> List = new ArrayList<String>();
        ArrayList<String> List1 = new ArrayList<String>();
        List liste = null;
        String active = "Y";
        String ac = "ACTIVE RECORDS";
        ServletContext ctx = getServletContext();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        KeypersonModel keyModel = new KeypersonModel();
        String k = "";
        try {
//            keyModel.setDriverClass(ctx.getInitParameter("driverClass"));
//            keyModel.setDb_username(ctx.getInitParameter("db_user_name"));
//            keyModel.setDb_password(ctx.getInitParameter("db_user_password"));
//            keyModel.setConnectionString(ctx.getInitParameter("connectionString"));
//            keyModel.setConnection();

        } catch (Exception e) {
            System.out.println("error in KeypersonController setConnection() calling try block" + e);
        }

        try {
            String isOrgBasicStep = request.getParameter("isOrgBasicStep");
            String office_code = request.getParameter("searchOrg");
            String searchfamily = request.getParameter("searchfamily");
            String person = request.getParameter("searchKeyPerson");
            String designation = request.getParameter("searchDesignation");
            String mobile = request.getParameter("searchmobile");

            if (isOrgBasicStep != null && !isOrgBasicStep.isEmpty()) {
                isOrgBasicStep = isOrgBasicStep.trim();
            } else {
                isOrgBasicStep = "";
            }
            if (office_code != null && !office_code.isEmpty()) {
                office_code = office_code.trim();
            } else {
                office_code = "";
            }
            if (person != null && !person.isEmpty()) {
                person = person.trim();
            } else {
                person = "";
            }
            if (designation != null && !designation.isEmpty()) {
                designation = designation.trim();
            } else {
                designation = "";
            }
            if (mobile != null && !mobile.isEmpty()) {
                mobile = mobile.trim();
            } else {
                mobile = "";
            }
            if (searchfamily != null && !searchfamily.isEmpty()) {
                searchfamily = searchfamily.trim();
            } else {
                searchfamily = "";
            }
            String requester = request.getParameter("requester");
            try {
                //----- This is only for Vendor key Person JQuery
                String JQstring = request.getParameter("action1");

                String q = request.getParameter("str");   // field own input
                if (JQstring != null) {
                    PrintWriter out = response.getWriter();
                    List<String> list = null;
                    if (JQstring.equals("getStateName")) {
                        list = keyModel.getStateName(q);
                    } else if (JQstring.equals("mobile_number")) {
                        list = keyModel.getMobilevalidty(q);//, request.getParameter("action2")
                    } else if (JQstring.equals("getMobile")) {
//                        list = keyModel.getsearchMobile(q);//, request.getParameter("action2")
                    } else if (JQstring.equals("getCityName")) {
                        list = keyModel.getCityName(q);//, request.getParameter("action2")
                    } else if (JQstring.equals("getOfficeType")) {
                        list = keyModel.getOffice_type(q);
                    } else if (JQstring.equals("getsearchOrganisation")) {
                        list = keyModel.getsearchOrganisation(q);
                    } else if (JQstring.equals("getSearchEmpCode")) {
                        list = keyModel.getSearchEmpCode(q);
                    } else if (JQstring.equals("getSearchKeyPerson")) {
                        String code = request.getParameter("action2");

                        list = keyModel.getOrgPersonNameList(q, code);
                    } else if (JQstring.equals("getSearchDesignation")) {
                        String code = request.getParameter("action2");
                        String key_person = request.getParameter("action3");
                        list = keyModel.getDesignation(q, code, key_person);
                    } else if (JQstring.equals("getOrgOfficeName")) {
                        list = keyModel.getOrgOfficeName(q, request.getParameter("action2"));
                    } else if (JQstring.equals("getDesignation")) {
                        String org_office = request.getParameter("action2");
                        int parent_check = 0;
                        //String key_person = request.getParameter("action3");
                        list = keyModel.getDesgnation(q, org_office, parent_check);
                        System.out.println(list.toString());
                    } else if (JQstring.equals("getunknownDesignation")) {
                        String org_office = request.getParameter("action2");
                        int parent_check = 0;
                        //String key_person = request.getParameter("action3");
                        list = keyModel.getunDesgnation(q, org_office, parent_check);
                        System.out.println(list.toString());
                    } else if (JQstring.equals("getOrgOfficeCode")) {
                        list = keyModel.getOrgOfficeCode(q);
                    } else if (JQstring.equals("searchOrg")) {
                        list = keyModel.searchOrg(q);
                    } else if (JQstring.equals("searchOrgOfficeCode")) {
                        list = keyModel.searchOrgOfficeCode(q);
                    } else if (JQstring.equals("getfamilycode")) {
//                        list = keyModel.searchfamilyOfficeCode(q);
                    }
                    JSONObject gson = new JSONObject();
                    gson.put("list", list);
                    out.println(gson);
                    keyModel.closeConnection();
                    return;
                }
            } catch (Exception e) {
                System.out.println("\n Error --SiteListController get JQuery Parameters Part-" + e);
            }

            List id_list = keyModel.getIdtypeList();

            List items = null;
            Iterator itr = null;
            DiskFileItemFactory fileItemFactory = new DiskFileItemFactory(); //Set the size threshold, above which content will be stored on disk.
            fileItemFactory.setSizeThreshold(1 * 1024 * 1024); //1 MB Set the temporary directory to store the uploaded files of size above threshold.
            fileItemFactory.setRepository(tmpDir);
            ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
            try {
                items = uploadHandler.parseRequest(request);
                itr = items.iterator();
                while (itr.hasNext()) {
                    FileItem item = (FileItem) itr.next();
                    if (item.isFormField()) {
                        System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString() + "\n");//(getString())its for form field
                        map.put(item.getFieldName(), item.getString("UTF-8"));
                    } else {
                        System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getName());//it is (getName()) for file related things
                        if (item.getName() == null || item.getName().isEmpty()) {
                            map.put(item.getFieldName(), "");
                        } else {
                            String image_name = item.getName();
                            image_name = image_name.substring(0, image_name.length());
                            System.out.println(image_name);
                            map.put(item.getFieldName(), item.getName());
                        }
                    }
                }
                itr = null;
                itr = items.iterator();
            } catch (Exception ex) {
                System.out.println("Error encountered while uploading file" + ex);
            }
            String task1 = request.getParameter("task1");
            if (task1 == null) {
                task1 = "";
            }
            String task = map.get("task");
            if (task == null) {
                task = "";
            }
            String searchOrganisation = null, searchKeyPerson = null, searchOfficeCode = null, searchEmpCode = null, searchDesignation = null;
            office_code = request.getParameter("searchOrg");
            person = request.getParameter("searchKeyPerson");
            designation = request.getParameter("searchDesignation");
            mobile = request.getParameter("searchmobile");
            searchOrganisation = request.getParameter("searchorg");
            searchfamily = request.getParameter("searchfamily");
            try {
                if (office_code == null) {
                    office_code = "";
                }
                if (person == null) {
                    person = "";
                }
                if (designation == null) {
                    designation = "";
                }
                if (searchEmpCode == null) {
                    searchEmpCode = "";
                }
                if (searchDesignation == null) {
                    searchDesignation = "";
                }
                if (mobile == null) {
                    mobile = "";
                }
                if (searchfamily == null) {
                    searchfamily = "";
                }
                if (searchOrganisation == null) {
                    searchOrganisation = "";
                }
            } catch (Exception e) {
            }

            String task2 = request.getParameter("task2");
            if (task2 == null) {
                task2 = "";
            }

            if (task2.equals("ACTIVE RECORDS")) {
                active = "Y";
                ac = "ACTIVE RECORDS";
            } else if (task2.equals("INACTIVE RECORDS")) {
                active = "N";
                ac = "INACTIVE RECORDS";
            } else if (task2.equals("ALL RECORDS")) {
                active = "";
                ac = "ALL RECORDS";
            }

            String active1 = request.getParameter("active");

            if (task1.equals("viewImage")) {
                try {
                    // String jrxmlFilePath;
                    // String img_name = "PRHindi_" ;
                    //String repository_name = "C:/ssadvt_repository/SOR";
                    //int estimate_no = Integer.parseInt(request.getParameter("estimate_no"));
                    //int estimate_revision_no = Integer.parseInt(request.getParameter("estimate_revision_no"));
                    String destinationPath = "";
                    String kp_id = request.getParameter("kp_id");
                    String type = request.getParameter("type");
                    if (kp_id != null && !kp_id.isEmpty()) {
                        destinationPath = keyModel.getImagePath(kp_id, type);
                        if (destinationPath.isEmpty()) {
                            destinationPath = "C:\\ssadvt_repository\\health_department\\general\\no_image.png";
                        }
                    } else {
                        System.out.println("Image Not Found");
                        destinationPath = "C:\\ssadvt_repository\\health_department\\general\\no_image.png";
                    }
                    //destinationPath = keyModel.getImagePath(emp_code);
                    File f = new File(destinationPath);
                    FileInputStream fis = null;
                    if (!f.exists()) {
                        destinationPath = "C:\\ssadvt_repository\\health_department\\general\\no_image.png";
                        f = new File(destinationPath);
                    }
                    fis = new FileInputStream(f);
                    if (destinationPath.contains("pdf")) {
                        response.setContentType("pdf");
                    } else {
                        response.setContentType("image/jpeg");
                    }

                    //  response.addHeader("Content-Disposition", "attachment; filename=\"" + f.getName() + "\"");
                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
                    // BufferedImage bi=ImageIO.read(f);
                    response.setContentLength(fis.available());
                    ServletOutputStream os = response.getOutputStream();
                    BufferedOutputStream out = new BufferedOutputStream(os);
                    int ch = 0;
                    ;
                    while ((ch = bis.read()) != -1) {
                        out.write(ch);
                    }

                    bis.close();
                    fis.close();
                    out.close();
                    os.close();
                    response.flushBuffer();

                    keyModel.closeConnection();
                    return;
                } catch (Exception e) {
                    System.out.println("SelectSupplierController Demand Note Error :" + e);
                    return;
                }
            }

            if (task1.equals("showMapWindow")) {

                String point_id = request.getParameter("keyperson_id");
                String latitude = "";
                String longitude = "";
                String LatLong = keyModel.getPointLatLong(point_id);
                System.out.println(LatLong);
                String[] words = LatLong.split("\\,");
                for (int i = 0; i < words.length; i++) {
                    latitude = words[0];
                    longitude = words[1];
                    System.out.println(latitude + "  " + longitude);
                }
                request.setAttribute("longi", latitude);
                request.setAttribute("latti", longitude);
                //System.out.println(latti + "," + longi);
                request.getRequestDispatcher("openMapWindowView").forward(request, response);
                return;

            }

//            if (task1.equals("PRINTRecordList")) {
//                String jrxmlFilePath;
//                List list = null;
//                response.setContentType("application/pdf");
//                response.setCharacterEncoding("UTF-8");
//                ServletOutputStream servletOutputStream = response.getOutputStream();
//                jrxmlFilePath = ctx.getRealPath("/report/Keyperson.jrxml");
//                list = keyModel.showAll(designation, person, office_code, searchEmpCode, searchDesignation, mobile, active1, searchfamily);
//                byte[] reportInbytes = keyModel.generateKeyperSonList(jrxmlFilePath, list);
//                response.setContentLength(reportInbytes.length);
//                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
//                servletOutputStream.flush();
//                servletOutputStream.close();
//                return;
//            } else if (task1 != null && task1.equals("PRINTExcelList")) {
//
//                String jrxmlFilePath;
//                List list = null;
//                response.setContentType("application/vnd.ms-excel");
//                response.setHeader("Content-Disposition", "attachment; filename=KeyPerson.xls");
//                ServletOutputStream servletOutputStream = response.getOutputStream();
//                jrxmlFilePath = ctx.getRealPath("/Report/organization/Keyperson.jrxml");
//                // list=keyModel.showAll(-1,noOfRowsToDisplay,searchKeyPerson,searchOfficeCode, searchEmpCode,searchDesignation);
//                //    ByteArrayOutputStream reportInbytes =keyModel.generateXlsRecordList(jrxmlFilePath,list);
//                //    response.setContentLength(reportInbytes.toByteArray().length);
//
//                //  servletOutputStream.write(reportInbytes.toByteArray() , 0, reportInbytes.toByteArray().length);
//                servletOutputStream.flush();
//                servletOutputStream.close();
//
//                return;
//            }
            if (task.equals("Delete")) {
                //String destination = "E:/Traffic/ImageUpload/";
                keyModel.deleteRecord(Integer.parseInt(map.get("key_person_id")));  // Pretty sure that city_id will be available.
            } else if (task.equals("Save") || task.equals("Save AS New")) {
                int key_person_id;
                int general_image_details_id = 0;
                try {
                    key_person_id = Integer.parseInt(map.get("key_person_id"));
                    general_image_details_id = Integer.parseInt(map.get("general_image_details_id"));// city_id may or may NOT be available i.e. it can be update or new record.
                } catch (Exception e) {
                    key_person_id = 0;
                    general_image_details_id = 0;
                }
                if (task.equals("Save AS New")) {
                    //   key_person_id = 0;
                    general_image_details_id = 0;
                }

//        for(int j=1;j<=k;j++)
//        {
//             ArrayList<String> emergency=new ArrayList<String>();
//                 emergency.add(map.get("emergency_name"+j));
//                 emergency.add(map.get("emergency_number"+j));
//                   System.out.println(emergency); 
//                   List.addAll(emergency);
//                   keyModel.insertemergency(List);
//                   //List<String> contact = 
//                   
//        }
                KeyPerson key = new KeyPerson();
                key.setKey_person_id(key_person_id);
                key.setSalutation(new String(map.get("salutation").trim().getBytes("iso-8859-1"), "UTF-8"));
                key.setKey_person_name(map.get("key_person_name").trim());
                key.setFather_name(map.get("father_name").trim());
                key.setDate_of_birth((map.get("date_of_birth")));
                key.setDesignation(map.get("designation").trim());
                key.setDesignation_id(keyModel.getDesignation_id((map.get("designation").trim())));
                key.setId_type(map.get("id_type"));
                key.setId_type_d(keyModel.getIdtype_id(map.get("id_type")));
                key.setId_no(map.get("id_no").trim());
                key.setOrg_office_id(keyModel.getOrgOffice_id((map.get("org_office_name").trim()), map.get("office_code").trim()));
                key.setCity_id(keyModel.getCity_id((map.get("city_name").trim())));
                key.setAddress_line1(map.get("address_line1").trim());
                key.setAddress_line2(map.get("address_line2").trim());
                key.setAddress_line3(map.get("address_line3").trim());
                key.setGender(map.get("gender").trim());

                key.setPassword(map.get("password").trim());
                key.setBlood(map.get("blood").trim());
                key.setEmergency_number(map.get("emergency_number"));
                key.setEmergency_name(map.get("emergency_name"));

                key.setMobile_no1(map.get("mobile_no1").trim());

                key.setMobile_no2(map.get("mobile_no2").trim());
                key.setLandline_no1(map.get("landline_no1").trim());
                key.setLandline_no2(map.get("landline_no2").trim());
                key.setEmail_id1(map.get("email_id1").trim());
                key.setEmail_id2(map.get("email_id2").trim());
                key.setLatitude(map.get("latitude").trim());
                key.setLongitude(map.get("longitude").trim());
                key.setEmp_code(map.get("employeeId").trim());
                key.setImage_path(map.get("design_name"));
                key.setId_proof(map.get("id_proof"));
                key.setGeneral_image_details_id(general_image_details_id);
                key.setFamilyName(map.get("familyoffice").trim());
                key.setFamilyDesignation(map.get("familydesignation").trim());
                key.setRelation(map.get("relation").trim());
                //String destination = "E:/Traffic/ImageUpload/";
                String photo_destination = keyModel.getDestination_Path("key_person_photo");
                String iD_destination = keyModel.getDestination_Path("key_person_ID");
                response.setContentType("image/jpeg");
                String mobile_no1 = map.get("mobile_no1");

                k = (map.get("i"));
                if ((k).equals("")) {
                    k = "0";
                }

                for (int j = 1; j <= Integer.parseInt(k); j++) {
                    ArrayList<String> emergency = new ArrayList<String>();
                    HashSet<String> name = new HashSet<String>();
                    HashSet<String> number = new HashSet<String>();

                    name.add(map.get("emergency_name" + j));
                    number.add(map.get("emergency_number" + j));

                    List.addAll(name);
                    List1.addAll(number);
                    //    keyModel.insertemergency(List,key_person_id,k);
                    //List<String> contact = 

                }
                List.add(map.get("emergency_name"));
                List1.add(map.get("emergency_number"));

                if (key_person_id == 0) {
                    // if city_id was not provided, that means insert new record.

                    int kp_id = keyModel.insertRecord(key, itr, photo_destination, iD_destination);
                    keyModel.insertemergency(List, List1, kp_id, k, mobile_no1);

                } else {
                    // update existing record.
//                    keyModel.updateRecord(key, key_person_id);
                    keyModel.insertemergency(List, List1, key_person_id, k, mobile_no1);
                    //  keyModel.updateRecord(key, itr, photo_destination, iD_destination);
                }
            } else if (task1.equals("Show All Records")) {
                searchOrganisation = "";
                searchKeyPerson = "";
                searchOfficeCode = "";
                searchEmpCode = "";
                searchDesignation = "";
                designation = "";
                person = "";
                office_code = "";
                mobile = "";
                searchfamily = "";
            }

            try {
                lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
                noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
            } catch (Exception e) {
                lowerLimit = noOfRowsTraversed = 0;
            }

            String buttonAction = request.getParameter("buttonAction"); // Holds the name of any of the four buttons: First, Previous, Next, Delete.
            if (buttonAction == null) {
                buttonAction = "none";
            } else {
                active = active1;
                ac = active;

                if (active.equals("")) {
                    ac = "ALL RECORDS";
                } else if (active.equals("Y")) {
                    ac = "ACTIVE RECORDS";
                } else {
                    ac = "INACTIVE RECORDS";
                }

            }
//            noOfRowsInTable = keyModel.getNoOfRows(searchOrganisation, person, searchOfficeCode, searchEmpCode, searchDesignation, mobile, active, office_code, searchfamily);
//            if (buttonAction.equals("Next")); // lowerLimit already has value such that it shows forward records, so do nothing here.
//            else if (buttonAction.equals("Previous")) {
//                int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
//                if (temp < 0) {
//                    noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
//                    lowerLimit = 0;
//                } else {
//                    lowerLimit = temp;
//                }
//            } else if (buttonAction.equals("First")) {
//                lowerLimit = 0;
//            } else if (buttonAction.equals("Last")) {
//                lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
//                if (lowerLimit < 0) {
//                    lowerLimit = 0;
//                }
//            }
//
//            if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New")) {
//                lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
//            }
            // Logic to show data in the table.
            List<KeyPerson> keyList = keyModel.showData(designation, person, office_code, searchEmpCode, searchDesignation, mobile, active, searchfamily);

            //lowerLimit = lowerLimit + keyList.size();
//            noOfRowsTraversed = keyList.size();
//
//            // Now set request scoped attributes, and then forward the request to view.
//            request.setAttribute("lowerLimit", lowerLimit);
//            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("keyList", keyList);

//            if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
//                request.setAttribute("showFirst", "false");
//                request.setAttribute("showPrevious", "false");
//            }
//            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
//                request.setAttribute("showNext", "false");
//                request.setAttribute("showLast", "false");
//            }
            if (task1.equals("showEmergency")) {

                String id = request.getParameter("keyperson_id");
                liste = keyModel.showEmergency(id);
                request.setAttribute("emerList", liste);
                request.getRequestDispatcher("Emergency").forward(request, response);
            }
            request.setAttribute("emerList", liste);
            request.setAttribute("message", keyModel.getMessage());
            request.setAttribute("msgBgColor", keyModel.getMsgBgColor());
            request.setAttribute("searchOfficeCode", searchOfficeCode);
            request.setAttribute("searchmobile", mobile);
            request.setAttribute("searchEmpCode", searchEmpCode);
            request.setAttribute("searchKeyPerson", person);
            request.setAttribute("searchDesignation", searchDesignation);
            request.setAttribute("searchfamily", searchfamily);
            request.setAttribute("searchOrg", office_code);
            request.setAttribute("active", active);
            request.setAttribute("ac", ac);
            request.setAttribute("id_list", id_list);
            keyModel.closeConnection();
            if (isOrgBasicStep.equals("Yes")) {
                request.setAttribute("stepNo", 4);
                request.getRequestDispatcher("orgBasicEntryView").forward(request, response);
            } else {
                request.getRequestDispatcher("keypersonnew").forward(request, response);
            }

        } catch (Exception ex) {

            System.out.println("KeypersonController error: " + ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
