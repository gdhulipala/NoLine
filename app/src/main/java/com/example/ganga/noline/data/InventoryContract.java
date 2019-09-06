package com.example.ganga.noline.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ganga on 9/30/17.
 */

public class InventoryContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private InventoryContract() {
    }


    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.example.ganga.noline";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.example.android.inventory/inventory/ is a valid path for
     * looking at inventory data. content://com.example.android.inventory/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */
    public static final String PATH_INVENTORY = "inventory";

    public static final String PATH_PURCHASE = "purchase";

    /**
     * Inner class that defines constant values for the inventory database table thats created by the seller.
     * Each entry in the table represents a single inventory.
     */

    public static final class InventoryEntry implements BaseColumns {

        /** The content URI to access the sellers inventory data in the provider */

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_INVENTORY);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of items on sellers table.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORY;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single item on sellers table.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORY;

        /** Name of database table for inventory */

        public final static String TABLE_NAME = "inventory";


        public final static String _ID = BaseColumns._ID;


        public final static String COLUMN_PRODUCT_NAME ="productname";


        public final static String COLUMN_PRICE = "price";


        public final static String COLUMN_IMAGE = "image";


        public final static String COLUMN_QUANTITY = "quantity";


        public final static String COLUMN_CODE = "code";


    }

    /**
     * Inner class that defines constant values for the purchase database table.
     * Each entry in the table represents a single item.
     */

    public static final class PurchaseEntry implements BaseColumns {

        /** The content URI to access the purchase table data in the provider */

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PURCHASE);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of items.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PURCHASE;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single item.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PURCHASE;

        /** Name of database table thats created as soon as the user clicks on the scan button */

        public final static String TABLE_NAME = "purchase";


        //Below is the list of constants related to all the columns in the purchase table


        public final static String _ID = BaseColumns._ID;


        public final static String COLUMN_PRODUCT_NAME ="productname";


        public final static String COLUMN_PRICE = "price";


        public final static String COLUMN_IMAGE = "image";


        public final static String COLUMN_QUANTITY = "quantity";


        public final static String COLUMN_NEWPRICE = "newprice";


    }
}
