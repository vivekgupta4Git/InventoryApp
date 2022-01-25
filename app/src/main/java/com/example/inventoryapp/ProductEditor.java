package com.example.inventoryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inventoryapp.data.InventoryContract;
import com.example.inventoryapp.data.InventoryContract.InventoryEntry;
import com.example.inventoryapp.data.InventoryDbHelper;

public class ProductEditor extends AppCompatActivity {

    private EditText product_name;
    private EditText product_unit;
    private EditText product_quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_editor);
        RadioButton stockin_radiobutton = findViewById(R.id.stockin_radiobutton);
stockin_radiobutton.performClick();

product_name = findViewById(R.id.product_editText);
product_unit = findViewById(R.id.unit_editText);
product_quantity = findViewById(R.id.quantiy_editText);

        Button saveButton = findViewById(R.id.Save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProduct();
            }
        });
    }

    private void resetEditor() {
        product_name.setText("");
        product_unit.setText("");
        product_quantity.setText("");
    }

    private void saveProduct() {

        String pName = product_name.getText().toString();
        String pUnit = product_unit.getText().toString();
        int pQuanity = Integer.parseInt(product_quantity.getText().toString());

        ContentValues contentValues = new ContentValues();
        contentValues.put(InventoryEntry.COLUMN_PRODUCT_NAME,pName);
        contentValues.put(InventoryEntry.COLUMN_PRODUCT_UNIT,pUnit);
        contentValues.put(InventoryEntry.COLUMN_PRODUCT_QUANTITY,pQuanity);

        Uri newUri = getContentResolver().insert(InventoryEntry.CONTENT_URI,contentValues);

        if(newUri != null)
        {
            Toast.makeText(this,"Sucess",Toast.LENGTH_LONG).show();
            resetEditor();

        }
        else
        {
            Toast.makeText(this,"Failure",Toast.LENGTH_LONG).show();

        }
        displayDatabaseInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
             insertProduct();
            displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void insertProduct() {

        InventoryDbHelper mDBhelper = new InventoryDbHelper(this);
        SQLiteDatabase db = mDBhelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME,"PARLE");
        values.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_UNIT,"CS");
        values.put(InventoryEntry.COLUMN_PRODUCT_QUANTITY,"10");

long rowid =  db.insert(InventoryContract.InventoryEntry.TABLE_NAME,null,values);




        if(rowid > 0 ) {
            Log.v("ProductActivity", "Newly Inserted Row id is " + String.valueOf(rowid));

            Toast.makeText(this, "New Row Inserted " + rowid, Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,"No New Row Inserted !!!!!!",Toast.LENGTH_LONG).show();
        }
    }


    private void displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.


        InventoryDbHelper mDbHelper = new InventoryDbHelper(this);

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Perform this raw SQL query "SELECT * FROM pets"
        // to get a Cursor that contains all rows from the pets table.
            //Cursor cursor = db.rawQuery("SELECT * FROM " + InventoryEntry.TABLE_NAME, null);


        Cursor cursor = db.query(InventoryContract.InventoryEntry.TABLE_NAME,null,null,null,null,null,null);
        try {

            TextView displayView = (TextView) findViewById(R.id.sample);
            displayView.setText("Number of rows in table: " + cursor.getCount());
            displayView.append("\n" + InventoryEntry._ID + "\t\t" +
                    InventoryEntry.COLUMN_PRODUCT_NAME + "\t\t" +
                    InventoryEntry.COLUMN_PRODUCT_UNIT+ "\t\t" +
                    InventoryEntry.COLUMN_PRODUCT_QUANTITY);
            int myProductID = cursor.getColumnIndex(InventoryEntry._ID);
            int myProductName = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_NAME);
            int myProductUnit = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_UNIT);
            int myProductPrice = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_QUANTITY);


            while(cursor.moveToNext())
            {

                displayView.append("\n" + cursor.getInt(myProductID) + "\t\t" +
                        cursor.getString(myProductName) + "\t\t" +
                        cursor.getString(myProductUnit) + "\t\t" +
                        cursor.getInt(myProductPrice) );
            }

        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

}