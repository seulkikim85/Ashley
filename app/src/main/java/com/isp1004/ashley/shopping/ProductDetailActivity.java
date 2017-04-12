package com.isp1004.ashley.shopping;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.isp1004.ashley.R;
import com.isp1004.ashley.common.BasketHelper;
import com.isp1004.ashley.common.Connector;
import com.isp1004.ashley.common.PicassoClient;
import com.isp1004.ashley.myinfo.MyInfoActivity;
import com.isp1004.ashley.myinfo.MyInfoTab2MyBasket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.text.DecimalFormat;

public class ProductDetailActivity extends AppCompatActivity {

    ImageView productImg;

    TextView pdDetailBrandName;
    TextView pdDetailProductName;
    TextView pdDetailProductId;
    TextView pdDetailQty;
    EditText pdDetailOrderQty;
    TextView pdDetailPrice;
    TextView pdDetailTotalPrice;
    TextView pdDetailDescription;

    int iOrderQty;
    int iPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);

        this.iOrderQty = 1;
        this.iPrice = 0;

        productImg = (ImageView)findViewById(R.id.pddetail_product_img);
        pdDetailBrandName = (TextView)findViewById(R.id.pddetail_brand_name);
        pdDetailProductName = (TextView)findViewById(R.id.pddetail_product_name);
        pdDetailProductId = (TextView)findViewById(R.id.pddetail_product_id);
        pdDetailQty = (TextView)findViewById(R.id.pddetail_qty);
        pdDetailOrderQty = (EditText) findViewById(R.id.pddetail_edt_order_qty);
        pdDetailPrice = (TextView)findViewById(R.id.pddetail_price);
        pdDetailTotalPrice = (TextView)findViewById(R.id.pddetail_total_price);
        pdDetailDescription = (TextView)findViewById(R.id.pddetail_description);

        Intent intent = this.getIntent();
        String strProductId = intent.getStringExtra("product_id");
        /*
        TextView pdetailProductId = (TextView)findViewById(R.id.pdetail_product_id);
        pdetailProductId.setText(strProductId);
        */

        pdDetailOrderQty.setText(String.valueOf(iOrderQty));

        pdDetailOrderQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("Seulki", "beforeTextChanged");

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("Seulki", "onTextChanged");
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("Seulki", "afterTextChanged");
                DecimalFormat priceDecimalFormat = new DecimalFormat("C$###,###,###.00");
                int iQty = Integer.parseInt(pdDetailQty.getText().toString());
                String strOrderQty = pdDetailOrderQty.getText().toString().replaceAll(",", "");
                if (strOrderQty == null || (strOrderQty !=null && strOrderQty.isEmpty())) {
                    strOrderQty = "0";
                }
                iOrderQty = Integer.parseInt(strOrderQty);
                if (iOrderQty > iQty) {
                    iOrderQty = iQty;
                    pdDetailOrderQty.setText(String.valueOf(iOrderQty));

                }
                pdDetailTotalPrice.setText(priceDecimalFormat.format(iOrderQty * iPrice));
                Log.d("Seulki", "Total Price : " + priceDecimalFormat.format(iOrderQty * iPrice));
            }
        });

        final String urlAddress = this.getResources().getString(R.string.serverUri) + "product_detail.php?product_id=" + strProductId;

        new DetailDataLoader(ProductDetailActivity.this, urlAddress).execute();

    }

    public void onAddBasket(View view) {

        String email4basket = "abc@gmail.com";
        String brand_name4basket = pdDetailBrandName.getText().toString();
        String product_id4basket = pdDetailProductId.getText().toString();
        String product_name4basket = pdDetailProductName.getText().toString();
        String qty4basket = String.valueOf(iOrderQty);
        Log.d("Seulki", "origin price : "  + pdDetailPrice.getText().toString());
        String price4basket = pdDetailPrice.getText().toString().replaceAll(",", "").replaceAll("\\$", "").replaceAll("C", "");
        Log.d("Seulki", "send qty : "  + qty4basket);
        Log.d("Seulki", "send price : " + price4basket);
        String urlAddress4basket = this.getResources().getString(R.string.serverUri) + "add_basket.php";


        new addBasketTask(ProductDetailActivity.this, urlAddress4basket, email4basket, brand_name4basket, product_id4basket, product_name4basket, qty4basket, price4basket).execute();




    }

    class addBasketTask extends AsyncTask<Void,Void,String> {
        Context context;
        String urlAddress;
        String email;
        String brandName;
        String productId;
        String productName;
        String qty;
        String price;

        ProgressDialog pd;

        SQLiteDatabase sqLiteDatabase;

        public addBasketTask(Context context, String urlAddress, String email, String brandName, String productId, String productName, String qty, String price) {
            this.context = context;
            this.urlAddress = urlAddress;
            this.email = email;
            this.brandName = brandName;
            this.productId = productId;
            this.productName = productName;
            this.qty = qty;
            this.price = price;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(context);
            pd.setTitle("Saves");
            pd.setMessage("Saving... Please wait.");
            pd.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            BasketHelper basketHelper = new BasketHelper(getBaseContext());
            sqLiteDatabase = basketHelper.getWritableDatabase();

            basketHelper.addBasket(this.email, this.brandName, this.productId, this.productName, this.qty, this.price, sqLiteDatabase);
            /*
            String urlAddress4basket = context.getResources().getString(R.string.serverUri) + "add_basket.php";
            String postData = "email=" + this.email + "&product_id=" + this.productId + "&qty=" + this.qty;
            Log.d("Seulki", postData);
            StringBuffer result = new StringBuffer();

            try {
                HttpURLConnection conn = Connector.connect(urlAddress4basket, postData);

                Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));


                for (int c; (c = in.read()) >= 0; ) {
                    result.append((char) c);
                }
                Log.d("Seulki", "result : " + result.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }
            */
            // return result.toString();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            pd.dismiss();

            /*

            if (jsonData == null) {
                Toast.makeText(context, "Unsuccessful, No Data Saved", Toast.LENGTH_SHORT).show();
            } else {
                DecimalFormat priceDecimalFormat = new DecimalFormat("C$###,###,###.00");
                DecimalFormat qtyDecimalFormat = new DecimalFormat("###,###,###");
                // Parse

                try {
                    JSONArray jsonArray = new JSONArray(jsonData);
                    JSONObject jsonObject;

                    jsonObject = jsonArray.getJSONObject(0);

                    String result   = jsonObject.getString("result");

                    if (result != null && result.isEmpty() != true) {
                        if (result.intern() == "success") {
                            Toast.makeText(context, "Add to basket successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Failed to add data", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (JSONException je) {
                    je.printStackTrace();
                }

            }
            */

            Intent intent = new Intent(context, MyInfoActivity.class);
            intent.putExtra("tab_seq", 0);
            startActivity(intent);
        }


    }

    class DetailDataLoader extends AsyncTask<Void,Void,String> {
        Context context;
        String urlAddress;

        ProgressDialog pd;

        public DetailDataLoader(Context context, String urlAddress) {
            this.context = context;
            this.urlAddress = urlAddress;
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
        protected void onPostExecute(String jsonData) {
            super.onPostExecute(jsonData);
            pd.dismiss();

            if (jsonData == null) {
                Toast.makeText(context, "Unsuccessful, No Data Retrieved", Toast.LENGTH_SHORT).show();
            } else {
                DecimalFormat priceDecimalFormat = new DecimalFormat("C$###,###,###.00");
                DecimalFormat qtyDecimalFormat = new DecimalFormat("###,###,###");
                // Parse

                try {
                    JSONArray jsonArray = new JSONArray(jsonData);
                    JSONObject jsonObject;

                    ProductVO productVO;

                    jsonObject = jsonArray.getJSONObject(0);

                    String productId   = jsonObject.getString("product_id");
                    String brandName   = jsonObject.getString("brand_name");
                    String productName = jsonObject.getString("product_name");
                    String category    = jsonObject.getString("category");
                    String description = jsonObject.getString("description");
                    String imgUrl      = jsonObject.getString("img_url");
                    int    price       = jsonObject.getInt("price");
                    iPrice = price; // To refer this value in detail qty onChange
                    int    qty         = jsonObject.getInt("qty");
                    String updateDt    = jsonObject.getString("update_dt");

                    pdDetailBrandName.setText(brandName);
                    pdDetailProductName.setText(productName);
                    pdDetailProductId.setText(productId);

                    if (qty > 0) {
                        pdDetailQty.setText(qtyDecimalFormat.format(qty));
                    } else {
                        pdDetailQty.setText("Sold Out");
                    }
                    pdDetailPrice.setText(priceDecimalFormat.format(price));
                    pdDetailTotalPrice.setText(priceDecimalFormat.format(iOrderQty * price));
                    pdDetailDescription.setText(description);

                    Log.d("Seulki", "Image URL : " + context.getResources().getString(R.string.serverUri) + imgUrl);
                    PicassoClient.viewImage(context, context.getResources().getString(R.string.serverUri) + imgUrl, productImg);

                } catch (JSONException je) {
                    je.printStackTrace();
                }

            }
        }

        private String loadData() {
            HttpURLConnection conn = Connector.connect(urlAddress, null);
            if (conn == null) {
                return null;
            }

            try {
                InputStream inputStream = new BufferedInputStream(conn.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line = new String();
                StringBuffer jsonData = new StringBuffer();

                while ((line = bufferedReader.readLine()) != null) {
                    jsonData.append(line + "\n");
                }

                bufferedReader.close();
                inputStream.close();

                return jsonData.toString();

            } catch (IOException ie) {
                ie.printStackTrace();
            }
            return null;
        }
    }

}

