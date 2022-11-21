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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.adapter.SpinAdapter;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dataObj.SpinnerObj;
import za.co.sacpo.grant.xCubeLib.dataObj.Step1DataVisibilityObj;
import za.co.sacpo.grant.xCubeLib.dataObj.Step2DataVisibilityObj;
import za.co.sacpo.grant.xCubeLib.dataObj.Step3DataVisibilityObj;
import za.co.sacpo.grant.xCubeLib.dataObj.Step4DataVisibilityObj;
import za.co.sacpo.grant.xCubeLib.dataObj.Step5DataVisibilityObj;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsGrantSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeStudent.SDashboardDA;

public class SEditProfileMainA extends BaseFormAPCPrivate {

    private String ActivityId = "S105";
    public View mProgressView, mContentView,heading;
    private TextView lblView;
    SEditProfileMainA thisClass;
    LinearLayout step1,step2,step3,step4,step5;
    ArrayList<Step1DataVisibilityObj> Step1ArrayList = new ArrayList<>();
    ArrayList<Step2DataVisibilityObj> Step2ArrayList = new ArrayList<>();
    ArrayList<Step3DataVisibilityObj> Step3ArrayList = new ArrayList<>();
    ArrayList<Step4DataVisibilityObj> Step4ArrayList = new ArrayList<>();
    ArrayList<Step5DataVisibilityObj> Step5ArrayList = new ArrayList<>();
    ArrayList<String> object = new ArrayList<String>();
    JSONObject title,f_name,person_last_name,cell_phone_number,birth_date,
            email,race,tax_ref_number,national_id,ss_number,nationality,gender,alternative_id;


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
            fetchData();
            callDataApi();
            initializeListeners();
            printLogs(LogTag, "bootStrapInit", "exitConnected");
            //showProgress(false, mContentView, mProgressView);
        }
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag, "initializeViews", "init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        heading = findViewById(R.id.heading);
        step1 = findViewById(R.id.step1);
        step2 = findViewById(R.id.step2);
        step3 = findViewById(R.id.step3);
        step4 = findViewById(R.id.step4);
        step5 = findViewById(R.id.step5);
    }

    @Override
    protected void initializeListeners() {
        step1.setOnClickListener(view -> {
            Intent intent = new Intent(SEditProfileMainA.this, SEditProfileStepOneA.class);
            Bundle args = new Bundle();
            args.putSerializable("Step1ArrayList",(Serializable)Step1ArrayList);
            intent.putExtra("BUNDLE",args);
            startActivity(intent);
           // startActivity(new Intent(SEditProfileMainA.this,SEditProfileStepOneA.class));
        });
        step2.setOnClickListener(view -> {
            Intent intent = new Intent(SEditProfileMainA.this, SEditProfileStepTwoA.class);
            Bundle args = new Bundle();
            args.putSerializable("Step2ArrayList",(Serializable)Step2ArrayList);
            intent.putExtra("BUNDLE",args);
            startActivity(intent);
        });
        step3.setOnClickListener(view -> {
            Intent intent = new Intent(SEditProfileMainA.this, SEditProfileStepTwoBA.class);
            Bundle args = new Bundle();
            args.putSerializable("Step3ArrayList",(Serializable)Step3ArrayList);
            intent.putExtra("BUNDLE",args);
            startActivity(intent);
        });
        step4.setOnClickListener(view -> {
            Intent intent = new Intent(SEditProfileMainA.this, SEDitProfileStepThreeA.class);
            Bundle args = new Bundle();
            args.putSerializable("Step4ArrayList",(Serializable)Step4ArrayList);
            intent.putExtra("BUNDLE",args);
            startActivity(intent);
        });
        step5.setOnClickListener(view -> {
            Intent intent = new Intent(SEditProfileMainA.this, SEditProfileStepFourA.class);
            Bundle args = new Bundle();
            args.putSerializable("Step5ArrayList",(Serializable)Step5ArrayList);
            intent.putExtra("BUNDLE",args);
            startActivity(intent);
        });
    }

    @Override
    protected void initializeInputs() {


    }

    @Override
    protected void initializeLabels() {


      String  Label = getLabelFromDb("h_505", R.string.h_505);
        lblView = (TextView) findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
        }
    }

    @Override
    protected void setLayoutXml() {
        printLogs(LogTag, "setLayoutXml", "edit_profile_main");
        setContentView(R.layout.a_edit_profile_main);
    }



    private void fetchData() {
        showProgress(true, mContentView, mProgressView);
        String token = userSessionObj.getToken();
        grantSessionObj = new OlumsGrantSession(SEditProfileMainA.this);
        String grant_id = grantSessionObj.getGrantId();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.STU_FIELDS_PERMISSION;
        FINAL_URL=FINAL_URL+"?token="+token+"&grant_id="+grant_id;
        printLogs(LogTag,"fetchData","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchData", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        for (int i = 0; i <dataM.length() ; i++) {

                            /*Step 1*/
                            title =          dataM.getJSONObject(i).getJSONObject("title");
                            f_name =            dataM.getJSONObject(i).getJSONObject("person_first_name");
                            person_last_name = dataM.getJSONObject(i).getJSONObject("person_last_name");
                             cell_phone_number = dataM.getJSONObject(i).getJSONObject("cell_phone_number");
                             birth_date = dataM.getJSONObject(i).getJSONObject("birth_date");
                             email = dataM.getJSONObject(i).getJSONObject("email");
                             race = dataM.getJSONObject(i).getJSONObject("race");
                             tax_ref_number = dataM.getJSONObject(i).getJSONObject("tax_ref_number");
                           national_id = dataM.getJSONObject(i).getJSONObject("national_id");
                            ss_number = dataM.getJSONObject(i).getJSONObject("ss_number");
                             nationality = dataM.getJSONObject(i).getJSONObject("nationality");
                             gender = dataM.getJSONObject(i).getJSONObject("gender");
                             alternative_id = dataM.getJSONObject(i).getJSONObject("alternative_id");
                            /*Step 2*/
                            Step1ArrayList.add(new Step1DataVisibilityObj(title.getString("s_d_f_is_visible"),title.getString("s_d_f_is_compulsory"), f_name.getString("s_d_f_is_visible"),f_name.getString("s_d_f_is_compulsory"),
                                    person_last_name.getString("s_d_f_is_visible"),person_last_name.getString("s_d_f_is_compulsory"),cell_phone_number.getString("s_d_f_is_visible"),cell_phone_number.getString("s_d_f_is_compulsory"),
                                    email.getString("s_d_f_is_visible"),email.getString("s_d_f_is_compulsory"),race.getString("s_d_f_is_visible"),race.getString("s_d_f_is_compulsory"),
                                    gender.getString("s_d_f_is_visible"),gender.getString("s_d_f_is_compulsory"),birth_date.getString("s_d_f_is_visible"),birth_date.getString("s_d_f_is_compulsory"),
                                    national_id.getString("s_d_f_is_visible"),national_id.getString("s_d_f_is_compulsory"),ss_number.getString("s_d_f_is_visible"),
                                    ss_number.getString("s_d_f_is_compulsory"),alternative_id.getString("s_d_f_is_visible"),alternative_id.getString("s_d_f_is_compulsory"),
                                    nationality.getString("s_d_f_is_visible"),nationality.getString("s_d_f_is_compulsory"),tax_ref_number.getString("s_d_f_is_visible"),tax_ref_number.getString("s_d_f_is_compulsory")));

                    /*Step 2*/
                           JSONObject home_language =          dataM.getJSONObject(i).getJSONObject("home_language");
                           JSONObject telephone =          dataM.getJSONObject(i).getJSONObject("telephone");
                           JSONObject fax =          dataM.getJSONObject(i).getJSONObject("fax");
                           JSONObject physical_code =          dataM.getJSONObject(i).getJSONObject("physical_code");
                           JSONObject physical_address_1 =dataM.getJSONObject(i).getJSONObject("physical_address_1");
                           JSONObject physical_address_2 =dataM.getJSONObject(i).getJSONObject("physical_address_2");
                           JSONObject physical_address__3 =dataM.getJSONObject(i).getJSONObject("physical_address__3");
                           JSONObject physical_municipality =dataM.getJSONObject(i).getJSONObject("physical_municipality");
                           JSONObject physical_urban_rural =dataM.getJSONObject(i).getJSONObject("physical_urban_rural");
                           JSONObject country =dataM.getJSONObject(i).getJSONObject("country");
                           JSONObject physical_province =dataM.getJSONObject(i).getJSONObject("physical_province");
                           JSONObject physical_city =dataM.getJSONObject(i).getJSONObject("physical_city");
                           JSONObject physical_suburb =dataM.getJSONObject(i).getJSONObject("physical_suburb");
                           JSONObject province =dataM.getJSONObject(i).getJSONObject("province");
                           JSONObject city =dataM.getJSONObject(i).getJSONObject("city");
                           JSONObject suburb =dataM.getJSONObject(i).getJSONObject("suburb");

                            Step2ArrayList.add(new Step2DataVisibilityObj(home_language.getString("s_d_f_is_visible"),home_language.getString("s_d_f_is_compulsory"), telephone.getString("s_d_f_is_visible"),telephone.getString("s_d_f_is_compulsory"),
                                    fax.getString("s_d_f_is_visible"),fax.getString("s_d_f_is_compulsory"),physical_code.getString("s_d_f_is_visible"),physical_code.getString("s_d_f_is_compulsory"),
                                    physical_address_1.getString("s_d_f_is_visible"),physical_address_1.getString("s_d_f_is_compulsory"),physical_address_2.getString("s_d_f_is_visible"),physical_address_2.getString("s_d_f_is_compulsory"),
                                    physical_address__3.getString("s_d_f_is_visible"),physical_address__3.getString("s_d_f_is_compulsory"),physical_municipality.getString("s_d_f_is_visible"),physical_municipality.getString("s_d_f_is_compulsory"),
                                    physical_urban_rural.getString("s_d_f_is_visible"),physical_urban_rural.getString("s_d_f_is_compulsory"),country.getString("s_d_f_is_visible"),country.getString("s_d_f_is_compulsory"),
                                    physical_province.getString("s_d_f_is_visible"),physical_province.getString("s_d_f_is_compulsory"),physical_city.getString("s_d_f_is_visible"),physical_city.getString("s_d_f_is_compulsory"),
                                    physical_suburb.getString("s_d_f_is_visible"),physical_suburb.getString("s_d_f_is_compulsory"),province.getString("s_d_f_is_visible"),province.getString("s_d_f_is_compulsory"),
                                    city.getString("s_d_f_is_visible"),city.getString("s_d_f_is_compulsory"),suburb.getString("s_d_f_is_visible"),suburb.getString("s_d_f_is_compulsory")));

                            /*Step 3*/

                            JSONObject postal_code = dataM.getJSONObject(i).getJSONObject("postal_code");
                            JSONObject postal_address_line_1 = dataM.getJSONObject(i).getJSONObject("postal_address_line_1");
                            JSONObject postal_address_line_2 = dataM.getJSONObject(i).getJSONObject("postal_address_line_2");
                            JSONObject postal_address_line_3 = dataM.getJSONObject(i).getJSONObject("postal_address_line_3");
                            JSONObject postal_municipality = dataM.getJSONObject(i).getJSONObject("postal_municipality");
                            JSONObject postal_urban_rural = dataM.getJSONObject(i).getJSONObject("postal_urban_rural");
                            JSONObject postal_country = dataM.getJSONObject(i).getJSONObject("postal_country");
                            JSONObject postal_province = dataM.getJSONObject(i).getJSONObject("postal_province");
                            JSONObject postal_city = dataM.getJSONObject(i).getJSONObject("postal_city");
                            JSONObject postal_suburb = dataM.getJSONObject(i).getJSONObject("postal_suburb");
                            JSONObject last_school_emis = dataM.getJSONObject(i).getJSONObject("last_school_emis");
                            JSONObject last_school_year = dataM.getJSONObject(i).getJSONObject("last_school_year");
                            JSONObject equity = dataM.getJSONObject(i).getJSONObject("equity");
                            JSONObject citizen_resident_status = dataM.getJSONObject(i).getJSONObject("citizen_resident_status");


                            Step3ArrayList.add(new Step3DataVisibilityObj(postal_code.getString("s_d_f_is_visible"),postal_code.getString("s_d_f_is_compulsory"), postal_address_line_1.getString("s_d_f_is_visible"),postal_address_line_1.getString("s_d_f_is_compulsory"),
                                    postal_address_line_2.getString("s_d_f_is_visible"),postal_address_line_2.getString("s_d_f_is_compulsory"),postal_address_line_3.getString("s_d_f_is_visible"),postal_address_line_3.getString("s_d_f_is_compulsory"),
                                    postal_municipality.getString("s_d_f_is_visible"),postal_municipality.getString("s_d_f_is_compulsory"),postal_urban_rural.getString("s_d_f_is_visible"),postal_urban_rural.getString("s_d_f_is_compulsory"),
                                    postal_country.getString("s_d_f_is_visible"),postal_country.getString("s_d_f_is_compulsory"),postal_province.getString("s_d_f_is_visible"),postal_province.getString("s_d_f_is_compulsory"),
                                    postal_city.getString("s_d_f_is_visible"),postal_city.getString("s_d_f_is_compulsory"),postal_suburb.getString("s_d_f_is_visible"),postal_suburb.getString("s_d_f_is_compulsory"),"1","1","1","1","1","1",
                                    last_school_emis.getString("s_d_f_is_visible"),last_school_emis.getString("s_d_f_is_compulsory"),last_school_year.getString("s_d_f_is_visible"),last_school_year.getString("s_d_f_is_compulsory"),
                                    equity.getString("s_d_f_is_visible"),equity.getString("s_d_f_is_compulsory"),citizen_resident_status.getString("s_d_f_is_visible"),citizen_resident_status.getString("s_d_f_is_compulsory")));


                            /*Step 4*/
                            JSONObject statssa_area_code = dataM.getJSONObject(i).getJSONObject("statssa_area_code");
                            JSONObject popi_act_status = dataM.getJSONObject(i).getJSONObject("popi_act_status");
                            JSONObject popi_act_status_date = dataM.getJSONObject(i).getJSONObject("popi_act_status_date");
                            JSONObject ofo_code = dataM.getJSONObject(i).getJSONObject("ofo_code");
                            JSONObject sponsorship = dataM.getJSONObject(i).getJSONObject("sponsorship");
                            JSONObject financial_year = dataM.getJSONObject(i).getJSONObject("financial_year");
                            JSONObject nqf_level = dataM.getJSONObject(i).getJSONObject("nqf_level");
                            JSONObject qualification_saqa_id = dataM.getJSONObject(i).getJSONObject("qualification_saqa_id");
                            JSONObject employer_sdl_number = dataM.getJSONObject(i).getJSONObject("employer_sdl_number");
                            JSONObject provider_sdl_number = dataM.getJSONObject(i).getJSONObject("provider_sdl_number");
                            JSONObject socio_economic_status = dataM.getJSONObject(i).getJSONObject("socio_economic_status");
                            JSONObject wil_start_date = dataM.getJSONObject(i).getJSONObject("wil_start_date");
                            JSONObject wil_end_date = dataM.getJSONObject(i).getJSONObject("wil_end_date");
                            JSONObject most_recent_registration_date = dataM.getJSONObject(i).getJSONObject("most_recent_registration_date");
                            JSONObject enrolment_status = dataM.getJSONObject(i).getJSONObject("enrolment_status");
                            JSONObject project = dataM.getJSONObject(i).getJSONObject("project");
                            JSONObject reference_number = dataM.getJSONObject(i).getJSONObject("reference_number");
                            JSONObject wil_type = dataM.getJSONObject(i).getJSONObject("wil_type");


                            Step4ArrayList.add(new Step4DataVisibilityObj(statssa_area_code.getString("s_d_f_is_visible"),statssa_area_code.getString("s_d_f_is_compulsory"), popi_act_status.getString("s_d_f_is_visible"),popi_act_status.getString("s_d_f_is_compulsory"),
                                    popi_act_status_date.getString("s_d_f_is_visible"),popi_act_status_date.getString("s_d_f_is_compulsory"),ofo_code.getString("s_d_f_is_visible"),ofo_code.getString("s_d_f_is_compulsory"),
                                    sponsorship.getString("s_d_f_is_visible"),sponsorship.getString("s_d_f_is_compulsory"),financial_year.getString("s_d_f_is_visible"),financial_year.getString("s_d_f_is_compulsory"),
                                    nqf_level.getString("s_d_f_is_visible"),nqf_level.getString("s_d_f_is_compulsory"),qualification_saqa_id.getString("s_d_f_is_visible"),qualification_saqa_id.getString("s_d_f_is_compulsory"),
                                    employer_sdl_number.getString("s_d_f_is_visible"),employer_sdl_number.getString("s_d_f_is_compulsory"),provider_sdl_number.getString("s_d_f_is_visible"),provider_sdl_number.getString("s_d_f_is_compulsory"),
                                    socio_economic_status.getString("s_d_f_is_visible"),socio_economic_status.getString("s_d_f_is_compulsory"),wil_start_date.getString("s_d_f_is_visible"),wil_start_date.getString("s_d_f_is_compulsory"),
                                    wil_end_date.getString("s_d_f_is_visible"),wil_end_date.getString("s_d_f_is_compulsory"),most_recent_registration_date.getString("s_d_f_is_visible"),most_recent_registration_date.getString("s_d_f_is_compulsory"),
                                    enrolment_status.getString("s_d_f_is_visible"),enrolment_status.getString("s_d_f_is_compulsory"),project.getString("s_d_f_is_visible"),project.getString("s_d_f_is_compulsory"),
                                    reference_number.getString("s_d_f_is_visible"),reference_number.getString("s_d_f_is_compulsory"),wil_type.getString("s_d_f_is_visible"),wil_type.getString("s_d_f_is_compulsory")));


                            /*Step 5*/
                            JSONObject name_of_kin = dataM.getJSONObject(i).getJSONObject("name_of_kin");
                            JSONObject contact_of_kin = dataM.getJSONObject(i).getJSONObject("contact_of_kin");
                            JSONObject physically_disable = dataM.getJSONObject(i).getJSONObject("physically_disable");
                            JSONObject select_disability_type = dataM.getJSONObject(i).getJSONObject("select_disability_type");
                            JSONObject intern_category_qualification = dataM.getJSONObject(i).getJSONObject("intern_category_qualification");
                            JSONObject institution = dataM.getJSONObject(i).getJSONObject("institution");
                            JSONObject university = dataM.getJSONObject(i).getJSONObject("university");
                            JSONObject college = dataM.getJSONObject(i).getJSONObject("college");

                            Step5ArrayList.add(new Step5DataVisibilityObj(name_of_kin.getString("s_d_f_is_visible"),name_of_kin.getString("s_d_f_is_compulsory"),
                                    contact_of_kin.getString("s_d_f_is_visible"),contact_of_kin.getString("s_d_f_is_compulsory"),physically_disable.getString("s_d_f_is_visible"),physically_disable.getString("s_d_f_is_compulsory"),
                                    select_disability_type.getString("s_d_f_is_visible"),select_disability_type.getString("s_d_f_is_compulsory"),intern_category_qualification.getString("s_d_f_is_visible"),intern_category_qualification.getString("s_d_f_is_compulsory"),
                                    institution.getString("s_d_f_is_visible"),institution.getString("s_d_f_is_compulsory"),university.getString("s_d_f_is_visible"),university.getString("s_d_f_is_compulsory"),
                                    college.getString("s_d_f_is_visible"),college.getString("s_d_f_is_compulsory")));
                        }

                        showProgress(false, mContentView, mProgressView);
                    }

                    else if(Status.equals("2")) {
                        showProgress(false, mContentView, mProgressView);
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
            Intent intent = new Intent(SEditProfileMainA.this, AppUpdatedA.class);
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
        Intent intent = new Intent(SEditProfileMainA.this, SDashboardDA.class);
        printLogs(LogTag, "onOptionsItemSelected", "SFeedbackDA");
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        printLogs(LogTag, "onOptionsItemSelected", "init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(SEditProfileMainA.this, SDashboardDA.class);
            printLogs(LogTag, "onOptionsItemSelected", "sDashboard");
            startActivity(intent);
            finish();
        }
        return true;
    }
    @Override
    protected void validateForm() {

    }

    @Override
    protected void FormSubmit() {

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