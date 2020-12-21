<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Eatee</title>
    <link href="https://tailwindcomponents.com/css/component.dashboard-template.css" rel="stylesheet">

    <link rel="icon" href="{{url('img/icon.png')}}">
</head>

<body>
    <div>
        <script src="https://cdn.jsdelivr.net/gh/alpinejs/alpine@v2.x.x/dist/alpine.min.js" defer></script>

        <div x-data="{ sidebarOpen: false }" class="flex h-screen bg-gray-200">
            <div :class="sidebarOpen ? 'block' : 'hidden'" @click="sidebarOpen = false" class="fixed z-20 inset-0 bg-black opacity-50 transition-opacity lg:hidden"></div>

            <div :class="sidebarOpen ? 'translate-x-0 ease-out' : '-translate-x-full ease-in'" class="fixed z-30 inset-y-0 left-0 w-64 transition duration-300 transform bg-gray-900 overflow-y-auto lg:translate-x-0 lg:static lg:inset-0">
                <div class="flex items-center justify-center mt-8">
                    <div class="flex items-center">
                        <a href="{{ route('home') }}"><img src="{{url('img/logo.png')}}" alt="EATEE"></a>
                    </div>
                </div>

                <?php
                $baseClass = "flex items-center mt-4 py-2 px-6 text-gray-500 hover:bg-gray-700 hover:bg-opacity-25 hover:text-gray-100";
                $selectedClass = "flex items-center mt-4 py-2 px-6 bg-gray-700 bg-opacity-25 text-gray-100";
                $selected = false;
                $url = Request::route()->getName();
                ?>
                <nav class="mt-10">
                    <a class="<?php if ($url == 'home') {
                                    echo ($selectedClass);
                                } else {
                                    echo ($baseClass);
                                } ?>" href="{{route('home')}}" onclick="document.body.style.cursor='wait';">
                        <svg class="h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6" />
                        </svg>
                        <span class="mx-3">Home</span>
                    </a>
                    <a class="<?php if ($url == 'orders' || $url == 'showOrder' || $url == 'finaliseOrder' || $url == 'changeDate') {
                                    echo ($selectedClass);
                                } else {
                                    echo ($baseClass);
                                } ?>" href="{{ route('orders', ['date' => date('Y-m-d')]) }}" onclick="document.body.style.cursor='wait';">
                        <svg class="h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2m-3 7h3m-3 4h3m-6-4h.01M9 16h.01" />
                        </svg>
                        <span class="mx-3">Orders</span>
                    </a>
                    <a class="<?php if ($url == 'checkout' || $url == 'checkoutOrder' || $url == 'changeDateCheckout') {
                                    echo ($selectedClass);
                                } else {
                                    echo ($baseClass);
                                } ?>" href="{{ route('checkout', ['date' => date('Y-m-d')]) }}" onclick="document.body.style.cursor='wait';">
                        <svg class="h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 11V7a4 4 0 00-8 0v4M5 9h14l1 12H4L5 9z" />
                        </svg>
                        <span class="mx-3">Checkout orders</span>
                    </a>

                    <a class="<?php if ($url == 'menu' || $url == 'wholeMenu' || $url == 'oneMenu' || $url == 'addProductMenu' || $url == 'addProductToMenu' || $url == 'deleteProductfromMenu' || $url == 'deleteMenu' || $url == 'addMenu' || $url == 'addProduct' || $url == 'editProductPage' || $url == 'editProduct' || $url == 'giveDiscount' || $url == 'createProduct' || $url == 'addAllergie') {
                                    echo ($selectedClass);
                                } else {
                                    echo ($baseClass);
                                } ?>" href="{{route('menu')}}" onclick="document.body.style.cursor='wait';">
                        <svg class="h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.747 0 3.332.477 4.5 1.253v13C19.832 18.477 18.247 18 16.5 18c-1.746 0-3.332.477-4.5 1.253" />
                        </svg>
                        <span class="mx-3">Menu's</span>
                    </a>

                    <a class="<?php if ($url == 'stock' || $url == 'addStock' || $url == 'removeStock' || $url == 'editStock' || $url == 'addIngredient' || $url == 'createIngredient') {
                                    echo ($selectedClass);
                                } else {
                                    echo ($baseClass);
                                } ?>" href="{{route('stock')}}" onclick="document.body.style.cursor='wait';">
                        <svg class="h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4" />
                        </svg>
                        <span class="mx-3">Stock</span>
                    </a>

                    <a class="<?php if ($url == 'proposedMeals' || $url == 'addPropose' || $url == 'proposeProduct' || $url == 'deleteProposal') {
                                    echo ($selectedClass);
                                } else {
                                    echo ($baseClass);
                                } ?>" href="{{route('proposedMeals')}}" onclick="document.body.style.cursor='wait';">
                        <svg class="h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.747 0 3.332.477 4.5 1.253v13C19.832 18.477 18.247 18 16.5 18c-1.746 0-3.332.477-4.5 1.253" />
                        </svg>
                        <span class="mx-3">Proposed meals</span>
                    </a>

                    <a class="<?php if ($url == 'statistics') {
                                    echo ($selectedClass);
                                } else {
                                    echo ($baseClass);
                                } ?>" href="{{route('statistics')}}" onclick="document.body.style.cursor='wait';">
                        <svg class="h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 13v-1m4 1v-3m4 3V8M8 21l4-4 4 4M3 4h18M4 4h16v12a1 1 0 01-1 1H5a1 1 0 01-1-1V4z" />
                        </svg>
                        <span class="mx-3">Statistics</span>
                    </a>

                    <a class="<?php if ($url == 'settings' || $url == 'updateSettings') {
                                    echo ($selectedClass);
                                } else {
                                    echo ($baseClass);
                                } ?>" href="{{route('settings')}}" onclick="document.body.style.cursor='wait';">
                        <svg class="h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z" />
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                        </svg>
                        <span class="mx-3">Settings</span>
                    </a>

                    @if(session('user')->role==1)
                    <a class="<?php if ($url == 'admin' || $url == 'addEmployee' || $url == 'editEmployee' || $url == 'editAnEmployee' || $url == 'addAnEmployee' || $url == 'resetPassword' || $url == 'editStatus') {
                                    echo ($selectedClass);
                                } else {
                                    echo ($baseClass);
                                } ?>" href="{{route('admin')}}" onclick="document.body.style.cursor='wait';">
                        <svg class="h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 6H5a2 2 0 00-2 2v9a2 2 0 002 2h14a2 2 0 002-2V8a2 2 0 00-2-2h-5m-4 0V5a2 2 0 114 0v1m-4 0a2 2 0 104 0m-5 8a2 2 0 100-4 2 2 0 000 4zm0 0c1.306 0 2.417.835 2.83 2M9 14a3.001 3.001 0 00-2.83 2M15 11h3m-3 4h2" />
                        </svg>
                        <span class="mx-3">Admin Dashboard</span>
                    </a>
                    @endif


                    <a class="<?php if ($url == 'dashboardExtra') {
                                    echo ($selectedClass);
                                } else {
                                    echo ($baseClass);
                                } ?>" href="{{route('dashboardExtra')}}" onclick="document.body.style.cursor='wait';">
                        <svg class="h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z" />
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                        </svg>
                        <span class="mx-3">Dashboard</span>
                    </a>


                    <a class="<?php echo ($baseClass);
                                ?>" href="{{route('logout')}}" onclick="document.body.style.cursor='wait';">
                        <svg class="h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1" />
                        </svg>
                        <span class="mx-3">Logout</span>
                    </a>
                </nav>
            </div>

            <div class="flex-1 flex flex-col overflow-hidden">
                <main class="flex-1 overflow-x-hidden overflow-y-auto bg-gray-200">
                    <div class="flex items-center">
                        <button @click="sidebarOpen = true" class="text-gray-500 focus:outline-none lg:hidden">
                            <svg class="h-6 w-6" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path d="M4 6H20M4 12H20M4 18H11" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                            </svg>
                        </button>
                    </div>
                    @include("partials/error-succesMsg")
                    @yield('content')
                    @include("partials/footer")
                </main>
            </div>
</body>

</html>