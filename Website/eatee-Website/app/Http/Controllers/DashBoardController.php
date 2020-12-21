<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http;

class DashBoardController extends Controller
{
    private $url = "10.3.50.23:69/";

    function showPage()
    {
        $categories = Http::get($this->url . 'categories/');
        $categories = json_decode($categories);

        $promotions = Http::get($this->url . 'promotions/');
        $promotions = json_decode($promotions);

        $allergies = Http::get($this->url . 'allergies/');
        $allergies = json_decode($allergies);

        $products = Http::get($this->url . 'products/');
        $products = json_decode($products);

        if ($categories == null || $promotions == null || $allergies == null || $products == null) {
            return redirect()->route('home')->withErrors('Error with extracting date from database');
        } else {
            return view('/content/other/dashboard')->with('categories', $categories)->with('promotions', $promotions)->with('allergies', $allergies)->with('products', $products);
        }
    }

    function createAllergy(Request $request)
    {
        $request->validate([
            'inputName' => 'required'
        ]);

        $body = array('name' => $request->input('inputName'));

        $response = Http::post($this->url . 'allergies/', $body);
        //$response->throw();

        if ($response->successful()) {
            return redirect()->route('dashboardExtra')->with('succes', ['text' => 'Allergy Created']);
        } else {
            return redirect()->route('dashboardExtra')->withErrors('Request failed');
        }
    }
    function createCategory(Request $request)
    {
        $request->validate([
            'inputName' => 'required'
        ]);
        $body = array('name' => $request->input('inputName'));

        $response = Http::post($this->url . 'categories/', $body);
        //$response->throw();

        if ($response->successful()) {
            return redirect()->route('dashboardExtra')->with('succes', ['text' => 'Category Created']);
        } else {
            return redirect()->route('dashboardExtra')->withErrors('Request failed');
        }
    }
    function deleteAllergy(Request $request)
    {
        $request->validate([
            'inputId' => 'required'
        ]);

        $response = Http::delete($this->url . 'allergies/' . $request->input('inputId'));
        //$response->throw();

        if ($response->successful()) {
            return redirect()->route('dashboardExtra')->with('succes', ['text' => 'Allergy Deleted']);
        } else {
            return redirect()->route('dashboardExtra')->withErrors('Request failed');
        }
    }

    function deleteCategory(Request $request)
    {
        $request->validate([
            'inputId' => 'required'
        ]);

        $response = Http::delete($this->url . 'categories/' . $request->input('inputId'));
        //$response->throw();


        if ($response->successful()) {
            return redirect()->route('dashboardExtra')->with('succes', ['text' => 'Category Deleted']);
        } else {
            return redirect()->route('dashboardExtra')->withErrors('Request failed');
        }
    }

    function deletePromotion(Request $request)
    {
        $request->validate([
            'inputId' => 'required'
        ]);
        $response = Http::delete($this->url . 'promotions/' . $request->input('inputId'));
        //$response->throw();

        if ($response->successful()) {
            return redirect()->route('dashboardExtra')->with('succes', ['text' => 'Promotion Deleted']);
        } else {
            return redirect()->route('dashboardExtra')->withErrors('Request failed');
        }
    }

    function createPromotion(Request $request)
    {
        $request->validate([
            'inputAmount' => 'required',
            'inputStartDate' => 'required',
            'inputEndDate' => 'required|after:inputStartDate',
            'productId' => 'required',
        ]);
        $product = Http::get($this->url . 'products/' . $request->input('productId'));
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
                return redirect()->route('dashboardExtra')->with('succes', ['text' => 'Promotion created']);
            } else {
                return redirect()->route('dashboardExtra')->withErrors('Request failed');
            }
        }
    }

    function editCategoriesAllergies(Request $request)
    {
        $request->validate([
            'inputType' => 'required',
            'inputId' => 'required',
            'inputName' => 'required',
        ]);
        $type = $request->input('inputType');
        $id = $request->input('inputId');

        switch ($type) {
            case 'Allergies':
                $message = 'Allergy Updated';
                $route = 'allergies/';
                break;
            case 'Categories':
                $message = 'Category Updated';
                $route = 'categories/';
                break;
        }
        $body = array('name' => $request->input('inputName'));

        $response = Http::patch($this->url . $route . $id, $body);
        //$response->throw();

        if ($response->successful()) {
            return redirect()->route('dashboardExtra')->with('succes', ['text' => $message]);
        } else {
            return redirect()->route('dashboardExtra')->withErrors('Request failed');
        }
    }

    function editPromotions(Request $request)
    {
        $request->validate([
            'inputId' => 'required',
            'inputStartDate' => 'required',
            'inputEndDate' => 'required',
            'inputAmount' => 'required',
        ]);

        $id = $request->input('inputId');
        $start = $request->input('inputStartDate');
        $end = $request->input('inputEndDate');
        $value = $request->input('inputAmount');

        $body = array('value' => $value, 'validFrom' => $start, 'validTo' => $end);

        $response = Http::patch($this->url . 'promotions/' . $id, $body);
        //$response->throw();

        if ($response->successful()) {
            return redirect()->route('dashboardExtra')->with('succes', ['text' => 'Promotion Updated']);
        } else {
            return redirect()->route('dashboardExtra')->withErrors('Request failed');
        }
    }
}
