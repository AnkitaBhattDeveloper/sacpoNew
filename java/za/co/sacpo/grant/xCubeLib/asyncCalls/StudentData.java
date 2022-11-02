package za.co.sacpo.grant.xCubeLib.asyncCalls;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
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
import za.co.sacpo.grant.xCubeLib.session.OlumsStudentSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsUserSession;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StudentData extends AsyncTask<Void, Void, Boolean> {
    private String user_token;
    private static final String TAG = StudentData.class.getSimpleName();
    private final String KEY_STATUS="s";
    private String KEY_DATA="m";
    private Boolean dataLogCall =false;
    private OlumsStudentSession studentSessionObj;
    private OlumsUserSession userSessionObj;
    private Context contextA;
    private boolean isLive=true;
    public StudentData(String token, Context context) {
        user_token = token;
        contextA = context;
    }
    public void printLogsAsync(String funcs,String msg){
        String tag = this.getClass().getSimpleName();
        if(isLive==false) {
        }
    }
    @Override
    protected void onPreExecute() { }
    @Override
    protected Boolean doInBackground(Void... params) {
        studentSessionObj = new OlumsStudentSession(contextA);
        userSessionObj = new OlumsUserSession(contextA);
        String userToken;
        userToken = userSessionObj.getToken();
        String UPDATES_URL_FINAL = URLHelper.DOMAIN_BASE_URL+URLHelper.STUDENT_DATA_URL+"?token="+userToken;
        printLogsAsync("doInBackground","URL "+UPDATES_URL_FINAL);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, UPDATES_URL_FINAL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject= new JSONObject(String.valueOf(response));
                    Log.i("doInBackground","RESPONSE "+response);
                    String Status = jsonObject.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONObject dataM = jsonObject.getJSONObject(KEY_DATA);
                        studentSessionObj.setStudentSNo(dataM.getString("s_s_no"));
                        studentSessionObj.setStudentIdNo(dataM.getString("s_id_no"));
                        studentSessionObj.setStudentQName(dataM.getString("q_name"));
                        //studentSessionObj.setStudentIName(dataM.getString("u_name"));
                        studentSessionObj.setGrantId(dataM.getString("grant_id"));
                        String sGrantId = dataM.getString("grant_id");
                        if(TextUtils.isEmpty(sGrantId)){
                            studentSessionObj.setIsActiveGrant(false);
                        }
                        else{
                            int grantIntId = Integer.parseInt(dataM.getString("grant_id"));
                            if(grantIntId>0){
                                studentSessionObj.setIsActiveGrant(true);
                            }
                        }
                    }
                    else{
                        studentSessionObj.clearStudentSession();
                        //userSessionObj.clearUserSession();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    studentSessionObj.clearStudentSession();
                    //userSessionObj.clearUserSession();
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
