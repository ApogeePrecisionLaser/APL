<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>
<style>
    .tableFixHead {
        table-layout: fixed;
        border-collapse: collapse;
    }
    .tableFixHead tbody {
        display: block;
        width: 100%;
        overflow: auto;
        height: 200px;
    }
    .tableFixHead thead tr {
        display: block;
    }
    .tableFixHead th,
    .tableFixHead  td {
        padding: 5px 10px;
        width: 200px;
    }

</style>
<div class="content-wrapper" id="contentWrapper">
    <section class="content-header mt-2">
        <div class="container-fluid">
            <div class="row mb-2 ">
                <div class="col-sm-6">
                    <div class="d-flex">
                        <!--                        <div>
                                                    <a href="#" class="btn btn-primary myNewLinkBtn fontFourteen">Save</a>
                                                    <a href="#" class="btn btn-primary myOtherBtn fontFourteen">Discard</a>
                                                </div>-->

                        <div class="col-md-7">
                            <div class="d-flex">
                                <div>
                                    <a href="QuotationController?task=new_quotation&type=vendor" class="btn btn-primary myNewLinkBtn bg-white ">Quotation by Vendor</a>
                                </div>
                                <div>
                                    <a href="QuotationController?task=new_quotation" class="btn btn-primary myNewLinkBtn   ">Quotation by Product</a>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-5"></div>
                        <c:if test="${not empty message}">
                            <c:if test="${msgBgColor=='green'}">
                                <div class="alert alert-success alert-dismissible myAlertBox"  id="msg" >
                                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                                    <strong>Success!</strong> ${message}

                                </div>
                            </c:if>
                            <c:if test="${msgBgColor=='red'}">
                                <div class="alert alert-danger alert-dismissible myAlertBox" id="msg" >
                                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                                    <strong>OOps!</strong> ${message}
                                </div>
                            </c:if>
                        </c:if>


                    </div>

                </div>


            </div>
        </div><!-- /.container-fluid -->
    </section>

    <section class="content">
        <form action="QuotationController" method="post">
            <div class="row">
                <div class="col-md-12">
                    <div class="card card-primary card-outline">            
                        <div class="card-body pt-2">
                            <!--                            <div class="container-fluid">
                                                            <div class="row">
                                                                <div class="col-md-6">
                                                                    <div>
                                                                        <a href="#" class="btn btn-success  fontFourteen">Send RFQ by Email</a>
                                                                        <a href="#" class="btn btn-success  fontFourteen">Print RFQ</a>
                                                                        <a href="#" class="btn btn-primary myOtherBtn fontFourteen">Confirm Order</a>
                                                                        <a href="#" class="btn btn-primary myOtherBtn fontFourteen">Cancel</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>-->


                            <c:if test="${role=='Admin' || role=='Super Admin'}">
                                <div class="container-fluid">
                                    <div class="row ">
                                        <div class="mt-3 d-flex">
                                            <form action="QuotationController" method="post" class="d-flex">
                                                <input type="text" name="org_office" id="org_office" placeholder="Select Org Office" class="form-control rounded-0" value="${org_office}">

                                                <input type="hidden" name="type" id="type" value="${type}">
                                                <input type="hidden" name="org_office" id="org_office_name" value="${org_office}">
                                                <input type="submit" name="task" value="Search" class="btn btn-primary myNewLinkBtn">
                                            </form>
                                        </div> 
                                    </div>
                                </div>
                            </c:if>
                            <hr>

                            <div class="container-fluid mt-2">
                                <div class="row">
                                    <div class="col-md-2">
                                        <div class="form-group">
                                            <label for="inputName" class="mb-1">RFQ No.<sup class="text-danger">*</sup></label>
                                            <input type="text" name="rfq_no" id="rfq_no" class="form-control rounded-0" value="${rfq}">
                                        </div>
                                    </div>

                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label for="inputName" class="mb-1">Date:<sup class="text-danger">*</sup></label>
                                            <input type="text" class="form-control rounded-0 datepicker1" name="current_date" id="current_date" 
                                                   value="">
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="table-responsive mailbox-messages tableScrollWrap mt-5" >
                                <table class="table table-hover table-striped tableFixHead" id="mytable1">
                                    <thead>
                                        <tr>
                                            <th class="pl-2">
                                                <div class="form-check">
                                                    <label class="form-check-label1 mb-0 d-flex">
                                                        <!--<input type="checkbox" class="form-check-input1 mt-1" value=""> All-->

                                                        <input type="checkbox" onchange="checkAll()" name="chk[]" id="checkall" />All
                                                    </label>
                                                </div>
                                            </th>
                                            <c:if test="${role=='Admin' || role=='Super Admin'}">
                                                <th>Org Office</th>
                                                </c:if>
                                            <th class="fontFourteen">Product</th>
                                            <th class="fontFourteen">Model</th>
                                            <th class="fontFourteen">Vendor</th>
                                            <th class="fontFourteen">Scheduled Date</th>
                                            <th class="fontFourteen">Quantity</th>
                                            <th class="fontFourteen">Unit</th>
                                            <th class="fontFourteen">Unit Price <small>(<i class="fas fa-rupee-sign curruncyIcon"></i>)</small></th>
                                            <th class="fontFourteen">Subtotal <small>(<i class="fas fa-rupee-sign curruncyIcon"></i>)</small></th>
                                            <!--<th class="fontFourteen">Action</th>-->
                                        </tr>
                                    </thead>
                                    <tbody>                               
                                        <c:forEach var="beanType" items="${requestScope['list']}"
                                                   varStatus="loopCounter">
                                            <tr style="background-color: ${beanType.color}">
                                                <td class="fontFourteen">
                                                    <div class="form-check">
                                                        <label class="form-check-label1 mb-0">
                                                            <input type="checkbox" name="checkitem" id="checkitem${loopCounter.count}"
                                                                   class="form-check-input1 mt-1" value="${loopCounter.count}" 
                                                                   onchange="makeEditable(${loopCounter.count})">
                                                        </label>
                                                    </div>
                                                </td> 

                                                <c:if test="${role=='Admin' || role=='Super Admin'}">
                                                    <td>${beanType.org_office_name}</td>
                                                </c:if>

                                                <td class="fontFourteen">${beanType.item_name}
                                                    <input type="hidden" name="item_names_id${loopCounter.count}" id="item_names_id${loopCounter.count}" 
                                                           value="${beanType.item_names_id}">
                                                </td>

                                                <td class="fontFourteen">${beanType.model}
                                                    <input type="hidden" name="model_id${loopCounter.count}" id="model_id${loopCounter.count}" 
                                                           value="${beanType.model_id}">
                                                </td>

                                                <c:choose>
                                                    <c:when test="${beanType.vendor==''}">
                                                        <td class="fontFourteen d-flex">
                                                            <input type="text" name="vendor" class="vendors" disabled="" 
                                                                   placeholder="Select" id="vendor${loopCounter.count}" style="width: 100px;height: 25px">
                                                            <a onclick="mapToVendor(${loopCounter.count})"  style="height: 25px" class="btn myNewLinkBtn btn-primary fontFourteen px-1" title="Map With Vendor"><i class="far fa-share-square"></i></a>
                                                        </td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td class="fontFourteen">${beanType.vendor}</td>
                                                    </c:otherwise>
                                                </c:choose>

                                                <td class="fontTwelve">                   
                                                    <input type="text" name="scheduled_date${loopCounter.count}" disabled=" "id="scheduled_date${loopCounter.count}"  
                                                           class="datepicker2" style="width: 110px">
                                                </td>

                                                <td class="fontFourteen">
                                                    <input type="number" name="qty${loopCounter.count}" disabled="" id="qty${loopCounter.count}" 
                                                           value="" style="width: 85px"></td>

                                                <td class="fontFourteen">Pcs</td>

                                                <td class="fontFourteen">0.00</td>

                                                <td class="fontFourteen">0.00</td>
                                                <!--                                                <td class="text-center">
                                                                                                    <a onclick="document.getElementById('id01').style.display = 'block'" href="#" class="btn far fa-trash-alt actionDelete" title="Delete Message"></a>
                                                                                                </td>-->
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>

                            <div class="container-fluid">
                                <hr>
                            </div>
                            <div class="container-fluid">
                                <div class="row">
                                    <div class="col-md-6">
                                        <textarea class="form-control rounded-0" name="description"
                                                  id="description" placeholder="Define your terms and conditions..."></textarea>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="text-right" style="text-align: right;">
                                            <table class="text-right ml-auto" style="text-align: right;">
                                                <tr>
                                                    <td class="fontFourteen"><strong>Untaxed Amount: </strong></td>
                                                    <td class="fontFourteen"><small>(<i class="fas fa-rupee-sign curruncyIcon"></i>)</small> 0.00</td>
                                                </tr>
                                                <tr style="border-bottom: 1px solid #000;">
                                                    <td class="fontFourteen"><strong>Taxed Amount: </strong></td>
                                                    <td class="fontFourteen"> <small>(<i class="fas fa-rupee-sign curruncyIcon"></i>)</small> 0.00</td>
                                                </tr>
                                                <tr>
                                                    <td class="fontFourteen">Total: </td>
                                                    <td class="fontFourteen"><strong><small>(<i class="fas fa-rupee-sign curruncyIcon"></i>)</small> 0.00</strong></td>
                                                </tr>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="container-fluid text-right mt-5">
                                <div>
                                    <input type="submit" class="btn btn-success  fontFourteen" value="Save" name="task">
                                    <!--<input type="submit" class="btn btn-primary myOtherBtn fontFourteen" value="Edit" name="task">-->
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </section>
</div>


<%@include file="/CRM Dashboard/CRM_footer.jsp" %>

<script>
    $("#org_office").autocomplete({
        source: function (request, response) {
            $('#vendor_name').val("");
            var random = $('#org_office').val();
            $.ajax({
                url: "QuotationController",
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
    $("#vendor_name").autocomplete({
        source: function (request, response) {
            var random = $('#vendor_name').val();
            $.ajax({
                url: "QuotationController",
                dataType: "json",
                data: {action1: "getVendor", str: random},
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
            $('#vendor_name').val(ui.item.label);
            return false;
        }
    });
//    $(document).ready(function () {
//
//        var list;
//        var item_name = [];
//        var model = [];
//        var stock_quantity = [];
//        var min_quantity = [];
//        var vendor = [];
//        var item_names_id = [];
//        var model_id = [];
//        var color = [];
//
//        $("#vendor_name").change(function () {
//            var vendor_name = $('#vendor_name').val();
//            var org_office = $('#org_office').val();
//            $.ajax({
//                url: "QuotationController",
//                dataType: "json",
//                data: {
//                    action1: "getData",
//                    vendor_name: vendor_name,
//                    org_office: org_office
//                },
//                success: function (data) {
//                    console.log(data);
//                    $('#mytable1 tbody').empty();
////                    displayPoduct();
//                    list = data.list;
//                    //for loop
//                    var j = 1;
//                    for (var i = 0; i < list.length; i++) {
//                        item_name[i] = list[i]["item_name"];
//                        model[i] = list[i]["model"];
//                        stock_quantity[i] = list[i]["stock_quantity"];
//                        min_quantity[i] = list[i]["min_quantity"];
//                        vendor[i] = list[i]["vendor"];
//                        item_names_id[i] = list[i]["item_names_id"];
//                        model_id[i] = list[i]["model_id"];
//                        color[i] = list[i]["color"];
//
//                        $('#mytable1 tbody').append('<tr style="background-color:' + color[i] + '"><td class="fontFourteen"><div class="form-check"><label class="form-check-label1 mb-0"><input type="checkbox" name="checkitem" id="checkitem' + j + '" class="form-check-input1 mt-1" value="' + j + '"></label></div></td><td class="fontFourteen">' + item_name[i] + '<input type="hidden" name="item_names_id' + j + '" id="item_names_id' + j + '" value="' + item_names_id[i] + '"></td><td class="fontFourteen">' + model[i] + '<input type="hidden" name="model_id' + j + '" id="model_id' + j + '" value="' + model_id[i] + '"></td><td class="fontTwelve"><input type="text" name="scheduled_date' + j + '" id="scheduled_date' + j + '" class="datepicker2" style="width: 110px"></td><td class="fontFourteen"><input type="number" name="qty' + j + '" id="qty' + j + '"  value="" style="width: 85px"></td><td class="fontFourteen">Pcs</td><td class="fontFourteen">0.00</td><td class="fontFourteen">0.00</td></tr>');
//                        $('.datepicker2').datepicker();
//
//                        j++;
//                    }
//                }
//            });
//        });
//    });


    $(document).ready(function () {
        var date = new Date();
        var today = new Date(date.getFullYear(), date.getMonth(), date.getDate());
        var end = new Date(date.getFullYear(), date.getMonth(), date.getDate());
        $('.datepicker1').datepicker({
            format: "DD/MM/YYYY",
            todayHighlight: true,
            startDate: today,
            endDate: end,
            autoclose: true
        });
        $('.datepicker1').datepicker('setDate', today);
        $('.datepicker2').datepicker();
    });

    function mapToVendor(count) {
        var vendor = $('#vendor' + count).val();
        var item_names_id = $('#item_names_id' + count).val();
        alert(vendor);
        alert(item_names_id);
        if (vendor != '') {
            $.ajax({
                url: "PurchaseOrdersController",
                dataType: "json",
                data: {task: "mapToVendor", item_names_id: item_names_id, vendor: vendor},
                success: function (data) {
                    if (data.message != '') {
                        $('#msg').text(data.message);
                        $('.alert-success').show();
                        setTimeout(function () {
                            $('#msg').fadeOut('fast');
                        }, 2000);
//                        window.location.reload();
                    } else {
                        $('.alert-success').hide();
                    }
                }
            });
        } else {
            $('.alert-danger').show();
            $('.alert-danger').html('Please Select a dealer!..');
            setTimeout(function () {
                $('.alert-danger').fadeOut('fast');
            }, 2000);
            return false;
        }
    }

    $(document).ready(function () {
        $(document).on('keydown', '.vendors', function () {
            var id = this.id;
            var random = this.value;
            var type = "vendor";
            $('#' + id).autocomplete({
                source: function (request, response) {
                    $.ajax({
                        url: "PurchaseOrdersController",
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
    function makeEditable(count) {
        var item_check = $('#checkitem' + count).val();
        if ($('#checkitem' + count).is(':checked')) {
            $('#qty' + count).attr('disabled', false);
            $('#scheduled_date' + count).attr('disabled', false);
            $('#vendor' + count).attr('disabled', false);
        } else {
            $('#qty' + count).attr('disabled', true);
            $('#scheduled_date' + count).attr('disabled', true);
            $('#vendor' + count).attr('disabled', true);
        }
    }

    function checkAll() {
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
</script>