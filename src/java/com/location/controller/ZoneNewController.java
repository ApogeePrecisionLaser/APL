/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.location.controller;

import com.DBConnection.DBConnection;
import com.location.model.ZoneNewModel;
import com.location.bean.ZoneNewBean;
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
import org.json.simple.JSONObject;

/**
 *
 * @author Administrator
 */
public class ZoneNewController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 15, noOfRowsInTable;

        System.out.println("Starting application");
        response.setContentType("text/html");
        ServletContext ctx = getServletContext();
        ZoneNewModel zoneModel = new ZoneNewModel();

        try {
            //       organisationNameModel.setConnection(DBConnection.getConnection(ctx, session));
            zoneModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in CityController setConnection() calling try block" + e);
        }

        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        System.out.println(zoneModel.getConnection());
        String task = request.getParameter("task");

        String searchZone = request.getParameter("searchZone");
        String searchZone_no = request.getParameter("searchZone_no");

        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getZone")) {
                    list = zoneModel.getZone(q);
                }
                if (JQstring.equals("getZone_no")) {
                    list = zoneModel.getZoneNo(q);
                }

                if (JQstring.equals("getCity")) {
                    list = zoneModel.getCity(q);
                }
                JSONObject gson = new JSONObject();
                gson.put("list", list);
                out.println(gson);
                DBConnection.closeConncetion(zoneModel.getConnection());
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }

        if (searchZone == null) {
            searchZone = "";
        }
        if (searchZone_no == null) {
            searchZone_no = "";
        }

        if (task == null) {
            task = "";
        }
        int zone_id;
        if (task.equals("Search All Records")) {
            searchZone = "";
            searchZone_no = "";
        } else if (task.equals("Save all records") || task.equals("Save As New") || task.equals("Save")) {

            if (task.equals("Save As New") || task.equals("Save")) {
                try {
                    zone_id = Integer.parseInt(request.getParameter("zoneId"));            // media_id may or may NOT be available i.e. it can be update or new record.
                } catch (Exception e) {
                    zone_id = 0;
                }
                if (task.equals("Save AS New")) {
                    // districtId = 0;
                }
                ZoneNewBean media = new ZoneNewBean();

                media.setZoneId(zone_id);
                media.setCityId((ZoneNewModel.getCityId(request.getParameter("cityName").trim())));
                media.setZoneName(request.getParameter("zoneName"));
                media.setZone_no(request.getParameter("zone_no"));
                media.setZoneDescription(request.getParameter("zoneDescription"));

                if (zone_id == 0) {
                    // if media_id was not provided, that means insert new record.
                    ZoneNewModel.insertNewRecord(media);
                } else {
                    // update existing record.
                    //  ZoneModel.updateRecord(media,districtId);
                    ZoneNewModel.updateRecord(media, zone_id);
                }

            }
        } else if (task.equals("Delete")) {
            zoneModel.deleteRecord(Integer.parseInt(request.getParameter("zoneId")));
        } else if (task.equals("generateMapReport")) {
            List listAll = null;
            String jrxmlFilePath;
            String search = request.getParameter("searchZone");
            response.setContentType("application/pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            listAll = zoneModel.showAllData(search);
            jrxmlFilePath = ctx.getRealPath("/report/location/ZoneNewReport.jrxml");
            byte[] reportInbytes = zoneModel.generateMapReport(jrxmlFilePath, listAll);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            DBConnection.closeConncetion(zoneModel.getConnection());
            return;
        } else if (task.equals("generateXlsMapReport")) {
            String jrxmlFilePath;
            List listAll = null;
            String search = request.getParameter("searchZone");
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition", "attachment; filename=Zone.xls");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            jrxmlFilePath = ctx.getRealPath("/report/location/ZoneNewReport.jrxml");
            listAll = zoneModel.showAllData(search);
            ByteArrayOutputStream reportInbytes = zoneModel.generateZoneXlsRecordList(jrxmlFilePath, listAll);
            response.setContentLength(reportInbytes.size());
            servletOutputStream.write(reportInbytes.toByteArray());
            servletOutputStream.flush();
            servletOutputStream.close();
            DBConnection.closeConncetion(zoneModel.getConnection());
            return;
        }
        noOfRowsInTable = zoneModel.getTotalRowsInTable(searchZone, searchZone_no);

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
        ArrayList<ZoneNewBean> list = zoneModel.getAllRecords(lowerLimit, noOfRowsToDisplay, searchZone, searchZone_no);
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
        request.setAttribute("searchZone", searchZone);
        request.setAttribute("searchZone_no", searchZone_no);
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("message", zoneModel.getMessage());
        request.setAttribute("messageBGColor", zoneModel.getMessageBGColor());
        request.setAttribute("zoneList", list);
        DBConnection.closeConncetion(zoneModel.getConnection());

        request.getRequestDispatcher("zoneNewView").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
