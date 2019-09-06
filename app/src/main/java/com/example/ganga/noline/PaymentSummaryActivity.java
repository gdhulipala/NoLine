package com.example.ganga.noline;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ganga.noline.data.InventoryContract;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PaymentSummaryActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    /** Adapter for the ListView */
    PayCursorAdapter mCursorAdapter;

    private static final String LOG_TAG = MainActivity.class.getName();

    /** Identifier for the pet data loader */
    private static final int PET_LOADER = 0;

    String totalsummaryText;
    String itemsTotalText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_summary);


        Intent intent = getIntent();
        int numberofItems = intent.getIntExtra("noofitems", 0);
        double summaryTotal = intent.getDoubleExtra("totalsummary", 0.0);

        TextView totalsummary = (TextView) findViewById(R.id.totalfinal);
        totalsummaryText = String.valueOf(summaryTotal);
        totalsummary.setText("$ " + totalsummaryText);

        TextView itemsTotal = (TextView) findViewById(R.id.numberofItems);
        itemsTotalText = String.valueOf(numberofItems);
        itemsTotal.setText(itemsTotalText + " Items");

        Date currentDate = new Date(System.currentTimeMillis());
        Toast.makeText(PaymentSummaryActivity.this, currentDate.toString(), Toast.LENGTH_SHORT).show();
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

        getLoaderManager().initLoader(PET_LOADER, null, PaymentSummaryActivity.this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_paymentsummary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.add_done:

                SharedPreferences shrdpref = getSharedPreferences("my_pref", MODE_PRIVATE);
                SharedPreferences.Editor edit = shrdpref.edit();
                edit.putString("totalValue", totalsummaryText);
                edit.putString("noofItems", itemsTotalText);
                edit.commit();
                Intent intent = new Intent(PaymentSummaryActivity.this, MainActivity.class);
                intent.putExtra("calling-activity", Constants.PaymentSummaryActivity);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startActivity(startMain);
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



}
