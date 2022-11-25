package za.co.sacpo.grant.xCubeStudent.claims;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.adapter.SNewClaimAdapter;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dataObj.SNewClaimObj;
import za.co.sacpo.grant.xCubeLib.db.existingLeaveListAdapter;
import za.co.sacpo.grant.xCubeLib.db.existingLeaveListArray;
import za.co.sacpo.grant.xCubeLib.db.pastClaimListAdapter;
import za.co.sacpo.grant.xCubeLib.db.pastClaimListArray;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsGrantSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsStudentSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeStudent.SDashboardDA;

public class SPastClaimDA extends BaseAPCPrivate {

    public String ActivityId = "S126";
    public View mProgressView, mContentView,mProgressRView, mContentRView,heading;
    ProgressBar download_progress_bar;
    public Button btnSubmitClaim;
    private TextView lblView,mIGrantNameText,mIStartDateText,mIEndDateText,mIClaimTTText,tv_progress;
    public String generator,is_claim_submitted,is_report_due;
    // declare the dialog as a member field of your activity
    public ProgressDialog mProgressDialog;
    LinearLayout ll_download_progress;


    private RecyclerView recyclerViewQ;
    public SNewClaimObj rDataObj = new SNewClaimObj();
    private List<SNewClaimObj.Item> rDataObjList = null;
    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId){
        baseApcContext = cnt;CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        printLogs(lt,"setBaseApcContextParent","weAreIn");
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);
        bootStrapInit();
        Intent inputIntent = getIntent();
        Bundle activeInputUri = getIntent().getExtras();
        is_claim_submitted= "1";
        is_report_due="1";
        if (inputIntent.hasExtra("is_claim_submitted")) {
            is_claim_submitted = activeInputUri.getString("is_claim_submitted");
            printLogs(LogTag,"onCreate","is_claim_submitted "+is_claim_submitted);
        }
        if (inputIntent.hasExtra("is_report_due")) {
            is_report_due = activeInputUri.getString("is_report_due");
            printLogs(LogTag,"onCreate","is_report_due "+is_report_due);
        }
        if (inputIntent.hasExtra("generator")) {
            generator = activeInputUri.getString("generator");
            printLogs(LogTag,"onCreate","GENERATOR ID "+generator);
        }

    }
    @Override
    protected void bootStrapInit() {
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext,activityIn);
        if(isConnected) {
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);
            initMenusCustom(ActivityId,baseApcContext,activityIn);
            initBackBtn();
            internetChangeBroadCast();
            fetchVersionData();
            verifyVersion();
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
            initializeInputs();
            callHeaderBuilder();
            fetchData();
            //showProgress(false,mContentView,mProgressView);
        }else{
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);
            initMenusCustom(ActivityId,baseApcContext,activityIn);
            initBackBtn();
            internetChangeBroadCast();
            fetchVersionData();
            verifyVersion();
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
            initializeInputs();
            callHeaderBuilder();
            fetchOfflineData();
            //showProgress(false,mContentView,mProgressView);
        }
    }

    @Override
    protected void internetChangeBroadCast() {
        registerBroadcastIC();
    }
    protected void fetchVersionData(){
        printLogs(LogTag,"fetchVersionData","exitConnected");
        syncUpdates(baseApcContext,activityIn);
    }
    protected void verifyVersion(){
        printLogs(LogTag,"verifyVersion","init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag,"verifyVersion","isUpdate "+isUpdate);
        printLogs(LogTag,"verifyVersion","isUpdate "+ utilSessionObj.getIsUpdateRequired());
        if(isUpdate){
            Intent intent = new Intent(SPastClaimDA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","a_claims");
        setContentView(R.layout.a_past_claims);
    }


    @Override
    protected void initializeViews() {
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        download_progress_bar = findViewById(R.id.download_progress_bar);
        tv_progress = findViewById(R.id.tv_progress);
        ll_download_progress = findViewById(R.id.ll_download_progress);
        heading = findViewById(R.id.heading);

        btnSubmitClaim = (Button) findViewById(R.id.btnSubmitClaim);
        mIGrantNameText = (TextView) findViewById(R.id.iGrantName);
        mIStartDateText = (TextView) findViewById(R.id.iStartDate);
        mIEndDateText = (TextView) findViewById(R.id.iEndDate);
        mIClaimTTText = (TextView) findViewById(R.id.lblClaimTT);
        mIClaimTTText.setText(Html.fromHtml(getString(R.string.question)));

        rDataObjList = rDataObj.getITEMS();
        recyclerViewQ = (RecyclerView) findViewById(R.id.rVClaim);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);
        setupRecyclerView(recyclerViewQ);
        printLogs(LogTag,"initializeViews","exit");

    }
    @Override
    protected void initializeLabels(){
        String Label = getLabelFromDb("b_S126_submit_claim",R.string.b_S126_submit_claim);
        btnSubmitClaim.setText(Label);

        Label = getLabelFromDb("l_S126_Claim",R.string.l_S126_Claim);
        lblView = (TextView)findViewById(R.id.lblClaim);
        lblView.setText(Label);

        Label = getLabelFromDb("h_S126",R.string.h_S126);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            btnSubmitClaim.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));

        }

    }
    @Override
    protected void initializeInputs(){
        printLogs(LogTag,"initializeInputs","init");
        studentSessionObj = new OlumsStudentSession(baseApcContext);
        boolean isGrantActive= studentSessionObj.getIsActiveGrant();
        if(isGrantActive) {
            printLogs(LogTag,"isGrantActive","if Condition");
            grantSessionObj = new OlumsGrantSession(baseApcContext);
            String StartDate = grantSessionObj.getGrantSDate();
            String EndDate = grantSessionObj.getGrantEDate();
            String grantName = grantSessionObj.getGrantName();
            mIGrantNameText.setText(grantName);
            mIStartDateText.setText(StartDate);
            mIEndDateText.setText(EndDate);

        }
        // instantiate it within the onCreate method
        mProgressDialog = new ProgressDialog(SPastClaimDA.this);
    }


    @Override
    protected void initializeListeners() {
        btnSubmitClaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int is_claimed = Integer.parseInt(is_claim_submitted);
                int is_reportOverDue = Integer.parseInt(is_report_due);
                //is_claimed=0;
                if(is_claimed==1){
                    ///OVERLAY IF NO CLAIM TO SUBMIT
                    String sTitle=getLabelFromDb("m_S126_title",R.string.m_S126_title);
                    String sMessage=getLabelFromDb("m_S126_message",R.string.m_S126_message);
                    String sButtonLabelClose="Close";
                    ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    //ErrorDialog.showSuccessDialogApplyLeave(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose,thisClass);
                }
                else {
                    if(is_reportOverDue==1){
                        inBundle = new Bundle();
                        Intent intent = new Intent(SPastClaimDA.this, SPendingReportsDA.class);
                        inBundle.putString("generator", ActivityId);
                        inBundle.putString("is_claim_submitted", String.valueOf(is_claim_submitted));
                        inBundle.putString("is_report_due", String.valueOf(is_report_due));
                        intent.putExtras(inBundle);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        inBundle = new Bundle();
                        Intent intent = new Intent(SPastClaimDA.this, SAttSummaryList.class);
                        inBundle.putString("generator", ActivityId);
                        inBundle.putString("is_claim_submitted", String.valueOf(is_claim_submitted));
                        inBundle.putString("is_report_due", String.valueOf(is_report_due));
                        intent.putExtras(inBundle);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
        mIClaimTTText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sToolTip=getLabelFromDb("tt_S126_scroll",R.string.tt_S126_scroll);
                showTooltip(mIClaimTTText,sToolTip,4);
            }
        });


    }

    public void callHeaderBuilder(){
        String tHeader3 = getLabelFromDb("l_claim_S126_year4",R.string.l_claim_S126_year4);
        String tHeader4 = getLabelFromDb("l_claim_S126_month4",R.string.l_claim_S126_month4);
        String tHeader5 = getLabelFromDb("l_claim_S126_date_5",R.string.l_claim_S126_date_5);
        String tHeader6 = getLabelFromDb("l_claim_S126_c_amount_6",R.string.l_claim_S126_c_amount_6);
        String tHeader7= getLabelFromDb("l_claim_S126_c_s_status",R.string.l_claim_S126_c_s_status);
        String tHeader8= getLabelFromDb("l_claim_S126_grant_admin_status7",R.string.l_claim_S126_grant_admin_status7);
        String tHeader11= getLabelFromDb("l_claim_S126_DUCF",R.string.l_claim_S126_DUCF);
        String tHeader15= getLabelFromDb("l_claim_S126_UCSF",R.string.l_claim_S126_UCSF);
        String tHeader14= getLabelFromDb("l_claim_S126_DCSF",R.string.l_claim_S126_DCSF);
        String tHeader12= "";
        String tHeader13= "";
        String tHeader9= "";
        String tHeader10= "";

        rDataObj.addItem(rDataObj.createItem(0,"",tHeader3,tHeader4,tHeader5,tHeader6,tHeader7,
                tHeader8,tHeader9,tHeader10,tHeader11,tHeader12,tHeader13,tHeader14,tHeader15));

    }

    public void callDataApi(){
        userToken = userSessionObj.getToken();
        if(!TextUtils.isEmpty(userToken)) {
            syncUserData(baseApcContext, activityIn);
            syncStudentData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn,ActivityId);

        }
    }

        public void fetchData(){
        String token = userSessionObj.getToken();
        String grantId = studentSessionObj.getGrantId();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_126+"?token="+token;

        printLogs(LogTag,"fetchData","URL : "+FINAL_URL);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject jsonObject= new JSONObject(String.valueOf(response));
                        printLogs(LogTag, "fetchData", "response : " + response);
                        String Status = jsonObject.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        pastClaimListAdapter adapter = new pastClaimListAdapter(getApplicationContext());
                        adapter.truncate();
                        ArrayList<pastClaimListArray> ArrayList = new ArrayList<>();
                        JSONArray dataM = jsonObject.getJSONArray(KEY_DATA);
                        for (int i = 0; i < dataM.length(); i++) {
                            int pos = i+1;
                            JSONObject rec = dataM.getJSONObject(i);
                            printLogs(LogTag,"fetchData","RESPONSE : "+pos+rec.toString());
                            String aId2 = rec.getString("s_m_s_id");

                            String claimYear3 = rec.getString("year");
                            String claimMonth4 = rec.getString("month");
                            String date5 = rec.getString("date_of_claim");
                            String claimAmount6 = rec.getString("claimed_ammount");
                            String supervisorStatus7 = rec.getString("supervisor_status");
                            String grantAdminStatus8 = rec.getString("grant_admin_status");
                            String isPendingSupervisorStatus9 = rec.getString("supervisor_status_color");
                            String isPendingGrantAdminStatus10 = rec.getString("grant_admin_status_color");
                            String downloadUSCF11 = rec.getString("download_unsigned_claim_form");
                            String isDownloadUSCF12 = rec.getString("download_unsigned_claim_form_btn");
                            String isDownloadSCF13 = rec.getString("download_sign_claim_form_btn");
                            String downloadSCF14 = rec.getString("download_signed_claim_form");
                            String isUSCF15 = rec.getString("upload_signed_claim_form_btn");

                            rDataObj.addItem(rDataObj.createItem(pos,aId2,claimYear3,claimMonth4,date5,claimAmount6,supervisorStatus7,grantAdminStatus8,isPendingSupervisorStatus9,isPendingGrantAdminStatus10,downloadUSCF11,isDownloadUSCF12,isDownloadSCF13,downloadSCF14,isUSCF15));

                           ArrayList.add(new pastClaimListArray(aId2,rec.getString("s_m_s_id"),
                                   rec.getString("s_m_s_id"),claimYear3,claimMonth4,date5,
                                   claimAmount6,supervisorStatus7,isPendingSupervisorStatus9,grantAdminStatus8,
                                   isPendingGrantAdminStatus10,isDownloadUSCF12,downloadUSCF11,isUSCF15,
                                   downloadSCF14,isDownloadSCF13));


                        }
                        adapter.insert(ArrayList);
                        showList();
                        showProgress(false,mContentView,mProgressView);
                       }
                    else if(Status.equals("2")) {
                        showProgress(false,mContentView,mProgressView);
                    }
                    else{
                        String sTitle="Error :"+ActivityId+"-102";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                        showProgress(false,mContentView,mProgressView);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    printLogs(LogTag,"fetchData","error_try_again : "+e.getMessage());
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
                        String sMessage=getLabelFromDb("error_S126_104",R.string.error_S126_104);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

                    }
                }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Accept", "*/*");
                    // params.put("Content-Type","application/json");

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


    void fetchOfflineData(){
        printLogs(LogTag, "fetchOfflineData", "init");
        pastClaimListAdapter adapter  =new pastClaimListAdapter(getApplicationContext());
        List<pastClaimListArray> adapterAll = adapter.getAll();
        for (int i = 0; i < adapterAll.size(); i++) {
            int pos = i+1;

            String aId2 = adapterAll.get(i).getS_m_s_id();
            String claimYear3 = adapterAll.get(i).getYear();
            String claimMonth4 = adapterAll.get(i).getMonth();
            String date5 = adapterAll.get(i).getDate_of_claim();
            String claimAmount6 = adapterAll.get(i).getClaimed_ammount();
            String supervisorStatus7 = adapterAll.get(i).getSupervisor_status();
            String grantAdminStatus8 = adapterAll.get(i).getGrant_admin_status();
            String isPendingSupervisorStatus9 = adapterAll.get(i).getSupervisor_status_color();
            String isPendingGrantAdminStatus10 = adapterAll.get(i).getGrant_admin_status_color();
            String downloadUSCF11 = adapterAll.get(i).getDownload_unsigned_claim_form();
            String isDownloadUSCF12 = adapterAll.get(i).getDownload_unsigned_claim_form_btn();
            String isDownloadSCF13 = adapterAll.get(i).getDownload_sign_claim_form_btn();
            String downloadSCF14 = adapterAll.get(i).getDownload_signed_claim_form();
            String isUSCF15 = adapterAll.get(i).getUpload_signed_claim_form_btn();

            rDataObj.addItem(rDataObj.createItem(pos,aId2,claimYear3,claimMonth4,date5,claimAmount6,supervisorStatus7,grantAdminStatus8,isPendingSupervisorStatus9,isPendingGrantAdminStatus10,downloadUSCF11,isDownloadUSCF12,isDownloadSCF13,downloadSCF14,isUSCF15));



        }
        showList();
        showProgress(false,mContentView,mProgressView);
        printLogs(LogTag, "fetchOfflineData", "exit");


    }


        private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
            recyclerView.setAdapter(new SNewClaimAdapter(rDataObjList,baseApcContext,activityIn,ll_download_progress,download_progress_bar,tv_progress));
        }
    public void showList(){
        List<SNewClaimObj.Item> ClaimData = rDataObj.getITEMS();
        SNewClaimAdapter adapter = new SNewClaimAdapter(ClaimData,baseApcContext,activityIn, ll_download_progress,download_progress_bar,tv_progress);
        recyclerViewQ.setAdapter(adapter);
        showProgress(false,mContentView,mProgressView);
    }
public void downloadPDF(Context context, String downloadurl, LinearLayout ll_download_progress, ProgressBar mProgressView, TextView tv_progress){
    DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

    Uri uri = Uri.parse(downloadurl);
    String path=uri.getPath();
    String filename=path.substring(path.lastIndexOf("/")+1);
  //  Uri uri = Uri.parse("https://decapoda.nhm.org/pdfs/25900/25900-001.pdf");
    DownloadManager.Request request = new DownloadManager.Request(uri);
    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
    request.setDestinationInExternalPublicDir("/Download",filename);
    long downloadReference = manager.enqueue(request);
    Timer progressTimer = new Timer();
    progressTimer.schedule(new TimerTask() {
        @Override
        public void run() {
            DownloadManager.Query downloadQuery = new DownloadManager.Query();
            downloadQuery.setFilterById(downloadReference);

            Cursor cursor = manager.query(downloadQuery);
            if (cursor.moveToFirst()) { // this "if" is crucial to prevent a kind of error
                final int downloadedBytes = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                final int totalBytes = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)); // integer is enough for files under 2GB
                cursor.close();

                final float downloadProgress = downloadedBytes * 100f / totalBytes;
                if(downloadProgress > 99.9) // stop repeating timer (it's also useful for error prevention)
                    progressTimer.cancel();

                runOnUiThread(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        @SuppressLint("DefaultLocale") String formatteddownloadProgress = String.format("%.02f", downloadProgress);
                        tv_progress.setText(downloadedBytes/1000 + "/" + totalBytes/1000 +" KB"+ "\n" + formatteddownloadProgress + "%");
                        mProgressView.setProgress((int) downloadProgress);
                        ll_download_progress.setVisibility(View.VISIBLE);
                        if (downloadProgress == 100){
                            ll_download_progress.setVisibility(View.GONE);
                        }
                    }
                });
            }
        }
    }, 0, 1000);
    ll_download_progress.setVisibility(View.GONE);
}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(SPastClaimDA.this,SDashboardDA.class);
            printLogs(LogTag,"onOptionsItemSelected","sDashboard");
            startActivity(intent);
            finish();
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SPastClaimDA.this,SDashboardDA.class);
        printLogs(LogTag,"onOptionsItemSelected","sDashboard");
        startActivity(intent);
        finish();
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