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

            <div class="marginTop30">
                <div class="">
                    <div class="row">
                        <div class="col-md-2">
                            <a href="DealersOrderController" class="btn btn-info">Back To List</a>

                        </div>
                        <div class="col-md-5">
                            <div class="searchWrap">
                                <form action="DealersOrderController">
                                    <div class="form-group mb-0 d-flex">
                                        <input type="hidden" name="item_name" id="item_name" value="${item_name}">

                                        <input type="text" name="search_model" id="search_model" class="form-control" placeholder="Search by product name" value="${search_model}">
                                        <button type="submit" class="btn btn-primary"><i class="fas fa-search"></i></button>
                                    </div>                          
                                </form>
                            </div>
                        </div>
                        <div class="col-md-5">
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

            <div class="row mt-3">
                <c:forEach var="beanType1" items="${requestScope['list']}"
                           varStatus="loopCounter">

                    <div class="col-md-3">
                        <div class="productBox">
                            <div class="ribbon-wrapper ribbon-xl" id="msg_div${loopCounter.count }">
                                <div class="ribbon bg-danger fontFourteen">
                                    Out of Stock
                                </div>
                            </div>
                            <div class="card">
                                <div class="card-header">

                                    <input type="hidden" name="model_id" id="model_id${loopCounter.count }" value="${beanType1.model_id}">
                                    <input type="hidden" name="image_path" id="image_path${loopCounter.count}" value="${beanType1.image_path}">
                                    <input type="hidden" name="image_name" id="image_name${loopCounter.count}" value="${beanType1.image_name}">
                                    <input type="hidden" name="count" id="count" value="${count}">
                                    <input type="hidden" name="stock_quantity" id="stock_quantity${loopCounter.count}" value="${beanType1.stock_quantity}">


                                    <a href="DealersOrderController?task=viewDetail&model_id=${beanType1.model_id}">
                                        <img src="" width="300" height="220" class="img-fluid${loopCounter.count}">
                                    </a>
                                </div>
                                <div class="card-body px-2 pt-2 pb-2">
                                    <div>
                                        <div class="catname">
                                            <small>${beanType1.manufacturer_name}</small>
                                        </div>
                                        <div class="mt-2 productName">
                                            <a href="DealersOrderController?task=viewDetail&model_id=${beanType1.model_id}">
                                                <p><b>${beanType1.model}</b></p>
                                            </a>
                                        </div>
                                    </div>
                                    <div class="d-flex justify-content-between">
                                        <div class="d-flex priceBox">
                                            <h2 style="">Rs. ${beanType1.basic_price}</h2> &nbsp&nbsp
                                            <!--<h3 style=""> <del>₹110.8</del></h3>-->
                                            <!--<div id="msg_div${loopCounter.count }" style="color:red;display: none;margin-left: 50px"> <b>Out Of Stock</b></div>-->

                                        </div>
                                        <div>
                                            <a>
                                                <button class="btn btn-primary addCartBtn" id="add${loopCounter.count }" 
                                                        onclick="addTocart('${beanType1.model_id}', '${beanType1.model}', '${beanType1.basic_price}')"><i class="fas fa-cart-plus"></i>&nbsp Add
                                                </button>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>

            </div>
        </div>
    </section>
</div>


<%@include file="/CRM Dashboard/CRM_footer.jsp" %>
<link href = "https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
      rel = "stylesheet">
<script src = "https://code.jquery.com/jquery-1.10.2.js"></script>
<script src = "https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script>
                                                            $(function () {
                                                                $("#search_model").autocomplete({
                                                                    source: function (request, response) {
                                                                        var random = $('#search_model').val();
                                                                        var item_name = $('#item_name').val();
                                                                        $.ajax({
                                                                            url: "DealersOrderController",
                                                                            dataType: "json",
                                                                            data: {action1: "getModelName", str: random, item_name: item_name},
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
                                                                        $('#search_model').val(ui.item.label);
                                                                        return false;
                                                                    }
                                                                });




                                                                var count = $('#count').val();
                                                                //alert(count);
                                                                for (var j = 0; j < count; j++) {
                                                                    var stock_quantity = $('#stock_quantity' + (j + 1)).val();
                                                                    if (stock_quantity == 0) {
                                                                        $('#msg_div' + (j + 1)).show();
                                                                        $('#add' + (j + 1)).attr('disabled', true);
                                                                    } else {
                                                                        $('#msg_div' + (j + 1)).hide();
                                                                        $('#add' + (j + 1)).attr('disabled', false);
                                                                    }


                                                                    var image_path = $('#image_path' + (j + 1)).val();
                                                                    var image_name = $('#image_name' + (j + 1)).val();
                                                                    var image = image_path + image_name;
                                                                    if (image != "") {
                                                                        image = image.replace(/\\/g, "/");
                                                                    }
                                                                    // alert("http://localhost:8080/APL/DealersOrderController?getImage=" + image + "");
//                                                                    $('.img-fluid' + (j + 1)).attr("src", "http://120.138.10.146:8080/APL/DealersOrderController?getImage=" + image + "");
                                                                    $('.img-fluid' + (j + 1)).attr("src", "http://localhost:8080/APL/DealersOrderController?getImage=" + image + "");
                                                                }
                                                            });

                                                            function addTocart(model_id, model, basic_price) {
                                                                debugger;
                                                                //alert("id  --" + model_id + "  model -- " + model + "  price --- " + basic_price);
                                                                var qty;
                                                                $.ajax({
                                                                    url: "DealersOrderController",
                                                                    dataType: "json",
                                                                    data: {task: "AddToCart", model_id: model_id, model_name: model, basic_price: basic_price, qty: qty},
                                                                    success: function (data) {
                                                                        console.log(data);
                                                                        if (data.list > 0) {
                                                                            $('.counting').text(data.list);
                                                                            $('#msg').text(data.msg);
                                                                            $('.myAlertBox').show();
                                                                            setTimeout(function () {
                                                                                $('#msg').fadeOut('fast');
                                                                            }, 1000);
                                                                        } else {
                                                                            $('.myAlertBox').hide();

                                                                        }


                                                                    }, error: function (error) {
                                                                        console.log(error.responseText);
                                                                        response(error.responseText);
                                                                    }
                                                                });
                                                            }
</script>