package com.excelimpex.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.excelimpex.Model.Distributor;
import com.excelimpex.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by abc on 30-11-2016.
 */
public class Temp extends BaseAdapter {

    Context context;
    ArrayList<Distributor> data = new ArrayList<>();
    View row;
    LayoutInflater inflater;




    private HashMap<Integer, View> my_view_map = new HashMap<Integer, View>();


    public Temp(Context context, ArrayList<Distributor> allDonwload) {


        this.context = context;
        data = allDonwload;

    }


    @Override
    public int getCount() {

        return data.size();
    }

    @Override
    public Object getItem(int position) {

        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder
    {
        TextView txt_teamname,txt_leageuame;
        ImageView team_logo;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        final Holder holder=new Holder();
        View rowView;

        if (convertView == null){

            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_distributor, null);


        }
        holder.txt_teamname = (TextView) convertView.findViewById(R.id.txtName);



        holder.txt_teamname.setText(data.get(position).getName());




        return convertView;
    }





}
