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
                        <li class="breadcrumb-item"><a href="#">Home</a></li>
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
                        <p class="text-secondry">${gst}</p>
                    </div>
                </div>
                <div class="text-left mt-4">
                    <a href="ProfileController" class="btn myThemeBtn text-right">Back</a>
                </div>
                <br><br><br>
                <div class="card card-primary rounded-0 profileCard mt-2">
                    <div class="card-body px-4">
                        <div class="mt-1">
                            <form class="myForm" method="post" action="ProfileController" enctype="multipart/form-data">
                                <div class="row">
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputName">Dealer Name:<sup class="text-danger">*</sup></label>
                                            <input type="text" class="form-control" disabled="" value="${logged_user_name}" name="dealer_name" id="dealer_name">
                                        </div>
                                    </div>
                                    <!--                                    <div class="col-md-4">
                                                                            <div class="form-group">
                                                                                <label for="inputName">Last Name:</label>
                                                                                <input type="text" class="form-control" value="">
                                                                            </div>
                                                                        </div>-->
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputName">Organisation Picture:<sup class="text-danger">*</sup></label>
                                            <input type="file" class="form-control" onchange="readURL(this);" name="image" id="image">
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
                                            <label for="inputName">Mobile No:<sup class="text-danger">*</sup></label>
                                            <input type="text" class="form-control" value="${mobile1}" name="mobile1" id="mobile1">
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputName">Alternate Mobile:</label>
                                            <input type="text" class="form-control" value="${mobile2}" name="mobile2" id="mobile2">
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputName">GST No:<sup class="text-danger">*</sup></label>
                                            <input type="text" class="form-control" value="${gst}" name="gst" id="gst">
                                        </div>
                                    </div>
<!--                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="inputName">Landmark:</label>
                                            <input type="text" class="form-control" value="" name="landmark" id="landmark">
                                        </div>
                                    </div>                    -->
                                    <!--                                    <div class="col-md-4">
                                                                            <div class="form-group">
                                                                                <label for="inputName">State:<sup class="text-danger">*</sup></label>
                                                                                <input type="text" class="form-control" value="" name="state" id="state">
                                                                            </div>
                                                                        </div>-->
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
                                            <label for="inputDescription">Address Line3:<sup class="text-danger">*</sup></label>
                                            <input type="text" class="form-control" value="${address_line3}" name="address_line3" id="address_line3">
                                        </div>
                                    </div>
                                    <div class="col-md-12">
                                        <div class="form-group mb-0">
                                            <button class="btn myThemeBtn" type="submit" name="task" value="Update">Update</button>
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

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<link href = "https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
      rel = "stylesheet">
<script src = "https://code.jquery.com/jquery-1.10.2.js"></script>
<script src = "https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>

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


//                                                    $("#state").autocomplete({
//                                                        source: function (request, response) {
//                                                            var random = $('#state').val();
//                                                            $.ajax({
//                                                                url: "ProfileController",
//                                                                dataType: "json",
//                                                                data: {action1: "getStates", str: random},
//                                                                success: function (data) {
//                                                                    console.log(data);
//                                                                    response(data.list);
//                                                                }, error: function (error) {
//                                                                    console.log(error.responseText);
//                                                                    response(error.responseText);
//                                                                }
//                                                            });
//                                                        },
//                                                        select: function (events, ui) {
//                                                            console.log(ui);
//                                                            $('#state').val(ui.item.label);
//                                                            return false;
//                                                        }
//                                                    });
                                                });
//                                                $('.usr_image').attr("src", "http://120.138.10.146:8080/APL/CRMDashboardController?task=viewImage");
                                                $('.usr_image').attr("src", "http://localhost:8080/APL/CRMDashboardController?task=viewImage");

</script>
<%@include file="/CRM Dashboard/CRM_footer.jsp" %>
