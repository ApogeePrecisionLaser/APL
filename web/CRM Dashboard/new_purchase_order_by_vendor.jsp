<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>
<style>
    .quantityWrap input{
        max-width: 80px;
        height: 25px;
    }
</style>

<div class="content-wrapper position-relative pt-3" id="contentWrapper">
    <section class="content">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-7">
                    <div class="d-flex">
                        <div>
                            <a href="PurchaseOrdersController?task=new order" class="btn btn-primary myNewLinkBtn  bg-white ">Order by Product</a>
                        </div>
                        <div>
                            <a href="PurchaseOrdersController?task=new order&type=vendor" class="btn btn-primary myNewLinkBtn ">Order by Vendor</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-5">
                    <div class="d-flex">                        
                        <div class="cartCountWrap d-flex ml-auto">
                            <a href="PurchaseOrdersController?task=cart&type=${type}" >
                                <div><i class="fas fa-cart-plus"></i></div>
                                <div class="counting">${cart_count}</div>
                            </a>
                        </div>
                    </div>                    
                </div>
                <div class="">
                    <div class="alert alert-success alert-dismissible myAlertBox mb-0" style="display:none"  id="msg_success">
                        <button type="button" class="close" data-dismiss="alert">&times;</button>
                        <strong>Success!</strong> New order create successfully.
                    </div>
                    <div class="alert alert-danger alert-dismissible myAlertBox mb-0" style="display:none" id="msg_danger">
                        <button type="button" class="close" data-dismiss="alert">&times;</button>
                        <strong>Oops!</strong> Something went wrong.
                    </div>
                </div>
            </div>

            <div class="mt-3 d-flex" style="max-width: 400px;">
                <c:if test="${role=='Admin' || role=='Super Admin'}">
                    <input type="text" name="org_office" id="org_office" placeholder="Select Org Office" class="form-control rounded-0 mr-2" >
                </c:if>
                <input type="text" name="vendor_name" id="vendor_name" placeholder="Select Vendor" class="form-control rounded-0">
            </div>        
            <div class="row mt-3" id="allProductListWrap">
                <div class="col-md-12">
                    <div class="card card-primary card-outline">            
                        <div class="card-body">                
                            <div>
                                <div class="table-responsive" >
                                    <table class="table mainTable" id="mytable1">
                                        <thead>
                                            <tr>
                                                <th>Sr. No.</th>
                                                <th>Product</th>
                                                <th>Model</th>
                                                <th>Min Qty</th>
                                                <th>Available Qty</th>
                                                <th>Quantity</th>
                                                <!--<th>Unit</th>-->                                    
                                                <th>Rate (<small><i class="fas fa-rupee-sign curruncyIcon"></i></small>)</th>
                                                <th>Price (<small><i class="fas fa-rupee-sign curruncyIcon"></i></small>)</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <!--                                            <tr>
                                                                                            <td class="fontFourteen"></td>
                                                                                            <td class="fontFourteen">CB-200</td>
                                                                                            <td class="fontFourteen">10</td>
                                                                                            <td class="fontFourteen">8</td>
                                                                                            <td class="fontFourteen">
                                                                                                <div class="quantityWrap">
                                                                                                    <input type="number" class="form-control rounded-0 pl-1 pr-0" name="">
                                                                                                </div>
                                                                                            </td>
                                                                                            <td class="fontFourteen">Pcs</td>
                                                                                            <td class="fontFourteen">?1800</td>
                                                                                            <td class="fontFourteen">?1,80,000</td>
                                                                                            <td class="fontFourteen d-flex">
                                                                                                <div>
                                                                                                    <a href="purchaseManagementByProduct2ndPage.php" class="btn actionEdit" title="Add to Cart"> Add to Cart</a>
                                                                                                </div>
                                                                                            </td> 
                                                                                        </tr>-->
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </section>
</div>




<%@include file="/CRM Dashboard/CRM_footer.jsp" %>

<script>

    $("#allProductListWrap").hide();


    function displayPoduct() {
        $("#allProductListWrap").show();
    }
    var row = 1;
    $(document).on("click", "#add-row", function () {
        var new_row = '<tr id="row' + row + '"><td class="pl-0"><input name="pr_name" id="pr_name' + row + '" type="text" class="form-control rounded-0 myAutocompleteClass" /><input name="inventory_id' + row + '" id="inventory_id' + row + '" type="hidden" class="form-control rounded-0 " /></td><td class="pl-0"><input name="pr_modelName' + row + '" id="pr_modelName' + row + '" type="text" onblur="getData(this.value, this.id)" class="form-control rounded-0 myAutocompleteClass" /></td><td class="pl-0"><input name="pr_model' + row + '" id="pr_model' + row + '" type="text" class="form-control rounded-0" /></td><td class="pl-0"><input name="pr_stock_qty' + row + '" id="pr_stock_qty' + row + '" type="text" class="form-control rounded-0" disabled=""/></td><td class="pl-0"><input name="pr_qty' + row + '" id="pr_qty' + row + '" type="text" class="form-control rounded-0" required=""/></td><td class="pl-0"><button class="delete-row btn btn-sm rounded-0 btn-danger"><i class="far fa-trash-alt"></i></button></td></tr>';
        $('#test-body').append(new_row);
        row++;
        return false;
    });
    $(document).on("click", ".delete-row", function () {
        if (row > 1) {
            $(this).closest('tr').remove();
            row--;
        }
        return false;
    });
//    $(document).ready(function () {
//        $(document).on('keydown', '.myAutocompleteClass', function () {
//            var id = this.id;
//            var manufacturer = "";
//            var type;
//            var vendor_name;
//            var item_name;
//            vendor_name = $('#vendor_name').val();
//            if (vendor_name == '') {
//                alert("please select vendor first..");
//                return false;
//            }
//            if (id.match("^pr_name")) {
//                type = "Product";
//            }
////            else if (id.match("^pr_vendor")) {
////                item_name = $('#pr_name' + id.substring(9)).val();
////                type = "Vendor";
////            }
//            else if (id.match("^pr_modelName")) {
//                type = "Model";
//                item_name = $('#pr_name' + id.substring(12)).val();
//            }
//
//            var random = this.value;
//            $('#' + id).autocomplete({
//                source: function (request, response) {
//                    $.ajax({
//                        url: "PurchaseOrdersController",
//                        dataType: "json",
//                        data: {
//                            action1: "getParameter",
//                            str: random,
//                            type: type,
//                            item_name: item_name,
//                            vendor_name: vendor_name
//                        },
//                        success: function (data) {
//                            console.log(data);
//                            response(data.list);
//                        },
//                        error: function (error) {
//                            console.log(error.responseText);
//                            response(error.responseText);
//                        }
//                    });
//                },
//                select: function (events, ui) {
//                    console.log(ui);
//                    $(this).val(ui.item.label);
//                    if (id.match("^pr_name")) {
//                        $('#pr_modelName' + id.substring(7)).val("");
//                        $('#pr_model' + id.substring(7)).val("");
//                        $('#pr_stock_qty' + id.substring(7)).val("");
//                    }
//                    return false;
//                }
//            });
//        });
//    });





    $("#vendor_name").autocomplete({
        source: function (request, response) {
            var random = $('#vendor_name').val();
            var org_office = $('#org_office').val();
            if (org_office == '') {
                alert("Please Select Org Office");
                return  false;
            }
            $.ajax({
                url: "PurchaseOrdersController",
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
            for (var j = 0; j < row; j++) {
                $('#row' + j).remove();
            }
            var new_row = '<tr id="row0"><td class="pl-0"><input name="pr_name" id="pr_name0" type="text" class="form-control rounded-0 myAutocompleteClass" /><input name="inventory_id0" id="inventory_id0" type="hidden" class="form-control rounded-0 " /></td><td class="pl-0"><input name="pr_modelName0" id="pr_modelName0" type="text" onblur="getData(this.value, this.id)" class="form-control rounded-0 myAutocompleteClass" /></td><td class="pl-0"><input name="pr_model0" id="pr_model0" type="text" class="form-control rounded-0" /></td><td class="pl-0"><input name="pr_stock_qty0" id="pr_stock_qty0" type="text" class="form-control rounded-0" disabled=""/></td><td class="pl-0"><input name="pr_qty0" id="pr_qty0" type="text" class="form-control rounded-0" required=""/></td><td class="pl-0"><button class="delete-row btn btn-sm rounded-0 btn-danger"><i class="far fa-trash-alt"></i></button></td></tr>';
            $('#test-body').append(new_row);
            row = 1;
//            $('#vendor_name').attr("disabled", "true");
            return false;
        }
    });

    $("#org_office").autocomplete({
        source: function (request, response) {
            $('#vendor_name').val("");
            var random = $('#org_office').val();
            $.ajax({
                url: "PurchaseOrdersController",
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
                url: "PurchaseOrdersController",
                dataType: "json",
                data: {
                    action1: "getData",
                    vendor_name: vendor_name,
                    org_office: org_office
                },
                success: function (data) {
                    console.log(data);
                    $('#mytable1 tbody').empty();
                    displayPoduct();
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

                        $('#mytable1 tbody').append('<tr style="background-color:' + color[i] + '"><td>' + j + '</td><td>' + item_name[i] + '</td><td>' + model[i] + '</td><td>' + min_quantity[i] + '</td><td>' + stock_quantity[i] + '</td><td><div class="quantityWrap"><input type="number" class="form-control rounded-0 pl-1 pr-0" name="qty" id="qty' + j + '" onblur="calculatePrice(' + j + ')"></div></td><td><div class="quantityWrap"><input type="text" class="form-control rounded-0 pl-1 pr-0" name="rate" id="rate' + j + '" onblur="calculatePrice(' + j + ')"></div></td><td id="price' + j + '"></td><td><div><input type="hidden" name="vendor" id="vendor' + item_names_id[i] + '" value="' + vendor[i] + '"><input type="hidden" name="model" id="model' + item_names_id[i] + '" value="' + model[i] + '"><a onclick="addTocart(' + model_id[i] + ', ' + item_names_id[i] + ',' + j + ')" class="btn actionEdit" title="Add to Cart"> Add to Cart</a></div></td></tr>');
                        j++;
                    }
                }
            });
        });


    });
//    function getData(model_name, id) {
//        $.ajax({
//            url: "PurchaseOrdersController",
//            dataType: "json",
//            data: {
//                action1: "getData",
//                model_name: model_name
//            },
//            success: function (data) {
//                console.log(data);
//                var all_data = data.list[0];
//                const myArray = all_data.split("&");
//                $('#pr_model' + id.substring(12)).val(myArray[0]);
//                $('#pr_vendor' + id.substring(12)).val(myArray[1]);
//                $('#pr_stock_qty' + id.substring(12)).val(myArray[2]);
//                $('#inventory_id' + id.substring(12)).val(myArray[3]);
//            }
//        });
//    }

    function addTocart(model_id, item_names_id, count) {
        var qty = $('#qty' + count).val();
        var rate = $('#rate' + count).val();
        var model = $('#model' + item_names_id).val();
        var vendor_name = $('#vendor' + item_names_id).val();
        var org_office_name = $('#org_office').val();
        if (qty == '') {
            alert("Please Enter Quantity!.");
            return  false;
        }
        if (qty <= 0) {
            alert("Please Enter valid qty!.");
            return  false;
        }
        if (rate <= 0) {
            alert("Please Enter valid rate!.");
            return  false;
        }
        if (rate == '') {
            alert("Please Enter Price!.");
            return  false;
        }
        $.ajax({
            url: "PurchaseOrdersController",
            dataType: "json",
            data: {task: "AddToCart", model_id: model_id, model: model, item_names_id: item_names_id, vendor_name: vendor_name, qty: qty, rate: rate, org_office_name: org_office_name},
            success: function (data) {
                console.log(data);
                if (data.success_msg != '') {
                    $('.counting').text(data.list);
                    $('#msg_success').text(data.success_msg);
                    $('#msg_success').show();
                    $('#msg_danger').hide();
                    setTimeout(function () {
                        $('#msg_success').fadeOut('fast');
                    }, 2000);

                } else {
                    $('.myAlertBox').hide();
                    $('#msg_danger').text(data.error_msg);
                    $('#msg_danger').show();
                    $('#msg_success').hide();
                    setTimeout(function () {
                        $('#msg_danger').fadeOut('fast');
                    }, 2000);

                }
//                window.location.reload();

            }
        });
    }
    function calculatePrice(count) {
        var qty = $('#qty' + count).val();
        var rate = $('#rate' + count).val();
//        if (qty <= 0) {
//            alert("Please Enter valid qty!.");
//            return  false;
//        }
//        if (rate <= 0) {
//            alert("Please Enter valid rate!.");
//            return  false;
//        }
        $('#price' + count).text(parseInt(qty * rate));
    }

</script>