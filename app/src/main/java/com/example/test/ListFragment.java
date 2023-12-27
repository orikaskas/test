package com.example.test;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ListFragment extends Fragment {
    MyDatabaseHelper myDatabaseHelper1;
    TableLayout t;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_list, container, false);
        myDatabaseHelper1 = new MyDatabaseHelper(getActivity());
        t=view.findViewById(R.id.Teblelayout);
        Cursor cursor = myDatabaseHelper1.readAllData();
        cursor.moveToFirst();
        Bundle bundle = getArguments();
        if(bundle != null){
            int n = cursor.getCount();
            for (int i = 0; i < n; i++) {
                String name = cursor.getString(1);
                int price = cursor.getInt(2);
                createTableRow(name,price);
                cursor.moveToNext();
            }
        }
        return view;
    }
    public void createTableRow(String name,int price){
        TextView name1 = new TextView(getActivity());
        name1.setText(name);
        TextView price1 = new TextView(getActivity());
        price1.setText(price);
        TableRow tableRow = new TableRow(getActivity());
        tableRow.setGravity(Gravity.CENTER);
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(-2,100);
        TableRow.LayoutParams layoutParams1 = new TableRow.LayoutParams(-2,50);
        tableRow.setLayoutParams(layoutParams);
        name1.setLayoutParams(layoutParams1);
        price1.setLayoutParams(layoutParams1);
        name1.setGravity(Gravity.CENTER);
        price1.setGravity(Gravity.CENTER);
        tableRow.addView(price1);
        tableRow.addView(name1);
        t.addView(tableRow);
    }
    public void onDestroy() {
        super.onDestroy();
    }
}


