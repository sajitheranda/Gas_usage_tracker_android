package com.example.gasweighterlast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Todoadapter extends ArrayAdapter<TODO>  {
    Context context;
    int  resource;
    List<TODO> todos;

    public Todoadapter(@NonNull Context context, int resource, List<TODO> todos) {
        super(context, resource,todos);
        this.context=context;
        this.resource=resource;
        this.todos=todos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.singleview,parent,false);


        TextView texttitle=row.findViewById(R.id.text_title);
        TextView textdescription=row.findViewById(R.id.text_number);


        texttitle.setText(todos.get(position).getTitle());
        textdescription.setText(todos.get(position).getNumber());


        return row;
    }
}
