package za.co.sacpo.grantportal.xCubeStudent.attendance;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import za.co.sacpo.grantportal.AppUpdatedA;
import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.component.Utils;
import za.co.sacpo.grantportal.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grantportal.xCubeStudent.SDashboardDA;

public class EditAttCalenderA extends BaseAPCPrivate {

    public String ActivityId = "S240";
    public View mProgressView, mContentView;
    private TextView lblView;

    String date,month,year,rb_status,random_in,random_out,s_month,a_month;
    private EditText et_loginTime,et_logoutTime;
    private int mHour,mMinute;
    private RadioButton rb_present,rb_absent;
    private LinearLayout ll_dateContainer;
    private Button btnEditAttendace,btnEditAttendace2;
    private RadioGroup rg_Att;


    private String KEY_LOGIN = "in_time";
    private String KEY_LOGOUT = "out_time";
    private String KEY_STATUS_ATT = "rb_status";
    private String KEY_DATE = "c_date";
    private String KEY_MONTH = "c_month";
    private String KEY_YEAR = "c_year";
    
    EditAttCalenderA thisClass;
    @Override
    protected void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cAId) {
        baseApcContext = cnt;
        CAId = cAId;
        activityIn = ain;
        thisClass = this;
        LogTag = lt;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         rb_status ="1";

         setBaseApcContextParent(getApplicationContext(), this, this.getClass().getSimpleName(), ActivityId);
        printLogs(LogTag, "onCreate", "init");
        Bundle activeInputUri = getIntent().getExtras();
        date = activeInputUri.getString("dayOfMonth");
        s_month = activeInputUri.getString("month");
        year = activeInputUri.getString("year");
        printLogs(LogTag, "onCreate", "Value_DMY"+date+s_month+year);
        int abc = Integer.parseInt(s_month);
        int numb = abc +1;
        a_month = String.valueOf(numb);
        bootStrapInit();


    }

    @Override
    protected void bootStrapInit() {
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
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
            //  fetchData();
            callDataApi();
            initializeInputs();
            printLogs(LogTag, "bootStrapInit", "exitConnected");
            showProgress(false, mContentView, mProgressView);
        }

    }
    @Override
    protected void initializeViews() {
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        rg_Att = findViewById(R.id.rg_Att);

        et_loginTime = findViewById(R.id.et_loginTime);
        et_logoutTime = findViewById(R.id.et_logoutTime);
        rb_present = findViewById(R.id.rb_present);
        rb_absent = findViewById(R.id.rb_absent);
        ll_dateContainer = findViewById(R.id.ll_dateContainer);
        btnEditAttendace = findViewById(R.id.btnEditAttendace);
        btnEditAttendace2 = findViewById(R.id.btnEditAttendace2);



    }

    @Override
    protected void initializeListeners() {

        btnEditAttendace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateForm();
            }
        });


        btnEditAttendace2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitAbsent();
            }
        });

        rb_present.setOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View v) {
                ll_dateContainer.setVisibility(View.VISIBLE);
                    btnEditAttendace.setVisibility(View.VISIBLE);
                    btnEditAttendace2.setVisibility(View.GONE);
                rb_status = rb_present.getTag().toString();
            }
        });

        rb_absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnEditAttendace2.setVisibility(View.VISIBLE);
                btnEditAttendace.setVisibility(View.GONE);
                ll_dateContainer.setVisibility(View.GONE);
                rb_status = rb_absent.getTag().toString();
            }
        });

        et_loginTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog tpd = new TimePickerDialog(EditAttCalenderA.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                String am_pm = "";
                                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                c.set(Calendar.MINUTE, minute);
                                if (c.get(Calendar.AM_PM) == Calendar.AM)
                                    am_pm = "AM";
                                else if (c.get(Calendar.AM_PM) == Calendar.PM)
                                    am_pm = "PM";
                                String strHrsToShow = (c.get(Calendar.HOUR) == 0) ? "12" : c.get(Calendar.HOUR) + "";

                                // Display Selected time in textbox
                                et_loginTime.setText(strHrsToShow + ":" + minute+" "+ am_pm);
                              
                                random_in = strHrsToShow+":"+minute+"~~~"+am_pm;

                            }
                        }, mHour, mMinute, false);
                tpd.show();

            }
        });



        et_logoutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog tpd = new TimePickerDialog(EditAttCalenderA.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                String am_pm = "";
                                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                c.set(Calendar.MINUTE, minute);
                                if (c.get(Calendar.AM_PM) == Calendar.AM)
                                    am_pm = "AM";
                                else if (c.get(Calendar.AM_PM) == Calendar.PM)
                                    am_pm = "PM";
                                String strHrsToShow = (c.get(Calendar.HOUR) == 0) ? "12" : c.get(Calendar.HOUR) + "";


                                // Display Selected time in textbox
                                et_logoutTime.setText(strHrsToShow + ":" + minute+" "+ am_pm);

                                random_out = strHrsToShow+":"+minute+"~~~"+am_pm;


                            }
                        }, mHour, mMinute, false);
                tpd.show();



            }
        });



    }



    private void ValidateForm() {
        String Intime = et_loginTime.getText().toString();
        String Outtime = et_logoutTime.getText().toString();
        if(TextUtils.isEmpty(Intime)) {
           // et_loginTime.setError(getLabelFromDb("validate_S240_in_time", R.string.validate_S240_in_time));
            Toast.makeText(thisClass, ""+getLabelFromDb("validate_S240_in_time", R.string.validate_S240_in_time), Toast.LENGTH_SHORT).show();
            return;


        } else if(TextUtils.isEmpty(Outtime)) {
           // et_logoutTime.setError(getLabelFromDb("validate_S240_out_time", R.string.validate_S240_out_time));
            Toast.makeText(thisClass, ""+getLabelFromDb("validate_S240_out_time", R.string.validate_S240_out_time), Toast.LENGTH_SHORT).show();

            return;
        }else if (rg_Att.getCheckedRadioButtonId() == -1) {
            // no radio buttons are checked
            Toast.makeText(getApplicationContext(), "Please Select Attendance Option", Toast.LENGTH_LONG).show();

        } else {
            showProgress(true, mContentView, mProgressView);
            submitForm();
        }
        printLogs(LogTag, "validateForm", "exit");
    }

    @Override
    protected void initializeInputs() {

    }

    @Override
    protected void initializeLabels() {
        String Label = getLabelFromDb("h_S240", R.string.h_S240);
        lblView = (TextView) findViewById(R.id.activity_heading);
        lblView.setText(Label);
    }

    private void initBackBtn() {
        printLogs(LogTag, "initBackBtn", "init");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }


    @Override
    protected void setLayoutXml() {
        setContentView(R.layout.a_edit_att_calender);
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
            Intent intent = new Intent(EditAttCalenderA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    private void submitForm() {
        final String c_date = date;
        final String c_month = a_month;
        final String c_year = year;
        final String in_time = random_in;
        final String out_time = random_out;

       // final String myLoginTime = in_time+"@@@"+out_time+"@@@"+c_date+"@@@"+c_month+"@@@"+c_year;
        final String mytime = random_in;
        printLogs(LogTag, "FormSubmit", "mytime" + mytime);
        printLogs(LogTag, "FormSubmit", "TIME_IN" + in_time);
        printLogs(LogTag, "FormSubmit", "c_month" + c_month);
       // printLogs(LogTag, "FormSubmit", "myLoginTime" + myLoginTime);

        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_240 + "/token/" + token;// +"/in_time/"+in_time+"/out_time/"+out_time+"/rb_status/"+rb_status+"/c_date/"+c_date+"/c_month/"+c_month+"/c_year/"+c_year;
        printLogs(LogTag, "FormSubmit", "URL : " + FINAL_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, FINAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject outputJson = null;

                try {
                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if (Status.equals("1")) {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = getLabelFromDb("m_S240_title", R.string.m_S240_title);
                        String sMessage = getLabelFromDb("m_S240_message", R.string.m_S240_message);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showSuccessDialogEditAttCalenderA(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose, thisClass);
                    } else {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-104";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "-105";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showProgress(false, mContentView, mProgressView);
                String sTitle = "Error :" + ActivityId + "-106";
                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put(KEY_LOGIN, in_time);
                params.put(KEY_LOGOUT, out_time);
                params.put(KEY_STATUS_ATT, rb_status);
                params.put(KEY_DATE, c_date);
                params.put(KEY_MONTH, c_month);
                params.put(KEY_YEAR, c_year);
                //params.put("myLoginTime", myLoginTime);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(EditAttCalenderA.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }


    private void submitAbsent() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_240 + "/token/" + token;//+"/rb_status/"+rb_status;
        printLogs(LogTag, "FormSubmit", "URL : " + FINAL_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, FINAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject outputJson = null;

                try {
                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if (Status.equals("1")) {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = getLabelFromDb("m_S240_title", R.string.m_S240_title);
                        String sMessage = getLabelFromDb("m_S240_message", R.string.m_S240_message);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showSuccessDialogEditAttCalenderA(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose, thisClass);
                    } else {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-104";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "-105";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showProgress(false, mContentView, mProgressView);
                String sTitle = "Error :" + ActivityId + "-106";
                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_STATUS_ATT, rb_status);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(EditAttCalenderA.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }



    public void  customRedirector(){
        Bundle activeUri = new Bundle();
        activeUri.putString("date", date);
        activeUri.putString("month", month);
        activeUri.putString("year", year);
        Intent intent = new Intent(EditAttCalenderA.this, SDashboardDA.class);
        intent.putExtras(activeUri);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag, "onOptionsItemSelected", "init");
        if (item.getItemId() == android.R.id.home) {

            Bundle inputUri = new Bundle();
            inputUri.putString("date", date);
            inputUri.putString("month", month);
            inputUri.putString("year", year);
            Intent intent = new Intent(EditAttCalenderA.this, EditAttendanceA.class);
            intent.putExtras(inputUri);
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
