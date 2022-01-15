package com.dashboard.controller;

import com.DBConnection.DBConnection;
import com.dashboard.bean.Help;
import com.dashboard.model.HelpModel;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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

public class HelpController extends HttpServlet {

    private File tmpDir;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Map<String, String> map = new HashMap<String, String>();

        System.out.println("Starting application");
        response.setContentType("text/html");
        ServletContext ctx = getServletContext();
        HelpModel model = new HelpModel();
        String logged_user_name = "";
        String logged_designation = "";
        String logged_org_name = "";
        String logged_org_office = "";
        int logged_org_office_id = 0;
        int logged_org_name_id = 0;
        int logged_key_person_id = 0;
        String loggedUser = "";

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
        }

        try {
            model.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in DealersOrderController setConnection() calling try block" + e);
        }

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
                        // System.out.println(image_name);
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

        if (task.equals("Send")) {
            int dealer_help_messages_id = 0;

            try {
                dealer_help_messages_id = Integer.parseInt("0" + map.get("dealer_help_messages_id").trim());
            } catch (Exception e) {
                dealer_help_messages_id = 0;
            }
            String subject = map.get("subject").trim();
            String message = map.get("message").trim();

            Help bean = new Help();
            bean.setSubject(subject);
            bean.setMessage(message);
            bean.setDealer_help_messages_id(dealer_help_messages_id);
            bean.setDealer_id(logged_key_person_id);
            bean.setDocument_path(map.get("document_name"));
            if (dealer_help_messages_id == 0) {
                try {
                    model.insertMessage(bean, itr);
                } catch (SQLException ex) {
                    Logger.getLogger(HelpController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        request.setAttribute("message", model.getMessage());
        request.setAttribute("msgBgColor", model.getMessageBGColor());
        request.getRequestDispatcher("help").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
