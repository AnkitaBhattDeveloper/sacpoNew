package za.co.sacpo.grant.xCubeStudent.grant;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.db.GrantDetailsArrayAdapter;
import za.co.sacpo.grant.xCubeLib.db.grantDetailsArray;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsGrantSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeStudent.SDashboardDA;
import za.co.sacpo.grant.xCubeStudent.messages.SChatA;

public class SGrantDetailsA extends BaseAPCPrivate {
    private String ActivityId="S136";
    public View mProgressView, mContentView,heading;

    private TextView lblView;

    TextView txtGrantName,txtSetaName,txtSetaManagerName,txtLEAName,txtLEAManager,txtHostName,txtHostSDLNo,txtMentorName,txtGStartDate,txtGEndDate,
            lblMentor_cellNo,txtMentor_cellNo,lblGrantAdmin_cellNo,txtGrantAdmin_cellNo,lblMentorEmail,txtMentorEmail,lblGrantAdminEmail,txtGrantAdminEmail;
    Button mLEAContactButton,mMentorContactButton,mMentorCallButton,mLEACallButton,mMentorEmailButton,mLEAEmailButton;
    String grant_id,mentor_id,seta_user_id,lea_id,generator,grant_adminId,fId;
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
        grantSessionObj = new OlumsGrantSession(baseApcContext);
        String grant_sesion_id = grantSessionObj.getGrantId();
        if(grant_sesion_id.isEmpty()) {
            grant_id = activeInputUri.getString("grant_id");
            printLogs(LogTag,"onCreate","GRANT_ID_HERE-----> "+grant_id);
        }
        else{
            grant_id = grant_sesion_id;
        }
        generator = activeInputUri.getString("generator");

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
            Intent intent = new Intent(SGrantDetailsA.this,AppUpdatedA.class);
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
           // initializeInputs();
            printLogs(LogTag,"onCreate","exitConnected");
        }
    }
    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","a_grant_details");
        setContentView(R.layout.a_grant_details);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        heading = findViewById(R.id.heading);

        txtGrantName=(TextView) findViewById(R.id.txtGrantName);
        txtSetaName=(TextView) findViewById(R.id.txtSetaName);
        txtSetaManagerName=(TextView) findViewById(R.id.txtSetaManagerName);
        txtLEAName=(TextView) findViewById(R.id.txtLEAName);
        txtLEAManager=(TextView) findViewById(R.id.txtLEAManager);
        txtHostName=(TextView) findViewById(R.id.txtHostName);
        txtHostSDLNo=(TextView) findViewById(R.id.txtHostSDLNo);
        txtMentorName=(TextView) findViewById(R.id.txtMentorName);
        txtGStartDate=(TextView) findViewById(R.id.txtGStartDate);
        txtGEndDate=(TextView) findViewById(R.id.txtGEndDate);

        mLEAContactButton = (Button) findViewById(R.id.mLEAContactButton);
        mMentorContactButton = (Button) findViewById(R.id.mMentorContactButton);
        mLEACallButton= (Button) findViewById(R.id.mLEACallButton);
        mMentorCallButton= (Button) findViewById(R.id.mMentorCallButton);
        mLEAEmailButton= (Button) findViewById(R.id.mLEAEmailButton);
        mMentorEmailButton= (Button) findViewById(R.id.mMentorEmailButton);

        lblMentor_cellNo=(TextView) findViewById(R.id.lblMentor_cellNo);
        txtMentor_cellNo=(TextView) findViewById(R.id.txtMentor_cellNo);
        lblGrantAdmin_cellNo=(TextView) findViewById(R.id.lblGrantAdmin_cellNo);
        txtGrantAdmin_cellNo=(TextView) findViewById(R.id.txtGrantAdmin_cellNo);

        lblMentorEmail=(TextView) findViewById(R.id.lblMentorEmail);
        txtMentorEmail=(TextView) findViewById(R.id.txtMentorEmail);
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
        String Label = getLabelFromDb("lbl_S136_seta_name",R.string.lbl_S136_seta_name);
        lblView = (TextView)findViewById(R.id.lblSetaName);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S136_seta_manager",R.string.lbl_S136_seta_manager);
        lblView = (TextView)findViewById(R.id.lblSetaManagerName);
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S136_lea_name",R.string.lbl_S136_lea_name);
        lblView = (TextView)findViewById(R.id.lblLEAName);
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S136_lea_manager",R.string.lbl_S136_lea_manager);
        lblView = (TextView)findViewById(R.id.lblLEAManager);
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S136_host_name",R.string.lbl_S136_host_name);
        lblView = (TextView)findViewById(R.id.lblHostName);
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S136_host_sdl",R.string.lbl_S136_host_sdl);
        lblView = (TextView)findViewById(R.id.lblHostSDLNo);
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S136_mentor_name",R.string.lbl_S136_mentor_name);
        lblView = (TextView)findViewById(R.id.lblMentorName);
        lblView.setText(Label);
        Label = getLabelFromDb("b_S136_Message",R.string.b_S136_Message);
        mLEAContactButton.setText(Label);
        Label = getLabelFromDb("b_S136_Message",R.string.b_S136_Message);
        mMentorContactButton.setText(Label);

        Label = getLabelFromDb("b_S136_Email",R.string.b_S136_Email);
        mLEAEmailButton.setText(Label);
        Label = getLabelFromDb("b_S136_Email",R.string.b_S136_Email);
        mMentorEmailButton.setText(Label);


        Label = getLabelFromDb("b_S136_Call",R.string.b_S136_Call);
        mLEACallButton.setText(Label);
        Label = getLabelFromDb("b_S136_Call",R.string.b_S136_Call);
        mMentorCallButton.setText(Label);


        Label = getLabelFromDb("lbl_S136_mentor_email",R.string.lbl_S136_mentor_email);
        lblView = (TextView)findViewById(R.id.lblMentorEmail);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S136_grant_email",R.string.lbl_S136_grant_email);
        lblView = (TextView)findViewById(R.id.lblGrantAdminEmail);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S136_mentor_cell",R.string.lbl_S136_mentor_cell);
        lblView = (TextView)findViewById(R.id.lblMentor_cellNo);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S136_grant_cell",R.string.lbl_S136_grant_cell);
        lblView = (TextView)findViewById(R.id.lblGrantAdmin_cellNo);
        lblView.setText(Label);

         Label = getLabelFromDb("lbl_135_grant_start_date",R.string.lbl_135_grant_start_date);
        lblView = (TextView)findViewById(R.id.lblGStartDate);
        lblView.setText(Label);


         Label = getLabelFromDb("lbl_135_grant_end_date",R.string.lbl_135_grant_end_date);
        lblView = (TextView)findViewById(R.id.lblGEndDate);
        lblView.setText(Label);

        txtGrantName.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            mLEAContactButton.setBackground(getDrawable(getDrwabaleResourceId("themed_small_button")));
            mLEAContactButton.setTextColor(getResources().getColor(getTextcolorResourceId("btn_textColor")));
            mLEACallButton.setBackground(getDrawable(getDrwabaleResourceId("themed_small_button")));
            mLEACallButton.setTextColor(getResources().getColor(getTextcolorResourceId("btn_textColor")));
            mLEAEmailButton.setBackground(getDrawable(getDrwabaleResourceId("themed_small_button")));
            mLEAEmailButton.setTextColor(getResources().getColor(getTextcolorResourceId("btn_textColor")));

        }


    }
    @Override
    protected void initializeListeners() {

        printLogs(LogTag,"setLayoutXml","getGrantId"+grant_adminId);

        mMentorContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activeUri = new Bundle();
                String mentor_id = grantSessionObj.getGrantMentorId();
                String mentor_name = grantSessionObj.getGrantMentorName();
                activeUri.putString("fId", mentor_id);
                activeUri.putString("fIsGroup", "0");
                activeUri.putString("fName",mentor_name);
                activeUri.putString("fImage","");
                Intent intent = new Intent(SGrantDetailsA.this, SChatA.class);
                activeUri.putString("user_id",mentor_id);
                printLogs(LogTag,"mMentorContactButton","Mentor USER ID "+mentor_id);
                intent.putExtras(activeUri);
                startActivity(intent);
                finish();
            }
        });

        mLEACallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grantSessionObj = new OlumsGrantSession(baseApcContext);
               String cell = txtGrantAdmin_cellNo.getText().toString();
              //  String cell = "-";
                Uri call = Uri.parse("tel:" + cell);
                Intent surf = new Intent(Intent.ACTION_DIAL, call);
                startActivity(surf);
            }
        });

        mLEAEmailButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("IntentReset")
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("mailto:"));
                intent.setType("plain/text");
                grantSessionObj = new OlumsGrantSession(baseApcContext);
                String email = txtGrantAdminEmail.getText().toString();
               // String email = grantSessionObj.getGrantMentorEmail();
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { email });
                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });

        mMentorCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grantSessionObj = new OlumsGrantSession(baseApcContext);
                String cell = grantSessionObj.getGrantMentorCell();
                Uri call = Uri.parse("tel:" + cell);
                Intent surf = new Intent(Intent.ACTION_DIAL, call);
                startActivity(surf);
            }
        });

        mMentorEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("mailto:"));
                intent.setType("plain/text");
                grantSessionObj = new OlumsGrantSession(baseApcContext);
                String email = grantSessionObj.getGrantMentorEmail();
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
            syncStudentData(baseApcContext, activityIn);
            syncGrantData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn,ActivityId);
        }
    }
    private void getGrantDetails() {
        String token = userSessionObj.getToken();
        String grantId = grant_id;
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_136 + "?token=" + token;
        printLogs(LogTag, "getGrantDetails", "URL : " + FINAL_URL);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject outputJson = null;
                try {
                    printLogs(LogTag, "getGrantDetails", "RESPONSE : " + response);
                    outputJson = new JSONObject(String.valueOf(response));
                    String Status = outputJson.getString(KEY_STATUS);
                    if (Status.equals("1")) {
                        GrantDetailsArrayAdapter adapter  = new GrantDetailsArrayAdapter(getApplicationContext());
                        adapter.truncate();

                        JSONObject dataM = outputJson.getJSONObject(KEY_DATA);
                        String grantId = dataM.getString("grant_id");
                        int grantIdInt = Integer.parseInt(grantId);
                        if (grantIdInt > 0) {
                            txtGrantName.setText(dataM.getString("grant_name"));
                            txtSetaName.setText(dataM.getString("seta_name"));
                            txtSetaManagerName.setText(dataM.getString("seta_manager_name"));
                            txtLEAName.setText(dataM.getString("lem_name"));
                            txtLEAManager.setText(dataM.getString("grant_admin"));
                            txtHostName.setText(dataM.getString("host_emp_name"));
                            txtHostSDLNo.setText(dataM.getString("host_emp_sdl"));
                            txtMentorName.setText(dataM.getString("mentor_name"));
                            txtGStartDate.setText(dataM.getString("s_s_g_grant_start_date"));
                            txtGEndDate.setText(dataM.getString("s_s_g_grant_end_date"));
                            txtGrantAdminEmail.setText(dataM.getString("grant_admin_email"));
                            txtMentorEmail.setText(dataM.getString("mentor_email"));
                            txtMentor_cellNo.setText(dataM.getString("mentor_cell"));
                            txtGrantAdmin_cellNo.setText(dataM.getString("grant_admin_cell"));
                            mentor_id = dataM.getString("mentor_id");
                            lea_id = dataM.getString("lem_manager_id");
                            seta_user_id = dataM.getString("seta_manager_id");
                            fId = dataM.getString("grant_admin_id");



                            adapter.insert(new grantDetailsArray(dataM.getString("u_id"),dataM.getString("grant_id"),
                                    dataM.getString("s_s_g_grant_start_date"),dataM.getString("s_s_g_grant_end_date"),
                                    dataM.getString("grant_name"),dataM.getString("mentor_name"),
                                    dataM.getString("mentor_id"),dataM.getString("lem_name"),
                                    dataM.getString("lem_id"),dataM.getString("host_emp_name"),
                                    dataM.getString("seta_id"),dataM.getString("seta_name"),
                                    dataM.getString("seta_manager_id"),dataM.getString("seta_manager_name"),
                                    dataM.getString("grant_admin_id"),dataM.getString("grant_admin"),
                                    dataM.getString("grant_admin_email"),dataM.getString("grant_admin_cell"),
                                    dataM.getString("seta_manager_email"),dataM.getString("seta_manager_cell")));


                            mLEAContactButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    activeUri = new Bundle();
                                    String mentor_id = grantSessionObj.getGrantMentorId();
                                    String mentor_name = grantSessionObj.getGrantMentorName();
                                    activeUri.putString("fId", mentor_id);
                                    activeUri.putString("fIsGroup", "0");
                                    activeUri.putString("fName", mentor_name);
                                    activeUri.putString("fImage", "");
                                    activeUri.putString("fId", fId);
                                    Intent intent = new Intent(SGrantDetailsA.this, SChatA.class);
                                    activeUri.putString("user_id", mentor_id);
                                    printLogs(LogTag, "mMentorContactButton", "Mentor USER ID " + mentor_id);
                                    intent.putExtras(activeUri);
                                    startActivity(intent);
                                    finish();
                                }
                            });

                            // showProgress(false,mContentView,mProgressView);
                        }
                        showProgress(false, mContentView, mProgressView);
                    } else if (Status.equals("2")) {
                        showProgress(false, mContentView, mProgressView);
                    } else {
                        //showProgress(false,mContentView,mProgressView);
                        printLogs(LogTag, "getGrantDetails", "ERROR_S136_100 : DATA_ERROR");
                        String sTitle = "Error :" + ActivityId + "-100";
                        String sMessage = getLabelFromDb("error_S136_100", R.string.error_S136_100);
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
                printLogs(LogTag, "getGrantDetails", "VolleyError : "+error);
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
        RequestQueue queue = Volley.newRequestQueue(this);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjectRequest);
    }
    private void fetchOfflineData() {

        GrantDetailsArrayAdapter adapter = new GrantDetailsArrayAdapter(getApplicationContext());
        List<grantDetailsArray> adapterAll = adapter.getAll();

        for (int i = 0; i < adapterAll.size(); i++) {
            String grantId = adapterAll.get(i).getGrant_id();
            int grantIdInt = Integer.parseInt(grantId);
            if (grantIdInt > 0) {
                txtGrantName.setText(adapterAll.get(i).getGrant_name());
                txtSetaName.setText(adapterAll.get(i).getSeta_name());
                txtSetaManagerName.setText(adapterAll.get(i).getSeta_manager_name());
                txtLEAName.setText(adapterAll.get(i).getLem_name());
                txtLEAManager.setText(adapterAll.get(i).getGrant_admin());
                txtHostName.setText(adapterAll.get(i).getHost_emp_name());
                txtHostSDLNo.setText("-");
                txtMentorName.setText(adapterAll.get(i).getMentor_name());
                txtGStartDate.setText(adapterAll.get(i).getS_s_g_grant_start_date());
                txtGEndDate.setText(adapterAll.get(i).getS_s_g_grant_end_date());
                txtGrantAdminEmail.setText(adapterAll.get(i).getGrant_admin_email());
                txtMentorEmail.setText("-");
                txtMentor_cellNo.setText("-");
                txtGrantAdmin_cellNo.setText(adapterAll.get(i).getGrant_admin_cell());
                mentor_id = adapterAll.get(i).getMentor_id();
                lea_id = adapterAll.get(i).getLem_id();
                seta_user_id = adapterAll.get(i).getSeta_manager_id();
                fId = adapterAll.get(i).getGrant_admin_id();


                mLEAContactButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        activeUri = new Bundle();
                        String mentor_id = grantSessionObj.getGrantMentorId();
                        String mentor_name = grantSessionObj.getGrantMentorName();
                        activeUri.putString("fId", mentor_id);
                        activeUri.putString("fIsGroup", "0");
                        activeUri.putString("fName", mentor_name);
                        activeUri.putString("fImage", "");
                        activeUri.putString("fId", fId);
                        Intent intent = new Intent(SGrantDetailsA.this, SChatA.class);
                        activeUri.putString("user_id", mentor_id);
                        printLogs(LogTag, "mMentorContactButton", "Mentor USER ID " + mentor_id);
                        intent.putExtras(activeUri);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }
        showProgress(false, mContentView, mProgressView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
                Intent intent = new Intent(SGrantDetailsA.this,SDashboardDA.class);
                printLogs(LogTag,"onOptionsItemSelected","sDashboard");
                startActivity(intent);
                finish();

        }
        return true;
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SGrantDetailsA.this, SDashboardDA.class);
        printLogs(LogTag,"onBackPressed","sDashboard");
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
