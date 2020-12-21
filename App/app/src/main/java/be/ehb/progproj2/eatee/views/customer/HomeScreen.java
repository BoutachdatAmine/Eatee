package be.ehb.progproj2.eatee.views.customer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

import be.ehb.progproj2.eatee.R;
import be.ehb.progproj2.eatee.entity.Customer;
import be.ehb.progproj2.eatee.entity.Menu;
import be.ehb.progproj2.eatee.entity.Product;
import be.ehb.progproj2.eatee.views.menu.CreateSandwichScreen;
import be.ehb.progproj2.eatee.views.menu.MenuDateScreen;
import be.ehb.progproj2.eatee.views.menu.ProductScreen;

public class HomeScreen extends AppCompatActivity {

    public static final String PRODUCTID = "productId";
    private final List<Product> products = new ArrayList<>();
    private final List<Integer> productIds = new ArrayList<>();
    private static final String CUSTOMERID = "customerId", PREFERENCES = "Preferences";
    private TextView firstname;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        firstname = findViewById(R.id.textViewWelcomeMessage);
        int customerId = sharedPreferences.getInt(CUSTOMERID, -1);
        getFirstNameAsync(customerId);

        getProductsAsync();

        LocalDate localDate = LocalDate.now();
        TextView date = findViewById(R.id.textViewDate);
        date.setText(localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        date.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    public void goToNavigationScreen(View view){
        Intent intent = new Intent(this, NavigationScreen.class);
        startActivity(intent);
    }

    public void goToAccountScreen(View view){
        Intent intent = new Intent(this, AccountScreen.class);
        startActivity(intent);
    }

    public void goToCreateSandwichScreen(View view){
        Intent intent = new Intent(this, CreateSandwichScreen.class);
        startActivity(intent);
    }

    public void goToMenuDateScreen(View view){
        Intent intent = new Intent(this, MenuDateScreen.class);
        startActivity(intent);
    }

    public void goToProductScreen(int productId){
        Intent intent = new Intent(this, ProductScreen.class);
        intent.putExtra(PRODUCTID, productId);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        //User is not allowed to return to previous screen
    }

    private String getCustomerName(int customerId){
        ObjectMapper customerMapper = new ObjectMapper();
        if(customerId > 0) {
            try {
                URL url = new URL("http://10.3.50.23:69/customers/" + customerId);
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
                return customerMapper.readValue(content.toString(), Customer.class).getFirstname();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private List<Integer> getMenu()  {
        ObjectMapper menuMapper = new ObjectMapper();
        List<Menu> menus;
        try {
            URL url = new URL("http://10.3.50.23:69/menus/current");
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
            menus = menuMapper.readValue(content.toString(), new TypeReference<List<Menu>>(){});
            for (Menu menu : menus)
                productIds.addAll(menu.getProducts());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productIds;
    }

    private List<Product> getProducts(List<Integer> productIds) {
        ObjectMapper productMapper = new ObjectMapper();
        for (Integer productId : productIds) {
            try {
                URL url = new URL("http://10.3.50.23:69/products/" + productId);
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
                products.add(product);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return products;
    }

    @SuppressLint("ResourceAsColor")
    private void showProductsOnScreen(List<Product> products){
        RelativeLayout relativeLayout = findViewById(R.id.loadingPanel);
        relativeLayout.setVisibility(View.GONE);

        TextView menuMessage = findViewById(R.id.textViewMenuMessage), or = findViewById(R.id.textViewOr);
        Button sandwich = findViewById(R.id.buttonMakeSandwich);
        View date = findViewById(R.id.viewDate);

        menuMessage.setVisibility(View.VISIBLE);
        or.setVisibility(View.VISIBLE);
        sandwich.setVisibility(View.VISIBLE);
        date.setVisibility(View.VISIBLE);

        for (Product product : products) {
            TableRow tableRow = new TableRow(this);
            tableRow.setClickable(true);
            tableRow.setFocusable(true);
            tableRow.setPadding(5, 5, 5, 5);
            int productId = product.getProductId();
            tableRow.setOnClickListener(view -> goToProductScreen(productId));

            TextView textView1 = new TextView(this);
            textView1.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
            textView1.setText(product.getName());
            textView1.setTextColor(Color.parseColor("#3F51B5"));
            textView1.setTextSize(18);

            TextView textView2 = new TextView(this);
            textView1.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 2.0f));
            DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
            textView2.setText(df.format(product.getPrice()));
            textView2.setTextColor(Color.parseColor("#3F51B5"));
            textView2.setTextSize(18);

            tableRow.addView(textView1);
            tableRow.addView(textView2);
            TableLayout tableLayout = findViewById(R.id.tableLayout2);
            tableLayout.addView(tableRow);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setFirstName(String firstName){
        String firstNameCapital = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
        firstname.setText("Welcome, " + firstNameCapital + "!");
        firstname.setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getProductsAsync() {
        CompletableFuture.supplyAsync(this::getMenu)
                .thenApply(this::getProducts)
                .thenAccept(products1 -> this.runOnUiThread(() -> showProductsOnScreen(products1)));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getFirstNameAsync(int customerId){
        CompletableFuture.supplyAsync(() -> getCustomerName(customerId))
                .thenAccept(s -> this.runOnUiThread(() -> setFirstName(s)));
    }
}