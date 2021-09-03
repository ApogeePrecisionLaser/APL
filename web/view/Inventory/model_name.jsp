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


        $("#searchManufacturer").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("searchManufacturer").value;
                $.ajax({
                    url: "ModelNameController",
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
                $('#searchManufacturer').val(ui.item.label); // display the selected text
                return false;
            }
        });


        $("#searchItem").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("searchItem").value;
                var manufacturer_name = document.getElementById("searchManufacturer").value;
                $.ajax({
                    url: "ModelNameController",
                    dataType: "json",
                    data: {action1: "getItem", str: random, str2: manufacturer_name},
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
                $('#searchItem').val(ui.item.label); // display the selected text
                return false;
            }
        });


        $("#manufacturer_name").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("manufacturer_name").value;
                $.ajax({
                    url: "ModelNameController",
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
                $('#manufacturer_name').val(ui.item.label); // display the selected text
                return false;
            }
        });

        $("#item_name").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("item_name").value;
                var manufacturer_name = document.getElementById("manufacturer_name").value;
                $.ajax({
                    url: "ModelNameController",
                    dataType: "json",
                    data: {action1: "getItem", str: random, str2: manufacturer_name},
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
                $('#item_name').val(ui.item.label); // display the selected text
                return false;
            }
        });

        $("#searchModel").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("searchModel").value;
                $.ajax({
                    url: "ModelNameController",
                    dataType: "json",
                    data: {action1: "getModel", str: random},
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
                $('#searchModel').val(ui.item.label); // display the selected text
                return false;
            }
        });

//
//        $('#lead_time').blur(function () {
//            var daily_req = $('#daily_req').val();
//            var lead_time = $('#lead_time').val();
//            var min_qty = daily_req * lead_time;
//            $('#min_quantity').val(min_qty);
//        })
    });

    function calculateMinQty()
    {
        var daily_req = parseInt(document.getElementById("daily_req").value);
        var lead_time = parseInt(document.getElementById("lead_time").value);
        document.getElementById("min_quantity").value = daily_req * lead_time;

    }

    function checkMinQty()
    {
        var daily_req = parseInt(document.getElementById("daily_req").value);
        var lead_time = parseInt(document.getElementById("lead_time").value);
        var min_qty = parseInt(document.getElementById("min_quantity").value);
        var calc_qty = daily_req * lead_time;

        if (min_qty < calc_qty) {
            // alert("reg");
            alert("Minimum Qty. is less than Calculated Qty !...");
        }
        //  document.getElementById("min_quantity").value = daily_req * lead_time;

    }



    var editable = false;
    function makeEditable(id) {
        document.getElementById("manufacturer_name").disabled = false;
        document.getElementById("item_name").disabled = false;
        document.getElementById("model").disabled = false;
        document.getElementById("daily_req").disabled = false;
        document.getElementById("min_quantity").disabled = false;
        document.getElementById("lead_time").disabled = false;
        document.getElementById("item_image").disabled = false;
        document.getElementById("description").disabled = false;
        document.getElementById("save").disabled = false;
        if (id == 'new') {
            editable = "false";
            $("#message").html("");
            document.getElementById("model_id").value = "";
            document.getElementById("edit").disabled = true;
            document.getElementById("delete").disabled = true;
            document.getElementById("model").focus();
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
            var manufacturer_name = document.getElementById("manufacturer_name").value;
            var item_name = document.getElementById("item_name").value;
            var model = document.getElementById("model").value;
            if (myLeftTrim(manufacturer_name).length == 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Manufacturer Name is required...</b></label></div>');
                document.getElementById("manufacturer_name").focus();
                return false;
            }
            if (myLeftTrim(item_name).length == 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Item Name is required...</b></label></div>');
                document.getElementById("item_name").focus();
                return false;
            }
            if (myLeftTrim(model).length == 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Model Name is required...</b></label></div>');
                document.getElementById("model").focus();
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

    function viewImages(id, img) {
        popupwin = openPopUp("Show Image List", 1000, 1000, id);
    }

    function openPopUp(window_name, popup_height, popup_width, id) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
        var queryString = "task1=getImageList&model_id=" + id;
        var url = "ItemImagesController?" + queryString;
        return window.open(url, window_name, window_features);
    }

//    function openPopUp(url, window_name, popup_height, popup_width) {ite
//        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
//        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
//        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
//        return window.open(url, window_name, window_features);
//    }
    if (!document.all) {
        document.captureEvents(Event.CLICK);
    }
    document.onclick = function () {
        if (popupwin != null && !popupwin.closed) {
            popupwin.focus();
        }
    }

    function fillColumn(id, count) {
        $('#model_id').val(id);
        $('#manufacturer_name').val($("#" + count + '2').html());
        $('#item_name').val($("#" + count + '3').html());
        $('#model').val($("#" + count + '4').html());
        $('#daily_req').val($("#" + count + '5').html());
        $('#lead_time').val($("#" + count + '6').html());
        $('#min_quantity').val($("#" + count + '7').html());
        $('#description').val($("#" + count + '8').html());
        $('#manufacturer_item_map_id').val($("#" + count + '9').html());
        $('#item_image_details_id').val($("#" + count + '10').html());
        $('#edit').attr('disabled', false);
        $('#delete').attr('disabled', false);
    }

</script>


<section>
    <div class="container-fluid page_heading sectionPadding35">
        <h1>Model Name</h1>
    </div>
</section>

<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Search Engine</h5>
        </div>
        <form name="form1" method="POST" action="ModelNameController" onsubmit="return verifySearch();" >
            <div class="row mt-3">
                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Manufacturer Name</label>
                        <input class="form-control myInput searchInput1 w-100" type="text" id="searchManufacturer" name="searchManufacturer"
                               value="${searchManufacturer}" size="30" >
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Item Name</label>
                        <input class="form-control myInput searchInput1 w-100" type="text" id="searchItem" name="searchItem"
                               value="${searchItem}" size="30" >
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Model Name</label>
                        <input class="form-control myInput searchInput1 w-100" type="text" id="searchModel" name="searchModel"
                               value="${searchModel}" size="30" >
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
                <div class="table-responsive">
                    <table class="table table-striped table-bordered" id="mytable" style="width:100%">
                        <thead>
                            <tr>
                                <th>S.No.</th>
                                <th>Manufacturer Name</th>
                                <th>Item Name</th>
                                <th>Model Name</th>
                                <th>Daily Requirement of Item</th>
                                <th>Lead Time in Days</th>
                                <th>Minimum qty</th>
                                <th>Description</th>
                                <th style="display:none"></th>
                                <th style="display: none"></th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="beanType" items="${requestScope['list']}"
                                       varStatus="loopCounter">
                                <tr
                                    onclick="fillColumn('${beanType.model_id}', '${loopCounter.count }');">
                                    <td>${loopCounter.count }</td>
                                    <td id="${loopCounter.count }2">${beanType.manufacturer_name}</td>
                                    <td id="${loopCounter.count }3">${beanType.item_name}</td>
                                    <td id="${loopCounter.count }4">${beanType.model}</td>
                                    <td id="${loopCounter.count }5">${beanType.daily_req}</td>
                                    <td id="${loopCounter.count }6">${beanType.lead_time}</td>
                                    <td id="${loopCounter.count }7">${beanType.min_qty}</td>
                                    <td id="${loopCounter.count }8">${beanType.description}</td>    
                                    <td id="${loopCounter.count }9" style="display:none">${beanType.manufacturer_item_map_id}</td>
                                    <td id="${loopCounter.count }10" style="display: none">${beanType.item_image_details_id}</td>
                                    <td id="${loopCounter.count }11" >
                                        <input type="button" class="btn btn-info" id="${loopCounter.count}" name="item_photo"
                                               value="View Images" onclick="viewImages(${beanType.model_id}, 'ph')">
                                    </td>
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
        <form name="form2" method="POST" action="ModelNameController" onsubmit="return verify()" enctype="multipart/form-data">
            <div class="row mt-3">
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Manufacturer Name<span class="text-danger">*</span></label>  
                            <input class="form-control myInput" type="hidden" id="model_id" name="model_id" value="" >
                            <input class="form-control myInput" type="hidden" id="manufacturer_item_map_id" name="manufacturer_item_map_id" value="" >
                            <input type="hidden" id="item_image_details_id" name="item_image_details_id" value="" size="28"   />

                            <input class="form-control myInput" type="text" id="manufacturer_name" name="manufacturer_name" value=""
                                   size="40" disabled>
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Item Name<span class="text-danger">*</span></label>  
                            <input class="form-control myInput" type="text" id="item_name" name="item_name" value=""
                                   size="40" disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Model Name<span class="text-danger">*</span></label>                            
                            <input class="form-control myInput" type="text" id="model" name="model" value=""
                                   size="40" disabled>
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Daily Requirement of Item<span class="text-danger">*</span></label>                            
                            <input class="form-control myInput" type="text" id="daily_req" name="daily_req" value=""
                                   size="40" disabled onblur="calculateMinQty();">
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Lead Time in Days<span class="text-danger">*</span></label>                            
                            <input class="form-control myInput" type="text" id="lead_time" name="lead_time" value=""
                                   size="40" disabled onblur="calculateMinQty();">
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Minimum Quantity<span class="text-danger">*</span></label>                            
                            <input class="form-control myInput" type="text" id="min_quantity" name="min_quantity" value=""
                                   size="40" disabled onblur="checkMinQty();">
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Select Photo<span class="text-danger"></span></label>
                            <input class="form-control myInput" type="file" multiple id="item_image" name="item_image"  size="30" value="" disabled onchange="readURL(this);"> 
                        </div>
                    </div>
                </div>


                <div class="col-md-12">
                    <div class="">
                        <div class="form-group">
                            <label>Description</label>
                            <textarea class="form-control myTextArea"  id="description" name="description" disabled></textarea>
                        </div>
                    </div>
                </div>
            </div>      
            <hr>

            <input type="hidden" id="clickedButton" value="">
            <div class="row">
                <div id="message">
                    <c:if test="${not empty message}">
                        <div class="col-md-12 text-center">
                            <label style="color:${msgBgColor}"><b>Result: ${message}</b></label>
                        </div>
                    </c:if>
                </div>
                <div class="col-md-12 text-center">                                           
                    <input type="button" class="btn normalBtn" name="task" id="edit" value="Edit" onclick="makeEditable(id)" disabled>
                    <input type="submit" class="btn normalBtn" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>
                    <input type="reset" class=" btn normalBtn" name="task" id="new" value="New" onclick="makeEditable(id)" >
                    <input type="submit" class="btn normalBtn" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                </div>
            </div>
        </form>
    </div>
</section>

<%@include file="../layout/footer.jsp" %>

