package com.example.gasweighterlast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class Phonenumbers extends AppCompatActivity {

    private static final int CALL_PHONE_PERMISSION_REQUEST_CODE = 100 ;
    Button button;
    ListView listView;
    Context context;
    DBhandler dBhandler;
    List<TODO> todos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonenumbers);

        button=(Button) findViewById(R.id.button_addnumber);
        listView=(ListView) findViewById(R.id.listview);
        dBhandler=new DBhandler(this);

        context=this;
        todos=dBhandler.getalltodos();


        setlist();
        addbutton();
        listitemselect();
    }

    private void setlist() {
        Todoadapter adapter=new Todoadapter(context,R.layout.singleview,todos);
        listView.setAdapter(adapter);
    }




    private void addbutton() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,AddNumber.class);
                startActivity(intent);
            }
        });
    }

    private void listitemselect() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TODO todo = todos.get(position);

                // Inflate the custom layout for the dialog
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.alert_dialog, null);

                // Find the views in the custom layout
                TextView titleTextView = dialogView.findViewById(R.id.titleTextView);
                TextView numberTextView = dialogView.findViewById(R.id.numberTextView);

                Button callButton = dialogView.findViewById(R.id.callButton);
                Button updateButton = dialogView.findViewById(R.id.updateButton);
                Button deleteButton = dialogView.findViewById(R.id.deleteButton);





                // Set the title and number from your todo object
                titleTextView.setText("Person: " + todo.getTitle());
                numberTextView.setText("Number: " + todo.getNumber());




                // Create the AlertDialog with the custom layout
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                AlertDialog alertDialog = builder.create();
//                alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.border_round);
                builder.setView(dialogView);

                AlertDialog alertDialog = builder.create();


                callButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Here you can place the code to make the call.
                        // Remember to handle the CALL_PHONE permission as shown in the previous response.
                        //Toast.makeText(context, "You clicked the Call button", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                        makePhoneCall(todo.getNumber()); // Replace with the actual phone number
                    }
                });

                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, EditNumber.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", todo.getId());
                        bundle.putString("title", todo.getTitle());
                        bundle.putString("descrption", todo.getNumber());
                        intent.putExtra("buddle1", bundle);
                        startActivity(intent);

                    }
                });

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dBhandler.deletetodo(todo.getId());
                        startActivity(new Intent(context, Phonenumbers.class));

                    }
                });



                // Show the dialog

                alertDialog.show();


            }
        });
    }

    private void makePhoneCall(String phoneNumber) {
        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
        dialIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(dialIntent);
    }
}