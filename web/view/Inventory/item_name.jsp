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
        $("#item_type").autocomplete({

            source: function (request, response) {

                var random = document.getElementById("item_type").value;
                $.ajax({
                    url: "ItemNameController",
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
                $('#item_type').val(ui.item.label); // display the selected text
                return false;
            }
        });

        $("#search_item_type").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("search_item_type").value;
                $.ajax({
                    url: "ItemNameController",
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
                $('#search_item_type').val(ui.item.label); // display the selected text
                return false;
            }
        });

        $("#search_item_code").autocomplete({

            source: function (request, response) {
                var item_type = document.getElementById("search_item_type").value;
                var item_name = document.getElementById("search_item_name").value;
                var random = document.getElementById("search_item_code").value;
                $.ajax({
                    url: "ItemNameController",
                    dataType: "json",
                    data: {action1: "getItemCode", str: random, str2: item_type, str3: item_name},
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
                $('#search_item_code').val(ui.item.label); // display the selected text
                return false;
            }
        });

        $("#search_item_name").autocomplete({

            source: function (request, response) {
                var item_type = document.getElementById("search_item_type").value;
                var random = document.getElementById("search_item_name").value;
                $.ajax({
                    url: "ItemNameController",
                    dataType: "json",
                    data: {action1: "getItemName", str: random, str2: item_type},
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
                $('#search_item_name').val(ui.item.label); // display the selected text
                return false;
            }
        });

    });



    function makeEditable(id) {
        //debugger;
        document.getElementById("item_type").disabled = false;
        document.getElementById("item_name").disabled = false;
        document.getElementById("item_code").disabled = false;
        document.getElementById("quantity").disabled = false;
        document.getElementById("item_image").disabled = false;
        document.getElementById("description").disabled = false;
        document.getElementById("save").disabled = false;
        if (id === 'new') {
            $("#message").html("");
            document.getElementById("edit").disabled = true;
            document.getElementById("delete").disabled = true;
            document.getElementById("save").disabled = false;

        }
        if (id == 'edit') {
            document.getElementById("save_As").disabled = true;
            document.getElementById("delete").disabled = false;
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

    function showImageList(image_path_arr) {
        alert(image_path_arr);
        var queryString = "task1=viewImage&item_names_id=";
        //alert(queryString);
        var url = "ItemImageController";
    }

    function viewImages(id, img) {
        popupwin = openPopUp("Show Image List", 1000, 1000,id);
    }

    function openPopUp(window_name, popup_height, popup_width,id) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
        var queryString = "task1=getImageList&item_names_id=" + id;
        var url = "ItemImagesController?" + queryString;
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
        $('#item_name_id').val(id);
        //alert(id);
        $('#item_name').val($("#" + count + '2').html());
        $('#item_code').val($("#" + count + '3').html());
        $('#item_type').val($("#" + count + '4').html());
        $('#quantity').val($("#" + count + '5').html());
        $('#description').val($("#" + count + '6').html());
        $('#item_image_details_id').val($("#" + count + '7').html());
        document.getElementById("edit").disabled = false;
        document.getElementById("delete").disabled = false;
    }


</script>




<section>
    <div class="container-fluid page_heading sectionPadding35">
        <h1>Item Name</h1>
    </div>
</section>



<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Search Engine</h5>
        </div>
        <form name="form1" method="POST" action="ItemNameController" onsubmit="return verifySearch();" >
            <div class="row mt-3">
                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Item Type</label>
                        <input type="text" Placeholder="Item Type" name="search_item_type" id="search_item_type" value="${search_item_type}" class="form-control myInput searchInput1 w-100">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Item Name</label>
                        <input type="text" name="search_item_name" id="search_item_name" value="${search_item_name}" Placeholder="Item Name" class="form-control myInput searchInput1 w-100" >
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Item Code</label>
                        <input type="text" Placeholder="Item Code"  name="search_item_code" id="search_item_code" value="${search_item_code}" class="form-control myInput searchInput1 w-100">
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
                                <th>Item Name</th>
                                <th>Item Code </th>  
                                <th>Item Type</th>
                                <th>Quantity</th>
                                <th>Description</th>
                                <th style="display: none"></th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>

                            <c:forEach var="beanType" items="${requestScope['list']}"
                                       varStatus="loopCounter">
                                <tr
                                    onclick="fillColumn('${beanType.item_names_id}', '${loopCounter.count }');">
                                    <td>${loopCounter.count }</td>
                                    <td id="${loopCounter.count }2">${beanType.item_name}</td>
                                    <td id="${loopCounter.count }3">${beanType.item_code}</td>                                               
                                    <td id="${loopCounter.count }4">${beanType.item_type}</td>                                               
                                    <td id="${loopCounter.count }5">${beanType.quantity}</td>                                               
                                    <td id="${loopCounter.count }6">${beanType.description}</td>     
                                    <td id="${loopCounter.count }7" style="display: none">${beanType.item_image_details_id}</td>
                                    <td id="${loopCounter.count }8" >
                                        <input type="button" class="btn btn-info" id="${loopCounter.count}" name="item_photo"
                                               value="View Images" onclick="viewImages(${beanType.item_names_id}, 'ph')">
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
        <form name="form2" method="POST" action="ItemNameController" onsubmit="return verify()" enctype="multipart/form-data">
            <div class="row mt-3">
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Item Type<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="hidden" id="item_name_id" name="item_name_id" value="" >
                            <input type="hidden" id="item_image_details_id" name="item_image_details_id" value="" size="28"   />
                            <input class="form-control myInput" type="text" id="item_type" name="item_type" value="" disabled >
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Item Name<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="item_name" name="item_name" size="60" value="" disabled >
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Item Code<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="item_code" name="item_code" value="${auto_item_code}" disabled>
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
                            <label>Select Photo<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="file" multiple id="item_image" name="item_image"  size="30" value="" disabled onchange="readURL(this);"> 
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
                    <input type="button" class="btn normalBtn" name="task" id="edit" value="Edit" onclick="makeEditable(id)" disabled="">
                    <input type="submit" class="btn normalBtn" name="task" id="save" value="Save" onclick="setStatus(id)" disabled="">
                    <input type="reset" class="btn normalBtn" name="task" id="new" value="New" onclick="makeEditable(id)">
                    <input type="submit" class="btn normalBtn" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled="">
                </div>
            </div>
        </form>
    </div>
</section>



<%@include file="../layout/footer.jsp" %>

