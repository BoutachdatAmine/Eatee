<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http;

class StatisticsController extends Controller
{
    private $url = "10.3.50.23:69/";

    function showPage()
    {
        $orders = Http::get($this->url . 'orders/');
        $orders = json_decode($orders);

        if ($orders == null) {
            return redirect()->route('home')->withErrors('Error with extracting date from database');
        } else {
            $total = 0;
            $processing = 0;
            $ready = 0;
            $delivered = 0;

            foreach ($orders as $order) {
                if ($order->status != 'CANCELLED') {
                    $total++;
                    switch ($order->status) {
                        case 'PAID':
                            $processing++;
                            break;
                        case 'READY_FOR_PICKUP':
                            $ready++;
                            break;
                        case 'DONE':
                            $delivered++;
                            break;
                    }
                }
            }

            $stats = array('total' => $total, 'processing' => $processing, 'ready' => $ready, 'deliverd' => $delivered);
            $stats = json_decode(json_encode($stats));

            return view('/content/other/statistics')->with('stats', $stats);
        }
    }
}
