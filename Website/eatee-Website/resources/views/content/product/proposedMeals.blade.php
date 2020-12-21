@extends('template/BaseTemplate')

@section('content')
<script src="{{ url('js/proposeScript.js')}}"></script>


<div class="container mx-auto px-6 py-8">
  <h3 class="text-gray-700 text-3xl font-medium">Proposed Meals</h3>
  <br><br>
  <a href="{{route('addPropose')}}" class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500">Add a product</a>
  <br><br>
  <label class="block uppercase tracking-wide text-grey-darker text-xs font-bold mb-2">Sort on ...</label>
  <div class="flex rounded-md bg-white py-4 px-4 overflow-x-auto mb-6">
    <button class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3" id="mostLiked">Most liked</button>
    <button class="px-6 py-3 bg-gray-600 rounded-md text-white font-medium tracking-wide hover:bg-gray-500 ml-3" id="leastLiked">Least liked</button>
  </div>

  <div class="-my-2 py-2 overflow-x-auto sm:-mx-6 sm:px-6 lg:-mx-8 lg:px-8">
    <div class="align-middle inline-block min-w-full shadow overflow-hidden sm:rounded-lg border-b border-gray-200">
      <table class="min-w-full">
        <thead>
          <tr>
            <th class="px-6 py-3 border-b border-gray-200 bg-gray-100 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">Name</th>
            <th class="px-6 py-3 border-b border-gray-200 bg-gray-100 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">Ingredients</th>
            <th class="px-6 py-3 border-b border-gray-200 bg-gray-100 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">Likes</th>
            <th class="px-6 py-3 border-b border-gray-200 bg-gray-100"></th>
          </tr>
        </thead>

        <tbody class="bg-white" id="inputBody">
          @foreach($products as $product)
          <tr class="rows">
            <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
              <div class="text-sm leading-5 text-gray-900">{{$product->title}}</div>
            </td>
            <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
              <div class="text-sm leading-5 text-gray-900">{{$product->description}}</div>
            </td>
            <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
              <div class="text-sm leading-5 text-gray-900">{{$product->likes}}</div>
            </td>

            <td class="px-6 py-4 whitespace-no-wrap text-right border-b border-gray-200 text-sm leading-5 font-medium">
              <a href="{{route('deleteProposal',$product->id)}}" class="px-6 py-3 bg-red-600 rounded-md text-white font-medium tracking-wide hover:bg-red-500">Delete</a>
            </td>

          </tr>
          @endforeach
        </tbody>
      </table>
    </div>
  </div>
</div>
@endsection