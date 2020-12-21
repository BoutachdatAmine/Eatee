<?php

namespace App\Http\Controllers;

use Carbon\Carbon;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http;

class OrderController extends Controller
{
    private $url = "10.3.50.23:69/";

    function showPage($givenDate)
    {
        // API
        $orders = Http::get($this->url . 'orders/?orderedFor=' . $givenDate);
        $orders = json_decode($orders);

        $date = date('l d/M/Y', strtotime($givenDate)) . ($givenDate == date('Y-m-d') ? " (today)":"");

        if(count($orders) == 0)
            return view('/content/order/orders')->with('date', $date)->with('orders', $orders)->withErrors('There are no orders on this date!');
        return view('/content/order/orders')->with('date', $date)->with('orders', $orders);
    }

    function changeDate(Request $request)
    {
        $request->validate([
            'inputDate' => 'required'
        ]);

        return redirect()->route('orders')->with('date', $request->input('inputDate'));
    }
    function showOrder($id)
    {
        $order = Http::get($this->url . 'orders/' . $id);
        $order = json_decode($order);

        $userId = $order->customerId;
        $account = Http::get($this->url . 'customers/' . $userId);
        $account = json_decode($account);

        $products = array();
        foreach ($order->productIds as $productId) {
            $product = Http::get($this->url . 'products/' . $productId);
            $product = json_decode($product);
            array_push($products, $product);
        }

        $sandwishes = array();
        foreach ($order->sandwichIds as $customId) {
            $sandwish = Http::get($this->url . 'custom/' . $customId);
            $sandwish = json_decode($sandwish);
            array_push($sandwishes, $sandwish);
        }

        $ingredients = Http::get($this->url . 'ingredients/');
        $ingredients = json_decode($ingredients);

        if ($order == null || $account == null || $products == null || $sandwishes == null || $ingredients == null) {
            return redirect()->route('home')->withErrors('Error with extracting date from database');
        } else {
            return view('/content/order/checkOrder')->with('order', $order)->with('account', $account)->with('products', $products)->with('sandwishes', $sandwishes)->with('ingredients', $ingredients);
        }
    }
    function finalise($id)
    {
        $body = array('status' => 'READY_FOR_PICKUP');
        $response = Http::patch($this->url . 'orders/' . $id, $body);
        // $response->throw();

        if ($response->successful()) {
            return redirect()->route('orders')->with('succes', ['text' => 'The order is ready to be picked up']);
        } else {
            return redirect()->route('orders')->withErrors('Request failed');
        }
    }
}
