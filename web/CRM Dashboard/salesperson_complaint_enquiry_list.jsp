<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>



<div class="content-wrapper" id="contentWrapper">
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <div class="d-flex">
                        <!--                        <div>
                                                    <a href="SalesEnquiryController" class="btn btn-primary myNewLinkBtn">Add New Enquiry</a>
                                                </div>-->
                        <div class="position-relative">
                            <div class="alert alert-success alert-dismissible myAlertBox" style="display:none"  id="msg">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong>Success!</strong>
                            </div>
                        </div>
                    </div>  
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="CRMDashboardController">Dashboard</a></li>
                        <li class="breadcrumb-item active">Complaint Enquiry List</li>
                    </ol>
                </div>
            </div>
        </div><!-- /.container-fluid -->
    </section>
    <section class="content">
        <div class="container-fluid">              
            <div class="row mt-0">
                <div class="col-md-12">
                    <div class="card card-primary card-outline">            
                        <div class="card-body">
                            <div>
                                <div class="table-responsive tableScrollWrap" >
                                    <table class="table table-striped1 mainTable" id="mytable" >
                                        <thead>
                                            <tr>
                                                <th class="fontFourteen">Sl. No.</th>
                                                <!--<th>Enquiry Source</th>--> 
                                                <!--<th>Marketing Vertical</th>-->
                                                <!--<th>Enquiry No</th>--> 
                                                <th class="fontFourteen">Sender Name</th>
                                                <!--<th>Sender Email</th>-->
                                                <th class="fontFourteen">Sender Mobile</th>
                                                <!-- <th>Sender Company Name</th> -->
                                                <!-- <th>Sender Address</th> -->
                                                <!--<th>City</th>-->
                                                <th class="fontFourteen">District</th>
                                                <th class="fontFourteen">Time Ago</th>
                                                <!-- <th>Enquiry Message</th> -->
                                                <th class="fontFourteen">Status</th>
                                                <th class="fontFourteen">Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="beanType" items="${requestScope['list']}"
                                                       varStatus="loopCounter">
                                                <tr>
                                                    <td class="fontFourteen">${loopCounter.count}</td>
                                                    <!--<td class="fontFourteen">${beanType.enquiry_source}</td>--> 
                                                    <!--<td class="fontFourteen">${beanType.marketing_vertical_name}</td>-->
                                                    <!--<td class="fontFourteen">${beanType.enquiry_no}</td>--> 
                                                    <td class="fontFourteen">${beanType.sender_name}</td>
                                                    <!--<td class="fontFourteen">${beanType.sender_email}</td>-->
                                                    <td class="fontFourteen">${beanType.sender_mob}</td>
                                                    <!-- <td class="fontFourteen">ABC Ltd</td> -->
                                                    <!-- <td class="fontFourteen">80/3 Harinagar, Jaitpur, Badarpur, New Delhi 110044</td> -->
                                                    <!--<td class="fontFourteen">${beanType.enquiry_city}</td>-->
                                                    <td class="fontFourteen">${beanType.description}</td>
                                                    <td class="fontFourteen">${beanType.enquiry_date_time}</td>

                                                    <!-- <td class="fontFourteen">It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.</td> -->
                                                    <td  class="fontFourteen">

                                                        <c:choose>
                                                            <c:when test="${beanType.status =='Assigned To SalesManager'}">
                                                                <input type="text" name="dealers" class="dealers" id="dealers${beanType.enquiry_table_id}">
                                                                <a onclick="assignComplaintToDealer('${beanType.enquiry_table_id}')" class="btn btn-info" title="Assigned To Dealer">Assign To Dealer</a>
                                                            </c:when>
                                                            <c:when test="${beanType.status=='Assigned To Dealer'}">
                                                                <button class="btn btn-danger" disabled>In Conversation</button>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <button class="btn btn-danger" disabled>${beanType.status}</button>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>

                                                    <td class="fontFourteen d-flex">
                                                        <div>
                                                            <a href="ApproveOrdersController?task=viewComplaintDetails&enquiry_table_id=${beanType.enquiry_table_id}" class="btn far fa-eye actionEdit" title="View Complaint Detail"></a>

                                                        </div> 
                                                    </td>
                                                </tr> 
                                            </c:forEach>
                                        </tbody>
                                    </table>
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

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<link href = "https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
      rel = "stylesheet">
<script src = "https://code.jquery.com/jquery-1.10.2.js"></script>
<script src = "https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script>
//                                                                    $(function () {
//                                                                        $(".dealers").autocomplete({
//                                                                            source: function (request, response) {
//                                                                                var random = $('.dealers').val();
//                                                                                $.ajax({
//                                                                                    url: "ApproveOrdersController",
//                                                                                    dataType: "json",
//                                                                                    data: {action1: "getDealersStateWise", str: random},
//                                                                                    success: function (data) {
//                                                                                        console.log(data);
//                                                                                        response(data.list);
//                                                                                    }, error: function (error) {
//                                                                                        console.log(error.responseText);
//                                                                                        response(error.responseText);
//                                                                                    }
//                                                                                });
//                                                                            },
//                                                                            select: function (events, ui) {
//                                                                                console.log(ui);
//                                                                                $('.dealers').val(ui.item.label);
//                                                                                return false;
//                                                                            }
//                                                                        });
//                                                                    });


                                                                    $(function () {
                                                                        $(document).on('keydown', '.dealers', function () {
                                                                            var id = this.id;
                                                                            var random = this.value;
                                                                            $('#' + id).autocomplete({
                                                                                source: function (request, response) {
                                                                                    $.ajax({
                                                                                        url: "ApproveOrdersController",
                                                                                        dataType: "json",
                                                                                        data: {
                                                                                            action1: "getDealersStateWise",
                                                                                            str: random
                                                                                        },
                                                                                        success: function (data) {
                                                                                            console.log(data);
                                                                                            response(data.list);
                                                                                        },
                                                                                        error: function (error) {
                                                                                            console.log(error.responseText);
                                                                                            response(error.responseText);
                                                                                        }
                                                                                    });
                                                                                },
                                                                                select: function (events, ui) {
                                                                                    console.log(ui);
                                                                                    $(this).val(ui.item.label); // display the selected text
                                                                                    return false;
                                                                                }
                                                                            });
                                                                        });
                                                                    }
                                                                    );

                                                                    function assignComplaintToDealer(enquiry_table_id) {
                                                                        var dealer_name = $('#dealers' + enquiry_table_id).val();
                                                                        $.ajax({
                                                                            url: "ApproveOrdersController",
                                                                            dataType: "json",
                                                                            data: {task: "assignComplaintToDealer", enquiry_table_id: enquiry_table_id, dealer_name: dealer_name},
                                                                            success: function (data) {
                                                                                if (data.message != '') {
                                                                                    $('#msg').text(data.message);
                                                                                    $('.myAlertBox').show();
                                                                                    setTimeout(function () {
                                                                                        $('#msg').fadeOut('fast');
                                                                                    }, 1000);
                                                                                    window.location.reload();
                                                                                } else {
                                                                                    $('.myAlertBox').hide();
                                                                                }
                                                                            }
                                                                        });
                                                                    }
</script>