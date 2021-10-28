<%@taglib prefix="myfn" uri="http://MyCustomTagFunctions" %>
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
        setTimeout(function () {
            $('#message').fadeOut('fast');
        }, 10000);
        $("#item_name").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("item_name").value;
                $.ajax({
                    url: "ItemAuthorizationController",
                    dataType: "json",
                    data: {action1: "getItemName", str: random},
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
                $('#item_name').val(ui.item.label);
                return false;
            }
        });

        $("#designation").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("designation").value;
                $.ajax({
                    url: "ItemAuthorizationController",
                    dataType: "json",
                    data: {action1: "getDesignation", str: random},
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
                $('#designation').val(ui.item.label);
                return false;
            }
        });


        $("#search_item_name").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("search_item_name").value;
                $.ajax({
                    url: "ItemAuthorizationController",
                    dataType: "json",
                    data: {action1: "getItemName", str: random},
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
                $('#search_item_name').val(ui.item.label);
                return false;
            }
        });

        $("#search_designation").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("search_designation").value;
                $.ajax({
                    url: "ItemAuthorizationController",
                    dataType: "json",
                    data: {action1: "getDesignation", str: random},
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
                $('#search_designation').val(ui.item.label);
                return false;
            }
        });
    });



    function makeEditable(id) {
        document.getElementById("item_name").disabled = false;
        document.getElementById("designation").disabled = false;
        document.getElementById("quantity").disabled = false;
        document.getElementById("description").disabled = false;
        document.getElementById("monthly_limit").disabled = false;

        document.getElementById("save").disabled = false;
        if (id === 'new') {
            $("#message").html("");
            document.getElementById("item_authorization_id").value = "";
            document.getElementById("item_name").focus();
        }
        if (id == 'edit') {
            document.getElementById("save_As").disabled = true;
            document.getElementById("delete").disabled = false;
        }
    }


    function setStatus(id) {
        if (id === 'save') {
            document.getElementById("clickedButton").value = "Save";
        } else {
            document.getElementById("clickedButton").value = "Delete";
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
            var item_name = document.getElementById("item_name").value;
            var designation = document.getElementById("designation").value;
            var quantity = document.getElementById("quantity").value;
            var monthly_limit = document.getElementById("monthly_limit").value;
            if (myLeftTrim(item_name).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Item Name is required...</b></label></div>');
                document.getElementById("item_name").focus();
                return false;
            }
            if (myLeftTrim(designation).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Designation is required...</b></label></div>');
                document.getElementById("designation").focus();
                return false;
            }
            if (myLeftTrim(quantity).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Quantity is required...</b></label></div>');
                document.getElementById("quantity").focus();
                return false;
            }
            if (myLeftTrim(monthly_limit).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Monthly limit is required...</b></label></div>');
                document.getElementById("monthly_limit").focus();
                return false;
            }

            if (result === false) {
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

    function openPopUp(window_name, popup_height, popup_width, id) {
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

    function fillColumn(id, count) {
        $('#item_authorization_id').val(id);
        $('#item_name').val($("#" + count + '2').html());
        $('#designation').val($("#" + count + '3').html());
        $('#quantity').val($("#" + count + '4').html());
        $('#monthly_limit').val($("#" + count + '5').html());
        $('#description').val($("#" + count + '6').html());
        document.getElementById("edit").disabled = false;
        document.getElementById("delete").disabled = false;
    }


</script>


<section>
    <div class="container-fluid page_heading sectionPadding35">
        <h1>Item Authorization</h1>
    </div>
</section>


<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Search Engine</h5>
        </div>
        <form name="form1" method="POST" action="ItemAuthorizationController" onsubmit="return verifySearch();" >
            <div class="row mt-3">
                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Item Name</label>
                        <input type="text" Placeholder="Item Name" name="search_item_name" id="search_item_name" value="${search_item_name}" class="form-control myInput searchInput1 w-100">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Designation</label>
                        <input type="text" Placeholder="Designation" name="search_designation" id="search_designation" value="${search_designation}" class="form-control myInput searchInput1 w-100">
                    </div>
                </div>        
            </div>
            <hr>
            <div class="row">
                <div class="col-md-12 text-center">
                    <input type="submit" class="btn formBtn" id="hiera" name="search_item" value="SEARCH RECORDS" onclick="setStatus(id)">
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
                                    <th>Item Name</th>
                                    <th>Designation</th>
                                    <th>Quantity</th>
                                    <th>Monthly Limit</th>
                                    <th>Description</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="beanType" items="${requestScope['list']}"
                                           varStatus="loopCounter">
                                    <tr
                                        onclick="fillColumn('${beanType.item_authorization_id}', '${loopCounter.count }');">
                                        <td>${loopCounter.count }</td>               
                                        <td id="${loopCounter.count }2">${beanType.item_name}</td>   
                                        <td id="${loopCounter.count }3">${beanType.designation}</td>
                                        <td id="${loopCounter.count }4">${beanType.quantity}</td> 
                                        <td id="${loopCounter.count }5">${beanType.monthly_limit}</td> 
                                        <td id="${loopCounter.count }6">${beanType.description}</td>  

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
        <form name="form2" method="POST" action="ItemAuthorizationController" onsubmit="return verify()" >
            <div class="row mt-3">
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Item Name<span class="text-danger">*</span></label>
                            <input type="hidden" name="item_authorization_id" id="item_authorization_id" value="">

                            <input class="form-control myInput" type="text" id="item_name" name="item_name" value="" disabled >
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Designation<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="designation" name="designation" value="" disabled >
                        </div>
                    </div>
                </div>



                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Quantity<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="quantity" name="quantity" value="" disabled>
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Monthly Limit<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="monthly_limit" name="monthly_limit" value="" disabled>
                        </div>
                    </div>
                </div>

                <div class="col-md-12">
                    <div class="">
                        <div class="form-group">
                            <label>Description</label>
                            <!--<input class="form-control" type="text" id="description" name="description" size="60" value="" disabled >-->
                            <textarea class="form-control myTextArea" id="description" name="description" name="description" disabled></textarea>
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
                    <c:if test="${myfn:isContainPrivileges2(loggedUser,'ItemAuthorizationController','update') eq 'True'}">
                    <input type="button" class="btn normalBtn" name="task" id="edit" value="Edit" onclick="makeEditable(id)" disabled="">
                    </c:if>
                    
                    <c:if test="${myfn:isContainPrivileges2(loggedUser,'ItemAuthorizationController','insert') eq 'True'}">
                    <input type="submit" class="btn normalBtn" name="task" id="save" value="Save" onclick="setStatus(id)" disabled="">
                    </c:if>
                    
                    <c:if test="${myfn:isContainPrivileges2(loggedUser,'ItemAuthorizationController','insert') eq 'True'}">
                    <input type="reset" class="btn normalBtn" name="task" id="new" value="New" onclick="makeEditable(id)">
                    </c:if>
                    
                    <c:if test="${myfn:isContainPrivileges2(loggedUser,'ItemAuthorizationController','delete') eq 'True'}">
                    <input type="submit" class="btn normalBtn" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled="">
                    </c:if>
                </div>
            </div>
        </form>
    </div>
</section>

<%@include file="../layout/footer.jsp" %>

