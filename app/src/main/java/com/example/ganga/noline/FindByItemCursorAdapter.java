package com.example.ganga.noline;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ganga.noline.data.InventoryContract;

import java.io.ByteArrayInputStream;

/**
 * Created by ganga on 10/12/17.
 */

public class FindByItemCursorAdapter extends CursorAdapter {


    private static final String LOG_TAG = FindByItemCursorAdapter.class.getName();

    public FindByItemCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.listitem_payactivity, parent, false);
    }

    /**
     * This method binds the pet data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current pet can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {



            // Find individual views that we want to modify in the list item layout
            TextView productnameTextView = (TextView) view.findViewById(R.id.name);
            final TextView prodPrice = (TextView) view.findViewById(R.id.price);
            ImageView productImage = (ImageView) view.findViewById(R.id.productImage);


                // Find the columns of purchase table attributes that we're interested in
                int productnameColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME);
                int prodPriceColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_PRICE);
                int productImageColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_IMAGE);



                // Read the purchase attributes from the Cursor for the current item
                String productName = cursor.getString(productnameColumnIndex);
                int price = cursor.getInt(prodPriceColumnIndex);
                byte[] image = cursor.getBlob(productImageColumnIndex);
                String priceText = String.valueOf(price);


                // Update the TextViews with the attributes for the current item
                productnameTextView.setText(productName);
                prodPrice.setText(priceText);
                ByteArrayInputStream imageStream = new ByteArrayInputStream(image);
                Bitmap theImage = BitmapFactory.decodeStream(imageStream);
                productImage.setImageBitmap(theImage);


    }

}


