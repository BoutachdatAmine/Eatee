@extends('template/BaseTemplate')

@section('content')
<div class="container mx-auto px-6 py-8">
    <h3 class="text-gray-700 text-3xl font-medium">Active Menus</h3>
    <br><br>
    <a href="{{route('addProduct')}}" class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500">Add Product</a>
    <br>
    <br>
    <br>
    <!-- FROM DATABASE -->
    @foreach($categories as $category)
    @if($category->name!="None" && $category->name!="Vegetable" && $category->name!="Meat" && $category->name!="Fruit" && $category->name!="Other")
    <div class="p-6 bg-white rounded-md shadow-md my-6">
        <h2 class="text-4xl font-semibold text-black">{{$category->name}}</h2>

        @foreach($products as $product)
        @if($category->id == $product->categoryId)

        <div class="my-3">
            <div class="grid gap-4 grid-cols-4 items-end">
                <h3 class="font-semibold text-black">{{$product->name}}</h3>
                <p>
                    Price: {{ number_format($product->price, 2) }}
                    @if($product->promotionActive > 0) 
                        <span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800">Promotion active</span>
                    @else 
                        <span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-red-100 text-red-800">No promotion active</span>
                    @endif
                </p>
                <div class="col-span-2 flex flex-grow">
                    <a class="flex-grow px-6 py-3 bg-red-600 rounded-md text-white font-medium tracking-wide hover:bg-red-500 ml-3 cursor-pointer">Delete</a>
                    <a href="{{route('editProductPage',$product->productId)}}" class="flex-grow px-6 py-3 bg-green-600 rounded-md text-white font-medium tracking-wide hover:bg-green-500 ml-3 cursor-pointer">Edit</a>
                    <a href="{{route('proposeProduct',$product->productId)}}" class="flex-grow px-6 py-3 bg-blue-600 rounded-md text-white font-medium tracking-wide hover:bg-blue-500 ml-3 cursor-pointer">Propose for next week</a>
                </div>
            </div>
            
            <p class="text-gray-600 dark:text-gray-400">
            
                @foreach($product->ingredients as $pIngredient)
                    <span>|</span>

                    @foreach($ingredients as $ingredient)
                        @if($pIngredient->id == $ingredient->ingredientId)
                            <span>{{ $pIngredient->amount }}x {{ $ingredient->name }}<span>
                        @endif
                    @endforeach
                @endforeach
                <span>|</span>
            </p>
        </div>

        @endif
        @endforeach
    </div>
    @endif
    @endforeach
</div>
@endsection