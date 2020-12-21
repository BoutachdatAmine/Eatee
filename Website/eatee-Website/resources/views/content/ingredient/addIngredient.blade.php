@extends('template/BaseTemplate')

@section('content')

<div class="container mx-auto px-6 py-8">
  <h3 class="text-gray-700 text-3xl font-medium">Add an ingredient</h3>
  <br>

  <form class="mt-6" action="{{route('createIngredient')}}" method="POST">
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
          <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2" for="stock">Stock quantity</label>
          <input class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-grey-lighter rounded py-3 px-4 mb-3" id="stock" name="inputstock" type="number" placeholder="1">
        </div>
      </div>

      <div class="-mx-3 md:flex mb-6">
        <div class="md:w-full px-3">
          <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2" for="type">Type</label>
          <select class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-grey-lighter rounded py-3 px-4 mb-3" name="inputType" id="type">
            <option value="fruit">Fruit</option>
            <option value="vegetable">Vegetable</option>
            <option value="meat">Meat/Fish</option>
            <option value="other">Other</option>
          </select>
        </div>
      </div>
      <div class="-mx-3 md:flex mb-6">
        <div class="md:w-full px-3">
        <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2">What use case</label>
          <input type="radio" id="male" name="inputUse" value="products">
          <label for="male">Products</label><br>
          <input type="radio" id="female" name="inputUse" value="sandwishes">
          <label for="female">For Custom Sandwishes</label><br>
        </div>
      </div>
      <button class="px-6 py-3 bg-green-600 rounded-md text-white font-medium tracking-wide hover:bg-green-500 ml-3" type="submit">Add Ingredient</button>
    </div>
  </form>
</div>
@endsection