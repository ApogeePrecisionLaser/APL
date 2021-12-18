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
import com.organization.model.OrganisationDesignationModel;
import com.organization.model.OrganisationDesignationNewModel;
import com.organization.model.OrganisationTypeDesignationModel;
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

public class OrganisationTypeDesignationController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 15, noOfRowsInTable;
        ServletContext ctx = getServletContext();

        /*      HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_name") == null) {
            response.sendRedirect("beforelogin.jsp");
            return;
        }
        String role = (String) session.getAttribute("user_role");   */
        //((Integer)session.getAttribute("user_id")).intValue();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        OrganisationTypeDesignationModel organisationModel = new OrganisationTypeDesignationModel();
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
            String organisation = request.getParameter("searchDesignationCode");
            String designation = request.getParameter("searchDesignation");
            String getgeneration = request.getParameter("searchgeneration");
            String searchhierarchy = request.getParameter("searchhierarchy");
            //   String superr =request.getParameter("super");
            if (isOrgBasicStep != null && !isOrgBasicStep.isEmpty()) {
                isOrgBasicStep = isOrgBasicStep.trim();
            } else {
                isOrgBasicStep = "";
            }
            if (organisation != null && !organisation.isEmpty()) {
                organisation = organisation.trim();
            } else {
                organisation = "";
            }
            if (designation != null && !designation.isEmpty()) {
                designation = designation.trim();
            } else {
                designation = "";
            }
            if (getgeneration != null && !getgeneration.isEmpty()) {
                getgeneration = getgeneration.trim();
            } else {
                getgeneration = "";
            }
            if (searchhierarchy != null && !searchhierarchy.isEmpty()) {
                searchhierarchy = searchhierarchy.trim();
            } else {
                searchhierarchy = "";
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
                    } else if (JQstring.equals("getOfficeName")) {
                        list = organisationModel.getOrgOfficeNameSearch(q, request.getParameter("action2"));
                    }
                    if (JQstring.equals("getgeneration")) {
                        list = organisationModel.getGeneration(q);
                    } else if (JQstring.equals("getOrgOfficeType")) {
                        list = organisationModel.OrgOfficeType(q);
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
                    } else if (JQstring.equals("gethierarchy")) {
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
                organisationModel.deleteRecord(Integer.parseInt(request.getParameter("designation_id")));  // Pretty sure that org_office_id will be available.
            } else if (task.equals("Save") || task.equals("Save AS New")) {
                int org_office_id;
                try {
                    org_office_id = Integer.parseInt(request.getParameter("designation_id"));            // org_office_id may or may NOT be available i.e. it can be update or new record.
                } catch (Exception e) {
                    org_office_id = 0;
                }
                if (task.equals("Save AS New")) {
                    //  org_office_id = 0;
                }

                OrganisationDesignationBean orgOffice = new OrganisationDesignationBean();
                orgOffice.setId(org_office_id);
                orgOffice.setOrganisation((request.getParameter("designation_code").trim()));

                orgOffice.setDesignation(request.getParameter("designation").trim());

                orgOffice.setSerialnumber(request.getParameter("description").trim());
                String superr = request.getParameter("super");
                String p_designation = request.getParameter("p_designation");
                if (superr.equals(null) || p_designation.equals(null)) {
                    superr = "";
                    p_designation = "";
                }
                orgOffice.setSuperp(superr);
                orgOffice.setP_designation(p_designation);

                if (org_office_id == 0) {
                    // if org_office_id was not provided, that means insert new record.
                    organisationModel.insertRecord(orgOffice);
                } else {
                    // update existing record.
                    organisationModel.updateRecord(orgOffice, org_office_id);
                }
            }
            if (requester != null && requester.equals("PRINT")) {
                String jrxmlFilePath;
                response.setContentType("application/pdf");
                //response.addHeader("Content-Disposition", "attachment; filename=OrganizationOffice_report.pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                List listAll = organisationModel.showPDF(organisation, designation, getgeneration, active1, searchhierarchy);
                jrxmlFilePath = ctx.getRealPath("/report/orgtypedesignation.jrxml");
                byte[] reportInbytes = organisationModel.generateSiteList(jrxmlFilePath, listAll);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                organisationModel.closeConnection();
                return;
            } else if (requester != null && requester.equals("PrintExcel")) {
                String jrxmlFilePath;
                response.setContentType("application/vnd.ms-excel");
                response.addHeader("Content-Disposition", "attachment; filename=OrganizationOffice_report.xls");
                int organisation_id = 0;
                request.getParameter("organisation");
                if (request.getParameter("organisation") != null && !request.getParameter("organisation").isEmpty()) {
                    organisation_id = organisationModel.getOrganisation_id(request.getParameter("organisation").trim());
                }
                ServletOutputStream servletOutputStream = response.getOutputStream();
                jrxmlFilePath = ctx.getRealPath("/report/organization/orgOfficelist.jrxml");
                ByteArrayOutputStream reportInbytes = organisationModel.generateOrganisationOfficeExcelList(jrxmlFilePath, organisation_id);
                response.setContentLength(reportInbytes.size());
                servletOutputStream.write(reportInbytes.toByteArray());
                servletOutputStream.flush();
                servletOutputStream.close();
                organisationModel.closeConnection();
                return;
            } else if (requester != null && requester.equals("PRINTAddress")) {
                String jrxmlFilePath;
                response.setContentType("application/pdf");
                int org_office_id = Integer.parseInt(request.getParameter("org_office_id"));

                ServletOutputStream servletOutputStream = response.getOutputStream();
                jrxmlFilePath = ctx.getRealPath("/report/office_address.jrxml");
                byte[] reportInbytes = organisationModel.generateofficeAddressList(jrxmlFilePath, org_office_id);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                organisationModel.closeConnection();
                return;
            }

            try {
                lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
                noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
            } catch (Exception e) {
                lowerLimit = noOfRowsTraversed = 0;
            }
            String org_name = "";
            String office_code_search = "";
            String office_name_search = "";
            org_name = request.getParameter("org_name");
            office_code_search = request.getParameter("office_code_search");
            office_name_search = request.getParameter("office_name_search");
            organisation = request.getParameter("searchDesignationCode");
            designation = request.getParameter("searchDesignation");
            getgeneration = request.getParameter("searchgeneration");
            searchhierarchy = request.getParameter("searchhierarchy");
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
                if (organisation == null) {
                    organisation = "";
                }
                if (designation == null) {
                    designation = "";
                }
                if (getgeneration == null) {
                    getgeneration = "";
                }
                if (searchhierarchy == null) {
                    searchhierarchy = "";
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
                organisation = "";
                getgeneration = "";
                searchhierarchy = "";
            }

            String buttonAction = request.getParameter("buttonAction"); // Holds the name of any of the four buttons: First, Previous, Next, Delete.
            if (buttonAction == null) {
                buttonAction = "none";
            } else {
                org_name = request.getParameter("org_name");
                active = active1;

                if (active.equals("")) {
                    ac = "ALL RECORDS";
                } else if (active.equals("Y")) {
                    ac = "ACTIVE RECORDS";
                } else {
                    ac = "INACTIVE RECORDS";
                }
            }
            //    if (org_name != null && !org_name.isEmpty()) {
            noOfRowsInTable = organisationModel.getNoOfRows(organisation, office_code_search, getgeneration, active, searchhierarchy);
            //    } else {
            //        noOfRowsInTable = organisationModel.getNoOfRows();
            //    }// get the number of records (rows) in the table.
            if (buttonAction.equals("Next")); // lowerLimit already has value such that it shows forward records, so do nothing here.
            else if (buttonAction.equals("Previous")) {
                int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
                if (temp < 0) {
                    noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
                    lowerLimit = 0;
                } else {
                    lowerLimit = temp;
                }
            } else if (buttonAction.equals("First")) {
                lowerLimit = 0;
            } else if (buttonAction.equals("Last")) {
                lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
                if (lowerLimit < 0) {
                    lowerLimit = 0;
                }
            }

            if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New")) {
                lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
            }
            // Logic to show data in the table.
            List<OrganisationDesignationBean> organisationList = organisationModel.showData(lowerLimit, noOfRowsToDisplay, organisation, designation, getgeneration, active, searchhierarchy);
            lowerLimit = lowerLimit + organisationList.size();
            noOfRowsTraversed = organisationList.size();

            // Now set request scoped attributes, and then forward the request to view.
            // Following request scoped attributes NAME will remain constant from module to module.
            if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("message", organisationModel.getMessage());
            request.setAttribute("msgBgColor", organisationModel.getMsgBgColor());

            // Following request scoped attributes NAME will change from module to module.
            request.setAttribute("organisationList", organisationList);
            request.setAttribute("searchhierarchy", searchhierarchy);
            request.setAttribute("org_name", org_name);
            request.setAttribute("ac", ac);
            request.setAttribute("active", active);
            request.setAttribute("office_code_search", office_code_search);
            request.setAttribute("office_name_search", office_name_search);
            request.setAttribute("organisation_name", organisation_name);
            organisationModel.closeConnection();
            if (isOrgBasicStep.equals("Yes")) {
                if (task.equals("Save & Next")) {
                    response.sendRedirect("personCount.do?isOrgBasicStep=Yes&searchOrganisation=" + request.getParameter("organisation_name"));
                    return;
                } else {
                    request.setAttribute("stepNo", 3);
                }
                request.getRequestDispatcher("OrganisationTypeDesignation").forward(request, response);
            } else {
                request.getRequestDispatcher("OrganisationTypeDesignation").forward(request, response);
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
