package com.webservice.model;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 *
 * @author komal
 */
public class SendMail {

    public void sendPlainTextEmail(String host, String port,
            final String userName, final String password, String toAddress,
            String subject, String message, String destination, String role, String name, String current_date1, String count_of_total_query_of_current_date,
            String count_of_total_query_till_date, String count_of_open_query_of_current_date, String count_of_open_query_till_date, String count_of_closed_query_of_current_date,
            String count_of_closed_query_till_date, String count_of_sold_query_of_current_date, String count_of_sold_query_till_date) throws AddressException,
            MessagingException {

        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };

        Session session = Session.getInstance(properties, auth);

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(userName));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
            msg.addRecipient(Message.RecipientType.CC, new InternetAddress("komal.apogee@gmail.com"));
            msg.setSubject(subject);

            //3) create MimeBodyPart object and set your message text     
            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setContent("Hello " + name + " (" + role + "), <br />Hope you are having a good day!<br />"
                    + "Here is your sales report of today.Please find the attchment.<br/><br/><br/>"
                    + "<table style='width:450px;border:1px solid #000;cellspacing:0;cellpadding:0;'><tbody><tr>"
                    + "<th style='border-right:1px solid #000;border-bottom:1px solid #000;text-align:left;'>Enquiry Received Today</th>"
                    + "<td style='border-bottom:1px solid #000;padding:10px;min-width:150px;'>" + count_of_total_query_of_current_date + "</td>"
                    + "</tr><tr><th style='border-right:1px solid #000;border-bottom:1px solid #000;text-align:left;'>Open</th>"
                    + "<td style='border-bottom:1px solid #000;padding:10px;min-width:150px;'>" + count_of_open_query_of_current_date + "</td>"
                    + "</tr><tr><th style='border-right:1px solid #000;border-bottom:1px solid #000;text-align:left;'>Closed</th>"
                    + "<td style='border-bottom:1px solid #000;padding:10px;min-width:150px;'>" + count_of_closed_query_of_current_date + "</td></tr><tr>"
                    + "<th style='border-right:1px solid #000;text-align:left;'>Sold</th>"
                    + "<td style='padding:10px;min-width:150px;'>" + count_of_sold_query_of_current_date + "</td></tr></tbody></table><br/><br/><br/>"
                    + "<table style='width:450px;border:1px solid #000;cellspacing:0;cellpadding:0;'><tbody><tr>"
                    + "<th style='border-right:1px solid #000;border-bottom:1px solid #000;text-align:left;'>Total Number Of Inquiry Received</th>"
                    + "<td style='border-bottom:1px solid #000;padding:10px;min-width:150px;'>" + count_of_total_query_till_date + "</td>"
                    + "</tr><tr><th style='border-right:1px solid #000;border-bottom:1px solid #000;text-align:left;'>Open</th>"
                    + "<td style='border-bottom:1px solid #000;padding:10px;min-width:150px;'>" + count_of_open_query_till_date + "</td>"
                    + "</tr><tr><th style='border-right:1px solid #000;border-bottom:1px solid #000;text-align:left;'>Closed</th>"
                    + "<td style='border-bottom:1px solid #000;padding:10px;min-width:150px;'>" + count_of_closed_query_till_date + "</td></tr><tr>"
                    + "<th style='border-right:1px solid #000;text-align:left;'>Sold</th>"
                    + "<td style='padding:10px;min-width:150px;'>" + count_of_sold_query_till_date + "</td></tr></tbody></table><br/><br/><br/>"
                    + "Thanks & Regards,<br/>"
                    + "<b>Apogee Precision Lasers.</b><br/>"
                    + "<img src='https://www.apogeeleveller.com/assets/images/logo.png'>", "text/html");

            //4) create new MimeBodyPart object and set DataHandler object to this object      
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();
            MimeBodyPart messageBodyPart3 = new MimeBodyPart();

            String filename = "C:\\ssadvt_repository\\APL\\Report\\sm.pdf";//change accordingly  
            //  String filename2 = "C:\\ssadvt_repository\\SmartMeterSurvey\\Report\\2021-04-03(Sanjay Nagar)_report.pdf";//change accordingly  
            System.err.println("destination---" + destination);
            filename = destination;
            Multipart multipart = new MimeMultipart();
            DataSource source = new FileDataSource(filename);
            // DataSource source2 = new FileDataSource(filename2);
            messageBodyPart2.setDataHandler(new DataHandler(source));
            // messageBodyPart3.setDataHandler(new DataHandler(source2));
            messageBodyPart2.setFileName(filename);
            // messageBodyPart3.setFileName(filename2);

            //5) create Multipart object and add MimeBodyPart objects to this object      
            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart2);
            // multipart.addBodyPart(messageBodyPart3);
            //6) set the multiplart object to the message object  
            msg.setContent(multipart);

            //7) send message  
            Transport.send(msg);

            System.out.println("message sent....");
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Test the send e-mail method
     *
     */
    public String sentMail(String destination, String mail_id, String role, String current_date, String name, String current_date1, String count_of_total_query_of_current_date,
            String count_of_total_query_till_date, String count_of_open_query_of_current_date, String count_of_open_query_till_date, String count_of_closed_query_of_current_date,
            String count_of_closed_query_till_date, String count_of_sold_query_of_current_date, String count_of_sold_query_till_date) {
        // SMTP server information

        String host = "smtp.gmail.com";
        String port = "587";
        String mailFrom = "smartmeter.apogee@gmail.com";
        String password = "jpss1277";
//C:\ssadvt_repository\APL\Report\2022-02-21 (Enquiry report)
        String mailTo = "komal.apogee@gmail.com";
//        String mailTo = mail_id;

        String subject = current_date + " (Enquiry Report)";
        String message = " Hello Sir/Mam, I hope You're having a wonderful day! ";

        SendMail mailer = new SendMail();

        try {
            mailer.sendPlainTextEmail(host, port, mailFrom, password, mailTo,
                    subject, message, destination, role, name, current_date, count_of_total_query_of_current_date,
                    count_of_total_query_till_date, count_of_open_query_of_current_date, count_of_open_query_till_date, count_of_closed_query_of_current_date,
                    count_of_closed_query_till_date, count_of_sold_query_of_current_date, count_of_sold_query_till_date);
            System.out.println("Email sent.");

        } catch (Exception ex) {
            System.out.println("Failed to sent email.");
            ex.printStackTrace();
        }
        return "strrrrrr";
    }

}
