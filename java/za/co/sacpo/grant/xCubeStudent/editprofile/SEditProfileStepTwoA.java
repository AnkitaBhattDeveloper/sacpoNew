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
import za.co.sacpo.grant.xCubeLib.dataObj.Step1DataVisibilityObj;
import za.co.sacpo.grant.xCubeLib.dataObj.Step2DataVisibilityObj;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;

public class SEditProfileStepTwoA extends BaseFormAPCPrivate {
    private String ActivityId = "S105B";
    public View mProgressView, mContentView,heading;
    private TextView lblView;
    SEditProfileStepTwoA thisClass;
    String phyurbanrural_value,spin_homelanguage,municipality_value="",homelanguage_value="";
    public EditText inputtelephone,inputfax,inputphysicalcode, inputphyaddress1, inputphyaddress2,
            inputphyaddress3, inputphyprovince, inputphycity, inputphysuburub;
    public TextInputLayout inputLayouttelephone, inputLayoutfax, inputLayoutphysicalcode,inputLayoutphyaddress1,
            inputLayoutphyaddress2, inputLayoutphyaddress3, inputLayoutmunicipality,inputLayoutphyprovince,
            inputLayoutphycity, inputLayoutphysuburub;
    public Button btnUpdate;
    private Spinner inputSpinnerurbanrural, inputSpinnerhomelanguage, inputSpinnercountry,inputSpinnersuburb,
            inputSpinnerprovince,inputSpinnercity;
    AutoCompleteTextView txtmunicipality;
    String spin_country,spin_province,spin_city,spin_suburb;
    String phyUrbanrual="",phyCountry="",phyProvince="",
            phyCity="",phySuburb="";

    public SpinnerObj[] LanguageType;
    public SpinnerObj[] CountryType;
    public SpinnerObj[] ProvinceType;
    public SpinnerObj[] CityType;
    public SpinnerObj[] SuburbsType;
    public SpinnerObj[] MunicipalityType;
    ArrayList<String> municipality_list = new ArrayList<>();
    LinearLayout spinnersLayout,edittextLayouts;
    ArrayList<Step2DataVisibilityObj> Step2ArrayList = new ArrayList<>();
    LinearLayout homelanguage,telephoneContainer,faxContainer,physicalcodeContainer,physicaladd1Container,
            physicaladd2Container,physicaladd3Container,municipalityContainer,urbanrural,country,
            province,city,suburb,phyprovinceContainer,phycityContainer,physuburubContainer;


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
        Step2ArrayList = (ArrayList<Step2DataVisibilityObj>) args.getSerializable("Step2ArrayList");
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
            fetchCountry(phyCountry,phyProvince,phyCity,phySuburb);
            fetchLanguage();
            fetchMunicipality();
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
       spinnersLayout = findViewById(R.id.spinnersLayout);
        edittextLayouts = findViewById(R.id.edittextLayouts);

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

        /*Spinners*/
        inputSpinnerurbanrural = findViewById(R.id.inputSpinnerurbanrural);
     inputSpinnerhomelanguage = findViewById(R.id.inputSpinnerhomelanguage);
        inputSpinnercountry = findViewById(R.id.inputSpinnercountry);
        inputSpinnerprovince = findViewById(R.id.inputSpinnerprovince);
        inputSpinnercity = findViewById(R.id.inputSpinnercity);
        inputSpinnersuburb = findViewById(R.id.inputSpinnersuburb);

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


       homelanguage           = findViewById(R.id.homelanguage          );
       telephoneContainer     = findViewById(R.id.telephoneContainer    );
       faxContainer           = findViewById(R.id.faxContainer          );
       physicalcodeContainer  = findViewById(R.id.physicalcodeContainer );
       physicaladd1Container  = findViewById(R.id.physicaladd1Container );
       physicaladd2Container  = findViewById(R.id.physicaladd2Container );
       physicaladd3Container  = findViewById(R.id.physicaladd3Container );
       municipalityContainer  = findViewById(R.id.municipalityContainer );
       urbanrural             = findViewById(R.id.urbanrural            );
       country                = findViewById(R.id.country               );
       province               = findViewById(R.id.province              );
       city                   = findViewById(R.id.city                  );
       suburb                 = findViewById(R.id.suburb                );
       phyprovinceContainer   = findViewById(R.id.phyprovinceContainer  );
       phycityContainer       = findViewById(R.id.phycityContainer      );
       physuburubContainer    = findViewById(R.id.physuburubContainer   );

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


    }

    @Override
    protected void initializeInputs() {


        for (int i = 0; i <Step2ArrayList.size() ; i++) {
            if(Step2ArrayList.get(i).getHomeLang_is_v_2().equals("1")){
                homelanguage.setVisibility(View.VISIBLE);
            }else{
                homelanguage.setVisibility(View.GONE);
            }
            if(Step2ArrayList.get(i).getTele_is_v_2().equals("1")){
                telephoneContainer.setVisibility(View.VISIBLE);
            }else{
                telephoneContainer.setVisibility(View.GONE);
            }
            if(Step2ArrayList.get(i).getFax_is_v_2().equals("1")){
                faxContainer.setVisibility(View.VISIBLE);
            }else{
                faxContainer.setVisibility(View.GONE);
            }
            if(Step2ArrayList.get(i).getPhyCode_is_v_2().equals("1")){
                physicalcodeContainer.setVisibility(View.VISIBLE);
            }else{
                physicalcodeContainer.setVisibility(View.GONE);
            }
            if(Step2ArrayList.get(i).getPhyAdd1_is_v_2().equals("1")){
                physicaladd1Container.setVisibility(View.VISIBLE);
            }else{
                physicaladd1Container.setVisibility(View.GONE);
            }
            if(Step2ArrayList.get(i).getPhyAdd2_is_v_2().equals("1")){
                physicaladd2Container.setVisibility(View.VISIBLE);
            }else{
                physicaladd2Container.setVisibility(View.GONE);
            }
            if(Step2ArrayList.get(i).getPhyAdd3_is_v_2().equals("1")){
                physicaladd3Container.setVisibility(View.VISIBLE);
            }else{
                physicaladd3Container.setVisibility(View.GONE);
            }
            if(Step2ArrayList.get(i).getPhyMuni_is_v_2().equals("1")){
                municipalityContainer.setVisibility(View.VISIBLE);
            }else{
                municipalityContainer.setVisibility(View.GONE);
            }
            if(Step2ArrayList.get(i).getPhyUrban_is_v_2().equals("1")){
                urbanrural.setVisibility(View.VISIBLE);
            }else{
                urbanrural.setVisibility(View.GONE);
            }
            if(Step2ArrayList.get(i).getCountry_is_v_2().equals("1")){
                country.setVisibility(View.VISIBLE);
            }else{
                country.setVisibility(View.GONE);
            }
            if(Step2ArrayList.get(i).getPhy_provinceSP_is_v_2().equals("1")){
                province.setVisibility(View.VISIBLE);
            }else{
                province.setVisibility(View.GONE);
            }
            if(Step2ArrayList.get(i).getPhy_citySP_is_v_2().equals("1")){
                city.setVisibility(View.VISIBLE);
            }else{
                city.setVisibility(View.GONE);
            }
            if(Step2ArrayList.get(i).getPhy_suburbSP_is_v_2().equals("1")){
                suburb.setVisibility(View.VISIBLE);
            }else{
                suburb.setVisibility(View.GONE);
            }
            if(Step2ArrayList.get(i).getPhy_province_is_v_2().equals("1")){
                phyprovinceContainer.setVisibility(View.VISIBLE);
            }else{
                phyprovinceContainer.setVisibility(View.GONE);
            }
            if(Step2ArrayList.get(i).getPhy_city_is_v_2().equals("1")){
                phycityContainer.setVisibility(View.VISIBLE);
            }else{
                phycityContainer.setVisibility(View.GONE);
            }
            if(Step2ArrayList.get(i).getPhy_suburb_is_v_2().equals("1")){
                physuburubContainer.setVisibility(View.VISIBLE);
            }else{
                physuburubContainer.setVisibility(View.GONE);
            }


        }


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


        Label = getLabelFromDb("b_S105_save", R.string.b_S105_save);
        btnUpdate.setText(Label);

        Label = getLabelFromDb("lbl_S105B_heading", R.string.lbl_S105B_heading);
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
            txtmunicipality.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputSpinnerhomelanguage.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            inputSpinnercountry.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            inputSpinnerprovince.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            inputSpinnercity.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            inputSpinnersuburb.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            inputSpinnerurbanrural.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
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
                            municipality_value = dataM.getString("s_d_o_phy_municipality");
                            phyUrbanrual= dataM.getString("s_d_o_is_urban_rural");
                            phyCountry = dataM.getString("s_d_o_country");
                            phyProvince = dataM.getString("s_d_o_physical_province");
                            phyCity = dataM.getString("s_d_o_physical_city");
                            phySuburb = dataM.getString("s_d_o_physical_suburb");

                        }
                        fetchCountry(phyCountry,phyProvince,phyCity,phySuburb);
                        fetchLanguage();
                        fetchMunicipality();
                        if(!phyUrbanrual.equals("")){
                            inputSpinnerurbanrural.setSelection(Integer.parseInt(phyUrbanrual));
                        }

                        showProgress(false, mContentView, mProgressView);
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
                        if(!municipality_value.equals("")){
                        for (int j = 0; j <MunicipalityType.length ; j++) {
                            if(MunicipalityType[j].getId().equals(municipality_value)){
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
            if (!validatetelephone(inputtelephone, inputLayouttelephone)) {
                cancel = true;
            } else if (!validatefax(inputfax, inputLayoutfax)) {
                cancel = true;
            } else if (!validatePhyCode(inputphysicalcode, inputLayoutphysicalcode)) {
                cancel = true;
            } else if (!validateAdd1(inputphyaddress1, inputLayoutphyaddress1)) {
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

    private boolean validateAdd1(EditText inputphyaddress1, TextInputLayout inputLayoutphyaddress1) {
        String fax = inputphyaddress1.getText().toString().trim();
        setCustomError(inputLayoutphyaddress1, null, inputphyaddress1);
        if (fax.isEmpty() || !isValidSDLNo(fax)) {
            String sMessage = getLabelFromDb("error_S105_phyadd1", R.string.error_S105_phyadd1);
            setCustomError(inputLayoutphyaddress1, sMessage, inputphyaddress1);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutphyaddress1, inputphyaddress1);
            return true;
        }
    }

    private boolean validatePhyCode(EditText inputphysicalcode, TextInputLayout inputLayoutphysicalcode) {
        String fax = inputphysicalcode.getText().toString().trim();
        setCustomError(inputLayoutphysicalcode, null, inputphysicalcode);
        if (fax.isEmpty() || !isValidSDLNo(fax)) {
            String sMessage = getLabelFromDb("error_S105_phycode", R.string.error_S105_phycode);
            setCustomError(inputLayoutphysicalcode, sMessage, inputphysicalcode);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutphysicalcode, inputphysicalcode);
            return true;
        }
    }

    private boolean validatefax(EditText inputfax, TextInputLayout inputLayoutfax) {
        String fax = inputfax.getText().toString().trim();
        setCustomError(inputLayoutfax, null, inputfax);
        if (fax.isEmpty() || !isValidSDLNo(fax)) {
            String sMessage = getLabelFromDb("error_S105_fax", R.string.error_S105_fax);
            setCustomError(inputLayoutfax, sMessage, inputfax);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutfax, inputfax);
            return true;
        }
    }

    private boolean validatetelephone(EditText inputtelephone, TextInputLayout inputLayouttelephone) {
        String phone = inputtelephone.getText().toString().trim();
        setCustomError(inputLayouttelephone, null, inputtelephone);
        if (phone.isEmpty() || !isValidMobile(phone)) {
            String sMessage = getLabelFromDb("error_S105_number", R.string.error_S105_number);
            setCustomError(inputLayouttelephone, sMessage, inputtelephone);
            return false;
        } else {
            setCustomErrorDisabled(inputLayouttelephone, inputtelephone);
            return true;
        }
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