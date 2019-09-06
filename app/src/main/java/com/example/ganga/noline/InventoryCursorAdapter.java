package com.example.ganga.noline;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ganga.noline.data.InventoryContract;

import java.io.ByteArrayInputStream;

/**
 * Created by ganga on 9/30/17.
 */

public class InventoryCursorAdapter extends CursorAdapter {

    private static final String LOG_TAG = InventoryCursorAdapter.class.getName();

    public InventoryCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        View listItem = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return listItem;
    }

    @Override
    public void bindView(final View view, final Context context, final Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView productnameTextView = (TextView) view.findViewById(R.id.name);
        final TextView prodPrice = (TextView) view.findViewById(R.id.price);
        ImageView productImage = (ImageView) view.findViewById(R.id.productImage);
        TextView quant = (TextView) view.findViewById(R.id.quantity);



        // Find the columns of purchase table attributes that we're interested in
        int productnameColumnIndex = cursor.getColumnIndex(InventoryContract.PurchaseEntry.COLUMN_PRODUCT_NAME);
        int prodPriceColumnIndex = cursor.getColumnIndex(InventoryContract.PurchaseEntry.COLUMN_PRICE);
        int productImageColumnIndex = cursor.getColumnIndex(InventoryContract.PurchaseEntry.COLUMN_IMAGE);
        int quantityColumnIndex = cursor.getColumnIndex(InventoryContract.PurchaseEntry.COLUMN_QUANTITY);
        int newPriceColumnIndex = cursor.getColumnIndex(InventoryContract.PurchaseEntry.COLUMN_NEWPRICE);



        // Read the purchase attributes from the Cursor for the current item
        String productName = cursor.getString(productnameColumnIndex);
        final int price = cursor.getInt(prodPriceColumnIndex);
        byte[] image = cursor.getBlob(productImageColumnIndex);
        int quantity = cursor.getInt(quantityColumnIndex);
        String quantText = String.valueOf(quantity);
        final int newPrice = cursor.getInt(newPriceColumnIndex);
        String newPriceText = String.valueOf(newPrice);



        // Update the TextViews with the attributes for the current item
        productnameTextView.setText(productName);
        prodPrice.setText(newPriceText);
        ByteArrayInputStream imageStream = new ByteArrayInputStream(image);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        productImage.setImageBitmap(theImage);
        quant.setText(quantText);

        ImageView popButton = (ImageView) view.findViewById(R.id.popup);
        popButton.setTag(cursor.getPosition());
        popButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final int pos = (Integer) v.getTag();


                PopupMenu popup = new PopupMenu(v.getContext(), v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.listitem_menu, popup.getMenu());
                Log.e(LOG_TAG, "This method is called");
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        Toast.makeText(context, "You Clicked : " + item.getItemId(), Toast.LENGTH_SHORT).show();

                        int i = item.getItemId();
                        if(i==R.id.one) {
                            long itemId = getItemId(pos);
                            ContentValues values = new ContentValues();
                            values.put(InventoryContract.PurchaseEntry.COLUMN_QUANTITY, 1);
                            int adjPrice = 1*price;
                            values.put(InventoryContract.PurchaseEntry.COLUMN_NEWPRICE, adjPrice);
                            Uri mCurrentPetUri = ContentUris.withAppendedId(InventoryContract.PurchaseEntry.CONTENT_URI,itemId);
                            int rowsAffected = context.getContentResolver().update(mCurrentPetUri, values, null, null);
                            Log.e(LOG_TAG, "This method is called" + mCurrentPetUri);
                            return true;
                        } if(i==R.id.two){
                            long itemId = getItemId(pos);
                            ContentValues values = new ContentValues();
                            values.put(InventoryContract.PurchaseEntry.COLUMN_QUANTITY, 2);
                            int adjPrice = 2*price;
                            values.put(InventoryContract.PurchaseEntry.COLUMN_NEWPRICE, adjPrice);
                            Uri mCurrentPetUri = ContentUris.withAppendedId(InventoryContract.PurchaseEntry.CONTENT_URI,itemId);
                            int rowsAffected = context.getContentResolver().update(mCurrentPetUri, values, null, null);
                            Log.e(LOG_TAG, "This method is called" + mCurrentPetUri);
                            return true;
                        }if(i==R.id.three) {
                            long itemId = getItemId(pos);
                            ContentValues values = new ContentValues();
                            values.put(InventoryContract.PurchaseEntry.COLUMN_QUANTITY, 3);
                            int adjPrice = 3*price;
                            values.put(InventoryContract.PurchaseEntry.COLUMN_NEWPRICE, adjPrice);
                            Uri mCurrentPetUri = ContentUris.withAppendedId(InventoryContract.PurchaseEntry.CONTENT_URI,itemId);
                            int rowsAffected = context.getContentResolver().update(mCurrentPetUri, values, null, null);
                            Log.e(LOG_TAG, "This method is called" + mCurrentPetUri);
                            return true;
                        }if(i==R.id.four){
                            long itemId = getItemId(pos);
                            ContentValues values = new ContentValues();
                            values.put(InventoryContract.PurchaseEntry.COLUMN_QUANTITY, 4);
                            int adjPrice = 4*price;
                            values.put(InventoryContract.PurchaseEntry.COLUMN_NEWPRICE, adjPrice);
                            Uri mCurrentPetUri = ContentUris.withAppendedId(InventoryContract.PurchaseEntry.CONTENT_URI,itemId);
                            int rowsAffected = context.getContentResolver().update(mCurrentPetUri, values, null, null);
                            Log.e(LOG_TAG, "This method is called" + mCurrentPetUri);
                            return true;
                        }if(i==R.id.five){
                            long itemId = getItemId(pos);
                            ContentValues values = new ContentValues();
                            values.put(InventoryContract.PurchaseEntry.COLUMN_QUANTITY, 5);
                            int adjPrice = 5*price;
                            values.put(InventoryContract.PurchaseEntry.COLUMN_NEWPRICE, adjPrice);
                            Uri mCurrentPetUri = ContentUris.withAppendedId(InventoryContract.PurchaseEntry.CONTENT_URI,itemId);
                            int rowsAffected = context.getContentResolver().update(mCurrentPetUri, values, null, null);
                            Log.e(LOG_TAG, "This method is called" + mCurrentPetUri);
                            return true;
                        }if(i==R.id.delete) {
                            long itemId = getItemId(pos);
                            Uri mCurrentPetUri = ContentUris.withAppendedId(InventoryContract.PurchaseEntry.CONTENT_URI, itemId);
                            int rowsDeleted = context.getContentResolver().delete(mCurrentPetUri, null, null);
                            Log.e(LOG_TAG, "This method is called" + mCurrentPetUri);
                            return true;
                        }
                        return true;
                    }
                });

                popup.show();

            }

        });


    }


}

