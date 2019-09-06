package com.example.ganga.noline;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ganga.noline.data.InventoryContract;
import com.example.ganga.noline.data.InventoryDbHelper;

public class PayActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    /** Adapter for the ListView */
    PayCursorAdapter mCursorAdapter;

    // Add comment

    private static final String LOG_TAG = MainActivity.class.getName();

    /** Identifier for the pet data loader */
    private static final int PET_LOADER = 0;

    InventoryDbHelper mDbHelper;

    double taxtextandsummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        mDbHelper = new InventoryDbHelper(this);

        invalidateOptionsMenu();

        // The following line presents the back arrow button on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Find the ListView which will be populated with the purchase table data
        ListView purchaseListView = (ListView) findViewById(R.id.listItems);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        //View emptyView = findViewById(R.id.empty_view);
       // purchaseListView.setEmptyView(emptyView);

        purchaseListView.setCacheColorHint(Color.TRANSPARENT);
        purchaseListView.requestFocus(0);

        // Setup an Adapter to create a list item for each row of pet data in the Cursor.
        // There is no pet data yet (until the loader finishes) so pass in null for the Cursor.
        mCursorAdapter = new PayCursorAdapter(this, null);
        purchaseListView.setAdapter(mCursorAdapter);

        Intent intent = getIntent();
        double d = intent.getDoubleExtra("totalvalue", 0);
        String totalValue = String.valueOf(d);
        TextView subtotalText = (TextView) findViewById(R.id.subtotal_value);
        subtotalText.setText("$ " + totalValue);
        TextView taxValue = (TextView) findViewById(R.id.tax_value);
        taxValue.setText( "$ 1.5");
        TextView summaryValue = (TextView) findViewById(R.id.totalsummary_value);
        double taxtext = Double.parseDouble("1.5");
        taxtextandsummary = taxtext + d;
        String taxttextplussubtotal = String.valueOf(taxtextandsummary);
        summaryValue.setText("$ " + taxttextplussubtotal);
        getLoaderManager().initLoader(PET_LOADER, null, PayActivity.this);

        Button onPaybutton = (Button) findViewById(R.id.pay);
        onPaybutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                SQLiteDatabase db = mDbHelper.getReadableDatabase();
                int total = getSold();
                Intent intent = new Intent(PayActivity.this, PaymentSummaryActivity.class);
                intent.putExtra("totalsummary", taxtextandsummary);
                intent.putExtra("noofitems", total);
                startActivity(intent);

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_mainactivity, menu);
        return true;
    }

    // The following code is called when the invalidate options menu is called  in the oncreate method

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
            MenuItem menuItem = menu.findItem(R.id.add_inventory);
            menuItem.setVisible(false);
            MenuItem menuItem2 = menu.findItem(R.id.finditem_code);
            menuItem2.setVisible(false);
            MenuItem menuItem3 = menu.findItem(R.id.receipts);
            menuItem3.setVisible(false);
            MenuItem menuItem4 = menu.findItem(R.id.share_application);
            menuItem4.setVisible(false);
            MenuItem menuItem5 = menu.findItem(R.id.rate_experience);
            menuItem5.setVisible(false);
            MenuItem menuItem6 = menu.findItem(R.id.save_changes);
            menuItem6.setVisible(false);
            MenuItem menuItem7 = menu.findItem(R.id.edit_payment);
            menuItem7.setVisible(false);
            MenuItem menuItem8 = menu.findItem(R.id.add_payment);
            menuItem8.setVisible(false);
            return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                /** Below 2 lines are optional code to  handle up arrow, so that it doesnt recreate the previous activity
                  onBackPressed();
                 return true;
                 */

                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                            // Navigate up to the closest parent
                            .startActivities();
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    NavUtils.navigateUpTo(this, upIntent);
                }

                return true;
            case R.id.payment_method:

                Intent paymentmethodIntent = new Intent(PayActivity.this, PaymentMethodActivity.class);
                startActivity(paymentmethodIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    public int getSold(){
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
}
