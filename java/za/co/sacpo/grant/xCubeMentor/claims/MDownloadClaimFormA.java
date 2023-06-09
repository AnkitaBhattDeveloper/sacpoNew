package za.co.sacpo.grant.xCubeMentor.claims;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
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

import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;

import za.co.sacpo.grant.xCubeStudent.SDashboardDA;

public class MDownloadClaimFormA extends BaseAPCPrivate {

    private String ActivityId = "203";
    private String KEY_U_TYPE_ID="user_type_id";

    public View mProgressView, mContentView;
    public Button btnDownloadForm;

    private TextView lblView,lblButton;

    public  TextView lblInstruction,Text_Instruction;
    String generator,m_student_name,student_id,stipend_id;
    String form_id;
    String date_input;

    TextView textView;
    private static String file_url = "http://seta.local/uploads/forms/Seta_2/Student/25_student_2018_09_01.pdf";

    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId) {
        baseApcContext = cnt;CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        CAId = CAId;
        printLogs(lt,"setBaseApcContextParent","weAreIn");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);

        Bundle activeInputUri = getIntent().getExtras();

        generator = activeInputUri.getString("generator");
        student_id = activeInputUri.getString("student_id");
        m_student_name = activeInputUri.getString("m_student_name");
        stipend_id = activeInputUri.getString("stipend_id");
        printLogs(LogTag,"onCreate","GENERATOR ID "+generator);
        printLogs(LogTag,"onCreate","student_id"+student_id);
        printLogs(LogTag,"onCreate","m_student_name"+m_student_name);
        printLogs(LogTag,"onCreate","stipend_id"+stipend_id);


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

            internetChangeBroadCast();
            fetchVersionData();
            verifyVersion();
            initBackBtn();
            initializeViews();
            initializeInputs();
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
        }
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
            Intent intent = new Intent(MDownloadClaimFormA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    public void callDataApi(){
        userToken = userSessionObj.getToken();
        if(!TextUtils.isEmpty(userToken)) {
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
        protected void initializeInputs(){
        if(stipend_id!=null)
        {  getFormDetails();
        }

    }
    @Override
    protected void initializeLabels(){

   /*     String  Label = getLabelFromDb("b_203_download_form",R.string.b_203_download_form);
        lblButton.setText(Label);*/
        String  Label = getLabelFromDb("l_203_btn",R.string.l_203_btn);
        btnDownloadForm.setText(Label);

        Label = getLabelFromDb("b_203_lbl_form_instruction", R.string.b_203_lbl_form_instruction);
        lblView = (TextView)findViewById(R.id.lblInstruction);
        lblView.setText(Label);

        Label = getLabelFromDb("h_203",R.string.h_203);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);
    }
    @Override
    protected void initializeViews() {

        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        btnDownloadForm = (Button) findViewById(R.id.btnDownloadForm);
        lblButton = (TextView) findViewById(R.id.lblButton);
        Text_Instruction = (TextView) findViewById(R.id.Text_Instruction);
    }
    @Override
    protected void initializeListeners() {
        btnDownloadForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lblButton.setVisibility(View.GONE);
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
        setContentView(R.layout.a_mdownload_claim_form);
    }
    private void getFormDetails() {

        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.MSTUDENT_DOWNLOAD_UNSIGNED_CLAIM_FORM;
        FINAL_URL=FINAL_URL+"?token="+token+"&stipend_id="+stipend_id;
        printLogs(LogTag,"getFormDetails","URL : "+FINAL_URL);
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
                        if(dataM.length()>0) {

                           // String info=dataM.getString("student_form");
                            lblButton.setText(dataM.getString("student_form"));
                            /* GET INSTRUCTIONS FROM API !!-->*/

                           /* String info=dataM.getString("form_instruction");
                            //Text_Instruction.setText(Html.fromHtml(info, Html.FROM_HTML_MODE_COMPACT));

                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                Text_Instruction.setText(Html.fromHtml(info,Html.FROM_HTML_MODE_LEGACY));
                            } else {
                                Text_Instruction.setText(Html.fromHtml(info));
                            }
*/
                            showProgress(false, mContentView, mProgressView);
                        }

                        else if(Status.equals("2")) {
                            showProgress(false,mContentView,mProgressView);
                        }

                        else{
                            //showProgress(false,mContentView,mProgressView);
                            String sTitle="Error :"+ActivityId+"-100";
                            String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                            String sButtonLabelClose="Close";
                            ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                        }
                    }
                    else{
                        //showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-101";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    printLogs(LogTag,"getStipendDetails","error_try_again : "+e.getMessage());
                    e.printStackTrace();
                    //showProgress(false,mContentView,mProgressView);
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
                        printLogs(LogTag,"getAttendanceDetails","error_try_again : "+error.getMessage());
                        //showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-103";
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

        public void FormSubmit(){
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.DOWNLOADUNSIGNEDFORM_URL;
        StringRequest stringRequest = new StringRequest(Request.Method.POST,FINAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject outputJson = null;
                try {
                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONObject dataM = outputJson.getJSONObject(KEY_DATA);

                        //String token = FirebaseInstanceId.getInstance().getToken();
                        //userSessionObj.setHasSession(true);
                        showProgress(false,mContentView,mProgressView);

                        Intent intent = new Intent(MDownloadClaimFormA.this,SDashboardDA.class);
                        startActivity(intent);
                        finish();

                    }
                    else{
                        showProgress(false,mContentView,mProgressView);
                        String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false,mContentView,mProgressView);
                    String sTitle="Error :"+ActivityId+"-101";
                    String sMessage = getLabelFromDb("error_203_101",R.string.error_try_again);
                    String sButtonLabelClose="Close";
                    ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String sTitle="Error :"+ActivityId+"-102";
                        String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put(KEY_U_TYPE_ID, "2");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MDownloadClaimFormA.this);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        printLogs(LogTag,"onOptionsItemSelected","init");

        if(generator.equals("126")){
            Intent intent = new Intent(MDownloadClaimFormA.this, MPastClaimA.class);
            Bundle inputUri = new Bundle();
            inputUri.putString("generator", "313");
            inputUri.putString("m_student_name", m_student_name);
            inputUri.putString("student_id", student_id);
            inputUri.putString("stipend_id", stipend_id);
            intent.putExtras(inputUri);
            printLogs(LogTag,"onOptionsItemSelected","MPastClaimA");
            startActivity(intent);
            finish();
        }else if(generator.equals("340")){

            Intent intent = new Intent(MDownloadClaimFormA.this, MPendingClaimsA.class);
            Bundle inputUri = new Bundle();
            inputUri.putString("generator", ActivityId);
            intent.putExtras(inputUri);
            printLogs(LogTag,"onOptionsItemSelected","MPendingClaimsA");
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
