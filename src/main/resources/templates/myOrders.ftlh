<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="_csrf_header" content="<#if _csrf.headerName??>${_csrf.headerName}</#if>"/>
    <meta name="_csrf_token" content="<#if _csrf.token??>${_csrf.token}</#if>"/>
    <title>My orders</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
    <link rel="stylesheet" href="../static/frontend/css/order.css">
</head>
<body>
<nav class="navbar navbar-dark bg-dark" id="navbar">
    <a class="navbar-brand" id="navbar-brand">Online-Shop</a>
</nav>
<div class="container-lg">
    <div class="myOrders" id="myOrders">
        <#if products??>
            <#list products as product>
                <div class="card orderCard ${product.id}" style="width: 18rem;">
                    <img class="card-img-top productCardImage" src="../static/images/${product.productImage}" alt="Card image cap">
                    <div class="card-body">
                        <h5 class="card-title">${product.productName}</h5>
                        <p class="card-text">
                            Quantity: ${product.productQuantity}<br>
                            Price: ${product.productPrice}<br>
                            Total price: ${product.totalPrice}<br>
                            Time: ${product.time}
                        </p>
                    </div>
                </div>
            </#list>
        <#else>
            <div>You don't have any orders</div>
        </#if>
    </div>
</div>
</body>
<script>
    document.getElementById('navbar-brand').addEventListener('click', function (){
        window.location.href = '/';
    })
</script>
</html>
