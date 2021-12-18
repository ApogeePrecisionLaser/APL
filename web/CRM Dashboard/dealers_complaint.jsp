<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>



  <div class="content-wrapper" id="contentWrapper">
    <section class="content-header">
      <div class="container-fluid">
        <div class="row mb-1">
          <div class="col-sm-6">
            <h1>Inbox</h1>
          </div>
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Dashboard</a></li>
              <li class="breadcrumb-item active">Complaint</li>
            </ol>
          </div>
        </div>
      </div><!-- /.container-fluid -->
    </section>

    <section class="content">
      <div class="row">
        <div class="col-md-12">
          <div class="card card-primary card-outline">            
            <div class="card-body">              
              <div class="table-responsive mailbox-messages tableScrollWrap">
                <table class="table table-hover table-striped" id="mytable">
                  <thead>
                    <tr>
                      <th class="pl-2">Sl. No.</th>
                      <th class="pl-2">Name</th>
                      <th class="pl-2">Mobile</th>
                      <th class="pl-2">Email</th>
                      <th class="pl-2">Subject</th>
                      <th class="pl-2">Status</th>
                      <th class="pl-2">Time</th>
                      <th class="pl-2">Action</th>
                    </tr>
                  </thead>
                  <tbody>
                  <tr>
                    <td class="fontFourteen">1</td>
                    <td class="fontFourteen"><a href="">Kundan Pandey</a></td>
                    <td class="fontFourteen">8700328890</td>
                    <td class="fontFourteen">Kundanapogee@gmail.com</td>
                    <td class="fontFourteen">Trying to find a solution to this problem...</td>
                    <td class="fontFourteen"><i class="statusDisapprove">Unread</i></td>
                    <td class="fontFourteen">5 mins ago</td>
                    <td>
                        <a href="#" class="btn far fa-eye actionEdit" title="See Message" data-toggle="modal" data-target="#myPopModal"></a>
                        <a onclick="return confirm('Are you sure you want to delete this message?');" href="#" class="btn far fa-trash-alt actionDelete" title="Delete Message"></a>
                    </td>
                  </tr>
                  <tr>
                    <td class="fontFourteen">2</td>
                    <td class="fontFourteen"><a href="">Alexander Pierce1</a></td>
                    <td class="fontFourteen">8700328890</td>
                    <td class="fontFourteen">Kundanapogee@gmail.com</td>
                    <td class="fontFourteen">Trying to find a solution to this problem...</td>
                    <td class="fontFourteen"><i class="statusApprove">Read</i></td>
                    <td class="fontFourteen">5 mins ago</td>
                    <td>
                        <a href="#" class="btn far fa-eye actionEdit" title="See Message" data-toggle="modal" data-target="#myPopModal"></a>
                        <a onclick="return confirm('Are you sure you want to delete this message?');" href="#" class="btn far fa-trash-alt actionDelete" title="Delete Message"></a>
                    </td>
                  </tr>
                  <tr>
                    <td class="fontFourteen">3</td>
                    <td class="fontFourteen"><a href="">Alexander Pierce</a></td>
                    <td class="fontFourteen">8700328890</td>
                    <td class="fontFourteen">Kundanapogee@gmail.com</td>
                    <td class="fontFourteen">Trying to find a solution to this problem...</td>
                    <td class="fontFourteen"><i class="statusDisapprove">Unread</i></td>
                    <td class="fontFourteen">5 mins ago</td>
                    <td>
                        <a href="#" class="btn far fa-eye actionEdit" title="See Message" data-toggle="modal" data-target="#myPopModal"></a>
                        <a onclick="return confirm('Are you sure you want to delete this message?');" href="#" class="btn far fa-trash-alt actionDelete" title="Delete Message"></a>
                    </td>
                  </tr>
                  <tr>
                    <td class="fontFourteen">4</td>
                    <td class="fontFourteen"><a href="">Alexander Pierce</a></td>
                    <td class="fontFourteen">8700328890</td>
                    <td class="fontFourteen">Kundanapogee@gmail.com</td>
                    <td class="fontFourteen">Trying to find a solution to this problem...</td>
                    <td class="fontFourteen"><i class="statusApprove">Read</i></td>
                    <td class="fontFourteen">5 mins ago</td>
                    <td>
                        <a href="#" class="btn far fa-eye actionEdit" title="See Message" data-toggle="modal" data-target="#myPopModal"></a>
                        <a onclick="return confirm('Are you sure you want to delete this message?');" href="#" class="btn far fa-trash-alt actionDelete" title="Delete Message"></a>
                    </td>
                  </tr>
                  <tr>
                    <td class="fontFourteen">5</td>
                    <td class="fontFourteen"><a href="">Alexander Pierce</a></td>
                    <td class="fontFourteen">8700328890</td>
                    <td class="fontFourteen">Kundanapogee@gmail.com</td>
                    <td class="fontFourteen">Trying to find a solution to this problem...</td>
                    <td class="fontFourteen"><i class="statusDisapprove">Unread</i></td>
                    <td class="fontFourteen">5 mins ago</td>
                    <td>
                        <a href="#" class="btn far fa-eye actionEdit" title="See Message" data-toggle="modal" data-target="#myPopModal"></a>
                        <a onclick="return confirm('Are you sure you want to delete this message?');" href="#" class="btn far fa-trash-alt actionDelete" title="Delete Message"></a>
                    </td>
                  </tr>
                  <tr>
                    <td class="fontFourteen">6</td>
                    <td class="fontFourteen"><a href="">Alexander Pierce</a></td>
                    <td class="fontFourteen">8700328890</td>
                    <td class="fontFourteen">Kundanapogee@gmail.com</td>
                    <td class="fontFourteen">Trying to find a solution to this problem...</td>
                    <td class="fontFourteen"><i class="statusApprove">Read</i></td>
                    <td class="fontFourteen">5 mins ago</td>
                    <td>
                        <a href="#" class="btn far fa-eye actionEdit" title="See Message" data-toggle="modal" data-target="#myPopModal"></a>
                        <a onclick="return confirm('Are you sure you want to delete this message?');" href="#" class="btn far fa-trash-alt actionDelete" title="Delete Message"></a>
                    </td>
                  </tr>
                  <tr>
                    <td class="fontFourteen">7</td>
                    <td class="fontFourteen"><a href="">Alexander Pierce</a></td>
                    <td class="fontFourteen">8700328890</td>
                    <td class="fontFourteen">Kundanapogee@gmail.com</td>
                    <td class="fontFourteen">Trying to find a solution to this problem...</td>
                    <td class="fontFourteen"><i class="statusDisapprove">Unread</i></td>
                    <td class="fontFourteen">5 mins ago</td>
                    <td>
                        <a href="#" class="btn far fa-eye actionEdit" title="See Message" data-toggle="modal" data-target="#myPopModal"></a>
                        <a onclick="return confirm('Are you sure you want to delete this message?');" href="#" class="btn far fa-trash-alt actionDelete" title="Delete Message"></a>
                    </td>
                  </tr>
                  <tr>
                    <td class="fontFourteen">8</td>
                    <td class="fontFourteen"><a href="">Alexander Pierce</a></td>
                    <td class="fontFourteen">8700328890</td>
                    <td class="fontFourteen">Kundanapogee@gmail.com</td>
                    <td class="fontFourteen">Trying to find a solution to this problem...</td>
                    <td class="fontFourteen"><i class="statusApprove">Read</i></td>
                    <td class="fontFourteen">5 mins ago</td>
                    <td>
                        <a href="#" class="btn far fa-eye actionEdit" title="See Message" data-toggle="modal" data-target="#myPopModal"></a>
                        <a onclick="return confirm('Are you sure you want to delete this message?');" href="#" class="btn far fa-trash-alt actionDelete" title="Delete Message"></a>
                    </td>
                  </tr>
                  <tr>
                    <td class="fontFourteen">9</td>
                    <td class="fontFourteen"><a href="">Alexander Pierce</a></td>
                    <td class="fontFourteen">8700328890</td>
                    <td class="fontFourteen">Kundanapogee@gmail.com</td>
                    <td class="fontFourteen">Trying to find a solution to this problem...</td>
                    <td class="fontFourteen"><i class="statusDisapprove">Unread</i></td>
                    <td class="fontFourteen">5 mins ago</td>
                    <td>
                        <a href="#" class="btn far fa-eye actionEdit" title="See Message" data-toggle="modal" data-target="#myPopModal"></a>
                        <a onclick="return confirm('Are you sure you want to delete this message?');" href="#" class="btn far fa-trash-alt actionDelete" title="Delete Message"></a>
                    </td>
                  </tr>
                  <tr>
                    <td class="fontFourteen">10</td>
                    <td class="fontFourteen"><a href="">Alexander Pierce</a></td>
                    <td class="fontFourteen">8700328890</td>
                    <td class="fontFourteen">Kundanapogee@gmail.com</td>
                    <td class="fontFourteen">Trying to find a solution to this problem...</td>
                    <td class="fontFourteen"><i class="statusApprove">Read</i></td>
                    <td class="fontFourteen">5 mins ago</td>
                    <td>
                        <a href="#" class="btn far fa-eye actionEdit" title="See Message" data-toggle="modal" data-target="#myPopModal"></a>
                        <a onclick="return confirm('Are you sure you want to delete this message?');" href="#" class="btn far fa-trash-alt actionDelete" title="Delete Message"></a>
                    </td>
                  </tr>
                  <tr>
                    <td class="fontFourteen">11</td>
                    <td class="fontFourteen"><a href="">Alexander Pierce</a></td>
                    <td class="fontFourteen">8700328890</td>
                    <td class="fontFourteen">Kundanapogee@gmail.com</td>
                    <td class="fontFourteen">Trying to find a solution to this problem...</td>
                    <td class="fontFourteen"><i class="statusApprove">Read</i></td>
                    <td class="fontFourteen">5 mins ago</td>
                    <td>
                        <a href="#" class="btn far fa-eye actionEdit" title="See Message" data-toggle="modal" data-target="#myPopModal"></a>
                        <a onclick="return confirm('Are you sure you want to delete this message?');" href="#" class="btn far fa-trash-alt actionDelete" title="Delete Message"></a>
                    </td>
                  </tr>
                  <tr>
                    <td class="fontFourteen">12</td>
                    <td class="fontFourteen"><a href="">Alexander Pierce</a></td>
                    <td class="fontFourteen">8700328890</td>
                    <td class="fontFourteen">Kundanapogee@gmail.com</td>
                    <td class="fontFourteen">Trying to find a solution to this problem...</td>
                    <td class="fontFourteen"><i class="statusDisapprove">Unread</i></td>
                    <td class="fontFourteen">5 mins ago</td>
                    <td>
                        <a href="#" class="btn far fa-eye actionEdit" title="See Message" data-toggle="modal" data-target="#myPopModal"></a>
                        <a onclick="return confirm('Are you sure you want to delete this message?');" href="#" class="btn far fa-trash-alt actionDelete" title="Delete Message"></a>
                    </td>
                  </tr>
                  <tr>
                    <td class="fontFourteen">13</td>
                    <td class="fontFourteen"><a href=""> Second Last</a></td>
                    <td class="fontFourteen">8700328890</td>
                    <td class="fontFourteen">Kundanapogee@gmail.com</td>
                    <td class="fontFourteen">Trying to find a solution to this problem...</td>
                    <td class="fontFourteen"><i class="statusApprove">Read</i></td>
                    <td class="fontFourteen">5 mins ago</td>
                    <td>
                        <a href="#" class="btn far fa-eye actionEdit" title="See Message" data-toggle="modal" data-target="#myPopModal"></a>
                        <a onclick="return confirm('Are you sure you want to delete this message?');" href="#" class="btn far fa-trash-alt actionDelete" title="Delete Message"></a>
                    </td>
                  </tr>
                  <tr>
                    <td class="fontFourteen">14</td>
                    <td class="fontFourteen"><a href="">Alexander Last</a></td>
                    <td class="fontFourteen">8700328890</td>
                    <td class="fontFourteen">Kundanapogee@gmail.com</td>
                    <td class="fontFourteen">Trying to find a solution to this problem...</td>
                    <td class="fontFourteen"><i class="statusApprove">Read</i></td>
                    <td class="fontFourteen">5 mins ago</td>
                    <td>
                        <a href="#" class="btn far fa-eye actionEdit" title="See Message" data-toggle="modal" data-target="#myPopModal"></a>
                        <a onclick="return confirm('Are you sure you want to delete this message?');" href="#" class="btn far fa-trash-alt actionDelete" title="Delete Message"></a>
                    </td>
                  </tr>
                  
                  </tbody>
                </table>
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

