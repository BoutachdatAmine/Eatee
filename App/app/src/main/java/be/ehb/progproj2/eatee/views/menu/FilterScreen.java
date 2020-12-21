package be.ehb.progproj2.eatee.views.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import be.ehb.progproj2.eatee.R;
import be.ehb.progproj2.eatee.entity.Allergy;
import be.ehb.progproj2.eatee.views.customer.NavigationScreen;
import be.ehb.progproj2.eatee.views.customer.AccountScreen;

public class FilterScreen extends AppCompatActivity {

    public static final String ALLERGIES = "ALLERGIES", ORDERING = "ORDERING";
    private final LinkedHashMap<Integer, String> allergiesMap = new LinkedHashMap<>();
    private final HashSet<Integer> allergieSet = new HashSet<>();
    private int ordering;
    private final ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    private RadioButton low, high;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_screen);

        low = findViewById(R.id.radioLow);
        high = findViewById(R.id.radioHigh);

        getAllergiesAsync();
    }

    public void goToNavigationScreen(View view) {
        Intent intent = new Intent(this, NavigationScreen.class);
        startActivity(intent);
    }

    public void goToAccountScreen(View view) {
        Intent intent = new Intent(this, AccountScreen.class);
        startActivity(intent);
    }

    public void goToMenuDateScreen(View view) {
        ArrayList<Integer> allergies = new ArrayList<>(allergieSet);
        Intent intent = new Intent(this, MenuDateScreen.class);
        intent.putExtra(ORDERING, ordering);
        intent.putIntegerArrayListExtra(ALLERGIES, allergies);
        startActivity(intent);
    }

    private LinkedHashMap<Integer, String> getAllAllergies(){
        ObjectMapper allergiesMapper = new ObjectMapper();
        try {
            URL url = new URL("http://10.3.50.23:69/allergies/");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null) {
                content.append(inputLine);
            }
            bufferedReader.close();
            con.disconnect();
            List<Allergy> allergies = allergiesMapper.readValue(content.toString(), new TypeReference<List<Allergy>>(){});
            for (Allergy allergy : allergies){
                allergiesMap.put(allergy.getId(), allergy.getName());
            }
            return allergiesMap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressLint({"ResourceAsColor", "UseCompatLoadingForColorStateLists", "NewApi"})
    private void showAllergiesOnScreen(){
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        TextView allergies = findViewById(R.id.textViewAllergiess), price = findViewById(R.id.textViewPrice);
        ScrollView scrollView = findViewById(R.id.scrollview3);
        RadioGroup radioGroup = findViewById(R.id.radioGroup3);
        Button buttonDone = findViewById(R.id.buttonDone);
        RelativeLayout loader = findViewById(R.id.loadingPanel);

        loader.setVisibility(View.GONE);
        allergies.setVisibility(View.VISIBLE);
        price.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.VISIBLE);
        radioGroup.setVisibility(View.VISIBLE);
        buttonDone.setVisibility(View.VISIBLE);

        for (Map.Entry<Integer, String> entry : allergiesMap.entrySet()){
            CheckBox checkBox = new CheckBox(this);
            TableRow.LayoutParams layoutParams = (new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
            checkBox.setLayoutParams(layoutParams);
            checkBox.setOnClickListener(this::onCheckboxClicked);
            checkBox.setTextColor(R.color.RedPinky);
            checkBox.setButtonTintList(this.getResources().getColorStateList(R.color.RedPinky));
            checkBox.setId(entry.getKey());
            checkBox.setText(entry.getValue());
            checkBoxes.add(checkBox);
        }

        for (CheckBox checkBox1 : checkBoxes) {
            TableRow tableRow = new TableRow(this);
            TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            tableRow.setLayoutParams(params);
            tableRow.addView(checkBox1);
            tableLayout.addView(tableRow);
        }
    }

    public void onCheckboxClicked(View view) {
        for (CheckBox allergyCheckbox : checkBoxes) {
            if (allergyCheckbox.isChecked()) {
                for (Map.Entry<Integer, String> entry : allergiesMap.entrySet())
                    if(allergyCheckbox.getId() == entry.getKey())
                        allergieSet.add(entry.getKey());
            } else {
                for (Map.Entry<Integer, String> entry : allergiesMap.entrySet())
                    if(allergyCheckbox.getId() == entry.getKey())
                        allergieSet.remove(entry.getKey());
            }
        }
    }

    public void onRadioButtonClicked(View view){
        if (low.isChecked()) {
            ordering = 1;
        } else if (high.isChecked()) {
            ordering = -1;
        } else {
            ordering = 0;
        }
    }

    @SuppressLint("NewApi")
    private void getAllergiesAsync(){
        CompletableFuture.supplyAsync(this::getAllAllergies)
                .thenAccept(integerStringLinkedHashMap -> runOnUiThread(this::showAllergiesOnScreen));
    }

}