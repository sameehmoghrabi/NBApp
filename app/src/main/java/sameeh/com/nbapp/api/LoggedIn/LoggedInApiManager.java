package sameeh.com.nbapp.api.LoggedIn;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sameeh.com.nbapp.LocalData.LocalStorageManager;
import sameeh.com.nbapp.Models.Player;
import sameeh.com.nbapp.Models.User;

/**
 * Created by samee on 4/1/2018.
 */

public class LoggedInApiManager {
    private LocalStorageManager localStorageManager;
    private OkHttpClient client;
    private Retrofit retrofit;
    private LoggedInApi loggedInApi;
    private String token;
    private static LoggedInApiManager loggedInApiManager;

    private LoggedInApiManager(Context context) {

        localStorageManager = LocalStorageManager.getInstance(context);
        User user = localStorageManager.getUser();
        token = user.getToken();

        client = new OkHttpClient
                .Builder()
                .addNetworkInterceptor(new AuthInterceptor())
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/").client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loggedInApi = retrofit.create(LoggedInApi.class);
    }

    public Call<User> getProfile() {
        return loggedInApi.getProfile();
    }

    public Call<List<Player>> createPlayer(Player player) {
        return loggedInApi.createNewPlayer(player);
    }

    public Call<User> uploadProfilePicture(File file) {
        MultipartBody.Part filePart = MultipartBody
                .Part
                .createFormData("stream", file.getName(), RequestBody
                        .create(MediaType.parse("image/*"), file));
        return loggedInApi.uploadProfilePicture(filePart);
    }

    public static LoggedInApiManager getInstance(Context context) {
        if (loggedInApiManager == null) {
            loggedInApiManager = new LoggedInApiManager(context);
        }
        return loggedInApiManager;
    }

    public Call<List<Player>> getPlayers() {
        return loggedInApi.getPlayers();
    }

    private class AuthInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request authorization = chain.request().newBuilder().addHeader("Authorization", token).build();
            return chain.proceed(authorization);

        }
    }
}

