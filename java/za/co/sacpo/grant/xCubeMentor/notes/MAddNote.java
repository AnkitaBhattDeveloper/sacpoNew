package za.co.sacpo.grant.xCubeMentor.notes;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
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

/*activity_madd_not*/
public class MAddNote extends BaseFormAPCPrivate {

    private String ActivityId="553";
    private String KEY_NOTE="note";
    private String KEY_USER_ID="user_id";
    private String KEY_DURATION="duration";
    private String KEY_TYPE="catagory";
    private MAddNote thisClass;
    String user_id;
    public EditText inputFirstName,inputLastName,inputMobile,inputNote;
    public View mProgressView, mContentView;

    public Button btnAddNote;
    TextInputLayout inputLayoutNote;
    private TextView lblView,txtStudentName;
    String student_name,generator,grant_id;
    int selected_category_type=0;
    Spinner inputSpinnerCategory;
    private SpinnerSetAdapter adapterNoteCategory;
    private static SpinnerSet[] CategoryTypeMasters;

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
        printLogs(LogTag,"onCreate","init");
        printLogs(LogTag,"onCreate","init");
        Bundle activeInputUri = getIntent().getExtras();
        user_id = activeInputUri.getString("user_id");
        generator = activeInputUri.getString("generator");
        student_name = activeInputUri.getString("student_name");
        grant_id = activeInputUri.getString("grant_id");

        printLogs(LogTag,"onCreate","USER ID "+user_id);
        printLogs(LogTag,"onCreate","GENERATOR ID "+generator);

        bootStrapInit();
        printLogs(LogTag,"onCreate","initConnected");
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
            Intent intent = new Intent(MAddNote.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void setLayoutXml(){
        printLogs(LogTag,"setLayoutXml","activity_madd_not");
        setContentView(R.layout.activity_madd_not);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        inputMobile = (EditText) findViewById(R.id.inputMobile);


        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        btnAddNote = (Button) findViewById(R.id.btnAddNote);
        inputNote = (EditText) findViewById(R.id.inputNote);
        inputLayoutNote = (TextInputLayout) findViewById(R.id.inputLayoutNote);
        txtStudentName= (TextView)findViewById(R.id.txtStudentName);
        inputSpinnerCategory = (Spinner) findViewById(R.id.inputSpinnerCategory);
    }
    @Override
    protected void initializeLabels(){

        printLogs(LogTag,"initializeLabels","init");
        String Label = getLabelFromDb("l_add_not",R.string.l_add_not);
        lblView = (TextView)findViewById(R.id.lblNote);
        lblView.setText(Label);



        Label = getLabelFromDb("b_553_add_note",R.string.b_553_add_note);
        btnAddNote.setText(Label);


        Label = getLabelFromDb("h_553",R.string.h_553);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);


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
        adapterNoteCategory = new SpinnerSetAdapter(MAddNote.this,android.R.layout.simple_spinner_item,CategoryTypeMasters);
        inputSpinnerCategory.setAdapter(adapterNoteCategory);


    }


    @Override
    protected void initializeListeners() {
        printLogs(LogTag,"initializeInputs","init");


        printLogs(LogTag,"initializeListeners","init");
        inputSpinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,int position, long id) {
                int item =position;
                selected_category_type = CategoryTypeMasters[item].getId();
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
            String ErrorMessage =getLabelFromDb("error_553_105",R.string.error_553_105);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        printLogs(LogTag,"onOptionsItemSelected","init");
        if(generator.equals("222")){
            printLogs(LogTag,"onOptionsItemSelected","MNoteList");
            Intent intent = new Intent(MAddNote.this,MNoteList.class);
            Bundle activeUri = new Bundle();
            activeUri.putString("generator","149");
            activeUri.putString("user_id",user_id);
            activeUri.putString("grant_id",grant_id);
            activeUri.putString("student_name", student_name);
            intent.putExtras(activeUri);
            startActivity(intent);
            finish();


        }
        return true;
    }
    public void FormSubmit(){
        final String note = inputNote.getText().toString().trim();
        final String duration = "";
        final String category = String.valueOf(selected_category_type);

        String token = userSessionObj.getToken();

        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.MADDNOTE;
        printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put("grant_id", grant_id);
            jsonBody.put(KEY_USER_ID,user_id);
            jsonBody.put(KEY_NOTE, note);
            jsonBody.put(KEY_DURATION, duration);
            jsonBody.put(KEY_TYPE, category);
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
                        String sTitle=getLabelFromDb("m_553_title",R.string.m_553_title);
                        String sMessage=getLabelFromDb("m_553_message",R.string.m_553_message);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showSuccessDialogAddNote(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose,thisClass);

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
        requestQueue.add(jsonObjectRequest);
    }
    public void customRedirector(){
        Intent intent = new Intent(MAddNote.this,MNoteList.class);
        Bundle activeUri = new Bundle();
        activeUri.putString("generator","149");
        activeUri.putString("user_id",user_id);
        activeUri.putString("grant_id",grant_id);
        activeUri.putString("student_name", student_name);
        intent.putExtras(activeUri);
        startActivity(intent);
        finish();
    }
    public boolean validateNote(EditText inputNote,TextInputLayout inputLayoutNote) {
        printLogs(LogTag,"onCreate","validate department");
        String feedback = inputNote.getText().toString().trim();
        setCustomError(inputLayoutNote,null,inputNote);
        if (feedback.isEmpty() ) {
            String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
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

