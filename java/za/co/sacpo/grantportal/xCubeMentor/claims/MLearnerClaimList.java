package za.co.sacpo.grantportal.xCubeMentor.claims;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import za.co.sacpo.grantportal.AppUpdatedA;
import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.adapter.MLearnerClaimAdapter;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.component.Utils;
import za.co.sacpo.grantportal.xCubeLib.dataObj.MLearnerClaimObj;
import za.co.sacpo.grantportal.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grantportal.xCubeLib.spinners.SpinnerSet;
import za.co.sacpo.grantportal.xCubeLib.spinners.SpinnerSetAdapter;

/*a_ga_learner_claim*/
public class MLearnerClaimList extends BaseAPCPrivate {

    public String ActivityId = "341";
    public View mProgressView, mContentView,mProgressRView, mContentRView;

    public Button mDateInputButton;
    private TextView lblView,mIClaimTTText;
    private Spinner spinnerMonth,spinnerYear;
    private static SpinnerSet[] yearsSet,monthsSet;
    private SpinnerSetAdapter adapterS,adapterM;
    private int selectedYear,selectedMonth;
    private RecyclerView recyclerViewQ;
    public MLearnerClaimObj rDataObj = new MLearnerClaimObj();
    private Spinner inputSpinnerStatus, inputSpinnerastudentList;
    String attendance_id,student_id,generator,studentName;

    private static SpinnerSet[] approvalTypeMasters,studentSpin;
    public SpinnerSetAdapter adapterStatus,adapterStudent;
    private int selectedStudent,selectedGrantID,selectedStatus;

    private List<MLearnerClaimObj.Item> rDataObjList = null;
    private static final String TAG = "WHAT_IS_ERROR";
    TextView txtStudentName;

    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cTAId){
        baseApcContext = cnt;
        CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        printLogs(lt,"setBaseApcContextParent","weAreIn");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);
        Bundle activeInputUri = getIntent().getExtras();
        student_id = activeInputUri.getString("student_id");
        generator = activeInputUri.getString("generator");
        studentName = activeInputUri.getString("studentName");
        printLogs(LogTag,"onCreate","student_id_VALUE "+student_id);
        printLogs(LogTag,"onCreate","generator_VALUE "+generator);
        printLogs(LogTag,"onCreate","generator_VALUE "+studentName);
        bootStrapInit();
    }

    @Override
    protected void bootStrapInit() {
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext,activityIn);
        if(isConnected ==true) {
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);
            initMenusCustom(ActivityId,baseApcContext,activityIn);
            initBackBtn();
            fetchVersionData();
            verifyVersion();
            internetChangeBroadCast();
            initializeViews();
            showProgress(true,mContentView,mProgressView);
            initializeLabels();
            initializeListeners();
            userToken =userSessionObj.getToken();
            syncToken(baseApcContext,activityIn);
            if(TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }

            callDataApi();
            fetchData();
            initializeInputs();
            callHeaderBuilder();


            showProgress(false,mContentView,mProgressView);
        }
    }

    @Override
    protected void initializeLabels(){

        String Label = getLabelFromDb("b_341_filter",R.string.b_341_filter);
        mDateInputButton.setText(Label);

   /*     Label = getLabelFromDb("l_341_Claim",R.string.l_341_Claim);
        lblView = (TextView)findViewById(R.id.lblClaim);
        lblView.setText(Label);*/

        Label = getLabelFromDb("h_341",R.string.h_341);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);
    }

    @Override
    protected void internetChangeBroadCast(){
        printLogs(LogTag,"internetChangeBroadCast","init");
        registerBroadcastIC();
    }
    @Override
    protected void fetchVersionData(){
        printLogs(LogTag,"fetchVersionData","exitConnected");
        syncUpdates(baseApcContext,activityIn);
    }
    @Override
    protected void verifyVersion(){
        printLogs(LogTag,"verifyVersion","init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag,"verifyVersion", "isUpdate " + isUpdate);
        printLogs(LogTag,"verifyVersion", "isUpdate " + utilSessionObj.getIsUpdateRequired());
        if(isUpdate){
            Intent intent = new Intent(MLearnerClaimList.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void initializeViews() {
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mContentRView = findViewById(R.id.content_container_r);
        mProgressRView = findViewById(R.id.progress_bar_r);
        txtStudentName = findViewById(R.id.lblClaim);

        mDateInputButton = (Button) findViewById(R.id.btnFilterData);
        spinnerMonth = (Spinner) findViewById(R.id.inputSpinnerMonth);
        spinnerYear = (Spinner) findViewById(R.id.inputSpinnerYear);
        inputSpinnerastudentList = (Spinner) findViewById(R.id.inputSpinnerastudentList);

        mIClaimTTText = (TextView) findViewById(R.id.lblmClaimTT);

        rDataObjList = rDataObj.getITEMS();
        recyclerViewQ = (RecyclerView) findViewById(R.id.rVmClaim);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);

        View recyclerView = findViewById(R.id.rVmClaim);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        txtStudentName.setText(studentName);
    }

    protected void initializeInputs() {
        printLogs(LogTag,"initializeInputs","init");

    }

    @Override
    protected void initializeListeners() {
        printLogs(LogTag,"init","Listner");
    }
      public void validateInput() {
          printLogs(LogTag,"init","init");
   }

    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","a_m_learner_claim");
        setContentView(R.layout.a_m_learner_claim);
    }
        public void callHeaderBuilder(){

        String tHeader3 = getLabelFromDb("l_claim_341_date",R.string.l_claim_341_date);
        String tHeader4 = getLabelFromDb("l_claim_341_month",R.string.l_claim_341_month);
        String tHeader5 = getLabelFromDb("l_claim_341_approvedAmt",R.string.l_claim_341_approvedAmt);
        String tHeader6= getLabelFromDb("l_claim_341_leave_",R.string.l_claim_341_daysWorked);
        String tHeader7= getLabelFromDb("l_claim_341_leave_",R.string.l_claim_341_leave_);
        String tHeader8= getLabelFromDb("l_claim_341_absent",R.string.l_claim_341_absent);
        String tHeader9= getLabelFromDb("l_claim_341_m_approval",R.string.l_claim_341_m_approval);
        String tHeader10= getLabelFromDb("l_claim_341_m_approval_date",R.string.l_claim_341_m_approval_date);
        String tHeader11= getLabelFromDb("l_claim_341_m_approved_ammount",R.string.l_claim_341_m_approved_ammount);
        String tHeader12= getLabelFromDb("l_claim_341_a_approval",R.string.l_claim_341_a_approval);
        String tHeader13= getLabelFromDb("l_claim_341_a_date",R.string.l_claim_341_a_date);
        String tHeader14= "";
        String tHeader15= "";
        String tHeader16= "";
        String tHeader17= "";
        String tHeader18= getLabelFromDb("l_claim_341_a_year",R.string.l_claim_341_a_year);
        rDataObj.addItem(rDataObj.createItem(0,"",tHeader3,tHeader4,tHeader5,tHeader6,tHeader7,tHeader8,
                tHeader9,tHeader10,tHeader11,tHeader12,tHeader13,tHeader14,"","",tHeader14,tHeader15,tHeader16,tHeader17,tHeader18));

    }
    public void callDataApi(){
        userToken = userSessionObj.getToken();
        if(!TextUtils.isEmpty(userToken)) {
            printLogs(LogTag,"callDataApi","Token Update");
            syncUserData(baseApcContext, activityIn);
            syncMentorData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn,ActivityId);
        }
    }
    public void fetchData(){
       String token = userSessionObj.getToken();
      String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.GA_CLAIM_LERNER_LISTING;
      FINAL_URL=FINAL_URL+"/token/"+token+"/student_id/"+student_id ;
      printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,FINAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject outputJson = null;
                printLogs(LogTag,"fetchData","RESPONSE : "+response);
                try {
                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        for (int i = 0; i < dataM.length(); i++) {
                            int pos = i+1;
                            JSONObject rec = dataM.getJSONObject(i);
                            String aId2 = rec.getString("grant_id");
                            String claDate3 = rec.getString("claimdate");
                            String claMonth4 = rec.getString("cmonth");
                            String cla_ammount5 = rec.getString("s_m_s_stipend");
                            String dayswork6 = rec.getString("attendance_count");
                            String leavecount7 = rec.getString("leave_count");
                            String absentcount8 = rec.getString("absent_count");
                            String m_status9 = rec.getString("mentor");
                            String m_date10 = rec.getString("s_m_s_mentor_approve_date");
                            String m_ammount_status11 = rec.getString("aby");
                            String admin_approve_ammount12 = rec.getString("aamount");
                            String admin_approve_date13 = rec.getString("pdate");

                            String claStudentId14 = rec.getString("stuid");
                            String claGrantId15 = rec.getString("grant_id");
                            String claStatus16 = rec.getString("s_s_g_status");
                            String present_type17 = rec.getString("present_type");
                            String leave_type18 = rec.getString("leave_type");
                            String absent_type19 = rec.getString("absent_type");
                            String month20 = rec.getString("m");
                            String year21 = rec.getString("year");
                            rDataObj.addItem(rDataObj.createItem(pos,aId2,claDate3,claMonth4,cla_ammount5,dayswork6,leavecount7,
                                    absentcount8,m_status9,m_date10,m_ammount_status11,admin_approve_ammount12,admin_approve_date13,
                                    claStudentId14, claGrantId15, claStatus16 ,present_type17,leave_type18,absent_type19,month20,year21));
                            showList();
                        }
                        showProgress(false,mContentRView,mProgressRView);
                    }
                        else if(Status.equals("2")) {
                        showProgress(false,mContentRView,mProgressRView);
                    }
                    else{
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-102";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
//                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                }

                catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false,mContentView,mProgressView);
                    String sTitle="Error :"+ActivityId+"-103";
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
                        String sTitle="Error :"+ActivityId+"-104";
                        String sMessage=getLabelFromDb("error_341_104",R.string.error_341_104);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                        printLogs(LogTag,"fetchData","error_341_104 : "+error.getMessage());
                        printLogs(LogTag,"fetchData","error_341_104 : "+error.toString());
                        printLogs(LogTag,"fetchData","error_341_104 : "+error.getLocalizedMessage());
                    }
                }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(MLearnerClaimList.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new MLearnerClaimAdapter(rDataObjList,baseApcContext,activityIn,studentName,this));
    }

    public void showList(){
        List<MLearnerClaimObj.Item> ClaimData = rDataObj.getITEMS();
        MLearnerClaimAdapter adapter = new MLearnerClaimAdapter(ClaimData,baseApcContext,activityIn,studentName,this);
        recyclerViewQ.setAdapter(adapter);
        showProgress(false,mContentRView,mProgressRView);
    }

    public String getStudentName(String studentName){
        return studentName;
    }
    public String getStudentId(){
        return student_id;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Bundle inputUri = new Bundle();
            inputUri.putString("student_id", student_id);
            inputUri.putString("generator", generator);
            inputUri.putString("m_student_name", studentName);
            Intent intent = new Intent(MLearnerClaimList.this, MPendingClaimsA.class);
            printLogs(LogTag,"onOptionsItemSelected","MPendingClaimsA");
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
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcastIC();
    }
}


