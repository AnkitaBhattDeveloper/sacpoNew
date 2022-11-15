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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.adapter.AutoCompleteAdapter;
import za.co.sacpo.grant.xCubeLib.adapter.SpinAdapter;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dataObj.SpinnerObj;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;

public class SEditProfileStepTwoA extends BaseFormAPCPrivate {
    private String ActivityId = "S105B";
    public View mProgressView, mContentView,heading;
    private TextView lblView;
    SEditProfileStepTwoA thisClass;
    String phyurbanrural_value,postalurbanrural_value,equity_value,residentstatus_value,spin_homelanguage
            ,spin_day,spin_month,spin_year,municipality_value,postalmunicipality_value,schoolEMI_value,
            homelanguage_value="";
    public EditText inputtelephone,inputfax,inputphysicalcode, inputphyaddress1, inputphyaddress2, inputphyaddress3,
            inputphyprovince, inputphycity, inputphysuburub, inputpostalcode, inputpostaladdress1,
            inputpostaladdress2,inputpostaladdress3,inputetpostalprovince,inputetpostalcity,inputetpostalsuburub,inputlastscyear;
    public TextInputLayout inputLayouttelephone, inputLayoutfax, inputLayoutphysicalcode,inputLayoutphyaddress1,
            inputLayoutphyaddress2, inputLayoutphyaddress3, inputLayoutmunicipality,inputLayoutphyprovince,
            inputLayoutphycity, inputLayoutphysuburub, inputLayoutpostalcode, inputLayoutpostaladdress1,
            inputLayoutpostaladdress2,inputLayoutpostaladdress3,inputLayoutpostalmunicipality,inputLayoutetpostalprovince
            ,inputLayoutetpostalcity,inputLayoutetpostalsuburub,inputLayoutschoolemis,inputLayoutlastscyear;
    public Button btnUpdate;
    private Spinner inputSpinnerurbanrural, inputSpinnerpostalurbanrural, inputSpinnerequity,
            inputSpinnerresidentstatus, inputSpinnerhomelanguage, inputSpinnercountry,inputSpinnersuburb,
            inputSpinnerprovince,inputSpinnercity,inputSpinnerpostalcountry,inputSpinnerpostalcity,inputSpinnerpostalprovince,
            inputSpinnerpostalsuburb;
    AutoCompleteTextView txtmunicipality,txtpostalmunicipality,txtschoolemis;
    String spin_urbanrural,spin_country,spin_province,spin_city,spin_suburb,urbanrual_value="",country_value="",
            province_value="",city_value="",suburb_value="";
    String spin_postalurbanrural,spin_postalcountry,spin_postalprovince,spin_postalcity,spin_postalsuburb
            ,spin_equity="",spin_residentstatus="",postalurbanrual_value="",postalcountry_value="",
            postalprovince_value="",postalcity_value="",postalsuburb_value="",phyMunicipality_value="",
            postMunicipality_value="",lastschoolEMI_value=""
            ,phyUrbanrual="",phyCountry="",phyProvince="",phyCity="",phySuburb="";

    public SpinnerObj[] LanguageType;
    public SpinnerObj[] CountryType;
    public SpinnerObj[] ProvinceType;
    public SpinnerObj[] CityType;
    public SpinnerObj[] SuburbsType;
    public SpinnerObj[] MunicipalityType;
    ArrayList<String> municipality_list = new ArrayList<>();
    public SpinnerObj[] SchoolEMIType;
    ArrayList<String> SchoolEMI_list = new ArrayList<>();
    LinearLayout spinnersLayout,edittextLayouts,postalspinnersLayout,postaledittextLayouts;




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
            showProgress(true, mContentView, mProgressView);
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
       /* txtpostalmunicipality = findViewById(R.id.txtpostalmunicipality);
        txtschoolemis = findViewById(R.id.txtschoolemis);
       */ spinnersLayout = findViewById(R.id.spinnersLayout);
        edittextLayouts = findViewById(R.id.edittextLayouts);
       /* postalspinnersLayout = findViewById(R.id.postalspinnersLayout);
        postaledittextLayouts = findViewById(R.id.postaledittextLayouts);
*/
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
       /* inputLayoutpostalcode = findViewById(R.id.inputLayoutpostalcode);
        inputLayoutpostaladdress1 = findViewById(R.id.inputLayoutpostaladdress1);
        inputLayoutpostaladdress2 = findViewById(R.id.inputLayoutpostaladdress2);
        inputLayoutpostaladdress3 = findViewById(R.id.inputLayoutpostaladdress3);
        inputLayoutpostalmunicipality = findViewById(R.id.inputLayoutpostalmunicipality);
        inputLayoutetpostalprovince = findViewById(R.id.inputLayoutetpostalprovince);
        inputLayoutetpostalcity = findViewById(R.id.inputLayoutetpostalcity);
        inputLayoutetpostalsuburub = findViewById(R.id.inputLayoutetpostalsuburub);
        inputLayoutschoolemis = findViewById(R.id.inputLayoutschoolemis);
        inputLayoutlastscyear = findViewById(R.id.inputLayoutlastscyear);
*/
        /*Spinners*/
        inputSpinnerurbanrural = findViewById(R.id.inputSpinnerurbanrural);
       // inputSpinnerpostalurbanrural = findViewById(R.id.inputSpinnerpostalurbanrural);
      //  inputSpinnerequity = findViewById(R.id.inputSpinnerequity);
      //  inputSpinnerresidentstatus = findViewById(R.id.inputSpinnerresidentstatus);
        inputSpinnerhomelanguage = findViewById(R.id.inputSpinnerhomelanguage);
        inputSpinnercountry = findViewById(R.id.inputSpinnercountry);
        inputSpinnerprovince = findViewById(R.id.inputSpinnerprovince);
        inputSpinnercity = findViewById(R.id.inputSpinnercity);
        inputSpinnersuburb = findViewById(R.id.inputSpinnersuburb);
        /*inputSpinnerpostalcountry = findViewById(R.id.inputSpinnerpostalcountry);
        inputSpinnerpostalprovince = findViewById(R.id.inputSpinnerpostalprovince);
        inputSpinnerpostalcity = findViewById(R.id.inputSpinnerpostalcity);
        inputSpinnerpostalsuburb = findViewById(R.id.inputSpinnerpostalsuburb);
*/
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
      /*  inputpostalcode = findViewById(R.id.inputpostalcode);
        inputpostaladdress1 = findViewById(R.id.inputpostaladdress1);
        inputpostaladdress2 = findViewById(R.id.inputpostaladdress2);
        inputpostaladdress3 = findViewById(R.id.inputpostaladdress3);
        inputetpostalprovince = findViewById(R.id.inputetpostalprovince);
        inputetpostalcity = findViewById(R.id.inputetpostalcity);
        inputetpostalsuburub = findViewById(R.id.inputetpostalsuburub);
        inputlastscyear = findViewById(R.id.inputlastscyear);
*/
    }

    @Override
    protected void initializeListeners() {

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });

        /*Physical urban rural spinner*/

        ArrayAdapter<CharSequence> urbanruraladapter = ArrayAdapter.createFromResource(this, R.array.phyUrbRural_type, android.R.layout.simple_spinner_item);
        urbanruraladapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputSpinnerurbanrural.setAdapter(urbanruraladapter);

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

      /*  *//*Postal urban rural spinner*//*

        ArrayAdapter<CharSequence> postalurbanruraladapter = ArrayAdapter.createFromResource(this, R.array.postalUrbRural_type, android.R.layout.simple_spinner_item);
        postalurbanruraladapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputSpinnerpostalurbanrural.setAdapter(postalurbanruraladapter);


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


        inputSpinnerresidentstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                residentstatus_value = String.valueOf(parent.getSelectedItemId());
                printLogs(LogTag, "initializeListeners", "residentstatus_value" + residentstatus_value);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
        showProgress(false, mContentView, mProgressView);
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

        /*Postal layouts labels*//*

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
*/

        Label = getLabelFromDb("b_S105_save", R.string.b_S105_save);
        btnUpdate.setText(Label);

        Label = getLabelFromDb("h_505", R.string.h_505);
        lblView = (TextView) findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            btnUpdate.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            inputtelephone.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputfax.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputphysicalcode.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputphyaddress1.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputphyaddress2.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputphyaddress3.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputphyprovince.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputphycity.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputphysuburub.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
           /* inputpostalcode.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputpostaladdress1.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputpostaladdress2.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputpostaladdress3.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputetpostalprovince.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputetpostalcity.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputetpostalsuburub.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputlastscyear.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
          */  txtmunicipality.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            /*txtpostalmunicipality.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            txtschoolemis.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            */inputSpinnerhomelanguage.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            inputSpinnercountry.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            inputSpinnerprovince.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            inputSpinnercity.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            inputSpinnersuburb.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            inputSpinnerurbanrural.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
           /* inputSpinnerpostalurbanrural.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            inputSpinnerpostalcountry.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            inputSpinnerpostalprovince.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            inputSpinnerpostalcity.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            inputSpinnerpostalsuburb.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            inputSpinnerequity.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            inputSpinnerresidentstatus.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
       */ }
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
                        JSONArray jsonArray = jsonObject.getJSONArray(KEY_DATA);
                        for (int i = 0; i <jsonArray.length() ; i++) {
                            JSONObject dataM = jsonArray.getJSONObject(i);
                            homelanguage_value =  dataM.getString("s_d_o_home_lang");
                            inputtelephone.setText(dataM.getString("s_d_o_telephone"));
                            inputfax.setText(dataM.getString("s_d_o_fax"));
                            inputphysicalcode.setText(dataM.getString("s_d_o_phy_code"));
                            inputphyaddress1.setText(dataM.getString("s_d_o_phy_add_one"));
                            inputphyaddress2.setText(dataM.getString("s_d_o_phy_add_two"));
                            inputphyaddress3.setText(dataM.getString("s_d_o_phy_add_three"));
                            inputphyprovince.setText(dataM.getString("s_d_o_other_province_name"));
                            inputphycity.setText(dataM.getString("s_d_o_other_city_name"));
                            inputphysuburub.setText(dataM.getString("s_d_o_other_suburb_name"));
                            phyMunicipality_value = dataM.getString("s_d_o_phy_municipality");
                            phyUrbanrual= dataM.getString("s_d_o_is_urban_rural");
                            phyCountry = dataM.getString("s_d_o_country");
                            phyProvince = dataM.getString("s_d_o_physical_province");
                            phyCity = dataM.getString("s_d_o_physical_city");
                            phySuburb = dataM.getString("s_d_o_physical_suburb");

                            /*postal data*//*
                            inputpostalcode.setText(dataM.getString("s_d_o_postal_code"));
                            inputpostaladdress1.setText(dataM.getString("s_d_o_postal_add_line_one"));
                            inputpostaladdress2.setText(dataM.getString("s_d_o_postal_add_line_two"));
                            inputpostaladdress3.setText(dataM.getString("s_d_o_postal_add_line_three"));
                            postMunicipality_value = dataM.getString("s_d_o_postal_municipality");
                            inputetpostalprovince.setText(dataM.getString("s_d_o_postal_province_name"));
                            inputetpostalcity.setText(dataM.getString("s_d_o_postal_city_name"));
                            inputetpostalsuburub.setText(dataM.getString("s_d_o_postal_suburb_name"));
                            lastschoolEMI_value = dataM.getString("s_d_o_last_school_emi");
                            inputlastscyear.setText(dataM.getString("s_d_o_last_school_year"));
                            postalurbanrual_value = dataM.getString("s_d_o_postal_urban_rural");
                            postalcountry_value = dataM.getString("s_d_o_postal_country");
                            postalprovince_value = dataM.getString("s_d_o_postal_province");
                            postalcity_value = dataM.getString("s_d_o_postal_city");
                            postalsuburb_value = dataM.getString("s_d_o_postal_suburb");
                            spin_residentstatus = dataM.getString("s_d_o_citizen_resident");
                            spin_equity = dataM.getString("s_d_o_equity");
*/

                        }
                        fetchCountry(phyCountry,phyProvince,phyCity,phySuburb);
                        fetchLanguage();
                        fetchMunicipality();
                       /* if(!spin_equity.equals("")){
                            inputSpinnerequity.setSelection(Integer.parseInt(spin_equity));
                        }*/
                        if(!phyUrbanrual.equals("")){
                            inputSpinnerurbanrural.setSelection(Integer.parseInt(phyUrbanrual));
                        }
                        /*if(!spin_residentstatus.equals("")){
                            inputSpinnerresidentstatus.setSelection(Integer.parseInt(spin_residentstatus));
                        }
                        if(!postalurbanrual_value.equals("")){
                            inputSpinnerpostalurbanrural.setSelection(Integer.parseInt(postalurbanrual_value));
                        }*/

                    } else if (Status.equals("2")) {
                       // showProgress(false, mContentView, mProgressView);
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


    private void fetchCountry(String phyCountry, String phyProvince, String phyCity, String phySuburb) {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.COUNTRY_105;
        FINAL_URL=FINAL_URL+"?token="+token;
        printLogs(LogTag,"fetchCountrySpinner","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchCountrySpinner", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        CountryType = new SpinnerObj[dataM.length()];
                        for (int i = 0; i <dataM.length() ; i++) {
                            JSONObject jsonObject = dataM.getJSONObject(i);
                            CountryType[i] = new SpinnerObj();
                            CountryType[i].setId(jsonObject.getString("country_id"));
                            CountryType[i].setName(jsonObject.getString("country_type"));

                        }
                        SpinAdapter adapter = new SpinAdapter(SEditProfileStepTwoA.this, android.R.layout.simple_spinner_item, CountryType);
                        inputSpinnercountry.setAdapter(adapter);
                        if(!phyCountry.equals("")){
                                int spinnerPosition = getSelectedPoostion(inputSpinnercountry, phyCountry);
                                inputSpinnercountry.setSelection(spinnerPosition);
                        }
                        inputSpinnercountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                spin_country = adapter.getItem(i).getId();
                                if(spin_country.equals("1")){
                                    fetchProvince(phyProvince,phyCity,phySuburb);
                                    spinnersLayout.setVisibility(View.VISIBLE);
                                    edittextLayouts.setVisibility(View.GONE);
                                }else{
                                    spinnersLayout.setVisibility(View.GONE);
                                    edittextLayouts.setVisibility(View.VISIBLE);
                                }
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

    private void fetchPostalCountry() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.COUNTRY_105;
        FINAL_URL=FINAL_URL+"?token="+token;
        printLogs(LogTag,"fetchPostalCountrySpinner","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchPostalCountrySpinner", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        CountryType = new SpinnerObj[dataM.length()];
                        for (int i = 0; i <dataM.length() ; i++) {
                            JSONObject jsonObject = dataM.getJSONObject(i);
                            CountryType[i] = new SpinnerObj();
                            CountryType[i].setId(jsonObject.getString("country_id"));
                            CountryType[i].setName(jsonObject.getString("country_type"));

                        }
                        SpinAdapter adapter = new SpinAdapter(SEditProfileStepTwoA.this, android.R.layout.simple_spinner_item, CountryType);
                        inputSpinnerpostalcountry.setAdapter(adapter);
                        if(!postalcountry_value.equals("")){
                            int spinnerPosition = getSelectedPoostion(inputSpinnerpostalcountry, postalcountry_value);
                            inputSpinnerpostalcountry.setSelection(spinnerPosition);
                        }
                        inputSpinnerpostalcountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                spin_postalcountry = adapter.getItem(i).getId();
                                if(spin_postalcountry.equals("1")){
                                    fetchPostalProvince();
                                    postalspinnersLayout.setVisibility(View.VISIBLE);
                                    postaledittextLayouts.setVisibility(View.GONE);
                                }else{
                                    postalspinnersLayout.setVisibility(View.GONE);
                                    postaledittextLayouts.setVisibility(View.VISIBLE);
                                }
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


    private void fetchPostalProvince() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.PROVINCE_105;
        FINAL_URL=FINAL_URL+"?token="+token;
        printLogs(LogTag,"fetchProvinceSpinner","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchProvinceSpinner", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        ProvinceType = new SpinnerObj[dataM.length()];
                        for (int i = 0; i <dataM.length() ; i++) {
                            JSONObject jsonObject = dataM.getJSONObject(i);
                            ProvinceType[i] = new SpinnerObj();
                            ProvinceType[i].setId(jsonObject.getString("province_id"));
                            ProvinceType[i].setName(jsonObject.getString("province_type"));

                        }
                       SpinAdapter adapter = new SpinAdapter(SEditProfileStepTwoA.this, android.R.layout.simple_spinner_item, ProvinceType);
                        inputSpinnerpostalprovince.setAdapter(adapter);
                        if(!postalprovince_value.equals("")){
                            int spinnerPosition = getSelectedPoostion(inputSpinnerpostalprovince, postalprovince_value);
                            inputSpinnerpostalprovince.setSelection(spinnerPosition);
                        }
                        inputSpinnerpostalprovince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                spin_postalprovince = adapter.getItem(i).getId();
                               if(!spin_postalprovince.equals("")){
                                    fetchPostalCity(spin_postalprovince);
                                }

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

    private void fetchPostalCity(String spin_postalprovince) {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.CITY_105;
        FINAL_URL=FINAL_URL+"?token="+token+"&province_id="+spin_postalprovince;
        printLogs(LogTag,"fetchCitySpinner","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchCitySpinner", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        CityType = new SpinnerObj[dataM.length()];
                        for (int i = 0; i <dataM.length() ; i++) {
                            JSONObject jsonObject = dataM.getJSONObject(i);
                            CityType[i] = new SpinnerObj();
                            CityType[i].setId(jsonObject.getString("city_id"));
                            CityType[i].setName(jsonObject.getString("city_type"));

                        }
                        SpinAdapter adapter = new SpinAdapter(SEditProfileStepTwoA.this, android.R.layout.simple_spinner_item, CityType);
                        inputSpinnerpostalcity.setAdapter(adapter);
                        if(!postalcity_value.equals("")){
                            int spinnerPosition = getSelectedPoostion(inputSpinnerpostalcity, postalcity_value);
                            inputSpinnerpostalcity.setSelection(spinnerPosition);
                        }
                        inputSpinnerpostalcity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                spin_postalcity = adapter.getItem(i).getId();
                                if(!spin_postalcity.equals("")){
                                    fetchPostalSuburbs(spin_postalcity);
                                }

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

    private void fetchPostalSuburbs(String spin_postalcity) {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.SUBURBS_105;
        FINAL_URL=FINAL_URL+"?token="+token+"&city_id="+spin_postalcity;
        printLogs(LogTag,"fetchSuburbsSpinner","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchSuburbsSpinner", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        SuburbsType = new SpinnerObj[dataM.length()];
                        for (int i = 0; i <dataM.length() ; i++) {
                            JSONObject jsonObject = dataM.getJSONObject(i);
                            SuburbsType[i] = new SpinnerObj();
                            SuburbsType[i].setId(jsonObject.getString("suburb_id"));
                            SuburbsType[i].setName(jsonObject.getString("suburb_type"));

                        }
                        SpinAdapter adapter = new SpinAdapter(SEditProfileStepTwoA.this, android.R.layout.simple_spinner_item, SuburbsType);
                        inputSpinnerpostalsuburb.setAdapter(adapter);
                        if(!postalsuburb_value.equals("")){
                            int spinnerPosition = getSelectedPoostion(inputSpinnerpostalsuburb, postalsuburb_value);
                            inputSpinnerpostalsuburb.setSelection(spinnerPosition);
                        }

                        inputSpinnerpostalsuburb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                spin_postalsuburb = adapter.getItem(i).getId();

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

    private void fetchProvince(String phyProvince, String phyCity, String phySuburb) {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.PROVINCE_105;
        FINAL_URL=FINAL_URL+"?token="+token;
        printLogs(LogTag,"fetchProvinceSpinner","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchProvinceSpinner", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        ProvinceType = new SpinnerObj[dataM.length()];
                        for (int i = 0; i <dataM.length() ; i++) {
                            JSONObject jsonObject = dataM.getJSONObject(i);
                            ProvinceType[i] = new SpinnerObj();
                            ProvinceType[i].setId(jsonObject.getString("province_id"));
                            ProvinceType[i].setName(jsonObject.getString("province_type"));

                        }
                        SpinAdapter adapter = new SpinAdapter(SEditProfileStepTwoA.this, android.R.layout.simple_spinner_item, ProvinceType);
                        inputSpinnerprovince.setAdapter(adapter);
                        if(!phyProvince.equals("")){
                            int spinnerPosition = getSelectedPoostion(inputSpinnerprovince, phyProvince);
                            inputSpinnerprovince.setSelection(spinnerPosition);
                        }
                        inputSpinnerprovince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                spin_province = adapter.getItem(i).getId();
                                if(!spin_province.equals("")){
                                    fetchCity(spin_province,phyCity,phySuburb);
                                }

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

    private void fetchCity(String spin_province, String phyCity, String phySuburb) {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.CITY_105;
        FINAL_URL=FINAL_URL+"?token="+token+"&province_id="+spin_province;
        printLogs(LogTag,"fetchCitySpinner","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchCitySpinner", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        CityType = new SpinnerObj[dataM.length()];
                        for (int i = 0; i <dataM.length() ; i++) {
                            JSONObject jsonObject = dataM.getJSONObject(i);
                            CityType[i] = new SpinnerObj();
                            CityType[i].setId(jsonObject.getString("city_id"));
                            CityType[i].setName(jsonObject.getString("city_type"));

                        }
                        SpinAdapter cityadapter = new SpinAdapter(SEditProfileStepTwoA.this, android.R.layout.simple_spinner_item, CityType);
                        inputSpinnercity.setAdapter(cityadapter);
                        if(!phyCity.equals("")){
                            int spinnerPosition = getSelectedPoostion(inputSpinnercity, phyCity);
                            inputSpinnercity.setSelection(spinnerPosition);
                        }
                        inputSpinnercity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                               spin_city = cityadapter.getItem(i).getId();
                                printLogs(LogTag, "fetchCitySpinner", "spin_city : " + spin_city);
                                if(!spin_city.equals("")){
                                    fetchSuburbs(spin_city,phySuburb);
                                }

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

    private void fetchSuburbs(String spin_city, String phySuburb) {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.SUBURBS_105;
        FINAL_URL=FINAL_URL+"?token="+token+"&city_id="+spin_city;
        printLogs(LogTag,"fetchSuburbsSpinner","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchSuburbsSpinner", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        SuburbsType = new SpinnerObj[dataM.length()];
                        for (int i = 0; i <dataM.length() ; i++) {
                            JSONObject jsonObject = dataM.getJSONObject(i);
                            SuburbsType[i] = new SpinnerObj();
                            SuburbsType[i].setId(jsonObject.getString("suburb_id"));
                            SuburbsType[i].setName(jsonObject.getString("suburb_type"));

                        }
                        SpinAdapter adapter = new SpinAdapter(SEditProfileStepTwoA.this, android.R.layout.simple_spinner_item, SuburbsType);
                        inputSpinnersuburb.setAdapter(adapter);
                        if(!phySuburb.equals("")){
                            int spinnerPosition = getSelectedPoostion(inputSpinnersuburb, phySuburb);
                            inputSpinnersuburb.setSelection(spinnerPosition);
                        }
                        inputSpinnersuburb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                spin_suburb = adapter.getItem(i).getId();

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

    private void fetchLastSchoolEMI() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.SCHOOLEMI_105B;
        FINAL_URL=FINAL_URL+"?token="+token;
        printLogs(LogTag,"fetchschoolEMISpinner","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchschoolEMISpinner", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        SchoolEMIType = new SpinnerObj[dataM.length()];
                        for (int i = 0; i <dataM.length() ; i++) {
                            JSONObject jsonObject = dataM.getJSONObject(i);
                            SchoolEMI_list.add(jsonObject.getString("school_emi_type"));
                            SchoolEMIType[i] = new SpinnerObj();
                            SchoolEMIType[i].setId(jsonObject.getString("school_emi_id"));
                            SchoolEMIType[i].setName(jsonObject.getString("school_emi_type"));

                        }

                        AutoCompleteAdapter autoCompleteAdapter = new AutoCompleteAdapter(SEditProfileStepTwoA.this,android.R.layout.simple_spinner_item,android.R.id.text1,SchoolEMI_list);
                        txtschoolemis.setAdapter(autoCompleteAdapter);
                        if(!lastschoolEMI_value.equals("")){
                            for (int j = 0; j <SchoolEMIType.length ; j++) {
                                if(SchoolEMIType[j].getId().equals(lastschoolEMI_value)){
                                    txtschoolemis.setText(SchoolEMIType[j].getName());
                                }
                            }
                        }

                        txtschoolemis.setThreshold(2);


                        txtschoolemis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String schoolemi_value1 = autoCompleteAdapter.getItem(i);
                                printLogs(LogTag, "fetchschoolEMISpinner", "schoolemi_value1 id: " + schoolemi_value1);

                                for (int j = 0; j <SchoolEMIType.length ; j++) {
                                    if(SchoolEMIType[j].getName().equals(schoolemi_value1)){
                                        schoolEMI_value = SchoolEMIType[j].getId();
                                        printLogs(LogTag, "fetchschoolEMISpinner", "schoolEMI_value id: " + schoolEMI_value);
                                    }
                                }
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

    private void fetchMunicipality() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.MUNICIPALITY_105B;
        FINAL_URL=FINAL_URL+"?token="+token;
        printLogs(LogTag,"fetchMunicipalitySpinner","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchLanguageSpinner", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        MunicipalityType = new SpinnerObj[dataM.length()];
                        for (int i = 0; i <dataM.length() ; i++) {
                            JSONObject jsonObject = dataM.getJSONObject(i);
                            municipality_list.add(jsonObject.getString("muncipality_type"));
                            MunicipalityType[i] = new SpinnerObj();
                            MunicipalityType[i].setId(jsonObject.getString("muncipality_id"));
                            MunicipalityType[i].setName(jsonObject.getString("muncipality_type"));

                        }
                        AutoCompleteAdapter autoCompleteAdapter = new AutoCompleteAdapter(SEditProfileStepTwoA.this,android.R.layout.simple_spinner_item,android.R.id.text1,municipality_list);
                        txtmunicipality.setAdapter(autoCompleteAdapter);
                        if(!phyMunicipality_value.equals("")){
                        for (int j = 0; j <MunicipalityType.length ; j++) {
                            if(MunicipalityType[j].getId().equals(phyMunicipality_value)){
                                txtmunicipality.setText(MunicipalityType[j].getName());
                            }
                        }
                        }
                        txtmunicipality.setThreshold(2);
                        txtmunicipality.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String municipality_value1 = autoCompleteAdapter.getItem(i);
                                for (int j = 0; j <MunicipalityType.length ; j++) {
                                    if(MunicipalityType[j].getName().equals(municipality_value1)){
                                        municipality_value = MunicipalityType[j].getId();
                                        printLogs(LogTag, "fetchmunicipalitySpinner", "municipality_value id: " + municipality_value);
                                    }
                                }
                            }
                        });
                        /*txtpostalmunicipality.setAdapter(autoCompleteAdapter);
                        if(!postMunicipality_value.equals("")){
                            for (int j = 0; j <MunicipalityType.length ; j++) {
                                if(MunicipalityType[j].getId().equals(postMunicipality_value)){
                                    txtpostalmunicipality.setText(MunicipalityType[j].getName());
                                }
                            }
                        }
                        txtpostalmunicipality.setThreshold(2);
                        txtpostalmunicipality.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String postalmunicipality_value1 = autoCompleteAdapter.getItem(i);
                                for (int j = 0; j <MunicipalityType.length ; j++) {
                                    if(MunicipalityType[j].getName().equals(postalmunicipality_value1)){
                                        postalmunicipality_value = MunicipalityType[j].getId();
                                        printLogs(LogTag, "fetchmunicipalitySpinner", "postalmunicipality_value id: " + postalmunicipality_value);
                                    }
                                }
                            }
                        });*/
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

    private void fetchLanguage() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.LANGAUGE_105B;
        FINAL_URL=FINAL_URL+"?token="+token;
        printLogs(LogTag,"fetchLanguageSpinner","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchLanguageSpinner", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        LanguageType = new SpinnerObj[dataM.length()];
                        for (int i = 0; i <dataM.length() ; i++) {
                            JSONObject jsonObject = dataM.getJSONObject(i);
                            LanguageType[i] = new SpinnerObj();
                            LanguageType[i].setId(jsonObject.getString("lang_id"));
                            LanguageType[i].setName(jsonObject.getString("lang_type"));

                        }
                        SpinAdapter adapter = new SpinAdapter(SEditProfileStepTwoA.this, android.R.layout.simple_spinner_item, LanguageType);
                        inputSpinnerhomelanguage.setAdapter(adapter);
                        if(!homelanguage_value.equals("")){
                            int spinnerPosition = getSelectedPoostion(inputSpinnerhomelanguage, homelanguage_value);
                            inputSpinnerhomelanguage.setSelection(spinnerPosition);
                        }
                        inputSpinnerhomelanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                spin_homelanguage = adapter.getItem(i).getId();
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

    private int getSelectedPoostion(Spinner spinner, String selectedItem) {
        //Long val = Long.parseLong(value);
        int pos=0;
        SpinAdapter adapter = (SpinAdapter) spinner.getAdapter();
        for (int position = 0; position < adapter.getCount(); position++) {
            printLogs(LogTag,"getSelectedPosition","init"+position+"==="+adapter.getItem(position).getId()+"==00=="+selectedItem +"Counting"+adapter.getCount());
            if(adapter.getItem(position).getId().equals (selectedItem)){
                printLogs(LogTag,"getSelectedPosition","position"+position+"=="+selectedItem);
                pos = position;
            }
        }
        return pos;
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
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.UPDATEDETAILS_105_B;
        printLogs(LogTag, "FormSubmit", "URL : " + FINAL_URL);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put("home_language", spin_homelanguage);
            jsonBody.put("telephone", telephone);
            jsonBody.put("fax", fax);
            jsonBody.put("physicalCode", physicalCode);
            jsonBody.put("phyaddress1", phyaddress1);
            jsonBody.put("phyaddress2", phyaddress2);
            jsonBody.put("phyaddress3", phyaddress3);
            jsonBody.put("phy_municipality", municipality_value);
            jsonBody.put("phy_urbanrural", phyurbanrural_value);
            jsonBody.put("phy_country", spin_country);
            jsonBody.put("phy_province", spin_province);
            jsonBody.put("phy_city", spin_city);
            jsonBody.put("phy_suburb", spin_suburb);
            jsonBody.put("phy_province_name", phyprovince);
            jsonBody.put("phy_city_name", phycity);
            jsonBody.put("phy_suburub_name", physuburub);
            jsonBody.put("postalcode", postalcode);
            jsonBody.put("postaladdress1", postaladdress1);
            jsonBody.put("postaladdress2", postaladdress2);
            jsonBody.put("postaladdress3", postaladdress3);
            jsonBody.put("postal_municipality", postalmunicipality_value);
            jsonBody.put("postal_urbanrural", postalurbanrural_value);
            jsonBody.put("postal_country", spin_postalcountry);
            jsonBody.put("postal_province", spin_postalprovince);
            jsonBody.put("postal_city", spin_postalcity);
            jsonBody.put("postal_suburb", spin_postalsuburb);
            jsonBody.put("postal_province_name", postalprovince);
            jsonBody.put("postal_city_name", postalcity);
            jsonBody.put("postal_suburub_name", postalsuburub);
            jsonBody.put("lastschool_emi", schoolEMI_value);
            jsonBody.put("lastschool_year", lastschoolyear);
            jsonBody.put("equity", spin_equity);
            jsonBody.put("c_resident_status", spin_residentstatus);

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
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = getLabelFromDb("m_S105_title", R.string.m_S105_title);
                        String sMessage = getLabelFromDb("m_S105_message", R.string.m_S105_message);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showSuccessDialogSEditProfileTwo(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose, thisClass);
                    } else {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-104";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "-S105";
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

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
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