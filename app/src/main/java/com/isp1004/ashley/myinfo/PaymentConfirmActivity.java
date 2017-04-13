package com.isp1004.ashley.myinfo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.isp1004.ashley.GlobalApp;
import com.isp1004.ashley.R;
import com.isp1004.ashley.common.BasketHelper;
import com.isp1004.ashley.common.Connector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class PaymentConfirmActivity extends AppCompatActivity {

    private String email;
    private GlobalApp globalApp;
    private String urlAddress;

    SQLiteDatabase sqLiteDatabase;

    TextView confirmCardCompany;
    TextView confirmCardNumber;
    TextView confirmCardCsv;
    TextView confirmCardExpiration;
    TextView confirmCardName;
    TextView confirmTotal;

    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_confirm);

        urlAddress = this.getResources().getString(R.string.serverUri) + "add_order.php";

        confirmCardCompany = (TextView) findViewById(R.id.showCardCompany);
        confirmCardNumber = (TextView) findViewById(R.id.showCardNumber);
        confirmCardCsv = (TextView) findViewById(R.id.showCardCsv);
        confirmCardExpiration = (TextView) findViewById(R.id.showCardExpiration);
        confirmCardName = (TextView) findViewById(R.id.showCardName);
        confirmTotal = (TextView) findViewById(R.id.showTotalPrice);

        btnConfirm = (Button) findViewById(R.id.btnConfirm);

        globalApp = (GlobalApp)getApplication();
        this.email = globalApp.getEmail();

        Intent intent = getIntent();
        confirmCardCompany.setText(intent.getStringExtra("cardCompany"));
        confirmCardNumber.setText(intent.getStringExtra("cardNumber"));
        confirmCardCsv.setText(intent.getStringExtra("cardCsv"));
        confirmCardExpiration.setText(intent.getStringExtra("cardExpiration"));
        confirmCardName.setText(intent.getStringExtra("cardName"));

        int totalAmount = 0;

        BasketHelper basketHelper = new BasketHelper(PaymentConfirmActivity.this);
        SQLiteDatabase sqLiteDatabase1 = basketHelper.getReadableDatabase();
        Cursor cursor = basketHelper.queryBasket(email, "Y", "N", sqLiteDatabase1);
        if (cursor.moveToFirst()) {
            do {
                int qty = cursor.getInt(4);
                int price = cursor.getInt(5) * qty;
                totalAmount = totalAmount + (qty * price);
            } while(cursor.moveToNext());
        }
        confirmTotal.setText(String.valueOf(totalAmount));
    }

    public void buttonConfirm(View view) {

        BasketHelper basketHelper = new BasketHelper(PaymentConfirmActivity.this);
        sqLiteDatabase = basketHelper.getReadableDatabase();
        Cursor cursor = basketHelper.queryBasket(email, "Y", "N", sqLiteDatabase);
        SQLiteDatabase sqLiteWritableDatabase = basketHelper.getWritableDatabase();

        if (cursor.moveToFirst()) {
            do {


                int basketId = cursor.getInt(0);
                String productId = cursor.getString(2);
                int qty = cursor.getInt(4);

                Log.d("Seulki", "basketId : "  + String.valueOf(basketId));
                /*
                String postData = "email=" + this.email + "&product_id=" + productId + "&qty=" + String.valueOf(qty);
                HttpURLConnection conn = Connector.connect(urlAddress, postData);
                */
                AsyncTask<Void,Void,String> task = new ConfirmLoader(PaymentConfirmActivity.this, email, productId, qty, urlAddress);

                basketHelper.updateBasket(String.valueOf(basketId), "Y", "Y", sqLiteWritableDatabase);
            } while(cursor.moveToNext());
        }

        //go to OderList in MyInfo
        Intent intent = new Intent(PaymentConfirmActivity.this, MyInfoActivity.class);
        intent.putExtra("tab_seq", 1);
        startActivity(intent);
    }

    class ConfirmLoader extends AsyncTask<Void,Void,String> {
        Context context;
        String email;
        String productId;
        int qty;
        String urlAddress;

        public ConfirmLoader(Context context, String email, String productId, int qty, String urlAddress) {
            this.context = context;
            this.email = email;
            this.productId = productId;
            this.qty = qty;
            this.urlAddress = urlAddress;

            Log.d("Seulki", "urlAddress : " + urlAddress);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("Seulki", "ConfirmLoader > onPreExecute");
        }

        @Override
        protected String doInBackground(Void... params) {
            Log.d("Seulki", "ConfirmLoader > doInBackground");

            String postData = "email=" + this.email + "&product_id=" + productId + "&qty=" + String.valueOf(qty);
            Log.d("Seulki", "postData : " + postData);
            HttpURLConnection conn = Connector.connect(urlAddress, postData);

            return null;
        }

        @Override
        protected void onPostExecute(String jsonData) {
            super.onPostExecute(jsonData);
            Log.d("Seulki", "ConfirmLoader > onPostExecute");
        }

    }
}
