<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title><@spring.message "successfulRegistration"/></title>
    <link rel="stylesheet" href="/static/frontend/css/util.css">
</head>

<body>
<main>
    <form action="/users/login" method="get">
        <fieldset>
            <div class="legend">
                <p><@spring.message "welcome"/>!</p>
                <img src="/static/utilsImage/confirmed.png" alt="confirmed" style="display:block; width: 200px; height: 200px;">
            </div>
            <div class="center"><@spring.message "registrationCompletedSuccessfully"/></div>
            <div class="hr-line">
                <span class="details"><@spring.message "yourRegistrationRequestHasBeenConfirmed"/></span>
            </div>
            <div class="form-element">
                <button class="register-button" type="submit"><@spring.message "login"/></button>
            </div>
        </fieldset>
    </form>
</main>
</body>

</html>