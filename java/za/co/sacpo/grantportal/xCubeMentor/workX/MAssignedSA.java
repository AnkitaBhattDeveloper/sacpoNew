package za.co.sacpo.grantportal.xCubeMentor.workX;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

import za.co.sacpo.grantportal.AppUpdatedA;
import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.adapter.MAssignSAdapter;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.component.Utils;
import za.co.sacpo.grantportal.xCubeLib.dataObj.MAssignSObj;
import za.co.sacpo.grantportal.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;

public class MAssignedSA extends BaseAPCPrivate {
    private String ActivityId = "303";
    public View mProgressView, mContentView, mProgressRView, mContentRView;
    private TextView lblView, lblWorkstation;
    private RecyclerView recyclerViewQ;
    public MAssignSObj rDataObj = new MAssignSObj();
    private List<MAssignSObj.Item> rDataObjList = null;
    String work_x_id,work_x_name;
    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId)
    {
        baseApcContext = cnt;
        activityIn = ain;
        LogTag = lt;
        CAId = CAId;
        printLogs(lt,"setBaseApcContextParent","weAreIn");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(), this, this.getClass().getSimpleName(),ActivityId);
        Bundle activeInputUri = getIntent().getExtras();
        work_x_id = Integer.toString(activeInputUri.getInt("work_x_id"));
        work_x_name= activeInputUri.getString("work_x_name");
        printLogs(LogTag,"onCreate","work_x_id >"+work_x_id+" work_x_name>"+work_x_name);
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
            fetchVersionData();
            verifyVersion();
            internetChangeBroadCast();

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
            Intent intent = new Intent(MAssignedSA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","a_m_assign_s");
        setContentView(R.layout.a_m_assign_s);

    }


    @Override
    protected void initializeLabels() {




        String Label = getLabelFromDb("h_303",R.string.h_303);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);
    }

  /*  private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(MAssignedSA.this,MWorkXsDA.class);
            printLogs(LogTag,"onOptionsItemSelected","MWorkXsDA");
            startActivity(intent);
            finish();
        }
        return true;
    }
*/
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mContentRView = findViewById(R.id.content_container_r);
        mProgressRView = findViewById(R.id.progress_bar_r);
        lblWorkstation = findViewById(R.id.lblWorkstation);
        rDataObjList = rDataObj.getITEMS();
        recyclerViewQ = (RecyclerView) findViewById(R.id.rVAssignStudent);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);
        View recyclerView = findViewById(R.id.rVAssignStudent);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        printLogs(LogTag,"initializeViews","exit");
    }

    @Override
    protected void initializeInputs() {
        //lblView = (TextView) findViewById(R.id.lblWorkstation);
        //lblView.setText(Label);
        lblWorkstation.setText(work_x_name);
    }



    @Override
    protected void initializeListeners() {




    }

    public void callDataApi() {
        userToken = userSessionObj.getToken();
        if(!TextUtils.isEmpty(userToken)) {
            printLogs(LogTag,"callDataApi","Token Update");
            syncUserData(baseApcContext, activityIn);
            syncMentorData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn,ActivityId);

        }

    }

    public void callHeaderBuilder() {
        String mSLearnerName3 = getLabelFromDb("t_head_303_wx_assign_stu_name", R.string.t_head_303_wx_assign_stu_name);
        String mSRegisNo4 = getLabelFromDb("t_head_303_wx_assign_stu_registration_no", R.string.t_head_303_wx_assign_stu_registration_no);
        String mwxStatus5 = getLabelFromDb("t_head_303_wx_assign_work_station_status", R.string.t_head_303_wx_assign_work_station_status);
        String mSUniversity6 = getLabelFromDb("t_head_303_wx_assign_stu_university_name", R.string.t_head_303_wx_assign_stu_university_name);
        String mwXWorkstationName7 = getLabelFromDb("t_head_303_wx_assign_work_station_name", R.string.t_head_303_wx_assign_work_station_name);


       rDataObj.addItem(rDataObj.createItem(0, 0, mSLearnerName3, mSRegisNo4, mwxStatus5, mSUniversity6,
               mwXWorkstationName7,""));
    }




    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new MAssignSAdapter(rDataObjList, baseApcContext, activityIn,this));
    }

    public void showList() {
        List<MAssignSObj.Item> WorkstationData = rDataObj.getITEMS();
        MAssignSAdapter adapter = new MAssignSAdapter(WorkstationData, baseApcContext, activityIn,this);
        recyclerViewQ.setAdapter(adapter);
        showProgress(false, mContentRView, mProgressRView);
    }

    public void fetchData() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.M_ASSIGN_WORKSSTATION;
        printLogs(LogTag,"getUrl","URL : "+FINAL_URL);
        FINAL_URL=FINAL_URL+"/token/"+token+"/department_id/"+work_x_id;
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
                            int pos = i+1;

                            printLogs(LogTag,"fetchData","dataM : "+dataM.length());
                            JSONObject rec = dataM.getJSONObject(i);
                            int aId2 = Integer.parseInt(rec.getString("l_s_id"));
                            String mSLearnerName3 = rec.getString("student_name");
                            String mSRegisNo4 = rec.getString("reg_no");
                            String mwxStatus5 = rec.getString("l_department_name");
                            //String mwxStatus5 = rec.getString("department_status");
                            String mSUniversity6 = rec.getString("u_name");
                            String mwXWorkstationName7 = rec.getString("q_name");
                            String mwxStudentid8 = rec.getString("s_u_id");

                            rDataObj.addItem(rDataObj.createItem(pos, aId2, mSLearnerName3,mSRegisNo4, mwxStatus5,mSUniversity6,mwXWorkstationName7,mwxStudentid8));
                            showList();
                            //printLogs(LogTag,"fetchData","datshowListaM : "+response);
                        }

                    } else {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-101";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "102";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showProgress(false, mContentView, mProgressView);
                String sTitle = "Error :" + ActivityId + "-103";
                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);

            }
        }) {
        };

        RequestQueue requestQueue = Volley.newRequestQueue(MAssignedSA.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }
    public String getWorkXName(){
        return work_x_name;
    }
    public String getWorkXId(){
        return work_x_id;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(MAssignedSA.this,MWorkXsDA.class);
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





