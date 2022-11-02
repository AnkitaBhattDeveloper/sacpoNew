package za.co.sacpo.grant.xCubeStudent.claims;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.ListarClientes;
import za.co.sacpo.grant.xCubeLib.adapter.SpinnerAdapter;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeLib.spinners.SpinnerSetAdapter;
import za.co.sacpo.grant.xCubeStudent.SDashboardDA;

public class SImpactStudiesA extends BaseFormAPCPrivate {

    private String ActivityId = "S233";
    public String generator;
    public View mProgressView, mContentView;
    private TextView lblView, lbl_g_c,lbl_e_status,lbl_is_fos,lbl_fos,lbl_start,lbl_end,lbl_salary,lbl_position;
    private String KEY_ES = "employment_status";
    private String KEY_ISFOS = "fos_status";
    private String KEY_FOS = "field_of_study";
    private String KEY_GC = "graduateStatus";
    private String KEY_SALARY = "m_salary";
    private String KEY_POSITION = "position";
    private String KEY_STARTDATE = "start_date";
    private String KEY_ENDDATE = "end_date";
    private Button btn_save;
    private EditText et_salary, et_position;
    private Spinner spinner_fos_name;
    public DatePicker inputStartDate,inputEndDate;
    public String gc="",es="",isFs="";
    public int esint=0;
    private String selectedBank ;
    String selected_fos;
    private LinearLayout endDateBlock,employedBlock;
    private SImpactStudiesA thisClass;
    public SpinnerSetAdapter adapterS;


    public RadioGroup rg_optn,rg_f_optn,rg_e_optn;
    public RadioButton rb_option_gc_1,rb_option_gc_2,rb_option_gc_3,rb_option_gc_4,rb_option_es_1,rb_option_es_2,rb_option_es_3,rb_option_es_4,rb_option_fs_1,rb_option_fs_2;
    public SpinnerSetAdapter adapter_bn, adapter_sn;
    final ArrayList<ListarClientes> datalistBName = new ArrayList<>();

    @Override
    protected void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cAId) {
        baseApcContext = cnt;
        CAId= cAId;
        activityIn = ain;
        LogTag = lt;
        thisClass = this;
        printLogs(lt,"setBaseApcContextParent","weAreIn");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(), this, this.getClass().getSimpleName(), ActivityId);
        printLogs(LogTag, "onCreate", "init");
        Bundle activeInputUri = getIntent().getExtras();
        generator = activeInputUri.getString("generator");
        selectedBank="0";
        bootStrapInit();
    }
    @Override
    protected void bootStrapInit() {
        Boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext, activityIn);
        if(isConnected) {
            printLogs(LogTag, "bootStrapInit", "initConnected");
            setLayoutXml();
            callFooter(baseApcContext,activityIn, ActivityId);
            initMenusCustom(ActivityId,baseApcContext, activityIn);
            internetChangeBroadCast();
            fetchFOS();
            fetchVersionData();
            verifyVersion();
            initializeViews();
            initBackBtn();
            showProgress(true, mContentView, mProgressView);
            initializeLabels();
            initializeListeners();
            userToken =userSessionObj.getToken();
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
            Intent intent = new Intent(SImpactStudiesA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void setLayoutXml(){
        printLogs(LogTag,"setLayoutXml","a_scedit_bank_details");
        setContentView(R.layout.a_scedit_bank_details);

    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);

        spinner_fos_name = (Spinner) findViewById(R.id.spinner_fos_name);

        inputStartDate = (DatePicker) findViewById(R.id.inputStartDate);
        inputEndDate = (DatePicker) findViewById(R.id.inputEndDate);
        et_salary = (EditText) findViewById(R.id.et_salary);
        et_position = (EditText) findViewById(R.id.et_position);
        endDateBlock = findViewById(R.id.endDateBlock);
        employedBlock = findViewById(R.id.employedBlock);

        rg_optn = (RadioGroup) findViewById(R.id.rg_optn);
        rb_option_gc_1 = (RadioButton) findViewById(R.id.rb_option_gc_1);
        rb_option_gc_2 = (RadioButton) findViewById(R.id.rb_option_gc_2);
        rb_option_gc_3 = (RadioButton) findViewById(R.id.rb_option_gc_3);
        rb_option_gc_4 = (RadioButton) findViewById(R.id.rb_option_gc_4);

        rg_f_optn = (RadioGroup) findViewById(R.id.rg_f_optn);
        rb_option_fs_1 = (RadioButton) findViewById(R.id.rb_option_fs_1);
        rb_option_fs_2 = (RadioButton) findViewById(R.id.rb_option_fs_2);

        rg_e_optn = (RadioGroup) findViewById(R.id.rg_e_optn);
        rb_option_es_1 = (RadioButton) findViewById(R.id.rb_option_es_1);
        rb_option_es_2 = (RadioButton) findViewById(R.id.rb_option_es_2);
        rb_option_es_3 = (RadioButton) findViewById(R.id.rb_option_es_3);
        rb_option_es_4 = (RadioButton) findViewById(R.id.rb_option_es_4);

        btn_save = (Button) findViewById(R.id.btn_save);
    }

    @Override
    protected void initializeLabels() {

        String Label = getLabelFromDb("lbl_S233_l_gc", R.string.lbl_S233_l_gc);
        lblView = (TextView) findViewById(R.id.lbl_g_c);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S233_l_es", R.string.lbl_S233_l_es);
        lblView = (TextView) findViewById(R.id.lbl_e_status);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S233_l_isfos", R.string.lbl_S233_l_isfos);
        lblView = (TextView) findViewById(R.id.lbl_is_fos);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S233_l_fos", R.string.lbl_S233_l_fos);
        lblView = (TextView) findViewById(R.id.lbl_fos);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S233_l_start", R.string.lbl_S233_l_start);
        lblView = (TextView) findViewById(R.id.lbl_start);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S233_l_end", R.string.lbl_S233_l_end);
        lblView = (TextView) findViewById(R.id.lbl_end);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S233_l_salary", R.string.lbl_S233_l_salary);
        lblView = (TextView) findViewById(R.id.lbl_salary);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S233_l_poition", R.string.lbl_S233_l_poition);
        lblView = (TextView) findViewById(R.id.lbl_position);
        lblView.setText(Label);


        Label = getLabelFromDb("h_S233", R.string.h_S233);
        lblView = (TextView) findViewById(R.id.activity_heading);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S233_l_btn_save", R.string.lbl_S233_l_btn_save);
        lblView = (Button) findViewById(R.id.btn_save);
        lblView.setText(Label);
    }
    @Override
    protected void initializeInputs() {
        printLogs(LogTag,"initializeInputs","init");

        printLogs(LogTag, "initializeInputs", "input");
        dbSetaObj = DbHelper.getInstance(this);

        SpinnerAdapter adapter = new SpinnerAdapter(SImpactStudiesA.this, android.R.layout.simple_spinner_dropdown_item, datalistBName);
        spinner_fos_name.setAdapter(adapter);//

    }
    @Override
    protected void initializeListeners() {

        rg_optn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbbutton=(RadioButton)group.findViewById(checkedId);
                gc = rbbutton.getTag().toString();
            }
        });
        rg_e_optn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbbutton=(RadioButton)group.findViewById(checkedId);
                es = rbbutton.getTag().toString();
                esint = Integer.parseInt(rbbutton.getTag().toString());
                enableBlocks();
            }
        });
        rg_f_optn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbbutton=(RadioButton)group.findViewById(checkedId);
                isFs = rbbutton.getTag().toString();
            }
        });

        spinner_fos_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               ListarClientes myModel = (ListarClientes) parent.getSelectedItem();
                selected_fos = myModel.getId();
                printLogs(LogTag,"setOnItemSelectedListener","FOS_"+selected_fos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {}
        });



        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });
    }
    public void enableBlocks(){
        if(esint ==4){
            endDateBlock.setVisibility(View.VISIBLE);
            employedBlock.setVisibility(View.GONE);
        }
        else{
            if(esint==3){
                endDateBlock.setVisibility(View.GONE);
            }
            else{
                endDateBlock.setVisibility(View.VISIBLE);
            }
            employedBlock.setVisibility(View.VISIBLE);
        }
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
    private void initBackBtn() {
        printLogs(LogTag, "initBackBtn", "init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public int getSelectedPosition(Spinner spnr, String value,String type) {
        //Long val = Long.parseLong(value);
        int pos=0;
        SpinnerAdapter adapter = (SpinnerAdapter) spnr.getAdapter();
        for (int position = 0; position < adapter.getCount(); position++) {
            printLogs(LogTag,"getSelectedPosition","init"+adapter.getItem(position).getId()+"=="+type+"--"+value);
            if(adapter.getItem(position).getId().equals(value)){
                //if(adapter.getItemId(position) == val) {
                //spnr.setSelection(position);
                printLogs(LogTag,"getSelectedPosition","position"+position+"=="+type+"--"+value);
                pos = position;
            }
        }
        return pos;
    }
    public int getSelectedPositionSetAdapter(Spinner spnr, String value) {
        Long val = Long.parseLong(value);
        int pos=0;
        SpinnerSetAdapter adapter = (SpinnerSetAdapter) spnr.getAdapter();
        for (int position = 0; position < adapter.getCount(); position++) {
            if(adapter.getItemId(position) == val) {
                //spnr.setSelection(position);
                pos = position;
            }

        }
        return pos;
    }
    private void fetchFOS() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_223_1;
        FINAL_URL = FINAL_URL + "/token/" + token;// + "/seta_id/" + selected_Bank;
        printLogs(LogTag, "fetchFOS", "URL : " + FINAL_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, FINAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject outputJson = null;
                try {
                    printLogs(LogTag, "fetchFOS", "RESPONSE : " + response);
                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if (Status.equals("1")) {

                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        for (int i = 0; i < dataM.length(); i++) {
                            JSONObject rec = dataM.getJSONObject(i);
                            final ListarClientes GetDatab_name = new ListarClientes();
                            GetDatab_name.setName(rec.getString("f_s_study_name"));
                            GetDatab_name.setId(rec.getString("f_s_id"));
                            datalistBName.add(GetDatab_name);
                            showProgress(false, mContentView, mProgressView);
                        }
                        printLogs(LogTag, "fetchFOS", "datalistBNameGETtTTTTTTTTTTT" + datalistBName.toString());
                        //  inputSpinnerGrant.setAdapter(new ArrayAdapter<String>(GAProcessStipendClaimA.this, android.R.layout.simple_spinner_dropdown_item, SetaName));
                        SpinnerAdapter adapter = new SpinnerAdapter(SImpactStudiesA.this, android.R.layout.simple_spinner_dropdown_item, datalistBName);
                        spinner_fos_name.setAdapter(adapter);
                    } else if (Status.equals("2")) {
                        showProgress(false, mContentView, mProgressView);
                    } else {
                        //showProgress(false,mContentView,mProgressView);
                        printLogs(LogTag, "fetchFOS", "error_try_again : DATA_ERROR");
                        String sTitle = "Error :" + ActivityId + "-107";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    printLogs(LogTag, "fetchFOS", "error_555_108 : " + e.getMessage());
                    e.printStackTrace();
                    //showProgress(false,mContentView,mProgressView);
                    String sTitle = "Error :" + ActivityId + "-108";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                printLogs(LogTag, "fetchFOS", "error_try_again: " + error.getMessage());
                String sTitle = "Error :" + ActivityId + "-109";
                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
            }
        }) {

        };

        RequestQueue requestQueue = Volley.newRequestQueue(SImpactStudiesA.this);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    @Override
    protected void validateForm() {
        boolean cancel = false;
        final String txtposition = et_position.getText().toString();
        final String txtsalary = et_salary.getText().toString();

        int monthS = inputStartDate.getMonth()+1;
        final String start_date = inputStartDate.getDayOfMonth()+"-"+monthS+"-"+inputStartDate.getYear();
        int monthE = inputEndDate.getMonth()+1;
        final String end_date = inputEndDate.getDayOfMonth() + "-" + monthE + "-" + inputEndDate.getYear();
        printLogs(LogTag,"validateForm","esint-"+esint+"==isFs-"+isFs+"=="+txtsalary+"=="+txtposition);
        if(gc.isEmpty()){
            String sMessage = getLabelFromDb("error_S233_F_101", R.string.error_S233_F_101);
            rb_option_gc_4.setError(sMessage);
            cancel = true;
        }
        else if(esint==0){
            String sMessage = getLabelFromDb("error_S233_F_102", R.string.error_S233_F_102);
            rb_option_es_4.setError(sMessage);
            cancel = true;
        }
        else {
            if(esint!=4){
                if(isFs.isEmpty()){
                    String sMessage = getLabelFromDb("error_S233_F_103", R.string.error_S233_F_103);
                    rb_option_fs_2.setError(sMessage);
                    cancel = true;
                }
                if (!validatesalary(txtsalary)) {
                    String sMessage = getLabelFromDb("error_S233_F_107", R.string.error_S233_F_107);
                    et_salary.setError(sMessage);
                    cancel = true;

                } else if(!validateposition(txtposition)) {
                    String sMessage = getLabelFromDb("error_S233_F_108", R.string.error_S233_F_108);
                    et_position.setError(sMessage);
                    cancel = true;
                }
            }
        }

        if (cancel) {
            return;
        } else {
            showProgress(true, mContentView, mProgressView);
            this.FormSubmit();
        }
        printLogs(LogTag, "validateForm", "exit");
    }
    public boolean validateSDate(DatePicker inputStartDate) {
        printLogs(LogTag,"validateSDate","DATA : ");
        return true;
    }
    public boolean validateEDate(DatePicker inputStartDate){
        printLogs(LogTag,"validateEDate","DATA : ");
        return true;
    }
    private boolean validateposition(String txtposition) {
        String regx = "^[a-zA-Z\\s]+$";
        Pattern pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(txtposition);
        if (txtposition != null && txtposition.length() > 1) {
            return matcher.find();
        }
        return false;
    }
    
    private boolean validatesalary(String txtsalary) {
        if (txtsalary != null && txtsalary.length() > 0) {
            return true;
        }
        return false;
    }
    @Override
    public void FormSubmit() {
        final String universal_bank_name = String.valueOf(selected_fos);
        printLogs(LogTag, "FormSubmit", "spinner_bnk_" + selected_fos);
        final String position = et_position.getText().toString().trim();
        final String salary = et_salary.getText().toString().trim();

        int monthS = inputStartDate.getMonth()+1;
        int monthE = inputEndDate.getMonth()+1;
        final String start_date = inputStartDate.getDayOfMonth()+"-"+monthS+"-"+inputStartDate.getYear();
        final String end_date = inputEndDate.getDayOfMonth()+"-"+monthE+"-"+inputEndDate.getYear();

        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_223_2;
        String token = userSessionObj.getToken();
        FINAL_URL = FINAL_URL + "/token/" + token;
        printLogs(LogTag, "FormSubmit", "URL : " + FINAL_URL);


        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put(KEY_ES, es);
            jsonBody.put(KEY_ISFOS, isFs);
            jsonBody.put(KEY_FOS, selected_fos);
            jsonBody.put(KEY_GC, gc);
            jsonBody.put(KEY_SALARY, salary);
            jsonBody.put(KEY_POSITION, position);
            jsonBody.put(KEY_STARTDATE, start_date);
            jsonBody.put(KEY_ENDDATE, end_date);
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
                    if (Status.equals("1")) {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = getLabelFromDb("m_S233_title", R.string.m_S233_title);
                        String sMessage = getLabelFromDb("m_S233_message", R.string.m_S233_message);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showSuccessDialogSCEditBankDetails(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose, thisClass);

                    } else {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-117";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "-118";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String sTitle = "Error :" + ActivityId + "-119";
                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
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
        RequestQueue requestQueue = Volley.newRequestQueue(SImpactStudiesA.this);
        requestQueue.add(jsonObjectRequest);

    }

    public void customRedirector() {


        Bundle activeUri = new Bundle();
        activeUri.putString("generator", "S233");
        Intent intent = new Intent(SImpactStudiesA.this, SDashboardDA.class);
        intent.putExtras(activeUri);
        startActivity(intent);
        finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        printLogs(LogTag, "onOptionsItemSelected", "init");

        Bundle inputUri = new Bundle();
        Intent intent = new Intent(SImpactStudiesA.this, SDashboardDA.class);
        intent.putExtras(inputUri);
        startActivity(intent);
        finish();

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
