package za.co.sacpo.grant.xCubeStudent.claims;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
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
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

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

public class SSubmitClaim extends BaseAPCPrivate {
    private String ActivityId = "S199";
    public View mProgressView, mContentView,heading;
    public ImageView imgGraph;
    private String KEY_ID = "stipend_id";
    TextView txtFile;
    SSubmitClaim thisClass;
    int is_last_claim=0;
    private TextView lblView, lblBankDetails, lblBankName, txtBankName, lblStipendClaim, txtStipendClaim, lblStipendMonth, txtStipendMonth, lblAccountNumber, txtAccNumb, lblBranchCode, txtBranchCode
    ,lblAtt,txtAtt,lblALeave,txtALeave,lblSLeave,txtSLeave,lbOPLeave,txtOPLeave,lblUPLeave,txtUPLeave,lblSurname, txtSurname,lblInitialName,txtInitialName;
    public WebView wv_information;
    public String generator,is_claim_submitted,is_report_due;

    public Button btnSubmitClaim,btn_back;
    private LinearLayout buttonContainer;
    ImageView headLogo;

    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cTAId) {
        baseApcContext = cnt;
        CAId = cTAId;
        activityIn = ain;
        LogTag = lt;
        thisClass = this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(), this, this.getClass().getSimpleName(), ActivityId);
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
        setLayoutXml();
        bootStrapInit();
        printLogs(LogTag, "onCreate", "onCreate-In");
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
        }
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

        if (isUpdate) {
            Intent intent = new Intent(SSubmitClaim.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void initializeInputs() {

        printLogs(LogTag, "initializeInputs", "init");
        fetchData();
    }

    @Override
    protected void initializeLabels() {

        printLogs(LogTag, "initializeLabels", "initializeLabels-In");
        String Label = getLabelFromDb("l_S199_101_btn", R.string.l_S199_101_btn);
        btnSubmitClaim.setText(Label);
        Label = getLabelFromDb("l_S199_101_btn_back", R.string.l_S199_101_btn_back);
        btn_back.setText(Label);


        Label = getLabelFromDb("h_S199", R.string.h_S199);
        lblView = (TextView) findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);


        Label = getLabelFromDb("lbl_S199_lblBankName", R.string.lbl_S199_lblBankName);
        lblBankName = (TextView) findViewById(R.id.lblBankName);
        lblBankName.setText(Label);

        Label = getLabelFromDb("lbl_S199_lblStipendClaim", R.string.lbl_S199_lblStipendClaim);
        lblStipendClaim = (TextView) findViewById(R.id.lblStipendClaim);
        lblStipendClaim.setText(Label);

        lblStipendMonth.setText(Label);
        Label = getLabelFromDb("lbl_S199_lblStipendMonth", R.string.lbl_S199_lblStipendMonth);
        lblStipendMonth = (TextView) findViewById(R.id.lblStipendMonth);
        lblStipendMonth.setText(Label);

        Label = getLabelFromDb("l_S220_att",R.string.l_S220_att);
        lblView = (TextView)findViewById(R.id.lblAtt);
        lblView.setText(Label);

        Label = getLabelFromDb("l_S220_lblALeave",R.string.l_S220_lblALeave);
        lblView = (TextView)findViewById(R.id.lblALeave);
        lblView.setText(Label);

        Label = getLabelFromDb("l_S220_llblsLeave",R.string.l_S220_llblsLeave);
        lblView = (TextView)findViewById(R.id.lblSLeave);
        lblView.setText(Label);

        Label = getLabelFromDb("l_S220_lblopLeave",R.string.l_S220_lblopLeave);
        lblView = (TextView)findViewById(R.id.lblOPLeave);
        lblView.setText(Label);
        Label = getLabelFromDb("l_S220_lblupLeave",R.string.l_S220_lblupLeave);
        lblView = (TextView)findViewById(R.id.lblUPLeave);
        lblView.setText(Label);
        
        
        Label = getLabelFromDb("lbl_S199_lblAccountNumber", R.string.lbl_S199_lblAccountNumber);
        lblAccountNumber = (TextView) findViewById(R.id.lblAccountNumber);
        lblAccountNumber.setText(Label);

        Label = getLabelFromDb("lbl_S199_lblBranchCode", R.string.lbl_S199_lblBranchCode);
        lblBranchCode = (TextView) findViewById(R.id.lblBranchCode);
        lblBranchCode.setText(Label);


        Label = getLabelFromDb("lbl_S232_lblSurname", R.string.lbl_S232_lblSurname);
        lblView = (TextView) findViewById(R.id.lblSurname);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S232_lblInitial_name", R.string.lbl_S232_lblInitial_name);
        lblView = (TextView) findViewById(R.id.lblInitialName);
        lblView.setText(Label);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            btnSubmitClaim.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            btn_back.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));

        }


    }

    @Override
    protected void setLayoutXml() {


        setContentView(R.layout.a_s_submit_claim);
        printLogs(LogTag, "setLayoutXml", "a_s_submit_claim");

    }

    @Override
    protected void initializeViews() {

            printLogs(LogTag, "initializeViews", "initializeViews-In");

            mContentView = findViewById(R.id.content_container);
            mProgressView = findViewById(R.id.progress_bar);
        heading = findViewById(R.id.heading);
            imgGraph = (ImageView) findViewById(R.id.imgGraph);
            lblBankDetails = (TextView) findViewById(R.id.lblBankDetails);
            lblBankName = (TextView) findViewById(R.id.lblBankName);
            txtBankName = (TextView) findViewById(R.id.txtBankName);
            lblStipendClaim = (TextView) findViewById(R.id.lblStipendClaim);
            txtStipendClaim = (TextView) findViewById(R.id.txtStipendClaim);
        lblSurname = (TextView) findViewById(R.id.lblSurname);
        txtSurname = (TextView) findViewById(R.id.txtSurname);

        lblInitialName = (TextView) findViewById(R.id.lblInitialName);
        txtInitialName = (TextView) findViewById(R.id.txtInitialName);
            lblStipendMonth = (TextView) findViewById(R.id.lblStipendMonth);
            txtStipendMonth = (TextView) findViewById(R.id.txtStipendMonth);
            lblAccountNumber = (TextView) findViewById(R.id.lblAccountNumber);
            txtAccNumb = (TextView) findViewById(R.id.txtAccNumb);
            lblBranchCode = (TextView) findViewById(R.id.lblBranchCode);
            txtBranchCode = (TextView) findViewById(R.id.txtBranchCode);

            lblAtt = (TextView) findViewById(R.id.lblAtt);
            txtAtt = (TextView) findViewById(R.id.txtAtt);
            lblALeave = (TextView) findViewById(R.id.lblALeave);
            txtALeave = (TextView) findViewById(R.id.txtALeave);
            lblSLeave = (TextView) findViewById(R.id.lblSLeave);
            txtSLeave = (TextView) findViewById(R.id.txtSLeave);
            lbOPLeave = (TextView) findViewById(R.id.lblOPLeave);
            txtOPLeave = (TextView) findViewById(R.id.txtOPLeave);
            lblUPLeave = (TextView) findViewById(R.id.lblUPLeave);
            txtUPLeave = (TextView) findViewById(R.id.txtUPLeave);
            
            buttonContainer = (LinearLayout) findViewById(R.id.buttonContainer);

            headLogo = (ImageView) findViewById(R.id.headLogo);


            btnSubmitClaim = (Button) findViewById(R.id.btnSubmitClaim);
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
        // Boolean txtAccNumb = true;

        btnSubmitClaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putapi();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printLogs(LogTag,"initializeListeners","SPastClaimDA");
                inBundle = new Bundle();
                Intent intent = new Intent(SSubmitClaim.this, SCMonthlyFeedbackA.class);
                inBundle.putString("generator", ActivityId);
                inBundle.putString("is_claim_submitted", String.valueOf(is_claim_submitted));
                inBundle.putString("is_report_due", String.valueOf(is_report_due));
                intent.putExtras(inBundle);
                startActivity(intent);
                finish();
            }
        });

    }

    private void putapi() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_199_1;
        printLogs(LogTag, "putapi", "URL : " + FINAL_URL);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
         //   jsonBody.put(KEY_ID, stipend_id);
            printLogs(LogTag, "putapi", "jsonBody " + jsonBody);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.POST, FINAL_URL, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject jsonObject;
                printLogs(LogTag, "putapi", String.format("RESPONSE : %s", response));
                try {
                    jsonObject = new JSONObject(String.valueOf(response));
                    String Status = jsonObject.getString(KEY_STATUS);
                    if (Status.equals("1")) {
                        showProgress(true, mContentView, mProgressView);
                        String sTitle = getLabelFromDb("m_S199_title", R.string.m_S199_title);
                        String sMessage = getLabelFromDb("m_S199_message", R.string.m_S199_message);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showSuccessDialogsubmitClaim(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose, thisClass);
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
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/json; charset=utf-8");
                header.put("Accept","*/*");
                return header;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(SSubmitClaim.this);
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

    private void fetchData() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_199_2;
        FINAL_URL = FINAL_URL + "?token=" + token;
        printLogs(LogTag, "fetchData", "URL : " + FINAL_URL);



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject outputJson = null;
                try {
                    printLogs(LogTag, "fetchData", "RESPONSE : " + response);
                    outputJson = new JSONObject(String.valueOf(response));
                    String Status = outputJson.getString(KEY_STATUS);
                    if (Status.equals("1")) {
                        JSONObject dataM = outputJson.getJSONObject(KEY_DATA);
                        if (dataM.length() > 0) {
                            txtBankName.setText(dataM.getString("bank_name"));
                            txtStipendClaim.setText(dataM.getString("stipend_amount"));
                            txtStipendMonth.setText(dataM.getString("claim_month"));
                            txtAccNumb.setText(dataM.getString("account_no"));
                            txtBranchCode.setText(dataM.getString("branch_code"));
                            txtSurname.setText(dataM.getString("b_d_surname"));
                            txtInitialName.setText(dataM.getString("initial_name"));
                            String payment_warning = dataM.getString("payment_warning");
                            wv_information.loadData(payment_warning, "text/html", "UTF-8");
                            txtAtt.setText(dataM.getString("attendance_count"));
                            txtALeave.setText(dataM.getString("annual_leave"));
                            txtSLeave.setText(dataM.getString("sick_leave"));
                            txtOPLeave.setText(dataM.getString("other_leave"));
                            txtUPLeave.setText(dataM.getString("unpaid_leave"));
                            lblAtt.setText(dataM.getString("attendance_duration"));
                            txtAtt.setText(dataM.getString("attendance_count"));
                            String isLastClaim =dataM.getString("isLastClaim");
                            is_last_claim = Integer.parseInt(isLastClaim);
                            String imagGraphS =URLHelper.DOMAIN_URL+dataM.getString("chart_link");
                            printLogs(LogTag,"fetchData","imagGraphS : "+imagGraphS);
                            Picasso.get().load(imagGraphS).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).networkPolicy(NetworkPolicy.NO_CACHE).into(imgGraph);
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
                    printLogs(LogTag, "fetchData", "error_S199_101 : " + e.getMessage());
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
    public boolean onOptionsItemSelected(MenuItem item) {
        printLogs(LogTag, "onOptionsItemSelected", "init");
        Bundle inBundle = new Bundle();
        Intent intent = new Intent(SSubmitClaim.this, SCMonthlyFeedbackA.class);
        printLogs(LogTag, "onOptionsItemSelected", "SStipendDetailsA");
        inBundle.putString("generator", ActivityId);
        inBundle.putString("is_claim_submitted", String.valueOf(is_claim_submitted));
        inBundle.putString("is_report_due", String.valueOf(is_report_due));
        intent.putExtras(inBundle);
        startActivity(intent);
        finish();

        return true;
    }

    public void customRedirector() {
        if(is_last_claim==1){
            printLogs(LogTag, "customRedirector", "SImpactStudiesA");
            inBundle = new Bundle();
            Intent intent = new Intent(SSubmitClaim.this, SImpactStudiesA.class);
            inBundle.putString("generator", ActivityId);
            intent.putExtras(inBundle);
            startActivity(intent);
            finish();
        }
        else{
            inBundle = new Bundle();
            Intent intent = new Intent(SSubmitClaim.this, SDashboardDA.class);
            inBundle.putString("generator", ActivityId);
            intent.putExtras(inBundle);
            startActivity(intent);
            finish();
        }


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