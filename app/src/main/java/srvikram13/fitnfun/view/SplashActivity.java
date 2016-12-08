package srvikram13.fitnfun.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;

import srvikram13.fitnfun.R;
public class SplashActivity extends AppCompatActivity {
    private static final int REDIRECT_DELAY = 3000;

    private final Handler mHideHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHideHandler.postDelayed(mUpdateTimeTask, REDIRECT_DELAY);
    }
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
        }
    };

}
