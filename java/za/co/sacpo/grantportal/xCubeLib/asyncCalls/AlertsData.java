package za.co.sacpo.grantportal.xCubeLib.asyncCalls;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUserSession;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AlertsData extends AsyncTask<Void, Void, Boolean> {
    private int user_id;
    private String user_token;
    private static final String TAG = AlertsData.class.getSimpleName();
    private final String KEY_STATUS="s";
    private String KEY_DATA="m";
    private Boolean dataLogCall =false;
    private OlumsUserSession userSessionObj;
    private OlumsUtilitySession utilSessionObj;
    private Context contextA;
    private boolean isLive=true;
    public AlertsData(String token, Context context) {
        user_token = token;
        contextA = context;
    }
    public void printLogsAsync(String funcs,String msg){
        String tag = this.getClass().getSimpleName();
        if(isLive==false) {
            //Log.i("OSG-"+tag+"__"+funcs,msg);
        }
    }
    @Override
    protected void onPreExecute() { }
    @Override
    protected Boolean doInBackground(Void... params) {
        utilSessionObj = new OlumsUtilitySession(contextA);
        userSessionObj = new OlumsUserSession(contextA);
        String userToken;
        userToken = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL+URLHelper.ALERT_URL+"?token="+userToken;
        printLogsAsync("doInBackground","URL "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject= new JSONObject(String.valueOf(response));
                    printLogsAsync("doInBackground","response "+response);
                    String Status = jsonObject.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONObject dataM = jsonObject.getJSONObject(KEY_DATA);
                        if(dataM.length()>0) {
                            utilSessionObj.setChatCount(dataM.getString("c"));
                            utilSessionObj.setNotificationCount(dataM.getString("n"));
                            utilSessionObj.setPollCount(dataM.getString("p"));
                            utilSessionObj.setSignInCount(dataM.getString("s"));
                            utilSessionObj.setSignOutCount(dataM.getString("so"));
                            utilSessionObj.setAttApprovalCount(dataM.getString("at"));
                            utilSessionObj.setClaimApprovalCount(dataM.getString("cl"));

                            /*utilSessionObj.setChatCount("");
                            utilSessionObj.setNotificationCount("");
                            utilSessionObj.setPollCount("");
                            utilSessionObj.setSignInCount("");
                            utilSessionObj.setSignOutCount("");
                            utilSessionObj.setAttApprovalCount("");
                            utilSessionObj.setClaimApprovalCount("");*/
                        }
                    }
                    else{
                        utilSessionObj.setChatCount("0");
                        utilSessionObj.setNotificationCount("0");
                        utilSessionObj.setPollCount("0");
                        utilSessionObj.setSignInCount("0");
                        utilSessionObj.setSignOutCount("0");
                        utilSessionObj.setAttApprovalCount("0");
                        utilSessionObj.setClaimApprovalCount("0");

                        /*utilSessionObj.setChatCount("");
                        utilSessionObj.setNotificationCount("");
                        utilSessionObj.setPollCount("");
                        utilSessionObj.setSignInCount("");
                        utilSessionObj.setSignOutCount("");
                        utilSessionObj.setAttApprovalCount("");
                        utilSessionObj.setClaimApprovalCount("");*/
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    utilSessionObj.setChatCount("0");
                    utilSessionObj.setNotificationCount("0");
                    utilSessionObj.setPollCount("0");
                    utilSessionObj.setSignInCount("0");
                    utilSessionObj.setSignOutCount("0");
                    utilSessionObj.setAttApprovalCount("0");
                    utilSessionObj.setClaimApprovalCount("0");

                    /*utilSessionObj.setChatCount("");
                    utilSessionObj.setNotificationCount("");
                    utilSessionObj.setPollCount("");
                    utilSessionObj.setSignInCount("");
                    utilSessionObj.setSignOutCount("");
                    utilSessionObj.setAttApprovalCount("");
                    utilSessionObj.setClaimApprovalCount("");*/
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