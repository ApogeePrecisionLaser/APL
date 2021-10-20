/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var apiURL = "http://120.138.10.146:8080/BLE_ProjectV6/DashboardController?";
var no_of_vehicles = 0;
$(document).ready(function () {// debugger;
    getModelType();
    getDeviceName();
    getFinishedProduct();

    getModulesType();
    getDeviceType();

    getNoOfModules();
    getNoOfDevices();
    getOperationList();
    getCommandList();
    getDevOpCommandList();
    getParameterList();

});



function getModelType() {
    var api_url = apiURL;
    var dataTable = $('#dataTables_ModelType').DataTable();
    //dataTable.clear().draw();    
    dataTable.clear().page.len(5).draw();
    //alert("url --" + api_url);
    $.ajax({
        url: api_url,
        contentType: "application/json",
        dataType: 'json',
        data: {
            action1: "getModelType",
            q: "getModelType"
        },
        success: function (result) {
            //alert(result);
            console.log(result);
            if ($("#dataTables_ModelType").length > 0) {
                var k = 0;
                $("#no_of_model_type").text(result.data.length);
                for (var l = 0; l < result.data.length; l++) {

                    $('#dataTables_ModelType').dataTable().fnAddData([
                        ++k,
                        result.data[l].model_id,
                        result.data[l].model_type,
                        result.data[l].active,
                        result.data[l].created_date
                    ]
                            );

                    $('.even').toggleClass('even class' + l + '').attr('onclick', 'viewDetails("' + result.data[l].model_id + '");');
                    $('.odd').toggleClass('odd class' + l + '').attr('onclick', 'viewDetails("' + result.data[l].model_id + '");');


                    //$("#dataTables_fire_staffing td:nth-child(4)").css('background-color', 'red');

                    //var getText=$("#dataTables_fire_staffing tr:nth-child(2) td:nth-child(4)").text().css('background-color', 'red');
                    //alert("texxxttt --"+getText);

//                    if (result.data[l].count == 2) {
//                        $("#dataTables_fire_staffing tr:nth-child(" + k + ") td:nth-child(4)").css('background-color', '#49ea49de');
//                    }
//                    if (result.data[l].count < 2) {
//                        $("#dataTables_fire_staffing tr:nth-child(" + k + ") td:nth-child(4)").css('background-color', 'yellow');
//                    }
//                    if (result.data[l].count > 2) {
//                        $("#dataTables_fire_staffing tr:nth-child(" + k + ") td:nth-child(4)").css('background-color', 'red');
//                    }
                }
            }
        }
    });
    //setTimeout(checkFuelTheft, 50000);
}


function getDeviceName() {
    var api_url = apiURL;
    var dataTable = $('#dataTables_DeviceName').DataTable();
    dataTable.clear().page.len(5).draw();
    //alert("url --" + api_url);
    $.ajax({
        url: api_url,
        contentType: "application/json",
        dataType: 'json',
        data: {
            action1: "getDeviceName",
            q: "getDeviceName"
        },
        success: function (result) {
            //alert(result);
            console.log(result);
            if ($("#dataTables_DeviceName").length > 0) {
                var k = 0;
                $("#no_of_device_name").text(result.data.length);
                for (var l = 0; l < result.data.length; l++) {

                    $('#dataTables_DeviceName').dataTable().fnAddData([
                        ++k,
                        result.data[l].device_id,
                        result.data[l].device_name,
                        result.data[l].device_no,
                        result.data[l].warranty_period,
                        result.data[l].device_address,
                        result.data[l].no_of_module,
                        result.data[l].model_type
                                //result.data[l].created_date                        
                    ]
                            );

                    $('.even').toggleClass('even class' + l + '').attr('onclick', 'viewDetails("' + result.data[l].device_id + '");');
                    $('.odd').toggleClass('odd class' + l + '').attr('onclick', 'viewDetails("' + result.data[l].device_id + '");');
                }
            }
        }
    });
}

function getFinishedProduct() {
    var api_url = "DashboardController";
    //var dataTable = $('#dataTables_FinishedProduct').DataTable();
    //dataTable.clear().page.len(5).draw();
    //alert("url --" + api_url);
    $.ajax({
        url: api_url,
        contentType: "application/json",
        dataType: 'json',
        data: {
            action1: "getFinishedProduct",
            q: "getFinishedProduct"
        },
        success: function (result) {
            //alert(result);
            console.log(result);
            //if ($("#dataTables_FinishedProduct").length > 0) {
            var k = 0;
            $("#no_of_finished_product").text(result.data.length);
            //}
        }
    });
}

function showFinishedProductImage() {
    if ($('#finished_type_details').css('display') == 'none') {
        $("#elementid1").empty();
        var size;
        var ar_image = [];
        var ar_name = [];
        // ajax
        var api_url = "DashboardController";
        $.ajax({
            url: api_url,
            contentType: "application/json",
            dataType: 'json',
            data: {
                action1: "getFinishedProduct",
                q: "getFinishedProduct"
            },
            success: function (result) {
                var size = result.data.length;
                //alert("size ="+size);
                var ar_image = [];
                var ar_name = [];
                $('#modules_type_details').hide();
                $('#registration_type_details').hide();
                $('#finished_type_details').show();
                for (var l = 0; l < size; l++) {
                    $("#elementid1").append('<div class="col-md-3"><img src="" name="' + result.data[l].device_name + '" id="' + result.data[l].device_id + '" onclick="showSubModules(' + result.data[l].device_id + ');" style="margin-top: 30px;" width="250" height="200"><h4 style="margin-top:0px"><b>' + result.data[l].device_name + '</b></h4></div>');

                    $("#" + result.data[l].device_id)
                            .attr(
                                    "src",
                                    "http://120.138.10.146:8080/BLE_ProjectV6_2/AddDeviceController?getImage="
                                    + result.data[l].image_path + "");
                }

                $("#elementid1").append('<div class="col-md-3"><a href="DeviceController?task=Finished" target="_blank"  class="btn btn-success" style="margin-top:115px;">Add Finished Product</a></div>');
                //$("#elementid2").append('<div class="col-md-2 col-sm-12 col-xs-12" onclick="addModules();"><div class="panel panel-default"style="background-color: red; height: 243px;"><div class="panel-body easypiechart-panel"><h4 class="textColor">Add Modules</h4><div data-percent="100"><center><div class="circle" id="no_of_modules">20</div></center></div><div style="margin-top: 10px"></div></div></div></div>');
            }
        });
    }
}

function showTable(table_name) {//alert(table_name);
    $("#table_for_model_type").hide();
    $("#table_for_device_name").hide();
    $("#table_for_finished_product").hide();
    if (table_name == 'model_type') {

        // $("#table_for_model_type").show();
    } else if (table_name == 'device_name') {
        //  $("#table_for_device_name").show();
    } else if (table_name == 'finished_product') {
        $("#table_for_finished_product").show();
    }

}

function showDeviceImage() {
    if ($('#model_type_details').css('display') == 'none') {
        $("#elementid").empty();
        var size;
        var ar_image = [];
        var ar_name = [];
        // ajax
        var api_url = "AddModulesController";
        $.ajax({
            url: api_url,
            contentType: "application/json",
            dataType: 'json',
            data: {
                action1: "getDeviceList",
                q: "getDeviceList"
            },
            success: function (result) {
                var size = result.data.length;
                var ar_image = [];
                var ar_name = [];
                $('#model_type_details').show();
                $('#modules_type_details').hide();
                for (var l = 0; l < size; l++) {
                    //$("#elementid").append('<div class="col-md-3"><img src="" name="' + result.data[l].device_id + '" id="' + result.data[l].device_id + '" style="margin-top: 30px;margin-left:10px;" width="75" height="75"><h4 style="margin-left:30px"><b>' + result.data[l].device_name + '</b></h4></div>');

                    $("#elementid").append('<div class="col-md-3"><img src="" name="' + result.data[l].device_id + '" id="' + result.data[l].device_id + '"  style="margin-top: 30px;" width="250" height="200"><h4 style="margin-top:0px"><b>' + result.data[l].device_name + '</b></h4></div>');

                    $("#" + result.data[l].device_id)
                            .attr(
                                    "src",
                                    "http://120.138.10.146:8080/BLE_ProjectV6_2/AddModulesController?getImage="
                                    + result.data[l].device_image_path + "");
                }
                //}
            }
        });
    }
}


function showModulesImage() {
    if ($('#modules_type_details').css('display') == 'none') {
        $("#elementid2").empty();
        var size;
        var ar_image = [];
        var ar_name = [];
        // ajax
        var api_url = "DashboardController";
        $.ajax({
            url: api_url,
            contentType: "application/json",
            dataType: 'json',
            data: {
                action1: "getModulesList",
                q: "getModulesList"
            },
            success: function (result) {
                var size = result.data.length;
                //alert("size ="+size);
                var ar_image = [];
                var ar_name = [];
                $('#modules_type_details').show();
                $('#registration_type_details').hide();
                $('#finished_type_details').hide();
                for (var l = 0; l < size; l++) {
                    $("#elementid2").append('<div class="col-md-3"><img src="" name="' + result.data[l].device_name + '" id="' + result.data[l].device_id + '" onclick="showSubModules(' + result.data[l].device_id + ');" style="margin-top: 30px;" width="250" height="200"><h4 style="margin-top:0px"><b>' + result.data[l].device_name + '</b></h4></div>');

                    $("#" + result.data[l].device_id)
                            .attr(
                                    "src",
                                    "http://120.138.10.146:8085/BLE_ProjectV6_2/AddDeviceController?getImage="
                                    + result.data[l].image_path + "");
                }

                $("#elementid2").append('<div class="col-md-3"><a href="DeviceController" target="_blank"  class="btn btn-success" style="margin-top:115px;">Add New Modules</a></div>');
                //$("#elementid2").append('<div class="col-md-2 col-sm-12 col-xs-12" onclick="addModules();"><div class="panel panel-default"style="background-color: red; height: 243px;"><div class="panel-body easypiechart-panel"><h4 class="textColor">Add Modules</h4><div data-percent="100"><center><div class="circle" id="no_of_modules">20</div></center></div><div style="margin-top: 10px"></div></div></div></div>');
            }
        });
    }
}
function showSubModules(id) {//alert(id);
    //if ($('#sub_modules_type_details').css('display') == 'none') {
//alert(device_type);
//    $("#elementid3").empty();
//    var size;
//    var ar_image = [];
//    var ar_name = [];
//    var ar_id = [];
//    // ajax
//    var api_url = apiURL;
//    var popupwin = null;
//    var queryString = "task=showSubModulesImage&action2=" + id;
//    var url = apiURL + queryString;
//    popupwin = openPopUp(url, "Sub Modules for ", 600, 1200);\

    window.open('DeviceController?task=OpenSpecific&idd='+id, '_blank');
}


//    function displayMapList() {
//        var queryString = "task=generateMapReport";
//        var url = "divisionCont?" + queryString;
//        popupwin = openPopUp(url, "Mounting Type Map Details", 500, 1000);
//
//    }

function openPopUp(url, window_name, popup_height, popup_width) {
    var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
    var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
    var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";

    //return window.open(url, window_name, window_features);
    return window.open(url);
}

function getModulesType() {
    var api_url = apiURL;
    var dataTable = $('#dataTables_ModulesType').DataTable();
    //dataTable.clear().draw();    
    dataTable.clear().page.len(5).draw();
    //alert("url --" + api_url);
    $.ajax({
        url: "AddModulesController",
        contentType: "application/json",
        dataType: 'json',
        data: {
            action1: "getModulesType",
            q: "getModulesType"
        },
        success: function (result) {
            //alert(result);
            console.log(result);
            if ($("#dataTables_ModulesType").length > 0) {
                var k = 0;
                //$("#no_of_model_type").text(result.data.length);
                for (var l = 0; l < result.data.length; l++) {
                    $('#dataTables_ModulesType').dataTable().fnAddData([
                        ++k,
                        result.data[l].module_name,
                        result.data[l].module_id,
                        result.data[l].module_category,
                        result.data[l].module_brand,
                        result.data[l].datasheet_link,
                        result.data[l].developers_note,
                        result.data[l].module_active,
                        result.data[l].date
                    ]
                            );

                    $('.even').toggleClass('even class' + l + '').attr('onclick', 'viewDetails("' + result.data[l].module_table_id + '");');
                    $('.odd').toggleClass('odd class' + l + '').attr('onclick', 'viewDetails("' + result.data[l].module_table_id + '");');
                }
            }
        }
    });
}

function getDeviceType() {
    var api_url = apiURL;
    var dataTable = $('#dataTables_DeviceType').DataTable();
    //dataTable.clear().draw();    
    dataTable.clear().page.len(5).draw();
    //alert("url --" + api_url);
    $.ajax({
        url: "AddDeviceController",
        contentType: "application/json",
        dataType: 'json',
        data: {
            action1: "getDeviceType",
            q: "getDeviceType"
        },
        success: function (result) {
            //alert(result);
            console.log(result);
            if ($("#dataTables_DeviceType").length > 0) {
                var k = 0;
                //$("#no_of_model_type").text(result.data.length);
                for (var l = 0; l < result.data.length; l++) {
                    $('#dataTables_DeviceType').dataTable().fnAddData([
                        ++k,
                        result.data[l].device_name,
                        result.data[l].device_id,
                        result.data[l].levels,
                        result.data[l].device_active,
                        result.data[l].date
                    ]
                            );

                    $('.even').toggleClass('even class' + l + '').attr('onclick', 'viewDetails("' + result.data[l].device_table_id + '");');
                    $('.odd').toggleClass('odd class' + l + '').attr('onclick', 'viewDetails("' + result.data[l].device_table_id + '");');
                }
            }
        }
    });
}

function getNoOfDevices() {
    var api_url = "AddModulesController";
    $.ajax({
        url: api_url,
        contentType: "application/json",
        dataType: 'json',
        data: {
            action1: "getDeviceList",
            q: "getDeviceList"
        },
        success: function (result) {
            var size = result.data.length;
            $("#no_of_devices").text(size);
        }
    });
}

function getNoOfModules() {
    var api_url = "DashboardController";
    $.ajax({
        url: api_url,
        contentType: "application/json",
        dataType: 'json',
        data: {
            action1: "getModulesList",
            q: "getModulesList"
        },
        success: function (result) {
            var size = result.data.length;
            $("#no_of_modules").text(size);
        }
    });
}

function getOperationList() {
    var api_url = "DashboardController";
    $.ajax({
        url: api_url,
        contentType: "application/json",
        dataType: 'json',
        data: {
            action1: "getOperationList",
            q: "getOperationList"
        },
        success: function (result) {
            var size = result.data.length;
            $("#operation_name").text(size);
        }
    });
}

function getCommandList() {
    var api_url = "DashboardController";
    $.ajax({
        url: api_url,
        contentType: "application/json",
        dataType: 'json',
        data: {
            action1: "getCommandList",
            q: "getCommandList"
        },
        success: function (result) {
            var size = result.data.length;
            $("#command_detail").text(size);
        }
    });
}

function getDevOpCommandList() {
    var api_url = "DashboardController";
    $.ajax({
        url: api_url,
        contentType: "application/json",
        dataType: 'json',
        data: {
            action1: "getDevOpCommandList",
            q: "getDevOpCommandList"
        },
        success: function (result) {
            var size = result.data.length;
            $("#device_op_command").text(size);
        }
    });
}

function getParameterList() {
    var api_url = "DashboardController";
    $.ajax({
        url: api_url,
        contentType: "application/json",
        dataType: 'json',
        data: {
            action1: "getParameterList",
            q: "getParameterList"
        },
        success: function (result) {
            var size = result.data.length;
            $("#parameter").text(size);
        }
    });
}

function showOperationName() {
    window.open('OperationNameController', '_blank');
}

function showCommandDetail() {
    window.open('CommandController', '_blank');
}

function showDevOpCommandList() {
    window.open('DevOpComMapController', '_blank');
}

function showParameter() {
    window.open('ParameterNameController', '_blank');
}

function showRespectiveDiv(type){
    $('#finished_type_details').hide();
    $('#modules_type_details').hide();
    $('#registration_type_details').hide();
    if(type==='Devices'){
        $('#device_modules_div').show();
        $('#registration_div').hide();        
        $('#operation_command_div').hide();        
    }else if(type==='operation_command'){
        $('#device_modules_div').hide();
        $('#registration_div').hide();        
        $('#operation_command_div').show();
    }else if(type==='Registration'){
        $('#device_modules_div').hide();
        $('#registration_div').show();        
        $('#operation_command_div').hide();
    }
}

function goToConfigurationPage(){
    window.open('DeviceConfigController', '_blank');
}