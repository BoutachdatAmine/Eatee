@extends('template/BaseTemplate')

@section('content')
<div class="container mx-auto px-6 py-8">
  <h3 class="text-gray-700 text-3xl font-medium">Add product to Proposed meals</h3>
  <br>
  <a href="{{route('addProduct')}}" class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500">Create new Product</a>


  <!-- FROM DATABASE -->
  @foreach($categories as $category)
    @if($category->name!="None" && $category->name!="Vegetable" && $category->name!="Meat" && $category->name!="Fruit" && $category->name!="Other")
    <div class="p-6 bg-white rounded-md shadow-md my-6">
        <h2 class="text-4xl font-semibold text-black">{{$category->name}}</h2>

        @foreach($products as $product)
        @if($category->id == $product->categoryId)

        <div class="my-3">
            <div class="grid gap-4 grid-cols-3 items-end">
                <h3 class="font-semibold text-black">{{$product->name}}</h3>
                <p>
                    Price: {{ number_format($product->price, 2) }}
                    @if($product->promotionActive > 0) 
                        <span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800">Promotion active</span>
                    @else 
                        <span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-red-100 text-red-800">No promotion active</span>
                    @endif
                </p>
                <div class="flex flex-grow">
                  <a href="{{route('proposeProduct',$product->productId)}}" class="px-6 py-3 bg-green-600 rounded-md text-white font-medium tracking-wide hover:bg-green-500">Add</a>
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

</div>
@endsection