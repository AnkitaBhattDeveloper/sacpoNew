package za.co.sacpo.grant.xCubeStudent.feedback;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.ListarClientes;
import za.co.sacpo.grant.xCubeLib.adapter.SpinnerAdapter;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsStudentSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeStudent.SDashboardDA;
import za.co.sacpo.grant.xCubeStudent.messages.SChatA;

import static za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPrivate.isValidReason;
import static za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPublic.isValidDepartment;

public class SAddFeedbackA extends BaseAPCPrivate {

    private String ActivityId = "S175";
    private String KEY_DEPARTMENT = "s_w_r_title";
    private String KEY_TRAINING = "about_my_training";
    private String KEY_FEEDBACK = "feedback";
    private String KEY_EXPERIENC = "experience";
    private String KEY_MONTH_YEAR = "month_year";

    public View mProgressView, mContentView,heading;
    public TextInputLayout inputLayoutDepartment;
    public TextInputLayout inputLayoutTraining, inputLayoutFeedback, inputLayoutExperience;
    public EditText inputDepartment, inputTraining, inputFeedback, inputExperience;
    public Button btnSubmitLv,btn_attach;
    public LinearLayout LLFormContainer, LLInformationContainer, LLInputContainer;
    String generator, date_input, month_year, grant_id;
    private Spinner SpinnerMonthYear,startdateSP,startSpinnerMonth,endSpinnerMonth,endSpinnerYear,
            endSpinnerDay,startSpinnerYear;
    Spinner inputSpinnerLeaveType;
    private TextView lblView;
    final ArrayList<ListarClientes> datalist = new ArrayList<>();
    Button btn_guide;
    SAddFeedbackA thisClass;
    private String selected_month_id,student_id,spin_startday,spin_endday;
    private static final int PICK_IMAGE_REQUEST = 99;
    private  int PICK_PDF_REQUEST=2;
    private Bitmap bitmap;
    public Dialog myDialog;
    ImageView btnAttachement;
    private String uploaded_file;
    private String pdf;
    String mimeType;
    private File myFile;
    private String extension;


    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cTAId) {
        baseApcContext = cnt;
        CAId = cTAId;
        activityIn = ain;
        LogTag = lt;
        thisClass = this;
        printLogs(lt, "setBaseApcContextParent", "weAreIn");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(), this, this.getClass().getSimpleName(), ActivityId);
        printLogs(LogTag, "onCreate", "init");
        Bundle activeInputUri = getIntent().getExtras();
        generator = activeInputUri.getString("generator");
        date_input = activeInputUri.getString("date_input");
        month_year = activeInputUri.getString("month_year");
        grant_id = activeInputUri.getString("grant_id");
        student_id = activeInputUri.getString("student_id");
        printLogs(LogTag, "onCreate", "GENERATOR ID " + generator);
        printLogs(LogTag, "onCreate", "student_id__" + student_id);

        bootStrapInit();
    }

    @Override
    protected void bootStrapInit() {
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext, activityIn);
        if (isConnected) {
            printLogs(LogTag, "bootStrapInit", "initConnected");
            setLayoutXml();
            callFooter(baseApcContext, activityIn, ActivityId);
            initMenusCustom(ActivityId, baseApcContext, activityIn);
            fetchVersionData();
            verifyVersion();
            internetChangeBroadCast();
            initializeViews();
            initBackBtn();
            showProgress(true, mContentView, mProgressView);
            initializeLabels();
            initializeListeners();
            fetchMonthYear();
            userToken = userSessionObj.getToken();
            syncToken(baseApcContext, activityIn);
            if (TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
            myDialog = new Dialog(SAddFeedbackA.this);
            callDataApi();
            initializeInputs();
            printLogs(LogTag, "bootStrapInit", "exitConnected");
            showProgress(false, mContentView, mProgressView);
        }
    }

    @Override
    protected void internetChangeBroadCast() {
        printLogs(LogTag, "internetChangeBroadCast", "init");
        registerBroadcastIC();
    }

    @Override
    protected void fetchVersionData() {
        printLogs(LogTag, "fetchVersionData", "exitConnected");
        syncUpdates(baseApcContext, activityIn);
    }

    @Override
    protected void verifyVersion() {
        printLogs(LogTag, "verifyVersion", "init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag, "verifyVersion", "isUpdate " + isUpdate);
        printLogs(LogTag, "verifyVersion", "isUpdate " + utilSessionObj.getIsUpdateRequired());
        if (isUpdate) {
            Intent intent = new Intent(SAddFeedbackA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void setLayoutXml() {
        printLogs(LogTag, "setLayoutXml", "a_add_feedback");
        setContentView(R.layout.a_add_feedback);
    }

    @Override
    protected void initializeViews() {
        printLogs(LogTag, "initializeViews", "init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        heading = findViewById(R.id.heading);
        SpinnerMonthYear = (Spinner) findViewById(R.id.inputSpinnerMonthYear);
        inputDepartment = (EditText) findViewById(R.id.inputDepartment);
        inputTraining = (EditText) findViewById(R.id.inputTraining);
        inputFeedback = (EditText) findViewById(R.id.inputFeedback);
        inputExperience = (EditText) findViewById(R.id.inputExperience);
        inputLayoutDepartment = (TextInputLayout) findViewById(R.id.inputLayoutDepartment);
        inputLayoutTraining = (TextInputLayout) findViewById(R.id.inputLayoutTraining);
        inputLayoutFeedback = (TextInputLayout) findViewById(R.id.inputLayoutFeedback);
        inputLayoutExperience = (TextInputLayout) findViewById(R.id.inputLayoutExperience);
        btnSubmitLv = (Button) findViewById(R.id.btn_submitLeave);
        startdateSP = findViewById(R.id.startdateSP);
        endSpinnerDay = findViewById(R.id.endSpinnerDay);
        btn_guide = findViewById(R.id.btn_guide);
        btn_attach = findViewById(R.id.btn_attach);
        printLogs(LogTag, "initializeViews", "exit");
    }


    @Override
    protected void initializeLabels() {
        printLogs(LogTag, "initializeLabels", "init");
        String Label = getLabelFromDb("l_S175_week_ending", R.string.l_S175_week_ending);
        lblView = (TextView) findViewById(R.id.lblWeekEnding);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("l_S175_department", R.string.l_S175_department);
        lblView = (TextView) findViewById(R.id.lblDepartment);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("l_S175_training", R.string.l_S175_training);
        lblView = (TextView) findViewById(R.id.lblTraining);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("l_S175_feedback", R.string.l_S175_feedback);
        lblView = (TextView) findViewById(R.id.lblFeedback);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("h_reportsdate", R.string.h_reportsdate);
        lblView = (TextView) findViewById(R.id.lblstartdate);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("h_reportenddate", R.string.h_reportenddate);
        lblView = (TextView) findViewById(R.id.lblenddate);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("l_S175_experence", R.string.l_S175_experence);
        lblView = (TextView) findViewById(R.id.lblExperience);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("h_S175", R.string.h_S175);
        lblView = (TextView) findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);


        Label = getLabelFromDb("b_apply_feedback", R.string.b_apply_feedback);
        btnSubmitLv.setText(Label);
        Label = getLabelFromDb("l_S175_guidebtn", R.string.l_S175_guidebtn);
        btn_guide.setText(Label);
        Label = getLabelFromDb("l_S175_attachbtn", R.string.l_S175_attachbtn);
        btn_attach.setText(Label);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            btnSubmitLv.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            btn_guide.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            btn_attach.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
           inputDepartment.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
           inputTraining.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
           inputFeedback.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
           inputExperience.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            SpinnerMonthYear.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            startdateSP.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));
            endSpinnerDay.setBackground(getDrawable(getDrwabaleResourceId("spinner_bg")));

        }

    }

    @Override
    protected void initializeInputs() {
        printLogs(LogTag, "initializeInputs", "init");
        studentSessionObj = new OlumsStudentSession(baseApcContext);
        SpinnerAdapter adapter = new SpinnerAdapter(SAddFeedbackA.this, android.R.layout.simple_spinner_dropdown_item, datalist);
        SpinnerMonthYear.setAdapter(adapter);
    }

    @Override
    protected void initializeListeners() {
        printLogs(LogTag, "initializeListeners", "init");


        SpinnerMonthYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                final String selected_Item = SpinnerMonthYear.getSelectedItem().toString();
//            final int selected_value = SpinnerMonthYear.getSelectedItemPosition();

                ListarClientes myModel = (ListarClientes) parent.getSelectedItem();

                selected_month_id = myModel.getId();
                printLogs(LogTag, "setOnItemSelectedListener", "MONTH_YEAR_IDDDDDDD" + selected_month_id);


            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // int selected_month_id = 0;
            }
        });


        btnSubmitLv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm(selected_month_id);
                printLogs(LogTag, "btnSubmitLv", "MONTH_YEAR_btnSubmitLv_IDDD : " + selected_month_id);
            }
        });
        btn_attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

        /*start date spinners*/


        ArrayList<String> days = new ArrayList<>();
        for (int i = 0; i <= 31; i++) {
            if(i == 0){
                days.add("");
            }else{
                days.add(Integer.toString(i));
            }

        }

        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, days);
        Spinner startdateSP = findViewById(R.id.startdateSP);
        startdateSP.setAdapter(adapter4);

        startdateSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spin_startday = parent.getItemAtPosition(position).toString();
                printLogs(LogTag, "initializeListeners", "spin_startday" + spin_startday);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        /*end date spinners*/

        ArrayAdapter<String> adapterendSpinnerDay = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, days);
        Spinner endSpinnerDay = findViewById(R.id.endSpinnerDay);
        endSpinnerDay.setAdapter(adapterendSpinnerDay);

        endSpinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spin_endday = parent.getItemAtPosition(position).toString();
                printLogs(LogTag, "initializeListeners", "spin_endday" + spin_endday);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }
    private void showFileChooser() {
        final CharSequence[] items = { "Choose from Gallery","Upload PDF", "Cancel"};
        final PackageManager pm = getApplicationContext().getPackageManager();
        final AlertDialog.Builder builder = new AlertDialog.Builder(SAddFeedbackA.this);
       // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Choose from Gallery")) {
                    dialogInterface.dismiss();
                    isGrantExternalRW(SAddFeedbackA.this);
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickPhoto.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    startActivityForResult(pickPhoto, PICK_IMAGE_REQUEST);}
                else if (items[i].equals("Upload PDF")) {
                    dialogInterface.dismiss();
                    isGrantExternalRW(SAddFeedbackA.this);
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
    private static boolean isGrantExternalRW(SAddFeedbackA SAddFeedbackA) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && (SAddFeedbackA.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

            ((Activity)SAddFeedbackA).requestPermissions(new String[]{
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
    public void callDataApi() {
        printLogs(LogTag, "callDataApi", "init");
        userToken = userSessionObj.getToken();
        if (!TextUtils.isEmpty(userToken)) {
            printLogs(LogTag, "callDataApi", "Token Update");
            syncUserData(baseApcContext, activityIn);
            syncStudentData(baseApcContext, activityIn);
            syncGrantData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn, ActivityId);
        }
    }

    private void fetchMonthYear() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_175_1;
        FINAL_URL = FINAL_URL + "?token=" + token;//+"/seta_id/"+selected_Bank;
        printLogs(LogTag, "fetchData", "URL : " + FINAL_URL);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchData", "response : " + response);
                    String Status = jsonObject.getString(KEY_STATUS);
                    if (Status.equals("1")) {

                        JSONArray dataM = jsonObject.getJSONArray(KEY_DATA);
                        for (int i = 0; i < dataM.length(); i++) {
                            JSONObject rec = dataM.getJSONObject(i);
                          /*  String seta_name = rec.getString("grant_name");
                            SetaName.add(seta_name);
                              */
                            ListarClientes GetDatadp = new ListarClientes();
                            /* String seta_name = rec.getString("grant_name");
                             SetaName.add(seta_name);*/
                            GetDatadp.setName(rec.getString("month_year"));
                            GetDatadp.setId(rec.getString("id"));
                            datalist.add(GetDatadp);
                            showProgress(false, mContentView, mProgressView);
                        }
                        //  inputSpinnerGrant.setAdapter(new ArrayAdapter<String>(GAProcessStipendClaimA.this, android.R.layout.simple_spinner_dropdown_item, SetaName));
                        SpinnerAdapter adapter = new SpinnerAdapter(SAddFeedbackA.this, android.R.layout.simple_spinner_dropdown_item, datalist);
                        SpinnerMonthYear.setAdapter(adapter);
                    } else if (Status.equals("2")) {
                        showProgress(false, mContentView, mProgressView);
                    } else {
                        //showProgress(false,mContentView,mProgressView);
                        printLogs(LogTag, "fetchData", "error_try_again : DATA_ERROR");
                        String sTitle = "Error :" + ActivityId + "-110";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    printLogs(LogTag, "fetchData", "error_196_108 : " + e.getMessage());
                    e.printStackTrace();
                    //showProgress(false,mContentView,mProgressView);
                    String sTitle = "Error :" + ActivityId + "-111";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                printLogs(LogTag, "fetchData", "error_try_again: " + error.getMessage());
                String sTitle = "Error :" + ActivityId + "-112";
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

    private void initBackBtn() {
        printLogs(LogTag, "initBackBtn", "init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void validateForm(final String selected_month_id) {
        printLogs(LogTag, "validateForm", "MONTH_YEAR_validateForm_IDDD : " + selected_month_id);
        boolean cancel = false;
        printLogs(LogTag, "validateForm", "init");
        if (!validateDepartment(inputDepartment, inputLayoutDepartment)) {
            cancel = true;
        } else if (!validateLearning(inputExperience, inputLayoutExperience)) {
            cancel = true;
        } else if (!validateTraining(inputTraining, inputLayoutTraining)) {
            cancel = true;
        } else if (!validateFeedback(inputFeedback, inputLayoutFeedback)) {
            cancel = true;
        }

        if (cancel) {
            return;
        } else {
            showProgress(true, mContentView, mProgressView);
            this.FormSubmit(selected_month_id);
        }
        printLogs(LogTag, "validateForm", "exit");
    }

    public void FormSubmit(String selected_month_id) {
        printLogs(LogTag, "FormSubmit", "MONTH_YEAR_FORMSUBMIT_IDDD : " + selected_month_id);
        final String department = inputDepartment.getText().toString().trim();
        final String training = inputTraining.getText().toString().trim();
        final String feedback = inputFeedback.getText().toString().trim();
        final String experience = inputExperience.getText().toString().trim();

        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_175_2;
        FINAL_URL = FINAL_URL+"?token="+token  ;
        printLogs(LogTag, "FormSubmit", "URL : " + FINAL_URL);
        JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("token",token);
                jsonBody.put(KEY_DEPARTMENT, department);
                jsonBody.put(KEY_TRAINING, training);
                jsonBody.put(KEY_MONTH_YEAR, selected_month_id);
                jsonBody.put(KEY_FEEDBACK, feedback);
                jsonBody.put(KEY_EXPERIENC, experience);
                jsonBody.put("r_start_date", spin_startday);
                jsonBody.put("r_end_date", spin_endday);
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
                        printLogs(LogTag, "FormSubmit", "SUCCESS : " + Status);
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = getLabelFromDb("m_S175_title", R.string.m_S175_title);
                        String sMessage = getLabelFromDb("m_S175_message", R.string.m_S175_message);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showSuccessDialogSAddFeedback(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose, thisClass);
                    } else {
                        printLogs(LogTag, "FormSubmit", "else");
                        showProgress(false, mContentView, mProgressView);
                        String sMessage = getLabelFromDb("error_S175_100", R.string.error_S175_100);
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-100";
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "-101";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                printLogs(LogTag, "onCreate", "error listener "+error);
                showProgress(false, mContentView, mProgressView);
                String sTitle = "Error :" + ActivityId + "-102";
                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
            }
        }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put("Content-Type", "application/json; charset=utf-8");
                header.put("Accept","*/*");
                return header;
            }};
            RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);


    }


    public boolean validateDepartment(EditText inputDepartment, TextInputLayout inputLayoutDepartment) {
        printLogs(LogTag, "onCreate", "validate department");
        String reasons = inputDepartment.getText().toString().trim();
        setCustomError(inputLayoutDepartment, null, inputDepartment);
        if (reasons.isEmpty() || isValidDepartment(reasons)) {
            String sMessage = getLabelFromDb("error_184_106", R.string.error_184_106);
            setCustomError(inputLayoutDepartment, sMessage, inputDepartment);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutDepartment, inputDepartment);
            return true;
        }
    }

    public boolean validateTraining(EditText inputTraining, TextInputLayout inputLayoutTraining) {
        printLogs(LogTag, "onCreate", "validate department");
        String reasons = inputTraining.getText().toString().trim();
        setCustomError(inputLayoutTraining, null, inputTraining);
        if (reasons.isEmpty()) {
            setCustomErrorDisabled(inputLayoutTraining, inputTraining);
            return true;
        } else if (isValidReason(reasons)) {
            String sMessage = getLabelFromDb("error_184_107", R.string.error_184_107);
            setCustomError(inputLayoutTraining, sMessage, inputTraining);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutTraining, inputTraining);
            return true;
        }
    }

    public boolean validateFeedback(EditText inputFeedback, TextInputLayout inputLayoutFeedback) {
        printLogs(LogTag, "onCreate", "validate department");
        String feedback = inputFeedback.getText().toString().trim();
        setCustomError(inputLayoutFeedback, null, inputFeedback);
        if (feedback.isEmpty()) {
            setCustomErrorDisabled(inputLayoutFeedback, inputFeedback);
            return true;
        } else if (isValidReason(feedback)) {
            String sMessage = getLabelFromDb("error_184_107", R.string.error_184_107);
            setCustomError(inputLayoutFeedback, sMessage, inputFeedback);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutFeedback, inputFeedback);
            return true;
        }
    }

    public boolean validateLearning(EditText inputExperience, TextInputLayout inputLayoutExperience) {
        printLogs(LogTag, "onCreate", "validate department");
        String feedback = inputExperience.getText().toString().trim();
        Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");
        setCustomError(inputLayoutExperience, null, inputExperience);
        if (feedback.isEmpty() || isValidReason(feedback)) {
            String sMessage = getLabelFromDb("error_184_109", R.string.error_184_109);
            setCustomError(inputLayoutExperience, sMessage, inputExperience);
            return false;
        }/*else if (regex.matcher(feedback).find()) {
            String sMessage = getLabelFromDb("error_184_110",R.string.error_184_110);
            setCustomError(inputLayoutExperience,sMessage,inputExperience);
            return false;
        }*/ else {
            setCustomErrorDisabled(inputLayoutExperience, inputExperience);
            return true;
        }
    }

    public void Popup(Bitmap bitmap){

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
                Toast.makeText(thisClass, "uploading pic...", Toast.LENGTH_SHORT).show();
               // uploadpic(editText,myDialog);
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
                Toast.makeText(thisClass, "uploading pdf...", Toast.LENGTH_SHORT).show();

             //  uploadpdf(editText,pdf,extension,myDialog);
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
                InputStream inputStream= SAddFeedbackA.this.getContentResolver().openInputStream(filePath);
                byte[] pdfbyte= new byte[inputStream.available()];
                inputStream.read(pdfbyte);
                pdf=     Base64.encodeToString(pdfbyte,Base64.DEFAULT);
                mimeType = getContentResolver().getType(filePath);
                extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
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
    public void customRedirector() {


            Intent intent = new Intent(SAddFeedbackA.this, SDashboardDA.class);
            printLogs(LogTag, "onOptionsItemSelected", "SDashboardDA");
            startActivity(intent);

    }




    protected class ErrorTextWatcher implements TextWatcher {

        private EditText EditView;
        private TextInputLayout EditLayout;

        private ErrorTextWatcher(EditText EditView, TextInputLayout EditLayout) {
            printLogs(LogTag, "onCreate", "ErrorTextWatcher");
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
                    validateDepartment(EditView, EditLayout);
                    break;
                case R.id.inputTraining:
                    validateTraining(EditView, EditLayout);
                    break;
                case R.id.inputFeedback:
                    validateFeedback(EditView, EditLayout);
                    break;
                case R.id.inputExperience:
                    validateFeedback(EditView, EditLayout);
                    break;

            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            Intent intent = new Intent(SAddFeedbackA.this, SDashboardDA.class);
            printLogs(LogTag, "onOptionsItemSelected", "SDashboardDA");
            startActivity(intent);
        return true;
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SAddFeedbackA.this, SDashboardDA.class);
        printLogs(LogTag, "onOptionsItemSelected", "SDashboardDA");
        startActivity(intent);
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