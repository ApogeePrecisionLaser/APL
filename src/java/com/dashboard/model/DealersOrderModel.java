package com.dashboard.model;

import com.dashboard.bean.DealersOrder;
import com.dashboard.bean.Enquiry;
import static com.dashboard.model.EnquiryModel.timeAgo;
import com.google.gson.Gson;
import com.inventory.tableClasses.Indent;
import com.organization.model.OrganisationTypeModel;
import com.organization.tableClasses.OrganisationType;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DealersOrderModel {

    static private Connection connection;
    private String driver, url, user, password;
    static private String message, messageBGColor = "#a2a220";
    static private final String COLOR_OK = "green";
    static private final String COLOR_ERROR = "red";
    int item_id = 0;
    int item_id1 = 0;
    int order_table_id = 0;
    private File tmpDir;
//    static final String SAVE_DIR = "C:\\ssadvt\\";
//    static final int BUFFER_SIZE = 4096;
    private static final String INSTANCE_ID = "5";
    private static final String CLIENT_ID = "chandansingh23396@gmail.com";
    private static final String CLIENT_SECRET = "3ae49e46cfe748bb83c39b5a47594353";

    private static String GATEWAY_URL_FOR_SENDING_MESSAGE_TO_SINGLE_USER = "http://api.whatsmate.net/v1/telegram/single/message/" + INSTANCE_ID;
    private static String GATEWAY_URL_FOR_SENDING_MESSAGE_TO_MULTIPLE_USERS = "http://api.whatsmate.net/v1/telegram/batch/message/" + INSTANCE_ID;

    private static String GATEWAY_URL_FOR_SENDING_IMAGES_TO_SINGLE_USER = "http://api.whatsmate.net/v1/telegram/single/photo/binary/" + INSTANCE_ID;
    private static String GATEWAY_URL_FOR_SENDING_IMAGES_TO_MULTIPLE_USERS = "http://api.whatsmate.net/v1/telegram/batch/photo/binary/" + INSTANCE_ID;

    private static String GATEWAY_URL_FOR_SENDING_DOCUMENT_TO_SINGLE_USER = "http://api.whatsmate.net/v1/telegram/single/document/binary/" + INSTANCE_ID;
    private static String GATEWAY_URL_FOR_SENDING_DOCUMENT_TO_MULTIPLE_USERS = "http://api.whatsmate.net/v1/telegram/batch/document/binary/" + INSTANCE_ID;

    private static String GATEWAY_URL_FOR_SENDING_AUDIO_TO_SINGLE_USER = "http://api.whatsmate.net/v1/telegram/single/audio/binary/" + INSTANCE_ID;
    private static String GATEWAY_URL_FOR_SENDING_AUDIO_TO_MULTIPLE_USERS = "http://api.whatsmate.net/v1/telegram/batch/audio/binary/" + INSTANCE_ID;

    private static String GATEWAY_URL_FOR_SENDING_LOCATION_TO_SINGLE_USER = "http://api.whatsmate.net/v1/telegram/single/location/" + INSTANCE_ID;
    private static String GATEWAY_URL_FOR_SENDING_LOCATION_TO_MULTIPLE_USERS = "http://api.whatsmate.net/v1/telegram/batch/location/" + INSTANCE_ID;

    private String[] numbers;
    private static String caption;
    private String image;
    private static String filename;
    private String document;
    private static String audio;
    private double latitude;
    private double longitude;
    String output;

    ArrayList<String> arr = new ArrayList<String>();

    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("DealersOrderModel setConnection() Error: " + e);
        }
    }

    public static ArrayList<DealersOrder> getAllItems(int logged_org_office_id, String search_item) {
        ArrayList<DealersOrder> list = new ArrayList<DealersOrder>();

        try {
//            String query = " select itn.item_name "
//                    + " from item_names itn, manufacturer_item_map mim,model m,item_authorization ia,designation d,manufacturer mr,"
//                    + " item_image_details iid "
//                    + " where itn.active='Y' and mim.active='Y' and m.active='Y' and d.active='Y' and mr.active='Y' and iid.active='Y' "
//                    + " and iid.model_id=m.model_id and mr.manufacturer_id=mim.manufacturer_id "
//                    + " and ia.active='Y' and itn.item_names_id= mim.item_names_id and mim.manufacturer_item_map_id=m.manufacturer_item_map_id"
//                    + " and ia.item_names_id=itn.item_names_id and "
//                    + " d.designation_id=ia.designation_id ";

            String query = " select itn.item_name  from item_names itn, manufacturer_item_map mim,model m,item_authorization ia, "
                    + " designation d,manufacturer mr, "
                    + " item_image_details iid,dealer_item_map dim,org_office oo  where itn.active='Y' and mim.active='Y' "
                    + " and m.active='Y' and d.active='Y' and mr.active='Y' and iid.active='Y' "
                    + " and iid.model_id=m.model_id and mr.manufacturer_id=mim.manufacturer_id  and ia.active='Y' "
                    + " and itn.item_names_id= mim.item_names_id and dim.active='Y' and oo.active='Y' "
                    + " and mim.manufacturer_item_map_id=m.manufacturer_item_map_id and ia.item_names_id=itn.item_names_id and "
                    + " d.designation_id=ia.designation_id  and dim.org_office_id=oo.org_office_id "
                    + " and dim.item_authorization_id=ia.item_authorization_id "
                    + " and oo.org_office_id='" + logged_org_office_id + "' ";

            if (!search_item.equals("") && search_item != null) {
                query += " and itn.item_name='" + search_item + "' ";
            }
            query += " group by itn.item_name ";

            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                DealersOrder bean = new DealersOrder();
                String item_name = rst.getString("item_name");
                bean.setItem_name(item_name);
                list.add(bean);
            }
        } catch (Exception e) {
            System.err.println("Exception------------" + e);
        }

        return list;
    }

    public static ArrayList<DealersOrder> getAllModels(int logged_org_office_id, List<DealersOrder> list2) {
        ArrayList<DealersOrder> list = new ArrayList<DealersOrder>();
        if (list2.size() > 0) {
            for (int i = 0; i < list2.size(); i++) {
                try {

//                    String query = " select itn.item_name,mr.manufacturer_name,m.model,m.model_id,iid.image_path,iid.image_name,m.description "
//                            + " ,m.basic_price,inv.stock_quantity "
//                            + " from item_names itn, manufacturer_item_map mim,model m,item_authorization ia,designation d,manufacturer mr,"
//                            + " item_image_details iid,inventory_basic ib,inventory inv,org_office oo "
//                            + " where itn.active='Y' and mim.active='Y' and m.active='Y' and d.active='Y' and mr.active='Y' and iid.active='Y' "
//                            + " and iid.model_id=m.model_id and mr.manufacturer_id=mim.manufacturer_id and ib.active='Y' and inv.active='Y' "
//                            + " and ia.active='Y' and itn.item_names_id= mim.item_names_id and mim.manufacturer_item_map_id=m.manufacturer_item_map_id"
//                            + " and ia.item_names_id=itn.item_names_id and ib.item_names_id=itn.item_names_id and ib.model_id=m.model_id "
//                            + " and ib.inventory_basic_id=inv.inventory_basic_id and oo.active='Y' and oo.org_office_id=ib.org_office_id and "
//                            + " d.designation_id=ia.designation_id ";
                    String query = " select itn.item_name,mr.manufacturer_name,m.model,m.model_id,iid.image_path,iid.image_name,m.description "
                            + " ,m.basic_price,inv.stock_quantity  from item_names itn, manufacturer_item_map mim,model m,item_authorization ia, "
                            + " designation d,manufacturer mr,"
                            + " item_image_details iid,inventory_basic ib,inventory inv,org_office oo,dealer_item_map dim  "
                            + " where itn.active='Y' and mim.active='Y' and m.active='Y' and d.active='Y' and mr.active='Y' "
                            + " and iid.active='Y'  and iid.model_id=m.model_id and mr.manufacturer_id=mim.manufacturer_id and ib.active='Y' "
                            + " and inv.active='Y'  and ia.active='Y' "
                            + " and itn.item_names_id= mim.item_names_id and mim.manufacturer_item_map_id=m.manufacturer_item_map_id "
                            + " and ia.item_names_id=itn.item_names_id and ib.item_names_id=itn.item_names_id "
                            + " and ib.model_id=m.model_id  and ib.inventory_basic_id=inv.inventory_basic_id and oo.active='Y'"
                            + "  and  d.designation_id=ia.designation_id "
                            + " and dim.org_office_id=oo.org_office_id and dim.active='Y' and dim.model_id=m.model_id "
                            + " and dim.item_authorization_id=ia.item_authorization_id and oo.org_office_id='" + logged_org_office_id + "' ";

                    query += " and itn.item_name='" + list2.get(i).getItem_name() + "' group by m.model";

                    ResultSet rst = connection.prepareStatement(query).executeQuery();
                    while (rst.next()) {
                        DealersOrder bean = new DealersOrder();
                        String manufacturer_name = rst.getString("manufacturer_name");
                        String model = rst.getString("model");
                        int model_id = rst.getInt("model_id");
                        String image_path = rst.getString("image_path");
                        String image_name = rst.getString("image_name");
                        String basic_price = rst.getString("basic_price");
                        String stock_quantity = rst.getString("stock_quantity");
                        bean.setItem_name(rst.getString("item_name"));
                        bean.setManufacturer_name(manufacturer_name);
                        bean.setModel(model);
                        bean.setModel_id(model_id);
                        bean.setImage_path(image_path);
                        bean.setImage_name(image_name);
                        bean.setBasic_price(basic_price);
                        bean.setStock_quantity(stock_quantity);
                        list.add(bean);

                    }

                } catch (Exception e) {
                    System.err.println("Exception------------" + e);
                }

            }
        }
        return list;
    }

    public ArrayList<DealersOrder> getAllModel(int logged_org_office_id, String item_name, String search_model) {
        ArrayList<DealersOrder> list = new ArrayList<DealersOrder>();

        try {

            String query = " select itn.item_name,mr.manufacturer_name,m.model,m.model_id,iid.image_path,iid.image_name,m.description "
                    + " ,m.basic_price,inv.stock_quantity  from item_names itn, manufacturer_item_map mim,model m,item_authorization ia, "
                    + " designation d,manufacturer mr,"
                    + " item_image_details iid,inventory_basic ib,inventory inv,org_office oo,dealer_item_map dim  "
                    + " where itn.active='Y' and mim.active='Y' and m.active='Y' and d.active='Y' and mr.active='Y' "
                    + " and iid.active='Y'  and iid.model_id=m.model_id and mr.manufacturer_id=mim.manufacturer_id and ib.active='Y' "
                    + " and inv.active='Y'  and ia.active='Y' "
                    + " and itn.item_names_id= mim.item_names_id and mim.manufacturer_item_map_id=m.manufacturer_item_map_id "
                    + " and ia.item_names_id=itn.item_names_id and ib.item_names_id=itn.item_names_id "
                    + " and ib.model_id=m.model_id  and ib.inventory_basic_id=inv.inventory_basic_id and oo.active='Y'"
                    + "  and  d.designation_id=ia.designation_id "
                    + " and dim.org_office_id=oo.org_office_id and dim.active='Y' and dim.model_id=m.model_id "
                    + " and dim.item_authorization_id=ia.item_authorization_id and oo.org_office_id='" + logged_org_office_id + "' ";

            if (!search_model.equals("") && search_model != null) {
                query += " and m.model='" + search_model + "' ";
            }

            query += " and itn.item_name='" + item_name + "' group by m.model";

            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                DealersOrder bean = new DealersOrder();
                String manufacturer_name = rst.getString("manufacturer_name");
                String model = rst.getString("model");
                int model_id = rst.getInt("model_id");
                String image_path = rst.getString("image_path");
                String image_name = rst.getString("image_name");
                String basic_price = rst.getString("basic_price");
                String stock_quantity = rst.getString("stock_quantity");
                bean.setItem_name(rst.getString("item_name"));
                bean.setManufacturer_name(manufacturer_name);
                bean.setModel(model);
                bean.setModel_id(model_id);
                bean.setImage_path(image_path);
                bean.setImage_name(image_name);
                bean.setBasic_price(basic_price);
                bean.setStock_quantity(stock_quantity);
                list.add(bean);

            }

        } catch (Exception e) {
            System.err.println("Exception------------" + e);

        }
        return list;
    }

    public ArrayList<DealersOrder> getAllDetails(String model_id, int logged_org_office_id) {
        ArrayList<DealersOrder> list = new ArrayList<DealersOrder>();

        try {

//            String query = " select itn.item_name,mr.manufacturer_name,m.model,m.model_id,iid.image_path,iid.image_name,m.description,m.basic_price "
//                    + " from item_names itn, manufacturer_item_map mim,model m,item_authorization ia,designation d,manufacturer mr,"
//                    + " item_image_details iid "
//                    + " where itn.active='Y' and mim.active='Y' and m.active='Y' and d.active='Y' and mr.active='Y' and iid.active='Y' "
//                    + " and iid.model_id=m.model_id and mr.manufacturer_id=mim.manufacturer_id "
//                    + " and ia.active='Y' and itn.item_names_id= mim.item_names_id and mim.manufacturer_item_map_id=m.manufacturer_item_map_id"
//                    + " and ia.item_names_id=itn.item_names_id and "
//                    + " d.designation_id=ia.designation_id ";
//
//            query += " and m.model_id='" + model_id + "' group by m.model";
            String query = " select itn.item_name,mr.manufacturer_name,m.model,m.model_id,iid.image_path,iid.image_name,m.description "
                    + " ,m.basic_price,inv.stock_quantity "
                    + " from item_names itn, manufacturer_item_map mim,model m,item_authorization ia,designation d,manufacturer mr,"
                    + " item_image_details iid,inventory_basic ib,inventory inv,org_office oo "
                    + " where itn.active='Y' and mim.active='Y' and m.active='Y' and d.active='Y' and mr.active='Y' and iid.active='Y' "
                    + " and iid.model_id=m.model_id and mr.manufacturer_id=mim.manufacturer_id and ib.active='Y' and inv.active='Y' "
                    + " and ia.active='Y' and itn.item_names_id= mim.item_names_id and mim.manufacturer_item_map_id=m.manufacturer_item_map_id"
                    + " and ia.item_names_id=itn.item_names_id and ib.item_names_id=itn.item_names_id and ib.model_id=m.model_id "
                    + " and ib.inventory_basic_id=inv.inventory_basic_id and oo.active='Y' and oo.org_office_id=ib.org_office_id and "
                    + " d.designation_id=ia.designation_id ";
            query += " and m.model_id='" + model_id + "' "
                    + " group by m.model ";

            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                DealersOrder bean = new DealersOrder();
                String manufacturer_name = rst.getString("manufacturer_name");
                String model = rst.getString("model");
//                int model_id = rst.getInt("model_id");
                String image_path = rst.getString("image_path");
                String image_name = rst.getString("image_name");
                String basic_price = rst.getString("basic_price");
                String stock_quantity = rst.getString("stock_quantity");
                bean.setItem_name(rst.getString("item_name"));
                bean.setManufacturer_name(manufacturer_name);
                bean.setModel(model);
                bean.setModel_id(rst.getInt("model_id"));
                bean.setDescription(rst.getString("description"));
                bean.setImage_path(image_path);
                bean.setImage_name(image_name);
                bean.setBasic_price(basic_price);
                bean.setStock_quantity(stock_quantity);
                list.add(bean);
            }

        } catch (Exception e) {
            System.err.println("Exception------------" + e);

        }
        return list;
    }

    public ArrayList<DealersOrder> getAllSimilarProducts(String model_id, int logged_org_office_id) {
        ArrayList<DealersOrder> list = new ArrayList<DealersOrder>();

        try {
            int item_id = getItemId(model_id);
            String query = " select itn.item_name,mr.manufacturer_name,m.model,m.model_id,iid.image_path,iid.image_name,m.description "
                    + " ,m.basic_price,inv.stock_quantity  from item_names itn, manufacturer_item_map mim,model m,item_authorization ia, "
                    + " designation d,manufacturer mr,"
                    + " item_image_details iid,inventory_basic ib,inventory inv,org_office oo,dealer_item_map dim  "
                    + " where itn.active='Y' and mim.active='Y' and m.active='Y' and d.active='Y' and mr.active='Y' "
                    + " and iid.active='Y'  and iid.model_id=m.model_id and mr.manufacturer_id=mim.manufacturer_id and ib.active='Y' "
                    + " and inv.active='Y'  and ia.active='Y' "
                    + " and itn.item_names_id= mim.item_names_id and mim.manufacturer_item_map_id=m.manufacturer_item_map_id "
                    + " and ia.item_names_id=itn.item_names_id and ib.item_names_id=itn.item_names_id "
                    + " and ib.model_id=m.model_id  and ib.inventory_basic_id=inv.inventory_basic_id and oo.active='Y'"
                    + "  and  d.designation_id=ia.designation_id "
                    + " and dim.org_office_id=oo.org_office_id and dim.active='Y' and dim.model_id=m.model_id "
                    + " and dim.item_authorization_id=ia.item_authorization_id and oo.org_office_id='" + logged_org_office_id + "'  ";
            query += " and itn.item_names_id='" + item_id + "' and m.model_id!='" + model_id + "' group by m.model ";

            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                DealersOrder bean = new DealersOrder();
                String manufacturer_name = rst.getString("manufacturer_name");
                String model = rst.getString("model");
                String image_path = rst.getString("image_path");
                String image_name = rst.getString("image_name");
                String basic_price = rst.getString("basic_price");
                String stock_quantity = rst.getString("stock_quantity");
                bean.setItem_name(rst.getString("item_name"));
                bean.setManufacturer_name(manufacturer_name);
                bean.setModel(model);
                bean.setModel_id(rst.getInt("model_id"));
//                bean.setDescription(rst.getString("description"));
                bean.setImage_path(image_path);
                bean.setImage_name(image_name);
                bean.setBasic_price(basic_price);
                bean.setStock_quantity(stock_quantity);
                list.add(bean);
            }
        } catch (Exception e) {
            System.err.println("Exception------------" + e);

        }

        return list;
    }

    public ArrayList<DealersOrder> getAllImages(List<DealersOrder> list2) {
        ArrayList<DealersOrder> list = new ArrayList<DealersOrder>();
        if (list2.size() > 0) {
            for (int i = 0; i < list2.size(); i++) {
                try {

                    String query = " select iid.image_path,iid.image_name "
                            + " from item_names itn, manufacturer_item_map mim,model m,item_authorization ia,designation d,manufacturer mr,"
                            + " item_image_details iid "
                            + " where itn.active='Y' and mim.active='Y' and m.active='Y' and d.active='Y' and mr.active='Y' and iid.active='Y' "
                            + " and iid.model_id=m.model_id and mr.manufacturer_id=mim.manufacturer_id "
                            + " and ia.active='Y' and itn.item_names_id= mim.item_names_id and mim.manufacturer_item_map_id=m.manufacturer_item_map_id"
                            + " and ia.item_names_id=itn.item_names_id and "
                            + " d.designation_id=ia.designation_id ";
                    query += " and m.model_id='" + list2.get(i).getModel_id() + "' group by iid.image_name ";

                    ResultSet rst = connection.prepareStatement(query).executeQuery();
                    while (rst.next()) {
                        DealersOrder bean = new DealersOrder();
                        String image_path = rst.getString("image_path");
                        String image_name = rst.getString("image_name");
                        bean.setImage_path(image_path);
                        bean.setImage_name(image_name);
                        list.add(bean);
                    }
                } catch (Exception e) {
                    System.err.println("Exception------------" + e);

                }
            }
        }
        return list;
    }

    public List<String> getItemName(String q, String logged_designation) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT itn.item_name FROM item_names itn,item_authorization ia,designation d where "
                + " itn.active='Y' and d.active='Y' and ia.active='Y' and d.designation_id=ia.designation_id "
                + " and ia.item_names_id=itn.item_names_id   ";
        if (!logged_designation.equals("") && logged_designation != null) {
            query += " and d.designation='" + logged_designation + "' ";
        }
        query += " group by itn.item_name ORDER BY itn.item_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String item_name = (rset.getString("item_name"));
                if (item_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(item_name);
                    count++;
                }
            }

            if (count == 0) {
                list.add("No such item_name  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:DealersOrderModel--getItemName()-- " + e);
        }
        return list;
    }

    public List<String> getModelName(String q, String logged_designation, String search_item) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT m.model "
                + " FROM item_names itn,model m,manufacturer_item_map mim,manufacturer mr "
                + " where  itn.active='Y' and m.active='Y' and mr.active='Y' and mim.active='Y' "
                + " and itn.item_names_id=mim.item_names_id "
                + " and mim.manufacturer_item_map_id=m.manufacturer_item_map_id and mr.manufacturer_id=mim.manufacturer_id ";

        if (!search_item.equals("") && search_item != null) {
            query += " and itn.item_name='" + search_item + "' ";
        }
        query += " group by m.model ORDER BY m.model ";
        try {

            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String model = (rset.getString("model"));
                if (model.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(model);
                    count++;
                }
            }

            if (count == 0) {
                list.add("No such model  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:DealersOrderModel--getModelName()-- " + e);
        }
        return list;
    }

    public List<String> getPaymentMode(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT payment_type FROM payment_type where active='Y' group by payment_type order by payment_type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String payment_type = (rset.getString("payment_type"));
                if (payment_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(payment_type);
                    count++;
                }
            }

            if (count == 0) {
                list.add("No such payment_type  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:DealersOrderModel--getPaymentMode()-- " + e);
        }
        return list;
    }

    public int getItemId(String model_id) {

        String query = "SELECT itn.item_names_id FROM item_names itn,manufacturer mr,manufacturer_item_map mim,model m "
                + " WHERE itn.active='Y' and mr.active='Y' and mim.active='Y' and m.active='Y' and itn.item_names_id=mim.item_names_id "
                + " and mr.manufacturer_id=mim.manufacturer_id and m.manufacturer_item_map_id=mim.manufacturer_item_map_id ";
        if (!model_id.equals("") && model_id != null) {
            query += " and m.model_id='" + model_id + "' ";
        }
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("item_names_id");
        } catch (Exception e) {
            System.out.println("DealersOrderModel getItemId Error: " + e);
        }
        return id;
    }

    public int addToCart(DealersOrder bean, int logged_key_person_id) throws SQLException {
        String query = "INSERT INTO cart_table(key_person_id,model_id,item_names_id,"
                + " cart_status_id,quantity,price,active,description,revision_no,remark) "
                + " VALUES(?,?,?,?,?,?,?,?,?,?) ";
        int rowsAffected = 0;
        int updateRowsAffected = 0;
        int item_id = getItemId(String.valueOf(bean.getModel_id()));
        int count = 0;
        int old_quantity = 0;

        int revision = DealersOrderModel.getRevisionno(bean, logged_key_person_id, item_id);
        String query2 = " select quantity from cart_table where key_person_id='" + logged_key_person_id + "' "
                + " and model_id='" + bean.getModel_id() + "' "
                + " and item_names_id='" + item_id + "' and cart_status_id=1 and active='Y' ";

        try {
            PreparedStatement pstmt1 = connection.prepareStatement(query2);
            ResultSet rs1 = pstmt1.executeQuery();
            while (rs1.next()) {
                old_quantity = rs1.getInt("quantity");
            }

            if (old_quantity > 0) {
                String query1 = "SELECT max(revision_no) as revision_no FROM cart_table WHERE key_person_id='" + logged_key_person_id + "' "
                        + " and model_id='" + bean.getModel_id() + "' "
                        + " and item_names_id='" + item_id + "' and cart_status_id=1 and active='Y' ";

                String query_update = " UPDATE cart_table SET active =? WHERE key_person_id=? and model_id=? and item_names_id=? "
                        + " and cart_status_id=? and revision_no=? ";

                PreparedStatement pstmt = connection.prepareStatement(query1);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    revision = rs.getInt("revision_no");

                    PreparedStatement pstm = connection.prepareStatement(query_update);
                    pstm.setString(1, "N");
                    pstm.setInt(2, logged_key_person_id);
                    pstm.setInt(3, bean.getModel_id());
                    pstm.setInt(4, item_id);
                    pstm.setInt(5, 1);
                    pstm.setInt(6, revision);

                    updateRowsAffected = pstm.executeUpdate();
                    if (updateRowsAffected >= 1) {
                        revision = rs.getInt("revision_no") + 1;
                        PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query);
                        psmt.setInt(1, logged_key_person_id);
                        psmt.setInt(2, bean.getModel_id());
                        psmt.setInt(3, item_id);
                        psmt.setInt(4, 1);
                        psmt.setInt(5, old_quantity + bean.getQuantity());
                        psmt.setString(6, bean.getBasic_price());
                        psmt.setString(7, "Y");
                        psmt.setString(8, "");
                        psmt.setInt(9, 0);
                        psmt.setString(10, "");
                        rowsAffected = psmt.executeUpdate();
                    }
                }

            } else {
                PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, logged_key_person_id);
                pstmt.setInt(2, bean.getModel_id());
                pstmt.setInt(3, item_id);
                pstmt.setInt(4, 1);
                pstmt.setInt(5, bean.getQuantity());
                pstmt.setString(6, bean.getBasic_price());
                pstmt.setString(7, "Y");
                pstmt.setString(8, "");
                pstmt.setInt(9, 0);
                pstmt.setString(10, "");
                rowsAffected = pstmt.executeUpdate();
            }

            if (rowsAffected > 0) {
                message = "Record saved successfully.";
                messageBGColor = COLOR_OK;
            } else {
                message = "Cannot save the record, some error.";
                messageBGColor = COLOR_ERROR;

            }
        } catch (Exception e) {
            System.err.println("Exception---" + e);
        }

        return rowsAffected;
    }

    public int removeFromcart(DealersOrder bean, int logged_key_person_id) throws SQLException {
        String query = " INSERT INTO cart_table(key_person_id,model_id,item_names_id,"
                + " cart_status_id,quantity,price,active,description,revision_no,remark) "
                + " VALUES(?,?,?,?,?,?,?,?,?,?) ";
        int rowsAffected = 0;
        int updateRowsAffected = 0;
        int item_id = getItemId(String.valueOf(bean.getModel_id()));
        int count = 0;
        int old_quantity = 0;

        int revision = DealersOrderModel.getRevisionno(bean, logged_key_person_id, item_id);
        String query2 = " select quantity from cart_table where key_person_id='" + logged_key_person_id + "' "
                + " and model_id='" + bean.getModel_id() + "' "
                + " and item_names_id='" + item_id + "' and cart_status_id=1 and active='Y' ";

        try {
            PreparedStatement pstmt1 = connection.prepareStatement(query2);
            ResultSet rs1 = pstmt1.executeQuery();
            while (rs1.next()) {
                old_quantity = rs1.getInt("quantity");
            }

            if (old_quantity > 0) {
                String query1 = "SELECT max(revision_no) as revision_no FROM cart_table WHERE key_person_id='" + logged_key_person_id + "' "
                        + " and model_id='" + bean.getModel_id() + "' "
                        + " and item_names_id='" + item_id + "' and cart_status_id=1 and active='Y' ";

                String query_update = " UPDATE cart_table SET active =? WHERE key_person_id=? and model_id=? and item_names_id=? "
                        + " and cart_status_id=? and revision_no=? ";

                PreparedStatement pstmt = connection.prepareStatement(query1);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    revision = rs.getInt("revision_no");

                    PreparedStatement pstm = connection.prepareStatement(query_update);
                    pstm.setString(1, "N");
                    pstm.setInt(2, logged_key_person_id);
                    pstm.setInt(3, bean.getModel_id());
                    pstm.setInt(4, item_id);
                    pstm.setInt(5, 1);
                    pstm.setInt(6, revision);

                    updateRowsAffected = pstm.executeUpdate();
                    if (updateRowsAffected >= 1) {
                        revision = rs.getInt("revision_no") + 1;
                        PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query);
                        psmt.setInt(1, logged_key_person_id);
                        psmt.setInt(2, bean.getModel_id());
                        psmt.setInt(3, item_id);
                        psmt.setInt(4, 1);
                        psmt.setInt(5, old_quantity - bean.getQuantity());
                        psmt.setString(6, bean.getBasic_price());
                        psmt.setString(7, "Y");
                        psmt.setString(8, "");
                        psmt.setInt(9, 0);
                        psmt.setString(10, "");
                        rowsAffected = psmt.executeUpdate();
                    }
                }

            } else {
                PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, logged_key_person_id);
                pstmt.setInt(2, bean.getModel_id());
                pstmt.setInt(3, item_id);
                pstmt.setInt(4, 1);
                pstmt.setInt(5, bean.getQuantity());
                pstmt.setString(6, bean.getBasic_price());
                pstmt.setString(7, "Y");
                pstmt.setString(8, "");
                pstmt.setInt(9, 0);
                pstmt.setString(10, "");
                rowsAffected = pstmt.executeUpdate();
            }

            if (rowsAffected > 0) {
                message = "Record saved successfully.";
                messageBGColor = COLOR_OK;
            } else {
                message = "Cannot save the record, some error.";
                messageBGColor = COLOR_ERROR;

            }
        } catch (Exception e) {
            System.err.println("Exception---" + e);
        }

        return rowsAffected;
    }

    public int removeAllFromcart(DealersOrder bean, int logged_key_person_id) throws SQLException {
        int rowsAffected = 0;
        int updateRowsAffected = 0;
        int item_id = getItemId(String.valueOf(bean.getModel_id()));
        int count = 0;
        int old_quantity = 0;

        int revision = DealersOrderModel.getRevisionno(bean, logged_key_person_id, item_id);

        try {
            String query1 = "SELECT max(revision_no) as revision_no FROM cart_table WHERE key_person_id='" + logged_key_person_id + "' "
                    + " and model_id='" + bean.getModel_id() + "' "
                    + " and item_names_id='" + item_id + "' and cart_status_id=1 and active='Y' ";

            String query_update = " UPDATE cart_table SET active =? WHERE key_person_id=? and model_id=? and item_names_id=? "
                    + " and cart_status_id=? and revision_no=? ";

            PreparedStatement pstmt = connection.prepareStatement(query1);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                revision = rs.getInt("revision_no");

                PreparedStatement pstm = connection.prepareStatement(query_update);
                pstm.setString(1, "N");
                pstm.setInt(2, logged_key_person_id);
                pstm.setInt(3, bean.getModel_id());
                pstm.setInt(4, item_id);
                pstm.setInt(5, 1);
                pstm.setInt(6, revision);
                updateRowsAffected = pstm.executeUpdate();
            }

            if (rowsAffected > 0) {
                message = "Record saved successfully.";
                messageBGColor = COLOR_OK;
            } else {
                message = "Cannot save the record, some error.";
                messageBGColor = COLOR_ERROR;

            }
        } catch (Exception e) {
            System.err.println("Exception---" + e);
        }

        return rowsAffected;
    }

    public static int getRevisionno(DealersOrder bean, int logged_key_person_id, int item_id) {
        int revision = 0;
        try {

            String query = " SELECT max(revision_no) as revision_no FROM cart_table where "
                    + "  key_person_id='" + logged_key_person_id + "' "
                    + " and model_id='" + bean.getModel_id() + "' "
                    + " and item_names_id='" + item_id + "' and cart_status_id=1 and active='Y' ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
            System.err.println("DealersOrderModel getRevisionno error--------" + e);
        }
        return revision;
    }

    public ArrayList<DealersOrder> viewCart(int logged_key_person_id) {
        ArrayList<DealersOrder> list = new ArrayList<DealersOrder>();

        try {
            String query = " select ct.cart_table_id,itn.item_name,m.model,mr.manufacturer_name,ct.price,ct.quantity,iid.image_path, "
                    + " iid.image_name,cs.cart_status,m.model_id "
                    + " from item_names itn,model m,key_person kp,cart_table ct, "
                    + " cart_status cs,manufacturer mr, "
                    + " manufacturer_item_map mim,item_image_details iid where itn.active='Y' and iid.active='Y' "
                    + " and iid.model_id=m.model_id and iid.model_id=ct.model_id "
                    + " and m.active='Y' and kp.active='Y' and ct.active='Y' and mr.active='Y' and mim.active='Y' "
                    + " and itn.item_names_id=ct.item_names_id "
                    + " and itn.item_names_id=mim.item_names_id and m.model_id=ct.model_id and ct.key_person_id=kp.key_person_id "
                    + " and ct.cart_status_id=cs.cart_status_id "
                    + " and mim.manufacturer_item_map_id=m.manufacturer_item_map_id and mr.manufacturer_id=mim.manufacturer_id "
                    + " and cs.cart_status_id=1 ";
            if (logged_key_person_id != 0) {
                query += " and kp.key_person_id='" + logged_key_person_id + "' ";
            }
            query += " group by m.model order by m.model ";

            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                DealersOrder bean = new DealersOrder();
                bean.setItem_name(rst.getString("item_name"));
                bean.setModel(rst.getString("model"));
                bean.setModel_id(rst.getInt("model_id"));
                bean.setManufacturer_name(rst.getString("manufacturer_name"));
                bean.setBasic_price(rst.getString("price"));
                bean.setQuantity(rst.getInt("quantity"));
                bean.setImage_path(rst.getString("image_path"));
                bean.setImage_name(rst.getString("image_name"));
                bean.setCart_table_id(rst.getInt("cart_table_id"));
                bean.setCart_status(rst.getString("cart_status"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.err.println("Exception------------" + e);
        }

        return list;
    }

    public ArrayList<DealersOrder> getLastAddedProduct(int logged_key_person_id) {
        ArrayList<DealersOrder> list = new ArrayList<DealersOrder>();

        try {
            String query = " select ct.cart_table_id,itn.item_name,m.model,mr.manufacturer_name,ct.price,ct.quantity,iid.image_path, "
                    + " iid.image_name,cs.cart_status,m.model_id "
                    + " from item_names itn,model m,key_person kp,cart_table ct, "
                    + " cart_status cs,manufacturer mr, "
                    + " manufacturer_item_map mim,item_image_details iid where itn.active='Y' and iid.active='Y' "
                    + " and iid.model_id=m.model_id and iid.model_id=ct.model_id "
                    + " and m.active='Y' and kp.active='Y' and ct.active='Y' and mr.active='Y' and mim.active='Y' "
                    + " and itn.item_names_id=ct.item_names_id "
                    + " and itn.item_names_id=mim.item_names_id and m.model_id=ct.model_id and ct.key_person_id=kp.key_person_id "
                    + " and ct.cart_status_id=cs.cart_status_id "
                    + " and mim.manufacturer_item_map_id=m.manufacturer_item_map_id and mr.manufacturer_id=mim.manufacturer_id "
                    + " and cs.cart_status_id=1 ";
            if (logged_key_person_id != 0) {
                query += " and kp.key_person_id='" + logged_key_person_id + "' ";
            }

            query += " group by m.model order by  ct.cart_table_id desc limit 1 ";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                DealersOrder bean = new DealersOrder();
                bean.setItem_name(rst.getString("item_name"));
                bean.setModel(rst.getString("model"));
                bean.setModel_id(rst.getInt("model_id"));
                bean.setManufacturer_name(rst.getString("manufacturer_name"));
                bean.setBasic_price(rst.getString("price"));
                bean.setQuantity(rst.getInt("quantity"));
                bean.setImage_path(rst.getString("image_path"));
                bean.setImage_name(rst.getString("image_name"));
                bean.setCart_table_id(rst.getInt("cart_table_id"));
                bean.setCart_status(rst.getString("cart_status"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.err.println("Exception------------" + e);
        }

        return list;
    }

    public int getCurrentQuantity(String model_id, int logged_key_person_id) {
        int current_qty = 0;
        try {
            int item_id = getItemId(model_id);

            String query = " SELECT quantity FROM cart_table WHERE key_person_id='" + logged_key_person_id + "' "
                    + " and model_id='" + model_id + "' "
                    + " and item_names_id='" + item_id + "' and cart_status_id=1 and active='Y' ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                current_qty = rset.getInt("quantity");

            }
        } catch (Exception e) {
            System.err.println("DealersOrderModel getRevisionno error--------" + e);
        }
        return current_qty;
    }

    public int getCounting() {
        int counting = 100000;
        int count = 0;
        String query = " SELECT order_no FROM order_table order by order_table_id desc limit 1 ";
        try {
            PreparedStatement psmt = connection.prepareStatement(query);
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                String order_no = rs.getString("order_no");
//                String order_no_arr[] = order_no.split("_");
//                int length = (order_no_arr.length) - 1;
                count = Integer.parseInt(order_no.substring(3));

                counting = count;
            }
        } catch (Exception ex) {
            System.out.println("ERROR: in getCounting in DealersOrderModel : " + ex);
        }
        return counting + 1;
    }

    public String getRequestedToKeyPersonorder(String q, String requested_by) {
        int loc_of_dealer = getRequestedKeyPersondegId(requested_by);
        String key_person_name = "";
        String query = "Select kp2.key_person_name from dealer_salesmanager_mapping as dsm,key_person as kp1,key_person as kp2 where kp1.key_person_id=dsm.dealer_id\n"
                + " and  kp2.key_person_id=dsm.salesman_id and dsm.dealer_id='" + loc_of_dealer + "' and  kp1.active='y' and dsm.active='Y' and kp2.active='Y'";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();

            while (rset.next()) {
                key_person_name = (rset.getString("key_person_name"));
            }

        } catch (Exception e) {
            System.out.println("Error:OrderModel--getRequestedToKeyPersonorder()-- " + e);
        }

        return key_person_name;
    }

    public int getRequestedKeyPersondegId(String person_name) {
        String query = "SELECT key_person_id FROM key_person WHERE key_person_name = '" + person_name + "' and active='Y' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("key_person_id");
        } catch (Exception e) {
            System.out.println(" In OrderModel getRequestedKeyPersondegId Error: " + e);
        }
        return id;
    }

    public int insertRecord(DealersOrder bean, String logged_user_name, int i, int logged_key_person_id) throws SQLException {
        String query = "INSERT INTO order_table(order_no,requested_by,requested_to,"
                + " status_id,active,remark,date_time,description,revision_no) "
                + " VALUES(?,?,?,?,?,?,?,?,?) ";
        int rowsAffected2 = 0;
        int rowsAffected = 0;
        int updateRowsAffected = 0;
        int requested_by_id = getRequestedKeyPersonId(logged_user_name);
        int requested_to_id = getRequestedKeyPersonId(bean.getRequested_to());
        int count = 0;
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date_time = sdf.format(date);

        try {
            if (i == 0) {
                String query4 = "SELECT count(*) as count FROM order_table WHERE "
                        + " order_no='" + bean.getOrder_no() + "' "
                        + " and active='Y'  ";

                PreparedStatement pstmt1 = connection.prepareStatement(query4);
                ResultSet rs1 = pstmt1.executeQuery();
                while (rs1.next()) {
                    count = rs1.getInt("count");
                }
                if (count > 0) {
                    message = "Order No. Already Exists!..";
                    messageBGColor = COLOR_ERROR;
                } else {
                    PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    pstmt.setString(1, bean.getOrder_no());
                    pstmt.setInt(2, requested_by_id);
                    pstmt.setInt(3, requested_to_id);
                    pstmt.setInt(4, 2);
                    pstmt.setString(5, "Y");
                    pstmt.setString(6, "OK");
                    pstmt.setString(7, date_time);
                    pstmt.setString(8, "");
                    pstmt.setInt(9, bean.getRevision_no());
                    rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        //rowsAffected = pstmt.executeUpdate();
                        ResultSet rs = pstmt.getGeneratedKeys();
                        while (rs.next()) {
                            order_table_id = rs.getInt(1);
                            PreparedStatement pay = connection.prepareStatement("insert into payment_mode(payment_mode,order_id) values(?,?)");
                            pay.setString(1, "");
                            pay.setInt(2, order_table_id);

                            rowsAffected = pay.executeUpdate();
                        }
                    }
                }
            }
            String query2 = "INSERT INTO order_item(order_table_id,item_names_id, required_qty,"
                    + " status_id,active,remark,expected_date_time,description,revision_no,delivered_date_time,model_id) "
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?) ";

            int model_id = bean.getModel_id();
            int item_names_id2 = getItemNameId(model_id);
            int count2 = 0;

            PreparedStatement pstmt2 = connection.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);
            pstmt2.setInt(1, order_table_id);
            pstmt2.setInt(2, item_names_id2);
            //    pstmt2.setInt(3, purpose_id2);
            pstmt2.setString(3, bean.getRequired_qty());
            pstmt2.setInt(4, 2);
            pstmt2.setString(5, "Y");
            pstmt2.setString(6, "OK");
            pstmt2.setString(7, "");
            pstmt2.setString(8, "");
            pstmt2.setInt(9, bean.getRevision_no());
            pstmt2.setString(10, "");
            pstmt2.setInt(11, model_id);
            rowsAffected2 = pstmt2.executeUpdate();
            if (rowsAffected2 > 0) {
                ResultSet rs2 = pstmt2.getGeneratedKeys();
                while (rs2.next()) {
                    int order_item_id = rs2.getInt(1);
                    PreparedStatement pay2 = connection.prepareStatement("insert into orders_sales_pricing(order_id,order_item_id,discount_percent,prices,approved_price)"
                            + " values(?,?,?,?,?)");
                    pay2.setInt(1, order_table_id);
                    pay2.setInt(2, order_item_id);
                    pay2.setString(3, "0");
                    pay2.setString(4, bean.getBasic_price());
                    pay2.setString(5, bean.getBasic_price());

                    rowsAffected = pay2.executeUpdate();
                }

                int revision = DealersOrderModel.getRevisionno(bean, logged_key_person_id, item_names_id2);

                String query1 = "SELECT max(revision_no) as revision_no,quantity,price "
                        + " FROM cart_table WHERE key_person_id='" + logged_key_person_id + "' "
                        + " and model_id='" + bean.getModel_id() + "' "
                        + " and item_names_id='" + item_names_id2 + "' and cart_status_id=1 and active='Y' ";

                String query_update = " UPDATE cart_table SET active =? "
                        + " WHERE key_person_id=? and model_id=? and item_names_id=? "
                        + " and cart_status_id=? and revision_no=? ";

                PreparedStatement pstmt = connection.prepareStatement(query1);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    revision = rs.getInt("revision_no");
                    int qty = rs.getInt("quantity");
                    String price = rs.getString("price");

                    PreparedStatement pstm = connection.prepareStatement(query_update);
                    pstm.setString(1, "N");
                    pstm.setInt(2, logged_key_person_id);
                    pstm.setInt(3, bean.getModel_id());
                    pstm.setInt(4, item_names_id2);
                    pstm.setInt(5, 1);
                    pstm.setInt(6, revision);
                    updateRowsAffected = pstm.executeUpdate();

                    if (updateRowsAffected >= 1) {
                        String query_insert = " INSERT INTO cart_table(key_person_id,model_id,item_names_id,"
                                + " cart_status_id,quantity,price,active,description,revision_no,remark) "
                                + " VALUES(?,?,?,?,?,?,?,?,?,?) ";
                        revision = rs.getInt("revision_no") + 1;
                        PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query_insert);
                        psmt.setInt(1, logged_key_person_id);
                        psmt.setInt(2, bean.getModel_id());
                        psmt.setInt(3, item_names_id2);
                        psmt.setInt(4, 2);
                        psmt.setInt(5, qty);
                        psmt.setString(6, price);
                        psmt.setString(7, "Y");
                        psmt.setString(8, "");
                        psmt.setInt(9, 0);
                        psmt.setString(10, "");
                        rowsAffected2 = psmt.executeUpdate();
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("OrderModel insertRecord() Error: " + e);
        }
        if (rowsAffected2 > 0) {
            message = "Record saved successfully.";
            messageBGColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            messageBGColor = COLOR_ERROR;
        }
        if (count > 0) {
            message = "Indent No. Already Exists!..";
            messageBGColor = COLOR_ERROR;
        }

        return rowsAffected2;
    }

    public int getItemNameId(int model_id) {

        String query = " SELECT itn.item_names_id FROM model m,manufacturer_item_map mim,item_names itn"
                + "  WHERE m.model_id = '" + model_id + "'  and m.manufacturer_item_map_id=mim.manufacturer_item_map_id  "
                + " and itn.item_names_id=mim.item_names_id and m.active='Y' and mim.active='Y' and itn.active='Y' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("item_names_id");
        } catch (Exception e) {
            System.out.println("OrderModel getItemNameId Error: " + e);
        }
        return id;
    }

    public int getRequestedKeyPersonId(String person_name) {
        String query = "SELECT key_person_id FROM key_person WHERE key_person_name = '" + person_name + "' and active='Y' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("key_person_id");
        } catch (Exception e) {
            System.out.println("In OrderModel getRequestedByKeyPersonId Error: " + e);
        }
        return id;
    }

    public ArrayList<DealersOrder> getAllOrders(String logged_key_person, String user_role) {
        ArrayList<DealersOrder> list = new ArrayList<DealersOrder>();

        String query = " select odt.order_no,odt.date_time,odt.description  ,s.status,kp2.key_person_name as requested_to,odt.order_table_id ,"
                + " SUM(osp.prices) as prices,kp1.key_person_name as requested_by  "
                + " from  order_table odt,key_person kp1,key_person kp2,  status s,order_item odi,payment_mode pm,orders_sales_pricing osp, "
                + " dealer_salesmanager_mapping dsm "
                + " where odt.requested_to=kp2.key_person_id  and odt.requested_by=kp1.key_person_id   and odt.status_id in(2,6,3)"
                + " and odi.status_id in (2,6,3) "
                + " and odt.status_id=s.status_id and odt.active='Y' and kp1.active='Y' and kp2.active='Y'  and odi.active='Y' "
                + " and pm.active='Y' and odt.order_table_id=odi.order_table_id and pm.order_id=odt.order_table_id and "
                + " osp.order_id=odt.order_table_id and dsm.dealer_id=kp1.key_person_id "
                + " and osp.order_item_id =odi.order_item_id and dsm.active='Y' ";

        if (user_role.equals("Dealer")) {
            if (!logged_key_person.equals("") && logged_key_person != null) {
                query += " and kp1.key_person_name='" + logged_key_person + "' ";
            }
        }
        if (user_role.equals("Sales")) {
            if (!logged_key_person.equals("") && logged_key_person != null) {
                query += " and kp2.key_person_name='" + logged_key_person + "' ";
            }
        }
        query += " group by odt.order_table_id order by odt.date_time desc ";
        int prices = 0;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                DealersOrder bean = new DealersOrder();
                bean.setOrder_no(rset.getString("order_no"));
                bean.setDate_time(rset.getString("date_time"));
                String status = rset.getString("status");
                bean.setStatus(status);
                bean.setRequested_to(rset.getString("requested_to"));
                bean.setDescription(rset.getString("description"));
                bean.setOrder_table_id(rset.getInt("order_table_id"));
                bean.setRequested_by(rset.getString("requested_by"));
//                prices = prices + (rset.getInt("prices"));

                bean.setBasic_price(rset.getString("prices"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: OrderModel showdata-" + e);
        }
        return list;
    }

    public ArrayList<DealersOrder> getAllDashboardOrders(String logged_key_person, String user_role) {
        ArrayList<DealersOrder> list = new ArrayList<DealersOrder>();
        String query = " select odt.order_no,odt.date_time,odt.description  ,s.status,kp2.key_person_name as requested_to,odt.order_table_id ,"
                + " SUM(osp.prices) as prices,kp1.key_person_name as requested_by,kp2.mobile_no1 as requested_to_mobile, "
                + " kp1.mobile_no1 as requested_by_mobile,kp1.key_person_id as requested_by_id "
                + " from  order_table odt,key_person kp1,key_person kp2,  status s,order_item odi,payment_mode pm,orders_sales_pricing osp "
                + " where odt.requested_to=kp2.key_person_id  and odt.requested_by=kp1.key_person_id "
                + " and odt.status_id=s.status_id and odt.active='Y' and kp1.active='Y' and kp2.active='Y'  and odi.active='Y' "
                + " and pm.active='Y' and odt.order_table_id=odi.order_table_id and pm.order_id=odt.order_table_id and "
                + " osp.order_id=odt.order_table_id "
                + " and osp.order_item_id =odi.order_item_id and s.status!='Delivered' ";
        if (!user_role.equals("Admin")) {
            if (!logged_key_person.equals("") && logged_key_person != null) {
                query += " and kp1.key_person_name='" + logged_key_person + "' ";
            }
        }
        query += " group by odt.order_table_id order by odt.date_time desc limit 5 ";
        int prices = 0;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                DealersOrder bean = new DealersOrder();
                bean.setOrder_no(rset.getString("order_no"));
                bean.setDate_time(rset.getString("date_time"));
                String status = rset.getString("status");
                bean.setStatus(status);
                bean.setRequested_to(rset.getString("requested_to"));
                bean.setDescription(rset.getString("description"));
                bean.setOrder_table_id(rset.getInt("order_table_id"));
                bean.setRequested_by(rset.getString("requested_by"));
                bean.setRequested_to_mobile(rset.getString("requested_to_mobile"));
                bean.setRequested_by_mobile(rset.getString("requested_by_mobile"));
                int requested_by_id = rset.getInt("requested_by_id");
                String org_office_name = getOrgOfficeName(requested_by_id);
                bean.setOrg_office(org_office_name);
                bean.setBasic_price(rset.getString("prices"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: OrderModel showdata-" + e);
        }
        return list;
    }

    public ArrayList<DealersOrder> getAllOrderItems(String order_table_id) {
        ArrayList<DealersOrder> list = new ArrayList<DealersOrder>();

//        String query = " select odt.order_no,itn.item_name,odi.required_qty,odi.expected_date_time,odi.approved_qty, s1.status as order_status,"
//                + " s2.status as item_status,odi.order_item_id,odt.order_table_id,inv.stock_quantity, odi.deliver_qty,odt.requested_by,"
//                + "  odt.requested_to,m.model,pm.payment_mode,osp.prices,iid.image_path,iid.image_name "
//                + " from order_table odt,order_item odi, item_names itn,payment_mode pm,  status s1,status s2,inventory inv,"
//                + " inventory_basic ib,model m,  manufacturer_item_map mim, orders_sales_pricing osp,item_image_details iid, "
//                + " key_person kp where odt.order_table_id=odi.order_table_id and odi.item_names_id=itn.item_names_id  "
//                + " and m.manufacturer_item_map_id=mim.manufacturer_item_map_id  and mim.item_names_id=itn.item_names_id  "
//                + " and pm.order_id=odt.order_table_id "
//                + " and ib.inventory_basic_id=inv.inventory_basic_id  and ib.item_names_id=itn.item_names_id  and ib.active='Y' "
//                + " and inv.active='Y' and m.active='Y' "
//                + " and mim.active='Y'  and odt.status_id=s1.status_id and  odi.status_id=s2.status_id and odt.active='Y' "
//                + " and odi.active='Y' and itn.active='Y' "
//                + " and odt.order_table_id='" + order_table_id + "' and ib.model_id=m.model_id and iid.active='Y' and iid.model_id=m.model_id "
//                + " and iid.model_id=odi.model_id and osp.order_id=odt.order_table_id  and osp.order_item_id =odi.order_item_id "
//                + " and kp.active='Y'  and kp.key_person_id=inv.key_person_id "
//                + " and odi.model_id=m.model_id and inv.key_person_id=115 group by m.model_id ";
        String query = " select odt.order_no,itn.item_name,odi.required_qty,odi.expected_date_time,odi.approved_qty, s1.status as order_status,"
                + " s2.status as item_status,odi.order_item_id,odt.order_table_id,inv.stock_quantity, odi.deliver_qty,odt.requested_by,"
                + "  odt.requested_to,m.model,pm.payment_mode,iid.image_path,iid.image_name,m.basic_price,osp.discount_percent,osp.prices,osp.approved_price "
                + " from order_table odt,order_item odi, item_names itn,payment_mode pm,  status s1,status s2,inventory inv,"
                + " inventory_basic ib,model m,  manufacturer_item_map mim,item_image_details iid, "
                + " key_person kp,orders_sales_pricing osp where odt.order_table_id=odi.order_table_id and odi.item_names_id=itn.item_names_id  "
                + " and m.manufacturer_item_map_id=mim.manufacturer_item_map_id  and mim.item_names_id=itn.item_names_id  "
                + " and pm.order_id=odt.order_table_id "
                + " and ib.inventory_basic_id=inv.inventory_basic_id  and ib.item_names_id=itn.item_names_id  and ib.active='Y' "
                + " and inv.active='Y' and m.active='Y' "
                + " and mim.active='Y'  and odt.status_id=s1.status_id and  odi.status_id=s2.status_id and odt.active='Y' "
                + " and odi.active='Y' and itn.active='Y' "
                + " and odt.order_table_id='" + order_table_id + "' and ib.model_id=m.model_id and iid.active='Y' and iid.model_id=m.model_id "
                + " and iid.model_id=odi.model_id "
                + " and kp.active='Y'  and kp.key_person_id=inv.key_person_id and osp.order_id=odt.order_table_id  and osp.order_item_id =odi.order_item_id "
                + " and odi.model_id=m.model_id and inv.key_person_id=115 group by m.model_id ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                DealersOrder bean = new DealersOrder();
                bean.setOrder_no(rset.getString("order_no"));
                bean.setItem_name((rset.getString("item_name")));
                bean.setRequired_qty(rset.getString("required_qty"));
                bean.setApproved_qty(rset.getString("approved_qty"));
                bean.setDelivered_qty(rset.getString("deliver_qty"));
                bean.setStock_quantity(rset.getString("stock_quantity"));
                bean.setOrder_table_id(Integer.parseInt(order_table_id));
//                String status = rset.getString("status");
//
                bean.setItem_status(rset.getString("item_status"));
                bean.setOrder_status(rset.getString("order_status"));
                bean.setOrder_item_id(rset.getInt("order_item_id"));
                bean.setModel(rset.getString("model"));

                bean.setBasic_price(String.valueOf(Integer.parseInt(rset.getString("required_qty")) * Integer.parseInt(rset.getString("basic_price"))));
                bean.setDiscount_price(rset.getString("prices"));
                bean.setDiscount_percent(rset.getString("discount_percent"));
                bean.setImage_path(rset.getString("image_path"));
                bean.setImage_name(rset.getString("image_name"));
                bean.setApproved_price(rset.getString("approved_price"));

                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: OrderModel getIndentItems-" + e);
        }
        return list;
    }

    public int deleteOrder(String order_table_id) throws SQLException {
        int rowsAffected = 0;
        int updateRowsAffected = 0;
        int updateRowsAffected1 = 0;
        int count = 0;

        int revision = DealersOrderModel.getRevisionnoForOrder(order_table_id);

        try {
            String query1 = " SELECT max(revision_no) as revision_no FROM order_table WHERE order_table_id='" + order_table_id + "' "
                    + " and active='Y' ";

            String query_update = " UPDATE order_table SET active =? WHERE order_table_id=? "
                    + " and revision_no=? ";
            String query_update_item = " UPDATE order_item SET active =? WHERE order_table_id=? ";

            PreparedStatement pstmt = connection.prepareStatement(query1);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                revision = rs.getInt("revision_no");

                PreparedStatement pstm = connection.prepareStatement(query_update);
                pstm.setString(1, "N");
                pstm.setString(2, order_table_id);
                pstm.setInt(3, revision);
                updateRowsAffected = pstm.executeUpdate();

                PreparedStatement pstm1 = connection.prepareStatement(query_update_item);
                pstm1.setString(1, "N");
                pstm1.setString(2, order_table_id);
                updateRowsAffected1 = pstm1.executeUpdate();
            }
            if (updateRowsAffected1 > 0) {
                message = "Record saved successfully.";
                messageBGColor = COLOR_OK;
            } else {
                message = "Cannot save the record, some error.";
                messageBGColor = COLOR_ERROR;

            }
        } catch (Exception e) {
            System.err.println("Exception---" + e);
        }

        return updateRowsAffected1;
    }

    public static ArrayList<DealersOrder> getAllHistoryOrders(String logged_key_person, String user_role) {
        ArrayList<DealersOrder> list = new ArrayList<DealersOrder>();
        String query = " select odt.order_no,odt.date_time,odt.description  ,s.status,kp2.key_person_name as requested_to,odt.order_table_id ,"
                + " SUM(osp.prices) as prices,kp1.key_person_name as requested_by "
                + " from  order_table odt,key_person kp1,key_person kp2,  status s,order_item odi,payment_mode pm,orders_sales_pricing osp "
                + " where odt.requested_to=kp2.key_person_id  and odt.requested_by=kp1.key_person_id "
                + " and odt.status_id=s.status_id and odt.active='Y' and kp1.active='Y' and kp2.active='Y'  and odi.active='Y' "
                + " and pm.active='Y' and odt.order_table_id=odi.order_table_id and pm.order_id=odt.order_table_id and "
                + " osp.order_id=odt.order_table_id "
                + " and osp.order_item_id =odi.order_item_id ";
        if (!user_role.equals("Admin")) {
            if (!logged_key_person.equals("") && logged_key_person != null) {
                query += " and kp1.key_person_name='" + logged_key_person + "' ";
            }
        }
        query += " group by odt.order_table_id order by odt.date_time desc ";
        int prices = 0;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                DealersOrder bean = new DealersOrder();
                bean.setOrder_no(rset.getString("order_no"));
                bean.setDate_time(rset.getString("date_time"));
                String status = rset.getString("status");
                bean.setStatus(status);
                bean.setRequested_to(rset.getString("requested_to"));
                bean.setDescription(rset.getString("description"));
                bean.setOrder_table_id(rset.getInt("order_table_id"));
                bean.setRequested_by(rset.getString("requested_by"));
//                prices = prices + (rset.getInt("prices"));

                bean.setBasic_price(rset.getString("prices"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: OrderModel showdata-" + e);
        }
        return list;
    }

    public static int getRevisionnoForOrder(String order_table_id) {
        int revision = 0;
        try {

            String query = " SELECT max(revision_no) as revision_no FROM order_table where "
                    + " order_table_id='" + order_table_id + "' and active='Y' ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
            System.err.println("DealersOrderModel getRevisionno error--------" + e);
        }
        return revision;
    }

    public static String getOrderNo(String order_table_id) {
        String order_no = "";
        try {

            String query = " SELECT order_no FROM order_table where "
                    + " order_table_id='" + order_table_id + "' and active='Y' ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                order_no = rset.getString("order_no");

            }
        } catch (Exception e) {
            System.err.println("DealersOrderModel getOrderNo error--------" + e);
        }
        return order_no;
    }

    public static String getOrgOfficeName(int key_person_id) {
        String org_office_name = "";
        try {

            String query = " select oo.org_office_name from org_office oo,key_person kp where oo.active='Y' and kp.active='Y'  "
                    + " and kp.org_office_id=oo.org_office_id and kp.key_person_id= '" + key_person_id + "' ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                org_office_name = rset.getString("org_office_name");

            }
        } catch (Exception e) {
            System.err.println("DealersOrderModel getOrderNo error--------" + e);
        }
        return org_office_name;
    }

    public String getImagePath(String key_person_id, String uploadedFor) {
        String img_name = "";
        if (uploadedFor.equals("ph")) {
            uploadedFor = "key_person_photo";
        } else {
            uploadedFor = "key_person_ID";
        }
        String destination_path = "";
//     String query = "SELECT image_name, destination_path FROM general_image_details gid, key_person kp, image_destination dp "
//             + " WHERE dp.image_destination_id=gid.image_destination_id "
//             + " AND gid.general_image_details_id = kp.general_image_details_id "
//             + " AND kp.emp_code = '" + emp_code + "'";
        String query = "SELECT general_image_details_id,image_name, destination_path "
                + " FROM general_image_details gid, image_destination dp, image_uploaded_for iuf "
                + " WHERE dp.image_destination_id=gid.image_destination_id AND iuf.image_uploaded_for_id=dp.image_uploaded_for_id "
                + " AND iuf.image_uploaded_for='" + uploadedFor + "' AND gid.key_person_id=" + key_person_id + " "
                + " and gid.active='Y' and dp.active='Y' and iuf.active='Y' "
                + " ORDER BY general_image_details_id DESC";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                img_name = rs.getString("image_name");
                destination_path = rs.getString("destination_path");
            }
            //String[] img_path = img_name.split("-");
            destination_path = destination_path + img_name;
        } catch (Exception ex) {
            System.out.println("ERROR: in getImagePath in DealersOrderModel : " + ex);
        }
        return destination_path;
    }

    public String approveOrder(DealersOrder bean, int order_item_id, int order_table_id, int i) {
        int updateRowsAffected = 0;
        String status = bean.getStatus();
        if (status.equals("Approve")) {
            status = "Approved";
        }
        String item_status = bean.getItem_status();

        int status_id = getStatusId(status);
        int item_status_id = getStatusId(item_status);

        String query2 = " UPDATE order_item SET status_id=?,approved_qty=? WHERE order_item_id=? ";

        int rowsAffected = 0;
        int updateRowsAffected2 = 0;
        int updateRowsAffected3 = 0;
        int map_count = 0;
        try {
            PreparedStatement pstm = connection.prepareStatement(query2);
            pstm.setInt(1, item_status_id);
            if (item_status.equals("Approved")) {
                pstm.setString(2, bean.getApproved_qty());
            } else {
                pstm.setString(2, "0");
            }
            pstm.setInt(3, order_item_id);
            updateRowsAffected = pstm.executeUpdate();

            String query = " update order_table set status_id=? where order_table_id=? ";
            PreparedStatement pstm2 = connection.prepareStatement(query);
            pstm2.setInt(1, status_id);
            pstm2.setInt(2, order_table_id);
            updateRowsAffected2 = pstm2.executeUpdate();

            if (i == 0) {
                String query_update = " delete from orders_sales_pricing where order_id='" + order_table_id + "' ";
                PreparedStatement pstm3 = connection.prepareStatement(query_update);
                updateRowsAffected3 = pstm3.executeUpdate();
            }
            PreparedStatement pay2 = connection.prepareStatement("insert into orders_sales_pricing(order_id,order_item_id,discount_percent,prices,approved_price)"
                    + " values(?,?,?,?,?)");
            pay2.setInt(1, order_table_id);
            pay2.setInt(2, order_item_id);
            pay2.setString(3, bean.getDiscount_percent());
            pay2.setString(4, bean.getDiscount_price());
            pay2.setString(5, bean.getApproved_price());

            rowsAffected = pay2.executeUpdate();

        } catch (Exception e) {
            System.out.println("DealersOrderModel approveOrder() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Your Indent is '" + status + "'!.";
            messageBGColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            messageBGColor = COLOR_ERROR;
        }
        return message + "&" + status;
    }

    public int getStatusId(String status) {

        String query = "SELECT status_id FROM status WHERE status = '" + status + "' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("status_id");
        } catch (Exception e) {
            System.out.println("ApproveIndentModel getStatusId Error: " + e);
        }
        return id;
    }

    public JSONArray getDealerLocation(String logged_org_office, String logged_user_name, String loggedUser) {

        JSONObject obj = new JSONObject();
        JSONArray arrayObj = new JSONArray();
        String query = " select distinct "
                + " kp.key_person_id, "
                + " kp.key_person_name,kp.address_line1,kp.address_line2,kp.address_line3,kp.mobile_no1,kp.email_id1,kp.emp_code, "
                + " kp.father_name,kp.date_of_birth,kp.emergency_contact_name,kp.emergency_contact_mobile, "
                + " onn.organisation_name,onn.organisation_code,oo.org_office_name,oo.address_line1,oo.email_id1, "
                + " oo.mobile_no1,oo.org_office_code,oo.org_office_id, "
                + " d.designation,d.designation_code ,oot.office_type,oo.address_line2,kp.mobile_no2,oo.service_tax_reg_no,oo.latitude,oo.longitude "
                + " from key_person kp, organisation_name onn, org_office oo, designation d, "
                + " org_office_designation_map oodm, org_office_type oot"
                + " where kp.active='y' and oo.active='y' and onn.active='y' and d.active='y' and oodm.active='Y' and oot.active='Y' "
                + " and oo.organisation_id=onn.organisation_id and kp.org_office_id=oo.org_office_id "
                + " and oodm.designation_id=d.designation_id and oodm.org_office_id=oo.org_office_id "
                + " and kp.org_office_designation_map_id=oodm.org_office_designation_map_id and oo.office_type_id=oot.office_type_id"
                + " and oo.office_type_id=3 ";

        if (!loggedUser.equals("Admin")) {
            if (!logged_org_office.equals("") && logged_org_office != null) {
                query += " and oo.org_office_name='" + logged_org_office + "' ";
            }
            if (!logged_user_name.equals("") && logged_user_name != null) {
                query += " and kp.key_person_name='" + logged_user_name + "' ";
            }
        }

        query += " group by oo.org_office_name ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {

                JSONObject jsonObj = new JSONObject();
                jsonObj.put("dealer_office_name", rset.getString("org_office_name"));
                jsonObj.put("latitude", rset.getString("latitude"));
                jsonObj.put("longitude", rset.getString("longitude"));
                jsonObj.put("email", rset.getString("email_id1"));
                jsonObj.put("mobile", rset.getString("mobile_no1"));
                jsonObj.put("person_name", rset.getString("key_person_name"));
                jsonObj.put("org_office_id", rset.getString("org_office_id"));
                jsonObj.put("key_person_id", rset.getString("key_person_id"));

                arrayObj.add(jsonObj);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return arrayObj;
    }

    public static ArrayList<Enquiry> getAllEnquiries(String user_role, int logged_key_person_id) {
        ArrayList<Enquiry> list = new ArrayList<Enquiry>();
        try {
            String query = " select et.enquiry_table_id,es.status,et.enquiry_no, et.sender_name,et.sender_email,et.sender_mob,et.sender_company_name, "
                    + " et.enquiry_address,et.enquiry_city,et.enquiry_state,et.country,et.enquiry_message,et.enquiry_date_time,  "
                    + " et.enquiry_call_duration,et.enquiry_reciever_mob,et.sender_alternate_email,  et.sender_alternate_mob,et.description, "
                    + " kp.key_person_name,oo.org_office_name "
                    + " from enquiry_table et,enquiry_status es,city ct,tehsil th,district dt,division dv,state st,salesmanager_state_mapping ssm,key_person kp, "
                    + " org_office oo  "
                    + " where et.active='Y' and ct.active='Y' and st.active='Y' and dt.active='Y'  and th.active='Y'  and dv.active='Y' and ssm.active='Y' and kp.active='Y' "
                    + " and kp.key_person_id=et.assigned_to and ct.tehsil_id=th.tehsil_id and th.district_id=dt.district_id and ssm.state_id=st.state_id "
                    + " and et.enquiry_status_id=es.enquiry_status_id and dt.district_name=et.description and es.active='Y' and oo.active='Y' "
                    + " and oo.org_office_id=kp.org_office_id  and dt.division_id=dv.division_id and dv.state_id=st.state_id  "
                    + " and et.enquiry_status_id!=1 and ssm.salesman_id=et.assigned_to ";
            if (user_role.equals("Sales")) {
                query += " and ssm.salesman_id='" + logged_key_person_id + "' ";
            }

            query += " group by et.enquiry_table_id ";
            query += " order by et.enquiry_table_id desc ";

            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                Enquiry bean = new Enquiry();
                bean.setEnquiry_table_id(rst.getInt("enquiry_table_id"));
//                bean.setEnquiry_source(rst.getString("enquiry_source"));
//                bean.setMarketing_vertical_name(rst.getString("marketing_vertical_name"));
                bean.setStatus(rst.getString("status"));
                bean.setEnquiry_no(rst.getString("enquiry_no"));
                bean.setSender_name(rst.getString("sender_name"));
                bean.setSender_email(rst.getString("sender_email"));
                bean.setSender_mob(rst.getString("sender_mob"));
                bean.setSender_company_name(rst.getString("sender_company_name"));
                bean.setEnquiry_address(rst.getString("enquiry_address"));
                bean.setEnquiry_city(rst.getString("enquiry_city"));
                bean.setEnquiry_state(rst.getString("enquiry_state"));
                bean.setCountry(rst.getString("country"));
                bean.setEnquiry_message(rst.getString("enquiry_message"));
                bean.setEnquiry_date_time(rst.getString("enquiry_date_time"));
                bean.setEnquiry_call_duration(rst.getString("enquiry_call_duration"));
                bean.setEnquiry_reciever_mob(rst.getString("enquiry_reciever_mob"));
                bean.setSender_alternate_email(rst.getString("sender_alternate_email"));
                bean.setSender_alternate_mob(rst.getString("sender_alternate_mob"));
                bean.setDescription(rst.getString("description"));
                if (rst.getString("status").equals("Assigned To SalesManager")) {
                    bean.setAssigned_to(rst.getString("key_person_name"));
                }
                if (rst.getString("status").equals("Assigned To Dealer")) {
                    bean.setAssigned_to(rst.getString("org_office_name"));
                }

                String enquiry_date_time = rst.getString("enquiry_date_time");
//                String split_arr[] = enquiry_date_time.split(" ");
//                enquiry_date_time = split_arr[0] + " " + split_arr[1];

//                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
                java.util.Date date = new java.util.Date();
                String currentDateString = dateFormatter.format(date);
                java.util.Date currentDate = dateFormatter.parse(currentDateString);
                String pastTimeInSecond = enquiry_date_time;
                java.util.Date pastDate = dateFormatter.parse(pastTimeInSecond);
                String time_ago = timeAgo(currentDate, pastDate);
                bean.setEnquiry_date_time(time_ago);

                list.add(bean);
            }
        } catch (Exception e) {
            System.err.println("Exception------------" + e);
        }

        return list;
    }

    public static ArrayList<Enquiry> getAllComplaints(String user_role, int logged_key_person_id) {
        ArrayList<Enquiry> list = new ArrayList<Enquiry>();
        try {
            String query = " select et.complaint_table_id,es.status,et.enquiry_no, et.sender_name,et.sender_email,et.sender_mob,et.sender_company_name, "
                    + " et.enquiry_address,et.enquiry_city,et.enquiry_state,et.country,et.enquiry_message,et.enquiry_date_time,  "
                    + " et.enquiry_call_duration,et.enquiry_reciever_mob,et.sender_alternate_email,  et.sender_alternate_mob,et.description, "
                    + " kp.key_person_name,oo.org_office_name "
                    + " from complaint_table et,enquiry_status es,city ct,tehsil th,district dt,division dv,state st,salesmanager_state_mapping ssm,key_person kp, "
                    + " org_office oo  "
                    + " where et.active='Y' and ct.active='Y' and st.active='Y' and dt.active='Y'  and th.active='Y'  and dv.active='Y' and ssm.active='Y' and kp.active='Y' "
                    + " and kp.key_person_id=et.assigned_to and ct.tehsil_id=th.tehsil_id and th.district_id=dt.district_id and ssm.state_id=st.state_id "
                    + " and et.enquiry_status_id=es.enquiry_status_id and dt.district_name=et.description and es.active='Y' and oo.active='Y' "
                    + " and oo.org_office_id=kp.org_office_id  and dt.division_id=dv.division_id and dv.state_id=st.state_id "
                    + " and et.enquiry_status_id!=1 and ssm.salesman_id=et.assigned_to ";
            if (user_role.equals("Sales")) {
                query += " and ssm.salesman_id='" + logged_key_person_id + "' ";
            }
            query += " group by et.complaint_table_id ";

            query += " order by et.complaint_table_id desc ";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                Enquiry bean = new Enquiry();
                bean.setEnquiry_table_id(rst.getInt("complaint_table_id"));
//                bean.setEnquiry_source(rst.getString("enquiry_source"));
//                bean.setMarketing_vertical_name(rst.getString("marketing_vertical_name"));
                bean.setStatus(rst.getString("status"));
                bean.setEnquiry_no(rst.getString("enquiry_no"));
                bean.setSender_name(rst.getString("sender_name"));
                bean.setSender_email(rst.getString("sender_email"));
                bean.setSender_mob(rst.getString("sender_mob"));
                bean.setSender_company_name(rst.getString("sender_company_name"));
                bean.setEnquiry_address(rst.getString("enquiry_address"));
                bean.setEnquiry_city(rst.getString("enquiry_city"));
                bean.setEnquiry_state(rst.getString("enquiry_state"));
                bean.setCountry(rst.getString("country"));
                bean.setEnquiry_message(rst.getString("enquiry_message"));
                bean.setEnquiry_date_time(rst.getString("enquiry_date_time"));
                bean.setEnquiry_call_duration(rst.getString("enquiry_call_duration"));
                bean.setEnquiry_reciever_mob(rst.getString("enquiry_reciever_mob"));
                bean.setSender_alternate_email(rst.getString("sender_alternate_email"));
                bean.setSender_alternate_mob(rst.getString("sender_alternate_mob"));
                bean.setDescription(rst.getString("description"));
                if (rst.getString("status").equals("Assigned To SalesManager")) {
                    bean.setAssigned_to(rst.getString("key_person_name"));
                }
                if (rst.getString("status").equals("Assigned To Dealer")) {
                    bean.setAssigned_to(rst.getString("org_office_name"));
                }

                String enquiry_date_time = rst.getString("enquiry_date_time");
//                String split_arr[] = enquiry_date_time.split(" ");
//                enquiry_date_time = split_arr[0] + " " + split_arr[1];

//                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
                java.util.Date date = new java.util.Date();
                String currentDateString = dateFormatter.format(date);
                java.util.Date currentDate = dateFormatter.parse(currentDateString);
                String pastTimeInSecond = enquiry_date_time;
                java.util.Date pastDate = dateFormatter.parse(pastTimeInSecond);
                String time_ago = timeAgo(currentDate, pastDate);
                bean.setEnquiry_date_time(time_ago);
                list.add(bean);
            }
        } catch (Exception e) {
            System.err.println("Exception------------" + e);
        }

        return list;
    }

    public ArrayList<Enquiry> getAllEnquiriesDetails(String enquiry_table_id) {
        ArrayList<Enquiry> list = new ArrayList<Enquiry>();

        try {
            String query = " select et.enquiry_table_id,es.status,et.enquiry_no, et.sender_name,et.sender_email,et.sender_mob,et.sender_company_name, "
                    + " et.enquiry_address,et.enquiry_city,et.enquiry_state,et.country,et.enquiry_message,et.enquiry_date_time,  "
                    + " et.enquiry_call_duration,et.enquiry_reciever_mob,et.sender_alternate_email,  et.sender_alternate_mob,et.description, "
                    + " kp.key_person_name,oo.org_office_name "
                    + " from enquiry_table et,enquiry_status es,city ct,tehsil th,district dt,division dv,state st,salesmanager_state_mapping ssm,key_person kp, "
                    + " org_office oo  "
                    + " where et.active='Y' and ct.active='Y' and st.active='Y' and dt.active='Y'  and th.active='Y'  and dv.active='Y' and ssm.active='Y' and kp.active='Y' "
                    + " and kp.key_person_id=et.assigned_to and ct.tehsil_id=th.tehsil_id and th.district_id=dt.district_id and ssm.state_id=st.state_id "
                    + " and et.enquiry_status_id=es.enquiry_status_id and dt.district_name=et.description and es.active='Y' and oo.active='Y' "
                    + " and oo.org_office_id=kp.org_office_id  and dt.division_id=dv.division_id and dv.state_id=st.state_id ";
            if (!enquiry_table_id.equals("") && enquiry_table_id != null) {
                query += " and et.enquiry_table_id='" + enquiry_table_id + "' ";
            }
            query += " group by et.enquiry_table_id ";

            query += " order by et.enquiry_table_id desc ";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                Enquiry bean = new Enquiry();
                bean.setEnquiry_table_id(rst.getInt("enquiry_table_id"));
//                bean.setEnquiry_source(rst.getString("enquiry_source"));
//                bean.setMarketing_vertical_name(rst.getString("marketing_vertical_name"));
                bean.setStatus(rst.getString("status"));
                bean.setEnquiry_no(rst.getString("enquiry_no"));
                bean.setSender_name(rst.getString("sender_name"));
                bean.setSender_email(rst.getString("sender_email"));
                bean.setSender_mob(rst.getString("sender_mob"));
                bean.setSender_company_name(rst.getString("sender_company_name"));
                bean.setEnquiry_address(rst.getString("enquiry_address"));
                bean.setEnquiry_city(rst.getString("enquiry_city"));
                bean.setEnquiry_state(rst.getString("enquiry_state"));
                bean.setCountry("IN");
                bean.setEnquiry_message(rst.getString("enquiry_message"));
                bean.setEnquiry_date_time(rst.getString("enquiry_date_time"));
                bean.setEnquiry_call_duration(rst.getString("enquiry_call_duration"));
                bean.setEnquiry_reciever_mob(rst.getString("enquiry_reciever_mob"));
                bean.setSender_alternate_email(rst.getString("sender_alternate_email"));
                bean.setSender_alternate_mob(rst.getString("sender_alternate_mob"));
                bean.setDescription(rst.getString("description"));
                if (rst.getString("status").equals("Assigned To SalesManager")) {
                    bean.setAssigned_to(rst.getString("key_person_name"));
                }
                if (rst.getString("status").equals("Assigned To Dealer")) {
                    bean.setAssigned_to(rst.getString("org_office_name"));
                }
                list.add(bean);
            }
        } catch (Exception e) {
            System.err.println("Exception------------" + e);
        }

        return list;
    }

    public ArrayList<Enquiry> getAllComplaintDetails(String enquiry_table_id) {
        ArrayList<Enquiry> list = new ArrayList<Enquiry>();

        try {
            String query = " select et.complaint_table_id,es.status,et.enquiry_no, et.sender_name,et.sender_email,et.sender_mob,et.sender_company_name, "
                    + " et.enquiry_address,et.enquiry_city,et.enquiry_state,et.country,et.enquiry_message,et.enquiry_date_time,  "
                    + " et.enquiry_call_duration,et.enquiry_reciever_mob,et.sender_alternate_email,  et.sender_alternate_mob,et.description, "
                    + " kp.key_person_name,oo.org_office_name "
                    + " from complaint_table et,enquiry_status es,city ct,tehsil th,district dt,division dv,state st,salesmanager_state_mapping ssm,key_person kp, "
                    + " org_office oo  "
                    + " where et.active='Y' and ct.active='Y' and st.active='Y' and dt.active='Y'  and th.active='Y'  and dv.active='Y' and ssm.active='Y' and kp.active='Y' "
                    + " and kp.key_person_id=et.assigned_to and ct.tehsil_id=th.tehsil_id and th.district_id=dt.district_id and ssm.state_id=st.state_id "
                    + " and et.enquiry_status_id=es.enquiry_status_id and dt.district_name=et.description and es.active='Y' and oo.active='Y' "
                    + " and oo.org_office_id=kp.org_office_id  and dt.division_id=dv.division_id and dv.state_id=st.state_id ";
            if (!enquiry_table_id.equals("") && enquiry_table_id != null) {
                query += " and et.complaint_table_id='" + enquiry_table_id + "' ";
            }
            query += " group by et.complaint_table_id ";

            query += " order by et.complaint_table_id desc ";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                Enquiry bean = new Enquiry();
                bean.setEnquiry_table_id(rst.getInt("complaint_table_id"));
//                bean.setEnquiry_source(rst.getString("enquiry_source"));
//                bean.setMarketing_vertical_name(rst.getString("marketing_vertical_name"));
                bean.setStatus(rst.getString("status"));
                bean.setEnquiry_no(rst.getString("enquiry_no"));
                bean.setSender_name(rst.getString("sender_name"));
                bean.setSender_email(rst.getString("sender_email"));
                bean.setSender_mob(rst.getString("sender_mob"));
                bean.setSender_company_name(rst.getString("sender_company_name"));
                bean.setEnquiry_address(rst.getString("enquiry_address"));
                bean.setEnquiry_city(rst.getString("enquiry_city"));
                bean.setEnquiry_state(rst.getString("enquiry_state"));
                bean.setCountry("IN");
                bean.setEnquiry_message(rst.getString("enquiry_message"));
                bean.setEnquiry_date_time(rst.getString("enquiry_date_time"));
                bean.setEnquiry_call_duration(rst.getString("enquiry_call_duration"));
                bean.setEnquiry_reciever_mob(rst.getString("enquiry_reciever_mob"));
                bean.setSender_alternate_email(rst.getString("sender_alternate_email"));
                bean.setSender_alternate_mob(rst.getString("sender_alternate_mob"));
                bean.setDescription(rst.getString("description"));
                if (rst.getString("status").equals("Assigned To SalesManager")) {
                    bean.setAssigned_to(rst.getString("key_person_name"));
                }
                if (rst.getString("status").equals("Assigned To Dealer")) {
                    bean.setAssigned_to(rst.getString("org_office_name"));
                }
                list.add(bean);
            }
        } catch (Exception e) {
            System.err.println("Exception------------" + e);
        }

        return list;
    }

    public static ArrayList<Enquiry> getAllEnquiriesForDealer(int logged_key_person_id) {
        ArrayList<Enquiry> list = new ArrayList<Enquiry>();
        try {

            String query = " select et.enquiry_table_id,es.status,et.enquiry_no, et.sender_name,et.sender_email,et.sender_mob,et.sender_company_name, "
                    + " et.enquiry_address,et.enquiry_city,et.enquiry_state,et.country,et.enquiry_message,et.enquiry_date_time,  "
                    + " et.enquiry_call_duration,et.enquiry_reciever_mob,et.sender_alternate_email,  et.sender_alternate_mob,et.description, "
                    + " kp.key_person_name,oo.org_office_name "
                    + " from enquiry_table et,enquiry_status es,city ct,tehsil th,district dt,division dv,state st, "
                    + " salesmanager_state_mapping ssm,key_person kp, "
                    + " org_office oo  "
                    + " where et.active='Y' and ct.active='Y' and st.active='Y' and dt.active='Y'  and th.active='Y'  and dv.active='Y' "
                    + " and ssm.active='Y' and kp.active='Y' "
                    + " and kp.key_person_id=et.assigned_to and ct.tehsil_id=th.tehsil_id and th.district_id=dt.district_id and ssm.state_id=st.state_id "
                    + " and et.enquiry_status_id=es.enquiry_status_id and dt.district_name=et.description and es.active='Y' and oo.active='Y' "
                    + " and dt.division_id=dv.division_id and dv.state_id=st.state_id "
                    + " and oo.org_office_id=kp.org_office_id and et.assigned_to='" + logged_key_person_id + "' ";
            query += " group by et.enquiry_table_id ";

            query += " order by et.enquiry_table_id desc ";

            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                Enquiry bean = new Enquiry();
                bean.setEnquiry_table_id(rst.getInt("enquiry_table_id"));
//                bean.setEnquiry_source(rst.getString("enquiry_source"));
//                bean.setMarketing_vertical_name(rst.getString("marketing_vertical_name"));
                bean.setStatus(rst.getString("status"));
                bean.setEnquiry_no(rst.getString("enquiry_no"));
                bean.setSender_name(rst.getString("sender_name"));
                bean.setSender_email(rst.getString("sender_email"));
                bean.setSender_mob(rst.getString("sender_mob"));
                bean.setSender_company_name(rst.getString("sender_company_name"));
                bean.setEnquiry_address(rst.getString("enquiry_address"));
                bean.setEnquiry_city(rst.getString("enquiry_city"));
                bean.setEnquiry_state(rst.getString("enquiry_state"));
                bean.setCountry(rst.getString("country"));
                bean.setEnquiry_message(rst.getString("enquiry_message"));
                bean.setEnquiry_date_time(rst.getString("enquiry_date_time"));
                bean.setEnquiry_call_duration(rst.getString("enquiry_call_duration"));
                bean.setEnquiry_reciever_mob(rst.getString("enquiry_reciever_mob"));
                bean.setSender_alternate_email(rst.getString("sender_alternate_email"));
                bean.setSender_alternate_mob(rst.getString("sender_alternate_mob"));
                bean.setDescription(rst.getString("description"));
                bean.setAssigned_to(rst.getString("org_office_name"));

                String enquiry_date_time = rst.getString("enquiry_date_time");
//                String split_arr[] = enquiry_date_time.split(" ");
//                enquiry_date_time = split_arr[0] + " " + split_arr[1];

//                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
                java.util.Date date = new java.util.Date();
                String currentDateString = dateFormatter.format(date);
                java.util.Date currentDate = dateFormatter.parse(currentDateString);
                String pastTimeInSecond = enquiry_date_time;
                java.util.Date pastDate = dateFormatter.parse(pastTimeInSecond);
                String time_ago = timeAgo(currentDate, pastDate);
                bean.setEnquiry_date_time(time_ago);

                list.add(bean);
            }
        } catch (Exception e) {
            System.err.println("Exception------------" + e);
        }

        return list;
    }

    public static ArrayList<Enquiry> getAllComplaintForDealer(int logged_key_person_id) {
        ArrayList<Enquiry> list = new ArrayList<Enquiry>();
        try {

            String query = " select et.complaint_table_id,es.status,et.enquiry_no, et.sender_name,et.sender_email,et.sender_mob,et.sender_company_name, "
                    + " et.enquiry_address,et.enquiry_city,et.enquiry_state,et.country,et.enquiry_message,et.enquiry_date_time,  "
                    + " et.enquiry_call_duration,et.enquiry_reciever_mob,et.sender_alternate_email,  et.sender_alternate_mob,et.description, "
                    + " kp.key_person_name,oo.org_office_name "
                    + " from complaint_table et,enquiry_status es,city ct,tehsil th,district dt,division dv,state st, "
                    + " salesmanager_state_mapping ssm,key_person kp, "
                    + " org_office oo  "
                    + " where et.active='Y' and ct.active='Y' and st.active='Y' and dt.active='Y'  and th.active='Y'  and dv.active='Y' "
                    + " and ssm.active='Y' and kp.active='Y' "
                    + " and kp.key_person_id=et.assigned_to and ct.tehsil_id=th.tehsil_id and th.district_id=dt.district_id and ssm.state_id=st.state_id "
                    + " and et.enquiry_status_id=es.enquiry_status_id and dt.district_name=et.description and es.active='Y' and oo.active='Y' "
                    + " and dt.division_id=dv.division_id and dv.state_id=st.state_id "
                    + " and oo.org_office_id=kp.org_office_id and et.assigned_to='" + logged_key_person_id + "' ";
            query += " group by et.complaint_table_id ";

            query += " order by et.complaint_table_id desc ";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                Enquiry bean = new Enquiry();
                bean.setEnquiry_table_id(rst.getInt("complaint_table_id"));
//                bean.setEnquiry_source(rst.getString("enquiry_source"));
//                bean.setMarketing_vertical_name(rst.getString("marketing_vertical_name"));
                bean.setStatus(rst.getString("status"));
                bean.setEnquiry_no(rst.getString("enquiry_no"));
                bean.setSender_name(rst.getString("sender_name"));
                bean.setSender_email(rst.getString("sender_email"));
                bean.setSender_mob(rst.getString("sender_mob"));
                bean.setSender_company_name(rst.getString("sender_company_name"));
                bean.setEnquiry_address(rst.getString("enquiry_address"));
                bean.setEnquiry_city(rst.getString("enquiry_city"));
                bean.setEnquiry_state(rst.getString("enquiry_state"));
                bean.setCountry(rst.getString("country"));
                bean.setEnquiry_message(rst.getString("enquiry_message"));
                bean.setEnquiry_date_time(rst.getString("enquiry_date_time"));
                bean.setEnquiry_call_duration(rst.getString("enquiry_call_duration"));
                bean.setEnquiry_reciever_mob(rst.getString("enquiry_reciever_mob"));
                bean.setSender_alternate_email(rst.getString("sender_alternate_email"));
                bean.setSender_alternate_mob(rst.getString("sender_alternate_mob"));
                bean.setDescription(rst.getString("description"));
                bean.setAssigned_to(rst.getString("org_office_name"));

                String enquiry_date_time = rst.getString("enquiry_date_time");
//                String split_arr[] = enquiry_date_time.split(" ");
//                enquiry_date_time = split_arr[0] + " " + split_arr[1];

//                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
                java.util.Date date = new java.util.Date();
                String currentDateString = dateFormatter.format(date);
                java.util.Date currentDate = dateFormatter.parse(currentDateString);
                String pastTimeInSecond = enquiry_date_time;
                java.util.Date pastDate = dateFormatter.parse(pastTimeInSecond);
                String time_ago = timeAgo(currentDate, pastDate);
                bean.setEnquiry_date_time(time_ago);

                list.add(bean);
            }
        } catch (Exception e) {
            System.err.println("Exception------------" + e);
        }

        return list;
    }

    public List<String> getDealersStateWise(String q, String district, String city, String state) {
        List<String> list = new ArrayList<String>();
        try {
            String query_map_district = " select dt.district_name from district dt,city ct,tehsil th,state st,division dv "
                    + " where dt.active='Y' and ct.active='Y' and th.active='Y' and st.active='Y' "
                    + " and dv.active='Y' "
                    + " and ct.tehsil_id=th.tehsil_id and th.district_id=dt.district_id and dt.division_id=dv.division_id "
                    + " and dv.state_id=st.state_id ";
            if (city.equals("") || state.equals("")) {

                query_map_district += " and dt.district_name='" + district + "' ";
            }
            if (!city.equals("") && city != null) {
                query_map_district += " and ct.city_name='" + city + "' ";
            }
            if (!state.equals("") && state != null) {
                query_map_district += " and st.state_name='" + state + "' ";
            }
            PreparedStatement pstmt2 = connection.prepareStatement(query_map_district);
            ResultSet rs2 = pstmt2.executeQuery();
            String district_name = "";
            while (rs2.next()) {
                district_name = rs2.getString("district_name");
            }
            if (!district_name.equals("")) {
                district = district_name;
            } else {
                district = "Hapur";
            }
            String query = " select oo.org_office_name  from state st,salesmanager_state_mapping ssm,org_office oo,city ct,tehsil th, "
                    + " district dt,division dv,key_person kp "
                    + " where st.active='Y' and ssm.active='Y' and oo.active='Y' and ct.active='Y' and dt.active='Y' and th.active='Y' "
                    + " and dv.active='Y' "
                    + " and oo.city_id=ct.city_id and ct.tehsil_id=th.tehsil_id and th.district_id=dt.district_id and  "
                    + " kp.active='Y' and dt.division_id=dv.division_id and dv.state_id=st.state_id "
                    + " and ssm.state_id=st.state_id  and ssm.salesman_id=kp.key_person_id and oo.office_type_id=3 "
                    + " and dt.district_name='" + district + "' ";

            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String org_office_name = (rset.getString("org_office_name"));
                if (org_office_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(org_office_name);
                    count++;
                }
            }

            if (count == 0) {
                list.add("No such org_office_name  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:EnquiryModel--getDealersStateWise()-- " + e);
        }
        return list;
    }

    public String assignToDealer(String enquiry_table_id, String dealer_name) {
        int revision = EnquiryModel.getRevisionno(enquiry_table_id);
        int updateRowsAffected = 0;
        boolean status = false;
        String query1 = " SELECT max(revision_no) revision_no,enquiry_source_table_id,marketing_vertical_id,enquiry_status_id,enquiry_no,sender_name, "
                + " sender_email,sender_mob,sender_company_name,enquiry_address,enquiry_city,enquiry_state,country,enquiry_message,enquiry_date_time, "
                + " enquiry_call_duration, "
                + " enquiry_reciever_mob,sender_alternate_email,sender_alternate_mob,description,assigned_to "
                + " FROM enquiry_table WHERE enquiry_table_id = " + enquiry_table_id + "  && active=? ";
        String query2 = " UPDATE enquiry_table SET active=? WHERE enquiry_table_id=? and revision_no=?";
        String query3 = " INSERT INTO enquiry_table(enquiry_table_id,enquiry_source_table_id,marketing_vertical_id,enquiry_status_id,enquiry_no,sender_name,sender_email, "
                + " sender_mob,sender_company_name,enquiry_address,enquiry_city,enquiry_state,country,enquiry_message,enquiry_date_time,enquiry_call_duration,"
                + " enquiry_reciever_mob,sender_alternate_email,sender_alternate_mob, "
                + " revision_no,active,description,assigned_to) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        int rowsAffected = 0;
        int assigned_to = getKeyPersonId(dealer_name);
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
            pstmt.setString(1, "Y");
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                revision = rs.getInt("revision_no");
                String enquiry_source_table_id = rs.getString("enquiry_source_table_id");
                String marketing_vertical_id = rs.getString("marketing_vertical_id");
                String enquiry_no = rs.getString("enquiry_no");
                String sender_name = rs.getString("sender_name");
                String sender_email = rs.getString("sender_email");
                String sender_mob = rs.getString("sender_mob");
                String sender_company_name = rs.getString("sender_company_name");
                String enquiry_address = rs.getString("enquiry_address");
                String enquiry_city = rs.getString("enquiry_city");
                String enquiry_state = rs.getString("enquiry_state");
                String country = rs.getString("country");
                String enquiry_message = rs.getString("enquiry_message");
                String enquiry_date_time = rs.getString("enquiry_date_time");
                String enquiry_call_duration = rs.getString("enquiry_call_duration");
                String enquiry_reciever_mob = rs.getString("enquiry_reciever_mob");
                String sender_alternate_email = rs.getString("sender_alternate_email");
                String sender_alternate_mob = rs.getString("sender_alternate_mob");
                String description = rs.getString("description");

                PreparedStatement pstm = connection.prepareStatement(query2);
                pstm.setString(1, "n");
                pstm.setString(2, enquiry_table_id);
                pstm.setInt(3, revision);
                updateRowsAffected = pstm.executeUpdate();
                if (updateRowsAffected >= 1) {
                    revision = rs.getInt("revision_no") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                    psmt.setString(1, enquiry_table_id);
                    psmt.setString(2, enquiry_source_table_id);
                    psmt.setString(3, marketing_vertical_id);
                    psmt.setInt(4, 3);
                    psmt.setString(5, enquiry_no);
                    psmt.setString(6, sender_name);
                    psmt.setString(7, sender_email);
                    psmt.setString(8, sender_mob);
                    psmt.setString(9, sender_company_name);
                    psmt.setString(10, enquiry_address);
                    psmt.setString(11, enquiry_city);
                    psmt.setString(12, enquiry_state);
                    psmt.setString(13, country);
                    psmt.setString(14, enquiry_message);
                    psmt.setString(15, enquiry_date_time);
                    psmt.setString(16, enquiry_call_duration);
                    psmt.setString(17, enquiry_reciever_mob);
                    psmt.setString(18, sender_alternate_email);
                    psmt.setString(19, sender_alternate_mob);
                    psmt.setInt(20, revision);
                    psmt.setString(21, "Y");
                    psmt.setString(22, description);
                    psmt.setInt(23, assigned_to);

                    rowsAffected = psmt.executeUpdate();
                    if (rowsAffected > 0) {
                        status = true;

                    } else {
                        status = false;
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("Error:EnquiryModel assignToSalesPerson-" + e);
        }
        if (rowsAffected > 0) {
            message = " Your Enquiry successfully assigend !.";
            messageBGColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            messageBGColor = COLOR_ERROR;
        }
        return message + "&" + status;

    }

    public String assignComplaintToDealer(String complaint_table_id, String dealer_name) {
        int revision = EnquiryModel.getRevisionnoForComplaint(complaint_table_id);
        int updateRowsAffected = 0;
        boolean status = false;
        String query1 = " SELECT max(revision_no) revision_no,enquiry_source_table_id,marketing_vertical_id,enquiry_status_id,enquiry_no,sender_name, "
                + " sender_email,sender_mob,sender_company_name,enquiry_address,enquiry_city,enquiry_state,country,enquiry_message,enquiry_date_time, "
                + " enquiry_call_duration, "
                + " enquiry_reciever_mob,sender_alternate_email,sender_alternate_mob,description,assigned_to "
                + " FROM complaint_table WHERE complaint_table_id = " + complaint_table_id + "  && active=? ";
        String query2 = " UPDATE complaint_table SET active=? WHERE complaint_table_id=? and revision_no=?";
        String query3 = " INSERT INTO complaint_table(complaint_table_id,enquiry_source_table_id,marketing_vertical_id,enquiry_status_id,enquiry_no,sender_name,sender_email, "
                + " sender_mob,sender_company_name,enquiry_address,enquiry_city,enquiry_state,country,enquiry_message,enquiry_date_time,enquiry_call_duration,"
                + " enquiry_reciever_mob,sender_alternate_email,sender_alternate_mob, "
                + " revision_no,active,description,assigned_to) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        int rowsAffected = 0;
        int assigned_to = getKeyPersonId(dealer_name);
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
            pstmt.setString(1, "Y");
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                revision = rs.getInt("revision_no");
                String enquiry_source_table_id = rs.getString("enquiry_source_table_id");
                String marketing_vertical_id = rs.getString("marketing_vertical_id");
                String enquiry_no = rs.getString("enquiry_no");
                String sender_name = rs.getString("sender_name");
                String sender_email = rs.getString("sender_email");
                String sender_mob = rs.getString("sender_mob");
                String sender_company_name = rs.getString("sender_company_name");
                String enquiry_address = rs.getString("enquiry_address");
                String enquiry_city = rs.getString("enquiry_city");
                String enquiry_state = rs.getString("enquiry_state");
                String country = rs.getString("country");
                String enquiry_message = rs.getString("enquiry_message");
                String enquiry_date_time = rs.getString("enquiry_date_time");
                String enquiry_call_duration = rs.getString("enquiry_call_duration");
                String enquiry_reciever_mob = rs.getString("enquiry_reciever_mob");
                String sender_alternate_email = rs.getString("sender_alternate_email");
                String sender_alternate_mob = rs.getString("sender_alternate_mob");
                String description = rs.getString("description");

                PreparedStatement pstm = connection.prepareStatement(query2);
                pstm.setString(1, "n");
                pstm.setString(2, complaint_table_id);
                pstm.setInt(3, revision);
                updateRowsAffected = pstm.executeUpdate();
                if (updateRowsAffected >= 1) {
                    revision = rs.getInt("revision_no") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                    psmt.setString(1, complaint_table_id);
                    psmt.setString(2, enquiry_source_table_id);
                    psmt.setString(3, marketing_vertical_id);
                    psmt.setInt(4, 3);
                    psmt.setString(5, enquiry_no);
                    psmt.setString(6, sender_name);
                    psmt.setString(7, sender_email);
                    psmt.setString(8, sender_mob);
                    psmt.setString(9, sender_company_name);
                    psmt.setString(10, enquiry_address);
                    psmt.setString(11, enquiry_city);
                    psmt.setString(12, enquiry_state);
                    psmt.setString(13, country);
                    psmt.setString(14, enquiry_message);
                    psmt.setString(15, enquiry_date_time);
                    psmt.setString(16, enquiry_call_duration);
                    psmt.setString(17, enquiry_reciever_mob);
                    psmt.setString(18, sender_alternate_email);
                    psmt.setString(19, sender_alternate_mob);
                    psmt.setInt(20, revision);
                    psmt.setString(21, "Y");
                    psmt.setString(22, description);
                    psmt.setInt(23, assigned_to);

                    rowsAffected = psmt.executeUpdate();
                    if (rowsAffected > 0) {
                        status = true;
                    } else {
                        status = false;
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("Error:EnquiryModel assignComplaintToSalesPerson-" + e);
        }
        if (rowsAffected > 0) {
            message = " Your Enquiry successfully assigend !.";
            messageBGColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            messageBGColor = COLOR_ERROR;
        }
        return message + "&" + status;
    }

    public int getKeyPersonId(String dealer) {

        String query = " SELECT kp.key_person_id from key_person kp,org_office oo where oo.active='Y' and kp.active='Y' "
                + " and kp.org_office_id=oo.org_office_id ";
        query += " and oo.org_office_name='" + dealer + "' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("key_person_id");
        } catch (Exception e) {
            System.out.println("EnquiryModel getKeyPersonId Error: " + e);
        }
        return id;
    }

    public int getOrderTableId(String order_no) {

        String query = " select order_table_id from order_table where order_no='" + order_no + "' and active='Y' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("order_table_id");
        } catch (Exception e) {
            System.out.println("EnquiryModel getOrderTableId Error: " + e);
        }
        return id;
    }

    public int getPaymentTypeId(String payment_type) {

        String query = " select payment_type_id from payment_type where payment_type='" + payment_type + "' and active='Y' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("payment_type_id");
        } catch (Exception e) {
            System.out.println("EnquiryModel getPaymentTypeId Error: " + e);
        }
        return id;
    }

    public ArrayList<DealersOrder> getAllPendingOrders(String logged_key_person, String user_role, String order_status) {
        ArrayList<DealersOrder> list = new ArrayList<DealersOrder>();

        String query = " select odt.order_no,odt.date_time,odt.description  ,s.status,kp2.key_person_name as requested_to,odt.order_table_id ,"
                + " SUM(osp.prices) as prices,kp1.key_person_name as requested_by  "
                + " from  order_table odt,key_person kp1,key_person kp2,  status s,order_item odi,payment_mode pm,orders_sales_pricing osp, "
                + " dealer_salesmanager_mapping dsm "
                + " where odt.requested_to=kp2.key_person_id  and odt.requested_by=kp1.key_person_id   and odt.status_id in(2,6,3)"
                + " and odi.status_id in (2,6,3) "
                + " and odt.status_id=s.status_id and odt.active='Y' and kp1.active='Y' and kp2.active='Y'  and odi.active='Y' "
                + " and pm.active='Y' and odt.order_table_id=odi.order_table_id and pm.order_id=odt.order_table_id and "
                + " osp.order_id=odt.order_table_id and dsm.dealer_id=kp1.key_person_id and dsm.salesman_id=kp2.key_person_id "
                + " and osp.order_item_id =odi.order_item_id and dsm.active='Y' ";

        if (user_role.equals("Dealer")) {
            if (!logged_key_person.equals("") && logged_key_person != null) {
                query += " and kp1.key_person_name='" + logged_key_person + "' ";
            }
        }
        if (user_role.equals("Sales")) {
            if (!logged_key_person.equals("") && logged_key_person != null) {
                query += " and kp2.key_person_name='" + logged_key_person + "' ";
            }
        }
        if (!order_status.equals("") && order_status != null) {
            query += " and s.status='" + order_status + "' ";
        }

        query += " group by odt.order_table_id order by odt.order_no desc ";
        int prices = 0;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                DealersOrder bean = new DealersOrder();
                bean.setOrder_no(rset.getString("order_no"));
                bean.setDate_time(rset.getString("date_time"));
                String status = rset.getString("status");
                bean.setStatus(status);
                bean.setRequested_to(rset.getString("requested_to"));
                bean.setDescription(rset.getString("description"));
                bean.setOrder_table_id(rset.getInt("order_table_id"));
                bean.setRequested_by(rset.getString("requested_by"));
//                prices = prices + (rset.getInt("prices"));

                bean.setBasic_price(rset.getString("prices"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: OrderModel showdata-" + e);
        }
        return list;
    }

    public int orderCheckout(DealersOrder bean, String logged_user_name, int logged_key_person_id) throws SQLException {
        String query = "INSERT INTO order_checkout(order_table_id,key_person_id,payment_type_id,"
                + " transaction_no,mobile_no,billing_address,shipping_address,total_amount,total_discount,discounted_amount,"
                + " active,remark,date_time,revision_no) "
                + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
        int rowsAffected = 0;
        int updateRowsAffected = 0;
        int updateRowsAffected2 = 0;
        int requested_by_id = getRequestedKeyPersonId(logged_user_name);
        int requested_to_id = getRequestedKeyPersonId(bean.getRequested_to());
        int order_table_id = getOrderTableId(bean.getOrder_no());
        int payment_type_id = getPaymentTypeId(bean.getPayment_type());
        int count = 0;
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date_time = sdf.format(date);

        try {
            connection.setAutoCommit(false);
            String exist_query = " select count(*) as count from order_checkout where active='Y' and order_table_id='" + order_table_id + "' ";

            PreparedStatement pstmt1 = connection.prepareStatement(exist_query);
            ResultSet rst = pstmt1.executeQuery();
            while (rst.next()) {
                count = rst.getInt("count");

            }
            if (count > 0) {
                message = "Can't Checkout Again!..";
                messageBGColor = COLOR_ERROR;
            } else {
                PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, order_table_id);
                pstmt.setInt(2, logged_key_person_id);
                pstmt.setInt(3, payment_type_id);
                pstmt.setString(4, bean.getTransaction_no());
                pstmt.setString(5, bean.getMobile_no());
                pstmt.setString(6, bean.getBilling_address());
                pstmt.setString(7, bean.getShipping_address());
                pstmt.setString(8, bean.getTotal_amount());
                pstmt.setString(9, bean.getDiscount_percent());
                pstmt.setString(10, bean.getDiscount_price());
                pstmt.setString(11, "Y");
                pstmt.setString(12, "OK");
                pstmt.setString(13, date_time);
                pstmt.setInt(14, bean.getRevision_no());
                rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    String query4 = " select approved_qty,order_item_id from order_table odt,order_item odi where odt.order_table_id=odi.order_table_id "
                            + " and odt.active='Y' and odi.active='Y' and odt.order_table_id='" + order_table_id + "' ";

                    PreparedStatement pstmt4 = connection.prepareStatement(query4);
                    ResultSet rst4 = pstmt4.executeQuery();
                    int approved_qty = 0;
                    int order_item_id = 0;
                    while (rst4.next()) {
                        approved_qty = rst4.getInt("approved_qty");
                        order_item_id = rst4.getInt("order_item_id");

                        String query2 = " UPDATE order_item SET status_id=?,deliver_qty=? WHERE order_table_id=? and order_item_id=? ";

                        PreparedStatement pstm2 = connection.prepareStatement(query2);
                        pstm2.setInt(1, 13);
                        pstm2.setInt(2, approved_qty);
                        pstm2.setInt(3, order_table_id);
                        pstm2.setInt(4, order_item_id);
                        updateRowsAffected = pstm2.executeUpdate();

                    }

                    String query3 = " update order_table set status_id=? where order_table_id=? ";
                    PreparedStatement pstm3 = connection.prepareStatement(query3);
                    pstm3.setInt(1, 13);
                    pstm3.setInt(2, order_table_id);
                    updateRowsAffected2 = pstm3.executeUpdate();
                }
            }
        } catch (Exception e) {
            System.out.println("OrderModel orderCheckout() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            messageBGColor = COLOR_OK;
            connection.commit();

        } else {
            message = "Cannot save the record, some error.";
            messageBGColor = COLOR_ERROR;
            connection.rollback();

        }
        if (count > 0) {
            message = "Indent No. Already Exists!..";
            messageBGColor = COLOR_ERROR;
        }

        return rowsAffected;
    }

    public String changeEnquiryStatus(String enquiry_status, String enquiry_table_id) throws SQLException {
        int revision = EnquiryModel.getRevisionno(enquiry_table_id);
        int updateRowsAffected = 0;
        boolean status = false;
        String query1 = " SELECT max(revision_no) revision_no,enquiry_source_table_id,marketing_vertical_id,enquiry_status_id,enquiry_no,sender_name, "
                + " sender_email,sender_mob,sender_company_name,enquiry_address,enquiry_city,enquiry_state,country,enquiry_message,enquiry_date_time, "
                + " enquiry_call_duration, "
                + " enquiry_reciever_mob,sender_alternate_email,sender_alternate_mob,description,assigned_to "
                + " FROM enquiry_table WHERE enquiry_table_id = " + enquiry_table_id + "  && active=? ";
        String query2 = " UPDATE enquiry_table SET active=? WHERE enquiry_table_id=? and revision_no=?";
        String query3 = " INSERT INTO enquiry_table(enquiry_table_id,enquiry_source_table_id,marketing_vertical_id,enquiry_status_id,enquiry_no,sender_name,sender_email, "
                + " sender_mob,sender_company_name,enquiry_address,enquiry_city,enquiry_state,country,enquiry_message,enquiry_date_time,enquiry_call_duration,"
                + " enquiry_reciever_mob,sender_alternate_email,sender_alternate_mob, "
                + " revision_no,active,description,assigned_to) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        int rowsAffected = 0;
//        int assigned_to = getKeyPersonId(dealer_name);
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
            pstmt.setString(1, "Y");
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                revision = rs.getInt("revision_no");
                String enquiry_source_table_id = rs.getString("enquiry_source_table_id");
                String marketing_vertical_id = rs.getString("marketing_vertical_id");
                String enquiry_no = rs.getString("enquiry_no");
                String sender_name = rs.getString("sender_name");
                String sender_email = rs.getString("sender_email");
                String sender_mob = rs.getString("sender_mob");
                String sender_company_name = rs.getString("sender_company_name");
                String enquiry_address = rs.getString("enquiry_address");
                String enquiry_city = rs.getString("enquiry_city");
                String enquiry_state = rs.getString("enquiry_state");
                String country = rs.getString("country");
                String enquiry_message = rs.getString("enquiry_message");
                String enquiry_date_time = rs.getString("enquiry_date_time");
                String enquiry_call_duration = rs.getString("enquiry_call_duration");
                String enquiry_reciever_mob = rs.getString("enquiry_reciever_mob");
                String sender_alternate_email = rs.getString("sender_alternate_email");
                String sender_alternate_mob = rs.getString("sender_alternate_mob");
                String description = rs.getString("description");
                int assigned_to = rs.getInt("assigned_to");
                int enquiry_status_id = 0;
                if (enquiry_status.equals("Enquiry Passed")) {
                    enquiry_status_id = 6;
                }
                if (enquiry_status.equals("Enquiry Failed")) {
                    enquiry_status_id = 5;
                }

                PreparedStatement pstm = connection.prepareStatement(query2);
                pstm.setString(1, "n");
                pstm.setString(2, enquiry_table_id);
                pstm.setInt(3, revision);
                updateRowsAffected = pstm.executeUpdate();
                if (updateRowsAffected >= 1) {
                    revision = rs.getInt("revision_no") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                    psmt.setString(1, enquiry_table_id);
                    psmt.setString(2, enquiry_source_table_id);
                    psmt.setString(3, marketing_vertical_id);
                    psmt.setInt(4, enquiry_status_id);
                    psmt.setString(5, enquiry_no);
                    psmt.setString(6, sender_name);
                    psmt.setString(7, sender_email);
                    psmt.setString(8, sender_mob);
                    psmt.setString(9, sender_company_name);
                    psmt.setString(10, enquiry_address);
                    psmt.setString(11, enquiry_city);
                    psmt.setString(12, enquiry_state);
                    psmt.setString(13, country);
                    psmt.setString(14, enquiry_message);
                    psmt.setString(15, enquiry_date_time);
                    psmt.setString(16, enquiry_call_duration);
                    psmt.setString(17, enquiry_reciever_mob);
                    psmt.setString(18, sender_alternate_email);
                    psmt.setString(19, sender_alternate_mob);
                    psmt.setInt(20, revision);
                    psmt.setString(21, "Y");
                    psmt.setString(22, description);
                    psmt.setInt(23, assigned_to);

                    rowsAffected = psmt.executeUpdate();
                    if (rowsAffected > 0) {
                        status = true;

                    } else {
                        status = false;
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("Error:EnquiryModel assignToSalesPerson-" + e);
        }
        if (rowsAffected > 0) {
            message = " Your Enquiry successfully assigend !.";
            messageBGColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            messageBGColor = COLOR_ERROR;
        }
        return enquiry_status;
    }

    public String changeComplaintEnquiryStatus(String enquiry_status, String complaint_table_id) throws SQLException {
        int revision = EnquiryModel.getRevisionnoForComplaint(complaint_table_id);
        int updateRowsAffected = 0;
        boolean status = false;
        String query1 = " SELECT max(revision_no) revision_no,enquiry_source_table_id,marketing_vertical_id,enquiry_status_id,enquiry_no,sender_name, "
                + " sender_email,sender_mob,sender_company_name,enquiry_address,enquiry_city,enquiry_state,country,enquiry_message,enquiry_date_time, "
                + " enquiry_call_duration, "
                + " enquiry_reciever_mob,sender_alternate_email,sender_alternate_mob,description,assigned_to "
                + " FROM complaint_table WHERE complaint_table_id = " + complaint_table_id + "  && active=? ";
        String query2 = " UPDATE complaint_table SET active=? WHERE complaint_table_id=? and revision_no=?";
        String query3 = " INSERT INTO complaint_table(complaint_table_id,enquiry_source_table_id,marketing_vertical_id,enquiry_status_id,enquiry_no,sender_name,sender_email, "
                + " sender_mob,sender_company_name,enquiry_address,enquiry_city,enquiry_state,country,enquiry_message,enquiry_date_time,enquiry_call_duration,"
                + " enquiry_reciever_mob,sender_alternate_email,sender_alternate_mob, "
                + " revision_no,active,description,assigned_to) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        int rowsAffected = 0;
//        int assigned_to = getKeyPersonId(dealer_name);
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
            pstmt.setString(1, "Y");
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                revision = rs.getInt("revision_no");
                String enquiry_source_table_id = rs.getString("enquiry_source_table_id");
                String marketing_vertical_id = rs.getString("marketing_vertical_id");
                String enquiry_no = rs.getString("enquiry_no");
                String sender_name = rs.getString("sender_name");
                String sender_email = rs.getString("sender_email");
                String sender_mob = rs.getString("sender_mob");
                String sender_company_name = rs.getString("sender_company_name");
                String enquiry_address = rs.getString("enquiry_address");
                String enquiry_city = rs.getString("enquiry_city");
                String enquiry_state = rs.getString("enquiry_state");
                String country = rs.getString("country");
                String enquiry_message = rs.getString("enquiry_message");
                String enquiry_date_time = rs.getString("enquiry_date_time");
                String enquiry_call_duration = rs.getString("enquiry_call_duration");
                String enquiry_reciever_mob = rs.getString("enquiry_reciever_mob");
                String sender_alternate_email = rs.getString("sender_alternate_email");
                String sender_alternate_mob = rs.getString("sender_alternate_mob");
                String description = rs.getString("description");
                int assigned_to = rs.getInt("assigned_to");
                int enquiry_status_id = 0;
                if (enquiry_status.equals("Enquiry Passed")) {
                    enquiry_status_id = 6;
                }
                if (enquiry_status.equals("Enquiry Failed")) {
                    enquiry_status_id = 5;
                }

                PreparedStatement pstm = connection.prepareStatement(query2);
                pstm.setString(1, "n");
                pstm.setString(2, complaint_table_id);
                pstm.setInt(3, revision);
                updateRowsAffected = pstm.executeUpdate();
                if (updateRowsAffected >= 1) {
                    revision = rs.getInt("revision_no") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                    psmt.setString(1, complaint_table_id);
                    psmt.setString(2, enquiry_source_table_id);
                    psmt.setString(3, marketing_vertical_id);
                    psmt.setInt(4, enquiry_status_id);
                    psmt.setString(5, enquiry_no);
                    psmt.setString(6, sender_name);
                    psmt.setString(7, sender_email);
                    psmt.setString(8, sender_mob);
                    psmt.setString(9, sender_company_name);
                    psmt.setString(10, enquiry_address);
                    psmt.setString(11, enquiry_city);
                    psmt.setString(12, enquiry_state);
                    psmt.setString(13, country);
                    psmt.setString(14, enquiry_message);
                    psmt.setString(15, enquiry_date_time);
                    psmt.setString(16, enquiry_call_duration);
                    psmt.setString(17, enquiry_reciever_mob);
                    psmt.setString(18, sender_alternate_email);
                    psmt.setString(19, sender_alternate_mob);
                    psmt.setInt(20, revision);
                    psmt.setString(21, "Y");
                    psmt.setString(22, description);
                    psmt.setInt(23, assigned_to);

                    rowsAffected = psmt.executeUpdate();
                    if (rowsAffected > 0) {
                        status = true;

                    } else {
                        status = false;
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("Error:EnquiryModel assignToSalesPerson-" + e);
        }
        if (rowsAffected > 0) {
            message = " Your Enquiry successfully assigend !.";
            messageBGColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            messageBGColor = COLOR_ERROR;
        }
        return enquiry_status;
    }

    public JSONArray getAllSalesEnquiries(String sales_enquiry_source) {

        PreparedStatement pstmt = null;
        ResultSet rset = null;
        int enquiry_source_id = 0;
        JSONObject obj = new JSONObject();
        JSONArray arrayObj = new JSONArray();
        if (sales_enquiry_source.equals("IndiaMART")) {
            enquiry_source_id = 1;
        }
        if (sales_enquiry_source.equals("Other")) {
            enquiry_source_id = 0;
        }
        String resolved_query = " select count(*) as count  from enquiry_table where enquiry_status_id in(6) and active='Y' ";
        if (sales_enquiry_source.equals("IndiaMART") || sales_enquiry_source.equals("Other")) {
            resolved_query += " and enquiry_source_table_id in('" + enquiry_source_id + "') ";
        }
        if (sales_enquiry_source.equals("") || sales_enquiry_source.equals("All")) {
            resolved_query += " and enquiry_source_table_id in(1,0) ";
        }

        String unresolved_query = " select count(*) as count from enquiry_table where enquiry_status_id in(5) and active='Y' ";
        if (sales_enquiry_source.equals("IndiaMART") || sales_enquiry_source.equals("Other")) {
            unresolved_query += " and enquiry_source_table_id in('" + enquiry_source_id + "') ";
        }
        if (sales_enquiry_source.equals("") || sales_enquiry_source.equals("All")) {
            unresolved_query += " and enquiry_source_table_id in(1,0) ";
        }

        String assigned_query = " select count(*) as count from enquiry_table where enquiry_status_id in(2,3,4) and active='Y' ";
        if (sales_enquiry_source.equals("IndiaMART") || sales_enquiry_source.equals("Other")) {
            assigned_query += " and enquiry_source_table_id in('" + enquiry_source_id + "') ";
        }
        if (sales_enquiry_source.equals("") || sales_enquiry_source.equals("All")) {
            assigned_query += " and enquiry_source_table_id in(1,0) ";
        }
        String pending_query = " select count(*) as count from enquiry_table where enquiry_status_id in(1) and active='Y' ";
        if (sales_enquiry_source.equals("IndiaMART") || sales_enquiry_source.equals("Other")) {
            pending_query += " and enquiry_source_table_id in('" + enquiry_source_id + "') ";
        }
        if (sales_enquiry_source.equals("") || sales_enquiry_source.equals("All")) {
            pending_query += " and enquiry_source_table_id in(1,0) ";
        }

        int resolved_enquiry_count = 0;
        int unresolved_enquiry_count = 0;
        int assigned_enquiry_count = 0;
        int pending_enquiry_count = 0;
        try {
            pstmt = connection.prepareStatement(resolved_query);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                resolved_enquiry_count = rset.getInt("count");
            }

            pstmt = null;
            rset = null;
            pstmt = connection.prepareStatement(unresolved_query);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                unresolved_enquiry_count = rset.getInt("count");
            }

            pstmt = null;
            rset = null;
            pstmt = connection.prepareStatement(assigned_query);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                assigned_enquiry_count = rset.getInt("count");
            }

            pstmt = null;
            rset = null;
            pstmt = connection.prepareStatement(pending_query);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                pending_enquiry_count = rset.getInt("count");
            }
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("resolved_enquiry_count", resolved_enquiry_count);
            jsonObj.put("unresolved_enquiry_count", unresolved_enquiry_count);
            jsonObj.put("assigned_enquiry_count", assigned_enquiry_count);
            jsonObj.put("pending_enquiry_count", pending_enquiry_count);

            arrayObj.add(jsonObj);
        } catch (Exception e) {
            System.out.println(e);
        }
        return arrayObj;
    }

    public JSONArray getAllComplaintEnquiries() {

        PreparedStatement pstmt = null;
        ResultSet rset = null;
        JSONObject obj = new JSONObject();
        JSONArray arrayObj = new JSONArray();
        String resolved_query = " select count(*) as count  from complaint_table where enquiry_status_id in(6) and active='Y' ";
        String unresolved_query = " select count(*) as count from complaint_table where enquiry_status_id in(5) and active='Y' ";
        String assigned_query = " select count(*) as count from complaint_table where enquiry_status_id in(2,3,4) and active='Y' ";
        String pending_query = " select count(*) as count from complaint_table where enquiry_status_id in(1) and active='Y' ";

        int resolved_enquiry_count = 0;
        int unresolved_enquiry_count = 0;
        int assigned_enquiry_count = 0;
        int pending_enquiry_count = 0;
        try {
            pstmt = connection.prepareStatement(resolved_query);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                resolved_enquiry_count = rset.getInt("count");
            }

            pstmt = null;
            rset = null;
            pstmt = connection.prepareStatement(unresolved_query);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                unresolved_enquiry_count = rset.getInt("count");
            }

            pstmt = null;
            rset = null;
            pstmt = connection.prepareStatement(assigned_query);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                assigned_enquiry_count = rset.getInt("count");
            }

            pstmt = null;
            rset = null;
            pstmt = connection.prepareStatement(pending_query);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                pending_enquiry_count = rset.getInt("count");
            }
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("resolved_enquiry_count", resolved_enquiry_count);
            jsonObj.put("unresolved_enquiry_count", unresolved_enquiry_count);
            jsonObj.put("assigned_enquiry_count", assigned_enquiry_count);
            jsonObj.put("pending_enquiry_count", pending_enquiry_count);

            arrayObj.add(jsonObj);
        } catch (Exception e) {
            System.out.println(e);
        }
        return arrayObj;
    }

    public static ArrayList<DealersOrder> getAllLatestItems(String logged_org_office_id) {
        ArrayList<DealersOrder> list = new ArrayList<DealersOrder>();

        try {
            String query = " select itn.item_name,mr.manufacturer_name,m.model,m.model_id,iid.image_path,iid.image_name,m.description "
                    + " ,m.basic_price,inv.stock_quantity  from item_names itn, manufacturer_item_map mim,model m,item_authorization ia, "
                    + " designation d,manufacturer mr,"
                    + " item_image_details iid,inventory_basic ib,inventory inv,org_office oo "
                    + " where itn.active='Y' and mim.active='Y' and m.active='Y' and d.active='Y' and mr.active='Y' "
                    + " and iid.active='Y'  and iid.model_id=m.model_id and mr.manufacturer_id=mim.manufacturer_id and ib.active='Y' "
                    + " and inv.active='Y'  and ia.active='Y' "
                    + " and itn.item_names_id= mim.item_names_id and mim.manufacturer_item_map_id=m.manufacturer_item_map_id "
                    + " and ia.item_names_id=itn.item_names_id and ib.item_names_id=itn.item_names_id "
                    + " and ib.model_id=m.model_id  and ib.inventory_basic_id=inv.inventory_basic_id and oo.active='Y'"
                    + "  and  d.designation_id=ia.designation_id ";
            query += " and itn.parent_id='717' group by m.model order by itn.created_at desc";

            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                DealersOrder bean = new DealersOrder();
                String manufacturer_name = rst.getString("manufacturer_name");
                String model = rst.getString("model");
                int model_id = rst.getInt("model_id");
                String image_path = rst.getString("image_path");
                String image_name = rst.getString("image_name");
                String basic_price = rst.getString("basic_price");
                String stock_quantity = rst.getString("stock_quantity");
                bean.setItem_name(rst.getString("item_name"));
                bean.setManufacturer_name(manufacturer_name);
                bean.setModel(model);
                bean.setModel_id(model_id);
                bean.setImage_path(image_path);
                bean.setImage_name(image_name);
                bean.setBasic_price(basic_price);
                bean.setStock_quantity(stock_quantity);
                list.add(bean);
            }
        } catch (Exception e) {
            System.err.println("Exception------------" + e);
        }

        return list;
    }

//    public static ArrayList<DealerItemMap> getAllModels(String logged_org_office_id, List<DealerItemMap> list2) {
//        ArrayList<DealerItemMap> list = new ArrayList<DealerItemMap>();
//        if (list2.size() > 0) {
//            for (int i = 0; i < list2.size(); i++) {
//                try {
//
//                    String query = " select itn.item_name,mr.manufacturer_name,m.model,m.model_id,iid.image_path,iid.image_name,m.description "
//                            + " ,m.basic_price,inv.stock_quantity  from item_names itn, manufacturer_item_map mim,model m,item_authorization ia, "
//                            + " designation d,manufacturer mr,"
//                            + " item_image_details iid,inventory_basic ib,inventory inv,org_office oo "
//                            + " where itn.active='Y' and mim.active='Y' and m.active='Y' and d.active='Y' and mr.active='Y' "
//                            + " and iid.active='Y'  and iid.model_id=m.model_id and mr.manufacturer_id=mim.manufacturer_id and ib.active='Y' "
//                            + " and inv.active='Y'  and ia.active='Y' "
//                            + " and itn.item_names_id= mim.item_names_id and mim.manufacturer_item_map_id=m.manufacturer_item_map_id "
//                            + " and ia.item_names_id=itn.item_names_id and ib.item_names_id=itn.item_names_id "
//                            + " and ib.model_id=m.model_id  and ib.inventory_basic_id=inv.inventory_basic_id and oo.active='Y'"
//                            + "  and  d.designation_id=ia.designation_id ";
//                    query += " and itn.item_name='" + list2.get(i).getItem_name() + "' group by m.model";
//
//                    ResultSet rst = connection.prepareStatement(query).executeQuery();
//                    while (rst.next()) {
//                        DealerItemMap bean = new DealerItemMap();
//                        String manufacturer_name = rst.getString("manufacturer_name");
//                        String model = rst.getString("model");
//                        String model_id = rst.getString("model_id");
//                        String image_path = rst.getString("image_path");
//                        String image_name = rst.getString("image_name");
//                        String basic_price = rst.getString("basic_price");
//                        String stock_quantity = rst.getString("stock_quantity");
//                        bean.setItem_name(rst.getString("item_name"));
//                        bean.setManufacturer_name(manufacturer_name);
//                        bean.setModel(model);
//                        bean.setModel_id(model_id);
//                        bean.setImage_path(image_path);
//                        bean.setImage_name(image_name);
//                        bean.setBasic_price(basic_price);
//                        bean.setStock_quantity(stock_quantity);
//
//                        String query2 = " select dealer_item_map_id from dealer_item_map where active='Y' and  model_id='" + rst.getString("model_id") + "' "
//                                + " and org_office_id='" + logged_org_office_id + "' ";
//                        int count_map = 0;
//                        ResultSet rst2 = connection.prepareStatement(query2).executeQuery();
//                        while (rst2.next()) {
//                            count_map = rst2.getInt("dealer_item_map_id");
//                        }
//                        if (count_map > 0) {
//                            bean.setChecked("Yes");
//                            bean.setDealer_item_map_id(count_map);
//                        } else {
//                            bean.setChecked("No");
////                            bean.setDealer_item_map_id(0);
//                        }
//
//                        list.add(bean);
//
//                    }
//
//                } catch (Exception e) {
//                    System.err.println("Exception------------" + e);
//                }
//
//            }
//        }
//        return list;
//    }
    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("DealersOrderModel closeConnection: " + e);
        }
    }

    public void setConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("DealersOrderModel setConnection error: " + e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public static String getMessage() {
        return message;
    }

    public static String getMessageBGColor() {
        return messageBGColor;
    }

}
