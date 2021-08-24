/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.organization.controller;

import com.organization.model.OrgOfficeModel;
import com.DBConnection.DBConnection;
import com.organization.model.OrgOfficeDesignationMapModel;
import com.organization.model.OrganisationDesignationModel;
import com.organization.model.OrganisationDesignationNewModel;
import com.organization.tableClasses.Org_Office;
import com.organization.tableClasses.OrganisationDesignationBean;
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

public class OrgOfficeDesignationMapController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext ctx = getServletContext();
 
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        OrgOfficeDesignationMapModel organisationModel = new OrgOfficeDesignationMapModel();
        String active = "Y";
        String ac = "ACTIVE RECORDS";

        try {
            // organisationModel.setConnection(DBConnection.getConnection(ctx, session));
            organisationModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in OrgOfficeController setConnection() calling try block" + e);
        }
        try {
            String isOrgBasicStep = request.getParameter("isOrgBasicStep");
            String searchOrgOffice = request.getParameter("searchOrgOffice");
            String designation = request.getParameter("searchDesignation");
            if (isOrgBasicStep != null && !isOrgBasicStep.isEmpty()) {
                isOrgBasicStep = isOrgBasicStep.trim();
            } else {
                isOrgBasicStep = "";
            }
            if (searchOrgOffice != null && !searchOrgOffice.isEmpty()) {
                searchOrgOffice = searchOrgOffice.trim();
            } else {
                searchOrgOffice = "";
            }
            if (designation != null && !designation.isEmpty()) {
                designation = designation.trim();
            } else {
                designation = "";
            }
           

            String requester = request.getParameter("requester");
            try {
                //----- This is only for Vendor key Person JQuery
                String JQstring = request.getParameter("action1");
                String action2 = "";
                String q = request.getParameter("str");   // field own input
                if (JQstring != null) {
                    PrintWriter out = response.getWriter();
                    List<String> list = null;
                    if (JQstring.equals("getOrgTypeName")) {
                        list = organisationModel.getOrganisation_Name(q);
                    } else if (JQstring.equals("getOfficeCodeName")) {
                        list = organisationModel.getOrgOfficeCodeSearch(q);
                    }
                    if (JQstring.equals("getgeneration")) {
                        list = organisationModel.getGeneration(q);
                    } else if (JQstring.equals("getOfficeName")) {
                        list = organisationModel.getOrgOfficeNameSearch(q, request.getParameter("action2"));
                    } else if (JQstring.equals("getOrgOffice")) {
                        list = organisationModel.OrgOffice(q);
                    } else if (JQstring.equals("searchOrganisation")) {
                        list = organisationModel.searchOrganisation_Name(q);
                    } else if (JQstring.equals("searchDesignation")) {
                        String code = request.getParameter("action2");
                        list = organisationModel.searchDesignation(q, code);
                    } else if (JQstring.equals("getDesignation")) {
                        String code = request.getParameter("action2");
                        list = organisationModel.getStateName(q, code);
                    } else if (JQstring.equals("getParentDesignation")) {
                        String code = request.getParameter("action2");
                        String edit = request.getParameter("action3");
                        String generation = request.getParameter("action4");
                        String currdesig = request.getParameter("action5");
                        list = organisationModel.getParentdesignation(q, code, edit, generation, currdesig);
                    } else if (JQstring.equals("getCityName")) {
                        list = organisationModel.getCityName(q);//, request.getParameter("action2")
                    } else if (JQstring.equals("searchhierarchy")) {
                        list = organisationModel.getHierarchy(q);//, request.getParameter("action2")
                    }
                    JSONObject gson = new JSONObject();
                    gson.put("list", list);
                    out.println(gson);

                    organisationModel.closeConnection();
                    return;
                }
            } catch (Exception e) {
                System.out.println("\n Error --OrgOfficeController get JQuery Parameters Part-" + e);
            }

            String organisation_name = request.getParameter("designation_code");
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

            String active1 = request.getParameter("active");

            if (task.equals("Delete")) {
                organisationModel.deleteRecord(Integer.parseInt(request.getParameter("org_office_designation_map_id")));  // Pretty sure that org_office_id will be available.
            } else if (task.equals("Save") || task.equals("Save AS New")) {
                int org_office_designation_map_id;
                try {
                    org_office_designation_map_id = Integer.parseInt(request.getParameter("org_office_designation_map_id"));            // org_office_id may or may NOT be available i.e. it can be update or new record.
                } catch (Exception e) {
                    org_office_designation_map_id = 0;
                }
                if (task.equals("Save AS New")) {
                    //  org_office_id = 0;
                }

                OrganisationDesignationBean orgOffice = new OrganisationDesignationBean();
                orgOffice.setId(org_office_designation_map_id);
                orgOffice.setOrganisation((request.getParameter("org_office").trim()));
                orgOffice.setDesignation(request.getParameter("designation").trim());
                orgOffice.setSerialnumber(request.getParameter("description").trim());
                //String superr = request.getParameter("super");
               // String p_designation = request.getParameter("p_designation");
//                if (superr.equals(null) || p_designation.equals(null)) {
//                    superr = "";
//                    p_designation = "";
//                }
//                orgOffice.setSuperp(superr);
//                orgOffice.setP_designation(p_designation);

                if (org_office_designation_map_id == 0) {
                    // if org_office_id was not provided, that means insert new record.
                    organisationModel.insertRecord(orgOffice);
                } else {
                    // update existing record.
                    organisationModel.updateRecord(orgOffice, org_office_designation_map_id);
                }
            }
          
            String org_name = "";
            String office_code_search = "";
            String office_name_search = "";
            org_name = request.getParameter("org_name");
            office_code_search = request.getParameter("office_code_search");
            office_name_search = request.getParameter("office_name_search");
            searchOrgOffice = request.getParameter("searchOrgOffice");
            designation = request.getParameter("searchDesignation");
            try {

                if (org_name == null) {
                    org_name = "";
                }
                if (office_name_search == null) {
                    office_name_search = "";
                }
                if (office_code_search == null) {
                    office_code_search = "";
                }
                if (searchOrgOffice == null) {
                    searchOrgOffice = "";
                }
                if (designation == null) {
                    designation = "";
                }
               

            } catch (Exception e) {
            }
//        String org_name = "";
//        if (request.getParameter("search_org") != null) {
//            if (request.getParameter("search_org").equals("SEARCH")) {
//                org_name = request.getParameter("org_name");
//            }
//        }
//        if (request.getParameter("clear_org") != null) {
//            if (request.getParameter("clear_org").equals("CLEAR")) {
//                org_name = request.getParameter("org_name");
//                org_name = org_name.trim();
//            }
//        }
            if (task.equals("Show All Records")) {
                org_name = "";
                office_code_search = "";
                office_name_search = "";
                searchOrgOffice = "";
            }

            String buttonAction = request.getParameter("buttonAction"); // Holds the name of any of the four buttons: First, Previous, Next, Delete.
            if (buttonAction == null) {
                buttonAction = "none";
            } else {
                org_name = request.getParameter("org_name");
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
          
            // Logic to show data in the table.
            List<OrganisationDesignationBean> organisationList = organisationModel.showData(searchOrgOffice, designation);
       
            request.setAttribute("message", organisationModel.getMessage());
            request.setAttribute("msgBgColor", organisationModel.getMsgBgColor());

            request.setAttribute("organisationList", organisationList);
            organisationModel.closeConnection();
            if (isOrgBasicStep.equals("Yes")) {
                if (task.equals("Save & Next")) {
                    response.sendRedirect("personCount.do?isOrgBasicStep=Yes&searchOrganisation=" + request.getParameter("organisation_name"));
                    return;
                } else {
                    request.setAttribute("stepNo", 3);
                }
                request.getRequestDispatcher("orgOfficeDesignationMap").forward(request, response);
            } else {
                request.getRequestDispatcher("orgOfficeDesignationMap").forward(request, response);
            }
        } catch (Exception ex) {
            System.out.println("OrgOfficeController error: " + ex);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
