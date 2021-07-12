package com.example.finanzapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.finanzapp.Categories.Category;
import com.example.finanzapp.Categories.CategoryType;
import com.example.finanzapp.Helpers.ChartType;
import com.example.finanzapp.database.Database;
import com.example.finanzapp.database.EntryContainer;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private PieChart chart;
    private PieChart chart2;
    private PieChart chart3;
    public MainFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        Typeface tf =  ResourcesCompat.getFont(getContext(), R.font.courier_prime);
        chart.setData(generatePieData(tf,ChartType.EXPENSE));
        chart2.setData(generatePieData(tf,ChartType.REVENUE));
        chart3.setData(generatePieData(tf,ChartType.EXP_REV_RATIO));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        Button btn = view.findViewById(R.id.btn_expense);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotToEntryActivity(true);
            }
        });
        btn = view.findViewById(R.id.btn_revenue);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotToEntryActivity(false);
            }
        });

        chart = view.findViewById(R.id.pieChart);
        chart2 = view.findViewById(R.id.pieChart2);
        chart3 = view.findViewById(R.id.pieChart3);
        CreateChart(chart,ChartType.EXPENSE);
        CreateChart(chart2,ChartType.REVENUE);
        CreateChart(chart3,ChartType.EXP_REV_RATIO);
        return view;
    }

    /**
     * Builds the chart based on charttype
     * @param chart the chartView that should be builded
     * @param type the type of chart it should be
     */
    private void CreateChart(PieChart chart,ChartType type){
        Typeface tf =  ResourcesCompat.getFont(getContext(), R.font.courier_prime);

        chart.setCenterTextTypeface(tf);
        chart.setCenterText(generateCenterText(type));
        chart.setCenterTextSize(8f);
        chart.setCenterTextTypeface(tf);
        chart.getLegend().setEnabled(false);
        chart.getDescription().setText("");
        // radius of the center hole in percent of maximum radius
        chart.setHoleRadius(50f);
        chart.setTransparentCircleRadius(55f);
        chart.setEntryLabelColor(Color.LTGRAY);
        if(type==ChartType.REVENUE)
            chart.setEntryLabelColor(Color.DKGRAY);

        chart.setEntryLabelTextSize(12f);
        chart.setData(generatePieData(tf,type));
    }

    /**
     * Changes the Activity to AddEntryActivity via Intent
     *
     * @author Hartmann.A
     * */
    public void GotToEntryActivity(boolean isExpense){
        Intent myIntent = new Intent(getContext(),AddEntryActivity.class);
        myIntent.putExtra("isExpense",isExpense);
        startActivity(myIntent);
    }

    /**
     * generates the center text based on chartType
     * @param type specifies the chartType
     * @return a spinnable String with content based on chartType
     */
    private SpannableString generateCenterText(ChartType type) {
        SpannableString s=null;
        switch (type){
            case EXPENSE:
                s = new SpannableString("Ausgaben \n Juni 2021");
                break;
            case REVENUE:
                s = new SpannableString("Einnhamen \n Juni 2021");
                break;
            case EXP_REV_RATIO:
                s = new SpannableString("Verhältniss \n Juni 2021");
                break;
        }
        s.setSpan(new RelativeSizeSpan(2f), 0, 11, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 11, s.length(), 0);
        return s;
    }

    /**
     * generates the Data that is displayed in the chart based on chartType
     * @param tf the Typeface needed for font
     * @param type the tpye of Chart the data is generated for
     * @return
     */
    protected PieData generatePieData(Typeface tf, ChartType type) {
        ArrayList<PieEntry> entries1 = new ArrayList<>();
        EntryContainer container =  Database.GetEntryContainer();
        PieDataSet ds1=null;
        switch (type){
            case EXPENSE:
                Map<Category,Double> expEntries = container.GetExpenseValues();
                for (Map.Entry<Category,Double> entry: expEntries.entrySet())
                {
                    entries1.add(new PieEntry((float) entry.getValue().doubleValue(), entry.getKey().GetName()));
                }
                ds1 = new PieDataSet(entries1, "Ausgaben");
                ds1.setColors(ColorTemplate.COLORFUL_COLORS);
                break;
            case REVENUE:
                Map<Category,Double> revEntries = container.GetRevenueValues();
                for (Map.Entry<Category,Double> entry: revEntries.entrySet())
                {
                    entries1.add(new PieEntry((float) entry.getValue().doubleValue(), entry.getKey().GetName()));
                }
                ds1 = new PieDataSet(entries1, "Einnahmen");
                ds1.setColors(ColorTemplate.MATERIAL_COLORS);
                break;
            case EXP_REV_RATIO:
                Map<CategoryType,Double> ratioEntries = container.GetExpRevRatio();
                for (Map.Entry<CategoryType,Double> entry: ratioEntries.entrySet())
                {
                    String name = "Ausgaben";
                    if(entry.getKey().toString()=="REVENUE")
                        name = "Einnahmen";
                    entries1.add(new PieEntry((float) entry.getValue().doubleValue(), name));
                }
                ds1 = new PieDataSet(entries1, "Verhältniss");
                ds1.setColors(ColorTemplate.PASTEL_COLORS);
                break;
        }


        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.LTGRAY);
        if(type==ChartType.REVENUE)
            ds1.setValueTextColor(Color.DKGRAY);
        ds1.setValueTextSize(12f);

        PieData d = new PieData(ds1);
        d.setValueTypeface(tf);

        return d;


    }
}
