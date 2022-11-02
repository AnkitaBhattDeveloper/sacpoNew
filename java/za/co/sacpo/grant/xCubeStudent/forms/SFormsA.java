package za.co.sacpo.grant.xCubeStudent.forms;


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

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.adapter.SFormsAdapter;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.baseFramework.StudentBaseDrawerA;
import za.co.sacpo.grant.xCubeLib.dataObj.SFormsObj;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsGrantSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsStudentSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeLib.spinners.SpinnerSet;
import za.co.sacpo.grant.xCubeLib.spinners.SpinnerSetAdapter;

public class SFormsA extends StudentBaseDrawerA {

    private String ActivityId = "130";
    public View mProgressView, mContentView, mProgressRView, mContentRView;

    public Button mDateInputButton;
    private TextView lblView, mIGrantNameText, mIStartDateText, mIEndDateText, mIFromTTText;
    private Spinner spinnerMonth, spinnerYear;
    private static SpinnerSet[] yearsSet, monthsSet;
    private SpinnerSetAdapter adapterS, adapterM;
    private int selectedYear, selectedMonth;
    private RecyclerView recyclerViewQ;
    public SFormsObj rDataObj = new SFormsObj();
    private List<SFormsObj.Item> rDataObjList = null;

    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId) {
        baseApcContext = cnt;CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
    }

    @Override
    protected void initializeInputs() {
        printLogs(LogTag, "initializeInputs", "init");
        String currentYear = "";
        SimpleDateFormat df_year = new SimpleDateFormat("yyyy");
        Calendar cY = Calendar.getInstance();
        currentYear = df_year.format(cY.getTime());

        String currentMonth = "";
        SimpleDateFormat df_month = new SimpleDateFormat("MM");
        Calendar cM = Calendar.getInstance();
        currentMonth = df_month.format(cM.getTime());

        selectedYear = Integer.parseInt(currentYear);
        selectedMonth = Integer.parseInt(currentMonth);
        studentSessionObj = new OlumsStudentSession(baseApcContext);
        Boolean isGrantActive = studentSessionObj.getIsActiveGrant();
        if (isGrantActive) {
            printLogs(LogTag, "isGrantActive", "if");
            String spinnerOptionYear = getLabelFromDb("l_130_spinner_option_year", R.string.l_130_spinner_option_year);
            String spinnerOptionMonth = getLabelFromDb("l_130_spinner_option_month", R.string.l_130_spinner_option_month);
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
                printLogs(LogTag, "Yformatter", "simpledateformat");

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
            adapterS = new SpinnerSetAdapter(SFormsA.this, android.R.layout.simple_spinner_item, yearsSet);
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
            adapterM = new SpinnerSetAdapter(SFormsA.this, android.R.layout.simple_spinner_item, monthsSet);
            spinnerMonth.setAdapter(adapterM);
        }
    }

    @Override
    protected void initializeLabels() {
        printLogs(LogTag, "initializeLabels", "initialLabels works");
        String Label = getLabelFromDb("b_130_filter", R.string.b_130_filter);
        mDateInputButton.setText(Label);

        Label = getLabelFromDb("l_130_form", R.string.l_130_form);
        lblView = (TextView) findViewById(R.id.lblForm);
        lblView.setText(Label);

        Label = getLabelFromDb("h_130",R.string.h_130);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);


    }

    @Override
    protected void initializeViews() {
        printLogs(LogTag, "initializeViews", "initializeViews works");
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
        recyclerViewQ = (RecyclerView) findViewById(R.id.rVForm);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);

        View recyclerView = findViewById(R.id.rVForm);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

    }

    @Override
    protected void initializeListeners() {
        printLogs(LogTag, "initializeListeners", "initializeListeners works");
        mDateInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateInput();
            }
        });
        mIFromTTText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String sToolTip = getLabelFromDb("tt_130_scroll", R.string.tt_130_scroll);
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
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });
    }

    public void validateInput() {
        printLogs(LogTag, "validateInput", "validateInput works");
        String ErrorTitle = "";
        String ErrorMessage = "";
        Boolean isError = true;
        if (isSpinnerValid(selectedMonth)) {
            if (isSpinnerValid(selectedYear)) {
                isError = false;
            }
        }
        if (isError) {
            printLogs(LogTag, "isError", "ii");
            ErrorTitle = "Error :" + ActivityId + "-101";
            ErrorMessage = getLabelFromDb("error_130_101", R.string.error_130_101);
            spinnerError(ErrorTitle, ErrorMessage);
        } else {

            showProgress(true, mContentRView, mProgressRView);
            rDataObjList.clear();
            callHeaderBuilder();
            SFormsAdapter adapter = new SFormsAdapter(rDataObjList, baseApcContext, activityIn);
            adapter.notifyDataSetChanged();
            fetchData(selectedYear, selectedMonth);
        }
    }

    @Override
    protected void setLayoutXml() {
        setContentView(R.layout.a_forms);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        printLogs(LogTag, "onCreate", "Bundle");
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(), this, this.getClass().getSimpleName(),ActivityId);
        bootStrapInit();
        internetChangeBroadCast();
        fetchVersionData();
        verifyVersion();
    }

    protected void fetchVersionData() {
        printLogs(LogTag, "fetchVersionData", "exitConnected");
        syncUpdates(baseApcContext, activityIn);
    }

    @Override
    protected void internetChangeBroadCast() {
        registerBroadcastIC();
    }

    protected void verifyVersion() {
        printLogs(LogTag, "verifyVersion", "init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag, "verifyVersion", "isUpdate " + isUpdate);
        printLogs(LogTag, "verifyVersion", "isUpdate " + utilSessionObj.getIsUpdateRequired());
        if (isUpdate == true) {
            Intent intent = new Intent(SFormsA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    public void callHeaderBuilder() {
        printLogs(LogTag, "callHeaderBuilder", "callHeaderBuilder i");
        String tHeader3 = getLabelFromDb("t_head_130_F_FormID", R.string.t_head_130_F_FormID);
        String tHeader4 = getLabelFromDb("t_head_130_F_FormName", R.string.t_head_130_F_FormName);
        String tHeader5 = getLabelFromDb("t_head_130_F_Learner", R.string.t_head_130_F_Learner);
        String tHeader6 = getLabelFromDb("t_head_130_F_HostEmployer", R.string.t_head_130_F_HostEmployer);
        String tHeader7 = getLabelFromDb("t_head_130_F_LeadEmployer", R.string.t_head_130_F_LeadEmployer);
        String tHeader8 = getLabelFromDb("t_head_130_F_Completion", R.string.t_head_130_F_Completion);

        rDataObj.addItem(rDataObj.createItem(0, 0, tHeader3, tHeader4, tHeader5, tHeader6, tHeader7, tHeader8));
    }

    @Override
    protected void bootStrapInit() {
        printLogs(LogTag, "bootStrapInit", "bootStrapInit msg");
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext, activityIn);
        if (isConnected) {
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);

            initMenusCustom(ActivityId,baseApcContext,activityIn);
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
            fetchData(selectedYear, selectedMonth);
            showProgress(false, mContentView, mProgressView);
        }
    }

    public void callDataApi() {

        printLogs(LogTag, "callDataApi", "callDataApi msg");
        userToken = userSessionObj.getToken();
        if (!TextUtils.isEmpty(userToken)) {
            syncUserData(baseApcContext, activityIn);
            syncStudentData(baseApcContext, activityIn);
            syncGrantData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn,ActivityId);
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        printLogs(LogTag, "setupRecyclerView", "setupRecyclerView msg");
        recyclerView.setAdapter(new SFormsAdapter(rDataObjList, baseApcContext, activityIn));
    }

    public void showList() {
        printLogs(LogTag, "showList", "showList msg");
        List<SFormsObj.Item> FormData = rDataObj.getITEMS();
        SFormsAdapter adapter = new SFormsAdapter(FormData, baseApcContext, activityIn);
        recyclerViewQ.setAdapter(adapter);
        showProgress(false, mContentRView, mProgressRView);
    }

    public void fetchData(int selectedYearInput, int selectedMonthInput) {
        printLogs(LogTag, "fetchData", "fetchData msg");
        String token = userSessionObj.getToken();
        String grantId = studentSessionObj.getGrantId();
        /*url helper form..*/
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.FORMS_STUDENTS;
        FINAL_URL = FINAL_URL + "?token=" + token + "&grant_id=" + grantId + "&date=" + selectedYearInput + "-" + selectedMonthInput;
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
                            int pos = i + 1;
                            /*doubt*/
                            printLogs(LogTag, "JSONArray", "JSONArray i");
                            JSONObject rec = dataM.getJSONObject(i);
                            int aId2 = Integer.parseInt(rec.getString("f_id"));
                            String sFname3 = rec.getString("f_name");
                            String sFid11 = rec.getString("f_code");
                            String sFlearner4 = "";

                            String sFhostEmp5 = rec.getString("f_role_type2");
                            String sFleadEmp6 = rec.getString("f_role_type1");
                            String sFcomplition7 = rec.getString("completion");


                            rDataObj.addItem(rDataObj.createItem(pos, aId2,sFid11, sFname3, sFlearner4, sFhostEmp5, sFleadEmp6, sFcomplition7));
                            showList();
                        }
                        showProgress(false, mContentView, mProgressView);

                    }
                    else if(Status.equals("2")) {
                        showProgress(false,mContentView,mProgressView);
                    }

                    else {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-100";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "-101";
                    String sMessage = getLabelFromDb("error_130_101", R.string.error_130_101);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showProgress(false, mContentView, mProgressView);
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
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