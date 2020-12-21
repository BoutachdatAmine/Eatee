@extends('template/BaseTemplate')

@section('content')
<div class="container mx-auto px-6 py-8">
    <h3 class="text-gray-700 text-3xl font-medium">Create an account</h3>

    <form action="{{route('addAnEmployee')}}" method="POST">
        @csrf
        <div class="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4 flex flex-col my-2">
            <div class="-mx-3 md:flex mb-6">
                <div class="md:w-full px-3">
                    <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2" for="grid-mail">Email</label>
                    <input class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-grey-lighter rounded py-3 px-4 mb-3" id="grid-mail" name="inputMail" type="email" placeholder="email@mail.com" value="{{old('inputMail')}}">
                </div>
            </div>
            <div class="-mx-3 md:flex mb-6">
                <div class="md:w-1/2 px-3 mb-6 md:mb-0">
                    <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2" for="grid-first-name">First Name</label>
                    <input class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-red rounded py-3 px-4 mb-3" id="grid-first-name" name="inputFirstName" type="text" placeholder="first name" value="{{old('inputFirstName')}}">
                </div>
                <div class="md:w-1/2 px-3">
                    <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2" for="grid-last-name">Last Name</label>
                    <input class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-grey-lighter rounded py-3 px-4" id="grid-last-name" name="inputLastName" type="text" placeholder="Last Name"value="{{old('inputLastName')}}">
                </div>
            </div>
            <div class="-mx-3 md:flex mb-6">
                <div class="md:w-full px-3">
                    <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2" for="grid-password">Password</label>
                    <input class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-grey-lighter rounded py-3 px-4 mb-3" id="grid-password" name="inputPassword" type="password" placeholder="******************">
                </div>
            </div>
            <div class="-mx-3 md:flex mb-6">
                <div class="md:w-full px-3">
                    <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2" for="grid-hpassword">Password Confirmation</label>
                    <input class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-grey-lighter rounded py-3 px-4 mb-3" id="grid-hpassword" name="inputConfirmPassword" type="password" placeholder="******************">
                </div>
            </div>
            <div class="-mx-3 md:flex mb-6">
                <div class="md:w-1/3 px-3">
                    <!-- FROM DATABASE -->
                    <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2" for="type">Type</label>
                    <select class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-grey-lighter rounded py-3 px-4 mb-3" name="inputType" id="type">
                        <option value="admin">Admin</option>
                        <option value="chef">Chef</option>
                        <option value="Other">Other</option>
                    </select>
                </div>
            </div>
            <button class="px-6 py-3 bg-green-600 rounded-md text-white font-medium tracking-wide hover:bg-green-500 ml-3" type="submit">Create</button>
        </div>
    </form>
    @endsection