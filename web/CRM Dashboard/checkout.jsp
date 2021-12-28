<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>


<div class="content-wrapper" id="contentWrapper">
    <br>
    <section class="content">
        <div class="container-fluid">
            <div class="mainNavigationMenu">
                <nav class="navbar navbar-expand-md navbar-dark" >
                    <a class="navbar-brand" href="#">SHOPPING</a>
                    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="collapsibleNavbar">
                        <ul class="navbar-nav ml-auto">
                            <li class="nav-item">
                                <a class="nav-link" href="#">One</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">Two</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">Three</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">Four</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">Five</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">Six</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">Seven</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">Eight</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">Nine</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">Ten</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">Eleven</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">Twelve</a>
                            </li>
                        </ul>
                    </div>
                </nav>
            </div>
        </div>


        <div class="marginTop25">
            <div class="">
                <div class="row">
                    <div class="col-md-7">
                        <a href="PendingOrdersController" class="btn myThemeBtn">Back</a>
                    </div>
                    <div class="col-md-5">
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


        <div id="services" class="services section-bg marginTop30 cartSection">
            <div class="container-fluid">
                <div class="row row-sm">
                    <div class="col-md-8">
                        <div class="border px-3 py-3 checkoutLeft">
                            <p class="checkoutLeftHead"> Checkout</p>
                            <div class="mt-1">
                                <form class="myForm">
                                    <div class="row">
                                        <input type="hidden" name="count" id="count" value="${count}">
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label for="inputName">Name:<sup class="text-danger">*</sup></label>
                                                <input type="text" class="form-control" value="${logged_org_office}" name="office_name" id="office_name">
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label for="inputName">Email:</label>
                                                <input type="text" class="form-control" readonly value="${email}" name="email" id="email">
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label for="inputName">Mobile:<sup class="text-danger">*</sup></label>
                                                <input type="text" class="form-control" value="${mobile1}"  name="mobile" id="mobile">
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label for="inputName">Payment Mode:<sup class="text-danger">*</sup></label>
                                                <input type="text" class="form-control" name="payment_mode" id="payment_mode" placeholder="Select Payment mode">
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label for="inputName">Transaction Number:<sup class="text-danger">*</sup></label>
                                                <input type="text" class="form-control" name="transaction_no" id="transaction_no">
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label for="inputName">Amount:<sup class="text-danger">*</sup></label>
                                                <input type="text" class="form-control" value="${total_discount_price}" name="amount" id="amount">
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label for="inputName">Billing Address:<sup class="text-danger">*</sup></label>
                                                <textarea class="form-control" readonly name="billing_add" id="billing_add">${address_line1},${address_line2},${address_line3},${city}</textarea>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label for="inputName">Shipping Address:<sup class="text-danger">*</sup></label>
                                                <textarea class="form-control" name="shipping_add" id="shipping_add"></textarea>
                                            </div>
                                        </div>
                                    </div>
                                </form>    
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="_product-detail-content  border" style="background-color: #102f42;box-shadow: 2px 2px 7px #999;">
                            <p class="_p-name text-white"> Grand Total </p>
                            <div class="searchWrap mb-3">
                                <form action="#" class="d-flex mb-1">
                                    <div class="form-group mb-0 w-100">
                                        <input type="text" class="form-control" placeholder="Coupon Code" id="email">
                                    </div>
                                    <button type="submit" class="btn bg-white border-white">Apply</button>
                                </form>
                                <p class="mb-0" style="color:#a1ff71;">Coupon Applied</p>
                                <!-- <p class="text-danger mb-0" style="color: #621c23;">Coupon Invalid</p> -->
                            </div>
                            <div>
                                <table class="table table-bordered mb-0 text-white">
                                    <tbody>
                                        <tr>
                                            <td >Subtotal</td>
                                            <td id="subtotal">${total_amount}</td>
                                        </tr>
                                        <tr>
                                            <td>Delivery Charge</td>
                                            <td id="delivery_charge">0</td>
                                        </tr>
                                        <tr>
                                            <td>Coupon Discount</td>
                                            <td id="coupon_discount">${total_discount_percent}</td>
                                        </tr>
                                        <tr>
                                            <td class="font-weight-bold1" style="font-size: 19px;font-weight: 600;">Total Amount</td>
                                            <td class="font-weight-bold1" style="font-size: 19px;font-weight: 600;" id="total_amount">${total_discount_price}</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>               
                        </div>
                        <div class="text-right mt-3">
                            <a class="btn myThemeBtn" style="background-color: #102f42;
                               color: white;" onclick="completeOrder()">Complete Order</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <!--                <div class="sec bg-light marginTop40 pt-4">
                            <div class="container">
                                <div class="row">
                                    <div class="col-sm-12 title_bx">
                                        <h3 class="title"> Related Products   </h3>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12 list-slider mt-4">
                                        <div class="owl-carousel common_wd  owl-theme" id="recent_post">
                
        <c:forEach var="beanType2" items="${requestScope['list2']}"
                   varStatus="loopCounter2">
            <div class="item">
                <div class="sq_box shadow">
                    <div class="pdis_img"> 
                                                                    <span class="wishlist">
                                                                        <a alt="Add to Wish List" title="Add to Wish List" href="javascript:void(0);"> <i class="fa fa-heart"></i></a>
                                                                    </span>
                        <a href="DealersOrderController?task=viewDetail&model_id=${beanType2.model_id}">
                            <img src="https://ucarecdn.com/05f649bf-b70b-4cf8-90f7-2588ce404a08/-/resize/680x/" > 
                            <input type="hidden" name="model_id" id="model_id${loopCounter2.count }" value="${beanType2.model_id}">
                            <input type="hidden" name="image_path2" id="image_path2${loopCounter2.count}" value="${beanType2.image_path}">
                            <input type="hidden" name="image_name2" id="image_name2${loopCounter2.count}" value="${beanType2.image_name}">
                            <input type="hidden" name="stock_quantity2" id="stock_quantity2${loopCounter2.count}" value="${beanType2.stock_quantity}">

                            <input type="hidden" name="count2" id="count2" value="${count2}">
                            <img src="https://s.fotorama.io/1.jpg" class="img-fluid2${loopCounter2.count}">
                        </a>
                    </div>
                    <h4 class="mb-1"> <a href="DealersOrderController?task=viewDetail&model_id=${beanType2.model_id}"> ${beanType2.model} </a> </h4>
                    <div class="price-box mb-2">
                        <span class="offer-price"> Price   ${beanType2.basic_price} </span>
                        <span class="offer-price"> Offer Price <i class="fa fa-inr"></i> 120 </span>
                    </div>
                    <div class="btn-box text-center">
                                                                    <a class="btn btn-sm" href="javascript:void(0);"> <i class="fa fa-shopping-cart"></i> Add to Cart </a>
                        <a ><button class="btn btn-primary addCartBtn" id="add${loopCounter2.count }" 
                                    onclick="addTocart('${beanType2.model_id}', '${beanType2.model}', '${beanType2.basic_price}')">
                                <i class="fa fa-shopping-cart"></i> Add to Cart</button></a>
                    </div>
                    <div id="msg_div${loopCounter2.count}" style="color:red;margin-left: 10px;display: none"><b>Out Of Stock</b></div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</div>
</div>
</div>-->

    </section>
</div>


<%@include file="/CRM Dashboard/CRM_footer.jsp" %>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<link href = "https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
      rel = "stylesheet">
<script src = "https://code.jquery.com/jquery-1.10.2.js"></script>
<script src = "https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>

<script>

                                   $(function () {
                                       var count = $('#count').val();
                                       var total_price = 0;
                                       $('.counting').text(count);
                                       var delivery_charge = parseInt(($('#delivery_charge').text()));
                                       var coupon_discount = parseInt(($('#coupon_discount').text()));
                                       var total_price = parseInt(($('#subtotal').text()));

//                                       $('#total_amount').text("Rs. " + (total_price + delivery_charge + coupon_discount));

                                   });


//                                                            $(function () {
//                                                                var count2 = $('#count2').val();
//                                                                for (var k = 0; k < count2; k++) {
//                                                                    var image_path2 = $('#image_path2' + (k + 1)).val();
//                                                                    var image_name2 = $('#image_name2' + (k + 1)).val();
//                                                                    var image2 = image_path2 + image_name2;
//                                                                    if (image2 != "") {
//                                                                        image2 = image2.replace(/\\/g, "/");
//                                                                    }
////                    $('.img-fluid2' + (k + 1)).attr("src", "http://120.138.10.146:8080/APL/DealersOrderController?getImage=" + image2 + "");
//                                                                    $('.img-fluid2' + (k + 1)).attr("src", "http://localhost:8080/APL/DealersOrderController?getImage=" + image2 + "");
//                                                                    var stock_quantity = $('#stock_quantity2' + (k + 1)).val();
//                                                                    if (stock_quantity == 0) {
//                                                                        $('#msg_div' + (k + 1)).show();
//                                                                        $('#add' + (k + 1)).attr('disabled', true);
//                                                                    } else {
//                                                                        $('#msg_div' + (k + 1)).hide();
//                                                                        $('#add' + (k + 1)).attr('disabled', false);
//                                                                    }
//
//                                                                }
//                                                            });



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
</script>