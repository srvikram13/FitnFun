package srvikram13.fitnfun.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import srvikram13.fitnfun.R;
import srvikram13.fitnfun.model.AppData;
import srvikram13.fitnfun.utils.DownloadImageTask;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private AppData appData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        appData = AppData.getInstance(this);

        ImageView userImage = (ImageView) findViewById(R.id.profilePicture);
        new DownloadImageTask(userImage).execute(appData.getUserImageUrl());

        TextView username = (TextView) findViewById(R.id.username);
        username.setText(appData.getUsername());

        Button btnChangeProfile = (Button) findViewById(R.id.btnChangeProfile);
        btnChangeProfile.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.btnChangeProfile:
                i = new Intent(this, LoginActivity.class);
                startActivity(i);
                break;
        }
    }

}
