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

    public List<ModelName> showData(String searchManufacturer, String searchModel, String searchItemCode, String active) {
        List<ModelName> list = new ArrayList<ModelName>();

        String query = " SELECT mr.manufacturer_name,m.model_id, m.model,m.description,inn.item_code,mim.manufacturer_item_map_id,m.lead_time"
                + " ,m.model_no,m.part_no "
                + "  FROM model m,manufacturer_item_map mim,item_names inn,manufacturer mr where m.active='Y' "
                + " and mim.active='Y' and mr.active='Y' and inn.active='Y' and m.manufacturer_item_map_id=mim.manufacturer_item_map_id and mim.item_names_id=inn.item_names_id and"
                + " mim.manufacturer_id=mr.manufacturer_id ";

        if (!searchModel.equals("") && searchModel != null) {
            query += " and m.model='" + searchModel + "' ";
        }
        if (!searchItemCode.equals("") && searchItemCode != null) {
            query += " and inn.item_code='" + searchItemCode + "' ";
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
//                bean.setItem_name(rset.getString("item_name"));
                bean.setItem_code(rset.getString("item_code"));
                bean.setManufacturer_item_map_id(rset.getInt("manufacturer_item_map_id"));
                bean.setLead_time(rset.getInt("lead_time"));
                bean.setDescription((rset.getString("description")));
                bean.setModel_no(rset.getString("model_no"));
                bean.setPart_no(rset.getString("part_no"));

                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("ModelNameModel showData() Error: " + e);
        }
        return list;
    }

    public int insertRecord(ModelName model_name, Iterator itr, String image_name, String destination, int image_count) {
        String query2 = " select count(*) as count from model where model='" + model_name.getModel() + "' and active='Y' ";
        String query = "INSERT INTO model(model,manufacturer_item_map_id,lead_time,"
                + " description,revision_no,active,remark,model_no,part_no) VALUES(?,?,?,?,?,?,?,?,?) ";
        int rowsAffected = 0;
        int manufacturer_item_map_id = 0;
        int map_count = 0;
        int item_id = getItemId(model_name.getItem_code());
        int manufacturer_id = getManufacturerId(model_name.getManufacturer_name());
        //  int manufacturer_item_map_id = getManufacturerItemMapId(manufacturer_id, item_id);

//        if (manufacturer_item_map_id == 0) {
//            message = "First map this item to manufacturer!...";
//            msgBgColor = COLOR_ERROR;
//        }
        try {
            String query3 = "insert into manufacturer_item_map(manufacturer_id,item_names_id,"
                    + " active,revision,remark,created_by,serial_no,created_at) "
                    + " values (?,?,?,?,?,?,?,now()) ";

            java.sql.PreparedStatement pstmt = connection.prepareStatement(query3, Statement.RETURN_GENERATED_KEYS);
            String query4 = "SELECT count(*) as count FROM manufacturer_item_map WHERE "
                    + " manufacturer_id='" + manufacturer_id + "' and item_names_id='" + item_id + "'"
                    + " and active='Y'  ";

            PreparedStatement pstmt1 = connection.prepareStatement(query4);
            ResultSet rs1 = pstmt1.executeQuery();
            while (rs1.next()) {
                map_count = rs1.getInt("count");
            }
            if (map_count > 0) {
                message = "Item has already mapped with this manufacturer!..";
                msgBgColor = COLOR_ERROR;
            } else {
                pstmt.setInt(1, manufacturer_id);
                pstmt.setInt(2, item_id);
                pstmt.setString(3, "Y");
                pstmt.setString(4, "0");
                pstmt.setString(5, "OK");
                pstmt.setString(6, "Komal");
                pstmt.setString(7, model_name.getDescription());
                rowsAffected = pstmt.executeUpdate();
                ResultSet rs2 = pstmt.getGeneratedKeys();
                while (rs2.next()) {
                    manufacturer_item_map_id = rs2.getInt(1);
                }
            }

            PreparedStatement pstm = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, (model_name.getModel()));
            pstm.setInt(2, manufacturer_item_map_id);
            pstm.setInt(3, model_name.getLead_time());
            pstm.setString(4, (model_name.getDescription()));
            pstm.setInt(5, model_name.getRevision_no());
            pstm.setString(6, "Y");
            pstm.setString(7, "OK");
            pstm.setString(8, model_name.getModel_no());
            pstm.setString(9, model_name.getPart_no());
            ResultSet rset = connection.prepareStatement(query2).executeQuery();
            int count = 0;
            while (rset.next()) {
                count = rset.getInt("count");
            }
            if (count > 0) {
                message = "Model Already Exists.";
                msgBgColor = COLOR_ERROR;
            } else {
                rowsAffected = pstm.executeUpdate();
                ResultSet rs = pstm.getGeneratedKeys();
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
        if (map_count > 0) {
            message = "Item has already mapped with this manufacturer!..";
            msgBgColor = COLOR_ERROR;
        }

        return rowsAffected;
    }

    public int updateRecord(ModelName model_name, Iterator itr, int model_id, String image_name, String destination, int image_count) {
        int revision = ModelNameModel.getRevisionno(model_name, model_id);
        int updateRowsAffected = 0;
        int map_count = 0;
        int item_id = getItemId(model_name.getItem_code());
        int manufacturer_id = getManufacturerId(model_name.getManufacturer_name());
        int manufacturer_item_map_id = model_name.getManufacturer_item_map_id();
        //  int manufacturer_item_map_id = getManufacturerItemMapId(manufacturer_id, item_id);
        boolean status = false;
        String query1 = "SELECT max(revision_no) revision_no FROM model WHERE model_id = " + model_id + "  && active='Y' ";
        String query2 = "UPDATE model SET active =? WHERE model_id =? and revision_no=? ";
        String query3 = "INSERT INTO model(model_id,model,manufacturer_item_map_id,lead_time,"
                + " description,revision_no,active,remark,model_no,part_no) VALUES(?,?,?,?,?,?,?,?,?,?)";
        int rowsAffected = 0;
        try {

            int revision_no = ModelNameModel.getManufacturerItemMapRevisionno(model_name, manufacturer_item_map_id);

            int rowsAffected_map = 0;
            int count_map = 0;
            int updateRowsAffected_map = 0;

            String map_query1 = "SELECT max(revision) FROM manufacturer_item_map WHERE "
                    + " manufacturer_item_map_id = " + manufacturer_item_map_id + " and active='Y' ";

            String map_query2 = "UPDATE manufacturer_item_map SET active=? WHERE manufacturer_item_map_id=? and revision=? ";

            String map_query3 = "insert into manufacturer_item_map(manufacturer_item_map_id,manufacturer_id,item_names_id, "
                    + " active,revision,remark,created_by,serial_no,created_at) "
                    + " values (?,?,?,?,?,?,?,?,now()) ";
            String map_query4 = "SELECT count(*) as count FROM manufacturer_item_map WHERE "
                    + " manufacturer_id='" + manufacturer_id + "' and item_names_id='" + item_id + "'"
                    + " and active='Y'  ";

            PreparedStatement pstmt1_map = connection.prepareStatement(map_query4);
            ResultSet rs1_map = pstmt1_map.executeQuery();
            while (rs1_map.next()) {
                map_count = rs1_map.getInt("count");
            }
            if (map_count > 0) {
                status = false;
                message = "Item has already mapped with this manufacturer!..";
                msgBgColor = COLOR_ERROR;
            } else {
                PreparedStatement pstmt_map = connection.prepareStatement(map_query1);
                ResultSet rs_map = pstmt_map.executeQuery();

                if (rs_map.next()) {
                    revision_no = rs_map.getInt("max(revision)");
                    PreparedStatement pstm_map = connection.prepareStatement(map_query2);
                    pstm_map.setString(1, "N");
                    pstm_map.setInt(2, manufacturer_item_map_id);
                    pstm_map.setInt(3, revision_no);
                    updateRowsAffected_map = pstm_map.executeUpdate();
                    if (updateRowsAffected_map >= 1) {
                        revision_no = rs_map.getInt("max(revision)") + 1;
                        PreparedStatement psmt_map = (PreparedStatement) connection.prepareStatement(map_query3);
                        psmt_map.setInt(1, manufacturer_item_map_id);
                        psmt_map.setInt(2, manufacturer_id);
                        psmt_map.setInt(3, item_id);
                        psmt_map.setString(4, "Y");
                        psmt_map.setInt(5, revision_no);
                        psmt_map.setString(6, "OK");
                        psmt_map.setString(7, "Komal");
                        psmt_map.setString(8, model_name.getDescription());
                        rowsAffected_map = psmt_map.executeUpdate();
                    }
                } else {
                    message = "Key already mapped with some child!";
                    msgBgColor = COLOR_ERROR;
                }
            }
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
                    psmt.setString(5, (model_name.getDescription()));
                    psmt.setInt(6, revision);
                    psmt.setString(7, "Y");
                    psmt.setString(8, "OK");
                    psmt.setString(9, model_name.getModel_no());
                    psmt.setString(10, model_name.getPart_no());
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
//        if (map_count > 0) {
//            status = false;
//            message = "Item has already mapped with this manufacturer!..";
//            msgBgColor = COLOR_ERROR;
//        }
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
                            destination = "C:\\ssadvt_repository\\APL\\item\\" + model_name.getItem_code() + "\\" + model + "\\";
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

    public static int getManufacturerItemMapRevisionno(ModelName model_name, int manufacturer_item_map_id) {
        int revision = 0;
        try {
            String query = " SELECT max(revision) as revision_no FROM manufacturer_item_map"
                    + " WHERE manufacturer_item_map_id =" + manufacturer_item_map_id + "  && active='Y' ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");
            }
        } catch (Exception e) {
        }
        return revision;
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

    public static int getItemId(String item_code) {
        int item_id = 0;
        try {
            String query = " SELECT item_names_id FROM item_names WHERE item_code='" + item_code + "' "
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

    public static int getManufacturerItemMapId(int model_id) {
        int manufacturer_item_map_id = 0;
        try {
            String query = " SELECT manufacturer_item_map_id from model where model_id='" + model_id + "' and active='Y' ";
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
        int manufacturer_item_map_id = getManufacturerItemMapId(model_id);

        int rowsAffected = 0;
        try {
            String query1 = "DELETE FROM manufacturer_item_map WHERE manufacturer_item_map_id = '" + manufacturer_item_map_id + "' "
                    + "and active='Y' ";

            rowsAffected = connection.prepareStatement(query).executeUpdate();
            PreparedStatement pstmt1 = connection.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            pstmt1.executeUpdate();
            connection.prepareStatement(query1).executeUpdate();
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

    public List<String> getItemCode(String q, String manufacturer_name) {
        List<String> list = new ArrayList<String>();
        String query = "";

        query = " SELECT inn.item_code FROM item_names inn where inn.active='Y' and inn.is_super_child='Y' "
                + " ORDER BY inn.item_code ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String item_code = (rset.getString("item_code"));
                if (item_code.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(item_code);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such item_code exists.");
            }
        } catch (Exception e) {
            System.out.println("ModelNameModel getItem ERROR - " + e);
        }
        return list;
    }

//    public List<String> getItemCode(String q, String manufacturer_name) {
//        List<String> list = new ArrayList<String>();
//        String query = "";
//        String query2 = " select itn.item_names_id from item_names itn,manufacturer mr,manufacturer_item_map mim where"
//                + "  mim.manufacturer_id=mr.manufacturer_id"
//                + "  and mim.item_names_id=itn.item_names_id and mim.active='Y' and mr.active='Y' and itn.active='Y' ";
//        if (!manufacturer_name.equals("") && manufacturer_name != null) {
//            query2 += " and mr.manufacturer_name='" + manufacturer_name + "' ";
//        }
//
//        try {
//            ResultSet rset2 = connection.prepareStatement(query2).executeQuery();
//            List<Integer> list2 = new ArrayList<Integer>();
//            while (rset2.next()) {
//                int item_names_id = rset2.getInt("item_names_id");
//                list2.add(item_names_id);
//            }
//            
//            query = " SELECT inn.item_code FROM item_names inn "
//                    + " where inn.active='Y' and inn.is_super_child='Y' ";
//            if (list2.size() > 0) {
//                query += " and inn.item_names_id  not in(" + list2.toString().replaceAll("\\[", "").replaceAll("\\]", "") + ") ";
//            }
//
//            query += " ORDER BY inn.item_code  ";
//
//            ResultSet rset = connection.prepareStatement(query).executeQuery();
//            int count = 0;
//            q = q.trim();
//            while (rset.next()) {    // move cursor from BOR to valid record.
//                String item_code = (rset.getString("item_code"));
//                if (item_code.toUpperCase().startsWith(q.toUpperCase())) {
//                    list.add(item_code);
//                    count++;
//                }
//            }
//            if (count == 0) {
//                list.add("No such item_code exists.");
//            }
//        } catch (Exception e) {
//            System.out.println("ModelNameModel getItem ERROR - " + e);
//        }
//        return list;
//    }

    public List<String> getModel(String q, String manufacturer_name, String item_code) {
        List<String> list = new ArrayList<String>();
        String query = " select m.model from model m,manufacturer mr,item_names inn,manufacturer_item_map mim "
                + " where m.manufacturer_item_map_id=mim.manufacturer_item_map_id "
                + " and mim.manufacturer_id=mr.manufacturer_id and mim.item_names_id=inn.item_names_id and m.active='Y' and mr.active='Y' "
                + " and mim.active='Y'";

        if (!manufacturer_name.equals("") && manufacturer_name != null) {
            query += " and mr.manufacturer_name='" + manufacturer_name + "' ";
        }
        if (!item_code.equals("") && item_code != null) {
            query += " and inn.item_code='" + item_code + "' ";
        }
        query += " group by m.model order by model ";

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

    public List<String> getItemTypeForModelOrPart(String item_code) {
        List<String> list = new ArrayList<String>();
        String query = " select itemt.item_type from item_type itemt,item_names itemn where itemn.item_type_id=itemt.item_type_id and itemn.active='Y' "
                + " and itemt.active='Y' ";

        if (!item_code.equals("") && item_code != null) {
            query += " and itemn.item_code='" + item_code + "' ";
        }

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {
                String item_type = (rset.getString("item_type"));
                list.add(item_type);

            }

        } catch (Exception e) {
            System.out.println("ModelNameModel getItemTypeForModelOrPart ERROR - " + e);
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
