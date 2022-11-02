package za.co.sacpo.grant.xCubeMentor.student;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeMentor.MDashboardDA;

/*activity_mchange_off_day*/
public class MChangeOffDay extends BaseFormAPCPrivate {
    private String ActivityId="179";
    private String KEY_SUNDAY="day1";
    private String KEY_MONDAY="day2";
    private String KEY_TUESDAY="day3";
    private String KEY_WEDNESDAY="day4";
    private String KEY_THURSDAY="day5";
    private String KEY_FRIDAY="day6";
    private String KEY_SATURDAY="day7";
    //    private String KEY_STUDENT_ID="student_id";
    public View mProgressView, mContentView;
    public Button btnSave;
    private MChangeOffDay thisClass;

    private TextView lblView;
     CheckBox checkboxSun,checkboxMon,checkboxTues,checkboxWed,checkboxThues,checkboxFriday,checkboxSatur;

    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cTAId){
        baseApcContext = cnt;CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        thisClass = this;
        printLogs(lt,"setBaseApcContextParent","weAreIn");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);
        printLogs(LogTag,"onCreate","init");
        Bundle activeInputUri = getIntent().getExtras();


        bootStrapInit();
    }
    @Override
    protected void bootStrapInit() {
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext, activityIn);
        if(isConnected) {
            printLogs(LogTag,"bootStrapInit","initConnected");
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);

            initMenusCustom(ActivityId,baseApcContext,activityIn);
            fetchVersionData();
            verifyVersion();
            internetChangeBroadCast();
            initializeViews();
            initBackBtn();
            showProgress(true,mContentView,mProgressView);
            initializeLabels();
            initializeInputs();
            userToken =userSessionObj.getToken();
            syncToken(baseApcContext,activityIn);
            if(TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
            callDataApi();
            initializeListeners();
            printLogs(LogTag,"bootStrapInit","exitConnected");
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
    protected void verifyVersion() {
        printLogs(LogTag,"verifyVersion","init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag,"verifyVersion","isUpdate "+isUpdate);
        printLogs(LogTag,"verifyVersion","isUpdate "+ utilSessionObj.getIsUpdateRequired());
        if(isUpdate){
            Intent intent = new Intent(MChangeOffDay.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void setLayoutXml() {
        setContentView(R.layout.activity_mchange_off_day);
        printLogs(LogTag,"setLayoutXml","activity_mchange_off_day");
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        btnSave = (Button) findViewById(R.id.btnSave);
        checkboxSun = (CheckBox) findViewById(R.id.checkboxSun);
        checkboxMon = (CheckBox) findViewById(R.id.checkboxMon);
        checkboxTues = (CheckBox) findViewById(R.id.checkboxTues);
        checkboxWed = (CheckBox) findViewById(R.id.checkboxWed);
        checkboxThues = (CheckBox) findViewById(R.id.checkboxThues);
        checkboxFriday = (CheckBox) findViewById(R.id.checkboxFriday);
        checkboxSatur = (CheckBox) findViewById(R.id.checkboxSatur);
        printLogs(LogTag,"initializeViews","exit");
    }
    @Override
    protected void initializeLabels(){
        printLogs(LogTag,"initializeLabels","init");
        String Label = getLabelFromDb("l_179_sun",R.string.l_179_sun);
        lblView = (TextView)findViewById(R.id.lblSun);
        lblView.setText(Label);
        Label = getLabelFromDb("l_179_mon",R.string.l_179_mon);
        lblView = (TextView)findViewById(R.id.lblMon);
        lblView.setText(Label);
        Label = getLabelFromDb("l_179_tues",R.string.l_179_tues);
        lblView = (TextView)findViewById(R.id.lblTues);
        lblView.setText(Label);

        Label = getLabelFromDb("l_179_wed",R.string.l_179_wed);
        lblView = (TextView)findViewById(R.id.lblWednes);
        lblView.setText(Label);

        Label = getLabelFromDb("l_179_thues",R.string.l_179_thues);
        lblView = (TextView)findViewById(R.id.lblThus);
        lblView.setText(Label);

        Label = getLabelFromDb("l_179_fri",R.string.l_179_fri);
        lblView = (TextView)findViewById(R.id.lblFriday);
        lblView.setText(Label);

        Label = getLabelFromDb("l_179_setur",R.string.l_179_setur);
        lblView = (TextView)findViewById(R.id.lblSatur);
        lblView.setText(Label);

        Label = getLabelFromDb("b_179_save",R.string.b_179_save);
        btnSave.setText(Label);

        Label = getLabelFromDb("h_179",R.string.h_179);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);
    }
    @Override
    protected void initializeInputs(){
        printLogs(LogTag,"initializeInputs","init");
        fetchData();
    }
    @Override
    protected void initializeListeners() {
        printLogs(LogTag,"initializeInputs","init");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });
    }
    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void fetchData(){
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.STUDENT_OFF_DETAILS;
        FINAL_URL=FINAL_URL+"/token/"+token;
        //+"/student_id/"+student_id;
        printLogs(LogTag,"fetchData","URL : "+FINAL_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,FINAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject outputJson = null;
                try {
                    printLogs(LogTag,"fetchData","RESPONSE : "+response);
                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONObject dataM = outputJson.getJSONObject(KEY_DATA);
                        String day1 = dataM.getString("status1");
                        String day2 = dataM.getString("status2");
                        String day3 = dataM.getString("status3");
                        String day4 = dataM.getString("status4");
                        String day5 = dataM.getString("status5");
                        String day6 = dataM.getString("status6");
                        String day7 = dataM.getString("status7");
                        if(day1.equals("1")) {
                            checkboxSun.setChecked(true);
                        }
                        else{
                            checkboxSun.setChecked(false);
                        }
                        if(day2.equals("1")) {
                            checkboxMon.setChecked(true);
                        }
                        else{
                            checkboxMon.setChecked(false);
                        }
                        if(day3.equals("1")) {
                            checkboxTues.setChecked(true);
                        }
                        else{
                            checkboxTues.setChecked(false);
                        }
                        if(day4.equals("1")) {
                            checkboxWed.setChecked(true);
                        }
                        else{
                            checkboxWed.setChecked(false);
                        }
                        if(day5.equals("1")) {
                            checkboxThues.setChecked(true);
                        }
                        else{
                            checkboxThues.setChecked(false);
                        }
                        if(day6.equals("1")) {
                            checkboxFriday.setChecked(true);
                        }
                        else{
                            checkboxFriday.setChecked(false);
                        }
                        if(day7.equals("1")) {
                            checkboxSatur.setChecked(true);
                        }
                        else{
                            checkboxSatur.setChecked(false);
                        }
                        showProgress(false, mContentView, mProgressView);
                    }

                    else if(Status.equals("2")) {
                        showProgress(false,mContentView,mProgressView);
                    }

                    else{
                        //showProgress(false,mContentView,mProgressView);
                        printLogs(LogTag, "fetchData", "error_try_again : DATA_ERROR");
                        String sTitle="Error :"+ActivityId+"-101";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    printLogs(LogTag, "fetchData", "error_try_again : " + e.getMessage());
                    e.printStackTrace();
                    //showProgress(false,mContentView,mProgressView);
                    String sTitle = "Error :" + ActivityId + "-102";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                printLogs(LogTag, "fetchData", "error_try_again: " + error.getMessage());
                String sTitle = "Error :" + ActivityId + "-103";
                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);

            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(MChangeOffDay.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }
    public void validateForm() {
        boolean cancel = false;
        if (cancel) {
            return;
        } else {
            showProgress(true,mContentView,mProgressView);
            this.FormSubmit();
        }
        printLogs(LogTag,"validateForm","exit");
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


    public void FormSubmit(){

        String token = userSessionObj.getToken();
        String SunVal="2";
        if(checkboxSun.isChecked()){
            SunVal="1";
        }
        String MonVal="2";
        if(checkboxMon.isChecked()){
            MonVal="1";
        }
        String TuesVal="2";
        if(checkboxTues.isChecked()){
            TuesVal="1";
        }
        String WedVal="2";
        if(checkboxWed.isChecked()){
            WedVal="1";
        }
        String ThursVal="2";
        if(checkboxThues.isChecked()){
            ThursVal="1";
        }
        String FriVal="2";
        if(checkboxFriday.isChecked()){
            FriVal="1";
        }
        String SatVal="2";
        if(checkboxSatur.isChecked()){
            SatVal="1";
        }
        final String sunday=SunVal;
        final String monday=MonVal;
        final String tuesday=TuesVal;
        final String wednesday=WedVal;
        final String thursday=ThursVal;
        final String friday=FriVal;
        final String saturday=SatVal;
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.STUDENT_UPDATE_OFF_DETAILS;
        FINAL_URL=FINAL_URL+"/token/"+token;
        printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.PUT,FINAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject outputJson = null;

                try {
                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        showProgress(false,mContentView,mProgressView);
                        String sTitle=getLabelFromDb("m_179_title",R.string.m_179_title);
                        String sMessage=getLabelFromDb("m_179_message",R.string.m_179_message);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showSuccessDialogMChangeOffDay(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose,thisClass);
                    }
                    else{
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-104";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    showProgress(false,mContentView,mProgressView);
                    String sTitle="Error :"+ActivityId+"-105";
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
                    String sTitle="Error :"+ActivityId+"-106";
                    String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                    String sButtonLabelClose="Close";
                    ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

                }
            })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_SUNDAY, sunday);
                params.put(KEY_MONDAY, monday);
                params.put(KEY_TUESDAY, tuesday);
                params.put(KEY_WEDNESDAY, wednesday);
                params.put(KEY_THURSDAY, thursday);
                params.put(KEY_FRIDAY, friday);
                params.put(KEY_SATURDAY, saturday);
                //params.put(KEY_STUDENT_ID, student_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MChangeOffDay.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    public void customRedirector(){
        Intent intent = new Intent(MChangeOffDay.this,MDashboardDA.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(MChangeOffDay.this,MDashboardDA.class);
            printLogs(LogTag,"onOptionsItemSelected","MDashboardDA");
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
