<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>




<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <h1 class="m-0">Dashboard</h1>
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
            <!-- Info boxes -->
            <div class="row headerTopCount">
                <div class="col-12 col-sm-6 col-md-3">
                    <a href="OrdersHistoryController" class="linkWrap">
                        <div class="info-box dashboardBoxShadow">
                            <span class="info-box-icon bg-warning elevation-1"><i class="fas fa-layer-group"></i></span>
                            <div class="info-box-content">
                                <span class="info-box-text">Orders</span>
                                <span class="info-box-number">
                                    ${total_orders}
                                    <!-- <small>%</small> -->
                                </span>
                            </div>
                        </div>
                    </a>
                </div>
                <div class="col-12 col-sm-6 col-md-3">
                    <a href="#" class="linkWrap">
                        <div class="info-box mb-3 dashboardBoxShadow">
                            <span class="info-box-icon bg-success elevation-1"><i class="fas fa-shopping-cart"></i></span>
                            <div class="info-box-content">
                                <span class="info-box-text">Sales</span>
                                <span class="info-box-number">760</span>
                            </div>
                        </div>
                    </a>
                </div>
                <div class="clearfix hidden-md-up"></div>
                <div class="col-12 col-sm-6 col-md-3">
                    <a href="#" class="linkWrap">
                        <div class="info-box mb-3 dashboardBoxShadow">
                            <span class="info-box-icon bg-danger elevation-1"><i class="fas fa-cubes"></i></span>
                            <div class="info-box-content">
                                <span class="info-box-text">Products</span>
                                <span class="info-box-number">760</span>
                            </div>
                        </div>
                    </a>
                </div>
                <div class="col-12 col-sm-6 col-md-3">
                    <a href="DealersController" class="linkWrap">
                        <div class="info-box mb-3 dashboardBoxShadow">
                            <span class="info-box-icon bg-info elevation-1"><i class="fas fa-users"></i></span>
                            <div class="info-box-content">
                                <span class="info-box-text">Dealers</span>
                                <span class="info-box-number">${total_dealers}</span>
                            </div>
                        </div>
                    </a>
                </div>
            </div>



            <div class="row">
                <div class="col-md-8">

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
                                            <th>Dealer</th>
                                            <th>Contact No</th>
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
                                                <td>${beanType.org_office}</td>
                                                <td><a href="tel:+${beanType.requested_by_mobile}">${beanType.requested_by_mobile}</a></td>
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

                    <div class="card col-lg-12 dashboardBoxShadow">
                        <div class="card-header border-0 px-2">
                            <h3 class="card-title">
                                <i class="fas fa-th"></i>
                                Dealers Location
                            </h3>
                            <div class="card-tools">
                                <button type="button" class="btn btn-tool" data-card-widget="collapse">
                                    <i class="fas fa-minus"></i>
                                </button>
                            </div>
                        </div>
                        <div class="card-body px-1 pt-1">
                            <div class="img-thumbnail">
                                <div id="map" style="height: 373px; width: auto;"></div>
                            </div>
                        </div>
                    </div>

                </div>

                <div class="col-md-4">
                    <!-- <div class="info-box mb-3 bg-warning dashboardBoxShadow">
                      <span class="info-box-icon"><i class="fas fa-envelope"></i></span>
                      <div class="info-box-content">
                        <span class="info-box-text">Complaint Enquiry</span>
                        <span class="info-box-number">5,200</span>
                      </div>
                    </div> -->
                    <div>
                        <a href="SalesEnquiryController?task=sales_enquiry_list">
                            <div class="info-box mb-3 bg-success dashboardBoxShadow">
                                <span class="info-box-icon"><i class="fas fa-envelope-open-text"></i></span>
                                <div class="info-box-content">
                                    <span class="info-box-text">Sales Enquiry</span>
                                    <span class="info-box-number">${sales_enquiries}</span>
                                </div>
                            </div>
                        </a>
                    </div>
                    <div>
                        <a href="SalesEnquiryController?task=complaint_enquiry_list">
                            <div class="info-box mb-3 bg-danger dashboardBoxShadow">
                                <span class="info-box-icon"><i class="fas fa-envelope"></i></span>
                                <div class="info-box-content">
                                    <span class="info-box-text">Complaint Enquiry</span>
                                    <span class="info-box-number">${complaint_enquiries}</span>
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
                                        <input type="hidden" name="count" id="count" value="12">
                                        <c:forEach var="beanType1" items="${requestScope['latest_dealers']}"
                                                   varStatus="loopCounter"> 

                                            <li>
                                                <input type="hidden" name="key_person_id" id="key_person_id${loopCounter.count}" value="${beanType1.key_person_id}">
                                                <img src="" alt="User Image" class="usr_image${loopCounter.count}">
                                                <a class="users-list-name" href="DealersController?task=viewDealerDetails&org_office_id=${beanType1.org_office_id}&key_person_id=${beanType1.key_person_id}">${beanType1.key_person_name}</a>
                                                <!--<span class="users-list-date">Today</span>-->
                                            </li>
                                        </c:forEach>

                                    </ul>
                                </div>
                                <div class="card-footer text-center">
                                    <a href="DealersController" class="fontFourteen">View All Dealers</a>
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
<!--<script src="CRM Dashboard/assets2/js/pages/dashboard.js"></script>-->
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDOT5yBi-LAmh9P2X0jQmm4y7zOUaWRXI0&sensor=false"></script>


<script type="text/javascript">
    $(document).ready(function () {
//                                                            var locations = [
//                                                                ['Raj Ghat', 28.648608, 77.250925, 1],
//                                                                ['Purana Qila', 28.618174, 77.242686, 2],
//                                                                ['Red Fort', 28.663973, 77.241656, 3],
//                                                                ['India Gate', 28.620585, 77.228609, 4],
//                                                                ['Jantar Mantar', 28.636219, 77.213846, 5],
//                                                                ['Akshardham', 28.622658, 77.277704, 6]
//                                                            ];
        var latitude = [];
        var longitude = [];
        var dealer_office_name = [];
        var mobile = [];
        var email = [];
        var person_name = [];
        var org_office_id = [];
        var key_person_id = [];
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
                    org_office_id[i] = dealer_data[i]["org_office_id"];
                    key_person_id[i] = dealer_data[i]["key_person_id"];
                    // alert(org_office_id[i]);
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
                                    window.location.href = "DealersController?task=viewDealerDetails&org_office_id=" + org_office_id[i] + "&key_person_id=" + key_person_id[i];

                                }
                            })(marker, i)
                            )

                }



            }
        });



    });

</script>
<script>

    $(document).ready(function () {
        var count = $('#count').val();
        //   alert(count);
        for (var j = 0; j < count; j++) {
            var key_person_id = $('#key_person_id' + (j + 1)).val();
            //alert(key_person_id);
            $('.usr_image' + (j + 1)).attr("src", "http://" + IMAGE_URL + "/APL/CRMDashboardController?task=viewImage&type=ph&key_person_id=" + key_person_id);

        }
    });
</script>