package za.co.sacpo.grant.xCubeStudent.queries;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import androidx.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.xCubeLib.adapter.STCAdapter;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.dataObj.TCObj;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;

public class SQueriesCommentsA extends BaseFormAPCPrivate {
    private String ActivityId="114";
    String generator,t_id,group_id,generatorP;
    private final String KEY_STATUS = "s";
    private final String MESSAGE_STATUS = "m";
    private String KEY_COMMENT="comment";
    private String KEY_TICKET_ID="i_id";
    private String KEY_IMAGE = "uploaded_file";
    private String KEY_EXT = "extension";
    private String extension="";
    public EditText inputComment;
    public View mProgressView, mContentView,mProgressRView,mContentRView,heading;
    public TextInputLayout inputLayoutComment,inputLayoutPassword;
    public Button btnCommitContainer,btnCommitContainerwithImage;
    public ImageButton btnUploadContainer;
    private RecyclerView recyclerViewQ;
    public TCObj rDataObj = new TCObj();
    private List<TCObj.Item> rDataObjList = null;
    private TextView lblView;
    private ImageView get_image01;
    private SQueriesCommentsA thisClass;

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int PICK_IMAGE_REQUEST= 99;
    private Bitmap bitmap;
    private Animator mCurrentAnimator;
    private int mShortAnimationDuration;
    boolean isImageFitToScreen;
    private RelativeLayout rr_showImg;
    private LinearLayout container_upload,container_uploadwithImage;
    private ImageButton imgBtn_removeImage;

    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId){
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
        Bundle activeInputUri = getIntent().getExtras();
        t_id = activeInputUri.getString("t_id");
        generator = activeInputUri.getString("generator");
        generatorP = activeInputUri.getString("generatorParent");
        group_id = activeInputUri.getString("group_id");
        printLogs(LogTag,"onCreate","T ID "+t_id);
        printLogs(LogTag,"onCreate","GENERATOR ID "+generator);
        bootStrapInit();
        printLogs(LogTag,"onCreate","initConnected");
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
            initializeListeners();
            printLogs(LogTag,"bootStrapInit","exitConnected");
            fetchData();
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
            Intent intent = new Intent(SQueriesCommentsA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void setLayoutXml(){
        printLogs(LogTag,"setLayoutXml","a_ticket_comments");
        setContentView(R.layout.a_ticket_comments);
    }
    @Override
    protected void initializeViews() {
        inputComment = (EditText) findViewById(R.id.inputComment);
        inputLayoutComment = (TextInputLayout) findViewById(R.id.inputLayoutComment);

        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mContentRView = findViewById(R.id.content_container_r);
        mProgressRView = findViewById(R.id.progress_bar_r);
        heading = findViewById(R.id.heading);
        rDataObjList = rDataObj.getITEMS();

        recyclerViewQ = (RecyclerView) findViewById(R.id.rvComments);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);
recyclerViewQ.setNestedScrollingEnabled(false);
        setupRecyclerView(recyclerViewQ);
        printLogs(LogTag,"initializeViews","exit");
        btnCommitContainer = (Button) findViewById(R.id.btnCommitContainer);
        btnCommitContainerwithImage = (Button) findViewById(R.id.btnCommitContainerwithImage);
        btnUploadContainer = (ImageButton) findViewById(R.id.btnUploadContainer);
        get_image01 = (ImageView) findViewById(R.id.get_image01);
        rr_showImg = (RelativeLayout) findViewById(R.id.rr_showImg);
        container_upload = (LinearLayout) findViewById(R.id.container_upload);
        container_uploadwithImage = (LinearLayout) findViewById(R.id.container_uploadwithImage);
        imgBtn_removeImage = (ImageButton) findViewById(R.id.imgBtn_removeImage);

    }
    @Override
    protected void initializeLabels(){
        printLogs(LogTag,"initializeLabels","init");
        String Label = getLabelFromDb("l_114_comments",R.string.l_114_comments);
        lblView = (TextView)findViewById(R.id.lblComment);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("l_114_btn_comments",R.string.l_114_btn_comments);
        lblView = (TextView)findViewById(R.id.btnCommitContainer);
        lblView.setText(Label);

  
        Label = getLabelFromDb("l_114_btn_comments",R.string.l_114_btn_comments);
        lblView = (TextView)findViewById(R.id.btnCommitContainerwithImage);
        lblView.setText(Label);


        Label = getLabelFromDb("h_114",R.string.h_114);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            btnCommitContainer.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            btnCommitContainerwithImage.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            inputComment.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));

        }
    }
    @Override
    protected void initializeInputs(){
        printLogs(LogTag,"initializeInputs","init");
    }
    @Override
    protected void initializeListeners() {


        imgBtn_removeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                container_uploadwithImage.setVisibility(View.GONE);
                container_upload.setVisibility(View.VISIBLE);
                rr_showImg.setVisibility(View.GONE);
            }
        });


        btnCommitContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });
        btnCommitContainerwithImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm2();
            }
        });

        btnUploadContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseFile();
            }
        });
        get_image01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // fullscreen();

            }
        });
    }

    private void fullscreen() {
        if(isImageFitToScreen) {
            isImageFitToScreen=false;
            get_image01.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            get_image01.setAdjustViewBounds(true);
        }else{
            isImageFitToScreen=true;
            get_image01.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            get_image01.setScaleType(ImageView.ScaleType.FIT_XY);
        }

    }

    private void ChooseFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
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
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                get_image01.setImageBitmap(bitmap);
                String mimeType = getContentResolver().getType(filePath);
                extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
                printLogs(LogTag,"onActivityResult","extension "+extension);
                container_uploadwithImage.setVisibility(View.VISIBLE);
                rr_showImg.setVisibility(View.VISIBLE);
                container_upload.setVisibility(View.GONE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void validateForm() {
        boolean cancel = false;
        if (!validateComment(inputComment, inputLayoutComment)) {
            cancel = true;
        }
        if (cancel) {
            return;
        } else {
            showProgress(true, mContentView, mProgressView);
            this.FormSubmit2();
        }
        printLogs(LogTag,"validateForm","exit");
    }

    private void validateForm2() {

        boolean cancel = false;
        if (!validateComment(inputComment, inputLayoutComment)) {
            cancel = true;
        }
        if (cancel) {
            return;
        } else {
            showProgress(true, mContentView, mProgressView);
            this.FormSubmit();
        }
        printLogs(LogTag,"validateForm","exit");
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
    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void fetchData(){
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.TICKETS_COMMENTS;
        FINAL_URL=FINAL_URL+"?token="+token+"&issue_id="+t_id;
        printLogs(LogTag,"fetchData","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchData", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if (Status.equals("1")) {
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        for (int i = 0; i < dataM.length(); i++) {
                            int pos = i+1;
                            JSONObject rec = dataM.getJSONObject(i);
                            int aId2 = Integer.parseInt(rec.getString("i_c_id"));
                            String lblRef3 = rec.getString("i_c_message");
                            String getUserName4 = rec.getString("i_c_by");
                            String getlblDate5 = rec.getString("i_date");
                            String getImage6 = rec.getString("i_c_image");
                            rDataObj.addItem(rDataObj.createItem(pos,aId2,lblRef3,getUserName4,getlblDate5,getImage6));
                            showList();
                        }
                        showProgress(false,mContentRView,mProgressRView);

                    }   else if(Status.equals("2")) {
                        showProgress(false,mContentView,mProgressView);
                    }
                    else{
                        showProgress(false,mContentRView,mProgressRView);
                        printLogs(LogTag, "fetchData", "error_try_again : DATA_ERROR");
                        String sTitle="Error :"+ActivityId+"-101";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    printLogs(LogTag, "fetchData", "error_try_again : " + e.getMessage());
                    e.printStackTrace();
                    showProgress(false,mContentRView,mProgressRView);
                    String sTitle = "Error :" + ActivityId + "-102";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showProgress(false,mContentRView,mProgressRView);
                printLogs(LogTag, "fetchData", "error_try_again: " + error.getMessage());
                String sTitle = "Error :" + ActivityId + "-103";
                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    private boolean validateComment(EditText inputComment, TextInputLayout inputLayoutComment) {
        String comment = inputComment.getText().toString().trim();
        setCustomError(inputLayoutComment,null,inputComment);
        if (comment.isEmpty() || isValidMessageComment(comment)) {
            String sMessage = getLabelFromDb("error_S105_empty",R.string.error_S105_empty);
            setCustomError(inputLayoutComment,sMessage,inputComment);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutComment,inputComment);
            return true;
        }
    }

    public void FormSubmit(){
        final String comment = inputComment.getText().toString().trim();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.ADD_TICKETS_COMMENTS;
        String token = userSessionObj.getToken();
        printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);
        String image = getStringImage(bitmap);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put(KEY_COMMENT, comment);
            jsonBody.put(KEY_TICKET_ID, t_id);
            jsonBody.put(KEY_EXT,extension);
            jsonBody.put(KEY_IMAGE,image);


            printLogs(LogTag, "FormSubmit", "jsonBody " + jsonBody);
            printLogs(LogTag, "FormSubmit", "extension " + extension);
            printLogs(LogTag, "FormSubmit", "image " + image);
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
                        String sTitle=getLabelFromDb("m_114_title",R.string.m_114_title);
                        String sMessage =getLabelFromDb("m_114_message_1",R.string.m_114_message_1);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showSuccessDialogSQueriesCommentsA(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose,thisClass);
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
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/json; charset=utf-8");
                header.put("Accept","*/*");
                return header;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(SQueriesCommentsA.this);
        requestQueue.add(jsonObjectRequest);
    }


    private void FormSubmit2() {
        final String comment = inputComment.getText().toString().trim();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.ADD_TICKETS_COMMENTS;
        String token = userSessionObj.getToken();
        printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put(KEY_COMMENT, comment);
            jsonBody.put(KEY_TICKET_ID, t_id);

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
                        String sTitle=getLabelFromDb("m_114_title",R.string.m_114_title);
                        String sMessage =getLabelFromDb("m_114_message_1",R.string.m_114_message_1);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showSuccessDialogSQueriesCommentsA(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose,thisClass);

                    }
                    else{
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-104";
                        String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false,mContentView,mProgressView);
                    String sTitle="Error :"+ActivityId+"-105";
                    String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                    String sButtonLabelClose="Close";
                    ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String sTitle="Error :"+ActivityId+"-106";
                        String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
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
        RequestQueue requestQueue = Volley.newRequestQueue(SQueriesCommentsA.this);
        requestQueue.add(jsonObjectRequest);
    }


    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        printLogs(LogTag,"setupRecyclerView","init");
        recyclerView.setAdapter(new STCAdapter(rDataObjList,baseApcContext,activityIn,ActivityId));
    }
    public void showList(){
        printLogs(LogTag,"showList","init");
        List<TCObj.Item> NGData = rDataObj.getITEMS();
        STCAdapter adapter = new STCAdapter(NGData,baseApcContext,activityIn,ActivityId);
        recyclerViewQ.setAdapter(adapter);
        showProgress(false,mContentRView,mProgressRView);
    }
    public void customRedirector(){
        Intent intent = new Intent(SQueriesCommentsA.this, SOutgoingQueriesA.class);
        Bundle inputUri = new Bundle();
        inputUri.putString("generator","114");
        inputUri.putString("t_id", t_id);
        inputUri.putString("generator", generatorP);
        inputUri.putString("group_id", group_id);
        intent.putExtras(inputUri);
        startActivity(intent);
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Bundle inputUri = new Bundle();
            inputUri.putString("t_id", t_id);
            inputUri.putString("generator", generatorP);
            inputUri.putString("group_id", group_id);
            Intent intent = new Intent(SQueriesCommentsA.this,SQueriesGroupA.class);
            intent.putExtras(inputUri);
            printLogs(LogTag,"onOptionsItemSelected","SQueriesDetailsA");
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