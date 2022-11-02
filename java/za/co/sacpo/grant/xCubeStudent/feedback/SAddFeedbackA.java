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
import java.util.Map;
import java.util.regex.Pattern;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.ListarClientes;
import za.co.sacpo.grant.xCubeLib.adapter.SpinnerAdapter;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsStudentSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeStudent.SDashboardDA;

import static za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPrivate.isValidReason;
import static za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPublic.isValidDepartment;

public class SAddFeedbackA extends BaseAPCPrivate {

    private String ActivityId = "S175";
    private String KEY_DEPARTMENT = "s_w_r_title";
    private String KEY_TRAINING = "about_my_training";
    private String KEY_FEEDBACK = "feedback";
    private String KEY_EXPERIENC = "experience";
    private String KEY_MONTH_YEAR = "month_year";

    public View mProgressView, mContentView;
    public TextInputLayout inputLayoutDepartment;
    public TextInputLayout inputLayoutTraining, inputLayoutFeedback, inputLayoutExperience;
    public EditText inputDepartment, inputTraining, inputFeedback, inputExperience;
    public Button btnSubmitLv;
    public LinearLayout LLFormContainer, LLInformationContainer, LLInputContainer;
    String generator, date_input, month_year, grant_id;
    private Spinner SpinnerMonthYear;
    Spinner inputSpinnerLeaveType;
    private TextView lblView;
    final ArrayList<ListarClientes> datalist = new ArrayList<>();
    Button btn_addMoreReport;
    SAddFeedbackA thisClass;
    private String selected_month_id,student_id;

    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cTAId) {
        baseApcContext = cnt;
        CAId = cTAId;
        activityIn = ain;
        LogTag = lt;
        thisClass = this;
        printLogs(lt, "setBaseApcContextParent", "weAreIn");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(), this, this.getClass().getSimpleName(), ActivityId);
        printLogs(LogTag, "onCreate", "init");
        Bundle activeInputUri = getIntent().getExtras();
        generator = activeInputUri.getString("generator");
        date_input = activeInputUri.getString("date_input");
        month_year = activeInputUri.getString("month_year");
        grant_id = activeInputUri.getString("grant_id");
        student_id = activeInputUri.getString("student_id");
        printLogs(LogTag, "onCreate", "GENERATOR ID " + generator);
        printLogs(LogTag, "onCreate", "student_id__" + student_id);

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
            fetchMonthYear();
            userToken = userSessionObj.getToken();
            syncToken(baseApcContext, activityIn);
            if (TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
            callDataApi();
            initializeInputs();
            printLogs(LogTag, "bootStrapInit", "exitConnected");
            showProgress(false, mContentView, mProgressView);
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
        if (isUpdate) {
            Intent intent = new Intent(SAddFeedbackA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void setLayoutXml() {
        printLogs(LogTag, "setLayoutXml", "a_add_feedback");
        setContentView(R.layout.a_add_feedback);
    }

    @Override
    protected void initializeViews() {
        printLogs(LogTag, "initializeViews", "init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        SpinnerMonthYear = (Spinner) findViewById(R.id.inputSpinnerMonthYear);
        inputDepartment = (EditText) findViewById(R.id.inputDepartment);
        inputTraining = (EditText) findViewById(R.id.inputTraining);
        inputFeedback = (EditText) findViewById(R.id.inputFeedback);
        inputExperience = (EditText) findViewById(R.id.inputExperience);
        inputSpinnerLeaveType = (Spinner) findViewById(R.id.inputSpinnerLeaveType);
        inputLayoutDepartment = (TextInputLayout) findViewById(R.id.inputLayoutDepartment);
        inputLayoutTraining = (TextInputLayout) findViewById(R.id.inputLayoutTraining);
        inputLayoutFeedback = (TextInputLayout) findViewById(R.id.inputLayoutFeedback);
        inputLayoutExperience = (TextInputLayout) findViewById(R.id.inputLayoutExperience);
        LLFormContainer = (LinearLayout) findViewById(R.id.form_container);
        LLInformationContainer = (LinearLayout) findViewById(R.id.informationContainer);
        // LLInputContainer = (LinearLayout) findViewById(R.id.inputContainer);
        btnSubmitLv = (Button) findViewById(R.id.btn_submitLeave);
        printLogs(LogTag, "initializeViews", "exit");
      /*  View rootView = getWindow().getDecorView().getRootView();
        onAddFirstField(rootView);*/
    }


    @Override
    protected void initializeLabels() {
        printLogs(LogTag, "initializeLabels", "init");
        String Label = getLabelFromDb("l_S175_week_ending", R.string.l_S175_week_ending);
        lblView = (TextView) findViewById(R.id.lblWeekEnding);
        lblView.setText(Label);

        Label = getLabelFromDb("l_S175_department", R.string.l_S175_department);
        lblView = (TextView) findViewById(R.id.lblDepartment);
        lblView.setText(Label);

        Label = getLabelFromDb("l_S175_training", R.string.l_S175_training);
        lblView = (TextView) findViewById(R.id.lblTraining);
        lblView.setText(Label);

        Label = getLabelFromDb("l_S175_feedback", R.string.l_S175_feedback);
        lblView = (TextView) findViewById(R.id.lblFeedback);
        lblView.setText(Label);

        Label = getLabelFromDb("l_S175_experence", R.string.l_S175_experence);
        lblView = (TextView) findViewById(R.id.lblExperience);
        lblView.setText(Label);

        Label = getLabelFromDb("h_S175", R.string.h_S175);
        lblView = (TextView) findViewById(R.id.activity_heading);
        lblView.setText(Label);


        Label = getLabelFromDb("b_apply_feedback", R.string.b_apply_feedback);
        btnSubmitLv.setText(Label);


    }

    @Override
    protected void initializeInputs() {
        printLogs(LogTag, "initializeInputs", "init");
        studentSessionObj = new OlumsStudentSession(baseApcContext);
        SpinnerAdapter adapter = new SpinnerAdapter(SAddFeedbackA.this, android.R.layout.simple_spinner_dropdown_item, datalist);
        SpinnerMonthYear.setAdapter(adapter);
    }

    @Override
    protected void initializeListeners() {
        printLogs(LogTag, "initializeListeners", "init");


        SpinnerMonthYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                final String selected_Item = SpinnerMonthYear.getSelectedItem().toString();
//            final int selected_value = SpinnerMonthYear.getSelectedItemPosition();

                ListarClientes myModel = (ListarClientes) parent.getSelectedItem();

                selected_month_id = myModel.getId();
                printLogs(LogTag, "setOnItemSelectedListener", "MONTH_YEAR_IDDDDDDD" + selected_month_id);


            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // int selected_month_id = 0;
            }
        });


        btnSubmitLv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm(selected_month_id);
                printLogs(LogTag, "btnSubmitLv", "MONTH_YEAR_btnSubmitLv_IDDD : " + selected_month_id);
            }
        });

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

    private void fetchMonthYear() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_175_1;
        FINAL_URL = FINAL_URL + "?token=" + token;//+"/seta_id/"+selected_Bank;
        printLogs(LogTag, "fetchData", "URL : " + FINAL_URL);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchData", "response : " + response);
                    String Status = jsonObject.getString(KEY_STATUS);
                    if (Status.equals("1")) {

                        JSONArray dataM = jsonObject.getJSONArray(KEY_DATA);
                        for (int i = 0; i < dataM.length(); i++) {
                            JSONObject rec = dataM.getJSONObject(i);
                          /*  String seta_name = rec.getString("grant_name");
                            SetaName.add(seta_name);
                              */
                            ListarClientes GetDatadp = new ListarClientes();
                            /* String seta_name = rec.getString("grant_name");
                             SetaName.add(seta_name);*/
                            GetDatadp.setName(rec.getString("month_year"));
                            GetDatadp.setId(rec.getString("id"));
                            datalist.add(GetDatadp);
                            showProgress(false, mContentView, mProgressView);
                        }
                        //  inputSpinnerGrant.setAdapter(new ArrayAdapter<String>(GAProcessStipendClaimA.this, android.R.layout.simple_spinner_dropdown_item, SetaName));
                        SpinnerAdapter adapter = new SpinnerAdapter(SAddFeedbackA.this, android.R.layout.simple_spinner_dropdown_item, datalist);
                        SpinnerMonthYear.setAdapter(adapter);
                    } else if (Status.equals("2")) {
                        showProgress(false, mContentView, mProgressView);
                    } else {
                        //showProgress(false,mContentView,mProgressView);
                        printLogs(LogTag, "fetchData", "error_try_again : DATA_ERROR");
                        String sTitle = "Error :" + ActivityId + "-110";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
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

    private void initBackBtn() {
        printLogs(LogTag, "initBackBtn", "init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void validateForm(final String selected_month_id) {
        printLogs(LogTag, "validateForm", "MONTH_YEAR_validateForm_IDDD : " + selected_month_id);
        boolean cancel = false;
        printLogs(LogTag, "validateForm", "init");
        if (!validateDepartment(inputDepartment, inputLayoutDepartment)) {
            cancel = true;
        } else if (!validateLearning(inputExperience, inputLayoutExperience)) {
            cancel = true;
        } else if (!validateTraining(inputTraining, inputLayoutTraining)) {
            cancel = true;
        } else if (!validateFeedback(inputFeedback, inputLayoutFeedback)) {
            cancel = true;
        }

        if (cancel) {
            return;
        } else {
            showProgress(true, mContentView, mProgressView);
            this.FormSubmit(selected_month_id);
        }
        printLogs(LogTag, "validateForm", "exit");
    }

    public void FormSubmit(String selected_month_id) {
        printLogs(LogTag, "FormSubmit", "MONTH_YEAR_FORMSUBMIT_IDDD : " + selected_month_id);
        final String department = inputDepartment.getText().toString().trim();
        final String training = inputTraining.getText().toString().trim();
        final String feedback = inputFeedback.getText().toString().trim();
        final String experience = inputExperience.getText().toString().trim();

        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_175_2;
        FINAL_URL = FINAL_URL+"?token="+token  ;
        printLogs(LogTag, "FormSubmit", "URL : " + FINAL_URL);
        JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("token",token);
                jsonBody.put(KEY_DEPARTMENT, department);
                jsonBody.put(KEY_TRAINING, training);
                jsonBody.put(KEY_MONTH_YEAR, selected_month_id);
                jsonBody.put(KEY_FEEDBACK, feedback);
                jsonBody.put(KEY_EXPERIENC, experience);
                printLogs(LogTag,"FormSubmit","jsonBody "+jsonBody);
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
                    if (Status.equals("1")) {
                        printLogs(LogTag, "FormSubmit", "SUCCESS : " + Status);
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = getLabelFromDb("m_S175_title", R.string.m_S175_title);
                        String sMessage = getLabelFromDb("m_S175_message", R.string.m_S175_message);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showSuccessDialogSAddFeedback(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose, thisClass);
                    } else {
                        printLogs(LogTag, "FormSubmit", "else");
                        showProgress(false, mContentView, mProgressView);
                        String sMessage = getLabelFromDb("error_S175_100", R.string.error_S175_100);
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-100";
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "-101";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                printLogs(LogTag, "onCreate", "error listener "+error);
                showProgress(false, mContentView, mProgressView);
                String sTitle = "Error :" + ActivityId + "-102";
                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
            }
        }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put("Content-Type", "application/json; charset=utf-8");
                header.put("Accept","*/*");
                return header;
            }};
            RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);


    }


    public boolean validateDepartment(EditText inputDepartment, TextInputLayout inputLayoutDepartment) {
        printLogs(LogTag, "onCreate", "validate department");
        String reasons = inputDepartment.getText().toString().trim();
        setCustomError(inputLayoutDepartment, null, inputDepartment);
        if (reasons.isEmpty() || isValidDepartment(reasons)) {
            String sMessage = getLabelFromDb("error_184_106", R.string.error_184_106);
            setCustomError(inputLayoutDepartment, sMessage, inputDepartment);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutDepartment, inputDepartment);
            return true;
        }
    }

    public boolean validateTraining(EditText inputTraining, TextInputLayout inputLayoutTraining) {
        printLogs(LogTag, "onCreate", "validate department");
        String reasons = inputTraining.getText().toString().trim();
        setCustomError(inputLayoutTraining, null, inputTraining);
        if (reasons.isEmpty()) {
            setCustomErrorDisabled(inputLayoutTraining, inputTraining);
            return true;
        } else if (isValidReason(reasons)) {
            String sMessage = getLabelFromDb("error_184_107", R.string.error_184_107);
            setCustomError(inputLayoutTraining, sMessage, inputTraining);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutTraining, inputTraining);
            return true;
        }
    }

    public boolean validateFeedback(EditText inputFeedback, TextInputLayout inputLayoutFeedback) {
        printLogs(LogTag, "onCreate", "validate department");
        String feedback = inputFeedback.getText().toString().trim();
        setCustomError(inputLayoutFeedback, null, inputFeedback);
        if (feedback.isEmpty()) {
            setCustomErrorDisabled(inputLayoutFeedback, inputFeedback);
            return true;
        } else if (isValidReason(feedback)) {
            String sMessage = getLabelFromDb("error_184_107", R.string.error_184_107);
            setCustomError(inputLayoutFeedback, sMessage, inputFeedback);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutFeedback, inputFeedback);
            return true;
        }
    }

    public boolean validateLearning(EditText inputExperience, TextInputLayout inputLayoutExperience) {
        printLogs(LogTag, "onCreate", "validate department");
        String feedback = inputExperience.getText().toString().trim();
        Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");
        setCustomError(inputLayoutExperience, null, inputExperience);
        if (feedback.isEmpty() || isValidReason(feedback)) {
            String sMessage = getLabelFromDb("error_184_109", R.string.error_184_109);
            setCustomError(inputLayoutExperience, sMessage, inputExperience);
            return false;
        }/*else if (regex.matcher(feedback).find()) {
            String sMessage = getLabelFromDb("error_184_110",R.string.error_184_110);
            setCustomError(inputLayoutExperience,sMessage,inputExperience);
            return false;
        }*/ else {
            setCustomErrorDisabled(inputLayoutExperience, inputExperience);
            return true;
        }
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
        public void onAddField(View v) {
            final float scale = getResources().getDisplayMetrics().density;
            int pixelsH = (int) (40 * scale + 0.5f);

            EditText inputTextView;
            Button removeButtonView;
            View emptyView;
            LinearLayout LLInputChildContainer;
            LLInputChildContainer  = new LinearLayout(SAddFeedbackA.this);
            LinearLayout.LayoutParams LLIP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, pixelsH,7f);
            LLIP.setMargins(0,10,0,10);
            LLInputChildContainer.setLayoutParams(LLIP);
            LLInputChildContainer.setOrientation(LinearLayout.HORIZONTAL);


            inputTextView = new EditText(SAddFeedbackA.this);
            inputTextView.setId(111111111+inputCounter);
            inputTextView.setLayoutParams(new LinearLayout.LayoutParams(0,pixelsH,5f));
            inputTextView.setBackground(getResources().getDrawable(R.drawable.input_boder_profile));
            inputTextView.setTextColor(getResources().getColor(R.color.colorblack));
            inputTextView.setPadding(5,5,5,5);

            removeButtonView = new Button(SAddFeedbackA.this);
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
        public void onAddFirstField(View v) {

            final float scale = getResources().getDisplayMetrics().density;
            int pixelsH = (int) (40 * scale + 0.5f);

            EditText inputTextView;
            Button addButtonView;
            LinearLayout LLInputChildContainer;
            LLInputChildContainer  = new LinearLayout(SAddFeedbackA.this);
            LinearLayout.LayoutParams LLIP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, pixelsH,7f);
            LLIP.setMargins(0,10,0,10);
            LLInputChildContainer.setLayoutParams(LLIP);
            LLInputChildContainer.setOrientation(LinearLayout.HORIZONTAL);


            inputTextView = new EditText(SAddFeedbackA.this);
            inputTextView.setId(111111111+inputCounter);
            inputTextView.setLayoutParams(new LinearLayout.LayoutParams(0,pixelsH,5f));
            //inputTextView.setTextAppearance(this,R.style.editText);
            inputTextView.setBackground(getResources().getDrawable(R.drawable.input_boder_profile));
            inputTextView.setTextColor(getResources().getColor(R.color.colorblack));
            inputTextView.setPadding(5,5,5,5);

            addButtonView = new Button(SAddFeedbackA.this);
            addButtonView.setId(222222222+inputCounter);
            addButtonView.setLayoutParams(new LinearLayout.LayoutParams(40, pixelsH,1f));
            addButtonView.setBackground(getResources().getDrawable(R.mipmap.ic_launcher_detail_button));
            addButtonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onAddField(view);
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
    public void customRedirector() {


            Intent intent = new Intent(SAddFeedbackA.this, SDashboardDA.class);
            printLogs(LogTag, "onOptionsItemSelected", "SDashboardDA");
            startActivity(intent);

    }




    protected class ErrorTextWatcher implements TextWatcher {

        private EditText EditView;
        private TextInputLayout EditLayout;

        private ErrorTextWatcher(EditText EditView, TextInputLayout EditLayout) {
            printLogs(LogTag, "onCreate", "ErrorTextWatcher");
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
                    validateDepartment(EditView, EditLayout);
                    break;
                case R.id.inputTraining:
                    validateTraining(EditView, EditLayout);
                    break;
                case R.id.inputFeedback:
                    validateFeedback(EditView, EditLayout);
                    break;
                case R.id.inputExperience:
                    validateFeedback(EditView, EditLayout);
                    break;

            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            Intent intent = new Intent(SAddFeedbackA.this, SDashboardDA.class);
            printLogs(LogTag, "onOptionsItemSelected", "SDashboardDA");
            startActivity(intent);
        return true;
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SAddFeedbackA.this, SDashboardDA.class);
        printLogs(LogTag, "onOptionsItemSelected", "SDashboardDA");
        startActivity(intent);
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