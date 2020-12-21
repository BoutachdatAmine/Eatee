package be.ehb.progproj2.eatee.views.customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import be.ehb.progproj2.eatee.R;
import be.ehb.progproj2.eatee.views.menu.MenuScreen;

public class NavigationScreen  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_screen);

    }

    public void goToAccountScreen(View view){
        Intent intent = new Intent(this, AccountScreen.class);
        startActivity(intent);
    }

    public void goToHomeScreen(View view){
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }

    public void goToCartScreen(View view){
        Intent intent = new Intent(this, CartScreen.class);
        startActivity(intent);
    }

    public void goToMenuScreen(View view){
        Intent intent = new Intent(this, MenuScreen.class);
        startActivity(intent);
    }
}
