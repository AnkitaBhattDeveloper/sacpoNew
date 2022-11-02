package za.co.sacpo.grant.xCubeStudent.attendance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeStudent.SDashboardDA;

public class EditAttendanceA extends BaseAPCPrivate {

    public String ActivityId = "S239";
    public View mProgressView, mContentView;
    private TextView lblView;
    private CalendarView calendarView;
    String claim_date,lastDate;

    //HardCoded Date just for testing
    String strDate = "2013-05-15T10:00:00-0700";

    @Override
    protected void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cAId) {
        baseApcContext = cnt;
        CAId = cAId;
        activityIn = ain;
        LogTag = lt;
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
            callFooter(baseApcContext, activityIn, ActivityId);

            initMenusCustom(ActivityId, baseApcContext, activityIn);
            fetchVersionData();
            verifyVersion();
            internetChangeBroadCast();
            initializeViews();
            initBackBtn();
            showProgress(true, mContentView, mProgressView);
            initializeLabels();
            initializeListeners();
            userToken = userSessionObj.getToken();
            syncToken(baseApcContext, activityIn);
            if (TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
             fetchData();
            callDataApi();
            initializeInputs();
            printLogs(LogTag, "bootStrapInit", "exitConnected");
            showProgress(false, mContentView, mProgressView);
        }
    }

    private void fetchData() {

        showProgress(true, mContentView, mProgressView);
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_239;
        FINAL_URL = FINAL_URL + "/token/" + token ;
        printLogs(LogTag, "fetchData", "URL : " + FINAL_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, FINAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject outputJson = null;
                try {
                    printLogs(LogTag, "fetchData", "RESPONSE : " + response);
                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if (Status.equals("1")) {
                        JSONObject dataM = outputJson.getJSONObject(KEY_DATA);

                        claim_date = dataM.getString("last_claim_date");
                         lastDate = claim_date;

                        showLastClaimDate(lastDate);



                        printLogs(LogTag, "fetchData", "claim_date"+claim_date);

                        showProgress(false, mContentView, mProgressView);
                        }

                    else if(Status.equals("2")) {
                        showProgress(false,mContentView,mProgressView);
                    }
                    else {
                        showProgress(false,mContentView,mProgressView);
                        printLogs(LogTag, "fetchData", "error_try_again : DATA_ERROR");
                        String sTitle = "Error :" + ActivityId + "-100";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    printLogs(LogTag, "fetchData", "error_try_again : " + e.getMessage());
                    e.printStackTrace();
                    showProgress(false,mContentView,mProgressView);
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
        RequestQueue requestQueue = Volley.newRequestQueue(EditAttendanceA.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);



    }

    private void showLastClaimDate(String lastDate) {

        String string_date = lastDate;
        printLogs(LogTag, "initializeViews", "milliseconds_VALUE "+string_date);
        printLogs(LogTag, "initializeViews", "xy_date_VALUE "+lastDate);

        //Disable  Dates according to last claim date...
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            Date da = sdf.parse(string_date);
            long milliseconds = da.getTime();
            printLogs(LogTag, "initializeViews", "milliseconds_VALUE "+milliseconds);
            String abcc = String.valueOf(milliseconds);
            calendarView.setMinDate(Long.parseLong(abcc));

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void initializeViews() {

        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        calendarView = findViewById(R.id.calendarView);

        //Disable Future Dates compare to current date...
        long endDate = System.currentTimeMillis();
        printLogs(LogTag, "initializeViews", "endDate_VALUE "+endDate);
        long abc = endDate-90425738;
        String ab = String.valueOf(abc);
        printLogs(LogTag, "initializeViews", "ab_VALUE "+ab);
        calendarView.setMaxDate(Long.parseLong(ab));


    }



    @Override
    protected void initializeListeners() {

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {



                int addMonth = month;
                int mon = addMonth+1;
                String newMonth = String.valueOf(mon);

                Bundle inputUri = new Bundle();
                inputUri.putString("dayOfMonth", String.valueOf(dayOfMonth));
                inputUri.putString("month", String.valueOf(month));
                inputUri.putString("year", String.valueOf(year));
                Intent intent = new Intent(EditAttendanceA.this, EditAttCalenderA.class);
                intent.putExtras(inputUri);
                startActivity(intent);
                finish();
                Toast.makeText(baseApcContext, "DATE :"+dayOfMonth+"-"+newMonth+"-"+year, Toast.LENGTH_SHORT).show();

                printLogs(LogTag, "initializeListeners", "ValueDMY"+dayOfMonth+month+year);
                //intent.putExtra("date", dayOfMonth+ "/" + month + "/" + year);

            }
        });

    }

    @Override
    protected void initializeInputs() {



    }

    @Override
    protected void initializeLabels() {


        String Label = getLabelFromDb("h_S239", R.string.h_S239);
        lblView = (TextView) findViewById(R.id.activity_heading);
        lblView.setText(Label);

    }

    private void initBackBtn() {
        printLogs(LogTag, "initBackBtn", "init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void setLayoutXml() {

        setContentView(R.layout.a_edit_attendance);
    }


    public void callDataApi() {
        printLogs(LogTag, "callDataApi", "init");
        userToken = userSessionObj.getToken();
        if (!TextUtils.isEmpty(userToken)) {
            printLogs(LogTag, "callDataApi", "Token Update");
            syncUserData(baseApcContext, activityIn);
            syncStudentData(baseApcContext, activityIn);
            syncGrantData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn, ActivityId);

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
        if (isUpdate) {
            Intent intent = new Intent(EditAttendanceA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag, "onOptionsItemSelected", "init");
        if (item.getItemId() == android.R.id.home) {

            Intent intent = new Intent(EditAttendanceA.this, SDashboardDA.class);
            printLogs(LogTag, "onOptionsItemSelected", "SDashboardDA");
            startActivity(intent);
            finish();

            printLogs(LogTag, "onOptionsItemSelected", "default");
            super.onOptionsItemSelected(item);
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
