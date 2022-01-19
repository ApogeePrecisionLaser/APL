<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                            <h3>65</h3>
                            <p>Notification</p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-pie-graph"></i>
                        </div>
                        <a href="NotificationController" class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
                    </div>
                </div>
                <div class="col-lg-2 dashboardTopBox col-6">
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
                </div>
            </div>
            <!-- /.row -->



            <div class="row">
                <div class="col-md-7">

                    <div class="card dashboardBoxShadow">
                        <div class="card-header border-transparent">
                            <h3 class="card-title">Latest Orders</h3>
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
                                            <th>Price</th>
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
                                                <td><a href="tel:+${beanType.requested_to_mobile}">${beanType.requested_to_mobile}</a></td>
                                                <td>${beanType.basic_price}</td>
                                                <td>${beanType.date_time}</td>

                                                <td>
                                                    <c:choose>
                                                        <c:when test="${beanType.status=='Pending'}">
                                                            <span class="badge badge-warning">${beanType.status}</span>
                                                        </c:when>
                                                        <c:when test="${beanType.status=='Approved'}">
                                                            <span class="badge badge-info">${beanType.status}</span>
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
                <div class="col-md-5">

                    <div class="card bg-gradient-info dashboardBoxShadow">
                        <div class="card-header border-0">
                            <h3 class="card-title">
                                <i class="fas fa-th mr-1"></i>
                                Sales Graph
                            </h3>
                            <div class="card-tools">
                                <button type="button" class="btn bg-info btn-sm" data-card-widget="collapse">
                                    <i class="fas fa-minus"></i>
                                </button>
                            </div>
                        </div>
                        <div class="card-body">
                            <canvas class="chart" id="line-chart" style="min-height: 250px; height: 250px; max-height: 250px; max-width: 100%;"></canvas>
                        </div>
                    </div>

                </div>

                <!--                <div class="col-md-4">
                                     <div class="info-box mb-3 bg-warning dashboardBoxShadow">
                                      <span class="info-box-icon"><i class="fas fa-envelope"></i></span>
                                      <div class="info-box-content">
                                        <span class="info-box-text">Complaint Enquiry</span>
                                        <span class="info-box-number">5,200</span>
                                      </div>
                                    </div> 
                                    <div>
                                        <a href="#">
                                            <div class="info-box mb-3 bg-success dashboardBoxShadow">
                                                <span class="info-box-icon"><i class="fas fa-envelope-open-text"></i></span>
                                                <div class="info-box-content">
                                                    <span class="info-box-text">Sales Enquiry</span>
                                                    <span class="info-box-number">92,050</span>
                                                </div>
                                            </div>
                                        </a>
                                    </div>
                                    <div>
                                        <a href="#">
                                            <div class="info-box mb-3 bg-danger dashboardBoxShadow">
                                                <span class="info-box-icon"><i class="fas fa-envelope"></i></span>
                                                <div class="info-box-content">
                                                    <span class="info-box-text">Complaint Enquiry</span>
                                                    <span class="info-box-number">114,381</span>
                                                </div>
                                            </div>
                                        </a>
                                    </div>
                                    <div>
                                        <a href="#">
                                            <div class="info-box mb-3 bg-info dashboardBoxShadow">
                                                <span class="info-box-icon"><i class="far fa-comment"></i></span>
                                                <div class="info-box-content">
                                                    <span class="info-box-text">Support Messages</span>
                                                    <span class="info-box-number">163,921</span>
                                                </div>
                                            </div>
                                        </a>
                                    </div>
                
                
                
                                    <div class="card dashboardBoxShadow">
                                        <div class="card-header">
                                            <h3 class="card-title">Recently Added Products</h3>
                                            <div class="card-tools">
                                                <button type="button" class="btn btn-tool" data-card-widget="collapse">
                                                    <i class="fas fa-minus"></i>
                                                </button>
                                            </div>
                                        </div>
                                        <div class="card-body p-0">
                                            <ul class="products-list product-list-in-card pl-2 pr-2">
                                                <li class="item">
                                                    <div class="product-img">
                                                        <img src="https://adminlte.io/themes/v3/dist/img/default-150x150.png" alt="Product Image" class="img-size-50">
                                                    </div>
                                                    <div class="product-info">
                                                        <a href="javascript:void(0)" class="product-title fontFourteen">Samsung TV
                                                            <span class="badge badge-warning float-right"><i class="fas fa-rupee-sign fontEight"></i>1800</span></a>
                                                        <span class="product-description fontFourteen">
                                                            Samsung 32" 1080p 60Hz LED Smart HDTV.
                                                        </span>
                                                    </div>
                                                </li>
                                                <li class="item">
                                                    <div class="product-img">
                                                        <img src="https://adminlte.io/themes/v3/dist/img/default-150x150.png" alt="Product Image" class="img-size-50">
                                                    </div>
                                                    <div class="product-info">
                                                        <a href="javascript:void(0)" class="product-title fontFourteen">Bicycle
                                                            <span class="badge badge-info float-right"><i class="fas fa-rupee-sign fontEight"></i>700</span></a>
                                                        <span class="product-description fontFourteen">
                                                            26" Mongoose Dolomite Men's 7-speed, Navy Blue.
                                                        </span>
                                                    </div>
                                                </li>
                                                <li class="item">
                                                    <div class="product-img">
                                                        <img src="https://adminlte.io/themes/v3/dist/img/default-150x150.png" alt="Product Image" class="img-size-50">
                                                    </div>
                                                    <div class="product-info">
                                                        <a href="javascript:void(0)" class="product-title fontFourteen">
                                                            Xbox One <span class="badge badge-danger float-right">
                                                                <i class="fas fa-rupee-sign fontEight"></i>350
                                                            </span>
                                                        </a>
                                                        <span class="product-description fontFourteen">
                                                            Xbox One Console Bundle with Halo Master Chief Collection.
                                                        </span>
                                                    </div>
                                                </li>
                                                <li class="item">
                                                    <div class="product-img">
                                                        <img src="https://adminlte.io/themes/v3/dist/img/default-150x150.png" alt="Product Image" class="img-size-50">
                                                    </div>
                                                    <div class="product-info">
                                                        <a href="javascript:void(0)" class="product-title fontFourteen">PlayStation 4
                                                            <span class="badge badge-success float-right"><i class="fas fa-rupee-sign fontEight"></i>399</span></a>
                                                        <span class="product-description fontFourteen">
                                                            PlayStation 4 500GB Console (PS4)
                                                        </span>
                                                    </div>
                                                </li>
                                            </ul>
                                        </div>
                                        <div class="card-footer text-center">
                                            <a href="javascript:void(0)" class="uppercase fontFourteen">View All Products</a>
                                        </div>
                                    </div>
                
                
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="card dashboardBoxShadow">
                                                <div class="card-header">
                                                    <h3 class="card-title">Latest Dealers</h3>
                                                    <div class="card-tools">
                                                        <span class="badge badge-danger">8 New Dealers</span>
                                                        <button type="button" class="btn btn-tool" data-card-widget="collapse">
                                                            <i class="fas fa-minus"></i>
                                                        </button>
                                                    </div>
                                                </div>
                                                <div class="card-body p-0">
                                                    <ul class="users-list clearfix">
                                                        <li>
                                                            <img src="https://adminlte.io/themes/v3/dist/img/user1-128x128.jpg" alt="User Image">
                                                            <a class="users-list-name" href="#">Alexander Pierce</a>
                                                            <span class="users-list-date">Today</span>
                                                        </li>
                                                        <li>
                                                            <img src="https://adminlte.io/themes/v3/dist/img/user8-128x128.jpg" alt="User Image">
                                                            <a class="users-list-name" href="#">Norman</a>
                                                            <span class="users-list-date">Yesterday</span>
                                                        </li>
                                                        <li>
                                                            <img src="https://adminlte.io/themes/v3/dist/img/user7-128x128.jpg" alt="User Image">
                                                            <a class="users-list-name" href="#">Jane</a>
                                                            <span class="users-list-date">12 Jan</span>
                                                        </li>
                                                        <li>
                                                            <img src="https://adminlte.io/themes/v3/dist/img/user6-128x128.jpg" alt="User Image">
                                                            <a class="users-list-name" href="#">John</a>
                                                            <span class="users-list-date">12 Jan</span>
                                                        </li>
                                                        <li>
                                                            <img src="https://adminlte.io/themes/v3/dist/img/user2-160x160.jpg" alt="User Image">
                                                            <a class="users-list-name" href="#">Alexander</a>
                                                            <span class="users-list-date">13 Jan</span>
                                                        </li>
                                                        <li>
                                                            <img src="https://adminlte.io/themes/v3/dist/img/user5-128x128.jpg" alt="User Image">
                                                            <a class="users-list-name" href="#">Sarah</a>
                                                            <span class="users-list-date">14 Jan</span>
                                                        </li>
                                                        <li>
                                                            <img src="https://adminlte.io/themes/v3/dist/img/user4-128x128.jpg" alt="User Image">
                                                            <a class="users-list-name" href="#">Nora</a>
                                                            <span class="users-list-date">15 Jan</span>
                                                        </li>
                                                        <li>
                                                            <img src="https://adminlte.io/themes/v3/dist/img/user3-128x128.jpg" alt="User Image">
                                                            <a class="users-list-name" href="#">Nadia</a>
                                                            <span class="users-list-date">15 Jan</span>
                                                        </li>                      
                                                        <li>
                                                            <img src="https://adminlte.io/themes/v3/dist/img/user1-128x128.jpg" alt="User Image">
                                                            <a class="users-list-name" href="#">Alexander Pierce</a>
                                                            <span class="users-list-date">Today</span>
                                                        </li>
                                                        <li>
                                                            <img src="https://adminlte.io/themes/v3/dist/img/user8-128x128.jpg" alt="User Image">
                                                            <a class="users-list-name" href="#">Norman</a>
                                                            <span class="users-list-date">Yesterday</span>
                                                        </li>
                                                        <li>
                                                            <img src="https://adminlte.io/themes/v3/dist/img/user7-128x128.jpg" alt="User Image">
                                                            <a class="users-list-name" href="#">Jane</a>
                                                            <span class="users-list-date">12 Jan</span>
                                                        </li>
                                                        <li>
                                                            <img src="https://adminlte.io/themes/v3/dist/img/user6-128x128.jpg" alt="User Image">
                                                            <a class="users-list-name" href="#">John</a>
                                                            <span class="users-list-date">12 Jan</span>
                                                        </li>
                                                    </ul>
                                                </div>
                                                <div class="card-footer text-center">
                                                    <a href="javascript:" class="fontFourteen">View All Dealers</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                
                                </div>-->
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




</script>
