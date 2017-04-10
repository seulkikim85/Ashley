package com.isp1004.ashley.myinfo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.isp1004.ashley.R;
import com.isp1004.ashley.common.Connector;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SEULKI on 2017-03-16.
 */

public class MyInfoTab3Orders extends Fragment {
    String email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.my_info_tab3_orders, container, false);

        final String urlAddress = this.getResources().getString(R.string.serverUri) + "order_list.php";

        MyInfoActivity myInfoActivity = (MyInfoActivity)getActivity();
        this.email = myInfoActivity.globalApp.getEmail();

        final ListView orderlist = (ListView)rootView.findViewById(R.id.lv_order_list);

        new OrderLoader(rootView.getContext(), orderlist, this.email, urlAddress).execute();

        return rootView;
    }

    class OrderLoader extends AsyncTask<Void,Void,String> {
        private Context context;
        private ListView orderlist;
        private String email;
        private String urlAddress;

        private List<OrderVO> orderVOs = new ArrayList<OrderVO>();

        ProgressDialog pd;

        public OrderLoader(Context context, ListView orderlist, String email, String urlAddress) {
            this.context = context;
            this.orderlist = orderlist;
            this.email = email;
            this.urlAddress = urlAddress;

            OrderAdaptor orderAdaptor = new OrderAdaptor(context, this.orderVOs);
            this.orderlist.setAdapter(orderAdaptor);
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
            String postData = "email=" + this.email;
            Log.d("Seulki", postData);
            StringBuffer result = new StringBuffer();

            try {
                HttpURLConnection conn = Connector.connect(this.urlAddress, postData);
                Log.d("Seulki", "after conn");

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

        @Override
        protected void onPostExecute(String jsonData) {
            super.onPostExecute(jsonData);

            pd.dismiss();
            Log.d("Seulki", jsonData);

            if (jsonData == null) {
                Toast.makeText(context, "Unsuccessful, No Data Retrieved", Toast.LENGTH_SHORT).show();
            } else {
                // Parse
                try {
                    JSONArray jsonArray = new JSONArray(jsonData);
                    JSONObject jsonObject;

                    this.orderVOs.clear();
                    OrderVO orderVO;

                    for (int i=0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);

                        String brandName   = jsonObject.getString("brand_name");
                        String productName = jsonObject.getString("product_name");
                        int    price       = jsonObject.getInt("price");
                        int    qty         = jsonObject.getInt("qty");
                        String orderDt    = jsonObject.getString("order_date");

                        orderVO = new OrderVO();

                        orderVO.setBrandName(brandName);
                        orderVO.setProductName(productName);
                        orderVO.setPrice(price);
                        orderVO.setQty(qty);
                        orderVO.setOrderDate(orderDt);

                        orderVOs.add(orderVO);

                    }
                } catch (JSONException je) {
                    je.printStackTrace();
                }
                Log.d("Seulki", "onPostExecute exit");
            }

        }

    }

}
