package za.co.sacpo.grant.xCubeStudent.attendance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;

public class SAttVMCommentA extends BaseAPCPrivate {

    private String ActivityId="S110";

    public View mProgressView, mContentView;

    private TextView lblView;

    TextView  txtComment,txtCommentDate,lblComment;

    String generator,month,year,comment;

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
        Bundle activeInputUri = getIntent().getExtras();
        month = activeInputUri.getString("month");
        year = activeInputUri.getString("year");
        comment = activeInputUri.getString("comment");
        generator = activeInputUri.getString("generator");
        printLogs(LogTag,"onCreate","month "+month+"year "+year+"comment "+comment);
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
            Intent intent = new Intent(SAttVMCommentA.this,AppUpdatedA.class);
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
        printLogs(LogTag,"setLayoutXml","a_s_att_view_comments");
        setContentView(R.layout.a_s_att_view_mentor_comments);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        txtCommentDate=(TextView) findViewById(R.id.txtCommentDate);
        txtComment=(TextView) findViewById(R.id.txtComment);
        lblComment=(TextView) findViewById(R.id.lblComment);

    }
    @Override
    protected void initializeInputs(){
        printLogs(LogTag,"initializeInputs","init");
        txtComment.setText(comment);
        txtCommentDate.setText(month+" "+year);
        showProgress(false,mContentView,mProgressView);
    }
    @Override
    protected void initializeLabels(){

        printLogs(LogTag,"initializeLabels","init");
        String  Label = getLabelFromDb("h_S110",R.string.h_S110);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);
        Label = getLabelFromDb("l_S110_Comment",R.string.l_S110_Comment);
        lblComment.setText(Label);
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
        printLogs(LogTag,"generator","generator"+generator);
        if (generator.equals("S109")) {
            Bundle inputUri = new Bundle();
            inputUri.putString("generator", generator);
            Intent intent = new Intent(SAttVMCommentA.this,SMonthlyAttDA.class);
            intent.putExtras(inputUri);
            printLogs(LogTag,"onOptionsItemSelected","SMonthlyAttDA");
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