package za.co.sacpo.grant.xCubeStudent.editprofile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.ListarClientes;
import za.co.sacpo.grant.xCubeLib.adapter.SpinAdapter;
import za.co.sacpo.grant.xCubeLib.adapter.SpinnerAdapter;
import za.co.sacpo.grant.xCubeLib.dataObj.SpinnerObj;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeStudent.SDashboardDA;

public class SEditProfileStepOneA extends BaseFormAPCPrivate {
    private String ActivityId = "S105A";

    private String KEY_NAME = "fname";
    private String KEY_SURNAME = "lname";
    private String KEY_PHONE = "mobile";
    private String KEY_EMAIL = "email";
    private String KEY_LEARNER_NO = "learner_no";
    private String KEY_LEARNER_ID = "learner_id";
    private String KEY_KIN_NAME = "kin_name";
    private String KEY_KIN_CONTACT = "kin_con";
    private String KEY_INTERN_UOT = "intern_uot";
    private String KEY_INTERN_QUALIFICATION = "intern_quali";
    private String KEY_TAX_REF = "tax_ref";
    private String KEY_STATUS_DISABILITY = "disability_key_status";
    private String KEY_STATUS_DISABILITY_TYPE = "disability_type";
    private String KEY_STATUS_GENDER = "gender_key_status";
    private String KEY_STATUS_RACE = "race_id";
    private String KEY_STATUS_TITLE = "title";
    private String KEY_STATUS_ENROLL = "enroll_year";
    private String KEY_STATUS_MONTH = "month";
    private String KEY_STATUS_YEAR = "year";
    private String KEY_STATUS_DATE = "date";

    final ArrayList<ListarClientes> datalist = new ArrayList<>();
    String rb_genderValue,rb_disableValue,spin_race,spin_nationality,spin_title,spin_day,spin_month,spin_year,spin_disability;
    public EditText inputFirstName, inputLastName, inputMobile,inputNational_id,inputsRegNo,inputalternative_id, inputEmail, inputLearnerNo, inputLearnerId, inputNameOfKin, inputContactOfKin, inputInternUTO, inputInternCategoryQualification, inputTaxRefNo;
    public View mProgressView, mContentView,heading;
    public TextInputLayout inputLayoutFirstName, inputLayoutLastName, inputLayoutMobile,inputLayoutNational_id, inputLayoutEmail, inputLayoutsRegNo, inputLayoutalternative_id,inputLayoutLearnerId, inputLayoutNameOfKin, inputLayoutContactOfKin, inputLayoutInternUTO, inputLayoutInternCategoryQualification, inputLayoutTaxRefNo;
    public Button btnUpdate;
    private Spinner inputSpinnerTitle, inputSpinnerRace, SpinnerDay, SpinnerMonth, SpinnerYear, inputSpinnerNationality, Spin_EnrollmentYear,spinner_InternCategoryQualification;
    private LinearLayout ll_DisabilityType;
    private RadioGroup RGPhysicalDisAbility, RgGender;
    private RadioButton rb_disable_y, rb_disable_n, rb_male, rb_female;
    private TextView lblView;
    ImageView headProfile;
    private int disability_status;
    private int gender_status;
    String spin_date,spin_years;
    String disability_key_status = "";
    String gender_key_status = "";
    String selected_enroll_year;
    String race_value, title_value,enrollment_no,month_value;
    int disability_value,qualcategory_value;
    public SpinnerObj[] StuQualcatType ;
    SEditProfileStepOneA thisClass;




    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cTAId) {
        baseApcContext = cnt;
        CAId = cTAId;
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
            showProgress(true, mContentView, mProgressView);
            initializeLabels();
            initializeInputs();
            userToken = userSessionObj.getToken();
            syncToken(baseApcContext, activityIn);
            if (TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
          //  fetchEnrollment();
            fetchData();
            callDataApi();
            initializeListeners();
          //  fetchQualCategoryType();
            printLogs(LogTag, "bootStrapInit", "exitConnected");
            showProgress(false, mContentView, mProgressView);
        }
    }

    private void fetchQualCategoryType() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.Qual_Category_Type;
        FINAL_URL=FINAL_URL+"/"+token;
        printLogs(LogTag,"fetchTicketIssueSpinner","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchTicketIssueSpinner", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        StuQualcatType = new SpinnerObj[dataM.length()];
                        for (int i = 0; i <dataM.length() ; i++) {
                            JSONObject jsonObject = dataM.getJSONObject(i);
                            StuQualcatType[i] = new SpinnerObj();
                            StuQualcatType[i].setId(jsonObject.getString("type_id"));
                            StuQualcatType[i].setName(jsonObject.getString("type_title"));

                        }
                        SpinAdapter adapter = new SpinAdapter(SEditProfileStepOneA.this, android.R.layout.simple_spinner_item, StuQualcatType);
                        spinner_InternCategoryQualification.setAdapter(adapter);
                        spinner_InternCategoryQualification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                qualcategory_value = Integer.parseInt(adapter.getItem(i).getId());
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }
                    else if(Status.equals("2")) {
                        // showProgress(false,mContentRView,mProgressRView);
                    }
                    else{
                        // showProgress(false,mContentRView,mProgressRView);
                        String sTitle="Error :"+ActivityId+"-102";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    //showProgress(false,mContentRView,mProgressRView);
                    String sTitle="Error :"+ActivityId+"-103";
                    String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                    String sButtonLabelClose="Close";
                    ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  showProgress(false,mContentRView,mProgressRView);
                        String sTitle="Error :"+ActivityId+"-104";
                        printLogs(LogTag, "fetchData", "VolleyError : " + error);
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

                    }
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
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag, "verifyVersion", "isUpdate " + isUpdate);
        printLogs(LogTag, "verifyVersion", "isUpdate " + utilSessionObj.getIsUpdateRequired());
        if (isUpdate) {
            Intent intent = new Intent(SEditProfileStepOneA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void setLayoutXml() {
        printLogs(LogTag, "setLayoutXml", "m_edit_profile");
        setContentView(R.layout.a_edit_profile_step_one);
    }

    @Override
    protected void initializeViews() {
        printLogs(LogTag, "initializeViews", "init");
        inputMobile = findViewById(R.id.inputMobile);
        inputNational_id = findViewById(R.id.inputNational_id);
        inputsRegNo = findViewById(R.id.inputsRegNo);
        inputalternative_id = findViewById(R.id.inputalternative_id);
        inputFirstName = findViewById(R.id.inputFirstName);
        inputLastName = findViewById(R.id.inputLastName);
        inputEmail = findViewById(R.id.inputEmail);

        inputTaxRefNo = findViewById(R.id.inputTaxRefNo);

        inputLayoutFirstName = findViewById(R.id.inputLayoutFirstName);
        inputLayoutMobile = findViewById(R.id.inputLayoutMobile);
        inputLayoutNational_id = findViewById(R.id.inputLayoutNational_id);
        inputLayoutsRegNo = findViewById(R.id.inputLayoutsRegNo);
        inputLayoutalternative_id = findViewById(R.id.inputLayoutalternative_id);
        inputLayoutEmail = findViewById(R.id.inputLayoutEmail);
        inputLayoutLastName = findViewById(R.id.inputLayoutLastName);

      //  inputLayoutInternCategoryQualification = findViewById(R.id.inputLayoutInternCategoryQualification);
        inputLayoutTaxRefNo = findViewById(R.id.inputLayoutTaxRefNo);

        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        heading = findViewById(R.id.heading);
        btnUpdate = findViewById(R.id.btnUpdate);


        RgGender = findViewById(R.id.RgGender);

        inputSpinnerTitle = findViewById(R.id.inputSpinnerTitle);
        inputSpinnerRace = findViewById(R.id.inputSpinnerRace);
        SpinnerDay = findViewById(R.id.SpinnerDay);
        SpinnerMonth = findViewById(R.id.SpinnerMonth);
        SpinnerYear = findViewById(R.id.SpinnerYear);
        inputSpinnerNationality = findViewById(R.id.inputSpinnerNationality);
               rb_male = findViewById(R.id.rb_male);
        rb_female = findViewById(R.id.rb_female);

        /*inputLearnerNo = findViewById(R.id.inputLearnerNo);
        inputLearnerId = findViewById(R.id.inputLearnerId);
        inputNameOfKin = findViewById(R.id.inputNameOfKin);
        inputContactOfKin = findViewById(R.id.inputContactOfKin);
        inputInternUTO = findViewById(R.id.inputInternUTO);
        ll_DisabilityType = findViewById(R.id.ll_DisabilityType);
        inputLayoutLearnerNo = findViewById(R.id.inputLayoutLearnerNo);
        inputLayoutLearnerId = findViewById(R.id.inputLayoutLearnerId);
        inputLayoutNameOfKin = findViewById(R.id.inputLayoutNameOfKin);
        inputLayoutContactOfKin = findViewById(R.id.inputLayoutContactOfKin);
        inputLayoutInternUTO = findViewById(R.id.inputLayoutInternUTO);
        RGPhysicalDisAbility = findViewById(R.id.RGPhysicalDisAbility);
        SpinnerDisabilityType = findViewById(R.id.inputDisabilityType);
        Spin_EnrollmentYear = findViewById(R.id.inputEnrollmentYear);
        spinner_InternCategoryQualification = findViewById(R.id.spinner_InternCategoryQualification);

        rb_disable_y = findViewById(R.id.rb_disable_y);
        rb_disable_n = findViewById(R.id.rb_disable_n);

        // inputInternCategoryQualification = findViewById(R.id.inputInternCategoryQualification);*/
    }

    @Override
    protected void initializeLabels() {
        printLogs(LogTag, "initializeLabels", "init");
        String Label = getLabelFromDb("l_S105_profile_name", R.string.l_S105_profile_name);
        lblView = (TextView) findViewById(R.id.lblFirstName);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("i_S105_edit_profile_mobile", R.string.i_S105_edit_profile_mobile);
        lblView = (TextView) findViewById(R.id.lblMobile);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("i_S105_edit_profile_national_id", R.string.i_S105_edit_profile_national_id);
        lblView = (TextView) findViewById(R.id.lblnational_id);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("i_S105_edit_profile_sRegNo", R.string.i_S105_edit_profile_sRegNo);
        lblView = (TextView) findViewById(R.id.lblsRegNo);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("i_S105_edit_profile_alternativeid", R.string.i_S105_edit_profile_alternativeid);
        lblView = (TextView) findViewById(R.id.lblalternative_id);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_sur_name", R.string.lbl_S105_sur_name);
        lblView = (TextView) findViewById(R.id.lblLastName);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S105_email", R.string.lbl_S105_email);
        lblView = (TextView) findViewById(R.id.lblEmail);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S105_title", R.string.lbl_S105_title);
        lblView = (TextView) findViewById(R.id.lblTitle);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

       /* Label = getLabelFromDb("lbl_S105_learner_no", R.string.lbl_S105_learner_no);
        lblView = (TextView) findViewById(R.id.lblLearnerNo);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S105_learner_id", R.string.lbl_S105_learner_id);
        lblView = (TextView) findViewById(R.id.lblLearnerId);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);*/

        Label = getLabelFromDb("lbl_S105_race", R.string.lbl_S105_race);
        lblView = (TextView) findViewById(R.id.lblRace);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S105_nationality", R.string.lbl_S105_nationality);
        lblView = (TextView) findViewById(R.id.lblNationality);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S105_DOB", R.string.lbl_S105_DOB);
        lblView = (TextView) findViewById(R.id.lblDob);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

      /*  Label = getLabelFromDb("lbl_S105_PhysicalDis", R.string.lbl_S105_PhysicalDis);
        lblView = (TextView) findViewById(R.id.lblPhysicalDis);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_disability_type", R.string.lbl_S105_disability_type);
        lblView = (TextView) findViewById(R.id.lblDisabilityType);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S105_enrollment_year", R.string.lbl_S105_enrollment_year);
        lblView = (TextView) findViewById(R.id.lblYearOfEnrollment);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S105_name_of_kin", R.string.lbl_S105_name_of_kin);
        lblView = (TextView) findViewById(R.id.lblNameOfKin);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S105_contact_of_kin", R.string.lbl_S105_contact_of_kin);
        lblView = (TextView) findViewById(R.id.lblContactOfKin);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S105_intern_UTO", R.string.lbl_S105_intern_UTO);
        lblView = (TextView) findViewById(R.id.lblInternUTO);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S105_intern_category_Q", R.string.lbl_S105_intern_category_Q);
        lblView = (TextView) findViewById(R.id.lblInternCategoryQualification);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);*/
        Label = getLabelFromDb("lbl_S105_Tax_ref_no", R.string.lbl_S105_Tax_ref_no);
        lblView = (TextView) findViewById(R.id.lblTaxRefNo);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S105_gender", R.string.lbl_S105_gender);
        lblView = (TextView) findViewById(R.id.lblGender);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("b_S105_save", R.string.b_S105_save);
        btnUpdate.setText(Label);

        Label = getLabelFromDb("h_505", R.string.h_505);
        lblView = (TextView) findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            btnUpdate.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            inputMobile.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputNational_id.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputsRegNo.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputalternative_id.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputFirstName.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputLastName.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputEmail.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            /*inputLearnerNo.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputLearnerId.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputNameOfKin.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputContactOfKin.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputInternUTO.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));*/
            inputTaxRefNo.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
           // ll_DisabilityType.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputSpinnerTitle.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            inputSpinnerRace.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            inputSpinnerNationality.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            SpinnerDay.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            SpinnerMonth.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            SpinnerYear.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            //.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
           // Spin_EnrollmentYear.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
          //  spinner_InternCategoryQualification.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));


        }
    }

    @Override
    protected void initializeInputs() {
        printLogs(LogTag, "initializeInputs", "init");


        //SPINNER FROM API
    /*    SpinnerAdapter adapter5 = new SpinnerAdapter(SEditProfileA.this, android.R.layout.simple_spinner_dropdown_item, datalist);
        Spin_EnrollmentYear.setAdapter(adapter5);*/


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


      /*  RGPhysicalDisAbility.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) findViewById(checkedId);
                disability_status = RGPhysicalDisAbility.getCheckedRadioButtonId();
                printLogs(LogTag, "initializeListeners", "disability_status : " + disability_status);

            }
        });*/


        RgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                gender_status = RgGender.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) findViewById(checkedId);
                printLogs(LogTag, "initializeListeners", "gender_status : " + gender_status);

            }
        });


  /*      rb_disable_y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_DisabilityType.setVisibility(View.VISIBLE);
                //disability_key_status = rb_disable_y.getTag().toString();
            }
        });

        rb_disable_n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ll_DisabilityType.setVisibility(View.GONE);
              //  disability_key_status = rb_disable_n.getTag().toString();
            }
        });*/


        rb_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender_key_status = rb_male.getTag().toString();
            }
        });

        rb_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender_key_status = rb_female.getTag().toString();
            }
        });


        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.race, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputSpinnerRace.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        inputSpinnerRace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                race_value = String.valueOf(parent.getSelectedItemId());
                printLogs(LogTag, "initializeListeners", "race_id" + race_value);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final ArrayAdapter<CharSequence> nationalityadapter = ArrayAdapter.createFromResource(this, R.array.race, android.R.layout.simple_spinner_item);
        nationalityadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputSpinnerNationality.setAdapter(nationalityadapter);
        nationalityadapter.notifyDataSetChanged();


        inputSpinnerNationality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                race_value = String.valueOf(parent.getSelectedItemId());
                printLogs(LogTag, "initializeListeners", "race_id" + race_value);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

      /*  final ArrayAdapter<CharSequence> adapter7 = ArrayAdapter.createFromResource(this, R.array.disability_type, android.R.layout.simple_spinner_item);
        adapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerDisabilityType.setAdapter(adapter7);
        adapter7.notifyDataSetChanged();


        SpinnerDisabilityType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                disability_value = Math.toIntExact(parent.getSelectedItemId());
                printLogs(LogTag, "initializeListeners", "disability_value" + disability_value);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/


   final ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(this, R.array.month_type, android.R.layout.simple_spinner_item);
        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerMonth.setAdapter(adapter6);
        adapter6.notifyDataSetChanged();


        SpinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                month_value = String.valueOf(parent.getSelectedItemId());
                printLogs(LogTag, "initializeListeners", "month_value" + month_value);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.title, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputSpinnerTitle.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();


        inputSpinnerTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                title_value = String.valueOf(parent.getSelectedItemId());
                printLogs(LogTag, "initializeListeners", "title_value" + title_value);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1900; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }


        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        final Spinner SpinnerYear = (Spinner)findViewById(R.id.SpinnerYear);
        SpinnerYear.setAdapter(adapter3);


        SpinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // spin_year = parent.getItemAtPosition(position).toString();
                spin_year = parent.getItemAtPosition(SpinnerYear.getSelectedItemPosition()).toString();
                //getSpinYearPos(spin_year);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList<String> days = new ArrayList<String>();
        for (int i = 1; i <= 31; i++) {
            days.add(Integer.toString(i));
        }

        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, days);
        Spinner SpinnerDay = (Spinner)findViewById(R.id.SpinnerDay);
        SpinnerDay.setAdapter(adapter4);

        SpinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spin_date = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

      /*  Spin_EnrollmentYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ListarClientes myModel = (ListarClientes) parent.getSelectedItem();
                selected_enroll_year = myModel.getId();
                printLogs(LogTag,"setOnItemSelectedListener","value_selected_enroll_year"+selected_enroll_year);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/

    }



    public void validateForm() {
        boolean cancel = false;
        if (!validateFirstName(inputFirstName, inputLayoutFirstName)) {
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
        }/* else if (!validateLearnerNo(inputLearnerNo, inputLayoutLearnerNo)) {
            cancel = true;
        } else if (!validateLearnerId(inputLearnerId, inputLayoutLearnerId)) {
            cancel = true;
        }*/else if (!validateTaxRefNo(inputTaxRefNo, inputLayoutTaxRefNo)) {
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



    public boolean validateNumber(EditText inputEditMobile, TextInputLayout inputLayoutMobile) {
        String phone = inputEditMobile.getText().toString().trim();
        setCustomError(inputLayoutMobile, null, inputEditMobile);
        if (phone.isEmpty() || !isValidMobile(phone)) {
            String sMessage = getLabelFromDb("error_S105_nationalid", R.string.error_S105_nationalid);
            setCustomError(inputLayoutMobile, sMessage, inputEditMobile);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutMobile, inputEditMobile);
            return true;
        }
    }
  public boolean validateNationalId(EditText inputEditMobile, TextInputLayout inputLayoutNational_id) {
        String phone = inputEditMobile.getText().toString().trim();
        setCustomError(inputLayoutNational_id, null, inputEditMobile);
        if (phone.isEmpty() || !isValidMobile(phone)) {
            String sMessage = getLabelFromDb("error_S105_nationalid", R.string.error_S105_nationalid);
            setCustomError(inputLayoutNational_id, sMessage, inputEditMobile);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutNational_id, inputEditMobile);
            return true;
        }
    }
    public boolean validateRegNo(EditText inputEditMobile, TextInputLayout inputLayoutsRegNo) {
        String phone = inputEditMobile.getText().toString().trim();
        setCustomError(inputLayoutsRegNo, null, inputEditMobile);
        if (phone.isEmpty() || !isValidMobile(phone)) {
            String sMessage = getLabelFromDb("error_S105_sregno", R.string.error_S105_sregno);
            setCustomError(inputLayoutsRegNo, sMessage, inputEditMobile);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutsRegNo, inputEditMobile);
            return true;
        }
    }
    public boolean validateAlternativeId(EditText inputEditMobile, TextInputLayout inputLayoutalternative_id) {
        String phone = inputEditMobile.getText().toString().trim();
        setCustomError(inputLayoutalternative_id, null, inputEditMobile);
        if (phone.isEmpty() || !isValidMobile(phone)) {
            String sMessage = getLabelFromDb("error_S105_alternativeid", R.string.error_S105_alternativeid);
            setCustomError(inputLayoutalternative_id, sMessage, inputEditMobile);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutalternative_id, inputEditMobile);
            return true;
        }
    }
    public boolean validateFirstName(EditText inputUser, TextInputLayout inputLayoutUser) {
        String name = inputUser.getText().toString().trim();
        setCustomError(inputLayoutUser, null, inputUser);
        if (name.isEmpty() || !isValidName(name)) {
            String sMessage = getLabelFromDb("error_S105_name", R.string.error_S105_name);
            setCustomError(inputLayoutUser, sMessage, inputUser);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutUser, inputUser);
            return true;
        }
    }

    public boolean validateLastName(EditText inputUser, TextInputLayout inputLayoutUser) {
        String name = inputUser.getText().toString().trim();
        setCustomError(inputLayoutUser, null, inputUser);
        if (name.isEmpty() || !isValidLName(name)) {
            String sMessage = getLabelFromDb("error_S167_name", R.string.error_S167_name);
            setCustomError(inputLayoutUser, sMessage, inputUser);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutUser, inputUser);
            return true;
        }
    }

    private boolean validateEmail(EditText inputEmail, TextInputLayout inputLayoutEmail) {
        String email = inputEmail.getText().toString().trim();
        setCustomError(inputLayoutEmail, null, inputEmail);
        if (email.isEmpty() || !isValidEmail(email)) {
            String sMessage = getLabelFromDb("error_S105_email", R.string.error_S105_email);
            setCustomError(inputLayoutEmail, sMessage, inputEmail);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutEmail, inputEmail);
            return true;
        }
    }

    private boolean validateLearnerNo(EditText inputLearnerNo, TextInputLayout inputLayoutLearnerNo) {
        String LearnerNo = inputLearnerNo.getText().toString().trim();
        setCustomError(inputLayoutLearnerNo, null, inputLearnerNo);
        if (LearnerNo.isEmpty() || !isValidLName(LearnerNo)) {
            String sMessage = getLabelFromDb("error_S105_empty", R.string.error_S105_empty);
            setCustomError(inputLayoutLearnerNo, sMessage, inputLearnerNo);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutLearnerNo, inputLearnerNo);
            return true;
        }
    }


    private boolean validateLearnerId(EditText inputLearnerId, TextInputLayout inputLayoutLearnerId) {
        String LearnerId = inputLearnerId.getText().toString().trim();
        setCustomError(inputLayoutLearnerId, null, inputLearnerId);
        if (LearnerId.isEmpty() || !isValidLName(LearnerId)) {
            String sMessage = getLabelFromDb("error_S105_empty", R.string.error_S105_empty);
            setCustomError(inputLayoutLearnerId, sMessage, inputLearnerId);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutLearnerId, inputLearnerId);
            return true;
        }
    }

    private boolean validateTaxRefNo(EditText inputTaxRefNo, TextInputLayout inputLayoutTaxRefNo) {

        String TaxRefNo = inputTaxRefNo.getText().toString().trim();
        setCustomError(inputLayoutTaxRefNo, null, inputTaxRefNo);
        if (TaxRefNo.isEmpty() || !isValidLName(TaxRefNo)) {
            String sMessage = getLabelFromDb("error_S105_empty", R.string.error_S105_empty);
            setCustomError(inputLayoutTaxRefNo, sMessage, inputTaxRefNo);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutTaxRefNo, inputTaxRefNo);
            return true;
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

        @SuppressLint("NonConstantResourceId")
        public void afterTextChanged(Editable editable) {
            switch (EditView.getId()) {
                case R.id.inputFirstName:
                    validateFirstName(EditView, EditLayout);
                    break;
                case R.id.inputLastName:
                    validateLastName(EditView, EditLayout);
                    break;
                case R.id.inputMobile:
                    validateNumber(EditView, EditLayout);
                    break;
                case R.id.inputNational_id:
                    validateNationalId(EditView,EditLayout);
                    break;
                    case R.id.inputsRegNo:
                    validateRegNo(EditView,EditLayout);
                    break;
                    case R.id.inputalternative_id:
                    validateAlternativeId(EditView,EditLayout);
                    break;
            }
        }
    }

    private void fetchData() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_105_A;
        FINAL_URL = FINAL_URL + "?token=" + token;
        printLogs(LogTag, "fetchData", "URL : " + FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchData", "RESPONSE : " + response);
                    String Status = jsonObject.getString(KEY_STATUS);
                    if (Status.equals("1")) {

                        JSONArray jsonArray = jsonObject.getJSONArray(KEY_DATA);
                        for (int i = 0; i <jsonArray.length() ; i++) {
                            JSONObject dataM = jsonArray.getJSONObject(i);

                            inputFirstName.setText(dataM.getString("fname"));
                            inputLastName.setText(dataM.getString("surname"));
                            inputMobile.setText(dataM.getString("cell"));
                            inputEmail.setText(dataM.getString("email"));
                            inputNational_id.setText(dataM.getString("s_id_no"));
                            inputsRegNo.setText(dataM.getString("s_s_no"));
                            inputTaxRefNo.setText(dataM.getString("s_s_tax_ref_no"));
                            inputalternative_id.setText(dataM.getString("s_c_o_alternative_id"));


                            rb_genderValue =  dataM.getString("gender");
                            spin_day =  dataM.getString("day");
                            spin_month =  dataM.getString("month");
                            spin_years =  dataM.getString("year");
                            spin_race =  dataM.getString("race");
                            spin_title =  dataM.getString("u_title");
                            spin_nationality =  dataM.getString("s_nationality");


                        }

                            //SpinnerDay, SpinnerMonth, SpinnerYear
                            //SET DOB Spinner data--PENDING..!!
                        if(!spin_day.equals("")){
                            int dday = Integer.parseInt(spin_day);
                            int new_day = dday-1;
                            String ryt_date = String.valueOf(new_day);
                            SpinnerDay.setSelection(Integer.parseInt(ryt_date));
                        }
                        if(!spin_month.equals("")){
                            SpinnerMonth.setSelection(Integer.parseInt(spin_month)-1);
                        }
                         String mYear = spin_years; //the value you want the position for
                        ArrayAdapter mAdpt = (ArrayAdapter) SpinnerYear.getAdapter(); //cast to an ArrayAdapter
                        int spinnerPosition = mAdpt.getPosition(mYear);
                        //set the default according to value
                        SpinnerYear.setSelection(spinnerPosition);

                        //spinner item..
                        inputSpinnerRace.setSelection(Integer.parseInt(spin_race));
                        //inputEnrollmentYear.setSelection(Integer.parseInt(spin_enroll));
                        inputSpinnerTitle.setSelection(Integer.parseInt(spin_title));
                        inputSpinnerNationality.setSelection(Integer.parseInt(spin_nationality));

                      /*  //spinner disability
                        SpinnerDisabilityType.setSelection(Integer.parseInt(spin_disability));

                        //spinner enrollment year
                        int enroll_id=0;
                        for (int i = 0; i <datalist.size() ; i++) {
                             if(datalist.get(i).getName().equals(spin_enroll)){
                                enroll_id = i;
                                printLogs(LogTag, "fetchData", "enroll_id : " + enroll_id);
                            }
                        }
                        Spin_EnrollmentYear.setSelection(enroll_id);*/
                        //CONDITION FOR GENDER MALE FEMALE
                        String Rbtn =  rb_genderValue;
                        if( Rbtn.equals("3")){
                            rb_male.setChecked(true);
                        }else{
                            rb_female.setChecked(true);
                        }
                        //CONDITION FOR DISABILITY
                        /*String DisBtn =  rb_disableValue;
                        if( DisBtn.equals("1")){
                            rb_disable_y.setChecked(true);
                            ll_DisabilityType.setVisibility(View.VISIBLE);
                        }else{
                            rb_disable_n.setChecked(true);
                            ll_DisabilityType.setVisibility(View.GONE);
                        }*/

                    } else if (Status.equals("2")) {
                        showProgress(false, mContentView, mProgressView);
                    } else {
                        //showProgress(false,mContentView,mProgressView);
                        printLogs(LogTag, "fetchData", "ERROR_S105_101 : DATA_ERROR");
                        String sTitle = "Error :" + ActivityId + "-101";
                        String sMessage = getLabelFromDb("error_S105_101", R.string.error_S105_101);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
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

    private void fetchEnrollment() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_105_2;
        FINAL_URL = FINAL_URL + "?token=" + token;//+"/seta_id/"+selected_Bank;
        printLogs(LogTag, "fetchEnrollment", "URL : " + FINAL_URL);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject jsonObject;
                printLogs(LogTag, "fetchEnrollment", String.format("RESPONSE : %s", response));
                try {
                    jsonObject = new JSONObject(String.valueOf(response));
                    String Status = jsonObject.getString(KEY_STATUS);
                    if (Status.equals("1")) {

                        JSONArray dataM = jsonObject.getJSONArray(KEY_DATA);
                        for (int i = 0; i < dataM.length(); i++) {
                            JSONObject rec = dataM.getJSONObject(i);
                          /*  String seta_name = rec.getString("grant_name");
                            SetaName.add(seta_name);
                              */
                            ListarClientes GetDatadp = new ListarClientes();
                            /* String seta_name = rec.getString("grant_name");
                             SetaName.add(seta_name);*/
                            GetDatadp.setName(rec.getString("year"));
                            GetDatadp.setId(rec.getString("id"));
                            datalist.add(GetDatadp);
                            showProgress(false, mContentView, mProgressView);
                        }
                        //  inputSpinnerGrant.setAdapter(new ArrayAdapter<String>(GAProcessStipendClaimA.this, android.R.layout.simple_spinner_dropdown_item, SetaName));
                        SpinnerAdapter adapter = new SpinnerAdapter(SEditProfileStepOneA.this, android.R.layout.simple_spinner_dropdown_item, datalist);
                        Spin_EnrollmentYear.setAdapter(adapter);
                    } else if (Status.equals("2")) {
                        showProgress(false, mContentView, mProgressView);
                    } else {
                        //showProgress(false,mContentView,mProgressView);
                        printLogs(LogTag, "fetchData", "error_try_again : DATA_ERROR");
                        String sTitle = "Error :" + ActivityId + "-109";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    printLogs(LogTag, "fetchData", "error_try_again : " + e.getMessage());
                    e.printStackTrace();
                    //showProgress(false,mContentView,mProgressView);
                    String sTitle = "Error :" + ActivityId + "-110";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                printLogs(LogTag, "fetchData", "error_try_again: " + error.getMessage());
                String sTitle = "Error :" + ActivityId + "-111";
                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put("Content-Type", "application/json; charset=utf-8");
                header.put("Accept", "*/*");
                return header;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    public void FormSubmit() {
        final int race_id = Integer.parseInt(String.valueOf(race_value));
        final int title = Integer.parseInt(String.valueOf(title_value));

       /* if(rb_disable_y.isChecked()){
            disability_key_status = rb_disable_y.getTag().toString();
        }else if(rb_disable_n.isChecked()){
            disability_key_status = rb_disable_n.getTag().toString();
        }
        final int disability_type = disability_value;*/
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

/*       // final String email = inputEmail.getText().toString().trim();
        final String learner_no = inputLearnerNo.getText().toString().trim();
        final String learner_id = inputLearnerId.getText().toString().trim();
        final String kin_name = inputNameOfKin.getText().toString().trim();
        final String kin_con = inputContactOfKin.getText().toString().trim();
        final String intern_uot = inputInternUTO.getText().toString().trim();
        //final String intern_quali = inputInternCategoryQualification.getText().toString().trim();*/
        final String tax_ref = inputTaxRefNo.getText().toString().trim();
        final String name = inputFirstName.getText().toString().trim();
        final String mobile = inputMobile.getText().toString().trim();
        final String national_id = inputNational_id.getText().toString().trim();
        final String sRegNo = inputsRegNo.getText().toString().trim();
        final String alternativeId = inputalternative_id.getText().toString().trim();
        final String sur_name = inputLastName.getText().toString().trim();

        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_105_3;
        printLogs(LogTag, "FormSubmit", "URL : " + FINAL_URL);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put(KEY_NAME, name);
            jsonBody.put(KEY_PHONE, mobile);
            jsonBody.put(KEY_SURNAME, sur_name);
            jsonBody.put("national_id", national_id);
            jsonBody.put("sRegNo", sRegNo);
            jsonBody.put("alternative_id", alternativeId);
           jsonBody.put("nationality", "2");
           // jsonBody.put(KEY_KIN_CONTACT, kin_con);
           // jsonBody.put(KEY_INTERN_UOT, intern_uot);
          //  jsonBody.put(KEY_INTERN_QUALIFICATION, qualCategoryType_value);
            jsonBody.put(KEY_TAX_REF, tax_ref);
           // jsonBody.put(KEY_STATUS_DISABILITY, disability_key_status);
            jsonBody.put(KEY_STATUS_GENDER, gender_key_status);
          //  jsonBody.put(KEY_STATUS_DISABILITY_TYPE, String.valueOf(disability_type));
            jsonBody.put(KEY_STATUS_RACE, String.valueOf(race_id));
            jsonBody.put(KEY_STATUS_TITLE, String.valueOf(title));
          //  jsonBody.put(KEY_STATUS_ENROLL, String.valueOf(enroll_year));
            jsonBody.put(KEY_STATUS_MONTH, String.valueOf(month));
            jsonBody.put(KEY_STATUS_DATE, String.valueOf(date));
            jsonBody.put(KEY_STATUS_YEAR, String.valueOf(year));
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

    public void customRedirector() {
        Intent intent = new Intent(SEditProfileStepOneA.this, SEditProfileMainA.class);
        printLogs(LogTag, "onOptionsItemSelected", "SFeedbackDA");
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        printLogs(LogTag, "onOptionsItemSelected", "init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(SEditProfileStepOneA.this, SEditProfileMainA.class);
            printLogs(LogTag, "onOptionsItemSelected", "sDashboard");
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