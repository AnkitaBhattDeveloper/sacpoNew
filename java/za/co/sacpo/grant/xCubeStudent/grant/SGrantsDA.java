package za.co.sacpo.grant.xCubeStudent.grant;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.adapter.SGrantAdapter;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.baseFramework.StudentBaseDrawerA;
import za.co.sacpo.grant.xCubeLib.dataObj.SGrantObj;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsGrantSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsStudentSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeLib.spinners.SpinnerSet;
import za.co.sacpo.grant.xCubeLib.spinners.SpinnerSetAdapter;

public class SGrantsDA extends StudentBaseDrawerA {
    private String ActivityId="135";
    public View mProgressView, mContentView,mProgressRView, mContentRView;

    public Button mDateInputButton;
    private TextView lblView,mIGrantNameText,mIStartDateText,mIEndDateText,mIAttendanceTTText;
    private Spinner spinnerMonth,spinnerYear;
    private static SpinnerSet[] yearsSet,monthsSet;
    private SpinnerSetAdapter adapterS,adapterM;
    private int selectedYear,selectedMonth;
    private RecyclerView recyclerViewQ;
    public SGrantObj rDataObj = new SGrantObj();
    private List<SGrantObj.Item> rDataObjList = null;
    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId){
        baseApcContext = cnt;CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
    }
    @Override
    protected void initializeInputs(){
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
        studentSessionObj = new OlumsStudentSession(baseApcContext);
        Boolean isGrantActive= studentSessionObj.getIsActiveGrant();
        if(isGrantActive) {
            String spinnerOptionYear= getLabelFromDb("l_135_spinner_option_year",R.string.l_135_spinner_option_year);
            String spinnerOptionMonth= getLabelFromDb("l_135_spinner_option_month",R.string.l_135_spinner_option_month);
            grantSessionObj = new OlumsGrantSession(baseApcContext);
            String StartDate = grantSessionObj.getGrantSDate();
            String EndDate = grantSessionObj.getGrantEDate();
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String grantName = grantSessionObj.getGrantName();
            mIGrantNameText.setText(grantName);
            mIStartDateText.setText(StartDate);
            mIEndDateText.setText(EndDate);

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


            if (isGrantActive) {
                DateFormat Yformatter = new SimpleDateFormat("yyyy");

                ArrayList<SpinnerSet> YearsData = new ArrayList<SpinnerSet>();
                while (beginCalendarY.before(finishCalendarY) || beginCalendarY.equals(finishCalendarY)) {
                    SpinnerSet SpinSet = new SpinnerSet();
                    int YearInt = Integer.parseInt(Yformatter.format(beginCalendarY.getTime()).toUpperCase());
                    SpinSet.setId(YearInt);
                    SpinSet.setName(Yformatter.format(beginCalendarY.getTime()).toUpperCase());
                    YearsData.add(SpinSet);
                    beginCalendarY.add(Calendar.YEAR, 1);
                }

                int totalYearsInData = YearsData.size();
                int total_years = totalYearsInData + 1;
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
            } else {
                int total_years = 1;
                yearsSet = new SpinnerSet[total_years];
                yearsSet[0] = new SpinnerSet();
                yearsSet[0].setId(0);
                yearsSet[0].setName(spinnerOptionYear);
            }
            adapterS = new SpinnerSetAdapter(SGrantsDA.this, android.R.layout.simple_spinner_item, yearsSet);
            spinnerYear.setAdapter(adapterS);

            if (isGrantActive) {
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
            } else {
                int total_months = 1;
                monthsSet = new SpinnerSet[total_months];
                monthsSet[0] = new SpinnerSet();
                monthsSet[0].setId(0);
                monthsSet[0].setName(spinnerOptionMonth);
            }
            adapterM = new SpinnerSetAdapter(SGrantsDA.this, android.R.layout.simple_spinner_item, monthsSet);
            spinnerMonth.setAdapter(adapterM);
        }
    }
    @Override
    protected void initializeLabels(){
        String Label = getLabelFromDb("b_135_filter",R.string.b_135_filter);
        mDateInputButton.setText(Label);

        Label = getLabelFromDb("l_135_grant_listing",R.string.l_135_grant_listing);
        lblView = (TextView)findViewById(R.id.lblGrantDetails);
        lblView.setText(Label);

        Label = getLabelFromDb("h_135",R.string.h_135);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);
    }
    @Override
    protected void initializeViews() {
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mContentRView = findViewById(R.id.content_container_r);
        mProgressRView = findViewById(R.id.progress_bar_r);

        mDateInputButton = (Button) findViewById(R.id.btnFilterData);
        spinnerMonth = (Spinner) findViewById(R.id.inputSpinnerMonth);
        spinnerYear = (Spinner) findViewById(R.id.inputSpinnerYear);

        mIGrantNameText = (TextView) findViewById(R.id.iGrantName);
        mIStartDateText = (TextView) findViewById(R.id.iStartDate);
        mIEndDateText = (TextView) findViewById(R.id.iEndDate);
        mIAttendanceTTText = (TextView) findViewById(R.id.lblAttendanceTT);

        rDataObjList = rDataObj.getITEMS();
        recyclerViewQ = (RecyclerView) findViewById(R.id.rVGrantsDetails);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);

        View recyclerView = findViewById(R.id.rVGrantsDetails);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

    }
    @Override
    protected void initializeListeners() {
        mDateInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateInput();
            }
        });
        mIAttendanceTTText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sToolTip=getLabelFromDb("tt_135_scroll",R.string.tt_135_scroll);
                showTooltip(mIAttendanceTTText,sToolTip,4);
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
    public void validateInput(){
        String ErrorTitle ="";
        String ErrorMessage ="";
        Boolean isError =true;
        if(isSpinnerValid(selectedMonth)){
            if(isSpinnerValid(selectedYear)){
                isError=false;
            }
        }
        if(isError){
            ErrorTitle ="Error :"+ActivityId+"-101";
            ErrorMessage =getLabelFromDb("error_135_101",R.string.error_135_101);
            spinnerError(ErrorTitle,ErrorMessage);
        }
        else{
            showProgress(true,mContentRView,mProgressRView);
            rDataObjList.clear();
            callHeaderBuilder();
            SGrantAdapter adapter = new SGrantAdapter(rDataObjList,baseApcContext,activityIn);
            adapter.notifyDataSetChanged();
            fetchData(selectedYear,selectedMonth);
        }
    }
    @Override
    protected void setLayoutXml()
    {
        setContentView(R.layout.a_grants);}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);
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
            Intent intent = new Intent(SGrantsDA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    public void callHeaderBuilder(){
        String tHeader3 = getLabelFromDb("lbl_135_grant_name",R.string.lbl_135_grant_name);
        String tHeader5 = getLabelFromDb("lbl_135_grant_category_name",R.string.lbl_135_grant_category_name);
        String tHeader4 = getLabelFromDb("lbl_135_grant_total_budget",R.string.lbl_135_grant_total_budget);
        String tHeader6 = getLabelFromDb("lbl_135_grant_start_date",R.string.lbl_135_grant_start_date);
        String tHeader7 = getLabelFromDb("lbl_135_grant_end_date",R.string.lbl_135_grant_end_date);
        String tHeader8 = getLabelFromDb("lbl_135_grant_daily_deduction",R.string.lbl_135_grant_daily_deduction);
        String tHeader9 = getLabelFromDb("b_135_mentor_name",R.string.b_135_mentor_name);
        rDataObj.addItem(rDataObj.createItem(0,0,tHeader3,tHeader4,tHeader5,tHeader6,tHeader7,tHeader8,tHeader9));
    }
    @Override
    protected void bootStrapInit() {
        Boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext,activityIn);
        if(isConnected ==true) {
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);

            initMenusCustom(ActivityId,baseApcContext,activityIn);
            initDrawer();
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
            fetchData(selectedYear,selectedMonth);
            showProgress(false,mContentView,mProgressView);
        }
    }
    public void callDataApi(){
        userToken = userSessionObj.getToken();
        if(!TextUtils.isEmpty(userToken)) {
            syncUserData(baseApcContext, activityIn);
            syncStudentData(baseApcContext, activityIn);
            syncGrantData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn,ActivityId);
        }
    }
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SGrantAdapter(rDataObjList,baseApcContext,activityIn));
    }
    public void showList(){
        List<SGrantObj.Item> AttData = rDataObj.getITEMS();
        SGrantAdapter adapter = new SGrantAdapter(AttData,baseApcContext,activityIn);
        recyclerViewQ.setAdapter(adapter);
        showProgress(false,mContentRView,mProgressRView);
    }
    public void fetchData(int selectedYearInput, int selectedMonthInput){
        String token = userSessionObj.getToken();
        String grantId = studentSessionObj.getGrantId();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.SGRANTLIST;
        FINAL_URL=FINAL_URL+"/token/"+token+"/grant_id/"+grantId+"/date/"+selectedYearInput+"-"+selectedMonthInput;
        StringRequest stringRequest = new StringRequest(Request.Method.GET,FINAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject outputJson = null;
                try {
                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        for (int i = 0; i < dataM.length(); i++) {
                            int pos = i+1;
                            JSONObject rec = dataM.getJSONObject(i);
                            int aId2 = Integer.parseInt(rec.getString("grant_id"));
                            String sGrantName3 = rec.getString("grant_name");
                            String sGrantCategory4 = rec.getString("category_name");
                            String sGrantTotalBudget5 = rec.getString("grant_total_Budget");
                            String sGrantStartDate6 = rec.getString("s_s_g_grant_start_date");
                            String sGrantEndDate7 = rec.getString("s_s_g_grant_end_date");
                            String sGrantDailyDeduction8 = rec.getString("grant_daily_deduction");
                            String SGrantMentorName9 = rec.getString("mentor_name");
                            rDataObj.addItem(rDataObj.createItem(pos,aId2,sGrantName3,sGrantCategory4,sGrantTotalBudget5,sGrantStartDate6,sGrantEndDate7,sGrantDailyDeduction8,SGrantMentorName9));
                            showList();
                        }
                        showProgress(false,mContentView,mProgressView);
                    }
                    else if(Status.equals("2")) {
                        showProgress(false,mContentRView,mProgressRView);
                    }
                    else{
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-102";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false,mContentView,mProgressView);
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
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-104";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

                    }
                }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(SGrantsDA.this);
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