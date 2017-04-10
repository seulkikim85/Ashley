package com.isp1004.ashley.myinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.isp1004.ashley.R;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Administrator on 2017-04-10.
 */

public class OrderAdaptor extends BaseAdapter {

    Context context;
    List<OrderVO> orderVOs;
    LayoutInflater inflater;

    public OrderAdaptor(Context context, List<OrderVO> orderVOs) {
        this.context = context;
        this.orderVOs = orderVOs;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.orderVOs.size();
    }

    @Override
    public Object getItem(int position) {
        return this.orderVOs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.order_layout, parent, false);
        }

        TextView txtDate = (TextView)convertView.findViewById(R.id.order_date);
        TextView txtBrandName = (TextView)convertView.findViewById(R.id.order_brand_name);
        TextView txtProductName = (TextView)convertView.findViewById(R.id.order_product_name);
        TextView txtQty = (TextView)convertView.findViewById(R.id.order_qty);
        TextView txtPrice = (TextView)convertView.findViewById(R.id.order_price);

        final OrderVO orderVO = orderVOs.get(position);

        DecimalFormat priceDecimalFormat = new DecimalFormat("C$###,###,###.00");
        DecimalFormat qtyDecimalFormat = new DecimalFormat("###,###,###");

        String strOrderDate = orderVO.getOrderDate();
        String strBrandName = orderVO.getBrandName();
        if (strBrandName.length() > 17) {
            strBrandName = strBrandName.substring(0, 17);
        }
        String strProductName = orderVO.getProductName();
        if (strProductName.length() > 17) {
            strProductName = strProductName.substring(0, 17);
        }

        String strPrice = priceDecimalFormat.format(orderVO.getPrice());
        String strQty = qtyDecimalFormat.format(orderVO.getQty());

        txtDate.setText(strOrderDate);
        txtBrandName.setText(strBrandName);
        txtProductName.setText(strProductName);
        txtQty.setText(strQty);
        txtPrice.setText(strPrice);

        return convertView;
    }

}
