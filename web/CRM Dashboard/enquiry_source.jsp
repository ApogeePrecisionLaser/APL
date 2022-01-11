<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>




<div class="content-wrapper" id="contentWrapper">
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2 marginTop10">
                <div class="col-sm-6">
                    <div>
                        <h1>Enquiry Source</h1>
                    </div>
                    <c:if test="${not empty message}">
                    <div class="alert alert-success alert-dismissible myAlertBox" id="msg" >
                        <button type="button" class="close" data-dismiss="alert">&times;</button>
                        <strong>Success!</strong> ${message}

                    </div>
                    </c:if>


                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="CRMDashboardController">Dashboard</a></li>
                        <li class="breadcrumb-item active">Enquiry Source</li>
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
                                                <th>S.No.</th>
                                                <th>Enquiry Source</th> 
                                                <th>Description</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="beanType" items="${requestScope['list']}"
                                                       varStatus="loopCounter">
                                                <tr>
                                            <input type="hidden" name="enquiry_source" id="enquiry_source${beanType.enquiry_source_table_id}" value="${beanType.enquiry_source}">
                                            <input type="hidden" name="enquiry_description" id="enquiry_description${beanType.enquiry_source_table_id}" value="${beanType.description}">
                                            <td class="fontFourteen">${loopCounter.count}</td>
                                            <td class="fontFourteen">${beanType.enquiry_source}</td> 

                                            <td class="fontFourteen">${beanType.description}</td> 
                                            <td class="fontFourteen d-flex">
                                                <div>
                                                    <a  class="btn far fa-edit actionEdit" title="Edit" onclick="editEnquirySource('${beanType.enquiry_source_table_id}')"></a>
                                                    <a onclick="deleteEnquirySource('${beanType.enquiry_source_table_id}')" class="btn far fa-times-circle actionDelete" title="Delete Enquiry Source"></a>
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


    <section class="content">
        <div class="row">
            <div class="col-md-12 px-3">
                <div class="card card-primary rounded-0 profileCard mt-2">
                    <div class="card-body px-4">
                        <div class="mt-1">
                            <form class="myForm" action="EnquirySourceController" method="post">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label>Enquiry Source:<sup class="text-danger">*</sup></label>
                                            <input type="text" class="form-control" required name="enquiry_source_name" id="enquiry_source_name">
                                            <input type="hidden" name="enquiry_source_table_id" id="enquiry_source_table_id" value="">

                                        </div>
                                    </div>

                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label>Description</label>
                                            <textarea class="form-control"  rows="4" id="description" name="description"></textarea>
                                        </div>
                                    </div>

                                    <div class="col-md-12">
                                        <div class="form-group mb-0 mt-3">
                                            <input type="submit" class="btn myThemeBtn" value="Submit" name="task">
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

    function editEnquirySource(enquiry_source_table_id) {
        var enquiry_source_name = $('#enquiry_source' + enquiry_source_table_id).val();
        var enquiry_description = $('#enquiry_description' + enquiry_source_table_id).val();
        $('#enquiry_source_name').val(enquiry_source_name);
        $('#description').val(enquiry_description);
        $('#enquiry_source_table_id').val(enquiry_source_table_id);
    }

    function deleteEnquirySource(enquiry_source_table_id) {
        var result = confirm('Are you sure you want to delete source ?');
        if (result == true) {
            $.ajax({
                url: "EnquirySourceController",
                dataType: "json",
                data: {task: "deleteEnquirySource", enquiry_source_table_id: enquiry_source_table_id},
                success: function (data) {
                    if (data.msg != '') {
                        $('.myAlertBox').show();
                        $('.myAlertBox').text(data.msg);
                        window.location.reload();
                    }

                }
            });
        } else {
            return;
        }


    }

    $(document).ready(function () {
        setTimeout(function () {
            $('.myAlertBox').fadeOut('fast');
        }, 1000);

    })
</script>
