package za.co.sacpo.grant.xCubeMentor.claims;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.adapter.MClaimAdapter;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dataObj.MClaimObj;
import za.co.sacpo.grant.xCubeLib.db.mApprovedClaimListAdapter;
import za.co.sacpo.grant.xCubeLib.db.mApprovedClaimListArray;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeMentor.MDashboardDA;

public class MPastClaimA extends BaseAPCPrivate {

    public String ActivityId = "M412";
    public View mProgressView, mContentView,mProgressRView, mContentRView,heading;
    public Button mDateInputButton;
    private TextView lblView,mIClaimTTText, lblClaim;
    private RecyclerView recyclerViewQ;
    public MClaimObj rDataObj = new MClaimObj();
    private Spinner inputSpinnerStatus, inputSpinnerastudentList;
    private List<MClaimObj.Item> rDataObjList = null;
    private static final String TAG = "WHAT_IS_ERROR";
    String student_id,m_student_name,generator;
     String monthField_18;

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

        Intent inputIntent = getIntent();
        Bundle activeInputUri = getIntent().getExtras();
        student_id="";
        m_student_name=null;
        generator=null;
        if (inputIntent.hasExtra("student_id")) {
            student_id = activeInputUri.getString("student_id");
        }
        if (inputIntent.hasExtra("m_student_name")) {
            m_student_name = activeInputUri.getString("m_student_name");
        }

        if (inputIntent.hasExtra("generator")) {
            generator = activeInputUri.getString("generator");
        }

        bootStrapInit();

    }

    @Override
    protected void bootStrapInit() {
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext,activityIn);
        if(isConnected) {
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);
            printLogs(LogTag,"callFooter","URL : "+student_id);
            initMenusCustom(ActivityId,baseApcContext,activityIn);
            initBackBtn();
            fetchVersionData();
            verifyVersion();
            internetChangeBroadCast();
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
        }else{
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);
            printLogs(LogTag,"callFooter","URL : "+student_id);
            initMenusCustom(ActivityId,baseApcContext,activityIn);
            initBackBtn();
            fetchVersionData();
            verifyVersion();
            internetChangeBroadCast();
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
            fetchOfflineData();
            showProgress(false,mContentView,mProgressView);
        }
    }



    @Override
    protected void internetChangeBroadCast(){
        registerBroadcastIC();
    }
    ////TODO::@Override
    protected void fetchVersionData(){
        printLogs(LogTag,"fetchVersionData","exitConnected");
        syncUpdates(baseApcContext,activityIn);
    }
    ////TODO::@Override
    protected void verifyVersion(){
        printLogs(LogTag,"verifyVersion","init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag,"verifyVersion","isUpdate "+isUpdate);
        printLogs(LogTag,"verifyVersion","isUpdate "+ utilSessionObj.getIsUpdateRequired());
        if(isUpdate){
            Intent intent = new Intent(MPastClaimA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    protected void setLayoutXml()
    {
        printLogs(LogTag,"setLayoutXml","a_mclaim");
        setContentView(R.layout.a_mclaim);
    }

    @Override
    protected void initializeViews() {
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mContentRView = findViewById(R.id.content_container_r);
        mProgressRView = findViewById(R.id.progress_bar_r);
        heading = findViewById(R.id.heading);

        mDateInputButton = (Button) findViewById(R.id.btnFilterData);
      //  spinnerMonth = (Spinner) findViewById(R.id.inputSpinnerMonth);
       // spinnerYear = (Spinner) findViewById(R.id.inputSpinnerYear);
        inputSpinnerastudentList = (Spinner) findViewById(R.id.inputSpinnerastudentList);
        mIClaimTTText = (TextView) findViewById(R.id.lblmClaimTT);

        rDataObjList = rDataObj.getITEMS();
        recyclerViewQ = (RecyclerView) findViewById(R.id.rVmClaim);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);

        View recyclerView = findViewById(R.id.rVmClaim);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        lblClaim = (TextView)findViewById(R.id.lblClaim);
        lblClaim.setText(m_student_name);
        lblClaim.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));

    }
    @Override
    protected void initializeLabels(){
        String Label = getLabelFromDb("b_205_filter",R.string.b_205_filter);
        mDateInputButton.setText(Label);

        Label = getLabelFromDb("h_205",R.string.h_205);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
        }
    }


    protected void initializeInputs() {
        printLogs(LogTag,"initializeInputs","init");
    }

    @Override
    protected void initializeListeners() {

    }
    public void callHeaderBuilder(){
        String tHeader3 = "";
        String tHeader4= getLabelFromDb("l_claim_M412_year",R.string.l_claim_M412_year);
        String tHeader5 = getLabelFromDb("l_claim_M412_month",R.string.l_claim_M412_month);
        String tHeader6= getLabelFromDb("l_claim_M412_amount",R.string.l_claim_M412_amount);
        String tHeader7= getLabelFromDb("l_claim_M412_o_range",R.string.l_claim_M412_o_range);
        String tHeader8 = getLabelFromDb("l_claim_M412_daysWorked",R.string.l_claim_M412_daysWorked);
        String tHeader9= getLabelFromDb("l_claim_M412_leave",R.string.l_claim_M412_leave);
        String tHeader10= getLabelFromDb("l_claim_M412_d_c_form",R.string.l_claim_M412_d_c_form);
        String tHeader11= "";
        String tHeader12= getLabelFromDb("l_claim_M412_d_s_c_form",R.string.l_claim_M412_d_s_c_form);
        String tHeader13= "";



        rDataObj.addItem(rDataObj.createItem(0,"",tHeader3,tHeader4,tHeader5,tHeader6,tHeader7,tHeader8,
                tHeader9,tHeader10,tHeader11,tHeader12,tHeader13));

    }

    public void callDataApi(){
        userToken = userSessionObj.getToken();
        if(!TextUtils.isEmpty(userToken)) {
            syncUserData(baseApcContext, activityIn);
            syncMentorData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn,ActivityId);
        }
    }

    public void fetchData(){
        String token = userSessionObj.getToken();
        showProgress(true,mContentRView,mProgressRView);
      //  String grantId = studentSessionObj.getGrantId();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.REF_M_412;
        FINAL_URL=FINAL_URL+"?token="+token +"&learner_id="+student_id;
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

                        mApprovedClaimListAdapter adapter = new mApprovedClaimListAdapter(getApplicationContext());
                        adapter.truncate();
                        ArrayList<mApprovedClaimListArray> arrayList = new ArrayList<>();
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        for (int i = 0; i < dataM.length(); i++) {
                            int pos = i+1;
                            JSONObject rec = dataM.getJSONObject(i);
                            String aId2 = rec.getString("s_m_s_id");
                            String claMonthVal3= rec.getString("s_m_s_stipend_month");
                            String claYear4= rec.getString("s_m_s_stipend_year");
                            String claMonth5= rec.getString("month_name");
                            String claAmount6= rec.getString("stipend_amount");
                            String claOutRange7= rec.getString("out_of_range_sign_in_counts");
                            String claDaysWorked8= rec.getString("days_worked");
                            String claLeave9= rec.getString("leave");
                            String claDCFLink10= rec.getString("download_claim_form_link");
                            String clasDCFLinkStatus11= rec.getString("show_claim_form_link");
                            String claSDCFLink12= rec.getString("download_signed_claim_form_link");
                            String claSDCFLinkStatus13= rec.getString("show_signed_claim_form_link");

                            rDataObj.addItem(rDataObj.createItem(pos,aId2,claMonthVal3,claYear4,claMonth5,claAmount6,claOutRange7,claDaysWorked8,claLeave9,claDCFLink10,clasDCFLinkStatus11,claSDCFLink12,claSDCFLinkStatus13));

                            arrayList.add(new mApprovedClaimListArray(claMonthVal3,claMonth5,claYear4,
                                    aId2,claAmount6,rec.getString("approve_stipend"),claOutRange7,
                                    rec.getString("out_of_range_link"),claDaysWorked8,claLeave9,claDCFLink10,
                                    clasDCFLinkStatus11,claSDCFLink12,claSDCFLinkStatus13));



                        }
                        adapter.insert(arrayList);
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
                        showProgress(false,mContentRView,mProgressRView);
                        String sTitle="Error :"+ActivityId+"-104";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                        printLogs(LogTag,"fetchData","error_try_again : "+error.getMessage());
                        printLogs(LogTag,"fetchData","error_try_again : "+error.toString());
                        printLogs(LogTag,"fetchData","error_try_again : "+error.getLocalizedMessage());
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


    private void fetchOfflineData(){

        mApprovedClaimListAdapter adapter = new mApprovedClaimListAdapter(getApplicationContext());
        ArrayList<mApprovedClaimListArray> arrayList = new ArrayList<>();
        List<mApprovedClaimListArray> adapterAll = adapter.getAll();

        for (int i = 0; i <adapterAll.size() ; i++) {
            int pos = i+1;
            String aId2 = adapterAll.get(i).getS_m_s_id();
            String claMonthVal3= adapterAll.get(i).getS_m_s_stipend_month();
            String claYear4= adapterAll.get(i).getS_m_s_stipend_year();
            String claMonth5= adapterAll.get(i).getMonth_name();
            String claAmount6= adapterAll.get(i).getStipend_amount();
            String claOutRange7= adapterAll.get(i).getOut_of_range_sign_in_counts();
            String claDaysWorked8= adapterAll.get(i).getDays_worked();
            String claLeave9= adapterAll.get(i).getLeave();
            String claDCFLink10= adapterAll.get(i).getDownload_claim_form_link();
            String clasDCFLinkStatus11= adapterAll.get(i).getShow_claim_form_link();
            String claSDCFLink12= adapterAll.get(i).getDownload_signed_claim_form_link();
            String claSDCFLinkStatus13= adapterAll.get(i).getShow_signed_claim_form_link();

            rDataObj.addItem(rDataObj.createItem(pos,aId2,claMonthVal3,claYear4,claMonth5,claAmount6,claOutRange7,claDaysWorked8,claLeave9,claDCFLink10,clasDCFLinkStatus11,claSDCFLink12,claSDCFLinkStatus13));
        }
        showList();
        showProgress(false,mContentRView,mProgressRView);
    }



    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new MClaimAdapter(rDataObjList,baseApcContext,activityIn,this));
    }

    public void showList(){
        List<MClaimObj.Item> ClaimData = rDataObj.getITEMS();
        MClaimAdapter adapter = new MClaimAdapter(ClaimData,baseApcContext,activityIn,this);
        recyclerViewQ.setAdapter(adapter);
        showProgress(false,mContentRView,mProgressRView);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(MPastClaimA.this,MDashboardDA.class);
            printLogs(LogTag,"onOptionsItemSelected","sDashboard");
            startActivity(intent);
            finish();
        }
        return true;
    }


    public String getStudentName(){

        Bundle inputUri = new Bundle();
        String StuName = m_student_name;
        inputUri.putString("StuName", StuName);
        printLogs(LogTag,"getStudentName","GetStudent_name"+StuName);
        return m_student_name;
    }
    public String getStudentId(){
        return student_id;
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

    public String getMonthField_18() {
        return monthField_18;
    }
}

