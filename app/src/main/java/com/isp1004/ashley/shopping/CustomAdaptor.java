package com.isp1004.ashley.shopping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.isp1004.ashley.R;
import com.isp1004.ashley.common.PicassoClient;

import java.util.List;

/**
 * Created by Administrator on 2017-03-22.
 */

public class CustomAdaptor extends BaseAdapter {

    Context context;
    List<ProductListVO> productListVOs;

    LayoutInflater inflater;

    public CustomAdaptor(Context context, List<ProductListVO> productListVOs) {
        this.context = context;
        this.productListVOs = productListVOs;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return productListVOs.size();
    }

    @Override
    public Object getItem(int position) {
        return productListVOs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.product, parent, false);
        }

        ImageView productImg = (ImageView) convertView.findViewById(R.id.product_img);
        TextView brandNname = (TextView) convertView.findViewById(R.id.brand_name);
        TextView productName = (TextView) convertView.findViewById(R.id.product_name);
        TextView qty = (TextView) convertView.findViewById(R.id.qty);
        TextView price = (TextView) convertView.findViewById(R.id.price);

        // BIND DATA
        ProductListVO productListVO = this.productListVOs.get(position);

        String strBrandName = productListVO.getBrandName().substring(0, 18);
        String strProductName = productListVO.getProductName().substring(0, 18);
        String strPrice = String.valueOf(productListVO.getPrice());

        brandNname.setText(strBrandName);
        productName.setText(strProductName);
        price.setText(strPrice);

        if (productListVO.getQty() != 0 ) {
            String strQty = String.valueOf(productListVO.getQty());
            qty.setText(strQty);
        } else {
            qty.setText("Sold Out");
        }

        // IMG
        PicassoClient.viewImage(context, context.getResources().getString(R.string.serverUri) + productListVO.getImgUrl(), productImg);


        return convertView;
    }
}
