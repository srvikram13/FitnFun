package srvikram13.fitnfun.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import srvikram13.fitnfun.R;

public class ScoreCardActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_card);

        Button btnHome = (Button) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(this);

        Button btnPlayAgain = (Button) findViewById(R.id.btnPlayAgain);
        btnPlayAgain.setOnClickListener(this);
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
