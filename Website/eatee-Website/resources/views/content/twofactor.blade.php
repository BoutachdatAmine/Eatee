@extends('template/LoginTemplate')

@section('content')
    <h1 class="text-xl font-semibold">EATEE Login - Two Factor Authentication</h1>
    <form class="mt-6" action="{{ route('twofactor') }}" method="POST">
        @csrf

        <input type="hidden" name="inputMail" value="{{ $inputMail ?? old('inputMail') }}">
        <input type="hidden" name="inputPassword" value="{{ $inputPassword ?? old('inputPassword') }}">

        <label for="key" class="block text-xs font-semibold text-gray-600 uppercase">Your account has two factor authentication enabled. <br>Please enter your key: </label>
        <input id="key" type="text" name="inputKey" placeholder="key" class="block w-full p-3 mt-2 text-gray-700 bg-gray-200 appearance-none focus:outline-none focus:bg-gray-300 focus:shadow-inner"/>

        <button type="submit" class="w-full py-3 mt-6 font-medium tracking-widest text-white uppercase bg-black shadow-lg focus:outline-none hover:bg-gray-900 hover:shadow-none">
            Submit key
        </button>
    </form>
@endsection