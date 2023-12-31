package za.co.sacpo.grantportal.xCubeStudent.editprofile;

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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import za.co.sacpo.grantportal.AppUpdatedA;
import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.adapter.AutoCompleteAdapter;
import za.co.sacpo.grantportal.xCubeLib.adapter.SpinAdapter;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.component.Utils;
import za.co.sacpo.grantportal.xCubeLib.dataObj.SpinnerObj;
import za.co.sacpo.grantportal.xCubeLib.dataObj.Step4DataVisibilityObj;
import za.co.sacpo.grantportal.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;

public class SEDitProfileStepThreeA extends BaseFormAPCPrivate {
    private String ActivityId = "S105C";
    public View mProgressView, mContentView,heading;
    private TextView lblView;
    SEDitProfileStepThreeA thisClass;
    public Button btnUpdate;
    AutoCompleteTextView txtssareacode,txtofocode;
    public SpinnerObj[] AreaCodeType;
    public SpinnerObj[] OFOCodeType;
    ArrayList<String> AreaCode_list = new ArrayList<>();
    ArrayList<String> OFOCode_list = new ArrayList<>();
    String ssareacode_value="",popiactstatus_value,sponsorship_value,economic_value,enrollment_value
            ,wiltype_value,spin_actstatusdate,spin_actstatusmonth,spin_actstatusyear,spin_wilstartdate,spin_wilstartmonth,spin_wilstartyear,
            spin_wilenddate,spin_wilendmonth,spin_wilendyear,spin_msregdate,spin_msregmonth,spin_msregyear,
            spin_nqf,spin_financialyear,txtofocode_value="";
    Spinner inputSpinneractstatus,SpinnerDay,SpinnerMonth,SpinnerYear,inputSpinnersponsorship,
            inputSpinnerfinancialyear,inputSpinnernqflevel,inputSpinnereconomicstatus,SpinnerwilstartDay,
            SpinnerwilstartMonth,SpinnerwilstartYear,SpinnerwilendDay,SpinnerwilendMonth,SpinnerwilendYear,
            SpinnermsregDay,SpinnermsregMonth,SpinnermsregYear,inputSpinnerenrollmentstatus,
            inputSpinnerwiltype;
    EditText inputsaqaid,inputempsdlno,inputprovidersdlno,inputproject,inputrefno;
    public SpinnerObj[] NQFLevelType;
    public SpinnerObj[] FinYearType;
    TextInputLayout inputLayoutsaqaid,inputLayoutempsdlno,inputLayoutssareacode,inputLayoutofocode,
            inputLayoutprovidersdlno,inputLayoutproject,inputLayoutrefno;
    /*fetch data strings*/
    String statssaAreaCode="",popiActStatus="",popiActDate="",popiActMonth="",popiActYear="",
    Sponsorship="",FinancialYear="",NQFLevel="",EconomicStatus="",EnrollmentStatus="",WILType="",
    WILStartDate="",WILStartMonth="",WILStartYear="",WILEndDate="",WILEndMonth="",WILEndYear="",
            MRRDate="",MRRMonth="",MRRYear="";
    ArrayList<Step4DataVisibilityObj> Step4ArrayList = new ArrayList<>();
    LinearLayout ssareacodeContainer,actstatus,actstatusdate,ofocodeContainer,sponsorship,financialyear,
            nqflevel,saqaidContainer,empsdlnoContainer,providersdlnoContainer,economicstatus,wilstartdate,
            wilenddate,msregdate,enrollmentstatus,projectContainer,refnoContainer,wiltype;
    boolean vaildSSAAreaCode,validPopiActStatus,validPopiActDate,validOFoCode,validSponsorship,validFYear,
    validNQFLevel,validSAQAId,validEMPSDlNo,validPSDLNo,validEcoStatus,validWilStartDate,validWilEndDate,
    validMSRegDate,validEnrollStatus,validProject,validRefNo,validWilType;


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
        Step4ArrayList = (ArrayList<Step4DataVisibilityObj>) args.getSerializable("Step4ArrayList");
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
            fetchNQFLevels();
            fetchFinancialYears();
            fetchSTATSSAAreaCode();
            fetchOFOCode();
            fetchData();


            callDataApi();
            initializeListeners();
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
        txtssareacode = findViewById(R.id.txtssareacode);
        txtofocode = findViewById(R.id.txtofocode);
        inputSpinneractstatus = findViewById(R.id.inputSpinneractstatus);
        SpinnerDay = findViewById(R.id.SpinnerDay);
        SpinnerMonth = findViewById(R.id.SpinnerMonth);
        SpinnerYear = findViewById(R.id.SpinnerYear);
        inputSpinnersponsorship = findViewById(R.id.inputSpinnersponsorship);
        inputSpinnerfinancialyear = findViewById(R.id.inputSpinnerfinancialyear);
        inputSpinnernqflevel = findViewById(R.id.inputSpinnernqflevel);
        inputsaqaid = findViewById(R.id.inputsaqaid);
        inputempsdlno = findViewById(R.id.inputempsdlno);
        inputprovidersdlno = findViewById(R.id.inputprovidersdlno);
        inputSpinnereconomicstatus = findViewById(R.id.inputSpinnereconomicstatus);
        SpinnerwilstartDay = findViewById(R.id.SpinnerwilstartDay);
        SpinnerwilstartMonth = findViewById(R.id.SpinnerwilstartMonth);
        SpinnerwilstartYear = findViewById(R.id.SpinnerwilstartYear);
         SpinnerwilendDay = findViewById(R.id.SpinnerwilendDay);
        SpinnerwilendMonth = findViewById(R.id.SpinnerwilendMonth);
        SpinnerwilendYear = findViewById(R.id.SpinnerwilendYear);
          SpinnermsregDay = findViewById(R.id.SpinnermsregDay);
        SpinnermsregMonth = findViewById(R.id.SpinnermsregMonth);
        SpinnermsregYear = findViewById(R.id.SpinnermsregYear);
        inputSpinnerenrollmentstatus = findViewById(R.id.inputSpinnerenrollmentstatus);
        inputproject = findViewById(R.id.inputproject);
        inputrefno = findViewById(R.id.inputrefno);
        inputSpinnerwiltype = findViewById(R.id.inputSpinnerwiltype);
        inputLayoutsaqaid = findViewById(R.id.inputLayoutsaqaid);
        inputLayoutempsdlno = findViewById(R.id.inputLayoutempsdlno);
        inputLayoutssareacode = findViewById(R.id.inputLayoutssareacode);
        inputLayoutofocode = findViewById(R.id.inputLayoutofocode);
        inputLayoutprovidersdlno = findViewById(R.id.inputLayoutprovidersdlno);
        inputLayoutproject = findViewById(R.id.inputLayoutproject);
        inputLayoutrefno = findViewById(R.id.inputLayoutrefno);

        ssareacodeContainer    = findViewById(R.id.ssareacodeContainer   );
        actstatus              = findViewById(R.id.actstatus             );
        actstatusdate          = findViewById(R.id.actstatusdate         );
        ofocodeContainer       = findViewById(R.id.ofocodeContainer      );
        sponsorship            = findViewById(R.id.sponsorship           );
        financialyear          = findViewById(R.id.financialyear         );
        nqflevel               = findViewById(R.id.nqflevel              );
        saqaidContainer        = findViewById(R.id.saqaidContainer       );
        empsdlnoContainer      = findViewById(R.id.empsdlnoContainer     );
        providersdlnoContainer = findViewById(R.id.providersdlnoContainer);
        economicstatus         = findViewById(R.id.economicstatus        );
        wilstartdate           = findViewById(R.id.wilstartdate          );
        wilenddate             = findViewById(R.id.wilenddate            );
        msregdate              = findViewById(R.id.msregdate             );
        enrollmentstatus       = findViewById(R.id.enrollmentstatus      );
        projectContainer       = findViewById(R.id.projectContainer      );
        refnoContainer         = findViewById(R.id.refnoContainer        );
        wiltype                = findViewById(R.id.wiltype               );

    }

    @Override
    protected void initializeListeners() {
        printLogs(LogTag, "initializeInputs", "init");

        ArrayList<String> days = new ArrayList<String>();
        for (int i = 1; i <= 31; i++) {
            if(i == 0){
                days.add("");
            }else{
                days.add(Integer.toString(i));
            }
        }
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1900; i <= thisYear; i++) {
            if(i == 1900){
                years.add("");
            }
            years.add(Integer.toString(i));
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });

        /*popi act status spinner*/
         ArrayAdapter<CharSequence> statusadapter = ArrayAdapter.createFromResource(this, R.array.popiact_status_type, android.R.layout.simple_spinner_item);
        statusadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputSpinneractstatus.setAdapter(statusadapter);

        inputSpinneractstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                popiactstatus_value = String.valueOf(parent.getSelectedItemId());
                printLogs(LogTag, "initializeListeners", "popiactstatus_value" + popiactstatus_value);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
         /*sponsorship spinner*/
         ArrayAdapter<CharSequence> sponsorshipadapter = ArrayAdapter.createFromResource(this, R.array.sponsorship_type, android.R.layout.simple_spinner_item);
        sponsorshipadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputSpinnersponsorship.setAdapter(sponsorshipadapter);


        inputSpinnersponsorship.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                sponsorship_value = String.valueOf(parent.getSelectedItemId());
                printLogs(LogTag, "initializeListeners", "sponsorship_value" + sponsorship_value);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
         /*enrollment spinner*/
         ArrayAdapter<CharSequence> enrollmentadapter = ArrayAdapter.createFromResource(this, R.array.enrollment_status_type, android.R.layout.simple_spinner_item);
        enrollmentadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputSpinnerenrollmentstatus.setAdapter(enrollmentadapter);

        inputSpinnerenrollmentstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                enrollment_value = String.valueOf(parent.getSelectedItemId());
                printLogs(LogTag, "initializeListeners", "enrollment_value" + enrollment_value);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
         /*economic spinner*/
         ArrayAdapter<CharSequence> economicadapter = ArrayAdapter.createFromResource(this, R.array.economic_status_type, android.R.layout.simple_spinner_item);
        economicadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputSpinnereconomicstatus.setAdapter(economicadapter);

        inputSpinnereconomicstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                economic_value = String.valueOf(parent.getSelectedItemId());
                printLogs(LogTag, "initializeListeners", "economic_value" + economic_value);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
         /*wiltype spinner*/
         ArrayAdapter<CharSequence> wiltypeadapter = ArrayAdapter.createFromResource(this, R.array.wil_type, android.R.layout.simple_spinner_item);
        wiltypeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputSpinnerwiltype.setAdapter(wiltypeadapter);

        inputSpinnerwiltype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                wiltype_value = String.valueOf(parent.getSelectedItemId());
                printLogs(LogTag, "initializeListeners", "wiltype_value" + wiltype_value);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        /*actstatusdate spinners*/
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, days);
        Spinner SpinnerDay = (Spinner)findViewById(R.id.SpinnerDay);
        SpinnerDay.setAdapter(adapter4);
        if(!popiActDate.equals("")){
            int spinnerPosition = getSelectedPoostion(SpinnerDay, popiActDate);
            SpinnerDay.setSelection(spinnerPosition);
        }
        SpinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spin_actstatusdate = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(this, R.array.month_type, android.R.layout.simple_spinner_item);
        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerMonth.setAdapter(adapter6);
        if(!popiActMonth.equals("")){
            int spinnerPosition = getSelectedPoostion(SpinnerMonth, popiActMonth);
            SpinnerMonth.setSelection(spinnerPosition);
        }


        SpinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spin_actstatusmonth = String.valueOf(parent.getSelectedItemId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        SpinnerYear.setAdapter(adapter3);
        if(!popiActYear.equals("")){
            int spinnerPosition = getSelectedPoostion(SpinnerYear, popiActYear);
            SpinnerYear.setSelection(spinnerPosition);
        }
        SpinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spin_actstatusyear = parent.getItemAtPosition(SpinnerYear.getSelectedItemPosition()).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        /*wilstartdate spinners*/
        ArrayAdapter<String> wilstartdateadapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, days);
        SpinnerwilstartDay.setAdapter(wilstartdateadapter4);

        SpinnerwilstartDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spin_wilstartdate = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final ArrayAdapter<CharSequence> wilstartadapter6 = ArrayAdapter.createFromResource(this, R.array.month_type, android.R.layout.simple_spinner_item);
        wilstartadapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerwilstartMonth.setAdapter(wilstartadapter6);
        wilstartadapter6.notifyDataSetChanged();


        SpinnerwilstartMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spin_wilstartmonth = String.valueOf(parent.getSelectedItemId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> wilstartadapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        SpinnerwilstartYear = (Spinner)findViewById(R.id.SpinnerwilstartYear);
        SpinnerwilstartYear.setAdapter(wilstartadapter3);


        SpinnerwilstartYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spin_wilstartyear = parent.getItemAtPosition(SpinnerwilstartYear.getSelectedItemPosition()).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /*msreg spinners*/
        ArrayAdapter<String> msregadapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, days);
        SpinnermsregDay.setAdapter(msregadapter4);

        SpinnermsregDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spin_msregdate = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final ArrayAdapter<CharSequence> msregadapter6 = ArrayAdapter.createFromResource(this, R.array.month_type, android.R.layout.simple_spinner_item);
        msregadapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnermsregMonth.setAdapter(msregadapter6);
        msregadapter6.notifyDataSetChanged();
        SpinnermsregMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spin_msregmonth = String.valueOf(parent.getSelectedItemId());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        ArrayAdapter<String> msregadapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        SpinnermsregYear.setAdapter(msregadapter3);
        SpinnermsregYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spin_msregyear = parent.getItemAtPosition(SpinnermsregYear.getSelectedItemPosition()).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        /*wilenddate spinners*/
        ArrayAdapter<String> wilenddateadapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, days);
        SpinnerwilendDay.setAdapter(wilenddateadapter4);

        SpinnerwilendDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spin_wilenddate = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final ArrayAdapter<CharSequence> wilendadapter6 = ArrayAdapter.createFromResource(this, R.array.month_type, android.R.layout.simple_spinner_item);
        wilendadapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerwilendMonth.setAdapter(wilendadapter6);
        wilendadapter6.notifyDataSetChanged();


        SpinnerwilendMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spin_wilendmonth = String.valueOf(parent.getSelectedItemId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> wilendadapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        SpinnerwilendYear.setAdapter(wilendadapter3);

        SpinnerwilendYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spin_wilendyear = parent.getItemAtPosition(SpinnerwilendYear.getSelectedItemPosition()).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    protected void initializeInputs() {

        for (int i = 0; i <Step4ArrayList.size() ; i++) {
            if(Step4ArrayList.get(i).getSTATSSA_is_v_4().equals("1")){
                ssareacodeContainer.setVisibility(View.VISIBLE);
            }else{
                ssareacodeContainer.setVisibility(View.GONE);
            }
            if(Step4ArrayList.get(i).getPOPIStatus_is_v_4().equals("1")){
                actstatus.setVisibility(View.VISIBLE);
            }else{
                actstatus.setVisibility(View.GONE);
            }
            if(Step4ArrayList.get(i).getPOPIDate_is_v_4().equals("1")){
                actstatusdate.setVisibility(View.VISIBLE);
            }else{
                actstatusdate.setVisibility(View.GONE);
            }
            if(Step4ArrayList.get(i).getOFO_is_v_4().equals("1")){
                ofocodeContainer.setVisibility(View.VISIBLE);
            }else{
                ofocodeContainer.setVisibility(View.GONE);
            }
            if(Step4ArrayList.get(i).getSponsorship_is_v_4().equals("1")){
                sponsorship.setVisibility(View.VISIBLE);
            }else{
                sponsorship.setVisibility(View.GONE);
            }
            if(Step4ArrayList.get(i).getfYear_is_v_4().equals("1")){
                financialyear.setVisibility(View.VISIBLE);
            }else{
                financialyear.setVisibility(View.GONE);
            }
            if(Step4ArrayList.get(i).getNQF_is_v_4().equals("1")){
                nqflevel.setVisibility(View.VISIBLE);
            }else{
                nqflevel.setVisibility(View.GONE);
            }
            if(Step4ArrayList.get(i).getSAQA_is_v_4().equals("1")){
                saqaidContainer.setVisibility(View.VISIBLE);
            }else{
                saqaidContainer.setVisibility(View.GONE);
            }
            if(Step4ArrayList.get(i).getEmpSDL_is_v_4().equals("1")){
                empsdlnoContainer.setVisibility(View.VISIBLE);
            }else{
                empsdlnoContainer.setVisibility(View.GONE);
            }
            if(Step4ArrayList.get(i).getPSDL_is_v_4().equals("1")){
                providersdlnoContainer.setVisibility(View.VISIBLE);
            }else{
                providersdlnoContainer.setVisibility(View.GONE);
            }
            if(Step4ArrayList.get(i).getEcoStatus_is_v_4().equals("1")){
                economicstatus.setVisibility(View.VISIBLE);
            }else{
                economicstatus.setVisibility(View.GONE);
            }
            if(Step4ArrayList.get(i).getWilStart_is_v_4().equals("1")){
                wilstartdate.setVisibility(View.VISIBLE);
            }else{
                wilstartdate.setVisibility(View.GONE);
            }
            if(Step4ArrayList.get(i).getWilEnd_is_v_4().equals("1")){
                wilenddate.setVisibility(View.VISIBLE);
            }else{
                wilenddate.setVisibility(View.GONE);
            }
            if(Step4ArrayList.get(i).getMSregDate_is_v_4().equals("1")){
                msregdate.setVisibility(View.VISIBLE);
            }else{
                msregdate.setVisibility(View.GONE);
            }
            if(Step4ArrayList.get(i).getEnroll_is_v_4().equals("1")){
                enrollmentstatus.setVisibility(View.VISIBLE);
            }else{
                enrollmentstatus.setVisibility(View.GONE);
            }
            if(Step4ArrayList.get(i).getProject_is_v_4().equals("1")){
                projectContainer.setVisibility(View.VISIBLE);
            }else{
                projectContainer.setVisibility(View.GONE);
            }
            if(Step4ArrayList.get(i).getRefNo_is_v_4().equals("1")){
                refnoContainer.setVisibility(View.VISIBLE);
            }else{
                refnoContainer.setVisibility(View.GONE);
            }
            if(Step4ArrayList.get(i).getWilType_is_v_4().equals("1")){
                wiltype.setVisibility(View.VISIBLE);
            }else{
                wiltype.setVisibility(View.GONE);
            }
////=================required===============================================
            if(Step4ArrayList.get(i).getSTATSSA_is_r_4().equals("1")){
                vaildSSAAreaCode =true;
            }else{
                vaildSSAAreaCode =false;
            }
            if(Step4ArrayList.get(i).getPOPIStatus_is_r_4().equals("1")){
                validPopiActStatus =true;
            }else{
                validPopiActStatus =false;
            }
            if(Step4ArrayList.get(i).getPOPIDate_is_r_4().equals("1")){
                validPopiActDate =true;
            }else{
                validPopiActDate =false;
            }
            if(Step4ArrayList.get(i).getOFO_is_r_4().equals("1")){
                validOFoCode =true;
            }else{
                validOFoCode =false;
            }
            if(Step4ArrayList.get(i).getSponsorship_is_r_4().equals("1")){
                validSponsorship =true;
            }else{
                validSponsorship =false;
            }
            if(Step4ArrayList.get(i).getfYear_is_r_4().equals("1")){
                validFYear =true;
            }else{
                validFYear =false;
            }
            if(Step4ArrayList.get(i).getNQF_is_r_4().equals("1")){
                validNQFLevel =true;
            }else{
                validNQFLevel =false;
            }
            if(Step4ArrayList.get(i).getSAQA_is_r_4().equals("1")){
                validSAQAId =true;
            }else{
                validSAQAId =false;
            }
            if(Step4ArrayList.get(i).getEmpSDL_is_r_4().equals("1")){
                validEMPSDlNo =true;
            }else{
                validEMPSDlNo =false;
            }
            if(Step4ArrayList.get(i).getPSDL_is_r_4().equals("1")){
                validPSDLNo =true;
            }else{
                validPSDLNo =false;
            }
            if(Step4ArrayList.get(i).getEcoStatus_is_r_4().equals("1")){
                validEcoStatus =true;
            }else{
                validEcoStatus =false;
            }
            if(Step4ArrayList.get(i).getWilStart_is_r_4().equals("1")){
                validWilStartDate =true;
            }else{
                validWilStartDate =false;
            }
            if(Step4ArrayList.get(i).getWilEnd_is_r_4().equals("1")){
                validWilEndDate =true;
            }else{
                validWilEndDate =false;
            }
            if(Step4ArrayList.get(i).getMSregDate_is_r_4().equals("1")){
                validMSRegDate =true;
            }else{
                validMSRegDate =false;
            }
            if(Step4ArrayList.get(i).getEnroll_is_r_4().equals("1")){
                validEnrollStatus =true;
            }else{
                validEnrollStatus =false;
            }
            if(Step4ArrayList.get(i).getProject_is_r_4().equals("1")){
                validProject =true;
            }else{
                validProject =false;
            }
            if(Step4ArrayList.get(i).getRefNo_is_r_4().equals("1")){
                validRefNo =true;
            }else{
                validRefNo =false;
            }
            if(Step4ArrayList.get(i).getWilType_is_r_4().equals("1")){
                validWilType =true;
            }else{
                validWilType =false;
            }
        }



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

        Label = getLabelFromDb("lbl_S105C_heading", R.string.lbl_S105C_heading);
        lblView = (TextView) findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            btnUpdate.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            inputsaqaid.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputempsdlno.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputprovidersdlno.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputproject.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputrefno.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            txtssareacode.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            txtofocode.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputSpinneractstatus.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            SpinnerDay.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            SpinnerMonth.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            SpinnerYear.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            inputSpinnersponsorship.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            inputSpinnerfinancialyear.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            inputSpinnernqflevel.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            inputSpinnereconomicstatus.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            SpinnerwilstartDay.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            SpinnerwilstartMonth.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            SpinnerwilstartYear.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            SpinnerwilendDay.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            SpinnerwilendMonth.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            SpinnerwilendYear.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
             SpinnermsregDay.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            SpinnermsregMonth.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            SpinnermsregYear.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            inputSpinnerenrollmentstatus.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            inputSpinnerwiltype.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));


      }


    }

    @Override
    protected void setLayoutXml() {
        printLogs(LogTag, "setLayoutXml", "edit_profile_stepthree");
        setContentView(R.layout.a_edit_profile_step_three);
    }

    private void fetchData() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_105_C;
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

                            inputempsdlno.setText(dataM.getString("s_d_t_emp_sdl_num"));
                            inputproject.setText(dataM.getString("s_d_t_project"));
                            inputsaqaid.setText(dataM.getString("s_d_t_saqa_id"));
                            inputprovidersdlno.setText(dataM.getString("s_d_t_provider_sdl_num"));
                            inputrefno.setText(dataM.getString("s_d_t_prefrence_number"));

                            ssareacode_value=dataM.getString("s_d_t_statssa_code");
                                    popiActStatus=dataM.getString("s_d_t_popi_status");
                                    popiActDate=dataM.getString("popiDay");
                                    popiActMonth=dataM.getString("popiMonth");
                                    popiActYear=dataM.getString("popiYear");
                            txtofocode_value=dataM.getString("s_d_t_ofo_code");
                                    Sponsorship=dataM.getString("s_d_t_sponsorship");
                                    FinancialYear=dataM.getString("s_d_t_financial_year");
                                    NQFLevel=dataM.getString("s_d_t_nql_level");
                                    EconomicStatus=dataM.getString("s_d_t_socio_status");
                                    EnrollmentStatus=dataM.getString("s_d_t_enrolment_status");
                                    WILType=dataM.getString("s_d_t_wil_type");
                                    WILStartDate=dataM.getString("wsDay");
                                    WILStartMonth=dataM.getString("wsMonth");
                                    WILStartYear=dataM.getString("wsYear");
                                    WILEndDate=dataM.getString("weDay");
                                    WILEndMonth=dataM.getString("weMonth");
                                    WILEndYear=dataM.getString("weYear");
                                    MRRDate=dataM.getString("regDay");
                                    MRRMonth=dataM.getString("regMonth");
                                    MRRYear=dataM.getString("regYear");

                        }
/*popiAct Date Spinner*/
                        if(!popiActDate.equals("")){
                            int dday = Integer.parseInt(popiActDate);
                            int new_day = dday-1;
                            String ryt_date = String.valueOf(new_day);
                            SpinnerDay.setSelection(Integer.parseInt(ryt_date));
                        }
                        if(!popiActMonth.equals("")){
                            SpinnerMonth.setSelection(Integer.parseInt(popiActMonth));
                        }
                        String mYear = popiActYear; //the value you want the position for
                        ArrayAdapter mAdpt = (ArrayAdapter) SpinnerYear.getAdapter(); //cast to an ArrayAdapter
                        int spinnerPosition = mAdpt.getPosition(mYear);
                        //set the default according to value
                        SpinnerYear.setSelection(spinnerPosition);

                        /*Wil Start date Spinner*/

                        if(!WILStartDate.equals("")){
                            int dday = Integer.parseInt(WILStartDate);
                            int new_day = dday-1;
                            String ryt_date = String.valueOf(new_day);
                            SpinnerwilstartDay.setSelection(Integer.parseInt(ryt_date));
                        }
                        if(!WILStartMonth.equals("")){
                            SpinnerwilstartMonth.setSelection(Integer.parseInt(WILStartMonth));
                        }
                        String wilstartYear = WILStartYear; //the value you want the position for
                        ArrayAdapter wilstartmAdpt = (ArrayAdapter) SpinnerwilstartYear.getAdapter(); //cast to an ArrayAdapter
                        int wilstartmAdptspinnerPosition = wilstartmAdpt.getPosition(wilstartYear);
                        //set the default according to value
                        SpinnerwilstartYear.setSelection(wilstartmAdptspinnerPosition);

                        /*Wil end date Spinner*/

                        if(!WILEndDate.equals("")){
                            int dday = Integer.parseInt(WILEndDate);
                            int new_day = dday-1;
                            String ryt_date = String.valueOf(new_day);
                            SpinnerwilendDay.setSelection(Integer.parseInt(ryt_date));
                        }
                        if(!WILEndMonth.equals("")){
                            SpinnerwilendMonth.setSelection(Integer.parseInt(WILEndMonth));
                        }
                        String wilEndYear = WILEndYear; //the value you want the position for
                        ArrayAdapter wilEndmAdpt = (ArrayAdapter) SpinnerwilendYear.getAdapter(); //cast to an ArrayAdapter
                        int wilEndmAdptspinnerPosition = wilEndmAdpt.getPosition(wilEndYear);
                        //set the default according to value
                        SpinnerwilendYear.setSelection(wilEndmAdptspinnerPosition);

                        /*Most recent registration date Spinner*/

                        if(!MRRDate.equals("")){
                            int dday = Integer.parseInt(MRRDate);
                            int new_day = dday-1;
                            String ryt_date = String.valueOf(new_day);
                            SpinnermsregDay.setSelection(Integer.parseInt(ryt_date));
                        }
                        if(!MRRMonth.equals("")){
                            SpinnermsregMonth.setSelection(Integer.parseInt(MRRMonth));
                        }
                        String MRYear = MRRYear; //the value you want the position for
                        ArrayAdapter MRRmAdpt = (ArrayAdapter) SpinnermsregYear.getAdapter(); //cast to an ArrayAdapter
                        int MRRmAdptspinnerPosition = MRRmAdpt.getPosition(MRYear);
                        //set the default according to value
                        SpinnermsregYear.setSelection(MRRmAdptspinnerPosition);



                        if(!Sponsorship.equals("")) {
                            inputSpinnersponsorship.setSelection(Integer.parseInt(Sponsorship));
                        }
                        if(!popiActStatus.equals("")){
                            inputSpinneractstatus.setSelection(Integer.parseInt(popiActStatus));
                        }
                        if(!EconomicStatus.equals("")){
                            inputSpinnereconomicstatus.setSelection(Integer.parseInt(EconomicStatus));
                        }
                        if(!EnrollmentStatus.equals("")){
                            inputSpinnerenrollmentstatus.setSelection(Integer.parseInt(EnrollmentStatus));
                        }
                        if(!WILType.equals("")){
                            inputSpinnerwiltype.setSelection(Integer.parseInt(WILType));
                        }
                        fetchNQFLevels();
                        fetchFinancialYears();

                        fetchOFOCode();
                        fetchSTATSSAAreaCode();






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

    private void fetchOFOCode() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.OFOCODES_105C;
        FINAL_URL=FINAL_URL+"?token="+token;
        printLogs(LogTag,"fetchOFOCode","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchOFOCode", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        OFOCodeType = new SpinnerObj[dataM.length()];
                        for (int i = 0; i <dataM.length() ; i++) {
                            JSONObject jsonObject = dataM.getJSONObject(i);
                            OFOCode_list.add(jsonObject.getString("ofo_type"));
                            OFOCodeType[i] = new SpinnerObj();
                            OFOCodeType[i].setId(jsonObject.getString("ofo_id"));
                            OFOCodeType[i].setName(jsonObject.getString("ofo_type"));

                        }
                        AutoCompleteAdapter autoCompleteAdapter = new AutoCompleteAdapter(SEDitProfileStepThreeA.this,android.R.layout.simple_spinner_item,android.R.id.text1,OFOCode_list);
                        txtofocode.setAdapter(autoCompleteAdapter);
                        if(!txtofocode_value.equals("")){
                            for (int j = 0; j <OFOCodeType.length ; j++) {
                                if(OFOCodeType[j].getId().equals(txtofocode_value)){
                                    txtofocode.setText(OFOCodeType[j].getName());
                                }
                            }
                        }
                        txtofocode.setThreshold(2);
                        txtofocode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String txtofocode_value1 = autoCompleteAdapter.getItem(i);
                                for (int j = 0; j <OFOCodeType.length ; j++) {
                                    if(OFOCodeType[j].getName().equals(txtofocode_value1)){
                                        txtofocode_value = OFOCodeType[j].getId();
                                        printLogs(LogTag, "fetchOFOCode", "txtofocode_value id: " + txtofocode_value);
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

    private void fetchFinancialYears() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.FINANCIAL_YEARS_105C;
        FINAL_URL=FINAL_URL+"?token="+token;
        printLogs(LogTag,"fetchFinancialYears","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchFinancialYears", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        FinYearType = new SpinnerObj[dataM.length()];
                        for (int i = 0; i <dataM.length() ; i++) {
                            JSONObject jsonObject = dataM.getJSONObject(i);
                            FinYearType[i] = new SpinnerObj();
                            FinYearType[i].setId(jsonObject.getString("fy_id"));
                            FinYearType[i].setName(jsonObject.getString("fy_type"));

                        }
                        SpinAdapter adapter = new SpinAdapter(SEDitProfileStepThreeA.this, android.R.layout.simple_spinner_item, FinYearType);
                        inputSpinnerfinancialyear.setAdapter(adapter);
                        if(!FinancialYear.equals("")){
                            int spinnerPosition = getSelectedPoostion(inputSpinnerfinancialyear, FinancialYear);
                            inputSpinnerfinancialyear.setSelection(spinnerPosition);
                        }


                        inputSpinnerfinancialyear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                spin_financialyear = adapter.getItem(i).getId();
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

    private void fetchNQFLevels() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.NQF_105C;
        FINAL_URL=FINAL_URL+"?token="+token;
        printLogs(LogTag,"fetchNQFLevelsSpinner","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchNQFLevelsSpinner", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        NQFLevelType = new SpinnerObj[dataM.length()];
                        for (int i = 0; i <dataM.length() ; i++) {
                            JSONObject jsonObject = dataM.getJSONObject(i);
                            NQFLevelType[i] = new SpinnerObj();
                            NQFLevelType[i].setId(jsonObject.getString("nqf_id"));
                            NQFLevelType[i].setName(jsonObject.getString("nqf_type"));

                        }
                        SpinAdapter adapter = new SpinAdapter(SEDitProfileStepThreeA.this, android.R.layout.simple_spinner_item, NQFLevelType);
                        inputSpinnernqflevel.setAdapter(adapter);

                        if(!NQFLevel.equals("")){
                            int spinnerPosition = getSelectedPoostion(inputSpinnernqflevel, NQFLevel);
                            inputSpinnernqflevel.setSelection(spinnerPosition);
                        }

                        inputSpinnernqflevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                spin_nqf = adapter.getItem(i).getId();
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

    private void fetchSTATSSAAreaCode() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.STATS_AREACODE_105C;
        FINAL_URL=FINAL_URL+"?token="+token;
        printLogs(LogTag,"fetchSTATSSAAreaCodeSpinner","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchSTATSSAAreaCodeSpinner", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        AreaCodeType = new SpinnerObj[dataM.length()];
                        for (int i = 0; i <dataM.length() ; i++) {
                            JSONObject jsonObject = dataM.getJSONObject(i);
                            AreaCode_list.add(jsonObject.getString("stats_type"));
                            AreaCodeType[i] = new SpinnerObj();
                            AreaCodeType[i].setId(jsonObject.getString("stats_id"));
                            AreaCodeType[i].setName(jsonObject.getString("stats_type"));

                        }
                        AutoCompleteAdapter autoCompleteAdapter = new AutoCompleteAdapter(SEDitProfileStepThreeA.this,android.R.layout.simple_spinner_item,android.R.id.text1,AreaCode_list);
                        txtssareacode.setAdapter(autoCompleteAdapter);
                        if(!ssareacode_value.equals("")){
                            for (int j = 0; j <AreaCodeType.length ; j++) {
                                if(AreaCodeType[j].getId().equals(ssareacode_value)){
                                    txtssareacode.setText(AreaCodeType[j].getName());
                                }
                            }
                        }
                        txtssareacode.setThreshold(2);
                        txtssareacode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String ssareacode_value1 = autoCompleteAdapter.getItem(i);
                                for (int j = 0; j <AreaCodeType.length ; j++) {
                                    if(AreaCodeType[j].getName().equals(ssareacode_value1)){
                                        ssareacode_value = AreaCodeType[j].getId();
                                        printLogs(LogTag, "fetchSTATSSAAreaCodeSpinner", "ssareacode_value id: " + ssareacode_value);
                                    }
                                }
                            }
                        });
                        showProgress(false, mContentView, mProgressView);
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



    @Override
    public void validateForm() {
        boolean cancel = false;

        if(validPopiActStatus){
            if (!validateSpinnerPopiActStatus(inputSpinneractstatus)) {
                cancel = true;
            }
        }
        if(validPopiActDate){
            if (!validateSpinnerPopiActDate(popiActDate,popiActMonth,popiActYear)) {
                cancel = true;
            }
        }
        if(validSponsorship){
            if (!validateSpinnerSponsorship(inputSpinnersponsorship)) {
                cancel = true;
            }
        }
        if(validFYear){
            if (!validateSpinnerFYear(inputSpinnerfinancialyear)) {
                cancel = true;
            }
        }
        if(validNQFLevel){
            if (!validateSpinnerNQFLevel(inputSpinnernqflevel)) {
                cancel = true;
            }
        }
        if(validEcoStatus){
            if (!validateSpinnerEcoStatus(inputSpinnereconomicstatus)) {
                cancel = true;
            }
        }
        if(validWilStartDate){
            if (!validateSpinnerWilStartDate(spin_wilstartdate,spin_wilstartmonth,spin_wilstartyear)) {
                cancel = true;
            }
        }
        if(validWilEndDate){
            if (!validateSpinnerWilEndDate(spin_wilenddate,spin_wilendmonth,spin_wilendyear)) {
                cancel = true;
            }
        }if(validMSRegDate){
            if (!validateSpinnerMSRegDate(spin_msregdate,spin_msregmonth,spin_msregyear)) {
                cancel = true;
            }
        }
        if(validEnrollStatus){
            if (!validateSpinnerEnrollStatus(inputSpinnerenrollmentstatus)) {
                cancel = true;
            }
        }
        if(validWilType){
            if (!validateSpinnerWilType(inputSpinnerwiltype)) {
                cancel = true;
            }
        }
        if(vaildSSAAreaCode){
            if (!validateSSAAreaCode(txtssareacode, inputLayoutssareacode)) {
                cancel = true;
            }
        }
        if(validOFoCode){
            if (!validateOFoCode(txtofocode, inputLayoutofocode)) {
                cancel = true;
            }
        }
        if(validSAQAId){
            if (!validateSAQAId(inputsaqaid, inputLayoutsaqaid)) {
                cancel = true;
            }
        }
        if(validEMPSDlNo){
            if (!validateEMPSDlNo(inputempsdlno, inputLayoutempsdlno)) {
                cancel = true;
            }
        }
        if(validPSDLNo){
            if (!validatePSDLNo(inputprovidersdlno, inputLayoutprovidersdlno)) {
                cancel = true;
            }
        }
        if(validProject){
            if (!validateProject(inputproject, inputLayoutproject)) {
                cancel = true;
            }
        }
        if(validRefNo){
            if (!validateRefNo(inputrefno, inputLayoutrefno)) {
                cancel = true;
            }
        }



        if (!validateSAQAId(inputsaqaid, inputLayoutsaqaid)) {
            cancel = true;
        } else if (!validateEmpSDLNo(inputempsdlno, inputLayoutempsdlno)) {
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

    private boolean validateSpinnerMSRegDate(String spin_msregdate, String spin_msregmonth, String spin_msregyear) {
        if (spin_msregdate.isEmpty() || spin_msregdate.equals("0")) {
            String sTitle = "";
            String sMessage = getLabelFromDb("lbl_S105D_errorspday", R.string.lbl_S105D_errorspday);
            String sButtonLabelClose = "Close";
            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
            return false;
        } else if (spin_msregmonth.isEmpty() || spin_msregmonth.equals("0")) {
            String sTitle = "";
            String sMessage = getLabelFromDb("lbl_S105D_errorspmonth", R.string.lbl_S105D_errorspmonth);
            String sButtonLabelClose = "Close";
            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
            return false;
        }else if (spin_msregyear.isEmpty() || spin_msregyear.equals("0")) {
            String sTitle = "";
            String sMessage = getLabelFromDb("lbl_S105D_errorspyear", R.string.lbl_S105D_errorspyear);
            String sButtonLabelClose = "Close";
            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
            return false;
        }else {
            return true;
        }
    }

    private boolean validateSpinnerWilEndDate(String spin_wilenddate, String spin_wilendmonth, String spin_wilendyear) {
        if (spin_wilenddate.isEmpty() || spin_wilenddate.equals("0")) {
            String sTitle = "";
            String sMessage = getLabelFromDb("lbl_S105D_errorspday", R.string.lbl_S105D_errorspday);
            String sButtonLabelClose = "Close";
            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
            return false;
        } else if (spin_wilendmonth.isEmpty() || spin_wilendmonth.equals("0")) {
            String sTitle = "";
            String sMessage = getLabelFromDb("lbl_S105D_errorspmonth", R.string.lbl_S105D_errorspmonth);
            String sButtonLabelClose = "Close";
            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
            return false;
        }else if (spin_wilendyear.isEmpty() || spin_wilendyear.equals("0")) {
            String sTitle = "";
            String sMessage = getLabelFromDb("lbl_S105D_errorspyear", R.string.lbl_S105D_errorspyear);
            String sButtonLabelClose = "Close";
            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
            return false;
        }else {
            return true;
        }
    }

    private boolean validateSpinnerWilStartDate(String spin_wilstartdate, String spin_wilstartmonth, String spin_wilstartyear) {
        if (spin_wilstartdate.isEmpty() || spin_wilstartdate.equals("0")) {
            String sTitle = "";
            String sMessage = getLabelFromDb("lbl_S105D_errorspday", R.string.lbl_S105D_errorspday);
            String sButtonLabelClose = "Close";
            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
            return false;
        } else if (spin_wilstartmonth.isEmpty() || spin_wilstartmonth.equals("0")) {
            String sTitle = "";
            String sMessage = getLabelFromDb("lbl_S105D_errorspmonth", R.string.lbl_S105D_errorspmonth);
            String sButtonLabelClose = "Close";
            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
            return false;
        }else if (spin_wilstartyear.isEmpty() || spin_wilstartyear.equals("0")) {
            String sTitle = "";
            String sMessage = getLabelFromDb("lbl_S105D_errorspyear", R.string.lbl_S105D_errorspyear);
            String sButtonLabelClose = "Close";
            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
            return false;
        }else {
            return true;
        }
    }

    private boolean validateRefNo(EditText inputrefno, TextInputLayout inputLayoutrefno) {
    String refno = inputrefno.getText().toString().trim();
        setCustomError(inputLayoutrefno, null, inputrefno);
        if (refno.isEmpty()) {
            String sMessage = getLabelFromDb("error_S105_emptyrefno", R.string.error_S105_emptyrefno);
            setCustomError(inputLayoutrefno, sMessage, inputrefno);
            return false;
        } else if(!isValidNumber(refno)){
            String sMessage = getLabelFromDb("error_invalid_refno", R.string.error_invalid_refno);
            setCustomError(inputLayoutrefno, sMessage, inputrefno);
            return false;

        }else {
            setCustomErrorDisabled(inputLayoutrefno, inputrefno);
            return true;
        }
    }

    private boolean validateProject(EditText inputproject, TextInputLayout inputLayoutproject) {
    String project = inputproject.getText().toString().trim();
        setCustomError(inputLayoutproject, null, inputproject);
        if (project.isEmpty()) {
            String sMessage = getLabelFromDb("error_S105_empty", R.string.error_S105_empty);
            setCustomError(inputLayoutproject, sMessage, inputproject);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutproject, inputproject);
            return true;
        }
    }

    private boolean validatePSDLNo(EditText inputprovidersdlno, TextInputLayout inputLayoutprovidersdlno) {
     String providersdlno = inputprovidersdlno.getText().toString().trim();
        setCustomError(inputLayoutprovidersdlno, null, inputprovidersdlno);
        if (providersdlno.isEmpty()) {
            String sMessage = getLabelFromDb("error_S105_emptyprosdl", R.string.error_S105_emptyprosdl);
            setCustomError(inputLayoutprovidersdlno, sMessage, inputprovidersdlno);
            return false;
        } else if(!isValidNumber(providersdlno)){
            String sMessage = getLabelFromDb("error_invalid_prono", R.string.error_invalid_prono);
            setCustomError(inputLayoutprovidersdlno, sMessage, inputprovidersdlno);
            return false;

        }else {
            setCustomErrorDisabled(inputLayoutprovidersdlno, inputprovidersdlno);
            return true;
        }
    }

    private boolean validateEMPSDlNo(EditText inputempsdlno, TextInputLayout inputLayoutempsdlno) {
    String empsdlno = inputempsdlno.getText().toString().trim();
        setCustomError(inputLayoutempsdlno, null, inputempsdlno);
        if (empsdlno.isEmpty()) {
            String sMessage = getLabelFromDb("error_S105_emptyempsdl", R.string.error_S105_emptyempsdl);
            setCustomError(inputLayoutempsdlno, sMessage, inputempsdlno);
            return false;
        }else if(!isValidNumber(empsdlno)){
            String sMessage = getLabelFromDb("error_invalid_empno", R.string.error_invalid_empno);
            setCustomError(inputLayoutempsdlno, sMessage, inputempsdlno);
            return false;

        }else {
            setCustomErrorDisabled(inputLayoutempsdlno, inputempsdlno);
            return true;
        }
    }

    private boolean validateOFoCode(AutoCompleteTextView txtofocode, TextInputLayout inputLayoutofocode) {
        String ofocode = txtofocode.getText().toString().trim();
        setCustomError(inputLayoutofocode, null, txtofocode);
        if (ofocode.isEmpty()) {
            String sMessage = getLabelFromDb("error_S105_empty", R.string.error_S105_empty);
            setCustomError(inputLayoutofocode, sMessage, txtofocode);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutofocode, txtofocode);
            return true;
        }
    }

    private boolean validateSSAAreaCode(AutoCompleteTextView txtssareacode, TextInputLayout inputLayoutssareacode) {
        String ssareacode = txtssareacode.getText().toString().trim();
        setCustomError(inputLayoutssareacode, null, txtssareacode);
        if (ssareacode.isEmpty()) {
            String sMessage = getLabelFromDb("error_S105_emptysaqaid", R.string.error_S105_emptysaqaid);
            setCustomError(inputLayoutssareacode, sMessage, txtssareacode);
            return false;
        }else if(!isValidNumber(ssareacode)){
            String sMessage = getLabelFromDb("error_invalid_saqaid", R.string.error_invalid_saqaid);
            setCustomError(inputLayoutssareacode, sMessage, txtssareacode);
            return false;

        }else {
            setCustomErrorDisabled(inputLayoutssareacode, txtssareacode);
            return true;
        }
    }

    private boolean validateSpinnerWilType(Spinner inputSpinnerwiltype) {
        printLogs(LogTag,"validateSpinnerWilType","wiltype_value : "+wiltype_value);
        if (wiltype_value.isEmpty() || wiltype_value.equals("0")) {
            String sTitle = "";
            String sMessage = getLabelFromDb("lbl_S105D_errorspwiltype", R.string.lbl_S105D_errorspwiltype);
            String sButtonLabelClose = "Close";
            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);

            return false;
        } else {
            return true;
        }
    }

    private boolean validateSpinnerEnrollStatus(Spinner inputSpinnerenrollmentstatus) {
        printLogs(LogTag,"validateSpinnerEnrollStatus","enrollment_value : "+enrollment_value);
        if (enrollment_value.isEmpty() || enrollment_value.equals("0")) {
            String sTitle = "";
            String sMessage = getLabelFromDb("lbl_S105D_errorenroll", R.string.lbl_S105D_errorenroll);
            String sButtonLabelClose = "Close";
            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);

            return false;
        } else {
            return true;
        }
    }

    private boolean validateSpinnerEcoStatus(Spinner inputSpinnereconomicstatus) {
        printLogs(LogTag,"validateSpinnerEcoStatus","economic_value : "+economic_value);
        if (economic_value.isEmpty() || economic_value.equals("0")) {
            String sTitle = "";
            String sMessage = getLabelFromDb("lbl_S105D_errorspeconomic", R.string.lbl_S105D_errorspeconomic);
            String sButtonLabelClose = "Close";
            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);

            return false;
        } else {
            return true;
        }
    }

    private boolean validateSpinnerNQFLevel(Spinner inputSpinnernqflevel) {
        printLogs(LogTag,"validateSpinnerNQFLevel","spin_nqf : "+spin_nqf);
        if (spin_nqf.isEmpty() || spin_nqf.equals("0")) {
            String sTitle = "";
            String sMessage = getLabelFromDb("lbl_S105D_errorspnqf", R.string.lbl_S105D_errorspnqf);
            String sButtonLabelClose = "Close";
            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);

            return false;
        } else {
            return true;
        }
    }

    private boolean validateSpinnerFYear(Spinner inputSpinnerfinancialyear) {
        printLogs(LogTag,"validateSpinnerFYear","spin_financialyear : "+spin_financialyear);
        if (spin_financialyear.isEmpty() || spin_financialyear.equals("0")) {
            String sTitle = "";
            String sMessage = getLabelFromDb("lbl_S105D_errorfyear", R.string.lbl_S105D_errorfyear);
            String sButtonLabelClose = "Close";
            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);

            return false;
        } else {
            return true;
        }
    }

    private boolean validateSpinnerSponsorship(Spinner inputSpinnersponsorship) {
        printLogs(LogTag,"validateSpinnerSponsorship","sponsorship_value : "+sponsorship_value);
        if (sponsorship_value.isEmpty() || sponsorship_value.equals("0")) {
            String sTitle = "";
            String sMessage = getLabelFromDb("lbl_S105D_errorspsponsorship", R.string.lbl_S105D_errorspsponsorship);
            String sButtonLabelClose = "Close";
            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
            return false;
        } else {
            return true;
        }
    }

    private boolean validateSpinnerPopiActDate(String popiActDate, String popiActMonth, String popiActYear) {
        printLogs(LogTag,"validateSpinnerPopiActStatus","popiactstatus_value : "+popiactstatus_value);
        if (popiActDate.isEmpty() || popiActDate.equals("0")) {
            String sTitle = "";
            String sMessage = getLabelFromDb("lbl_S105D_errorspday", R.string.lbl_S105D_errorspday);
            String sButtonLabelClose = "Close";
            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
            return false;
        } else if (popiActMonth.isEmpty() || popiActMonth.equals("0")) {
            String sTitle = "";
            String sMessage = getLabelFromDb("lbl_S105D_errorspmonth", R.string.lbl_S105D_errorspmonth);
            String sButtonLabelClose = "Close";
            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
            return false;
        }else if (popiActYear.isEmpty() || popiActYear.equals("0")) {
            String sTitle = "";
            String sMessage = getLabelFromDb("lbl_S105D_errorspyear", R.string.lbl_S105D_errorspyear);
            String sButtonLabelClose = "Close";
            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
            return false;
        }else {
            return true;
        }
    }

    private boolean validateSpinnerPopiActStatus(Spinner inputSpinneractstatus) {
        printLogs(LogTag,"validateSpinnerPopiActStatus","popiactstatus_value : "+popiactstatus_value);
        if (popiactstatus_value.isEmpty() || popiactstatus_value.equals("0")) {
            String sTitle = "";
            String sMessage = getLabelFromDb("lbl_S105D_errorspinneractstatus", R.string.lbl_S105D_errorspinneractstatus);
            String sButtonLabelClose = "Close";
            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);

            return false;
        } else {
            return true;
        }
    }

    private boolean validateEmpSDLNo(EditText inputempsdlno, TextInputLayout inputLayoutempsdlno) {
        String sRegNo = inputempsdlno.getText().toString().trim();
        setCustomError(inputLayoutempsdlno, null, inputempsdlno);
        if (sRegNo.isEmpty() || !isValidSDLNo(sRegNo)) {
            String sMessage = getLabelFromDb("error_S105_empsdlno", R.string.error_S105_empsdlno);
            setCustomError(inputLayoutempsdlno, sMessage, inputempsdlno);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutempsdlno, inputempsdlno);
            return true;
        }
    }

    private boolean validateSAQAId(EditText inputsaqaid, TextInputLayout inputLayoutsaqaid) {
        String sRegNo = inputsaqaid.getText().toString().trim();
        setCustomError(inputLayoutsaqaid, null, inputsaqaid);
        if (sRegNo.isEmpty() || !isValidSDLNo(sRegNo)) {
            String sMessage = getLabelFromDb("error_S105_saqaid", R.string.error_S105_saqaid);
            setCustomError(inputLayoutsaqaid, sMessage, inputsaqaid);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutsaqaid, inputsaqaid);
            return true;
        }
    }

    @Override
    public void FormSubmit() {

        final int month = Integer.parseInt(String.valueOf(spin_actstatusmonth));
        final int date = Integer.parseInt(String.valueOf(spin_actstatusdate));
        final int year = Integer.parseInt(String.valueOf(spin_actstatusyear));

        String actstatusdate = year+"-"+month+"-"+date;


        final int wilstartmonth = Integer.parseInt(String.valueOf(spin_wilstartmonth));
        final int wilstartdate = Integer.parseInt(String.valueOf(spin_wilstartdate));
        final int wilstartyear = Integer.parseInt(String.valueOf(spin_wilstartyear));

        String wilstart_date = wilstartyear+"-"+wilstartmonth+"-"+wilstartdate;

        final int wilendmonth = Integer.parseInt(String.valueOf(spin_wilendmonth));
        final int wilenddate = Integer.parseInt(String.valueOf(spin_wilenddate));
        final int wilendyear = Integer.parseInt(String.valueOf(spin_wilendyear));

        String wilend_date = wilendyear+"-"+wilendmonth+"-"+wilenddate;

        final int msregmonth = Integer.parseInt(String.valueOf(spin_msregmonth));
        final int msregdate = Integer.parseInt(String.valueOf(spin_msregdate));
        final int msregyear = Integer.parseInt(String.valueOf(spin_msregyear));

        String msreg_date = msregyear+"-"+msregmonth+"-"+msregdate;

        final String saqaid = inputsaqaid.getText().toString().trim();
        final String empsdlno = inputempsdlno.getText().toString().trim();
        final String providersdlno = inputprovidersdlno.getText().toString().trim();
        final String project = inputproject.getText().toString().trim();
        final String refno = inputrefno.getText().toString().trim();


        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.UPDATEDETAILS_105_C;
        printLogs(LogTag, "FormSubmit", "URL : " + FINAL_URL);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put("statssa_area_code", ssareacode_value);
            jsonBody.put("popiact_status", popiactstatus_value);
            jsonBody.put("popiact_status_date", actstatusdate);
            jsonBody.put("ofo_code", txtofocode_value);
            jsonBody.put("sponsorship", sponsorship_value);
            jsonBody.put("financial_year", spin_financialyear);
            jsonBody.put("nqf_level", spin_nqf);
            jsonBody.put("quali_saqa_id",saqaid );
            jsonBody.put("emp_sdl_no", empsdlno);
            jsonBody.put("provider_sdl_no", providersdlno);
            jsonBody.put("economic_status", economic_value);
             jsonBody.put("wil_start_date", wilstart_date);
             jsonBody.put("wil_end_date", wilend_date);
             jsonBody.put("recent_reg_date", msreg_date);
             jsonBody.put("enroll_status", enrollment_value);
             jsonBody.put("project", project);
             jsonBody.put("ref_no", refno);
             jsonBody.put("wil_type_status", wiltype_value);

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
                        ErrorDialog.showSuccessDialogSEditProfileThree(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose, thisClass);
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
    protected void onPause() {
        super.onPause();
        unregisterBroadcastIC();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkInternetConnection();
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
        checkInternetConnection();
        registerBroadcastIC();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcastIC();
    }

}