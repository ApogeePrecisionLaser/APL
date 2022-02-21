<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>



<div class="content-wrapper" id="contentWrapper">
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2 marginTop10">
                <div class="col-sm-3">
                    <h1>Profile</h1>
                </div>
                <div class="col-sm-4">
                    <c:if test="${not empty message}">
                        <c:if test="${msgBgColor=='green'}">
                            <div class="alert alert-success alert-dismissible myAlertBox"  id="msg" >
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong>Success!</strong> ${message}

                            </div>
                        </c:if>
                        <c:if test="${msgBgColor=='red'}">
                            <div class="alert alert-danger alert-dismissible myAlertBox" id="msg" >
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong>OOps!</strong> ${message}

                            </div>
                        </c:if>
                    </c:if>
                </div>
                <div class="col-sm-5">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="CRMDashboardController">Home</a></li>
                        <li class="breadcrumb-item active">Profile</li>
                    </ol>
                </div>
            </div>
        </div><!-- /.container-fluid -->
    </section>


    <section class="content">
        <div class="row">
            <div class="col-md-12 px-3">
                <div class="profileHeader" style="">
                    <div class="text-center">
                        <input type="hidden" name="image_name" id="image_name" value="${image_name}">
                        <input type="hidden" name="image_path" id="image_path" value="${image_path}">
                        <img id="blah" class="img-thumbnail usr_image" src="CRM Dashboard/assets2/img/product/profileImg.png" />
                        <h2 class="mt-1 mb-1">${logged_org_office}</h2>
                        <c:if test="${gst!=''}">
                            <p class="text-secondry"><strong>GST:</strong> ${gst}</p>
                        </c:if>


                        <!--                        <div class="text-left1">
                                                    <small class="text-danger ">Profile completion</small>
                                                    <div class="progress mx-auto" style="width:300px;">                                        
                                                        <div class="progress-bar bg-primary progress-bar-striped" role="progressbar"
                                                             aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%">
                                                          <span class="sr-only">40% Complete (success)</span>
                                                        </div>
                                                    </div>
                                                </div>-->
                    </div>
                </div>
                <div class="text-right mt-2">
                    <a href="EditProfileController" class="btn myThemeBtn text-right"><i class="far fa-edit"></i></a>
                </div>

                <div class="card card-primary rounded-0 profileCard" style="">
                    <div class="card-body">
                        <div class="mt-1">
                            <div class="d-flex justify-content-between flex-wrap">
                                <div class="profileProgreeBar mb-2">
                                    <p class="text-success fontFourteen">Profile completion</p>
                                    <div class="progress">                                        
                                        <div class="progress-bar bg-success progress-bar-striped" role="progressbar"
                                             aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%">
                                            <span class="progress_percent">40% Complete</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row mt-2">
                                <div class="col-md-4">
                                    <div>
                                        <p class="mb-0"><small>Dealer Name:</small></p>
                                        <p id="user_name"><strong>
                                                <c:if test="${salutation=='Mr.'}">
                                                    Mr.
                                                </c:if>
                                                <c:if test="${salutation=='Mrs.'}">
                                                    Mrs.
                                                </c:if>

                                                ${logged_user_name}</strong></p>
                                    </div>
                                </div>

                                <div class="col-md-4">
                                    <div>
                                        <p class="mb-0"><small>Email:</small></p>
                                        <p id="email"><strong><a href="mailTo:${email}">${email}</a></strong></p>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div>
                                        <p class="mb-0"><small>Landline No:</small></p>
                                        <p id="landline"><strong><a href="tel:${landline}">${landline}</a></strong></p>
                                    </div>
                                </div>

                                <div class="col-md-4">
                                    <div>
                                        <p class="mb-0"><small>Office Mobile:</small></p>
                                        <p id="mobile1"><strong><a href="tel:${mobile1}">${mobile1}</a></strong></p>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div>
                                        <p class="mb-0"><small>Person Mobile:</small></p>
                                        <p id="mobile2"><strong><a href="tel:${mobile2}">${mobile2}</a></strong></p>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div>
                                        <p class="mb-0"><small>GST No:</small></p>
                                        <p id="gst"><strong>${gst}</strong></p>
                                    </div>
                                </div>

                                <div class="col-md-4">
                                    <div>
                                        <p class="mb-0"><small>City:</small></p>
                                        <p id="city"><strong>${city}</strong></p>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div>
                                        <p class="mb-0"><small>Address Line 1:</small></p>
                                        <p><strong>${address_line1},${address_line2},${address_line3}</strong></p>
                                        <p id="address1" hidden=""><strong>${address_line1}</strong></p>
                                        <p id="address2" hidden=""><strong>${address_line2}</strong></p>
                                        <p id="address3" hidden=""><strong>${address_line3}</strong></p>
                                    </div>
                                </div>


                                <div class="col-md-4">
                                    <div>
                                        <p class="mb-0"><small> Blood Group:</small></p>
                                        <p id="blood"><strong>${blood}</strong></p>
                                    </div>
                                </div>

                                <div class="col-md-4">
                                    <div>
                                        <p class="mb-0"><small> Date Of Birth:</small></p>
                                        <p id="dob"><strong>${date_of_birth}</strong></p>
                                    </div>
                                </div>

                                <div class="col-md-4">
                                    <div class="myIDImgPopUpWrap d-flex justify-content-start">
                                        <div class="position-relative">
                                            <img id="myIDImgPopUp" class="img-thumbnail1" src="">
                                            <div id="myModal" class="modal">
                                                <span class="close" id="close_modal">&times;</span>
                                                <img class="modal-content" id="img01">
                                            </div>   
                                        </div>
                                        <div>
                                            <p class="mb-0"><small>ID Proof:</small></p>
                                            <p id="id_type"><strong>${id_type}</strong></p>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div>
                                        <p class="mb-0"><small>ID No:</small></p>
                                        <p id="id_no"><strong>${id_no}</strong></p>
                                    </div>
                                </div>
                                <p id="gender" hidden=""><strong>${gender}</strong></p>
                                <!--                                <div class="col-md-4">
                                                                    <div>
                                                                        <p class="mb-0"><small>Gender:</small></p>
                                                                        <p id="gender"><strong>${gender}</strong></p>
                                                                    </div>
                                                                </div>-->
                                <div class="col-md-4">
                                    <div>
                                        <p class="mb-0"><small>Latitude:</small></p>
                                        <p id="latitude"><strong>${latitude}</strong></p>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div>
                                        <p class="mb-0"><small>Longitude:</small></p>
                                        <p id="longitude"><strong>${longitude}</strong></p>
                                    </div>
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



<script>
    var modal = document.getElementById("myModal");
    var img = document.getElementById("myIDImgPopUp");
    var modalImg = document.getElementById("img01");
    img.onclick = function () {
        modal.style.display = "block";
        modalImg.src = this.src;
    }
    var span = document.getElementsByClassName("close")[0];
    span.onclick = function () {
        modal.style.display = "none";
    }

    setTimeout(function () {
        $('#msg').fadeOut('fast');
    }, 3000);
</script>


<script>
//    $('.usr_image').attr("src", "http://120.138.10.146:8080/APL/CRMDashboardController?task=viewImage");
    $('.usr_image').attr("src", "http://" + IMAGE_URL + "/APL/CRMDashboardController?task=viewImage&type=ph");
    $('#myIDImgPopUp').attr("src", "http://" + IMAGE_URL + "/APL/CRMDashboardController?task=viewImage&type=");

    var modal = document.getElementById("myModal");
    var img = document.getElementById("myIDImgPopUp");
    var modalImg = document.getElementById("img01");
    img.onclick = function () {
        modal.style.display = "block";
        modalImg.src = this.src;
    }
//    var span = document.getElementsByClassName("close")[0];
    $('#close_modal').click(function () {
        modal.style.display = "none";
    })




    $(function () {
        CalculatePercentage();
    });
    function CalculatePercentage() {
        var user_name = $('#user_name').text();
        var email = $('#email').text();
        var landline = $('#landline').text();
        var mobile1 = $('#mobile1').text();
        var mobile2 = $('#mobile2').text();
        var gst = $('#gst').text();
        var city = $('#city').text();
        var address1 = $('#address1').text();
        var address2 = $('#address2').text();
        var address3 = $('#address3').text();
        var blood = $('#blood').text();
        var dob = $('#dob').text();
        var id_type = $('#id_type').text();
        var id_no = $('#id_no').text();
        var gender = $('#gender').text();
        var latitude = $('#latitude').text();
        var longitude = $('#longitude').text();
        var image_path = $('#image_path').val();
        var image_name = $('#image_name').val();

        var user_name_percentage = 10;
        var email_percentage = 5;
        var landline_percentage = 5;
        var mobile1_percentage = 5;
        var mobile2_percentage = 5;
        var gst_percentage = 5;
        var city_percentage = 5;
        var address1_percentage = 5;
        var address2_percentage = 5;
        var address3_percentage = 5;
        var blood_percentage = 5;
        var dob_percentage = 5;
        var id_type_percentage = 5;
        var id_no_percentage = 5;
        var gender_percentage = 5;
        var latitude_percentage = 5;
        var longitude_percentage = 5;
        var image_path_percentage = 5;
        var image_name_percentage = 5;

        var percent = 0;
        if (user_name != '') {
            percent += user_name_percentage;
        }
        if (email != '') {
            percent += email_percentage;
        }
        if (landline != '') {
            percent += landline_percentage;
        }
        if (mobile1 != '') {
            percent += mobile1_percentage;
        }
        if (mobile2 != '') {
            percent += mobile2_percentage;
        }
        if (gst != '') {
            percent += gst_percentage;
        }
        if (city != '') {
            percent += city_percentage;
        }
        if (address1 != '') {
            percent += address1_percentage;
        }
        if (address2 != '') {
            percent += address2_percentage;
        }
        if (address3 != '') {
            percent += address3_percentage;
        }
        if (blood != '') {
            percent += blood_percentage;
        }
        if (dob != '') {
            percent += dob_percentage;
        }

        if (id_type != '') {
            percent += id_type_percentage;
        }

        if (id_no != '') {
            percent += id_no_percentage;
        }

        if (gender != '') {
            percent += gender_percentage;
        }

        if (latitude != '') {
            percent += latitude_percentage;
        }
        if (longitude != '') {
            percent += longitude_percentage;
        }
        if (image_path != '') {
            percent += image_path_percentage;
        }
        if (image_name != '') {
            percent += image_name_percentage;
        }
//        $('.progress-bar').addClass('progress-striped').addClass('active');
//        $('.progress-bar .progress-bar:first').removeClass().addClass('progress-bar')
//                .addClass((percent < 40) ? 'progress-bar-danger' : ((percent < 80) ? 'progress-bar-warning' : 'progress-bar-success'));
        $('.progress-bar').width(percent + '%');
        $('.progress_percent').text(percent + '% Complete');
    }
</script>
