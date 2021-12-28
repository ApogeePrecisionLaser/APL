<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="../layout/header.jsp" %>

<style>
    .selected_row {
        font-weight: bolder;
        color: blue;
        border: 3px solid black;
    }
    table.dataTable {      
        border-collapse: collapse;
    }
</style>

<script>
    $(function () {
        setTimeout(function () {
            $('#message').fadeOut('fast');
        }, 10000);

    });
    $(function () {
        $("#salesmanname").autocomplete({

            source: function (request, response) {
                var random = document.getElementById("salesmanname").value;
                $.ajax({
                    url: "SalesManagerStateMappingController",
                    dataType: "json",
                    data: {action1: "getSalesDealer", str: random},
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
                $('#salesmanname').val(ui.item.label); // display the selected text
                return false;
            }
        });


        $("#sales_search").autocomplete({

            source: function (request, response) {
                var random = document.getElementById("sales_search").value;
                $.ajax({
                    url: "SalesManagerStateMappingController",
                    dataType: "json",
                    data: {action1: "getSalesDealer", str: random},
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
                $('#sales_search').val(ui.item.label); // display the selected text
                return false;
            }
        });
        $("#searchstate").autocomplete({

            source: function (request, response) {
                var random = document.getElementById("searchstate").value;
                $.ajax({
                    url: "SalesManagerStateMappingController",
                    dataType: "json",
                    data: {action1: "getState", str: random},
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
                $('#searchstate').val(ui.item.label); // display the selected text
                return false;
            }
        });

        $("#state").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("state").value;

                $.ajax({
                    url: "SalesManagerStateMappingController",
                    dataType: "json",
                    data: {action1: "getState", str: random},
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
                $('#state').val(ui.item.label); // display the selected text
                return false;
            }
        });


    });
    function fillColumn(id, count) {
        $('#salesmanager_state_mapping_id').val(id);
        $('#salesmanname').val($("#" + count + '2').html());
        $('#state').val($("#" + count + '3').html());
        $('#remark').val($("#" + count + '4').html());

        $('#edit').attr('disabled', false);
        $('#delete').attr('disabled', false);
    }





    var editable = false;
    function makeEditable(id) {
        document.getElementById("salesmanname").disabled = false;
        document.getElementById("state").disabled = false;
        document.getElementById("remark").disabled = false;
        document.getElementById("save").disabled = false;
        if (id == 'new') {
            editable = "false";
            // document.getElementById("message").innerHTML = "";      // Remove message
            $("#message").html("");
            document.getElementById("org_office_id").value = "";
            document.getElementById("serialnumber").disabled = false;
            document.getElementById("supern").disabled = false;
            document.getElementById("supery").disabled = false;
            document.getElementById("edit").disabled = true;
            document.getElementById("delete").disabled = true;
            // document.getElementById("save_As").disabled = true;
            document.getElementById("get_cordinate").disabled = false;
            // setDefaultColordOrgn(document.getElementById("noOfRowsTraversed").value, 17);
            document.getElementById("organisation_name").focus();
        }
        if (id == 'edit') {
            editable = "true";
            // document.getElementById("save_As").disabled = false;
            document.getElementById("delete").disabled = false;
            document.getElementById("serialnumber").disabled = false;
            document.getElementById("supern").disabled = false;
            document.getElementById("supery").disabled = false;
            document.getElementById("get_cordinate").disabled = false;
        }

    }



    $(document).ready(function () {
        $('#mytable tbody').on(
                'click',
                'tr',
                function () {

                    if ($(this).hasClass('selected_row')) {
                        $(this).removeClass('selected_row');
                    } else {
                        $("#mytable").DataTable().$(
                                'tr.selected_row').removeClass(
                                'selected_row');
                        $(this).addClass('selected_row');
                    }
                });
    });


</script>




<section>
    <div class="container-fluid page_heading sectionPadding35">
        <h1>SalesManager State Mapping</h1>
    </div>
</section>



<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Search Engine</h5>
        </div>
        <form name="form1" method="POST" action="SalesManagerStateMappingController" onsubmit="return verifySearch();" >
            <div class="row mt-3">
                <div class="col-md-4 mb-md-2">
                    <div class="form-group mb-md-0">
                        <label>SalesManName</label>
                        <input type="text"  class="form-control myInput" id="sales_search" name="sales_search" 
                               value="${sales_search}" size="20">
                    </div>
                </div>
                <div class="col-md-4 mb-md-2">
                    <div class="form-groupmb-md-0">
                        <label>State Name</label>
                        <input type="text" class="form-control myInput" id="searchstate" name="searchstate" value="${searchstate}" size="20">
                    </div>
                </div>



            </div>

            <hr>
            <div class="row">
                <div class="col-md-12 text-center">
                    <input type="submit" class="btn normalBtn" id="search_org" name="search_org" value="SEARCH RECORDS" onclick="setStatus(id)">
                </div>
            </div>
        </form>
    </div>
</section>

<section class="marginTop30 ">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Search List</h5>
        </div>
        <div class="row mt-3 myTable">
            <div class="col-md-12">
                <div class="table-responsive verticleScroll">
                    <table class="table table-striped table-bordered" id="mytable" style="width:100%" data-page-length='6'>
                        <thead>
                            <tr>   
                                <th>S.No.</th>


                                <th>Salesman Name</th>
                                <th>State Name</th>
                                <th>Remark</th>


                            </tr>
                        </thead>
                        <tbody>   
                            <c:forEach var="bean" items="${requestScope['list']}" varStatus="loopCounter">
                                <tr
                                    onclick="fillColumn('${bean.map_id}', '${loopCounter.count }');">
                                    <td>${loopCounter.count }</td>        
                                    <td id="${loopCounter.count }2">${bean.salesmanname}</td>
                                    <td id="${loopCounter.count }3">${bean.state_name}</td>

                                    <td id="${loopCounter.count }4">${bean.remark}</td>


                                    <!--                                    <td>
                                                                            <input type="button" class="btn normalBtn"  value ="View Map" id="map_container${loopCounter.count}" onclick="openMap('${organisation.map_id}');"/>
                                                                        </td>-->
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

    </div>
</section>




<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Data Entry</h5>
        </div>
        <form name="form2" method="POST" action="SalesManagerStateMappingController" onsubmit="return verify()">
            <div class="row mt-3">
                <div class="col-md-12">
                    <div class="">
                        <div class="form-group">
                            <label>Select SalesMan  <span class="text-danger">*</span></label>                            
                            <input type="hidden" class="form-control myInput" id="salesmanager_state_mapping_id" name="salesmanager_state_mapping_id" value="" >
                            <input class="form-control myInput" type="text" id="salesmanname" name="salesmanname" value="${after_save_organisation}" disabled>
                        </div>
                    </div>
                </div>



            </div>
            <div class="row mt-3">
                <div class="col-md-12">
                    <div class="">
                        <div class="form-group">
                            <label>Select State<span class="text-danger">*</span></label>                            
                            <input class="form-control myInput"  type="text" id="state" name="state" value="" disabled>
                        </div>
                    </div>
                </div>

            </div>


            <div class="row mt-3">
                <div class="col-md-12">
                    <div class="">
                        <div class="form-group">
                            <label>Remark<span class="text-danger">*</span></label>                            
                            <input class="form-control myInput"  type="text" id="remark" name="remark" value="" disabled>
                        </div>
                    </div>
                </div>

            </div>
    </div>

    <div class="col-md-3">
        <div class="">
            <div class="form-group">
                <!--<label>Generation<span class="text-danger">*</span></label>-->
                <input class="form-control myInput" type="text" id="generation" name="generation" value="" size="45" disabled hidden>
            </div>
        </div>
    </div>
    <hr>
    <div class="row">
        <div id="message">
            <c:if test="${not empty message}">
                <div class="col-md-12 text-center">
                    <label style="color:${msgBgColor}"><b>Result: ${message}</b></label>
                </div>
            </c:if>
        </div>
        <input type="hidden" id="clickedButton" value="">
        <div class="col-md-12 text-center"> 
            <input type="button" class="btn normalBtn" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled>
            <input type="submit" class="btn normalBtn" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>
            <input type="reset" class=" btn normalBtn" name="new" id="new" value="New" onclick="makeEditable(id)" >
            <input type="submit" class="btn normalBtn" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
        </div>
    </div>
</form>
</div>
</section>



<%@include file="../layout/footer.jsp" %>

