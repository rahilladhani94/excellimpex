package com.excelimpex.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.excelimpex.Activities.ActivityNewOrder;
import com.excelimpex.Model.Productlist;
import com.excelimpex.Model.ProductsRequestModelList;
import com.excelimpex.R;
import com.excelimpex.application.ExcelImpex;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class ProductsRequestAdapterNew extends BaseAdapter {

    Context context;
    private ImageLoader imageLoader;
    LayoutInflater inflater;
    List<ProductsRequestModelList> productListRequest;
    ActivityNewOrder activity;


    public ProductsRequestAdapterNew(Context context, List<ProductsRequestModelList> productListRequest,ActivityNewOrder activity) {


        this.context = context;
        this.productListRequest = productListRequest;
        this.activity = activity;
        imageLoader = ImageLoader.getInstance();
    }


    @Override
    public int getCount() {

        return productListRequest.size();
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
        TextView txtName,txtqty;
        ImageView ivDelete;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        final Holder holder = new Holder();
        View rowView;

        if (convertView == null) {

            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_requestproducts, null);



            holder.txtName = (TextView) convertView.findViewById(R.id.txtName);

            holder.txtqty = (TextView) convertView.findViewById(R.id.txtqty);
            holder.ivDelete = (ImageView) convertView.findViewById(R.id.ivDelete);

            if (convertView!=null){
                holder.txtName .setText(productListRequest.get(position).getName());

                holder.txtqty .setText("Qty : "+productListRequest.get(position).getQty());


            }
            holder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    activity.deleteProduct(position);
                }
            });
            }


        return convertView;
    }


}