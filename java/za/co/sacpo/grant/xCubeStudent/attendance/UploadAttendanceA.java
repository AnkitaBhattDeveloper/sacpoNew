package za.co.sacpo.grant.xCubeStudent.attendance;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import androidx.annotation.RequiresApi;

import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
    import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.component.VolleyMultipartRequest;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeStudent.claims.SAttSummaryList;

import static za.co.sacpo.grant.xCubeLib.component.URLHelper.Upload_att;


public class UploadAttendanceA extends BaseAPCPrivate {

    private static final int STORAGE_PERMISSION_CODE = 123;
    public String ActivityId = "243";
    public View mProgressView, mContentView;
    private TextView lblView;
    private Button btnUpload, btnUploadImage, btnBrowsePdf, btnUploadPdf_file;
    private ImageButton imgBtn_removeImage;
    private ImageView get_image01;
    private RelativeLayout rr_showImg;
    private TextView tv_pdfStatus;
    private static final int PICK_IMAGE_REQUEST = 99;
    //Pdf request code
    private static final int PICK_FILE_REQUEST = 1;

    private String upload_URL = "https://demonuts.com/Demonuts/JsonTest/Tennis/uploadfile.php?";

    private Bitmap bitmap;
    private String extension, extension2,extensionFile;
    private String KEY_IMAGE = "uploaded_file";
    private String KEY_FILE = "uploaded_file";
    private String KEY_EXT = "extension";
    String date_input, month_year, grant_id, is_upload_attendance,filename;

    private File myFile;

    private String pdf;


    //Progress bar to check the progress of obtaining pdf
    private ProgressDialog loading;

    UploadAttendanceA thisClass;


    private RequestQueue rQueue;
    private ArrayList<HashMap<String, String>> arraylist;

    String url = "https://www.google.com";

    @Override
    protected void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cAId) {
        baseApcContext = cnt;
        CAId = cAId;
        thisClass = this;
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

        printLogs(LogTag, "onCreate", "BundleValuee" + date_input + " " + month_year + " " + grant_id);

        bootStrapInit();
    }

    @Override
    protected void bootStrapInit() {
        Boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
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


        btnUploadPdf_file = findViewById(R.id.btnUploadPdf_file);
        btnUploadImage = findViewById(R.id.btnUploadImage);
        btnBrowsePdf = findViewById(R.id.btnUploadPdf);
        btnUpload = findViewById(R.id.btnUpload);
        imgBtn_removeImage = findViewById(R.id.imgBtn_removeImage);
        get_image01 = findViewById(R.id.get_image01);
        rr_showImg = findViewById(R.id.rr_showImg);
        tv_pdfStatus = findViewById(R.id.tv_pdfStatus);
    }

    @Override
    protected void initializeListeners() {


        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                chooseFile();
                btnUploadPdf_file.setVisibility(View.GONE);
                tv_pdfStatus.setVisibility(View.GONE);
                btnUpload.setVisibility(View.VISIBLE);
            }
        });

        imgBtn_removeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rr_showImg.setVisibility(View.GONE);
                btnUpload.setVisibility(View.GONE);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                    submitfile();


            }
        });


        btnBrowsePdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openGallary();
                btnUploadPdf_file.setVisibility(View.GONE);//gone for new code..
                btnUpload.setVisibility(View.GONE);

            }
        });

        tv_pdfStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);*/
            }
        });

    }


    private void openGallary() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select PDF"), PICK_FILE_REQUEST);

    }

    private void chooseFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        rr_showImg.setVisibility(View.VISIBLE);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            try {

                if(data!=null) { // user selects some Image

                    Uri filePath = data.getData();
                    if (filePath != null) {

                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                        get_image01.setImageBitmap(bitmap);
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                        String mimeType = getContentResolver().getType(filePath);
                        extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
                        
                    }
                }else{


                    // user simply backpressed from gallery
                    Toast.makeText(thisClass, "Back..", Toast.LENGTH_SHORT).show();
                }


            } catch (IOException e) {
                e.printStackTrace();
            }


        }else{


//other code


            if(data!=null) {

                Uri uri = data.getData();
                if (uri != null) {

                    String uriString = uri.toString();
                    myFile = new File(uriString);
                    String path = myFile.getAbsolutePath();
                    String displayName = null;

                    filename = uri.getLastPathSegment();

                    String mimeType = getContentResolver().getType(uri);
                    extension2 = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);

                    getStringFile(myFile);

                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = this.getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                Log.d("nameeeee>>>>  ", displayName);


                                uploadPDF(displayName, uri);
                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                        Log.d("nameeeee>>>>  ", displayName);
                    }
                }

               else{


                    Toast.makeText(thisClass, "Nothing Selected...", Toast.LENGTH_SHORT).show();
                }


            }
            super.onActivityResult(requestCode, resultCode, data);



        }

        }



    public String getStringFile(File myFile)
    {


        printLogs(LogTag,"getStringFile","getStringFileValue__ :"+myFile);
        StringBuilder sb = new StringBuilder();
        try
        {
            FileInputStream fileInputStream = new FileInputStream(myFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line ;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line).append("\n");
                reader.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return sb.toString();
    }






    //UPLOAD PDF FILE CODE>>>HERE

    private void uploadPDF(final String pdfname, Uri pdffile){
       // showProgress(true,mContentView,mProgressView);

        InputStream iStream = null;
        try {
            iStream = getContentResolver().openInputStream(pdffile);
            final byte[] inputData = getBytes(iStream);

            printLogs(LogTag,"uploadPDF","pdffile : "+pdffile);
            printLogs(LogTag,"uploadPDF","inputData : "+inputData);

            String token = userSessionObj.getToken();
            final String FINAL_URL = URLHelper.DOMAIN_BASE_URL + Upload_att + "/token/"+token + "/grant_id/"+grant_id+"/month_year/"+month_year;
            printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);

            loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);


            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, FINAL_URL,

                    new Response.Listener<NetworkResponse>() {

                        @Override
                        public void onResponse(NetworkResponse response) {
                            Log.d("ressssssoo",new String(response.data));
                            printLogs(LogTag,"uploadPDF","URL : "+FINAL_URL);
                            rQueue.getCache().clear();

                            try {
                                JSONObject jsonObject = new JSONObject(new String(response.data));
                               // Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                                jsonObject.toString().replace("\\\\","");

                                if (jsonObject.getString(KEY_STATUS).equals("1")) {
                                    Log.d("come::: >>>  ","yessssss");
                                    arraylist = new ArrayList<HashMap<String, String>>();

                                    //JSONArray dataArray = jsonObject.getJSONArray(KEY_DATA);
//                                    JSONObject dataM = jsonObject.getJSONObject(KEY_DATA);


                                   /// url = dataM.optString("pathToFile");
                                    //tv_pdfStatus.setVisibility(View.VISIBLE);
                                    //tv_pdfStatus.setText(url);

                                   /* for (int i = 0; i < dataArray.length(); i++) {
                                        JSONObject dataobj = dataArray.getJSONObject(i);
                                        url = dataobj.optString("pathToFile");
                                        tv_pdfStatus.setVisibility(View.VISIBLE);
                                        tv_pdfStatus.setText(url);
                                    }*/

                                    loading.dismiss();
                                    showProgress(false,mContentView,mProgressView);
                                    String sTitle=getLabelFromDb("m_243_title",R.string.m_243_title);
                                    String sMessage =getLabelFromDb("m_243_message",R.string.m_243_message);
                                    String sButtonLabelClose="Close";
                                    ErrorDialog.showSuccessDialogUploadAttendanceA(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose,thisClass);


                                    // JSONObject dataM = outputJson.getJSONObject(KEY_DATA);
                                       /* for (int i = 0; i < jsonObj.length(); i++) {
                                            JSONObject dataobj = jsonObj.getJSONObject(String.valueOf(i));
                                            url = dataobj.optString("pathToFile");
                                            tv_pdfStatus.setText(url);


                                        }*/


                                    //}
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                loading.dismiss();
                                showProgress(false,mContentView,mProgressView);
                                String sTitle="Error :"+ActivityId+"-104";
                                String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                                String sButtonLabelClose="Close";
                                ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            loading.dismiss();
                            showProgress(false,mContentView,mProgressView);
                            String sTitle="Error :"+ActivityId+"-105";
                            String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                            String sButtonLabelClose="Close";
                            ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                            // Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {

                /*
                 * If you want to add more parameters with the image
                 * you can do it here
                 * here we have only one parameter with the image
                 * which is tags
                 * */
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    String doc = getStringFile(myFile);
                /*    params.put(KEY_FILE, filename);
                     params.put(KEY_EXT, extension2);*/
                  //  params.put(KEY_EXT, extension2);
                    // params.put(KEY_FILE, String.valueOf(myFile));
                     //params.put(KEY_EXT, extension2);

                    return params;
                }

                /*
                 *pass files using below method
                 * */
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();

                    String pdfName = System.currentTimeMillis() + ".pdf";
                   // params.put("pdf", new DataPart(pdfName, getFileData()));


                   // params.put("filename", new DataPart(pdfname ,inputData));
                    params.put("filename", new DataPart(pdfname, inputData,extension2));
                    //params.put("extension", new DataPart(inputData,extension2));
                    printLogs(LogTag,"ParamPost","PostData__ : " +extension2+"__more__"+inputData+"__pdfname__"+pdfname);

                    return params;
                }
            };


            volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            rQueue = Volley.newRequestQueue(UploadAttendanceA.this);
            rQueue.add(volleyMultipartRequest);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private byte[] getFileData() {

        int size = (int) pdf.length();
        byte[] bytes = new byte[size];
        byte[] tmpBuff = new byte[size];

        try (FileInputStream inputStream = new FileInputStream(pdf)) {
            int read = inputStream.read(bytes, 0, size);
            if (read < size) {
                int remain = size - read;
                while (remain > 0) {
                    read = inputStream.read(tmpBuff, 0, remain);
                    System.arraycopy(tmpBuff, 0, bytes, size - remain, read);
                    remain -= read;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytes;
    }


    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    @Override
    protected void initializeInputs() {

    }

    @Override
    protected void initializeLabels() {

        String Label = getLabelFromDb("h_243", R.string.h_243);
        lblView = (TextView) findViewById(R.id.activity_heading);
        lblView.setText(Label);

        Label = getLabelFromDb("l_243_btn_upload", R.string.l_243_btn_upload);
        lblView = (Button) findViewById(R.id.btnUpload);
        lblView.setText(Label);

        Label = getLabelFromDb("l_243_btn_uploadPdf", R.string.l_243_btn_uploadPdf);
        lblView = (Button) findViewById(R.id.btnUploadPdf_file);
        lblView.setText(Label);

    }

    private void initBackBtn() {
        printLogs(LogTag, "initBackBtn", "init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void setLayoutXml() {

        setContentView(R.layout.a_upload_attendance);

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
            Intent intent = new Intent(UploadAttendanceA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    private void submitfile() {
        showProgress(true,mContentView,mProgressView);
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + Upload_att + "/token/"+token + "/grant_id/"+grant_id+"/extension/"+extension+"/month_year/"+month_year;
        printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,FINAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject outputJson = null;
                try {
                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        showProgress(false,mContentView,mProgressView);
                        String sTitle=getLabelFromDb("m_243_title",R.string.m_243_title);
                        String sMessage =getLabelFromDb("m_243_message",R.string.m_243_message);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showSuccessDialogUploadAttendanceA(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose,thisClass);
                    }
                    else{
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-101";
                        String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false,mContentView,mProgressView);
                    String sTitle="Error :"+ActivityId+"-102";
                    String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                    String sButtonLabelClose="Close";
                    ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String sTitle="Error :"+ActivityId+"-103";
                        String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                String image = getStringImage(bitmap);
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_IMAGE,image);
                params.put(KEY_EXT,extension);
             //   params.put(date_input,"date_input");
                params.put(month_year,"month_year");
                params.put(grant_id,"grant_id");
                printLogs(LogTag,"Submit","Submit_Valueeeee___" +month_year+" "+grant_id+"  " + extension + "   "+image);
                return params;


            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(UploadAttendanceA.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    public void customRedirector(){
        Bundle inputUri = new Bundle();
        inputUri.putString("date_input", date_input);
        inputUri.putString("month_year", month_year);
        inputUri.putString("grant_id", grant_id);
        inputUri.putString("is_upload_attendance", is_upload_attendance);
        Intent intent = new Intent(UploadAttendanceA.this, SAttSummaryList.class);
        intent.putExtras(inputUri);
        startActivity(intent);
        finish();
        printLogs(LogTag, "customRedirector", "Redirect");
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
            Intent intent = new Intent(UploadAttendanceA.this, SAttSummaryList.class);
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
