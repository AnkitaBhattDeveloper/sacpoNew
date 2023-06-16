package za.co.sacpo.grantportal.xCubeStudent.attendance;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

import za.co.sacpo.grantportal.AppUpdatedA;
import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.component.Utils;
import za.co.sacpo.grantportal.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grantportal.xCubeStudent.claims.SAttSummaryList;

import static android.view.View.GONE;

public class DownloadAttA extends BaseAPCPrivate  {

    private final String ActivityId = "244";

    public View mProgressView, mContentView;
    private TextView lblView;
    private Button  btnDownloadImg, btnDownloadPdf;
    String showBtn;
    ImageView imageview;
    String date_input,month_year,grant_id,is_upload_attendance;
    private LinearLayout ll_tv_info;
  String Path;



    @Override
    protected void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cAId) {
        baseApcContext = cnt;
        CAId = cAId;
        activityIn = ain;
        LogTag = lt;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(), this, this.getClass().getSimpleName(), ActivityId);
        printLogs(LogTag, "onCreate", "init");
        Bundle activeInputUri = getIntent().getExtras();

        date_input = activeInputUri.getString("date_input");
        month_year = activeInputUri.getString("month_year");
        grant_id = activeInputUri.getString("grant_id");
        is_upload_attendance = activeInputUri.getString("is_upload_attendance");
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
            userToken = userSessionObj.getToken();
            syncToken(baseApcContext, activityIn);
            if (TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
            fetchData();
            callDataApi();
            initializeInputs();
            printLogs(LogTag, "bootStrapInit", "exitConnected");
            showProgress(false, mContentView, mProgressView);
        }

    }




    @Override
    protected void initializeViews() {
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        btnDownloadImg = findViewById(R.id.btnDownloadImg);
        btnDownloadPdf = findViewById(R.id.btnDownloadPdf);
        imageview = findViewById(R.id.imageview);
        ll_tv_info = findViewById(R.id.ll_tv_info);

    }

    @Override
    protected void initializeListeners() {

        btnDownloadPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageview.setVisibility(GONE);
                ll_tv_info.setVisibility(GONE);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse("http://docs.google.com/viewer?url=" + Path), "text/html");
                startActivity(intent);
            }
        });


        btnDownloadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                imageview.setVisibility(View.VISIBLE);

            DownloadImage downloadImage = new DownloadImage();
            downloadImage.execute(Path);

            }
        });



    }


    private class DownloadImage extends AsyncTask<String, Integer, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute(){
            progressDialog = new ProgressDialog(DownloadAttA.this);
            progressDialog.setTitle("Download in progress...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMax(100);
            progressDialog.setProgress(0);
            progressDialog.show();
        }


        @Override
        protected String doInBackground(String... params) {

            String path = params[0];
            int file_length = 0;


            try {
                URL url  = new URL(path);
                URLConnection urlConnection = url.openConnection();
                urlConnection.connect();
                file_length = urlConnection.getContentLength();
                File new_folder = new File("sdcard/attendanceData");
                if(!new_folder.exists()){

                    new_folder.mkdir();
                }

                File input_file = new File(new_folder,"download_attendance.jpg");
                InputStream inputStream = new BufferedInputStream(url.openStream(),8192);
                byte[] data = new byte[1024];
                int total = 0;
                int count = 0;

                OutputStream outputStream = new FileOutputStream(input_file );
                while((count = inputStream.read(data))!=-1){

                    total+= count;
                    outputStream.write(data,0,count);
                    int progress = total *100/file_length;
                    publishProgress(progress);


                }

                inputStream.close();
                outputStream.close();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "DOWNLOAD COMPLETE...";
        }




        @Override
        protected void onProgressUpdate(Integer... values) {
            progressDialog.setProgress(values[0]);
            super.onProgressUpdate(values);
        }


        @Override
        protected void onPostExecute(String result  ) {
            progressDialog.hide();
            ll_tv_info.setVisibility(View.VISIBLE);
            Toast.makeText(baseApcContext, ""+result, Toast.LENGTH_SHORT).show();
            String path = "sdcard/attendanceData/download_attendance.jpg";
            imageview.setImageDrawable(Drawable.createFromPath(path));
        }


    }



        @Override
    protected void initializeInputs() {

    }

    @Override
    protected void initializeLabels() {

        String Label = getLabelFromDb("h_244", R.string.h_244);
        lblView = findViewById(R.id.activity_heading);
        lblView.setText(Label);

        Label = getLabelFromDb("l_244_btn_pdf", R.string.l_244_btn_pdf);
        lblView = findViewById(R.id.btnDownloadPdf);
        lblView.setText(Label);


        Label = getLabelFromDb("l_244_btn_img", R.string.l_244_btn_img);
        lblView = findViewById(R.id.btnDownloadImg);
        lblView.setText(Label);

        Label = getLabelFromDb("l_244_txt_info_download", R.string.l_244_txt_info_download);
        lblView = findViewById(R.id.tv_downloadImg_info);
        lblView.setText(Label);
    }

    private void initBackBtn() {
        printLogs(LogTag, "initBackBtn", "init");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }


    @Override
    protected void setLayoutXml() {
        setContentView(R.layout.a_download_att);
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
        if (isUpdate) {
            Intent intent = new Intent(DownloadAttA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }


    private void fetchData() {


        showProgress(true, mContentView, mProgressView);
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.DOWNLOAD_ATT+"/token/"+token+"/grant_id/"+grant_id+"/month_year/"+month_year;
        printLogs(LogTag, "fetchData", "URL : " + FINAL_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, FINAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject outputJson = null;
                printLogs(LogTag, "fetchDataLeave", "RESPONSE : " + response);
                try {

                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if (Status.equals("1")) {
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        for (int i = 0; i < dataM.length(); i++) {
                            JSONObject rec = dataM.getJSONObject(i);

                            String show_pdf = rec.getString("att_img");


                            Path = rec.getString("download_path");

                            showBtn = show_pdf;
                            printLogs(LogTag, "showAtt", "showAtt" + showBtn);

                            if(showBtn.equals("1")){

                                btnDownloadImg.setVisibility(View.VISIBLE);
                                btnDownloadPdf.setVisibility(GONE);


                            }else{

                                btnDownloadPdf.setVisibility(View.VISIBLE);
                                btnDownloadImg.setVisibility(GONE);

                            }



                        }
                        showProgress(false, mContentView, mProgressView);
                    } else if (Status.equals("2")) {
                        showProgress(false, mContentView, mProgressView);
                    }else {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-101";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
                }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(DownloadAttA.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag, "onOptionsItemSelected", "init");
        if (item.getItemId() == android.R.id.home) {
            Bundle inputUri = new Bundle();
            inputUri.putString("date_input", date_input);
            inputUri.putString("month_year", month_year);
            inputUri.putString("grant_id", grant_id);
            inputUri.putString("is_upload_attendance", is_upload_attendance);
            Intent intent = new Intent(DownloadAttA.this, SAttSummaryList.class);
            printLogs(LogTag, "onOptionsItemSelected", "SAttSummaryList");
            intent.putExtras(inputUri);
            startActivity(intent);
            finish();
            //printLogs(LogTag, "onOptionsItemSelected", "default");
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
