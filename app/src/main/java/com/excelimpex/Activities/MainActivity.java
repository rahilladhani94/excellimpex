package com.excelimpex.Activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.excelimpex.Model.SimpleMessageStatusResponse;
import com.excelimpex.R;
import com.excelimpex.Utils.SessionManager;
import com.excelimpex.Utils.UIUtil;
import com.excelimpex.WebServices.ApiHandler;
import com.excelimpex.application.ExcelImpex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * Created by Android on 3/17/2018.
 */

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    ImageView ivBack, ivuser;
    RelativeLayout rlNewPhoto, rlNeworder, rladdExpanse, rlTarget, rlLocation;
    TextView txtLogout, txtName, txtEmail, txtLoginWork;
    SessionManager sessionManager;
    ImageLoader imageLoader;
    Double latitude, longitude;
    String address = "";

    private int CAM_PERMISSION_CODE = 24;
    private CountDownTimer timer;
    Location mLocation;
    GoogleApiClient mGoogleApiClient;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 15000;  /* 15 secs */
    private long FASTEST_INTERVAL = 5000; /* 5 secs */

    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();

    private final static int ALL_PERMISSIONS_RESULT = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        sessionManager = new SessionManager(MainActivity.this);
        imageLoader = ImageLoader.getInstance();
        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        idMapping();
        //   callContinousGetLatLong();

        setOnClick();
    }

    private void idMapping() {

        ivBack = (ImageView) findViewById(R.id.ivBack);
        txtLogout = (TextView) findViewById(R.id.txtLogout);
        rlNewPhoto = (RelativeLayout) findViewById(R.id.rlNewPhoto);
        rlNeworder = (RelativeLayout) findViewById(R.id.rlNeworder);
        rladdExpanse = (RelativeLayout) findViewById(R.id.rladdExpanse);
        rlTarget = (RelativeLayout) findViewById(R.id.rlTarget);
        rlLocation = (RelativeLayout) findViewById(R.id.rlLocation);
        txtName = (TextView) findViewById(R.id.txtName);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtLoginWork = (TextView) findViewById(R.id.txtLoginWork);
        ivuser = (ImageView) findViewById(R.id.ivuser);
        txtName.setText(sessionManager.getKeyFirstName() + " " + sessionManager.getKeyLastName());
        txtEmail.setText(sessionManager.getKeyEmail());


        if (sessionManager.getKeyImage().length() > 0) {
            imageLoader.displayImage(sessionManager.getKeyImage(), ivuser, ExcelImpex.getUserImageDisplayOption(MainActivity.this));

        }


        setLoginWorkStatus();


//        Font.setupFont(MainActivity.this, edtPassword, Font.MyRegular);
//        Font.setupFont(MainActivity.this, edtMobile, Font.MyRegular);
//        Font.setupFont(MainActivity.this, btnSubmit, Font.MyBold);

    }

    private void setLoginWorkStatus() {
        Log.e("work", "" + sessionManager.getKeyLoginwork());
        if (sessionManager.getKeyLoginwork().length() > 0) {
            if (sessionManager.getKeyLoginwork().equalsIgnoreCase("1")) {
                txtLoginWork.setText("Logout from Work");
            } else {
                txtLoginWork.setText("Login for Work");
            }
        } else {
            txtLoginWork.setText("Login for Work");

        }
    }

    private void setOnClick() {

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                // Include dialog.xml file
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_simple_dialog_message_with_button);


                dialog.show();


                TextView btn_cancel = (TextView) dialog.findViewById(R.id.btn_cancel);
                TextView btn_ok = (TextView) dialog.findViewById(R.id.btn_ok);


                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Close dialog
                        dialog.dismiss();
                    }
                });
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Close dialog
                        dialog.dismiss();


                        sessionManager.removeUserDetail();
                        Intent i_logout = new Intent(MainActivity.this, ActivityLogin.class);
                        i_logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i_logout.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i_logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i_logout);

                    }
                });

            }
        });
        rlNeworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(MainActivity.this, ActivityNewOrder.class);
                startActivity(i);
            }
        });
        rlNewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ActivityUploadPhoto.class);
                startActivity(i);

            }
        });

        rladdExpanse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ActivityExpansesList.class);
                startActivity(i);

            }
        });

        rlTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ActivityTargetList.class);
                startActivity(i);

            }
        });
        rlLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sessionManager.getKeyLoginwork().equalsIgnoreCase("1")) {
                    Intent i = new Intent(MainActivity.this, ActivityLocationUpdate.class);
                    startActivity(i);
                } else {
                    Toast.makeText(MainActivity.this, "Please login for Work to send location update", Toast.LENGTH_SHORT).show();
                }


            }
        });
        txtLoginWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sessionManager.getKeyLoginwork().equalsIgnoreCase("1")) {
                    callLoginWorkApi("0");
                } else {
                    callLoginWorkApi("1");
                }

            }
        });


    }

    private void callLoginWorkApi(final String signinstatus) {

        UIUtil.showDialog(MainActivity.this);

        Map<String, String> map = new HashMap<>();
        final SessionManager sessionManager = new SessionManager(MainActivity.this);
        map.put("nSalesPersonId", sessionManager.getUserId());

        map.put("is_signin", "" + signinstatus);
        ApiHandler.getApiService().signinWork(map, new Callback<SimpleMessageStatusResponse>() {


            @Override
            public void success(SimpleMessageStatusResponse mainResponse, Response response) {


                UIUtil.dismissDialog();

                if (mainResponse == null) {

                    Toast.makeText(MainActivity.this, "Something went wrong...Please try again later.", Toast.LENGTH_SHORT).show();

                    return;
                }


                if (sessionManager.getKeyLoginwork().equalsIgnoreCase("1")) {
                    sessionManager.setKeyLoginwork("0");
                } else {
                    sessionManager.setKeyLoginwork("1");
                }
                setLoginWorkStatus();


                Log.e("success", "success");


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


    private String getCompleteAddressString() {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                address = strAdd;
                if (sessionManager.getKeyLoginwork().equalsIgnoreCase("1")) {
                    callLocationUpdateApi();
                }


                Log.e("address", strReturnedAddress.toString());
            } else {
                Log.e("address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("address", "Cannot get Address!");
        }

        return strAdd;
    }

    private void callLocationUpdateApi() {


        ApiHandler.getApiService().sendLocationUpdate(getmap(), new Callback<SimpleMessageStatusResponse>() {


            @Override
            public void success(SimpleMessageStatusResponse mainResponse, Response response) {


                if (mainResponse == null) {

                    //   Toast.makeText(MainActivity.this, "Something went wrong...Please try again later.", Toast.LENGTH_SHORT).show();


                    return;
                }


                if (mainResponse.getStatus() == null) {

                    //    Toast.makeText(MainActivity.this, "Something went wrong...Please try again later.", Toast.LENGTH_SHORT).show();


                    return;
                }


                if (mainResponse.getStatus() == 0) {

                    //  Toast.makeText(MainActivity.this, "" + mainResponse.getMsg(), Toast.LENGTH_SHORT).show();


                    return;
                }


                if (mainResponse.getStatus() == 1) {

                    Log.e("success", "success");


                }

            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                error.getMessage();
                Log.e("error", "" + error.getMessage());

            }
        });

    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!checkPlayServices()) {
            Toast.makeText(MainActivity.this, "Please install Google Play services.", Toast.LENGTH_SHORT).show();
            //latLng.setText("Please install Google Play services.");
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {


        getLocation();


    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);


        if (mLocation != null) {

            latitude = mLocation.getLatitude();
            longitude = mLocation.getLongitude();
            getCompleteAddressString();

            timer = new CountDownTimer(4000, 4000) {

                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    try {
                        latitude = mLocation.getLatitude();
                        longitude = mLocation.getLongitude();
                        getCompleteAddressString();
                        timer.start();
                    } catch (Exception e) {
                        Log.e("Error", "Error: " + e.toString());
                    }
                }
            }.start();
            //latLng.setText("Latitude : "+mLocation.getLatitude()+" , Longitude : "+mLocation.getLongitude());
        } else {
            Toast.makeText(MainActivity.this, "Please enable Location Access from your device..", Toast.LENGTH_SHORT).show();
        }

        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        if (location != null) {

            latitude = location.getLatitude();
            longitude = location.getLongitude();
            // getCompleteAddressString();
        }
        //latLng.setText("Latitude : "+location.getLatitude()+" , Longitude : "+location.getLongitude());


    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else
                finish();

            return false;
        }
        return true;
    }

    protected void startLocationUpdates() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Enable Permissions", Toast.LENGTH_LONG).show();
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);


    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }
                if(permissionsRejected.size()==0){
                    getLocation();
                }
                Log.e("ss",""+permissionsRejected.size());


                break;
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
    }


    public void stopLocationUpdates() {
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi
                    .removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    private Map<String, String> getmap() {
        Map<String, String> map = new HashMap<>();
        SessionManager sessionManager = new SessionManager(MainActivity.this);
        map.put("nSalesPersonId", sessionManager.getUserId());

        map.put("sLat", "" + latitude);
        map.put("sLong", "" + longitude);
        map.put("sAddress", "" + address);


        Log.e("getMap", "" + map);

        return map;
    }


}

