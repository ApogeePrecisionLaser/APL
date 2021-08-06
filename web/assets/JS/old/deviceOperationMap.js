/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(function(){
   $(document).on('keydown', '.myAutocompleteClass', function () {
        var id = this.id;
        //alert("iddd -"+id);
        var mf_name="";
        var device_type="";
        var model_name="";
        var model_no="";
        
        var action;
        if (id.match("^manufacturer_name")) {
            action='getManufactureName';
        } else if (id.match("^device_type")) {
            action='getDeviceTypeName';
            mf_name=$('#manufacturer_name').val();
        } else if (id.match("^model_name")) {
            action='getDeviceName';
            mf_name=$('#manufacturer_name').val();
            device_type=$('#device_type').val();            
        } else if (id.match("^model_no")) {
            action='getDeviceNo';
            mf_name=$('#manufacturer_name').val();
            device_type=$('#device_type').val();            
            model_name=$('#model_name').val();            
        }
        //alert("type --"+type);


        var random = this.value;
        $('#' + id).autocomplete({
            source: function (request, response) {
                $.ajax({
                    url: "FinDevOpMapController",
                    dataType: "json",
                    data: {
                        action1: action,
                        mf_name: mf_name,
                        device_type: device_type,
                        model_name: model_name,
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
                $(this).val(ui.item.label); // display the selected text
                return false;
            }
        });
    }); 
});