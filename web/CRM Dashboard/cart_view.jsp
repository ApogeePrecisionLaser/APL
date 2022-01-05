<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>




<div class="content-wrapper" id="contentWrapper">
    <section class="content">

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


        <div class="marginTop20">
            <div class="">
                <div class="row mx-0">
                    <div class="col-10 col-md-7">
                        <div class="mr-2 backBtnWrap">
                            <a href="DealersOrderController" class="btn btnBack "><i class="fas fa-chevron-circle-left"></i></a>
                        </div>

                    </div>
                    <div class="col-2 col-md-5 mt-1 mt-md-0">
                        <!--                        <div class="alert alert-success alert-dismissible myAlertBox" style="display:none" id="msg">
                                                    <button type="button" class="close" data-dismiss="alert" >&times;</button>
                                                    <strong>Success!</strong>
                                                </div>-->
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




        <style>
            /*            .cartSection .leftSide .removeCart{
                            font-weight: 900;
                            padding: 5px 10px;
                            background-color: #f2f2f2;
                            box-shadow: 2px 2px 5px #999;
                            color: red;
                        }
                        .cartSection .leftSide .proName{
                            color: #000;
                            font-weight: 600;
                            margin-bottom: 2px;
                        }
                        .cartSection .leftSide .catName{
                            color: #999;
                            font-weight: 400;
                            font-size: 13px;
                        }
                        .cartSection .leftSide img{
                            width: 55px;
                            height: 50px;
                            object-fit: cover;
                        }*/
        </style>


        <div id="services" class="services section-bg marginTop30 cartSection">
            <div class="container-fluid">
                <div class="row row-sm">
                    <div class="col-md-8">
                        <div class="_product-detail-content leftSide">
                            <p class="_p-name"> Cart Item </p>
                            <div class="_p-price-box">
                                <div class="table-responsive cartTable"> 
                                    <table class="table table-bordered mb-0">
                                        <thead>
                                            <tr>
                                                <th>Name</th>
                                                <th>Quantity</th>
                                                <th>Rate <small>(<i class="fas fa-rupee-sign curruncyIcon"></i>)</small></th>
                                                <th>Price <small>(<i class="fas fa-rupee-sign curruncyIcon"></i>)</small></th>
                                                <!--                                                <th>Rate (Rs.)</th>
                                                                                                <th>Price (Rs.)</th>-->
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody id="tbody">
                                            <c:forEach var="beanType" items="${requestScope['list']}"
                                                       varStatus="loopCounter">

                                                <tr id="model_row${beanType.model_id}">
                                                    <td class="d-flex">
                                                        <div>

                                                            <input type="hidden" name="cart_table_id" id="cart_table_id${loopCounter.count }" value="${beanType.cart_table_id}">
                                                            <input type="hidden" name="image_path" id="image_path${loopCounter.count}" value="${beanType.image_path}">
                                                            <input type="hidden" name="image_name" id="image_name${loopCounter.count}" value="${beanType.image_name}">
                                                            <input type="hidden" name="count" id="count" value="${count}">
                                                            <input type="hidden" name="price" id="price${loopCounter.count}" value=" ${beanType.basic_price * beanType.quantity}">
                                                            <input type="hidden" name="model_id" id="model_id${loopCounter.count}" value="${beanType.model_id}">
                                                            <img src="https://s.fotorama.io/1.jpg" class="img-fluid${loopCounter.count}">
                                                        </div>
                                                        <div class="ml-2">
                                                            <p class="proName">${beanType.model}</p>
                                                            <p class="catName mb-0">${beanType.item_name}</p>
                                                        </div>                              
                                                    </td>
                                                    <td>
                                                        <div class="_p-add-cart mb-0">
                                                            <div class="_p-qty">
                                                                <div class="value-button decrease_" id="" value="Decrease Value"
                                                                     onclick="removeFromcart('${beanType.model_id}', '${beanType.model}', '${beanType.basic_price}')">-</div>

                                                                <input style="width:50px" type="text" class="inputIncDec" name="qty${beanType.model_id}" id="qty${beanType.model_id}" value="${beanType.quantity}" />

                                                                <div class="value-button increase_" id="" value="Increase Value" 
                                                                     onclick="addTocart('${beanType.model_id}', '${beanType.model}', '${beanType.basic_price}')">+</div>
                                                            </div>
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <input type="hidden" name="rate${beanType.model_id}" id="rate${beanType.model_id}" value="${beanType.basic_price}">
                                                        ${beanType.basic_price}</td>
                                                    <td id="price_div${beanType.model_id}">
                                                        <input type="hidden" name="basic_price${beanType.model_id}" id="basic_price${beanType.model_id}" value="${beanType.basic_price * beanType.quantity}">
                                                        ${beanType.basic_price * beanType.quantity}</td>
                                                    <td>
                                                        <a class="removeCart" 
                                                           onclick="removeAllFromcart('${beanType.model_id}', '${beanType.model}', '${beanType.basic_price}')"><i class="fas fa-trash-alt"></i></a> </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="_product-detail-content  border" style="background-color: #102f42;box-shadow: 2px 2px 7px #999;">
                            <p class="_p-name text-white"> Grand Total </p>
                            <div class="couponWrap mb-3">
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
                                            <td class="fontFourteen">Subtotal</td>
                                            <td class="fontFourteen" id="subtotal"></td>
                                        </tr>
                                        <tr>
                                            <td>Delivery Charge</td>
                                            <td id="delivery_charge">0</td>
                                        </tr>
                                        <tr>
                                            <td>Delivery Charge</td>
                                            <td id="coupon_discount">0</td>
                                        </tr>
                                        <tr>
                                            <td class="totalValue">Total Amount</td>
                                            <td class="totalValue" id="total_amount">?10,80,000.00</td>
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



        <div class="sec bg-light marginTop40 pt-4">
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
                                            <!--                                            <span class="wishlist">
                                                                                            <a alt="Add to Wish List" title="Add to Wish List" href="javascript:void(0);"> <i class="fa fa-heart"></i></a>
                                                                                        </span>-->
                                            <a href="DealersOrderController?task=viewDetail&model_id=${beanType2.model_id}">
                                                <!--<img src="https://ucarecdn.com/05f649bf-b70b-4cf8-90f7-2588ce404a08/-/resize/680x/" >--> 
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
                                            <!--<span class="offer-price"> Offer Price <i class="fa fa-inr"></i> 120 </span>-->
                                        </div>
                                        <div class="btn-box text-center">
                                            <!--                                            <a class="btn btn-sm" href="javascript:void(0);"> <i class="fa fa-shopping-cart"></i> Add to Cart </a>-->
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
        </div>

        <%@include file="/CRM Dashboard/CRM_footer.jsp" %>

        <script>
            $(function () {
                var count = $('#count').val();
                var total_price = 0;
                $('.counting').text(count);
                for (var j = 0; j < count; j++) {
                    var image_path = $('#image_path' + (j + 1)).val();
                    var image_name = $('#image_name' + (j + 1)).val();
                    var price = $('#price' + (j + 1)).val();
                    total_price = parseInt(total_price) + parseInt(price);
                    // alert(total_price);
                    $('#subtotal').text(total_price);
                    var image = image_path + image_name;
                    if (image != "") {
                        image = image.replace(/\\/g, "/");
                    }
                    // alert("http://localhost:8080/APL/DealersOrderController?getImage=" + image + "");
//                    $('.img-fluid' + (j + 1)).attr("src", "http://120.138.10.146:8080/APL/DealersOrderController?getImage=" + image + "");
                    $('.img-fluid' + (j + 1)).attr("src", "http://localhost:8080/APL/DealersOrderController?getImage=" + image + "");

                }
                var delivery_charge = parseInt(($('#delivery_charge').text()));
                var coupon_discount = parseInt(($('#coupon_discount').text()));

                $('#total_amount').text("Rs. " + (total_price + delivery_charge + coupon_discount));

            });

            $(function () {
                var count2 = $('#count2').val();
                for (var k = 0; k < count2; k++) {
                    var image_path2 = $('#image_path2' + (k + 1)).val();
                    var image_name2 = $('#image_name2' + (k + 1)).val();
                    var image2 = image_path2 + image_name2;
                    if (image2 != "") {
                        image2 = image2.replace(/\\/g, "/");
                    }
                    // alert("http://localhost:8080/APL/DealersOrderController?getImage=" + image + "");
//                    $('.img-fluid2' + (k + 1)).attr("src", "http://120.138.10.146:8080/APL/DealersOrderController?getImage=" + image2 + "");
                    $('.img-fluid2' + (k + 1)).attr("src", "http://localhost:8080/APL/DealersOrderController?getImage=" + image2 + "");
                    var stock_quantity = $('#stock_quantity2' + (k + 1)).val();
                    if (stock_quantity == 0) {
                        $('#msg_div' + (k + 1)).show();
                        $('#add' + (k + 1)).attr('disabled', true);
                    } else {
                        $('#msg_div' + (k + 1)).hide();
                        $('#add' + (k + 1)).attr('disabled', false);
                    }

                }
            });


            $(document).ready(function () {
                var owl = $('#recent_post');
                owl.owlCarousel({
                    margin: 20,
                    dots: false,
                    nav: true,
                    navText: [
                        "<i class='fa fa-chevron-left'></i>",
                        "<i class='fa fa-chevron-right'></i>"
                    ],
                    autoplay: true,
                    autoplayHoverPause: true,
                    responsive: {
                        0: {
                            items: 2
                        },
                        600: {
                            items: 3
                        },
                        1000: {
                            items: 5
                        },
                        1200: {
                            items: 4
                        }
                    }
                });

                $('.decrease_').click(function () {
                    decreaseValue(this);
                });
                $('.increase_').click(function () {
                    increaseValue(this);
                });
                function increaseValue(_this) {
                    var value = parseInt($(_this).siblings('input#number').val(), 10);
                    value = isNaN(value) ? 0 : value;
                    value++;
                    $(_this).siblings('input#number').val(value);
                }

                function decreaseValue(_this) {
                    var value = parseInt($(_this).siblings('input#number').val(), 10);
                    value = isNaN(value) ? 0 : value;
                    value < 1 ? value = 1 : '';
                    value--;
                    $(_this).siblings('input#number').val(value);
                }
            });



            function addTocart(model_id, model, basic_price) {
                var qty;

                var count = $('#count').val();
                // alert(count);
                $.ajax({
                    url: "DealersOrderController",
                    dataType: "json",
                    data: {task: "AddToCart", model_id: model_id, model_name: model, basic_price: basic_price, qty: qty},
                    success: function (data) {
                        console.log(data);
                        if (data.list > 0) {
                            $('.counting').text(data.list);
                            var lastaddedmodel_id = data.model_id;
                            var lastaddedmodel = data.model;
                            var lastaddeditem_name = data.item_name;
                            var lastaddedimage_path = data.image_path;
                            var lastaddedimage_name = data.image_name;
                            var lastaddedquantity = data.quantity;
                            var lastaddedbasic_price = data.basic_price;
                            var lastaddedcart_table_id = data.cart_table_id;
                            var lastaddeddimage = lastaddedimage_path + lastaddedimage_name;
                            if (lastaddeddimage != "") {
                                lastaddeddimage = lastaddeddimage.replace(/\\/g, "/");
                            }

                            var counting = data.list;
                            if (lastaddedmodel_id != '') {
                                count = parseInt(data.list);
                                $('#model_row' + model_id).remove();
                                $('#tbody').append('<tr id="model_row' + lastaddedmodel_id + '"><td class="d-flex"><div><input type="hidden" name="cart_table_id" id="cart_table_id' + counting + '" value="' + lastaddedcart_table_id + '"><input type="hidden" name="image_path" id="image_path' + counting + '" value="' + lastaddedimage_path + '"><input type="hidden" name="image_name" id="image_name' + counting + '" value="' + lastaddedimage_name + '"><input type="hidden" name="count" id="count" value="' + counting + '"><input type="hidden" name="price" id="price' + counting + '" value=" ' + lastaddedbasic_price * lastaddedquantity + '"><input type="hidden" name="model_id" id="model_id' + counting + '" value="' + lastaddedmodel_id + '"><img src="https://s.fotorama.io/1.jpg" class="img-fluid' + counting + '"></div><div class="ml-2"><p class="proName">' + lastaddedmodel + '</p><p class="catName mb-0">' + lastaddeditem_name + '</p></div></td><td><div class="_p-add-cart mb-0"><div class="_p-qty"><div class="value-button decrease_" id="" value="Decrease Value" onclick="removeFromcart(\'' + lastaddedmodel_id + '\', \'' + lastaddedmodel + '\', \'' + lastaddedbasic_price + '\')">-</div><input style="width:50px" type="text" class="inputIncDec" name="qty' + lastaddedmodel_id + '" id="qty' + lastaddedmodel_id + '" value="' + lastaddedquantity + '" /><div class="value-button increase_" id="" value="Increase Value" onclick="addTocart(\'' + lastaddedmodel_id + '\', \'' + lastaddedmodel + '\', \'' + lastaddedbasic_price + '\')">+</div></div></div></td><td><input type="hidden" name="rate' + lastaddedmodel_id + '" id="rate' + lastaddedmodel_id + '" value="' + lastaddedbasic_price + '">Rs. ' + lastaddedbasic_price + '</td><td id="price_div' + lastaddedmodel_id + '"><input type="hidden" name="basic_price' + lastaddedmodel_id + '" id="basic_price' + lastaddedmodel_id + '" value="' + lastaddedbasic_price * lastaddedquantity + '">Rs. ' + lastaddedbasic_price * lastaddedquantity + '</td><td><a class="removeCart" onclick="removeAllFromcart(\'' + lastaddedmodel_id + '\', \'' + lastaddedmodel + '\', \'' + lastaddedbasic_price + '\')"><i class="fas fa-trash-alt"></i></a> </td></tr>');

//                                $('.img-fluid' + counting).attr("src", "http://120.138.10.146:8080/APL/DealersOrderController?getImage=" + lastaddeddimage + "");
                                $('.img-fluid' + counting).attr("src", "http://localhost:8080/APL/DealersOrderController?getImage=" + lastaddeddimage + "");

                                var rate = parseInt($('#rate' + lastaddedmodel_id).val());
                                var qty = parseInt(lastaddedquantity);
                                var price = parseInt($('#basic_price' + lastaddedmodel_id).val());
                                $('#price_div' + lastaddedmodel_id).html('<input type="hidden" name="basic_price' + lastaddedmodel_id + '" id="basic_price' + lastaddedmodel_id + '" value="' + rate * qty + '">Rs. ' + rate * qty);
                                $('#basic_price' + lastaddedmodel_id).val(rate * qty);
                                var total_price = 0;
                                for (var j = 0; j < count; j++) {
                                    var model_ids = $('#model_id' + (j + 1)).val();
                                    var price = ($('#basic_price' + model_ids)).val();
                                    if (price == undefined) {
                                        price = 0;
                                    }
                                    total_price = parseInt(total_price) + parseInt(price);
                                    $('#subtotal').text(total_price);
                                }
                                var delivery_charge = parseInt(($('#delivery_charge').text()));
                                var coupon_discount = parseInt(($('#coupon_discount').text()));

                                $('#total_amount').text("Rs. " + (total_price + delivery_charge + coupon_discount));

                            } else {
                                //  alert("else");
                                count = data.list;

                                $('#qty' + model_id).val(data.current_quantity);
                                var rate = parseInt($('#rate' + model_id).val());
                                var qty = parseInt(data.current_quantity);
                                var price = parseInt($('#basic_price' + model_id).val());
                                $('#price_div' + model_id).html('<input type="hidden" name="basic_price' + model_id + '" id="basic_price' + model_id + '" value="' + rate * qty + '">Rs. ' + rate * qty);
                                $('#basic_price' + model_id).val(rate * qty);
                                var total_price = 0;
                                for (var j = 0; j < count; j++) {
                                    var model_ids = $('#model_id' + (j + 1)).val();
                                    var price = ($('#basic_price' + model_ids)).val();
                                    if (price == undefined) {
                                        price = 0;
                                    }
                                    total_price = parseInt(total_price) + parseInt(price);
                                    //  alert(total_price);
                                    $('#subtotal').text(total_price);
                                }
                                var delivery_charge = parseInt(($('#delivery_charge').text()));
                                var coupon_discount = parseInt(($('#coupon_discount').text()));

                                $('#total_amount').text("Rs. " + (total_price + delivery_charge + coupon_discount));
                            }



                            window.location.reload();

                            if (data.success_msg != '') {
                                $('.counting').text(data.list);
                                $('#msg_success').text(data.success_msg);
                                $('#msg_success').show();
                                $('#msg_danger').hide();
                                setTimeout(function () {
                                    $('#msg_success').fadeOut('fast');
                                }, 2000);
                            }
//                            $('#msg').text(data.msg);
//                            $('.myAlertBox').show();
//                            setTimeout(function () {
//                                $('#msg').fadeOut('fast');
//                            }, 1000);
                        } else {
                            //$('.myAlertBox').hide();
                            $('#msg_danger').text(data.error_msg);
                            $('#msg_danger').show();
                            $('#msg_success').hide();
                            setTimeout(function () {
                                $('#msg_danger').fadeOut('fast');
                            }, 2000);
                        }
                    }, error: function (error) {
                        console.log(error.responseText);
                        response(error.responseText);
                    }
                });
            }

            function removeFromcart(model_id, model, basic_price) {
                var qty;
                var count = $('#count').val();
                $.ajax({
                    url: "DealersOrderController",
                    dataType: "json",
                    data: {task: "removeFromcart", model_id: model_id, model_name: model, basic_price: basic_price, qty: qty},
                    success: function (data) {
                        console.log(data);
                        if (data.list > 0) {
                            $('.counting').text(data.list);
                            $('#msg_success').text(data.msg);
                            $('#msg_success').show();
                            $('#msg_danger').hide();
                            $('#qty' + model_id).val(data.current_quantity);
                            var rate = parseInt($('#rate' + model_id).val());
                            var qty = parseInt(data.current_quantity);
                            var price = parseInt($('#basic_price' + model_id).val());
                            $('#price_div' + model_id).html('<input type="hidden" name="basic_price' + model_id + '" id="basic_price' + model_id + '" value="' + rate * qty + '">Rs. ' + rate * qty);
                            $('#basic_price' + model_id).val(rate * qty);
                            var total_price = 0;
                            count = data.list;

                            for (var j = 0; j < count; j++) {
                                var model_ids = $('#model_id' + (j + 1)).val();
                                var price = ($('#basic_price' + model_ids)).val();
                                if (price == undefined) {
                                    price = 0;
                                }
                                total_price = parseInt(total_price) + parseInt(price);
                                $('#subtotal').text(total_price);
                            }
                            var delivery_charge = parseInt(($('#delivery_charge').text()));
                            var coupon_discount = parseInt(($('#coupon_discount').text()));

                            $('#total_amount').text("Rs. " + (total_price + delivery_charge + coupon_discount));

                            window.location.reload();

                            setTimeout(function () {
                                $('#msg_danger').fadeOut('fast');
                            }, 1000);
                        } else {
                            $('#msg_success').hide();
                            $('#msg_danger').show();
                            setTimeout(function () {
                                $('#msg_danger').fadeOut('fast');
                            }, 2000);

                        }
                    }, error: function (error) {
                        console.log(error.responseText);
                        response(error.responseText);
                    }
                });
            }


            function removeAllFromcart(model_id, model, basic_price) {
                var qty;
                if (confirm('Are you sure you want to remove it from cart?')) {
                    var count = $('#count').val();
                    $.ajax({
                        url: "DealersOrderController",
                        dataType: "json",
                        data: {task: "removeAllFromcart", model_id: model_id, model_name: model, basic_price: basic_price, qty: qty},
                        success: function (data) {
                            console.log(data);
                            if (data.list > 0) {
                                $('.counting').text(data.list);
                                $('#msg_success').text(data.msg);
                                $('#msg_success').show();
                                $('#msg_danger').hide();
                                var total_price = 0;
                                $('#model_row' + model_id).remove();
                                count = parseInt(data.list) + 1;
                                for (var j = 0; j < count; j++) {
                                    var model_ids = $('#model_id' + (j + 1)).val();
                                    var price = ($('#basic_price' + model_ids)).val();

                                    if (price == undefined) {
                                        price = 0;
                                    }

                                    total_price = parseInt(total_price) + parseInt(price);

                                    $('#subtotal').text(total_price);
                                }
                                var delivery_charge = parseInt(($('#delivery_charge').text()));
                                var coupon_discount = parseInt(($('#coupon_discount').text()));

                                $('#total_amount').text("Rs. " + (total_price + delivery_charge + coupon_discount));

                                window.location.reload();

                                setTimeout(function () {
                                    $('#msg_success').fadeOut('fast');
                                }, 2000);
                            } else {
                                $('#msg_success').hide();
                                $('#msg_danger').show();
                                setTimeout(function () {
                                    $('#msg_danger').fadeOut('fast');
                                }, 2000);

                            }
                        }, error: function (error) {
                            console.log(error.responseText);
                            response(error.responseText);
                        }
                    });
                } else {
                    return false;
                }


            }

            function completeOrder() {
                var count = $('#count').val();
                var model_id = [];
                var qty = [];
                var rate = [];
                var basic_price = [];
                for (var i = 0; i < count; i++) {
                    model_id[i] = $('#model_id' + (i + 1)).val();
                    qty[i] = $('#qty' + model_id[i]).val();
                    rate[i] = $('#rate' + model_id[i]).val();
                    basic_price[i] = $('#basic_price' + model_id[i]).val();
                    console.log(model_id);
                }
                console.log(model_id);
                console.log(qty);
                console.log(rate);
                console.log(basic_price);

                var subtotal = parseInt($('#subtotal').text());
                var delivery_charge = parseInt($('#delivery_charge').text());
                var coupon_discount = parseInt($('#coupon_discount').text());
                var total_amount = parseInt(($('#total_amount').text()).substring(3));
                $.ajax({
                    url: "DealersOrderController",
                    dataType: "json",
                    data: {task: "completeOrder", model_id: model_id, qty: qty, rate: rate, basic_price: basic_price, subtotal: subtotal,
                        delivery_charge: delivery_charge, coupon_discount: coupon_discount, total_amount: total_amount},
                    success: function (data) {
                        console.log(data.msg);
                        console.log(data.order_no);
                        if (data.msg == 'thank_you') {
                            window.open('DealersOrderController?order_no=' + data.order_no, '_self');
                        } else {
                            window.open('error', '_self', data.order_no);
                        }
//                        window.location.reload("thank_you");

                    }
                });
            }


        </script>
        <td>Coupon Discount</td>
        <td id="coupon_discount">0</td>
