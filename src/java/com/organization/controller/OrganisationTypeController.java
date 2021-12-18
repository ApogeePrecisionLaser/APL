/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.organization.controller;

import com.organization.model.OrganisationTypeModel;
import com.DBConnection.DBConnection;
import com.organization.tableClasses.OrganisationType;
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
public class OrganisationTypeController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 20, noOfRowsInTable;
        ServletContext ctx = getServletContext();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        OrganisationTypeModel orgTypeModel = new OrganisationTypeModel();
        OrganisationType organisationType = new OrganisationType();
        String active = "Y";
        String ac = "ACTIVE RECORDS";
        try {
            orgTypeModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in OrganisationTypeController setConnection() calling try block" + e);
        }
        String requester = request.getParameter("requester");
        String active1 = request.getParameter("active");
        try {

            try {
                //----- This is only for Vendor key Person JQuery
                String JQstring = request.getParameter("action1");
                String q = request.getParameter("str");   // field own input
                if (JQstring != null) {
                    PrintWriter out = response.getWriter();
                    List<String> list = null;
                    if (JQstring.equals("getOrganisationType")) {
                        list = orgTypeModel.getOrgType(q);
                    }
                    if (JQstring.equals("getgeneration")) {
                        list = orgTypeModel.getGeneration(q);
                    }
                    if (JQstring.equals("getParentOrganisationType")) {
                        String organisation_type = request.getParameter("action2");
                        String supper = request.getParameter("action3");
                        String edit = request.getParameter("edit");
                        String generation = request.getParameter("generation");
                        list = orgTypeModel.getParentOrgType(q, organisation_type, supper, edit, generation);
                    }
//                    if (JQstring.equals("gethierarchysearch")) {
//                        list = orgTypeModel.getHierarchsearch(q);
//                    }
                    JSONObject gson = new JSONObject();
                    gson.put("list", list);
                    out.println(gson);

                    orgTypeModel.closeConnection();
                    return;
                }
            } catch (Exception e) {
                System.out.println("\n Error --OrganisationTypeController get JQuery Parameters Part-" + e);
            }
            String searchOrgType = request.getParameter("searchOrgType");
            String searchgeneration = request.getParameter("searchgeneration");
           // String searchhierarchy = request.getParameter("hierarchysearch");

            try {
                if (searchOrgType == null) {
                    searchOrgType = "";
                }
                if (searchgeneration == null) {
                    searchgeneration = "";
                }
//                if (searchhierarchy == null) {
//                    searchhierarchy = "";
//                }
            } catch (Exception e) {
            }

            String task = request.getParameter("task");
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
                orgTypeModel.deleteRecord(Integer.parseInt(request.getParameter("organisation_type_id")));  // Pretty sure that organisation_type_id will be available.
            } else if (task.equals("Save") || task.equals("Save AS New")) {
                int organisation_type_id;
                try {
                    organisation_type_id = Integer.parseInt(request.getParameter("organisation_type_id"));  // organisation_type_id may or may NOT be available i.e. it can be update or new record.

                } catch (Exception e) {
                    organisation_type_id = 0;
                }
                if (task.equals("Save AS New")) {
                    // organisation_type_id = 0;
                }

                organisationType.setOrganisation_type_id(organisation_type_id);

                organisationType.setOrg_type_name(request.getParameter("org_type_name").trim());

                organisationType.setDescription(request.getParameter("description"));

                String p_ot = (request.getParameter("p_ot").trim());
                String supper = request.getParameter("super").trim();
                if (supper.equals(null) || p_ot.equals(null)) {
                    supper = "";
                    p_ot = "";
                }
                organisationType.setP_ot((request.getParameter("p_ot").trim()));
//             organisationType.setP_ot_id(Integer.parseInt(orgTypeModel.getParentOrgTypeid((request.getParameter("p_ot").trim()))));
                organisationType.setSupper(request.getParameter("super").trim());
                if (organisation_type_id == 0) {
                    // if organisation_type_id was not provided, that means insert new record.
                    orgTypeModel.insertRecord(organisationType);
                } else {
                    // update existing record.
                    orgTypeModel.updateRecord(organisationType, organisation_type_id);
                }
            }   

            // Logic to show data in the table.
            
            List<OrganisationType> orgTypeList = orgTypeModel.showData(searchOrgType, searchgeneration);

            request.setAttribute("searchOrgType", searchOrgType);
            request.setAttribute("searchgeneration", searchgeneration);
           // request.setAttribute("hierarchysearch", searchhierarchy);
            request.setAttribute("orgTypeList", orgTypeList);
            request.setAttribute("message", orgTypeModel.getMessage());
            request.setAttribute("msgBgColor", orgTypeModel.getMsgBgColor());
            orgTypeModel.closeConnection();
            request.getRequestDispatcher("organization_type").forward(request, response);
        } catch (Exception ex) {
            System.out.println("OrganisationTypeController error: " + ex);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
