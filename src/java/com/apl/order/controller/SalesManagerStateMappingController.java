package com.apl.order.controller;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.organization.model.OrgOfficeModel;
import com.DBConnection.DBConnection;
import com.apl.order.model.DealerMapModel;
import com.inventory.tableClasses.ItemAuthorization;
import com.organization.tableClasses.Org_Office;
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

public class SalesManagerStateMappingController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext ctx = getServletContext();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        DealerMapModel model = new DealerMapModel();
        String active = "Y";
        String ac = "ACTIVE RECORDS";
        try {
            model.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in DealerSalemanMapController setConnection() calling try block" + e);
        }

        try {

            String requester = request.getParameter("requester");
            try {
                //----- This is only for Vendor key Person JQuery
                String JQstring = request.getParameter("action1");
                String action2 = "";
                String q = request.getParameter("str");   // field own input
                if (JQstring != null) {
                    PrintWriter out = response.getWriter();
                    List<String> list = null;
                    if (JQstring.equals("getState")) {
                        String org_name = request.getParameter("action2");
                        list = model.getState(q);
                    } else if (JQstring.equals("getSalesDealer")) {

                        list = model.getSalesDealer(q);
                    }
                    JSONObject gson = new JSONObject();
                    gson.put("list", list);
                    out.println(gson);

                    model.closeConnection();
                    return;
                }
            } catch (Exception e) {
                System.out.println("\n Error --DealerSalemanMapController get JQuery Parameters Part-" + e);
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
                model.deleteRecordForSSM(Integer.parseInt(request.getParameter("salesmanager_state_mapping_id")));  // Pretty sure that org_office_id will be available.
            } else if (task.equals("Save") || task.equals("Save AS New")) {
                int salesmanager_state_mapping_id;
                try {
                    salesmanager_state_mapping_id = Integer.parseInt("0" + request.getParameter("salesmanager_state_mapping_id"));            // org_office_id may or may NOT be available i.e. it can be update or new record.
                } catch (Exception e) {
                    salesmanager_state_mapping_id = 0;
                }
                if (task.equals("Save AS New")) {
                    //  org_office_id = 0;
                }
                int salesmankpid = model.getKp_id(request.getParameter("salesmanname"));
                int state_id = model.getStateId(request.getParameter("state"));
                String remark = request.getParameter("remark");

                if (salesmanager_state_mapping_id == 0) {
                    // if org_office_id was not provided, that means insert new record.

                    model.insertRecordForSSM(state_id, salesmankpid, remark);
                } else {
                    // update existing record.
                    model.updateRecordForSSM(state_id, salesmankpid, remark, salesmanager_state_mapping_id);
                }
            }

            String searchstate = "";
            String sales_search = "";

            searchstate = request.getParameter("searchstate");
            sales_search = request.getParameter("sales_search");

            try {

                if (searchstate == null) {
                    searchstate = "";
                }

                if (sales_search == null) {
                    sales_search = "";
                }

            } catch (Exception e) {
            }

            if (task.equals("Show All Records")) {
                searchstate = "";
                sales_search = "";

            }

            String buttonAction = request.getParameter("buttonAction"); // Holds the name of any of the four buttons: First, Previous, Next, Delete.
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
            List<Org_Office> list = model.showSalesManStateMapData(searchstate, sales_search);

            request.setAttribute("message", model.getMessage());
            request.setAttribute("msgBgColor", model.getMsgBgColor());
            request.setAttribute("list", list);
            request.setAttribute("searchstate", searchstate);
            request.setAttribute("sales_search", sales_search);
            model.closeConnection();
            request.getRequestDispatcher("salesmanager_state_mapping").forward(request, response);
        } catch (Exception ex) {
            System.out.println("DealerSalemanMapController error: " + ex);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
