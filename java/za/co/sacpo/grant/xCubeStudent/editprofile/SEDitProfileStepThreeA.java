package za.co.sacpo.grant.xCubeStudent.editprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;

public class SEDitProfileStepThreeA extends BaseFormAPCPrivate {
    private String ActivityId = "S105C";
    public View mProgressView, mContentView,heading;
    private TextView lblView;
    SEDitProfileStepThreeA thisClass;
    public Button btnUpdate;

    @Override
    protected void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cAId) {
        baseApcContext = cnt;
        CAId = cAId;
        activityIn = ain;
        LogTag = lt;
        thisClass = this;
        printLogs(lt, "setBaseApcContextParent", "weAreIn");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(), this, this.getClass().getSimpleName(), ActivityId);
        bootStrapInit();
        printLogs(LogTag, "onCreate", "initConnected");

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
            //showProgress(true, mContentView, mProgressView);
            initializeLabels();
            initializeInputs();
            userToken = userSessionObj.getToken();
            syncToken(baseApcContext, activityIn);
            if (TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
            //  fetchEnrollment();
            // fetchData();
            callDataApi();
            initializeListeners();
            //  fetchQualCategoryType();
            printLogs(LogTag, "bootStrapInit", "exitConnected");
            //showProgress(false, mContentView, mProgressView);
        }
    }

    @Override
    protected void initializeViews() {
            mContentView = findViewById(R.id.content_container);
            mProgressView = findViewById(R.id.progress_bar);
            heading = findViewById(R.id.heading);
        btnUpdate = findViewById(R.id.btnUpdate);
    }

    @Override
    protected void initializeListeners() {
 printLogs(LogTag, "initializeInputs", "init");
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });
    }

    @Override
    protected void initializeInputs() {

    }

    @Override
    protected void initializeLabels() {
        printLogs(LogTag, "initializeLabels", "init");

        String Label = getLabelFromDb("lbl_S105C_ssareacode", R.string.lbl_S105C_ssareacode);
        lblView = (TextView) findViewById(R.id.lblssareacode);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S105C_actstatus", R.string.lbl_S105C_actstatus);
        lblView = (TextView) findViewById(R.id.lblactstatus);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105C_actstatusdate", R.string.lbl_S105C_actstatusdate);
        lblView = (TextView) findViewById(R.id.lblactstatusdate);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105C_ofocode", R.string.lbl_S105C_ofocode);
        lblView = (TextView) findViewById(R.id.lblofocode);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105C_sponsorship", R.string.lbl_S105C_sponsorship);
        lblView = (TextView) findViewById(R.id.lblsponsorship);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105C_financialyear", R.string.lbl_S105C_financialyear);
        lblView = (TextView) findViewById(R.id.lblfinancialyear);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105C_nqflevel", R.string.lbl_S105C_nqflevel);
        lblView = (TextView) findViewById(R.id.lblnqflevel);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105C_saqaid", R.string.lbl_S105C_saqaid);
        lblView = (TextView) findViewById(R.id.lblsaqaid);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105C_empsdlno", R.string.lbl_S105C_empsdlno);
        lblView = (TextView) findViewById(R.id.lblempsdlno);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105C_providersdlno", R.string.lbl_S105C_providersdlno);
        lblView = (TextView) findViewById(R.id.lblprovidersdlno);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105C_economicstatus", R.string.lbl_S105C_economicstatus);
        lblView = (TextView) findViewById(R.id.lbleconomicstatus);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105C_wilstartdate", R.string.lbl_S105C_wilstartdate);
        lblView = (TextView) findViewById(R.id.lblwilstartdate);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105C_wilenddate", R.string.lbl_S105C_wilenddate);
        lblView = (TextView) findViewById(R.id.lblwilenddate);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105C_msregdate", R.string.lbl_S105C_msregdate);
        lblView = (TextView) findViewById(R.id.lblmsregdate);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105C_enrollmentstatus", R.string.lbl_S105C_enrollmentstatus);
        lblView = (TextView) findViewById(R.id.lblenrollmentstatus);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105C_project", R.string.lbl_S105C_project);
        lblView = (TextView) findViewById(R.id.lblproject);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105C_refno", R.string.lbl_S105C_refno);
        lblView = (TextView) findViewById(R.id.lblrefno);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105C_wiltype", R.string.lbl_S105C_wiltype);
        lblView = (TextView) findViewById(R.id.lblwiltype);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);



        Label = getLabelFromDb("b_S105_save", R.string.b_S105_save);
        btnUpdate.setText(Label);

        Label = getLabelFromDb("h_505", R.string.h_505);
        lblView = (TextView) findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

    }

    @Override
    protected void setLayoutXml() {
        printLogs(LogTag, "setLayoutXml", "edit_profile_stepthree");
        setContentView(R.layout.a_edit_profile_step_three);
    }

    @Override
    protected void verifyVersion() {
        printLogs(LogTag, "verifyVersion", "init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag, "verifyVersion", "isUpdate " + isUpdate);
        printLogs(LogTag, "verifyVersion", "isUpdate " + utilSessionObj.getIsUpdateRequired());
        if (isUpdate) {
            Intent intent = new Intent(SEDitProfileStepThreeA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void fetchVersionData() {
        printLogs(LogTag, "fetchVersionData", "exitConnected");
        syncUpdates(baseApcContext, activityIn);
    }

    @Override
    protected void internetChangeBroadCast() {
        printLogs(LogTag, "internetChangeBroadCast", "init");
        registerBroadcastIC();
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
    public void customRedirector() {
        Intent intent = new Intent(SEDitProfileStepThreeA.this, SEditProfileMainA.class);
        printLogs(LogTag, "onOptionsItemSelected", "SEditProfileStepTwoA");
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        printLogs(LogTag, "onOptionsItemSelected", "init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(SEDitProfileStepThreeA.this, SEditProfileMainA.class);
            printLogs(LogTag, "onOptionsItemSelected", "SEditProfileStepTwoA");
            startActivity(intent);
            finish();
        }
        return true;
    }

    @Override
    public void validateForm() {
        boolean cancel = false;
        /*if (!validateFirstName(inputFirstName, inputLayoutFirstName)) {
            cancel = true;
        } else if (!validateLastName(inputLastName, inputLayoutLastName)) {
            cancel = true;
        } else if (!validateNumber(inputMobile, inputLayoutMobile)) {
            cancel = true;
        } else if (!validateNationalId(inputNational_id, inputLayoutNational_id)) {
            cancel = true;
        }else if (!validateRegNo(inputsRegNo, inputLayoutsRegNo)) {
            cancel = true;
        } else if (!validateAlternativeId(inputalternative_id, inputLayoutalternative_id)) {
            cancel = true;
        }else if (!validateEmail(inputEmail, inputLayoutEmail)) {
            cancel = true;
        }*//* else if (!validateLearnerNo(inputLearnerNo, inputLayoutLearnerNo)) {
            cancel = true;
        } else if (!validateLearnerId(inputLearnerId, inputLayoutLearnerId)) {
            cancel = true;
        }*//*else if (!validateTaxRefNo(inputTaxRefNo, inputLayoutTaxRefNo)) {
            cancel = true;
        }*/

        if (cancel) {
            return;
        } else {
            showProgress(true, mContentView, mProgressView);
            this.FormSubmit();
        }
        printLogs(LogTag, "validateForm", "exit");
    }

    @Override
    public void FormSubmit() {
   /*     final int race_id = Integer.parseInt(String.valueOf(race_value));
        final int title = Integer.parseInt(String.valueOf(title_value));

       */
        /* if(rb_disable_y.isChecked()){
            disability_key_status = rb_disable_y.getTag().toString();
        }else if(rb_disable_n.isChecked()){
            disability_key_status = rb_disable_n.getTag().toString();
        }
        final int disability_type = disability_value;*/
        /*
        if(rb_male.isChecked()){
            gender_key_status = rb_male.getTag().toString();
        }else if(rb_female.isChecked()){
            gender_key_status = rb_female.getTag().toString();
        }

        //    final int enroll_year = Integer.parseInt(String.valueOf(selected_enroll_year));


        final int month = Integer.parseInt(String.valueOf(month_value)) +1;
        final int date = Integer.parseInt(String.valueOf(spin_date));
        final int year = Integer.parseInt(String.valueOf(spin_year));
        // final int qualCategoryType_value = qualcategory_value;

        printLogs(LogTag, "FormSubmit", "VALUE__DOB__" +"  "+date+"   "+month +"  "+year+"   "+selected_enroll_year+" titleValueee  " +title);
*/
/*       // final String email = inputEmail.getText().toString().trim();
        final String learner_no = inputLearnerNo.getText().toString().trim();
        final String learner_id = inputLearnerId.getText().toString().trim();
        final String kin_name = inputNameOfKin.getText().toString().trim();
        final String kin_con = inputContactOfKin.getText().toString().trim();
        final String intern_uot = inputInternUTO.getText().toString().trim();
        //final String intern_quali = inputInternCategoryQualification.getText().toString().trim();*/
 /*       final String tax_ref = inputTaxRefNo.getText().toString().trim();
        final String name = inputFirstName.getText().toString().trim();
        final String mobile = inputMobile.getText().toString().trim();
        final String national_id = inputNational_id.getText().toString().trim();
        final String sRegNo = inputsRegNo.getText().toString().trim();
        final String alternativeId = inputalternative_id.getText().toString().trim();
        final String sur_name = inputLastName.getText().toString().trim();*/

        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_105_3;
        printLogs(LogTag, "FormSubmit", "URL : " + FINAL_URL);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put("statssa_area_code", "2");
            jsonBody.put("popiact_status", "2");
            jsonBody.put("popiact_status_date", "12-11-2022");
            jsonBody.put("ofo_code", "89798");
            jsonBody.put("sponsorship", "2");
            jsonBody.put("financial_year", "2");
            jsonBody.put("nqf_level", "2");
            jsonBody.put("quali_saqa_id", "5464");
            jsonBody.put("emp_sdl_no", "789845");
            jsonBody.put("provider_sdl_no", "9872");
            jsonBody.put("economic_status", "2");
             jsonBody.put("wil_start_date", "12-11-2022");
             jsonBody.put("wil_end_date", "12-11-2022");
             jsonBody.put("recent_reg_date", "12-11-2022");
             jsonBody.put("enroll_status", "2");
             jsonBody.put("project", "sffesfwef");
             jsonBody.put("ref_no", "dwdw");
             jsonBody.put("wil_type_status", "2");

            printLogs(LogTag, "FormSubmit", "jsonBody " + jsonBody);
        } catch (JSONException e) {
            e.printStackTrace();
        }


//        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.POST, FINAL_URL, jsonBody, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                JSONObject jsonObject;
//                printLogs(LogTag, "FormSubmit", String.format("RESPONSE : %s", response));
//                try {
//                    jsonObject = new JSONObject(String.valueOf(response));
//                    String Status = jsonObject.getString(KEY_STATUS);
//                    if (Status.equals("1")) {
//                        SEditProfileA.this.showProgress(false, mContentView, mProgressView);
//                        String sTitle = SEditProfileA.this.getLabelFromDb("m_S105_title", R.string.m_S105_title);
//                        String sMessage = SEditProfileA.this.getLabelFromDb("m_S105_message", R.string.m_S105_message);
//                        String sButtonLabelClose = "Close";
//                        ErrorDialog.showSuccessDialogSEditProfile(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose, thisClass);
//                    } else {
//                        SEditProfileA.this.showProgress(false, mContentView, mProgressView);
//                        String sTitle = "Error :" + ActivityId + "-104";
//                        String sMessage = SEditProfileA.this.getLabelFromDb("error_try_again", R.string.error_try_again);
//                        String sButtonLabelClose = "Close";
//                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
//                    }
//                } catch (JSONException e) {
//                    SEditProfileA.this.showProgress(false, mContentView, mProgressView);
//                    String sTitle = "Error :" + ActivityId + "-S105";
//                    String sMessage = SEditProfileA.this.getLabelFromDb("error_try_again", R.string.error_try_again);
//                    String sButtonLabelClose = "Close";
//                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
//                }
//            }
//
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                showProgress(false, mContentView, mProgressView);
//                String sTitle = "Error :" + ActivityId + "-106";
//                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
//                String sButtonLabelClose = "Close";
//                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
//            }
//        }) {
//        @Override
//        public Map<String, String> getHeaders() {
//            Map<String, String> header = new HashMap<>();
//            header.put("Content-Type", "application/json; charset=utf-8");
//            header.put("Accept","*/*");
//            return header;
//        }
//    };
//
//    RequestQueue requestQueue = Volley.newRequestQueue(SEditProfileA.this);
//        requestQueue.add(jsonObjectRequest);
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