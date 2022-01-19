package com.dashboard.controller;

import com.DBConnection.DBConnection;
import com.dashboard.bean.Help;
import com.dashboard.model.HelpModel;
import static com.lowagie.text.pdf.PdfFileSpecification.url;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
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
import org.apache.commons.io.FileUtils;
import static org.apache.poi.hssf.usermodel.HeaderFooter.file;
import org.json.simple.JSONObject;

public class SupportMessagesController extends HttpServlet {

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

        String task1 = request.getParameter("task1");
        if (task1 == null) {
            task1 = "";
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
        if (task1.equals("downloadFile")) {
            String document_name = request.getParameter("document_name");
            String filePath = "C:/ssadvt_repository/APL/help_doc/" + document_name;
            File downloadFile = new File(filePath);
            FileInputStream inStream = new FileInputStream(downloadFile);

            // if you want to use a relative path to context root:
            String relativePath = getServletContext().getRealPath("");
            System.out.println("relativePath = " + relativePath);

            // obtains ServletContext
            ServletContext context = getServletContext();

            // gets MIME type of the file
            String mimeType = context.getMimeType(filePath);
            if (mimeType == null) {
                // set to binary type if MIME mapping not found
                mimeType = "application/octet-stream";
            }
            System.out.println("MIME type: " + mimeType);

            // modifies response
            response.setContentType(mimeType);
            response.setContentLength((int) downloadFile.length());

            // forces download
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
            response.setHeader(headerKey, headerValue);

            // obtains response's output stream
            OutputStream outStream = response.getOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead = -1;

            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }

            inStream.close();
            outStream.close();
        }

        List<Help> supportMessages = model.getAllSupportMessages();

        request.setAttribute("supportMessages", supportMessages);
        request.setAttribute("message", model.getMessage());
        request.setAttribute("msgBgColor", model.getMessageBGColor());
        request.getRequestDispatcher("support_messages").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
