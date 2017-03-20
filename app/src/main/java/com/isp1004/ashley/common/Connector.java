package com.isp1004.ashley.common;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2017-03-20.
 */

public class Connector {
    public static HttpURLConnection connect(String urlAddress) {
        try {
            URL url = new URL(urlAddress);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // CONNECTION PROPERTIES
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(20000);
            conn.setReadTimeout(20000);
            conn.setDoInput(true);

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
