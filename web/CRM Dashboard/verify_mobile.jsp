<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Apogee Precision</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
        <link rel="stylesheet" href="CRM Dashboard/plugins/fontawesome-free/css/all.min.css">
        <link rel="stylesheet" href="CRM Dashboard/plugins/icheck-bootstrap/icheck-bootstrap.min.css">
        <link rel="stylesheet" href="CRM Dashboard/assets2/css/adminlte.min.css">
        <link rel="stylesheet" href="CRM Dashboard/assets2/css/myStyle.css">
        <link rel="stylesheet" type="text/css" href="CRM Dashboard/assets2/css/mobileResponsive.css">
    </head>

    <style>
        .myLoginPage .card-primary.card-outline {
            border-top: 3px solid #3F99D3;
        }
        .myLoginPage .card-body {
            padding: 1rem 1.25rem;
        }
    </style>

    <body class="hold-transition login-page myLoginPage">
        <div class="login-box">
            <div class="card card-outline card-primary">
                <div class="card-header text-center">
                    <a href="https://www.apogeeprecision.com/" target="_blank" class="h1 text-uppercase" style="font-size: 36px;"><b>Apogee</b> Precision</a>
                </div>
                <div class="card-body">

                    <c:if test="${message!=''}">
                        <c:if test="${msgBgColor=='green'}">
                            <p class="login-box-msg text-success">${message}</p>

                        </c:if>
                        <c:if test="${msgBgColor=='red'}">
                            <!--<p class="login-box-msg text-danger">OTP has been sent successfully on ${mobile}</p>-->
                            <p class="login-box-msg text-danger">${message}</p>

                        </c:if>

                    </c:if>
                    <p class="login-box-msg text-success" id="otpValue" style="display:none"></p>
                    <form action="LoginController" method="post">
                        <input type="hidden" name="verify_type" value="${type}">
                        <input type="hidden" name="mobile" value="${mobile}">
                        <input type="hidden" name="otp_value" value="${otp}" id="otp_value">

                        <div class="input-group mb-0">
                            <input type="text" class="form-control rounded-0 fontFourteen" name="otp" placeholder="OTP">
                            <div class="input-group-append">
                                <div class="input-group-text rounded-0">
                                    <span class="fas fa-lock"></span>
                                </div>
                            </div>
                        </div>
                        <div class="mb-2">
                            <!--<a  href="javascript:history.go(0)" class="fontFourteen">Resend OTP</a>-->
                            <a  href="LoginController?task=Resend&mobile=${mobile}" class="fontFourteen">Resend OTP</a>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <button type="submit" class="btn myThemeBtn btn-block" name="task" value="VerifyOTP">Verify</button>
                            </div>
                        </div>
                    </form>

                    <div class="d-flex justify-content-between mt-3">
                        <p class="mb-1">
                            <a href="LoginController" class="fontFourteen">Go to Login</a>
                        </p>
                        <!-- <p class="mb-0">
                          <a href="register.html" class="fontFourteen">Register! New Dealer?</a>
                        </p> -->
                    </div>

                </div>
            </div>
        </div>


        <script src="CRM Dashboard/plugins/jquery/jquery.min.js"></script>
        <!-- Bootstrap 4 -->
        <script src="CRM Dashboard/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
        <!-- AdminLTE App -->
        <script src="CRM Dashboard/assets2/js/adminlte.min.js"></script>
    </body>
</html>

<script>
    $(document).ready(function () {
        var otp = $('#otp_value').val();
        if (otp != '') {
//            alert("Your OTP is----" + otp);
            $('#otpValue').show();
            $('#otpValue').html('Your OTP is--' + otp);
        }
    })

</script>
