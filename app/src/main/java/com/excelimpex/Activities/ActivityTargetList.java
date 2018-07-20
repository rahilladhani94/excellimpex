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
import com.excelimpex.Adapter.TargetAdapter;
import com.excelimpex.Model.CategoryList;
import com.excelimpex.Model.CategoryMain;
import com.excelimpex.Model.Productlist;
import com.excelimpex.Model.ProductlistMain;
import com.excelimpex.Model.TargetListMain;
import com.excelimpex.Model.Targetlist;
import com.excelimpex.R;
import com.excelimpex.Utils.SessionManager;
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

public class ActivityTargetList extends AppCompatActivity {

    ImageView ivBack;
    ListView lvDisributor;
    TargetAdapter adapter;
    List<Productlist> productsList;
    List<Targetlist> targetList;
    TextView txtTotal,txtTotalAch;
    List<CategoryList> categorylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_targetlist);
        getSupportActionBar().hide();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        idMapping();
        callTargetApi();
        setOnClick();

    }


    private void callCategoryApi() {


        if (!UIUtil.checkNetwork(ActivityTargetList.this)) {

            UIUtil.showCustomDialog("Alert", "There seems to be some problem with your internet connection. Please check", ActivityTargetList.this);
            return;
        }


        ApiHandler.getApiService().getCategorylist(new Callback<CategoryMain>() {
            @Override
            public void success(CategoryMain listMainResponse, Response response) {
                UIUtil.dismissDialog();

                if (listMainResponse == null) {
                    Toast.makeText(ActivityTargetList.this, "Something wrong please try again...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (listMainResponse.getStatus() == null) {
                    Toast.makeText(ActivityTargetList.this, "Something wrong please try again...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (listMainResponse.getCategorylist() == null) {
                    Toast.makeText(ActivityTargetList.this, "There are no products to select...", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (listMainResponse.getCategorylist().isEmpty()) {
                    Toast.makeText(ActivityTargetList.this, "There are no products to select...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (listMainResponse.getStatus() == 0) {
                    Toast.makeText(ActivityTargetList.this, "There are no products to select...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (listMainResponse.getStatus() == 1) {

                    categorylist = listMainResponse.getCategorylist();

                    adapter = new TargetAdapter(ActivityTargetList.this,targetList,categorylist);
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

    private void callTargetApi() {

        if (!UIUtil.checkNetwork(ActivityTargetList.this)) {

            UIUtil.showCustomDialog("Alert", "There seems to be some problem with your internet connection. Please check", ActivityTargetList.this);
            return;
        }

        UIUtil.showDialog(ActivityTargetList.this);

        ApiHandler.getApiService().getTargetlist(getmap(),new Callback<TargetListMain>() {
            @Override
            public void success(TargetListMain listMainResponse, Response response) {


                if (listMainResponse == null) {
                    UIUtil.dismissDialog();
                    Toast.makeText(ActivityTargetList.this, "Something wrong please try again...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (listMainResponse.getStatus() == null) {
                    UIUtil.dismissDialog();
                    Toast.makeText(ActivityTargetList.this, "Something wrong please try again...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (listMainResponse.getTargetlist() == null) {
                    UIUtil.dismissDialog();
                    Toast.makeText(ActivityTargetList.this, "There are no targets for you yet...", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (listMainResponse.getTargetlist().isEmpty()) {
                    UIUtil.dismissDialog();
                    Toast.makeText(ActivityTargetList.this, "There are no targets for you yet...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (listMainResponse.getStatus() == 0) {
                    UIUtil.dismissDialog();
                    Toast.makeText(ActivityTargetList.this, "There are no targets for you yet...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (listMainResponse.getStatus() == 1) {

                    if(listMainResponse.getNTotalAchived() !=null){
                        txtTotalAch.setText("Total Target Achieved : RS."+listMainResponse.getNTotalAchived());
                    }
                    if(listMainResponse.getNTotalTarget() !=null){
                        txtTotal.setText("Total Target : RS."+listMainResponse.getNTotalTarget());

                    }
                    targetList = listMainResponse.getTargetlist();

                    callCategoryApi();




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
        txtTotal = findViewById(R.id.txtTotal);
        txtTotalAch = findViewById(R.id.txtTotalAch);


    }

    private void setOnClick() {

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });


    }
    private Map<String, String> getmap() {
        Map<String, String> map = new HashMap<>();
        SessionManager sessionManager = new SessionManager(ActivityTargetList.this);
        map.put("nSalesPersonId", sessionManager.getUserId());



        Log.e("getMap", "" + map);

        return map;
    }
}
