package com.example.gasweighterlast;

import android.content.Entity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Gasusage} factory method to
 * create an instance of this fragment.
 */

//// Mpandroidchart
public class Gasusage extends Fragment {

    LineChart linechart;
    FirebaseDatabase database;
    DatabaseReference gasref;
    ArrayList<Entry> arrayList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_gasusage, container, false);

        ///initialize id
        linechart=root.findViewById(R.id.lineChart);
        database = FirebaseDatabase.getInstance();//get firebase connection
        gasref= database.getReference();
        getdatafire();



        return root;
    }

    private void getdatafire() {
        DatabaseReference gastime=gasref.child("gasapp").child("gastime");
        gastime.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList=new ArrayList<>();
                for(DataSnapshot shot:snapshot.getChildren()){
                    long time=shot.child("time").getValue(Long.class);
                    int value=shot.child("value").getValue(Integer.class);
                    arrayList.add(new Entry(time,value));
                }
                chartshow(arrayList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void chartshow(ArrayList<Entry> arrayList) {
        LineDataSet dataSet=new LineDataSet(arrayList,"Gas dataset");
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);// to get curve chart

        /////////////////
        dataSet.setColor(Color.parseColor("#2498F4"));
        dataSet.setValueTextColor(Color.parseColor("#FFEB3B"));
        dataSet.setValueTextSize(10f);
        //////////////////

        changedatasetdetails();


        ArrayList<ILineDataSet> arraydata=new ArrayList<>();
        arraydata.add(dataSet);


        //arraydata
        LineData data=new LineData(arraydata);
        linechart.setData(data);



        ///////////////////// description
        Description description=new Description();
        description.setText("Gas amount");
        description.setTextColor(Color.WHITE);
        description.setTextSize(20);
        linechart.setDescription(description);
        linechart.setBackgroundColor(Color.rgb(38,71,250));

        ////////////// when no gas details
        linechart.setNoDataText("No Gas details");
        linechart.setNoDataTextColor(Color.WHITE);
        //////////////grid
        linechart.setGridBackgroundColor(Color.BLACK);
        ////////////////
        setupLineChart();
        ////////////////
        linechart.invalidate();
    }

    private void changedatasetdetails() {
        Legend legend=linechart.getLegend();
        legend.setTextColor(Color.parseColor("#FFEB3B"));
        legend.setTextSize(16f);
    }

    private ArrayList<Entry> datavalue1(){
        ArrayList<Entry> arrayList=new ArrayList<>();
        arrayList.add(new Entry(10,5));
        arrayList.add(new Entry(12,15));
        arrayList.add(new Entry(15,9));
        arrayList.add(new Entry(16,8));
        return arrayList;
    }

    private void setupLineChart() {
        // Set the name of the Y-axis as "Quantity"
        YAxis yAxis = linechart.getAxisLeft();
        yAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return value + "kg";
            }
        });
        yAxis.setTextColor(Color.WHITE); // Set the desired text color
        yAxis.setTextSize(12f);

        // Set the name of the X-axis as "Day"
        XAxis xAxis = linechart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            private final SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, HH");//"MMM dd, HH:mm"
            @Override
            public String getFormattedValue(float value) {
//                int time = (int) value ;
//                return sdf.format(new Date(time));
                //return "Day " + day;

                long index = (long) value;
                Log.d("val",index+"");
                return sdf.format(new Date(index*1000))+"h";
//                if (index >= 0 && index < arrayList.size()) {
//                    Entry entry = arrayList.get(index);
//                    long time = (long) entry.getX(); // Assuming "time" is stored as the x-value of Entry
//                    return sdf.format(new Date(time));
//                }
//                return "";
            }
        });
        xAxis.setTextColor(Color.WHITE); // Set the desired text color
        xAxis.setTextSize(12f);

        // Other line chart configuration
    }

}