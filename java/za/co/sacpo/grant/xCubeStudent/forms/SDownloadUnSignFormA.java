package za.co.sacpo.grant.xCubeStudent.forms;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
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

import java.net.MalformedURLException;
import java.net.URL;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;

public class SDownloadUnSignFormA extends BaseAPCPrivate {

    private String ActivityId="132";
    private String KEY_U_TYPE_ID="user_type_id";

    public View mProgressView, mContentView;
    public Button btnDownloadForm;
    public  TextView lblButton, lblInstruction, Text_Instruction;
    private TextView lblView;
    String form_id;
    String generator,date_input;
    // File url to download
   // private static String file_url = "http://seta.local/uploads/forms/Seta_2/Student/25_student_2018_09_01.pdf";
    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId){
        baseApcContext = cnt;CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);

        Bundle activeInputUri = getIntent().getExtras();
        date_input = activeInputUri.getString("date_input");
        form_id = activeInputUri.getString("form_id");
        generator = activeInputUri.getString("generator");
        bootStrapInit();

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
            Intent intent = new Intent(SDownloadUnSignFormA.this,AppUpdatedA.class);
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
            initBackBtn();
//        initDrawer();
            internetChangeBroadCast();
            fetchVersionData();
            verifyVersion();
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



    @Override
    protected void initializeInputs(){
        getFormDetails();
    }

    @Override
    protected void initializeLabels(){


        String  Label = getLabelFromDb("b_132_dounlode_form",R.string.b_132_download_form);
        lblButton.setText(Label);

        Label = getLabelFromDb("l_132_btn",R.string.l_132_btn);
        btnDownloadForm.setText(Label);

        Label = getLabelFromDb("b_132_lbl_form_instruction", R.string.b_132_lbl_form_instruction);
        lblInstruction.setText(Label);

        Label = getLabelFromDb("b_132_lbl_Btutton", R.string.b_lbl_Btutton);
        lblButton.setText(Label);

        Label = getLabelFromDb("h_132",R.string.h_132);
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

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        URL url = null;
        try {
            url = new URL("http://setaportal.co.za/uploads/forms/Seta_1/Student/18_seta_form_2018_07_03.pdf");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        intent.setData(Uri.parse(String.valueOf(url)));
        startActivity(intent);
    }



    @Override
    protected void setLayoutXml() {
        setContentView(R.layout.a_download_un_sign_form);
    }


    private void getFormDetails() {
      //  int form_id = 1;
        int seta_id = 1;
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.STUDENT_DOWNLOADSTUDENTFORM;
        FINAL_URL=FINAL_URL+"/token/"+token+"/form_id/"+form_id+"/seta_id/"+seta_id;
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

                            String info=dataM.getString("form_instruction");
                            //Text_Instruction.setText(Html.fromHtml(info, Html.FROM_HTML_MODE_COMPACT));

                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                Text_Instruction.setText(Html.fromHtml(info,Html.FROM_HTML_MODE_LEGACY));
                            } else {
                                Text_Instruction.setText(Html.fromHtml(info));
                            }

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
                        printLogs(LogTag,"getAttendanceDetails","error_try_again : "+error.getMessage());
                        //showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-102";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

                    }
                })
        {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(SDownloadUnSignFormA.this);
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
            Intent intent = new Intent(SDownloadUnSignFormA.this,SFormsA.class);
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
