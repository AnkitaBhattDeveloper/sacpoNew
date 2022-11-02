package za.co.sacpo.grant;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.db.DbSchema;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPublic;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeLib.spinners.SpinnerSet;
import za.co.sacpo.grant.xCubeLib.spinners.SpinnerSetAdapter;

public class ContactSupportPublicA extends BaseFormAPCPublic {
    private String ActivityId = "A98";
    private final String KEY_STATUS = "s";
    private final String MESSAGE_STATUS = "m";
    public String KEY_MESSAGE = "message";
    private String KEY_EMAIL = "email";
    private String KEY_Name = "name";
    private String KEY_USERTYPE = "user_type";
    private String KEY_SUBJECT = "subject";
    public EditText mNameView, mEmailView,mSubjectView,mMessageView;
    public View mProgressView, mContentView;
    public TextInputLayout inputLayoutEmail,inputLayoutName,inputLayoutSubject,inputLayoutMessage;
    public Button mSendMessageButton;
    private TextView lblView,ttSubjectView, ttMessageView;
    int selected_user_type=0;
    Spinner inputSpinnerUserType;
    private SpinnerSetAdapter adapterUserType;
    private static SpinnerSet[] userType;
    ContactSupportPublicA thisClass;

    public void setBaseApcContextParent(Context cnt,AppCompatActivity ain,String lt,String cTAId){
        baseApcContext = cnt;
        CAId=cTAId;
        thisClass = this;
        activityIn = ain;
        LogTag = lt;
        printLogs(lt,"setBaseApcContextParent","weAreIn");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);
        printLogs(LogTag,"onCreate","init");
        setLayoutXml();
        validateLogin(baseApcContext,activityIn);
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        if(isConnected) {
            fetchVersionData();
            verifyVersion();
            internetChangeBroadCast();
            printLogs(LogTag,"onCreate","initConnected");
            initializeViews();
            showProgress(true,mContentView,mProgressView);
            initializeListeners();
            initializeLabels();
            initializeInputs();
            initBackBtn();
            showProgress(false,mContentView,mProgressView);
            printLogs(LogTag,"onCreate","exitConnected");
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
        printLogs(LogTag,"verifyVersion","exitConnected");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        if(utilSessionObj.getIsUpdateRequired()){
            Intent intent = new Intent(ContactSupportPublicA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void setLayoutXml() {
        setContentView(R.layout.a_contact_support_public);
        printLogs(LogTag,"setLayoutXml","a_contact_support_public");
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mEmailView = (EditText) findViewById(R.id.inputEmail);
        mNameView = (EditText) findViewById(R.id.inputName);
        inputSpinnerUserType = (Spinner) findViewById(R.id.inputUsertype);
        mSubjectView = (EditText) findViewById(R.id.inputSubject);
        mMessageView = (EditText) findViewById(R.id.inputMessage);

        inputLayoutEmail = (TextInputLayout) findViewById(R.id.inputLayoutEmail);
        inputLayoutName = (TextInputLayout) findViewById(R.id.inputLayoutName);
        inputLayoutSubject = (TextInputLayout) findViewById(R.id.inputLayoutSubject);
        inputLayoutMessage = (TextInputLayout) findViewById(R.id.inputLayoutMessage);
        ttSubjectView = (TextView)findViewById(R.id.ttSubject);
        ttMessageView = (TextView)findViewById(R.id.ttMessage);
        mSendMessageButton= (Button) findViewById(R.id.btnSendMessage);
        printLogs(LogTag,"initializeViews","exit");
    }
    @Override
    protected void initializeLabels(){
        printLogs(LogTag,"initializeLabels","init");
        String Label = getLabelFromDb("l_A98_email",R.string.l_A98_email);
        lblView = (TextView)findViewById(R.id.lblEmail);
        lblView.setText(Label);

        Label = getLabelFromDb("l_A98_name",R.string.l_A98_name);
        lblView = (TextView)findViewById(R.id.lblName);
        lblView.setText(Label);

        Label = getLabelFromDb("l_A98_subject",R.string.l_A98_subject);
        lblView = (TextView)findViewById(R.id.lblSubject);
        lblView.setText(Label);

        Label = getLabelFromDb("l_A98_message",R.string.l_A98_message);
        lblView = (TextView)findViewById(R.id.lblMessage);
        lblView.setText(Label);

        Label = getLabelFromDb("b_A98_send_message",R.string.b_A98_send_message);
        mSendMessageButton.setText(Label);
        printLogs(LogTag,"initializeLabels","exit");

        Label = getLabelFromDb("h_A98",R.string.h_A98);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);

        ttSubjectView.setText(Html.fromHtml(getString(R.string.question)));
    }
    @Override
    protected void initializeInputs(){
        Cursor resUTM = dbSetaObj.getAllByType(DbSchema.TABLE_MASTER_ARRAYS,DbSchema.COLUMN_M_A_TYPE,6);
        int total_leave_type = resUTM.getCount();
        userType = new SpinnerSet[total_leave_type];
        if(total_leave_type > 0) {
            int i=0;
            try {
                while (resUTM.moveToNext()) {
                    userType[i] = new SpinnerSet();
                    userType[i].setId(resUTM.getInt(resUTM.getColumnIndex(DbSchema.COLUMN_M_A_SERVER_ID)));
                    userType[i].setName(resUTM.getString(resUTM.getColumnIndex(DbSchema.COLUMN_M_A_NAME)));
                    i++;
                }
            } finally {
                if (resUTM != null && !resUTM.isClosed()) {
                    resUTM.close();
                }
            }
        }
        adapterUserType = new SpinnerSetAdapter(ContactSupportPublicA.this,android.R.layout.simple_spinner_item,userType);
        inputSpinnerUserType.setAdapter(adapterUserType);
        printLogs(LogTag,"initializeInputs","init");
    }
    @Override
    protected void initializeListeners() {
        printLogs(LogTag,"initializeListeners","init");
        mSendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });

        ttSubjectView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=getLabelFromDb("tt_A98_subject",R.string.tt_A98_subject);
                showTooltip(ttSubjectView,sToolTip,4);
            }
        });
        ttMessageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=getLabelFromDb("tt_A98_Message",R.string.tt_A98_Message);
                showTooltip(ttMessageView,sToolTip,4);
            }
        });
        inputSpinnerUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,int position, long id) {
                int item =position;
                selected_user_type = userType[item].getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {selected_user_type=0;}
        });

        printLogs(LogTag,"initializeListeners","exit");
    }
    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void validateForm() {
        printLogs(LogTag,"validateForm","init");
        boolean cancel = false;

        if (!isSpinnerValid(selected_user_type)) {
            String ErrorTitle = "Message :" + ActivityId + "-115";
            String ErrorMessage = getLabelFromDb("error_A98_115", R.string.error_A98_115);
            spinnerError(ErrorTitle, ErrorMessage);
            cancel = true;
        }
        else if (!validateName(mNameView,inputLayoutName)) {
            cancel = true;
        }
        else if (!validateEmail(mEmailView,inputLayoutEmail)) {
            cancel = true;
        }
        else if (!validateSubject(mSubjectView,inputLayoutSubject)) {
            cancel = true;
        }
        else if (!validateMessage(mMessageView,inputLayoutMessage)) {
            cancel = true;
        }
        if (cancel) {
            return;
        } else {
            showProgress(true,mContentView,mProgressView);
            this.FormSubmit();
        }
        printLogs(LogTag,"validateForm","exit");
    }
    public void FormSubmit(){
        final String emailVal = mEmailView.getText().toString().trim();
        final String nameVal = mNameView.getText().toString().trim();
        final String usertypeVal = String.valueOf(selected_user_type);
        final String subjectVal = mSubjectView.getText().toString().trim();
        final String messageVal = mMessageView.getText().toString().trim();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.TICKET_URL;
        printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put(KEY_EMAIL, emailVal);
            jsonBody.put(KEY_USERTYPE, usertypeVal);
            jsonBody.put(KEY_Name, nameVal);
            jsonBody.put(KEY_MESSAGE, messageVal);
            jsonBody.put(KEY_SUBJECT, subjectVal);

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
                        mEmailView.setText(null);
                        mNameView.setText(null);
                        mSubjectView.setText(null);
                        mMessageView.setText(null);

                        showProgress(false,mContentView,mProgressView);
                        String sTitleA=getLabelFromDb("m_A98_title",R.string.m_A98_title);
                        String sTitle = sTitleA+"   ["+REF+"]   ";
                        String sMessage1=getLabelFromDb("m_A98_message_1",R.string.m_A98_message_1);
                        String sMessage2=getLabelFromDb("m_A98_message_2",R.string.m_A98_message_2);
                        String sMessage =sMessage1+sMessage2;
                        String sButtonLabelClose="Close";
                        ErrorDialog.showSuccessDialogContactSupportPublicA(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose,thisClass);
                    }
                    else{
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-98";
                        String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false,mContentView,mProgressView);
                    String sTitle="Error :"+ActivityId+"-98";
                    String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                    String sButtonLabelClose="Close";
                    ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String sTitle="Error :"+ActivityId+"-98";
                        String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                })  {
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






    public void customRedirector(){
        printLogs(LogTag,"customRedirector","init");
        Intent intent = new Intent(ContactSupportPublicA.this,LoginA.class);
        startActivity(intent);
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(ContactSupportPublicA.this,LoginA.class);
            printLogs(LogTag,"onOptionsItemSelected","LoginA");
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