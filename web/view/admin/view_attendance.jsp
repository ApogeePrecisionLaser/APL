<%-- 
    Document   : view_attendance
    Created on : 24 Aug, 2021, 1:16:15 PM
    Author     : DELL
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="../layout/header.jsp" %>
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>

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


    $(document).ready(
            function () {
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


    $(function () {
        $("#organisation_type").autocomplete({

            source: function (request, response) {

                var random = document.getElementById("organisation_type").value;
                $.ajax({
                    url: "OrganizationNameController",
                    dataType: "json",
                    data: {action1: "getOrganisationTypeName", str: random},
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
                $('#organisation_type').val(ui.item.label); // display the selected text
                return false;
            }
        });

    });


</script>




<section>
    <div class="container-fluid page_heading sectionPadding35">
        <h1>Attendance</h1>
    </div>
</section>



<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Search Engine</h5>
        </div>
        <form name="form1" method="POST" action="AttendanceController" onsubmit="return verifySearch();" >
            <div class="row mt-3">
                <!--                <div class="col-md-3">
                                    <div class="form-group mb-md-0">
                                        <label>Organization Type</label>
                                        <input type="text" Placeholder="Organization Type" class="form-control myInput searchInput1 w-100">
                                    </div>
                                </div>-->
                <div class="col-md-12">
                    <div class="form-group mb-md-0">
                        <label>Key Person Name</label>
                        <input type="text"  id="kp_name" name="kp_name" value="" Placeholder="Key Person Name" class="form-control myInput searchInput1 w-100" >
                    </div>
                </div>

            </div>
        </form>
        <hr>
        <div class="row">
            <div class="col-md-12 text-center">
                <input type="submit" class="btn formBtn" id="hiera" name="search_org" value="SEARCH RECORDS" onclick="setStatus(id)">
            </div>
        </div>
    </div>
</section>

<section class="marginTop30 ">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Search List</h5>
        </div>
        <div class="row mt-3 myTable">
            <div class="col-md-12">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered" id="mytable" style="width:100%">
                        <thead>
                            <tr>
                                <th>S.No.</th>
                                <th>Name</th>
                                <th>Entry Time</th>                                    
                                <th>Exit Time</th>
                                <th>Latitude</th>
                                <th>Longitude</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="beanType" items="${requestScope['list']}"
                                       varStatus="loopCounter">
                                <tr
                                    onclick="fillColumn();">
                                    <td>${loopCounter.count }</td>
                                    <td id="${loopCounter.count }2">${beanType.kp_name}</td>
                                    <td id="${loopCounter.count }3">${beanType.coming_time}</td>
                                    <td id="${loopCounter.count }4">${beanType.going_time}</td>
                                    <td id="${loopCounter.count }5">${beanType.latitude}</td>
                                    <td id="${loopCounter.count }6">${beanType.longitude}</td>                            
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

    </div>
</section>








<%@include file="../layout/footer.jsp" %>
