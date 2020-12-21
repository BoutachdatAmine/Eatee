package be.ehb.progproj2.eatee.views.customer;

import android.annotation.SuppressLint;
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
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import be.ehb.progproj2.eatee.R;
import be.ehb.progproj2.eatee.entity.AuthUser;


public class TwoFactorScreen extends AppCompatActivity {
    private static final String PREFERENCES = "Preferences";
    private static final String CUSTOMERID = "customerId";
    private String requestBody;
    private int customerId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_factor_screen);
    }

    @Override
    public void onBackPressed() {
        //User is not allowed to return to previous screen
    }

    @SuppressLint("ApplySharedPref")
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void goToHomeScreen(View view) throws JsonProcessingException {

        EditText etTFA = findViewById(R.id.code);

        if (etTFA.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Fill a 6 digit code in", Toast.LENGTH_SHORT).show();
        }

        AuthUser authUser = new AuthUser(
                getIntent().getStringExtra("Email"),
                getIntent().getStringExtra("Password"),
                etTFA.getText().toString()
        );

        ObjectMapper userMapper = new ObjectMapper();
        requestBody = userMapper.writeValueAsString(authUser);

        twoFactorAuthentication();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void twoFactorAuthentication(){
        CompletableFuture.supplyAsync(() -> {
            try {
                URL url = new URL("http://10.3.50.23:69/auth/customer");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestProperty("Content-Type", "application/json; utf-8");
                con.setDoOutput(true);
                con.setDoInput(true);

                OutputStream os = con.getOutputStream();
                os.write(requestBody.getBytes());
                os.flush();
                os.close();

                try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                    String response = br.readLine();
                    if (Integer.parseInt(response) > 0 || Integer.parseInt(response) == -6) {
                        customerId = Integer.parseInt(response);
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return customerId;
        }).thenAccept(integer -> {
            if(integer == -6){
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Incorrect key", Toast.LENGTH_SHORT).show());
            } else if(integer > 0) {
                SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(CUSTOMERID, integer);
                editor.apply();

                Intent intent = new Intent(this, HomeScreen.class);
                startActivity(intent);
            }
        });
    }
}
