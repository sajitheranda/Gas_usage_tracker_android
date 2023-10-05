package com.example.gasweighterlast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.CircularArray;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Calendar;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    Context context;
    AutoCompleteTextView gastype;
    FirebaseDatabase database;
    DatabaseReference gasref;
    String selectedgastype;
    Integer time_milli;
    Long date_milli;

    ImageView imagetime;
    TextView texttime;

    ImageView imagedate;
    TextView textdate;

    Button submit;



    com.google.android.material.textfield.TextInputLayout dropdown_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        context=this;
        database = FirebaseDatabase.getInstance();//get firebase connection
        gasref= database.getReference();

        setids();
        getgastype();
        setgastype();
        selecttime();
        selectdate();
        submitclick();
        storetokens();









    }

    private void storetokens() {
        ///////////////////////
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String deviceId = task.getResult();
                        //Log.d("Device ID", deviceId);
                        // You can use the device ID for sending notifications or other purposes
                        DatabaseReference tokensRef=gasref.child("gasapp").child("token");

                        tokensRef.orderByValue().equalTo(deviceId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (!dataSnapshot.exists()) {
                                    tokensRef.push().setValue(deviceId)
                                            .addOnSuccessListener(aVoid -> {
                                                Log.d("Token", "Token stored successfully: " + deviceId);
                                            })
                                            .addOnFailureListener(e -> {
                                                Log.e("Token", "Failed to store token: " + deviceId + ", Error: " + e.getMessage());
                                            });
                                } else {
                                    Log.d("Token", "Token already exists: " + deviceId);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.e("Token", "Failed to check token existence: " + databaseError.getMessage());
                            }
                        });
                    } else {
                        Log.e("Device ID", "Failed to get device ID");
                    }
                });
        ////////////////////////

    }

    private void submitclick() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Seconndpage.class);
                startActivity(intent);
            }
        });
    }

    private void getgastype() {
        DatabaseReference valuegastype=gasref.child("gasapp").child("gas_type");
        valuegastype.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                selectedgastype=snapshot.getValue(String.class);
                //Toast.makeText(context,selectedgastype+"***",Toast.LENGTH_LONG).show();
                //Log.d("value ; ",selectedgastype);

                if(selectedgastype != null){
                    dropdown_back.setHint(selectedgastype);
                    dropdown_back.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFFFF")));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void setgastype() {

        String items[]={"buddy","budget","regular"};
        ArrayAdapter gastypeadapter=new ArrayAdapter<String>(context,R.layout.single_item_drop_menu,items);

        gastype.setAdapter(gastypeadapter);

        //Toast.makeText(context,selectedgastype+"***",Toast.LENGTH_SHORT).show();
        //Log.d("value 2",selectedgastype+"**");

//        if(selectedgastype != null & gastypeadapter != null){
//            int position=gastypeadapter.getPosition(selectedgastype);
//            Toast.makeText(context,position,Toast.LENGTH_SHORT).show();
//            gastype.setSelection(position);
//        }


        gastype.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected=parent.getItemAtPosition(position).toString();
                //Toast.makeText(context,"gas type:"+selected,Toast.LENGTH_SHORT).show();

                // Set a value under a specific node
                gasref.child("gasapp").child("gas_type").setValue(selected);
            }
        });
    }


////////////////////////////////////////// time part
    private void selecttime() {
        ////////////// for loading
        getselecttime();
        ////////////// loading part

        imagetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimeZone timeZone = TimeZone.getTimeZone("Asia/Colombo");
                Calendar calendar = Calendar.getInstance(timeZone);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int min = calendar.get(Calendar.MINUTE);




                TimePickerDialog timedialog =new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        texttime.setText(String.format("%02d",hourOfDay)+"."+String.format("%02d",minute));

                        //Calendar calendar = Calendar.getInstance();
                        //calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        //calendar.set(Calendar.MINUTE, minute);
                        //calendar.set(Calendar.SECOND, 0);
                        //calendar.set(Calendar.MILLISECOND, 0);

                        // Get the time in milliseconds
                        time_milli = hourOfDay*(60*60*1000)+minute*(60*1000);

                        saveselecttime();
                    }
                }, hour, min, true);
                timedialog.show();

            }
        });
    }

    private void saveselecttime() {
        DatabaseReference valuetime=gasref.child("gasapp").child("time");
        valuetime.setValue(time_milli);

    }
    private void getselecttime(){
        DatabaseReference valuetime=gasref.child("gasapp").child("time");
        valuetime.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                time_milli = snapshot.getValue(Integer.class);
                //Toast.makeText(context,selectedgastype+"***",Toast.LENGTH_LONG).show();
                //Log.d("value ; ",selectedgastype);
                if(time_milli != null){
                    //Calendar calendar = Calendar.getInstance();
                    //calendar.setTimeInMillis(time_milli);
                    int hourOfDay= time_milli/(60*60*1000);
                    int minute=(time_milli%(60*60*1000))/(60*1000);
                    texttime.setText(String.format("%02d",hourOfDay)+"."+String.format("%02d",minute));

                    Log.d("time",time_milli+"");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

///////////////////////////////////////////////////////////
    private void selectdate() {
        // load date
        getselectdate();
        /////// select date

        imagedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeZone timeZone = TimeZone.getTimeZone("Asia/Colombo");
                Calendar calendar = Calendar.getInstance(timeZone);
                int y = calendar.get(Calendar.YEAR);
                int m = calendar.get(Calendar.MONTH);
                int d = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datedialog=new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {



                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        textdate.setText(year+"-"+String.format("%02d",month+1)+"-"+String.format("%02d",dayOfMonth));

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, dayOfMonth, 0, 0, 0);
                        calendar.set(Calendar.MILLISECOND, 0);

                        date_milli = calendar.getTimeInMillis();

                        saveselectdate();
                    }
                    }, y, m, d);

            datedialog.show();
            }
         });
    }

    private void saveselectdate() {
        DatabaseReference valuedate=gasref.child("gasapp").child("date");
        valuedate.setValue(date_milli);
    }

    private void getselectdate(){
        DatabaseReference valuedate=gasref.child("gasapp").child("date");
        valuedate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                date_milli = snapshot.getValue(Long.class);
                //Toast.makeText(context,selectedgastype+"***",Toast.LENGTH_LONG).show();
                //Log.d("value ; ",selectedgastype);
                if(date_milli != null){
                    Calendar  calendar=Calendar.getInstance();
                    calendar.setTimeInMillis(date_milli);
                    int year=calendar.get(Calendar.YEAR);
                    int month=calendar.get(Calendar.MONTH);
                    int day=calendar.get(Calendar.DAY_OF_MONTH);
                    textdate.setText(year+"-"+String.format("%02d",month+1)+"-"+String.format("%02d",day));

                    Log.d("date",date_milli+"");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


////////////////////////////////////////




    private void setids() {
        gastype=findViewById(R.id.auto_complete_size);
        dropdown_back=findViewById(R.id.dropdown_back);
        imagetime= (ImageView) findViewById(R.id.imagetime);
        texttime=(TextView) findViewById(R.id.texttime);
        imagedate=(ImageView)findViewById(R.id.imagedate);
        textdate=(TextView)findViewById(R.id.textdate);
        submit=(Button)findViewById(R.id.submit);
    }


}