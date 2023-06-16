package za.co.sacpo.grantportal.xCubeStudent.feedback;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
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

import za.co.sacpo.grantportal.AppUpdatedA;
import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.component.Utils;
import za.co.sacpo.grantportal.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grantportal.xCubeLib.spinners.SpinnerSet;
import za.co.sacpo.grantportal.xCubeLib.spinners.SpinnerSetAdapter;
import za.co.sacpo.grantportal.xCubeStudent.SDashboardDA;

import static za.co.sacpo.grantportal.xCubeLib.component.URLHelper.S_REMOVE_FEEDBACK;


/*a_s_remove_feedback*/
public class SRemoveFeedbackA extends BaseFormAPCPrivate {

    private String ActivityId = "185";

    private String KEY_STUDENT_ID = "s_w_r_id";


    public EditText inputHoliDaysName, inputHoliDaysDate,inputApprove;
    public View mProgressView, mContentView;

    public Button btnSubmit;
    public View mProgressRView, mContentRView;
    public EditText inputMStudentID, inputLeaveReasons, inputMStuDate;
    private TextView txt_date,txt_feedback,txt_message;
    public Button btn_yes,btn_no;

    //   public TextInputLayout inputLayoutMStudentID, inputLayoutLeaveReasons, inputLayoutMStuDate;
    private TextView lblView;
    String generator,date;

    int selected_leave_type=0;
    Spinner inputSpinnerApproveType;
    private SpinnerSetAdapter adapterM;

    private static SpinnerSet[] leaveTypeMasters;
    String student_id,grant_id,s_w_r_id,feedback,date_input;

    SRemoveFeedbackA   thisClass;
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
        printLogs(LogTag,"onCreate","init");
        Bundle activeInputUri = getIntent().getExtras();
        generator = activeInputUri.getString("generator");
      /*  date = activeInputUri.getString("date");
        student_id = activeInputUri.getString("student_id");
        grant_id = activeInputUri.getString("grant_id");*/
        s_w_r_id = activeInputUri.getString("s_w_r_id");
        date = activeInputUri.getString("date");
        feedback = activeInputUri.getString("feedback");
        generator = activeInputUri.getString("generator");
        date_input = activeInputUri.getString("date_input");
        //   printLogs(LogTag,"onCreate","GRANT ID "+grant_id);
        printLogs(LogTag,"onCreate","FEEDBACK ID "+s_w_r_id);
        printLogs(LogTag,"onCreate","DATE"+date);
        printLogs(LogTag,"onCreate","feedback"+feedback);
        printLogs(LogTag,"onCreate","GENERATOR ID "+generator);
        printLogs(LogTag,"onCreate","date_input "+date_input);

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

            //  showProgress(true,mContentView,mProgressView);
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


        String   Label = getLabelFromDb("h_185",R.string.h_185);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);
       /* Label = getLabelFromDb("l_185_btn_save", R.string.l_185_btn_save);
        lblView = (TextView) findViewById(R.id.btnSubmit);
        btnSubmit.setText(Label);*/
        Label = getLabelFromDb("m_185_txt_i", R.string.m_185_txt_i);
        lblView = (TextView) findViewById(R.id.txt_message);
        txt_message.setText(Label);

        Label = getLabelFromDb("l_185_btn_save", R.string.l_185_btn_yes);
        lblView = (TextView) findViewById(R.id.btn_yes);
        btn_yes.setText(Label);

        Label = getLabelFromDb("l_185_btn_no", R.string.l_185_btn_no);
        lblView = (Button) findViewById(R.id.btn_no);
        btn_no.setText(Label);

    }


    @Override
    protected void initializeViews() {
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mContentRView = findViewById(R.id.content_container_r);
        mProgressRView = findViewById(R.id.progress_bar_r);

        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        btn_no = (Button) findViewById(R.id.btn_no);
        btn_yes = (Button) findViewById(R.id.btn_yes);

        txt_feedback = (TextView) findViewById(R.id.txt_feedback);
        txt_date = (TextView) findViewById(R.id.txt_date);
        txt_message = (TextView) findViewById(R.id.txt_message);


        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        printLogs(LogTag,"initializeViews","exit");
         //txt_date.setText(date);
         txt_feedback.setText(feedback);
       }

    @Override
    protected void initializeInputs() {
        printLogs(LogTag,"bootStrapInit","exitConnected");
        showProgress(false,mContentView,mProgressView);

        printLogs(LogTag,"initializeInputs","init");

    }
    @Override
    protected void initializeListeners() {

        printLogs(LogTag,"initializeListeners","init");


        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle inputUri = new Bundle();
                inputUri.putString("date_input", date_input);
                Intent intent = new Intent(SRemoveFeedbackA.this, SFeedbackDA.class);
                printLogs(LogTag, "onOptionsItemSelected", "SFeedbackDA");
                intent.putExtras(inputUri);
                startActivity(intent);
                finish();
                finish();
            }
        });
    }


/*
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });*//*

    }
*/



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
            Intent intent = new Intent(SRemoveFeedbackA.this,AppUpdatedA.class);
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
            syncStudentData(baseApcContext, activityIn);
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
        setContentView(R.layout.a_remove_feedback);
    }

    public void validateForm() {
        boolean cancel = false;

        if (cancel) {
            return;
        } else {
            showProgress(true, mContentView, mProgressView);
            this.FormSubmit();
        }
        printLogs(LogTag,"validateForm","exit");
    }



    public void FormSubmit() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + S_REMOVE_FEEDBACK;
        printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put("s_w_r_id", s_w_r_id);
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
                        String sTitle = getLabelFromDb("m_185_title", R.string.m_185_title);
                        String sMessage = getLabelFromDb("m_185_message", R.string.m_185_message);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showSuccessDialogRemoveFeedback(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose,thisClass);
                      //  customRedirector();
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
                }){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/json; charset=utf-8");
                header.put("Accept","*/*");
                return header;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(SRemoveFeedbackA.this);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    public void  customRedirector(){
        if(generator.equals("174")) {
            Intent intent = new Intent(SRemoveFeedbackA.this, SDashboardDA.class);
            Bundle activeUri = new Bundle();
            activeUri.putString("generator", "174");
            activeUri.putString("date_input", date_input);
            intent.putExtras(activeUri);
            startActivity(intent);
            finish();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            if(generator.equals("174")){
                Intent intent = new Intent(SRemoveFeedbackA.this,SFeedbackDA.class);
                printLogs(LogTag,"onOptionsItemSelected","sDashboard");
                Bundle activeUri = new Bundle();
                activeUri.putString("generator", "174");
                activeUri.putString("date_input", date_input);
                intent.putExtras(activeUri);
                startActivity(intent);
                finish();
            }

            else {
                printLogs(LogTag,"onOptionsItemSelected","default");
                super.onOptionsItemSelected(item);
            }
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
