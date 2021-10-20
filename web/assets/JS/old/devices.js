/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
    $(document).on('keydown', '.myAutocompleteClass', function () {
        var id = this.id;
        // alert("iddd -"+id.split("_"));
        var arr = [];
        arr = id.split("_");
        //alert(arr[3]);        

        var model_name, device_type, mf_name, fin_mf_name, fin_device_type, fin_model_name, fin_model_no, model_no;
        var type;
        if (id.match("^manufacturer_name_module")) {
            type = "manufacturer_name";
        } else if (id.match("^device_type_module")) {
            type = "device_type";
        } else if (id.match("^model_name_module")) {
            type = "model_name";
        } else if (id.match("^model_no_module")) {
            // finished
            fin_mf_name = $('#manufacturer_name').val();
            //alert("fin_mf_name -"+fin_mf_name);
            fin_device_type = $('#device_type').val();
            fin_model_name = $('#model_name').val();
            fin_model_no = $('#model_no').val();

            // module
            mf_name = (($('#manufacturer_name_module_' + arr[3])).val());
            device_type = (($('#device_type_module_' + arr[3])).val());
            model_name = (($('#model_name_module_' + arr[3])).val());
            //model_name=(($('#model_name_module_'+arr[3])).val());

            if (arr[3] > 0) {
                model_no = (($('#model_no_module_' + (arr[3]-1))).val());
                //alert("model no --"+model_no);
            }
            

            //alert(model_name);
            type = "model_no";
        }

        //alert("type --"+type);


        var random = this.value;
        $('#' + id).autocomplete({
            source: function (request, response) {
                $.ajax({
                    url: "DeviceController",
                    dataType: "json",
                    data: {
                        action1: "getParameter",
                        str: random,
                        model_no: model_no,
                        type: type,
                        //module
                        mf_name: mf_name,
                        device_type: device_type,
                        model_name: model_name,
                        

                        // finished
                        fin_mf_name: fin_mf_name,
                        fin_device_type: fin_device_type,
                        fin_model_name: fin_model_name,
                        fin_model_no: fin_model_no,

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