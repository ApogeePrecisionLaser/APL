<%-- 
    Document   : view_attendance
    Created on : 24 Aug, 2021, 1:16:15 PM
    Author     : DELL
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="../layout/header.jsp" %>
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<link href="https://fonts.googleapis.com/css?family=Lato:300,400,700&display=swap" rel="stylesheet">

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

<link rel="stylesheet" href="calendar/css/style.css">

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
        <h1>Calendar</h1>
    </div>
</section>

<section class="ftco-section">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="elegant-calencar d-md-flex">
                    <div class="wrap-header d-flex align-items-center img" style="background-image: url(calendar/images/bg.jpg);">
                        <p id="reset">Today</p>
                        <div id="header" class="p-0">
                            <!-- <div class="pre-button d-flex align-items-center justify-content-center"><i class="fa fa-chevron-left"></i></div> -->
                            <div class="head-info">
                                <div class="head-month"></div>
                                <div class="head-day"></div>
                                <div class="head-occasion"></div>
                            </div>
                            <!-- <div class="next-button d-flex align-items-center justify-content-center"><i class="fa fa-chevron-right"></i></div> -->
                        </div>
                    </div>
                    <div class="calendar-wrap">
                        <div class="w-100 button-wrap">
                            <div class="pre-button d-flex align-items-center justify-content-center"><i class="fa fa-chevron-left"></i></div>
                            <div class="next-button d-flex align-items-center justify-content-center"><i class="fa fa-chevron-right"></i></div>
                        </div>
                        <table id="calendar">
                            <thead>
                                <tr>
                                    <th>Sun</th>
                                    <th>Mon</th>
                                    <th>Tue</th>
                                    <th>Wed</th>
                                    <th>Thu</th>
                                    <th>Fri</th>
                                    <th>Sat</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td style="background:red;color:#fff;"></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td style="background:red;color:#fff;"></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td style="background:red;color:#fff;"></td>
                                </tr>
                                <tr>
                                    <td style="background:red;color:#fff;"></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td style="background:red;color:#fff;"></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td style="background:red;color:#fff;"></td>
                                </tr>
                                <tr>
                                    <td style="background:red;color:#fff;"></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td style="background:red;color:#fff;"></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<%@include file="../layout/footer.jsp" %>
<!--<script src="calendar/js/jquery.min.js"></script>-->
<!--<script src="calendar/js/popper.js"></script>-->
<!--<script src="calendar/js/bootstrap.min.js"></script>-->
<script src="calendar/js/main.js"></script>
