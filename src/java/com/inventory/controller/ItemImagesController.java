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
 * @author Komal
 */
public class ItemImagesController extends HttpServlet {

    private File tmpDir;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext ctx = getServletContext();
        Map<String, String> map = new HashMap<String, String>();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        ModelNameModel model = new ModelNameModel();

        try {
            model.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in ItemImagesController setConnection() calling try block" + e);
        }
        try {
            String task1 = request.getParameter("task1");
            if (task1 == null) {
                task1 = "";
            }
            String model_id = "";
            List<ModelName> list = null;
            String item_image_details_id = request.getParameter("item_image_details_id");
            if (task1.equals("getImageList")) {
                model_id = request.getParameter("model_id");
                list = model.getImageList(model_id);
            }
            if (task1.equals("deleteImage")) {
                model_id = request.getParameter("model_id");
                model.deleteImageRecord(item_image_details_id);
                list = model.getImageList(model_id);
            }

            request.setAttribute("message", model.getMessage());
            request.setAttribute("msgBgColor", model.getMsgBgColor());
            request.setAttribute("model_id", model_id);
            request.setAttribute("list", list);
            DBConnection.closeConncetion(model.getConnection());
            request.getRequestDispatcher("item_images").forward(request, response);
        } catch (Exception ex) {
            System.out.println("ItemImagesController error: " + ex);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
