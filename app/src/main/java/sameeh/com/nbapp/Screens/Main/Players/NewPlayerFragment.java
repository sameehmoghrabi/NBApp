package sameeh.com.nbapp.Screens.Main.Players;


import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sameeh.com.nbapp.Models.ApiError;
import sameeh.com.nbapp.Models.Player;
import sameeh.com.nbapp.Base.LoggedInScreen;
import sameeh.com.nbapp.Base.BaseFragment;
import sameeh.com.nbapp.R;
import sameeh.com.nbapp.api.LoggedIn.LoggedInApiManager;

public class NewPlayerFragment extends BaseFragment implements LoggedInScreen {

    public static final String TAG = NewPlayerFragment.class.getSimpleName();

    private EditText fnameEditText;
    private EditText lnameEditText;
    private EditText ageEditText;
    private EditText idEditText;
    private Button submitButton;

    private LoggedInApiManager loggedInApiManager;

    private String fname, lname, age, playerID;

    private PlayerFragmentListener listener;


    public NewPlayerFragment() {
        // Required empty public constructor
    }

    public static NewPlayerFragment newInstance() {
        NewPlayerFragment fragment = new NewPlayerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loggedInApiManager = LoggedInApiManager.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_player, container, false);

        fnameEditText = view.findViewById(R.id.fname);
        lnameEditText = view.findViewById(R.id.lname);
        ageEditText = view.findViewById(R.id.age);
        idEditText = view.findViewById(R.id.playerID);

        submitButton = view.findViewById(R.id.submitPlayer);
        submitButton.setOnClickListener(v -> {
            fname = fnameEditText.getText().toString();
            lname = lnameEditText.getText().toString();
            age = ageEditText.getText().toString();
            playerID = idEditText.getText().toString();

            if (!TextUtils.isEmpty(lname) && !TextUtils.isEmpty(fname) && !TextUtils.isEmpty(age) && !TextUtils.isEmpty(playerID) ) {
                Player player = new Player(fname,lname,age,playerID);
                loggedInApiManager.createPlayer(player).enqueue(new Callback<List<Player>>() {

                    @Override
                    public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {
                        if (response.isSuccessful()) {
                            listener.onNewPlayerCreatedSuccessfully();
                        } else {
                            try {
                                String errorJson = response.errorBody().string();
                                ApiError apiError = parseApiErrorString(errorJson);
                                actBasedOnApiErrorCode(apiError);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Player>> call, Throwable t) {

                    }
                });
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PlayerFragmentListener) {
            listener = (PlayerFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement LocationFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void notLoggedInAnymore() {
        if (listener != null) {
            listener.onNewPlayerCreationFailure();
        }
    }

    public interface PlayerFragmentListener {
        void onNewPlayerCreatedSuccessfully();

        void onNewPlayerCreationFailure();
    }
}