<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Successful password recovery</title>
    <link rel="stylesheet" href="/static/frontend/css/util.css">
</head>

<body>
<main>
    <form action="/users/login" method="get">
        <fieldset>
            <div class="legend">
                <p>Welcome!</p>
                <img src="/static/utilsImage/confirmed.png" alt="confirmed" style="display:block; width: 200px; height: 200px;">
            </div>
            <div class="center">Success</div>
            <div class="hr-line">
                <span class="details">Password has been changed</span>
            </div>
            <div class="form-element">
                <button class="register-button" type="submit">Log in</button>
            </div>
        </fieldset>
    </form>
</main>
</body>

</html>