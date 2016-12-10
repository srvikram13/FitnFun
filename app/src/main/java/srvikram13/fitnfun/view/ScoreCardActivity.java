package srvikram13.fitnfun.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import srvikram13.fitnfun.R;
import srvikram13.fitnfun.model.AppData;

public class ScoreCardActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_card);

        AppData appData = AppData.getInstance(this);
        Button btnHome = (Button) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(this);

        Button btnPlayAgain = (Button) findViewById(R.id.btnPlayAgain);
        btnPlayAgain.setOnClickListener(this);

        TextView score = (TextView) findViewById(R.id.finalScore);
        score.setText(String.valueOf(appData.getLatestScore()));
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()){
            case R.id.btnHome:
                i = new Intent(this, HomeActivity.class);
                startActivity(i);
                break;
            case R.id.btnPlayAgain:
                i = new Intent(this, GameActivity.class);
                startActivity(i);
                break;
        }

    }
}
