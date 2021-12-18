package com.apl.order.model;

import com.inventory.model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;
import com.inventory.tableClasses.Indent;
import com.inventory.tableClasses.ItemName;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.json.simple.JSONArray;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Komal
 */
public class OrderModel {

    private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "#a2a220";
    private final String COLOR_ERROR = "red";
    int item_id = 0;
    int item_id1 = 0;
    int indent_table_id = 0;
    List<Integer> allIdList = new ArrayList<Integer>();

    public void setConnection(Connection con) {
        try {
            connection = con;
        } catch (Exception e) {
            System.out.println("OrderModel setConnection() Error: " + e);
        }
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

//    public List<Indent> showReportData(String logged_key_person, String office_admin, String orderno, String delivery_challan_date, String delivery_challan_no, String itemname) {
//        List<Indent> list = new ArrayList<Indent>();
//
//        String query = "select required_qty,deliver_qty,approved_qty,kp1.key_person_name as dealer,kp2.key_person_name as SalesPerson "
//                + ",item_name,m.model from model m,manufacturer_item_map mim,order_table ot,order_item indi,key_person  kp1,key_person kp2 ,item_names as itn "
//                + "where indi.order_table_id=ot.order_table_id and indi.model_id=m.model_id \n"
//                + "  and m.active='Y' and mim.item_names_id=itn.item_names_id and mim.active='Y' and m.manufacturer_item_map_id=mim.manufacturer_item_map_id  and "
//                + "kp1.key_person_id=ot.requested_by  and kp2.key_person_id=ot.requested_to and kp1.active='y' "
//                + "and kp2.active='y' and itn.active='Y' and ot.order_no='" + orderno + "'";
//
//        try {
//            ResultSet rset = connection.prepareStatement(query).executeQuery();
//            while (rset.next()) {
//                Indent bean = new Indent();
//
//                bean.setSalesperson(rset.getString("SalesPerson"));
//                bean.setDelivery_challan_no(delivery_challan_no);
//                bean.setDelivery_challan_date(delivery_challan_date);
//                bean.setItemname(rset.getString("item_name"));
//                bean.setOrderno(orderno);
//
//                bean.setDealer(rset.getString("dealer"));
//                bean.setRequested_to(rset.getString("SalesPerson"));
//                bean.setApproved_qty(rset.getInt("approved_qty"));
//                bean.setDelivered_qty(rset.getInt("deliver_qty"));
//                bean.setRequired_qty(rset.getInt("required_qty"));
//                if (rset.getInt("required_qty") == rset.getInt("deliver_qty")) {
//                    bean.setBalance_qty(0);
//                } else {
//                    bean.setBalance_qty(rset.getInt("required_qty") - rset.getInt("deliver_qty"));
//                }
//
//                list.add(bean);
//            }
//        } catch (Exception e) {
//            System.out.println("Error: InventoryModel showdata-" + e);
//        }
//        return list;
//    }
    public List<Indent> showReportData(String logged_key_person, String office_admin, String orderno, String delivery_challan_date, String delivery_challan_no, String itemname) {
        List<Indent> list = new ArrayList<Indent>();

        String amount_in_words = numberToWords(234678);

        String query = " select required_qty,deliver_qty,approved_qty,kp1.key_person_name as requested_by,kp2.key_person_name as requested_to "
                + " ,item_name,m.model,itn.HSNCode,oo2.address_line1 as oo_address_line1,oo2.address_line2 as oo_address_line2,"
                + " oo2.address_line3 as oo_address_line3,c2.city_name as org_office_city,c2.pin_code,oo1.service_tax_reg_no as partyGSTNo,"
                + " oo2.service_tax_reg_no as officeGSTNo,oo2.mobile_no1,oo2.mobile_no2,osp.prices, "
                + " c1.city_name as key_person_city,oo1.org_office_name as requested_by_office, "
                + " oo1.address_line1 as kp_address_line1,oo1.address_line2 as kp_address_line2,oo1.address_line3 as kp_address_line3 "
                + " from model m,manufacturer_item_map mim,order_table indt,order_item indi,key_person kp1,key_person kp2 ,"
                + " item_names as itn,org_office oo1,org_office oo2,city c1,city c2,orders_sales_pricing osp "
                + " where indi.order_table_id=indt.order_table_id and indi.model_id=m.model_id "
                + "  and m.active='Y' and mim.item_names_id=itn.item_names_id and mim.active='Y' "
                + " and m.manufacturer_item_map_id=mim.manufacturer_item_map_id  and "
                + " kp1.key_person_id=indt.requested_by  and kp2.key_person_id=indt.requested_to and kp1.active='y' "
                + " and kp2.active='y' and itn.active='Y' and indt.order_no='" + orderno + "' and oo1.active='Y' and oo2.active='Y'"
                + " and c1.active='Y' and osp.order_id=indt.order_table_id and osp.order_item_id=indi.order_item_id "
                + " and c2.active='Y' "
                + " and kp1.org_office_id=oo1.org_office_id and kp2.org_office_id=oo2.org_office_id "
                + " and oo1.city_id=c1.city_id  and oo2.city_id=c2.city_id  ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int sum_value = 0;
            while (rset.next()) {
                Indent bean = new Indent();

                bean.setRequested_to(rset.getString("requested_to"));
                bean.setDelivery_challan_no(delivery_challan_no);
                bean.setDelivery_challan_date(delivery_challan_date);
                bean.setItemname(rset.getString("item_name"));
                String hsn_code = "";
                if (rset.getString("HSNCode") == null) {
                    hsn_code = "";
                } else {
                    hsn_code = rset.getString("HSNCode");
                }
                bean.setHSNCode(hsn_code);
                String oo_address_line1 = rset.getString("oo_address_line1");
                String oo_address_line2 = rset.getString("oo_address_line2");
                String oo_address_line3 = rset.getString("oo_address_line3");
                String oo_city_name = rset.getString("org_office_city");
                String pin_code = rset.getString("pin_code");

                String kp_address_line1 = rset.getString("kp_address_line1");
                String kp_address_line2 = rset.getString("kp_address_line2");
                String kp_address_line3 = rset.getString("kp_address_line3");
                String kp_city_name = rset.getString("key_person_city");

                String office_address = oo_address_line1 + ", " + oo_address_line2 + ", " + oo_address_line3 + ", " + oo_city_name + "- "
                        + pin_code;
                String key_person_address = kp_address_line1 + ", " + kp_address_line2 + ", " + kp_address_line3 + ", " + kp_city_name;

                bean.setOffice_address(office_address);
                bean.setKey_person_address(key_person_address);
                bean.setModel(rset.getString("model"));
                bean.setIndent_no(orderno);

                bean.setRequested_by(rset.getString("requested_by"));
                bean.setRequested_by_office(rset.getString("requested_by_office") + " (" + rset.getString("requested_by") + ")");
                bean.setPartyGSTNo(rset.getString("partyGSTNo"));
                bean.setOfficeGSTNo(rset.getString("officeGSTNo"));
                // bean.setRequested_to(rset.getString("SalesPerson"));
                bean.setApproved_qty(rset.getInt("approved_qty"));
                bean.setDelivered_qty(rset.getInt("deliver_qty"));
                bean.setRequired_qty(rset.getInt("required_qty"));
                bean.setRate(rset.getString("prices"));
                String value = "";
                value = String.valueOf(Integer.parseInt(rset.getString("prices")) * rset.getInt("deliver_qty"));
                bean.setValue(value);
                sum_value += Integer.valueOf(value);
                bean.setTotal_assessable_value(String.valueOf(sum_value));

                amount_in_words = numberToWords(sum_value);
                bean.setAmount_in_words(amount_in_words);

                String officeMobNo = "";
                officeMobNo = (rset.getString("mobile_no1")) + ", " + (rset.getString("mobile_no2"));
                bean.setOfficeMobNo(officeMobNo);

                if (rset.getInt("required_qty") == rset.getInt("deliver_qty")) {
                    bean.setBalance_qty(0);
                } else {
                    bean.setBalance_qty(rset.getInt("required_qty") - rset.getInt("deliver_qty"));
                }

                list.add(bean);
                // sum_value++;
            }
        } catch (Exception e) {
            System.out.println("Error: OrderModel showReportData-" + e);
        }
        return list;
    }

    static String numberToWords(int number) {
        String words = "";
        String unitsArray[] = {"Zero", "One", "Two", "Three", "Four", "Five", "Six",
            "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve",
            "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen",
            "Eighteen", "Nineteen"};
        String tensArray[] = {"Zero", "Ten", "Twenty", "Thirty", "Forty", "Fifty",
            "Sixty", "Seventy", "Eighty", "Ninety"};

        if (number == 0) {
            return "Zero";
        }
        // add minus before conversion if the number is less than 0
        if (number < 0) {
            // convert the number to a string
            String numberStr = "" + number;
            // remove minus before the number 
            numberStr = numberStr.substring(1);
            // add minus before the number and convert the rest of number 
            return "minus " + numberToWords(Integer.parseInt(numberStr));
        }
        // check if number is divisible by 1 million
        if ((number / 1000000) > 0) {
            words += numberToWords(number / 1000000) + " Million ";
            number %= 1000000;
        }
        // check if number is divisible by 1 thousand
        if ((number / 1000) > 0) {
            words += numberToWords(number / 1000) + " Thousand ";
            number %= 1000;
        }
        // check if number is divisible by 1 hundred
        if ((number / 100) > 0) {
            words += numberToWords(number / 100) + " Hundred ";
            number %= 100;
        }

        if (number > 0) {
            // check if number is within teens
            if (number < 20) {
                // fetch the appropriate value from unit array
                words += unitsArray[number];
            } else {
                // fetch the appropriate value from tens array
                words += tensArray[number / 10];
                if ((number % 10) > 0) {
                    words += "-" + unitsArray[number % 10];
                }
            }
        }
        return words;
    }

    public byte[] generateMapReport(String jrxmlFilePath, List<Indent> listAll) {
        byte[] reportInbytes = null;
        Connection c;
        //     HashMap mymap = new HashMap();
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, beanColDataSource);
        } catch (Exception e) {
            System.out.println("Error: in OrderModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public List<Indent> showData(String logged_key_person, String office_admin, String indent_status, String search_by_date) {
        List<Indent> list = new ArrayList<Indent>();
        if (indent_status.equals("All")) {
            indent_status = "";
        }
        String query = " select indt.order_no,indt.date_time,indt.description "
                + " ,s.status,kp2.key_person_name as requested_to,indt.order_table_id  from "
                + " order_table indt,key_person kp1,key_person kp2, "
                + " status s where indt.requested_to=kp2.key_person_id "
                + " and indt.requested_by=kp1.key_person_id  "
                + " and indt.status_id=s.status_id and indt.active='Y' and kp1.active='Y' and kp2.active='Y'  ";

        if (!logged_key_person.equals("") && logged_key_person != null) {
            query += " and kp1.key_person_name='" + logged_key_person + "' ";
        }
        if (!office_admin.equals("") && office_admin != null) {
            query += " and kp2.key_person_name='" + office_admin + "' ";
        }
        if (!search_by_date.equals("") && search_by_date != null) {
            query += " and indt.date_time like '" + search_by_date + "%' ";
        }

        if (!indent_status.equals("") && indent_status != null) {
            query += " and s.status='" + indent_status + "' ";
        }

        query += " order by indt.order_no desc ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                Indent bean = new Indent();
                bean.setIndent_no(rset.getString("order_no"));
                bean.setDate_time(rset.getString("date_time"));
                String status = rset.getString("status");
                bean.setStatus(status);
                bean.setRequested_to(rset.getString("requested_to"));
                bean.setDescription(rset.getString("description"));
                bean.setIndent_table_id(rset.getInt("order_table_id"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: OrderModel showdata-" + e);
        }
        return list;
    }

    public List<Indent> getStatus() {
        List<Indent> list = new ArrayList<Indent>();

        String query = " select status,status_id from status  order by status";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                Indent bean = new Indent();
                String status = rset.getString("status");
//                if (status.equals("Request Sent")) {
//                    status = "Pending";
//                }
                bean.setStatus_id(rset.getInt("status_id"));
                bean.setStatus(status);

                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: OrderModel getStatus-" + e);
        }
        return list;
    }

    public int insertRecord(Indent bean, String logged_user_name, String office_admin, int i, String paymentmode) throws SQLException {
        String query = "INSERT INTO order_table(order_no,requested_by,requested_to,"
                + " status_id,active,remark,date_time,description,revision_no) "
                + " VALUES(?,?,?,?,?,?,?,?,?) ";
        int rowsAffected2 = 0;
        int rowsAffected = 0;
        int requested_by_id = getRequestedKeyPersonId(logged_user_name);
        int requested_to_id = getRequestedKeyPersonId(bean.getRequested_to());
        int count = 0;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date_time = sdf.format(date);

        try {
            if (i == 0) {
                String query4 = "SELECT count(*) as count FROM order_table WHERE "
                        + " order_no='" + bean.getIndent_no() + "' "
                        + " and active='Y'  ";

                PreparedStatement pstmt1 = connection.prepareStatement(query4);
                ResultSet rs1 = pstmt1.executeQuery();
                while (rs1.next()) {
                    count = rs1.getInt("count");
                }
                if (count > 0) {
                    message = "Indent No. Already Exists!..";
                    msgBgColor = COLOR_ERROR;
                } else {
                    PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    pstmt.setString(1, bean.getIndent_no());
                    pstmt.setInt(2, requested_by_id);
                    pstmt.setInt(3, requested_to_id);
                    pstmt.setInt(4, 2);
                    pstmt.setString(5, "Y");
                    pstmt.setString(6, "OK");
                    pstmt.setString(7, date_time);
                    pstmt.setString(8, bean.getDescription());
                    pstmt.setInt(9, bean.getRevision_no());
                    rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        //rowsAffected = pstmt.executeUpdate();
                        ResultSet rs = pstmt.getGeneratedKeys();
                        while (rs.next()) {
                            indent_table_id = rs.getInt(1);
                            PreparedStatement pay = connection.prepareStatement("insert into payment_mode(payment_mode,order_id) values(?,?)");
                            pay.setString(1, paymentmode);
                            pay.setInt(2, indent_table_id);

                            rowsAffected = pay.executeUpdate();

                        }
                    }
                }
            }
            String query2 = "INSERT INTO order_item(order_table_id,item_names_id, required_qty,"
                    + " status_id,active,remark,expected_date_time,description,revision_no,delivered_date_time,model_id) "
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?) ";

            int model_id = getModelId(bean.getModel());
            int item_names_id2 = getItemNameId(model_id);
            int count2 = 0;

            PreparedStatement pstmt2 = connection.prepareStatement(query2);
            pstmt2.setInt(1, indent_table_id);
            pstmt2.setInt(2, item_names_id2);
            //    pstmt2.setInt(3, purpose_id2);
            pstmt2.setInt(3, bean.getRequired_qty());
            pstmt2.setInt(4, 2);
            pstmt2.setString(5, "Y");
            pstmt2.setString(6, "OK");
            pstmt2.setString(7, bean.getExpected_date_time());
            pstmt2.setString(8, bean.getDescription());
            pstmt2.setInt(9, bean.getRevision_no());
            pstmt2.setString(10, "");
            pstmt2.setInt(11, model_id);
            rowsAffected2 = pstmt2.executeUpdate();

        } catch (Exception e) {
            System.out.println("OrderModel insertRecord() Error: " + e);
        }
        if (rowsAffected2 > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        if (count > 0) {
            message = "Indent No. Already Exists!..";
            msgBgColor = COLOR_ERROR;
        }

        return rowsAffected2;
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

    public int getModelId(String model) {

        String query = "SELECT model_id FROM model WHERE model = '" + model + "' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("model_id");
        } catch (Exception e) {
            System.out.println("OrderModel getModelId Error: " + e);
        }
        return id;
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

    public int getPurposeId(String purpose) {

        String query = "SELECT purpose_id FROM purpose WHERE purpose = '" + purpose + "' ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt("purpose_id");
        } catch (Exception e) {
            System.out.println("OrderModel getPurposeId Error: " + e);
        }
        return id;
    }

    public List<String> getStatus(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT status from status  ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String status = (rset.getString("status"));
                if (status.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(status);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such status  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:OrderModel--getStatus()-- " + e);
        }
        return list;
    }

    public List<String> getPurpose(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT purpose from purpose  ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String purpose = (rset.getString("purpose"));
                if (purpose.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(purpose);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such purpose  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:OrderModel--getPurpose()-- " + e);
        }
        return list;
    }

    public List<String> getRequestedByKeyPerson(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT key_person_name from key_person where active='Y' ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String key_person_name = (rset.getString("key_person_name"));
                if (key_person_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(key_person_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such key_person_name  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:OrderModel--getRequestedByKeyPerson()-- " + e);
        }
        return list;
    }

    public List<String> getRequestedToKeyPerson(String q, String requested_by) {
        int loc_of_dealer = getRequestedKeyPersondegId(requested_by);
        List<String> list = new ArrayList<String>();
        String query = "Select * from key_person as kp ,city as c , designation as d  where kp.city_id=c.city_id and\n"
                + " kp.designation_id=d.designation_id and d.designation='sales' and c.city_id='" + loc_of_dealer + "' \n"
                + " and c.active='Y' and kp.active='y'";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String key_person_name = (rset.getString("key_person_name"));
                if (key_person_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(key_person_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such key_person_name  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:OrderModel--getRequestedToKeyPerson()-- " + e);
        }

        return list;
    }

    public List<Integer> getIdList(String logged_designation) {
        List<Integer> list = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        try {
            String query = " select itn.item_names_id "
                    + " from item_names itn, item_type itt,item_authorization ia,designation d where "
                    + " ia.designation_id=d.designation_id and ia.item_names_id=itn.item_names_id and d.designation='" + logged_designation + "' "
                    + " and itt.item_type_id=itn.item_type_id  and itn.active='Y' "
                    + " and itt.active='y' and ia.active='Y' and d.active='Y' ";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                list2.add(rst.getInt(1));
            }

            // Start Sorted Array for Parent Child Hierarchy
            List<Integer> list3_value = new ArrayList<>();
            List<Integer> list3_key = new ArrayList<>();
            List<Integer> list4 = new ArrayList<>();
            List<Integer> sorted_list = new ArrayList<>();
            List<Integer> sorted_list_noDuplicacy = new ArrayList<>();

            MultiMap map = new MultiValueMap();

            for (int k = 0; k < list2.size(); k++) {
                String qry_order = " SELECT T2.item_names_id,T2.item_name FROM (SELECT @r AS _id, "
                        + " (SELECT @r := parent_id FROM item_names WHERE item_names_id = _id and active='Y') AS parent_id, "
                        + " @l := @l + 1 AS lvl "
                        + " FROM "
                        + " (SELECT @r := '" + list2.get(k) + "', @l := 0) vars, "
                        + " item_names h "
                        + " WHERE @r <> 0) T1 "
                        + " JOIN item_names T2 "
                        + " ON T1._id = T2.item_names_id where T2.active='y'  "
                        + " ORDER BY T1.lvl DESC limit 1 ";

                ResultSet rst3 = connection.prepareStatement(qry_order).executeQuery();
                while (rst3.next()) {
                    map.put(rst3.getInt("item_names_id"), list2.get(k));
                    list3_key.add(list2.get(k));
                    list3_value.add(rst3.getInt("item_names_id"));
                }
            }

            Collections.sort(list3_value);

            for (int k = 0; k < list3_value.size(); k++) {
                if (sorted_list.contains(list3_value.get(k))) {
                } else {
                    sorted_list.add(list3_value.get(k));
                }
            }

            List<Integer> intArr = new ArrayList<Integer>();

            for (int k = 0; k < sorted_list.size(); k++) {

                map.values();
                List<ArrayList> ee = new ArrayList<>();
                ee.add((ArrayList) map.get(sorted_list.get(k)));
                // System.err.println("eee " + ee.size());

                for (int v = 0; v < ee.get(0).size(); v++) {
                    list4.add((Integer) ee.get(0).get(v));
                }
            }

            // END Sorted Array for Parent Child Hierarchy
            for (int k = 0; k < list4.size(); k++) {
                String qry = " SELECT T2.item_names_id,T2.item_name FROM (SELECT @r AS _id, "
                        + " (SELECT @r := parent_id FROM item_names WHERE item_names_id = _id and active='Y') AS parent_id, "
                        + " @l := @l + 1 AS lvl "
                        + " FROM "
                        + " (SELECT @r := '" + list4.get(k) + "', @l := 0) vars, "
                        + " item_names h "
                        + " WHERE @r <> 0) T1 "
                        + " JOIN item_names T2 "
                        + " ON T1._id = T2.item_names_id where T2.active='y'  "
                        + " ORDER BY T1.lvl DESC";

                ResultSet rst2 = connection.prepareStatement(qry).executeQuery();
                while (rst2.next()) {
                    list.add(rst2.getInt(1));
                }

            }

        } catch (Exception e) {
            System.out.println("OrderModel.getIdList() -" + e);
        }
        return list;
    }

    public List<ItemName> getItemsList(String logged_designation, String checkedValue, int checked_req_qty, String checked_purpose,
            String checked_item_name, String checked_expected_date_time, String checked_model) {
        List<ItemName> list = new ArrayList<ItemName>();
        List<ItemName> list1 = new ArrayList<ItemName>();
        List<Integer> desig_map_list = new ArrayList<Integer>();
        List<Integer> desig_map_listAll = new ArrayList<>();
        List<Integer> desig_map_listAllFinal = new ArrayList<>();
        List<Integer> desig_map_listUnmatched = new ArrayList<>();
        String search_item_name = "";
        String search_item_type = "";
        String search_item_codee = "";
        String search_super_child = "";
        String search_generation = "";
        try {
            desig_map_list = getIdList(logged_designation);
            ItemNameModel model = new ItemNameModel();
            List<ItemName> allIdList = model.showData(search_item_name, search_item_type, search_item_codee, search_super_child, search_generation);
            for (int k = 0; k < allIdList.size(); k++) {
                desig_map_listAll.add(allIdList.get(k).getItem_names_id());
            }

            desig_map_listAllFinal.addAll(desig_map_listAll);
            desig_map_listAll.removeAll(desig_map_list);
            desig_map_listUnmatched.addAll(desig_map_listAll);

            desig_map_listAllFinal.removeAll(desig_map_listUnmatched);

            String query = "  select itn.item_names_id,itn.item_name,itn.description,itn.item_code,itt.item_type,itn.quantity,itn.parent_id, "
                    + " itn.generation,itn.is_super_child,itn.prefix "
                    + " from item_names itn, item_type itt where itt.item_type_id=itn.item_type_id and itn.active='Y' and itt.active='y' ";
            query += "  and itn.item_names_id in(" + desig_map_listAllFinal.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ") "
                    + " order by field(itn.item_names_id," + desig_map_listAllFinal.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ")  ";
            String query2 = " select max(item_names_id) as item_names_id from item_names ";
            PreparedStatement pstmt1 = connection.prepareStatement(query2);
            ResultSet rset1 = pstmt1.executeQuery();
            while (rset1.next()) {
                item_id1 = rset1.getInt("item_names_id");
            }
            //    System.err.println("query------" + query);
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {

                ItemName bean1 = new ItemName();
                int checked_id = 0;
                int item_name_id = (rset.getInt("item_names_id"));
                int model_id = getModelId(checked_model);
                if (checkedValue.equals("")) {
                    checked_id = Integer.parseInt("0");
                } else {
                    checked_id = Integer.parseInt(checkedValue);
                }
                String checked_qty = "";
                if (checked_req_qty == 0) {
                    checked_qty = "";
                } else {
                    checked_qty = String.valueOf(checked_req_qty);
                }

                if (model_id == checked_id) {
                    bean1.setChecked_item_name(checked_item_name);
                    bean1.setChecked_model(checked_model);
                    bean1.setCheckedValue(checkedValue);
                    bean1.setChecked_purpose(checked_purpose);
                    bean1.setChecked_req_qty(checked_qty);
                    bean1.setChecked_expected_date_time(checked_expected_date_time);
                } else {
                    bean1.setChecked_item_name(checked_item_name);
                    bean1.setChecked_model(checked_model);
                    bean1.setCheckedValue(checkedValue);
                    bean1.setChecked_purpose(checked_purpose);
                    bean1.setChecked_req_qty(checked_qty);
                    bean1.setChecked_expected_date_time(checked_expected_date_time);
                }
                bean1.setItem_names_id(rset.getInt("item_names_id"));
                bean1.setItem_name((rset.getString("item_name")));
                String parent_id = rset.getString("parent_id");
                int generation = rset.getInt("generation");
                if (parent_id == null) {
                    parent_id = "";
                }
                bean1.setParent_item_id(parent_id);
                bean1.setGeneration(generation);
                bean1.setSuperp(rset.getString("is_super_child"));
                // item_id1++;
                if (rset.getString("is_super_child").equals("Y")) {
                    bean1.setSuperp("N");
                }
                list.add(bean1);
                if (rset.getString("is_super_child").equals("Y")) {
                    String query1 = " select m.model,m.model_id from item_names itn,manufacturer_item_map mim,model m "
                            + " where itn.item_names_id=mim.item_names_id and m.manufacturer_item_map_id=mim.manufacturer_item_map_id"
                            + " and mim.active='Y' and m.active='Y' and itn.active='Y' and itn.item_names_id='" + rset.getInt("item_names_id") + "' ";

                    //    System.err.println("query------" + query);   
                    PreparedStatement pstmt2 = connection.prepareStatement(query1);
                    ResultSet rset2 = pstmt2.executeQuery();
                    while (rset2.next()) {
                        ItemName bean = new ItemName();
                        item_id1 += 1;
                        bean.setItem_names_id(item_id1);
                        bean.setItem_name(rset2.getString("model"));
                        bean.setModel_id(rset2.getInt("model_id"));
                        bean.setParent_item_id(String.valueOf(rset.getInt("item_names_id")));
                        bean.setGeneration(rset.getInt("generation") + 1);
                        bean.setSuperp("Y");
                        bean.setItem(rset.getString("item_name"));
                        list.add(bean);

                    }

                }

                //   list.addAll(list1);
            }
        } catch (Exception e) {
            System.err.println("OrderModel Exception in getItemsList---------" + e);
        }

        return list;

    }

    public List<Indent> getIndentItems(int indent_table_id) {
        List<Indent> list = new ArrayList<Indent>();

        String query = " select indt.order_no,itn.item_name,p.purpose,indi.required_qty,indi.expected_date_time, "
                + " indi.approved_qty, "
                + " indi.deliver_qty, itn.quantity as stock_qty  ,s.status,indi.order_item_id,m.model "
                + " from order_table indt,order_item indi, item_names itn,purpose p,  status s,manufacturer_item_map mim"
                + " ,model m  "
                + " where indt.order_table_id=indi.order_table_id   and mim.item_names_id=itn.item_names_id "
                + " and m.manufacturer_item_map_id=mim.manufacturer_item_map_id and indi.model_id=m.model_id"
                + " and mim.active='Y'  and m.active='Y' "
                + " and indi.status_id=s.status_id and indt.active='Y' and indi.active='Y' and itn.active='Y' "
                + " and indt.order_table_id='" + indent_table_id + "' ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                Indent bean = new Indent();
                bean.setIndent_no(rset.getString("order_no"));
                bean.setItem_name((rset.getString("item_name")));
                bean.setPurpose((rset.getString("purpose")));
                bean.setRequired_qty(rset.getInt("required_qty"));
                bean.setApproved_qty(rset.getInt("approved_qty"));
                bean.setDelivered_qty(rset.getInt("deliver_qty"));
                bean.setStock_qty(rset.getInt("stock_qty"));
                bean.setExpected_date_time(rset.getString("expected_date_time"));
                String status = rset.getString("status");
//                if(status.equals("Request Sent")){
//                    status="Pending";
//                }
                bean.setStatus(status);
                bean.setIndent_item_id(rset.getInt("order_item_id"));
                bean.setModel(rset.getString("model"));

                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: OrderModel getIndentItems-" + e);
        }
        return list;
    }

    public int getCounting() {
        int counting = 100;
        int count = 0;
        String query = " SELECT order_no FROM order_table order by order_table_id desc limit 1 ";
        try {
            PreparedStatement psmt = connection.prepareStatement(query);
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                String indent_no = rs.getString("order_no");
                String indent_no_arr[] = indent_no.split("_");
                int length = (indent_no_arr.length) - 1;
                count = Integer.parseInt(indent_no_arr[length]);

                counting = count;
            }
        } catch (Exception ex) {
            System.out.println("ERROR: in getCounting in OrderModel : " + ex);
        }
        return counting + 1;
    }

    public static int getLastIndentTableId() {
        int indent_table_id = 0;
        try {
            String query = " SELECT indent_table_id FROM indent_table "
                    + " WHERE active='Y' order by indent_table_id desc limit 1 ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                indent_table_id = rset.getInt("indent_table_id");

            }
        } catch (Exception e) {
            System.err.println("In OrderModel getLastIndentTableId error:" + e);
        }
        return indent_table_id;
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
            System.out.println("OrderModel closeConnection() Error: " + e);
        }
    }
}
