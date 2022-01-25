package com.example.inventoryapp.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.inventoryapp.data.InventoryContract.InventoryEntry;

public class InventoryDbHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "balaji.db";
    public static final int DATABASE_VERSION = 1;

    //CREATE TABLE INVENTORY ( _ID INTEGER PRIMARY KEY AUTOINCREMENT, PRODUCT_NAME TEXT NOT NULL, PRODUCT_UNIT TEXT, PRODUCT_PRICE INTEGER NOT NULL);
    private static final  String SQL_CREATE_INVENTORY_TABLE =  "CREATE TABLE " + InventoryEntry.TABLE_NAME + " ("
            + InventoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + InventoryEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, "
            + InventoryEntry.COLUMN_PRODUCT_UNIT + " TEXT, "
            + InventoryEntry.COLUMN_PRODUCT_QUANTITY + " INTEGER NOT NULL);";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + InventoryEntry.TABLE_NAME ;


    public InventoryDbHelper(@Nullable Context context){

        super(context,DATABASE_NAME , null,DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_INVENTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
