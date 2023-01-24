package za.co.sacpo.grant.xCubeStudent.bank;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.ListarClientes;
import za.co.sacpo.grant.xCubeLib.adapter.SpinnerAdapter;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeLib.db.DbSchema;
import za.co.sacpo.grant.xCubeLib.db.bankDetailsArray;
import za.co.sacpo.grant.xCubeLib.db.bankDetailsArrayAdapter;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeLib.spinners.SpinnerSet;
import za.co.sacpo.grant.xCubeLib.spinners.SpinnerSetAdapter;
import za.co.sacpo.grant.xCubeStudent.SDashboardDA;
import za.co.sacpo.grant.xCubeStudent.claims.SSubmitClaim;

public class SEditBankDetailA extends BaseFormAPCPrivate {
    private final String ActivityId="S122";
    private String KEY_SURNAME = "surname";
    private String KEY_INITIAL_NAME = "initial_name";
    private String KEY_ACCOUNT_NUMBER = "acc_num";
    private String KEY_BANK_NAME = "universal_bank_name";
    private String KEY_BRANCH_CODE = "branch_code";
    private String KEY_ACC_TYPE = "universal_accountType";
    public String generator;
    public View mProgressView, mContentView,heading;
    private TextView lblView;
    private EditText et_account_number, et_initial_name, et_surname;
    private Spinner spinner_bank_name, spinner_branch_code, spinner_account_type;
    public Button mSaveButton;
    private String bank_idInt,Account_typeInt,BranchCodeInt,selectedBank,selectedBranch,selectedAccount ;
    private SpinnerSetAdapter adapterUserType;
    private static SpinnerSet[] userType;
    int selected_user_type;
    String selected_bank_name;
    String selected_branch_code;
    int selected_account_type = 0;
    SEditBankDetailA thisClass;
    final ArrayList<ListarClientes> datalistBName = new ArrayList<>();
    final ArrayList<ListarClientes> datalistBCode = new ArrayList<>();

    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId){
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
        printLogs(LogTag, "onCreate", "init");
        Bundle activeInputUri = getIntent().getExtras();
        generator = activeInputUri.getString("generator");
        bank_idInt = activeInputUri.getString("bank_idInt");
        BranchCodeInt = activeInputUri.getString("BranchCodeInt");
        Account_typeInt = activeInputUri.getString("Account_typeInt");
        printLogs(LogTag,"onCreate","GENERATOR ID "+generator);
        printLogs(LogTag,"onCreate","bank_idInt_id "+bank_idInt);
        printLogs(LogTag,"onCreate","Account_type_iDDDDDD "+Account_typeInt);
        printLogs(LogTag,"onCreate","BranchCodeInt_iDDDDDD "+BranchCodeInt);
        selectedBank="0";
        selectedBranch="0";
        selectedAccount="0";
        bootStrapInit();
    }
    @Override
    protected void bootStrapInit(){
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
       // validateLogin(baseApcContext,activityIn);
        if(isConnected) {
            printLogs(LogTag,"bootStrapInit","initConnected");
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);
            initMenusCustom(ActivityId,baseApcContext,activityIn);
            internetChangeBroadCast();
            fetchVersionData();
            verifyVersion();
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
            initializeInputs();
            fetchBankname();
            printLogs(LogTag,"bootStrapInit","exitConnected");
            showProgress(false,mContentView,mProgressView);
        }else {
            printLogs(LogTag,"bootStrapInit","initConnected");
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);
            initMenusCustom(ActivityId,baseApcContext,activityIn);
            internetChangeBroadCast();
            fetchVersionData();
            verifyVersion();
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
            initializeInputs();
            fetchOfflineData();
            //fetchBankname();
            printLogs(LogTag,"bootStrapInit","exitConnected");
            showProgress(false,mContentView,mProgressView);
        }
    }

    private void fetchOfflineData() {

        printLogs(LogTag, "fetchOfflineData", "init");
        bankDetailsArrayAdapter adapter  =new bankDetailsArrayAdapter(getApplicationContext());
        List<bankDetailsArray> adapterAll = adapter.getAll();
        for (int i = 0; i < adapterAll.size(); i++) {

            String id = adapterAll.get(i).getId();
            String bank_name = adapterAll.get(i).getBank_name();
            String initial_name = adapterAll.get(i).getInitial_name();
            String account_no = adapterAll.get(i).getAccount_no();
            String branch_code = adapterAll.get(i).getBranch_code();
            String b_d_status = adapterAll.get(i).getB_d_status();
            String b_d_surname = adapterAll.get(i).getB_d_surname();
            String account_type = adapterAll.get(i).getAccount_type();
            String u_b_id = adapterAll.get(i).getU_b_id();
            String b_d_a_type = adapterAll.get(i).getB_d_a_type();
            String b_d_u_branch_id = adapterAll.get(i).getB_d_u_branch_id();


            et_initial_name.setText(initial_name);
            et_surname.setText(b_d_surname);

            et_account_number.setText(account_no);

            selectedBank = u_b_id;
            selectedBranch = b_d_u_branch_id;
            selectedAccount = b_d_a_type;
            spinner_account_type.setSelection(Integer.parseInt(selectedAccount));
        }
        showProgress(false, mContentView, mProgressView);
        printLogs(LogTag, "fetchOfflineData", "exit");

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
            Intent intent = new Intent(SEditBankDetailA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void setLayoutXml(){
        printLogs(LogTag,"setLayoutXml","a_edit_bank_name");
        setContentView(R.layout.a_edit_bank_details);

    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        heading =  findViewById(R.id.heading);
        spinner_bank_name = (Spinner) findViewById(R.id.spinner_bank_name);
        spinner_branch_code = (Spinner) findViewById(R.id.spinner_branch_code);
        spinner_account_type = (Spinner) findViewById(R.id.spinner_account_type);
        et_account_number = (EditText) findViewById(R.id.et_account_number);
        et_initial_name = (EditText) findViewById(R.id.et_initial_name);
        et_surname = (EditText) findViewById(R.id.et_surname);
        mSaveButton = (Button) findViewById(R.id.btnSave);

    }

    @Override
    protected void initializeLabels(){
        printLogs(LogTag,"initializeLabels","init");

        String Label = getLabelFromDb("lbl_S122_l_bank_name", R.string.lbl_S122_l_bank_name);
        lblView = (TextView) findViewById(R.id.lbl_bank_name);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S122_l_branch_code", R.string.lbl_S122_l_branch_code);
        lblView = (TextView) findViewById(R.id.lbl_branch_code);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S122_l_sur_name", R.string.lbl_S122_l_sur_name);
        lblView = (TextView) findViewById(R.id.lbl_sur_name);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S122_l_sur_initial", R.string.lbl_S122_l_sur_initial);
        lblView = (TextView) findViewById(R.id.lbl_initial_name);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S122_l_account_number", R.string.lbl_S122_l_account_number);
        lblView = (TextView) findViewById(R.id.lbl_account_number);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S122_l_account_type", R.string.lbl_S122_l_account_type);
        lblView = (TextView) findViewById(R.id.lbl_account_type);
        lblView.setText(Label);
        Label = getLabelFromDb("h_S122",R.string.h_S122);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));

        Label = getLabelFromDb("l_S122_save",R.string.l_S122_save);
        lblView = (Button) findViewById(R.id.btnSave);
        lblView.setText(Label);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            mSaveButton.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            et_account_number.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            et_initial_name.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            et_surname.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            spinner_bank_name.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            spinner_branch_code.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            spinner_account_type.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));

        }

    }
    @Override
    protected void initializeInputs(){
        printLogs(LogTag,"initializeInputs","init");
        Cursor resUTM = dbSetaObj.getAllByType(DbSchema.TABLE_MASTER_ARRAYS,DbSchema.COLUMN_M_A_TYPE,10);
        int total_leave_type = resUTM.getCount();
        userType = new SpinnerSet[total_leave_type];
        if(total_leave_type > 0) {
            int i=0;
            try {
                while (resUTM.moveToNext()) {
                    userType[i] = new SpinnerSet();
                    userType[i].setId(resUTM.getInt(resUTM.getColumnIndex(DbSchema.COLUMN_M_A_SERVER_ID)));
                    userType[i].setName(resUTM.getString(resUTM.getColumnIndex(DbSchema.COLUMN_M_A_NAME)));
                    i++;
                }
            } finally {
                if (resUTM != null && !resUTM.isClosed()) {
                    resUTM.close();
                }
            }
        }
        adapterUserType = new SpinnerSetAdapter(SEditBankDetailA.this,android.R.layout.simple_spinner_item,userType);
        spinner_account_type.setAdapter(adapterUserType);
        printLogs(LogTag,"initializeInputs","init");
        dbSetaObj = DbHelper.getInstance(this);
        SpinnerAdapter adapter = new SpinnerAdapter(SEditBankDetailA.this, android.R.layout.simple_spinner_dropdown_item, datalistBName);
        spinner_bank_name.setAdapter(adapter);
        SpinnerAdapter adapter2 = new SpinnerAdapter(SEditBankDetailA.this, android.R.layout.simple_spinner_dropdown_item, datalistBCode);
        spinner_branch_code.setAdapter(adapter2);
    }
    @Override
    protected void initializeListeners() {

        spinner_account_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,int position, long id) {
                selected_user_type = userType[position].getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
                // selected_user_type=0;
            }
        });

        spinner_bank_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ListarClientes myModel = (ListarClientes) parent.getSelectedItem();
                    selected_bank_name = myModel.getId();
                printLogs(LogTag,"setOnItemSelectedListener","SELECTED_BANKNAME_IDDD"+selected_bank_name);
                fetchBranchcode(selected_bank_name,selectedBranch);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {}
        });

        spinner_branch_code.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ListarClientes myModel = (ListarClientes) parent.getSelectedItem();
                selected_branch_code = myModel.getId();
                printLogs(LogTag,"setOnItemSelectedListener","SELECTED_branch_code_IDDD"+selected_branch_code);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {}
        });


        mSaveButton.setOnClickListener(new View.OnClickListener() {
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
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    public int getSelectedPosition(Spinner spnr, String value,String type) {
        int pos=0;
        SpinnerAdapter adapter = (SpinnerAdapter) spnr.getAdapter();
        for (int position = 0; position < adapter.getCount(); position++) {
            printLogs(LogTag,"getSelectedPosition","init"+adapter.getItem(position).getId()+"=="+type+"--"+value);
            if(adapter.getItem(position).getId().equals(value)){
                printLogs(LogTag,"getSelectedPosition","position"+position+"=="+type+"--"+value);
                pos = position;
            }
        }
        return pos;
    }
    public int getSelectedPositionSetAdapter(Spinner spnr, String value) {
        long val = Long.parseLong(value);
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
    private void fetchBankname() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_122_1;
        FINAL_URL = FINAL_URL + "?token=" + token;
        printLogs(LogTag, "fetchBankname", "URL : " + FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchBankname", "RESPONSE : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if (Status.equals("1")) {
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        for (int i = 0; i < dataM.length(); i++) {
                            JSONObject rec = dataM.getJSONObject(i);
                            final ListarClientes GetDatab_name = new ListarClientes();
                            GetDatab_name.setName(rec.getString("bank_name"));
                            GetDatab_name.setId(rec.getString("id"));
                            datalistBName.add(GetDatab_name);
                        }
                        printLogs(LogTag, "fetchBankname", "datalistBName" + datalistBName.toString());
                        SpinnerAdapter adapter = new SpinnerAdapter(SEditBankDetailA.this, android.R.layout.simple_spinner_dropdown_item, datalistBName);
                        spinner_bank_name.setAdapter(adapter);
                        fetchData();
                    } else if (Status.equals("2")) {
                        showProgress(false, mContentView, mProgressView);
                    } else {
                        //showProgress(false,mContentView,mProgressView);
                        printLogs(LogTag, "fetchBankname", "error_S122_107 : DATA_ERROR");
                        String sTitle = "Error :" + ActivityId + "-107";
                        String sMessage = getLabelFromDb("error_S122_107", R.string.error_S122_107);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    printLogs(LogTag, "fetchBankname", "error_555_108 : " + e.getMessage());
                    e.printStackTrace();
                    //showProgress(false,mContentView,mProgressView);
                    String sTitle = "Error :" + ActivityId + "-108";
                    String sMessage = getLabelFromDb("error_S122_108", R.string.error_S122_108);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                printLogs(LogTag, "fetchBankname", "error_S122_109: " + error.getMessage());
                String sTitle = "Error :" + ActivityId + "-109";
                String sMessage = getLabelFromDb("error_S122_109", R.string.error_S122_109);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
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

    private void fetchBranchcode(String bank_id,final String branch_id) {
        datalistBCode.clear();
        final ArrayList<ListarClientes> datalistBCodeU = new ArrayList<>();
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_122_2;
        FINAL_URL = FINAL_URL + "?token=" + token+"&bank_id="+bank_id;
        printLogs(LogTag, "fetchBranchcode", "URL : " + FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchBranchcode", "RESPONSE : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if (Status.equals("1")) {
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        for (int i = 0; i < dataM.length(); i++) {
                            JSONObject rec = dataM.getJSONObject(i);
                            ListarClientes GetDatadp = new ListarClientes();
                            GetDatadp.setName(rec.getString("branch_code"));
                            GetDatadp.setId(rec.getString("id"));
                            datalistBCodeU.add(GetDatadp);
                        }
                        //  inputSpinnerGrant.setAdapter(new ArrayAdapter<String>(GAProcessStipendClaimA.this, android.R.layout.simple_spinner_dropdown_item, SetaName));
                        SpinnerAdapter adapter5 = new SpinnerAdapter(SEditBankDetailA.this, android.R.layout.simple_spinner_dropdown_item, datalistBCodeU);
                        spinner_branch_code.setAdapter(adapter5);
                        int selectedBranchPos=getSelectedPosition(spinner_branch_code,branch_id,"branch");
                        printLogs(LogTag, "fetchBranchcode", "selectedBranchPos : " + selectedBranchPos);
                        spinner_branch_code.setSelection(selectedBranchPos);
                    } else if (Status.equals("2")) {
                        showProgress(false, mContentView, mProgressView);
                    } else {
                        showProgress(false,mContentView,mProgressView);
                        printLogs(LogTag, "fetchBranchcode", "error_555_107 : DATA_ERROR");
                        String sTitle = "Error :" + ActivityId + "-107";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    printLogs(LogTag, "fetchBranchcode", "error_try_again : " + e.getMessage());
                    e.printStackTrace();
                    showProgress(false,mContentView,mProgressView);
                    String sTitle = "Error :" + ActivityId + "-108";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                showProgress(false,mContentView,mProgressView);
                printLogs(LogTag, "fetchBranchcode", "error_try_again: " + error.getMessage());
                String sTitle = "Error :" + ActivityId + "-109";
                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
            }
        })   {
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
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_121+ "?token=" + token;
        printLogs(LogTag, "fetchData", "URL : " + FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject outputJson = null;
                try {
                    printLogs(LogTag, "getGrantDetails", "RESPONSE : " + response);
                    outputJson = new JSONObject(String.valueOf(response));
                    String Status = outputJson.getString(KEY_STATUS);
                    if (Status.equals("1")) {
                        JSONObject dataM = outputJson.getJSONObject(KEY_DATA);
                        et_account_number.setText(dataM.getString("account_no"));
                        et_initial_name.setText(dataM.getString("initial_name"));
                        et_surname.setText(dataM.getString("b_d_surname"));
                        selectedBank = dataM.getString("u_b_id");
                        selectedBranch = dataM.getString("b_d_u_branch_id");
                        fetchBranchcode(selectedBank,selectedBranch);
                        selectedAccount = dataM.getString("b_d_a_type");
                        int selectedBankPos=getSelectedPosition(spinner_bank_name,selectedBank,"bank");
                        int selectedAccountTypePos=getSelectedPositionSetAdapter(spinner_account_type,selectedAccount);
                        printLogs(LogTag, "fetchData", "selectedBranchPos : " + selectedBankPos);
                        printLogs(LogTag, "fetchData", "selectedAccount : " + selectedAccount);
                        printLogs(LogTag, "fetchData", "selectedAccountTypePos : " + selectedAccountTypePos);
                        spinner_bank_name.setSelection(selectedBankPos);
                        spinner_account_type.setSelection(selectedAccountTypePos);
                        showProgress(false, mContentView, mProgressView);
                    } else if (Status.equals("2")) {
                        showProgress(false, mContentView, mProgressView);
                    } else {
                        printLogs(LogTag, "fetchData", "error_try_again : DATA_ERROR");
                        String sTitle = "Error :" + ActivityId + "-100";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    printLogs(LogTag, "fetchData", "error_try_again : " + e.getMessage());
                    e.printStackTrace();
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
        RequestQueue queue = Volley.newRequestQueue(this);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjectRequest);
    }
    public void validateForm() {
        boolean cancel = false;
        final String txtSurname = et_surname.getText().toString();
        final String txtAccNumber = et_account_number.getText().toString();
        final String txtInitial_name = et_initial_name.getText().toString();

        if (!isSpinnerValid(selected_user_type)) {
            String ErrorTitle = "Message :" + ActivityId + "-223";
            String ErrorMessage = getLabelFromDb("error_S122_116", R.string.error_S122_116);
            spinnerError(ErrorTitle, ErrorMessage);
            cancel = true;

        } else if (!validateSurname(txtSurname)) {
            String sMessage = getLabelFromDb("error_S122_104", R.string.error_S122_104);
            et_surname.setError(sMessage);
            cancel = true;

        } else if (!validateAccountNumber(txtAccNumber)) {
            String sMessage = getLabelFromDb("error_S122_106", R.string.error_S122_106);
            et_account_number.setError(sMessage);
            cancel = true;

        } else if (!validateinitialName(txtInitial_name)) {
            String sMessage = getLabelFromDb("error_S122_105", R.string.error_S122_105);
            et_initial_name.setError(sMessage);
            cancel = true;

        } if (cancel) {
            return;
        } else {
            showProgress(true, mContentView, mProgressView);
            this.FormSubmit();
        }
        printLogs(LogTag, "validateForm", "exit");
    }
    private boolean validateSurname(String txtSurname) {
        String regx = "^[a-zA-Z\\s]+$";
        Pattern pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(txtSurname);
        if (txtSurname.length() > 1) {
            return matcher.find();
        }
        return false;
    }
    private boolean validateAccountNumber(String txtAccNumber) {
        return txtAccNumber != null && txtAccNumber.length() > 6;
    }
    private boolean validateinitialName(String txtInitial_name) {
        String regx = "^[a-zA-Z\\s]+$";
        Pattern pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(txtInitial_name);
        if (txtInitial_name.length() > 0) {
            return matcher.find();
        }
        return false;
    }
    @Override
    public void FormSubmit(){
        final String universal_accountType = String.valueOf(selected_user_type);
        final String branch_code = String.valueOf(selected_branch_code);
        final String universal_bank_name = String.valueOf(selected_bank_name);
        final String surname = et_surname.getText().toString().trim();
        final String initial_name = et_initial_name.getText().toString().trim();
        final String acc_num = et_account_number.getText().toString().trim();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_122_3;
        String token = userSessionObj.getToken();
        printLogs(LogTag, "FormSubmit", "URL : " + FINAL_URL);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put(KEY_SURNAME, surname);
            jsonBody.put(KEY_INITIAL_NAME, initial_name);
            jsonBody.put(KEY_ACCOUNT_NUMBER, acc_num);
            jsonBody.put(KEY_BANK_NAME, universal_bank_name);
            jsonBody.put(KEY_BRANCH_CODE, branch_code);
            jsonBody.put(KEY_ACC_TYPE, universal_accountType);
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
                        String sTitle = getLabelFromDb("m_S122_title", R.string.m_S122_title);
                        String sMessage = getLabelFromDb("m_S122_message", R.string.m_S122_message);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showSuccessDialogSEditBankDetailA(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose, thisClass);
                    } else {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-104";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
                String sTitle = "Error :" + ActivityId + "-106";
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

        RequestQueue requestQueue = Volley.newRequestQueue(SEditBankDetailA.this);
        requestQueue.add(jsonObjectRequest);

    }

    public void customRedirector() {

        Intent intent = new Intent(SEditBankDetailA.this, SDashboardDA.class);
        Bundle activeUri = new Bundle();
        activeUri.putString("generator", ActivityId);
        intent.putExtras(activeUri);
        startActivity(intent);
        finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(generator.equals("S121")){
            Intent intent = new Intent(SEditBankDetailA.this,SBankDA.class);
            printLogs(LogTag,"onOptionsItemSelected","SBankDA");
            startActivity(intent);
            finish();
        }
        else if(generator.equals("S199")){
            Intent intent = new Intent(SEditBankDetailA.this,SSubmitClaim.class);
            printLogs(LogTag,"onOptionsItemSelected","SSubmitClaim");
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
