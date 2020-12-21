'use strict'
window.onload = function () {
    console.log('Script loaded');

    //Give all buttons an event listener
    var fruitBtn = document.getElementById("fruit");
    fruitBtn.addEventListener('click', function (event) {
        event.preventDefault();
        showFruit();
    });

    var vegetablesBtn = document.getElementById("vegetables");
    vegetablesBtn.addEventListener('click', function (event) {
        event.preventDefault();
        showVeg();
    });
    var meatBtn = document.getElementById("meat");
    meatBtn.addEventListener('click', function (event) {
        event.preventDefault();
        showDrink();
    });
    var carbsBtn = document.getElementById("carbs");
    carbsBtn.addEventListener('click', function (event) {
        event.preventDefault();
        showPasta();
    });

    var orgClass = "px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3";
    var clickedClass = "px-6 py-3 bg-yellow-600 rounded-md text-white font-medium tracking-wide hover:bg-yellow-500 ml-3";

    var listFruit = document.getElementById('ListFruit');
    var listVeg = document.getElementById('ListVeg');
    var listMeat = document.getElementById('ListMeat');
    var listCarbs = document.getElementById('ListCarbs');



    function showFruit() {
        hideAll();
        fruitBtn.className = clickedClass;
        listFruit.style.display = "block";
    }

    function showVeg() {
        hideAll();
        vegetablesBtn.className = clickedClass;
        listVeg.style.display = "block";
    }

    function showDrink() {
        hideAll();
        meatBtn.className = clickedClass;
        listMeat.style.display = "block";
    }

    function showPasta() {
        hideAll();
        carbsBtn.className = clickedClass;
        listCarbs.style.display = "block";
    }

    function hideAll() {
        fruitBtn.className = orgClass;
        vegetablesBtn.className = orgClass;
        meatBtn.className = orgClass;
        carbsBtn.className = orgClass;
        listFruit.style.display = "none";
        listVeg.style.display = "none";
        listMeat.style.display = "none";
        listCarbs.style.display = "none";
    }



    //New Category
    var input = document.getElementById('newCat');
    var selection = document.getElementById('category');
    selection.addEventListener('change', function (event) {
        event.preventDefault();
        if (selection.value == 'Other') {
            input.style.display = "block";
        } else {
            input.style.display = "none";
        }
    });

    let checkbox = document.getElementsByClassName('checkBoxIngredient');

    for (let i = 0; i < checkbox.length; i++) {
        let id = checkbox[i].value;
        let name = checkbox[i].nextElementSibling.innerHTML;

        checkbox[i].addEventListener('change', function (event) {
            event.preventDefault();
            if (event.target.checked) {
                let html = `<div class="md:w-full px-3" id="${name}"> <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2" for="quantity${id}">${name}</label>`
                html += `<input class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-grey-lighter rounded py-3 px-4 mb-3" id="quantity${id}" name="inputQuantity${id}" type="number" placeholder="1"></div>`
                document.getElementById('manyIngredients').innerHTML += html;
            } else {
                if (document.getElementById(name)) {
                    document.getElementById(name).remove();
                }
            }
        })

        check(checkbox[i], id, name);
    }

    async function check(event, id, name) {
        if (event.checked) {
            var quantity = await getAmount(id);
            let html = `<div class="md:w-full px-3" id="${name}"> <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2" for="quantity${id}">${name}</label>`
            html += `<input value="${quantity}"  class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-grey-lighter rounded py-3 px-4 mb-3" id="quantity${id}" name="inputQuantity${id}" type="number" placeholder="1"></div>`
            document.getElementById('manyIngredients').innerHTML += html;
        } else {
            if (document.getElementById(name)) {
                document.getElementById(name).remove();
            }
        }
    }
    async function getAmount(ingredientId) {
        let productId = document.getElementById('productId').value;

        let response = await fetch(`http://10.3.50.23:69/products/${productId}`);
        let data = await response.json();
        let ingredients = data.ingredients;
        let amount = 0;
        for (const ingredient of ingredients) {
            if (ingredient.id == ingredientId) {
                amount = ingredient.amount;
                break;
            }
        }
        return amount;
    }

    //MODAL
    document.onkeydown = function (evt) {
        evt = evt || window.event
        var isEscape = false
        if ("key" in evt) {
            isEscape = (evt.key === "Escape" || evt.key === "Esc")
        } else {
            isEscape = (evt.keyCode === 27)
        }
        if (isEscape && document.body.classList.contains('createModal-active')) {
            toggleCreateModal()
        }
        if (isEscape && document.body.classList.contains('allergyModal-active')) {
            toggleCategoryModal()
        }
    };

    //MODAL Allergy
    document.getElementById('allergyModal-open').addEventListener('click', function (event) {
        event.preventDefault();
        toggleCategoryModal()
    })

    document.getElementById('allergyModal-overlay').addEventListener('click', toggleCategoryModal)
    
    var closeCategoryModal = document.getElementsByClassName('allergyModal-close')
    for (var i = 0; i < closeCategoryModal.length; i++) {
        closeCategoryModal[i].addEventListener('click', toggleCategoryModal)
    }

    function toggleCategoryModal() {
        const modal = document.getElementById('allergyModal')
        modal.classList.toggle('opacity-0')
        modal.classList.toggle('pointer-events-none')
        document.body.classList.toggle('allergyModal-active')
    }

    //MODAL createModal
    
    document.getElementById('createModal-open').addEventListener('click', function (event) {
        event.preventDefault();
        toggleCreateModal()
    })

    document.getElementById('createModal-overlay').addEventListener('click', toggleCreateModal)

    var closeCreateModal = document.getElementsByClassName('createModal-close')
    for (var i = 0; i < closeCreateModal.length; i++) {
        closeCreateModal[i].addEventListener('click', toggleCreateModal)
    }

    function toggleCreateModal() {
        const modal = document.getElementById('createModal')
        modal.classList.toggle('opacity-0')
        modal.classList.toggle('pointer-events-none')
        document.body.classList.toggle('createModal-active')
    }
    
}