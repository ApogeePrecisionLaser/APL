<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/CRM Dashboard/CRM_header.jsp" %>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
        <div class="container-fluid mt-2">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <h1 class="m-0 text-dark">Dashboard</h1>
                </div><!-- /.col -->
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="CRMDashboardController">Home</a></li>
                        <li class="breadcrumb-item active">Dashboard</li>
                        <input type="hidden" name="email" id="email" value="${email}">
                        <input type="hidden" name="password" id="password" value="${password}">
                    </ol>
                </div><!-- /.col -->
            </div><!-- /.row -->
        </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->

    <!-- Main content -->
    <section class="content">
        <div class="container-fluid">
            <div class="row1 d-flex justify-content-around flex-wrap">
                <div class="col-lg-2 dashboardTopBox col-6">
                    <div class="small-box bg-info">
                        <div class="inner">
                            <h3>${pending_orders}</h3>
                            <p>Pending Order</p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-bag"></i>
                        </div>
                        <a href="PendingOrdersController" class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
                    </div>
                </div>
                <div class="col-lg-2 dashboardTopBox col-6">
                    <div class="small-box bg-success">
                        <div class="inner">
                            <h3>${sales_enquiries}<!-- <sup style="font-size: 20px">%</sup> --></h3>
                            <p>Sales Enquiry </p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-stats-bars"></i>
                        </div>
                        <a href="DealersOrderController?task=sales_enquiry_list" class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
                    </div>
                </div>
                <div class="col-lg-2 dashboardTopBox col-6">
                    <div class="small-box bg-warning">
                        <div class="inner">
                            <h3>${complaint_enquiries}</h3>
                            <p>Customer Complaint</p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-person-add"></i>
                        </div>
                        <a href="DealersOrderController?task=complaint_enquiry_list" class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
                    </div>
                </div>
                <div class="col-lg-2 dashboardTopBox col-6">
                    <div class="small-box bg-danger">
                        <div class="inner">
                            <h3>${pending_news}</h3>
                            <p>News</p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-pie-graph"></i>
                        </div>
                        <a href="NotificationController" class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
                    </div>
                </div>
                <!--                <div class="col-lg-2 dashboardTopBox col-6">
                                    <div class="small-box bg-voilet">
                                        <div class="inner">
                                            <h3 class="text-white">65</h3>
                                            <p class="text-white">Total Sale</p>
                                        </div>
                                        <div class="icon">
                                            <i class="ion ion-pie-graph"></i>
                                        </div>
                                        <a href="NotificationController" class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
                                    </div>
                                </div>-->
            </div>
            <!-- /.row -->



            <div class="row">
                <div class="col-md-7">

                    <div class="card dashboardBoxShadow">
                        <div class="card-header border-transparent">
                            <h3 class="card-title">Latest Orders</h3>
                            <input type="hidden" name="dashboard_pending_orders_count" id="dashboard_pending_orders_count" value="${dashboard_pending_orders_count}">

                            <div class="card-tools">
                                <button type="button" class="btn btn-tool" data-card-widget="collapse">
                                    <i class="fas fa-minus"></i>
                                </button>
                            </div>
                        </div>
                        <div class="card-body p-0">
                            <div class="table-responsive dashboardTbl">
                                <table class="table m-0 ">
                                    <thead>
                                        <tr>
                                            <th>Order ID</th>
                                            <th>Contacted Person</th>
                                            <th>Contact To</th>
                                            <th>Price (<i class="fas fa-rupee-sign fontTen"></i>)</th>
                                            <th>Date</th>
                                            <th>Status</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="beanType" items="${requestScope['dashboard_pending_orders']}"
                                                   varStatus="loopCounter">
                                            <tr>
                                                <td><a href="OrdersHistoryController?task=viewOrderDetails&order_table_id=${beanType.order_table_id}">${beanType.order_no}</a></td>
                                                <td>${beanType.requested_to}</td>
                                                <td><a href="tel:+${beanType.requested_to_mobile}" class="nowrap"><i class="fas fa-mobile-alt"></i> ${beanType.requested_to_mobile}</a></td>
                                                <td id="price${loopCounter.count}">
                                                    ${beanType.basic_price}
                                                </td>
                                                <td>${beanType.date_time}</td>

                                                <td>
                                                    <c:choose>
                                                        <c:when test="${beanType.status=='Pending'}">
                                                            <span class="badge badge-warning">Order Placed</span>
                                                        </c:when>
                                                        <c:when test="${beanType.status=='Approved'}">
                                                            <span class="badge badge-info">Order Confirmed</span>
                                                        </c:when>
                                                        <c:when test="${beanType.status=='Denied'}">
                                                            <span class="badge badge-danger">${beanType.status}</span>
                                                        </c:when>
                                                        <c:when test="${beanType.status=='Payment Done'}">
                                                            <span class="badge badge-secondary">${beanType.status}</span>
                                                        </c:when>
                                                        <c:when test="${beanType.status=='Delivered'}">
                                                            <span class="badge badge-success">${beanType.status}</span>
                                                        </c:when>
                                                    </c:choose >
                                                </td> 
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- /.table-responsive -->
                        </div>
                        <!-- /.card-body -->
                        <div class="card-footer clearfix">
                            <a href="OrdersHistoryController" class="btn btn-sm btn-info float-left fontFourteen">View All Orders</a>
                            <!-- <a href="javascript:void(0)" class="btn btn-sm btn-secondary float-right">View All Orders</a> -->
                        </div>
                        <!-- /.card-footer -->
                    </div>
                </div>

                <div class="col-lg-5 connectedSortable ui-sortable"> 
                    <div class="card dashboardBoxShadow">
                        <div class="card-header">
                            <h3 class="card-title mb-0">Upcoming News</h3>
                            <div class="card-tools">
                                <button type="button" class="btn btn-tool" data-card-widget="collapse">
                                    <i class="fas fa-minus"></i>
                                </button>
                            </div>
                        </div>
                        <div class="card-body p-0">
                            <div class="px-3">
                                <marquee direction="up" Scrolldelay="300" >
                                    <input type="hidden" name="news_count" id="news_count" value="${pending_news}">

                                    <c:forEach var="beanType" items="${requestScope['pending_news_list']}"
                                               varStatus="loopCounter">
                                        <div>
                                            <div class="mb-2">
                                                <input type="hidden" name="document_name" id="document_name${loopCounter.count}" 
                                                       value="${beanType.document_name}">
                                                <img src=""  class="event_image${loopCounter.count}" style="width:100%;height: 100px;object-fit: cover;">
                                            </div>
                                            <h5 class="font-weight-bold fontFourteen mb-1 text-info">${beanType.title}</h5>
                                            <p class="fontFourteen">${beanType.description}</p>
                                        </div>
                                    </c:forEach>
                                    <!--                                    <div>
                                                                            <div class="mb-2">
                                                                                <img src="CRM Dashboard/assets2/img/product/profileBg.jpg" style="width:100%;height: 100px;object-fit: cover;">
                                                                            </div>
                                                                            <h5 class="font-weight-bold fontFourteen mb-1 text-info">What is Lorem Ipsum?</h5>
                                                                            <p class="fontFourteen">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.</p>
                                                                        </div>-->
                                </marquee>
                            </div>
                        </div>
                        <div class="card-footer text-center">
                            <a href="NotificationController" class="uppercase fontFourteen">View All News</a>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </section>
</div>



<%@include file="/CRM Dashboard/CRM_footer.jsp" %>
<script src="CRM Dashboard/assets2/js/pages/dashboard.js"></script>
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDOT5yBi-LAmh9P2X0jQmm4y7zOUaWRXI0&sensor=false"></script>


<script type="text/javascript">
    $(document).ready(function () {
        var latitude = [];
        var longitude = [];
        var dealer_office_name = [];
        var mobile = [];
        var email = [];
        var person_name = [];
        var dealer_data;
        $.ajax({
            type: "POST",
            url: 'CRMDashboardController?task=getDealerLocation',
            dataType: 'json',
            success: function (data) {
                var map = new google.maps.Map(document.getElementById('map'), {
                    zoom: 7,
                    center: new google.maps.LatLng(28.614884, 77.208917),
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                });
                var infowindow = new google.maps.InfoWindow();
                var marker;
                dealer_data = data.dealers;
                for (var i = 0; i < dealer_data.length; i++) {
                    latitude[i] = dealer_data[i]["latitude"];
                    longitude[i] = dealer_data[i]["longitude"];
                    dealer_office_name[i] = dealer_data[i]["dealer_office_name"];
                    email[i] = dealer_data[i]["email"];
                    mobile[i] = dealer_data[i]["mobile"];
                    person_name[i] = dealer_data[i]["person_name"];
                    if (latitude[i] == '') {
                        latitude[i] = 28.614884;
                        longitude[i] = 77.208917;
                    }
                    console.log(latitude[i]);

                    marker = new google.maps.Marker({
                        position: new google.maps.LatLng(latitude[i], longitude[i]),
                        map: map,
                        title: dealer_office_name[i],
                        // icon: image
                    })

                    google.maps.event.addListener(
                            marker,
                            'click',
                            (function (marker, i) {
                                return function () {
                                    infowindow.setContent('<b><h6>' + dealer_office_name[i] + ' (' + person_name[i] + ')</h6></b></br><b>Email:- </b>' + email[i] + '</br><b>Mobile:- </b>' + mobile[i] + '</br><b>Latitude:- </b>' + latitude[i] + '</br><b>Longitude:- </b>' + longitude[i] + '')

                                    infowindow.open(map, marker)
                                }
                            })(marker, i)
                            )

                }
            }
        });
    });
    $(document).ready(function () {
        var count = $('#news_count').val();
        for (var j = 0; j < count; j++) {
            var document_name = $('#document_name' + (j + 1)).val();
            $('.event_image' + (j + 1)).attr("src", "http://" + IMAGE_URL + "/APL/EventController?task=viewImage&document_name=" + document_name);
        }
    })
    $(document).ready(function () {
        var password = $('#password').val();
        var email = $('#email').val();
        if (email != '' || password != '') {
            sendData(email, password);
        }
    });
    function sendData(email, password) {
        if (Android !== undefined) {
            if (Android.invoke !== undefined) {
                Android.invoke(email, password);
            }
        }
    }

    $(function () {
        var count = $('#dashboard_pending_orders_count').val();
        for (var j = 0; j < count; j++) {
            var price = $('#price' + (j + 1)).text();
            var price1 = convertToCommaSeperate(price);
            $('#price' + (j + 1)).text(price1);
        }


    });
    function convertToCommaSeperate(x) {
        x = x.toString();
        var afterPoint = '';
        if (x.indexOf('.') > 0)
            afterPoint = x.substring(x.indexOf('.'), x.length);
        x = Math.floor(x);
        x = x.toString();
        var lastThree = x.substring(x.length - 3);
        var otherNumbers = x.substring(0, x.length - 3);
        if (otherNumbers != '')
            lastThree = ',' + lastThree;
        var res = otherNumbers.replace(/\B(?=(\d{2})+(?!\d))/g, ",") + lastThree + afterPoint;

        return res;
    }


</script>
