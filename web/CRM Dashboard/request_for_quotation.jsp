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
        width: 218px;
    }

</style>
<div class="content-wrapper" id="contentWrapper">
    <section class="content-header mt-2">
        <div class="container-fluid">
            <div class="row mb-2 ">
                <div class="col-sm-6">
                    <div class="d-flex">
                        <div>
                            <a href="QuotationController" class="btn btn-primary myNewLinkBtn">All Quotations</a>
                            <input type="hidden" name="role" id="role" value="${role}">
                        </div>
                        <!--                        <div>
                                                    <a href="#" class="btn btn-primary myNewLinkBtn fontFourteen">Save</a>
                                                    <a href="#" class="btn btn-primary myOtherBtn fontFourteen">Discard</a>
                                                </div>-->

                        <!--                        <div class="col-md-7">
                                                    <div class="d-flex">
                                                        <div>
                                                            <a href="QuotationController?task=new_quotation&type=vendor" class="btn btn-primary myNewLinkBtn ">Quotation by Vendor</a>
                                                        </div>
                                                        <div>
                                                            <a href="QuotationController?task=new_quotation" class="btn btn-primary myNewLinkBtn bg-white  ">Quotation by Product</a>
                                                        </div>
                                                    </div>
                                                </div>-->
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
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <c:choose>
                            <c:when test="${role=='Incharge' || role=='Super Admin'}">
                                <li class="breadcrumb-item"><a href="dashboard">Dashboard</a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="breadcrumb-item"><a href="CRMDashboardController">Dashboard</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                        <li class="breadcrumb-item active">Quotation Request</li>
                    </ol>
                </div>
            </div>
        </div><!-- /.container-fluid -->
    </section>

    <section class="content">
        <form action="QuotationController" method="post" >
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


                            <div class="container-fluid mt-2">
                                <div class="row">
                                    <div class="col-md-2">
                                        <div class="form-group">
                                            <label for="inputName" class="mb-1">RFQ No.<sup class="text-danger">*</sup></label>
                                            <input type="text" name="rfq_no" id="rfq_no" class="form-control rounded-0" value="${rfq}" required="">
                                            <input type="hidden" name="type" id="type" value="vendor">
                                        </div>
                                    </div>
                                    <c:if test="${role=='Admin' || role=='Super Admin'}">
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <label for="inputName" class="mb-1">Org Office<sup class="text-danger">*</sup></label>
                                                <input type="text" name="org_office" id="org_office" required="" placeholder="Select Org Office" class="form-control rounded-0 mr-2" >
                                            </div>
                                        </div>
                                    </c:if>

                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label for="inputName" 
                                                   class="mb-1">Vendor:<sup class="text-danger">*</sup></label>
                                            <input type="text" id="vendor_name" name="vendor_name"  class="form-control rounded-0" 
                                                   placeholder="Select Vendor" required="">
                                        </div>
                                    </div>
                                    <div class="col-md-2">
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
                                                        <!--                                                        <input type="checkbox" class="form-check-input1 mt-1" value=""> All-->
                                                        <input type="checkbox" onchange="checkAll()" name="chk[]" id="checkall" />All
                                                    </label>
                                                </div>
                                            </th>
                                            <th class="fontFourteen">Product</th>
                                            <th class="fontFourteen">Model</th>
                                            <th class="fontFourteen">Scheduled Date</th>
                                            <th class="fontFourteen">Quantity</th>
                                            <th class="fontFourteen">Unit</th>
                                            <!--                                            <th class="fontFourteen">Unit Price <small>(<i class="fas fa-rupee-sign curruncyIcon"></i>)</small></th>
                                                                                        <th class="fontFourteen">Subtotal <small>(<i class="fas fa-rupee-sign curruncyIcon"></i>)</small></th>-->
                                            <!--<th class="fontFourteen">Action</th>-->
                                        </tr>
                                    </thead>
                                    <tbody>                               
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
                                    <!--                                    <div class="col-md-6">
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
                                                                        </div>-->
                                </div>
                            </div>
                            <div class="container-fluid text-right mt-5">
                                <div>
                                    <input type="submit" class="btn btn-primary myOtherSuccessBtn  fontFourteen" value="Save" name="task">
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
            var role = $('#role').val();
            if (role != 'Incharge')
            {
                var org_office = $('#org_office').val();
                if (org_office == '') {
                    alert("Please Select Org Office");
                    return false;
                }
            }


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


    $(document).ready(function () {

        var list;
        var item_name = [];
        var model = [];
        var stock_quantity = [];
        var min_quantity = [];
        var vendor = [];
        var item_names_id = [];
        var model_id = [];
        var color = [];

        $("#vendor_name").change(function () {
            var vendor_name = $('#vendor_name').val();
            var org_office = $('#org_office').val();
            $.ajax({
                url: "QuotationController",
                dataType: "json",
                data: {
                    action1: "getData",
                    vendor_name: vendor_name,
                    org_office: org_office
                },
                success: function (data) {
                    console.log(data);
                    $('#mytable1 tbody').empty();
//                    displayPoduct();
                    list = data.list;
                    //for loop
                    var j = 1;
                    for (var i = 0; i < list.length; i++) {
                        item_name[i] = list[i]["item_name"];
                        model[i] = list[i]["model"];
                        stock_quantity[i] = list[i]["stock_quantity"];
                        min_quantity[i] = list[i]["min_quantity"];
                        vendor[i] = list[i]["vendor"];
                        item_names_id[i] = list[i]["item_names_id"];
                        model_id[i] = list[i]["model_id"];
                        color[i] = list[i]["color"];

//                        $('#mytable1 tbody').append('<tr style="background-color:' + color[i] + '"><td class="fontFourteen"><div class="form-check"><label class="form-check-label1 mb-0"><input type="checkbox" name="checkitem" id="checkitem' + j + '" class="form-check-input1 mt-1" value="' + j + '"></label></div></td><td class="fontFourteen">' + item_name[i] + '<input type="hidden" name="item_names_id' + j + '" id="item_names_id' + j + '" value="' + item_names_id[i] + '"></td><td class="fontFourteen">' + model[i] + '<input type="hidden" name="model_id' + j + '" id="model_id' + j + '" value="' + model_id[i] + '"></td><td class="fontTwelve"><input type="text" name="scheduled_date' + j + '" id="scheduled_date' + j + '" class="datepicker2" style="width: 110px"></td><td class="fontFourteen"><input type="number" name="qty' + j + '" id="qty' + j + '"  value="" style="width: 85px"></td><td class="fontFourteen">Pcs</td><td class="fontFourteen">0.00</td><td class="fontFourteen">0.00</td></tr>');
                        $('#mytable1 tbody').append('<tr style="background-color:' + color[i] + '"><td class="fontFourteen"><div class="form-check"><label class="form-check-label1 mb-0"><input type="checkbox" name="checkitem" id="checkitem' + j + '" class="form-check-input1 mt-1" value="' + j + '"  onchange="makeEditable(' + j + ')"></label></div></td><td class="fontFourteen">' + item_name[i] + '<input type="hidden" name="item_names_id' + j + '" id="item_names_id' + j + '" value="' + item_names_id[i] + '"></td><td class="fontFourteen">' + model[i] + '<input type="hidden" name="model_id' + j + '" id="model_id' + j + '" value="' + model_id[i] + '"></td><td class="fontTwelve"><input type="text" disabled="" name="scheduled_date' + j + '" id="scheduled_date' + j + '" class="datepicker2" style="width: 110px"></td><td class="fontFourteen"><input type="number" disabled="" name="qty' + j + '" id="qty' + j + '"  value="" style="width: 85px"></td><td class="fontFourteen">Pcs</td></tr>');
                        $('.datepicker2').datepicker();
                        j++;
                    }
                }
            });
        });
    });



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

    function makeEditable(count) {
        var item_check = $('#checkitem' + count).val();
        if ($('#checkitem' + count).is(':checked')) {
            $('#qty' + count).attr('disabled', false);
            $('#scheduled_date' + count).attr('disabled', false);
//            $('#vendor' + count).attr('disabled', false);
        } else {
            $('#qty' + count).attr('disabled', true);
            $('#scheduled_date' + count).attr('disabled', true);
//            $('#vendor' + count).attr('disabled', true);
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