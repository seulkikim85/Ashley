package com.isp1004.ashley.myinfo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.isp1004.ashley.R;
import com.isp1004.ashley.common.BasketVO;
import com.isp1004.ashley.shopping.ProductDetailActivity;
import com.isp1004.ashley.shopping.ProductVO;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by h06 on 2017-04-07.
 */

public class BasketAdaptor extends BaseAdapter {

    Context context;
    List<BasketVO> basketListVOs;

    LayoutInflater inflater;

    public BasketAdaptor(Context context, List<BasketVO> basketListVOs) {
        this.context = context;
        this.basketListVOs = basketListVOs;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return basketListVOs.size();
    }

    @Override
    public Object getItem(int position) {
        return basketListVOs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.basket_layout, parent, false);
        }

        TextView tvBrandName   = (TextView)convertView.findViewById(R.id.basket_brand_name);
        TextView tvProductName = (TextView)convertView.findViewById(R.id.basket_product_name);
        TextView tvProductId   = (TextView)convertView.findViewById(R.id.basket_product_id);
        TextView tvQty         = (TextView)convertView.findViewById(R.id.basket_qty);
        TextView tvPrice       = (TextView)convertView.findViewById(R.id.basket_price);
        TextView tvOriginQty   = (TextView)convertView.findViewById(R.id.basket_origin_qty);
        TextView tvOriginPrice = (TextView)convertView.findViewById(R.id.basket_origin_price);

        // Bind Data
        final BasketVO basketVO = basketListVOs.get(position);

        DecimalFormat priceDecimalFormat = new DecimalFormat("C$###,###,###.00");
        DecimalFormat qtyDecimalFormat = new DecimalFormat("###,###,###");

        String strBrandName = basketVO.getBrandName();
        if (strBrandName.length() > 17) {
            strBrandName = strBrandName.substring(0, 17);
        }
        String strProductName = basketVO.getProductName();
        if (strProductName.length() > 17) {
            strProductName = strProductName.substring(0, 17);
        }
        String strProductId = basketVO.getProductId();
        String strPrice = priceDecimalFormat.format(basketVO.getPrice());
        String strQty = qtyDecimalFormat.format(basketVO.getQty());
        String strOriginQty = String.valueOf(basketVO.getQty());
        String strOriginPrice = String.valueOf(basketVO.getPrice());


        tvBrandName.setText(strBrandName);
        tvProductName.setText(strProductName);
        tvProductId.setText(strProductId);
        tvPrice.setText(strPrice);
        tvQty.setText(strQty);
        tvOriginQty.setText(strOriginQty);
        tvOriginPrice.setText(strOriginPrice);

        return convertView;
    }
}
