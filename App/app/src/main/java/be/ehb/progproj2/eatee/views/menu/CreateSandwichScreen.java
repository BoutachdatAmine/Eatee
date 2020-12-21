package be.ehb.progproj2.eatee.views.menu;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import be.ehb.progproj2.eatee.R;
import be.ehb.progproj2.eatee.entity.Ingredient;
import be.ehb.progproj2.eatee.views.customer.NavigationScreen;
import be.ehb.progproj2.eatee.views.customer.AccountScreen;

import static android.os.Build.VERSION_CODES.O;

public class CreateSandwichScreen extends AppCompatActivity {

    private final List<CheckBox> checkBoxes = new ArrayList<>();
    private final HashSet<String> names = new HashSet<>();
    private final HashSet<Integer> ingredientIds = new HashSet<>();
    private static final String CUSTOMERID = "customerId";
    private static final String PREFERENCES = "Preferences";
    private int customerId;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_sandwich_screen);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getIngredientsAsync();
        }

        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        customerId = sharedPreferences.getInt(CUSTOMERID, -1);

        Button addToCart = findViewById(R.id.buttonAddToCart);
        addToCart.setOnClickListener(v -> {
            async();
            Toast.makeText(v.getContext(), "Sandwich with " + String.join(", ", names) + " was added to your cart!", Toast.LENGTH_SHORT).show();
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

    private void addToCart(int sandwichId){
        try {
            URL url = new URL("http://10.3.50.23:69/customers/" + customerId + "/cart/s/" + sandwichId);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.getInputStream().close();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("NewApi")
    public Integer addCustomSandwich() {
        try {
            URL url = new URL("http://10.3.50.23:69/customers/" + customerId + "/custom");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            List<String> strings = new ArrayList<>();
            for (Integer integer : ingredientIds) {
                strings.add(String.valueOf(integer));
            }

            String json = "{\"ingredients\":[" + String.join(", ", strings) + "]}";

            OutputStream os = con.getOutputStream();
            byte[] input;
            input = json.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            String response = br.readLine();
            return Integer.parseInt(response);
            } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Ingredient> getIngredients() {
        ObjectMapper ingredientMapper = new ObjectMapper();
        try {
            URL url = new URL("http://10.3.50.23:69/ingredients/?sandwichIngredient");
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
            return ingredientMapper.readValue(content.toString(), new TypeReference<List<Ingredient>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressLint({"NewApi", "UseCompatLoadingForColorStateLists"})
    private void setIngredients(List<Ingredient> ingredients){
        TextView sandwichWith = findViewById(R.id.sandwichWith);
        Button addToCart = findViewById(R.id.buttonAddToCart);
        RelativeLayout loading = findViewById(R.id.loadingPanel);
        TableLayout tableLayout = findViewById(R.id.tableLayout);

        loading.setVisibility(View.GONE);
        sandwichWith.setVisibility(View.VISIBLE);
        addToCart.setVisibility(View.VISIBLE);

        tableLayout.setVisibility(View.VISIBLE);

        for (Ingredient ingredient : ingredients) {
                CheckBox checkBox = new CheckBox(this);
                checkBox.setText(ingredient.getName());
                checkBox.setButtonTintList(this.getResources().getColorStateList(R.color.RedPinky));
                checkBox.setTextColor(this.getResources().getColorStateList(R.color.RedPinky));
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View v) {
                        onCheckboxClicked(ingredients);
                        sandwichWith.setText("Sandwich with " + String.join(", ", names));
                    }
                });
                checkBoxes.add(checkBox);
        }

        for (int i = 0; i < checkBoxes.size(); i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setPadding(5, 5, 5, 5);
            tableRow.addView(checkBoxes.get(i));
            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
            tableRowParams.setMargins(0, 0, 0, 15);
            tableRow.setLayoutParams(tableRowParams);
            tableLayout.addView(tableRow);
        }
    }

    public void onCheckboxClicked(List<Ingredient> ingredients) {
        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isChecked()) {
                for (Ingredient ingredient : ingredients){
                    if(checkBox.getText().equals(ingredient.getName())){
                        names.add((String) checkBox.getText());
                        ingredientIds.add(ingredient.getIngredientId());
                    }
                }
            } else if (!checkBox.isChecked()){
                for (Ingredient ingredient : ingredients) {
                    if(checkBox.getText().equals(ingredient.getName())) {
                        names.remove(checkBox.getText());
                        ingredientIds.remove(ingredient.getIngredientId());
                    }
                }
            }
        }
    }

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getIngredientsAsync() {
        CompletableFuture.supplyAsync(this::getIngredients)
                .thenAccept(ingredients -> this.runOnUiThread(() -> setIngredients(ingredients)));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void async(){
        CompletableFuture.supplyAsync(this::addCustomSandwich)
                .thenAccept(this::addToCart);
    }
}