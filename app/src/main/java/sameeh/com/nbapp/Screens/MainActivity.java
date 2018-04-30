package sameeh.com.nbapp.Screens;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sameeh.com.nbapp.Models.TeamPlayersList;
import sameeh.com.nbapp.R;
import sameeh.com.nbapp.api.NbaApiManager;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 100;
    public static final String PLAYERS_RESULT_KEY = "PLAYERS_RESULT_KEY";
    private NbaApiManager nbaApiManager;
    private EditText teamIdEditText;
    private ImageView searchImageView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            nbaApiManager.getTeamPlayers(teamID).enqueue(new Callback<TeamPlayersList>() {
                @Override
                public void onResponse(Call<TeamPlayersList> call, Response<TeamPlayersList> response) {
                    if (response.isSuccessful()) {
                        TeamPlayersList body = response.body();
                        //  Intent i = new Intent(getApplicationContext(), MoviesActivity.class);
                        //  i.putExtra(PLAYERS_RESULT_KEY, body);
                        //  startActivityForResult(i, RESULT_OK);
                    }
                    hideProgressBar();

                }

                @Override
                public void onFailure(Call<TeamPlayersList> call, Throwable t) {

                }
            });
        }else{
            hideProgressBar();
            Toast.makeText(getApplicationContext(), "Please enter a teamID", Toast.LENGTH_LONG).show();
        }

    }

    private void showProgressBar() {

        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {

        progressBar.setVisibility(View.INVISIBLE);
    }
}
