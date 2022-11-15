package za.co.sacpo.grant.xCubeStudent.editprofile;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.util.HashMap;
import java.util.Map;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.adapter.SpinAdapter;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dataObj.SpinnerObj;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;

public class SEditProfileStepFourA extends BaseFormAPCPrivate {
    private String ActivityId = "S105D";
    public View mProgressView, mContentView,heading;
    private TextView lblView;
    SEditProfileStepFourA thisClass;
    public Button btnUpdate;
    EditText inputNameOfKin,inputContactOfKin;
    Spinner inputDisabilityType;
    Spinner inputSpinnerinstitution;
    Spinner inputSpinneruniversity;
    Spinner inputSpinnercollege,spinner_InternCategoryQualification;
    String institution_value,qualcategory_value,is_disablity,spin_college="",spin_university="",
            disability_key_status="",spin_disability,university_value,college_value;
    int disability_status,disability_value;
    public SpinnerObj[] StuQualcatType;
    public SpinnerObj[] UniversityType;
    public SpinnerObj[] CollegeType;
    LinearLayout ll_university,ll_college,ll_DisabilityType;
    RadioGroup RGPhysicalDisAbility;
    RadioButton rb_disable_y,rb_disable_n;


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
            fetchUniversity();
            fetchColleges();
            fetchData();
            callDataApi();
            initializeListeners();
            fetchQualCategoryType();
            printLogs(LogTag, "bootStrapInit", "exitConnected");
        }
    }

    private void fetchData() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_105_D;
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

                            inputNameOfKin.setText(dataM.getString("s_k_name"));
                            inputContactOfKin.setText(dataM.getString("s_k_home_tel"));

                            institution_value =  dataM.getString("s_s_g_student_institution_type");
                            university_value =  dataM.getString("s_s_g_student_institution");
                            college_value =  dataM.getString("s_s_g_student_institution");
                            qualcategory_value  =  dataM.getString("s_s_g_learner_qual_category");
                            is_disablity =  dataM.getString("s_is_physical_disable");
                            spin_disability =  dataM.getString("s_physical_disable_type");

                        }
                        if(!college_value.equals("")){
                            inputSpinnercollege.setSelection(Integer.parseInt(college_value));
                        }

                        if(!institution_value.equals("")){
                            inputSpinnerinstitution.setSelection(Integer.parseInt(institution_value));
                        }
                        if(!university_value.equals("")){
                            inputSpinneruniversity.setSelection(Integer.parseInt(university_value));
                        }
                        if(!qualcategory_value.equals("")){
                            spinner_InternCategoryQualification.setSelection(Integer.parseInt(qualcategory_value));
                        }
                        if(!spin_disability.equals("")){
                            inputDisabilityType.setSelection(Integer.parseInt(spin_disability));
                        }


                        //CONDITION FOR DISABILITY
                        if( is_disablity.equals("1")){
                            rb_disable_y.setChecked(true);
                            ll_DisabilityType.setVisibility(View.VISIBLE);
                        }else{
                            rb_disable_n.setChecked(true);
                            ll_DisabilityType.setVisibility(View.GONE);
                        }

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

    private void fetchColleges() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.COLLEGE_105D;
        FINAL_URL=FINAL_URL+"?token="+token;
        printLogs(LogTag,"fetchColleges","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchColleges", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        CollegeType = new SpinnerObj[dataM.length()];
                        for (int i = 0; i <dataM.length() ; i++) {
                            JSONObject jsonObject = dataM.getJSONObject(i);
                            CollegeType[i] = new SpinnerObj();
                            CollegeType[i].setId(jsonObject.getString("college_id"));
                            CollegeType[i].setName(jsonObject.getString("college_type"));

                        }
                        SpinAdapter adapter = new SpinAdapter(SEditProfileStepFourA.this, android.R.layout.simple_spinner_item, CollegeType);
                        inputSpinnercollege.setAdapter(adapter);
                        inputSpinnercollege.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                spin_college = adapter.getItem(i).getId();
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

    private void fetchUniversity() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.UNIVERSITY_105D;
        FINAL_URL=FINAL_URL+"?token="+token;
        printLogs(LogTag,"fetchUniversity","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchUniversity", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        UniversityType = new SpinnerObj[dataM.length()];
                        for (int i = 0; i <dataM.length() ; i++) {
                            JSONObject jsonObject = dataM.getJSONObject(i);
                            UniversityType[i] = new SpinnerObj();
                            UniversityType[i].setId(jsonObject.getString("university_id"));
                            UniversityType[i].setName(jsonObject.getString("university_type"));

                        }
                        SpinAdapter adapter = new SpinAdapter(SEditProfileStepFourA.this, android.R.layout.simple_spinner_item, UniversityType);
                        inputSpinneruniversity.setAdapter(adapter);
                        inputSpinneruniversity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                spin_university = adapter.getItem(i).getId();
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
    protected void initializeViews() {
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        heading = findViewById(R.id.heading);
        btnUpdate = findViewById(R.id.btnUpdate);
        inputNameOfKin = findViewById(R.id.inputNameOfKin);
        inputContactOfKin = findViewById(R.id.inputContactOfKin);
        inputDisabilityType = findViewById(R.id.inputDisabilityType);
        spinner_InternCategoryQualification = findViewById(R.id.spinner_InternCategoryQualification);
        inputSpinnerinstitution = findViewById(R.id.inputSpinnerinstitution);
        inputSpinneruniversity = findViewById(R.id.inputSpinneruniversity);
        inputSpinnercollege = findViewById(R.id.inputSpinnercollege);
        ll_university = findViewById(R.id.ll_university);
        ll_college = findViewById(R.id.ll_college);
        ll_DisabilityType = findViewById(R.id.ll_DisabilityType);
        RGPhysicalDisAbility = findViewById(R.id.RGPhysicalDisAbility);
        rb_disable_y = findViewById(R.id.rb_disable_y);
        rb_disable_n = findViewById(R.id.rb_disable_n);
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
        RGPhysicalDisAbility.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                disability_status = RGPhysicalDisAbility.getCheckedRadioButtonId();
                printLogs(LogTag, "initializeListeners", "disability_status : " + disability_status);

            }
        });
        rb_disable_y.setOnClickListener(new View.OnClickListener() {
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
        });



        /*institution spinner*/
        ArrayAdapter<CharSequence> institutionadapter = ArrayAdapter.createFromResource(this, R.array.institution_type, android.R.layout.simple_spinner_item);
        institutionadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputSpinnerinstitution.setAdapter(institutionadapter);
        institutionadapter.notifyDataSetChanged();

        inputSpinnerinstitution.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                institution_value = String.valueOf(parent.getSelectedItemId());
                if(institution_value.equals("1")){
                    ll_college.setVisibility(View.GONE);
                    ll_university.setVisibility(View.VISIBLE);
                }else if(institution_value.equals("2")){
                    ll_college.setVisibility(View.VISIBLE);
                    ll_university.setVisibility(View.GONE);
                }else{
                    ll_college.setVisibility(View.GONE);
                    ll_university.setVisibility(View.GONE);
                }
                printLogs(LogTag, "initializeListeners", "institution_value" + institution_value);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /*SpinnerDisabilityType*/
        final ArrayAdapter<CharSequence> adapter7 = ArrayAdapter.createFromResource(this, R.array.disability_type, android.R.layout.simple_spinner_item);
        adapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputDisabilityType.setAdapter(adapter7);
        adapter7.notifyDataSetChanged();


        inputDisabilityType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                disability_value = Math.toIntExact(parent.getSelectedItemId());
                printLogs(LogTag, "initializeListeners", "disability_value" + disability_value);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    protected void initializeInputs() {

    }

    @Override
    protected void initializeLabels() {
        printLogs(LogTag, "initializeLabels", "init");

        String Label = getLabelFromDb("lbl_S105D_nameOfKin", R.string.lbl_S105D_nameOfKin);
        lblView = (TextView) findViewById(R.id.lblNameOfKin);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S105D_contactOfKin", R.string.lbl_S105D_contactOfKin);
        lblView = (TextView) findViewById(R.id.lblContactOfKin);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105D_PhysicalDis", R.string.lbl_S105D_PhysicalDis);
        lblView = (TextView) findViewById(R.id.lblPhysicalDis);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105D_DisabilityType", R.string.lbl_S105D_DisabilityType);
        lblView = (TextView) findViewById(R.id.lblDisabilityType);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105D_Interncatquali", R.string.lbl_S105D_Interncatquali);
        lblView = (TextView) findViewById(R.id.lblInternCategoryQualification);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105D_institution", R.string.lbl_S105D_institution);
        lblView = (TextView) findViewById(R.id.lblinstitution);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105D_university", R.string.lbl_S105D_university);
        lblView = (TextView) findViewById(R.id.lbluniversity);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105D_college", R.string.lbl_S105D_college);
        lblView = (TextView) findViewById(R.id.lblcollege);
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
            inputNameOfKin.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputContactOfKin.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputDisabilityType.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            spinner_InternCategoryQualification.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            inputSpinnerinstitution.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            inputSpinneruniversity.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            inputSpinnercollege.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
        }


    }

    @Override
    protected void setLayoutXml() {
        printLogs(LogTag, "setLayoutXml", "edit_profile_stepthree");
        setContentView(R.layout.a_edit_profile_step_four);
    }
    private void fetchQualCategoryType() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.Qual_Category_Type;
        FINAL_URL=FINAL_URL+"/"+token;
        printLogs(LogTag,"fetchQualCategoryTypeSpinner","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchQualCategoryTypeSpinner", "response : " + response);
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
                        SpinAdapter adapter = new SpinAdapter(SEditProfileStepFourA.this, android.R.layout.simple_spinner_item, StuQualcatType);
                        spinner_InternCategoryQualification.setAdapter(adapter);
                        spinner_InternCategoryQualification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                qualcategory_value = adapter.getItem(i).getId();
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
    protected void verifyVersion() {
        printLogs(LogTag, "verifyVersion", "init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag, "verifyVersion", "isUpdate " + isUpdate);
        printLogs(LogTag, "verifyVersion", "isUpdate " + utilSessionObj.getIsUpdateRequired());
        if (isUpdate) {
            Intent intent = new Intent(SEditProfileStepFourA.this, AppUpdatedA.class);
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
        Intent intent = new Intent(SEditProfileStepFourA.this, SEditProfileMainA.class);
        printLogs(LogTag, "onOptionsItemSelected", "SEditProfileStepTwoA");
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        printLogs(LogTag, "onOptionsItemSelected", "init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(SEditProfileStepFourA.this, SEditProfileMainA.class);
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
        final String NameOfKin = inputNameOfKin.getText().toString().trim();
        final String ContactOfKin = inputContactOfKin.getText().toString().trim();
        if(rb_disable_y.isChecked()){
            disability_key_status = rb_disable_y.getTag().toString();
        }else if(rb_disable_n.isChecked()){
            disability_key_status = rb_disable_n.getTag().toString();
        }
        final int disability_type = disability_value;
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.UPDATEDETAILS_105_D;
        printLogs(LogTag, "FormSubmit", "URL : " + FINAL_URL);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put("kin_name", NameOfKin);
            jsonBody.put("kin_contact", ContactOfKin);
            jsonBody.put("is_physically_disable", disability_key_status);
            jsonBody.put("disability_type", disability_type);
            jsonBody.put("intern_cat_quali", qualcategory_value);
            jsonBody.put("institution", institution_value);
            jsonBody.put("university", spin_university);
            jsonBody.put("college", spin_college);


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