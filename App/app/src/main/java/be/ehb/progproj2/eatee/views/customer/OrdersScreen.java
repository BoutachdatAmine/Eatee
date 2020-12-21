package be.ehb.progproj2.eatee.views.customer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

import be.ehb.progproj2.eatee.R;
import be.ehb.progproj2.eatee.entity.Order;

public class OrdersScreen extends AppCompatActivity {

    public static final String ORDERID = "orderId";
    private static final String CUSTOMERID = "customerId";
    private static final String PREFERENCES = "Preferences";
    private int customerId;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_screen);

        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        customerId = sharedPreferences.getInt(CUSTOMERID, -1);

        calculateOrdersAsync();
    }

    public void goToNavigationScreen(View view){
        Intent intent = new Intent(this, NavigationScreen.class);
        startActivity(intent);
    }

    public void goToAccountScreen(View view){
        Intent intent = new Intent(this, AccountScreen.class);
        startActivity(intent);
    }

    public void goToOrderScreen(int orderId){
        Intent intent = new Intent(this, OrderScreen.class);
        intent.putExtra(ORDERID, orderId);
        startActivity(intent);
    }

    private void showOrdersOnScreen(List<Order> orders){

        RelativeLayout relativeLayout = findViewById(R.id.loadingPanel);
        @SuppressLint("CutPasteId") TableLayout tableLayout4 = findViewById(R.id.tableLayout4);
        TextView textViewAllergiess = findViewById(R.id.textViewAllergiess);

        textViewAllergiess.setVisibility(View.VISIBLE);
        tableLayout4.setVisibility(View.VISIBLE);
        relativeLayout.setVisibility(View.GONE);

        for (Order order : orders) {
            TableRow tableRow = new TableRow(this);
            tableRow.setPadding(5, 5, 5, 5);
            tableRow.setClickable(true);
            tableRow.setFocusable(true);
            int orderId = order.getOrderId();
            tableRow.setOnClickListener(view -> goToOrderScreen(orderId));

            TextView textView = new TextView(this);
            textView.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
            textView.setText(String.valueOf(order.getOrderId()));
            textView.setTextColor(Color.parseColor("#3F51B5"));
            textView.setTextSize(14);
            tableRow.addView(textView);

            TextView textView1 = new TextView(this);
            textView1.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            textView1.setText(simpleDateFormat.format(order.getOrderedAt()));
            textView1.setTextColor(Color.parseColor("#3F51B5"));
            textView1.setTextSize(14);
            tableRow.addView(textView1);

            TextView textView3 = new TextView(this);
            textView3.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
            textView3.setText(simpleDateFormat.format(order.getOrderedFor()));
            textView3.setTextColor(Color.parseColor("#3F51B5"));
            textView3.setTextSize(14);
            tableRow.addView(textView3);

            TextView textView2 = new TextView(this);
            textView2.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
            DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
            textView2.setText(df.format(order.getTotal()));
            textView2.setTextColor(Color.parseColor("#3F51B5"));
            textView2.setTextSize(14);
            tableRow.addView(textView2);

            TextView textView4 = new TextView(this);
            textView4.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
            textView4.setText(String.valueOf(order.getStatus()));
            textView4.setTextColor(Color.parseColor("#3F51B5"));
            textView4.setTextSize(14);
            tableRow.addView(textView4);

            @SuppressLint("CutPasteId") TableLayout tableLayout = findViewById(R.id.tableLayout4);
            tableLayout.addView(tableRow);
        }
    }

    private List<Order> getCustomerOrders() {
        ObjectMapper customerMapper = new ObjectMapper();
        try {
            URL url = new URL("http://10.3.50.23:69/customers/" + customerId + "/orders");
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
            return customerMapper.readValue(content.toString(), new TypeReference<List<Order>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void calculateOrdersAsync() {
        CompletableFuture.supplyAsync(this::getCustomerOrders)
                .thenAccept(orders ->  this.runOnUiThread(() -> showOrdersOnScreen(orders)));
    }
}