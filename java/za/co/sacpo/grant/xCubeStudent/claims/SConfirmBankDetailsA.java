package za.co.sacpo.grant.xCubeStudent.claims;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
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

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeStudent.SDashboardDA;
import za.co.sacpo.grant.xCubeStudent.bank.SBankDA;

public class SConfirmBankDetailsA extends BaseAPCPrivate {

    private String ActivityId = "S232";
    public View mProgressView, mContentView;
    SConfirmBankDetailsA thisClass;
    private TextView lblView, lblBankDetails, lblBankName, txtBankName, lblSurname, txtSurname,
            lblAccountNumber, txtAccNumb, lblBranchCode, txtBranchCode,lblInitialName,txtInitialName;
    private String KEY_ID = "stipend_id";
    public WebView wv_information;
    String stipend_id = "4";
    public String generator,is_claim_submitted,is_report_due;

    public Button btnSubmitClaim, btnUpdateBankDetails,btn_back;
    private LinearLayout buttonContainer;
    TextView txtFile;



    @Override
    protected void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cAId) {
        baseApcContext = cnt;
        CAId = cAId;
        activityIn = ain;
        LogTag = lt;
        thisClass = this;

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(), this, this.getClass().getSimpleName(), ActivityId);
        printLogs(LogTag, "onCreate", "init");
        Intent inputIntent = getIntent();
        Bundle activeInputUri = getIntent().getExtras();
        is_claim_submitted= "1";
        is_report_due="1";
        if (inputIntent.hasExtra("is_claim_submitted")) {
            is_claim_submitted = activeInputUri.getString("is_claim_submitted");
            printLogs(LogTag,"onCreate","is_claim_submitted "+is_claim_submitted);
        }
        if (inputIntent.hasExtra("is_report_due")) {
            is_report_due = activeInputUri.getString("is_report_due");
            printLogs(LogTag,"onCreate","is_report_due "+is_report_due);
        }
        if (inputIntent.hasExtra("generator")) {
            generator = activeInputUri.getString("generator");
            printLogs(LogTag,"onCreate","GENERATOR ID "+generator);
        }

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
            initBackBtn();
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
            fetchData();
        }

    }

    @Override
    protected void initializeViews() {

        printLogs(LogTag, "initializeViews", "initializeViews-In");

        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);

        lblBankDetails = (TextView) findViewById(R.id.lblBankDetails);
        lblBankName = (TextView) findViewById(R.id.lblBankName);
        txtBankName = (TextView) findViewById(R.id.txtBankName);
        lblSurname = (TextView) findViewById(R.id.lblSurname);
        txtSurname = (TextView) findViewById(R.id.txtSurname);
        lblAccountNumber = (TextView) findViewById(R.id.lblAccountNumber);
        txtAccNumb = (TextView) findViewById(R.id.txtAccNumb);
        lblBranchCode = (TextView) findViewById(R.id.lblBranchCode);
        txtBranchCode = (TextView) findViewById(R.id.txtBranchCode);
        lblInitialName = (TextView) findViewById(R.id.lblInitialName);
        txtInitialName = (TextView) findViewById(R.id.txtInitialName);


        buttonContainer = (LinearLayout) findViewById(R.id.buttonContainer);

        btnSubmitClaim = (Button) findViewById(R.id.btnSubmitClaim);
        btnUpdateBankDetails = (Button) findViewById(R.id.btnUpdateBankDetails);
        btn_back = (Button) findViewById(R.id.btn_back);

        wv_information = findViewById(R.id.wv_information);
        final WebSettings webSettingsF = wv_information.getSettings();
        Resources res = getResources();
        float fontSize = res.getDimension(R.dimen.tiniestTextSize);
        webSettingsF.setDefaultFontSize((int)fontSize);
        wv_information.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        wv_information.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

    }

    @Override
    protected void initializeListeners() {


        btnSubmitClaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Bundle inBundle = new Bundle();
                Intent intent = new Intent(SConfirmBankDetailsA.this, SCMonthlyFeedbackA.class);
                inBundle.putString("generator", ActivityId);
                inBundle.putString("is_claim_submitted", String.valueOf(is_claim_submitted));
                inBundle.putString("is_report_due", String.valueOf(is_report_due));
                intent.putExtras(inBundle);
                context.startActivity(intent);
            }
        });

        btnUpdateBankDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inBundle = new Bundle();
                Intent intent = new Intent(SConfirmBankDetailsA.this, SBankDA.class);
                inBundle.putString("generator", ActivityId);
                inBundle.putString("is_claim_submitted", String.valueOf(is_claim_submitted));
                inBundle.putString("is_report_due", String.valueOf(is_report_due));
                intent.putExtras(inBundle);
                startActivity(intent);
                finish();

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context context = view.getContext();
                Bundle inBundle = new Bundle();
                Intent intent3 = new Intent(SConfirmBankDetailsA.this, SAttSummaryList.class);
                inBundle.putString("generator", ActivityId);
                inBundle.putString("is_claim_submitted", String.valueOf(is_claim_submitted));
                inBundle.putString("is_report_due", String.valueOf(is_report_due));
                intent3.putExtras(inBundle);
                context.startActivity(intent3);

            }
        });


    }

    @Override
    protected void initializeInputs() {

    }

    @Override
    protected void initializeLabels() {

        printLogs(LogTag, "initializeLabels", "initializeLabels-In");
        String Label = getLabelFromDb("l_S232_101_btn", R.string.l_S232_101_btn);
        btnSubmitClaim.setText(Label);

        Label = getLabelFromDb("h_S232", R.string.h_S232);
        lblView = (TextView) findViewById(R.id.activity_heading);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S232_lblBankName", R.string.lbl_S232_lblBankName);
        lblView = (TextView) findViewById(R.id.lblBankName);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S232_lblSurname", R.string.lbl_S232_lblSurname);
        lblView = (TextView) findViewById(R.id.lblSurname);
        lblView.setText(Label);


        Label = getLabelFromDb("lbl_S232_lblAccountNumber", R.string.lbl_S232_lblAccountNumber);
        lblView = (TextView) findViewById(R.id.lblAccountNumber);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S232_lblBranchCode", R.string.lbl_S232_lblBranchCode);
        lblView = (TextView) findViewById(R.id.lblBranchCode);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S232_lblInitial_name", R.string.lbl_S232_lblInitial_name);
        lblView = (TextView) findViewById(R.id.lblInitialName);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S232_btn_update_bank_details", R.string.lbl_S232_btn_update_bank_details);
        lblView = (Button) findViewById(R.id.btnUpdateBankDetails);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S232_btn_back", R.string.lbl_S232_btn_back);
        lblView = (Button) findViewById(R.id.btn_back);
        lblView.setText(Label);



    }

    @Override
    protected void setLayoutXml() {

        setContentView(R.layout.a_sconfirm_bank_details);

    }


    private void initBackBtn() {
        printLogs(LogTag, "initBackBtn", "init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void callDataApi() {
        printLogs(LogTag, "callDataApi", "init");
        userToken = userSessionObj.getToken();
        if (!TextUtils.isEmpty(userToken)) {
            printLogs(LogTag, "callDataApi", "Token Update");
            syncUserData(baseApcContext, activityIn);
            syncStudentData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn, ActivityId);
        }
    }

    private void fetchData(){
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_232;
        FINAL_URL = FINAL_URL + "?token=" + token;
        printLogs(LogTag, "fetchData", "URL : " + FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchData", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if (Status.equals("1")) {
                        JSONObject dataM = outputJson.getJSONObject(KEY_DATA);
                        if (dataM.length() > 0) {
                            txtBankName.setText(dataM.getString("bank_name"));
                            txtInitialName.setText(dataM.getString("initial_name"));
                            txtSurname.setText(dataM.getString("b_d_surname"));
                            String authc = dataM.getString("autharization_confirmation");
                            txtAccNumb.setText(dataM.getString("account_no"));
                            txtBranchCode.setText(dataM.getString("branch_code"));
                            wv_information.loadData(authc, "text/html", "UTF-8");
                            btnSubmitClaim.setVisibility(View.VISIBLE);
                        }
                        showProgress(false, mContentView, mProgressView);
                    } else if (Status.equals("2")) {
                        showProgress(false, mContentView, mProgressView);
                    } else {
                        showProgress(false,mContentView,mProgressView);
                        printLogs(LogTag, "fetchData", "error_try_again : DATA_ERROR");
                        String sTitle = "Error :" + ActivityId + "-100";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    printLogs(LogTag, "fetchData", "error_S232_101 : " + e.getMessage());
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

                showProgress(false, mContentView, mProgressView);
                printLogs(LogTag, "fetchData", "onErrorResponse : " + error);
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
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);

    }


    @Override
    protected void internetChangeBroadCast() {
        printLogs(LogTag, "internetChangeBroadCast", "internetChangeBroadCast-In");
        registerBroadcastIC();
    }

    @Override
    protected void fetchVersionData() {
        printLogs(LogTag, "fetchVersionData", "fetchVersionData-In");
        syncUpdates(baseApcContext, activityIn);
    }

    @Override
    protected void verifyVersion() {
        printLogs(LogTag, "verifyVersion", "init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();

        if (isUpdate == true) {
            Intent intent = new Intent(SConfirmBankDetailsA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        printLogs(LogTag, "onOptionsItemSelected", "init");
        if (item.getItemId() == android.R.id.home) {
            Bundle inBundle = new Bundle();
            printLogs(LogTag, "onOptionsItemSelected", "SDashboardDA");
            Intent intent = new Intent(SConfirmBankDetailsA.this, SAttSummaryList.class);
            inBundle.putString("generator", ActivityId);
            inBundle.putString("is_claim_submitted", String.valueOf(is_claim_submitted));
            inBundle.putString("is_report_due", String.valueOf(is_report_due));
            intent.putExtras(inBundle);
            startActivity(intent);
            finish();
        /*}else{

            Bundle inputUri = new Bundle();
            inputUri.putString("generator", "S232");
            inputUri.putString("month_year", month_year);
            inputUri.putString("grant_id", grant_id);
            Intent intent = new Intent(SConfirmBankDetailsA.this, SCMonthlyFeedbackA.class);
            intent.putExtras(inputUri);
            startActivity(intent);
            finish();*/
        }
        return true;
    }

    public void customRedirector() {

        Intent intent = new Intent(SConfirmBankDetailsA.this, SDashboardDA.class);
    /*        Bundle activeUri = new Bundle();
            activeUri.putString("generator", "337");
            intent.putExtras(activeUri);*/
        startActivity(intent);
        finish();

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