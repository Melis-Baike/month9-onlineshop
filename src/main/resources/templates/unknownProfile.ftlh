<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Unsuccessful entry</title>
    <link rel="stylesheet" href="/static/frontend/css/formInfo.css">
</head>

<body>
<main>
    <form action="/users/login" method="get">
        <fieldset>
            <div class="legend">
                <p>Привет некий пользователь!</p>
                <img src="/static/utilsImage/rejected.png" alt="rejected" style="display:block; width: 200px; height: 200px;">
            </div>
            <div class="center">Неизвестный профиль</div>
            <div class="hr-line">
                <span class="details">Вы еще не авторизованы</span>
            </div>
            <div class="form-element">
                <button class="register-button" type="submit">Авторизоваться</button>
            </div>
        </fieldset>
    </form>
</main>
</body>

</html>