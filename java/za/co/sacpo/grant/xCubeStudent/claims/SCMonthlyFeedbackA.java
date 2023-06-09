package za.co.sacpo.grant.xCubeStudent.claims;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import java.util.Objects;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.adapter.SCMonthlyFeedbackAdapter;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dataObj.SCMonthlyFeedbackObj;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;

public class SCMonthlyFeedbackA extends BaseAPCPrivate {
    private String ActivityId = "S231";
    public View mProgressView, mContentView,heading;
    public TextView activity_heading,lblView;
    private RecyclerView recyclerViewQ;
    private SCMonthlyFeedbackA thisClass;
    public String generator,is_claim_submitted,is_report_due,is_feedback_pending;
    private Button btn_next,btn_back;
    private String feedback_ratings_input ="";
    public SCMonthlyFeedbackObj rDataObj = new SCMonthlyFeedbackObj();
    private List<SCMonthlyFeedbackObj.Item> rDataObjList = null;
    @Override
    protected void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cAId) {
        baseApcContext = cnt;
        CAId = cAId;
        activityIn = ain;
        LogTag = lt;
        thisClass = this;
        printLogs(lt, "setBaseApcContextParent", "weAreIn");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(), this, this.getClass().getSimpleName(), ActivityId);
        printLogs(LogTag, "onCreate", "init");
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
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext, activityIn);
        if (isConnected) {
            printLogs(LogTag, "bootStrapInit", "initConnected");
            setLayoutXml();
            callFooter(baseApcContext, activityIn, ActivityId);

            initMenusCustom(ActivityId, baseApcContext, activityIn);
            fetchVersionData();
            verifyVersion();
            internetChangeBroadCast();
            initBackBtn();
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
            initializeInputs();
            fetchData();
        }
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag, "initializeViews", "init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        heading = findViewById(R.id.heading);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_back = (Button) findViewById(R.id.btn_back);
        rDataObjList = rDataObj.getITEMS();
        recyclerViewQ = (RecyclerView) findViewById(R.id.rvSCmonthlyfeedback);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);
        recyclerViewQ.setNestedScrollingEnabled(false);
        setupRecyclerView(recyclerViewQ);
        printLogs(LogTag,"initializeViews","exit");
    }
    @Override
    protected void initializeListeners() {
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(feedback_ratings_input.equals("")){
                    showProgress(false,mContentView,mProgressView);
                    String sTitle="Error :"+ActivityId+"-107";
                    String sMessage=getLabelFromDb("error_S231_107",R.string.error_S231_107);
                    String sButtonLabelClose="Close";
                    ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                }
                else{
                    printLogs(LogTag,"initializeListeners","feedback_ratings_input -"+feedback_ratings_input);
                    putApi(feedback_ratings_input);
                }
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle inBundle = new Bundle();
                Intent intent = new Intent(SCMonthlyFeedbackA.this, SConfirmBankDetailsA.class);
                inBundle.putString("generator", ActivityId);
                inBundle.putString("is_claim_submitted", String.valueOf(is_claim_submitted));
                inBundle.putString("is_report_due", String.valueOf(is_report_due));
                inBundle.putString("is_feedback_report_due", String.valueOf(is_feedback_pending));
                intent.putExtras(inBundle);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    protected void initializeInputs() {
    }

    @Override
    protected void initializeLabels() {
        String Label = getLabelFromDb("h_S231",R.string.h_S231);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("l_S231_btn_next",R.string.l_S231_btn_next);
        lblView = (TextView)findViewById(R.id.btn_next);
        lblView.setText(Label);
        Label = getLabelFromDb("l_S231_btn_back",R.string.l_S231_btn_back);
        lblView = (TextView)findViewById(R.id.btn_back);
        lblView.setText(Label);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            btn_next.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            btn_back.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));

        }

    }
    @Override
    protected void setLayoutXml() {
        setContentView(R.layout.a_monthly_feedback);
    }
    public void callDataApi(){
        printLogs(LogTag, "callDataApi", "init");
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
        showProgress(true,mContentView,mProgressView);
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_231;
        FINAL_URL= FINAL_URL + "?token=" + token+"&month_year="+""+"&grant_id="+"";
        printLogs(LogTag,"fetchData","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
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
                            printLogs(LogTag,"fetchData","dataM : "+dataM.length());
                            JSONObject rec = dataM.getJSONObject(i);
                            int aId2 = Integer.parseInt(rec.getString("f_q_id"));
                            String SFQues3 = rec.getString("f_q_question");
                            String SFQ_Id4 = rec.getString("f_q_id");
                            String SANS_TYPE5 = rec.getString("set_answer_type");
                            String SANS_ID6 = rec.getString("set_answer_id");
                            String SANS_TYPE_17 = rec.getString("answer_type1");
                            String SANS_TYPE_ID18 = rec.getString("answer_type_id1");
                            String SANS_TYPE_29 = rec.getString("answer_type2");
                            String SANS_TYPE_ID210 = rec.getString("answer_type_id2");
                            String SANS_TYPE_311 = rec.getString("answer_type3");
                            String SANS_TYPE_ID312 = rec.getString("answer_type_id3");
                            String SANS_TYPE_413 = rec.getString("answer_type4");
                            String SANS_TYPE_ID414 = rec.getString("answer_type_id4");
                            String SANS_TYPE_515 = rec.getString("answer_type5");
                            String SANS_TYPE_ID516 = rec.getString("answer_type_id5");
                            rDataObj.addItem(rDataObj.createItem(pos,aId2,SFQues3,SFQ_Id4,SANS_TYPE5,SANS_ID6,SANS_TYPE_17,SANS_TYPE_ID18,SANS_TYPE_29,SANS_TYPE_ID210,SANS_TYPE_311,SANS_TYPE_ID312,SANS_TYPE_413,SANS_TYPE_ID414,SANS_TYPE_515,SANS_TYPE_ID516));
                        }
                        showList();
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

                    showProgress(false, mContentView, mProgressView);
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
    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
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
            Intent intent = new Intent(SCMonthlyFeedbackA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    private void setupRecyclerView(RecyclerView recyclerView) {
        printLogs(LogTag,"setupRecyclerView","init");
        recyclerView.setAdapter(new SCMonthlyFeedbackAdapter(rDataObjList,baseApcContext,activityIn,this));
    }

    public void showList(){
        printLogs(LogTag,"fetchData","showList : ");
        List<SCMonthlyFeedbackObj.Item> FormData = rDataObj.getITEMS();
        SCMonthlyFeedbackAdapter adapter = new SCMonthlyFeedbackAdapter(FormData,baseApcContext,activityIn,this);
        recyclerViewQ.setAdapter(adapter);
        showProgress(false,mContentView,mProgressView);
    }
    public void setStatusRemarks(String feedback_ratings){
        feedback_ratings_input = feedback_ratings;
        printLogs(LogTag,"setStatusRemarks","GET_feedback_ratings f: "+feedback_ratings);
     }

    private void putApi(final String feedback_ratings) {
        showProgress(true, mContentView, mProgressView);
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_ADD_MONTHLY_FEEDBACK;
        printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put("feedback_ratings", feedback_ratings);

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
                    if(Status.equals("1")){
                        Bundle inBundle = new Bundle();
                        Intent intent = new Intent(SCMonthlyFeedbackA.this,SSubmitClaim.class);
                        inBundle.putString("generator", ActivityId);
                        inBundle.putString("is_claim_submitted", String.valueOf(is_claim_submitted));
                        inBundle.putString("is_report_due", String.valueOf(is_report_due));
                        inBundle.putString("is_feedback_report_due", String.valueOf(is_feedback_pending));
                        intent.putExtras(inBundle);
                        startActivity(intent);
                    }
                    else{
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-104";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }

                } catch (JSONException e) {
                    showProgress(false,mContentView,mProgressView);
                    String sTitle="Error :"+ActivityId+"-105";
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
                        String sTitle="Error :"+ActivityId+"-106";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

                    }
                }){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/json; charset=utf-8");
                header.put("Accept","*/*");
                return header;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(SCMonthlyFeedbackA.this);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }
    public void customRedirector(){
        if(generator.equals("S231")) {
            Bundle inBundle = new Bundle();
            Intent intent = new Intent(SCMonthlyFeedbackA.this, SConfirmBankDetailsA.class);
            inBundle.putString("generator", ActivityId);
            inBundle.putString("is_claim_submitted", String.valueOf(is_claim_submitted));
            inBundle.putString("is_report_due", String.valueOf(is_report_due));
            inBundle.putString("is_feedback_report_due", String.valueOf(is_feedback_pending));
            intent.putExtras(inBundle);
            startActivity(intent);
            finish();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Bundle inBundle = new Bundle();
            Intent intent = new Intent(SCMonthlyFeedbackA.this, SConfirmBankDetailsA.class);
            inBundle.putString("generator", ActivityId);
            inBundle.putString("is_claim_submitted", String.valueOf(is_claim_submitted));
            inBundle.putString("is_report_due", String.valueOf(is_report_due));
            inBundle.putString("is_feedback_report_due", String.valueOf(is_feedback_pending));
            intent.putExtras(inBundle);
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