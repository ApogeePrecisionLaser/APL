package com.location.controller;

import com.DBConnection.DBConnection;
import com.location.model.StateutModel;
import com.location.bean.StateutBean;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StateutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 15, noOfRowsInTable;
        String message = null;
        String msgbgColor = null;
        System.out.println("Starting application");
        response.setContentType("text/html");
        ServletContext ctx = getServletContext();
        StateutModel stateutModel = new StateutModel();

        try {
            //       organisationNameModel.setConnection(DBConnection.getConnection(ctx, session));
            stateutModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in CityController setConnection() calling try block" + e);
        }

        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        System.out.println(stateutModel.getConnection());
        String task = request.getParameter("task");
        int stateutId;
        String searchStateut = request.getParameter("searchStateut");
        String stateName;

        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input           
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getStateut")) {
                    list = stateutModel.getStateut(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                DBConnection.closeConncetion(stateutModel.getConnection());
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }
        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action2");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getZoneName")) {
                    list = stateutModel.getZoneName(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                DBConnection.closeConncetion(stateutModel.getConnection());
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }
        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action3");
            String q = request.getParameter("q");   // field own input
            String zoneName = request.getParameter("zoneName");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getStateName")) {
                    list = stateutModel.getStateName(q, zoneName);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                DBConnection.closeConncetion(stateutModel.getConnection());
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }
        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action4");
            String q = request.getParameter("q");   // field own input
            String zoneName = request.getParameter("zoneName");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getUtName")) {
                    list = stateutModel.getUtName(q, zoneName);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                DBConnection.closeConncetion(stateutModel.getConnection());
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }

        if (searchStateut == null) {
            searchStateut = "";
        }
        if (task == null) {
            task = "";
        }
        try {
            stateName = request.getParameter("stateName").trim();
        } catch (Exception e) {
            stateName = "";
        }
//         else if(task.equals("Search All Records"))
//             searchStateut="";
//         else if (task.equals("Save all records") || task.equals("Save As New") || task.equals("Save") )
//         {
//             if(task.equals("Save all records"))
//                stateutModel.insertRecord(request.getParameterValues("countryName"),request.getParameterValues("zoneName"),request.getParameterValues("stateut_id"),request.getParameterValues("stateName"),request.getParameterValues("utName"),request.getParameterValues("stateutDescription"));
//             else if (task.equals("Save As New"))  
//             {
//                 String stateut_id[]={"1"};
//                 stateutModel.insertRecord(request.getParameterValues("countryName"),request.getParameterValues("zoneName"),stateut_id,request.getParameterValues("stateName"),request.getParameterValues("utName"),request.getParameterValues("stateutDescription"));
//             } if(task.equals("Save"))
//                   if(stateutId==0){
////                    String stateutId = request.getParameter("stateutId");
//                    stateutModel.insertRecord(request.getParameterValues("countryName"),request.getParameterValues("zoneName"),stateutId,request.getParameterValues("stateName"),request.getParameterValues("utName"),request.getParameterValues("stateutDescription"));
//                   }else{  
//                 stateutModel.updateRecord(request.getParameter("stateutId"),request.getParameter("countryName"),request.getParameter("zoneName"),request.getParameter("stateName"),request.getParameter("utName"),request.getParameter("stateutDescription"));
//                   }
//         }             
//         else if (task.equals("Delete"))
//             stateutModel.deleteRecord(Integer.parseInt(request.getParameter("stateutId")));      
        int country_id = 0;
        country_id = stateutModel.getCountryId(request.getParameter("countryName"));
        if (stateName.equals("No State")) {
            message = "You Could Not Delete or update this record. It is required for Project Operation...";
            msgbgColor = "red";
        } else if (task != null) {
            if (task.equals("Delete")) {
                stateutModel.deleteRecord(Integer.parseInt(request.getParameter("stateutId")));  // Pretty sure that media_id will be available.
            } else if (task.equals("Save") || task.equals("Save AS New")) {

                try {
                    stateutId = Integer.parseInt(request.getParameter("stateutId"));            // media_id may or may NOT be available i.e. it can be update or new record.
                } catch (Exception e) {
                    stateutId = 0;
                }
                if (task.equals("Save AS New")) {
                    stateutId = 0;
                }
                StateutBean media = new StateutBean();

                media.setCountry_id(country_id);

                media.setStateName(request.getParameter("stateName"));
                media.setUtName(request.getParameter("utName"));
                media.setStateutDescription(request.getParameter("stateutDescription"));

                if (stateutId == 0) {
                    // if media_id was not provided, that means insert new record.
                    stateutModel.insertRecord(media);
                } else {
                    // update existing record.
                    stateutModel.updateRecord(media, stateutId);
                }
            }
            message = stateutModel.getMessage();
            msgbgColor = stateutModel.getMessageBGColor();
        } else if (task.equals("generateMapReport")) {
            List listAll = null;
            String jrxmlFilePath;
            String search = request.getParameter("searchStateut");
            response.setContentType("application/pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            listAll = stateutModel.showAllData(search);
            jrxmlFilePath = ctx.getRealPath("/report/location/StateutReport.jrxml");
            byte[] reportInbytes = stateutModel.generateMapReport(jrxmlFilePath, listAll);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            return;
        } else if (task.equals("generateXlsapReport")) {
            String jrxmlFilePath;
            List listAll = null;
            String search = request.getParameter("searchStateut");
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition", "attachment; filename=StateUT_report.xls");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            jrxmlFilePath = ctx.getRealPath("/report/location/StateutReport.jrxml");
            listAll = stateutModel.showAllData(search);
            ByteArrayOutputStream reportInbytes = stateutModel.generateStateXlsRecordList(jrxmlFilePath, listAll);
            response.setContentLength(reportInbytes.size());
            servletOutputStream.write(reportInbytes.toByteArray());
            servletOutputStream.flush();
            servletOutputStream.close();
            return;
        }
        noOfRowsInTable = stateutModel.getTotalRowsInTable(searchStateut);

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
        ArrayList<StateutBean> list = stateutModel.getAllRecords(lowerLimit, noOfRowsToDisplay, searchStateut);
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
        request.setAttribute("searchStateut", searchStateut);
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("message", stateutModel.getMessage());
        request.setAttribute("messageBGColor", stateutModel.getMessageBGColor());
        request.setAttribute("stateutList", list);
        DBConnection.closeConncetion(stateutModel.getConnection());

        request.getRequestDispatcher("stateutView").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
