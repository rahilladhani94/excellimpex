package com.excelimpex.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.excelimpex.Activities.ActivityNewOrder;
import com.excelimpex.Model.ProductsRequestModelList;
import com.excelimpex.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class ProductsRequestAdapter extends ArrayAdapter<ProductsRequestModelList> {

    private ArrayList<ProductsRequestModelList> dataSet;
    Context mContext;
    private ImageLoader imageLoader;
    private  ActivityNewOrder activity;
    // View lookup cache
    private static class ViewHolder {
        TextView txtName,txtqty,txtSubtotal,txtdiscount,txtcategory;
        ImageView ivDelete;

    }

    public ProductsRequestAdapter(Context context,ArrayList<ProductsRequestModelList> data, ActivityNewOrder activity) {
        super(context, R.layout.row_requestproducts, data);
        this.dataSet = data;
        this.mContext=context;
        imageLoader = ImageLoader.getInstance();
        this.activity =activity;

    }





    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ProductsRequestModelList dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_requestproducts, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            viewHolder.txtqty = (TextView) convertView.findViewById(R.id.txtqty);
            viewHolder.ivDelete = (ImageView) convertView.findViewById(R.id.ivDelete);
            viewHolder.txtSubtotal = (TextView) convertView.findViewById(R.id.txtSubtotal);
            viewHolder.txtdiscount = (TextView) convertView.findViewById(R.id.txtdiscount);
            viewHolder.txtcategory = (TextView) convertView.findViewById(R.id.txtcategory);



            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
        viewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.deleteProduct(position);
            }
        });

        viewHolder.txtName.setText(dataModel.getName());

        viewHolder.txtcategory.setText(dataModel.getsName());

        viewHolder.txtqty.setText("Qty : "+dataModel.getQty());
        viewHolder.txtdiscount.setText("Discount : "+dataModel.getDiscount() +" %");

        Double temp = Double.valueOf(dataModel.getSubtotal());

        viewHolder.txtSubtotal.setText("Total with Discount : Rs. "+String.format("%.2f",temp ));


        // Return the completed view to render on screen
        return convertView;
    }
}