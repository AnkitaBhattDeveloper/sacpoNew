package za.co.sacpo.grantportal.xCubeStudent.claims;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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

import org.json.JSONException;
import org.json.JSONObject;

import za.co.sacpo.grantportal.AppUpdatedA;
import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.component.Utils;
import za.co.sacpo.grantportal.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;

public class SStipendDetailsA extends BaseAPCPrivate {

    public String ActivityId = "190";
    public String KEY_ID = "id";
    public View mProgressView, mContentView;
    private TextView lblView;
    private TextView txtMonth,lblMonth,lblAmount,txtAmount,lblAttCount,txtAttCount,lblLeaveCount,txtleaveCount,lblWorkedDays,txtWorkedDays;
    
    public LinearLayout lblHeadView,lblBodyView,lblFooterView;
    public CardView lblCardView;
    private Button btnSubmit;
    String generator,t_id,group_id,month_year,stipend_id,student_id,clamDate;
    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId) {
        baseApcContext = cnt;
        CAId = cTAId;
        activityIn = ain;
        LogTag = lt;
        printLogs(lt, "setBaseApcContextParent", "weAreIn");
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);
        printLogs(LogTag,"onCreate","init");
        Bundle activeInputUri = getIntent().getExtras();
        t_id = activeInputUri.getString("t_id");
        month_year = activeInputUri.getString("month_year");
        stipend_id = activeInputUri.getString("stipend_id");
        clamDate = activeInputUri.getString("clamDate");
        generator = activeInputUri.getString("generator");
        group_id = activeInputUri.getString("group_id");
        printLogs(LogTag,"onCreate","T ID "+t_id);
        printLogs(LogTag,"onCreate","MYEAR" +month_year);
        printLogs(LogTag,"onCreate","STIPENDID" +stipend_id);
        printLogs(LogTag,"onCreate","GENERATOR ID "+generator);
        printLogs(LogTag,"onCreate","DATE "+clamDate);
        bootStrapInit();
    }
    @Override
    protected void bootStrapInit() {
        printLogs(LogTag,"onOptionsItemSelected","MONTHYEAR1" +month_year);
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
            printLogs(LogTag,"bootStrapInit","exitConnected");
            showProgress(false,mContentView,mProgressView);
        }
    }
    @Override
    protected void internetChangeBroadCast(){

        printLogs(LogTag,"internetChangeBroadCast","MONTHZXxxxxxxxxxxxxxxxxxx" +month_year);

        printLogs(LogTag,"internetChangeBroadCast","init");
        registerBroadcastIC();
    }
    @Override
    protected void fetchVersionData(){
        printLogs(LogTag,"fetchVersionData","exitConnected");
        printLogs(LogTag,"fetchVersionData","MONTHZXxxxxxxxxxxxxxxxxxx" +month_year);

        syncUpdates(baseApcContext,activityIn);
    }
    @Override
    protected void verifyVersion(){
        printLogs(LogTag,"verifyVersion","init");
        printLogs(LogTag,"verifyVersion","MONTHZXxxxxxxxxxxxxxxxxxx" +month_year);

        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag,"verifyVersion","isUpdate "+isUpdate);
        printLogs(LogTag,"verifyVersion","isUpdate "+ utilSessionObj.getIsUpdateRequired());
        if(isUpdate){
            Intent intent = new Intent(SStipendDetailsA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void setLayoutXml() {

        printLogs(LogTag,"setLayoutXml","MONTHZXxxxxxxxxxxxxxxxxxx" +month_year);

        printLogs(LogTag,"setLayoutXml","a_s_stipend");

        setContentView(R.layout.a_s_stipend);
    }


    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        printLogs(LogTag,"initializeViews","MONTHZXxxxxxxxxxxxxxxxxxx" +month_year);

        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);

        lblAmount= (TextView) findViewById(R.id.lblAmount);
        txtAmount= (TextView) findViewById(R.id.txtAmount);
        txtMonth= (TextView) findViewById(R.id.txtMonth);
        lblMonth= (TextView) findViewById(R.id.lblMonth);
        txtAttCount= (TextView) findViewById(R.id.txtAttCount);
        lblAttCount= (TextView) findViewById(R.id.lblAttCount);
        lblLeaveCount= (TextView) findViewById(R.id.lblLeaveCount);
        txtleaveCount= (TextView) findViewById(R.id.txtleaveCount);
        lblWorkedDays= (TextView) findViewById(R.id.lblWorkedDays);
        txtWorkedDays= (TextView) findViewById(R.id.txtWorkedDays);

        lblCardView = (CardView) findViewById(R.id.cardView);
        lblHeadView = (LinearLayout) findViewById(R.id.head_container);
        lblBodyView = (LinearLayout) findViewById(R.id.body_container);
        lblFooterView = (LinearLayout) findViewById(R.id.footer_container);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        txtMonth.setText(clamDate);
        printLogs(LogTag,"initializeViews","exit");
    }
    @Override
    protected void initializeLabels(){
        printLogs(LogTag,"initializeLabels","init");
        printLogs(LogTag,"initializeLabels","MONTHZXxxxxxxxxxxxxxxxxxx" +month_year);

        String Label = getLabelFromDb("h_190",R.string.h_190);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);

        Label = getLabelFromDb("l_190_s_month",R.string.l_190_s_month);
        lblView = (TextView)findViewById(R.id.lblMonth);
        lblView.setText(Label);

        Label = getLabelFromDb("l_190_s_amt",R.string.l_190_s_amt);
        lblView = (TextView)findViewById(R.id.lblAmount);
        lblView.setText(Label);

        Label = getLabelFromDb("l_190_s_att",R.string.l_190_s_att);
        lblView = (TextView)findViewById(R.id.lblAttCount);
        lblView.setText(Label);

        Label = getLabelFromDb("l_190_s_leave",R.string.l_190_s_leave);
        lblView = (TextView)findViewById(R.id.lblLeaveCount);
        lblView.setText(Label);

        Label = getLabelFromDb("l_190_s_worked_days",R.string.l_190_s_worked_days);
        lblView = (TextView)findViewById(R.id.lblWorkedDays);
        lblView.setText(Label);

        Label = getLabelFromDb("l_190_s_btn_submit",R.string.l_190_s_btn_submit);
        lblView = (Button)findViewById(R.id.btnSubmit);
        lblView.setText(Label);
    }

    @Override
    protected void initializeInputs(){
        printLogs(LogTag,"initializeInputs","init");
        printLogs(LogTag,"initializeInputs","MONTHZXxxxxxxxxxxxxxxxxxx" +month_year);

        fetchData();
    }

    @Override
    protected void initializeListeners() {
        printLogs(LogTag,"initializeListeners","init");
        printLogs(LogTag,"initializeListeners","MMMMMMMMMMMMMMMM" +month_year);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 printLogs(LogTag,"onClick","MONTHYEAR" +month_year);
                 Intent intent = new Intent(SStipendDetailsA.this,SSubmitClaim.class);
                 printLogs(LogTag,"onOptionsItemSelected","SSubmitClaim");
                 Bundle inputUri = new Bundle();
                 inputUri.putString("month_year",month_year);
                 intent.putExtras(inputUri);
                startActivity(intent);
                finish();
            }
        });
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
    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void fetchData(){
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.VIEW_STIPEND_DETAILS;
        FINAL_URL=FINAL_URL+"/token/"+token+"/month_year/"+month_year;
        printLogs(LogTag,"fetchData","URL : "+FINAL_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,FINAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject outputJson = null;
                try {
                    printLogs(LogTag,"fetchData","RESPONSE : "+response);
                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONObject dataM = outputJson.getJSONObject(KEY_DATA);
                        String StipendId=dataM.getString("s_s_g_student_id");
                        int StipendIdInt=Integer.parseInt(StipendId);
                        if(StipendIdInt>0) {


                            txtAmount.setText(dataM.getString("salary"));
                            txtleaveCount.setText(dataM.getString("leave"));
                            txtWorkedDays.setText(dataM.getString("working_day"));

                            showProgress(false,mContentView,mProgressView);
                        }
                        else if(Status.equals("2")) {
                            showProgress(false,mContentView,mProgressView);
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
                }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(SStipendDetailsA.this);
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

                Intent intent = new Intent(SStipendDetailsA.this, SPastClaimDA.class);
                printLogs(LogTag,"onOptionsItemSelected","SClaimDA");
                Bundle inputUri = new Bundle();
                intent.putExtras(inputUri);
                startActivity(intent);
                finish();

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