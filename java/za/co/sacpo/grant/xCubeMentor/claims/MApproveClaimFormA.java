package za.co.sacpo.grant.xCubeMentor.claims;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import static za.co.sacpo.grant.xCubeLib.component.URLHelper.M_APPROVE_LEAVE;

public class MApproveClaimFormA extends BaseFormAPCPrivate {

    private String ActivityId = "153";

    private String KEY_U_TYPE_ID = "user_type_id";
    private String KEY_LEAVE_TYPE = "";
    public EditText inputHoliDaysName, inputHoliDaysDate;
    public View mProgressView, mContentView;

    public Button btnSubmit;
    public View mProgressRView, mContentRView;
    public EditText inputMStudentID, inputLeaveReasons, inputMStuDate;

    public TextInputLayout inputLayoutMStudentID, inputLayoutLeaveReasons, inputLayoutMStuDate;
    private TextView lblView;
    String generator;

    int selected_leave_type=0;
    Spinner inputSpinnerApproveType;
    private SpinnerSetAdapter adapterM;

    private static SpinnerSet[] leaveTypeMasters;

    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId) {
        baseApcContext = cnt;CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        printLogs(lt,"setBaseApcContextParent","weAreIn");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);
        bootStrapInit();


    }
    @Override
    protected void bootStrapInit() {
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext, activityIn);
        if (isConnected) {
            syncToken(baseApcContext, activityIn);
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);

            initMenusCustom(ActivityId,baseApcContext,activityIn);

            showProgress(true,mContentView,mProgressView);
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
        String Label = getLabelFromDb("l_153_approve_type", R.string.l_153_approve_type);
        lblView = (TextView) findViewById(R.id.lblapproveType);
        lblView.setText(Label);
        Label = getLabelFromDb("l_153_approve_date", R.string.l_153_approve_date);
        lblView = (TextView) findViewById(R.id.lblDate);
        lblView.setText(Label);
        Label = getLabelFromDb("h_153",R.string.h_153);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);
        Label = getLabelFromDb("l_153_btn_save", R.string.l_153_btn_save);
        lblView = (TextView) findViewById(R.id.btnSubmit);
        btnSubmit.setText(Label);


    }


    @Override
    protected void initializeViews() {
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mContentRView = findViewById(R.id.content_container_r);
        mProgressRView = findViewById(R.id.progress_bar_r);

        inputMStudentID = (EditText) findViewById(R.id.inputMStudentID);
        inputLeaveReasons = (EditText) findViewById(R.id.inputLeaveReasons);
        inputMStuDate = (EditText) findViewById(R.id.inputMStuDate);
        inputSpinnerApproveType = (Spinner) findViewById(R.id.inputSpinnerApproveType);

        inputLayoutLeaveReasons = (TextInputLayout) findViewById(R.id.inputLayoutLeaveReasons);
        inputLayoutMStuDate = (TextInputLayout) findViewById(R.id.inputLayoutMStuDate);
        inputLayoutMStudentID = (TextInputLayout) findViewById(R.id.inputLayoutMStudentID);


        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        printLogs(LogTag,"initializeViews","exit");
    }

    @Override
    protected void initializeInputs() {
        printLogs(LogTag,"bootStrapInit","exitConnected");
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

        adapterM = new SpinnerSetAdapter(MApproveClaimFormA.this,android.R.layout.simple_spinner_item,leaveTypeMasters);
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
            Intent intent = new Intent(MApproveClaimFormA.this,AppUpdatedA.class);
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
            syncAlerts(baseApcContext, activityIn,ActivityId);

        }
    }
    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","activity_mapprove_claim_form");
        setContentView(R.layout.activity_mapprove_claim_form);
    }

    public void validateForm() {
        boolean cancel = false;
       /* if (!validateHoliName(inputHoliDaysDate, inputLayoutHolidaysDate)) {
            cancel = true;
        } else if (!validateHolidate(inputHoliDaysDate, inputLayoutHolidaysDate)) {
            cancel = true;
        }*/
        if (cancel) {
            return;
        } else {
            showProgress(true, mContentView, mProgressView);
            this.FormSubmit();
        }
        printLogs(LogTag,"validateForm","exit");
    }



    public void FormSubmit() {
     /*   final String s_id = inputMStudentID.getText().toString().trim();
        final String reasons = inputLeaveReasons.getText().toString().trim();
        final String date = inputMStuDate.getText().toString().trim();*/
        final String LeaveType = String.valueOf(selected_leave_type);
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + M_APPROVE_LEAVE;
        FINAL_URL=FINAL_URL+"/token/"+token;
        printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, FINAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject outputJson = null;
                try {
                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if (Status.equals("1")) {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = getLabelFromDb("m_153_title", R.string.m_153_title);
                        String sMessage = getLabelFromDb("m_153_message", R.string.m_153_message);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                    else {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-101";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                }
                catch (JSONException e) {
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
                })
           {

           @Override

            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
               params.put(KEY_LEAVE_TYPE ,LeaveType);

             /* params.put(KEY_Stu_ID, s_id);
                params.put(KEY_REASONS, reasons);
                params.put(KEY_LEAVE_TYPE ,LeaveType);
                params.put(KEY_DATE, date);*/


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(MApproveClaimFormA.this);
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
            Intent intent = new Intent(MApproveClaimFormA.this, MPastClaimA.class);
            printLogs(LogTag,"onOptionsItemSelected","MPastClaimA");
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
    public void onDestroy(){
        super.onDestroy();
        unregisterBroadcastIC();
    }
}