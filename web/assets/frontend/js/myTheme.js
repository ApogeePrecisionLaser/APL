
  AOS.init();




  // $('.dropdown-menu a.dropdown-toggle').on('hover', function(e) {
// $(".dropdown-menu a.dropdown-toggle").hover(function(e){
//   $(this).parents('.dropdown-menu').first().find('.show').removeClass('show');
//   if (!$(this).next().hasClass('show')) {
//     $(this).parents('.dropdown-menu').first().find('.show').removeClass('show');
//   }
//   var $subMenu = $(this).next('.dropdown-menu');
//   $subMenu.toggleClass('show');
//   $(this).parents('li.nav-item.dropdown.show').on('hidden.bs.dropdown', function(e) {
//     $('.dropdown-submenu .show').removeClass('show');
//   });
//   return false;
// });



(function($){
    $('.dropdown-menu a.dropdown-toggle').on('click', function(e) {
      if (!$(this).next().hasClass('show')) {
        $(this).parents('.dropdown-menu').first().find('.show').removeClass("show");
      }
      var $subMenu = $(this).next(".dropdown-menu");
      $subMenu.toggleClass('show');

      $(this).parents('li.nav-item.dropdown.show').on('hidden.bs.dropdown', function(e) {
        $('.dropdown-submenu .show').removeClass("show");
      });

      return false;
    });
})(jQuery)


$('.clientSlider').owlCarousel({
    loop:true,
    margin:10,
    autoplay:true,
    dots:false,
    responsiveClass:true,
    responsive:{
        0:{
            items:1,
            nav:true
        },
        600:{
            items:2,
            nav:false
        },
        1000:{
            items:2,
            nav:true,
            dots:false
        }
    }
})



  $('.testimonialSlider').owlCarousel({
    loop:true,
    margin:10,
    dots:false,
    autoplay:true,
    responsiveClass:true,
    responsive:{
        0:{
            items:1,
            nav:true
        },
        600:{
            items:1,
            nav:false
        },
        1000:{
            items:1,
            nav:true,
            dots:false
        }
    }
})





 $('.fourSlider').owlCarousel({
     loop:true,
     margin:16,
     nav:false,
     responsive:{
         0:{
             items:1
         },
         600:{
             items:2
         },
         1000:{
             items:4
         }
     }
})



window.onscroll = function() {myFunction()};
var navbar = document.getElementById("mainMenuWrap");
var myNavsticky = navbar.offsetTop;
function myFunction() {
  if (window.pageYOffset >= myNavsticky) {
    navbar.classList.add("myNavsticky")
  } else {
    navbar.classList.remove("myNavsticky");
  }
}



$(document).on("click", '[data-toggle="lightbox"]', function(event) {
  event.preventDefault();
  $(this).ekkoLightbox();
});




  $(document).ready(function() {
  // $(".faqs-container .faq-singular:first-child").addClass("active").children(".faq-answer").slideDown();
  $(".faq-question").on("click", function(){
    if( $(this).parent().hasClass("active") ){
      $(this).next().slideUp();
      $(this).parent().removeClass("active");
    }
    else{
      $(".faq-answer").slideUp();
      $(".faq-singular").removeClass("active");
      $(this).parent().addClass("active");
      $(this).next().slideDown();
    }
  });
});




function myReadMore() {
  var dots = document.getElementById("dots");
  var moreText = document.getElementById("more");
  var btnText = document.getElementById("myReadmoreBtn");
  if (dots.style.display === "inline") {
    dots.style.display = "none";
    btnText.innerHTML = "Read less";
    moreText.style.display = "inline";
  } else {
    

    dots.style.display = "inline";
    btnText.innerHTML = "Read more";
    moreText.style.display = "none";
  }
}



