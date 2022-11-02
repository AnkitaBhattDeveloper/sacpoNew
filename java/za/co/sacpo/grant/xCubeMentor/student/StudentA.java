package za.co.sacpo.grant.xCubeMentor.student;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;

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
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import za.co.sacpo.grant.AppUpdatedA;

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeMentor.MDashboardDA;
import za.co.sacpo.grant.xCubeStudent.attendance.SAttDA;

import za.co.sacpo.grant.xCubeStudent.leaves.SLeavesDA;
import za.co.sacpo.grant.xCubeStudent.stipends.SStipendsDA;


public class StudentA extends BaseAPCPrivate {
    private String ActivityId = "145";
    public View mProgressView, mContentView;
    private TextView lblView;
    TextView txtStudentName, txtEmail, txtStudentID, txtSetaManagerName, txtGenderName, txtStartDate, txtEndDate, txtMentorName, txtHostEmployer,
            txtGrantAdmin, txtLeadEmployer, txtWorkStation, txtSetaName,lblLearnertName;
    Button mbtn_attendance,mbtn_leave,mbtn_stipendClaim;
    String grant_id, mentor_id, seta_user_id, lea_id, generator;
    Bundle activeUri;
    String student_id;

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
        generator = activeInputUri.getString("generator");
        printLogs(LogTag, "onCreate", "GRANT ID " + student_id);
        printLogs(LogTag, "onCreate", "GENERATOR ID " + generator);
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
            Intent intent = new Intent(StudentA.this, AppUpdatedA.class);
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
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void setLayoutXml() {
        printLogs(LogTag, "setLayoutXml", "a_grant_details");
        setContentView(R.layout.a_student);
    }

    @Override
    protected void initializeViews() {
        printLogs(LogTag, "initializeViews", "init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);

        // txtStdeName=(TextView) findViewById(R.id.txtStdeName);
        txtStudentID = (TextView) findViewById(R.id.txtStudentID);
        txtStudentName = (TextView) findViewById(R.id.txtStudentName);
        txtSetaManagerName = (TextView) findViewById(R.id.txtSetaManagerName);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtGenderName = (TextView) findViewById(R.id.txtGenderName);
        txtStartDate = (TextView) findViewById(R.id.txtStartDate);
        txtEndDate = (TextView) findViewById(R.id.txtEndDate);
        txtMentorName = (TextView) findViewById(R.id.txtMentorName);
        txtHostEmployer = (TextView) findViewById(R.id.txtHostEmployer);
        txtGrantAdmin = (TextView) findViewById(R.id.txtGrantAdmin);
        txtLeadEmployer = (TextView) findViewById(R.id.txtLeadEmployer);
        txtWorkStation = (TextView) findViewById(R.id.txtWorkStation);
        txtSetaName = (TextView) findViewById(R.id.txtSetaName);
        lblLearnertName = (TextView) findViewById(R.id.lblLearnertName);

        mbtn_attendance = (Button) findViewById(R.id.mbtn_attendance);
        mbtn_leave = (Button) findViewById(R.id.mbtn_leave);
        mbtn_stipendClaim = (Button) findViewById(R.id.mbtn_stipendClaim);


    }

    @Override
    protected void initializeInputs() {
        printLogs(LogTag, "initializeInputs", "init");
        getGrantDetails();
    }

    @Override
    protected void initializeLabels() {


        printLogs(LogTag, "initializeLabels", "init");
        String Label = getLabelFromDb("error_145_learner_id", R.string.error_145_learner_id);
        lblView = (TextView) findViewById(R.id.lblStudent_id);
        lblView.setText(Label);

        Label = getLabelFromDb("error_145_learner_name", R.string.error_145_learner_name);
        lblView = (TextView) findViewById(R.id.lblStudentName);
        lblView.setText(Label);
        Label = getLabelFromDb("error_145_learner_email", R.string.error_145_learner_email);
        lblView = (TextView) findViewById(R.id.lblEmail);
        lblView.setText(Label);

        Label = getLabelFromDb("error_145_learner_gender", R.string.error_145_learner_gender);
        lblView = (TextView) findViewById(R.id.lblGenderName);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_145_start_date", R.string.lbl_145_start_date);
        lblView = (TextView) findViewById(R.id.lblStartDate);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_145_end_date", R.string.lbl_145_end_date);
        lblView = (TextView) findViewById(R.id.lblEndDate);
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_145_mentor_name", R.string.lbl_145_mentor_name);
        lblView = (TextView) findViewById(R.id.lblMentorName);
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_145_host_employer", R.string.lbl_145_host_employer);
        lblView = (TextView) findViewById(R.id.lblHostEmployer);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_145_grant_admin", R.string.lbl_145_grant_admin);
        lblView = (TextView) findViewById(R.id.lblGrantAdmin);
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_145_lead_employer", R.string.lbl_145_lead_employer);
        lblView = (TextView) findViewById(R.id.lblLeadEmployer);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_145_workstation", R.string.lbl_145_workstation);
        lblView = (TextView) findViewById(R.id.lblWorkStation);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_145_seta_name", R.string.lbl_145_seta_name);
        lblView = (TextView) findViewById(R.id.lblSetaName);
        lblView.setText(Label);

         Label = getLabelFromDb("lbl_145_grant_details", R.string.lbl_145_grant_details);
        lblView = (TextView) findViewById(R.id.lblGrantDetails);
        lblView.setText(Label);


        Label = getLabelFromDb("l_btn_145_goto_Leave",R.string.l_btn_145_goto_Leave);
        mbtn_leave.setText(Label);

        Label = getLabelFromDb("l_btn_145_goto_Attendance",R.string.l_btn_145_goto_Attendance);
        mbtn_attendance.setText(Label);
        Label = getLabelFromDb("l_btn_145_goto_sCLaim",R.string.l_btn_145_goto_sCLaim);
        mbtn_stipendClaim.setText(Label);

        Label = getLabelFromDb("h_145", R.string.h_145);
        lblView = (TextView) findViewById(R.id.activity_heading);
        lblView.setText(Label);

        Label = getLabelFromDb("l_145_learner_details", R.string.l_145_learner_details);
        lblView = (TextView) findViewById(R.id.lblLearnertName);
        lblView.setText(Label);


    }

    @Override
    protected void initializeListeners() {
        mbtn_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printLogs(LogTag,"mbtn_attendance","SETA USER ID "+seta_user_id);

                activeUri = new Bundle();
                Intent intent = new Intent(StudentA.this, SAttDA.class);
                activeUri.putString("user_id",seta_user_id);
                intent.putExtras(activeUri);
                startActivity(intent);
                finish();

            }
        });
        mbtn_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printLogs(LogTag,"mbtn_leave","LEA USER ID "+lea_id);
                activeUri = new Bundle();
                Intent intent = new Intent(StudentA.this, SLeavesDA.class);
                activeUri.putString("user_id",lea_id);
                intent.putExtras(activeUri);
                startActivity(intent);
                finish();
            }
        });
        mbtn_stipendClaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printLogs(LogTag,"mbtn_stipendClaim","Mentor USER ID "+mentor_id);
                activeUri = new Bundle();
                Intent intent = new Intent(StudentA.this, SStipendsDA.class);
                activeUri.putString("user_id",mentor_id);
                intent.putExtras(activeUri);
                startActivity(intent);
                finish();
            }
        });
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
        // String studentId = student_id;
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.MENTOR_GRANT_STUDENT_DATA_URL;
        FINAL_URL = FINAL_URL + "?token=" + token + "&student_id=" + student_id;
        printLogs(LogTag, "getGrantDetails", "URL : " + FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "getGrantDetails", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if (Status.equals("1")) {
                        JSONObject dataM = outputJson.getJSONObject(KEY_DATA);

                        /*TODO:Create new Function fetch data to add one more api for Grant Details -Pending...!!!*/

                        txtStudentID.setText(dataM.getString("s_id_no"));
                        txtStudentName.setText(dataM.getString("student_name"));
                        txtEmail.setText(dataM.getString("u_email"));
                        txtGenderName.setText(dataM.getString("gender_name"));
                        txtStartDate.setText(dataM.getString("s_s_g_grant_start_date"));
                        txtEndDate.setText(dataM.getString("s_s_g_grant_end_date"));
                        txtMentorName.setText(dataM.getString("mentor_name"));
                        txtHostEmployer.setText(dataM.getString("host_emp_name"));
                        txtGrantAdmin.setText(dataM.getString("grant_admin"));
                        txtWorkStation.setText(dataM.getString("workstation"));
                        txtSetaName.setText(dataM.getString("seta_name"));
                        txtLeadEmployer.setText(dataM.getString("lem_name"));

                        showProgress(false, mContentView, mProgressView);
                    } else if (Status.equals("2")) {
                        showProgress(false, mContentView, mProgressView);
                    } else {
                        //showProgress(false,mContentView,mProgressView);
                        printLogs(LogTag, "getGrantDetails", "ERROR_145_100 : DATA_ERROR");
                        String sTitle = "Error :" + ActivityId + "-100";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    printLogs(LogTag, "getGrantDetails", "ERROR_145_101 : " + e.getMessage());
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
                printLogs(LogTag, "getGrantDetails", "ERROR_145_102 : " + error.getMessage());
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

            if (item.getItemId() == android.R.id.home) {
                Bundle inputUri = new Bundle();
                inputUri.putString("student_id", student_id);
                inputUri.putString("generator", generator);
                Intent intent = new Intent(StudentA.this, MDashboardDA.class);
                intent.putExtras(inputUri);
                startActivity(intent);
                finish();
                printLogs(LogTag,"onOptionsItemSelected","MDashboardDA");
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
