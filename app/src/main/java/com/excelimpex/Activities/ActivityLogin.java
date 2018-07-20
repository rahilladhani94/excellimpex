package com.excelimpex.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.excelimpex.Model.LoginManager.LoginManager;
import com.excelimpex.Model.LoginUser.Login;
import com.excelimpex.R;
import com.excelimpex.Utils.SessionManager;
import com.excelimpex.Utils.UIUtil;
import com.excelimpex.WebServices.ApiHandler;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ActivityLogin extends AppCompatActivity {
    private int CAMERA_PERMISSION_CODE = 24;
    Button btnSubmit;
    ImageView ivBack;
    EditText edtPassword, edtEmail;
    CheckBox checkBoxSales,checkBoxManager,checkBoxStockiest;
    LinearLayout llstockies,llmanager,llsales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        idMapping();
        setOnClick();
    }

    private void idMapping() {
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        checkBoxSales=(CheckBox)findViewById(R.id.checkBoxSales);
        checkBoxManager=(CheckBox)findViewById(R.id.checkBoxManager);
        checkBoxStockiest=(CheckBox)findViewById(R.id.checkBoxStockiest);

        llstockies = findViewById(R.id.llstockies);
        llmanager = findViewById(R.id.llmanager);
        llsales = findViewById(R.id.llsales);

    }

    private void setOnClick() {


        llsales.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                //is chkIos checked?
                if (checkBoxSales.isChecked()) {
                    checkBoxSales.setChecked(false);
                    checkBoxManager.setChecked(true);
                    checkBoxStockiest.setChecked(false);

                }
                else {


                    checkBoxSales.setChecked(true);
                    checkBoxStockiest.setChecked(false);
                    checkBoxManager.setChecked(false);
                }

            }
        });

        llmanager.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if ((checkBoxManager).isChecked()) {
                    checkBoxSales.setChecked(true);
                    checkBoxManager.setChecked(false);
                    checkBoxStockiest.setChecked(false);

                }
                else {


                    checkBoxSales.setChecked(false);
                    checkBoxStockiest.setChecked(false);
                    checkBoxManager.setChecked(true);
                }

            }
        });

        llstockies.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //is chkIos checked?
                        if (checkBoxStockiest.isChecked()) {



                            checkBoxSales.setChecked(true);
                            checkBoxManager.setChecked(false);
                            checkBoxStockiest.setChecked(false);
                        }
                        else {
                            checkBoxSales.setChecked(false);
                            checkBoxManager.setChecked(false);
                            checkBoxStockiest.setChecked(true);
                        }

                    }
                });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edtEmail.getText().toString().trim().length() == 0) {


                    Toast.makeText(ActivityLogin.this, "Please enter Email Id", Toast.LENGTH_SHORT).show();

                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(
                        edtEmail.getText().toString()).matches()) {
                    Toast.makeText(ActivityLogin.this, "Please enter valid Email Id", Toast.LENGTH_SHORT).show();
                    return;

                }


                if (edtPassword.getText().toString().trim().length() == 0) {


                    Toast.makeText(ActivityLogin.this, "Please enter Password", Toast.LENGTH_SHORT).show();

                    return;
                }
                if (!UIUtil.checkNetwork(ActivityLogin.this)) {

                    UIUtil.showCustomDialog("Alert", "There seems to be some problem with your internet connection. Please check.", ActivityLogin.this);
                    return;
                }

                if(checkBoxManager.isChecked()){
                    callLoginApiManager();

                }
                else {
                    callLoginApi();
                }



            }
        });
    }
    private void callLoginApiManager() {


        UIUtil.showDialog(ActivityLogin.this);
        ApiHandler.getApiService().getLoginManager(getmap(), new Callback<LoginManager>() {


            @Override
            public void success(LoginManager mainResponse, Response response) {
                UIUtil.dismissDialog();


                if (mainResponse == null) {

                    Toast.makeText(ActivityLogin.this, "Something went wrong...Please try again later.", Toast.LENGTH_SHORT).show();


                    return;
                }


                if (mainResponse.getStatus() == null) {

                    Toast.makeText(ActivityLogin.this, "Something went wrong...Please try again later.", Toast.LENGTH_SHORT).show();


                    return;
                }


                if (mainResponse.getStatus() == 0) {

                    Toast.makeText(ActivityLogin.this, "" + mainResponse.getMsg(), Toast.LENGTH_SHORT).show();


                    return;
                }


                if (mainResponse.getStatus() == 1) {


                    if (mainResponse.getUserdata() != null) {


                        Toast.makeText(ActivityLogin.this, "You are successfully logged in.", Toast.LENGTH_SHORT).show();


                        SessionManager sessionManager = new SessionManager(ActivityLogin.this);

                        sessionManager.setKeyUserId("" + mainResponse.getUserdata().get(0).getNManagerId());
                        if( mainResponse.getUserdata().get(0).getSFullName() !=null){
                            sessionManager.setKeyFirstName("" + mainResponse.getUserdata().get(0).getSFullName());
                        }

                        if( mainResponse.getUserdata().get(0).getSEmail() !=null){
                            sessionManager.setKeyEmail("" + mainResponse.getUserdata().get(0).getSEmail());
                        }
//
//                        Intent i = new Intent(ActivityLogin.this, MainActivity.class);
//                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(i);


                    }
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
    private void callLoginApi() {


        UIUtil.showDialog(ActivityLogin.this);
        ApiHandler.getApiService().getLogin(getmap(), new Callback<Login>() {


            @Override
            public void success(Login mainResponse, Response response) {
                UIUtil.dismissDialog();


                if (mainResponse == null) {

                    Toast.makeText(ActivityLogin.this, "Something went wrong...Please try again later.", Toast.LENGTH_SHORT).show();


                    return;
                }


                if (mainResponse.getStatus() == null) {

                    Toast.makeText(ActivityLogin.this, "Something went wrong...Please try again later.", Toast.LENGTH_SHORT).show();


                    return;
                }


                if (mainResponse.getStatus() == 0) {

                    Toast.makeText(ActivityLogin.this, "" + mainResponse.getMsg(), Toast.LENGTH_SHORT).show();


                    return;
                }


                if (mainResponse.getStatus() == 1) {


                    if (mainResponse.getUserdata() != null) {


                        Toast.makeText(ActivityLogin.this, "You are successfully logged in.", Toast.LENGTH_SHORT).show();


                        SessionManager sessionManager = new SessionManager(ActivityLogin.this);

                        sessionManager.setKeyUserId("" + mainResponse.getUserdata().get(0).getNSalesPersonId());
                        if( mainResponse.getUserdata().get(0).getSFirstName() !=null){
                            sessionManager.setKeyFirstName("" + mainResponse.getUserdata().get(0).getSFirstName());
                        }
                        if( mainResponse.getUserdata().get(0).getSLastName() !=null){
                            sessionManager.setKeyLastName("" + mainResponse.getUserdata().get(0).getSLastName());
                        }

                        if( mainResponse.getUserdata().get(0).getSEmail() !=null){
                            sessionManager.setKeyEmail("" + mainResponse.getUserdata().get(0).getSEmail());
                        }
                        if( mainResponse.getUserdata().get(0).getNPhone() !=null){
                            sessionManager.setKeyPhone("" + mainResponse.getUserdata().get(0).getNPhone());
                        }

                        if( mainResponse.getUserdata().get(0).getSImage() !=null){
                            sessionManager.setKeyImage("" + mainResponse.getUserdata().get(0).getSImage());
                        }
                        Intent i = new Intent(ActivityLogin.this, MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);


                    }
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

    private Map<String, String> getmap() {
        Map<String, String> map = new HashMap<>();

        map.put("sEmail", "" + edtEmail.getText().toString().trim());
        map.put("sPassword", "" + edtPassword.getText().toString().trim());
        if(checkBoxManager.isChecked()){
            map.put("sUserType", "3");

        }
        else {
            map.put("sUserType", "2");
        }


        Log.e("getMap", "" + map);

        return map;
    }


}
