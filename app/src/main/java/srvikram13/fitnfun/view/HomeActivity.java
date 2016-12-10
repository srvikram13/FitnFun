package srvikram13.fitnfun.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import srvikram13.fitnfun.R;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Set up click listeners for all the elements
        Button btnProfile  = (Button) findViewById(R.id.btnProfile);
        btnProfile.setOnClickListener(this);

        Button btnScoreboard  = (Button) findViewById(R.id.btnScoreboard);
        btnScoreboard.setOnClickListener(this);

        Button btnNewGame  = (Button) findViewById(R.id.btnNewGame);
        btnNewGame.setOnClickListener(this);

        Button btnHelpTutorial  = (Button) findViewById(R.id.btnHelpTutorial);
        btnHelpTutorial.setOnClickListener(this);

        Button btnAbout = (Button) findViewById(R.id.btnAbout);
        btnAbout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Intent i;
        switch (view.getId()) {
            case R.id.btnProfile:
                i = new Intent(this, ProfileActivity.class);
                startActivity(i);
                break;
            case R.id.btnScoreboard:
                i = new Intent(this, ScoreBoardActivity.class);
                startActivity(i);
                break;
            case R.id.btnNewGame:
                i = new Intent(this, NewGameActivity.class);
                startActivity(i);
                break;
            case R.id.btnAbout:
            case R.id.btnHelpTutorial:
                Toast.makeText(getApplicationContext(), "This is show a popup", Toast.LENGTH_LONG);
                break;
        }
    }
    /*@Override
    public void onConfigurationChanged(Configuration newConfig) {
        setContentView(R.layout.activity_home);
        super.onConfigurationChanged(newConfig);
        *//*if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            createHorizontalalLayout();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            createVerticalLayout();
        }*//*
    }*/
}

