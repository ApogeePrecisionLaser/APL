/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function initMap() {
    const myLatlng = {lat: -25.363, lng: 131.044};
    const map = new google.maps.Map(document.getElementById("map"), {
        zoom: 4,
        center: myLatlng,
    });
    // Create the initial InfoWindow.
    let infoWindow = new google.maps.InfoWindow({
        content: "Click the map to get Lat/Lng!",
        position: myLatlng,
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
        infoWindow.setContent(
                JSON.stringify(mapsMouseEvent.latLng.toJSON(), null, 2)
                );
        infoWindow.open(map);
    });
}


function getLatLong() {// alert("theft");
    // debugger;

    var api_url = 'http://120.138.10.146:8080/fireApp/map/dashdata';
    var vehicle_longitude = "79.9367538";
    var vehicle_latitude = "23.1641888";

//    $.ajax({
//        type: 'GET',
//        url: api_url,
//        contentType: "application/json",
//        dataType: 'json',
//        success: function (result) {
//            console.log(result);
//            for (var i = 0; i < result.data.length; i++) {
//                var id = "126";
//                vehicle_longitude = result.data[i].vehicle_longitude;
//                vehicle_latitude = result.data[i].vehicle_latitude;
//                alert("vehicle_longitude -" + vehicle_longitude);
//                alert("vehicle_latitude -" + vehicle_latitude);
//            }
//        }
//    });
    //setTimeout(checkFuelTheft, 50000);
    alert("vehicle_longitude -" + vehicle_longitude);
    alert("vehicle_latitude -" + vehicle_latitude);
}