package be.ehb.progproj2.eatee.views.menu;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import be.ehb.progproj2.eatee.R;
import be.ehb.progproj2.eatee.entity.Menu;
import be.ehb.progproj2.eatee.entity.Product;
import be.ehb.progproj2.eatee.views.customer.NavigationScreen;
import be.ehb.progproj2.eatee.views.customer.AccountScreen;

public class MenuDateScreen extends AppCompatActivity {

    private final ArrayList<Product> products = new ArrayList<>();
    public static final String PRODUCTID = "productId";

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_date_screen);

        //set date
        TextView textView = findViewById(R.id.textViewDate);
        Intent intent = getIntent();
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = simpleDateFormat.format(today);
        textView.setText(date);
        if(intent.getStringExtra(MenuScreen.SELECTED_DATE) != null) {
            String selectedDate = intent.getStringExtra(MenuScreen.SELECTED_DATE);
            textView.setText(selectedDate);
        }

        ArrayList<Integer> allergyIds = intent.getIntegerArrayListExtra(FilterScreen.ALLERGIES);
        int ordering = intent.getIntExtra(FilterScreen.ORDERING,0);

        getDataAsync(allergyIds, ordering);
    }

    public void goToNavigationScreen(View view){
        Intent intent = new Intent(this, NavigationScreen.class);
        startActivity(intent);
    }

    public void goToAccountScreen(View view){
        Intent intent = new Intent(this, AccountScreen.class);
        startActivity(intent);
    }

    public void goToFilterScreen(View view){
        Intent intent = new Intent(this, FilterScreen.class);
        startActivity(intent);
    }

    public void goToProductScreen(int productId){
        Intent intent = new Intent(this, ProductScreen.class);
        intent.putExtra(PRODUCTID, productId);
        startActivity(intent);
    }

    public void goToCreateSandwichScreen(View view){
        Intent intent = new Intent(this, CreateSandwichScreen.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("ResourceAsColor")
    private void addProductsToTable(List<Product> products, List<Integer> allergyIds, int ordering){
        if(allergyIds != null && allergyIds.size() > 0) {
            List<Product> productArrayList = products.stream().filter(product -> {
                for (Integer allergyId : allergyIds) {
                    if (!product.getAllergies().contains(allergyId))
                        return true;
                }
                return false;
            }).collect(Collectors.toList());

            if(ordering == 0) {
                setView(productArrayList);
            }
            else if (ordering == 1) {
                setView(productArrayList.stream().sorted((product, t1) -> Double.compare(product.getPrice(), t1.getPrice())).collect(Collectors.toList()));
            } else if (ordering == -1){
                setView(productArrayList.stream().sorted((product, t1) -> -1 * Double.compare(product.getPrice(), t1.getPrice())).collect(Collectors.toList()));
            }
        } else if (ordering == 0){
            setView(products);
        } else if (ordering == 1){
            setView(products.stream().sorted((product, t1) -> Double.compare(product.getPrice(), t1.getPrice())).collect(Collectors.toList()));
        } else if (ordering == -1){
            setView(products.stream().sorted((product, t1) -> -1*Double.compare(product.getPrice(), t1.getPrice())).collect(Collectors.toList()));
        } else setView(products);
    }

    private void setView(List<Product> products){
        RelativeLayout relativeLayout = findViewById(R.id.loadingPanel);
        relativeLayout.setVisibility(View.GONE);

        for(Product product : products) {
            TableRow tableRow = new TableRow(this);
            tableRow.setPadding(10,10,10,10);
            tableRow.setClickable(true);
            tableRow.setFocusable(true);
            int productId = product.getProductId();
            tableRow.setOnClickListener(view -> goToProductScreen(productId));

            TextView textView1 = new TextView(this);
            textView1.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
            textView1.setText(product.getName());
            textView1.setTextColor(Color.parseColor("#3F51B5"));
            textView1.setTextSize(18);
            tableRow.addView(textView1);

            TextView textView2 = new TextView(this);
            textView1.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
            DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
            textView2.setText(df.format(product.getPrice()));
            textView2.setTextColor(Color.parseColor("#3F51B5"));
            textView2.setTextSize(18);
            tableRow.addView(textView2);

            TableLayout tableLayout = findViewById(R.id.tableLayout2);
            tableLayout.addView(tableRow);
        }
    }

    private List<Integer> getMenu() {
        ObjectMapper menuMapper = new ObjectMapper();
        List<Menu> menus = new ArrayList<>();
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
            menus = menuMapper.readValue(content.toString(), new TypeReference<List<Menu>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Integer> productIds = new ArrayList<>();
        for (Menu menu : menus){
            productIds.addAll(menu.getProducts());
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getDataAsync(List<Integer> allergyIds, int ordering) {
        CompletableFuture.supplyAsync(this::getMenu)
                .thenApply(this::getProducts)
                .thenAccept(products1 -> this.runOnUiThread(() -> addProductsToTable(products1, allergyIds, ordering)));
    }
}