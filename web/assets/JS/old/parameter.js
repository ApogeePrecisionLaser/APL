/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function getParameterType(val) {
    $('.selection_div_class').remove();
    $('.bitwise_div_class').remove();
    $('#sel_val_div').hide();
    $('#bitiwse_no_div').hide();
    $('#selection_div_start').hide();
    $('#bitwise_field_div').hide();
    if (val == 'Selection' || val=='Fixed Response') {
        $('#sel_val_div').show();
    } else if (val == 'Bitwise' || val=='Bitwise Response') {
        $('#bitiwse_no_div').show();
    }
}

function getSelectionValueNo(val) {
    $('.selection_div_class').remove();
    $('#selection_div_start').show();
    $('#selection_field_div').show();
    val_count = val;
    for (var i = 1; i <= val_count; i++) {
        $('#selection_field_div').append('<div class="col-lg-6 selection_div_class" style="border:2px solid rgb(56, 165, 238, 0.5);"><div class="row"><div class="col-lg-6"><label>Display Value</label></div><div class="col-lg-6"><label>Byte Value</label></div></div><div class="row" style="margin-bottom: 10px;"><div class="col-lg-6"><input type="text" name="diaplay_value_' + i + '" id="diaplay_value_' + i + '" class="form-control" placeholder="Enter Display Value"></div><div class="col-lg-6"><input type="text" name="byte_value_' + i + '" id="byte_value_' + i + '" class="form-control" placeholder="Enter Byte Value"></div></div></div>');
    }
}

function getBitwiseNo(val) {
    $('.bitwise_div_class').remove();
    $('#bitwise_div_start').show();
    $('#bitwise_field_div').show();
    var count = val;
    for (var i = 1; i <= 1; i++) {
        $('#bitwise_field_div').append('<div class="col-lg-12 bitwise_div_class" style="border:2px solid rgb(56, 165, 238, 0.5);padding:0px 0px 4px 0px" id="bitwise_div_class_' + i + '"><div class="col-lg-1" style="margin-top:5px;padding:0px 0px 0px 0px"><label style="font-size:16px;">' + i + '</label></div><div class="col-lg-5" style="margin-top:5px;padding:0px;margin-left: -15px;"></div><div class="col-lg-5" style="margin-top:5px"><label style="font-size:16px;">Sub Byte division</label></div><div class="col-lg-1" style="margin-top:5px;padding:0px;margin-left: -15px;"><input class="form-control" type="number" onchange="getSubByteDivValue(value,' + i + ');" id="sub_byte_division_' + i + '" name="sub_byte_division_' + i + '"/></div></div>');
    }
}

function getSubByteDivValue(val, loop_count) {
    $('.sub_bitwise_div_class' + loop_count).remove();
    var val_count = val;
    if (val_count > 0) {
        $('#bitwise_div_class_' + loop_count).append('<div class="col-lg-12 sub_bitwise_div_class' + loop_count + '" style="border:2px solid red;padding:0px 0px 4px 0px" id="sub_bitwise_div_class' + loop_count + '"><div class="col-lg-4" style="margin-top:5px;background:red;"><label style="font-size:16px;">Sub-Div Selection No</label></div><div class="col-lg-4" style="margin-top:5px;background:red;"><label style="font-size:16px;">Start Position</label></div><div class="col-lg-4" style="margin-top:5px;background:red;"><label style="font-size:16px;">No Of Bit</label></div></div>');
    }
    for (var i = 1; i <= val_count; i++) {
        $('#sub_bitwise_div_class' + loop_count).append('<div class="col-lg-12 sub_bitwise_div_class" style="padding:0px 0px 4px 0px" id="sub_sub_bitwise_div_class_' + loop_count + i + '"><div class="col-lg-4 sub_bitwise_div_class' + loop_count + '" style="margin-top:5px;" id="sub_bitwise_div_class_' + loop_count + i + '"><input class="form-control" type="number" id="sub_div_sel_no_' + loop_count + i + '" name="sub_div_sel_no' + loop_count + i + '" onchange="getSubDivSelNo(value,' + loop_count + i + ');"/></div><div class="col-lg-4 sub_bitwise_div_class' + loop_count + '" style="margin-top:5px;"><input class="form-control" type="text" id="start_position_' + loop_count + i + '" name="start_position_' + loop_count + i + '"/></div><div class="col-lg-4 sub_bitwise_div_class' + loop_count + '" style="margin-top:5px;"><input class="form-control" type="text" id="no_of_bit_' + loop_count + i + '" name="no_of_bit_' + loop_count + i + '"/></div></div>');
    }
}


function getSubDivSelNo(val, loop_count) {
    $('.sub_bitwise_display_byte_class' + loop_count).remove();
    var val_count = val;
    if (val_count > 0) {
        $('#sub_sub_bitwise_div_class_' + loop_count).append('<div class="col-lg-12 sub_bitwise_display_byte_class' + loop_count + '" style="border:4px solid yellow;padding:0px 0px 4px 0px" id="sub_bitwise_display_byte_class_' + loop_count + '"><div class="col-lg-4 sub_bitwise_display_byte_class_header' + loop_count + '" style="margin-top:5px;background:yellow;"><label style="font-size:16px;">S.No</label></div><div class="col-lg-4 sub_bitwise_div_class_header' + loop_count + '" style="margin-top:5px;background:yellow;"><label style="font-size:16px;">Display Value</label></div><div class="col-lg-4 sub_bitwise_display_byte_class_header' + loop_count + '" style="margin-top:5px;background:yellow;"><label style="font-size:16px;">bit Value</label></div></div>');
    }
    for (var i = 1; i <= val_count; i++) {
        //$('#sub_bitwise_div_class' + loop_count).append('<div class="col-lg-4 sub_bitwise_display_byte_class_header' + loop_count + '" style="margin-top:5px;"><label style="font-size:16px;">S.No</label></div><div class="col-lg-4 sub_bitwise_display_byte_class_header' + loop_count + '" style="margin-top:5px;"><label style="font-size:16px;">Start Position</label></div><div class="col-lg-4 sub_bitwise_div_class' + loop_count + '" style="margin-top:5px;"><label style="font-size:16px;">Display Value</label></div><div class="col-lg-4 sub_bitwise_display_byte_class_header' + loop_count + '" style="margin-top:5px;"><label style="font-size:16px;">bit Value</label></div>');
        $('#sub_bitwise_display_byte_class_' + loop_count).append('<div class="col-lg-4 sub_bitwise_display_byte_class' + loop_count + '" style="margin-top:5px;"><input class="form-control" type="text" id="serial_no' + loop_count + i + '" name="serail_no' + loop_count + i + '" value="' + i + '"/></div><div class="col-lg-4 sub_bitwise_display_byte_class' + loop_count + '" style="margin-top:5px;"><input class="form-control" type="text" id="display_value_' + loop_count + i + '" name="display_value_' + loop_count + i + '"/></div><div class="col-lg-4 sub_bitwise_display_byte_class' + loop_count + '" style="margin-top:5px;"><input class="form-control" type="text" id="bit_value_' + loop_count + i + '" name="bit_value_' + loop_count + i + '"/></div>');
    }
}
