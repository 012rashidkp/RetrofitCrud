package com.example.crudapi.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudapi.ApiClient;
import com.example.crudapi.DetailActivity;
import com.example.crudapi.Interface.ApiInterface;
import com.example.crudapi.MainActivity;
import com.example.crudapi.Model.Datas;
import com.example.crudapi.Model.Result;
import com.example.crudapi.R;
import com.example.crudapi.UpdateActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {
    private Context mCtx;
    private List<Datas> items=new ArrayList<>();
    private ApiInterface apiInterface;
    private ProgressDialog Loadingbar;

    public ItemsAdapter(Context mCtx, List<Datas> items) {
        this.mCtx = mCtx;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.data_list,parent,false);

        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
final Datas datas= items.get(position);

holder.Title.setText(datas.getTitle());
holder.Body.setText(datas.getBody());

if (datas.getCompleted().equals(true)){
    holder.Status.setText("completed");
    holder.Status.setTextColor(mCtx.getResources().getColor(R.color.green));
}
else if (datas.getCompleted().equals(false)){
    holder.Status.setText("not completed");
    holder.Status.setTextColor(mCtx.getResources().getColor(R.color.deep_pink));

}
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(mCtx, DetailActivity.class);
       intent.putExtra("id",String.valueOf(datas.getId()));
        mCtx.startActivity(intent);
    }
});
Loadingbar=new ProgressDialog(mCtx);
holder.Delete_Btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Loadingbar.setTitle("please wait");
        Loadingbar.setMessage("we are deleting your item");
        Loadingbar.show();
        apiInterface=ApiClient.getApiClient().create(ApiInterface.class);
        Call<Result>call=apiInterface.getdelete(String.valueOf(datas.getId()));
call.enqueue(new Callback<Result>() {
    @Override
    public void onResponse(Call<Result> call, Response<Result> response) {
        if (response.body().getError()){
            Loadingbar.dismiss();
            Toast.makeText(mCtx, ""+response.body().getMessage(), Toast.LENGTH_LONG).show();
        }
        else if (!response.body().getError()){
            Loadingbar.dismiss();
            Toast.makeText(mCtx, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
            Intent intent1=new Intent(mCtx, MainActivity.class);
            mCtx.startActivity(intent1);
            ((Activity)mCtx).finish();

        }
    }

    @Override
    public void onFailure(Call<Result> call, Throwable t) {
       Loadingbar.dismiss();
        Toast.makeText(mCtx, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
    }
});


    }
});

holder.Update_btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(mCtx, UpdateActivity.class);
        intent.putExtra("id",String.valueOf(datas.getId()));
        mCtx.startActivity(intent);
    }
});




    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
private TextView Title,Body,Status;
private Button Delete_Btn,Update_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
Title=itemView.findViewById(R.id.title);
Body=itemView.findViewById(R.id.body);
Status=itemView.findViewById(R.id.status);
Delete_Btn=itemView.findViewById(R.id.delete_btn);
Update_btn=itemView.findViewById(R.id.update_btn);

        }
    }
}
