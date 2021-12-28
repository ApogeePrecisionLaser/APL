<%-- 
    Document   : route_tolerance
    Created on : 2 Apr, 2021, 11:20:45 AM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <title>Marker Layer</title>
        <style type="text/css">
            body{
                margin: 0;
                overflow: hidden;
                background: #fff;
            }
            #map{
                position: relative;
                height: 553px;
                border:1px solid #3473b7;
            }
        </style>
        <!--        <script src = "supermap-libs/classic/libs/SuperMap.Include.js"></script>-->
        <!--        <script
                src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDOT5yBi-LAmh9P2X0jQmm4y7zOUaWRXI0&sensor=false"></script>-->
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDOT5yBi-LAmh9P2X0jQmm4y7zOUaWRXI0&libraries=places"></script>
        <script type="text/javascript">
            function myfunction() {
//                var list = $('#list').val();
//                list = list.replaceAll('[', '').replaceAll(']', '');
//                var loc = list.split(",");
//                loc
//                var latS,latE,longS,longE;
//                for (i = 0; i < loc.length; i++) {
//                    const re = /(.*?)_(.*)/;
//                    if (i == 0) {
//                        const start = loc[i];
//                        [, latS, longS] = re.exec(start);
//                    } else if (i == 4) {
//                        const end = loc[i];
//                        [, latE, longE] = re.exec(end);
//                    }
//                }
                var map;
//                var start = new google.maps.LatLng(latS,longS);
//                var end = new google.maps.LatLng(latE,longE);

// delhi lat long --- 28.6466773,76.8130739    28.5844789,77.3540411
// noida lat long --- 28.5166817,77.2580448    28.5986111,77.3658778

                var start = new google.maps.LatLng(28.5605046,77.3737967);
                var end = new google.maps.LatLng(28.612681,77.377207);
                var option = {
                    zoom: 10,
                    center: start
                };
                map = new google.maps.Map(document.getElementById('map'), option);
                var display = new google.maps.DirectionsRenderer();
                var services = new google.maps.DirectionsService();
                display.setMap(map);
                var request = {
                    origin: start,
                    destination: end,
                    travelMode: 'DRIVING'
                };
                services.route(request, function (result, status) {
                    if (status == 'OK') {
                        display.setDirections(result);
                    }else{
                        window.alert("Directions request failed due to " + status);
                    }
                });
            }

        </script>
    </head>
    <body onload="myfunction();" >
        <div id="map" style="width: 90%; height: 700px;margin-left:5%;">
<!--            <input align="center" type="hid den"  id="lat" size="19" name="lat" value="${latitude}" disabled>
            <input align="center" type="hid  den"  id="long" size="19" name="long" value="${longitude}" disabled>-->

        </div>

        <!--        <div class="col-md-12 col-sm-12 col-xs-12">
                    <div id="map" style="width: 100%; height: 700px;"></div>
                </div>-->

    </body>
</html>

