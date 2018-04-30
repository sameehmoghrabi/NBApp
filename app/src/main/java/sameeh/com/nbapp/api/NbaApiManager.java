package sameeh.com.nbapp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sameeh.com.nbapp.Models.Player;

/**
 * Created by samee on 4/30/2018.
 */

public class NbaApiManager {

    private Retrofit retrofit;
    private NbaApi nbaApi;

    private final String MOVIES_BASE_URL = "http://api.suredbits.com/nba/v0/";

    public NbaApiManager(){
        Gson gson = new GsonBuilder().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(MOVIES_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        nbaApi = retrofit.create(NbaApi.class);
    }

    public Call<ArrayList<Player>> getTeamPlayers (String teamID){
        return nbaApi.getTeamPlayers(teamID);
    }


}
