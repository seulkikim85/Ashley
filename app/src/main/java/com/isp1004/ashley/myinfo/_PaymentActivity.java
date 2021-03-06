package com.isp1004.ashley.myinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.isp1004.ashley.GlobalApp;
import com.isp1004.ashley.R;

public class _PaymentActivity extends AppCompatActivity {

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

        editCardCompany = (EditText) findViewById(R.id.editCardCompany);
        editCardNumber = (EditText) findViewById(R.id.editCardNumber);
        editCsv = (EditText) findViewById(R.id.editCsv);
        editExpiration = (EditText) findViewById(R.id.editExpirationDate);
        editName = (EditText) findViewById(R.id.editName);

        globalApp = (GlobalApp)getApplication();
        this.email = globalApp.getEmail();

        /*
        new CardInfoLoader(_PaymentActivity.this, editCardCompany, editCardNumber, editCsv, editExpiration, editName, this.email).execute();
        */
    }

/*
    class CardInfoLoader extends AsyncTask<Void,Void,String> {

        Context context;
        EditText editCardCompany;
        EditText editCardNumber;
        EditText editCsv;
        EditText editExpiration;
        EditText editName ;
        String email;

        ProgressDialog pd;
        SQLiteDatabase sqLiteDatabase;

        public CardInfoLoader(Context context, EditText editCardCompany, EditText editCardNumber, EditText editCsv, EditText editExpiration, EditText editName, String email) {
            this.context = context;
            this.editCardCompany = editCardCompany;
            this.editCardNumber = editCardNumber;
            this.editCsv = editCsv;
            this.editExpiration = editExpiration;
            this.editName = editName;
            this.email = email;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            pd = new ProgressDialog(context);
            pd.setTitle("Retrives");
            pd.setMessage("Retriving... Please wait.");
            pd.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            return loadData();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
        }

        private String loadData() {

            CardInfoHelper cardInfoHelper = new CardInfoHelper(context);
            sqLiteDatabase = cardInfoHelper.getReadableDatabase();
            Cursor cursor = cardInfoHelper.queryCardInfo(email, sqLiteDatabase);

            if (cursor.moveToFirst()) {

                    String email = cursor.getString(0);
                    String cardCompany = cursor.getString(1);
                    String cardNumber = cursor.getString(2);
                    String cardCsv = cursor.getString(3);
                    String cardExpiration = cursor.getString(4);
                    String cardName = cursor.getString(5);


                editCardCompany.setText(cardCompany);
                editCardNumber.setText(cardNumber);
                editCsv.setText(cardCsv);
                editExpiration.setText(cardExpiration);
                editName.setText(cardName);
            }


            return null;
        }
    }

*/


    public void buttonContinue(View view) {

        boolean isValidated = true;
        TextView textView = (TextView) findViewById(R.id.txtResult);

        String cardCompany = editCardCompany.getText().toString();
        String cardNumber = editCardNumber.getText().toString();
        String cardCsv = editCsv.getText().toString();
        String cardExpiration = editExpiration.getText().toString();
        String cardName = editName.getText().toString();

        // 1. validation
        if (isValidated = false) {
            if(cardCompany==null || cardCompany.isEmpty()) {
                textView.setText("Could you check your Card Company Information");
            } if(cardNumber==null || cardNumber.isEmpty()) {
                textView.setText("Could you check your Card Number");
            } if(cardCsv==null || cardCsv.isEmpty()) {
                textView.setText("Could you check your CSV number");
            } if (cardExpiration==null || cardExpiration.isEmpty()) {
                textView.setText("Could you check your Card Expiration Date");
            } if(cardName==null || cardName.isEmpty()) {
                textView.setText("Could you check your Card Name");
            }

            /*
            // 2. SQL exist
            CardInfoHelper cardInfoHelper = new CardInfoHelper(_PaymentActivity.this);

            SQLiteDatabase sqLiteDatabase = cardInfoHelper.getReadableDatabase();
            Cursor cursor = cardInfoHelper.queryCardInfo(email, sqLiteDatabase);

            SQLiteDatabase sqLiteWritableDatabase = cardInfoHelper.getWritableDatabase();

            if (cursor.moveToFirst()) {

                // 3-1. if exist , update
                cardInfoHelper.updateCardInfo(email,cardCompany, cardNumber, cardCsv, cardExpiration, cardName,sqLiteWritableDatabase);
            } else {
                // 3-2  if not exist, insert
                cardInfoHelper.addCard(email, cardCompany, cardNumber, cardCsv, cardExpiration, cardName,sqLiteWritableDatabase);
            }
            */
            Intent intent = new Intent(this, _PaymentConfirmActivity.class);
            intent.putExtra("cardCompany", cardCompany);
            intent.putExtra("cardNumber", cardNumber);
            intent.putExtra("cardCsv", cardCsv);
            intent.putExtra("cardExpiration", cardExpiration);
            intent.putExtra("cardName", cardName);
            startActivity(intent);
        }
    }
}
