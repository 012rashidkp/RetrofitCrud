package com.example.crudapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.crudapi.Fragments.ChartFragment;
import com.example.crudapi.Fragments.CreateFragment;

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        String method=getIntent().getStringExtra("method");
        Bundle bundle=new Bundle();
        bundle.putString("method",method);
        ChartFragment chartFragment=new ChartFragment();
        chartFragment.setArguments(bundle);
       getSupportFragmentManager().beginTransaction().add(R.id.fragmentcontainer,chartFragment).commit();
        Toast.makeText(this, ""+method, Toast.LENGTH_SHORT).show();
    }
}