package za.co.sacpo.grant.xCubeStudent.bank;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeStudent.SDashboardDA;


public class SBankDA extends BaseFormAPCPrivate {
    private String ActivityId="S121";
    public View mProgressView, mContentView;
    private TextView lblView, lblBankDetails, lblBankName, txtBankName, lblSurname, txtSurname, lblAccountType, txtAccountType,
            lblAccountNumber, txtAccNumb, lblBranchCode, txtBranchCode,lblInitialName,txtInitialName;
    Button mEditBankDetailsButton;
    public String generator;
    String bank_id,Account_type,BranchCode;
    int bank_idInt,Account_typeInt,BranchCodeInt;

    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId) {
        baseApcContext = cnt;
        CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        printLogs(lt, "setBaseApcContextParent", "weAreIn");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(), this, this.getClass().getSimpleName(),ActivityId);
        printLogs(LogTag, "onCreate", "init");
        bootStrapInit();
    }
    @Override
    protected void bootStrapInit() {
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext, activityIn);
        if(isConnected) {
            printLogs(LogTag,"bootStrapInit","initConnected");
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);
            initMenusCustom(ActivityId,baseApcContext,activityIn);
            fetchVersionData();
            verifyVersion();
            internetChangeBroadCast();
         //   initDrawer();
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
            printLogs(LogTag, "onCreate", "exitConnected");
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
    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
    @Override
    protected void verifyVersion(){
        printLogs(LogTag,"verifyVersion","init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag,"verifyVersion","isUpdate "+isUpdate);
        printLogs(LogTag,"verifyVersion","isUpdate "+ utilSessionObj.getIsUpdateRequired());
        if(isUpdate){
            Intent intent = new Intent(SBankDA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","a_s_bank_details");
        setContentView(R.layout.a_bank_details);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mEditBankDetailsButton = (Button) findViewById(R.id.btnEditBankDetails);

        lblBankDetails = (TextView) findViewById(R.id.lblBankDetails);
        lblBankName = (TextView) findViewById(R.id.lblBankName);
        txtBankName = (TextView) findViewById(R.id.txtBankName);
        lblSurname = (TextView) findViewById(R.id.lblSurname);
        txtSurname = (TextView) findViewById(R.id.txtSurname);
        lblAccountType = (TextView) findViewById(R.id.lblAccountType);
        txtAccountType = (TextView) findViewById(R.id.txtAccountType);
        lblAccountNumber = (TextView) findViewById(R.id.lblAccountNumber);
        txtAccNumb = (TextView) findViewById(R.id.txtAccNumb);
        lblBranchCode = (TextView) findViewById(R.id.lblBranchCode);
        txtBranchCode = (TextView) findViewById(R.id.txtBranchCode);
        lblInitialName = (TextView) findViewById(R.id.lblInitialName);
        txtInitialName = (TextView) findViewById(R.id.txtInitialName);
        printLogs(LogTag,"initializeViews","exit");
    }
    @Override
    protected void initializeLabels(){
        printLogs(LogTag, "initializeLabels", "init");

        String Label = getLabelFromDb("lbl_S121_lblBankDetails", R.string.lbl_S121_lblBankDetails);
        lblView = (TextView) findViewById(R.id.lblBankDetails);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S121_lblBankName", R.string.lbl_S121_lblBankName);
        lblView = (TextView) findViewById(R.id.lblBankName);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S121_lblSurname", R.string.lbl_S121_lblSurname);
        lblView = (TextView) findViewById(R.id.lblSurname);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S121_lblAccountType", R.string.lbl_S121_lblAccount_type);
        lblView = (TextView) findViewById(R.id.lblAccountType);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S121_lblAccountNumber", R.string.lbl_S121_lblAccountNumber);
        lblView = (TextView) findViewById(R.id.lblAccountNumber);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S121_lblBranchCode", R.string.lbl_S121_lblBranchCode);
        lblView = (TextView) findViewById(R.id.lblBranchCode);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S121_lblInitial_name", R.string.lbl_S121_lblInitial_name);
        lblView = (TextView) findViewById(R.id.lblInitialName);
        lblView.setText(Label);

       // Label = getLabelFromDb("lbl_S121_mEditBankDetailsButton", R.string.lbl_S121_mEditBankDetailsButton);
        Label = getLabelFromDb("b_S103_add_bank_details", R.string.b_S103_add_bank_details);
        mEditBankDetailsButton.setText(Label);

        Label = getLabelFromDb("h_S121",R.string.h_S121);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);
    }
    @Override
    protected void initializeInputs(){
        printLogs(LogTag,"initializeInputs","init");
        fetchData();
    }
    @Override
    protected void initializeListeners() {
        printLogs(LogTag,"initializeListeners","init");
        mEditBankDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle inputUri = new Bundle();
                inputUri.putString("generator","S121");
                inputUri.putString("Account_typeInt", String.valueOf(Account_typeInt));
                inputUri.putString("bank_idInt", String.valueOf(bank_idInt));
                inputUri.putString("BranchCodeInt", String.valueOf(BranchCodeInt));
                Context context = view.getContext();
                Intent intent = new Intent(SBankDA.this,SEditBankDetailA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);

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


                        Account_type = dataM.getString("b_d_a_type");
                        Account_typeInt = Integer.parseInt(Account_type);

                        BranchCode = dataM.getString("b_d_u_branch_id");
                        BranchCodeInt = Integer.parseInt(BranchCode);



                        bank_id = dataM.getString("id");
                         bank_idInt = Integer.parseInt(bank_id);


                        if (dataM.length() > 0) {
                            txtBankName.setText(dataM.getString("bank_name"));
                            txtInitialName.setText(dataM.getString("initial_name"));
                            txtSurname.setText(dataM.getString("b_d_surname"));
                            txtAccountType.setText(dataM.getString("account_type"));
                            txtAccNumb.setText(dataM.getString("account_no"));
                            txtBranchCode.setText(dataM.getString("branch_code"));
                        }
                       String Label = getLabelFromDb("lbl_S121_mEditBankDetailsButton", R.string.lbl_S121_mEditBankDetailsButton);
                        mEditBankDetailsButton.setText(Label);
                        showProgress(false, mContentView, mProgressView);
                    } else if (Status.equals("2")) {
                        showProgress(false, mContentView, mProgressView);
                    } else {
                        //showProgress(false,mContentView,mProgressView);
                        printLogs(LogTag, "fetchData", "error_try_again : DATA_ERROR");
                        String sTitle = "Error :" + ActivityId + "-100";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    printLogs(LogTag, "fetchData", "error_S121_101 : " + e.getMessage());
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
                printLogs(LogTag, "fetchData", "error_try_again : " + error.getMessage());
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
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SBankDA.this, SDashboardDA.class);
        printLogs(LogTag,"onBackPressed","sDashboard");
        startActivity(intent);
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(SBankDA.this, SDashboardDA.class);
        printLogs(LogTag,"onOptionsItemSelected","sDashboard");
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

    @Override
    protected void validateForm() {

    }

    @Override
    protected void FormSubmit() {

    }
}