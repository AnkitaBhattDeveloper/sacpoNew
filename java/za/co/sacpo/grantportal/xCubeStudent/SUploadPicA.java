package za.co.sacpo.grantportal.xCubeStudent;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import za.co.sacpo.grantportal.AppUpdatedA;

import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grantportal.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.component.Utils;
import za.co.sacpo.grantportal.permision.PermissionsChecker;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;

public class SUploadPicA extends BaseFormAPCPrivate {
    private String ActivityId="S143";
    public View mProgressView, mContentView,heading;
    private String KEY_IMAGE = "uploaded_file";
    private String extension;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int PICK_IMAGE_REQUEST= 99;
    private TextView lblView;
    PermissionsChecker checker;
    String generator;
    Button upload;
    public Button btnChoose,btnFileUpload;
    ImageView headLogo;
    private Bitmap bitmap;
    String filestring="";


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
            Intent intent = new Intent(SUploadPicA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void setLayoutXml(){
        printLogs(LogTag,"setLayoutXml","setLayoutXml-In");
        setContentView(R.layout.a_upload_profile_pic);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","initializeViews-In");
        mContentView = findViewById(R.id.content_container);
        heading = findViewById(R.id.heading);
        checker = new PermissionsChecker(this);
        mProgressView = findViewById(R.id.progress_bar);
        btnFileUpload = (Button) findViewById(R.id.btnFileUpload);
        btnChoose = (Button)findViewById(R.id.btnChoose);
        headLogo = (ImageView)findViewById(R.id.headLogo);
    }
    @Override
    protected void initializeLabels(){
        printLogs(LogTag,"initializeLabels","initializeLabels-In");
        String Label = getLabelFromDb("l_S143_choose",R.string.l_S143_101_btn);
        btnChoose.setText(Label);
        Label = getLabelFromDb("h_S143",R.string.h_S143);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("l_S143_upload",R.string.l_S143_102_btn);
        btnFileUpload.setText(Label);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            btnFileUpload.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            btnChoose.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
        }

    }
    @Override
    protected void initializeInputs(){
        printLogs(LogTag,"initializeInputs","initializeInputs-In");
        String thumb = userSessionObj.getUserThumb();
        headLogo.setVisibility(View.VISIBLE);
        if(thumb.isEmpty()){
            thumb = URLHelper.DOMAIN_URL;
        }
        Picasso.get().load(thumb).resize(130,130).placeholder(R.mipmap.user).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).networkPolicy(NetworkPolicy.NO_CACHE).error(R.mipmap.user).into(headLogo);
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

        btnFileUpload.setOnClickListener(new View.OnClickListener() {
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
        if (ActivityCompat.shouldShowRequestPermissionRationale(SUploadPicA.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(SUploadPicA.this, " Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(SUploadPicA.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(SUploadPicA.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(SUploadPicA.this, "Permission Granted Successfully! ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SUploadPicA.this, "Permission Denied 🙁 ", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                headLogo.setImageBitmap(bitmap);
                String mimeType = getContentResolver().getType(filePath);
                extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
                filestring = getStringImage(bitmap);
             //   String extension = MimeTypeMap.getFileExtensionFromUrl(path);
                printLogs(LogTag,"initializeViews","filePath "+filePath);
                printLogs(LogTag,"initializeViews","extension "+extension);
                printLogs(LogTag,"initializeViews","bitmap "+bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void confirmdialog() {
        // TODO Auto-generated method stub
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SUploadPicA.this);
        // Setting Dialog Title
        alertDialog.setTitle("UPLOAD PROFILE PIC");
        // Setting Dialog Message
        alertDialog.setMessage("Have you already choose  profile pic ?");
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
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_143;
        printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);
       JSONObject jsonBody = new JSONObject();
       try {
           jsonBody.put("token", token);
           jsonBody.put(KEY_IMAGE, filestring);
           jsonBody.put("extension", extension);
           printLogs(LogTag, "FormSubmit", "token " + token);
           printLogs(LogTag, "FormSubmit", "filestring " + filestring);
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
                    if(Status.equals("1")){
                        showProgress(false,mContentView,mProgressView);
                        String sTitle=getLabelFromDb("m_S143_title",R.string.m_S143_title);
                        String sMessage=getLabelFromDb("m_S143_message",R.string.m_S143_message);
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
       RequestQueue requestQueue = Volley.newRequestQueue(this);
       jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
               10000,
               DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
               DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
       requestQueue.add(jsonObjectRequest);
    }


    private void initBackBtn() {
        printLogs(LogTag, "initBackBtn", "init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
   public void  customRedirector(){
        Intent intent = new Intent(SUploadPicA.this, SDashboardDA.class);
        printLogs(LogTag, "customRedirector", "SDashboardDA");
        startActivity(intent);
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
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(SUploadPicA.this,SDashboardDA.class);
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