package za.co.sacpo.grantportal.xCubeLib.asyncCalls;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import za.co.sacpo.grantportal.SplashA;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsMentorSession;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsStudentSession;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUserSession;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsVerifierSession;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserData extends AsyncTask<Void, Void, Boolean> {
    private TokenDelete mTokenDeleteTask = null;
    private String user_token;
    private static final String TAG = UserData.class.getSimpleName();
    private final String KEY_STATUS="s";
    private String KEY_DATA="m";
    private Boolean dataLogCall =false;
    private OlumsUserSession userSessionObj;
    private OlumsStudentSession studentSessionObj;
    private OlumsMentorSession mentorSessionObj;
    private OlumsVerifierSession verifierSessionObj;
    private Context contextA;
    public UserData(String token, Context context) {
        user_token = token;
        contextA = context;
    }
    @Override
    protected void onPreExecute() { }
    @Override
    protected Boolean doInBackground(Void... params) {
        userSessionObj = new OlumsUserSession(contextA);
        String userToken;
        userToken = userSessionObj.getToken();
        String UPDATES_URL_FINAL = URLHelper.DOMAIN_BASE_URL+URLHelper.PROFILE_URL+"?token="+userToken;
        //Log.d("UserData","UPDATES_URL_FINAL "+UPDATES_URL_FINAL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, UPDATES_URL_FINAL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject= new JSONObject(String.valueOf(response));
                    String Status = jsonObject.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        //u_id
                        JSONObject dataM = jsonObject.getJSONObject(KEY_DATA);
                        userSessionObj.setUserEmail(dataM.getString("u_email"));
                        userSessionObj.setUserFName(dataM.getString("u_p_fname"));
                        userSessionObj.setUserSName(dataM.getString("u_p_surname"));
                        userSessionObj.setUserThumb(dataM.getString("u_p_image_thumb"));
                        userSessionObj.setUserType(dataM.getString("r_role_id"));
                        userSessionObj.setUserMobile(dataM.getString("u_p_cell_no"));
                        userSessionObj.setUserLastLogin(dataM.getString("u_last_login_time"));
                        userSessionObj.setEmailVerified(dataM.getString("is_email_verified"));
                        userSessionObj.setUserMobileStatus(dataM.getString("u_p_cell_status"));
                        userSessionObj.setUserTypeName(dataM.getString("r_role"));
                    }
                    else{

                        dataLogCall = false;

                        userSessionObj = new OlumsUserSession(contextA);
                        userSessionObj.clearUserSession();
                        userSessionObj.setHasSession(false);
                        studentSessionObj = new OlumsStudentSession(contextA);
                        studentSessionObj.clearStudentSession();
                        mentorSessionObj = new OlumsMentorSession(contextA);
                        mentorSessionObj.clearMentorSession();
                        verifierSessionObj = new OlumsVerifierSession(contextA);
                        verifierSessionObj.clearVerifierSession();
                        mTokenDeleteTask = new TokenDelete(contextA);
                        mTokenDeleteTask.execute((Void) null);
                        Intent intent = new Intent(contextA, SplashA.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        contextA.startActivity(intent);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    dataLogCall = false;
                    userSessionObj = new OlumsUserSession(contextA);
                    userSessionObj.clearUserSession();
                    userSessionObj.setHasSession(false);
                    studentSessionObj = new OlumsStudentSession(contextA);
                    studentSessionObj.clearStudentSession();
                    mentorSessionObj = new OlumsMentorSession(contextA);
                    mentorSessionObj.clearMentorSession();
                    verifierSessionObj = new OlumsVerifierSession(contextA);
                    verifierSessionObj.clearVerifierSession();
                    mTokenDeleteTask = new TokenDelete(contextA);
                    mTokenDeleteTask.execute((Void) null);
                    Intent intent = new Intent(contextA,SplashA.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    contextA.startActivity(intent);
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
