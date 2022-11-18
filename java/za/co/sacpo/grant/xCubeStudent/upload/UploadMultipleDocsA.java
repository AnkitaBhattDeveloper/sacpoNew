package za.co.sacpo.grant.xCubeStudent.upload;

import static za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPublic.isValidLName;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dataObj.CheckBoxListObj;
import za.co.sacpo.grant.xCubeLib.dataObj.SpinnerObj;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;

public class UploadMultipleDocsA extends BaseAPCPrivate {
    private String ActivityId = "S196B";
    public EditText inputTitle ;
    public TextInputLayout inputLayoutTitle ;
    public View mProgressView, mContentView,heading;
    public String generator , document_id,document_name , documentType,document_title ;
    public LinearLayout LL_titleContainer ;
    public Button btnBrows , btnSubmit ;
    private TextView lblView , lblFileName ,docName ;
    UploadMultipleDocsA thisClass ;
    private String extension="",filestring="";
    private File myFile;
    String filename;
    String displayName;
    Uri fileUriVal, cachedFileUriVal;
    private RequestQueue rQueue;
    int MY_PERMISSIONS_REQUEST_STORAGE =1;
    private Boolean permissionStorage;
    private ArrayList<HashMap<String, String>> arraylist;
    private ListView llChb;
    private ArrayList<SpinnerObj> arrayList=new ArrayList<>();
    private ArrayList<CheckBoxListObj> infodata;
    String finalDocType="";


    @Override
    protected void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cAId) {
        baseApcContext = cnt;
        CAId=cAId;
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
        inBundle = getIntent().getExtras();
        generator = inBundle.getString("generator");
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
            initializeLabels();
            initializeInputs();
            userToken =userSessionObj.getToken();
            syncToken(baseApcContext,activityIn);
            if(TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
            fetchData();
            callDataApi();
            initializeListeners();
            printLogs(LogTag,"bootStrapInit","exitConnected");
        }
    }

    private void fetchData() {
        showProgress(true,mContentView,mProgressView);
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.REF_S_193;
        FINAL_URL=FINAL_URL+"?token="+token;
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
                        for (int i = 0; i < dataM.length(); i++) {
                            int pos = i+1;
                            JSONObject rec = dataM.getJSONObject(i);
                            String aId2 = rec.getString("id");
                            String aName3 = rec.getString("name_of_document");
                            arrayList.add(new SpinnerObj(aId2,aName3));
                            showList();
                        }
                        showProgress(false,mContentView,mProgressView);
                    }
                    else if(Status.equals("2")) {
                        showProgress(false,mContentView,mProgressView);
                    }
                    else{
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-102";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false,mContentView,mProgressView);
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
                        showProgress(false,mContentView,mProgressView);
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

    private void showList() {
        infodata = new ArrayList<CheckBoxListObj>();
        for (int i = 0; i < arrayList.size(); i++) {
            infodata.add(new CheckBoxListObj(false, arrayList.get(i).getId(),arrayList.get(i).getName()));
        }
        llChb.invalidate();
        llChb.setAdapter(new MyAdapter());
    }

    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","a_upload_multiple_docs");
        setContentView(R.layout.a_upload_multiple_docs);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");

        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        heading = findViewById(R.id.heading);
        llChb = findViewById(R.id.cb_listview);
        inputTitle =  findViewById(R.id.inputTitle);
        docName =  findViewById(R.id.lblDocName);
        inputLayoutTitle =  findViewById(R.id.inputLayouTitle);

        btnBrows = findViewById(R.id.btnBrows);
        btnSubmit = findViewById(R.id.btnSubmit);
        LL_titleContainer = findViewById(R.id.titleContainer);

        lblFileName = findViewById(R.id.lblFileName);


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

    public void validateForm() {
        boolean cancel = false;
        if (!validateTitle(inputTitle, inputLayoutTitle)) {
            cancel = true;
        }else if(!validateDocument(filestring,extension)){
            cancel = true;
        }else if(!validateDocType()){
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

    private boolean validateDocType() {
        if(infodata.get(0).isClicked()){
            return true;
        }else{
            Toast.makeText(thisClass, "Please select at least 1 document type.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean validateDocument(String filestring, String extension) {
        if(filestring.equals("") || extension.equals("")){
            Toast.makeText(thisClass, "Please Choose a file", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    public boolean validateTitle(EditText inputTitle, TextInputLayout inputLayoutTitle) {
        String title = inputTitle.getText().toString().trim();
        setCustomError(inputLayoutTitle, null, inputTitle);
        if (title.isEmpty() || !isValidLName(title)) {
            String sMessage = getLabelFromDb("l_S193_errortitle", R.string.l_S193_errortitle);
            setCustomError(inputLayoutTitle, sMessage, inputTitle);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutTitle, inputTitle);
            return true;
        }
    }

    public void FormSubmit(){
        showProgress(true,mContentView,mProgressView);
        final String title = inputTitle.getText().toString().trim();
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_196B;
        printLogs(LogTag, "FormSubmit", "URL : " + FINAL_URL);
        for(int i=0;i<infodata.size();i++) {
            if (infodata.get(i).isClicked()) {
                finalDocType = infodata.get(i).getId()+"@@@" + finalDocType;
            }
        }

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put("doc_types", finalDocType);
            jsonBody.put("title", title);
            jsonBody.put("extension", extension);
            jsonBody.put("uploaded_file", filestring);
            printLogs(LogTag, "FormSubmit", "jsonBody " + jsonBody);

            printLogs(LogTag, "FormSubmit", "filestring " + filestring);
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
                        String sTitle = getLabelFromDb("m_S196_title", R.string.m_S196_title);
                        String sMessage = getLabelFromDb("m_S196_message", R.string.m_S196_message);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showSuccessDialogUploadMultipleDocS(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose, thisClass);
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
        RequestQueue requestQueue = Volley.newRequestQueue(UploadMultipleDocsA.this);
        requestQueue.add(jsonObjectRequest);



    }
    @Override
    protected void initializeInputs() {

    }
    public class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() { return arrayList.size(); }

        @Override
        public Object getItem(int position) { return null;  }

        @Override
        public long getItemId(int position) { return Long.parseLong(arrayList.get(position).getId()); }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View row = null;
            row = View.inflate(getApplicationContext(), R.layout.row_checkbox, null);
            TextView tvContent= row.findViewById(R.id.tvContent);
            tvContent.setText(arrayList.get(position).getName());

            final CheckBox cb = (CheckBox) row.findViewById(R.id.chbContent);
            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (infodata.get(position).isClicked()) {
                        infodata.get(position).setClicked(false);
                    } else {
                        infodata.get(position).setClicked(true);
                    }

                    for(int i=0;i<infodata.size();i++) {
                        if (infodata.get(i).isClicked())
                        {
                            System.out.println("Selectes Are == "+ arrayList.get(position).getName());
                        }

                    }
                }
            });

            if (infodata.get(position).isClicked()) {
                cb.setChecked(true);
            }
            else {
                cb.setChecked(false);
            }
            return row;
        }

    }
    @Override
    protected void initializeLabels() {
        printLogs(LogTag,"initializeLabels","init");
        String  Label = getLabelFromDb("l_S193_compositeupload",R.string.l_S193_compositeupload);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S105_title",R.string.lbl_S105_title);
        lblView = (TextView)findViewById(R.id.lblTitle);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("l_S193_selectdocs",R.string.l_S193_selectdocs);
        lblView = (TextView)findViewById(R.id.lblselectdocs);
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

        Label = getLabelFromDb("lbl_M345_pdf", R.string.lbl_S196_pdf);
        lblView = (TextView) findViewById(R.id.lblPdf);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        docName.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            btnSubmit.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            btnBrows.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            inputTitle.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));

        }
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

    public String getStringPdf (Uri filepath) {
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            inputStream = getContentResolver().openInputStream(filepath);
            byte[] buffer = new byte[1024];
            byteArrayOutputStream = new ByteArrayOutputStream();
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        byte[] pdfByteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(pdfByteArray, Base64.DEFAULT);
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
                filestring = getStringPdf(fileUriVal);
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
                    btnSubmit.setVisibility(View.VISIBLE);
                    //ownFile(fileUriVal);
                }
            } else {
                Toast.makeText(thisClass, "Nothing Selected...", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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
        printLogs(LogTag,"checkStoragePermission","init");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                printLogs(LogTag,"checkStoragePermission","PERMISSION ERROR");
            } else {
                printLogs(LogTag,"checkStoragePermission","PERMISSION INPUT FINE");
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSIONS_REQUEST_STORAGE);
            }
        } else {
            printLogs(LogTag,"checkStoragePermission","PERMISSION INPUT");
            permissionStorage = true;
        }
    }

    @Override
    protected void verifyVersion() {
        printLogs(LogTag,"verifyVersion","init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag,"verifyVersion","isUpdate "+isUpdate);
        printLogs(LogTag,"verifyVersion","isUpdate "+ utilSessionObj.getIsUpdateRequired());
        if(isUpdate){
            Intent intent = new Intent(UploadMultipleDocsA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void fetchVersionData() {
        printLogs(LogTag,"fetchVersionData","exitConnected");
        syncUpdates(baseApcContext,activityIn);
    }

    @Override
    protected void internetChangeBroadCast() {
        printLogs(LogTag,"internetChangeBroadCast","init");
        registerBroadcastIC();
    }
    public void callDataApi(){
        printLogs(LogTag,"callDataApi","init");
        userToken = userSessionObj.getToken();
        if(!TextUtils.isEmpty(userToken)) {
            printLogs(LogTag,"callDataApi","Token Update");
            syncUserData(baseApcContext, activityIn);
            syncStudentData(baseApcContext, activityIn);
            syncGrantData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn,ActivityId);
        }
    }
    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void customRedirector() {
        Intent intent = new Intent(UploadMultipleDocsA.this, DocumentCenterListA.class);
        Bundle inputUri = new Bundle();
        intent.putExtras(inputUri);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        printLogs(LogTag, "onOptionsItemSelected", "init");
        Intent intent = new Intent(UploadMultipleDocsA.this, DocumentCenterListA.class);
        Bundle activeUri = new Bundle();
        activeUri.putString("generator", ActivityId);
        intent.putExtras(activeUri);
        startActivity(intent);
        finish();
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