package za.co.sacpo.grantportal.xCubeStudent.queries;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import za.co.sacpo.grantportal.AppUpdatedA;
import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.component.Utils;
import za.co.sacpo.grantportal.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;

public class SQueriesDetailsA extends BaseAPCPrivate {
    private String ActivityId="150";
    public String KEY_ID = "id";
    public View mProgressView, mContentView,heading;
    private TextView lblView;
    private TextView lblTitle,lblDate,lblData,lblRef,lblStatus;
    private ImageView imQueryImage;
    public LinearLayout lblHeadView,lblBodyView,lblFooterView;
    public CardView lblCardView;
    private Button mCommentsBtn;
    String generator,t_id,group_id;
    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId){
        baseApcContext = cnt;
        CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        printLogs(lt,"setBaseApcContextParent","weAreIn");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);
        printLogs(LogTag,"onCreate","init");
        Bundle activeInputUri = getIntent().getExtras();
        t_id = activeInputUri.getString("t_id");
        generator = activeInputUri.getString("generator");
        group_id = activeInputUri.getString("group_id");
        printLogs(LogTag,"onCreate","T ID "+t_id);
        printLogs(LogTag,"onCreate","GENERATOR ID "+generator);
        bootStrapInit();
    }
    @Override
    protected void bootStrapInit() {
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
            Intent intent = new Intent(SQueriesDetailsA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","a_ticket_details");
        setContentView(R.layout.a_ticket_details);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        heading = findViewById(R.id.heading);

        lblTitle= (TextView) findViewById(R.id.lblTitle);
        lblRef= (TextView) findViewById(R.id.lblRef);
        lblTitle= (TextView) findViewById(R.id.lblTitle);
        lblDate= (TextView) findViewById(R.id.lblDate);
        lblStatus= (TextView) findViewById(R.id.lblStatus);
        lblData= (TextView) findViewById(R.id.lblData);
        lblCardView = (CardView) findViewById(R.id.cardView);
        lblHeadView = (LinearLayout) findViewById(R.id.head_container);
        lblBodyView = (LinearLayout) findViewById(R.id.body_container);
        lblFooterView = (LinearLayout) findViewById(R.id.footer_container);
        mCommentsBtn = (Button) findViewById(R.id.btnComments);
        imQueryImage = (ImageView) findViewById(R.id.imQueryImage);
        printLogs(LogTag,"initializeViews","exit");
    }
    @Override
    protected void initializeLabels(){
        printLogs(LogTag,"initializeLabels","init");
        String Label = getLabelFromDb("h_150",R.string.h_150);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            mCommentsBtn.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            lblRef.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));

        }



    }
    @Override
    protected void initializeInputs(){
        printLogs(LogTag,"initializeInputs","init");
        fetchData();
    }

    @Override
    protected void initializeListeners() {
        printLogs(LogTag,"initializeListeners","init");
        mCommentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(SQueriesDetailsA.this,SQueriesCommentsA.class);
            printLogs(LogTag,"onOptionsItemSelected","SQueriesCommentsA");
            Bundle inputUri = new Bundle();
            inputUri.putString("generator", ActivityId);
            inputUri.putString("generatorParent", generator);
            inputUri.putString("t_id", t_id);
            inputUri.putString("group_id", group_id);
            intent.putExtras(inputUri);
            startActivity(intent);
            finish();
            }
        });
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
    public void fetchData(){
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.TICKET_DETAILS_URL;
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
                    if(Status.equals("1")){
                        JSONObject dataM = outputJson.getJSONObject(KEY_DATA);
                        String TicketId=dataM.getString("i_id");
                        int TicketIdInt=Integer.parseInt(TicketId);
                        if(TicketIdInt>0) {
                            String refString =dataM.getString("i_ref");
                            String imageUrl =dataM.getString("i_issue_image");
                            if(imageUrl.isEmpty()){
                                imQueryImage.setVisibility(View.GONE);
                            }
                            else{
                                Picasso.get().load(imageUrl).resize(250,250).centerInside().memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).networkPolicy(NetworkPolicy.NO_CACHE).placeholder(R.mipmap.user).error(R.mipmap.user).into(imQueryImage);
                                imQueryImage.setVisibility(View.VISIBLE);
                            }

                            lblRef.setText(refString);
                            lblTitle.setText(dataM.getString("i_title"));
                            lblDate.setText(dataM.getString("i_date"));
                            lblData.setText(dataM.getString("i_message"));
                            lblStatus.setText(dataM.getString("i_text_status"));
                            String comments = dataM.getString("i_comment_count");
                            int commentsInt = Integer.parseInt(comments);
                            if(commentsInt>0){
                                mCommentsBtn.setText("Comments("+commentsInt+")");
                            }


                            showProgress(false,mContentView,mProgressView);
                        }
                        else if(Status.equals("2")) {
                            showProgress(false,mContentView,mProgressView);
                        }
                    }
                    else{
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-102";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false,mContentView,mProgressView);
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
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-104";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            if(generator.equals("188")){
                Intent intent = new Intent(SQueriesDetailsA.this,SOutgoingQueriesA.class);
                printLogs(LogTag,"onOptionsItemSelected","SOutgoingQueriesA");
                Bundle inputUri = new Bundle();
                inputUri.putString("generator", "402");
                inputUri.putString("group_id", group_id);
                intent.putExtras(inputUri);
                startActivity(intent);
                finish();
            }
            if(generator.equals("207")){
                Intent intent = new Intent(SQueriesDetailsA.this,SIncomingQueriesA.class);
                printLogs(LogTag,"onOptionsItemSelected","SIncomingQueriesA");
                Bundle inputUri = new Bundle();
                inputUri.putString("generator", "402");
                inputUri.putString("group_id", group_id);
                intent.putExtras(inputUri);
                startActivity(intent);
                finish();
            }
            else {
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