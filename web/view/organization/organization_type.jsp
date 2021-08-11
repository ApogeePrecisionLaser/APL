<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="../layout/header.jsp" %>


<script>

    $(function () {

        $("#searchOrgType").autocomplete({

            source: function (request, response) {
                var random = document.getElementById("searchOrgType").value;
                $.ajax({
                    url: "OrganisationTypeController",
                    dataType: "json",
                    data: {action1: "getOrganisationType", str: random},
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
                $('#searchOrgType').val(ui.item.label); // display the selected text
                return false;
            }
        });


        $("#hierarchysearch").autocomplete({

            source: function (request, response) {
                var random = document.getElementById("hierarchysearch").value;
                $.ajax({
                    url: "OrganisationTypeController",
                    dataType: "json",
                    data: {action1: "gethierarchysearch", str: random},
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
                $('#hierarchysearch').val(ui.item.label); // display the selected text
                return false;
            }
        });







        $("#searchgeneration").autocomplete({

            source: function (request, response) {
                var random = document.getElementById("searchgeneration").value;
                $.ajax({
                    url: "OrganisationTypeController",
                    dataType: "json",
                    data: {action1: "getgeneration", str: random},
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
                $('#searchgeneration').val(ui.item.label); // display the selected text
                return false;
            }
        });



        $("#p_ot").autocomplete({

            source: function (request, response) {

                var random = document.getElementById("p_ot").value;
                var o_t = document.getElementById("org_type_name").value;
                var generation = document.getElementById("generation").value;
                var edit = editable;

                if (document.getElementById("supern").value == 'N')
                {
                    var superr = "N";
                } else
                {
                    var superr = "Y";

                }
                $.ajax({
                    url: "OrganisationTypeController",
                    dataType: "json",
                    data: {action1: "getParentOrganisationType", str: random, action2: o_t, action3: superr, edit: edit, generation: generation},
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
                $('#p_ot').val(ui.item.label); // display the selected text
                return false;
            }
        });

    });

    var editable = false;
    function makeEditable(id) {
        debugger;
        document.getElementById("org_type_name").disabled = false;
        document.getElementById("description").disabled = false;
        document.getElementById("p_ot").disabled = false;

        document.getElementById("save").disabled = false;
        if (id == 'new') {
            editable = "false";
            //document.getElementById("message").innerHTML = "";      // Remove message
            $("#message").html("");
            document.getElementById("organisation_type_id").value = "";
            document.getElementById("edit").disabled = true;
            document.getElementById("delete").disabled = true;
//            document.getElementById("save_As").disabled = true;
            document.getElementById("supern").disabled = false;
            document.getElementById("supery").disabled = false;
          //  setDefaultColor(document.getElementById("noOfRowsTraversed").value, 3);
            document.getElementById("org_type_name").focus();
        }
        if (id == 'edit') {
            editable = "true";
          //  document.getElementById("save_As").disabled = false;
            document.getElementById("delete").disabled = false;
            document.getElementById("supern").disabled = false;
            document.getElementById("supery").disabled = false;
        }

    }
    function setDefaultColor(noOfRowsTraversed, noOfColumns) {
        for (var i = 0; i < noOfRowsTraversed; i++) {
            for (var j = 1; j <= noOfColumns; j++) {
                document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
            }
        }
    }
    function fillColumns(id) {
        var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
        var noOfColumns = 6;
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

        document.getElementById("organisation_type_id").value = document.getElementById("organisation_type_id" + rowNo).value;
        document.getElementById("org_type_name").value = document.getElementById(t1id + (lowerLimit + 1)).innerHTML;
        document.getElementById("p_ot").value = document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
        if (document.getElementById(t1id + (lowerLimit + 3)).innerHTML == 'N')
        {
            document.getElementById("supern").checked = document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
            document.getElementById("supery").unchecked = document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
        } else
        {
            document.getElementById("supery").checked = document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
            document.getElementById("supern").unchecked = document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
        }
        document.getElementById("description").value = document.getElementById(t1id + (lowerLimit + 4)).innerHTML;
        document.getElementById("generation").value = document.getElementById(t1id + (lowerLimit + 5)).innerHTML;
        for (var i = 0; i < noOfColumns; i++) {
            document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
        }
        document.getElementById("edit").disabled = false;
        if (!document.getElementById("save").disabled)   // if save button is already enabled, then make edit, and delete button enabled too.
        {
            document.getElementById("save_As").disabled = true;
            document.getElementById("delete").disabled = false;
        }
        //  document.getElementById("message").innerHTML = "";      // Remove message
        $("#message").html("");
    }
    function setStatus(id) {
        if (id == 'save') {
            document.getElementById("clickedButton").value = "Save";
        } else if (id == 'save_As') {
            document.getElementById("clickedButton").value = "Save AS New";
        } else
            document.getElementById("clickedButton").value = "Delete";
        ;
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
            var org_type_name = document.getElementById("org_type_name").value;
            if (myLeftTrim(org_type_name).length == 0) {
                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>");
                document.getElementById("org_type_name").focus();
                return false; // code to stop from submitting the form2.
            }
            if (result == false)    // if result has value false do nothing, so result will remain contain value false.
            {

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
    function displayOrgnList(id) {
        var queryString;
        var active = document.getElementById("activee").value;
        var searchgeneration = document.getElementById("searchgeneration").value;
        var searchOrgType = document.getElementById("searchOrgType").value;
        var hierarchysearch = document.getElementById("hierarchysearch").value;
        if (id == 'viewPdf')
        {
            queryString = "requester=PRINT&searchOrgType=" + searchOrgType + "&active=" + active + "&searchgeneration=" + searchgeneration + "&hierarchysearch=" + hierarchysearch;

        } else
        {
            queryString = "requester=PRINTXls&searchOrgType=" + searchOrgType;
        }
        var url = "orgTypeCont.do?" + queryString;
        popupwin = openPopUp(url, "Organisation Type Report", 600, 900);
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
            if (popupwin != null && !popupwin.closed) {
                popupwin.focus();
            }
        }
</script>




<section>
    <div class="container-fluid page_heading sectionPadding35">
        <h1>Organisation Type</h1>
    </div>
</section>



<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Search Engine</h5>
        </div>
        <form name="form1" method="POST" action="OrganisationTypeController" onsubmit="return verifySearch();" >
            <div class="row mt-3">
                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Organization Type</label>
                        <input class="form-control myInput searchInput1 w-100" type="text" id="searchOrgType" name="searchOrgType" value="${searchOrgType}" size="30" >
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Generation</label>
                        <input class="form-control myInput searchInput1 w-100" type="text" id="searchgeneration" name="searchgeneration" value="${searchgeneration}" size="30" >
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Org Type Hierarchy</label>
                        <input class="form-control myInput searchInput1 w-100" type="text" id="hierarchysearch" name="hierarchysearch" value="${hierarchysearch}" size="30" >
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
                <!--                <div class="col-md-3">
                                    <div class="formBtnWrap">
                                        <input type="submit" class="btn formBtn" id="hiera" name="search_org" value="SEARCH RECORDS" onclick="setStatus(id)">
                                    </div>
                                </div>-->
            </div>

            <hr>
            <div class="row">
                <div class="col-md-12 text-center">
                    <input class="btn normalBtn" type="submit" name="task" id="searchIn" value="Search Records">
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
                                <th>Organization Type Name</th>
                                <th>Parent Organization Type Name</th>
                                <th>Is Super Child</th>
                                <th>Organization Description</th>
                                <th>Generation</th>
                            </tr>
                        </thead>
                        <tbody>


                            <c:forEach var="orgType" items="${requestScope['orgTypeList']}"
                                       varStatus="loopCounter">
                                <tr
                                    onclick="fillColumn();">
                                    <td>${loopCounter.count }</td>
                                    <td id="${loopCounter.count }2">${orgType.org_type_name}</td>
                                    <td id="${loopCounter.count }3">${orgType.p_ot}</td>
                                    <td id="${loopCounter.count }4">${orgType.supper}</td>
                                    <td id="${loopCounter.count }5">${orgType.description}</td>  
                                    <td id="${loopCounter.count }6">${orgType.generation}</td>  
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
        <form name="form2" method="POST" action="OrganisationTypeController" onsubmit="return verify()">
            <div class="row mt-3">
                <div class="col-md-4">
                    <div class="">
                        <div class="form-group">
                            <label>Organization Type Name<span class="text-danger">*</span></label>                            
                            <input class="form-control myInput" type="hidden" id="organisation_type_id" name="organisation_type_id" value="" >
                            <input class="form-control myInput" type="text" id="org_type_name" name="org_type_name" value="" size="40" disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="">
                        <div class="form-group">
                            <label>Parent Organization Type Name<span class="text-danger">*</span></label>
                            <input  class="form-control myInput" type="text" id="p_ot" name="p_ot" value="" size="40" disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="">
                        <div class="form-group mb-1">
                            <label class="" for="email">Is super child<span class="text-danger">*</span></label>
                        </div>
                        <div class="form-group form-check mb-0 d-inline mr-2 pl-0">
                            <label class="form-check-label ">
                                <input type="radio" id="supery" name="super"  disabled value="Y"> Yes
                            </label>
                        </div>
                        <div class="form-group form-check d-inline pl-0">
                            <label class="form-check-label">
                                <input type="radio" id="supern" name="super" disabled value="N"> No
                            </label>
                        </div>
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="">
                        <div class="form-group">
                            <!--<label>Generation<span class="text-danger">*</span></label>-->
                            <input  class="form-control myInput" type="hidden" id="generation" name="generation" value="" size="40" disabled>
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

