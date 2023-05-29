<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="_csrf_header" content="<#if _csrf.headerName??>${_csrf.headerName}</#if>"/>
  <meta name="_csrf_token" content="<#if _csrf.token??>${_csrf.token}</#if>"/>
  <title><@spring.message "mainPage"/></title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
  <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.4/font/bootstrap-icons.css">
  <link rel="stylesheet" href="../static/frontend/css/index.css">
</head>
<body>
<nav class="navbar navbar-dark bg-dark" id="navbar">
  <a class="navbar-brand" id="navbar-brand"><@spring.message "title"/></a>
  <form action="" method="get">
    <label for="lang_select">
      <@spring.message "label.language" />
    </label>
    <select name="lang" id="lang_select">
      <option value=""><@spring.message "default" /></option>
      <option value="" disabled>----------</option>
      <option value="en-GB">British English</option>
      <option value="ru-RU">Русский</option>
    </select>
    <button type="submit">
        <@spring.message "button.language-change.text" />
    </button>
  </form>
  <div class="userBtn d-flex">
    <form action="/myOrders" method="get">
      <button type="submit" id="orderBtn"><@spring.message "myOrders"/></button>
    </form>
    <form action="/basket" method="get">
      <button type="submit" id="modalBtn"><@spring.message "basket"/></button>
    </form>
    <#if _csrf??>
      <form action="/users/logout" method="get">
        <input type="hidden" name="${(_csrf.parameterName)!'csrf'}" value="${(_csrf.token)!'--no-token--'}" />
        <button type="submit"><@spring.message "logOut"/></button>
      </form>
    <#else>
      <button class="logOut" id="logout" onclick="logoutHandler()"><@spring.message "logOut"/></button>
    </#if>
  </div>
</nav>
<div class="page" id="page">
  <div class="container-lg">
    <form class="form-inline d-flex flex-wrap" id="searching-form">
      <input class="form-control mr-sm-2" type="text" placeholder="<@spring.message "name"/>" aria-label="Search" name="name">
      <input type="number" id="min-price" name="min-price" min="0" step="1" placeholder="<@spring.message "min-price"/>">
      <input type="number" id="max-price" name="max-price" min="0" step="1" placeholder="<@spring.message "max-price"/>">
      <input type="number" id="quantity-field" name="quantity" min="0" placeholder="<@spring.message "quantity"/>">
      <input type="text" placeholder="<@spring.message "description"/>" name="description">
      <select name="category" id="option" class="option">
        <option value="Any"><@spring.message "category.any"/></option>
        <option value="Computers"><@spring.message "category.computers"/></option>
        <option value="Smartphones"><@spring.message "category.smartphones"/></option>
        <option value="Tablets"><@spring.message "category.tablets"/></option>
        <option value="TVs"><@spring.message "category.tvs"/></option>
        <option value="Cameras"><@spring.message "category.cameras"/></option>
        <option value="Game consoles"><@spring.message "category.gameConsoles"/></option>
        <option value="Laptops"><@spring.message "category.laptops"/></option>
        <option value="Smartwatches"><@spring.message "category.smartwatches"/></option>
        <option value="Headphones"><@spring.message "category.headphones"/></option>
        <option value="Printers"><@spring.message "category.printers"/></option>
        <option value="Speakers"><@spring.message "category.speakers"/></option>
      </select>
      <select name="brand" id="brand" class="brand">
        <option value="Any"><@spring.message "brand.any"/></option>
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
      <button class="btn btn-outline-success my-2 my-sm-0" type="submit"><@spring.message "search"/></button>
    </form>
    <div class="productArea page0" id="productArea"></div>
  </div>
</div>
<div class="modal fade productModalWindow" id="productQuantityModal" tabindex="-1" authority="dialog"
       aria-labelledby="exampleModalLongTitle" aria-hidden="true">
  <div class="modal-dialog" authority="document">
      <div class="modal-content">
          <div class="modal-header">
              <h5 class="modal-title" id="productModalTitle"><@spring.message "product"/></h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
          <div class="modal-body">
                <div class="card productModalCard" style="width: 18rem;">
                       <img class="card-img-top" src="" alt="Card image cap">
                       <div class="card-body">
                         <h5 class="card-title"></h5>
                         <p class="card-text"></p>
                         <form id="saveProductForm">
                               <input class="form-control mr-sm-2" type="number" placeholder="<@spring.message "quantity"/>"
                                      aria-label="Search" name="quantity"  min="1" pattern="[0-9]*" required>
                               <button type="submit" class="btn btn-primary saveBtn"><@spring.message "save"/></button>
                           </form>
                         </div>
                  </div>
            </div>
        </div>
    </div>
</div>
<div class="modal" tabindex="-1" role="dialog" id="feedbackModal">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title"><@spring.message "feedback"/></h5>
      </div>
      <div class="modal-body">
        <form id="feedbackSendForm" class="d-flex flex-wrap justify-content-between align-items-center flex-column">
          <input type="hidden" class="hiddenProductName" name="productName" value="">
          <input type="number" max="5" min="0.1" step="0.1" name="rating" placeholder="<@spring.message "rating"/>" class="w-50" required>
          <input type="text" name="text" placeholder="<@spring.message "text"/>" required>
          <button type="submit" class="btn btn-secondary feedbackSendBtn" id="feedbackSendBtn"><@spring.message "send"/></button>
        </form>
      </div>
    </div>
  </div>
</div>
</body>
<script language="JavaScript" src="../static/frontend/js/main.js"></script>
</html>