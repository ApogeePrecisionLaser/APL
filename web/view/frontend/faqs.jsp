<%@include file="../layout/frontend_header.jsp" %>



<section >
  <div class="page_header text-center">
      <ul class="d-flex align-items-center">
        <li><a href="index.php" class="text-white">Home</a></li>
        <li>&nbsp / &nbsp</li>
        <li><span class="text-white">Support</span></li>
      </ul>
      <h2 class="text-white specialFont">Support</h2>
  </div>
</section>



<style>
.faqSection .faqs-container{
    overflow: hidden;
}
.faqSection .faq-singular {
    position: relative;
    border-bottom: 1px solid #ccc;
    padding: 0 30px 0 20px;
}
.faqSection .faq-singular:hover,
.faqSection .faq-singular.active{
    background: #f2f2f2;
    background-image: linear-gradient(90deg, #335548 0%, #335548 3px, transparent 3px, transparent 100%);
}
.faqSection .faq-singular .faq-question {
    position: relative;
    cursor: pointer;
    margin: 0;
    padding: 25px 25px 25px 0px;
    font-size: 17px;
    font-weight: 500;
    color: #255946;
  }
.faqSection .faq-singular .faq-question:before {
    position: absolute;
    content: "+";
    right: -15px;
    top: 47%;
    transform: translateY(-50%);
    font-size: 25px;
    font-weight: 500;
    transition: all .5s;
    color: #255946;
}
.faqSection .faq-singular.active .faq-question:before {
    transform: translateY(-50%) rotate(45deg) scale(1.2);
}
.faqSection .faq-answer {
    display: none;
    padding-bottom: 20px;
    font-size: 15px;
}
/*.faqSection .faq-answer .text p{
    font-size: 15px!important;
}*/
.faqSection .sideFrom{
  border: 1px solid #dee2e6!important;
  padding:25px 25px;
}
</style>


<section class="faqSection sectionPadding">
    <div class="container">
      <div class="sectionHead mb-5">
          <h2 class="specialFont" style="margin-bottom: 33px;">Get a Quote</h2>
          <p class="text-center" >There are many variations of passages of lorem ipsum <br> available but the majority have suffered alteration in some</p>
      </div><br>
      <div class="row">
        <div class="col-md-6 ">
            <div class="faqs-container" itemscope >
              <div class="faq-singular" itemscope itemprop="mainEntity">
                  <h2 class="faq-question" itemprop="name">What is the return policy?</h2>
                  <div class="faq-answer" itemscope itemprop="acceptedAnswer" itemtype="https://schema.org/Answer">
                    <div class="text" itemprop="text">
                     Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.
                    </div>
                  </div>
                </div>
                
                <div class="faq-singular" itemscope itemprop="mainEntity">
                  <h2 class="faq-question" itemprop="name">How long does it take to process a refund?</h2>
                  <div class="faq-answer" itemscope itemprop="acceptedAnswer" itemtype="https://schema.org/Answer">
                    <div itemprop="text">
                     Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.
                  </div>
                </div>
              </div>
              <div class="faq-singular" itemscope itemprop="mainEntity">
                <h2 class="faq-question" itemprop="name">What is the policy for late/non-delivery of items ordered online?</h2>
                <div class="faq-answer" itemscope itemprop="acceptedAnswer" itemtype="https://schema.org/Answer">
                  <div itemprop="text">
                   Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.
                  </div>
                </div>
              </div>
              <div class="faq-singular" itemscope itemprop="mainEntity">
                <h2 class="faq-question" itemprop="name">Is the product same as shown in pictures?</h2>
                <div class="faq-answer" itemscope itemprop="acceptedAnswer" itemtype="https://schema.org/Answer">
                  <div itemprop="text">
                   Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.
                  </div>
                </div>
              </div>
              <div class="faq-singular" itemscope itemprop="mainEntity">
                <h2 class="faq-question" itemprop="name">What is the policy for late/non-delivery of items ordered online?</h2>
                <div class="faq-answer" itemscope itemprop="acceptedAnswer" itemtype="https://schema.org/Answer">
                  <div itemprop="text">
                   Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.
                  </div>
                </div>
              </div>
              <div class="faq-singular" itemscope itemprop="mainEntity">
                <h2 class="faq-question" itemprop="name">Is the product same as shown in pictures?</h2>
                <div class="faq-answer" itemscope itemprop="acceptedAnswer" itemtype="https://schema.org/Answer">
                  <div itemprop="text">
                   Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.
                  </div>
                </div>
              </div>
            </div>
        </div>



        <div class="col-md-6 mb-3">
          <div class="sideFrom">
            <div class="mb-3 mt-1">
              <h2 class="specialFont">Ask Your Questions</h2>
            </div>
            <!-- <form action="/action_page.php" class="myFormStyle">
              <div class="row">
                <div class="col-md-6">
                  <div class="text-field">
                    <input type="text" class="w-100" required>
                    <label>Name</label>
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="text-field">
                    <input type="text" class="w-100" required>
                    <label>Contact No</label>
                  </div>
                </div>
                <div class="col-md-12">
                  <div class="text-field">
                    <input type="text" class="w-100" required>
                    <label>Email</label>
                  </div>
                </div>
                <div class="col-md-12">
                  <div class="text-field">
                    <textarea type="text" class="w-100" rows="5" required></textarea>
                    <label>Message</label>
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="form-group">
                    <button type="submit" class="btn myButtonEffect">Submit</button>
                  </div>
                </div>
              </div>
            </form> -->
            <form action="#" class="myForm queryForm" id="questionForm">
              <div class="row">
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="email"> Name: <span class="text-danger">*</span></label>
                    <input type="text" class="form-control" name="name">
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="email"> Contact Number: <span class="text-danger">*</span></label>
                    <input type="text" class="form-control" name="mobile" >
                  </div>
                </div>
                <div class="col-md-12">
                  <div class="form-group">
                    <label for="email"> E-mail: <span class="text-danger">*</span></label>
                    <input type="email" class="form-control" name="email" >
                  </div>
                </div>
                <div class="col-md-12">
                  <div class="form-group">
                    <label for="email"> Your Message: <span class="text-danger">*</span></label>
                    <textarea class="form-control" rows="4" name="message" ></textarea>
                  </div>
                </div>
                <div class="col-md-6">
                  <button type="submit" class="btn myButtonEffect">Submit</button>
                </div>
              </div>
            </form>
          </div>
        </div>


      </div>
    </div>



    
</section>





<%@include file="../layout/frontend_footer.jsp" %>
