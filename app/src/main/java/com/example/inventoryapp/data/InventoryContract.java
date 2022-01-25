package com.example.inventoryapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

public final class InventoryContract  {

    public abstract static class InventoryEntry implements BaseColumns{

        public final static String TABLE_NAME = "Inventory";
        public final static String _ID = BaseColumns._ID;
        public  final static String COLUMN_PRODUCT_NAME = "name";
        public final static String COLUMN_PRODUCT_UNIT = "unit";
        public static final String COLUMN_PRODUCT_QUANTITY = "quantity";
    //    public final static String COLUMN_PRODUCT_EXPIRY_DATE= "expirydate";
     //   public final static int COLUMN_PRODUCT_ACTIVE = 1;
      //  public final static int COLUMN_PRODCUT_NOT_ACTIVE = 0;

        public static final String CONTENT_AUTHORITY = "com.example.inventoryapp";
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
        public static final String PATH_INVENTORY = "Inventory";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_INVENTORY);



    }
}
