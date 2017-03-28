package com.isp1004.ashley.shopping;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-03-20.
 */

public class DataParser extends AsyncTask<Void, Void, Integer> {
    Context context;
    String jsonData;
    ListView listView;

    ProgressDialog pd;

    List<ProductVO> productListVOs = new ArrayList<ProductVO>();



    public DataParser(Context context, String jsonData, ListView listView) {
        this.context = context;
        this.jsonData = jsonData;
        this.listView = listView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd = new ProgressDialog(context);
        pd.setTitle("Parse");
        pd.setMessage("Parsing... Please wait.");
        pd.show();

    }

    @Override
    protected Integer doInBackground(Void... params) {
        return this.parseData();
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);

        pd.dismiss();

        if (result == 0) {
            Toast.makeText(context, "Unable To Parse", Toast.LENGTH_SHORT).show();
        } else {
            //BIND DATA TO LISTVIEW
            CustomAdaptor customAdaptor = new CustomAdaptor(context, productListVOs);
            listView.setAdapter(customAdaptor);
        }
    }

    private int parseData() {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            JSONObject jsonObject;

            productListVOs.clear();
            ProductVO productVO;

            for (int i=0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);

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

                productVO = new ProductVO();

                productVO.setProductId(productId);
                productVO.setBrandName(brandName);
                productVO.setProductName(productName);
                productVO.setCategory(category);
                productVO.setDescription(description);
                productVO.setImgUrl(imgUrl);
                productVO.setPrice(price);
                productVO.setQty(qty);
                productVO.setIsSoldout(isSoldout);
                productVO.setUpdateDt(updateDt);

                productListVOs.add(productVO);

            }

            return 1;

        } catch (JSONException je) {
            je.printStackTrace();
        }

        return 0;
    }
}
