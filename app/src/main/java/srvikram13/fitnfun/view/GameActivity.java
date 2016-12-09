package srvikram13.fitnfun.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import srvikram13.fitnfun.controller.MotionSensor;
import srvikram13.fitnfun.R;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Button btnEndGame = (Button) findViewById(R.id.btnEndGame);
        btnEndGame.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch(view.getId()) {
            case R.id.btnEndGame:
                i = new Intent(this, ScoreCardActivity.class);
                startActivity(i);
                break;
        }
    }
}
