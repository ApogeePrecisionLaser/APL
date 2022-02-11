
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.controller;

import com.DBConnection.DBConnection;
import com.inventory.model.InventoryBasicModel;
import com.inventory.model.ItemNameModel;
import com.inventory.tableClasses.InventoryBasic;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Komal
 */
public class InventoryBasicController extends HttpServlet {

    private File tmpDir;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext ctx = getServletContext();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        InventoryBasicModel model = new InventoryBasicModel();
        ItemNameModel model2 = new ItemNameModel();

        String loggedUser = "";
        String logged_user_name = "";
        String logged_designation = "";
        String logged_org_name = "";
        String logged_org_office = "";
        int logged_org_office_id = 0;
        int logged_org_name_id = 0;
        int logged_key_person_id = 0;

        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("logged_user_name") == null) {
            request.getRequestDispatcher("/").forward(request, response);
            return;
        } else {
            loggedUser = session.getAttribute("user_role").toString();
            logged_user_name = session.getAttribute("logged_user_name").toString();
            logged_org_name = session.getAttribute("logged_org_name").toString();
            logged_designation = session.getAttribute("logged_designation").toString();
            logged_org_office = session.getAttribute("logged_org_office").toString();
            logged_org_name_id = Integer.parseInt(session.getAttribute("logged_org_name_id").toString());
            logged_org_office_id = Integer.parseInt(session.getAttribute("logged_org_office_id").toString());
            logged_key_person_id = Integer.parseInt(session.getAttribute("logged_key_person_id").toString());
            //  office_admin = session.getAttribute("office_admin").toString();
        }

        String search_item_name = "";
        String search_org_office = "";
        String search_item_code = "";
        String search_manufacturer = "";
        String search_model = "";
        String search_key_person = "";
        String search_by_date = "";

        search_org_office = request.getParameter("search_org_office");
        search_item_code = request.getParameter("search_item_code");
        search_manufacturer = request.getParameter("search_manufacturer");
        search_model = request.getParameter("search_model");
        search_key_person = request.getParameter("search_key_person");
        search_by_date = request.getParameter("search_by_date");

        if (search_item_name == null) {
            search_item_name = "";
        }
        if (search_org_office == null) {
            search_org_office = "";
        }
        if (search_item_code == null) {
            search_item_code = "";
        }
        if (search_manufacturer == null) {
            search_manufacturer = "";
        }
        if (search_model == null) {
            search_model = "";
        }
        if (search_key_person == null) {
            search_key_person = "";
        }
        if (search_by_date == null) {
            search_by_date = "";
        }
        if (!search_item_code.equals("")) {
            String search_item_code_arr[] = search_item_code.split(" - ");
            search_item_name = search_item_code_arr[0];
            search_item_code = search_item_code_arr[1];
        }
        try {
            model.setConnection(DBConnection.getConnectionForUtf(ctx));
            model2.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in InventoryBasicController setConnection() calling try block" + e);
        }

        try {
            String requester = request.getParameter("requester");
            try {
                String JQstring = request.getParameter("action1");
                String q = request.getParameter("str");
                String str2 = request.getParameter("str2");
                String str3 = request.getParameter("str3");

                if (JQstring != null) {
                    PrintWriter out = response.getWriter();
                    List<String> list = null;
                    JSONObject json = null;

                    if (JQstring.equals("getManufacturer")) {
                        list = model.getManufacturer(q);
                    }

                    if (JQstring.equals("getItemCode")) {
                        String manufacturer = request.getParameter("manufacturer");
                        list = model.getItemCode(q, manufacturer, logged_user_name, loggedUser, logged_org_office);
                    }

                    if (JQstring.equals("getModelName")) {
                        String manufacturer_name = request.getParameter("manufacturer_name");
                        String item_code = request.getParameter("item_code");
                        list = model.getModelName(q, manufacturer_name, item_code);
                    }
                    if (JQstring.equals("getOrgOffice")) {
                        search_org_office = request.getParameter("search_org_office");
                        if (!loggedUser.equals("Super Admin")) {
                            search_org_office = logged_org_office;
                        }
                        list = model.getOrgOffice(q, search_org_office, logged_user_name, loggedUser);
                    }
                    if (JQstring.equals("getLeadTime")) {
                        String model_name = request.getParameter("model_name");
                        list = model.getLeadTime(model_name);
                    }

                    if (JQstring.equals("getKeyPerson")) {
//                        if (!loggedUser.equals("Super Admin")) {
//                            logged_user_name = logged_user_name;
//                        }
                        list = model.getKeyPerson(q, str2, logged_user_name, loggedUser);
                    }
                    if (json != null) {

                        out.println(json);
                    } else {
                        Iterator<String> iter = list.iterator();
                        JSONObject gson = new JSONObject();
                        gson.put("list", list);
                        out.println(gson);
                    }
                    DBConnection.closeConncetion(model.getConnection());
                    return;
                }
            } catch (Exception e) {
                System.out.println("\n Error --InventoryBasicController get JQuery Parameters Part-" + e);
            }

            String task = request.getParameter("task");
            if (task == null) {
                task = "";
            }
            if (task.equals("Delete")) {
                model.deleteRecord(Integer.parseInt(request.getParameter("inventory_basic_id")));
            } else if (task.equals("Save") || task.equals("Save AS New") || task.equals("Save & Next")) {
                int inventory_basic_id = 0;
                int inventory_id = 0;
                try {

                    inventory_basic_id = Integer.parseInt(request.getParameter("inventory_basic_id").trim());
                    inventory_id = Integer.parseInt(request.getParameter("inventory_id").trim());
                } catch (Exception e) {
                    inventory_basic_id = 0;
                    inventory_id = 0;
                }

                if (task.equals("Save AS New")) {
                    inventory_basic_id = 0;
                    inventory_id = 0;
                }

                InventoryBasic bean = new InventoryBasic();
                bean.setInventory_basic_id(inventory_basic_id);
                bean.setInventory_id(inventory_id);
                bean.setItem_code(request.getParameter("item_code").trim());
                bean.setOrg_office(request.getParameter("org_office").trim());
                bean.setKey_person(request.getParameter("key_person").trim());
                bean.setDescription(request.getParameter("description").trim());
                bean.setMin_quantity(Integer.parseInt(request.getParameter("min_quantity").trim()));
                bean.setDaily_req(Integer.parseInt(request.getParameter("daily_req").trim()));
                bean.setOpening_balance(request.getParameter("opening_balance").trim());
                bean.setDate_time(request.getParameter("date_time").trim());
                bean.setModel(request.getParameter("model_name"));
                bean.setStock_quantity(Integer.parseInt(request.getParameter("quantity").trim()));

                if (inventory_basic_id == 0) {
                    model.insertRecord(bean);

                    request.setAttribute("org_office", request.getParameter("org_office").trim());
                    request.setAttribute("manufacturer_name", request.getParameter("manufacturer_name").trim());
                    request.setAttribute("item_code", request.getParameter("item_code").trim());
                    request.setAttribute("model_name", request.getParameter("model_name").trim());
                    request.setAttribute("key_person", request.getParameter("key_person").trim());
                    request.setAttribute("quantity", request.getParameter("quantity").trim());
                    request.setAttribute("daily_req", request.getParameter("daily_req").trim());
                    request.setAttribute("min_quantity", request.getParameter("min_quantity").trim());
                    request.setAttribute("opening_balance", request.getParameter("opening_balance").trim());
                    request.setAttribute("date_time", request.getParameter("date_time").trim());
                    request.setAttribute("description", request.getParameter("description").trim());
                } else {
                    model.updateRecord(bean, inventory_basic_id, inventory_id);
                }
            } else if (task.equals("Map To Another Office")) {
                int inventory_basic_id = 0;
                int inventory_id = 0;
                try {
                    inventory_basic_id = Integer.parseInt(request.getParameter("inventory_basic_id").trim());
                    inventory_id = Integer.parseInt(request.getParameter("inventory_id").trim());
                } catch (Exception e) {
                    inventory_basic_id = 0;
                    inventory_id = 0;
                }

                if (task.equals("Save AS New")) {
                    inventory_basic_id = 0;
                    inventory_id = 0;
                }

                InventoryBasic bean = new InventoryBasic();
                String allCheckBoxes[] = request.getParameterValues("checkboxes");
                String org_office_map = request.getParameter("org_office").trim();
                String key_person_map = request.getParameter("key_person").trim();
                String search_org_office_old = request.getParameter("search_org_office_old").trim();
                for (int j = 0; j < allCheckBoxes.length; j++) {
                    String item_name_map = allCheckBoxes[j];
                    bean.setItem_name(item_name_map);
                    bean.setOrg_office(org_office_map);
                    bean.setKey_person(key_person_map);
                    model.insertMapRecord(bean, search_org_office_old);
                }

//                request.setAttribute("org_office", request.getParameter("org_office").trim());
//                request.setAttribute("manufacturer_name", request.getParameter("manufacturer_name").trim());
//                request.setAttribute("item_code", request.getParameter("item_code").trim());
//                request.setAttribute("model_name", request.getParameter("model_name").trim());
//                request.setAttribute("key_person", request.getParameter("key_person").trim());
//                request.setAttribute("quantity", request.getParameter("quantity").trim());
//                request.setAttribute("daily_req", request.getParameter("daily_req").trim());
//                request.setAttribute("min_quantity", request.getParameter("min_quantity").trim());
//                request.setAttribute("opening_balance", request.getParameter("opening_balance").trim());
//                request.setAttribute("date_time", request.getParameter("date_time").trim());
//                request.setAttribute("description", request.getParameter("description").trim());
            }

            if (!loggedUser.equals("Super Admin")) {
                search_key_person = logged_user_name;
            }

            List<InventoryBasic> list = model.showData(search_item_name, search_org_office, search_manufacturer, search_item_code, search_model,
                    search_key_person, search_by_date);
            request.setAttribute("list", list);
            if (!search_item_code.equals("")) {
                request.setAttribute("search_item_code", search_item_name + " - " + search_item_code);
            }
            if (!loggedUser.equals("Super Admin")) {
                search_org_office = logged_org_office;
                search_key_person = logged_user_name;
                request.setAttribute("org_office", logged_org_office);
                request.setAttribute("key_person", logged_user_name);

            }
            request.setAttribute("search_org_office", search_org_office);
            request.setAttribute("search_manufacturer", search_manufacturer);
            request.setAttribute("search_model", search_model);
            request.setAttribute("search_key_person", search_key_person);
            request.setAttribute("search_by_date", search_by_date);
            request.setAttribute("message", model.getMessage());
            request.setAttribute("msgBgColor", model.getMsgBgColor());
            request.setAttribute("loggedUser", loggedUser);

            DBConnection.closeConncetion(model.getConnection());
            DBConnection.closeConncetion(model2.getConnection());
            request.getRequestDispatcher("inventory_basic").forward(request, response);
        } catch (Exception ex) {
            System.out.println("InventoryBasicController error: " + ex);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
