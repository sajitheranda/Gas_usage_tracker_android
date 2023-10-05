package com.example.gasweighterlast;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link percentage} factory method to
 * create an instance of this fragment.
 */
public class percentage extends Fragment {


    FirebaseDatabase database;
    DatabaseReference gasref;
    ProgressBar progressBar;
    TextView progresstext;
    Float gas_value;
    Integer percentagegas;

    TextView textweight;
    TextView textdays;

    HashMap<String,Double> onlygas;
    HashMap<String,Double> emptytank;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_percentage, container, false);

        database = FirebaseDatabase.getInstance();//get firebase connection
        gasref= database.getReference();


        progressBar=rootView.findViewById(R.id.progressbar);
        progresstext=rootView.findViewById(R.id.progresstext);
        textweight=rootView.findViewById(R.id.textweight);
        textdays=rootView.findViewById(R.id.textdays);

        setgas();

        //gas_value=70;

        //progressBar.setProgress(gas_value);
        //progresstext.setText(gas_value+"%");

        changeprogress();





        return rootView;
    }

    private void setgas() {
        onlygas=new HashMap<>();
        //"buddy","budget","regular"
        onlygas.put("buddy",10.0);
        onlygas.put("budget",10.0);
        onlygas.put("regular",12.5);

        emptytank=new HashMap<>();
        emptytank.put("buddy",1.0);// change that values
        emptytank.put("budget",4.5);
        emptytank.put("regular",13.5);
    }

    private void changeprogress() {
        DatabaseReference valuegas=gasref.child("gasapp");
        valuegas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("gas_type")&& snapshot.hasChild("gas")){
                    Double empty=emptytank.get(snapshot.child("gas_type").getValue(String.class));
                    Double total_net=onlygas.get(snapshot.child("gas_type").getValue(String.class));
                    Double current=snapshot.child("gas").getValue(Double.class);
                    Long date=snapshot.child("date").getValue(Long.class);
                    Long time=snapshot.child("time").getValue(Long.class);

                    if(empty !=null && total_net !=null && current !=null){
                        Double net= current-empty;
                        int percentage = (int) ((double) net / total_net * 100);
                        if(percentage>=0 && percentage<=100){
                            progressBar.setProgress(percentage);
                            progresstext.setText(percentage+"%");
                            ///////////////////
                            DecimalFormat decimalFormat = new DecimalFormat("#.##");
                            double roundednet = Double.parseDouble(decimalFormat.format(net));
                            //////////////////
                            textweight.setText(roundednet+"kg");
                            if(date!=null && time!=null){
                                changedays(date,time,net,total_net);
                            }


                        }else{
                            progressBar.setProgress(0);
                            progresstext.setText("Invalid data");
                            textweight.setText("Invalid data");
                            textdays.setText("Invalid data");
                        }


                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void changedays(long date,long time,double net,double total) {
//        TimeZone timeZone = TimeZone.getTimeZone("Asia/Colombo");
//        Calendar calendar = Calendar.getInstance(timeZone);
//        long currentTimeMillis = calendar.getTimeInMillis();

        ZoneId sriLankaTimeZone = null;
        LocalDateTime currentTime = null;
        long currentTimeMillis =0;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            sriLankaTimeZone = ZoneId.of("Asia/Colombo");
            currentTime = LocalDateTime.now(sriLankaTimeZone);
            Instant instant = currentTime.atZone(sriLankaTimeZone).toInstant();
            currentTimeMillis = instant.toEpochMilli();
        }


        Log.d("daysmill",currentTimeMillis+"");

        long previous=date+time;

        long milidays= (3600 * 1000 * 24);
        long milihours= (3600 * 1000 );

        double next_time = (previous+((currentTimeMillis - previous) / (total - net)) * total );
        long next=(long) next_time;
        long days =  (next -currentTimeMillis)/ milidays;
        long hours =((next -currentTimeMillis)% milidays)/ milihours;

        Log.d("days",previous +"");
        if(days>=0){
            textdays.setText(days+" days "+hours+" hours");
        }

    }


}