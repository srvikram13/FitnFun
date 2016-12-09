package srvikram13.fitnfun.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import srvikram13.fitnfun.R;
import srvikram13.fitnfun.model.AppData;


public class LoginActivity extends AppCompatActivity {
    private AppData appData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        appData = AppData.getInstance(getApplicationContext());
        ImageView fb = (ImageView) findViewById(R.id.facebook);
        ImageView g = (ImageView) findViewById(R.id.google);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i);
            }

        };
        fb.setOnClickListener(clickListener);
        g.setOnClickListener(clickListener);

    }

}

