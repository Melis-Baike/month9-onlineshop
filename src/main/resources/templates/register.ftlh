<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><@spring.message "registrationForm"/></title>
    <link rel="stylesheet" href="../static/frontend/css/formInfo.css">
</head>
<body>
<div class="auth-page splash-header" id="auth-page">
    <div class="form">
      <form action="/users/register/post" method="post">
          <#if _csrf??>
              <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}" value="${(_csrf.token)!'csrf-token'}" />
          </#if>
        <input type="text" required placeholder="<@spring.message "name"/>" name="name" id="registrationName"/>
        <input type="email" required placeholder="<@spring.message "email"/>" name="email" id="registrationEmail"/>
        <input type="password" required placeholder="<@spring.message "password"/>" name="password" id="registrationPassword"/>
        <button type="submit"><@spring.message "create"/></button>
      </form>
        <button id="signIn" onclick="loginBtnHandler()"><@spring.message "alreadyRegistered"/></button>
    </div>
</div>
</body>
<script language="JavaScript" src="../static/frontend/js/register.js"></script>
</html>
