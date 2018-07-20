package com.excelimpex.Activities;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.excelimpex.R;


public abstract class BaseActivity extends AppCompatActivity {

    private LinearLayout llContainer;
    private CountDownTimer timer;
    int i =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        getSupportActionBar().hide();


        setView();
        getLatLong();
    }


    private void setView() {
        llContainer = (LinearLayout) findViewById(R.id.ll_container);

    }


    private void getLatLong() {
        timer = new CountDownTimer(1000, 1) {

            @Override
            public void onTick(long millisUntilFinished)
            {

            }

            @Override
            public void onFinish() {
                try{

                    callContinousGetLatLong();
                    i++;
                    timer.start();
                }catch(Exception e){
                 //   Log.e("Error", "Error: " + e.toString());
                }
            }
        }.start();
    }

    private void callContinousGetLatLong() {
        Log.e("Error", ""+i);

    }



    /**
     *  Abstract methods*
     */



    /**
     *  Hide keyboard
     */
    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    protected LinearLayout getContainer() {
        return llContainer;
    }

    /**
     * To set Information button
     */


    @Override
    public void onBackPressed() {


        if(timer !=null){
            timer.cancel();
        }
        super.onBackPressed();
        return;

    }




}
