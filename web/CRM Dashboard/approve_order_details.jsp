<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>
<div class="content-wrapper" id="contentWrapper">
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <div class="d-flex">
                        <div class="mr-2 backBtnWrap">
                            <a href="ApproveOrdersController" class="btn btnBack "><i class="fas fa-chevron-circle-left"></i></a>
                        </div>
                        <div>
                            <h6 class="mt-2">Order ID : ${order_no}</h6>
                        </div>
                    </div>  
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="CRMDashboardController">Dashboard</a></li>
                        <li class="breadcrumb-item active">Order Detail</li>
                    </ol>
                </div>
            </div>
        </div>
    </section>
    <section class="content mt-0">
        <div class="container-fluid">              
            <div class="row mt-0">
                <div class="col-md-12">
                    <div class="card card-primary card-outline">
                        <div class="card-body">
                            <form action="ApproveOrdersController" method="POST">
                                <div class="table-responsive tableScrollWrap noWrapTable" >
                                    <table class="table table-striped1 mainTable" id="mytablel" >
                                        <thead>
                                            <tr>
                                                <th>S.No.</th>
                                                <th>Image</th>
                                                <th>Name</th>
                                                <!--<th>Category</th>-->
                                                <th>Req Qty</th>
                                                <!--<th>Stock Qty</th>-->
                                                <!--<th>Approved Qty</th>-->
                                                <th>MRP (<small class="fas fa-rupee-sign"></small>)</th>
                                                <!--<th>Approved (<small class="fas fa-rupee-sign"></small>)</th>-->
                                                <th>Discount (%)</th>
                                                <th>Discounted (<small class="fas fa-rupee-sign"></small>)</th>
                                                <th>Action</th>
                                                <!--<th>Discount Price</th>-->
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="beanType" items="${requestScope['list']}"
                                                       varStatus="loopCounter">
                                                <tr>
                                                    <td>${loopCounter.count}</td>
                                            <input type="hidden" name="image_path" id="image_path${loopCounter.count}" value="${beanType.image_path}">
                                            <input type="hidden" name="image_name" id="image_name${loopCounter.count}" value="${beanType.image_name}">
                                            <input type="hidden" name="count" id="count" value="${count}">
                                            <input type="hidden" name="order_table_id" id="order_table_id" value="${beanType.order_table_id}">
                                            <input type="hidden" name="order_item_id" id="order_item_id${loopCounter.count}" value="${beanType.order_item_id}">
                                            <input type="hidden" name="item_status" id="item_status" value="${beanType.item_status}">
                                            <input type="hidden" name="order_status" id="order_status" value="${beanType.order_status}">
                                            <input type="hidden" name="required_qty" id="required_qty${beanType.order_item_id}" value="${beanType.required_qty}">
                                            <input type="hidden" name="stock_qty" id="stock_qty${beanType.order_item_id}" value="${beanType.stock_quantity}">
                                            <input type="hidden" name="approved_qty${beanType.order_item_id}" 
                                                   value="${beanType.approved_qty}" id="approved_qty${beanType.order_item_id}">
                                            <input type="hidden" name="approved_price${beanType.order_item_id}" 
                                                   value="${beanType.approved_price}" id="approved_price${beanType.order_item_id}">
                                            <input type="hidden" name="basic_price${beanType.order_item_id}" value="${beanType.basic_price}" id="basic_price${beanType.order_item_id}">


                                            <td>
                                                <img class="orderImg img-fluid${loopCounter.count}" src="" width="50px" height="55px">
                                            </td>

<!--                                            <td>${beanType.model}</td>
                                            <td>${beanType.item_name}</td>-->

                                            <td><p class="mb-0">${beanType.model}</p>
                                                <small>(${beanType.item_name})</small></td>
                                            <td>${beanType.required_qty}</td>

<!--<td>${beanType.stock_quantity}</td>-->


                                            <!--<td id="basic_price_col${beanType.order_item_id}">${beanType.basic_price}</td>-->


                                            <c:if test="${beanType.item_status=='Denied'}">
                                                <td id="basic_price_col${beanType.order_item_id}">0</td>

                                            </c:if>
                                            <c:if test="${beanType.item_status!='Denied'}">
                                                <td id="basic_price_col${beanType.order_item_id}">${beanType.basic_price}</td>

                                            </c:if>


                                            <td>
                                                <c:if test="${beanType.order_status=='Pending'}">
                                                    <input type="text" name="discounted_percent${beanType.order_item_id}"  id="discounted_percent${beanType.order_item_id}" 
                                                           value="${beanType.discount_percent}" style="width:70px" onblur="checkValidationForPrice(this.value, '${beanType.basic_price}', '${beanType.order_item_id}')">
                                                </c:if>
                                                <c:if test="${beanType.order_status!='Pending'}">
                                                    <input type="text" name="discounted_percent${beanType.order_item_id}" id="discounted_percent${beanType.order_item_id}"
                                                           disabled="" value="${beanType.discount_percent}" style="width:70px" onblur="checkValidationForPrice(this.value, '${beanType.basic_price}', '${beanType.order_item_id}')">
                                                </c:if>
                                            </td>

                                            <td>
                                                <c:if test="${beanType.order_status=='Pending'}">
                                                    <input type="text" name="discounted_price${beanType.order_item_id}"  id="discounted_price${beanType.order_item_id}" 
                                                           value="${beanType.discount_price}" style="width:90px" >
                                                </c:if>
                                                <c:if test="${beanType.order_status!='Pending'}">
                                                    <input type="text" name="discounted_price${beanType.order_item_id}"  id="discounted_price${beanType.order_item_id}" 
                                                           value="${beanType.discount_price}" style="width:90px" disabled="">
                                                </c:if>
                                            </td>
                                            <td>
                                                <select class="btn btn-primary myNewLinkBtn px-1 ml-3 fontFourteen" style="width:100px" value="${beanType.item_status}"
                                                        name="item_status${beanType.order_item_id}"  id="item_status${beanType.order_item_id}" onchange="validate(this.value, '${beanType.order_item_id}')">
                                                    <!--                                                    <option  class="btn btn-primary">Select</option>-->
                                                    <c:if test="${beanType.item_status=='Approved'}">
                                                        <option  class="btn btn-primary actionEdit fontFourteen" value="${beanType.item_status}">Approved</option>
                                                    </c:if>
                                                    <c:if test="${beanType.item_status=='Denied'}">
                                                        <option  class="btn btn-primary actionDelete fontFourteen" value="${beanType.item_status}"> Denied</option>
                                                    </c:if>
                                                    <c:if test="${beanType.item_status=='Pending'}">
                                                        <option  class="btn btn-primary fontFourteen" >Select</option>
                                                        <option  class="btn btn-primary actionEdit fontFourteen" value="Approved">Approved</option>
                                                        <option  class="btn btn-primary actionDelete fontFourteen" value="Denied">Denied</option>

                                                    </c:if>
                                                </select>
                                            </td>
                                            </tr>
                                        </c:forEach>

                                        <tr class="darkBlueBg">
                                            <td colspan="3"></td>
                                            <td  class="totalValue text-white">Total Amount</td>
                                            <!--<td class="totalValue text-white" id="total_approved_qty"></td>-->
                                            <td class="totalValue text-white" id="total_amount">${total_amount}</td>  
<!--                                            <td class="totalValue text-white" id="total_approved_price">${total_approved_price}</td>  -->
                                            <td class="totalValue text-white" id="total_percent">${total_discount_percent}</td>
                                            <td class="totalValue text-white" id="total_discounted_price">${total_discount_price}</td>
                                            <td class="totalValue text-white py-2">
                                                <input type="submit" name="task" onclick="checkStatus(this.value)" class="btn actionEdit fontFourteen " id="approved" value="Confirm">
                                                <input type="submit" name="task" onclick="checkStatus(this.value)" class="btn actionDelete fontFourteen " id="denied" value="Denied All">
                                            </td>   
                                        </tr>                    
                                        </tbody>
                                    </table>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </section>
</div>
<%@include file="/CRM Dashboard/CRM_footer.jsp" %>
<script>

    $(function () {
        var count = $('#count').val();
        var order_status = $('#order_status').val();
        if (order_status == 'Pending') {
            $('#approved').attr('disabled', false);
            $('#denied').attr('disabled', false);
        } else {
            $('#approved').attr('disabled', true);
            $('#denied').attr('disabled', true);

        }
        var total_price = 0;
        var total_discounted_percent = 0;
        var total_discounted_price = 0;

        $('.counting').text(count);
        for (var j = 0; j < count; j++) {
            var image_path = $('#image_path' + (j + 1)).val();
            var image_name = $('#image_name' + (j + 1)).val();
            var image = image_path + image_name;
            if (image != "") {
                image = image.replace(/\\/g, "/");
            }
            $('.img-fluid' + (j + 1)).attr("src", "http://" + IMAGE_URL + "/APL/DealersOrderController?getImage=" + image + "");


        }
    });

    function  checkStatus(btnstatus) {
        var count = $('#count').val();

//        alert(btnstatus);
        if (btnstatus == 'Confirm') {
            for (var k = 0; k < count; k++) {
                var order_item_ids = $('#order_item_id' + (k + 1)).val();
                var item_status = $('#item_status' + order_item_ids).val();

//            alert(item_status);
                if (item_status == 'Select' || item_status == '') {
                    alert("Please Select Status ");
                    event.preventDefault();
                    return false;
                }
            }
        }
        return true;
    }



    function checkValidationForPrice(discounted_percent, basic_price, order_item_id) {
        var numVal1 = basic_price;
        var numVal2 = discounted_percent / 100;

        var approved_qty = $('#approved_qty' + order_item_id).val();
        var required_qty = $('#required_qty' + order_item_id).val();
        if (approved_qty == '') {
            alert("Please enter approved qty!...");
            $('#discounted_percent' + order_item_id).val("");
            return false;
        }

        numVal1 = (basic_price / required_qty) * approved_qty;
        var totalValue = numVal1 - (numVal1 * numVal2)
        $('#discounted_price' + order_item_id).val(totalValue);
        if (parseInt(totalValue) > parseInt(basic_price)) {
            alert("Please enter valid Price!...");
            return false;
        }
        var count = $('#count').val();
        var total_approved_qty = 0;
        var total_discounted_percent = 0;
        var total_discounted_price = 0;
        var total_approved_price = 0;
        for (var k = 0; k < count; k++) {
            var order_item_ids = $('#order_item_id' + (k + 1)).val();
            total_approved_qty = total_approved_qty + parseInt($('#approved_qty' + order_item_ids).val());
            total_discounted_price = total_discounted_price + parseInt($('#discounted_price' + order_item_ids).val());
            total_approved_price = total_approved_price + parseInt($('#approved_price' + order_item_ids).val());

        }

        if (total_approved_price == 0 && total_discounted_price == 0) {
            total_discounted_percent = 0;
        } else {
            total_discounted_percent = ((total_approved_price - total_discounted_price) / total_approved_price) * 100;

        }

        $('#total_percent').html(parseFloat(total_discounted_percent).toFixed(2));
        $('#total_discounted_price').html(parseInt(total_discounted_price));
    }


    function validate(value, order_item_id) {
        if (value == 'Denied' || value == 'Select') {
            $('#discounted_price' + order_item_id).val(0);
            $('#discounted_percent' + order_item_id).val(0);
            var count = $('#count').val();
            var total_approved_qty = 0;
            var total_discounted_percent = 0;
            var total_discounted_price = 0;
            var total_approved_price = 0;
            var total_amount = 0;
            $('#approved_qty' + order_item_id).val(0);
            $('#approved_price' + order_item_id).val(0);
            $('#basic_price_col' + order_item_id).html(0);
            for (var k = 0; k < count; k++) {
                var order_item_ids = $('#order_item_id' + (k + 1)).val();
                total_approved_qty = total_approved_qty + parseInt($('#approved_qty' + order_item_ids).val());
                total_discounted_price = total_discounted_price + parseInt($('#discounted_price' + order_item_ids).val());
                total_approved_price = total_approved_price + parseInt($('#approved_price' + order_item_ids).val());
                total_amount = total_amount + parseInt($('#basic_price_col' + order_item_ids).html());
            }
            total_discounted_percent = 0;

            $('#total_percent').html(parseFloat(total_discounted_percent).toFixed(2));
            $('#total_discounted_price').html(parseInt(total_discounted_price));
            $('#total_amount').html(parseInt(total_amount));

        } else if (value == 'Approved') {
            var count = $('#count').val();
            var total_approved_qty = 0;
            var total_discounted_percent = 0;
            var total_discounted_price = 0;
            var total_approved_price = 0;
            var total_amount = 0;
            var approved_qty = 0;
            var approved_price = 0;
            var stock_qty = parseInt($('#stock_qty' + order_item_id).val());
            var required_qty = parseInt($('#required_qty' + order_item_id).val());
            var basic_price = parseInt($('#basic_price' + order_item_id).val());

            if (stock_qty < required_qty) {
                approved_qty = stock_qty;
                approved_price = stock_qty * basic_price / required_qty;
            } else {
                approved_qty = required_qty;
                approved_price = basic_price;
            }
            if ($('#discounted_percent' + order_item_id).val() == 0) {
                $('#approved_qty' + order_item_id).val(approved_qty);
                $('#discounted_price' + order_item_id).val(basic_price);
                $('#basic_price_col' + order_item_id).html(basic_price);
                $('#approved_price' + order_item_id).val(approved_price);
            } else {
                var numVal1 = basic_price;
                var numVal2 = $('#discounted_percent' + order_item_id).val() / 100;
                $('#approved_qty' + order_item_id).val(approved_qty);
                numVal1 = (basic_price / required_qty) * approved_qty;
                var totalValue = numVal1 - (numVal1 * numVal2)
                $('#discounted_price' + order_item_id).val(totalValue);
                $('#discounted_price' + order_item_id).val(totalValue);
                $('#basic_price_col' + order_item_id).html(basic_price);
                $('#approved_price' + order_item_id).val(approved_price);
            }

            for (var k = 0; k < count; k++) {
                var order_item_ids = $('#order_item_id' + (k + 1)).val();
                total_approved_qty = total_approved_qty + parseInt($('#approved_qty' + order_item_ids).val());
                total_discounted_price = total_discounted_price + parseInt($('#discounted_price' + order_item_ids).val());
                total_approved_price = total_approved_price + parseInt($('#approved_price' + order_item_ids).val());
                total_amount = total_amount + parseInt($('#basic_price_col' + order_item_ids).html());
            }

            total_discounted_percent = ((total_approved_price - total_discounted_price) / total_approved_price) * 100;
//
            $('#total_percent').html(parseFloat(total_discounted_percent).toFixed(2));
            $('#total_discounted_price').html(parseInt(total_discounted_price));
            $('#total_amount').html(parseInt(total_amount));
        }
    }
</script>
