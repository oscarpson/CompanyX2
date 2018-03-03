package joslabs.companyx.analysis;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import joslabs.companyx.R;

public class AnalysisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        LineChart chart = (LineChart) findViewById(R.id.chart);

       /* LineChart data = new LineChart(getXAxisValues(), getDataSet());
        chart.setData(data);
        chart.setDescription("My Chart");
        chart.animateXY(2000, 2000);
        chart.invalidate();*/
        chart.animateY(5000);
        chart.invalidate();
        chart.setDescription("Fitness analysis");

        ArrayList<Entry> entries=new ArrayList<>();
        ArrayList<LineDataSet>all=null;
        entries.add(new Entry(4f,0));
        entries.add(new Entry(8f,1));
        entries.add(new Entry(6f,2));
        entries.add(new Entry(2f,3));
        entries.add(new Entry(18f,4));
        entries.add(new Entry(9f,5));

        ArrayList <Entry> entriesb=new ArrayList<>();
        entriesb.add(new Entry(3f,0));
        entriesb.add(new Entry(5f,1));
        entriesb.add(new Entry(4f,2));
        entriesb.add(new Entry(1f,3));
        entriesb.add(new Entry(14f,4));
        entriesb.add(new Entry(6f,5));

        ArrayList <Entry> entriesc=new ArrayList<>();
        entriesc.add(new Entry(6f,0));
        entriesc.add(new Entry(9f,1));
        entriesc.add(new Entry(10f,2));
        entriesc.add(new Entry(11f,3));
        entriesc.add(new Entry(15f,4));
        entriesc.add(new Entry(19f,5));


        LineDataSet dataSet= new LineDataSet(entries,"Products distribution\n");
        LineDataSet dataSetb= new LineDataSet(entriesb,"Cash/pending payments\n");
        LineDataSet dataSetc= new LineDataSet(entriesc,"Expected");
        all=new ArrayList<>();
        all.add(dataSet);
        all.add(dataSetb);
        all.add(dataSetc);
        dataSet.setDrawCubic(true);
        // dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setColor(Color.GREEN);
        dataSetc.setColor(Color.MAGENTA);

        ArrayList <String> labels=new ArrayList<>();
        labels.add("Week1");
        labels.add("Week2");
        labels.add("Week3");
        labels.add("Week4");
        labels.add("Week5");
        labels.add("Week6");

        LineData data= new LineData(labels,all);
        chart.setData(data);

    }
    /* private ArrayList<LineDataSet> getDataSet() {
         ArrayList<LineDataSet> dataSets = null;

         ArrayList<BarEntry> valueSet1 = new ArrayList<>();
         BarEntry v1e1 = new BarEntry(110.000f, 0); // Jan
         valueSet1.add(v1e1);
         BarEntry v1e2 = new BarEntry(40.000f, 1); // Feb
         valueSet1.add(v1e2);
         BarEntry v1e3 = new BarEntry(60.000f, 2); // Mar
         valueSet1.add(v1e3);
         BarEntry v1e4 = new BarEntry(30.000f, 3); // Apr
         valueSet1.add(v1e4);
         BarEntry v1e5 = new BarEntry(90.000f, 4); // May
         valueSet1.add(v1e5);
         BarEntry v1e6 = new BarEntry(100.000f, 5); // Jun
         valueSet1.add(v1e6);

         ArrayList<BarEntry> valueSet2 = new ArrayList<>();
         BarEntry v2e1 = new BarEntry(250.000f, 0); // Jan
         valueSet2.add(v2e1);
         BarEntry v2e2 = new BarEntry(90.000f, 1); // Feb
         valueSet2.add(v2e2);
         BarEntry v2e3 = new BarEntry(220.000f, 2); // Mar
         valueSet2.add(v2e3);
         BarEntry v2e4 = new BarEntry(60.000f, 3); // Apr
         valueSet2.add(v2e4);
         BarEntry v2e5 = new BarEntry(20.000f, 4); // May
         valueSet2.add(v2e5);
         BarEntry v2e6 = new BarEntry(80.000f, 5); // Jun
         valueSet2.add(v2e6);

         LineDataSet barDataSet1 = new LineDataSet(valueSet1);
         barDataSet1.setColor(Color.rgb(0, 155, 0));
         BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Brand 2");
         barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

         dataSets = new ArrayList<>();
         dataSets.add(barDataSet1);
         dataSets.add(barDataSet2);
         return dataSets;
     }

     private ArrayList<String> getXAxisValues() {
         ArrayList<String> xAxis = new ArrayList<>();
         xAxis.add("JAN");
         xAxis.add("FEB");
         xAxis.add("MAR");
         xAxis.add("APR");
         xAxis.add("MAY");
         xAxis.add("JUN");
         return xAxis;
     }*/
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
