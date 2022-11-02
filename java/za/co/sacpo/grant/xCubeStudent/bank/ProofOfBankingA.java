package za.co.sacpo.grant.xCubeStudent.bank;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

//import net.gotev.uploadservice.MultipartUploadRequest;
//import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
import za.co.sacpo.grant.xCubeStudent.SDashboardDA;

import static za.co.sacpo.grant.xCubeLib.component.URLHelper.PROOF_OF_BANKING;

public class ProofOfBankingA extends BaseAPCPrivate {

    private String ActivityId = "259";
    public View mProgressView, mContentView;
    public TextView lblView;
    private String bank_id,filename,extension;
    //Pdf request code
    private static final int PICK_FILE_REQUEST = 1;

    private ProgressDialog loading;
    ImageView ivAttachment;
    TextView tvFileName;

    private RequestQueue rQueue;
    private ArrayList<HashMap<String, String>> arraylist;

    String url = "https://www.google.com";
    private File myFile;
    //new code


    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;
    //Uri to store the image uri
    private Button buttonUpload;
    private TextView txtInitialName,txtSurname,txtAccNumb,txtBankName,txtAccountType,txtBranchCode;


    String Account_type,BranchCode;
    int bank_idInt,Account_typeInt,BranchCodeInt;

   //private Button btnChooseFile,btnUploadBankProof;

    ProofOfBankingA thisClass;

    @Override
    protected void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cAId) {

        baseApcContext = cnt;
        CAId = cAId;
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
        bank_id = activeInputUri.getString("bank_id");
        printLogs(LogTag, "onCreate", "bank_id" + bank_id);
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
            initBackBtn();
            initializeViews();
            showProgress(true, mContentView, mProgressView);
            initializeLabels();
            initializeListeners();
            userToken = userSessionObj.getToken();
            syncToken(baseApcContext, activityIn);
            if (TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
            callDataApi();
            fetchData();
            initializeInputs();
            showProgress(false, mContentView, mProgressView);
        }
    }

    @Override
    protected void initializeViews() {
        printLogs(LogTag, "initializeViews", "init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);

        /*ivAttachment = (ImageView) findViewById(R.id.ivAttachment);

        tvFileName = (TextView) findViewById(R.id.tv_file_name);*/

        buttonUpload = findViewById(R.id.buttonUpload);

        //txtInitialName,txtSurname,txtAccNumb,txtBankName,txtAccountType,txtBranchCode

        txtInitialName = (TextView) findViewById(R.id.txtInitialName);
        txtSurname = (TextView) findViewById(R.id.txtSurname);
        txtAccNumb = (TextView) findViewById(R.id.txtAccNumb);
        txtBankName = (TextView) findViewById(R.id.txtBankName);
        txtAccountType = (TextView) findViewById(R.id.txtAccountType);
        txtBranchCode = (TextView) findViewById(R.id.txtBranchCode);

    }

    @Override
    protected void initializeListeners() {

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                openGallary();
            }
        });


    }

    private void openGallary() {

        Intent intent = new Intent();
      //  intent.setType("application/pdf");
         intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select PDF"), PICK_FILE_REQUEST);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        


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
                    extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
                    

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
            final String FINAL_URL = URLHelper.DOMAIN_BASE_URL + PROOF_OF_BANKING + "/token/"+token + "/bank_id/"+bank_id;
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
                                    String sTitle=getLabelFromDb("m_259_title",R.string.m_259_title);
                                    String sMessage =getLabelFromDb("m_259_message",R.string.m_259_message);
                                    String sButtonLabelClose="Close";
                                    ErrorDialog.showSuccessDialogProofOfBankingA(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose,thisClass);


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
                    params.put("filename", new DataPart(pdfname, inputData,extension));
                    //params.put("extension", new DataPart(inputData,extension2));
                    printLogs(LogTag,"ParamPost","PostData__ : " +extension+"__more__"+inputData+"__pdfname__"+pdfname);

                    return params;
                }
            };


            volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            rQueue = Volley.newRequestQueue(ProofOfBankingA.this);
            rQueue.add(volleyMultipartRequest);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

        String Label = getLabelFromDb("h_259",R.string.h_259);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);


        Label = getLabelFromDb("lbl_S121_lblBankDetails", R.string.lbl_S121_lblBankDetails);
        lblView = (TextView) findViewById(R.id.lblBankDetails);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S121_lblBankName", R.string.lbl_S121_lblBankName);
        lblView = (TextView) findViewById(R.id.lblBankName);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S121_lblSurname", R.string.lbl_S121_lblSurname);
        lblView = (TextView) findViewById(R.id.lblSurname);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S121_lblAccountType", R.string.lbl_S121_lblAccount_type);
        lblView = (TextView) findViewById(R.id.lblAccountType);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S121_lblAccountNumber", R.string.lbl_S121_lblAccountNumber);
        lblView = (TextView) findViewById(R.id.lblAccountNumber);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S121_lblBranchCode", R.string.lbl_S121_lblBranchCode);
        lblView = (TextView) findViewById(R.id.lblBranchCode);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_S121_lblInitial_name", R.string.lbl_S121_lblInitial_name);
        lblView = (TextView) findViewById(R.id.lblInitialName);
        lblView.setText(Label);



    }

    @Override
    protected void setLayoutXml() {
        setContentView(R.layout.a_proof_of_banking);
    }

    public void callDataApi(){
        printLogs(LogTag, "callDataApi", "init");
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
            Intent intent = new Intent(ProofOfBankingA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }


    private void fetchData(){
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_121+ "?token=" + token;
        printLogs(LogTag, "fetchData", "URL : " + FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject outputJson = null;
                try {
                    printLogs(LogTag, "getGrantDetails", "RESPONSE : " + response);
                    outputJson = new JSONObject(String.valueOf(response));
                    String Status = outputJson.getString(KEY_STATUS);
                    if (Status.equals("1")) {
                        JSONObject dataM = outputJson.getJSONObject(KEY_DATA);


                        Account_type = dataM.getString("b_d_a_type");
                        Account_typeInt = Integer.parseInt(Account_type);

                        BranchCode = dataM.getString("b_d_u_branch_id");
                        BranchCodeInt = Integer.parseInt(BranchCode);



                        bank_id = dataM.getString("id");
                        bank_idInt = Integer.parseInt(bank_id);


                        if (dataM.length() > 0) {
                            txtBankName.setText(dataM.getString("bank_name"));
                            txtInitialName.setText(dataM.getString("initial_name"));
                            txtSurname.setText(dataM.getString("b_d_surname"));
                            txtAccountType.setText(dataM.getString("account_type"));
                            txtAccNumb.setText(dataM.getString("account_no"));
                            txtBranchCode.setText(dataM.getString("branch_code"));
                        }
                        showProgress(false, mContentView, mProgressView);
                    } else if (Status.equals("2")) {
                        showProgress(false, mContentView, mProgressView);
                    } else {
                        //showProgress(false,mContentView,mProgressView);
                        printLogs(LogTag, "fetchData", "error_try_again : DATA_ERROR");
                        String sTitle = "Error :" + ActivityId + "-100";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    printLogs(LogTag, "fetchData", "error_S121_101 : " + e.getMessage());
                    e.printStackTrace();
                    //showProgress(false,mContentView,mProgressView);
                    String sTitle = "Error :" + ActivityId + "-102";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                printLogs(LogTag, "fetchData", "error_try_again : " + error.getMessage());
                String sTitle = "Error :" + ActivityId + "-103";
                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);

            }
        })  {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "*/*");
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjectRequest);

    }


    public void customRedirector(){
        Bundle inputUri = new Bundle();
        printLogs(LogTag,"onOptionsItemSelected","customRedirector_call");
        inputUri.putString("bank_id", bank_id);
        Intent intent = new Intent(ProofOfBankingA.this, SDashboardDA.class);

        intent.putExtras(inputUri);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Bundle inputUri = new Bundle();
            inputUri.putString("bank_id", bank_id);
            Intent intent = new Intent(ProofOfBankingA.this, SDashboardDA.class);
            intent.putExtras(inputUri);
            startActivity(intent);
            finish();
            printLogs(LogTag,"onOptionsItemSelected","ProofOfBankingA");
        }else {
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
