package com.example.ros66.testt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

    }

    public void OnClick(View v) {
        Intent intent = new Intent(this, ChoiseActivity.class);
        startActivity(intent);
        this.finish();
    }
    public void Exit(View view)
    {
        finish();
        System.exit(0);
    }

}



