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
                                <span class="info-box-number">${allProducts}
                                    <input type="hidden" name="product_count" id="product_count" value="4"></span>
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


            <!--            <div class="row">
                            <div class="col-12">
                                <div class="card card-primary card-outline dashboardBoxShadow">
                                    <div class="card-header">
                                        <h3 class="card-title">
                                            <i class="far fa-chart-bar"></i>
                                            Our Sales
                                        </h3>
                                        <div class="card-tools">
                                            Real time
                                            <div class="btn-group" id="realtime" data-toggle="btn-toggle">
                                                <button type="button" class="btn btn-default btn-sm active" data-toggle="on">On</button>
                                                <button type="button" class="btn btn-default btn-sm" data-toggle="off">Off</button>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="card-body">
                                        <div id="interactive" style="height: 300px;"></div>
                                    </div>
                                </div>
                            </div>
                        </div>-->

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

                    <div class="row">
                        <div class="col-md-6">           
                            <div class="card dashboardBoxShadow">
                                <div class="card-header">
                                    <h3 class="card-title"><i class="far fa-chart-bar"></i> Sales Enquiry</h3>
                                    <div class="card-tools">
                                        <button type="button" class="btn btn-tool" data-card-widget="collapse">
                                            <i class="fas fa-minus"></i>
                                        </button>
                                    </div>
                                </div>
                                <div class="card-body pt-2">
                                    <!--                                    <div class="">
                                                                            <div class="form-group text-right mb-0">
                                                                                <div class="custom-control custom-switch">
                                                                                    <input type="checkbox" class="custom-control-input sales_enquiry_source" id="customSwitch1" onclick="filterQueries()">
                                                                                    <label class="custom-control-label fontFourteen" for="customSwitch1">India Mart</label>
                                                                                </div>
                                                                            </div>
                                                                        </div>-->

                                    <div class="salesComplaintFilter">                      
                                        <div class="form-group form-check d-inline mr-1">
                                            <label class="form-check-label">
                                                <input class="form-check-input " type="radio" name="salesReport" checked value="All" onclick="filterQueries(this.value)"><span>All</span>
                                            </label>
                                        </div>
                                        <div class="form-group form-check d-inline mr-1">
                                            <label class="form-check-label">
                                                <input class="form-check-input " type="radio" name="salesReport" value="IndiaMART" onclick="filterQueries(this.value)"><span>IndiaMart</span>
                                            </label>
                                        </div>
                                        <div class="form-group form-check d-inline mr-1">
                                            <label class="form-check-label">
                                                <input class="form-check-input " type="radio" name="salesReport" value="Other" onclick="filterQueries(this.value)"><span>Other</span>
                                            </label>
                                        </div>                      
                                    </div>
                                    <canvas id="salesEnquiryChart" style="min-height: 250px; height: 250px; max-height: 250px; max-width: 100%;"></canvas>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">           
                            <div class="card dashboardBoxShadow">
                                <div class="card-header">
                                    <h3 class="card-title"><i class="far fa-chart-bar"></i> Complaint Enquiry</h3>
                                    <div class="card-tools">
                                        <button type="button" class="btn btn-tool" data-card-widget="collapse">
                                            <i class="fas fa-minus"></i>
                                        </button>
                                    </div>
                                </div>
                                <div class="card-body">
                                    <canvas id="complaintEnquiryChart" style="min-height: 260px; height: 260px; max-height: 260px; max-width: 100%;"></canvas>
                                </div>
                            </div>
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
                        <a href="SupportMessagesController">
                            <div class="info-box mb-3 bg-info dashboardBoxShadow">
                                <span class="info-box-icon"><i class="far fa-comment"></i></span>
                                <div class="info-box-content">
                                    <span class="info-box-text">Support Messages</span>
                                    <span class="info-box-number">${supportMessages}</span>
                                </div>
                            </div>
                        </a>
                    </div>
                    <div class="card card-primary card-outline dashboardBoxShadow">
                        <div class="card-header">
                            <h3 class="card-title">
                                <i class="far fa-chart-bar"></i>
                                Monthly Sales Graph
                            </h3>
                            <div class="card-tools">
                                <button type="button" class="btn btn-tool" data-card-widget="collapse">
                                    <i class="fas fa-minus"></i>
                                </button>
                            </div>
                        </div>
                        <div class="card-body">
                            <div id="bar-chart" style="height: 300px;"></div>
                        </div>
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
                                <c:forEach var="beanType" items="${requestScope['allModels']}"
                                           varStatus="loopCounter" end="3">
                                    <li class="item">
                                        <div class="product-img">
                                            <input type="hidden" name="image_path" id="image_path${loopCounter.count}" value="${beanType.image_path}">
                                            <input type="hidden" name="image_name" id="image_name${loopCounter.count}" value="${beanType.image_name}">
                                            <img src=""  class="img-size-50 product_img${loopCounter.count }">
                                        </div>
                                        <div class="product-info">
                                            <a href="javascript:void(0)" class="product-title fontFourteen">${beanType.model}
                                                <span class="badge badge-warning float-right">
                                                    <i class="fas fa-rupee-sign fontEight"></i> ${beanType.basic_price}</span></a>
                                            <span class="product-description fontFourteen">
                                                ${beanType.item_name} / ${beanType.manufacturer_name}
                                            </span>
                                        </div>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                        <div class="card-footer text-center">
                            <!--<a href="javascript:void(0)" class="uppercase fontFourteen">View All Products</a>-->
                        </div>
                    </div>


                    <div class="row">
                        <div class="col-md-12">
                            <div class="card dashboardBoxShadow">
                                <div class="card-header">
                                    <h3 class="card-title">Latest Dealers</h3>
                                    <div class="card-tools">
                                        <span class="badge badge-danger"></span>
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
        for (var j = 0; j < count; j++) {
            var key_person_id = $('#key_person_id' + (j + 1)).val();
            $('.usr_image' + (j + 1)).attr("src", "http://" + IMAGE_URL + "/APL/CRMDashboardController?task=viewImage&type=ph&key_person_id=" + key_person_id);

        }


        var product_count = $('#product_count').val();

        for (var k = 0; k < product_count; k++) {
            var image_path = $('#image_path' + (k + 1)).val();
            var image_name = $('#image_name' + (k + 1)).val();
            var image = image_path + image_name;
            if (image != "") {
                image = image.replace(/\\/g, "/");
            }
            $('.product_img' + (k + 1)).attr("src", "http://" + IMAGE_URL + "/APL/DealersOrderController?getImage=" + image + "");
        }
    });
</script>

<script>


//    $(document).ready(function () {

//        var sales_enquiries;
//        $.ajax({
//            type: "POST",
//            url: 'CRMDashboardController?task=getAllSalesEnquiries',
//            dataType: 'json',
//            success: function (data) {
//                sales_enquiries = data.sales_enquiries;
////                alert(sales_enquiries);
////                alert(sales_enquiries.length);
////                alert(sales_enquiries[0]["resolved_enquiry_count"]);
//
//
//                if (sales_enquiries.length > 0) {
//                    var colors = ["#3c8dbc", "#0073b7", "#00c0ef", "#e8c3b9"];
//                    var labels = ["Resolved", "Unresolved", "Assigned", "Pending"];
//                    var datas = [sales_enquiries[0]["resolved_enquiry_count"], sales_enquiries[0]["unresolved_enquiry_count"], sales_enquiries[0]["assigned_enquiry_count"], sales_enquiries[0]["pending_enquiry_count"]];
//                    var saleEnquiryData = [
//                        {
//                            label: labels[0],
//                            data: datas[0],
//                            color: colors[0]
//                        },
//                        {
//                            label: labels[1],
//                            data: datas[1],
//                            color: colors[1]
//                        },
//                        {
//                            label: labels[2],
//                            data: datas[2],
//                            color: colors[2]
//                        },
//                        {
//                            label: labels[3],
//                            data: datas[3],
//                            color: colors[3]
//                        }
//                    ]
//                    $.plot('#salesEnquiry', saleEnquiryData, {
//                        series: {
//                            pie: {
//                                show: true,
//                                radius: 1,
//                                innerRadius: 0.5,
//                                label: {
//                                    show: true,
//                                    radius: 2 / 3,
//                                    formatter: labelFormatter,
//                                    threshold: 0.1,
//                                    text: 'hello'
//                                }
//
//                            }
//                        },
//                        legend: {
//                            show: false
//                        }
//                    })

//
//                    anychart.onDocumentReady(function () {
//                        // create pie chart with passed data
//                        var chart = anychart.pie([
//                            ['Resolved' + "(" + datas[0] + ")", datas[0]],
//                            ['Unresolved' + "(" + datas[1] + ")", datas[1]],
//                            ['Assigned' + "(" + datas[2] + ")", datas[2]],
//                            ['Pending' + "(" + datas[3] + ")", datas[3]]
//                        ]);
//                        var total = parseInt(datas[0]) + parseInt(datas[1]) + parseInt(datas[2]) + parseInt(datas[3]);
//
//                        // creates palette
//                        var palette = anychart.palettes.distinctColors();
//                        palette.items([
//                            {color: '#7d9db6'},
//                            {color: '#8db59d'},
//                            {color: '#f38367'},
//                            {color: '#b97792'}
//                        ]);
//
//                        // set chart radius
//                        chart
//                                .radius('43%')
//                                // create empty area in pie chart
//                                .innerRadius('60%')
//                                // set chart palette
//                                .palette(palette);
//
//                        // set outline settings
//                        chart
//                                .outline()
//                                .width('3%')
//                                .fill(function () {
//                                    return anychart.color.darken(this.sourceColor, 0.25);
//                                });
//
//                        // format tooltip
//                        chart.tooltip().format('Percent Value: {%PercentValue}%');
//
//                        // create standalone label and set label settings
//                        var label = anychart.standalones.label();
//                        label
//                                .enabled(true)
//                                .text(total)
//                                .width('100%')
//                                .height('100%')
//                                .adjustFontSize(true, true)
//                                .minFontSize(10)
//                                .maxFontSize(25)
//                                .fontColor('#60727b')
//                                .position('center')
//                                .anchor('center')
//                                .hAlign('center')
//                                .vAlign('middle');
//
//                        // set label to center content of chart
//                        chart.center().content(label);
//
//                        // set container id for the chart
//                        chart.container('container1');
//                        // initiate chart drawing
//                        chart.draw();
//                    });
//                }
//            },
//        });
//    })
</script>
<script>
    $(function () {
        var sales_enquiries;
        var sales_enquiry_source = "";
//        sales_enquiry_source = $('.sales_enquiry_source').val();
//        if (sales_enquiry_source == 'on') {
//            sales_enquiry_source = "";
//        }
//        if (sales_enquiry_source == 'off') {
//            sales_enquiry_source = "IndiaMART";
//        }
        $.ajax({
            type: "POST",
            url: 'CRMDashboardController?task=getAllSalesEnquiries',
            data: {sales_enquiry_source: sales_enquiry_source},
            dataType: 'json',
            success: function (data) {
                sales_enquiries = data.sales_enquiries;
                if (sales_enquiries.length > 0) {
                    var total = parseInt(sales_enquiries[0]["resolved_enquiry_count"]) + parseInt(sales_enquiries[0]["unresolved_enquiry_count"])
                            + parseInt(sales_enquiries[0]["assigned_enquiry_count"]) + parseInt(sales_enquiries[0]["pending_enquiry_count"]);
                    var colors = ["#00a65a", "#f56954", "#00c0ef", "#f39c12", "#6c757d"];
                    var labels = ["Resolved" + "(" + sales_enquiries[0]["resolved_enquiry_count"] + ")", "Failed" + "(" + sales_enquiries[0]["unresolved_enquiry_count"] + ")", "Assigned" + "(" + sales_enquiries[0]["assigned_enquiry_count"] + ")", "Pending" + "(" + sales_enquiries[0]["pending_enquiry_count"] + ")", "Total" + "(" + total + ")"];
                    var datas = [sales_enquiries[0]["resolved_enquiry_count"], sales_enquiries[0]["unresolved_enquiry_count"], sales_enquiries[0]["assigned_enquiry_count"], sales_enquiries[0]["pending_enquiry_count"], 0];
                    var pieData = {
                        labels: labels,
                        datasets: [
                            {
                                data: datas,
                                backgroundColor: colors,
                            }
                        ]
                    }
                    var pieChartCanvas = $('#salesEnquiryChart').get(0).getContext('2d')
                    var pieData = pieData;
                    var pieOptions = {
                        maintainAspectRatio: false,
                        responsive: true,
                    }
                    new Chart(pieChartCanvas, {
                        type: 'pie',
                        data: pieData,
                        options: pieOptions
                    })
                }
            }
        });
    })
</script>
<script>

    function filterQueries(value) {
        var sales_enquiries;
        var sales_enquiry_source = "";
        sales_enquiry_source = value;
//        alert(sales_enquiry_source);

//        alert(sales_enquiry_source);
        $.ajax({
            type: "POST",
            url: 'CRMDashboardController?task=getAllSalesEnquiries',
            data: {sales_enquiry_source: sales_enquiry_source},
            dataType: 'json',
            success: function (data) {
                sales_enquiries = data.sales_enquiries;
                if (sales_enquiries.length > 0) {
                    var total = parseInt(sales_enquiries[0]["resolved_enquiry_count"]) + parseInt(sales_enquiries[0]["unresolved_enquiry_count"])
                            + parseInt(sales_enquiries[0]["assigned_enquiry_count"]) + parseInt(sales_enquiries[0]["pending_enquiry_count"]);
                    var colors = ["#00a65a", "#f56954", "#00c0ef", "#f39c12", "#6c757d"];
                    var labels = ["Resolved" + "(" + sales_enquiries[0]["resolved_enquiry_count"] + ")", "Failed" + "(" + sales_enquiries[0]["unresolved_enquiry_count"] + ")", "Assigned" + "(" + sales_enquiries[0]["assigned_enquiry_count"] + ")", "Pending" + "(" + sales_enquiries[0]["pending_enquiry_count"] + ")", "Total" + "(" + total + ")"];
                    var datas = [sales_enquiries[0]["resolved_enquiry_count"], sales_enquiries[0]["unresolved_enquiry_count"], sales_enquiries[0]["assigned_enquiry_count"], sales_enquiries[0]["pending_enquiry_count"], 0];
                    var pieData = {
                        labels: labels,
                        datasets: [
                            {
                                data: datas,
                                backgroundColor: colors,
                            }
                        ]
                    }
                    var pieChartCanvas = $('#salesEnquiryChart').get(0).getContext('2d')
                    var pieData = pieData;
                    var pieOptions = {
                        maintainAspectRatio: false,
                        responsive: true,
                    }
                    new Chart(pieChartCanvas, {
                        type: 'pie',
                        data: pieData,
                        options: pieOptions
                    })
                }
            }
        });
    }
</script>

<script>
    $(function () {
        var complaint_enquiries;
        $.ajax({
            type: "POST",
            url: 'CRMDashboardController?task=getAllComplaintEnquiries',
            dataType: 'json',
            success: function (data) {
                complaint_enquiries = data.complaint_enquiries;
                if (complaint_enquiries.length > 0) {
                    var total = parseInt(complaint_enquiries[0]["resolved_enquiry_count"]) + parseInt(complaint_enquiries[0]["unresolved_enquiry_count"])
                            + parseInt(complaint_enquiries[0]["assigned_enquiry_count"]) + parseInt(complaint_enquiries[0]["pending_enquiry_count"]);
                    var colors = ["#00a65a", "#f56954", "#00c0ef", "#f39c12", "#6c757d"];
                    var labels = ["Resolved" + "(" + complaint_enquiries[0]["resolved_enquiry_count"] + ")", "Failed" + "(" + complaint_enquiries[0]["unresolved_enquiry_count"] + ")", "Assigned" + "(" + complaint_enquiries[0]["assigned_enquiry_count"] + ")", "Pending" + "(" + complaint_enquiries[0]["pending_enquiry_count"] + ")", "Total" + "(" + total + ")"];
                    var datas = [complaint_enquiries[0]["resolved_enquiry_count"], complaint_enquiries[0]["unresolved_enquiry_count"], complaint_enquiries[0]["assigned_enquiry_count"], complaint_enquiries[0]["pending_enquiry_count"], 0];
                    var pieData = {
                        labels: labels,
                        datasets: [
                            {
                                data: datas,
                                backgroundColor: colors,
                            }
                        ]
                    }
                    var pieChartCanvas = $('#complaintEnquiryChart').get(0).getContext('2d')
                    var pieData = pieData;
                    var pieOptions = {
                        maintainAspectRatio: false,
                        responsive: true,
                    }
                    new Chart(pieChartCanvas, {
                        type: 'pie',
                        data: pieData,
                        options: pieOptions
                    })
                }
            }
        });
    })
</script>

<script>


    $(function () {
//    var data        = [],
//        totalPoints = 100
//    function getRandomData() {
//
//      if (data.length > 0) {
//        data = data.slice(1)
//      }
//      while (data.length < totalPoints) {
//
//        var prev = data.length > 0 ? data[data.length - 1] : 50,
//            y    = prev + Math.random() * 10 - 5
//
//        if (y < 0) {
//          y = 0
//        } else if (y > 100) {
//          y = 100
//        }
//
//        data.push(y)
//      }
//      var res = []
//      for (var i = 0; i < data.length; ++i) {
//        res.push([i, data[i]])
//      }
//
//      return res
//    }
//
//    var interactive_plot = $.plot('#interactive', [
//        {
//          data: getRandomData(),
//        }
//      ],
//      {
//        grid: {
//          borderColor: '#f3f3f3',
//          borderWidth: 1,
//          tickColor: '#f3f3f3'
//        },
//        series: {
//          color: '#3c8dbc',
//          lines: {
//            lineWidth: 2,
//            show: true,
//            fill: true,
//          },
//        },
//        yaxis: {
//          min: 0,
//          max: 100,
//          show: true
//        },
//        xaxis: {
//          show: true
//        }
//      }
//    )
//
//    var updateInterval = 500 //Fetch data ever x milliseconds
//    var realtime       = 'on' //If == to on then fetch data every x seconds. else stop fetching
//    function update() {
//
//      interactive_plot.setData([getRandomData()])
//
//      // Since the axes don't change, we don't need to call plot.setupGrid()
//      interactive_plot.draw()
//      if (realtime === 'on') {
//        setTimeout(update, updateInterval)
//      }
//    }
//
//    //INITIALIZE REALTIME DATA FETCHING
//    if (realtime === 'on') {
//      update()
//    }
//    //REALTIME TOGGLE
//    $('#realtime .btn').click(function () {
//      if ($(this).data('toggle') === 'on') {
//        realtime = 'on'
//      }
//      else {
//        realtime = 'off'
//      }
//      update()
//    })
        /*
         * END INTERACTIVE CHART
         */

        var bar_data = {
            data: [[1, 10], [2, 8], [3, 4], [4, 13], [5, 17], [6, 9]],
            bars: {show: true}
        }
        $.plot('#bar-chart', [bar_data], {
            grid: {
                borderWidth: 1,
                borderColor: '#f3f3f3',
                tickColor: '#f3f3f3'
            },
            series: {
                bars: {
                    show: true, barWidth: 0.5, align: 'center',
                },
            },
            colors: ['#3c8dbc'],
            xaxis: {
                ticks: [[1, 'Jan'], [2, 'Feb'], [3, 'Mar'], [4, 'Apr'], [5, 'May'], [6, 'June']]
            }
        })
    })

    function labelFormatter(label, series) {
        return '<div style="font-size:13px; text-align:center; padding:2px; color: #fff; font-weight: 600;">'
                + label
                + '<br>'
                + Math.round(series.percent) + '%</div>'
    }
</script>