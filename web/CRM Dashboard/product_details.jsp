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


        <div class=" marginTop20">
            <div class="">
                <div class="row mx-0">
                    <div class="col-10 col-md-7">
                        <div class="mr-2 backBtnWrap">
                            <a href="DealersOrderController" class="btn btnBack "><i class="fas fa-chevron-circle-left"></i></a>
                        </div>

                    </div>
                    <div class="col-2 col-md-5 mt-1 mt-md-0">
                        <div class="alert alert-success alert-dismissible myAlertBox" style="display:none" id="msg">
                            <button type="button" class="close" data-dismiss="alert" >&times;</button>
                            <strong>Success!</strong> 
                        </div>
                        <div class="d-flex">                        
                            <div class="cartCountWrap d-flex ml-auto">
                                <a href="DealersOrderController?task=viewCart" >
                                    <div><i class="fas fa-cart-plus"></i></div>
                                    <div class="counting">${cart_count}</div>
                                </a>
                            </div>
                        </div>                    
                    </div>
                </div>
            </div>
        </div>

        <c:forEach var="beanType" items="${requestScope['list']}"
                   varStatus="loopCounter">
            <div id="services" class="services section-bg marginTop30">
                <div class="container-fluid">
                    <div class="row row-sm">
                        <div class="col-md-6 _boxzoom">
                            <div class="zoom-thumb">
                                <ul class="piclist">
                                    <c:forEach var="beanType2" items="${requestScope['list2']}"
                                               varStatus="loopCounter2">
                                        <li><img src="" alt="" class="my_img2${loopCounter2.count}"></li>
                                        <input type="hidden" name="image_path2" id="image_path2${loopCounter2.count}" value="${beanType2.image_path}">
                                        <input type="hidden" name="image_name2" id="image_name2${loopCounter2.count}" value="${beanType2.image_name}">
                                        <input type="hidden" name="count2" id="count2" value="${count2}">
                                    </c:forEach>

                                </ul>
                            </div>
                            <div class="_product-images">
                                <div class="picZoomer">
                                    <input type="hidden" name="model_id" id="model_id${loopCounter.count }" value="${beanType.model_id}">
                                    <input type="hidden" name="image_path" id="image_path${loopCounter.count}" value="${beanType.image_path}">
                                    <input type="hidden" name="image_name" id="image_name${loopCounter.count}" value="${beanType.image_name}">
                                    <input type="hidden" name="count" id="count" value="${count}">
                                    <input type="hidden" name="stock_quantity" id="stock_quantity" value="${beanType.stock_quantity}">

                                    <img class="my_img${loopCounter.count} mainImgPrDetail" src="" alt=""  width="100%" height="400">
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">

                            <div class="_product-detail-content">
                                <p class="_p-name"> <b>${beanType.model}</b> </p>
                                <div class="_p-price-box">
                                    <div class="p-list">
                                        <span> M.R.P. : <i class="fa fa-inr"></i> <del> Rs. ${beanType.basic_price}  </del>   </span>
                                        <span class="price"> Rs. ${beanType.basic_price} </span>
                                    </div>
                                    <div class="_p-add-cart">
                                        <!--                                        <div class="_p-qty">
                                                                                    <span>Add Quantity</span>
                                                                                    <div class="value-button decrease_" id="" value="Decrease Value">-</div>
                                                                                    <input type="number" name="qty" id="qty" class="inputIncDec" value="1" />
                                                                                    <div class="value-button increase_" id="" value="Increase Value">+</div>
                                                                                </div>-->
                                    </div>
                                    <div class="_p-features">
                                        <span> Description About this product:- </span>
                                        ${beanType.description}
                                    </div>
                                    <!--                                    <form action="" method="post" accept-charset="utf-8">-->
                                    <ul class="spe_ul"></ul>
                                    <div class="_p-qty-and-cart">
                                        <div class="_p-add-cart">
                                            <!--                                            <button class="btn-theme btn buy-btn" tabindex="0" id="buy_now_btn">
                                                                                            <i class="fa fa-shopping-cart"></i> Buy Now
                                                                                        </button>-->
                                            <a>
                                                <button class="btn-theme btn btn-success"  tabindex="0" onclick="addTocart('${beanType.model_id}', '${beanType.model}', '${beanType.basic_price}')" id="add_to_cart_btn">
                                                    <i class="fa fa-shopping-cart"></i> Add to Cart</button>
                                            </a>
                                            <div id="msg_div" style="color:red;margin-left: 10px;display: none"><b>Out Of Stock</b></div>
                                            <input type="hidden" name="pid" value="18" />
                                            <input type="hidden" name="price" value="850" />
                                            <input type="hidden" name="url" value="" />    
                                        </div>
                                    </div>
                                    <!--                                    </form>-->
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </c:forEach>

        <div class="sec bg-light marginTop40">
            <div class="container">
                <div class="row">
                    <div class="col-sm-12 title_bx">
                        <h3 class="title"> Related Products   </h3>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12 list-slider mt-4">
                        <div class="owl-carousel common_wd  owl-theme" id="recent_post">

                            <c:forEach var="beanType3" items="${requestScope['list3']}" varStatus="loopCounter3">
                                <div class="item">
                                    <div class="ribbon-wrapper ribbon-xl" id="msg_div${loopCounter.count }">
                                        <div class="ribbon bg-danger fontFourteen">
                                            Out of Stock
                                        </div>
                                    </div>
                                    <div class="sq_box shadow">
                                        <div class="pdis_img"> 


                                            <!--                                            <span class="wishlist">
                                                                                            <a alt="Add to Wish List" title="Add to Wish List" href="javascript:void(0);"> <i class="fa fa-heart"></i></a>
                                                                                        </span>-->
                                            <a href="DealersOrderController?task=viewDetail&model_id=${beanType3.model_id}">

                                                <input type="hidden" name="model_id" id="model_id3${loopCounter3.count }" value="${beanType3.model_id}">
                                                <input type="hidden" name="image_path" id="image_path3${loopCounter3.count}" value="${beanType3.image_path}">
                                                <input type="hidden" name="image_name" id="image_name3${loopCounter3.count}" value="${beanType3.image_name}">
                                                <input type="hidden" name="count3" id="count3" value="${count3}">
                                                <input type="hidden" name="stock_quantity" id="stock_quantity${loopCounter3.count}" value="${beanType3.stock_quantity}">
                                                <img class="my_img3${loopCounter3.count}" src="" alt="">
                                            </a>
                                        </div>
                                        <h4 class="mb-1"> <a href="DealersOrderController?task=viewDetail&model_id=${beanType3.model_id}"> ${beanType3.model} </a> </h4>
                                        <div class="price-box mb-2">
                                            <!--<span class="price"> Price <i class="fa fa-inr"></i> Rs. ${beanType3.basic_price} </span>-->
                                            <span class="offer-price"> Offer Price <i class="fa fa-inr"></i> Rs. ${beanType3.basic_price} </span>
                                        </div>
                                        <div class="btn-box text-center">
                                            <a href="javascript:void(0);">
                                                <button  class="btn btn-sm" id="add_to_cart_btn${loopCounter3.count}"
                                                         onclick="addTocart('${beanType3.model_id}', '${beanType3.model}', '${beanType3.basic_price}')" id="add_to_cart_btn"><i class="fa fa-shopping-cart"></i> Add to Cart</button>  </a>
                                        </div>
                                        <!--<div id="msg_div${loopCounter3.count}" style="color:red;margin-left: 10px;display: none"><b>Out Of Stock</b></div>-->

                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </section>
</div>


<%@include file="/CRM Dashboard/CRM_footer.jsp" %>

<script>

    ;
    (function ($) {
        $.fn.picZoomer = function (options) {
            var opts = $.extend({}, $.fn.picZoomer.defaults, options),
                    $this = this,
                    $picBD = $('<div class="picZoomer-pic-wp"></div>').css({'width': opts.picWidth + 'px', 'height': opts.picHeight + 'px'}).appendTo($this),
                    $pic = $this.children('img').addClass('picZoomer-pic').appendTo($picBD),
                    $cursor = $('<div class="picZoomer-cursor"><i class="f-is picZoomCursor-ico"></i></div>').appendTo($picBD),
                    cursorSizeHalf = {w: $cursor.width() / 2, h: $cursor.height() / 2},
                    $zoomWP = $('<div class="picZoomer-zoom-wp"><img src="" alt="" class="picZoomer-zoom-pic"></div>').appendTo($this),
                    $zoomPic = $zoomWP.find('.picZoomer-zoom-pic'),
                    picBDOffset = {x: $picBD.offset().left, y: $picBD.offset().top};


            opts.zoomWidth = opts.zoomWidth || opts.picWidth;
            opts.zoomHeight = opts.zoomHeight || opts.picHeight;
            var zoomWPSizeHalf = {w: opts.zoomWidth / 2, h: opts.zoomHeight / 2};

            //???zoom????
            $zoomWP.css({'width': opts.zoomWidth + 'px', 'height': opts.zoomHeight + 'px'});
            $zoomWP.css(opts.zoomerPosition || {top: 0, left: opts.picWidth + 30 + 'px'});
            //???zoom????
            $zoomPic.css({'width': opts.picWidth * opts.scale + 'px', 'height': opts.picHeight * opts.scale + 'px'});

            //?????
            $picBD.on('mouseenter', function (event) {
                $cursor.show();
                $zoomWP.show();
                $zoomPic.attr('src', $pic.attr('src'))
            }).on('mouseleave', function (event) {
                $cursor.hide();
                $zoomWP.hide();
            }).on('mousemove', function (event) {
                var x = event.pageX - picBDOffset.x,
                        y = event.pageY - picBDOffset.y;

                $cursor.css({'left': x - cursorSizeHalf.w + 'px', 'top': y - cursorSizeHalf.h + 'px'});
                $zoomPic.css({'left': -(x * opts.scale - zoomWPSizeHalf.w) + 'px', 'top': -(y * opts.scale - zoomWPSizeHalf.h) + 'px'});

            });
            return $this;

        };
        $.fn.picZoomer.defaults = {
            picHeight: 400,
            scale: 2.5,
            zoomerPosition: {top: '0', left: '380px'},

            zoomWidth: 400,
            zoomHeight: 400
        };
    })(jQuery);



    $(document).ready(function () {
        $('.picZoomer').picZoomer();
        $('.piclist li').on('click', function (event) {
            var $pic = $(this).find('img');
            $('.picZoomer-pic').attr('src', $pic.attr('src'));
        });

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


    $(function () {
        var count = $('#count').val();
        var stock_quantity = $('#stock_quantity').val();

        if (stock_quantity == 0) {
            $('#msg_div').show();
            $('#buy_now_btn').attr('disabled', true);
            $('#add_to_cart_btn').attr('disabled', true);
        } else {
            $('#msg_div').hide();
            $('#buy_now_btn').attr('disabled', false);
            $('#add_to_cart_btn').attr('disabled', false);
        }

        var count2 = $('#count2').val();
        for (var j = 0; j < count; j++) {
            var image_path = $('#image_path' + (j + 1)).val();
            var image_name = $('#image_name' + (j + 1)).val();
            var image = image_path + image_name;
            if (image != "") {
                image = image.replace(/\\/g, "/");
            }
            $('.my_img' + (j + 1)).attr("src", "http://localhost:8080/APL/DealersOrderController?getImage=" + image + "");
        }
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
            $('.my_img2' + (k + 1)).attr("src", "http://localhost:8080/APL/DealersOrderController?getImage=" + image2 + "");
        }
    });

    $(function () {
        var count3 = $('#count3').val();

        for (var l = 0; l < count3; l++) {
            var stock_quantity = $('#stock_quantity' + (l + 1)).val();
            if (stock_quantity == 0) {
                $('#msg_div' + (l + 1)).show();
                $('#add_to_cart_btn' + (l + 1)).attr('disabled', true);
            } else {
                $('#msg_div' + (l + 1)).hide();
                $('#add_to_cart_btn' + (l + 1)).attr('disabled', false);
            }


            var image_path3 = $('#image_path3' + (l + 1)).val();
            var image_name3 = $('#image_name3' + (l + 1)).val();
            var image3 = image_path3 + image_name3;
            if (image3 != "") {
                image3 = image3.replace(/\\/g, "/");
            }
            $('.my_img3' + (l + 1)).attr("src", "http://localhost:8080/APL/DealersOrderController?getImage=" + image3 + "");
        }
    });

    function addTocart(model_id, model, basic_price) {
        debugger;
        //alert("id  --" + model_id + "  model -- " + model + "  price --- " + basic_price);
        var qty = $('#qty').val();
        $.ajax({
            url: "DealersOrderController",
            dataType: "json",
            data: {task: "AddToCart", model_id: model_id, model_name: model, basic_price: basic_price, qty: qty},
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



            }, error: function (error) {
                console.log(error.responseText);
                response(error.responseText);
            }
        });
    }

</script>
