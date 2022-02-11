<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Apogee Precision</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
        <link rel="stylesheet" href="plugins/fontawesome-free/css/all.min.css">
        <link rel="stylesheet" href="plugins/icheck-bootstrap/icheck-bootstrap.min.css">
        <link rel="stylesheet" href="assets2/css/adminlte.min.css">
        <link rel="stylesheet" href="assets2/css/myStyle.css">
        <link rel="stylesheet" type="text/css" href="assets2/css/mobileResponsive.css">
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
                    <p class="login-box-msg text-success">OTP has been sent successfully on 87******90</p>
                    <form action="recovery_password.php" target="_blank" method="post">
                        <div class="input-group mb-0">
                            <input type="password" class="form-control rounded-0 fontFourteen" placeholder="OTP">
                            <div class="input-group-append">
                                <div class="input-group-text rounded-0">
                                    <span class="fas fa-lock"></span>
                                </div>
                            </div>
                        </div>
                        <div class="mb-2">
                            <a href="#" class="fontFourteen">Resend OTP</a>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <button type="submit" class="btn myThemeBtn btn-block">Verify</button>
                            </div>
                        </div>
                    </form>

                    <div class="d-flex justify-content-between mt-3">
                        <p class="mb-1">
                            <a href="login.php" class="fontFourteen">Go to Login</a>
                        </p>
                        <!-- <p class="mb-0">
                          <a href="register.html" class="fontFourteen">Register! New Dealer?</a>
                        </p> -->
                    </div>

                </div>
            </div>
        </div>


        <script src="plugins/jquery/jquery.min.js"></script>
        <!-- Bootstrap 4 -->
        <script src="plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
        <!-- AdminLTE App -->
        <script src="assets2/js/adminlte.min.js"></script>
    </body>
</html>
