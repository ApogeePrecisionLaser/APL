/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.model;

import com.inventory.tableClasses.ItemName;
import com.inventory.tableClasses.Manufacturer;
import com.inventory.tableClasses.ModelName;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author Komal
 */
public class ModelNameModel {

    private static Connection connection;

    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "#a2a220";
    private final String COLOR_ERROR = "red";
    int model_id = 0;

    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("ModelNameModel setConnection() Error: " + e);
        }
    }

    public List<ModelName> showData(String searchManufacturer, String searchModel, String searchItem, String active) {
        List<ModelName> list = new ArrayList<ModelName>();

        String query = " SELECT mr.manufacturer_name,m.model_id, m.model,m.description,inn.item_name,mim.manufacturer_item_map_id,m.lead_time,"
                + " m.min_quantity,m.daily_req FROM"
                + " model m,manufacturer_item_map mim,item_names inn,manufacturer mr where m.active='Y' "
                + " and mim.active='Y' and mr.active='Y' and inn.active='Y' and m.manufacturer_item_map_id=mim.manufacturer_item_map_id and mim.item_names_id=inn.item_names_id and"
                + " mim.manufacturer_id=mr.manufacturer_id ";

        if (!searchModel.equals("") && searchModel != null) {
            query += " and m.model='" + searchModel + "' ";
        }
        if (!searchItem.equals("") && searchItem != null) {
            query += " and inn.item_name='" + searchItem + "' ";
        }
        if (!searchManufacturer.equals("") && searchManufacturer != null) {
            query += " and mr.manufacturer_name='" + searchManufacturer + "' ";
        }

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                ModelName bean = new ModelName();
                bean.setModel_id(rset.getInt("model_id"));
                bean.setModel((rset.getString("model")));
                bean.setManufacturer_name(rset.getString("manufacturer_name"));
                bean.setItem_name(rset.getString("item_name"));
                bean.setManufacturer_item_map_id(rset.getInt("manufacturer_item_map_id"));
                bean.setDaily_req(rset.getInt("daily_req"));
                bean.setMin_qty(rset.getInt("min_quantity"));
                bean.setLead_time(rset.getInt("lead_time"));
                bean.setDescription((rset.getString("description")));

                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("ModelNameModel showData() Error: " + e);
        }
        return list;
    }

    public int insertRecord(ModelName model_name, Iterator itr, String image_name, String destination, int image_count) {
        String query2 = " select count(*) as count from model where model='" + model_name.getModel() + "' ";
        String query = "INSERT INTO model(model,manufacturer_item_map_id,lead_time,min_quantity,daily_req,"
                + " description,revision_no,active,remark) VALUES(?,?,?,?,?,?,?,?,?) ";
        int rowsAffected = 0;
        int item_id = getItemId(model_name.getItem_name());
        int manufacturer_id = getManufacturerId(model_name.getManufacturer_name());
        int manufacturer_item_map_id = getManufacturerItemMapId(manufacturer_id, item_id);

        if (manufacturer_item_map_id == 0) {
            message = "First map this item to manufacturer!...";
            msgBgColor = COLOR_ERROR;
        }
        try {
            PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, (model_name.getModel()));
            pstmt.setInt(2, manufacturer_item_map_id);
            pstmt.setInt(3, model_name.getLead_time());
            pstmt.setInt(4, model_name.getMin_qty());
            pstmt.setInt(5, model_name.getDaily_req());
            pstmt.setString(6, (model_name.getDescription()));
            pstmt.setInt(7, model_name.getRevision_no());
            pstmt.setString(8, "Y");
            pstmt.setString(9, "OK");

            ResultSet rset = connection.prepareStatement(query2).executeQuery();
            int count = 0;
            while (rset.next()) {
                count = rset.getInt("count");
            }
            if (count > 0) {
                message = "Model Already Exists.";
                msgBgColor = COLOR_ERROR;
            } else {
                rowsAffected = pstmt.executeUpdate();
                ResultSet rs = pstmt.getGeneratedKeys();
                while (rs.next()) {
                    model_id = rs.getInt(1);
                }
            }
            rowsAffected = insertImageRecord(rowsAffected, model_name, itr, destination, model_id, image_name, image_count);
        } catch (Exception e) {
            System.out.println("ModelNameModel insertRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        if (manufacturer_item_map_id == 0) {
            message = "First map this item to manufacturer!...";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int updateRecord(ModelName model_name, Iterator itr, int model_id, String image_name, String destination, int image_count) {
        int revision = ModelNameModel.getRevisionno(model_name, model_id);
        int updateRowsAffected = 0;
        int item_id = getItemId(model_name.getItem_name());
        int manufacturer_id = getManufacturerId(model_name.getManufacturer_name());
        int manufacturer_item_map_id = getManufacturerItemMapId(manufacturer_id, item_id);
        boolean status = false;
        String query1 = "SELECT max(revision_no) revision_no FROM model WHERE model_id = " + model_id + "  && active='Y' ";
        String query2 = "UPDATE model SET active =? WHERE model_id =? and revision_no=? ";
        String query3 = "INSERT INTO model(model_id,model,manufacturer_item_map_id,lead_time,min_quantity,daily_req,"
                + " description,revision_no,active,remark) VALUES(?,?,?,?,?,?,?,?,?,?)";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                PreparedStatement pstm = connection.prepareStatement(query2);
                pstm.setString(1, "n");
                pstm.setInt(2, model_id);
                pstm.setInt(3, revision);
                updateRowsAffected = pstm.executeUpdate();
                if (updateRowsAffected >= 1) {
                    revision = rs.getInt("revision_no") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                    psmt.setInt(1, (model_id));
                    psmt.setString(2, (model_name.getModel()));
                    psmt.setInt(3, manufacturer_item_map_id);
                    psmt.setInt(4, model_name.getLead_time());
                    psmt.setInt(5, model_name.getMin_qty());
                    psmt.setInt(6, model_name.getDaily_req());
                    psmt.setString(7, (model_name.getDescription()));
                    psmt.setInt(8, revision);
                    psmt.setString(9, "Y");
                    psmt.setString(10, "OK");
                    rowsAffected = psmt.executeUpdate();
                    if (rowsAffected > 0) {
                        status = true;
                    } else {
                        status = false;
                    }
                    rowsAffected = insertImageRecord(rowsAffected, model_name, itr, destination, model_id, image_name, image_count);
                }
            }
        } catch (Exception e) {
            System.out.println("ModelNameModel updateRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record updated successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }
    
    public int insertImageRecord(int rowsAffected, ModelName model_name, Iterator itr, String destination, int model_id, String image_name, int image_count) {
        String img_message = "";
        DateFormat dateFormat1 = new SimpleDateFormat("dd.MMMMM.yyyy");
        DateFormat dateFormat = new SimpleDateFormat("dd.MMMMM.yyyy/ hh:mm:ss aaa");
        Date date = new Date();
        String middleName = "";
        String current_date = dateFormat.format(date);
        String query3 = "INSERT INTO item_image_details (image_name, image_path, date_time, description,model_id,revision_no,active,remark) "
                + " VALUES(?,?,?,?,?,?,?,?)";
        String imageName = "";
        try {

            if ((!model_name.getImage_path().isEmpty())) {
                if ((!model_name.getImage_path().isEmpty())) {
                    String tempExt = image_name;
                    if (!tempExt.isEmpty()) {

                        String fieldName = "";
                        if (tempExt.equals(image_name)) {
                            String model = model_name.getModel().replaceAll("[^a-zA-Z0-9]", "_");
                            middleName = "img_Item_" + model + "_" + image_count;
                            destination = "C:\\ssadvt_repository\\APL\\item\\" + model_name.getItem_name() + "\\" + model + "\\";
                            fieldName = "item_image";
                        }

                        int index = tempExt.lastIndexOf(".");
                        int index1 = tempExt.length();
                        String Extention = tempExt.substring(index + 1, index1);
                        tempExt = "." + Extention;
                        // middleName=middleName.replaceAll("[^a-zA-Z0-9]", "_");
                        imageName = middleName + tempExt;

                        // imageName = imageName.replaceAll("[^a-zA-Z0-9]", "_");
                        WirteImage(model_name, itr, destination, imageName, fieldName);
                    }
                    PreparedStatement pstmt1 = connection.prepareStatement(query3);
                    pstmt1.setString(1, imageName);
                    pstmt1.setString(2, destination);
                    pstmt1.setString(3, current_date);
                    pstmt1.setString(4, "this image is for site");
                    pstmt1.setInt(5, model_id);
                    pstmt1.setString(6, "0");
                    pstmt1.setString(7, "Y");
                    pstmt1.setString(8, "ok");
                    rowsAffected = pstmt1.executeUpdate();
                    pstmt1.close();
                }
            }
        } catch (Exception e) {
            System.err.println("insertImageRecord exception-----" + e);
        }
        return rowsAffected;
    }

    public void WirteImage(ModelName key, Iterator itr, String destination, String imageName, String fieldName) {
        int count = 0;

        try {
            while (itr.hasNext()) {
                FileItem item = (FileItem) itr.next();
                makeDirectory(destination);
                try {
                    if (!item.isFormField()) {
                        if (item.getFieldName().equals(fieldName)) {
                            File file = new File(destination + imageName);
                            String image = item.getName();
                            if (image.isEmpty() || image.equals(destination)) {
                            } else {
                                item.write(file);
                                message = "Image Uploaded Successfully.";
                                count++;
                            }
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("WirteImage error: " + e);
                }
            }
            //}
        } catch (Exception ex) {
        }
    }

    public boolean makeDirectory(String dirPathName) {
        boolean result = false;
        File directory = new File(dirPathName);
        if (!directory.exists()) {
            try {
                result = directory.mkdirs();
            } catch (Exception e) {
                System.out.println("makeDirectory Error - " + e);
            }
        }
        return result;
    }

    public String getDestination_Path(String image_uploaded_for) {
        String destination_path = "";
        String query = " SELECT destination_path FROM image_destination id, image_uploaded_for  iuf "
                + " WHERE id.image_uploaded_for_id = iuf.image_uploaded_for_id "
                + " AND iuf.image_uploaded_for = '" + image_uploaded_for + "' ";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                destination_path = rs.getString("destination_path");
            }
        } catch (Exception ex) {
            System.out.println("ERROR: in getDestination_Path in ModelNameModel : " + ex);
        }
        return destination_path;
    }

    public List<ModelName> getImageList(String model_id) {
        List<ModelName> list = new ArrayList<ModelName>();

        String data = "";
        String img_name = "";
        String destination_path = "";
        String query = "SELECT item_image_details_id,image_name, image_path,model_id "
                + " FROM item_image_details iid "
                + " where iid.model_id=" + model_id + " and active='Y'  ORDER BY item_image_details_id ";

        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                ModelName bean = new ModelName();
                int item_image_details_id = rs.getInt("item_image_details_id");
                img_name = rs.getString("image_name");
                destination_path = rs.getString("image_path");
                destination_path = destination_path + img_name;
                bean.setModel_id(Integer.parseInt(model_id));
                bean.setItem_image_details_id(item_image_details_id);
                bean.setImage_name(img_name);
                bean.setDestination_path(destination_path);
                list.add(bean);
            }

        } catch (Exception e) {
            System.err.println("Exception in getImageList---------" + e);
        }
        return list;
    }

    public int deleteImageRecord(String item_image_detail_id) {
        int rowsAffected = 0;
        ResultSet rst;
        int count = 0;
        int updateRowsAffected = 0;

        String query2 = "UPDATE item_image_details SET active=? WHERE item_image_details_id=? ";

        try {
            PreparedStatement pstm = connection.prepareStatement(query2);
            pstm.setString(1, "n");
            pstm.setString(2, item_image_detail_id);
            rowsAffected = pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println("deleteImageRecord Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record deleted successfully.";
            msgBgColor = COLOR_OK;
        } else {

            message = "Cannot delete the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public String getImagePath(String item_image_details_id) {
        List<String> list = new ArrayList<String>();
        String img_name = "";

        String destination_path = "";
        String query = "SELECT item_image_details_id,image_name, image_path "
                + " FROM item_image_details iid "
                + " where iid.item_image_details_id=" + item_image_details_id + " ORDER BY item_image_details_id ";

        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                img_name = rs.getString("image_name");
                destination_path = rs.getString("image_path");
                destination_path = destination_path + img_name;
            }

        } catch (Exception ex) {
            System.out.println("ERROR: in getImagePath in ModelNameModel : " + ex);
        }
        return destination_path;
    }

    public int getImageCount(int model_id) {
        int image_count = 0;
        String image_name = "";

        try {

            String query = " SELECT image_name FROM item_image_details"
                    + " WHERE model_id =" + model_id + " order by item_image_details_id desc limit 1 ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                image_name = rset.getString("image_name");
                String img_name_arr[] = image_name.split(".");
                int indexOfDecimal = image_name.indexOf(".");
                String img_count_arr[] = image_name.substring(0, indexOfDecimal).split("_");
                image_count = Integer.parseInt(img_count_arr[3]);
            }
        } catch (Exception e) {
            System.err.println("getImageCount Exception-----" + e);
        }
        return image_count;
    }

    public static int getRevisionno(ModelName bean, int model_id) {
        int revision = 0;
        try {

            String query = " SELECT max(revision_no) as revision_no FROM model WHERE model_id =" + model_id + " "
                    + " && active='Y' ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
        }
        return revision;
    }

    public static int getManufacturerId(String manufacturer_name) {
        int manufacturer_id = 0;
        try {
            String query = " SELECT manufacturer_id FROM manufacturer WHERE manufacturer_name='" + manufacturer_name + "' "
                    + " AND active='Y' ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                manufacturer_id = rset.getInt("manufacturer_id");

            }
        } catch (Exception e) {
        }
        return manufacturer_id;
    }

    public static int getItemId(String item_name) {
        int item_id = 0;
        try {
            String query = " SELECT item_names_id FROM item_names WHERE item_name='" + item_name + "' "
                    + " AND active='Y' ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                item_id = rset.getInt("item_names_id");

            }
        } catch (Exception e) {
        }
        return item_id;
    }

    public static int getManufacturerItemMapId(int manufacturer_id, int item_id) {
        int manufacturer_item_map_id = 0;
        try {
            String query = " SELECT manufacturer_item_map_id from manufacturer_item_map where item_names_id='" + item_id + "' "
                    + " and manufacturer_id='" + manufacturer_id + "' and active='Y' ";
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                manufacturer_item_map_id = rset.getInt("manufacturer_item_map_id");

            }
        } catch (Exception e) {
        }
        return manufacturer_item_map_id;
    }

    public int deleteRecord(int model_id) {
        String query = "update model set active='N' where model_id = " + model_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("ModelNameModel deleteRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record deleted successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot delete the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public List<String> getManufacturer(String q) {
        List<String> list = new ArrayList<String>();

        String query = " SELECT manufacturer_name FROM manufacturer  where active='Y' ORDER BY manufacturer_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String manufacturer_name = (rset.getString("manufacturer_name"));
                if (manufacturer_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(manufacturer_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such manufacturer_name exists.");
            }
        } catch (Exception e) {
            System.out.println("ModelNameModel getManufacturer ERROR - " + e);
        }
        return list;
    }

    public List<String> getItem(String q, String manufacturer_name) {
        List<String> list = new ArrayList<String>();
        String query = "";
        if (!manufacturer_name.equals("") && manufacturer_name != null) {
            query = " SELECT inn.item_name FROM item_names inn,manufacturer mr,manufacturer_item_map mim where inn.active='Y' and mr.active='Y' "
                    + " and mim.active='Y' and mim.manufacturer_id=mr.manufacturer_id and mim.item_names_id=inn.item_names_id and mr.manufacturer_name='" + manufacturer_name + "'"
                    + " ORDER BY inn.item_name ";
        } else {
            query = " SELECT inn.item_name FROM item_names inn where inn.active='Y' "
                    + " ORDER BY inn.item_name ";
        }

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String item_name = (rset.getString("item_name"));
                if (item_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(item_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such item_name exists.");
            }
        } catch (Exception e) {
            System.out.println("ModelNameModel getItem ERROR - " + e);
        }
        return list;
    }

    public List<String> getModel(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT model_id, model FROM model  where active='Y' ORDER BY model ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String model = (rset.getString("model"));
                if (model.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(model);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such model exists.");
            }
        } catch (Exception e) {
            System.out.println("ModelNameModel getModel ERROR - " + e);
        }
        return list;
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
            System.out.println("ModelNameModel closeConnection() Error: " + e);
        }
    }
}
