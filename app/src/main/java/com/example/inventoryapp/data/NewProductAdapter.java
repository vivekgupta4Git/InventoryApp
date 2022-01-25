package com.example.inventoryapp.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.inventoryapp.R;

import java.util.ArrayList;

public class NewProductAdapter extends ArrayAdapter<Product> {
    public NewProductAdapter(@NonNull Context context, ArrayList<Product> list) {
        super(context,0,list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;

        if(currentItemView == null)
        {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.listviewproduct, parent, false);
        }

        Product currentp = getItem(position);
        TextView textView1 = currentItemView.findViewById(R.id.pnameofnewp);
        textView1.setText(currentp.getpName());
        TextView textView2 = currentItemView.findViewById(R.id.pquantityofnewp);
        textView2.setText(String.valueOf(currentp.getpQuanity()));
        TextView textView3 = currentItemView.findViewById(R.id.punitofnewp);
        textView3.setText(currentp.getpUnit());
        Button remove = currentItemView.findViewById(R.id.buttondelete);
        remove.setText("Delete");
        return  currentItemView;
    }
}
