'use strict'

window.onload = function () {
    console.log('Settings Script loaded');

    let id = document.getElementById('employeeId').value;
    let field = document.getElementById('twoFactorKey');
    let factorImage=document.getElementById('factorImage');

    let generateKeyBtn = document.getElementById('generateKeyBtn');
    let seeKeyBtn = document.getElementById('seeKeyBtn');
    let generateNewKeyBtn = document.getElementById('generateNewKeyBtn');
    let disableKeyBtn = document.getElementById('disableKeyBtn');
    let hideKeyBtn = document.getElementById('hideKeyBtn');

    seeKeyBtn.addEventListener('click', function (event) {
        event.preventDefault();
        seeKey(id)
    })
    generateNewKeyBtn.addEventListener('click', function (event) {
        event.preventDefault();
        generateKey(id)
    })
    disableKeyBtn.addEventListener('click', function (event) {
        event.preventDefault();
        deactivateKey(id)
    })
    generateKeyBtn.addEventListener('click', function (event) {
        event.preventDefault();
        generateKey(id)
    })
    hideKeyBtn.addEventListener('click', function (event) {
        event.preventDefault();
        hideKey(id)
    })
    showBtn(id);

    async function showBtn(id) {
        let employee = await getEmployee(id);
    
        generateKeyBtn.style.display = "none";
        seeKeyBtn.style.display = "none";
        generateNewKeyBtn.style.display = "none";
        disableKeyBtn.style.display = "none";
        hideKeyBtn.style.display = "none";
    
        if (employee.twoFactorKey == null) {
            generateKeyBtn.style.display = "block";
        } else {
            if (field.innerHTML == "") {
                seeKeyBtn.style.display = "block";
                generateNewKeyBtn.style.display = "block";
                disableKeyBtn.style.display = "block";
            } else {
                generateNewKeyBtn.style.display = "block";
                disableKeyBtn.style.display = "block";
                hideKeyBtn.style.display = "block";
            }
    
        }
    }
    
    async function seeKey(id) {
        let employee = await getEmployee(id);
        field.innerHTML = employee.twoFactorKey;
        field.style.display = 'block';
        let qrkey=`otpauth://totp/Eatee?secret=${employee.twoFactorKey}`;

        var qr=new QRCode(factorImage);
        qr.makeCode(qrkey);
        showBtn(id)
    }
    
    function hideKey(id) {
        field.innerHTML = "";
        field.style.display = 'none';
        factorImage.innerHTML=null;
        showBtn(id);
    }
    
    async function getEmployee(id) {
        let response = await fetch(`http://10.3.50.23:69/employees/${id}`);
        let data = await response.json();
        return data;
    }
    
    async function generateKey(id) {
        hideKey(id)
        let response = await fetch(`http://10.3.50.23:69/employees/${id}`, {
            method: "PATCH",
            headers: {
                "content-type": "application/json"
            },
            body: JSON.stringify({
                twoFactorKey: true
            })
        });
        let returnData = await response.json();
    
        seeKey(id);
    }
    
    async function deactivateKey(id) {
        let response = await fetch(`http://10.3.50.23:69/employees/${id}`, {
            method: "PATCH",
            headers: {
                "content-type": "application/json"
            },
            body: JSON.stringify({
                twoFactorKey: false
            })
        });
        let returnData = await response.json();
    
        hideKey(id);
    }
    
}