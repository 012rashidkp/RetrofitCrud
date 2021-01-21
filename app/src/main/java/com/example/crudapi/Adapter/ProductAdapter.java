package com.example.crudapi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.crudapi.Model.Datas;
import com.example.crudapi.Model.Products;
import com.example.crudapi.R;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private Context context;
    private List<Products> items=new ArrayList<>();

    public ProductAdapter(Context context, List<Products> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.products_card,parent,false);

        return new ProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         Products products=items.get(position);
         holder.prod_title.setText(products.getName());
         holder.prod_desc.setText(products.getDesc());
         if (products.getAvailable().equals(false)){
             holder.prod_avail.setText("not available");
             holder.prod_avail.setTextColor(context.getResources().getColor(R.color.deep_pink));
         }
         else if (products.getAvailable().equals(true)){
             holder.prod_avail.setText("available");
             holder.prod_avail.setTextColor(context.getResources().getColor(R.color.green));

         }
        if (products!=null){
            Glide.with(context).load("http://192.168.1.101:8000/covers/"+products.getName()+"/"+products.getImage().trim()).into(holder.prod_img);
        }
        else {

            Glide.with(context)
                    .load(R.drawable.ic_launcher_background)
                    .into(holder.prod_img);


        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView prod_title,prod_desc,prod_avail;
        private ImageView prod_img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            prod_title=itemView.findViewById(R.id.producttitle);
            prod_desc=itemView.findViewById(R.id.productdesc);
            prod_avail=itemView.findViewById(R.id.productavailability);
            prod_img=itemView.findViewById(R.id.productimage);

        }
    }
}
