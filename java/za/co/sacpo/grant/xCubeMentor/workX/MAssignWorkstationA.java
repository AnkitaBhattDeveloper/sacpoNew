package za.co.sacpo.grant.xCubeMentor.workX;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.adapter.MAssignWorkAdapter;
import za.co.sacpo.grant.xCubeLib.baseFramework.MentorBaseDrawerA;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dataObj.MAssginWorkObj;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeMentor.MDashboardDA;

public class MAssignWorkstationA extends MentorBaseDrawerA {

    private String ActivityId = "246";
    public View mProgressView, mContentView;
    public TextView lblView;


    private RecyclerView recyclerViewAw;
    public MAssginWorkObj rDataObj = new MAssginWorkObj();
    private List<MAssginWorkObj.Item> rDataObjList = null;


    @Override
    protected void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cAId) {

        baseApcContext = cnt;
        CAId = cAId;
        activityIn = ain;
        LogTag = lt;
        printLogs(lt, "setBaseApcContextParent", "weAreIn");
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(), this, this.getClass().getSimpleName(), ActivityId);
        printLogs(LogTag, "onCreate", "init");
        bootStrapInit();


    }

    @Override
    protected void bootStrapInit() {
        Boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext, activityIn);
        if (isConnected) {
            printLogs(LogTag, "bootStrapInit", "initConnected");
            setLayoutXml();
            callFooter(baseApcContext, activityIn, ActivityId);
            initMenusCustom(ActivityId, baseApcContext, activityIn);
            fetchVersionData();
            verifyVersion();
            internetChangeBroadCast();
            initDrawer();
            initializeViews();
            showProgress(true, mContentView, mProgressView);
            initializeLabels();
            initializeListeners();
            userToken = userSessionObj.getToken();
            syncToken(baseApcContext, activityIn);
            if (TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
            callDataApi();
            callHeaderBuilder();
            fetchData();
            initializeInputs();
            showProgress(false, mContentView, mProgressView);
        }
    }



    @Override
    protected void initializeViews() {

        printLogs(LogTag, "initializeViews", "init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);



        rDataObjList = rDataObj.getITEMS();
        recyclerViewAw = (RecyclerView) findViewById(R.id.rVAssignWorkstation);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewAw.setLayoutManager(linearLayoutManager);

        View recyclerView = findViewById(R.id.rVAssignWorkstation);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        printLogs(LogTag,"setupRecyclerView","init");
        recyclerView.setAdapter(new MAssignWorkAdapter(rDataObjList,baseApcContext,activityIn));
        
    }

    @Override
    protected void initializeListeners() {

    }

    @Override
    protected void initializeInputs() {

    }

    @Override
    protected void initializeLabels() {

        String Label = getLabelFromDb("h_246",R.string.h_246);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);
    }



    private void callHeaderBuilder() {



        String tHeader3 = getLabelFromDb("l_claim_246_learner_name",R.string.l_claim_246_learner_name);
        String tHeader4 = getLabelFromDb("l_claim_246_email",R.string.l_claim_246_email);
        String tHeader5 = getLabelFromDb("l_claim_246_cell_no",R.string.l_claim_246_cell_no);
        String tHeader6= getLabelFromDb("l_claim_246_grant_name",R.string.l_claim_246_grant_name);
        String tHeader7= getLabelFromDb("l_claim_246_start_date",R.string.l_claim_246_start_date);
        String tHeader8= getLabelFromDb("l_claim_246_end_date",R.string.l_claim_246_end_date);


        rDataObj.addItem(rDataObj.createItem(0,0,tHeader3,tHeader4,tHeader5,tHeader6,tHeader7,tHeader8,"","","",""));

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


    @Override
    protected void setLayoutXml() {

        setContentView(R.layout.a_massign_workstation);
    }

    @Override
    protected void internetChangeBroadCast() {
        printLogs(LogTag, "internetChangeBroadCast", "init");
        registerBroadcastIC();
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
        if (isUpdate) {
            Intent intent = new Intent(MAssignWorkstationA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }


    private void fetchData() {


        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.ASSIGN_WORKSTATION +"/token/"+token;

        printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,FINAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject outputJson = null;
                printLogs(LogTag,"fetchData","RESPONSE : "+response);
                try {

                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        for (int i = 0; i < dataM.length(); i++) {
                            int pos = i+1;
                            JSONObject rec = dataM.getJSONObject(i);
                            int aId2 = Integer.parseInt(rec.getString("u_id"));
                            String MaLearnerName3 = rec.getString("u_name");
                            String MaEmail4 = rec.getString("u_email");
                            String MaCellNo5 = rec.getString("u_cell");
                            String MaGrantName6 = rec.getString("za");
                            String MaStartDate7 = rec.getString("grant_start");
                            String MaEndDate8 = rec.getString("grant_end");
                            String MaBgColor9 = rec.getString("workstations_status");
                            String MabtnName10 = rec.getString("workstation_name");
                            String mWorkstaionid11 = rec.getString("workstation_id");
                            String mLinkedStudentId12 = rec.getString("l_s_id");


                            rDataObj.addItem(rDataObj.createItem(pos,aId2,MaLearnerName3,MaEmail4,MaCellNo5,MaGrantName6,MaStartDate7,MaEndDate8,MaBgColor9,MabtnName10,mWorkstaionid11,mLinkedStudentId12));

                            showList();

                        }
                        showProgress(false,mContentView,mProgressView);
                    }
                    else if(Status.equals("2")) {
                        showProgress(false,mContentView,mProgressView);

                    }
                    else{
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-102";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
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
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-104";
                        String sMessage=getLabelFromDb("error_246_104",R.string.error_246_104);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                        printLogs(LogTag,"fetchData","error_246_104 : "+error.getMessage());
                        printLogs(LogTag,"fetchData","error_246_104 : "+error.toString());
                        printLogs(LogTag,"fetchData","error_246_104 : "+error.getLocalizedMessage());
                    }
                }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(MAssignWorkstationA.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }


    public void showList(){
        List<MAssginWorkObj.Item> AssignWork = rDataObj.getITEMS();
        MAssignWorkAdapter adapter = new MAssignWorkAdapter(AssignWork,baseApcContext,activityIn);
        recyclerViewAw.setAdapter(adapter);
        showProgress(false,mContentView,mProgressView);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {

            Bundle inputUri = new Bundle();
            printLogs(LogTag,"onOptionsItemSelected","SurveyListA");
            Intent intent = new Intent(MAssignWorkstationA.this, MDashboardDA.class);
            intent.putExtras(inputUri);
            startActivity(intent);
            finish();
        } else {
            printLogs(LogTag,"onOptionsItemSelected","default");
            super.onOptionsItemSelected(item);
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
