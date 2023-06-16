package za.co.sacpo.grantportal.xCubeMentor.student;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import za.co.sacpo.grantportal.AppUpdatedA;
import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.adapter.MStudentAdapter;
import za.co.sacpo.grantportal.xCubeLib.dataObj.MStudentObj;
import za.co.sacpo.grantportal.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.component.Utils;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.StudentBaseDrawerA;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;

public class StudentsA extends StudentBaseDrawerA {

    private String ActivityId = "144";

    public View mProgressView, mContentView, mProgressRView, mContentRView;
    private TextView lblView, mIGrantNameText, mIStartDateText, mIEndDateText, mIStudentsTTText;
    private RecyclerView recyclerViewQ;
    public MStudentObj rDataObj = new MStudentObj();
    private List<MStudentObj.Item> rDataObjList = null;

    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId) {
        baseApcContext = cnt;CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
    }
    @Override
    protected void internetChangeBroadCast(){
        registerBroadcastIC();
    }
    @Override
    protected void initializeInputs() {



    }

    @Override
    protected void initializeLabels() {


        String Label = getLabelFromDb("l_144_students", R.string.l_144_students);
        lblView = (TextView) findViewById(R.id.lblStudents);
        lblView.setText(Label);

        Label = getLabelFromDb("h_144",R.string.h_144);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);
    }

    @Override
    protected void initializeViews() {
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mContentRView = findViewById(R.id.content_container_r);
        mProgressRView = findViewById(R.id.progress_bar_r);


        mIGrantNameText = (TextView) findViewById(R.id.iGrantName);
        mIStartDateText = (TextView) findViewById(R.id.iStartDate);
        mIEndDateText = (TextView) findViewById(R.id.iEndDate);
        mIStudentsTTText = (TextView) findViewById(R.id.lblStudentsTT);

        rDataObjList = rDataObj.getITEMS();
        recyclerViewQ = (RecyclerView) findViewById(R.id.rVStudents);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);

        View recyclerView = findViewById(R.id.rVStudents);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

    }

    @Override
    protected void initializeListeners() {

        mIStudentsTTText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sToolTip = getLabelFromDb("tt_144_scroll", R.string.tt_144_scroll);
                showTooltip(mIStudentsTTText, sToolTip, 4);
            }
        });


    }

    public void validateInput() {

    }

    @Override
    protected void setLayoutXml() {
        setContentView(R.layout.a_students);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(), this, this.getClass().getSimpleName(),ActivityId);
        bootStrapInit();
        fetchVersionData();
        verifyVersion();
        internetChangeBroadCast();
    }
    ////TODO::@Override
    protected void fetchVersionData() {
        printLogs(LogTag, "fetchVersionData", "exitConnected");
        syncUpdates(baseApcContext, activityIn);
    }

    ////TODO::@Override
    protected void verifyVersion() {
        printLogs(LogTag, "verifyVersion", "init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag, "verifyVersion", "isUpdate " + isUpdate);
        printLogs(LogTag, "verifyVersion", "isUpdate " + utilSessionObj.getIsUpdateRequired());
        if (isUpdate == true) {
            Intent intent = new Intent(StudentsA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    public void callHeaderBuilder() {

        String sHeader3 = getLabelFromDb("t_head_144_s_LearnerName", R.string.t_head_144_s_LearnerName);
        String sHeader4 = getLabelFromDb("t_head_144_s_RegisNo", R.string.t_head_144_s_RegisNo);
        String sHeader5 = getLabelFromDb("t_head_144_s_LearnerId", R.string.t_head_144_s_LearnerId);
        String sHeader6 = getLabelFromDb("t_head_144_s_University", R.string.t_head_144_s_University);
        String sHeader7 = getLabelFromDb("t_head_144_s_Duration", R.string.t_head_144_s_Duration);
        String sHeader8 = getLabelFromDb("t_head_144_s_StartDate", R.string.t_head_144_s_StartDate);
        String sHeader9 = getLabelFromDb("t_head_144_s_EndDate", R.string.t_head_144_s_EndDate);
        String sHeader10 = getLabelFromDb("t_head_144_s_WorkedDays", R.string.t_head_144_s_WorkedDays);
        String sHeader11 = getLabelFromDb("t_head_144_s_LeaveDays", R.string.t_head_144_s_LeaveDay);
        String sHeader12 = getLabelFromDb("t_head_144_s_LeaveDay", R.string.t_head_144_s_mSbtnApproveAttendance);
        String sHeader13 = getLabelFromDb("t_head_144_s_LeaveDay", R.string.t_head_144_s_mSbtnDownloadClaimForm);
        String sHeader14 = getLabelFromDb("t_head_144_s_LeaveDay", R.string.t_head_144_s_mSbtnApproved);
        String sHeader15 = getLabelFromDb("t_head_144_s_LeaveDay", R.string.t_head_144_s_mSbtnDownloadLearnerCV);

        rDataObj.addItem(rDataObj.createItem(0, 0, sHeader3, sHeader4, sHeader5, sHeader6, sHeader7, sHeader8, sHeader9, sHeader10, sHeader11, sHeader12, sHeader13, sHeader14, sHeader15));
    }

    @Override
    protected void bootStrapInit() {
        Boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext, activityIn);
        if (isConnected == true) {
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);

            initMenusCustom(ActivityId,baseApcContext,activityIn);

            initDrawer();
            initializeViews();
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
            callHeaderBuilder();
            fetchData();
            showProgress(false, mContentView, mProgressView);
        }
    }

    public void callDataApi() {
        userToken = userSessionObj.getToken();
        if (!TextUtils.isEmpty(userToken)) {
            syncUserData(baseApcContext, activityIn);
            syncMentorData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn,ActivityId);
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new MStudentAdapter(rDataObjList, baseApcContext, activityIn));
    }

    public void showList() {
        List<MStudentObj.Item> StudentData = rDataObj.getITEMS();
        MStudentAdapter adapter = new MStudentAdapter(StudentData, baseApcContext, activityIn);
        recyclerViewQ.setAdapter(adapter);
        showProgress(false, mContentRView, mProgressRView);
    }

    public void fetchData() {
        String token = userSessionObj.getToken();
        String grantId = studentSessionObj.getGrantId();

        /*PAss holiday api*/
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.MSTUDENT_LIST;
        FINAL_URL = FINAL_URL + "?token=" + token + "&grant_id=" + grantId;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchData", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if (Status.equals("1")) {
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        for (int i = 0; i < dataM.length(); i++) {
                            int pos = i + 1;
                            JSONObject rec = dataM.getJSONObject(i);
                            int aId2 = Integer.parseInt(rec.getString("s_m_s_id"));
                            String mSLearnerName3 = rec.getString("m_student_name");
                            String mSRegisNo4 = rec.getString("s_id_no");
                            String mSLearnerId5 = rec.getString("m_s_student_id");
                            String mSUniversity6 = rec.getString("u_name");
                            String mSDuration7 = rec.getString("duration");
                            String mSStartDate8 = rec.getString("start_date_string");
                            String mSEndDate9 = rec.getString("end_date_string");
                            String mSWorkedDays10 = rec.getString("totalAtt");
                            String mSLeaveDay11 = rec.getString("leave_days");
                            String mSbtnDownloadLearnerCV12 = "";
                            String mSbtnApproveAttendance13 = "";
                            String mSbtnDownloadClaimForm14 = "";
                            String mSbtnApproved15 = "";
                            rDataObj.addItem(rDataObj.createItem(pos, aId2, mSLearnerName3, mSRegisNo4, mSLearnerId5, mSUniversity6, mSDuration7, mSStartDate8, mSEndDate9, mSWorkedDays10, mSLeaveDay11, mSbtnDownloadLearnerCV12, mSbtnApproveAttendance13, mSbtnDownloadClaimForm14, mSbtnApproved15));
                            showList();
                        }
                    } else {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-101";
                        String sMessage = getLabelFromDb("error_144_101", R.string.error_144_101);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
                        String sTitle = "Error :" + ActivityId + "-104";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);

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





