package za.co.sacpo.grant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.component.ClickActionHelper;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.db.helpArrayAdapter;
import za.co.sacpo.grant.xCubeLib.db.helpArrays;
import za.co.sacpo.grant.xCubeLib.db.masterArrayAdapter;
import za.co.sacpo.grant.xCubeLib.db.masterArrays;
import za.co.sacpo.grant.xCubeLib.db.staticLabels;
import za.co.sacpo.grant.xCubeLib.db.staticLabelsAdapter;

public class SplashA extends Activity {
    private final String ActivityId="A100";
    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9001;
     public String KEY_STATUS = "s";
    public String KEY_DATA = "m";
    public Boolean errorFlag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkPlayServices();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_splash);
        ImageView img = findViewById(R.id.imgLoading);
        img.setBackgroundResource(R.drawable.loader);
        ImageView iv_logo = findViewById(R.id.iv_logo);
        Field resourceField;
        int resourceId=0;
        try {
            resourceField = R.drawable.class.getDeclaredField("app_logo"+"_"+URLHelper.PORTAL_ID);
            //Here we are getting the String id in R file...But you can change to R.drawable or any other resource you want...
            resourceId = resourceField.getInt(resourceField);
            //Here you can use it as usual
        } catch (Exception e) {
            e.printStackTrace();
        }
        iv_logo.setImageResource(resourceId);
        AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
        frameAnimation.start();
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null) {
            getAllStaticLabels();
           getMasterArray();
           getHelpArray();
        }
        else{
            String sTitle="Error :"+ActivityId+"-101";
            String sMessage=sTitle+" >>> "+getApplicationContext().getString(R.string.error_A100_101);
            Toast.makeText(getApplicationContext(),sMessage, Toast.LENGTH_LONG).show();
        }

    }
    public void getAllStaticLabels(){
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.LABELS_URL;
        //Log.i("getAllStaticLabels","FINAL_URL "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, response -> {
            try {
                JSONObject jsonObject= new JSONObject(String.valueOf(response));
                //Log.i("getAllStaticLabels","RESPONSE "+response);
                String Status = jsonObject.getString(KEY_STATUS);
                if(Status.equals("1")){
                    staticLabelsAdapter slAd = new staticLabelsAdapter(getApplicationContext());
                    slAd.truncate();
                    JSONArray dataM = jsonObject.getJSONArray(KEY_DATA);
                    for (int i = 0; i < dataM.length(); i++) {
                        JSONObject rec = dataM.getJSONObject(i);
                        String Name = rec.getString("name");
                        String Value = rec.getString("value");
                        staticLabels slObj = new staticLabels(i,Name,Value);

                        slAd.insert(slObj);
                    }
                    setErrorFlag(false);
                }
                else{
                    String sTitle="Error :"+ActivityId+"-102";
                    String sMessage=sTitle+" >>> "+getApplicationContext().getString(R.string.error_try_again);
                    Toast.makeText(getApplicationContext(),sMessage, Toast.LENGTH_LONG).show();
                    setErrorFlag(true);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                String sTitle="Error :"+ActivityId+"-103";
                String sMessage=sTitle+" >>> "+getApplicationContext().getString(R.string.error_try_again);
                Toast.makeText(getApplicationContext(),sMessage, Toast.LENGTH_LONG).show();
                setErrorFlag(true);
                 }
        },
                error -> {
                    String sTitle="Error :"+ActivityId+"-104";
                    String sMessage=sTitle+" >>> "+getApplicationContext().getString(R.string.error_try_again);
                    Toast.makeText(getApplicationContext(),sMessage, Toast.LENGTH_LONG).show();
                    setErrorFlag(true);
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
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
    public void getMasterArray(){
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.GET_MASTER_ARRAY_URL;
        //Log.i("getMasterArray"," : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, response -> {
            try {
                JSONObject outputJson= new JSONObject(String.valueOf(response));
                //Log.i("getAllStaticLabels","RESPONSE "+response);
                String Status = outputJson.getString(KEY_STATUS);
                //Log.i("getMasterArray","response "+response);
                if(Status.equals("1")){
                    masterArrayAdapter cAd = new masterArrayAdapter(getApplicationContext());
                    cAd.truncate();
                    JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                    for (int i = 0; i < dataM.length(); i++) {
                        JSONObject rec = dataM.getJSONObject(i);
                        int Id = Integer.parseInt(rec.getString("m_id"));
                        String Name = rec.getString("m_name");
                        int Type = rec.getInt("m_type");
                        masterArrays cObj = new masterArrays(i,Id,Type,Name);

                        cAd.insert(cObj);
                    }
                    setErrorFlag(false);
                }
                else{
                    String sTitle="Error :"+ActivityId+"-105";
                    String sMessage=sTitle+" >>> "+getApplicationContext().getString(R.string.error_try_again);
                    Toast.makeText(getApplicationContext(),sMessage, Toast.LENGTH_LONG).show();
                      setErrorFlag(true);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                String sTitle="Error :"+ActivityId+"-106";
                String sMessage=sTitle+" >>> "+getApplicationContext().getString(R.string.error_try_again);
                Toast.makeText(getApplicationContext(),sMessage, Toast.LENGTH_LONG).show();
                setErrorFlag(true);
                  }
        },
                error -> {
                    String sTitle="Error :"+ActivityId+"-107";
                    String sMessage=sTitle+" >>> "+getApplicationContext().getString(R.string.error_try_again);
                    Toast.makeText(getApplicationContext(),sMessage, Toast.LENGTH_LONG).show();
                    setErrorFlag(true);
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
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
    public void getHelpArray(){
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.GET_HELP_ARRAY_URL;
        //Log.i("getHelpArray"," : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, response -> {
            try {
                JSONObject outputJson= new JSONObject(String.valueOf(response));
                //Log.i("getAllStaticLabels","RESPONSE "+response);
                String Status = outputJson.getString(KEY_STATUS);
              //  Log.i("getHelpArray","response "+response);
                if(Status.equals("1")){
                    helpArrayAdapter cAd = new helpArrayAdapter(getApplicationContext());
                    cAd.truncate();
                    JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                    for (int i = 0; i < dataM.length(); i++) {
                        JSONObject rec = dataM.getJSONObject(i);
                        int Id = Integer.parseInt(rec.getString("h_id"));
                        String Title = rec.getString("h_title");
                        String Content = rec.getString("h_content");
                        String Activity = rec.getString("h_activity_id");
                        int Type = Integer.parseInt(rec.getString("h_type"));
                        helpArrays cObj = new helpArrays(i,Id,Type,Title,Content,Activity);

                        cAd.insert(cObj);
                    }
                    setErrorFlag(false);
                }
                else{
                    String sTitle="Error :"+ActivityId+"-105";
                    String sMessage=sTitle+" >>> "+getApplicationContext().getString(R.string.error_try_again);
                    Toast.makeText(getApplicationContext(),sMessage, Toast.LENGTH_LONG).show();
                    setErrorFlag(true);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                String sTitle="Error :"+ActivityId+"-106";
                String sMessage=sTitle+" >>> "+getApplicationContext().getString(R.string.error_try_again);
                Toast.makeText(getApplicationContext(),sMessage, Toast.LENGTH_LONG).show();
                setErrorFlag(true);
              }
        },
                error -> {
                    String sTitle="Error :"+ActivityId+"-107";
                    String sMessage=sTitle+" >>> "+getApplicationContext().getString(R.string.error_try_again);
                    Toast.makeText(getApplicationContext(),sMessage, Toast.LENGTH_LONG).show();
                    setErrorFlag(true);
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
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
    public void setErrorFlag(Boolean isError){
        this.errorFlag = isError;
        if(isError) {
            finishAffinity();
            System.exit(0);
        }else{
            Intent intent = new Intent(SplashA.this, LoginA.class);
            startActivity(intent);
            finish();
        }
    }

    protected void onPause(){
        super.onPause();
        finish();
    }
    protected void onResume(){
        super.onResume();
        checkPlayServices();
    }
    private void checkPlayServices() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int code = api.isGooglePlayServicesAvailable(this);
        if (code != ConnectionResult.SUCCESS) {
            if(api.isUserResolvableError(code)) {
                api.getErrorDialog(this, code,PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            else {
                finish();
            }
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.hasExtra("click_action")) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ClickActionHelper.startActivity(intent.getStringExtra("click_action"), intent.getExtras(),
                    getApplicationContext());
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }
    }
}