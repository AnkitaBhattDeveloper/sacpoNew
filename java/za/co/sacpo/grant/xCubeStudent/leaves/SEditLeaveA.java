package za.co.sacpo.grant.xCubeStudent.leaves;

import android.content.Context;
import android.content.Intent;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeLib.db.DbSchema;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grant.xCubeLib.session.OlumsGrantSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsStudentSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeLib.spinners.SpinnerSet;
import za.co.sacpo.grant.xCubeLib.spinners.SpinnerSetAdapter;

public class SEditLeaveA extends BaseFormAPCPrivate {
    private String ActivityId="S125";

    public EditText inputLeaveReasons;
    public DatePicker inputStartDate,inputEndDate;
    public View mProgressView, mContentView,heading;
    public TextInputLayout inputLayoutLeaveReasons;
    public Button btnSubmitLv,btn_view_Leave;
    public TextInputLayout inputLayoutLeaveDays;
    public EditText inputLeaveInDays;
    public LinearLayout LLFormContainer,LLInformationContainer;
    String generator , leave_id;
    int selected_leave_type=0;
    String selected_leave_type_val="";
    Spinner inputSpinnerLeaveType ;
    private SpinnerSetAdapter adapterS;
    private TextView lblView;
    private static SpinnerSet[] leaveTypeMasters;


    private String KEY_NO_OF_DAYS="no_of_days";
    private String KEY_START_DATE="start_date";
    private String KEY_END_DATE="end_date";
    private String KEY_LEAVE_TYPE="leave_type";
    private String KEY_LEAVE_REASONS="leave_reason";

    SEditLeaveA thisClass;

    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId) {
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
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);
        Bundle activeInputUri = getIntent().getExtras();
        generator = activeInputUri.getString("generator");
        leave_id = activeInputUri.getString("leave_id");
        printLogs(LogTag,"onCreate","GENERATOR ID/"+generator+"/leave_id/"+leave_id);
        bootStrapInit();
    }

    @Override
    protected void bootStrapInit() {
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext, activityIn);
        if (isConnected) {
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
            FetchData();
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
    protected void setLayoutXml() {
        setContentView(R.layout.a_edit_leave);
        printLogs(LogTag,"setLayoutXml","sedit_leave");
    }

    @Override
    protected void verifyVersion(){
        printLogs(LogTag,"verifyVersion","init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag,"verifyVersion","isUpdate "+isUpdate);
        printLogs(LogTag,"verifyVersion","isUpdate "+ utilSessionObj.getIsUpdateRequired());
        if(isUpdate){
            Intent intent = new Intent(SEditLeaveA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void initializeInputs() {
        printLogs(LogTag, "initializeInputs", "init");
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

        adapterS = new SpinnerSetAdapter(SEditLeaveA.this,android.R.layout.simple_spinner_item,leaveTypeMasters);
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

        Label = getLabelFromDb("l_S113_total_days",R.string.l_S113_total_days);
        lblView = (TextView)findViewById(R.id.lblTotalDays);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("l_S113_leave_reasons",R.string.l_S113_leave_reasons);
        lblView = (TextView)findViewById(R.id.lblLeaveReasons);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("h_S125",R.string.h_S125);
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
            inputSpinnerLeaveType.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            inputLeaveReasons.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputLeaveInDays.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
        }


    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        heading = findViewById(R.id.heading);

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
        /*inputStartDate.updateDate(2020,23,20);
        inputSpinnerLeaveType.setSelection(3);*/
        printLogs(LogTag,"initializeViews","exit");

    }

    @Override
    protected void initializeListeners() {
        printLogs(LogTag,"initializeListeners","init");
        inputSpinnerLeaveType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,int position, long id) {
                int item =position;
                selected_leave_type = leaveTypeMasters[item].getId();
                selected_leave_type_val = leaveTypeMasters[item].getName();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {selected_leave_type=0;selected_leave_type_val="";}
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




    }

    public void setNoOfDays(Date dateStart,Date dateStop){
        int number;
        number = countWorkingDate(dateStart,dateStop);
        printLogs(LogTag,"setNoOfDays","number"+number+dateStart);
        printLogs(LogTag,"setNoOfDays","nums"+dateStop);
        inputLeaveInDays.setText(String.valueOf(number));
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
        return !fDate.after(sDate);
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
        }/*else if(regex.matcher(reasons).find()){
            String sMessage = getLabelFromDb("error_S113_109",R.string.error_S113_109);
            setCustomError(inputLayoutLeaveReasons,sMessage,inputLeaveReasons);
            return false;

        } */else {
            setCustomErrorDisabled(inputLayoutLeaveReasons,inputLeaveReasons);
            return true;
        }
    }

    public void FetchData(){
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_125_2;
        FINAL_URL=FINAL_URL+"?token="+token+"&leave_id="+leave_id;
        printLogs(LogTag,"fetchData","URL : "+FINAL_URL);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "FetchData", "RESPONSE : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONObject dataM = outputJson.getJSONObject(KEY_DATA);
                        inputLeaveInDays.setText(dataM.getString("total_days"));
                        inputLeaveReasons.setText(dataM.getString("leave_reason"));
                        selected_leave_type = Integer.parseInt(dataM.getString("leave_type"));
                        inputSpinnerLeaveType.setSelection(selected_leave_type);
                        int start_day = Integer.parseInt(dataM.getString("start_day"));
                        int start_month = Integer.parseInt(dataM.getString("start_month"));
                        int start_year = Integer.parseInt(dataM.getString("start_year"));
                        int end_day = Integer.parseInt(dataM.getString("end_day"));
                        int end_month = Integer.parseInt(dataM.getString("end_month"));
                        int end_year = Integer.parseInt(dataM.getString("end_year"));
                        if (start_month > 0){
                            start_month = start_month-1;
                        }
                        if (end_month > 0){
                            end_month = end_month-1;
                        }
                        inputStartDate.updateDate(start_year,start_month,start_day);
                        inputEndDate.updateDate(end_year,end_month,end_day);



                        showProgress(false, mContentView, mProgressView);
                    }
                    else if(Status.equals("2")) {
                        showProgress(false,mContentView,mProgressView);
                    }

                    else{
                        //showProgress(false,mContentView,mProgressView);
                        printLogs(LogTag, "fetchData", "error_S125_101 : DATA_ERROR");
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
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "*/*");
               return params;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    public void FormSubmit(){
        int monthS = inputStartDate.getMonth()+1;
        int monthE = inputEndDate.getMonth()+1;
        final String start_date = inputStartDate.getDayOfMonth()+"-"+monthS+"-"+inputStartDate.getYear();
        final String end_date = inputEndDate.getDayOfMonth()+"-"+monthE+"-"+inputEndDate.getYear();
        final String LeaveReasons = inputLeaveReasons.getText().toString().trim();
        final String no_of_days = inputLeaveInDays.getText().toString().trim();
        final String LeaveType = String.valueOf(selected_leave_type);

        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_125_1;
        printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put("start_date", start_date);
            jsonBody.put("end_date", end_date);
            jsonBody.put("leave_id",leave_id);
            jsonBody.put("leave_reason",LeaveReasons);
            jsonBody.put("leave_type",selected_leave_type);
            printLogs(LogTag, "FormSubmit", "jsonBody " + jsonBody);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.POST, FINAL_URL, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject jsonObject;
                printLogs(LogTag, "FormSubmit", String.format("RESPONSE : %s", response));
                try {
                    jsonObject = new JSONObject(String.valueOf(response));
                    String Status = jsonObject.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        printLogs(LogTag,"FormSubmit","SUCCESS : "+Status);
                        showProgress(false,mContentView,mProgressView);
                        String sTitle=getLabelFromDb("m_S125_title",R.string.m_S125_title);
                        String sMessage=getLabelFromDb("m_S125_message",R.string.m_S125_message);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showSuccessDialogEditLeave(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose,thisClass);

                    }/*else if (Status.equals("2")) {
                        showProgress(false, mContentView, mProgressView);
                    }*/
                    else{
                        printLogs(LogTag,"FormSubmit","else");
                        showProgress(false,mContentView,mProgressView);
                        String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-100";
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false,mContentView,mProgressView);
                    String sTitle="Error :"+ActivityId+"-101";
                    String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                    String sButtonLabelClose="Close";
                    ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        printLogs(LogTag,"onCreate","error listener");
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-102";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                }){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/json; charset=utf-8");
                header.put("Accept","*/*");
                return header;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(SEditLeaveA.this);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);


    }

    public void  customRedirector(){
        Bundle activeUri = new Bundle();
        activeUri.putString("generator", "generator");
        activeUri.putString("leave_id", "leave_id");
        Intent intent = new Intent(SEditLeaveA.this, SLeavesDA.class);
        intent.putExtras(activeUri);
        startActivity(intent);
        finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {

            Bundle activeUri = new Bundle();
            activeUri.putString("generator", "generator");
            activeUri.putString("Leave_type", "leave_id");
            Intent intent = new Intent(SEditLeaveA.this, SLeavesDA.class);
            intent.putExtras(activeUri);
            startActivity(intent);
            finish();
            printLogs(LogTag,"onOptionsItemSelected","SDashboardDA");

        }
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
            if (EditView.getId() == R.id.inputLeaveReasons) {
                validateReasons(EditView, EditLayout);
            }
        }
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
