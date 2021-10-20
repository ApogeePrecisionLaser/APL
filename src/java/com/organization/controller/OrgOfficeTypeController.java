/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.organization.controller;

import com.organization.model.OrgOfficeTypeModel;
import com.DBConnection.DBConnection;
import com.organization.tableClasses.OrgOfficeType;
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
 * @author Komal
 */
public class OrgOfficeTypeController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext ctx = getServletContext();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        OrgOfficeTypeModel orgOfficeTypeModel = new OrgOfficeTypeModel();
        String active = "Y";
        String ac = "ACTIVE RECORDS";
        String active1 = request.getParameter("active");
        try {
            orgOfficeTypeModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in OrgOfficeTypeController setConnection() calling try block" + e);
        }
        try {
            String searchOrgOfficeType = "";
            String searchOrgOfficeCode = "";
            try {
                //----- This is only for Vendor key Person JQuery
                String JQstring = request.getParameter("action1");
                String q = request.getParameter("str");   // field own input
                if (JQstring != null) {
                    PrintWriter out = response.getWriter();
                    List<String> list = null;
                    if (JQstring.equals("getOrganisationOfficeType")) {
                        list = orgOfficeTypeModel.getOrgOfficeType(q);
                    }
                    JSONObject gson = new JSONObject();
                    gson.put("list", list);
                    out.println(gson);
                    orgOfficeTypeModel.closeConnection();
                    return;
                }
            } catch (Exception e) {
                System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
            }
            searchOrgOfficeType = request.getParameter("searchOrgOfficeType");
            try {
                if (searchOrgOfficeType == null) {
                    searchOrgOfficeType = "";
                }
            } catch (Exception e) {
            }
            String search = request.getParameter("searchOfficeType");
            if (search == null) {
                search = "";
            }

            String task = request.getParameter("task");
            if (task == null) {
                task = "";
            }


            if (task.equals("ACTIVE RECORDS")) {
                active = "Y";
                ac = "ACTIVE RECORDS";

            } else if (task.equals("INACTIVE RECORDS")) {
                active = "n";
                ac = "INACTIVE RECORDS";
            } else if (task.equals("ALL RECORDS")) {
                active = "";
                ac = "ALL RECORDS";
            }

            if (task.equals("Delete")) {
                orgOfficeTypeModel.deleteRecord(Integer.parseInt(request.getParameter("office_type_id")));  // Pretty sure that office_type_id will be available.
            } else if (task.equals("Save") || task.equals("Save AS New")) {
                int office_type_id;
                try {
                    // office_type_id may or may NOT be available i.e. it can be update or new record.
                    office_type_id = Integer.parseInt(request.getParameter("office_type_id"));
                } catch (Exception e) {
                    office_type_id = 0;
                }
                if (task.equals("Save AS New")) {
                    //office_type_id = 0;
                }
                OrgOfficeType orgOfficeType = new OrgOfficeType();
                orgOfficeType.setOffice_type_id(office_type_id);
                orgOfficeType.setOffice_type(request.getParameter("office_type").trim());
                orgOfficeType.setDescription(request.getParameter("description").trim());
                if (office_type_id == 0) {
                    // if office_type_id was not provided, that means insert new record.
                    orgOfficeTypeModel.insertRecord(orgOfficeType);
                } else {
                    // update existing record.
                    orgOfficeTypeModel.updateRecord(orgOfficeType, office_type_id);
                }
            }

            // Logic to show data in the table.
            List<OrgOfficeType> orgOfficeTypeList = orgOfficeTypeModel.showData(searchOrgOfficeType, active);
            request.setAttribute("orgOfficeTypeList", orgOfficeTypeList);
            request.setAttribute("searchOrgOfficeType", searchOrgOfficeType);
            request.setAttribute("message", orgOfficeTypeModel.getMessage());
            request.setAttribute("msgBgColor", orgOfficeTypeModel.getMsgBgColor());

            orgOfficeTypeModel.closeConnection();
            request.getRequestDispatcher("org_office_type").forward(request, response);
        } catch (Exception ex) {
            System.out.println("OrgOfficeTypeController error: " + ex);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
