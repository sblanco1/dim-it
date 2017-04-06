package com.att.a56withrice.dimit;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class metrics extends AppCompatActivity {

    BarChart myChart;
    ArrayList<BarEntry> smartData;
    BarDataSet smartSet;
    ArrayList<BarEntry> dumbData;
    BarDataSet dumbSet;
    //Spinner spinner;
    ArrayList<String> time;
    int timeSpan = 7;
    RadioGroup radioGroup;
    TextView congratsCalc;
    TextView smartCalc;
    TextView dumbCalc;
    Float kWday = 29.64f;
    Float kWcost = 0.12f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metrics);

        myChart = (BarChart) findViewById(R.id.chart);
        myChart.setDrawBorders(true);
        myChart.setAutoScaleMinMaxEnabled(true);

        smartData = new ArrayList<>();
        dumbData = new ArrayList<>();

        for(int i = 0; i != (timeSpan+1); ++i) {
            BarEntry smartEntry = new BarEntry((float)i, i); //y axis
            smartData.add(smartEntry);
            //System.out.println(smartEntry);
            BarEntry dumbEntry = new BarEntry(kWday, i);
            dumbData.add(dumbEntry);
            //System.out.println(dumbEntry);
        }

        time = new ArrayList<String>();
        for(int i = 0; i != timeSpan; ++i) {
            time.add(String.valueOf(i));
        }
        smartSet = new BarDataSet(smartData, "Smart bulb");
        dumbSet = new BarDataSet(dumbData, "\"Dumb\" bulb");
        BarData allData = new BarData(smartSet, dumbSet);
        myChart.setData(allData);
        dumbSet.setColor(R.color.colorPrimary);
        float groupSpace = 0.06f;
        float barSpace = 0f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset
        XAxis timePeriod = myChart.getXAxis();
        timePeriod.setCenterAxisLabels(true);
        myChart.groupBars(1f, groupSpace, barSpace);
        myChart.setFitBars(true);
        myChart.setScaleEnabled(true);
        myChart.setTouchEnabled(true);
        myChart.setDragEnabled(true);
        myChart.notifyDataSetChanged();
        myChart.invalidate();

        dumbCalc = (TextView) findViewById(R.id.dumbCalc);
        Float dumbKWDay = ((timeSpan * kWday)*10000)/10000;
        dumbCalc.setText(dumbKWDay.toString());
        smartCalc = (TextView) findViewById(R.id.smartCalc);
        Float smartKWDay = 10f;
        smartCalc.setText(smartKWDay.toString());
        congratsCalc = (TextView) findViewById(R.id.congratsCalc);
        Float savings = (((0.12f * (dumbKWDay - smartKWDay))*1000)/1000);
        congratsCalc.setText("$" + savings.toString());

        /*radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int i;
                switch (checkedId) {
                    case 2131492962: timeSpan = 7;
                        i = 1;
                        break;
                    case 2131492963: timeSpan = 14;
                        i = 7;
                        break;
                    case 2131492964: timeSpan = 30;
                        i = 15;
                        break;
                    default: timeSpan = 7;
                        i = 1;
                        break;
                }
                System.out.println(checkedId);
                System.out.println(timeSpan);
            }
        });*/

        // Sets appearance


        /*spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
                Date d = new Date();
                String date = sdf.format(d);
                System.out.println(date);



                for(; i != timeSpan; ++i) {
                     //x axis
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
    }
}
