<%-- 
    Document   : mapWindow1
    Created on : Nov 6, 2017, 4:34:54 PM
    Author     : Com7_2
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <script src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDOT5yBi-LAmh9P2X0jQmm4y7zOUaWRXI0&sensor=false"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <title>Map View</title>
        <script type="text/javascript" language="javascript">

            function getCordinate() {
                $http.get('http://maps.google.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&sensor=false').success(function (mapData) {
                    angular.extend($scope, mapData);
                });
            }

            function openMap()
            {
                var latitude = document.getElementById("latti").value;
                var longitude = document.getElementById("longi").value;

               
                var myCenter = new google.maps.LatLng(latitude, longitude);
                var mapProp = {
                    center: myCenter,
                    zoom: 10,
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                };
                var map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
                var marker = new google.maps.Marker({
                    position: myCenter
                });
                marker.setMap(map);
            }

        </script>
    </head>
    <body onload="openMap()">
        <div id="googleMap" style="width:900px;height:550px;" onclick="getCordinate();"></div>
        <input type="hidden" id="latti" value="${latti}" >
        <input type="hidden" id="longi" value="${longi}" >
    </body>
</html>
