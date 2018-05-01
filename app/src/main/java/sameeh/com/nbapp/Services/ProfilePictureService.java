package sameeh.com.nbapp.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sameeh.com.nbapp.LocalData.LocalStorageManager;
import sameeh.com.nbapp.Models.User;
import sameeh.com.nbapp.api.LoggedIn.LoggedInApiManager;

public class ProfilePictureService extends Service {

    public final static String IMAGE_EXTRA_KEY = "image";
    private LoggedInApiManager loggedInApiManager;

    private LocalStorageManager localStorageManager;

    public ProfilePictureService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        loggedInApiManager = LoggedInApiManager.getInstance(this);
        localStorageManager = LocalStorageManager.getInstance(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        File imageFile = (File) intent.getSerializableExtra(IMAGE_EXTRA_KEY);
        loggedInApiManager.uploadProfilePicture(imageFile).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User updatedUser = response.body();
                    localStorageManager.saveUser(updatedUser);
                }
                stopSelf();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                stopSelf();
            }
        });
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
