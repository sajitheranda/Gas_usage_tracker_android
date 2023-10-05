package com.example.gasweighterlast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EditNumber extends AppCompatActivity {

    Button update;
    TextView title,number;
    Intent intent;
    String nameTitle,namenumber;
    int id;

    DBhandler dBhandler;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_number);

        context=this;

        update=(Button)findViewById(R.id.updatebutton);
        title=(TextView) findViewById(R.id.updatetitle);
        number=(TextView)findViewById(R.id.updatedescription);
        dBhandler=new DBhandler(this);

        intent=getIntent();
        Bundle bundle1=intent.getBundleExtra("buddle1");
        id=bundle1.getInt("id");


        getfromdatabase();
        setdetails();
        updatedetails();


    }

    private void getfromdatabase() {
        //Toast.makeText(context,id+" ",Toast.LENGTH_LONG).show();
        TODO todo=dBhandler.getonetodo(id);
        nameTitle=todo.getTitle();
        namenumber=todo.getNumber();

    }

    private void updatedetails() {
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatetitle=title.getText().toString();
                String updatedescription=number.getText().toString();
                dBhandler.updatenewtodo(id,updatetitle,updatedescription);
                //Toast.makeText(context,updatetitle+updatedescription,Toast.LENGTH_LONG).show();
                startActivity(new Intent(context,Phonenumbers.class));
            }
        });
    }

    private void setdetails() {
        title.setText(nameTitle);
        number.setText(namenumber);
    }
}