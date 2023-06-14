package za.co.sacpo.grant;
/*
 *
 * Rule Sets
 * Variables
 * **** Loaderviews
 * **** Activity id
 * **** async calls
 * **** Support button
 * **** inputs
 * **** buttons
 * **** string variables
 * 01.setBaseApcContextParent
 * 02.Create
 * **** setxmllayout
 * **** Call setBaseApcContext
 * **** call Validate login
 * **** check connection
 * **** registerBroadcast
 * **** initializeViews
 * **** showProgressBar
 * **** initializelabels
 * **** initializeInputs
 * **** hideProgerssBar
 * 03.setLayoutXml
 * 03.InitializeViews
 * **** progressbarViews
 * **** support button
 * **** inputs
 * ******** inputsLayout
 * ******** inputs
 * ******** textWatchers
 * **** buttons
 * ******** Intents
 * ******** functions
 * 04.InitializeLabels
 * **** inputs
 * **** buttons
 * 05.InitializeInputs
 * 06.InitializeListners
 * **** spinners
 * **** buttons
 * 07.Validate
 * 08.FormSubmit
 * 09.ValidationEmail
 * 10.ValidationPassword
 * 11.TextWatcher
 * 12.pause
 * 13.Resume
 * 14.Destroy
 ******/

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPublic;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.db.students;
import za.co.sacpo.grant.xCubeLib.db.studentsAdapter;
import za.co.sacpo.grant.xCubeLib.db.workx;
import za.co.sacpo.grant.xCubeLib.db.workxAdapter;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsMentorSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsStudentSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsUserSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeMentor.MDashboardDA;
import za.co.sacpo.grant.xCubeStudent.SDashboardDA;


public class LoginA extends BaseFormAPCPublic {

    private View mProgressView, mContentView;
    private final String ActivityId = "A101";
    private TextView mSupportButton;
    private TextView v_info;
    private TextInputLayout inputLayoutEmail, inputLayoutPassword;
    private EditText mEmailView, mPasswordView;
    private Button mLoginButton;
    private TextView mForgotPassButton;
    private String KEY_EMAIL = "email";
    private String KEY_PASSWORD = "password";
    private String KEY_U_TYPE_ID = "user_type_id";
    private String device_info;
    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cTAId) {
        baseApcContext = cnt;
        CAId = cTAId;
        activityIn = ain;
        LogTag = lt;
        printLogs(lt, "setBaseApcContextParent", "weAreIn");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(), this, this.getClass().getSimpleName(), ActivityId);
        printLogs(LogTag, "onCreate", "init");
        validateLogin(baseApcContext, activityIn);
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        if (isConnected) {
            setLayoutXml();
            setAppLogo();
            fetchVersionData();
            verifyVersion();
            internetChangeBroadCast();
            printLogs(LogTag, "onCreate", "initConnected");
            initializeViews();
            showProgress(true, mContentView, mProgressView);
            initializeListeners();
            initializeLabels();
            initializeInputs();
            showProgress(false, mContentView, mProgressView);
            printLogs(LogTag, "onCreate", "exitConnected");
        }else{
            if(userSessionObj != null){
                if (userSessionObj.getUserType().equals("2")) {
                    Intent intent = new Intent(LoginA.this, SDashboardDA.class);
                    startActivity(intent);
                    finish();
                }else if (userSessionObj.getUserType().equals("5")) {
                    Intent intentM = new Intent(baseApcContext,MDashboardDA.class);
                    startActivity(intentM);
                    activityIn.finish();
                }else{
                    Toast.makeText(baseApcContext, "Please Login First...", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(baseApcContext, "Please Check Your Internet Connection...", Toast.LENGTH_SHORT).show();

            }


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
        if (utilSessionObj.getIsUpdateRequired()) {
            Intent intent = new Intent(LoginA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void setLayoutXml() {
        setContentView(R.layout.a_login);
        printLogs(LogTag, "setLayoutXml", "a_login");
    }

    @Override
    protected void initializeViews() {
        printLogs(LogTag, "initializeViews", "init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mSupportButton =  findViewById(R.id.btnSupport);

        inputLayoutEmail = findViewById(R.id.inputLayoutEmail);
        mEmailView =  findViewById(R.id.inputEmail);
        inputLayoutPassword =  findViewById(R.id.inputLayoutPassword);
        mPasswordView = findViewById(R.id.inputPassword);

        mLoginButton =  findViewById(R.id.btnLogin);
        mForgotPassButton = findViewById(R.id.btnForgotPass);
        printLogs(LogTag, "initializeViews", "exit");
        v_info = findViewById(R.id.v_info);
    }

    @Override
    protected void initializeLabels() {
        printLogs(LogTag, "initializeLabels", "init");
        String Label = getLabelFromDb("l_A101_username", R.string.l_A101_username);
        TextView lblView =  findViewById(R.id.lblEmail);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("l_A101_password", R.string.l_A101_password);
        lblView =  findViewById(R.id.lblPassword);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("b_A101_login", R.string.b_A101_login);
        mLoginButton.setText(Label);

        Label = getLabelFromDb("i_A101_login", R.string.i_A101_login);
        lblView =  findViewById(R.id.iLogin);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("b_A101_forgot_password", R.string.b_A101_forgot_password);
        mForgotPassButton.setText(Label);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mLoginButton.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            mEmailView.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            mPasswordView.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));


        }


        printLogs(LogTag, "initializeLabels", "exit");
    }

    @SuppressLint({"SetTextI18n", "HardwareIds"})
    @Override
    protected void initializeInputs() {
        v_info.setText("VERSION : "+versionName);

        JsonObject deviceObj = new JsonObject();
        deviceObj.addProperty("os_version",System.getProperty("os.version")+ "(" + android.os.Build.VERSION.INCREMENTAL + ")");
        deviceObj.addProperty("device=",android.os.Build.DEVICE);
        deviceObj.addProperty("model=",android.os.Build.MODEL);
        deviceObj.addProperty("product=",android.os.Build.PRODUCT);
        deviceObj.addProperty("release=", android.os.Build.VERSION.RELEASE );
        deviceObj.addProperty("brand=",  android.os.Build.BRAND );
        deviceObj.addProperty("display=",  android.os.Build.DISPLAY );
        deviceObj.addProperty("cpu_abi=",  android.os.Build.CPU_ABI );
        deviceObj.addProperty("cpu_abi_2=",  android.os.Build.CPU_ABI2 );
        deviceObj.addProperty("unknown=",  android.os.Build.UNKNOWN );
        deviceObj.addProperty("hardware=",  android.os.Build.HARDWARE );
        deviceObj.addProperty("build_id=",  android.os.Build.ID );
        deviceObj.addProperty("manufacturer=",  android.os.Build.MANUFACTURER );
        deviceObj.addProperty("serial=",  android.os.Build.SERIAL );
        deviceObj.addProperty("user=",  android.os.Build.USER );
        deviceObj.addProperty("host=",  android.os.Build.HOST );
        deviceObj.addProperty("tags=",  android.os.Build.TAGS );
        deviceObj.addProperty("time=",  android.os.Build.TIME );
        deviceObj.addProperty("type=",  android.os.Build.TYPE );
        deviceObj.addProperty("board=",  android.os.Build.BOARD );
        deviceObj.addProperty("bootloader=",  android.os.Build.BOOTLOADER);
        device_info = deviceObj.toString();
        printLogs(LogTag, "initializeInputs", "device_info"+device_info);
        //v_info.setText(device_info+"=="+device_info.length());
    }

    @Override
    protected void initializeListeners() {
        printLogs(LogTag, "initializeListeners", "init");
        mSupportButton.setOnClickListener(view -> {
            Intent intent = new Intent(LoginA.this, ContactSupportPublicA.class);
            startActivity(intent);
            finish();
        });
        mForgotPassButton.setOnClickListener(view -> {
            Intent intent = new Intent(LoginA.this, ForgotPassA.class);
            startActivity(intent);
            finish();
        });

        mLoginButton.setOnClickListener(view -> validateForm());
        printLogs(LogTag, "initializeListeners", "exit");
    }

    public void validateForm() {
        printLogs(LogTag, "validateForm", "init");
        boolean cancel = false;
        if (!validateUsername(mEmailView, inputLayoutEmail)) {
            cancel = true;
        } else if (!validatePassword(mPasswordView, inputLayoutPassword)) {
            cancel = true;
        }
        if (cancel) {
            return;
        } else {
            showProgress(true, mContentView, mProgressView);
            this.FormSubmit();
        }
        printLogs(LogTag, "validateForm", "exit");
    }

    public void FormSubmit() {
        final String email = mEmailView.getText().toString().trim();
        final String password = mPasswordView.getText().toString().trim();
        String LOGIN_FINAL_URL = URLHelper.DOMAIN_BASE_URL+ URLHelper.LOGIN_URL;
        printLogs(LogTag, "FormSubmit", "URL " + LOGIN_FINAL_URL);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", email);
            jsonBody.put("password", password);
            printLogs(LogTag, "FormSubmit", "jsonBody " + jsonBody);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // final String mRequestBody = jsonBody.toString();
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.POST, LOGIN_FINAL_URL, jsonBody, response -> {
            JSONObject jsonObject;
            //printLogs(LogTag, "FormSubmit", String.format("RESPONSE : %s", response));
            printLogs(LogTag, "FormSubmit", String.valueOf(response));
            try {
                jsonObject = new JSONObject(String.valueOf(response));
                String Status = jsonObject.getString(KEY_STATUS);
                //Log.d("TAG","response is:"+Status);
                if (Status.equals("1")) {
                    JSONObject dataM = jsonObject.getJSONObject(KEY_DATA);
                    final int user_id = Integer.parseInt(dataM.getString("u_id"));
                    final int user_type_id = Integer.parseInt(dataM.getString("u_t_user_type_id"));
                    userSessionObj.setUserId(user_id);
                    userSessionObj.setUserEmail(dataM.getString("u_email"));
                    userSessionObj.setUserFName(dataM.getString("u_p_fname"));
                    userSessionObj.setUserSName(dataM.getString("u_p_surname"));
                    userSessionObj.setUserThumb(dataM.getString("u_p_image_thumb"));
                    userSessionObj.setUserType(dataM.getString("r_role_id"));
                    userSessionObj.setUserMobile(dataM.getString("u_p_cell_no"));
                    userSessionObj.setUserLastLogin(dataM.getString("u_last_login_time"));
                    userSessionObj.setEmailVerified(dataM.getString("is_email_verified"));
                    userSessionObj.setUserMobileStatus(dataM.getString("u_p_cell_status"));
                    userSessionObj.setUserTypeName(dataM.getString("r_display_name"));
                    printLogs(LogTag, "FormSubmit", "USERType" + dataM.getString("r_display_name"));
                    printLogs(LogTag, "FormSubmit", "USERID" + user_id);
                    FirebaseMessaging.getInstance().getToken()
                            .addOnCompleteListener(new OnCompleteListener<String>() {
                                @Override
                                public void onComplete(@NonNull Task<String> task) {
                                    if (!task.isSuccessful()) {
                                        printLogs(LogTag, "FormSubmit", "FirebaseMessaging > Token failed "+task.getException());
                                        return;
                                    }
                                    // Get new FCM registration token
                                    String token = task.getResult();
                                    printLogs(LogTag, "FormSubmit", "FirebaseMessaging > Token"+token);
                                    callBackLogin(token, user_id, user_type_id);
                                }
                            });
//                    FirebaseMessaging.getInstance().getToken().addOnSuccessListener(token -> {
//                        if (!TextUtils.isEmpty(token)) {
//                            callBackLogin(token, user_id, user_type_id);
//                        } else{
//                            printLogs(LogTag, "FormSubmit", "FirebaseMessaging > Empty Token");
//                        }
//                    }).addOnFailureListener(e -> {
//                        printLogs(LogTag, "FormSubmit", "FirebaseMessaging > Empty Token"+e.getMessage());
//                    }).addOnCanceledListener(() -> {
//                        printLogs(LogTag, "FormSubmit", "FirebaseMessaging > Empty Token");
//                    }).addOnCompleteListener(task -> printLogs(LogTag, "FormSubmit", "FirebaseMessaging > TRA "+task.getResult()));
//                    FirebaseMessaging.getInstance().getToken().addOnSuccessListener(token -> {
//                        if (!TextUtils.isEmpty(token)) {
//                            callBackLogin(token, user_id, user_type_id);
//                        } else{
//                            printLogs(LogTag, "FormSubmit", "FirebaseMessaging > Empty Token");
//                        }
//                    }).addOnFailureListener(e -> {
//                        printLogs(LogTag, "FormSubmit", "FirebaseMessaging > Empty Token"+e.getMessage());
//                    }).addOnCanceledListener(() -> {
//                        printLogs(LogTag, "FormSubmit", "FirebaseMessaging > Empty Token");
//                    }).addOnCompleteListener(task -> printLogs(LogTag, "FormSubmit", "FirebaseMessaging > TRA "+task));
                    //}).addOnCompleteListener(task -> printLogs(LogTag, "FormSubmit", "FirebaseMessaging > TRA "+task.getResult()));

//                    FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(LoginA.this, instanceIdResult -> {
//                        String newToken = instanceIdResult.getToken();
//                        callBackLogin(newToken, user_id, user_type_id);
//                    });
                } else {
                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "-101";
                    String sMessage = getLabelFromDb("error_101_101", R.string.error_101_101);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);

                }
            } catch (JSONException e) {
                e.printStackTrace();
                showProgress(false, mContentView, mProgressView);
                String sTitle = "Error :" + ActivityId + "-103";
                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
            }
        },
                error -> {
                    printLogs(LogTag, "FormSubmit", "VolleyError " + error);
                    String sTitle = "Error :" + ActivityId + "-104";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    //Toast.makeText(getApplicationContext(),"ERROR LOGIN IN. PLEASE TRY AGAIN.", Toast.LENGTH_SHORT).show();
                }){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/json; charset=utf-8");
                header.put("Accept","*/*");
                return header;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(LoginA.this);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }


    public void callBackLogin(String tokenId, int userId, int userTypeId) {
        printLogs(LogTag, "callBackLogin", "DATA Token-UserId-Utype: " + tokenId + "-" + userId + "-" + userTypeId);
        updateToken(tokenId, userId, userTypeId);
    }


    public void updateToken(final String tokenId, final int userId, final int userTypeId) {
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.TOKEN_UPDATE;
        printLogs(LogTag, "updateToken", "URL : " + FINAL_URL);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put(URLHelper.TOKEN_TO_SERVER_KEY, tokenId);
            jsonBody.put("id", Integer.toString(userId));
            //Log.d("TOKEN","TOKEN_TO_SERVER"+tokenId+userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.POST, FINAL_URL,jsonBody, response -> {
            JSONObject jsonObject;
            printLogs(LogTag, "updateToken", "RESPONSE : " + response);
            try {
                jsonObject = new JSONObject(String.valueOf(response));
                String Status;
                Status = jsonObject.getString(KEY_STATUS);
                //Log.d("TAG","KEYSTATUS"+KEY_STATUS);
                if (Status.equals("1")) {
                    userSessionObj = new OlumsUserSession(baseApcContext);
                    userSessionObj.setToken(tokenId);
                    if (userTypeId == 2) {
                        getStudentDetails();
                    }
                    if (userTypeId == 5) {
                        getMentorData();
                    }
                } else {
                    showProgress(false, mContentView, mProgressView);
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    mPasswordView.setError(sMessage);
                    mPasswordView.requestFocus();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                showProgress(false, mContentView, mProgressView);
                String sTitle = "Error :" + ActivityId + "-109";
                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
            }
        },
                error -> {
                    String sTitle = "Error :" + ActivityId + "-110";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/json; charset=utf-8");
                header.put("Accept","*/*");
                return header;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(LoginA.this);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    public void getStudentDetails() {
        final OlumsStudentSession studentSessionObj = new OlumsStudentSession(baseApcContext);
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.STUDENT_DATA_URL + "?token=" + token;
        printLogs(LogTag, "getStudentDetails", "URL : " + FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, response -> {
            try {
                JSONObject jsonObject= new JSONObject(String.valueOf(response));
                printLogs(LogTag, "getStudentDetails", "RESPONSE : " + response);
                String Status = jsonObject.getString(KEY_STATUS);
                if (Status.equals("1")) {
                    JSONObject dataM = jsonObject.getJSONObject(KEY_DATA);

                    studentSessionObj.setStudentSNo(dataM.getString("s_s_no"));
                    studentSessionObj.setStudentIdNo(dataM.getString("s_id_no"));
                    studentSessionObj.setStudentQName(dataM.getString("q_name"));
                    //studentSessionObj.setStudentIName(dataM.getString("u_name"));
                    studentSessionObj.setGrantId(dataM.getString("grant_id"));

                    int grantIntId = Integer.parseInt(dataM.getString("grant_id"));
                    if (grantIntId > 0) {
                        studentSessionObj.setIsActiveGrant(true);
                    }
                    printLogs(LogTag, "getStudentDetails", "GrantId : " + grantIntId);
                    userSessionObj.setHasSession(true);
                    showProgress(false, mContentView, mProgressView);

                    Intent intent = new Intent(LoginA.this, SDashboardDA.class);
                    startActivity(intent);
                    finish();

                } else {
                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "-111";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                printLogs(LogTag, "getStudentDetails", "GrantId : " + e.getMessage());
                showProgress(false, mContentView, mProgressView);
                String sTitle = "Error :" + ActivityId + "-112";
                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
            }
        },
                error -> {

                    printLogs(LogTag, "getStudentDetails", "error_try_again : "+ error.toString());
                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "-113";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Accept", "*/*");
                // params.put("Content-Type","application/json");

                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    public void getMentorData() {
        getStudentList();
        getMentorWorkX();
        getMentorDetails();
    }

    public void getMentorWorkX() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.WORKSTATION;
        FINAL_URL = FINAL_URL + "?token=" + token;
        printLogs(LogTag, "getMentorWorkx", "URL : " + FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, response -> {
            try {
                JSONObject outputJson= new JSONObject(String.valueOf(response));
                printLogs(LogTag, "getMentorWorkx", "response : " + response);
                String Status = outputJson.getString(KEY_STATUS);
                if (Status.equals("1")) {
                    workxAdapter cAd = new workxAdapter(getApplicationContext());
                    cAd.truncate();
                    JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                    for (int i = 0; i < dataM.length(); i++) {
                        JSONObject rec = dataM.getJSONObject(i);
                        int Id = Integer.parseInt(rec.getString("e_g_l_id"));
                        String Name = rec.getString("e_g_l_department_name");
                        int Type = 1;
                        workx cObj = new workx(i, Id, Type, Name);

                        cAd.insert(cObj);
                    }
                } else {
                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "-114";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                showProgress(false, mContentView, mProgressView);
                String sTitle = "Error :" + ActivityId + "-115";
                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
            }
        },
                error -> {
                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "-116";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
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

    public void getStudentList() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.MSTUDENT_LIST;
        FINAL_URL = FINAL_URL + "?token=" + token;
        printLogs(LogTag, "getStudentList", "URL : " + FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, response -> {
            try {
                JSONObject outputJson= new JSONObject(String.valueOf(response));
                printLogs(LogTag, "getMentorWorkx", "response : " + response);
                String Status = outputJson.getString(KEY_STATUS);
                if (Status.equals("1")) {
                    studentsAdapter cAd = new studentsAdapter(getApplicationContext());
                    cAd.truncate();
                    JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                    for (int i = 0; i < dataM.length(); i++) {
                        JSONObject rec = dataM.getJSONObject(i);
                        int Id = Integer.parseInt(rec.getString("m_s_student_id"));
                        String Name = rec.getString("m_student_name");
                        int Type = 1;
                        students cObj = new students(i, Id, Type, Name);

                        cAd.insert(cObj);
                    }
                    showProgress(false, mContentView, mProgressView);

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
        },

                error -> {
                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "-119";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
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

    public void getMentorDetails() {
        final OlumsMentorSession mentorSessionObj = new OlumsMentorSession(baseApcContext);
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.MENTOR_DATA_URL + "?token=" + token;
        printLogs(LogTag, "getMentorDetails", "URL : " + FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, response -> {
            try {
                JSONObject outputJson= new JSONObject(String.valueOf(response));
                printLogs(LogTag, "getMentorWorkx", "response : " + response);
                String Status = outputJson.getString(KEY_STATUS);
                if (Status.equals("1")) {
                    JSONObject dataM = outputJson.getJSONObject(KEY_DATA);
                    if (dataM.length() > 0) {
                        mentorSessionObj.setHostEmployer(dataM.getString("hostemp"));
                        mentorSessionObj.setEmployerSDL(dataM.getString("e_sdl_number"));

                        printLogs(LogTag, "getMentorDetails", "Launched");
                        userSessionObj.setHasSession(true);
                        showProgress(false, mContentView, mProgressView);
                        Intent intent = new Intent(LoginA.this, MDashboardDA.class);
                        startActivity(intent);
                        finish();
                    } else {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-120";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                    showProgress(false, mContentView, mProgressView);
                } else if (Status.equals("2")) {
                    showProgress(false, mContentView, mProgressView);
                } else {

                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "-121";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                printLogs(LogTag, "getStudentDetails", "GrantId : " + e.getMessage());
                showProgress(false, mContentView, mProgressView);
                String sTitle = "Error :" + ActivityId + "-123";
                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
            }
        },
                error -> {
                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "-124";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
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

    protected class ErrorTextWatcher implements TextWatcher {
        private EditText EditView;
        private TextInputLayout EditLayout;

        private ErrorTextWatcher(EditText EditView, TextInputLayout EditLayout) {
            this.EditView = EditView;
            this.EditLayout = EditLayout;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @SuppressLint("NonConstantResourceId")
        public void afterTextChanged(Editable editable) {
            int id = EditView.getId();
            if (id == R.id.inputEmail) {
                validateUsername(EditView, EditLayout);
            } else if (id == R.id.inputPassword) {
                validatePassword(EditView, EditLayout);
            }
        }
    }

    @Override
    public void onBackPressed() {
        printLogs(LogTag, "onBackPressed", "LoginA");
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
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