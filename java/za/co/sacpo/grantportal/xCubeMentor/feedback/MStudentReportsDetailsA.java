package za.co.sacpo.grantportal.xCubeMentor.feedback;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Html;
import android.text.TextUtils;

import android.view.MenuItem;
import android.view.View;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
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
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grantportal.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.component.Utils;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;


public class MStudentReportsDetailsA extends BaseAPCPrivate {
    private String ActivityId = "338";
    public View mProgressView, mContentView,heading;

    private TextView lblView,tv_AboutLearnerTraining,tv_Experience,tv_Feedback;

    TextView  activity_heading, txtWeekendDate, txtReportTitle,txtLearnerN,lblLearnerN,lblSupervisorHeading,lblTrainingProgress,lblReportWriting,lblComment,txtComment,txtReportWriting,txtTrainingProgress;
    private WebView webFeedback,webAboutLearnerTraining,webExperience;
    private LinearLayout llSupervisorComment;
    String grant_id, mentor_id, seta_user_id, lea_id, generator,stipend_id;
    Bundle activeUri;
    String student_id,report_id,student_name,month_year;

    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cTAId) {
        baseApcContext = cnt;
        CAId = cTAId;
        activityIn = ain;
        LogTag = lt;
        printLogs(lt, "setBaseApcContextParent", "weAreIn");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(), this, this.getClass().getSimpleName(), ActivityId);
        printLogs(LogTag, "onCreate", "init");
        Bundle activeInputUri = getIntent().getExtras();
        student_id = activeInputUri.getString("student_id");
        student_name = activeInputUri.getString("student_name");
        generator = activeInputUri.getString("generator");
        report_id = activeInputUri.getString("report_id");
        student_id = activeInputUri.getString("student_id");
        month_year = activeInputUri.getString("month_year");
        stipend_id = activeInputUri.getString("stipend_id");
        printLogs(LogTag, "onCreate", "GRANT ID " + student_id);
        printLogs(LogTag, "onCreate", "GENERATOR ID " + generator);
        printLogs(LogTag, "onCreate", "REPORT ID " + report_id);
        printLogs(LogTag, "onCreate", "student_id " + student_id);
        printLogs(LogTag, "onCreate", "month_year " + month_year);
        printLogs(LogTag, "onCreate", "getstipend_idFromAdapter__" + stipend_id);
        bootStrapInit();
        internetChangeBroadCast();
        fetchVersionData();
        verifyVersion();
    }

    @Override
    protected void fetchVersionData() {
        printLogs(LogTag, "fetchVersionData", "exitConnected");
        syncUpdates(baseApcContext, activityIn);
    }

    @Override
    protected void internetChangeBroadCast() {
        registerBroadcastIC();
    }

    @Override
    protected void verifyVersion() {
        printLogs(LogTag, "verifyVersion", "init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag, "verifyVersion", "isUpdate " + isUpdate);
        printLogs(LogTag, "verifyVersion", "isUpdate " + utilSessionObj.getIsUpdateRequired());
        if (isUpdate) {
            Intent intent = new Intent(MStudentReportsDetailsA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void bootStrapInit() {
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext, activityIn);
        if (isConnected) {
            setLayoutXml();
            callFooter(baseApcContext, activityIn, ActivityId);

            initMenusCustom(ActivityId, baseApcContext, activityIn);
            printLogs(LogTag, "bootStrapInit", "initConnected");
            initializeViews();
            initBackBtn();
            showProgress(true, mContentView, mProgressView);
            initializeLabels();
            initializeListeners();
            userToken = userSessionObj.getToken();
            syncToken(baseApcContext, activityIn);
            if (TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
            callDataApi();
            initializeInputs();
            printLogs(LogTag, "onCreate", "exitConnected");
        }
    }

    private void initBackBtn() {
        printLogs(LogTag, "initBackBtn", "init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void setLayoutXml() {
        printLogs(LogTag, "setLayoutXml", "a_grant_details");
        setContentView(R.layout.a_m_student_reports_details);
    }

    @Override
    protected void initializeViews() {
        printLogs(LogTag, "initializeViews", "init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        heading = findViewById(R.id.heading);

        tv_AboutLearnerTraining = findViewById(R.id.tv_AboutLearnerTraining);
        tv_Experience = findViewById(R.id.tv_Experience);
        tv_Feedback = findViewById(R.id.tv_Feedback);
        // txtStdeName=(TextView) findViewById(R.id.txtStdeName);

        txtWeekendDate = (TextView) findViewById(R.id.txtWeekendDate);
        txtReportTitle = (TextView) findViewById(R.id.txtReportTitle);
        webFeedback = (WebView) findViewById(R.id.webFeedback);
        webAboutLearnerTraining = (WebView) findViewById(R.id.webAboutLearnerTraining);
        webExperience = (WebView) findViewById(R.id.webExperience);
        lblLearnerN = (TextView) findViewById(R.id.lblLearnerN);
        txtLearnerN = (TextView) findViewById(R.id.txtLearnerN);
        activity_heading = (TextView) findViewById(R.id.activity_heading);
        lblSupervisorHeading =findViewById(R.id.lblSupervisorHeading);
        lblTrainingProgress =findViewById(R.id.lblTrainingProgress);
        lblReportWriting =findViewById(R.id.lblReportWriting);
        lblComment=findViewById(R.id.lblComment);
        txtComment=findViewById(R.id.txtComment);
        txtReportWriting=findViewById(R.id.txtReportWriting);
        txtTrainingProgress=findViewById(R.id.txtTrainingProgress);
        llSupervisorComment = findViewById(R.id.detail_supervisor_container);
        final WebSettings webSettingsF = webFeedback.getSettings();
        Resources res = getResources();
        float fontSize = res.getDimension(R.dimen.tiniestTextSize);
        webSettingsF.setDefaultFontSize((int)fontSize);
        webFeedback.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webFeedback.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        final WebSettings webSettingsAL = webAboutLearnerTraining.getSettings();
        webSettingsAL.setDefaultFontSize((int)fontSize);
        webAboutLearnerTraining.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webAboutLearnerTraining.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        final WebSettings webSettingsE = webExperience.getSettings();
        webSettingsE.setDefaultFontSize((int)fontSize);
        webExperience.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webExperience.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

    }

    @Override
    protected void initializeInputs() {
        printLogs(LogTag, "initializeInputs", "init");
        getGrantDetails();
        txtLearnerN.setText(student_name);
    }

    @Override
    protected void initializeLabels() {


        printLogs(LogTag, "initializeLabels", "init");
        String Label = getLabelFromDb("l_338_learner_name", R.string.l_338_learner_name);
        /*lblView = (TextView) findViewById(R.id.lblLearnerName);
        lblView.setText(Label);*/

        Label = getLabelFromDb("l_338_progress_report", R.string.l_338_progress_report);
        lblView = (TextView) findViewById(R.id.lblWeekendDate);
        lblView.setText(Label);

        Label = getLabelFromDb("l_338_title", R.string.l_338_title);
        lblView = (TextView) findViewById(R.id.lblReportTitle);
        lblView.setText(Label);


        Label = getLabelFromDb("l_338_feedback", R.string.l_338_feedback);
        lblView = (TextView) findViewById(R.id.lblFeedback);
        lblView.setText(Label);


        Label = getLabelFromDb("l_338_abt_leaener_training", R.string.l_338_abt_leaener_training);
        lblView = (TextView) findViewById(R.id.lblAboutLearnerTraining);
        lblView.setText(Label);



        Label = getLabelFromDb("h_338", R.string.h_338);
        lblView = (TextView) findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("l_338_lbl_exp", R.string.l_338_lbl_exp);
        lblView = (TextView) findViewById(R.id.lblAboutLearnerExp);
        lblView.setText(Label);

        Label = getLabelFromDb("l_338_learner_name", R.string.l_338_learner_name);
        lblView = (TextView) findViewById(R.id.lblLearnerN);
        lblView.setText(Label);

        Label = getLabelFromDb("l_338_supervisor", R.string.l_338_supervisor);
        lblSupervisorHeading.setText(Label);
        Label = getLabelFromDb("l_338_training_progress", R.string.l_338_training_progress);
        lblTrainingProgress.setText(Label);
        Label = getLabelFromDb("l_338_report_writing", R.string.l_338_report_writing);
        lblReportWriting.setText(Label);
        Label = getLabelFromDb("l_338_comment", R.string.l_338_comment);
        lblComment.setText(Label);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
        }

    }

    @Override
    protected void initializeListeners() {

    }

    public void callDataApi() {
        printLogs(LogTag, "callDataApi", "init");
        userToken = userSessionObj.getToken();
        if (!TextUtils.isEmpty(userToken)) {
            printLogs(LogTag, "callDataApi", "Token Update");
            syncUserData(baseApcContext, activityIn);
            syncMentorData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn, ActivityId);
        }
    }

    private void getGrantDetails() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.M_STUDENT_REPORT_DETAILS;
        FINAL_URL = FINAL_URL + "?token=" + token + "&report_id=" + report_id;
        printLogs(LogTag, "getGrantDetails", "URL : " + FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "getGrantDetails", "RESPONSE : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if (Status.equals("1")) {
                        JSONObject dataM = outputJson.getJSONObject(KEY_DATA);
                      //  student_id=dataM.getString("s_w_r_student_id");
                       // student_name= dataM.getString("student_name");
                        txtReportTitle.setText(dataM.getString("title"));
                        txtWeekendDate.setText(dataM.getString("date"));
                        String feedback = dataM.getString("feedback");
                        tv_Feedback.setText(Html.fromHtml(feedback).toString());
                       String newStr = feedback.replaceAll("\\\\<[^>]*>", " ");
                        //tv_Feedback.setText(newStr);
                     //   webFeedback.loadData(newStr, "text/html", "UTF-8");
                        String training = dataM.getString("training");
                        String newStr2 = training.replaceAll("\\\\<[^>]*>", " ");
                        tv_AboutLearnerTraining.setText(Html.fromHtml(training).toString());
                      //  webAboutLearnerTraining.loadData(newStr2, "text/html", "UTF-8");
                        String experience = dataM.getString("learning_experices");
                        String newStr3 = experience.replaceAll("\\\\<[^>]*>", " ");
                        tv_Experience.setText(Html.fromHtml(experience).toString());
                    //    webExperience.loadData(newStr3, "text/html", "UTF-8");

                        String stpcomment = dataM.getString("c_u_r_training_progress");
                        String srwcomment = dataM.getString("c_u_r_report_writing");
                        String comment = dataM.getString("c_u_r_comment");
                        int sAcommented = 0;
                        try {
                            sAcommented = Integer.parseInt(dataM.getString("is_supervisor_commented"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(sAcommented==1) {
                            if(!comment.equalsIgnoreCase("")) {
                                txtComment.setText(comment);
                            }
                            else{
                                txtComment.setVisibility(View.GONE);
                                lblComment.setVisibility(View.GONE);
                            }
                            txtReportWriting.setText(stpcomment);
                            txtTrainingProgress.setText(srwcomment);
                        }
                        else{
                            llSupervisorComment.setVisibility(View.GONE);
                        }


                        showProgress(false, mContentView, mProgressView);
                    }  else if(Status.equals("2")) {
                        showProgress(false,mContentView,mProgressView);
                    }else {
                        //showProgress(false,mContentView,mProgressView);
                        printLogs(LogTag, "getGrantDetails", "error_try_again : DATA_ERROR");
                        String sTitle = "Error :" + ActivityId + "-100";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    printLogs(LogTag, "getGrantDetails", "error_try_again : " + e.getMessage());
                    e.printStackTrace();
                    //showProgress(false,mContentView,mProgressView);
                    String sTitle = "Error :" + ActivityId + "-101";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                printLogs(LogTag, "getGrantDetails", "error_try_again : " + error);
                //showProgress(false,mContentView,mProgressView);
                String sTitle = "Error :" + ActivityId + "-102";
                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag, "onOptionsItemSelected", "init");
            Bundle inputUri = new Bundle();
            inputUri.putString("student_id", student_id);
            inputUri.putString("student_name", student_name);
            inputUri.putString("generator", generator);
            inputUri.putString("month_year", month_year);
            printLogs(LogTag, "onOptionsItemSelected", "student_id"+student_id+"-student_name"+student_name + "generator" + generator);
            Intent intent = new Intent(MStudentReportsDetailsA.this, MStudentReports.class);
            intent.putExtras(inputUri);
            printLogs(LogTag, "onOptionsItemSelected", "MStudentReports");
            startActivity(intent);
            finish();

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
