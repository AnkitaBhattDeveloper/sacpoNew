package za.co.sacpo.grant.xCubeMentor.workX;

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

import za.co.sacpo.grant.xCubeLib.adapter.MNAssignSAdapter;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dataObj.MNAssignSObj;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;

import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;


public class MNonAssignSA extends BaseAPCPrivate {
    private String ActivityId = "304";
    public View mProgressView, mContentView, mProgressRView, mContentRView;
    private TextView lblView;
    private RecyclerView recyclerViewQ;
    public MNAssignSObj rDataObj = new MNAssignSObj();
    private List<MNAssignSObj.Item> rDataObjList = null;
    String work_x_id;
    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId)
    {
        baseApcContext = cnt;
        CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        printLogs(lt, "setBaseApcContextParent", "weAreIn"+CAId);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(), this, this.getClass().getSimpleName(),ActivityId);
        Bundle activeInputUri = getIntent().getExtras();
        /* work_x_id = Integer.toString(activeInputUri.getInt("work_x_id"));
        printLogs(LogTag,"onCreate","work_x_id "+work_x_id);*/
        bootStrapInit();
    }

    @Override
    protected void bootStrapInit() {
        Boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext, activityIn);
        if (isConnected == true) {
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);
            initMenusCustom(ActivityId,baseApcContext,activityIn);
            fetchVersionData();
            verifyVersion();
            internetChangeBroadCast();
            initializeViews();
            initBackBtn();
            showProgress(true, mContentView, mProgressView);
            initializeLabels();
            initializeListeners();
            userToken = userSessionObj.getToken();
            syncToken(baseApcContext, activityIn);
            if (TextUtils.isEmpty(userToken)) {
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
        registerBroadcastIC();
    }



    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        if (isUpdate == true) {
            Intent intent = new Intent(MNonAssignSA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","a_m_n_assign_s");
        setContentView(R.layout.a_m_n_assign_s);
    }

    @Override
    protected void initializeViews() {
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mContentRView = findViewById(R.id.content_container_r);
        mProgressRView = findViewById(R.id.progress_bar_r);
        rDataObjList = rDataObj.getITEMS();
        recyclerViewQ = (RecyclerView) findViewById(R.id.rVNonAssignStudent);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);

        View recyclerView = findViewById(R.id.rVNonAssignStudent);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        printLogs(LogTag,"initializeViews","exit");
    }

    @Override
    protected void initializeLabels() {

        String Label = getLabelFromDb("l_304_workstation", R.string.l_304_NonAssign_Workstation);
        lblView = (TextView) findViewById(R.id.lblNONAssignWorkstation);
        lblView.setText(Label);

        Label = getLabelFromDb("h_304",R.string.h_304);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);
        printLogs(LogTag,"initializeInputs","exit");
    }

    @Override
    protected void initializeInputs() {
        printLogs(LogTag,"initializeViews","exit");
    }






    @Override
    protected void initializeListeners() {

       /* mIWorkstationTTText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sToolTip = getLabelFromDb("tt_304_scroll", R.string.tt_304_scroll);
                showTooltip(mIWorkstationTTText, sToolTip, 4);
            }
            }
        });*/


    }

    public void callDataApi() {
        userToken = userSessionObj.getToken();
        if (!TextUtils.isEmpty(userToken)) {
            syncUserData(baseApcContext, activityIn);
            syncMentorData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn,ActivityId);
        }
    }

    public void callHeaderBuilder() {

      /*  String tHeader3 = getLabelFromDb("t_head_304_wx_non_assign_u_id3", R.string.t_head_304_wx_non_assign_u_id3);
        String tHeader4 = getLabelFromDb("t_head_304_wx_non_assign_stu_name4", R.string.t_head_304_wx_non_assign_stu_name4);
        String tHeader5 = getLabelFromDb("t_head_304_wx_non_assign_stu_reg_no5", R.string.t_head_304_wx_non_assign_stu_reg_no5);
        String tHeader6 = getLabelFromDb("t_head_304_wx_non_assign_work_station_name6", R.string.t_head_304_wx_non_assign_work_station_name6);
        String tHeader7 = getLabelFromDb("t_head_304_wx_non_assign_uni_name7", R.string.t_head_304_wx_non_assign_uni_name7);
        String tHeader8 = getLabelFromDb("t_head_304_wx_non_assign_email8", R.string.t_head_304_wx_non_assign_email8);
*/
        String tHeader3 = getLabelFromDb("t_head_304_wx_non_assign_u_id3", R.string.t_head_304_wx_non_assign_u_id3);
        String tHeader4 = getLabelFromDb("t_head_304_wx_non_assign_stu_name4", R.string.t_head_304_wx_non_assign_stu_name4);
        String tHeader5 = "";
        String tHeader6 = "";
        String tHeader7 = getLabelFromDb("t_head_304_wx_non_assign_uni_name7", R.string.t_head_304_wx_non_assign_uni_name7);
        String tHeader8 = getLabelFromDb("t_head_304_wx_non_assign_email8", R.string.t_head_304_wx_non_assign_email8);

        rDataObj.addItem(rDataObj.createItem(0, 0, tHeader3, tHeader4, tHeader5, tHeader6,tHeader7,tHeader8));
    }
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new MNAssignSAdapter(rDataObjList, baseApcContext, activityIn));
    }

    public void showList() {
        List<MNAssignSObj.Item> WorkstationData = rDataObj.getITEMS();
        MNAssignSAdapter adapter = new MNAssignSAdapter(WorkstationData, baseApcContext, activityIn);
        recyclerViewQ.setAdapter(adapter);
        showProgress(false, mContentRView, mProgressRView);
    }

    public void fetchData() {
        String token = userSessionObj.getToken();
       // String dept_id = studentSessionObj.getGrantId();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.M_NON_ASSIGN_WORKSTATION;

        FINAL_URL = FINAL_URL + "/token/" + token /*+ "/work_x_id/"*/  ;
        printLogs(LogTag,"fetchData","URL : "+FINAL_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, FINAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject outputJson = null;
                try {
                    printLogs(LogTag,"onResponse","onResponse : "+response);
                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if (Status.equals("1")) {

                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        for (int i = 0; i < dataM.length(); i++) {
                            int pos = i + 1;
                            JSONObject rec = dataM.getJSONObject(i);
                            int aId2 = Integer.parseInt(rec.getString("s_id"));
                            String mwXStu_User_id3 = rec.getString("s_u_id");
                            String mwX_student_name4 = rec.getString("student_name");
                            String mwX_reg_no5 = rec.getString("reg_no");
                            String mwX_Name6 = rec.getString("q_name");
                            String mwX_university_name7 = rec.getString("u_name");
                            String mwX_Student_Emaill8 = rec.getString("email");

               rDataObj.addItem(rDataObj.createItem(pos, aId2, mwXStu_User_id3,mwX_student_name4, mwX_reg_no5,mwX_Name6,mwX_university_name7,mwX_Student_Emaill8));
                            showList();
                        }
                        showProgress(false,mContentRView,mProgressRView);
                    }
                    else if(Status.equals("2")) {
                        showProgress(false,mContentRView,mProgressRView);
                    } else {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-101";
                        String sMessage = getLabelFromDb("error_304_101", R.string.error_304_101);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "103";
                    String sMessage = getLabelFromDb("error_304_103", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showProgress(false, mContentView, mProgressView);
                String sTitle = "Error :" + ActivityId + "-104";
                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);

            }
        }) {
        };

        RequestQueue requestQueue = Volley.newRequestQueue(MNonAssignSA.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(MNonAssignSA.this,MWorkXsDA.class);
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
