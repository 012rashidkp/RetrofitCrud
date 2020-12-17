package com.example.crudapi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.appcompat.widget.AppCompatTextView;

import com.example.crudapi.Model.SearchItems;
import com.example.crudapi.R;

import java.util.ArrayList;
import java.util.List;

public class SearchItemAdapter extends ArrayAdapter<SearchItems> {

    private Context context;
    private int layoutResourceId;
    private List<SearchItems> arrayList=new ArrayList<>();

    public SearchItemAdapter(Context context, int layoutResourceId, List<SearchItems> arrayList) {
        super(context, layoutResourceId, arrayList);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.arrayList = arrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchitems, parent, false);
            }

            SearchItems model = arrayList.get(position);

            AppCompatTextView itemName = convertView.findViewById(R.id.searchitem);
//            tvUserName.setText(model.getFullname());
            itemName.setText(model.getTitle());
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }

    public String getItemNameAtPosition(int position) {
        return arrayList.get(position).getTitle();
    }

    public int getItemIDAtPosition(int position) {
        return arrayList.get(position).getId();
    }
}

