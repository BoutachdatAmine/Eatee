@extends('template/BaseTemplate')

@section('content')

<link href="CSS/statisticsCSS.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>


<script type="text/javascript" src="{{url('JS/statisticsScript.js')}}"></script>
<div class="container mx-auto px-6 py-8">
  <h3 class="text-gray-700 text-3xl font-medium">Statistics</h3>
  <div class="row">
    <div id="column1" class="column">
      <h4> All time statistics</h4>
      <div class="bg-gray-200 flex items-center justify-center px-5 py-5">
        <div class="w-full max-w-3xl">
          <div class="-mx-2 md:flex">
            <div class="w-full md:w-1/3 px-2">
              <div class="rounded-lg shadow-sm mb-4">
                <div class="rounded-lg bg-white shadow-lg md:shadow-xl relative overflow-hidden">
                  <div class="px-3 pt-8 pb-10 text-center relative z-10">
                    <h4 class="text-sm uppercase text-gray-500 leading-tight">Total</h4>
                    <h3 class="text-3xl text-gray-700 font-semibold leading-tight my-3">{{$stats->total}}</h3>
                  </div>
                  <div class="absolute bottom-0 inset-x-0">
                    <canvas id="chart1" height="70"></canvas>
                  </div>
                </div>
              </div>
            </div>
            <div class="w-full md:w-1/3 px-2">
              <div class="rounded-lg shadow-sm mb-4">
                <div class="rounded-lg bg-white shadow-lg md:shadow-xl relative overflow-hidden">
                  <div class="px-3 pt-8 pb-10 text-center relative z-10">
                    <h4 class="text-sm uppercase text-gray-500 leading-tight">Processing</h4>
                    <h3 class="text-3xl text-gray-700 font-semibold leading-tight my-3">{{$stats->processing}}</h3>
                  </div>
                  <div class="absolute bottom-0 inset-x-0">
                    <canvas id="chart2" height="70"></canvas>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="bg-gray-200 flex items-center justify-center px-5 py-5">
        <div class="w-full max-w-3xl">
          <div class="-mx-2 md:flex">
            <div class="w-full md:w-1/3 px-2">
              <div class="rounded-lg shadow-sm mb-4">
                <div class="rounded-lg bg-white shadow-lg md:shadow-xl relative overflow-hidden">
                  <div class="px-3 pt-8 pb-10 text-center relative z-10">
                    <h4 class="text-sm uppercase text-gray-500 leading-tight">Ready for pickup</h4>
                    <h3 class="text-3xl text-gray-700 font-semibold leading-tight my-3">{{$stats->ready}}</h3>
                  </div>
                  <div class="absolute bottom-0 inset-x-0">
                    <canvas id="chart1" height="70"></canvas>
                  </div>
                </div>
              </div>
            </div>
            <div class="w-full md:w-1/3 px-2">
              <div class="rounded-lg shadow-sm mb-4">
                <div class="rounded-lg bg-white shadow-lg md:shadow-xl relative overflow-hidden">
                  <div class="px-3 pt-8 pb-10 text-center relative z-10">
                    <h4 class="text-sm uppercase text-gray-500 leading-tight">Delivered</h4>
                    <h3 class="text-3xl text-gray-700 font-semibold leading-tight my-3">{{$stats->deliverd}}</h3>
                  </div>
                  <div class="absolute bottom-0 inset-x-0">
                    <canvas id="chart2" height="70"></canvas>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div id="column2" class="column">
    <h4> Most sold product</h4>
      <div class="chartjs-wrapper"><iframe class="chartjs-hidden-iframe" tabindex="-1" 
      style="display: block; overflow: hidden; border: 0px; margin: 0px; inset: 0px; height: 100%; width: 100%; position: absolute; pointer-events: none; z-index: -1;"></iframe>
        <canvas id="chartjs-4" class="chartjs" width="297" height="148" style="display: block; width: 297px; height: 148px;"></canvas>
       
      </div>
    </div>
  </div> <br>
  <div class="row">
    <div class="chartjs-wrapper"><iframe class="chartjs-hidden-iframe" tabindex="-1" style="display: block; overflow: hidden; border: 0px; margin: 0px; inset: 0px; height: 100%; width: 100%; position: absolute; pointer-events: none; z-index: -1;"></iframe>
      <canvas id="chartjs-1" class="chartjs" width="770" height="385" style="display: block; width: 770px; height: 385px;"></canvas>
    </div>
  </div>

</div>
@endsection