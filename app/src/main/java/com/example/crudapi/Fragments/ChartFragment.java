package com.example.crudapi.Fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.crudapi.ApiClient;
import com.example.crudapi.Interface.ApiInterface;
import com.example.crudapi.Model.Growth;
import com.example.crudapi.Model.Result;
import com.example.crudapi.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Templates;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChartFragment extends Fragment {

private BarChart mbarChart;
private PieChart mpieChart;
private ApiInterface apiInterface;
private ProgressDialog loadingbar;
private String method;

    public ChartFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_chart, container, false);
        mbarChart=view.findViewById(R.id.barchart);
        mpieChart=view.findViewById(R.id.piechart);
        loadingbar=new ProgressDialog(getContext());
         method = getArguments().getString("method");
        getGrowthChart();
        return view;
    }

    private void getGrowthChart() {
        loadingbar.setTitle("please wait");
        loadingbar.setMessage("we are fetching your data");
        loadingbar.show();
apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        Call<Result>call=apiInterface.getChart();
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.body().getError()){
                    loadingbar.dismiss();
                    Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                }
                else if (!response.body().getError()){
                    loadingbar.dismiss();
                    if (method.equals("barchart")){
                        List<BarEntry>barEntries=new ArrayList<>();
                        for (Growth growth : response.body().getGrowths()){
                            barEntries.add(new BarEntry(Integer.valueOf(growth.getYear()),Float.parseFloat(growth.getGrowthrate())));
                        }
                        BarDataSet barDataSet=new BarDataSet(barEntries,"growth");
                        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                        BarData barData=new BarData(barDataSet);
                        barData.setBarWidth(0.9f);
                        mbarChart.setVisibility(View.VISIBLE);
                        mbarChart.animateY(5000);
                        mbarChart.setData(barData);
                        mbarChart.setFitBars(true);

                        Description description=new Description();
                        description.setText("growth rate per year");
                        mbarChart.setDescription(description);
                        mbarChart.invalidate();



                    }
                    else if (method.equals("piechart")){
List<PieEntry>pieEntries=new ArrayList<>();
for (Growth growth:response.body().getGrowths()){
    pieEntries.add(new PieEntry(Float.parseFloat(growth.getGrowthrate()),growth.getYear()));

           }
               mpieChart.setVisibility(View.VISIBLE);
               mpieChart.animateXY(5000,5000);
               PieDataSet pieDataSet=new PieDataSet(pieEntries,"growth per year");
               pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
               PieData pieData=new PieData(pieDataSet);
               mpieChart.setData(pieData);
                        Description description=new Description();
                        description.setText("growth rate per year");
                        mpieChart.setDescription(description);
                        mpieChart.invalidate();



                    }
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
              loadingbar.dismiss();
                Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}