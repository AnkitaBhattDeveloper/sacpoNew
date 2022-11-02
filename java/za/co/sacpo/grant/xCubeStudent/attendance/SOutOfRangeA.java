package za.co.sacpo.grant.xCubeStudent.attendance;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.adapter.SAttOutOffRangeAdapter;
import za.co.sacpo.grant.xCubeLib.baseFramework.StudentBaseDrawerA;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dataObj.SAttOutOfRangeObj;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeLib.spinners.SpinnerSet;
import za.co.sacpo.grant.xCubeLib.spinners.SpinnerSetAdapter;

/*message_video*/
public class SOutOfRangeA extends StudentBaseDrawerA {

    private String ActivityId="187";
    public View mProgressView, mContentView,mProgressRView, mContentRView;
    public TextView lblView;
    public LinearLayout LLinformationContainer,LLFilterContainer,LLAttendanceContainer;
    public Button mDateInputButton;
    public Bundle activeUri;
    public String date_input,generator,year_input,month_input;
    private Spinner spinnerMonth,spinnerYear;
    private static SpinnerSet[] yearsSet,monthsSet;
    public SpinnerSetAdapter adapterS,adapterM;
    private int selectedYear,selectedMonth;
    private RecyclerView recyclerViewQ;
    public SAttOutOfRangeObj rDataObj = new SAttOutOfRangeObj();
    private List<SAttOutOfRangeObj.Item> rDataObjList = null;
    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cTAId){
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
        /*Intent inputIntent = getIntent();
        Bundle activeInputUri = getIntent().getExtras();
        year_input=null;
        month_input=null;
        if (inputIntent.hasExtra("date_input")) {
            date_input = activeInputUri.getString("date_input");
            year_input=date_input.substring(0,4);
            month_input=date_input.substring(4,6);
            printLogs(LogTag,"onCreate","Date Input "+date_input+"--"+year_input+"--"+month_input);
        }
        if (inputIntent.hasExtra("generator")) {
            generator = activeInputUri.getString("generator");
            printLogs(LogTag,"onCreate","GENERATOR ID "+generator);
        }
        printLogs(LogTag,"onCreate","Inputs "+date_input+"--"+year_input+"--"+month_input);*/
        bootStrapInit();
    }
    @Override
    protected void bootStrapInit() {
        Boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext,activityIn);
        if(isConnected) {
            printLogs(LogTag,"bootStrapInit","initConnected");
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);
            initMenusCustom(ActivityId,baseApcContext,activityIn);
            initDrawer();
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
            fetchData();
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
            Intent intent = new Intent(SOutOfRangeA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","a_out_of_range");
        setContentView(R.layout.a_out_of_range);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mContentRView = findViewById(R.id.content_container_r);
        mProgressRView = findViewById(R.id.progress_bar_r);
        LLinformationContainer = findViewById(R.id.informationContainer);
        LLFilterContainer = findViewById(R.id.filterContainer);
        LLAttendanceContainer = findViewById(R.id.attendanceContainer);

        mDateInputButton = (Button) findViewById(R.id.btnFilterData);
        spinnerMonth = (Spinner) findViewById(R.id.inputSpinnerMonth);
        spinnerYear = (Spinner) findViewById(R.id.inputSpinnerYear);


        rDataObjList = rDataObj.getITEMS();
        recyclerViewQ = (RecyclerView) findViewById(R.id.rVAttendance);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);

        View recyclerView = findViewById(R.id.rVAttendance);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        printLogs(LogTag,"initializeViews","exit");
    }
    @Override
    protected void initializeLabels(){
        printLogs(LogTag,"initializeLabels","init");
        String Label = getLabelFromDb("b_187_filter",R.string.b_187_filter);
        mDateInputButton.setText(Label);

        Label = getLabelFromDb("h_187",R.string.h_187);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);

    }
    @Override
    protected void initializeInputs(){

        printLogs(LogTag,"initializeInputs","init");

    }

    @Override
    protected void initializeListeners() {
        printLogs(LogTag,"initializeListeners","init");


    }
    public void callDataApi(){
        printLogs(LogTag,"callDataApi","callDataApi");
        userToken = userSessionObj.getToken();
        if(!TextUtils.isEmpty(userToken)) {
            printLogs(LogTag,"callDataApi","Token Update");
            syncUserData(baseApcContext, activityIn);
            syncStudentData(baseApcContext, activityIn);
            syncGrantData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn,ActivityId);
        }
    }

    public void fetchData(){
       // showProgress(true,mContentRView,mProgressRView);
        String token = userSessionObj.getToken();

        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_OUT_OF_RANGE;
        FINAL_URL=FINAL_URL+"/token/"+token;
        printLogs(LogTag,"fchData","URL : "+FINAL_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,FINAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject outputJson = null;
                printLogs(LogTag,"fetchData","RESPONSE OUT OF RANGE: "+response);
                try {
                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        for (int i = 0; i < dataM.length(); i++) {
                            int pos = i+1;
                            JSONObject rec = dataM.getJSONObject(i);
                            String aId2 = rec.getString("s_a_id");
                            String aLiDate3 = rec.getString("s_a_add_date");
                            String aLiTime4 = rec.getString("s_a_add_date");
                            String aLoDate5 = rec.getString("coord_diff");

                            LLAttendanceContainer.setVisibility(View.VISIBLE);
                            rDataObj.addItem(rDataObj.createItem("", aId2,aLiDate3,aLiTime4,aLoDate5));
                            showList();
                            printLogs(LogTag,"fetchData","datshowListaM : "+response);
                        }
                        showProgress(false,mContentRView,mProgressRView);
                    }
                    else if(Status.equals("2")) {
                        showProgress(false,mContentView,mProgressView);
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
                        showProgress(false,mContentRView,mProgressRView);
                        String sTitle="Error :"+ActivityId+"-104";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

                    }
                }) {
        };
        RequestQueue requestQueue = Volley.newRequestQueue(SOutOfRangeA.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    public void validateInput(){
        printLogs(LogTag,"validateInput","init");

    }
    public void callHeaderBuilder(){
        printLogs(LogTag,"callHeaderBuilder","init");
        String tHeader3 = getLabelFromDb("t_head_187_in_date",R.string.t_head_187_in_date);
        String tHeader4 = getLabelFromDb("t_head_187_out_date",R.string.t_head_187_out_date);
        String tHeader5 = getLabelFromDb("t_head_187_in_time",R.string.t_head_187_in_time);

        rDataObj.addItem(rDataObj.createItem("","",tHeader3,tHeader4,tHeader5));
    }
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        printLogs(LogTag,"setupRecyclerView","init");
        recyclerView.setAdapter(new SAttOutOffRangeAdapter(rDataObjList,baseApcContext,activityIn));
    }
    public void showList(){
        printLogs(LogTag,"showList","init");
        List<SAttOutOfRangeObj.Item> AttData = rDataObj.getITEMS();
        SAttOutOffRangeAdapter adapter = new SAttOutOffRangeAdapter(AttData,baseApcContext,activityIn);
        recyclerViewQ.setAdapter(adapter);
        showProgress(false,mContentRView,mProgressRView);
    }


/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(SOutOfRangeA.this,SDashboardDA.class);
            printLogs(LogTag,"onOptionsItemSelected","sDashboard");
            startActivity(intent);
            finish();
        }
        return true;
    }*/

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