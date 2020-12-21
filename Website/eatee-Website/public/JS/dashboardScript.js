'use strict'
window.onload = function () {
    console.log('Dashboard Script loaded');

    var promotionsBtn = document.getElementById('promotionsBtn');
    promotionsBtn.addEventListener('click', showPromotions);

    var categoriesBtn = document.getElementById('categoriesBtn');
    categoriesBtn.addEventListener('click', showCategories);

    var allergiesBtn = document.getElementById('allergiesBtn');
    allergiesBtn.addEventListener('click', showAllergies);

    var allBtn = document.getElementById('allBtn');
    allBtn.addEventListener('click', showAll);

    var promotionsTable = document.getElementById('promotionsTable');
    var categoriesTable = document.getElementById('categoriesTable');
    var allergiesTable = document.getElementById('allergiesTable');


    var orgClass = "px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3";
    var clickedClass = "px-6 py-3 bg-yellow-600 rounded-md text-white font-medium tracking-wide hover:bg-yellow-500 ml-3";

    showAll();

    function showAll() {
        hideAll();
        allBtn.classList = clickedClass;
        promotionsTable.style.display = "block";
        categoriesTable.style.display = "block";
        allergiesTable.style.display = "block";
    }

    function hideAll() {
        promotionsBtn.classList = orgClass;
        categoriesBtn.classList = orgClass;
        allergiesBtn.classList = orgClass;
        allBtn.classList = orgClass;

        promotionsTable.style.display = "none";
        categoriesTable.style.display = "none";
        allergiesTable.style.display = "none";
    }

    function showPromotions() {
        hideAll();
        promotionsBtn.classList = clickedClass;
        promotionsTable.style.display = "block";
    }

    function showCategories() {
        hideAll();
        categoriesBtn.classList = clickedClass;
        categoriesTable.style.display = "block";
    }

    function showAllergies() {
        hideAll();
        allergiesBtn.classList = clickedClass;
        allergiesTable.style.display = "block";
    }




    //MODALS
    document.onkeydown = function (evt) {
        evt = evt || window.event
        var isEscape = false
        if ("key" in evt) {
            isEscape = (evt.key === "Escape" || evt.key === "Esc")
        } else {
            isEscape = (evt.keyCode === 27)
        }
        if (isEscape && document.body.classList.contains('createAllergyModal-active')) {
            toggleCreateAllergyModal()
        }
        if (isEscape && document.body.classList.contains('createCategoryModal-active')) {
            toggleCreateCategoryModal()
        }
        if (isEscape && document.body.classList.contains('deleteAllergyModal-active')) {
            toggleDeleteAllergyModal()
        }
        if (isEscape && document.body.classList.contains('deleteCategoryModal-active')) {
            toggleDeleteCategoryModal()
        }
        if (isEscape && document.body.classList.contains('deletePromotionModal-active')) {
            toggleDeletePromotionModal()
        }
        if (isEscape && document.body.classList.contains('deletePromotionModal-active')) {
            toggleCreatePromotionModal()
        }
        if (isEscape && document.body.classList.contains('editCategoriesAllergiesModal-active')) {
            toggleEditCategoriesAllergiesModal()
        }
        if (isEscape && document.body.classList.contains('editPromotionModal-active')) {
            toggleEditPromotionsModal()
        }
    };

    //create allergy
    document.getElementById('createAllergyModal-open').addEventListener('click', toggleCreateAllergyModal);
    document.getElementById('createAllergyModal-overlay').addEventListener('click', toggleCreateAllergyModal)

    var closeCreateAllergyModal = document.getElementsByClassName('createAllergyModal-close')
    for (var i = 0; i < closeCreateAllergyModal.length; i++) {
        closeCreateAllergyModal[i].addEventListener('click', toggleCreateAllergyModal)
    }

    function toggleCreateAllergyModal() {
        const modal = document.getElementById('createAllergyModal')
        modal.classList.toggle('opacity-0')
        modal.classList.toggle('pointer-events-none')
        document.querySelector('body').classList.toggle('createAllergyModal-active')
    }

    //create category
    document.getElementById('createCategoryModal-open').addEventListener('click', toggleCreateCategoryModal);
    document.getElementById('createCategoryModal-overlay').addEventListener('click', toggleCreateCategoryModal)

    var closeCreateCategoryModal = document.getElementsByClassName('createCategoryModal-close')
    for (var i = 0; i < closeCreateCategoryModal.length; i++) {
        closeCreateCategoryModal[i].addEventListener('click', toggleCreateCategoryModal)
    }

    function toggleCreateCategoryModal() {
        const modal = document.getElementById('createCategoryModal')
        modal.classList.toggle('opacity-0')
        modal.classList.toggle('pointer-events-none')
        document.querySelector('body').classList.toggle('createCategoryModal-active')
    }


    //Delete category
    var openDeleteCategoryModal = document.getElementsByClassName('deleteCategoryModal-open');
    for (let i = 0; i < openDeleteCategoryModal.length; i++) {
        openDeleteCategoryModal[i].addEventListener('click', function (event) {
            event.preventDefault();
            let id = openDeleteCategoryModal[i].parentElement.parentElement.firstElementChild.firstElementChild.firstElementChild.firstElementChild.innerHTML; //GET ID

            document.getElementById('categoryId').value = id;
            if (isNull(openDeleteCategoryModal[i])) {
                toggleDeleteCategoryModal();
            }
        })
    }

    document.getElementById('deleteCategoryModal-overlay').addEventListener('click', toggleDeleteCategoryModal)

    var closeDeleteCategoryModal = document.getElementsByClassName('deleteCategoryModal-close')
    for (var i = 0; i < closeDeleteCategoryModal.length; i++) {
        closeDeleteCategoryModal[i].addEventListener('click', toggleDeleteCategoryModal)
    }

    function toggleDeleteCategoryModal() {
        const modal = document.getElementById('deleteCategoryModal')
        modal.classList.toggle('opacity-0')
        modal.classList.toggle('pointer-events-none')
        document.querySelector('body').classList.toggle('deleteCategoryModal-active')
    }


    //delete allergy
    var openDeleteAllergyModal = document.getElementsByClassName('deleteAllergyModal-open');
    for (let i = 0; i < openDeleteAllergyModal.length; i++) {
        openDeleteAllergyModal[i].addEventListener('click', function (event) {
            event.preventDefault();
            let id = openDeleteAllergyModal[i].parentElement.parentElement.firstElementChild.firstElementChild.firstElementChild.firstElementChild.innerHTML; //GET ID

            document.getElementById('allergyId').value = id;

            if (isNull(openDeleteAllergyModal[i])) {
                toggleDeleteAllergyModal();
            }
        })
    }

    document.getElementById('deleteAllergyModal-overlay').addEventListener('click', toggleDeleteAllergyModal)

    var closeDeleteAllergyModal = document.getElementsByClassName('deleteAllergyModal-close')
    for (var i = 0; i < closeDeleteAllergyModal.length; i++) {
        closeDeleteAllergyModal[i].addEventListener('click', toggleDeleteAllergyModal)
    }

    function toggleDeleteAllergyModal() {
        const modal = document.getElementById('deleteAllergyModal')
        modal.classList.toggle('opacity-0')
        modal.classList.toggle('pointer-events-none')
        document.querySelector('body').classList.toggle('deleteAllergyModal-active')
    }

    //delete Promotion
    var openDeletePromotionModal = document.getElementsByClassName('deletePromotionModal-open');
    for (let i = 0; i < openDeletePromotionModal.length; i++) {
        openDeletePromotionModal[i].addEventListener('click', function (event) {
            event.preventDefault();
            let id = openDeletePromotionModal[i].parentElement.parentElement.firstElementChild.firstElementChild.firstElementChild.firstElementChild.innerHTML; //GET ID

            document.getElementById('promotionId').value = id;
            toggleDeletePromotionModal();
        })
    }

    document.getElementById('deletePromotionModal-overlay').addEventListener('click', toggleDeletePromotionModal)

    var closeDeletePromotionModal = document.getElementsByClassName('deletePromotionModal-close')
    for (var i = 0; i < closeDeletePromotionModal.length; i++) {
        closeDeletePromotionModal[i].addEventListener('click', toggleDeletePromotionModal)
    }

    function toggleDeletePromotionModal() {
        const modal = document.getElementById('deletePromotionModal')
        modal.classList.toggle('opacity-0')
        modal.classList.toggle('pointer-events-none')
        document.querySelector('body').classList.toggle('deletePromotionModal-active')
    }




    //Create Promotion
    document.getElementById('createPromotionModal-open').addEventListener('click', async function (event) {
        event.preventDefault();
        var html = '<select id="inputProduct" name="productId" class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-grey-lighter rounded py-3 px-4 mb-3">';
        var products = await getProducts();

        products.forEach(product => {
            if (product.promotionActive == 0) {
                html += `<option value="${product.productId}">${product.name}</option>`;
            }
        });
        html += '</select>';

        var location = document.getElementById('body');
        console.log(location);
        location.innerHTML = html;
        toggleCreatePromotionModal();
    })




    document.getElementById('createPromotionModal-overlay').addEventListener('click', toggleCreatePromotionModal)

    var closeDeletePromotionModal = document.getElementsByClassName('createPromotionModal-close')
    for (var i = 0; i < closeDeletePromotionModal.length; i++) {
        closeDeletePromotionModal[i].addEventListener('click', toggleCreatePromotionModal)
    }

    function toggleCreatePromotionModal() {
        const modal = document.getElementById('createPromotionModal')
        modal.classList.toggle('opacity-0')
        modal.classList.toggle('pointer-events-none')
        document.querySelector('body').classList.toggle('createPromotionModal-active')
    }

    async function getProducts() {
        let response = await fetch(`http://10.3.50.23:69/products/`);
        let data = await response.json();

        return data;
    }



    //Edit Categories + Allergies
    var openEditCategoriesAllergiesModal = document.getElementsByClassName('editCategoriesAllergiesModal-open');
    for (let i = 0; i < openEditCategoriesAllergiesModal.length; i++) {
        openEditCategoriesAllergiesModal[i].addEventListener('click', function (event) {
            event.preventDefault();
            let id = openEditCategoriesAllergiesModal[i].parentElement.parentElement.firstElementChild.firstElementChild.firstElementChild.firstElementChild.innerHTML; //GET ID
            let type = openEditCategoriesAllergiesModal[i].parentElement.parentElement.parentElement.parentElement.parentElement.parentElement.firstElementChild.innerHTML;
            let name = openEditCategoriesAllergiesModal[i].parentElement.parentElement.firstElementChild.nextElementSibling.firstElementChild.innerHTML;

            document.getElementById('inputFieldEditModal').value = id;
            document.getElementById('inputType').value = type;
            document.getElementById('name').placeholder = name;

            document.getElementById('title').innerHTML = `Edit ${type}: ${name}`;
            toggleEditCategoriesAllergiesModal();
        })
    }

    document.getElementById('editCategoriesAllergiesModal-overlay').addEventListener('click', toggleEditCategoriesAllergiesModal)

    var closeEditCategoriesAllergiesModal = document.getElementsByClassName('editCategoriesAllergiesModal-close')
    for (var i = 0; i < closeEditCategoriesAllergiesModal.length; i++) {
        closeEditCategoriesAllergiesModal[i].addEventListener('click', toggleEditCategoriesAllergiesModal)
    }

    function toggleEditCategoriesAllergiesModal() {
        const modal = document.getElementById('editCategoriesAllergiesModal')
        modal.classList.toggle('opacity-0')
        modal.classList.toggle('pointer-events-none')
        document.querySelector('body').classList.toggle('editCategoriesAllergiesModal-active')
    }

    //Edit Promotions
    var openEditPromotionsModal = document.getElementsByClassName('editPromotionModal-open');
    for (let i = 0; i < openEditPromotionsModal.length; i++) {
        openEditPromotionsModal[i].addEventListener('click', function (event) {
            event.preventDefault();
            let id = openEditPromotionsModal[i].parentElement.parentElement.firstElementChild.firstElementChild.firstElementChild.firstElementChild.innerHTML; //GET ID

            let amount = openEditPromotionsModal[i].parentElement.previousElementSibling.previousElementSibling.previousElementSibling.previousElementSibling.firstElementChild.innerHTML;
            let start = openEditPromotionsModal[i].parentElement.previousElementSibling.previousElementSibling.firstElementChild.innerHTML;
            let end = openEditPromotionsModal[i].parentElement.previousElementSibling.firstElementChild.innerHTML;
            let name = openEditPromotionsModal[i].parentElement.parentElement.firstElementChild.nextElementSibling.nextElementSibling.firstElementChild.innerHTML;


            document.getElementById('inputAmount2').value = amount;
            document.getElementById('inputEndDate2').value = end;
            document.getElementById('inputStartDate2').value = start;

            document.getElementById('promotionId2').value = id;

            document.getElementById('titleEdit').innerHTML = `Edit Promotion: ${name}`;

            toggleEditPromotionsModal();
        })
    }

    document.getElementById('editPromotionModal-overlay').addEventListener('click', toggleEditPromotionsModal)

    var closeEditPromotionsModal = document.getElementsByClassName('editPromotionModal-close')
    for (var i = 0; i < closeEditPromotionsModal.length; i++) {
        closeEditPromotionsModal[i].addEventListener('click', toggleEditPromotionsModal)
    }

    function toggleEditPromotionsModal() {
        const modal = document.getElementById('editPromotionModal')
        modal.classList.toggle('opacity-0')
        modal.classList.toggle('pointer-events-none')
        document.querySelector('body').classList.toggle('editPromotionModal-active')
    }




    function isNull(button) {
        var amount = button.parentElement.previousElementSibling.firstElementChild.innerHTML;
        amount = amount.split(' ').join('');
        if (amount != 0) {
            button.innerHTML = 'Unable to delete - need to delete all uses';
            return false
        } else {
            return true
        }
    }
}
