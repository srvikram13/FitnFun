package srvikram13.fitnfun.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import srvikram13.fitnfun.R;

public class NewGameActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        // btnSinglePlayer, btnMultiPlayer, btnDismiss,
        // btnAddMore, btnCancel,
        // btnBack, , btnDismissAgain
        Button btnDismiss = (Button) findViewById(R.id.btnDismiss);
        btnDismiss.setOnClickListener(this);

        Button btnDismissAgain = (Button) findViewById(R.id.btnDismissAgain);
        btnDismissAgain.setOnClickListener(this);

        Button btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        Button btnSinglePlayer = (Button) findViewById(R.id.btnSinglePlayer);
        btnSinglePlayer.setOnClickListener(this);

        Button btnMultiPlayer = (Button) findViewById(R.id.btnMultiPlayer);
        btnMultiPlayer.setOnClickListener(this);

        Button btnStartGame = (Button) findViewById(R.id.btnStartGame);
        btnStartGame.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.btnDismiss:
            case R.id.btnDismissAgain:
                i = new Intent(this, HomeActivity.class);
                startActivity(i);
                break;
            case R.id.btnBack:
                // TODO: go back to new game screen in this activity
                break;
            case R.id.btnMultiPlayer:
                // TODO: Hide existing buttons and show multiplayer menu
                break;
            case R.id.btnStartGame:
            case R.id.btnSinglePlayer:
                i = new Intent(this, GameActivity.class);
                startActivity(i);
                break;
            case R.id.btnAddMore:
                // TODO: add a new edittext element
                break;
        }

    }
}
