package com.isp1004.ashley.shopping;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.isp1004.ashley.R;
import com.isp1004.ashley.common.Connector;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class ProductListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list);
    }




}

class DataLoader extends AsyncTask<Void,Void,String> {
    Context context;
    String urlAddress;
    ListView listView;

    ProgressDialog pd;

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