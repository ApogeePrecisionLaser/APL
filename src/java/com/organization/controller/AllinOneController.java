/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.organization.controller;

import com.organization.model.OrganisationNameModel;
import com.DBConnection.DBConnection;
import com.organization.tableClasses.OrganisationName;
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
 * @author Tarun
 */
public class AllinOneController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        ServletContext ctx = getServletContext();
        /*     HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_name") == null) {
            response.sendRedirect("beforelogin.jsp");
            return;
        }
        String role = (String) session.getAttribute("user_role");   */
        //((Integer)session.getAttribute("user_id")).intValue();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        OrganisationNameModel organisationNameModel = new OrganisationNameModel();
        String active = "Y";
        String ac = "ACTIVE RECORDS";

        try {
            //       organisationNameModel.setConnection(DBConnection.getConnection(ctx, session));
            organisationNameModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in OrganisationNameController setConnection() calling try block" + e);
        }
        try {
            String isOrgBasicStep = request.getParameter("isOrgBasicStep");
            String org_name = request.getParameter("org_name");
            String office_name = request.getParameter("office_name_search");
            String key_person = request.getParameter("searchKeyPerson");

            if (isOrgBasicStep != null && !isOrgBasicStep.isEmpty()) {
                isOrgBasicStep = isOrgBasicStep.trim();
            } else {
                isOrgBasicStep = "";
            }
            if (org_name != null && !org_name.isEmpty()) {
                org_name = org_name.trim();
            } else {
                org_name = "";
            }
            if (office_name != null && !office_name.isEmpty()) {
                office_name = office_name.trim();
            } else {
                office_name = "";
            }
            if (key_person != null && !key_person.isEmpty()) {
                key_person = key_person.trim();
            } else {
                key_person = "";
            }

            String requester = request.getParameter("requester");
            try {
                //----- This is only for Vendor key Person JQuery
                String JQstring = request.getParameter("action1");
                String q = request.getParameter("str");   // field own input
                if (JQstring != null) {
                    PrintWriter out = response.getWriter();
                    List<String> list = null;
                    if (JQstring.equals("getOrganisationName")) {
                        String organisation_type = request.getParameter("action2");
                        String organisation_sub_type = request.getParameter("action3");
                        list = organisationNameModel.getOrganisationName(q, organisation_type, organisation_sub_type);
                    }
                    if (JQstring.equals("getOrganisationTypeName")) {
                        list = organisationNameModel.getOrganisationTypeName(q);
                    }
                    if (JQstring.equals("searchOrganisationName")) {
                        list = organisationNameModel.getOrganisationName(q);
                    }
                    if (JQstring.equals("getOrganisationSubTypeName")) {
                        String organisation_type = request.getParameter("action2");
                        list = organisationNameModel.getOrganisationSubTypeName(q, organisation_type);
                    }
                    Iterator<String> iter = list.iterator();
                    JSONObject gson = new JSONObject();
                    gson.put("list", list);
                    out.println(gson);
                    organisationNameModel.closeConnection();
                    return;
                }
            } catch (Exception e) {
                System.out.println("\n Error --SiteListController get JQuery Parameters Part-" + e);
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
            String active1 = request.getParameter("active");

            if (task.equals("Delete")) {
                organisationNameModel.deleteRecord(Integer.parseInt(request.getParameter("organisation_id")));  // Pretty sure that tp_works_id will be available.
            } else if (task.equals("Save") || task.equals("Save AS New") || task.equals("Save & Next")) {
                int organisation_id;
                try {
                    // tp_works_id may or may NOT be available i.e. it can be update or new record.

                    organisation_id = Integer.parseInt(request.getParameter("organisation_id"));
                } catch (Exception e) {
                    organisation_id = 0;
                }
                if (task.equals("Save AS New")) {
                    // organisation_id = 0;
                }
                OrganisationName organisationName = new OrganisationName();
                organisationName.setOrganisation_id(organisation_id);
                organisationName.setOrganisation_code(request.getParameter("code"));
                organisationName.setOrganisation_type_id(organisationNameModel.getOrganisationTypeID(request.getParameter("organisation_type").trim()));

                //organisationName.setOrganisation_sub_type_id(organisationNameModel.getOrganisationSubTypeID(request.getParameter("organisation_sub_type_name").trim()));
                organisationName.setOrganisation_name(request.getParameter("organisation_name").trim());
                organisationName.setDescription(request.getParameter("description").trim());
                if (organisation_id == 0) {
                    // if tp_works_id was not provided, that means insert new record.
                    organisationNameModel.insertRecord(organisationName);
                } else {
                    // update existing record.
                    organisationNameModel.updateRecord(organisationName, organisation_id);
                }
            }
//            if (requester != null && requester.equals("PRINT")) {
//             
//                
//                String jrxmlFilePath="";
//                response.setContentType("application/pdf");
//                ServletOutputStream servletOutputStream = response.getOutputStream();
//               List  listAll=organisationNameModel.showAllinOnePDF(org_name, office_name,key_person);
//              
//                jrxmlFilePath = ctx.getRealPath("/report/allinoneoffice.jrxml");
//              
//                byte[] reportInbytes = organisationNameModel.generateSiteList(jrxmlFilePath,listAll);
//                response.setContentLength(reportInbytes.length);
//                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
//                servletOutputStream.flush();
//                servletOutputStream.close();
//                organisationNameModel.closeConnection();
//                return;
//            }

//           else if (requester != null && requester.equals("PRINTKp")) {
//             
//                
//                String jrxmlFilePath="";
//                response.setContentType("application/pdf");
//                ServletOutputStream servletOutputStream = response.getOutputStream();
//               List  listAll=organisationNameModel.showkpPDF(org_name, office_name,key_person);
//               
//               jrxmlFilePath = ctx.getRealPath("/report/allinonekeyperson.jrxml");
//               
//                byte[] reportInbytes = organisationNameModel.generateSiteList(jrxmlFilePath,listAll);
//                response.setContentLength(reportInbytes.length);
//                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
//                servletOutputStream.flush();
//                servletOutputStream.close();
//                organisationNameModel.closeConnection();
//                return;
//            }
//               else if (requester != null && requester.equals("viewPdfOrg")) {
//             
//                
//                String jrxmlFilePath="";
//                response.setContentType("application/pdf");
//                ServletOutputStream servletOutputStream = response.getOutputStream();
//               List  listAll=organisationNameModel.showOrgPDF(org_name, office_name,key_person);
//               
//               jrxmlFilePath = ctx.getRealPath("/report/allinoneorg.jrxml");
//               
//                byte[] reportInbytes = organisationNameModel.generateSiteList(jrxmlFilePath,listAll);
//                response.setContentLength(reportInbytes.length);
//                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
//                servletOutputStream.flush();
//                servletOutputStream.close();
//                organisationNameModel.closeConnection();
//                return;
//            }
//            
//            else if(requester!=null && requester.equals("PRINTXls"))
//            {        String jrxmlFilePath;
//                      List listAll=null;
//                       response.setContentType("application/vnd.ms-excel");
//                       response.addHeader("Content-Disposition", "attachment; filename=Orginisation_view_Report.xls");
//                       ServletOutputStream servletOutputStream = response.getOutputStream();
//                       jrxmlFilePath = ctx.getRealPath("/report/organization/organisationlist.jrxml");
//                       ByteArrayOutputStream reportInbytes =organisationNameModel.generateOrginisationXlsRecordList(jrxmlFilePath);
//                       response.setContentLength(reportInbytes.size());
//                       servletOutputStream.write(reportInbytes.toByteArray());
//                       servletOutputStream.flush();
//                       servletOutputStream.close();
//                       return;
//            }
            try {
                lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
                noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
            } catch (Exception e) {
                lowerLimit = noOfRowsTraversed = 0;
            }
            org_name = null;
            // sub_org_name=null;
            if (request.getParameter("search_org") != null) {
                if (request.getParameter("search_org").equals("SEARCH")) {
                    org_name = request.getParameter("org_name");
                }
            }
            if (request.getParameter("clear_org") != null) {
                if (request.getParameter("clear_org").equals("CLEAR")) {
                    org_name = request.getParameter("org_name");
                    org_name = org_name.trim();
                }
            }
            String buttonAction = request.getParameter("buttonAction"); // Holds the name of any of the four buttons: First, Previous, Next, Delete.
            if (buttonAction == null) {
                buttonAction = "none";
            } else {
                org_name = request.getParameter("org_name");
                //        sub_org_name=request.getParameter("organisation_sub_type_name");
                active = active1;
                if (active.equals("")) {
                    ac = "ALL RECORDS";
                } else if (active.equals("Y")) {
                    ac = "ACTIVE RECORDS";
                } else {
                    ac = "INACTIVE RECORDS";
                }
            }
            if (org_name != null && !org_name.isEmpty()) {
                noOfRowsInTable = organisationNameModel.getNoOfRows(org_name, active);
            } else {
                noOfRowsInTable = organisationNameModel.getNoOfRows(active);
            }// get the number of records (rows) in the table.
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
            //  List<OrganisationName> orgNameList = organisationNameModel.showData(lowerLimit, noOfRowsToDisplay, org_name,org_name,active);
//            lowerLimit = lowerLimit + orgNameList.size();
//            noOfRowsTraversed = orgNameList.size();
//
//            // Now set request scoped attributes, and then forward the request to view.
//            request.setAttribute("lowerLimit", lowerLimit);
//            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
//            request.setAttribute("orgNameList", orgNameList);

            if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }
            request.setAttribute("message", organisationNameModel.getMessage());
            request.setAttribute("org_name", org_name);
            request.setAttribute("msgBgColor", organisationNameModel.getMsgBgColor());
            request.setAttribute("active", active);
            request.setAttribute("ac", ac);
            organisationNameModel.closeConnection();
            if (isOrgBasicStep.equals("Yes")) {
                if (task.equals("Save & Next")) {
                    response.sendRedirect("mapOrgCont.do?isOrgBasicStep=Yes&organisation=" + request.getParameter("organisation_name"));
                    return;
                } else {
                    request.setAttribute("stepNo", 1);
                }

                request.getRequestDispatcher("orgBasicEntryView").forward(request, response);
            } else {
                request.getRequestDispatcher("allinone").forward(request, response);
            }
        } catch (Exception ex) {
            System.out.println("OrganisationNameController error: " + ex);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
