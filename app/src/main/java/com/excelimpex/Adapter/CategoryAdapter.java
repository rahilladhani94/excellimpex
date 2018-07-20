package com.excelimpex.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.excelimpex.Model.CategoryList;
import com.excelimpex.Model.Productlist;
import com.excelimpex.R;
import com.excelimpex.application.ExcelImpex;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {

    Context context;
    private ImageLoader imageLoader;
    LayoutInflater inflater;
    List<CategoryList> productListResponses;


    public CategoryAdapter(Context context, List<CategoryList> productListResponses) {


        this.context = context;
        this.productListResponses = productListResponses;
        imageLoader = ImageLoader.getInstance();
    }


    @Override
    public int getCount() {

        return productListResponses.size();
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
        TextView txtName,txtPrice;
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
            convertView = inflater.inflate(R.layout.row_category, null);



            holder.txtName = (TextView) convertView.findViewById(R.id.txtName);



            if (convertView!=null){
          holder.txtName .setText(productListResponses.get(position).getSName());


            }




        }


        return convertView;
    }


}