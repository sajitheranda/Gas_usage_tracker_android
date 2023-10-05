package com.example.gasweighterlast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class Seconndpage extends AppCompatActivity {


    TabLayout tabLayout;
    androidx.viewpager.widget.ViewPager viewPager;
    Context context;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seconndpage);
        context=this;

        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewpager);


        tabLayout.setupWithViewPager(viewPager);

        Taskadapter taskadapter=new Taskadapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        taskadapter.Addfragment(new percentage(),"Percentage");
        taskadapter.Addfragment(new Gasusage(),"Usage");
        taskadapter.Addfragment(new Gasleakage(),"Leakage");


        viewPager.setAdapter(taskadapter);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ///for action bar
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.call:
                // Add code here to perform the desired action when the "Call" button is clicked
                // For example, you could start a phone call intent
                // Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:PHONE_NUMBER"));
                // startActivity(intent);
                //Toast.makeText(context, "second page", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context,Phonenumbers.class);
                startActivity(intent);

                return true;
            // Add more cases for other menu items if needed
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}