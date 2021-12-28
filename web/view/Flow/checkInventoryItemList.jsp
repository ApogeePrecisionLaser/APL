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

<link rel="stylesheet" type="text/css" href="collapsetable/css/bootstrap.min.css">
<script src="collapsetable/js/javascript.js"></script>
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
</style>
<script>

    $(document).ready(function () {
        var status = $('#indent_status').val();
        if (status == "Delivery Challan Generated") {
            $('.btn-success').val("Delivery Challan Generated");
            $('.btn-success').attr('disabled', 'true');
        } else if ((status != "Approved") && ((status != "Delivery Challan Generated"))) {
            $('.btn-success').attr('disabled', 'true');
//            $('.btn-danger').attr('disabled', 'true');
        }


        var disable_denied_btn = $('#disable_denied_btn').val();
        if (disable_denied_btn == "disable")
        {
            $('.btn-danger').show();
        }

        var disable_less_stock_btn = $('#disable_less_stock_btn').val();
        if (disable_less_stock_btn == "disable")
        {
            $('.btn-warning').show();
        }

        var final_indent_table_id = $('#final_indent_table_id').val();
        var final_message = $('#final_message').val();
        var final_status = $('#final_status').val();
        var final_task = $('#final_task').val();        
           
            if (final_status != "") {
                opener.document.getElementById("stock_indent_final_indent_table_id").value = final_indent_table_id;
                opener.document.getElementById("stock_indent_final_status").value = final_status;
                opener.document.getElementById("stock_indent_final_message").value = final_message;
                opener.document.getElementById("final_task").value = final_task;
                opener.location.reload();
                window.close();
            }
       
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
                        url: "CheckInventoryController",
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
                    $(this).val(ui.item.label);
                    return false;
                }
            });
        });
    });

</script>


<section>
    <div class="container-fluid page_heading sectionPadding35">
        <h1>Generate Delivery Challan</h1>
    </div>
</section>

<section class="marginTop30 ">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Indent Items</h5>
        </div>
        <div class="row mt-3 myTable">
            <div class="col-md-12">
                <div class="table-responsive verticleScroll">
                    <form action="CheckInventoryController" method="POST">
                        <input type="hidden" name="indent_status" id="indent_status" value="${indent_status}">
                        <input type="hidden" name="final_status" id="final_status" value="${final_status}">
                        <input type="hidden" name="final_indent_table_id" id="final_indent_table_id"  value="${final_indent_table_id}">
                        <input type="hidden" name="final_message" id="final_message" value="${final_message}">
                        <input type="hidden" name="final_task" id="final_task" value="${task}">


                        <table class="table table-striped table-bordered" id="mytable" style="width:100%" data-page-length='6'>
                            <thead>
                                <tr>
                                    <th>S.No.</th>
                                    <th style="display:none"></th>
                                    <!--<th>Indent No.</th>-->
                                    <th>Item Name</th>
                                    <th>Required Qty</th>
                                    <th>Approved Qty</th>
                                    <th>Stock Qty</th>
                                    <th>Deliver Qty</th>
                                    <th>Status</th>
                                    <th>Purpose</th>
                                    <th>Expected Date</th>
                                    <!--<th></th>-->
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="beanType" items="${requestScope['indent_items_list']}"
                                           varStatus="loopCounter">

                                    <tr onclick="openPopUpForItems(${beanType.indent_item_id})">
                                        <td>${loopCounter.count }

                                            <input type="hidden" name="indent_table_id" id="indent_table_id" value="${beanType.indent_table_id}">
                                            <input type="hidden" name="requested_by" id="requested_by" value="${beanType.requested_by}">
                                            <input type="hidden" name="requested_to" id="requested_to" value="${beanType.requested_to}">
                                            <input type="hidden" name="item_name${beanType.indent_item_id}" id="item_name" value="${beanType.item_name}">
                                            <input type="hidden" name="status" id="status" value="${beanType.status}">

                                        </td>
                                        <td style="display:none"><input type="hidden" name="indent_item_id" id="indent_item_id" value="${beanType.indent_item_id}"></td>

                                        <td id="${loopCounter.count }">${beanType.item_name}</td>
                                        <td id="${loopCounter.count }">${beanType.required_qty}</td>

                                        <td id="${loopCounter.count }">${beanType.approved_qty}</td>

                                        <td>${beanType.stock_qty}</td>
                                        <c:if test="${beanType.stock_qty == 0}">
                                    <input type="hidden" id="disable_less_stock_btn" name="disable_less_stock_btn" value="disable">

                                </c:if>
                                <c:if test="${beanType.approved_qty == 0}">
                                    <input type="hidden" id="disable_denied_btn" name="disable_denied_btn" value="disable">
                                </c:if>

                                <c:choose>

                                    <c:when test="${beanType.item_status =='Delivered'}">
                                        <td><input type="text" name="deliver_qty${beanType.indent_item_id}" id="deliver_qty"  value="${beanType.delivered_qty}"></td>
                                        <td id="${loopCounter.count }"><input type="text" name="item_status${beanType.indent_item_id}" placeholder="select status" class="myAutocompleteClass" id="status${beanType.indent_item_id}" value="${beanType.item_status}">
                                        </td>
                                    </c:when>
                                    <c:when test="${(beanType.item_status == 'Approved')  && (beanType.stock_qty <= beanType.required_qty)}" >
                                        <td><input type="text" name="deliver_qty${beanType.indent_item_id}" id="deliver_qty"  value="${beanType.stock_qty}"></td>
                                        <td id="${loopCounter.count }"><input type="text" name="item_status${beanType.indent_item_id}" placeholder="select status" class="myAutocompleteClass" id="status${beanType.indent_item_id}" value="Less Stock">
                                        </td>
                                    </c:when>

                                    <c:when test="${(beanType.item_status == 'Approved')  && (beanType.approved_qty !=0)}" >
                                        <td><input type="text" name="deliver_qty${beanType.indent_item_id}" id="deliver_qty"  value="${beanType.approved_qty}"></td>
                                        <td id="${loopCounter.count }"><input type="text" name="item_status${beanType.indent_item_id}" placeholder="select status" class="myAutocompleteClass" id="status${beanType.indent_item_id}" value="">
                                        </td>
                                    </c:when>
                                    <c:when test="${beanType.item_status =='Denied'}">
                                        <td><input type="text" name="deliver_qty${beanType.indent_item_id}" id="deliver_qty"  value="0"></td>
                                        <td id="${loopCounter.count }"><input type="text"  name="item_status${beanType.indent_item_id}" placeholder="select status" class="myAutocompleteClass" id="status${beanType.indent_item_id}" value="${beanType.item_status}">
                                        </td>
                                    </c:when>
                                    <c:when test="${beanType.item_status =='Delivery Challan Generated'}">
                                        <td><input type="text" name="deliver_qty${beanType.indent_item_id}" id="deliver_qty" disabled="" value="${beanType.delivered_qty}"></td>
                                        <td id="${loopCounter.count }"><input type="text" disabled="" name="item_status${beanType.indent_item_id}" placeholder="select status" class="myAutocompleteClass" id="status${beanType.indent_item_id}" value="${beanType.item_status}">
                                        </td>
                                    </c:when>


                                    <c:when test="${(beanType.item_status == 'Approved')  && (beanType.approved_qty ==0)}">
                                        <td><input type="text" name="deliver_qty${beanType.indent_item_id}" id="deliver_qty" value="${beanType.approved_qty}"></td>
                                        <td id="${loopCounter.count }"><input type="text"  name="item_status${beanType.indent_item_id}" placeholder="select status" class="myAutocompleteClass" id="status${beanType.indent_item_id}" value="Denied">
                                        </td>
                                    </c:when>


                                    <c:otherwise>
                                        <td><input type="text" name="deliver_qty${beanType.indent_item_id}" id="deliver_qty" value="0"></td>
                                        <td id="${loopCounter.count }"><input type="text"  name="item_status${beanType.indent_item_id}" placeholder="select status" class="myAutocompleteClass" id="status${beanType.indent_item_id}" value="${beanType.item_status}">
                                        </td>
                                    </c:otherwise>
                                </c:choose>

                                <td id="${loopCounter.count }">${beanType.purpose}</td>
                                <td id="${loopCounter.count }">${beanType.expected_date_time}</td> 
                                </tr>

                            </c:forEach>
                            </tbody>
                        </table>
                        <input type="submit" class="btn btn-success" id="approve_denied" name="task" value="Generate Delivery Challan" style="margin-left:40%">
                        <input type="submit" style="display: none" class="btn btn-danger" value="Denied" id="approve_denied" name="task">
                        <input type="submit" style="display: none" class="btn btn-warning" value="Less Stock" id="approve_denied" name="task">
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>



