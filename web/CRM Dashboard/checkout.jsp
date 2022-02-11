<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>


<div class="content-wrapper" id="contentWrapper">
    <section class="content">
        <div class="marginTop20">
            <div class="">
                <div class="row">
                    <div class="col-10 col-md-7">
                        <div class="d-flex leftHeadText">
                            <div class="mr-2 backBtnWrap">
                                <a href="PendingOrdersController" class="btn btnBack "><i class="fas fa-chevron-circle-left"></i></a>
                            </div>
                            <div>
                                <h6 class="mt-2">Order ID : ${order_no}
                                </h6></div>
                        </div>
                    </div> 

                    <div class="col-2 col-md-5 mt-1 mt-md-0">
                        <div class="d-flex">                        
                            <div class="cartCountWrap d-flex ml-auto">
                                <a href="DealersOrderController?task=viewCart" >
                                    <div><i class="fas fa-cart-plus"></i></div>
                                    <div class="counting">0</div>
                                </a>
                            </div>
                        </div>                    
                    </div>
                </div>
            </div>
        </div>


        <div id="services" class="services section-bg marginTop20 cartSection mb-3 mb-md-2">
            <div class="container-fluid">
                <form action="DealersOrderController" method="post">
                    <div>
                        <div class="row row-sm" >
                            <div class="col-md-8 orderTwo">
                                <div class="border px-3 py-3 checkoutLeft">
                                    <p class="checkoutLeftHead"> Checkout Detail</p>
                                    <div class="mt-1">
                                        <!--                                <form class="myForm">-->
                                        <div class="row">
                                            <input type="hidden" name="count" id="count" value="${count}">
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label for="inputName" class="fontFourteen">Name:<sup class="text-danger">*</sup></label>
                                                    <input type="text" class="form-control" value="${logged_org_office}" name="office_name" id="office_name">
                                                    <input type="hidden" class="form-control" value="${order_no}" name="order_no" id="order_no">
                                                    <input type="hidden" class="form-control" value="${total_approved_price}" name="subtotal" id="subtotal">
                                                    <input type="hidden" class="form-control" value="0" name="delivery_charge" id="delivery_charge">
                                                    <input type="hidden" class="form-control" value="${total_discount_percent}" name="coupon_discount" id="coupon_discount">
                                                    <input type="hidden" class="form-control" value="${total_discount_price}" name="total_amount" id="total_amount">
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label for="inputName" class="fontFourteen">Email:</label>
                                                    <input type="text" class="form-control" readonly value="${email}" name="email" id="email">
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label for="inputName" class="fontFourteen">Mobile:<sup class="text-danger">*</sup></label>
                                                    <input type="text" class="form-control" value="${mobile1}"  name="mobile" id="mobile">
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label for="inputName" class="fontFourteen">Payment Mode:<sup class="text-danger">*</sup></label>
                                                    <input type="text" class="form-control" name="payment_mode" id="payment_mode" placeholder="Select Payment mode">
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label for="inputName" class="fontFourteen">Transaction Number:<sup class="text-danger">*</sup></label>
                                                    <input type="text" class="form-control" name="transaction_no" id="transaction_no">
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label for="inputName" class="fontFourteen">Amount:<sup class="text-danger">*</sup></label>
                                                    <input type="text" class="form-control" value="${total_discount_price}" name="amount" id="amount">
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label for="inputName" class="fontFourteen">Billing Address:<sup class="text-danger">*</sup></label>
                                                    <textarea class="form-control" readonly name="billing_add" id="billing_add">${address_line1},${address_line2},${address_line3},${city}</textarea>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label for="inputName" class="fontFourteen">Shipping Address:<sup class="text-danger">*</sup></label>
                                                    <textarea class="form-control" name="shipping_add" id="shipping_add"></textarea>
                                                </div>
                                            </div>
                                        </div>
                                        <!--</form>-->    
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4 mb-4 mb-md-0">
                                <div class="_product-detail-content  border" style="background-color: #102f42;box-shadow: 2px 2px 7px #999;">
                                    <p class="_p-name text-white"> Your Order Detail </p>
                                    <!--                                    <div class="couponWrap mb-3">
                                                                            <div class="d-flex justify-content-start">
                                                                                <div class="form-group mb-0 w-100">
                                                                                    <input type="text" class="form-control" placeholder="Coupon Code" id="email">
                                                                                </div>
                                                                                <button type="button" class="btn bg-white border-white">Apply</button>
                                                                            </div>
                                                                            <p class="mb-0" style="color:#a1ff71;">Coupon Applied</p>
                                                                             <p class="text-danger mb-0" style="color: #621c23;">Coupon Invalid</p> 
                                                                        </div>-->
                                    <div>
                                        <table class="table table-bordered mb-0 text-white">
                                            <tbody>
                                                <tr>
                                                    <td >Subtotal</td>
                                                    <td id="subtotal">${total_approved_price}</td>
                                                </tr>
                                                <tr>
                                                    <td class="fontFourteen">Delivery Charge</td>
                                                    <td class="fontFourteen" id="delivery_charge">0</td>
                                                </tr>
                                                <tr>
                                                    <td class="fontFourteen">Coupon Discount</td>
                                                    <td class="fontFourteen" id="coupon_discount">${total_discount_percent}</td>
                                                </tr>
                                                <tr>
                                                    <td class="totalValue">Total Amount</td>
                                                    <td class="totalValue" id="total_amount">Rs. ${total_discount_price}</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>               
                                </div>
                                <!--                        <div class="text-right mt-3">
                                                            <a class="btn myThemeBtn" style="background-color: #102f42;
                                                               color: white;" onclick="completeOrder()">Complete Order</a>
                                                        </div>-->
                            </div>
                        </div>
                        <div class="text-right mt-3">
                            <!--<a class="btn myThemeBtn" onclick="completeOrder()">Complete Order</a>-->
                            <input type="submit" class="btn myThemeBtn" name="task" id="deliverOrder" value="Complete Checkout">
                        </div>
                    </div>
                </form>
            </div>
        </div>

    </section>
</div>


<%@include file="/CRM Dashboard/CRM_footer.jsp" %>
<!--<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<link href = "https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
      rel = "stylesheet">
<script src = "https://code.jquery.com/jquery-1.10.2.js"></script>
<script src = "https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>-->

<script>

    $(function () {
        var count = $('#count').val();
        var total_price = 0;
        $('.counting').text(count);
        var delivery_charge = parseInt(($('#delivery_charge').text()));
        var coupon_discount = parseInt(($('#coupon_discount').text()));
        var total_price = parseInt(($('#subtotal').text()));
    });

    $(function () {

        $("#payment_mode").autocomplete({
            source: function (request, response) {
                var random = $('#payment_mode').val();
                $.ajax({
                    url: "DealersOrderController",
                    dataType: "json",
                    data: {action1: "getPaymentMode", str: random},
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
                $('#payment_mode').val(ui.item.label);
                return false;
            }
        });
    });


//    function completeOrder() {
//
//        var office_name = $('#office_name').val();
//        var email = $('#email').val();
//        var mobile = $('#mobile').val();
//        var payment_mode = $('#payment_mode').val();
//        var transaction_no = $('#transaction_no').val();
//        var amount = $('#amount').val();
//        var billing_add = $('#billing_add').val();
//        var shipping_add = $('#shipping_add').val();
//        var order_no = $('#order_no').val();
//        var subtotal = parseInt($('#subtotal').text());
//        var delivery_charge = parseInt($('#delivery_charge').text());
//        var coupon_discount = parseInt($('#coupon_discount').text());
//        var total_amount = parseInt(($('#total_amount').text()).substring(3));
//        $.ajax({
//            url: "DealersOrderController",
//            dataType: "json",
//            data: {task: "deliverOrder", office_name: office_name, email: email, mobile: mobile, payment_mode: payment_mode, transaction_no: transaction_no,
//                subtotal: subtotal, amount: amount, billing_add: billing_add, shipping_add: shipping_add, order_no: order_no,
//                delivery_charge: delivery_charge, coupon_discount: coupon_discount, total_amount: total_amount},
//            success: function (data) {
//                alert(data);
//                console.log(data.msg);
//                console.log(data.order_no);
//                if (data.msg == 'thank_you') {
//                    window.open('DealersOrderController?order_no=' + data.order_no, '_self');
//                } else {
//                    window.open('error', '_self', data.order_no);
//                }
//            }
//        });
//    }
</script>
