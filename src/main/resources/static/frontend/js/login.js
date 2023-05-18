const loginForm = document.getElementById('login-form');

function redirectToMainPage(){
    window.location.href = '/';
}

function saveUser(user) {
    const userAsJSON = JSON.stringify(user)
    localStorage.setItem('user', userAsJSON);
}

function updateOptions(options) {
    const update = { ...options };
    update.mode = 'cors';
    update.headers = { ... options.headers };
    update.headers['Content-Type'] = 'application/json';
    const user = restoreUser();
    if(user) {
        update.headers['Authorization'] = 'Basic ' + btoa(user.email + ':' + user.password);
    }
    return update;
}

function restoreUser() {
    const userAsJSON = localStorage.getItem('user');
    return JSON.parse(userAsJSON);
}

const user = restoreUser();
userCheckHandler(user);
async function userCheckHandler(user) {
    if(user !== null){
        const dto = {
            email : user.email,
            password : user.password
        }
        console.log('user exists')
        // const account = await axios.post('http://127.0.0.1:8080/users/auth', dto, updateOptions({}));
        redirectToMainPage();
    } else {
        console.log('user doesn\'t exist');
    }
}

function notRegisteredBtnHandler(){
    window.location.href = '/users/register';
}