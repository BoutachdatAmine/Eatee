'use strict'
window.onload = function () {
    console.log('Script loaded');

    var finishBtn = document.getElementById('finish');
    finishBtn.hidden = "true";

    var doneBtn = document.getElementsByClassName('doneBtn');
    for (let i = 0; i < doneBtn.length; i++) {
        doneBtn[i].addEventListener('click', function (event) {
            event.preventDefault()
            checkdone(i);
        })
    }
    var newClass = "doneBtn px-6 py-3 bg-green-600 rounded-md text-white font-medium tracking-wide hover:bg-green-500";
    var oldClass = "doneBtn px-6 py-3 bg-red-600 rounded-md text-white font-medium tracking-wide hover:bg-red-500";

    function checkdone(row) {
        if (doneBtn[row].className == oldClass) {
            doneBtn[row].className = newClass;
            doneBtn[row].innerHTML = "Done";
        } else {
            doneBtn[row].className = oldClass;
            doneBtn[row].innerHTML = "Not Done";
        }
        verify();
    }

    function verify() {
        var succes = false;
        for (let i = 0; i < doneBtn.length; i++) {
            if (doneBtn[i].innerHTML == "Done") {
                succes = true;
            } else {
                succes = false;
                break;
            }
        }
        if (succes) {
            finishBtn.hidden = false;
        } else {
            finishBtn.hidden = true;
        }
    }
}
