package com.excelimpex.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.excelimpex.Activities.ActivityExpanseLDetail;
import com.excelimpex.Activities.ActivityProductList;
import com.excelimpex.Model.Distributor;
import com.excelimpex.Model.Expanses;
import com.excelimpex.Model.Productlist;
import com.excelimpex.R;
import com.excelimpex.Utils.UIUtil;
import com.excelimpex.application.ExcelImpex;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class ExpansesAdapter extends BaseAdapter {

    Context context;
    private ImageLoader imageLoader;
    LayoutInflater inflater;
    List<Expanses> expansesListResponses;


    public ExpansesAdapter(Context context, List<Expanses> expansesListResponses) {


        this.context = context;
        this.expansesListResponses = expansesListResponses;
        imageLoader = ImageLoader.getInstance();
    }


    @Override
    public int getCount() {

        return expansesListResponses.size();
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
        TextView txtCost,txtMessage,txtDate;
        ImageView ivProuct;
        RelativeLayout rlmain;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        final Holder holder = new Holder();
        View rowView;

        if (convertView == null) {

            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_expanses, null);



            holder.txtCost = (TextView) convertView.findViewById(R.id.txtCost);

            holder.txtMessage = (TextView) convertView.findViewById(R.id.txtMessage);
            holder.txtDate = (TextView) convertView.findViewById(R.id.txtDate);

            holder.rlmain= convertView.findViewById(R.id.rlmain);

            if (convertView!=null){
                holder.txtCost .setText("Rs. "+expansesListResponses.get(position).getSTotal());

                if(expansesListResponses.get(position).getDDate() !=null){
                    String date ="";
                    date = UIUtil.convertDateTime("yyyy-MM-dd HH:mm:ss", "MM/dd/yyyy hh:mm a", expansesListResponses.get(position).getDDate());

                    holder.txtDate.setText(date);

                }

                holder.rlmain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(context, ActivityExpanseLDetail.class);
                        if(expansesListResponses.get(position).getSXerox() !=null){
                            i.putExtra("SXerox",""+expansesListResponses.get(position).getSXerox());
                        }
                        else {
                            i.putExtra("SXerox","");

                        }
                        if(expansesListResponses.get(position).getSStationery() !=null){
                            i.putExtra("SStationery",""+expansesListResponses.get(position).getSStationery());
                        }
                        else {
                            i.putExtra("SStationery","");

                        }
                        if(expansesListResponses.get(position).getSTravelPlace() !=null){
                            i.putExtra("getSTravelPlace",""+expansesListResponses.get(position).getSTravelPlace());
                        }
                        else {
                            i.putExtra("getSTravelPlace","");

                        }
                        if(expansesListResponses.get(position).getSTravelFrom() !=null){
                            i.putExtra("getSTravelFrom",""+expansesListResponses.get(position).getSTravelFrom());
                        }
                        else {
                            i.putExtra("getSTravelFrom","");

                        }
                        if(expansesListResponses.get(position).getSTravelTo()!=null){
                            i.putExtra("getSTravelTo",""+expansesListResponses.get(position).getSTravelTo());
                        }
                        else {
                            i.putExtra("getSTravelTo","");

                        }
                        if(expansesListResponses.get(position).getSVisitType()!=null){
                            i.putExtra("getSVisitType",""+expansesListResponses.get(position).getSVisitType());
                        }
                        else {
                            i.putExtra("getSVisitType","");

                        }
                        if(expansesListResponses.get(position).getSDailyAllowance()!=null){
                            i.putExtra("getSDailyAllowance",""+expansesListResponses.get(position).getSDailyAllowance());
                        }
                        else {
                            i.putExtra("getSDailyAllowance","");

                        }
                        if(expansesListResponses.get(position).getSFare()!=null){
                            i.putExtra("getSFare",""+expansesListResponses.get(position).getSFare());
                        }
                        else {
                            i.putExtra("getSFare","");

                        }
                        if(expansesListResponses.get(position).getSFareImage()!=null){
                            i.putExtra("getSFareImage",""+expansesListResponses.get(position).getSFareImage());
                        }
                        else {
                            i.putExtra("getSFareImage","");

                        }
                        if(expansesListResponses.get(position).getSTotal()!=null){
                            i.putExtra("getSTotal",""+expansesListResponses.get(position).getSTotal());
                        }
                        else {
                            i.putExtra("getSTotal","");

                        }

                        if(expansesListResponses.get(position).getSXeroxImage()!=null){
                            i.putExtra("getSXeroxImage",""+expansesListResponses.get(position).getSXeroxImage());
                        }
                        else {
                            i.putExtra("getSXeroxImage","");

                        }
                        if(expansesListResponses.get(position).getSXeroxImage()!=null){
                            i.putExtra("getSStationeryImage",""+expansesListResponses.get(position).getSStationeryImage());
                        }
                        else {
                            i.putExtra("getSStationeryImage","");

                        }



                        context.startActivity(i);
//                        final Dialog dialog = new Dialog(context);
//                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                        // Include dialog.xml file
//                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                        dialog.setContentView(R.layout.image_detail);
//
//
//                        dialog.show();
//
//
//                        TextView btn_ok = (TextView) dialog.findViewById(R.id.btn_ok);
//                        ImageView ivimage = (ImageView) dialog.findViewById(R.id.ivimage);
//                        imageLoader.displayImage(expansesListResponses.get(position).getSImage() ,ivimage, ExcelImpex.getProductImageDisplayOption(context));
//
//                        btn_ok.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog.dismiss();
//                            }
//                        });

                    }
                });

            }




        }


        return convertView;
    }

}