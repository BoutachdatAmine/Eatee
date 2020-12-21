package be.ehb.progproj2.eatee.views.customer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

import be.ehb.progproj2.eatee.R;

import be.ehb.progproj2.eatee.entity.AuthUser;

import be.ehb.progproj2.eatee.views.register.FirstRegisterScreen;

public class LoginScreen extends AppCompatActivity {

    private static final String PREFERENCES = "Preferences", CUSTOMERID = "customerId";
    private String requestBody;
    private int customerId = -1;
    private EditText etEmail, etPassword;

    @SuppressLint({"ClickableViewAccessibility", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
    }

    public void goToFirstRegisterScreen(View view) {
        ActivityOptions options = ActivityOptions.makeCustomAnimation(this, R.anim.slide_from_right, R.anim.slide_to_left);
        Intent intentRegister = new Intent(this, FirstRegisterScreen.class);
        startActivity(intentRegister, options.toBundle());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void goToHomeScreen(View view) throws JsonProcessingException{

        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.code);

        if (etEmail.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Fill in all fields", Toast.LENGTH_SHORT).show();
        }

        AuthUser authUser = new AuthUser(
                etEmail.getText().toString(),
                etPassword.getText().toString()
        );

        ObjectMapper userMapper = new ObjectMapper();
        requestBody = userMapper.writeValueAsString(authUser);

        async();
    }

    @SuppressLint({"CommitPrefEdits", "ApplySharedPref"})
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void async() {
        CompletableFuture.supplyAsync(() -> {
            try {
                URL url = new URL("http://10.3.50.23:69/auth/customer");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestProperty("Content-Type", "application/json; utf-8");
                con.setDoOutput(true);
                OutputStream os = con.getOutputStream();
                os.write(requestBody.getBytes());
                os.flush();
                os.close();
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
                String response = br.readLine();
                br.close();
                if (response.equals("-5")) {
                    customerId = Integer.parseInt(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return customerId;
        }).thenAccept(integer -> {
            if (integer == -5) {
                SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
                sharedPreferences.edit().putInt(CUSTOMERID, integer);
                sharedPreferences.edit().commit();

                ActivityOptions options = ActivityOptions.makeCustomAnimation(this, R.anim.slide_from_right, R.anim.slide_to_left);
                Intent intent = new Intent(this, TwoFactorScreen.class);
                intent.putExtra("Email", etEmail.getText().toString());
                intent.putExtra("Password", etPassword.getText().toString());
                startActivity(intent, options.toBundle());
            } else {
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Incorrect credentials", Toast.LENGTH_SHORT).show());
            }
        });
    }

    @Override
    public void onBackPressed() {
        //User is not allowed to return to previous screen
    }
}