package com.example.ganga.noline.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by ganga on 9/30/17.
 */

public class InventoryDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = InventoryDbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "inventory.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;


    /**
     * Constructs a new instance of {@link InventoryDbHelper}.
     *
     * @param context of the app
     */


    public InventoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create a String that contains the SQL statement to create the inventory table as soon as the sellers starts to save inventory

        String SQL_CREATE_PETS_TABLE =  "CREATE TABLE " + InventoryContract.InventoryEntry.TABLE_NAME + " ("
                + InventoryContract.InventoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, "
                + InventoryContract.InventoryEntry.COLUMN_PRICE + " INTEGER NOT NULL,"
                + InventoryContract.InventoryEntry.COLUMN_CODE + " LONG NOT NULL,"
                + InventoryContract.InventoryEntry.COLUMN_IMAGE + " BLOB NOT NULL, "
                + InventoryContract.InventoryEntry.COLUMN_QUANTITY + " INTEGER NOT NULL " + ");";

        // Create a String that contains the SQL statement to create the purchase table as soon as the buyer press the scan button

        String SQL_CREATE_PETS_TABLE_PURCHASE =  "CREATE TABLE " + InventoryContract.PurchaseEntry.TABLE_NAME + " ("
                + InventoryContract.PurchaseEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + InventoryContract.PurchaseEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, "
                + InventoryContract.PurchaseEntry.COLUMN_PRICE + " INTEGER NOT NULL,"
                + InventoryContract.PurchaseEntry.COLUMN_IMAGE + " BLOB NOT NULL, "
                + InventoryContract.PurchaseEntry.COLUMN_QUANTITY + " INTEGER NOT NULL, "
                + InventoryContract.PurchaseEntry.COLUMN_NEWPRICE + ");";
                Log.d(TAG, "Database Created Successfully" );


        // Execute the SQL statement
        db.execSQL(SQL_CREATE_PETS_TABLE);
        Log.d(TAG, "Database Created Successfully" );

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_PETS_TABLE_PURCHASE);
        Log.d(TAG, "Database Created Successfully" );
    }

    /**
     * This is called when the database needs to be upgraded.
     */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + InventoryContract.InventoryEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + InventoryContract.PurchaseEntry.TABLE_NAME);
        // Create tables again
        onCreate(db);

    }
}
