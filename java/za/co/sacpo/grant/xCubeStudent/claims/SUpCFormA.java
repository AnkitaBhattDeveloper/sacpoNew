package za.co.sacpo.grant.xCubeStudent.claims;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import java.util.Map;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.permision.PermissionsChecker;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;

public class SUpCFormA extends BaseAPCPrivate implements View.OnClickListener {
    private String ActivityId="129";
    public View mProgressView, mContentView;

    private String KEY_EXTENTION = "pdf";
    private String KEY_FILE_NAME = "uploaded_file";
    private String KEY_ID= "stipend_id";
    File file;
    private Bitmap bitmap;
    String extension;
    String filename;
    String stipend_id="4";
    String s1,s2;
    TextView txtFile;

    private String KEY_IMAGE = "extension";
    private TextView lblView, lblBankDetails, lblBankName, txtBankName, lblSurname, txtSurname, lblAccountType, txtAccountType,
            lblAccountNumber, txtAccNumb, lblBranchCode, txtBranchCode,lblInitialName,txtInitialName;

    private CheckBox checkbox;

    /*http://seta.local/api/user/type/uploadImage/token/123ha?uploaded_file=&extension=img*/
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int PICK_IMAGE_REQUEST= 99;
    PermissionsChecker checker;
    String generator;
    Button upload;
    public Button btnChoose,btnFileUpload;
    private LinearLayout buttonContainer;
    ImageView headLogo;


    private Uri filePath;

   // private String UPLOAD_URL ="http://demo.setaportal.co.za/api/user/type/uploadImage";


    @Override
    protected void bootStrapInit() {

        printLogs(LogTag,"bootStrapInit","bootStrap-In");
       /* Bundle activeInputUri = getIntent().getExtras();

        stipend_id = activeInputUri.getString("stipend_id");
        generator = activeInputUri.getString("generator");

        printLogs(LogTag,"onCreate","STIPEND ID "+stipend_id);
        printLogs(LogTag,"onCreate","GENERATOR ID"+generator);*/

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutXml();
        printLogs(LogTag,"onCreate","onCreate-In");
        // callFooter(baseApcContext,activityIn,ActivityId);
//        initMenusCustom(ActivityId,baseApcContext,activityIn);
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);
        validateLogin(baseApcContext,activityIn);
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        if(isConnected) {
            fetchVersionData();
            verifyVersion();
            internetChangeBroadCast();
            initializeViews();
            initBackBtn();
            showProgress(true,mContentView,mProgressView);
            initializeListeners();
            initializeLabels();
            initializeInputs();

            printLogs(LogTag,"bootStrapInit","exitConnected");
            showProgress(false,mContentView,mProgressView);
            userToken =userSessionObj.getToken();
            syncToken(baseApcContext,activityIn);
            if(TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
            callDataApi();
            initializeInputs();
            printLogs(LogTag,"bootStrapInit","exitConnected");
            showProgress(false,mContentView,mProgressView);
        }
    }

    @Override
    protected void internetChangeBroadCast(){
        printLogs(LogTag,"internetChangeBroadCast","internetChangeBroadCast-In");
        registerBroadcastIC();
    }
    @Override
    protected void fetchVersionData(){
        printLogs(LogTag,"fetchVersionData","fetchVersionData-In");
        syncUpdates(baseApcContext,activityIn);
    }
    @Override
    protected void verifyVersion(){
        printLogs(LogTag,"verifyVersion","init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();

        if(isUpdate){
            Intent intent = new Intent(SUpCFormA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cTAId){
        baseApcContext = cnt;CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
    }
    @Override
    protected void initializeInputs(){

        printLogs(LogTag,"initializeInputs","init");
        fetchData();
    }

    @Override
    protected void initializeLabels(){

        printLogs(LogTag,"initializeLabels","initializeLabels-In");
        String Label = getLabelFromDb("l_129_101_btn",R.string.l_129_101_btn);
        btnChoose.setText(Label);

        Label = getLabelFromDb("h_129",R.string.h_129);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);

        Label = getLabelFromDb("l_129_102_btn",R.string.l_129_102_btn);
        btnFileUpload.setText(Label);

         Label = getLabelFromDb("lbl_129_lblBankDetails", R.string.lbl_129_lblBankDetails);
         lblBankDetails = (TextView) findViewById(R.id.lblBankDetails);
         lblBankDetails.setText(Label);

        Label = getLabelFromDb("lbl_129_lblBankName", R.string.lbl_129_lblBankName);
        lblView = (TextView) findViewById(R.id.lblBankName);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_129_lblSurname", R.string.lbl_129_lblSurname);
        lblView = (TextView) findViewById(R.id.lblSurname);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_129_lblAccountType", R.string.lbl_129_lblAccount_type);
        lblView = (TextView) findViewById(R.id.lblAccountType);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_129_lblAccountNumber", R.string.lbl_129_lblAccountNumber);
        lblView = (TextView) findViewById(R.id.lblAccountNumber);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_129_lblBranchCode", R.string.lbl_129_lblBranchCode);
        lblView = (TextView) findViewById(R.id.lblBranchCode);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_129_lblInitial_name", R.string.lbl_129_lblInitial_name);
        lblView = (TextView) findViewById(R.id.lblInitialName);
        lblView.setText(Label);



    }

    @Override
    protected void setLayoutXml() {

        printLogs(LogTag,"setLayoutXml","setLayoutXml-In");
        setContentView(R.layout.a_upload_claim_form);

    }

    @Override
    protected void initializeViews() {

        printLogs(LogTag,"initializeViews","initializeViews-In");

        mContentView = findViewById(R.id.content_container);
        checker = new PermissionsChecker(this);
        mProgressView = findViewById(R.id.progress_bar);

        lblBankDetails = (TextView) findViewById(R.id.lblBankDetails);
        lblBankDetails = (TextView) findViewById(R.id.lblBankDetails);
        lblBankName = (TextView) findViewById(R.id.lblBankName);
        txtBankName = (TextView) findViewById(R.id.txtBankName);
        lblSurname = (TextView) findViewById(R.id.lblSurname);
        txtSurname = (TextView) findViewById(R.id.txtSurname);
        lblAccountType = (TextView) findViewById(R.id.lblAccountType);
        txtAccountType = (TextView) findViewById(R.id.txtAccountType);
        lblAccountNumber = (TextView) findViewById(R.id.lblAccountNumber);
        txtAccNumb = (TextView) findViewById(R.id.txtAccNumb);
        lblBranchCode = (TextView) findViewById(R.id.lblBranchCode);
        txtBranchCode = (TextView) findViewById(R.id.txtBranchCode);
        lblInitialName = (TextView) findViewById(R.id.lblInitialName);
        txtInitialName = (TextView) findViewById(R.id.txtInitialName);

        btnFileUpload = (Button) findViewById(R.id.btnFileUpload);
        buttonContainer = (LinearLayout) findViewById(R.id.buttonContainer);
        btnChoose = (Button)findViewById(R.id.btnChoose);
        headLogo = (ImageView)findViewById(R.id.headLogo);
        txtFile = (TextView) findViewById(R.id.txtFile);

        checkbox = (CheckBox) findViewById(R.id.checkbox);

        btnFileUpload.setOnClickListener(this);
        btnChoose.setOnClickListener(this);
    }
    @Override
    protected void initializeListeners() {
        printLogs(LogTag,"initializeListeners","initializeListeners-In");
      /*  btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        btnFileUpload.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {


                printLogs(LogTag,"initializeListeners","initializeListeners-In");

            }
        });*/

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (checkbox.isChecked() ==true){

                   buttonContainer.setVisibility(View.VISIBLE);

               }else {
                   buttonContainer.setVisibility(View.GONE);
               }
            }
        });

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                // Code for above or equal 23 API Oriented Device
                // Your Permission granted already .Do next code
            } else {
                requestPermission();
            }
        }

    }


    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(SUpCFormA.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(SUpCFormA.this, " Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(SUpCFormA.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(SUpCFormA.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(SUpCFormA.this, "Permission Granted Successfully! ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SUpCFormA.this, "Permission Denied 🙁 ", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {

        if(v == btnChoose){
            showFileChooser();
        }

        if(v == btnFileUpload){
            uploadImage();
        }
    }


    private void showFileChooser() {


        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        txtFile.setVisibility(View.VISIBLE);
        startActivityForResult(Intent.createChooser(intent, "Select PDF"), PICK_IMAGE_REQUEST);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        btnFileUpload.setVisibility(View.VISIBLE);
     //   putapi();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            Uri filePath = data.getData();
            filename=filePath.getLastPathSegment();
            txtFile.setText(filename);
            file = new File(filePath.toString());
            getStringFile(file);

        }
    }

    public String getStringFile(File f)
    {
        StringBuilder sb = new StringBuilder();
        try
        {
            FileInputStream fileInputStream = new FileInputStream(file);

            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));

            String line ;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line).append("\n");
                reader.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return sb.toString();
    }


    public void uploadImage(){

        String token = userSessionObj.getToken();

        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.UPLOADFORM_URL;

        FINAL_URL=FINAL_URL+"/token/"+token+"/stipend_id/"+stipend_id;

        printLogs(LogTag,"error_try_again","URL : "+FINAL_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,FINAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject outputJson = null;
                try {
                    printLogs(LogTag,"fetchData","RESPONSE : "+response);
                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        showProgress(false,mContentView,mProgressView);
                        String sTitle=getLabelFromDb("m_129_title",R.string.m_129_title);
                        String sMessage=getLabelFromDb("m_129_message",R.string.m_129_message);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                    else{
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-101";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    showProgress(false,mContentView,mProgressView);
                    String sTitle="Error :"+ActivityId+"-102";
                    String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                    String sButtonLabelClose="Close";
                    ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showProgress(false,mContentView,mProgressView);
                String sTitle=getLabelFromDb("m_129_title2",R.string.m_129_title2);
                String sMessage=getLabelFromDb("error_129_103",R.string.error_129_103);
                String sButtonLabelClose="Close";
                ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<String,String>();
                String pdf = getStringFile(file);
                //Adding parameters
                 params.put("KEY_EXTENTION", pdf);
                 params.put("KEY_FILE_NAME", filename);
                 params.put("KEY_ID", stipend_id);



                //returning parameters
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(SUpCFormA.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }
    private void initBackBtn() {
        printLogs(LogTag, "initBackBtn", "init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void callDataApi(){
        printLogs(LogTag,"callDataApi","init");
        userToken = userSessionObj.getToken();
        if(!TextUtils.isEmpty(userToken)) {
            printLogs(LogTag,"callDataApi","Token Update");
            syncUserData(baseApcContext, activityIn);
            syncStudentData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn,ActivityId);
        }
    }

    private void fetchData(){
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_121+ "?token=" + token;
        printLogs(LogTag, "fetchData", "URL : " + FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject outputJson = null;
                try {
                    printLogs(LogTag, "getGrantDetails", "RESPONSE : " + response);
                    outputJson = new JSONObject(String.valueOf(response));
                    String Status = outputJson.getString(KEY_STATUS);
                    if (Status.equals("1")) {
                        JSONObject dataM = outputJson.getJSONObject(KEY_DATA);
                        if (dataM.length() > 0) {
                            txtBankName.setText(dataM.getString("bank_name"));
                            txtInitialName.setText(dataM.getString("initial_name"));
                            txtSurname.setText(dataM.getString("b_d_surname"));
                            txtAccountType.setText(dataM.getString("account_type"));
                            txtAccNumb.setText(dataM.getString("account_no"));
                            txtBranchCode.setText(dataM.getString("branch_code"));
                        }
                        showProgress(false, mContentView, mProgressView);
                    } else if (Status.equals("2")) {
                        showProgress(false, mContentView, mProgressView);
                    } else {
                        //showProgress(false,mContentView,mProgressView);
                        printLogs(LogTag, "fetchData", "error_try_again : DATA_ERROR");
                        String sTitle = "Error :" + ActivityId + "-100";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
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
                printLogs(LogTag, "fetchData", "error_129_106 : " + error.getMessage());
                String sTitle = "Error :" + ActivityId + "-103";
                String sMessage = getLabelFromDb("error_129_106", R.string.error_129_106);
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
        RequestQueue queue = Volley.newRequestQueue(this);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjectRequest);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(SUpCFormA.this, SPastClaimDA.class);
            printLogs(LogTag,"onOptionsItemSelected","SClaimDA");
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
