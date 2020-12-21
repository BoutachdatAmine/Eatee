package be.ehb.progproj2.eatee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import be.ehb.progproj2.eatee.views.customer.LoginScreen;

public class WelcomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        Animation logo_animation = AnimationUtils.loadAnimation(this, R.anim.logo_animation);
        ImageView logo = findViewById(R.id.logo);

        logo.setAnimation(logo_animation);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(WelcomeScreen.this, LoginScreen.class);
            startActivity(intent);
            finish();
        }, 2500);
    }
}