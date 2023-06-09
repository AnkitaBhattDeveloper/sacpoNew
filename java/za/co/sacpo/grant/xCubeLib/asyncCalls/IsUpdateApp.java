package za.co.sacpo.grant.xCubeLib.asyncCalls;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.session.OlumsUserSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;

public class IsUpdateApp extends AsyncTask<Void, Void, Boolean> {
        private String versionV;
        private static final String TAG = IsUpdateApp.class.getSimpleName();
        private final String KEY_STATUS="s";
        private String KEY_DATA="m";
        private String KEY_APP_UPDATE_STATUS="app_updated";
        private String KEY_APP_DOWN_STATUS="app_down";
        private String KEY_DOWN_MESSAGE="app_down_message";
        private String KEY_APP_DOWN_USER_TYPE_ID="app_down_user_type_id";
        private Boolean dataLogCall =false;
        private Context contextA;
        private Boolean isLive =true;
        private OlumsUtilitySession utilSessionObj;
        private OlumsUserSession userSessionObj;
    public IsUpdateApp(String version, Context context) {
        versionV = version;
        contextA = context;
    }
    public void printLogsAsync(String funcs,String msg){
        String tag = this.getClass().getSimpleName();
        if(isLive==false) {
            Log.i("OSG-"+tag+"__"+funcs,msg);
        }
    }
    @Override
    protected void onPreExecute() { }
    @Override
    protected Boolean doInBackground(Void... params) {
        userSessionObj = new OlumsUserSession(contextA);
        String userSessionType = userSessionObj.getUserType();
        String UPDATES_URL_FINAL = URLHelper.DOMAIN_BASE_URL+URLHelper.UPDATE_URL+"?u_type="+userSessionType+"&version_number="+versionV;
        printLogsAsync("doInBackground","URL "+UPDATES_URL_FINAL);
        //Log.d("doInBackground","URL "+UPDATES_URL_FINAL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(UPDATES_URL_FINAL,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject outputJson = null;
                try {
                    printLogsAsync("doInBackground","RESPONSE "+response);
                    outputJson = new JSONObject(String.valueOf(response));
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")) {
                        JSONArray jsonArray = outputJson.getJSONArray(KEY_DATA);
                        JSONObject object = null;
                        for (int i = 0; i <jsonArray.length() ; i++) {
                        object = jsonArray.getJSONObject(i);
                        }
                        assert object != null;
                        String AppUpdated = object.getString(KEY_APP_UPDATE_STATUS);
                    String AppDown = object.getString(KEY_APP_DOWN_STATUS);
                    String AppDownMessage = object.getString(KEY_DOWN_MESSAGE);
                    String AppDownUserTypeId = object.getString(KEY_APP_DOWN_USER_TYPE_ID);
                    utilSessionObj = new OlumsUtilitySession(contextA);

                    boolean isUserLogin = userSessionObj.getHasSession();
                    String userSessionType = userSessionObj.getUserType();

                    if(AppUpdated.equalsIgnoreCase("1")) {
                        printLogsAsync("doInBackground", "APP UPDATED >> TRUE");
                        utilSessionObj.setIsUpdateRequired(true);
                        utilSessionObj.setIsGooglePlayUpdateRequired(true);
                        utilSessionObj.setIsAppDown(false);
                        utilSessionObj.setDownMessage("");
                        utilSessionObj.setDownUserTypeId("");
                    }
                    else{
                        if(AppDown.equalsIgnoreCase("1")){
                            printLogsAsync("doInBackground", "APP Down >> TRUE");
                            printLogsAsync("doInBackground", "APP Down For "+userSessionType+ "---" + AppDownUserTypeId + " >> TRUE");
                            utilSessionObj.setIsUpdateRequired(true);
                            utilSessionObj.setIsGooglePlayUpdateRequired(false);
                            utilSessionObj.setIsAppDown(true);
                            utilSessionObj.setDownMessage(AppDownMessage);
                            utilSessionObj.setDownUserTypeId(AppDownUserTypeId);

                        }
                        else{
                            printLogsAsync("doInBackground","APP Down >> FALSE");
                            utilSessionObj = new OlumsUtilitySession(contextA);
                            utilSessionObj.setIsUpdateRequired(false);
                            utilSessionObj.setIsGooglePlayUpdateRequired(false);
                            utilSessionObj.setIsAppDown(false);
                            utilSessionObj.setDownMessage("");
                            utilSessionObj.setDownUserTypeId("");
                        }
                    }
                }
                else{
                    printLogsAsync("doInBackground","APP UPDATED >> FALSE");
                    utilSessionObj = new OlumsUtilitySession(contextA);
                    utilSessionObj.setIsUpdateRequired(false);
                    utilSessionObj.setIsGooglePlayUpdateRequired(false);
                    utilSessionObj.setIsAppDown(false);
                    utilSessionObj.setDownMessage("");
                    utilSessionObj.setDownUserTypeId("");
                }
            } catch (JSONException e) {
                printLogsAsync("doInBackground","Catch "+e.getMessage());
                e.printStackTrace();
            }
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dataLogCall = false;
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "*/*");


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(contextA);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
        return dataLogCall;
    }
    @Override
    protected void onPostExecute(final Boolean success) {

    }
    @Override
    protected void onCancelled() {
    }
}
