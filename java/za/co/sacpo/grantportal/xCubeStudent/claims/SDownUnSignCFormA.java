package za.co.sacpo.grantportal.xCubeStudent.claims;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import java.util.Timer;
import java.util.TimerTask;

import za.co.sacpo.grantportal.AppUpdatedA;
import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.asyncCalls.CheckFile;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grantportal.xCubeLib.dataObj.FormsObj;
import za.co.sacpo.grantportal.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.component.Utils;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;

/* private String ActivityId="128";*/
/*a_download_un_sign_claim_form*/
public class SDownUnSignCFormA extends BaseAPCPrivate {
    private String ActivityId="128";
    private String KEY_U_TYPE_ID="user_type_id";

    public View mProgressView, mContentView;
    public Button btnDownloadForm;
    public  TextView lblButton, lblInstruction, Text_Instruction;
    private TextView lblView;
    String stipend_id;
    String generator,data_input,apiUrl;
    private CheckFile mCheckFileTask = null;
    // File url to download
    // private static String file_url = "http://seta.local/uploads/forms/Seta_2/Student/25_student_2018_09_01.pdf";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);

        Bundle activeInputUri = getIntent().getExtras();

        stipend_id = activeInputUri.getString("stipend_id");
        data_input = activeInputUri.getString("claPDate15");
        generator = activeInputUri.getString("generator");
        printLogs(LogTag,"onCreate","Date Input "+data_input);
        printLogs(LogTag,"onCreate","Date Input "+data_input);
        printLogs(LogTag,"onCreate","SSSSSSSSSSSSSSID"+stipend_id);
        bootStrapInit();
        initBackBtn();
 //        initDrawer();
        callDataApi();
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
        if(isUpdate==true){
            Intent intent = new Intent(SDownUnSignCFormA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
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
//            showProgress(true,mContentView,mProgressView);

            initializeViews();
            initializeInputs();
            initializeLabels();
            initializeListeners();

        }
    }


    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId){
        baseApcContext = cnt;CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
    }
    @Override
    protected void initializeInputs(){

        if(stipend_id!=null)
        {

            downloadClaimForm();
           // generateClaimForm();
        }


    }
    public void checkClaimForm(){
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask backGroundTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            mCheckFileTask= new CheckFile(baseApcContext,apiUrl,ActivityId,stipend_id);
                            mCheckFileTask.execute((Void) null);
                            enableDownloadButton();
                        }
                        catch (Exception e) { }
                    }
                });
            }
        };
        timer.schedule(backGroundTask , 0, 100000);
    }
    public void enableDownloadButton(){
        FormsObj frmObj = utilSessionObj.getFormsdata();

        String formId = ActivityId+"_"+stipend_id;
        String formURL = frmObj.getFormURLByID(formId);
        if(!formURL.equals("")){
            data_input =formURL;
            btnDownloadForm.setVisibility(View.VISIBLE);
        }
    }
    @Override
    protected void initializeLabels(){


        String  Label = getLabelFromDb("b_128_download_form",R.string.b_128_download_form);
        lblButton.setText(Label);

        Label = getLabelFromDb("l_128_btn",R.string.l_128_btn);
        btnDownloadForm.setText(Label);

        Label = getLabelFromDb("b_128_lbl_form_instruction", R.string.b_128_lbl_form_instruction);
        lblInstruction.setText(Label);

        Label = getLabelFromDb("l_128_btn", R.string.l_128_btn);
        lblButton.setText(Label);

        Label = getLabelFromDb("h_128",R.string.h_128);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);

    }
    @Override
    protected void initializeViews() {

        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        btnDownloadForm = (Button) findViewById(R.id.btnDownloadForm);
        lblButton = (TextView) findViewById(R.id.lblButton);
        lblInstruction = (TextView) findViewById(R.id.lblInstruction);
        Text_Instruction = (TextView) findViewById(R.id.Text_Instruction);

    }
    @Override
    protected void initializeListeners() {
        btnDownloadForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                down();


            }
        });
    }
    private void down(){


        lblButton.setMovementMethod(LinkMovementMethod.getInstance());
        String url = lblButton.getText().toString();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }



    @Override
    protected void setLayoutXml() {
        setContentView(R.layout.a_download_un_sign_claim_form);
    }


    public void callDataApi(){
        printLogs(LogTag,"callDataApi","init");
        userToken = userSessionObj.getToken();
        if(!TextUtils.isEmpty(userToken)) {
            printLogs(LogTag,"callDataApi","Token Update");
            syncUserData(baseApcContext, activityIn);
            syncStudentData(baseApcContext, activityIn);
            syncGrantData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn, ActivityId);
        }
    }

    private void downloadClaimForm() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.STUDENT_DOWNLOADSTUDENTFORM;
        FINAL_URL=FINAL_URL+"/token/"+token+"/stipend_id/"+stipend_id;
        printLogs(LogTag,"downloadClaimForm","URL : "+FINAL_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,FINAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject outputJson = null;
                printLogs(LogTag,"downloadClaimForm","RESPONSE : "+response);
                try {
                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONObject dataM = outputJson.getJSONObject(KEY_DATA);
                        if(dataM.length()>0) {
                          //  data_input = dataM.getString("student_form");
                            lblButton.setText(dataM.getString("student_form"));
                            btnDownloadForm.setVisibility(View.VISIBLE);
                            showProgress(false, mContentView, mProgressView);
                        }
                        else{
                            btnDownloadForm.setVisibility(View.GONE);
                            showProgress(false,mContentView,mProgressView);
                            String sTitle="Error :"+ActivityId+"-100";
                            String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                            String sButtonLabelClose="Close";
                            ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                        }
                    }
                    else if(Status.equals("2")){
                        btnDownloadForm.setVisibility(View.GONE);
                        showProgress(false,mContentView,mProgressView);
                    }
                    else{
                        btnDownloadForm.setVisibility(View.GONE);
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-101";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    btnDownloadForm.setVisibility(View.GONE);
                    printLogs(LogTag,"getStipendDetails","error_try_again : "+e.getMessage());
                    e.printStackTrace();
                    showProgress(false,mContentView,mProgressView);
                    String sTitle="Error :"+ActivityId+"-102";
                    String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                    String sButtonLabelClose="Close";
                    ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        printLogs(LogTag,"downloadClaimForm","error_try_again : "+error.getMessage());
                        printLogs(LogTag,"downloadClaimForm","error_try_again : "+error.getLocalizedMessage());
                        //showProgress(false,mContentView,mProgressView);

                        printLogs(LogTag,"downloadClaimForm","error_try_again : ");
                        String sTitle="Error :"+ActivityId+"-103";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

                    }
                })
        {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(SDownUnSignCFormA.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

  /*  private void generateClaimForm() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.STUDENT_GENERATEDOWNLOADSTUDENTFORM;
        FINAL_URL=FINAL_URL+"/token/"+token+"/stipend_id/"+stipend_id;
        printLogs(LogTag,"generateClaimForm","URL : "+FINAL_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,FINAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject outputJson = null;
                printLogs(LogTag,"generateClaimForm","RESPONSE : "+response);
                try {
                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONObject dataM = outputJson.getJSONObject(KEY_DATA);
                        if(dataM.length()>0) {

                            lblButton.setText(dataM.getString("student_form"));
                            //String info=dataM.getString("form_instruction");
                            //data_input = dataM.getString("student_form");
                            //btnDownloadForm.setVisibility(View.VISIBLE);
                            showProgress(false, mContentView, mProgressView);
                        }
                        else{
                            btnDownloadForm.setVisibility(View.GONE);
                            showProgress(false,mContentView,mProgressView);
                            String sTitle="Error :"+ActivityId+"-100";
                            String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                            String sButtonLabelClose="Close";
                            ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                        }
                    }
                    else if(Status.equals("2")){
                        btnDownloadForm.setVisibility(View.GONE);
                        showProgress(false,mContentView,mProgressView);
                    }
                    else{
                       // btnDownloadForm.setVisibility(View.GONE);
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-101";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    btnDownloadForm.setVisibility(View.GONE);
                    printLogs(LogTag,"generateClaimForm","error_try_again : "+e.getMessage());
                    e.printStackTrace();
                    showProgress(false,mContentView,mProgressView);
                    String sTitle="Error :"+ActivityId+"-102";
                    String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                    String sButtonLabelClose="Close";
                    ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        printLogs(LogTag,"generateClaimForm","error_try_again : "+error.getMessage());
                        printLogs(LogTag,"generateClaimForm","error_try_again : "+error.getLocalizedMessage());
                        //showProgress(false,mContentView,mProgressView);

                        printLogs(LogTag,"generateClaimForm","error_try_again : ");
                        String sTitle="Error :"+ActivityId+"-103";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

                    }
                })
        {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(SDownUnSignCFormA.this);
        requestQueue.add(stringRequest);





    }*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(SDownUnSignCFormA.this, SPastClaimDA.class);
            printLogs(LogTag,"onOptionsItemSelected","sDashboard");
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
