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
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>

<script type="text/javascript" language="javascript">
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

        setTimeout(function () {
            $('#message').fadeOut('fast');
        }, 10000);



        $("#tehsil").autocomplete({

            source: function (request, response) {

                $.ajax({
                    url: "cityTypeCont",
                    dataType: "json",
                    data: {action1: "getTehsil"},
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
                $('#tehsil').val(ui.item.label); // display the selected text
                return false;
            }
        });


        $("#searchCity").autocomplete({

            source: function (request, response) {
                var random = document.getElementById("searchCity").value;
                $.ajax({
                    url: "CityController",
                    dataType: "json",
                    data: {action1: "getCity", str: random},
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
                $('#searchCity').val(ui.item.label); // display the selected text
                return false;
            }
        });

    });



    function makeEditable(id)
    {
        //debugger;

        if (id == "new")
        {
            document.getElementById('edit').disabled = true;
            document.getElementById('delete').disabled = true;
            //document.getElementById('saveAsNew').disabled = true;
            document.getElementById('cityName').disabled = false;
            document.getElementById('cityDescription').disabled = false;
            document.getElementById('pin_code').disabled = false;
            document.getElementById('std_code').disabled = false;
            document.getElementById('tehsil').disabled = false;
            document.getElementById('save').disabled = false;

            document.getElementById('message').innerHTML = "";

        }
        if (id == "edit")
        {
            document.getElementById('pin_code').disabled = false;
            document.getElementById('std_code').disabled = false;
            document.getElementById('cityName').disabled = false;
            document.getElementById('tehsil').disabled = false;
            document.getElementById('cityDescription').disabled = false;
            document.getElementById('save').disabled = false;
            document.getElementById('saveAsNew').disabled = false;
            document.getElementById('delete').disabled = false;
        }

    }


    function popupWindow(url, windowName)
    {
        var windowfeatures = "left=50, top=50, width=1000, height=500, resizable=no, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
        return window.open(url, windowName, windowfeatures)
    }

    function fillColumn(id, count) {
        $('#city_id').val(id);
        $('#cityName').val($("#" + count + '2').html());
        $('#pin_code').val($("#" + count + '3').html());
        $('#std_code').val($("#" + count + '4').html());
        $('#cityDescription').val($("#" + count + '5').html());
        $('#tehsil').val($("#" + count + '6').html());
        document.getElementById("edit").disabled = false;
        document.getElementById("delete").disabled = false;
    }

</script>




<section>
    <div class="container-fluid page_heading sectionPadding35">
        <h1>City Name</h1>
    </div>
</section>



<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Search Engine</h5>
        </div>
        <form name="form1" method="POST" action="CityController" onsubmit="return verifySearch();" >
            <div class="row mt-3">

                <div class="col-md-12">
                    <div class="form-group mb-md-0">
                        <label>City Name</label>
                        <input type="text"  id="searchCity" name="searchCity" value="" Placeholder="City Name" class="form-control myInput searchInput1 w-100" >
                    </div>
                </div>

            </div>

            <hr>
            <div class="row">
                <div class="col-md-12 text-center">
                    <input type="submit" class="btn formBtn" id="hiera" name="search_org" value="SEARCH RECORDS" onclick="setStatus(id)">
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
                                <th>City Name</th>
                                <th>Pin Code</th>
                                <th>Std Code</th>
                                <th>City Description</th>
                                <th>Tehsil Name</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="beanType" items="${requestScope['cityList']}"
                                       varStatus="loopCounter">
                                <tr
                                    onclick="fillColumn('${beanType.cityId}', '${loopCounter.count }');">
                                    <td>${loopCounter.count }</td>
                                    <td id="${loopCounter.count }2">${beanType.cityName}</td>
                                    <td id="${loopCounter.count }3">${beanType.pin_code}</td>
                                    <td id="${loopCounter.count }4">${beanType.std_code}</td>
                                    <td id="${loopCounter.count }5">${beanType.cityDescription}</td>                                                
                                    <td id="${loopCounter.count }5">${beanType.tehsilName}</td>                                                
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
        <form name="form2" method="POST" action="CityController" onsubmit="return verify()">
            <div class="row mt-3">
                <div class="col-md-4">
                    <div class="">
                        <div class="form-group">
                            <label>City Name<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="hidden" id="city_id" name="city_id" value="" >
                            <input class="form-control myInput" type="text" id="cityName" name="cityName" value="" disabled >
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="">
                        <div class="form-group">
                            <label>Pin Code<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="pin_code" name="pin_code" size="60" value="" disabled >
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="">
                        <div class="form-group">
                            <label>STD Code<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="std_code" name="std_code" value="" disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="">
                        <div class="form-group">
                            <label>Tehsil Name<span class="text-danger"></span></label>
                            <input class="form-control myInput" type="text" id="tehsil" name="tehsil" value="" disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="">
                        <div class="form-group">
                            <label>Description</label>
                            <!--<input class="form-control" type="text" id="description" name="description" size="60" value="" disabled >-->
                            <textarea class="form-control myTextArea"  id="cityDescription" name="cityDescription" disabled></textarea>
                        </div>
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
                <div class="col-md-12 text-center">                       
                    <input type="button" class="btn normalBtn" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled>
                    <input type="submit" class="btn normalBtn" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>
                    <input type="reset" class="btn normalBtn" name="new" id="new" value="New" onclick="makeEditable(id)" >
                    <input type="submit" class="btn normalBtn" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                </div>
            </div>
        </form>
    </div>
</section>



<%@include file="../layout/footer.jsp" %>

