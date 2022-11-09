package za.co.sacpo.grant.xCubeMentor.workX;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import androidx.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.fragments.FMap;
import za.co.sacpo.grant.xCubeLib.adapter.PlaceAutocompleteAdapter;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.db.workx;
import za.co.sacpo.grant.xCubeLib.db.workxAdapter;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.model.PlaceInfo;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeMentor.MDashboardDA;

public class MAddWorksA extends BaseFormAPCPrivate  implements GoogleApiClient.OnConnectionFailedListener {
    private String ActivityId = "314";
    public View mProgressView, mContentView,heading;
    // private String generator;
    private String KEY_LATE = "latitude";
    private String KEY_Long = "longitude";
    private String KEY_DEPT = "departmentName";
    private String KEY_PHYSICAL_ADDRESS = "physical_address";
    public AutoCompleteTextView act_physical_address;
    private EditText inputAddDeptName;
    public TextInputLayout inputLayoutAddDeptName;
    public Button btnDeptAdd;
    private TextView lblView;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1;
    private Boolean permissionLocation;
    private Location myCurrentLocation;
    private Double currentLat;
    private Double currentLong;
    String generator;
    private MAddWorksA thisClass;
    String student_id, work_x_name, work_x_id, mwX_student_name4,getLat,getLong;
    private static final float DEFAULT_ZOOM = 15f;
    private PlaceAutocompleteAdapter mplaceAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40,-168), new LatLng(71,136)) ;

    private PlaceInfo mPlace;


    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cTAId) {
        baseApcContext = cnt;
        CAId = cTAId;
        activityIn = ain;
        LogTag = lt;
        thisClass = this;
        printLogs(lt, "setBaseApcContextParent", "weAreIn" + CAId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(), this, this.getClass().getSimpleName(), ActivityId);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        Bundle activeInputUri = getIntent().getExtras();
        student_id = Integer.toString(activeInputUri.getInt("student_id"));
        work_x_name = activeInputUri.getString("work_x_name");
        work_x_id = activeInputUri.getString("work_x_id");
        mwX_student_name4 = activeInputUri.getString("mwX_student_name4");
        generator = activeInputUri.getString("generator");
        act_physical_address = findViewById(R.id.act_physical_address);
        printLogs(LogTag, "onCreate", "student_id " + student_id);
        printLogs(LogTag, "onCreate", "work_x_name " + work_x_name);
        printLogs(LogTag, "onCreate", "work_x_id " + work_x_id);
        printLogs(LogTag, "onCreate", "mwX_student_name4 " + mwX_student_name4);
        printLogs(LogTag, "onCreate", "act_physical_address " + act_physical_address);
        bootStrapInit();


    }

    @Override
    protected void bootStrapInit() {
        Boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
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
            initBackBtn();
            showProgress(true, mContentView, mProgressView);
            initializeLabels();
            initializeInputs();
            userToken = userSessionObj.getToken();
            syncToken(baseApcContext, activityIn);
            if (TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
            callDataApi();
            //initializeListeners();
            showProgress(false, mContentView, mProgressView);
            printLogs(LogTag, "bootStrapInit", "exitConnected");
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
            Intent intent = new Intent(MAddWorksA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void setLayoutXml() {
        setContentView(R.layout.a_m_add_workx);
        printLogs(LogTag, "setLayoutXml", "add department");
    }

    @Override
    protected void initializeViews() {
        printLogs(LogTag, "initializeViews", "init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        heading = findViewById(R.id.heading);
        inputAddDeptName = findViewById(R.id.inputAddDeptName);
        act_physical_address =  findViewById(R.id.act_physical_address);
        inputLayoutAddDeptName = (TextInputLayout) findViewById(R.id.inputLayoutAddDeptName);
        btnDeptAdd = (Button) findViewById(R.id.btnDeptAdd);
        printLogs(LogTag, "initializeViews", "exit");
    }

    @Override
    protected void initializeLabels() {
        printLogs(LogTag, "initializeLabels", "init");
        String Label = getLabelFromDb("lbl_314_add_wx_name", R.string.lbl_314_add_wx_name);
        lblView = (TextView) findViewById(R.id.lblAddDeptName);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("h_314", R.string.h_314);
        lblView = (TextView) findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_314_btnDeptAdd", R.string.lbl_314_btnDeptAdd);
        lblView = (TextView) findViewById(R.id.btnDeptAdd);
        btnDeptAdd.setText(Label);

        Label = getLabelFromDb("lbl_164_btn_work_address", R.string.lbl_164_btn_work_address);
        lblView = findViewById(R.id.lblWorkstationAddress);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            btnDeptAdd.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            inputAddDeptName.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            act_physical_address.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        printLogs(LogTag,"onConnectionFailed","Google Places API connection failed with error code:" + connectionResult.getErrorCode());
        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    private AdapterView.OnItemClickListener mAutoCompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            hidesoftKeyboard();

            final AutocompletePrediction item = mplaceAutocompleteAdapter.getItem(position);
            final String placeId = item.getPlaceId();

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient,placeId);
            placeResult.setResultCallback(mUpdateDetailsCallback);

        }
    };

    private ResultCallback<PlaceBuffer> mUpdateDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {

            if(!places.getStatus().isSuccess()){

                printLogs(LogTag,"ResultCallback","Place Query did not complete successfully :" +places.getStatus().toString());
                places.release();
                return;
            }
            final Place place = places.get(0);


            try {

                mPlace = new PlaceInfo();
                mPlace.setName(place.getName().toString());
                mPlace.setAddress(place.getAddress().toString());
                mPlace.setAttributions(place.getAttributions().toString());
                mPlace.setPhonenumber(place.getPhoneNumber().toString());
                mPlace.setId(place.getId());
                mPlace.setLatLng(place.getLatLng());
                mPlace.setRating(place.getRating());
                mPlace.setWebsiteUri(place.getWebsiteUri());
                printLogs(LogTag,"ResultCallback","result:places"+mPlace.toString());

            }catch (NullPointerException e){
                printLogs(LogTag,"NullPointerException","Exception"+e.getMessage());
            }
            moveCamera(new LatLng(place.getViewport().getCenter().latitude,place.getViewport().getCenter().latitude), DEFAULT_ZOOM,mPlace.getName());
            places.release();

        }
    };

    @Override
    protected void initializeInputs() {
        printLogs(LogTag, "initializeInputs", "init");
        if (mMap == null) {
            SupportMapFragment mapFragment = (FMap) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                        mMap = googleMap;
                    //mMap.setMyLocationEnabled(true);
                    mMap.getUiSettings().setMyLocationButtonEnabled(true);
                    mMap.getUiSettings().setZoomGesturesEnabled(true);
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                    initializeListeners();

                    final ScrollView mScrollView = findViewById(R.id.scroll_layout);
                    ((FMap) getSupportFragmentManager().findFragmentById(R.id.map)).setListener(new FMap.OnTouchListener() {
                        @Override
                        public void onTouch() {
                            mScrollView.requestDisallowInterceptTouchEvent(true);
                        }
                    });
                }
            });
        }
        getLocation();
    }

    @Override
    protected void initializeListeners() {
        printLogs(LogTag, "initializeInputs", "init");




        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addOnConnectionFailedListener(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(LocationServices.API)
                .build();

        act_physical_address.setOnItemClickListener(mAutoCompleteClickListener);


      /*  mplaceAutocompleteAdapter = new PlaceAutocompleteAdapter(baseApcContext,mGoogleApiClient,
                LAT_LNG_BOUNDS,null);
*/

           // act_physical_address.setAdapter(mplaceAutocompleteAdapter);


        act_physical_address.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        || event.getAction() == KeyEvent.KEYCODE_ENTER){

                    //execute methode for search

                    geoLocation();

                    hidesoftKeyboard();
                }
                return false;

            }




        });





        btnDeptAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker mMarker) {
            }

            @SuppressWarnings("unchecked")
            @Override
            public void onMarkerDragEnd(Marker mMarker) {
                currentLat = mMarker.getPosition().latitude;
                currentLong = mMarker.getPosition().longitude;
                printLogs(LogTag, "setOnMarkerDragListener", "onMarkerDragEnd " + mMarker.getPosition().latitude + "==" + mMarker.getPosition().longitude);
                mMap.animateCamera(CameraUpdateFactory.newLatLng(mMarker.getPosition()));
            }

            @Override
            public void onMarkerDrag(Marker mMarker) {
            }
        });


    }

    private void geoLocation() {
        String searchString = act_physical_address.getText().toString();
        Geocoder geocoder = new Geocoder(baseApcContext);
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchString,1);



        }catch(IOException e){

            printLogs(LogTag,"geoLocation","exception:"+e.getMessage());
        }

        if(list.size() > 0){
            Address address = list.get(0);
            printLogs(LogTag,"geolocation","FoundLocation"+address.toString());
                moveCamera(new LatLng(address.getLatitude(),address.getLongitude()),DEFAULT_ZOOM,address.getAddressLine(0));
        }
    }



    private void moveCamera(LatLng latLng,float zoom,String title){
        printLogs(LogTag,"moveCamera","mmoving camera to latlong :"+latLng.latitude + ",lng: " + latLng.longitude );
         getLat = String.valueOf(latLng.latitude);
         getLong = String.valueOf(latLng.longitude);
        printLogs(LogTag,"latlong","getLatLong__"+getLat+","+getLong);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
        MarkerOptions  markerOptions = new MarkerOptions().position(latLng).title(title);
        mMap.addMarker(markerOptions);
        hidesoftKeyboard();
    }

    private void initBackBtn() {
        printLogs(LogTag, "initBackBtn", "init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void validateForm() {
        boolean cancel = false;
        final String et_err = inputAddDeptName.getText().toString();
        if (!validateDeptName(et_err)) {
            cancel = true;
        } /*else if (!isValidate(currentLat, currentLong)) {
            cancel = true;
        }*/
        if (cancel) {
            return;
        } else {
            showProgress(true, mContentView, mProgressView);
            this.FormSubmit();
        }
        printLogs(LogTag, "validateForm", "exit");
    }

    public void FormSubmit() {

        final String pos_lat = getLat;
        final String pos_long = getLong;
        printLogs(LogTag, "SubmitForm", "latLong__Value____" + pos_lat+","+pos_long);

        final String latitude = Double.toString(currentLat);
        final String longitude = Double.toString(currentLong);
        final String physical_address = act_physical_address.getText().toString();
        final String dept_name = inputAddDeptName.getText().toString();
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.MADD_WORKSTATION;
        printLogs(LogTag, "FormSubmit", "URL : " + FINAL_URL);


        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put(KEY_LATE, latitude);
            jsonBody.put(KEY_Long, longitude);
            jsonBody.put(KEY_DEPT, dept_name);
            jsonBody.put(KEY_PHYSICAL_ADDRESS, physical_address);
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
                        getMentorWorkX();
                        printLogs(LogTag, "FormSubmit", "SUCCESS : " + Status);
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = getLabelFromDb("m_314_title", R.string.m_314_title);
                        String sMessage = getLabelFromDb("m_314_message", R.string.m_314_message);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showSuccessDialogMAddWorksA(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose, thisClass);
                    } else {
                        printLogs(LogTag, "FormSubmit", "else");
                        showProgress(false, mContentView, mProgressView);
                        String sMessage = getLabelFromDb("error_314_101", R.string.error_314_101);
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-101";
                        // String sButtonLabelClose="Close";
                        // ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "-102";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                printLogs(LogTag, "onCreate", "error listener");
                showProgress(false, mContentView, mProgressView);
                String sTitle = "Error :" + ActivityId + "-103";
                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/json; charset=utf-8");
                header.put("Accept","*/*");
                return header;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);


    }

    public void getMentorWorkX() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.WORKSTATION;
        FINAL_URL = FINAL_URL + "?token=" + token;
        printLogs(LogTag, "getMentorWorkx", "URL : " + FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "getMentorWorkx", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if (Status.equals("1")) {
                        workxAdapter cAd = new workxAdapter(getApplicationContext());
                        cAd.truncate();
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        for (int i = 0; i < dataM.length(); i++) {
                            JSONObject rec = dataM.getJSONObject(i);
                            int Id = Integer.parseInt(rec.getString("e_g_l_id"));
                            String Name = rec.getString("e_g_l_department_name");
                            int Type = 1;
                            workx cObj = new workx(i, Id, Type, Name);

                            cAd.insert(cObj);
                        }
                    } else {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-114";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "-115";
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
                        String sTitle = "Error :" + ActivityId + "-116";
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
    public void callDataApi() {
        printLogs(LogTag, "callDataApi", "init");
        userToken = userSessionObj.getToken();
        if (!TextUtils.isEmpty(userToken)) {
            printLogs(LogTag, "callDataApi", "Token Update");
            syncUserData(baseApcContext, activityIn);
            syncMentorData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn, ActivityId);

        }
    }


    public boolean validateDeptName(String et_err) {
        if (et_err.isEmpty()) {
            String sMessage = getLabelFromDb("error_dept_name", R.string.error_dept_name);
            inputAddDeptName.setError(sMessage);
            return false;
        } else {
            //FormSubmit();
            return true;
        }
    }


    private void getLocation() {
        myCurrentLocation = null;
        permissionLocation = false;
        printLogs(LogTag, "getLocation", "init");
        checkLocationPermission();
        printLogs(LogTag, "getLocation", "permissionLocation " + permissionLocation);
        if (permissionLocation) {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    printLogs(LogTag, "getLocation", "mFusedLocationClient oss" + location);
                    if (location != null) {
                        myCurrentLocation = location;
                        printLogs(LogTag, "getLocation", "mFusedLocationClient " + location);
                        if (myCurrentLocation != null) {
                            currentLat = myCurrentLocation.getLatitude();
                            currentLong = myCurrentLocation.getLongitude();

                            if (isValidLatLng(currentLat, currentLong)) {
                                createMarker(currentLat, currentLong, "Workstation", "Your Location "+currentLat+","+currentLong, BitmapDescriptorFactory.HUE_AZURE);
                                printLogs(LogTag, "mFusedLocationClient", " Your Current Location Longitude - " + currentLong + " Latitude - " + currentLat);
                            } else {
                                printLogs(LogTag, "mFusedLocationClient", "ERROR_314_104 : LOCATION_ERROR");
                                String sTitle = "Error :" + ActivityId + "-104";
                                String sMessage = getLabelFromDb("error_314_104", R.string.error_314_104);
                                String sButtonLabelClose = "Close";
                                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                            }
                        } else {
                            printLogs(LogTag, "mFusedLocationClient", "ERROR_118_105 : LOCATION_ERROR");
                            String sTitle = "Error :" + ActivityId + "-105";
                            String sMessage = getLabelFromDb("error_314_105", R.string.error_314_105);
                            String sButtonLabelClose = "Close";
                            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                        }
                    }
                }
            });
            printLogs(LogTag, "mFusedLocationClient", "getLocation : On Sucess Exit");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        printLogs(LogTag, "onRequestPermissionsResult", "init");
        switch (requestCode) {
            case 1: {
                printLogs(LogTag, "onRequestPermissionsResult", "REQUEST CODE " + requestCode);
                permissionLocation = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
                return;
            }
        }
    }

    private void checkLocationPermission() {
        printLogs(LogTag, "checkLocationPermission", "init");
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                printLogs(LogTag, "checkLocationPermission", "PERMISSION ERROR");
            } else {
                printLogs(LogTag, "checkLocationPermission", "PERMISSION INPUT");
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            }
        } else {
            printLogs(LogTag, "checkLocationPermission", "PERMISSION INPUT");
            permissionLocation = true;
        }
    }



    protected class ErrorTextWatcher implements TextWatcher {
        private AutoCompleteTextView EditView;
        private TextInputLayout EditLayout;

        private ErrorTextWatcher(AutoCompleteTextView EditView, TextInputLayout EditLayout) {
            this.EditView = EditView;
            this.EditLayout = EditLayout;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (EditView.getId()) {
                case R.id.act_physical_address:
                    final String et_err = act_physical_address.getText().toString();
                    validateDeptName(et_err);
                    break;
            }
        }
    }

    protected void createMarker(double latitude, double longitude, String title, String snippet, float iconResID) {
        printLogs(LogTag, "createMarker", "INIT" + latitude + "---" + longitude);
        LatLng loginLatLong = new LatLng(latitude, longitude);
        int height = 100;
        int width = 100;
        BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.location_marker);

        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(loginLatLong));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loginLatLong, 15f));
        mMap.getMaxZoomLevel();
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet)
                .draggable(true)
                .icon(BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(bitmapdraw.getBitmap(), width, height, false))));

    }


    public boolean isValidLatLng(double lat, double lng) {
        printLogs(LogTag, "isValidLatLng", "DATA : " + lat + " -- " + lng);
        if (lat < -90 || lat > 90) {
            return false;
        } else return !(lng < -180) && !(lng > 180);
    }

    public void customRedirector() {
        Intent intent = new Intent(MAddWorksA.this, MDashboardDA.class);
        Bundle activeUri = new Bundle();
        activeUri.putString("student_id", "student_id");
        activeUri.putString("work_x_name", "work_x_name");
        activeUri.putString("work_x_id", "work_x_id");
        intent.putExtras(activeUri);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        printLogs(LogTag, "onOptionsItemSelected", "init");

        if (generator.equals("313")) {


            Bundle inputUri = new Bundle();
            inputUri.putString("generator", "313");
            inputUri.putString("student_id", student_id);
            inputUri.putString("work_x_name", work_x_name);
            inputUri.putString("work_x_id", work_x_id);
            inputUri.putString("mwX_student_name4", mwX_student_name4);
            Intent intent = new Intent(MAddWorksA.this, MChangeSWorkXA.class);
            intent.putExtras(inputUri);
            printLogs(LogTag, "onOptionsItemSelected", "MChangeSWorkXA");
            startActivity(intent);
            finish();

        } else if (generator.equals("160")) {
            Intent intent = new Intent(MAddWorksA.this, MWorkXsDA.class);
            printLogs(LogTag, "onOptionsItemSelected", "MWorkXsDA");
            startActivity(intent);
            finish();
        }else{

            Intent intent = new Intent(MAddWorksA.this, MWorkXsDA.class);
            printLogs(LogTag, "onOptionsItemSelected", "MWorkXsDA");
            startActivity(intent);
            finish();

        }

        return true;
    }


    private void hidesoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterBroadcastIC();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerBroadcastIC();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterBroadcastIC();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcastIC();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcastIC();
    }
}