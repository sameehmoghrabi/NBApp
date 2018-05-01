package sameeh.com.nbapp.api.LoggedIn;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import sameeh.com.nbapp.Models.Player;
import sameeh.com.nbapp.Models.User;

/**
 * Created by samee on 4/1/2018.
 */

public interface LoggedInApi {

    @GET("profile")
    Call<User> getProfile();

    @POST("players")
    Call<List<Player>> createNewPlayer(@Body Player player);

    @GET("players")
    Call<List<Player>> getPlayers();

    @Multipart
    @POST("profile/picture")
    Call<User> uploadProfilePicture(@Part MultipartBody.Part filePart);
}
