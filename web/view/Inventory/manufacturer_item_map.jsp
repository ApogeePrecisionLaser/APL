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
        $("#searchManufacturer").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("searchManufacturer").value;
                $.ajax({
                    url: "ManufacturerItemMapController",
                    dataType: "json",
                    data: {action1: "getManufacturer", str: random},
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
                $('#searchManufacturer').val(ui.item.label);
                return false;
            }
        });
        $("#searchItem").autocomplete({
            source: function (request, response) {
                var code = document.getElementById("searchManufacturer").value;
                var random = document.getElementById("searchItem").value;
                $.ajax({
                    url: "ManufacturerItemMapController",
                    dataType: "json",
                    data: {action1: "getItem",
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
                $('#searchItem').val(ui.item.label);
                return false;
            }
        });
//        $("#searchModel").autocomplete({
//            source: function (request, response) {
//                var code = document.getElementById("searchManufacturer").value;
//                var searchItem = document.getElementById("searchItem").value;
//                var random = document.getElementById("searchModel").value;
//                $.ajax({
//                    url: "ManufacturerItemModelMapController",
//                    dataType: "json",
//                    data: {action1: "getModel", str: random, str2: code, str3: searchItem},
//                    success: function (data) {
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
//                $('#searchModel').val(ui.item.label);
//                return false;
//            }
//        });
        $("#manufacturer_name").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("manufacturer_name").value;
                $.ajax({
                    url: "ManufacturerItemMapController",
                    dataType: "json",
                    data: {action1: "getManufacturer", str: random},
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
                $('#manufacturer_name').val(ui.item.label);
            }
        });
        $("#item_name").autocomplete({
            source: function (request, response) {
                var code = document.getElementById("manufacturer_name").value;
                var random = document.getElementById("item_name").value;
                $.ajax({
                    url: "ManufacturerItemMapController",
                    dataType: "json",
                    data: {action1: "getItem",
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
                $('#item_name').val(ui.item.label);
                return false;
            }
        });
//        $("#model_name").autocomplete({
//            source: function (request, response) {
//                var code = document.getElementById("manufacturer_name").value;
//                var searchItem = document.getElementById("item_name").value;
//                var random = document.getElementById("model_name").value;
//                $.ajax({
//                    url: "ManufacturerItemModelMapController",
//                    dataType: "json",
//                    data: {action1: "getModel", str: random, str2: code, str3: searchItem},
//                    success: function (data) {
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
//                $('#model_name').val(ui.item.label);
//                return false;
//            }
//        });
    });
    function makeEditable(id) {
        document.getElementById("manufacturer_name").disabled = false;
        document.getElementById("item_name").disabled = false;
        // document.getElementById("model_name").disabled = false;
        document.getElementById("save").disabled = false;
        if (id == 'new') {
            $("#message").html("");
            document.getElementById("edit").disabled = true;
            document.getElementById("delete").disabled = true;
            document.getElementById("description").disabled = false;
            document.getElementById("manufacturer_name").focus();
        }
        if (id == 'edit') {
            document.getElementById("delete").disabled = false;
            document.getElementById("manufacturer_name").focus();
            document.getElementById("description").disabled = false;
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
            var manufacturer_name = document.getElementById("manufacturer_name").value;
            var item_name = document.getElementById("item_name").value;
            // var model_name = document.getElementById("model_name").value;
            if (myLeftTrim(manufacturer_name).length == 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Manufacturer Name is required...</b></label></div>');
                document.getElementById("manufacturer_name").focus();
                return  false;
            }
//            if (myLeftTrim(model_name).length == 0) {
//                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Model Name is required...</b></label></div>');
//                document.getElementById("model_name").focus();
//                return  false;
//            }
            if (myLeftTrim(item_name).length == 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Item Name is required...</b></label></div>');
                document.getElementById("item_name").focus();
                return  false;
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

    function popupWindow(url, windowName)
    {
        var windowfeatures = "left=50, top=50, width=1000, height=500, resizable=no, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
        return window.open(url, windowName, windowfeatures)
    }

    function fillColumn(id, count) {
        $('#manufacturer_item_map_id').val(id);
        $('#manufacturer_name').val($("#" + count + '2').html());
        $('#item_name').val($("#" + count + '3').html());
        // $('#model_name').val($("#" + count + '4').html());
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
    });</script>


<section>
    <div class="container-fluid page_heading sectionPadding35">
        <h1>Manufacturer Item Model Map</h1>
    </div>
</section>

<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Search Engine</h5>
        </div>
        <form name="form1" method="POST" action="ManufacturerItemMapController" onsubmit="return verifySearch();" >
            <div class="row mt-3">
                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Manufacturer</label>
                        <input class="form-control myInput" type="text" id="searchManufacturer" name="searchManufacturer" value="${searchManufacturer}" size="150" >
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Item</label>
                        <input class="form-control myInput" type="text" id="searchItem" name="searchItem" value="${searchItem}" size="150" >
                    </div>
                </div>
                <!--                <div class="col-md-4">
                                    <div class="form-group mb-md-0">
                                        <label>Model</label>
                                        <input class="form-control myInput" type="text" id="searchModel" name="searchModel" value="${searchModel}" size="150" >
                                    </div>
                                </div>-->
            </div>

            <hr>
            <div class="row">
                <div class="col-md-12 text-center">
                    <input class="btn normalBtn" type="submit" name="task" id="searchInMapping" value="SEARCH RECORDS">

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
                                <th>Manufacturer</th>
                                <th>Item</th>
                                <!--<th>Model</th>-->
                                <th>Description</th>
                            </tr>
                        </thead>
                        <tbody>   
                            <c:forEach var="beanType" items="${requestScope['list']}"
                                       varStatus="loopCounter">
                                <tr
                                    onclick="fillColumn('${beanType.manufacturer_item_map_id}', '${loopCounter.count }');">
                                    <td>${loopCounter.count }</td>
                                    <td id="${loopCounter.count }2">${beanType.manufacturer_name}</td>
                                    <td id="${loopCounter.count }3">${beanType.item_name}</td>                                               
                                    <!--<td id="${loopCounter.count }4">${beanType.model_name}</td>-->                                               
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
        <form name="form2" method="POST" action="ManufacturerItemMapController" onsubmit="return verify()">
            <div class="row mt-3">
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <input type="hidden" id="manufacturer_item_map_id" name="manufacturer_item_map_id" value="" >
                            <label>Manufacturer Name<span class="text-danger">*</span></label>                            
                            <input class="form-control myInput" type="text" id="manufacturer_name" name="manufacturer_name" value="" size="45" disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Item Name<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="item_name" name="item_name" value=""  size="45" disabled>
                        </div>
                    </div>
                </div>
                <!--                <div class="col-md-3">
                                    <div class="">
                                        <div class="form-group">
                                            <label>Model Name<span class="text-danger">*</span></label>
                                            <input class="form-control myInput" type="text" id="model_name" name="model_name" value=""  size="45" disabled>
                                        </div>
                                    </div>
                                </div>-->
            </div>

            <div class="row mt-3">
                <div class="col-md-12">
                    <div class="">
                        <div class="form-group">
                            <label>Description<span class="text-danger"></span></label>
                            <textarea class="form-control myTextArea"  id="description" name="description" name="description" disabled></textarea>
                        </div>
                    </div>
                </div>
            </div>

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

