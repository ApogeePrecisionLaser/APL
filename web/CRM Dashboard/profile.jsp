<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>




<div class="content-wrapper" id="contentWrapper">
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <h1>Profile</h1>
                </div>
                <div class="col-sm-6">
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
                        <!--<input type="hidden" name="image_name" id="image_name" value="${image_name}">-->
                        <img id="blah" class="img-thumbnail usr_image" src="CRM Dashboard/assets2/img/product/profileImg.png" />
                        <h2 class="mt-1 mb-1">${logged_org_office}</h2>
                        <p class="text-secondry">${gst}</p>
                    </div>
                </div>
                <br><br><br><br><br><br>
                <div class="card card-primary rounded-0 profileCard" style="">
                    <div class="card-body">
                        <div class="mt-1">
                            <div class="text-right">
                                <a href="EditProfileController" class="btn myThemeBtn text-right">Edit Profile</a>
                            </div>
                            <div class="row mt-4">
                                <div class="col-md-4">
                                    <div>
                                        <p class="mb-0"><small>Dealer Name:</small></p>
                                        <p><strong>${logged_user_name}</strong></p>
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
                                        <p class="mb-0"><small>Email:</small></p>
                                        <p><strong>${email}</strong></p>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div>
                                        <p class="mb-0"><small>Landline No:</small></p>
                                        <p><strong>${landline}</strong></p>
                                    </div>
                                </div>

                                <div class="col-md-4">
                                    <div>
                                        <p class="mb-0"><small>Mobile No:</small></p>
                                        <p><strong>+${mobile1}</strong></p>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div>
                                        <p class="mb-0"><small>Alternate Mobile:</small></p>
                                        <p><strong>+${mobile2}</strong></p>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div>
                                        <p class="mb-0"><small>GST No:</small></p>
                                        <p><strong>${gst}</strong></p>
                                    </div>
                                </div>
<!--                                <div class="col-md-4">
                                    <div>
                                        <p class="mb-0"><small>Landmark:</small></p>
                                        <p><strong></strong></p>
                                    </div>
                                </div>-->
<!--                                <div class="col-md-4">
                                    <div>
                                        <p class="mb-0"><small>State:</small></p>
                                        <p><strong></strong></p>
                                    </div>
                                </div>               -->
                                <div class="col-md-4">
                                    <div>
                                        <p class="mb-0"><small>City:</small></p>
                                        <p><strong>${city}</strong></p>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div>
                                        <p class="mb-0"><small>Address:</small></p>
                                        <p><strong>${address_line1},${address_line2},${address_line3}</strong></p>
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
//    $('.usr_image').attr("src", "http://120.138.10.146:8080/APL/CRMDashboardController?task=viewImage");
    $('.usr_image').attr("src", "http://localhost:8080/APL/CRMDashboardController?task=viewImage");

</script>