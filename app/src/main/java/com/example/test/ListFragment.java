package com.example.test;
import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment  {
    MyDatabaseHelper myDatabaseHelper1;
    TableLayout t;
    Button buttonUpdate;
    Cursor cursor;
    private Button buttonDelete;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_list, container, false);
        myDatabaseHelper1 = new MyDatabaseHelper(requireActivity());
        cursor = myDatabaseHelper1.readAllData();
        t=view.findViewById(R.id.Teblelayout);
        int n =cursor.getCount();
        cursor.moveToFirst();
        for (int i = 0; i < n; i++) {
            String s = cursor.getString(1);
            int p = cursor.getInt(2);
            addTableRow(s,p, Integer.parseInt(cursor.getString(0)));
            cursor.moveToNext();
        }

        return view;
    }

    private void addTableRow(String name, int price, int i)
    {
        TableRow exist = t.findViewWithTag(i);
        if(exist != null)
        {
            TextView tvName = exist.findViewWithTag("name");
            TextView tvPrice = exist.findViewWithTag("price");
            if (name.length() > 10) {
                tvName.setText(DownRow(name));
            } else {
                tvName.setText(name);
            }
            tvPrice.setText(String.valueOf(price));

        }
        else{
            TableRow row = new TableRow(requireActivity());
            row.setTag(i);
            TableRow.LayoutParams rowp = new TableRow.LayoutParams(-2, -2);
            row.setLayoutParams(rowp);
            row.setBackgroundColor(Color.WHITE);

            TextView tvName = new TextView(requireActivity());
            tvName.setTag("name");
            TableRow.LayoutParams ll = new TableRow.LayoutParams(-2, -2);
            ll.setMargins(0, 1, 0, 0);
            tvName.setLayoutParams(ll);

            if (name.length() > 10) {
                tvName.setText(DownRow(name));
            }
            else
            {
                tvName.setText(name);
            }
            tvName.setTextColor(Color.BLACK);
            tvName.setTextSize(15);
            tvName.setGravity(Gravity.CENTER);

            TextView tvPrice = new TextView(requireActivity());
            tvPrice.setTag("price");
            tvPrice.setLayoutParams(ll);
            tvPrice.setTextColor(Color.BLACK);
            tvPrice.setTextSize(15);
            tvPrice.setGravity(Gravity.CENTER);
            tvPrice.setText("" + price);

            buttonUpdate =new Button(requireActivity());
            buttonUpdate.setId(i);
            buttonUpdate.setLayoutParams(ll);
            buttonUpdate.setText("עדכון");

            buttonDelete = new Button(requireActivity());
            buttonDelete.setId(i);
            buttonDelete.setLayoutParams(ll);
            buttonDelete.setText("מחיקה");


            buttonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UpdateDialog(name,price,i);
                }
            });
            row.addView(tvName);
            row.addView(tvPrice);
            row.addView(buttonUpdate);
            row.addView(buttonDelete);
            t.addView(row);
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myDatabaseHelper1.deleteAllData();

                }
            });
        }
    }

    private void UpdateDialog(String name, int price,int i) {
        Dialog dialog=new Dialog(requireActivity());
        dialog.setContentView(R.layout.dialog);
        EditText upname;
        upname = dialog.findViewById(R.id.etupName);
        Button buttonUpdate1 = dialog.findViewById(R.id.btnUpdate);
        Button buttonclose = dialog.findViewById(R.id.btnClose);
        EditText upprice ;
        upprice = dialog.findViewById(R.id.etupPrice);
        if(upname.length() ==0&&upprice.length()==0){
            Toast.makeText(requireActivity(), "enter name or price", Toast.LENGTH_SHORT).show();
            return;
        }
        buttonUpdate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s=upname.getText().toString();
                int p=Integer.parseInt(upprice.getText().toString());
                myDatabaseHelper1.updateData(String.valueOf(i),s,p);
                addTableRow(s,p,i);
                dialog.dismiss();
            }
        });
        buttonclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.show();
    }


    public String DownRow(String name)
    {
        int index = 0;
        String str = "";
        for (int i = 0; i < name.length(); i++)
        {
            index++;
            str += name.charAt(i);
            if (index >= 10)
            {
                str += "\n";
                index = 0;
            }
        }
        return str;
    }
    public void onDestroy() {
        super.onDestroy();
    }
}


