'use strict'
window.onload = function () {
    console.log('Script loaded');

    //MODAL ChangeDate
    document.getElementById('changeModal-open').addEventListener('click',toggleChangeModal)
    document.getElementById('changeModal-overlay').addEventListener('click', toggleChangeModal)

    var closeChangeModal = document.getElementsByClassName('changeModal-close')
    for (var i = 0; i < closeChangeModal.length; i++) {
        closeChangeModal[i].addEventListener('click', toggleChangeModal)
    }

    document.onkeydown = function (evt) {
        evt = evt || window.event
        var isEscape = false
        if ("key" in evt) {
            isEscape = (evt.key === "Escape" || evt.key === "Esc")
        } else {
            isEscape = (evt.keyCode === 27)
        }
        if (isEscape && document.body.classList.contains('changeModal-active')) {
            toggleChangeModal
        }
    };

    function toggleChangeModal() {
        const modal = document.getElementById('changeModal')
        modal.classList.toggle('opacity-0')
        modal.classList.toggle('pointer-events-none')
        document.body.classList.toggle('changeModal-active')
    }
}
