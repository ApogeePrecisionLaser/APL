<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>



<div class="content-wrapper" id="contentWrapper">
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2 marginTop10">
                <div class="col-sm-6">
                    <div class="d-flex">
                        <div class="mr-2">
                            <a href="SalesEnquiryController" class="btn btn-primary myNewLinkBtn fontFourteen">Add New Enquiry</a>
                        </div>

                        <form name="my-form" method="post" action="ApproveOrdersController?task=complaint_enquiry_list" class="d-flex">
                            <div class="mr-2 selectEnquiryWrap">
                                <input type="text" name="enquiry_source" id="enquiry_source" class="form-control" value="${enquiry_source}"> 
                            </div>
                            <div class="mr-2 selectEnquiryWrap">
                                <input type="text" name="status" id="status" class="form-control" value="${status}"> 
                            </div>
                            <div class="mr-2 enqStatusBtnWrap" id="enqStatusBtnWrap">
                                <input type="submit" value="Search"
                                       class="btn btn-primary myNewLinkBtn fontFourteen" name="search" id="search" class="form-control"> 
                            </div>
                        </form>
                        <div class="position-relative">
                            <div class="alert alert-success alert-dismissible myAlertBox" style="display:none">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong>Success!</strong> Indicates a successful or positive action.
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
                                <div class="table-responsive tableScrollWrap noWrapTable" >
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
                                                <th class="fontFourteen">Product Name</th>
                                                <!-- <th>Sender Company Name</th> -->
                                                <!-- <th>Sender Address</th> -->
                                                <th class="fontFourteen">City</th>
                                                <th class="fontFourteen">State</th>
                                                <!--<th class="fontFourteen">District</th>-->
                                                <th class="fontFourteen">Time Ago</th>
                                                <!-- <th>Enquiry Message</th> -->
                                                <th class="fontFourteen">Status</th>
                                                <th class="fontFourteen">Assigned To</th>
                                                <th class="fontFourteen">Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="beanType" items="${requestScope['list']}"
                                                       varStatus="loopCounter">
                                                <tr>
                                                    <td class="fontFourteen">${loopCounter.count} <input type="hidden" name="district${beanType.enquiry_table_id}" value="${beanType.description}" id="district${beanType.enquiry_table_id}"></td>
                                                    <!--<td class="fontFourteen">${beanType.enquiry_source}</td>--> 
                                                    <!--<td class="fontFourteen">${beanType.marketing_vertical_name}</td>-->
                                                    <!--<td class="fontFourteen">${beanType.enquiry_no}</td>--> 
                                                    <td class="fontFourteen">${beanType.sender_name}</td>
                                                    <!--<td class="fontFourteen">${beanType.sender_email}</td>-->
                                                    <td class="fontFourteen"><a href="tel:+${beanType.sender_mob}">${beanType.sender_mob}</a></td>
                                                    <!-- <td class="fontFourteen">ABC Ltd</td> -->
                                                    <!-- <td class="fontFourteen">80/3 Harinagar, Jaitpur, Badarpur, New Delhi 110044</td> -->
                                                    <td class="fontFourteen">${beanType.product_name}</td>
                                                    <td class="fontFourteen">${beanType.enquiry_city}</td>
                                                    <td class="fontFourteen">${beanType.enquiry_state}</td>
                                                    <!--<td class="fontFourteen">${beanType.description}</td>-->
                                                    <td class="fontFourteen">${beanType.enquiry_date_time}</td>

                                                    <!-- <td class="fontFourteen">It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.</td> -->
                                                    <td  class="fontFourteen">


                                                        <c:choose>
                                                            <c:when test="${beanType.status =='Assigned To SalesManager'}">
                                                                <div class="salesManAppInputWrap">
                                                                    <input type="text" name="dealers" class="dealers form-control" id="dealers${beanType.enquiry_table_id}">
                                                                    <a onclick="assignComplaintToDealer('${beanType.enquiry_table_id}')" class="btn actionEdit fontFourteen px-1" title="Assigned To Dealer"><i class="far fa-share-square"></i></a>
                                                                </div>
                                                            </c:when>
                                                            <c:when test="${beanType.status =='Assigned To Dealer'}">
                                                                <button class="btn assigneDealer fontFourteen" disabled>Assigned </button>
                                                            </c:when>

                                                            <c:when test="${beanType.status =='Open' || beanType.status =='Call' ||  beanType.status =='Follow Up'}">
                                                                <button class="btn inConversation fontFourteen" disabled>${beanType.status} </button>
                                                            </c:when>
                                                            <c:when test="${beanType.status =='Irrelevant' || beanType.status =='Not Interested'
                                                                            || beanType.status =='Purchased From Others'}">
                                                                    <button class="btn enquiryFailed fontFourteen" disabled>${beanType.status} </button>
                                                            </c:when>
                                                            <c:when test="${beanType.status =='Sold'}">
                                                                <button class="btn enquiryPassed fontFourteen" disabled>${beanType.status} </button>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <button class="btn myBtnDanger fontFourteen" disabled>${beanType.status} </button>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>${beanType.assigned_to}</td>
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
<!--
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<link href = "https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
      rel = "stylesheet">
<script src = "https://code.jquery.com/jquery-1.10.2.js"></script>
<script src = "https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>-->
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
            var district = $('#district' + id.substring(7)).val();
            $('#' + id).autocomplete({
                source: function (request, response) {
                    $.ajax({
                        url: "ApproveOrdersController",
                        dataType: "json",
                        data: {
                            action1: "getDealersStateWise",
                            str: random,
                            district: district
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
        $("#status").autocomplete({
            source: function (request, response) {
                var random = $('#status').val();
                $.ajax({
                    url: "SalesEnquiryController",
                    dataType: "json",
                    data: {action1: "getStatus", str: random},
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
                $('#status').val(ui.item.label);
                return false;
            }
        });
    })
</script>
