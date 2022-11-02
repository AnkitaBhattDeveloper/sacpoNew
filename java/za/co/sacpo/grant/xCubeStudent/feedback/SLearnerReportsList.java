package za.co.sacpo.grant.xCubeStudent.feedback;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import za.co.sacpo.grant.xCubeLib.adapter.SSupervisorAdapter;

import za.co.sacpo.grant.xCubeLib.baseFramework.StudentBaseDrawerA;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dataObj.SSupervisorCommentObj;

import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;


/*a_s_learner_reports_list*/
public class SLearnerReportsList extends StudentBaseDrawerA {
    private String ActivityId="337";
    String generator,student_id,group_id,generatorP;
    private final String KEY_STATUS = "s";
    private final String MESSAGE_STATUS = "m";
    private String KEY_COMMENT="comment";
    private String KEY_STUDENT_ID="student_id";
    public EditText inputComment;
    public View mProgressView, mContentView,mProgressRView,mContentRView;
    public TextInputLayout inputLayoutComment,inputLayoutPassword;
    public Button btnCommitContainer,btnEdit,btnDelete;
    public ImageButton btnUploadContainer;
    private RecyclerView recyclerViewQ;
    public SSupervisorCommentObj rDataObj = new SSupervisorCommentObj();

    private List<SSupervisorCommentObj.Item> rDataObjList = null;
    private TextView lblView;

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

        bootStrapInit();
        printLogs(LogTag,"onCreate","initConnected");
    }
    @Override
    protected void bootStrapInit() {
        Boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext, activityIn);
        if(isConnected) {
            printLogs(LogTag,"bootStrapInit","initConnected");
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);

            initMenusCustom(ActivityId,baseApcContext,activityIn);
            fetchVersionData();
            verifyVersion();
            internetChangeBroadCast();
            initializeViews();
            initDrawer();
            showProgress(true,mContentView,mProgressView);
            initializeLabels();
            initializeInputs();
            userToken =userSessionObj.getToken();
            syncToken(baseApcContext,activityIn);
            if(TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
            callDataApi();
            initializeListeners();
            printLogs(LogTag,"bootStrapInit","exitConnected");
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
            Intent intent = new Intent(SLearnerReportsList.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","a_s_learner_reports_list");
        setContentView(R.layout.a_s_learner_reports_list);
    }
    @Override
    protected void initializeViews() {

        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mContentRView = findViewById(R.id.content_container_r);
        mProgressRView = findViewById(R.id.progress_bar_r);
        rDataObjList = rDataObj.getITEMS();
        recyclerViewQ = (RecyclerView) findViewById(R.id.rvComments);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);

        View recyclerView = findViewById(R.id.rvComments);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        printLogs(LogTag,"initializeViews","exit");
        btnCommitContainer = (Button) findViewById(R.id.btnCommitContainer);
        btnUploadContainer = (ImageButton) findViewById(R.id.btnUploadContainer);
    }
    @Override
    protected void initializeLabels(){
        String Label = getLabelFromDb("l_337_comments",R.string.l_337_comments);
        lblView = (TextView)findViewById(R.id.lblComment);
        lblView.setText(Label);

        Label = getLabelFromDb("l_337_comments",R.string.l_337_comments);
        lblView = (TextView)findViewById(R.id.btnCommitContainer);
        lblView.setText(Label);


        Label = getLabelFromDb("h_337",R.string.h_337);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);
    }
    @Override
    protected void initializeInputs(){
        printLogs(LogTag,"initializeInputs","init");
    }
    @Override
    protected void initializeListeners() {
        btnCommitContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });



    }
    public void validateForm() {
        boolean cancel = false;

        if (cancel) {
            return;
        } else {
            showProgress(true, mContentView, mProgressView);
            this.FormSubmit();
        }
        printLogs(LogTag,"validateForm","exit");
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

    private void fetchData(){
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_SUPERVISOR_COMMENTS;
        FINAL_URL=FINAL_URL+"/token/"+token;
        printLogs(LogTag,"fetchData","URL : "+FINAL_URL);
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
                            int aId2 = Integer.parseInt(rec.getString("c_u_r_id"));
                            String lblRef3 = rec.getString("c_u_r_comment");
                            String getUserName4 = rec.getString("mentor_name");
                            String getlblDate5 = rec.getString("c_u_r_a_date");
                            String getImage6 = "";
                            rDataObj.addItem(rDataObj.createItem(pos,aId2,lblRef3,getUserName4,getlblDate5));
                            showList();
                        }
                        showProgress(false,mContentRView,mProgressRView);

                    }   else if(Status.equals("2")) {
                        showProgress(false,mContentView,mProgressView);
                    }
                    else{
                        showProgress(false,mContentRView,mProgressRView);
                        printLogs(LogTag, "fetchData", "error_try_again : DATA_ERROR");
                        String sTitle="Error :"+ActivityId+"-101";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    printLogs(LogTag, "fetchData", "error_try_again : " + e.getMessage());
                    e.printStackTrace();
                    showProgress(false,mContentRView,mProgressRView);
                    String sTitle = "Error :" + ActivityId + "-102";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showProgress(false,mContentRView,mProgressRView);
                printLogs(LogTag, "fetchData", "error_try_again: " + error.getMessage());
                String sTitle = "Error :" + ActivityId + "-103";
                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);

            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(SLearnerReportsList.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }


    public void FormSubmit(){

    }
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        printLogs(LogTag,"setupRecyclerView","init");
        recyclerView.setAdapter(new SSupervisorAdapter(rDataObjList,baseApcContext,activityIn,ActivityId));
    }
    public void showList(){
        printLogs(LogTag,"showList","init");
        List<SSupervisorCommentObj.Item> NGData = rDataObj.getITEMS();
        SSupervisorAdapter adapter = new SSupervisorAdapter(NGData,baseApcContext,activityIn,ActivityId);
        recyclerViewQ.setAdapter(adapter);
        showProgress(false,mContentRView,mProgressRView);
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