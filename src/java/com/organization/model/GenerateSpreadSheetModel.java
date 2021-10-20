/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.organization.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
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
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.function.Supplier;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import org.json.simple.JSONObject;

/**
 *
 * @author Komal
 */
public class GenerateSpreadSheetModel {

    private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "#a2a220";
    private final String COLOR_ERROR = "red";
    int kp_id;

    Logger logger = Logger.getLogger("MyLog");
    FileHandler fh;

    /**
     * Application name.
     */
    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    /**
     * Directory to store user credentials for this application.
     */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/sheets.googleapis.com-java-quickstart");
    /**
     * Global instance of the {@link FileDataStoreFactory}.
     */
    private static FileDataStoreFactory DATA_STORE_FACTORY;
    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    /**
     * Global instance of the HTTP transport.
     */
    private static HttpTransport HTTP_TRANSPORT;
    /**
     * Global instance of the scopes required by this spreadsheet.
     */
    private static final List<String> SCOPES = Arrays.asList(SheetsScopes.DRIVE);
    //private static final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("GenerateSpreadSheetModel setConnection() Error: " + e);
        }
    }

    public List<GenerateSpreadSheet> getData(String task, String org_name, String org_office_name, String org_office_type, String designation, String person) {
        List<GenerateSpreadSheet> list = new ArrayList<GenerateSpreadSheet>();
        ArrayList<GenerateSpreadSheet> dataList = new ArrayList<GenerateSpreadSheet>();

        String query = " select distinct "
                + " kp.key_person_id, "
                + " kp.key_person_name,kp.address_line1,kp.address_line2,kp.address_line3,kp.mobile_no1,kp.email_id1,kp.emp_code, "
                + " kp.father_name,kp.date_of_birth,kp.emergency_contact_name,kp.emergency_contact_mobile, "
                + " onn.organisation_name,onn.organisation_code,oo.org_office_name,oo.address_line1,oo.email_id1, "
                + " oo.mobile_no1,oo.org_office_code, "
                + " d.designation,d.designation_code ,oot.office_type"
                + " from key_person kp, organisation_name onn, org_office oo, designation d, "
                + " org_office_designation_map oodm, org_office_type oot"
                + " where kp.active='y' and oo.active='y' and onn.active='y' and d.active='y' and oodm.active='Y' and oot.active='Y' "
                + " and oo.organisation_id=onn.organisation_id and kp.org_office_id=oo.org_office_id "
                + " and oodm.designation_id=d.designation_id and oodm.org_office_id=oo.org_office_id "
                + " and kp.org_office_designation_map_id=oodm.org_office_designation_map_id and oo.office_type_id=oot.office_type_id ";

        if (!org_name.equals("") && org_name != null) {
            query += " and onn.organisation_name='" + org_name + "' ";
        }
        if (!org_office_name.equals("") && org_office_name != null) {
            query += " and oo.org_office_name='" + org_office_name + "' ";
        }
        if (!org_office_type.equals("") && org_office_type != null) {
            query += " and oot.office_type='" + org_office_type + "' ";
        }
        if (!designation.equals("") && designation != null) {
            query += " and d.designation='" + designation + "' ";
        }
        if (!person.equals("") && person != null) {
            query += " and kp.key_person_name='" + person + "' ";
        }

        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/apl", "root", "root");

            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                GenerateSpreadSheet bean = new GenerateSpreadSheet();
                bean.setKey_person_id(rset.getInt(1));
                bean.setKey_person_name(rset.getString(2));
                bean.setKp_address_line1(rset.getString(3));
                bean.setKp_address_line2(rset.getString(4));
                bean.setKp_address_line3(rset.getString(5));
                bean.setKp_mobile_no1(rset.getString(6));
                bean.setKp_email_id1(rset.getString(7));
                bean.setEmp_code(rset.getInt(8));
                bean.setKp_father_name(rset.getString(9));
                bean.setKp_date_of_birth(rset.getString(10));
                bean.setEmergency_contact_name(rset.getString(11));
                bean.setEmergency_contact_mobile(rset.getString(12));
                bean.setOrganisation_name(rset.getString(13));
                bean.setOrganisation_code(rset.getString(14));
                bean.setOrg_office_name(rset.getString(15));
                bean.setOff_address_line1(rset.getString(16));
                bean.setOff_email_id1(rset.getString(17));
                bean.setOff_mobile_no1(rset.getString(18));
                bean.setOrg_office_code(rset.getString(19));
                bean.setDesignation(rset.getString(20));
                bean.setDesignation_code(rset.getInt(21));
                bean.setOrg_office_type(rset.getString(22));
                dataList.add(bean);
                kp_id = rset.getInt(1);

            }

        } catch (Exception e) {
            System.out.println("Error in GoogleSpreadSheet -- " + e);

        }

        return dataList;

    }

    public String sendReport(String task, String org_name, String office_name, String search_org_office_type, String searchDesignation,
            String searchPerson, String email) {
        try {

            String str = "komal";
            JSONObject object = new JSONObject();
            object.put("range", "Sheet1!A4");
            object.put("majorDimension", "ROWS");
            object.put("values", "Hello");

            String spreadsheetId = "1yIqtcD4jIMT9ytr610Rqv1E4lNPNc-Qde6D09RW3e6M";
            String valueInputOption = "USER_ENTERED";
            String insertDataOption = "INSERT_ROWS";
            Sheets sheetsService = createSheetsService();
            String sheet_link = "";
            String sheet = "Dealers";
            String sheetRange = sheet + "!A2";
            List<List<Object>> listOfListOfStrings = new ArrayList<List<Object>>();

            List<GenerateSpreadSheet> list1 = getData(task, org_name, office_name, search_org_office_type, searchDesignation, searchPerson);

            Iterator itr = list1.iterator();
            GenerateSpreadSheet gs = new GenerateSpreadSheet();
            int k = 0;
            while (itr.hasNext() && k < list1.size()) {

                List<Object> listOfStrings = new ArrayList<Object>();
                gs = (GenerateSpreadSheet) list1.get(k);
                int key_person_id = gs.getKey_person_id();
                String key_person_name = gs.getKey_person_name();
                String kp_address_line1 = gs.getKp_address_line1();
                String kp_address_line2 = gs.getKp_address_line2();
                String kp_address_line3 = gs.getKp_address_line3();
                String kp_mobile_no1 = gs.getKp_mobile_no1();
                String kp_email_id1 = gs.getKp_email_id1();
                int kp_emp_code = gs.getEmp_code();
                String kp_father_name = gs.getKp_father_name();
                String kp_date_of_birth = gs.getKp_date_of_birth();
                String emergency_contact_name = gs.getEmergency_contact_name();
                String emergency_contact_mobile = gs.getEmergency_contact_mobile();
                String organisation_name = gs.getOrganisation_name();
                String organisation_code = gs.getOrganisation_code();
                String org_office_name = gs.getOrg_office_name();
                String org_office_type = gs.getOrg_office_type();
                String off_address_line1 = gs.getOff_address_line1();
                String off_email_id1 = gs.getOff_email_id1();
                String off_mobile_no1 = gs.getOff_mobile_no1();
                String org_office_code = gs.getOrg_office_code();
                String designation_name = gs.getDesignation();
                int designation_code = gs.getDesignation_code();

                // listOfStrings.add(key_person_id);
                listOfStrings.add(organisation_name);
                listOfStrings.add(organisation_code);
                listOfStrings.add(org_office_name);
                listOfStrings.add(org_office_type);
                listOfStrings.add(org_office_code);
                listOfStrings.add(designation_name);
                listOfStrings.add(designation_code);
                listOfStrings.add(off_address_line1);
                listOfStrings.add(off_email_id1);
                listOfStrings.add(off_mobile_no1);
                listOfStrings.add(key_person_name);
                listOfStrings.add(kp_address_line1);
                listOfStrings.add(kp_address_line2);
                listOfStrings.add(kp_address_line3);
                listOfStrings.add(kp_mobile_no1);
                listOfStrings.add(kp_email_id1);
                listOfStrings.add(kp_emp_code);
                listOfStrings.add(kp_father_name);
                listOfStrings.add(kp_date_of_birth);
                listOfStrings.add(emergency_contact_name);
                listOfStrings.add(emergency_contact_mobile);
                listOfListOfStrings.add(listOfStrings);
                k++;
            }

            ClearValuesResponse execute = sheetsService.spreadsheets().values().clear(spreadsheetId, "A2:U500", new ClearValuesRequest()).execute();

            ValueRange requestBody1 = new ValueRange();
            requestBody1.setRange(sheetRange);
            requestBody1.setMajorDimension("ROWS");
            requestBody1.setValues(listOfListOfStrings);

            Sheets.Spreadsheets.Values.Append request_sheet
                    = sheetsService.spreadsheets().values().append(spreadsheetId, sheetRange, requestBody1);
            request_sheet.setValueInputOption(valueInputOption);

            AppendValuesResponse response_sheet = request_sheet.execute();

            sheet_link = "https://docs.google.com/spreadsheets/d/1yIqtcD4jIMT9ytr610Rqv1E4lNPNc-Qde6D09RW3e6M/edit?usp=sharing";

            message = sentMail(sheet_link, email);
            if (message.equals("")) {
                message = "Sending Mail Failed!...";
            }
        } catch (Exception e) {
            System.err.println("exception-------" + e);
        }
        return message;
    }

    public Sheets createSheetsService() throws IOException, GeneralSecurityException {
        String refreshToken = "1//04SlFKMWjZytYCgYIARAAGAQSNwF-L9IrqhkeNxNRL5auyqb2SZFC4FIEZqSFTcMr0IzPgWDEZ_RuRTE37Og7nX8kPZg6LZghu4A";
        TokenResponse token = refreshAccessToken(refreshToken);
        Credential credential = createCredentialWithRefreshToken(token);
        // System.err.println("credential -----" + credential.getAccessToken());

        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential) // .setApplicationName("Google-SheetsSample/0.1")
                .setApplicationName(APPLICATION_NAME).build();
    }

    public TokenResponse refreshAccessToken(String refreshToken) throws IOException {
//        String client_id = "765406836926-dmdni26h4jdsotnpjgmgrt3g6rlplgss.apps.googleusercontent.com";
//        String secret_id = "Yl1k_a8bko6lAYLG-P_EhbVe";

        String client_id = "795825412545-u8f2isupi4mbhb219hdfe6fn7fbobqsv.apps.googleusercontent.com";
        String secret_id = "DQUPJyx2wT-hdPnhfsM4DExf";

        TokenResponse response = new GoogleRefreshTokenRequest(
                new NetHttpTransport(),
                new JacksonFactory(),
                refreshToken,
                client_id,
                secret_id)
                .execute();
        //  System.out.println("Access token: " + response.getAccessToken());

        return response;
    }

    public Credential createCredentialWithRefreshToken(TokenResponse tokenResponse) {
        return new Credential.Builder(BearerToken.authorizationHeaderAccessMethod()).setTransport(
                new NetHttpTransport())
                .setJsonFactory(new JacksonFactory())
                .setTokenServerUrl(
                        new GenericUrl("https://oauth2.googleapis.com/token"))
                .setClientAuthentication(new BasicAuthentication("DQUPJyx2wT-hdPnhfsM4DExf", "795825412545-u8f2isupi4mbhb219hdfe6fn7fbobqsv.apps.googleusercontent.com"))
                .build()
                .setFromTokenResponse(tokenResponse);
    }

    public String sentMail(String sheet_link, String mail_id) {
        //System.err.println("hello-----------");
        String to = mail_id;//change accordingly  
        String from = "smartmeter.apogee@gmail.com";
        String host = "smtp.gmail.com";//or IP address  
        //String port = "587";
        String port = "465";
        String password = "jpss1277";
        String success_message = "";
        //Get the session object  
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        //properties.put("mail.smtp.starttls.enable", "true");

//        properties.put("mail.smtp.host", host);
//        properties.put("mail.smtp.port", "465");
//        properties.put("mail.smtp.ssl.enable", "true");
//        properties.put("mail.smtp.auth", "true");
        // creates a new session with an authenticator
//        Authenticator auth = new Authenticator() {
//            public PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(from, password);
//            }
//        };
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(from, password);

            }

        });

        session.setDebug(true);

        try {
            fh = new FileHandler("C:/Logs/apl.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            // the following statement is used to log any messages  
            logger.info("APL log");

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Dealers List");

            // Now set the actual message
            message.setText("https://docs.google.com/spreadsheets/d/1yIqtcD4jIMT9ytr610Rqv1E4lNPNc-Qde6D09RW3e6M/edit#gid=0");

            // System.out.println("sending...");
            // Send message
            Transport.send(message);

            success_message = "Mail sent successfully....";
            logger.info("sent mail");

            //System.out.println("Mail Sent successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
            String mes = mex.toString();
            String mes2 = mex.getMessage();
            //System.err.println("message ----" + mes);
            //System.err.println("message 222 ----" + mes2);
            logger.info(mex.getMessage());
        }

//        Session session = Session.getInstance(properties, auth);
//        //compose the message  
//        try {
//
//            MimeMessage message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(from));
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//            message.setSubject("APL Report of Dealers");
//            // message.setText(sheet_link);
//
//            BodyPart messageBodyPart1 = new MimeBodyPart();
//            messageBodyPart1.setText(sheet_link);
//
//            Multipart multipart = new MimeMultipart();
//
//            multipart.addBodyPart(messageBodyPart1);
//
//            message.setContent(multipart);
//            // message.setText("https://docs.google.com/spreadsheets/d/1yIqtcD4jIMT9ytr610Rqv1E4lNPNc-Qde6D09RW3e6M/edit#gid=0");
//
//            Transport.send(message);
//            System.out.println("Mail sent successfully....");
//            success_message = "Mail sent successfully....";
//
//        } catch (MessagingException mex) {
//            mex.printStackTrace();
//        }
        return success_message;
    }

    public List<String> getOrgName(String q) {
        List<String> list = new ArrayList<String>();

        int count = 0;
        String query = " SELECT distinct organisation_name FROM organisation_name where active='Y' "
                + " ORDER BY organisation_id ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            q = q.trim();
            while (rset.next()) {
                String organisation_name = (rset.getString("organisation_name"));
                if (organisation_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(organisation_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such organisation_name exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:designationModel --getOrgName-- " + e);
        }
        return list;
    }

    public List<String> getOrgOfficeName(String org_name, String q) {
        List<String> list = new ArrayList<String>();

        int count = 0;
        String query = " SELECT distinct oo.org_office_name FROM org_office oo,organisation_name oname where oo.active='Y' and oname.active='Y' "
                + "and oo.organisation_id=oname.organisation_id ";
        if (!org_name.equals("") && org_name != null) {
            query += " and oname.organisation_name='" + org_name + "' ";
        }
        query += " ORDER BY oo.org_office_id ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            q = q.trim();
            while (rset.next()) {
                String org_office_name = (rset.getString("org_office_name"));
                if (org_office_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(org_office_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such org_office_name exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:designationModel --getOrgOfficeName-- " + e);
        }
        return list;
    }

    public List<String> getOrgOfficeType(String org_name, String org_office_name, String q) {
        List<String> list = new ArrayList<String>();

        int count = 0;
        String query = " SELECT distinct oot.office_type FROM org_office_type oot,org_office oo,organisation_name oname "
                + " where oo.active='Y' and oname.active='Y' and oot.active='Y' and oo.office_type_id=oot.office_type_id "
                + "and oo.organisation_id=oname.organisation_id ";
        if (!org_name.equals("") && org_name != null) {
            query += " and oname.organisation_name='" + org_name + "' ";
        }
        if (!org_office_name.equals("") && org_office_name != null) {
            query += " and oo.org_office_name='" + org_office_name + "' ";
        }
        query += " ORDER BY oot.office_type_id ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            q = q.trim();
            while (rset.next()) {
                String office_type = (rset.getString("office_type"));
                if (office_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(office_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such office_type exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:designationModel --getOrgOfficeType-- " + e);
        }
        return list;
    }

    public List<String> getDesignation(String q, String org_office) {
        List<String> list = new ArrayList<String>();
        String org_type = "";
        int org_type_id = 0;
        int org_office_id = 0;
        if (!org_office.equals("")) {
            org_office_id = getOrgOfficeId(org_office);
        }

        String designation = "";
        // System.err.println("org_office_id---------" + org_office_id);
        String query = " select distinct d.designation from org_office_designation_map map,designation d where "
                + " d.designation_id=map.designation_id ";
        if (org_office_id != 0) {
            query += " and map.org_office_id='" + org_office_id + "' ";
        }
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            int count = 0;
            q = q.trim();
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {    // move cursor from BOR to valid record.                 
                designation = (rset.getString("designation"));
                list.add(designation);
            }

        } catch (Exception e) {
            System.out.println("Error:keypersonModel--getDesignationList()-- " + e);
        }

        return list;
    }

    public List<String> getPerson(String designation, String org_office_name, String q) {
        List<String> list = new ArrayList<String>();

        int count = 0;
        String query = " SELECT distinct kp.key_person_name FROM key_person kp,org_office oo,designation d "
                + " where kp.active='Y' and oo.active='Y' and d.active='Y' "
                + "and kp.org_office_id=oo.org_office_id and kp.designation_id=d.designation_id ";
        if (!org_office_name.equals("") && org_office_name != null) {
            query += " and oo.org_office_name='" + org_office_name + "' ";
        }
        if (!designation.equals("") && designation != null) {
            query += " and d.designation='" + designation + "' ";
        }
        query += " ORDER BY kp.key_person_id ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            q = q.trim();
            while (rset.next()) {
                String key_person_name = (rset.getString("key_person_name"));
                if (key_person_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(key_person_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such key_person_name exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:designationModel --getPerson-- " + e);
        }
        return list;
    }

    public int getOrgOfficeId(String org_office) {
        int org_office_id = 0;
        String query = "SELECT org_office_id FROM org_office WHERE  org_office_name = '" + org_office + "'  and active='Y' ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            org_office_id = rset.getInt("org_office_id");
        } catch (Exception e) {
            System.out.println("Error: OrganisationMapModel--" + e);
        }

        return org_office_id;
    }

    public String getMessage() {

        return message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("designationModel closeConnection() Error: " + e);
        }
    }
}
