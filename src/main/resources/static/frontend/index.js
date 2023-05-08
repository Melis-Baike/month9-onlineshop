const authPage = document.getElementById('auth-page');
const registerForm = document.getElementById('register-form');
const loginForm = document.getElementById('login-form');
const splashArea = document.getElementById('splash');
const navbar = document.getElementById('navbar');
const userProfile = document.getElementById('userProfile');
const searchingForm = document.getElementById('searching-form');
const productArea = document.getElementById('productArea');
const page = document.getElementById('page');

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
    axios.post('http://127.0.0.1:8080/users/registration', json, {
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
function loginHandler(e){
    e.preventDefault();
    const form = e.target;
    const data = new FormData(form);
    const user = Object.fromEntries(data);
    saveUser(user);
    axios.post('http://127.0.0.1:8080/users/auth', user, updateOptions({}))
        .then(function (response){
            hideLoginPage(user);
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
        console.log();
        hideLoginPage(dto);

    } else {
        console.log('user doesn\'t exist');
    }
}

function hideLoginPage(user){
    authPage.className = '';
    splashArea.className = '';
    authPage.style.display = 'none';
    navbar.style.display = 'flex';
    userProfile.innerText = user.email;
}

searchingForm.addEventListener('submit', searchFormHandler)
function searchFormHandler(e){
    e.preventDefault();
    const form = e.target;
    const data = new FormData(form);
    console.log(data.get('name') + data.get('price') + data.get('category'));
    axios.get('http://127.0.0.1:8080/products', updateOptions({ headers : {
            'Name': data.get('name'),
            'Price': data.get('price'),
            'Category': data.get('category')
        }
    })).then(function (response){
        console.log(response.data.content);
        for (let i = 0; i < response.data.content.length; i++) {
            console.log(response.data.content[i]);
            let layout = createProductElement(response.data.content[i]);
            console.log(layout)
            let nDoc = parse(layout);
            productArea.append(nDoc.body.getElementsByClassName('productElement')[0]);
            let information = document.getElementsByClassName('productInfo');
            let info = information[information.length - 1];
            info.addEventListener('click', infoHandler);
            let reviews = document.getElementsByClassName('review');
            let review = reviews[reviews.length - 1];
            review.addEventListener('click', reviewHandler)
        }
    }).catch(function (error){
        console.log(error);
    });
}

function parse(element){
    let ndp = new DOMParser();
    return ndp.parseFromString(element, 'text/html');
}

function createProductElement(product){
    return '<div class="card productElement" style="width: 18rem;" id="product' + product.id + '">\n' +
        '  <img class="card-img-top" src="..' + product.image + '" alt="Card image cap">\n' +
        '  <div class="card-body">\n' +
        '    <h5 class="card-title">' + product.name + '</h5>\n' +
        '    <p class="card-text">' + product.description + '</p>\n' +
        '    <a href="#" class="btn btn-primary review">Reviews</a>\n' +
        '    <a href="#" class="btn btn-primary productInfo">More info</a>\n' +
        '  </div>\n' +
        '</div>'
}

function infoHandler(e){
    e.preventDefault();
    const form = e.target.parentNode;
    const productName = form.getElementsByClassName('card-title')[0].innerText;
    axios.get('http://127.0.0.1:8080/products/' + productName, updateOptions({}))
        .then(function (response){
            console.log(response);
            page.style.display = 'none';
            const layout = createProductInfoElement(response.data);
            const nDoc = parse(layout);
            document.body.append(nDoc.body.getElementsByClassName('card')[0]);
        }).catch(function (error){
        console.log(error);
    });
    console.log(form);
    console.log('aaa');
}

function createProductInfoElement(product){
    return '<div class="card d-flex justify-content-center">\n' +
        '    <div class="card-header">\n' +
        '<img src="..' + product.image + '" class="card-img-top align-self-center productInfoImage" ' +
        ' alt="productImage">' +
        '    </div>\n' +
        '    <div class="card-body">\n' +
        '        <h5 class="card-title">' + product.name + '</h5>\n' +
        '        <p class="card-text description">Description: ' + product.description + '</p>\n' +
        '        <p class="card-text quantity">Quantity: ' + product.quantity + '</p>\n' +
        '        <p class="card-text price">Price: ' + product.price + '</p>\n' +
        '        <p class="card-text category">Category: ' + product.category.name + '</p>\n' +
        '        <p class="card-text brand">Brand: ' + product.brand.name + '</p>\n' +
        '    </div>\n' +
        '    <div class="card-footer">\n' +
        '        Футер карточки\n' +
        '    </div>\n' +
        '</div>'
}

function reviewHandler(e){
    e.preventDefault();
    const form = e.target.parentNode;
    const card = form.parentNode;
    console.log(card)
    const productName = form.getElementsByClassName('card-title')[0].innerText;
    axios.get('http://127.0.0.1:8080/products/' + productName + '/reviews', updateOptions({}))
        .then(function (response){
            console.log(response.data.content);
            console.log(card.firstElementChild.src);
            page.style.display = 'none';
            const cardLayout = createProductReviewsCardElement(card);
            const cardDoc = parse(cardLayout);
            document.body.append(cardDoc.getElementsByClassName('card')[0]);
            console.log(document.body.lastElementChild.getElementsByClassName('reviewArea')[0]);
            console.log(response.data.content.length)
            for (let i = 0; i < response.data.content.length; i++) {
                console.log(response.data.content[i]);
                const reviewLayout = createReviewElement(response.data.content[i]);
                console.log(reviewLayout);
                const reviewDoc = parse(reviewLayout);
                document.body.lastElementChild.getElementsByClassName('reviewArea')[0]
                    .append(reviewDoc.body.getElementsByClassName('reviewElement')[0]);
            }
            console.log('response above');
        }).catch(function (error){
        console.log(error);
    });
}

function createProductReviewsCardElement(card){
    return '<div class="card d-flex justify-content-center">\n' +
        '    <div class="card-header">\n' +
        '<img src="' + card.firstElementChild.src + '" class="card-img-top align-self-center productInfoImage" ' +
        ' alt="productImage">' +
        '    </div>\n' +
        '    <div class="card-body">\n' +
        '        <h5 class="card-title">' + card.children[1].getElementsByClassName('card-title')[0].innerText + '</h5>\n' +
        '        <div class="reviewArea"></div>' +
        '    </div>\n' +
        '    <div class="card-footer">\n' +
        '        Футер карточки\n' +
        '    </div>\n' +
        '</div>'
}

function createReviewElement(review){
    return '<div class="reviewElement"> User: ' + review.user.email + '<br>' +
            'Text: ' + review.text + '<br>' +
            'Rating: ' + review.rating.toFixed(1) + '<br>' +
        '</div>'
}

