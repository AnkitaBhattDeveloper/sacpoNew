package za.co.sacpo.grant.xCubeMentor.feedback;

import android.content.Context;
import android.content.Intent;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import za.co.sacpo.grant.AppUpdatedA;

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;

public class MFeedbackA extends BaseFormAPCPrivate {
    private String ActivityId="337";
    String generator,student_id,group_id,generatorP,student_name,report_id,report_number;
    String tp_value="",rw_value="";
    private final String KEY_STATUS = "s";
    private final String MESSAGE_STATUS = "m";
    private String KEY_COMMENT="comment";
    private String KEY_TRAINING_PROGRESS="training_progress";
    private String KEY_REPORT_WRITING="report_writing";
    private String KEY_REPORT_ID="report_id";
    private String KEY_STUDENT_ID="student_id";
    public EditText inputComment;
    public View mProgressView, mContentView,mProgressRView,mContentRView,heading;
    public TextInputLayout inputLayoutComment,inputLayoutPassword;
    public Button btnCommitContainer;
    private TextView lblView,lbl_training_progress,lbl_report_writing,lbl_report_number;
    MFeedbackA thisClass;
    public RadioGroup rg_optn,rg_rp_optn;
    public RadioButton rb_option_tp_1,rb_option_tp_2,rb_option_tp_3,rb_option_rp_1,rb_option_rp_2,rb_option_rp_3;
    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId){
        baseApcContext = cnt;
        CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        thisClass = this;
        printLogs(lt,"setBaseApcContextParent","weAreIn");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);
        Bundle activeInputUri = getIntent().getExtras();
        student_id = activeInputUri.getString("student_id");
        student_name = activeInputUri.getString("student_name");
        report_id = activeInputUri.getString("report_id");
        generator = activeInputUri.getString("generator");
        generatorP = activeInputUri.getString("generatorParent");
        student_name = activeInputUri.getString("student_name");
        report_number = activeInputUri.getString("report_number");
        bootStrapInit();
        printLogs(LogTag,"onCreate","initConnected");
    }
    @Override
    protected void bootStrapInit() {
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
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
            initBackBtn();
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
            Intent intent = new Intent(MFeedbackA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","m_edit_profile");
        setContentView(R.layout.a_m_supervisor_comments);
    }
    @Override
    protected void initializeViews() {
        inputComment = (EditText) findViewById(R.id.inputComment);
        inputLayoutComment = (TextInputLayout) findViewById(R.id.inputLayoutComment);

        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        heading = findViewById(R.id.heading);
        lbl_report_number = findViewById(R.id.lbl_report_number);
        rg_optn = (RadioGroup) findViewById(R.id.rg_optn);
        rb_option_tp_1 = (RadioButton) findViewById(R.id.rb_option_tp_1);
        rb_option_tp_2 = (RadioButton) findViewById(R.id.rb_option_tp_2);
        rb_option_tp_3 = (RadioButton) findViewById(R.id.rb_option_tp_3);

        rg_rp_optn = (RadioGroup) findViewById(R.id.rg_rp_optn);
        rb_option_rp_1 = (RadioButton) findViewById(R.id.rb_option_rp_1);
        rb_option_rp_2 = (RadioButton) findViewById(R.id.rb_option_rp_2);
        rb_option_rp_3 = (RadioButton) findViewById(R.id.rb_option_rp_3);
        lbl_report_writing = findViewById(R.id.lbl_report_writing);
        lbl_training_progress = findViewById(R.id.lbl_training_progress);
        
        btnCommitContainer = (Button) findViewById(R.id.btnCommitContainer);
    }
    @Override
    protected void initializeLabels(){
        String Label = getLabelFromDb("l_337_comments",R.string.l_337_comments);
        lblView = (TextView)findViewById(R.id.lblComment);
        lblView.setText(Label);

        Label = getLabelFromDb("l_337_btn_comments",R.string.l_337_btn_comments);
        lblView = (TextView)findViewById(R.id.btnCommitContainer);
        lblView.setText(Label);

        Label = getLabelFromDb("l_337_report_writing",R.string.l_337_report_writing);
        lbl_report_writing.setText(Label);
        Label = getLabelFromDb("l_337_training_progress",R.string.l_337_training_progress);
        lbl_training_progress.setText(Label);

        Label = getLabelFromDb("rb_337_rp_1",R.string.rb_337_rp_1);
        rb_option_rp_1.setText(Label);
        Label = getLabelFromDb("rb_337_rp_2",R.string.rb_337_rp_2);
        rb_option_rp_2.setText(Label);
        Label = getLabelFromDb("rb_337_rp_3",R.string.rb_337_rp_3);
        rb_option_rp_3.setText(Label);

        Label = getLabelFromDb("rb_337_tp_1",R.string.rb_337_tp_1);
        rb_option_tp_1.setText(Label);
        Label = getLabelFromDb("rb_337_tp_2",R.string.rb_337_tp_2);
        rb_option_tp_2.setText(Label);
        Label = getLabelFromDb("rb_337_tp_3",R.string.rb_337_tp_3);
        rb_option_tp_3.setText(Label);


        Label = getLabelFromDb("rb_337_rp_1_tag",R.string.rb_337_rp_1_tag);
        rb_option_rp_1.setTag(Label);
        Label = getLabelFromDb("rb_337_rp_2_tag",R.string.rb_337_rp_2_tag);
        rb_option_rp_2.setTag(Label);
        Label = getLabelFromDb("rb_337_rp_3_tag",R.string.rb_337_rp_3_tag);
        rb_option_rp_3.setTag(Label);

        Label = getLabelFromDb("rb_337_tp_1_tag",R.string.rb_337_tp_1_tag);
        rb_option_tp_1.setTag(Label);
        Label = getLabelFromDb("rb_337_tp_2_tag",R.string.rb_337_tp_2_tag);
        rb_option_tp_2.setTag(Label);
        Label = getLabelFromDb("rb_337_tp_3_tag",R.string.rb_337_tp_3_tag);
        rb_option_tp_3.setTag(Label);


        Label = getLabelFromDb("h_337",R.string.h_337);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            btnCommitContainer.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            inputComment.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
        }

        }
    @Override
    protected void initializeInputs(){
        lbl_report_number.setText(report_number);
        printLogs(LogTag,"initializeInputs","init");
    }
    @Override
    protected void initializeListeners() {
        rg_optn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbbutton=(RadioButton)group.findViewById(checkedId);
                tp_value = rbbutton.getTag().toString();
            }
        });
        rg_rp_optn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbbutton=(RadioButton)group.findViewById(checkedId);
                rw_value = rbbutton.getTag().toString();
            }
        });
        btnCommitContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });
    }
    public void validateForm() {
        boolean cancel = false;
        if(tp_value.isEmpty()){
            String sMessage = getLabelFromDb("error_337_validate_101", R.string.error_337_validate_101);
            rb_option_tp_3.setError(sMessage);
            cancel=true;
        }
        else if(rw_value.isEmpty()){
            String sMessage = getLabelFromDb("error_337_validate_102", R.string.error_337_validate_102);
            rb_option_rp_3.setError(sMessage);
            cancel=true;
        }
        if (!validateComment(inputComment, inputLayoutComment)) {
            cancel = true;
        }
        printLogs(LogTag,"validateForm","TP-"+tp_value+"--RW"+rw_value+"com--"+inputComment.getText().toString().trim());
        //cancel = true;
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
            syncMentorData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn,ActivityId);
        }
    }
    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private boolean validateComment(EditText inputComment, TextInputLayout inputLayoutComment) {
        String comment = inputComment.getText().toString().trim();
        setCustomError(inputLayoutComment,null,inputComment);
        //if (comment.isEmpty() || isValidMessageComment(comment)) {
        if (!comment.isEmpty()){
            if(isValidMessageComment(comment)) {
                String sMessage = getLabelFromDb("error_337_104", R.string.error_337_104);
                setCustomError(inputLayoutComment, sMessage, inputComment);
                return false;
            }
            else {
                setCustomErrorDisabled(inputLayoutComment,inputComment);
                return true;
            }
        } else {
            setCustomErrorDisabled(inputLayoutComment,inputComment);
            return true;
        }
    }

    public void FormSubmit(){
        final String comment = inputComment.getText().toString().trim();
        final String tp_value_in = tp_value;
        final String rw_value_in = rw_value;
        final String student_id_in = student_id;
        final String report_id_in = report_id;
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.REF_M_337;
        String token = userSessionObj.getToken();
        printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);
        JSONObject object = new JSONObject();
        try {
            object.put("token",token);
            object.put(KEY_COMMENT, comment);
            object.put(KEY_TRAINING_PROGRESS, tp_value_in);
            object.put(KEY_REPORT_WRITING, rw_value_in);
            object.put(KEY_STUDENT_ID, student_id_in);
            object.put(KEY_REPORT_ID, report_id_in);
            object.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, FINAL_URL, object, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "FormSubmit", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        showProgress(false,mContentView,mProgressView);
                        String sTitle=getLabelFromDb("m_337_title",R.string.m_337_title);
                        String sMessage =getLabelFromDb("m_337_message_1",R.string.m_337_message_1);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showSuccessDialogMFeedback(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose,thisClass);
                    }
                    else{
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-101";
                        String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
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
                        String sTitle="Error :"+ActivityId+"-103";
                        String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
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

    public void  customRedirector(){

            Bundle inputUri = new Bundle();
            inputUri.putString("student_id", student_id);
            inputUri.putString("generator", generatorP);
            inputUri.putString("student_name", student_name);
            Intent intent = new Intent(MFeedbackA.this,MStudentReports.class);
            intent.putExtras(inputUri);
            printLogs(LogTag,"onOptionsItemSelected","MStudentReports");
            startActivity(intent);
            finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Bundle inputUri = new Bundle();
            inputUri.putString("student_id", student_id);
            inputUri.putString("generator", generatorP);
            inputUri.putString("student_name", student_name);
            Intent intent = new Intent(MFeedbackA.this,MStudentReports.class);
            intent.putExtras(inputUri);
            printLogs(LogTag,"onOptionsItemSelected","MStudentReports");
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