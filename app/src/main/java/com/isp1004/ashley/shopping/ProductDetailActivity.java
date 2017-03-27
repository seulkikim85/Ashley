package com.isp1004.ashley.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.isp1004.ashley.R;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);

        Intent intent = this.getIntent();
        String strProductId = intent.getStringExtra("product_id");
        TextView pdetailProductId = (TextView)findViewById(R.id.pdetail_product_id);
        pdetailProductId.setText(strProductId);
    }
}
