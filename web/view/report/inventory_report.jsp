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
                    url: "InventoryReportController",
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





        $("#search_manufacturer").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("search_manufacturer").value;
                $.ajax({
                    url: "InventoryReportController",
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




        $("#search_model").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("search_model").value;
                var manufacturer_name = document.getElementById("search_manufacturer").value;
                var item_code = document.getElementById("search_item_code").value;
                $.ajax({
                    url: "InventoryReportController",
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



        $("#search_item_code").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("search_item_code").value;
                var manufacturer = document.getElementById("search_manufacturer").value;
                $.ajax({
                    url: "InventoryReportController",
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




        $("#search_key_person").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("search_key_person").value;
                var org_office = document.getElementById("search_org_office").value;
                $.ajax({
                    url: "InventoryReportController",
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


    function displayMapList() {
        var queryString;
        var search_org_office = document.getElementById("search_org_office").value;
        var search_manufacturer = document.getElementById("search_manufacturer").value;
        var search_item_code = document.getElementById("search_item_code").value;
        var search_key_person = document.getElementById("search_key_person").value;
        var search_model = document.getElementById("search_model").value;
        var search_by_date = document.getElementById("search_by_date").value;
        if (search_org_office == "") {
            alert("please select office name!...");
            return false;
        }

        queryString = "task=viewPdf&search_org_office=" + search_org_office + "&search_manufacturer=" + search_manufacturer + "&search_item_code=" + search_item_code + "&search_model=" + search_model + "&search_key_person=" + search_key_person + "&search_by_date=" + search_by_date;
        //  queryString = "task=viewPdf";
        var url = "InventoryReportController?" + queryString;
        popupwin = openPopUp1(url, "Division List", 600, 1000);
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
        <h1>Inventory Report</h1>
    </div>
</section>


<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Search Engine</h5>
        </div>
        <form name="form1" method="POST" action="InventoryReportController" onsubmit="return verifySearch();" >
            <div class="row mt-3">
                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Org Office</label>
                        <input type="text" name="search_org_office" id="search_org_office" value="${search_org_office}" Placeholder="Org Office" class="form-control myInput searchInput1 w-100" >
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Manufacturer</label>
                        <input type="text" name="search_manufacturer" id="search_manufacturer" value="${search_manufacturer}" Placeholder="Manufacturer" class="form-control myInput searchInput1 w-100" >
                    </div>
                </div>

                <!--                <div class="col-md-4">
                                    <div class="form-group mb-md-0">
                                        <label>Item Name - Code</label>
                                        <input type="text" Placeholder="Item Name" name="search_item_name" id="search_item_name" value="${search_item_name}" class="form-control myInput searchInput1 w-100">
                                    </div>
                                </div>-->

                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Item Name - Code</label>
                        <input type="text" Placeholder="Item Name" name="search_item_code" id="search_item_code" value="${search_item_code}" class="form-control myInput searchInput1 w-100">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Model</label>
                        <input type="text" name="search_model" id="search_model" value="${search_model}" Placeholder="Model" class="form-control myInput searchInput1 w-100" >
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Key Person</label>
                        <input type="text" Placeholder="Key Person" name="search_key_person" id="search_key_person" value="${search_key_person}" 
                               class="form-control myInput searchInput1 w-100">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Date</label>
                        <input type="date" style="height:38px" placeholder="Search.." name="search_by_date" id="search_by_date" value="${search_by_date}"
                               class="form-control myInput searchInput1 w-100">

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

<c:if test="${isSelectPriv eq 'Y'}">  
    <section class="marginTop30">
        <div class="container organizationBox">
            <div class="headBox">
                <h5 class="">Items List</h5>
            </div>
            <div class="row mt-3 table_div">

                <table id="tree-table" class="table table-hover table-bordered" data-page-length='6'>

                    <tr>
                        <th>S.No.</th>
                        <th>Item Name</th>
                        <th style="width:80px">Item Code</th>
                        <th style="width:80px">Org Office</th>
                        <th style="width:80px">Model No.</th>
                        <th style="width:80px">Part No.</th>
                        <th style="width:80px">Key Person</th>
                        <th style="width:80px">Stock Quantity</th>
                        <th style="width:80px">Inward Quantity</th>
                        <th style="width:80px">Outward Quantity</th>
                        <th style="width:80px">Reference Document Type</th>
                        <th style="width:80px">Reference Document Id</th>
                        <th style="width:80px">Date Time</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="beanType" items="${requestScope['list']}"
                                   varStatus="loopCounter">
                            <tr data-id="${beanType.item_names_id}" data-parent="${beanType.parent_item_id}" data-level="${beanType.generation}">
                                <td>${beanType.counting}</td>
                                <td id="${loopCounter.count }2" data-column="name">${beanType.item_name}</td>
                                <td id="${loopCounter.count }4">${beanType.item_code}</td>
                                <td id="${loopCounter.count }3">${beanType.org_office}</td> 
                                <td id="${loopCounter.count }5">${beanType.model_no}</td>
                                <td id="${loopCounter.count }6">${beanType.part_no}</td>
                                <td id="${loopCounter.count }7">${beanType.key_person}</td>                                               
                                <td id="${loopCounter.count }8">${beanType.stock_quantity}</td>                                               
                                <td id="${loopCounter.count }9">${beanType.inward_quantity}</td> 
                                <td id="${loopCounter.count }10">${beanType.outward_quantity}</td>
                                <td id="${loopCounter.count }11">${beanType.reference_document_type}</td>
                                <td id="${loopCounter.count }12">${beanType.reference_document_id}</td>
                                <td id="${loopCounter.count }13">${beanType.date_time}</td> 
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>    
            </div>
        </div>
    </div>
</div>
</section>
</c:if>

<%@include file="../layout/footer.jsp" %>

