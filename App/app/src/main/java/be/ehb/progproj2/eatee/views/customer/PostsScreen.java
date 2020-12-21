package be.ehb.progproj2.eatee.views.customer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import be.ehb.progproj2.eatee.R;
import be.ehb.progproj2.eatee.entity.Post;

public class PostsScreen extends AppCompatActivity {

    private static final String CUSTOMERID = "customerId", PREFERENCES = "Preferences";
    private int customerId;
    private List<Integer> likedPosts = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_screen);

        isLikedAsync();

        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        customerId = sharedPreferences.getInt(CUSTOMERID, -1);

        setDataAsync();
    }

    public void goToNavigationScreen(View view){
        Intent intent = new Intent(this, NavigationScreen.class);
        startActivity(intent);
    }

    public void goToAccountScreen(View view){
        Intent intent = new Intent(this, AccountScreen.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    private void setPosts(List<Post> posts){
        LinearLayout linearscroll = findViewById(R.id.linearscroll);

        for (Post post : posts){
            LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            cardParams.setMargins(30, 30, 30, 30);

            CardView card = new CardView(new ContextThemeWrapper(this, R.style.CardViewStyle), null, 0);
            card.setLayoutParams(cardParams);

            LinearLayout cardInner = new LinearLayout(new ContextThemeWrapper(this, R.style.Widget_CardContent));

            TextView title = new TextView(this);
            title.setTextAppearance(R.style.TextAppearance_AppCompat_Title);
            title.setText(post.getTitle());
            title.setTextColor(R.color.RedPinky);
            title.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            TextView description = new TextView(this);
            description.setTextAppearance(R.style.TextAppearance_AppCompat_Body1);
            description.setText(post.getDescription());
            description.setTextColor(R.color.RedPinky);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 30,0,0);
            description.setLayoutParams(params);

            TextView likeCounter = new TextView(this);
            LinearLayout.LayoutParams counterLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            counterLayout.weight = 1;
            counterLayout.setMargins(0, 30, 0, 0);
            likeCounter.setText(String.valueOf(post.getLikes()));
            likeCounter.setLayoutParams(counterLayout);

            ImageView like = new ImageView(this);
            LinearLayout.LayoutParams imgLayout = new LinearLayout.LayoutParams(75, 75);
            imgLayout.weight = 1;
            like.setLayoutParams(imgLayout);
            like.setClickable(true);
            like.setFocusable(true);
            like.setTag(R.id.postId, post.getId());

            if(likedPosts.contains(post.getId())){
                like.setTag(R.bool.liked, true);
                like.setBackgroundResource(R.drawable.bluepinkylike);
            }
            else {
                like.setTag(R.bool.liked, false);
                like.setBackgroundResource(R.drawable.redpinkylike);
            }
            like.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {
                    int likes = Integer.parseInt((String) likeCounter.getText());
                    if(like.getTag(R.bool.liked).equals(false)){
                        runOnUiThread(() -> like.setBackgroundResource(R.drawable.bluepinkylike));
                        likes++;
                        likeCounter.setText(String.valueOf(likes));
                        likeAsync(post.getId());
                        like.setTag(R.bool.liked, true);
                    }
                    else {
                        runOnUiThread(() -> like.setBackgroundResource(R.drawable.redpinkylike));
                        likes--;
                        likeCounter.setText(String.valueOf(likes));
                        removeLikeAsync(post.getId());
                        like.setTag(R.bool.liked, false);
                    }
                }
            });

            cardInner.addView(title);
            cardInner.addView(description);
            cardInner.addView(likeCounter);
            cardInner.addView(like);

            card.addView(cardInner);

            linearscroll.addView(card);
        }
    }

    private List<Post> getPosts(){
        ObjectMapper postsMapper = new ObjectMapper();
        try {
            URL url = new URL("http://10.3.50.23:69/posts/");
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
            return postsMapper.readValue(content.toString(), new TypeReference<List<Post>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Integer> isLiked(){
        ObjectMapper likedPostsMapper = new ObjectMapper();
        try {
            URL url = new URL("http://10.3.50.23:69/posts/liked/" + customerId);
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
            return likedPostsMapper.readValue(content.toString(), new TypeReference<List<Integer>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void isLikedAsync(){
        CompletableFuture.supplyAsync(this::isLiked)
                .thenAccept(integerList -> likedPosts = integerList);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setDataAsync(){
        CompletableFuture.supplyAsync(this::getPosts)
                .thenAccept(posts -> this.runOnUiThread(() -> setPosts(posts)));
    }

    private boolean likePost(int postId){
        try {
            URL url = new URL("http://10.3.50.23:69/posts/" + postId + "/like/" + customerId);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.getInputStream().close();;
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean removeLikePost(int postId){
        try {
            URL url = new URL("http://10.3.50.23:69/posts/" + postId + "/removelike/" + customerId);
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
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void likeAsync(int postId){
        CompletableFuture.supplyAsync(() -> likePost(postId));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void removeLikeAsync(int postId){
        CompletableFuture.supplyAsync(() -> removeLikePost(postId));
    }
}