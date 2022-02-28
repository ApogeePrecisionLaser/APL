package com.location.controller;

import com.DBConnection.DBConnection;
import com.location.model.DivisionModel;
import com.location.bean.DivisionBean;
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

public class DivisionController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        String message = null;
        String msgbgColor = null;
        int divisionId;
        String divisionName;
        System.out.println("Starting application");
        response.setContentType("text/html");
        ServletContext ctx = getServletContext();
        DivisionModel divisionModel = new DivisionModel();
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("logged_user_name") == null || !session.getAttribute("user_role").equals("Super Admin")) {
            request.getRequestDispatcher("/").forward(request, response);
            return;
        }
        try {
            //       organisationNameModel.setConnection(DBConnection.getConnection(ctx, session));
            divisionModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in CityController setConnection() calling try block" + e);
        }

        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        System.out.println(divisionModel.getConnection());
        String task = request.getParameter("task");

        String searchDivision = request.getParameter("searchDivision");

        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input           
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getDivision")) {
                    list = divisionModel.getDivision(q);
                } else if (JQstring.equals("getZone")) {
                    list = divisionModel.getZone(q);
                } else if (JQstring.equals("getStateName")) {
                    //  String zoneName = request.getParameter("zoneName");
                    list = divisionModel.getStateName(q);
                } else if (JQstring.equals("getUtName")) {
                    String zoneName = request.getParameter("zoneName");
                    list = divisionModel.getUtName(q, zoneName);
                } else if (JQstring.equals("getDivName")) {
                    String stateName = request.getParameter("stateName");
                    String utName = request.getParameter("utName");
                    list = divisionModel.getDivName(q, stateName, utName);
                }
                JSONObject gson = new JSONObject();
                gson.put("list", list);
                out.println(gson);
                DBConnection.closeConncetion(divisionModel.getConnection());
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }

        if (searchDivision == null) {
            searchDivision = "";
        }
        try {
            divisionName = request.getParameter("divisionName").trim();
        } catch (Exception e) {
            divisionName = "";
        }
        if (task == null) {
            task = "";
        } else if (task.equals("generateMapReport")) {
            List listAll = null;
            String jrxmlFilePath;
            String search = request.getParameter("searchDivision");
            response.setContentType("application/pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            listAll = divisionModel.showAllData(search);
            jrxmlFilePath = ctx.getRealPath("/report/location/DivisionReport.jrxml");
            byte[] reportInbytes = divisionModel.generateMapReport(jrxmlFilePath, listAll);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            DBConnection.closeConncetion(divisionModel.getConnection());
            return;
        } else if (task.equals("generateXlsMapReport")) {
            String jrxmlFilePath;
            List listAll = null;
            String search = request.getParameter("searchDivision");
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition", "attachment; filename=Division.xls");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            jrxmlFilePath = ctx.getRealPath("/report/location/DivisionReport.jrxml");
            listAll = divisionModel.showAllData(search);
            ByteArrayOutputStream reportInbytes = divisionModel.generateDivisionXlsRecordList(jrxmlFilePath, listAll);
            response.setContentLength(reportInbytes.size());
            servletOutputStream.write(reportInbytes.toByteArray());
            servletOutputStream.flush();
            servletOutputStream.close();
            DBConnection.closeConncetion(divisionModel.getConnection());
            return;
        }
//        else if (task.equals("Search All Records"))
//             searchDivision="";
//        else if(task.equals("Save all records")||task.equals("Save As New")||task.equals("Save"))
//         {
//             if(task.equals("Save all records"))
//                divisionModel.insertRecord(request.getParameterValues("zoneName"),request.getParameterValues("stateName"),request.getParameterValues("utName"),request.getParameterValues("division_id"),request.getParameterValues("divisionName"),request.getParameterValues("divisionDescription"));
//             else if(task.equals("Save As New"))
//             {
//                 String division_id[]={"1"};
//                 divisionModel.insertRecord(request.getParameterValues("zoneName"),request.getParameterValues("stateName"),request.getParameterValues("utName"),division_id,request.getParameterValues("divisionName"),request.getParameterValues("divisionDescription"));
//             }else if(task.equals("Save"))
//                 divisionModel.updateRecord(request.getParameter("zoneName"),request.getParameter("stateName"),request.getParameter("utName"),request.getParameter("divisionId"),request.getParameter("divisionName"),request.getParameter("divisionDescription"));
//                 
//         }else if(task.equals("Delete"))
//             divisionModel.deleteRecord(Integer.parseInt(request.getParameter("divisionId")));
//        
        int state_id = 0;
        state_id = divisionModel.getStateId(request.getParameter("stateName"), request.getParameter("utName"));
        if (divisionName.equals("No Division")) {
            message = "You Could Not Delete or update this record. It is required for Project Operation...";
            msgbgColor = "red";
        }
        if (task.equals("Delete")) {
            divisionModel.deleteRecord(Integer.parseInt(request.getParameter("divisionId")));  // Pretty sure that media_id will be available.
        } else if (task.equals("Save") || task.equals("Save As New")) {

            try {
                divisionId = Integer.parseInt(request.getParameter("divisionId"));            // media_id may or may NOT be available i.e. it can be update or new record.
            } catch (Exception e) {
                divisionId = 0;
            }
            if (task.equals("Save AS New")) {
                // divisionId = 0;
            }
            DivisionBean media = new DivisionBean();

            media.setStateId((DivisionModel.getStateID(request.getParameter("stateName").trim())));
            media.setDivisionName(request.getParameter("divisionName"));

            media.setStateName(request.getParameter("stateName"));
            media.setUtName(request.getParameter("utName"));
            media.setDivisionDescription(request.getParameter("divisionDescription"));

            if (divisionId == 0) {
                // if media_id was not provided, that means insert new record.
                divisionModel.insertRecord(media);
            } else {
                // update existing record.
                divisionModel.updateRecord(media, divisionId);
            }
        }

        noOfRowsInTable = divisionModel.getTotalRowsInTable(searchDivision);

        try {
            lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
            noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
        } catch (Exception e) {
            lowerLimit = noOfRowsTraversed = 0;
        }

        if (task.equals("Next")); // lowerLimit already has value such that it shows forward records, so do nothing here.
        else if (task.equals("Previous")) {
            int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
            if (temp < 0) {
                noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
                lowerLimit = 0;
            } else {
                lowerLimit = temp;
            }
        } else if (task.equals("First")) {
            lowerLimit = 0;
        } else if (task.equals("Last")) {
            lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
            if (lowerLimit < 0) {
                lowerLimit = 0;
            }
        }

        if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New") || task.equals("Add All Records")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        }
        ArrayList<DivisionBean> list = divisionModel.getAllRecords(lowerLimit, noOfRowsToDisplay, searchDivision);
        lowerLimit = lowerLimit + list.size();
        noOfRowsTraversed = list.size();

        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
        request.setAttribute("searchDivision", searchDivision);
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("message", divisionModel.getMessage());
        request.setAttribute("messageBGColor", divisionModel.getMessageBGColor());
        request.setAttribute("divisionList", list);
        DBConnection.closeConncetion(divisionModel.getConnection());

        request.getRequestDispatcher("divisionView").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
