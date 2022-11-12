package za.co.sacpo.grant.xCubeStudent.editprofile;

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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.material.textfield.TextInputLayout;

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

public class SEditProfileStepTwoA extends BaseFormAPCPrivate {
    private String ActivityId = "S105B";
    public View mProgressView, mContentView,heading;
    private TextView lblView;
    SEditProfileStepTwoA thisClass;
    String phyurbanrural_value,postalurbanrural_value,equity_value,residentstatus_value,spin_homelanguage,spin_day,spin_month,spin_year,spin_disability;
    public EditText inputtelephone,inputfax,inputphysicalcode, inputphyaddress1, inputphyaddress2, inputphyaddress3,
            inputphyprovince, inputphycity, inputphysuburub, inputpostalcode, inputpostaladdress1,
            inputpostaladdress2,inputpostaladdress3,inputetpostalprovince,inputetpostalcity,inputetpostalsuburub,inputlastscyear;
    public TextInputLayout inputLayouttelephone, inputLayoutfax, inputLayoutphysicalcode,inputLayoutphyaddress1,
            inputLayoutphyaddress2, inputLayoutphyaddress3, inputLayoutmunicipality,inputLayoutphyprovince,
            inputLayoutphycity, inputLayoutphysuburub, inputLayoutpostalcode, inputLayoutpostaladdress1,
            inputLayoutpostaladdress2,inputLayoutpostaladdress3,inputLayoutpostalmunicipality,inputLayoutetpostalprovince
            ,inputLayoutetpostalcity,inputLayoutetpostalsuburub,inputLayoutschoolemis,inputLayoutlastscyear;
    public Button btnUpdate;
    private Spinner inputSpinnerurbanrural, inputSpinnerpostalurbanrural, inputSpinnerequity, inputSpinnerresidentstatus, inputSpinnerhomelanguage, SpinnerDisabilityType, Spin_EnrollmentYear,spinner_InternCategoryQualification;

    AutoCompleteTextView txtmunicipality,txtpostalmunicipality,txtschoolemis;
    String[] countries={"India","Australia","West indies","indonesia","Indiana",
            "South Africa","England","Bangladesh","Srilanka","singapore","iscbsjdkbs","insfwefwef","inwdawefawf","inDWEFEF","INWDFEF","insfefafgr"};

String spin_urbanrural,spin_country,spin_province,spin_city,spin_suburb;
String spin_postalurbanrural,spin_postalcountry,spin_postalprovince,spin_postalcity,spin_postalsuburb
        ,spin_equity,spin_residentstatus;

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
            fetchData();
            callDataApi();
            initializeListeners();
            //  fetchQualCategoryType();
            printLogs(LogTag, "bootStrapInit", "exitConnected");
            //showProgress(false, mContentView, mProgressView);
        }
    }

    @Override
    protected void setLayoutXml() {
        printLogs(LogTag, "setLayoutXml", "edit_profile_steptwo");
        setContentView(R.layout.a_edit_profile_step_two);
    }

    @Override
    protected void initializeViews() {
        printLogs(LogTag, "initializeViews", "init");

        txtmunicipality = findViewById(R.id.txtmunicipality);
        txtpostalmunicipality = findViewById(R.id.txtpostalmunicipality);
        txtschoolemis = findViewById(R.id.txtschoolemis);

        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        heading = findViewById(R.id.heading);
        btnUpdate = findViewById(R.id.btnUpdate);

        /*inputlayout*/
        inputLayouttelephone = findViewById(R.id.inputLayouttelephone);
        inputLayoutfax = findViewById(R.id.inputLayoutfax);
        inputLayoutphysicalcode = findViewById(R.id.inputLayoutphysicalcode);
        inputLayoutphyaddress1 = findViewById(R.id.inputLayoutphyaddress1);
        inputLayoutphyaddress2 = findViewById(R.id.inputLayoutphyaddress2);
        inputLayoutphyaddress3 = findViewById(R.id.inputLayoutphyaddress3);
        inputLayoutmunicipality = findViewById(R.id.inputLayoutmunicipality);
        inputLayoutphyprovince = findViewById(R.id.inputLayoutphyprovince);
        inputLayoutphycity = findViewById(R.id.inputLayoutphycity);
        inputLayoutphysuburub = findViewById(R.id.inputLayoutphysuburub);
        inputLayoutpostalcode = findViewById(R.id.inputLayoutpostalcode);
        inputLayoutpostaladdress1 = findViewById(R.id.inputLayoutpostaladdress1);
        inputLayoutpostaladdress2 = findViewById(R.id.inputLayoutpostaladdress2);
        inputLayoutpostaladdress3 = findViewById(R.id.inputLayoutpostaladdress3);
        inputLayoutpostalmunicipality = findViewById(R.id.inputLayoutpostalmunicipality);
        inputLayoutetpostalprovince = findViewById(R.id.inputLayoutetpostalprovince);
        inputLayoutetpostalcity = findViewById(R.id.inputLayoutetpostalcity);
        inputLayoutetpostalsuburub = findViewById(R.id.inputLayoutetpostalsuburub);
        inputLayoutschoolemis = findViewById(R.id.inputLayoutschoolemis);
        inputLayoutlastscyear = findViewById(R.id.inputLayoutlastscyear);

        /*Spinners*/
        inputSpinnerurbanrural = findViewById(R.id.inputSpinnerurbanrural);
        inputSpinnerpostalurbanrural = findViewById(R.id.inputSpinnerpostalurbanrural);
        inputSpinnerequity = findViewById(R.id.inputSpinnerequity);
        inputSpinnerresidentstatus = findViewById(R.id.inputSpinnerresidentstatus);
        inputSpinnerhomelanguage = findViewById(R.id.inputSpinnerhomelanguage);

        /*Edittext*/
        inputtelephone = findViewById(R.id.inputtelephone);
        inputfax = findViewById(R.id.inputfax);
        inputphysicalcode = findViewById(R.id.inputphysicalcode);
        inputphyaddress1 = findViewById(R.id.inputphyaddress1);
        inputphyaddress2 = findViewById(R.id.inputphyaddress2);
        inputphyaddress3 = findViewById(R.id.inputphyaddress3);
        inputphyprovince = findViewById(R.id.inputphyprovince);
        inputphycity = findViewById(R.id.inputphycity);
        inputphysuburub = findViewById(R.id.inputphysuburub);

        /*Postal edittext*/
        inputpostalcode = findViewById(R.id.inputpostalcode);
        inputpostaladdress1 = findViewById(R.id.inputpostaladdress1);
        inputpostaladdress2 = findViewById(R.id.inputpostaladdress2);
        inputpostaladdress3 = findViewById(R.id.inputpostaladdress3);
        inputetpostalprovince = findViewById(R.id.inputetpostalprovince);
        inputetpostalcity = findViewById(R.id.inputetpostalcity);
        inputetpostalsuburub = findViewById(R.id.inputetpostalsuburub);
        inputlastscyear = findViewById(R.id.inputlastscyear);

    }

    @Override
    protected void initializeListeners() {

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });




        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,countries);
        txtmunicipality.setThreshold(2);
        txtmunicipality.setAdapter(adapter);

        ArrayAdapter<String> txtpostalmunicipalityadapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,countries);
        txtpostalmunicipality.setThreshold(2);
        txtpostalmunicipality.setAdapter(txtpostalmunicipalityadapter);

        ArrayAdapter<String> txtschoolemisadapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,countries);
        txtschoolemis.setThreshold(2);
        txtschoolemis.setAdapter(txtschoolemisadapter);



        /*Physical urban rural spinner*/

        ArrayAdapter<CharSequence> urbanruraladapter = ArrayAdapter.createFromResource(this, R.array.phyUrbRural_type, android.R.layout.simple_spinner_item);
        urbanruraladapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputSpinnerurbanrural.setAdapter(urbanruraladapter);
        urbanruraladapter.notifyDataSetChanged();

        inputSpinnerurbanrural.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                phyurbanrural_value = String.valueOf(parent.getSelectedItemId());
                printLogs(LogTag, "initializeListeners", "urbanrural" + phyurbanrural_value);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

 /*Postal urban rural spinner*/

        ArrayAdapter<CharSequence> postalurbanruraladapter = ArrayAdapter.createFromResource(this, R.array.postalUrbRural_type, android.R.layout.simple_spinner_item);
        postalurbanruraladapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputSpinnerpostalurbanrural.setAdapter(postalurbanruraladapter);
        postalurbanruraladapter.notifyDataSetChanged();

        inputSpinnerpostalurbanrural.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                postalurbanrural_value = String.valueOf(parent.getSelectedItemId());
                printLogs(LogTag, "initializeListeners", "urbanrural" + postalurbanrural_value);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<CharSequence> equityadapter = ArrayAdapter.createFromResource(this, R.array.equity_type, android.R.layout.simple_spinner_item);
        equityadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputSpinnerequity.setAdapter(equityadapter);
        equityadapter.notifyDataSetChanged();

        inputSpinnerequity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                equity_value = String.valueOf(parent.getSelectedItemId());
                printLogs(LogTag, "initializeListeners", "equity_value" + equity_value);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<CharSequence> residentstatusadapter = ArrayAdapter.createFromResource(this, R.array.c_rStatus_type, android.R.layout.simple_spinner_item);
        residentstatusadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputSpinnerresidentstatus.setAdapter(residentstatusadapter);
        residentstatusadapter.notifyDataSetChanged();

        inputSpinnerresidentstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                residentstatus_value = String.valueOf(parent.getSelectedItemId());
                printLogs(LogTag, "initializeListeners", "residentstatus_value" + residentstatus_value);

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
        String Label = getLabelFromDb("lbl_S105_telephone", R.string.lbl_S105_telephone);
        lblView = (TextView) findViewById(R.id.lbltelephone);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S105_fax", R.string.lbl_S105_fax);
        lblView = (TextView) findViewById(R.id.lblfax);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);


        Label = getLabelFromDb("lbl_S105_homelanguage", R.string.lbl_S105_homelanguage);
        lblView = (TextView) findViewById(R.id.lblhomelanguage);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S105_physicalcode", R.string.lbl_S105_physicalcode);
        lblView = (TextView) findViewById(R.id.lblphysicalcode);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S105_phyaddress1", R.string.lbl_S105_phyaddress1);
        lblView = (TextView) findViewById(R.id.lblphyaddress1);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_phyaddress2", R.string.lbl_S105_phyaddress2);
        lblView = (TextView) findViewById(R.id.lblphyaddress2);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_phyaddress3", R.string.lbl_S105_phyaddress3);
        lblView = (TextView) findViewById(R.id.lblphyaddress3);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_municipality", R.string.lbl_S105_municipality);
        lblView = (TextView) findViewById(R.id.lblmunicipality);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_urbanrural", R.string.lbl_S105_urbanrural);
        lblView = (TextView) findViewById(R.id.lblurbanrural);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_country", R.string.lbl_S105_country);
        lblView = (TextView) findViewById(R.id.lblcountry);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_province", R.string.lbl_S105_province);
        lblView = (TextView) findViewById(R.id.lblprovince);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_city", R.string.lbl_S105_city);
        lblView = (TextView) findViewById(R.id.lblcity);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_suburb", R.string.lbl_S105_suburb);
        lblView = (TextView) findViewById(R.id.lblsuburb);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_province", R.string.lbl_S105_province);
        lblView = (TextView) findViewById(R.id.lblphyprovince);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_city", R.string.lbl_S105_city);
        lblView = (TextView) findViewById(R.id.lblphycity);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_suburb", R.string.lbl_S105_suburb);
        lblView = (TextView) findViewById(R.id.lblphysuburub);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        /*Postal layouts labels*/

        Label = getLabelFromDb("lbl_S105_postalcode", R.string.lbl_S105_postalcode);
        lblView = (TextView) findViewById(R.id.lblpostalcode);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S105_postaladdress1", R.string.lbl_S105_postaladdress1);
        lblView = (TextView) findViewById(R.id.lblpostaladdress1);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_postaladdress2", R.string.lbl_S105_postaladdress2);
        lblView = (TextView) findViewById(R.id.lblpostaladdress2);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_postaladdress3", R.string.lbl_S105_postaladdress3);
        lblView = (TextView) findViewById(R.id.lblpostaladdress3);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_postalmunicipality", R.string.lbl_S105_postalmunicipality);
        lblView = (TextView) findViewById(R.id.lblpostalmunicipality);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_postalurbanrural", R.string.lbl_S105_postalurbanrural);
        lblView = (TextView) findViewById(R.id.lblpostalurbanrural);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_postalcountry", R.string.lbl_S105_postalcountry);
        lblView = (TextView) findViewById(R.id.lblpostalcountry);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_postalprovince", R.string.lbl_S105_postalprovince);
        lblView = (TextView) findViewById(R.id.lblpostalprovince);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_postalcity", R.string.lbl_S105_postalcity);
        lblView = (TextView) findViewById(R.id.lblpostalcity);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_postalsuburb", R.string.lbl_S105_postalsuburb);
        lblView = (TextView) findViewById(R.id.lblpostalsuburb);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_postalprovince", R.string.lbl_S105_postalprovince);
        lblView = (TextView) findViewById(R.id.lbletpostalprovince);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_postalcity", R.string.lbl_S105_postalcity);
        lblView = (TextView) findViewById(R.id.lbletpostalcity);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_postalsuburb", R.string.lbl_S105_postalsuburb);
        lblView = (TextView) findViewById(R.id.lbletpostalsuburb);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_schoolemis", R.string.lbl_S105_schoolemis);
        lblView = (TextView) findViewById(R.id.lblschoolemis);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_lastscyear", R.string.lbl_S105_lastscyear);
        lblView = (TextView) findViewById(R.id.lbllastscyear);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_lblequity", R.string.lbl_S105_lblequity);
        lblView = (TextView) findViewById(R.id.lblequity);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_residentstatus", R.string.lbl_S105_residentstatus);
        lblView = (TextView) findViewById(R.id.lblresidentstatus);
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
            txtmunicipality.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            txtpostalmunicipality.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            txtschoolemis.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputSpinnerurbanrural.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            inputSpinnerpostalurbanrural.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            inputSpinnerequity.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            inputSpinnerresidentstatus.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
        }
    }



    private void fetchData() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_105_B;
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
                        JSONObject dataM = jsonObject.getJSONObject(KEY_DATA);

                        spin_homelanguage =  dataM.getString("s_d_o_home_lang");
                        inputtelephone.setText(dataM.getString("s_d_o_telephone"));
                        inputfax.setText(dataM.getString("s_d_o_fax"));
                        inputphysicalcode.setText(dataM.getString("s_d_o_phy_code"));
                        inputphyaddress1.setText(dataM.getString("s_d_o_phy_add_one"));
                        inputphyaddress2.setText(dataM.getString("s_d_o_phy_add_two"));
                        inputphyaddress3.setText(dataM.getString("s_d_o_phy_add_three"));
                        inputphyprovince.setText(dataM.getString("s_d_o_other_province_name"));
                        inputphycity.setText(dataM.getString("s_d_o_other_city_name"));
                        inputphysuburub.setText(dataM.getString("s_d_o_other_suburb_name"));
                        txtmunicipality.setText(dataM.getString("s_d_o_phy_municipality"));

                        spin_urbanrural = dataM.getString("s_d_o_is_urban_rural");
                        spin_country = dataM.getString("s_d_o_country");
                        spin_province = dataM.getString("s_d_o_physical_province");
                        spin_city = dataM.getString("s_d_o_physical_city");
                        spin_suburb = dataM.getString("s_d_o_physical_suburb");

                        /*postal data*/
                        inputpostalcode.setText(dataM.getString("s_d_o_postal_code"));
                        inputpostaladdress1.setText(dataM.getString("s_d_o_postal_add_line_one"));
                        inputpostaladdress2.setText(dataM.getString("s_d_o_postal_add_line_two"));
                        inputpostaladdress3.setText(dataM.getString("s_d_o_postal_add_line_three"));
                        txtpostalmunicipality.setText(dataM.getString("s_d_o_postal_municipality"));
                        inputetpostalprovince.setText(dataM.getString("s_d_o_postal_province_name"));
                        inputetpostalcity.setText(dataM.getString("s_d_o_postal_city_name"));
                        inputetpostalsuburub.setText(dataM.getString("s_d_o_postal_suburb_name"));
                        txtschoolemis.setText(dataM.getString("s_d_o_last_school_emi"));
                        inputlastscyear.setText(dataM.getString("s_d_o_last_school_year"));

                        spin_postalurbanrural = dataM.getString("s_d_o_postal_urban_rural");
                        spin_postalcountry = dataM.getString("s_d_o_postal_country");
                        spin_postalprovince = dataM.getString("s_d_o_postal_province");
                        spin_postalcity = dataM.getString("s_d_o_postal_city");
                        spin_postalsuburb = dataM.getString("s_d_o_postal_suburb");
                        spin_residentstatus = dataM.getString("s_d_o_citizen_resident");
                        spin_equity = dataM.getString("s_d_o_equity");

                     /*   String spin_categoryquali = dataM.getString("learner_qual_category_id");
                        //  inputInternCategoryQualification.setText(dataM.getString("learner_qual_category"));
//                        spinner_InternCategoryQualification.setSelection(Integer.parseInt(spin_categoryquali));

                        inputTaxRefNo.setText(dataM.getString("txt_ref_no"));
                        showProgress(false, mContentView, mProgressView);
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

                      *//*  //spinner disability
                        SpinnerDisabilityType.setSelection(Integer.parseInt(spin_disability));

                        //spinner enrollment year
                        int enroll_id=0;
                        for (int i = 0; i <datalist.size() ; i++) {
                             if(datalist.get(i).getName().equals(spin_enroll)){
                                enroll_id = i;
                                printLogs(LogTag, "fetchData", "enroll_id : " + enroll_id);
                            }
                        }
                        Spin_EnrollmentYear.setSelection(enroll_id);*//*
                        //CONDITION FOR GENDER MALE FEMALE
                        String Rbtn =  rb_genderValue;
                        if( Rbtn.equals("3")){
                            rb_male.setChecked(true);
                        }else{
                            rb_female.setChecked(true);
                        }*/
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




    @Override
    protected void verifyVersion() {
        printLogs(LogTag, "verifyVersion", "init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag, "verifyVersion", "isUpdate " + isUpdate);
        printLogs(LogTag, "verifyVersion", "isUpdate " + utilSessionObj.getIsUpdateRequired());
        if (isUpdate) {
            Intent intent = new Intent(SEditProfileStepTwoA.this, AppUpdatedA.class);
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
        Intent intent = new Intent(SEditProfileStepTwoA.this, SEditProfileMainA.class);
        printLogs(LogTag, "onOptionsItemSelected", "SEditProfileStepTwoA");
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        printLogs(LogTag, "onOptionsItemSelected", "init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(SEditProfileStepTwoA.this, SEditProfileMainA.class);
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

    public void FormSubmit() {

        final String telephone = inputtelephone.getText().toString().trim();
        final String fax = inputfax.getText().toString().trim();
        final String physicalCode = inputphysicalcode.getText().toString().trim();
        final String phyaddress1 = inputphyaddress1.getText().toString().trim();
        final String phyaddress2 = inputphyaddress2.getText().toString().trim();
        final String phyaddress3 = inputphyaddress3.getText().toString().trim();
        final String phyprovince = inputphyprovince.getText().toString().trim();
        final String phycity = inputphycity.getText().toString().trim();
        final String physuburub = inputphysuburub.getText().toString().trim();
        final String postalcode = inputpostalcode.getText().toString().trim();
        final String postaladdress1 = inputpostaladdress1.getText().toString().trim();
        final String postaladdress2 = inputpostaladdress2.getText().toString().trim();
        final String postaladdress3 = inputpostaladdress3.getText().toString().trim();
        final String postalprovince = inputetpostalprovince.getText().toString().trim();
        final String postalcity = inputetpostalcity.getText().toString().trim();
        final String postalsuburub = inputetpostalsuburub.getText().toString().trim();
        final String lastschoolyear = inputlastscyear.getText().toString().trim();

        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_105_3;
        printLogs(LogTag, "FormSubmit", "URL : " + FINAL_URL);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put("home_language", "1");
            jsonBody.put("telephone", telephone);
            jsonBody.put("fax", fax);
            jsonBody.put("physicalCode", physicalCode);
            jsonBody.put("phyaddress1", phyaddress1);
            jsonBody.put("phyaddress2", phyaddress2);
            jsonBody.put("phyaddress3", phyaddress3);
            jsonBody.put("phy_municipality", "2");
            jsonBody.put("phy_urbanrural", "3");
            jsonBody.put("phy_country", "2");
            jsonBody.put("phy_province", "2");
            jsonBody.put("phy_city", "2");
            jsonBody.put("phy_suburb", "2");
            jsonBody.put("phy_province_name", phyprovince);
            jsonBody.put("phy_city_name", phycity);
            jsonBody.put("phy_suburub_name", physuburub);
            jsonBody.put("postalcode", postalcode);
            jsonBody.put("postaladdress1", postaladdress1);
            jsonBody.put("postaladdress2", postaladdress2);
            jsonBody.put("postaladdress3", postaladdress3);
            jsonBody.put("postal_municipality", "2");
            jsonBody.put("postal_urbanrural", "3");
            jsonBody.put("postal_country", "2");
            jsonBody.put("postal_province", "2");
            jsonBody.put("postal_city", "2");
            jsonBody.put("postal_suburb", "2");
            jsonBody.put("postal_province_name", postalprovince);
            jsonBody.put("postal_city_name", postalcity);
            jsonBody.put("postal_suburub_name", postalsuburub);
            jsonBody.put("lastschool_emi", "4");
            jsonBody.put("lastschool_year", lastschoolyear);
            jsonBody.put("equity", "1");
            jsonBody.put("c_resident_status", "1");

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