<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Authentication</title>
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
    <link rel="stylesheet" href="/static/frontend/css/index.css">
</head>
<body>
    <div class="auth-page splash-header" id="auth-page">
        <div class="form">
            <form class="login-form" id="login-form" action="/users/auth" method="post">
                <#if _csrf??>
                    <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}" value="${(_csrf.token)!'csrf-token'}" />
                </#if>
                <input type="email" required placeholder="email" name="email"/>
                <input type="password" required placeholder="password" name="password"/>
                <button type="submit">Login</button>
            </form>
            <button id="createAccount" onclick="notRegisteredBtnHandler()">Not registered?</button>
            <button class="signInWithoutAuth" id="signInWithoutAuth" onclick="redirectToMainPage()">Sign in without auth</button>
        </div>
    </div>
</body>
<script language="JavaScript" src="/static/frontend/js/login.js"></script>
</html>