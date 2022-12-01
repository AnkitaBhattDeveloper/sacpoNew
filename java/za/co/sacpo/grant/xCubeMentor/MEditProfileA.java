package za.co.sacpo.grant.xCubeMentor;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;

/*m_edit_profile*/
public class MEditProfileA extends BaseFormAPCPrivate {
    private String ActivityId="M167";
    private String KEY_NAME="fname";
    private String KEY_SURNAME="lname";
    private String KEY_PHONE="mobile";
    public EditText inputFirstName,inputLastName,inputMobile;
    public View mProgressView, mContentView,heading;
    public TextInputLayout inputLayoutFirstName,inputLayoutLastName,inputLayoutMobile;
    public Button btnUpdate;

    private TextView lblView;
    ImageView headProfile;
    MEditProfileA thisClass;
    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId){
        baseApcContext = cnt;CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        thisClass = this;
        printLogs(lt,"setBaseApcContextParent","weAreIn");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);
        bootStrapInit();
        printLogs(LogTag,"onCreate","initConnected");
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
            initializeViews();
            initBackBtn();
            showProgress(true,mContentView,mProgressView);
            initializeLabels();
            initializeInputs();
            userToken =userSessionObj.getToken();
            syncToken(baseApcContext,activityIn);
            if(TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
            callDataApi();
            initializeListeners();
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
    protected void verifyVersion() {
        printLogs(LogTag,"verifyVersion","init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag,"verifyVersion","isUpdate "+isUpdate);
        printLogs(LogTag,"verifyVersion","isUpdate "+ utilSessionObj.getIsUpdateRequired());
        if(isUpdate){
            Intent intent = new Intent(MEditProfileA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void setLayoutXml(){
        printLogs(LogTag,"setLayoutXml","m_edit_profile");
        setContentView(R.layout.m_edit_profile);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        inputMobile = (EditText) findViewById(R.id.inputMobile);
        inputFirstName = (EditText) findViewById(R.id.inputFirstName);
        inputLastName = (EditText) findViewById(R.id.inputLastName);
        inputLayoutFirstName = (TextInputLayout) findViewById(R.id.inputLayoutFirstName);
        inputLayoutMobile = (TextInputLayout) findViewById(R.id.inputLayoutMobile);
        inputLayoutLastName = (TextInputLayout) findViewById(R.id.inputLayoutLastName);

        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        heading = findViewById(R.id.heading);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
    }
    @Override
    protected void initializeLabels(){
        printLogs(LogTag,"initializeLabels","init");
        String Label = getLabelFromDb("l_M167_profile_name",R.string.l_M167_profile_name);
        lblView = (TextView)findViewById(R.id.lblFirstName);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("i_M167_edit_profile_mobile",R.string.i_M167_edit_profile_mobile);
        lblView = (TextView)findViewById(R.id.lblMobile);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_M167_sur_name",R.string.lbl_M167_sur_name);
        lblView = (TextView)findViewById(R.id.lblLastName);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);


        Label = getLabelFromDb("b_M167_save",R.string.b_M167_save);
        btnUpdate.setText(Label);

        Label = getLabelFromDb("h_M167",R.string.h_M167);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            btnUpdate.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            inputMobile.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputFirstName.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputLastName.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
        }


        }
    @Override
    protected void initializeInputs(){
        printLogs(LogTag,"initializeInputs","init");
        fetchData();
    }
    @Override
    protected void initializeListeners() {
        printLogs(LogTag,"initializeInputs","init");
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });
    }
    public void validateForm() {
        boolean cancel = false;
        if (!validateFirstName(inputFirstName,inputLayoutFirstName)) {
            cancel = true;
        }
        else if (!validateLastName(inputLastName,inputLayoutLastName)) {
            cancel = true;
        }
        else if (!validateNumber(inputMobile,inputLayoutMobile)) {
            cancel = true;
        }
        if (cancel) {
            return;
        } else {
            showProgress(true,mContentView,mProgressView);
            this.FormSubmit();
        }
        printLogs(LogTag,"validateForm","exit");
    }
    public void callDataApi(){
        printLogs(LogTag,"callDataApi","init");
        userToken = userSessionObj.getToken();
        if(!TextUtils.isEmpty(userToken)) {
            printLogs(LogTag,"callDataApi","Token Update");
            syncUserData(baseApcContext, activityIn);
            syncMentorData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn,ActivityId);
        }
    }
    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
    private void fetchData(){
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.M_REF_167;
        FINAL_URL=FINAL_URL+"?token="+token;
        printLogs(LogTag,"fetchData","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchData", "RESPONSE : " + response);
                    String Status = jsonObject.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONObject dataM = jsonObject.getJSONObject(KEY_DATA);

                        inputFirstName.setText(dataM.getString("u_p_fname"));
                        inputLastName.setText(dataM.getString("u_p_surname"));
                        inputMobile.setText(dataM.getString("u_p_cell_no"));

                        showProgress(false, mContentView, mProgressView);


                    } else{
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
        })   {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "*/*");
                // params.put("Content-Type","application/json");

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
    public boolean onOptionsItemSelected(MenuItem item) {
        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(MEditProfileA.this,MDashboardDA.class);
            printLogs(LogTag,"onOptionsItemSelected","MDashboardDA");
            startActivity(intent);
            finish();
        }
        return true;
    }
    public void FormSubmit(){
        final String name = inputFirstName.getText().toString().trim();
        final String phone = inputMobile.getText().toString().trim();
        final String sur_name = inputLastName.getText().toString().trim();
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_M167;
        printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put(KEY_NAME, name);
            jsonBody.put(KEY_PHONE, phone);
            jsonBody.put(KEY_SURNAME, sur_name);
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
                        showProgress(false,mContentView,mProgressView);
                        String sTitle=getLabelFromDb("m_M167_title",R.string.m_M167_title);
                        String sMessage=getLabelFromDb("m_M167_message",R.string.m_M167_message);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showSuccessDialogMEditProfile(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose,thisClass);
                    }
                    else{
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-104";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    showProgress(false,mContentView,mProgressView);
                    String sTitle="Error :"+ActivityId+"-105";
                    String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                    String sButtonLabelClose="Close";
                    ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-106";
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

        public void  customRedirector(){
        Intent intent = new Intent(MEditProfileA.this, MDashboardDA.class);
        printLogs(LogTag, "onOptionsItemSelected", "SFeedbackDA");
        startActivity(intent);
        }


    public boolean validateNumber(EditText inputEditMobile,TextInputLayout inputLayoutMobile) {
        String phone = inputEditMobile.getText().toString().trim();
        setCustomError(inputLayoutMobile,null,inputEditMobile);
        if (phone.isEmpty() || !isValidMobile(phone)) {
            String sMessage = getLabelFromDb("error_M167_number",R.string.error_M167_number);
            setCustomError(inputLayoutMobile,sMessage,inputEditMobile);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutMobile,inputEditMobile);
            return true;
        }
    }
    public boolean validateFirstName(EditText inputUser,TextInputLayout inputLayoutUser) {
        String name = inputUser.getText().toString().trim();
        setCustomError(inputLayoutUser,null,inputUser);
        if (name.isEmpty() || !isValidName(name)) {
            String sMessage = getLabelFromDb("error_M167_name",R.string.error_M167_name);
            setCustomError(inputLayoutUser,sMessage,inputUser);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutUser,inputUser);
            return true;
        }
    }
    public boolean validateLastName(EditText inputUser,TextInputLayout inputLayoutUser) {
        String name = inputUser.getText().toString().trim();
        setCustomError(inputLayoutUser,null,inputUser);
        if (name.isEmpty() || !isValidLName(name)) {
            String sMessage = getLabelFromDb("error_M167_name",R.string.error_M167_name);
            setCustomError(inputLayoutUser,sMessage,inputUser);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutUser,inputUser);
            return true;
        }
    }

    protected class ErrorTextWatcher implements TextWatcher {
        private EditText EditView;
        private TextInputLayout EditLayout;
        private ErrorTextWatcher(EditText EditView,TextInputLayout EditLayout) {
            this.EditView = EditView;
            this.EditLayout = EditLayout;
        }
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        public void afterTextChanged(Editable editable) {
            switch (EditView.getId()) {
                case R.id.inputFirstName:
                    validateFirstName(EditView,EditLayout);
                    break;
                case R.id.inputLastName:
                    validateLastName(EditView,EditLayout);
                    break;
                case R.id.inputMobile:
                    validateNumber(EditView,EditLayout);
                    break;
            }
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