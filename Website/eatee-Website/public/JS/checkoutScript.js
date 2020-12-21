'use strict'
window.onload = function () {
    console.log('Script loaded');


    var openmodal = document.getElementsByClassName('modal-open')
    for (let i = 0; i < openmodal.length; i++) {
        openmodal[i].addEventListener('click', async function (event) {
            event.preventDefault();
            var id = openmodal[i].parentElement.parentElement.firstElementChild.firstElementChild.firstElementChild.firstElementChild.innerHTML;

            //DO API CALL TO GET DATA FROM THIS ORDER
            var orderDetails = await getOrder(id);
            var clientDetails = await getClient(orderDetails.customerId);


            document.getElementById('inputfield').value = id;
            var html = "<p><b>Client:</b> " + clientDetails.firstname + ' ' + clientDetails.lastname + "<br><br>";
            html += "<b>Products: </b> <br>";
            for (var y = 0; y < orderDetails.productIds.length; y++) {
                var productDetails = await getProduct(orderDetails.productIds[y]);

                html += "1x " + productDetails.name + " <br> ";
            }
            for (var y = 0; y < orderDetails.sandwichIds.length; y++) {
                var sandwichDetails = await getProduct(orderDetails.sandwichIds[y]);
                html += "1x Custom sandwish => ";
                for (var x = 0; x < sandwichDetails.ingredients; x++) {
                    var ingredientDetail = await getIngredient(sandwichDetails.ingredients[x]);
                    html += " " + ingredientDetail.name + " ";
                }
                html += " <br> ";
            }
            html += "<br><br> <b>Price: </b>" + orderDetails.total + "<br><br>";
            html += "<b>Order Status: </b>" + orderDetails.status + "</p>";

            document.getElementById('bodyModal').innerHTML = html;

            document.getElementById('idModal').innerHTML = 'id: ' + id;
            toggleModal()
        })
    }

    const overlay = document.querySelector('.modal-overlay')
    overlay.addEventListener('click', toggleModal)

    var closemodal = document.querySelectorAll('.modal-close')
    for (var i = 0; i < closemodal.length; i++) {
        closemodal[i].addEventListener('click', toggleModal)
    }

    function toggleModal() {
        const body = document.querySelector('body')
        const modal = document.querySelector('.modal')
        modal.classList.toggle('opacity-0')
        modal.classList.toggle('pointer-events-none')
        body.classList.toggle('modal-active')
    }


    //MODAL ChangeDate
    document.getElementById('changeModal-open').addEventListener('click', toggleChangeModal)
    document.getElementById('changeModal-overlay').addEventListener('click', toggleChangeModal)

    var closeChangeModal = document.getElementsByClassName('changeModal-close')
    for (var i = 0; i < closeChangeModal.length; i++) {
        closeChangeModal[i].addEventListener('click', toggleChangeModal)
    }

    function toggleChangeModal() {
        const modal = document.getElementById('changeModal')
        modal.classList.toggle('opacity-0')
        modal.classList.toggle('pointer-events-none')
        document.body.classList.toggle('changeModal-active')
    }

    //CLOSE MODALS
    document.onkeydown = function (evt) {
        evt = evt || window.event
        var isEscape = false
        if ("key" in evt) {
            isEscape = (evt.key === "Escape" || evt.key === "Esc")
        } else {
            isEscape = (evt.keyCode === 27)
        }
        if (isEscape && document.body.classList.contains('modal-active')) {
            toggleModal()
        }
        if (isEscape && document.body.classList.contains('changeModal-active')) {
            toggleChangeModal
        }
    };
}

async function getOrder(orderId) {
    let response = await fetch(`http://10.3.50.23:69/orders/${orderId}`);
    let data = await response.json();

    return data;
}
async function getClient(customerId) {
    let response = await fetch(`http://10.3.50.23:69/customers/${customerId}`);
    let data = await response.json();

    return data;
}

async function getProduct(productId) {
    let response = await fetch(`http://10.3.50.23:69/products/${productId}`);
    let data = await response.json();

    return data;
}
async function getSandwish(sandwishId) {
    let response = await fetch(`http://10.3.50.23:69/custom/${sandwishId}`);
    let data = await response.json();

    return data;
}

async function getIngredient(ingredientId) {
    let response = await fetch(`http://10.3.50.23:69/ingredients/${ingredientId}`);
    let data = await response.json();

    return data;
}
