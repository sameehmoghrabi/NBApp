package sameeh.com.nbapp.Screens.LoginRegister;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sameeh.com.nbapp.LocalData.LocalStorageManager;
import sameeh.com.nbapp.Models.ApiError;
import sameeh.com.nbapp.Models.User;
import sameeh.com.nbapp.Base.BaseFragment;
import sameeh.com.nbapp.R;
import sameeh.com.nbapp.Screens.SearchTeamActivity;
import sameeh.com.nbapp.api.LoginResgister.LoginRegisterApiManager;

public class LoginFragment extends BaseFragment {
    private LoginFragmentListener listener;
    private LoginRegisterApiManager loginRegisterApiManager;
    private LocalStorageManager localStorageManager;
    private final int REQUEST_CODE = 100;

    @BindView(R.id.email_holder)
    TextInputLayout emailHolder;

    @BindView(R.id.email)
    EditText emailEditText;

    @BindView(R.id.password_holder)
    TextInputLayout passwordHolder;

    @BindView(R.id.password)
    EditText passwordEditText;

    @BindView(R.id.login)
    Button loginButton;

    @BindView(R.id.register)
    TextView registerTextView;

    @BindView(R.id.guest)
    TextView guestTextView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginRegisterApiManager = LoginRegisterApiManager.getInstance();
        localStorageManager = LocalStorageManager.getInstance(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoginFragmentListener) {
            listener = (LoginFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement LoginFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @OnClick(R.id.login)
    public void attemptLogin() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        boolean flag = true;

        if (TextUtils.isEmpty(email)) {
            emailHolder.setError("email is required");
            flag = false;
        } else {
            emailHolder.setErrorEnabled(false);
        }

        if (TextUtils.isEmpty(password)) {
            passwordHolder.setError("password is required");
            flag = false;
        } else {
            passwordHolder.setErrorEnabled(false);
        }

        if (flag) {
            showProgressBar();
            User loginUser = new User(email, password);
            loginRegisterApiManager.login(loginUser).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    hideProgressBar();
                    if (response.isSuccessful()) {
                        User apiUser = response.body();
                        localStorageManager.saveUser(apiUser);
                        listener.onLoginSuccess();
                    } else {
                        try {
                            String errorString = response.errorBody().string();
                            ApiError error = parseApiErrorString(errorString);
                            showToastMessage(error.getMessage());
                            listener.onLoginFailure();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    hideProgressBar();
                    listener.onLoginFailure();
                    showToastMessage(t.getMessage());
                }
            });
        }
    }

    @OnClick(R.id.register)
    public void requestRegister() {
        listener.onRequestRegister();
    }

    @OnClick(R.id.guest)
    public void guest(){
        Intent intent = new Intent(getActivity(), SearchTeamActivity.class);
        startActivity(intent);
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    public interface LoginFragmentListener {

        void onLoginSuccess();

        void onLoginFailure();

        void onRequestRegister();
    }
}