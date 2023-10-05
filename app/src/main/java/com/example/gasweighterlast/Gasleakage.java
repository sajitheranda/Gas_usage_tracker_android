package com.example.gasweighterlast;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Gasleakage} factory method to
 * create an instance of this fragment.
 */
public class Gasleakage extends Fragment {

    FirebaseDatabase database;
    DatabaseReference gasref;
    Button button_alarm;
    Button buttontest;
    Integer alarm_val;
    boolean isButtonClickable = false;
    private final Object buttonLock = new Object();

    int test_state=0;
    Double val;
    Double current_gas=0.0;
    long current_milli=0;
    Double previous_gas=0.0;
    long previous_milli=0;
    TextView reduceweight;
    TextView leakagetime;
    TextView leakagestatus;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_gasleakage, container, false);
        button_alarm=root.findViewById(R.id.buttonalarm);
        buttontest=root.findViewById(R.id.buttontest);
        reduceweight=root.findViewById(R.id.reduceweight);
        leakagetime=root.findViewById(R.id.leakagetime);
        leakagestatus=root.findViewById(R.id.leakagestatus);


        database = FirebaseDatabase.getInstance();//get firebase connection
        gasref= database.getReference();



        setvalueusingfire();
        button_alarmer();
        button_tester();



        return root;
    }

    private void button_tester() {
        buttontest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(test_state==0){
                    starttest();
                    buttontest.setText("Stop test");
                    buttontest.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor((R.color.red))));
                    test_state=1;
                } else if (test_state==1) {
                    stopstate();
                    buttontest.setText("Clean all");
                    buttontest.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor((R.color.yellow))));
                    test_state=2;
                } else if (test_state==2) {
                    clearall();
                    buttontest.setText("Start test");
                    buttontest.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor((R.color.light_blue))));
                    test_state=0;
                }
            }

        });
    }
    private void starttest() {
        DatabaseReference valuegaspre = gasref.child("gasapp").child("gas");
        valuegaspre.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                val = snapshot.getValue(Double.class);
                if (val != null) {
                    previous_gas = val;
                    previous_milli = System.currentTimeMillis();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error if needed
            }
        });

    }
    private void stopstate() {
        DatabaseReference valuegaspre = gasref.child("gasapp").child("gas");
        valuegaspre.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                val= snapshot.getValue(Double.class);

                if (val != null) {
                    current_gas=val;
                    current_milli=System.currentTimeMillis();
                    //Log.i("previous",previous_gas+"");
                    //Log.i("previous",current_gas+"");
                    Double difference=previous_gas-current_gas;
                    long current_difference=(current_milli-previous_milli)/(60*1000);
                    reduceweight.setText(difference+"kg");
                    leakagetime.setText(current_difference+" minutes");
                    if(difference>0.2){
                        leakagestatus.setText("Gas leakage");
                        leakagestatus.setTextColor(Color.parseColor("#FF3324"));
                    }else{

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error if needed
            }
        });

        ///////////


    }
    private void clearall(){

    }

    private void button_alarmer() {

        DatabaseReference valuegas=gasref.child("gasapp").child("alarm");
        valuegas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                alarm_val = dataSnapshot.getValue(Integer.class);
                if (alarm_val != null) {
                        if (alarm_val == 0) {
                            button_alarm.setText("TURN ON");
                            button_alarm.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor((R.color.light_blue))));

                        } else {
                            button_alarm.setText("TURN OFF");
                            button_alarm.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor((R.color.red))));

                        }

                    }
                else {
                    alarm_val = 0;

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error if any
            }
        });

        DatabaseReference valuealarmer = gasref.child("gasapp").child("alarm");
            button_alarm.setOnClickListener(new View.OnClickListener() {

                @SuppressLint("ResourceType")
                @Override
                public void onClick(View v) {

                    if (alarm_val != null) {
                        if (alarm_val == 0) {
                            button_alarm.setText("TURN OFF");
                            button_alarm.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor((R.color.red))));
                            valuealarmer.setValue(1);
                            alarm_val = 1;
                        } else {
                            button_alarm.setText("TURN ON");
                            button_alarm.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor((R.color.light_blue))));
                            valuealarmer.setValue(0);
                            alarm_val = 0;
                        }
                    } else {
                        alarm_val = 0;
                        valuealarmer.setValue(0);

                    }

                }


            });
        }


    private void setvalueusingfire() {
        DatabaseReference valuealarm=gasref.child("gasapp").child("alarm");
        /*valuealarm.addValueEventListener(new ValueEventListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                alarm_val = snapshot.getValue(Integer.class);
                if (alarm_val != null) {
                    if (alarm_val == 0) {
                        button_alarm.setText("TURN OFF");
                        button_alarm.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor((R.color.red))));
                        alarm_val = 1;
                    } else {
                        button_alarm.setText("TURN ON");
                        button_alarm.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor((R.color.light_blue))));
                        alarm_val = 0;
                    }
                }



                /*
                if(alarm_val != null){
                   if(alarm_val==0){
                       button_alarm.setText("TURN ON");
                       button_alarm.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(Color.parseColor("#3796E2"))));
                   }
                    if(alarm_val==1){
                        button_alarm.setText("TURN OFF");
                        button_alarm.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor((R.color.red))));
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
        /*
        ValueEventListener valueEventListener = new ValueEventListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                alarm_val = snapshot.getValue(Integer.class);

                if (alarm_val != null) {
                    if (alarm_val == 1) {
                        button_alarm.setText("TURN OFF");
                        button_alarm.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor((R.color.red))));
                    } else {
                        button_alarm.setText("TURN ON");
                        button_alarm.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor((R.color.light_blue))));
                    }

                    // Remove the event listener to stop further updates
                    valuealarm.removeEventListener(this);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error if needed
            }
        };

        valuealarm.addValueEventListener(valueEventListener);
        */

        /////////////////

        valuealarm.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                alarm_val = snapshot.getValue(Integer.class);

                if (alarm_val != null) {
                    if (alarm_val == 1) {
                        button_alarm.setText("TURN OFF");
                        button_alarm.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor((R.color.red))));
                    } else {
                        button_alarm.setText("TURN ON");
                        button_alarm.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor((R.color.light_blue))));
                    }

                    // Remove the event listener to stop further updates
                    valuealarm.removeEventListener(this);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error if needed
            }
        });

    }
}