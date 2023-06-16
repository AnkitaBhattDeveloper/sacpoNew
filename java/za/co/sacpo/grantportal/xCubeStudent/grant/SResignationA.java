package za.co.sacpo.grantportal.xCubeStudent.grant;

import android.content.Context;
import android.content.Intent;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import za.co.sacpo.grantportal.AppUpdatedA;
import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.component.Utils;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;

public class SResignationA extends BaseFormAPCPrivate {
    private String ActivityId = "134";
    private String KEY_DAY = "day";
    private String KEY_MONTH = "month";
    private String KEY_YEAR = "Year";
    private String KEY_U_TYPE_ID = "user_type_id";
    public EditText inputComment;
    public View mProgressView, mContentView;
    public TextInputLayout inputLayoutcomment;
    public Button btnResign;
    private TextView lblView;

    @Override
    protected void bootStrapInit() {

    }

    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cTAId) {
        baseApcContext = cnt;
        CAId = cTAId;
        activityIn = ain;
        LogTag = lt;
    }

    @Override
    protected void initializeInputs() {

    }

    @Override
    protected void initializeLabels() {
        String Label = getLabelFromDb("b_134_s_regination", R.string.b_134_s_regination);
        lblView = (Button) findViewById(R.id.btnResign);
        lblView.setText(Label);


        Label = getLabelFromDb("h_134", R.string.h_134);
        lblView = (TextView) findViewById(R.id.activity_heading);
        lblView.setText(Label);

    }

    @Override
    protected void initializeViews() {
        inputComment = (EditText) findViewById(R.id.inputComment);
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        btnResign = (Button) findViewById(R.id.btnResign);


    }

    @Override
    protected void initializeListeners() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutXml();
//        initMenusCustom(ActivityId,baseApcContext,activityIn);
        setBaseApcContextParent(getApplicationContext(), this, this.getClass().getSimpleName(), ActivityId);
        validateLogin(baseApcContext, activityIn);
        Boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        if (isConnected == true) {
            internetChangeBroadCast();
            fetchVersionData();
            verifyVersion();
            initializeViews();
            showProgress(true, mContentView, mProgressView);
            initializeListeners();
            initializeLabels();
            initializeInputs();
            showProgress(false, mContentView, mProgressView);
        }
    }

    protected void fetchVersionData() {
        printLogs(LogTag, "fetchVersionData", "exitConnected");
        syncUpdates(baseApcContext, activityIn);
    }

    @Override
    protected void internetChangeBroadCast() {
        registerBroadcastIC();
    }

    protected void verifyVersion() {
        printLogs(LogTag, "verifyVersion", "init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag, "verifyVersion", "isUpdate " + isUpdate);
        printLogs(LogTag, "verifyVersion", "isUpdate " + utilSessionObj.getIsUpdateRequired());
        if (isUpdate == true) {
            Intent intent = new Intent(SResignationA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void setLayoutXml() {
        setContentView(R.layout.a_resignation);
    }

    @Override
    protected void validateForm() {

    }

    @Override
    protected void FormSubmit() {

    }
}
   /* public void FormSubmit() {
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.LOGIN_URL;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, FINAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject outputJson = null;
                try {
                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if (Status.equals("1")) {
                        JSONObject dataM = outputJson.getJSONObject(KEY_DATA);



                        userSessionObj.setUserId(dataM.getInt("u_id"));
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
                        String token = FirebaseInstanceId.getInstance().getToken();
                        userSessionObj.setHasSession(true);
                        showProgress(false, mContentView, mProgressView);

                        Intent intent = new Intent(SResignationA.this, SDashboardDA.class);
                        startActivity(intent);
                        finish();

                    } else {
                        showProgress(false, mContentView, mProgressView);
                        String sMessage = getLabelFromDb("error_134_100", R.string.error_134_100);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "-101";
                    String sMessage = getLabelFromDb("error_134_101", R.string.error_134_101);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String sTitle = "Error :" + ActivityId + "-102";
                String sMessage = getLabelFromDb("error_134_102", R.string.error_134_102);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                //Toast.makeText(getApplicationContext(),"ERROR LOGIN IN. PLEASE TRY AGAIN.", Toast.LENGTH_SHORT).show();
            }
        }) {
           *//* @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_DAY, );
                params.put(KEY_MONTH, );
                params.put(KEY_U_TYPE_ID, "2");
                return params;
            }
        };*//*
        RequestQueue requestQueue = Volley.newRequestQueue(SResignationA.this);
        requestQueue.add(stringRequest);
    }

    public boolean validateEmail(EditText mEmailView, TextInputLayout inputLayoutEmail) {
        String email = mEmailView.getText().toString().trim();
        setCustomError(inputLayoutEmail, null, mEmailView);
        if (email.isEmpty() || !isValidEmail(email)) {
            String sMessage = getLabelFromDb("error_134_103", R.string.error_134_103);
            setCustomError(inputLayoutEmail, sMessage, mEmailView);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutEmail, mEmailView);
            return true;
        }
    }

    public boolean validatePassword(EditText mPasswordView, TextInputLayout inputLayoutPassword) {
        String pass = mPasswordView.getText().toString().trim();
        setCustomError(inputLayoutPassword, null, mPasswordView);
        if (pass.isEmpty()) {
            String sMessage = getLabelFromDb("error_134_104", R.string.error_134_104);
            setCustomError(inputLayoutPassword, sMessage, mPasswordView);
            return false;
        } else {
            if (pass.length() < 5) {
                String sMessage = getLabelFromDb("error_134_105", R.string.error_134_105);
                setCustomError(inputLayoutPassword, sMessage, mPasswordView);
                return false;
            } else if (pass.length() > 15) {
                String sMessage = getLabelFromDb("error_134_106", R.string.error_134_106);
                setCustomError(inputLayoutPassword, sMessage, mPasswordView);
                return false;
            } else {
                setCustomErrorDisabled(inputLayoutPassword, mPasswordView);
                return true;
            }
        }
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

        public void afterTextChanged(Editable editable) {
            switch (EditView.getId()) {
                case R.id.inputEmail:
                    validateEmail(EditView, EditLayout);
                    break;
                case R.id.inputPassword:
                    validatePassword(EditView, EditLayout);
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

}*/
