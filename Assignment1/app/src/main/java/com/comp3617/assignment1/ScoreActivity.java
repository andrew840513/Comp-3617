package com.comp3617.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {
    private TextView score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        score =(TextView) findViewById(R.id.score);
        Intent intent = getIntent();
        int scoreNum =0;

        if(intent !=null){
            scoreNum = intent.getIntExtra("score",0);
            score.setText(Integer.toString(scoreNum));
        }
    }

    public void onStartOver(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onShareBtn(View view){
        String message = "Hey look I got "+score.getText() +" out of 5!";
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}