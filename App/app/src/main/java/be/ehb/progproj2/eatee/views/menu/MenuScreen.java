package be.ehb.progproj2.eatee.views.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import be.ehb.progproj2.eatee.R;
import be.ehb.progproj2.eatee.views.customer.NavigationScreen;
import be.ehb.progproj2.eatee.views.customer.AccountScreen;

public class MenuScreen extends AppCompatActivity {

    public static final String SELECTED_DATE = "selectedDate";
    public String selectedDate = "";
    protected boolean dateChanged = false;
    @SuppressLint("SimpleDateFormat")
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);

        CalendarView calendarView = findViewById(R.id.calendarViewMenu);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfTheMonth) {
                selectedDate = dayOfTheMonth + "/" + (month+1) + "/" + year;
                dateChanged = true;
            }
        });
    }

    public void goToNavigationScreen(View view){
        Intent intent = new Intent(this, NavigationScreen.class);
        startActivity(intent);
    }

    public void goToAccountScreen(View view){
        Intent intent = new Intent(this, AccountScreen.class);
        startActivity(intent);
    }

    public void goToMenuDateScreen(View view){
        CalendarView calendarView = findViewById(R.id.calendarViewMenu);
        Date date = new Date(calendarView.getDate());
        Intent intent = new Intent(this, MenuDateScreen.class);
        if(dateChanged)
            intent.putExtra(SELECTED_DATE, selectedDate);
        else intent.putExtra(SELECTED_DATE, dateFormat.format(date));
        startActivity(intent);
    }
}