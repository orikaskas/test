package com.example.test;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class productFragment extends Fragment {
    MyDatabaseHelper myDatabaseHelper;
    EditText name,price;
    Button button;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_product, container, false);
        name=view.findViewById(R.id.name);
        price=view.findViewById(R.id.price);
        button=view.findViewById(R.id.btn);
        myDatabaseHelper= new MyDatabaseHelper(requireActivity());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                androidx.fragment.app.ListFragment f= new androidx.fragment.app.ListFragment();
                f.setArguments(b);
                if(name.length()>0 && price.length()>0){
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.listfragment,f).commit();
                    myDatabaseHelper.addproduct(name.getText().toString(), Integer.parseInt(price.getText().toString()));
                }

            }
        });
        return view;
    }
}