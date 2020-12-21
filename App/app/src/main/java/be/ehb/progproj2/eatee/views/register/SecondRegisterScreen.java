package be.ehb.progproj2.eatee.views.register;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import be.ehb.progproj2.eatee.R;
import be.ehb.progproj2.eatee.views.customer.HomeScreen;

public class SecondRegisterScreen extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_register_screen);

        TextView tv2FA = findViewById(R.id.TwoFactorKey);
        tv2FA.setText("Please copy the TFA key into Google Authenticator: " + getIntent().getStringExtra("TFA"));
    }

    public void goToHomeScreen(View view){
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        //User is not allowed to return to previous screen
    }
}