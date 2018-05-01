package sameeh.com.nbapp.Screens;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import sameeh.com.nbapp.LocalData.LocalStorageManager;
import sameeh.com.nbapp.Models.User;
import sameeh.com.nbapp.R;
import sameeh.com.nbapp.Screens.LoginRegister.LoginRegisterActivity;
import sameeh.com.nbapp.Screens.Main.LoggedInActivity;

public class LaunchActivity extends AppCompatActivity {

    LocalStorageManager localStorageManager;

    private ValueAnimator valueAnimator;
    private ImageView logoImageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        logoImageView = findViewById(R.id.nbaLogo);

        valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(3000);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                logoImageView.setAlpha(value);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                final MediaPlayer mp = MediaPlayer.create(LaunchActivity.this,R.raw.sound);
                mp.start();


                gotoNextScreen();
            }
        });
        valueAnimator.start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        valueAnimator.removeAllUpdateListeners();
        valueAnimator.removeAllListeners();
    }

    private void gotoNextScreen() {
        Intent intent;

        localStorageManager = LocalStorageManager.getInstance(getApplicationContext());
        User user = localStorageManager.getUser();

        if (user == null) {
            intent = new Intent(this, LoginRegisterActivity.class);
        } else {
            intent = new Intent(this, LoggedInActivity.class);
        }
        startActivity(intent);
        finish();
    }
}

