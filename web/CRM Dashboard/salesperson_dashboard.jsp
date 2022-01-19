<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>




<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
        <div class="container-fluid">
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
            <!-- Small boxes (Stat box) -->
            <div class="row">
                <div class="col-lg-3 col-6">
                    <!-- small box -->
                    <div class="small-box bg-info">
                        <div class="inner">
                            <h3>${pending_orders}</h3>
                            <p>Pending Order</p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-bag"></i>
                        </div>
                        <a href="ApproveOrdersController" class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
                    </div>
                </div>
                <!-- ./col -->

                <div class="col-lg-3 col-6">
                    <!-- small box -->
                    <div class="small-box bg-warning">
                        <div class="inner">
                            <h3>${approved_orders}</h3>
                            <p>Approved Order</p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-person-add"></i>
                        </div>
                        <a href="ApproveOrdersController" class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
                    </div>
                </div>
                <!-- ./col -->

                <div class="col-lg-3 col-6">
                    <div class="small-box bg-success">
                        <div class="inner">
                            <h3>${sales_enquiries}<!-- <sup style="font-size: 20px">%</sup> --></h3>
                            <p>Sales Enquiry </p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-stats-bars"></i>
                        </div>
                        <a href="ApproveOrdersController?task=sales_enquiry_list" class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
                    </div>
                </div>
                <div class="col-lg-3 col-6">
                    <!-- small box -->
                    <div class="small-box bg-danger">
                        <div class="inner">
                            <h3>${complaint_enquiries}</h3>
                            <p>Complaint Enquiry</p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-pie-graph"></i>
                        </div>
                        <a href="ApproveOrdersController?task=complaint_enquiry_list" class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
                    </div>
                </div>
                <!-- ./col -->
            </div>
            <!-- /.row -->
            <!-- Main row -->
            <div class="row">
                <!-- Left col -->
                <section class="col-lg-7 connectedSortable ui-sortable d-none">
                    <div class="card">
                        <div class="card-header ui-sortable-handle" style="cursor: move;">
                            <h3 class="card-title">
                                <i class="fas fa-chart-pie mr-1"></i>
                                Sales
                            </h3>
                        </div>
                        <div class="card-body">
                            <div class="tab-content p-0">
                                <div class="" id="revenue-chart" style="position: relative; height: 300px;"><div class="chartjs-size-monitor"><div class="chartjs-size-monitor-expand"><div class=""></div></div><div class="chartjs-size-monitor-shrink"><div class=""></div></div></div>
                                    <canvas id="revenue-chart-canvas" height="300" style="height: 300px; display: block; width: 577px;" width="577" class="chartjs-render-monitor"></canvas>                         
                                </div>
                                <div class="chart tab-pane" id="sales-chart" style="position: relative; height: 300px;">
                                    <canvas id="sales-chart-canvas" height="0" style="height: 0px; display: block; width: 0px;" class="chartjs-render-monitor" width="0"></canvas>                         
                                </div>  
                            </div>
                        </div>
                    </div>        
                </section>


                <section class="col-lg-6 connectedSortable ui-sortable"> 
                    <div class="card bg-gradient-info">
                        <div class="card-header border-0">
                            <h3 class="card-title">
                                <i class="fas fa-th mr-1"></i>
                                Sales Graph
                            </h3>
                            <div class="card-tools">
                                <button type="button" class="btn bg-info btn-sm" data-card-widget="collapse">
                                    <i class="fas fa-minus"></i>
                                </button>
                                <button type="button" class="btn bg-info btn-sm d-none" data-card-widget="remove">
                                    <i class="fas fa-times"></i>
                                </button>
                            </div>
                        </div>
                        <div class="card-body">
                            <canvas class="chart" id="line-chart" style="min-height: 250px; height: 250px; max-height: 250px; max-width: 100%;"></canvas>
                        </div>              
                    </div>
                    <!-- /.card -->

                </section>

                <!--                <section class="col-lg-6  content">
                                    <div class="container-fluid">  
                                        <div class="card-header border-0">
                                            <h3 class="card-title">
                                                <i class="fas fa-th mr-1"></i>
                                                Dealer Location
                                            </h3>
                
                                        </div>
                                        <div class="card-body">
                                            <div id="map" style="height: 280px; width: auto;"></div>
                
                                        </div>
                                    </div>
                                </section>-->
                <!-- right col -->
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
