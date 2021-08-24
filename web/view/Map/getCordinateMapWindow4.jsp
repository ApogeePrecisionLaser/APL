<%--
    Document   : getCordinateMapWindow1
    Created on : Oct 25, 2017, 3:20:44 PM
    Author     : Com7_2
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style type="text/css">
            #map {
                height: 350px;
                border: 1px solid #000;
            }
        </style>
        <script src="js/map_js.js"></script>
        <script type="text/javascript" language="javascript">
            var data = [];
            window.onload = function ()
            {
                map();
            };
            function map()
            {
                directionsService = new google.maps.DirectionsService();
                directionsRenderer = new google.maps.DirectionsRenderer();
                //var cen = new google.maps.LatLng(41.850033, -87.6500523);
                var cen = new google.maps.LatLng(28.612776, 77.386388); // 28.6126823,77.37714   // 28.6266395,77.3755757
                var mapOptions = {
                    zoom: 7,
                    center: cen
                }
                var map = new google.maps.Map(document.getElementById('map'), mapOptions);
                directionsRenderer.setMap(map);

                let infoWindow = new google.maps.InfoWindow({
                    content: "Click the map to get Lat/Lng!",
                    position: cen,
                });
                infoWindow.open(map);
                // Configure the click listener.
                map.addListener("click", (mapsMouseEvent) => {
                    // Close the current InfoWindow.
                    infoWindow.close();
                    // Create a new InfoWindow.
                    infoWindow = new google.maps.InfoWindow({
                        position: mapsMouseEvent.latLng,
                    });

                    var lat_long = mapsMouseEvent.latLng.toString().replace(/"/g, "").replace(/'/g, "").replace(/\(|\)/g, "");
                    //var lat_long = lat_long.toString().replace(/"/g, "").replace(/'/g, "").replace(/\(|\)/g, "");
                    var array = lat_long.split(",");
                    var lat = array[0];
                    var long = array[1];
                  //  alert(" array lat 1 ---" + lat);
                  //  alert(" array long 2 ---" + long);
                    opener.document.getElementById("latitude").value =lat;
                    opener.document.getElementById("longitude").value = long;
//                    $('#latti').val(lat);
//                    $('#longi').val(long);
                    alert('Location selected');
//                    infoWindow.setContent(
//                            JSON.stringify(mapsMouseEvent.latLng.toJSON(), null, 2)
//                            );
                    infoWindow.open(map);
                });

            }

        </script>
    </head>
    <body>
        <script type="text/javascript" src="http://maps.google.com/maps/api/js?key=AIzaSyDOT5yBi-LAmh9P2X0jQmm4y7zOUaWRXI0"></script>
        <input type="button" value="Close" onclick="window.close();">
        <div id="map" style="width:600px;height:550px;"></div>
        <input type="" id="longi" value="${longi}" size="20">
        <input type="" id="latti" value="${latti}" size="20">
    </body>
</html>
