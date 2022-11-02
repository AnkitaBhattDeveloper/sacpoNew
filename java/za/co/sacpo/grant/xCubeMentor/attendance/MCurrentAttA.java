package za.co.sacpo.grant.xCubeMentor.attendance;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.adapter.MCurrentAttAdapter;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dataObj.MCurrentAttObj;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeLib.spinners.SpinnerSet;
import za.co.sacpo.grant.xCubeLib.spinners.SpinnerSetAdapter;
import za.co.sacpo.grant.xCubeMentor.MDashboardDA;


/*a_matt_out_off_range*/
public class MCurrentAttA extends BaseAPCPrivate {
    private String ActivityId="M407";
    public View mProgressView, mContentView,mProgressRView, mContentRView;
    public TextView lblView ;
    public LinearLayout LLinformationContainer,LLFilterContainer,LLAttendanceContainer,LLaddRemarksContainer;
    public Button mDateInputButton,btnAddRemark;
    public Bundle activeUri;
    public String date_input,generator,year_input,month_input,student_id;
    private Spinner spinnerMonth,spinnerYear;
    private static SpinnerSet[] yearsSet,monthsSet;
    public SpinnerSetAdapter adapterS,adapterM;
    private int selectedYear,selectedMonth;
    private RecyclerView recyclerViewQ;
    TextView iNoActiveGrant;
    String m_student_name,attendance_input_str,start_date,end_date;
    int selectedStudent;
    private EditText inputRemarks;
    public MCurrentAttObj rDataObj = new MCurrentAttObj();
    private List<MCurrentAttObj.Item> rDataObjList = null;
    MCurrentAttA thisClass;
    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cTAId){
        baseApcContext = cnt;
        CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        CAId = CAId;
        thisClass = this;
        printLogs(lt,"setBaseApcContextParent","weAreIn");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);
        printLogs(LogTag,"onCreate","init");
        printLogs(LogTag,"onCreate","init");
        Bundle activeInputUri = getIntent().getExtras();

        student_id = activeInputUri.getString("student_id");
        selectedStudent = Integer.parseInt(student_id);
        m_student_name = activeInputUri.getString("m_student_name");
        generator = activeInputUri.getString("generator");
        start_date = activeInputUri.getString("start_date");
        end_date = activeInputUri.getString("end_date");
        bootStrapInit();
    }

    @Override
    protected void bootStrapInit() {
        Boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext,activityIn);
        if(isConnected) {
            printLogs(LogTag,"bootStrapInit","initConnected");
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);
            initMenusCustom(ActivityId,baseApcContext,activityIn);
            fetchVersionData();
            verifyVersion();
            internetChangeBroadCast();
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
            getAllDetails();
            Calendar cal = Calendar.getInstance();
            int CurrentYear = cal.get(Calendar.YEAR);
            int CurrentMonth = cal.get(Calendar.MONTH)+1;
            fetchData(CurrentYear,CurrentMonth);
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
            Intent intent = new Intent(MCurrentAttA.this,AppUpdatedA.class);
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
        printLogs(LogTag,"setLayoutXml","a_out_of_range");
        setContentView(R.layout.a_matt_out_off_range);
    }

    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mContentRView = findViewById(R.id.content_container_r);
        mProgressRView = findViewById(R.id.progress_bar_r);
        LLinformationContainer = findViewById(R.id.informationContainer);
        LLFilterContainer = findViewById(R.id.filterContainer);
        LLAttendanceContainer = findViewById(R.id.attendanceContainer);

        mDateInputButton = (Button) findViewById(R.id.btnFilterData);
        iNoActiveGrant = (TextView) findViewById(R.id.iNoActiveGrant);
        spinnerMonth = (Spinner) findViewById(R.id.inputSpinnerMonth);
        spinnerYear = (Spinner) findViewById(R.id.inputSpinnerYear);
        btnAddRemark = findViewById(R.id.btnAddRemark);
        inputRemarks = findViewById(R.id.inputRemarks);

        LLaddRemarksContainer = findViewById(R.id.addRemarksContainer);

        rDataObjList = rDataObj.getITEMS();
        recyclerViewQ = (RecyclerView) findViewById(R.id.rVAttendance);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);

        View recyclerView = findViewById(R.id.rVAttendance);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        iNoActiveGrant.setText(m_student_name);

        printLogs(LogTag,"initializeViews","exit");
    }
    @Override
    protected void initializeLabels(){
        printLogs(LogTag,"initializeLabels","init");
        String Label = getLabelFromDb("b_189_filter",R.string.b_189_filter);
        mDateInputButton.setText(Label);

        Label = getLabelFromDb("h_M407",R.string.h_M407);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_189_remark",R.string.lbl_189_remark);
        lblView = (TextView)findViewById(R.id.lblRemarks);
        lblView.setText(Label);
        Label = getLabelFromDb("b_189_add_remark",R.string.b_189_add_remark);
        lblView = (TextView)findViewById(R.id.btnAddRemark);
        lblView.setText(Label);

    }
    @Override
    protected void initializeInputs(){
        printLogs(LogTag,"initializeInputs","init");
        String currentYear="";
        SimpleDateFormat df_year= new SimpleDateFormat("yyyy");
        Calendar cY = Calendar.getInstance();
        currentYear=df_year.format(cY.getTime());

        String currentMonth="";
        SimpleDateFormat df_month= new SimpleDateFormat("MM");
        Calendar cM = Calendar.getInstance();
        currentMonth=df_month.format(cM.getTime());

        selectedYear=Integer.parseInt(currentYear);
        selectedMonth=Integer.parseInt(currentMonth);

        btnAddRemark.setVisibility(View.GONE);
        LLaddRemarksContainer.setVisibility(View.GONE);

        String spinnerOptionYear= getLabelFromDb("l_120_spinner_year",R.string.l_120_spinner_option_year);
        String spinnerOptionMonth= getLabelFromDb("l_120_spinner_year",R.string.l_120_spinner_option_month);
        //String StartDate = "2018-06-01";
        //String EndDate = "2019-08-01";
        String StartDate = start_date;
        String EndDate = end_date;
        printLogs(LogTag,"initializeInputs","init=StartDate"+StartDate+"==EndDate"+EndDate);
        DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        Calendar beginCalendarY = Calendar.getInstance();
        Calendar finishCalendarY = Calendar.getInstance();
        Calendar beginCalendarM = Calendar.getInstance();
        Calendar finishCalendarM = Calendar.getInstance();
        try {
            beginCalendarY.setTime(formatter.parse(StartDate));
            finishCalendarY.setTime(formatter.parse(EndDate));
            beginCalendarM.setTime(formatter.parse(StartDate));
            finishCalendarM.setTime(formatter.parse(EndDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat Yformatter = new SimpleDateFormat("yyyy");

        ArrayList<SpinnerSet> YearsData = new ArrayList<SpinnerSet>();
        finishCalendarY.add(Calendar.YEAR,1);
        while (beginCalendarY.before(finishCalendarY)) {
            SpinnerSet SpinSet = new SpinnerSet();
            int YearInt = Integer.parseInt(Yformatter.format(beginCalendarY.getTime()).toUpperCase());
            SpinSet.setId(YearInt);
            SpinSet.setName(Yformatter.format(beginCalendarY.getTime()).toUpperCase());
            printLogs(LogTag,"initializeInputs","init=Y===="+beginCalendarY.equals(finishCalendarY));
            printLogs(LogTag,"initializeInputs","init=YFEB"+beginCalendarY.before(finishCalendarY));
            YearsData.add(SpinSet);
            beginCalendarY.add(Calendar.YEAR, 1);
            printLogs(LogTag,"initializeInputs","init=after"+Yformatter.format(beginCalendarY.getTime()).toUpperCase());
            printLogs(LogTag,"initializeInputs","init=after"+Yformatter.format(finishCalendarY.getTime()).toUpperCase());
            printLogs(LogTag,"initializeInputs","init=Y===="+beginCalendarY.equals(finishCalendarY));
        }
        int totalYearsInData = YearsData.size();
        int total_years = totalYearsInData + 1;
        printLogs(LogTag,"initializeInputs","init=total_years"+total_years+"==totalYearsInData"+totalYearsInData);
        yearsSet = new SpinnerSet[total_years];
        yearsSet[0] = new SpinnerSet();
        yearsSet[0].setId(0);
        yearsSet[0].setName(spinnerOptionYear);

        for (int i = 0; i < totalYearsInData; i++) {
            int nextI = i + 1;
            yearsSet[nextI] = new SpinnerSet();
            yearsSet[nextI].setId(YearsData.get(i).getId());
            yearsSet[nextI].setName(YearsData.get(i).getName());
        }


        adapterS = new SpinnerSetAdapter(MCurrentAttA.this, android.R.layout.simple_spinner_item, yearsSet);
        int spinnerYPos=1;
        for(int instY=0;instY < total_years;instY++){
            int YId = yearsSet[instY].getId();
            printLogs(LogTag,"initializeInputs","YId"+YId);
            if(YId==selectedYear){
                spinnerYPos = adapterS.getPosition(yearsSet[instY]);
                printLogs(LogTag,"initializeInputs","spinnerYPos"+spinnerYPos);
            }
        }
        printLogs(LogTag,"initializeInputs","spinnerYPos"+spinnerYPos);
        spinnerYear.setAdapter(adapterS);
        spinnerYear.setSelection(spinnerYPos);
        DateFormat Mformatter = new SimpleDateFormat("MM");
        DateFormat MformatterName = new SimpleDateFormat("MMMM");

        ArrayList<SpinnerSet> MonthsData = new ArrayList<SpinnerSet>();


        while (beginCalendarM.before(finishCalendarM) || beginCalendarM.equals(finishCalendarM)) {
            SpinnerSet SpinSet = new SpinnerSet();
            int MonthInt = Integer.parseInt(Mformatter.format(beginCalendarM.getTime()).toUpperCase());
            SpinSet.setId(MonthInt);
            SpinSet.setName(MformatterName.format(beginCalendarM.getTime()).toUpperCase());
            MonthsData.add(SpinSet);
            beginCalendarM.add(Calendar.MONTH, 1);
        }
        int totalMonthInData = MonthsData.size();
        int total_months = totalMonthInData + 1;
        monthsSet = new SpinnerSet[total_months];
        monthsSet[0] = new SpinnerSet();
        monthsSet[0].setId(0);
        monthsSet[0].setName(spinnerOptionMonth);
        for (int i = 0; i < totalMonthInData; i++) {
            int nextI = i + 1;
            monthsSet[nextI] = new SpinnerSet();
            monthsSet[nextI].setId(MonthsData.get(i).getId());
            monthsSet[nextI].setName(MonthsData.get(i).getName());
        }
        adapterM = new SpinnerSetAdapter(MCurrentAttA.this, android.R.layout.simple_spinner_item, monthsSet);
        int spinnerMPos=1;
        for(int instM=0;instM < total_months;instM++){
            int MId = monthsSet[instM].getId();
            printLogs(LogTag,"initializeInputs","MId"+MId);
            if(MId==selectedMonth){
                spinnerMPos = adapterM.getPosition(monthsSet[instM]);
                printLogs(LogTag,"initializeInputs","spinnerMPos"+spinnerMPos);
            }
        }
        printLogs(LogTag,"initializeInputs","spinnerMPos"+spinnerMPos);
        spinnerMonth.setAdapter(adapterM);
        spinnerMonth.setSelection(spinnerMPos);
        printLogs(LogTag,"initializeInputs","spinnerMPosPP"+spinnerMPos);
        spinnerMonth.setAdapter(adapterM);
    }

    @Override
    protected void initializeListeners() {
        printLogs(LogTag,"initializeListeners","init");
        mDateInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateInput();
            }

        });
        btnAddRemark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(attendance_input_str.equals("")){
                    showProgress(false,mContentView,mProgressView);
                    String sTitle="Error :"+ActivityId+"-107";
                    String sMessage=getLabelFromDb("error_M407_107",R.string.error_M407_107);
                    String sButtonLabelClose="Close";
                    ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                }
                else{
                    final String remark = inputRemarks.getText().toString();
                    putApi(attendance_input_str,remark);
                }
                /*Bundle inputUri = new Bundle();
                inputUri.putString("generator", ActivityId);
                inputUri.putString("month_year", month_year);
                inputUri.putString("student_id", student_id);
                inputUri.putString("stipend_id", stipend_id);
                inputUri.putString("mName", mName);
                Intent intent = new Intent(MMonthlyFeedbackA.this, MLearnerGrantDetailsA.class);
                printLogs(LogTag,"onOptionsItemSelected","MLearnerGrantDetailsA");
                intent.putExtras(inputUri);
                startActivity(intent);
                finish();*/
            }
        });

        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,int position, long id) {
                int item =position;
                selectedYear= yearsSet[item].getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });

        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,int position, long id) {
                int item =position;
                selectedMonth = monthsSet[item].getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });

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

    public void fetchData(int selectedYear, int selectedMonth){
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.M_REF_407;
        FINAL_URL=FINAL_URL+"?token="+token+"&learner_id="+student_id+"&month_year="+selectedYear+"-"+selectedMonth;
        printLogs(LogTag,"fchData","URL : "+FINAL_URL);
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
                            String aId2 = rec.getString("attendance_id");
                            String aDate3 = rec.getString("date");
                            String aDay4 = rec.getString("day");
                            String aDisCord5 = rec.getString("distance_from_workstation");
                            String aLearnerStatus6 = rec.getString("learner_status");
                            String aLearnerStatusId7 = rec.getString("learner_status_id");
                            String aSignInHours8 = rec.getString("sign_in_hours");
                            String aSignInMin9 = rec.getString("sign_in_min");
                            String aSignInSec10 = rec.getString("sign_in_sec");
                            String aSignOutHours11 = rec.getString("sign_out_hours");
                            String aSignOutMin12 = rec.getString("sign_out_min");
                            String aSignOutSec13 = rec.getString("sign_out_sec");
                            String aSignInTime14 = rec.getString("sign_in_time");
                            String aSignOutTime15 = rec.getString("sign_out_time");
                            String aHoursWorked16 = rec.getString("hours_worked");
                            String aLearnerComment17 = rec.getString("learner_comment");
                            LLAttendanceContainer.setVisibility(View.VISIBLE);
                            rDataObj.addItem(rDataObj.createItem("",aId2,aDate3,aDay4,aDisCord5,aLearnerStatus6,aLearnerStatusId7,aSignInHours8,aSignInMin9,aSignInSec10,aSignOutHours11,aSignOutMin12,aSignOutSec13,aSignInTime14,aSignOutTime15,aHoursWorked16,aLearnerComment17));
                            showList();
                        }
                        btnAddRemark.setVisibility(View.VISIBLE);
                        LLaddRemarksContainer.setVisibility(View.VISIBLE);
                        showProgress(false,mContentRView,mProgressRView);
                    }
                    else if(Status.equals("2")) {
                        showProgress(false,mContentView,mProgressView);
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
                })  {
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

    public void validateInput() {
            String ErrorTitle = "";
            String ErrorMessage = "";
            Boolean isError = true;

            printLogs(LogTag,"validateInput","INPUTS : "+ this.selectedMonth +"---" + this.selectedYear);
            printLogs(LogTag,"validateInput","INPUTS : "+isSpinnerValid(this.selectedMonth));
            if (isSpinnerValid(this.selectedMonth)) {
                if (isSpinnerValid(this.selectedYear)) {
                    isError = false;
                }
            }

            printLogs(LogTag,"validateInput","INPUTS : "+isError);
            if(isError){
                ErrorTitle ="Error :"+ActivityId+"-101";
                ErrorMessage =getLabelFromDb("m_195_error",R.string.m_195_error);
                spinnerError(ErrorTitle,ErrorMessage);
            }
            else{
                showProgress(true,mContentRView,mProgressRView);
                rDataObjList.clear();
                callHeaderBuilder();
                MCurrentAttAdapter adapter = new MCurrentAttAdapter(rDataObjList,baseApcContext,activityIn,this);
                adapter.notifyDataSetChanged();
                fetchData(selectedYear, selectedMonth);
            }
        }

    private void putApi(final String attendance_input,final String remark) {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.M_ATTENDENCE_APPRVAL;
        printLogs(LogTag,"putApi","FINAL_URL ---"+FINAL_URL);


        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put("attendance_approval", attendance_input);
            jsonBody.put("remark",remark);
            jsonBody.put("student_id",student_id);
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
                    if(Status.equals("1")) {
                        showProgress(false,mContentView,mProgressView);
                        String sTitle=getLabelFromDb("m_M407_title",R.string.m_M407_title);
                        String sMessage=getLabelFromDb("m_M407_message",R.string.m_M407_message);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showSuccessDialogEditAttendance(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose, thisClass);
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
                }){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/json; charset=utf-8");
                header.put("Accept","*/*");
                return header;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    public void customRedirector(){
            Intent intent = new Intent(MCurrentAttA.this, MDashboardDA.class);
            printLogs(LogTag,"customRedirectorr","MDashboardDA");
            startActivity(intent);
            finish();
     }

     public void getAllDetails() {
        final TextView tv_leadEmpName , tv_setaName , tv_GrantName , tv_GrantNo , tv_LName , tv_LEmail ,tv_LId , tv_LTele ,
                 tv_GAName , tv_GAEmail , tv_GATele , tv_AnnualL , tv_PaidL , tv_UnpaidL , tv_SickL ;
         final LinearLayout LL_GD, LL_LD,LL_LT,LL_GAD;
         LL_GD = findViewById(R.id.LL_GD);
         LL_LD = findViewById(R.id.LL_LD);
         LL_LT = findViewById(R.id.LL_LT);
         LL_GAD = findViewById(R.id.LL_GAD);

         LL_GD.setVisibility(View.VISIBLE);
         LL_LD.setVisibility(View.VISIBLE);
         LL_LT.setVisibility(View.VISIBLE);
         LL_GAD.setVisibility(View.VISIBLE);
         tv_leadEmpName = findViewById(R.id.txtLeadEmpName);
         tv_setaName = findViewById(R.id.txtSetaName);
         tv_GrantName = findViewById(R.id.txtGrantName);
         tv_GrantNo = findViewById(R.id.txtGrantNo);
         tv_LName = findViewById(R.id.txtLearnerName);
         tv_LEmail = findViewById(R.id.txtLearnerEmail);
         tv_LId = findViewById(R.id.txtLearnerId);
         tv_LTele = findViewById(R.id.txtLearnerTele);
         tv_GAName = findViewById(R.id.txtGrantAdminName);
         tv_GAEmail = findViewById(R.id.txtGrantEmail);
         tv_GATele = findViewById(R.id.txtGrantATele);
         tv_AnnualL = findViewById(R.id.txtTotalALeave);
         tv_PaidL = findViewById(R.id.txtTotalPaidLeave);
         tv_UnpaidL = findViewById(R.id.txtTotalUnPaidLeave);
         tv_SickL = findViewById(R.id.txtTotalSickLeave);

         String Label = getLabelFromDb("lblLeadEmpName",R.string.lblLeadEmpName);
         lblView = (TextView)findViewById(R.id.lblLeadEmpName);
         lblView.setText(Label);
         Label = getLabelFromDb("lblSetaName",R.string.lblSetaName);
         lblView = (TextView)findViewById(R.id.lblSetaName);
         lblView.setText(Label);
         Label = getLabelFromDb("lblGrantName",R.string.lblGrantName);
         lblView = (TextView)findViewById(R.id.lblGrantName);
         lblView.setText(Label);
         Label = getLabelFromDb("lblGrantNo",R.string.lblGrantNo);
         lblView = (TextView)findViewById(R.id.lblGrantNo);
         lblView.setText(Label);

         Label = getLabelFromDb("lblLearnerName",R.string.lblLearnerName);
         lblView = (TextView)findViewById(R.id.lblLearnerName);
         lblView.setText(Label);
         Label = getLabelFromDb("lblLearnerEmail",R.string.lblLearnerEmail);
         lblView = (TextView)findViewById(R.id.lblLearnerEmail);
         lblView.setText(Label);
         Label = getLabelFromDb("lblLearnerTele",R.string.lblLearnerTele);
         lblView = (TextView)findViewById(R.id.lblLearnerTele);
         lblView.setText(Label);
         Label = getLabelFromDb("lblLearneriD",R.string.lblLearneriD);
         lblView = (TextView)findViewById(R.id.lblLearnerId);
         lblView.setText(Label);

         Label = getLabelFromDb("lblTotalLeave",R.string.lblTotalLeave);
         lblView = (TextView)findViewById(R.id.lblTotalALeave);
         lblView.setText(Label);
         Label = getLabelFromDb("lblTotalUnPaidLeave",R.string.lblTotalUnPaidLeave);
         lblView = (TextView)findViewById(R.id.lblTotalUnPaidLeave);
         lblView.setText(Label);
         Label = getLabelFromDb("lblTotalPaidLeave",R.string.lblTotalPaidLeave);
         lblView = (TextView)findViewById(R.id.lblTotalPaidLeave);
         lblView.setText(Label);
         Label = getLabelFromDb("lblTotalSickLeave",R.string.lblTotalSickLeave);
         lblView = (TextView)findViewById(R.id.lblTotalSickLeave);
         lblView.setText(Label);

         Label = getLabelFromDb("lblGAName",R.string.lblGAName);
         lblView = (TextView)findViewById(R.id.lblGrantAdminName);
         lblView.setText(Label);
         Label = getLabelFromDb("lblGAEmail",R.string.lblGAEmail);
         lblView = (TextView)findViewById(R.id.lblGrantEmail);
         lblView.setText(Label);
         Label = getLabelFromDb("lblGATele",R.string.lblGATele);
         lblView = (TextView)findViewById(R.id.lblGranATele);
         lblView.setText(Label);

         Label = getLabelFromDb("h_GrantDetails",R.string.h_GrantDetails);
         lblView = (TextView)findViewById(R.id.h_GrantDetails);
         lblView.setText(Label);

         Label = getLabelFromDb("h_LDetails",R.string.h_LDetails);
         lblView = (TextView)findViewById(R.id.h_LDetails);
         lblView.setText(Label);

         Label = getLabelFromDb("h_GADetail",R.string.h_GADetail);
         lblView = (TextView)findViewById(R.id.h_GADetails);
         lblView.setText(Label);

         Label = getLabelFromDb("h_TotalLeaves",R.string.h_TotalLeaves);
         lblView = (TextView)findViewById(R.id.h_totalLeaves);
         lblView.setText(Label);

         String token = userSessionObj.getToken();
         String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.GET_ALL_DETAILS_URL;
         FINAL_URL=FINAL_URL+"?token="+token+"&learner_id="+student_id;
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
                         JSONObject dataM = outputJson.getJSONObject(KEY_DATA);
                         tv_leadEmpName.setText(dataM.getString("lead_emp"));
                         tv_setaName.setText(dataM.getString("setaName"));
                         tv_GrantName.setText(dataM.getString("grantName"));
                         tv_GrantNo.setText(dataM.getString("g_d_grant_number"));
                         tv_GAName.setText(dataM.getString("leadManager"));
                         tv_GAEmail.setText(dataM.getString("leadManagerEmail"));
                         tv_GATele.setText(dataM.getString("leadManagerContact"));
                         tv_AnnualL.setText(dataM.getString("annual_leave"));
                         tv_SickL.setText(dataM.getString("sick_leave"));
                         tv_PaidL.setText(dataM.getString("other_paid_leave"));
                         tv_UnpaidL.setText(dataM.getString("unpaid_leave"));
                         tv_LName.setText(dataM.getString("studName"));
                         tv_LId.setText(dataM.getString("s_id_no"));
                         tv_LEmail.setText(dataM.getString("stu_email"));
                         tv_LTele.setText(dataM.getString("stu_cell"));
                         showProgress(false, mContentView, mProgressView);
                     }
                     else if(Status.equals("2")) {
                         showProgress(false,mContentView,mProgressView);
                     }

                     else{
                         //showProgress(false,mContentView,mProgressView);
                         printLogs(LogTag, "fetchData", "error_M345_101 : DATA_ERROR");
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
             }
         };
         RequestQueue requestQueue = Volley.newRequestQueue(this);
         jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                 10000,
                 DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                 DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
         requestQueue.add(jsonObjectRequest);
     }

    public void callHeaderBuilder(){
        printLogs(LogTag,"callHeaderBuilder","init");
        String tHeader3 = getLabelFromDb("t_head_M407_in_date",R.string.t_head_M407_in_date);
        String tHeader4 = getLabelFromDb("t_head_M407_in_day",R.string.t_head_M407_in_day);
        String tHeader16 = getLabelFromDb("t_head_M407_in_d",R.string.t_head_M407_in_d);
        String tHeader5 = getLabelFromDb("t_head_M407_in_edit",R.string.t_head_M407_in_edit);
        String tHeader6 = getLabelFromDb("t_head_M407_in_time",R.string.t_head_M407_in_time);
        String tHeader14 = getLabelFromDb("t_head_M407_out_time",R.string.t_head_M407_out_time);
        String tHeader15 = getLabelFromDb("t_head_M407_hours_worked",R.string.t_head_M407_hours_worked);
        String tHeader17 = getLabelFromDb("t_head_M407_l_c",R.string.t_head_M407_l_c);

        rDataObj.addItem(rDataObj.createItem("","",tHeader3,tHeader4,tHeader5,tHeader6,tHeader14,tHeader15,tHeader16,tHeader17,"","","","","","",""));
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        printLogs(LogTag,"setupRecyclerView","init");
        recyclerView.setAdapter(new MCurrentAttAdapter(rDataObjList,baseApcContext,activityIn,this));
    }

    public void showList(){
        printLogs(LogTag,"showList","init");
        List<MCurrentAttObj.Item> AttData = rDataObj.getITEMS();
        MCurrentAttAdapter adapter = new MCurrentAttAdapter(AttData,baseApcContext,activityIn,this);
        recyclerViewQ.setAdapter(adapter);
        recyclerViewQ.setNestedScrollingEnabled(false);
        showProgress(false,mContentRView,mProgressRView);
    }
    public void setStatusRemarks(String attendance_input){
        printLogs(LogTag,"setStatusRemarks","GET_ATT_APPROVAL f: "+attendance_input);
        attendance_input_str = attendance_input;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(MCurrentAttA.this,MDashboardDA.class);
            printLogs(LogTag,"onOptionsItemSelected","sDashboard");
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