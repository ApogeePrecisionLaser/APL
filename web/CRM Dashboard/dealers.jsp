<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>



<div class="content-wrapper" id="contentWrapper">
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <div class="d-flex">
                        <!--                        <div>
                                                    <a href="DealersOrderController" class="btn btn-primary myNewLinkBtn">Create Order</a>
                                                </div>-->
                        <div>
                            <h1>Dealer List</h1>
                        </div>
                        <div class="position-relative">
                            <div class="alert alert-success alert-dismissible myAlertBox" style="display:none">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong>Success!</strong> 
                            </div>
                        </div>
                    </div>  
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="CRMDashboardController">Dashboard</a></li>
                        <li class="breadcrumb-item active">Dealers</li>
                    </ol>
                </div>
            </div>
        </div><!-- /.container-fluid -->
    </section>
    <section class="content">
        <div class="container-fluid">              
            <div class="row mt-0">
                <div class="col-md-12">
                    <div class="card card-primary card-outline">            
                        <div class="card-body">
                            <div>
                                <div class="table-responsive tableScrollWrap noWrapTable" >
                                    <table class="table table-striped1 mainTable" id="mytable" >
                                        <thead>
                                            <tr>                                
                                                <th>S.No.</th>
                                                <th>Org. Office Name</th>
                                                <th>Office GST</th>
                                                <!--<th>Office Address</th>-->
                                                <!--<th>Office Email</th>-->
                                                <th>Office Mobile</th>
                                                <th>Person Name</th>
                                                <!--<th>Person Address</th>-->
                                                <th>Person Mobile Number</th>
                                                <!--<th>Person Email</th>-->
                                                <th></th>

                                            </tr>
                                        </thead>
                                        <tbody>

                                            <c:forEach var="beanType" items="${requestScope['list']}" varStatus="loopCounter">
                                                <tr
                                                    onclick="fillColumn();">
                                                    <td>${loopCounter.count }</td>
                                                    <td>${beanType.org_office_name}</td>
                                                    <td>${beanType.gst_number}</td>
                                                    <!--<td>${beanType.off_address_line1}</td>-->
                                                    <!--<td>${beanType.off_email_id1}</td>-->
                                                    <td>${beanType.off_mobile_no1}</td>
                                                    <td>${beanType.key_person_name}</td>
                                                    <!--<td>${beanType.kp_address_line1}</td>-->
                                                    <td>${beanType.kp_mobile_no1}</td>
                                                    <!--<td>${beanType.kp_email_id1}</td>-->
                                                    <td>
                                                        <div>
                                                            <a href="DealersController?task=viewDealerDetails&org_office_id=${beanType.org_office_id}&key_person_id=${beanType.key_person_id}" class="btn far fa-eye actionEdit" title="View Dealer Detail"></a>
                                                            <a class="btn actionView" title="Map Items" href="DealerItemMapController?org_office_id=${beanType.org_office_id}">Map Items</a>
                        <div class="position-relative">
                            <div class="alert alert-success alert-dismissible myAlertBox" style="display:none">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong>Success!</strong> 
                            </div>
                        </div>
                    </div>  
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="CRMDashboardController">Dashboard</a></li>
                        <li class="breadcrumb-item active">Dealers</li>
                    </ol>
                </div>
            </div>
        </div><!-- /.container-fluid -->
    </section>
    <section class="content">
        <div class="container-fluid">              
            <div class="row mt-0">
                <div class="col-md-12">
                    <div class="card card-primary card-outline">            
                        <div class="card-body">
                            <div>
                                <div class="table-responsive tableScrollWrap" >
                                    <table class="table table-striped1 mainTable" id="mytable" >
                                        <thead>
                                            <tr>                                
                                                <th>S.No.</th>
                                                <th>Org. Office Name</th>
                                                <th>Office GST</th>
                                                <!--<th>Office Address</th>-->
                                                <!--<th>Office Email</th>-->
                                                <th>Office Mobile</th>
                                                <th>Person Name</th>
                                                <!--<th>Person Address</th>-->
                                                <th>Person Mobile Number</th>
                                                <!--<th>Person Email</th>-->
                                                <th></th>

                                            </tr>
                                        </thead>
                                        <tbody>

                                            <c:forEach var="beanType" items="${requestScope['list']}" varStatus="loopCounter">
                                                <tr
                                                    onclick="fillColumn();">
                                                    <td>${loopCounter.count }</td>
                                                    <td>${beanType.org_office_name}</td>
                                                    <td>${beanType.gst_number}</td>
                                                    <!--<td>${beanType.off_address_line1}</td>-->
                                                    <!--<td>${beanType.off_email_id1}</td>-->
                                                    <td>${beanType.off_mobile_no1}</td>
                                                    <td>${beanType.key_person_name}</td>
                                                    <!--<td>${beanType.kp_address_line1}</td>-->
                                                    <td>${beanType.kp_mobile_no1}</td>
                                                    <!--<td>${beanType.kp_email_id1}</td>-->
                                                    <td>
                                                        <div>
                                                            <a href="DealersController?task=viewDealerDetails&org_office_id=${beanType.org_office_id}&key_person_id=${beanType.key_person_id}" class="btn far fa-eye actionEdit" title="View Dealer Detail"></a>
                                                            <a class="btn btn-info" title="Map Items" href="DealerItemMapController?org_office_id=${beanType.org_office_id}">Map Items</a>
                                                        </div> 
                                                    </td>

                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
            <div class="row mt-3">
                <div class="col-md-12 mt-3 img-thumbnail">
                    <div id="map" style="height: 500px; width: auto;"></div>
                </div>
            </div>
        </div>
    </section>
</div>

<%@include file="/CRM Dashboard/CRM_footer.jsp" %>
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
                                                            var dealer_data;
                                                            $.ajax({
                                                                type: "POST",
                                                                url: 'DealersController?task=getAllDealers',
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
