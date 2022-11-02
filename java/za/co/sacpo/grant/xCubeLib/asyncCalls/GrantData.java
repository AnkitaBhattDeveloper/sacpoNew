package za.co.sacpo.grant.xCubeLib.asyncCalls;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.session.OlumsGrantSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsStudentSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsUserSession;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GrantData extends AsyncTask<Void, Void, Boolean> {
    private int user_id;
    private String user_token;
    private static final String TAG = GrantData.class.getSimpleName();
    private final String KEY_STATUS="s";
    private String KEY_DATA="m";
    private Boolean dataLogCall =false;
    private OlumsStudentSession studentSessionObj;
    private OlumsUserSession userSessionObj;
    private OlumsGrantSession grantSessionObj;
    private Context contextA;
    private boolean isLive=true;
    public GrantData(String token, Context context) {
        user_token = token;
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
        studentSessionObj = new OlumsStudentSession(contextA);
        grantSessionObj = new OlumsGrantSession(contextA);
        userSessionObj = new OlumsUserSession(contextA);
        String userToken;
        userToken = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL+URLHelper.S_REF_136+"?token="+userToken;
        printLogsAsync("doInBackground","URL "+FINAL_URL);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject= new JSONObject(String.valueOf(response));
                    Log.i("doInBackground","RESPONSE "+response);
                    String Status = jsonObject.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONObject dataM = jsonObject.getJSONObject(KEY_DATA);
                        if(dataM.length()>0) {
                            String grantId = dataM.getString("grant_id");
                            //String grantId = dataM.getString("g_d_id");
                            int grantIdInt = Integer.parseInt(grantId);
                            //Log.i(TAG,"grant"+grantId);
                            if (grantIdInt > 0) {
                                studentSessionObj.setGrantId(grantId);
                                studentSessionObj.setIsActiveGrant(true);
                                grantSessionObj.setGrantId(grantId);
                                grantSessionObj.setGrantSDate(dataM.getString("s_s_g_grant_start_date"));
                                grantSessionObj.setGrantEDate(dataM.getString("s_s_g_grant_end_date"));
                                grantSessionObj.setGrantName(dataM.getString("grant_name"));
                                grantSessionObj.setGrantSetaName(dataM.getString("seta_name"));
                                grantSessionObj.setGrantHostEmpName(dataM.getString("host_emp_name"));
                                grantSessionObj.setGrantHostEmpSDL(dataM.getString("host_emp_sdl"));
                                grantSessionObj.setGrantMentorName(dataM.getString("mentor_name"));
                                grantSessionObj.setGrantMentorCell(dataM.getString("mentor_cell"));
                                grantSessionObj.setGrantMentorEmail(dataM.getString("mentor_email"));
                                grantSessionObj.setGrantMentorPic(dataM.getString("mentor_image"));
                                grantSessionObj.setGrantMentorId(dataM.getString("mentor_id"));
                                grantSessionObj.setLEMManageName(dataM.getString("lem_manager_name"));
                                grantSessionObj.setLEMManagerId(dataM.getString("lem_manager_id"));
                                grantSessionObj.setGrantLEMName(dataM.getString("lem_name"));
                                grantSessionObj.setGrantLEMId(dataM.getString("lem_id"));
                                grantSessionObj.setSETAManagerName(dataM.getString("seta_manager_name"));
                                grantSessionObj.setSETAManagerId(dataM.getString("seta_manager_id"));
                                grantSessionObj.setGrantMonthlyStipend(dataM.getString("grant_monthly_budget"));
                                grantSessionObj.setGrantDailyDeduction(dataM.getString("grant_daily_deduction"));

                            } else {
                                studentSessionObj.setGrantId(null);
                                studentSessionObj.setIsActiveGrant(false);
                                grantSessionObj.clearGrantSession();
                            }
                        }
                    }
                    else{
                        grantSessionObj.clearGrantSession();
                        studentSessionObj.setIsActiveGrant(false);
                        studentSessionObj.setGrantId(null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    grantSessionObj.clearGrantSession();
                    studentSessionObj.setIsActiveGrant(false);
                    studentSessionObj.setGrantId(null);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dataLogCall = false;
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "*/*");
                // params.put("Content-Type","application/json");

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