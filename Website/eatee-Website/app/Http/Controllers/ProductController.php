<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http;


class ProductController extends Controller
{
    private $url = "10.3.50.23:69/";

    function showPage()
    {
        $ingredients = Http::get($this->url . 'ingredients/');
        $ingredients = json_decode($ingredients);

        $categories = Http::get($this->url . 'categories/');
        $categories = json_decode($categories);

        $allergies = Http::get($this->url . 'allergies/');
        $allergies = json_decode($allergies);

        if ($ingredients == null || $categories == null || $allergies == null) {
            return redirect()->route('home')->withErrors('Error with extracting date from database');
        } else {
            return view('/content/product/addProduct')->with('ingredients', $ingredients)->with('categories', $categories)->with('allergies', $allergies);
        }
    }
    function addProduct(Request $request)
    {
        $request->validate([
            "inputName" => 'required',
            "inputPrice" => 'required',
            "inputCategory" => 'required',
        ]);

        $ingredients = $request->input('inputIngredients');
        $ingredientDetails = array();
        if ($ingredients != null) {
            foreach ($ingredients as $ingredientId) {
                if ($request->input('inputQuantity' . $ingredientId) == null) {
                    return back()->withErrors('Ingredients amount not filled in');
                } else {
                    $ingredient = array(
                        'id'     => $ingredientId,
                        'amount' => $request->input('inputQuantity' . $ingredientId)
                    );
                    array_push($ingredientDetails, $ingredient);
                }
            }
        }

        $allergyIds = array();
        if ($request->inputAllergies != null) {
            foreach ($request->inputAllergies as $ingredientId) {
                array_push($allergyIds, $ingredientId);
            }
        }

        $product = array(
            'name'        => $request->input('inputName'),
            'price'       => $request->input('inputPrice'),
            'categoryId'  => $request->input('inputCategory'),
            'allergies'   => $allergyIds,
            'ingredients' => $ingredientDetails
        );

        $response = Http::post($this->url . 'products/', $product);
        //$response->throw();


        if ($response->successful()) {
            return redirect()->route('wholeMenu')->with('succes', ['text' => 'Product created']);
        } else {
            return redirect()->route('wholeMenu')->withErrors('Request failed');
        }
    }

    function showEditProduct($id)
    {
        $ingredients = Http::get($this->url . 'ingredients/');
        $ingredients = json_decode($ingredients);

        $categories = Http::get($this->url . 'categories/');
        $categories = json_decode($categories);

        $allergies = Http::get($this->url . 'allergies/');
        $allergies = json_decode($allergies);

        $product = Http::get($this->url . 'products/' . $id);
        $product = json_decode($product);

        if ($ingredients == null || $categories == null || $allergies == null || $product == null) {
            return redirect()->route('home')->withErrors('Error with extracting date from database');
        } else {
            return view('/content/product/editProduct')->with('product', $product)->with('ingredients', $ingredients)->with('categories', $categories)->with('allergies', $allergies);
        }
    }
    function editProduct(Request $request)
    {
        $request->validate([
            "inputName" => 'required',
            "inputPrice" => 'required',
            "inputCategory" => 'required',
        ]);

        $ingredients = $request->input('inputIngredients');
        $ingredientDetails = array();
        if ($ingredients != null) {
            foreach ($ingredients as $ingredientId) {
                if ($request->input('inputQuantity' . $ingredientId) == null) {
                    return back()->withErrors('Ingredients amount not filled in');
                } else {
                    $ingredient = array(
                        'id'     => $ingredientId,
                        'amount' => $request->input('inputQuantity' . $ingredientId)
                    );
                    array_push($ingredientDetails, $ingredient);
                }
            }
        }

        $allergyIds = array();
        if ($request->inputAllergies != null) {
            foreach ($request->inputAllergies as $ingredientId) {
                array_push($allergyIds, $ingredientId);
            }
        }


        $product = array(
            'name'        => $request->input('inputName'),
            'price'       => $request->input('inputPrice'),
            'categoryId'  => $request->input('inputCategory'),
            'allergies'   => $allergyIds,
            'ingredients' => $ingredientDetails
        );
        $id = $request->input('id');

        $response = Http::patch($this->url . 'products/' . $id, $product);
        //$response->throw();

        if ($response->successful()) {
            return redirect()->route('wholeMenu')->with('succes', ['text' => 'Product edited']);
        } else {
            return redirect()->route('wholeMenu')->withErrors('Request failed');
        }
    }

    function giveDiscount(Request $request)
    {
        $request->validate([
            'inputAmount' => 'required',
            'inputId' => 'required',
            'inputStartDate' => 'required',
            'inputEndDate' => 'required',
        ]);
        $product = Http::get($this->url . 'products/' . $request->input('inputId'));
        $product = json_decode($product);

        if ($product == null) {
            return redirect()->route('home')->withErrors('Error with extracting date from database');
        } else {
            if ($product->promotionActive == 0) {
                if ($request->input('inputAmount') >= $product->price) {
                    return back()->withErrors('Amount of promotion is more than the price of product');
                }
            } else {
                return back()->withErrors('Already a promotion active');
            }

            $body = array('product' => $product->productId, 'value' => $request->input('inputAmount'), 'validFrom' => $request->input('inputStartDate'), 'validTo' => $request->input('inputEndDate'));

            $response = Http::post($this->url . 'promotions/', $body);
            //$response->throw();

            if ($response->successful()) {
                return redirect()->route('wholeMenu')->with('succes', ['text' => 'promotion given to the product']);
            } else {
                return redirect()->route('wholeMenu')->withErrors('Request failed');
            }
        }
    }
    function addAllergie(Request $request)
    {
        $request->validate([
            'inputAllergie' => 'required',
        ]);

        $body = array('name' => $request->input('inputAllergie'));

        $response = Http::post($this->url . 'allergies/', $body);
        //$response->throw();


        if ($response->successful()) {
            return back()->with('succes', ['text' => 'New Allergie added']);
        } else {
            return back()->withErrors('Request failed');
        }
    }
}
