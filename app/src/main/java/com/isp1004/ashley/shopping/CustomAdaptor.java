package com.isp1004.ashley.shopping;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.isp1004.ashley.R;
import com.isp1004.ashley.common.PicassoClient;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Administrator on 2017-03-22.
 */

public class CustomAdaptor extends BaseAdapter {

    Context context;
    List<ProductVO> productListVOs;

    LayoutInflater inflater;

    public CustomAdaptor(Context context, List<ProductVO> productListVOs) {
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
        TextView productId = (TextView) convertView.findViewById(R.id.product_id);
        TextView qty = (TextView) convertView.findViewById(R.id.qty);
        TextView price = (TextView) convertView.findViewById(R.id.price);

        // BIND DATA
        final ProductVO productVO = this.productListVOs.get(position);

        DecimalFormat priceDecimalFormat = new DecimalFormat("C$###,###,###.00");
        DecimalFormat qtyDecimalFormat = new DecimalFormat("###,###,###");

        String strBrandName = productVO.getBrandName();
        if (strBrandName.length() > 17) {
            strBrandName = strBrandName.substring(0, 17);
        }
        String strProductName = productVO.getProductName();
        if (strProductName.length() > 17) {
            strProductName = strProductName.substring(0, 17);
        }
        String strProductId = productVO.getProductId();
        String strPrice = priceDecimalFormat.format(productVO.getPrice());

        brandNname.setText(strBrandName);
        productName.setText(strProductName);
        productId.setText(strProductId);
        price.setText(strPrice);

        if (productVO.getQty() != 0 ) {
            String strQty = qtyDecimalFormat.format(productVO.getQty());
            qty.setText(strQty);
        } else {
            qty.setText("Sold Out");
        }

        // IMG
        PicassoClient.viewImage(context, context.getResources().getString(R.string.serverUri) + productVO.getImgUrl(), productImg);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Seulki", productVO.getProductId());

                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("product_id", productVO.getProductId());

                context.startActivity(intent);
            }
        });


        return convertView;
    }
}
