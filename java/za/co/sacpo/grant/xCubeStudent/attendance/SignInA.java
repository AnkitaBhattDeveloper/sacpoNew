package za.co.sacpo.grant.xCubeStudent.attendance;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.fragments.FMap;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.LocationUpdatesService;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsGrantSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsStudentSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeStudent.SDashboardDA;

public class SignInA extends BaseFormAPCPrivate implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private String ActivityId = "S107";
    public View mProgressView, mContentView,heading;
    private TextView tvII, lblView, txtiPunchOut, txtPunchOutDate, txtiPunchInDone, iDate, iTime, iDistanceFrmWorkstation, iWorkstation, lblGpsCordinate, iLat, iLong, gps_error_po, gps_error_pi;
    private GoogleMap googleMap;
    private FusedLocationProviderClient mFusedLocationClient;
    public Location currentLocation;
    int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1;
    private Boolean permissionLocation;
    public Double currentLat;
    public Double currentLong;
    private Location myCurrentLocation;
    public LocationManager locationManager ;
    private LinearLayout LLiPunchOutContainer, LLiPunchInContainer, LLinformationContainer, LLiPunchInDoneContainer, LLII;
    private Button btnPunchIn, btnPunchOut, btnTryAgain;
    private String KEY_LAT = "latitude";
    private String KEY_LONG = "longitude";
    private String generator;
    private boolean gps_enabled;
    private boolean network_enabled;
    private SignInA thisClass;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    //private SignInA.MyReceiver myReceiver;
    SupportMapFragment mapFragment ;
    private boolean mBound = false;
    private LocationUpdatesService mService = null;
    private Button mRequestLocationUpdatesButton;
    private Button mRemoveLocationUpdatesButton;
    private GoogleApiClient mGoogleApiClient;
    private static final int REQUEST_LOCATION = 1;
    double latitude, longitude;
    Location mLastLocation;
    private LocationRequest mLocationRequest;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    Geocoder geocoder;
    List<Address> addresses;
    boolean GpsStatus ;
    TextView text_internet_info_head;




    @Override
    protected void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cAId) {
        baseApcContext = cnt;
        CAId = cAId;
        activityIn = ain;
        LogTag = lt;
        thisClass = this;
        printLogs(lt, "setBaseApcContextParent", "weAreIn");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Bundle outBundle = getIntent().getExtras();
        //generator = outBundle.getString("generator");
        network_enabled = false;
        gps_enabled = false;
       // myReceiver = new SignInA.MyReceiver();
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(), this, this.getClass().getSimpleName(), ActivityId);
        printLogs(LogTag, "onCreate", "init");
       ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();

          mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        bootStrapInit();
    }
    @Override
    protected void bootStrapInit() {
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext, activityIn);
        if (isConnected) {
            printLogs(LogTag, "bootStrapInit", "initConnected");
            setLayoutXml();
            callFooter(baseApcContext, activityIn, ActivityId);
            initMenusCustom(ActivityId, baseApcContext, activityIn);
            fetchVersionData();
            verifyVersion();
            internetChangeBroadCast();
            initializeViews();
            showProgress(true,mContentView,mProgressView);
            initBackBtn();
            initializeLabels();
            initializeListeners();
            userToken = userSessionObj.getToken();
            syncToken(baseApcContext, activityIn);
            if (TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
            callDataApi();
            CheckGpsStatus();
          //  initializeInputs();
            //fetchData();
            printLogs(LogTag, "bootStrapInit", "exitConnected");
        }
    }
    public void CheckGpsStatus(){
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(GpsStatus) {
            setButtonsState(true);
        } else {
            setButtonsState(false);
        }
    }
    @Override
    protected void setLayoutXml() {
        printLogs(LogTag, "setLayoutXml", "a_sign_in");
        setContentView(R.layout.a_sign_in);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag, "initializeViews", "init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        heading = findViewById(R.id.heading);
        text_internet_info_head = findViewById(R.id.text_internet_info_head);
        gps_error_po = findViewById(R.id.gps_error_po);
        gps_error_pi = findViewById(R.id.gps_error_pi);

        btnPunchIn = (Button) findViewById(R.id.btnPunchIn);
        btnPunchOut = (Button) findViewById(R.id.btnPunchOut);

        LLiPunchOutContainer = (LinearLayout) findViewById(R.id.iPunchOutContainer);
        LLiPunchInContainer = (LinearLayout) findViewById(R.id.iPunchInContainer);
        LLinformationContainer = (LinearLayout) findViewById(R.id.informationContainer);
        LLiPunchInDoneContainer = (LinearLayout) findViewById(R.id.iPunchInDoneContainer);

        lblGpsCordinate = findViewById(R.id.lblGpsCordinate);
        iLat = findViewById(R.id.iLat);
        iLong = findViewById(R.id.iLong);
        txtiPunchInDone = (TextView) findViewById(R.id.iPunchInDone);
        txtPunchOutDate = (TextView) findViewById(R.id.txtPunchOutDate);

        iDate = (TextView) findViewById(R.id.iDate);
        iTime = (TextView) findViewById(R.id.iTime);
        iDistanceFrmWorkstation = (TextView) findViewById(R.id.iDistanceFrmWorkstation);
        iWorkstation = (TextView) findViewById(R.id.iWorkstation);

        LLII = (LinearLayout) findViewById(R.id.internet_info);
        LLII.setVisibility(View.GONE);
        tvII = (TextView) findViewById(R.id.text_internet_info);
        btnTryAgain = findViewById(R.id.btnTryAgain);
        mRequestLocationUpdatesButton = (Button) findViewById(R.id.request_location_updates_button);
        mRemoveLocationUpdatesButton = (Button) findViewById(R.id.remove_location_updates_button);
        printLogs(LogTag, "initializeViews", "exit");
    }

    @Override
    protected void initializeListeners() {
        printLogs(LogTag, "initializeListeners", "init");
        btnPunchIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });
        btnPunchOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inBundle = new Bundle();
                Intent intent = new Intent(SignInA.this, SignOutA.class);
                inBundle.putString("generator", ActivityId);
                intent.putExtras(inBundle);
                startActivity(intent);
                finish();
            }
        });


        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
            }
        });


        mRequestLocationUpdatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printLogs(LogTag, "mRequestLocationUpdatesButton", "init "+checkPermissions());
               // turnGPSOn();
                enableLoc();
              //  setButtonsState(true);
            }
        });
    }
    private void enableLoc() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(SignInA.this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {

                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            mGoogleApiClient.connect();
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {

                            //Log.d("Location error","Location error " + connectionResult.getErrorCode());
                        }
                    }).build();
            mGoogleApiClient.connect();
        }

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(SignInA.this, REQUEST_LOCATION);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                }
            }
        });
    }

    @Override
    protected void initializeInputs() {
        printLogs(LogTag, "initializeInputs", "init");
        studentSessionObj = new OlumsStudentSession(baseApcContext);
        boolean isGrantActive = studentSessionObj.getIsActiveGrant();
        //isGrantActive = false;
        if (isGrantActive) {
            printLogs(LogTag, "initializeInputs", "init");
            grantSessionObj = new OlumsGrantSession(baseApcContext);
            String grantId = grantSessionObj.getGrantId();
            int grantIdInt = 0;
            if (!TextUtils.isEmpty(grantId)) {
                grantIdInt = Integer.parseInt(grantId);
            }
            if (grantIdInt > 0) {
                LLinformationContainer.setVisibility(View.GONE);
                initializeMap();
                fetchData();
                checkPermissions();
            } else {
                LLinformationContainer.setVisibility(View.VISIBLE);
            }
        }
    }
    public void initializeMap() {
        printLogs(LogTag,"initializeMap","init");
        if (googleMap == null) {
            mapFragment = (FMap) getSupportFragmentManager().findFragmentById(R.id.map);
            if (mapFragment != null) {
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override

                    public void onMapReady(GoogleMap googleMapI)
                    {
                        googleMap = googleMapI;
                        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        googleMap.getUiSettings().setZoomControlsEnabled(true);

                        final ScrollView mScrollView = findViewById(R.id.scroll_layout); //parent scrollview in xml, give your scrollview id value
                        ((FMap) getSupportFragmentManager().findFragmentById(R.id.map)).setListener(new FMap.OnTouchListener() {
                            @Override
                            public void onTouch() {
                                mScrollView.requestDisallowInterceptTouchEvent(true);
                            }
                        });
                    }
                });
            }
        }
        MapsInitializer.initialize(getApplicationContext());
    }
    private boolean checkPermissions() {
        gps_enabled = PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        LocationManager lm = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        printLogs(LogTag,"checkPermissions","gps_enabled"+gps_enabled);
        if(gps_enabled ==false || network_enabled ==false) {
            LLII.setVisibility(View.VISIBLE);
        }
        else {
            LLII.setVisibility(View.GONE);
        }
        return gps_enabled;

    }
    @Override
    protected void initializeLabels() {
        printLogs(LogTag, "initializeLabels", "init");
        String Label = getLabelFromDb("i_S107_sign_out_pre", R.string.i_S107_sign_out_pre);
        lblView = (TextView) findViewById(R.id.iPunchOutPre);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("i_S107_sign_out_post", R.string.i_S107_sign_out_post);
        lblView = (TextView) findViewById(R.id.iPunchOutPost);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S107_sign_out_date", R.string.lbl_S107_sign_out_date);
        lblView = (TextView) findViewById(R.id.lblPunchOutDate);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S107_gps_coordinate", R.string.lbl_S107_gps_coordinate);
        lblGpsCordinate.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblGpsCordinate.setText(Label);

        Label = getLabelFromDb("b_S107_sign_in", R.string.b_S107_sign_in);
        btnPunchIn.setText(Label);

        Label = getLabelFromDb("error_gps", R.string.error_gps);
        gps_error_pi.setText(Label);
        gps_error_po.setText(Label);

        Label = getLabelFromDb("h_S107", R.string.h_S107);
        lblView = (TextView) findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("b_S107_sign_out", R.string.b_S107_sign_out);
        btnPunchOut.setText(Label);

        Label = getLabelFromDb("i_no_active_grant", R.string.i_no_active_grant);
        lblView = (TextView) findViewById(R.id.iNoActiveGrant);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("l_S107_distance_from_workstation", R.string.l_S107_distance_from_workstation);
        lblView = (TextView) findViewById(R.id.lblDistanceFrmWorkstation);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("l_S107_workstation", R.string.l_S107_workstation);
        lblView = (TextView) findViewById(R.id.lblWorkstation);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("l_103_date", R.string.l_103_date);
        lblView = (TextView) findViewById(R.id.lblDate);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("l_103_time", R.string.l_103_time);
        lblView = (TextView) findViewById(R.id.lblTime);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        tvII.setText(getApplicationContext().getString(R.string.error_enable_location));



        text_internet_info_head.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        tvII.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        txtPunchOutDate.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        iDate.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        iTime.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        iDistanceFrmWorkstation.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        iWorkstation.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        iLat.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        iLong.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        txtiPunchInDone.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            btnTryAgain.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            btnPunchOut.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            btnPunchIn.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
          //  txtiPunchInDone.setBackground(getDrawable(getDrwabaleResourceId("themed_small_button")));
           // txtiPunchInDone.setTextColor(R.);
         }

    }


    private void initBackBtn() {
        printLogs(LogTag, "initBackBtn", "init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void callDataApi() {
        printLogs(LogTag, "callDataApi", "init");
        userToken = userSessionObj.getToken();
        if (!TextUtils.isEmpty(userToken)) {
            printLogs(LogTag, "callDataApi", "Token Update");
            syncUserData(baseApcContext, activityIn);
            syncStudentData(baseApcContext, activityIn);
            syncGrantData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn, ActivityId);
        }
    }
    private void fetchData() {
        final String currentLatS = String.valueOf(currentLat);
        final String currentLongS = String.valueOf(currentLong);
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.REF_S_107+ "?token=" + token ;
        printLogs(LogTag, "fetchData", "URL : " + FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchData", "response : " + response);
                    String Status = jsonObject.getString(KEY_STATUS);
                    if (Status.equals("1")) {
                        JSONObject dataM = jsonObject.getJSONObject(KEY_DATA);
                        String punchInShow = dataM.getString("punch_in_status");
                        String current_time = dataM.getString("current_time");
                        String current_date = dataM.getString("current_date");
                        String distance_location = dataM.getString("distance_from_location");
                        String date_pending = dataM.getString("date_pending");
                        String workstation = dataM.getString("workstation_location");

                        String wLat = dataM.getString("workstation_lat");
                        String wLon = dataM.getString("workstation_long");

                        if (!wLat.isEmpty()) {
                            if (!wLon.isEmpty()) {
                                    if (!wLat.equals("null")) {
                                            if (!wLon.equals("null")) {
                                                double workLat = Double.parseDouble(wLat);
                                                double workLong = Double.parseDouble(wLon);
                                                LatLng point = new LatLng(workLat, workLong);
                                                CircleOptions circleOptions = new CircleOptions()
                                                        .center(point)
                                                        .radius(200)
                                                        .fillColor(Color.GREEN)
                                                        .strokeColor(Color.BLACK)
                                                        .strokeWidth(5);
                                                googleMap.addCircle(circleOptions);
                                                LatLng loginLatLong = new LatLng(workLat, workLong);
                                                //googleMap.moveCamera(CameraUpdateFactory.newLatLng(loginLatLong));
                                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loginLatLong, 15f));
                                                googleMap.getMaxZoomLevel();
                                            }
                                    }

                            }
                        }
                        txtPunchOutDate.setText(date_pending);
                        iTime.setText(current_time);
                        iDate.setText(current_date);
                        iDistanceFrmWorkstation.setText(distance_location);
                        iWorkstation.setText(workstation);

                        if (punchInShow.equals("1")) {
                            //getLocation();
                            enablePunchInActions(currentLatS, currentLongS, currentLat, currentLong);
                        } else if (punchInShow.equals("2")) {
                            disableActions(currentLatS, currentLongS, currentLat, currentLong);
                        } else {
                            enablePunchOutActions(currentLatS, currentLongS, currentLat, currentLong);
                        }
                    } else {
                        //showProgress(false,mContentView,mProgressView);
                        printLogs(LogTag, "fetchData", "error_try_again : DATA_ERROR");
                        String sTitle = "Error :" + ActivityId + "-100";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    printLogs(LogTag, "fetchData", "error_try_again : " + e.getMessage());
                    e.printStackTrace();
                    //showProgress(false,mContentView,mProgressView);
                    String sTitle = "Error :" + ActivityId + "-101";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        printLogs(LogTag, "fetchData", "error_try_again : " + error.getMessage());
                        //showProgress(false,mContentView,mProgressView);
                        String sTitle = "Error :" + ActivityId + "-102";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "*/*");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    private void enablePunchInActions(String currentLatS, String currentLongS, Double currentLat, Double currentLong) {
        if ((currentLat != null) && (currentLong != null)) {
           createMarker(currentLat, currentLong, "SIGN-IN", "", BitmapDescriptorFactory.HUE_AZURE);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLat, currentLong), 5));


        }
        LLiPunchInContainer.setVisibility(View.VISIBLE);
        LLiPunchOutContainer.setVisibility(View.GONE);
        LLiPunchInDoneContainer.setVisibility(View.GONE);
       // checkAllActions(currentLatS, currentLongS, currentLat, currentLong);
        printLogs(LogTag, "enablePunchInActions", "init");
        showProgress(false, mContentView, mProgressView);

    }
    protected Marker createMarker(double latitude, double longitude, String title, String snippet, float iconResID) {
        printLogs(LogTag, "createMarker", "INIT" + latitude + "---" + longitude);
        int height = 100;
        int width = 100;
        BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.location_marker);
        return googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .title(title)
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(bitmapdraw.getBitmap(), width, height, false))));

    }

     private void enablePunchOutActions(String currentLatS, String currentLongS, Double currentLat, Double currentLong) {
        LLiPunchInContainer.setVisibility(View.GONE);
        LLiPunchInDoneContainer.setVisibility(View.GONE);
        LLiPunchOutContainer.setVisibility(View.VISIBLE);
       // checkAllActions(currentLatS, currentLongS, currentLat, currentLong);
        printLogs(LogTag, "enablePunchOutActions", "init");
        showProgress(false, mContentView, mProgressView);
    }
    private void disableActions(String currentLatS, String currentLongS, Double currentLat, Double currentLong) {
        if ((currentLat != null) && (currentLong != null)) {
            createMarker(currentLat, currentLong, "SIGN-IN", "", BitmapDescriptorFactory.HUE_AZURE);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLat, currentLong), 5));
        }
        printLogs(LogTag, "disableActions", "init");
        LLiPunchInContainer.setVisibility(View.GONE);
        LLiPunchInDoneContainer.setVisibility(View.VISIBLE);
        String Label = getLabelFromDb("i_S107_already_signin", R.string.i_S107_already_signin);
        txtiPunchInDone.setText(Label);
        btnPunchIn.setVisibility(View.GONE);
        LLiPunchOutContainer.setVisibility(View.GONE);
        showProgress(false, mContentView, mProgressView);
    }
    private void setButtonsState(boolean requestingLocationUpdates) {
        if (requestingLocationUpdates) {
            btnPunchIn.setVisibility(View.VISIBLE);
            mRequestLocationUpdatesButton.setVisibility(View.GONE);
            mRemoveLocationUpdatesButton.setVisibility(View.GONE);
        } else {
            mRequestLocationUpdatesButton.setVisibility(View.VISIBLE);
            mRemoveLocationUpdatesButton.setVisibility(View.GONE);
            btnPunchIn.setVisibility(View.GONE);
        }
    }
    @Override
    protected void verifyVersion() {
        printLogs(LogTag, "verifyVersion", "init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag, "verifyVersion", "isUpdate " + isUpdate);
        printLogs(LogTag, "verifyVersion", "isUpdate " + utilSessionObj.getIsUpdateRequired());
        if (isUpdate) {
            Intent intent = new Intent(SignInA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void fetchVersionData() {
        printLogs(LogTag, "fetchVersionData", "exitConnected");
        syncUpdates(baseApcContext, activityIn);
    }

    @Override
    protected void internetChangeBroadCast() {
        printLogs(LogTag, "internetChangeBroadCast", "init");
        registerBroadcastIC();
    }



    @Override
    protected void validateForm() {
        printLogs(LogTag, "validateForm", "init");
        boolean cancel = false;
        if (!isValidate(currentLat, currentLong)) {
            cancel = true;
        }
        if (cancel) {
            return;
        } else {
            showProgress(true, mContentView, mProgressView);
            this.FormSubmit();
        }
        printLogs(LogTag, "validateForm", "exit");
    }
    public boolean isValidate(double lat, double lng) {
        printLogs(LogTag, "isValidate", "DATA : " + lat + " -- " + lng);
        if (lat < -90 || lat > 90) {
            String sTitle = "Error :" + ActivityId + "-106";
            String sMessage = getLabelFromDb("error_location", R.string.error_location);
            String sButtonLabelClose = "Close";
            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
            return false;
        } else if (lng < -180 || lng > 180) {
            String sTitle = "Error :" + ActivityId + "-106";
            String sMessage = getLabelFromDb("error_location", R.string.error_location);
            String sButtonLabelClose = "Close";
            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
            return false;
        }
        return true;
    }
    @Override
    protected void FormSubmit() {
        final String dataLat = Double.toString(currentLat);
        final String dataLong = Double.toString(currentLong);
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.REF_S_107_SAVE;

        JSONObject object = new JSONObject();
        try {
            object.put(KEY_LAT,dataLat);
            object.put(KEY_LONG,dataLong);
            object.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        printLogs(LogTag, "FormSubmit", "URL : " + FINAL_URL);
        printLogs(LogTag, "FormSubmit", "object : " + object);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, FINAL_URL, object, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "FormSubmit", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if (Status.equals("1")) {
                        /// Show go back data
                        //disableActions();
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = getLabelFromDb("m_S107_title", R.string.m_S107_title);
                        String sMessage = getLabelFromDb("m_S107_message", R.string.m_S107_message);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showSuccessDialogSignInAA(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose, thisClass);
                    } else {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-107";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "-108";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-109";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "*/*");
                return params;
            }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }
    public void customRedirector(){
        Intent intent = new Intent(SignInA.this, SDashboardDA.class);
        Bundle activeUri = new Bundle();
        activeUri.putString("generator","S107");
        intent.putExtras(activeUri);
        startActivity(intent);
        finish();
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(100000); // Update location every second
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this::onLocationChanged);
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            geocoder = new Geocoder(this, Locale.getDefault());
            latitude = mLastLocation.getLatitude();
            currentLat = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
            currentLong = mLastLocation.getLongitude();
          //  submituserlatlong(latitude,longitude);
            iLat.setText("LAT :: " + latitude);
            iLong.setText("LONG :: " + longitude);
            if(currentLat !=null && currentLong != null){
                initializeInputs();
            }else{
                Toast.makeText(thisClass, "Something Went Wrong !!", Toast.LENGTH_SHORT).show();
            }




            //   enablePunchInActions("latitude", "longitude", latitude, longitude);
           // createMarker(latitude, longitude, "SIGN-IN", "", BitmapDescriptorFactory.HUE_AZURE);

            printLogs(LogTag, "onConnected", "=======================================" );
            printLogs(LogTag, "onConnected", "latitude : " + latitude);
            printLogs(LogTag, "onConnected", "longitude : " + longitude);
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
             /*   String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();*/
              //  tv_address.setText("Address : "+address);
                //Log.i("mLastLocation", address);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Log.d("locationlat",""+latitude+","+longitude);
        }else{
            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(SignInA.this);
            alertDialog2.setTitle("GPS Disabled");
          //  alertDialog2.setIcon(R.drawable.ic_baseline_location_off_24);
            alertDialog2.setPositiveButton("Enable",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    });
            alertDialog2.setNegativeButton("Refresh",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            inBundle = new Bundle();
                            Intent intent = new Intent(SignInA.this, SignInA.class);
                            inBundle.putString("generator", "S104");
                            intent.putExtras(inBundle);
                            startActivity(intent);
                            finish();
                        }
                    });
            alertDialog2.setCancelable(false);
            alertDialog2.show();
        }
    }
    public void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }
    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        printLogs(LogTag, "onOptionsItemSelected", "init");
        if (item.getItemId() == android.R.id.home) {

            //backRedirectHandler(baseApcContext,activityIn,generator);
            Intent intent = new Intent(SignInA.this, SCurrentAttDA.class);
            printLogs(LogTag, "onOptionsItemSelected", "SCurrentAttDA");
            startActivity(intent);
            finish();
        }
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        checkInternetConnection();
        mGoogleApiClient.connect();
        registerBroadcastIC();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mGoogleApiClient.disconnect();
        unregisterBroadcastIC();

    }


    @Override
    protected void onStart() {
        super.onStart();
        checkInternetConnection();
        registerBroadcastIC();
        mGoogleApiClient.connect();
    }

    @Override
    public void onDestroy() {
        mGoogleApiClient.disconnect();
        unregisterBroadcastIC();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(LocationSettingsStatusCodes.RESOLUTION_REQUIRED == 6){

            if(resultCode == 0){
                setButtonsState(false);
            }else{
                setButtonsState(true);
            }

            printLogs(LogTag, "onActivityResult", "requestCode "+requestCode);
            printLogs(LogTag, "onActivityResult", "requestCode "+resultCode);
            printLogs(LogTag, "onActivityResult", "requestCode "+data);
        }
    }
}
