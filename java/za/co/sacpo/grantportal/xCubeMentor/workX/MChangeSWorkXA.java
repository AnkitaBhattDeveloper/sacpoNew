package za.co.sacpo.grantportal.xCubeMentor.workX;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import za.co.sacpo.grantportal.AppUpdatedA;
import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.ListarClientes;
import za.co.sacpo.grantportal.xCubeLib.adapter.SpinnerAdapter;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.component.Utils;
import za.co.sacpo.grantportal.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grantportal.xCubeLib.spinners.SpinnerSet;
import za.co.sacpo.grantportal.xCubeLib.spinners.SpinnerSetAdapter;
import za.co.sacpo.grantportal.xCubeMentor.MDashboardDA;


public class MChangeSWorkXA extends BaseFormAPCPrivate {
    private String ActivityId="313";
    public View mProgressView, mContentView,heading;
    private String selectedWorks;
    public SpinnerSetAdapter adapterWorks;
    private String KEY_DEPARTMENT_ID= "department_id";
    String student_id,generator,work_x_name,work_x_id,mWorkstation_name;
    Button btnAssignWorkX;
    private static SpinnerSet[] workxSpin;
    TextView TxtRegisterWorkX,txtStudent_Name;
    private Spinner inputSpinnerWorkX;
    private TextView lblView;
    String mwX_student_name4;
    final ArrayList<ListarClientes> datalist = new ArrayList<>();
    int selected_Workstation_id;
    MChangeSWorkXA thisClass;


    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId){
        baseApcContext = cnt;CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        thisClass = this;
        printLogs(lt,"setBaseApcContextParent","weAreIn");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);
        Bundle activeInputUri = getIntent().getExtras();
        student_id = activeInputUri.getString("student_id");
        work_x_name = activeInputUri.getString("work_x_name");
        work_x_id = activeInputUri.getString("work_x_id");
        mwX_student_name4 = activeInputUri.getString("mwX_student_name4");
        mWorkstation_name = activeInputUri.getString("mWorkstation_name");
        generator = activeInputUri.getString("generator");

        printLogs(LogTag,"onCreate","student_id "+student_id);
        printLogs(LogTag,"onCreate","work_x_name "+work_x_name);
        printLogs(LogTag,"onCreate","work_x_id "+work_x_id);
        printLogs(LogTag,"onCreate","mwX_student_name4 "+mwX_student_name4);
        printLogs(LogTag,"onCreate","mWorkstation_name "+mWorkstation_name);
        printLogs(LogTag,"onCreate","generator "+generator);
        bootStrapInit();
    }
    @Override
    protected void bootStrapInit() {
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext, activityIn);
        if(isConnected) {
            printLogs(LogTag,"bootStrapInit","initConnected");
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);
            initMenusCustom(ActivityId,baseApcContext,activityIn);
            fetchVersionData();
            verifyVersion();
            internetChangeBroadCast();
            initializeViews();
            initBackBtn();
            showProgress(true,mContentView,mProgressView);
            initializeLabels();
            initializeListeners();
            userToken =userSessionObj.getToken();
            syncToken(baseApcContext,activityIn);
            if(TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
            callDataApi();
            //fetchData();
            fetchWorkstation();
            initializeInputs();
            printLogs(LogTag,"bootStrapInit","exitConnected");
            showProgress(false,mContentView,mProgressView);
        }
    }

    @Override
    protected void internetChangeBroadCast(){
        printLogs(LogTag,"internetChangeBroadCast","init");
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
            Intent intent = new Intent(MChangeSWorkXA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","a_m_change_s_works");
        setContentView(R.layout.a_m_change_s_works);
    }
    @Override
    protected void initializeViews() {
        txtStudent_Name = (TextView) findViewById(R.id.txtStudent_Name);
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        heading = findViewById(R.id.heading);
        inputSpinnerWorkX = (Spinner) findViewById(R.id.inputSpinnerWorkX);
        btnAssignWorkX = (Button) findViewById(R.id.btnAssignWorkX);
        TxtRegisterWorkX= (TextView) findViewById(R.id.btnRegisterWorkX);
        txtStudent_Name.setText(mwX_student_name4);
    }

    @Override
    protected void initializeListeners() {

        inputSpinnerWorkX.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ListarClientes myModel = (ListarClientes) parent.getSelectedItem();
                selected_Workstation_id = Integer.parseInt(myModel.getId());

                printLogs(LogTag,"setOnItemSelectedListener","selected_Workstation_id"+selected_Workstation_id);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnAssignWorkX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });

        TxtRegisterWorkX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                     Bundle inputUri = new Bundle();
                     inputUri.putString("generator", "313");
                     inputUri.putString("student_id", student_id);
                     inputUri.putString("work_x_name", work_x_name);
                     inputUri.putString("work_x_id", work_x_id);
                     inputUri.putString("mwX_student_name4", mwX_student_name4);
                     Intent intent = new Intent(MChangeSWorkXA.this,MAddWorksA.class);
                     intent.putExtras(inputUri);
                    startActivity(intent);
                    printLogs(LogTag,"onOptionsItemSelected","MAddWorksA"+generator);
                    finish();

            }
        });
    }

    @Override
    protected void initializeLabels(){

        String  Label = getLabelFromDb("lbl_313_wx_btn_save",R.string.lbl_313_wx_btn_save);
        lblView = (TextView)findViewById(R.id.btnAssignWorkX);
        btnAssignWorkX.setText(Label);

        Label = getLabelFromDb("h_313",R.string.h_313);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_313_assign",R.string.lbl_313_assign);
        lblView = (TextView)findViewById(R.id.lblAssignedWork);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_313_btn_regis",R.string.lbl_313_btn_regis);
        lblView = (TextView)findViewById(R.id.btnRegisterWorkX);
        lblView.setText(Label);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            btnAssignWorkX.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            inputSpinnerWorkX.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
        }

    }

    @Override
    protected void initializeInputs(){

        printLogs(LogTag,"initializeInputs","init");
/*
        dbSetaObj = DbHelper.getInstance(this);
        Cursor resSTS = dbSetaObj.getAllData(DbSchema.TABLE_WORKX);
        int total_workx = resSTS.getCount();
        workxSpin = new SpinnerSet[total_workx];
        if(total_workx > 0) {
            int i=0;
            try {
                while (resSTS.moveToNext()) {
                    workxSpin[i] = new SpinnerSet();
                    workxSpin[i].setId(resSTS.getInt(resSTS.getColumnIndex(DbSchema.COLUMN_W_SERVER_ID)));
                    workxSpin[i].setName(resSTS.getString(resSTS.getColumnIndex(DbSchema.COLUMN_W_NAME)));
                    i++;
                }
            } finally {
                if (resSTS != null && !resSTS.isClosed()) {
                    resSTS.close();
                }
            }
        }
        adapterWorks = new SpinnerSetAdapter(MChangeSWorkXA.this,android.R.layout.simple_spinner_item,workxSpin);
        inputSpinnerWorkX.setAdapter(adapterWorks);*/




        SpinnerAdapter adapter = new SpinnerAdapter(MChangeSWorkXA.this, android.R.layout.simple_spinner_dropdown_item, datalist);
        inputSpinnerWorkX.setAdapter(adapter);
        
        
    }
    public void callDataApi(){
        printLogs(LogTag,"callDataApi","init");
        userToken = userSessionObj.getToken();
        if(!TextUtils.isEmpty(userToken)) {
            printLogs(LogTag,"callDataApi","Token Update");
            syncUserData(baseApcContext, activityIn);
            syncMentorData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn,ActivityId);

        }
    }

    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void fetchWorkstation() {
       // String WorkStationName = work_x_id;

        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.DROPDOWN_WORKSTATION;
        FINAL_URL=FINAL_URL+"?token="+token;//+"/WorkStationName/"+WorkStationName;
        printLogs(LogTag,"fetchData","URL : "+FINAL_URL);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchData", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){

                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        for(int i=0;i<dataM.length();i++) {
                            JSONObject rec = dataM.getJSONObject(i);
                            ListarClientes GetDatadp= new ListarClientes();
                            GetDatadp.setName(rec.getString("workstation_name"));
                            GetDatadp.setId(rec.getString("workstation_id"));
                            datalist.add(GetDatadp);
                            showProgress(false, mContentView, mProgressView);
                        }
                         SpinnerAdapter adapter = new SpinnerAdapter(MChangeSWorkXA.this, android.R.layout.simple_spinner_dropdown_item, datalist);
                        inputSpinnerWorkX.setAdapter(adapter);
                    }
                    else if(Status.equals("2")) {
                        showProgress(false,mContentView,mProgressView);
                    }

                    else{
                        
                        //showProgress(false,mContentView,mProgressView);
                        printLogs(LogTag, "fetchData", "error_try_again : DATA_ERROR");
                        String sTitle="Error :"+ActivityId+"-108";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    printLogs(LogTag, "fetchData", "error_try_again : " + e.getMessage());
                    e.printStackTrace();
                    //showProgress(false,mContentView,mProgressView);
                    String sTitle = "Error :" + ActivityId + "-109";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                printLogs(LogTag, "fetchData", "error_try_again: " + error.getMessage());
                String sTitle = "Error :" + ActivityId + "-110";
                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);

            }
        }) {
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



/*    private void fetchData(){
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.MENTOR_STUDENT_DATA_URL;
        FINAL_URL=FINAL_URL+"/token/"+token+"/student_id/"+ student_id;
        printLogs(LogTag,"fetchData","URL : "+FINAL_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,FINAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject outputJson = null;
                try {
                    printLogs(LogTag,"fetchData","RESPONSE : "+response);
                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONObject dataM = outputJson.getJSONObject(KEY_DATA);
                      *//*  String student_name = dataM.getString("student_name");
                        txtStudent_Name.setText(student_name);*//*
                    }
                    else{
                        //showProgress(false,mContentView,mProgressView);
                        printLogs(LogTag,"fetchData","ERROR_313_100 : DATA_ERROR");
                        String sTitle="Error :"+ActivityId+"-100";
                        String sMessage=getLabelFromDb("error_313_100",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    printLogs(LogTag,"fetchData","ERROR_313_101 : "+e.getMessage());
                    e.printStackTrace();
                    //showProgress(false,mContentView,mProgressView);
                    String sTitle="Error :"+ActivityId+"-101";
                    String sMessage=getLabelFromDb("error_313_101",R.string.error_313_101);
                    String sButtonLabelClose="Close";
                    ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        printLogs(LogTag,"fetchData","ERROR_313_102 : "+error.getMessage());
                        //showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-102";
                        String sMessage=getLabelFromDb("error_313_102",R.string.error_313_102);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

                    }
                })
        {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(MChangeSWorkXA.this);
        requestQueue.add(stringRequest);
    }*/



    public void validateForm() {
        boolean cancel = false;
        if(!isSpinnerValid(selected_Workstation_id)){
            cancel = true;
        }

        if (cancel) {
            return;
        } else {
            showProgress(true,mContentView,mProgressView);
            this.FormSubmit();
        }
        showProgress(true,mContentView,mProgressView);
        this.FormSubmit();
        printLogs(LogTag,"validateForm","exit");
    }

    public void FormSubmit() {
        final int abc = selected_Workstation_id;
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.MCHANGE_WORKSTATION_DETAILS;
        printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put("student_id" ,student_id);
            jsonBody.put(KEY_DEPARTMENT_ID , String.valueOf(abc));
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
                    if(Status.equals("1")){
                        //JSONObject dataM = outputJson.getJSONObject(KEY_DATA);

                        printLogs(LogTag,"FormSubmit","SUCCESS : "+Status);
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="";
                        String sMessage=getLabelFromDb("m_313_message",R.string.m_313_message);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showSuccessDialogMChangeSWorkXA(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose,thisClass);

                    }

                    else{
                        showProgress(false,mContentView,mProgressView);
                        String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                       /* mPasswordView.setError(sMessage);
                        mPasswordView.requestFocus();*/
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false,mContentView,mProgressView);
                    String sTitle="Error :"+ActivityId+"-105";
                    String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                    String sButtonLabelClose="Close";
                    ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String sTitle="Error :"+ActivityId+"-105";
                        String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                        //Toast.makeText(getApplicationContext(),"ERROR LOGIN IN. PLEASE TRY AGAIN.", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/json; charset=utf-8");
                header.put("Accept","*/*");
                return header;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MChangeSWorkXA.this);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    public void customRedirector() {

/*        if(generator.equals("303")){
            Bundle inputUri = new Bundle();
            int work_x_idINT = Integer.parseInt(work_x_id);
            inputUri.putInt("work_x_id", work_x_idINT);
            inputUri.putString("work_x_name", work_x_name);
            Intent intent = new Intent(MChangeSWorkXA.this,MAssignedSA.class);
            printLogs(LogTag,"onOptionsItemSelected","MAssignedSA");
            intent.putExtras(inputUri);
            startActivity(intent);
            finish();
        }else if(generator.equals("304")){
            Intent intent = new Intent(MChangeSWorkXA.this,MNonAssignSA.class);
            printLogs(LogTag,"onOptionsItemSelected","MNonAssignSA");
            startActivity(intent);
            finish();
        }*/
        //else {
            Intent intent = new Intent(MChangeSWorkXA.this, MDashboardDA.class);
            printLogs(LogTag, "onOptionsItemSelected", "MDashboardDA");
            startActivity(intent);
            finish();
        //}
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {

                Intent intent = new Intent(MChangeSWorkXA.this, MDashboardDA.class);
                printLogs(LogTag, "onOptionsItemSelected", "MDashboardDA");
                startActivity(intent);
                finish();
            //}
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
