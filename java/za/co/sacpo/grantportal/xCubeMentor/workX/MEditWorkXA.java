package za.co.sacpo.grantportal.xCubeMentor.workX;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import za.co.sacpo.grantportal.AppUpdatedA;
import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.fragments.FMap;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grantportal.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.component.Utils;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;

public class MEditWorkXA extends BaseFormAPCPrivate {
    private String ActivityId="164";
    public View mProgressView, mContentView,heading;
    private String KEY_LATE="latitude";
    private String KEY_Long="longitude";
    private String KEY_DEPT="departmentName";
    private String KEY_CODE="departmentCode";
    private String KEY_E_ID="emp_geo_location_id";
    private String KEY_PHYSICAL_ADDRESS="physical_address";
    String work_x_id,longitute,latitude,getLat, getLong;
    private static final float DEFAULT_ZOOM = 15f;
    public AutoCompleteTextView act_physical_address;
    public EditText inputAddDeptName;
    public TextInputLayout inputLayoutAddDeptName;
    public Button btnDeptAdd,btnDeptAddMoveMarker;
    private TextView lblView;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    int MY_PERMISSIONS_REQUEST_FINE_LOCATION =1;
    private Boolean permissionLocation;
    private Location myCurrentLocation;
    private Double currentLat;
    private Double currentLong;
    MEditWorkXA thisClass;
    private Marker marker;
    private LatLng latLng;
    private Geocoder geocoder;


    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId){
        baseApcContext = cnt;
        CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        thisClass = this;
        printLogs(lt,"setBaseApcContextParent","weAreIn");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);
        printLogs(LogTag,"onCreate","init");
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        geocoder = new Geocoder(baseApcContext, Locale.getDefault());
        Bundle activeInputUri = getIntent().getExtras();
      //  work_x_id = Integer.toString(activeInputUri.getInt("work_x_id"));
      //  latitude = Integer.toString(activeInputUri.getInt("latitude"));
     //   longitute = Integer.toString(activeInputUri.getInt("longitute"));


        work_x_id = activeInputUri.getString("work_x_id");
        latitude = activeInputUri.getString("latitude");
        longitute = activeInputUri.getString("longitute");

        printLogs(LogTag,"onCreate","work_x_id "+work_x_id);
        printLogs(LogTag,"onCreate","latitude_Value "+latitude);
        printLogs(LogTag,"onCreate","longitude_Value "+longitute);
        bootStrapInit();
    }

    @Override
    protected void bootStrapInit() {
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext, activityIn);
        if(isConnected) {
            printLogs(LogTag,"bootStrapInit","initConnected");
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);

            initMenusCustom(ActivityId,baseApcContext,activityIn);
            fetchVersionData();
            verifyVersion();
            internetChangeBroadCast();
            initializeViews();
            initBackBtn();
            showProgress(true,mContentView,mProgressView);
            initializeLabels();
            initializeInputs();
            userToken =userSessionObj.getToken();
            syncToken(baseApcContext,activityIn);
            if(TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
            callDataApi();
            fetchData();
            //initializeListeners();
            showProgress(false, mContentView, mProgressView);
            printLogs(LogTag, "bootStrapInit", "exitConnected");
        }
    }
    @Override
    protected void internetChangeBroadCast(){
        printLogs(LogTag,"internetChangeBroadCast","init");
        registerBroadcastIC();
    }
    @Override
    protected void fetchVersionData(){
        printLogs(LogTag,"fetchVersionData","exitConnected");
        syncUpdates(baseApcContext,activityIn);
    }
    @Override
    protected void verifyVersion() {
        printLogs(LogTag,"verifyVersion","init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag,"verifyVersion","isUpdate "+ utilSessionObj.getIsUpdateRequired());
        if(isUpdate){
            Intent intent = new Intent(MEditWorkXA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void setLayoutXml(){
        setContentView(R.layout.a_m_edit_workx);
        printLogs(LogTag,"setLayoutXml","a_m_edit_workx");
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        heading = findViewById(R.id.heading);
        inputAddDeptName = (EditText) findViewById(R.id.inputAddDeptName);
        inputLayoutAddDeptName = (TextInputLayout) findViewById(R.id.inputLayoutAddDeptName);
        btnDeptAdd = (Button) findViewById(R.id.btnDeptAdd);
        btnDeptAddMoveMarker = (Button) findViewById(R.id.btnDeptAddMoveMarker);
        act_physical_address = findViewById(R.id.act_physical_address);
        printLogs(LogTag,"initializeViews","exit");
    }
    @Override
    protected void initializeLabels(){
        printLogs(LogTag,"initializeLabels","init");
        String Label = getLabelFromDb("lbl_164_add_wx_name",R.string.lbl_164_add_wx_name);
        lblView = (TextView)findViewById(R.id.lblAddDeptName);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("h_164",R.string.h_164);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_164_btnDeptAdd",R.string.lbl_164_btnDeptAdd);
        lblView = (TextView)findViewById(R.id.btnDeptAdd);
        btnDeptAdd.setText(Label);
        Label = getLabelFromDb("lbl_164_btnDeptMarkerMove",R.string.lbl_164_btnDeptMarkerMove);
        lblView = (TextView)findViewById(R.id.btnDeptAddMoveMarker);
        btnDeptAddMoveMarker.setText(Label);
        Label = getLabelFromDb("lbl_164_btn_work_address", R.string.lbl_164_btn_work_address);
        lblView = findViewById(R.id.lblWorkstationAddress);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            btnDeptAdd.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            btnDeptAddMoveMarker.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            inputAddDeptName.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            act_physical_address.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
        }

        }
    @Override
    protected void initializeInputs(){
        printLogs(LogTag,"initializeInputs","init");
        if (mMap == null) {
            SupportMapFragment mapFragment = (FMap) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap)
                {
                mMap = googleMap;
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                mMap.getUiSettings().setZoomControlsEnabled(true);
                initializeListeners();

                final ScrollView mScrollView = findViewById(R.id.scroll_layout);
                ((FMap) getSupportFragmentManager().findFragmentById(R.id.map))
                    .setListener(new FMap.OnTouchListener() {
                        @Override
                        public void onTouch()
                        {
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
        printLogs(LogTag,"initializeInputs","init");


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

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {



            /*    marker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(latLng.latitude,latLng.longitude))
                        .draggable(true).visible(true));


                latLng = point;
                List<Address> addresses = new ArrayList<>();
                try {
                    addresses = geocoder.getFromLocation(point.latitude, point.longitude,1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                android.location.Address address = addresses.get(0);

                if (address != null) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < address.getMaxAddressLineIndex(); i++){
                        sb.append(address.getAddressLine(i) + "\n");

                    }
                    Toast.makeText(thisClass, "address"+sb.toString(), Toast.LENGTH_SHORT).show();
                   printLogs(LogTag,"markerPosition","PhysicalAddress"+point);

                }

                if (marker != null) {
                    marker.remove();
                }

                marker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(latLng.latitude,
                                latLng.longitude))
                        .anchor(0.5f, 0.5f)
                        //.title("Address"+point)
                        .title("You Mark Here")
                        .snippet(new LatLng(latLng.latitude,
                                latLng.longitude)+"")
                        .draggable(true)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
*/
                        //.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_login_map_marker)));


            }
        });

        btnDeptAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });


        btnDeptAddMoveMarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm2();
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
                btnDeptAddMoveMarker.setVisibility(View.VISIBLE);
                btnDeptAdd.setVisibility(View.GONE);
                printLogs(LogTag,"setOnMarkerDragListener","onMarkerDragEnd "+mMarker.getPosition().latitude+"=="+mMarker.getPosition().longitude);
                mMap.animateCamera(CameraUpdateFactory.newLatLng(mMarker.getPosition()));


            }

            @Override
            public void onMarkerDrag(Marker mMarker) {
            }
        });
    }

    private void geoLocation() {

        btnDeptAddMoveMarker.setVisibility(View.GONE);
        btnDeptAdd.setVisibility(View.VISIBLE);


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
            //createMarker2(new LatLng(address.getLatitude(),address.getLongitude()),address.getAddressLine(0),"", BitmapDescriptorFactory.HUE_AZURE);



        }
    }

    private void moveCamera(LatLng latLng,float zoom,String title){


        printLogs(LogTag,"moveCamera","mmoving camera to latlong :"+latLng.latitude + ",lng: " + latLng.longitude );
        getLat = String.valueOf(latLng.latitude);
        getLong = String.valueOf(latLng.longitude);
        printLogs(LogTag,"latlong","getLatLong__"+getLat+","+getLong);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));

        MarkerOptions  markerOptions = new MarkerOptions()
                .position(latLng)
                .title(title);

    /*    if(!title.equals(title)){
            MarkerOptions options = new MarkerOptions().position(latLng).title(title);
            mMap.addMarker(markerOptions);
        }
         */



        mMap.addMarker(markerOptions);
        hidesoftKeyboard();


    }



    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void fetchData(){
            String token = userSessionObj.getToken();
            String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.WORKSTATION_DETAILS;
            FINAL_URL=FINAL_URL+"?token="+token+"&work_x_id="+work_x_id;
            printLogs(LogTag,"fetchData","URL : "+FINAL_URL);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchData", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                        if(Status.equals("1")){
                            JSONObject dataM = outputJson.getJSONObject(KEY_DATA);
                            String mWName3 = dataM.getString("e_g_l_department_name");
                            inputAddDeptName.setText(mWName3);
                            String physical_Address = dataM.getString("e_g_l_physical_address");
                            act_physical_address.setText(physical_Address);
                        }else if(Status.equals("2")){

                        }
                        else{
                            //showProgress(false,mContentView,mProgressView);
                            printLogs(LogTag,"fetchData","ERROR_164_100 : DATA_ERROR");
                            String sTitle="Error :"+ActivityId+"-100";
                            String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                            String sButtonLabelClose="Close";
                            ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                        }
                    } catch (JSONException e) {
                        printLogs(LogTag,"fetchData","ERROR_164_101 : "+e.getMessage());
                        e.printStackTrace();
                        //showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-101";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            printLogs(LogTag,"fetchData","error_try_again : "+error.getMessage());
                            //showProgress(false,mContentView,mProgressView);
                            String sTitle="Error :"+ActivityId+"-102";
                            String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                            String sButtonLabelClose="Close";
                            ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

                        }
                    }){
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
    public void validateForm() {
        boolean cancel = false;
        if (!validateDeptName(inputAddDeptName,inputLayoutAddDeptName)) {
            cancel = true;
        }
        else if(!isValidate(currentLat,currentLong)){
            cancel =true;
        }
        if (cancel) {
            return;
        } else {
            showProgress(true,mContentView,mProgressView);
            this.FormSubmit();
        }
        printLogs(LogTag,"validateForm","exit");
    }


    public void validateForm2() {
        boolean cancel = false;
        if (!validateDeptName(inputAddDeptName,inputLayoutAddDeptName)) {
            cancel = true;
        }
        else if(!isValidate(currentLat,currentLong)){
            cancel =true;
        }
        if (cancel) {
            return;
        } else {
            showProgress(true,mContentView,mProgressView);
            this.FormSubmit2();
        }
        printLogs(LogTag,"validateForm","exit");
    }

     public void FormSubmit() {
         //call when location search is in process

         //physical address lat,long..
         final String pos_lat = getLat;
         final String pos_long = getLong;
         printLogs(LogTag, "SubmitForm", "latLong__Value____" + pos_lat+","+pos_long);
         final String physical_Address = act_physical_address.getText().toString();


         final String latitude=Double.toString(currentLat);
         final String longitude=Double.toString(currentLong);
         String token = userSessionObj.getToken();
         final String dept_name=inputAddDeptName.getText().toString();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.EDIT_WORKSTATION;
        printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);
         JSONObject jsonBody = new JSONObject();
         try {
             jsonBody.put("token", token);
             jsonBody.put(KEY_LATE, latitude);
             jsonBody.put(KEY_Long, longitude);
             jsonBody.put(KEY_DEPT ,dept_name);
             jsonBody.put(KEY_E_ID,work_x_id);
             jsonBody.put(KEY_PHYSICAL_ADDRESS,physical_Address);
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
                    if(Status.equals("1")){
                        //JSONObject dataM = outputJson.getJSONObject(KEY_DATA);

                        printLogs(LogTag,"FormSubmit","SUCCESS : "+Status);
                        showProgress(false,mContentView,mProgressView);
                        String sTitle=getLabelFromDb("m_164_title",R.string.m_164_title);
                        String sMessage=getLabelFromDb("m_164_message",R.string.m_164_message);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showSuccessDialogMEditWorkXA(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose,thisClass);

                    }

                    else{
                        showProgress(false,mContentView,mProgressView);
                        String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                       /* mPasswordView.setError(sMessage);
                        mPasswordView.requestFocus();*/
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false,mContentView,mProgressView);
                    String sTitle="Error :"+ActivityId+"-164";
                    String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                    String sButtonLabelClose="Close";
                    ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String sTitle="Error :"+ActivityId+"-105";
                        String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                        //Toast.makeText(getApplicationContext(),"ERROR LOGIN IN. PLEASE TRY AGAIN.", Toast.LENGTH_SHORT).show();
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
         RequestQueue requestQueue = Volley.newRequestQueue(MEditWorkXA.this);
         jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                 10000,
                 DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                 DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
         requestQueue.add(jsonObjectRequest);
    }

    public void FormSubmit2() {
//call when MarkerMove from his current position...
        //physical address lat,long..
        final String pos_lat = getLat;
        final String pos_long = getLong;

        final String physical_Address = act_physical_address.getText().toString();


        final String latitude=Double.toString(currentLat);
        final String longitude=Double.toString(currentLong);
        String token = userSessionObj.getToken();
        final String dept_name=inputAddDeptName.getText().toString();

        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.EDIT_WORKSTATION;
        FINAL_URL=FINAL_URL+"/token/"+token;
        printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.PUT,FINAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject outputJson = null;
                printLogs(LogTag,"FormSubmit","RESPONSE : "+response);
                try {
                    printLogs(LogTag,"FormSubmit","RESPONSE : "+response);
                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        //JSONObject dataM = outputJson.getJSONObject(KEY_DATA);

                        printLogs(LogTag,"FormSubmit","SUCCESS : "+Status);
                        showProgress(false,mContentView,mProgressView);
                        String sTitle=getLabelFromDb("m_164_title",R.string.m_164_title);
                        String sMessage=getLabelFromDb("m_164_message",R.string.m_164_message);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showSuccessDialogMEditWorkXA(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose,thisClass);

                    }

                    else{
                        showProgress(false,mContentView,mProgressView);
                        String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                       /* mPasswordView.setError(sMessage);
                        mPasswordView.requestFocus();*/
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false,mContentView,mProgressView);
                    String sTitle="Error :"+ActivityId+"-164";
                    String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                    String sButtonLabelClose="Close";
                    ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String sTitle="Error :"+ActivityId+"-105";
                        String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                        //Toast.makeText(getApplicationContext(),"ERROR LOGIN IN. PLEASE TRY AGAIN.", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_LATE, latitude);
                params.put(KEY_Long, longitude);
                params.put(KEY_DEPT ,dept_name);
                params.put(KEY_E_ID,work_x_id);
                params.put(KEY_PHYSICAL_ADDRESS,physical_Address);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MEditWorkXA.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }


    public void callDataApi(){
        printLogs(LogTag,"callDataApi","init");
        userToken = userSessionObj.getToken();
        if(!TextUtils.isEmpty(userToken)) {
            printLogs(LogTag,"callDataApi","Token Update");
            syncUserData(baseApcContext, activityIn);
            syncMentorData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn,ActivityId);

        }
    }
    public boolean validateLatitude(EditText inputDeptAddLatitude,TextInputLayout inputLayoutDeptAddLatitude) {
        String lat = inputDeptAddLatitude.getText().toString().trim();
        setCustomError(inputLayoutDeptAddLatitude,null,inputDeptAddLatitude);
        if (lat.isEmpty()) {
            String sMessage = getLabelFromDb("error_dept_lat",R.string.error_dept_lat);
            setCustomError(inputLayoutDeptAddLatitude,sMessage,inputDeptAddLatitude);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutDeptAddLatitude,inputDeptAddLatitude);
            return true;
        }
    }
    public boolean validateLongtitude(EditText inputDeptAddLongitude,TextInputLayout inputLayoutDeptAddLongitude) {
        String  longitude = inputDeptAddLongitude.getText().toString().trim();
        setCustomError(inputLayoutDeptAddLongitude,null,inputDeptAddLongitude);
        if (longitude.isEmpty()) {
            String sMessage = getLabelFromDb("error_dept_long",R.string.error_dept_long);
            setCustomError(inputLayoutDeptAddLongitude,sMessage,inputDeptAddLongitude);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutDeptAddLongitude,inputDeptAddLongitude);
            return true;
        }
    }
    public boolean validateDeptName(EditText inputAddDeptName,TextInputLayout inputLayoutAddDeptName) {
        String  dept_name = inputAddDeptName.getText().toString().trim();
        setCustomError(inputLayoutAddDeptName,null,inputAddDeptName);
        if (dept_name.isEmpty() ) {
            String sMessage = getLabelFromDb("error_dept_name",R.string.error_dept_name);
            setCustomError(inputLayoutAddDeptName,sMessage,inputAddDeptName);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutAddDeptName,inputAddDeptName);
            return true;
        }
    }
    public boolean validateDeptCode(EditText inputDeptCode,TextInputLayout inputLayoutwXDeptCode) {
        String  dept_code = inputDeptCode.getText().toString().trim();
        setCustomError(inputLayoutwXDeptCode,null,inputDeptCode);
        if (dept_code.isEmpty()) {
            String sMessage = getLabelFromDb("error_164_wx_code",R.string.error_164_wx_code);
            setCustomError(inputLayoutwXDeptCode,sMessage,inputDeptCode);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutwXDeptCode,inputDeptCode);
            return true;
        }
    }

    private void getLocation(){
        myCurrentLocation = null;
        permissionLocation = false;
        printLogs(LogTag,"getLocation","init");
        checkLocationPermission();
        printLogs(LogTag,"getLocation","permissionLocation "+permissionLocation);
        if(permissionLocation ==true) {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            printLogs(LogTag,"getLocation","mFusedLocationClient oss"+location);
                            if (location != null) {
                                myCurrentLocation = location;
                                printLogs(LogTag,"getLocation","mFusedLocationClient "+location);
                                if(myCurrentLocation !=null) {
                                    currentLat = Double.valueOf(latitude);
                                    currentLong = Double.valueOf(longitute);

                                    if (isValidLatLng(currentLat, currentLong)) {
                                        createMarker(currentLat,currentLong,"Workstation","", BitmapDescriptorFactory.HUE_AZURE);
                                        printLogs(LogTag, "mFusedLocationClient", " Your Current Location \n Longitude - "+currentLong+" \n Latitude - "+currentLat);
                                    }
                                    else{
                                        printLogs(LogTag,"mFusedLocationClient","error_try_again : LOCATION_ERROR");
                                        String sTitle="Error :"+ActivityId+"-104";
                                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                                        String sButtonLabelClose="Close";
                                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                                    }
                                }
                                else{
                                    printLogs(LogTag,"mFusedLocationClient","ERROR_118_105 : LOCATION_ERROR");
                                    String sTitle="Error :"+ActivityId+"-105";
                                    String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                                    String sButtonLabelClose="Close";
                                    ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                                }
                            }
                        }
                    });
            printLogs(LogTag,"mFusedLocationClient","getLocation : On Sucess Exit");
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        printLogs(LogTag,"onRequestPermissionsResult","init");
        switch (requestCode) {
            case 1: {
                printLogs(LogTag,"onRequestPermissionsResult","REQUEST CODE "+requestCode);
                permissionLocation = grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED;
                return;
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
                printLogs(LogTag,"checkLocationPermission","PERMISSION INPUT");
                ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            }
        } else {
            printLogs(LogTag,"checkLocationPermission","PERMISSION INPUT");
            permissionLocation = true;
        }
    }

    protected class ErrorTextWatcher implements TextWatcher {
        private EditText EditView;
        private TextInputLayout EditLayout;
        private ErrorTextWatcher(EditText EditView,TextInputLayout EditLayout) {
            this.EditView = EditView;
            this.EditLayout = EditLayout;
        }
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        public void afterTextChanged(Editable editable) {
            if (EditView.getId() == R.id.inputAddDeptName) {
                validateDeptName(EditView, EditLayout);
            }
        }
    }
    protected Marker createMarker(double latitude, double longitude, String title, String snippet, float iconResID) {
        printLogs(LogTag,"createMarker","INIT"+latitude+"---"+longitude);
        LatLng loginLatLong = new LatLng(latitude, longitude);
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(loginLatLong));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loginLatLong,15f));
        mMap.getMaxZoomLevel();
        int height = 100;
        int width = 100;
        BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.location_marker);
        return mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet)
                .draggable(true)
                .icon(BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(bitmapdraw.getBitmap(), width, height, false))));

    }


    public boolean isValidLatLng(double lat, double lng){
        printLogs(LogTag,"isValidLatLng","DATA : "+lat+" -- "+lng);
        if(lat < -90 || lat > 90)
        {
            return false;
        }
        else return !(lng < -180) && !(lng > 180);
    }
    public boolean isValidate(double lat, double lng){
        printLogs(LogTag,"isValidate","DATA : "+lat+" -- "+lng);
        if(lat < -90 || lat > 90) {
            String sTitle="Error :"+ActivityId+"-106";
            String sMessage=getLabelFromDb("error_164_106",R.string.error_164_106);
            String sButtonLabelClose="Close";
            ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
            return false;
        }
        else if(lng < -180 || lng > 180) {
            String sTitle="Error :"+ActivityId+"-107";
            String sMessage=getLabelFromDb("error_164_107",R.string.error_164_106);
            String sButtonLabelClose="Close";
            ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
            return false;
        }
        return true;
    }


    public void customRedirector(){

        Intent intent = new Intent(MEditWorkXA.this, MWorkXsDA.class);
        startActivity(intent);
        printLogs(LogTag,"customRedirectorr","MWorkXsDA");
        finish();
    }

    private void hidesoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(MEditWorkXA.this,MWorkXsDA.class);
            printLogs(LogTag,"onOptionsItemSelected","MWorkXsDA");
            startActivity(intent);
            finish();
        }
        return true;
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterBroadcastIC();
    }
    @Override
    protected void onResume() {
        super.onResume();
        checkInternetConnection();
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
        checkInternetConnection();
        registerBroadcastIC();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcastIC();
    }
}