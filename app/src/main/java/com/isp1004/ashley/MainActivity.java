package com.isp1004.ashley;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.isp1004.ashley.myinfo.MyInfoActivity;

public class MainActivity extends AppCompatActivity {

    GlobalApp globalApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        globalApp = (GlobalApp)getApplication();
    }

    public void onButton(View view) {
        Intent intent = new Intent(this, MyInfoActivity.class);
        startActivity(intent);
    }
}
