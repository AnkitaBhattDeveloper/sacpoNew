package za.co.sacpo.grant.xCubeMentor.notes;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;

import static za.co.sacpo.grant.xCubeLib.component.URLHelper.M_EDIT_PRIVATE_REPORT;

/*activity_mdelete_note*/
public class MDeleteNoteA extends BaseFormAPCPrivate {

    private String ActivityId = "227";

    private String KEY_STUDENT_ID = "n_id";

    public EditText inputHoliDaysName, inputHoliDaysDate,inputApprove;
    public View mProgressView, mContentView,heading;

    public View mProgressRView, mContentRView;
    public EditText inputMStudentID, inputLeaveReasons, inputMStuDate;
    private TextView lbl_date,txt_date,lbl_feedback,txt_feedback,txt_message;
    public Button btn_yes,btn_no;
    private TextView lblView;
    String generator,n_id,date,description;
    MDeleteNoteA thisClass;
    String student_id,student_name,user_id,grant_id,s_w_r_id;

    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cTAId) {
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
        printLogs(LogTag,"onCreate","init");
        printLogs(LogTag,"onCreate","init");
        Bundle activeInputUri = getIntent().getExtras();
        generator = activeInputUri.getString("generator");
      /*  date = activeInputUri.getString("date");

        grant_id = activeInputUri.getString("grant_id");*/
        n_id = activeInputUri.getString("n_id");
        generator = activeInputUri.getString("generator");
        date = activeInputUri.getString("date");
        student_id = activeInputUri.getString("student_id");
        description = activeInputUri.getString("description");
        student_name = activeInputUri.getString("student_name");
        user_id = activeInputUri.getString("user_id");
        grant_id = activeInputUri.getString("grant_id");
        //   printLogs(LogTag,"onCreate","GRANT ID "+grant_id);
        printLogs(LogTag,"onCreate","NOTE ID "+n_id);
        printLogs(LogTag,"onCreate","GENERATOR ID "+generator);
        printLogs(LogTag,"onCreate","date "+date);
        printLogs(LogTag,"onCreate","desctiption "+description);
        printLogs(LogTag,"onCreate","grant_id_Note "+grant_id);
        printLogs(LogTag,"onCreate","user_id_Note "+user_id);

        bootStrapInit();


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

           // showProgress(true,mContentView,mProgressView);
            fetchVersionData();
            verifyVersion();
            internetChangeBroadCast();
            initializeViews();
            initBackBtn();
            initializeLabels();
            initializeListeners();
            userToken =userSessionObj.getToken();
            syncToken(baseApcContext,activityIn);
            if(TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
            callDataApi();
            initializeInputs();
            printLogs(LogTag,"initializeInputs","exitConnected");
        }
    }

    @Override
    protected void initializeLabels() {


        String   Label = getLabelFromDb("h_227",R.string.h_227);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
       
        Label = getLabelFromDb("l_229_btn_save", R.string.l_229_btn_yes);
        lblView = (TextView) findViewById(R.id.btn_yes);
        btn_yes.setText(Label);

        Label = getLabelFromDb("l_229_date", R.string.l_229_date);
        lblView = (TextView) findViewById(R.id.lbl_date);
        lbl_date.setText(Label);

        Label = getLabelFromDb("l_229_feedback", R.string.l_229_feedback);
        lblView = (TextView) findViewById(R.id.lbl_feedback);
        lbl_feedback.setText(Label);

        Label = getLabelFromDb("l_229_btn_no", R.string.l_229_btn_no);
        lblView = (Button) findViewById(R.id.btn_no);
        btn_no.setText(Label);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            btn_no.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            btn_yes.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));

        }


        }


    @Override
    protected void initializeViews() {
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mContentRView = findViewById(R.id.content_container_r);
        mProgressRView = findViewById(R.id.progress_bar_r);
        heading = findViewById(R.id.heading);

        lbl_date = (TextView) findViewById(R.id.lbl_date);

        lbl_feedback = (TextView) findViewById(R.id.lbl_feedback);
        txt_feedback = (TextView) findViewById(R.id.txt_feedback);
        txt_date = (TextView) findViewById(R.id.txt_date);
        txt_message = (TextView) findViewById(R.id.txt_message);



        btn_no = (Button) findViewById(R.id.btn_no);
        btn_yes = (Button) findViewById(R.id.btn_yes);

        txt_date.setText(date);
        txt_feedback.setText(description);

        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);

    }

    @Override
    protected void initializeInputs() {
        printLogs(LogTag,"bootStrapInit","exitConnected");
        showProgress(false,mContentView,mProgressView);

        printLogs(LogTag,"initializeInputs","init");

    }
    @Override
    protected void initializeListeners() {

        printLogs(LogTag,"initializeListeners","init");



        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });
    }



    @Override
    protected void internetChangeBroadCast(){
        registerBroadcastIC();
    }
    ////TODO::@Override
    protected void fetchVersionData(){
        printLogs(LogTag,"fetchVersionData","exitConnected");
        syncUpdates(baseApcContext,activityIn);
    }
    ////TODO::@Override
    protected void verifyVersion(){
        printLogs(LogTag,"verifyVersion","init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag,"verifyVersion","isUpdate "+isUpdate);
        printLogs(LogTag,"verifyVersion","isUpdate "+ utilSessionObj.getIsUpdateRequired());
        if(isUpdate==true){
            Intent intent = new Intent(MDeleteNoteA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
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

    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","a_ga_approval_claim");
        setContentView(R.layout.activity_mdelete_note);
    }

    public void validateForm() {
        boolean cancel = false;

        if (cancel) {
            return;
        } else {
            showProgress(true, mContentView, mProgressView);
            this.FormSubmit();
        }
        printLogs(LogTag,"validateForm","exit");
    }



    public void FormSubmit() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + M_EDIT_PRIVATE_REPORT;
        printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put("n_id", n_id);
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
                    if (Status.equals("1")) {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = getLabelFromDb("m_227_title", R.string.m_227_title);
                        String sMessage = getLabelFromDb("m_227_message", R.string.m_227_message);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showSuccessDialogMDeleteNote(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose,thisClass);


                    }
                    else {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-101";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                }
                catch (JSONException e) {
                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "-102";
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
                        String sTitle = "Error :" + ActivityId + "-103";
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
        RequestQueue requestQueue = Volley.newRequestQueue(MDeleteNoteA.this);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    public void  customRedirector(){
            Intent intent = new Intent(MDeleteNoteA.this, MNoteList.class);
            Bundle activeUri = new Bundle();
            activeUri.putString("description", description);
            activeUri.putString("student_name", student_name);
            activeUri.putString("grant_id", grant_id);
            activeUri.putString("user_id", user_id);
            activeUri.putString("generator", "227");
            intent.putExtras(activeUri);
            startActivity(intent);
            finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            if(generator.equals("222")){
                Bundle activeUri = new Bundle();
                activeUri.putString("generator", "227");
                activeUri.putString("description", description);
                activeUri.putString("student_name", student_name);
                activeUri.putString("grant_id", grant_id);
                activeUri.putString("user_id", user_id);
                Intent intent = new Intent(MDeleteNoteA.this, MNoteList.class);
                intent.putExtras(activeUri);
                startActivity(intent);
                printLogs(LogTag,"onOptionsItemSelected","main");
                finish();
            }

            else {
                Intent intent = new Intent(MDeleteNoteA.this, MNoteList.class);
                Bundle activeUri = new Bundle();
                activeUri.putString("generator", "227");
                activeUri.putString("description", description);
                activeUri.putString("student_name", student_name);
                activeUri.putString("grant_id", grant_id);
                activeUri.putString("user_id", user_id);
                intent.putExtras(activeUri);
                startActivity(intent);
                printLogs(LogTag,"onOptionsItemSelected","default");
                super.onOptionsItemSelected(item);
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
