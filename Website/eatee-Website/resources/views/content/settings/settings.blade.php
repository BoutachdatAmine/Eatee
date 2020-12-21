@extends('template/BaseTemplate')

@section('content')

<script src="{{ url('js/settingsScript.js')}}"></script>
<script src="{{url('js/qrcode.min.js')}}"></script>

<div class="container mx-auto px-6 py-8">
    <h3 class="text-gray-700 text-3xl font-medium">Account Settings</h3>

    <form action="{{route('updateSettings')}}" method="POST">
        @csrf
        <input type="hidden" name="inputId" id="employeeId" value="{{$employee->employeeId}}">
        <div class="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4 flex flex-col my-2">
            <div class="-mx-3 md:flex mb-6">
                <div class="md:w-full px-3">
                    <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2" for="mail">Email</label>
                    <input class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-grey-lighter rounded py-3 px-4 mb-3" id="mail" name="inputMail" type="mail" value="{{$employee->email}}" placeholder="email@mail.com">
                </div>
            </div>
            <div class="-mx-3 md:flex mb-6">
                <div class="md:w-1/2 px-3 mb-6 md:mb-0">
                    <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2" for="first-name">First Name</label>
                    <input class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-red rounded py-3 px-4 mb-3" id="first-name" name="inputFirstName" type="text" value="{{$employee->firstname}}" placeholder="first name">
                </div>
                <div class="md:w-1/2 px-3">
                    <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2" for="last-name">Last Name</label>
                    <input class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-grey-lighter rounded py-3 px-4" id="last-name" name="inputLastName" type="text" value="{{$employee->lastname}}" placeholder="Last Name">
                </div>
            </div>
            <div class="-mx-3 md:flex mb-6">
                <div class="md:w-full px-3">
                    <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2" for="password">New Password</label>
                    <input class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-grey-lighter rounded py-3 px-4 mb-3" id="password" name="inputPassword" type="password" placeholder="***********" autocomplete="new-password">
                </div>
            </div>
            <div class="-mx-3 md:flex mb-6">
                <div class="md:w-full px-3">
                    <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2" for="hpassword">Password Confirmation</label>
                    <input class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-grey-lighter rounded py-3 px-4 mb-3" id="hpassword" name="inputConfirmPassword" type="password" placeholder="***********" autocomplete="new-password">
                </div>
            </div>

            <div class="-mx-3 md:flex mb-6">
                <div class="md:w-1/2 px-3">
                    <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2">Two Factor Key</label>
                    <label style="display: none;" class="block uppercase tracking-wide text-grey-darker text-xs mb-2" id="twoFactorKey"></label>
                    <div id="factorImage">

                    </div>
                </div>
                <div class="md:w-1/2 px-3">
                    <button class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3 mb-2" id="seeKeyBtn" type="button">See key</button>
                    <button class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3 mb-2" id="generateKeyBtn" type="button">Generate key</button>
                    <button class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3 mb-2" id="generateNewKeyBtn" type="button">Generate New key</button>
                    <button class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3 mb-2" id="disableKeyBtn" type="button">Disable key</button>
                    <button class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3 mb-2" id="hideKeyBtn" type="button">Hide key</button> </div>
            </div>

            <button class="px-6 py-3 bg-green-600 rounded-md text-white font-medium tracking-wide hover:bg-green-500 ml-3" type="submit">Update</button>
        </div>
    </form>
</div>
@endsection