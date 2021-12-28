package com.dashboard.controller;

import com.DBConnection.DBConnection;
import com.dashboard.bean.Profile;
import com.dashboard.model.ProfileModel;
import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;

public class EditProfileController extends HttpServlet {

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
        String task = request.getParameter("task");

        if (task == null) {
            task = "";
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
        DBConnection.closeConncetion(model.getConnection());

        request.getRequestDispatcher("edit_profile").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
