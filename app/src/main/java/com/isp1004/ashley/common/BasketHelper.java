package com.isp1004.ashley.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.isp1004.ashley.myinfo.CardInfoContract;

/**
 * Created by h06 on 2017-04-07.
 */

public class BasketHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Ashley";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_BASKET =
            "CREATE TABLE " +
                    BasketContact.DBList.TABLE_NAME   + " (" +
                    BasketContact.DBList.BASKET_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    BasketContact.DBList.EMAIL        + " TEXT, " +
                    BasketContact.DBList.BRAND_NAME   + " TEXT, " +
                    BasketContact.DBList.PRODUCT_ID   + " TEXT, " +
                    BasketContact.DBList.PRODUCT_NAME + " TEXT, " +
                    BasketContact.DBList.QTY          + " INTEGER, " +
                    BasketContact.DBList.PRICE        + " INTEGER, " +
                    BasketContact.DBList.IS_ORDERED   + " TEXT, " +
                    BasketContact.DBList.IS_PAID      + " TEXT);";

    public BasketHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("Seulki", "Database Created / Opened");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BASKET);
        Log.d("Seulki", "Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Add Basket
    public void addBasket(String email, String brandName, String productId, String productName, String qty, String price, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(BasketContact.DBList.EMAIL, email);
        contentValues.put(BasketContact.DBList.BRAND_NAME, brandName);
        contentValues.put(BasketContact.DBList.PRODUCT_ID, productId);
        contentValues.put(BasketContact.DBList.PRODUCT_NAME, productName);
        contentValues.put(BasketContact.DBList.QTY, qty);
        contentValues.put(BasketContact.DBList.PRICE, price);
        contentValues.put(BasketContact.DBList.IS_ORDERED, "N");
        contentValues.put(BasketContact.DBList.IS_PAID, "N");

        db.insert(BasketContact.DBList.TABLE_NAME, null, contentValues);
        Log.d("Seulki", "add Basket info");
    }

    // Update Basket
    public void updateBasket(String basketId, String isOrdered, String isPaid, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(BasketContact.DBList.IS_ORDERED, isOrdered);
        contentValues.put(BasketContact.DBList.IS_PAID, isPaid);

        String selection = BasketContact.DBList.BASKET_ID + " = ?";
        String[] selection_args = { basketId };

        db.update(BasketContact.DBList.TABLE_NAME, contentValues, selection, selection_args);
        Log.d("Seulki", "update Basket info");

    }

    // Query Basket
    public Cursor queryBasket(String email, String isOrdered, String isPaid, SQLiteDatabase db) {
        Log.d("Seulki", "queryBasket");
        Cursor cursor;
        String[] projections = { BasketContact.DBList.BASKET_ID, BasketContact.DBList.BRAND_NAME, BasketContact.DBList.PRODUCT_ID, BasketContact.DBList.PRODUCT_NAME, BasketContact.DBList.QTY, BasketContact.DBList.PRICE, BasketContact.DBList.IS_ORDERED, BasketContact.DBList.IS_PAID};
        String selection = BasketContact.DBList.EMAIL + "=? and ";
        selection = selection + BasketContact.DBList.IS_ORDERED + "=? and ";
        selection = selection + BasketContact.DBList.IS_PAID + "=?";
        String[] selection_args = { email, isOrdered, isPaid };

        Log.d("Seulki", "selection : " + selection);
        Log.d("Seulki", "selection_args" + selection_args[0] + ", " + selection_args[1] + ", " + selection_args[2]);

        cursor = db.query(BasketContact.DBList.TABLE_NAME, projections, selection, selection_args, null, null, null);

        Log.d("Seulki", "Qeury basket");

        return cursor;
    }
}
