<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/CRM Dashboard/CRM_header.jsp" %>
<div class="content-wrapper" id="contentWrapper">
    <section class="content-header">
        <div class="container-fluid">


            <div class="row mb-1">
                <div class="col-sm-3">
                    <h1>News</h1>
                </div>
                <div class="col-sm-4">
                    <c:if test="${not empty message}">
                        <c:if test="${msgBgColor=='green'}">
                            <div class="alert alert-success alert-dismissible myAlertBox"  id="msg" >
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong>Success!</strong> ${message}

                            </div>
                        </c:if>
                        <c:if test="${msgBgColor=='red'}">
                            <div class="alert alert-danger alert-dismissible myAlertBox" id="msg" >
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong>OOps!</strong> ${message}

                            </div>
                        </c:if>
                    </c:if>
                </div>
                <div class="col-sm-5">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="CRMDashboardController">Home</a></li>
                        <li class="breadcrumb-item active">News</li>
                    </ol>
                </div>
            </div>
        </div><!-- /.container-fluid -->
    </section>


    <section class="content">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <form class="myForm" name="form1" action="EventController" method="post" enctype="multipart/form-data">
                        <div class="card card-primary card-outline helpFormWrap">
                            <div class="card-header">
                                <h3 class="card-title">Create new news</h3>
                            </div>
                            <div class="card-body"> 
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group mt-2">
                                            <label class="fontFourteen">Image</label>
                                            <div class="input-group">
                                                <div class="custom-file">
                                                    <input type="file" name="document_name" class="custom-file-input" onchange="readURL(this);" id="exampleInputFile">
                                                    <label class="custom-file-label" for="exampleInputFile">Choose file</label>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="fontFourteen">Title</label>
                                            <input class="form-control " placeholder="Title" id="title" name="title">
                                        </div>   
                                        <div class="form-group">
                                            <label class="fontFourteen">Description</label>
                                            <textarea class="form-control " rows="6" placeholder="Type your text" name="description" id="description"></textarea>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div>
                                            <img id="blah" src="" style="width:100%;height: 370px;object-fit: cover;">
                                        </div>
                                    </div>
                                </div>  
                            </div>
                            <div class="card-footer">
                                <div class="float-right">
                                    <button type="submit" class="btn btn-primary myThemeBtn" name="task1" value="Send"></i> Submit &nbsp<i class="fas fa-paper-plane"></i></button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</div>

<%@include file="/CRM Dashboard/CRM_footer.jsp" %>


<script>
    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $('#blah')
                        .attr('src', e.target.result);
            };
            reader.readAsDataURL(input.files[0]);
        }
    }
</script>
