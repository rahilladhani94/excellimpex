package com.excelimpex.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.excelimpex.BuildConfig;
import com.excelimpex.Model.LoginUser.UpdateProfile;
import com.excelimpex.Model.SimpleMessageStatusResponse;
import com.excelimpex.R;
import com.excelimpex.Utils.BitmapLoader;
import com.excelimpex.Utils.SessionManager;
import com.excelimpex.Utils.UIUtil;
import com.excelimpex.WebServices.ApiHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class ActivityAddExpanse extends AppCompatActivity {
    private String selectedImagePath="",selectedImagePathSt="";
    private static int REQUEST_IMAGE_CAPTURE_FOR = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int RequestPermissionCode = 7;
    Button btnSubmit;
    ImageView ivBack;
    EditText edtFrom,edtTo,edtVisitType,edtAllowance,edtFare,edtStationary,edtXerox,edtExpanses;
    SessionManager sessionManager;
    LinearLayout llphotoFare,llphotoStXe;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addexpanse);
        getSupportActionBar().hide();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        sessionManager = new SessionManager(ActivityAddExpanse.this);

        idMapping();


        setOnClick();
    }

    private void idMapping() {
        btnSubmit =findViewById(R.id.btnSubmit);


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
    }

    private void setOnClick() {

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        llphotoFare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(CheckingPermissionIsEnabledOrNot())
                {
                    REQUEST_IMAGE_CAPTURE_FOR = 1;
                    dispatchTakePictureIntent() ;
                }


                else {


                    RequestMultiplePermission();

                }

            }
        });

        llphotoStXe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(CheckingPermissionIsEnabledOrNot())
                {
                    REQUEST_IMAGE_CAPTURE_FOR = 2;
                    dispatchTakePictureIntent() ;
                }


                else {


                    RequestMultiplePermission();

                }

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edtExpanses.getText().toString().trim().length() == 0) {


                    Toast.makeText(ActivityAddExpanse.this, "Please enter total Expanses in Rs.", Toast.LENGTH_SHORT).show();

                    return;
                }
                if (edtFrom.getText().toString().trim().length() == 0 ) {


                    Toast.makeText(ActivityAddExpanse.this, "Please enter Visit From", Toast.LENGTH_SHORT).show();

                    return;
                }
                if (edtTo.getText().toString().trim().length() == 0 ) {


                    Toast.makeText(ActivityAddExpanse.this, "Please enter Visit To", Toast.LENGTH_SHORT).show();

                    return;
                }
                if (edtVisitType.getText().toString().trim().length() == 0 ) {


                    Toast.makeText(ActivityAddExpanse.this, "Please enter Visit Type", Toast.LENGTH_SHORT).show();

                    return;
                }
                if (edtAllowance.getText().toString().trim().length() == 0 ) {


                    Toast.makeText(ActivityAddExpanse.this, "Please enter Daily Allowance", Toast.LENGTH_SHORT).show();

                    return;
                }
                if (edtFare.getText().toString().trim().length() == 0 ) {


                    Toast.makeText(ActivityAddExpanse.this, "Please enter Fare in Rs.", Toast.LENGTH_SHORT).show();

                    return;
                }
                if (selectedImagePath.trim().length() == 0) {


                    Toast.makeText(ActivityAddExpanse.this, "Please add Photo of your Fare.", Toast.LENGTH_SHORT).show();

                    return;
                }

                if (!UIUtil.checkNetwork(ActivityAddExpanse.this)) {

                    UIUtil.showCustomDialog("Alert", "There seems to be some problem with your internet connection. Please check.", ActivityAddExpanse.this);
                    return;
                }



                Log.e("selectedImagePath",""+selectedImagePath);
                Log.e("selectedImagePathSt",""+selectedImagePathSt);
                if(selectedImagePathSt.trim().length()==0){
                    callApi();
                    Log.e("api", "fare");
                }
                else {
                    Log.e("api", "farestxw");
                    callApiStXe();
                }




            }
        });
    }

    private void callAddExpanseApi() {


    }


    private Map<String, String> getmap() {
        Map<String, String> map = new HashMap<>();

        map.put("nUserId", "" + sessionManager.getUserId());


        Log.e("getMap", "" + map);

        return map;
    }

    private void dispatchTakePictureIntent() {


        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {

                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                return;
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = null;
                try {
                    photoURI = FileProvider.getUriForFile(ActivityAddExpanse.this,
                            BuildConfig.APPLICATION_ID + ".provider",
                            createImageFile());

                } catch (IOException e) {
                    e.printStackTrace();
                }

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }



    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        if(REQUEST_IMAGE_CAPTURE_FOR==1){
            selectedImagePath = "file:" + image.getAbsolutePath();
        }
        else {
            selectedImagePathSt = "file:" + image.getAbsolutePath();
        }

        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            // Show the thumbnail on ImageView
            if(REQUEST_IMAGE_CAPTURE_FOR==1){
                Uri imageUri = Uri.parse(selectedImagePath);
                File file = new File(imageUri.getPath());
                try {
                    InputStream ims = new FileInputStream(file);
                    //ivpic.setImageBitmap(BitmapFactory.decodeStream(ims));
                    setFullImageFromFilePath((ImageView)this.findViewById(R.id.ivpicFare), imageUri.getPath());

                } catch (FileNotFoundException e) {
                    return;
                }
            }
            else {
                Uri imageUri = Uri.parse(selectedImagePathSt);
                File file = new File(imageUri.getPath());
                try {
                    InputStream ims = new FileInputStream(file);
                    //ivpic.setImageBitmap(BitmapFactory.decodeStream(ims));
                    setFullImageFromFilePathStXe((ImageView)this.findViewById(R.id.ivpicStXe), imageUri.getPath());

                } catch (FileNotFoundException e) {
                    return;
                }
            }


            // ScanFile so it will be appeared on Gallery

        }
    }
    private Bitmap setFullImageFromFilePath(ImageView imageView, String imageSelectedPath) {
        selectedImagePath=imageSelectedPath;
        Bitmap bitmap = BitmapLoader.downSampleBitmap(selectedImagePath, imageView);
        int imageAngle = UIUtil.getImageAngle(selectedImagePath);
        Bitmap rotateBitMap = UIUtil.rotateImage(bitmap, imageAngle);
        imageView.setImageBitmap(rotateBitMap);

        return bitmap;
    }
    private Bitmap setFullImageFromFilePathStXe(ImageView imageView, String imageSelectedPath) {
        selectedImagePathSt=imageSelectedPath;
        Bitmap bitmap = BitmapLoader.downSampleBitmap(selectedImagePathSt, imageView);
        int imageAngle = UIUtil.getImageAngle(selectedImagePathSt);
        Bitmap rotateBitMap = UIUtil.rotateImage(bitmap, imageAngle);
        imageView.setImageBitmap(rotateBitMap);

        return bitmap;
    }
    //Permission function starts from here
    private void RequestMultiplePermission() {

        // Creating String Array with Permissions.
        ActivityCompat.requestPermissions(ActivityAddExpanse.this, new String[]
                {
                        Manifest.permission.CAMERA,
                        WRITE_EXTERNAL_STORAGE
                }, RequestPermissionCode);

    }

    // Calling override method.
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case RequestPermissionCode:

                if (grantResults.length > 0) {

                    boolean CameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean WritePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (CameraPermission && WritePermission) {
                        dispatchTakePictureIntent();

                    } else {
                        Toast.makeText(ActivityAddExpanse.this, "Permission Denied", Toast.LENGTH_LONG).show();

                    }
                }

                break;
        }
    }

    // Checking permission is enabled or not using function starts from here.
    public boolean CheckingPermissionIsEnabledOrNot() {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        int sPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);


        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                sPermissionResult == PackageManager.PERMISSION_GRANTED;
    }



    private void callApiStXe() {
        Log.e("api","stxe");

        if(!UIUtil.checkNetwork(ActivityAddExpanse.this)){

            UIUtil.showCustomDialog("Alert", "There seems to be some problem with your internet connection. Please check", ActivityAddExpanse.this);
            return;
        }

        UIUtil.showDialog(ActivityAddExpanse.this);
        ApiHandler.getApiService().addExpanseFareStXe((new TypedFile("image/*", userUploadedImage())),(new TypedFile("image/*", userUploadedImageStXe())),  geteimagemapStXe(), new Callback<SimpleMessageStatusResponse>() {
            @Override
            public void success(SimpleMessageStatusResponse addexpansesResponse, Response response) {
                UIUtil.dismissDialog();
                if (addexpansesResponse == null) {
                    Toast.makeText(ActivityAddExpanse.this,"Something went wrong.Please try again later.",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (addexpansesResponse == null) {
                    Toast.makeText(ActivityAddExpanse.this,"Something went wrong.Please try again later.",Toast.LENGTH_SHORT).show();

                    return;
                }

                if (addexpansesResponse.getStatus() == null) {
                    Toast.makeText(ActivityAddExpanse.this,"Something went wrong.Please try again later.",Toast.LENGTH_SHORT).show();

                    return;
                }

                if (addexpansesResponse.getStatus() == 0) {
                    Toast.makeText(ActivityAddExpanse.this, "Something went wrong.Please try again later.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (addexpansesResponse.getStatus() == 1) {
                    Toast.makeText(ActivityAddExpanse.this, "Expanse successfully added...", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                


            }

            @Override
            public void failure(RetrofitError error) {
                UIUtil.dismissDialog();
                UIUtil.log("Retrofit Error" + error.getMessage());
            }
        });

    }

    private void callApi() {
        Log.e("api","fare");
        if(!UIUtil.checkNetwork(ActivityAddExpanse.this)){

            UIUtil.showCustomDialog("Alert", "There seems to be some problem with your internet connection. Please check", ActivityAddExpanse.this);
            return;
        }

        UIUtil.showDialog(ActivityAddExpanse.this);
        ApiHandler.getApiService().addExpanseFare((new TypedFile("image/*", userUploadedImage())), geteimagemap(), new Callback<SimpleMessageStatusResponse>() {
            @Override
            public void success(SimpleMessageStatusResponse addexpansesResponse, Response response) {
                UIUtil.dismissDialog();
                if (addexpansesResponse == null) {
                    Toast.makeText(ActivityAddExpanse.this,"Something went wrong.Please try again later.",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (addexpansesResponse == null) {
                    Toast.makeText(ActivityAddExpanse.this,"Something went wrong.Please try again later.",Toast.LENGTH_SHORT).show();

                    return;
                }

                if (addexpansesResponse.getStatus() == null) {
                    Toast.makeText(ActivityAddExpanse.this,"Something went wrong.Please try again later.",Toast.LENGTH_SHORT).show();

                    return;
                }

                if (addexpansesResponse.getStatus() == 0) {
                    Toast.makeText(ActivityAddExpanse.this, "Something went wrong.Please try again later.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (addexpansesResponse.getStatus() == 1) {
                    Toast.makeText(ActivityAddExpanse.this, "Expanse successfully added...", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }



            }

            @Override
            public void failure(RetrofitError error) {
                UIUtil.dismissDialog();
                UIUtil.log("Retrofit Error" + error.getMessage());
            }
        });

    }
    private File userUploadedImage() {
        if(selectedImagePath!=null && !selectedImagePath.isEmpty()) {
            File image = new File(selectedImagePath);
            return image;
        }
        return  null;
    }
    private File userUploadedImageStXe() {
        if(selectedImagePathSt!=null && !selectedImagePathSt.isEmpty()) {
            File image = new File(selectedImagePathSt);
            return image;
        }
        return  null;
    }


    private Map<String,String> geteimagemap() {
        Map<String, String> map = new HashMap<>();

        SessionManager sessionManager = new SessionManager(ActivityAddExpanse.this);

        map.put("nSalesPersonId", sessionManager.getUserId());
        map.put("sTravelPlace",edtTo.getText().toString().trim());
        map.put("sTravelFrom", edtFrom.getText().toString().trim());
        map.put("sTravelTo", edtTo.getText().toString().trim());
        map.put("sVisitType", edtVisitType.getText().toString().trim());
        map.put("sDailyAllowance", edtAllowance.getText().toString().trim());
        map.put("sFare", edtFare.getText().toString().trim());
        map.put("sTotal", edtExpanses.getText().toString().trim());


        Log.e("getMapfare", "" + map);

        return map;
    }
    private Map<String,String> geteimagemapStXe() {
        Map<String, String> map = new HashMap<>();

        SessionManager sessionManager = new SessionManager(ActivityAddExpanse.this);

        map.put("nSalesPersonId", sessionManager.getUserId());
        map.put("sTravelPlace",edtTo.getText().toString().trim());
        map.put("sTravelFrom", edtFrom.getText().toString().trim());
        map.put("sTravelTo", edtTo.getText().toString().trim());
        map.put("sVisitType", edtVisitType.getText().toString().trim());
        map.put("sDailyAllowance", edtAllowance.getText().toString().trim());
        map.put("sFare", edtFare.getText().toString().trim());
        if(edtStationary.getText().toString().length()>0){
            map.put("sStationery", edtStationary.getText().toString().trim());
        }
        if(edtXerox.getText().toString().length()>0){
            map.put("sXerox", edtXerox.getText().toString().trim());
        }
        map.put("sTotal", edtExpanses.getText().toString().trim());





        Log.e("getMap", "" + map);

        return map;
    }
}
