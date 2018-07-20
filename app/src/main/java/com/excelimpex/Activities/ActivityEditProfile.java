package com.excelimpex.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.excelimpex.Model.LoginUser.Login;
import com.excelimpex.Model.LoginUser.UpdateProfile;
import com.excelimpex.R;
import com.excelimpex.Utils.SessionManager;
import com.excelimpex.Utils.UIUtil;
import com.excelimpex.WebServices.ApiHandler;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ActivityEditProfile extends AppCompatActivity {
    private int CAMERA_PERMISSION_CODE = 24;
    Button btnSubmit;
    ImageView ivBack;
    EditText edtFname, edtLname,edtEmail,edtPhone;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        getSupportActionBar().hide();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        sessionManager = new SessionManager(ActivityEditProfile.this);

        idMapping();
        setOnClick();
    }

    private void idMapping() {
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        edtFname = (EditText) findViewById(R.id.edtFname);
        edtLname = (EditText) findViewById(R.id.edtLname);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        ivBack = (ImageView) findViewById(R.id.ivBack);

        if(sessionManager.getKeyFirstName() !=null){
            edtFname.setText(""+sessionManager.getKeyFirstName());
        }
        if(sessionManager.getKeyLastName() !=null){
            edtLname.setText(""+sessionManager.getKeyLastName());
        }
        if(sessionManager.getKeyEmail()!=null){
            edtEmail.setText(""+sessionManager.getKeyEmail());
        }

        if(sessionManager.getKeyPhone() !=null){
            edtPhone.setText(""+sessionManager.getKeyPhone());
        }

    }

    private void setOnClick() {

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edtFname.getText().toString().trim().length() == 0) {


                    Toast.makeText(ActivityEditProfile.this, "Please enter First Name", Toast.LENGTH_SHORT).show();

                    return;
                }
                if (edtLname.getText().toString().trim().length() == 0) {


                    Toast.makeText(ActivityEditProfile.this, "Please enter Last Name", Toast.LENGTH_SHORT).show();

                    return;
                }


                if (edtEmail.getText().toString().trim().length() == 0) {


                    Toast.makeText(ActivityEditProfile.this, "Please enter Email Id", Toast.LENGTH_SHORT).show();

                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(
                        edtEmail.getText().toString()).matches()) {
                    Toast.makeText(ActivityEditProfile.this, "Please enter valid Email Id", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (edtPhone.getText().toString().trim().length() == 0) {


                    Toast.makeText(ActivityEditProfile.this, "Please enter Phone Number", Toast.LENGTH_SHORT).show();

                    return;
                }

                if (edtPhone.getText().toString().trim().length() != 10) {


                    Toast.makeText(ActivityEditProfile.this, "Please enter valid Phone Number", Toast.LENGTH_SHORT).show();

                    return;
                }



                if (!UIUtil.checkNetwork(ActivityEditProfile.this)) {

                    UIUtil.showCustomDialog("Alert", "There seems to be some problem with your internet connection. Please check.", ActivityEditProfile.this);
                    return;
                }

                callUpdateProfileApi();


            }
        });
    }

    private void callUpdateProfileApi() {


        UIUtil.showDialog(ActivityEditProfile.this);
        ApiHandler.getApiService().getUpdateProfile(getmap(), new Callback<UpdateProfile>() {


            @Override
            public void success(UpdateProfile mainResponse, Response response) {
                UIUtil.dismissDialog();


                if (mainResponse == null) {

                    Toast.makeText(ActivityEditProfile.this, "Something went wrong...Please try again later.", Toast.LENGTH_SHORT).show();


                    return;
                }


                if (mainResponse.getStatus() == null) {

                    Toast.makeText(ActivityEditProfile.this, "Something went wrong...Please try again later.", Toast.LENGTH_SHORT).show();


                    return;
                }


                if (mainResponse.getStatus() == 0) {

                    Toast.makeText(ActivityEditProfile.this, "" + mainResponse.getMsg(), Toast.LENGTH_SHORT).show();


                    return;
                }


                if (mainResponse.getStatus() == 1) {


                    if (mainResponse.getUserdata() != null) {


                        Toast.makeText(ActivityEditProfile.this, "Profile successfully updated.", Toast.LENGTH_SHORT).show();


                        if( mainResponse.getUserdata().getSFirstName() !=null){
                            sessionManager.setKeyFirstName("" + mainResponse.getUserdata().getSFirstName());
                        }
                        if( mainResponse.getUserdata().getSLastName() !=null){
                            sessionManager.setKeyLastName("" + mainResponse.getUserdata().getSLastName());
                        }

                        if( mainResponse.getUserdata().getSEmail() !=null){
                            sessionManager.setKeyEmail("" + mainResponse.getUserdata().getSEmail());
                        }
                        if( mainResponse.getUserdata().getNPhone() !=null){
                            sessionManager.setKeyPhone("" + mainResponse.getUserdata().getNPhone());
                        }


                        finish();


                    }
                }

            }

            @Override
            public void failure(RetrofitError error) {
                UIUtil.dismissDialog();
                Toast.makeText(ActivityEditProfile.this, "Something went wrong...Please try again later.", Toast.LENGTH_SHORT).show();

                error.printStackTrace();
                error.getMessage();
                Log.e("error", "" + error.getMessage());

            }
        });


    }

    private Map<String, String> getmap() {
        Map<String, String> map = new HashMap<>();

        map.put("nPhone", "" + edtPhone.getText().toString().trim());
        map.put("nUserId", "" + sessionManager.getUserId());
        map.put("sLastName", "" + edtLname.getText().toString().trim());
        map.put("sFirstName", "" + edtFname.getText().toString().trim());



        Log.e("getMap", "" + map);

        return map;
    }


}
