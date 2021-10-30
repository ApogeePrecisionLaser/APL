<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@include file="../layout/header.jsp" %>--%>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.25/css/jquery.dataTables.min.css">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.7.1/css/buttons.dataTables.min.css">
<link rel="stylesheet" type="text/css" href="assets/css/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="assets/css/style.css">

<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
<script src="assets/JS/jquery-ui.js"></script>
<script src="assets/JS/moment.min.js"></script>
<script src="https://cdn.datatables.net/1.10.25/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/buttons/1.7.1/js/dataTables.buttons.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/pdfmake.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/vfs_fonts.js"></script>
<script src="https://cdn.datatables.net/buttons/1.7.1/js/buttons.html5.min.js"></script>
<script src="https://cdn.datatables.net/buttons/1.7.1/js/buttons.print.min.js"></script>

<!--<link rel="stylesheet" type="text/css" href="collapsetable/css/bootstrap.min.css">-->
<!--<script src="collapsetable/js/javascript.js"></script>-->
<script src="https://cdn.datatables.net/buttons/1.7.1/js/buttons.colVis.min.js"></script>
<style>
    .selected_row {
        font-weight: bolder;
        color: blue;
        border: 3px solid black;
    }
    table.dataTable {      
        border-collapse: collapse;
    }
    .not_in_stock{
        background-color: #d587c8;
        color:white;
    }
    .approved{
        background-color: #2e6da4;
        color:white; 
    }
    .delivered{
        background-color: #5cb85c;
        color:white; 
    }
    .denied{
        background-color: #dc3545;
        color:white; 
    }
    .pending{
        background-color: #ffc107;
        color:white; 
    }
    .delivery_challan_generated{
        background-color: #df7d35;
        color:white; 
    }
</style>
<script>

    $(document).ready(function () {
        var delivery_indent_table_id = $('#delivery_indent_table_id').val();
        var delivery_message = $('#delivery_message').val();
        var delivery_status = $('#delivery_status').val();
        if (delivery_status != "") {
            opener.document.getElementById("delivery_indent_table_id").value = delivery_indent_table_id;
            opener.document.getElementById("delivery_indent_status").value = delivery_status;
            opener.document.getElementById("delivery_indent_message").value = delivery_message;
            opener.location.reload();
            window.close();
        }


        $(".datepicker").datepicker({minDate: new Date()});

        $(document).on('keydown', '.myAutocompleteClass', function () {
            var id = this.id;
            var type;
            if (id.match("^status")) {
                type = "Status";
            } else if (id.match("^purpose")) {
                type = "Purpose";
            }

            var random = this.value;
            $('#' + id).autocomplete({
                source: function (request, response) {
                    $.ajax({
                        url: "DeliverItemController",
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
        }
        );
    });


    function makeEditable(id) {
        document.getElementById("delivery_challan_no").disabled = false;
        document.getElementById("delivery_challan_date").disabled = false;
        document.getElementById("indent_no").disabled = false;
        document.getElementById("description").disabled = false;
        document.getElementById("save").disabled = false;
    }
    function updatestatus(id) {

        var orderno = document.getElementById("indent_no").value;
        var url = "CheckOrderInventoryController?task=GetOrderUpdatestatus&orderno=" + orderno;
        window.open(url, "_self");
        window.close();
    }
    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
        return window.open(url, window_name, window_features);
    }

    function displayReportPrint() {

        var counter = document.getElementById("counter").value;
        var delivered_qty = document.getElementById("delivered_qty").value;
        var delivery_challan_date = document.getElementById("delivery_challan_date").value;
        var order_no = document.getElementById("indent_no").value;
        var delivery_challan_no = document.getElementById("delivery_challan_no").value;
        var item_name = document.getElementById("item_name").value;
        
        var queryString = "task=generateDeliveryReport&counter=" + counter + "&delivered_qty=" + delivered_qty+ "&delivery_challan_date=" + delivery_challan_date+ "&order_no=" + order_no+ "&delivery_challan_no=" + delivery_challan_no+ "&item_name=" + item_name;
        var url = "OrderController?" + queryString;
        popupwin = openPopUp(url, "Mounting Type Map Details", 500, 1000);

    }

</script>


<section>
    <div class="container-fluid page_heading sectionPadding35">
        <h1>Order Delivery Challan</h1>
    </div>
</section>



<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Data Entry</h5>
        </div>
        <form name="form2" method="POST" action="DeliverOrderItemController" onsubmit="return verify()" enctype="multipart/form-data" >
            <input type="hidden" name="delivery_status" id="delivery_status" value="${delivery_status}">
            <input type="hidden" name="delivery_indent_table_id" id="delivery_indent_table_id"  value="${delivery_indent_table_id}">
            <input type="hidden" name="delivery_message" id="delivery_message" value="${delivery_message}">

            <div class="row mt-3">
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Delivery Challan No.<span class="text-danger">*</span></label>

                            <input class="form-control myInput" type="text" id="delivery_challan_no" name="delivery_challan_no" value="${autogenerate_delivery_challan_no}" disabled onclick="alert('You cannot Change AutoGenerate Indent No!............')" >
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Delivery Challan Date<span class="text-danger">*</span></label>
                            <input class="form-control myInput datepicker" disabled="" type="text" id="delivery_challan_date" name="delivery_challan_date" value="${delivery_challan_date}">
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Order No.<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="indent_no" name="indent_no" value="${indent_nos}" disabled onclick="alert('You cannot Change this person!............')">
                        </div>
                    </div>
                </div>

                <div class="row mt-3 myTable">
                    <div class="col-md-12">
                        <div class="table-responsive verticleScroll">
                            <table class="table table-striped table-bordered" id="mytable" style="width:100%">
                                <thead>
                                    <tr>
                                        <th>S.No.</th>
                                        <th>Item Name</th>
                                        <th>Required Qty</th>
                                        <th>Approved Qty</th>
                                        <th>Stock Qty</th>
                                        <th>Deliver Qty</th>
                                        <th>Status</th>
                                        <th>Purpose</th>
                                        <th>Expected Date</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="beanType" items="${requestScope['deliverey_challan_items_list']}"
                                               varStatus="loopCounter">

                                        <tr >

                                            <td>${loopCounter.count }
                                                <input type="hidden" name="counter" id="counter" value="${loopCounter.count }">
                                                <input type="hidden" name="indent_table_id" id="indent_table_id" value="${beanType.indent_table_id}">
                                                <input type="hidden" name="indent_item_id" id="indent_item_id" value="${beanType.indent_item_id}">
                                                <input type="hidden" name="item_name${beanType.indent_item_id}" id="item_name" value="${beanType.item_name}">
                                                <input type="hidden" name="requested_by" id="requested_by" value="${beanType.requested_by}">
                                                <input type="hidden" name="requested_to" id="requested_to" value="${beanType.requested_to}">
                                            </td>


                                            <td id="${loopCounter.count }">${beanType.item_name}</td>
                                            <td id="${loopCounter.count }">${beanType.required_qty}</td> 
                                            <td id="${loopCounter.count }">${beanType.approved_qty}</td> 
                                            <td id="${loopCounter.count }">${beanType.stock_qty}</td> 
                                            <td id="${loopCounter.count }"><input type="text" name="delivered_qty${beanType.indent_item_id}" id="delivered_qty" value="${beanType.delivered_qty}"></td> 
                                            <td id="${loopCounter.count }"><input type="text" name="item_status${beanType.indent_item_id}" id="item_status" value="${beanType.status}"></td> 
                                            <td id="${loopCounter.count }">${beanType.purpose}</td> 
                                            <td id="${loopCounter.count }">${beanType.expected_date_time}</td> 
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div
                    </div>
                </div>

                <div class="col-md-12">
                    <div class="">
                        <div class="form-group">
                            <label>Description</label>
                            <textarea class="form-control myTextArea" id="description" name="description" name="description" disabled>${description}</textarea>
                        </div>
                    </div>
                </div>
            </div>      
            <hr>
            <div class="row">
                <div id="message">
                    <c:if test="${not empty message}">
                        <div class="col-md-12 text-center">
                            <label id="message_level" style="color:${msgBgColor}"><b>Result: ${message}</b></label>
                            <input type="text" name="last_indent_table_id" id="last_indent_table_id" value="${last_indent_table_id}">
                        </div>
                    </c:if>
                </div>
                <input type="hidden" id="clickedButton" value="">
                <div class="col-md-12 text-center">    
                    <input type="file" value="Upload" name="up" id="up">
<!--                     <input type="button" class="btn normalBtn" name="task" id="sendtodealer" value="Send Challan To Dealer" onclick="updatestatus('${last_indent_table_id}');">-->
                    <input type="button" class="btn normalBtn" name="task" id="save" value="Print Challan" onclick="displayReportPrint(id);">
                    <input type="submit" class="btn normalBtn" name="task" id="save" value="Upload Challan & Deliver Items" onclick="makeEditable(id);">
                </div>
            </div>

    </div>
</form>
</section>



