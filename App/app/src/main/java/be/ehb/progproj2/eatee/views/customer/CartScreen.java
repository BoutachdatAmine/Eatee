package be.ehb.progproj2.eatee.views.customer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;

import be.ehb.progproj2.eatee.R;
import be.ehb.progproj2.eatee.entity.Cart;
import be.ehb.progproj2.eatee.entity.CustomSandwich;
import be.ehb.progproj2.eatee.entity.Product;
import be.ehb.progproj2.eatee.views.payment.PaymentScreen;

public class CartScreen extends AppCompatActivity {

    private static final String PREFERENCES = "Preferences", CUSTOMERID = "customerId", TOTAL = "total";
    private final Map<Integer, Product> products = new TreeMap<>();
    private final Map<Integer, CustomSandwich> sandwiches = new TreeMap<>();
    private int customerId;
    private Cart cart = new Cart();
    private double cartTotal;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_screen);

        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        customerId = sharedPreferences.getInt(CUSTOMERID, -1);

        getProductsAndSandwichesAsync();
    }

    public void goToNavigationScreen(View view) {
        Intent intent = new Intent(this, NavigationScreen.class);
        startActivity(intent);
    }

    public void goToAccountScreen(View view) {
        Intent intent = new Intent(this, AccountScreen.class);
        startActivity(intent);
    }

    public void goToPaymentScreen(View view) {
        if(cartTotal < 0.50)
            Toast.makeText(this, "Total must be atleast 0.50!", Toast.LENGTH_SHORT).show();
        else{
            Intent intent = new Intent(this, PaymentScreen.class);
            intent.putExtra(TOTAL, cartTotal);
            startActivity(intent);
        }
    }

    @SuppressLint({"ResourceAsColor", "UseCompatLoadingForDrawables", "SetTextI18n"})
    private void showProductsOnScreen(Map<Integer, Product> products, Map<Integer, CustomSandwich> sandwiches) {
        TableLayout tableLayout = findViewById(R.id.tableLayout3);

        RelativeLayout loader = findViewById(R.id.loadingPanel);
        Button order = findViewById(R.id.buttonOrder);
        ScrollView scrollView = findViewById(R.id.scrollView2);

        loader.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);
        order.setVisibility(View.VISIBLE);

        for(Map.Entry<Integer, Product> entry : products.entrySet()) {
            TableRow tableRow = new TableRow(this);
            TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins (0 ,20,0, 20);
            tableRow.setLayoutParams(layoutParams);
            tableRow.setPadding(5,5,5,5);

            TextView textView1 = new TextView(this);
            textView1.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
            textView1.setGravity(Gravity.CENTER);
            textView1.setText(entry.getValue().getName());
            textView1.setFocusable(true);
            textView1.setTextColor(Color.parseColor("#3F51B5"));
            textView1.setTextSize(18);

            TextView textView2 = new TextView(this);
            textView1.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
            textView2.setGravity(Gravity.CENTER);
            DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
            textView2.setText("\u20ac " + df.format(entry.getValue().getPrice()));
            textView2.setTextColor(Color.parseColor("#3F51B5"));
            textView2.setTextSize(18);

            ImageView imageView1 = new ImageView(this);
            imageView1.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1.0f));
            imageView1.setPadding(0,0,0,0);
            imageView1.setImageDrawable(getResources().getDrawable(R.drawable.cancel));
            imageView1.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View view) {
                    tableRow.setVisibility(View.GONE);
                    removeProductAsync(entry.getKey());
                    cartTotal -= entry.getValue().getPrice();
                    Toast.makeText(view.getContext(), entry.getValue().getName() + " was removed from your cart!", Toast.LENGTH_SHORT).show();
                }
            });

            tableRow.addView(textView1);
            tableRow.addView(textView2);
            tableRow.addView(imageView1);

            tableLayout.addView(tableRow);
        }

        for(Map.Entry<Integer, CustomSandwich> entry : sandwiches.entrySet()) {
            TableRow tableRow = new TableRow(this);
            TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins (0 ,20,0, 20);
            tableRow.setLayoutParams(layoutParams);
            tableRow.setPadding(5,5,5,5);

            TextView textView1 = new TextView(this);
            textView1.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
            textView1.setGravity(Gravity.CENTER);
            textView1.setText("Custom Sandwich #" + entry.getValue().getId());
            textView1.setFocusable(true);
            textView1.setTextColor(Color.parseColor("#3F51B5"));
            textView1.setTextSize(18);

            TextView textView2 = new TextView(this);
            textView1.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
            textView2.setGravity(Gravity.CENTER);
            DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
            textView2.setText("\u20ac " + df.format(4.00));
            textView2.setTextColor(Color.parseColor("#3F51B5"));
            textView2.setTextSize(18);

            ImageView imageView1 = new ImageView(this);
            imageView1.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1.0f));
            imageView1.setPadding(0,0,0,0);
            imageView1.setImageDrawable(getResources().getDrawable(R.drawable.cancel));
            imageView1.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View view) {
                    tableRow.setVisibility(View.GONE);
                    cartTotal -= 4.00;
                    removeSandwichAsync(entry.getKey());
                    Toast.makeText(view.getContext(), "Custom Sandwich #" + entry.getValue().getId() + " was removed from your cart!", Toast.LENGTH_SHORT).show();
                }
            });

            tableRow.addView(textView1);
            tableRow.addView(textView2);
            tableRow.addView(imageView1);

            tableLayout.addView(tableRow);
        }
    }

    private Cart getCart() {
        ObjectMapper cardMapper = new ObjectMapper();
        try {
            URL url = new URL("http://10.3.50.23:69/customers/" + customerId + "/cart");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null) {
                content.append(inputLine);
            }
            bufferedReader.close();
            con.disconnect();
            cart = cardMapper.readValue(content.toString(), Cart.class);
            cartTotal = cart.getTotal();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cart;
    }

    private Cart getProduct() {
        ObjectMapper productMapper = new ObjectMapper();
        Map<Integer, Integer> productIds = cart.getProducts();
        for (Map.Entry<Integer, Integer> entry : productIds.entrySet()){
            try {
                URL url = new URL("http://10.3.50.23:69/products/" + entry.getValue());
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setDoInput(true);
                con.setRequestProperty("Content-Type", "application/json");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = bufferedReader.readLine()) != null) {
                    content.append(inputLine);
                }
                bufferedReader.close();
                con.disconnect();
                Product product = productMapper.readValue(content.toString(), Product.class);
                products.put(entry.getKey(), product);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cart;
    }

    private Cart getSandwich() {
        ObjectMapper sandwichMapper = new ObjectMapper();
        Map<Integer, Integer> sandwichIds = cart.getSandwiches();
        for (Map.Entry<Integer, Integer> entry : sandwichIds.entrySet()){
            try {
                URL url = new URL("http://10.3.50.23:69/custom/" + entry.getValue());
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setDoInput(true);
                con.setRequestProperty("Content-Type", "application/json");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = bufferedReader.readLine()) != null) {
                    content.append(inputLine);
                }
                bufferedReader.close();
                con.disconnect();
                CustomSandwich sandwich = sandwichMapper.readValue(content.toString(), CustomSandwich.class);
                sandwiches.put(entry.getKey(), sandwich);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cart;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getProductsAndSandwichesAsync() {
        CompletableFuture.supplyAsync(this::getCart)
                .thenApply(cart -> getProduct())
                .thenApply(cart -> getSandwich())
                .thenAccept(cart -> this.runOnUiThread(() -> showProductsOnScreen(products, sandwiches)));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void removeProductAsync(int cartId){
        CompletableFuture.runAsync(() -> {
            try{
                URL url = new URL("http://10.3.50.23:69/customers/" + customerId + "/cart/p/" + cartId);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("DELETE");
                con.getInputStream().close();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void removeSandwichAsync(int cartId){
        CompletableFuture.runAsync(() -> {
            try {
                URL url = new URL("http://10.3.50.23:69/customers/" + customerId + "/cart/s/" + cartId);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("DELETE");
                con.getInputStream().close();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
