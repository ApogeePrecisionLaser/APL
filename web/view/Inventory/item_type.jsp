<%@taglib prefix="myfn" uri="http://MyCustomTagFunctions" %>
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


        $("#searchItemType").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("searchItemType").value;
                $.ajax({
                    url: "ItemTypeController",
                    dataType: "json",
                    data: {action1: "getItemType", str: random},
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
                $('#searchItemType').val(ui.item.label); // display the selected text
                return false;
            }
        });
    });


    var editable = false;
    function makeEditable(id) {
        document.getElementById("item_type_name").disabled = false;
        document.getElementById("description").disabled = false;
        document.getElementById("save").disabled = false;
        if (id == 'new') {
            editable = "false";
            $("#message").html("");
            document.getElementById("organisation_type_id").value = "";
            document.getElementById("edit").disabled = true;
            document.getElementById("delete").disabled = true;
            document.getElementById("org_type_name").focus();
        }
        if (id == 'edit') {
            editable = "true";
            document.getElementById("delete").disabled = false;
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
            var item_type_name = document.getElementById("item_type_name").value;
            if (myLeftTrim(item_type_name).length == 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Item Type is required...</b></label></div>');
                document.getElementById("item_type_name").focus();
                return false;
            }
            if (result == false)
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

    function fillColumn(id, count) {
        $('#item_type_id').val(id);
        $('#item_type_name').val($("#" + count + '2').html());
        $('#description').val($("#" + count + '3').html());
        $('#edit').attr('disabled', false);
        $('#delete').attr('disabled', false);
    }


</script>


<section>
    <div class="container-fluid page_heading sectionPadding35">
        <h1>Item Type</h1>
    </div>
</section>

<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Search Engine</h5>
        </div>
        <form name="form1" method="POST" action="ItemTypeController" onsubmit="return verifySearch();" >
            <div class="row mt-3">
                <div class="col-md-12">
                    <div class="form-group mb-md-0">
                        <label>Item Type </label>
                        <input class="form-control myInput searchInput1 w-100" type="text" id="searchItemType" name="searchItemType" value="${searchItemType}" size="30" >
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

<c:if test="${isSelectPriv eq 'Y'}">
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
                                    <th>Item Type Name</th>
                                    <th>Description</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="beanType" items="${requestScope['list']}"
                                           varStatus="loopCounter">
                                    <tr
                                        onclick="fillColumn('${beanType.item_type_id}', '${loopCounter.count }');">
                                        <td>${loopCounter.count }</td>
                                        <td id="${loopCounter.count }2">${beanType.item_type}</td>
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
</c:if>

<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Data Entry</h5>
        </div>
        <form name="form2" method="POST" action="ItemTypeController" onsubmit="return verify()">
            <div class="row mt-3">
                <div class="col-md-12">
                    <div class="">
                        <div class="form-group">
                            <label>Item Type Name<span class="text-danger">*</span></label>                            
                            <input class="form-control myInput" type="hidden" id="item_type_id" name="item_type_id" value="" >
                            <input class="form-control myInput" type="text" id="item_type_name" name="item_type_name" value="" size="40" disabled>
                        </div>
                    </div>
                </div>

                <div class="col-md-12">
                    <div class="">
                        <div class="form-group">
                            <label>Item Type Description</label>
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
                <input type="hidden" id="clickedButton" name="clickedButton">
                <div class="col-md-12 text-center">                                           
                    <c:if test="${myfn:isContainPrivileges2(loggedUser,'ItemTypeController','update') eq 'True'}">
                        <input type="button" class="btn normalBtn" name="task" id="edit" value="Edit" onclick="makeEditable(id)" disabled="">
                    </c:if>

                    <c:if test="${myfn:isContainPrivileges2(loggedUser,'ItemTypeController','insert') eq 'True'}">
                        <input type="submit" class="btn normalBtn" name="task" id="save" value="Save" onclick="setStatus(id)" disabled="">
                    </c:if>

                    <c:if test="${myfn:isContainPrivileges2(loggedUser,'ItemTypeController','insert') eq 'True'}">
                        <input type="reset" class="btn normalBtn" name="task" id="new" value="New" onclick="makeEditable(id)">
                    </c:if>

                    <c:if test="${myfn:isContainPrivileges2(loggedUser,'ItemTypeController','delete') eq 'True'}">
                        <input type="submit" class="btn normalBtn" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled="">
                    </c:if>
                </div>
            </div>
        </form>
    </div>
</section>

<%@include file="../layout/footer.jsp" %>

