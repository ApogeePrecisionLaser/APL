package com.dashboard.controller;

import com.DBConnection.DBConnection;
import com.dashboard.bean.Profile;
import com.dashboard.model.ProfileModel;
import com.organization.tableClasses.Org_Office;
import com.organization.tableClasses.OrganisationDesignationBean;
import java.io.ByteArrayOutputStream;
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
import org.json.simple.JSONObject;

public class ProfileController extends HttpServlet {

    private File tmpDir;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Map<String, String> map = new HashMap<String, String>();

        String logged_user_name = "";
        String logged_designation = "";
        String logged_org_name = "";
        String logged_org_office = "";
        String loggedUser = "";
        String loggedMobile = "";
        String loggedEmail = "";
        int logged_org_office_id = 0;
        int logged_org_name_id = 0;
        int logged_key_person_id = 0;

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
            loggedMobile = session.getAttribute("log_mobile").toString();
            loggedEmail = session.getAttribute("log_email").toString();
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
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("str");
            String str2 = request.getParameter("str2");
            String str3 = request.getParameter("str3");

            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                JSONObject json = null;
                if (JQstring.equals("getCities")) {
                    list = model.getCities(q);
                }
//                if (JQstring.equals("getStates")) {
//                    list = model.getStates(q);
//                }

                if (json != null) {
                    out.println(json);
                } else {
                    Iterator<String> iter = list.iterator();
                    JSONObject gson = new JSONObject();
                    gson.put("list", list);
                    out.println(gson);
                }
                DBConnection.closeConncetion(model.getConnection());

                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ItemNameController get JQuery Parameters Part-" + e);
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

        String task = map.get("task");
        if (task == null) {
            task = "";
        }

        String task1 = request.getParameter("task1");
        if (task1 == null) {
            task1 = "";
        }

        if (task1.equals("Change")) {
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");
            int update_rows = 0;
            try {
                update_rows = model.updatePassword(logged_key_person_id, newPassword, loggedUser);
            } catch (SQLException ex) {
                Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }

            request.setAttribute("message", model.getMessage());
            request.setAttribute("msgBgColor", model.getMessageBGColor());
            DBConnection.closeConncetion(model.getConnection());
            if (update_rows > 0) {
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        }
        if (task.equals("Update")) {
//            System.err.println("");
//            Profile key = new Profile();
//            key.setKey_person_id(logged_key_person_id);
//            key.setKey_person_name(logged_user_name);
//            key.setEmail_id1(map.get("email").trim());
//            key.setLandline_no1((map.get("landline")));
//            key.setMobile_no1(map.get("mobile1").trim());
//            key.setMobile_no2(map.get("mobile2").trim());
//            key.setGst_number(map.get("gst").trim());
//            key.setCity_name(map.get("city").trim());
////            key.setState_name(map.get("state").trim());
//            key.setAddress_line1(map.get("address_line1").trim());
//            key.setAddress_line2(map.get("address_line2").trim());
//            key.setAddress_line3(map.get("address_line3").trim());
//            key.setImage_name(map.get("image").trim());
//            String photo_destination = model.getDestination_Path("key_person_photo");
//            response.setContentType("image/jpeg");
//            model.updateRecord(key, itr, logged_key_person_id, logged_org_office_id, photo_destination);

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
            orgOffice.setOrg_office_name(logged_org_office);
            orgOffice.setOffice_type("Dealer Office");
            orgOffice.setOffice_type_id(3);
            orgOffice.setOrg_office_code(logged_org_office);
            orgOffice.setAddress_line1(map.get("address_line1").trim());
            orgOffice.setAddress_line2(map.get("address_line2").trim());
            orgOffice.setAddress_line3(map.get("address_line3").trim());
            orgOffice.setCity_id(model.getCity_id((map.get("city").trim())));
            orgOffice.setEmail_id1(map.get("email").trim());
            orgOffice.setEmail_id2("");
            orgOffice.setMobile_no1(map.get("mobile1").trim());
            orgOffice.setMobile_no2("");
            orgOffice.setLandline_no1(map.get("landline").trim());
            orgOffice.setLandline_no2("");
            orgOffice.setLandline_no3("");
            orgOffice.setGst_number(map.get("gst").trim());
            orgOffice.setLatitude(map.get("latitude").trim());
            orgOffice.setLongitude(map.get("longitude").trim());

            String superp = "";
            String p_org_office = "";
            orgOffice.setSuperp(superp);
            orgOffice.setP_org(p_org_office);

            if (org_office_id != 0) {
                try {
                    model.updateRecordForOrgOffice(orgOffice, org_office_id);
                } catch (SQLException ex) {
                    Logger.getLogger(DealersController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            OrganisationDesignationBean orgOfficebean = new OrganisationDesignationBean();
            orgOfficebean.setId(org_office_designation_map_id);
            orgOfficebean.setOrganisation(logged_org_office);
            orgOfficebean.setDesignation("Owner");
            if (org_office_designation_map_id != 0) {
                try {
                    model.updatemapOrganisationDesignation(orgOfficebean, org_office_designation_map_id, logged_org_office_id);
                } catch (SQLException ex) {
                    Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            Profile key = new Profile();
            key.setKey_person_id(logged_key_person_id);
            key.setSalutation("Mr.");
            key.setKey_person_name(logged_user_name);
            key.setFather_name("");
            key.setDate_of_birth((map.get("date_of_birth").trim()));
            key.setDesignation("Owner");
            key.setDesignation_id(8);
            key.setId_type(map.get("id_type"));
            key.setId_type_d(model.getIdtype_id(map.get("id_type")));
            key.setId_no(map.get("id_no").trim());
            key.setOrg_office_id(model.getOrgOffice_id(logged_org_office, logged_org_office));

            key.setOrg_office_des_map_id(model.getOrgOfficeDesignationMapId(8, model.getOrgOffice_id(logged_org_office, logged_org_office)));

            key.setCity_id(model.getCity_id((map.get("city").trim())));
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
            key.setMobile_no1(map.get("mobile2").trim());
            key.setMobile_no2("");
            key.setLandline_no1(map.get("landline").trim());
            key.setLandline_no2("");
            key.setEmail_id1(map.get("email").trim());
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

            if (key_person_id != 0) {
                try {
                    int kp_id = model.updateKeyPersonRecord(key, itr, key_person_id, photo_destination, iD_destination);
                } catch (SQLException ex) {
                    Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        List<Profile> list = model.getAllDetails(logged_user_name, logged_org_office);
        String email = "";
        String landline = "";
        String mobile1 = "";
        String mobile2 = "";
        String gst = "";
        String city = "";
        String address_line1 = "";
        String address_line2 = "";
        String address_line3 = "";
        String blood = "";
        String date_of_birth = "";
        String id_no = "";
        String id_type = "";
        String gender = "";
        String latitude = "";
        String longitude = "";
        String image_path = "";
        String image_name = "";

        if (list.get(0).getEmail_id1() != null) {
            email = list.get(0).getEmail_id1().toString();
        }
        if (list.get(0).getLandline_no1() != null) {
            landline = list.get(0).getLandline_no1().toString();
        }
        if (list.get(0).getMobile_no1() != null) {
            mobile1 = list.get(0).getMobile_no1().toString();
        }
        if (list.get(0).getMobile_no2() != null) {
            mobile2 = list.get(0).getMobile_no2().toString();
        }
        if (list.get(0).getGst_number() != null) {
            gst = list.get(0).getGst_number().toString();
        }

        if (list.get(0).getCity_name() != null) {
            city = list.get(0).getCity_name().toString();
        }

        if (list.get(0).getAddress_line1() != null) {
            address_line1 = list.get(0).getAddress_line1().toString();
        }
        if (list.get(0).getAddress_line2() != null) {
            address_line2 = list.get(0).getAddress_line2().toString();
        }
        if (list.get(0).getAddress_line3() != null) {
            address_line3 = list.get(0).getAddress_line3().toString();
        }

        if (list.get(0).getBlood() != null) {
            blood = list.get(0).getBlood().toString();
        }

        if (list.get(0).getDate_of_birth() != null) {
            date_of_birth = list.get(0).getDate_of_birth().toString();
        }

        if (list.get(0).getId_no() != null) {
            id_no = list.get(0).getId_no().toString();
        }

        if (list.get(0).getId_type() != null) {
            id_type = list.get(0).getId_type().toString();
        }
        if (list.get(0).getGender() != null) {
            gender = list.get(0).getGender().toString();
        }
        if (list.get(0).getLatitude() != null) {
            latitude = list.get(0).getLatitude().toString();
        }
        if (list.get(0).getLongitude() != null) {
            longitude = list.get(0).getLongitude().toString();
        }
        if (list.get(0).getImage_path() != null) {
            image_path = list.get(0).getImage_path().toString();
        }
        if (list.get(0).getImage_name() != null) {
            image_name = list.get(0).getImage_name().toString();
        }

        request.setAttribute("logged_user_name", logged_user_name);
        request.setAttribute("logged_org_office", logged_org_office);
        request.setAttribute("email", email);
        request.setAttribute("landline", landline);
        request.setAttribute("mobile1", mobile1);
        request.setAttribute("mobile2", mobile2);
        request.setAttribute("gst", gst);
        request.setAttribute("city", city);
        request.setAttribute("address_line1", address_line1);
        request.setAttribute("address_line2", address_line2);
        request.setAttribute("address_line3", address_line3);
        request.setAttribute("blood", blood);
        request.setAttribute("date_of_birth", date_of_birth);
        request.setAttribute("id_no", id_no);
        request.setAttribute("id_type", id_type);
        request.setAttribute("gender", gender);
        request.setAttribute("latitude", latitude);
        request.setAttribute("longitude", longitude);
        request.setAttribute("image_path", image_path);
        request.setAttribute("image_name", image_name);

        request.setAttribute("message", model.getMessage());
        request.setAttribute("msgBgColor", model.getMessageBGColor());
        DBConnection.closeConncetion(model.getConnection());

        request.getRequestDispatcher("profile").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
