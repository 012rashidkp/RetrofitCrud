package com.example.crudapi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crudapi.Interface.ApiInterface;
import com.example.crudapi.Model.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateActivity extends AppCompatActivity {
    private EditText edit_Title,edit_Body;
    private Button UPDATE_btn;
    private String title,body;
    private ProgressDialog Loadingbar;
    private AlertDialog.Builder builder;
    private String id;
    private ApiInterface apiInterface;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        edit_Title=(EditText) findViewById(R.id.edit_title);
        edit_Body=(EditText)findViewById(R.id.edit_body);
        UPDATE_btn=(Button)findViewById(R.id.update_task);
        Loadingbar=new ProgressDialog(this);
        builder=new AlertDialog.Builder(this);
        id=getIntent().getStringExtra("id");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
        fetchDetails(id);


    }

    private void fetchDetails(final String id) {

        Loadingbar.setTitle("please wait");
        Loadingbar.setMessage("we are fetching your ");
        Loadingbar.show();
        apiInterface=ApiClient.getApiClient().create(ApiInterface.class);
        Call<Result> call = apiInterface.getdetails(id);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.body().getError()){
                    Loadingbar.dismiss();
                }
                else if (!response.body().getError()){
                    Loadingbar.dismiss();
                    String title=response.body().getDetails().getTitle();
                    String body=response.body().getDetails().getBody();
                    Boolean status=response.body().getDetails().getCompleted();
                    edit_Title.setText(title);
                    edit_Body.setText(body);


                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Loadingbar.dismiss();
                Toast.makeText(UpdateActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        UPDATE_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              updateData();
            }
        });


    }
    private void updateData(){
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
            Call<Result> call = service.updateTask(
                    id, title,body
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
                Intent intent=new Intent(UpdateActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }




    }