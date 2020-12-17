package com.example.crudapi.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.crudapi.Adapter.SearchItemAdapter;
import com.example.crudapi.ApiClient;
import com.example.crudapi.Interface.ApiInterface;
import com.example.crudapi.Model.Result;
import com.example.crudapi.Model.SearchItems;
import com.example.crudapi.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment {
private ListView listView;
private EditText Searchedit;
private List<SearchItems>searchItems=new ArrayList<>();
private SearchItemAdapter adapter;
private String search;
private ApiInterface apiInterface;
    public SearchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_search, container, false);
       listView=view.findViewById(R.id.listview);
       Searchedit=view.findViewById(R.id.edit_search);
       Searchedit.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               searchThings(search);
           }

           @Override
           public void afterTextChanged(Editable editable) {

           }
       });

        return view;
    }

    private void searchThings(final String search) {
        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        Call<Result>call=apiInterface.getsearchitems(search);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()){
                    searchItems=response.body().getSearchItems();
                    adapter = new SearchItemAdapter(getContext(),1,searchItems);
                  listView.setAdapter(adapter);
                  adapter.setNotifyOnChange(true);


                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });




    }
}