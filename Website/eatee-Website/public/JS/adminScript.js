'use strict'
window.onload = function () {
    console.log('Script loaded');

    //MODAL 1
    var openStatusModal = document.getElementsByClassName('StatusModal-open')
    for (let i = 0; i < openStatusModal.length; i++) {
        openStatusModal[i].addEventListener('click', function (event) {
            event.preventDefault()
            var id = openStatusModal[i].parentElement.parentElement.firstElementChild.nextElementSibling.firstElementChild.innerHTML;
            document.getElementById('inputfield').value = id;

            var selectionActive = document.getElementById('active');
            var selectionDeactive = document.getElementById('deactive');
            var status = openStatusModal[i].parentElement.previousElementSibling.previousElementSibling.firstElementChild.innerHTML;
            if (status == 'Activated') {
                selectionActive.selected = 'true';
            } else {
                selectionDeactive.selected = 'true';
            }
            toggleModal1()
        })
    }

    const overlayStatusModal = document.querySelector('.StatusModal-overlay')
    overlayStatusModal.addEventListener('click', toggleModal1)

    var closeStatusModal = document.querySelectorAll('.StatusModal-close')
    for (var i = 0; i < closeStatusModal.length; i++) {
        closeStatusModal[i].addEventListener('click', toggleModal1)
    }

    function toggleModal1() {
        const body = document.querySelector('body')
        const modal = document.querySelector('.StatusModal')
        modal.classList.toggle('opacity-0')
        modal.classList.toggle('pointer-events-none')
        body.classList.toggle('StatusModal-active')
    }

    //MODAL 2
    var openResetModal = document.getElementsByClassName('ResetModal-open')
    for (let i = 0; i < openResetModal.length; i++) {
        openResetModal[i].addEventListener('click', function (event) {
            event.preventDefault()
            var id2 = openResetModal[i].parentElement.parentElement.firstElementChild.nextElementSibling.firstElementChild.innerHTML;
            document.getElementById('inputfield2').value = id2;   
            toggleModal2()
        })
    }

    const overlayResetModal = document.querySelector('.ResetModal-overlay')
    overlayResetModal.addEventListener('click', toggleModal2)

    var closeResetModal = document.querySelectorAll('.ResetModal-close')
    for (var i = 0; i < closeResetModal.length; i++) {
        closeResetModal[i].addEventListener('click', toggleModal2)
    }
    function toggleModal2() {
        const body = document.querySelector('body')
        const modal = document.querySelector('.ResetModal')
        modal.classList.toggle('opacity-0')
        modal.classList.toggle('pointer-events-none')
        body.classList.toggle('ResetModal-active')
    }


    document.onkeydown = function (evt) {
        evt = evt || window.event
        var isEscape = false
        if ("key" in evt) {
            isEscape = (evt.key === "Escape" || evt.key === "Esc")
        } else {
            isEscape = (evt.keyCode === 27)
        }
        if (isEscape && document.body.classList.contains('StatusModal-active')) {
            toggleModal1()
        }
        if (isEscape && document.body.classList.contains('ResetModal-active')) {
            toggleModal2()
        }
    };

    //SORT

    //Give all buttons an event listener
    var activatedBtn = document.getElementById("activated");
    var deactivatedBtn = document.getElementById("deactivated");
    var chefsBtn = document.getElementById("chefs");
    var adminsBtn = document.getElementById("admins");
    var otherBtn = document.getElementById("other");
    var allBtn = document.getElementById("all");

    activatedBtn.addEventListener('click', function (event) {
        event.preventDefault();
        showActive();
    });
    deactivatedBtn.addEventListener('click', function (event) {
        event.preventDefault();
        showDeactive();
    });
    chefsBtn.addEventListener('click', function (event) {
        event.preventDefault();
        showChefs();
    });
    adminsBtn.addEventListener('click', function (event) {
        event.preventDefault();
        showAdmins();
    });
    otherBtn.addEventListener('click', function (event) {
        event.preventDefault();
        showOthers();
    });
    allBtn.addEventListener('click', function (event) {
        event.preventDefault();
        showAll();
    });

    var orgClass = "px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3";
    var clickedClass = "px-6 py-3 bg-yellow-600 rounded-md text-white font-medium tracking-wide hover:bg-yellow-500 ml-3";



    var activatedRows = document.getElementsByClassName('active');
    var deactivatedRows = document.getElementsByClassName('deactive');
    var chefsRows = document.getElementsByClassName('2');
    var adminRows = document.getElementsByClassName('1');
    var otherRows = document.getElementsByClassName('3');

    function showActive() {
        hideAll();
        activatedBtn.className = clickedClass;
        for (var i = 0; i < activatedRows.length; i++) {
            activatedRows[i].style.visibility = "visible";
        }
    }

    function showDeactive() {
        hideAll();
        deactivatedBtn.className = clickedClass;
        for (var i = 0; i < deactivatedRows.length; i++) {
            deactivatedRows[i].style.visibility = "visible";
        }
    }

    function showChefs() {
        hideAll();
        chefsBtn.className = clickedClass;
        for (var i = 0; i < chefsRows.length; i++) {
            chefsRows[i].style.visibility = "visible";
        }
    }

    function showAdmins() {
        hideAll();
        adminsBtn.className = clickedClass;
        for (var i = 0; i < adminRows.length; i++) {
            adminRows[i].style.visibility = "visible";
        }
    }

    function showOthers() {
        hideAll();
        otherBtn.className = clickedClass;
        for (var i = 0; i < otherRows.length; i++) {
            otherRows[i].style.visibility = "visible";
        }
    }


    function hideAll() {
        activatedBtn.className = orgClass;
        deactivatedBtn.className = orgClass;
        chefsBtn.className = orgClass;
        adminsBtn.className = orgClass;
        otherBtn.className = orgClass;
        allBtn.className = orgClass;

        for (var i = 0; i < activatedRows.length; i++) {
            activatedRows[i].style.visibility = "collapse";
        }
        for (var i = 0; i < deactivatedRows.length; i++) {
            deactivatedRows[i].style.visibility = "collapse";
        }
        for (var i = 0; i < chefsRows.length; i++) {
            chefsRows[i].style.visibility = "collapse";
        }
        for (var i = 0; i < adminRows.length; i++) {
            adminRows[i].style.visibility = "collapse";
        }
        for (var i = 0; i < otherRows.length; i++) {
            otherRows[i].style.visibility = "collapse";
        }
    }
showAll();
    function showAll() {
        hideAll();
        allBtn.className=clickedClass;
        for (var i = 0; i < activatedRows.length; i++) {
            activatedRows[i].style.visibility = "visible";
        }
        for (var i = 0; i < deactivatedRows.length; i++) {
            deactivatedRows[i].style.visibility = "visible";
        }
        for (var i = 0; i < chefsRows.length; i++) {
            chefsRows[i].style.visibility = "visible";
        }
        for (var i = 0; i < adminRows.length; i++) {
            adminRows[i].style.visibility = "visible";
        }
        for (var i = 0; i < otherRows.length; i++) {
            otherRows[i].style.visibility = "visible";
        }
    }
}
