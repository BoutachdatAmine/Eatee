<?php

use App\Http\Controllers\LoginController;
use App\Http\Controllers\AdminDashboardController;
use App\Http\Controllers\CheckoutController;
use App\Http\Controllers\IngredientController;
use App\Http\Controllers\MenuController;
use App\Http\Controllers\OrderController;
use App\Http\Controllers\SettingsController;
use App\Http\Controllers\StatisticsController;
use App\Http\Controllers\StockController;
use App\Http\Controllers\ProposeController;
use App\Http\Controllers\ProductController;
use App\Http\Controllers\DashBoardController;
use Illuminate\Support\Facades\Route;
/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|

*/

Route::fallback(function () {
    return redirect()->route('home')->withErrors('Page not found');
});

Route::get('/', function () {
    return view('content/index');
})->name('home')->middleware('other');

Route::get('/statistics', [StatisticsController::class, 'showPage'])->name('statistics')->middleware('other');

Route::prefix('login')->middleware('notLoggedIn')->group(function () {
    Route::get('/', [LoginController::class, 'showPage'])->name('loginPage');
    Route::get('/two-factor', [LoginController::class, 'twofactorPage'])->name('twofactorPage');

    Route::post('/login-two-factor', [LoginController::class, 'login'])->name('login');
    Route::post('/twofactor', [LoginController::class, 'twoFactorPost'])->name('twofactor');
});

Route::get('/logout', [LoginController::class, 'logout'])->name('logout')->middleware('other');

Route::prefix('menu')->middleware('other')->group(function () {
    Route::get('/', [MenuController::class, 'showPage'])->name('menu');
    Route::get('/wholeMenu', [MenuController::class, 'showWholeMenu'])->name('wholeMenu');
    Route::get('/oneMenu/{id}', [MenuController::class, 'showOneMenu'])->name('oneMenu');
    Route::get('/addProduct/{id}', [MenuController::class, 'showAddProduct'])->name('addProductMenu');

    Route::post('/addProductToMenu', [MenuController::class, 'addProductToMenu'])->name('addProductToMenu');
    Route::post('/deleteProductfromMenu', [MenuController::class, 'deleteProductFromMenu'])->name('deleteProductfromMenu');
    Route::post('/deleteMenu', [MenuController::class, 'deleteMenu'])->name('deleteMenu');
    Route::post('/addMenu', [MenuController::class, 'addMenu'])->name('addMenu');
    Route::post('/changeDate', [MenuController::class, 'changeDate'])->name('changeDate');


    Route::prefix('Product')->middleware('other')->group(function () {
        Route::get('/', [ProductController::class, 'showPage'])->name('addProduct');
        Route::get('/product/{id}', [ProductController::class, 'showEditProduct'])->name('editProductPage');

        Route::post('/editProduct', [ProductController::class, 'editProduct'])->name('editProduct');
        Route::post('/createProduct', [ProductController::class, 'addProduct'])->name('createProduct');
        Route::post('/giveDiscount', [ProductController::class, 'giveDiscount'])->name('giveDiscount');
        Route::post('/addAllergie', [ProductController::class, 'addAllergie'])->name('addAllergie');

        Route::prefix('Ingredient')->group(function () {
            Route::get('/', [IngredientController::class, 'showPage'])->name('addIngredient');

            Route::post('/create', [IngredientController::class, 'addIngredient'])->name('createIngredient');
        });
    });
});

Route::prefix('stock')->middleware('other')->group(function () {
    Route::get('/', [StockController::class, 'showPage'])->name('stock');
    Route::post('/add', [StockController::class, 'addStock'])->name('addStock');
    Route::post('/remove', [StockController::class, 'removeStock'])->name('removeStock');
    Route::post('/edit', [StockController::class, 'editStock'])->name('editStock');
});

Route::prefix('propose')->middleware('other')->group(function () {
    Route::get('/', [ProposeController::class, 'showPage'])->name('proposedMeals');
    Route::get('/add', [ProposeController::class, 'showAddPage'])->name('addPropose');
    Route::get('/add/{id}', [ProposeController::class, 'addAnProduct'])->name('proposeProduct');
    Route::get('/delete/{id}', [ProposeController::class, 'deleteProduct'])->name('deleteProposal');
});

Route::prefix('order')->middleware('other')->group(function () {
    Route::get('/{date}', [OrderController::class, 'showPage'])->name('orders');

    Route::get('/show/{id}', [OrderController::class, 'showOrder'])->name('showOrder');
    Route::get('/finalise/{id}', [OrderController::class, 'finalise'])->name('finaliseOrder');

    Route::post('/changeDate', [OrderController::class, 'changeDate'])->name('changeDateOrder');
});

Route::prefix('checkout')->middleware('other')->group(function () {
    Route::get('/{date}', [CheckoutController::class, 'showPage'])->name('checkout');

    Route::post('/checkoutOrder', [CheckoutController::class, 'checkout'])->name('checkoutOrder');
    Route::post('/changeDateCheckout', [CheckoutController::class, 'changeDate'])->name('changeDateCheckout');
});

Route::prefix('settings')->middleware('other')->group(function () {
    Route::get('/', [SettingsController::class, 'showPage'])->name('settings');

    Route::post('/updateSettings', [SettingsController::class, 'updateSettings'])->name('updateSettings');
});

Route::prefix('dashboard')->middleware('other')->group(function () {
    Route::get('/', [DashBoardController::class, 'showPage'])->name('dashboardExtra');

    Route::post('/createAllergy', [DashBoardController::class, 'createAllergy'])->name('dashboardCreateAllergy');
    Route::post('/createCategory', [DashBoardController::class, 'createCategory'])->name('dashboardCreateCategory');
    Route::post('/createPromotion', [DashBoardController::class, 'createPromotion'])->name('dashboardCreatePromotion');

    Route::post('/deleteAllergy', [DashBoardController::class, 'deleteAllergy'])->name('dashboardDeleteAllergy');
    Route::post('/deleteCategory', [DashBoardController::class, 'deleteCategory'])->name('dashboardDeleteCategory');
    Route::post('/deletePromotion', [DashBoardController::class, 'deletePromotion'])->name('dashboardDeletePromotion');

    Route::post('/editCategoriesAllergies', [DashBoardController::class, 'editCategoriesAllergies'])->name('dashboardEditCategoriesAllergies');
    Route::post('/editPromotions', [DashBoardController::class, 'editPromotions'])->name('dashboardEditPromotion');
});

Route::prefix('Admin')->middleware('admin')->group(function () {
    Route::get('/', [AdminDashboardController::class, 'showPage'])->name('admin');
    Route::get('/addEmployee', [AdminDashboardController::class, 'showAddEmployee'])->name('addEmployee');
    Route::get('/editEmployee/{id}', [AdminDashboardController::class, 'showEditEmployee'])->name('editEmployee');

    Route::post('/editAnEmployee', [AdminDashboardController::class, 'editEmployee'])->name('editAnEmployee');
    Route::post('/addAnEmployee', [AdminDashboardController::class, 'addEmployee'])->name('addAnEmployee');
    Route::post('/editStatus', [AdminDashboardController::class, 'editStatus'])->name('editStatus');

    Route::post('/resetPassword', [AdminDashboardController::class, 'resetPassword'])->name('resetPassword');
});
