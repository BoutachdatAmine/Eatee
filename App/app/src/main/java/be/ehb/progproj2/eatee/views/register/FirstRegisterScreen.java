package be.ehb.progproj2.eatee.views.register;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

import be.ehb.progproj2.eatee.R;
import be.ehb.progproj2.eatee.entity.Customer;
import be.ehb.progproj2.eatee.entity.Register;

public class FirstRegisterScreen extends AppCompatActivity {

    private EditText etEmail, etPassword, etFirstName, etLastName;
    private static final String PREFERENCES = "Preferences", CUSTOMERID = "customerId";
    private String requestBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        etFirstName = findViewById(R.id.firstName);
        etLastName = findViewById(R.id.lastName);
        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.code);
    }

    @Override
    public void onBackPressed() {
        //User is not allowed to return to previous screen
    }

    @SuppressLint("ApplySharedPref")
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void goToSecondRegisterScreen(View view) throws JsonProcessingException {

        if (etEmail.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty() || etFirstName.getText().toString().isEmpty() || etLastName.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Fill in all fields", Toast.LENGTH_SHORT).show();
        }

        if (etEmail.getText().toString().contains("@")) {

            Register register = new Register(
                    etFirstName.getText().toString(),
                    etLastName.getText().toString(),
                    etPassword.getText().toString(),
                    etEmail.getText().toString(),
                    "true"
            );

            ObjectMapper registerMapper = new ObjectMapper();
            requestBody = registerMapper.writeValueAsString(register);

            register();
        }
    }

    @SuppressLint({"CommitPrefEdits", "ApplySharedPref"})
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void register() {
        CompletableFuture.supplyAsync(() -> {
            try {
                URL url = new URL("http://10.3.50.23:69/customers/");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json; utf-8");
                con.setDoOutput(true);
                con.setDoInput(true);

                OutputStream os = con.getOutputStream();
                os.write(requestBody.getBytes());
                os.flush();
                os.close();

                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
                String response = br.readLine();

                ObjectMapper customerMapper = new ObjectMapper();
                return customerMapper.readValue(response, Customer.class);
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }).thenAccept(customer1 -> {
            if(customer1.getCustomerId() == -1){
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Email address already exists.", Toast.LENGTH_SHORT).show());
            } else {
                String tfa = customer1.getTwoFactorKey();
                int customerId = customer1.getCustomerId();

                SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
                sharedPreferences.edit().putInt(CUSTOMERID, customerId);
                sharedPreferences.edit().commit();

                ActivityOptions options = ActivityOptions.makeCustomAnimation(this, R.anim.slide_from_right, R.anim.slide_to_left);
                Intent intent = new Intent(this, SecondRegisterScreen.class);
                intent.putExtra("TFA", tfa);
                startActivity(intent, options.toBundle());
            }
        });
    }
}