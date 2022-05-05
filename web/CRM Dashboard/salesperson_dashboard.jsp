<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>

<style>
    .ui-autocomplete
    {
        max-height:320px;
        overflow: auto;
    }
</style>
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
                        <a href="ApproveOrdersController?status=Pending" class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
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
                        <a href="ApproveOrdersController?status=Approved" class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
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
            <!--            <div class="row">
                             Left col 
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
                                 /.card 
            
                            </section>
            
                                            <section class="col-lg-6  content">
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
                                            </section>
                             right col 
                        </div>-->
        </div>

    </section>
</div>


<div class="modal fade rounded-0 AddEnquiryPopup" id="EnquiryPopup">
    <div class="modal-dialog">
        <div class="modal-content rounded-0">
            <div class="modal-header rounded-0 bg-voilet " >
                <h4 class="modal-title text-white" >Add Enquiry</h4>
                <button type="button" class="close text-white" data-dismiss="modal" aria-hidden="true" id="CloseEnquiryPopup">&times;</button>
            </div>
            <div class="modal-body">
                <c:if test="${not empty message}">
                    <div class="">
                        <c:if test="${msgBgColor=='green'}">
                            <div class="alert alert-success alert-dismissible py-1 px-2 rounded-0 fontFourteen">
                                <button type="button" class="close p-1" data-dismiss="alert">&times;</button>
                                <strong>Success!</strong> ${message}
                            </div>
                        </c:if>
                    </div>
                </c:if>
                <div class="">
                    <div class="alert alert-success alert-dismissible py-1 px-2 rounded-0 fontFourteen"  id="msg" style="display:none">
                        <button type="button" class="close p-1" data-dismiss="alert">&times;</button>
                        <strong>Success!</strong>
                    </div>
                    <div class="alert alert-danger alert-dismissible py-1 px-2 rounded-0 fontFourteen" id="msg" style="display:none">
                        <button type="button" class="close p-1" data-dismiss="alert">&times;</button>
                        <strong>OOps!</strong> 
                    </div>
                </div>
                <div>
                    <form class="myForm" action="SalesEnquiryController" method="post">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group mb-2">
                                    <label>Enquiry Type:<sup class="text-danger">*</sup></label>
                                </div>
                                <div class="d-flex justify-content-start">
                                    <div class="form-group form-check mr-3">
                                        <label class="form-check-label">
                                            <input class="form-check-input" type="radio" name="enquiry_type" id="enquiry_type" value="Sales" checked> Sales
                                        </label>
                                    </div>
                                    <div class="form-group form-check">
                                        <label class="form-check-label">
                                            <input class="form-check-input" type="radio" name="enquiry_type" id="enquiry_type" value="complaint"> Complaint
                                        </label>
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>District:<sup class="text-danger">*</sup></label>
                                    <span role="status" aria-live="polite" class="ui-helper-hidden-accessible"></span>
                                    <input type="text" class="form-control ui-autocomplete-input" required="" name="district" placeholder="Press Space" id="district" autocomplete="off">
                                </div>
                            </div>

                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Name:<sup class="text-danger">*</sup></label>
                                    <input type="text" class="form-control rounded-0" required="" name="sender_name" id="sender_name">
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Mobile:<sup class="text-danger">*</sup></label>
                                    <input type="text" class="form-control rounded-0" required="" name="sender_mob" id="sender_mob" onblur="ValidateNo()">
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label>Enquiry Message:</label>
                                    <textarea class="form-control rounded-0" required="" rows="4" name="enquiry_message" id="enquiry_message"></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12 pl-0">
                            <div class="d-flex justify-content-between">
                                <div class="form-group mb-0">  
                                    <input type="submit" name="task" value="Submit" class="btn myThemeBtn px-4">
                                </div>
                                <div >
                                    <a href="ApproveOrdersController?task=sales_enquiry_list" class="fontFourteen text-right text-underline">Check Enquiry List</a>
                                </div>
                            </div>                    
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
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
</script>
<script>
    $('#EnquiryPopup').modal('show');
    $('#CloseEnquiryPopup').modal('hide');
    // setTimeout(function() {
    //     $('#overlay').modal('hide');
    // }, 5000);


    $("#district").autocomplete({
        source: function (request, response) {
            var random = $('#district').val();
            $.ajax({
                url: "SalesEnquiryController",
                dataType: "json",
                data: {action1: "getDistrict", str: random},
                success: function (data) {
                    console.log(data);
                    response(data.list);
                }, error: function (error) {
                    console.log(error.responseText);
                    response(error.responseText);
                }
            });
        }, autoFocus: true,
        minLength: 0,
        appendTo: "#EnquiryPopup",
        select: function (events, ui) {
            console.log(ui);
            $('#district').val(ui.item.label);
            return false;
        }
    });
    $(function () {
        setTimeout(function () {
            $('.alert-success').fadeOut('fast');
        }, 3000);

        setTimeout(function () {
            $('.alert-danger').fadeOut('fast');
        }, 4000);
    });

    function ValidateNo() {
        var phoneNo = document.getElementById('sender_mob');
        if (phoneNo.value == "" || phoneNo.value == null) {
//                                                        alert("Please enter your Mobile No.");
            $('.alert-danger').show();
            $('.alert-danger').html("Please enter your Mobile No.");
            return false;
        } else if (phoneNo.value.length < 10 || phoneNo.value.length > 10) {
//            alert(phoneNo);
//            alert("Mobile No. is not valid, Please Enter 10 Digit Mobile No.");
            $('.alert-danger').show();
            $('.alert-danger').html('<button type="button" class="close" data-dismiss="alert">&times;</button><strong>Oops!</strong>Mobile No. is not valid, Please Enter 10 Digit Mobile No.');
            return false;
        } else {
            $('.alert-danger').hide();
        }
        return true;
    }
</script>