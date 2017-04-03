package com.isp1004.ashley.common;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2017-03-20.
 */

public class Connector {
    public static HttpURLConnection connect(String urlAddress, String postData) {
        try {
            URL url = new URL(urlAddress);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // CONNECTION PROPERTIES
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(20000);
            conn.setReadTimeout(20000);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            if (postData != null && postData.isEmpty() == false) {
                Log.d("Seulki", "Connect post Data : " + postData);
                byte[] postDataBytes = postData.toString().getBytes("UTF-8");
                conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                conn.getOutputStream().write(postDataBytes);
            }
            /*
            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            StringBuffer result = new StringBuffer();
            for (int c; (c = in.read()) >= 0;) {
                result.append((char)c);
            }
            Log.d("Seulki", result.toString());
            */

            return conn;
        } catch (MalformedURLException me) {
            me.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
