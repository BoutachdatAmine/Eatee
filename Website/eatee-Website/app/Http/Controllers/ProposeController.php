<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http;

class ProposeController extends Controller
{
    private $url = "10.3.50.23:69/";

    function showPage()
    {
        $products = Http::get($this->url . 'posts/');
        $products = json_decode($products);

        return view('/content/product/proposedMeals')->with('products', $products);
    }

    function showAddPage()
    {
        $products = Http::get($this->url . 'products/');
        $products = json_decode($products);

        $categories = Http::get($this->url . 'categories/');
        $categories = json_decode($categories);

        $ingredients = Http::get($this->url . 'ingredients/');
        $ingredients = json_decode($ingredients);

        if ($ingredients == null || $categories == null || $products == null) {
            return redirect()->route('home')->withErrors('Error with extracting date from database');
        } else {
            return view('/content/product/addProductToPropose')->with('products', $products)->with('categories', $categories)->with('ingredients', $ingredients);
        }
    }

    function addAnProduct($id)
    {

        $product = Http::get($this->url . 'products/' . $id);
        $product = json_decode($product);

        if ($product == null) {
            return redirect()->route('home')->withErrors('Error with extracting date from database');
        } else {
            $ingredients = array();

            foreach ($product->ingredients as $ingredientId) {
                $ingredient = Http::get($this->url . 'ingredients/' . $ingredientId->id);
                $ingredient = json_decode($ingredient);
                if ($ingredient == null) {
                    return redirect()->route('home')->withErrors('Error with extracting date from database');
                } else {
                    array_push($ingredients, $ingredient->name);
                }
            }
            $ingredientsName = implode(', ', $ingredients);

            $body = array('title' => $product->name, 'description' => $ingredientsName);

            $response = Http::post($this->url . 'employees/1/post', $body);

            //$response->throw();

            if ($response->successful()) {
                return redirect()->route('proposedMeals')->with('succes', ['text' => 'Product succesfully proposed']);
            } else {
                return redirect()->route('proposedMeals')->withErrors('Request failed');
            }
        }
    }

    function deleteProduct($id)
    {
        $response = Http::delete($this->url . 'posts/' . $id);
        //$response->throw();


        if ($response->successful()) {
            return redirect()->route('proposedMeals')->with('succes', ['text' => 'Product deleted from proposals']);
        } else {
            return redirect()->route('proposedMeals')->withErrors('Request failed');
        }
    }
}
