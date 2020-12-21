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
        checkbox[i].addEventListener('change', function (event) {
            event.preventDefault();
            let id = checkbox[i].value;
            let name = checkbox[i].nextElementSibling.innerHTML;
            console.log(id)

            if (event.target.checked) {
                let html = `<div class="md:w-full px-3" id="${name}"> <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2" for="price">${name}</label>`
                html += `<input class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-grey-lighter rounded py-3 px-4 mb-3" id="price" name="inputQuantity${id}" type="number" placeholder="1"></div>`
                document.getElementById('manyIngredients').innerHTML += html;
            } else {
                document.getElementById(name).remove();
            }
        })
    }


    //MODAL
    var openmodal = document.getElementsByClassName('modal-open')
    for (var i = 0; i < openmodal.length; i++) {
        openmodal[i].addEventListener('click', function (event) {
            event.preventDefault();
            toggleModal()
        })
    }

    const overlay = document.querySelector('.modal-overlay')
    overlay.addEventListener('click', toggleModal)

    var closemodal = document.querySelectorAll('.modal-close')
    for (var i = 0; i < closemodal.length; i++) {
        closemodal[i].addEventListener('click', toggleModal)
    }

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
    };


    function toggleModal() {
        const body = document.querySelector('body')
        const modal = document.querySelector('.modal')
        modal.classList.toggle('opacity-0')
        modal.classList.toggle('pointer-events-none')
        body.classList.toggle('modal-active')
    }
}
