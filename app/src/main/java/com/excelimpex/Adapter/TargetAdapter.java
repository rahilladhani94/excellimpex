package com.excelimpex.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.excelimpex.Model.CategoryList;
import com.excelimpex.Model.Targetlist;
import com.excelimpex.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class TargetAdapter extends BaseAdapter {

    Context context;
    private ImageLoader imageLoader;
    LayoutInflater inflater;
    List<CategoryList> productListResponses;
    List<Targetlist> targetList;

    public TargetAdapter(Context context, List<Targetlist> targetList, List<CategoryList> productListResponses) {


        this.context = context;

        this.targetList = targetList;
        this.productListResponses= productListResponses;

        imageLoader = ImageLoader.getInstance();
    }


    @Override
    public int getCount() {

        return targetList.size();
    }

    @Override
    public Object getItem(int position) {

        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        TextView txtName,txtQty,txtTarget,txtMonth;
        ImageView ivProuct;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        final Holder holder = new Holder();
        View rowView;

        if (convertView == null) {

            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_target, null);



            holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            holder.txtQty = (TextView) convertView.findViewById(R.id.txtQty);

            holder.txtTarget = (TextView) convertView.findViewById(R.id.txtTarget);
            holder.txtMonth = (TextView) convertView.findViewById(R.id.txtMonth);

            if (convertView!=null){

                holder.txtQty.setText("Qty : "+targetList.get(position).getSQty());
                holder.txtTarget.setText("Target Rs. : "+targetList.get(position).getSTarget());
                holder.txtMonth.setText("Month : "+targetList.get(position).getSMonth());




                if(targetList !=null && productListResponses !=null )
             {
            int index = 0;
            boolean found = false;
            for(int i = 0;i<productListResponses.size();i++)
            {
            if(!found)
            {
                if(productListResponses.get(i).getNCatId().equalsIgnoreCase(targetList.get(position).getnCatId()))
                {
                    index = i; found = true;
                    Log.e("in",""+i);

                    String productname = productListResponses.get(i).getSName();
                    holder.txtName .setText(productname);


                }
                else{

                }
            }
            }

        }




            }




        }


        return convertView;
    }


}