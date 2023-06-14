package za.co.sacpo.grant.xCubeMentor;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeStudent.SDashboardDA;


/*activity_mupdate_mobile*/
public class MUpdateMobile extends BaseFormAPCPrivate {

    private String ActivityId="169";
    private String KEY_PHONE="u_p_cell_no";
    private String KEY_OTP="otp";
    private String KEY_U_TYPE_ID="user_type_id";
    public EditText inputMobileNumber, mPasswordView;
    public View mProgressView, mContentView;
    public TextInputLayout inputLayoutMobile,inputLayoutPassword;
    public Button btnSend;

     String generator;
    private static final int PERMISSION_REQUEST_CODE = 1;
    EditText editTextConfirmOtp;
    private AppCompatButton buttonConfirm;
    private TextView lblView;

    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cTAId){
        baseApcContext = cnt;CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);
        bootStrapInit();

    }
    @Override
    protected void bootStrapInit() {
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext, activityIn);
        if (isConnected) {
            syncToken(baseApcContext, activityIn);
            setLayoutXml();
            setAppLogo();
            initMenusCustom(ActivityId,baseApcContext,activityIn);
            fetchVersionData();
            verifyVersion();
            internetChangeBroadCast();
            initializeViews();
            initBackBtn();
            showProgress(true,mContentView,mProgressView);

            initializeInputs();
            initializeLabels();
            initializeListeners();
            userToken =userSessionObj.getToken();
            syncToken(baseApcContext,activityIn);
            if(TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
            callDataApi();

            initializeInputs();
            printLogs(LogTag,"bootStrapInit","exitConnected");
            showProgress(false,mContentView,mProgressView);
        }
    }

    @Override
    protected void internetChangeBroadCast(){
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
        if(isUpdate==true){
            Intent intent = new Intent(MUpdateMobile.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void initializeInputs(){

    }

    @Override
    protected void initializeLabels(){

        String Label = getLabelFromDb("b_169_update",R.string.b_169_update);
        lblView = (TextView)findViewById(R.id.btnSend);
        lblView.setText(Label);

        Label = getLabelFromDb("h_169",R.string.h_169);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);
        Label = getLabelFromDb("b_confirm",R.string.b_confirm);
     /*   lblView = (TextView)findViewById(R.id.buttonConfirm);
        lblView.setText(Label);*/

        Label = getLabelFromDb("b_169_mobile",R.string.b_169_mobile);
        lblView = (TextView)findViewById(R.id.lblMobile);
        lblView.setText(Label);

    }
    @Override
    protected void initializeViews() {
        inputMobileNumber = (EditText) findViewById(R.id.inputMobileNumber);
         mPasswordView = (EditText) findViewById(R.id.inputPassword);
        inputLayoutMobile = (TextInputLayout) findViewById(R.id.inputLayoutMobile);


        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        btnSend = (Button) findViewById(R.id.btnSend);
        buttonConfirm = (AppCompatButton) findViewById(R.id.buttonConfirm);

   /*
        //Creating a LayoutInflater object for the dialog box
        LayoutInflater li = LayoutInflater.from(this);
        //Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.dialog_confirm, null);

        //Initizliaing confirm button fo dialog box and edittext of dialog box
        buttonConfirm = (AppCompatButton) confirmDialog.findViewById(R.id.buttonConfirm);
        editTextConfirmOtp = (EditText) confirmDialog.findViewById(R.id.editTextOtp);

        //Creating an alertdialog builder
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        //Adding our dialog box to the view of alert dialog
        alert.setView(confirmDialog);

        //Creating an alert dialog
        final AlertDialog alertDialog = alert.create();

        //Displaying the alert dialog
        alertDialog.show();*/
    }
    @Override
    protected void initializeListeners() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });


        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                // Code for above or equal 23 API Oriented Device
                // Your Permission granted already .Do next code
            } else {
                requestPermission();
            }
        }
    }




    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MUpdateMobile.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(MUpdateMobile.this, " Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(MUpdateMobile.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MUpdateMobile.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MUpdateMobile.this, "Permission Granted Successfully! ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MUpdateMobile.this, "Permission Denied 🙁 ", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }



    @Override

    protected void setLayoutXml() {
        setContentView(R.layout.activity_mupdate_mobile);
    }

    public void validateForm() {

        boolean cancel = false;

        if (!validateNumber(inputMobileNumber,inputLayoutMobile)) {

            cancel = true;
        }

        if (cancel) {
            return;
        } else {
            showProgress(true,mContentView,mProgressView);
            this.FormSubmit();
        }
    }
    public void FormSubmit(){
        String token = userSessionObj.getToken();
        final String phone = inputMobileNumber.getText().toString().trim();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.UPDATE_PHONE;
        printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);


        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put(KEY_U_TYPE_ID, "2");
            jsonBody.put(KEY_PHONE, phone);
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



                        confirmOtp();
                        //  JSONObject dataM = outputJson.getJSONObject(KEY_DATA);
                        showProgress(false,mContentView,mProgressView);
                        printLogs(LogTag,"FormSubmit","SUCCESS : "+Status);
                        String sTitle=getLabelFromDb("m_169_title",R.string.m_169_title);
                        String sMessage=getLabelFromDb("m_169_message",R.string.m_169_message);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

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
                        String sTitle="Error :"+ActivityId+"-102";
                        String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                        //Toast.makeText(getApplicationContext(),"ERROR LOGIN IN. PLEASE TRY AGAIN.", Toast.LENGTH_SHORT).show();
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
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    private void confirmOtp() throws JSONException {

        LayoutInflater li = LayoutInflater.from(this);

        View confirmDialog = li.inflate(R.layout.dialog_confirm, null);


        buttonConfirm = (AppCompatButton) confirmDialog.findViewById(R.id.buttonConfirm);
        editTextConfirmOtp = (EditText) confirmDialog.findViewById(R.id.editTextOtp);


        AlertDialog.Builder alert = new AlertDialog.Builder(this);


        alert.setView(confirmDialog);


        final AlertDialog alertDialog = alert.create();


        alertDialog.show();


        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Hiding the alert dialog
                alertDialog.dismiss();

                //Displaying a progressbar
                final ProgressDialog loading = ProgressDialog.show(MUpdateMobile.this, "Authenticating", "Please wait while we check the entered code", false, false);

                //Getting the user entered otp from edittext
                final String otp = editTextConfirmOtp.getText().toString().trim();
                String token = userSessionObj.getToken();

                String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.UPDATE_PHONE;

                FINAL_URL=FINAL_URL+"/token/"+token;
                printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);

                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("token", token);
                    jsonBody.put(KEY_OTP, otp);
                    jsonBody.put(KEY_U_TYPE_ID, "2");
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
                                //  JSONObject dataM = outputJson.getJSONObject(KEY_DATA);
                                showProgress(false, mContentView, mProgressView);
                                printLogs(LogTag, "FormSubmit", "SUCCESS : " + Status);
                                String sTitle = getLabelFromDb("m_169_title", R.string.m_169_title);
                                String sMessage = getLabelFromDb("m_169_message_verify", R.string.m_169_message_verify);
                                String sButtonLabelClose = "Close";
                                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                                startActivity(new Intent(MUpdateMobile.this, SDashboardDA.class));
                            }
                            else {


                                printLogs(LogTag, "FormSubmit", "else");
                                showProgress(false, mContentView, mProgressView);
                                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
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
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {


                                String sTitle = "Error :" + ActivityId + "-102";
                                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                                String sButtonLabelClose = "Close";
                                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                                //Toast.makeText(getApplicationContext(),"ERROR LOGIN IN. PLEASE TRY AGAIN.", Toast.LENGTH_SHORT).show();
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
                RequestQueue requestQueue = Volley.newRequestQueue(MUpdateMobile.this);
                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                        10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(jsonObjectRequest);
            }
        });
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(MUpdateMobile.this,MDashboardDA.class);
            printLogs(LogTag,"onOptionsItemSelected","sDashboard");
            startActivity(intent);
            finish();
        }
        return true;
    }

    public boolean validateNumber(EditText inputMobileNumber,TextInputLayout inputLayoutMobile) {
        String phone = inputMobileNumber.getText().toString().trim();
        setCustomError(inputLayoutMobile,null,inputMobileNumber);
        if (phone.isEmpty() || !isValidMobile(phone)) {
            String sMessage = getLabelFromDb("error_169_number",R.string.error_169_number);
            setCustomError(inputLayoutMobile,sMessage,inputMobileNumber);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutMobile,inputMobileNumber);
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
            if (EditView.getId() == R.id.inputMobileNumber) {
                validateNumber(EditView, EditLayout);
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
