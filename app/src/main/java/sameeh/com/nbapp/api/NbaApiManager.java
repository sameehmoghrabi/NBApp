package sameeh.com.nbapp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sameeh.com.nbapp.Models.TeamPlayersList;

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

    public Call<TeamPlayersList> getTeamPlayers (String teamID){
        return nbaApi.getTeamPlayers(teamID);
    }


}
