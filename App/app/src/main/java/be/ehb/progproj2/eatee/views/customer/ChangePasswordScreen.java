package be.ehb.progproj2.eatee.views.customer;

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

import be.ehb.progproj2.eatee.R;

public class ChangePasswordScreen extends AppCompatActivity {

    private EditText password, newPassword, confirmNewPassword;
    private static final String PREFERENCES = "Preferences", CUSTOMERID = "customerId";
    private int customerId;
    private String requestBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_screen);

        customerId = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE).getInt(CUSTOMERID, -1);
    }

    public void goToNavigationScreen(View view) {
        Intent intent = new Intent(this, NavigationScreen.class);
        startActivity(intent);
    }

    public void goToAccountScreen(View view) {
        Intent intent = new Intent(this, AccountScreen.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void goToHomeScreen(View view) {
        password = findViewById(R.id.oldPasswordTxt);
        newPassword = findViewById(R.id.newPasswordTxt);
        confirmNewPassword = findViewById(R.id.passwordConfirmationTxt);

        if (password.getText().toString().isEmpty() || newPassword.getText().toString().isEmpty() || confirmNewPassword.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Fill in all fields", Toast.LENGTH_SHORT).show();
        } else if (!newPassword.getText().toString().equals(confirmNewPassword.getText().toString())) {
            Toast.makeText(this, "New passwords don't match!", Toast.LENGTH_SHORT).show();
        } else passwordAsync();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean checkOldPassword() {
        try {
            URL url = new URL("http://10.3.50.23:69/auth/customer/" + customerId + "/password");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            requestBody = "{\"password\":\"" + password.getText().toString() + "\"}";
            os.write(requestBody.getBytes());
            os.flush();
            os.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            String response = br.readLine();
            br.close();
            return Boolean.parseBoolean(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void changePassword() {
        try {
            URL url = new URL("http://10.3.50.23:69/customers/" + customerId);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestMethod("PATCH");
            con.setDoOutput(true);
            con.setDoInput(true);
            OutputStream os = con.getOutputStream();
            requestBody = "{\"password\":\"" + confirmNewPassword.getText().toString() + "\"}";
            os.write(requestBody.getBytes());
            os.flush();
            os.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void passwordAsync() {
        CompletableFuture.supplyAsync(this::checkOldPassword).thenAccept(aBoolean -> {
            if (aBoolean) {
                changePassword();
                runOnUiThread(() -> Toast.makeText(this, "Your password was succesfully changed", Toast.LENGTH_SHORT).show());
                Intent intent = new Intent(this, HomeScreen.class);
                startActivity(intent);
            } else {
                runOnUiThread(() -> Toast.makeText(this, "Old Password is incorrect!", Toast.LENGTH_SHORT).show());
            }
        });
    }
}
