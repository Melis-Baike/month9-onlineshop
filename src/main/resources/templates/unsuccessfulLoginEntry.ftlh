<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Unsuccessful entry</title>
    <link rel="stylesheet" href="/static/frontend/css/util.css">
</head>

<body>
<main>
    <form action="/users/login" method="get">
        <fieldset>
            <div class="legend">
                <p>Something went wrong!</p>
                <img src="/static/utilsImage/rejected.png" alt="rejected" style="display:block; width: 200px; height: 200px;">
            </div>
            <div class="center">Authorization failed</div>
            <div class="hr-line">
                <span class="details">The user does not exist</span>
            </div>
            <div class="form-element">
                <button class="register-button" type="submit">Log in</button>
            </div>
        </fieldset>
    </form>
</main>
</body>

</html>