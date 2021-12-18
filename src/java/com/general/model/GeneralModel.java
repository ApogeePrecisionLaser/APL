/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.general.model;

import com.general.tableClasses.CityBean;
import com.general.tableClasses.GenralBean;
import com.general.tableClasses.MobileDao;
import com.general.tableClasses.imgbean;
import com.organization.model.KeypersonModel;
import com.organization.tableClasses.KeyPerson;
import com.organization.tableClasses.Org_Office;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.ColorSupported;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.PrinterName;
import javax.print.attribute.standard.Sides;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author komal
 */
public class GeneralModel {
    private Connection connection;
    private String driver, url, user, password;
    private String message, messageBGColor;
    static List<String> printerList ;
    List<String> list = new ArrayList<String>();
    
    public static byte[] generateRecordList(String jrxmlFilePath, List list) {
        byte[] reportInbytes = null;
        HashMap mymap = new HashMap();
        try {
            JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(list);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, jrBean);
//            JasperReport compiledReport1 = JasperCompileManager.compileReport(jrxmlFilePath);
//            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport1, null, jrBean);
//            String path = createAppropriateDirectories1("C:/ssadvt_repository/prepaid/RideReceipt");
//            path = path + "/receipt.pdf";
//            JRPdfExporter exporter = new JRPdfExporter();
//            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, path);
//            exporter.exportReport();
        } catch (Exception e) {
            System.out.println("GeneralModel generateRecordList() JRException: " + e);
        }
        return reportInbytes;
    }

    public ByteArrayOutputStream generateExcelList(String jrxmlFilePath, List list) {
        ByteArrayOutputStream reportInbytes = new ByteArrayOutputStream();
        HashMap mymap = new HashMap();
        try {
            JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(list);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, jrBean);
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, reportInbytes);
            exporter.exportReport();
            //print(jasperPrint);
        } catch (Exception e) {
            System.out.println("GeneralModel generateExcelList() JRException: " + e);
        }
        return reportInbytes;
    }

    public void SavePdf(String jrxmlFilePath, List list, String reportName) {
        ByteArrayOutputStream reportInbytes = new ByteArrayOutputStream();
        HashMap mymap = new HashMap();
        try {
            JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(list);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, jrBean);
            //JRXlsExporter exporter = new JRXlsExporter();
            String path = createAppropriateDirectories1("C:/ssadvt_repository/prepaid/temp_pdf");
            path = path + "/" +reportName;
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, path);
            exporter.exportReport();
        } catch (Exception e) {
            System.out.println("GeneralModel SavePdf() JRException: " + e);
        }
        //return reportInbytes;
    }

    public ByteArrayOutputStream generateANDSavePdf(String jrxmlFilePath, List list) {
        ByteArrayOutputStream reportInbytes = new ByteArrayOutputStream();
        HashMap mymap = new HashMap();
        try {
            JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(list);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, jrBean);
            //JRXlsExporter exporter = new JRXlsExporter();
            String path = createAppropriateDirectories1("C:/ssadvt_repository/prepaid/RideReceipt");
            path = path + "/receipt.pdf";
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, path);
            exporter.exportReport();
            print(jasperPrint, path);
        } catch (Exception e) {
            System.out.println("GeneralModel generateANDSavePdf() JRException: " + e);
        }
        return reportInbytes;
    }

    
    
    
    public void print(JasperPrint jsprPrint, String fileName) {
        String query = "SELECT printer_name, printer_path, no_of_copies FROM printer_detail";
        try{
            JasperPrint jasperPrint = jsprPrint;//getJasperPrint();
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            String selectedPrinter = "";
            int no_of_copies = 0;
            if(rs.next()){
                selectedPrinter = rs.getString("printer_path");//"\\\\navitus2-pc\\hp officejet 5600 series";//AllPrinter.getDepartmentPrinter("Admin");
                no_of_copies = rs.getInt("no_of_copies");
            }
            //selectedPrinter = "\\\\navitus2-pc\\hp officejet 5600 series";
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
            PrintService selectedService = null;

            if(services.length != 0 || services != null)
            {
                for(PrintService service : services){
                    String existingPrinter = service.getName().toLowerCase();
                    System.out.println("Printer : " + existingPrinter);
                    if(existingPrinter.equals(selectedPrinter))
                    {
                        selectedService = service;
                        break;
                    }
                }
                if(selectedService != null)
                {
                    printerJob.setPrintService(selectedService);
                    
                    if(no_of_copies > 0)
                        printerJob.setCopies(no_of_copies);
                    System.out.println("Printing...");
                    boolean printSucceed = JasperPrintManager.printReport(jsprPrint, false);
                    //boolean printSucceed2 = JasperPrintManager.printReport(fileName, false);
//                    PrintReport pr = new PrintReport();
//                    pr.print(jasperPrint);

                }
            }
        }catch(Exception ex){
            System.out.println("GeneralModel print() JRException: " + ex);
        }
    }

    public static List<String> getPrinter() {
        List<String> list = new ArrayList<String>();
        try{
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            PrintService[] serv = PrinterJob.lookupPrintServices();
            PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
            PrintService selectedService = null;
            if(services.length != 0 || services != null)
            {
                for(PrintService service : services){
                    String existingPrinter = service.getName().toLowerCase();
                    list.add(existingPrinter);
                }
            }
        }catch(Exception ex){
            System.out.println("GeneralModel getPrinter() JRException: " + ex);
        }
        return list;
    }

//    public static List<String> getPrinter() {
//        List<String> list = new ArrayList<String>();
//        POSPrinterControl113 printer = (jpos.POSPrinterControl113) new POSPrinter();
//CashDrawerControl113 drawer = (CashDrawerControl113) new CashDrawer();
//try {
//        printer.open("POSPrinter");
//        printer.claim(100);
//
//        printer.setDeviceEnabled(true);
//    } catch (Exception e) {
//        System.err.println("Printer deactivated " + e.getMessage());
////        printerdisabled = true;
////        drawerdisabled  = true;
////        return;
//    }
//    try {
//        drawer.open("CashDrawer");
//        drawer.claim(100);
//        drawer.setDeviceEnabled(true);
//    } catch (Exception e) {
//        System.out.println("Cashdrawer deactivated: " + e.getMessage());
////        drawerdisabled = true;
//        //return;
//    }
//        return list;
//    }

//    public static List<String> getPrinter2() {
//        List<String> list = new ArrayList<String>();
//        try{
//            PrinterJob printerJob = PrinterJob.getPrinterJob();
//            PrintService[] services = PrinterJob.lookupPrintServices();
//            //PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
//            PrintService selectedService = null;
//            if(services.length != 0 || services != null)
//            {
//                for(PrintService service : services){
//                    String existingPrinter = service.getName().toLowerCase();
//                    list.add(existingPrinter);
//                }
//            }
//        }catch(Exception ex){
//            System.out.println("GeneralModel getPrinter() JRException: " + ex);
//        }
//        return list;
//    }
//    public static List<String> getPrinter() {
//        List<String> list = new ArrayList<String>();
//        DocFlavor psFlavor = 
//           new DocFlavor("application/postscript", "java.io.OutputStream");
//        StreamPrinter2D[] streamPrinter[] = PrinterServiceLookup.lookupStreamPrinters(psFlavor);
//        if(streamPrinter != null && streamPrinter.length > 0) {
//           StreamPrinter2D sprinter = streamPrinter[0];
//           try {
//              FileOutputStream fos = new FileOutputStream("out.ps");
//              PrinterJob pjob =
//                 PrinterJob.getPrinterJob(sprinter, fos);
//              pjob.setPageable(this);
//              pjob.print();
//              fos.close();
//           } catch (IOException e) {
//           } catch (PrinterException e) {
//           }
//        }
//        return list;
//    }

//    public static List<String> getPrinter2() {
//        List<String> list = new ArrayList<String>();
//        Doc psDoc = new PrintDoc("file.ps");
//        // Gets the format of the document
//        DocFlavor psFlavor = psDoc.getDocFlavor();
//        // Creates a new attribute set
//        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
//        // Prints 5 copies
//        aset.add(new Copies(5));
//        // Specifies the size of the medium
//        aset.add(MediaSize.A4);
//        // Prints two-sided
//        aset.add(Sides.DUPLEX);
//        // Staples the pages
//        aset.add( FinishingsBinding STAPLE);
//        /* Locates printers that can print the specified document format
//         * with the specified attribute set
//         */
//        PrintService[] services =
//        PrintServiceLookup.lookupPrintServices(psFlavor, aset);
//        if (services != null && services.length > 0) {
//           DocPrintJob job = services[0].createPrintJob();
//           try {
//              job.print(psDoc, aset);
//           }catch (PrintException pe) {
//        }
//        }
//        return list;
//    }
    public static List<String> getPrinter2() {
        List<String> list = new ArrayList<String>();
        printerList = list;
     PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);



	        System.out.println("Printer Services found:");
                printerList.add("Printer Services found:");
	        printService(services);



	        // Look up the default print service

	        PrintService service = PrintServiceLookup.lookupDefaultPrintService();



	        if (service!=null) {

	            System.out.println("Default Printer Service found:");
                    printerList.add("Default Printer Service found:");
	            System.out.println("t" + service);
                    printerList.add(service.getName());

	        }



	        // find services that support a particular input format (e.g. JPEG)

	        services = PrintServiceLookup.lookupPrintServices(DocFlavor.INPUT_STREAM.JPEG, null);

	        System.out.println("Printer Services with JPEG support:");
                printerList.add("Printer Services with JPEG support:");

	        printService(services);



	        // find printer service by name

	        AttributeSet aset = new HashAttributeSet();

	        aset.add(new PrinterName("Microsoft XPS Document Writer", null));

	        services = PrintServiceLookup.lookupPrintServices(null, aset);



	        System.out.println("Printer Service Microsoft XPS Document Writer:");
                printerList.add("Printer Service Microsoft XPS Document Writer:");

	        printService(services);


	        // find services that support a set of print job capabilities (e.g. color)

	        aset = new HashAttributeSet();

	        aset.add(ColorSupported.SUPPORTED);

	        services = PrintServiceLookup.lookupPrintServices(null, aset);



	        System.out.println("Printer Services with color support:");
                printerList.add("Printer Services with color support:");
	        printService(services);

                return printerList;

	    }



	    private static void printService(PrintService[] services) {

	        if (services!=null && services.length>0) {

	            for (int i = 0; i < services.length; i++) {

	                System.out.println("t" + services[i]);
                        printerList.add(services[i].getName());

	            }

	        }

	    }



    public static void refreshSystemPrinterList() {

    Class[] classes = PrintServiceLookup.class.getDeclaredClasses();

    for (int i = 0; i < classes.length; i++) {

        if ("javax.print.PrintServiceLookup$Services".equals(classes[i].getName())) {

            sun.awt.AppContext.getAppContext().remove(classes[i]);
            break;
        }
    }
}



//    private static JasperPrint getJasperPrint() {
//            return JasperPrinterCreator.getJasperprint();
//    }


    public java.sql.Date convertToSqlDate(String date) throws ParseException {
        java.sql.Date finalDate = null;
        String strD = date;
        String[] str1 = strD.split("-");
        strD = str1[1] + "/" + str1[0] + "/" + str1[2]; // Format: mm/dd/yy
        finalDate = new java.sql.Date(DateFormat.getDateInstance(DateFormat.SHORT, new Locale("en", "US")).parse(strD).getTime());
        return finalDate;
    }

    public String sendSmsToAssignedFor(String numberStr1, String messageStr1) {
        String result = "";
        try {
            //String query = "SELECT si.user_name, si.user_password, si.sender_id FROM sms_info si ";
//            PreparedStatement pstmt = connection.prepareStatement(query);
//            ResultSet rset = pstmt.executeQuery();
//            rset.next();
            String host_url = "http://login.smsgatewayhub.com/api/mt/SendSMS?";;
//            String user_name = rset.getString("user_name");        // e.g. "jpss1277@gmail.com"
//            String user_password = rset.getString("user_password"); // e.g. "8826887606"
//            String sender_id = java.net.URLEncoder.encode(rset.getString("sender_id"), "UTF-8");         // e.g. "TEST+SMS"
            messageStr1 = java.net.URLEncoder.encode(messageStr1, "UTF-8");
            //String hex = calstr(messageStr1);
                  // e.g. "TEST+SMS"
            //String msg = String.format("%x", new BigInteger(1, messageStr1.getBytes()));
            String queryString = "APIKey=WIOg7OdIzkmYTrqTsw262w&senderid=JPSOFT&channel=2&DCS=8&flashsms=0&number=" + numberStr1 + "&text=" + messageStr1 + "&route=";
            String url = host_url + queryString;
            result = callURL(url);
            System.out.println("SMS URL: " + url);
        } catch (Exception e) {
            result = e.toString();
            System.out.println("SMSModel sendSMS() Error: " + e);
        }
        return result;
    }

    private String callURL(String strURL) {
        String status = "";
        try {
            java.net.URL obj = new java.net.URL(strURL);
            HttpURLConnection httpReq = (HttpURLConnection) obj.openConnection();
            httpReq.setDoOutput(true);
            httpReq.setInstanceFollowRedirects(true);
            httpReq.setRequestMethod("GET");
            status = httpReq.getResponseMessage();
        } catch (MalformedURLException me) {
            status = me.toString();
        } catch (IOException ioe) {
            status = ioe.toString();
        } catch (Exception e) {
            status = e.toString();
        }
        return status;
    }
    
    public String dhex(int str) {
        //Integer a = new Integer(str);
        return Integer.toString(str, 16).toUpperCase();
        //return (str+0).toString().toUpperCase();
    }

   public String calstr (String str){// convert unicode to hexadecimal
	int haut = 0;
	//int n = 0;
	String CPstring = "";
	for (int i = 0; i < str.length(); i++)
	{
		int b = str.charAt(i); 
		if (b < 0 || b > 0xFFFF) {
			CPstring += "Error " + dhex(b) + "!";
		}
		if (haut != 0) {
			if (0xDC00 <= b && b <= 0xDFFF) {
				CPstring += dhex(0x10000 + ((haut - 0xD800) << 10) + (b - 0xDC00)) + ' ';
				haut = 0;
				continue;
				}
			else {
				CPstring += "!erreur " + dhex(haut) + "!";
				haut = 0;
				}
			}
		if (0xD800 <= b && b <= 0xDBFF) {
			haut = b;
			}
		else {
			CPstring += dhex(b) + ' ';
			}
	}
	CPstring = CPstring.substring(0, CPstring.length()-1);
	
	String hex = convertCP2HexNCR(CPstring);
        return hex;
	//var eu = convertHex2EU(hex);
	
//	document.uni.name2.value = convertCP2DecNCR(CPstring);
//	document.uni.name3.value = convertCP2HexNCR(CPstring);
//	document.uni.name4.value = convertCP2UTF8(CPstring);
//	document.uni.name5.value = eu;
}

public String convertCP2HexNCR (String argstr)
{
  String outputString = "";
  argstr = argstr.replace("\\s+", "");
  if (argstr.length() == 0) { return ""; }
  argstr = argstr.replace("\\s+/g", " ");
  String[] listArray = argstr.split(" ");
  for ( int i = 0; i < listArray.length; i++ )
  {
    int n = Integer.parseInt(listArray[i], 16);
//    if(n == 32) // for space
//        outputString += "00" + dhex(n);// + ';';
    if((n == 32 || n >= 65 && n <= 90) || (n >= 97 && n <= 122) || (n >= 48 && n <= 57) || n == 45 || n == 46 || n == 58)
        outputString += "00" + dhex(n);// + ';';
//    else if(n >= 48 && n <= 57)// for numbers
//        outputString += "00" + dhex(n);// + ';';
//    else if(n == 45 || n == 46 || n == 58)// for . - : 
//        outputString += "00" + dhex(n);// + ';';
    else
        outputString += "0" + dhex(n);// + ';';
  }
  return( outputString );
}

    public String createAppropriateDirectories1(String destination_path) {
        // destination_path e.g. "C:/ssadvt_repository/SSADVT/MPWZ/PAYMENT REQUISITIONS/Aug-2014"
        String destPath[] = destination_path.split("/");
        String path = "";
        int length = destPath.length;
        for(int i = 0; i < length; i++)
        {
            if(path.isEmpty())
                path = destPath[i];
            else
                path = path + "/" + destPath[i];
            makeDirectory(path);
        }
        return path;
    }
public List getNodeDetail(){
        List list = new ArrayList();
        String query =  " select node_name "
                       +" from node1 n,node_detail nd "
                       +" where nd.node_id=n.node_id ";
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while(rs.next()){
                GenralBean general = new GenralBean();
              general.setNode_name(rs.getString("node_name"));
                list.add(general);
            }


        }catch(Exception e){
            System.out.println(e);
        }

        return list;
    }

    public String getdestpath(int id)
    {
     String path="";
       String query =  " select * from image_destination where image_destination_id=? and active=? ";
       
        try {
            PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
            ps.setInt(1,id);
            ps.setString(2,"Y");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
            path=rs.getString("destination_path");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(GeneralModel.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    return path;
    }

    public List<imgbean>  generalids(int id)
    {
    List<imgbean> ids=new ArrayList<imgbean>();
    
      String query =  " select * from general_image_details where key_person_id=? and active=? ";
        try {
            PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
            ps.setInt(1, (id));
            ps.setString(2,"Y");
            ResultSet rs=ps.executeQuery();
        while(rs.next()){
            String imgname=rs.getString("image_name");
            int destid=rs.getInt("image_destination_id");
            String type="";
            if(destid == 1)
            {
              type="keyperson";
            }
            else
            {
                type="id";
            }
            String path=getdestpath(destid);
            
            imgbean im =new imgbean();
            im.setImgname(imgname);
            im.setDestinationpath(path);
            im.setKpid(id);
            im.setType(type);
            ids.add(im);
            
        }
        } catch (SQLException ex) {
            Logger.getLogger(GeneralModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    return ids;
    }


    public int getCityId(String name)
    {
    int id=0;
    
    String query =  " select * from city where city_name=? and active=? ";
    
    PreparedStatement ps;
        try {
            ps = (PreparedStatement) connection.prepareStatement(query);
            ps.setString(1,name);
             ps.setString(2,"Y");
              ResultSet rs=ps.executeQuery();
        while(rs.next()){
           id=rs.getInt("city_id");
        }
             
        } catch (SQLException ex) {
            Logger.getLogger(GeneralModel.class.getName()).log(Level.SEVERE, null, ex);
        }
             
    
    return id;
    }
    
    public List<CityBean> getZoneData(String cityname)
    {
     List<CityBean> lst=new ArrayList<CityBean>();
     int cityid=getCityId(cityname);

      String query =  " select * from zone where city_id=? and active=? ";
    
    PreparedStatement ps;
        try {
            ps = (PreparedStatement) connection.prepareStatement(query);
            ps.setInt(1,cityid);
             ps.setString(2,"Y");
              ResultSet rs=ps.executeQuery();
        while(rs.next()){
              CityBean cb=new CityBean();
              cb.setId(rs.getInt("zone_id"));
              cb.setName(rs.getString("zone_name"));
              cb.setForeignid(rs.getInt("city_id"));
              
              lst.add(cb);
        }
             
        } catch (SQLException ex) {
            Logger.getLogger(GeneralModel.class.getName()).log(Level.SEVERE, null, ex);
        }         
    
    return lst;
    }
    public JSONArray getZonArray(String cityname)
    {
        JSONArray jsonArray = new JSONArray();
 
     int cityid=getCityId(cityname);

      String query =  " select * from zone where city_id=? and active=? ";
    
    PreparedStatement ps;
        try {
            ps = (PreparedStatement) connection.prepareStatement(query);
            ps.setInt(1,cityid);
             ps.setString(2,"Y");
              ResultSet rs=ps.executeQuery();
        while(rs.next()){
 
              
              JSONObject jsnobject = new JSONObject();
              
                try {
                    jsnobject.put("id", rs.getInt("zone_id"));
                    jsnobject.put("name", rs.getString("zone_name"));
                    jsnobject.put("foreignid", rs.getInt("city_id"));
                    jsonArray.put(jsnobject);
                } catch (JSONException ex) {
                    Logger.getLogger(GeneralModel.class.getName()).log(Level.SEVERE, null, ex);
                }
               
     
        }
             
        } catch (SQLException ex) {
            Logger.getLogger(GeneralModel.class.getName()).log(Level.SEVERE, null, ex);
        }         
    
    return jsonArray;
    }
    
     public JSONArray getIdArray()
    {
        JSONArray jsonArray = new JSONArray();
 

      String query =  " select * from id_type where  active=? ";
    
    PreparedStatement ps;
        try {
            ps = (PreparedStatement) connection.prepareStatement(query);
           
             ps.setString(1,"Y");
              ResultSet rs=ps.executeQuery();
        while(rs.next()){
 
              
              JSONObject jsnobject = new JSONObject();
              
                try {
                    jsnobject.put("id", rs.getInt("id_type_id"));
                    jsnobject.put("name", rs.getString("id_type"));
                   
                    jsonArray.put(jsnobject);
                } catch (JSONException ex) {
                    Logger.getLogger(GeneralModel.class.getName()).log(Level.SEVERE, null, ex);
                }
               
     
        }
             
        } catch (SQLException ex) {
            Logger.getLogger(GeneralModel.class.getName()).log(Level.SEVERE, null, ex);
        }         
    
    return jsonArray;
    }
     
      public JSONArray getCityArray()
    {
        JSONArray jsonArray = new JSONArray();
 
    

      String query =  " select * from city where  active=? ";
    
    PreparedStatement ps;
        try {
            ps = (PreparedStatement) connection.prepareStatement(query);
           
             ps.setString(1,"Y");
              ResultSet rs=ps.executeQuery();
        while(rs.next()){
 
              
              JSONObject jsnobject = new JSONObject();
              
                try {
                    jsnobject.put("id", rs.getInt("city_id"));
                    jsnobject.put("name", rs.getString("city_name"));
                   
                    jsonArray.put(jsnobject);
                } catch (JSONException ex) {
                    Logger.getLogger(GeneralModel.class.getName()).log(Level.SEVERE, null, ex);
                }
               
     
        }
             
        } catch (SQLException ex) {
            Logger.getLogger(GeneralModel.class.getName()).log(Level.SEVERE, null, ex);
        }         
    
    return jsonArray;
    }
      
      
      public JSONArray getOrderStatusArray()
    {
        JSONArray jsonArray = new JSONArray();
 
    

      String query =  " select * from order_status  ";
    
    PreparedStatement ps;
        try {
            ps = (PreparedStatement) connection.prepareStatement(query);
           
            
              ResultSet rs=ps.executeQuery();
        while(rs.next()){
 
              
              JSONObject jsnobject = new JSONObject();
              
                try {
                    jsnobject.put("id", rs.getInt("order_id"));
                    jsnobject.put("name", rs.getString("order_status"));
                   
                    jsonArray.put(jsnobject);
                } catch (JSONException ex) {
                    Logger.getLogger(GeneralModel.class.getName()).log(Level.SEVERE, null, ex);
                }
               
     
        }
             
        } catch (SQLException ex) {
            Logger.getLogger(GeneralModel.class.getName()).log(Level.SEVERE, null, ex);
        }         
    
    return jsonArray;
    }
      
       public JSONArray getOrgTypeArray()
    {
        JSONArray jsonArray = new JSONArray();
 
    

      String query =  " select * from organisation_type where  active=? and parent_org_id=?";
    
    PreparedStatement ps;
        try {
            ps = (PreparedStatement) connection.prepareStatement(query);
           
             ps.setString(1,"Y");
             ps.setInt(2,0);
              ResultSet rs=ps.executeQuery();
        while(rs.next()){
 
              
              JSONObject jsnobject = new JSONObject();
              
                try {
                    jsnobject.put("orgtypeid", rs.getInt("organisation_type_id"));
                    jsnobject.put("orgtypename", rs.getString("org_type_name"));
                   
                    jsonArray.put(jsnobject);
                } catch (JSONException ex) {
                    Logger.getLogger(GeneralModel.class.getName()).log(Level.SEVERE, null, ex);
                }
               
     
        }
             
        } catch (SQLException ex) {
            Logger.getLogger(GeneralModel.class.getName()).log(Level.SEVERE, null, ex);
        }         
    
    return jsonArray;
    }
       
       
       
       
       
       public JSONArray getDesigArray(String name)
    {
        String random=name;
        List <String> list= getdesiglist(name,0);
        
        JSONArray jsonArray = new JSONArray();
 
    for ( String designame:list)
    {
        
      int id=getdesigid(designame);
      
      JSONObject jsnobject = new JSONObject();
              
                try {
                    jsnobject.put("desigid", id);
                    jsnobject.put("designame", designame);
                   
                    jsonArray.put(jsnobject);
                } catch (JSONException ex) {
                    Logger.getLogger(GeneralModel.class.getName()).log(Level.SEVERE, null, ex);
                }
      
    }

     
    
    return jsonArray;
    }
       
       
       
        public JSONArray getOrgTypeArray(int id)
    {
     JSONArray jsonArray = new JSONArray();
     
 String query =  " select * from organisation_name where organisation_id=? and active=? ";
    
    PreparedStatement ps;
        try {
            ps = (PreparedStatement) connection.prepareStatement(query);
            ps.setInt(1,id);
             ps.setString(2,"Y");
              ResultSet rs=ps.executeQuery();
        while(rs.next()){
                
            
            
              int orgid=rs.getInt("organisation_type_id");
              int parentid=1;
              
              while(parentid != 0)
              {
              
              String query2 =  " select * from organisation_type where organisation_type_id=? and active=? ";
            
              PreparedStatement ps2 = (PreparedStatement) connection.prepareStatement(query2);
            
               ps2.setInt(1,orgid);
              ps2.setString(2,"Y");
              ResultSet rs2=ps2.executeQuery();
             while(rs2.next()){
                 
                 parentid=rs2.getInt("parent_org_id");
                 
                 if(parentid == 0)
                 {
               JSONObject jsnobject = new JSONObject();
              
                try {
                    jsnobject.put("id", rs2.getInt("organisation_type_id"));
                    jsnobject.put("orgtypename", rs2.getString("org_type_name"));
                  
                    jsonArray.put(jsnobject);
                } catch (JSONException ex) {
                    Logger.getLogger(GeneralModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                 }
                 else
                 {
                 orgid=parentid;
                 }
              
            }
              
              
              }
        }
             
        } catch (SQLException ex) {
            Logger.getLogger(GeneralModel.class.getName()).log(Level.SEVERE, null, ex);
        }         
    
    return jsonArray;
    }
       
        
        
        
    public List<CityBean> getBuildingData(int id)
    {
     List<CityBean> lst=new ArrayList<CityBean>();
     

      String query =  " select * from building_table where city_location_id=? and active=? ";
    
    PreparedStatement ps;
        try {
            ps = (PreparedStatement) connection.prepareStatement(query);
            ps.setInt(1,id);
             ps.setString(2,"Y");
              ResultSet rs=ps.executeQuery();
        while(rs.next()){
              CityBean cb=new CityBean();
              cb.setId(rs.getInt("id"));
              cb.setName(rs.getString("building_name"));
              cb.setForeignid(rs.getInt("city_location_id"));
              
              lst.add(cb);
        }
             
        } catch (SQLException ex) {
            Logger.getLogger(GeneralModel.class.getName()).log(Level.SEVERE, null, ex);
        }         
    
    return lst;
    }

        
        
         public JSONArray getOrgNameArray(int id)
    {
     JSONArray jsonArray = new JSONArray();
     

      String query =  " select * from organisation_name where organisation_id=? and active=? ";
    
    PreparedStatement ps;
        try {
            ps = (PreparedStatement) connection.prepareStatement(query);
            ps.setInt(1,id);
             ps.setString(2,"Y");
              ResultSet rs=ps.executeQuery();
        while(rs.next()){
                          
               JSONObject jsnobject = new JSONObject();
              
                try {
                    jsnobject.put("id", rs.getInt("organisation_id"));
                    jsnobject.put("orgname", rs.getString("organisation_name"));
                    jsnobject.put("orgtype", rs.getInt("organisation_type_id"));
                    jsonArray.put(jsnobject);
                } catch (JSONException ex) {
                    Logger.getLogger(GeneralModel.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
             
        } catch (SQLException ex) {
            Logger.getLogger(GeneralModel.class.getName()).log(Level.SEVERE, null, ex);
        }         
    
    return jsonArray;
    }
       
       
       
       
       
       
        public JSONArray getKeyPersonArray(int id)
    {
        JSONArray jsonArray = new JSONArray();
 
    

      String query =  " select * from key_person where  active=? and org_office_id=?";
    
    PreparedStatement ps;
        try {
            ps = (PreparedStatement) connection.prepareStatement(query);
           
             ps.setString(1,"Y");
             ps.setInt(2,id);
              ResultSet rs=ps.executeQuery();
        while(rs.next()){
 
              
              JSONObject jsnobject = new JSONObject();
              
                try {
                    jsnobject.put("id", rs.getInt("key_person_id"));
                    jsnobject.put("name", rs.getString("key_person_name"));
                    jsnobject.put("officeid", rs.getInt("org_office_id"));
                    jsnobject.put("city_id", rs.getInt("city_id"));
                    jsnobject.put("address", rs.getString("address_line1"));
                    jsnobject.put("mobile", rs.getString("mobile_no1"));
                    jsnobject.put("landline", rs.getString("landline_no1"));
                    jsnobject.put("email", rs.getString("email_id1"));
                   jsnobject.put("fathername", rs.getString("father_name"));
                   jsnobject.put("dob", rs.getString("date_of_birth"));
                   jsnobject.put("lat", rs.getString("latitude"));
                   jsnobject.put("long", rs.getString("longitude"));
                   jsnobject.put("bloodgroup", rs.getString("bloodgroup"));
                   jsnobject.put("emergencyname", rs.getString("emergency_contact_name"));
                   jsnobject.put("emergencynumber", rs.getString("emergency_contact_mobile"));
                   jsnobject.put("gender", rs.getString("gender"));
                   jsnobject.put("idnumber", rs.getString("id_no"));
                   jsnobject.put("typeid", rs.getInt("id_type_id"));
                   
                   jsnobject.put("desigid", rs.getInt("designation_id"));
                   jsnobject.put("relation", rs.getString("relation"));
                       jsnobject.put("family_office", rs.getInt("family_office"));
                           jsnobject.put("family_designation", rs.getString("family_designation"));
                   
                    jsonArray.put(jsnobject);
                } catch (JSONException ex) {
                    Logger.getLogger(GeneralModel.class.getName()).log(Level.SEVERE, null, ex);
                }
               
     
        }
             
        } catch (SQLException ex) {
            Logger.getLogger(GeneralModel.class.getName()).log(Level.SEVERE, null, ex);
        }         
    
    return jsonArray;
    }
        
        
        
           public JSONArray getKeyPersonfamilyArray(int id)
    {
        JSONArray jsonArray = new JSONArray();
 
    

      String query =  " select * from key_person where  active=? and family_office=?";
    
    PreparedStatement ps;
        try {
            ps = (PreparedStatement) connection.prepareStatement(query);
           
             ps.setString(1,"Y");
             ps.setInt(2,id);
              ResultSet rs=ps.executeQuery();
        while(rs.next()){
 
              
              JSONObject jsnobject = new JSONObject();
              
                try {
                    jsnobject.put("id", rs.getInt("key_person_id"));
                    jsnobject.put("name", rs.getString("key_person_name"));
                    jsnobject.put("officeid", rs.getInt("org_office_id"));
                    jsnobject.put("city_id", rs.getInt("city_id"));
                    jsnobject.put("address", rs.getString("address_line1"));
                    jsnobject.put("mobile", rs.getString("mobile_no1"));
                    jsnobject.put("landline", rs.getString("landline_no1"));
                    jsnobject.put("email", rs.getString("email_id1"));
                   jsnobject.put("fathername", rs.getString("father_name"));
                   jsnobject.put("dob", rs.getString("date_of_birth"));
                   jsnobject.put("lat", rs.getString("latitude"));
                   jsnobject.put("long", rs.getString("longitude"));
                   jsnobject.put("bloodgroup", rs.getString("bloodgroup"));
                   jsnobject.put("emergencyname", rs.getString("emergency_contact_name"));
                   jsnobject.put("emergencynumber", rs.getString("emergency_contact_mobile"));
                   jsnobject.put("gender", rs.getString("gender"));
                   jsnobject.put("idnumber", rs.getString("id_no"));
                   jsnobject.put("typeid", rs.getInt("id_type_id"));
                   
                   jsnobject.put("desigid", rs.getInt("designation_id"));
                   jsnobject.put("relation", rs.getString("relation"));
                       jsnobject.put("family_office", rs.getInt("family_office"));
                           jsnobject.put("family_designation", rs.getString("family_designation"));
                   
                    jsonArray.put(jsnobject);
                } catch (JSONException ex) {
                    Logger.getLogger(GeneralModel.class.getName()).log(Level.SEVERE, null, ex);
                }
               
     
        }
             
        } catch (SQLException ex) {
            Logger.getLogger(GeneralModel.class.getName()).log(Level.SEVERE, null, ex);
        }         
    
    return jsonArray;
    }
    
        
        
    
    public JSONArray getWardArray(int id)
    {
     JSONArray jsonArray = new JSONArray();
     

      String query =  " select * from ward where zone_id=? and active=? ";
    
    PreparedStatement ps;
        try {
            ps = (PreparedStatement) connection.prepareStatement(query);
            ps.setInt(1,id);
             ps.setString(2,"Y");
              ResultSet rs=ps.executeQuery();
        while(rs.next()){
                          
               JSONObject jsnobject = new JSONObject();
              
                try {
                    jsnobject.put("id", rs.getInt("ward_id"));
                    jsnobject.put("name", rs.getString("ward_name"));
                    jsnobject.put("foreignid", rs.getInt("zone_id"));
                    jsonArray.put(jsnobject);
                } catch (JSONException ex) {
                    Logger.getLogger(GeneralModel.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
             
        } catch (SQLException ex) {
            Logger.getLogger(GeneralModel.class.getName()).log(Level.SEVERE, null, ex);
        }         
    
    return jsonArray;
    }
    
    public JSONArray getAreaArray(int id)
    {
     JSONArray jsonArray = new JSONArray();
     

      String query =  " select * from area where ward_id=? and active=? ";
    
    PreparedStatement ps;
        try {
            ps = (PreparedStatement) connection.prepareStatement(query);
            ps.setInt(1,id);
             ps.setString(2,"Y");
              ResultSet rs=ps.executeQuery();
        while(rs.next()){
             
              
                JSONObject jsnobject = new JSONObject();
              
                try {
                    jsnobject.put("id", rs.getInt("area_id"));
                    jsnobject.put("name", rs.getString("area_name"));
                    jsnobject.put("foreignid", rs.getInt("ward_id"));
                    jsonArray.put(jsnobject);
                } catch (JSONException ex) {
                    Logger.getLogger(GeneralModel.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
             
        } catch (SQLException ex) {
            Logger.getLogger(GeneralModel.class.getName()).log(Level.SEVERE, null, ex);
        }         
    
    return jsonArray;
    }
    
     public JSONArray getCityLocationAray(int id)
    {
     JSONArray jsonArray = new JSONArray();
     

      String query =  " select * from city_location where area_id=? and active=? ";
    
    PreparedStatement ps;
        try {
            ps = (PreparedStatement) connection.prepareStatement(query);
            ps.setInt(1,id);
             ps.setString(2,"Y");
              ResultSet rs=ps.executeQuery();
        while(rs.next()){
              
              
              JSONObject jsnobject = new JSONObject();
              
                try {
                    jsnobject.put("id", rs.getInt("city_location_id"));
                    jsnobject.put("name", rs.getString("location"));
                    jsnobject.put("foreignid", rs.getInt("area_id"));
                    jsonArray.put(jsnobject);
                } catch (JSONException ex) {
                    Logger.getLogger(GeneralModel.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
             
        } catch (SQLException ex) {
            Logger.getLogger(GeneralModel.class.getName()).log(Level.SEVERE, null, ex);
        }         
    
    return jsonArray;
    }
    
    
     public List<CityBean> getWardData(int id)
    {
     List<CityBean> lst=new ArrayList<CityBean>();
     

      String query =  " select * from ward where zone_id=? and active=? ";
    
    PreparedStatement ps;
        try {
            ps = (PreparedStatement) connection.prepareStatement(query);
            ps.setInt(1,id);
             ps.setString(2,"Y");
              ResultSet rs=ps.executeQuery();
        while(rs.next()){
              CityBean cb=new CityBean();
              cb.setId(rs.getInt("ward_id"));
              cb.setName(rs.getString("ward_name"));
              cb.setForeignid(rs.getInt("zone_id"));
              
              lst.add(cb);
        }
             
        } catch (SQLException ex) {
            Logger.getLogger(GeneralModel.class.getName()).log(Level.SEVERE, null, ex);
        }         
    
    return lst;
    }
    
    public List<CityBean> getAreaData(int id)
    {
     List<CityBean> lst=new ArrayList<CityBean>();
     

      String query =  " select * from area where ward_id=? and active=? ";
    
    PreparedStatement ps;
        try {
            ps = (PreparedStatement) connection.prepareStatement(query);
            ps.setInt(1,id);
             ps.setString(2,"Y");
              ResultSet rs=ps.executeQuery();
        while(rs.next()){
              CityBean cb=new CityBean();
              cb.setId(rs.getInt("area_id"));
              cb.setName(rs.getString("area_name"));
              cb.setForeignid(rs.getInt("ward_id"));
              
              lst.add(cb);
        }
             
        } catch (SQLException ex) {
            Logger.getLogger(GeneralModel.class.getName()).log(Level.SEVERE, null, ex);
        }         
    
    return lst;
    }
    
    public List<CityBean> getCityLocationData(int id)
    {
     List<CityBean> lst=new ArrayList<CityBean>();
     

      String query =  " select * from city_location where area_id=? and active=? ";
    
    PreparedStatement ps;
        try {
            ps = (PreparedStatement) connection.prepareStatement(query);
            ps.setInt(1,id);
             ps.setString(2,"Y");
              ResultSet rs=ps.executeQuery();
        while(rs.next()){
              CityBean cb=new CityBean();
              cb.setId(rs.getInt("city_location_id"));
              cb.setName(rs.getString("location"));
              cb.setForeignid(rs.getInt("area_id"));
              
              lst.add(cb);
        }
             
        } catch (SQLException ex) {
            Logger.getLogger(GeneralModel.class.getName()).log(Level.SEVERE, null, ex);
        }         
    
    return lst;
    }

public String  getLatLang(String node_name){
       String data="";
       String query =  " select head_latitude,head_longitude,tail_lattitude,tail_longitude "
                       +" from node_detail nd,node1 n "
                       +" where nd.node_id=n.node_id "
                       +" and n.node_name='"+node_name+"'";
       try{
           ResultSet rs = connection.prepareStatement(query).executeQuery();
           while(rs.next()){
              String head_latitude=rs.getString("head_latitude");
              String head_longitude=rs.getString("head_longitude");
              String tail_lattitude=rs.getString("tail_lattitude");
              String tail_longitude=rs.getString("tail_longitude");
              data=head_latitude+","+head_longitude+","+tail_lattitude+","+tail_longitude;
           }


       }catch(Exception e){
           System.out.println(e);
       }

      return data;
   }
    public boolean makeDirectory(String dirPathName) {
        boolean result = false;
        File directory = new File(dirPathName);
        if (!directory.exists()) {
            result = directory.mkdir();
        }
        return result;
    }
    
    
     public KeyPerson authorize(String number,String password)
    {
        
        
    KeyPerson b2=null;
        String query = "select * from  key_person  where mobile_no1=? and active=? and password=?";
        try {
             PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
             ps.setString(1,number);
             ps.setString(2,"Y");
             ps.setString(3, password);
             ResultSet rs=ps.executeQuery();
        while(rs.next()){
            KeyPerson b=new KeyPerson();
            b.setMobile_no1(rs.getString("mobile_no1"));
            b.setIsVarified(rs.getString("isVarified"));
            b.setKey_person_id(rs.getInt("key_person_id"));
            b.setRevision_no(rs.getInt("revision_no"));
            b.setKey_person_name(rs.getString("key_person_name"));
            b.setOrg_office_id(rs.getInt("org_office_id"));
            b.setCity_id(rs.getInt("city_id"));
            b.setAddress_line1(rs.getString("address_line1"));
            b.setEmail_id1(rs.getString("email_id1"));
            b.setDesignation_id(rs.getInt("designation_id"));
            b.setFather_name(rs.getString("father_name"));
            b.setDate_of_birth(rs.getString("date_of_birth"));
            b.setLatitude(rs.getString("latitude"));
            b.setLongitude(rs.getString("longitude"));
            b.setFamilyofiiceid(rs.getInt("family_office"));
            
            b2=b;
           // message = "Record saved successfully.";
           // msgBgColor = COLOR_OK;
        } 
        } catch (Exception e) {
           //  message = "Cannot save the record, some error.";
           // msgBgColor = COLOR_ERROR;
            System.out.println("error in Check Existing - " + e);
        }
        return b2;
    }
     
      public String getDestination_Path(String image_uploaded_for){
     String destination_path = "";
     String query = " SELECT destination_path FROM image_destination id, image_uploaded_for  iuf "
             + " WHERE id.image_uploaded_for_id = iuf.image_uploaded_for_id "
             + " AND iuf.image_uploaded_for = '" + image_uploaded_for + "' ";//traffic_police
     try{
         ResultSet rs = connection.prepareStatement(query).executeQuery();
         if(rs.next())
             destination_path = rs.getString("destination_path");
     }catch(Exception ex){
         System.out.println("ERROR: in getTrafficPoliceId in TraffiPoliceSearchModel : " + ex);
     }
     return destination_path;
  }
    
     public int getOrganisationType_id(String office_type) {
        String query = "SELECT organisation_id FROM organisation_name WHERE  organisation_name = ?  and active=? ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
          //  pstmt.setString(1, office_code);
            pstmt.setString(1, office_type);
             pstmt.setString(2, "Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("organisation_id");
        } catch (Exception e) {
            System.out.println("Error: OrganisationMapModel--" + e);
        }
        return organisation_id;
    }
      
     public Org_Office checkExistingOffice(String number)
    {
        
        int orgnameid=getOrganisationType_id("Family");
    Org_Office b2=null;
        String query = "select * from org_office  where mobile_no1=? and active=?";
        try {
             PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
             ps.setString(1,number);
             ps.setString(2,"Y");
             ResultSet rs=ps.executeQuery();
        while(rs.next()){
            if(rs.getInt("organisation_id") != orgnameid)
            {
            Org_Office b=new Org_Office();
            b.setMobile_no1(rs.getString("mobile_no1"));
            b.setOrg_office_id(rs.getInt("org_office_id"));
            b.setOrg_office_name(rs.getString("org_office_name"));
            b.setRevision_no(rs.getInt("revision_no"));
            b.setCity_id(rs.getInt("city_id"));
            b.setAddress_line1(rs.getString("address_line1"));
             b.setOrganisation_id(rs.getInt("organisation_id"));
             b.setLatitude(rs.getString("latitude"));
             b.setLongitude(rs.getString("longitude"));
            
            
            b2=b;
            }
           // message = "Record saved successfully.";
           // msgBgColor = COLOR_OK;
        } 
        } catch (Exception e) {
           //  message = "Cannot save the record, some error.";
           // msgBgColor = COLOR_ERROR;
            System.out.println("error in Check Existing - " + e);
        }
        return b2;
    }
     
     public Org_Office getFamilyOffice(String number)
    {
        
        int orgnameid=getOrganisationType_id("Family");
    Org_Office b2=null;
        String query = "select * from org_office  where mobile_no1=? and active=?";
        try {
             PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
             ps.setString(1,number);
             ps.setString(2,"Y");
             ResultSet rs=ps.executeQuery();
        while(rs.next()){
            if(rs.getInt("organisation_id") == orgnameid)
            {
            Org_Office b=new Org_Office();
            b.setMobile_no1(rs.getString("mobile_no1"));
            b.setOrg_office_id(rs.getInt("org_office_id"));
            b.setOrg_office_name(rs.getString("org_office_name"));
            b.setRevision_no(rs.getInt("revision_no"));
            b.setCity_id(rs.getInt("city_id"));
            b.setAddress_line1(rs.getString("address_line1"));
             b.setOrganisation_id(rs.getInt("organisation_id"));
             b.setLatitude(rs.getString("latitude"));
             b.setLongitude(rs.getString("longitude"));
            
            
            b2=b;
            }
           // message = "Record saved successfully.";
           // msgBgColor = COLOR_OK;
        } 
        } catch (Exception e) {
           //  message = "Cannot save the record, some error.";
           // msgBgColor = COLOR_ERROR;
            System.out.println("error in Check Existing - " + e);
        }
        return b2;
    }
     
     
      public Org_Office getOfficeByID(int  number)
    {
        
        
    Org_Office b2=null;
        String query = "select * from org_office  where org_office_id=? and active=?";
        try {
             PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
             ps.setInt(1,number);
             ps.setString(2,"Y");
             ResultSet rs=ps.executeQuery();
        while(rs.next()){
            Org_Office b=new Org_Office();
            b.setMobile_no1(rs.getString("mobile_no1"));
            b.setOrg_office_id(rs.getInt("org_office_id"));
            b.setOrg_office_name(rs.getString("org_office_name"));
            b.setRevision_no(rs.getInt("revision_no"));
            b.setCity_id(rs.getInt("city_id"));
            b.setAddress_line1(rs.getString("address_line1"));
             b.setOrganisation_id(rs.getInt("organisation_id"));
             b.setLatitude(rs.getString("latitude"));
             b.setLongitude(rs.getString("longitude"));
          
            
            b2=b;
           // message = "Record saved successfully.";
           // msgBgColor = COLOR_OK;
        } 
        } catch (Exception e) {
           //  message = "Cannot save the record, some error.";
           // msgBgColor = COLOR_ERROR;
            System.out.println("error in Check Existing - " + e);
        }
        return b2;
    }
     
     
     
     
      public KeyPerson checkExisting(String number)
    {
        
        
    KeyPerson b2=null;
        
        String query = "select * from key_person  where mobile_no1=? and active=?";
        try {
             PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
             ps.setString(1,number);
             ps.setString(2,"Y");
             ResultSet rs=ps.executeQuery();
        while(rs.next()){
            KeyPerson b=new KeyPerson();
            b.setMobile_no1(rs.getString("mobile_no1"));
            b.setIsVarified(rs.getString("isVarified"));
            b.setKey_person_id(rs.getInt("key_person_id"));
            b.setRevision_no(rs.getInt("revision_no"));
            b.setKey_person_name(rs.getString("key_person_name"));
            b.setOrg_office_id(rs.getInt("org_office_id"));
            b.setCity_id(rs.getInt("city_id"));
            b.setAddress_line1(rs.getString("address_line1"));
            b.setEmail_id1(rs.getString("email_id1"));
            b.setDesignation_id(rs.getInt("designation_id"));
            b.setFather_name(rs.getString("father_name"));
            b.setDate_of_birth(rs.getString("date_of_birth"));
            b.setLatitude(rs.getString("latitude"));
            b.setLongitude(rs.getString("longitude"));
            b.setFamilyofiiceid(rs.getInt("family_office"));
            b2=b;
           // message = "Record saved successfully.";
           // msgBgColor = COLOR_OK;
        } 
        } catch (Exception e) {
           //  message = "Cannot save the record, some error.";
           // msgBgColor = COLOR_ERROR;
            System.out.println("error in Check Existing - " + e);
        }
        return b2;
    }
    
    public boolean insertimgdetails(String imagename,int destid,int kpid)
    {
    Boolean status=false;
       String query = "insert into general_image_details(image_name,image_destination_id,key_person_id) values(?,?,?)";
     
        try {
            PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
            ps.setString(1,imagename);
            ps.setInt(2,destid);
            ps.setInt(3,kpid);
            int rowsAffected=ps.executeUpdate();
        if (rowsAffected > 0) {
             status=true;
        
        }
        } catch (SQLException ex) {
            Logger.getLogger(GeneralModel.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    
    
    return status;
    }
    

    public boolean insertRecord(MobileDao propertiesBean)
    {
        
        
    boolean b=false;
        String query = "insert into key_person (key_person_name,mobile_no1,remark,email_id1,gender,password,bloodgroup,isVarified,emergency_contact_name,emergency_contact_mobile,latitude,longitude,city_id,address_line1,org_office_id,designation_id,father_name,date_of_birth,relation,family_office,family_designation) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
             PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
             ps.setString(1,propertiesBean.getName());
             ps.setString(2,propertiesBean.getMobilenumber());
             ps.setString(3,propertiesBean.getRemark());
             ps.setString(4,propertiesBean.getEmailid());
             ps.setString(5,propertiesBean.getGender());
             ps.setString(6,propertiesBean.getPassword());
             ps.setString(7,propertiesBean.getBloodgroup());
             ps.setString(8,"No");
             ps.setString(9,propertiesBean.getEmergencyContactName());
             ps.setString(10,propertiesBean.getEmergencyMobileNo());
             ps.setString(11,propertiesBean.getLatitude());
             ps.setString(12,propertiesBean.getLongitude());
             ps.setInt(13,propertiesBean.getCityid());
             ps.setString(14,propertiesBean.getAddress());
             if(propertiesBean.getOfficeid() == 0)
             {
             ps.setString(15,null);
             }
             else
             {
              ps.setInt(15,propertiesBean.getOfficeid());
             }
              if(propertiesBean.getDesigid() == 0)
             {
             ps.setString(16,null);
             }
             else
             {
              ps.setInt(16,propertiesBean.getDesigid());
             }
            
            
             
             
             ps.setString(17,propertiesBean.getFathername());
             ps.setString(18,propertiesBean.getDob());
             ps.setString(19,propertiesBean.getRelation());
             ps.setInt(20,propertiesBean.getFamilyoffice());
             ps.setInt(21,propertiesBean.getFamilydesig());
             int rowsAffected=ps.executeUpdate();
        if (rowsAffected > 0) {
            b= true;
           // message = "Record saved successfully.";
           // msgBgColor = COLOR_OK;
        } else {
           // message = "Cannot save the record, some error.";
          //  msgBgColor = COLOR_ERROR;
        }
        } catch (Exception e) {
           //  message = "Cannot save the record, some error.";
           // msgBgColor = COLOR_ERROR;
            System.out.println("properties ERROR inside propertiesModel - " + e);
        }
        return b;
    }
    
      public boolean UpdateRecord(String number,int id)
    {
        boolean b=false;
        String query = "update key_person set isVarified='Yes' where mobile_no1="+number+" and key_person_id=? and active='Y'";
        try {
             PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
             ps.setInt(1,id);
             int rowsAffected=ps.executeUpdate();
             if (rowsAffected > 0) {
            message = "Record Update successfully.";
            b = true;
           
        } else {
            message = "Cannot update the record, some error.";
            
        }
        } catch (Exception e) {
             message = "Cannot update the record, some error.";
            
            System.out.println("UpdateRecord ERROR UpdateRecord - " + e);
        }
        
        return b;
    }
     
     public boolean UpdateKp(int id,int typeid,String idnumber)
    {
        boolean b=false;
        String query = "update key_person set id_type_id=?,id_no=?  where key_person_id=? and active=?";
        try {
             PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
             ps.setInt(1, typeid);
             ps.setString(2,idnumber);
             ps.setInt(3, id);
             ps.setString(4,"Y");
             
             int rowsAffected=ps.executeUpdate();
             if (rowsAffected > 0) {
            message = "Record Update successfully.";
            b = true;
           
        } else {
            message = "Cannot update the record, some error.";
            
        }
        } catch (Exception e) {
             message = "Cannot update the record, some error.";
            
            System.out.println("UpdateRecord ERROR UpdateRecord - " + e);
        }
        
        return b;
    }

public static String random(int size) {
        StringBuilder generatedToken = new StringBuilder();
        try {
            SecureRandom number = SecureRandom.getInstance("SHA1PRNG");
            // Generate 20 integers 0..20
            for (int i = 0; i < size; i++) {
                generatedToken.append(number.nextInt(9));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return generatedToken.toString();
    }
    

   public int getdesigid(String emp_code) {
       int i =0;
      String query = " select * from designation  where designation=? and active=? ";
         try {
            PreparedStatement pstmt = connection.prepareStatement(query);
              pstmt.setString(1, emp_code);
              pstmt.setString(2,"Y");
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {

                i = rset.getInt("designation_id");

            }

        } catch (Exception ex) {
            System.out.println("Error: UploadBillImageModel-getimage_destination_id-" + ex);
        }
       
       return i;
   }



 public int getOrgtype_id(String emp_code) {
        String query;
        int key_person_id = 0;
         query = " select om.organisation_id,ot.organisation_type_id from org_office oo,organisation_name om ,organisation_type ot " +
                 " where org_office_name='"+emp_code+"' and om.organisation_id=oo.organisation_id and om.organisation_type_id=ot.organisation_type_id and oo.active='Y' "
                + "  and om.active='Y' and ot.active='Y'";
              
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {

                key_person_id = rset.getInt("organisation_type_id");

            }

        } catch (Exception ex) {
            System.out.println("Error: UploadBillImageModel-getimage_destination_id-" + ex);
        }
        return key_person_id;
    }




 public List<String> getdesiglist(String name,int parent_check)
       { 
        
         List<String> list1=null;
//         list=null;
        String org_type ="";
        int org_type_id=0;
        
        if (parent_check == 0){
         org_type_id = getOrgtype_id(name);
         
        }else{
       org_type_id = parent_check;
        }

        int parent_org_id=0;

        String query = "select ot.org_type_name,ot.organisation_type_id,ot.parent_org_id,d.designation" +
                       " from organisation_type ot," +
                       " designation d, designation_organisation_type_map dotm" +
                       " where ot.organisation_type_id = "+org_type_id+" and dotm.organisation_type_id = ot.organisation_type_id and dotm.designation_id = d.designation_id" +
                       " and ot.active='Y' and dotm.active='Y' and d.active='Y' ;";
            
            
        try     {    
           PreparedStatement pstmt = connection.prepareStatement(query);
            int count = 0;
           
       
           ResultSet rset = pstmt.executeQuery();
           
           
           
             while (rset.next()) {    // move cursor from BOR to valid record.                 
     
                 org_type = (rset.getString("org_type_name"));
                 parent_org_id = (rset.getInt("parent_org_id"));
                String designation = rset.getString("designation");
                if(parent_org_id==0)
                {

                    count++;
                    
            }
                 list.add(designation);
                    count++;
             }
             
            if (count == 0) {
       
                parent_check = getParentName(org_type_id);
                if(parent_check!=0)
                {
                getdesiglist(name,parent_check);
                }
                else
                {
          
               list1 = getDesgn(name,parent_check);
               list = list1;
                }
         
            }
             
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--getDesignationList()-- " + e);
        }
        
        return list;
        
     
       }




 public int getParentName(int code) {
     int  parent_org_id=0;
  
            String query = "select ot.parent_org_id from organisation_type ot where organisation_type_id="+code;
        
        try     {    
           PreparedStatement pstmt = connection.prepareStatement(query);
            int count = 0;
           
        
           ResultSet rset = pstmt.executeQuery();
           
           
           
             while (rset.next()) {                    
                 parent_org_id = (rset.getInt("parent_org_id"));
                    count++;

             }
            if (count == 0) {
                System.out.println("No such Designation exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--getDesignationList()-- " + e);
        }
        return parent_org_id;
    }
       
        public List<String> getDesgn(String code,int parent_check ) {
        List<String> list = new ArrayList<String>();
 
        String query1 = "  select  o.organisation_name,d.designation from organisation_name o,org_office oft,organisation_designation od,designation d "
                        + "  where oft.org_office_name='"+code+"' and od.organisation_id=o.organisation_id and "
                        + " d.designation_id=od.organisation_designation_map_id_1 and "                   
                        + "  oft.organisation_id=o.organisation_id and o.active='Y' and oft.active='Y' and od.active='Y' and d.active='Y'  ";
            
        
        try     {    
           PreparedStatement pstmt = connection.prepareStatement(query1);
            int count = 0;
           
       
           ResultSet rset = pstmt.executeQuery();
           
           
           
             while (rset.next()) {    // move cursor from BOR to valid record.
                 
                String designation = (rset.getString("designation"));
              
                    list.add(designation);
                    count++;
                   
            }
            if (count == 0) {
                list.add("No such Designation exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--getDesignationList()-- " + e);
        }
        return list;
    }











    
    
    
    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("CityModel closeConnection: " + e);
        }
    }

    public void setConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("CityModel setConnection error: " + e);
        }
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
