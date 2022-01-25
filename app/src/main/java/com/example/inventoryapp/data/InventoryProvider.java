package com.example.inventoryapp.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.CursorAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.example.inventoryapp.data.InventoryContract.*;

public class InventoryProvider extends ContentProvider {

    public static final String LOG_TAG = InventoryProvider.class.getSimpleName();
    private InventoryDbHelper myInventoryDbHelperObject;
    private static final int INVENTORY = 100;
    private static final int INVENTORY_ID = 101;
    private static final UriMatcher myUriMatcherObject = new UriMatcher(UriMatcher.NO_MATCH);


    static
    {
        myUriMatcherObject.addURI(InventoryEntry.CONTENT_AUTHORITY, InventoryEntry.PATH_INVENTORY,INVENTORY);
        myUriMatcherObject.addURI(InventoryEntry.CONTENT_AUTHORITY, InventoryEntry.PATH_INVENTORY + "/#",INVENTORY_ID);

    }

    @Override
    public boolean onCreate() {

        myInventoryDbHelperObject = new InventoryDbHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = myInventoryDbHelperObject.getReadableDatabase();
        Cursor c;

        int match = myUriMatcherObject.match(uri);
        switch (match)
        {
            case INVENTORY:
             c =   db.query(InventoryEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,null,null);
                break;
            case INVENTORY_ID:
                selection = InventoryEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri))};
                c = db.query(InventoryEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,null,null);
                break;
            default:
                throw new IllegalStateException("Unexpected Value = " + uri);
        }

        c.setNotificationUri(getContext().getContentResolver(),uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        
        int match = myUriMatcherObject.match(uri);
        
        switch (match)
        {
            case INVENTORY:
     return    insertInventoryData(uri,values);
            default:
                throw new IllegalStateException("Unexpected uri = "+ uri);
        }

    }

    private Uri insertInventoryData(Uri uri, ContentValues values) {
long id = -1;
        try {
            //data validation
            String ProductName = values.getAsString(InventoryEntry.COLUMN_PRODUCT_NAME);

            if (ProductName.trim() == null || ProductName.isEmpty()) {
                throw new IllegalArgumentException("Product Name cannot be null");
            }

            String UnitName = values.getAsString(InventoryEntry.COLUMN_PRODUCT_UNIT);

            if (UnitName.isEmpty() || UnitName.trim() == null) {
                throw new IllegalArgumentException("Unit Name cannot be null");

            }

            Integer ProductQuantity = values.getAsInteger(InventoryEntry.COLUMN_PRODUCT_QUANTITY);

            if (ProductQuantity == null || ProductQuantity <= 0) {
                throw new IllegalArgumentException("Price cannot be null or negative");
            }

            SQLiteDatabase db = myInventoryDbHelperObject.getWritableDatabase();
             id = db.insert(InventoryEntry.TABLE_NAME, null, values);

            getContext().getContentResolver().notifyChange(uri, null);
        }
        catch (IllegalArgumentException e)
        {
            Log.e(LOG_TAG,e.toString());
        }
if(id == -1)
    return  null;

        return ContentUris.withAppendedId(uri,id);


    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
