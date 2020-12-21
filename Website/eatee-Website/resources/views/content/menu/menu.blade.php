@extends('template/BaseTemplate')

@section('content')
<script src="{{ url('js/menuScript.js')}}"></script>


<div class="container mx-auto px-6 py-8">
  <h3 class="text-gray-700 text-3xl font-medium">Active Menu's </h3>
  <br>
  <a href="{{route('wholeMenu')}}" class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500">See Whole menu/ All products</a>
  <button class="createModal-open px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500">Create New Menu</button>

  <br><br>

  <div class="-my-2 py-2 overflow-x-auto sm:-mx-6 sm:px-6 lg:-mx-8 lg:px-8">
    <div class="align-middle inline-block min-w-full shadow overflow-hidden sm:rounded-lg border-b border-gray-200">
      <table class="min-w-full">
        <thead>
          <tr>
            <th class="px-6 py-3 border-b border-gray-200 bg-gray-100 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">Menu Id</th>
            <th class="px-6 py-3 border-b border-gray-200 bg-gray-100 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">From Date</th>
            <th class="px-6 py-3 border-b border-gray-200 bg-gray-100 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">To Date</th>
            <th class="px-6 py-3 border-b border-gray-200 bg-gray-100 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">Products</th>
            <th class="px-6 py-3 border-b border-gray-200 bg-gray-100"></th>
          </tr>
        </thead>

        <tbody class="bg-white">
          @foreach($menus as $menu)
          <?php $i = 0; ?>
          <tr>
            <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
              <div class="text-sm leading-5 text-gray-900">{{$menu->menuId}}</div>
            </td>
            <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
              <div class="text-sm leading-5 text-gray-900">{{$menu->validFrom}}</div>
            </td>
            <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
              <div class="text-sm leading-5 text-gray-900"> {{$menu->validTo}} </div>
            </td>
            <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
              <div class="text-sm leading-5 text-gray-900">
                @foreach($menu->products as $productId)
                @foreach($products as $product)
                @if($product->productId==$productId)

                {{$product->name}}
                <br>

                @endif
                @endforeach
                @endforeach
              </div>
            </td>
            <td class="px-6 py-4 whitespace-no-wrap text-right border-b border-gray-200 text-sm leading-5 font-medium">
              <a href="{{route('oneMenu',$menu->menuId)}}" class="px-6 py-3 bg-green-600 rounded-md text-white font-medium tracking-wide hover:bg-green-500">show / edit</a>
              <button class=" deleteModal-open px-6 py-3 bg-red-600 rounded-md text-white font-medium tracking-wide hover:bg-red-500">Delete</button>
            </td>
          </tr>
          @endforeach
        </tbody>
      </table>
    </div>
  </div>
</div>


<!-- MODAL 1 -->

<div class="createModal opacity-0 pointer-events-none fixed w-full h-full top-0 left-0 flex items-center justify-center">
  <div class="createModal-overlay absolute w-full h-full bg-gray-900 opacity-50"></div>

  <div class="createModal-container bg-white w-11/12 md:max-w-md mx-auto rounded shadow-lg z-50 overflow-y-auto">

    <div class="createModal-close absolute top-0 right-0 cursor-pointer flex flex-col items-center mt-4 mr-4 text-white text-sm z-50">
      <svg class="fill-current text-white" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18">
        <path d="M14.53 4.53l-1.06-1.06L9 7.94 4.53 3.47 3.47 4.53 7.94 9l-4.47 4.47 1.06 1.06L9 10.06l4.47 4.47 1.06-1.06L10.06 9z"></path>
      </svg>
      <span class="text-sm">(Esc)</span>
    </div>

    <!-- Add margin if you want to see some of the overlay behind the modal-->
    <div class="createModal-content py-4 text-left px-6">
      <!--Title-->
      <div class="flex justify-between items-center pb-3">
        <p class="text-2xl font-bold">Create new menu</p>
      </div>

      <!--Body-->
      <form class="mt-6" action="{{route('addMenu')}}" method="POST">
        @csrf

        <a style="cursor:pointer;" class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3" id="singleBtn">Single day menu</a>
        <a style="cursor:pointer;" class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3" id="multipleBtn">Multiple day menu</a>

        <br><br>
        <input type="hidden" name="type" id="type">
        <div id="singleFields">
          <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2" for="inputDate">Date</label>
          <input class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-grey-lighter rounded py-3 px-4 mb-3" id="inputDate" name="inputDate" type="date">
        </div>
        <div id="multipleFields">
          <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2" for="inputStartDate">Start Date</label>
          <input class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-grey-lighter rounded py-3 px-4 mb-3" id="inputStartDate" name="inputStartDate" type="date">
          <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2" for="inputEndDate">End Date</label>
          <input class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-grey-lighter rounded py-3 px-4 mb-3" id="inputEndDate" name="inputEndDate" type="date">
        </div>
        <!--Footer-->
        <div class="flex justify-end pt-2">
          <button type="submit" class="px-4 bg-green-500 p-3 rounded-lg text-white hover:bg-green-400">Create</button>
          <a style="cursor:pointer;" class="createModal-close px-4 bg-indigo-500 p-3 rounded-lg text-white hover:bg-indigo-400">Close</a>
        </div>
      </form>
    </div>
  </div>
</div>


<!-- MODAL 2 -->
<div class="deleteModal opacity-0 pointer-events-none fixed w-full h-full top-0 left-0 flex items-center justify-center">
  <div class="deleteModal-overlay absolute w-full h-full bg-gray-900 opacity-50"></div>

  <div class="deleteModal-container bg-white w-11/12 md:max-w-md mx-auto rounded shadow-lg z-50 overflow-y-auto">

    <div class="deleteModal-close absolute top-0 right-0 cursor-pointer flex flex-col items-center mt-4 mr-4 text-white text-sm z-50">
      <svg class="fill-current text-white" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18">
        <path d="M14.53 4.53l-1.06-1.06L9 7.94 4.53 3.47 3.47 4.53 7.94 9l-4.47 4.47 1.06 1.06L9 10.06l4.47 4.47 1.06-1.06L10.06 9z"></path>
      </svg>
      <span class="text-sm">(Esc)</span>
    </div>

    <!-- Add margin if you want to see some of the overlay behind the modal-->
    <div class="deleteModal-content py-4 text-left px-6">
      <!--Title-->
      <div class="flex justify-between items-center pb-3">
        <p class="text-2xl font-bold">Are you sure?</p>
      </div>

      <!--Body-->
      <form class="mt-6" action="{{route('deleteMenu')}}" method="POST">
        @csrf
        <input type="hidden" name="menuId" value="1" id="menuIdField1">

        <!--Footer-->
        <div class="flex justify-end pt-2">
          <button type="submit" class="px-4 bg-red-500 p-3 rounded-lg text-white hover:bg-red-400">Yes</button>
          <a style="cursor:pointer;" class="deleteModal-close px-4 bg-indigo-500 p-3 rounded-lg text-white hover:bg-indigo-400">No</a>
        </div>
      </form>
    </div>
  </div>
</div>
@endsection