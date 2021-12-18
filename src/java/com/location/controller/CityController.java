package com.location.controller;

import com.location.model.CityModel;
import com.location.bean.CityBean;
import com.DBConnection.DBConnection;
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

public class CityController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 15, noOfRowsInTable;

        System.out.println("Starting application");
        response.setContentType("text/html");
        ServletContext ctx = getServletContext();
        CityModel cityModel = new CityModel();

        try {
            //       organisationNameModel.setConnection(DBConnection.getConnection(ctx, session));
            cityModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in CityController setConnection() calling try block" + e);
        }

        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        System.out.println(cityModel.getConnection());
        String task = request.getParameter("task");

        String searchCity = request.getParameter("searchCity");

        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("str");   // field own input           
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getCity")) {
                    list = cityModel.getCity(q);
                }
                if (JQstring.equals("getTehsil")) {
                    list = cityModel.getTehsil(q);
                }
                JSONObject gson = new JSONObject();
                gson.put("list", list);
                out.println(gson);
                cityModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --CityController get JQuery Parameters Part-" + e);
        }

        if (searchCity == null) {
            searchCity = "";
        }

        if (task == null) {
            task = "";
        } else if (task.equals("Save all records") || task.equals("Save As New") || task.equals("Save")) {
            int city_id = 0;
            try {
                city_id = Integer.parseInt(request.getParameter("city_id").trim());
            } catch (Exception ex) {
                city_id = 0;
            }
            int pin_code = 0;
            int std_code = 0;
            String tehsilName = "";
            String cityName = "";
            String cityDescription = "";

            if ((request.getParameter("pin_code").equals(""))) {
                pin_code = Integer.parseInt("0" + request.getParameter("pin_code"));
            } else {
                pin_code = Integer.parseInt(request.getParameter("pin_code"));
            }
            if ((request.getParameter("std_code").equals(""))) {
                std_code = Integer.parseInt("0" + request.getParameter("std_code"));
            } else {
                std_code = Integer.parseInt(request.getParameter("std_code"));
            }
            cityDescription = request.getParameter("cityDescription");
            cityName = request.getParameter("cityName");
            tehsilName = request.getParameter("tehsil");
            // city_id = CityModel.getCityId(request.getParameter("cityName"));
            CityBean b = new CityBean();
            b.setCityId(city_id);
            b.setCityName(cityName);
            b.setTehsilName(tehsilName);
            b.setCityDescription(cityDescription);
            b.setPin_code(pin_code);
            b.setStd_code(std_code);
            if (task.equals("Save all records")) {
                cityModel.insertRecord(b);
            } else if (task.equals("Save As New")) {
                city_id = 0;
                cityModel.insertRecord(b);
            } else if (task.equals("Save")) {
                if (city_id == 0) {
                    cityModel.insertRecord(b);
                } else {
                    cityModel.updateRecord(b, city_id);
                }

            }
        } else if (task.equals("Delete")) {
            cityModel.deleteRecord(Integer.parseInt(request.getParameter("city_id")));
        }

        ArrayList<CityBean> list = cityModel.getAllRecords(searchCity);
        request.setAttribute("message", cityModel.getMessage());
        request.setAttribute("msgBgColor", cityModel.getMessageBGColor());
        request.setAttribute("cityList", list);
        cityModel.closeConnection();

        request.getRequestDispatcher("city_view").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
