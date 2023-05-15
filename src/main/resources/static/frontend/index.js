const authPage = document.getElementById('auth-page');
const registerForm = document.getElementById('register-form');
const loginForm = document.getElementById('login-form');
const splashArea = document.getElementById('splash');
const navbar = document.getElementById('navbar');
const searchingForm = document.getElementById('searching-form');
const productArea = document.getElementById('productArea');
const page = document.getElementById('page');
const main = document.getElementById('navbar-brand');
const logout = document.getElementById('logout');
const basket = document.getElementById('basket');
const productQuantityModal = document.getElementById('productQuantityModal');
const saveBtn = document.getElementsByClassName('saveBtn')[0];
const signInWithoutAuthBtn = document.getElementById('signInWithoutAuth');

$('.message a').click(function(){
    $('form').animate({height: "toggle", opacity: "toggle"}, "slow");
});

registerForm.addEventListener('submit', registrationHandler)
async function registrationHandler(e){
    e.preventDefault();
    const form = e.target;
    const data = new FormData(form);
    const json = parseJson(data);
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
            console.log(response);
            hideLoginPage();
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
        hideLoginPage();

    } else {
        console.log('user doesn\'t exist');
    }
}

function hideLoginPage(){
    authPage.className = '';
    splashArea.className = '';
    authPage.style.display = 'none';
    navbar.style.display = 'flex';
    page.style.display = 'block';
    // userProfile.innerText = user.email;
}

searchingForm.addEventListener('submit', searchFormHandler)
function searchFormHandler(e){
    e.preventDefault();
    const form = e.target;
    const data = new FormData(form);
    const page = productArea.className.substring(16);
    const size = 2;
    axios.get('http://127.0.0.1:8080/products/' + page + "/" + size, updateOptions({ headers : {
            'Name': data.get('name'),
            'Min-Price': data.get('min-price'),
            'Max-Price': data.get('max-price'),
            'Quantity': data.get('quantity'),
            'Description': data.get('description'),
            'Category': data.get('category'),
            'Brand': data.get('brand')
        }
    })).then(function (response){
        productArea.innerHTML = '';
        for (let i = 0; i < response.data.content.length; i++) {
            let layout;
            if(restoreUser() !== null) {
                layout = createProductElement(response.data.content[i]);
                createFoundProducts(layout);
                let reviews = document.getElementsByClassName('review');
                let review = reviews[reviews.length - 1];
                review.addEventListener('click', reviewHandler);
            } else {
                layout = createProductElementForNotAuthUsers(response.data.content[i]);
                createFoundProducts(layout);
            }
        }
        if(response.data.content.length !== 0) {
            const previousPageBtn = document.getElementById('previousPageBtn');
            if(!previousPageBtn){
                const previousPageBtnMarkUp = createPreviousPageBtn();
                const previousPageDoc = parse(previousPageBtnMarkUp);
                productArea.insertAdjacentElement('afterend', previousPageDoc
                    .getElementsByClassName('previousPageBtn')[0]);
                document.getElementsByClassName('previousPageBtn')[0].addEventListener('click', previousPageEventHandler);
            }
            const nextPageBtn = document.getElementById('nextPageBtn');
            if (!nextPageBtn) {
                const nextPageBtnMarkUp = createNextPageBtn();
                const nextPageBtnDoc = parse(nextPageBtnMarkUp);
                productArea.insertAdjacentElement('afterend', nextPageBtnDoc
                    .getElementsByClassName('nextPageBtn')[0]);
                document.getElementsByClassName('nextPageBtn')[0].addEventListener('click', nextPageEventHandler);
            }
        }
    }).catch(function (error){
        console.log(error);
    });
}

function createFoundProducts(layout){
    let nDoc = parse(layout);
    productArea.append(nDoc.body.getElementsByClassName('productElement')[0]);
    let information = document.getElementsByClassName('productInfo');
    let info = information[information.length - 1];
    info.addEventListener('click', infoHandler);
    let putBtns = document.getElementsByClassName('putBtn');
    let putBtn = putBtns[putBtns.length - 1];
    putBtn.addEventListener('click', formatProductModalWindow);
    saveBtn.addEventListener('click', putInBasketBtnHandler);
}

function previousPageEventHandler(e){
    e.preventDefault();
    const page = productArea.className.substring(16);
    if(page !== '0') {
        const nextPage = parseInt(page) - 1;
        productArea.className = 'productArea page' + nextPage;
        console.log(productArea.className);
        const submitButton = searchingForm.querySelector('[type="submit"]');
        submitButton.click();
    }
}

function nextPageEventHandler(e){
    e.preventDefault();
    const page = productArea.className.substring(16);
    const nextPage = parseInt(page) + 1;
    productArea.className = 'productArea page' + nextPage;
    console.log(productArea.className);
    const submitButton = searchingForm.querySelector('[type="submit"]');
    submitButton.click();
}

function parse(element){
    let ndp = new DOMParser();
    return ndp.parseFromString(element, 'text/html');
}

function createProductElement(product){
    return '<div class="card productElement" style="width: 21rem;" id="product' + product.id + '">\n' +
        '  <img class="card-img-top productCardImage" src="/static/images/' + product.image + '" alt="Card image cap">\n' +
        '  <div class="card-body productCardBody">\n' +
        '    <h5 class="card-title">' + product.name + '</h5>\n' +
        '    <p class="card-text">' + product.description + '</p>\n' +
        '    <div class="cardBtns">' +
        '       <a href="#" class="btn btn-primary review cardBtn">Reviews</a>\n' +
        '       <a href="#" class="btn btn-primary productInfo cardBtn">More info</a>\n' +
        '       <button type="button" class="btn btn-primary putBtn cardBtn" data-bs-toggle="modal" data-bs-target="#productQuantityModal" ' +
        '           id="modalBtn">\n' +
        '           Put in the basket\n' +
        '       </button>\n' +
        '    </div>' +
        '  </div>\n' +
        '</div>'
}

function createNextPageBtn(){
    return '<button class="btn btn-primary nextPageBtn" id="nextPageBtn">Next page</button>';
}

function createPreviousPageBtn(){
    return '<button class="btn btn-primary previousPageBtn" id="previousPageBtn">Previous page</button>';
}

function createProductElementForNotAuthUsers(product){
    return '<div class="card productElement" style="width: 21rem;" id="product' + product.id + '">\n' +
        '  <img class="card-img-top productCardImage" src="/static/images/' + product.image + '" alt="Card image cap">\n' +
        '  <div class="card-body productCardBody">\n' +
        '    <h5 class="card-title">' + product.name + '</h5>\n' +
        '    <p class="card-text">' + product.description + '</p>\n' +
        '    <div class="cardBtns">' +
        '       <a href="#" class="btn btn-primary productInfo cardBtn">More info</a>\n' +
        '       <button type="button" class="btn btn-primary putBtn cardBtn" data-bs-toggle="modal" data-bs-target="#productQuantityModal" ' +
        '           id="modalBtn">\n' +
        '           Put in the basket\n' +
        '       </button>\n' +
        '    </div>' +
        '  </div>\n' +
        '</div>'
}

function infoHandler(e){
    e.preventDefault();
    const form = e.target.parentNode.parentNode;
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
}

function createProductInfoElement(product){
    return '<div class="card d-flex justify-content-center more-info">\n' +
        '    <div class="card-header">\n' +
        '<img src="/static/images/' + product.image + '" class="card-img-top align-self-center productInfoImage" ' +
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
        '</div>'
}

function reviewHandler(e){
    e.preventDefault();
    const form = e.target.parentNode.parentNode;
    const card = form.parentNode;
    const productName = form.getElementsByClassName('card-title')[0].innerText;
    axios.get('http://127.0.0.1:8080/products/' + productName + '/reviews', updateOptions({}))
        .then(function (response){
            page.style.display = 'none';
            const cardLayout = createProductReviewsCardElement(card);
            const cardDoc = parse(cardLayout);
            document.body.append(cardDoc.getElementsByClassName('card')[0]);
            for (let i = 0; i < response.data.content.length; i++) {
                const reviewLayout = createReviewElement(response.data.content[i]);
                const reviewDoc = parse(reviewLayout);
                document.body.lastElementChild.getElementsByClassName('reviewArea')[0]
                    .append(reviewDoc.body.getElementsByClassName('reviewElement')[0]);
            }
        }).catch(function (error){
        console.log(error);
    });
}

function createProductReviewsCardElement(card){
    return '<div class="card d-flex justify-content-center more-info">\n' +
        '    <div class="card-header">\n' +
        '<img src="' + card.firstElementChild.src + '" class="card-img-top align-self-center productInfoImage" ' +
        ' alt="productImage">' +
        '    </div>\n' +
        '    <div class="card-body">\n' +
        '        <h5 class="card-title">' + card.children[1].getElementsByClassName('card-title')[0].innerText + '</h5>\n' +
        '        <div class="reviewArea"></div>' +
        '    </div>\n' +
        '</div>'
}

function createReviewElement(review){
    return '<div class="reviewElement"> User: ' + review.user.email + '<br>' +
            'Text: ' + review.text + '<br>' +
            'Rating: ' + review.rating.toFixed(1) + '<br>' +
        '</div>'
}

main.addEventListener('click', mainHandler);
function mainHandler(e){
    e.preventDefault();
    if(page.style.display === 'none') {
        console.log('test info')
        document.body.getElementsByClassName('more-info')[0].remove();
        page.style.display = 'block';
    }
}

logout.addEventListener('click', logoutHandler)
function logoutHandler(e){
    e.preventDefault();
    localStorage.clear();
    showSplashScreen();
}

function showSplashScreen(){
    authPage.className = 'auth-page splash-header';
    splashArea.className = 'splash';
    authPage.style.display = 'flex';
    page.style.display = 'none';
    navbar.style.display = 'none';
}


function putInBasketBtnHandler(e){
    e.preventDefault();
    const submitForm = e.target.parentElement;
    const form = e.target.parentNode.parentElement.parentElement;
    const data = new FormData(submitForm);
    const order = {
        name: form.getElementsByClassName('card-title')[0].innerText,
        quantity: data.get('quantity')
    }
    axios.post('http://127.0.0.1:8080/basket/ordering', order, updateOptions({}))
        .then(function (response){
            console.log(response);
            const layout = createProductForBasket(form, data.get('quantity'));
            const nDoc = parse(layout);
            const title = nDoc.body.getElementsByClassName('card')[0].getElementsByClassName('card-title')[0].innerText;
            const src = nDoc.body.getElementsByClassName('card')[0].getElementsByClassName('card-img-top')[0].src;
            basket.append(nDoc.body.getElementsByClassName('card')[0]);
            const quantity = form.getElementsByClassName('card-text')[0].innerText.substring(10) - data.get('quantity');
            form.getElementsByClassName('card-text')[0].innerText = 'Quantity: ' + quantity;
            let removeButtons = document.body.getElementsByClassName('remove');
            let lastRemoveButton = removeButtons[removeButtons.length - 1];
            lastRemoveButton.addEventListener('click', e => removeProductHandler(e, response.data.id));
            document.body.getElementsByClassName('changeQuantityForm')[0].addEventListener('submit',
                    e => changeQuantityBtnHandler(e, response.data.id));
            const products = restoreProducts() || [];
            const productJSON = {
                id: response.data.id,
                name: title,
                src: src,
                quantity: data.get('quantity')
            };
            products.push(productJSON);
            saveProducts(products);
        }).catch(function (error){
        console.log(error);
    })
}

function restoreProducts(){
    const productsAsJSON = localStorage.getItem('products');
    return JSON.parse(productsAsJSON);
}

function saveProducts(products){
    const productsAsJSON = JSON.stringify(products);
    localStorage.setItem('products', productsAsJSON);
}

async function formatProductModalWindow(e){
    e.preventDefault();
    const form = e.target.parentElement.parentElement.parentElement;
    const imageSrc = form.getElementsByClassName('card-img-top')[0].src;
    const title = form.getElementsByClassName('card-title')[0].innerText;
    const product = await axios.get('http://127.0.0.1:8080/products/' + title, updateOptions({}));
    productQuantityModal.getElementsByClassName('card-img-top')[0].src = imageSrc;
    productQuantityModal.getElementsByClassName('card-title')[0].innerText = title;
    productQuantityModal.getElementsByClassName('card-text')[0].innerText = 'Quantity: ' + product.data.quantity;
}

while (basket.firstChild){
    basket.removeChild(basket.firstChild);
}

function createProductForBasket(card, quantity){
    const imageSrc = card.getElementsByClassName('card-img-top')[0].src;
    const title = card.getElementsByClassName('card-title')[0].innerText;
    return '<div class="card basketCard" style="width: 18rem;">\n' +
        '  <img class="card-img-top basketCardImage" src="' + imageSrc + '" alt="Card image cap">\n' +
        '  <div class="card-body">\n' +
        '    <h5 class="card-title">' + title + '</h5>\n' +
        '    <p class="card-text">Quantity: ' + quantity + '</p>\n' +
        '    <a href="#" class="btn btn-primary remove" id="productRemoveFromBasket">Remove</a>\n' +
        '    <form class="changeQuantityForm" id="changeQuantityForm">' +
        '       <input class="form-control mr-sm-2" type="number" placeholder="Quantity" name="quantity" required>\n' +
        '       <button type="submit" class="btn btn-primary changeQuantityBtn" id="changeQuantityBtn">Change quantity</button>' +
        '    </form>' +
        '  </div>\n' +
        '</div>'
}

function removeProductHandler(e, basketProductId){
    e.preventDefault();
    const form = e.target.parentElement.parentElement;
    console.log(basketProductId);
    axios.post('http://127.0.0.1:8080/basket/' + basketProductId, {},updateOptions({
        headers: {
            'X-HTTP-Method-Override':'DELETE'
        }
    }))
        .then(function (response){
            console.log(response);
            let products = restoreProducts() || [];
            products = products.filter(function(product) {
                return product.id !== basketProductId;
            });
            saveProducts(products);
            form.remove();
        }).catch(function (error){
        console.log(error);
    });
}


document.querySelector('form').addEventListener('submit', function(e) {
    e.preventDefault();

    let minPrice = parseInt(document.getElementById('min-price').value);
    let maxPrice = parseInt(document.getElementById('max-price').value);

    console.log('Минимальная цена:', minPrice);
    console.log('Максимальная цена:', maxPrice);
});

function changeQuantityBtnHandler(e, basketProductId){
    e.preventDefault();
    const form = e.target;
    const parentElem = e.target.parentElement;
    const data = new FormData(form);
    const quantity = data.get('quantity');
    const previousQuantity = parentElem.getElementsByClassName('card-text')[0].innerText.substring(10);
    const changeProductQuantityDTO = {
        productName: parentElem.getElementsByClassName('card-title')[0].innerText,
        basketProductId: basketProductId,
        previousQuantity: previousQuantity,
        currentQuantity: quantity
    };
    axios.post('http://127.0.0.1:8080/basket/quantity', changeProductQuantityDTO, updateOptions({}))
        .then(function (response){
            console.log(response);
            parentElem.getElementsByClassName('card-text')[0].innerText = 'Quantity: ' + response.data.productQuantity;
            let products = restoreProducts();
            for (let i = 0; i < products.length; i++) {
                if(basketProductId === products[i].id){
                    products[i].quantity = quantity;
                }
            }
            saveProducts(products);
        })
        .catch(function (error){
            console.log(error);
        });
}

signInWithoutAuthBtn.addEventListener('click', signInWithoutAuthHandler)
function signInWithoutAuthHandler(e) {
    e.preventDefault();
    hideLoginPage();
}

