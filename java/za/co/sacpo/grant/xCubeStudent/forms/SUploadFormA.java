package za.co.sacpo.grant.xCubeStudent.forms;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import za.co.sacpo.grant.AppUpdatedA;

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeStudent.SDashboardDA;
import za.co.sacpo.grant.xCubeStudent.claims.SPastClaimDA;

public class SUploadFormA extends BaseFormAPCPrivate {
    private String ActivityId = "133";
    int SELECT_PDF = 20;
    int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1;
    private Boolean permissionLocation;
    private String extension,filestring="",stipend_id = "",filename;
    public View mProgressView, mContentView;
    public TextView txtFilePath;
    public Button btnUpload, btnChoose;
    private TextView lblView;
    SUploadFormA thisClass;
    private File myFile;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(), this, this.getClass().getSimpleName(), ActivityId);
        AllowRunTimePermission();
        initBackBtn();
        bootStrapInit();

        internetChangeBroadCast();
        fetchVersionData();
        verifyVersion();
        Bundle activeInputUri = getIntent().getExtras();
        stipend_id = activeInputUri.getString("s_m_s_id");
    }

    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void AllowRunTimePermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                  printLogs(LogTag, "checkLocationPermission", "PERMISSION ERROR");
            } else {
                // ActivityCompat.requestPermissions(SUploadFormA.this,new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                printLogs(LogTag, "checkLocationPermission", "PERMISSION INPUT");
                ActivityCompat.requestPermissions(SUploadFormA.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            }
            }else{
                printLogs(LogTag, "checkLocationPermission", "PERMISSION INPUT");
                permissionLocation = true;
            }

        }

    @Override
    public void onRequestPermissionsResult(int RC, String[] per, int[] Result) {
        if (RC == 1) {
            if (Result.length > 0 && Result[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(SUploadFormA.this, "Permission Granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(SUploadFormA.this, "Permission Cancelled", Toast.LENGTH_LONG).show();
            }
        }
    }



    protected void fetchVersionData(){
        printLogs(LogTag,"fetchVersionData","exitConnected");
        syncUpdates(baseApcContext,activityIn);
    }

    @Override
    protected void internetChangeBroadCast() {
        registerBroadcastIC();
    }

    protected void verifyVersion(){
        printLogs(LogTag,"verifyVersion","init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag,"verifyVersion","isUpdate "+isUpdate);
        printLogs(LogTag,"verifyVersion","isUpdate "+ utilSessionObj.getIsUpdateRequired());
        if(isUpdate){
            Intent intent = new Intent(SUploadFormA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void bootStrapInit() {
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext, activityIn);
        if (isConnected) {
            syncToken(baseApcContext, activityIn);
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);
            initMenusCustom(ActivityId,baseApcContext,activityIn);
            initializeViews();
            initializeInputs();
            initializeLabels();
            initializeListeners();
        }
    }

    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId){
        baseApcContext = cnt;CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        thisClass = this;
    }
    @Override
    protected void initializeInputs(){

    }

    @Override
    protected void initializeLabels(){
        String  Label = getLabelFromDb("b_133_s_uploade_form",R.string.b_133_s_uploade);
        lblView = (TextView)findViewById(R.id.btnUpload);
        lblView.setText(Label);

        Label = getLabelFromDb("h_133",R.string.h_133);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);

        Label = getLabelFromDb("b_133_l_choose_file",R.string.b_133_l_choose_file);
        lblView = (TextView)findViewById(R.id.btnChoose);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_M345_nofileChosen",R.string.lbl_M345_nofileChosen);
        txtFilePath.setText(Label+" (PDF ONLY)");
    }
    @Override
    protected void initializeViews() {
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        btnUpload =findViewById(R.id.btnUpload);
        btnChoose = findViewById(R.id.btnChoose);
        txtFilePath = findViewById(R.id.txtFilePath);
    }



    @Override
    protected void initializeListeners() {
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmdialog();
            }
        });

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                selectPDF();
            }
        });


    }
    private void selectPDF() {
        printLogs(LogTag, "selectPDF", "init");
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(Intent.createChooser(intent, "Select PDF"), SELECT_PDF);
        printLogs(LogTag, "selectPDF", "exit");
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
    private void confirmdialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SUploadFormA.this);
        alertDialog.setTitle("UPLOAD CLAIM FORM");
        alertDialog.setMessage("Have you already choose claim form ?");
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                FormSubmit();
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SELECT_PDF && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri filePath = data.getData();
            String mimeType = getContentResolver().getType(filePath);
            extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
            filestring = getStringPdf(filePath);
            String uriString = filePath.toString();
            myFile = new File(uriString);
            String displayName = null;
            filename = filePath.getLastPathSegment();
            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = this.getContentResolver().query(filePath, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        //Log.d("nameeeee>>>>  ", displayName);
                        txtFilePath.setText(displayName);
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
                //Log.d("nameeeee>>>>  ", displayName);
                txtFilePath.setText(displayName);
            }
        }
    }

    @Override
    protected void setLayoutXml() {
        setContentView(R.layout.a_upload_form);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(SUploadFormA.this, SPastClaimDA.class);
            startActivity(intent);
            finish();
        }
        return true;
    }

    @Override
    protected void validateForm() {

    }

    @Override
    protected void FormSubmit() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.UPLOADFORM_URL;
        printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put("uploaded_file", filestring);
            jsonBody.put("extension", extension);
            jsonBody.put("stipend_id", stipend_id);
            printLogs(LogTag, "FormSubmit", "token " + token);
            printLogs(LogTag, "FormSubmit", "stipend_id " + stipend_id);
            printLogs(LogTag, "FormSubmit", "image " + filestring);
            printLogs(LogTag, "FormSubmit", "extension " + extension);
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
                        String sMessage = getLabelFromDb("m_M349_message", R.string.m_M349_message);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showSuccesUploadClaim(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose, thisClass);

                    } else {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-101";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showSuccesUploadClaim(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose,thisClass);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "-101";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showSuccesUploadClaim(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose,thisClass);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String sTitle = "Error :" + ActivityId + "-102";
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
    public void  customRedirector(){
        Intent intent = new Intent(SUploadFormA.this, SDashboardDA.class);
        printLogs(LogTag, "customRedirector", "SDashboardDA");
        startActivity(intent);
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
