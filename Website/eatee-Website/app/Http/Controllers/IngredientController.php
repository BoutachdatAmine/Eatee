<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http;

class IngredientController extends Controller
{
    private $url = "10.3.50.23:69/";

    function showPage()
    {
        return view('/content/ingredient/addIngredient');
    }
    function addIngredient(Request $request)
    {
        $request->validate([
            'inputName' => 'required',
            'inputstock' => 'required',
            'inputType' => 'required',
            'inputUse' => 'required'
        ]);
        $type = $request->input('inputType');

        switch ($type) {
            case 'vegetable':
                $type = 4;
                break;
            case 'meat':
                $type = 6;
                break;
            case 'fruit':
                $type = 5;
                break;
            case 'other':
                $type = 9;
                break;
        }
        $use = false;
        switch ($request->input('inputUse')) {
            case "products":
                $use = false;
                break;
            case "sandwishes":
                $use = true;
                break;
        }
        $body = array('name' => $request->input('inputName'), 'quantity' => $request->input('inputstock'), 'categoryId' => $type, 'sandwichIngredient' => $use);


        $response = Http::post($this->url . 'ingredients/', $body);
        //$response->throw();

        if ($response->successful()) {
            return redirect()->route('stock')->with('succes', ['text' => 'New Ingredient added']);
        } else {
            return redirect()->route('stock')->withErrors('Request failed');
        }
    }
}
