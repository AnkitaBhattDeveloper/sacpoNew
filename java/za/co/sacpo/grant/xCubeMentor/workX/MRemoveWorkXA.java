package za.co.sacpo.grant.xCubeMentor.workX;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

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
import za.co.sacpo.grant.xCubeStudent.SDashboardDA;

/*a_remove_works*/
/*a_remove_works*/
public class MRemoveWorkXA extends BaseFormAPCPrivate {


    private String ActivityId="315";
    private String KEY_GEO_LOC="emp_geo_location_id";

    // private String KEY_U_TYPE_ID="user_type_id";
    public EditText inputwXGeo;
    public View mProgressView, mContentView;
    public TextInputLayout inputLayoutwXGeoLoc;
    public Button btnRemoveWorkStation;

    private TextView lblView;
    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId){
        baseApcContext = cnt;CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        printLogs(lt,"setBaseApcContextParent","weAreIn");
    }
    @Override
    protected void initializeInputs(){
        syncUpdates(baseApcContext,activityIn);

    }
    @Override
    protected void initializeLabels(){

    String Label = getLabelFromDb("lbl_315_geo_location",R.string.lbl_315_geo_location);
        lblView = (TextView)findViewById(R.id.lblwXGeoLoc);
        lblView.setText(Label);
         Label = getLabelFromDb("lbl_315_btnRemoveWorkStation",R.string.lbl_315_btnRemoveWorkStation);
        lblView = (TextView)findViewById(R.id.btnRemoveWorkStation);
        btnRemoveWorkStation.setText(Label);

        Label = getLabelFromDb("h_315",R.string.h_315);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);


         Label = getLabelFromDb("lbl_315_btn_remove",R.string.lbl_315_btn_remove);
         lblView = (TextView)findViewById(R.id.btnRemoveWorkStation);
         btnRemoveWorkStation.setText(Label);

    }
    @SuppressLint("NewApi")
    @Override
    protected void initializeViews() {
        inputwXGeo = (EditText) findViewById(R.id.inputwXGeo);
        inputLayoutwXGeoLoc = (TextInputLayout) findViewById(R.id.inputLayoutwXGeoLoc);


/*

        inputwXGeo.addTextChangedListener(new MRemoveWorkXA.ErrorTextWatcher(inputwXGeo,inputLayoutwXGeoLoc));
*/

        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        btnRemoveWorkStation = (Button) findViewById(R.id.btnRemoveWorkStation);

        printLogs(LogTag,"initializeLabels","exit");
    }
    @Override
    protected void initializeListeners() {

        btnRemoveWorkStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });



    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutXml();
        initMenusCustom(ActivityId,baseApcContext,activityIn);
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);

    }

    @Override
    protected void bootStrapInit() {
        Boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext, activityIn);
        if (isConnected == true) {

            syncToken(baseApcContext, activityIn);
            fetchVersionData();
            verifyVersion();
            internetChangeBroadCast();
            initializeViews();
            showProgress(true,mContentView,mProgressView);
            initializeListeners();
            initializeLabels();
            initializeInputs();
            showProgress(false,mContentView,mProgressView);
        }
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
            Intent intent = new Intent(MRemoveWorkXA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void internetChangeBroadCast(){
        registerBroadcastIC();
    }
    @Override
    protected void setLayoutXml() {
        setContentView(R.layout.a_remove_works);
        printLogs(LogTag,"setLayoutXml","add department");
    }

    public void validateForm() {
        boolean cancel = false;
        if (!validateLatitude(inputwXGeo,inputLayoutwXGeoLoc)) {
        }
        if (cancel) {
            return;
        } else {
            showProgress(true,mContentView,mProgressView);
            this.FormSubmit();
        }
        printLogs(LogTag,"validateForm","exit");
    }

    public void FormSubmit() {
        final String latitude= inputwXGeo.getText().toString();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.M_REMOVE_WORKSTATION;
        String token = userSessionObj.getToken();
        printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put(KEY_GEO_LOC, latitude);
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
                    if(Status.equals("1")){
                        JSONObject dataM = outputJson.getJSONObject(KEY_DATA);

                        userSessionObj.setUserId(dataM.getInt("latitude"));

                        String token = FirebaseInstanceId.getInstance().getToken();
                        userSessionObj.setHasSession(true);
                        showProgress(false,mContentView,mProgressView);
                        Intent intent = new Intent(MRemoveWorkXA.this,SDashboardDA.class);
                        startActivity(intent);
                        finish();

                    }

                    else{
                        showProgress(false,mContentView,mProgressView);
                        String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                       /* mPasswordView.setError(sMessage);
                        mPasswordView.requestFocus();*/
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false,mContentView,mProgressView);
                    String sTitle="Error :"+ActivityId+"-103";
                    String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                    String sButtonLabelClose="Close";
                    ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                }
            }
        },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String sTitle="Error :"+ActivityId+"-104";
                        String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                        //Toast.makeText(getApplicationContext(),"ERROR LOGIN IN. PLEASE TRY AGAIN.", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(MRemoveWorkXA.this);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    public boolean validateLatitude(EditText inputDeptAddLatitude,TextInputLayout inputLayoutDeptAddLatitude) {
        String lat = inputDeptAddLatitude.getText().toString().trim();
        setCustomError(inputLayoutDeptAddLatitude,null,inputDeptAddLatitude);
        if (lat.isEmpty()) {
            String sMessage = getLabelFromDb("error_315_106",R.string.error_315_106);
            setCustomError(inputLayoutDeptAddLatitude,sMessage,inputDeptAddLatitude);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutDeptAddLatitude,inputDeptAddLatitude);
            return true;
        }
    }

    protected class ErrorTextWatcher implements TextWatcher {
        private EditText EditView;
        private TextInputLayout EditLayout;
        private ErrorTextWatcher(EditText EditView,TextInputLayout EditLayout) {
            this.EditView = EditView;
            this.EditLayout = EditLayout;
        }
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        public void afterTextChanged(Editable editable) {
            switch (EditView.getId()) {

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