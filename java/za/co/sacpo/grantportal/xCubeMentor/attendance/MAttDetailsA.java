package za.co.sacpo.grantportal.xCubeMentor.attendance;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
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

import java.util.HashMap;
import java.util.Map;

import za.co.sacpo.grantportal.AppUpdatedA;
import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.fragments.FMap;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.component.Utils;
import za.co.sacpo.grantportal.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;


public class MAttDetailsA extends BaseAPCPrivate  implements OnMapReadyCallback {
    //TODO :: Map Correction
    private String ActivityId="M112";
    public View mProgressView, mContentView,heading;

    private TextView lblView;
    TextView txtDay,txtLoginDate,txtLoginTime,txtLogoutDate,txtLogoutTime,txtLearnerRemark,lblLearnerRemark;
    private GoogleMap googleMap;
    String student_id,m_student_name,generator,attendance_id,diffMeterLogin,diffMeterLogout,month,year,date_input;
    Double loginLat,loginLong,logoutLat,logoutLong,workLat,workLong;
    TextView txtloginLat, txtloginLong ,  txtlogoutLat  ,  txtLogoutLong   ;

    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId){
        baseApcContext = cnt;CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        printLogs(lt,"setBaseApcContextParent","weAreIn");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);
        printLogs(LogTag,"onCreate","init");
        Bundle activeInputUri = getIntent().getExtras();
        m_student_name = activeInputUri.getString("m_student_name");
        attendance_id = activeInputUri.getString("attendance_id");
        student_id = activeInputUri.getString("student_id");
        month = activeInputUri.getString("month");
        year = activeInputUri.getString("year");
        date_input = activeInputUri.getString("date_input");
        generator = activeInputUri.getString("generator");
        printLogs(LogTag,"onCreate","m_student_name "+m_student_name);
        printLogs(LogTag,"onCreate","student ID "+student_id);
        printLogs(LogTag,"onCreate","Attendance ID "+attendance_id);
        printLogs(LogTag,"onCreate","GENERATOR ID "+generator);
        bootStrapInit();

    }
    @Override
    protected void bootStrapInit() {
        Boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext,activityIn);
        if(isConnected ==true) {
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);

            initMenusCustom(ActivityId,baseApcContext,activityIn);
            printLogs(LogTag,"bootStrapInit","initConnected");
            internetChangeBroadCast();
            fetchVersionData();
            verifyVersion();
            initializeViews();
            initBackBtn();
            showProgress(true,mContentView,mProgressView);

            initializeLabels();
            initializeListeners();
            userToken =userSessionObj.getToken();
            syncToken(baseApcContext,activityIn);
            if(TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
            callDataApi();
            initializeInputs();
            printLogs(LogTag,"onCreate","exitConnected");
        }
    }

    protected void fetchVersionData(){
        printLogs(LogTag,"fetchVersionData","exitConnected");
        syncUpdates(baseApcContext,activityIn);
    }

    @Override
    protected void internetChangeBroadCast() {
        registerBroadcastIC();
    }

    protected void verifyVersion(){
        printLogs(LogTag,"verifyVersion","init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag,"verifyVersion","isUpdate "+isUpdate);
        printLogs(LogTag,"verifyVersion","isUpdate "+ utilSessionObj.getIsUpdateRequired());
        if(isUpdate==true){
            Intent intent = new Intent(MAttDetailsA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","a_m_attendance_details");
        setContentView(R.layout.a_m_attendance_details);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        heading = findViewById(R.id.heading);
        lblLearnerRemark = findViewById(R.id.lblLearnerRemark);
        txtloginLat=(TextView) findViewById(R.id.txtloginLat);
        txtloginLong=(TextView) findViewById(R.id.txtloginLong);
        txtlogoutLat=(TextView) findViewById(R.id.txtlogoutLat);
        txtLogoutLong=(TextView) findViewById(R.id.txtLogoutLong);
        txtDay=(TextView) findViewById(R.id.txtDay);
        txtLoginDate=(TextView) findViewById(R.id.txtLoginDate);
        txtLoginTime=(TextView) findViewById(R.id.txtLoginTime);
        txtLogoutDate=(TextView) findViewById(R.id.txtLogoutDate);
        txtLogoutTime=(TextView) findViewById(R.id.txtLogoutTime);
        txtLearnerRemark = findViewById(R.id.txtLearnerRemark);
    }
    @Override
    protected void initializeInputs(){
        printLogs(LogTag,"initializeInputs","init");

       /*SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        MapsInitializer.initialize(getApplicationContext());*/

        if (googleMap == null) {
            SupportMapFragment mapFragment = (FMap) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMapI)
                {
                    googleMap = googleMapI;
                    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    googleMap.getUiSettings().setZoomControlsEnabled(true);

                    final ScrollView mScrollView = findViewById(R.id.scroll_layout); //parent scrollview in xml, give your scrollview id value
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
        MapsInitializer.initialize(getApplicationContext());

        getAttendanceDetails();
    }

    @Override
    protected void initializeLabels(){

        printLogs(LogTag,"initializeLabels","init");
        String Label = getLabelFromDb("lbl_S112_login_data",
                R.string.lbl_S112_login_data);
        lblView = (TextView)findViewById(R.id.lblLoginData);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S112_logout_data",R.string.lbl_S112_logout_data);
        lblView = (TextView)findViewById(R.id.lblLogoutData);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S112_login_lat",R.string.lbl_S112_login_lat);
        lblView = (TextView)findViewById(R.id.lblloginLat);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S112_login_long",R.string.lbl_S112_login_long);
        lblView = (TextView)findViewById(R.id.lblloginLong);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S112_logout_lat",R.string.lbl_S112_logout_lat);
        lblView = (TextView)findViewById(R.id.lbllogoutLat);
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S112_logout_long",R.string.lbl_S112_logout_long);
        lblView = (TextView)findViewById(R.id.lbllogoutLong);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S112_login_date",R.string.lbl_S112_login_date);
        lblView = (TextView)findViewById(R.id.lblLoginDate);
        lblView.setText(Label);

        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S112_learner_remark",R.string.lbl_S112_learner_remark);
        lblView = (TextView)findViewById(R.id.lblLearnerRemark);
        lblView.setText(Label);

        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S112_login_time",R.string.lbl_S112_login_time);
        lblView = (TextView)findViewById(R.id.lblLoginTime);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S112_logout_date",R.string.lbl_S112_logout_date);
        lblView = (TextView)findViewById(R.id.lblLogoutDate);
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S112_logout_time",R.string.lbl_S112_logout_time);
        lblView = (TextView)findViewById(R.id.lblLogoutTime);
        lblView.setText(Label);

        Label = getLabelFromDb("h_S112",R.string.h_S112);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
        }

    }
    @Override
    protected void initializeListeners() {

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
    private void getAttendanceDetails() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.MENTOR_ATTENDANCE_DETAILS;
        FINAL_URL=FINAL_URL+"?token="+token+"&attendance_id="+attendance_id;
        printLogs(LogTag,"getAttendanceDetails","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "getAttendanceDetails", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONObject dataM = outputJson.getJSONObject(KEY_DATA);
                        txtLoginDate.setText(dataM.getString("s_a_login_date"));
                        txtLoginTime.setText(dataM.getString("s_a_login_time"));
                        txtLogoutDate.setText(dataM.getString("s_a_logout_date"));
                        txtLogoutTime.setText(dataM.getString("s_a_logout_time"));
                        txtDay.setText(dataM.getString("day"));
                        String remark =dataM.getString("learner_remark");
                        if(!remark.equalsIgnoreCase("-")){
                            if(!remark.isEmpty()){
                                txtLearnerRemark.setText(remark);
                                txtLearnerRemark.setVisibility(View.VISIBLE);
                                lblLearnerRemark.setVisibility(View.VISIBLE);
                            }
                            else{
                                txtLearnerRemark.setVisibility(View.GONE);
                                lblLearnerRemark.setVisibility(View.GONE);
                            }
                        }

                        txtloginLat.setText(dataM.getString("login_latitude"));
                        txtloginLong.setText(dataM.getString("login_longitude"));
                        txtlogoutLat.setText(dataM.getString("logout_latitude"));
                        txtLogoutLong.setText(dataM.getString("logout_longitude"));

                        String linLat = dataM.getString("login_latitude");

                        String linLon = dataM.getString("login_longitude");
                        if(!linLat.equals("0")) {
                            if (!linLat.isEmpty()) {
                                if (!linLat.equals("null")) {
                                    if (linLat != null) {
                                        if (!linLon.equals("0")) {
                                            if (!linLon.isEmpty()) {
                                                if (!linLon.equals("null")) {
                                                    if (linLon != null) {
                                                        loginLat = Double.parseDouble(linLat);
                                                        loginLong = Double.parseDouble(linLon);
                                                        createMarker(loginLat, loginLong, "Login", "", BitmapDescriptorFactory.HUE_AZURE);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        String loLat = dataM.getString("logout_latitude");
                        String loLon = dataM.getString("logout_longitude");
                        if(!loLat.equals("0")) {
                            if (!loLat.isEmpty()) {
                                if (!loLat.equals("null")) {
                                    if (loLat != null) {
                                        if(!loLon.equals("0")){
                                            if (!loLon.isEmpty()) {
                                                if (!loLon.equals("null")) {
                                                    if (loLon != null) {
                                                        logoutLat = Double.parseDouble(loLat);
                                                        logoutLong = Double.parseDouble(loLon);
                                                        createMarker(logoutLat, loginLong, "Logout", "", BitmapDescriptorFactory.HUE_AZURE);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        String wLat = dataM.getString("workstation_latitude");
                        String wLon = dataM.getString("workstation_longitude");
                        if(!wLat.equals("0")) {
                            if(!wLat.equals("0")) {
                                if (!wLon.equals("0")) {
                                    if (!wLon.isEmpty()) {
                                        if (wLat != null) {
                                            if (!wLat.equals("null")) {
                                                if (wLon != null) {
                                                    if (!wLon.equals("null")) {

                                                        workLat = Double.parseDouble(wLat);
                                                        workLong = Double.parseDouble(wLon);
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
                            }
                        }
                        diffMeterLogin =dataM.getString("login_coord_diff");

                        diffMeterLogout = dataM.getString("logout_coord_diff");

                        showProgress(false,mContentView,mProgressView);

                    }

                    else if(Status.equals("2")) {
                        showProgress(false,mContentView,mProgressView);
                    }

                    else{
                        //showProgress(false,mContentView,mProgressView);
                        printLogs(LogTag,"getAttendanceDetails","error_try_again : DATA_ERROR");
                        String sTitle="Error :"+ActivityId+"-100";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    printLogs(LogTag,"getAttendanceDetails","error_try_again : "+e.getMessage());
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
                        printLogs(LogTag,"getAttendanceDetails","error_try_again : "+error.getMessage());
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
    @Override
    public void onMapReady(GoogleMap gMap) {
        googleMap = gMap;
        printLogs(LogTag,"onMapReady","MAP READY");
        /*LatLng loginLatLong = new LatLng(loginLat, loginLong);
        googleMap.addMarker(new MarkerOptions().position(loginLatLong).title("Login").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(loginLatLong));

        LatLng logoutLatLong = new LatLng(logoutLat, logoutLong);
        googleMap.addMarker(new MarkerOptions().position(logoutLatLong).title("Logout").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        */
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(logoutLatLong));

    }

    protected Marker createMarker(double latitude, double longitude, String title, String snippet, float iconResID) {
        printLogs(LogTag,"createMarker","INIT"+latitude+"---"+longitude);
        int height = 100;
        int width = 100;
        BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.location_marker);
        return googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(bitmapdraw.getBitmap(), width, height, false))));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Bundle inputUri = new Bundle();
            inputUri.putString("student_id", student_id);
            inputUri.putString("m_student_name", m_student_name);
            inputUri.putString("generator", generator);
            inputUri.putString("year", year);
            inputUri.putString("month", month);
            inputUri.putString("date_input", date_input);
            printLogs(LogTag,"onOptionsItemSelected","MAttMonthlyA");
            Intent intent = new Intent(MAttDetailsA.this, MAttMonthlyA.class);
            intent.putExtras(inputUri);
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