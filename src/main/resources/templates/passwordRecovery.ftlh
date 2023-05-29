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
    <title><@spring.message "passwordRecovery"/></title>
    <link rel="stylesheet" href="../static/frontend/css/formInfo.css">
</head>
<body>
    <div class="auth-page splash-header" id="auth-page">
        <div class="form">
            <div id="errormsg" style="display:none"></div>
            <form id="passwordRecoveryForm" action="/users/passwordRecovery" method="post">
                <#if _csrf??>
                    <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}" value="${(_csrf.token)!'csrf-token'}" />
                </#if>
                <input name="email" type="hidden" value="${email}">
                <input id="pass" name="newPassword" type="password" placeholder="<@spring.message "newPassword"/>" required/>
                <input id="passConfirm" name="confirmPassword" type="password" placeholder="<@spring.message "confirmPassword"/>" required/>
                <button type="submit"><@spring.message "changePassword"/></button>
            </form>
            <div class="hr-line" id="error" style="display: none">
                <span class="details"><@spring.message "passwordMismatch"/></span>
            </div>
        </div>
    </div>
</body>
</html>