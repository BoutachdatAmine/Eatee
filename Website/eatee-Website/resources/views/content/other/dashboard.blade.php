@extends('template/BaseTemplate')

@section('content')
<script src="{{ url('js/dashboardScript.js')}}"></script>

<div class="container mx-auto px-6 py-8">
    <h3 class="text-gray-700 text-3xl font-medium">Dashboard &#8212; Overview of allergies, categories & promotions</h3>
    <br>

    <button id="createPromotionModal-open" class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 mb-2">Create promotion</button>
    <button id="createCategoryModal-open" class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 mb-2">Create category</button>
    <button id="createAllergyModal-open" class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 mb-2">Create allergy</button>
    <br><br>

    <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2">Show</label>
    <div class="flex rounded-md bg-white py-4 px-4 overflow-x-auto">
        <button class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3" id="allBtn">See all</button>
        <button class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3" id="promotionsBtn">Promotions</button>
        <button class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3" id="categoriesBtn">Categories</button>
        <button class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3" id="allergiesBtn">Allergies</button>
    </div>

    <div id="promotionsTable" class="-my-2 py-2 overflow-x-auto sm:-mx-6 sm:px-6 lg:-mx-8 lg:px-8">
        <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2 mt-5">Promotions</label>
        <div class="align-middle inline-block min-w-full shadow overflow-hidden sm:rounded-lg border-b border-gray-200">
            <table class="min-w-full">
                <thead>
                    <tr>
                        <th class="px-6 py-3 border-b border-gray-200 bg-gray-100 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">id</th>
                        <th class="px-6 py-3 border-b border-gray-200 bg-gray-100 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">productId</th>
                        <th class="px-6 py-3 border-b border-gray-200 bg-gray-100 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">Product name</th>
                        <th class="px-6 py-3 border-b border-gray-200 bg-gray-100 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">Status</th>
                        <th class="px-6 py-3 border-b border-gray-200 bg-gray-100 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">value</th>
                        <th class="px-6 py-3 border-b border-gray-200 bg-gray-100 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">Current price</th>
                        <th class="px-6 py-3 border-b border-gray-200 bg-gray-100 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">valid From</th>
                        <th class="px-6 py-3 border-b border-gray-200 bg-gray-100 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">valid To</th>
                        <th class="px-6 py-3 border-b border-gray-200 bg-gray-100"></th>
                    </tr>
                </thead>
                <tbody class="bg-white">
                    @foreach($promotions as $promotion)
                    <tr>

                        <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
                            <div class="flex items-center">
                                <div class="ml-4">
                                    <div class="text-sm leading-5 font-medium text-gray-900">{{$promotion->id}}</div>
                                </div>
                            </div>
                        </td>

                        <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
                            <div class="text-sm leading-5 text-gray-900">{{$promotion->productId}}</div>
                        </td>

                        <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
                            <div class="text-sm leading-5 text-gray-900">
                                @foreach($products as $product)
                                @if($product->productId == $promotion->productId)
                                {{$product->name}}
                                @endif
                                @endforeach
                            </div>
                        </td>

                        <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">

                            <?php

                            $start = $promotion->validFrom;
                            $end = $promotion->validTo;
                            if (Carbon\Carbon::now()->between($start, $end)) {
                                echo ('<span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800"> Active</span>');
                            } else {
                                echo ('<span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-red-100 text-red-800">Not Active</span>');
                            }
                            ?>

                        </td>

                        <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
                            <span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full">{{ number_format($promotion->value, 2) }}</span>
                        </td>

                        <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
                            <div class="text-sm leading-5 text-gray-900">
                                @foreach($products as $product)
                                @if($product->productId == $promotion->productId)
                                {{ number_format($product->price, 2) }}
                                @endif
                                @endforeach
                            </div>
                        </td>

                        <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
                            <span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full">{{$promotion->validFrom}}</span>
                        </td>

                        <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
                            <span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full">{{$promotion->validTo}}</span>
                        </td>


                        <td class="px-6 py-4 whitespace-no-wrap text-right border-b border-gray-200 text-sm leading-5 font-medium">
                            <button class="editPromotionModal-open px-6 py-3 bg-blue-600 rounded-md text-white font-medium tracking-wide hover:bg-blue-500">Edit</button>
                            <button class="deletePromotionModal-open px-6 py-3 bg-red-600 rounded-md text-white font-medium tracking-wide hover:bg-red-500">Delete</button>
                        </td>
                    </tr>
                    @endforeach
                </tbody>
            </table>
        </div>
    </div>

    <div id="allergiesTable" class="-my-2 py-2 overflow-x-auto sm:-mx-6 sm:px-6 lg:-mx-8 lg:px-8">
        <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2 mt-5">Allergies</label>
        <div class="align-middle inline-block min-w-full shadow overflow-hidden sm:rounded-lg border-b border-gray-200">
            <table class="min-w-full">
                <thead>
                    <tr>
                        <th class="px-6 py-3 border-b border-gray-200 bg-gray-100 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">id</th>
                        <th class="px-6 py-3 border-b border-gray-200 bg-gray-100 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">name</th>
                        <th class="px-6 py-3 border-b border-gray-200 bg-gray-100 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">How frequently used</th>
                        <th class="px-6 py-3 border-b border-gray-200 bg-gray-100"></th>
                    </tr>
                </thead>
                <tbody class="bg-white">
                    @foreach($allergies as $allergy)
                    <tr>
                        <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
                            <div class="flex items-center">
                                <div class="ml-4">
                                    <div class="text-sm leading-5 font-medium text-gray-900">{{$allergy->id}}</div>
                                </div>
                            </div>
                        </td>

                        <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
                            <div class="text-sm leading-5 text-gray-900">{{$allergy->name}}</div>
                        </td>

                        <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
                            <div class="text-sm leading-5 text-gray-900">
                                <?php
                                $count = 0;
                                foreach ($products as $product) {
                                    foreach ($product->allergies as $allergyId) {
                                        if ($allergyId == $allergy->id) {
                                            $count++;
                                        }
                                    }
                                }
                                echo ($count);
                                ?>
                            </div>
                        </td>

                        <td class="px-6 py-4 whitespace-no-wrap text-right border-b border-gray-200 text-sm leading-5 font-medium">
                            <button class="editCategoriesAllergiesModal-open px-6 py-3 bg-blue-600 rounded-md text-white font-medium tracking-wide hover:bg-blue-500">Edit</button>
                            <button class="deleteAllergyModal-open px-6 py-3 bg-red-600 rounded-md text-white font-medium tracking-wide hover:bg-red-500">Delete</button>
                        </td>
                    </tr>
                    @endforeach
                </tbody>
            </table>
        </div>
    </div>

    <div id="categoriesTable" class="-my-2 py-2 overflow-x-auto sm:-mx-6 sm:px-6 lg:-mx-8 lg:px-8">
        <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2 mt-5">Categories</label>
        <div class="align-middle inline-block min-w-full shadow overflow-hidden sm:rounded-lg border-b border-gray-200">
            <table class="min-w-full">
                <thead>
                    <tr>
                        <th class="px-6 py-3 border-b border-gray-200 bg-gray-100 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">id</th>
                        <th class="px-6 py-3 border-b border-gray-200 bg-gray-100 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">name</th>
                        <th class="px-6 py-3 border-b border-gray-200 bg-gray-100 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">How frequently used</th>
                        <th class="px-6 py-3 border-b border-gray-200 bg-gray-100"></th>
                    </tr>
                </thead>
                <tbody class="bg-white">
                    @foreach($categories as $category)
                    @if($category->name!='Fruit'&&$category->name!='Meat'&&$category->name!='Other'&&$category->name!='Vegetable')
                    <tr>
                        <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
                            <div class="flex items-center">
                                <div class="ml-4">
                                    <div class="text-sm leading-5 font-medium text-gray-900">{{$category->id}}</div>
                                </div>
                            </div>
                        </td>

                        <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
                            <div class="text-sm leading-5 text-gray-900">{{$category->name}}</div>
                        </td>

                        <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
                            <div class="text-sm leading-5 text-gray-900">
                                <?php
                                $count = 0;
                                foreach ($products as $product) {
                                    if ($product->categoryId == $category->id) {
                                        $count++;
                                    }
                                }
                                echo ($count);
                                ?>
                            </div>
                        </td>

                        <td class="px-6 py-4 whitespace-no-wrap text-right border-b border-gray-200 text-sm leading-5 font-medium">
                            <button class="editCategoriesAllergiesModal-open px-6 py-3 bg-blue-600 rounded-md text-white font-medium tracking-wide hover:bg-blue-500">Edit</button>
                            <button class="deleteCategoryModal-open px-6 py-3 bg-red-600 rounded-md text-white font-medium tracking-wide hover:bg-red-500">Delete</button>
                        </td>
                    </tr>
                    @endif
                    @endforeach
                </tbody>
            </table>
        </div>
    </div>
</div>



<!-- MODALS -->

<div id="createAllergyModal" class="opacity-0 pointer-events-none fixed w-full h-full top-0 left-0 flex items-center justify-center">
    <div id="createAllergyModal-overlay" class="absolute w-full h-full bg-gray-900 opacity-50"></div>
    <div id="createAllergyModal-container" class="bg-white w-11/12 md:max-w-md mx-auto rounded shadow-lg z-50 overflow-y-auto">
        <div class="createAllergyModal-close absolute top-0 right-0 cursor-pointer flex flex-col items-center mt-4 mr-4 text-white text-sm z-50">
            <svg class="fill-current text-white" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18">
                <path d="M14.53 4.53l-1.06-1.06L9 7.94 4.53 3.47 3.47 4.53 7.94 9l-4.47 4.47 1.06 1.06L9 10.06l4.47 4.47 1.06-1.06L10.06 9z"></path>
            </svg>
            <span class="text-sm">(Esc)</span>
        </div>
        <div id="createAllergyModal-content" class="py-4 text-left px-6">
            <form class="mt-6" action="{{route('dashboardCreateAllergy')}}" method="POST">
                @csrf
                <div class="flex justify-between items-center pb-3">
                    <p class="text-2xl font-bold">Create new allergy</p>
                    <div class="createAllergyModal-close cursor-pointer z-50">
                        <svg class="fill-current text-black" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18">
                            <path d="M14.53 4.53l-1.06-1.06L9 7.94 4.53 3.47 3.47 4.53 7.94 9l-4.47 4.47 1.06 1.06L9 10.06l4.47 4.47 1.06-1.06L10.06 9z"></path>
                        </svg>
                    </div>
                </div>
                <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2" for="newAlle">Name</label>
                <input class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-grey-lighter rounded py-3 px-4" id="newAlle" name="inputName" type="text" placeholder="allergy">
                <br>
                <div class="flex justify-end pt-2">
                    <button type="submit" class="px-4 bg-green-500 p-3 rounded-lg text-white hover:bg-green-400">Create</button>
                    <button type='button' class="createAllergyModal-close px-4 bg-indigo-500 p-3 rounded-lg text-white hover:bg-indigo-400">Close</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div id="createCategoryModal" class="opacity-0 pointer-events-none fixed w-full h-full top-0 left-0 flex items-center justify-center">
    <div id="createCategoryModal-overlay" class="absolute w-full h-full bg-gray-900 opacity-50"></div>
    <div id="createCategoryModal-container" class="bg-white w-11/12 md:max-w-md mx-auto rounded shadow-lg z-50 overflow-y-auto">
        <div class="createCategoryModal-close absolute top-0 right-0 cursor-pointer flex flex-col items-center mt-4 mr-4 text-white text-sm z-50">
            <svg class="fill-current text-white" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18">
                <path d="M14.53 4.53l-1.06-1.06L9 7.94 4.53 3.47 3.47 4.53 7.94 9l-4.47 4.47 1.06 1.06L9 10.06l4.47 4.47 1.06-1.06L10.06 9z"></path>
            </svg>
            <span class="text-sm">(Esc)</span>
        </div>
        <div id="createCategoryModal-content" class="py-4 text-left px-6">
            <form class="mt-6" action="{{route('dashboardCreateCategory')}}" method="POST">
                @csrf
                <div class="flex justify-between items-center pb-3">
                    <p class="text-2xl font-bold">Create new category</p>
                    <div class="createCategoryModal-close cursor-pointer z-50">
                        <svg class="fill-current text-black" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18">
                            <path d="M14.53 4.53l-1.06-1.06L9 7.94 4.53 3.47 3.47 4.53 7.94 9l-4.47 4.47 1.06 1.06L9 10.06l4.47 4.47 1.06-1.06L10.06 9z"></path>
                        </svg>
                    </div>
                </div>
                <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2" for="newAlle">Name</label>
                <input class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-grey-lighter rounded py-3 px-4" id="newAlle" name="inputName" type="text" placeholder="category">
                <br>
                <div class="flex justify-end pt-2">
                    <button type="submit" class="px-4 bg-green-500 p-3 rounded-lg text-white hover:bg-green-400">Create</button>
                    <button type='button' class="createCategoryModal-close px-4 bg-indigo-500 p-3 rounded-lg text-white hover:bg-indigo-400">Close</button>
                </div>
            </form>
        </div>
    </div>
</div>


<div id="deleteAllergyModal" class="opacity-0 pointer-events-none fixed w-full h-full top-0 left-0 flex items-center justify-center">
    <div id="deleteAllergyModal-overlay" class="absolute w-full h-full bg-gray-900 opacity-50"></div>
    <div id="deleteAllergyModal-container" class="bg-white w-11/12 md:max-w-md mx-auto rounded shadow-lg z-50 overflow-y-auto">
        <div class="deleteAllergyModal-close absolute top-0 right-0 cursor-pointer flex flex-col items-center mt-4 mr-4 text-white text-sm z-50">
            <svg class="fill-current text-white" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18">
                <path d="M14.53 4.53l-1.06-1.06L9 7.94 4.53 3.47 3.47 4.53 7.94 9l-4.47 4.47 1.06 1.06L9 10.06l4.47 4.47 1.06-1.06L10.06 9z"></path>
            </svg>
            <span class="text-sm">(Esc)</span>
        </div>
        <div id="deleteAllergyModal-content" class="py-4 text-left px-6">
            <form class="mt-6" action="{{route('dashboardDeleteAllergy')}}" method="POST">
                @csrf
                <div class="flex justify-between items-center pb-3">
                    <p class="text-2xl font-bold">Are you sure? <br>You're about to delete an allergy!</p>
                    <div class="deleteAllergyModal-close cursor-pointer z-50">
                        <svg class="fill-current text-black" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18">
                            <path d="M14.53 4.53l-1.06-1.06L9 7.94 4.53 3.47 3.47 4.53 7.94 9l-4.47 4.47 1.06 1.06L9 10.06l4.47 4.47 1.06-1.06L10.06 9z"></path>
                        </svg>
                    </div>
                </div>
                <input type="hidden" name="inputId" id="allergyId" value="">
                <div class="flex justify-end pt-2">
                    <button type="submit" class="px-4 bg-green-500 p-3 rounded-lg text-white hover:bg-green-400">Yes</button>
                    <button type='button' class="deleteAllergyModal-close px-4 bg-indigo-500 p-3 rounded-lg text-white hover:bg-indigo-400">Close</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div id="deleteCategoryModal" class="opacity-0 pointer-events-none fixed w-full h-full top-0 left-0 flex items-center justify-center">
    <div id="deleteCategoryModal-overlay" class="absolute w-full h-full bg-gray-900 opacity-50"></div>
    <div id="deleteCategoryModal-container" class="bg-white w-11/12 md:max-w-md mx-auto rounded shadow-lg z-50 overflow-y-auto">
        <div class="deleteCategoryModal-close absolute top-0 right-0 cursor-pointer flex flex-col items-center mt-4 mr-4 text-white text-sm z-50">
            <svg class="fill-current text-white" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18">
                <path d="M14.53 4.53l-1.06-1.06L9 7.94 4.53 3.47 3.47 4.53 7.94 9l-4.47 4.47 1.06 1.06L9 10.06l4.47 4.47 1.06-1.06L10.06 9z"></path>
            </svg>
            <span class="text-sm">(Esc)</span>
        </div>
        <div id="deleteCategoryModal-content" class="py-4 text-left px-6">
            <form class="mt-6" action="{{route('dashboardDeleteCategory')}}" method="POST">
                @csrf
                <div class="flex justify-between items-center pb-3">
                    <p class="text-2xl font-bold">Are you sure? You're about to delete a category!</p>
                    <div class="deleteCategoryModal-close cursor-pointer z-50">
                        <svg class="fill-current text-black" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18">
                            <path d="M14.53 4.53l-1.06-1.06L9 7.94 4.53 3.47 3.47 4.53 7.94 9l-4.47 4.47 1.06 1.06L9 10.06l4.47 4.47 1.06-1.06L10.06 9z"></path>
                        </svg>
                    </div>
                </div>
                <input type="hidden" name="inputId" id="categoryId" value="">

                <div class="flex justify-end pt-2">
                    <button type="submit" class="px-4 bg-green-500 p-3 rounded-lg text-white hover:bg-green-400">Yes</button>
                    <button type='button' class="deleteCategoryModal-close px-4 bg-indigo-500 p-3 rounded-lg text-white hover:bg-indigo-400">Close</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div id="deletePromotionModal" class="opacity-0 pointer-events-none fixed w-full h-full top-0 left-0 flex items-center justify-center">
    <div id="deletePromotionModal-overlay" class="absolute w-full h-full bg-gray-900 opacity-50"></div>
    <div id="deletePromotionModal-container" class="bg-white w-11/12 md:max-w-md mx-auto rounded shadow-lg z-50 overflow-y-auto">
        <div class="deletePromotionModal-close absolute top-0 right-0 cursor-pointer flex flex-col items-center mt-4 mr-4 text-white text-sm z-50">
            <svg class="fill-current text-white" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18">
                <path d="M14.53 4.53l-1.06-1.06L9 7.94 4.53 3.47 3.47 4.53 7.94 9l-4.47 4.47 1.06 1.06L9 10.06l4.47 4.47 1.06-1.06L10.06 9z"></path>
            </svg>
            <span class="text-sm">(Esc)</span>
        </div>
        <div id="deletePromotionModal-content" class="py-4 text-left px-6">
            <form class="mt-6" action="{{route('dashboardDeletePromotion')}}" method="POST">
                @csrf
                <div class="flex justify-between items-center pb-3">
                    <p class="text-2xl font-bold">Are you sure? You're about to delete a promotion!</p>
                    <div class="deletePromotionModal-close cursor-pointer z-50">
                        <svg class="fill-current text-black" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18">
                            <path d="M14.53 4.53l-1.06-1.06L9 7.94 4.53 3.47 3.47 4.53 7.94 9l-4.47 4.47 1.06 1.06L9 10.06l4.47 4.47 1.06-1.06L10.06 9z"></path>
                        </svg>
                    </div>
                </div>
                <input type="hidden" name="inputId" id="promotionId" value="">

                <div class="flex justify-end pt-2">
                    <button type="submit" class="px-4 bg-green-500 p-3 rounded-lg text-white hover:bg-green-400">Yes</button>
                    <button type='button' class="deletePromotionModal-close px-4 bg-indigo-500 p-3 rounded-lg text-white hover:bg-indigo-400">Close</button>
                </div>
            </form>
        </div>
    </div>
</div>


<div id="createPromotionModal" class="opacity-0 pointer-events-none fixed w-full h-full top-0 left-0 flex items-center justify-center">
    <div id="createPromotionModal-overlay" class=" absolute w-full h-full bg-gray-900 opacity-50"></div>
    <div id="createPromotionModal-container" class=" bg-white w-11/12 md:max-w-md mx-auto rounded shadow-lg z-50 overflow-y-auto">
        <div class="createPromotionModal-close absolute top-0 right-0 cursor-pointer flex flex-col items-center mt-4 mr-4 text-white text-sm z-50">
            <svg class="fill-current text-white" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18">
                <path d="M14.53 4.53l-1.06-1.06L9 7.94 4.53 3.47 3.47 4.53 7.94 9l-4.47 4.47 1.06 1.06L9 10.06l4.47 4.47 1.06-1.06L10.06 9z"></path>
            </svg>
            <span class="text-sm">(Esc)</span>
        </div>


        <div id="createPromotionModal-content" class=" py-4 text-left px-6">
            <form class="mt-6" action="{{route('dashboardCreatePromotion')}}" method="POST">
                @csrf

                <div class="flex justify-between items-center pb-3">
                    <p class="text-2xl font-bold">Add promotion</p>
                    <div class="createPromotionModal-close cursor-pointer z-50">
                        <svg class="fill-current text-black" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18">
                            <path d="M14.53 4.53l-1.06-1.06L9 7.94 4.53 3.47 3.47 4.53 7.94 9l-4.47 4.47 1.06 1.06L9 10.06l4.47 4.47 1.06-1.06L10.06 9z"></path>
                        </svg>
                    </div>
                </div>

                <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2" for="inputStartDate">Start Date</label>
                <input class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-grey-lighter rounded py-3 px-4 mb-3" id="inputStartDate" name="inputStartDate" type="date">

                <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2" for="inputEndDate">End Date</label>
                <input class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-grey-lighter rounded py-3 px-4 mb-3" id="inputEndDate" name="inputEndDate" type="date">

                <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2" for="inputAmount">Amount</label>
                <input class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-grey-lighter rounded py-3 px-4 mb-3" step=".01" id="inputAmount" name="inputAmount" type="number">

                <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2" for="inputProduct">Product</label>
                <div id='body'>
                </div>
                <br>
                <div class="flex justify-end pt-2">
                    <button type="submit" class="px-4 bg-green-500 p-3 rounded-lg text-white hover:bg-green-400">Create Promotion</button>
                    <button type="button" class="createPromotionModal-close px-4 bg-indigo-500 p-3 rounded-lg text-white hover:bg-indigo-400">Close</button>
                </div>
            </form>
        </div>
    </div>
</div>



<div id="editCategoriesAllergiesModal" class="opacity-0 pointer-events-none fixed w-full h-full top-0 left-0 flex items-center justify-center">
    <div id="editCategoriesAllergiesModal-overlay" class="absolute w-full h-full bg-gray-900 opacity-50"></div>
    <div id="editCategoriesAllergiesModal-container" class="bg-white w-11/12 md:max-w-md mx-auto rounded shadow-lg z-50 overflow-y-auto">
        <div class="editCategoriesAllergiesModal-close absolute top-0 right-0 cursor-pointer flex flex-col items-center mt-4 mr-4 text-white text-sm z-50">
            <svg class="fill-current text-white" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18">
                <path d="M14.53 4.53l-1.06-1.06L9 7.94 4.53 3.47 3.47 4.53 7.94 9l-4.47 4.47 1.06 1.06L9 10.06l4.47 4.47 1.06-1.06L10.06 9z"></path>
            </svg>
            <span class="text-sm">(Esc)</span>
        </div>

        <div id="editCategoriesAllergiesModal-content" class="py-4 text-left px-6">
            <form class="mt-6" action="{{route('dashboardEditCategoriesAllergies')}}" method="POST">
                @csrf
                <div class="flex justify-between items-center pb-3">
                    <p class="text-2xl font-bold" id="title"></p>
                    <div class="editCategoriesAllergiesModal-close cursor-pointer z-50">
                        <svg class="fill-current text-black" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18">
                            <path d="M14.53 4.53l-1.06-1.06L9 7.94 4.53 3.47 3.47 4.53 7.94 9l-4.47 4.47 1.06 1.06L9 10.06l4.47 4.47 1.06-1.06L10.06 9z"></path>
                        </svg>
                    </div>
                </div>
                <input type="hidden" name="inputId" value="" id="inputFieldEditModal">
                <input type="hidden" name="inputType" value="" id="inputType">

                <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2" for="name">New Name</label>
                <input class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-grey-lighter rounded py-3 px-4" id="name" name="inputName" type="text">
                <br>
                <div class="flex justify-end pt-2">
                    <button type="submit" class="px-4 bg-green-500 p-3 rounded-lg text-white hover:bg-green-400">Update</button>
                    <button type='button' class="editCategoriesAllergiesModal-close px-4 bg-indigo-500 p-3 rounded-lg text-white hover:bg-indigo-400">Close</button>
                </div>
            </form>
        </div>
    </div>
</div>


<div id="editPromotionModal" class="opacity-0 pointer-events-none fixed w-full h-full top-0 left-0 flex items-center justify-center">
    <div id="editPromotionModal-overlay" class=" absolute w-full h-full bg-gray-900 opacity-50"></div>

    <div id="editPromotionModal-container" class=" bg-white w-11/12 md:max-w-md mx-auto rounded shadow-lg z-50 overflow-y-auto">

        <div class="editPromotionModal-close absolute top-0 right-0 cursor-pointer flex flex-col items-center mt-4 mr-4 text-white text-sm z-50">
            <svg class="fill-current text-white" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18">
                <path d="M14.53 4.53l-1.06-1.06L9 7.94 4.53 3.47 3.47 4.53 7.94 9l-4.47 4.47 1.06 1.06L9 10.06l4.47 4.47 1.06-1.06L10.06 9z"></path>
            </svg>
            <span class="text-sm">(Esc)</span>
        </div>


        <div id="editPromotionModal-content" class=" py-4 text-left px-6">
            <form class="mt-6" action="{{route('dashboardEditPromotion')}}" method="POST">
                @csrf
                <div class="flex justify-between items-center pb-3">
                    <p class="text-2xl font-bold" id="titleEdit">Edit promotion</p>
                    <div class="editPromotionModal-close cursor-pointer z-50">
                        <svg class="fill-current text-black" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18">
                            <path d="M14.53 4.53l-1.06-1.06L9 7.94 4.53 3.47 3.47 4.53 7.94 9l-4.47 4.47 1.06 1.06L9 10.06l4.47 4.47 1.06-1.06L10.06 9z"></path>
                        </svg>
                    </div>
                </div>
                <input type='hidden' name="inputId" id="promotionId2">
                <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2" for="inputStartDate2">Start Date</label>
                <input class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-grey-lighter rounded py-3 px-4 mb-3" id="inputStartDate2" name="inputStartDate" type="date">

                <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2" for="inputEndDate2">End Date</label>
                <input class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-grey-lighter rounded py-3 px-4 mb-3" id="inputEndDate2" name="inputEndDate" type="date">

                <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2" for="inputAmount2">Amount</label>
                <input class="appearance-none block w-full bg-grey-lighter text-grey-darker border border-grey-lighter rounded py-3 px-4 mb-3" step=".01" value="1" id="inputAmount2" name="inputAmount" type="number">
                <br>
                <div class="flex justify-end pt-2">
                    <button type="submit" class="px-4 bg-green-500 p-3 rounded-lg text-white hover:bg-green-400">Update</button>
                    <button type="button" class="editPromotionModal-close px-4 bg-indigo-500 p-3 rounded-lg text-white hover:bg-indigo-400">Close</button>
                </div>
            </form>
        </div>
    </div>
</div>
@endsection