package com.att.a56withrice.dimit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class metrics extends AppCompatActivity {

    BarChart myChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metrics);

        myChart = (BarChart) findViewById(R.id.chart); // this is just making the graph
        myChart.setDrawBorders(true);
        myChart.setAutoScaleMinMaxEnabled(true);
        ArrayList<BarEntry> smartData = new ArrayList<>();
        ArrayList<BarEntry> dumbData = new ArrayList<>();
        for(int i = 1; i != 11; ++i) {
            smartData.add(new BarEntry((float)i, i)); //y axis
            dumbData.add(new BarEntry((float)(i-1), i));
        }

        BarDataSet smartSet = new BarDataSet(smartData, "Smart bulb");
        smartSet.setColor(R.color.colorAccent);
        BarDataSet dumbSet = new BarDataSet(dumbData, "\"Dumb\" bulb");
        dumbSet.setColor(R.color.colorPrimary);
        BarData allData = new BarData(smartSet, dumbSet);
        float groupSpace = 0.06f;
        float barSpace = 0f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset
        XAxis timePeriod = myChart.getXAxis();
        timePeriod.setCenterAxisLabels(true);
        myChart.setData(allData);
        myChart.groupBars(1f, groupSpace, barSpace);
        myChart.setFitBars(true);

        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int timeSpan;
                int i;
                switch (position) {
                    case 0: timeSpan = 7;
                        i = 1;
                        break;
                    case 1: timeSpan = 14;
                        i = 7;
                        break;
                    case 2: timeSpan = 30;
                        i = 15;
                        break;
                    default: timeSpan = 7;
                        i = 1;
                        break;
                }

                ArrayList<String> time = new ArrayList<>();
                for(; i != timeSpan; ++i) {
                    time.add(String.valueOf(i)); //x axis
                }
                myChart.notifyDataSetChanged();
                myChart.invalidate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
