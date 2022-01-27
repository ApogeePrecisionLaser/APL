
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.controller;

import com.DBConnection.DBConnection;
import com.inventory.model.ItemNameModel;
import com.inventory.model.ModelNameModel;
import com.inventory.tableClasses.ItemAuthorization;
import com.inventory.tableClasses.ItemName;
import com.inventory.tableClasses.ModelName;
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
public class ItemNameController extends HttpServlet {

    private File tmpDir;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext ctx = getServletContext();
        HttpSession session = request.getSession();
        Map<String, String> map = new HashMap<String, String>();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        ItemNameModel model = new ItemNameModel();
        ModelNameModel model2 = new ModelNameModel();
        String loggedUser = "";
        String active = "Y";
        String ac = "ACTIVE RECORDS";
        String item_name = "";
        String item_type = "";
        String item_code = "";
        String image_folder = "";
        String image_name = "";
        int no_of_col = 1;
        int quantity = 0;
        int counting = 100;

//        String auto_item_code = "";
//        int auto_increment_key = 100;
//        Random rnd = new Random();
//        int number = rnd.nextInt(999999);
//        auto_item_code = "APL_ITEM_" + number;
        String search_item_name = "";
        String search_item_type = "";
        String search_item_code = "";
        String search_generation = "";
        String search_super_child = "";

        search_item_name = request.getParameter("search_item_name");
        search_item_type = request.getParameter("search_item_type");
        search_item_code = request.getParameter("search_item_code");
        search_generation = request.getParameter("search_generation");
        search_super_child = request.getParameter("search_super_child");

        if (search_item_name == null) {
            search_item_name = "";
        }
        if (search_item_type == null) {
            search_item_type = "";
        }
        if (search_item_code == null) {
            search_item_code = "";
        }
        if (search_generation == null) {
            search_generation = "";
        }
        if (search_super_child == null) {
            search_super_child = "";
        }

        try {
            loggedUser = session.getAttribute("user_role").toString();
            model.setConnection(DBConnection.getConnectionForUtf(ctx));
            model2.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in ItemNameController setConnection() calling try block" + e);
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
                    if (JQstring.equals("getItemType")) {

                        list = model.getItemType(q);
                    }
                    if (JQstring.equals("getItemName")) {
                        list = model.getItemName(q, str2);
                    }
                    if (JQstring.equals("getItemCode")) {
                        list = model.getItemCode(q, str2, str3);
                    }
                    if (JQstring.equals("getParentItemName")) {
                        list = model.getParentItemName(q);
                    }
                    if (JQstring.equals("getGeneration")) {
                        list = model.getGeneration(q);
                    }
                    if (JQstring.equals("getSuperChild")) {
                        list = model.getSuperChild(q);
                    }
                    if (JQstring.equals("getItems")) {
                        JSONObject obj1 = new JSONObject();
                        JSONArray arrayObj = new JSONArray();
                        String item_names = request.getParameter("item_name");
                        arrayObj = model.getItems(item_names);
                        obj1.put("org_chart_data", arrayObj);
                        out.print(obj1);
                        return;
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
                System.out.println("\n Error --ItemNameController get JQuery Parameters Part-" + e);
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
            DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
            fileItemFactory.setSizeThreshold(8 * 1024 * 1024);
            fileItemFactory.setRepository(new File(""));
            ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
            try {
                String auto_no = "";
                items = uploadHandler.parseRequest(request);
                itr = items.iterator();
                while (itr.hasNext()) {
                    FileItem item = (FileItem) itr.next();
                    if (item.isFormField()) {
                        System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString() + "\n");
                        map.put(item.getFieldName(), item.getString("UTF-8"));

                    } else {
                        System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getName());
                        if (item.getName() == null || item.getName().isEmpty()) {
                            map.put(item.getFieldName(), "");
                        } else {
                            image_name = item.getName();
                            imageNameList.add(image_name);
                            image_name = image_name.substring(0, image_name.length());
                            int index = image_name.indexOf('.');
                            String ext = image_name.substring(index, image_name.length());
                            map.put(item.getFieldName(), item.getName());
                            destination_path = "C:\\ssadvt_repository\\APL\\item";
                            String folder = map.get("item_name");
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
            List<String> lst = new ArrayList<>();

            if (task.equals("Delete")) {
                model.deleteRecord(Integer.parseInt(map.get("item_name_id")));
            } else if (task.equals("Save") || task.equals("Save AS New") || task.equals("Save & Next")) {
                int item_name_id = 0;
                int model_id = 0;
                int manufacturer_item_map_id = 0;
                int item_image_details_id = 0;
                int item_authorization_id = 0;
                int count = 0;
                try {
                    item_name_id = Integer.parseInt(map.get("item_name_id").trim());
//                    model_id = Integer.parseInt(map.get("model_id"));
//                    manufacturer_item_map_id = Integer.parseInt(map.get("manufacturer_item_map_id"));
//                    item_image_details_id = Integer.parseInt(map.get("item_image_details_id"));
                } catch (Exception e) {
                    item_name_id = 0;
                    model_id = 0;
                    manufacturer_item_map_id = 0;
                    item_image_details_id = 0;
                    item_authorization_id = 0;
                }

                ItemName bean = new ItemName();
                bean.setItem_names_id(item_name_id);
                // bean.setItem_code(map.get("item_code").trim());
                bean.setItem_type_id(model.getItemTypeID(map.get("item_type").trim()));
//                String qty = map.get("quantity");
//                int int_qty = 0;
//                if (qty.equals("")) {
//                    int_qty = 0;
//                } else {
//                    int_qty = Integer.parseInt(map.get("quantity").trim());
//                }
//                bean.setQuantity(int_qty);
                bean.setHSNCode(map.get("HSNCode").trim());
                bean.setItem_name(map.get("item_name").trim());
                bean.setPrefix(map.get("prefix").trim());
                bean.setParent_item(map.get("parent_item").trim());
                bean.setDescription(map.get("description").trim());
                String superp = map.get("super").trim();
                if (superp == null) {
                    superp = "";
                }

                bean.setSuperp(superp);

                ItemAuthorization itemAuthBean = new ItemAuthorization();
                itemAuthBean.setItem_authorization_id(item_authorization_id);
                itemAuthBean.setDesignation(map.get("designation").trim());

                if (item_name_id == 0) {
                    if (superp.equals("Y")) {
                        String model_no = "";
                        String part_no = "";
                        ModelName modelBean = new ModelName();

                        count = Integer.parseInt(map.get("count").trim());
                        if (count == 0) {
                            count = 1;
                        }
                        for (int j = 0; j < count; j++) {
                            modelBean.setModel_id(model_id);
                            modelBean.setModel(map.get("model_" + (j + 1)).trim());
                            modelBean.setManufacturer_name(map.get("manufacturer_name_" + (j + 1)));
                            modelBean.setManufacturer_item_map_id(Integer.parseInt("0" + map.get("manufacturer_item_map_id_" + (j + 1))));
                            modelBean.setLead_time(Integer.parseInt("0" + map.get("lead_time_" + (j + 1)).trim()));
                            modelBean.setBasic_price(map.get("basic_price_" + (j + 1)).trim());
                            if (map.get("model_no_" + (j + 1)) == null) {
                                model_no = "";
                            } else {
                                model_no = map.get("model_no_" + (j + 1));
                            }
                            if (map.get("part_no_" + (j + 1)) == null) {
                                part_no = "";
                            } else {
                                part_no = map.get("part_no_" + (j + 1));
                            }
                            modelBean.setModel_no(model_no);
                            modelBean.setPart_no(part_no);
                            modelBean.setDescription(map.get("description").trim());
                            String item_image = "";
                            modelBean.setItem_image_details_id(item_image_details_id);
                            modelBean.setImage_path(image_folder);
                            modelBean.setImage_name(image_name);
                            item_image = model.getDestination_Path("item_img");
                            response.setContentType("image/jpeg");
                            model.insertRecord(bean, itr, modelBean, j, image_name, image_folder, itemAuthBean);
                        }
                    } else {
                        model.insertParent(bean, itr);
                    }
                } else {
                    model.updateRecord(bean, itr, item_name_id);
                }

                String item_types = map.get("item_type").trim();
                request.setAttribute("item_type", map.get("item_type").trim());
                request.setAttribute("item_name", map.get("item_name").trim());
                request.setAttribute("prefix", map.get("prefix").trim());
                request.setAttribute("parent_item", map.get("parent_item").trim());
                request.setAttribute("super_child", map.get("super").trim());
                request.setAttribute("HSNCode", map.get("HSNCode").trim());
                request.setAttribute("designation", map.get("designation").trim());
                request.setAttribute("count", map.get("count").trim());
                request.setAttribute("description", map.get("description").trim());

                List<String> lst2 = new ArrayList<>();
                List<String> lst3 = new ArrayList<>();
                List<String> lst4 = new ArrayList<>();
                List<String> lst5 = new ArrayList<>();
                List<String> lst6 = new ArrayList<>();
                if (superp.equals("Y")) {
//                    if (count == 0) {
//                        count = 1;
//                    }
                    for (int k = 0; k < count; k++) {
                        lst.add(map.get("manufacturer_name_" + (k + 1)).trim());
                        lst2.add(map.get("model_" + (k + 1)).trim());
                        lst3.add(map.get("lead_time_" + (k + 1)).trim());
                        lst4.add(map.get("basic_price_" + (k + 1)).trim());
                        if (item_types.equals("Non-Raw Material")) {
                            lst5.add(map.get("model_no_" + (k + 1)).trim());

                        } else {
                            lst6.add(map.get("part_no_" + (k + 1)).trim());

                        }

                    }

                    request.setAttribute("lst", lst);
                    request.setAttribute("lst2", lst2);
                    request.setAttribute("lst3", lst3);
                    request.setAttribute("lst4", lst4);
                    request.setAttribute("lst5", lst5);
                    request.setAttribute("lst6", lst6);

                }
            }
            String org_chart = request.getParameter("org_chart");
            if (org_chart == null) {
                org_chart = "";
            }
            if (org_chart.equals("Org Chart")) {
                String item_names = request.getParameter("item_name");
                request.setAttribute("item_name", item_names);
                request.getRequestDispatcher("orgChart.jsp").forward(request, response);
            }

            //Auto increment count for item code
            //  counting = model.getCounting();
            List<ItemName> list = model.showData(search_item_name, search_item_type, search_item_code, search_super_child, search_generation);
            List<ItemName> designation_list = model.getDesignation();
            String auto_item_code = "APL_ITEM_" + counting;
            request.setAttribute("list", list);
            // request.setAttribute("auto_item_code", auto_item_code);
            request.setAttribute("search_item_name", search_item_name);
            request.setAttribute("search_item_type", search_item_type);
            request.setAttribute("search_item_code", search_item_code);
            request.setAttribute("search_super_child", search_super_child);
            request.setAttribute("search_generation", search_generation);
            request.setAttribute("designation_list", designation_list);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("message", model.getMessage());
            request.setAttribute("msgBgColor", model.getMsgBgColor());
            request.setAttribute("lst_size", lst.size());

            DBConnection.closeConncetion(model.getConnection());

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
