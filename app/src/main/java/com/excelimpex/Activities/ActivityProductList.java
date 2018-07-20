package com.excelimpex.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.excelimpex.Adapter.ProductsAdapter;
import com.excelimpex.Model.Productlist;
import com.excelimpex.Model.ProductlistMain;
import com.excelimpex.R;
import com.excelimpex.Utils.UIUtil;
import com.excelimpex.WebServices.ApiHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Android on 3/17/2018.
 */

public class ActivityProductList extends AppCompatActivity {

    ImageView ivBack;
    ListView lvDisributor;
    ProductsAdapter adapter;
    List<Productlist> productsList;
    String categoryid="",categoryname="",baseprice="",salesprice="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distibutorlist);
        getSupportActionBar().hide();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        idMapping();
        getData();

        setOnClick();

    }


    private void getData() {


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            categoryid = extras.getString("categoryid");
            categoryname = extras.getString("categoryname");
            baseprice= extras.getString("baseprice");
            salesprice= extras.getString("salesprice");
            callApi();
        }

    }
    private void callApi() {


        if (!UIUtil.checkNetwork(ActivityProductList.this)) {

            UIUtil.showCustomDialog("Alert", "There seems to be some problem with your internet connection. Please check", ActivityProductList.this);
            return;
        }

        UIUtil.showDialog(ActivityProductList.this);

        ApiHandler.getApiService().getProductlist(getmap(),new Callback<ProductlistMain>() {
            @Override
            public void success(ProductlistMain listMainResponse, Response response) {
                UIUtil.dismissDialog();

                if (listMainResponse == null) {
                    Toast.makeText(ActivityProductList.this, "Something wrong please try again...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (listMainResponse.getStatus() == null) {
                    Toast.makeText(ActivityProductList.this, "Something wrong please try again...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (listMainResponse.getProductlist() == null) {
                    Toast.makeText(ActivityProductList.this, "There are no products to select...", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (listMainResponse.getProductlist().isEmpty()) {
                    Toast.makeText(ActivityProductList.this, "There are no products to select...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (listMainResponse.getStatus() == 0) {
                    Toast.makeText(ActivityProductList.this, "There are no products to select...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (listMainResponse.getStatus() == 1) {

                    productsList = listMainResponse.getProductlist();

                    adapter = new ProductsAdapter(ActivityProductList.this, productsList);
                    lvDisributor.setAdapter(adapter);


                }

            }

            @Override
            public void failure(RetrofitError error) {
                UIUtil.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Log.e("error", "" + error.getMessage());

            }
        });


    }

    private void idMapping() {

        ivBack = (ImageView) findViewById(R.id.ivBack);
        lvDisributor = (ListView) findViewById(R.id.lvDisributor);

    }

    private void setOnClick() {

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        lvDisributor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                final Dialog dialog = new Dialog(ActivityProductList.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                // Include dialog.xml file
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_qty);


                dialog.show();


                TextView btn_ok = (TextView) dialog.findViewById(R.id.btn_ok);
                TextView btn_cancel = (TextView) dialog.findViewById(R.id.btn_cancel);
                final EditText edtQty = (EditText) dialog.findViewById(R.id.edtQty);
                final EditText edtDiscount = (EditText) dialog.findViewById(R.id.edtDiscount);
                TextView txt_total = dialog.findViewById(R.id.txt_total);




                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if (edtQty.getText().toString().trim().length() == 0) {


                            Toast.makeText(ActivityProductList.this, "Please enter Quantity", Toast.LENGTH_SHORT).show();

                            return;
                        }
                        Double product_subtotal_base=0.0,afterdiscount_base=0.0,product_subtotal_sales=0.0,afterdiscount_sales=0.0;

                        Intent intent = new Intent();
                        intent.putExtra("prouctname", "" + productsList.get(position).getSProductName());
                        intent.putExtra("prouctid", "" + productsList.get(position).getNProductId());
                        intent.putExtra("qty", edtQty.getText().toString().trim());

                        intent.putExtra("categoryid", ""+categoryid);
                        intent.putExtra("categoryname", ""+categoryname);

                        product_subtotal_base  = Double.valueOf(edtQty.getText().toString())* Double.valueOf(baseprice);
                        product_subtotal_sales  = Double.valueOf(edtQty.getText().toString())* Double.valueOf( salesprice);


                        intent.putExtra("beforediscount_subtotal_sales",""+product_subtotal_sales);
                        intent.putExtra("beforediscount_subtotal_base",""+product_subtotal_base);

                        try {

                            if(edtDiscount.getText().toString().trim().length()>0){
                                double temp = Double.parseDouble(edtDiscount.getText().toString().trim());
                                if(temp>100){
                                    Toast.makeText(ActivityProductList.this,"Please enter proper discount",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                intent.putExtra("discount", edtDiscount.getText().toString().trim());
                                afterdiscount_sales = (product_subtotal_sales - (product_subtotal_sales*temp)/100);
                                afterdiscount_base= (product_subtotal_base - (product_subtotal_base*temp)/100);

                                intent.putExtra("afterdiscount_subtotal_sales",""+ afterdiscount_sales);

                                intent.putExtra("afterdiscount_subtotal_base",""+ afterdiscount_base);
                            }
                            else{
                                intent.putExtra("discount", "0");
                                product_subtotal_sales  = Double.valueOf(edtQty.getText().toString())* Double.valueOf(salesprice);
                                product_subtotal_base  = Double.valueOf(edtQty.getText().toString())* Double.valueOf( baseprice);

                                intent.putExtra("afterdiscount_subtotal_sales",""+ product_subtotal_sales);

                                intent.putExtra("afterdiscount_subtotal_base",""+ product_subtotal_base);
                            }



                        }
                        catch (Exception e){

                        }
                        dialog.dismiss();

                        setResult(RESULT_OK, intent);

                        finish();
                    }
                });


            }
        });


    }
    private Map<String, String> getmap() {
        Map<String, String> map = new HashMap<>();

        map.put("nCatId", "" + categoryid);


        return map;
    }


}
