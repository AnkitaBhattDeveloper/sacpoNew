package za.co.sacpo.grantportal.xCubeMentor.upload;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import za.co.sacpo.grantportal.AppUpdatedA;
import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.adapter.MDCenterAdapter;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.component.Utils;
import za.co.sacpo.grantportal.xCubeLib.dataObj.MDCenterObj;
import za.co.sacpo.grantportal.xCubeLib.db.mDocCenterListAdapter;
import za.co.sacpo.grantportal.xCubeLib.db.mDocCenterListArray;
import za.co.sacpo.grantportal.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grantportal.xCubeMentor.MDashboardDA;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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

public class MDocumentCenterA extends BaseAPCPrivate {
    private String ActivityId="M199";
    public View mProgressView, mContentView,mProgressRView, mContentRView,heading;
    public TextView lblView,activity_heading;
    private String generator,student_id,student_name;
    public Bundle activeUri;
    private RecyclerView recyclerViewQ;
    public MDCenterObj rDataObj = new MDCenterObj();
    private List<MDCenterObj.Item> rDataObjList = null;
    MDocumentCenterA thisClass;
    @Override
    protected void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cTAId) {
        baseApcContext = cnt;
        CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        CAId = CAId;
        thisClass = this ;
        printLogs(lt,"setBaseApcContextParent","weAreIn");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);
        printLogs(LogTag,"onCreate","init");
        Intent inputIntent = getIntent();
        inBundle = getIntent().getExtras();
        student_id="";
        student_name="";
        if (inputIntent.hasExtra("student_id")) {
            student_id = inBundle.getString("student_id");
        }
        if (inputIntent.hasExtra("m_student_name")) {
            student_name = inBundle.getString("m_student_name");
        }
        generator = inBundle.getString("generator");
        printLogs(LogTag,"onCreate","GROUP ID "+student_id+"/student_name/"+student_name);
        bootStrapInit();
    }

    @Override
    protected void bootStrapInit() {
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext,activityIn);
        if(isConnected) {
            printLogs(LogTag,"bootStrapInit","initConnected");
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);
            initMenusCustom(ActivityId,baseApcContext,activityIn);
            initBackBtn();
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
            showProgress(false,mContentView,mProgressView);
        }else{
            printLogs(LogTag,"bootStrapInit","initConnected");
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);
            initMenusCustom(ActivityId,baseApcContext,activityIn);
            initBackBtn();
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
            showProgress(false,mContentView,mProgressView);
        }
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
        printLogs(LogTag,"verifyVersion","isUpdate "+isUpdate);
        printLogs(LogTag,"verifyVersion","isUpdate "+ utilSessionObj.getIsUpdateRequired());
        if(isUpdate){
            Intent intent = new Intent(MDocumentCenterA.this, AppUpdatedA.class);
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
        setContentView(R.layout.a_m_document_center);
        printLogs(LogTag,"setLayoutXml","a_current_attendance");
    }

    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mContentRView = findViewById(R.id.content_container_r);
        mProgressRView = findViewById(R.id.progress_bar_r);
        heading = findViewById(R.id.heading);
        activity_heading = findViewById(R.id.activity_heading);
        rDataObjList = rDataObj.getITEMS();
        recyclerViewQ = (RecyclerView) findViewById(R.id.rVAttendance);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);
        setupRecyclerView(recyclerViewQ);
        printLogs(LogTag,"initializeViews","exit");
    }

    @Override
    protected void initializeLabels() {
        printLogs(LogTag,"initializeLabels","init");
        String  Label = getLabelFromDb("h_M199",R.string.h_M199);
        activity_heading.setText(Label);
        activity_heading.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));

        Label = getLabelFromDb("h_M199",R.string.h_M199);
        lblView = (TextView)findViewById(R.id.activity_heading);
        if(student_name.isEmpty()){
            Label=Label;
        }
        else {
            Label =Label+" \n\n"+student_name;
        }
        lblView.setText(Label);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
        }
        }

    @Override
    protected void initializeInputs() {
        printLogs(LogTag,"initializeInputs","init");
    }

    @Override
    protected void initializeListeners() {
        printLogs(LogTag,"initializeListeners","init");
    }

    public void callDataApi(){
        printLogs(LogTag,"callDataApi","init");
        userToken = userSessionObj.getToken();
        if(!TextUtils.isEmpty(userToken)) {
            printLogs(LogTag,"callDataApi","Token Update");
            syncUserData(baseApcContext, activityIn);
            syncMentorData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn,ActivityId);
        }
    }

    public void fetchData(){
        showProgress(true,mContentRView,mProgressRView);
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.REF_M_199;
        FINAL_URL=FINAL_URL+"?token="+token+"&student_id="+student_id;
        printLogs(LogTag,"fetchData","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchData", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if (Status.equals("1")) {


                        mDocCenterListAdapter adapter = new mDocCenterListAdapter(getApplicationContext());
                        adapter.truncate();

                        ArrayList<mDocCenterListArray> arrayList = new ArrayList<>();

                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        for (int i = 0; i < dataM.length(); i++) {
                            int pos = i+1;
                            JSONObject rec = dataM.getJSONObject(i);

                            int aId2 = Integer.parseInt(rec.getString("stipend_id"));
                           // int aId2 = pos;
                            String aYear3 = rec.getString("year");
                            String aMonth4 =rec.getString("monthName");
                            String aDownloadAttRegister5 = rec.getString("download_att_register");
                            String aIsDownloadAttRegister6 = rec.getString("is_download_att_register");
                            String aDownloadUnsignedClaimForm7 = "download_unsigned_claim_form";
                            String aIsDownloadUnsignedClaimForm8 = rec.getString("is_download_unsigned_claim_form");
                            String aUploadSignedClaimForm9 = rec.getString("upload_signed_claim_form");
                            String aDownloadSignedClaimForm10 = rec.getString("download_signed_claim_form");
                            String aIsDownloadSignedClaimForm11 = rec.getString("is_download_signed_claim_form");
                            String aMonthNumber12 = rec.getString("month");
                            //aYear3,aMonth4,aDownloadAttRegister5,aIsDownloadAttRegister6,aDownloadUnsignedClaimForm7,aIsDownloadUnsignedClaimForm8,aUploadSignedClaimForm9,aDownloadSignedClaimForm10,aIsDownloadSignedClaimForm11,aMonthNumber12
                            rDataObj.addItem(rDataObj.createItem(pos,aId2,aYear3,aMonth4,aDownloadAttRegister5,aIsDownloadAttRegister6,aDownloadUnsignedClaimForm7,aIsDownloadUnsignedClaimForm8,aUploadSignedClaimForm9,aDownloadSignedClaimForm10,aIsDownloadSignedClaimForm11,aMonthNumber12));

                            arrayList.add(new mDocCenterListArray(String.valueOf(aId2),aYear3,aMonth4,aDownloadAttRegister5,
                                    aIsDownloadAttRegister6,aDownloadUnsignedClaimForm7,aIsDownloadUnsignedClaimForm8,
                                    aUploadSignedClaimForm9,aDownloadSignedClaimForm10,aIsDownloadSignedClaimForm11,
                                    aMonthNumber12));


                        }
                        adapter.insert(arrayList);
                        showList();
                        showProgress(false,mContentRView,mProgressRView);
                    }
                    else if(Status.equals("2")) {
                        showProgress(false,mContentRView,mProgressRView);
                    }
                    else{
                        showProgress(false,mContentRView,mProgressRView);
                        String sTitle="Error :"+ActivityId+"-102";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false,mContentRView,mProgressRView);
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
                        showProgress(false,mContentRView,mProgressRView);
                        String sTitle="Error :"+ActivityId+"-104";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
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

        mDocCenterListAdapter adapter = new mDocCenterListAdapter(getApplicationContext());
        List<mDocCenterListArray> all = adapter.getAll();
        for (int i = 0; i < all.size(); i++) {
            int pos = i+1;
            int aId2 = Integer.parseInt(all.get(i).getStipend_id());
            // int aId2 = pos;
            String aYear3 = all.get(i).getYear();
            String aMonth4 =all.get(i).getMonthName();
            String aDownloadAttRegister5 = all.get(i).getDownload_att_register();
            String aIsDownloadAttRegister6 = all.get(i).getIs_download_att_register();
            String aDownloadUnsignedClaimForm7 = "download_unsigned_claim_form";
            String aIsDownloadUnsignedClaimForm8 = all.get(i).getIs_download_unsigned_claim_form();
            String aUploadSignedClaimForm9 = all.get(i).getUpload_signed_claim_form();
            String aDownloadSignedClaimForm10 = all.get(i).getDownload_signed_claim_form();
            String aIsDownloadSignedClaimForm11 = all.get(i).getIs_download_signed_claim_form();
            String aMonthNumber12 = all.get(i).getMonth();
            //aYear3,aMonth4,aDownloadAttRegister5,aIsDownloadAttRegister6,aDownloadUnsignedClaimForm7,aIsDownloadUnsignedClaimForm8,aUploadSignedClaimForm9,aDownloadSignedClaimForm10,aIsDownloadSignedClaimForm11,aMonthNumber12
            rDataObj.addItem(rDataObj.createItem(pos,aId2,aYear3,aMonth4,aDownloadAttRegister5,aIsDownloadAttRegister6,aDownloadUnsignedClaimForm7,aIsDownloadUnsignedClaimForm8,aUploadSignedClaimForm9,aDownloadSignedClaimForm10,aIsDownloadSignedClaimForm11,aMonthNumber12));

        }
        showList();
        showProgress(false,mContentRView,mProgressRView);

    }
    public void callHeaderBuilder(){
        printLogs(LogTag,"callHeaderBuilder","init");
        String tHeader3 = getLabelFromDb("t_head_M199_YEAR",R.string.t_head_M199_YEAR);
        String tHeader4 = getLabelFromDb("t_head_M199_MONTH",R.string.t_head_M199_MONTH);
        String tHeader8 ="";
        String tHeader5 = getLabelFromDb("t_head_M199_DAReg",R.string.t_head_M199_DAReg);
        String tHeader9 = getLabelFromDb("t_head_M199_upload",R.string.t_head_M199_upload);
        String tHeader6 = "";
        String tHeader7 = getLabelFromDb("t_head_M199_download",R.string.t_head_M199_download);
        String tHeader10 =getLabelFromDb("t_head_M199_dSClaimedForm",R.string.t_head_M199_dSClaimedForm);
        String tHeader11 = "";
        //aYear3,aMonth4,aDownloadAttRegister5,aIsDownloadAttRegister6,aDownloadUnsignedClaimForm7,aIsDownloadUnsignedClaimForm8,aUploadSignedClaimForm9,aDownloadSignedClaimForm10,aIsDownloadSignedClaimForm11,aMonthNumber12
        rDataObj.addItem(rDataObj.createItem(0,0,tHeader3,tHeader4,tHeader5,tHeader6,tHeader7,tHeader8,tHeader9,tHeader10,tHeader11,""));

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        printLogs(LogTag,"setupRecyclerView","init");
        recyclerView.setAdapter(new MDCenterAdapter(rDataObjList,baseApcContext,activityIn,thisClass));
    }
    public void showList(){
        printLogs(LogTag,"showList","init");
        List<MDCenterObj.Item> AttData = rDataObj.getITEMS();
        MDCenterAdapter adapter = new MDCenterAdapter(AttData,baseApcContext,activityIn,thisClass);
        recyclerViewQ.setAdapter(adapter);
        showProgress(false,mContentRView,mProgressRView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(MDocumentCenterA.this, MDashboardDA.class);
            printLogs(LogTag,"onOptionsItemSelected","SDashboardDA");
            startActivity(intent);
            finish();
        }
        return true;
    }

    public String getStudentName(){
        return student_name;
    }
    public String getStudentId(){

        return student_id;
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
