package com.example.crudapi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crudapi.Interface.ApiInterface;
import com.example.crudapi.Model.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
private String id;
private TextView Title_txt,Body_txt,Status_txt;
ApiInterface apiInterface;
private ProgressDialog Loadingbar;
private ImageView done_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        id=getIntent().getStringExtra("id");
        Title_txt=(TextView)findViewById(R.id.item_title);
        Body_txt=(TextView)findViewById(R.id.item_body);
        Status_txt=(TextView)findViewById(R.id.item_status);
        done_img=(ImageView)findViewById(R.id.complete_image);
        Loadingbar=new ProgressDialog(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }






        fetchDetails(id);

    }

    private void fetchDetails(String id) {
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
                    Title_txt.setText(title);
                    Body_txt.setText(body);
                    if (status.equals(false)){
                        Status_txt.setText("not completed");
                        Status_txt.setTextColor(Color.parseColor("#cb0162"));
                    }
                    else if (status.equals(true)){
                        Status_txt.setText("completed");
                        Status_txt.setTextColor(Color.parseColor("#4CAF50"));
                        done_img.setVisibility(View.VISIBLE);

                    }

                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
Loadingbar.dismiss();
                Toast.makeText(DetailActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onBackPressed() {
        finish();
    }
}