package sameeh.com.nbapp.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import sameeh.com.nbapp.Models.TeamPlayersList;

/**
 * Created by samee on 4/30/2018.
 */

public interface NbaApi {

    @GET("players/{teamID}")
    Call<TeamPlayersList> getTeamPlayers(@Path("teamID") String teamID);


}
