/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
    $(document).on('keydown', '.myAutocompleteClass', function () {
        var id = this.id;
        //alert("iddd -"+id);
        var type;
        if (id.match("^selection_parameter")) {
            type = "Selection";
        } else if (id.match("^input_parameter")) {
            type = "Input";
        } else if (id.match("^bitwise_parameter")) {
            type = "Bitwise";
        }
        //alert("type --"+type);


        var random = this.value;
        $('#' + id).autocomplete({
            source: function (request, response) {
                $.ajax({
                    url: "CommandController",
                    dataType: "json",
                    data: {
                        action1: "getParameter",
                        str: random,
                        type: type
                    },
                    success: function (data) {
                        console.log(data);
                        response(data.list);
                    },
                    error: function (error) {
                        console.log(error.responseText);
                        response(error.responseText);
                    }
                });
            },

            select: function (events, ui) {
                console.log(ui);
                $(this).val(ui.item.label); // display the selected text
                return false;
            }
        });
    });


    $(document).on('keydown', '.myAutoCompleteForSelectionValue', function () {
        var id = this.id;
        //alert("iddd -"+id);
        var param;
        var count = id.substring(16, 20);
        //alert("counttt --"+count);
        param = $('#selection_parameter' + count).val();
        //alert("parammm --"+param);


        var random = this.value;
        $('#' + id).autocomplete({
            source: function (request, response) {
                $.ajax({
                    url: "CommandController",
                    dataType: "json",
                    data: {
                        action1: "getSelectionValue",
                        str: random,
                        param: param
                    },
                    success: function (data) {
                        console.log(data);
                        response(data.list);
                    },
                    error: function (error) {
                        console.log(error.responseText);
                        response(error.responseText);
                    }
                });
            },

            select: function (events, ui) {
                console.log(ui);
                $(this).val(ui.item.label); // display the selected text
                return false;
            }
        });
    });
    
    
    $(document).on('keydown', '.myAutoCompleteForBitwiseValue', function () {
        var id = this.id;
        //alert("iddd -"+id);
        var param;
        //bitwise_values1
        var count = id.substring(14, 20);
        //alert("counttt --"+count);
        param = $('#bitwise_parameter' + count).val();
        //alert("parammm --"+param);


        var random = this.value;
        $('#' + id).autocomplete({
            source: function (request, response) {
                $.ajax({
                    url: "CommandController",
                    dataType: "json",
                    data: {
                        action1: "getBitwiseValue",
                        str: random,
                        param: param
                    },
                    success: function (data) {
                        console.log(data);
                        response(data.list);
                    },
                    error: function (error) {
                        console.log(error.responseText);
                        response(error.responseText);
                    }
                });
            },

            select: function (events, ui) {
                console.log(ui);
                $(this).val(ui.item.label); // display the selected text
                return false;
            }
        });
    });



});


$(function () {
    $("#selection_parameter1").autocomplete({
        source: function (request, response) {
            alert(1213);
            var random = document.getElementById("selection_parameter1").value;
            $.ajax({
                url: "CommandController",
                dataType: "json",
                data: {
                    action1: "getParameter",
                    str: random
                },
                success: function (data) {
                    console.log(data);
                    response(data.list);
                },
                error: function (error) {
                    console.log(error.responseText);
                    response(error.responseText);
                }
            });
        },
        select: function (events, ui) {
            console.log(ui);
            $('#selection_parameter1').val(ui.item.label); // display the selected text
            return false;
        }
    });
   
})


function showSelectionValueFields(val) {

    $('#show_hide_btn').show();

    $('.selection_div_class').remove();

    $('#selection_div_start').show();
    var count = val;
    for (var i = 1; i <= count; i++) {
        $('#selection_field_div').append('<div class="col-lg-4 selection_div_class" style="border:2px solid rgb(56, 165, 238, 0.5);padding:0px 0px 4px 0px" id="selection_div_' + i + '"><div class="col-lg-1" style="margin-top:5px;padding:0px 0px 0px 0px"><label style="font-size:16px;">' + i + '</label></div><div class="col-lg-5" style="margin-top:5px;padding:0px;margin-left: -15px;"><input class="form-control myAutocompleteClass" type="text" id="selection_parameter' + i + '" name="selection_parameter' + i + '"/></div><div class="col-lg-5" style="margin-top:5px"><label style="font-size:16px;">Selection Values</label></div><div class="col-lg-1" style="margin-top:5px;padding:0px;margin-left: -15px;"><input style="width:60px;" class="form-control myAutoCompleteForSelectionValue" type="text" onchange="getSelectionValue(value,' + i + ');" id="selection_values' + i + '" name="selection_values' + i + '"/></div></div>');
    }
}



function getSelectionValue(val, loop_count) {
//alert("selction val; --"+val+" looop ci=ont -"+loop_count);
    $('.display_byte_level' + loop_count).remove();

    // changes start auto fill on change of selection val

    var sel_val = val;
    var param = $('#selection_parameter' + loop_count).val();

    $.ajax({
        url: "CommandController",
        contentType: "application/json",
        dataType: 'json',
        data: {
            action1: "getDisplayByteVal",
            str: "dummy",
            sel_val: sel_val,
            param: param
        },
        success: function (result) {
            //alert(result);
            console.log(result);
            var k = 0;
            var val_count = result.data.length;

            //$.isNumeric(id)
            if ($.isNumeric(val)) {
                $('#selection_div_' + loop_count).append('<div class="col-lg-5 display_byte_level' + loop_count + '" style="margin-top:5px;"><label style="font-size:16px;">Display Value</label></div><div class="col-lg-2 display_byte_level' + loop_count + '" style="margin-top:5px;"><label style="font-size:16px;"></label></div><div class="col-lg-5 display_byte_level' + loop_count + '" style="margin-top:5px;"><label style="font-size:16px;">Byte Value</label></div>');
            }

            for (var i = 1; i <= val_count; i++) {                
                $('#selection_div_' + loop_count).append('<div class="col-lg-5 display_byte_level' + loop_count + '" style="margin-top:5px;"><input class="form-control" disabled type="text" id="display_value' + loop_count + i + '" name="display_value' + loop_count + i + '" value="' + result.data[k].display_val + '"/></div><div class="col-lg-2 display_byte_level' + loop_count + '" style="margin-top:5px;"><input class="" type="checkbox" id="check_box' + loop_count + i + '" name="check_box' + loop_count + i + '" value="'+result.data[k].selection_val_id+'" style="width: 25px;height: 25px;"/></div><div class="col-lg-5 display_byte_level' + loop_count + '" style="margin-top:5px;"><input class="form-control" disabled type="text" id="byte_value' + loop_count + i + '" name="byte_value' + loop_count + i + '" value="' + result.data[k].byte_val + '"/></div>');
                k++;
            }

        }
    });
}

function enable_param() {
    $("#parameter").attr('disabled', false);
    $("#selection_no").attr('disabled', false);
    $("#input_no").attr('disabled', false);
    $("#bitwise").attr('disabled', false);
}

function addParamValueToCommand() {
    var param = $('#parameter').val();
    var command = document.getElementById("command").value;
    var para = command + "/" + param + "/";
    //command.value = para;

    $("#command").val(para);
}

function showDivHide() {
    if ($('#selection_div_start').css('display') == 'none') {
        $('#selection_div_start').show();
        $('#show_hide_btn').text("Hide");
    } else {
        $('#selection_div_start').hide();
        $('#show_hide_btn').text("Show");
    }
}


function showInputValueFields(val) {

    $('#input_show_hide_btn').show();

    $('.input_div_class').remove();

    $('#input_div_start').show();
    var count = val;
    for (var i = 1; i <= count; i++) {
        $('#input_field_div').append('<div class="col-lg-1 input_div_class" style="margin-top:5px;"><label style="font-size:16px;">' + i + '</label></div><div class="col-lg-3 input_div_class" style="margin-top:5px"><input class="form-control myAutocompleteClass" type="text" id="input_parameter' + i + '" name="input_parameter' + i + '"/></div>');
    }
}



function inputShowDivHide() {
    if ($('#input_div_start').css('display') == 'none') {
        $('#input_div_start').show();
        $('#input_show_hide_btn').text("Hide");
    } else {
        $('#input_div_start').hide();
        $('#input_show_hide_btn').text("Show");
    }
}


function showBitwiseValueFields(val) {

    $('#show_hide_btn_bitwise').show();

    $('.bitwise_div_class').remove();

    $('#bitwise_div_start').show();
    var count = val;
    for (var i = 1; i <= count; i++) {
        $('#bitwise_field_div').append('<div class="col-lg-4 bitwise_div_class" style="border:2px solid rgb(56, 165, 238, 0.5);padding:0px 0px 4px 0px" id="bitwise_div_' + i + '"><div class="col-lg-1" style="margin-top:5px;padding:0px 0px 0px 0px"><label style="font-size:16px;">' + i + '</label></div><div class="col-lg-5" style="margin-top:5px;padding:0px;margin-left: -15px;"><input class="form-control myAutocompleteClass" type="text" id="bitwise_parameter' + i + '" name="bitwise_parameter' + i + '"/></div><div class="col-lg-5" style="margin-top:5px"><label style="font-size:16px;">Sub Byte Division</label></div><div class="col-lg-1" style="margin-top:5px;padding:0px;margin-left: -15px;"><input style="width:60px;" class="form-control myAutoCompleteForBitwiseValue" type="text" onchange="getBitwiseValue(value,' + i + ');" id="bitwise_values' + i + '" name="bitwise_values' + i + '"/></div></div>');
    }
}

function showDivHideBitwise() {
    if ($('#bitwise_div_start').css('display') == 'none') {
        $('#bitwise_div_start').show();
        $('#show_hide_btn_bitwise').text("Hide");
    } else {
        $('#bitwise_div_start').hide();
        $('#show_hide_btn_bitwise').text("Show");
    }
}


function getBitwiseValue(val, loop_count) {
//alert("selction val; --"+val+" looop ci=ont -"+loop_count);
    $('.sub_byte_div_class' + loop_count).remove();    

    var sub_byte_div_val = val;
    var param = $('#bitwise_parameter' + loop_count).val();

    $.ajax({
        url: "CommandController",
        contentType: "application/json",
        dataType: 'json',
        data: {
            action1: "getSubByteDivVal",
            str: "dummy",
            sub_byte_div_val: sub_byte_div_val,
            param: param
        },
        success: function (result) {
            //alert(result);
            console.log(result);
            var k = 0;
            var val_count = result.data.length;

            //$.isNumeric(id)
            if ($.isNumeric(val) && val>0 && param!='') {
                $('#bitwise_div_' + loop_count).append('<div class="col-lg-4 sub_byte_div_class' + loop_count + '" style="margin-top:5px;"><label style="font-size:14px;">Sub Byte Name</label></div><div class="col-lg-2 sub_byte_div_class' + loop_count + '" style="margin-top:5px;"><label style="font-size:14px;">Start Pos</label></div><div class="col-lg-2 sub_byte_div_class' + loop_count + '" style="margin-top:5px;"><label style="font-size:14px;">No Of Bit</label></div><div class="col-lg-2 sub_byte_div_class' + loop_count + '" style="margin-top:5px;"><label style="font-size:14px;">Sub Div No</label></div>');
            }
//
            for (var i = 1; i <= val_count; i++) {                
                //$('#bitwise_div_' + loop_count).append('<div class="col-lg-4 sub_byte_div_class' + loop_count + '" style="margin-top:5px;" id="sub_byte_div_class_'+i+'"><input class="form-control" disabled type="text" id="sub_param_name' + loop_count + i + '" name="sub_param_name' + loop_count + i + '" value="' + result.data[k].sub_parameter_name + '"/></div><div class="col-lg-2 sub_byte_div_class' + loop_count + '" style="margin-top:5px;"><input class="form-control" disabled type="text" id="start_pos' + loop_count + i + '" name="start_pos' + loop_count + i + '" value="' + result.data[k].start_pos + '"/></div><div class="col-lg-2 sub_byte_div_class' + loop_count + '" style="margin-top:5px;"><input class="form-control" disabled type="text" id="no_of_bit' + loop_count + i + '" name="no_of_bit' + loop_count + i + '" value="' + result.data[k].no_of_bit + '"/></div><div class="col-lg-2 sub_byte_div_class' + loop_count + '" style="margin-top:5px;"><input class="form-control" disabled type="text" id="sub_div_no' + loop_count + i + '" name="sub_div_no' + loop_count + i + '" value="' + result.data[k].sub_div_no + '"/></div><div class="col-lg-2 sub_byte_div_class' + loop_count + '" style="margin-top:5px;"><button type="button" class="btn btn-success" onclick="getButtonIdForDisplayVal(value,'+loop_count+');" id="sub_byte_div_id' + loop_count + i + '" name="sub_byte_div_id' + loop_count + i + '" value="' + result.data[k].sub_byte_div_id + '">+</button</div>');                
                $('#bitwise_div_' + loop_count).append('<div class="col-lg-12 sub_byte_div_class' + loop_count +i+ '" id="sub_byte_div_class_'+loop_count+i+'" style="border:2px solid;"><div class="col-lg-4" style="margin-top:5px;"><input class="form-control" disabled type="text" id="sub_param_name' + loop_count + i + '" name="sub_param_name' + loop_count + i + '" value="' + result.data[k].sub_parameter_name + '"/></div><div class="col-lg-2 sub_byte_div_class' + loop_count + '" style="margin-top:5px;"><input class="form-control" disabled type="text" id="start_pos' + loop_count + i + '" name="start_pos' + loop_count + i + '" value="' + result.data[k].start_pos + '"/></div><div class="col-lg-2 sub_byte_div_class' + loop_count + '" style="margin-top:5px;"><input class="form-control" disabled type="text" id="no_of_bit' + loop_count + i + '" name="no_of_bit' + loop_count + i + '" value="' + result.data[k].no_of_bit + '"/></div><div class="col-lg-2 sub_byte_div_class' + loop_count + '" style="margin-top:5px;"><input class="form-control" disabled type="text" id="sub_div_no' + loop_count + i + '" name="sub_div_no' + loop_count + i + '" value="' + result.data[k].sub_div_no + '"/></div><div class="col-lg-2 sub_byte_div_class' + loop_count + '" style="margin-top:5px;"><button type="button" class="btn btn-success" onclick="getButtonIdForDisplayVal(value,'+loop_count+','+i+');" id="sub_byte_div_id' + loop_count + i + '" name="sub_byte_div_id' + loop_count + i + '" value="' + result.data[k].sub_byte_div_id + '">+</button</div></div>');                
                k++;
            }

        }
    });  

}


function getButtonIdForDisplayVal(val,loop_count,itr){
    //alert("val -"+val+" loop count -"+loop_count+" itr -"+itr);
    $('#sub_byte_div_id'+loop_count+itr).prop('disabled', true);
    
    $.ajax({
        url: "CommandController",
        contentType: "application/json",
        dataType: 'json',
        data: {
            action1: "getBitwiseDisByteVal",
            str: "dummy",
            id: val            
        },
        success: function (result) {
            //alert(result);
            console.log(result);
            var k = 0;
            var val_count = result.data.length;

            //$.isNumeric(id)
            if ($.isNumeric(val) && val>0) {
                $('#sub_byte_div_class_' + loop_count+itr).append('<div class="col-lg-5 bitwise_disp_byte_class' + loop_count + '" style="margin-top:5px;"><label style="font-size:16px;">Display Value</label></div><div class="col-lg-2 bitwise_disp_byte_class' + loop_count + '" style="margin-top:5px;"><label style="font-size:16px;"></label></div><div class="col-lg-5 bitwise_disp_byte_class' + loop_count + '" style="margin-top:5px;"><label style="font-size:16px;">Bit Value</label></div>');
            }
//
            for (var i = 1; i <= val_count; i++) {                
                $('#sub_byte_div_class_' + loop_count+itr).append('<div class="col-lg-5 bitwise_disp_byte_class' + loop_count + '" style="margin-bottom:5px;"><input class="form-control" disabled type="text" id="sub_param_name' + loop_count + itr + '" name="sub_param_name' + loop_count + itr + '" value="' + result.data[k].display_val + '"/></div><div class="col-lg-2 bitwise_disp_byte_class' + loop_count + '" style="margin-bottom:5px;"><input class="form-control" type="checkbox" style="height:25px;width:25px;" id="bitwise_disp_byte_id' + loop_count + itr + '" name="bitwise_disp_byte_id' + loop_count + itr + '" value="' + result.data[k].sub_div_sel_id + '"/></div><div class="col-lg-5 bitwise_disp_byte_class' + loop_count + '" style="margin-bottom:5px;"><input class="form-control" disabled type="text" id="start_pos' + loop_count + itr + '" name="start_pos' + loop_count + itr + '" value="' + result.data[k].byte_val + '"/></div>');                
                k++;
            }

        }
    }); 
}