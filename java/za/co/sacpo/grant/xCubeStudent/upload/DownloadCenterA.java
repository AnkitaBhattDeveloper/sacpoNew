package za.co.sacpo.grant.xCubeStudent.upload;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.adapter.DownloadCenterAdapter;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dataObj.DownloadCenterObj;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;

public class DownloadCenterA extends BaseAPCPrivate{
    private String ActivityId = "S267";
    public View mProgressView, mContentView,mProgressRView, mContentRView;
    public TextView lblView,activity_heading,lblDocName;
    public Bundle activeUri;
    public String generator,doc_name,doc_type;
    private RecyclerView recyclerViewQ;
    public DownloadCenterObj rDataObj = new DownloadCenterObj();
    private List<DownloadCenterObj.Item> rDataObjList = null;
    DownloadCenterA thisClass;
    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId){
        baseApcContext = cnt;
        CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        CAId = CAId;
        this.thisClass= this;
        printLogs(lt,"setBaseApcContextParent","weAreIn");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);
        printLogs(LogTag,"onCreate","init");
        Intent inputIntent = getIntent();
        inBundle = getIntent().getExtras();
        doc_name="";
        doc_type="";
        if (inputIntent.hasExtra("doc_name")) {
            doc_name = inBundle.getString("doc_name");
        }
        if (inputIntent.hasExtra("doc_type")) {
            doc_type = inBundle.getString("doc_type");
        }
        generator = inBundle.getString("generator");
        printLogs(LogTag,"onCreate","GROUP ID "+doc_type+"/"+doc_name);
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
            Intent intent = new Intent(DownloadCenterA.this,AppUpdatedA.class);
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
        printLogs(LogTag,"setLayoutXml","a_download_center");
        setContentView(R.layout.a_download_center);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mContentRView = findViewById(R.id.content_container_r);
        mProgressRView = findViewById(R.id.progress_bar_r);
        activity_heading = findViewById(R.id.activity_heading);
        rDataObjList = rDataObj.getITEMS();
        recyclerViewQ = (RecyclerView) findViewById(R.id.rVAttendance);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);
        lblDocName = findViewById(R.id.lblDocName);

        View recyclerView = findViewById(R.id.rVAttendance);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        printLogs(LogTag,"initializeViews","exit");
    }
    @Override
    protected void initializeLabels(){
        printLogs(LogTag,"initializeLabels","init");
        String  Label = getLabelFromDb("h_S267",R.string.h_S267);
        activity_heading.setText(Label);
    }
    @Override
    protected void initializeInputs(){
        printLogs(LogTag,"initializeInputs","init");
        lblDocName.setText(doc_name);
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
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_267;
        FINAL_URL=FINAL_URL+"/token/"+token+"/doc_type/"+doc_type;
        printLogs(LogTag,"fetchData","URL : "+FINAL_URL);
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
                            int aId2 = Integer.parseInt(rec.getString("file_id"));
                            String aTitle3 = rec.getString("file_title");
                            String aPath4 = rec.getString("file_path");
                            String aType5 = rec.getString("file_type");
                            String aIsUploaded6 = rec.getString("is_file_uploaded");
                            String aIsReUploaded7 = rec.getString("is_file_reupload");
                            String aIsRemoved8 = rec.getString("is_file_removed");
                            ///aTitle3,aPath4,aType5,aIsUploaded6,aIsReUploaded7,aIsRemoved8
                            rDataObj.addItem(rDataObj.createItem(pos,aId2,aTitle3,aPath4,aType5,aIsUploaded6,aIsReUploaded7,aIsRemoved8));
                            showList();
                        }
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
        };
        RequestQueue requestQueue = Volley.newRequestQueue(DownloadCenterA.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }


    public void callHeaderBuilder(){
        printLogs(LogTag,"callHeaderBuilder","init");
        String tHeader3 = getLabelFromDb("t_head_S267_title",R.string.t_head_S267_title);
        String tHeader4 =getLabelFromDb("t_head_S267_download",R.string.t_head_S267_download);
        String tHeader7 = getLabelFromDb("t_head_S267_upload",R.string.t_head_S267_upload);
        String tHeader5 = "";
        String tHeader6 = "";
        String tHeader8 = getLabelFromDb("t_head_S267_remove",R.string.t_head_S267_remove);
        /////aTitle3,aPath4,aType5,aIsUploaded6,aIsReUploaded7,aIsRemoved8
        rDataObj.addItem(rDataObj.createItem(0,0,tHeader3,tHeader4,tHeader5,tHeader6,tHeader7,tHeader8));
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        printLogs(LogTag,"setupRecyclerView","init");
        recyclerView.setAdapter(new DownloadCenterAdapter(rDataObjList,baseApcContext,activityIn,thisClass));
    }
    public void showList(){
        printLogs(LogTag,"showList","init");
        List<DownloadCenterObj.Item> AttData = rDataObj.getITEMS();
        DownloadCenterAdapter adapter = new DownloadCenterAdapter(AttData,baseApcContext,activityIn,thisClass);
        recyclerViewQ.setAdapter(adapter);
        showProgress(false,mContentRView,mProgressRView);
    }
    public void customRedirector() {
        Intent intent = new Intent(DownloadCenterA.this, DownloadCenterA.class);
        Bundle inputUri = new Bundle();
        printLogs(LogTag,"onOptionsItemSelected","DownloadCenterA");
        inputUri.putString("doc_type", doc_type);
        inputUri.putString("doc_name", doc_name);
        intent.putExtras(inputUri);
        startActivity(intent);
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(DownloadCenterA.this, DocumentCenterListA.class);
            printLogs(LogTag,"onOptionsItemSelected","DocumentCenterListA");
            startActivity(intent);
            finish();
        }
        return true;
    }
    public String getDocType(){
        return doc_type;
    }
    public String getDocName(){
        return doc_name;
    }
    public void removeDocument(final String document_id){
        showProgress(true, mContentView, mProgressView);
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.REF_S_267_1;
        String token = userSessionObj.getToken();
        FINAL_URL = FINAL_URL + "/token/" + token+"/document_id/" + document_id;
        printLogs(LogTag, "FormSubmit", "URL : " + FINAL_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, FINAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject outputJson = null;
                try {
                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if (Status.equals("1")) {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = getLabelFromDb("m_267_title", R.string.m_267_title);
                        String sMessage = getLabelFromDb("m_267_message_removed", R.string.m_267_message_removed);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showSuccessDialogDownlodCenterA(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose, thisClass);

                    } else {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-104";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "-105";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String sTitle = "Error :" + ActivityId + "-106";
                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("document_id", document_id);
                return params;


            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(DownloadCenterA.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
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