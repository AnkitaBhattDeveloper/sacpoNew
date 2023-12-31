package za.co.sacpo.grantportal.xCubeStudent.attendance;

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
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
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
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Granularity;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.Priority;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import za.co.sacpo.grantportal.AppUpdatedA;
import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.fragments.FMap;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grantportal.xCubeLib.component.LocationUpdatesService;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.component.Utils;
import za.co.sacpo.grantportal.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsGrantSession;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsStudentSession;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grantportal.xCubeStudent.SDashboardDA;

public class SignOutA extends BaseFormAPCPrivate implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,LocationListener {

    private String ActivityId = "S108";
    public View mProgressView, mContentView,heading;
    private TextView tvII, lblView, txtiPunchOut, txtiPunchIn, txtiPunchOutDone, iDate, iDateOld, iTime, iDistanceFrmWorkstation, iWorkstation, txtPunchOutDate, txtiPunchOutPre, txtiPunchOutPost, lblGpsCordinate, iLat, iLong, gps_error_po, gps_error_pi;
    public GoogleMap googleMap;
    private String KEY_TIME = "punchout_time";
    private String KEY_LAT = "latitude";
    private String KEY_LONG = "longitude";
    private String KEY_S_A_ID = "s_a_id";
    private FusedLocationProviderClient mFusedLocationClient;
    int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1;
    private Boolean permissionLocation;
    private Location myCurrentLocation;
    private Double currentLat;
    private Double currentLong;
    private LinearLayout LLII, LLiPunchOutDoneContainer, LLiPunchOutContainer, LLiPunchInContainer, LLiTimeContainer, LLinformationContainer, LLpunchOutPostContainer, LLiPunchOutInfoContainer, LLiPunchOutMapContainer;
    private Button btnPunchOut, btnPunchIn, btnTryAgain;
    private String OldDate;
    private String OldId = "";
    public TimePicker inputPunchOutTime;
    private String generator;
    private boolean gps_enabled;
    private boolean network_enabled;
    private SignOutA thisClass;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    //private SignOutA.MyReceiver myReceiver;
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
    public LocationManager locationManager ;
    boolean GpsStatus ;
    TextView text_internet_info_head;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cAId) {
        baseApcContext = cnt;
        CAId = cAId;
        activityIn = ain;
        LogTag = lt;
        thisClass = this;
        printLogs(lt, "setBaseApcContextParent", "weAreIn");

        Log.e("TAG", "setBaseApcContextParent: called ");

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        network_enabled = false;
        gps_enabled = false;
        //myReceiver = new SignOutA.MyReceiver();
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(), this, this.getClass().getSimpleName(), ActivityId);
        printLogs(LogTag, "onCreate", "init");
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        Bundle outBundle = getIntent().getExtras();
//        generator = outBundle.getString("generator");

        Log.e("TAG", "onCreate: called ");




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
            showProgress(true, mContentView, mProgressView);
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
            initializeInputs();
           // fetchData();
            printLogs(LogTag, "onCreate", "exitConnected");

            Log.e("TAG", "bootstrapinit: called ");


        }
    }

    public void CheckGpsStatus(){
        Log.e("TAG", "checkGpsStatus: called ");

        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        assert locationManager != null;
        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(GpsStatus) {
            setButtonsState(true);
        } else {
            setButtonsState(false);
        }
    }
    private void initBackBtn() {
        Log.e("TAG", "back btn: called ");

        printLogs(LogTag, "initBackBtn", "init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void callDataApi() {
        Log.e("TAG", "call dataApi: called ");

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
        Log.e("TAG", "fetch data: called ");

        final String currentLatS = String.valueOf(currentLat);
        final String currentLongS = String.valueOf(currentLong);
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.REF_S_108;
        FINAL_URL = FINAL_URL + "?token=" + token + "&lat=" + currentLatS + "&long=" + currentLongS;
        printLogs(LogTag, "fetchData", "URL : " + FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchData", "response : " + response);
                    String Status = jsonObject.getString(KEY_STATUS);
                    if (Status.equals("1")) {
                        JSONObject dataM = jsonObject.getJSONObject(KEY_DATA);
                        String punchOutShow = dataM.getString("punch_out_status");
                        String current_time = dataM.getString("current_time");
                        String current_date = dataM.getString("current_date");
                        String distance_location = dataM.getString("distance_from_location");
                        String workstation = dataM.getString("workstation_location");
                        OldId = dataM.getString("s_a_id");
                        String wLat = dataM.getString("workstation_lat");
                        String wLon = dataM.getString("workstation_long");
                        //txtPunchOutDate.setText(s_a_login_date);
                        iTime.setText(current_time);
                        iDate.setText(current_date);
                        iDistanceFrmWorkstation.setText(distance_location);
                        iWorkstation.setText(workstation);

                        if (punchOutShow.equals("2")) {

                            showWorkstation(wLat, wLon);
                            //enableTodayPunchOut();
                            enableTodayPunchOutActions(currentLatS, currentLongS, currentLat, currentLong);
                        } else if (punchOutShow.equals("1")) {
                            OldDate = dataM.getString("s_a_login_date");
                            //showWorkstation(wLat,wLon);
                            //enableOldPunchOut();
                            enableOldPunchOutActions(currentLatS, currentLongS, currentLat, currentLong);
                        } else if (punchOutShow.equals("3")) {
                            ///TodayPunchInPending
                            enablePunchInActions(currentLatS, currentLongS, currentLat, currentLong);
                            String Label = getLabelFromDb("l_S108_not_signed_in", R.string.l_S108_not_signed_in);
                            String Label2 = getLabelFromDb("l_S108_not_signed_in", R.string.l_S108_not_signed_in_post);
                            txtiPunchIn.setText(Label + " " + current_date + "\n\n" + Label2);
                        } else {
                            /// All done
                            disableActions(currentLatS, currentLongS, currentLat, currentLong);
                        }
                    } else {
                        //showProgress(false,mContentView,mProgressView);
                        printLogs(LogTag, "fetchData", "error_try_again : DATA_ERROR");
                        String sTitle = "Error :" + ActivityId + "-S108";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    printLogs(LogTag, "fetchData", "error_try_again : " + e.getMessage());
                    e.printStackTrace();
                    //showProgress(false,mContentView,mProgressView);
                    String sTitle = "Error :" + ActivityId + "-S108";
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
                        String sTitle = "Error :" + ActivityId + "-S108";
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

    public void initializeMap() {
        Log.e("TAG", "initialize map: called ");

        printLogs(LogTag, "initializeMap", "init");
        if (googleMap == null) {
            SupportMapFragment mapFragment = (FMap) getSupportFragmentManager().findFragmentById(R.id.map);
            if (mapFragment != null) {
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override

                    public void onMapReady(GoogleMap googleMapI) {
                        googleMap = googleMapI;
                        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        googleMap.getUiSettings().setZoomControlsEnabled(true);

                        final ScrollView mScrollView = findViewById(R.id.scroll_layout); //parent scrollview in xml, give your scrollview id value
                        ((FMap) Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.map))).setListener(new FMap.OnTouchListener() {
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

    @Override
    protected void initializeViews() {
        Log.e("TAG", "initialize views: called ");

        printLogs(LogTag, "initializeViews", "init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        heading = findViewById(R.id.heading);
        text_internet_info_head = findViewById(R.id.text_internet_info_head);
        gps_error_po = findViewById(R.id.gps_error_po);
        gps_error_pi = findViewById(R.id.gps_error_pi);
        lblGpsCordinate = findViewById(R.id.lblGpsCordinate);
        iLat = findViewById(R.id.iLat);
        iLong = findViewById(R.id.iLong);
        inputPunchOutTime = (TimePicker) findViewById(R.id.inputPunchOutTime);
        btnPunchIn = (Button) findViewById(R.id.btnPunchIn);
        btnPunchOut = (Button) findViewById(R.id.btnPunchOut);
        LLiPunchOutContainer = (LinearLayout) findViewById(R.id.iPunchOutContainer);
        LLiPunchOutDoneContainer = (LinearLayout) findViewById(R.id.iPunchOutDoneContainer);
        LLiPunchInContainer = (LinearLayout) findViewById(R.id.iPunchInContainer);
        LLiTimeContainer = (LinearLayout) findViewById(R.id.iTimeContainer);
        LLinformationContainer = (LinearLayout) findViewById(R.id.informationContainer);
        LLpunchOutPostContainer = (LinearLayout) findViewById(R.id.punchOutPostContainer);
        LLiPunchOutInfoContainer = (LinearLayout) findViewById(R.id.punchOutInfoContainer);
        LLiPunchOutMapContainer = (LinearLayout) findViewById(R.id.punchOutMapContainer);
        txtiPunchOutPost = (TextView) findViewById(R.id.iPunchOutPost);
        txtiPunchOutPre = (TextView) findViewById(R.id.iPunchOutPre);
        txtPunchOutDate = (TextView) findViewById(R.id.txtPunchOutDate);
        txtiPunchIn = (TextView) findViewById(R.id.iPunchIn);
        txtiPunchOutDone = (TextView) findViewById(R.id.iPunchOutDone);
        iDate = (TextView) findViewById(R.id.iDate);
        iDateOld = (TextView) findViewById(R.id.iDateOld);
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
        Log.e("TAG", "initializeListeners: called ");

        btnPunchIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inBundle = new Bundle();
                //   Intent intent = new Intent(SignOutA.this, SignInA.class);
                Intent intent = new Intent(SignOutA.this, SignInA.class);
                inBundle.putString("generator", ActivityId);
                intent.putExtras(inBundle);
                startActivity(intent);
                finish();
            }
        });
        btnPunchOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
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
        Log.e("TAG", "enableloc: called ");

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(SignOutA.this)
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
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(SignOutA.this, REQUEST_LOCATION);
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
        Log.e("TAG", "initialize input: called ");

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

                //getLocation();
                //checkLocationEnabled();
                fetchData();
               checkPermissions();
            } else {
                LLinformationContainer.setVisibility(View.VISIBLE);
            }
        }


    }
    private boolean checkPermissions() {
        Log.e("TAG", "check permission: called ");

        gps_enabled = PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        LocationManager lm = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
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
        Log.e("TAG", "initalize lables: called ");

        printLogs(LogTag, "initializeLabels", "init");
        String Label = getLabelFromDb("l_S108_sign_out_time", R.string.l_S108_sign_out_time);
        lblView = (TextView) findViewById(R.id.lblPunchOutTime);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("l_S108_punch_out", R.string.l_S108_punch_out);
        btnPunchOut.setText(Label);
        Label = getLabelFromDb("error_gps_so", R.string.error_gps_so);
        gps_error_pi.setText(Label);
        gps_error_po.setText(Label);
        Label = getLabelFromDb("l_S108_punch_in", R.string.l_S108_punch_in);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        btnPunchIn.setText(Label);
        Label = getLabelFromDb("lbl_S107_gps_coordinate", R.string.lbl_S107_gps_coordinate);
        lblGpsCordinate.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblGpsCordinate.setText(Label);
        Label = getLabelFromDb("i_no_active_grant", R.string.i_no_active_grant);
        lblView = (TextView) findViewById(R.id.iNoActiveGrant);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S108_sign_out_date", R.string.lbl_S108_sign_out_date);
        lblView = (TextView) findViewById(R.id.lblPunchOutDate);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("h_S108", R.string.h_S108);
        lblView = (TextView) findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("l_S108_distance_from_workstation", R.string.l_S108_distance_from_workstation);
        lblView = (TextView) findViewById(R.id.lblDistanceFrmWorkstation);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("l_S108_workstation", R.string.l_S108_workstation);
        lblView = (TextView) findViewById(R.id.lblWorkstation);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("l_S108_date", R.string.l_S108_date);
        lblView = (TextView) findViewById(R.id.lblDate);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("l_S108_date", R.string.l_S108_date);
        lblView = (TextView) findViewById(R.id.lblDateOld);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("l_S108_time", R.string.l_S108_time);
        lblView = (TextView) findViewById(R.id.lblTime);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        tvII.setText(getApplicationContext().getString(R.string.error_enable_location));
        tvII.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));

        text_internet_info_head.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        tvII.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        txtPunchOutDate.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        iDate.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        iTime.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        iDistanceFrmWorkstation.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        iWorkstation.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        iLat.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        iLong.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        txtiPunchOutDone.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            btnTryAgain.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            btnPunchOut.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            btnPunchIn.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            //  txtiPunchInDone.setBackground(getDrawable(getDrwabaleResourceId("themed_small_button")));
            // txtiPunchInDone.setTextColor(R.);
        }
    }

    @Override
    protected void setLayoutXml() {
        Log.e("TAG", "set layout xaml: called ");

        printLogs(LogTag, "setLayoutXml", "a_sign_out");
        setContentView(R.layout.a_sign_out);
    }
    @Override
    protected void internetChangeBroadCast() {
        Log.e("TAG", "internet change broadcast: called ");

        printLogs(LogTag, "internetChangeBroadCast", "init");
        registerBroadcastIC();
    }

    @Override
    protected void fetchVersionData() {
        Log.e("TAG", "fetch version data: called ");

        printLogs(LogTag, "fetchVersionData", "exitConnected");
        syncUpdates(baseApcContext, activityIn);
    }

    @Override
    protected void verifyVersion() {
        Log.e("TAG", "verify version: called ");

        printLogs(LogTag, "verifyVersion", "init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag, "verifyVersion", "isUpdate " + isUpdate);
        printLogs(LogTag, "verifyVersion", "isUpdate " + utilSessionObj.getIsUpdateRequired());
        if (isUpdate) {
            Intent intent = new Intent(SignOutA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    private void setButtonsState(boolean requestingLocationUpdates) {
        Log.e("TAG", "set butto state: called ");

        if (requestingLocationUpdates) {
            btnPunchIn.setVisibility(View.VISIBLE);
            btnPunchOut.setVisibility(View.VISIBLE);
            mRequestLocationUpdatesButton.setVisibility(View.GONE);
            mRemoveLocationUpdatesButton.setVisibility(View.GONE);
            gps_error_po.setVisibility(View.GONE);
            gps_error_pi.setVisibility(View.GONE);
        } else {
            mRequestLocationUpdatesButton.setVisibility(View.VISIBLE);
            mRemoveLocationUpdatesButton.setVisibility(View.GONE);
            btnPunchIn.setVisibility(View.GONE);
            btnPunchOut.setVisibility(View.GONE);
            gps_error_po.setVisibility(View.VISIBLE);
            gps_error_pi.setVisibility(View.VISIBLE);
        }
    }



    public void showWorkstation(String wLat, String wLon) {
        Log.e("TAG", "show work station: called ");

        if (!wLat.isEmpty()) {
            if (!wLon.isEmpty()) {
                    if (!wLat.equals("null")) {
                            if (!wLon.equals("null")) {
                                Double workLat = Double.parseDouble(wLat);
                                Double workLong = Double.parseDouble(wLon);
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
    }


    protected Marker createMarker(double latitude, double longitude, String title, String snippet, float iconResID) {
        Log.e("TAG", "create Markr: called ");

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

    public void validateForm() {
        Log.e("TAG", "validate form: called ");

        printLogs(LogTag, "validateForm", "init");
        boolean cancel = false;
        if (!isValidateLtLng(currentLat, currentLong)) {
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

    @Override
    protected void FormSubmit() {
        Log.e("TAG", "formsubmit: called ");

        int hour = inputPunchOutTime.getCurrentHour();
            int min = inputPunchOutTime.getCurrentMinute();
            String sTime = "";
            if (!OldId.isEmpty()) {
                sTime = hour + ":" + min + ":00";
            }
            final String SelectedTime = sTime;
            final String dataLat = Double.toString(currentLat);
            final String dataLong = Double.toString(currentLong);
            String token = userSessionObj.getToken();
            String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.REF_S_108_SAVE;
            printLogs(LogTag, "FormSubmit", "URL : " + FINAL_URL);
            JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put(KEY_LAT, dataLat);
            jsonBody.put(KEY_LONG, dataLong);
            jsonBody.put(KEY_TIME, SelectedTime);
            jsonBody.put(KEY_S_A_ID, OldId);
            printLogs(LogTag, "FormSubmit", "jsonBody " + jsonBody);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.POST, FINAL_URL, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject jsonObject;
                printLogs(LogTag, "FormSubmit", String.format("RESPONSE : %s", response));
                try {
                    jsonObject = new JSONObject(String.valueOf(response));
                    String Status = jsonObject.getString(KEY_STATUS);
                        if (Status.equals("1")) {
                            showProgress(false, mContentView, mProgressView);
                            String sTitle = getLabelFromDb("m_S108_title", R.string.m_S108_title);
                            String sMessage = getLabelFromDb("m_S108_message", R.string.m_S108_message);
                            String sButtonLabelClose = "Close";
                            ErrorDialog.showSuccessDialogSignOutAA(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose, thisClass);
                            //disableActions();
                            // customRedirector();
                        } else {
                            showProgress(false, mContentView, mProgressView);
                            String sTitle = "Error :" + ActivityId + "-S108";
                            String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                            String sButtonLabelClose = "Close";
                            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                        }
                    } catch (JSONException e) {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-S108";
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
                            String sTitle = "Error :" + ActivityId + "-S108";
                            String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                            String sButtonLabelClose = "Close";
                            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                        }
                    }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/json; charset=utf-8");
                header.put("Accept","*/*");
                return header;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(SignOutA.this);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }


    public boolean isValidateLtLng(double lat, double lng) {
        Log.e("TAG", "isValidateLtLng: called ");

        printLogs(LogTag, "isValidate", "DATA : " + lat + " -- " + lng);
        if (lat < -90 || lat > 90) {
            String sTitle = "Error :" + ActivityId + "-S108";
            String sMessage = getLabelFromDb("error_location", R.string.error_location);
            String sButtonLabelClose = "Close";
            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
            return false;
        } else if (lng < -180 || lng > 180) {
            String sTitle = "Error :" + ActivityId + "-S108";
            String sMessage = getLabelFromDb("error_location", R.string.error_location);
            String sButtonLabelClose = "Close";
            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
            return false;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.e("TAG", "on option item selected: called ");

        printLogs(LogTag, "onOptionsItemSelected", "init");
        if (item.getItemId() == android.R.id.home) {
            if (generator.equals("S108")) {
                inBundle = new Bundle();
                //    Intent intent = new Intent(SignOutA.this, SignInA.class);
                Intent intent = new Intent(SignOutA.this, SignInA.class);
                printLogs(LogTag, "onOptionsItemSelected", "SignInA");
                inBundle.putString("generator", "103");
                intent.putExtras(inBundle);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(SignOutA.this, SCurrentAttDA.class);
                printLogs(LogTag, "onOptionsItemSelected", "SCurrentAttDA");
                startActivity(intent);
                finish();
            }
        }
        return true;
    }

    private void enablePunchInActions(String currentLatS, String currentLongS, Double currentLat, Double currentLong) {
        Log.e("TAG", "enable punchInActions: called ");

        if ((currentLat != null) && (currentLong != null)) {
            createMarker(currentLat, currentLong, "SIGN-OUT", "", BitmapDescriptorFactory.HUE_AZURE);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLat, currentLong), 5));
        }
        LLiPunchInContainer.setVisibility(View.VISIBLE);
        LLiPunchOutContainer.setVisibility(View.GONE);
       // checkAllActions(currentLatS, currentLongS, currentLat, currentLong);
        printLogs(LogTag, "enablePunchInActions", "init");
        showProgress(false, mContentView, mProgressView);
    }

    private void enableTodayPunchOutActions(String currentLatS, String currentLongS, Double currentLat, Double currentLong) {
        Log.e("TAG", "enable today punch out: called ");

        LLiPunchInContainer.setVisibility(View.GONE);
        LLiPunchOutContainer.setVisibility(View.VISIBLE);
        LLiTimeContainer.setVisibility(View.GONE);
        if ((currentLat instanceof Double) && (currentLong instanceof Double)) {
            createMarker(currentLat, currentLong, "SIGN-OUT", "", BitmapDescriptorFactory.HUE_AZURE);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLat, currentLong), 5));
        }
        LLpunchOutPostContainer.setVisibility(View.GONE);
      //  checkAllActions(currentLatS, currentLongS, currentLat, currentLong);
        printLogs(LogTag, "enableTodayPunchOutActions", "init");
        showProgress(false, mContentView, mProgressView);
    }

    private void enableOldPunchOutActions(String currentLatS, String currentLongS, Double currentLat, Double currentLong) {
        Log.e("TAG", "enabled old punch out: called ");

        LLpunchOutPostContainer.setVisibility(View.VISIBLE);
        LLiPunchInContainer.setVisibility(View.GONE);
        LLiPunchOutContainer.setVisibility(View.VISIBLE);
        LLiTimeContainer.setVisibility(View.VISIBLE);
        LLiPunchOutInfoContainer.setVisibility(View.GONE);
        //LLiPunchOutMapContainer.setVisibility(View.GONE);
        if ((currentLat instanceof Double) && (currentLong instanceof Double)) {
            createMarker(currentLat, currentLong, "SIGN-OUT", "", BitmapDescriptorFactory.HUE_AZURE);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLat, currentLong), 5));
        }
        String LabelPre = getLabelFromDb("l_S108_old_pending_pre", R.string.l_S108_old_pending_pre);
        String LabelPost = getLabelFromDb("l_S108_old_pending_post", R.string.l_S108_old_pending_post);
        txtiPunchOutPre.setText(LabelPre);
        txtiPunchOutPre.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        txtiPunchOutPost.setText(LabelPost);
        txtiPunchOutPost.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        txtPunchOutDate.setText(OldDate);
        txtPunchOutDate.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        iDateOld.setText(OldDate);
        iDateOld.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
       // checkAllActions(currentLatS, currentLongS, currentLat, currentLong);
        printLogs(LogTag, "enableOldPunchOutActions", "init");
        showProgress(false, mContentView, mProgressView);
    }

    private void disableActions(String currentLatS, String currentLongS, Double currentLat, Double currentLong) {
        Log.e("TAG", "disable actions: called ");

        if ((currentLat != null) && (currentLong != null)) {
            createMarker(currentLat, currentLong, "SIGN-OUT", "", BitmapDescriptorFactory.HUE_AZURE);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLat, currentLong), 5));
        }
        printLogs(LogTag, "disableActions", "init");
        LLiPunchOutDoneContainer.setVisibility(View.VISIBLE);
        LLiPunchInContainer.setVisibility(View.GONE);
        LLiPunchOutContainer.setVisibility(View.GONE);
        String Label = getLabelFromDb("i_S108_already_signout", R.string.i_S108_already_signout);
        txtiPunchOutDone.setText(Label);
        showProgress(false, mContentView, mProgressView);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.e("TAG", "onConnected: called ");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        getLastLocation();
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
            printLogs(LogTag, "onConnected", "=======================================" );
            printLogs(LogTag, "onConnected", "latitude : " + latitude);
            printLogs(LogTag, "onConnected", "longitude : " + longitude);

            if(currentLat !=null && currentLong != null){
                initializeInputs();
            }else{
                Toast.makeText(thisClass, "Something Went Wrong !!", Toast.LENGTH_SHORT).show();
            }

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
        }
       /* else{
            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(SignOutA.this);
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
                            Intent intent = new Intent(SignOutA.this, SignOutA.class);
                            inBundle.putString("generator", "S104");
                            intent.putExtras(inBundle);
                            startActivity(intent);
                            finish();
                        }
                    });
            alertDialog2.setCancelable(false);
            alertDialog2.show();
        }*/
    }
   /* public void checkLocationPermission() {
        Log.e("TAG", "check location permission: called ");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }*/
    //(Ankita)
    public boolean checkLocationPermission(){
        Log.e("TAG", "check location permission: called ");

        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;



    }

    private void getLastLocation() {
        // check if permissions are given
        Log.e("TAG", "getLastLocation: called");
        if (checkLocationPermission()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();


                        } else {

                            /*latitudeTextView.setText(location.getLatitude() + "");
                            longitTextView.setText(location.getLongitude() + "");*/
                            currentLat = location.getLatitude();
                            currentLong = location.getLongitude();
                           // setMapLocation();
                            Log.e("TAG", "onComplete: latitude get last location current lat =  " + currentLat);
                            Log.e("TAG", "onComplete: longitude get last location current long  =  " + currentLong);
                            iLat.setText(currentLat.toString());
                            iLong.setText(currentLong.toString());
                            geocoder = new Geocoder(baseApcContext);
                            try {
                                addresses = geocoder.getFromLocation(currentLat, currentLong, 1);
                                Log.w("TAG", "onComplete: address country name " + addresses.get(0).getCountryName());
                                Log.w("TAG", "onComplete: address postal code " + addresses.get(0).getPostalCode());
                                Log.w("TAG", "onComplete: address SubLocality  " + addresses.get(0).getSubLocality());
                                Log.w("TAG", "onComplete: address SubAdminArea " + addresses.get(0).getSubAdminArea());
                                Log.w("TAG", "onComplete: locale " + addresses.get(0).getLocale());
                                //setMapLocation();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            Log.e("TAG", "onComplete: latitude = location result ***** " + mLastLocation.getLatitude());
            Log.e("TAG", "onComplete: longitude  = location Result *** " + mLastLocation.getLongitude());


        }
    };

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        /*LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);*/
        Log.e("TAG", "requestNewLocationData: Requst new location called");
        LocationRequest.Builder locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 100);
        locationRequest.setMinUpdateDistanceMeters(100);
        locationRequest.setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL);
        locationRequest.setWaitForAccurateLocation(true);
        locationRequest.build();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }



    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSIONS_REQUEST_LOCATION);

        getLastLocation();
        // setMapLocation();
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
    protected void onResume() {
        super.onResume();
        Log.e("TAG", "onResume: called ");

        checkInternetConnection();
        mGoogleApiClient.connect();
        registerBroadcastIC();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("TAG", "onPause: called ");

        mGoogleApiClient.disconnect();
        unregisterBroadcastIC();

    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.e("TAG", "onStart: called ");

        checkInternetConnection();
        registerBroadcastIC();
        mGoogleApiClient.connect();
    }

    @Override
    public void onDestroy() {
        Log.e("TAG", "onDestroy: called ");

        mGoogleApiClient.disconnect();
        unregisterBroadcastIC();
        super.onDestroy();
    }
    public void customRedirectorr(){
        Log.e("TAG", "custom Redirector: called ");

        Intent intent = new Intent(SignOutA.this, SDashboardDA.class);
        Bundle activeUri = new Bundle();
        activeUri.putString("generator","S107");
        intent.putExtras(activeUri);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("TAG", "onActivity result : called ");

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
