package za.co.sacpo.grant.xCubeStudent.claims;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;

public class SPendingReportsDA extends BaseAPCPrivate {

    public String ActivityId = "S192";
    public View mProgressView, mContentView;

    public Button btn_next;
    public ImageView imgGraph;
    private TextView lblView,txtWarningPost,txtWarningPre,txtWarningHeading;
    public String generator,is_claim_submitted,is_report_due,is_feedback_pending;


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
        Intent inputIntent = getIntent();
        Bundle activeInputUri = getIntent().getExtras();
        is_claim_submitted= "1";
        is_report_due="1";
        is_feedback_pending="1";
        if (inputIntent.hasExtra("is_claim_submitted")) {
            is_claim_submitted = activeInputUri.getString("is_claim_submitted");
            printLogs(LogTag,"onCreate","is_claim_submitted "+is_claim_submitted);
        }
        if (inputIntent.hasExtra("is_feedback_report_due")) {
            is_feedback_pending = activeInputUri.getString("is_feedback_report_due");
            printLogs(LogTag,"onCreate","is_feedback_pending "+is_feedback_pending);
        }
        if (inputIntent.hasExtra("is_report_due")) {
            is_report_due = activeInputUri.getString("is_report_due");
            printLogs(LogTag,"onCreate","is_report_due "+is_report_due);
        }
        if (inputIntent.hasExtra("generator")) {
            generator = activeInputUri.getString("generator");
            printLogs(LogTag,"onCreate","GENERATOR ID "+generator);
        }

        bootStrapInit();
    }
    @Override
    protected void bootStrapInit() {
        Boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext,activityIn);
        if(isConnected ==true) {
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);
            initMenusCustom(ActivityId,baseApcContext,activityIn);
            fetchVersionData();
            verifyVersion();
            initBackBtn();
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
            fetchData();
            //showProgress(false,mContentView,mProgressView);
        }
    }

    @Override
    protected void internetChangeBroadCast() {
        registerBroadcastIC();
    }
    protected void fetchVersionData(){
        printLogs(LogTag,"fetchVersionData","exitConnected");
        syncUpdates(baseApcContext,activityIn);
    }
    protected void verifyVersion(){
        printLogs(LogTag,"verifyVersion","init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag,"verifyVersion","isUpdate "+isUpdate);
        printLogs(LogTag,"verifyVersion","isUpdate "+ utilSessionObj.getIsUpdateRequired());
        if(isUpdate==true){
            Intent intent = new Intent(SPendingReportsDA.this,AppUpdatedA.class);
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
        printLogs(LogTag,"setLayoutXml","a_s_overdue_claims");

        setContentView(R.layout.a_s_overdue_claims);
    }


    @Override
    protected void initializeViews() {
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        btn_next = (Button) findViewById(R.id.btn_next);
        txtWarningPost = (TextView) findViewById(R.id.txtWarningPost);
        txtWarningPre = (TextView) findViewById(R.id.txtWarningPre);
        txtWarningHeading = (TextView) findViewById(R.id.txtWarningHeading);
        imgGraph = (ImageView) findViewById(R.id.imgGraph);

        printLogs(LogTag,"initializeViews","exit");

    }
    @Override
    protected void initializeLabels(){
        String Label = getLabelFromDb("b_S192_continue",R.string.b_S192_continue);
        btn_next.setText(Label);

        Label = getLabelFromDb("b_S192_w_pre",R.string.b_S192_w_post);
        txtWarningPost.setText(Label);

        Label = getLabelFromDb("b_S192_w_post",R.string.b_S192_w_pre);
        txtWarningPre.setText(Label);

        Label = getLabelFromDb("b_S192_w_heading",R.string.b_S192_w_heading);
        txtWarningHeading.setText(Label);

    }
    @Override
    protected void initializeInputs(){
        printLogs(LogTag,"initializeInputs","init");
    }


    @Override
    protected void initializeListeners() {
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printLogs(LogTag,"initializeListeners","SAttSummaryList");
                /*CLAIM PROCESS HALTED*/
                Bundle inBundle = new Bundle();

                Intent intent = new Intent(SPendingReportsDA.this, SAttSummaryList.class);
                inBundle.putString("generator", ActivityId);
                inBundle.putString("is_feedback_report_due", String.valueOf(is_feedback_pending));
                inBundle.putString("is_claim_submitted", String.valueOf(is_claim_submitted));
                inBundle.putString("is_report_due", String.valueOf(is_report_due));
                intent.putExtras(inBundle);
                startActivity(intent);
                finish();
            }
        });
    }



    public void callDataApi(){
        userToken = userSessionObj.getToken();
        if(!TextUtils.isEmpty(userToken)) {
            syncUserData(baseApcContext, activityIn);
            syncStudentData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn,ActivityId);

        }
    }

    public void fetchData(){
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_192;
        FINAL_URL=FINAL_URL+"?token="+token;
        printLogs(LogTag,"fetchData","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchData", "response : " + response);
                    String Status = jsonObject.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONObject dataM = jsonObject.getJSONObject(KEY_DATA);

                        String Label = getLabelFromDb("b_S192_w_post",R.string.b_S192_w_pre);
                        txtWarningPre.setText(Label+" "+dataM.getString("overDueMonthYear"));

                        String imagGraphS =URLHelper.DOMAIN_URL+dataM.getString("chart_link");
                        printLogs(LogTag,"fetchData","imagGraphS : "+imagGraphS);
                        Picasso.get().load(imagGraphS).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).networkPolicy(NetworkPolicy.NO_CACHE).into(imgGraph);

                        showProgress(false,mContentView,mProgressView);
                       }
                    else if(Status.equals("2")) {
                        showProgress(false,mContentView,mProgressView);
                    }
                    else{
                        String sTitle="Error :"+ActivityId+"-102";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                        showProgress(false,mContentView,mProgressView);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    printLogs(LogTag,"fetchData","error_try_again : "+e.getMessage());
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
                        String sMessage=getLabelFromDb("error_S126_104",R.string.error_S126_104);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

                    }
                }){
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(SPendingReportsDA.this,SPastClaimDA.class);
            Bundle inBundle = new Bundle();
            inBundle.putString("generator", ActivityId);
            inBundle.putString("is_claim_submitted", String.valueOf(is_claim_submitted));
            inBundle.putString("is_report_due", String.valueOf(is_report_due));
            inBundle.putString("is_feedback_report_due", String.valueOf(is_feedback_pending));
            intent.putExtras(inBundle);
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