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
</style>
<script>

    $(document).ready(function () {
        var item_name = [];
        var items = "";
        $.ajax({
            url: "IndentController",
            dataType: "json",
            data: {action1: "getItemsList"},
            success: function (data) {

                //  $('#items_div').show();
                $('#item_list').empty();
                console.log(data);
                items = data.item_name;
                for (var i = 0; i < items.length; i++) {
                    item_name[i] = items[i]["item_name"];
                    $('#item_list').append('<li class="mb-1" id="items"><div class="row"><div class="col-lg-2"><input type="checkbox" value="' + item_name[i] + '" name="item_name" id="item_name' + i + '" onclick=enableFields(' + i + ')> ' + item_name[i] + ' </div><div class="col-lg-2"><input style="width:100px;font-size:13px" type="text" name="required_qty' + i + '" id="required_qty' + i + '" placeholder="Qty" disabled></div><div class="col-lg-2"><input style="font-size:13px" type="date" name="expected_date_time' + i + '" id="expected_date_time' + i + '" placeholder="Expected Date Time" disabled></div><div class="col-lg-2"><input style="font-size:13px" class="myAutocompleteClass" type="text" name="purpose' + i + '" id="purpose' + i + '" placeholder="Purpose" disabled></div></div></li>');
                }
            }
        });
    });

    $(document).ready(function () {
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
                        url: "IndentController",
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

    });


    function enableFields(i) {
        document.getElementById("required_qty" + i).disabled = false;
        document.getElementById("expected_date_time" + i).disabled = false;
        document.getElementById("purpose" + i).disabled = false;
        //document.getElementById("status" + i).disabled = false;
    }




</script>



<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Items List</h5>
        </div>
        <form name="form2" method="POST" action="IndentController" onsubmit="return verify()" >
            <div class="row mt-3">

                <div class="col-md-12"  id="items_div" style="border:1px solid black" >
                    <div class="form-group" style="overflow: scroll;height: 400px" >
                        <input type="hidden" name="indent_item_id" id="indent_item_id">

                        <input type="hidden" name="indent_table_id" id="indent_table_id" value="">
                        <input type="hidden" name="indent_no" id="indent_no" value="${indent_no}">
                        <input type="hidden" name="requested_by" id="requested_by" value="${requested_by}">
                        <input type="hidden" name="requested_to" id="requested_to" value="${requested_to}">
                        <input type="hidden" name="description" id="description" value="${description}">

                        <label for="email"> <b>Items:</b></label>

                        <ul id="" style="border-bottom:1px solid black" >
                            <div class="row">
                                <div class="col-lg-2">
                                    <label for="item_name"> <b><u>Item Name</u></b></label>
                                </div>
                                <div class="col-lg-2">
                                    <label for="required_qty"> <b><u>Required Qty</u></b></label>
                                </div>  

                                <div class="col-lg-2">
                                    <label for="expected_date_time"> <b><u>Expected Date Time</u></b></label>
                                </div>

                                <!--                                <div class="col-lg-1">
                                                                    <label for="stock_qty"> <b><u>Stock Qty</u></b></label>
                                                                </div>
                                
                                                                <div class="col-lg-1">
                                                                    <label for="delivered_qty"> <b><u>Delivered Qty</u></b></label>
                                                                </div>
                                
                                                                <div class="col-lg-1">
                                                                    <label for="status"> <b><u>Status</u></b></label>
                                                                </div>-->

                                <div class="col-lg-2">
                                    <label for="purpose"> <b><u>Purpose</u></b></label>
                                </div>
                            </div>
                        </ul>
                        <ul id="item_list" >  
                        </ul>
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
                    <input type="submit" class="btn normalBtn" name="task" id="save" value="Send Request" >
                </div>
            </div>
        </form>
    </div>
</section>

<%--<%@include file="../layout/footer.jsp" %>--%>

