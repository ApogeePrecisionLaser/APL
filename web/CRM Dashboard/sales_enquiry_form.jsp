<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>




<div class="content-wrapper" id="contentWrapper">
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2 marginTop10">
                <div class="col-sm-3">
                    <h1>Add Enquiry</h1>
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
                        <li class="breadcrumb-item"><a href="CRMDashboardController">Dashboard</a></li>
                        <li class="breadcrumb-item active">Add Enquiry</li>
                    </ol>
                </div>
            </div>
        </div><!-- /.container-fluid -->
    </section>


    <section class="content">
        <div class="row">
            <div class="col-md-12 px-3">
                <div class="card card-primary rounded-0 profileCard mt-2">
                    <div class="card-body px-4">
                        <div class="mt-1">

                            <div class="d-flex justify-content-between">

                                <div class="text-right">
                                    <c:if test="${user_role=='Sales'}">
                                        <a href="ApproveOrdersController?task=sales_enquiry_list" class="btn myThemeBtn text-right">Go to Enquiries List</a>
                                    </c:if>
                                    <c:if test="${user_role!='Sales'}">
                                        <a href="SalesEnquiryController?task=sales_enquiry_list" class="btn myThemeBtn text-right">Go to Enquiries List</a>
                                    </c:if>                                </div>
                            </div>
                            <form class="myForm" action="SalesEnquiryController" method="post" style="margin-top:20px">
                                <div class="row">

                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label>Enquiry Type:<sup class="text-danger">*</sup></label>
                                            <!--                                            <label>Sales</label>
                                                                                        <input type="radio"  required name="enquiry_type" id="enquiry_type" value="sales">
                                                                                        <label>Complaint</label>
                                                                                        <input type="radio"  required name="enquiry_type" id="enquiry_type" value="complaint">-->
                                        </div>


                                        <div class="d-flex justify-content-start">
                                            <div class="form-group form-check mr-3">
                                                <label class="form-check-label">
                                                    <input class="form-check-input" type="radio" name="enquiry_type" id="enquiry_type" value="Sales"> Sales
                                                </label>
                                            </div>
                                            <div class="form-group form-check">
                                                <label class="form-check-label">
                                                    <input class="form-check-input" type="radio" name="enquiry_type" id="enquiry_type" value="complaint"> Complaint
                                                </label>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label>District:<sup class="text-danger">*</sup></label>
                                            <input type="text" class="form-control" required name="district" id="district">
                                        </div>
                                    </div>

                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label>Name:<sup class="text-danger">*</sup></label>
                                            <input type="text" class="form-control" required name="sender_name" id="sender_name">
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label>Mobile:<sup class="text-danger">*</sup></label>
                                            <input type="text" class="form-control" required name="sender_mob" id="sender_mob" onblur="ValidateNo()">
                                        </div>
                                    </div>
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label>Address:</label>
                                            <textarea class="form-control" rows="2" name="sender_address" id="sender_address"></textarea>
                                        </div>
                                    </div>

                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label>Enquiry Message:</label>
                                            <textarea class="form-control" rows="4" name="enquiry_message" id="enquiry_message"></textarea>
                                        </div>
                                    </div>
                                </div>
                                <div class="row" style="display:none" id="add_info_div">

                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label>Enquiry Source:</label>

                                            <input type="text" class="form-control"  name="enquiry_source" id="enquiry_source">

                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label>Marketing Vertical:</label>

                                            <input type="text" class="form-control"  name="marketing_vertical" id="marketing_vertical">


                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label>Enquiry No :</label>
                                            <input type="text" class="form-control" name="enquiry_no" id="enquiry_no">
                                        </div>
                                    </div>

                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label>Sender Email:</label>
                                            <input type="email" class="form-control"  name="sender_email" id="sender_email" onblur="ValidateEmail()">
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label>Sender Alternate Email:</label>
                                            <input type="email" class="form-control"  name="sender_alternate_email" id="sender_alternate_email" >
                                        </div>
                                    </div>

                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label>Sender Alternate Mobile:</label>
                                            <input type="text" class="form-control"  name="sender_alternate_mob" id="sender_alternate_mob" onblur="ValidateAlternateNo()">
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label>Sender Company Name:</label>
                                            <input type="text" class="form-control"  name="sender_company_name" id="sender_company_name">
                                        </div>
                                    </div>

                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label>City:</label>
                                            <input type="text" class="form-control"  name="sender_city" id="sender_city">
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label>State:</label>
                                            <input type="text" class="form-control"  name="sender_state" id="sender_state">
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label>Country:</label>
                                            <input type="text" class="form-control"  name="sender_country" id="sender_country">
                                        </div>
                                    </div>
                                </div>

                                <div class="col-md-12">
                                    <div class="form-group mb-0 mt-3">  
                                        <input type="button" id="add_info" value="Additional Info" class="btn myThemeBtn" name="add_info">
                                        <input type="submit" name="task" value="Submit" class="btn myThemeBtn">
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

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<link href = "https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
      rel = "stylesheet">
<script src = "https://code.jquery.com/jquery-1.10.2.js"></script>
<script src = "https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>

<script>
//    var row = 1;
//    $(document).on("click", "#add-row", function () {
//        var new_row = '<tr id="row' + row + '"><td><input name="pr_name' + row + '" type="text" class="form-control" /></td><td><input name="pr_uniqID' + row + '" type="text" class="form-control" /></td><td><input name="pr_model' + row + '" type="text" class="form-control" /></td><td><input name="pr_qty' + row + '" type="text" class="form-control" /></td><td><input name="pr_price' + row + '" type="text" class="form-control" /></td><td><input class="delete-row btn btn-danger" type="button" value="Delete" /></td></tr>';
//        // alert(new_row);
//        $('#test-body').append(new_row);
//        row++;
//        return false;
//    });
//    $(document).on("click", ".delete-row", function () {
//        if (row > 1) {
//            $(this).closest('tr').remove();
//            row--;
//        }
//        return false;
//    });

                                                $(function () {


                                                    setTimeout(function () {
                                                        $('.myAlertBox').fadeOut('fast');
                                                    }, 2000);

                                                    $("#enquiry_source").autocomplete({
                                                        source: function (request, response) {
                                                            var random = $('#enquiry_source').val();
                                                            $.ajax({
                                                                url: "SalesEnquiryController",
                                                                dataType: "json",
                                                                data: {action1: "getEnquirySource", str: random},
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
                                                            $('#enquiry_source').val(ui.item.label);
                                                            return false;
                                                        }
                                                    });

                                                    $("#marketing_vertical").autocomplete({
                                                        source: function (request, response) {
                                                            var random = $('#marketing_vertical').val();
                                                            $.ajax({
                                                                url: "SalesEnquiryController",
                                                                dataType: "json",
                                                                data: {action1: "getMarketingVertical", str: random},
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
                                                            $('#marketing_vertical').val(ui.item.label);
                                                            return false;
                                                        }
                                                    });


                                                    $("#district").autocomplete({
                                                        source: function (request, response) {
                                                            var random = $('#district').val();
                                                            $.ajax({
                                                                url: "SalesEnquiryController",
                                                                dataType: "json",
                                                                data: {action1: "getDistrict", str: random},
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
                                                            $('#district').val(ui.item.label);
                                                            return false;
                                                        }
                                                    });

                                                    $("#sender_city").autocomplete({
                                                        source: function (request, response) {
                                                            var random = $('#sender_city').val();
                                                            $.ajax({
                                                                url: "SalesEnquiryController",
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
                                                            $('#sender_city').val(ui.item.label);
                                                            return false;
                                                        }
                                                    });


                                                    $("#sender_state").autocomplete({
                                                        source: function (request, response) {
                                                            var random = $('#sender_state').val();
                                                            $.ajax({
                                                                url: "SalesEnquiryController",
                                                                dataType: "json",
                                                                data: {action1: "getStates", str: random},
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
                                                            $('#sender_state').val(ui.item.label);
                                                            return false;
                                                        }
                                                    });

                                                    $("#sender_country").autocomplete({
                                                        source: function (request, response) {
                                                            var random = $('#sender_country').val();
                                                            $.ajax({
                                                                url: "SalesEnquiryController",
                                                                dataType: "json",
                                                                data: {action1: "getCountry", str: random},
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
                                                            $('#sender_country').val(ui.item.label);
                                                            return false;
                                                        }
                                                    });
                                                });

                                                $('#add_info').click(function () {
                                                    if ($('#add_info_div').css('display') == 'none') {
                                                        $('#add_info_div').show();
                                                    } else {
                                                        $('#add_info_div').hide();
                                                    }

                                                });

                                                function ValidateNo() {
                                                    var phoneNo = document.getElementById('sender_mob');

                                                    if (phoneNo.value == "" || phoneNo.value == null) {
                                                        alert("Please enter your Mobile No.");
                                                        return false;
                                                    }
                                                    if (phoneNo.value.length < 10 || phoneNo.value.length > 10) {
                                                        alert("Mobile No. is not valid, Please Enter 10 Digit Mobile No.");
                                                        return false;
                                                    }
                                                    return true;
                                                }

                                                function ValidateAlternateNo() {
                                                    var phoneNo = document.getElementById('sender_alternate_mob');

                                                    if (phoneNo.value == "" || phoneNo.value == null) {
                                                        alert("Please enter your Mobile No.");
                                                        return false;
                                                    }
                                                    if (phoneNo.value.length < 10 || phoneNo.value.length > 10) {
                                                        alert("Mobile No. is not valid, Please Enter 10 Digit Mobile No.");
                                                        return false;
                                                    }
                                                    return true;
                                                }

                                                function ValidateEmail() {
                                                    var validRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;

                                                    if (($('#sender_email').val()).match(validRegex)) {
                                                        return true;

                                                    } else {
                                                        alert("Invalid email address!");
                                                        document.getElementById("sender_email").focus();
                                                        return false;

                                                    }

                                                }

</script>
