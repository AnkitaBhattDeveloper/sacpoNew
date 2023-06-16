package za.co.sacpo.grantportal.xCubeMentor.forms;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

import za.co.sacpo.grantportal.AppUpdatedA;
import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.adapter.MFormsAdapter;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.MentorBaseDrawerA;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.component.Utils;
import za.co.sacpo.grantportal.xCubeLib.dataObj.MFormsObj;
import za.co.sacpo.grantportal.xCubeLib.db.DbHelper;
import za.co.sacpo.grantportal.xCubeLib.db.DbSchema;
import za.co.sacpo.grantportal.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grantportal.xCubeLib.spinners.SpinnerSet;
import za.co.sacpo.grantportal.xCubeLib.spinners.SpinnerSetAdapter;
/*201*/
public class MFormsA extends MentorBaseDrawerA {

    private String ActivityId = "201";
    public View mProgressView, mContentView, mProgressRView, mContentRView;
    public Button mDateInputButton;
    private TextView lblView, mIGrantNameText, mIStartDateText, mIEndDateText, mIFromTTText;
    private Spinner spinnerMonth, spinnerYear;
    private static SpinnerSet[] yearsSet, monthsSet;
    private SpinnerSetAdapter adapterS, adapterM;
    private int selectedYear, selectedMonth;
    private RecyclerView recyclerViewQ;
    public MFormsObj rDataObj = new MFormsObj();
    private List<MFormsObj.Item> rDataObjList = null;

    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId) {
        baseApcContext = cnt;CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        CAId = CAId;
        printLogs(lt,"setBaseApcContextParent","weAreIn");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(), this, this.getClass().getSimpleName(), ActivityId);
        printLogs(LogTag, "onCreate", "init");
        bootStrapInit();
    }

    @Override
    protected void bootStrapInit() {
        Boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext, activityIn);
        if (isConnected) {

            printLogs(LogTag, "bootStrapInit", "initConnected");
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);

            initMenusCustom(ActivityId, baseApcContext, activityIn);
            fetchVersionData();
            verifyVersion();
            internetChangeBroadCast();
            initDrawer();
            initializeViews();
            showProgress(true, mContentView, mProgressView);
            initializeLabels();
            initializeListeners();
            userToken = userSessionObj.getToken();
            syncToken(baseApcContext, activityIn);
            if (TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
            callDataApi();
            initializeInputs();
            callHeaderBuilder();
            //fetchData(selectedYear,selectedMonth);
            showProgress(false,mContentView,mProgressView);
        }
    }
    @Override
    protected void internetChangeBroadCast() {
        printLogs(LogTag, "internetChangeBroadCast", "init");
        registerBroadcastIC();
    }

    @Override
    protected void fetchVersionData() {
        printLogs(LogTag, "fetchVersionData", "exitConnected");
        syncUpdates(baseApcContext, activityIn);
    }

    @Override
    protected void verifyVersion() {
        printLogs(LogTag, "verifyVersion", "init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag, "verifyVersion", "isUpdate " + isUpdate);
        printLogs(LogTag, "verifyVersion", "isUpdate " + utilSessionObj.getIsUpdateRequired());
        if (isUpdate == true) {
            Intent intent = new Intent(MFormsA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void setLayoutXml() {
        setContentView(R.layout.a_mforms);
        printLogs(LogTag,"setLayoutXml","a_mforms");
    }

    @Override
    protected void initializeInputs() {
        printLogs(LogTag,"initializeInputs","init");
        dbSetaObj = DbHelper.getInstance(this);

        Cursor resUTM = dbSetaObj.getAllByType(DbSchema.TABLE_MASTER_ARRAYS,DbSchema.COLUMN_M_A_TYPE,3);
        int total_leave_type = resUTM.getCount();



        Cursor resSTS = dbSetaObj.getAllData(DbSchema.TABLE_STUDENTS);
        int total_students = resSTS.getCount();



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



        String spinnerOptionYear= getLabelFromDb("l_S111_spinner_year",R.string.l_S111_spinner_option_year);
        String spinnerOptionMonth= getLabelFromDb("l_S111_spinner_year",R.string.l_S111_spinner_option_month);
        String StartDate = "2018-06-01";
        String EndDate = "2019-08-01";

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
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


        adapterS = new SpinnerSetAdapter(MFormsA.this, android.R.layout.simple_spinner_item, yearsSet);
        spinnerYear.setAdapter(adapterS);
        adapterS = new SpinnerSetAdapter(MFormsA.this, android.R.layout.simple_spinner_item, yearsSet);
        spinnerYear.setAdapter(adapterS);

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
        adapterM = new SpinnerSetAdapter(MFormsA.this, android.R.layout.simple_spinner_item, monthsSet);
        spinnerMonth.setAdapter(adapterM);



    }

    @Override
    protected void initializeLabels() {
        String Label = getLabelFromDb("b_201_filter", R.string.b_201_filter);
        mDateInputButton.setText(Label);

        Label = getLabelFromDb("l_201_form", R.string.l_201_form);
        lblView = (TextView) findViewById(R.id.lblForm);
        lblView.setText(Label);

        Label = getLabelFromDb("h_201",R.string.h_201);
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
        mIFromTTText = (TextView) findViewById(R.id.lblFormTT);

        rDataObjList = rDataObj.getITEMS();
        recyclerViewQ = (RecyclerView) findViewById(R.id.rVmForm);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);

        View recyclerView = findViewById(R.id.rVmForm);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

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
        mIFromTTText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sToolTip = getLabelFromDb("tt_201_scroll", R.string.tt_201_scroll);
                showTooltip(mIFromTTText, sToolTip, 4);
            }
        });
        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                int item = position;
                selectedYear = yearsSet[item].getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });
        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                int item = position;
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

    public void validateInput() {
        String ErrorTitle = "";
        String ErrorMessage = "";
        Boolean isError = true;

        if (isSpinnerValid(selectedMonth)) {
            if (isSpinnerValid(selectedYear)) {
                isError = false;
            }
        }

        if(isError){
            ErrorTitle ="Error :"+ActivityId+"-101";
            ErrorMessage =getLabelFromDb("error_201_101",R.string.error_201_101);
            spinnerError(ErrorTitle,ErrorMessage);
        }
        else{
            showProgress(true,mContentRView,mProgressRView);
            rDataObjList.clear();
            callHeaderBuilder();
            MFormsAdapter adapter = new MFormsAdapter(rDataObjList, baseApcContext, activityIn);
            adapter.notifyDataSetChanged();
            fetchData(selectedYear, selectedMonth);
        }
    }
    public void callHeaderBuilder(){
        String tHeader3 = getLabelFromDb("t_head_203_student_name",R.string.t_head_203_student_name);
        String tHeader4 = getLabelFromDb("t_head_203_student_id",R.string.t_head_203_student_id);
        String tHeader5 = getLabelFromDb("t_head_203_lead_emp",R.string.t_head_203_lead_emp);
        String tHeader6 = getLabelFromDb("t_head_203_grant_name",R.string.t_head_203_grant_name);
        String tHeader7 = getLabelFromDb("t_head_203_grant_start_date",R.string.t_head_203_grant_start_date);
        String tHeader8 = getLabelFromDb("t_head_203_grant_end_date",R.string.t_head_203_grant_end_date);
        String tHeader9 = getLabelFromDb("t_head_203_last_login",R.string.t_head_203_last_login);
        String tHeader10 = getLabelFromDb("t_head_203_form_id",R.string.t_head_203_form_id);
        String tHeader11 = getLabelFromDb("t_head_149_M_Fid11",R.string.t_head_149_M_Fid11);

  /* <string name="t_head_203_student_name">STUDENT NAME</string>
    <string name="t_head_203_student_id">STUDENT ID</string>
    <string name="t_head_203_lead_emp">LEADER EMPLOYEE </string>
    <string name="t_head_203_host_emp">HOST  EMPLOYEE </string>
    <string name="t_head_203_grant_name">GRANT NAME </string>
    <string name="t_head_203_grant_start_date">GRANT START DATE </string>
    <string name="t_head_203_grant_end_date">GRANT END DATE </string>
    <string name="t_head_203_last_login">LAST LOGIN </string>
    <string name="t_head_203_form_id">ORM ID </string>*/

        rDataObj.addItem(rDataObj.createItem(0,0,tHeader3,tHeader4,tHeader5,tHeader6,tHeader7,tHeader8,tHeader9,tHeader10,tHeader11));
    }

        public void fetchData(int selectedYearInput, int selectedMonthInput) {
        String token = userSessionObj.getToken();
     //  String grantId = studentSessionObj.getGrantId();
        /*url helper form..*/
       String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.LERNERS_FORM_LIST;
       FINAL_URL = FINAL_URL + "?token=" + token;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
                @SuppressLint("SetTextI18n")
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject outputJson= new JSONObject(String.valueOf(response));
                        printLogs(LogTag, "fetchData", "response : " + response);
                        String Status = outputJson.getString(KEY_STATUS);
                    if (Status.equals("1")) {
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        for (int i = 0; i < dataM.length(); i++) {
                            int pos = i+1;

                            printLogs(LogTag,"fetchData","dataM : "+dataM.length());
                            JSONObject rec = dataM.getJSONObject(i);
                            int aId2 = Integer.parseInt(rec.getString("s_s_g_id"));
                            String tHeader3 = rec.getString("Student");
                            String tHeader4 = rec.getString("m_s_student_id");
                            String tHeader5 = rec.getString("leademp");
                            String tHeader6 = rec.getString("g_d_name");
                            String tHeader7 = rec.getString("grant_start_date");
                            String tHeader8 =rec.getString("grant_start_date");
                            String tHeader9 = rec.getString("form_id");
                            String sFbtnDownloadSignedForm10 ="";
                            String mFid11 = "";


                            rDataObj.addItem(rDataObj.createItem(pos, aId2, tHeader3, tHeader4, tHeader5, tHeader6, tHeader7, tHeader8, tHeader9, sFbtnDownloadSignedForm10, mFid11));
                            showList();
                            printLogs(LogTag,"fetchData","datshowListaM : "+response);
                        }


                        showProgress(false,mContentView,mProgressView);
                    }
                    else if(Status.equals("2")) {
                        showProgress(false,mContentView,mProgressView);
                    }


                    else {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-102";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                    //    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    printLogs(LogTag,"onResponse","103 : "+e.getMessage());
                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "-103";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-104";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);

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


    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        printLogs(LogTag,"setupRecyclerView","init");
        recyclerView.setAdapter(new MFormsAdapter(rDataObjList,baseApcContext,activityIn));
    }

    public void showList(){
        printLogs(LogTag,"fetchData","showList : ");
        List<MFormsObj.Item> FormData = rDataObj.getITEMS();
        MFormsAdapter adapter = new MFormsAdapter(FormData,baseApcContext,activityIn);
        recyclerViewQ.setAdapter(adapter);
        showProgress(false,mContentRView,mProgressRView);
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