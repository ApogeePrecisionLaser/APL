/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.report.controller;

import com.DBConnection.DBConnection;
import com.general.model.GeneralModel;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
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
import static java.awt.PageAttributes.MediaType.C2;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.apache.http.HttpEntity;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
//import org.json.JSONArray;
//import org.json.simple.JSONObject;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import sun.misc.BASE64Decoder;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.BasicAuthentication;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ClearValuesRequest;
import com.google.api.services.sheets.v4.model.ClearValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.organization.tableClasses.GenerateSpreadSheet;
import com.report.model.TestModel;

/**
 *
 * @author Komal
 */
public class TestController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext ctx = getServletContext();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");

        TestModel model = new TestModel();
        String active = "Y";
        String ac = "ACTIVE RECORDS";
        String msg = "";

        try {
            model.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in TestController setConnection() calling try block" + e);
        }
        String message = null;
        String bgColor = null;
        String task = request.getParameter("genrateReport");
        String task1 = request.getParameter("task");
        String designation = null;
        if (task == null) {
            task = "";
        }
        if (task1 == null) {
            task1 = "";
        }
        String email = request.getParameter("email");
        if (email == null) {
            email = "";
        }
        String JQstring = request.getParameter("action1");
        String q = request.getParameter("str");   // field own input

        String org_name = "";
        String office_name = "";
        String search_org_office_type = "";
        String searchDesignation = "";
        String searchPerson = "";

        org_name = request.getParameter("search_org_name");
        office_name = request.getParameter("search_org_office_name");
        search_org_office_type = request.getParameter("search_org_office_type");
        searchDesignation = request.getParameter("searchDesignation");
        searchPerson = request.getParameter("searchPerson");

        if (org_name == null) {
            org_name = "";
        }
        if (office_name == null) {
            office_name = "";
        }
        if (search_org_office_type == null) {
            search_org_office_type = "";
        }
        if (searchDesignation == null) {
            searchDesignation = "";
        }
        if (searchPerson == null) {
            searchPerson = "";
        }
        if (JQstring != null) {
            PrintWriter out = response.getWriter();
            List<String> list = null;
            if (JQstring.equals("getOrgName")) {
                list = model.getOrgName(q);
            } else if (JQstring.equals("getOrgOfficeName")) {
                list = model.getOrgOfficeName(org_name, office_name);
            } else if (JQstring.equals("getOrgOfficeType")) {
                list = model.getOrgOfficeType(org_name, office_name, search_org_office_type);
            } else if (JQstring.equals("getDesignation")) {
                list = model.getDesignation(searchDesignation, office_name);
            } else if (JQstring.equals("getPerson")) {
                list = model.getPerson(searchDesignation, office_name, searchPerson);
            }
            JSONObject gson = new JSONObject();
            gson.put("list", list);
            out.println(gson);

            model.closeConnection();
            return;
        }

        String sheet_link = "";
        try {
            if ((task1.equals("View Report"))) {

                String jrxmlFilePath;
                List list = null;

                response.setContentType("application/pdf");
                response.setCharacterEncoding("UTF-8");
                ServletOutputStream servletOutputStream = response.getOutputStream();

                String type = request.getParameter("type");

                jrxmlFilePath = ctx.getRealPath("/DealersReport.jrxml");
                list = model.showData(task, org_name, office_name, search_org_office_type, searchDesignation, searchPerson);
                byte[] reportInbytes = GeneralModel.generateRecordList(jrxmlFilePath, list);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                return;
                //  message = model.sendReport(task, org_name, office_name, search_org_office_type, searchDesignation, searchPerson, email);
            }

            List<GenerateSpreadSheet> list = model.getData(task, org_name, office_name, search_org_office_type, searchDesignation, searchPerson);
            request.setAttribute("message", message);
            request.setAttribute("list", list);
            request.setAttribute("msgBgColor", "#a2a220");
            request.setAttribute("org_name", org_name);
            request.setAttribute("office_name", office_name);
            request.setAttribute("search_org_office_type", search_org_office_type);
            request.setAttribute("searchDesignation", searchDesignation);
            request.setAttribute("searchPerson", searchPerson);
            request.getRequestDispatcher("testreport").forward(request, response);
        } catch (Exception ex) {
            System.out.println("TestController error: " + ex);
        }
    }

//    public Sheets createSheetsService() throws IOException, GeneralSecurityException {
//        String refreshToken = "1//04SlFKMWjZytYCgYIARAAGAQSNwF-L9IrqhkeNxNRL5auyqb2SZFC4FIEZqSFTcMr0IzPgWDEZ_RuRTE37Og7nX8kPZg6LZghu4A";
//        TokenResponse token = refreshAccessToken(refreshToken);
//        Credential credential = createCredentialWithRefreshToken(token);
//        // System.err.println("credential -----" + credential.getAccessToken());
//
//        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential) // .setApplicationName("Google-SheetsSample/0.1")
//                .setApplicationName(APPLICATION_NAME).build();
//    }
//
//    public TokenResponse refreshAccessToken(String refreshToken) throws IOException {
////        String client_id = "765406836926-dmdni26h4jdsotnpjgmgrt3g6rlplgss.apps.googleusercontent.com";
////        String secret_id = "Yl1k_a8bko6lAYLG-P_EhbVe";
//
//        String client_id = "795825412545-u8f2isupi4mbhb219hdfe6fn7fbobqsv.apps.googleusercontent.com";
//        String secret_id = "DQUPJyx2wT-hdPnhfsM4DExf";
//
//        TokenResponse response = new GoogleRefreshTokenRequest(
//                new NetHttpTransport(),
//                new JacksonFactory(),
//                refreshToken,
//                client_id,
//                secret_id)
//                .execute();
//        //  System.out.println("Access token: " + response.getAccessToken());
//
//        return response;
//    }
//
//    public Credential createCredentialWithRefreshToken(TokenResponse tokenResponse) {
//        return new Credential.Builder(BearerToken.authorizationHeaderAccessMethod()).setTransport(
//                new NetHttpTransport())
//                .setJsonFactory(new JacksonFactory())
//                .setTokenServerUrl(
//                        new GenericUrl("https://oauth2.googleapis.com/token"))
//                .setClientAuthentication(new BasicAuthentication("DQUPJyx2wT-hdPnhfsM4DExf", "795825412545-u8f2isupi4mbhb219hdfe6fn7fbobqsv.apps.googleusercontent.com"))
//                .build()
//                .setFromTokenResponse(tokenResponse);
//    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
