package com.example.crudapi.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.crudapi.Adapter.ItemsAdapter;
import com.example.crudapi.ApiClient;
import com.example.crudapi.Interface.ApiInterface;
import com.example.crudapi.Model.Datas;
import com.example.crudapi.Model.Result;
import com.example.crudapi.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListFragment extends Fragment {
private RecyclerView recyclerView;
private ItemsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Datas> items=new ArrayList<>();
    private ApiInterface apiInterface;
    private ProgressDialog Loadingbar;
    private AlertDialog.Builder builder;
    public ListFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_list, container, false);
       recyclerView=view.findViewById(R.id.list_items);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        Loadingbar=new ProgressDialog(getContext());
        builder=new AlertDialog.Builder(getContext());
        fetchTask();
        return view;
    }

    private void fetchTask() {
Loadingbar.setTitle("please wait");
Loadingbar.setMessage("we are fetching your data");
Loadingbar.show();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Result> call = apiInterface.getdatas();

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.body().getError()){
                    Loadingbar.dismiss();
                    builder.setTitle("server response");
                    builder.setMessage("something went wrong");
                    displayAlert("error");
                }
                else if (!response.body().getError()){
                    Loadingbar.dismiss();
                    items=response.body().getDatas();
                 adapter=new ItemsAdapter(getContext(),items);
                 recyclerView.setAdapter(adapter);
                 adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
             Loadingbar.dismiss();
             builder.setTitle("server response");
             builder.setMessage(t.getMessage());
             displayAlert("error");
            }
        });





    }

    private void displayAlert(String error) {
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Loadingbar.dismiss();
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();


    }
}