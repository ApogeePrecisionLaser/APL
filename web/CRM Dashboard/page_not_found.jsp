<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>
<div class="content-wrapper" id="contentWrapper">
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-1">
                <div class="col-sm-6">
                    <h1>404 Error Page</h1>
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="#">Home</a></li>
                        <li class="breadcrumb-item active">404 Error Page</li>
                    </ol>
                </div>
            </div>
        </div><!-- /.container-fluid -->
    </section>

    <section class="content confirmPage">
        <div class="row">
            <div class="col-md-12">
                <div class="card card-primary card-outline">            
                    <div class="card-body"> 
                        <div class="text-center text-md-right">
                            <img src="CRM Dashboard/assets2/img/product/logo.png" width="140">
                        </div>             
                        <div class="boxSize">
                            <div class="text-center">
                                <img src="CRM Dashboard/assets2/img/product/error-404.png" width="75">
                            </div>
                            <div class="text-center mt-2" >
                                <h2 class="font-weight-bold text-warning"> <i class="fas fa-exclamation-triangle text-warning"></i> Oops! Page not found. </h2>
                                <p>We could not find the page you were looking for. Meanwhile, you may <a href="#">return to dashboard</a> or try using the search form.</p>
                                <!-- <p class="mb-0">Thank you for ordering with us. Your payment has been completed.  For any query, you can write us at <a href="mailto:sumit@apogeeleveller.com">sumit@apogeeleveller.com.</a> or contact us at <a href="tel:+91 7624009260">7624009260</a>. Stay Connected with us.</p> -->
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="boxOne" style="">
                                        <h3>Connected With Us</h3>
                                        <div class="SocialIconList">
                                            <ul class="ml-auto">
                                                <li><a href="https://www.facebook.com/ApogeePrecisionLasers" target="_blank"><i class="fab fa-facebook-f"></i></a></li>
                                                <li><a href="https://www.instagram.com/apogeeprecisionlasers/" target="_blank"><i class="fab fa-instagram"></i></a></li>
                                                <li><a href="https://www.youtube.com/channel/UCXrkE93UbR0DeHLWbTwEJqw" target="_blank"><i class="fab fa-youtube"></i></a></li>
                                                <li><a href="https://www.linkedin.com/company/apogee-precision/?viewAsMember=true" target="_blank"><i class="fab fa-linkedin-in"></i></a></li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="boxOne" style="">
                                        <h3>Visit Our Website</h3>
                                        <div class="SocialIconList">
                                            <a href="http://apogeeprecision.com" class="btn myThemeBtn" target="_blank">Visit Website</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </section>
</div>


<%@include file="/CRM Dashboard/CRM_footer.jsp" %>

<script>
    $(function () {
        $('#compose-textarea').summernote()
    })
</script>