'use strict'
window.onload = function () {
    console.log('Script loaded');


    //Give all buttons an event listener
    var fruitBtn = document.getElementById("fruit");
    fruitBtn.addEventListener('click', showFruit);

    var vegetablesBtn = document.getElementById("vegetables");
    vegetablesBtn.addEventListener('click', showVeg);

    var meatBtn = document.getElementById("meat");
    meatBtn.addEventListener('click', showMeat);

    var carbsBtn = document.getElementById("carbs");
    carbsBtn.addEventListener('click', showCarbs);

    var allBtn = document.getElementById('all');
    allBtn.addEventListener('click', showAll);

    var productsBtn = document.getElementById('products');
    productsBtn.addEventListener('click', showProducts);

    var sandwishIngredientsBtn = document.getElementById('pIngredients');
    sandwishIngredientsBtn.addEventListener('click', showPIngredients);

    var ingredientsBtn = document.getElementById('ingedients');
    ingredientsBtn.addEventListener('click', showIngredients);

    var orgClass = "px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3";
    var clickedClass = "px-6 py-3 bg-yellow-600 rounded-md text-white font-medium tracking-wide hover:bg-yellow-500 ml-3";



    var vegetableRows = document.getElementsByClassName('vegetableRows');
    var fruitRows = document.getElementsByClassName('fruitRows');
    var meatRows = document.getElementsByClassName('meatRows');
    var carbsRows = document.getElementsByClassName('carbsRows');
    var productRows = document.getElementById('productRows');
    var sandwishRows = document.getElementsByClassName('sandwish');
    var ingredientsRows = document.getElementById('ingredientsRows');

    function showFruit() {
        hideAll();
        ingredientsRows.style.display = "block";

        fruitBtn.className = clickedClass;
        for (var i = 0; i < fruitRows.length; i++) {
            fruitRows[i].style.visibility = "visible";
        }
    }

    function showVeg() {
        hideAll();
        ingredientsRows.style.display = "block";

        vegetablesBtn.className = clickedClass;
        for (var i = 0; i < vegetableRows.length; i++) {
            vegetableRows[i].style.visibility = "visible";
        }
    }

    function showMeat() {
        hideAll();
        ingredientsRows.style.display = "block";

        meatBtn.className = clickedClass;
        for (var i = 0; i < meatRows.length; i++) {
            meatRows[i].style.visibility = "visible";
        }
    }

    function showCarbs() {
        hideAll();
        ingredientsRows.style.display = "block";

        carbsBtn.className = clickedClass;
        for (var i = 0; i < carbsRows.length; i++) {
            carbsRows[i].style.visibility = "visible";
        }
    }
    function showProducts() {
        hideAll();
        productsBtn.className = clickedClass;
        productRows.style.display = "block";
    }

    function showIngredients() {
        hideAll();
        
        ingredientsBtn.className = clickedClass;
        ingredientsRows.style.display = "block";
        for (var i = 0; i < sandwishRows.length; i++) {
            sandwishRows[i].style.visibility = "visible";
        }
        for (var i = 0; i < carbsRows.length; i++) {
            carbsRows[i].style.visibility = "visible";
        }
        for (var i = 0; i < meatRows.length; i++) {
            meatRows[i].style.visibility = "visible";
        }
        for (var i = 0; i < vegetableRows.length; i++) {
            vegetableRows[i].style.visibility = "visible";
        }
        for (var i = 0; i < fruitRows.length; i++) {
            fruitRows[i].style.visibility = "visible";
        }
    }

    function showPIngredients() {
        hideAll();
        ingredientsRows.style.display = "block";

        sandwishIngredientsBtn.className = clickedClass;
        for (var i = 0; i < sandwishRows.length; i++) {
            sandwishRows[i].style.visibility = "visible";
        }
    }

    function hideAll() {
        fruitBtn.className = orgClass;
        vegetablesBtn.className = orgClass;
        meatBtn.className = orgClass;
        carbsBtn.className = orgClass;
        productsBtn.className = orgClass;
        allBtn.className = orgClass;
        sandwishIngredientsBtn.className = orgClass;
        ingredientsBtn.className = orgClass;


        for (var i = 0; i < fruitRows.length; i++) {
            fruitRows[i].style.visibility = "collapse";
        }
        for (var i = 0; i < vegetableRows.length; i++) {
            vegetableRows[i].style.visibility = "collapse";
        }
        for (var i = 0; i < meatRows.length; i++) {
            meatRows[i].style.visibility = "collapse";
        }
        for (var i = 0; i < carbsRows.length; i++) {
            carbsRows[i].style.visibility = "collapse";
        }
        productRows.style.display = "none";
        ingredientsRows.style.display = "none";

        for (var i = 0; i < sandwishIngredientsBtn.length; i++) {
            sandwishIngredientsBtn[i].style.visibility = "collapse";
        }
    }


    showAll();

    function showAll() {
        hideAll();
        allBtn.className = clickedClass;
        for (var i = 0; i < fruitRows.length; i++) {
            fruitRows[i].style.visibility = "visible";
        }
        for (var i = 0; i < vegetableRows.length; i++) {
            vegetableRows[i].style.visibility = "visible";
        }
        for (var i = 0; i < meatRows.length; i++) {
            meatRows[i].style.visibility = "visible";
        }
        for (var i = 0; i < carbsRows.length; i++) {
            carbsRows[i].style.visibility = "visible";
        }
        productRows.style.display = "block";
        ingredientsRows.style.display = "block";


        for (var i = 0; i < sandwishIngredientsBtn.length; i++) {
            sandwishIngredientsBtn[i].style.visibility = "visible";
        }
    }


    //MODAL 
    var openAddModal = document.getElementsByClassName('addModal-open')
    for (let i = 0; i < openAddModal.length; i++) {
        openAddModal[i].addEventListener('click', function (event) {
            event.preventDefault();

            var inputFieldId = document.getElementById('inputFieldId');
            var id = openAddModal[i].parentElement.parentElement.firstElementChild.firstElementChild.firstElementChild.firstElementChild.innerHTML;

            inputFieldId.value = id;
            toggleAddModal()
        })
    }

    const overlayAddModal = document.querySelector('.addModal-overlay')
    overlayAddModal.addEventListener('click', toggleAddModal)

    var closeAddModal = document.querySelectorAll('.addModal-close')
    for (var i = 0; i < closeAddModal.length; i++) {
        closeAddModal[i].addEventListener('click', toggleAddModal)
    }

    function toggleAddModal() {
        const body = document.querySelector('body')
        const modal = document.querySelector('.addModal')
        modal.classList.toggle('opacity-0')
        modal.classList.toggle('pointer-events-none')
        body.classList.toggle('addModal-active')
    }


    //MODAL 2
    var openRemoveModal = document.getElementsByClassName('removeModal-open')
    for (let i = 0; i < openRemoveModal.length; i++) {
        openRemoveModal[i].addEventListener('click', function (event) {
            event.preventDefault();

            var inputFieldId2 = document.getElementById('inputFieldId2');
            var id = openRemoveModal[i].parentElement.parentElement.firstElementChild.firstElementChild.firstElementChild.firstElementChild.innerHTML;
            inputFieldId2.value = id;
            toggleRemoveModal()
        })
    }

    const overlayRemoveModal = document.querySelector('.removeModal-overlay')
    overlayRemoveModal.addEventListener('click', toggleRemoveModal)

    var closeRemoveModal = document.querySelectorAll('.removeModal-close')
    for (var i = 0; i < closeRemoveModal.length; i++) {
        closeRemoveModal[i].addEventListener('click', toggleRemoveModal)
    }

    function toggleRemoveModal() {
        const body = document.querySelector('body')
        const modal = document.querySelector('.removeModal')
        modal.classList.toggle('opacity-0')
        modal.classList.toggle('pointer-events-none')
        body.classList.toggle('removeModal-active')
    }


    //MODAL 3
    var openEditModal = document.getElementsByClassName('editModal-open')
    for (let i = 0; i < openEditModal.length; i++) {
        openEditModal[i].addEventListener('click', async function (event) {
            event.preventDefault();

            var inputFieldId3 = document.getElementById('inputFieldId3');
            var inputFieldType3 = document.getElementById('inputFieldType3');
            var id = openEditModal[i].parentElement.parentElement.firstElementChild.firstElementChild.firstElementChild.firstElementChild.innerHTML;
            var type = openEditModal[i].parentElement.previousElementSibling.firstElementChild.innerHTML;

            var body = document.getElementById('bodyModal');

            type = type.split(" ");
            inputFieldId3.value = id;
            document.getElementById('name').value = openEditModal[i].parentElement.parentElement.firstElementChild.nextElementSibling.firstElementChild.innerHTML;

            if (type[0] == "Product") {
                document.getElementById('title').innerHTML = 'Edit Product';
                inputFieldType3.value = "product";
                let categories = await getCategories();
                var tempString = '<select class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-grey-lighter rounded py-3 px-4 mb-3" name="inputType" id="type">';

                for (const category of categories) {
                    if (category.id != 4 && category.id != 5 && category.id != 6 && category.id != 9) {
                        tempString = tempString + `<option value="${category.id}">${category.name}</option>`;
                    }
                }
                body.innerHTML = tempString;

            } else {
                document.getElementById('title').innerHTML = 'Edit Ingredient';
                inputFieldType3.value = "ingredient";
                var tempString = '<select class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-grey-lighter rounded py-3 px-4 mb-3" name="inputType" id="type">';
                tempString = tempString + '<option value="5">Fruit</option>'
                tempString = tempString + '<option value="4">Vegetable</option>'
                tempString = tempString + '<option value="6">Meat</option>'
                tempString = tempString + '<option value="9">Other</option>'
                body.innerHTML = tempString;
            }
            toggleEditModal()
        })
    }
    const overlayEditModal = document.querySelector('.editModal-overlay')
    overlayEditModal.addEventListener('click', toggleEditModal)

    var closeEditModal = document.querySelectorAll('.editModal-close')
    for (var i = 0; i < closeEditModal.length; i++) {
        closeEditModal[i].addEventListener('click', toggleEditModal)
    }


    function toggleEditModal() {
        const body = document.querySelector('body')
        const modal = document.querySelector('.editModal')
        modal.classList.toggle('opacity-0')
        modal.classList.toggle('pointer-events-none')
        body.classList.toggle('editModal-active')
    }

    async function getCategories() {
        let response = await fetch('http://10.3.50.23:69/categories/');
        let data = await response.json();
        return data;
    }

    document.onkeydown = function (evt) {
        evt = evt || window.event
        var isEscape = false
        if ("key" in evt) {
            isEscape = (evt.key === "Escape" || evt.key === "Esc")
        } else {
            isEscape = (evt.keyCode === 27)
        }
        if (isEscape && document.body.classList.contains('addModal-active')) {
            toggleAddModal()
        }
        if (isEscape && document.body.classList.contains('removeModal-active')) {
            toggleRemoveModal()
        }
        if (isEscape && document.body.classList.contains('editModal-active')) {
            toggleEditModal()
        }
    };

}
