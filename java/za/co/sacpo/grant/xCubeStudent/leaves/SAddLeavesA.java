package za.co.sacpo.grant.xCubeStudent.leaves;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeLib.db.DbSchema;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.session.OlumsGrantSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsStudentSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeLib.spinners.SpinnerSet;
import za.co.sacpo.grant.xCubeLib.spinners.SpinnerSetAdapter;
import za.co.sacpo.grant.xCubeStudent.SDashboardDA;


public class SAddLeavesA extends BaseFormAPCPrivate{
    private String ActivityId="S113";

    // private String no_of_days="";
    public EditText inputLeaveReasons;
    public DatePicker inputStartDate,inputEndDate;
    public View mProgressView, mContentView,heading;
    public TextInputLayout inputLayoutLeaveReasons;
    public Button btnSubmitLv,btn_view_Leave;
    public TextInputLayout inputLayoutLeaveDays;
    public EditText inputLeaveInDays;
    public LinearLayout LLFormContainer,LLInformationContainer;
    String generator;
    int selected_leave_type=0;
    String selected_leave_type_val="";
    Spinner inputSpinnerLeaveType;
    private SpinnerSetAdapter adapterS;
    private TextView lblView;
    SAddLeavesA thisClass;
    String start_date,end_date,LeaveReasons,no_of_days,LeaveType;
    private static SpinnerSet[] leaveTypeMasters;
    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId){
        baseApcContext = cnt;
        CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        thisClass = this;
        printLogs(lt,"setBaseApcContextParent","weAreIn");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(), this, this.getClass().getSimpleName(),ActivityId);
        printLogs(LogTag, "onCreate", "init");
        Bundle activeInputUri = getIntent().getExtras();
        generator = activeInputUri.getString("generator");
        start_date = activeInputUri.getString("start_date");
        end_date = activeInputUri.getString("end_date");
        LeaveReasons = activeInputUri.getString("LeaveReasons");
        no_of_days = activeInputUri.getString("no_of_days");
        LeaveType = activeInputUri.getString("LeaveType");
        selected_leave_type_val =activeInputUri.getString("selected_leave_type_val");
        printLogs(LogTag,"onCreate","GENERATOR ID "+generator);
        bootStrapInit();
    }
    @Override
    protected void bootStrapInit(){
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext,activityIn);
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
            initializeListeners();
            userToken =userSessionObj.getToken();
            syncToken(baseApcContext,activityIn);
            if(TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
            callDataApi();
            initializeInputs();
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
    protected void verifyVersion(){
        printLogs(LogTag,"verifyVersion","init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag,"verifyVersion","isUpdate "+isUpdate);
        printLogs(LogTag,"verifyVersion","isUpdate "+ utilSessionObj.getIsUpdateRequired());
        if(isUpdate){
            Intent intent = new Intent(SAddLeavesA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","a_add_leaves");
        setContentView(R.layout.a_add_leaves);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        heading = findViewById(R.id.heading);

        btn_view_Leave = findViewById(R.id.btn_view_Leave);
        inputStartDate = (DatePicker) findViewById(R.id.inputStartDate);
        inputEndDate = (DatePicker) findViewById(R.id.inputEndDate);
        inputLeaveReasons = (EditText) findViewById(R.id.inputLeaveReasons);
        inputSpinnerLeaveType = (Spinner) findViewById(R.id.inputSpinnerLeaveType);
        inputLayoutLeaveReasons = (TextInputLayout) findViewById(R.id.inputLayoutLeaveReasons);
        LLFormContainer = (LinearLayout) findViewById(R.id.form_container);
        LLInformationContainer = (LinearLayout) findViewById(R.id.informationContainer);
        btnSubmitLv = (Button) findViewById(R.id.btn_submitLeave);
        inputLayoutLeaveDays = (TextInputLayout) findViewById(R.id.inputLayoutLeaveDays);
        inputLeaveInDays = (EditText) findViewById(R.id.inputLeaveInDays);
        printLogs(LogTag,"initializeViews","exit");
    }
    @Override
    protected void initializeLabels(){
        printLogs(LogTag,"initializeLabels","init");
        String Label = getLabelFromDb("l_S113_start_date",R.string.l_S113_start_date);
        lblView = (TextView)findViewById(R.id.lblStartDate);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));

        lblView.setText(Label);
        Label = getLabelFromDb("l_S113_end_date",R.string.l_S113_end_date);
        lblView = (TextView)findViewById(R.id.lblEndDate);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));

        lblView.setText(Label);
        Label = getLabelFromDb("b_S113_view_Leave",R.string.b_S113_view_Leave);
        btn_view_Leave.setText(Label);
        Label = getLabelFromDb("l_S113_total_days",R.string.l_S113_total_days);
        lblView = (TextView)findViewById(R.id.lblTotalDays);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));

        lblView.setText(Label);

        Label = getLabelFromDb("l_S113_leave_reasons",R.string.l_S113_leave_reasons);
        lblView = (TextView)findViewById(R.id.lblLeaveReasons);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));

        lblView.setText(Label);
        Label = getLabelFromDb("h_S113",R.string.h_S113);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));

        lblView.setText(Label);
        Label = getLabelFromDb("b_apply_leave",R.string.b_apply_leave);
        btnSubmitLv.setText(Label);
        Label = getLabelFromDb("i_no_active_grant",R.string.i_no_active_grant);
        lblView = (TextView)findViewById(R.id.iNoActiveGrant);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));

        lblView.setText(Label);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            btnSubmitLv.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            btn_view_Leave.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
        }

        }
    @Override
    protected void initializeInputs(){
        printLogs(LogTag,"initializeInputs","init");
        dbSetaObj = DbHelper.getInstance(this);

        Cursor resUTM = dbSetaObj.getAllByType(DbSchema.TABLE_MASTER_ARRAYS,DbSchema.COLUMN_M_A_TYPE,3);
        int total_leave_type = resUTM.getCount();
        leaveTypeMasters = new SpinnerSet[total_leave_type];
        if(total_leave_type > 0) {
            int i=0;
            try {
                while (resUTM.moveToNext()) {
                    leaveTypeMasters[i] = new SpinnerSet();
                    leaveTypeMasters[i].setId(resUTM.getInt(resUTM.getColumnIndex(DbSchema.COLUMN_M_A_SERVER_ID)));
                    leaveTypeMasters[i].setName(resUTM.getString(resUTM.getColumnIndex(DbSchema.COLUMN_M_A_NAME)));
                    i++;
                }
            } finally {
                if (resUTM != null && !resUTM.isClosed()) {
                    resUTM.close();
                }
            }
        }

        adapterS = new SpinnerSetAdapter(SAddLeavesA.this,android.R.layout.simple_spinner_item,leaveTypeMasters);
        inputSpinnerLeaveType.setAdapter(adapterS);

        studentSessionObj = new OlumsStudentSession(baseApcContext);
        boolean isGrantActive= studentSessionObj.getIsActiveGrant();
        //isGrantActive = false;
        if(isGrantActive) {
            grantSessionObj = new OlumsGrantSession(baseApcContext);
            String grantId = grantSessionObj.getGrantId();
            int grantIdInt = 0;
            if(!TextUtils.isEmpty(grantId)) {
                grantIdInt =Integer.parseInt(grantId);
            }
            if (grantIdInt > 0) {
                printLogs(LogTag,"initializeInputs","GRANT "+grantIdInt);
                String StartDate = grantSessionObj.getGrantSDate();
                String EndDate = grantSessionObj.getGrantEDate();
                printLogs(LogTag,"initializeInputs","GSD "+StartDate+"=="+EndDate);
                Long minDate = longDateFromString(StartDate);
                Long maxDate = longDateFromString(EndDate);
                inputStartDate.setMinDate(minDate);
                inputStartDate.setMaxDate(maxDate);

                inputEndDate.setMaxDate(maxDate);
                inputEndDate.setMinDate(minDate);

                LLFormContainer.setVisibility(View.VISIBLE);
                LLInformationContainer.setVisibility(View.GONE);
            }
            else{
                printLogs(LogTag,"initializeInputs","NO GRANT");
                LLFormContainer.setVisibility(View.GONE);
                LLInformationContainer.setVisibility(View.VISIBLE);
            }
        }
        else{
            printLogs(LogTag,"initializeInputs","NO GRANT");
            LLFormContainer.setVisibility(View.GONE);
            LLInformationContainer.setVisibility(View.VISIBLE);
        }


    }
    @Override
    protected void initializeListeners() {
        printLogs(LogTag,"initializeListeners","init");
        inputSpinnerLeaveType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,int position, long id) {
                selected_leave_type = leaveTypeMasters[position].getId();
                selected_leave_type_val = leaveTypeMasters[position].getName();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {selected_leave_type=0;selected_leave_type_val="";}
        });
        btn_view_Leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inBundle = new Bundle();
                Intent intent = new Intent(SAddLeavesA.this, SLeavesDA.class);
                inBundle.putString("generator", ActivityId);
                intent.putExtras(inBundle);
                startActivity(intent);
                finish();
            }
        });
        btnSubmitLv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        inputEndDate.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                int monthS = inputStartDate.getMonth()+1;
                int monthE = inputEndDate.getMonth()+1;
                final String start_date = inputStartDate.getYear()+"-"+monthS+"-"+inputStartDate.getDayOfMonth();
                final String end_date = inputEndDate.getYear()+"-"+monthE+"-"+inputEndDate.getDayOfMonth();

                Date dateStart = dateFromString(start_date);
                Date dateEnd = dateFromString(end_date);
                printLogs(LogTag,"initializeListeners","dateStart"+dateStart);
                printLogs(LogTag,"initializeListeners","dateEnd"+dateEnd);
                setNoOfDays(dateStart,dateEnd);
            }
        });

        /*inputEndDate.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                int monthS = inputStartDate.getMonth()+1;
                int monthE = inputEndDate.getMonth()+1;
                final String start_date = inputStartDate.getDayOfMonth()+"-"+monthS+"-"+inputStartDate.getYear();
                final String end_date = inputEndDate.getDayOfMonth()+"-"+monthE+"-"+inputEndDate.getYear();

                Date dateStart = dateFromString(inputStartDate.getDayOfMonth() +"-"+monthS+ "-"+year);
                Date dateEnd = dateFromString(dayOfMonth +"-"+monthOfYear+ "-"+year);
                setNoOfDays(dateStart,dateEnd);
            }
        });*/



    }
    public void setNoOfDays(Date dateStart,Date dateStop){
        int number;
        number = countWorkingDate(dateStart,dateStop);
        printLogs(LogTag,"setNoOfDays","number"+number+dateStart);
        String nums = String.valueOf(number);
        printLogs(LogTag,"setNoOfDays","nums"+nums+dateStop);
        inputLeaveInDays.setText(nums);
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
    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void validateForm() {
        boolean cancel = false;
        printLogs(LogTag,"validateForm","init");
        int monthS = inputStartDate.getMonth()+1;
        int monthE = inputEndDate.getMonth()+1;
        final String start_date = inputStartDate.getDayOfMonth()+"-"+monthS+"-"+inputStartDate.getYear();
        final String end_date = inputEndDate.getDayOfMonth()+"-"+monthE+"-"+inputEndDate.getYear();
        if(!isSpinnerValid(selected_leave_type)){
            cancel = true;
            String ErrorTitle ="Message :"+ActivityId+"-101";
            String ErrorMessage =getLabelFromDb("error_S113_106",R.string.error_S113_106);
            spinnerError(ErrorTitle,ErrorMessage);
        }
        else if (!validateSDate(inputStartDate)) {
            cancel = true;
        }
        else if (!validateEDate(inputEndDate)) {
            cancel = true;
        }/*else if (!validateELessSDate(start_date,end_date)) {
            cancel = true;
        }*/
        else if(!validateLeaveDays(inputLeaveInDays,inputLayoutLeaveDays)){
            cancel = true;
        }
        else if(!validateReasons(inputLeaveReasons,inputLayoutLeaveReasons)){
            cancel = true;
        }
        if (cancel) {
            return;
        } else {
            showProgress(true,mContentView,mProgressView);
            this.FormSubmit();
        }
        printLogs(LogTag,"validateForm","exit");
    }

    private boolean validateLeaveDays(EditText inputLeaveInDays, TextInputLayout inputLayoutLeaveDays) {
        printLogs(LogTag,"onCreate","validate department");
        String feedback = inputLeaveInDays.getText().toString().trim();
        setCustomError(inputLayoutLeaveDays,null,inputLeaveInDays);
        if (feedback.isEmpty()) {
            String sMessage = getLabelFromDb("error_S113_108",R.string.error_S113_108);
            setCustomError(inputLayoutLeaveDays,sMessage,inputLeaveInDays);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutLeaveDays,inputLeaveInDays);
            return true;
        }
    }

    public void FormSubmit(){
        int monthS = inputStartDate.getMonth()+1;
        int monthE = inputEndDate.getMonth()+1;
        final String start_date = inputStartDate.getDayOfMonth()+"-"+monthS+"-"+inputStartDate.getYear();
        final String end_date = inputEndDate.getDayOfMonth()+"-"+monthE+"-"+inputEndDate.getYear();
        final String LeaveReasons = inputLeaveReasons.getText().toString().trim();
        final String no_of_days = inputLeaveInDays.getText().toString().trim();
        final String LeaveType = String.valueOf(selected_leave_type);


        inBundle = new Bundle();
        Intent intent = new Intent(SAddLeavesA.this, SLConfirmationA.class);
        inBundle.putString("generator", ActivityId);
        inBundle.putString("start_date", start_date);
        inBundle.putString("end_date", end_date);
        inBundle.putString("LeaveReasons", LeaveReasons);
        inBundle.putString("no_of_days", no_of_days);
        inBundle.putString("LeaveType", LeaveType);
        inBundle.putString("selected_leave_type_val", selected_leave_type_val);
        intent.putExtras(inBundle);
        startActivity(intent);
        finish();
    }
    public boolean validateSDate(DatePicker inputStartDate) {
        printLogs(LogTag,"validateSDate","DATA : ");
        return true;
    }
    public boolean validateEDate(DatePicker inputStartDate){
        printLogs(LogTag,"validateEDate","DATA : ");
        return true;
    }
    public boolean validateELessSDate(String firstDate,String secondDate){
        Date fDate = dateFromString(firstDate);
        Date sDate = dateFromString(secondDate);
        return fDate.after(sDate);
    }
    public boolean validateReasons(EditText inputLeaveReasons,TextInputLayout inputLayoutLeaveReasons) {
        printLogs(LogTag,"onCreate","validateReason");
        String reasons = inputLeaveReasons.getText().toString().trim();
        setCustomError(inputLayoutLeaveReasons,null,inputLeaveReasons);
      //  Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/<>.^*()%!-]");
        Pattern regex = Pattern.compile("[$&+:;=\\\\?@#|/<>^*!]");
        if (reasons.isEmpty() || isValidReason(reasons) || regex.matcher(reasons).find()) {
            String sMessage = getLabelFromDb("error_S113_105",R.string.error_S113_105);
            setCustomError(inputLayoutLeaveReasons,sMessage,inputLeaveReasons);
            return false;
        }else if(regex.matcher(reasons).find()){
            String sMessage = getLabelFromDb("error_S113_109",R.string.error_S113_109);
            setCustomError(inputLayoutLeaveReasons,sMessage,inputLeaveReasons);
            return false;

        } else {
            setCustomErrorDisabled(inputLayoutLeaveReasons,inputLeaveReasons);
            return true;
        }
    }

    public void  customRedirector(){
            Intent intent = new Intent(SAddLeavesA.this, SDashboardDA.class);
            Bundle activeUri = new Bundle();
            intent.putExtras(activeUri);
            startActivity(intent);
            finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
                Intent intent = new Intent(SAddLeavesA.this,SDashboardDA.class);
                printLogs(LogTag,"onOptionsItemSelected","SDashboardDA");
                startActivity(intent);
                finish();

        return true;
    }

    protected class ErrorTextWatcher implements TextWatcher {

        private EditText EditView;
        private TextInputLayout EditLayout;

        private ErrorTextWatcher(EditText EditView,TextInputLayout EditLayout) {
            printLogs(LogTag,"onCreate","ErrorTextWatcher");
            this.EditView = EditView;
            this.EditLayout = EditLayout;
        }
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void afterTextChanged(Editable editable) {
            switch (EditView.getId()) {
                case R.id.inputLeaveReasons:
                    validateReasons(EditView,EditLayout);
                    break;

            }
        }
    }
    public static int countWorkingDate(Date dateStart, Date dateStop) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(dateStart);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(dateStop);
        int workDays = 1;
        //Return 1 if start and end are the same
        if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
            return 1;
        }
        if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
            startCal.setTime(dateStop);
            endCal.setTime(dateStart);
        }
        do {
            //excluding start date
            startCal.add(Calendar.DAY_OF_MONTH, 1);
            if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                ++workDays;
            }
        } while (startCal.getTimeInMillis() < endCal.getTimeInMillis()); //excluding end date
        return workDays;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SAddLeavesA.this,SDashboardDA.class);
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