<?php

namespace App\Http\Middleware;

use Closure;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class OtherMiddleware
{
    /**
     * Handle an incoming request.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \Closure  $next
     * @return mixed
     */
    public function handle(Request $request, Closure $next)
    {
        if (session()->has('user')) {
            $role = session('user')->role;
            if ($role == 2 || $role == 1 || $role == 3) {
                return $next($request);
            } else {
                return redirect()->route('home')->withErrors("You don't have this permission");
            }
        } else {
            return redirect()->route('loginPage');
        }
    }
}
