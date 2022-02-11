<%@taglib prefix="myfn" uri="http://MyCustomTagFunctions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<%@include file="../layout/header.jsp" %>
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="collapsetable/css/bootstrap.min.css">
<!--<script src="collapsetable/js/jquery-3.1.1.min.js"></script>-->
<!--<script src="collapsetable/js/bootstrap.min.js"></script>-->
<script src="collapsetable/js/javascript.js"></script>

<style>
    .selected_row {
        font-weight: bolder;
        color: blue;
        border: 3px solid black;
    }
    table.dataTable {      
        border-collapse: collapse;
    }
    .treegrid-indent {
        width: 0px;
        height: 16px;
        display: inline-block;
        position: relative;
    }
    .treegrid-expander {
        width: 0px;
        height: 16px;
        display: inline-block;
        position: relative;
        left:-17px;
        cursor: pointer;
    }
    label{
        font-size: 13px;
    }
    table{
        font-size:13px
    }
    .ui-widget{
        font-size: 1.4em;
    }

    .scrollable {
        overflow: auto;
        height: 100px;
        border: 1px solid #332A7C;
        font-size: 13px;
    }
</style>
<script>
    $(document).ready(function () {
        $('#mytable tbody').on('click', 'tr', function () {
            if ($(this).hasClass('selected_row')) {
                $(this).removeClass('selected_row');
            } else {
                $("#mytable").DataTable().$(
                        'tr.selected_row').removeClass(
                        'selected_row');
                $(this).addClass('selected_row');
            }
        });
    });
    $(function () {
        setTimeout(function () {
            $('#message').fadeOut('fast');
        }, 10000);
        $("#item_type").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("item_type").value;
                $.ajax({
                    url: "ItemNameController",
                    dataType: "json",
                    data: {action1: "getItemType", str: random},
                    success: function (data) {
                        console.log(data);
                        response(data.list);
                    }, error: function (error) {
                        console.log(error.responseText);
                        response(error.responseText);
                    }
                });
            },
            select: function (events, ui) {
                console.log(ui);
                $('#item_type').val(ui.item.label);
                return false;
            }
        });
        $("#search_item_type").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("search_item_type").value;
                $.ajax({
                    url: "ItemNameController",
                    dataType: "json",
                    data: {action1: "getItemType", str: random},
                    success: function (data) {
                        console.log(data);
                        response(data.list);
                    }, error: function (error) {
                        console.log(error.responseText);
                        response(error.responseText);
                    }
                });
            },
            select: function (events, ui) {
                console.log(ui);
                $('#search_item_type').val(ui.item.label);
                return false;
            }
        });
        $("#search_item_code").autocomplete({
            source: function (request, response) {
                var item_type = document.getElementById("search_item_type").value;
                var item_name = document.getElementById("search_item_name").value;
                var random = document.getElementById("search_item_code").value;
                $.ajax({
                    url: "ItemNameController",
                    dataType: "json",
                    data: {action1: "getItemCode", str: random, str2: item_type, str3: item_name},
                    success: function (data) {
                        console.log(data);
                        response(data.list);
                    }, error: function (error) {
                        console.log(error.responseText);
                        response(error.responseText);
                    }
                });
            },
            select: function (events, ui) {
                console.log(ui);
                $('#search_item_code').val(ui.item.label);
                return false;
            }
        });
        $("#search_item_name").autocomplete({
            source: function (request, response) {
                var item_type = document.getElementById("search_item_type").value;
                var random = document.getElementById("search_item_name").value;
                $.ajax({
                    url: "ItemNameController",
                    dataType: "json",
                    data: {action1: "getItemName", str: random, str2: item_type},
                    success: function (data) {
                        console.log(data);
                        response(data.list);
                    }, error: function (error) {
                        console.log(error.responseText);
                        response(error.responseText);
                    }
                });
            },
            select: function (events, ui) {
                console.log(ui);
                $('#search_item_name').val(ui.item.label);
                return false;
            }
        });
        $("#parent_item").autocomplete({
            source: function (request, response) {
                //var item_type = document.getElementById("item_type").value;
                var random = document.getElementById("parent_item").value;
                $.ajax({
                    url: "ItemNameController",
                    dataType: "json",
                    data: {action1: "getParentItemName", str: random},
                    success: function (data) {
                        console.log(data);
                        response(data.list);
                    }, error: function (error) {
                        console.log(error.responseText);
                        response(error.responseText);
                    }
                });
            },
            select: function (events, ui) {
                console.log(ui);
                $('#parent_item').val(ui.item.label);
                return false;
            }
        });
        $("#search_generation").autocomplete({
            source: function (request, response) {
                //var item_type = document.getElementById("item_type").value;
                var random = document.getElementById("search_generation").value;
                $.ajax({
                    url: "ItemNameController",
                    dataType: "json",
                    data: {action1: "getGeneration", str: random},
                    success: function (data) {
                        console.log(data);
                        response(data.list);
                    }, error: function (error) {
                        console.log(error.responseText);
                        response(error.responseText);
                    }
                });
            },
            select: function (events, ui) {
                console.log(ui);
                $('#search_generation').val(ui.item.label);
                return false;
            }
        });
        $("#search_super_child").autocomplete({
            source: function (request, response) {
                //var item_type = document.getElementById("item_type").value;
                var random = document.getElementById("search_super_child").value;
                $.ajax({
                    url: "ItemNameController",
                    dataType: "json",
                    data: {action1: "getSuperChild", str: random},
                    success: function (data) {
                        console.log(data);
                        response(data.list);
                    }, error: function (error) {
                        console.log(error.responseText);
                        response(error.responseText);
                    }
                });
            },
            select: function (events, ui) {
                console.log(ui);
                $('#search_super_child').val(ui.item.label);
                return false;
            }
        });



//
//        $("#designation").autocomplete({
//            source: function (request, response) {
//                var random = document.getElementById("designation").value;
//                var org_office = document.getElementById("org_office").value;
//                if (org_office == '') {
//                    alert("Please select org office!..");
//                    return  false;
//                }
//                $.ajax({
//                    url: "ItemNameController",
//                    dataType: "json",
//                    data: {action1: "getDesignation", str: random, org_office: org_office},
//                    success: function (data) {
//                        console.log(data);
//                        response(data.list);
//                    }, error: function (error) {
//                        console.log(error.responseText);
//                        response(error.responseText);
//                    }
//                });
//            },
//            select: function (events, ui) {
//                console.log(ui);
//                $('#designation').html('<input type="checkbox" name="designation_check">' + ui.item.label);
//                return false;
//            }
//        });

        $("#org_office").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("org_office").value;
                var search_org_office = "";
                //  alert("jfhg");
                $.ajax({
                    url: "InventoryBasicController",
                    dataType: "json",
                    data: {action1: "getOrgOffice", str: random, search_org_office: search_org_office},
                    success: function (data) {
                        console.log(data);
                        response(data.list);
                    }, error: function (error) {
                        console.log(error.responseText);
                        response(error.responseText);
                    }
                });
            },
            select: function (events, ui) {
                console.log(ui);
                $('#org_office').val(ui.item.label);
                return false;
            }
        });




//        $('#item_name').blur(function () {
//            var min = 10000;
//            var max = 900000;
//            var item_name=$('#item_name').val();
//            var num = Math.floor(Math.random() * min) + max;
//            var auto_item_code = "APL_ITEM_"+item_name +"_"+ num;
//            $('#item_code').val(auto_item_code);
//        });
    });



    function getDesignation() {
        var random = "";
        var org_office = document.getElementById("org_office").value;
        $('#des_list').empty();
        if (org_office == '') {
            alert("Please select org office!..");
            return  false;
        }
        $.ajax({
            url: "ItemNameController",
            dataType: "json",
            data: {action1: "getDesignation", str: random, org_office: org_office},
            success: function (data) {
                console.log(data);
//                response(data.list);
//                alert(data.list.length);
                console.log(data.list.length);
                if (data.list.length > 1) {
                    $('#des_list').append('<input name="designation" type="checkbox" id="checkAll" value="All" onchange="checkAllCheckBoxes()" > All');
                }
                for (var i = 0; i < data.list.length; i++) {
                    var designation = data.list[i];
                    var myArray = designation.split("-");
                    designation = myArray[0];
                    var designation_id = myArray[1];

                    $('#des_list').append(' <li><input name="designation" type="checkbox" onchange="getKeyPerson(' + designation_id + ');" id="des_check' + designation_id + '" value="' + designation + '"> ' + designation + '<input name="designation_id" type="checkbox" value="' + designation_id + '" hidden id="designation_id' + designation_id + '"></li>');
                }

//                $('#designation').html('<input type="checkbox" name="designation_check">' + );
            }
        });
    }

    function getKeyPerson(designation_id) {
        var org_office = $('#org_office').val();
//        alert(org_office);
        var checkAll = document.getElementById("checkAll");
        var random = "";
        if ($('#des_check' + designation_id).prop("checked") == true) {
            var designation = $('#des_check' + designation_id).val();
            $('#designation_id' + designation_id).attr("checked", "true");
//            alert(designation);
//            $('#key_person_div').append('<div class="col-md-3" id="key_person_col' + designation_id + '"><div class=""><div class="form-group"><label>' + designation + '<span class="text-danger">*</span></label><div class="scrollable" id="key_person_list"><li><input name="key_person" type="checkbox"  id="key_person_check' + key_person_id + '" value="' + designation + '"> ' + designation + '</li></div></div></div></div>');
            $.ajax({
                url: "ItemNameController",
                dataType: "json",
                data: {action1: "getKeyPerson", str: random, org_office: org_office, designation_id: designation_id},
                success: function (data) {
                    $('#key_person_div').show();
                    $('#key_person_div').append('<div class="col-md-3" id="key_person_col' + designation_id + '"><div class=""><div class="form-group"><label>' + designation + '<span class="text-danger">*</span></label><div class="scrollable" id="key_person_list' + designation_id + '"></div></div></div></div>');

                    for (var i = 0; i < data.list.length; i++) {
                        var key_person = data.list[i];
                        var myArray = key_person.split("-");
                        key_person = myArray[0];
                        var key_person_id = myArray[1];
                        $('#key_person_list' + designation_id).append('<li><input name="key_person" type="checkbox"  id="key_person_check' + key_person_id + '" value="' + key_person + '"> ' + key_person + '</li>');

//                        $('#des_list').append(' <li><input name="designation" type="checkbox" onchange="getKeyPerson(' + designation_id + ');" id="des_check' + designation_id + '" value="' + designation + '"> ' + designation + '</li>');
                    }

                }
            });


        } else {
            $('#key_person_col' + designation_id).remove();
            $('#designation_id' + designation_id).removeAttr("checked");

        }
    }

    function checkAllCheckBoxes() {

        var checkboxes = document.getElementsByTagName('input');

        var val = null;
        for (var i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].type == 'checkbox') {
                if (val === null)
                    val = checkboxes[i].checked;
                checkboxes[i].checked = val;


            }
        }
    }

    function makeEditable(id) {
        document.getElementById("item_type").disabled = false;
        document.getElementById("item_name").disabled = false;
        //  document.getElementById("item_code").disabled = false;
//        document.getElementById("quantity").disabled = false;
        document.getElementById("HSNCode").disabled = false;
        document.getElementById("prefix").disabled = false;
        document.getElementById("description").disabled = false;
        document.getElementById("parent_item").disabled = false;
        document.getElementById("supern").disabled = false;
        document.getElementById("supery").disabled = false;
        document.getElementById("save").disabled = false;
        if (id === 'new') {
            $("#message").html("");
            document.getElementById("item_name_id").value = "";
            document.getElementById("item_type").focus();
        }
        if (id == 'edit') {
            document.getElementById("save_As").disabled = true;
            document.getElementById("delete").disabled = false;
        }
    }
    function setStatus(id) {
        if (id === 'save') {
            document.getElementById("clickedButton").value = "Save";
        } else {
            document.getElementById("clickedButton").value = "Delete";
        }
    }
    function myLeftTrim(str) {
        var beginIndex = 0;
        for (var i = 0; i < str.length; i++) {
            if (str.charAt(i) === ' ')
                beginIndex++;
            else
                break;
        }
        return str.substring(beginIndex, str.length);
    }
    function verify() {
        var result;
        if (document.getElementById("clickedButton").value === 'Save' || document.getElementById("clickedButton").value === 'Save AS New') {
            var item_type = document.getElementById("item_type").value;
            var item_name = document.getElementById("item_name").value;
//            var quantity = document.getElementById("quantity").value;
            var prefix = document.getElementById("prefix").value;
            if (myLeftTrim(item_type).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Item Type is required...</b></label></div>');
                document.getElementById("item_type").focus();
                return false;
            }
            if (myLeftTrim(item_name).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Item Name is required...</b></label></div>');
                document.getElementById("item_name").focus();
                return false;
            }
            if (myLeftTrim(prefix).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Prefix is required...</b></label></div>');
                document.getElementById("prefix").focus();
                return false;
            }
            if (result === false) {
            } else {
                result = true;
            }
            if (document.getElementById("clickedButton").value === 'Save AS New') {
                result = confirm("Are you sure you want to save it as New record?")
                return result;
            }
        } else {
            result = confirm("Are you sure you want to delete this record?");
        }
        return result;
    }
    function verifySearch() {
        var result;
        if (document.getElementById("clickedButton").value === 'SEARCH') {
            var org_name = document.getElementById("org_name").value;
            if (myLeftTrim(org_name).length === 0) {
                document.getElementById("org_msg").innerHTML = "<b>Organization Name is required...</b>";
                document.getElementById("org_name").focus();
                return false; // code to stop from submitting the form2.
            }
        }
    }
//    function viewImages(id, img) {
//        popupwin = openPopUp("Show Image List", 1000, 1000, id);
//    }
    function openPopUp(window_name, popup_height, popup_width, id) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
        // var queryString = "task1=getImageList&item_names_id=" + id;
        // var url = "ItemImagesController?" + queryString;
        return window.open(url, window_name, window_features);
    }
    if (!document.all) {
        document.captureEvents(Event.CLICK);
    }
//    document.onclick = function () {
//        if (popupwin !== null && !popupwin.closed) {
//            popupwin.focus();
//        }
//    }
    function fillColumn(id, count) {

        $('#item_name_id').val(id);

        $('#item_name').val($("#" + count + '2').text());
        // alert($("#" + count + '3').text());
        $('#prefix').val($("#" + count + '3').html());
        $('#item_type').val($("#" + count + '5').html());
//        $('#quantity').val($("#" + count + '6').html());
        $('#HSNCode').val($("#" + count + '7').html());
        if (($("#" + count + '10').text()) != "") {
            $('#parent_item').val($("#" + count + '10').html() + " - " + $("#" + count + '12').html());
        } else {
            $('#parent_item').val($("#" + count + '10').html());
        }
        var super_child = $("#" + count + '11').html();
        if (super_child == 'Y') {
            $('#supery').attr('checked', true);
        } else {
            $('#supern').attr('checked', true);
        }
        $('#description').val($("#" + count + '9').html());
        document.getElementById("edit").disabled = false;
        document.getElementById("delete").disabled = false;
    }


    function showQuantity() {
        var is_super_child = $("input[name='super']:checked").val();
        if (is_super_child == 'Y') {
//            $('#quantity_div').show();
            $('#HSNCode_div').show();
            $('.is_super_child_yes').show();
            $('#designation_div').show();
            $('#org_office_div').show();
            $('#key_person_div').show();


        } else {
//            $('#quantity_div').hide();
            $('#HSNCode_div').hide();
            $('.is_super_child_yes').hide();
            $('#designation_div').hide();
            $('#org_office_div').hide();
            $('#key_person_div').hide();
        }
    }
    function getOrgChartData(item_name) {
        window.open("ItemNameController?org_chart=Org Chart&item_name=" + item_name);
    }

    function getItemTypeForModelOrPart() {
        var item_type = $('#item_type').val();

        if (item_type == "Non-Raw Material") {
            $('.model_no_div').show();
            $('.part_no_div').hide();
        } else {
            $('.model_no_div').hide();
            $('.part_no_div').show();
        }

    }

    $(document).ready(function () {

        // Add new element
        $(".add").click(function () {
            var count = $('#count').val();
//            alert(count);
//            if (count > 0) {
//                total_element = 0;
//            }
            // Finding total number of elements added
            var total_element = $(".is_super_child_yes").length;

            // last <div> with element class id
            var lastid = $(".is_super_child_yes:last").attr("id");
            var split_id = lastid.split("_");
            var nextindex = Number(split_id[1]) + 1;

            var max = 5;
            // Check total number elements
            if (total_element < max) {
                // Adding new div container after last occurance of element class
                $(".is_super_child_yes:last").after("<div class='is_super_child_yes row' id='superchilddiv_" + nextindex + "'></div>");

                // Adding element to <div>
                $("#superchilddiv_" + nextindex).append('<div class="col-md-2"><div class=""><div class="form-group"><input class="form-control myInput" type="hidden" id="model_id_' + nextindex + '" name="model_id_' + nextindex + '" value="" ><input class="form-control myInput" type="hidden" id="manufacturer_item_map_id_' + nextindex + '" name="manufacturer_item_map_id_' + nextindex + '" value="" ><input type="hidden" id="item_image_details_id_' + nextindex + '" name="item_image_details_id_' + nextindex + '" value="" size="28"   /><input class="form-control myInput myAutocompleteClass" type="text" id="manufacturer_name_' + nextindex + '" name="manufacturer_name_' + nextindex + '" value="" size="40"></div></div></div><div class="col-md-2"><div class=""><div class="form-group"><input class="form-control myInput" type="text" id="model_' + nextindex + '" name="model_' + nextindex + '" value="" size="40"  onblur="getItemTypeForModelOrPart()"></div></div></div><div class="col-md-2 model_no_div" id="model_no_div" style="display: none"><div class=""><div class="form-group"><input class="form-control myInput" type="text" id="model_no_' + nextindex + '" name="model_no_' + nextindex + '" value="" size="40"></div></div></div><div class="col-md-2 part_no_div" id="part_no_div" style="display: none"><div class=""><div class="form-group"><input class="form-control myInput" type="text" id="part_no_' + nextindex + '" name="part_no_' + nextindex + '" value="" size="40"  ></div></div></div><div class="col-md-2"><div class=""><div class="form-group"><input class="form-control myInput" type="text" id="lead_time_' + nextindex + '" name="lead_time_' + nextindex + '" value="0"  size="40"></div></div></div><div class="col-md-1"><div class=""><div class="form-group"><input class="form-control myInput" type="text" id="basic_price_' + nextindex + '" name="basic_price_' + nextindex + '" value="0" size="40" ></div></div></div><div class="col-md-2"><div class=""><div class="form-group"><input class="form-control myInput" type="file" multiple id="item_image_' + nextindex + '" name="item_image"  size="30" value=""  onchange="readURL(this);"></div></div></div><input type="button" class="btn btn-danger remove" id="remove_' + nextindex + '" style="height:35px;" value="X">');

            } else {
                alert("Can't Add More Than 5 !.....");
                return false;
            }



            $('#count').val(total_element + 1);
        });




        $(document).on('keydown', '.myAutocompleteClass', function () {
            var id = this.id;
            var random = this.value;
            $('#' + id).autocomplete({
                source: function (request, response) {
                    $.ajax({
                        url: "ModelNameController",
                        dataType: "json",
                        data: {action1: "getManufacturer", str: random},
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
                    $(this).val(ui.item.label);
                    return false;
                }
            });
        });

        // Remove element
        $('.container').on('click', '.remove', function () {
            var id = this.id;

            var split_id = id.split("_");
            var deleteindex = split_id[1];
            // Remove <div> with id
            $("#superchilddiv_" + deleteindex).remove();
            if ($('#count').val() > 0) {
                $('#count').val($('#count').val() - 1);

            }

        });
    });
</script>


<section>
    <div class="container-fluid page_heading sectionPadding35">
        <h1>Item Name</h1>
    </div>
</section>


<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Search Engine</h5>
        </div>
        <form name="form1" method="POST" action="ItemNameController" onsubmit="return verifySearch();" >
            <div class="row mt-3">
                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Item Type</label>
                        <input type="text" Placeholder="Item Type" name="search_item_type" id="search_item_type" value="${search_item_type}" class="form-control myInput searchInput1 w-100">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Item Name</label>
                        <input type="text" name="search_item_name" id="search_item_name" value="${search_item_name}" Placeholder="Item Name" class="form-control myInput searchInput1 w-100" >
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Item Code</label>
                        <input type="text" Placeholder="Item Code"  name="search_item_code" id="search_item_code" value="${search_item_code}" class="form-control myInput searchInput1 w-100">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Is Super Child</label>
                        <input type="text" Placeholder="Is Super Child"  name="search_super_child" id="search_super_child" value="${search_super_child}" class="form-control myInput searchInput1 w-100">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Generation</label>
                        <input type="text" Placeholder="Generation"  name="search_generation" id="search_generation" value="${search_generation}" class="form-control myInput searchInput1 w-100">
                    </div>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-md-12 text-center">
                    <input type="submit" class="btn formBtn" id="hiera" name="search_item" value="SEARCH RECORDS" onclick="setStatus(id)">
                    <!--<input type="submit" class="btn formBtn" id="org_chart" name="org_chart" value="Org Chart">-->
                </div>
            </div>
        </form>
    </div>
</section>


<c:if test="${isSelectPriv eq 'Y'}">  
    <section class="marginTop30 ">
        <div class="container organizationBox">
            <div class="headBox">
                <h5 class="">Search List</h5>
            </div>
            <table id="tree-table" class="table table-hover table-bordered" data-page-length='6'>
                <tbody>
                <th>Item Name</th>
                <th>Prefix</th>
                <th>Item Code</th>
                <th>Item Type</th>
                <!--<th>Quantity</th>-->
                <th>HSN CODE</th>
                <th>Generation</th>
                <th>Description</th>
                <th style="display:none"></th>
                <th style="display:none"></th>
                <th style="display:none"></th>
                <th style="display:none"></th>


                <c:forEach var="beanType" items="${requestScope['list']}"
                           varStatus="loopCounter">
                    <tr data-id="${beanType.item_names_id}" data-parent="${beanType.parent_item_id}" data-level="${beanType.generation}"
                        onclick="fillColumn('${beanType.item_names_id}', '${loopCounter.count }');">
                        <td id="${loopCounter.count }2" data-column="name">${beanType.item_name}</td>
                        <td id="${loopCounter.count }3">${beanType.prefix}</td>
                        <td id="${loopCounter.count }4">${beanType.item_code}</td>                                               
                        <td id="${loopCounter.count }5">${beanType.item_type}</td>                                               
                        <!--<td id="${loopCounter.count }6">${beanType.quantity}</td>--> 
                        <td id="${loopCounter.count }7">${beanType.HSNCode}</td> 
                        <td id="${loopCounter.count }8">${beanType.generation}</td>
                        <td id="${loopCounter.count }9">${beanType.description}</td> 
                        <td id="${loopCounter.count }10" style="display:none">${beanType.parent_item}</td>
                        <td id="${loopCounter.count }11" style="display:none">${beanType.superp}</td>
                        <td id="${loopCounter.count }12" style="display:none">${beanType.parent_item_code}</td>
                        <td id="${loopCounter.count }13">
                            <input type="submit" class="btn formBtn" id="org_chart" name="org_chart" value="Org Chart" onclick="getOrgChartData('${beanType.item_name}')">

     <!--<a class="btn normalBtn" target="_blank" href="ModelNameController?task1=map_model&item_names_id=${beanType.item_names_id}">Map Model</a>-->
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>          
    </section>
</c:if>

<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Data Entry</h5>
        </div>
        <form name="form2" method="POST" action="ItemNameController" onsubmit="return verify()" enctype="multipart/form-data">
            <div class="row mt-3">
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Item Type<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="hidden" id="item_name_id" name="item_name_id" value="" >
                            <input class="form-control myInput" type="hidden" id="count" name="count" value="0" >
                            <input type="hidden" id="item_image_details_id" name="item_image_details_id" value="" size="28"   />
                            <input type="hidden" name="item_authorization_id" id="item_authorization_id" value="">

                            <input class="form-control myInput" type="text" id="item_type" name="item_type" value="${item_type}" disabled >
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Item Name<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="item_name" name="item_name" size="60" value="${item_name}" disabled >
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Prefix<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="prefix" name="prefix" size="60" value="${prefix}" disabled >
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Parent Item - Code<span class="text-danger"></span></label>                            
                            <input class="form-control myInput" type="text" id="parent_item" name="parent_item"  value="${parent_item}" disabled>
                            <input class="form-control myInput" type="text" id="generation" name="generation" value="" size="45" disabled hidden>
                        </div>
                    </div>
                </div>
            </div>


            <div class="row mt-3">
                <div class="col-md-3">
                    <div class="form-group mb-1">
                        <label class="" for="email">Is super child<span class="text-danger">*</span></label>
                    </div>
                    <c:choose>
                        <c:when test="${super_child=='Y'}">
                            <div class="form-group form-check mb-0 d-inline mr-2 pl-0">
                                <label class="form-check-label">
                                    <input type="radio" id="supery" name="super" value="Y" disabled onclick="showQuantity()" checked=""> Yes
                                </label>
                            </div>
                            <div class="form-group form-check d-inline pl-0">
                                <label class="form-check-label">
                                    <input type="radio" id="supern" name="super" value="N" disabled onclick="showQuantity()"> No
                                </label>
                            </div>

                        </c:when>
                        <c:when test="${super_child=='N'}">
                            <div class="form-group form-check mb-0 d-inline mr-2 pl-0">
                                <label class="form-check-label">
                                    <input type="radio" id="supery" name="super" value="Y" disabled onclick="showQuantity()"> Yes
                                </label>
                            </div>
                            <div class="form-group form-check d-inline pl-0">
                                <label class="form-check-label">
                                    <input type="radio" id="supern" name="super" value="N" disabled onclick="showQuantity()" checked=""> No
                                </label>
                            </div>

                        </c:when>
                        <c:otherwise>
                            <div class="form-group form-check mb-0 d-inline mr-2 pl-0">
                                <label class="form-check-label">
                                    <input type="radio" id="supery" name="super" value="Y" disabled onclick="showQuantity()"> Yes
                                </label>
                            </div>
                            <div class="form-group form-check d-inline pl-0">
                                <label class="form-check-label">
                                    <input type="radio" id="supern" name="super" value="N" disabled onclick="showQuantity()"> No
                                </label>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>


                <c:choose>
                    <c:when test="${super_child=='Y'}">
                        <div class="col-md-3" id="HSNCode_div">
                            <div class="">
                                <div class="form-group">
                                    <label>HSN CODE<span class="text-danger">*</span></label>
                                    <input class="form-control myInput" type="text" id="HSNCode" name="HSNCode" value="${HSNCode}" >
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3"  id="org_office_div">
                            <div class="">
                                <div class="form-group">
                                    <label>Org Office<span class="text-danger">*</span></label>
                                    <input class="form-control myInput" type="text" id="org_office" name="org_office" 
                                           value="${org_office}" onblur="getDesignation()">
                                </div>
                            </div>
                        </div>

                        <div class="col-md-3"  id="designation_div">
                            <div class="">
                                <div class="form-group">
                                    <label>Designation<span class="text-danger">*</span></label>
                                    <div class="scrollable" id="des_list">
                                        <c:if test="${all_checked=='checked'}">
                                            <li>
                                                <input name="designation" checked="" type="checkbox" id="checkAll" onchange="checkAllCheckBoxes()" value="All"> All
                                            </li>
                                        </c:if>
                                        <c:if test="${all_checked!='checked'}">
                                            <li>
                                                <input name="designation"  type="checkbox" id="checkAll" onchange="checkAllCheckBoxes()" value="All"> All
                                            </li>
                                        </c:if>

                                        <c:forEach var="test" items="${requestScope['desig_map_listAllFinal']}"
                                                   varStatus="loopCounter">
                                            <c:set var = "des" value = "${fn:split(test,'&')}" />
                                            <li>
                                                <input name="designation"   onchange="getKeyPerson(${des[1]});" type="checkbox" id="des_check${des[1]}" value="${des[0]}"> ${des[0]}
                                                <input name="designation_id"  type="checkbox" value="${des[1]}" hidden id="designation_id${des[1]}">
                                            </li>
                                        </c:forEach>
                                        <c:forEach var="test2" items="${requestScope['all_des_list']}"
                                                   varStatus="loopCounter">
                                            <c:set var = "all_des" value = "${fn:split(test2,'&')}" />

                                            <li>
                                                <input name="designation" type="checkbox" onchange="getKeyPerson(${all_des[1]});" id="des_check${all_des[1]}" value="${all_des[0]}"> ${all_des[0]}
                                                <input name="designation_id" type="checkbox" value="${all_des[1]}" hidden id="designation_id${all_des[1]}">
                                            </li>
                                        </c:forEach>


                                        <!--                                        <li>
                                                                                    <input name="designation" type="checkbox" id="check_all" value="All"> All
                                                                                </li>-->
                                    </div>

                                </div>
                            </div>
                        </div>
                    </c:when>

                    <c:otherwise>
                        <div class="col-md-3" style="display:none" id="HSNCode_div">
                            <div class="">
                                <div class="form-group">
                                    <label>HSN CODE<span class="text-danger">*</span></label>
                                    <input class="form-control myInput" type="text" id="HSNCode" name="HSNCode" value="" >
                                </div>
                            </div>
                        </div>

                        <div class="col-md-3" style="display:none" id="org_office_div">
                            <div class="">
                                <div class="form-group">
                                    <label>Org Office<span class="text-danger">*</span></label>
                                    <input class="form-control myInput" type="text" id="org_office" name="org_office"  
                                           value="${org_office}"  onblur="getDesignation()">
                                </div>
                            </div>
                        </div>

                        <div class="col-md-3" style="display:none" id="designation_div">
                            <div class="">
                                <div class="form-group">
                                    <label>Designation<span class="text-danger">*</span></label>
                                    <div class="scrollable" id="des_list">

                                    </div>
                                </div>
                            </div>
                        </div>




                    </c:otherwise>
                </c:choose>

            </div>
            <hr>

            <div class="row" id="key_person_div" style="display:none" >


            </div>  






            <hr>

            <c:if test="${super_child=='Y'}">
                <c:forEach items = "${lst}" var = "lst" varStatus="loopCounter">   

                    <div class="row is_super_child_yes" id="superchilddiv_${loopCounter.count}">
                        <div class="col-md-2">
                            <div class="">
                                <div class="form-group">
                                    <c:if test="${loopCounter.count==1}">
                                        <label>Manufacturer Name<span class="text-danger">*</span></label> 
                                    </c:if>

                                    <input class="form-control myInput" type="hidden" id="model_id_${loopCounter.count}" name="model_id_${loopCounter.count}" value="" >
                                    <input class="form-control myInput" type="hidden" id="manufacturer_item_map_id_${loopCounter.count}" name="manufacturer_item_map_id_${loopCounter.count}" value="" >
                                    <input type="hidden" id="item_image_details_id_${loopCounter.count}" name="item_image_details_id_${loopCounter.count}" value="" size="28"   />


                                    <div class="d-flex">
                                        <input class="form-control myInput myAutocompleteClass" type="text" id="manufacturer_name_${loopCounter.count}" name="manufacturer_name_${loopCounter.count}"
                                               value="${lst}"  size="40">
                                        <a class="btn normalBtn" target="_blank" href="ManufacturerController">New</a>
                                    </div>

                                </div>
                            </div>
                        </div>

                        <div class="col-md-2">
                            <div class="">
                                <div class="form-group">
                                    <c:if test="${loopCounter.count==1}">
                                        <label>Model Name<span class="text-danger">*</span></label>    
                                    </c:if>
                                    <input class="form-control myInput" value="${lst2[loopCounter.count-1]}" type="text" id="model_${loopCounter.count}" name="model_${loopCounter.count}"
                                           size="40"  onblur="getItemTypeForModelOrPart()">
                                </div>
                            </div>
                        </div>

                        <c:choose>
                            <c:when test="${item_type=='Non-Raw Material'}">

                                <div class="col-md-2 model_no_div" id="model_no_div" >
                                    <div class="">
                                        <div class="form-group">
                                            <c:if test="${loopCounter.count==1}">
                                                <label>Model No.<span class="text-danger">*</span></label>   
                                            </c:if>

                                            <input class="form-control myInput" type="text" id="model_no_${loopCounter.count}" name="model_no_${loopCounter.count}" 
                                                   value="${lst5[loopCounter.count-1]}"  size="40"  >
                                        </div>
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="col-md-2 part_no_div" id="part_no_div" >
                                    <div class="">
                                        <div class="form-group">
                                            <c:if test="${loopCounter.count==1}">
                                                <label>Part No.<span class="text-danger">*</span></label>   
                                            </c:if>
                                            <input class="form-control myInput" type="text" id="part_no_${loopCounter.count}" name="part_no_${loopCounter.count}" 
                                                   value="${lst6[loopCounter.count-1]}"  size="40"  >
                                        </div>
                                    </div>
                                </div>
                            </c:otherwise>
                        </c:choose>




                        <div class="col-md-2">
                            <div class="">
                                <div class="form-group">
                                    <c:if test="${loopCounter.count==1}">
                                        <label>Lead Time in Days<span class="text-danger">*</span></label>           
                                    </c:if>
                                    <input class="form-control myInput" type="text" id="lead_time_${loopCounter.count}" name="lead_time_${loopCounter.count}" value="${lst3[loopCounter.count-1]}"
                                           size="40">
                                </div>
                            </div>
                        </div>


                        <div class="col-md-1">
                            <div class="">
                                <div class="form-group">
                                    <c:if test="${loopCounter.count==1}">
                                        <label>Basic Price<span class="text-danger">*</span></label>      
                                    </c:if>
                                    <input class="form-control myInput" type="text" id="basic_price_${loopCounter.count}" name="basic_price_${loopCounter.count}" value="${lst4[loopCounter.count-1]}"
                                           size="40" >
                                </div>
                            </div>
                        </div>


                        <div class="col-md-2">
                            <div class="">
                                <div class="form-group">
                                    <c:if test="${loopCounter.count==1}">
                                        <label>Select Photo<span class="text-danger"></span></label>
                                        </c:if>
                                    <input class="form-control myInput" type="file" multiple id="item_image_${loopCounter.count}" name="item_image"  size="30" value=""  onchange="readURL(this);"> 
                                </div>
                            </div>
                        </div>
                        <c:if test="${loopCounter.count==1}">
                            <a class="btn normalBtn add" style="height:35px;margin-top: 22px">+</a>
                        </c:if>
                        <c:if test="${loopCounter.count!=1}">
                            <input type="button" class="btn btn-danger remove" id="remove_${loopCounter.count}" style="height:35px;" value="X">
                        </c:if>
                    </div>
                </c:forEach>
            </c:if>

            <c:if test="${lst_size==0}">

                <div class="row is_super_child_yes" id="superchilddiv_1" style="display:none">
                    <div class="col-md-2">
                        <div class="">
                            <div class="form-group">
                                <label>Manufacturer Name<span class="text-danger">*</span></label> 

                                <input class="form-control myInput" type="hidden" id="model_id_1" name="model_id_1" value="" >
                                <input class="form-control myInput" type="hidden" id="manufacturer_item_map_id_1" name="manufacturer_item_map_id_1" value="" >
                                <input type="hidden" id="item_image_details_id_1" name="item_image_details_id_1" value="" size="28"   />

                                <div class="d-flex">
                                    <input class="form-control myInput myAutocompleteClass" type="text" id="manufacturer_name_1" name="manufacturer_name_1" value=""
                                           size="40">
                                    <a class="btn normalBtn" target="_blank" href="ManufacturerController">New</a>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="">
                            <div class="form-group">
                                <label>Model Name<span class="text-danger">*</span></label>                            
                                <input class="form-control myInput" type="text" id="model_1" name="model_1" value=""
                                       size="40"  onblur="getItemTypeForModelOrPart()">
                            </div>
                        </div>
                    </div>

                    <div class="col-md-2 model_no_div" id="model_no_div" style="display: none">
                        <div class="">
                            <div class="form-group">
                                <label>Model No.<span class="text-danger">*</span></label>                            
                                <input class="form-control myInput" type="text" id="model_no_1" name="model_no_1" value=""
                                       size="40"  >
                            </div>
                        </div>
                    </div>

                    <div class="col-md-2 part_no_div" id="part_no_div" style="display: none">
                        <div class="">
                            <div class="form-group">
                                <label>Part No.<span class="text-danger">*</span></label>                            
                                <input class="form-control myInput" type="text" id="part_no_1" name="part_no_1" value=""
                                       size="40"  >
                            </div>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="">
                            <div class="form-group">
                                <label>Lead Time in Days<span class="text-danger">*</span></label>                            
                                <input class="form-control myInput" type="text" id="lead_time_1" name="lead_time_1" value="0"
                                       size="40">
                            </div>
                        </div>
                    </div>


                    <div class="col-md-1">
                        <div class="">
                            <div class="form-group">
                                <label>Basic Price<span class="text-danger">*</span></label>                            
                                <input class="form-control myInput" type="text" id="basic_price_1" name="basic_price_1" value="0"
                                       size="40" >
                            </div>
                        </div>
                    </div>


                    <div class="col-md-2">
                        <div class="">
                            <div class="form-group">
                                <label>Select Photo<span class="text-danger"></span></label>
                                <input class="form-control myInput" type="file" multiple id="item_image_1" name="item_image"  size="30" value=""  onchange="readURL(this);"> 
                            </div>
                        </div>
                    </div>


                    <!--                <div class="col-md-11">
                                        <div class="">
                                            <div class="form-group">
                                                <label>Model Description</label>
                                                <input class="form-control" type="text" id="description" name="description" size="60" value="" disabled >
                                                <textarea class="form-control myTextArea" id="model_description_1" name="model_description_1" rows="1"></textarea>
                                            </div>
                                        </div>
                                    </div>-->
                    <a class="btn normalBtn add" style="height:35px;margin-top: 22px">+</a>
                </div>
            </c:if>
            <hr>
            <div class="row mt-12">
                <div class="col-md-12">
                    <div class="">
                        <div class="form-group">
                            <label>Description</label>
                            <!--<input class="form-control" type="text" id="description" name="description" size="60" value="" disabled >-->
                            <textarea class="form-control myTextArea" id="description" name="description" name="description" disabled ></textarea>
                        </div>
                    </div>
                </div>
            </div>
    </div>


    <hr>
    <div class="row">
        <div id="message">
            <c:if test="${not empty message}">
                <div class="col-md-12 text-center">
                    <label style="color:${msgBgColor}"><b>Result: ${message}</b></label>
                </div>
            </c:if>
        </div>
        <input type="hidden" id="clickedButton" value="">
        <div class="col-md-12 text-center">                       
            <c:if test="${myfn:isContainPrivileges2(loggedUser,'ItemNameController','update') eq 'True'}">
                <input type="button" class="btn normalBtn" name="task" id="edit" value="Edit" onclick="makeEditable(id)" disabled="">
            </c:if>

            <c:if test="${myfn:isContainPrivileges2(loggedUser,'ItemNameController','insert') eq 'True'}">
                <input type="submit" class="btn normalBtn" name="task" id="save" value="Save" onclick="setStatus(id)" disabled="">
            </c:if>

            <c:if test="${myfn:isContainPrivileges2(loggedUser,'ItemNameController','insert') eq 'True'}">
                <input type="reset" class="btn normalBtn" name="task" id="new" value="New" onclick="makeEditable(id)">
            </c:if>

            <c:if test="${myfn:isContainPrivileges2(loggedUser,'ItemNameController','delete') eq 'True'}">
                <input type="submit" class="btn normalBtn" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled="">
            </c:if>
        </div>
    </div>
</form>
</div>
</section>

<%@include file="../layout/footer.jsp" %>
