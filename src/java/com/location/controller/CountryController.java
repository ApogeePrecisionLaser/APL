/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.location.controller;

import com.DBConnection.DBConnection;
import com.location.model.CountryModel;
import com.location.bean.CountryBean;
import com.util.UniqueIDGenerator;
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

/**
 *
 * @author DELL
 */
public class CountryController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 20, noOfRowsInTable;
        ServletContext ctx = getServletContext();
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("logged_user_name") == null) {
            request.getRequestDispatcher("/").forward(request, response);
            return;
        }
        /*      HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_name") == null) {
            response.sendRedirect("beforelogin.jsp");
            return;
        }  
        String role = (String) session.getAttribute("user_role");   */
        //((Integer)session.getAttribute("user_id")).intValue();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        CountryModel countryModel = new CountryModel();
        try {
            //   designationModel.setConnection(DBConnection.getConnection(ctx, session));
            countryModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in CountryController setConnection() calling try block" + e);
        }
        String message = null;
        String bgColor = null;
        String task = request.getParameter("task");
        String country_name = null;
        if (task == null) {
            task = "";
        }

        try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input

            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getCountryList")) {
                    list = countryModel.getCountryList(q);
                } else if (JQstring.equals("getSearchCountryDiscription")) {
                    list = countryModel.getCountryDiscription(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    if (data == null) {
                        out.print("");
                    } else {
                        out.println(data);
                    }
                }
                DBConnection.closeConncetion(countryModel.getConnection());

                return;
            }

            String searchCountryName = request.getParameter("searchCountryName");
            String searchCountryDiscription = request.getParameter("searchCountryDiscription");
            try {

                if (searchCountryName == null) {
                    searchCountryName = "";
                }
                if (searchCountryDiscription == null) {
                    searchCountryDiscription = "";
                }
            } catch (Exception e) {
                System.out.println("Throwing Nullpointer Exception!!!");
            }
            try {
                country_name = request.getParameter("country_name").trim();
            } catch (Exception e) {
                country_name = "";
            }
            if (task.equals("generateCountryReport"))//start from here
            {
                String jrxmlFilePath;
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                List listAll = countryModel.showAllData(searchCountryName, searchCountryDiscription);
                jrxmlFilePath = ctx.getRealPath("/report/organization/DesignationReport.jrxml");
                byte[] reportInbytes = countryModel.generateCountryReport(jrxmlFilePath, listAll);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                DBConnection.closeConncetion(countryModel.getConnection());
                return;
            } else if (task.equals("generateCountryXlsReport")) {
                String jrxmlFilePath;
                response.setContentType("application/vnd.ms-excel");
                response.addHeader("Content-Disposition", "attachment; filename=Country.xls");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                jrxmlFilePath = ctx.getRealPath("/report/organization/CountryReport.jrxml");
                List listAll = countryModel.showAllData(searchCountryName, searchCountryDiscription);
                ByteArrayOutputStream reportInbytes = countryModel.generateDesignationXlsRecordList(jrxmlFilePath, listAll);
                response.setContentLength(reportInbytes.size());
                servletOutputStream.write(reportInbytes.toByteArray());
                servletOutputStream.flush();
                servletOutputStream.close();
                DBConnection.closeConncetion(countryModel.getConnection());
                return;
            }

            if (country_name.equals("No Country")) {
                message = "You Could Not Delete or update this record. It is required for Project Operation...";
                bgColor = "red";
            } else {
                if (task == null) {
                    task = "";
                }
                if (task.equals("Delete")) {
                    countryModel.deleteRecord(Integer.parseInt(request.getParameter("country_id")));  // Pretty sure that media_id will be available.
                } else if (task.equals("Save") || task.equals("Save AS New")) {
                    int country_id;
                    try {
                        country_id = Integer.parseInt(request.getParameter("country_id"));            // media_id may or may NOT be available i.e. it can be update or new record.
                    } catch (Exception e) {
                        country_id = 0;
                    }
                    if (task.equals("Save AS New")) {
                        country_id = 0;
                    }
                    CountryBean media = new CountryBean();

                    media.setCountry_id(country_id);
                    media.setCountry_discription(request.getParameter("country_discription").trim());
                    media.setCountry_name(request.getParameter("country_name").trim());

                    if (country_id == 0) {
                        // if media_id was not provided, that means insert new record.
                        countryModel.insertRecord(media);
                    } else {
                        // update existing record.
                        countryModel.updateRecord(media);
                    }
                }
                message = countryModel.getMessage();
                bgColor = countryModel.getMsgBgColor();
            }

            try {
                lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
                noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
            } catch (Exception e) {
                lowerLimit = noOfRowsTraversed = 0;
            }
            String buttonAction = request.getParameter("buttonAction"); // Holds the name of any of the four buttons: First, Previous, Next, Delete.
            if (buttonAction == null) {
                buttonAction = "none";

            }

            // get the number of records (rows) in the table.
            if (task.equals("Search")) {
                lowerLimit = noOfRowsTraversed = 0;
            } else if (task.equals("Show All Records")) {
                searchCountryName = "";
                searchCountryDiscription = "";
            }
            noOfRowsInTable = countryModel.getNoOfRows(searchCountryName, searchCountryDiscription);
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

                // Logic to show data in the table.
            }
            List<CountryBean> mediaList = countryModel.showData(lowerLimit, noOfRowsToDisplay, searchCountryName, searchCountryDiscription);
            lowerLimit = lowerLimit + mediaList.size();
            noOfRowsTraversed = mediaList.size();

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
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            request.setAttribute("message", message);
            request.setAttribute("msgBgColor", bgColor);

            // Following request scoped attributes NAME will change from module to module.
            request.setAttribute("mediaList", mediaList);
            request.setAttribute("searchCountryName", searchCountryName);
            request.setAttribute("searchCountryDiscription", searchCountryDiscription);
            DBConnection.closeConncetion(countryModel.getConnection());
            request.getRequestDispatcher("country").forward(request, response);
        } catch (Exception ex) {
            System.out.println("CountryController error: " + ex);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
