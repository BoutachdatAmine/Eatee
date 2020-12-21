<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http;

class SettingsController extends Controller
{
    private $url = "10.3.50.23:69/";

    function showPage()
    {

        $id = session('user')->employeeId;

        $employee = Http::get($this->url . 'employees/' . $id);
        $employee = json_decode($employee);

        if ($employee == null) {
            return redirect()->route('home')->withErrors('Error with extracting date from database');
        } else {
            return view('/content/settings/settings')->with('employee', $employee);
        }
    }
    function updateSettings(Request $request)
    {
        $request->validate([
            'inputMail' => 'required',
            'inputFirstName' => 'required',
            'inputLastName' => 'required',
            'inputPassword' => '',
            'inputConfirmPassword' => 'same:inputPassword',
        ]);
        if ($request->input('inputPassword') != "") {
            $body = array('firstName' => $request->input('inputFirstName'), 'lastName' => $request->input('inputLastName'), 'email' => $request->input('inputMail'), "password" => $request->input('inputPassword'));
        } else {
            $body = array('firstName' => $request->input('inputFirstName'), 'lastName' => $request->input('inputLastName'), 'email' => $request->input('inputMail'));
        }
        $id = $request->input('inputId');

        $response = Http::patch($this->url . 'employees/' . $id, $body);

        //$response->throw();


        if ($response->successful()) {
            return back()->with('succes', ['text' => 'Account edited']);
        } else {
            return back()->withErrors('Request failed');
        }
    }
}
