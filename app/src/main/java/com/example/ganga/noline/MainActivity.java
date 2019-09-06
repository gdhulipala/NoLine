package com.example.ganga.noline;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ganga.noline.data.InventoryContract;
import com.example.ganga.noline.data.InventoryDbHelper;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = MainActivity.class.getName();

    /**
     * Identifier for the pet data loader
     */
    private static final int PET_LOADER = 0;

    /**
     * Adapter for the ListView
     */
    InventoryCursorAdapter mCursorAdapter;


    InventoryDbHelper mDbHelper;

    int number = 0;

    double d = 0.0;

    int code = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Added int

        int i =0;

        //intialize the InventoryDbHelper
        mDbHelper = new InventoryDbHelper(this);


        // Invalidate options menu calls the onpreparationsmenu method written below to display the right menu related to current activity
        invalidateOptionsMenu();


        /**The following if clause checks if main activity is being launched for the first time or its being called by other activity.
         If it is called from Payment summary activity or finditembyname activity, the rows in the current purchase table are not deleted. Under the
         current logic, every time the main activity is launched for the first time, the rows in the current purchase table are deleted.*/

        int callingActivity = getIntent().getIntExtra("calling-activity", 0);
        Log.e(LOG_TAG, "called constants" + callingActivity);


        if (callingActivity == Constants.PaymentSummaryActivity || callingActivity == Constants.FindByItemNameActivity) {


            if (callingActivity == Constants.FindByItemNameActivity) {

                /** One it is detrmined that the calling activity is finditembynameactivity, the id of the item that was clicked in the finditembyname activity is retreived
                 through get intent method. The retreived id is furthur passed on to sql query method to query the sellers table and retreive the item corresponding to the id
                 and add it to the current purchase table so that it is displayed in the cart list*/

                Intent intent = getIntent();
                long id = intent.getLongExtra("idValue", 1);

                //Getting the database connection

                SQLiteDatabase db = mDbHelper.getReadableDatabase();

                //Querying the datatabase with the id i.e. received from finditem byname activity
                Cursor cursor = db.rawQuery("SELECT * FROM " + InventoryContract.InventoryEntry.TABLE_NAME + " WHERE " + InventoryContract.InventoryEntry._ID + " = \"" + id + "\"", null);
                Log.e(LOG_TAG, "onclick" + id);

                int idnumber = (int) id;


                int productColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME);
                int priceColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_PRICE);
                int imageColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_IMAGE);
                int quantityColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_QUANTITY);

                Log.e(LOG_TAG, "onclick" + cursor.getCount());

                cursor.moveToFirst();

                String pName = cursor.getString(productColumnIndex);
                int price = cursor.getInt(priceColumnIndex);
                byte[] image = cursor.getBlob(imageColumnIndex);
                int quantity = cursor.getInt(quantityColumnIndex);

                // Each time the user press the scan button, one item is transfreed ata a time from the inventory table to purchase table
                // The code below inserts the values from inventory table to purchase table

                ContentValues values = new ContentValues();
                values.put(InventoryContract.PurchaseEntry.COLUMN_PRODUCT_NAME, pName);
                values.put(InventoryContract.PurchaseEntry.COLUMN_PRICE, price);
                values.put(InventoryContract.PurchaseEntry.COLUMN_IMAGE, image);
                values.put(InventoryContract.PurchaseEntry.COLUMN_QUANTITY, quantity);
                values.put(InventoryContract.PurchaseEntry.COLUMN_NEWPRICE, price);


                Uri newUri = getContentResolver().insert(InventoryContract.PurchaseEntry.CONTENT_URI, values);
                Log.e(LOG_TAG, "Inventory provider method is called" + newUri);

                cursor.close();

                //Kick off the loader
                getLoaderManager().initLoader(PET_LOADER, null, MainActivity.this);

            }

        } else {


            int rowsDeleted = getContentResolver().delete(InventoryContract.PurchaseEntry.CONTENT_URI, null, null);
        }

        // Find the ListView which will be populated with the purchase table data
        ListView purchaseListView = (ListView) findViewById(R.id.list);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        purchaseListView.setEmptyView(emptyView);

        purchaseListView.setCacheColorHint(Color.TRANSPARENT);
        purchaseListView.requestFocus(0);

        // Setup an Adapter to create a list item for each row of pet data in the Cursor.
        // There is no pet data yet (until the loader finishes) so pass in null for the Cursor.
        mCursorAdapter = new InventoryCursorAdapter(this, null);
        purchaseListView.setAdapter(mCursorAdapter);


        ImageView cartImage = (ImageView) findViewById(R.id.scan_button);
        cartImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int callingActivity = getIntent().getIntExtra("calling-activity", 0);

                if (callingActivity == Constants.PaymentSummaryActivity && code == 0) {
                    Log.e(LOG_TAG, "called constants" + callingActivity);
                    int rowsDeleted = getContentResolver().delete(InventoryContract.PurchaseEntry.CONTENT_URI, null, null);
                    //Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    //startActivity(intent);
                    code = code + 1;
                }

                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                integrator.setPrompt("Scan a Bar Code");
                integrator.setCaptureActivity(TorchOnCaptureActivity.class);
                integrator.setOrientationLocked(false);
                //integrator.setResultDisplayDuration(0);
                //integrator.setWide();  // Wide scanning rectangle, may work better for 1D barcodes
                integrator.setCameraId(0);  // Use a specific camera of the device
                integrator.setBarcodeImageEnabled(true);
                // set turn the camera flash on by default
                // integrator.addExtra(appConstants.CAMERA_FLASH_ON,true);
                integrator.initiateScan();


            }

        });

        Button checkOut = (Button) findViewById(R.id.checkout);
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (d > 0) {
                    Intent intent = new Intent(MainActivity.this, PayActivity.class);
                    intent.putExtra("totalvalue", d);
                    startActivity(intent);
                } else {

                    Toast.makeText(MainActivity.this, "No items Scanned", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem menuItem = menu.findItem(R.id.save_changes);
        menuItem.setVisible(false);
        MenuItem menuItem2 = menu.findItem(R.id.edit_payment);
        menuItem2.setVisible(false);
        MenuItem menuItem3 = menu.findItem(R.id.add_payment);
        menuItem3.setVisible(false);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_mainactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {

            case R.id.add_inventory:
                Intent intent = new Intent(MainActivity.this, AddInventoryActivity.class);
                startActivity(intent);
                return true;

            case R.id.receipts:
                Intent receiptIntent = new Intent(MainActivity.this, ReceiptsActivity.class);
                startActivity(receiptIntent);
                return true;

            case R.id.payment_method:

                Intent paymentmethodIntent = new Intent(MainActivity.this, PaymentMethodActivity.class);
                startActivity(paymentmethodIntent);
                return true;


            case R.id.rate_experience:

                Intent rateexperienceIntent = new Intent(MainActivity.this, RatingActivity.class);
                startActivity(rateexperienceIntent);
                return true;

            case R.id.share_application:

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Skip the Line With NoLine");
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out my app at: https://play.google.com/store/apps/details?id=com.google.android.apps.noline");
                sendIntent.setType("text/plain");
                if (sendIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(Intent.createChooser(sendIntent, "Share"));
                    return true;
                }

            case R.id.finditem_code:

                Intent findItemcodeIntent = new Intent(MainActivity.this, FindByItemNameActivity.class);
                startActivity(findItemcodeIntent);
                return true;
            //startActivity(sendIntent);
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
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
        TextView totalText = (TextView) findViewById(R.id.total_sum);
        int total = getSold();
        d = (double) total;
        Log.e(LOG_TAG, "Total Text Value " + d);
        if (total > 0) {
            String totalValue = String.valueOf(d);
            totalText.setText("$ " + totalValue);
        } else {

            totalText.setText("0.0");
        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Callback called when the data needs to be deleted
        mCursorAdapter.swapCursor(null);

    }

    public int getSold() {
        int sold = 0;
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor = database.query(InventoryContract.PurchaseEntry.TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                sold += cursor.getInt(cursor.getColumnIndex(InventoryContract.PurchaseEntry.COLUMN_NEWPRICE));
                cursor.moveToNext();
            }
            cursor.close();
        }
        database.close();
        return sold;
    }

    public int getQuant() {
        int sold = 0;
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor = database.query(InventoryContract.PurchaseEntry.TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                sold += cursor.getInt(cursor.getColumnIndex(InventoryContract.PurchaseEntry.COLUMN_QUANTITY));
                cursor.moveToNext();
            }
            cursor.close();
        }
        database.close();
        return sold;
    }

    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startActivity(startMain);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        long barcode =0;
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                String code_scanned = result.getContents();
                try {
                    barcode = Long.parseLong(code_scanned);
                }catch (NumberFormatException nfe){

                }

                Cursor cursor2 = null;

                // Create and/or open a database to read from it
                SQLiteDatabase db = mDbHelper.getReadableDatabase();

                try {

                    // Perform this raw SQL query "SELECT * FROM inventory"
                    // to get a Cursor that contains all rows from the inventory table.
                    cursor2 = db.rawQuery("SELECT * FROM " + InventoryContract.InventoryEntry.TABLE_NAME + " WHERE " + InventoryContract.InventoryEntry.COLUMN_CODE + " = \"" + barcode + "\"", null);

                    if (cursor2.getCount() >= 1) {

                        cursor2.moveToPosition(number);

                        int productColumnIndex = cursor2.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME);
                        int priceColumnIndex = cursor2.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_PRICE);
                        int imageColumnIndex = cursor2.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_IMAGE);
                        int quantityColumnIndex = cursor2.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_QUANTITY);

                        String pName = cursor2.getString(productColumnIndex);
                        int price = cursor2.getInt(priceColumnIndex);
                        byte[] image = cursor2.getBlob(imageColumnIndex);
                        int quantity = cursor2.getInt(quantityColumnIndex);

                        // Each time the user press the scan button, one item is transfreed ata a time from the inventory table to purchase table
                        // The code below inserts the values from inventory table to purchase table

                        ContentValues values = new ContentValues();
                        values.put(InventoryContract.PurchaseEntry.COLUMN_PRODUCT_NAME, pName);
                        values.put(InventoryContract.PurchaseEntry.COLUMN_PRICE, price);
                        values.put(InventoryContract.PurchaseEntry.COLUMN_IMAGE, image);
                        values.put(InventoryContract.PurchaseEntry.COLUMN_QUANTITY, quantity);
                        values.put(InventoryContract.PurchaseEntry.COLUMN_NEWPRICE, price);


                        Uri newUri = getContentResolver().insert(InventoryContract.PurchaseEntry.CONTENT_URI, values);
                        Log.e(LOG_TAG, "Inventory provider method is called" + newUri);


                        // Insert a new row for purchased items in the database, returning the ID of that new row.

                        getLoaderManager().initLoader(PET_LOADER, null, MainActivity.this);

                        /**TextView totalText = (TextView) findViewById(R.id.total_sum);
                         int total = getSold();
                         Log.e(LOG_TAG, "Total Text Value " + total);
                         if(total>0){
                         String totalValue = String.valueOf(total);
                         totalText.setText(totalValue);
                         //getLoaderManager().initLoader(PET_LOADER, null, MainActivity.this);
                         }*/


                        //Show a toast message depending on whether or not the insertion was successful.
                        if (newUri == null) {
                            Log.e(LOG_TAG, "Error");
                        } else {
                            Log.e(LOG_TAG, "Apple Saved");
                        }

                    } else {

                        Toast.makeText(MainActivity.this, " First Add Inventory From Menu Before You Scan", Toast.LENGTH_SHORT).show();
                    }

                } finally {

                    if (cursor2.getCount() > number)

                        number = number + 1;

                }
                if (cursor2.getCount() == number) {

                    number = 0;
                }
            }


        }
    }
}






