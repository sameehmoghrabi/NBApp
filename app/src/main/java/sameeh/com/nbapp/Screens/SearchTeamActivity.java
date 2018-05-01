package sameeh.com.nbapp.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sameeh.com.nbapp.Models.Player;
import sameeh.com.nbapp.R;
import sameeh.com.nbapp.api.NbaApiManager;

public class SearchTeamActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 100;
    public static final String PLAYERS_RESULT_KEY = "PLAYERS_RESULT_KEY";
    private NbaApiManager nbaApiManager;
    private EditText teamIdEditText;
    private ImageView searchImageView;
    private ProgressBar progressBar;
    String TAGG = "TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_team);

        nbaApiManager = new NbaApiManager();
        teamIdEditText = findViewById(R.id.teamID);
        searchImageView = findViewById(R.id.search_button);
        progressBar = findViewById(R.id.progressBar);
        searchImageView.setOnClickListener(e -> searchButtonListener());
    }

    private void searchButtonListener() {

        showProgressBar();
        String teamID = teamIdEditText.getText().toString();
        if(!TextUtils.isEmpty(teamID)){
            nbaApiManager.getTeamPlayers(teamID).enqueue(new Callback<ArrayList<Player>>() {
                @Override
                public void onResponse(Call<ArrayList<Player>> call, Response<ArrayList<Player>> response) {
                    if ((response.body() != null)) {
                        if(!response.body().isEmpty()){
                            //   Type TeamPlayersList = new TypeToken<ArrayList<Player>>() {}.getType();
                            //   ArrayList<Player> array = new Gson().fromJson(String.valueOf(response), TeamPlayersList);
                            ArrayList<Player> body = response.body();
                            Intent i = new Intent(getApplicationContext(), TeamPlayerActivity.class);
                            i.putExtra(PLAYERS_RESULT_KEY, body);
                            startActivityForResult(i, RESULT_OK);
                        }
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Examples of teams acronyms:\nTeam acronym for Clevland: cle \nTeam acronym for warriors: gsw \n" +
                                "Team acronym for miami: mia", Toast.LENGTH_LONG).show();
                    hideProgressBar();

                }

                @Override
                public void onFailure(Call<ArrayList<Player>> call, Throwable t) {
                    hideProgressBar();
                    Log.d(TAGG, "onFailure: failure");
                }
            });
        }else{
            hideProgressBar();
            Toast.makeText(getApplicationContext(), "Please enter a team acronym", Toast.LENGTH_LONG).show();
        }

    }

    private void showProgressBar() {

        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {

        progressBar.setVisibility(View.INVISIBLE);
    }
}
