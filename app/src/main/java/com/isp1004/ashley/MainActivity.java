package com.isp1004.ashley;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.isp1004.ashley.company.ContactUsActivity;
import com.isp1004.ashley.myinfo.MyInfoActivity;
import com.isp1004.ashley.user.LoginActivity;

public class MainActivity extends AppCompatActivity {

    GlobalApp globalApp;

    Button userButton;
    TextView nameMessage;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        globalApp = (GlobalApp)getApplication();
        email = globalApp.getEmail();

        userButton = (Button)findViewById(R.id.btn_user);
        nameMessage = (TextView)findViewById(R.id.txt_name_message);

        if (email != null && email.isEmpty() != true) {
            userButton.setText("LOG OUT");
            nameMessage.setText("Welcome, " + globalApp.getFirstName());
        } else {
            userButton.setText("LOG IN");
            nameMessage.setText("Log In, please.");
        }

    }

    public void onGoMyInfo(View view) {
        if (email != null && email.isEmpty() != true) {
            Intent intent = new Intent(this, MyInfoActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    public void onLogin(View view) {
        Log.d("Seulki", "press login button");
        if (email != null && email.isEmpty() != true) {
            globalApp.setEmail(null);
            globalApp.setFirstName(null);
            globalApp.setLastName(null);
            userButton.setText("LOG IN");
            nameMessage.setText("Log In, please.");
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    public void onBuyFaceThings(View view) {
        goShopping("001"); // categoy - 001 : Faces Things
    }

    public void onBuyCheeksThings(View view) {
        goShopping("002"); // categoy - 002 : Cheeks Things
    }

    public void onBuyEyesThings(View view) {
        goShopping("003"); // categoy - 003 : Eyes Things
    }

    public void onBuyLipsThings(View view) {
        goShopping("004"); // categoy - 004 : Lips Things
    }

    private void goShopping(String category) {
        Intent intent = new Intent(this, com.isp1004.ashley.shopping.ProductListActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }

    public void onGoCompanyInfo(View view) {
        Intent intent = new Intent(this, ContactUsActivity.class);
        startActivity(intent);
    }
}
