const authPage = document.getElementById('auth-page');
const registerForm = document.getElementById('register-form');
const loginForm = document.getElementById('login-form');

$('.message a').click(function(){
    $('form').animate({height: "toggle", opacity: "toggle"}, "slow");
});

registerForm.addEventListener('submit', registrationHandler)
async function registrationHandler(e){
    e.preventDefault();
    const form = e.target;
    const data = new FormData(form);
    console.log(data);
    const json = parseJson(data);
    console.log(json);
    axios.post('http://localhost:8080/users/registration', json, {
        headers : {
            'Content-Type':'application/json'
        }
    }).then(function (response){
        console.log(response);
    }).catch(function (error){
        console.log(error);
    });
}

loginForm.addEventListener('submit', loginHandler)
async function loginHandler(e){
    e.preventDefault();
    const form = e.target;
    const data = new FormData(form);
    const user = Object.fromEntries(data);
    saveUser(user);
    console.log(updateOptions({}));
    console.log(user);
    axios.post('http://127.0.0.1:8080/users/auth', user, updateOptions({}))
        .then(function (response){
            console.log(response.data)
        }).catch(function (error) {
        console.log(error);
        localStorage.clear();
    });
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

function saveUser(user) {
    const userAsJSON = JSON.stringify(user)
    localStorage.setItem('user', userAsJSON);
}


function parseJson(data){
    let object = {};
    data.forEach(function(value, key){
        object[key] = value;
    });
    return JSON.stringify(object);
}


