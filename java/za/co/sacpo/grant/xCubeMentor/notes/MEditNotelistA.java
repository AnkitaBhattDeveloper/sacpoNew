package za.co.sacpo.grant.xCubeMentor.notes;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeLib.db.DbSchema;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeLib.spinners.SpinnerSet;
import za.co.sacpo.grant.xCubeLib.spinners.SpinnerSetAdapter;

/*a_m_edit_notelist*/
public class MEditNotelistA extends BaseFormAPCPrivate {

    private String ActivityId="226";
    private String KEY_NOTE="note";
    private String KEY_USER_ID="n_id";
    private String KEY_DURATION="duration";
    private String KEY_TYPE="catagory";
    private MEditNotelistA thisClass;
    String n_id,user_id,grant_id;
    public EditText inputFirstName,inputLastName,inputMobile,inputNote;
    TextInputLayout inputLayoutNote;
    public View mProgressView, mContentView,heading;

    public Button btnAddNote;

    private TextView lblView,txtStudentName;
    String student_name,generator,category_id;
    int selected_category_type=0;
    Spinner inputSpinnerCategory;
    private SpinnerSetAdapter adapterNoteCategory;
    private static SpinnerSet[] CategoryTypeMasters;
    String description;
    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cTAId){
        baseApcContext = cnt;CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        printLogs(lt,"setBaseApcContextParent","weAreIn");
        thisClass = this;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);
        printLogs(LogTag,"onCreate","init");
        Bundle activeInputUri = getIntent().getExtras();
        n_id = activeInputUri.getString("n_id");
        description = activeInputUri.getString("description");
        generator = activeInputUri.getString("generator");
        student_name = activeInputUri.getString("student_name");
        user_id = activeInputUri.getString("user_id");
        grant_id = activeInputUri.getString("grant_id");
        selected_category_type = Integer.parseInt(activeInputUri.getString("category_id"));

        printLogs(LogTag,"onCreate","NOTE ID "+n_id);
        printLogs(LogTag,"onCreate","GENERATOR ID "+generator);
        printLogs(LogTag,"onCreate","description "+description);
        printLogs(LogTag,"onCreate","student_name "+student_name);
        printLogs(LogTag,"onCreate","grant_id_Note "+grant_id);
        printLogs(LogTag,"onCreate","user_id_Note "+user_id);
        printLogs(LogTag,"onCreate","category_id "+selected_category_type);

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
    protected void verifyVersion() {
        printLogs(LogTag,"verifyVersion","init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag,"verifyVersion","isUpdate "+isUpdate);
        printLogs(LogTag,"verifyVersion","isUpdate "+ utilSessionObj.getIsUpdateRequired());
        if(isUpdate){
            Intent intent = new Intent(MEditNotelistA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    protected void setLayoutXml(){
        printLogs(LogTag,"setLayoutXml","activity_madd_not");
        setContentView(R.layout.a_m_edit_notelist);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        inputMobile = (EditText) findViewById(R.id.inputMobile);


        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        heading = findViewById(R.id.heading);
        btnAddNote = (Button) findViewById(R.id.btnAddNote);
        inputNote = (EditText) findViewById(R.id.inputNote);
        txtStudentName= (TextView)findViewById(R.id.txtStudentName);
        inputSpinnerCategory = (Spinner) findViewById(R.id.inputSpinnerCategory);
        inputLayoutNote = (TextInputLayout) findViewById(R.id.inputLayoutNote);

        inputNote.setText(description);

    }
    @Override
    protected void initializeLabels(){

        printLogs(LogTag,"initializeLabels","init");
        String Label = getLabelFromDb("l_edit_not",R.string.l_edit_not);
        lblView = (TextView)findViewById(R.id.lblNote);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("b_226_add_note",R.string.b_226_add_note);
        btnAddNote.setText(Label);

        Label = getLabelFromDb("h_226",R.string.h_226);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            btnAddNote.setBackground(getDrawable(getDrwabaleResourceId("themed_small_button")));
            txtStudentName.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
            inputNote.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputSpinnerCategory.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));

        }

    }
    @Override
    protected void initializeInputs(){
        printLogs(LogTag,"initializeInputs","init");

        printLogs(LogTag,"bootStrapInit","exitConnected");
        showProgress(false,mContentView,mProgressView);
        txtStudentName.setText(student_name);
        printLogs(LogTag,"initializeInputs","init");
        dbSetaObj = DbHelper.getInstance(this);

        Cursor resUTM = dbSetaObj.getAllByType(DbSchema.TABLE_MASTER_ARRAYS,DbSchema.COLUMN_M_A_TYPE,7);
        int total_leave_type = resUTM.getCount();
        CategoryTypeMasters = new SpinnerSet[total_leave_type];
        if(total_leave_type > 0) {
            int i=0;
            try {
                while (resUTM.moveToNext()) {
                    CategoryTypeMasters[i] = new SpinnerSet();
                    CategoryTypeMasters[i].setId(resUTM.getInt(resUTM.getColumnIndex(DbSchema.COLUMN_M_A_SERVER_ID)));
                    CategoryTypeMasters[i].setName(resUTM.getString(resUTM.getColumnIndex(DbSchema.COLUMN_M_A_NAME)));
                    i++;
                }
            } finally {
                if (resUTM != null && !resUTM.isClosed()) {
                    resUTM.close();
                }
            }

        }
        adapterNoteCategory = new SpinnerSetAdapter(MEditNotelistA.this,android.R.layout.simple_spinner_item,CategoryTypeMasters);
        inputSpinnerCategory.setAdapter(adapterNoteCategory);

        inputSpinnerCategory.setSelection(selected_category_type);
    }


    @Override
    protected void initializeListeners() {
        printLogs(LogTag,"initializeInputs","init");


        printLogs(LogTag,"initializeListeners","init");
        inputSpinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,int position, long id) {
                selected_category_type = CategoryTypeMasters[position].getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {selected_category_type=0;}
        });

        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });
    }

    public void validateForm() {
        boolean cancel = false;

        if(!isSpinnerValid(selected_category_type)){
            cancel = true;
            String ErrorTitle ="Error :"+ActivityId+"-105";
            String ErrorMessage =getLabelFromDb("error_226_105",R.string.error_226_105);
            spinnerError(ErrorTitle,ErrorMessage);
        }else if(!validateNote(inputNote,inputLayoutNote)){
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


    public void FormSubmit(){
        final String note = inputNote.getText().toString().trim();
        final String duration = "";
        final String category = String.valueOf(selected_category_type);

        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.MEDIT_PRIVATE_REPORT;
        printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put(KEY_USER_ID,n_id);
            jsonBody.put(KEY_NOTE, note);
            jsonBody.put(KEY_DURATION, duration);
            jsonBody.put(KEY_TYPE ,category);
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
                    if(Status.equals("1")){
                        showProgress(false,mContentView,mProgressView);
                        String sTitle=getLabelFromDb("m_226_title",R.string.m_226_title);
                        String sMessage=getLabelFromDb("m_226_message",R.string.m_226_message);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showSuccessDialogMEditNote(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose,thisClass);


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
                    String sTitle="Error :"+ActivityId+"-102";
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
                        String sTitle="Error :"+ActivityId+"-103";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
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
        RequestQueue requestQueue = Volley.newRequestQueue(MEditNotelistA.this);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }



    public boolean validateNote(EditText inputNote,TextInputLayout inputLayoutNote) {
        printLogs(LogTag,"onCreate","validate department");
        String feedback = inputNote.getText().toString().trim();
        setCustomError(inputLayoutNote,null,inputNote);
        if (feedback.isEmpty() ) {
            String sMessage = getLabelFromDb("error_226_104",R.string.error_226_104);
            setCustomError(inputLayoutNote,sMessage,inputNote);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutNote,inputNote);
            return true;
        }
    }

    protected class ErrorTextWatcher implements TextWatcher {

        private EditText EditView;
        private TextInputLayout EditLayout;

        private ErrorTextWatcher(EditText EditView,TextInputLayout EditLayout) {
            printLogs(LogTag,"onCreate","ErrorTextWatcher");
            this.EditView = EditView;
            this.EditLayout = EditLayout;
        }
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void afterTextChanged(Editable editable) {
            switch (EditView.getId()) {
                case R.id.inputDepartment:
                    validateNote(EditView,EditLayout);
                    break;


            }
        }
    }

    public void  customRedirector(){

            Intent intent = new Intent(MEditNotelistA.this, MNoteList.class);
            Bundle activeUri = new Bundle();
            activeUri.putString("generator", "226");
            activeUri.putString("description", description);
            activeUri.putString("student_name", student_name);
            activeUri.putString("grant_id", grant_id);
            activeUri.putString("user_id", user_id);
            intent.putExtras(activeUri);
            startActivity(intent);
            finish();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        printLogs(LogTag,"onOptionsItemSelected","init");
        if(generator.equals("222")){
            printLogs(LogTag,"onOptionsItemSelected","MNoteList");
            Intent intent = new Intent(MEditNotelistA.this,MNoteList.class);
            Bundle activeUri = new Bundle();
            activeUri.putString("generator","226");
            activeUri.putString("user_id",n_id);
            activeUri.putString("student_name", student_name);
            activeUri.putString("description", description);
            activeUri.putString("grant_id", grant_id);
            activeUri.putString("user_id", user_id);
            intent.putExtras(activeUri);
            startActivity(intent);
            finish();


        } else {
            Intent intent = new Intent(MEditNotelistA.this, MNoteList.class);
            Bundle activeUri = new Bundle();
            activeUri.putString("generator", "226");
            activeUri.putString("description", description);
            activeUri.putString("student_name", student_name);
            activeUri.putString("grant_id", grant_id);
            activeUri.putString("user_id", user_id);
            intent.putExtras(activeUri);
            startActivity(intent);
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

