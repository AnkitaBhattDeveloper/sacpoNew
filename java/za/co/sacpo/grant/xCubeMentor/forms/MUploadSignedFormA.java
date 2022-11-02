package za.co.sacpo.grant.xCubeMentor.forms;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
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
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeMentor.claims.MPendingClaimsA;
import za.co.sacpo.grant.xCubeMentor.claims.MPastClaimA;

/*a_mupload_signed_form*/
public class MUploadSignedFormA extends BaseFormAPCPrivate {
    private String ActivityId="148";
    public View mProgressView, mContentView;
    private String KEY_IMAGE = "uploaded_file";
    private String KEY_STIPEND_ID= "stipend_id";
    private String extension;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int PICK_IMAGE_REQUEST= 99;

    private TextView lblView;
    PermissionsChecker checker;
    String generator;
    Button upload;
    String m_student_name,student_id,stipend_id;
    public Button btnChoose,btnUpload;
    TextView headLogo;
    public int PDF_REQ_CODE = 1;
    private Bitmap bitmap;
    File file;

    String filename;
    String s1,s2;
    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId){
        baseApcContext = cnt;
        CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        printLogs(lt,"setBaseApcContextParent","weAreIn");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);
        printLogs(LogTag,"onCreate","init");

        Bundle activeInputUri = getIntent().getExtras();

        generator = activeInputUri.getString("generator");
        student_id = activeInputUri.getString("student_id");
        m_student_name = activeInputUri.getString("m_student_name");
        stipend_id = activeInputUri.getString("stipend_id");
        printLogs(LogTag,"onCreate","GENERATOR ID "+generator);
        printLogs(LogTag,"onCreate","student_id"+student_id);
        printLogs(LogTag,"onCreate","m_student_name"+m_student_name);
        printLogs(LogTag,"onCreate","SSSSSSIDID"+stipend_id);


        bootStrapInit();
    }
    @Override
    protected void bootStrapInit() {
        Boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
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
            showProgress(false, mContentView, mProgressView);
            printLogs(LogTag, "bootStrapInit", "exitConnected");
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
        printLogs(LogTag,"verifyVersion","isUpdate "+ utilSessionObj.getIsUpdateRequired());
        if(isUpdate){
            Intent intent = new Intent(MUploadSignedFormA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void setLayoutXml(){
        printLogs(LogTag,"setLayoutXml","a_mupload_signed_form-In");
        setContentView(R.layout.a_mupload_signed_form);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","initializeViews-In");
        mContentView = findViewById(R.id.content_container);
        checker = new PermissionsChecker(this);
        mProgressView = findViewById(R.id.progress_bar);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        btnChoose = (Button)findViewById(R.id.btnChoose);
        headLogo = (TextView) findViewById(R.id.headLogo);
    }
    @Override
    protected void initializeLabels(){

        String  Label = getLabelFromDb("l_148_btn_uplode_signed_form",R.string.l_148_btn_uplode_signed_form);
        btnUpload.setText(Label);

        Label = getLabelFromDb("h_148",R.string.h_148);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);

        Label = getLabelFromDb("b_148_l_choose_file",R.string.b_148_l_choose_file);
        lblView = (TextView)findViewById(R.id.btnChoose);
        lblView.setText(Label);
    }
    @Override
    protected void initializeInputs(){
        printLogs(LogTag,"initializeInputs","initializeInputs-In");
      /*  String thumb = userSessionObj.getUserThumb();
        headLogo.setVisibility(View.VISIBLE);
        if(thumb.isEmpty()){
            thumb = URLHelper.DOMAIN_URL;
        }
        Picasso.get().load(thumb).resize(130,130).placeholder(R.mipmap.user).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).networkPolicy(NetworkPolicy.NO_CACHE).error(R.mipmap.user).into(headLogo);
  */

    }
    @Override
    protected void initializeListeners() {
        printLogs(LogTag,"initializeListeners","initializeListeners-In");
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmdialog();
                printLogs(LogTag,"initializeListeners","initializeListeners-In");
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
        if (ActivityCompat.shouldShowRequestPermissionRationale(MUploadSignedFormA.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(MUploadSignedFormA.this, " Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(MUploadSignedFormA.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MUploadSignedFormA.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MUploadSignedFormA.this, "Permission Granted Successfully! ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MUploadSignedFormA.this, "Permission Denied 🙁 ", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
    private void confirmdialog() {
        // TODO Auto-generated method stub
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MUploadSignedFormA.this);

        // Setting Dialog Title
        alertDialog.setTitle("UPLOAD SIGNED CLAIM FORM");

        // Setting Dialog Message
        alertDialog.setMessage("Have you already choose the claim signed form ?");



        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {


                validateForm();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //cancel the dialog
                dialog.cancel();
            }
        });


        alertDialog.show();
    }

    private void showFileChooser() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_IMAGE_REQUEST);

    }



    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            Uri filePath = data.getData();
            Toast.makeText(MUploadSignedFormA.this, ""+filePath, Toast.LENGTH_SHORT).show();

            filename=filePath.getLastPathSegment();
            headLogo.setText(filename);
            Toast.makeText(MUploadSignedFormA.this, ""+filename, Toast.LENGTH_SHORT).show();

            file = new File(filePath.toString());

            String mimeType = getContentResolver().getType(filePath);
            extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);

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
    public void validateForm() {
        boolean cancel = false;

        if (cancel) {
            return;
        } else {
            showProgress(true,mContentView,mProgressView);
            this.FormSubmit();
        }
        printLogs(LogTag,"validateForm","exit");
    }


    public void FormSubmit(){
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.UPLOAD_CLAIM_FORMBY_MENTOR;
        printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);
        String doc = getStringFile(file);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put("KEY_STIPEND_ID", stipend_id);
            jsonBody.put("KEY_IMAGE", doc);
            jsonBody.put("extension", extension);
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
                        showProgress(false,mContentView,mProgressView);
                        String sTitle=getLabelFromDb("m_148_title",R.string.m_148_title);
                        String sMessage=getLabelFromDb("m_148_message",R.string.m_148_message);
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
                String sTitle="Error :"+ActivityId+"-103";
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
        RequestQueue requestQueue = Volley.newRequestQueue(MUploadSignedFormA.this);
        requestQueue.add(jsonObjectRequest);
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
            syncGrantData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn,ActivityId);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        printLogs(LogTag,"onOptionsItemSelected","init");

        if(generator.equals("126")){
            Intent intent = new Intent(MUploadSignedFormA.this, MPastClaimA.class);

            Bundle inputUri = new Bundle();
            inputUri.putString("generator", "148");
            inputUri.putString("m_student_name", m_student_name);
            inputUri.putString("student_id", student_id);
            inputUri.putString("stipend_id", stipend_id);
            intent.putExtras(inputUri);
            printLogs(LogTag,"onOptionsItemSelected","MPastClaimA");
            startActivity(intent);
            finish();
        }else if(generator.equals("340")){
            Intent intent = new Intent(MUploadSignedFormA.this, MPendingClaimsA.class);
            Bundle inputUri = new Bundle();
            inputUri.putString("generator", ActivityId);
            intent.putExtras(inputUri);
            printLogs(LogTag,"onOptionsItemSelected","MPendingClaimsA");
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