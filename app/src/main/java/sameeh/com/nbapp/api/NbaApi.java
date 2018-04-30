package sameeh.com.nbapp.api;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import sameeh.com.nbapp.Models.Player;

/**
 * Created by samee on 4/30/2018.
 */

public interface NbaApi {

    @GET("players/{teamID}")
    Call<ArrayList<Player>> getTeamPlayers(@Path("teamID") String teamID);


}
