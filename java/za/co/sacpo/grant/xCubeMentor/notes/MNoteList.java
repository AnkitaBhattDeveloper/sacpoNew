package za.co.sacpo.grant.xCubeMentor.notes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.adapter.MNoteListAdapter;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dataObj.MNoteObj;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeMentor.MDashboardDA;


/*a_m_not_list*/
public class MNoteList extends BaseAPCPrivate {
    private String ActivityId = "222";
    public View mProgressView, mContentView, mProgressRView, mContentRView,heading;
    Bundle activeUri;

    private TextView lblView;
    private RecyclerView recyclerViewQ;
    public MNoteObj rDataObj = new MNoteObj();
    private List<MNoteObj.Item> rDataObjList = null;
    String user_id,generator,grant_id,student_name;
    Button btnAddNote;

    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cTAId) {
        baseApcContext = cnt;CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        printLogs(lt,"setBaseApcContextParent","weAreIn");
    }

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(), this, this.getClass().getSimpleName(),ActivityId);
        printLogs(LogTag,"onCreate","init");
        Bundle activeInputUri = getIntent().getExtras();
        user_id = activeInputUri.getString("user_id");
        grant_id = activeInputUri.getString("grant_id");
        generator = activeInputUri.getString("generator");
        student_name = activeInputUri.getString("student_name");
        printLogs(LogTag,"onCreate","USER ID "+user_id);
        printLogs(LogTag,"onCreate","GENERATOR ID "+generator);

        bootStrapInit();

    }

    @Override
    protected void bootStrapInit() {
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext, activityIn);
        if (isConnected) {
            setLayoutXml();
             callFooter(baseApcContext,activityIn,ActivityId);
            initMenusCustom(ActivityId,baseApcContext,activityIn);
            registerBroadcastIC();
            fetchVersionData();
            verifyVersion();
            initializeViews();
            initBackBtn();
            showProgress(true, mContentView, mProgressView);
            initializeLabels();
            initializeListeners();
            userToken = userSessionObj.getToken();
            syncToken(baseApcContext, activityIn);
            if (TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
            callDataApi();
            initializeInputs();
            printLogs(LogTag,"bootStrapInit","exitConnected");
            fetchData();
            showProgress(false, mContentView, mProgressView);
        }
    }
    @Override
    protected void internetChangeBroadCast() {
        registerBroadcastIC();
    }



    protected void fetchVersionData(){
        printLogs(LogTag,"fetchVersionData","exitConnected");
        syncUpdates(baseApcContext,activityIn);
    }



    protected void verifyVersion(){
        printLogs(LogTag,"verifyVersion","init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag,"verifyVersion","isUpdate "+isUpdate);
        printLogs(LogTag,"verifyVersion","isUpdate "+ utilSessionObj.getIsUpdateRequired());
        if(isUpdate){
            Intent intent = new Intent(MNoteList.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    @Override

    protected void initializeInputs() {
        lblView = (TextView) findViewById(R.id.lblNote_list);
        lblView.setText(student_name.toUpperCase());
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));

    }

    @Override
    protected void initializeLabels() {


        String Label = getLabelFromDb("l_222_add_note", R.string.l_222_add_note);
        lblView = (TextView) findViewById(R.id.btnAddNote);
        lblView.setText(Label);

        Label = getLabelFromDb("h_222",R.string.h_222);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            btnAddNote.setBackground(getDrawable(getDrwabaleResourceId("themed_small_button")));
            btnAddNote.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));

        }

    }

    @Override
    protected void initializeViews() {
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mContentRView = findViewById(R.id.content_container_r);
        mProgressRView = findViewById(R.id.progress_bar_r);
        heading = findViewById(R.id.heading);
        btnAddNote = findViewById(R.id.btnAddNote);
        rDataObjList = rDataObj.getITEMS();
        recyclerViewQ = (RecyclerView) findViewById(R.id.rVNoteList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);
        setupRecyclerView(recyclerViewQ);

    }

    @Override
    protected void initializeListeners() {


        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MNoteList.this,MAddNote.class);
                activeUri = new Bundle();
                activeUri.putString("generator","222");
                activeUri.putString("user_id",user_id);
                activeUri.putString("grant_id",grant_id);
                activeUri.putString("student_name",student_name);
                intent.putExtras(activeUri);
                startActivity(intent);
                finish();
            }
        });
    }
    public void validateInput() {
    }


    public void callDataApi() {
        userToken = userSessionObj.getToken();
        if (!TextUtils.isEmpty(userToken)) {
            syncUserData(baseApcContext, activityIn);
            syncStudentData(baseApcContext, activityIn);
            syncMentorData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn,ActivityId);
        }
    }

    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","a_m_ticket_details");
        setContentView(R.layout.a_m_not_list);
    }
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new MNoteListAdapter(rDataObjList, baseApcContext, activityIn,this));
    }

    public void showList() {
        List<MNoteObj.Item> WorkstationData = rDataObj.getITEMS();
        MNoteListAdapter adapter = new MNoteListAdapter(WorkstationData, baseApcContext, activityIn,this);
        recyclerViewQ.setAdapter(adapter);
        showProgress(false, mContentRView, mProgressRView);
    }

    public void fetchData() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.M_NOTE_LIST;
        FINAL_URL = FINAL_URL + "?token=" + token + "&user_id=" + user_id + "&grant_id=" + grant_id;
        printLogs(LogTag,"getNoteList","URL : "+FINAL_URL);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
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
                            int pos = i + 1;
                            JSONObject rec = dataM.getJSONObject(i);
                            int aId2 = Integer.parseInt(rec.getString("n_id"));
                            String description3 = rec.getString("n_description");
                            String add_date4 = rec.getString("n_add_date");
                            String addby5 = rec.getString("n_add_by");
                            String note_for6 = rec.getString("note_for");
                            String cell_no7 = rec.getString("n_id");
                            String note_from8= rec.getString("note_from");
                            String duration9= rec.getString("n_duration");
                            String note_10= rec.getString("n_category_name");
                            String grant_id11= rec.getString("grant_id");
                            String user_id12= rec.getString("n_user_id");
                            rDataObj.addItem(rDataObj.createItem(pos, aId2, description3,add_date4
                            ,addby5,note_for6,cell_no7,note_from8,duration9,note_10,grant_id11,user_id12));
                            showList();
                        }

                    showProgress(false,mContentView,mProgressView);
                }
                    else if(Status.equals("2")) {
                    showProgress(false,mContentView,mProgressView);
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
                    e.printStackTrace();
                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "-102";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
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
    public String getStudentName(){
        return student_name;
    }
    public String getStudentId(){
        return user_id;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(MNoteList.this,MDashboardDA.class);
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
