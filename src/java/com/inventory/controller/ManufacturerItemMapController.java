/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.controller;

import com.DBConnection.DBConnection;
import com.inventory.model.ManufacturerItemMapModel;
import com.inventory.tableClasses.ManufacturerItemMap;
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

public class ManufacturerItemMapController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext ctx = getServletContext();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        ManufacturerItemMapModel model = new ManufacturerItemMapModel();
        String active = "Y";
        String ac = "ACTIVE RECORDS";

        try {
            model.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in ManufacturerItemMapController setConnection() calling try block" + e);
        }
        try {
            String searchManufacturer = request.getParameter("searchManufacturer");
            String searchItem = request.getParameter("searchItem");
            if (searchManufacturer != null && !searchManufacturer.isEmpty()) {
                searchManufacturer = searchManufacturer.trim();
            } else {
                searchManufacturer = "";
            }
            if (searchItem != null && !searchItem.isEmpty()) {
                searchItem = searchItem.trim();
            } else {
                searchItem = "";
            }

            String requester = request.getParameter("requester");
            try {
                String JQstring = request.getParameter("action1");
                String action2 = request.getParameter("action2");
                String q = request.getParameter("str");
                if (JQstring != null) {
                    PrintWriter out = response.getWriter();
                    List<String> list = null;
                    if (JQstring.equals("getManufacturer")) {
                        list = model.getManufacturer(q);
                    } else if (JQstring.equals("getItem")) {
                        list = model.getItem(q);
                    }
                    if (JQstring.equals("getModel")) {
                        String manufacturer_str = request.getParameter("str2");
                        String item_str = request.getParameter("str3");
                        list = model.getModel(q);
                    }
                    JSONObject gson = new JSONObject();
                    gson.put("list", list);
                    out.println(gson);
                    DBConnection.closeConncetion(model.getConnection());
                    return;
                }
            } catch (Exception e) {
                System.out.println("\n Error --ManufacturerItemMapController get JQuery Parameters Part-" + e);
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

            String active1 = request.getParameter("active");

            if (task.equals("Delete")) {
                model.deleteRecord(Integer.parseInt(request.getParameter("manufacturer_item_map_id")));  // Pretty sure that org_office_id will be available.
            } else if (task.equals("Save") || task.equals("Save AS New")) {
                int manufacturer_item_map_id;
                try {
                    manufacturer_item_map_id = Integer.parseInt(request.getParameter("manufacturer_item_map_id"));            // org_office_id may or may NOT be available i.e. it can be update or new record.
                } catch (Exception e) {
                    manufacturer_item_map_id = 0;
                }
                if (task.equals("Save AS New")) {
                    //  org_office_id = 0;
                }

                ManufacturerItemMap bean = new ManufacturerItemMap();
                bean.setManufacturer_id(manufacturer_item_map_id);
                bean.setManufacturer_name((request.getParameter("manufacturer_name").trim()));
                bean.setItem_name(request.getParameter("item_name").trim());
                bean.setDescription((request.getParameter("description").trim()));

                if (manufacturer_item_map_id == 0) {
                    model.insertRecord(bean);
                } else {
                    model.updateRecord(bean, manufacturer_item_map_id);
                }
            }
            if (task.equals("Show All Records")) {
                searchManufacturer = "";
                searchItem = "";
            }

            String buttonAction = request.getParameter("buttonAction");
            if (buttonAction == null) {
                buttonAction = "none";
            } else {
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

            List<ManufacturerItemMap> list = model.showData(searchManufacturer, searchItem);
            request.setAttribute("searchManufacturer", searchManufacturer);
            request.setAttribute("searchItem", searchItem);
            request.setAttribute("message", model.getMessage());
            request.setAttribute("msgBgColor", model.getMsgBgColor());
            request.setAttribute("list", list);
            DBConnection.closeConncetion(model.getConnection());
            request.getRequestDispatcher("manufacturer_item_map").forward(request, response);
        } catch (Exception ex) {
            System.out.println("ManufacturerItemMapController error: " + ex);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
