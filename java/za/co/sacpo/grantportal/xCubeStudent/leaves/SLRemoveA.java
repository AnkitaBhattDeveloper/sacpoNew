package za.co.sacpo.grantportal.xCubeStudent.leaves;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import za.co.sacpo.grantportal.AppUpdatedA;
import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grantportal.xCubeLib.component.Utils;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;

public class SLRemoveA extends BaseAPCPrivate {

    private String ActivityId="S118";
    //TODO :: Map Correction

    public View mProgressView, mContentView;

    private TextView lblView;

    TextView txtDay,txtLeave_reason,txtLeave_Status,txtAddLeaveDate,txtLeave_student_status,txtApprovalStatus;

  //  private GoogleMap googleMap;
    String motivation,generator,dated;

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
        printLogs(LogTag,"onCreate","init");
        printLogs(LogTag,"onCreate","init");
        Bundle activeInputUri = getIntent().getExtras();
        motivation = activeInputUri.getString("motivation");
        dated = activeInputUri.getString("dated");
        generator = activeInputUri.getString("generator");
        printLogs(LogTag,"onCreate","GENERATOR ID "+generator);
        bootStrapInit();
        internetChangeBroadCast();

        fetchVersionData();
        verifyVersion();
    }

    protected void fetchVersionData(){
        printLogs(LogTag,"fetchVersionData","exitConnected");
        syncUpdates(baseApcContext,activityIn);
    }

    @Override
    protected void internetChangeBroadCast() {
        registerBroadcastIC();
    }

    protected void verifyVersion(){
        printLogs(LogTag,"verifyVersion","init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag,"verifyVersion","isUpdate "+isUpdate);
        printLogs(LogTag,"verifyVersion","isUpdate "+ utilSessionObj.getIsUpdateRequired());
        if(isUpdate==true){
            Intent intent = new Intent(SLRemoveA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void bootStrapInit() {
        Boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext,activityIn);
        if(isConnected ==true) {
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
            printLogs(LogTag,"initializeInputs","exitConnected");
        }
    }
    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","a_s_leave_details");
        setContentView(R.layout.a_s_leave_motivation);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);

        txtDay=(TextView) findViewById(R.id.txtDay);
        txtLeave_reason=(TextView) findViewById(R.id.txtLeave_reason);
        txtAddLeaveDate=(TextView) findViewById(R.id.txtAddLeaveDate);
        txtApprovalStatus=(TextView) findViewById(R.id.txtApprovalStatus);
        txtLeave_student_status=(TextView) findViewById(R.id.txtLeave_student_status);
    }
    @Override
    protected void initializeInputs(){
        printLogs(LogTag,"initializeInputs","init");
        txtLeave_reason.setText(motivation);
        txtAddLeaveDate.setText(dated);
        showProgress(false,mContentView,mProgressView);
    }
    @Override
    protected void initializeLabels(){

        printLogs(LogTag,"initializeLabels","init");
        String  Label = getLabelFromDb("h_S118",R.string.h_S118);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);

    }
    @Override
    protected void initializeListeners() {

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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Bundle inputUri = new Bundle();
            Intent intent = new Intent(SLRemoveA.this,SLeavesDA.class);
            printLogs(LogTag,"onOptionsItemSelected","SLeavesDA");
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

