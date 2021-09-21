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
    setTimeout(function () {
        $('#message').fadeOut('fast');
    }, 10000);

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
        $("#searchDesignationCode").autocomplete({

            source: function (request, response) {
                var random = document.getElementById("searchDesignationCode").value;
                $.ajax({
                    url: "DesignationController",
                    dataType: "json",
                    data: {action1: "getSearchDesignationCode", str: random},
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
                $('#searchDesignationCode').val(ui.item.label); // display the selected text
                return false;
            }
        });


        $("#searchDesignation").autocomplete({

            source: function (request, response) {
                var code = document.getElementById("searchDesignationCode").value;
                var random = document.getElementById("searchDesignation").value;
                $.ajax({
                    url: "DesignationController",
                    dataType: "json",
                    data: {action1: "getDesignationList",
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
        document.getElementById("designation_id").disabled = false;
        document.getElementById("designation").disabled = false;
        document.getElementById("designation_code").disabled = false;
        document.getElementById("description").disabled = false;
        document.getElementById("save").disabled = false;
        if (id == 'new') {
            $("#message").html("");
            document.getElementById("designation_id").value = "";
            document.getElementById("edit").disabled = true;
            document.getElementById("delete").disabled = true;
            document.getElementById("save_As").disabled = true;
            document.getElementById("designation_code").focus();
            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 4);
        }
        if (id == 'edit') {
            document.getElementById("save_As").disabled = false;
            document.getElementById("delete").disabled = false;
            document.getElementById("designation_code").focus();
        }

    }
    function setDefaultColor(noOfRowsTraversed, noOfColumns) {
        for (var i = 0; i < noOfRowsTraversed; i++) {
            for (var j = 1; j <= noOfColumns; j++) {
                document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
            }
        }
    }
    function fillColumns(id)
    {
        var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
        var noOfColumns = 4;
        var columnId = id;
    <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
        columnId = columnId.substring(3, id.length);
    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
        var lowerLimit, higherLimit, rowNo = 0;
        for (var i = 0; i < noOfRowsTraversed; i++) {
            lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
            higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)
            rowNo++;
            if ((columnId >= lowerLimit) && (columnId <= higherLimit))
                break;
        }
        setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
        var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.
        var t2id = "t2c";       // particular column id of table 2 e.g. t2c3.
        alert(document.getElementById("designation_id" + rowNo).value);
        document.getElementById("designation_id").value = document.getElementById("designation_id" + rowNo).value;
        document.getElementById("designation_code").value = document.getElementById(t1id + (lowerLimit + 1)).innerHTML;
        document.getElementById("designation").value = document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
        document.getElementById("description").value = document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
        for (var i = 0; i < noOfColumns; i++) {
            document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
        }
        document.getElementById("edit").disabled = false;
        if (!document.getElementById("save").disabled) {   // if save button is already enabled, then make edit, and delete button enabled too.
            document.getElementById("save_As").disabled = true;
            document.getElementById("delete").disabled = false;
        }
        $("#message").html("");
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
            var designation_code = document.getElementById("designation_code").value;
            if (myLeftTrim(media_type).length == 0) {
                $("#message").html("<td colspan='2' bgcolor='coral'><b>Designation is required...</b></td>");
                document.getElementById("designation").focus();
                return  false; // code to stop from submitting the form2.
            }
            if (myLeftTrim(designation_code).length == 0) {
                $("#message").html("<td colspan='2' bgcolor='coral'><b>Designation is required...</b></td>");
                document.getElementById("designation_code").focus();
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
        var active = document.getElementById("activee").value;
        var action;
        if (id == 'viewPdf')
            action = "task=generateDesignationReport&searchDesignation=" + searchDesignation + "&searchDesignationCode=" + searchDesignationCode + "&active=" + active;
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
        $('#designation_id').val(id);
        $('#designation_code').val($("#" + count + '2').html());
        $('#designation').val($("#" + count + '3').html());
        $('#description').val($("#" + count + '4').html());
        $('#edit').attr('disabled', false);
        $('#delete').attr('disabled', false);
    }
</script>




<section>
    <div class="container-fluid page_heading sectionPadding35">
        <h1>Designation</h1>
    </div>
</section>



<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Search Engine</h5>
        </div>
        <form name="form1" method="POST" action="DesignationController" onsubmit="return verifySearch();" >
            <div class="row mt-3">
                <div class="col-md-6">
                    <div class="form-group mb-md-0">

                        <label>Designation Code</label>
                        <input class="form-control myInput searchInput1 w-100" type="text" id="searchDesignationCode"
                               name="searchDesignationCode" value="${searchDesignationCode}" size="150" >
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-group mb-md-0">
                        <label>Designation</label>
                        <input class="form-control myInput searchInput1 w-100" type="text" id="searchDesignation"
                               name="searchDesignation" value="${searchDesignation}" size="150" >
                    </div>
                </div>
            </div>

            <hr>
            <div class="row">
                <div class="col-md-12 text-center">
                    <input type="submit" class="btn normalBtn" id="searchDesignation" name="search" value="SEARCH RECORDS" onclick="setStatus(id)">
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
                                <th>Designation Code</th>
                                <th>Designation</th>
                                <th>Description</th>
                            </tr>
                        </thead>
                        <tbody>                                
                            <c:forEach var="beanType" items="${requestScope['mediaList']}" varStatus="loopCounter">
                                <tr
                                    onclick="fillColumn('${beanType.designation_id}', '${loopCounter.count }');">
                                    <td>${loopCounter.count }</td>
                                    <td id="${loopCounter.count }2">${beanType.designation_code}</td>
                                    <td id="${loopCounter.count }3">${beanType.designation}</td>
                                    <td id="${loopCounter.count }4">${beanType.description}</td>
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
        <form name="form2" method="POST" action="DesignationController" onsubmit="return verify()">
            <div class="row mt-3">
                <div class="col-md-4">
                    <div class="">
                        <div class="form-group">
                            <label>Designation code<span class="text-danger">*</span></label> 
                            <input type="hidden" id="designation_id" name="designation_id" value="" >
                            <input class="form-control myInput  w-100" type="text" id="designation_code" name="designation_code" value="" size="45" disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="">
                        <div class="form-group">
                            <label>Designation<span class="text-danger">*</span></label>
                            <input class="form-control myInput  w-100" type="text" id="designation" name="designation" value=""  size="45" disabled>
                        </div>
                    </div>
                </div>                
                <div class="col-md-12">
                    <div class="">
                        <div class="form-group">
                            <label>Type Description</label>
                            <textarea class="form-control myTextArea"  id="description" name="description" disabled></textarea>
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
                    <input type="reset" class=" btn normalBtn" name="new" id="new" value="New" onclick="makeEditable(id)" >
                    <input type="submit" class="btn normalBtn" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                </div>
            </div>
        </form>
    </div>
</section>



<%@include file="../layout/footer.jsp" %>

