package za.co.sacpo.grant.xCubeMentor.training;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.util.IOUtils;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MEditTrainingPlanA extends BaseFormAPCPrivate {
    private String ActivityId="M345";
    public EditText inputTitle ;
    public TextInputLayout inputLayoutTitle ;
    public View mProgressView, mContentView,heading;
    public String  student_id,student_name;;

    public Button btnBrows , btnSubmit ;
    private TextView lblView , lblFileName ;
    public  String training_id ,training_title, generator ;
    MEditTrainingPlanA thisClass;
    private String extension;
    private File myFile;
    String filename;
    String displayName;
    Uri fileUriVal, cachedFileUriVal;
    private RequestQueue rQueue;
    int MY_PERMISSIONS_REQUEST_STORAGE =1;
    private Boolean permissionStorage;
    private ArrayList<HashMap<String, String>> arraylist;

    public String KEY_TRAINING_PLAN = "title";

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
        Bundle activeInputUri = getIntent().getExtras();
        if (activeInputUri != null) {
            training_id = activeInputUri.getString("training_id");
            training_title = activeInputUri.getString("training_title");
            generator = activeInputUri.getString("generator");
        }
        printLogs(LogTag,"oncreate","training_id "+training_id+"/generator/"+generator);
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
            initializeInputs();
            userToken =userSessionObj.getToken();
            syncToken(baseApcContext,activityIn);
            if(TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
            callDataApi();
            //fetchData();
            initializeListeners();
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
    protected void fetchVersionData(){
        printLogs(LogTag,"fetchVersionData","exitConnected");
        syncUpdates(baseApcContext,activityIn);
    }
    @Override
    protected void verifyVersion() {
        printLogs(LogTag,"verifyVersion","init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag,"verifyVersion","isUpdate "+isUpdate);
        printLogs(LogTag,"verifyVersion","isUpdate "+ utilSessionObj.getIsUpdateRequired());
        if(isUpdate){
            Intent intent = new Intent(MEditTrainingPlanA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void setLayoutXml(){
        printLogs(LogTag,"setLayoutXml","a_m_edit_notelist");
        setContentView(R.layout.a_medit_training_plan);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");

        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        heading = findViewById(R.id.heading);
        inputTitle =  findViewById(R.id.inputTitle);
        inputTitle.setText(training_title);
        inputLayoutTitle =  findViewById(R.id.inputLayouTitle);
        btnBrows = findViewById(R.id.btnBrows);
        btnSubmit = findViewById(R.id.btnSubmit);
        lblFileName = findViewById(R.id.lblFileName);
        btnSubmit.setVisibility(View.GONE);

    }



    @Override
    protected void initializeListeners() {
        btnBrows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileChooser();
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });
    }

    @Override
    protected void initializeInputs() {

    }

    @Override
    protected void initializeLabels() {
        printLogs(LogTag,"initializeLabels","init");
        String  Label = getLabelFromDb("h_M345",R.string.h_M345);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_M345_title",R.string.lbl_M345_title);
        lblView = (TextView)findViewById(R.id.lblTitle);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("b_M345_brows", R.string.b_M345_brows);
        lblView = (TextView) findViewById(R.id.btnBrows);
        lblView.setText(Label);

        Label = getLabelFromDb("b_M345_submit", R.string.b_M345_submit);
        lblView = (TextView) findViewById(R.id.btnSubmit);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_M345_nofileChosen", R.string.lbl_M345_nofileChosen);
        lblView = (TextView) findViewById(R.id.lblFileName);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

                Label = getLabelFromDb("lbl_M345_pdf", R.string.lbl_M345_pdf);
        lblView = (TextView) findViewById(R.id.lblPdf);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            btnSubmit.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            btnBrows.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            inputTitle.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
        }

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



    @Override
    protected void validateForm() {
        boolean cancel = false;
        if (!validateTitle(inputTitle, inputLayoutTitle)) {
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

    public boolean validateTitle(EditText inputTitle, TextInputLayout inputLayoutTitle) {
        String title = inputTitle.getText().toString().trim();
        setCustomError(inputLayoutTitle, null, inputTitle);
        if (title.isEmpty() || !isValidLName(title)) {
            String sMessage = getLabelFromDb("error_M346_name", R.string.error_M346_name);
            setCustomError(inputLayoutTitle, sMessage, inputTitle);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutTitle, inputTitle);
            return true;
        }
    }

    private void fetchData() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.M_REF_345_1;
        FINAL_URL=FINAL_URL+"/token/"+token+"/training_id/"+training_id;
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
                        inputTitle.setText(dataM.getString("title"));
                        showProgress(false, mContentView, mProgressView);
                    }
                    else if(Status.equals("2")) {
                        showProgress(false,mContentView,mProgressView);
                    }

                    else{
                        //showProgress(false,mContentView,mProgressView);
                        printLogs(LogTag, "fetchData", "error_M345_101 : DATA_ERROR");
                        String sTitle="Error :"+ActivityId+"-101";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
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
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(MEditTrainingPlanA.this);
        requestQueue.add(stringRequest);
    }

    private void fileChooser() {
        permissionStorage = false;
        printLogs(LogTag,"getLocation","init");
        checkStoragePermission();
        printLogs(LogTag,"getLocation","permissionStorage "+permissionStorage);


        if (permissionStorage == true) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf");   //ONLY PDF
            //intent.setType("*/*");   //ALL FILE
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            try {
                startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), 1);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getApplicationContext(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            fileUriVal = data.getData();
            cachedFileUriVal = fileUriVal;
            ///ownFile(fileUriVal);
            if (fileUriVal != null) {
                String uriValString = fileUriVal.toString();
                myFile = new File(uriValString);
                String path = myFile.getAbsolutePath();
                displayName = null;
                filename = fileUriVal.getLastPathSegment();
                String mimeType = getContentResolver().getType(fileUriVal);
                extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);

                // getStringFile(myFile);

                if (uriValString.startsWith("content://")) {
                    Cursor cursor = null;
                    try {
                        cursor = this.getContentResolver().query(fileUriVal, null, null, null, null);
                        if (cursor != null && cursor.moveToFirst()) {
                            displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                            lblFileName.setText(displayName);
                            printLogs(LogTag, "onActivityResult", "fileUriVal"+fileUriVal+"---displayName"+displayName);
                            ///ownFile(fileUriVal);
                            btnSubmit.setVisibility(View.VISIBLE);
                        }
                    } finally {
                        cursor.close();
                    }
                } else if (uriValString.startsWith("file://")) {
                    printLogs(LogTag, "onActivityResult", "fileUriVal"+fileUriVal+"---displayName"+displayName);
                    displayName = myFile.getName();
                    lblFileName.setText(displayName);
                    //ownFile(fileUriVal);
                }
            } else {
                Toast.makeText(thisClass, "Nothing Selected...", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void ownFile(Uri fileUri){
        printLogs(LogTag, "ownFile", "fileUri"+fileUri+"---displayName"+displayName);
        ParcelFileDescriptor pfd = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            try {
                pfd = getContentResolver().openFileDescriptor(fileUri, "r", null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(pfd !=null){
            InputStream istream = new FileInputStream(pfd.getFileDescriptor());
            File ifs = new File(getApplicationContext().getCacheDir(),displayName);
            OutputStream ostream = null;
            try {
                ostream = new FileOutputStream(ifs);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if(ostream != null){
                try {
                    IOUtils.copyStream(istream,ostream);
                    final File file = new File(getApplicationContext().getCacheDir(), displayName);
                    cachedFileUriVal = Uri.fromFile(file);
                    printLogs(LogTag, "ownFile", "cachedFileUriVal"+cachedFileUriVal+"---displayName"+displayName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        printLogs(LogTag,"onRequestPermissionsResult","init");
        switch (requestCode) {
            case 1: {
                printLogs(LogTag,"onRequestPermissionsResult","REQUEST CODE "+requestCode);
                permissionStorage = grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED;
                return;
            }
        }
    }
    private void checkStoragePermission(){
        printLogs(LogTag,"checkLocationPermission","init");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                printLogs(LogTag,"checkLocationPermission","PERMISSION ERROR");
            } else {
                printLogs(LogTag,"checkLocationPermission","PERMISSION INPUT FINE");
                ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSIONS_REQUEST_STORAGE);
            }
        } else {
            printLogs(LogTag,"checkLocationPermission","PERMISSION INPUT");
            permissionStorage = true;
        }
    }
    @Override
    protected void FormSubmit(){
        //Uri fileUriLocalVal = cachedFileUriVal;
        Uri fileUriLocalVal = fileUriVal;
        printLogs(LogTag, "ownFile", "fileUri"+fileUriLocalVal+"---displayName"+displayName);
        showProgress(true,mContentView,mProgressView);
        InputStream iStream = null;
        try {
            try {
                iStream = getContentResolver().openInputStream(fileUriLocalVal);
            } catch (FileNotFoundException e) {
                showProgress(false, mContentView, mProgressView);
                String sTitle = "Error :" + ActivityId + "-112";
                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                e.printStackTrace();
            }
            if (iStream != null) {
                final byte[] inputData = getBytes(iStream);
                final String training_plan = inputTitle.getText().toString().trim();
                String token = userSessionObj.getToken();
                String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.M_REF_345_2;
                printLogs(LogTag, "FormSubmit", "URL : " + FINAL_URL);

                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("token", token);
                    jsonBody.put(KEY_TRAINING_PLAN, training_plan);
                    jsonBody.put("training_id", training_id);
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
                                        arraylist = new ArrayList<HashMap<String, String>>();

                                        showProgress(false, mContentView, mProgressView);
                                        String sTitle = getLabelFromDb("m_M131_title", R.string.m_M131_title);
                                        String sMessage = getLabelFromDb("m_M131_message", R.string.m_M131_message);
                                        String sButtonLabelClose = "Close";
                                        ErrorDialog.showSuccessDialogEditTrainingPlan(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose, thisClass);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    showProgress(false, mContentView, mProgressView);
                                    String sTitle = "Error :" + ActivityId + "-104";
                                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                                    String sButtonLabelClose = "Close";
                                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                showProgress(false, mContentView, mProgressView);
                                String sTitle = "Error :" + ActivityId + "-105";
                                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                                String sButtonLabelClose = "Close";
                                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
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
                RequestQueue requestQueue = Volley.newRequestQueue(MEditTrainingPlanA.this);
                requestQueue.add(jsonObjectRequest);
            }
        } catch(FileNotFoundException e){
            e.printStackTrace();
            showProgress(false, mContentView, mProgressView);
            String sTitle = "Error :" + ActivityId + "-110";
            String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
            String sButtonLabelClose = "Close";
            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
        } catch(IOException e){
            e.printStackTrace();
            showProgress(false, mContentView, mProgressView);
            String sTitle = "Error :" + ActivityId + "-111";
            String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
            String sButtonLabelClose = "Close";
            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
        }

    }
    public void customRedirector() {
        Intent intent = new Intent(MEditTrainingPlanA.this, MExistingTrainingPlanA.class);
        Bundle inputUri = new Bundle();
        intent.putExtras(inputUri);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        printLogs(LogTag,"onOptionsItemSelected","init");
        if(generator.equals("M312")){
            printLogs(LogTag,"onOptionsItemSelected","MFeedbackA");
            Intent intent = new Intent(MEditTrainingPlanA.this, MExistingTrainingPlanA.class);
            Bundle activeUri = new Bundle();
            activeUri.putString("generator",ActivityId);
            //activeUri.putString("user_id",c_u_r_id);
            //activeUri.putString("student_name", student_name);
            intent.putExtras(activeUri);
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
