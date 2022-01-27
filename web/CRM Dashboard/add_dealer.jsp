<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>

<div class="content-wrapper" id="contentWrapper">
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2 marginTop10">
                <div class="col-sm-6">
                    <h1>Add New Dealer</h1>
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="#">Home</a></li>
                        <li class="breadcrumb-item active">Add New Dealer</li>
                    </ol>
                </div>
            </div>
        </div>
    </section>

    <section class="content">
        <div class="row">
            <div class="col-md-12 px-3">
                <div class="profileHeader">
                    <div class="text-center">
                        <img id="blah" class="img-thumbnail" src="dist/img/product/profileImg.png" />                
                    </div>
                </div>
                <div class="mr-2 mt-2 backBtnWrap">
                    <a href="DealersController" class="btn btnBack "><i class="fas fa-chevron-circle-left"></i></a>
                </div>
                <br>
                <div class="card card-primary rounded-0 profileCard mt-2">
                    <div class="card-body px-4">
                        <div class="mt-1">
                            <form class="myForm" name="form1" action="DealersController" method="post" enctype="multipart/form-data" >
                                <div class="row">
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <input type="hidden" class="form-control myInput" id="org_office_id" name="org_office_id" value="" >
                                            <input type="hidden" class="form-control myInput" id="key_person_id" name="key_person_id" value="" >
                                            <input type="hidden" class="form-control myInput" id="org_office_designation_map_id" name="org_office_designation_map_id" value="" >
                                            <input type="hidden" class="form-control myInput" id="general_image_details_id" name="general_image_details_id" value="" >

                                            <label for="inputName">Office Name:<sup class="text-danger">*</sup></label>
                                            <input type="text" class="form-control" name="org_office_name" id="org_office_name" required="">
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputName">Mobile Number:<sup class="text-danger">*</sup></label>
                                            <input type="text" class="form-control" name="mobile_no1" id="mobile_no1" required="" onkeyup="myFun(id)" >
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputName">Person Name:<sup class="text-danger">*</sup></label>
                                            <input type="text" class="form-control" name="key_person_name" id="key_person_name" required="">
                                        </div>
                                    </div>

                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputName">Person Mobile:<sup class="text-danger">*</sup></label>
                                            <input type="text" class="form-control" name="key_person_mobile" id="key_person_mobile" required="" onkeyup="myFunForPersonNumber(id)">
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputName">GST:</label>
                                            <input type="text" class="form-control" name="gst_number" id="gst_number">
                                        </div>
                                    </div>
                                </div>

                                <div class="row" id="add_info_div" style="display:none">
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputName">Email:</label>
                                            <input type="text" class="form-control" name="email_id1" id="email_id1">
                                        </div>
                                    </div>


                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputName">Address Line1</label>
                                            <input type="text" class="form-control" name="address_line1" id="address_line1">
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputName">Address Line2</label>
                                            <input type="text" class="form-control" name="address_line2" id="address_line2">
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputName">Address Line3</label>
                                            <input type="text" class="form-control" name="address_line3" id="address_line3">
                                        </div>
                                    </div>



                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputName">City:</label>
                                            <input type="text" class="form-control" name="city_name" id="city_name">
                                        </div>
                                    </div>

                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputName">Blood Group:</label>
                                            <select class="ui dropdown mySelect form-control" name="blood" id="blood" >
                                                <option>---Select--- </option>
                                                <option value="A+">A+</option>
                                                <option value="A-">A-</option>
                                                <option value="B+">B+</option>
                                                <option value="B-">B-</option>
                                                <option value="O+">O+</option>
                                                <option value="O-">O-</option>
                                                <option value="AB+">AB+</option>
                                                <option value="AB-">AB-</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputName">Date Of Birth:</label>
                                            <input type="date" class="form-control" name="date_of_birth" id="date_of_birth" size="5">
                                        </div>
                                    </div>

                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputName">ID Proof Type:</label>
                                            <select class="ui dropdown form-control mySelect" name="id_type" id="id_type">
                                                <option>---Select--- </option>
                                                <c:forEach var="id_list"  items="${requestScope['id_list']}">
                                                    <option value="${id_list}">
                                                        <c:out value="${id_list}"/>
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputName">ID Proof No:</label>
                                            <input class="form-control myInput" type="text" id="id_no" name="id_no" value=""  size="30" >
                                        </div>
                                    </div>

                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputName">Latitude:</label>
                                            <input type="text" class="form-control" name="latitude" id="latitude">
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputName">Longitude:</label>
                                            <div class="d-flex">
                                                <input class="form-control" type="text" id="longitude" name="longitude" value="" size="20" >
                                                <input class="btn myThemeBtn rounded-0 px-2" type="button" id="get_cordinate" value="Get Cordinate" onclick="openMapForCord()">
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputName">Picture:</label>
                                            <input class="form-control myInput" type="file" id="design_name" name="design_name"  size="30" value="" onchange="readURL(this);"> 
                                        </div>
                                    </div>  

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

                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputName">Gender:</label>
                                        </div>
                                        <div class="form-group form-check mb-0 d-inline mr-2 pl-0">
                                            <label class="form-check-label ">
                                                <input type="radio" id="genderm" name="gender" value="M" > Male
                                            </label>
                                        </div>
                                        <div class="form-group form-check d-inline pl-0">
                                            <label class="form-check-label">
                                                <input type="radio" id="genderf" name="gender" value="F" > Female
                                            </label>
                                        </div>
                                    </div>


                                    <!--                                    <div class="col-md-12">
                                                                            <div class="form-group">
                                                                                <label for="inputDescription">Address:</label>
                                                                                <textarea class="form-control rounded-0" rows="2" name="address" id="address"></textarea>
                                                                            </div>
                                                                        </div>-->
                                </div>
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="form-group mb-0">
                                            <input type="button" id="add_info" value="Additional Info" class="btn myThemeBtn" name="add_info">
                                            <input type="submit" class="btn myThemeBtn" name="task1" value="Submit" >
                                        </div>
                                    </div>
                                </div>
                            </form>    
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
    // var span = document.getElementsByClassName("close")[0];
    $('#close_modal').click(function () {
        modal.style.display = "none";
    })

</script>

<script>
    $('.usr_image').attr("src", "http://" + IMAGE_URL + "/APL/CRMDashboardController?task=viewImage&type=ph");
    $('#myIDImgPopUp').attr("src", "http://" + IMAGE_URL + "/APL/CRMDashboardController?task=viewImage&type=");
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

    $('#add_info').click(function () {
        if ($('#add_info_div').css('display') == 'none') {
            $('#add_info_div').show();
        } else {
            $('#add_info_div').hide();
        }

    });

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


    function myFun(value) {
        var req = null;
        if (req != null)
            req.abort();
        var random = document.getElementById("mobile_no1").value;
        if (random.length >= 10) {
            req = $.ajax({
                type: "POST",
                url: "OrgOfficeController",
                data: {action1: "mobile_number",
                    str: random},
                dataType: "json",
                success: function (response_data) {
                    console.log(response_data);
                    alert(response_data.list[0]);
                    document.getElementById("mobile_no1").value = response_data.list[0];
                    //  response(response_data.list);
                }, error: function (error) {
                    console.log(error.responseText);
                    document.getElementById("mobile_no1").value = "";
                    response(error.responseText);
                }
            });
        }
    }

    function myFunForPersonNumber(value) {
        var req = null;
        if (req != null)
            req.abort();
        var random = document.getElementById("key_person_mobile").value;
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
                    document.getElementById("key_person_mobile").value = response_data.list[0];
                    //  response(response_data.list);
                }, error: function (error) {
                    console.log(error.responseText);
                    document.getElementById("key_person_mobile").value = "";
                    response(error.responseText);
                }
            });
        }
    }

    $(function () {
        $("#city_name").autocomplete({
            source: function (request, response) {
                var random = $('#city_name').val();
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
                $('#city_name').val(ui.item.label);
                return false;
            }
        });
    });


//    function validate() {
//        var gst = $('#gst_number').val();
//
//        if (gst == '') {
//            alert("Please enter GST number if not add ID proof !...");
//            return false;
//        }
//    }


</script>

