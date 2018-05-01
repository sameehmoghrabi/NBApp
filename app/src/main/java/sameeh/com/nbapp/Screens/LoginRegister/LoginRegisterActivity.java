package sameeh.com.nbapp.Screens.LoginRegister;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import sameeh.com.nbapp.R;
import sameeh.com.nbapp.Screens.Main.LoggedInActivity;

/**
 * Created by samee on 4/1/2018.
 */

public class LoginRegisterActivity extends AppCompatActivity
        implements LoginFragment.LoginFragmentListener, RegisterFragment.RegisterFragmentListener {


    @BindView(R.id.container)
    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        ButterKnife.bind(this);
        addLoginFragment();
    }

    private void addLoginFragment() {
        LoginFragment fragment = LoginFragment.newInstance();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void onLoginSuccess() {
        Intent intent = new Intent(this, LoggedInActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginFailure() {

    }

    @Override
    public void onRequestRegister() {
        RegisterFragment fragment = RegisterFragment.newInstance();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment, RegisterFragment.TAG)
                .addToBackStack(RegisterFragment.TAG)
                .commit();
    }

    @Override
    public void onRegisterSuccess() {
        getFragmentManager().popBackStack();
    }

    @Override
    public void onRegisterFailure() {

    }
}
