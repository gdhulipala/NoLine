package com.example.ganga.noline;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import com.example.ganga.noline.data.InventoryContract;
import com.example.ganga.noline.data.InventoryDbHelper;

public class FindByItemNameActivity extends AppCompatActivity {

    FindByItemCursorAdapter mCursorAdapter;
    /**
     * Identifier for the pet data loader
     */
    private static final int PET_LOADER = 0;

    private static final String LOG_TAG = FindByItemNameActivity.class.getName();

    InventoryDbHelper mDbHelper;

    EditText inputSearch;

    ListView petListView;

    MenuItem searchItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_by_item_name);

        mDbHelper = new InventoryDbHelper(this);

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        // Perform this raw SQL query "SELECT * FROM inventory"
        // to get a Cursor that contains all rows from the inventory table.
        Cursor cursor = db.rawQuery("SELECT * FROM " + InventoryContract.InventoryEntry.TABLE_NAME, null);
        Log.e(LOG_TAG, "cursor2 count  " + cursor.getCount());

        // Find the ListView which will be populated with the pet data
        petListView = (ListView) findViewById(R.id.listItems);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        petListView.setEmptyView(emptyView);

        // Setup an Adapter to create a list item for each row of pet data in the Cursor.
        // There is no pet data yet (until the loader finishes) so pass in null for the Cursor.
        mCursorAdapter = new FindByItemCursorAdapter(this, cursor);
        petListView.setAdapter(mCursorAdapter);
        mCursorAdapter.changeCursor(cursor);
        petListView.setTextFilterEnabled(true);


        // Setup the item click listener
        petListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent intent = new Intent(FindByItemNameActivity.this, MainActivity.class);
                intent.putExtra("idValue", id);
                intent.putExtra("calling-activity", Constants.FindByItemNameActivity);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_item_search).getActionView();
        searchView.setSearchableInfo( searchManager.getSearchableInfo(getComponentName()) );

        return true;
    }

    @Override
    public boolean onSearchRequested() {
        Bundle appData = new Bundle();
        appData.putString("hello", "world");
        startSearch(null, false, appData, false);
        return true;
    }
}
