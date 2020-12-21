@extends('template/BaseTemplate')

@section('content')
<link href="CSS/homeCSS.css" rel="stylesheet" type="text/css">
<div class="container mx-auto px-6 py-8">
    <h3 class="text-gray-700 text-3xl font-medium">Home</h3>
    <br>

    <div class="column">
        <h1>Order Page</h1>
        <br>
        <p>On the order page you can see a list orders that need to be prepared that day.
            You can click a button to see a more detailed version of the order.
            In this more detailed version you will be able to see a list of products which the person has ordered.
            There are buttons next to those items which you will have to click when the product is prepared.
            Once all the items are prepared there will appear a button that says finish the order.
            The fully prepared order will be sent to the checkout order page.
        </p>
    </div>
    <div class="column">
        <h1>Checkout Order Page</h1>
        <br>
        <p>On this page you will be able to see a list of fully prepared orders that are ready to be picked up.
            Next to an order is button that you will have to click once the order is picked up by the person.
        </p>
    </div>
    <div class="column">
        <h1>Menu Page</h1>
        <br>
    </div>
    <div class="column">
        <h1>Stock Page</h1>
        <br>
        <p>On this page you will be able to see a list of the ingredients that are in stock.
            Furthermore, you will be able to sort this list on the type of ingredient. You will be able to choose between fruit, vegetables, meat and finally carbs.
            There is also a button that says add ingredient. This button is to be used when you notice that the stock of a certain
            ingredient is starting to run low.
        </p>
    </div>
    <div class="column">
        <h1>Proposed Meals Page</h1>
        <br>
    </div>
    <div class="column">
        <h1>Statistics Page</h1>
        <br>
        <p>On this page you will be able to see all the statistics of the restaurant.
            These include the total orders for that day, the orders that are being processed.
            The orders that are ready. The amount of delivered products that day. The amount of money made and a chart with the top 5 dishes.
        </p>
    </div>
    <div class="column">
        <h1>Settings Page</h1>
        <br>
        <p>On this page you will be able to change your acount settings.
            You can change your email adress, your name or choose a new password. Once you have filled
            in the form you can click the update button this will update your account information.
        </p>
    </div>
    <div class="column">
        <h1>Admin Dashboard Page</h1>
        <br>
        <p>On this page the administrator will be able to see all the accounts of the employees and other administrators.
            He can sort this list on activated accounts, deactivated accounts, chefs, admins,other.
            On this page the admin will also be able to add a new employee. If the admin clicks the add a new employee button he will be redirected to a
            form where he will be able to create an account for an employee. He can also select the type of employee.
        </p>
    </div>
    <div class="column">
        <h1>Logout Page</h1>
        <br>
        <p>If you click on this page you will be logged out of your account.</p>
    </div>
</div>
@endsection