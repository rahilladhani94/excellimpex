package com.excelimpex.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.excelimpex.BuildConfig;
import com.excelimpex.Model.SimpleMessageStatusResponse;
import com.excelimpex.R;
import com.excelimpex.Utils.BitmapLoader;
import com.excelimpex.Utils.RequestManager;
import com.excelimpex.Utils.SessionManager;
import com.excelimpex.Utils.UIUtil;
import com.excelimpex.WebServices.ApiHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

import retrofit.mime.TypedFile;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;
import static android.Manifest.permission_group.STORAGE;

/**
 * Created by Android on 3/17/2018.
 */

public class ActivityUploadPhoto extends AppCompatActivity {

    ImageView ivBack,ivpic;
    TextView txtClick;
    private int CAMERA_PERMISSION_CODE = 24;
    private Uri fileUri;
    private String selectedImagePath="";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int RequestPermissionCode = 7;
    RelativeLayout rlUpload;
    Button btnSubmit;
    public static  String s= "s";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadphoto);
        getSupportActionBar().hide();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        idMapping();
        setOnClick();
    }

    private void idMapping() {

        ivBack = (ImageView)findViewById(R.id.ivBack);
        ivpic= (ImageView)findViewById(R.id.ivpic);
        txtClick = (TextView) findViewById(R.id.txtClick);
        rlUpload = (RelativeLayout) findViewById(R.id.rlUpload);
        btnSubmit = (Button)findViewById(R.id.btnSubmit);



//        Font.setupFont(ActivityUploadPhoto.this, edtPassword, Font.MyRegular);
//        Font.setupFont(ActivityUploadPhoto.this, edtMobile, Font.MyRegular);
//        Font.setupFont(ActivityUploadPhoto.this, btnSubmit, Font.MyBold);

    }
    private void setOnClick() {

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

                
            }
        });

        rlUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(CheckingPermissionIsEnabledOrNot())
                {
                    dispatchTakePictureIntent() ;              
                }

          
                else {

                    
                    RequestMultiplePermission();

                }
                

            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedImagePath.trim().length() == 0) {


                    Toast.makeText(ActivityUploadPhoto.this, "Please add Photo of your visit.", Toast.LENGTH_SHORT).show();

                    return;
                }

               callApi();

               // uploadDetails();
            }
        });


    }




    private void uploadDetails() {


        UIUtil.showDialog(ActivityUploadPhoto.this);
//        SimpleMultiPartRequest savecardRequest = new SimpleMultiPartRequest(Request.Method.POST, "http://www.excelimpex.in/backend/api/api/storevisit",
//                new com.android.volley.Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        UIUtil.dismissDialog();
//                        Log.e("SaveCardResponse",response.toString());
//                    }
//                }, new com.android.volley.Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        });
//      //  SessionManager sessionManager = new SessionManager(ActivityUploadPhoto.this);
//
//
//        savecardRequest.addStringParam("nSalesPersonId", "5");
//        savecardRequest.addFile("sImage", selectedImagePath);
//
//        RequestManager.getnstance(ActivityUploadPhoto.this).add(savecardRequest);



        SimpleMultiPartRequest signupRequest = new SimpleMultiPartRequest(Request.Method.POST,"http://www.excelimpex.in/backend/api/api/storevisit",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.e("SignupResponse",response.toString());

                        UIUtil.dismissDialog();                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        signupRequest.addFile("sImage", selectedImagePath);
        signupRequest.addStringParam("nSalesPersonId", "5");

        RequestManager.getnstance(this).add(signupRequest);


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
                    photoURI = FileProvider.getUriForFile(ActivityUploadPhoto.this,
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
        selectedImagePath = "file:" + image.getAbsolutePath();
        return image;
    }
    private boolean isDeviceSupportCamera(Context context) {
        if (context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // This device has a camera
            return true;
        } else {
            // no camera on this device
            Toast.makeText(context, "This device does not have a camera.", Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Show the thumbnail on ImageView
            Uri imageUri = Uri.parse(selectedImagePath);
            File file = new File(imageUri.getPath());
            try {
                InputStream ims = new FileInputStream(file);
                //ivpic.setImageBitmap(BitmapFactory.decodeStream(ims));

                setFullImageFromFilePath((ImageView)this.findViewById(R.id.ivpic), imageUri.getPath());

            } catch (FileNotFoundException e) {
                return;
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
    //Permission function starts from here
    private void RequestMultiplePermission() {

        // Creating String Array with Permissions.
        ActivityCompat.requestPermissions(ActivityUploadPhoto.this, new String[]
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

                    if (CameraPermission && WritePermission ) {
                        dispatchTakePictureIntent() ;

                    }
                    else {
                        Toast.makeText(ActivityUploadPhoto.this,"Permission Denied",Toast.LENGTH_LONG).show();

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
                sPermissionResult == PackageManager.PERMISSION_GRANTED ;
    }


    private void callApi() {

        if(!UIUtil.checkNetwork(ActivityUploadPhoto.this)){

            UIUtil.showCustomDialog("Alert", "There seems to be some problem with your internet connection. Please check", ActivityUploadPhoto.this);
            return;
        }

        UIUtil.showDialog(ActivityUploadPhoto.this);
        ApiHandler.getApiService().addStoreVisit((new TypedFile("image/*", userUploadedImage())), geteimagemap(), new Callback<SimpleMessageStatusResponse>() {
            @Override
            public void success(SimpleMessageStatusResponse addexpansesResponse, retrofit.client.Response response) {
                UIUtil.dismissDialog();
                if (addexpansesResponse == null) {
                    Toast.makeText(ActivityUploadPhoto.this,"Something went wrong.Please try again later.",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (addexpansesResponse == null) {
                    Toast.makeText(ActivityUploadPhoto.this,"Something went wrong.Please try again later.",Toast.LENGTH_SHORT).show();

                    return;
                }

                if (addexpansesResponse.getStatus() == null) {
                    Toast.makeText(ActivityUploadPhoto.this,"Something went wrong.Please try again later.",Toast.LENGTH_SHORT).show();

                    return;
                }

                if (addexpansesResponse.getStatus() == 0) {
                    Toast.makeText(ActivityUploadPhoto.this, "Something went wrong.Please try again later.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (addexpansesResponse.getStatus() == 1) {
                    Toast.makeText(ActivityUploadPhoto.this, "Visit successfully added...", Toast.LENGTH_SHORT).show();
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


    private Map<String,String> geteimagemap() {
        Map<String, String> map = new HashMap<>();

        SessionManager sessionManager = new SessionManager(ActivityUploadPhoto.this);

        map.put("nSalesPersonId", sessionManager.getUserId());
      




        Log.e("getMap", "" + map);

        return map;
    }




}
