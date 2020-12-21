package be.ehb.progproj2.eatee.views.menu;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

import be.ehb.progproj2.eatee.R;
import be.ehb.progproj2.eatee.entity.Allergy;
import be.ehb.progproj2.eatee.entity.Product;
import be.ehb.progproj2.eatee.views.customer.NavigationScreen;
import be.ehb.progproj2.eatee.views.customer.AccountScreen;

import static android.os.Build.VERSION_CODES.O;

public class ProductScreen extends AppCompatActivity {

    private int productId;
    private static final String CUSTOMERID = "customerId";
    private static final String PREFERENCES = "Preferences";
    private int customerId;

    @RequiresApi(api = O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_screen);

        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        customerId = sharedPreferences.getInt(CUSTOMERID, -1);

        Intent intent = getIntent();
        productId = intent.getIntExtra(MenuDateScreen.PRODUCTID, 0);

        getProductAsync();
    }

    public void goToNavigationScreen(View view){
        Intent intent = new Intent(this, NavigationScreen.class);
        startActivity(intent);
    }

    public void goToAccountScreen(View view){
        Intent intent = new Intent(this, AccountScreen.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addToCart() {
        CompletableFuture.runAsync(() -> {
            try {
                URL url = new URL("http://10.3.50.23:69/customers/" + customerId + "/cart/p/" + productId);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
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

    private Product getProduct() {
        ObjectMapper productMapper = new ObjectMapper();
        try {
            URL url = new URL("http://10.3.50.23:69/products/"+productId);
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
            product = productMapper.readValue(content.toString(), Product.class);
            return product;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Allergy> getAllergies(List<Integer> allergyIds) {
        ObjectMapper allergiesMapper = new ObjectMapper();
        List<Allergy> allergies = new ArrayList<>();
        for (Integer allergyId : allergyIds) {
            try {
                URL url = new URL("http://10.3.50.23:69/allergies/" + allergyId);
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
                allergies.add(allergiesMapper.readValue(content.toString(), Allergy.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return allergies;
    }

    private Product product;

    @RequiresApi(api = O)
    private void getProductAsync() {
        CompletableFuture.supplyAsync(this::getProduct)
                .thenApply(product -> getAllergies(product.getAllergies()))
                .thenAccept(allergies -> this.runOnUiThread(() -> showProductOnScreen(product, allergies)));
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = O)
    private void showProductOnScreen(Product product, List<Allergy> allergies) {

        RelativeLayout relativeLayout = findViewById(R.id.loadingPanel);
        TableLayout tableLayout = findViewById(R.id.tableLayout3);
        Button available = findViewById(R.id.availability), addToCart = findViewById(R.id.button3);

        TextView productName = findViewById(R.id.orderId), productPrice = findViewById(R.id.orderDate);
        productName.setText(product.getName());
        DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
        productPrice.setText(df.format(product.getPrice()));

        if(product.isAvailable()){
            available.setBackgroundColor(getResources().getColor(R.color.available));
            available.setText("Available");
            addToCart.setVisibility(View.VISIBLE);
            addToCart.setOnClickListener(v -> {
                addToCart();
                Toast.makeText(v.getContext(), product.getName() + " was added to your cart!", Toast.LENGTH_SHORT).show();
            });

        }else{
            available.setBackgroundColor(getResources().getColor(R.color.unavailable));
            available.setText("Unavailable");
        }

        TextView productAllergies = findViewById(R.id.allergieNames);

        if(product.getAllergies().size() > 0) {
            ArrayList<String> allergyNames = new ArrayList<>();
            for (Allergy allergy : allergies) {
                allergyNames.add(allergy.getName());
            }
            if (Build.VERSION.SDK_INT >= O) {
                productAllergies.setText(String.join(", ", allergyNames));
            }
        } else productAllergies.setText("None");

        relativeLayout.setVisibility(View.GONE);
        tableLayout.setVisibility(View.VISIBLE);
        available.setVisibility(View.VISIBLE);
    }
}