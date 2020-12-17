package com.example.crudapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.crudapi.Adapter.ItemsAdapter;
import com.example.crudapi.Adapter.ProductAdapter;
import com.example.crudapi.Interface.ApiInterface;
import com.example.crudapi.Model.Datas;
import com.example.crudapi.Model.Products;
import com.example.crudapi.Model.Result;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Products> products=new ArrayList<>();
    private ApiInterface apiInterface;
    private ProgressDialog Loadingbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        recyclerView=(RecyclerView)findViewById(R.id.prod_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        Loadingbar=new ProgressDialog(this);

        getProducts();

    }

    private void getProducts() {
        Loadingbar.setTitle("please wait");
        Loadingbar.setMessage("we are fetching your data");
apiInterface=ApiClient.getApiClient().create(ApiInterface.class);
        Call<Result>call=apiInterface.getproducts();
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.body().getError()){
                    Toast.makeText(ProductsActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
                else if (!response.body().getError()){
                    products=response.body().getProducts();
                    adapter=new ProductAdapter(ProductsActivity.this,products);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });



    }
}