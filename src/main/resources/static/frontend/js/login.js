const csrfToken = document.querySelector('meta[name="_csrf_token"]').getAttribute('content');
const signInWithoutAuthBtn = document.getElementById('signInWithoutAuth');
const loginForm = document.getElementById('login-form');

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

function recoveryPasswordBtnHandler(){
    window.location.href = '/users/specifyEmail'
}

signInWithoutAuthBtn.addEventListener('click', signInWithoutAuthBtnHandler)
function signInWithoutAuthBtnHandler(e) {
    e.preventDefault();
    axios({
        method: 'post',
        url: '/guests',
        headers: {
            'X-CSRF-TOKEN': csrfToken
        },
        data: {}
    }).then(function (response){
        loginForm.getElementsByClassName('logEmail')[0].value = response.data.email;
        loginForm.getElementsByClassName('logPassword')[0].value = response.data.password;
        console.log(loginForm.getElementsByClassName('logEmail')[0].value);
        console.log(loginForm.getElementsByClassName('logPassword')[0].value);
        loginForm.submit();
        loginForm.reset();
    }).catch(function (error){
        console.log(error);
    })
}
