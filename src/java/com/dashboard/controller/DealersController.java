package com.dashboard.controller;

import com.DBConnection.DBConnection;
import com.dashboard.bean.Profile;
import com.dashboard.model.ProfileModel;
import com.organization.tableClasses.Org_Office;
import com.organization.tableClasses.OrganisationDesignationBean;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 *
 * @author Komal
 */
public class DealersController extends HttpServlet {

    private File tmpDir;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String logged_user_name = "";
        String logged_designation = "";
        String logged_org_name = "";
        String logged_org_office = "";
        String loggedUser = "";
        int logged_org_office_id = 0;
        int logged_org_name_id = 0;
        int logged_key_person_id = 0;
        Map<String, String> map = new HashMap<String, String>();

        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("logged_user_name") == null) {
            request.getRequestDispatcher("/").forward(request, response);
            return;
        } else {
            loggedUser = session.getAttribute("user_role").toString();
            logged_user_name = session.getAttribute("logged_user_name").toString();
            logged_org_name = session.getAttribute("logged_org_name").toString();
            logged_designation = session.getAttribute("logged_designation").toString();
            logged_org_office = session.getAttribute("logged_org_office").toString();
            logged_org_name_id = Integer.parseInt(session.getAttribute("logged_org_name_id").toString());
            logged_org_office_id = Integer.parseInt(session.getAttribute("logged_org_office_id").toString());
            logged_key_person_id = Integer.parseInt(session.getAttribute("logged_key_person_id").toString());
        }
        System.out.println("Starting application");
        response.setContentType("text/html");
        ServletContext ctx = getServletContext();
        ProfileModel model = new ProfileModel();

        try {
            model.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in CityController setConnection() calling try block" + e);
        }

        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        System.out.println(model.getConnection());

        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String action2 = "";
            String q = request.getParameter("str");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("mobile_number")) {
                    list = model.getMobilevalidty(q);//, request.getParameter("action2")
                }

                JSONObject gson = new JSONObject();
                gson.put("list", list);
                out.println(gson);

                DBConnection.closeConncetion(model.getConnection());
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --OrgOfficeNewController get JQuery Parameters Part-" + e);
        }
        String task = request.getParameter("task");

        if (task == null) {
            task = "";
        }
        if (task.equals("getAllDealers")) {

            JSONObject obj1 = new JSONObject();
            JSONArray arrayObj = new JSONArray();
            arrayObj = model.getAllDealerList();
            obj1.put("dealers", arrayObj);
            PrintWriter out = response.getWriter();
            out.print(obj1);
            DBConnection.closeConncetion(model.getConnection());

            return;
        }

        if (task.equals("viewDealerDetails")) {
            String key_person_id = request.getParameter("key_person_id");
            String org_office_id = request.getParameter("org_office_id");

            ArrayList<Profile> list = model.viewDealerDetails(key_person_id, org_office_id);
            request.setAttribute("list", list);
            DBConnection.closeConncetion(model.getConnection());

            request.getRequestDispatcher("dealer_details").forward(request, response);
        }

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
                        // System.out.println(image_name);
                        map.put(item.getFieldName(), item.getName());
                    }
                }
            }
            itr = null;
            itr = items.iterator();
        } catch (Exception ex) {
            System.out.println("Error encountered while uploading file" + ex);
        }

        String task1 = map.get("task1");
        if (task1 == null) {
            task1 = "";
        }
        if (task1.equals("Submit")) {
            int org_office_id;
            int key_person_id;
            int org_office_designation_map_id;
            int general_image_details_id;
            try {
                org_office_id = Integer.parseInt("0" + map.get("org_office_id").trim());
                key_person_id = Integer.parseInt("0" + map.get("key_person_id").trim());
                org_office_designation_map_id = Integer.parseInt("0" + map.get("org_office_designation_map_id").trim());
                general_image_details_id = Integer.parseInt("0" + map.get("general_image_details_id").trim());
            } catch (Exception e) {
                org_office_id = 0;
                key_person_id = 0;
                org_office_designation_map_id = 0;
                general_image_details_id = 0;
            }

            Org_Office orgOffice = new Org_Office();
            orgOffice.setOrg_office_id(org_office_id);
            orgOffice.setOrganisation_name("Apogee Precision Lasers");
            orgOffice.setOrganisation_id(5);
            orgOffice.setOrg_office_name(map.get("org_office_name").trim());
            orgOffice.setOffice_type("Dealer Office");
            orgOffice.setOffice_type_id(3);
            orgOffice.setOrg_office_code(map.get("org_office_name").trim());
            orgOffice.setAddress_line1(map.get("address_line1").trim());
            orgOffice.setAddress_line2(map.get("address_line2").trim());
            orgOffice.setAddress_line3(map.get("address_line3").trim());
            orgOffice.setCity_id(model.getCity_id((map.get("city_name").trim())));
            orgOffice.setEmail_id1(map.get("email_id1").trim());
            orgOffice.setEmail_id2("");
            orgOffice.setMobile_no1(map.get("mobile_no1").trim());
            orgOffice.setMobile_no2("");
            orgOffice.setLandline_no1("");
            orgOffice.setLandline_no2("");
            orgOffice.setLandline_no3("");
            orgOffice.setGst_number(map.get("gst_number").trim());
            orgOffice.setLatitude(map.get("latitude").trim());
            orgOffice.setLongitude(map.get("longitude").trim());

            String superp = "";
            String p_org_office = "";
            orgOffice.setSuperp(superp);
            orgOffice.setP_org(p_org_office);

            if (org_office_id == 0) {
                try {
                    model.insertRecord(orgOffice);
                } catch (SQLException ex) {
                    Logger.getLogger(DealersController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            OrganisationDesignationBean orgOfficebean = new OrganisationDesignationBean();
            orgOfficebean.setId(org_office_designation_map_id);
            orgOfficebean.setOrganisation(map.get("org_office_name").trim());
            orgOfficebean.setDesignation("Owner");
            if (org_office_designation_map_id == 0) {
                model.mapOrganisationDesignation(orgOfficebean);
            }

            Profile key = new Profile();
            key.setKey_person_id(key_person_id);
            key.setSalutation("Mr.");
            key.setKey_person_name(map.get("key_person_name").trim());
            key.setFather_name("");
            key.setDate_of_birth((map.get("date_of_birth").trim()));
            key.setDesignation("Owner");
            key.setDesignation_id(8);
            key.setId_type(map.get("id_type"));
            key.setId_type_d(model.getIdtype_id(map.get("id_type")));
            key.setId_no(map.get("id_no").trim());
            key.setOrg_office_id(model.getOrgOffice_id((map.get("org_office_name").trim()), map.get("org_office_name").trim()));

            key.setOrg_office_des_map_id(model.getOrgOfficeDesignationMapId(8, model.getOrgOffice_id((map.get("org_office_name").trim()), map.get("org_office_name").trim())));

            key.setCity_id(model.getCity_id((map.get("city_name").trim())));
            key.setAddress_line1(map.get("address_line1").trim());
            key.setAddress_line2(map.get("address_line2").trim());
            key.setAddress_line3(map.get("address_line3").trim());
            String gender = map.get("gender");
            if (map.get("gender") == null) {
                gender = "";
            }
            key.setGender(gender.trim());

            key.setPassword("");
            key.setBlood(map.get("blood").trim());
            key.setEmergency_number("");
            key.setEmergency_name("");

            key.setMobile_no1(map.get("key_person_mobile").trim());

            key.setMobile_no2("");
            key.setLandline_no1("");
            key.setLandline_no2("");
            key.setEmail_id1(map.get("email_id1").trim());
            key.setEmail_id2("");
            key.setLatitude(map.get("latitude").trim());
            key.setLongitude(map.get("longitude").trim());
            key.setEmp_code(12);
            key.setImage_path(map.get("design_name"));
            key.setId_proof(map.get("id_proof"));
            key.setGeneral_image_details_id(general_image_details_id);
            key.setFamilyName("");
            key.setFamilyDesignation("");
            key.setRelation("");
            String photo_destination = model.getDestination_Path("key_person_photo");
            String iD_destination = model.getDestination_Path("key_person_ID");
            response.setContentType("image/jpeg");
            String mobile_no1 = map.get("mobile_no1");

            if (key_person_id == 0) {
                try {
                    int kp_id = model.insertKeyPersonRecord(key, itr, photo_destination, iD_destination);
                } catch (SQLException ex) {
                    Logger.getLogger(DealersController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }

        if (task.equals("AddDealer")) {
            List id_list = model.getIdtypeList();
            request.setAttribute("id_list", id_list);

            request.getRequestDispatcher("add_dealer").forward(request, response);

        }
        List id_list = model.getIdtypeList();
        List<Profile> list = model.getAllDealers();
        request.setAttribute("id_list", id_list);

        request.setAttribute("list", list);
        request.setAttribute("message", model.getMessage());
        request.setAttribute("msgBgColor", model.getMessageBGColor());
        DBConnection.closeConncetion(model.getConnection());

        request.getRequestDispatcher("dealers").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
