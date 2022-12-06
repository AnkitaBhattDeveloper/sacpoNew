package za.co.sacpo.grant.xCubeStudent.attendance;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.adapter.SAttAdapter;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dataObj.SAttObj;
import za.co.sacpo.grant.xCubeLib.db.attDetailsArray;
import za.co.sacpo.grant.xCubeLib.db.attDetailsArrayAdapter;
import za.co.sacpo.grant.xCubeLib.db.attListArray;
import za.co.sacpo.grant.xCubeLib.db.attListDetailsAdapter;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsGrantSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsStudentSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;

public class SAttDA extends BaseAPCPrivate {
    private String ActivityId="S111";
    public View mProgressView, mContentView,mProgressRView, mContentRView,heading;
    public TextView lblView;
    public LinearLayout LLinformationContainer,LLAttendanceContainer;

    public Bundle activeUri;
    public String date_input,generator,year_input,month_input;

    private int selectedYear,selectedMonth;
    private RecyclerView recyclerViewQ;
    public SAttObj rDataObj = new SAttObj();
    private List<SAttObj.Item> rDataObjList = null;
    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId){
        baseApcContext = cnt;
        CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        CAId = CAId;
        printLogs(lt,"setBaseApcContextParent","weAreIn");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);
        printLogs(LogTag,"onCreate","init");
        Intent inputIntent = getIntent();
        Bundle activeInputUri = getIntent().getExtras();
        year_input=null;
        month_input=null;
        if (inputIntent.hasExtra("date_input")) {
            date_input = activeInputUri.getString("date_input");
            year_input=date_input.substring(0,4);
            month_input=date_input.substring(4,6);
            printLogs(LogTag,"onCreate","Date_Input__ "+date_input+"--"+year_input+"--"+month_input);
        }
        if (inputIntent.hasExtra("generator")) {
            generator = activeInputUri.getString("generator");
            printLogs(LogTag,"onCreate","GENERATOR ID "+generator);
        }
        printLogs(LogTag,"onCreate","Inputs "+date_input+"--"+year_input+"--"+month_input);
        bootStrapInit();
    }
    @Override
    protected void bootStrapInit() {
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext,activityIn);
        if(isConnected) {
            printLogs(LogTag,"bootStrapInit","initConnected");
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);
            initMenusCustom(ActivityId,baseApcContext,activityIn);
            initBackBtn();
            initializeViews();
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
            callHeaderBuilder();
            fetchData(selectedYear,selectedMonth);
            showProgress(false,mContentView,mProgressView);
        }else{
            printLogs(LogTag,"bootStrapInit","initConnected");
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);
            initMenusCustom(ActivityId,baseApcContext,activityIn);
            initBackBtn();
            initializeViews();
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
            callHeaderBuilder();
            fetchOfflineData(selectedYear,selectedMonth);
            showProgress(false,mContentView,mProgressView);
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
    protected void verifyVersion(){
        printLogs(LogTag,"verifyVersion","init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag,"verifyVersion","isUpdate "+isUpdate);
        printLogs(LogTag,"verifyVersion","isUpdate "+ utilSessionObj.getIsUpdateRequired());
        if(isUpdate){
            Intent intent = new Intent(SAttDA.this,AppUpdatedA.class);
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
        printLogs(LogTag,"setLayoutXml","a_attendance");
        setContentView(R.layout.a_attendance);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mContentRView = findViewById(R.id.content_container_r);
        mProgressRView = findViewById(R.id.progress_bar_r);
        heading = findViewById(R.id.heading);
        LLinformationContainer = findViewById(R.id.informationContainer);
        LLAttendanceContainer = findViewById(R.id.attendanceContainer);

        rDataObjList = rDataObj.getITEMS();
        recyclerViewQ = (RecyclerView) findViewById(R.id.rVAttendance);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);
        recyclerViewQ.setNestedScrollingEnabled(false);

        assert recyclerViewQ != null;
        setupRecyclerView((RecyclerView) recyclerViewQ);
        printLogs(LogTag,"initializeViews","exit");
    }
    @Override
    protected void initializeLabels(){
        printLogs(LogTag,"initializeLabels","init");
        String Label =  getLabelFromDb("h_S111",R.string.h_S111);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("l_S111_no_active_grant",R.string.l_S111_no_active_grant);
        lblView = (TextView)findViewById(R.id.iNoActiveGrant);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
        }
    }
    @Override
    protected void initializeInputs(){
        LLAttendanceContainer.setVisibility(View.GONE);
        printLogs(LogTag,"initializeInputs","init");
        selectedYear=Integer.parseInt(year_input);
        selectedMonth=Integer.parseInt(month_input);

        studentSessionObj = new OlumsStudentSession(baseApcContext);
        boolean isGrantActive= studentSessionObj.getIsActiveGrant();
        //isGrantActive= false;

        if(isGrantActive) {
            printLogs(LogTag,"isGrantActive","if_Condition"+isGrantActive);
            String spinnerOptionYear= getLabelFromDb("l_S111_spinner_year",R.string.l_S111_spinner_option_year);
            String spinnerOptionMonth= getLabelFromDb("l_S111_spinner_year",R.string.l_S111_spinner_option_month);
            grantSessionObj = new OlumsGrantSession(baseApcContext);
            String grantId = grantSessionObj.getGrantId();
            int grantIdInt = 0;
            if(!TextUtils.isEmpty(grantId)) {
                grantIdInt =Integer.parseInt(grantId);
            }
            if (grantIdInt > 0) {

                LLinformationContainer.setVisibility(View.GONE);
                LLAttendanceContainer.setVisibility(View.VISIBLE);
            }
            else{
                LLAttendanceContainer.setVisibility(View.GONE);
                LLinformationContainer.setVisibility(View.VISIBLE);
            }
        }
        else{
            LLAttendanceContainer.setVisibility(View.GONE);
            LLinformationContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initializeListeners() {
        printLogs(LogTag,"initializeListeners","init");
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

    public void fetchData(int selectedYearInput, int selectedMonthInput){
        showProgress(true,mContentRView,mProgressRView);
        String token = userSessionObj.getToken();
        String grantId = studentSessionObj.getGrantId();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_111;
        FINAL_URL=FINAL_URL+"?token="+token+"&date="+selectedYearInput+"-"+selectedMonthInput+"&grant_id="+grantId;
        printLogs(LogTag,"fetchData","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchData", "RESPONSE : " + response);
                    String Status = jsonObject.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        attListDetailsAdapter adapter  =new attListDetailsAdapter(getApplicationContext());
                        //adapter.truncate();
                        ArrayList<attListArray> attArrayList = new ArrayList<>();
                        JSONArray dataM = jsonObject.getJSONArray(KEY_DATA);
                        for (int i = 0; i < dataM.length(); i++) {
                            int pos = i+1;
                            JSONObject rec = dataM.getJSONObject(i);
                            int aId2 = Integer.parseInt(rec.getString("s_a_id"));
                            String aLiDate3 = rec.getString("date");
                            String aLiTime4 = rec.getString("sign_in_time");
                            String aLoDate5 = date_input;
                            String aLoTime6 = rec.getString("sign_out_time");
                            String aTimeSpent7 = rec.getString("hours_worked");
                            String aStatus8 = rec.getString("attendance_status");
                            String aDay9 = rec.getString("day");
                            String aStatusShort10 = rec.getString("attendance_status");
                            String aViewComments11 = rec.getString("distance_from_workstation");//to view Comments _
                            String aMonth_year12 = rec.getString("date");//For _Month And Date



                            attArrayList.add(new attListArray(String.valueOf(aId2),aLiDate3,aDay9,aLiTime4,aLoTime6,aTimeSpent7,
                                    aStatus8,aViewComments11));

                            LLAttendanceContainer.setVisibility(View.VISIBLE);
                            rDataObj.addItem(rDataObj.createItem(pos,aId2,aLiDate3,aLiTime4,aLoDate5,aLoTime6,aTimeSpent7,aStatus8,aDay9,aStatusShort10,aViewComments11,aMonth_year12));

                        }
                        adapter.insert(attArrayList);
                        showList();
                        showProgress(false,mContentRView,mProgressRView);
                    }
                    else if(Status.equals("2")) {
                        showProgress(false,mContentRView,mProgressRView);
                    }
                    else{
                        showProgress(false,mContentRView,mProgressRView);
                        String sTitle="Error :"+ActivityId+"-102";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false,mContentRView,mProgressRView);
                    String sTitle="Error :"+ActivityId+"-103";
                    String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                    String sButtonLabelClose="Close";
                    ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        printLogs(LogTag, "fetchData", "error : " + error);
                        showProgress(false,mContentRView,mProgressRView);
                        String sTitle="Error :"+ActivityId+"-104";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

                    }
                })  {
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
    private void fetchOfflineData(int selectedYear, int selectedMonth) {

        printLogs(LogTag, "fetchOfflineData", "init");
        attListDetailsAdapter adapter  =new attListDetailsAdapter(getApplicationContext());
        List<attListArray> adapterAll = adapter.getAll();
        printLogs(LogTag, "fetchOfflineData", "init"+adapterAll);
        for (int i = 0; i < adapterAll.size(); i++) {
            int pos = i+1;
            int aId2 = Integer.parseInt(adapterAll.get(i).getS_a_id());
            String aLiDate3 = adapterAll.get(i).getDate();
            String aLiTime4 = adapterAll.get(i).getSign_in_time();
            String aLoDate5 = date_input;
            String aLoTime6 = adapterAll.get(i).getSign_out_time();
            String aTimeSpent7 =adapterAll.get(i).getHours_worked();
            String aStatus8 = adapterAll.get(i).getAttendance_status();
            String aDay9 = adapterAll.get(i).getDay();
            String aStatusShort10 = adapterAll.get(i).getAttendance_status();
            String aViewComments11 = adapterAll.get(i).getDistance_from_workstation();//to view Comments _
            String aMonth_year12 = adapterAll.get(i).getDay();//For _Month And Date

            LLAttendanceContainer.setVisibility(View.VISIBLE);
            rDataObj.addItem(rDataObj.createItem(pos,aId2,aLiDate3,aLiTime4,aLoDate5,aLoTime6,aTimeSpent7,aStatus8,aDay9,aStatusShort10,aViewComments11,aMonth_year12));

        }
        showList();
        showProgress(false,mContentRView,mProgressRView);
        printLogs(LogTag, "fetchOfflineData", "exit");


    }

    public void callHeaderBuilder(){
        printLogs(LogTag,"callHeaderBuilder","init");
        String tHeader3 = getLabelFromDb("t_head_S111_in_date",R.string.t_head_S111_in_date);
        String tHeader5 = getLabelFromDb("t_head_S111_out_date",R.string.t_head_S111_out_date);
        String tHeader4 = getLabelFromDb("t_head_S111_in_time",R.string.t_head_S111_in_time);
        String tHeader6 = getLabelFromDb("t_head_S111_out_time",R.string.t_head_S111_out_time);
        String tHeader7 = getLabelFromDb("t_head_S111_hours_worked",R.string.t_head_S111_hours_worked);
        String tHeader8 = getLabelFromDb("t_head_S111_status",R.string.t_head_S111_status);
        String tHeader9 = getLabelFromDb("t_head_S111_day",R.string.t_head_S111_day);
        String tHeader11 = getLabelFromDb("t_head_S111_distance_from_workstation",R.string.t_head_S111_distance_from_workstation);
        rDataObj.addItem(rDataObj.createItem(0,0,tHeader3,tHeader4,tHeader5,tHeader6,tHeader7,tHeader8,tHeader9,"",tHeader11,""));
    }
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        printLogs(LogTag,"setupRecyclerView","init");
        recyclerView.setAdapter(new SAttAdapter(rDataObjList,baseApcContext,activityIn));
    }
    public void showList(){
        printLogs(LogTag,"showList","init");
        List<SAttObj.Item> AttData = rDataObj.getITEMS();
        SAttAdapter adapter = new SAttAdapter(AttData,baseApcContext,activityIn);
        recyclerViewQ.setAdapter(adapter);
        showProgress(false,mContentRView,mProgressRView);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(SAttDA.this,SMonthlyAttDA.class);
            printLogs(LogTag,"onOptionsItemSelected","SMonthlyAttDA");
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