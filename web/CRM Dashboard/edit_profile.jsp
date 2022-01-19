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
                <div class="profileHeader">
                    <div class="text-center">
                        <img id="blah" class="img-thumbnail usr_image" src="CRM Dashboard/assets2/img/product/profileImg.png" />
                        <h2 class="mt-1 mb-1">${logged_org_office}</h2>
                        <!--<p class="text-secondry"><strong>GST:</strong> ${gst}</p>-->
                        <c:if test="${gst!=''}">
                            <p class="text-secondry"><strong>GST:</strong> ${gst}</p>
                        </c:if>
                    </div>
                </div>
                <div class="text-left mt-2">
                    <!--<a href="ProfileController" class="btn myThemeBtn text-right">Back</a>-->
                    <a href="ProfileController" class="btn btnBack "><i class="fas fa-chevron-circle-left"></i></a>

                </div>
                <div class="card card-primary rounded-0 profileCard">
                    <div class="card-body px-4">
                        <div class="mt-1">
                            <form id="editProfile" class="myForm" method="post" action="ProfileController" enctype="multipart/form-data">
                                <div class="row">
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputName">Title:<sup class="text-danger">*</sup></label>



                                            <select class="form-control" name="salutation" disabled="">
                                                <c:if test="${salutation=='Mr.'}">
                                                    <option value="Mr." selected="">Mr.</option>
                                                </c:if>
                                                <c:if test="${salutation=='Mrs.'}">
                                                    <option value="Mrs." selected="">Mrs.</option>
                                                </c:if>
                                                <c:if test="${salutation!='Mr.' || salutation!='Mrs.'}">
                                                    <option>---Select--- </option>
                                                    <option value="Mr." >Mr.</option>
                                                    <option value="Mrs." >Mrs.</option>

                                                </c:if>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputName">Dealer Name:<sup class="text-danger">*</sup></label>
                                            <input type="text" disabled="" class="form-control" value="${logged_user_name}" name="dealer_name" id="dealer_name">
                                            <input type="hidden" class="form-control myInput" id="org_office_id" name="org_office_id" value="${org_office_id}" >
                                            <input type="hidden" class="form-control myInput" id="key_person_id" name="key_person_id" value="${key_person_id}" >
                                            <input type="hidden" class="form-control myInput" id="org_office_designation_map_id" name="org_office_designation_map_id" value="${org_office_designation_map_id}" >
                                            <input type="hidden" class="form-control myInput" id="general_image_details_id" name="general_image_details_id" value="${general_image_details_id}" >

                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputName">Organisation Picture:</label>
                                            <input type="file" class="form-control" onchange="readURL(this);" name="design_name" id="design_name">
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputName">Email:<sup class="text-danger">*</sup></label>
                                            <input type="email" class="form-control" value="${email}" name="email">
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputName">Landline No:</label>
                                            <input type="text" class="form-control" value="${landline}" name="landline" id="landline">
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputName">Office Mobile No:<sup class="text-danger">*</sup></label>
                                            <input type="text" class="form-control" value="${mobile1}" name="mobile1" id="mobile1" onkeyup="myFun(id)" >
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputName">Person Mobile No:<sup class="text-danger">*</sup></label>
                                            <input type="text" class="form-control" value="${mobile2}" name="mobile2" id="mobile2" onkeyup="myFunForPersonNumber(id)">
                                        </div>
                                    </div>
                                    <!--                                    <div class="col-md-4">
                                                                            <div class="form-group">
                                                                                <label for="inputName">GST No:<sup class="text-danger">*</sup></label>-->
                                    <input type="hidden" class="form-control" value="${gst}" name="gst" id="gst">
                                    <!--                                        </div>
                                                                        </div>                                    -->
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputName">City:<sup class="text-danger">*</sup></label>
                                            <input type="text" class="form-control" value="${city}" name="city" id="city">
                                        </div>
                                    </div>                                        
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputDescription">Address Line1:<sup class="text-danger">*</sup></label>
                                            <input type="text" class="form-control" value="${address_line1}" name="address_line1" id="address_line1">
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputDescription">Address Line2:<sup class="text-danger">*</sup></label>
                                            <input type="text" class="form-control" value="${address_line2}" name="address_line2" id="address_line2">
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputDescription">Address Line3:</label>
                                            <input type="text" class="form-control" value="${address_line3}" name="address_line3" id="address_line3">
                                        </div>
                                    </div>



                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputName">Blood Group:</label>
                                            <select class="ui dropdown mySelect form-control" name="blood" id="blood" >
                                                <c:if test="${blood=='A+'}">
                                                    <option value="A+" selected="">A+</option>
                                                </c:if>
                                                <c:if test="${blood=='A-'}">
                                                    <option value="A-" selected="">A-</option>
                                                </c:if>
                                                <c:if test="${blood=='B+'}">
                                                    <option value="B+" selected="">B+</option>
                                                </c:if>
                                                <c:if test="${blood=='B-'}">
                                                    <option value="B-" selected="">B-</option>
                                                </c:if>
                                                <c:if test="${blood=='O+'}">
                                                    <option value="O+" selected="">O+</option>
                                                </c:if>
                                                <c:if test="${blood=='O-'}">
                                                    <option value="O-" selected="">O-</option>
                                                </c:if>
                                                <c:if test="${blood=='AB+'}">
                                                    <option value="AB+" selected="">AB+</option>
                                                </c:if>
                                                <c:if test="${blood=='AB+'}">
                                                    <option value="AB-" selected="">AB-</option>
                                                </c:if>
                                                <c:if test="${blood!='A+' || blood!='A-' || blood!='B+' || blood!='B-' || blood!='O+' || blood!='O-' || blood!='AB+' || blood!='AB-' }">
                                                    <option>---Select--- </option>
                                                    <option value="A+">A+</option>
                                                    <option value="A-">A-</option>
                                                    <option value="B+">B+</option>
                                                    <option value="B-">B-</option>
                                                    <option value="O+">O+</option>
                                                    <option value="O-">O-</option>
                                                    <option value="AB+">AB+</option>
                                                    <option value="AB-">AB-</option>
                                                </c:if>

                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputName">Date Of Birth:<sup class="text-danger">*</sup></label>
                                            <input type="date" class="form-control" name="date_of_birth" id="date_of_birth" size="5" value="${date_of_birth}">
                                        </div>
                                    </div>

                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputName">ID Proof Type:</label>
                                            <select class="ui dropdown form-control mySelect" name="id_type" id="id_type">
                                                <option>---Select--- </option>
                                                <c:forEach var="id_list"  items="${requestScope['id_list']}">
                                                    <c:if test="${id_type==id_list}">
                                                        <option value="${id_list}" selected="">
                                                            <c:out value="${id_list}"/>
                                                        </option>
                                                    </c:if> 
                                                    <c:if test="${id_type!=id_list}">
                                                        <option value="${id_list}">
                                                            <c:out value="${id_list}"/>
                                                        </option>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>


                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputName">ID Proof No:</label>
                                            <input class="form-control myInput" type="text" id="id_no" name="id_no"  size="30" value="${id_no}" >
                                        </div>
                                    </div>

                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputName">Latitude:<sup class="text-danger">*</sup></label>
                                            <input type="text" class="form-control" name="latitude" id="latitude" value="${latitude}">
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputName">Longitude:<sup class="text-danger">*</sup></label>
                                            <div class="d-flex">
                                                <input class="form-control" type="text" id="longitude" name="longitude"  size="20" value="${longitude}">
                                                <input class="btn myThemeBtn rounded-0 px-2" type="button" id="get_cordinate" value="Get Cordinate" onclick="openMapForCord()">
                                            </div>
                                        </div>
                                    </div>

                                    <!--                                    <div class="col-md-4">
                                                                            <div class="form-group">
                                                                                <label for="inputName">Picture:</label>
                                                                                <input class="form-control myInput" type="file" id="design_name" name="design_name"  size="30" value="" onchange="readURL(this);"> 
                                                                            </div>
                                                                        </div>  -->

                                    <div class="col-md-4">
                                        <div class="myIDImgPopUpWrap d-flex justify-content-start">
                                            <div class="position-relative">
                                                <img id="myIDImgPopUp" class="IDImg"  src=""  >
                                                <div id="myModal" class="modal">
                                                    <span class="close" id="close_modal">&times;</span>
                                                    <img class="modal-content" id="img01">
                                                </div>   
                                            </div>
                                            <div class="form-group">
                                                <label for="inputName">Select ID Proof:</label>
                                                <input class="form-control myInput" type="file" id="id_proof" name="id_proof"  size="30" value=""  onchange="getIDProof(this);">
                                            </div>
                                        </div>
                                        <!--                                        <div class="form-group">
                                                                                    <label for="inputName">Select ID Proof:</label>
                                                                                    <input class="form-control myInput" type="file" id="id_proof" name="id_proof"  size="30" value=""  onchange="readURL(this);">
                                                                                </div>-->
                                    </div>

                                    <!--                                    <div class="col-md-4">
                                                                            <div class="form-group mb-0">
                                                                                <label for="inputName">Gender:</label>
                                                                            </div>
                                                                            <div class="form-group form-check d-inline mr-2 pl-0">
                                                                                <label class="form-check-label ">
                                    <c:choose>
                                        <c:when test="${gender=='M'}">
                                            <input type="radio" id="genderm" name="gender" value="M" checked=""> Male
                                        </c:when>
                                        <c:otherwise>
                                            <input type="radio" id="genderm" name="gender" value="M" > Male
                                        </c:otherwise>
                                    </c:choose>

                                </label>
                            </div>
                            <div class="form-group form-check d-inline pl-0">
                                <label class="form-check-label">

                                    <c:choose>
                                        <c:when test="${gender=='F'}">
                                            <input type="radio" id="genderf" name="gender" value="F" checked=""> Female
                                        </c:when>
                                        <c:otherwise>
                                            <input type="radio" id="genderf" name="gender" value="F" > Female
                                        </c:otherwise>
                                    </c:choose>

                                </label>
                            </div>
                        </div>-->
                                    <div class="col-md-12">
                                        <div class="form-group mb-0 mt-3">
                                            <button class="btn myThemeBtn" type="submit" name="task" value="Update">Update</button>
                                        </div>
                                    </div>
                                </div>
                            </form>    
                        </div>
                    </div>
                </div>




                <div class="card card-primary rounded-0 profileCard mt-2">
                    <div class="card-body px-4">

                        <div class="changePasswordSec">
                            <div class="mb-3">
                                <h2>Change Password</h2>
                            </div>
                            <div class="changePassFormWrap">
                                <form class="myForm" id="changePassword" method="post" action="ProfileController">
                                    <div class="row">
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label for="inputName">New Password:<sup class="text-danger">*</sup></label>
                                                <input type="password" class="form-control" name="newPassword" id="newPass">
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label for="inputName">Confirm Password:<sup class="text-danger">*</sup></label>
                                                <input type="password" class="form-control" name="confirmPassword" id="confirmPass">
                                            </div>
                                        </div>
                                        <div class="col-md-4 col-2">
                                            <div class="form-group mb-0 showPassBtn">
                                                <a class="btn myThemeBtn showPass " id="showPass" onclick="hidePass();"><i class="far fa-eye"></i>  </a>
                                                <a class="btn myThemeBtn hidePass" id="hidePass" onclick="showPass();"> <i class="fas fa-eye-slash"></i> </a>
                                            </div>
                                        </div>
                                        <div class="col-md-12 col-10">
                                            <div class="form-group mb-0 changePassBtn">
                                                <input type="submit" class="btn myThemeBtn " name="task1" value="Update Password">
                                            </div>
                                        </div>                      
                                    </div>
                                </form>
                            </div>   
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>


<br><br><br><br><br><br><br><br>

<!--<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<link href = "https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
      rel = "stylesheet">
<script src = "https://code.jquery.com/jquery-1.10.2.js"></script>
<script src = "https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>-->


<script>
    var modal = document.getElementById("myModal");
    var img = document.getElementById("myIDImgPopUp");
    var modalImg = document.getElementById("img01");
    img.onclick = function () {
        modal.style.display = "block";
        modalImg.src = this.src;
    }
    // var span = document.getElementsByClassName("close")[0];
    $('#close_modal').click(function () {
        modal.style.display = "none";
    })

</script>



<script>
    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $('#blah')
                        .attr('src', e.target.result);
            };
            reader.readAsDataURL(input.files[0]);
        }
    }

    function getIDProof(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $('#myIDImgPopUp')
                        .attr('src', e.target.result);
            };
            reader.readAsDataURL(input.files[0]);
        }
    }
</script>
<script>
    $(function () {

        $("#city").autocomplete({
            source: function (request, response) {
                var random = $('#city').val();
                $.ajax({
                    url: "ProfileController",
                    dataType: "json",
                    data: {action1: "getCities", str: random},
                    success: function (data) {
                        console.log(data);
                        response(data.list);
                    }, error: function (error) {
                        console.log(error.responseText);
                        response(error.responseText);
                    }
                });
            },
            select: function (events, ui) {
                console.log(ui);
                $('#city').val(ui.item.label);
                return false;
            }
        });

    });
//                                                $('.usr_image').attr("src", "http://120.138.10.146:8080/APL/CRMDashboardController?task=viewImage");
    $('.usr_image').attr("src", "http://" + IMAGE_URL + "/APL/CRMDashboardController?task=viewImage&type=ph");
    $('#myIDImgPopUp').attr("src", "http://" + IMAGE_URL + "/APL/CRMDashboardController?task=viewImage&type=");



    function openMapForCord() {
        var url = "GeneralController?task=GetCordinates4"; //"getCordinate";
        popupwin = openPopUp(url, "", 600, 630);
    }


    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
        return window.open(url, window_name, window_features);
    }


    function myFun(value)
    {
        var req = null;
        if (req != null)
            req.abort();
        var random = document.getElementById("mobile1").value;


        if (random.length >= 10)
        {
            req = $.ajax({
                type: "POST",
                url: "OrgOfficeController",
                data: {action1: "mobile_number",
                    str: random},
                dataType: "json",
                success: function (response_data) {

                    console.log(response_data);
                    alert(response_data.list[0]);
                    document.getElementById("mobile1").value = response_data.list[0];
                    //  response(response_data.list);
                }, error: function (error) {
                    console.log(error.responseText);
                    document.getElementById("mobile1").value = "";
                    response(error.responseText);
                }


            });
        }

    }

    function myFunForPersonNumber(value)
    {
        var req = null;
        if (req != null)
            req.abort();
        var random = document.getElementById("mobile2").value;


        if (random.length >= 10)
        {
            req = $.ajax({
                type: "POST",
                url: "DealersController",
                data: {action1: "mobile_number",
                    str: random},
                dataType: "json",
                success: function (response_data) {

                    console.log(response_data);
                    alert(response_data.list[0]);
                    document.getElementById("mobile2").value = response_data.list[0];
                    //  response(response_data.list);
                }, error: function (error) {
                    console.log(error.responseText);
                    document.getElementById("mobile2").value = "";
                    response(error.responseText);
                }


            });
        }

    }


    $("#hidePass").hide();
    function hidePass() {
        $("#newPass").attr('type', 'text');
        $("#confirmPass").attr('type', 'text');
        $("#showPass").hide();
        $("#hidePass").show();
    }
    function showPass() {
        $("#newPass").attr('type', 'password');
        $("#confirmPass").attr('type', 'password');
        $("#showPass").show();
        $("#hidePass").hide();
    }


    $(document).ready(function () {
        $("#changePassword").validate({
            rules: {
                newPassword: {
                    required: true,
                    minlength: 6,
                },
                confirmPassword: {
                    required: true,
                    minlength: 6,
                    equalTo: "#newPass"
                }
            },
            messages: {
                newPassword: {
                    required: "Please enter new password.",
                    minlength: "Please enter atleast 6 digit."
                },
                confirmPassword: {
                    required: "Please enter confirm password.",
                    minlength: "Please enter atleast 6 digit.",
                    equalTo: "Please enter same password"
                },
            }
        });
    });

</script>
<%@include file="/CRM Dashboard/CRM_footer.jsp" %>




<script>
    $(document).ready(function () {
        $("#editProfile").validate({
            rules: {
                gender: {
                    required: true,
                },
                dealer_name: {
                    required: true
                },
                email: {
                    required: true,
                    email: true
                },
                mobile1: {
                    required: true
                },
                mobile2: {
                    required: true
                },
                gst: {
                    required: true
                },
                city: {
                    required: true
                },
                address_line1: {
                    required: true
                },
                address_line2: {
                    required: true
                },
                date_of_birth: {
                    required: true
                },
//                id_type: {
//                    required: true
//                },
//                id_no: {
//                    required: true
//                },
                latitude: {
                    required: true
                },
                longitude: {
                    required: true
                },
//                id_proof: {
//                    required: true
//                },
            }
        });
    });
</script>