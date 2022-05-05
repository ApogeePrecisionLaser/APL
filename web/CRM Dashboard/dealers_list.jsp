<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/CRM Dashboard/CRM_header.jsp" %>
<div class="content-wrapper" id="contentWrapper">
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-1">
                <div class="col-sm-6">            
                    <div class="d-flex">
                        <div>
                            <h1>Dealer List</h1>
                        </div>
                        <!--                        <div class="position-relative">
                                                    <div class="alert alert-success alert-dismissible myAlertBox">
                                                        <button type="button" class="close" data-dismiss="alert">&times;</button>
                                                        <strong>Success!</strong> Indicates a successful or positive action.
                                                    </div>
                                                </div>-->
                    </div>  
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="CRMDashboardController">Dashboard</a></li>
                        <li class="breadcrumb-item active">Dealer List</li>
                    </ol>
                </div>
            </div>
        </div><!-- /.container-fluid -->
    </section>

    <section class="content">
        <div class="row">
            <div class="col-md-12">
                <div class="card card-primary card-outline">            
                    <div class="card-body pt-2"> 
                        <form id="frm-example" action="EventController" method="post">     
                            <input type="hidden" name='events_id' value="${events_id}" id='events_id'>
                            <div>
                                <div class="mb-2 text-right mr-2">
                                    <button class="btn myThemeBtn text-right" type="submit" name='task' value="sendEventsToDealer">Send <i class="fas fa-paper-plane"></i></button>
                                </div>
                            </div>
                            <div class="table-responsive mailbox-messages tableScrollWrap">
                                <table class="table table-hover table-striped" id="mytable1">
                                    <thead>
                                        <tr>
                                            <th class="pl-2">
                                                <div class="form-check">
                                                    <label class="form-check-label font-weight-medium">
                                                        <input type="checkbox" class="form-check-input" value="" name="select_all" id="example-select-all">All
                                                    </label>
                                                </div>
                                            </th>
                                            <th class="pl-2">Office Name</th>
                                            <th class="pl-2">Name</th>
                                            <th class="pl-2">Mobile</th>
                                            <th class="pl-2">Email</th>
                                            <th class="pl-2">City</th>
                                            <!--<th class="pl-2">Action</th>-->
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="beanType" items="${requestScope['dealers_list']}"
                                                   varStatus="loopCounter">
                                            <tr>
                                                <td class="fontFourteen">
                                                    <div class="">
                                                        <c:choose>
                                                            <c:when test="${beanType.checked=='yes'}">
                                                                <input type="checkbox" checked class="form-check-input1 chkboxes" value="${beanType.key_person_id}" 
                                                                       name='check' id='check${counter}'>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input type="checkbox" class="form-check-input1 chkboxes" value="${beanType.key_person_id}" 
                                                                       name='check' id='check${counter}'>
                                                            </c:otherwise>
                                                        </c:choose>

                                                    </div>
                                                </td>
                                                <td class="fontFourteen">${beanType.org_office}</td>
                                                <td class="fontFourteen">${beanType.key_person}</td>
                                                <td class="fontFourteen">${beanType.key_person_mobile}</td>
                                                <td class="fontFourteen">${beanType.key_person_email}</td>
                                                <td class="fontFourteen">${beanType.city}</td>
                                                <!--                                                <td class="text-right">
                                                                                                    <a href="profile.php" class="btn far fa-eye actionEdit" title="See Detail"></a>
                                                                                                    <a onclick="return confirm('Are you sure you want to delete this message?');" href="#" class="btn far fa-trash-alt actionDelete" title="Delete Message"></a>
                                                                                                </td>-->
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!--                            <div class="mt-2 text-right mr-2">
                                                            <button class="btn myThemeBtn text-right" type="submit" name='task' value="dealers_list" onclick="sendData()">Send <i class="fas fa-paper-plane"></i></button>
                                                        </div>-->
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>

<%@include file="/CRM Dashboard/CRM_footer.jsp" %>
<script>
//    var table = $('#mytable').DataTable({
//
//    });

    $('#example-select-all').on('click', function () {
        // Get all rows with search applied
//        var rows = table.rows({'search': 'applied'}).nodes();
        // Check/uncheck checkboxes for all rows in the table
        $('input[type="checkbox"]').prop('checked', this.checked);
    });

    // Handle click on checkbox to set state of "Select all" control
    $('#mytable tbody').on('change', 'input[type="checkbox"]', function () {
        // If checkbox is not checked
        if (!this.checked) {
            var el = $('#example-select-all').get(0);
            // If "Select all" control is checked and has 'indeterminate' property
            if (el && el.checked && ('indeterminate' in el)) {
                // Set visual state of "Select all" control
                // as 'indeterminate'
                el.indeterminate = true;
            }
        }
    });
    $(document).ready(function () {
        $('#mytable1').DataTable({
            "lengthMenu": [[-1], ["All"]]
        });
    });


</script>