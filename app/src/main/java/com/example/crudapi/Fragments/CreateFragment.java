package com.example.crudapi.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.crudapi.ApiClient;
import com.example.crudapi.Interface.ApiInterface;
import com.example.crudapi.Model.Result;
import com.example.crudapi.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CreateFragment extends Fragment {
    private EditText edit_Title,edit_Body;
    private Button SUBMIT_btn;
    private String title,body;
    private ProgressDialog Loadingbar;
    private AlertDialog.Builder builder;


    public CreateFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_create, container, false);
        edit_Title=view.findViewById(R.id.edit_title);
        edit_Body=view.findViewById(R.id.edit_body);
        SUBMIT_btn=view.findViewById(R.id.submit_btn);
        Loadingbar=new ProgressDialog(getContext());
        builder=new AlertDialog.Builder(getContext());
       SUBMIT_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               CreateTask();
           }
       });




        return view;
    }

    private void CreateTask() {

            title= edit_Title.getText().toString().trim();
            body=edit_Body.getText().toString().trim();
            if (title.equals("")||body.equals("")){
                builder.setTitle("someting went wrong");
                builder.setMessage("please complete task upload details");
                displayAlert("error");
            }
            else {
                Loadingbar.setTitle("please wait");
                Loadingbar.setMessage("we are storing your data");
                Loadingbar.show();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ApiClient.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiInterface service = retrofit.create(ApiInterface.class);

                //defining the call
                Call<Result> call = service.createTask(
                        title,body
                );

                call.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if (response.body().getError()){
                            Loadingbar.dismiss();
                            builder.setTitle("server response");
                            builder.setMessage(response.body().getMessage());
                            displayAlert("error");
                        }
                        else if (!response.body().getError()){
                            builder.setTitle("server response");
                            builder.setMessage(response.body().getMessage());
                            displayAlert("error");
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


    }

    private void displayAlert(String error) {
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
Loadingbar.dismiss();
edit_Title.setText("");
edit_Body.setText("");
            }
        });
AlertDialog alertDialog=builder.create();
alertDialog.show();




    }
}