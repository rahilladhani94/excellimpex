package com.excelimpex.Activities;

import android.content.Intent;
import android.os.Bundle;
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
import com.excelimpex.Model.Distributor;
import com.excelimpex.Model.DistributorList;
import com.excelimpex.Model.DistributorMain;
import com.excelimpex.Model.Expanses;
import com.excelimpex.Model.ExpansesMainResponse;
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

public class ActivityDisributorList extends AppCompatActivity {

    ImageView ivBack;
    ListView lvDisributor;

    DistributorAdapter adapter;
    List<DistributorList> distributorLists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distibutorlist);
        getSupportActionBar().hide();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        idMapping();
        setOnClick();

    }

    private void idMapping() {

        ivBack = (ImageView) findViewById(R.id.ivBack);
        lvDisributor = (ListView) findViewById(R.id.lvDisributor);

        callApi();




    }

    private void callApi() {


        if (!UIUtil.checkNetwork(ActivityDisributorList.this)) {

            UIUtil.showCustomDialog("Alert", "There seems to be some problem with your internet connection. Please check", ActivityDisributorList.this);
            return;
        }

        UIUtil.showDialog(ActivityDisributorList.this);

        ApiHandler.getApiService().getDistributorlist(getmap(), new Callback<DistributorMain>() {
            @Override
            public void success(DistributorMain listMainResponse, Response response) {
                UIUtil.dismissDialog();

                if (listMainResponse == null) {
                    Toast.makeText(ActivityDisributorList.this, "Something wrong please try again...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (listMainResponse.getStatus() == null) {
                    Toast.makeText(ActivityDisributorList.this, "Something wrong please try again...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (listMainResponse.getDistributorLists() == null) {
                    Toast.makeText(ActivityDisributorList.this, "No distributor found...", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (listMainResponse.getDistributorLists().isEmpty()) {
                    Toast.makeText(ActivityDisributorList.this, "No distributor found...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (listMainResponse.getStatus() == 0) {
                    Toast.makeText(ActivityDisributorList.this, "No distributor found...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (listMainResponse.getStatus() == 1) {


                    distributorLists = listMainResponse.getDistributorLists();

                    adapter = new DistributorAdapter(ActivityDisributorList.this, distributorLists);
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


                Intent intent = new Intent();
                intent.putExtra("name", "" + distributorLists.get(position).getSFullName());
                intent.putExtra("id", "" + distributorLists.get(position).getNSalesPersonId());

                setResult(RESULT_OK, intent);
                finish();


            }
        });
    }

    private Map<String, String> getmap() {
        Map<String, String> map = new HashMap<>();
        SessionManager sessionManager = new SessionManager(ActivityDisributorList.this);
        map.put("nSalesPersonId", sessionManager.getUserId());



        Log.e("getMap", "" + map);

        return map;
    }


}
