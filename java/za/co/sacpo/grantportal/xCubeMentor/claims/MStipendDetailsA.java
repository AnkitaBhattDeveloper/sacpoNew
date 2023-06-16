package za.co.sacpo.grantportal.xCubeMentor.claims;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


/*a_m_stipend*/
public class MStipendDetailsA extends BaseAPCPrivate {

    public String ActivityId = "191";
    public View mProgressView, mContentView;
    String generator,group_id;
    public Button btnSubmit;

    public TextView txtStudentName,txtDate,txtLeaveType,txtStatus,txtReason,txtRemark,
            lblStudentName,lblDate,lblLeaveType,lblStatus,lblReason,txtWorkedDays,lblWorkedDays;
    public View mProgressRView, mContentRView;
    public EditText mSubjectView;
    public LinearLayout details_container;
    public TextInputLayout inputLayoutSubject;
    private TextView lblView;
    public String grant_id,date,month_year,student_id,StuName,month_year19;
    String s_id;
    int selected_leave_type=0;
    int aId2=0;

     Bundle activeUri;
    String m_student_name,stipend_id;
    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cTAId) {
        baseApcContext = cnt;CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        printLogs(lt,"setBaseApcContextParent","weAreIn");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);
        printLogs(LogTag,"onCreate","init");

        Bundle activeInputUri = getIntent().getExtras();
        s_id = activeInputUri.getString("s_id");
        student_id = activeInputUri.getString("student_id");
        m_student_name = activeInputUri.getString("m_student_name");
        StuName = activeInputUri.getString("StuName");
        generator = activeInputUri.getString("generator");
       // month_year = activeInputUri.getString("month_year");
        printLogs(LogTag,"onCreate","MYEAR" +month_year);

        month_year = activeInputUri.getString("month_year19");
        printLogs(LogTag,"onCreate","month_year19" +month_year19);

        stipend_id = activeInputUri.getString("stipend_id");
        printLogs(LogTag,"onCreate","stipend_id "+stipend_id);
        printLogs(LogTag,"onCreate","StuName "+StuName);
        printLogs(LogTag,"onCreate","m_student_name "+m_student_name);



        //   if (activeInputUri.hasExtra("m_student_name")) {


        generator = activeInputUri.getString("generator");
        printLogs(LogTag,"onCreate","generator "+generator);


        printLogs(LogTag,"onCreate","Date Input "+s_id);
        printLogs(LogTag,"onCreate","Attendance ID "+student_id);
        printLogs(LogTag,"onCreate","GENERATOR ID "+generator);

        bootStrapInit();


    }
    @Override
    protected void bootStrapInit() {
        Boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext, activityIn);
        if (isConnected == true) {
            syncToken(baseApcContext, activityIn);
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);

            initMenusCustom(ActivityId,baseApcContext,activityIn);

            //   showProgress(true,mContentView,mProgressView);
            fetchVersionData();
            verifyVersion();
            internetChangeBroadCast();
            initializeViews();
            initBackBtn();
            initializeLabels();
            initializeListeners();
            userToken =userSessionObj.getToken();
            syncToken(baseApcContext,activityIn);
            if(TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
            callDataApi();
            getDetails();
            initializeInputs();
            printLogs(LogTag,"initializeInputs","exitConnected");
        }
    }


    @Override
    protected void initializeLabels() {

        String    Label = getLabelFromDb("h_191", R.string.h_191);
        lblView = (TextView) findViewById(R.id.HeadingDetails);
        lblView.setText(Label);


        Label = getLabelFromDb("l_191_btn_submit", R.string.l_191_btn_submit);
        lblView = (Button) findViewById(R.id.btnSubmit);
        lblView.setText(Label);

        Label = getLabelFromDb("l_191_m_S_month", R.string.l_191_m_S_month);
        lblView = (TextView) findViewById(R.id.lblStudentName);
        lblView.setText(Label);

        Label = getLabelFromDb("l_191_m_amt", R.string.l_191_m_amt);
        lblView = (TextView) findViewById(R.id.lblDate);
        lblView.setText(Label);

        Label = getLabelFromDb("l_191_m_att", R.string.l_191_m_att);
        lblView = (TextView) findViewById(R.id.lblLeaveType);
        lblView.setText(Label);

        Label = getLabelFromDb("l_191_m_leave", R.string.l_191_m_leave);
        lblView = (TextView) findViewById(R.id.lblStatus);
        lblView.setText(Label);

        Label = getLabelFromDb("l_191_m_worked_days", R.string.l_191_m_worked_days);
        lblView = (TextView) findViewById(R.id.lblWorkedDays);
        lblView.setText(Label);


    }
    @Override
    protected void initializeViews() {
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mContentRView = findViewById(R.id.content_container_r);
        mProgressRView = findViewById(R.id.progress_bar_r);

        mSubjectView = (EditText) findViewById(R.id.inputMessage);

        inputLayoutSubject = (TextInputLayout) findViewById(R.id.inputLayoutMessage);


        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        txtStudentName = (TextView) findViewById(R.id.txtStudentName);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtLeaveType = (TextView) findViewById(R.id.txtLeaveType);
        txtStatus = (TextView) findViewById(R.id.txtStatus);
        txtWorkedDays = (TextView) findViewById(R.id.txtWorkedDays);


        lblStudentName = (TextView) findViewById(R.id.lblStudentName);
        lblDate = (TextView) findViewById(R.id.lblDate);
        lblLeaveType = (TextView) findViewById(R.id.lblLeaveType);
        lblStatus = (TextView) findViewById(R.id.lblStatus);
        txtWorkedDays = (TextView) findViewById(R.id.txtWorkedDays);


        details_container = (LinearLayout) findViewById(R.id.details_container);


        printLogs(LogTag,"initializeViews","exit");
    }

    @Override
    protected void initializeInputs() {

        printLogs(LogTag,"bootStrapInit","exitConnected");
        showProgress(false,mContentView,mProgressView);

    }
    @Override
    protected void initializeListeners() {

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Bundle inputUri = new Bundle();
                inputUri.putString("student_id", student_id);
                inputUri.putString("m_student_name", m_student_name);
                inputUri.putString("StuName", StuName);
                inputUri.putString("stipend_id", stipend_id);
                inputUri.putString("generator", "312");
                inputUri.putString("month_year",month_year);
                printLogs(LogTag,"onClick","MONTHYEAR" +month_year);
                printLogs(LogTag,"onCreate","STIPEND ID "+stipend_id);
                Intent intent=new Intent(MStipendDetailsA.this,MApproveA.class);
                intent.putExtras(inputUri);
                startActivity(intent);
                finish();

            }
        });

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
        if(isUpdate==true){
            Intent intent = new Intent(MStipendDetailsA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    public void callDataApi(){
        printLogs(LogTag,"callDataApi","init");
        userToken = userSessionObj.getToken();
        if(!TextUtils.isEmpty(userToken)) {
            printLogs(LogTag,"callDataApi","Token Update");
            syncUserData(baseApcContext, activityIn);
            syncMentorData(baseApcContext, activityIn);
            syncGrantData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn,ActivityId);

        }
    }
    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","a_m_stipend");
        setContentView(R.layout.a_m_stipend);
    }


    private void getDetails() {
        details_container.setVisibility(View.VISIBLE);

        String token = userSessionObj.getToken();

            String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.VIEW_STIPEND_DETAILS;
            FINAL_URL=FINAL_URL+"/token/"+token+"/month_year/"+month_year+"/student_id/"+student_id;

            printLogs(LogTag,"getstipendclaimDetails","URL : "+FINAL_URL);

            StringRequest stringRequest = new StringRequest(Request.Method.GET,FINAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject outputJson = null;
                try {
                    printLogs(LogTag,"getstipendclaimDetails","RESPONSE : "+response);
                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONObject dataM = outputJson.getJSONObject(KEY_DATA);

                        txtStudentName.setText(dataM.getString("month"));//STIPEND MONTH
                        txtDate.setText(dataM.getString("salary"));//STIPEND AMOUNT
                        txtLeaveType.setText(dataM.getString("working_day"));//ATTENDANCE COUNT
                        txtStatus.setText(dataM.getString("leave"));//LEAVE COUNT
                        txtWorkedDays.setText(dataM.getString("holiday_count"));//HOLIDAYS




                        /* txtMonth.setText(dataM.getString("month"));
                            txtAmount.setText(dataM.getString("amount"));
                            txtAmount.setText(dataM.getString("att_count"));
                            txtleaveCount.setText(dataM.getString("leave_count"));
                            txtleaveCount.setText(dataM.getString("worked_days"));*/
                        showProgress(false,mContentView,mProgressView);
                    }

                    else if(Status.equals("2")) {
                        showProgress(false,mContentView,mProgressView);
                    }


                    else{
                        //showProgress(false,mContentView,mProgressView);
                        printLogs(LogTag,"getLeaveDetails","error_try_again : DATA_ERROR");
                        String sTitle="Error :"+ActivityId+"-100";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    printLogs(LogTag,"getLeaveDetails","error_try_again : "+e.getMessage());
                    e.printStackTrace();
                    //showProgress(false,mContentView,mProgressView);
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
                        printLogs(LogTag,"getLeaveDetails","error_try_again : "+error.getMessage());
                        //showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-102";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

                    }
                })
        {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(MStipendDetailsA.this);
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
            Bundle inputUri = new Bundle();
            inputUri.putString("student_id", student_id);
            inputUri.putString("m_student_name", m_student_name);
            inputUri.putString("StuName", StuName);
            inputUri.putString("generator", "205");
            printLogs(LogTag,"onOptionsItemSelected","MPastClaimA"+student_id+m_student_name+StuName);
            Intent intent = new Intent(MStipendDetailsA.this, MPastClaimA.class);
            intent.putExtras(inputUri);
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