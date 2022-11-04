package za.co.sacpo.grant.xCubeMentor.training;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.ListarClientes;
import za.co.sacpo.grant.xCubeLib.adapter.SpinnerAdapter;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeMentor.MDashboardDA;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class MTrainingPlanA extends BaseFormAPCPrivate {

    private String ActivityId="M313";
    public View mProgressView, mContentView;
    public String generator,student_id,student_name;
    int selected_Training_plan;
    Button btnSave , btnAddTraining , btnTariningList  ;
    public Spinner Spiner_training_plan ;
    TextView txtStudent_Name;
    private TextView lblView;
    public WebView wv_information;
    final ArrayList<ListarClientes> datalist = new ArrayList<>();
    MTrainingPlanA thisClass;

    public String KEY_TRAINING_PLAN = "training_plan";

    @Override
    protected void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cTAId) {
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
        printLogs(LogTag,"onCreate","init");
        Intent inputIntent = getIntent();
        inBundle = getIntent().getExtras();
        student_id="";
        student_name="";
        if (inputIntent.hasExtra("student_id")) {
            student_id = inBundle.getString("student_id");
        }
        student_name = inBundle.getString("mwX_student_name4");

        generator = inBundle.getString("generator");
        printLogs(LogTag,"onCreate","GROUP ID "+student_id+student_name);
        bootStrapInit();
    }

    @Override
    protected void bootStrapInit() {
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext,activityIn);
        if(isConnected) {
            printLogs(LogTag,"bootStrapInit","initConnected");
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);
            initMenusCustom(ActivityId,baseApcContext,activityIn);
            initBackBtn();
            initializeViews();
            showProgress(true,mContentView,mProgressView);
            initializeLabels();
            initializeListeners();
            userToken =userSessionObj.getToken();
            syncToken(baseApcContext,activityIn);
            if(TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
            callDataApi();
            initializeInputs();
            fetchTrainingPlan();
            showProgress(false,mContentView,mProgressView);
        }
        showProgress(false,mContentView,mProgressView);
        printLogs(LogTag,"bootStrapInit","exitConnected");

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
            Intent intent = new Intent(MTrainingPlanA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","a_m_training_plan");
        setContentView(R.layout.a_m_training_plan);
    }
    @Override
    protected void initializeViews() {

        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        txtStudent_Name = (TextView) findViewById(R.id.txtStudent_Name);
        btnSave = (Button) findViewById(R.id.btnSave);
        Spiner_training_plan = findViewById(R.id.Spiner_training_plan);
        btnAddTraining = (Button) findViewById(R.id.btnAddTraining);
        btnTariningList = (Button) findViewById(R.id.btnTrainingList);
        txtStudent_Name.setText(student_name);

        /*inputTitle = findViewById(R.id.inputTitle);
        inputLayoutTitle = findViewById(R.id.inputLayoutTitle);
        btnBrows = findViewById(R.id.btnPickFile);*/

        wv_information = findViewById(R.id.wv_information);
        final WebSettings webSettingsF = wv_information.getSettings();
        Resources res = getResources();
        float fontSize = res.getDimension(R.dimen.tiniestTextSize);
        webSettingsF.setDefaultFontSize((int)fontSize);
        wv_information.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        wv_information.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
    }

    @Override
    protected void initializeListeners() {
        /*Todo:Hold for now*/
        //btnAddTraining.setVisibility(View.GONE);
        //btnTariningList.setVisibility(View.GONE);
        btnTariningList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle inputUri = new Bundle();
                inputUri.putString("student_id", student_id);
                inputUri.putString("m_student_name", student_name);
                inputUri.putString("generator", ActivityId);
                Context context = view.getContext();
                Intent intent = new Intent(MTrainingPlanA.this, MExistingTrainingPlanA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });

        btnAddTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle inputUri = new Bundle();
                inputUri.putString("student_id", student_id);
                inputUri.putString("m_student_name", student_name);
                inputUri.putString("generator", ActivityId);
                Context context = view.getContext();
                Intent intent = new Intent(MTrainingPlanA.this, MAddTrainingPlanA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);
            }
        });

        Spiner_training_plan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ListarClientes myModel = (ListarClientes) parent.getSelectedItem();
                selected_Training_plan = Integer.parseInt(myModel.getId());
                printLogs(LogTag,"setOnItemSelectedListener","selected_EmpStatus"+selected_Training_plan);

            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
     /*   btnBrows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                fileChooser();
            }
        });
*/
    }


    @Override
    protected void initializeLabels() {

        String  Label = getLabelFromDb("h_M313",R.string.h_M313);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);

        Label = getLabelFromDb("h_M313",R.string.i_M313_message);
        wv_information.loadData(Label, "text/html", "UTF-8");


        Label = getLabelFromDb("tv_M313_title",R.string.tv_M313_title);
        lblView = (TextView)findViewById(R.id.lblTitle);
        lblView.setText(Label);

        Label = getLabelFromDb("btn_M313_save",R.string.btn_M313_save);
        lblView = (TextView)findViewById(R.id.btnSave);
        lblView.setText(Label);

        Label = getLabelFromDb("btn_M313_add",R.string.btn_M313_add);
        btnAddTraining.setText(Label);

        Label = getLabelFromDb("btn_M313_list",R.string.btn_M313_list);
        btnTariningList.setText(Label);


    }

    @Override
    protected void initializeInputs() {
        SpinnerAdapter adapter = new SpinnerAdapter(MTrainingPlanA.this, android.R.layout.simple_spinner_dropdown_item, datalist);
        Spiner_training_plan.setAdapter(adapter);
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

    private void fetchTrainingPlan() {

        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.M_REF_313_1 + "?token=" + token;
        printLogs(LogTag,"fetchData","URL : "+FINAL_URL);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "getMentorWorkx", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        for(int i=0;i<dataM.length();i++) {
                            JSONObject rec = dataM.getJSONObject(i);
                            ListarClientes GetDatadp= new ListarClientes();
                            GetDatadp.setName(rec.getString("title"));
                            GetDatadp.setId(rec.getString("id"));
                            datalist.add(GetDatadp);
                            showProgress(false, mContentView, mProgressView);
                        }
                        //  inputSpinnerGrant.setAdapter(new ArrayAdapter<String>(GAProcessStipendClaimA.this, android.R.layout.simple_spinner_dropdown_item, SetaName));
                        SpinnerAdapter adapter = new SpinnerAdapter(MTrainingPlanA.this, android.R.layout.simple_spinner_dropdown_item, datalist);
                        Spiner_training_plan.setAdapter(adapter);
                    }
                    else if(Status.equals("2")) {
                        showProgress(false,mContentView,mProgressView);
                    }

                    else{
                        //showProgress(false,mContentView,mProgressView);
                        printLogs(LogTag, "fetchData", "error_try_again : DATA_ERROR");
                        String sTitle="Error :"+ActivityId+"-107";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    printLogs(LogTag, "fetchData", "error_try_again : " + e.getMessage());
                    e.printStackTrace();
                    //showProgress(false,mContentView,mProgressView);
                    String sTitle = "Error :" + ActivityId + "-108";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                printLogs(LogTag, "fetchData", "error_try_again: " + error.getMessage());
                String sTitle = "Error :" + ActivityId + "-109";
                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);

            }
        }){
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
    private void fileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //intent.setType("application/pdf");   //ONLY PDF
        intent.setType("*/*");   //ALL FILE
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), 1);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(MTrainingPlanA.this, MDashboardDA.class);
            printLogs(LogTag,"onOptionsItemSelected","SDashboardDA");
            startActivity(intent);
            finish();
        }
        return true;
    }

    public void validateForm() {
        FormSubmit();
        printLogs(LogTag,"validateForm","exit");

    }
    @Override
    protected void FormSubmit() {
        final int training_plan = selected_Training_plan ;
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.M_REF_313_2;
        printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put("student_id", student_id);
            jsonBody.put("training_id", training_plan);
            jsonBody.put(KEY_TRAINING_PLAN, String.valueOf(training_plan));
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
                        printLogs(LogTag,"FormSubmit","SUCCESS : "+Status);
                        showProgress(false,mContentView,mProgressView);
                        String sTitle=getLabelFromDb("m_M131_title",R.string.m_M131_title);
                        String sMessage=getLabelFromDb("m_M131_message",R.string.m_M131_message);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showSuccessDialogAssignTrainingPlan(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose,thisClass);
                    }  else{
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-104";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    showProgress(false,mContentView,mProgressView);
                    String sTitle="Error :"+ActivityId+"-105";
                    String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                    String sButtonLabelClose="Close";
                    ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-106";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }


    public void customRedirector() {
        Intent intent = new Intent(MTrainingPlanA.this, MDashboardDA.class);
        Bundle inputUri = new Bundle();
        intent.putExtras(inputUri);
        startActivity(intent);
        finish();
    }

  /*  private void uploadPDF(final String title ,final String pdfname, Uri pdffile){
        InputStream iStream = null;
        final String title_name  ;
        title_name = title ;
        try {
            iStream = getContentResolver().openInputStream(pdffile);
            final byte[] inputData = getBytes(iStream);

            printLogs(LogTag,"uploadPDF","pdffile : "+pdffile);
            printLogs(LogTag,"uploadPDF","pdfname : "+pdfname);
            printLogs(LogTag,"uploadPDF","title : "+title);

            String token = userSessionObj.getToken();
            // url Is Empty  for upload PDF....
            String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.M_REF_313;
            FINAL_URL=FINAL_URL+"/token/"+token+"/title/"+title+"/file/"+pdffile;
            printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);

            //loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);

            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, FINAL_URL,

                    new Response.Listener<NetworkResponse>() {

                        @Override
                        public void onResponse(NetworkResponse response) {
                            //Log.d("ressssssoo",new String(response.data));
                            printLogs(LogTag,"uploadPDF","URL : "+UPLOAD_DOCUMENTS_BY_LEARNER);
                           // rQueue.getCache().clear();

                            try {
                                JSONObject jsonObject = new JSONObject(new String(response.data));

                                jsonObject.toString().replace("\\\\","");

                                if (jsonObject.getString(KEY_STATUS).equals("1")) {
                                    showProgress(false,mContentView,mProgressView);
                                    String sTitle=getLabelFromDb("m_M131_title",R.string.m_M131_title);
                                    String sMessage=getLabelFromDb("m_M131_message",R.string.m_M131_message);
                                    String sButtonLabelClose="Close";
                                    ErrorDialog.showSuccessDialogAddTrainingPlan(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose,thisClass);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                               // loading.dismiss();
                                showProgress(false,mContentView,mProgressView);
                                String sTitle="Error :"+ActivityId+"-104";
                                String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                                String sButtonLabelClose="Close";
                                ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //loading.dismiss();
                            showProgress(false,mContentView,mProgressView);
                            String sTitle="Error :"+ActivityId+"-105";
                            String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                            String sButtonLabelClose="Close";
                            ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                        }
                    }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    return params;
                }
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();

                    String pdfName = System.currentTimeMillis() + ".pdf";
                    params.put("filename", new DataPart(pdfName, inputData,extension));
                    return params;
                }
            };

            volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(MTrainingPlanA.this);
            requestQueue.add(volleyMultipartRequest);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/
  /* public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }*/
    /*public boolean validateTitle(EditText inputTitlet,TextInputLayout inputLayoutTitle) {
        String  Title = inputTitlet.getText().toString().trim();
        setCustomError(inputLayoutTitle,null,inputTitlet);
        if (Title.isEmpty() ) {
            String sMessage = getLabelFromDb("error_title",R.string.error_title);
            setCustomError(inputLayoutTitle,sMessage,inputTitlet);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutTitle,inputTitlet);
            return true;
        }
    }*/
    public String getStudentName(){
        return student_name;
    }
    public String getStudentId(){ return student_id; }

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
