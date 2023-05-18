<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Unsuccessful entry</title>
    <link rel="stylesheet" href="/static/frontend/css/formInfo.css">
</head>

<body>
<main>
    <form action="/users/register" method="get">
        <fieldset>
            <div class="legend">
                <p>Что-то пошло не так!</p>
                <img src="/static/utilsImage/rejected.png" alt="rejected" style="display:block; width: 200px; height: 200px;">
            </div>
            <div class="center">Регистрация не удалась</div>
            <div class="hr-line">
                <span class="details">Ваша заявка на регистрацию отклонена</span>
            </div>
            <div class="form-element">
                <button class="register-button" type="submit">Зарегистрироваться</button>
            </div>
        </fieldset>
    </form>
</main>
</body>
</html>