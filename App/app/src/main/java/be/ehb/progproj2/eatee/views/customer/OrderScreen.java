package be.ehb.progproj2.eatee.views.customer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

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
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

import be.ehb.progproj2.eatee.R;
import be.ehb.progproj2.eatee.entity.Order;
import be.ehb.progproj2.eatee.entity.Product;

import static android.os.Build.VERSION_CODES.O;

public class OrderScreen extends AppCompatActivity {

    private int orderId;
    private Order order = new Order();

    @RequiresApi(O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_screen);

        Intent intent = getIntent();
        orderId = intent.getIntExtra(OrdersScreen.ORDERID, 0);

        getDataAsync();
    }

    public void goToNavigationScreen(View view){
        Intent intent = new Intent(this, NavigationScreen.class);
        startActivity(intent);
    }

    public void goToAccountScreen(View view){
        Intent intent = new Intent(this, AccountScreen.class);
        startActivity(intent);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(O)
    private void setOrderOnScreen(Order order, List<Product> products){

        TableLayout tableLayout3 = findViewById(R.id.tableLayout3);
        RelativeLayout loader = findViewById(R.id.loadingPanel);

        loader.setVisibility(View.GONE);
        tableLayout3.setVisibility(View.VISIBLE);

        TextView orderId = findViewById(R.id.orderId), orderProductNames = findViewById(R.id.allergieNames), orderTotal = findViewById(R.id.orderTotal), orderStatus = findViewById(R.id.orderStatus), orderDate = findViewById(R.id.orderDate), orderDateFor = findViewById(R.id.orderDateFor);
        orderId.setText("Order #"+order.getOrderId());
        ArrayList<String> strings = new ArrayList<>();
        for (Product product : products){
            strings.add(product.getName());
        }
        orderProductNames.setText(String.join(", ", strings));
        DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
        orderTotal.setText("\u20ac " + df.format(order.getTotal()));
        orderStatus.setText(String.valueOf(order.getStatus()));
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = simpleDateFormat.format(order.getOrderedAt());
        orderDate.setText(date);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
        String date1 = simpleDateFormat1.format(order.getOrderedFor());
        orderDateFor.setText(date1);
    }

    private List<Integer> getOrder() {
        ObjectMapper orderMapper = new ObjectMapper();
        try {
            URL url = new URL("http://10.3.50.23:69/orders/"+orderId);
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
            order = orderMapper.readValue(content.toString(), Order.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return order.getProductIds();
    }

    private List<Product> getProducts(List<Integer> productIds)  {
        ObjectMapper productMapper = new ObjectMapper();
        ArrayList<Product> products = new ArrayList<>();
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

    @RequiresApi(O)
    private void getDataAsync(){
        CompletableFuture.supplyAsync(this::getOrder)
                .thenApply(this::getProducts)
                .thenAccept(products -> this.runOnUiThread(() -> setOrderOnScreen(order, products)));
    }
}