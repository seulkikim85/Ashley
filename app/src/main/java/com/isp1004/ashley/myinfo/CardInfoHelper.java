package com.isp1004.ashley.myinfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.isp1004.ashley.common.BasketContact;

/**
 * Created by Administrator on 2017-04-11.
 */

public class CardInfoHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Ashley";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_QUERY =
            "CREATE TABLE " +
                    CardInfoContract.DBList.TABLE_NAME   + " (" +
                    CardInfoContract.DBList.EMAIL        + " TEXT PRIMARY KEY, " +
                    CardInfoContract.DBList.CARD_COMPANY        + " TEXT, " +
                    CardInfoContract.DBList.CARD_NUMBER + " TEXT, " +
                    CardInfoContract.DBList.CARD_CSV   + " TEXT, " +
                    CardInfoContract.DBList.CARD_EXPIRATION + " TEXT, " +
                    CardInfoContract.DBList.CARD_NAME      + " TEXT);";

    public CardInfoHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("Seulki", "Card info Database Created / Opened");
    }


    public CardInfoHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public CardInfoHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_QUERY);
        Log.d("Seulki", "Card info Table Created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Add Card Information
    public void addCard(String email, String cardCompany, String cardNumber, String cardCsv, String cardExpiration, String cardName, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(CardInfoContract.DBList.EMAIL, email);
        contentValues.put(CardInfoContract.DBList.CARD_COMPANY, cardCompany);
        contentValues.put(CardInfoContract.DBList.CARD_NUMBER, cardNumber);
        contentValues.put(CardInfoContract.DBList.CARD_CSV, cardCsv);
        contentValues.put(CardInfoContract.DBList.CARD_EXPIRATION, cardExpiration);
        contentValues.put(CardInfoContract.DBList.CARD_NAME, cardName);



        db.insert(CardInfoContract.DBList.TABLE_NAME, null, contentValues);
        Log.d("Seulki", "add Card info");
    }

    // Update Basket
    public void updateCardInfo(String email, String cardCompany, String cardNumber, String cardCsv, String cardExpiration, String cardName, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(CardInfoContract.DBList.CARD_COMPANY, cardCompany);
        contentValues.put(CardInfoContract.DBList.CARD_NUMBER, cardNumber);
        contentValues.put(CardInfoContract.DBList.CARD_CSV, cardCsv);
        contentValues.put(CardInfoContract.DBList.CARD_EXPIRATION, cardExpiration);
        contentValues.put(CardInfoContract.DBList.CARD_NAME, cardName);



        String selection = CardInfoContract.DBList.EMAIL + " = ?";
        String[] selection_args = { email };

        db.update(CardInfoContract.DBList.TABLE_NAME, contentValues, selection, selection_args);
        Log.d("Seulki", "update Card info");

    }

    // Query Basket
    public Cursor queryCardInfo(String email, SQLiteDatabase db) {
        Log.d("Seulki", "queryCardInfo");

        Cursor cursor;
        String[] projections = { CardInfoContract.DBList.CARD_COMPANY, CardInfoContract.DBList.CARD_NUMBER, CardInfoContract.DBList.CARD_CSV, CardInfoContract.DBList.CARD_EXPIRATION, CardInfoContract.DBList.CARD_NAME};
        String selection = CardInfoContract.DBList.EMAIL + "=? ";

        String[] selection_args = { email };

        Log.d("Seulki", "selection : " + selection);
        Log.d("Seulki", "selection_args" + selection_args[0]);

        cursor = db.query(CardInfoContract.DBList.TABLE_NAME, projections, selection, selection_args, null, null, null);

        Log.d("Seulki", "Qeury CardInfo");

        return cursor;
    }

}
