package com.isp1004.ashley.myinfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.isp1004.ashley.GlobalApp;
import com.isp1004.ashley.R;

public class PaymentActivity extends AppCompatActivity {

    private String email;
    private GlobalApp globalApp;

    EditText editCardCompany;
    EditText editCardNumber;
    EditText editCsv;
    EditText editExpiration;
    EditText editName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);

        editCardCompany = (EditText) findViewById(R.id.editCardCompany);
        editCardNumber = (EditText) findViewById(R.id.editCardNumber);
        editCsv = (EditText) findViewById(R.id.editCsv);
        editExpiration = (EditText) findViewById(R.id.editExpirationDate);
        editName = (EditText) findViewById(R.id.editName);

        globalApp = (GlobalApp)getApplication();
        this.email = globalApp.getEmail();
    }

    public void buttonContinue(View view) {

        boolean isValidated = true;
        TextView textView = (TextView) findViewById(R.id.txtResult);

        String cardCompany = editCardCompany.getText().toString();
        String cardNumber = editCardNumber.getText().toString();
        String cardCsv = editCsv.getText().toString();
        String cardExpiration = editExpiration.getText().toString();
        String cardName = editName.getText().toString();

        // 1. validation

        if (cardCompany == null || cardCompany.isEmpty()) {
            textView.setText("Could you check your Card Company Information");
            isValidated = false;
        }
        if (cardNumber == null || cardNumber.isEmpty()) {
            textView.setText("Could you check your Card Number");
            isValidated = false;
        }
        if (cardCsv == null || cardCsv.isEmpty()) {
            textView.setText("Could you check your CSV number");
            isValidated = false;
        }
        if (cardExpiration == null || cardExpiration.isEmpty()) {
            textView.setText("Could you check your Card Expiration Date");
            isValidated = false;
        }
        if (cardName == null || cardName.isEmpty()) {
            textView.setText("Could you check your Card Name");
            isValidated = false;
        }

        if (isValidated == true) {

            Intent intent = new Intent(PaymentActivity.this, PaymentConfirmActivity.class);
            intent.putExtra("cardCompany", cardCompany);
            intent.putExtra("cardNumber", cardNumber);
            intent.putExtra("cardCsv", cardCsv);
            intent.putExtra("cardExpiration", cardExpiration);
            intent.putExtra("cardName", cardName);
            startActivity(intent);
        }

    }
}
