package com.example.ganga.noline;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ganga.noline.data.InventoryContract;
import com.example.ganga.noline.data.InventoryDbHelper;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ReceiptDetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    /** Adapter for the ListView */
    PayCursorAdapter mCursorAdapter;

    InventoryDbHelper mDbHelper;


    private static final String LOG_TAG = ReceiptDetailsActivity.class.getName();

    /** Identifier for the pet data loader */
    private static final int PET_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_details);

        mDbHelper = new InventoryDbHelper(this);

        TextView totalsummary = (TextView) findViewById(R.id.totalfinal);
        int total = getSold();
        double d = (double) total;
        double totalPlusTax = d +1.5;
        Log.e(LOG_TAG, "Total Text Value " + d);
        if(total>0){
            String totalValue = String.valueOf(totalPlusTax);
            totalsummary.setText( "$ " + totalValue);
        }else{

            totalsummary.setText("0.0");
        }
        TextView itemsTotal = (TextView) findViewById(R.id.numberofItems);
        int quantity = getQuant();
        if(total>0){
            String totalquantity = String.valueOf(quantity);
            itemsTotal.setText(totalquantity + " Items");
        }else{

            totalsummary.setText("0.0");
        }


        Date currentDate = new Date(System.currentTimeMillis());
        Toast.makeText(ReceiptDetailsActivity.this, currentDate.toString(), Toast.LENGTH_SHORT).show();
        TextView dateTextView = (TextView) findViewById(R.id.current_date);
        dateTextView.setText(currentDate.toString());

        TextView timeStamp = (TextView) findViewById(R.id.current_time);



        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy/HH:mm:ss", Locale.US);
        String strDate = sdf.format(cal.getTime());

        String[] values = strDate.split("/", 0);

        for(int i =0; i<values.length; i++){

            dateTextView.setText(values[0]);
            timeStamp.setText(values[1]);

        }

        // Find the ListView which will be populated with the purchase table data
        ListView purchaseListView = (ListView) findViewById(R.id.list_paymentsummary);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        //View emptyView = findViewById(R.id.empty_view);
        // purchaseListView.setEmptyView(emptyView);

        purchaseListView.setCacheColorHint(Color.TRANSPARENT);
        purchaseListView.requestFocus(0);

        // Setup an Adapter to create a list item for each row of pet data in the Cursor.
        // There is no pet data yet (until the loader finishes) so pass in null for the Cursor.
        mCursorAdapter = new PayCursorAdapter(this, null);
        purchaseListView.setAdapter(mCursorAdapter);

        getLoaderManager().initLoader(PET_LOADER, null, ReceiptDetailsActivity.this);
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Log.e(LOG_TAG, "Oncreate loader is called");
        // Define a projection that specifies the columns from the table we care about.
        String[] projection = {
                InventoryContract.PurchaseEntry._ID,
                InventoryContract.PurchaseEntry.COLUMN_PRODUCT_NAME,
                InventoryContract.PurchaseEntry.COLUMN_PRICE,
                InventoryContract.PurchaseEntry.COLUMN_IMAGE,
                InventoryContract.PurchaseEntry.COLUMN_QUANTITY,
                InventoryContract.PurchaseEntry.COLUMN_NEWPRICE};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                InventoryContract.PurchaseEntry.CONTENT_URI,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Update {@link PetCursorAdapter} with this new cursor containing updated pet data
        mCursorAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Callback called when the data needs to be deleted
        mCursorAdapter.swapCursor(null);


    }

    public int getQuant(){
        int sold = 0;
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor = database.query(InventoryContract.PurchaseEntry.TABLE_NAME,null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                sold += cursor.getInt(cursor.getColumnIndex(InventoryContract.PurchaseEntry.COLUMN_QUANTITY));
                cursor.moveToNext();
            }
            cursor.close();
        }
        database.close();
        return sold;
    }

    public int getSold(){
        int sold = 0;
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor = database.query(InventoryContract.PurchaseEntry.TABLE_NAME,null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                sold += cursor.getInt(cursor.getColumnIndex(InventoryContract.PurchaseEntry.COLUMN_NEWPRICE));
                cursor.moveToNext();
            }
            cursor.close();
        }
        database.close();
        return sold;
    }
}
