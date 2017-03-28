package com.isp1004.ashley.shopping;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.isp1004.ashley.R;
import com.isp1004.ashley.common.Connector;
import com.isp1004.ashley.common.PicassoClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class ProductDetailActivity extends AppCompatActivity {

    ImageView productImg = (ImageView)findViewById(R.id.pddetail_product_img);


    TextView pdDetailBrandName = (TextView)findViewById(R.id.pddetail_brand_name);
    TextView pdDetailProductName = (TextView)findViewById(R.id.pddetail_product_name);
    TextView pdDetailProductId = (TextView)findViewById(R.id.pddetail_product_id);
    TextView pdDetailQty = (TextView)findViewById(R.id.pddetail_qty);
    EditText pdDetailOrderQty = (EditText) findViewById(R.id.pddetail_edt_order_qty);
    TextView pdDetailPrice = (TextView)findViewById(R.id.pddetail_price);
    TextView pdDetailTotalPrice = (TextView)findViewById(R.id.pddetail_total_price);
    TextView pdDetailDescription = (TextView)findViewById(R.id.pddetail_description);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);

        Intent intent = this.getIntent();
        String strProductId = intent.getStringExtra("product_id");
        /*
        TextView pdetailProductId = (TextView)findViewById(R.id.pdetail_product_id);
        pdetailProductId.setText(strProductId);
        */

        final String urlAddress = this.getResources().getString(R.string.serverUri) + "product_detail.php?product_id=" + strProductId;

        new DetailDataLoader(ProductDetailActivity.this, urlAddress).execute();

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
                    int    qty         = jsonObject.getInt("qty");
                    String isSoldout   = jsonObject.getString("is_soldout");
                    String updateDt    = jsonObject.getString("update_dt");

                    pdDetailBrandName.setText(brandName);
                    pdDetailProductName.setText(productName);
                    pdDetailProductId.setText(productId);

                    if (qty > 0) {
                        pdDetailQty.setText(String.valueOf(qty));
                    } else {
                        pdDetailQty.setText("Sold Out");
                    }
                    pdDetailPrice.setText(String.valueOf(price));
                    pdDetailDescription.setText(description);

                    PicassoClient.viewImage(context, context.getResources().getString(R.string.serverUri) + imgUrl, productImg);

                } catch (JSONException je) {
                    je.printStackTrace();
                }

            }
        }

        private String loadData() {
            HttpURLConnection conn = Connector.connect(urlAddress);
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

