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


    $(document).ready(function () {
        $('#mytable tbody').on('click', 'tr', function () {
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
        $("#key_person").autocomplete({

            source: function (request, response) {
                var random = document.getElementById("key_person").value;
                $.ajax({
                    url: "AttendanceController",
                    dataType: "json",
                    data: {action1: "getKeyPerson", str: random},
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
                $('#key_person').val(ui.item.label); // display the selected text
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
        <form name="form1" method="POST" action="AttendanceController">
            <div class="row mt-3">
                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Key Person</label>
                        <input type="text" Placeholder="Key Person" name="key_person" id="key_person" value="${key_person}" class="form-control myInput searchInput1 w-100">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Date</label>
                        <input type="date" name="date" id="date" value="" Placeholder="Item Name" class="form-control myInput searchInput1 w-100" >
                    </div>
                </div>
            </div>

            <hr>
            <div class="row">
                <div class="col-md-12 text-center">
                    <input type="submit" class="btn formBtn" id="search_record" name="search_record" value="SEARCH RECORDS">
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
                                <th>Name</th>
                                <th>Contact No.</th>
                                <th>Date</th>
                                <th>Entry Time</th>                                    
                                <th>Exit Time</th>
                                <th>Distance(Km)</th>
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
                                    <td id="${loopCounter.count }2">${beanType.contact}</td>
                                    <td id="${loopCounter.count }2">${beanType.date}</td>
                                    <td id="${loopCounter.count }3">${beanType.coming_time}</td>
                                    <td id="${loopCounter.count }4">${beanType.going_time}</td>
                                    <td id="${loopCounter.count }5">${beanType.distance_between}</td>
                                    <td id="${loopCounter.count }6">${beanType.latitude}</td>
                                    <td id="${loopCounter.count }7">${beanType.longitude}</td>                            
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
