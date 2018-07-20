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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.excelimpex.Adapter.CategoryAdapter;
import com.excelimpex.Adapter.ProductsRequestAdapter;

import com.excelimpex.Model.CategoryList;
import com.excelimpex.Model.CategoryMain;
import com.excelimpex.Model.ProductsRequestModel;
import com.excelimpex.Model.ProductsRequestModelList;
import com.excelimpex.Model.ProductsRequestModelListApi;
import com.excelimpex.Model.SimpleMessageStatusResponse;
import com.excelimpex.R;
import com.excelimpex.Utils.ExpandableHeightListView;
import com.excelimpex.Utils.SessionManager;
import com.excelimpex.Utils.UIUtil;
import com.excelimpex.WebServices.ApiHandler;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Android on 3/17/2018.
 */

public class ActivityNewOrder extends AppCompatActivity {

    ImageView ivBack;
    TextView edtDistributorname, edtProductname, txtTotal, txtGst, txtaftergst;
    Button btnSubmit;
    ArrayList<ProductsRequestModelList> productRequestList;
    ArrayList<ProductsRequestModelListApi> productsRequestModelListApis;

    ProductsRequestAdapter productsRequestAdapter;
    ExpandableHeightListView lvProduct;
    RelativeLayout rlproducts, rldistributor;
    LinearLayout llTotal;
    Double totalAmount_sales = 0.0,totalAmount_base = 0.0;
    ProductsRequestModel productRequest;
    CategoryAdapter categoryAdapter;
    List<CategoryList> categorylist;
    String nDistributorId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neworder);
        getSupportActionBar().hide();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        idMapping();
        setOnClick();
    }

    private void idMapping() {

        ivBack = (ImageView) findViewById(R.id.ivBack);
        lvProduct = (ExpandableHeightListView) findViewById(R.id.lvProduct);
        lvProduct.setExpanded(true);

        rldistributor = (RelativeLayout) findViewById(R.id.rldistributor);
        rlproducts = (RelativeLayout) findViewById(R.id.rlproducts);


        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        edtDistributorname = (TextView) findViewById(R.id.edtDistributorname);
        edtProductname = (TextView) findViewById(R.id.edtProductname);
        llTotal = findViewById(R.id.llTotal);

        txtTotal = (TextView) findViewById(R.id.txtTotal);
        txtGst = (TextView) findViewById(R.id.txtGst);
        txtaftergst = (TextView) findViewById(R.id.txtaftergst);

        callCategoryApi();
        SessionManager sessionManager = new SessionManager(ActivityNewOrder.this);

        productRequest = new ProductsRequestModel();

        productRequestList = new ArrayList<>();
        productsRequestModelListApis = new ArrayList<>();
        productRequest.setId(sessionManager.getUserId());
        if (nDistributorId.length() > 0) {
            productRequest.setnDistributorId(nDistributorId);
        }

        productRequest.setsProducts(productsRequestModelListApis);

        productsRequestAdapter = new ProductsRequestAdapter(ActivityNewOrder.this, productRequestList, ActivityNewOrder.this);
        lvProduct.setAdapter(productsRequestAdapter);
        setTotal();
    }

    private void setTotal() {
        if (productRequestList.size() > 0) {
            llTotal.setVisibility(View.VISIBLE);
            try {
                txtTotal.setText("Rs. " + String.format("%.2f", totalAmount_sales));
                Double aftergst = ((totalAmount_sales * 18) / 100) + totalAmount_sales;


                txtaftergst.setText("Rs. " + String.format("%.2f", aftergst));


            } catch (Exception e) {
            }

        } else {
            llTotal.setVisibility(View.GONE);
            totalAmount_sales = 0.0;
        }
    }

    private void setOnClick() {

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        rldistributor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityNewOrder.this, ActivityDisributorList.class);
                startActivityForResult(intent, 202);
            }
        });
        rlproducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (categorylist != null) {

                    if (categorylist.size() > 0) {
                        showDailogforCategory();
                    } else {
                        Toast.makeText(ActivityNewOrder.this, "There are no products to select...", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(ActivityNewOrder.this, "There are no products to select...", Toast.LENGTH_SHORT).show();

                }


            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nDistributorId.length() == 0) {
                    Toast.makeText(ActivityNewOrder.this, "Please select distributor", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (productRequestList.size() == 0) {
                    Toast.makeText(ActivityNewOrder.this, "Please add products", Toast.LENGTH_SHORT).show();
                    return;
                }

                callApi();

            }
        });

    }

    private void callCategoryApi() {


        if (!UIUtil.checkNetwork(ActivityNewOrder.this)) {

            UIUtil.showCustomDialog("Alert", "There seems to be some problem with your internet connection. Please check", ActivityNewOrder.this);
            return;
        }

        UIUtil.showDialog(ActivityNewOrder.this);

        ApiHandler.getApiService().getCategorylist(new Callback<CategoryMain>() {
            @Override
            public void success(CategoryMain listMainResponse, Response response) {
                UIUtil.dismissDialog();

                if (listMainResponse == null) {
                    Toast.makeText(ActivityNewOrder.this, "Something wrong please try again...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (listMainResponse.getStatus() == null) {
                    Toast.makeText(ActivityNewOrder.this, "Something wrong please try again...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (listMainResponse.getCategorylist() == null) {
                    Toast.makeText(ActivityNewOrder.this, "There are no products to select...", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (listMainResponse.getCategorylist().isEmpty()) {
                    Toast.makeText(ActivityNewOrder.this, "There are no products to select...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (listMainResponse.getStatus() == 0) {
                    Toast.makeText(ActivityNewOrder.this, "There are no products to select...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (listMainResponse.getStatus() == 1) {

                    categorylist = listMainResponse.getCategorylist();


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

    private void showDailogforCategory() {


        final Dialog dialog = new Dialog(ActivityNewOrder.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Include dialog.xml file
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_dailog_list);


        dialog.show();


        TextView btn_cancel = (TextView) dialog.findViewById(R.id.btn_cancel);

        ListView lv = (ListView) dialog.findViewById(R.id.lv);
        categoryAdapter = new CategoryAdapter(ActivityNewOrder.this, categorylist);
        lv.setAdapter(categoryAdapter);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (categorylist.get(position).getNBasePrice() != null && categorylist.get(position).getNSalePrice() != null)

                {

                    if (categorylist.get(position).getNBasePrice().length() > 0 && categorylist.get(position).getNSalePrice().length() > 0) {


                        dialog.dismiss();
                        Intent i = new Intent(ActivityNewOrder.this, ActivityProductList.class);
                        i.putExtra("categoryid", "" + categorylist.get(position).getNCatId());
                        i.putExtra("categoryname", "" + categorylist.get(position).getSName());
                        i.putExtra("baseprice", "" + categorylist.get(position).getNBasePrice());
                        i.putExtra("salesprice", "" + categorylist.get(position).getNSalePrice());

                        startActivityForResult(i, 201);
                    }
                }

                else {
                    Toast.makeText(ActivityNewOrder.this,"Price of this Category is not defined.",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void callApi() {

        if (!UIUtil.checkNetwork(ActivityNewOrder.this)) {

            UIUtil.showCustomDialog("Alert", "There seems to be some problem with your internet connection. Please check", ActivityNewOrder.this);
            return;
        }

        UIUtil.showDialog(ActivityNewOrder.this);
        ApiHandler.getApiService().addOrder(getmap(), new Callback<SimpleMessageStatusResponse>() {
            @Override
            public void success(SimpleMessageStatusResponse addexpansesResponse, Response response) {
                UIUtil.dismissDialog();
                if (addexpansesResponse == null) {
                    Toast.makeText(ActivityNewOrder.this, "Something went wrong.Please try again later.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (addexpansesResponse == null) {
                    Toast.makeText(ActivityNewOrder.this, "Something went wrong.Please try again later.", Toast.LENGTH_SHORT).show();

                    return;
                }

                if (addexpansesResponse.getStatus() == null) {
                    Toast.makeText(ActivityNewOrder.this, "Something went wrong.Please try again later.", Toast.LENGTH_SHORT).show();

                    return;
                }

                if (addexpansesResponse.getStatus() == 0) {
                    Toast.makeText(ActivityNewOrder.this, "Something went wrong.Please try again later.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (addexpansesResponse.getStatus() == 1) {
                    Toast.makeText(ActivityNewOrder.this, "Order successfully added...", Toast.LENGTH_SHORT).show();
                   // finish();
                    return;
                }


            }

            @Override
            public void failure(RetrofitError error) {
                UIUtil.dismissDialog();
                Toast.makeText(ActivityNewOrder.this, "Something went wrong.Please try again later.", Toast.LENGTH_SHORT).show();
                UIUtil.log("Retrofit Error" + error.getMessage());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == 201) {
                String subtotal_sales = "", subtotal_base = "",beforediscount_subtotal_sales = "",beforediscount_subtotal_base = "";
                String name = data.getExtras().getString("prouctname");
                String id = data.getExtras().getString("prouctid");
                String quantity = data.getExtras().getString("qty");

                String categoryid = data.getExtras().getString("categoryid");
                String categoryname = data.getExtras().getString("categoryname");


                String discount = data.getExtras().getString("discount");
                if (data.getExtras().getString("beforediscount_subtotal_sales") != null) {
                    beforediscount_subtotal_sales= data.getExtras().getString("beforediscount_subtotal_sales");
                }
                if (data.getExtras().getString("afterdiscount_subtotal_sales") != null) {
                    subtotal_sales = data.getExtras().getString("afterdiscount_subtotal_sales");
                    Double dob_subtotal = Double.valueOf(subtotal_sales);
                    totalAmount_sales = totalAmount_sales + dob_subtotal;
                }

                if (data.getExtras().getString("beforediscount_subtotal_base") != null) {
                    beforediscount_subtotal_base = data.getExtras().getString("beforediscount_subtotal_base");
                }
                if (data.getExtras().getString("afterdiscount_subtotal_base") != null) {
                    subtotal_base = data.getExtras().getString("afterdiscount_subtotal_base");
                    Double dob_subtotal = Double.valueOf(subtotal_base);
                    totalAmount_base = totalAmount_base + dob_subtotal;
                }
                productRequestList.add(new ProductsRequestModelList(name, id, quantity, subtotal_sales, discount, beforediscount_subtotal_sales, categoryname,subtotal_base,beforediscount_subtotal_base));

                productsRequestModelListApis.add(new ProductsRequestModelListApi(id, categoryid, quantity, subtotal_base, discount, beforediscount_subtotal_base, subtotal_base));

                productsRequestAdapter.notifyDataSetChanged();
                setTotal();
            } else if (requestCode == 202) {
                String name = data.getExtras().getString("name");
                String id = data.getExtras().getString("id");
                nDistributorId = id;
                if (nDistributorId.length() > 0) {
                    productRequest.setnDistributorId(nDistributorId);
                }
                edtDistributorname.setText(name);
            }


        }
    }

    public void deleteProduct(int position) {

        try {

            Double temp_sub = Double.valueOf(productRequestList.get(position).getSubtotal());
            totalAmount_sales = totalAmount_sales - temp_sub;
            productRequestList.remove(position);
            productsRequestModelListApis.remove(position);
            productsRequestAdapter.notifyDataSetChanged();

            Double temp_sub_base = Double.valueOf(productRequestList.get(position).getsAfterDiscountBase());
            totalAmount_base = totalAmount_base - temp_sub_base;

        } catch (Exception e) {
        }


        setTotal();
    }

    private Map<String, String> getmap() {
        Map<String, String> map = new HashMap<>();

        Gson gson = new Gson();
        String jsonObject = gson.toJson(productsRequestModelListApis);

        SessionManager sessionManager = new SessionManager(ActivityNewOrder.this);
        map.put("nSalesPersonId", sessionManager.getUserId());
        map.put("nDistributorId", "" + nDistributorId);
        map.put("sTotal", "" + totalAmount_base);
        map.put("sProducts", jsonObject);

        Log.e("getMap", "" + map);

        return map;
    }
}
