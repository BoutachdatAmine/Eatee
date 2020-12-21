@extends('template/BaseTemplate')

@section('content')
<script src="{{ url('js/adminScript.js')}}"></script>


<div class="container mx-auto px-6 py-8">
  <h3 class="text-gray-700 text-3xl font-medium">Dashboard</h3>
  <br>
  <a href="{{route('addEmployee')}}" class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500">Add employee</a>
  <br><br>
  <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2">Sort on ...</label>
  <div class="flex rounded-md bg-white py-4 px-4 overflow-x-auto">
  <button class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3" id="all">Show All</button>
    <button class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3" id="activated">Activated accounts</button>
    <button class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3" id="deactivated">Deactivated accounts</button>
    <button class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3" id="chefs">All Chefs</button>
    <button class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3" id="admins">All Admins</button>
    <button class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3" id="other">All Other</button>
  </div>
  <br>

  <div class="-my-2 py-2 overflow-x-auto sm:-mx-6 sm:px-6 lg:-mx-8 lg:px-8">
    <div class="align-middle inline-block min-w-full shadow overflow-hidden sm:rounded-lg border-b border-gray-200">
      <table class="min-w-full">
        <thead>
          <tr>
            <th class="px-6 py-3 border-b border-gray-200 bg-gray-100 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">Id</th>
            <th class="px-6 py-3 border-b border-gray-200 bg-gray-100 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">EmployeeId</th>
            <th class="px-6 py-3 border-b border-gray-200 bg-gray-100 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">Name</th>
            <th class="px-6 py-3 border-b border-gray-200 bg-gray-100 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">Status</th>
            <th class="px-6 py-3 border-b border-gray-200 bg-gray-100 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">Role</th>
            <th class="px-6 py-3 border-b border-gray-200 bg-gray-100"></th>
          </tr>
        </thead>

        <tbody class="bg-white">
          @foreach($accounts as $account)
          <tr class="<?php if ($account->activated == 'true') {
                        echo ('active');
                      } else {
                        echo ('deactive');
                      } ?> {{$account->role}}">
            <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
              <div class="text-sm leading-5 text-gray-900">{{$account->userId}}</div>
            </td>
            <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
              <div class="text-sm leading-5 text-gray-900">{{$account->employeeId}}</div>
            </td>

            <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
              <div class="flex items-center">
                <div class="ml-4">
                  <div class="text-sm leading-5 font-medium text-gray-900">{{$account->firstname}} {{$account->lastname}}</div>
                  <div class="text-sm leading-5 text-gray-500">{{$account->email}}</div>
                </div>
              </div>
            </td>



            <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
              @if($account->activated=='true')
              <span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800">Activated</span>
              @else
              <span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-red-100 text-red-800">Deactivated</span>
              @endif
            </td>

            <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200 text-sm leading-5 text-gray-500">
              @if($account->role==1)
              Admin
              @elseif($account->role==2)
              Chef
              @elseif($account->role==3)
              Other
              @endif
            </td>

            <td class="px-6 py-4 whitespace-no-wrap text-right border-b border-gray-200 text-sm leading-5 font-medium">
              <button class="StatusModal-open px-6 py-3 bg-blue-600 rounded-md text-white font-medium tracking-wide hover:bg-blue-500">Edit status</button>
              <a href="{{route('editEmployee',$account->employeeId)}}" class="px-6 py-3 bg-green-600 rounded-md text-white font-medium tracking-wide hover:bg-green-500">Edit Employee</a>
              <button class="ResetModal-open px-6 py-3 bg-red-600 rounded-md text-white font-medium tracking-wide hover:bg-red-500">Reset Password</button>
            </td>
          </tr>
          @endforeach
        </tbody>
      </table>
    </div>
  </div>
</div>

<!-- MODALS -->

<div class="StatusModal opacity-0 pointer-events-none fixed w-full h-full top-0 left-0 flex items-center justify-center">
  <div class="StatusModal-overlay absolute w-full h-full bg-gray-900 opacity-50"></div>

  <div class="StatusModal-container bg-white w-11/12 md:max-w-md mx-auto rounded shadow-lg z-50 overflow-y-auto">

    <div class="StatusModal-close absolute top-0 right-0 cursor-pointer flex flex-col items-center mt-4 mr-4 text-white text-sm z-50">
      <svg class="fill-current text-white" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18">
        <path d="M14.53 4.53l-1.06-1.06L9 7.94 4.53 3.47 3.47 4.53 7.94 9l-4.47 4.47 1.06 1.06L9 10.06l4.47 4.47 1.06-1.06L10.06 9z"></path>
      </svg>
      <span class="text-sm">(Esc)</span>
    </div>

    <!-- Add margin if you want to see some of the overlay behind the modal-->
    <div class="StatusModal-content py-4 text-left px-6">
      <!--Title-->
      <div class="flex justify-between items-center pb-3">
        <p class="text-2xl font-bold">Edit status</p>
      </div>

      <!--Body-->
      <form class="mt-6" action="{{route('editStatus')}}" method="POST">
        @csrf
        <input type="hidden" name="inputId" id="inputfield" value="1">
        <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2" for="status">Status</label>
        <select class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-grey-lighter rounded py-3 px-4 mb-3" name="inputStatus" id="status">
          <option value="active" id="active">Activated</option>
          <option value="deactive" id="deactive">Deactivated</option>
        </select>
        <!--Footer-->
        <div class="flex justify-end pt-2">
          <button type="submit" class="px-4 bg-green-500 p-3 rounded-lg text-white hover:bg-green-400">Change</button>
          <a style="cursor:pointer;" class="StatusModal-close px-4 bg-indigo-500 p-3 rounded-lg text-white hover:bg-indigo-400">Close</a>
        </div>
      </form>
    </div>
  </div>
</div>

<div class="ResetModal opacity-0 pointer-events-none fixed w-full h-full top-0 left-0 flex items-center justify-center">
  <div class="ResetModal-overlay absolute w-full h-full bg-gray-900 opacity-50"></div>

  <div class="ResetModal-container bg-white w-11/12 md:max-w-md mx-auto rounded shadow-lg z-50 overflow-y-auto">

    <div class="ResetModal-close absolute top-0 right-0 cursor-pointer flex flex-col items-center mt-4 mr-4 text-white text-sm z-50">
      <svg class="fill-current text-white" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18">
        <path d="M14.53 4.53l-1.06-1.06L9 7.94 4.53 3.47 3.47 4.53 7.94 9l-4.47 4.47 1.06 1.06L9 10.06l4.47 4.47 1.06-1.06L10.06 9z"></path>
      </svg>
      <span class="text-sm">(Esc)</span>
    </div>

    <!-- Add margin if you want to see some of the overlay behind the modal-->
    <div class="ResetModal-content py-4 text-left px-6">
      <!--Title-->
      <div class="flex justify-between items-center pb-3">
        <p class="text-2xl font-bold">Reset Password</p>
      </div>

      <!--Body-->
      <form class="mt-6" action="{{route('resetPassword')}}" method="POST">
        @csrf
        <input type="hidden" name="inputId" id="inputfield2" value="1">
        <label for="password" class="block text-xs font-semibold text-gray-600 uppercase">New Password</label>
        <input id="password" type="password" name="inputPassword" class="block w-full p-3 mt-2 text-gray-700 bg-gray-200 appearance-none focus:outline-none focus:bg-gray-300 focus:shadow-inner" placeholder="******************" />
        <label for="passwordConfirm" class="block text-xs font-semibold text-gray-600 uppercase">Confirm Password</label>
        <input id="passwordConfirm" type="password" name="inputConfirmPassword" class="block w-full p-3 mt-2 text-gray-700 bg-gray-200 appearance-none focus:outline-none focus:bg-gray-300 focus:shadow-inner " placeholder="******************" />


        <!--Footer-->
        <div class="flex justify-end pt-2">
          <button type="submit" class="px-4 bg-green-500 p-3 rounded-lg text-white hover:bg-green-400">Change</button>
          <a style="cursor:pointer;" class="ResetModal-close px-4 bg-indigo-500 p-3 rounded-lg text-white hover:bg-indigo-400">Close</a>
        </div>
      </form>
    </div>
  </div>
</div>
@endsection