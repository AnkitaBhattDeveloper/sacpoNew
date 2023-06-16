package za.co.sacpo.grantportal.xCubeStudent.upload;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.Objects;

import za.co.sacpo.grantportal.AppUpdatedA;
import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.adapter.DCenterAdapter;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.component.Utils;
import za.co.sacpo.grantportal.xCubeLib.dataObj.DCenterObj;
import za.co.sacpo.grantportal.xCubeLib.db.docCenterListAdapter;
import za.co.sacpo.grantportal.xCubeLib.db.docCenterListArray;
import za.co.sacpo.grantportal.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grantportal.xCubeStudent.SDashboardDA;

public class DocumentCenterListA extends BaseAPCPrivate {
    private String ActivityId="S193";
    public View mProgressView, mContentView,mProgressRView, mContentRView,heading;
    public TextView lblView,activity_heading;
    public Bundle activeUri;
    public String generator;
    private RecyclerView recyclerViewQ;
    public DCenterObj rDataObj = new DCenterObj();
    private List<DCenterObj.Item> rDataObjList = null;
    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId){
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
        printLogs(LogTag,"onCreate","init");
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
            Intent intent = new Intent(DocumentCenterListA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

    }
    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","a_current_attendance");
        setContentView(R.layout.a_d_center);
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
        recyclerViewQ = findViewById(R.id.rVAttendance);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);
        setupRecyclerView(recyclerViewQ);
        printLogs(LogTag,"initializeViews","exit");
    }
    @Override
    protected void initializeLabels(){
        printLogs(LogTag,"initializeLabels","init");
        String  Label = getLabelFromDb("h_S193",R.string.h_S193);
        activity_heading.setText(Label);
        activity_heading.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
        }
    }
    @Override
    protected void initializeInputs(){
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
            syncStudentData(baseApcContext, activityIn);
            syncGrantData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn,ActivityId);
        }
    }

    public void fetchData(){
        showProgress(true,mContentRView,mProgressRView);
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.REF_S_193;
        FINAL_URL=FINAL_URL+"?token="+token;
        printLogs(LogTag,"fetchData","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchData", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        docCenterListAdapter adapter = new docCenterListAdapter(getApplicationContext());
                        adapter.truncate();
                        ArrayList<docCenterListArray> ArrayList = new ArrayList<>();
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        for (int i = 0; i < dataM.length(); i++) {
                            int pos = i+1;
                            JSONObject rec = dataM.getJSONObject(i);
                            int aId2 = Integer.parseInt(rec.getString("id"));
                            String aName3 = rec.getString("name_of_document");
                            String aDay4 ="";
                            String aPrevious5 = "";
                           // String aPrevious5 = rec.getString("previous_document");
                          //  String aIsPrevious6 = "";
                            String aIsPrevious6 = rec.getString("previous_document_btn");
                            //String aIsPrevious6="1";
                            String aIsUpload7 = rec.getString("upload_document_link");
                            String aUploadType8 = rec.getString("upload_document_type");
                            //String aUploadType8 ="3";
                            String aIsDownload9 = rec.getString("download_document_btn");
                            String aDownload10 = rec.getString("download_document_link");
                            //String aIsDownload9 = "1";
                            rDataObj.addItem(rDataObj.createItem(pos,aId2,aName3,aDay4,aPrevious5,aIsPrevious6,aIsUpload7,aUploadType8,aIsDownload9,aDownload10));

                            ArrayList.add(new docCenterListArray(String.valueOf(aId2),rec.getString("student_id"),
                                    rec.getString("grant_id"),rec.getString("previous_document"),
                                    aIsPrevious6,aName3,rec.getString("d_c_doc_status"),rec.getString("d_c_doc_type"),
                                    aUploadType8,aDownload10,aIsDownload9,aIsUpload7));



                        }

                        adapter.insert(ArrayList);
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
                        printLogs(LogTag, "fetchData", "VolleyError : " + error);
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

        printLogs(LogTag, "fetchOfflineData", "init");
        docCenterListAdapter adapter  =new docCenterListAdapter(getApplicationContext());
        List<docCenterListArray> adapterAll = adapter.getAll();
        for (int i = 0; i < adapterAll.size(); i++) {
            int pos = i+1;
            int aId2 = Integer.parseInt(adapterAll.get(i).getId());
            String aName3 = adapterAll.get(i).getName_of_document();
            String aDay4 ="";
            String aPrevious5 = "";
             //String aPrevious5 = adapterAll.get(i).getPrevious_document();
            //  String aIsPrevious6 = "";
            String aIsPrevious6 = adapterAll.get(i).getPrevious_document_btn();
            //String aIsPrevious6="1";
            String aIsUpload7 = adapterAll.get(i).getUpload_document_link();
            String aUploadType8 = adapterAll.get(i).getUpload_document_type();
            //String aUploadType8 ="3";
            String aIsDownload9 = adapterAll.get(i).getDownload_document_btn();
            String aDownload10 = adapterAll.get(i).getDownload_document_link();
            //String aIsDownload9 = "1";
            rDataObj.addItem(rDataObj.createItem(pos,aId2,aName3,aDay4,aPrevious5,aIsPrevious6,aIsUpload7,aUploadType8,aIsDownload9,aDownload10));
        }
        showList();

        showProgress(false,mContentRView,mProgressRView);
        printLogs(LogTag, "fetchOfflineData", "exit");


    }

    public void callHeaderBuilder(){
        printLogs(LogTag,"callHeaderBuilder","init");
        String tHeader3 = getLabelFromDb("t_head_S193_name",R.string.t_head_S193_name);
        String tHeader4 ="";
        String tHeader5 = getLabelFromDb("t_head_S193_previous",R.string.t_head_S193_previous);
        String tHeader6 = "";
        String tHeader7 = getLabelFromDb("t_head_S193_upload",R.string.t_head_S193_upload);
        String tHeader8 = "";
        String tHeader9 = "";
        String tHeader10 = getLabelFromDb("t_head_S193_download",R.string.t_head_S193_download);
        //aName3,aDay4,aPrevious5,aIsPrevious6,aIsUpload7,aUploadType8,aIsDownload9,aDownload10
        rDataObj.addItem(rDataObj.createItem(0,0,tHeader3,tHeader4,tHeader5,tHeader6,tHeader7,tHeader8,tHeader9,tHeader10));
        printLogs(LogTag,"callHeaderBuilder","init");
        String tHeader13 = getLabelFromDb("l_S193_compositeupload",R.string.l_S193_compositeupload);
        String tHeader14 ="";
        String tHeader15 = "";
        String tHeader16 = "";
        String tHeader17 = getLabelFromDb("t_head_S193_upload",R.string.t_head_S193_upload);
        String tHeader18 = "";
        String tHeader19 = "";
        String tHeader110 = "";
        //aName3,aDay4,aPrevious5,aIsPrevious6,aIsUpload7,aUploadType8,aIsDownload9,aDownload10
        rDataObj.addItem(rDataObj.createItem(1,0,tHeader13,tHeader14,tHeader15,tHeader16,tHeader17,tHeader18,tHeader19,tHeader110));

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        printLogs(LogTag,"setupRecyclerView","init");
        recyclerView.setAdapter(new DCenterAdapter(rDataObjList,baseApcContext,activityIn));
    }
    public void showList(){
        printLogs(LogTag,"showList","init");
        List<DCenterObj.Item> AttData = rDataObj.getITEMS();
        DCenterAdapter adapter = new DCenterAdapter(AttData,baseApcContext,activityIn);
        recyclerViewQ.setAdapter(adapter);
        showProgress(false,mContentRView,mProgressRView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(DocumentCenterListA.this, SDashboardDA.class);
            printLogs(LogTag,"onOptionsItemSelected","SDashboardDA");
            startActivity(intent);
            finish();
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DocumentCenterListA.this, SDashboardDA.class);
            printLogs(LogTag,"onOptionsItemSelected","SDashboardDA");
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
