<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="_csrf_header" content="<#if _csrf.headerName??>${_csrf.headerName}</#if>"/>
    <meta name="_csrf_token" content="<#if _csrf.token??>${_csrf.token}</#if>"/>
    <title><@spring.message "authentication"/></title>
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
    <link rel="stylesheet" href="../static/frontend/css/formInfo.css">
</head>
<body>
    <div class="auth-page splash-header" id="auth-page">
        <div class="form">
            <form class="login-form" id="login-form" action="/users/auth" method="post">
                <#if _csrf??>
                    <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}" value="${(_csrf.token)!'csrf-token'}" />
                </#if>
                <input type="email" required placeholder="<@spring.message "email"/>" name="email" class="logEmail"/>
                <input type="password" required placeholder="<@spring.message "password"/>" name="password" class="logPassword"/>
                <button type="submit"><@spring.message "login"/></button>
            </form>
            <button id="createAccount" onclick="notRegisteredBtnHandler()"><@spring.message "notRegistered"/></button>
            <button id="createAccount" onclick="recoveryPasswordBtnHandler()"><@spring.message "forgotYourPassword"/></button>
            <button class="signInWithoutAuth" id="signInWithoutAuth"><@spring.message "singInWithoutAuth"/></button>
        </div>
    </div>
</body>
<script language="JavaScript" src="../static/frontend/js/login.js"></script>
</html>