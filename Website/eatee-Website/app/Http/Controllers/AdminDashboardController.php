<?php

namespace App\Http\Controllers;

use Error;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http;

class AdminDashboardController extends Controller
{
    private $url = "10.3.50.23:69/";

    function showPage()
    {
        $accounts = Http::get($this->url . 'employees/');
        $accounts = json_decode($accounts);

        if ($accounts == null) {
            return redirect()->route('home')->withErrors('Error with extracting date from database');
        } else {
            return view('/content/admin/admin')->with('accounts', $accounts);
        }
    }

    function showAddEmployee()
    {
        return view('/content/admin/addEmployee');
    }
    function addEmployee(Request $request)
    {
        $request->validate([
            'inputMail' => 'required|email',
            "inputFirstName" => 'required',
            "inputLastName" => 'required',
            "inputPassword" => 'required',
            "inputConfirmPassword" => 'required|same:inputPassword',
            "inputType" => 'required',
        ]);

        $type = $request->input('inputType');
        if ($type == "admin") {
            $type = 1;
        } elseif ($type == "chef") {
            $type = 2;
        } else {
            $type = 3;
        }

        $password = $request->input('inputPassword');

        $body = array('firstName' => $request->input('inputFirstName'), 'lastName' => $request->input('inputLastName'), 'password' => $password, 'email' => $request->input('inputMail'), 'activated' => true, 'role' => $type);

        $response = Http::post($this->url . 'employees/', $body);
        //$response->throw();

        if ($response->successful()) {
            return redirect()->route('admin')->with('succes', ['text' => 'Employee has been added!']);
        } else {
            return redirect()->route('admin')->withErrors('Request failed');
        }
    }
    function showEditEmployee($id)
    {
        $account = Http::get($this->url . 'employees/' . $id);
        $account = json_decode($account);


        if ($account == null) {
            return redirect()->route('home')->withErrors('Error with extracting date from database');
        } else {
            return view('/content/admin/editAccount')->with('account', $account);
        }
    }
    function editEmployee(Request $request)
    {
        $request->validate([
            "inputMail" => 'required|email',
            "inputFirstName" => 'required',
            "inputLastName" => 'required',
            "inputType" => 'required',
        ]);
        $id = $request->input('inputId');
        $type = $request->input('inputType');

        if ($type == "admin") {
            $type = 1;
        } elseif ($type == "chef") {
            $type = 2;
        } else {
            $type = 3;
        }
        $body = array('firstName' => $request->input('inputFirstName'), 'lastName' => $request->input('inputLastName'), 'email' => $request->input('inputMail'), 'activated' => true, 'role' => $type);

        $response = Http::patch($this->url . 'employees/' . $id, $body);
        $response->throw();

        if ($response->successful()) {
            return redirect()->route('admin')->with('succes', ['text' => 'Employee has been edited!']);
        } else {
            return redirect()->route('admin')->withErrors('Request failed');
        }
    }

    function editStatus(Request $request)
    {
        $request->validate([
            'inputStatus' => 'required'
        ]);

        $id = $request->input('inputId');
        if ($request->input('inputStatus') == "active") {
            $body = array("activated" => true);
        } else {
            $body = array("activated" => false);
        }
        $response = Http::patch($this->url . 'employees/' . $id, $body);
        //$response->throw();
        if ($response->successful()) {
            return redirect()->route('admin')->with('succes', ['text' => 'Status has been changed!']);
        } else {
            return redirect()->route('admin')->withErrors('Request failed');
        }
    }

    function resetPassword(Request $request)
    {
        $request->validate([
            'inputPassword' => 'required',
            'inputConfirmPassword' => 'required|same:inputPassword',
        ]);

        $id = $request->input('inputId');
        $password = $request->input('inputPassword');

        $body = array('password' => $password, 'activated' => true);

        $response = Http::patch($this->url . 'employees/' . $id, $body);
        //$response->throw();

        if ($response->successful()) {
            return redirect()->route('admin')->with('succes', ['text' => 'Password has been resetted!']);
        } else {
            return redirect()->route('admin')->withErrors('Request failed');
        }
    }
}
