<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http;

class MenuController extends Controller
{
    private $url = "10.3.50.23:69/";

    function showPage()
    {
        $products = Http::get($this->url . 'products/');
        $products = json_decode($products);

        $menus = Http::get($this->url . 'menus/');
        $menus = json_decode($menus);

        if ($menus == null || $products == null) {
            return redirect()->route('home')->withErrors('Error with extracting date from database');
        } else {
            return view('/content/menu/menu')->with('products', $products)->with('menus', $menus);
        }
    }
    function showWholeMenu()
    {
        $products = Http::get($this->url . 'products/');
        $products = json_decode($products);

        $categories = Http::get($this->url . 'categories/');
        $categories = json_decode($categories);

        $ingredients = Http::get($this->url . 'ingredients/');
        $ingredients = json_decode($ingredients);

        if ($ingredients == null || $products == null || $categories == null) {
            return redirect()->route('home')->withErrors('Error with extracting date from database');
        } else {
            return view('/content/menu/wholeMenu')->with('products', $products)->with('categories', $categories)->with('ingredients', $ingredients);
        }
    }

    function showOneMenu($id)
    {
        $menu = Http::get($this->url . 'menus/' . $id);
        $menu = json_decode($menu);

        if ($menu == null) {
            return redirect()->route('home')->withErrors('Error with extracting date from database');
        } else {
            $products = array();
            foreach ($menu->products as $productId) {
                $product = Http::get($this->url . 'products/' . $productId);
                $product = json_decode($product);

                if ($product == null) {
                    return redirect()->route('home')->withErrors('Error with extracting date from database');
                } else {
                    array_push($products, $product);
                }
            }
            $ingredients = Http::get($this->url . 'ingredients/');
            $ingredients = json_decode($ingredients);

            $allergies = Http::get($this->url . 'allergies/');
            $allergies = json_decode($allergies);

            if ($ingredients == null || $allergies == null) {
                return redirect()->route('home')->withErrors('Error with extracting date from database');
            } else {
                return view('/content/menu/oneMenu')->with('menu', $menu)->with('products', $products)->with('ingredients', $ingredients)->with('allergies', $allergies);
            }
        }
    }


    function showAddProduct($id)
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
            return view('/content/menu/addProduct')->with('products', $products)->with('categories', $categories)->with('ingredients', $ingredients)->with('id', $id);
        }
    }
    function addProductToMenu(Request $request)
    {
        $request->validate([
            'productId' => 'required',
            'menuId' => 'required',
        ]);

        $productId = $request->input('productId');
        $menuId = $request->input('menuId');

        $response = Http::post($this->url . 'menus/' . $menuId . '/' . 'add/' . $productId);
        //$response->throw();


        if ($response->successful()) {
            return redirect()->route('oneMenu', $menuId)->with('succes', ['text' => 'Product added to menu']);
        } else {
            return redirect()->route('dashboardExtra')->withErrors('Request failed');
        }
    }

    function deleteProductFromMenu(Request $request)
    {
        $request->validate([
            'productId' => 'required',
            'menuId' => 'required',
        ]);

        $productId = $request->input('productId');
        $menuId = $request->input('menuId');

        $response = Http::post($this->url . 'menus/' . $menuId . '/' . 'remove/' . $productId);
        //$response->throw();


        if ($response->successful()) {
            return redirect()->route('oneMenu', $menuId)->with('succes', ['text' => 'Product deleted to menu']);
        } else {
            return redirect()->route('oneMenu', $menuId)->withErrors('Request failed');
        }
    }

    function deleteMenu(Request $request)
    {
        $request->validate([
            'menuId' => 'required',
        ]);

        $menuId = $request->input('menuId');
        $response = Http::delete($this->url . 'menus/' . $menuId);
        //$response->throw();

        if ($response->successful()) {
            return redirect()->route('menu')->with('succes', ['text' => 'Menu has been deleted']);
        } else {
            return redirect()->route('menu')->withErrors('Request failed');
        }
    }

    function addMenu(Request $request)
    {
        $request->validate([
            'type' => 'required',
        ]);

        if ($request->input('type') == 'single') {
            $request->validate([
                'inputDate' => 'required|date',
            ]);
            $body = array('validFrom' => $request->input('inputDate'), 'validTo' => $request->input('inputDate'));
        } elseif ($request->input('type') == 'multi') {
            $request->validate([
                'inputStartDate' => 'required|date|before:inputEndDate',
                'inputEndDate' => 'required|date|after:inputStartDate',
            ]);
            $body = array('validFrom' => $request->input('inputStartDate'), 'validTo' => $request->input('inputEndDate'));
        }
        $response = Http::post($this->url . 'menus/', $body);
        // $response->throw();


        if ($response->successful()) {
            $menuId = $response->json();
            return redirect()->route('oneMenu', $menuId)->with('succes', ['text' => 'Menu created']);
        } else {
            return redirect()->route('menu')->withErrors('Request failed');
        }
    }

    function changeDate(Request $request)
    {
        $request->validate([
            'menuId' => 'required',
        ]);
        if ($request->input('type') == 'single') {
            $request->validate([
                'inputDate' => 'required|date',
            ]);
            $body = array('validFrom' => $request->input('inputDate'), 'validTo' => $request->input('inputDate'));
        } elseif ($request->input('type') == 'multi') {
            $request->validate([
                'inputStartDate' => 'required|date|before:inputEndDate',
                'inputEndDate' => 'required|date|after:inputStartDate',
            ]);
            $body = array('validFrom' => $request->input('inputStartDate'), 'validTo' => $request->input('inputEndDate'));
        }

        $menuId = $request->input('menuId');

        $response = Http::patch($this->url . 'menus/' . $menuId, $body);
        //$response->throw();


        if ($response->successful()) {
            return redirect()->route('oneMenu', $menuId)->with('succes', ['text' => 'Date from menu changed']);
        } else {
            return redirect()->route('oneMenu', $menuId)->withErrors('Request failed');
        }
    }
}
