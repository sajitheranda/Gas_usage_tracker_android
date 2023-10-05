package com.example.gasweighterlast;

import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class Taskadapter extends FragmentPagerAdapter {
    ArrayList<Fragment>  fragementarraylist=new ArrayList<>();
    ArrayList<String> titlearraylist=new ArrayList<>();

    public Taskadapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }


    @Override
    public int getCount() {
        return fragementarraylist.size();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragementarraylist.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titlearraylist.get(position);
    }

    public void Addfragment(Fragment fragment,String title){
        fragementarraylist.add(fragment);
        titlearraylist.add(title);
    }



}
