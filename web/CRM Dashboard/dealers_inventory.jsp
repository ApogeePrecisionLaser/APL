<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>


<div class="content-wrapper" id="contentWrapper">
    <br>
    <section class="content">
        <div class="container-fluid">
            <div class="d-flex">
                <div>
                    <!--<a href="#" class="btn btn-primary myNewLinkBtn">Dealer Add</a>-->
                    <a href="#" class="btn btn-primary myNewLinkBtn">Dealer's Inventory</a>
                </div>
                <div class="position-relative">
                    <div class="alert alert-success alert-dismissible myAlertBox">
                        <button type="button" class="close" data-dismiss="alert">&times;</button>
                        <strong>Success!</strong> Indicates a successful or positive action.
                    </div>
                </div>
            </div>        
            <div class="row mt-3">
                <div class="col-md-12">
                    <div id="dataPlace">
                        <div class="table-responsive" >
                            <table class="table table-striped1 mainTable" id="mytable" data-page-length='10'>
                                <thead>
                                    <tr>
                                        <th>Name</th>
                                        <th>Position</th>
                                        <th>Office</th>
                                        <th>Age</th>
                                        <th>Start date</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>Tiger Nixon</td>
                                        <td>System Architect</td>
                                        <td>Edinburgh</td>
                                        <td>61</td>
                                        <td>2011/04/25</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Garrett Winters</td>
                                        <td>Accountant</td>
                                        <td>Tokyo</td>
                                        <td>63</td>
                                        <td>2011/07/25</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Ashton Cox</td>
                                        <td>Junior Technical Author</td>
                                        <td>San Francisco</td>
                                        <td>66</td>
                                        <td>2009/01/12</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Cedric Kelly</td>
                                        <td>Senior Javascript Developer</td>
                                        <td>Edinburgh</td>
                                        <td>22</td>
                                        <td>2012/03/29</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Airi Satou</td>
                                        <td>Accountant</td>
                                        <td>Tokyo</td>
                                        <td>33</td>
                                        <td>2008/11/28</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Brielle Williamson</td>
                                        <td>Integration Specialist</td>
                                        <td>New York</td>
                                        <td>61</td>
                                        <td>2012/12/02</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Herrod Chandler</td>
                                        <td>Sales Assistant</td>
                                        <td>San Francisco</td>
                                        <td>59</td>
                                        <td>2012/08/06</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Rhona Davidson</td>
                                        <td>Integration Specialist</td>
                                        <td>Tokyo</td>
                                        <td>55</td>
                                        <td>2010/10/14</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Colleen Hurst</td>
                                        <td>Javascript Developer</td>
                                        <td>San Francisco</td>
                                        <td>39</td>
                                        <td>2009/09/15</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Sonya Frost</td>
                                        <td>Software Engineer</td>
                                        <td>Edinburgh</td>
                                        <td>23</td>
                                        <td>2008/12/13</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Jena Gaines</td>
                                        <td>Office Manager</td>
                                        <td>London</td>
                                        <td>30</td>
                                        <td>2008/12/19</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Quinn Flynn</td>
                                        <td>Support Lead</td>
                                        <td>Edinburgh</td>
                                        <td>22</td>
                                        <td>2013/03/03</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Charde Marshall</td>
                                        <td>Regional Director</td>
                                        <td>San Francisco</td>
                                        <td>36</td>
                                        <td>2008/10/16</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Haley Kennedy</td>
                                        <td>Senior Marketing Designer</td>
                                        <td>London</td>
                                        <td>43</td>
                                        <td>2012/12/18</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Tatyana Fitzpatrick</td>
                                        <td>Regional Director</td>
                                        <td>London</td>
                                        <td>19</td>
                                        <td>2010/03/17</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Michael Silva</td>
                                        <td>Marketing Designer</td>
                                        <td>London</td>
                                        <td>66</td>
                                        <td>2012/11/27</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Paul Byrd</td>
                                        <td>Chief Financial Officer (CFO)</td>
                                        <td>New York</td>
                                        <td>64</td>
                                        <td>2010/06/09</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Gloria Little</td>
                                        <td>Systems Administrator</td>
                                        <td>New York</td>
                                        <td>59</td>
                                        <td>2009/04/10</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Bradley Greer</td>
                                        <td>Software Engineer</td>
                                        <td>London</td>
                                        <td>41</td>
                                        <td>2012/10/13</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Dai Rios</td>
                                        <td>Personnel Lead</td>
                                        <td>Edinburgh</td>
                                        <td>35</td>
                                        <td>2012/09/26</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Jenette Caldwell</td>
                                        <td>Development Lead</td>
                                        <td>New York</td>
                                        <td>30</td>
                                        <td>2011/09/03</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Yuri Berry</td>
                                        <td>Chief Marketing Officer (CMO)</td>
                                        <td>New York</td>
                                        <td>40</td>
                                        <td>2009/06/25</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Caesar Vance</td>
                                        <td>Pre-Sales Support</td>
                                        <td>New York</td>
                                        <td>21</td>
                                        <td>2011/12/12</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Doris Wilder</td>
                                        <td>Sales Assistant</td>
                                        <td>Sydney</td>
                                        <td>23</td>
                                        <td>2010/09/20</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Angelica Ramos</td>
                                        <td>Chief Executive Officer (CEO)</td>
                                        <td>London</td>
                                        <td>47</td>
                                        <td>2009/10/09</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Gavin Joyce</td>
                                        <td>Developer</td>
                                        <td>Edinburgh</td>
                                        <td>42</td>
                                        <td>2010/12/22</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Jennifer Chang</td>
                                        <td>Regional Director</td>
                                        <td>Singapore</td>
                                        <td>28</td>
                                        <td>2010/11/14</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Brenden Wagner</td>
                                        <td>Software Engineer</td>
                                        <td>San Francisco</td>
                                        <td>28</td>
                                        <td>2011/06/07</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Fiona Green</td>
                                        <td>Chief Operating Officer (COO)</td>
                                        <td>San Francisco</td>
                                        <td>48</td>
                                        <td>2010/03/11</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Shou Itou</td>
                                        <td>Regional Marketing</td>
                                        <td>Tokyo</td>
                                        <td>20</td>
                                        <td>2011/08/14</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Michelle House</td>
                                        <td>Integration Specialist</td>
                                        <td>Sydney</td>
                                        <td>37</td>
                                        <td>2011/06/02</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Suki Burks</td>
                                        <td>Developer</td>
                                        <td>London</td>
                                        <td>53</td>
                                        <td>2009/10/22</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Prescott Bartlett</td>
                                        <td>Technical Author</td>
                                        <td>London</td>
                                        <td>27</td>
                                        <td>2011/05/07</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Gavin Cortez</td>
                                        <td>Team Leader</td>
                                        <td>San Francisco</td>
                                        <td>22</td>
                                        <td>2008/10/26</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Martena Mccray</td>
                                        <td>Post-Sales support</td>
                                        <td>Edinburgh</td>
                                        <td>46</td>
                                        <td>2011/03/09</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Unity Butler</td>
                                        <td>Marketing Designer</td>
                                        <td>San Francisco</td>
                                        <td>47</td>
                                        <td>2009/12/09</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Howard Hatfield</td>
                                        <td>Office Manager</td>
                                        <td>San Francisco</td>
                                        <td>51</td>
                                        <td>2008/12/16</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Hope Fuentes</td>
                                        <td>Secretary</td>
                                        <td>San Francisco</td>
                                        <td>41</td>
                                        <td>2010/02/12</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Vivian Harrell</td>
                                        <td>Financial Controller</td>
                                        <td>San Francisco</td>
                                        <td>62</td>
                                        <td>2009/02/14</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Timothy Mooney</td>
                                        <td>Office Manager</td>
                                        <td>London</td>
                                        <td>37</td>
                                        <td>2008/12/11</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Jackson Bradshaw</td>
                                        <td>Director</td>
                                        <td>New York</td>
                                        <td>65</td>
                                        <td>2008/09/26</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td>Olivia Liang</td>
                                        <td>Support Engineer</td>
                                        <td>Singapore</td>
                                        <td>64</td>
                                        <td>2011/02/03</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Bruno Nash</td>
                                        <td>Software Engineer</td>
                                        <td>London</td>
                                        <td>38</td>
                                        <td>2011/05/03</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Sakura Yamamoto</td>
                                        <td>Support Engineer</td>
                                        <td>Tokyo</td>
                                        <td>37</td>
                                        <td>2009/08/19</td>
                                        <td>
                                            <a href="product_edit.php?pid=245" class="btn fa fa-edit actionEdit"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this item?');" href="backend/gallery_delete.php?gid=245" class="btn far fa-trash-alt actionDelete"></a>
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

<%@include file="/CRM Dashboard/CRM_footer.jsp" %>

