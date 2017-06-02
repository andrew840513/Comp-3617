package com.ionblaster.week2inclass;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static String LOGTAG = "LifecycleDemo";
    private int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOGTAG,"In onCreate() method...");

//        Button button = (Button) findViewById(R.id.button);

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, R.string.app_name,Toast.LENGTH_LONG).show();
//            }
//        });
    }

    public void  onClick(View v){
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("MSG","How are you doing?");
        startActivity(intent);
        //startActivityForResult(intent,REQUEST_CODE);
    }

    //when 2nd view send data back
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            String msg = data.getStringExtra("RESPONSE");
            Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOGTAG,"In onStart() method...");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOGTAG,"In onResume() method...");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOGTAG,"In onStop() method...");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOGTAG,"In onPause() method...");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOGTAG,"In onRestart() method...");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOGTAG,"In onDestroy() method...");
    }
}
