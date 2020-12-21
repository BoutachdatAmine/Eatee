<!doctype html>
<html lang="en">

<head>
    <title>Eatee</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=dauto, initial-scale=1">
    <link href="https://unpkg.com/tailwindcss@1.9.6/dist/tailwind.min.css" rel="stylesheet">
    <link rel="icon" href="{{url('img/icon.png')}}">

</head>

<body>
    <div class="grid min-h-screen place-items-center bg-gray-200">
        @include("partials/error-succesMsg")
        <div class="w-11/12 p-12 bg-white sm:w-8/12 md:w-1/2 lg:w-5/12">
            @yield('content')
        </div>
        @include("partials/footer")
    </div>
</body>

</html>