package com.isp1004.ashley.common;

import android.content.Context;
import android.widget.ImageView;
import com.isp1004.ashley.R;

import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2017-03-20.
 */

public class PicassoClient {
    public static void viewImage(Context context, String imageUrl, ImageView imageView) {
        if (imageUrl.length() > 0 && imageUrl != null) {
            Picasso.with(context).load(imageUrl).placeholder(R.drawable.placeholder).into(imageView);
        } else {
            Picasso.with(context).load(R.drawable.placeholder).into(imageView);
        }
    }
}
