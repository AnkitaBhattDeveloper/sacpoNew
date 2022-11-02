package za.co.sacpo.grant.xCubeStudent.details;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.fragments.FMap;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.session.OlumsGrantSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeStudent.SDashboardDA;
import za.co.sacpo.grant.xCubeStudent.attendance.SAttDA;
import za.co.sacpo.grant.xCubeStudent.leaves.SLeavesDA;

public class SWorkstationDetailsA extends BaseAPCPrivate implements OnMapReadyCallback{
    private String ActivityId="S189";
    public View mProgressView, mContentView;
    private GoogleMap googleMap;
    private TextView lblView;
    Double loginLat,loginLong,logoutLat,logoutLong,workLat,workLong;
    TextView lbladdress,txtWorkStation;
    String grant_id,mentor_id,seta_user_id,lea_id,generator,grant_adminId,fId;
    Bundle activeUri;
    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId){
        baseApcContext = cnt;
        CAId=cTAId;
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
        grantSessionObj = new OlumsGrantSession(baseApcContext);
        String grant_sesion_id = grantSessionObj.getGrantId();
        if(grant_sesion_id.isEmpty()) {
            grant_id = activeInputUri.getString("grant_id");
            printLogs(LogTag,"onCreate","GRANT_ID_HERE-----> "+grant_id);
        }
        else{
            grant_id = grant_sesion_id;
        }
        generator = activeInputUri.getString("generator");

        printLogs(LogTag,"onCreate","GENERATOR ID "+generator);
        bootStrapInit();
        internetChangeBroadCast();
        fetchVersionData();
        verifyVersion();
    }
    @Override
    protected void fetchVersionData(){
        printLogs(LogTag,"fetchVersionData","exitConnected");
        syncUpdates(baseApcContext,activityIn);
    }

    @Override
    protected void internetChangeBroadCast() {
        registerBroadcastIC();
    }
    @Override
    protected void verifyVersion(){
        printLogs(LogTag,"verifyVersion","init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag,"verifyVersion","isUpdate "+isUpdate);
        printLogs(LogTag,"verifyVersion","isUpdate "+ utilSessionObj.getIsUpdateRequired());
        if(isUpdate==true){
            Intent intent = new Intent(SWorkstationDetailsA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void bootStrapInit() {
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext,activityIn);
        if(isConnected) {
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);

            initMenusCustom(ActivityId,baseApcContext,activityIn);
            printLogs(LogTag,"bootStrapInit","initConnected");
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
    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","a_grant_details");
        setContentView(R.layout.a_workstation_details);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        lbladdress =findViewById(R.id.lbladdress);
        txtWorkStation =findViewById(R.id.txtWorkStation);



    }
    @Override
    protected void initializeInputs(){
        printLogs(LogTag,"initializeInputs","init");
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
        getGrantDetails();
    }
    @Override
    protected void initializeLabels(){
        String Label = getLabelFromDb("h_S189H",R.string.h_S189H);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);
        printLogs(LogTag,"initializeLabels","init");

    }
    @Override
    protected void initializeListeners() {



        printLogs(LogTag,"setLayoutXml","getGrantId"+grant_adminId);


    }
    public void callDataApi(){
        printLogs(LogTag,"callDataApi","init");
        userToken = userSessionObj.getToken();
        if(!TextUtils.isEmpty(userToken)) {
            printLogs(LogTag,"callDataApi","Token Update");
            syncUserData(baseApcContext, activityIn);
            syncStudentData(baseApcContext, activityIn);
            syncGrantData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn,ActivityId);
        }
    }
    private void getGrantDetails() {
        String token = userSessionObj.getToken();
        String grantId = grant_id;
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_S_189;
        FINAL_URL=FINAL_URL+"?token="+token;
        printLogs(LogTag,"getGrantDetails","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "getGrantDetails", "RESPONSE : " + response);
                    String Status = jsonObject.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONArray dataM = jsonObject.getJSONArray(KEY_DATA);
                        String wLat = "",wLon= "";
                        for (int i = 0; i < dataM.length(); i++) {
                            JSONObject object = dataM.getJSONObject(i);
                            txtWorkStation.setText(object.getString("workstationName"));
                            lbladdress.setText(object.getString("physical_address"));
                            wLat = object.getString("e_g_l_latitude");
                            wLon = object.getString("e_g_l_longitude");
                        }

                        if(!wLat.isEmpty()){
                            if(!wLon.isEmpty()) {
                                if (!wLat.equals("null")) {
                                    if (!wLon.equals("null")) {
                                        workLat = Double.parseDouble(wLat);
                                        workLong=Double.parseDouble(wLon);
                                        LatLng point =  new LatLng(workLat,workLong);
                                        CircleOptions circleOptions = new CircleOptions()
                                                .center(point)
                                                .radius(200)
                                                .fillColor(Color.GREEN)
                                                .strokeColor(Color.BLACK)
                                                .strokeWidth(5);
                                        googleMap.addCircle(circleOptions);
                                        LatLng loginLatLong = new LatLng(workLat, workLong);
                                        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(loginLatLong));
                                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loginLatLong,15f));
                                        googleMap.getMaxZoomLevel();
                                    }
                                }
                            }
                        }
                        showProgress(false,mContentView,mProgressView);
                    }
                    else if(Status.equals("2")) {
                        showProgress(false,mContentView,mProgressView);
                    }
                    else{
                        //showProgress(false,mContentView,mProgressView);
                        printLogs(LogTag,"getGrantDetails","ERROR_136_100 : DATA_ERROR");
                        String sTitle="Error :"+ActivityId+"-100";
                        String sMessage=getLabelFromDb("error_S136_100",R.string.error_S136_100);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    printLogs(LogTag,"getGrantDetails","error_try_again : "+e.getMessage());
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
                        printLogs(LogTag,"getGrantDetails","error_try_again : "+error.getMessage());
                        //showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-136";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "*/*");
                // params.put("Content-Type","application/json");

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

        return googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.login_map)));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            if(generator.equals("S103")){
                Intent intent = new Intent(SWorkstationDetailsA.this,SDashboardDA.class);
                printLogs(LogTag,"onOptionsItemSelected","SDashboardDA");
                startActivity(intent);
                finish();
            }
            else if(generator.equals("S120")){
                Intent intent = new Intent(SWorkstationDetailsA.this,SAttDA.class);
                printLogs(LogTag,"onOptionsItemSelected","SAttDA");
                startActivity(intent);
                finish();
            }
            else if(generator.equals("S123")){
                Intent intent = new Intent(SWorkstationDetailsA.this, SLeavesDA.class);
                printLogs(LogTag,"onOptionsItemSelected","SLeavesDA");
                startActivity(intent);
                finish();
            }
            else {
                Intent intent = new Intent(SWorkstationDetailsA.this,SDashboardDA.class);
                printLogs(LogTag,"onOptionsItemSelected","sDashboard");
                startActivity(intent);
                finish();
            }
        }
        return true;
    }
    @Override
    public void onBackPressed() {
            if(generator.equals("S103")){
                Intent intent = new Intent(SWorkstationDetailsA.this,SDashboardDA.class);
                printLogs(LogTag,"onOptionsItemSelected","SDashboardDA");
                startActivity(intent);
                finish();
            }
            else if(generator.equals("S120")){
                Intent intent = new Intent(SWorkstationDetailsA.this,SAttDA.class);
                printLogs(LogTag,"onOptionsItemSelected","SAttDA");
                startActivity(intent);
                finish();
            }
            else if(generator.equals("S123")){
                Intent intent = new Intent(SWorkstationDetailsA.this, SLeavesDA.class);
                printLogs(LogTag,"onOptionsItemSelected","SLeavesDA");
                startActivity(intent);
                finish();
            }
            else {
                Intent intent = new Intent(SWorkstationDetailsA.this,SDashboardDA.class);
                printLogs(LogTag,"onOptionsItemSelected","sDashboard");
                startActivity(intent);
                finish();
            }
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
