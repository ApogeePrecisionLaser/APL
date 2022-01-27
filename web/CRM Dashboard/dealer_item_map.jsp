<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>

<style>
    .productBox .checkboxWrap{
        margin-right: 4px;
    }
    .productBox .checkboxWrap input[type=checkbox]{
        transform: scale(1.6);
    }
</style>

<div class="content-wrapper" id="contentWrapper">
    <div class="content-header">
        <div class="container-fluid">
            <div class="row marginTop10 mx-0">
                <div class="col-sm-6 pl-0">
                    <div class="d-flex">
                        <div class="mr-2 backBtnWrap">
                            <a href="DealersController" class="btn btnBack "><i class="fas fa-chevron-circle-left"></i></a>
                        </div>
                        <div>
                            <h1>${org_office_name}</h1>
                        </div>
                        <div class="position-relative">
                            <div class="alert alert-success alert-dismissible myAlertBox" style="display:none"  id="msg">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong>Success!</strong> 
                            </div>
                        </div>
                    </div>  
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="CRMDashboardController">Dashboard</a></li>
                        <li class="breadcrumb-item active">Dealers Product Map</li>
                    </ol>
                </div>
            </div>
        </div><!-- /.container-fluid -->
    </div>
    <section class="content">
        <div class="container-fluid">
            <div class="container-fluid">
                <c:forEach var="beanType1" items="${requestScope['list1']}"
                           varStatus="loopCounter">
                    <div class="mt-4">
                        <div class="headerText d-flex justify-content-between mb-3">
                            <a><h2 class="mb-0 mt-2">${beanType1.item_name}</h2></a>          
                        </div>

                        <div class="mt-3 ">
                            <div class="owl-carousel owl-theme productSlider">
                                <c:forEach var="beanType2" items="${requestScope['list2']}"
                                           varStatus="loopCounter">
                                    <c:if test="${beanType1.item_name == beanType2.item_name}" >
                                        <div class="item">
                                            <div class="productBox">
<!--                                                <div class="ribbon-wrapper ribbon-xl" id="msg_div${loopCounter.count }">
                                                <div class="ribbon bg-danger fontFourteen">
                                                    Out of Stock
                                                </div>
                                            </div>-->
                                                <div class="card">
                                                    <div class="card-header" id="myid${loopCounter.count}">
                                                        <input type="hidden" name="model_id" id="model_id${loopCounter.count }" value="${beanType2.model_id}">
                                                        <input type="hidden" name="image_path" id="image_path${loopCounter.count}" value="${beanType2.image_path}">
                                                        <input type="hidden" name="image_name" id="image_name${loopCounter.count}" value="${beanType2.image_name}">
                                                        <input type="hidden" name="count" id="count" value="${count}">
                                                        <input type="hidden" name="stock_quantity" id="stock_quantity${loopCounter.count}" value="${beanType2.stock_quantity}">

                                                        <!--<img src="http://apogeeleveller.com/assets/images/gallery/Apogee1Transmitter.png" class="img-fluid">-->
                                                        <a>
                                                            <img src="" width="300" height="220" class="img-fluid${loopCounter.count }">
                                                        </a>
                                                    </div>
                                                    <div class="card-body px-2 pt-2 pb-2">
                                                        <div>
                                                            <div class="catname">
                                                                <small>${beanType2.manufacturer_name}</small>
                                                            </div>
                                                            <div class="mt-2 productName">
                                                                <a href="">
                                                                    <p><b>${beanType2.model}</b></p>
                                                                </a>
                                                            </div>
                                                        </div>
                                                        <div class="d-flex justify-content-between">
                                                            <div class="d-flex priceBox">
                                                                <h2 style="">Rs. ${beanType2.basic_price} </h2> &nbsp&nbsp
                                                                <!--<h3 style=""> <del>?110.8</del></h3>-->
                                                                <!--<div id="msg_div${loopCounter.count }" style="color:red;display: none;margin-left: 50px"> <b>Out Of Stock</b></div>-->
                                                            </div>
                                                            <div class="checkboxWrap">
                                                                <c:if  test="${beanType2.checked=='Yes'}">
                                                                    <input type="checkbox" name="map_item" id="map_item" value="${beanType2.model}" checked="" onclick="unMapItemWithDealer('${beanType2.dealer_item_map_id}')">
                                                                </c:if>
                                                                <c:if  test="${beanType2.checked=='No'}">
                                                                    <input type="checkbox" name="map_item" id="map_item" value="${beanType2.model}" onclick="mapItemWithDealer('${beanType2.model}', '${beanType2.model_id}', '${beanType2.item_name}', '${org_office_id}')">
                                                                </c:if>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </section>

    <script>
        $(function () {
            var count = $('#count').val();

            //        $("#search_item").autocomplete({
            //            source: function (request, response) {
            //                var random = $('#search_item').val();
            //                $.ajax({
            //                    url: "DealersOrderController",
            //                    dataType: "json",
            //                    data: {action1: "getItemName", str: random},
            //                    success: function (data) {
            //                        console.log(data);
            //                        response(data.list);
            //                    }, error: function (error) {
            //                        console.log(error.responseText);
            //                        response(error.responseText);
            //                    }
            //                });
            //            },
            //            select: function (events, ui) {
            //                console.log(ui);
            //                $('#search_item').val(ui.item.label);
            //                return false;
            //            }
            //        });



            for (var j = 0; j < count; j++) {
                var image_path = $('#image_path' + (j + 1)).val();
                var image_name = $('#image_name' + (j + 1)).val();
                var image = image_path + image_name;
                if (image != "") {
                    image = image.replace(/\\/g, "/");
                }
                // alert("http://localhost:8080/APL/DealersOrderController?getImage=" + image + "");
                //                                                                        $('.img-fluid' + (j + 1)).attr("src", "http://120.138.10.146:8080/APL/DealersOrderController?getImage=" + image + "");
                $('.img-fluid' + (j + 1)).attr("src", "http://" + IMAGE_URL + "/APL/DealersOrderController?getImage=" + image + "");

                var stock_quantity = $('#stock_quantity' + (j + 1)).val();
                if (stock_quantity == 0) {
                    $('#msg_div' + (j + 1)).show();
                    $('#add' + (j + 1)).attr('disabled', true);
                } else {
                    $('#msg_div' + (j + 1)).hide();
                    $('#add' + (j + 1)).attr('disabled', false);
                }
            }
        });


        function mapItemWithDealer(model, model_id, item_name, org_office_id) {
            var qty;
            $.ajax({
                url: "DealerItemMapController",
                dataType: "json",
                data: {task: "mapWithDealer", model_id: model_id, model_name: model, item_name: item_name, org_office_id: org_office_id},
                success: function (data) {
                    console.log(data.msg);
                    if (data.msg != '') {
                        $('#msg').text(data.msg);
                        $('.myAlertBox').show();
                        setTimeout(function () {
                            $('#msg').fadeOut('fast');
                        }, 2000);
//                                window.location.reload();
                    } else {
                        $('.myAlertBox').hide();

                    }
                }
            });
        }


        function unMapItemWithDealer(dealer_item_map_id) {
            var qty;
            $.ajax({
                url: "DealerItemMapController",
                dataType: "json",
                data: {task: "deleteMapping", dealer_item_map_id: dealer_item_map_id},
                success: function (data) {
                    console.log(data.msg);
                    if (data.msg != '') {
                        $('#msg').text(data.msg);
                        $('.myAlertBox').show();
                        setTimeout(function () {
                            $('#msg').fadeOut('fast');
                        }, 2000);
//                                window.location.reload();
                    } else {
                        $('.myAlertBox').hide();
                    }
                }
            });
        }
    </script>
    <%@include file="/CRM Dashboard/CRM_footer.jsp" %>



