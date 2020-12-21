'use strict'
window.onload = function () {
    console.log('Propose script loaded');


    let mostBtn = document.getElementById('mostLiked');
    let leastBtn = document.getElementById('leastLiked');

    mostBtn.addEventListener('click', filterMostLiked);
    leastBtn.addEventListener('click', filterLeastLiked);

    let rows = document.getElementsByClassName('rows');

    var orgClass = "px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3";
    var clickedClass = "px-6 py-3 bg-yellow-600 rounded-md text-white font-medium tracking-wide hover:bg-yellow-500 ml-3";


    function filterMostLiked() {
        mostBtn.className = clickedClass;
        leastBtn.className = orgClass;
        let futureRows = [];

        for (let i = 0; i < rows.length; i++) {
            futureRows.push(rows[i]);
        }
        futureRows.sort(function (a, b) {
            let alikes = a.firstElementChild.nextElementSibling.nextElementSibling.firstElementChild.innerHTML;
            let blikes = b.firstElementChild.nextElementSibling.nextElementSibling.firstElementChild.innerHTML;
            if (alikes > blikes) {
                return -1;
            }
            if (blikes > alikes) {
                return 1;
            }
            return 0;
        })
        let body = document.getElementById('inputBody');

        let html = '';
        futureRows.forEach(element => {
            html += '<tr class="rows">'
            html += element.innerHTML;
            html+='</tr>'
        });
        body.innerHTML = html;
    }


    function filterLeastLiked() {
        leastBtn.className = clickedClass;
        mostBtn.className = orgClass;
        let futureRows = [];

        for (let i = 0; i < rows.length; i++) {
            futureRows.push(rows[i]);
        }

        futureRows.sort(function (a, b) {
            let alikes = a.firstElementChild.nextElementSibling.nextElementSibling.firstElementChild.innerHTML;
            let blikes = b.firstElementChild.nextElementSibling.nextElementSibling.firstElementChild.innerHTML;
            if (alikes > blikes) {
                return 1;
            }
            if (blikes > alikes) {
                return -1;
            }
            return 0;
        })
        let body = document.getElementById('inputBody');

        let html = '';
        futureRows.forEach(element => {
            html += '<tr class="rows">'
            html += element.innerHTML;
            html+='</tr>'
        });
        body.innerHTML = html;
    }
}
