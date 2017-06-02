package com.ionblaster.week2inclass;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        if(intent !=null){
            String msg = intent.getStringExtra("MSG");
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }
    }

    public void popText(View v){
        Intent intent = new Intent();
        intent.putExtra("RESPONSE","Hi");
        setResult(RESULT_OK,intent);
        finish();
    }
}
