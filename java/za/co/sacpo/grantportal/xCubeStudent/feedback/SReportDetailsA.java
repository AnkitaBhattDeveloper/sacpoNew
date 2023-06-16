package za.co.sacpo.grantportal.xCubeStudent.feedback;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import za.co.sacpo.grantportal.AppUpdatedA;
import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.component.Utils;
import za.co.sacpo.grantportal.xCubeLib.db.sWeeklyReportDetails;
import za.co.sacpo.grantportal.xCubeLib.db.sWeeklyReportDetailsAdapter;
import za.co.sacpo.grantportal.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;


/*a_s_report_details*/
public class SReportDetailsA extends BaseAPCPrivate {

    private String ActivityId = "177";
    public View mProgressView, mContentView,heading;

    private TextView lblView,tv_AboutLearnerTraining,tv_Experience,tv_Feedback;

    //TextView txtMonthYear, txtReportTitle,txtFeedback,txtAboutLearnerTraining,txtExperience,activity_heading;
    private TextView txtMonthYear, txtReportTitle,activity_heading,lblSupervisorHeading,lblTrainingProgress,lblReportWriting,lblComment,txtComment,txtReportWriting,txtTrainingProgress;
    private WebView webFeedback,webAboutLearnerTraining,webExperience;
    // Button mbtn_attendance,mbtn_leave,mbtn_stipendClaim;
    String grant_id, mentor_id, seta_user_id, lea_id, generator,is_upload_attendance,student_id;
    Bundle activeUri;
    String report_id="",date_input;
    private LinearLayout llSupervisorComment;
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
        report_id = activeInputUri.getString("report_id");
        grant_id = activeInputUri.getString("grant_id");
        generator = activeInputUri.getString("generator");
        date_input = activeInputUri.getString("month_year");
        student_id = activeInputUri.getString("student_id");
        is_upload_attendance = activeInputUri.getString("is_upload_attendance");

        String result = new StringBuffer(date_input).reverse().toString();

        printLogs(LogTag, "onCreate", "DATE_INPUT_RESULT"  + result);
        printLogs(LogTag, "onCreate", "student_id_GET__"  + student_id);
        printLogs(LogTag, "onCreate", "REPORT_ID__" + report_id);
        printLogs(LogTag, "onCreate", "grant_id__" + grant_id);
        printLogs(LogTag, "onCreate", "DDDDDDDDDD_INPUT" + date_input);
        printLogs(LogTag, "onCreate", "GENERATOR ID " + generator);
        printLogs(LogTag, "onCreate", "is_upload_attendance " + is_upload_attendance);
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
            Intent intent = new Intent(SReportDetailsA.this, AppUpdatedA.class);
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
        }else{
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
            fetchOfflineData();
            printLogs(LogTag, "onCreate", "exitConnected");
        }
    }

    private void initBackBtn() {
        printLogs(LogTag, "initBackBtn", "init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void setLayoutXml() {
        printLogs(LogTag, "setLayoutXml", "a_s_report_details");
        setContentView(R.layout.a_s_report_details);
    }

    @Override
    protected void initializeViews() {
        printLogs(LogTag, "initializeViews", "init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        heading = findViewById(R.id.heading);
        txtMonthYear = (TextView) findViewById(R.id.txtMonthYear);
        txtReportTitle = (TextView) findViewById(R.id.txtReportTitle);
        lblSupervisorHeading =findViewById(R.id.lblSupervisorHeading);
        lblTrainingProgress =findViewById(R.id.lblTrainingProgress);
        lblReportWriting =findViewById(R.id.lblReportWriting);
        lblComment=findViewById(R.id.lblComment);
        txtComment=findViewById(R.id.txtComment);
        txtReportWriting=findViewById(R.id.txtReportWriting);
        txtTrainingProgress=findViewById(R.id.txtTrainingProgress);
        llSupervisorComment = findViewById(R.id.detail_supervisor_container);
        webFeedback = (WebView) findViewById(R.id.webFeedback);
        webAboutLearnerTraining = (WebView) findViewById(R.id.webAboutLearnerTraining);
        webExperience = (WebView) findViewById(R.id.webExperience);
        activity_heading = (TextView) findViewById(R.id.activity_heading);
        tv_AboutLearnerTraining = findViewById(R.id.tv_AboutLearnerTraining);
        tv_Experience = findViewById(R.id.tv_Experience);
        tv_Feedback = findViewById(R.id.tv_Feedback);

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
    }

    @Override
    protected void initializeLabels() {
        printLogs(LogTag, "initializeLabels", "init");
        String Label = getLabelFromDb("l_177_month_year", R.string.l_177_month_year);
        lblView = (TextView) findViewById(R.id.lblMonthYear);
        lblView.setText(Label);

        Label = getLabelFromDb("l_177_title", R.string.l_177_title);
        lblView = (TextView) findViewById(R.id.lblReportTitle);
        lblView.setText(Label);


        Label = getLabelFromDb("l_177_feedback", R.string.l_177_feedback);
        lblView = (TextView) findViewById(R.id.lblFeedback);
        lblView.setText(Label);


        Label = getLabelFromDb("l_177_abt_leaener_training", R.string.l_177_abt_leaener_training);
        lblView = (TextView) findViewById(R.id.lblAboutLearnerTraining);
        lblView.setText(Label);

        Label = getLabelFromDb("l_177_lbl_exp", R.string.l_177_lbl_exp);
        lblView = (TextView) findViewById(R.id.lblExperience);
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
            activity_heading.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));

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
            syncStudentData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn, ActivityId);
        }
    }

    private void getGrantDetails() {
        String token = userSessionObj.getToken();
        // String studentId = student_id;
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
                        sWeeklyReportDetailsAdapter adapter = new sWeeklyReportDetailsAdapter(getApplicationContext());
                       // adapter.truncate();
                        ArrayList<sWeeklyReportDetails> arrayList = new ArrayList<>();
                        JSONObject dataM = outputJson.getJSONObject(KEY_DATA);
                        activity_heading.setText(dataM.getString("number"));
                        txtMonthYear.setText(dataM.getString("date"));
                        txtReportTitle.setText(dataM.getString("title"));
                        String feedback = dataM.getString("feedback");
                        tv_Feedback.setText(Html.fromHtml(feedback).toString());
                        String training = dataM.getString("training");
                        tv_AboutLearnerTraining.setText(Html.fromHtml(training).toString());
                        String experience = dataM.getString("learning_experices");
                        tv_Experience.setText(Html.fromHtml(experience).toString());
                        String stpcomment = dataM.getString("c_u_r_training_progress");
                        String srwcomment = dataM.getString("c_u_r_report_writing");
                        String comment = dataM.getString("c_u_r_comment");
                        int sAcommented = 0;


                        arrayList.add(new sWeeklyReportDetails(dataM.getString("s_w_r_id"),
                                dataM.getString("s_w_r_student_id"),dataM.getString("student_name"),
                                dataM.getString("title"),dataM.getString("training"),
                                dataM.getString("day"),dataM.getString("feedback"),
                                dataM.getString("learning_experices"),dataM.getString("number")
                                ,dataM.getString("month_year"),dataM.getString("report_add_date"),
                                dataM.getString("date"),dataM.getString("is_supervisor_commented"),
                                dataM.getString("c_u_r_comment"),dataM.getString("c_u_r_training_progress"),
                                dataM.getString("c_u_r_report_writing"),dataM.getString("supervisor_status")));
                        adapter.insert(arrayList);
                        sWeeklyReportDetails sWeeklyReportDetails = new sWeeklyReportDetails(dataM.getString("s_w_r_id"),
                                dataM.getString("s_w_r_student_id"),dataM.getString("student_name"),
                                dataM.getString("title"),dataM.getString("training"),
                                dataM.getString("day"),dataM.getString("feedback"),
                                dataM.getString("learning_experices"),dataM.getString("number")
                                ,dataM.getString("month_year"),dataM.getString("report_add_date"),
                                dataM.getString("date"),dataM.getString("is_supervisor_commented"),
                                dataM.getString("c_u_r_comment"),dataM.getString("c_u_r_training_progress"),
                                dataM.getString("c_u_r_report_writing"),dataM.getString("supervisor_status"));
                        sWeeklyReportDetails byId = adapter.getById(Integer.parseInt(report_id));
                        printLogs(LogTag, "getGrantDetails", "report_id : " + report_id);
                        printLogs(LogTag, "getGrantDetails", "byId : " + byId);

                        if(!byId.getS_w_r_id().equals(report_id)){
                            adapter.insert(arrayList);
                        }else{
                            adapter.update(sWeeklyReportDetails);
                        }



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
                printLogs(LogTag, "getGrantDetails", "error_try_again : " + error.getMessage());
                //showProgress(false,mContentView,mProgressView);
                String sTitle = "Error :" + ActivityId + "-102";
                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);

            }
        })  {
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
    private void fetchOfflineData() {
        printLogs(LogTag, "fetchOfflineData", "init");
        sWeeklyReportDetailsAdapter adapter  =new sWeeklyReportDetailsAdapter(getApplicationContext());
        List<sWeeklyReportDetails> adapterAll = adapter.getAll();
        printLogs(LogTag, "fetchOfflineData", "init"+adapterAll);
        sWeeklyReportDetails reportDetails = adapter.getById(Integer.parseInt(report_id));


        activity_heading.setText(reportDetails.getNumber());
        txtMonthYear.setText(reportDetails.getDate());
        txtReportTitle.setText(reportDetails.getTitle());
        String feedback = reportDetails.getFeedback();
        tv_Feedback.setText(Html.fromHtml(feedback).toString());
        String training = reportDetails.getTraining();
        tv_AboutLearnerTraining.setText(Html.fromHtml(training).toString());
        String experience = reportDetails.getLearning_experices();
        tv_Experience.setText(Html.fromHtml(experience).toString());
        String stpcomment = reportDetails.getC_u_r_training_progress();
        String srwcomment = reportDetails.getC_u_r_report_writing();
        String comment = reportDetails.getC_u_r_comment();
        int sAcommented = 0;

        sAcommented = Integer.parseInt(reportDetails.getIs_supervisor_commented());
        if (sAcommented == 1) {
            if (!comment.equalsIgnoreCase("")) {
                txtComment.setText(comment);
            } else {
                txtComment.setVisibility(View.GONE);
                lblComment.setVisibility(View.GONE);
            }
            txtReportWriting.setText(stpcomment);
            txtTrainingProgress.setText(srwcomment);
        } else {
            llSupervisorComment.setVisibility(View.GONE);
        }

        showProgress(false, mContentView, mProgressView);
        printLogs(LogTag, "fetchOfflineData", "exit");


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag, "onOptionsItemSelected", "init");
        if (item.getItemId() == android.R.id.home) {
            Bundle inputUri = new Bundle();
            Intent intent = new Intent(SReportDetailsA.this, SFeedbackDA.class);
            printLogs(LogTag, "onOptionsItemSelected", "SFeedbackDA");
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
