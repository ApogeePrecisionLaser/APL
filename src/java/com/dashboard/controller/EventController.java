package com.dashboard.controller;

import com.DBConnection.DBConnection;
import com.dashboard.bean.EventBean;
import com.dashboard.model.EventModel;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
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


/**
 *
 * @author Komal
 */
public class EventController extends HttpServlet {

    private File tmpDir;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Map<String, String> map = new HashMap<String, String>();

        System.out.println("Starting application");
        response.setContentType("text/html");
        ServletContext ctx = getServletContext();
        EventModel model = new EventModel();
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

        String task = request.getParameter("task");
        if (task == null) {
            task = "";
        }

        String task1 = map.get("task1");
        if (task1 == null) {
            task1 = "";
        }

        if (task1.equals("Send")) {
            int events_id = 0;

            try {
                events_id = Integer.parseInt("0" + map.get("events_id").trim());
            } catch (Exception e) {
                events_id = 0;
            }
            String title = map.get("title").trim();
            String description = map.get("description").trim();

            EventBean bean = new EventBean();
            bean.setTitle(title);
            bean.setDescription(description);
            bean.setEvents_id(events_id);
            bean.setDocument_path(map.get("document_name"));
            if (events_id == 0) {
                try {
                    model.insertEvent(bean, itr);
                } catch (SQLException ex) {
                    Logger.getLogger(HelpController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (task.equals("viewImage")) {
            String document_name = request.getParameter("document_name");
            String destinationPath = "C:\\ssadvt_repository\\APL\\events_doc\\" + document_name;
            if (destinationPath.isEmpty()) {
                destinationPath = "C:\\ssadvt_repository\\APL\\key_person\\no_image.png";
            }

            File f = new File(destinationPath);
            FileInputStream fis = null;
            if (!f.exists()) {
                destinationPath = "C:\\ssadvt_repository\\APL\\key_person\\no_image.png";
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
            while ((ch = bis.read()) != -1) {
                out.write(ch);
            }
            bis.close();
            fis.close();
            out.close();
            os.close();
            response.flushBuffer();
            DBConnection.closeConncetion(model.getConnection());

            return;
        }

        if (task.equals("add_event")) {
            request.getRequestDispatcher("event_add").forward(request, response);
        }
        if (task.equals("dealers_list")) {
            String events_id = request.getParameter("events_id");
//            List<EventBean> dealer_events = model.getDealersEvents(events_id);
            List<EventBean> dealers_list = model.getAllDealers(events_id);

//            request.setAttribute("dealer_events", dealer_events);
            request.setAttribute("dealers_list", dealers_list);
            request.setAttribute("events_id", events_id);
            request.getRequestDispatcher("dealers_list").forward(request, response);
        }
        if (task.equals("event_detail")) {
            String events_id = request.getParameter("events_id");
            List<EventBean> event_details = model.getEventDetail(events_id);
            List<EventBean> dealer_events = model.getDealersEvents(events_id, logged_key_person_id, loggedUser);

            request.setAttribute("title", event_details.get(0).getTitle());
            request.setAttribute("description", event_details.get(0).getDescription());
            request.setAttribute("date_time", event_details.get(0).getDate_time());
            request.setAttribute("document_name", event_details.get(0).getDocument_name());
            request.setAttribute("user_role", loggedUser);

            request.setAttribute("dealer_events", dealer_events);
            request.setAttribute("count", dealer_events.size());

            request.getRequestDispatcher("event_detail").forward(request, response);
        }
        if (task.equals("deleteEvent")) {
            String events_id = request.getParameter("events_id");
            try {
                int rowsAffected = model.deleteEvent(events_id);

//            request.getRequestDispatcher("event_detail").forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(EventController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (task.equals("sendEventsToDealer")) {
            String checked_dealer[] = request.getParameterValues("check");
            String events_id = request.getParameter("events_id");
            try {
                int rowsAffected = model.sendEventsToDealer(events_id, checked_dealer);
            } catch (SQLException ex) {
                Logger.getLogger(EventController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        List<EventBean> event_list = model.getAllEvents(logged_key_person_id, loggedUser);

        request.setAttribute("event_list", event_list);
        request.setAttribute("count", event_list.size());
        request.setAttribute("message", model.getMessage());
        request.setAttribute("msgBgColor", model.getMessageBGColor());
        request.getRequestDispatcher("event_list").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
