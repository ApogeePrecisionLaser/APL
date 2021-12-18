<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>




<div class="content-wrapper" id="contentWrapper">
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-3">
                    <h1>Add Sales Enquiry</h1>
                </div>
                <div class="col-sm-4">

                    <c:if test="${not empty message}">
                        <div class="alert alert-success alert-dismissible myAlertBox" id="msg" >
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong>Success!</strong> ${message}

                        </div>
                    </c:if>

                </div>
                <div class="col-sm-5">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="CRMDashboardController">Dashboard</a></li>
                        <li class="breadcrumb-item active">Add Sales Enquiry</li>
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
                                    <a href="SalesEnquiryController?task=sales_enquiry_list" class="btn myThemeBtn text-right">Go to Enquiries List</a>
                                </div>
                            </div>
                            <form class="myForm" action="SalesEnquiryController" method="post" style="margin-top:20px">
                                <div class="row">
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label>Enquiry Source:<sup class="text-danger">*</sup></label>

                                            <input type="text" class="form-control" required name="enquiry_source" id="enquiry_source">

                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label>Marketing Vertical:<sup class="text-danger">*</sup></label>

                                            <input type="text" class="form-control" required name="marketing_vertical" id="marketing_vertical">


                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label>Enquiry No :<sup class="text-danger">*</sup></label>
                                            <input type="text" class="form-control" required name="enquiry_no" id="enquiry_no">
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label>Sender Name:<sup class="text-danger">*</sup></label>
                                            <input type="text" class="form-control" required name="sender_name" id="sender_name">
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label>Sender Email:<sup class="text-danger">*</sup></label>
                                            <input type="email" class="form-control" required name="sender_email" id="sender_email">
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label>Sender Alternate Email:</label>
                                            <input type="email" class="form-control"  name="sender_alternate_email" id="sender_alternate_email">
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label>Sender Mobile:<sup class="text-danger">*</sup></label>
                                            <input type="text" class="form-control" required name="sender_mob" id="sender_mob">
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label>Sender Alternate Mobile:</label>
                                            <input type="text" class="form-control"  name="sender_alternate_mob" id="sender_alternate_mob">
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
                                            <label>Sender Address:</label>
                                            <input type="text" class="form-control"  name="sender_address" id="sender_address">
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


                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label>Enquiry Message:<sup class="text-danger">*</sup></label>
                                            <textarea class="form-control" required rows="4" name="enquiry_message" id="enquiry_message"></textarea>
                                        </div>
                                    </div>

                                    <div class="col-md-12">
                                        <div class="form-group mb-0 mt-3">
                                            <input type="submit" name="task" value="Submit" class="btn myThemeBtn">
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

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<link href = "https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
      rel = "stylesheet">
<script src = "https://code.jquery.com/jquery-1.10.2.js"></script>
<script src = "https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>

<script>
    var row = 1;
    $(document).on("click", "#add-row", function () {
        var new_row = '<tr id="row' + row + '"><td><input name="pr_name' + row + '" type="text" class="form-control" /></td><td><input name="pr_uniqID' + row + '" type="text" class="form-control" /></td><td><input name="pr_model' + row + '" type="text" class="form-control" /></td><td><input name="pr_qty' + row + '" type="text" class="form-control" /></td><td><input name="pr_price' + row + '" type="text" class="form-control" /></td><td><input class="delete-row btn btn-danger" type="button" value="Delete" /></td></tr>';
        // alert(new_row);
        $('#test-body').append(new_row);
        row++;
        return false;
    });
    $(document).on("click", ".delete-row", function () {
        if (row > 1) {
            $(this).closest('tr').remove();
            row--;
        }
        return false;
    });

    $(function () {
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
    });

</script>