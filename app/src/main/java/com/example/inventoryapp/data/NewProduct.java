package com.example.inventoryapp.data;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.inventoryapp.R;

import java.util.ArrayList;

public class NewProduct extends AppCompatActivity {

    private ArrayList<Product> arrayList;
    private NewProductAdapter newProductAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);
        final Button add = findViewById(R.id.addButton);


        arrayList = new ArrayList<Product>();
        Product sample = new Product("Goodday",10,"C/s");
        arrayList.add(sample);
        newProductAdapter = new NewProductAdapter(this,arrayList);
         ListView listView = findViewById(R.id.listofProducts);
         listView.setAdapter(newProductAdapter);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText addnewproduct = findViewById(R.id.editorProductName);
                EditText addquanityproduct = findViewById(R.id.editorProductQuanity);


                String pn = addnewproduct.getText().toString();
                Integer q = Integer.parseInt(addquanityproduct.getText().toString());
                String u = "c/s";



                Product addproduct = new Product(pn,q,u);
                arrayList.add(addproduct);

                newProductAdapter.notifyDataSetChanged();


            }
        });

    }
}