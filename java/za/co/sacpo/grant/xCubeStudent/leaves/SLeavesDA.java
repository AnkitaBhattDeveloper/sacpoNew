package za.co.sacpo.grant.xCubeStudent.leaves;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.adapter.SLeavesAdapter;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dataObj.SLeavesObj;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeLib.spinners.SpinnerSet;
import za.co.sacpo.grant.xCubeLib.spinners.SpinnerSetAdapter;
import za.co.sacpo.grant.xCubeStudent.SDashboardDA;

public class SLeavesDA extends BaseAPCPrivate {
    private String ActivityId="S115";
    public View mProgressView, mContentView,mProgressRView, mContentRView,heading;
    private TextView lblView;
    public LinearLayout LLinformationContainer,LLLeavesContainer,LLAddLeavesContainer;
    public Button mAddLeaveButton;
    Bundle activeUri;
    private static SpinnerSet[] yearsSet,monthsSet,approvalTypeMasters,studentSpin;
    private Spinner spinnerMonth,spinnerYear,inputSpinnerStatus, inputSpinnerastudentList;
    public Button mDateInputButton;
    SLeavesDA thisClass;
    public String date_input,generator,grant_id,leave_type_id , leave_id;
    private int selectedYear,selectedMonth;
    public SpinnerSetAdapter adapterS,adapterM;

    private RecyclerView recyclerViewQ;
    public SLeavesObj rDataObj = new SLeavesObj();
    private List<SLeavesObj.Item> rDataObjList = null;
    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId){
        baseApcContext = cnt;CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        this.thisClass = this;
        printLogs(lt,"setBaseApcContextParent","weAreIn");

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);
        printLogs(LogTag,"onCreate","init");
        Bundle activeInputUri = getIntent().getExtras();
        leave_type_id = activeInputUri.getString("leave_type_id");
        leave_id = activeInputUri.getString("leave_id");
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
            Intent intent = new Intent(SLeavesDA.this,AppUpdatedA.class);
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
        printLogs(LogTag,"setLayoutXml","a_leaves");
        setContentView(R.layout.a_leaves);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mContentRView = findViewById(R.id.content_container_r);
        mProgressRView = findViewById(R.id.progress_bar_r);
        heading = findViewById(R.id.heading);
        mDateInputButton = (Button) findViewById(R.id.btnFilterData);
        spinnerMonth = (Spinner) findViewById(R.id.inputSpinnerMonth);
        spinnerYear = (Spinner) findViewById(R.id.inputSpinnerYear);
        inputSpinnerStatus = (Spinner) findViewById(R.id.inputSpinnerStatus);
        inputSpinnerastudentList = (Spinner) findViewById(R.id.inputSpinnerastudentList);

        LLinformationContainer = findViewById(R.id.informationContainer);
        LLLeavesContainer = findViewById(R.id.leavesContainer);

     /* //  LLAddLeavesContainer = findViewById(R.id.addLeaveContainer);
        mAddLeaveButton = (Button) findViewById(R.id.btnAddLeave);*/

        rDataObjList = rDataObj.getITEMS();
        recyclerViewQ = (RecyclerView) findViewById(R.id.rVLeaves);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);
        setupRecyclerView(recyclerViewQ);
        printLogs(LogTag,"initializeViews","exit");
    }

    @Override
    protected void initializeLabels(){
        printLogs(LogTag,"initializeLabels","init");

        String Label = getLabelFromDb("h_S115",R.string.h_S115);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
        }
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
        printLogs(LogTag,"callDataApi","init");
        userToken = userSessionObj.getToken();
        if(!TextUtils.isEmpty(userToken)) {
            printLogs(LogTag,"callDataApi","Token USpdate");
            syncUserData(baseApcContext, activityIn);
            syncStudentData(baseApcContext, activityIn);
            syncGrantData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn,ActivityId);
        }
    }

    public void fetchData() {
        showProgress(true,mContentRView,mProgressRView);
        String token = userSessionObj.getToken();

        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_115;
        FINAL_URL=FINAL_URL+"?token="+token;
        printLogs(LogTag,"fetchData","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchData", "RESPONSE : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        for (int i = 0; i < dataM.length(); i++) {
                            int pos = i+1;
                            JSONObject rec = dataM.getJSONObject(i);
                            int aId2 = Integer.parseInt(rec.getString("s_a_id"));
                            String aLeMonth3 = rec.getString("month");
                            String aLeFromDate4 = rec.getString("from_date");
                            String aLeToDate5 = rec.getString("to_date");
                            String aLeAnnual6 = rec.getString("annual_leave");
                            String aLeSick7 = rec.getString("sick_leave");

                            String aLeOPaid8 = rec.getString("other_paid_leave");
                            String aLeUnPaid9 = rec.getString("unpaid_leave");
                            String aLeSaStatus10 = rec.getString("supervisor_approval_status");
                            String aLeMotivation11 = rec.getString("motivation");
                            String aLeIsSupervisorComm12 = rec.getString("reason_btn");
                            String aLeIsDocument13 = rec.getString("is_upload");
                            String aLeIsDocumentPath14 = rec.getString("uploads");

                            String aLeMotivationBtn15 = rec.getString("motivation_btn");
                            String aLeEditBtn16 = rec.getString("show_edit_link");
                            String aLeRemoveBtn17 = rec.getString("show_remove_link");
                            String aLeSComment18 = rec.getString("sa_reason");
                            rDataObj.addItem(rDataObj.createItem(pos,aId2,aLeMonth3, aLeFromDate4,aLeToDate5, aLeAnnual6, aLeSick7,aLeOPaid8,aLeUnPaid9,aLeSaStatus10, aLeMotivation11,aLeIsSupervisorComm12,aLeIsDocument13,aLeIsDocumentPath14,aLeMotivationBtn15,aLeEditBtn16,aLeRemoveBtn17,aLeSComment18));
                            showList();
                        }
                        showProgress(false,mContentRView,mProgressRView);
                    }
                    else if(Status.equals("2")) {
                        showProgress(false,mContentRView,mProgressRView);
                    }
                    else{
                        showProgress(false,mContentRView,mProgressRView);
                        String sTitle="Error :"+ActivityId+"-100";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false,mContentRView,mProgressRView);
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
                        showProgress(false,mContentRView,mProgressRView);
                        String sTitle="Error :"+ActivityId+"-102";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

                    }
                })  {
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
    public void validateInput(){
        printLogs(LogTag,"validateInput","init");

    }
    public void callHeaderBuilder(){
        printLogs(LogTag,"callHeaderBuilder","init");
        String tHeader3 = getLabelFromDb("t_head_S115_month",R.string.t_head_S115_month);
        String tHeader4 = getLabelFromDb("t_head_S115_from_date",R.string.t_head_S115_from_date);
        String tHeader5= getLabelFromDb("t_head_S115_to_date",R.string.t_head_S115_to_date);
        String tHeader6 = getLabelFromDb("t_head_S115_annual",R.string.t_head_S115_annual);

        String tHeader7 = getLabelFromDb("t_head_S115_sick",R.string.t_head_S115_sick);
        String tHeader8 = getLabelFromDb("t_head_S115_oPaid",R.string.t_head_S115_oPaid);
        String tHeader9= getLabelFromDb("t_head_S115_unPaid",R.string.t_head_S115_unPaid);
        String tHeader10 = getLabelFromDb("t_head_S115_status",R.string.t_head_S115_status);

        String tHeader11 = getLabelFromDb("t_head_S115_s_comments",R.string.t_head_S115_l_comments);
        String tHeader12 = getLabelFromDb("t_head_S115_annual",R.string.t_head_S115_annual);
        String tHeader13= getLabelFromDb("t_head_S115_annual",R.string.t_head_S115_annual);
        String tHeader14 = getLabelFromDb("t_head_S115_uploads",R.string.t_head_S115_uploads);
        String tHeader15 = getLabelFromDb("t_head_S115_annual",R.string.t_head_S115_annual);
        String tHeader16= getLabelFromDb("t_head_S115_edit",R.string.t_head_S115_edit);
        String tHeader17 = getLabelFromDb("t_head_S115_remove",R.string.t_head_S115_remove);
        String tHeader18 = getLabelFromDb("t_head_S115_s_comments",R.string.t_head_S115_s_comments);
        rDataObj.addItem(rDataObj.createItem(0,0,tHeader3,tHeader4,tHeader5,tHeader6,tHeader7,tHeader8,tHeader9,tHeader10,tHeader11,tHeader12,tHeader13,tHeader14,tHeader15,tHeader16,tHeader17,tHeader18));
    }
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        printLogs(LogTag,"setupRecyclerView","init");
        recyclerView.setAdapter(new SLeavesAdapter(rDataObjList,baseApcContext,activityIn,thisClass));
    }
    public void showList(){
        printLogs(LogTag,"showList","init");
        List<SLeavesObj.Item> AttData = rDataObj.getITEMS();
        SLeavesAdapter adapter = new SLeavesAdapter(AttData,baseApcContext,activityIn,thisClass);
        recyclerViewQ.setAdapter(adapter);
        showProgress(false,mContentRView,mProgressRView);
        showProgress(false,mContentView,mProgressView);
    }
    public void customRedirector() {
        Intent intent = new Intent(SLeavesDA.this, SLeavesDA.class);
        Bundle inputUri = new Bundle();
        printLogs(LogTag,"onOptionsItemSelected","SLeavesDA");
        intent.putExtras(inputUri);
        startActivity(intent);
        finish();
    }
    public void removeLeave(final String att_ids){
        showProgress(true, mContentView, mProgressView);
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.REF_S_115_1;
        String token = userSessionObj.getToken();
        printLogs(LogTag, "FormSubmit", "URL : " + FINAL_URL);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put("att_ids", att_ids);
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
                        String sTitle = getLabelFromDb("m_S115_title", R.string.m_S115_title);
                        String sMessage = getLabelFromDb("m_S115_message_removed", R.string.m_S115_message_removed);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showSuccessDialogRemoveLeaveA(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose, thisClass);

                    } else {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-104";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "-105";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String sTitle = "Error :" + ActivityId + "-106";
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Bundle inputUri = new Bundle();
            inputUri.putString("leave_type_id",leave_type_id);

            Intent intent = new Intent(SLeavesDA.this,SDashboardDA.class);
            printLogs(LogTag,"onOptionsItemSelected","sDashboard");
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