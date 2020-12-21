package be.ehb.progproj2.eatee.views.customer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import be.ehb.progproj2.eatee.R;

public class AccountScreen extends AppCompatActivity {

    private static final String PREFERENCES = "Preferences", CUSTOMERID = "customerId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_screen);
    }

    public void goToNavigationScreen(View view){
        Intent intent = new Intent(this, NavigationScreen.class);
        startActivity(intent);
    }

    public void goToOrdersScreen(View view){
        Intent intent = new Intent(this, OrdersScreen.class);
        startActivity(intent);
    }

    public void goToChangePasswordScreen(View view){
        Intent intent = new Intent(this, ChangePasswordScreen.class);
        startActivity(intent);
    }

    public void goToPostsScreen(View view){
        Intent intent = new Intent(this, PostsScreen.class);
        startActivity(intent);
    }

    @SuppressLint({"CommitPrefEdits", "ApplySharedPref"})
    public void logoutOfAccount(View view){
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(CUSTOMERID);
        sharedPreferences.edit().commit();

        Intent intent = new Intent(this,LoginScreen.class);
        startActivity(intent);
        finish();
    }
}
