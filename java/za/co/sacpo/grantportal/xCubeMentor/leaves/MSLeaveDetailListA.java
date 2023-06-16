package za.co.sacpo.grantportal.xCubeMentor.leaves;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import za.co.sacpo.grantportal.AppUpdatedA;
import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.adapter.MLeavesAdapter;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.component.Utils;
import za.co.sacpo.grantportal.xCubeLib.dataObj.MLeavesObj;
import za.co.sacpo.grantportal.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;

/*a_m_leave*/
public class MSLeaveDetailListA extends BaseAPCPrivate {
    private String ActivityId="M409";
    public View mProgressView, mContentView,mProgressRView, mContentRView,heading,heading2,heading3,heading4
            ,l_heading1,l_heading2,l_heading3,lt_heading1,lt_heading2,lt_heading3
            ,g_heading1,g_heading2,g_heading3;
    private TextView lblView;
    private TextView mILeavesTTText,lblMLeavess;
    public Button btnSubmit;
    String student_id,generator,m_student_name,type;
    private RecyclerView recyclerViewQ;
    public MLeavesObj rDataObj = new MLeavesObj();
    private List<MLeavesObj.Item> rDataObjList = null;
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
        Intent inputIntent = getIntent();
        Bundle activeInputUri = getIntent().getExtras();
        student_id=null;
        m_student_name="";
        generator=null;
        type = "";
        if (inputIntent.hasExtra("student_id")) {
            student_id = activeInputUri.getString("student_id");
            printLogs(LogTag,"onCreate","student_id"+student_id);
        }if (inputIntent.hasExtra("m_student_name")) {
            m_student_name = activeInputUri.getString("m_student_name");
            printLogs(LogTag,"onCreate","m_student_name"+m_student_name);
        }
        if (inputIntent.hasExtra("type")) {
            type = activeInputUri.getString("type");
            printLogs(LogTag,"onCreate","type"+type);
        }
        if (inputIntent.hasExtra("generator")) {
            generator = activeInputUri.getString("generator");
            printLogs(LogTag,"onCreate","GENERATOR ID "+generator);
        }
        printLogs(LogTag,"onCreate","init");
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
            getAllDetails();
            callHeaderBuilder();
            //fetchData();
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
        printLogs(LogTag,"verifyVersion", "isUpdate " + isUpdate);
        printLogs(LogTag,"verifyVersion", "isUpdate " + utilSessionObj.getIsUpdateRequired());
        if(isUpdate){
            Intent intent = new Intent(MSLeaveDetailListA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","a_m_leave");
        setContentView(R.layout.a_m_leave);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mContentRView = findViewById(R.id.content_container_r);
        mProgressRView = findViewById(R.id.progress_bar_r);
        heading     = findViewById(R.id.heading);
        heading2    = findViewById(R.id.heading2);
        heading3    = findViewById(R.id.heading3);
        heading4    = findViewById(R.id.heading4);
        l_heading1  = findViewById(R.id.l_heading1);
        l_heading2  = findViewById(R.id.l_heading2);
        l_heading3  = findViewById(R.id.l_heading3);
        lt_heading1 = findViewById(R.id.lt_heading1);
        lt_heading2 = findViewById(R.id.lt_heading2);
        lt_heading3 = findViewById(R.id.lt_heading3);
        g_heading1  = findViewById(R.id.g_heading1);
        g_heading2  = findViewById(R.id.g_heading2);
        g_heading3  = findViewById(R.id.g_heading3);
        mILeavesTTText = (TextView) findViewById(R.id.lblLeavesTT);
        lblMLeavess = (TextView) findViewById(R.id.lblMLeavess);
        rDataObjList = rDataObj.getITEMS();
        recyclerViewQ = (RecyclerView) findViewById(R.id.rVLeaves);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);
        setupRecyclerView(recyclerViewQ);

        lblMLeavess.setText(m_student_name);
        lblMLeavess.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));

        printLogs(LogTag,"initializeViews","exit");
    }
    @Override
    protected void initializeLabels(){
        printLogs(LogTag,"initializeLabels","init");
        String Label="";
        if(type.equalsIgnoreCase("a")) {
            Label = getLabelFromDb("h_M409_A", R.string.h_M409_A);
        }
        else {
            Label = getLabelFromDb("h_M409_P", R.string.h_M409_P);
        }
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            heading2.setBackgroundColor(getColor(getTextcolorResourceId("dashboard_textColor")));
            heading3.setBackgroundColor(getColor(getTextcolorResourceId("dashboard_textColor")));
            heading4.setBackgroundColor(getColor(getTextcolorResourceId("dashboard_textColor")));
            l_heading1.setBackgroundColor(getColor(getTextcolorResourceId("dashboard_textColor")));
            l_heading2.setBackgroundColor(getColor(getTextcolorResourceId("dashboard_textColor")));
            l_heading3.setBackgroundColor(getColor(getTextcolorResourceId("dashboard_textColor")));
            lt_heading1.setBackgroundColor(getColor(getTextcolorResourceId("dashboard_textColor")));
            lt_heading2.setBackgroundColor(getColor(getTextcolorResourceId("dashboard_textColor")));
            lt_heading3.setBackgroundColor(getColor(getTextcolorResourceId("dashboard_textColor")));
            g_heading1.setBackgroundColor(getColor(getTextcolorResourceId("dashboard_textColor")));
            g_heading2.setBackgroundColor(getColor(getTextcolorResourceId("dashboard_textColor")));
            g_heading3.setBackgroundColor(getColor(getTextcolorResourceId("dashboard_textColor")));
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

    public void getAllDetails() {
        final TextView tv_leadEmpName , tv_setaName , tv_GrantName , tv_GrantNo , tv_LName , tv_LEmail ,tv_LId , tv_LTele ,
                tv_GAName , tv_GAEmail , tv_GATele , tv_AnnualL , tv_PaidL , tv_UnpaidL , tv_SickL ;
        final LinearLayout LL_GD, LL_LD,LL_LT,LL_GAD;
        LL_GD = findViewById(R.id.LL_GD);
        LL_LD = findViewById(R.id.LL_LD);
        LL_LT = findViewById(R.id.LL_LT);
        LL_GAD = findViewById(R.id.LL_GAD);

        LL_GD.setVisibility(View.VISIBLE);
        LL_LD.setVisibility(View.VISIBLE);
        LL_LT.setVisibility(View.VISIBLE);
        LL_GAD.setVisibility(View.VISIBLE);

        tv_leadEmpName = findViewById(R.id.txtLeadEmpName);
        tv_setaName = findViewById(R.id.txtSetaName);
        tv_GrantName = findViewById(R.id.txtGrantName);
        tv_GrantNo = findViewById(R.id.txtGrantNo);
        tv_LName = findViewById(R.id.txtLearnerName);
        tv_LEmail = findViewById(R.id.txtLearnerEmail);
        tv_LId = findViewById(R.id.txtLearnerId);
        tv_LTele = findViewById(R.id.txtLearnerTele);
        tv_GAName = findViewById(R.id.txtGrantAdminName);
        tv_GAEmail = findViewById(R.id.txtGrantEmail);
        tv_GATele = findViewById(R.id.txtGrantATele);
        tv_AnnualL = findViewById(R.id.txtTotalALeave);
        tv_PaidL = findViewById(R.id.txtTotalPaidLeave);
        tv_UnpaidL = findViewById(R.id.txtTotalUnPaidLeave);
        tv_SickL = findViewById(R.id.txtTotalSickLeave);

        String Label = getLabelFromDb("lblLeadEmpName",R.string.lblLeadEmpName);
        lblView = (TextView)findViewById(R.id.lblLeadEmpName);
        lblView.setText(Label);
        Label = getLabelFromDb("lblSetaName",R.string.lblSetaName);
        lblView = (TextView)findViewById(R.id.lblSetaName);
        lblView.setText(Label);
        Label = getLabelFromDb("lblGrantName",R.string.lblGrantName);
        lblView = (TextView)findViewById(R.id.lblGrantName);
        lblView.setText(Label);
        Label = getLabelFromDb("lblGrantNo",R.string.lblGrantNo);
        lblView = (TextView)findViewById(R.id.lblGrantNo);
        lblView.setText(Label);

        Label = getLabelFromDb("lblLearnerName",R.string.lblLearnerName);
        lblView = (TextView)findViewById(R.id.lblLearnerName);
        lblView.setText(Label);
        Label = getLabelFromDb("lblLearnerEmail",R.string.lblLearnerEmail);
        lblView = (TextView)findViewById(R.id.lblLearnerEmail);
        lblView.setText(Label);
        Label = getLabelFromDb("lblLearnerTele",R.string.lblLearnerTele);
        lblView = (TextView)findViewById(R.id.lblLearnerTele);
        lblView.setText(Label);
        Label = getLabelFromDb("lblLearneriD",R.string.lblLearneriD);
        lblView = (TextView)findViewById(R.id.lblLearnerId);
        lblView.setText(Label);

        Label = getLabelFromDb("lblTotalLeave",R.string.lblTotalLeave);
        lblView = (TextView)findViewById(R.id.lblTotalALeave);
        lblView.setText(Label);
        Label = getLabelFromDb("lblTotalUnPaidLeave",R.string.lblTotalUnPaidLeave);
        lblView = (TextView)findViewById(R.id.lblTotalUnPaidLeave);
        lblView.setText(Label);
        Label = getLabelFromDb("lblTotalPaidLeave",R.string.lblTotalPaidLeave);
        lblView = (TextView)findViewById(R.id.lblTotalPaidLeave);
        lblView.setText(Label);
        Label = getLabelFromDb("lblTotalSickLeave",R.string.lblTotalSickLeave);
        lblView = (TextView)findViewById(R.id.lblTotalSickLeave);
        lblView.setText(Label);

        Label = getLabelFromDb("lblGAName",R.string.lblGAName);
        lblView = (TextView)findViewById(R.id.lblGrantAdminName);
        lblView.setText(Label);
        Label = getLabelFromDb("lblGAEmail",R.string.lblGAEmail);
        lblView = (TextView)findViewById(R.id.lblGrantEmail);
        lblView.setText(Label);
        Label = getLabelFromDb("lblGATele",R.string.lblGATele);
        lblView = (TextView)findViewById(R.id.lblGranATele);
        lblView.setText(Label);

        Label = getLabelFromDb("h_GrantDetails",R.string.h_GrantDetails);
        lblView = (TextView)findViewById(R.id.h_GrantDetails);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("h_LDetails",R.string.h_LDetails);
        lblView = (TextView)findViewById(R.id.h_LDetails);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("h_GADetail",R.string.h_GADetail);
        lblView = (TextView)findViewById(R.id.h_GADetails);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("h_TotalLeaves",R.string.h_TotalLeaves);
        lblView = (TextView)findViewById(R.id.h_totalLeaves);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.GET_ALL_DETAILS_URL;
        FINAL_URL=FINAL_URL+"?token="+token+"&learner_id="+student_id;
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
                        tv_leadEmpName.setText(dataM.getString("lead_emp"));
                        tv_setaName.setText(dataM.getString("setaName"));
                        tv_GrantName.setText(dataM.getString("grantName"));
                        tv_GrantNo.setText(dataM.getString("g_d_grant_number"));
                        tv_GAName.setText(dataM.getString("leadManager"));
                        tv_GAEmail.setText(dataM.getString("leadManagerEmail"));
                        tv_GATele.setText(dataM.getString("leadManagerContact"));
                        tv_AnnualL.setText(dataM.getString("annual_leave"));
                        tv_SickL.setText(dataM.getString("sick_leave"));
                        tv_PaidL.setText(dataM.getString("other_paid_leave"));
                        tv_UnpaidL.setText(dataM.getString("unpaid_leave"));
                        tv_LName.setText(dataM.getString("studName"));
                        tv_LId.setText(dataM.getString("s_id_no"));
                        tv_LEmail.setText(dataM.getString("stu_email"));
                        tv_LTele.setText(dataM.getString("stu_cell"));
                        showProgress(false, mContentView, mProgressView);
                    }
                    else if(Status.equals("2")) {
                        showProgress(false,mContentView,mProgressView);
                    }

                    else{
                        //showProgress(false,mContentView,mProgressView);
                        printLogs(LogTag, "fetchData", "error_M345_101 : DATA_ERROR");
                        String sTitle="Error :"+ActivityId+"-101";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    printLogs(LogTag, "fetchData", "error_try_again : " + e.getMessage());
                    e.printStackTrace();
                    //showProgress(false,mContentView,mProgressView);
                    String sTitle = "Error :" + ActivityId + "-102";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                printLogs(LogTag, "fetchData", "error_try_again: " + error.getMessage());
                String sTitle = "Error :" + ActivityId + "-103";
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

    public void callHeaderBuilder(){
        printLogs(LogTag,"callHeaderBuilder","init");
        String tHeader = "";
        rDataObj.addItem(rDataObj.createItem(0,0,tHeader,tHeader,tHeader,tHeader,tHeader,tHeader,tHeader,tHeader,tHeader));
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

    public void fetchData(){
        showProgress(true,mContentRView,mProgressRView);
        String token = userSessionObj.getToken();
        String SUB_URL = "";
        if(type.equalsIgnoreCase("a")) {
            SUB_URL = URLHelper.REF_M_409_A;
        }
        else{
            SUB_URL = URLHelper.REF_M_409_P;
        }
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + SUB_URL;
        FINAL_URL=FINAL_URL+"?token="+token+"&learner_id="+student_id;
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
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        for (int i = 0; i < dataM.length(); i++) {
                            int pos = i+1;
                            JSONObject rec = dataM.getJSONObject(i);

                            int aId2 = Integer.parseInt(rec.getString("id"));
                            String month3 = rec.getString("month");
                            String fromDate4 = rec.getString("from_date");
                            String toDate5 = rec.getString("to_date");
                            String annualLeave6 = rec.getString("annual_leave");
                            String sickLeave7 = rec.getString("paid_sick_leave");
                            String oPaidLeave8 = rec.getString("other_paid_leave");
                            String uPLeave9 = rec.getString("unpaid_leave");
                            String notesLeave10="";
                            String attIds11="";
                            if(type.equalsIgnoreCase("a")) {
                                notesLeave10 = rec.getString("notes");
                                attIds11 =rec.getString("att_id");
                            }
                            else {
                                attIds11 = rec.getString("att_ids");
                            }
                            printLogs(LogTag,"fetchData","att_id:"+attIds11);
                            rDataObj.addItem(rDataObj.createItem(pos,aId2,month3,fromDate4,toDate5,annualLeave6,sickLeave7,oPaidLeave8,uPLeave9,notesLeave10,attIds11));
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
    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Bundle inputUri = new Bundle();
            Intent intent = new Intent(MSLeaveDetailListA.this,MSLeaveListA.class);
            inputUri.putString("student_id", student_id);
            inputUri.putString("m_student_name", m_student_name);
            printLogs(LogTag,"onOptionsItemSelected","MSLeaveListA");
            intent.putExtras(inputUri);
            startActivity(intent);
            finish();
        }
        return true;
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        printLogs(LogTag,"setupRecyclerView","init");
        recyclerView.setAdapter(new MLeavesAdapter(rDataObjList,baseApcContext,activityIn,this));
    }
    public void showList(){
        printLogs(LogTag,"showList","init");
        List<MLeavesObj.Item> AttData = rDataObj.getITEMS();
        MLeavesAdapter adapter = new MLeavesAdapter(AttData,baseApcContext,activityIn,this);
        recyclerViewQ.setAdapter(adapter);
        showProgress(false,mContentRView,mProgressRView);
        showProgress(false,mContentView,mProgressView);
    }
    public String getStudentName(){
        printLogs(LogTag,"getStudentName","-"+m_student_name);
        return m_student_name;
    }
    public String getType(){
        printLogs(LogTag,"getType","-"+type);
        return type;
    }
    public String getStudentId(){
        printLogs(LogTag,"getStudentId","-"+student_id);
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
}
