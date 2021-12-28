<%@include file="../layout/frontend_header.jsp" %>




<section >
    <div class="page_header text-center">
        <ul class="d-flex align-items-center">
            <li><a href="index.php" class="text-white">Home</a></li>
            <li>&nbsp / &nbsp</li>
            <li><span class="text-white">Contact</span></li>
        </ul>
        <h2 class="text-white specialFont">Contact Us</h2>
    </div>
</section>




<section class="contactSection contactPage sectionPadding">
    <div class="container">
        <div class="sectionHead mb-4 mb-md-5">
            <h2 class="">Contact Us</h2>
            <p class="text-center">We  are available for Sales, Service & Support 24x7. Please Feel free to send in your Enquiries, Feedback or Complaints. We'll get back to you shortly.</p>
        </div>
        <div class="row">
            <div class="col-md-4">
                <div class="contactLeft">
                    <div class="bgGreenOverlay py-3">
                        <!-- <h3 class="text-white">Contact Information</h3>
                        <p>We  are available for Sales, Service & Support 24x7.</p> -->
                        <div>
                            <ul>
                                <li class="mb-1"><a href="#"><strong>Utter Pradesh</strong></a> </li>
                                <li class="mb-1"><a href="tel:+91 8954894145" ><i class="fas fa-mobile-alt"></i> +91 8954894145</a></li>
                                <li class="mb-1"><a href="#"><i class="fas fa-map-marker-alt"></i> 619, Opp. New Mandi Yard, Garh Road, Hapur (UP) 245101</a></li>
                            </ul>
                            <hr class="bg-white">
                            <ul>
                                <li><a href="#"><strong>Gujarat</strong></a> </li>
                                <li><a href="tel:+91 7624002261" ><i class="fas fa-mobile-alt"></i> +91 7624002261</a></li>
                                <li><a href="#"><i class="fas fa-map-marker-alt"></i> First Floor, Shri Ram Complex, #1401, Kailashpati Chokdi, GIDC, Vitthal Udyognagar, Anand (Gujarat) 388121 </a></li>
                            </ul>
                            <hr class="bg-white">
                            <ul>
                                <li><a href="#"><strong>Madhya Pradesh</strong></a> </li>
                                <li><a href="tel:+91 7624002266" ><i class="fas fa-mobile-alt"></i> +91 7624002266</a></li>
                                <li><a href="#"><i class="fas fa-map-marker-alt"></i> Shanti Market, New Bypass Road, Near Imalia, Bhopal (MP) </a></li>
                            </ul>
                            <hr class="bg-white">
                            <ul>
                                <li><a href="#"><strong>Haryana </strong></a> </li>
                                <li><a href="tel:+91 7624002266" ><i class="fas fa-mobile-alt"></i> +91 9896830281, 9837212286</a></li>
                                <li><a href="#"><i class="fas fa-map-marker-alt"></i> Near Jat Dharamsala Ladhot Road, Sukhpura Chowk Rohtak 124001</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="squareArrowBox"></div>
            </div>
            <div class="col-md-8">
                <div class="formWrap">
                    <form action="#" class="myForm queryForm" id="contactForm">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="email"> Name: <span class="text-danger">*</span></label>
                                    <input type="text" class="form-control" name="name">
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="email"> E-mail: <span class="text-danger">*</span></label>
                                    <input type="email" class="form-control" name="email">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="email"> Contact Number: <span class="text-danger">*</span></label>
                                    <input type="text" class="form-control" name="mobile">
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="email"> Pin Code: <span class="text-danger">*</span></label>
                                    <div class="d-flex">
                                        <input type="text" class="form-control rounded-0" name="pincode">
                                        <div>
                                            <!--<a href="#" class="btn myBtn fontFourteen bgGreen text-white text-nowrap rounded-0 getCoordinate" onclick="InitMap(); getLocation();" > Get Coordinates</a>-->
                                            <input type="button" class="btn myBtn fontFourteen bgGreen text-white text-nowrap rounded-0 getCoordinate" onclick="openMapForCord()" value="Select Location"> 
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        
                        <div class="row">
                            <div class="col-md-12">
                                <p style="color:red">To Get Dealers List Either Enter Pincode or select a location by clicking on Select Location Button!....</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <div id="coordinates">
                                        <input class="form-control myInput" type="text" id="latitude" hidden  name="latitude" value="" size="20" 
                                               >
                                        <input class="form-control myInput rounded-0" type="text" id="longitude" hidden   name="longitude" value="" 
                                               size="20" >
                                    </div>
                                    <!--<div id="map" style="height: 200px; width: auto;"></div>-->
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6" >
                                <div class="form-group">
                                    <label for="email"> Your Message: <span class="text-danger">*</span></label>
                                    <textarea class="form-control" rows="4" name="message" style="min-height: 125px;"></textarea>
                                </div>
                            </div>
                            <div class="col-md-6" style="display: none" id="dealers_div">
                                <div class="form-group" style="overflow: scroll;height: 200px" >

                                    <label for="email"> <b>Dealer List:</b></label>

                                    <ul id="dealer_list">
                                        <!--<li class="mb-1" id="dealer_list"><input type="checkbox" name="dealer_check" id="dealer_check"> Ajay Kumar</li>-->
                                        <!--  <li class="mb-1">Vijay Kumar</li>
                                         <li class="mb-1">Suraj Kumar</li>
                                         <li class="mb-1">Kundan Kumar</li>
                                         <li class="mb-1"> Ajay Kumar</li>
                                         <li class="mb-1">Vijay Kumar</li>
                                         <li class="mb-1">Suraj Kumar</li>
                                         <li class="mb-1">Kundan Kumar</li>
                                         <li class="mb-1"> Ajay Kumar</li>
                                         <li class="mb-1">Vijay Kumar</li>
                                         <li class="mb-1">Suraj Kumar</li>
                                         <li class="mb-1">Kundan Kumar</li>-->

                                    </ul>


                                    <!--                                    <input type="text" class="form-control dealerListInput" value="Ajay Kumar" name="country">
                                                                        <input type="text" class="form-control dealerListInput" value="Vijay Kumar" name="country">                  
                                                                        <input type="text" class="form-control dealerListInput" value="Suraj Kumar" name="country">
                                                                        <input type="text" class="form-control dealerListInput" value="Kundan Kumar" name="country">-->


                                </div>
                            </div>
                        </div>


                        <div class="row">                                
                            <div class="col-md-6">
                                <button type="submit" class="btn btn-default myButtonEffect">Submit</button>
                            </div>
                        </div>
                    </form>
                </div>        
            </div>
        </div>
    </div>
</section>





<section class="sectionPadding pt-0">
    <div class="container">
        <iframe src="https://www.google.com/maps/d/embed?mid=17IBfe7Ld203yGGlvoig_0r2Yn2aHeTid" width="100%" height="480"></iframe>
    </div>
</section>





<%@include file="../layout/frontend_footer.jsp" %>



<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDOT5yBi-LAmh9P2X0jQmm4y7zOUaWRXI0&sensor=false"></script>
<script type="text/javascript">
                                                function openMapForCord() {

                                                    //alert("1 --" + $('#latitude').val());
                                                    var url = "GeneralController?task=GetCordinates4"; //"getCordinate";
                                                    popupwin = openPopUp(url, "", 600, 630);
                                                    getData();



                                                }

                                                function openPopUp(url, window_name, popup_height, popup_width) {
                                                    //alert("3 --" + $('#latitude').val());
                                                    var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                                                    var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                                                    var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
                                                    return window.open(url, window_name, window_features);
                                                    //alert("4 --" + $('#latitude').val());
                                                }

                                                function getData() {

                                                    alert("latitue---" + $('#latitude').val());
                                                    var latitude = $('#latitude').val();
                                                    var longitude = $('#longitude').val();

                                                    console.log("lat-------" + latitude);
                                                    console.log("long-------" + longitude);
                                                    if (latitude != "" && latitude != null && longitude != "" && longitude != null) {

                                                        var dealer_name = [];

                                                        $.ajax({
                                                            url: "ContactUsController",
                                                            dataType: "json",
                                                            data: {action1: "getDealersList", latitude: latitude, longitude: longitude},
                                                            success: function (data) {

                                                                $('#dealers_div').show();
                                                                $('#dealer_list').empty();
                                                                console.log(data);
                                                                var dealers = data.dealer_name;

                                                                for (var i = 0; i < dealers.length; i++) {
                                                                    dealer_name[i] = dealers[i]["dealer_name"];
                                                                    //alert(dealer_name[i]);
                                                                    $('#dealer_list').append('<li class="mb-1" id="dealer"><input type="checkbox" name="dealer_check" id="dealer_check' + i + '">  ' + dealer_name[i] + '</li>');
                                                                }

                                                            }
                                                        });
                                                    }

                                                }



</script>