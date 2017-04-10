package com.isp1004.ashley.company;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.isp1004.ashley.R;

public class ContactUsActivity extends AppCompatActivity {

    String number;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us);
        number = "tel:647-600-6666";
        email = "service@ashley.ca";
    }

    public void onTel(View view) {
        try {
            Uri callUri = Uri.parse(this.number);
            Intent callIntent = new Intent(Intent.ACTION_CALL, callUri);
            startActivity(callIntent);
        } catch(SecurityException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
