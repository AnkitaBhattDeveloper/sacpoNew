package za.co.sacpo.grantportal.xCubeStudent.queries;

import android.Manifest;
import android.animation.Animator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
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
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import za.co.sacpo.grantportal.AppUpdatedA;
import za.co.sacpo.grantportal.R;

import za.co.sacpo.grantportal.xCubeLib.adapter.SpinAdapter;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grantportal.xCubeLib.dataObj.SpinnerObj;
import za.co.sacpo.grantportal.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.component.Utils;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;


import za.co.sacpo.grantportal.xCubeStudent.SDashboardDA;

public class SContactSupportPrivateA extends BaseFormAPCPrivate {
    private final String ActivityId="110";
    private final String KEY_STATUS = "s";
    private final String MESSAGE_STATUS = "m";
    public String KEY_MESSAGE = "message";
    private final String KEY_SUBJECT = "title";
    private final String KEY_IMAGE = "uploaded_file";
    private final String KEY_EXT = "extension";
    private String extension="",extensionOne="";
    public EditText mSubjectView,mMessageView,inputtickettypeSubject,inputEname,inputMFName,inputMSName,inputMCell,inputMTel,inputMEmail;
    public View mProgressView, mContentView,heading;
    public TextInputLayout inputLayoutSubject,inputLayoutMessage,inputLayoutEname,inputLayoutMFName,inputLayoutMSName,inputLayoutMCell,inputLayoutMTel,inputLayoutMEmail;
    public Button mSendMessageButton;
    private TextView lblView,ttSubjectView,ttMessageView,txtFilePath,mEmail,mCSupervisor,mCSupervisorData,gCDateData;
    public Button btnSendMessage,btnSendMessagewithImage,btnChoose;
    public ImageButton imgBtn_removeImage;
    private LinearLayout ActionContainerwithImage,ActionContainer,topicContainer,ticket_typeContainer
                        ,ll_topic,tickettypetitleContainer,ll_emp_update,ll_mentor_update,ll_gd_update;
    String filestring="",i_requested_file="";
    int SELECT_PDF = 20;
    public String generator,title_value,grant_s_date,grant_e_date,emp_name,m_title,m_u_title,m_fname,m_surname,m_email,m_cell_no,m_office_cell_no;
    private ImageButton uploadImage_btn;
    private Bitmap bitmap;
    private ImageView imageView_getimagewithImage;
    private LinearLayout Linearlayout_ShowImagewithImage;
    private ScrollView scroll_layout_postQuery;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int PICK_IMAGE_REQUEST= 99;
    private Animator mCurrentAnimator;
    private int mShortAnimationDuration,selected_subjecttype,i_stu_issue_title_type;
    SContactSupportPrivateA thisClass;
    Spinner inputSpinnerTitle,inputSpinnersubject;
    CheckBox cb_ticket_type,cb_m_email_change;
    int is_email_changed;

    private File myFile;
    public SpinnerObj[] StuTicketIssue ;
    public SpinnerObj[] TicketIssue ;
   // ArrayList<SpinnerObj> StuTicketIssue = new ArrayList<>();


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
            fetchTicketIssueSpinner();
            fetchStudentData();
            printLogs(LogTag,"bootStrapInit","exitConnected");
            showProgress(false,mContentView,mProgressView);
        }
    }

    private void fetchStudentData() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.STU_DATA_FOR_CONTACT;
        FINAL_URL=FINAL_URL+"?token="+token;
        //FINAL_URL=FINAL_URL+"/"+token;
        printLogs(LogTag,"fetchStudentData","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchStudentData", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONObject dataM = outputJson.getJSONObject(KEY_DATA);
                        printLogs(LogTag, "fetchStudentData", "dataM : " + dataM);
                        grant_s_date=dataM.getString("grant_s_date");
                        grant_e_date=dataM.getString("grant_e_date");
                        emp_name=dataM.getString("emp_name");
                        m_title=dataM.getString("m_title");
                        m_u_title=dataM.getString("m_u_title");
                        m_fname=dataM.getString("m_fname");
                        m_surname=dataM.getString("m_surname");
                        m_email=dataM.getString("m_email");
                        m_cell_no=dataM.getString("m_cell_no");
                        m_office_cell_no=dataM.getString("m_office_cell_no");



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

    private void fetchTicketIssueSpinner() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.STU_ISSUE_TITLE_TYPE;
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
                        StuTicketIssue = new SpinnerObj[dataM.length()];
                        for (int i = 0; i <dataM.length() ; i++) {
                            JSONObject jsonObject = dataM.getJSONObject(i);
                            StuTicketIssue[i] = new SpinnerObj();
                            StuTicketIssue[i].setId(jsonObject.getString("i_title_id"));
                            StuTicketIssue[i].setName(jsonObject.getString("i_title_type"));

                        }
                        SpinAdapter adapter = new SpinAdapter(SContactSupportPrivateA.this, android.R.layout.simple_spinner_item, StuTicketIssue);
                        inputSpinnerTitle.setAdapter(adapter);
                        inputSpinnerTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                               SpinnerObj obj = adapter.getItem(i);
                               i_stu_issue_title_type = Integer.parseInt(adapter.getItem(i).getId());
                                if(i_stu_issue_title_type == 0){
                                    tickettypetitleContainer.setVisibility(View.VISIBLE);
                                    ll_emp_update.setVisibility(View.GONE);
                                    ll_mentor_update.setVisibility(View.GONE);
                                    ll_gd_update.setVisibility(View.GONE);
                                }else{
                                    tickettypetitleContainer.setVisibility(View.GONE);
                                    ll_gd_update.setVisibility(View.GONE);
                                    ll_emp_update.setVisibility(View.GONE);
                                    ll_mentor_update.setVisibility(View.GONE);
                                    mEmail.setVisibility(View.GONE);
                                    cb_m_email_change.setVisibility(View.GONE);
                                    mCSupervisor.setVisibility(View.GONE);
                                    mCSupervisorData.setVisibility(View.GONE);
                                    if(i_stu_issue_title_type==11){
                                        ll_emp_update.setVisibility(View.VISIBLE);
                                        inputEname.setText(emp_name);
                                    }
                                    if(i_stu_issue_title_type==12){
                                        ll_emp_update.setVisibility(View.VISIBLE);
                                        ll_mentor_update.setVisibility(View.VISIBLE);
                                    }
                                    if(i_stu_issue_title_type==13){
                                        ll_mentor_update.setVisibility(View.VISIBLE);
                                        mCSupervisor.setVisibility(View.VISIBLE);
                                        mCSupervisorData.setVisibility(View.VISIBLE);
                                        //grant_s_date,grant_e_date,emp_name,m_title,m_u_title,m_fname,m_surname,m_email,m_cell_no,m_office_cell_no
                                        mCSupervisorData.setText(m_title+" "+m_fname+" "+m_surname+ " "+m_email);
                                    }
                                    if(i_stu_issue_title_type==14){
                                        ll_gd_update.setVisibility(View.VISIBLE);
                                        gCDateData.setText(grant_s_date + " - " +grant_e_date);
                                    }

                                    if(i_stu_issue_title_type==17){
                                        ll_mentor_update.setVisibility(View.VISIBLE);
                                        inputLayoutMEmail.setVisibility(View.GONE);
                                        mEmail.setVisibility(View.VISIBLE);
                                        cb_m_email_change.setVisibility(View.VISIBLE);
                                        inputMFName.setText(m_fname);
                                        inputMSName.setText(m_surname);
                                        mEmail.setText(m_email);
                                        inputMEmail.setText(m_email);
                                        inputMCell.setText(m_cell_no);
                                        inputMTel.setText(m_office_cell_no);
                                    }
                                }
                                printLogs(LogTag, "inputSpinnerSubjectType", "i_stu_issue_title_type : "+i_stu_issue_title_type);
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
            Intent intent = new Intent(SContactSupportPrivateA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","a_contact_support_private");
        setContentView(R.layout.a_contact_support_private);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        heading = findViewById(R.id.heading);
        mSubjectView = findViewById(R.id.inputSubject);
        mMessageView = findViewById(R.id.inputMessage);
        inputSpinnerTitle = findViewById(R.id.inputSpinnerTitle);
        inputSpinnersubject = findViewById(R.id.inputSpinnersubject);
        topicContainer = findViewById(R.id.topicContainer);
        tickettypetitleContainer = findViewById(R.id.tickettypetitleContainer);
        cb_ticket_type = findViewById(R.id.cb_ticket_type);
        ticket_typeContainer = findViewById(R.id.ticket_typeContainer);
        btnChoose = findViewById(R.id.btnChoose);
        txtFilePath = findViewById(R.id.txtFilePath);
        ll_topic = findViewById(R.id.ll_topic);
        inputtickettypeSubject = findViewById(R.id.inputtickettypeSubject);

        gCDateData = findViewById(R.id.gCDateData);
        inputEname = findViewById(R.id.inputEname);
        inputLayoutEname = findViewById(R.id.inputLayoutEname);
        ll_emp_update = findViewById(R.id.ll_emp_update);
        ll_mentor_update=findViewById(R.id.ll_mentor_update);
        ll_gd_update=findViewById(R.id.ll_gd_update);
        inputLayoutMFName=findViewById(R.id.inputLayoutMFName);
        inputMFName=findViewById(R.id.inputMFName);
        inputMSName=findViewById(R.id.inputMSName);
        inputLayoutMSName=findViewById(R.id.inputLayoutMSName);
        inputLayoutMCell=findViewById(R.id.inputLayoutMCell);
        inputMCell=findViewById(R.id.inputMCell);
        inputLayoutMTel=findViewById(R.id.inputLayoutMTel);
        inputMTel=findViewById(R.id.inputMTel);
        mEmail=findViewById(R.id.mEmail);
        cb_m_email_change=findViewById(R.id.cb_m_email_change);
        inputLayoutMEmail=findViewById(R.id.inputLayoutMEmail);
        inputMEmail=findViewById(R.id.inputMEmail);
        mCSupervisor=findViewById(R.id.mCSupervisor);
        mCSupervisorData=findViewById(R.id.mCSupervisorData);
        inputLayoutSubject = findViewById(R.id.inputLayoutSubject);
        inputLayoutMessage = findViewById(R.id.inputLayoutMessage);

        btnSendMessagewithImage = findViewById(R.id.btnSendMessagewithImage);

        mSendMessageButton= findViewById(R.id.btnSendMessage);
        uploadImage_btn= findViewById(R.id.uploadImage_btn);
        imageView_getimagewithImage= findViewById(R.id.imageView_getimagewithImage);
        Linearlayout_ShowImagewithImage= findViewById(R.id.Linearlayout_ShowImagewithImage);
        scroll_layout_postQuery = findViewById(R.id.scroll_layout_postQuery);
        imgBtn_removeImage = findViewById(R.id.imgBtn_removeImage);
        ActionContainer = findViewById(R.id.ActionContainer);
        ActionContainerwithImage = findViewById(R.id.ActionContainerwithImage);

        printLogs(LogTag,"initializeViews","exit");
    }
    @Override
    protected void initializeLabels(){
        printLogs(LogTag,"initializeLabels","init");

        String Label = getLabelFromDb("b_110_send_message",R.string.b_110_send_message);
        mSendMessageButton.setText(Label);

        Label = getLabelFromDb("b_703_send_message",R.string.b_703_send_message);
        btnSendMessagewithImage.setText(Label);

        Label = getLabelFromDb("h_110",R.string.h_110);
        lblView = findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("b_133_l_choose_file",R.string.b_133_l_choose_file);
        lblView = findViewById(R.id.btnChoose);
        lblView.setText(Label);

        Label = getLabelFromDb("l_110_ticket_type",R.string.l_110_ticket_type);
        lblView = findViewById(R.id.lblselecttickettype);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("l_110_fileins",R.string.l_110_fileins);
        lblView = findViewById(R.id.txtfileins);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

         Label = getLabelFromDb("lbl_M345_nofileChosen",R.string.lbl_M345_nofileChosen);
        txtFilePath.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        txtFilePath.setText(Label);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            mSendMessageButton.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            btnSendMessagewithImage.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            btnChoose.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            inputSpinnerTitle.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            inputSpinnersubject.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            mMessageView.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
        }
    }
    @Override
    protected void initializeInputs(){
        printLogs(LogTag,"initializeInputs","init");
        is_email_changed = 0;
        ticket_typeContainer.setVisibility(View.VISIBLE);
        ll_topic.setVisibility(View.GONE);
    }
    @Override
    protected void initializeListeners() {
        printLogs(LogTag,"initializeListeners","init");

        cb_m_email_change.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    is_email_changed = 1;
                    inputLayoutMEmail.setVisibility(View.VISIBLE);
                }else{
                    is_email_changed = 0;
                    inputLayoutMEmail.setVisibility(View.GONE);
                }
            }
        });
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                getFileChooserIntent();
             //   fileChooser();
            }
        });

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

        uploadImage_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // showFileChooser();
                showFileChooser();
              //  showBottomSheetDialog();
            }
        });
        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);

        printLogs(LogTag,"initializeListeners","exit");
    }
    private void showFileChooser() {
        final CharSequence[] items = { "Choose from Gallery","Upload PDF", "Cancel"};
        final PackageManager pm = getApplicationContext().getPackageManager();
        final AlertDialog.Builder builder = new AlertDialog.Builder(SContactSupportPrivateA.this);
    //    builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Choose from Gallery")) {
                    dialogInterface.dismiss();
                    isGrantExternalRW(SContactSupportPrivateA.this);
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, PICK_IMAGE_REQUEST);}
                else if (items[i].equals("Upload PDF")) {
                    dialogInterface.dismiss();
                    isGrantExternalRW(SContactSupportPrivateA.this);
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
    private static boolean isGrantExternalRW(SContactSupportPrivateA sChatA) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && (sChatA.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

            ((Activity)sChatA).requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);
            return false;}

        return true;}

    private void getFileChooserIntent() {
        String[] mimeTypes = {"image/*", "application/pdf"};
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);}
        } else {
            String mimeTypesStr = "";
            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";}
            intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
        }
        startActivityForResult(Intent.createChooser(intent, "Select File to Upload"), 121);
    }
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
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
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
        }else if(requestCode == 121 && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri filePath = data.getData();
            String mimeType = getContentResolver().getType(filePath);
            extensionOne = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
            String uriString = filePath.toString();
            myFile = new File(uriString);
            String displayName;
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
            if(extensionOne.equals("pdf") || extensionOne.equals("PDF")){
                i_requested_file = getStringPdf(filePath);
            }else if(extensionOne.equals("jpg") || extensionOne.equals("jpeg") || extensionOne.equals("png")
                    || extensionOne.equals("JPG") || extensionOne.equals("JPEG") || extensionOne.equals("PNG")){
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    i_requested_file = getStringImage(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
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
        final String i_stu_issue_title = inputtickettypeSubject.getText().toString().trim();

        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.TICKET_URL_STU_PRIVATE;
        printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put("checkbox", is_email_changed);
            jsonBody.put("extensionOne", extensionOne);
            jsonBody.put("i_stu_issue_title_type", i_stu_issue_title_type);
            jsonBody.put("i_stu_issue_title", i_stu_issue_title);
            jsonBody.put("i_title_type", selected_subjecttype);
            jsonBody.put("i_title", subjectVal);
            jsonBody.put("i_message", messageVal);
            jsonBody.put("extension",extension);
            jsonBody.put("i_issue_image",filestring);
            jsonBody.put("i_requested_file", i_requested_file);

            printLogs(LogTag,"FormSubmit","jsonBody "+jsonBody);
            printLogs(LogTag,"FormSubmit","extension "+extensionOne);
            printLogs(LogTag,"FormSubmit","i_requested_file "+i_requested_file);
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
                    String REF = jsonObject.getString(MESSAGE_STATUS);
                    if(Status.equals("1")){
                        showProgress(false,mContentView,mProgressView);
                        String sTitle=getLabelFromDb("m_110_title",R.string.m_110_title);
                        String sMessage1=getLabelFromDb("m_110_message_1",R.string.m_110_message_1);
                        String sMessage2=getLabelFromDb("m_110_message_2_"+URLHelper.PORTAL_ID,getStringResourceId("m_110_message_2_")+URLHelper.PORTAL_ID);
                        String sMessage =sMessage1+"   ["+REF+"]   "+sMessage2;
                        String sButtonLabelClose="Close";
                        ErrorDialog.showSuccessDialogSContactSupportPrivateA(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose,thisClass);
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
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }


    public void FormSubmit2(){
        String token = userSessionObj.getToken();
        final String subjectVal = mSubjectView.getText().toString().trim();
        final String messageVal = mMessageView.getText().toString().trim();
        final String i_stu_issue_title = inputtickettypeSubject.getText().toString().trim();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.TICKET_URL_STU_PRIVATE;
        printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put("checkbox", is_email_changed);
            jsonBody.put("extensionOne", extensionOne);
            jsonBody.put("i_stu_issue_title_type", i_stu_issue_title_type);
            jsonBody.put("i_stu_issue_title", i_stu_issue_title);
            jsonBody.put("i_title_type", selected_subjecttype);
            jsonBody.put("i_title", subjectVal);
            jsonBody.put("i_message", messageVal);
            jsonBody.put("extension",extension);
            jsonBody.put("i_issue_image",filestring);
            jsonBody.put("i_requested_file", i_requested_file);

            printLogs(LogTag,"FormSubmit","jsonBody "+jsonBody);
            printLogs(LogTag,"FormSubmit","extension "+extensionOne);
            printLogs(LogTag,"FormSubmit","i_requested_file "+i_requested_file);

            printLogs(LogTag,"FormSubmit","jsonBody "+jsonBody);
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
                    String REF = jsonObject.getString(MESSAGE_STATUS);
                    if(Status.equals("1")){
                        showProgress(false,mContentView,mProgressView);
                        String sTitle=getLabelFromDb("m_703_title",R.string.m_703_title);
                        String sMessage1=getLabelFromDb("m_703_message_1",R.string.m_703_message_1);
                        String sMessage2=getLabelFromDb("m_703_message_2_"+URLHelper.PORTAL_ID,getStringResourceId("m_703_message_2_")+URLHelper.PORTAL_ID);
                        String sMessage =sMessage1+"   ["+REF+"]   "+sMessage2;
                        String sButtonLabelClose="Close";
                        ErrorDialog.showSuccessSContactSupport(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose,thisClass);
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
                        error.printStackTrace();
                        String sTitle="Error :"+ActivityId+"-103";
                        String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
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


    public void customRedirector(){
        Intent intent = new Intent(SContactSupportPrivateA.this,SDashboardDA.class);
        startActivity(intent);
        finish();
    }

    public boolean validateSubject(EditText mSubjectView,TextInputLayout inputLayoutSubject) {
        String data = mSubjectView.getText().toString().trim();
        setCustomError(inputLayoutSubject,null,mSubjectView);
        if (data.isEmpty() || isValidTitleContactUs(data)) {
            String sMessage = getLabelFromDb("error_110_104",R.string.error_110_104);
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
            String sMessage = getLabelFromDb("error_110_107",R.string.error_110_107);
            setCustomError(inputLayoutMessage,sMessage,mMessageView);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutMessage,mMessageView);
            return true;
        }
    }
    protected class ErrorTextWatcher implements TextWatcher {
        private final EditText EditView;
        private final TextInputLayout EditLayout;

        private ErrorTextWatcher(EditText EditView, TextInputLayout EditLayout) {
            this.EditView = EditView;
            this.EditLayout = EditLayout;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            int id = EditView.getId();
            if (id == R.id.inputSubject) {//   validateSubject(EditView, EditLayout);
            } else if (id == R.id.inputMessage) {
                validateMessage(EditView, EditLayout);
            }
        }
    }

    public boolean validateSubject2(EditText mSubjectView,TextInputLayout inputLayoutSubject) {
        String data = mSubjectView.getText().toString().trim();
        setCustomError(inputLayoutSubject,null,mSubjectView);
        Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/<>.^*()%!-]");
        if (data.isEmpty() || isValidTitleContactUs(data) || regex.matcher(data).find()) {
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
        Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/<>.^*()%!-]");
        if (data.isEmpty() || isValidMessageContent(data) || regex.matcher(data).find()) {
            String sMessage = getLabelFromDb("error_703_107",R.string.error_703_107);
            setCustomError(inputLayoutMessage,sMessage,mMessageView);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutMessage,mMessageView);
            return true;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {

            Intent intent = new Intent(SContactSupportPrivateA.this,SDashboardDA.class);
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