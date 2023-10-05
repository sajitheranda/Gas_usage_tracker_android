package com.example.gasweighterlast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddNumber extends AppCompatActivity {

    Button addbutton;
    TextView title,number;
    DBhandler dBhandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_number);


        addbutton=(Button)findViewById(R.id.addingbutton);
        title=(TextView)findViewById(R.id.addtitle);
        number=(TextView)findViewById(R.id.adddescriptipn);
        dBhandler=new DBhandler(this);

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Title=title.getText().toString();
                String Number=number.getText().toString();

                TODO todo=new TODO(Title,Number);
                dBhandler.Addtodo(todo);
                ////////////////////// if there is any change
                startActivity(new Intent(getApplicationContext(),Phonenumbers.class));

            }
        });
    }
}