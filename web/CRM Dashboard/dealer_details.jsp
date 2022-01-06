<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>




<div class="content-wrapper" id="contentWrapper">
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
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
                            <img id="blah" class="img-thumbnail usr_image" src="CRM Dashboard/assets2/img/product/profileImg.png" />
                            <h2 class="mt-1 mb-1">${beanType.org_office_name}</h2>
                            <p class="text-secondry">${beanType.gst_number}</p>
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
                                <div class="row mt-4">
                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small>Dealer Name:</small></p>
                                            <p><strong>${beanType.key_person_name}</strong></p>
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
                                            <p><strong>${beanType.off_email_id1}</strong></p>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small>Office Mobile:</small></p>
                                            <p><strong>${beanType.off_mobile_no1}</strong></p>
                                        </div>
                                    </div>

                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small>Person Mobile No:</small></p>
                                            <p><strong>${beanType.kp_mobile_no1}</strong></p>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small>Person Email:</small></p>
                                            <p><strong>${beanType.kp_email_id1}</strong></p>
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
                                            <p><strong>${beanType.kp_address_line1}</strong></p>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small>City:</small></p>
                                            <p><strong>${beanType.city_name}</strong></p>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small> Blood Group:</small></p>
                                            <p><strong>${beanType.blood}</strong></p>
                                        </div>
                                    </div>

                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small> Date Of Birth:</small></p>
                                            <p><strong>${beanType.kp_date_of_birth}</strong></p>
                                        </div>
                                    </div>

                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small>ID Proof:</small></p>
                                            <p><strong>${beanType.id_type}</strong></p>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small>ID No:</small></p>
                                            <p><strong>${beanType.id_no}</strong></p>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small>Gender:</small></p>
                                            <p><strong>${beanType.gender}</strong></p>
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
//    $('.usr_image').attr("src", "http://120.138.10.146:8080/APL/CRMDashboardController?task=viewImage&key_person_id=" + key_person_id);
    $('.usr_image').attr("src", "http://"+IMAGE_URL+"/APL/CRMDashboardController?task=viewImage&key_person_id=" + key_person_id);

</script>
