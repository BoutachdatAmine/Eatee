(function (){
    console.log('Message Script loaded');

    let error = document.getElementsByClassName('error');
    let succes = document.getElementById('succes');

     if (error) {
        for (let i = 0; i < error.length; i++) {
            setTimeout(function () {
                error[i].style.display = "none";
                console.log('timeout - error')
           }, 5000);
        }
    }

    if (succes != null) {
        setTimeout(function () {
            succes.style.display = "none";
            console.log('timeout - succes')
        }, 5000);
    }

})();