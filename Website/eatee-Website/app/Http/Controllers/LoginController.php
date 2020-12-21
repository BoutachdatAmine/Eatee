<?php

namespace App\Http\Controllers;

use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Session;

class LoginController extends Controller
{
    private $url = "10.3.50.23:69/";

    function showPage()
    {
        return view('/content/login');
    }

    function login(Request $request)
    {
        $request->validate([
            'inputMail' => 'required',
            'inputPassword' => 'required',
        ]);

        $body = array(
            'email' => $request->input('inputMail'),
            'password' => $request->input('inputPassword')
        );

        $response = Http::post($this->url . 'auth/employee', $body);

        //$response->throw();

        $id = $response->json();
        if ($id > 0) { //succes
            $employee = Http::get($this->url . 'employees/' . $id);
            $employee = json_decode($employee);

            if ($employee == null) {
                return redirect()->back()->withErrors('Error with extracting date from database');
            } else {

                $user = new User();
                $user->firstname = $employee->firstname;
                $user->lastname = $employee->lastname;
                $user->userId = $employee->userId;
                $user->employeeId = $employee->employeeId;
                $user->role = $employee->role;
                $user->email = $employee->email;

                session(['user' => $user]);

                return redirect()->route('home')->with('succes', ['text' => 'Welcome back ' . $employee->firstname . '!']);
            }
        } elseif ($id == -7) {
            // Account disabled
            return back()->withErrors('This account has been deactivated!');
        } elseif ($id == -5 || $id == -6) {
            // Require two factor key
            return redirect()->route('twofactorPage')->with('inputMail', $request->inputMail)->with('inputPassword', $request->inputPassword);
        } else {
            return back()->withErrors('Invalid credentials.');
        }
    }

    function twofactorPage()
    {
        return view('content.twofactor', ['inputMail' => session()->get('inputMail'), 'inputPassword' => session()->get('inputPassword')]);
    }

    function twoFactorPost(Request $request)
    {
        $request->validate([
            'inputMail'    => 'required',
            'inputPassword' => 'required',
            'inputKey'      => 'required',
        ]);

        $body = array(
            'email' => $request->inputMail,
            'password' => $request->inputPassword,
            'twoFactorKey' => $request->inputKey,
        );

        $response = Http::post($this->url . 'auth/employee', $body);
        //$response->throw();

        $id = $response->json();

        if ($id > 0) {
            $employee = Http::get($this->url . 'employees/' . $id);
            $employee = json_decode($employee);

            if ($employee == null) {
                return redirect()->back()->withErrors('Error with extracting date from database');
            } else {
                // Login
                $user = new User();
                $user->firstname = $employee->firstname;
                $user->lastname = $employee->lastname;
                $user->userId = $employee->userId;
                $user->employeeId = $employee->employeeId;
                $user->role = $employee->role;
                $user->email = $employee->email;

                session(['user' => $user]);

                return redirect()->route('home')->with('succes', ['text' => 'Welcome back ' . $employee->firstname . '!']);
            }
        } else {
            return back()->withInput()->withErrors('The entered key is incorrect.');
        }
    }

    function logout()
    {
        if (session()->has('user')) {
            session()->forget('user');
        }
        return redirect()->route('loginPage')->with('succes', ['text' => 'Logout succesfull']);
    }
}
