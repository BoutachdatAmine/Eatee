@extends('template/LoginTemplate')

@section('content')
<h1 class="text-xl font-semibold">EATEE Login</h1>
<form class="mt-6" action="{{route('login')}}" method="POST">
  @csrf
  <label for="email" class="block text-xs font-semibold text-gray-600 uppercase">E-mail</label>
  <input id="email" type="email" name="inputMail" placeholder="john.doe@company.com" autocomplete="email" class="block w-full p-3 mt-2 text-gray-700 bg-gray-200 appearance-none focus:outline-none focus:bg-gray-300 focus:shadow-inner"/>

  <label for="password" class="block mt-2 text-xs font-semibold text-gray-600 uppercase">Password</label>
  <input id="password" type="password" name="inputPassword" placeholder="********" class="block w-full p-3 mt-2 text-gray-700 bg-gray-200 appearance-none focus:outline-none focus:bg-gray-300 focus:shadow-inner" />

  <button type="submit" class="w-full py-3 mt-6 font-medium tracking-widest text-white uppercase bg-black shadow-lg focus:outline-none hover:bg-gray-900 hover:shadow-none">
    Log In
  </button>
</form>
@endsection