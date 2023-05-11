package za.co.sacpo.grant.xCubeMentor.claims;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.ListarClientes;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeMentor.MDashboardDA;

public class MLearnerGrantDetailsA extends BaseFormAPCPrivate {


    public String ActivityId = "339";
    public String student_id,month_year, generator,stipend_id,m_student_name,mentor_key_status,selected_EmpStatus,is_last_claim;
    private Button btn_SavenContinue,btn_save,btn_back;
    public View mProgressView, mContentView;
    private TextView lblView,lbl_rejectReason;
    private TextView txtStipend,txtHostEmployer,txtMentor,txtLeadEmployer,txtStartDate,txtEmployerSDLNo,txtLearnerName,txtEndDate;
    private RadioButton rb_reject,rb_approve;
    private RadioGroup rg_container;
    private String KEY_STATUS_APPROVE = "mentor_key_status";
    private String KEY_REJECT_REASON = "reject_Reason";

    private String KEY_START_DATE = "start_date";
    private String KEY_END_DATE = "end_date";
    private String KEY_PERMANENT_EMPLOYEE = "permanent_employee";
    private String KEY_EMPLOYMENT_STATUS = "employment_status";
    private String KEY_EMPLOYMENT_FOS = "employment_fos";
    private String KEY_POSITION = "position";
    private String KEY_SALARY_STATUS = "m_salary";

    int selected_status=0;
    private int learner_status;
    private LinearLayout ll_reject_comment_container,ll_send_withComment,ll_send_without_comment;
    private EditText et_reject_reason;
    MLearnerGrantDetailsA thisClass;


    private RadioButton rb_yes,rb_no;
    private String emp_status;
    private Spinner SpinEmpStatus;
    private EditText inputFos,inputStartDate,inputEndDate,inputPosition,inputMonthlySalary;
    private LinearLayout linearlayoutforPost;

    private int mYear,mMonth,mDay;
    public String mStartDate,mEndDate;
    final ArrayList<ListarClientes> datalist = new ArrayList<>();

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
        Bundle activeInputUri = getIntent().getExtras();
        student_id = activeInputUri.getString("student_id");
        m_student_name = activeInputUri.getString("m_student_name");
        stipend_id = activeInputUri.getString("stipend_id");
        printLogs(LogTag,"onCreate","student_id "+student_id);
        printLogs(LogTag,"onCreate","m_student_name "+m_student_name);
        printLogs(LogTag,"onCreate","stipend_id "+stipend_id);
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
            showProgress(false, mContentView, mProgressView);
        }

    }

    @Override
    protected void initializeViews() {

        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);

        rb_approve = (RadioButton) findViewById(R.id.rb_approve);
        rb_reject = (RadioButton) findViewById(R.id.rb_reject);
        btn_SavenContinue = (Button) findViewById(R.id.btn_SavenContinue);
        btn_save = (Button) findViewById(R.id.btn_save);

        txtStipend = (TextView) findViewById(R.id.txtStipend);

        txtStartDate = (TextView) findViewById(R.id.txtStartDate);
        txtLearnerName = (TextView) findViewById(R.id.txtLearnerName);
        txtEndDate = (TextView) findViewById(R.id.txtEndDate);

        rg_container = (RadioGroup) findViewById(R.id.rg_container);
        ll_reject_comment_container = (LinearLayout) findViewById(R.id.ll_reject_comment_container);
        ll_send_withComment = (LinearLayout) findViewById(R.id.ll_send_withComment);
        ll_send_without_comment = (LinearLayout) findViewById(R.id.ll_send_without_comment);
        lbl_rejectReason = (TextView) findViewById(R.id.lbl_rejectReason);
        et_reject_reason = (EditText) findViewById(R.id.et_reject_reason);
        btn_back = (Button) findViewById(R.id.btn_back);


    }

    @Override
    protected void initializeListeners() {


        
        btn_SavenContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FormSubmit();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });

        
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle inputUri = new Bundle();
                inputUri.putString("generator", ActivityId);
                inputUri.putString("m_student_name", m_student_name);
                inputUri.putString("stipend_id", stipend_id);
                inputUri.putString("student_id", student_id);
                Intent intent = new Intent(MLearnerGrantDetailsA.this, MMonthlyFeedbackA.class);
                printLogs(LogTag, "onOptionsItemSelected", "MMonthlyFeedbackA");
                intent.putExtras(inputUri);
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

       String Label = getLabelFromDb("h_339",R.string.h_339);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);


        Label = getLabelFromDb("l_339_stipend",R.string.l_339_stipend);
        lblView = (TextView)findViewById(R.id.lblStipend);
        lblView.setText(Label);



        Label = getLabelFromDb("l_339_startDate",R.string.l_339_startDate);
        lblView = (TextView)findViewById(R.id.lblStartDate);
        lblView.setText(Label);


        Label = getLabelFromDb("l_339_learnerName",R.string.l_339_learnerName);
        lblView = (TextView)findViewById(R.id.lblLearnerName);
        lblView.setText(Label);

        Label = getLabelFromDb("l_339_endDate",R.string.l_339_endDate);
        lblView = (TextView)findViewById(R.id.lblEndDate);
        lblView.setText(Label);

        Label = getLabelFromDb("rb_339_app",R.string.rb_339_app);
        lblView = (RadioButton)findViewById(R.id.rb_approve);
        lblView.setText(Label);

        Label = getLabelFromDb("rb_339_reject",R.string.rb_339_reject);
        lblView = (RadioButton)findViewById(R.id.rb_reject);
        lblView.setText(Label);

        Label = getLabelFromDb("rb_339_btn_SavenContinue",R.string.rb_339_btn_SavenContinue);
        lblView = (Button)findViewById(R.id.btn_SavenContinue);
        lblView.setText(Label);

        Label = getLabelFromDb("rb_339_btn_SavenContinue",R.string.rb_339_btn_SavenContinue);
        lblView = (Button)findViewById(R.id.btn_save);
        lblView.setText(Label);

        Label = getLabelFromDb("l_339_reject_reason",R.string.l_339_reject_reason);
        lblView = (TextView) findViewById(R.id.lbl_rejectReason);
        lblView.setText(Label);

        Label = getLabelFromDb("l_339_btn_back",R.string.l_339_btn_back);
        lblView = (Button) findViewById(R.id.btn_back);
        lblView.setText(Label);


    }

    @Override
    protected void setLayoutXml() {
        setContentView(R.layout.a_m_learner_grant_details);

    }

    public void fetchData(){
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.M_LIST_LearnerGrantDetails;
        FINAL_URL=FINAL_URL+"?token="+token+"&stipend_id="+stipend_id;
        printLogs(LogTag,"fetchData_ATT","URL : "+FINAL_URL);
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
                        String StudentId=dataM.getString("stuid");
                        int StudentIdInt=Integer.parseInt(StudentId);
                        if(StudentIdInt>0) {

                            is_last_claim = dataM.getString("is_last_claim");

                            txtStipend.setText(dataM.getString("Salary"));
                            txtStartDate.setText(dataM.getString("start_Date"));
                            txtLearnerName.setText(dataM.getString("student_Name"));
                            txtEndDate.setText(dataM.getString("end_Date"));

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

    public void callDataApi(){
        userToken = userSessionObj.getToken();
        if(!TextUtils.isEmpty(userToken)) {
            printLogs(LogTag,"callDataApi","Token Update");
            syncUserData(baseApcContext, activityIn);
            syncMentorData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn,ActivityId);
        }
    }

    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
            Intent intent = new Intent(MLearnerGrantDetailsA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void validateForm() {

      //  boolean cancel = false;
        final String txt_reason = et_reject_reason.getText().toString();

        if (!validateRejectReason(txt_reason)) {
            String sMessage = getLabelFromDb("error_339_109", R.string.error_339_109);
            et_reject_reason.setError(sMessage);

        }/*if (cancel) {
            return;
        } */else {
            showProgress(true, mContentView, mProgressView);
            this.FormSubmitwithComment();
        }
        printLogs(LogTag, "validateForm", "exit");

    }

    private boolean validateRejectReason(String txt_reason) {
        return txt_reason != null && txt_reason.length() > 1;
    }

    @Override
    protected void FormSubmit() {
        showProgress(true, mContentView, mProgressView);
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.M_Post_LearnerGrantDetails;
        String token = userSessionObj.getToken();
        printLogs(LogTag, "FormSubmit", "URL : " + FINAL_URL);


        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put("stipend_id", stipend_id);
            jsonBody.put("mentor_key_status", "1");

            printLogs(LogTag,"FormSubmit","jsonBody "+jsonBody);
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
                    if (Status.equals("1")) {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = getLabelFromDb("m_339_title", R.string.m_339_title);
                        String sMessage = getLabelFromDb("m_339_message_approve", R.string.m_339_message_approve);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showSuccessDialogMLearnerGrantDetailsA(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose, thisClass);

                    } else {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-104";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "-105";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String sTitle = "Error :" + ActivityId + "-106";
                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
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
        RequestQueue requestQueue = Volley.newRequestQueue(MLearnerGrantDetailsA.this);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);


    }


    protected void FormSubmitwithComment() {
        showProgress(true, mContentView, mProgressView);
        final String reject_Reason = et_reject_reason.getText().toString().trim();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.M_Post_LearnerGrantDetails;
        String token = userSessionObj.getToken();
        printLogs(LogTag, "FormSubmit", "URL : " + FINAL_URL);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put("stipend_id", stipend_id);
            jsonBody.put("mentor_key_status", "2");
            jsonBody.put(KEY_REJECT_REASON, reject_Reason);

            printLogs(LogTag,"FormSubmit","jsonBody "+jsonBody);
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
                    if (Status.equals("1")) {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = getLabelFromDb("m_339_title", R.string.m_339_title);
                        String sMessage = getLabelFromDb("m_339_message", R.string.m_339_message);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showSuccessDialogMLearnerGrantDetailsA(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose, thisClass);

                    } else {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-104";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "-105";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String sTitle = "Error :" + ActivityId + "-106";
                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/json; charset=utf-8");
                header.put("Accept","*/*");
                return header;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MLearnerGrantDetailsA.this);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);

    }

    public void customRedirector() {

        Intent intent = new Intent(MLearnerGrantDetailsA.this, MDashboardDA.class);
        Bundle activeUri = new Bundle();
        activeUri.putString("generator", ActivityId);
        activeUri.putString("m_student_name", m_student_name);
        activeUri.putString("student_id", student_id);
        activeUri.putString("stipend_id", stipend_id);
        intent.putExtras(activeUri);
        startActivity(intent);
        finish();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       /* printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            if(generator.equals("103")){*/
        Bundle inputUri = new Bundle();
        inputUri.putString("generator", ActivityId);
        inputUri.putString("m_student_name", m_student_name);
        inputUri.putString("stipend_id", stipend_id);
        inputUri.putString("student_id", student_id);
        Intent intent = new Intent(MLearnerGrantDetailsA.this, MMonthlyFeedbackA.class);
        printLogs(LogTag,"onOptionsItemSelected","MMonthlyFeedbackA");
        intent.putExtras(inputUri);
        startActivity(intent);
        finish();

          /*  }
            else if(generator.equals("120")){
                Intent intent = new Intent(MWeeklyReport.this,MDashboardDA.class);
                printLogs(LogTag,"onOptionsItemSelected","MDashboardDA");
                startActivity(intent);
                finish();
            }
            else {
                printLogs(LogTag,"onOptionsItemSelected","default");
                super.onOptionsItemSelected(item);
            }*/
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
