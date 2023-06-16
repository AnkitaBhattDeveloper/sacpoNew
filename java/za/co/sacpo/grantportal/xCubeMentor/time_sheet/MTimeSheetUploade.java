package za.co.sacpo.grantportal.xCubeMentor.time_sheet;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import za.co.sacpo.grantportal.AppUpdatedA;
import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.component.Utils;
import za.co.sacpo.grantportal.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grantportal.xCubeStudent.SDashboardDA;

/*activity_mtime_sheet_uplode*/
public class MTimeSheetUploade extends BaseFormAPCPrivate {
    private String ActivityId = "328";
    private String KEY_U_TYPE_ID = "user_type_id";

    int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1;
    private Boolean permissionLocation;

    public View mProgressView, mContentView;
    public TextView txt_file;

    private Uri uri;
    private Uri filePath;

    public static final String PDF_UPLOAD_HTTP_URL = "http://demo.setaportal.co.za/api/user/type/uploadClaimFormByStudent/token";

    public int PDF_REQ_CODE = 1;
    String PdfNameHolder, PdfPathHolder, PdfID;
    public Button btnUpload, btnChoose;


    private TextView lblView;


    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cTAId){
        baseApcContext = cnt;CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(), this, this.getClass().getSimpleName(), ActivityId);
        AllowRunTimePermission();
        bootStrapInit();


    }


    @Override
    protected void bootStrapInit() {
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext, activityIn);
        if (isConnected) {
            syncToken(baseApcContext, activityIn);
            setLayoutXml();
            setAppLogo();
            callFooter(baseApcContext,activityIn,ActivityId);

            initMenusCustom(ActivityId,baseApcContext,activityIn);
//            showProgress(true,mContentView,mProgressView);
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
            initializeListeners();
            showProgress(false, mContentView, mProgressView);
            printLogs(LogTag, "bootStrapInit", "exitConnected");
        }
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
        if(isUpdate==true){
            Intent intent = new Intent(MTimeSheetUploade.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    protected void fetchVersionData(){
        printLogs(LogTag,"fetchVersionData","exitConnected");
        syncUpdates(baseApcContext,activityIn);
    }


    @Override
    protected void initializeInputs(){
        printLogs(LogTag,"initializeInputs","init");
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
    protected void initializeLabels(){


        String  Label = getLabelFromDb("l_328_btn_uplode_time_sheet",R.string.l_328_btn_uplode_time_sheet);
        btnUpload.setText(Label);

        Label = getLabelFromDb("h_328",R.string.h_328);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);

        Label = getLabelFromDb("b_328_l_choose_file",R.string.b_328_l_choose_file);
        lblView = (TextView)findViewById(R.id.btnChoose);
        lblView.setText(Label);

    }
    @Override
    protected void initializeViews() {

        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        btnChoose = (Button) findViewById(R.id.btnChoose);
        /*txt_file = (TextView) findViewById(R.id.txt_file);*/
    }

    @Override
    protected void initializeListeners() {
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // PDF selection code start from here .

                Intent intent = new Intent();

                intent.setType("application/pdf");

                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PDF_REQ_CODE);

            }
        });


        btnChoose.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                uploadMultipart();

            }
        });


    }


    private void uploadMultipart() {
/*
        //getting the actual path of the image
        String path = FilePath.getPath(this, filePath);

        if (path == null) {

            Toast.makeText(this, "Please move your .pdf file to internal storage and retry", Toast.LENGTH_LONG).show();
        } else {
            //Uploading code
            try {
                String uploadId = UUID.randomUUID().toString();

                //Creating a multi part request
                new MultipartUploadRequest(this, uploadId, PDF_UPLOAD_HTTP_URL)
                        .addFileToUpload(path, "pdf") //Adding file
                      //  .addParameter("name", name) //Adding text parameter to the request
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(2)
                        .startUpload(); //Starting the upload

            } catch (Exception exc) {
                Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
*/
    }

    private void AllowRunTimePermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(MTimeSheetUploade.this, "READ_EXTERNAL_STORAGE permission Access Dialog", Toast.LENGTH_LONG).show();
                printLogs(LogTag, "checkLocationPermission", "PERMISSION ERROR");


            } else {

                // ActivityCompat.requestPermissions(SUploadFormA.this,new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                printLogs(LogTag, "checkLocationPermission", "PERMISSION INPUT");
                ActivityCompat.requestPermissions(MTimeSheetUploade.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            }
        }else{
            printLogs(LogTag, "checkLocationPermission", "PERMISSION INPUT");
            permissionLocation = true;
        }

    }

    @Override
    public void onRequestPermissionsResult(int RC, String[] per, int[] Result) {

        switch (RC) {

            case 1:

                if (Result.length > 0 && Result[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MTimeSheetUploade.this,"Permission Granted", Toast.LENGTH_LONG).show();
                } else {

                    Toast.makeText(MTimeSheetUploade.this,"Permission Canceled", Toast.LENGTH_LONG).show();
                }
                break;
        }

    }
    @Override
    protected void setLayoutXml() {
        setContentView(R.layout.activity_mtime_sheet_uplode);
    }


    @Override
    protected void validateForm() {

    }

    @Override
    protected void FormSubmit() {
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.UPLOADFORM_URL;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, FINAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject outputJson = null;
                try {
                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if (Status.equals("1")) {
                        JSONObject dataM = outputJson.getJSONObject(KEY_DATA);

                        showProgress(false, mContentView, mProgressView);

                        Toast.makeText(baseApcContext, "Demo Test", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MTimeSheetUploade.this, SDashboardDA.class);
                        startActivity(intent);
                        finish();

                    } else {
                        showProgress(false, mContentView, mProgressView);
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "-101";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String sTitle = "Error :" + ActivityId + "-102";
                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                //Toast.makeText(getApplicationContext(),"ERROR LOGIN IN. PLEASE TRY AGAIN.", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put(KEY_U_TYPE_ID, "2");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MTimeSheetUploade.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(MTimeSheetUploade.this,TimeSheetA.class);
            printLogs(LogTag,"onOptionsItemSelected","sDashboard");
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
