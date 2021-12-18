package com.apl.order.controller;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.organization.model.OrgOfficeModel;
import com.DBConnection.DBConnection;
import com.apl.order.model.DealerMapModel;
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

public class DealerSalemanMapController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext ctx = getServletContext();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        DealerMapModel organisationModel = new DealerMapModel();
        String active = "Y";
        String ac = "ACTIVE RECORDS";
        try {
            // organisationModel.setConnection(DBConnection.getConnection(ctx, session));
            organisationModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in DealerSalemanMapController setConnection() calling try block" + e);
        }

        try {
            String isOrgBasicStep = request.getParameter("isOrgBasicStep");
            String serial_no = request.getParameter("searchDesignationCode");
            String designation = request.getParameter("searchDesignation");
            String after_save_organisation = (request.getParameter("organisation_name"));
            String after_save_office_type = (request.getParameter("office_type"));

            String office_code = (request.getParameter("office_code_search"));
            String organisation_name = (request.getParameter("org_name"));
            String office_name = (request.getParameter("office_name_search"));
            String mobile = (request.getParameter("searchmobile"));
            String searchgeneration = request.getParameter("searchgeneration");
            //   String searchhierarchy = request.getParameter("searchhierarchy");

            if (isOrgBasicStep != null && !isOrgBasicStep.isEmpty()) {
                isOrgBasicStep = isOrgBasicStep.trim();
            } else {
                isOrgBasicStep = "";
            }
            if (serial_no != null && !serial_no.isEmpty()) {
                serial_no = serial_no.trim();
            } else {
                serial_no = "";
            }

            if (designation != null && !designation.isEmpty()) {
                designation = designation.trim();
            } else {
                designation = "";
            }

            if (office_code != null && !office_code.isEmpty()) {
                office_code = office_code.trim();
            } else {
                office_code = "";
            }

            if (organisation_name != null && !organisation_name.isEmpty()) {
                organisation_name = organisation_name.trim();
            } else {
                organisation_name = "";
            }

            if (office_name != null && !office_name.isEmpty()) {
                office_name = office_name.trim();
            } else {
                office_name = "";
            }

            if (mobile != null && !mobile.isEmpty()) {
                mobile = mobile.trim();
            } else {
                mobile = "";
            }
            if (searchgeneration != null && !searchgeneration.isEmpty()) {
                searchgeneration = searchgeneration.trim();
            } else {
                searchgeneration = "";
            }
//            if (searchhierarchy != null && !searchhierarchy.isEmpty()) {
//                searchhierarchy = searchhierarchy.trim();
//            } else {
//                searchhierarchy = "";
//            }

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
                        list = organisationModel.organisation_Name(q);
                    }
                    if (JQstring.equals("getMobile")) {
                        list = organisationModel.searchMobile(q);
                    } else if (JQstring.equals("mobile_number")) {
                        list = organisationModel.getMobilevalidty(q);//, request.getParameter("action2")
                    }
                    if (JQstring.equals("getsearchgeneration")) {
                        list = organisationModel.getGeneration(q);
                    }
                    if (JQstring.equals("searchOrgTypeName")) {
                        list = organisationModel.getOrganisation_Name(q);
                    } else if (JQstring.equals("getOfficeCodeName")) {
                        String org_name = request.getParameter("action2");
                        list = organisationModel.getOrgOfficeCodeSearch(q, org_name);
                    } else if (JQstring.equals("getDealer")) {
                        String org_name = request.getParameter("action2");
                        list = organisationModel.getDealer();
                    } else if (JQstring.equals("getSalesDealer")) {

                        list = organisationModel.getSalesDealer();
                    } else if (JQstring.equals("getParentOrgOffice")) {
                        String code = request.getParameter("action2");
                        String edit = request.getParameter("action3");
                        String generation = request.getParameter("action4");
                        String currdesig = request.getParameter("action5");
                        list = organisationModel.getParentOrgOffice(q, code, edit, generation, currdesig);
                    } else if (JQstring.equals("getOrgOfficeType")) {
                        list = organisationModel.OrgOfficeType(q);
                    }// else if (JQstring.equals("getStateName")) {
                    //  list = organisationModel.getStateName(q);
                    // }
                    else if (JQstring.equals("getCityName")) {
                        list = organisationModel.getCityName(q);//, request.getParameter("action2")
                    }

                    if (JQstring.equals("gethierarchysearch")) {
                        list = organisationModel.getHierarchsearch(q);
                    }
                    JSONObject gson = new JSONObject();
                    gson.put("list", list);
                    out.println(gson);

                    organisationModel.closeConnection();
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
                organisationModel.deleteRecord(Integer.parseInt(request.getParameter("org_office_id")));  // Pretty sure that org_office_id will be available.
            } else if (task.equals("Save") || task.equals("Save AS New")) {
                int org_office_id;
                try {
                    org_office_id = Integer.parseInt("0" + request.getParameter("org_office_id"));            // org_office_id may or may NOT be available i.e. it can be update or new record.
                } catch (Exception e) {
                    org_office_id = 0;
                }
                if (task.equals("Save AS New")) {
                    //  org_office_id = 0;
                }
                int salesmankpid = organisationModel.getKp_id(request.getParameter("salesmanname"));
                int dealerkpid = organisationModel.getKp_id(request.getParameter("dealername"));
                String remark = request.getParameter("remark");

                if (org_office_id == 0) {
                    // if org_office_id was not provided, that means insert new record.

                    organisationModel.insertRecord(dealerkpid, salesmankpid, remark);
                } else {
                    // update existing record.
                    organisationModel.updateRecordd(dealerkpid, salesmankpid, remark, org_office_id);
                }
            }

            if (task.equals("showMapWindow")) {

                String point_id = request.getParameter("org_office_id");
                String latitude = "";
                String longitude = "";
                String LatLong = organisationModel.getPointLatLong(point_id);
                // System.out.println(LatLong);
                String[] words = LatLong.split("\\,");
                for (int i = 0; i < words.length; i++) {
                    latitude = words[0];
                    longitude = words[1];
                    //  System.out.println(latitude + "  " + longitude);
                }
                request.setAttribute("longi", longitude);
                request.setAttribute("latti", latitude);
                //System.out.println(latti + "," + longi);
                request.getRequestDispatcher("openMapWindowView").forward(request, response);
                return;

            }

            String searchdealername = "";
            String sales_search = "";
            String office_name_search = "";
            searchdealername = request.getParameter("searchdealername");
            sales_search = request.getParameter("sales_search");
            office_name_search = request.getParameter("office_name_search");
            mobile = request.getParameter("searchmobile");
            serial_no = request.getParameter("searchDesignationCode");
            designation = request.getParameter("searchDesignation");
            searchgeneration = request.getParameter("searchgeneration");
            // searchhierarchy = request.getParameter("searchhierarchy");

            try {

                if (searchdealername == null) {
                    searchdealername = "";
                }
                if (office_name_search == null) {
                    office_name_search = "";
                }
                if (sales_search == null) {
                    sales_search = "";
                }
                if (serial_no == null) {
                    serial_no = "";
                }
                if (designation == null) {
                    designation = "";
                }
                if (mobile == null) {
                    mobile = "";
                }

                if (searchgeneration == null) {
                    searchgeneration = "";
                }
//                if (searchhierarchy == null) {
//                    searchhierarchy = "";
//                }

            } catch (Exception e) {
            }

            if (task.equals("Show All Records")) {
                searchdealername = "";
                sales_search = "";
                office_name_search = "";
                serial_no = "";
                designation = "";
                searchgeneration = "";
                mobile = "";
                // searchhierarchy = "";
            }

            String buttonAction = request.getParameter("buttonAction"); // Holds the name of any of the four buttons: First, Previous, Next, Delete.
            if (buttonAction == null) {
                buttonAction = "none";
            } else {
                //org_name = request.getParameter("org_name");
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

            // List<Org_Office> organisationList1 = organisationModel.showDataa(org_name, office_code_search, office_name_search, mobile, searchgeneration, active, searchhierarchy);
            // Logic to show data in the table.
            List<Org_Office> organisationList = organisationModel.showData(searchdealername, sales_search, office_name_search, mobile, searchgeneration, active);

            request.setAttribute("message", organisationModel.getMessage());
            request.setAttribute("msgBgColor", organisationModel.getMsgBgColor());
            request.setAttribute("organisationList", organisationList);
            request.setAttribute("searchdealername", searchdealername);
            request.setAttribute("sales_search", sales_search);
            organisationModel.closeConnection();
            request.getRequestDispatcher("dealersalesmap").forward(request, response);
        } catch (Exception ex) {
            System.out.println("DealerSalemanMapController error: " + ex);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
