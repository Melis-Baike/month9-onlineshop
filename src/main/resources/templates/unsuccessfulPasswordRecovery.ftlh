<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Unsuccessful password recovery</title>
    <link rel="stylesheet" href="/static/frontend/css/util.css">
</head>

<body>
<main>
    <form action="/users/specifyEmail" method="get">
        <fieldset>
            <div class="legend">
                <p>Something went wrong!</p>
                <img src="/static/utilsImage/rejected.png" alt="rejected" style="display:block; width: 200px; height: 200px;">
            </div>
            <div class="center">Failed to recover password</div>
            <div class="hr-line">
                <span class="details">Most likely the confirmed password doesn't match</span>
            </div>
            <div class="form-element">
                <button class="register-button" type="submit">Password recovery</button>
            </div>
        </fieldset>
    </form>
</main>
</body>
</html>