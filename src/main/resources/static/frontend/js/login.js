function redirectToMainPage(){
    window.location.href = '/';
}

function restoreUser() {
    const userAsJSON = localStorage.getItem('user');
    return JSON.parse(userAsJSON);
}

const user = restoreUser();
userCheckHandler(user);
function userCheckHandler(user) {
    if(user !== null){
        console.log('user exists')
        redirectToMainPage();
    } else {
        console.log('user doesn\'t exist');
    }
}

function notRegisteredBtnHandler(){
    window.location.href = '/users/register';
}