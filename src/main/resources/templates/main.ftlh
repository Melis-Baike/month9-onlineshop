<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="_csrf_header" content="<#if _csrf.headerName??>${_csrf.headerName}</#if>"/>
  <meta name="_csrf_token" content="<#if _csrf.token??>${_csrf.token}</#if>"/>
  <title>Main</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
  <script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
  <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
  <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.4/font/bootstrap-icons.css">
  <link rel="stylesheet" href="../static/frontend/css/index.css">
</head>
<body>
<nav class="navbar navbar-dark bg-dark" id="navbar">
  <a class="navbar-brand" id="navbar-brand">Online-Shop</a>
  <div class="userBtn">
    <form action="/basket" method="get">
      <button type="submit" id="modalBtn">Basket</button>
    </form>
    <#if _csrf??>
      <form action="/users/logout" method="post">
        <input type="hidden" name="${(_csrf.parameterName)!'csrf'}" value="${(_csrf.token)!'--no-token--'}" />
        <button type="submit">Выйти</button>
      </form>
    <#else>
      <button class="logOut" id="logout" onclick="logoutHandler()">Выйти</button>
    </#if>
  </div>
</nav>
<div class="page" id="page">
  <div class="container-lg">
    <form class="form-inline d-flex" id="searching-form">
      <input class="form-control mr-sm-2" type="text" placeholder="Name" aria-label="Search" name="name">
      <input type="number" id="min-price" name="min-price" min="0" step="1" placeholder="Min-Price">
      <input type="number" id="max-price" name="max-price" min="0" step="1" placeholder="Max-Price">
      <input type="number" id="quantity-field" name="quantity" min="0" placeholder="Quantity">
      <input type="text" placeholder="Description" name="description">
      <select name="category" id="option" class="option">
        <option>Any</option>
        <option>Computers</option>
        <option>Smartphones</option>
        <option>Tablets</option>
        <option>TVs</option>
        <option>Cameras</option>
        <option>Game Consoles</option>
        <option>Laptops</option>
        <option>Smartwatches</option>
        <option>Headphones</option>
        <option>Printers</option>
        <option>Speakers</option>
      </select>
      <select name="brand" id="brand" class="brand">
        <option>Any</option>
        <option>Apple</option>
        <option>Samsung</option>
        <option>Sony</option>
        <option>Bose</option>
        <option>Dell</option>
        <option>LG</option>
        <option>Canon</option>
        <option>HP</option>
        <option>Alienware</option>
        <option>Sonos</option>
      </select>
      <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
    </form>
    <div class="productArea page0" id="productArea"></div>
  </div>
</div>
<div class="modal fade productModalWindow" id="productQuantityModal" tabindex="-1" authority="dialog"
       aria-labelledby="exampleModalLongTitle" aria-hidden="true">
  <div class="modal-dialog" authority="document">
      <div class="modal-content">
          <div class="modal-header">
              <h5 class="modal-title" id="productModalTitle">Product modal</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
          <div class="modal-body">
                <div class="card productModalCard" style="width: 18rem;">
                       <img class="card-img-top" src="" alt="Card image cap">
                       <div class="card-body">
                         <h5 class="card-title"></h5>
                         <p class="card-text"></p>
                         <form id="saveProductForm">
                               <input class="form-control mr-sm-2" type="number" placeholder="Quantity"
                                      aria-label="Search" name="quantity"  min="1" pattern="[0-9]*" required>
                               <button type="submit" class="btn btn-primary saveBtn">Save</button>
                           </form>
                         </div>
                  </div>
            </div>
        </div>
    </div>
</div>
</body>
<script language="JavaScript" src="../static/frontend/js/main.js"></script>
</html>