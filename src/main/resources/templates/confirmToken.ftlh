<#import "/spring.ftl" as spring>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
    <link rel="stylesheet" href="../static/frontend/css/formInfo.css">
    <title><@spring.message "token"/></title>
</head>
<body>
<div class="auth-page splash-header" id="auth-page">
    <div class="form">
        <form id="myForm" action="/users/token" method="post">
            <#if _csrf??>
                <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}" value="${(_csrf.token)!'csrf-token'}" />
            </#if>
            <input type="hidden" name="email" value="${email}">
            <input type="text" placeholder="${token}" readonly value="${token}" name="token">
            <input type="text" name="text" placeholder="<@spring.message "token"/>" required>
            <button type="submit"><@spring.message "submit"/></button>
        </form>
    </div>
</div>
</body>
</html>
