package com.excelimpex.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.excelimpex.Adapter.DistributorAdapter;
import com.excelimpex.Adapter.ExpansesAdapter;
import com.excelimpex.Adapter.ProductsAdapter;
import com.excelimpex.Model.Distributor;
import com.excelimpex.Model.Expanses;
import com.excelimpex.Model.ExpansesMainResponse;
import com.excelimpex.Model.Productlist;
import com.excelimpex.Model.ProductlistMain;
import com.excelimpex.R;
import com.excelimpex.Utils.SessionManager;
import com.excelimpex.Utils.UIUtil;
import com.excelimpex.WebServices.ApiHandler;

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

public class ActivityExpansesList extends AppCompatActivity {

    ImageView ivBack;
    ListView lvExpanses;
    List<Expanses> expansesList;
    ExpansesAdapter adapter;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanseslist);
        getSupportActionBar().hide();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        idMapping();
        setOnClick();

    }

    private void idMapping() {

        ivBack = (ImageView)findViewById(R.id.ivBack);
        lvExpanses = (ListView)findViewById(R.id.lvExpanses);
        fab = findViewById(R.id.fab);



    }


    @Override
    protected void onResume() {
        callExpansesApi();
        super.onResume();
    }

    private void callExpansesApi() {



        if (!UIUtil.checkNetwork(ActivityExpansesList.this)) {

            UIUtil.showCustomDialog("Alert", "There seems to be some problem with your internet connection. Please check", ActivityExpansesList.this);
            return;
        }

        UIUtil.showDialog(ActivityExpansesList.this);

        ApiHandler.getApiService().getExpanseslist(getmap(),new Callback<ExpansesMainResponse>() {
            @Override
            public void success(ExpansesMainResponse listMainResponse, Response response) {
                UIUtil.dismissDialog();

                if (listMainResponse == null) {
                    Toast.makeText(ActivityExpansesList.this, "Something wrong please try again...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (listMainResponse.getStatus() == null) {
                    Toast.makeText(ActivityExpansesList.this, "Something wrong please try again...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (listMainResponse.getExpancelist() == null) {
                    Toast.makeText(ActivityExpansesList.this, "You have no added any Expanse yet...", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (listMainResponse.getExpancelist().isEmpty()) {
                    Toast.makeText(ActivityExpansesList.this, "You have no added any Expanse yet...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (listMainResponse.getStatus() == 0) {
                    Toast.makeText(ActivityExpansesList.this, "You have no added any Expanse yet...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (listMainResponse.getStatus() == 1) {



                    expansesList = listMainResponse.getExpancelist();

                    adapter = new ExpansesAdapter(ActivityExpansesList.this, expansesList);
                    lvExpanses.setAdapter(adapter);


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

    private void setOnClick() {

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityExpansesList.this, ActivityAddExpanse.class);
                startActivity(i);

            }
        });

    }

    private Map<String, String> getmap() {
        Map<String, String> map = new HashMap<>();
        SessionManager sessionManager = new SessionManager(ActivityExpansesList.this);
          map.put("nSalesPersonId", sessionManager.getUserId());



        Log.e("getMap", "" + map);

        return map;
    }


}
