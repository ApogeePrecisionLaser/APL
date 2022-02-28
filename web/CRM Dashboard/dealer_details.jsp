<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>

<div class="content-wrapper" id="contentWrapper">
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2 marginTop10">
                <div class="col-sm-6">
                    <h1>Dealer Profile</h1>
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="CRMDashboardController">Home</a></li>
                        <li class="breadcrumb-item active">Dealer Profile</li>
                    </ol>
                </div>
            </div>
        </div><!-- /.container-fluid -->
    </section>


    <section class="content">
        <c:forEach var="beanType" items="${requestScope['list']}" varStatus="loopCounter">
            <div class="row">
                <div class="col-md-12 px-3">
                    <div class="profileHeader" style="">
                        <div class="text-center">
                            <input type="hidden" name="key_person_id" id="key_person_id" value="${beanType.key_person_id}">
                            <input type="hidden" name="image_name" id="image_name" value="${beanType.image_name}">
                            <input type="hidden" name="image_path" id="image_path" value="${beanType.image_path}">
                            <img id="blah" class="img-thumbnail usr_image" src="CRM Dashboard/assets2/img/product/profileImg.png" />
                            <h2 class="mt-1 mb-1">${beanType.org_office_name}</h2>
                            <c:if test="${beanType.gst_number!=''}">
                                <p class="text-secondry" id="gst"><strong>GST:</strong> ${beanType.gst_number}</p>
                            </c:if>
                            <!--<p class="text-secondry"><strong>GST:</strong> ${beanType.gst_number}</p>-->
                        </div>
                    </div>
                    <div class="mr-2 mt-2 backBtnWrap">
                        <a href="DealersController" class="btn btnBack "><i class="fas fa-chevron-circle-left"></i></a>
                    </div>
                    <!--                    <div class="text-left mt-4">
                                            <a href="ProfileController" class="btn myThemeBtn text-right">Back</a>
                                        </div>-->
                    <div class="card card-primary rounded-0 profileCard">
                        <div class="card-body">
                            <div class="mt-1">
                                <!--                                <div class="text-right">
                                                                    <a href="DealersController" class="btn myThemeBtn text-right">Back</a>
                                
                                                                    <a href="DealersController" class="btn btnBack "><i class="fas fa-chevron-circle-left"></i></a>
                                
                                                                </div>-->
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
                                <div class="row mt-4">
                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small>Dealer Name:</small></p>
                                            <p id="user_name"><strong>${beanType.key_person_name}</strong></p>
                                        </div>
                                    </div>
                                    <!--                  <div class="col-md-4">
                                                        <div>
                                                          <p class="mb-0"><small>Last Name:</small></p>
                                                          <p><strong></strong></p>
                                                        </div>
                                                      </div>-->
                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small>Office Email:</small></p>
                                            <p id="email"><strong><a href="mailTo:${beanType.off_email_id1}"><i class="far fa-envelope"></i> ${beanType.off_email_id1}</a></strong></p>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small>Office Mobile:</small></p>
                                            <p id="mobile1"><strong><a href="tel:+${beanType.off_mobile_no1}"><i class="fas fa-mobile-alt fontTwelve"></i> ${beanType.off_mobile_no1}</a></strong></p>
                                        </div>
                                    </div>

                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small>Person Mobile No:</small></p>
                                            <p id="mobile2"><strong><a href="tel:+${beanType.off_mobile_no1}"><i class="fas fa-mobile-alt fontTwelve"></i> ${beanType.kp_mobile_no1}</a></strong></p>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small>Person Email:</small></p>
                                            <p><strong><a href="mailTo:${beanType.kp_email_id1}"><i class="far fa-envelope"></i> ${beanType.kp_email_id1}</a></strong></p>
                                        </div>
                                    </div>

                                    <!--                                    <div class="col-md-4">
                                                                            <div>
                                                                                <p class="mb-0"><small>City:</small></p>
                                                                                <p><strong>${city}</strong></p>
                                                                            </div>
                                                                        </div>-->
                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small>Office Address:</small></p>
                                            <p><strong>${beanType.off_address_line1}</strong></p>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small> Person Address:</small></p>
                                            <p id="address1"><strong>${beanType.kp_address_line1}</strong></p>
                                            <p id="address2" hidden=""><strong>${beanType.kp_address_line1}</strong></p>
                                            <p id="address3" hidden=""><strong>${beanType.kp_address_line1}</strong></p>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small>City:</small></p>
                                            <p id="city"><strong>${beanType.city_name}</strong></p>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small> Blood Group:</small></p>
                                            <p id="blood"><strong>${beanType.blood}</strong></p>
                                        </div>
                                    </div>

                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small> Date Of Birth:</small></p>
                                            <p id="dob"><strong>${beanType.kp_date_of_birth}</strong></p>
                                        </div>
                                    </div>

                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small>ID Proof:</small></p>
                                            <p id="id_type"><strong>${beanType.id_type}</strong></p>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small>ID No:</small></p>
                                            <p id="id_no"><strong>${beanType.id_no}</strong></p>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small>Gender:</small></p>
                                            <p id="gender"><strong>${beanType.gender}</strong></p>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small>Latitude:</small></p>
                                            <p id="latitude"><strong>${beanType.latitude}</strong></p>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small>Longitude:</small></p>
                                            <p id="longitude"><strong>${beanType.longitude}</strong></p>
                                        </div>
                                    </div>
                                </div> 
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </section>
</div>

<%@include file="/CRM Dashboard/CRM_footer.jsp" %>

<script>
    var key_person_id = $('#key_person_id').val();
    // alert(key_person_id);
//    $('.usr_image').attr("src", "http://120.138.10.146:8080/APL/CRMDashboardController?task=viewImage&key_person_id=" + key_person_id);
    $('.usr_image').attr("src", "http://" + IMAGE_URL + "/APL/CRMDashboardController?task=viewImage&key_person_id=" + key_person_id + "&type=ph");

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
