/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.controller;

import com.DBConnection.DBConnection;
import com.inventory.model.ModelNameModel;
import com.inventory.tableClasses.ModelName;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Komal
 */
public class ModelNameController extends HttpServlet {

    private File tmpDir;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext ctx = getServletContext();
        Map<String, String> map = new HashMap<String, String>();
        request.setCharacterEncoding("UTF-8");

        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        ModelNameModel model = new ModelNameModel();
        String active = "Y";
        String ac = "ACTIVE RECORDS";
        String image_folder = "";
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("logged_user_name") == null) {
            request.getRequestDispatcher("/").forward(request, response);
            return;
        }
        String loggedUser = "";
        loggedUser = session.getAttribute("user_role").toString();
        String image_name = "";
        String active1 = request.getParameter("active");
        try {

            model.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in ModelNameController setConnection() calling try block" + e);
        }

        try {
            String searchModel = "";
            String searchManufacturer = "";
            String searchItemCode = "";
            try {
                String JQstring = request.getParameter("action1");
                String q = request.getParameter("str");
                if (JQstring != null) {
                    PrintWriter out = response.getWriter();
                    List<String> list = null;
                    if (JQstring.equals("getManufacturer")) {
                        list = model.getManufacturer(q);
                    }

                    if (JQstring.equals("getItemCode")) {
                        String str2 = request.getParameter("str2");
                        list = model.getItemCode(q, str2);
                    }
                    if (JQstring.equals("getModel")) {
                        String str2 = request.getParameter("str2");
                        String str3 = request.getParameter("str3");
                        list = model.getModel(q, str2, str3);
                    }

                    if (JQstring.equals("getItemTypeForModelOrPart")) {
                        String item_code = request.getParameter("item_code");
                        list = model.getItemTypeForModelOrPart(item_code);
                    }
                    JSONObject gson = new JSONObject();
                    gson.put("list", list);
                    out.println(gson);
                    DBConnection.closeConncetion(model.getConnection());
                    return;
                }
            } catch (Exception e) {
                System.out.println("\n Error --ModelNameController get JQuery Parameters Part-" + e);
            }
            searchModel = request.getParameter("searchModel");
            searchItemCode = request.getParameter("searchItemCode");
            searchManufacturer = request.getParameter("searchManufacturer");
            try {

                if (searchManufacturer == null) {
                    searchManufacturer = "";
                }
                if (searchModel == null) {
                    searchModel = "";
                }
                if (searchItemCode == null) {
                    searchItemCode = "";
                }
            } catch (Exception e) {
            }
            String search = request.getParameter("searchModel");
            if (search == null) {
                search = "";
            }

            List image_name_list = new ArrayList();
            String destination_path = "";
            String imagePath = "";
            String string = "";
            List<String> imageNameList = new ArrayList<String>();
            boolean isCreated = true;
            List items = null;
            Iterator itr = null;
            List<File> list2 = new ArrayList<File>();
            DiskFileItemFactory fileItemFactory = new DiskFileItemFactory(); //Set the size threshold, above which content will be stored on disk.
            fileItemFactory.setSizeThreshold(8 * 1024 * 1024); //1 MB Set the temporary directory to store the uploaded files of size above threshold.
            fileItemFactory.setRepository(new File(""));
            ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
            try {
                String auto_no = "";
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

                            image_name = item.getName();
                            imageNameList.add(image_name);
                            image_name = image_name.substring(0, image_name.length());
                            int index = image_name.indexOf('.');
                            String ext = image_name.substring(index, image_name.length());
                            map.put(item.getFieldName(), item.getName());
                            destination_path = "C:\\ssadvt_repository\\APL\\item\\" + map.get("item_name") + "\\";
                            String folder = map.get("model");
                            image_folder = destination_path + "\\" + folder;
                            imagePath = image_folder;
                            image_name_list.add(image_name);
                            list2.add(new File(imagePath + "\\" + image_name));
                        }
                    }
                }

                itr = null;
                itr = items.iterator();
            } catch (Exception e) {
                System.out.println("Error is :" + e);
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
            String item_image_detail_id = request.getParameter("item_image_details_id");
            if (task1 == null) {
                task1 = "";
            }

            if (task1.equals("viewImage")) {
                try {

                    List<String> image_list = null;
                    String destinationPath = "";

                    if (item_image_detail_id != null && !item_image_detail_id.isEmpty()) {

                        destinationPath = model.getImagePath(item_image_detail_id);

                        if (destinationPath.isEmpty()) {
                            destinationPath = "C:\\ssadvt_repository\\health_department\\general\\no_image.png";
                        }
                    } else {
                        System.out.println("Image Not Found");
                        destinationPath = "C:\\ssadvt_repository\\health_department\\general\\no_image.png";
                    }

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

                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
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
//            if (task1.equals("map_model")) {
//                String item_names_id = request.getParameter("item_names_id").trim();
//                String item_code=model.getItemNameCode(item_names_id);
//                request.setAttribute("item_code", item_code);
//                request.getRequestDispatcher("model_name").forward(request, response);
//            }

            if (task.equals("Delete")) {
                model.deleteRecord(Integer.parseInt(map.get("model_id")));  // Pretty sure that office_type_id will be available.
            } else if (task.equals("Save") || task.equals("Save AS New")) {
                int model_id;
                int manufacturer_item_map_id;
                int item_image_details_id = 0;
                try {
                    model_id = Integer.parseInt(map.get("model_id"));
                    manufacturer_item_map_id = Integer.parseInt(map.get("manufacturer_item_map_id"));
                    item_image_details_id = Integer.parseInt(map.get("item_image_details_id"));

                } catch (Exception e) {
                    model_id = 0;
                    manufacturer_item_map_id = 0;
                    item_image_details_id = 0;
                }

                String model_no = "";
                String part_no = "";
                ModelName bean = new ModelName();
                System.err.println("map.get(\"model_no\")---" + map.get("model_no"));
                bean.setModel_id(model_id);
                bean.setModel(map.get("model").trim());
                bean.setManufacturer_name(map.get("manufacturer_name"));
                bean.setManufacturer_item_map_id(Integer.parseInt("0" + map.get("manufacturer_item_map_id")));
                bean.setItem_code(map.get("item_code"));
                bean.setLead_time(Integer.parseInt("0" + map.get("lead_time").trim()));
                bean.setBasic_price(map.get("basic_price").trim());
                if (map.get("model_no") == null) {
                    model_no = "";
                } else {
                    model_no = map.get("model_no");
                }
                if (map.get("part_no") == null) {
                    part_no = "";
                } else {
                    part_no = map.get("part_no");
                }
                bean.setModel_no(model_no);
                bean.setPart_no(part_no);
                bean.setDescription(map.get("description").trim());

                String item_image = "";
                if (model_id == 0) {
                    if (imageNameList.size() > 0) {
                        for (int i = 0; i < imageNameList.size(); i++) {
                            bean.setItem_image_details_id(item_image_details_id);
                            bean.setImage_path(image_folder);
                            bean.setImage_name(image_name);
                            item_image = model.getDestination_Path("item_img");
                            response.setContentType("image/jpeg");
                            model.insertRecord(bean, itr, image_name, image_folder, i);
                        }
                    } else {
                        model.insertRecord(bean, itr, image_name, image_folder, 0);
                    }
                } else {
                    int image_count = model.getImageCount(model_id);
                    if (image_count == 0 || imageNameList.size() == 0) {
                        model.updateRecord(bean, itr, model_id, image_name, image_folder, 0);
                    }
                    for (int i = (image_count + 1); i < (image_count + 1 + imageNameList.size()); i++) {
                        bean.setItem_image_details_id(item_image_details_id);
                        bean.setImage_path(image_folder);
                        bean.setImage_name(image_name);
                        item_image = model.getDestination_Path("item_img");
                        response.setContentType("image/jpeg");
                        model.updateRecord(bean, itr, model_id, image_name, image_folder, i);
                    }
                }

            }

            List<ModelName> list = model.showData(searchManufacturer, searchModel, searchItemCode, active);
            request.setAttribute("list", list);
            request.setAttribute("searchManufacturer", searchManufacturer);
            request.setAttribute("searchModel", searchModel);
            request.setAttribute("searchItemCode", searchItemCode);
            request.setAttribute("message", model.getMessage());
            request.setAttribute("msgBgColor", model.getMsgBgColor());
            request.setAttribute("loggedUser", loggedUser);

            DBConnection.closeConncetion(model.getConnection());
            request.getRequestDispatcher("model_name").forward(request, response);
        } catch (Exception ex) {
            System.out.println("ModelNameController error: " + ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }

}
