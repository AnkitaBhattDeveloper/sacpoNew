package za.co.sacpo.grant.xCubeMentor.time_sheet;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;

/*activity_mtime_sheet_download*/
public class MTimeSheetDownload extends BaseAPCPrivate {

    private String ActivityId = "327";
    private String KEY_U_TYPE_ID = "user_type_id";
    public EditText mEmailView, mPasswordView;
    public View mProgressView, mContentView;
    public TextView lblButton, lblInstruction, Text_Instruction;
    public Button btnDownloadMentorSignedForm;
    private TextView lblView;
    String Form_id;
    private static String file_url = "http://seta.local/uploads/forms/Seta_2/Student/25_student_2018_09_01.pdf";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(), this, this.getClass().getSimpleName(),ActivityId);
        initBackBtn();
        bootStrapInit();
        //   initDrawer();
        internetChangeBroadCast();
        fetchVersionData();
        verifyVersion();
    }

    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
            Intent intent = new Intent(MTimeSheetDownload.this, AppUpdatedA.class);
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
            //showProgress(true,mContentView,mProgressView);

            initializeViews();
            initializeInputs();
            initializeLabels();
            initializeListeners();
            //showProgress(false,mContentView,mProgressView);
        }
    }

    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cTAId) {
        baseApcContext = cnt;
        CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
    }

    @Override
    protected void initializeInputs() {
      //  getFormDetails();
    }

    @Override
    protected void initializeLabels() {

        String   Label = getLabelFromDb("b_327_downlode", R.string.b_327_downlode);
        btnDownloadMentorSignedForm.setText(Label);

        Label = getLabelFromDb("b_327_form_instruction", R.string.b_327_form_instruction);
        lblInstruction.setText(Label);

        Label = getLabelFromDb("b_327_lbl_downlode", R.string.b_327_lbl_downlode);
        lblButton.setText(Label);

        Label = getLabelFromDb("h_327",R.string.h_327);
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
        setContentView(R.layout.activity_mtime_sheet_download);
    }

/*    private void getFormDetails() {

        int form_id = 1;
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.STUDENT_DOWNLOADSTUDENTFORM;
        FINAL_URL=FINAL_URL+"/token/"+token+"/form_id/"+form_id;
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

                            *//* GET INSTRUCTIONS FROM API !!-->*//*

                            String info=dataM.getString("form_instruction");
                            //Text_Instruction.setText(Html.fromHtml(info, Html.FROM_HTML_MODE_COMPACT));

                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                Text_Instruction.setText(Html.fromHtml(info,Html.FROM_HTML_MODE_LEGACY));
                            } else {
                                Text_Instruction.setText(Html.fromHtml(info));
                            }

                            showProgress(false, mContentView, mProgressView);
                        }
                        else{
                            //showProgress(false,mContentView,mProgressView);
                            String sTitle="Error :"+ActivityId+"-100";
                            String sMessage=getLabelFromDb("error_327_100",R.string.error_327_100);
                            String sButtonLabelClose="Close";
                            ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                        }
                    }
                    else{
                        //showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-101";
                        String sMessage=getLabelFromDb("error_327_101",R.string.error_327_101);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    printLogs(LogTag,"getStipendDetails","ERROR_327_102 : "+e.getMessage());
                    e.printStackTrace();
                    //showProgress(false,mContentView,mProgressView);
                    String sTitle="Error :"+ActivityId+"-102";
                    String sMessage=getLabelFromDb("error_327_102",R.string.error_327_102);
                    String sButtonLabelClose="Close";
                    ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        printLogs(LogTag,"getAttendanceDetails","ERROR_327_103 : "+error.getMessage());
                        //showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-103";
                        String sMessage=getLabelFromDb("error_327_103",R.string.error_327_103);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

                    }
                })
        {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(MTimeSheetDownload.this);
        requestQueue.add(stringRequest);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(MTimeSheetDownload.this,TimeSheetA.class);
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
