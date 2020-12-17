package com.example.crudapi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.crudapi.Fragments.CreateFragment;
import com.example.crudapi.Fragments.ListFragment;
import com.example.crudapi.Fragments.SearchFragment;
import com.example.crudapi.Interface.ApiInterface;

public class MainActivity extends AppCompatActivity {
private TextView CreateTask,searchbtn,product_btn;
private ApiInterface apiInterface;
private TinyDB tinyDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CreateTask=(TextView)findViewById(R.id.create_task);
        searchbtn=(TextView)findViewById(R.id.search_select);
        product_btn=(TextView)findViewById(R.id.prod_txt);
        tinyDB=new TinyDB(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
        product_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ProductsActivity.class);
                startActivity(intent);
            }
        });





        if (findViewById(R.id.fragment_container)!=null){
            if (savedInstanceState!=null){
                return;
            }

                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new CreateFragment()).commit();


CreateTask.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new ListFragment()).commit();


    }
});
searchbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new SearchFragment()).commit();

    }
});

        }


    }
}