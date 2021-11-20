<%@taglib prefix="myfn" uri="http://MyCustomTagFunctions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    .table_div{
        height:300px;
        overflow-y: scroll;
        position:relative;
    }
    .table_div{
        height:300px;
        overflow-y: scroll;
        position:relative;
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



        $("#search_org_office").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("search_org_office").value;
                $.ajax({
                    url: "OfficeItemMapReportController",
                    dataType: "json",
                    data: {action1: "getOrgOffice", str: random},
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
                $('#search_org_office').val(ui.item.label);
                return false;
            }
        });


        $("#org_office").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("org_office").value;
                $.ajax({
                    url: "OfficeItemMapReportController",
                    dataType: "json",
                    data: {action1: "getOrgOffice", str: random},
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


        $("#search_manufacturer").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("search_manufacturer").value;
                $.ajax({
                    url: "OfficeItemMapReportController",
                    dataType: "json",
                    data: {action1: "getManufacturer", str: random},
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
                $('#search_manufacturer').val(ui.item.label);
                return false;
            }
        });



        $("#manufacturer_name").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("manufacturer_name").value;
                $.ajax({
                    url: "OfficeItemMapReportController",
                    dataType: "json",
                    data: {action1: "getManufacturer", str: random},
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
                $('#manufacturer_name').val(ui.item.label);
                return false;
            }
        });

        $("#item_code").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("item_code").value;
                var manufacturer = document.getElementById("manufacturer_name").value;
                var org_office = document.getElementById("org_office").value;
                $.ajax({
                    url: "OfficeItemMapReportController",
                    dataType: "json",
                    data: {action1: "getItemCode", str: random, manufacturer: manufacturer},
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
                $('#item_code').val(ui.item.label);
                return false;
            }
        });


        $("#search_model").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("search_model").value;
                var manufacturer_name = document.getElementById("search_manufacturer").value;
                var item_code = document.getElementById("search_item_code").value;
                $.ajax({
                    url: "OfficeItemMapReportController",
                    dataType: "json",
                    data: {action1: "getModelName", str: random, manufacturer_name: manufacturer_name, item_code: item_code},
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
                $('#search_model').val(ui.item.label);
                return false;
            }
        });


        $("#model_name").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("model_name").value;
                var manufacturer_name = document.getElementById("manufacturer_name").value;
                var item_code = document.getElementById("item_code").value;
                $.ajax({
                    url: "OfficeItemMapReportController",
                    dataType: "json",
                    data: {action1: "getModelName", str: random, manufacturer_name: manufacturer_name, item_code: item_code},
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
                $('#model_name').val(ui.item.label);
                return false;
            }
        });

        $("#search_item_code").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("search_item_code").value;
                var manufacturer = document.getElementById("search_manufacturer").value;
                $.ajax({
                    url: "OfficeItemMapReportController",
                    dataType: "json",
                    data: {action1: "getItemCode", str: random, manufacturer: manufacturer},
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


        $("#key_person").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("key_person").value;
                var org_office = document.getElementById("org_office").value;

                $.ajax({
                    url: "OfficeItemMapReportController",
                    dataType: "json",
                    data: {action1: "getKeyPerson", str: random, str2: org_office},
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
                $('#key_person').val(ui.item.label);
                return false;
            }
        });

        $("#search_key_person").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("search_key_person").value;
                var org_office = document.getElementById("search_org_office").value;
                $.ajax({
                    url: "OfficeItemMapReportController",
                    dataType: "json",
                    data: {action1: "getKeyPerson", str: random, str2: org_office},
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
                $('#search_key_person').val(ui.item.label);
                return false;
            }
        });


    });


    function makeEditable(id) {
        //alert("ewfc");
        document.getElementById("item_code").disabled = false;
        document.getElementById("org_office").disabled = false;
        document.getElementById("manufacturer_name").disabled = false;
        document.getElementById("model_name").disabled = false;
        document.getElementById("key_person").disabled = false;
        document.getElementById("min_quantity").disabled = false;
        document.getElementById("description").disabled = false;
        document.getElementById("daily_req").disabled = false;
        document.getElementById("opening_balance").disabled = false;
        document.getElementById("date_time").disabled = false;
        document.getElementById("save").disabled = false;
        if (id === 'new') {
            $("#message").html("");
            document.getElementById("inventory_basic_id").value = "";
            document.getElementById("org_office").focus();
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
            var item_code = document.getElementById("item_code").value;
            var min_quantity = document.getElementById("min_quantity").value;
            var daily_req = document.getElementById("daily_req").value;
            var opening_balance = document.getElementById("opening_balance").value;
            var org_office = document.getElementById("org_office").value;
            var manufacturer_name = document.getElementById("manufacturer_name").value;
            var model_name = document.getElementById("model_name").value;
            var key_person = document.getElementById("key_person").value;

            if (myLeftTrim(org_office).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Org Office is required...</b></label></div>');
                document.getElementById("org_office").focus();
                return false;
            }
            if (myLeftTrim(manufacturer_name).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Manufacturer Name is required...</b></label></div>');
                document.getElementById("manufacturer_name").focus();
                return false;
            }
            if (myLeftTrim(model_name).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Model Name is required...</b></label></div>');
                document.getElementById("model_name").focus();
                return false;
            }
            if (myLeftTrim(key_person).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Key Person is required...</b></label></div>');
                document.getElementById("key_person").focus();
                return false;
            }
            if (myLeftTrim(item_code).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Item Code is required...</b></label></div>');
                document.getElementById("item_code").focus();
                return false;
            }
            if (myLeftTrim(min_quantity).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Minimum Quantity is required...</b></label></div>');
                document.getElementById("min_quantity").focus();
                return false;
            }
            if (myLeftTrim(daily_req).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Daily Requirement is required...</b></label></div>');
                document.getElementById("daily_req").focus();
                return false;
            }
            if (myLeftTrim(opening_balance).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Opening Balance is required...</b></label></div>');
                document.getElementById("opening_balance").focus();
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



    function openPopUp(window_name, popup_height, popup_width, id) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";

        return window.open(url, window_name, window_features);
    }
    if (!document.all) {
        document.captureEvents(Event.CLICK);
    }
    document.onclick = function () {
        if (popupwin !== null && !popupwin.closed) {
            popupwin.focus();
        }
    }

    function fillColumn(id, inventory_id, count) {
        $('#inventory_basic_id').val(id);
        $('#inventory_id').val(inventory_id);
        $('#org_office').val($("#" + count + '2').html());
        $('#manufacturer_name').val($("#" + count + '3').html());
//        $('#item_name').val($("#" + count + '4').html());
        $('#item_code').val($("#" + count + '4').html() + " - " + $("#" + count + '5').html());
        $('#model_name').val($("#" + count + '6').html());
        $('#key_person').val($("#" + count + '7').html());
        $('#min_quantity').val($("#" + count + '8').html());
        $('#daily_req').val($("#" + count + '9').html());
        $('#opening_balance').val($("#" + count + '10').html());
        $('#date_time').val($("#" + count + '11').html());
        $('#description').val($("#" + count + '12').html());
        // document.getElementById("edit").disabled = false;
        //document.getElementById("delete").disabled = false;
    }



    function calculateMinQty() {
        var model_name = $('#model_name').val();
        $.ajax({
            url: "OfficeItemMapReportController",
            dataType: "json",
            data: {action1: "getLeadTime", model_name: model_name},
            success: function (data) {
                console.log(data.list[0]);
                var lead_time = data.list[0];
                var daily_req = $('#daily_req').val();
                if (lead_time != 0) {
                    $('#min_quantity').val(lead_time * daily_req);
                }
            }
        });
    }

    function displayMapList() {
        var queryString;
        var search_org_office = document.getElementById("search_org_office").value;
        var search_manufacturer = document.getElementById("search_manufacturer").value;
        var search_item_code = document.getElementById("search_item_code").value;
        var search_model = document.getElementById("search_model").value;
        if (search_org_office == "") {
            alert("please select office name!...");
            return false;
        }

        queryString = "task=viewPdf&search_org_office=" + search_org_office + "&search_manufacturer=" + search_manufacturer + "&search_item_code=" + search_item_code + "&search_model=" + search_model;
        //  queryString = "task=viewPdf";
        var url = "OfficeItemMapReportController?" + queryString;
        popupwin = openPopUp1(url, "Division List", 600, 900);
    }

    function openPopUp1(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";

        return window.open(url, window_name, window_features);
    }
</script>


<section>
    <div class="container-fluid page_heading sectionPadding35">
        <h1>tEST Report</h1>
    </div>
</section>


<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Search Engine</h5>
        </div>
        <form name="form1" method="POST" action="OfficeItemMapReportController" onsubmit="return verifySearch();" >
            <div class="row mt-3">
                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Org Office</label>
                        <input type="text" name="search_org_office" id="search_org_office" value="" Placeholder="Org Office" class="form-control myInput searchInput1 w-100" >
                    </div>
                </div>

            </div>
            <hr>
            <div class="row">
                <div class="col-md-12 text-center">
                    <input type="submit" class="btn formBtn" id="hiera" name="search_item" value="SEARCH RECORDS" onclick="setStatus(id)">
                    <input type="button" class="btn formBtn" id="viewPdf" name="viewPdf" value="View Report" onclick="displayMapList()">
                </div>
            </div>
        </form>
    </div>
</section>


<%@include file="../layout/footer.jsp" %>

