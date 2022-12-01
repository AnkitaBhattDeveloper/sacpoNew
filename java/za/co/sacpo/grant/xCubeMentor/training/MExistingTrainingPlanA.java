package za.co.sacpo.grant.xCubeMentor.training;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.adapter.ETrainingPlanAdapter;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dataObj.ETrainingPlanObj;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeMentor.MDashboardDA;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MExistingTrainingPlanA extends BaseAPCPrivate {
    private String ActivityId="M312";
    public View mProgressView, mContentView,mProgressRView, mContentRView,heading;
    public TextView lblView,activity_heading;
    public Bundle activeUri;
    public String generator , student_id,student_name;
    public Button btnAddTraining;
    private RecyclerView recyclerViewQ;
    public ETrainingPlanObj rDataObj = new ETrainingPlanObj();
    private List<ETrainingPlanObj.Item> rDataObjList = null;
    @Override
    protected void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cTAId) {
        baseApcContext = cnt;
        CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        CAId = CAId;
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
        if (inputIntent.hasExtra("student_name")) {
            student_name = inBundle.getString("student_name");
        }
        generator = inBundle.getString("generator");
        printLogs(LogTag,"onCreate","GROUP ID "+student_id+student_name);
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
            Intent intent = new Intent(MExistingTrainingPlanA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        btnAddTraining = (Button) findViewById(R.id.btnAddTraining);
        recyclerViewQ = (RecyclerView) findViewById(R.id.rVTPlantList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);

        setupRecyclerView(recyclerViewQ);
        printLogs(LogTag,"initializeViews","exit");
    }


    @Override
    protected void setLayoutXml() {
        setContentView(R.layout.a_m_existing_training_plan);
    }

    @Override
    protected void initializeLabels(){
        printLogs(LogTag,"initializeLabels","init");
        String  Label = getLabelFromDb("h_M312",R.string.h_M312);
        activity_heading.setText(Label);
        activity_heading.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));

        Label = getLabelFromDb("btn_M313_add",R.string.btn_M313_add);
        btnAddTraining.setText(Label);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            btnAddTraining.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
        }


        }
    @Override
    protected void initializeInputs(){
        printLogs(LogTag,"initializeInputs","init");
    }

    @Override
    protected void initializeListeners() {
        printLogs(LogTag,"initializeListeners","init");
        btnAddTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle inputUri = new Bundle();
                inputUri.putString("student_id", student_id);
                inputUri.putString("m_student_name", student_name);
                inputUri.putString("generator", ActivityId);
                Context context = view.getContext();
                Intent intent = new Intent(MExistingTrainingPlanA.this, MAddTrainingPlanA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);
            }
        });
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
    public void callHeaderBuilder(){
        printLogs(LogTag,"callHeaderBuilder","init");
        String tHeader3 = getLabelFromDb("t_head_M312_name",R.string.t_head_M312_name);
        String tHeader4 = getLabelFromDb("t_head_M312_file",R.string.t_head_M312_file);
        String tHeader5 = "";
        String tHeader6 = getLabelFromDb("t_head_M312_edit",R.string.t_head_M312_edit);
        rDataObj.addItem(rDataObj.createItem(0,0,tHeader3,tHeader4,tHeader5,tHeader6));
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        printLogs(LogTag,"setupRecyclerView","init");
        recyclerView.setAdapter(new ETrainingPlanAdapter(rDataObjList,baseApcContext,activityIn));
    }
    public void showList(){
        printLogs(LogTag,"showList","init");
        List<ETrainingPlanObj.Item> AttData = rDataObj.getITEMS();
        ETrainingPlanAdapter adapter = new ETrainingPlanAdapter(AttData,baseApcContext,activityIn);
        recyclerViewQ.setAdapter(adapter);
        showProgress(false,mContentRView,mProgressRView);
    }

    public void fetchData(){
        showProgress(true,mContentRView,mProgressRView);
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.M_REF_312;
        FINAL_URL=FINAL_URL+"?token="+token;
        printLogs(LogTag,"fetchData","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchData", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        for (int i = 0; i < dataM.length(); i++) {
                            int pos = i+1;
                            JSONObject rec = dataM.getJSONObject(i);
                            int aId2 = Integer.parseInt(rec.getString("training_id"));
                            String atitle3 = rec.getString("title");
                            String afile4 =rec.getString("fileLink");

                            //String aedit5 = rec.getString("edit_link");
                            String aIsEdit6 = rec.getString("edit_link");
                            //rDataObj.addItem(rDataObj.createItem(pos,aId2,atitle3,afile4,aedit5,aIsEdit6));

                            String aIsFile5 = rec.getString("is_file");
                            rDataObj.addItem(rDataObj.createItem(pos,aId2,atitle3,afile4,aIsFile5,aIsEdit6));

                            showList();
                        }
                        showProgress(false,mContentRView,mProgressRView);
                    }
                    else if(Status.equals("2")) {
                        showProgress(false,mContentRView,mProgressRView);
                        printLogs(LogTag,"fetchData","RESPONSE : 2");
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
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(MExistingTrainingPlanA.this, MDashboardDA.class);
            printLogs(LogTag,"onOptionsItemSelected","SDashboardDA");
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
