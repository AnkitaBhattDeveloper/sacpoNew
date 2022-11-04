package za.co.sacpo.grant.xCubeStudent.leaves;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

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
import za.co.sacpo.grant.xCubeLib.session.OlumsGrantSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeStudent.SDashboardDA;

public class SLConfirmationA extends BaseAPCPrivate {

    private String ActivityId="S114";
    private String KEY_NO_OF_DAYS="no_of_days";
    private String KEY_START_DATE="start_date";
    private String KEY_END_DATE="end_date";
    private String KEY_LEAVE_TYPE="leave_type";
    private String KEY_LEAVE_REASONS="leave_reason";
    SLConfirmationA thisClass;
    public LinearLayout LLFormContainer,LLInformationContainer;
    public View mProgressView, mContentView,heading;
    public Button btn_back, btn_submitLeave;
    private TextView lblView;

    TextView lbl_text_1,lbl_text_2,lbl_text_3,lbl_text_4,lbl_text_5;

    String start_date,generator,end_date,LeaveReasons,no_of_days,LeaveType,selected_leave_type_val;

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
        printLogs(LogTag,"onCreate","init");

        Bundle activeInputUri = getIntent().getExtras();
        start_date = activeInputUri.getString("start_date");
        end_date = activeInputUri.getString("end_date");
        LeaveReasons = activeInputUri.getString("LeaveReasons");
        no_of_days = activeInputUri.getString("no_of_days");
        LeaveType = activeInputUri.getString("LeaveType");
        selected_leave_type_val =activeInputUri.getString("selected_leave_type_val");
        generator = activeInputUri.getString("generator");
        printLogs(LogTag,"onCreate","GENERATOR ID "+generator+"s"+start_date+"e"+end_date+"nod"+no_of_days+"sl"+selected_leave_type_val);

        bootStrapInit();
        internetChangeBroadCast();

        fetchVersionData();
        verifyVersion();
    }

    protected void fetchVersionData(){
        printLogs(LogTag,"fetchVersionData","exitConnected");
        syncUpdates(baseApcContext,activityIn);
    }

    @Override
    protected void internetChangeBroadCast() {
        registerBroadcastIC();
    }

    protected void verifyVersion(){
        printLogs(LogTag,"verifyVersion","init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag,"verifyVersion","isUpdate "+isUpdate);
        printLogs(LogTag,"verifyVersion","isUpdate "+ utilSessionObj.getIsUpdateRequired());
        if(isUpdate){
            Intent intent = new Intent(SLConfirmationA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void bootStrapInit() {
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext,activityIn);
        if(isConnected) {
            setLayoutXml();

            callFooter(baseApcContext,activityIn,ActivityId);
            initMenusCustom(ActivityId,baseApcContext,activityIn);
            printLogs(LogTag,"bootStrapInit","initConnected");
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
            printLogs(LogTag,"initializeInputs","exitConnected");
        }
    }
    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","a_confirm_leaves");
        setContentView(R.layout.a_confirm_leaves);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        heading = findViewById(R.id.heading);

        LLFormContainer = (LinearLayout) findViewById(R.id.form_container);
        LLInformationContainer = (LinearLayout) findViewById(R.id.informationContainer);
        btn_back = findViewById(R.id.btn_back);
        btn_submitLeave = findViewById(R.id.btn_submitLeave);
        lbl_text_1=(TextView) findViewById(R.id.lbl_text_1);
        lbl_text_2=(TextView) findViewById(R.id.lbl_text_2);
        lbl_text_3=(TextView) findViewById(R.id.lbl_text_3);
        lbl_text_4=(TextView) findViewById(R.id.lbl_text_4);
        lbl_text_5=(TextView) findViewById(R.id.lbl_text_5);
    }
    @Override
    protected void initializeInputs(){
        printLogs(LogTag,"initializeInputs","init");
        showProgress(false,mContentView,mProgressView);


    }
    @Override
    protected void initializeLabels(){
        grantSessionObj = new OlumsGrantSession(baseApcContext);
        String mentor_name = grantSessionObj.getGrantMentorName();
        printLogs(LogTag,"initializeLabels","init");
        String  Label = getLabelFromDb("h_S114",R.string.h_S114);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("msg_S114_1",R.string.msg_S114_1);
        lbl_text_1.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lbl_text_1.setText(Label);
        String Label1 = getLabelFromDb("msg_S114_2",R.string.msg_S114_2_1);
        String Label2 = getLabelFromDb("msg_S114_2",R.string.msg_S114_2_2);
        String finallbl = Label1+" "+mentor_name+" " +Label2;
        lbl_text_2.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lbl_text_2.setText(finallbl);
        String sdate=  stringFromDateForCL(dateFromStringForCL(start_date));
        String edate=  stringFromDateForCL(dateFromStringForCL(end_date));
        Label = selected_leave_type_val+" : "+no_of_days+" Days, "+sdate+" to "+edate;
        lbl_text_3.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lbl_text_3.setText(Label);
        Label = getLabelFromDb("msg_S114_4",R.string.msg_S114_3);
        lbl_text_4.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lbl_text_4.setText(Label);
        Label = getLabelFromDb("msg_S114_5",R.string.msg_S114_4);
        lbl_text_5.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lbl_text_5.setText(Label);

        Label = getLabelFromDb("b_apply_leave",R.string.b_apply_leave);
        btn_submitLeave.setText(Label);

        Label = getLabelFromDb("b_back",R.string.b_back);
        btn_back.setText(Label);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            btn_submitLeave.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            btn_back.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
        }

    }
    @Override
    protected void initializeListeners() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inBundle = new Bundle();
                Intent intent = new Intent(SLConfirmationA.this, SAddLeavesA.class);
                inBundle.putString("generator", ActivityId);
                inBundle.putString("start_date", start_date);
                inBundle.putString("end_date", end_date);
                inBundle.putString("LeaveReasons", LeaveReasons);
                inBundle.putString("no_of_days", no_of_days);
                inBundle.putString("LeaveType", LeaveType);
                inBundle.putString("selected_leave_type_val", selected_leave_type_val);

                intent.putExtras(inBundle);
                startActivity(intent);
                finish();
            }
        });
        btn_submitLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FormSubmit();
            }
        });
    }
    public void  customRedirector(){
            Intent intent = new Intent(SLConfirmationA.this, SDashboardDA.class);
            startActivity(intent);
            finish();
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Bundle inputUri = new Bundle();
            Intent intent = new Intent(SLConfirmationA.this,SLeavesDA.class);
            printLogs(LogTag,"onOptionsItemSelected","SLeavesDA");
            intent.putExtras(inputUri);
            startActivity(intent);
            finish();
        }
        return true;
    }
    public void FormSubmit(){
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.R_REF_114;
      //  FINAL_URL=FINAL_URL+"/token/"+token;
        printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);
        printLogs(LogTag,"FormSubmit","INPUTS : "+start_date+"==="+no_of_days+"==="+"===="+LeaveReasons+"==="+LeaveType);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token",token);
            jsonBody.put(KEY_START_DATE,start_date);
            jsonBody.put(KEY_END_DATE,end_date);
            jsonBody.put(KEY_NO_OF_DAYS, no_of_days);
            jsonBody.put(KEY_LEAVE_TYPE ,LeaveType);
            jsonBody.put(KEY_LEAVE_REASONS, LeaveReasons);
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
                    if(Status.equals("1")){
                        printLogs(LogTag,"FormSubmit","SUCCESS : "+Status);
                        showProgress(false,mContentView,mProgressView);
                        String sTitle=getLabelFromDb("m_S113_title",R.string.m_S113_title);
                        String sMessage=getLabelFromDb("m_S113_message",R.string.m_S113_message);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                        LLFormContainer.setVisibility(View.GONE);
                        String Label = getLabelFromDb("i_leave_apply_succes",R.string.i_leave_apply_succes);
                        lblView = (TextView)findViewById(R.id.iNoActiveGrant);
                        lblView.setText(Label);
                        LLInformationContainer.setVisibility(View.VISIBLE);
                        ErrorDialog.showSuccessDialogApplyLeave(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose,SLConfirmationA.this);


                    }/*else if (Status.equals("2")) {
                        showProgress(false, mContentView, mProgressView);
                    }*/
                    else{
                        printLogs(LogTag,"FormSubmit","else");
                        showProgress(false,mContentView,mProgressView);
                        String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-100";
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false,mContentView,mProgressView);
                    String sTitle="Error :"+ActivityId+"-101";
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
                        String sTitle="Error :"+ActivityId+"-102";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> header = new HashMap<String, String>();
            header.put("Content-Type", "application/json; charset=utf-8");
            header.put("Accept","*/*");
            return header;
        }};
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
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

