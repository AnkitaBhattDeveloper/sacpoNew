package za.co.sacpo.grant.xCubeMentor.claims;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

import java.util.HashMap;
import java.util.Map;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeMentor.MDashboardDA;

public class MApproveA extends BaseFormAPCPrivate {

    private String ActivityId = "188";

    private String KEY_LEAVE_TYPE = "";
    private String KEY_ID= "stipend_id";

    MApproveA thisClass;
    private String Is_Att ="is_att_approve";

    public View mProgressView, mContentView;

    public Button btnSubmit;
    public View mProgressRView, mContentRView;

    private TextView lblView,lblapproveType;
    private LinearLayout submitContainer;

    String generator,m_student_name,student_id,stipend_id;
    private CheckBox checkbox;

    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId) {
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
        Bundle activeInputUri = getIntent().getExtras();

        m_student_name=null;
        stipend_id = activeInputUri.getString("stipend_id");
        printLogs(LogTag,"onCreate","stipend_id "+stipend_id);



   //   if (activeInputUri.hasExtra("m_student_name")) {
            m_student_name = activeInputUri.getString("m_student_name");
            printLogs(LogTag,"onCreate","m_student_name"+m_student_name);

        student_id = activeInputUri.getString("student_id");
            printLogs(LogTag,"onCreate","student_id"+student_id);

        generator = activeInputUri.getString("generator");
        printLogs(LogTag,"onCreate","generator "+generator);




        bootStrapInit();


    }
    @Override
    protected void bootStrapInit() {
        printLogs(LogTag,"onCreate","bootStrapInitiiiiiiiiii "+stipend_id);
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext, activityIn);
        if (isConnected == true) {
            syncToken(baseApcContext, activityIn);
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);
            printLogs(LogTag,"onCreate","callFooter "+stipend_id);
            initMenusCustom(ActivityId,baseApcContext,activityIn);

//            showProgress(true,mContentView,mProgressView);
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
        String Label = getLabelFromDb("l_188_approve_type", R.string.l_188_approve_type);
        lblView = (TextView) findViewById(R.id.lblapproveType);
        lblView.setText(Label);
        Label = getLabelFromDb("txt_188_approveType", R.string.txt_188_approveType);
        lblView = (TextView) findViewById(R.id.lblapproveType);
        lblView.setText(Label);
        Label = getLabelFromDb("h_188",R.string.h_188);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);
        Label = getLabelFromDb("l_188_btn_submit", R.string.l_188_btn_submit);
        lblView = (TextView) findViewById(R.id.btnSubmit);
        btnSubmit.setText(Label);


    }


    @Override
    protected void initializeViews() {
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mContentRView = findViewById(R.id.content_container_r);
        mProgressRView = findViewById(R.id.progress_bar_r);

        lblapproveType = (TextView)findViewById(R.id.lblapproveType);
        submitContainer = (LinearLayout) findViewById(R.id.buttonContainer);
        checkbox = (CheckBox) findViewById(R.id.checkbox);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        printLogs(LogTag,"initializeViews","exit");
    }

    @Override
    protected void initializeInputs() {
        printLogs(LogTag, "bootStrapInit", "exitConnected");
        showProgress(false, mContentView, mProgressView);
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkbox.isChecked() ==true){

                    submitContainer.setVisibility(View.VISIBLE);

                }else {
                    submitContainer.setVisibility(View.GONE);
                }
            }
        });

        printLogs(LogTag, "initializeInputs", "init");
        dbSetaObj = DbHelper.getInstance(this);


    }
    @Override
    protected void initializeListeners() {

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Is_Att.equals("0")){

                    String sTitle="Error :"+ActivityId+"-108";
                    String sMessage=getLabelFromDb("error_188_100",R.string.error_188_100);
                    String sButtonLabelClose="Close";
                    ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

                    // Toast.makeText(baseApcContext, "Please Approved Attendance Register Before Approving Claim", Toast.LENGTH_SHORT).show();
                }else{
                    FormSubmit();
                }



            }
        });

    }

    public void FormSubmit() {
            String token = userSessionObj.getToken();
            String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.MENTOR_APPROVE_CLAIM;
            FINAL_URL=FINAL_URL+"/token/"+token+"/stipend_id/"+stipend_id;
            //+"/student_id/"+student_id+"/stipend_id/"+stipend_id;

        printLogs(LogTag,"initializeInputs","stipend_id"+stipend_id);

            printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);
            StringRequest stringRequest = new StringRequest(Request.Method.PUT,FINAL_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONObject outputJson = null;
                    try {
                        outputJson = new JSONObject(response);
                        String Status = outputJson.getString(KEY_STATUS);
                        if(Status.equals("1")){
                            showProgress(false,mContentView,mProgressView);
                            String sTitle=getLabelFromDb("m_188_title",R.string.m_188_title);
                            String sMessage=getLabelFromDb("m_188_message",R.string.m_188_message);
                            String sButtonLabelClose="Close";
                            ErrorDialog.showSuccessDialogMApproveA(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose,thisClass);
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
                    })
            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(KEY_ID, stipend_id);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(MApproveA.this);
              stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);

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
            Intent intent = new Intent(MApproveA.this,AppUpdatedA.class);
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
        printLogs(LogTag,"setLayoutXml","a_approve");
        setContentView(R.layout.a_approve);
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


    private void next() {
        Intent intent = new Intent(MApproveA.this, MPastClaimA.class);
        startActivity(intent);
        finish();
    }

    public void  customRedirector(){
        Intent intent = new Intent(MApproveA.this, MDashboardDA.class);
        printLogs(LogTag, "onOptionsItemSelected", "MDashboardDA");
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

      /*  printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {

            Intent intent = new Intent(MApproveA.this,MPastClaimA.class);
            Bundle inputUri = new Bundle();
            inputUri.putString("generator", "205");
            inputUri.putString("stipend_id", stipend_id);
           // inputUri.putString("m_student_name", m_student_name);
            printLogs(LogTag,"onOptionsItemSelected","m_student_name"+m_student_name);
            intent.putExtras(inputUri);
            printLogs(LogTag,"onOptionsIt`emSelected","onOptionsItemSelected"+stipend_id);
            startActivity(intent);
            finish();

        }
        return true;*/

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Bundle inputUri = new Bundle();
           // inputUri.putString("student_id", stipend_id);
            inputUri.putString("student_id", student_id);
            printLogs(LogTag,"onOptionsItemSelected","back_studentIDDDDD00"+student_id);
            inputUri.putString("m_student_name", m_student_name);
            inputUri.putString("generator", "312");
            printLogs(LogTag,"onOptionsItemSelected","MPastClaimA");
            Intent intent = new Intent(MApproveA.this, MPastClaimA.class);
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
    public void onDestroy(){
        super.onDestroy();
        unregisterBroadcastIC();
    }
}