/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.organization.controller;

import com.organization.model.DesignationModel;
import com.DBConnection.DBConnection;
import com.organization.tableClasses.Designation;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
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

/**
 *
 * @author komal
 */
public class DesignationController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext ctx = getServletContext();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        DesignationModel designationModel = new DesignationModel();
        String active = "Y";
        String ac = "ACTIVE RECORDS";
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("logged_user_name") == null) {
            request.getRequestDispatcher("/").forward(request, response);
            return;
        }
        try {
            designationModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in DesignationController setConnection() calling try block" + e);
        }
        String message = null;
        String bgColor = null;
        String task = request.getParameter("task");
        String designation = null;
        if (task == null) {
            task = "";
        }

        try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("str");   // field own input

            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getDesignationList")) {
                    String code = request.getParameter("action2");
                    list = designationModel.getDesignationList(q, code);
                } else if (JQstring.equals("getSearchDesignationCode")) {

                    list = designationModel.getDesignationCode(q);
                }
                JSONObject gson = new JSONObject();
                gson.put("list", list);
                out.println(gson);

                designationModel.closeConnection();
                return;
            }

            String active1 = request.getParameter("active");
            String searchDesignation = request.getParameter("searchDesignation");
            String searchDesignationCode = request.getParameter("searchDesignationCode");
            try {

                if (searchDesignation == null) {
                    searchDesignation = "";
                }
                if (searchDesignationCode == null) {
                    searchDesignationCode = "";
                }
            } catch (Exception e) {
                System.out.println("Throwing Nullpointer Exception!!!");
            }
            try {
                designation = request.getParameter("designation").trim();
            } catch (Exception e) {
                designation = "";
            }

            if (designation.equals("No Designation")) {
                message = "You Could Not Delete or update this record. It is required for Project Operation...";
                bgColor = "red";
            } else {
                if (task == null) {
                    task = "";
                }

                if (task.equals("ACTIVE RECORDS")) {
                    active = "Y";
                    ac = "ACTIVE RECORDS";
                } else if (task.equals("INACTIVE RECORDS")) {
                    active = "N";
                    ac = "INACTIVE RECORDS";
                } else if (task.equals("ALL RECORDS")) {
                    active = "";
                    ac = "ALL RECORDS";
                }

                if (task.equals("Delete")) {
                    designationModel.deleteRecord(Integer.parseInt(request.getParameter("designation_id")));  // Pretty sure that media_id will be available.
                } else if (task.equals("Save") || task.equals("Save AS New")) {
                    int designation_id;
                    try {
                        designation_id = Integer.parseInt(request.getParameter("designation_id"));            // media_id may or may NOT be available i.e. it can be update or new record.
                    } catch (Exception e) {
                        designation_id = 0;
                    }
                    if (task.equals("Save AS New")) {
                        // designation_id = 0;
                    }
                    Designation media = new Designation();

                    media.setDesignation_id(designation_id);
                    media.setDesignation_code(request.getParameter("designation_code").trim());
                    media.setDesignation(request.getParameter("designation").trim());
                    media.setDescription(request.getParameter("description").trim());
                    if (designation_id == 0) {
                        // if media_id was not provided, that means insert new record.
                        designationModel.insertRecord(media);
                    } else {
                        // update existing record.
                        designationModel.updateRecord(media, designation_id);
                    }
                }
                message = designationModel.getMessage();
                bgColor = designationModel.getMsgBgColor();
            }

            // Logic to show data in the table.
            List<Designation> mediaList = designationModel.showData(searchDesignation, searchDesignationCode, active);

            request.setAttribute("searchDesignationCode", searchDesignationCode);
            request.setAttribute("searchDesignation", searchDesignation);
            request.setAttribute("message", message);
            request.setAttribute("msgBgColor", bgColor);
            request.setAttribute("mediaList", mediaList);
            designationModel.closeConnection();
            request.getRequestDispatcher("designation").forward(request, response);
        } catch (Exception ex) {
            System.out.println("DesignationController error: " + ex);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
