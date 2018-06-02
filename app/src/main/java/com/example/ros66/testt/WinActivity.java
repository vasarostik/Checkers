package com.example.ros66.testt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WinActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
        TextView textView = findViewById(R.id.rooos);
        textView.setText(MainActivity.status);

    }

    public void Exit(View v) {
        this.finish();

        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        System.exit(0);
    }


}
