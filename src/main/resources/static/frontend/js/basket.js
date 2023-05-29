const csrfToken = document.querySelector('meta[name="_csrf_token"]').getAttribute('content');

document.addEventListener('DOMContentLoaded', function() {
    const removeButtons = document.querySelectorAll('.remove');
    removeButtons.forEach(function (button){
        button.addEventListener('click', function (e){
            e.preventDefault();
            const form = e.target.parentElement.parentElement;
            const basketProductId = form.className.substring(16);
            axios({
                method: 'post',
                url: '/basket/' + basketProductId,
                headers: {
                    'X-CSRF-TOKEN': csrfToken,
                    'X-HTTP-Method-Override': 'DELETE'
                },
                data: {}
            })
                .then(function (response){
                    console.log(response.data);
                    form.remove();
                }).catch(function (error){
                console.log(error);
            });
        })
    })

    const changeQuantityButtons = document.querySelectorAll('.changeQuantityForm');
    changeQuantityButtons.forEach(function (form){
        form.addEventListener('submit', function (e){
            e.preventDefault();
            const form = e.target;
            const parentElem = e.target.parentElement;
            const data = new FormData(form);
            const basketProductId = parentElem.parentElement.className.substring(16);
            const quantity = data.get('quantity');
            const previousQuantity = parentElem.getElementsByClassName('card-text')[0].innerText;
            const parts = previousQuantity.split(":");
            const value = parts[1].trim();
            const changeProductQuantityDTO = {
                productName: parentElem.getElementsByClassName('card-title')[0].innerText,
                basketProductId: basketProductId,
                previousQuantity: value,
                currentQuantity: quantity
            };
            axios({
                method: 'post',
                url: '/basket/quantity',
                headers: {
                    'X-CSRF-TOKEN': csrfToken,
                },
                data: changeProductQuantityDTO
            })
                .then(function (response){
                    console.log(response.data);
                    parentElem.getElementsByClassName('card-text')[0].innerText = 'Quantity: ' + response.data.productQuantity;
                })
                .catch(function (error){
                    console.log(error);
                });
        })
    })

    const checkoutButtons = document.querySelectorAll('.checkout');
    checkoutButtons.forEach(function (checkoutBtn){
        checkoutBtn.addEventListener('click', function (e){
            e.preventDefault();
            const card = e.target.parentElement.parentElement;
            const basketProductId = card.className.substring(16);
            axios({
                method: 'post',
                url: '/orders/' + basketProductId,
                headers: {
                    'X-CSRF-TOKEN': csrfToken,
                },
                data: {}
            }).then(function (response){
                console.log(response);
                card.remove();
            }).catch(function (error){
                console.log(error);
            })
        })
    })
})

document.getElementById('navbar-brand').addEventListener('click', function (){
    window.location.href = '/';
})

