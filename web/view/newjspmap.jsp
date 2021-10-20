<%-- 
    Document   : newjspmap
    Created on : 4 Aug, 2021, 3:39:31 PM
    Author     : DELL
--%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.datatables.net/1.10.25/css/dataTables.bootstrap4.min.css">
        <script async
                src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCuX12H0Ak5aa_AUhy5XWoy7d0WIATWypU&callback=initMap">
        </script>  
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="../js/map_js.js"></script>
        <title>JSP Page</title>
    </head>
    <style>
        #map {
            height: 70%;
            width: 70%;
        }
        html, body {
            height: 100%;
            margin: 0;
            padding: 0;
        }
    </style>

</style>
<body>
    <h1></h1>

    <div id="map" width="700" height="700"></div>
    <div class="col-md-12 text-center mt-3">
        <button class="btn btn-info text-center" onclick="calcRoute();" style="margin-top:20px;">Draw Direction</button>

        <!--        <button class="btn btn-info text-center" onclick="calcRoute();" style="margin-top:20px;">Draw Direction</button>-->
        <input type="hidden" id="lat" name="lati">
        <input type="hidden" id="long" name="longi">
    </div>
    <script>
//        let map;        
//        //28.612776,77.386388
//        function initMap() {
//            map = new google.maps.Map(document.getElementById("map"), {
//                center: {lat: 28.612776, lng: 77.386388},
//                zoom: 8,
//            });
//        }




// This example requires the Geometry library. Include the libraries=geometry
// parameter when you first load the API. For example:
// <script src="https://maps.googleapis.com/maps/api/js?key=YOUR_API_KEY&libraries=geometry">
//        let marker1, marker2;
//        let poly, geodesicPoly;
//
//        function initMap() {
//            const map = new google.maps.Map(document.getElementById("map"), {
//                zoom: 4,
//                center: {lat: 28.612776, lng: 77.386388},
//            });
//            map.controls[google.maps.ControlPosition.TOP_CENTER].push(
//                    document.getElementById("info")
//                    );
//            marker1 = new google.maps.Marker({
//                map,
//                draggable: true,
//                position: {lat: 28.612776, lng: 77.386388},
//            });
//            marker2 = new google.maps.Marker({
//                map,
//                draggable: true,//28.60974,77.375902
//                position: {lat: 28.60974, lng: 77.375902},
//            });
//            const bounds = new google.maps.LatLngBounds(
//                    marker1.getPosition(),
//                    marker2.getPosition()
//                    );
//            map.fitBounds(bounds);
//            google.maps.event.addListener(marker1, "position_changed", update);
//            google.maps.event.addListener(marker2, "position_changed", update);
//            poly = new google.maps.Polyline({
//                strokeColor: "#FF0000",
//                strokeOpacity: 1.0,
//                strokeWeight: 3,
//                map: map,
//            });
//            geodesicPoly = new google.maps.Polyline({
//                strokeColor: "#CC0099",
//                strokeOpacity: 1.0,
//                strokeWeight: 3,
//                geodesic: true,
//                map: map,
//            });
//            update();
//        }
//
//        function update() {
//            const path = [marker1.getPosition(), marker2.getPosition()];
//            poly.setPath(path);
//            geodesicPoly.setPath(path);
//            const heading = google.maps.geometry.spherical.computeHeading(
//                    path[0],
//                    path[1]
//                    );
//            document.getElementById("heading").value = String(heading);
//            document.getElementById("origin").value = String(path[0]);
//            document.getElementById("destination").value = String(path[1]);
//        }

        function initMap() {
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

//
//            var locations = [
//                ['Bondi Beach', -33.890542, 151.274856, 4],
//                ['Coogee Beach', -33.923036, 151.259052, 5],
//                ['Cronulla Beach', -34.028249, 151.157507, 3],
//                ['Manly Beach', -33.80010128657071, 151.28747820854187, 2],
//                ['Maroubra Beach', -33.950198, 151.259302, 1]
//            ];
//            for (i = 0; i < locations.length; i++) {
//                marker = new google.maps.Marker({
//                    position: new google.maps.LatLng(locations[i][1], locations[i][2]),
//                    map: map
//                });
//
//                google.maps.event.addListener(marker, 'click', (function (marker, i) {
//                    return function () {
//                        infowindow.setContent(locations[i][0]);
//                        infowindow.open(map, marker);
//                    }
//                })(marker, i));
//            }
            //calcRoute();


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
                //alert("lat long --"+mapsMouseEvent.latLng);
                var lat_long = mapsMouseEvent.latLng.toString().replace(/"/g, "").replace(/'/g, "").replace(/\(|\)/g, "");
                //var lat_long = lat_long.toString().replace(/"/g, "").replace(/'/g, "").replace(/\(|\)/g, "");
                //alert("lat_long --"+lat_long);
                var array = lat_long.split(",");
                var lat = array[0];
                var long = array[1];
//                alert(" array lat 1 ---"+lat);
//                alert(" array long 2 ---"+long);
                $('#lat').val(lat);
                $('#long').val(long);
                alert('Location selected');
                infoWindow.setContent(
                        JSON.stringify(mapsMouseEvent.latLng.toJSON(), null, 2)
                        );
                infoWindow.open(map);
            });

        }

        function calcRoute() {
            //getLatLong();
            var lat = $('#lat').val();
            var long = $('#long').val();
            var vehicle_longitude = "79.9367538";
            var vehicle_latitude = "23.1641888";

            var start = new google.maps.LatLng(lat, long);
            var end = new google.maps.LatLng(vehicle_latitude, vehicle_longitude);

//            var start = new google.maps.LatLng(28.6126823, 77.37714);
//            var end = new google.maps.LatLng(28.6266395, 77.3755757);

            var request = {
                origin: start,
                destination: end,
                travelMode: 'DRIVING'
            };
            directionsService.route(request, function (result, status) {
                if (status == 'OK') {
                    directionsRenderer.setDirections(result);
                }
            });
        }


    </script>
</body>
</html>
