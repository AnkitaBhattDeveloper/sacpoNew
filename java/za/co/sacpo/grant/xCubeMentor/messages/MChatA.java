package za.co.sacpo.grant.xCubeMentor.messages;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.adapter.ChatMessageAdapter;
import za.co.sacpo.grant.xCubeLib.dataObj.ChatMessageObj;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grant.xCubeLib.session.OlumsUserSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeMentor.MDashboardDA;
import za.co.sacpo.grant.xCubeMentor.grant.MGrantDetailsA;

public class MChatA extends BaseFormAPCPrivate {

    private String ActivityId = "405";
    private String KEY_U_TYPE_ID = "user_type_id";
    public EditText mEmailView, mPasswordView;
    public View mProgressView, mContentView;
    private TextView lblView;
    private String generator;
    String fId,fName,fIsGroup,fImage;
    private ArrayList<ChatMessageObj> messages;
    public static final String KEY_MESSAGE = "msg";
    public static final String KEY_FRIEND_ID = "fid";
    public static final String KEY_U_ID = "u_id";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    public String PUSH_NOTIFICATION ="pushnotification";
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    public EditText editTextMessage;
    private String extension;
  //  public ScrollView scroll_view;
    public String student_id,grant_id;
    public FrameLayout id_Footer;
    private ImageButton mButtonSend;
    MChatA thisClass;
    private  int PICK_PDF_REQUEST=2;
 //   private String KEY_IMAGE = "uploaded_file";
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int PICK_IMAGE_REQUEST = 99;
    private Uri selectedImageUri;
    private Bitmap bitmap;
    public Dialog myDialog;
    ImageView btnAttachement;
    private String uploaded_file;
    private String pdf;
    String mimeType;
    private File myFile;

    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId){
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


        printLogs(LogTag, "onCreate", "init");
        Bundle activeInputUri = getIntent().getExtras();
        fId = activeInputUri.getString("fId");
        fName = activeInputUri.getString("fName");
        fIsGroup = activeInputUri.getString("fIsGroup");
        fImage = activeInputUri.getString("fImage");
        generator = activeInputUri.getString("generator");
        student_id = activeInputUri.getString("student_id");
        grant_id = activeInputUri.getString("grant_id");
        printLogs(LogTag,"onCreate","grant_idgrant_id "+grant_id);
        printLogs(LogTag,"onCreate","fId "+fId);
        printLogs(LogTag,"onCreate","fName "+fName);
        printLogs(LogTag,"onCreate","fIsGroup "+fIsGroup);
        printLogs(LogTag,"onCreate","generatorgenerator "+generator);

        printLogs(LogTag,"onCreate","init");
        // id_Footer.setVisibility(View.VISIBLE);

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
       //     unregisterBroadcastIC();
            showProgress(true,mContentView,mProgressView);
            initializeLabels();
            initializeListeners();
            userToken =userSessionObj.getToken();
            myDialog = new Dialog(MChatA.this);
            syncToken(baseApcContext,activityIn);
            if(TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
            callDataApi();
            fetchData();
            initializeInputs();
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
            Intent intent = new Intent(MChatA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","a_chat");
        setContentView(R.layout.a_m_chat);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        editTextMessage =(EditText) findViewById(R.id.editTextMessage);
        mButtonSend = findViewById(R.id.buttonSend);
       // scroll_view =(ScrollView) findViewById(R.id.scroll_view);
        btnAttachement= findViewById(R.id.attachment);
        id_Footer = (FrameLayout) findViewById(R.id.id_Footer);

        printLogs(LogTag,"initializeViews","exit");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        messages = new ArrayList<>();
    }
    @Override
    protected void initializeLabels(){
        printLogs(LogTag,"initializeLabels","init");
      }
    @Override
    protected void initializeInputs(){

        printLogs(LogTag,"bc","initINputs");
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(PUSH_NOTIFICATION)) {
                    String name = intent.getStringExtra("name");
                    String message = intent.getStringExtra("message");
                    String id = intent.getStringExtra("id");
                    String gid = intent.getStringExtra("gid");
                    int gINTId = Integer.parseInt(gid);
                    int fINTId = Integer.parseInt(id);
                    String type = intent.getStringExtra("type");
                    String image = intent.getStringExtra("image");
                    String filepdf = intent.getStringExtra("pdf");
                    String ext = intent.getStringExtra("ext");
                    String otherImage = fImage;
                    int friend_id = Integer.parseInt(fId);
                    String myImage = userSessionObj.getUserThumb();
                    String f_type = intent.getStringExtra("f_type");
                    printLogs(LogTag, "bc", "fid " + Integer.parseInt(fId));
                    printLogs(LogTag, "bc", "message" + message);
                    printLogs(LogTag, "bc", "name" + name);
                    printLogs(LogTag, "bc", "id" + id);
                    printLogs(LogTag, "bc", "g" + gid);
                    printLogs(LogTag, "bc", "f t" + f_type);

                    if(f_type.equals("2")){
                        if(type.equals("2")) {
                            if (gINTId == friend_id) {
                                processMessage(name, message, id, type,image,myImage,otherImage,filepdf,ext);

                            }
                        }
                    }
                    else {
                        if(type.equals("1")) {
                            if (friend_id == fINTId) {
                                processMessage(name, message, id, type,image,myImage,otherImage,filepdf,ext);
                            }
                        }
                    }
                }
            }
        };
    }
    @Override
    protected void initializeListeners() {
        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });

        editTextMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    id_Footer.setVisibility(View.GONE);
            }
        });
        btnAttachement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });
    }

    public void validateForm() {
        printLogs(LogTag,"validateForm","init");
        this.FormSubmit();
        //id_Footer.setVisibility(View.VISIBLE);
        printLogs(LogTag,"validateForm","exit");
    }
    public void FormSubmit() {
        final String message = editTextMessage.getText().toString().trim();
        if (message.equalsIgnoreCase(""))
            return;

        userSessionObj = new OlumsUserSession(getApplicationContext());
        final int uId = userSessionObj.getUserId();
        final int mfriendId = Integer.parseInt(fId);
        final String uToken = userSessionObj.getToken();
        String name = (userSessionObj.getUserFName() + " " + userSessionObj.getUserSName()).toUpperCase();
        String sentAt = getTimeStamp();
        final String type = fIsGroup;
        final String image = "";
        final String myImage = userSessionObj.getUserThumb();
        String oImage="";
        String extension="";
        if(!fIsGroup.equals("1")){
            oImage=fImage;
        }
        final String otherImage = oImage;
        final String filepdf = oImage;

        ChatMessageObj m = new ChatMessageObj(uId, message, sentAt, name, type, image,myImage,otherImage,filepdf,extension);
        messages.add(m);
       // adapter.notifyDataSetChanged();
      //  scrollToBottom();
        editTextMessage.setText("");

        String FINAL_URL;
        if(fIsGroup.equals("1")){
            FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.R_REF_142_2;
        }
        else {
            FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.R_REF_142_1;
        }
        printLogs(LogTag,"FormSubmit","URL FINAL_URL: "+FINAL_URL);
        printLogs(LogTag,"FormSubmit","INPUT mfriendId: "+mfriendId);
        printLogs(LogTag,"FormSubmit","INPUT fIsGroup : "+fIsGroup);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put(KEY_MESSAGE, message);
            jsonBody.put(KEY_FRIEND_ID, String.valueOf(mfriendId));
            jsonBody.put("token", uToken);
            jsonBody.put("f_type", type);
            jsonBody.put("f_is_group", fIsGroup);
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
                        fetchData();
                    }
                    else{
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-100";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    showProgress(false,mContentView,mProgressView);
                    String sTitle="Error :"+ActivityId+"-101";
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
                        String sTitle="Error :"+ActivityId+"-102";
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

    private void fetchData() {
        messages.clear();
        String token = userSessionObj.getToken();
        final int uId = userSessionObj.getUserId();
        String FINAL_URL;
        if(fIsGroup.equals("1")){
            FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.R_REF_142_3;
        }
        else {
            FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.R_REF_142_4;
        }
        FINAL_URL = FINAL_URL+"?fid="+fId+"&token="+token;
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
                        JSONArray thread = outputJson.getJSONArray("m");
                        for (int i = 0; i < thread.length(); i++) {
                            JSONObject obj = thread.getJSONObject(i);
                            int userId = obj.getInt("from_id");
                            String message = obj.getString("message");
                            String name = obj.getString("from");
                            String sentAt = obj.getString("sent");
                            String otherimage =obj.getString("image_link");
                            String filepdf =obj.getString("image_link");
                            String type = "0";
                            String extension = obj.getString("file_type");
                            String image=fImage;
                            String myimage = userSessionObj.getUserThumb();
                            printLogs(LogTag, "fetchData", "filepdf : " + filepdf);
                            printLogs(LogTag, "fetchData", "otherimage : " + otherimage);
                            ChatMessageObj messagObject = new ChatMessageObj(userId, message, sentAt, name, type, image,myimage,otherimage,filepdf,extension);
                            messages.add(messagObject);
                        }
                        Collections.reverse(messages);
                        adapter = new ChatMessageAdapter(MChatA.this, messages, uId);
                        recyclerView.setAdapter(adapter);
                        scrollToBottom();
                        showProgress(false,mContentView,mProgressView);
                    }else if(Status.equals("2")){
                        showProgress(false,mContentView,mProgressView);
                    }
                    else{
                        //showProgress(false,mContentView,mProgressView);
                        printLogs(LogTag,"fetchData","error_try_again : DATA_ERROR");
                        String sTitle="Error :"+ActivityId+"-103";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    printLogs(LogTag,"fetchData","error_try_again : "+e.getMessage());
                    e.printStackTrace();
                    //showProgress(false,mContentView,mProgressView);
                    String sTitle="Error :"+ActivityId+"-104";
                    String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                    String sButtonLabelClose="Close";
                    ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        printLogs(LogTag,"fetchData","error_try_again : "+error.getMessage());
                        //showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-105";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                }) {
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
    public static String getTimeStamp() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }
    private void scrollToBottom() {
        printLogs(LogTag, "scrollToBottom", "init" + adapter.getItemCount());
        layoutManager.scrollToPosition(messages.size() - 1);
       /* if (adapter.getItemCount() > 1) {
            printLogs(LogTag, "scrollToBottom", "init" + adapter.getItemCount());
            recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, adapter.getItemCount() - 1);
            recyclerView.getLayoutManager().scrollToPosition(adapter.getItemCount() - 1);
        }*/
    }
    private void processMessage(String name, String message, String id, String type,String image,String myImage,String otherImage,String filepdf,String extension) {
        ChatMessageObj m = new ChatMessageObj(Integer.parseInt(id), message, getTimeStamp(), name, type, image,myImage,otherImage,filepdf,extension);
        messages.add(m);
        scrollToBottom();
    }

    public void Popup(Bitmap bitmap){

        Bundle activeInputUri = getIntent().getExtras();
        fId = activeInputUri.getString("fId");
        fName = activeInputUri.getString("fName");
        fIsGroup = activeInputUri.getString("fIsGroup");
        fImage = activeInputUri.getString("fImage");

        printLogs(LogTag,"onCreate","fId "+fId);
        printLogs(LogTag,"onCreate","fName "+fName);
        printLogs(LogTag,"onCreate","fIsGroup "+fIsGroup);
        printLogs(LogTag,"onCreate","init");


        ImageView image_view_show,btn_upload_pic;
        Button btn_cancel;
        myDialog.setContentView(R.layout.pop_layout);
        myDialog.setCanceledOnTouchOutside(false);
        image_view_show = (ImageView) myDialog.findViewById(R.id.image_view_show);
        LinearLayout cons_image = myDialog.findViewById(R.id.cons_image);
        cons_image.setVisibility(View.VISIBLE);
        LinearLayout cons_pdf = myDialog.findViewById(R.id.cons_pdf);
        cons_pdf.setVisibility(View.GONE);
        EditText editText = myDialog.findViewById(R.id.editTextMessage);
        image_view_show.setImageBitmap(bitmap);
        btn_upload_pic = myDialog.findViewById(R.id.btn_upload_pic);
        btn_upload_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadpic(editText,myDialog);
            }
        });

        btn_cancel = myDialog.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myDialog.isShowing()){
                    myDialog.dismiss();
                }

            }
        });
        myDialog.show();
        Window window = myDialog.getWindow();
        window.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

    }
    private void ShowPdfPopup(String pdf, String extension, String filename, String fileSize) {

        ImageView image_view_show,btn_upload_pdf;
        Button btn_cancel;
        myDialog.setContentView(R.layout.pop_layout);
        myDialog.setCanceledOnTouchOutside(false);
        image_view_show = (ImageView) myDialog.findViewById(R.id.image_view_show);
        LinearLayout cons_image = myDialog.findViewById(R.id.cons_image);
        cons_image.setVisibility(View.GONE);
        TextView tv_filename = myDialog.findViewById(R.id.tv_filename);
        tv_filename.setText(filename+"\n"+fileSize+" KB");
        LinearLayout cons_pdf = myDialog.findViewById(R.id.cons_pdf);
        cons_pdf.setVisibility(View.VISIBLE);
        EditText editText = myDialog.findViewById(R.id.pdfeditTextMessage);
        image_view_show.setImageBitmap(bitmap);
        btn_upload_pdf = myDialog.findViewById(R.id.btn_upload_pdf);
        btn_upload_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadpdf(editText,pdf,extension,myDialog);
            }
        });

        btn_cancel = myDialog.findViewById(R.id.btn_pdfcancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myDialog.isShowing()){
                    myDialog.dismiss();
                }

            }
        });
        myDialog.show();
        Window window = myDialog.getWindow();
        window.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

    }
    private void uploadpic(EditText msg, Dialog myDialog) {
        showProgress(true, mContentView, mProgressView);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        Bitmap.CompressFormat.JPEG.toString();
        byte[] bytetosetimge = byteArrayOutputStream.toByteArray();
        uploaded_file = android.util.Base64.encodeToString(bytetosetimge, Base64.DEFAULT);
        uploaded_file = getStringImage(bitmap);
        String token = userSessionObj.getToken();
        final int mfriendId = Integer.parseInt(fId);
        if(extension.equals("")){
            Toast.makeText(thisClass, "Please Select Again..", Toast.LENGTH_SHORT).show();
        }
        //  String image = getStringImage(bitmap);
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.R_REF_142_1;
        printLogs(LogTag, "FormSubmit", "URL : " + FINAL_URL);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", token);
            jsonObject.put("uploaded_file", uploaded_file);
            jsonObject.put("extension", extension);
            jsonObject.put("fid",mfriendId);
            jsonObject.put("msg",msg.getText().toString().trim());
            jsonObject.put("is_group",fIsGroup);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (extension.equals("PNG") || extension.equals("JPEG") || extension.equals("JPG") || extension.equals("png") || extension.equals("jpeg") || extension.equals("jpg")) {

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, FINAL_URL, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    JSONObject outputJson = null;
                    try {
                        printLogs(LogTag, "fetchData", "RESPONSE : " + response);
                        outputJson = new JSONObject(String.valueOf(response));
                        String Status = outputJson.getString(KEY_STATUS);
                        if (Status.equals("1")) {
                            myDialog.dismiss();
                            fetchData();
                        } else {
                            showProgress(false, mContentView, mProgressView);
                            String sTitle = "Error :" + ActivityId + "-101";
                            String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                            String sButtonLabelClose = "Close";
                            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                        }
                    } catch (JSONException e) {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-102";
                        String sMessage = getLabelFromDb("api_post_catchException", R.string.api_post_catchException);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "-103";
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
            RequestQueue requestQueue = Volley.newRequestQueue(MChatA.this);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);
        }
    }
    private void uploadpdf(EditText editText, String pdf, String extension, Dialog myDialog) {
        showProgress(true, mContentView, mProgressView);
        String token = userSessionObj.getToken();
        final int mfriendId = Integer.parseInt(fId);
        if(extension.equals("")){
            Toast.makeText(thisClass, "Please Select Again..", Toast.LENGTH_SHORT).show();
        }
        //  String image = getStringImage(bitmap);
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.R_REF_142_1;
        printLogs(LogTag, "uploadpdf", "URL : " + FINAL_URL);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", token);
            jsonObject.put("uploaded_file", pdf);
            jsonObject.put("extension", extension);
            jsonObject.put("fid",mfriendId);
            jsonObject.put("msg",editText.getText().toString().trim());
            jsonObject.put("is_group",fIsGroup);
            printLogs(LogTag, "uploadpdf", "token : " + jsonObject);
            printLogs(LogTag, "uploadpdf", "extension : " + extension);
            printLogs(LogTag, "uploadpdf", "uploaded_file : " + pdf);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (extension.equals("PDF") || extension.equals("pdf")) {

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, FINAL_URL, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    JSONObject outputJson = null;
                    try {
                        printLogs(LogTag, "uploadpdf", "RESPONSE : " + response);
                        outputJson = new JSONObject(String.valueOf(response));
                        String Status = outputJson.getString(KEY_STATUS);
                        if (Status.equals("1")) {
                            myDialog.dismiss();
                            fetchData();
                           /* String sTitle = getLabelFromDb("m_S217_title", R.string.m_S217_title);
                            String sMessage = getLabelFromDb("m_S217_title", R.string.m_S217_title);
                            String sButtonLabelClose = "Close";
                            ErrorDialog.showSuccessDialogSUploadChatImg(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose, thisClass);
*/
                        } else {
                            showProgress(false, mContentView, mProgressView);
                            String sTitle = "Error :" + ActivityId + "-101";
                            String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                            String sButtonLabelClose = "Close";
                            ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                        }
                    } catch (JSONException e) {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-102";
                        String sMessage = getLabelFromDb("api_post_catchException", R.string.api_post_catchException);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "-103";
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
            RequestQueue requestQueue = Volley.newRequestQueue(MChatA.this);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                String mimeType = getContentResolver().getType(filePath);
                extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
                Popup(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                InputStream inputStream= MChatA.this.getContentResolver().openInputStream(filePath);
                byte[] pdfbyte= new byte[inputStream.available()];
                inputStream.read(pdfbyte);
                pdf=     Base64.encodeToString(pdfbyte,Base64.DEFAULT);
                mimeType = getContentResolver().getType(filePath);
                extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
                //  uploadImageView.setImageResource(Integer.parseInt(pdf));
                //uploadImageView.setVisibility(View.VISIBLE);
                AssetFileDescriptor fileDescriptor = getApplicationContext().getContentResolver().openAssetFileDescriptor(filePath , "r");
                double fileSizeInBytes = fileDescriptor.getLength();
                double fileSizekb = (fileSizeInBytes / 1024);
                String fileSize = String.format("%.2f", fileSizekb);
                File file = new File(filePath.getPath());
                String path = file.getPath();
                String uriString = filePath.toString();
                String displayName = "";
                myFile = new File(uriString);
                String filename=path.substring(path.lastIndexOf("/")+1);
                if (uriString.startsWith("content://")) {
                    Cursor cursor = null;
                    try {
                        cursor = this.getContentResolver().query(filePath, null, null, null, null);
                        if (cursor != null && cursor.moveToFirst()) {
                            displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        }
                    } finally {
                        cursor.close();
                    }
                } else if (uriString.startsWith("file://")) {
                    displayName = myFile.getName();
                }
                ShowPdfPopup(pdf,extension,displayName,fileSize);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void showFileChooser() {
        final CharSequence[] items = { "Choose from Gallery","Upload PDF", "Cancel"};
        final PackageManager pm = getApplicationContext().getPackageManager();
        final AlertDialog.Builder builder = new AlertDialog.Builder(MChatA.this);
        //builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Choose from Gallery")) {
                    dialogInterface.dismiss();
                    isGrantExternalRW(MChatA.this);
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, PICK_IMAGE_REQUEST);}
                else if (items[i].equals("Upload PDF")) {
                    dialogInterface.dismiss();
                    isGrantExternalRW(MChatA.this);
                    Intent pickPdf = new Intent(Intent.ACTION_GET_CONTENT);
                    pickPdf.addCategory(Intent.CATEGORY_OPENABLE);
                    pickPdf.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    pickPdf.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    pickPdf.setType("application/pdf");
                    startActivityForResult(pickPdf, PICK_PDF_REQUEST);
                }
                else if (items[i].equals("Cancel")) {
                    dialogInterface.dismiss();}}});
        builder.show();
    }
    private static boolean isGrantExternalRW(MChatA sChatA) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && (sChatA.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

            ((Activity)sChatA).requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);
            return false;}

        return true;}
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    public void  customRedirector(){


        Bundle activeUri = new Bundle();
        activeUri.putString("fid", "fId");
        activeUri.putString("fIsGroup", "fIsGroup");
        activeUri.putString("fName", "fName");
        activeUri.putString("fImage", "fImage");
        Intent intent = new Intent(MChatA.this, MDashboardDA.class);
        intent.putExtras(activeUri);
        startActivity(intent);
        finish();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        printLogs(LogTag,"onOptionsItemSelected","init");

        int itemId = item.getItemId();
        if (itemId == R.id.s_chat_menu_uploadImage) {
            showFileChooser();
            return true;
        } else if (itemId == R.id.s_chat_menu_msg_remove) {
            Toast.makeText(baseApcContext, "TIME :" + getTimeStamp(), Toast.LENGTH_SHORT).show();
            return true;
        } /*else if (itemId == R.id.home) {
            Intent intent = new Intent(MChatA.this, MChatListA.class);
            printLogs(LogTag, "onOptionsItemSelected", "MChatListA");
            startActivity(intent);
            finish();


            if (generator.equals("406")) {
                Bundle inputUri = new Bundle();
                inputUri.putString("fId", fId);
                inputUri.putString("fIsGroup", fIsGroup);
                inputUri.putString("fImage", fImage);
                inputUri.putString("generator", generator);
                Intent intent0 = new Intent(MChatA.this, MChatListA.class);
                intent0.putExtras(inputUri);
                startActivity(intent0);
                finish();

            } else if (generator.equals("230")) {
                Bundle inputUri = new Bundle();
                inputUri.putString("fId", fId);
                inputUri.putString("fIsGroup", fIsGroup);
                inputUri.putString("fImage", fImage);
                inputUri.putString("grant_id", grant_id);
                inputUri.putString("student_id", student_id);
                inputUri.putString("generator", generator);
                Intent intent2 = new Intent(MChatA.this, MGrantDetailsA.class);
                printLogs(LogTag, "onOptionsItemSelected", "MGrantDetailsA__");
                intent2.putExtras(inputUri);
                startActivity(intent2);
                finish();
            } else {

                Intent intent3 = new Intent(MChatA.this, MChatListA.class);
                printLogs(LogTag, "onOptionsItemSelected", "Default_Intent__");
                startActivity(intent3);
                finish();
            }
        }*/ else {
            if (generator.equals("406")) {
                Bundle inputUri = new Bundle();
                inputUri.putString("fId", fId);
                inputUri.putString("fIsGroup", fIsGroup);
                inputUri.putString("fImage", fImage);
                inputUri.putString("generator", generator);
                Intent intent0 = new Intent(MChatA.this, MChatListA.class);
                intent0.putExtras(inputUri);
                startActivity(intent0);
                finish();

            } else if (generator.equals("230")) {
                Bundle inputUri = new Bundle();
                inputUri.putString("fId", fId);
                inputUri.putString("fIsGroup", fIsGroup);
                inputUri.putString("fImage", fImage);
                inputUri.putString("grant_id", grant_id);
                inputUri.putString("student_id", student_id);
                inputUri.putString("generator", generator);
                Intent intent2 = new Intent(MChatA.this, MGrantDetailsA.class);
                printLogs(LogTag, "onOptionsItemSelected", "MGrantDetailsA__");
                intent2.putExtras(inputUri);
                startActivity(intent2);
                finish();
            } else {

                Intent intent3 = new Intent(MChatA.this, MChatListA.class);
                printLogs(LogTag, "onOptionsItemSelected", "Default_Intent__");
                startActivity(intent3);
                finish();
            }
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
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mRegistrationBroadcastReceiver,new IntentFilter(PUSH_NOTIFICATION));
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcastIC();
    }

}