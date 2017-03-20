package com.isp1004.ashley.shopping;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

/**
 * Created by Administrator on 2017-03-20.
 */

public class DataParser extends AsyncTask<Void, Void, Integer> {
    Context context;
    String jsonData;
    ListView listView;

    ProgressDialog pd;



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
        return null;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
    }

    private int parseData() {
        try {

        }
    }
}
