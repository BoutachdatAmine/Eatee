@extends('template/BaseTemplate')

@section('content')
<script src="{{ url('js/addProductScript.js')}}"></script>

<div class="container mx-auto px-6 py-8">
  <h3 class="text-gray-700 text-3xl font-medium">Add a product</h3>
  <br>
  <button class="modal-open px-6 py-3 bg-green-600 rounded-md text-white font-medium tracking-wide hover:bg-green-500">Add new allergie</button>
  <a class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 mb-2" href="{{route('addIngredient')}}">Create new ingredient</a>

  <form class="mt-6" action="{{route('createProduct')}}" method="POST">
    @csrf
    <div class="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4 flex flex-col my-2">
      <div class="-mx-3 md:flex mb-6">
        <div class="md:w-full px-3">
          <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2" for="grid-name">Name</label>
          <input class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-grey-lighter rounded py-3 px-4 mb-3" id="grid-name" name="inputName" type="text" placeholder="name">
        </div>
      </div>
      <div class="-mx-3 md:flex mb-6">
        <div class="md:w-full px-3">
          <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2" for="price">Price</label>
          <input class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-grey-lighter rounded py-3 px-4 mb-3" step=".01"  id="price" name="inputPrice" type="number" placeholder="1">
        </div>
      </div>
      <div class="-mx-3 md:flex mb-6">
        <div class="md:w-full px-3">
          <!-- FROM DATABASE -->
          <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2" for="category">Category</label>
          <select class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-grey-lighter rounded py-3 px-4 mb-3" name="inputCategory" id="category">
            @foreach($categories as $category)
            @if($category->id!=4&&$category->id!=5&&$category->id!=6&&$category->id!=9)
            <option value="{{$category->id}}">{{$category->name}}</option>
            @endif
            @endforeach
            <option value="Other">Other</option>
          </select>
        </div>
      </div>

      <div class="-mx-3 md:flex mb-6" style="display:none;" id="newCat">
        <div class="md:w-full px-3">
          <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2" for="grid-cat">New Category name</label>
          <input class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-grey-lighter rounded py-3 px-4 mb-3" id="grid-cat" name="inputNewCat" type="text" placeholder="New Category name">
        </div>
      </div>

      <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2">Allergies</label>
      <div class="flex rounded-md bg-white py-4 px-4 overflow-x-auto">

        @foreach($allergies as $allergy)
        <label class="inline-flex items-center ml-3">
          <input type="checkbox" class="form-checkbox h-5 w-5 text-gray-600" value="{{ $allergy->id }}" name="inputAllergies[]">
          <span class="ml-2 text-gray-700">{{$allergy->name}}</span>
        </label>
        @endforeach

      </div>


      <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2">Ingredients</label>
      <div class="flex rounded-md bg-white py-4 px-4 overflow-x-auto">
        <button class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3" id="fruit">Fruit</button>
        <button class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3" id="vegetables">Vegetables</button>
        <button class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3" id="meat">Meat</button>
        <button class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3" id="carbs">Other</button>
      </div>

      <!-- GET ALL INGREDIENTS FROM DATABASE -->
      <div class="flex rounded-md bg-white py-4 px-4 overflow-x-auto" id="ListFruit" style="display:none;">
        @foreach($ingredients as $ingredient)
        @if($ingredient->categoryId=='5')
        <label class="inline-flex items-center ml-3">
          <input type="checkbox" class="form-checkbox h-5 w-5 text-gray-600 checkBoxIngredient" value="{{$ingredient->ingredientId}}" name="inputIngredients[]"><span class="ml-2 text-gray-700">{{$ingredient->name}}</span>
        </label>
        @endif
        @endforeach
      </div>
      <div class="flex rounded-md bg-white py-4 px-4 overflow-x-auto" id="ListVeg" style="display:none;">
        @foreach($ingredients as $ingredient)
        @if($ingredient->categoryId=='4')
        <label class="inline-flex items-center ml-3">
          <input type="checkbox" class="form-checkbox h-5 w-5 text-gray-600 checkBoxIngredient" value="{{$ingredient->ingredientId}}" name="inputIngredients[]"><span class="ml-2 text-gray-700">{{$ingredient->name}}</span>
        </label>
        @endif
        @endforeach
      </div>
      <div class="flex rounded-md bg-white py-4 px-4 overflow-x-auto" id="ListMeat" style="display:none;">
        @foreach($ingredients as $ingredient)
        @if($ingredient->categoryId=='6')
        <label class="inline-flex items-center ml-3">
          <input type="checkbox" class="form-checkbox h-5 w-5 text-gray-600 checkBoxIngredient" value="{{$ingredient->ingredientId}}" name="inputIngredients[]"><span class="ml-2 text-gray-700">{{$ingredient->name}}</span>
        </label>
        @endif
        @endforeach
      </div>
      <div class="flex rounded-md bg-white py-4 px-4 overflow-x-auto" id="ListCarbs" style="display:none;">
        @foreach($ingredients as $ingredient)
        @if($ingredient->categoryId=='9')
        <label class="inline-flex items-center ml-3">
          <input type="checkbox" class="form-checkbox h-5 w-5 text-gray-600 checkBoxIngredient" value="{{$ingredient->ingredientId}}" name="inputIngredients[]"><span class="ml-2 text-gray-700">{{$ingredient->name}}</span>
        </label>
        @endif
        @endforeach
      </div>
      <br>
      <a class='block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2'>How many ingredients needed</a>
      <br>

      <div id="manyIngredients" class="flex rounded-md bg-white py-4 px-4 overflow-x-auto">

      </div>
      <button class="px-6 py-3 bg-green-600 rounded-md text-white font-medium tracking-wide hover:bg-green-500 ml-3" type="submit">Add product</button>
    </div>
  </form>
</div>



<!-- MODALS -->

<div class="modal opacity-0 pointer-events-none fixed w-full h-full top-0 left-0 flex items-center justify-center">
  <div class="modal-overlay absolute w-full h-full bg-gray-900 opacity-50"></div>

  <div class="modal-container bg-white w-11/12 md:max-w-md mx-auto rounded shadow-lg z-50 overflow-y-auto">

    <div class="modal-close absolute top-0 right-0 cursor-pointer flex flex-col items-center mt-4 mr-4 text-white text-sm z-50">
      <svg class="fill-current text-white" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18">
        <path d="M14.53 4.53l-1.06-1.06L9 7.94 4.53 3.47 3.47 4.53 7.94 9l-4.47 4.47 1.06 1.06L9 10.06l4.47 4.47 1.06-1.06L10.06 9z"></path>
      </svg>
      <span class="text-sm">(Esc)</span>
    </div>

    <!-- Add margin if you want to see some of the overlay behind the modal-->
    <div class="modal-content py-4 text-left px-6">
      <form class="mt-6" action="{{route('addAllergie')}}" method="POST">
        @csrf
        <input type="hidden" name="inputId" id="id" value="1">

        <!--Title-->
        <div class="flex justify-between items-center pb-3">
          <p class="text-2xl font-bold">Create new Allergie</p>
          <div class="modal-close cursor-pointer z-50">
            <svg class="fill-current text-black" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18">
              <path d="M14.53 4.53l-1.06-1.06L9 7.94 4.53 3.47 3.47 4.53 7.94 9l-4.47 4.47 1.06 1.06L9 10.06l4.47 4.47 1.06-1.06L10.06 9z"></path>
            </svg>
          </div>
        </div>

        <!--Body-->

        <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2" for="newAlle">Name</label>
        <input class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-grey-lighter rounded" id="newAlle" name="inputAllergie" type="text" placeholder="allergie">

        <!--Footer-->
        <div class="flex justify-end pt-2">
          <button type="submit" class="modal-close px-4 bg-green-500 p-3 rounded-lg text-white hover:bg-green-400">Create</button>
      </form>
      <button class="modal-close px-4 bg-indigo-500 p-3 rounded-lg text-white hover:bg-indigo-400">Close</button>
    </div>
    </form>
  </div>
</div>
</div>
@endsection