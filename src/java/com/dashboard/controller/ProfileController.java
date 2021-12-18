package com.dashboard.controller;

import com.DBConnection.DBConnection;
import com.dashboard.bean.Profile;
import com.dashboard.model.ProfileModel;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
        if (task.equals("Update")) {
            System.err.println("");
            Profile key = new Profile();
            key.setKey_person_id(logged_key_person_id);
            key.setKey_person_name(logged_user_name);
            key.setEmail_id1(map.get("email").trim());
            key.setLandline_no1((map.get("landline")));
            key.setMobile_no1(map.get("mobile1").trim());
            key.setMobile_no2(map.get("mobile2").trim());
            key.setGst_number(map.get("gst").trim());
            key.setCity_name(map.get("city").trim());
//            key.setState_name(map.get("state").trim());
            key.setAddress_line1(map.get("address").trim());
            key.setImage_name(map.get("image").trim());
            String photo_destination = model.getDestination_Path("key_person_photo");
            response.setContentType("image/jpeg");
            model.updateRecord(key, itr, logged_key_person_id, logged_org_office_id, photo_destination);
        }


        List<Profile> list = model.getAllDetails(logged_user_name, logged_org_office);
        String email = list.get(0).getEmail_id1().toString();
        String landline = list.get(0).getLandline_no1().toString();
        String mobile1 = list.get(0).getMobile_no1().toString();
        String mobile2 = list.get(0).getMobile_no2().toString();
        String gst = list.get(0).getGst_number().toString();
        String city = list.get(0).getCity_name().toString();
        String address_line1 = list.get(0).getAddress_line1().toString();
        String address_line2 = list.get(0).getAddress_line2().toString();
        String address_line3 = list.get(0).getAddress_line3().toString();

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

        request.setAttribute("message", model.getMessage());
        request.setAttribute("msgBgColor", model.getMessageBGColor());
        model.closeConnection();
        request.getRequestDispatcher("profile").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
