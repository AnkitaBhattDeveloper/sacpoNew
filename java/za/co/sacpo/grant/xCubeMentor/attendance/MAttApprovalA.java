package za.co.sacpo.grant.xCubeMentor.attendance;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeLib.db.DbSchema;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeLib.spinners.SpinnerSet;
import za.co.sacpo.grant.xCubeLib.spinners.SpinnerSetAdapter;

public class MAttApprovalA extends BaseFormAPCPrivate {
    private String ActivityId="165";
    public View mProgressView, mContentView;
    private TextView lblView;
    public TextView lblMAddAttRemark,lblAttType;
    private String KEY_TYPE="attendance_status";
    private String KEY_E_ID="attendance_id";
    private String KEY_ADD_REMARK="remark";
    private  String generator;
    String attendance_id;
    int selected_leave_type=0;
    Spinner inputSpinnerAttType;
    private SpinnerSetAdapter adapterAttApproval;
    private static SpinnerSet[] attendenceTypeMasters;
    public EditText inputMAddAttRemark;
    public TextInputLayout inputLayoutMMAddAttRemark;
    String student_id,m_student_name;
    Button btnSubmit;

    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cTAId){
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
        printLogs(LogTag,"onCreate","init");
        Bundle activeInputUri = getIntent().getExtras();
        attendance_id = activeInputUri.getString("attendance_id");
        generator = activeInputUri.getString("generator");
        m_student_name = activeInputUri.getString("m_student_name");
        student_id = activeInputUri.getString("student_id");
        printLogs(LogTag,"onCreate","m_student_name "+m_student_name);
        printLogs(LogTag,"onCreate","student ID "+student_id);
        printLogs(LogTag,"onCreate","Attendance ID "+attendance_id);
        printLogs(LogTag,"onCreate","GENERATOR ID "+generator);


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
            showProgress(false,mContentView,mProgressView);
            syncAlerts(baseApcContext, activityIn,ActivityId);
            printLogs(LogTag,"onCreate","exitConnected");

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
            Intent intent = new Intent(MAttApprovalA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void setLayoutXml() {
        setContentView(R.layout.activity_matt_approal_map);
        printLogs(LogTag,"setLayoutXml","a_attendance_approval");
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        inputMAddAttRemark = (EditText) findViewById(R.id.inputMAddAttRemark);
        inputLayoutMMAddAttRemark = (TextInputLayout) findViewById(R.id.inputLayoutMMAddAttRemark);
        inputSpinnerAttType = (Spinner) findViewById(R.id.inputSpinnerAttType);
        lblMAddAttRemark = (TextView) findViewById(R.id.lblMAddAttRemark);
        lblAttType = (TextView) findViewById(R.id.lbl_AttType);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
    }

    @Override
    protected void initializeLabels(){
        printLogs(LogTag,"initializeLabels","init");
        String Label = getLabelFromDb("lbl_165_lblMAddAttRemark",R.string.lbl_165_lblMAddAttRemark);
        lblView = (TextView)findViewById(R.id.lblMAddAttRemark);
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_165_type",R.string.lbl_165_type);
        lblView = (TextView) findViewById(R.id.lbl_AttType);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_165_submit",R.string.lbl_165_submit);
        lblView = (TextView)findViewById(R.id.btnSubmit);
        lblView.setText(Label);

        Label = getLabelFromDb("h_165",R.string.h_165);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);
    }
    @Override
    protected void initializeInputs(){

        printLogs(LogTag,"bootStrapInit","exitConnected");
        showProgress(false,mContentView,mProgressView);

        printLogs(LogTag,"initializeInputs","init");
        dbSetaObj = DbHelper.getInstance(this);

        Cursor resUTM = dbSetaObj.getAllByType(DbSchema.TABLE_MASTER_ARRAYS,DbSchema.COLUMN_M_A_TYPE,5);
        int total_leave_type = resUTM.getCount();
        attendenceTypeMasters = new SpinnerSet[total_leave_type];
        if(total_leave_type > 0) {
            int i=0;
            try {
                while (resUTM.moveToNext()) {
                    attendenceTypeMasters[i] = new SpinnerSet();
                    attendenceTypeMasters[i].setId(resUTM.getInt(resUTM.getColumnIndex(DbSchema.COLUMN_M_A_SERVER_ID)));
                    attendenceTypeMasters[i].setName(resUTM.getString(resUTM.getColumnIndex(DbSchema.COLUMN_M_A_NAME)));
                    i++;
                }
            } finally {
                if (resUTM != null && !resUTM.isClosed()) {
                    resUTM.close();
                }
            }
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    validateForm();

                }
            });
        }
        adapterAttApproval = new SpinnerSetAdapter(MAttApprovalA.this,android.R.layout.simple_spinner_item,attendenceTypeMasters);
        inputSpinnerAttType.setAdapter(adapterAttApproval);


    }



    @Override
    protected void initializeListeners() {

        printLogs(LogTag,"initializeListeners","init");
        inputSpinnerAttType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,int position, long id) {
                int item =position;
                selected_leave_type = attendenceTypeMasters[item].getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {selected_leave_type=0;}
        });

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

    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public void validateForm() {
        boolean cancel = false;
        if(!isSpinnerValid(selected_leave_type)){
            cancel = true;
        }
        if(!validateRemarks(inputMAddAttRemark,inputLayoutMMAddAttRemark)){
            cancel = true;
        }
        if (cancel) {
            return;
        } else {
            showProgress(true,mContentView,mProgressView);
            this.FormSubmit();
        }
        printLogs(LogTag,"validateForm","exit");
    }
    @Override
    protected void FormSubmit() {


        final String LeaveType = String.valueOf(selected_leave_type);
        final String add_remark = inputMAddAttRemark.getText().toString().trim();

        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.M_ATTENDENCE_APPRVAL;
        printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put(KEY_E_ID ,attendance_id);
            jsonBody.put(KEY_TYPE ,LeaveType);
            jsonBody.put(KEY_ADD_REMARK ,add_remark);
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
                        printLogs(LogTag,"FormSubmit","SUCCESS : "+Status);
                        showProgress(false,mContentView,mProgressView);
                        String sTitle=getLabelFromDb("m_165_message",R.string.m_165_message);
                        String sMessage=getLabelFromDb("m_165_title",R.string.m_165_title);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                        //     LLFormContainer.setVisibility(View.GONE);
                        String Label = getLabelFromDb("i_165_approval_att_apply_succes",R.string.i_165_approval_att_apply_succes);
                      /*  lblView = (TextView)findViewById(R.id.iNoActiveGrant);
                        lblView.setText(Label);*/
                        //   LLInformationContainer.setVisibility(View.VISIBLE);
                    }
                    else{
                        printLogs(LogTag,"FormSubmit","else");
                        showProgress(false,mContentView,mProgressView);
                        String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-101";
                        // String sButtonLabelClose="Close";
                        // ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false,mContentView,mProgressView);
                    String sTitle="Error :"+ActivityId+"-102";
                    String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                    String sButtonLabelClose="Close";
                    ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        printLogs(LogTag,"onCreate","error listener");
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-103";
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);


    }


    public boolean validateRemarks(EditText inputMAddAttRemark,TextInputLayout inputLayoutMMAddAttRemark) {
        printLogs(LogTag,"onCreate","validateReason");
        String remarks = inputMAddAttRemark.getText().toString().trim();
        setCustomError(inputLayoutMMAddAttRemark,null,inputMAddAttRemark);
        if (remarks.isEmpty() || isValidMessageContent(remarks)) {
            String sMessage = getLabelFromDb("error_165_116",R.string.error_165_116);
            setCustomError(inputLayoutMMAddAttRemark,sMessage,inputMAddAttRemark);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutMMAddAttRemark,inputMAddAttRemark);
            return true;
        }
    }
    protected class ErrorTextWatcher implements TextWatcher {

        private EditText EditView;
        private TextInputLayout EditLayout;

        private ErrorTextWatcher(EditText EditView,TextInputLayout EditLayout) {
            printLogs(LogTag,"onCreate","ErrorTextWatcher");
            this.EditView = EditView;
            this.EditLayout = EditLayout;
        }
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void afterTextChanged(Editable editable) {
            if (EditView.getId() == R.id.inputMRemark) {
                validateRemarks(EditView, EditLayout);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Bundle inputUri = new Bundle();
            inputUri.putString("student_id", student_id);
            inputUri.putString("m_student_name", m_student_name);
            inputUri.putString("generator", "312");
            printLogs(LogTag,"onOptionsItemSelected","MAttSummaryA");
            Intent intent = new Intent(MAttApprovalA.this, MAttSummaryA.class);
            intent.putExtras(inputUri);
            startActivity(intent);
            finish();
        }
        return true;
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