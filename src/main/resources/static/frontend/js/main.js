const searchingForm = document.getElementById('searching-form');
const productArea = document.getElementById('productArea');
const page = document.getElementById('page');
const main = document.getElementById('navbar-brand');
const basket = document.getElementById('basket');
const productQuantityModal = document.getElementById('productQuantityModal');
const saveProductForm = document.getElementById('saveProductForm');
const feedbackSendForm = document.getElementById('feedbackSendForm');
const csrfToken = document.querySelector('meta[name="_csrf_token"]').getAttribute('content');
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

searchingForm.addEventListener('submit', searchFormHandler)
function searchFormHandler(e){
    e.preventDefault();
    const form = e.target;
    const data = new FormData(form);
    const page = productArea.className.substring(16);
    const size = 2;
    axios({
        method: 'get',
        url: '/products/' + page + '/' + size,
        headers: {
            'Name': data.get('name'),
            'Min-Price': data.get('min-price'),
            'Max-Price': data.get('max-price'),
            'Quantity': data.get('quantity'),
            'Description': data.get('description'),
            'Category': data.get('category'),
            'Brand': data.get('brand'),
            'X-CSRF-TOKEN': csrfToken,
        }
    }).then(function (parentResponse){
        productArea.innerHTML = '';
        for (let i = 0; i < parentResponse.data.content.length; i++) {
            axios({
                method: 'get',
                url: '/orders/check/' + parentResponse.data.content[i].name,
                headers: {
                    'X-CSRF-TOKEN': csrfToken,
                }
            }).then(function (response){
                let layout;
                let nDoc;
                if(response.data !== true){
                    layout = createProductElement(parentResponse.data.content[i]);
                    nDoc = parse(layout);
                    createFoundProducts(nDoc);
                } else {
                    layout = createProductElementWithFeedback(parentResponse.data.content[i]);
                    nDoc = parse(layout);
                    createFoundProducts(nDoc);
                    let feedbackBtns = document.getElementsByClassName('feedbackBtn');
                    let feedbackBtn = feedbackBtns[feedbackBtns.length - 1];
                    feedbackBtn.addEventListener('click', feedbackHandler);
                }
            }).catch(function (error){
                console.log(error);
            })
        }
        if(parentResponse.data.content.length !== 0) {
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

function createFoundProducts(nDoc){
    productArea.append(nDoc.body.getElementsByClassName('productElement')[0]);
    let information = document.getElementsByClassName('productInfo');
    let info = information[information.length - 1];
    info.addEventListener('click', infoHandler);
    let putBtns = document.getElementsByClassName('putBtn');
    let putBtn = putBtns[putBtns.length - 1];
    putBtn.addEventListener('click', formatProductModalWindow);
    let reviews = document.getElementsByClassName('review');
    let review = reviews[reviews.length - 1];
    review.addEventListener('click', reviewHandler);
    saveProductForm.addEventListener('submit', putInBasketBtnHandler);
}

function feedbackHandler(e){
    e.preventDefault();
    feedbackSendForm.getElementsByClassName('hiddenProductName')[0].value = e.target
        .parentElement.parentElement.getElementsByClassName('card-title')[0].innerText;
}


function previousPageEventHandler(e){
    e.preventDefault();
    const page = productArea.className.substring(16);
    if(page !== '0') {
        const nextPage = parseInt(page) - 1;
        productArea.className = 'productArea page' + nextPage;
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
        '  <img class="card-img-top productCardImage" src="../static/images/' + product.image + '" alt="Card image cap">\n' +
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

function createProductElementWithFeedback(product){
    return '<div class="card productElement" style="width: 21rem;" id="product' + product.id + '">\n' +
        '  <img class="card-img-top productCardImage" src="../static/images/' + product.image + '" alt="Card image cap">\n' +
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
        '       <button type="button" class="btn btn-primary feedbackBtn cardBtn" id="feedbackBtn" data-bs-toggle="modal" data-bs-target="#feedbackModal" ' +
        '           id="feedbackModalBtn">' +
        '           Feedback' +
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

function infoHandler(e){
    e.preventDefault();
    const form = e.target.parentNode.parentNode;
    const productName = form.getElementsByClassName('card-title')[0].innerText;
        axios({
            method: 'get',
            url: '/products/' + productName,
            headers: {
                'X-CSRF-TOKEN': csrfToken,
            }
        }).then(function (response){
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
        '<img src="../static/images/' + product.image + '" class="card-img-top align-self-center productInfoImage" ' +
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
    axios({
        method: 'get',
        url: '/products/' + productName + '/reviews',
        headers: {
            'X-CSRF-TOKEN': csrfToken,
        }
    }).then(function (response){
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

function logoutHandler(){
    window.location.href = '/login?logout';
}

function putInBasketBtnHandler(e){
    e.preventDefault();
    const submitForm = e.target;
    const form = e.target.parentElement.parentElement;
    const data = new FormData(submitForm);
    const order = {
        name: form.getElementsByClassName('card-title')[0].innerText,
        quantity: data.get('quantity')
    }
    console.log(order)
    axios({
        method: 'post',
        url: '/basket/ordering',
        headers: {
            'X-Requested-With': 'XMLHttpRequest',
            'X-CSRF-TOKEN': csrfToken,
            'Content-Type': 'application/json',
            withCredentials: true
        },
        data: order
    }).then(function(response) {
            console.log(response.data);
            const quantity = form.getElementsByClassName('card-text')[0].innerText.substring(10) - data.get('quantity');
            form.getElementsByClassName('card-text')[0].innerText = 'Quantity: ' + quantity;
        })
        .catch(function(error) {
            console.log(error);
        });
}

function formatProductModalWindow(e){
    e.preventDefault();
    const form = e.target.parentElement.parentElement.parentElement;
    const imageSrc = form.getElementsByClassName('card-img-top')[0].src;
    const title = form.getElementsByClassName('card-title')[0].innerText;
    axios({
        method: 'get',
        url: '/products/' + title,
        headers: {
            'X-CSRF-TOKEN': csrfToken
        }
    }).then(function (response){
        productQuantityModal.getElementsByClassName('card-img-top')[0].src = imageSrc;
        productQuantityModal.getElementsByClassName('card-title')[0].innerText = title;
        productQuantityModal.getElementsByClassName('card-text')[0].innerText = 'Quantity: ' + response.data.quantity;
    }).catch(function (error){
        console.log(error);
    })
}

feedbackSendForm.addEventListener('submit', feedbackSendFormHandler)
function feedbackSendFormHandler(e){
    e.preventDefault();
    const form = e.target;
    const data = new FormData(form);
    console.log(data.get('productName'));
    console.log(data.get('rating'));
    console.log(data.get('text'));
    axios({
        method: 'post',
        url: '/products/reviews',
        headers: {
            'X-CSRF-TOKEN': csrfToken,
            'Content-Type': 'application/json'
        },
        data: data
    }).then(function (response){
        console.log(response);
    }).catch(function (error){
        console.log(error);
    })
}

