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
                    <p class="login-box-msg">Sign in to start your session</p>
                    <form action="LoginController" method="post">
                        <!--                        <div class="input-group mb-3">
                                                    <select class="form-control rounded-0 fontFourteen">
                                                        <option selected disabled >Select One</option>
                                                        <option>Admin</option>
                                                        <option>Sales Manager</option>
                                                        <option>Dealer</option>
                                                    </select>
                                                    <div class="input-group-append">
                                                        <div class="input-group-text rounded-0">
                                                            <span class="fas fa-user"></span>
                                                        </div>
                                                    </div>
                                                </div>-->

                        <!--                        <div class="input-group mb-3">
                                                    <input type="text" class="form-control rounded-0 fontFourteen" placeholder="Mobile" name="mobile" id="mobile">
                                                    <div class="input-group-append">
                                                        <div class="input-group-text rounded-0">
                                                            <span class="fas fa-phone"></span>
                                                        </div>
                                                    </div>
                                                </div>-->
                        <div class="input-group mb-3">
                            <input type="text" class="form-control rounded-0 fontFourteen" placeholder="Mobile Or Email" name="email" id="email">
                            <div class="input-group-append">
                                <div class="input-group-text rounded-0">
                                    <span class="fas fa-envelope"></span>
                                </div>
                            </div>
                        </div>
                        <div class="input-group mb-3">
                            <input type="password" class="form-control rounded-0 fontFourteen" name="password" placeholder="Password" required="">
                            <div class="input-group-append">
                                <div class="input-group-text rounded-0">
                                    <span class="fas fa-lock"></span>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <button  type="submit" name="task" id="login12" value="login" class="btn myThemeBtn btn-block" onclick="validate()" >Sign In</button>
                            </div>
                        </div>
                    </form>
                    <div class="d-flex justify-content-center mt-3 login_container">
                        <strong style="color: red;" id="error_msg">${message}</strong>
                    </div>


                    <div class="d-flex justify-content-between mt-3">
                        <p class="mb-1">
                            <a href="forgot_password" class="fontFourteen">Forgot password</a>
                        </p>
                        <!--                        <p class="mb-0">
                                                    <a href="register.html" class="fontFourteen">Register! New Dealer?</a>
                                                </p>-->
                    </div>

                </div>
            </div>
        </div>


        <script src="CRM Dashboard/plugins/jquery/jquery.min.js"></script>
        <!-- Bootstrap 4 -->
        <script src="CRM Dashboard/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
        <!-- AdminLTE App -->
        <script src="CRM Dashboard/assets2/js/adminlte.min.js"></script>
        <script>

                                    function validate() {
//                                        var mobile = $('#mobile').val();
                                        var email = $('#email').val();
                                        if (email == '') {
                                            $('#error_msg').text("Please enter any one of the info either Email Or Mobile!..");
                                            return false;
                                        }

                                    }
        </script>
    </body>
</html>

