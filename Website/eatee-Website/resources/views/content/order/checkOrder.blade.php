@extends('template/BaseTemplate')

@section('content')
<script src="{{ url('js/checkOrderScript.js')}}"></script>

<div class="container mx-auto px-6 py-8">
  <h3 class="text-gray-700 text-3xl font-medium">Order {{$order->orderId}}</h3>

  <div class="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4 flex flex-col my-2">
    <p>Client name: {{$account->firstname}} {{$account->lastname}}</p>
    <p>Email client: {{$account->email}}</p>
    <p>Total Price: {{ number_format($order->total, 2) }}</p>
    <p>Ordered date: {{ date("m-d-Y H:i:s", strtotime($order->orderedAt)) }}</p>
    <p>For date: {{ date("m-d-Y H:i:s", strtotime($order->orderedFor)) }}</p>
    
    <a id="finish" href="{{route('finaliseOrder',['id'=>$order->orderId])}}" class="px-6 py-3 bg-blue-600 rounded-md text-white font-medium tracking-wide hover:bg-blue-500">Finish Order</a>
  </div>


  <div class="-my-2 py-2 overflow-x-auto sm:-mx-6 sm:px-6 lg:-mx-8 lg:px-8">
    <div class="align-middle inline-block min-w-full shadow overflow-hidden sm:rounded-lg border-b border-gray-200">
      <table class="min-w-full">
        <thead>
          <tr>
            <th class="px-6 py-3 border-b border-gray-200 bg-gray-100 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">Product name</th>
            <th class="px-6 py-3 border-b border-gray-200 bg-gray-100"></th>
          </tr>
        </thead>

        <tbody class="bg-white">
          <!-- All products from database-->
          @foreach($products as $product)
          <tr>
            <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
              <div class="text-sm leading-5 text-gray-900">{{$product->name}}</div>
            </td>
            <td class="px-6 py-4 whitespace-no-wrap text-right border-b border-gray-200 text-sm leading-5 font-medium">
              <button class="doneBtn px-6 py-3 bg-red-600 rounded-md text-white font-medium tracking-wide hover:bg-red-500">Not Done</button>
            </td>
          </tr>
          @endforeach
          @foreach($sandwishes as $sandwish)
          <tr>
            <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
              <div class="text-sm leading-5 text-gray-900">Custom Sandwich --
                @foreach($sandwish->ingredients as $ingrientId)
                @foreach($ingredients as $ingredient)
                @if($ingredient->ingredientId == $ingrientId)
                {{$ingredient->name}}
                @endif
                @endforeach
                @endforeach
              </div>
            </td>
            <td class="px-6 py-4 whitespace-no-wrap text-right border-b border-gray-200 text-sm leading-5 font-medium">
              <button class="doneBtn px-6 py-3 bg-red-600 rounded-md text-white font-medium tracking-wide hover:bg-red-500">Not Done</button>
            </td>
          </tr>
          @endforeach
        </tbody>
      </table>
    </div>

  </div>
</div>
@endsection