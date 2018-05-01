package sameeh.com.nbapp.api.LoginResgister;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sameeh.com.nbapp.Models.User;

/**
 * Created by samee on 4/1/2018.
 */

public class LoginRegisterApiManager {
    private Retrofit retrofit;
    private LoginRegisterApi loginRegisterApi;

    private static LoginRegisterApiManager loginRegisterApiManager;

    private LoginRegisterApiManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loginRegisterApi = retrofit.create(LoginRegisterApi.class);
    }

    public static LoginRegisterApiManager getInstance() {
        if (loginRegisterApiManager == null) {
            loginRegisterApiManager = new LoginRegisterApiManager();
        }
        return loginRegisterApiManager;
    }

    public Call<User> login(User user) {
        return loginRegisterApi.login(user);
    }

    public Call<User> register(User user) {
        return loginRegisterApi.register(user);
    }
}