package za.co.sacpo.grantportal.xCubeMentor.leaves;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import za.co.sacpo.grantportal.AppUpdatedA;
import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.component.Utils;
import za.co.sacpo.grantportal.xCubeLib.db.DbHelper;
import za.co.sacpo.grantportal.xCubeLib.db.DbSchema;
import za.co.sacpo.grantportal.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grantportal.xCubeLib.spinners.SpinnerSet;
import za.co.sacpo.grantportal.xCubeLib.spinners.SpinnerSetAdapter;


public class MLeaveApproval extends BaseFormAPCPrivate {
    private String ActivityId = "M411";
    private String KEY_LEAVE_ID = "att_ids";
    private String KEY_LEAVE_STATUSD = "disapprove";
    private String KEY_LEAVE_STATUSA = "approve";
    public String KEY_MESSAGE = "reject_reason";
    public EditText inputHoliDaysName, inputHoliDaysDate;
    public View mProgressView, mContentView;
    String generator,type,attendance_id;
    MLeaveApproval thisClass;
    public Button btnSubmit;
    public LinearLayout LLApprovalContainer,LLRemark,MessageContainer;
    public TextView txtStudentName,txtDate,txtLeaveType,txtStatus,txtReason,txtRemark,lblStudentName,txtDateApplied,
            lblDate,lblLeaveType,lblStatus,lblReason;
    public View mProgressRView, mContentRView;
    public EditText mSubjectView;
    public LinearLayout details_container;
    public TextInputLayout inputLayoutSubject;
    private TextView lblView;
    public String grant_id,date;
    int selected_leave_type=0;
    int aId2=0;
    Spinner inputSpinnerApproveType;
    private SpinnerSetAdapter adapterM;
    private static SpinnerSet[] leaveTypeMasters;
    Bundle activeUri;
    String student_id,m_student_name,att_ids;
    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cTAId) {
        baseApcContext = cnt;CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        thisClass = this;
        printLogs(lt,"setBaseApcContextParent","weAreIn");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);
        printLogs(LogTag,"onCreate","init");
        Bundle activeInputUri = getIntent().getExtras();
        type = activeInputUri.getString("type");
        student_id = activeInputUri.getString("student_id");
        m_student_name = activeInputUri.getString("m_student_name");
        generator = activeInputUri.getString("generator");
        att_ids = activeInputUri.getString("att_ids");
        attendance_id = activeInputUri.getString("attendance_id");
        printLogs(LogTag,"onCreate","m_student_name"+m_student_name+"type--"+"/att_ids/"+att_ids);
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
            initializeInputs();
            printLogs(LogTag,"initializeInputs","exitConnected");
        }
    }


    @Override
    protected void initializeLabels() {
        String Label = getLabelFromDb("l_330_approve_type", R.string.l_330_approve_type);
        lblView = (TextView) findViewById(R.id.lblapproveType);
        lblView.setText(Label);
        Label = getLabelFromDb("l_330_reason", R.string.l_330_reason);
        lblView = (TextView) findViewById(R.id.lblleaveReason);
        lblView.setText(Label);
        if(type.equalsIgnoreCase("p")){
            Label = getLabelFromDb("l_330_details", R.string.l_330_details);
        }
        else{
            Label = getLabelFromDb("l_330_details_leave", R.string.l_330_details_leave);
        }
        lblView = (TextView) findViewById(R.id.HeadingDetails);
        lblView.setText(Label);
        lblView.setText(Label);
        Label = getLabelFromDb("l_330_btn_submit", R.string.l_330_btn_submit);
        lblView = (TextView) findViewById(R.id.btnSubmit);
        lblView.setText(Label);
        Label = getLabelFromDb("l_330_s_name", R.string.l_330_s_name);
        lblView = (TextView) findViewById(R.id.lblStudentName);
        lblView.setText(Label);
        Label = getLabelFromDb("l_330_date", R.string.l_330_date);
        lblView = (TextView) findViewById(R.id.lblDate);
        lblView.setText(Label);
        Label = getLabelFromDb("l_330_leave_type", R.string.l_330_leave_type);
        lblView = (TextView) findViewById(R.id.lblLeaveType);
        lblView.setText(Label);
        Label = getLabelFromDb("l_330_l_status", R.string.l_330_l_status);
        lblView = (TextView) findViewById(R.id.lblStatus);
        lblView.setText(Label);
        Label = getLabelFromDb("l_330_l_reason", R.string.l_330_l_reason);
        lblView = (TextView) findViewById(R.id.lblReason);
        lblView.setText(Label);
        Label = getLabelFromDb("l_330_l_applied_date", R.string.l_330_l_applied_date);
        lblView = (TextView) findViewById(R.id.lblDateApplied);
        lblView.setText(Label);
        Label = getLabelFromDb("l_330_l_reason", R.string.l_330_m_remark);
        lblView = (TextView) findViewById(R.id.lblRemark);
        lblView.setText(Label);
    }

    @Override
    protected void initializeViews() {
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mContentRView = findViewById(R.id.content_container_r);
        mProgressRView = findViewById(R.id.progress_bar_r);
        mSubjectView = (EditText) findViewById(R.id.inputMessage);
        inputSpinnerApproveType = (Spinner) findViewById(R.id.inputSpinnerApproveType);
        inputLayoutSubject = (TextInputLayout) findViewById(R.id.inputLayoutMessage);
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        txtStudentName = (TextView) findViewById(R.id.txtStudentName);
        txtDateApplied = (TextView) findViewById(R.id.txtDateApplied);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtLeaveType = (TextView) findViewById(R.id.txtLeaveType);
        txtStatus = (TextView) findViewById(R.id.txtStatus);
        txtReason = (TextView) findViewById(R.id.txtReason);
        txtRemark = (TextView) findViewById(R.id.txtRemark);
        lblStudentName = (TextView) findViewById(R.id.lblStudentName);
        lblDate = (TextView) findViewById(R.id.lblDate);
        lblLeaveType = (TextView) findViewById(R.id.lblLeaveType);
        lblStatus = (TextView) findViewById(R.id.lblStatus);
        lblReason = (TextView) findViewById(R.id.lblReason);
        details_container = (LinearLayout) findViewById(R.id.details_container);
        LLApprovalContainer = findViewById(R.id.LLApprovalContainer);
        LLRemark=findViewById(R.id.LLRemark);
        MessageContainer=findViewById(R.id.MessageContainer);
        printLogs(LogTag,"initializeViews","exit");
    }

    @Override
    protected void initializeInputs() {
        if(type.equalsIgnoreCase("p")){
            LLApprovalContainer.setVisibility(View.VISIBLE);
        }
        else{
            LLApprovalContainer.setVisibility(View.GONE);
        }
        txtStudentName.setText(m_student_name);
        getLeaveDetails();
        showProgress(false,mContentView,mProgressView);
        printLogs(LogTag,"initializeInputs","init");
        dbSetaObj = DbHelper.getInstance(this);
        Cursor resUTM = dbSetaObj.getAllByType(DbSchema.TABLE_MASTER_ARRAYS,DbSchema.COLUMN_M_A_TYPE,4);
        int total_leave_type = resUTM.getCount();
        leaveTypeMasters = new SpinnerSet[total_leave_type];
        if(total_leave_type > 0) {
            int i=0;
            try {
                while (resUTM.moveToNext()) {
                    leaveTypeMasters[i] = new SpinnerSet();
                    leaveTypeMasters[i].setId(resUTM.getInt(resUTM.getColumnIndex(DbSchema.COLUMN_M_A_SERVER_ID)));
                    leaveTypeMasters[i].setName(resUTM.getString(resUTM.getColumnIndex(DbSchema.COLUMN_M_A_NAME)));
                    i++;
                }
            } finally {
                if (resUTM != null && !resUTM.isClosed()) {
                    resUTM.close();
                }
            }
        }

        adapterM = new SpinnerSetAdapter(MLeaveApproval.this,android.R.layout.simple_spinner_item,leaveTypeMasters);
        inputSpinnerApproveType.setAdapter(adapterM);
    }
    @Override
    protected void initializeListeners() {

        printLogs(LogTag,"initializeListeners","init");
        inputSpinnerApproveType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,int position, long id) {
                int item =position;
                selected_leave_type = leaveTypeMasters[item].getId();
                if(selected_leave_type==2){
                    MessageContainer.setVisibility(View.VISIBLE);
                }
                else{
                    MessageContainer.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {selected_leave_type=0;}
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
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
            Intent intent = new Intent(MLeaveApproval.this,AppUpdatedA.class);
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
        printLogs(LogTag,"setLayoutXml","a_m_leave_approval");
        setContentView(R.layout.a_m_leave_approval);
    }

    public void validateForm() {
        boolean cancel = false;

        if (!isSpinnerValid(selected_leave_type)) {
            String ErrorTitle = "Message :" + ActivityId + "-222";
            String ErrorMessage = getLabelFromDb("error_330_109", R.string.error_330_109);
            spinnerError(ErrorTitle, ErrorMessage);
            cancel = true;

        }
        if(selected_leave_type==2) {
            if (!validateSubject(mSubjectView, inputLayoutSubject)) {
                cancel = true;
            }
        }
        if (cancel) {
            return;
        } else {
            showProgress(true, mContentView, mProgressView);
            this.FormSubmit();
        }
        printLogs(LogTag,"validateForm","exit");
    }

    private void getLeaveDetails() {
        details_container.setVisibility(View.VISIBLE);

            String token = userSessionObj.getToken();
            String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.REF_M_411_1;
            FINAL_URL=FINAL_URL+"?token="+token+"&attendance_id="+attendance_id;


            printLogs(LogTag,"getLeaveDetails","URL : "+FINAL_URL);
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
                            txtDate.setText(dataM.getString("s_a_leave_date"));
                            txtDateApplied.setText(dataM.getString("s_a_add_date"));
                            txtLeaveType.setText(dataM.getString("s_a_leave_type"));
                            int status_id = Integer.parseInt(dataM.getString("s_a_status_id"));
                            if(status_id==0){
                                LLRemark.setVisibility(View.GONE);
                                LLApprovalContainer.setVisibility(View.VISIBLE);
                            }
                            else if(status_id==1){
                                LLRemark.setVisibility(View.GONE);
                                LLApprovalContainer.setVisibility(View.GONE);
                            }
                            else{
                                LLRemark.setVisibility(View.VISIBLE);
                                LLApprovalContainer.setVisibility(View.GONE);
                            }
                            txtStatus.setText(dataM.getString("s_a_leave_status"));
                            txtReason.setText(dataM.getString("s_a_leave_reason"));
                            txtRemark.setText(dataM.getString("s_a_leave_reject_reason"));
                            if(type.equalsIgnoreCase("p")){
                                LLApprovalContainer.setVisibility(View.VISIBLE);
                            }
                            else{
                                LLApprovalContainer.setVisibility(View.GONE);
                            }
                            showProgress(false,mContentView,mProgressView);
                        }

                        else if(Status.equals("2")) {
                            showProgress(false,mContentView,mProgressView);
                        }


                        else{
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

        public boolean validateSubject(EditText mSubjectView,TextInputLayout inputLayoutSubject) {
        String data = mSubjectView.getText().toString().trim();
        setCustomError(inputLayoutSubject,null,mSubjectView);
        printLogs(LogTag,"validateSubject","DATA : "+data);
            Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/<>.^*()%!-]");

        if (regex.matcher(data).find()) {
            String sMessage = getLabelFromDb("error_330_108",R.string.error_330_108);
            setCustomError(inputLayoutSubject,sMessage,mSubjectView);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutSubject,mSubjectView);
            return true;
        }
    }



    public void FormSubmit() {
        final String mentor_leave_remarks = mSubjectView.getText().toString().trim();
        String token = userSessionObj.getToken();
        boolean statusUrl = false;
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL;
        if(selected_leave_type==1){
            statusUrl = true;
            FINAL_URL = FINAL_URL + URLHelper.REF_M_411_2_A;
        } else{
            statusUrl = true;
            FINAL_URL = FINAL_URL+ URLHelper.REF_M_411_2_D;
        }
        if(statusUrl) {
            printLogs(LogTag, "FormSubmit", "URL : " + FINAL_URL);
            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("token", token);
                jsonBody.put(KEY_LEAVE_STATUSA, "1");
                jsonBody.put(KEY_LEAVE_STATUSD, "1");
                jsonBody.put(KEY_MESSAGE, mentor_leave_remarks);
                jsonBody.put(KEY_LEAVE_ID, att_ids);
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
                        if (Status.equals("1")) {
                            showProgress(false, mContentView, mProgressView);
                            String sTitle = getLabelFromDb("m_330_title", R.string.m_330_title);
                            String sMessage = getLabelFromDb("m_330_message", R.string.m_330_message);
                            String sButtonLabelClose = "Close";
                            ErrorDialog.showSuccessDialogMLeaveApproval(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose, thisClass);
                        } else {
                            showProgress(false, mContentView, mProgressView);
                            String sTitle = "Error :" + ActivityId + "-101";
                            String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                            String sButtonLabelClose = "Close";
                            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                        }
                    } catch (JSONException e) {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-102";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            showProgress(false, mContentView, mProgressView);
                            String sTitle = "Error :" + ActivityId + "-103";
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
            RequestQueue requestQueue = Volley.newRequestQueue(MLeaveApproval.this);
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(jsonObjectRequest);
        }
    }

    public void  customRedirector(){
        Bundle inputUri = new Bundle();
        inputUri.putString("m_student_name", m_student_name);
        printLogs(LogTag,"customRedirector","m_student_name"+m_student_name);
        inputUri.putString("generator", "M411");
        inputUri.putString("type", type);
        inputUri.putString("student_id", student_id);
        inputUri.putString("m_student_name", m_student_name);
        Intent intent = new Intent(MLeaveApproval.this, MSLeaveDetailListA.class);
        intent.putExtras(inputUri);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Bundle inputUri = new Bundle();
            inputUri.putString("student_id", student_id);
            inputUri.putString("m_student_name", m_student_name);
            inputUri.putString("generator", "M411");
            inputUri.putString("type", type);
            printLogs(LogTag,"onOptionsItemSelected","MSLeaveDetailListA");
            Intent intent = new Intent(MLeaveApproval.this, MSLeaveDetailListA.class);
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