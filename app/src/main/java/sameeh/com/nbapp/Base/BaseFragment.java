package sameeh.com.nbapp.Base;

import android.app.Fragment;
import android.widget.Toast;

import com.google.gson.Gson;

import sameeh.com.nbapp.Models.ApiError;

/**
 * Created by samee on 4/1/2018.
 */

public class BaseFragment extends Fragment{
    private Gson gson = new Gson();

    public void showToastMessage(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public ApiError parseApiErrorString(String error) {
        return gson.fromJson(error, ApiError.class);
    }

    public void actBasedOnApiErrorCode(ApiError apiError) {
        if (apiError.getStatusCode() == 401 && this instanceof LoggedInScreen) {
            LoggedInScreen screen = (LoggedInScreen) this;
            screen.notLoggedInAnymore();
        }
    }
}
