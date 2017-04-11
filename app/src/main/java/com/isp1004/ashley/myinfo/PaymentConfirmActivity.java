package com.isp1004.ashley.myinfo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.isp1004.ashley.GlobalApp;
import com.isp1004.ashley.MainActivity;
import com.isp1004.ashley.R;
import com.isp1004.ashley.common.BasketHelper;
import com.isp1004.ashley.common.BasketVO;
import com.isp1004.ashley.common.Connector;

import java.net.HttpURLConnection;
import java.util.Iterator;

public class PaymentConfirmActivity extends AppCompatActivity {


    private String email;
    private GlobalApp globalApp;

    SQLiteDatabase sqLiteDatabase;

    TextView confirmCardCompany;
    TextView confirmCardNumber;
    TextView confirmCardCsv;
    TextView confirmCardExpiration;
    TextView confirmCardName;
    TextView confirmTotal;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        confirmCardCompany = (TextView) findViewById(R.id.showCardCompany);
        confirmCardNumber = (TextView) findViewById(R.id.showCardNumber);
        confirmCardCsv = (TextView) findViewById(R.id.showCardCsv);
        confirmCardExpiration = (TextView) findViewById(R.id.showCardExpiration);
        confirmCardName = (TextView) findViewById(R.id.showCardName);
        confirmTotal = (TextView) findViewById(R.id.showTotalPrice);

        globalApp = (GlobalApp)getApplication();
        this.email = globalApp.getEmail();

        new PaymentConfirmActivity.confirmOrder(PaymentConfirmActivity.this, confirmCardCompany,confirmCardNumber,confirmCardCsv,confirmCardExpiration,confirmCardName,confirmTotal,this.email).execute();



        /*
        setContentView(R.layout.payment_confirm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }


    class confirmOrder extends AsyncTask<Void,Void,String>{


        Context context;

        String email;


        TextView confirmCardCompany;
        TextView confirmCardNumber;
        TextView confirmCardCsv;
        TextView confirmCardExpiration;
        TextView confirmCardName;
        TextView confirmTotal;



        ProgressDialog pd;

        public confirmOrder(Context context, TextView confirmCardCompany, TextView confirmCardNumber, TextView confirmCardCsv, TextView confirmCardExpiration, TextView confirmCardName, TextView confirmTotal, String email) {

            this.context = context;
            this.confirmCardCompany = confirmCardCompany;
            this.confirmCardNumber = confirmCardNumber;
            this.confirmCardCsv = confirmCardCsv;
            this.confirmCardExpiration = confirmCardExpiration;
            this.confirmCardName = confirmCardName;
            this.confirmTotal = confirmTotal;





        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            super.onPreExecute();

            pd = new ProgressDialog(context);
            pd.setTitle("Retrives");
            pd.setMessage("Retriving... Please wait.");
            pd.show();


        }

        @Override
        protected String doInBackground(Void... params) {




            return loadData();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }


        private String loadData() {

            CardInfoHelper cardInfoHelper = new CardInfoHelper(context);

            SQLiteDatabase sqLiteDatabase = cardInfoHelper.getWritableDatabase();
            sqLiteDatabase = cardInfoHelper.getReadableDatabase();

            Cursor cursor = cardInfoHelper.queryCardInfo(email, sqLiteDatabase);

            if (cursor.moveToFirst()) {

                String email = cursor.getString(0);
                String cardCompany = cursor.getString(1);
                String cardNumber = cursor.getString(2);
                String cardCsv = cursor.getString(3);
                String cardExpiration = cursor.getString(4);
                String cardName = cursor.getString(5);


                confirmCardCompany.setText(cardCompany);
                confirmCardNumber.setText(cardNumber);
                confirmCardCsv.setText(cardCsv);
                confirmCardExpiration.setText(cardExpiration);
                confirmCardName.setText(cardName);
            }

            BasketHelper basketHelper = new BasketHelper(PaymentConfirmActivity.this);
            SQLiteDatabase sqLiteDatabase1 = basketHelper.getWritableDatabase();
            sqLiteDatabase1 = basketHelper.getReadableDatabase();

            Cursor cursor1 = basketHelper.queryBasket(email, "Y", "N", sqLiteDatabase1);

            Iterator<BasketVO> iterator = PaymentConfirmActivity.basketVOs.iterator();



            while (iterator.hasNext()) {
                BasketVO basketVO = iterator.next();
                int basketId = basketVO.getBasketId();
                int qty = basketVO.getQty();
                int price = basketVO.getPrice();

                confirmTotal.setText(qty*price);

            }




            return null;
        }
    }





    public void buttonConfirm(View view) {


        BasketHelper basketHelper = new BasketHelper(PaymentConfirmActivity.this);

        sqLiteDatabase = basketHelper.getReadableDatabase();

        Cursor cursor = basketHelper.queryBasket(email, "Y", "N", sqLiteDatabase);

        SQLiteDatabase sqLiteWritableDatabase = basketHelper.getWritableDatabase();

        int productId;
        int qty;

        Iterator<BasketVO> iterator = PaymentConfirmActivity.BasketLoader.this.basketVOs.iterator();

        while (iterator.hasNext()) {

            if (cursor.moveToFirst()) {

                // basket update
                basketHelper.updateBasket(email, "Y", "Y", sqLiteWritableDatabase);
            }



            String postData = "email=" + this.email + "&product_id=" + productId + "&qty=" + qty;
            HttpURLConnection conn = Connector.connect(urlAddress4basket, postData);
        }


        //go to main
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);



    }
}
