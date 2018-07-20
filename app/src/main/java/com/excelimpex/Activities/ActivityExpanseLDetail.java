package com.excelimpex.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.excelimpex.R;
import com.excelimpex.Utils.SessionManager;
import com.excelimpex.application.ExcelImpex;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.Map;


public class ActivityExpanseLDetail extends AppCompatActivity {

    Button btnSubmit;
    ImageView ivBack,ivpicStXe,ivpicFare;
    EditText edtFrom,edtTo,edtVisitType,edtAllowance,edtFare,edtStationary,edtXerox,edtExpanses;
    SessionManager sessionManager;
    LinearLayout llphotoFare,llphotoStXe;
    private ImageLoader imageLoader;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expansedetail);
        getSupportActionBar().hide();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        sessionManager = new SessionManager(ActivityExpanseLDetail.this);
        imageLoader = ImageLoader.getInstance();
        idMapping();

        getData();

        setOnClick();
    }

    private void getData() {

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();


        if (extras != null) {
            String SXerox = extras.getString("SXerox");
            if(SXerox.length()>0){
                edtXerox.setText("Xerox: "+SXerox);
            }
           String SStationery = extras.getString("SStationery");
            if(SStationery.length()>0){
                edtStationary.setText("Stationary: "+SStationery);
            }

            String getSTravelPlace = extras.getString("getSTravelPlace");
            if(getSTravelPlace.length()>0){

            }

            String getSTravelFrom = extras.getString("getSTravelFrom");

            if(getSTravelFrom.length()>0){
                edtFrom.setText("Travel From : "+getSTravelFrom);
            }


            String getSTravelTo = extras.getString("getSTravelTo");
            if(getSTravelTo.length()>0){
                edtTo.setText("Travel To : "+getSTravelTo);
            }

            String getSVisitType = extras.getString("getSVisitType");
            if(getSVisitType.length()>0){
                edtVisitType.setText("Visit Type : "+getSVisitType);
            }

            String getSDailyAllowance = extras.getString("getSDailyAllowance");
            if(getSDailyAllowance.length()>0){
                edtAllowance.setText("Daily Allowance  : "+getSDailyAllowance);
            }


            String getSFare = extras.getString("getSFare");
            if(getSFare.length()>0){
                edtFare.setText("Fare in Rs. : "+getSFare);
            }

            String getSFareImage = extras.getString("getSFareImage");
            if(getSFareImage.length()>0){


                imageLoader.displayImage(getSFareImage,ivpicFare, ExcelImpex.getExpanseImageDisplayOption(ActivityExpanseLDetail.this));

            }



            String getSTotal = extras.getString("getSTotal");
            if(getSTotal.length()>0){
                edtExpanses.setText("Total Expanses in Rs. : "+getSTotal);
            }

            String getSXeroxImage = extras.getString("getSXeroxImage");
            if(getSXeroxImage.length()>0){
                imageLoader.displayImage(getSFareImage,ivpicStXe, ExcelImpex.getExpanseImageDisplayOption(ActivityExpanseLDetail.this));

            }

            String getSStationeryImage = extras.getString("getSStationeryImage");
            if(getSStationeryImage.length()>0){
                imageLoader.displayImage(getSFareImage,ivpicStXe, ExcelImpex.getExpanseImageDisplayOption(ActivityExpanseLDetail.this));

            }



        }
    }

    private void idMapping() {



        ivBack = findViewById(R.id.ivBack);
        edtExpanses = findViewById(R.id.edtExpanses);
        edtFrom = findViewById(R.id.edtFrom);
        edtTo = findViewById(R.id.edtTo);
        edtVisitType = findViewById(R.id.edtVisitType);
        edtFare = findViewById(R.id.edtFare);
        edtStationary = findViewById(R.id.edtStationary);
        edtXerox = findViewById(R.id.edtXerox);
        edtAllowance= findViewById(R.id.edtAllowance);
        llphotoFare = (LinearLayout)findViewById(R.id.llphotoFare);
        llphotoStXe = (LinearLayout)findViewById(R.id.llphotoStXe);
        ivpicFare = (ImageView)findViewById(R.id.ivpicFare);
        ivpicStXe= (ImageView)findViewById(R.id.ivpicStXe);
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

        map.put("nUserId", "" + sessionManager.getUserId());


        Log.e("getMap", "" + map);

        return map;
    }


}
