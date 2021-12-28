/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apogee.admin;

import com.DBConnection.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;

/**
 *
 * @author Vikrant
 */
public class AttendanceController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext ctx = getServletContext();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        AttendanceModel model = new AttendanceModel();
        String task = request.getParameter("search_record");
        String key_person = request.getParameter("key_person");
        String date = request.getParameter("date");

        HttpSession session = request.getSession();
        String user_name = session.getAttribute("log_user").toString();
        System.err.println("logged user --" + user_name);

        if (task == null) {
            task = "";
        }
        if (key_person == null) {
            key_person = "";
        }
        if (date == null) {
            date = "";
        }
        
        

        if (!user_name.equals("sagar")) {

            try {
                model.setConnection(DBConnection.getConnectionForUtf(ctx));
            } catch (Exception e) {
                System.out.println("com.apogee.admin.AttendanceController.doGet() -" + e);
            }

        } else {
            model.setDriver(ctx.getInitParameter("driverClass"));
            model.setUrl(ctx.getInitParameter("connectionString"));
            model.setUser(ctx.getInitParameter("reader"));
            model.setPassword(ctx.getInitParameter("reader"));
            model.setConnection2();
        }
        
        

        try {
            try {
                String JQstring = request.getParameter("action1");
                String q = request.getParameter("str");   // field own input
                if (JQstring != null) {
                    PrintWriter out = response.getWriter();
                    List<String> list = null;
                    JSONObject json = null;
//                    if (JQstring.equals("getOrganisationName")) {
//                        String organisation_type = request.getParameter("action2");
//                        String organisation_sub_type = request.getParameter("action3");
//                        list = model.getOrganisationName(q, organisation_type, organisation_sub_type);
//                    }
                    if (JQstring.equals("getKeyPerson")) {
                        list = model.getKeyPerson(q);
                    }

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

            }

            List<AttendanceBean> list = model.showData(key_person, date);

            request.setAttribute("list", list);
            request.setAttribute("message", model.getMessage());
            request.setAttribute("msgBgColor", model.getMsgBgColor());
            model.closeConnection();

            request.getRequestDispatcher("view_attendance").forward(request, response);
        } catch (Exception ex) {

        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
