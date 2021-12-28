<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>




<div class="content-wrapper" id="contentWrapper">
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
<!--                <div class="col-sm-2">
                    <h1>Dealer Item Map 1</h1>
                </div>-->

                <div class="col-sm-6">
                    <div class="d-flex">
                        <div class="mr-2">
                            <a href="DealersController" class="btn btn-primary myNewLinkBtn">Back</a>
                        </div>
<!--                        <div class="mr-2">
                            <h1>Dealer Item Map</h1>
                        </div>-->
                        <c:if test="${not empty message}">
                            <div class="alert alert-success alert-dismissible myAlertBox" id="msg" >
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong>Success!</strong> ${message}

                            </div>
                        </c:if>

                    </div>
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="CRMDashboardController">Dashboard</a></li>
                        <li class="breadcrumb-item active">Dealer Item Map </li>
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
                                                <th>S.No.</th>
                                                <th>Dealer</th> 
                                                <th>Item</th> 
                                                <th>Model</th> 
                                                <th>Description</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="beanType" items="${requestScope['list']}"
                                                       varStatus="loopCounter">
                                                <tr>
                                            <input type="hidden" name="dealer_item_map_id" id="dealer_item_map_id${beanType.dealer_item_map_id}" value="${beanType.dealer_item_map_id}">
                                            <td class="fontFourteen">${loopCounter.count}</td>
                                            <td class="fontFourteen">${beanType.org_office_name}</td> 
                                            <td class="fontFourteen">${beanType.item_name}</td> 
                                            <td class="fontFourteen">${beanType.model}</td> 
                                            <td class="fontFourteen">${beanType.description}</td> 
                                            <td class="fontFourteen d-flex">
                                                <div>
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
                            <form class="myForm" action="DealerItemMapController" method="post">
                                <div class="row">
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label>Dealer:<sup class="text-danger">*</sup></label>
                                            <input type="text" disabled class="form-control" required name="dealer" id="dealer" value="${org_office_name}">
                                            <input type="hidden" name="dealer_item_map_id" id="dealer_item_map_id" value="">
                                            <input type="hidden" name="org_office_id" id="org_office_id" value="${org_office_id}">

                                        </div>
                                    </div>

                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label>Items:<sup class="text-danger">*</sup></label>
                                            <input type="text" class="form-control" required name="item" id="item" style="position:relative">
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label>Model:<sup class="text-danger">*</sup></label>
                                            <input type="text" class="form-control" required name="model" id="model">
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

<!--<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>-->
<!--<link href = "https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel = "stylesheet">-->


<!--<script src = "https://code.jquery.com/jquery-1.10.2.js"></script>
<script src = "https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>-->

<script>
    
    $(function () {
        $("#item").autocomplete({
            source: function (request, response) {
                var random = $('#item').val();
                var org_office_id = $('#org_office_id').val();
                $.ajax({
                    url: "DealerItemMapController",
                    dataType: "json",
                    data: {action1: "getItemName", str: random, org_office_id: org_office_id},
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
                $('#item').val(ui.item.label);
                return false;
            }
        });

        $("#model").autocomplete({
            source: function (request, response) {
                var random = $('#model').val();
                var item = $('#item').val();
                $.ajax({
                    url: "DealerItemMapController",
                    dataType: "json",
                    data: {action1: "getModel", str: random, item: item},
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
                $('#model').val(ui.item.label);
                return false;
            }
        });
    });

    $(document).ready(function () {
        setTimeout(function () {
            $('.myAlertBox').fadeOut('fast');
        }, 1000);

    })
</script>