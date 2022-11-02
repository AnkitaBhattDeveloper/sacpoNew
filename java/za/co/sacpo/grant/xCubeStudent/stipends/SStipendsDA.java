package za.co.sacpo.grant.xCubeStudent.stipends;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
import za.co.sacpo.grant.xCubeLib.adapter.SStipendAdapter;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dataObj.SStipendObj;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsGrantSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsStudentSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeStudent.SDashboardDA;

/*a_stipends*/
public class SStipendsDA extends BaseAPCPrivate {
    private String ActivityId="138";
    public View mProgressView, mContentView,mProgressRView, mContentRView;
    public LinearLayout LLinformationContainer,LLGrantDetailContainer,LLStipendContainer;
    private TextView lblView,mIGrantNameText,mIStartDateText,mIEndDateText,lblStudentStipendsTT;
    public Button mGrantButton;
    public Button activeUri;
    private RecyclerView recyclerViewQ;
    public SStipendObj rDataObj = new SStipendObj();
    private List<SStipendObj.Item> rDataObjList = null;
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
        printLogs(LogTag,"onCreate","onCreateBundle works");
        bootStrapInit();
    }
    @Override
    protected void bootStrapInit() {
        Boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext,activityIn);
        if(isConnected) {
            printLogs(LogTag,"bootStrapInit","bootStrapInit works");
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);

            initMenusCustom(ActivityId,baseApcContext,activityIn);
            fetchVersionData();
            verifyVersion();
            internetChangeBroadCast();
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
            fetchData();
            showProgress(false,mContentView,mProgressView);
        }
    }
    @Override
    protected void internetChangeBroadCast() {
        printLogs(LogTag,"internetChangeBroadCast","internetchangeBroadcast ..i");
        registerBroadcastIC();
    }
    @Override
    protected void fetchVersionData(){
        printLogs(LogTag,"fetchVersionData","exitConnected");
        syncUpdates(baseApcContext,activityIn);
    }
    @Override
    protected void verifyVersion(){
        printLogs(LogTag,"verifyVersion","err");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag,"verifyVersion","isUpdate "+isUpdate);
        printLogs(LogTag,"verifyVersion","isUpdate "+ utilSessionObj.getIsUpdateRequired());
        if(isUpdate){
            Intent intent = new Intent(SStipendsDA.this,AppUpdatedA.class);
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
        printLogs(LogTag,"setLayoutXml","setLayoutXml works");
        setContentView(R.layout.a_stipends);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","initializeViews works");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mContentRView = findViewById(R.id.content_container_r);
        mProgressRView = findViewById(R.id.progress_bar_r);

        LLGrantDetailContainer = findViewById(R.id.grantDetailContainer);
        LLinformationContainer = findViewById(R.id.informationContainer);
        LLStipendContainer = findViewById(R.id.stipendsContainer);

        mIGrantNameText = (TextView) findViewById(R.id.iGrantName);
        mIStartDateText = (TextView) findViewById(R.id.iStartDate);
        mIEndDateText = (TextView) findViewById(R.id.iEndDate);
        lblStudentStipendsTT = (TextView) findViewById(R.id.lblStudentStipendsTT);
        mGrantButton = (Button) findViewById(R.id.btnGrantDetails);
        rDataObjList = rDataObj.getITEMS();
        recyclerViewQ = (RecyclerView) findViewById(R.id.rVStudentStipends);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);

        View recyclerView = findViewById(R.id.rVStudentStipends);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        printLogs(LogTag,"initializeViews","exit");
    }

    @Override
    protected void initializeLabels(){
        printLogs(LogTag,"initializeLabels","initializeLabels works");
        String Label = getLabelFromDb("l_138_stipends",R.string.l_138_stipends);
        lblView = (TextView)findViewById(R.id.lblStudentStipends);
        lblView.setText(Label);

        Label = getLabelFromDb("h_138",R.string.h_138);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);

        Label = getLabelFromDb("b_138_grant_details",R.string.b_138_grant_details);
        mGrantButton.setText(Label);
        Label = getLabelFromDb("l_120_no_active_grant",R.string.l_S111_no_active_grant);
        lblView = (TextView)findViewById(R.id.iNoActiveGrant);
        lblView.setText(Label);
    }
    @Override
    protected void initializeInputs(){
        printLogs(LogTag,"initializeInputs","init");
        studentSessionObj = new OlumsStudentSession(baseApcContext);
        Boolean isGrantActive= studentSessionObj.getIsActiveGrant();
        if(isGrantActive) {
            printLogs(LogTag,"isGrantActive","if Condition");
            grantSessionObj = new OlumsGrantSession(baseApcContext);
            String StartDate = grantSessionObj.getGrantSDate();
            String EndDate = grantSessionObj.getGrantEDate();
            String grantName = grantSessionObj.getGrantName();
            mIGrantNameText.setText(grantName);
            mIStartDateText.setText(StartDate);
            mIEndDateText.setText(EndDate);
            LLinformationContainer.setVisibility(View.GONE);
            LLGrantDetailContainer.setVisibility(View.VISIBLE);
            LLStipendContainer.setVisibility(View.VISIBLE);
        }
        else{
            LLGrantDetailContainer.setVisibility(View.GONE);
            LLStipendContainer.setVisibility(View.GONE);
            LLinformationContainer.setVisibility(View.VISIBLE);
        }
    }
    @Override
    protected void initializeListeners() {
        printLogs(LogTag,"initializeListeners","initializeListeners works");
        lblStudentStipendsTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sToolTip=getLabelFromDb("tt_138_scroll",R.string.tt_138_scroll);
                showTooltip(lblStudentStipendsTT,sToolTip,4);
            }
        });
    }
    public void validateInput(){
        printLogs(LogTag,"validateInput","validateInput works");
    }

    public void callDataApi(){
        printLogs(LogTag,"callDataApi","callDataApi works");
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
        String token = userSessionObj.getToken();
        String grantId = studentSessionObj.getGrantId();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.STUDENT_STIPEND_LISTING;
        FINAL_URL=FINAL_URL+"/token/"+token+"/grant_id/"+grantId;
        printLogs(LogTag,"fetchData","URL : "+FINAL_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,FINAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                printLogs(LogTag,"fetchData","RESPONSE : "+response);
                JSONObject outputJson = null;
                try {
                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        for (int i = 0; i < dataM.length(); i++) {
                            int pos = i+1;
                            JSONObject rec = dataM.getJSONObject(i);
                            printLogs(LogTag,"JSONObject","JSONObject api --i");
                            int aId2 = Integer.parseInt(rec.getString("s_m_s_id"));
                            String sStDate3 = rec.getString("s_m_s_a_date");
                            String sStMonth4 = rec.getString("s_month");
                            String sStYear5 = rec.getString("s_year");
                            String sStTotalStipend6 = rec.getString("s_m_s_stipend");
                            String sStbtnStipendDetails7 = rec.getString("s_m_s_status");
                            rDataObj.addItem(rDataObj.createItem(pos,aId2,sStDate3,sStMonth4,sStYear5,sStTotalStipend6,sStbtnStipendDetails7));
                            showList();
                        }
                    }


                    else{
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-102";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    printLogs(LogTag,"JSONException","JSONException works");
                    e.printStackTrace();
                    e.printStackTrace();
                    showProgress(false,mContentView,mProgressView);
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
                        printLogs(LogTag,"onErrorResponse","onErrorResponse works");
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-104";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

                    }
                }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(SStipendsDA.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        printLogs(LogTag,"setupRecyclerView","setupRecyclerView works");
        recyclerView.setAdapter(new SStipendAdapter(rDataObjList,baseApcContext,activityIn));
    }
    public void showList(){
        printLogs(LogTag,"showList","showList works");
        List<SStipendObj.Item> AttData = rDataObj.getITEMS();
        SStipendAdapter adapter = new SStipendAdapter(AttData,baseApcContext,activityIn);
        recyclerViewQ.setAdapter(adapter);
        showProgress(false,mContentRView,mProgressRView);
    }

    public void callHeaderBuilder(){
        printLogs(LogTag,"callHeaderBuilder","callHeaderBuilder works");
        String tHeader3 = getLabelFromDb("t_head_138_in_Stipend_date",R.string.t_head_138_in_Stipend_date);
        String tHeader4 = getLabelFromDb("t_head_138_stipend_month",R.string.t_head_138_stipend_month);
        String tHeader5 = getLabelFromDb("t_head_138_stipend_year",R.string.t_head_138_stipend_year);
        String tHeader6 = getLabelFromDb("t_head_138_stipend_Amt",R.string.t_head_138_stipend_Amt);
        String tHeader7 = getLabelFromDb("t_head_138_stipend_Detail_Btn",R.string.t_head_138_stipend_Detail_Btn);


        rDataObj.addItem(rDataObj.createItem(0,0,tHeader3,tHeader4,tHeader5,tHeader6,tHeader7));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(SStipendsDA.this,SDashboardDA.class);
            printLogs(LogTag,"onOptionsItemSelected","sDashboard");
            startActivity(intent);
            finish();
        }
        return true;
    }


    @Override
    protected void onPause() {
        printLogs(LogTag,"onPause","onPause redirectHere");
        super.onPause();
        unregisterBroadcastIC();
    }
    @Override
    protected void onResume() {
        printLogs(LogTag,"onResume"," redirectHere");
        super.onResume();
        registerBroadcastIC();
    }
    @Override
    protected void onStop() {
        printLogs(LogTag,"onStop","onStop redirectHere");
        super.onStop();
        unregisterBroadcastIC();
    }
    @Override
    protected void onStart() {
        printLogs(LogTag,"onStart","onStart redirectHere");
        super.onStart();
        registerBroadcastIC();
    }
    @Override
    public void onDestroy() {
        printLogs(LogTag,"onPause","onPause redirectHere");
        super.onDestroy();
        unregisterBroadcastIC();
    }
}