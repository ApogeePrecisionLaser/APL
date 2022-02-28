/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.location.controller;

import com.DBConnection.DBConnection;
import com.location.model.TehsilModel;
import com.location.bean.TehsilBean;
import com.location.model.CityModel;
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

/**
 *
 * @author DELL
 */
public class TehsilController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 15, noOfRowsInTable;

        System.out.println("Starting application");
        response.setContentType("text/html");
        ServletContext ctx = getServletContext();
        TehsilModel tehsilModel = new TehsilModel();
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("logged_user_name") == null || !session.getAttribute("user_role").equals("Super Admin")) {
            request.getRequestDispatcher("/").forward(request, response);
            return;
        }
        try {
            tehsilModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in CityController setConnection() calling try block" + e);
        }

        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        String task = request.getParameter("task");

        String searchTehsil = request.getParameter("searchTehsil");

        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input           
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getTehsil")) {
                    list = tehsilModel.getTehsil(q);
                }
                if (JQstring.equals("getDistrict")) {
                    list = tehsilModel.getDistrict(q);
                }
                JSONObject gson = new JSONObject();
                gson.put("list", list);
                out.println(gson);
                DBConnection.closeConncetion(tehsilModel.getConnection());
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }

        if (searchTehsil == null) {
            searchTehsil = "";
        }

        if (task == null) {
            task = "";
        } else if (task.equals("generateMapReport"))//start from here
        {
            List listAll = null;
            String jrxmlFilePath;
            String search = request.getParameter("searchTeshil");
            response.setContentType("application/pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            listAll = tehsilModel.showAllData(search);
            jrxmlFilePath = ctx.getRealPath("/report/location/TehsilReport.jrxml");
            byte[] reportInbytes = tehsilModel.generateMapReport(jrxmlFilePath, listAll);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            DBConnection.closeConncetion(tehsilModel.getConnection());
            return;
        } else if (task.equals("generateMapXlsReport")) {
            String jrxmlFilePath;
            List listAll = null;
            String search = request.getParameter("searchTehsil");
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition", "attachment; filename=tehsil.xls");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            jrxmlFilePath = ctx.getRealPath("/report/location/TehsilReport.jrxml");
            listAll = tehsilModel.showAllData(search);
            ByteArrayOutputStream reportInbytes = tehsilModel.generateTehsilXlsRecordList(jrxmlFilePath, listAll);
            response.setContentLength(reportInbytes.size());
            servletOutputStream.write(reportInbytes.toByteArray());
            servletOutputStream.flush();
            servletOutputStream.close();
            DBConnection.closeConncetion(tehsilModel.getConnection());
            return;
        } else if (task.equals("Search All Records")) {
            searchTehsil = "";
        } else if (task.equals("Save all records") || task.equals("Save As New") || task.equals("Save")) {
            int tehsil_id = 0;
            try {
                tehsil_id = Integer.parseInt(request.getParameter("tehsil_id"));
            } catch (Exception ex) {
                tehsil_id = 0;
            }

            String tehsilDescription = request.getParameter("tehsilDescription");
            int districtId = TehsilModel.getDistrictId(request.getParameter("district"));
            String tehsilName = request.getParameter("tehsilName");
            tehsil_id = TehsilModel.getTehsilId(request.getParameter("tehsilName"));
            String districtName = request.getParameter("district");
            TehsilBean b = new TehsilBean();
            b.setDistrictId(tehsil_id);
            b.setTehsilId(tehsil_id);
            b.setTehsilName(tehsilName);
            b.setDistrictId(districtId);
            b.setDistrictName(districtName);
            b.setTehsilDescription(tehsilDescription);

            if (task.equals("Save all records")) {
                tehsilModel.insertRecord(b);
            } else if (task.equals("Save As New")) {
                tehsil_id = 0;
                tehsilModel.insertRecord(b);
            } else if (task.equals("Save")) {
                if (tehsil_id == 0) {
                    tehsilModel.insertRecord(b);
                } else {
                    tehsilModel.updateRecord(b, tehsil_id);
                }
            }
        } else if (task.equals("Delete")) {
            tehsilModel.deleteRecord(Integer.parseInt(request.getParameter("tehsilId")));
        }

        noOfRowsInTable = tehsilModel.getTotalRowsInTable(searchTehsil);

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
        ArrayList<TehsilBean> list = tehsilModel.getAllRecords(lowerLimit, noOfRowsToDisplay, searchTehsil);
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
        request.setAttribute("searchTehsil", searchTehsil);
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("message", tehsilModel.getMessage());
        request.setAttribute("messageBGColor", tehsilModel.getMessageBGColor());
        request.setAttribute("tehsilList", list);
        DBConnection.closeConncetion(tehsilModel.getConnection());

        request.getRequestDispatcher("tehsilView").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
