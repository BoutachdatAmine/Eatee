'use strict'
window.onload = function () {
    console.log('Statistics Script loaded');

    var pieChart=new Chart(document.getElementById("chartjs-4"), {
      "type": "pie",
      "data": {
        "labels": ["Red", "Blue", "Yellow"],
        "datasets": [{
          "label": "Most sold products",
          "data": [300, 50, 100],
          "backgroundColor": ["rgb(255, 99, 132)", "rgb(54, 162, 235)", "rgb(255, 205, 86)"]
        }]
      }
    });
    let chart =new Chart(document.getElementById("chartjs-1"), {
        "type": "bar",
        "data": {
            "labels": ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"],
            "datasets": [{
                "label": "Sold products",
                "data": [65, 59, 80, 81, 56, 55, 40, 44, 44, 44, 55, 44],
                "fill": false,
                "backgroundColor": ["rgb(27,16,220)",
                    "rgb(96,57,138)",
                    "rgb(77,19,106)",
                    "rgb(176,7,103)",
                    "rgb(218,6,1)",
                    "rgb(243,42,0)",
                    "rgb(252,115,0)",
                    "rgb(253,203,44)",
                    "rgb(253,234,44)",
                    "rgb(174,213,16)",
                    "rgb(35,142,35)",
                    "rgb(19,129,126)",
                ],
                "borderColor": ["rgb(0,0,0)",
                    "rgb(0,0,0)",
                    "rgb(0,0,0)",
                    "rgb(0,0,0)",
                    "rgb(0,0,0)",
                    "rgb(0,0,0)",
                    "rgb(0,0,0)",
                    "rgb(0,0,0)",
                    "rgb(0,0,0)",
                    "rgb(0,0,0)",
                    "rgb(0,0,0)",
                    "rgb(0,0,0)",
                ],
                "borderWidth": 1
            }]
        },
        "options": {
            "scales": {
                "yAxes": [{
                    "ticks": {
                        "beginAtZero": true
                    }
                }]
            }
        }
    });

setTimeout(updateGraph1,'200')
setTimeout(updateGraph2,'2000')

async function getOrders() {
    let response = await fetch('http://10.3.50.23:69/orders/');
    let data = await response.json();
    return data;
}
async function getProducts() {
    let response = await fetch('http://10.3.50.23:69/products/');
    let data = await response.json();
    return data;
}

async function updateGraph2() {
    console.log('updateGraph2')
    let orders = await getOrders();
    var amounts = [];
    for (let x = 0; x < 12; x++) {
        let temp = 0;
        for (let i = 0; i < orders.length; i++) {
            let orderDate = orders[i].orderedAt;
            orderDate=new Date(orderDate)
            if(orderDate.getMonth()==x){
                temp += orders[i].productIds.length;
            }
        }
        amounts[x] = temp;
    }
    chart.data.datasets.forEach((dataset)=>{
        dataset.data=amounts;
    })
   chart.update();
}

async function updateGraph1() {
    console.log('updateGraph1')

    let orders = await getOrders();
    let products = await getProducts();

    var amounts = [];
    var names = [];
    var colors = [];

    
    for (let x = 0; x < products.length ; x++) {
        let temp = 0;
        for (let y = 0; y < orders.length; y++) {
            for (let i = 0; i < orders[y].productIds.length; i++) {
                if (orders[y].productIds[i] == products[x].productId) {
                    temp++;
                }
            }
        }
        amounts[x]=temp;
        names[x]=products[x].name;
    }

    for(let i=0;i<names.length;i++){
        let r=Math.floor(Math.random()*255);
        let g=Math.floor(Math.random()*255);
        let b=Math.floor(Math.random()*255);
        let color=`rgb(${r},${g},${b})`;
        colors.push(color);
    }

    pieChart.data.datasets.forEach((dataset)=>{
        dataset.data=amounts;

        dataset.backgroundColor=colors;
    });
    pieChart.data.labels=names;

    pieChart.update();

}
}

