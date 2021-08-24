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

        $("#searchOrgOffice").autocomplete({

            source: function (request, response) {
                var random = document.getElementById("searchOrgOffice").value;
                $.ajax({
                    url: "OrgOfficeDesignationMapController",
                    dataType: "json",
                    data: {action1: "getOrgOffice", str: random},
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
                $('#searchOrgOffice').val(ui.item.label); // display the selected text
                return false;
            }
        });


        $("#designation").autocomplete({

            source: function (request, response) {
                var code = document.getElementById("org_office").value;
                var random = document.getElementById("designation").value;
                $.ajax({
                    url: "OrgOfficeDesignationMapController",
                    dataType: "json",
                    data: {action1: "getDesignation",
                        action2: code, str: random},
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
                $('#designation').val(ui.item.label); // display the selected text
                return false;
            }
        });


        $("#org_office").autocomplete({

            source: function (request, response) {
                var random = document.getElementById("org_office").value;
                $.ajax({
                    url: "OrgOfficeDesignationMapController",
                    dataType: "json",
                    data: {action1: "getOrgOffice", str: random},
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
                $('#org_office').val(ui.item.label); // display the selected text
                return false;
            }
        });


        $("#searchDesignation").autocomplete({

            source: function (request, response) {
                var code = document.getElementById("searchDesignationCode").value;
                var random = document.getElementById("searchDesignation").value;
                $.ajax({
                    url: "organisationdesignationCont.do",
                    dataType: "json",
                    data: {action1: "searchDesignation",
                        action2: code, str: random},
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
                $('#searchDesignation').val(ui.item.label); // display the selected text
                return false;
            }
        });

    });


    function makeEditable(id) {
        //  document.getElementById("designation_id").disabled = false;
        document.getElementById("designation").disabled = false;
        document.getElementById("org_office").disabled = false;
        document.getElementById("save").disabled = false;
        if (id == 'new') {
            $("#message").html("");
            // document.getElementById("designation_id").value = "";
            document.getElementById("edit").disabled = true;
            document.getElementById("delete").disabled = true;
            // document.getElementById("save_As").disabled = true;
            document.getElementById("description").disabled = false;
            // document.getElementById("super").disabled = false;
            document.getElementById("org_office").focus();
            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 4);
        }
        if (id == 'edit') {
            //  document.getElementById("save_As").disabled = false;
            document.getElementById("delete").disabled = false;
            document.getElementById("org_office").focus();
            document.getElementById("description").disabled = false;
//            document.getElementById("super").disabled = false;
        }
    }


    function setDefaultColor(noOfRowsTraversed, noOfColumns) {
        for (var i = 0; i < noOfRowsTraversed; i++) {
            for (var j = 1; j <= noOfColumns; j++) {
                document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
            }
        }
    }


    function setStatus(id) {
        if (id == 'save') {
            document.getElementById("clickedButton").value = "Save";
        } else if (id == 'save_As') {
            document.getElementById("clickedButton").value = "Save AS New";
        } else
            document.getElementById("clickedButton").value = "Delete";
    }


    function myLeftTrim(str) {
        var beginIndex = 0;
        for (var i = 0; i < str.length; i++) {
            if (str.charAt(i) == ' ')
                beginIndex++;
            else
                break;
        }
        return str.substring(beginIndex, str.length);
    }


    function verify() {
        var result;
        if (document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New') {
            var media_type = document.getElementById("designation").value;
            var org_office = document.getElementById("org_office").value;
            if (myLeftTrim(media_type).length == 0) {
                $("#message").html("<td colspan='2' bgcolor='coral'><b>Designation is required...</b></td>");
                document.getElementById("designation").focus();
                return  false; // code to stop from submitting the form2.
            }
            if (myLeftTrim(org_office).length == 0) {
                $("#message").html("<td colspan='2' bgcolor='coral'><b>Designation is required...</b></td>");
                document.getElementById("org_office").focus();
                return  false; // code to stop from submitting the form2.
            }
            if (result == false)
            {// if result has value false do nothing, so result will remain contain value false.

            } else {
                result = true;

            }
            if (document.getElementById("clickedButton").value == 'Save AS New') {
                result = confirm("Are you sure you want to save it as New record?")
                return result;
            }
        } else
            result = confirm("Are you sure you want to delete this record?")
        return result;
    }


    function searchDesignationList(id)
    {
        var searchDesignation = document.getElementById('searchDesignation').value;
        var searchDesignationCode = document.getElementById('searchDesignationCode').value;
        var action;
        if (id == 'viewPdf')
            action = "task=generateDesignationReport&searchDesignation=" + searchDesignation + "&searchDesignationCode=" + searchDesignationCode;
        else
            action = "task=generateDesignationXlsReport&searchDesignation=" + searchDesignation + "&searchDesignationCode=" + searchDesignationCode;
        var url = "designationCont.do?" + action;
        popup = popupWindow(url, "Designation_View_Report");
    }


    function popupWindow(url, windowName)
    {
        var windowfeatures = "left=50, top=50, width=1000, height=500, resizable=no, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
        return window.open(url, windowName, windowfeatures)
    }


    function fillColumn(id, count) {
        // alert(id);
        $('#org_office_designation_map_id').val(id);
        $('#org_office').val($("#" + count + '2').html());
        $('#designation').val($("#" + count + '3').html());
        $('#description').val($("#" + count + '4').html());

        $('#edit').attr('disabled', false);
        $('#delete').attr('disabled', false);
    }


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


</script>




<section>
    <div class="container-fluid page_heading sectionPadding35">
        <h1>Org. Office Designation Map</h1>
    </div>
</section>



<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Search Engine</h5>
        </div>
        <form name="form1" method="POST" action="OrgOfficeDesignationMapController" onsubmit="return verifySearch();" >
            <div class="row mt-3">
                <div class="col-md-12">
                    <div class="form-group mb-md-0">
                        <label>Org. Office</label>
                        <input class="form-control myInput" type="text" id="searchOrgOffice" name="searchOrgOffice" value="${searchOrgOffice}" size="150" >
                    </div>
                </div>
            </div>

            <hr>
            <div class="row">
                <div class="col-md-12 text-center">
                    <input class="btn normalBtn" type="submit" name="task" id="searchInDesignation" value="SEARCH RECORDS">
                    <!--                <input type="button" class="btn normalBtn" id="viewPdf" name="viewPdf" value="PDF" onclick="displayOrgnList(id)">
                                    <input type="button" class="btn normalBtn" id="viewXls" name="viewXls" value="Excel" onclick="displayOrgnList(id)">-->
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
                                <th>Org. Office</th>
                                <th>Designation</th>
                                <th>Serial No</th>
                            </tr>
                        </thead>
                        <tbody>   
                            <c:forEach var="beanType" items="${requestScope['organisationList']}" varStatus="loopCounter">

                                <tr onclick="fillColumn('${beanType.id}', '${loopCounter.count }');">
                                    <td>${loopCounter.count }</td>
                                    <td id="${loopCounter.count }2">${beanType.organisation}</td>
                                    <td id="${loopCounter.count }3">${beanType.designation}</td>
                                    <td id="${loopCounter.count }4">${beanType.serialnumber}</td>
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
        <form name="form2" method="POST" action="OrgOfficeDesignationMapController" onsubmit="return verify()">
            <div class="row mt-3">
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <input type="hidden" id="org_office_designation_map_id" name="org_office_designation_map_id" value="" >
                            <label>Org. Office<span class="text-danger">*</span></label>                            
                            <input class="form-control myInput" type="text" id="org_office" name="org_office" value="" size="45" disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Designation<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="designation" name="designation" value=""  size="45" disabled>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row mt-3">
                <div class="col-md-12">
                    <div class="">
                        <div class="form-group">
                            <label>Description<span class="text-danger">*</span></label>
                            <textarea class="form-control myTextArea"  id="description" name="description" name="description" disabled></textarea>
                        </div>
                    </div>
                </div>
            </div>
            <!--                <div class="col-md-3">
                                <div class="">
                                    <div class="form-group">
                                        <label>Super<span class="text-danger">*</span></label>
                                        <input class="form-control myInput" type="text" id="super" name="super" value="" size="45" disabled>
                                    </div>
                                </div>
                            </div>-->


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
                    <input type="reset" class=" btn normalBtn" name="new" id="new" value="New" onclick="makeEditable(id)" >
                    <input type="submit" class="btn normalBtn" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                </div>
            </div>
        </form>
    </div>
</section>



<%@include file="../layout/footer.jsp" %>

