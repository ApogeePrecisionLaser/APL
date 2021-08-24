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

        setTimeout(function () {
            $('#message').fadeOut('fast');
        }, 10000);
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


    $(function () {

        $("#organisation_sub_type_name").autocomplete({

            source: function (request, response) {
                var org_type = document.getElementById("organisation_type").value;
                var random = document.getElementById("organisation_sub_type_name").value;
                $.ajax({
                    url: "OrganizationNameController",
                    dataType: "json",
                    data: {action1: "getOrganisationSubTypeName",
                        action2: org_type,
                        str: random},
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
                $('#organisation_sub_type_name').val(ui.item.label); // display the selected text
                return false;
            }


        });


        $("#org_name").autocomplete({

            source: function (request, response) {

                var random = document.getElementById("org_name").value;

                $.ajax({
                    url: "OrganizationNameController",
                    dataType: "json",
                    data: {action1: "searchOrganisationName", str: random},
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
                $('#org_name').val(ui.item.label); // display the selected text
                return false;
            }


        });



    });
//       $(function () {  
//                 
//              $("#organisation_name").autocomplete({
//                    
//            source: function (request, response) {
//                debugger;
//                $.ajax({
//                    url: "orgNameCont.do",
//                    dataType: "json",
//                    data: {action1: "getOrganisationName"},
//                    success: function (data) {
//
//                        console.log(data);
//                        response(data.list);
//                    }, error: function (error) {
//                        console.log(error.responseText);
//                        response(error.responseText);
//                    }
//                });
//            },
//            select: function (events, ui) {
//                console.log(ui);
//                $('#organisation_name').val(ui.item.label); // display the selected text
//                return false;
//            }
//        });

//        $('#btnDialog').click(function ()
//{
//    $(this).speedoPopup(
//    {
//        width:550,
//        height:265,
//        useFrame: TRUE,
//        href: '#divContentToPopup'
//    });
//});





    function makeEditable(id) {
        debugger;
        document.getElementById("organisation_name").disabled = false;
        document.getElementById("organisation_type").disabled = false;
        document.getElementById("code").disabled = false;
        document.getElementById("description").disabled = false;
        document.getElementById("save").disabled = false;
        if (id === 'new') {
            // document.getElementById("message").innerHTML = "";      // Remove message
            $("#message").html("");
            document.getElementById("organisation_id").value = "";
            //document.getElementById("organisation_sub_type_id").value = "";
            //document.getElementById("organisation_type_id").value = "";
            document.getElementById("edit").disabled = true;
            document.getElementById("delete").disabled = true;
            document.getElementById("save_As").disabled = true;
            document.getElementById("save").disabled = false;

            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 4);
            document.getElementById("organisation_name").focus();
            document.getElementById("organisation_type").focus();
            document.getElementById("description").focus();
            document.getElementById("code").focus();
        }
        if (id == 'edit') {
            document.getElementById("save_As").disabled = true;
            document.getElementById("delete").disabled = false;
        }
        // document.getElementById("save").disabled = false;
    }
    function setDefaultColor(noOfRowsTraversed, noOfColumns) {
        for (var i = 0; i < noOfRowsTraversed; i++) {
            for (var j = 1; j <= noOfColumns; j++) {
                document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
            }
        }
    }


    function setStatus(id) {
        if (id === 'save') {
            document.getElementById("clickedButton").value = "Save";
        } else if (id === 'save_As') {
            document.getElementById("clickedButton").value = "Save AS New";
        } else if (id === 'search_org') {
            var org_name = document.getElementById("org_name").value;
            document.getElementById("org_name1").value = org_name;
            document.getElementById("org_name2").value = org_name;
            document.getElementById("clickedButton").value = "SEARCH";
        } else if (id === 'clear_org') {
            document.getElementById("clickedButton").value = " ";
            $("#org_msg").html("");
            document.getElementById("org_name").value = " ";
            document.getElementById("org_name1").value = " ";
            document.getElementById("org_name2").value = " ";
        } else {
            document.getElementById("clickedButton").value = "Delete";
            ;
        }
    }
    function myLeftTrim(str) {
        var beginIndex = 0;
        for (var i = 0; i < str.length; i++) {
            if (str.charAt(i) === ' ')
                beginIndex++;
            else
                break;
        }
        return str.substring(beginIndex, str.length);
    }
    function verify() {
        var result;
        if (document.getElementById("clickedButton").value === 'Save' || document.getElementById("clickedButton").value === 'Save AS New') {
            var organisation_name = document.getElementById("organisation_name").value;
            if (myLeftTrim(organisation_name).length === 0) {
                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Name is required...</b></td>";
                $("#message").html("<td colspan='2' bgcolor='coral'><b>Organisation Name is required...</b></td>");
                document.getElementById("organisation_name").focus();
                return false; // code to stop from submitting the form2.
            }
            if (result === false) {
                // if result has value false do nothing, so result will remain contain value false.
            } else {
                result = true;
            }
            if (document.getElementById("clickedButton").value === 'Save AS New') {
                result = confirm("Are you sure you want to save it as New record?")
                return result;
            }
        } else {
            result = confirm("Are you sure you want to delete this record?");
        }
        return result;
    }
    function verifySearch() {
        var result;
        if (document.getElementById("clickedButton").value === 'SEARCH') {
            var org_name = document.getElementById("org_name").value;
            if (myLeftTrim(org_name).length === 0) {
                document.getElementById("org_msg").innerHTML = "<b>Organization Name is required...</b>";
                document.getElementById("org_name").focus();
                return false; // code to stop from submitting the form2.
            }
        }
    }

    function displayOrgnList(id) {
        var queryString;
        var active = document.getElementById("activee").value;
        var org_name = document.getElementById("org_name").value;
        if (id === 'viewPdf')
            queryString = "requester=PRINT" + "&active=" + active + "&org_name=" + org_name;
        else
            queryString = "requester=PRINTXls";
        var url = "orgNameCont.do?" + queryString;
        popupwin = openPopUp(url, "Organisation", 600, 900);
    }

    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
        return window.open(url, window_name, window_features);
    }
    if (!document.all) {
        document.captureEvents(Event.CLICK);
    }
    document.onclick = function () {
        if (popupwin !== null && !popupwin.closed) {
            popupwin.focus();
        }
    }


    function fillColumn(organisation_id, count) {
        $('#organisation_type').val($("#" + count + '6').val());
        $('#organisation_id').val($("#" + count + '2').html());
        $('#organisation_name').val($("#" + count + '3').html());
        $('#code').val($("#" + count + '5').html());
        $('#description').val($("#" + count + '4').html());
        document.getElementById("edit").disabled = false;
        document.getElementById("delete").disabled = false;

    }


</script>




<section>
    <div class="container-fluid page_heading sectionPadding35">
        <h1>Organization Name</h1>
    </div>
</section>



<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Search Engine</h5>
        </div>
        <form name="form1" method="POST" action="OrganizationNameController" onsubmit="return verifySearch();" >
            <div class="row mt-3">
                <!--                <div class="col-md-3">
                                    <div class="form-group mb-md-0">
                                        <label>Organization Type</label>
                                        <input type="text" Placeholder="Organization Type" class="form-control myInput searchInput1 w-100">
                                    </div>
                                </div>-->
                <div class="col-md-12">
                    <div class="form-group mb-md-0">
                        <label>Organization Name</label>
                        <input type="text"  id="org_name" name="org_name" value="${searchOrganisation_name}" Placeholder="Organization Name" class="form-control myInput searchInput1 w-100" >
                    </div>
                </div>
                <!--                <div class="col-md-3">
                                    <div class="form-group mb-md-0">
                                        <label>Status</label>
                                        <select class="ui dropdown form-control selectInput mySelect" name="task" id="hiera" >
                                            <option>${ac}(CURRENTLY)</option>
                                            <option value="ACTIVE RECORDS">ACTIVE RECORDS</option>
                                            <option value="INACTIVE RECORDS">INACTIVE RECORDS</option>
                                            <option value="ALL RECORDS">ALL RECORDS</option>
                                        </select>
                                    </div>
                                </div>-->

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
                <div class="table-responsive">
                    <table class="table table-striped table-bordered" id="mytable" style="width:100%">
                        <thead>
                            <tr>
                                <th>S.No.</th>
                                <th>Organization Id</th>
                                <th>Organization Name</th>                                    
                                <th>Description</th>
                                <th>Organization Code</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="beanType" items="${requestScope['orgNameList']}"
                                       varStatus="loopCounter">
                                <tr
                                    onclick="fillColumn('${beanType.organisation_id}', '${loopCounter.count }');">
                                    <td>${loopCounter.count }</td>
                                    <td id="${loopCounter.count }2">${beanType.organisation_id}</td>
                                    <td id="${loopCounter.count }3">${beanType.organisation_name}</td>
                                    <td id="${loopCounter.count }4">${beanType.description}</td>
                                    <td id="${loopCounter.count }5">${beanType.organisation_code}</td>
                            <input id="${loopCounter.count }6" type="hidden" value="${beanType.organisation_type}"/>                                              
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
        <form name="form2" method="POST" action="OrganizationNameController" onsubmit="return verify()">
            <div class="row mt-3">
                <div class="col-md-4">
                    <div class="">
                        <div class="form-group">
                            <label>Organization Type<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="hidden" id="organisation_id" name="organisation_id" value="" >
                            <input class="form-control myInput" type="text" id="organisation_type" name="organisation_type" value="" disabled >
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="">
                        <div class="form-group">
                            <label>Organization Name<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="organisation_name" name="organisation_name" size="60" value="" disabled >
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="">
                        <div class="form-group">
                            <label>Organization Code<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="code" name="code" value="" disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="">
                        <div class="form-group">
                            <label>Description</label>
                            <!--<input class="form-control" type="text" id="description" name="description" size="60" value="" disabled >-->
                            <textarea class="form-control myTextArea"  id="description" name="description" name="description" disabled></textarea>
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
                    <input type="button" class="btn normalBtn" name="task" id="edit" value="Edit" disabled="" onclick="makeEditable(id)" >
                    <input type="submit" class="btn normalBtn" name="task" id="save" value="Save" disabled=""  onclick="setStatus(id)">
                    <input type="reset" class="btn normalBtn" name="task" id="new" value="New"   onclick="makeEditable(id)" >
                    <input type="submit" class="btn normalBtn" name="task" id="delete" value="Delete" disabled=""  onclick="setStatus(id)" >
                </div>
            </div>
        </form>
    </div>
</section>



<%@include file="../layout/footer.jsp" %>

