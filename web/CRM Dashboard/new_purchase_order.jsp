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
                            <a href="PurchaseOrdersController?task=new order" class="btn btn-primary myNewLinkBtn ">Order by Product</a>
                        </div>
                        <div>
                            <a href="PurchaseOrdersController?task=new order&type=vendor" class="btn btn-primary myNewLinkBtn bg-white ">Order by Vendor</a>
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

            <c:if test="${role=='Admin' || role=='Super Admin'}">
                <div class="mt-3" style="width: 400px">
                    <form action="PurchaseOrdersController" method="post" class="d-flex">
                        <input type="text" name="org_office" id="org_office" placeholder="Select Org Office" class="form-control rounded-0" value="${org_office}">

                        <input type="hidden" name="type" id="type" value="${type}">
                        <input type="hidden" name="org_office" id="org_office_name" value="${org_office}">
                        <input type="submit" name="task" value="Search" class="btn btn-primary myNewLinkBtn">
                    </form>
                </div> 
            </c:if>
        </div>
        <div class="container-fluid">
            <div class="row mt-3" >
                <div class="col-md-12">
                    <div class="card card-primary card-outline">            
                        <div class="card-body">
                            <div>
                                <div class="table-responsive" >
                                    <table class="table mainTable" id="mytable">
                                        <thead>
                                            <tr>
                                                <th>Sr. No.</th>
                                                <th>Product</th>
                                                <th>Model</th>
                                                    <c:if test="${role=='Admin' || role=='Super Admin'}">
                                                    <th>Org Office</th>
                                                    </c:if>
                                                <th>Min Qty</th>
                                                <th>Available Qty</th>
                                                <th>Vendor </th>
                                                <th>Quantity</th>
                                                <!--<th>Unit</th>-->                                    
                                                <th>Rate (<small><i class="fas fa-rupee-sign curruncyIcon"></i></small>)</th>
                                                <th>Price (<small><i class="fas fa-rupee-sign curruncyIcon"></i></small>)</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="beanType" items="${requestScope['list']}"
                                                       varStatus="loopCounter">
                                                <tr style="background-color: ${beanType.color}">
                                                    <td class="fontFourteen">${loopCounter.count}</td>
                                                    <td class="fontFourteen">${beanType.item_name}</td>
                                                    <td class="fontFourteen">${beanType.model}</td>
                                                    <c:if test="${role=='Admin' || role=='Super Admin'}">
                                                        <td>${beanType.org_office_name}</td>
                                                    </c:if>
                                                    <td class="fontFourteen">${beanType.min_qty}</td>
                                                    <td class="fontFourteen">${beanType.stock_qty}</td>
                                                    <c:choose>
                                                        <c:when test="${beanType.vendor==''}">
                                                            <td class="fontFourteen d-flex">
                                                                <input type="text" name="vendor" class="vendors" placeholder="Select" id="vendor${beanType.item_names_id}" style="width: 100px;height: 25px">
                                                                <a onclick="mapToVendor('${beanType.item_names_id}')"  style="height: 25px" class="btn myNewLinkBtn btn-primary fontFourteen px-1" title="Map With Vendor"><i class="far fa-share-square"></i></a>
                                                            </td>
                                                        </c:when>

                                                        <c:otherwise>
                                                            <td class="fontFourteen">${beanType.vendor}</td>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <td class="fontFourteen">
                                                        <div class="quantityWrap">
                                                            <input type="number" class="form-control rounded-0 pl-1 pr-0" onblur="calculatePrice(${loopCounter.count})"
                                                                   name="qty" id="qty${loopCounter.count}">
                                                        </div>
                                                    </td>
                                                    <!--<td class="fontFourteen">Pcs</td>-->
                                                    <td class="fontFourteen">
                                                        <div class="quantityWrap">
                                                            <input type="text" class="form-control rounded-0 pl-1 pr-0" onblur="calculatePrice(${loopCounter.count})" 
                                                                   name="rate" id="rate${loopCounter.count}">
                                                        </div>
                                                    </td>
                                                    <td id="price${loopCounter.count}"></td>

                                                    <!--<td class="fontFourteen">0</td>-->
                                                    <td class="fontFourteen d-flex">
                                                        <div>
                                                            <c:choose>
                                                                <c:when test="${org_office=='' && (role=='Admin' || role=='Super Admin')}">
                                                                    <a style="pointer-events: none;cursor: default;" onclick="addTocart('${beanType.model_id}', '${beanType.model}', '${beanType.item_names_id}', '${beanType.vendor}')" class="btn actionEdit" title="Add to Cart"> Add to Cart</a>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <a onclick="addTocart('${beanType.model_id}', '${beanType.model}', '${beanType.item_names_id}', '${beanType.vendor}', ${loopCounter.count})" class="btn actionEdit" title="Add to Cart"> Add to Cart</a>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </div>
                                                    </td> 
                                                </tr>

                                            </c:forEach>
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
                    if (id.match("^pr_name")) {
                        $('#pr_modelName' + id.substring(7)).val("");
                        $('#pr_model' + id.substring(7)).val("");
                        $('#pr_stock_qty' + id.substring(7)).val("");
                    }
                    return false;
                }
            });
        });
    });
    $("#vendor_name").autocomplete({
        source: function (request, response) {
            var random = $('#vendor_name').val();
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
    function getData(model_name, id) {
        $.ajax({
            url: "PurchaseOrdersController",
            dataType: "json",
            data: {
                action1: "getData",
                model_name: model_name
            },
            success: function (data) {
                console.log(data);
                var all_data = data.list[0];
                const myArray = all_data.split("&");
                $('#pr_model' + id.substring(12)).val(myArray[0]);
                $('#pr_vendor' + id.substring(12)).val(myArray[1]);
                $('#pr_stock_qty' + id.substring(12)).val(myArray[2]);
                $('#inventory_id' + id.substring(12)).val(myArray[3]);
            }
        });
    }

    function mapToVendor(item_names_id) {
        var vendor = $('#vendor' + item_names_id).val();
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


    function addTocart(model_id, model, item_names_id, vendor_name, count) {
        var qty = $('#qty' + count).val();
        var rate = $('#rate' + count).val();
        var org_office_name = $('#org_office_name').val();
        if (vendor_name == '') {
            alert("Please Map item to vendor first!...");
            return false;
        }
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


//    $(document).ready(function () {
//        $("#org_office").change(function () {
//            window.open("PurchaseOrdersController?task=new order&org_office=" + this.value);
//            $('#table_data').show();
//        });
//    });
</script>