package com.isp1004.ashley.myinfo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.isp1004.ashley.R;
import com.isp1004.ashley.common.BasketHelper;
import com.isp1004.ashley.common.BasketVO;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by SEULKI on 2017-03-16.
 */

public class MyInfoTab2MyBasket extends Fragment {

    private String email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.my_info_tab2_my_basket, container, false);

        final ListView listView = (ListView) rootView.findViewById(R.id.lv_basket_list);
        final TextView totalView = (TextView) rootView.findViewById(R.id.basket_total);
        final Button orderButton = (Button) rootView.findViewById(R.id.basket_btn_order);


        MyInfoActivity myInfoActivity = (MyInfoActivity)getActivity();
        this.email = myInfoActivity.globalApp.getEmail();
        //this.email = "abc@gmail.com";

        new BasketLoader(rootView.getContext(), listView, totalView, orderButton, this.email).execute();


        return rootView;
    }

    class BasketLoader extends AsyncTask<Void,Void,String> {

        Context context;
        ListView listView;
        TextView totalView;
        Button orderButton;
        BasketAdaptor basketAdaptor;
        String email;
        List<BasketVO> basketVOs = new ArrayList<BasketVO>();

        ProgressDialog pd;

        SQLiteDatabase sqLiteDatabase;

        public BasketLoader(final Context context, ListView listView, TextView totalView, Button orderButton, String email) {
            this.context = context;
            this.listView = listView;
            this.totalView = totalView;
            this.orderButton = orderButton;
            this.email = email;

            BasketAdaptor basketAdaptor = new BasketAdaptor(context, basketVOs);
            listView.setAdapter(basketAdaptor);

            orderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Iterator<BasketVO> iterator = BasketLoader.this.basketVOs.iterator();

                    BasketHelper basketHelper = new BasketHelper(BasketLoader.this.context);
                    BasketLoader.this.sqLiteDatabase = basketHelper.getWritableDatabase();



                    while (iterator.hasNext()) {
                        BasketVO basketVO = iterator.next();
                        int basketId = basketVO.getBasketId();

                        basketHelper.updateBasket(String.valueOf(basketId), "Y", "N", sqLiteDatabase);

                    }

                    Intent intent = new Intent(context, PaymentActivity.class);
                    startActivity(intent);



                }
            });
        }

        @Override
        protected void onPreExecute() {
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
            pd.dismiss();
            DecimalFormat priceDecimalFormat = new DecimalFormat("C$###,###,###.00");

            totalView.setText(priceDecimalFormat.format(Integer.parseInt(s)));
        }

        private String loadData() {
            int total = 0;
            BasketHelper basketHelper = new BasketHelper(context);
            sqLiteDatabase = basketHelper.getReadableDatabase();

            Cursor cursor = basketHelper.queryBasket(this.email, "N", sqLiteDatabase);

            if (cursor.moveToFirst()) {
                do {
                    int basketId = cursor.getInt(0);
                    String brandName = cursor.getString(1);
                    String productId = cursor.getString(2);
                    String productName = cursor.getString(3);
                    int qty = cursor.getInt(4);
                    int price = cursor.getInt(5) * qty;
                    String isOrdered = cursor.getString(6);
                    String isPaid = cursor.getString(7);
                    BasketVO basketVO = new BasketVO(basketId, this.email, brandName, productId, productName, qty, price, isOrdered, isPaid);
                    basketVOs.add(basketVO);

                    total = total + price;

                } while(cursor.moveToNext());
            }

            return String.valueOf(total);
        }
    }



}
