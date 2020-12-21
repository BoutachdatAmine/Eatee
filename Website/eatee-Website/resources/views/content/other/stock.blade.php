@extends('template/BaseTemplate')

@section('content')
<script src="{{ url('js/stockScript.js')}}"></script>


<div class="container mx-auto px-6 py-8">
    <h3 class="text-gray-700 text-3xl font-medium">Stock</h3>
    <br>
    <a class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 mb-2" href="{{route('addIngredient')}}">Add ingredient</a>
    <br><br>

    <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2">Filter</label>
    <div class="flex rounded-md bg-white py-4 px-4 overflow-x-auto">
        <button class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3" id="all">Show All</button>
        <button class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3" id="products">Products</button>
        <button class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3" id="ingedients">Ingedients</button>
        <button class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3" id="pIngredients">Ingedients for Sandwiches</button>
    </div>
    <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2 my-6">Sort on ingedients type</label>
    <div class="flex rounded-md bg-white py-4 px-4 overflow-x-auto my-6">
        <button class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3" id="fruit">Fruit</button>
        <button class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3" id="vegetables">Vegetables</button>
        <button class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3" id="meat">Meat</button>
        <button class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3" id="carbs">Other</button>
    </div>
  
    <div id="ingredientsRows" class="-my-2 py-2 overflow-x-auto sm:-mx-6 sm:px-6 lg:-mx-8 lg:px-8 mt-2">
        <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2 my-6">Ingredients</label>
        <div class="align-middle inline-block min-w-full shadow overflow-hidden sm:rounded-lg border-b border-gray-200">
            <table class="min-w-full">
                <thead>
                    <tr>
                        <th class="px-6 py-3 border-b border-gray-200 bg-gray-100 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">id</th>
                        <th class="px-6 py-3 border-b border-gray-200 bg-gray-100 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">Name</th>
                        <th class="px-6 py-3 border-b border-gray-200 bg-gray-100 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">Quantity</th>
                        <th class="px-6 py-3 border-b border-gray-200 bg-gray-100 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">Type</th>
                        <th class="px-6 py-3 border-b border-gray-200 bg-gray-100"></th>
                    </tr>
                </thead>
                <tbody class="bg-white">
                    <!-- classes vegetableRows - fruitRows - meatRows - carbsRows -->
                    @foreach($ingredients as $ingredient)
                    <tr class="<?php switch ($ingredient->categoryId) {
                                    case 4:
                                        echo (' vegetableRows ');
                                        break;
                                    case 5:
                                        echo (' fruitRows ');
                                        break;
                                    case 6:
                                        echo (' meatRows ');
                                        break;
                                    case 9:
                                    case 1:
                                        echo (' carbsRows ');
                                        break;
                                }
                                if ($ingredient->sandwichIngredient) {
                                    echo (' sandwish ');
                                } ?> ">

                        <td class=" px-6 py-4 whitespace-no-wrap border-b border-gray-200">
                            <div class="flex items-center">
                                <div class="ml-4">
                                    <div class="text-sm leading-5 font-medium text-gray-900">{{$ingredient->ingredientId}}</div>
                                </div>
                            </div>
                        </td>

                        <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
                            <div class="text-sm leading-5 text-gray-900">{{$ingredient->name}}</div>
                        </td>

                        <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
                            <span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800">{{$ingredient->quantityStock}}</span>
                        </td>

                        <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
                            <div class="text-sm leading-5 text-gray-900">
                                <?php foreach ($categories as $category) {
                                    if ($ingredient->categoryId == $category->id) {
                                        echo ($category->name);
                                    }
                                }
                                if ($ingredient->sandwichIngredient) {
                                    echo (' -- Only for custom sandwishes');
                                } ?>
                            </div>
                        </td>

                        <td class="px-6 py-4 whitespace-no-wrap text-right border-b border-gray-200 text-sm leading-5 font-medium">
                            <button class="editModal-open px-6 py-3 bg-blue-600 rounded-md text-white font-medium tracking-wide hover:bg-blue-500">Edit</button>
                            <button class="removeModal-open px-6 py-3 bg-red-600 rounded-md text-white font-medium tracking-wide hover:bg-red-500">Remove stock</button>
                            <button class="addModal-open px-6 py-3 bg-green-600 rounded-md text-white font-medium tracking-wide hover:bg-green-500">Add stock</button>
                        </td>
                    </tr>
                    @endforeach
                </tbody>
            </table>
        </div>
    </div>
    
    <div id="productRows" class="-my-2 py-2 overflow-x-auto sm:-mx-6 sm:px-6 lg:-mx-8 lg:px-8 mt-2">
        <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2 my-6">Products</label>
        <div class="align-middle inline-block min-w-full shadow overflow-hidden sm:rounded-lg border-b border-gray-200">
            <table class="min-w-full">
                <thead>
                    <tr>
                        <th class="px-6 py-3 border-b border-gray-200 bg-gray-100 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">id</th>
                        <th class="px-6 py-3 border-b border-gray-200 bg-gray-100 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">Name</th>
                        <th class="px-6 py-3 border-b border-gray-200 bg-gray-100 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">Quantity</th>
                        <th class="px-6 py-3 border-b border-gray-200 bg-gray-100 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">Type</th>
                        <th class="px-6 py-3 border-b border-gray-200 bg-gray-100"></th>
                    </tr>
                </thead>
                <tbody class="bg-white">
                    @foreach($products as $product)
                    <tr>
                        <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
                            <div class="flex items-center">
                                <div class="ml-4">
                                    <div class="text-sm leading-5 font-medium text-gray-900">{{$product->productId}}</div>
                                </div>
                            </div>
                        </td>

                        <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
                            <div class="text-sm leading-5 text-gray-900">{{$product->name}}</div>
                        </td>

                        <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
                            <span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800">{{$product->quantity}}</span>
                        </td>

                        <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
                            <div class="text-sm leading-5 text-gray-900"><?php foreach ($categories as $category) {
                                                                                if ($product->categoryId == $category->id) {
                                                                                    echo ($category->name);
                                                                                }
                                                                            } ?></div>
                        </td>

                        <td class="px-6 py-4 whitespace-no-wrap text-right border-b border-gray-200 text-sm leading-5 font-medium">
                            <button class="editModal-open px-6 py-3 bg-blue-600 rounded-md text-white font-medium tracking-wide hover:bg-blue-500">Edit</button>
                        </td>
                    </tr>
                    @endforeach
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- MODALS -->

<div class="addModal opacity-0 pointer-events-none fixed w-full h-full top-0 left-0 flex items-center justify-center">
    <div class="addModal-overlay absolute w-full h-full bg-gray-900 opacity-50"></div>
    <div class="addModal-container bg-white w-11/12 md:max-w-md mx-auto rounded shadow-lg z-50 overflow-y-auto">
        <div class="addModal-close absolute top-0 right-0 cursor-pointer flex flex-col items-center mt-4 mr-4 text-white text-sm z-50">
            <svg class="fill-current text-white" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18">
                <path d="M14.53 4.53l-1.06-1.06L9 7.94 4.53 3.47 3.47 4.53 7.94 9l-4.47 4.47 1.06 1.06L9 10.06l4.47 4.47 1.06-1.06L10.06 9z"></path>
            </svg>
            <span class="text-sm">(Esc)</span>
        </div>
        <div class="addModal-content py-4 text-left px-6">
            <div class="flex justify-between items-center pb-3">
                <p class="text-2xl font-bold">Add stock</p>
                <div class="addModal-close cursor-pointer z-50">
                    <svg class="fill-current text-black" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18">
                        <path d="M14.53 4.53l-1.06-1.06L9 7.94 4.53 3.47 3.47 4.53 7.94 9l-4.47 4.47 1.06 1.06L9 10.06l4.47 4.47 1.06-1.06L10.06 9z"></path>
                    </svg>
                </div>
            </div>
            <form class="mt-6" action="{{route('addStock')}}" method="POST">
                @csrf
                <input type="hidden" name="inputId" id="inputFieldId" value="1">
                <label for="amount" class="block text-xs font-semibold text-gray-600 uppercase">Amount</label>
                <input id="amount" type="number" name="inputAmount" placeholder="1" class="block w-full p-3 mt-2 text-gray-700 bg-gray-200 appearance-none focus:outline-none focus:bg-gray-300 focus:shadow-inner" />
                <div class="flex justify-end pt-2">
                    <button type="submit" class="addModal-close px-4 bg-green-500 p-3 rounded-lg text-white hover:bg-green-400">Add stock</button>
                    <button class="addModal-close px-4 bg-indigo-500 p-3 rounded-lg text-white hover:bg-indigo-400">Close</button>
                </div>
            </form>

        </div>
    </div>
</div>

<div class="removeModal opacity-0 pointer-events-none fixed w-full h-full top-0 left-0 flex items-center justify-center">
    <div class="removeModal-overlay absolute w-full h-full bg-gray-900 opacity-50"></div>
    <div class="removeModal-container bg-white w-11/12 md:max-w-md mx-auto rounded shadow-lg z-50 overflow-y-auto">
        <div class="removeModal-close absolute top-0 right-0 cursor-pointer flex flex-col items-center mt-4 mr-4 text-white text-sm z-50">
            <svg class="fill-current text-white" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18">
                <path d="M14.53 4.53l-1.06-1.06L9 7.94 4.53 3.47 3.47 4.53 7.94 9l-4.47 4.47 1.06 1.06L9 10.06l4.47 4.47 1.06-1.06L10.06 9z"></path>
            </svg>
            <span class="text-sm">(Esc)</span>
        </div>
        <div class="removeModal-content py-4 text-left px-6">
            <div class="flex justify-between items-center pb-3">
                <p class="text-2xl font-bold">Remove stock</p>
                <div class="removeModal-close cursor-pointer z-50">
                    <svg class="fill-current text-black" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18">
                        <path d="M14.53 4.53l-1.06-1.06L9 7.94 4.53 3.47 3.47 4.53 7.94 9l-4.47 4.47 1.06 1.06L9 10.06l4.47 4.47 1.06-1.06L10.06 9z"></path>
                    </svg>
                </div>
            </div>
            <form class="mt-6" action="{{route('removeStock')}}" method="POST">
                @csrf
                <input type="hidden" name="inputId2" id="inputFieldId2" value="1">
                <label for="amount" class="block text-xs font-semibold text-gray-600 uppercase">Amount</label>
                <input id="amount" type="number" name="inputAmount2" placeholder="1" class="block w-full p-3 mt-2 text-gray-700 bg-gray-200 appearance-none focus:outline-none focus:bg-gray-300 focus:shadow-inner" />
                <div class="flex justify-end pt-2">
                    <button type="submit" class="removeModal-close px-4 bg-green-500 p-3 rounded-lg text-white hover:bg-green-400">Remove stock</button>
                    <button type="button" class="removeModal-close px-4 bg-indigo-500 p-3 rounded-lg text-white hover:bg-indigo-400">Close</button>
                </div>
            </form>

        </div>
    </div>
</div>

<div class="editModal opacity-0 pointer-events-none fixed w-full h-full top-0 left-0 flex items-center justify-center">
    <div class="editModal-overlay absolute w-full h-full bg-gray-900 opacity-50"></div>
    <div class="editModal-container bg-white w-11/12 md:max-w-md mx-auto rounded shadow-lg z-50 overflow-y-auto">
        <div class="editModal-close absolute top-0 right-0 cursor-pointer flex flex-col items-center mt-4 mr-4 text-white text-sm z-50">
            <svg class="fill-current text-white" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18">
                <path d="M14.53 4.53l-1.06-1.06L9 7.94 4.53 3.47 3.47 4.53 7.94 9l-4.47 4.47 1.06 1.06L9 10.06l4.47 4.47 1.06-1.06L10.06 9z"></path>
            </svg>
            <span class="text-sm">(Esc)</span>
        </div>
        <div class="editModal-content py-4 text-left px-6">
            <div class="flex justify-between items-center pb-3">
                <p id="title" class="text-2xl font-bold">Edit product/ingredient</p>
                <div class="editModal-close cursor-pointer z-50">
                    <svg class="fill-current text-black" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18">
                        <path d="M14.53 4.53l-1.06-1.06L9 7.94 4.53 3.47 3.47 4.53 7.94 9l-4.47 4.47 1.06 1.06L9 10.06l4.47 4.47 1.06-1.06L10.06 9z"></path>
                    </svg>
                </div>
            </div>
            <form class="mt-6" action="{{route('editStock')}}" method="POST">
                @csrf
                <input type="hidden" name="inputId3" id="inputFieldId3" value="1">
                <input type="hidden" name="inputType3" id="inputFieldType3" value="1">

                <label for="name" class="block text-xs font-semibold text-gray-600 uppercase">Name</label>
                <input id="name" type="text" name="inputName" placeholder="name" class="block w-full p-3 mt-2 text-gray-700 bg-gray-200 appearance-none focus:outline-none focus:bg-gray-300 focus:shadow-inner" />

                <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2" for="type">Type</label>
                <div id="bodyModal">
                </div>
                </select>
                <div class="flex justify-end pt-2">
                    <button type="submit" class="editModal-close px-4 bg-green-500 p-3 rounded-lg text-white hover:bg-green-400">Edit</button>
                    <button class="editModal-close px-4 bg-indigo-500 p-3 rounded-lg text-white hover:bg-indigo-400">Close</button>
                </div>
            </form>
        </div>
    </div>
</div>
@endsection