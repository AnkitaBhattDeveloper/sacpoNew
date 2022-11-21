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
import za.co.sacpo.grant.xCubeLib.dataObj.Step2DataVisibilityObj;
import za.co.sacpo.grant.xCubeLib.dataObj.Step3DataVisibilityObj;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;

public class SEditProfileStepTwoBA extends BaseFormAPCPrivate {
    private String ActivityId = "S105B";
    public View mProgressView, mContentView,heading;
    private TextView lblView;
    SEditProfileStepTwoBA thisClass;
    String postalurbanrural_value,equity_value,residentstatus_value,postalmunicipality_value="",schoolEMI_value="";
    public EditText inputpostalcode, inputpostaladdress1,inputpostaladdress2,inputpostaladdress3,
            inputetpostalprovince,inputetpostalcity,inputetpostalsuburub,inputlastscyear;
    public TextInputLayout  inputLayoutpostalcode, inputLayoutpostaladdress1,inputLayoutpostaladdress2,
            inputLayoutpostaladdress3,inputLayoutpostalmunicipality,inputLayoutetpostalprovince
            ,inputLayoutetpostalcity,inputLayoutetpostalsuburub,inputLayoutschoolemis,inputLayoutlastscyear;
    public Button btnUpdate;
    private Spinner inputSpinnerpostalurbanrural, inputSpinnerequity,inputSpinnerresidentstatus,
            inputSpinnerpostalcountry,inputSpinnerpostalcity,inputSpinnerpostalprovince,inputSpinnerpostalsuburb;
    AutoCompleteTextView txtpostalmunicipality,txtschoolemis;
    String spin_postalcountry,spin_postalprovince,spin_postalcity,spin_postalsuburb,spin_equity="",
            spin_residentstatus="",postalurbanrual_value="",postalcountry_value="",postalprovince_value="",
            postalcity_value="",postalsuburb_value="";
    public SpinnerObj[] CountryType;
    public SpinnerObj[] ProvinceType;
    public SpinnerObj[] CityType;
    public SpinnerObj[] SuburbsType;
    public SpinnerObj[] MunicipalityType;
    ArrayList<String> municipality_list = new ArrayList<>();
    public SpinnerObj[] SchoolEMIType;
    ArrayList<String> SchoolEMI_list = new ArrayList<>();
    LinearLayout postalspinnersLayout,postaledittextLayouts;
    ArrayList<Step3DataVisibilityObj> Step3ArrayList = new ArrayList<>();
    LinearLayout postalcodeContainer,postaladd1Container,postaladd2Container,postaladd3Container,postalmunicipalityContainer,
            postalurbanrural,postalcountry,postalprovince,postalcity,postalsuburb,postalprovinceContainer,
            etpostalcityContainer,etpostalsuburubContainer,schoolemisContainer,lastscyearContainer,equity,
            residentstatus;
    boolean validPostalCode,validPostalAdd1,validPostalAdd2,validPostalAdd3,validPostalMuni,validPostalUrban,
            validPostalCountry,validPostalProvinceSP,validPostalCitySP,validPostalSuburbSP,validPostalProvince,
            validPostalCity,validPostalSuburb,validLastScEMI,validLastScYear,validEquity,validCRStatus;


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
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        Step3ArrayList = (ArrayList<Step3DataVisibilityObj>) args.getSerializable("Step3ArrayList");
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
            fetchMunicipality();
            fetchLastSchoolEMI();
            fetchData();
            callDataApi();
            initializeListeners();
            printLogs(LogTag, "bootStrapInit", "exitConnected");
        }
    }



    @Override
    protected void setLayoutXml() {
        printLogs(LogTag, "setLayoutXml", "edit_profile_steptwo");
        setContentView(R.layout.a_edit_profile_step_two_b);
    }

    @Override
    protected void initializeViews() {
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        heading = findViewById(R.id.heading);
        btnUpdate = findViewById(R.id.btnUpdate);


        postaledittextLayouts = findViewById(R.id.postaledittextLayouts);
        postalspinnersLayout = findViewById(R.id.postalspinnersLayout);
        txtschoolemis = findViewById(R.id.txtschoolemis);
        txtpostalmunicipality = findViewById(R.id.txtpostalmunicipality);
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
        inputSpinnerpostalurbanrural = findViewById(R.id.inputSpinnerpostalurbanrural);
        inputSpinnerequity = findViewById(R.id.inputSpinnerequity);
        inputSpinnerresidentstatus = findViewById(R.id.inputSpinnerresidentstatus);
        inputSpinnerpostalcountry = findViewById(R.id.inputSpinnerpostalcountry);
        inputSpinnerpostalprovince = findViewById(R.id.inputSpinnerpostalprovince);
        inputSpinnerpostalcity = findViewById(R.id.inputSpinnerpostalcity);
        inputSpinnerpostalsuburb = findViewById(R.id.inputSpinnerpostalsuburb);
        inputpostalcode = findViewById(R.id.inputpostalcode);
        inputpostaladdress1 = findViewById(R.id.inputpostaladdress1);
        inputpostaladdress2 = findViewById(R.id.inputpostaladdress2);
        inputpostaladdress3 = findViewById(R.id.inputpostaladdress3);
        inputetpostalprovince = findViewById(R.id.inputetpostalprovince);
        inputetpostalcity = findViewById(R.id.inputetpostalcity);
        inputetpostalsuburub = findViewById(R.id.inputetpostalsuburub);
        inputlastscyear = findViewById(R.id.inputlastscyear);

        postalcodeContainer         = findViewById(R.id.postalcodeContainer        );
        postaladd1Container         = findViewById(R.id.postaladd1Container        );
        postaladd2Container         = findViewById(R.id.postaladd2Container        );
        postaladd3Container         = findViewById(R.id.postaladd3Container        );
        postalmunicipalityContainer = findViewById(R.id.postalmunicipalityContainer);
        postalurbanrural            = findViewById(R.id.postalurbanrural           );
        postalcountry               = findViewById(R.id.postalcountry              );
        postalprovince              = findViewById(R.id.postalprovince             );
        postalcity                  = findViewById(R.id.postalcity                 );
        postalsuburb                = findViewById(R.id.postalsuburb               );
        postalprovinceContainer     = findViewById(R.id.postalprovinceContainer    );
        etpostalcityContainer       = findViewById(R.id.etpostalcityContainer      );
        etpostalsuburubContainer    = findViewById(R.id.etpostalsuburubContainer   );
        schoolemisContainer         = findViewById(R.id.schoolemisContainer        );
        lastscyearContainer         = findViewById(R.id.lastscyearContainer        );
        equity                      = findViewById(R.id.equity                     );
        residentstatus              = findViewById(R.id.residentstatus             );



    }

    @Override
    protected void initializeListeners() {

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });


        /*Postal urban rural spinner*/

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

                spin_equity = String.valueOf(parent.getSelectedItemId());
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

                spin_residentstatus = String.valueOf(parent.getSelectedItemId());
                printLogs(LogTag, "initializeListeners", "residentstatus_value" + residentstatus_value);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void initializeInputs() {

        for (int i = 0; i <Step3ArrayList.size() ; i++) {

            if(Step3ArrayList.get(i).getPostalCode_is_v_3().equals("1")){
                postalcodeContainer.setVisibility(View.VISIBLE);
            }else{
                postalcodeContainer.setVisibility(View.GONE);
            }
            if(Step3ArrayList.get(i).getPostalAdd1_is_v_3().equals("1")){
                postaladd1Container.setVisibility(View.VISIBLE);
            }else{
                postaladd1Container.setVisibility(View.GONE);
            }
            if(Step3ArrayList.get(i).getPostalAdd2_is_v_3().equals("1")){
                postaladd2Container.setVisibility(View.VISIBLE);
            }else{
                postaladd2Container.setVisibility(View.GONE);
            }
            if(Step3ArrayList.get(i).getPostalAdd3_is_v_3().equals("1")){
                postaladd3Container.setVisibility(View.VISIBLE);
            }else{
                postaladd3Container.setVisibility(View.GONE);
            }
            if(Step3ArrayList.get(i).getPostalMuni_is_v_3().equals("1")){
                postalmunicipalityContainer.setVisibility(View.VISIBLE);
            }else{
                postalmunicipalityContainer.setVisibility(View.GONE);
            }
            if(Step3ArrayList.get(i).getPostalUrban_is_v_3().equals("1")){
                postalurbanrural.setVisibility(View.VISIBLE);
            }else{
                postalurbanrural.setVisibility(View.GONE);
            }
            if(Step3ArrayList.get(i).getPostalCountry_is_v_3().equals("1")){
                postalcountry.setVisibility(View.VISIBLE);
            }else{
                postalcountry.setVisibility(View.GONE);
            }
            if(Step3ArrayList.get(i).getPostalProvinceSP_is_v_3().equals("1")){
                postalprovince.setVisibility(View.VISIBLE);
            }else{
                postalprovince.setVisibility(View.GONE);
            }
            if(Step3ArrayList.get(i).getPostalCitySP_is_v_3().equals("1")){
                postalcity.setVisibility(View.VISIBLE);
            }else{
                postalcity.setVisibility(View.GONE);
            }
            if(Step3ArrayList.get(i).getPostalSuburbSP_is_v_3().equals("1")){
                postalsuburb.setVisibility(View.VISIBLE);
            }else{
                postalsuburb.setVisibility(View.GONE);
            }
            if(Step3ArrayList.get(i).getPostalProvince_is_v_3().equals("1")){
                postalprovinceContainer.setVisibility(View.VISIBLE);
            }else{
                postalprovinceContainer.setVisibility(View.GONE);
            }
            if(Step3ArrayList.get(i).getPostalCity_is_v_3().equals("1")){
                etpostalcityContainer.setVisibility(View.VISIBLE);
            }else{
                etpostalcityContainer.setVisibility(View.GONE);
            }
            if(Step3ArrayList.get(i).getPostalSuburb_is_v_3().equals("1")){
                etpostalsuburubContainer.setVisibility(View.VISIBLE);
            }else{
                etpostalsuburubContainer.setVisibility(View.GONE);
            }
            if(Step3ArrayList.get(i).getPostalScEMI_is_v_3().equals("1")){
                schoolemisContainer.setVisibility(View.VISIBLE);
            }else{
                schoolemisContainer.setVisibility(View.GONE);
            }
            if(Step3ArrayList.get(i).getPostalScYear_is_v_3().equals("1")){
                lastscyearContainer.setVisibility(View.VISIBLE);
            }else{
                lastscyearContainer.setVisibility(View.GONE);
            }
            if(Step3ArrayList.get(i).getPostalEquity_is_v_3().equals("1")){
                equity.setVisibility(View.VISIBLE);
            }else{
                equity.setVisibility(View.GONE);
            }
            if(Step3ArrayList.get(i).getPostalCRStatus_is_v_3().equals("1")){
                residentstatus.setVisibility(View.VISIBLE);
            }else{
                residentstatus.setVisibility(View.GONE);
            }
////=====================Required=====================================================

            if(Step3ArrayList.get(i).getPostalCode_is_r_3().equals("1")){
                validPostalCode = true;
            }else{
                validPostalCode = false;
            }
            if(Step3ArrayList.get(i).getPostalAdd1_is_r_3().equals("1")){
                validPostalAdd1 = true;
            }else{
                validPostalAdd1 = false;
            }
            if(Step3ArrayList.get(i).getPostalAdd2_is_r_3().equals("1")){
                validPostalAdd2 = true;
            }else{
                validPostalAdd2 = false;
            }
            if(Step3ArrayList.get(i).getPostalAdd3_is_r_3().equals("1")){
                validPostalAdd3 = true;
            }else{
                validPostalAdd3 = false;
            }
            if(Step3ArrayList.get(i).getPostalMuni_is_r_3().equals("1")){
                validPostalMuni = true;
            }else{
                validPostalMuni = false;
            }
            if(Step3ArrayList.get(i).getPostalUrban_is_r_3().equals("1")){
                validPostalUrban = true;
            }else{
                validPostalUrban = false;
            }
            if(Step3ArrayList.get(i).getPostalCountry_is_r_3().equals("1")){
                validPostalCountry = true;
            }else{
                validPostalCountry = false;
            }
            if(Step3ArrayList.get(i).getPostalProvinceSP_is_r_3().equals("1")){
                validPostalProvinceSP = true;
            }else{
                validPostalProvinceSP = false;
            }
            if(Step3ArrayList.get(i).getPostalCitySP_is_r_3().equals("1")){
                validPostalCitySP = true;
            }else{
                validPostalCitySP = false;
            }
            if(Step3ArrayList.get(i).getPostalSuburbSP_is_r_3().equals("1")){
                validPostalSuburbSP = true;
            }else{
                validPostalSuburbSP = false;
            }
            if(Step3ArrayList.get(i).getPostalProvince_is_r_3().equals("1")){
                validPostalProvince = true;
            }else{
                validPostalProvince = false;
            }
            if(Step3ArrayList.get(i).getPostalCity_is_r_3().equals("1")){
                validPostalCity = true;
            }else{
                validPostalCity = false;
            }
            if(Step3ArrayList.get(i).getPostalSuburb_is_r_3().equals("1")){
                validPostalSuburb = true;
            }else{
                validPostalSuburb = false;
            }
            if(Step3ArrayList.get(i).getPostalScEMI_is_r_3().equals("1")){
                validLastScEMI = true;
            }else{
                validLastScEMI = false;
            }
            if(Step3ArrayList.get(i).getPostalScYear_is_r_3().equals("1")){
                validLastScYear = true;
            }else{
                validLastScYear = false;
            }
            if(Step3ArrayList.get(i).getPostalEquity_is_r_3().equals("1")){
                validEquity = true;
            }else{
                validEquity = false;
            }
            if(Step3ArrayList.get(i).getPostalCRStatus_is_r_3().equals("1")){
                validCRStatus = true;
            }else{
                validCRStatus = false;
            }
        }
    }

    @Override
    protected void initializeLabels() {
        /*Postal layouts labels*/

       String Label = getLabelFromDb("lbl_S105_postalcode", R.string.lbl_S105_postalcode);
        lblView =findViewById(R.id.lblpostalcode);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S105_postaladdress1", R.string.lbl_S105_postaladdress1);
        lblView =  findViewById(R.id.lblpostaladdress1);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_postaladdress2", R.string.lbl_S105_postaladdress2);
        lblView =  findViewById(R.id.lblpostaladdress2);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_postaladdress3", R.string.lbl_S105_postaladdress3);
        lblView =  findViewById(R.id.lblpostaladdress3);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_postalmunicipality", R.string.lbl_S105_postalmunicipality);
        lblView =  findViewById(R.id.lblpostalmunicipality);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_postalurbanrural", R.string.lbl_S105_postalurbanrural);
        lblView =  findViewById(R.id.lblpostalurbanrural);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_postalcountry", R.string.lbl_S105_postalcountry);
        lblView =  findViewById(R.id.lblpostalcountry);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_postalprovince", R.string.lbl_S105_postalprovince);
        lblView =  findViewById(R.id.lblpostalprovince);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_postalcity", R.string.lbl_S105_postalcity);
        lblView =  findViewById(R.id.lblpostalcity);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_postalsuburb", R.string.lbl_S105_postalsuburb);
        lblView =  findViewById(R.id.lblpostalsuburb);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_postalprovince", R.string.lbl_S105_postalprovince);
        lblView =  findViewById(R.id.lbletpostalprovince);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_postalcity", R.string.lbl_S105_postalcity);
        lblView =  findViewById(R.id.lbletpostalcity);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_postalsuburb", R.string.lbl_S105_postalsuburb);
        lblView =  findViewById(R.id.lbletpostalsuburb);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_schoolemis", R.string.lbl_S105_schoolemis);
        lblView =  findViewById(R.id.lblschoolemis);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_lastscyear", R.string.lbl_S105_lastscyear);
        lblView =  findViewById(R.id.lbllastscyear);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_lblequity", R.string.lbl_S105_lblequity);
        lblView =  findViewById(R.id.lblequity);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_S105_residentstatus", R.string.lbl_S105_residentstatus);
        lblView =  findViewById(R.id.lblresidentstatus);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);


        Label = getLabelFromDb("b_S105_save", R.string.b_S105_save);
        btnUpdate.setText(Label);

        Label = getLabelFromDb("lbl_S105BA_heading", R.string.lbl_S105BA_heading);
        lblView =  findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            btnUpdate.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            inputpostalcode.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputpostaladdress1.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputpostaladdress2.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputpostaladdress3.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputetpostalprovince.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputetpostalcity.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputetpostalsuburub.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputlastscyear.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            txtpostalmunicipality.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            txtschoolemis.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputSpinnerpostalurbanrural.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            inputSpinnerpostalcountry.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            inputSpinnerpostalprovince.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            inputSpinnerpostalcity.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            inputSpinnerpostalsuburb.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
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
                        JSONArray jsonArray = jsonObject.getJSONArray(KEY_DATA);
                        for (int i = 0; i <jsonArray.length() ; i++) {
                            JSONObject dataM = jsonArray.getJSONObject(i);
                            /*postal data*/
                            inputpostalcode.setText(dataM.getString("s_d_o_postal_code"));
                            inputpostaladdress1.setText(dataM.getString("s_d_o_postal_add_line_one"));
                            inputpostaladdress2.setText(dataM.getString("s_d_o_postal_add_line_two"));
                            inputpostaladdress3.setText(dataM.getString("s_d_o_postal_add_line_three"));
                            postalmunicipality_value = dataM.getString("s_d_o_postal_municipality");
                            inputetpostalprovince.setText(dataM.getString("s_d_o_postal_province_name"));
                            inputetpostalcity.setText(dataM.getString("s_d_o_postal_city_name"));
                            inputetpostalsuburub.setText(dataM.getString("s_d_o_postal_suburb_name"));
                            schoolEMI_value = dataM.getString("s_d_o_last_school_emi");
                            inputlastscyear.setText(dataM.getString("s_d_o_last_school_year"));
                            postalurbanrual_value = dataM.getString("s_d_o_postal_urban_rural");
                            postalcountry_value = dataM.getString("s_d_o_postal_country");
                            postalprovince_value = dataM.getString("s_d_o_postal_province");
                            postalcity_value = dataM.getString("s_d_o_postal_city");
                            postalsuburb_value = dataM.getString("s_d_o_postal_suburb");
                            spin_residentstatus = dataM.getString("s_d_o_citizen_resident");
                            spin_equity = dataM.getString("s_d_o_equity");


                        }
                        if(!spin_equity.equals("")){
                            inputSpinnerequity.setSelection(Integer.parseInt(spin_equity));
                        }
                        if(!spin_residentstatus.equals("")){
                            inputSpinnerresidentstatus.setSelection(Integer.parseInt(spin_residentstatus));
                        }
                        if(!postalurbanrual_value.equals("")){
                            inputSpinnerpostalurbanrural.setSelection(Integer.parseInt(postalurbanrual_value));
                        }
                        fetchPostalCountry();

                        fetchMunicipality();
                        fetchLastSchoolEMI();



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
                        SpinAdapter adapter = new SpinAdapter(SEditProfileStepTwoBA.this, android.R.layout.simple_spinner_item, CountryType);
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
                        SpinAdapter adapter = new SpinAdapter(SEditProfileStepTwoBA.this, android.R.layout.simple_spinner_item, ProvinceType);
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
                        SpinAdapter adapter = new SpinAdapter(SEditProfileStepTwoBA.this, android.R.layout.simple_spinner_item, CityType);
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
                        SpinAdapter adapter = new SpinAdapter(SEditProfileStepTwoBA.this, android.R.layout.simple_spinner_item, SuburbsType);
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

                        AutoCompleteAdapter autoCompleteAdapter = new AutoCompleteAdapter(SEditProfileStepTwoBA.this,android.R.layout.simple_spinner_item,android.R.id.text1,SchoolEMI_list);
                        txtschoolemis.setAdapter(autoCompleteAdapter);
                        if(!schoolEMI_value.equals("")){
                            for (int j = 0; j <SchoolEMIType.length ; j++) {
                                if(SchoolEMIType[j].getId().equals(schoolEMI_value)){
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
                        showProgress(false, mContentView, mProgressView);
                    } else if(Status.equals("2")) {
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
                        AutoCompleteAdapter autoCompleteAdapter = new AutoCompleteAdapter(SEditProfileStepTwoBA.this,android.R.layout.simple_spinner_item,android.R.id.text1,municipality_list);
                        txtpostalmunicipality.setAdapter(autoCompleteAdapter);
                        if(!postalmunicipality_value.equals("")){
                            for (int j = 0; j <MunicipalityType.length ; j++) {
                                if(MunicipalityType[j].getId().equals(postalmunicipality_value)){
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
            Intent intent = new Intent(SEditProfileStepTwoBA.this, AppUpdatedA.class);
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
        Intent intent = new Intent(SEditProfileStepTwoBA.this, SEditProfileMainA.class);
        printLogs(LogTag, "onOptionsItemSelected", "SEditProfileStepTwoA");
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        printLogs(LogTag, "onOptionsItemSelected", "init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(SEditProfileStepTwoBA.this, SEditProfileMainA.class);
            printLogs(LogTag, "onOptionsItemSelected", "SEditProfileStepTwoA");
            startActivity(intent);
            finish();
        }
        return true;
    }




    @Override
    public void validateForm() {
        boolean cancel = false;

        if(validPostalUrban){
            if (!validateSpinnerPostalUrban(inputSpinnerpostalurbanrural)) {
                cancel = true;
            }
        }
        if(validPostalCountry){
            if (!validateSpinnerCountry(inputSpinnerpostalcountry)) {
                cancel = true;
            }
        }
        if(validPostalProvinceSP){
            if (!validateSpinnerProvinceSP(inputSpinnerpostalprovince)) {
                cancel = true;
            }
        }
        if(validPostalCitySP){
            if (!validateSpinnerPostalCitySP(inputSpinnerpostalcity)) {
                cancel = true;
            }
        }
        if(validPostalSuburbSP){
            if (!validateSpinnerPostalSuburbSP(inputSpinnerpostalsuburb)) {
                cancel = true;
            }
        }
        if(validEquity){
            if (!validateSpinnerEquity(inputSpinnerequity)) {
                cancel = true;
            }
        }
        if(validCRStatus){
            if (!validateSpinnerCRStatus(inputSpinnerresidentstatus)) {
                cancel = true;
            }
        }
        if(validPostalCode){
                if (!validatePostalCode(inputpostalcode, inputLayoutpostalcode)) {
                    cancel = true;
                }
        }
        if(validPostalAdd1){
            if (!validatePostalAdd1(inputpostaladdress1, inputLayoutpostaladdress1)) {
                cancel = true;
            }
        }
        if(validPostalAdd2){
            if (!validatePostalAdd2(inputpostaladdress2, inputLayoutpostaladdress2)) {
                cancel = true;
            }
        }
        if(validPostalAdd3){
            if (!validatePostalAdd3(inputpostaladdress3, inputLayoutpostaladdress3)) {
                cancel = true;
            }
        }
        if(validPostalMuni){
            if (!validatePostalMuni(txtpostalmunicipality, inputLayoutpostalmunicipality)) {
                cancel = true;
            }
        }
        if(validPostalProvince){
            if (!validatePostalProvince(inputetpostalprovince, inputLayoutetpostalprovince)) {
                cancel = true;
            }
        }
        if(validPostalCity){
            if (!validatePostalCity(inputetpostalcity, inputLayoutetpostalcity)) {
                cancel = true;
            }
        }
        if(validPostalSuburb){
            if (!validatePostalSuburb(inputetpostalsuburub, inputLayoutetpostalsuburub)) {
                cancel = true;
            }
        }
        if(validLastScEMI){
            if (!validateLastScEMI(txtschoolemis, inputLayoutschoolemis)) {
                cancel = true;
            }
        }
        if(validLastScYear){
            if (!validateLastScYear(inputlastscyear, inputLayoutlastscyear)) {
                cancel = true;
            }
        }
        if (cancel) {
            return;
        } else {
            showProgress(true, mContentView, mProgressView);
            this.FormSubmit();
        }
        printLogs(LogTag, "validateForm", "exit");
    }

    private boolean validateLastScYear(EditText inputlastscyear, TextInputLayout inputLayoutlastscyear) {
        String lastscyear = inputlastscyear.getText().toString().trim();
        setCustomError(inputLayoutlastscyear, null, inputlastscyear);
        if (lastscyear.isEmpty()) {
            String sMessage = getLabelFromDb("error_S105_emptyscyear", R.string.error_S105_emptyscyear);
            setCustomError(inputLayoutlastscyear, sMessage, inputlastscyear);
            return false;
        }else if(!isValidYear(lastscyear)){
            String sMessage = getLabelFromDb("error_invalid_scyear", R.string.error_invalid_scyear);
            setCustomError(inputLayoutlastscyear, sMessage, inputlastscyear);
            return false;

        }else {
            setCustomErrorDisabled(inputLayoutlastscyear, inputlastscyear);
            return true;
        }
    }

    private boolean validateLastScEMI(AutoCompleteTextView txtschoolemis, TextInputLayout inputLayoutschoolemis) {
        String schoolemis = txtschoolemis.getText().toString().trim();
        setCustomError(inputLayoutschoolemis, null, txtschoolemis);
        if (schoolemis.isEmpty()) {
            String sMessage = getLabelFromDb("error_S105_empty", R.string.error_S105_empty);
            setCustomError(inputLayoutschoolemis, sMessage, txtschoolemis);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutschoolemis, txtschoolemis);
            return true;
        }
    }

    private boolean validatePostalSuburb(EditText inputetpostalsuburub, TextInputLayout inputLayoutetpostalsuburub) {
        String postalsuburub = inputetpostalsuburub.getText().toString().trim();
        setCustomError(inputLayoutetpostalsuburub, null, inputetpostalsuburub);
        if (postalsuburub.isEmpty()) {
            String sMessage = getLabelFromDb("error_S105_empty", R.string.error_S105_empty);
            setCustomError(inputLayoutetpostalsuburub, sMessage, inputetpostalsuburub);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutetpostalsuburub, inputetpostalsuburub);
            return true;
        }
    }

    private boolean validatePostalCity(EditText inputetpostalcity, TextInputLayout inputLayoutetpostalcity) {
        String postalcity = inputetpostalcity.getText().toString().trim();
        setCustomError(inputLayoutetpostalcity, null, inputetpostalcity);
        if (postalcity.isEmpty()) {
            String sMessage = getLabelFromDb("error_S105_empty", R.string.error_S105_empty);
            setCustomError(inputLayoutetpostalcity, sMessage, inputetpostalcity);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutetpostalcity, inputetpostalcity);
            return true;
        }
    }

    private boolean validatePostalProvince(EditText inputetpostalprovince, TextInputLayout inputLayoutetpostalprovince) {
        String postalprovince = inputetpostalprovince.getText().toString().trim();
        setCustomError(inputLayoutetpostalprovince, null, inputetpostalprovince);
        if (postalprovince.isEmpty()) {
            String sMessage = getLabelFromDb("error_S105_empty", R.string.error_S105_empty);
            setCustomError(inputLayoutetpostalprovince, sMessage, inputetpostalprovince);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutetpostalprovince, inputetpostalprovince);
            return true;
        }
    }

    private boolean validatePostalMuni(AutoCompleteTextView txtpostalmunicipality, TextInputLayout inputLayoutpostalmunicipality) {
        String postalmunicipality = txtpostalmunicipality.getText().toString().trim();
        setCustomError(inputLayoutpostalmunicipality, null, txtpostalmunicipality);
        if (postalmunicipality.isEmpty()) {
            String sMessage = getLabelFromDb("error_S105_empty", R.string.error_S105_empty);
            setCustomError(inputLayoutpostalmunicipality, sMessage, txtpostalmunicipality);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutpostalmunicipality, txtpostalmunicipality);
            return true;
        }
    }

    private boolean validatePostalAdd3(EditText inputpostaladdress3, TextInputLayout inputLayoutpostaladdress3) {
        String address3 = inputpostaladdress3.getText().toString().trim();
        setCustomError(inputLayoutpostaladdress3, null, inputpostaladdress3);
        if (address3.isEmpty()) {
            String sMessage = getLabelFromDb("error_S105_empty", R.string.error_S105_empty);
            setCustomError(inputLayoutpostaladdress3, sMessage, inputpostaladdress3);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutpostaladdress3, inputpostaladdress3);
            return true;
        }
    }
private boolean validatePostalAdd2(EditText inputpostaladdress2, TextInputLayout inputLayoutpostaladdress2) {
        String address2 = inputpostaladdress2.getText().toString().trim();
        setCustomError(inputLayoutpostaladdress2, null, inputpostaladdress2);
        if (address2.isEmpty()) {
            String sMessage = getLabelFromDb("error_S105_empty", R.string.error_S105_empty);
            setCustomError(inputLayoutpostaladdress2, sMessage, inputpostaladdress2);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutpostaladdress2, inputpostaladdress2);
            return true;
        }
    }
    private boolean validatePostalAdd1(EditText inputpostaladdress1, TextInputLayout inputLayoutpostaladdress1) {
        String address1 = inputpostaladdress1.getText().toString().trim();
        setCustomError(inputLayoutpostaladdress1, null, inputpostaladdress1);
        if (address1.isEmpty()) {
            String sMessage = getLabelFromDb("error_S105_empty", R.string.error_S105_empty);
            setCustomError(inputLayoutpostaladdress1, sMessage, inputpostaladdress1);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutpostaladdress1, inputpostaladdress1);
            return true;
        }
    }

    private boolean validatePostalCode(EditText inputpostalcode, TextInputLayout inputLayoutpostalcode) {
        String postalcode = inputpostalcode.getText().toString().trim();
        setCustomError(inputLayoutpostalcode, null, inputpostalcode);
        if (postalcode.isEmpty()) {
            String sMessage = getLabelFromDb("error_S105_emptypostalcode", R.string.error_S105_emptypostalcode);
            setCustomError(inputLayoutpostalcode, sMessage, inputpostalcode);
            return false;
        }else if(!isValidPhyCode(postalcode)){
            String sMessage = getLabelFromDb("error_invalid_postalcode", R.string.error_invalid_postalcode);
            setCustomError(inputLayoutpostalcode, sMessage, inputpostalcode);
            return false;

        } else {
            setCustomErrorDisabled(inputLayoutpostalcode, inputpostalcode);
            return true;
        }
    }

    private boolean validateSpinnerCRStatus(Spinner inputSpinnerresidentstatus) {
        printLogs(LogTag,"validateSpinnerCRStatus","spin_residentstatus : "+spin_residentstatus);
        if (spin_residentstatus.isEmpty() || spin_residentstatus.equals("0")) {
            String sTitle = "";
            String sMessage = getLabelFromDb("lbl_S105D_errorspresstatus", R.string.lbl_S105D_errorspresstatus);
            String sButtonLabelClose = "Close";
            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);

            return false;
        } else {
            return true;
        }
    }

    private boolean validateSpinnerEquity(Spinner inputSpinnerequity) {
        printLogs(LogTag,"validateSpinnerEquity","spin_equity : "+spin_equity);
        if (spin_equity.isEmpty() || spin_equity.equals("0")) {
            String sTitle = "";
            String sMessage = getLabelFromDb("lbl_S105D_errorspequity", R.string.lbl_S105D_errorspequity);
            String sButtonLabelClose = "Close";
            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);

            return false;
        } else {
            return true;
        }
    }

    private boolean validateSpinnerPostalSuburbSP(Spinner inputSpinnerpostalsuburb) {
        printLogs(LogTag,"validateSpinnerPostalSuburbSP","spin_postalsuburb : "+spin_postalsuburb);
        if (spin_postalsuburb.isEmpty() || spin_postalsuburb.equals("0")) {
            String sTitle = "";
            String sMessage = getLabelFromDb("lbl_S105D_errorsppossuburb", R.string.lbl_S105D_errorsppossuburb);
            String sButtonLabelClose = "Close";
            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);

            return false;
        } else {
            return true;
        }
    }

    private boolean validateSpinnerPostalCitySP(Spinner inputSpinnerpostalcity) {
        printLogs(LogTag,"validateSpinnerPostalCitySP","spin_postalcity : "+spin_postalcity);
        if (spin_postalcity.isEmpty() || spin_postalcity.equals("0")) {
            String sTitle = "";
            String sMessage = getLabelFromDb("lbl_S105D_errorspposcity", R.string.lbl_S105D_errorspposcity);
            String sButtonLabelClose = "Close";
            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);

            return false;
        } else {
            return true;
        }
    }

    private boolean validateSpinnerProvinceSP(Spinner inputSpinnerpostalprovince) {
        printLogs(LogTag,"validateSpinnerProvinceSP","spin_postalprovince : "+spin_postalprovince);
        if (spin_postalprovince.isEmpty() || spin_postalprovince.equals("0")) {
            String sTitle = "";
            String sMessage = getLabelFromDb("lbl_S105D_errorspposprovince", R.string.lbl_S105D_errorspposprovince);
            String sButtonLabelClose = "Close";
            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);

            return false;
        } else {
            return true;
        }
    }

    private boolean validateSpinnerCountry(Spinner inputSpinnerpostalcountry) {
        printLogs(LogTag,"validateSpinnerCountry","spin_postalcountry : "+spin_postalcountry);
        if (spin_postalcountry.isEmpty() || spin_postalcountry.equals("0")) {
            String sTitle = "";
            String sMessage = getLabelFromDb("lbl_S105D_errorspposcountry", R.string.lbl_S105D_errorspposcountry);
            String sButtonLabelClose = "Close";
            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);

            return false;
        } else {
            return true;
        }
    }

    private boolean validateSpinnerPostalUrban(Spinner inputSpinnerpostalurbanrural) {
            printLogs(LogTag,"validateSpinnerPostalUrban","postalurbanrural_value : "+postalurbanrural_value);
            if (postalurbanrural_value.isEmpty() || postalurbanrural_value.equals("0")) {
                String sTitle = "";
                String sMessage = getLabelFromDb("lbl_S105D_errorspposurban", R.string.lbl_S105D_errorspposurban);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);

                return false;
            } else {
                return true;
            }
    }

    @Override
    public void FormSubmit() {

        final String postalcode = inputpostalcode.getText().toString().trim();
        final String postaladdress1 = inputpostaladdress1.getText().toString().trim();
        final String postaladdress2 = inputpostaladdress2.getText().toString().trim();
        final String postaladdress3 = inputpostaladdress3.getText().toString().trim();
        final String postalprovince = inputetpostalprovince.getText().toString().trim();
        final String postalcity = inputetpostalcity.getText().toString().trim();
        final String postalsuburub = inputetpostalsuburub.getText().toString().trim();
        final String lastschoolyear = inputlastscyear.getText().toString().trim();

        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.UPDATEDETAILS_105_BB;
        printLogs(LogTag, "FormSubmit", "URL : " + FINAL_URL);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
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
                        ErrorDialog.showSuccessDialogSEditProfileTwoB(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose, thisClass);
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