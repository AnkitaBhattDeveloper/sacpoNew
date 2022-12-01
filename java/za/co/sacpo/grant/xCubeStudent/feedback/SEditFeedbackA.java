package za.co.sacpo.grant.xCubeStudent.feedback;

import android.content.Context;
import android.content.Intent;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.ListarClientes;
import za.co.sacpo.grant.xCubeLib.adapter.SpinnerAdapter;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsStudentSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;


/*a_s_edit_feedback*/
public class SEditFeedbackA extends BaseFormAPCPrivate {
    private String ActivityId="184";
    private String KEY_MONTH_YEAR="month_year";
    private String KEY_DEPARTMENT="title";
    private String KEY_TRAINING="about_my_training";
    private String KEY_FEEDBACK="feedback";
    private String KEY_EXPERIENCE="experience";
    private String KEY_ID="s_w_r_id";
    List<EditText> allLearningExp = new ArrayList<EditText>();
    String date_input,s_w_r_id,report_id,is_upload_attendance,student_id;
    public View mProgressView, mContentView,heading;
    public TextInputLayout inputLayoutDepartment;
    public TextInputLayout inputLayoutTraining,inputLayoutFeedback,inputLayoutExperience;
    public EditText inputDepartment,inputTraining,inputFeedback,inputExperience;
    public Button btnSubmitLv;
    private EditText et_month_year;
    public LinearLayout LLFormContainer,LLInformationContainer,LLInputContainer;
    String generator,grant_id;
    int inputCounter=0;
    private TextView lblView;
    final ArrayList<ListarClientes> datalist = new ArrayList<>();
    private Spinner SpinnerMonthYear;
    private String selected_month_id,date;
    private SEditFeedbackA thisClass;
    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cTAId){
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
        report_id = activeInputUri.getString("s_w_r_id");
        s_w_r_id = activeInputUri.getString("s_w_r_id");
        generator = activeInputUri.getString("generator");
        date_input = activeInputUri.getString("date");
        grant_id = activeInputUri.getString("grant_id");
        date = activeInputUri.getString("date");
        student_id = activeInputUri.getString("student_id");
        is_upload_attendance = activeInputUri.getString("is_upload_attendance");
        printLogs(LogTag,"onCreate","GENERATOR ID "+generator);
        printLogs(LogTag,"onCreate","date_input"+date_input);
        printLogs(LogTag,"onCreate","s_w_r_id "+s_w_r_id);
        printLogs(LogTag,"onCreate","report_id "+report_id);
        printLogs(LogTag,"onCreate","grant_id "+grant_id);
        printLogs(LogTag,"onCreate","date "+date);
        printLogs(LogTag,"onCreate","student_id_GET___ "+student_id);
        printLogs(LogTag,"onCreate","is_upload_attendance "+is_upload_attendance);
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
            fetchMonthYear();
            showProgress(true,mContentView,mProgressView);
            initializeLabels();
            initializeListeners();
            userToken =userSessionObj.getToken();
            syncToken(baseApcContext,activityIn);
            if(TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
            callDataApi();
            fetchData();
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
            Intent intent = new Intent(SEditFeedbackA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","a_s_edit_feedback");
        setContentView(R.layout.a_s_edit_feedback);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        heading = findViewById(R.id.heading);


        SpinnerMonthYear = (Spinner) findViewById(R.id.inputSpinnerMonthYear);
        inputDepartment = (EditText) findViewById(R.id.inputDepartment);
        inputTraining = (EditText) findViewById(R.id.inputTraining);
        inputFeedback = (EditText) findViewById(R.id.inputFeedback);
        inputExperience = (EditText) findViewById(R.id.inputExperience);
        inputLayoutDepartment = (TextInputLayout) findViewById(R.id.inputLayoutDepartment);
        inputLayoutTraining = (TextInputLayout) findViewById(R.id.inputLayoutTraining);
        inputLayoutFeedback = (TextInputLayout) findViewById(R.id.inputLayoutFeedback);
        inputLayoutExperience = (TextInputLayout) findViewById(R.id.inputLayoutExperience);
        LLFormContainer = (LinearLayout) findViewById(R.id.form_container);
        et_month_year = (EditText) findViewById(R.id.et_month_year);
        LLInformationContainer = (LinearLayout) findViewById(R.id.informationContainer);
        btnSubmitLv = (Button) findViewById(R.id.btn_submitLeave);
        printLogs(LogTag,"initializeViews","exit");

    }


    @Override
    protected void initializeLabels(){
        printLogs(LogTag,"initializeLabels","init");
        String Label = getLabelFromDb("l_184_start_date",R.string.l_184_start_date);
        lblView = (TextView)findViewById(R.id.lblStartDate);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("l_184_department",R.string.l_184_department);
        lblView = (TextView)findViewById(R.id.lblDepartment);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("l_184_training",R.string.l_184_training);
        lblView = (TextView)findViewById(R.id.lblTraining);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("l_184_feedback",R.string.l_184_feedback);
        lblView = (TextView)findViewById(R.id.lblFeedback);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("l_184_experence",R.string.l_184_experence);
        lblView = (TextView)findViewById(R.id.lblExperience);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("h_184",R.string.h_184);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);


        Label = getLabelFromDb("b_edit_apply_feedback",R.string.b_edit_apply_feedback);
        btnSubmitLv.setText(Label);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            btnSubmitLv.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            et_month_year.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputDepartment.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputTraining.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputFeedback.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputExperience.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
        }


        }
    @Override
    protected void initializeInputs(){
        printLogs(LogTag,"initializeInputs","init");
        dbSetaObj = DbHelper.getInstance(this);

        studentSessionObj = new OlumsStudentSession(baseApcContext);


        SpinnerAdapter adapter = new SpinnerAdapter(SEditFeedbackA.this, android.R.layout.simple_spinner_dropdown_item, datalist);
        SpinnerMonthYear.setAdapter(adapter);

    }
    @Override
    protected void initializeListeners() {
        printLogs(LogTag,"initializeListeners","init");

        SpinnerMonthYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                final String selected_Item = SpinnerMonthYear.getSelectedItem().toString();
//            final int selected_value = SpinnerMonthYear.getSelectedItemPosition();

                ListarClientes myModel = (ListarClientes) parent.getSelectedItem();

                selected_month_id = myModel.getId();
                printLogs(LogTag,"setOnItemSelectedListener","MONTH_YEAR_ID"+selected_month_id);



            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selected_month_id="0";
            }
        });


        btnSubmitLv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });
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

     /*   final String txtDepartment = inputDepartment.getText().toString();
        final String txtTraining = inputTraining.getText().toString();
        final String txtFeedback = inputFeedback.getText().toString();
        final String txtExperience = inputExperience.getText().toString();*/


        printLogs(LogTag,"validateForm","init");

  /*      if (!isSpinnerValid(Integer.parseInt(selected_month_id))) {
            cancel = true;
        }
         else*/ if(!validateDepartment(inputDepartment,inputLayoutDepartment)) {
            cancel = true;
        }
        else if(!validateTraining(inputTraining,inputLayoutTraining)){
            cancel = true;
        }
        else if(!validateFeedback(inputFeedback,inputLayoutFeedback)){
            cancel = true;
        } else if(!validateExperience(inputExperience,inputLayoutExperience)){
            cancel = true;
        }if (cancel) {
            return;
        } else {
            showProgress(true,mContentView,mProgressView);
            this.FormSubmit();
        }
        printLogs(LogTag,"validateForm","exit");
    }

    private void fetchMonthYear() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_175_1;
        FINAL_URL=FINAL_URL+"?token="+token;//+"/seta_id/"+selected_Bank;
        printLogs(LogTag,"fetchData","URL : "+FINAL_URL);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchData", "response : " + response);
                    String Status = jsonObject.getString(KEY_STATUS);
                    if(Status.equals("1")){

                        JSONArray dataM = jsonObject.getJSONArray(KEY_DATA);
                        for(int i=0;i<dataM.length();i++) {
                            JSONObject rec = dataM.getJSONObject(i);
                          /*  String seta_name = rec.getString("grant_name");
                            SetaName.add(seta_name);
                              */
                            ListarClientes GetDatadp= new ListarClientes();
                            /* String seta_name = rec.getString("grant_name");
                             SetaName.add(seta_name);*/
                            GetDatadp.setName(rec.getString("month_year"));
                            GetDatadp.setId(rec.getString("id"));
                            datalist.add(GetDatadp);
                            showProgress(false, mContentView, mProgressView);
                        }
                        //  inputSpinnerGrant.setAdapter(new ArrayAdapter<String>(GAProcessStipendClaimA.this, android.R.layout.simple_spinner_dropdown_item, SetaName));
                        SpinnerAdapter adapter = new SpinnerAdapter(SEditFeedbackA.this, android.R.layout.simple_spinner_dropdown_item, datalist);
                        SpinnerMonthYear.setAdapter(adapter);
                    }
                    else if(Status.equals("2")) {
                        showProgress(false,mContentView,mProgressView);
                    }

                    else{
                        //showProgress(false,mContentView,mProgressView);
                        printLogs(LogTag, "fetchData", "error_try_again : DATA_ERROR");
                        String sTitle="Error :"+ActivityId+"-110";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    printLogs(LogTag, "fetchData", "error_196_108 : " + e.getMessage());
                    e.printStackTrace();
                    //showProgress(false,mContentView,mProgressView);
                    String sTitle = "Error :" + ActivityId + "-111";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                printLogs(LogTag, "fetchData", "error_try_again: " + error.getMessage());
                String sTitle = "Error :" + ActivityId + "-112";
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

    private void fetchData(){
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.M_STUDENT_REPORT_DETAILS;
        FINAL_URL=FINAL_URL+"?token="+token+"&report_id="+s_w_r_id+"&date="+date;
        printLogs(LogTag,"fetchData","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchData", "RESPONSE : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONObject dataM = outputJson.getJSONObject(KEY_DATA);
                        inputDepartment.setText(dataM.getString("title"));
                        et_month_year.setText(dataM.getString("date"));
                        String feedback = dataM.getString("feedback");
                        inputFeedback.setText(Html.fromHtml(feedback).toString());
                        String training = dataM.getString("training");
                        inputTraining.setText(Html.fromHtml(training).toString());
                        String experience = dataM.getString("learning_experices");
                        inputExperience.setText(Html.fromHtml(experience).toString());
                        showProgress(false, mContentView, mProgressView);
                    }
                    else if(Status.equals("2")) {
                        showProgress(false,mContentView,mProgressView);
                    }
                    else{
                        //showProgress(false,mContentView,mProgressView);
                        printLogs(LogTag, "fetchData", "error_try_again : DATA_ERROR");
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


    public void FormSubmit(){

        showProgress(true,mContentView,mProgressView);
        final String department = inputDepartment.getText().toString().trim();
        final String about_my_training = inputTraining.getText().toString().trim();
        final String feedback = inputFeedback.getText().toString().trim();
        final String experience = inputExperience.getText().toString().trim();
        final String month_year = String.valueOf(selected_month_id);
        printLogs(LogTag,"FormSubmit","month_year_VALUEEEE "+month_year);

        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_EDIT_FEEDBACK;
        printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put(KEY_MONTH_YEAR, month_year);
            jsonBody.put(KEY_DEPARTMENT, department);
            jsonBody.put(KEY_TRAINING, about_my_training);
            jsonBody.put(KEY_FEEDBACK, feedback);
            jsonBody.put(KEY_EXPERIENCE, experience);
            jsonBody.put(KEY_ID, s_w_r_id);
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
                        String sTitle=getLabelFromDb("m_184_title",R.string.m_184_title);
                        String sMessage=getLabelFromDb("m_184_message",R.string.m_184_message);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showSuccessDialogEditFeedback(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose,thisClass);

                    }
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
        RequestQueue requestQueue = Volley.newRequestQueue(SEditFeedbackA.this);
        requestQueue.add(jsonObjectRequest);


    }



    public boolean validateDepartment(EditText inputDepartment,TextInputLayout inputLayoutDepartment) {

        String department = inputDepartment.getText().toString().trim();
        setCustomError(inputLayoutDepartment,null,inputDepartment);
        if (department.isEmpty() || departmentName(department)) {
            String sMessage = getLabelFromDb("error_184_106",R.string.error_184_106);
            setCustomError(inputLayoutDepartment,sMessage,inputDepartment);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutDepartment,inputDepartment);
            return true;
        }

       /* printLogs(LogTag,"onCreate","validate department");
        String reasons = inputDepartment.getText().toString().trim();
        Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");
        setCustomError(inputLayoutDepartment,null,inputDepartment);
        if (reasons.isEmpty() || regex.matcher(reasons).find()) {
            String sMessage = getLabelFromDb("error_184_106",R.string.error_184_106);
            setCustomError(inputLayoutDepartment,sMessage,inputDepartment);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutDepartment,inputDepartment);
            return true;
        }*/
    }

    public boolean validateTraining(EditText inputTraining,TextInputLayout inputLayoutTraining) {

        String training = inputTraining.getText().toString().trim();
        setCustomError(inputLayoutTraining,null,inputTraining);
        if (training.isEmpty() || isValidTraining(training)) {
            String sMessage = getLabelFromDb("error_184_107",R.string.error_184_107);
            setCustomError(inputLayoutTraining,sMessage,inputTraining);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutTraining,inputTraining);
            return true;
        }


        /*printLogs(LogTag,"onCreate","validate department");
        String feedback = inputTraining.getText().toString().trim();
        setCustomError(inputLayoutTraining,null,inputTraining);
        if (feedback.isEmpty() ) {
            String sMessage = getLabelFromDb("error_226_104",R.string.error_226_104);
            setCustomError(inputLayoutTraining,sMessage,inputTraining);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutTraining,inputTraining);
            return true;
        }*/


       /* printLogs(LogTag,"onCreate","validate department");
        String reasons = inputTraining.getText().toString().trim();
        setCustomError(inputLayoutTraining,null,inputTraining);
        Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");
        if (reasons.isEmpty() || regex.matcher(reasons).find() ) {
            String sMessage = getLabelFromDb("error_184_107",R.string.error_184_107);
            setCustomError(inputLayoutTraining,sMessage,inputTraining);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutTraining,inputTraining);
            return true;
        }*/
    }

    public boolean validateFeedback(EditText inputFeedback,TextInputLayout inputLayoutFeedback) {


        String feedback = inputFeedback.getText().toString().trim();
        setCustomError(inputLayoutFeedback,null,inputFeedback);
        if (feedback.isEmpty() || isValidTraining(feedback)) {
            String sMessage = getLabelFromDb("error_184_108",R.string.error_184_108);
            setCustomError(inputLayoutFeedback,sMessage,inputFeedback);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutFeedback,inputFeedback);
            return true;
        }



       /* printLogs(LogTag,"onCreate","validate department");
        String feedback = inputFeedback.getText().toString().trim();
        Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");
        setCustomError(inputLayoutFeedback,null,inputFeedback);
        if (feedback.isEmpty() || regex.matcher(feedback).find()) {
            String sMessage = getLabelFromDb("error_184_108",R.string.error_184_108);
            setCustomError(inputLayoutFeedback,sMessage,inputFeedback);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutFeedback,inputFeedback);
            return true;
        }*/
    }

    public boolean validateExperience(EditText inputExperience,TextInputLayout inputLayoutExperience) {


        String experience = inputExperience.getText().toString().trim();
        setCustomError(inputLayoutExperience,null,inputExperience);
        if (experience.isEmpty() || isValidTraining(experience)) {
            String sMessage = getLabelFromDb("error_184_109",R.string.error_184_109);
            setCustomError(inputLayoutExperience,sMessage,inputExperience);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutExperience,inputExperience);
            return true;
        }

       /* printLogs(LogTag,"onCreate","validate department");
        String feedback = inputExperience.getText().toString().trim();
        Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");
        setCustomError(inputLayoutExperience,null,inputExperience);
        if (feedback.isEmpty() || regex.matcher(feedback).find()) {
            String sMessage = getLabelFromDb("error_184_109",R.string.error_184_109);
            setCustomError(inputLayoutExperience,sMessage,inputExperience);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutExperience,inputExperience);
            return true;
        }*/
    }

/*    public boolean validateLearning(){
        String Experience ="";
        for(int i = 0; i < allLearningExp.size(); i++) {
            String learning =allLearningExp.get(i).getText().toString().trim();
            if(!learning.equals("")) {
                Experience = Experience + "---@---" +learning;
            }
        }
        printLogs(LogTag,"validateLearning","Experience : "+Experience);
        if(Experience.equals("")){
            String ErrorTitle = "";
            String ErrorMessage = "";
            ErrorTitle ="Error :"+ActivityId+"-109";
            ErrorMessage =getLabelFromDb("error_184_109",R.string.error_184_109);
            spinnerError(ErrorTitle,ErrorMessage);
            return false;
        }
        else{
            return true;
        }
    }
    public void onAddField(View v,String text) {
        final float scale = getResources().getDisplayMetrics().density;
        int pixelsH = (int) (40 * scale + 0.5f);

        EditText inputTextView;
        Button removeButtonView;
        View emptyView;
        LinearLayout LLInputChildContainer;
        LLInputChildContainer  = new LinearLayout(SEditFeedbackA.this);
        LinearLayout.LayoutParams LLIP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, pixelsH,7f);
        LLIP.setMargins(0,10,0,10);
        LLInputChildContainer.setLayoutParams(LLIP);
        LLInputChildContainer.setOrientation(LinearLayout.HORIZONTAL);


        inputTextView = new EditText(SEditFeedbackA.this);
        inputTextView.setId(111111111+inputCounter);
        inputTextView.setLayoutParams(new LinearLayout.LayoutParams(0,pixelsH,5f));
        inputTextView.setBackground(getResources().getDrawable(R.drawable.input_boder_profile));
        inputTextView.setTextColor(getResources().getColor(R.color.colorblack));
        inputTextView.setPadding(5,5,5,5);
        inputTextView.setText(text);
        removeButtonView = new Button(SEditFeedbackA.this);
        removeButtonView.setId(222222222+inputCounter);

        removeButtonView.setLayoutParams(new LinearLayout.LayoutParams(40, pixelsH,1f));
        removeButtonView.setBackground(getResources().getDrawable(R.mipmap.ic_launcher_remove_button));
        removeButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDelete(view);
            }
        });


        LLInputChildContainer.addView(inputTextView);
        LLInputChildContainer.addView(removeButtonView);
        allLearningExp.add(inputTextView);
        printLogs(LogTag,"onAddField","inputCounter"+inputCounter);
        LLInputContainer.addView(LLInputChildContainer);
        inputCounter=inputCounter+1;
    }
    public void onAddFirstField(View v, String text) {

        final float scale = getResources().getDisplayMetrics().density;
        int pixelsH = (int) (40 * scale + 0.5f);

        EditText inputTextView;
        Button addButtonView;
        LinearLayout LLInputChildContainer;
        LLInputChildContainer  = new LinearLayout(SEditFeedbackA.this);
        LinearLayout.LayoutParams LLIP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, pixelsH,7f);
        LLIP.setMargins(0,10,0,10);
        LLInputChildContainer.setLayoutParams(LLIP);
        LLInputChildContainer.setOrientation(LinearLayout.HORIZONTAL);


        inputTextView = new EditText(SEditFeedbackA.this);
        inputTextView.setId(111111111+inputCounter);
        inputTextView.setLayoutParams(new LinearLayout.LayoutParams(0,pixelsH,5f));
        inputTextView.setText(text);
        inputTextView.setBackground(getResources().getDrawable(R.drawable.input_boder_profile));
        inputTextView.setTextColor(getResources().getColor(R.color.colorblack));
        inputTextView.setPadding(5,5,5,5);

        addButtonView = new Button(SEditFeedbackA.this);
        addButtonView.setId(222222222+inputCounter);
        addButtonView.setLayoutParams(new LinearLayout.LayoutParams(40, pixelsH,1f));
        addButtonView.setBackground(getResources().getDrawable(R.mipmap.ic_launcher_detail_button));
        addButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddField(view,"");
            }
        });
        LLInputChildContainer.addView(inputTextView);
        LLInputChildContainer.addView(addButtonView);
        allLearningExp.add(inputTextView);
        LLInputContainer.addView(LLInputChildContainer);
        inputCounter=inputCounter+1;
    }
    public void onDelete(View v) {
        LLInputContainer.removeView((View) v.getParent());
    }*/





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
                case R.id.inputDepartment:
                    validateDepartment(EditView,EditLayout);
                    break;
               case R.id.inputTraining:
                    validateTraining(EditView,EditLayout);
                    break;
               case R.id.inputFeedback:
                     validateFeedback(EditView,EditLayout);
                    break;

                 case R.id.inputExperience:
                     validateFeedback(EditView,EditLayout);
                    break;

            }
        }
    }


    public void customRedirector(){





                Intent intent = new Intent(SEditFeedbackA.this, SFeedbackDA.class);
                printLogs(LogTag, "onOptionsItemSelected", "SFeedbackDA");
                Bundle activeUri = new Bundle();
                activeUri.putString("generator", "174");
                intent.putExtras(activeUri);
                startActivity(intent);
                finish();


    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {

                Intent intent = new Intent(SEditFeedbackA.this,SFeedbackDA.class);
                printLogs(LogTag,"onOptionsItemSelected","SFeedbackDA");
                Bundle activeUri = new Bundle();
                activeUri.putString("generator", "174");
                activeUri.putString("date_input",date_input );
                activeUri.putString("s_w_r_id",s_w_r_id );
                activeUri.putString("date",date );
                activeUri.putString("is_upload_attendance",is_upload_attendance );
                intent.putExtras(activeUri);
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