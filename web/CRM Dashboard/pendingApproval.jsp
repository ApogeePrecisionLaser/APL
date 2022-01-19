<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>



<div class="content-wrapper" id="contentWrapper">
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2 marginTop10">
                <div class="col-sm-6">
                    <h1>Wait</h1>
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="CRMDashboardController">Dashboard</a></li>
                        <li class="breadcrumb-item active">Wait</li>
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
                                <img src="CRM Dashboard/assets2/img/product/waiting.png" width="75">
                            </div>
                            <div class="text-center mt-2" >
                                <h2 class="font-weight-bold text-warning">Please Wait for Approval </h2>
                                <p class="mb-0">Thank you for ordering with us. Your order is pending for the approval.  Once it gets approved, you will receive a notification via whatsapp or mail. You will be able to payment only after getting approval. If you have any problem, Please contact us at <a href="tel:+91 7624009260">+91 7624009260</a> or write us at <a href="mailto:sumit@apogeeleveller.com">sumit@apogeeleveller.com.</a></p>
                                <h5 class="mt-3">Your Order No.-<B> ${order_no}</B> </h5>
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





<div class="modal myPopup" id="myPopModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header rounded-0">
                <div>
                    <h4 class="modal-title">Kundan Pandey</h4>
                </div>
                <button type="button" class="close text-white" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <p class="fontFourteen">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p>
            </div>
        </div>
    </div>
</div>



<%@include file="/CRM Dashboard/CRM_footer.jsp" %>