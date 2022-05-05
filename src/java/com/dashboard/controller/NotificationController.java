package com.dashboard.controller;

import com.DBConnection.DBConnection;
import com.dashboard.bean.EventBean;
import com.dashboard.model.EventModel;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class NotificationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 15, noOfRowsInTable;

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

        String task = request.getParameter("task");
        if (task == null) {
            task = "";
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

            request.getRequestDispatcher("event_detail").forward(request, response);
        }
        List<EventBean> event_list = model.getAllEvents(logged_key_person_id, loggedUser);

        request.setAttribute("event_list", event_list);
        request.setAttribute("count", event_list.size());
        request.setAttribute("user_role", loggedUser);

        request.setAttribute("message", model.getMessage());
        request.setAttribute("msgBgColor", model.getMessageBGColor());

        request.getRequestDispatcher("notification").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
