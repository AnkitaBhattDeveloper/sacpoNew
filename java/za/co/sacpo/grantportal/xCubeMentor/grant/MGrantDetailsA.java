package za.co.sacpo.grantportal.xCubeMentor.grant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;

import za.co.sacpo.grantportal.AppUpdatedA;
import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.component.Utils;
import za.co.sacpo.grantportal.xCubeLib.db.mLearnerGrantDetailsAdapter;
import za.co.sacpo.grantportal.xCubeLib.db.mLearnerGrantDetailsArray;
import za.co.sacpo.grantportal.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grantportal.xCubeMentor.MDashboardDA;
import za.co.sacpo.grantportal.xCubeMentor.messages.MChatA;

public class MGrantDetailsA extends BaseAPCPrivate {
    private String ActivityId="230";
    public View mProgressView, mContentView,heading;

    private TextView lblView,txt_b_heading;

    TextView txtSetaName,txtSetaManagerName,txtLEAName,txtLEAManager,txtHostName,txtHostSDLNo,txtGStartDate,txtGEndDate,lblGrantAdmin_cellNo,txtGrantAdmin_cellNo,lblGrantAdminEmail,txtGrantAdminEmail;
    Button mLEAContactButton,mLEACallButton,mLEAEmailButton;
    String grant_id,mentor_id,seta_user_id,lea_id,generator,student_id,fId;
    Bundle activeUri;
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
        Bundle activeInputUri = getIntent().getExtras();

        grant_id = activeInputUri.getString("g_d_id");
        student_id = activeInputUri.getString("student_id");

        printLogs(LogTag,"onCreate","GRANT_ID_HERE-----> "+grant_id);
        printLogs(LogTag,"onCreate","student_id: "+student_id);

        generator = activeInputUri.getString("generator");
        printLogs(LogTag,"onCreate","GRANT ID "+grant_id);
        printLogs(LogTag,"onCreate","GENERATOR ID "+generator);
        bootStrapInit();
        internetChangeBroadCast();
        fetchVersionData();
        verifyVersion();
    }
    @Override
    protected void fetchVersionData(){
        printLogs(LogTag,"fetchVersionData","exitConnected");
        syncUpdates(baseApcContext,activityIn);
    }

    @Override
    protected void internetChangeBroadCast() {
        registerBroadcastIC();
    }
    @Override
    protected void verifyVersion(){
        printLogs(LogTag,"verifyVersion","init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag,"verifyVersion","isUpdate "+isUpdate);
        printLogs(LogTag,"verifyVersion","isUpdate "+ utilSessionObj.getIsUpdateRequired());
        if(isUpdate){
            Intent intent = new Intent(MGrantDetailsA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
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
            printLogs(LogTag,"bootStrapInit","initConnected");
            initializeViews();
            initBackBtn();
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
            printLogs(LogTag,"onCreate","exitConnected");
        }else{
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);
            initMenusCustom(ActivityId,baseApcContext,activityIn);
            printLogs(LogTag,"bootStrapInit","initConnected");
            initializeViews();
            initBackBtn();
            showProgress(true,mContentView,mProgressView);
            initializeLabels();
            initializeListeners();
            userToken =userSessionObj.getToken();
            syncToken(baseApcContext,activityIn);
            if(TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
            callDataApi();
            fetchOfflineData();
            //initializeInputs();
            printLogs(LogTag,"onCreate","exitConnected");
        }
    }



    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

    }
    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","a_grant_details");
        setContentView(R.layout.m_a_grant_details);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        heading = findViewById(R.id.heading);

        txt_b_heading=(TextView) findViewById(R.id.txt_b_heading);
        txtSetaName=(TextView) findViewById(R.id.txtSetaName);
        txtSetaManagerName=(TextView) findViewById(R.id.txtSetaManagerName);
        txtLEAName=(TextView) findViewById(R.id.txtLEAName);
        txtLEAManager=(TextView) findViewById(R.id.txtLEAManager);
        txtHostName=(TextView) findViewById(R.id.txtHostName);
        txtHostSDLNo=(TextView) findViewById(R.id.txtHostSDLNo);

        txtGStartDate=(TextView) findViewById(R.id.txtGStartDate);
        txtGEndDate=(TextView) findViewById(R.id.txtGEndDate);

        mLEAContactButton = (Button) findViewById(R.id.mLEAContactButton);

        mLEACallButton= (Button) findViewById(R.id.mLEACallButton);

        mLEAEmailButton= (Button) findViewById(R.id.mLEAEmailButton);



        lblGrantAdmin_cellNo=(TextView) findViewById(R.id.lblGrantAdmin_cellNo);
        txtGrantAdmin_cellNo=(TextView) findViewById(R.id.txtGrantAdmin_cellNo);


        lblGrantAdminEmail=(TextView) findViewById(R.id.lblGrantAdminEmail);
        txtGrantAdminEmail=(TextView) findViewById(R.id.txtGrantAdminEmail);

    }
    @Override
    protected void initializeInputs(){
        printLogs(LogTag,"initializeInputs","init");
        getGrantDetails();
    }
    @Override
    protected void initializeLabels(){

        printLogs(LogTag,"initializeLabels","init");
        String Label = getLabelFromDb("lbl_230_seta_name",R.string.lbl_230_seta_name);
        lblView = (TextView)findViewById(R.id.lblSetaName);
        lblView.setText(Label);

        Label = getLabelFromDb("h_230",R.string.h_230);
        lblView = (TextView)findViewById(R.id.txt_b_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_230_seta_manager",R.string.lbl_230_seta_manager);
        lblView = (TextView)findViewById(R.id.lblSetaManagerName);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_230_lea_name",R.string.lbl_230_lea_name);
        lblView = (TextView)findViewById(R.id.lblLEAName);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_230_grant_admin",R.string.lbl_230_grant_admin);
        lblView = (TextView)findViewById(R.id.lblLEAManager);
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_230_host_name",R.string.lbl_230_host_name);
        lblView = (TextView)findViewById(R.id.lblHostName);
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_230_host_sdl",R.string.lbl_230_host_sdl);
        lblView = (TextView)findViewById(R.id.lblHostSDLNo);
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_230_mentor_name",R.string.lbl_230_mentor_name);

        Label = getLabelFromDb("b_230_Message",R.string.b_230_Message);
        mLEAContactButton.setText(Label);


        Label = getLabelFromDb("b_230_Email",R.string.b_230_Email);
        mLEAEmailButton.setText(Label);


        Label = getLabelFromDb("b_230_Call",R.string.b_230_Call);
        mLEACallButton.setText(Label);


        Label = getLabelFromDb("lbl_230_grant_email",R.string.lbl_230_grant_email);
        lblView = (TextView)findViewById(R.id.lblGrantAdminEmail);
        lblView.setText(Label);


        Label = getLabelFromDb("lbl_230_grant_cell",R.string.lbl_230_grant_cell);
        lblView = (TextView)findViewById(R.id.lblGrantAdmin_cellNo);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_135_grant_start_date",R.string.lbl_135_grant_start_date);
        lblView = (TextView)findViewById(R.id.lblGStartDate);
        lblView.setText(Label);


        Label = getLabelFromDb("lbl_135_grant_end_date",R.string.lbl_135_grant_end_date);
        lblView = (TextView)findViewById(R.id.lblGEndDate);
        lblView.setText(Label);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            mLEAEmailButton.setBackground(getDrawable(getDrwabaleResourceId("themed_small_button")));
            mLEAEmailButton.setTextColor(getResources().getColor(getTextcolorResourceId("btn_textColor")));
            mLEAContactButton.setTextColor(getResources().getColor(getTextcolorResourceId("btn_textColor")));
            mLEACallButton.setTextColor(getResources().getColor(getTextcolorResourceId("btn_textColor")));
            mLEAContactButton.setBackground(getDrawable(getDrwabaleResourceId("themed_small_button")));
            mLEACallButton.setBackground(getDrawable(getDrwabaleResourceId("themed_small_button")));
        }
        }
    @Override
    protected void initializeListeners() {
        mLEAContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activeUri = new Bundle();
              //  String mentor_id = grantSessionObj.getGrantMentorId();
              // String mentor_name = grantSessionObj.getGrantMentorName();
                activeUri.putString("fId", fId);
                activeUri.putString("fIsGroup", "0");
              //  activeUri.putString("fName",mentor_name);
                activeUri.putString("fImage","");
                activeUri.putString("generator",ActivityId);
                activeUri.putString("grant_id",grant_id);
                activeUri.putString("student_id",student_id);
                Intent intent = new Intent(MGrantDetailsA.this, MChatA.class);
                activeUri.putString("user_id",mentor_id);
                printLogs(LogTag,"mMentorContactButton","Mentor_USER_ID "+mentor_id);
                printLogs(LogTag,"mMentorContactButton","grant_admin_id_grant_admin_id"+fId);
                intent.putExtras(activeUri);
                startActivity(intent);
                finish();
            }
        });


        mLEACallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cell = txtGrantAdmin_cellNo.getText().toString().trim();
                Uri call = Uri.parse("tel:" + cell);
                Intent surf = new Intent(Intent.ACTION_DIAL, call);
                startActivity(surf);
            }
        });

        mLEAEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("mailto:"));
                intent.setType("plain/text");
                String email = txtGrantAdminEmail.getText().toString().trim();
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { email });
                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });


    }
    public void callDataApi(){
        printLogs(LogTag,"callDataApi","init");
        userToken = userSessionObj.getToken();
        if(!TextUtils.isEmpty(userToken)) {
            printLogs(LogTag,"callDataApi","Token Update");
            syncUserData(baseApcContext, activityIn);
            syncMentorData(baseApcContext, activityIn);
            syncGrantData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn,ActivityId);
        }
    }
    private void getGrantDetails() {

        String token = userSessionObj.getToken();

        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.MENTOR_GRANT_STUDENT_DATA_URL;
        FINAL_URL=FINAL_URL+"?token="+token+"&student_id="+student_id;
        printLogs(LogTag,"getGrantDetails","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "getGrantDetails", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        mLearnerGrantDetailsAdapter adapter = new mLearnerGrantDetailsAdapter(getApplicationContext());
                        adapter.truncate();
                        mLearnerGrantDetailsArray array = null;
                        JSONObject dataM = outputJson.getJSONObject(KEY_DATA);
                        printLogs(LogTag,"getGrantDetails","INSIDE JSON : "+response);

                        fId = dataM.getString("grant_admin_id");
                        txtSetaName.setText(dataM.getString("seta_name"));
                        txtSetaManagerName.setText(dataM.getString("seta_manager_name"));
                        txtLEAName.setText(dataM.getString("lem_name"));
                        txtLEAManager.setText(dataM.getString("grant_admin"));
                        txtHostName.setText(dataM.getString("host_emp_name"));
                        txtHostSDLNo.setText(dataM.getString("host_emp_sdl"));
                        txtGStartDate.setText(dataM.getString("s_s_g_grant_start_date"));
                        txtGEndDate.setText(dataM.getString("s_s_g_grant_end_date"));
                        txtGrantAdminEmail.setText(dataM.getString("grant_admin_email"));
                        txtGrantAdmin_cellNo.setText(dataM.getString("grant_admin_cell"));
                        mentor_id =dataM.getString("mentor_id");
                        lea_id =dataM.getString("lem_manager_id");
                        seta_user_id = dataM.getString("seta_manager_id");

                        array = new mLearnerGrantDetailsArray(dataM.getString("u_id"),
                                dataM.getString("s_id_no"),dataM.getString("grant_id"),
                                dataM.getString("seta_name"),dataM.getString("seta_manager_name"),
                                dataM.getString("seta_manager_id"),dataM.getString("lem_name"),
                                dataM.getString("lem_id"),dataM.getString("host_emp_name"),
                                dataM.getString("grant_admin"),dataM.getString("grant_admin_id"),
                                dataM.getString("grant_admin_email"),dataM.getString("grant_admin_cell"),
                                dataM.getString("host_emp_sdl"),dataM.getString("s_s_g_grant_start_date"),
                                dataM.getString("s_s_g_grant_end_date"),dataM.getString("mentor_id"));
adapter.insert(array);
                        // showProgress(false,mContentView,mProgressView);

                        showProgress(false,mContentView,mProgressView);
                    }
                    else if(Status.equals("2")) {
                        showProgress(false,mContentView,mProgressView);
                    }
                    else{
                        //showProgress(false,mContentView,mProgressView);
                        printLogs(LogTag,"getGrantDetails","ERROR_230_100 : DATA_ERROR");
                        String sTitle="Error :"+ActivityId+"-100";
                        String sMessage=getLabelFromDb("error_230_100",R.string.error_230_100);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    printLogs(LogTag,"getGrantDetails","error_try_again : "+e.getMessage());
                    e.printStackTrace();
                    //showProgress(false,mContentView,mProgressView);
                    String sTitle="Error :"+ActivityId+"-101";
                    String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                    String sButtonLabelClose="Close";
                    ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                }
            }
        },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        printLogs(LogTag,"getGrantDetails","error_try_again : "+error.getMessage());
                        //showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-102";
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

    private void fetchOfflineData() {
        mLearnerGrantDetailsAdapter adapter = new mLearnerGrantDetailsAdapter(getApplicationContext());
        List<mLearnerGrantDetailsArray> adapterAll = adapter.getAll();

        for (int i = 0; i < adapterAll.size(); i++) {
            fId = adapterAll.get(i).getGrant_admin_id();
            txtSetaName.setText(adapterAll.get(i).getSeta_name());
            txtSetaManagerName.setText(adapterAll.get(i).getSeta_manager_name());
            txtLEAName.setText(adapterAll.get(i).getLem_name());
            txtLEAManager.setText(adapterAll.get(i).getGrant_admin());
            txtHostName.setText(adapterAll.get(i).getHost_emp_name());
            txtHostSDLNo.setText(adapterAll.get(i).getHost_emp_sdl());
            txtGStartDate.setText(adapterAll.get(i).getS_s_g_grant_start_date());
            txtGEndDate.setText(adapterAll.get(i).getS_s_g_grant_end_date());
            txtGrantAdminEmail.setText(adapterAll.get(i).getGrant_admin_email());
            txtGrantAdmin_cellNo.setText(adapterAll.get(i).getGrant_admin_cell());
            mentor_id =adapterAll.get(i).getMentor_id();
            lea_id =adapterAll.get(i).getLem_id();
            seta_user_id = adapterAll.get(i).getSeta_manager_id();
        }
        showProgress(false, mContentView, mProgressView);


    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
                Intent intent = new Intent(MGrantDetailsA.this,MDashboardDA.class);
                printLogs(LogTag,"onOptionsItemSelected","MDashboard");
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
