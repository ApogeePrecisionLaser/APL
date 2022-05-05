
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.controller;

import com.inventory.model.ItemNameModel;
import com.DBConnection.DBConnection;
import com.inventory.model.InventoryModel;
import com.inventory.tableClasses.Inventory;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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
 * @author Komal
 */
public class InventoryController extends HttpServlet {

    private File tmpDir;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext ctx = getServletContext();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        InventoryModel model = new InventoryModel();
        ItemNameModel model2 = new ItemNameModel();
        int counting = 100000;
        String autogenerate_order_no = "";

        String search_key_person = "";
        String search_item_name = "";
        String search_org_office = "";
        String search_item_code = "";
        String search_manufacturer = "";
        String search_model = "";
        String search_by_date = "";

        String loggedUser = "";
        String logged_user_name = "";
        String logged_designation = "";
        String logged_org_name = "";
        String logged_org_office = "";
        int logged_org_office_id = 0;
        int logged_org_name_id = 0;
        int logged_key_person_id = 0;

        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("logged_user_name") == null || (!session.getAttribute("user_role").equals("Super Admin")
                && !session.getAttribute("user_role").equals("Incharge"))) {
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

        // search_item_name = request.getParameter("search_item_name");
        search_org_office = request.getParameter("search_org_office");
        search_item_code = request.getParameter("search_item_code");
        search_manufacturer = request.getParameter("search_manufacturer");
        search_model = request.getParameter("search_model");
        search_key_person = request.getParameter("search_key_person");
        search_by_date = request.getParameter("search_by_date");

        if (search_key_person == null) {
            search_key_person = "";
        }
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
            System.out.println("error in InventoryController setConnection() calling try block" + e);
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
                        if (!loggedUser.equals("Super Admin")) {
                            search_org_office = logged_org_office;
                        }
                        list = model.getItemCode(q, manufacturer, search_org_office, logged_user_name, loggedUser);
                    }
                    if (JQstring.equals("getParameter")) {
                        String type = request.getParameter("type");
                        if (type.equals("Product")) {
                            list = model.getItem(q, "", search_org_office, logged_user_name, loggedUser);
                        } else if (type.equals("Vendor")) {
                            list = model.getAllVendorName(q);
                        }
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
                    if (JQstring.equals("getKeyPerson")) {
                        list = model.getKeyPerson(q, str2, logged_user_name, loggedUser);
                    }

                    if (JQstring.equals("getVendorName")) {
                        String item_name = request.getParameter("item_name");
                        list = model.getVendorName(item_name);
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
                System.out.println("\n Error --InventoryController get JQuery Parameters Part-" + e);
            }

            String task = request.getParameter("task");
            String task1 = request.getParameter("task1");
            if (task == null) {
                task = "";
            }
            if (task1 == null) {
                task1 = "";
            }
            if (task1.equals("viewImage")) {
                try {
                    String destinationPath = "";
                    String inventory_id = request.getParameter("inventory_id");
                    String type = request.getParameter("type");
                    if (inventory_id != null && !inventory_id.isEmpty()) {
                        destinationPath = model.getImagePath(inventory_id, type);
                        if (destinationPath.isEmpty()) {
                            destinationPath = "C:\\ssadvt_repository\\DeliveryChallanPdf\\no_image.png";
                        }
                    } else {
                        // System.out.println("Image Not Found");
                        destinationPath = "C:\\ssadvt_repository\\DeliveryChallanPdf\\no_image.png";
                    }
                    //destinationPath = keyModel.getImagePath(emp_code);

                    //System.err.println("destinationPath=-------------" + destinationPath);
                    File f = new File(destinationPath);
                    FileInputStream fis = null;
                    if (!f.exists()) {
                        destinationPath = "C:\\ssadvt_repository\\DeliveryChallanPdf\\no_image.png";
                        f = new File(destinationPath);
                    }
                    fis = new FileInputStream(f);
                    if (destinationPath.contains("pdf")) {
                        response.setContentType("pdf");
                    } else {
                        response.setContentType("image/jpeg");
                    }

                    //  response.addHeader("Content-Disposition", "attachment; filename=\"" + f.getName() + "\"");
                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
                    // BufferedImage bi=ImageIO.read(f);
                    response.setContentLength(fis.available());
                    ServletOutputStream os = response.getOutputStream();
                    BufferedOutputStream out = new BufferedOutputStream(os);
                    int ch = 0;
                    ;
                    while ((ch = bis.read()) != -1) {
                        out.write(ch);
                    }

                    bis.close();
                    fis.close();
                    out.close();
                    os.close();
                    response.flushBuffer();

                    model.closeConnection();
                    return;
                } catch (Exception e) {
                    System.out.println("InventoryController Demand Note Error :" + e);
                    return;
                }
            }

            if (task.equals("GetDetails")) {
                List<Inventory> list = null;

                int item_names_id = Integer.parseInt(request.getParameter("item_names_id").trim());
                list = model.getAllDetails(item_names_id, logged_key_person_id, loggedUser);

                request.setAttribute("list", list);
                request.getRequestDispatcher("itemAllDetails").forward(request, response);
                return;
            }
            if (task.equals("Delete")) {
                model.deleteRecord(Integer.parseInt(request.getParameter("inventory_id")));
            } else if (task.equals("Save") || task.equals("Save AS New") || task.equals("Save & Next")) {
                int inventory_id = 0;
                try {
                    inventory_id = Integer.parseInt(request.getParameter("inventory_id").trim());
                } catch (Exception e) {
                    inventory_id = 0;
                }

                if (task.equals("Save AS New")) {
                    inventory_id = 0;
                }

                Inventory bean = new Inventory();
                bean.setInventory_id(inventory_id);
                bean.setItem_code(request.getParameter("item_code").trim());
                bean.setOrg_office(request.getParameter("org_office").trim());
                bean.setKey_person(request.getParameter("key_person").trim());
                bean.setDescription(request.getParameter("description").trim());
                bean.setDate_time(request.getParameter("date_time").trim());
                bean.setReference_document_type("");
                bean.setReference_document_id("");

                if (inventory_id == 0) {
                    model.insertRecord(bean);
                } else {
                    model.updateRecord(bean, inventory_id);
                }
            }

            if (!loggedUser.equals("Super Admin")) {
                search_org_office = logged_org_office;
                search_key_person = logged_user_name;
                request.setAttribute("org_office", logged_org_office);
                request.setAttribute("key_person", logged_user_name);
            }
            counting = model.getCounting();
            autogenerate_order_no = "ORDER" + counting;

            List<Inventory> list = model.showData(search_item_name, search_org_office, search_manufacturer, search_item_code, search_model,
                    search_key_person, search_by_date, loggedUser);
            request.setAttribute("list", list);
            if (!search_item_code.equals("")) {
                request.setAttribute("search_item_code", search_item_name + " - " + search_item_code);
            }
            request.setAttribute("search_org_office", search_org_office);
            request.setAttribute("search_manufacturer", search_manufacturer);
            request.setAttribute("search_model", search_model);
            request.setAttribute("search_key_person", search_key_person);
            request.setAttribute("search_by_date", search_by_date);
            request.setAttribute("message", model.getMessage());
            request.setAttribute("msgBgColor", model.getMsgBgColor());
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("autogenerate_order_no", autogenerate_order_no);

            DBConnection.closeConncetion(model.getConnection());
            DBConnection.closeConncetion(model2.getConnection());

            request.getRequestDispatcher("inventory").forward(request, response);
        } catch (Exception ex) {
            System.out.println("InventoryController error: " + ex);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
