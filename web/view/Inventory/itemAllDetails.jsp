<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.25/css/jquery.dataTables.min.css">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.7.1/css/buttons.dataTables.min.css">
<link rel="stylesheet" type="text/css" href="assets/css/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="assets/css/style.css">

<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
<script src="assets/JS/jquery-ui.js"></script>
<script src="assets/JS/moment.min.js"></script>
<script src="https://cdn.datatables.net/1.10.25/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/buttons/1.7.1/js/dataTables.buttons.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/pdfmake.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/vfs_fonts.js"></script>
<script src="https://cdn.datatables.net/buttons/1.7.1/js/buttons.html5.min.js"></script>
<script src="https://cdn.datatables.net/buttons/1.7.1/js/buttons.print.min.js"></script>

<link rel="stylesheet" type="text/css" href="collapsetable/css/bootstrap.min.css">
<script src="collapsetable/js/javascript.js"></script>
<script src="https://cdn.datatables.net/buttons/1.7.1/js/buttons.colVis.min.js"></script>
<style>
    .selected_row {
        font-weight: bolder;
        color: blue;
        border: 3px solid black;
    }
    table.dataTable {      
        border-collapse: collapse;
    }


    .treegrid-indent {
        width: 0px;
        height: 16px;
        display: inline-block;
        position: relative;
    }

    .treegrid-expander {
        width: 0px;
        height: 16px;
        display: inline-block;
        position: relative;
        left:-17px;
        cursor: pointer;
    }
    label{
        font-size: 13px;
    }
    table{
        font-size:13px
    }
    .ui-widget{
        font-size: 1.4em;
    }
</style>

<script>
    function viewDemandNote(id, img) {
        var queryString = "task1=viewImage&inventory_id=" + id + "&type=" + img;
        // alert(queryString);
        var url = "InventoryController?" + queryString;
        popupwin = openPopUp(url, "Show Image", 600, 900);
    }
    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
        return window.open(url, window_name, window_features);
    }


</script>



<section class="marginTop30" >
    <div class="container organizationBox" style="max-width:1100px">
        <div class="headBox">
            <h5 class="">Items Details</h5>
        </div>
        <div class="row mt-3 table_div">

            <table id="tree-table" class="table table-hover table-bordered" data-page-length='6'>

                <tr>
                    <!--<th>S.No.</th>-->

                    <th>Item Name</th>
                    <!--<th style="width:40px">Item Code</th>-->
                    <th style="width:80px">Org Office</th>
                    <th style="width:80px">Manufacturer Name</th>
                    <th style="width:40px">Model Name</th>
                    <th style="width:80px">Model No.</th>
                    <th style="width:80px">Part No.</th>
                    <th style="width:80px">Key Person</th>
                    <th style="width:80px">Stock Quantity</th>
                    <th style="width:80px">Inward Quantity</th>
                    <th style="width:80px">Outward Quantity</th>
                    <!--<th style="width:80px">Reference Document Type</th>-->
                    <!--<th style="width:80px">Reference Document Id</th>-->
                    <th style="width:80px">Date Time</th>
                    <th></th>
                    <!--<th>Description</th>-->
                </tr>
                <tbody>
                    <c:forEach var="beanType" items="${requestScope['list']}"
                               varStatus="loopCounter">
                        <tr onclick="fillColumn('${beanType.inventory_id}', '${loopCounter.count }');"
                            data-id="${beanType.item_names_id}" data-parent="${beanType.parent_item_id}" data-level="${beanType.generation}">
                            <!--<td data-column="name">${loopCounter.count }</td>-->               

                            <td id="${loopCounter.count }2" data-column="name">${beanType.item_name}</td>
                            <!--<td id="${loopCounter.count }3">${beanType.item_code}</td>-->
                            <td id="${loopCounter.count }4" >${beanType.org_office}</td> 
                            <td id="${loopCounter.count }5">${beanType.manufacturer_name}</td> 
                            <td id="${loopCounter.count }6">${beanType.model}</td>
                            <td id="${loopCounter.count }7">${beanType.model_no}</td>
                            <td id="${loopCounter.count }8">${beanType.part_no}</td>
                            <td id="${loopCounter.count }9">${beanType.key_person}</td>                                               
                            <td id="${loopCounter.count }10">${beanType.stock_quantity}</td>                                               
                            <td id="${loopCounter.count }11">${beanType.inward_quantity}</td> 
                            <td id="${loopCounter.count }12">${beanType.outward_quantity}</td>
                            <!--<td id="${loopCounter.count }13">${beanType.reference_document_type}</td>-->
                            <!--<td id="${loopCounter.count }14">${beanType.reference_document_id}</td>--> 
                            <td id="${loopCounter.count }15">${beanType.date_time}</td> 

                            <c:if test="${beanType.reference_document_type!=''}">
                                <td id="${loopCounter.count }16" >
                                    <input type="btn normalBtn" class="btn btn-info" id="${loopCounter.count}" name="delivery_challan_img"
                                           value="DC" onclick="viewDemandNote(${beanType.inventory_id}, 'ph')" style="width:70px">
                                </td>
                            </c:if>

<!--<td id="${loopCounter.count }16">${beanType.description}</td>-->  


                        </tr>
                    </c:forEach>
                </tbody>
            </table>

        </div>      
    </div>
</section>

