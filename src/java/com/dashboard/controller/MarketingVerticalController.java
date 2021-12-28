package com.dashboard.controller;

import com.DBConnection.DBConnection;
import com.dashboard.bean.Enquiry;
import com.dashboard.bean.Profile;
import com.dashboard.model.EnquiryModel;
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

public class MarketingVerticalController extends HttpServlet {

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
        EnquiryModel model = new EnquiryModel();

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
        if (task.equals("deleteMarketingVertical")) {
            int rowsAffected = 0;
            rowsAffected = model.deleteMarketingVerticals(Integer.parseInt(request.getParameter("marketing_vertical_id")));
            JSONObject json = null;

            PrintWriter out = response.getWriter();
            List<String> list = null;
            if (json != null) {
                out.println(json);
            } else {
                JSONObject gson = new JSONObject();
                if (rowsAffected > 0) {
                    String message = "Record Deleted Successfully!....";
                    gson.put("msg", message);
                } else {
                    String message = "";
                    gson.put("msg", message);
                }
                out.println(gson);
            }

            DBConnection.closeConncetion(model.getConnection());

            return;
        }
        if (task.equals("Submit")) {
            int marketing_vertical_id;
            try {
                // tp_works_id may or may NOT be available i.e. it can be update or new record.

                marketing_vertical_id = Integer.parseInt(request.getParameter("marketing_vertical_id"));
            } catch (Exception e) {
                marketing_vertical_id = 0;
            }

            Enquiry bean = new Enquiry();
            bean.setMarketing_vertical_id(marketing_vertical_id);
            bean.setMarketing_vertical_name(request.getParameter("marketing_vertical_name"));
            bean.setDescription(request.getParameter("description").trim());

            if (marketing_vertical_id == 0) {
                model.insertMarketingVerticals(bean);
            } else {
                model.updateMarketingVerticals(bean, marketing_vertical_id);
            }
        }

        List<Enquiry> list = model.getMarketingVerticals();
        request.setAttribute("logged_user_name", logged_user_name);
        request.setAttribute("logged_org_office", logged_org_office);
        request.setAttribute("list", list);
        request.setAttribute("message", model.getMessage());
        request.setAttribute("msgBgColor", model.getMessageBGColor());
        DBConnection.closeConncetion(model.getConnection());

        request.getRequestDispatcher("marketing_vertical").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
