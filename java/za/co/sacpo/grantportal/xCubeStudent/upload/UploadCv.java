package za.co.sacpo.grantportal.xCubeStudent.upload;

import static za.co.sacpo.grantportal.xCubeLib.component.URLHelper.UPLOAD_CV;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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

import za.co.sacpo.grantportal.AppUpdatedA;
import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.component.Utils;
import za.co.sacpo.grantportal.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grantportal.xCubeStudent.SDashboardDA;

public class UploadCv extends BaseAPCPrivate {

    private String ActivityId = "245";
    public View mProgressView, mContentView,heading;
    public TextView lblView;
    private String extension;
    String filename;
    private ProgressDialog loading;
    UploadCv thisClass;
    public TextView txtFilePath;
    private File myFile;
    public Button btnUpload, btnChoose;
    private String grant_id,filestring;

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
        printLogs(LogTag, "onCreate", "init");
        bootStrapInit();


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
            initBackBtn();
            initializeViews();
            showProgress(true, mContentView, mProgressView);
            initializeLabels();
            initializeListeners();
            userToken = userSessionObj.getToken();
            syncToken(baseApcContext, activityIn);
            if (TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
            callDataApi();
            fetchStudetails();
            initializeInputs();
            showProgress(false, mContentView, mProgressView);
        }

    }

    private void fetchStudetails() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.STUDENT_DATA_URL + "?token=" + token;
        printLogs(LogTag, "getStudentDetails", "URL : " + FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject= new JSONObject(String.valueOf(response));
                    //Log.i("fetchStudetails","RESPONSE "+response);
                    String Status = jsonObject.getString(KEY_STATUS);
                    if (Status.equals("1")) {
                        JSONObject dataM = jsonObject.getJSONObject(KEY_DATA);
                        grant_id = dataM.getString("grant_id");
                        printLogs(LogTag, "Data", "GrantIdValue__:" + grant_id);
                    } else {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-106";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "-107";
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
                        String sTitle = "Error :" + ActivityId + "-108";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "*/*");
                // params.put("Content-Type","application/json");

                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(UploadCv.this);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);




    }

    @Override
    protected void initializeViews() {

        printLogs(LogTag, "initializeViews", "init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        heading = findViewById(R.id.heading);
        txtFilePath = findViewById(R.id.txtFilePath);
        btnUpload =findViewById(R.id.btnUpload);
        btnChoose = findViewById(R.id.btnChoose);
    }

    @Override
    protected void initializeListeners() {
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                fileChooser();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPDF(filestring,extension);
            }
        });

    }


    @Override
    protected void initializeInputs() {


    }

    @Override
    protected void initializeLabels() {

        String Label = getLabelFromDb("h_245", R.string.h_245);
        lblView = (TextView) findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

         Label = getLabelFromDb("b_133_s_uploade_form",R.string.b_133_s_uploade);
        lblView = (TextView)findViewById(R.id.btnUpload);
        lblView.setText(Label);

        Label = getLabelFromDb("b_133_l_choose_file",R.string.b_133_l_choose_file);
        lblView = (TextView)findViewById(R.id.btnChoose);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_M345_nofileChosen",R.string.lbl_M345_nofileChosen);
        txtFilePath.setText(Label+" (PDF ONLY)");
        txtFilePath.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            btnUpload.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            btnChoose.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
        }

    }

    private void initBackBtn() {
        printLogs(LogTag, "initBackBtn", "init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void setLayoutXml() {
        setContentView(R.layout.a_upload_cv);
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

    @Override
    protected void internetChangeBroadCast() {
        printLogs(LogTag, "internetChangeBroadCast", "init");
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
            Intent intent = new Intent(UploadCv.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    private void fileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(Intent.createChooser(intent, "Select PDF to Upload"), 1);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
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
                Uri uri = data.getData();
                if (uri != null) {
                    String uriString = uri.toString();
                    myFile = new File(uriString);
                    String displayName;
                    filename = uri.getLastPathSegment();
                    String mimeType = getContentResolver().getType(uri);
                    extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
                    filestring = getStringPdf(uri);
                   // getStringFile(myFile);
                    printLogs(LogTag,"onActivityResult","uriString : "+uriString);
                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = this.getContentResolver().query(uri, null, null, null, null);
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
                } else {
                    Toast.makeText(thisClass, "Nothing Selected...", Toast.LENGTH_SHORT).show();
                }
            }
            super.onActivityResult(requestCode, resultCode, data);
        }


    private void uploadPDF(String filestring, String extension){
            String token = userSessionObj.getToken();
            // url Is Empty  for upload PDF....
            String FINAL_URL = URLHelper.DOMAIN_BASE_URL + UPLOAD_CV;
            printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);

            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("token", token);
                jsonBody.put("grant_id", grant_id);
                jsonBody.put("extension", extension);
                jsonBody.put("uploaded_file",filestring);
                printLogs(LogTag,"FormSubmit","jsonBody "+jsonBody);
                printLogs(LogTag,"FormSubmit","extension "+extension);
                printLogs(LogTag,"FormSubmit","filestring "+filestring);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
            JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.POST, FINAL_URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    JSONObject jsonObject;
                    printLogs(LogTag, "FormSubmit", String.format("RESPONSE : %s", response));
                    try {
                        jsonObject = new JSONObject(String.valueOf(response));
                        String Status = jsonObject.getString(KEY_STATUS);
                                if (Status.equals("1")) {
                                    loading.dismiss();
                                    showProgress(false,mContentView,mProgressView);
                                    String sTitle=getLabelFromDb("m_245_title",R.string.m_245_title);
                                    String sMessage =getLabelFromDb("m_245_message",R.string.m_245_message);
                                    String sButtonLabelClose="Close";
                                    ErrorDialog.showSuccessDialogUploadCv(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose,thisClass);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                loading.dismiss();
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
                            loading.dismiss();
                            showProgress(false,mContentView,mProgressView);
                            String sTitle="Error :"+ActivityId+"-105";
                            String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                            String sButtonLabelClose="Close";
                            ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
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
            RequestQueue requestQueue = Volley.newRequestQueue(UploadCv.this);
            requestQueue.add(jsonObjectRequest);

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


    public void customRedirector(){
        Bundle inputUri = new Bundle();
        Intent intent = new Intent(UploadCv.this, SDashboardDA.class);
        intent.putExtras(inputUri);
        startActivity(intent);
        finish();
        printLogs(LogTag, "customRedirector", "Redirect");
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {

            Bundle inputUri = new Bundle();
            printLogs(LogTag,"onOptionsItemSelected","SDashboardDA");
            Intent intent = new Intent(UploadCv.this, SDashboardDA.class);
            intent.putExtras(inputUri);
            startActivity(intent);
            finish();
        } else {
            printLogs(LogTag,"onOptionsItemSelected","default");
            super.onOptionsItemSelected(item);
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
