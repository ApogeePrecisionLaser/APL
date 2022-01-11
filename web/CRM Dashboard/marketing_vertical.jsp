<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>




<div class="content-wrapper" id="contentWrapper">
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2 marginTop10">
                
                <div class="col-sm-6">
                    <div class="">
                        <h1>Marketing Vertical</h1>
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
                        <li class="breadcrumb-item active">Marketing Vertical</li>
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
                                                <th>Marketing Vertical</th> 
                                                <th>Description</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="beanType" items="${requestScope['list']}"
                                                       varStatus="loopCounter">
                                                <tr>
                                            <input type="hidden" name="marketing_vertical" id="marketing_vertical${beanType.marketing_vertical_id}" value="${beanType.marketing_vertical_name}">
                                            <input type="hidden" name="marketing_vertical_description" id="marketing_vertical_description${beanType.marketing_vertical_id}" value="${beanType.description}">
                                            <td class="fontFourteen">${loopCounter.count}</td>
                                            <td class="fontFourteen">${beanType.marketing_vertical_name}</td> 

                                            <td class="fontFourteen">${beanType.description}</td> 
                                            <td class="fontFourteen d-flex">
                                                <div>
                                                    <a  class="btn far fa-edit actionEdit" title="Edit" onclick="editMarketingVertical('${beanType.marketing_vertical_id}')"></a>
                                                    <a onclick="deleteMarketingVertical('${beanType.marketing_vertical_id}')" class="btn far fa-times-circle actionDelete" title="Delete Marketing Vertical"></a>
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
                            <form class="myForm" action="MarketingVerticalController" method="post">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label>Marketing Vertical:<sup class="text-danger">*</sup></label>
                                            <input type="text" class="form-control" required name="marketing_vertical_name" id="marketing_vertical_name">
                                            <input type="hidden" name="marketing_vertical_id" id="marketing_vertical_id" value="">

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

    function editMarketingVertical(marketing_vertical_id) {
        var marketing_vertical_name = $('#marketing_vertical' + marketing_vertical_id).val();
        var description = $('#marketing_vertical_description' + marketing_vertical_id).val();
        $('#marketing_vertical_name').val(marketing_vertical_name);
        $('#description').val(description);
        $('#marketing_vertical_id').val(marketing_vertical_id);
    }

    function deleteMarketingVertical(marketing_vertical_id) {
        var result = confirm('Are you sure you want to delete Marketing Vertical ?');
        if (result == true) {
            $.ajax({
                url: "MarketingVerticalController",
                dataType: "json",
                data: {task: "deleteMarketingVertical", marketing_vertical_id: marketing_vertical_id},
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
