/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.controller;

import com.organization.model.OrganisationNameModel;
import com.DBConnection.DBConnection;
import com.inventory.model.ItemNameModel;
import com.inventory.tableClasses.ItemName;
import com.organization.tableClasses.OrganisationName;
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
import org.json.simple.JSONObject;

/**
 *
 * @author Vikrant
 */
public class ItemNameController extends HttpServlet {

    private File tmpDir;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        ServletContext ctx = getServletContext();
        Map<String, String> map = new HashMap<String, String>();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        ItemNameModel model = new ItemNameModel();
        String active = "Y";
        String ac = "ACTIVE RECORDS";
        String item_name = "";
        String item_type = "";
        String item_code = "";
        int quantity = 0;
        String auto_item_code = "";
        int auto_increment_key = 100;
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        auto_item_code = "APL_ITEM_" + number;

        String search_item_name = "";
        String search_item_type = "";
        String search_item_code = "";

        search_item_name = request.getParameter("search_item_name");
        search_item_type = request.getParameter("search_item_type");
        search_item_code = request.getParameter("search_item_code");

        if (search_item_name == null) {
            search_item_name = "";
        }
        if (search_item_type == null) {
            search_item_type = "";
        }
        if (search_item_code == null) {
            search_item_code = "";
        }
        try {
            //       organisationNameModel.setConnection(DBConnection.getConnection(ctx, session));
            model.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in ItemNameController setConnection() calling try block" + e);
        }
        try {
            String requester = request.getParameter("requester");
            try {
                //----- This is only for Vendor key Person JQuery
                String JQstring = request.getParameter("action1");
                String q = request.getParameter("str");   // field own input
                String str2 = request.getParameter("str2");
                String str3 = request.getParameter("str3");
                if (JQstring != null) {
                    PrintWriter out = response.getWriter();
                    List<String> list = null;
                    JSONObject json = null;
                    if (JQstring.equals("getItemType")) {

                        list = model.getItemType(q);
                    }
                    if (JQstring.equals("getItemName")) {
                        list = model.getItemName(q, str2);
                    }
                    if (JQstring.equals("getItemCode")) {
                        list = model.getItemCode(q, str2, str3);
                    }

                    if (json != null) {

                        out.println(json);
                    } else {
                        Iterator<String> iter = list.iterator();
                        JSONObject gson = new JSONObject();
                        gson.put("list", list);
                        out.println(gson);
                    }
                    return;
                }
            } catch (Exception e) {
                System.out.println("\n Error --ItemNameController get JQuery Parameters Part-" + e);
            }

            //System.err.println("task organization name ----"+task);
            List items = null;
            Iterator itr = null;
            DiskFileItemFactory fileItemFactory = new DiskFileItemFactory(); //Set the size threshold, above which content will be stored on disk.
            fileItemFactory.setSizeThreshold(1 * 1024 * 1024); //1 MB Set the temporary directory to store the uploaded files of size above threshold.
            fileItemFactory.setRepository(tmpDir);
            ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
            try {
                items = uploadHandler.parseRequest(request);
                itr = items.iterator();
                while (itr.hasNext()) {
                    FileItem item = (FileItem) itr.next();
                    if (item.isFormField()) {
                        System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString() + "\n");//(getString())its for form field
                        map.put(item.getFieldName(), item.getString("UTF-8"));
                    } else {
                        System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getName());//it is (getName()) for file related things
                        if (item.getName() == null || item.getName().isEmpty()) {
                            map.put(item.getFieldName(), "");
                        } else {
                            String image_name = item.getName();
                            image_name = image_name.substring(0, image_name.length());
                            System.out.println(image_name);
                            map.put(item.getFieldName(), item.getName());
                        }
                    }
                }
                itr = null;
                itr = items.iterator();
            } catch (Exception ex) {
                System.out.println("Error encountered while uploading file" + ex);
            }

            String task = map.get("task");
            if (task == null) {
                task = "";
            }

            if (task.equals("ACTIVE RECORDS")) {
                active = "Y";
                ac = "ACTIVE RECORDS";
            } else if (task.equals("INACTIVE RECORDS")) {
                active = "n";
                ac = "INACTIVE RECORDS";
            } else if (task.equals("ALL RECORDS")) {
                active = "";
                ac = "ALL RECORDS";
            }

            String task1 = request.getParameter("task1");
            if (task1 == null) {
                task1 = "";
            }
            
            if (task1.equals("viewImage")) {
                try {

                    String destinationPath = "";
                    String item_names_id = request.getParameter("item_names_id");
                    if (item_names_id != null && !item_names_id.isEmpty()) {
                        destinationPath = model.getImagePath(item_names_id);
                        if (destinationPath.isEmpty()) {
                            destinationPath = "C:\\ssadvt_repository\\health_department\\general\\no_image.png";
                        }
                    } else {
                        System.out.println("Image Not Found");
                        destinationPath = "C:\\ssadvt_repository\\health_department\\general\\no_image.png";
                    }
                    //destinationPath = keyModel.getImagePath(emp_code);

                    System.err.println("destinationPath=-------------" + destinationPath);
                    File f = new File(destinationPath);
                    FileInputStream fis = null;
                    if (!f.exists()) {
                        destinationPath = "C:\\ssadvt_repository\\health_department\\general\\no_image.png";
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
                    System.out.println("ItemNameController Demand Note Error :" + e);
                    return;
                }
            }

            if (task.equals("Delete")) {
                model.deleteRecord(Integer.parseInt(map.get("item_name_id")));  // Pretty sure that tp_works_id will be available.
            } else if (task.equals("Save") || task.equals("Save AS New") || task.equals("Save & Next")) {
                int item_name_id;
                int item_image_details_id;
                try {
                    // tp_works_id may or may NOT be available i.e. it can be update or new record.

                    item_name_id = Integer.parseInt(map.get("item_name_id"));
                    item_image_details_id = Integer.parseInt(map.get("item_image_details_id"));
                } catch (Exception e) {
                    item_name_id = 0;
                    item_image_details_id = 0;
                }
                if (task.equals("Save AS New")) {
                    item_name_id = 0;
                    item_image_details_id = 0;
                }
                ItemName bean = new ItemName();
                bean.setItem_names_id(item_name_id);
                bean.setItem_code(map.get("item_code").trim());
                bean.setItem_type_id(model.getItemTypeID(map.get("item_type").trim()));
                bean.setQuantity(Integer.parseInt(map.get("quantity").trim()));
                bean.setItem_name(map.get("item_name").trim());
                bean.setDescription(map.get("description").trim());
                bean.setItem_image_details_id(item_image_details_id);
                bean.setImage_path(map.get("item_image"));
                String item_image = model.getDestination_Path("item_img");
                response.setContentType("image/jpeg");

                if (item_name_id == 0) {
                    // if tp_works_id was not provided, that means insert new record.
                    model.insertRecord(bean, itr, item_image);
                } else {
                    // update existing record.
                    model.updateRecord(bean, itr, item_name_id, item_image);
                }
            }

            List<ItemName> list = model.showData(search_item_name, search_item_type, search_item_code);

            request.setAttribute("list", list);
            request.setAttribute("auto_item_code", auto_item_code);
            request.setAttribute("search_item_name", search_item_name);
            request.setAttribute("search_item_type", search_item_type);
            request.setAttribute("search_item_code", search_item_code);
            request.setAttribute("message", model.getMessage());
            request.setAttribute("msgBgColor", model.getMsgBgColor());
            model.closeConnection();

            request.getRequestDispatcher("item_name").forward(request, response);
        } catch (Exception ex) {
            System.out.println("ItemNameController error: " + ex);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}