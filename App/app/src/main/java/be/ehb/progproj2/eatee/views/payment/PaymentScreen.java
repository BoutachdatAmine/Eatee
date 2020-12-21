package be.ehb.progproj2.eatee.views.payment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Token;
import com.stripe.param.CustomerRetrieveParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import be.ehb.progproj2.eatee.R;
import be.ehb.progproj2.eatee.views.customer.AccountScreen;
import be.ehb.progproj2.eatee.views.customer.HomeScreen;
import be.ehb.progproj2.eatee.views.customer.NavigationScreen;

import static android.os.Build.VERSION_CODES.O;

public class PaymentScreen extends AppCompatActivity {

    private EditText etCardNumber, etCvc, etExpiry, orderedFor;
    private double cartTotal;
    private static final String TOTAL = "total", PREFERENCES = "Preferences", CUSTOMERID = "customerId";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_screen);

        Intent intent = getIntent();
        cartTotal = intent.getDoubleExtra(TOTAL, -1);
        TextView total = findViewById(R.id.total);
        DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
        total.setText(df.format(cartTotal));

        etCardNumber = findViewById(R.id.cardNumber);
        etCvc = findViewById(R.id.cvc);
        etExpiry = findViewById(R.id.expiry);
        orderedFor = findViewById(R.id.orderedFor);
        etCardNumber.setText("4242424242424242");
        etExpiry.setText("11/22");
        etCvc.setText("123");

        Stripe.apiKey = "GET YOUR OWN KEY FROM STRIPE API";
    }

    public void goToNavigationScreen(View view){
        Intent intent = new Intent(this, NavigationScreen.class);
        startActivity(intent);
    }

    public void goToAccountScreen(View view){
        Intent intent = new Intent(this, AccountScreen.class);
        startActivity(intent);
    }

    @RequiresApi(O)
    public void goToPaymentDoneScreen(View view) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Date.valueOf(orderedFor.getText().toString()));
        
        if(etCardNumber.getText().toString().length() != 16 || etExpiry.getText().toString().indexOf("/") != 2
                || !etExpiry.getText().toString().substring(0,1).chars().allMatch(Character::isDigit)
                || !etExpiry.getText().toString().substring(3,5).chars().allMatch(Character::isDigit)
                || etCvc.getText().toString().length() != 3){

            Toast.makeText(getApplicationContext(), "Incorrect card details", Toast.LENGTH_SHORT).show();
            return;
        } else if(orderedFor.getText().toString().isEmpty() || orderedFor.getText().toString().length() != 10 || LocalDate.parse(orderedFor.getText()).isBefore(LocalDate.now())){
            Toast.makeText(getApplicationContext(), "Incorrect date", Toast.LENGTH_SHORT).show();
            return;
        }

        paymentAsync();

        ImageView navigation = findViewById(R.id.imageViewNavigation), account = findViewById(R.id.imageViewAccount);
        TextView orderFor = findViewById(R.id.textView2), cardNumber = findViewById(R.id.textView3), expiry = findViewById(R.id.textView5), cvc = findViewById(R.id.textView4), total = findViewById(R.id.total), total2 = findViewById(R.id.textView7);
        Button pay = findViewById(R.id.Pay);
        RelativeLayout loader = findViewById(R.id.loadingPanel);
        EditText card = findViewById(R.id.cardNumber), expiry2 = findViewById(R.id.expiry), cvc2 = findViewById(R.id.cvc), orderedFor = findViewById(R.id.orderedFor);

        navigation.setVisibility(View.GONE);
        account.setVisibility(View.GONE);
        orderFor.setVisibility(View.GONE);
        cardNumber.setVisibility(View.GONE);
        expiry.setVisibility(View.GONE);
        cvc.setVisibility(View.GONE);
        total.setVisibility(View.GONE);
        total2.setVisibility(View.GONE);
        pay.setVisibility(View.GONE);
        card.setVisibility(View.GONE);
        expiry2.setVisibility(View.GONE);
        cvc2.setVisibility(View.GONE);
        orderedFor.setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private Customer createNewCustomer() {
        Map<String, Object> customerParameter = new HashMap<>();
        customerParameter.put("email", "t3@email.com");
        Customer newCustomer = null;
        try {
            newCustomer = Customer.create(customerParameter);
        } catch (StripeException e) {
            e.printStackTrace();
        }

        CustomerRetrieveParams retrieveParams = CustomerRetrieveParams
                .builder()
                .addExpand("sources")
                .build();
        try {
            return Customer.retrieve(newCustomer.getId(), retrieveParams, null);
        } catch (StripeException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private Customer createPayment(Customer customer) {
        Map<String, Object> cardParam = new HashMap<>();
        cardParam.put("number", etCardNumber.getText().toString());

        cardParam.put("exp_month", Integer.parseInt(etExpiry.getText().toString().substring(0,1)));

        cardParam.put("exp_year", Integer.parseInt("20" + etExpiry.getText().toString().substring(3,5)));

        cardParam.put("cvc", etCvc.getText().toString());

        Map<String, Object> tokenParam = new HashMap<>();
        tokenParam.put("card", cardParam);
        Token token = getToken(tokenParam);

        Map<String, Object> source = new HashMap<>();
        if(token != null) source.put("source", token.getId());

        try {
            customer.getSources().create(source);
        } catch (StripeException e) {
            e.printStackTrace();
        }

        Map<String, Object> chargeParam = new HashMap<>();
        chargeParam.put("amount", (int)(cartTotal * 100));
        chargeParam.put("currency", "eur");
        chargeParam.put("customer", customer.getId());

        try {
            Charge.create(chargeParam);
        } catch (StripeException e) {
            e.printStackTrace();
        }

        return customer;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private Token getToken(Map<String, Object> tokenParam) {
        try {
            return Token.create(tokenParam);
        } catch (StripeException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private boolean deleteCustomer(Customer customer) {
        try {
            customer.delete();
            return true;
        } catch (StripeException e) {
            e.printStackTrace();
            return false;
        }
    }

    @RequiresApi(api = O)
    private boolean addOrder(){
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        int customerId = sharedPreferences.getInt(CUSTOMERID, -1);

        String requestBody = "{\"customerId\": " + customerId + ", \"status\": \"PAID\", \"orderedFor\": \"" + orderedFor.getText().toString() + "\"}";

        try {
            URL url = new URL("http://10.3.50.23:69/orders/");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            os.write(requestBody.getBytes());
            os.flush();
            os.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    private void paymentDone(){
        TextView paymentDone = findViewById(R.id.textView9);
        Button buttonGoBack = findViewById(R.id.buttonGoBack);
        RelativeLayout loader = findViewById(R.id.loadingPanel);
        loader.setVisibility(View.GONE);
        paymentDone.setVisibility(View.VISIBLE);
        buttonGoBack.setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = O)
    private void paymentAsync(){
        CompletableFuture.supplyAsync(this::createNewCustomer)
                .thenApply(this::createPayment)
                .thenApply(this::deleteCustomer)
                .thenApply(aBoolean -> addOrder())
                .thenAccept(aBoolean -> runOnUiThread(this::paymentDone));
    }

    @Override
    public void onBackPressed() {
        //User is not allowed to return to previous screen
    }

    public void goBackToHomeScreen(View view){
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }
}
