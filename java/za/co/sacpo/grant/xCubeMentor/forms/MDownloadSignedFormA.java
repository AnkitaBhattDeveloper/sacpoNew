package za.co.sacpo.grant.xCubeMentor.forms;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeMentor.claims.MPendingClaimsA;
import za.co.sacpo.grant.xCubeMentor.claims.MPastClaimA;

/*a_mdownload_signed_form*/
public class MDownloadSignedFormA extends BaseAPCPrivate {
    private String ActivityId = "146";
    private String KEY_U_TYPE_ID = "user_type_id";
    public EditText mEmailView, mPasswordView;
    public View mProgressView, mContentView;
    public TextView lblButton, lblInstruction, Text_Instruction;
    public Button btnDownloadMentorSignedForm;
    private TextView lblView;
    String Form_id;
    private static String file_url = "http://seta.local/uploads/forms/Seta_2/Student/25_student_2018_09_01.pdf";
    String generator,m_student_name,student_id,stipend_id;

    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId) {
        baseApcContext = cnt;
        CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(), this, this.getClass().getSimpleName(),ActivityId);

        Bundle activeInputUri = getIntent().getExtras();
        generator = activeInputUri.getString("generator");
        student_id = activeInputUri.getString("student_id");
        m_student_name = activeInputUri.getString("m_student_name");
        stipend_id = activeInputUri.getString("stipend_id");
        printLogs(LogTag,"onCreate","GENERATOR ID "+generator);
        printLogs(LogTag,"onCreate","student_id"+student_id);
        printLogs(LogTag,"onCreate","m_student_name"+m_student_name);
        printLogs(LogTag,"onCreate","stipend_id"+stipend_id);

        initBackBtn();
        bootStrapInit();
        //   initDrawer();
        internetChangeBroadCast();
        fetchVersionData();
        verifyVersion();
    }



    protected void fetchVersionData() {
        printLogs(LogTag, "fetchVersionData", "exitConnected");
        syncUpdates(baseApcContext, activityIn);
    }

    @Override
    protected void internetChangeBroadCast() {
        registerBroadcastIC();
    }

    protected void verifyVersion() {
        printLogs(LogTag, "verifyVersion", "init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag, "verifyVersion", "isUpdate " + isUpdate);
        printLogs(LogTag, "verifyVersion", "isUpdate " + utilSessionObj.getIsUpdateRequired());
        if (isUpdate == true) {
            Intent intent = new Intent(MDownloadSignedFormA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void bootStrapInit() {
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext, activityIn);
        if (isConnected) {
            syncToken(baseApcContext, activityIn);
            setLayoutXml();
            setAppLogo();
            callFooter(baseApcContext,activityIn,ActivityId);

            initMenusCustom(ActivityId,baseApcContext,activityIn);
            //showProgress(true,mContentView,mProgressView);

            initializeViews();
            initializeInputs();
            initializeLabels();
            initializeListeners();
            //showProgress(false,mContentView,mProgressView);
        }
    }


    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    protected void initializeInputs() {
        if(stipend_id!=null)
        {  getFormDetails();
        }


    }

    @Override
    protected void initializeLabels() {

        String   Label = getLabelFromDb("b_146_lbl_downlode", R.string.b_146_lbl_downlode);
        btnDownloadMentorSignedForm.setText(Label);

        /*Label = getLabelFromDb("b_146_form_instruction", R.string.b_146_form_instruction);
        lblInstruction.setText(Label);*/

   /*     Label = getLabelFromDb("b_downlode", R.string.b_downlode);
        lblButton.setText(Label);
*/
        Label = getLabelFromDb("h_146",R.string.h_146);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);

    }

    @Override
    protected void initializeViews() {

        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        btnDownloadMentorSignedForm = (Button) findViewById(R.id.btnDownloadMentorSignedForm);
        lblButton = (TextView) findViewById(R.id.lblButton);
        lblInstruction = (TextView) findViewById(R.id.lblInstruction);
        Text_Instruction = (TextView) findViewById(R.id.Text_Instruction);

    }

    @Override
    protected void initializeListeners() {
        btnDownloadMentorSignedForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // /Toast.makeText(baseApcContext, "Button Clicked", Toast.LENGTH_SHORT).show();
                down();
            }
        });



    }

    private void down(){
/*
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        URL url = null;
        try {
            url = new URL("http://setaportal.co.za/uploads/forms/Seta_1/Student/18_seta_form_2018_07_03.pdf");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        intent.setData(Uri.parse(String.valueOf(url)));
        startActivity(intent);*/
        lblButton.setMovementMethod(LinkMovementMethod.getInstance());
        String url = lblButton.getText().toString();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    @Override
    protected void setLayoutXml() {
        setContentView(R.layout.a_mdownload_signed_form);
    }

    private void getFormDetails() {


        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.M_DOWNLOAD_SIGNED_FORM;
        FINAL_URL=FINAL_URL+"/token/"+token+"/stipend_id/"+stipend_id;
        printLogs(LogTag,"getFormDetails","URL : "+FINAL_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,FINAL_URL, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {
                JSONObject outputJson = null;
                printLogs(LogTag,"getFormDetails","RESPONSE : "+response);
                try {
                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONObject dataM = outputJson.getJSONObject(KEY_DATA);
                        if(dataM.length()>0) {

                            /* GET INSTRUCTIONS FROM API !!-->*/
                            lblButton.setText(dataM.getString("student_form"));
                         //   String info=dataM.getString("student_form");
                            //Text_Instruction.setText(Html.fromHtml(info, Html.FROM_HTML_MODE_COMPACT));

                         /*   if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                Text_Instruction.setText(Html.fromHtml(info,Html.FROM_HTML_MODE_LEGACY));
                            } else {
                                Text_Instruction.setText(Html.fromHtml(info));
                            }*/

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
                })
        {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(MDownloadSignedFormA.this);
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
            Intent intent = new Intent(MDownloadSignedFormA.this, MPastClaimA.class);

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

            Intent intent = new Intent(MDownloadSignedFormA.this, MPendingClaimsA.class);
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
