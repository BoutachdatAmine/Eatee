'use strict'
window.onload = function () {
    console.log('Script loaded');

    //MODAL delete
    var openDeleteModal = document.getElementsByClassName('deleteModal-open')
    for (let i = 0; i < openDeleteModal.length; i++) {
        openDeleteModal[i].addEventListener('click', function (event) {
            event.preventDefault()
            var id = openDeleteModal[i].parentElement.parentElement.firstElementChild.firstElementChild.innerHTML;
            document.getElementById('menuIdField1').value = id;
            toggleDeleteModal()
        })
    }

    const overlayDeleteModal = document.querySelector('.deleteModal-overlay')
    overlayDeleteModal.addEventListener('click', toggleDeleteModal)

    var closeDeleteModal = document.querySelectorAll('.deleteModal-close')
    for (var i = 0; i < closeDeleteModal.length; i++) {
        closeDeleteModal[i].addEventListener('click', toggleDeleteModal)
    }

    document.onkeydown = function (evt) {
        evt = evt || window.event
        var isEscape = false
        if ("key" in evt) {
            isEscape = (evt.key === "Escape" || evt.key === "Esc")
        } else {
            isEscape = (evt.keyCode === 27)
        }
        if (isEscape && document.body.classList.contains('deleteModal-active')) {
            toggleDeleteModal
        }
        if (isEscape && document.body.classList.contains('createModal-active')) {
            toggleCreateModal
        }
    };


    function toggleDeleteModal() {
        const body = document.querySelector('body')
        const modal = document.querySelector('.deleteModal')
        modal.classList.toggle('opacity-0')
        modal.classList.toggle('pointer-events-none')
        body.classList.toggle('deleteModal-active')
    }



    //MODAL createModal
    var openCreateModal = document.getElementsByClassName('createModal-open')
    for (let i = 0; i < openCreateModal.length; i++) {
        openCreateModal[i].addEventListener('click', function (event) {
            event.preventDefault()
            toggleCreateModal()
        })
    }

    const overlayCreateModal = document.querySelector('.createModal-overlay')
    overlayCreateModal.addEventListener('click', toggleCreateModal)

    var closeCreateModal = document.querySelectorAll('.createModal-close')
    for (var i = 0; i < closeCreateModal.length; i++) {
        closeCreateModal[i].addEventListener('click', toggleCreateModal)
    }



    function toggleCreateModal() {
        const body = document.querySelector('body')
        const modal = document.querySelector('.createModal')
        modal.classList.toggle('opacity-0')
        modal.classList.toggle('pointer-events-none')
        body.classList.toggle('createModal-active')
    }




    let singleBtn = document.getElementById('singleBtn');
    let multipleBtn = document.getElementById('multipleBtn');

    let singleFields = document.getElementById('singleFields');
    let multipleFields = document.getElementById('multipleFields');

    let type = document.getElementById('type');

    let orgClass = "px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3";
    let clickedClass = "px-6 py-3 bg-yellow-600 rounded-md text-white font-medium tracking-wide hover:bg-yellow-500 ml-3";

    singleBtn.addEventListener('click', function (event) {
        event.preventDefault();
        showSingle();
    });
    multipleBtn.addEventListener('click', function (event) {
        event.preventDefault();
        showMultiple();
    })
    hideall();

    function showSingle() {
        hideall();
        type.value = "single";
        singleBtn.className = clickedClass;
        singleFields.style.display = "block";
    }

    function showMultiple() {
        hideall();
        type.value = "multi";
        multipleBtn.className = clickedClass;
        multipleFields.style.display = "block";
    }

    function hideall() {
        singleBtn.className = orgClass;
        multipleBtn.className = orgClass;
        singleFields.style.display = "none";
        multipleFields.style.display = "none";

        document.getElementById('inputStartDate').value="";
        document.getElementById('inputEndDate').value="";
        document.getElementById('inputDate').value="";
    }

}
