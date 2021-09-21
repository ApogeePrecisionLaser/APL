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
        setTimeout(function () {
            $('#message').fadeOut('fast');
        }, 10000);


        $("#searchOrgOfficeType").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("searchOrgOfficeType").value;
                $.ajax({
                    url: "OrgOfficeTypeController",
                    dataType: "json",
                    data: {action1: "getOrganisationOfficeType", str: random},
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
                $('#searchOrgOfficeType').val(ui.item.label); // display the selected text
                return false;
            }
        });
    });

    function makeEditable(id) {
        document.getElementById("office_type").disabled = false;
        document.getElementById("description").disabled = false;
        if (id == 'new') {
            $("#message").html("");
            document.getElementById("office_type_id").value = "";
            document.getElementById("save").disabled = false;
            document.getElementById("edit").disabled = true;
            document.getElementById("delete").disabled = true;
            document.getElementById("save_As").disabled = true;
            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 4);
            document.getElementById("office_code").focus();
        }
        if (id == 'edit') {
            document.getElementById("save").disabled = false;
            document.getElementById("delete").disabled = false;
        }
        document.getElementById("save").disabled = false;
    }

    function setStatus(id) {
        if (id == 'save') {
            document.getElementById("clickedButton").value = "Save";
        } else if (id == 'save_As') {
            document.getElementById("clickedButton").value = "Save AS New";
        } else {
            document.getElementById("clickedButton").value = "Delete";
            ;
        }
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
            var office_type = document.getElementById("office_type").value;

            if (myLeftTrim(office_type).length == 0) {
                //  document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Office Type is required...</b></td>";
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Org Office Type is required...</b></label></div>');
                document.getElementById("office_type").focus();
                return false; // code to stop from submitting the form2.
            }
            if (result == false) {
                // if result has value false do nothing, so result will remain contain value false.
            } else {
                result = true;
            }

            if (document.getElementById("clickedButton").value == 'Save AS New') {
                result = confirm("Are you sure you want to save it as New record?")
                return result;
            }
        } else {
            result = confirm("Are you sure you want to delete this record?");
        }
        return result;
    }
    function orgOfficeReport(id)
    {
        var searchOfficeType = document.getElementById('searchOrgOfficeType').value;
        var active = document.getElementById("activee").value;
        var action;
        if (id == 'viewPdf')
        {
            action = "task=generateOrgOfficeReport&searchOrgOfficeType=" + searchOfficeType + "&active=" + active;

        } else
        {
            action = "task=generateOrgOfficeXlsReport&searchOrgOfficeType=" + searchOfficeType + "&active=" + active;
        }
        var url = "orgOfficeTypeCont.do?" + action;
        popup = popupWindow(url, "Org_Office_View_Report");
    }
    function popupWindow(url, windowName)
    {
        var windowfeatures = "left=50, top=50, width=1000, height=500, resizable=no, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
        return window.open(url, windowName, windowfeatures)
    }


    function  myrequest()
    {
        var active = document.getElementById("active").value;
        var inactive = document.getElementById("inactive").value;
        var all = document.getElementById("all").value;
    }

    function fillColumn(id, count) {
        $('#office_type_id').val(id);
        $('#office_type').val($("#" + count + '2').html());
        $('#description').val($("#" + count + '3').html());
        $('#edit').attr('disabled', false);
        $('#delete').attr('disabled', false);
    }

</script>




<section>
    <div class="container-fluid page_heading sectionPadding35">
        <h1>Org Office Type</h1>
    </div>
</section>



<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Search Engine</h5>
        </div>
        <form name="form1" method="POST" action="OrgOfficeTypeController" onsubmit="return verifySearch();" >
            <div class="row mt-3">
                <div class="col-md-12">
                    <div class="form-group mb-md-0">
                        <label>Office Type</label>
                        <input class="form-control myInput searchInput1 w-100" type="text" id="searchOrgOfficeType" name="searchOrgOfficeType" 
                               value="${searchOrgOfficeType}" size="30" >
                    </div>
                </div>
            </div>

            <hr>
            <div class="row">
                <div class="col-md-12 text-center">
                    <input class="btn normalBtn" type="submit" name="task" id="searchIn" value="Search Records">
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
                                <th>Office Type</th>
                                <th>Description</th>
                            </tr>
                        </thead>
                        <tbody>       
                            <c:forEach var="beanType" items="${requestScope['orgOfficeTypeList']}"
                                       varStatus="loopCounter">
                                <tr
                                    onclick="fillColumn('${beanType.office_type_id}', '${loopCounter.count }');">
                                    <td>${loopCounter.count }</td>
                                    <td id="${loopCounter.count }2">${beanType.office_type}</td>
                                    <td id="${loopCounter.count }3">${beanType.description}</td>                                               
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
        <form name="form2" method="POST" action="OrgOfficeTypeController" onsubmit="return verify()">
            <div class="row mt-3">
                <div class="col-md-12">
                    <div class="">
                        <div class="form-group">
                            <label>Office Type<span class="text-danger">*</span></label>                            
                            <input type="hidden" id="office_type_id" name="office_type_id" value="" >
                            <input class="form-control myInput" type="text" id="office_type" name="office_type" size="30" value="" disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="">
                        <div class="form-group">
                            <label>Description<span class="text-danger"></span></label>
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

