<!--

<!DOCTYPE html>
<html>

    <head>
        <title>Apogee Levelling</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" crossorigin="anonymous">

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" integrity="sha384-gfdkjb5BdAXd+lj+gudLWI+BXq4IuLW5IT+brZEZsLFm++aCMlF1V92rMkPaX4PP" crossorigin="anonymous">
    </head>

    <style>
        body,
        html {
            margin: 0;
            padding: 0;
            height: 100%;
        }
        .user_card {
            width: 350px;
            margin-top: auto;
            margin-bottom: auto;
            background: #0A953D;
            position: relative;
            display: flex;
            justify-content: center;
            flex-direction: column;
            padding: 30px 10px;
            box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
            -webkit-box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
            -moz-box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
            border-radius: 5px;

        }
        .brand_logo_container {
            position: absolute;
            height: 170px;
            width: 170px;
            top: -75px;
            border-radius: 50%;
            background: #60a3bc;
            padding: 10px;
            text-align: center;
        }
        .brand_logo {
            height: 150px;
            width: 150px;
            border-radius: 50%;
            border: 2px solid white;
        }
        .form_container {
            margin-top: 100px;
        }
        .login_btn {
            width: 100%;
            background: #c0392b !important;
            color: white !important;
        }
        .login_btn:focus {
            box-shadow: none !important;
            outline: 0px !important;
        }
        .login_container {
            padding: 0 2rem;
        }
        .input-group-text {
            background: #c0392b !important;
            color: white !important;
            border: 0 !important;
            border-radius: 0.25rem 0 0 0.25rem !important;
        }
        .input_user,
        .input_pass:focus {
            box-shadow: none !important;
            outline: 0px !important;
        }
        .custom-checkbox .custom-control-input:checked~.custom-control-label::before {
            background-color: #c0392b !important;
        }
    </style>

    <body>
        <div class="container h-100">
            <div class="d-flex justify-content-center h-100">
                <div class="user_card">
                    <div class="d-flex justify-content-center">
                        <div class="brand_logo_container">
                            <img src="assets/images/loginBg.png" class="brand_logo" alt="Logo">
                        </div>
                    </div>

                    <div class="d-flex justify-content-center form_container">
                        <form class="form-horizontal" role="form" action="LoginController" method="post">
                            <div class="input-group mb-3">
                                <div class="input-group-append">
                                    <span class="input-group-text"><i class="fas fa-user"></i></span>
                                </div>
                                <input type="text" name="user_name" class="form-control input_user" value="" placeholder="username">
                            </div>
                            <div class="input-group mb-2">
                                <div class="input-group-append">
                                    <span class="input-group-text"><i class="fas fa-key"></i></span>
                                </div>
                                <input type="password" name="password" class="form-control input_pass" value="" placeholder="password">
                            </div>
                            <div class="form-group">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" id="customControlInline">
                                    <label class="custom-control-label" for="customControlInline">Remember me</label>
                                </div>
                            </div>
                            <div class="d-flex justify-content-center mt-3 login_container">
                                <button type="submit" name="button" class="btn login_btn">Login</button>
                                <input  type="submit" class="btn login_btn" name="task" id="login12" value="login"/>
                            </div>

                            <div class="d-flex justify-content-center mt-3 login_container">
                                <strong style="color: white;">${message}</strong>
                            </div>
                        </form>
                    </div>

                                                <div class="mt-4">
                                                            <div class="d-flex justify-content-center links">
                                                                    Don't have an account? <a href="#" class="ml-2 text-white">Sign Up</a>
                                                            </div>
                                                            <div class="d-flex justify-content-center links">
                                                                    <a href="#" class="text-white">Forgot your password?</a>
                                                            </div>
                                                    </div>
                </div>
            </div>
        </div>
    </body>
</html>-->




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

        <%
            session = request.getSession(false);
            if (session.getAttribute("log_user") != null) {
                response.sendRedirect("LoginController");
            }

        %>
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


                    <!--                    <div class="d-flex justify-content-between mt-3">
                                            <p class="mb-1">
                                                <a href="forgot-password.html" class="fontFourteen">I forgot my password</a>
                                            </p>
                                            <p class="mb-0">
                                                <a href="register.html" class="fontFourteen">Register! New Dealer?</a>
                                            </p>
                                        </div>-->

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

