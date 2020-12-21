<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http;

class StockController extends Controller
{
    private $url = "10.3.50.23:69/";

    function showPage()
    {
        $products = Http::get($this->url . 'products/');
        $products = json_decode($products);

        $ingredients = Http::get($this->url . 'ingredients/');
        $ingredients = json_decode($ingredients);

        $category = Http::get($this->url . 'categories/');
        $category = json_decode($category);

        if ($products == null || $ingredients == null || $category == null) {
            return redirect()->route('home')->withErrors('Error with extracting date from database');
        } else {
            return view('/content/other/stock', ['ingredients' => $ingredients])->with('products', $products)->with('categories', $category);
        }
    }

    function addStock(Request $request)
    {
        $request->validate([
            'inputAmount' => 'required|min:1',
        ]);
        $id = $request->input('inputId');
        $amount = $request->input('inputAmount');



        $ingredient = Http::get($this->url . 'ingredients/' . $id);
        $ingredient = json_decode($ingredient);

        if ($ingredients == null) {
            return redirect()->route('home')->withErrors('Error with extracting date from database');
        } else {


            $amount = $amount + $ingredient->quantityStock;

            $body = array('quantity' => $amount);
            $response = Http::patch($this->url . 'ingredients/' . $id, $body);

            //$response->throw();
            if ($response->successful()) {
                return redirect()->route('stock')->with('succes', ['text' => 'Stock added']);
            } else {
                return redirect()->route('stock')->withErrors('Request failed');
            }
        }
    }
    function removeStock(Request $request)
    {
        $request->validate([
            'inputAmount2' => 'required',
        ]);
        $id = $request->input('inputId2');
        $amount = $request->input('inputAmount2');

        $ingredient = Http::get($this->url . 'ingredients/' . $id);
        $ingredient = json_decode($ingredient);

        if ($ingredient == null) {
            return redirect()->route('home')->withErrors('Error with extracting date from database');
        } else {
            $amount = $ingredient->quantityStock - $amount;
            if ($amount >= 0) {
                $body = array('quantity' => $amount);
                $response = Http::patch($this->url . 'ingredients/' . $id, $body);

                //$response->throw();
                if ($response->successful()) {
                    return redirect()->route('stock')->with('succes', ['text' => 'Stock removed']);
                } else {
                    return redirect()->route('stock')->withErrors('Request failed');
                }
            } else {
                return back()->withErrors('You want to remove more than you currently have');
            }
        }
    }

    function editStock(Request $request)
    {
        $request->validate([
            'inputName' => 'required',
        ]);
        $type = $request->input('inputType3');
        $id = $request->input('inputId3');
        $category = $request->input('inputType');


        if ($type == "product") {
            $body = array('categoryId' => $category, 'name' => $request->input('inputName'));

            $response = Http::patch($this->url . 'products/' . $id, $body);
            //$response->throw();

            if ($response->successful()) {
                return redirect()->route('stock')->with('succes', ['text' => 'product information changed']);
            } else {
                return redirect()->route('stock')->withErrors('Request failed');
            }
        } else {
            $body = array('categoryId' => $category, 'name' => $request->input('inputName'));

            $response = Http::patch($this->url . 'ingredients/' . $id, $body);
            //$response->throw();

            if ($response->successful()) {
                return redirect()->route('stock')->with('succes', ['text' => 'Ingredient information changed']);
            } else {
                return redirect()->route('stock')->withErrors('Request failed');
            }
        }
    }
}
