package za.co.sacpo.grant.xCubeMentor.queries;

import android.Manifest;
import android.animation.Animator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;

import za.co.sacpo.grant.xCubeLib.adapter.SpinAdapter;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grant.xCubeLib.dataObj.SpinnerObj;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;


import za.co.sacpo.grant.xCubeMentor.MDashboardDA;

public class MContactSupportPrivateA extends BaseFormAPCPrivate {
    private String ActivityId="322";
    private final String KEY_STATUS = "s";
    private final String MESSAGE_STATUS = "m";
    public String KEY_MESSAGE = "message";
    private String KEY_SUBJECT = "title";
    private String KEY_IMAGE = "uploaded_file";
  //  private String KEY_EXT = "extension";
    private String extension;
    public EditText mSubjectView,mMessageView;
    public View mProgressView, mContentView,heading;
    public TextInputLayout inputLayoutSubject,inputLayoutMessage;
    public Button mSendMessageButton;
    private TextView lblView,ttSubjectView,ttMessageView;
    public String generator;
    public Button btnSendMessage,btnSendMessagewithImage;
    public ImageButton imgBtn_removeImage;
    private LinearLayout ActionContainerwithImage,ActionContainer;
    private ImageButton uploadImage_btn;
    private Bitmap bitmap;
    String filestring="";
    private ImageView imageView_getimagewithImage;
    private LinearLayout Linearlayout_ShowImagewithImage,topicContainer;
    private ScrollView scroll_layout_postQuery;
    Spinner inputSpinnersubject;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int PICK_IMAGE_REQUEST= 99;

    private Animator mCurrentAnimator;
    private int mShortAnimationDuration;
    int SELECT_PICTURE = 200;
    int SELECT_PDF = 20,selected_subjecttype;
    MContactSupportPrivateA thisClass;
    public SpinnerObj[] TicketIssue ;

    public void setBaseApcContextParent(Context cnt,AppCompatActivity ain,String lt,String cTAId){
        baseApcContext = cnt;
        CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        thisClass = this;
        printLogs(lt,"setBaseApcContextParent","weAreIn");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);
        printLogs(LogTag, "onCreate", "init");
        Bundle activeInputUri = getIntent().getExtras();
        generator = activeInputUri.getString("generator");
        printLogs(LogTag,"onCreate","GENERATOR ID "+generator);
        bootStrapInit();
    }
    @Override
    protected void bootStrapInit(){
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext,activityIn);
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
            initializeInputs();
            fetchTicketSpinner();
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
    protected void verifyVersion(){
        printLogs(LogTag,"verifyVersion","init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag,"verifyVersion","isUpdate "+isUpdate);
        printLogs(LogTag,"verifyVersion","isUpdate "+ utilSessionObj.getIsUpdateRequired());
        if(isUpdate){
            Intent intent = new Intent(MContactSupportPrivateA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","a_contact_support_private");
        setContentView(R.layout.a_m_contact_support_private);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        heading = findViewById(R.id.heading);
        mSubjectView = (EditText) findViewById(R.id.inputSubject);
        mMessageView = (EditText) findViewById(R.id.inputMessage);
        inputSpinnersubject = findViewById(R.id.inputSpinnersubject);
        topicContainer = findViewById(R.id.topicContainer);
        inputLayoutSubject = (TextInputLayout) findViewById(R.id.inputLayoutSubject);
        inputLayoutMessage = (TextInputLayout) findViewById(R.id.inputLayoutMessage);

        btnSendMessagewithImage = (Button) findViewById(R.id.btnSendMessagewithImage);

        mSendMessageButton= (Button) findViewById(R.id.btnSendMessage);
        uploadImage_btn= (ImageButton) findViewById(R.id.uploadImage_btn);
        imageView_getimagewithImage= (ImageView) findViewById(R.id.imageView_getimagewithImage);
        Linearlayout_ShowImagewithImage= (LinearLayout) findViewById(R.id.Linearlayout_ShowImagewithImage);
        scroll_layout_postQuery = (ScrollView) findViewById(R.id.scroll_layout_postQuery);
        imgBtn_removeImage = (ImageButton) findViewById(R.id.imgBtn_removeImage);
        ActionContainer = (LinearLayout) findViewById(R.id.ActionContainer);
        ActionContainerwithImage = (LinearLayout) findViewById(R.id.ActionContainerwithImage);

        printLogs(LogTag,"initializeViews","exit");
    }
    @Override
    protected void initializeLabels(){
        printLogs(LogTag,"initializeLabels","init");

        String Label = getLabelFromDb("b_322_send_message",R.string.b_322_send_message);
        mSendMessageButton.setText(Label);

        Label = getLabelFromDb("b_703_send_message",R.string.b_703_send_message);
        btnSendMessagewithImage.setText(Label);
        
        Label = getLabelFromDb("h_322",R.string.h_322);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            mSendMessageButton.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            btnSendMessagewithImage.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            mSubjectView.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            mMessageView.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputSpinnersubject.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));

        }

        }
    @Override
    protected void initializeInputs(){
        printLogs(LogTag,"initializeInputs","init");}
    @Override
    protected void initializeListeners() {
        printLogs(LogTag,"initializeListeners","init");


        imgBtn_removeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActionContainerwithImage.setVisibility(View.GONE);
                ActionContainer.setVisibility(View.VISIBLE);
                mSendMessageButton.setVisibility(View.VISIBLE);
            }
        });
        mSendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm2();
            }
        });
        btnSendMessagewithImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });
        
    /*    ttSubjectView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=getLabelFromDb("tt_322_subject",R.string.tt_322_subject);
                showTooltip(ttSubjectView,sToolTip,4);
            }
        });
        ttMessageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=getLabelFromDb("tt_322_Message",R.string.tt_322_Message);
                showTooltip(ttMessageView,sToolTip,4);
            }
        });*/
        uploadImage_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showFileChooser();
                showFileChooser();
                //  showBottomSheetDialog();
            }
        });
        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);
        printLogs(LogTag,"initializeListeners","exit");
    }

    private void fetchTicketSpinner() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.STU_TITLE_TYPE;
        FINAL_URL=FINAL_URL+"/"+token;
        printLogs(LogTag,"fetchTicketIssueSpinner","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchTicketIssueSpinner", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        TicketIssue = new SpinnerObj[dataM.length()];
                        for (int i = 0; i <dataM.length() ; i++) {
                            JSONObject jsonObject = dataM.getJSONObject(i);
                            TicketIssue[i] = new SpinnerObj();
                            TicketIssue[i].setId(jsonObject.getString("i_title_id"));
                            TicketIssue[i].setName(jsonObject.getString("i_title_type"));
                        }
                        SpinAdapter adapter = new SpinAdapter(MContactSupportPrivateA.this, android.R.layout.simple_spinner_item, TicketIssue);
                        inputSpinnersubject.setAdapter(adapter);
                        inputSpinnersubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                selected_subjecttype = Integer.parseInt(adapter.getItem(i).getId());
                                if(selected_subjecttype == 0){
                                    topicContainer.setVisibility(View.VISIBLE);
                                }else{
                                    topicContainer.setVisibility(View.GONE);
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }
                    else if(Status.equals("2")) {
                        // showProgress(false,mContentRView,mProgressRView);
                    }
                    else{
                        // showProgress(false,mContentRView,mProgressRView);
                        String sTitle="Error :"+ActivityId+"-102";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    //showProgress(false,mContentRView,mProgressRView);
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
                        //  showProgress(false,mContentRView,mProgressRView);
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

    private void showFileChooser() {
        final CharSequence[] items = { "Choose from Gallery","Upload PDF", "Cancel"};
        final PackageManager pm = getApplicationContext().getPackageManager();
        final AlertDialog.Builder builder = new AlertDialog.Builder(MContactSupportPrivateA.this);
        //builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Choose from Gallery")) {
                    dialogInterface.dismiss();
                    isGrantExternalRW(MContactSupportPrivateA.this);
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, PICK_IMAGE_REQUEST);}
                else if (items[i].equals("Upload PDF")) {
                    dialogInterface.dismiss();
                    isGrantExternalRW(MContactSupportPrivateA.this);
                    Intent pickPdf = new Intent(Intent.ACTION_GET_CONTENT);
                    pickPdf.addCategory(Intent.CATEGORY_OPENABLE);
                    pickPdf.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    pickPdf.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    pickPdf.setType("application/pdf");
                    startActivityForResult(pickPdf, SELECT_PDF);
                }
                else if (items[i].equals("Cancel")) {
                    dialogInterface.dismiss();}}});
        builder.show();
    }
    private static boolean isGrantExternalRW(MContactSupportPrivateA sChatA) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && (sChatA.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

            ((Activity)sChatA).requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);
            return false;}

        return true;}

    private void selectPDF() {
        printLogs(LogTag, "selectPDF", "init");
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(Intent.createChooser(intent, "Select PDF"), SELECT_PDF);
        printLogs(LogTag, "selectPDF", "exit");
    }

    void imageChooser() {
        printLogs(LogTag, "imageChooser", "init");
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), PICK_IMAGE_REQUEST);
        printLogs(LogTag, "imageChooser", "exit");
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

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView_getimagewithImage.setImageBitmap(bitmap);
                String mimeType = getContentResolver().getType(filePath);
                extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
                filestring =   getStringImage(bitmap);
                ActionContainerwithImage.setVisibility(View.VISIBLE);
                ActionContainer.setVisibility(View.GONE);
                imageView_getimagewithImage.setVisibility(View.VISIBLE);
                btnSendMessagewithImage.setVisibility(View.VISIBLE);
                mSendMessageButton.setVisibility(View.GONE);
                Linearlayout_ShowImagewithImage.setVisibility(View.VISIBLE);
                scroll_layout_postQuery.fullScroll(View.FOCUS_DOWN);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(requestCode == SELECT_PDF && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri filePath = data.getData();
            String mimeType = getContentResolver().getType(filePath);
            extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
            filestring = getStringPdf(filePath);
            imageView_getimagewithImage.setImageResource(R.drawable.ic_picture_as_pdf);
            ActionContainerwithImage.setVisibility(View.VISIBLE);
            ActionContainer.setVisibility(View.GONE);
            imageView_getimagewithImage.setVisibility(View.VISIBLE);
            btnSendMessagewithImage.setVisibility(View.VISIBLE);
            mSendMessageButton.setVisibility(View.GONE);
            Linearlayout_ShowImagewithImage.setVisibility(View.VISIBLE);
            scroll_layout_postQuery.fullScroll(View.FOCUS_DOWN);
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
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
    public void validateForm() {
        boolean cancel = false;
       if (!validateMessage(mMessageView,inputLayoutMessage)) {
            cancel = true;
        }
        if (cancel) {
            return;
        } else {
            showProgress(true,mContentView,mProgressView);
            this.FormSubmit();
        }
    }
    public void validateForm2() {
        boolean cancel = false;
        if (!validateMessage2(mMessageView,inputLayoutMessage)) {
            cancel = true;
        }
        if (cancel) {
            return;
        } else {
            showProgress(true,mContentView,mProgressView);
            this.FormSubmit2();
        }
    }
    public void FormSubmit(){
        String token = userSessionObj.getToken();
        final String subjectVal = mSubjectView.getText().toString().trim();
        final String messageVal = mMessageView.getText().toString().trim();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.TICKET_URL_PRIVATE;
        printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put(KEY_MESSAGE, messageVal);
            jsonBody.put("title", subjectVal);
            jsonBody.put("i_title_type", selected_subjecttype);
            jsonBody.put(KEY_IMAGE,filestring);
            jsonBody.put("extension",extension);

            printLogs(LogTag,"FormSubmit","jsonBody "+jsonBody);
            printLogs(LogTag,"FormSubmit","image "+filestring);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.POST, FINAL_URL, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject outputJson;
                printLogs(LogTag, "FormSubmit", String.format("RESPONSE : %s", response));
                try {
                    outputJson = new JSONObject(String.valueOf(response));
                    String Status = outputJson.getString(KEY_STATUS);
                    String REF = outputJson.getString(MESSAGE_STATUS);
                    if(Status.equals("1")){
                        showProgress(false,mContentView,mProgressView);
                        String sTitle=getLabelFromDb("m_322_title",R.string.m_322_title);
                        String sMessage1=getLabelFromDb("m_322_message_1",R.string.m_322_message_1);
                        String sMessage2=getLabelFromDb("m_322_message_2_"+URLHelper.PORTAL_ID,getStringResourceId("m_322_message_2_")+URLHelper.PORTAL_ID);
                        String sMessage =sMessage1+"   ["+REF+"]   "+sMessage2;
                        String sButtonLabelClose="Close";
                        ErrorDialog.showSuccessDialogMContactSupport(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose,thisClass);
                    }
                    else{
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-101";
                        String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false,mContentView,mProgressView);
                    String sTitle="Error :"+ActivityId+"-102";
                    String sMessage = getLabelFromDb("error_322_102",R.string.error_try_again);
                    String sButtonLabelClose="Close";
                    ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String sTitle="Error :"+ActivityId+"-103";
                        String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);


                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put("Content-Type", "application/json; charset=utf-8");
                header.put("Accept","*/*");
                return header;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }


    public void FormSubmit2(){
        String token = userSessionObj.getToken();
        final String subjectVal = mSubjectView.getText().toString().trim();
        final String messageVal = mMessageView.getText().toString().trim();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.TICKET_URL_PRIVATE;
        printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put(KEY_MESSAGE, messageVal);
            jsonBody.put("title", subjectVal);
            jsonBody.put("i_title_type", selected_subjecttype);
            printLogs(LogTag,"FormSubmit","jsonBody "+jsonBody);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.POST, FINAL_URL, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject outputJson;
                printLogs(LogTag, "FormSubmit", String.format("RESPONSE : %s", response));
                try {
                    outputJson = new JSONObject(String.valueOf(response));
                    String Status = outputJson.getString(KEY_STATUS);
                    String REF = outputJson.getString(MESSAGE_STATUS);
                    if(Status.equals("1")){
                        showProgress(false,mContentView,mProgressView);
                        String sTitle=getLabelFromDb("m_703_title",R.string.m_703_title);
                        String sMessage1=getLabelFromDb("m_703_message_1",R.string.m_703_message_1);
                        String sMessage2=getLabelFromDb("m_703_message_2_"+URLHelper.PORTAL_ID,getStringResourceId("m_703_message_2_")+URLHelper.PORTAL_ID);
                        String sMessage =sMessage1+"   ["+REF+"]   "+sMessage2;
                        String sButtonLabelClose="Close";
                        ErrorDialog.showSuccessDialogMContactSupport(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose,thisClass);
                    }
                    else{
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-112";
                        String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false,mContentView,mProgressView);
                    String sTitle="Error :"+ActivityId+"-113";
                    String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                    String sButtonLabelClose="Close";
                    ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String sTitle="Error :"+ActivityId+"-103";
                        String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put("Content-Type", "application/json; charset=utf-8");
                header.put("Accept","*/*");
                return header;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
    public boolean validateSubject(EditText mSubjectView,TextInputLayout inputLayoutSubject) {
        String data = mSubjectView.getText().toString().trim();
        setCustomError(inputLayoutSubject,null,mSubjectView);
        if (data.isEmpty() || isValidTitleContactUs(data)) {
            String sMessage = getLabelFromDb("error_322_104",R.string.error_322_104);
            setCustomError(inputLayoutSubject,sMessage,mSubjectView);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutSubject,mSubjectView);
            return true;
        }
    }
    public boolean validateMessage(EditText mMessageView,TextInputLayout inputLayoutMessage) {
        String data = mMessageView.getText().toString().trim();
        setCustomError(inputLayoutMessage,null,mMessageView);
        if (data.isEmpty() || isValidMessageContent(data)) {
            String sMessage = getLabelFromDb("error_322_107",R.string.error_322_107);
            setCustomError(inputLayoutMessage,sMessage,mMessageView);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutMessage,mMessageView);
            return true;
        }
    }
    protected class ErrorTextWatcher implements TextWatcher {
        private EditText EditView;
        private TextInputLayout EditLayout;

        private ErrorTextWatcher(EditText EditView, TextInputLayout EditLayout) {
            this.EditView = EditView;
            this.EditLayout = EditLayout;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (EditView.getId()) {
                case R.id.inputSubject:
                    validateSubject(EditView, EditLayout);
                    break;
                case R.id.inputMessage:
                    validateMessage(EditView, EditLayout);
                    break;
            }
        }
    }
    public boolean validateSubject2(EditText mSubjectView,TextInputLayout inputLayoutSubject) {
        String data = mSubjectView.getText().toString().trim();
        setCustomError(inputLayoutSubject,null,mSubjectView);
        if (data.isEmpty() || isValidTitleContactUs(data)) {
            String sMessage = getLabelFromDb("error_703_104",R.string.error_703_104);
            setCustomError(inputLayoutSubject,sMessage,mSubjectView);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutSubject,mSubjectView);
            return true;
        }
    }
    public boolean validateMessage2(EditText mMessageView,TextInputLayout inputLayoutMessage) {
        String data = mMessageView.getText().toString().trim();
        setCustomError(inputLayoutMessage,null,mMessageView);


        if (data.isEmpty() || isValidMessageContent(data)) {
            String sMessage = getLabelFromDb("error_703_107",R.string.error_703_107);
            setCustomError(inputLayoutMessage,sMessage,mMessageView);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutMessage,mMessageView);
            return true;
        }
    }

    public void  customRedirector(){
        Intent intent = new Intent(MContactSupportPrivateA.this, MDashboardDA.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
                Intent intent = new Intent(MContactSupportPrivateA.this,MDashboardDA.class);
                printLogs(LogTag,"onOptionsItemSelected","MDashboardDA");
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
