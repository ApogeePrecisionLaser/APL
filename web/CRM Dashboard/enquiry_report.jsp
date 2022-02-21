<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>





<div class="content-wrapper" id="contentWrapper">
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2 mt-1">
                <div class="col-sm-6">
                    <h1>Sales Report</h1>
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="#">Dashboard</a></li>
                        <li class="breadcrumb-item active">Sales Report</li>
                    </ol>
                </div>
            </div>
        </div><!-- /.container-fluid -->
    </section>



    <section class="content">
        <div class="row">
            <div class="col-md-12">
                <div class="card card-primary card-outline">            
                    <div class="card-body">
                        <div class="col-md-12 px-0">
                            <form class="" action="">
                                <div class="d-flex">
                                    <div class="mr-2">
                                        <label for="email">From Date:</label>
                                        <input type="date" class="form-control rounded-0" placeholder="From Date" 
                                               name="from_date" id="from_date" value="${from_date}">
                                    </div>
                                    <div class="mr-2">
                                        <label for="pwd">To Date:</label>
                                        <input type="date" class="form-control rounded-0" placeholder="To Date" 
                                               value="${to_date}" name="to_date" id="to_date">
                                    </div>
                                    <div class="mt-2">
                                        <label for="pwd"></label>
                                        <button type="submit" class="btn myThemeBtn rounded-0 d-table">Search</button>
                                    </div>
                                    <div class="mt-2  ml-2">
                                        <label for="pwd"></label>
                                        <button type="button" class="btn myThemeBtn rounded-0 d-table" onclick="viewReport()">View Report</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <br>

                        <c:forEach var="beanType" items="${requestScope['list']}"
                                   varStatus="loopCounter">
                            <div class="row">                
                                <div class="col-md-6">
                                    <h6 class="text-info font-weight-bold">Here is your sales report for the date (${current_date})</h6> 
                                    <div class="table-responsive">
                                        <table class="table table-hover table-striped border mb-0" id="mytable">
                                            <tbody>
                                                <tr>
                                                    <td class="fontFourteen py-2 font-weight-bold">Enquiry Received Today</td>
                                                    <td class="fontFourteen py-2 font-weight-bold">${beanType.total_query_of_current_date}</td>
                                                </tr>
                                                <tr>
                                                    <td class="fontFourteen py-2 font-weight-bold">Open</td>
                                                    <td class="fontFourteen py-2 font-weight-bold">${beanType.open_query_of_current_date}</td>
                                                </tr>
                                                <tr>
                                                    <td class="fontFourteen py-2 font-weight-bold">Closed</td>
                                                    <td class="fontFourteen py-2 font-weight-bold">${beanType.closed_query_of_current_date}</td>
                                                </tr>
                                                <tr>
                                                    <td class="fontFourteen py-2 font-weight-bold">Sold</td>
                                                    <td class="fontFourteen py-2 font-weight-bold">${beanType.sold_query_of_current_date}</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <h6 class="text-info font-weight-bold">Here is your sales report till date.</h6> 
                                    <div class="table-responsive">
                                        <table class="table table-hover table-striped border mb-0" id="mytable">
                                            <tbody>
                                                <tr>
                                                    <td class="fontFourteen py-2 font-weight-bold">Total Number Of Inquiry Received</td>
                                                    <td class="fontFourteen py-2 font-weight-bold">${beanType.total_query_till_date}</td>
                                                </tr>
                                                <tr>
                                                    <td class="fontFourteen py-2 font-weight-bold">Open</td>
                                                    <td class="fontFourteen py-2 font-weight-bold">${beanType.open_query_till_date}</td>
                                                </tr>
                                                <tr>
                                                    <td class="fontFourteen py-2 font-weight-bold">Closed</td>
                                                    <td class="fontFourteen py-2 font-weight-bold">${beanType.closed_query_till_date}</td>
                                                </tr>
                                                <tr>
                                                    <td class="fontFourteen py-2 font-weight-bold">Sold</td>
                                                    <td class="fontFourteen py-2 font-weight-bold">${beanType.sold_query_till_date}</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>





<div class="modal myPopup" id="myPopModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header rounded-0">
                <div>
                    <h4 class="modal-title">Kundan Pandey</h4>
                </div>
                <button type="button" class="close text-white" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <p class="fontFourteen">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p>
            </div>
        </div>
    </div>
</div>


<%@include file="/CRM Dashboard/CRM_footer.jsp" %>
<script>

    function viewReport() {
        var queryString;
        var from_date = $('#from_date').val();
        var to_date = $('#to_date').val();
        queryString = "task=View Report&from_date=" + from_date + "&to_date=" + to_date;
        var url = "DailyEnquiryReportController?" + queryString;
        popupwin = openPopUp1(url, "Division List", 600, 1000);
    }

    function openPopUp1(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";

        return window.open(url, window_name, window_features);
    }



</script>