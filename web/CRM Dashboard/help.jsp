<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>



<div class="content-wrapper" id="contentWrapper">
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-1">
                <div class="col-sm-3">
                    <h1>Help</h1>
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
                        <li class="breadcrumb-item"><a href="#">Home</a></li>
                        <li class="breadcrumb-item active">Help</li>
                    </ol>
                </div>
            </div>
        </div><!-- /.container-fluid -->
    </section>


    <section class="content">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <form class="myForm" name="form1" action="HelpController" method="post" enctype="multipart/form-data"  >
                        <div class="card card-primary card-outline helpFormWrap">
                            <div class="card-header">
                                <h3 class="card-title">Compose New Message</h3>
                            </div>
                            <div class="card-body">                
                                <div class="form-group">
                                    <input class="form-control" placeholder="Subject:" name="subject">
                                </div>
                                <div class="form-group">
                                    <textarea class="form-control" rows="6" name="message"></textarea>
                                </div>
                                <div class="form-group">
                                    <div class="btn btn-default btn-file">
                                        <i class="fas fa-paperclip"></i> Attachment
                                        <input type="file" name="document_name">
                                    </div>
                                    <p class="help-block">Max. 32MB</p>
                                </div>
                            </div>
                            <!-- /.card-body -->
                            <div class="card-footer">
                                <div class="float-right">
                                    <!-- <button type="button" class="btn btn-default"><i class="fas fa-pencil-alt"></i> Draft</button> -->
                                    <button type="submit" class="btn btn-primary myThemeBtn" name="task" value="Send"><i class="far fa-envelope"></i>&nbsp Send</button>
                                </div>
                                <!-- <button type="reset" class="btn btn-default"><i class="fas fa-times"></i> Discard</button> -->
                            </div>
                            <!-- /.card-footer -->
                        </div>
                    </form>
                    <!-- /.card -->
                </div>
                <!-- /.col -->
            </div>
            <!-- /.row -->
        </div><!-- /.container-fluid -->
    </section>



</div>
<%@include file="/CRM Dashboard/CRM_footer.jsp" %>






<script>
    $(function () {
        $('#compose-textarea').summernote()
    })
    setTimeout(function () {
        $('#msg').fadeOut('fast');
    }, 3000);
</script>