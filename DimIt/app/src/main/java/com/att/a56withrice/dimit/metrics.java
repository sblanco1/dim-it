package com.att.a56withrice.dimit;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
    int timeSpan = 7;
    String[] time;
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

        /*URL url = null;
        HttpURLConnection huc = null;
        try {
            url = new URL(url);
            huc = (HttpURLConnection) url.openConnection();
            try {
                StringBuilder result = new StringBuilder();
                huc.setRequestProperty(api key);
                huc.setRequestMethod("GET");
                InputStream is = huc.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line).append("\n");
                }
                rd.close();
                String finalString = result.toString();
                System.out.println(finalString);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            huc.disconnect();
        }*/

        calculate();

        myChart = (BarChart) findViewById(R.id.chart);
        myChart.setDrawBorders(true);
        myChart.setAutoScaleMinMaxEnabled(true);

        graphValues();

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton selected = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                String text = selected.getText().toString();
                int i;
                switch (text) {
                    case "7 days":
                        timeSpan = 7;
                        i = 1;
                        break;
                    case "14 days":
                        timeSpan = 14;
                        i = 7;
                        break;
                    case "30 days":
                        timeSpan = 30;
                        i = 15;
                        break;
                    default:
                        timeSpan = 7;
                        i = 1;
                        break;
                }
                calculate();
                graphValues();
            }
        });
    }

    public void calculate() {
        dumbCalc = (TextView) findViewById(R.id.dumbCalc);
        Float dumbKWDay = ((timeSpan * kWday) * 10000) / 10000;
        dumbCalc.setText(dumbKWDay.toString());
        smartCalc = (TextView) findViewById(R.id.smartCalc);
        Float smartKWDay = 10f;
        smartCalc.setText(smartKWDay.toString());
        congratsCalc = (TextView) findViewById(R.id.congratsCalc);
        int firstCalc = (int)((kWcost*(dumbKWDay - smartKWDay))*100);
        Float savings = ((float)firstCalc / 100f);
        congratsCalc.setText("$" + savings.toString());
    }

    public void graphValues() {
        smartData = new ArrayList<>();
        dumbData = new ArrayList<>();
        //for (int i = 0; i != (timeSpan + 1); ++i) {
        //for (int i = timeSpan; i >= 0; --i) {
        for (int i = 0; i != (timeSpan + 1); ++i) {
            //String j = dateCalc(sdf, c, today, j);
            //
            BarEntry smartEntry = new BarEntry(i, i);
            smartData.add(smartEntry);
            BarEntry dumbEntry = new BarEntry(i, kWday);
            dumbData.add(dumbEntry);
        }

        /*time = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        Calendar c = Calendar.getInstance();
        Date today = Calendar.getInstance().getTime();
        for (int i = timeSpan; i >= 0; --i) {
            String j = dateCalc(sdf, c, today, i);
            time.add(j);
            System.out.println(j);
        }*/

        smartSet = new BarDataSet(smartData, "Smart bulb");
        dumbSet = new BarDataSet(dumbData, "\"Dumb\" bulb");
        BarData allData = new BarData(smartSet, dumbSet);
        myChart.setData(allData);
        dumbSet.setColor(R.color.colorPrimary);
        float groupSpace = 0.06f;
        float barSpace = 0f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset
        smartSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        dumbSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        /*IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                System.out.println("Value: " + value);
                value *= (-1);
                return time[((int)(value))];
            }
        };*/
        myChart.groupBars(1f, groupSpace, barSpace);
        XAxis timePeriod = myChart.getXAxis();
        timePeriod.setCenterAxisLabels(true);
        timePeriod.setGranularity(1f);
        //timePeriod.setValueFormatter(formatter);
        myChart.setFitBars(true);
        myChart.setScaleEnabled(true);
        myChart.setTouchEnabled(true);
        myChart.setDragEnabled(true);
        System.out.println("What's failing?");
        myChart.notifyDataSetChanged();
        myChart.invalidate();
    }

    public String[] dateCalc(int days)
    {
        time = new String[timeSpan];
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        Calendar c = Calendar.getInstance();
        Date today = Calendar.getInstance().getTime();
        for(int i = 0; i != (timeSpan+1); ++i) {
            System.out.println();
            c.setTime(today);
            c.add(Calendar.DATE, (days));
            time[i] = sdf.format(c.getTime().getTime());
            System.out.println(time[i]);
        }
        return time;
    }
}
