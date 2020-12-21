<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Carbon\Carbon;
use Illuminate\Support\Facades\Http;

class CheckoutController extends Controller
{
    private $url = "10.3.50.23:69/";


    function showPage($givenDate)
    {
        $orders = Http::get($this->url . 'orders/?orderedFor=' . $givenDate);
        $orders = json_decode($orders);

        $date = date('l d/M/Y', strtotime($givenDate)) . ($givenDate == date('Y-m-d') ? " (today)":"");

        if(count($orders) == 0)
            return view('/content/order/checkout')->with('date', $date)->with('orders', $orders)->withErrors('There are no orders on this date!');
        return view('/content/order/checkout')->with('date', $date)->with('orders', $orders);
    }

    function checkout(Request $request)
    {
        $id = $request->input('inputId');

        $body = array('status' => 'DONE');
        $response = Http::patch($this->url . 'orders/' . $id, $body);
        //$response->throw();

        if ($response->successful()) {
            return redirect()->route('checkout')->with('succes', ['text' => 'Order has been checked out']);
        } else {
            return redirect()->route('checkout')->withErrors('Request failed');
        }
    }

    function changeDate(Request $request)
    {

        $request->validate([
            'inputDate' => 'required'
        ]);

        return redirect()->route('checkout')->with('date', $request->input('inputDate'));
    }
}
