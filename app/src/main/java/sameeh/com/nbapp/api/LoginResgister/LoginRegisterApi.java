package sameeh.com.nbapp.api.LoginResgister;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import sameeh.com.nbapp.Models.User;

/**
 * Created by samee on 4/1/2018.
 */

public interface LoginRegisterApi{

        @POST("register")
        Call<User> register(@Body User user);

        @POST("login")
        Call<User> login(@Body User user);

}
