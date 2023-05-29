<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title><@spring.message "unsuccessfulAuthorization"/></title>
    <link rel="stylesheet" href="/static/frontend/css/util.css">
</head>

<body>
<main>
    <form action="/users/login" method="get">
        <fieldset>
            <div class="legend">
                <p><@spring.message "somethingWentWrong"/>!</p>
                <img src="/static/utilsImage/rejected.png" alt="rejected" style="display:block; width: 200px; height: 200px;">
            </div>
            <div class="center"><@spring.message "authorizationFailed"/></div>
            <div class="hr-line">
                <span class="details"><@spring.message "theUserDoesNotExist"/></span>
            </div>
            <div class="form-element">
                <button class="register-button" type="submit"><@spring.message "login"/></button>
            </div>
        </fieldset>
    </form>
</main>
</body>

</html>