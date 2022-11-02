package za.co.sacpo.grant.xCubeStudent.attendance;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import za.co.sacpo.grant.AppUpdatedA;


import za.co.sacpo.grant.R;
import za.co.sacpo.grant.fragments.FMap;
import za.co.sacpo.grant.xCubeLib.component.LocationUpdatesService;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grant.xCubeLib.dialogs.GpsDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsGrantSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsStudentSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeStudent.SDashboardDA;


public class SignInA extends BaseFormAPCPrivate implements SharedPreferences.OnSharedPreferenceChangeListener{
    private String ActivityId = "S107";
    public View mProgressView, mContentView;
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
    private MyReceiver myReceiver;
    SupportMapFragment mapFragment ;
    private boolean mBound = false;
    private LocationUpdatesService mService = null;
    private Button mRequestLocationUpdatesButton;
    private Button mRemoveLocationUpdatesButton;

    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocationUpdatesService.LocalBinder binder = (LocationUpdatesService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mBound = false;
        }
    };

    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cTAId) {
        baseApcContext = cnt;
        CAId = cTAId;
        activityIn = ain;
        LogTag = lt;
        thisClass = this;
        printLogs(lt, "setBaseApcContextParent", "weAreIn");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Bundle outBundle = getIntent().getExtras();
        generator = outBundle.getString("generator");
        network_enabled = false;
        gps_enabled = false;
        myReceiver = new MyReceiver();
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(), this, this.getClass().getSimpleName(), ActivityId);
        printLogs(LogTag, "onCreate", "init");
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
            initializeInputs();
            fetchData();
            printLogs(LogTag, "bootStrapInit", "exitConnected");
        }
    }

    public void initializeMap() {
        printLogs(LogTag,"initializeMap","init");
        if (googleMap == null) {
            mapFragment = (FMap) getSupportFragmentManager().findFragmentById(R.id.map);
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
        MapsInitializer.initialize(getApplicationContext());
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

        printLogs(LogTag, "initializeViews", "exit");
    }

    @Override
    protected void initializeLabels() {
        printLogs(LogTag, "initializeLabels", "init");
        String Label = getLabelFromDb("i_S107_sign_out_pre", R.string.i_S107_sign_out_pre);
        lblView = (TextView) findViewById(R.id.iPunchOutPre);
        lblView.setText(Label);
        Label = getLabelFromDb("i_S107_sign_out_post", R.string.i_S107_sign_out_post);
        lblView = (TextView) findViewById(R.id.iPunchOutPost);
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S107_sign_out_date", R.string.lbl_S107_sign_out_date);
        lblView = (TextView) findViewById(R.id.lblPunchOutDate);
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S107_gps_coordinate", R.string.lbl_S107_gps_coordinate);
        lblGpsCordinate.setText(Label);

        Label = getLabelFromDb("b_S107_sign_in", R.string.b_S107_sign_in);
        btnPunchIn.setText(Label);
        Label = getLabelFromDb("error_gps", R.string.error_gps);
        gps_error_pi.setText(Label);
        gps_error_po.setText(Label);
        Label = getLabelFromDb("h_S107", R.string.h_S107);
        lblView = (TextView) findViewById(R.id.activity_heading);
        lblView.setText(Label);
        Label = getLabelFromDb("b_S107_sign_out", R.string.b_S107_sign_out);
        btnPunchOut.setText(Label);
        Label = getLabelFromDb("i_no_active_grant", R.string.i_no_active_grant);
        lblView = (TextView) findViewById(R.id.iNoActiveGrant);
        lblView.setText(Label);

        Label = getLabelFromDb("l_S107_distance_from_workstation", R.string.l_S107_distance_from_workstation);
        lblView = (TextView) findViewById(R.id.lblDistanceFrmWorkstation);
        lblView.setText(Label);
        Label = getLabelFromDb("l_S107_workstation", R.string.l_S107_workstation);
        lblView = (TextView) findViewById(R.id.lblWorkstation);
        lblView.setText(Label);
        Label = getLabelFromDb("l_103_date", R.string.l_103_date);
        lblView = (TextView) findViewById(R.id.lblDate);
        lblView.setText(Label);
        Label = getLabelFromDb("l_103_time", R.string.l_103_time);
        lblView = (TextView) findViewById(R.id.lblTime);
        lblView.setText(Label);
        tvII.setText(getApplicationContext().getString(R.string.error_enable_location));

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
                if (Utils.requestingLocationUpdates(this)) {
                    if (!checkPermissions()) {
                        requestPermissions();
                    }/*else {
                       mService.requestLocationUpdates();
                    }*/
                }
                fetchData();

            } else {
                LLinformationContainer.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    protected void internetChangeBroadCast() {
        printLogs(LogTag, "internetChangeBroadCast", "init");
        registerBroadcastIC();
    }

    @Override
    protected void fetchVersionData() {
        printLogs(LogTag, "fetchVersionData", "exitConnected");
        syncUpdates(baseApcContext, activityIn);
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
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals(Utils.KEY_REQUESTING_LOCATION_UPDATES)) {
            setButtonsState(sharedPreferences.getBoolean(Utils.KEY_REQUESTING_LOCATION_UPDATES,
                    false));
        }
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

    private void initBackBtn() {
        printLogs(LogTag, "initBackBtn", "init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void fetchData() {
        final String currentLatS = String.valueOf(currentLat);
        final String currentLongS = String.valueOf(currentLong);
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.REF_S_107;
        FINAL_URL = FINAL_URL + "/token/" + token + "/lat/" + currentLatS + "/long/" + currentLongS;
        printLogs(LogTag, "fetchData", "URL : " + FINAL_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, FINAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject outputJson = null;
                printLogs(LogTag, "fetchData", "RESPONSE : " + response);
                try {
                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if (Status.equals("1")) {
                        JSONObject dataM = outputJson.getJSONObject(KEY_DATA);
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
                                if (wLat != null) {
                                    if (!wLat.equals("null")) {
                                        if (wLon != null) {
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
                            disableActions();
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
        };
        RequestQueue requestQueue = Volley.newRequestQueue(SignInA.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    public void validateForm() {
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

    public void FormSubmit() {
        final String dataLat = Double.toString(currentLat);
        final String dataLong = Double.toString(currentLong);
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.REF_S_107_SAVE;
        FINAL_URL = FINAL_URL + "/token/" + token;
        printLogs(LogTag, "FormSubmit", "URL : " + FINAL_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, FINAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject outputJson = null;
                printLogs(LogTag, "FormSubmit", "RESPONSE : " + response);
                try {
                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if (Status.equals("1")) {

                        /// Show go back data
                        disableActions();

                        showProgress(false, mContentView, mProgressView);
                        String sTitle = getLabelFromDb("m_S107_title", R.string.m_S107_title);
                        String sMessage = getLabelFromDb("m_S107_message", R.string.m_S107_message);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showSuccessDialogSignInA(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose, thisClass);


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
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_LAT, dataLat);
                params.put(KEY_LONG, dataLong);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(SignInA.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
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

    public boolean isValidLatLng(double lat, double lng) {
        printLogs(LogTag, "isValidLatLng", "DATA : " + lat + " -- " + lng);
        if (lat < -90 || lat > 90) {
            return false;
        } else return !(lng < -180) && !(lng > 180);
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

    private void enablePunchInActions(String currentLatS, String currentLongS, Double currentLat, Double currentLong) {
        if ((currentLat instanceof Double) && (currentLong instanceof Double)) {
            createMarker(currentLat, currentLong, "SIGN-IN", "", BitmapDescriptorFactory.HUE_AZURE);
        }
        LLiPunchInContainer.setVisibility(View.VISIBLE);
        LLiPunchOutContainer.setVisibility(View.GONE);
        LLiPunchInDoneContainer.setVisibility(View.GONE);
        checkAllActions(currentLatS, currentLongS, currentLat, currentLong);
        printLogs(LogTag, "enablePunchInActions", "init");
        showProgress(false, mContentView, mProgressView);

    }

    private void enablePunchOutActions(String currentLatS, String currentLongS, Double currentLat, Double currentLong) {
        LLiPunchInContainer.setVisibility(View.GONE);
        LLiPunchInDoneContainer.setVisibility(View.GONE);
        LLiPunchOutContainer.setVisibility(View.VISIBLE);
        checkAllActions(currentLatS, currentLongS, currentLat, currentLong);
        printLogs(LogTag, "enablePunchOutActions", "init");
        showProgress(false, mContentView, mProgressView);
    }

    private void checkAllActions(String currentLatS, String currentLongS, Double currentLat, Double currentLong) {
        printLogs(LogTag, "checkAllActions", "currentLatS--" + currentLatS);
        printLogs(LogTag, "checkAllActions", "currentLongS--" + currentLongS);
        iLat.setText("LAT :: " + currentLatS);
        iLong.setText("LONG :: " + currentLongS);
        Boolean isGpsOn = true;
        if (currentLatS.isEmpty()) {
            isGpsOn = false;
        } else {
            if (currentLatS == "null") {
                isGpsOn = false;
            } else {
                if (currentLongS.isEmpty()) {
                    isGpsOn = false;
                } else {
                    if (currentLongS == "null") {
                        isGpsOn = false;
                    } else {
                        if (!isValidLatLng(currentLat, currentLong)) {
                            isGpsOn = false;
                        }
                    }
                }
            }
        }
        if (isGpsOn == true) {
            btnPunchIn.setVisibility(View.VISIBLE);
            btnPunchOut.setVisibility(View.VISIBLE);
            gps_error_po.setVisibility(View.GONE);
            gps_error_pi.setVisibility(View.GONE);
        } else {
            btnPunchIn.setVisibility(View.GONE);
            btnPunchOut.setVisibility(View.GONE);
            gps_error_po.setVisibility(View.VISIBLE);
            gps_error_pi.setVisibility(View.VISIBLE);
        }
        /// enable LOCATION
    }

    private void disableActions() {
        printLogs(LogTag, "disableActions", "init");
        LLiPunchInContainer.setVisibility(View.GONE);
        LLiPunchInDoneContainer.setVisibility(View.VISIBLE);
        String Label = getLabelFromDb("i_S107_already_signin", R.string.i_S107_already_signin);
        txtiPunchInDone.setText(Label);
        btnPunchIn.setVisibility(View.GONE);
        LLiPunchOutContainer.setVisibility(View.GONE);
        showProgress(false, mContentView, mProgressView);
    }

    private void checkLocationEnabled() {
        printLogs(LogTag, "checkLocationEnabled", "init");
        LocationManager lm = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }
        if (!gps_enabled || !network_enabled) {
            String sTitle = getString(R.string.dialog_no_gps);
            String sMessage = getString(R.string.dialog_no_gps_message);
            String sButtonLabelGps = getString(R.string.dialog_enable_gps);
            GpsDialog.showGpsDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelGps);
        } else {
            printLogs(LogTag, "checkLocationEnabled", "gps_enabled" + gps_enabled);
            printLogs(LogTag, "checkLocationEnabled", "network_enabled" + network_enabled);
            registerBroadcastIC();
            removeGpsDialog();
        }
    }

    private void removeGpsDialog() {
        printLogs(LogTag, "removeGpsDialog", "init");
        if (gps_enabled && network_enabled) {
            printLogs(LogTag, "removeGpsDialog", "REMOVE");
            GpsDialog.removeGpsDialog();
        //    getLocation();
            initializeMap();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        unregisterBroadcastIC();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver);
    }
    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver,
                new IntentFilter(LocationUpdatesService.ACTION_BROADCAST));
        registerBroadcastIC();
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            // Unbind from the service. This signals to the service that this activity is no longer
            // in the foreground, and the service can respond by promoting itself to a foreground
            // service.
            unbindService(mServiceConnection);
            mBound = false;
        }
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
        unregisterBroadcastIC();
    }
    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcastIC();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);

        mRequestLocationUpdatesButton = (Button) findViewById(R.id.request_location_updates_button);
        mRemoveLocationUpdatesButton = (Button) findViewById(R.id.remove_location_updates_button);

        mRequestLocationUpdatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkPermissions()) {
                    requestPermissions();
                } else {
                    mService.requestLocationUpdates();
                }
            }
        });

        mRemoveLocationUpdatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mService.removeLocationUpdates();
            }
        });

        // Restore the state of the buttons when the activity (re)launches.
        setButtonsState(Utils.requestingLocationUpdates(this));

        // Bind to the service. If the service is in foreground mode, this signals to the service
        // that since this activity is in the foreground, the service can exit foreground mode.
        bindService(new Intent(this, LocationUpdatesService.class), mServiceConnection,
                Context.BIND_AUTO_CREATE);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcastIC();
        if (mBound) {
            // Unbind from the service. This signals to the service that this activity is no longer
            // in the foreground, and the service can respond by promoting itself to a foreground
            // service.
            unbindService(mServiceConnection);
            mBound = false;
        }
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
        unregisterBroadcastIC();
    }


    private boolean checkPermissions() {
        /*String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        printLogs(LogTag, "checkPermissions", "provider_GPS----" + provider);
        if (!provider.contains("gps")) {
            printLogs(LogTag, "checkPermissions", "provider.contains_GPS----" + provider.contains("gps"));
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
            LLII.setVisibility(View.VISIBLE);
        } else {
            printLogs(LogTag, "checkPermissions", "provider.contains_GPS----" + provider.contains("gps"));
            LLII.setVisibility(View.GONE);
            permissionLocation = true;
         //   getLocation();
            if (permissionLocation == true){
                //  getLocation();
                mService.requestLocationUpdates();
            }
        }
        return true;*/

        gps_enabled = PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION);
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

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            printLogs(LogTag, "requestPermissions", "Displaying permission rationale to provide additional context");
            Snackbar.make(
                    findViewById(R.id.activity_main),
                    R.string.permission_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(SignInA.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    })
                    .show();
        } else {
            printLogs(LogTag, "requestPermissions", "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(SignInA.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private void getLocation() {
        permissionLocation = false;
        printLogs(LogTag, "getLocation", "init");
        checkLocationPermission();

        printLogs(LogTag, "getLocation", "permissionLocation " + permissionLocation);
        if (permissionLocation == true) {
                                if (currentLat != null) {
                                    if (currentLong != null) {

                                        iLat.setText("LAT :: " + currentLat.toString()); // change for test.
                                        iLong.setText("LONG :: " + currentLong.toString()); // change for test.


                                        printLogs(LogTag, "getLocation", "currentLat--" + currentLat);
                                        printLogs(LogTag, "getLocation", "currentLong--" + currentLong);

                                        if (isValidLatLng(currentLat, currentLong)) {
                                            fetchData();
                                            printLogs(LogTag, "mFusedLocationClient", "showMessage and signout button");
                                        } else {
                                            printLogs(LogTag, "mFusedLocationClient", "error_location : LOCATION_ERROR");
                                            String sTitle = "Error :" + ActivityId + "-104";
                                            String sMessage = getLabelFromDb("error_location", R.string.error_location);
                                            String sButtonLabelClose = "Close";
                                            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                                        }
                                    } else {
                                        printLogs(LogTag, "mFusedLocationClient", "error_location : LOCATION_ERROR");
                                        String sTitle = "Error :" + ActivityId + "-105";
                                        String sMessage = getLabelFromDb("error_location", R.string.error_location);
                                        String sButtonLabelClose = "Close";
                                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                                    }
                                }



        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        printLogs(LogTag,"onRequestPermissionsResult","init");
        /*switch (requestCode) {
            case 1: {
                printLogs(LogTag,"onRequestPermissionsResult","REQUEST CODE "+requestCode);
                permissionLocation = grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED;
                return;
            }
        }*/

        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                printLogs(LogTag,"onRequestPermissionResult","User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted.
                mService.requestLocationUpdates();
            } else {
                // Permission denied.
                setButtonsState(false);
                Snackbar.make(
                        findViewById(R.id.activity_main),
                        R.string.permission_denied_explanation,
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.settings, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        getApplicationContext().getPackageName(), null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        }
    }

    private void checkLocationPermission(){
        printLogs(LogTag,"checkLocationPermission","init");
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                printLogs(LogTag,"checkLocationPermission","PERMISSION ERROR");
            } else {
                printLogs(LogTag,"checkLocationPermission","PERMISSION INPUT FINE");
                ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            }
        } else {
            printLogs(LogTag,"checkLocationPermission","PERMISSION INPUT");
            permissionLocation = true;
        }
    }

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Location location = intent.getParcelableExtra(LocationUpdatesService.EXTRA_LOCATION);
            if (location != null) {
               // Toast.makeText(SignInA.this, Utils.getLocationText(location),Toast.LENGTH_SHORT).show();
                //location.getLatitude() + ", " + location.getLongitude()
                currentLong = location.getLongitude();
                currentLat = location.getLatitude();
                getLocation();

                printLogs(LogTag,"MyReceiver","currentLong--"+currentLong+"currentLat--"+currentLat);

            }
        }
    }


    private void setButtonsState(boolean requestingLocationUpdates) {
        if (requestingLocationUpdates) {
            mRequestLocationUpdatesButton.setEnabled(false);
            mRemoveLocationUpdatesButton.setEnabled(true);
        } else {
            mRequestLocationUpdatesButton.setEnabled(true);
            mRemoveLocationUpdatesButton.setEnabled(false);
        }
    }

    public void customRedirector(){
        Intent intent = new Intent(SignInA.this,SDashboardDA.class);
        Bundle activeUri = new Bundle();
        activeUri.putString("generator","S107");
        intent.putExtras(activeUri);
        startActivity(intent);
        finish();
    }
}