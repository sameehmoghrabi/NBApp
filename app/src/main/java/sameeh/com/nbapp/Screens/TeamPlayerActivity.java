package sameeh.com.nbapp.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import sameeh.com.nbapp.Models.Player;
import sameeh.com.nbapp.Models.TeamPlayersAdapter;
import sameeh.com.nbapp.R;

public class TeamPlayerActivity extends AppCompatActivity {
    private RecyclerView teamPlayersRecyclerView;
    private TeamPlayersAdapter adapter;
    private ArrayList<Player> teamPlayersList;
    private final int REQUEST_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_players_);

        Intent i = getIntent();
        teamPlayersList = (ArrayList<Player>) i.getSerializableExtra(MainActivity.PLAYERS_RESULT_KEY);
        Log.d("TAG", "onCreate: "+teamPlayersList);
        teamPlayersRecyclerView = findViewById(R.id.teamPlayers);
        teamPlayersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadRecyclerViewData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.teams:
                Intent intent = new Intent(this, MainActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadRecyclerViewData() {

        if (teamPlayersList != null) {
            showTeamInfo(teamPlayersList);
        }

    }

    private void showTeamInfo(ArrayList<Player> info) {

        List<Player> players = info;

        adapter = new TeamPlayersAdapter(players);
        teamPlayersRecyclerView.setAdapter(adapter);
        Intent i = getIntent();
        setResult(RESULT_OK, i);
    }
}
