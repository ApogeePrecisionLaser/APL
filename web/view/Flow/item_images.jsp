<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>APL</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.25/css/jquery.dataTables.min.css">
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.7.1/css/buttons.dataTables.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/jquery-ui.css">
        <link rel="stylesheet" type="text/css" href="assets/css/style.css">

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
        <script src="https://cdn.datatables.net/buttons/1.7.1/js/buttons.colVis.min.js"></script>
    </head>
    <body>
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
            function viewImage(id) {
                var queryString = "task1=viewImage&item_image_details_id=" + id;
                var url = "ModelNameController?" + queryString;
                popupwin = openPopUp(url, "Show Image", 600, 900, id);
            }

            function openPopUp(url, window_name, popup_height, popup_width, id) {
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

            function deleteImages(id, model_id) {
                var queryString = "task1=deleteImage&item_image_details_id=" + id + "&model_id=" + model_id;
                var url = "ItemImagesController?" + queryString;
                window.open(url, "_self");
            }
        </script>


        <section>
            <div class="container-fluid page_heading sectionPadding35">
                <h1>Item Images</h1>
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
                                        <th>Item Image Name</th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody>

                                    <c:forEach var="beanType" items="${requestScope['list']}"
                                               varStatus="loopCounter">
                                        <tr>
                                            <td>${loopCounter.count }</td>
                                            <td id="${loopCounter.count }2">${beanType.image_name}</td>                                                                                          
                                            <td id="${loopCounter.count }3" >
                                                <input type="button" class="btn btn-info" id="${loopCounter.count}" name="item_photo"
                                                       value="View Image" onclick="viewImage(${beanType.item_image_details_id})">
                                                <input type="button" class="btn btn-danger" id="${loopCounter.count}" name="delete"
                                                       value="Delete" onclick="deleteImages(${beanType.item_image_details_id},${beanType.model_id})">
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
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
                </div>
            </div>
        </section>

    </body>
</html>