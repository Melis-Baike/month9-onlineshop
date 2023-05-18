<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Successful entry</title>
    <link rel="stylesheet" href="/static/frontend/css/formInfo.css">
</head>

<body>
<main>
    <form action="/users/login" method="get">
        <fieldset>
            <div class="legend">
                <p>Добро пожаловать!</p>
                <img src="/static/utilsImage/confirmed.png" alt="confirmed" style="display:block; width: 200px; height: 200px;">
            </div>
            <div class="center">Регистрация прошла успешно</div>
            <div class="hr-line">
                <span class="details">Ваша заявка на регистрацию подтверждена</span>
            </div>
            <div class="form-element">
                <button class="register-button" type="submit">Авторизоваться</button>
            </div>
        </fieldset>
    </form>
</main>
</body>

</html>