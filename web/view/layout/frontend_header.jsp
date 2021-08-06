<%--<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>--%>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Agriculture</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.theme.default.css">
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/ekko-lightbox/5.3.0/ekko-lightbox.css">
    <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Handlee&family=Inter:wght@100;200;300;400;500;600;700;800;900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="assets/frontend/css/swiper.min.css">
    <link rel="stylesheet" href="assets/frontend/css/slider_style.css">
    <link rel="stylesheet" type="text/css" href="assets/frontend/css/style.css">
    <link rel="stylesheet" type="text/css" href="assets/frontend/css/responsive.css">
  </head>
<body>

 <header id="topHeader">
  <div class="bgGreen">
    <div class="container topheader ">
      <div class="row">
        <div class="col-md-6">
          <div class="leftSide">
            <ul>
              <li><a href="tel:+91 8954894145"> <i class="fas fa-mobile-alt"></i> +91 8954894145 </a></li>
              <li><a href="mailto:info@apogeeprecision.com"><i class="far fa-envelope"></i> info@apogeeprecision.com</a></li>
            </ul>
          </div>
        </div>
        <div class="col-md-6 d-none d-sm-block">
             <div class="row justify-content-end mr-0">
                <!-- <div class="socialIconTop d-inline">
                    <ul class="ml-auto" style="width: 170px;">
                      <li><a href="#"><i class="fab fa-facebook-f"></i></a></li>
                      <li><a href="#"><i class="fab fa-instagram"></i></a></li>
                      <li><a href="#"><i class="fab fa-youtube"></i></a></li>
                      <li><a href="#"><i class="fab fa-linkedin-in"></i></a></li>
                    </ul>
                </div> -->
                 <div class="d-inline mr-3" style="height:22px;overflow: hidden">
                    <div id="google_translate_element"></div>
                </div>
                <div class="d-inline">
                    <a href="http://localhost:8080/APL/login" class="text-white">Login</a>
                </div>
            </div>        
        </div>
      </div>
    </div>
  </div>
  <div>


  <div class="mainMenuWrap" id="mainMenuWrap">
    <div class="mainMenu ">
      <nav class="navbar navbar-light navbar-expand-lg mainmenu">
          <div class="container px-0">
          <a class="navbar-brand pl-md-0 pl-3" href="home"><img src="assets/frontend/images/logo.png"></a>
          <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
          </button>

          <div class="collapse navbar-collapse bg-white" id="navbarSupportedContent">
              <ul class="navbar-nav mx-auto">
                  <li class="active"><a class="nav-link" href="home"><i class="fas fa-home"></i></a></li>
                  <li class="dropdown">
                      <a class="dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">About Us</a>
                      <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                      <li ><a href="about">Company Profile</a></li>
                      <li ><a href="about#whoWeAre">Who We Are</a></li>
                      </ul>
                  </li>
                  <li class="dropdown">
                      <a class="dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Products</a>
                      <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                      <li class="dropdown">
                          <a class="dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Laser Land Leveller</a>
                          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                          <li><a href="product_detail">Laser Receiver</a></li>
                          <li><a href="product_detail">Laser Transmitter</a></li>
                          <li><a href="product_detail">Controller</a></li>
                          <li><a href="product_detail">Scrapper</a></li>
                          <!-- <li class="dropdown">
                              <a class="dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Dropdown3</a>
                              <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                  <li><a href="#">Action</a></li>
                                  <li><a href="#">Another action</a></li>
                                  <li><a href="#">Something else here</a></li>
                              </ul>
                          </li> -->
                          </ul>
                      </li>
                      <li class="dropdown">
                          <a class="dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">GNSS Land Leveller</a>
                          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                          <li><a href="product_detail">Base Station</a></li>
                          <li><a href="product_detail">Controling Unit</a></li>
                          </ul>
                      </li>
                      <li class="dropdown">
                          <a class="dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Auto Steering Rice Transplanter</a>
                          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                          <li><a href="product_detail">Auto Steering</a></li>
                          <li><a href="product_detail">Motor</a></li>
                          <li><a href="product_detail">Controller</a></li>
                          </ul>
                      </li>
                      </ul>
                  </li>
                  <li class="dropdown">
                      <a class="dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Multimedia</a>
                      <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a href="image_gallery">Image Gallery</a></li>
                        <li><a href="video_gallery">Video Gallery</a></li>
                      </ul>
                  </li>
                  <li><a href="blogs">Blogs</a></li>
                  <li><a href="contact">Contact Us</a></li> 
              </ul>
              <div class="px-3 mb-3 mb-md-0">
                  <a href="home#enquiryForm" class="myButtonEffect text-white" style="font-size: 15px;">Enquiry Now</a>
              </div>
          </div>
        </div>
      </nav>
    </div>
  </div>
</header>
    
    
    
    
    
    