package za.co.sacpo.grant.xCubeLib.asyncCalls;

import android.content.Context;
import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.session.OlumsGASession;
import za.co.sacpo.grant.xCubeLib.session.OlumsUserSession;

public class GAData extends AsyncTask<Void, Void, Boolean> {
    private String user_token;
    private static final String TAG = GAData.class.getSimpleName();
    private final String KEY_STATUS="s";
    private String KEY_DATA="m";
    private Boolean dataLogCall =false;
    private OlumsGASession gASessionObj;
    private OlumsUserSession userSessionObj;
    private Context contextA;
    public GAData(String token, Context context) {
        user_token = token;
        contextA = context;
    }
    @Override
    protected void onPreExecute() { }
    @Override
    protected Boolean doInBackground(Void... params) {
        gASessionObj = new OlumsGASession(contextA);
        userSessionObj = new OlumsUserSession(contextA);
        String userToken;
        userToken = userSessionObj.getToken();
        String FINAL = URLHelper.DOMAIN_BASE_URL+URLHelper.GA_DATA_URL+"/token/"+userToken;
        StringRequest stringRequest = new StringRequest(FINAL,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject outputJson = null;
                try {
                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONObject dataM = outputJson.getJSONObject(KEY_DATA);
                        gASessionObj.setGaLeadEmployer(dataM.getString("lead_employer_name"));
                        gASessionObj.setGaLeadEmployerSdl(dataM.getString("lead_employer_sdl"));
                    }
                    else{
                        gASessionObj.clearGASession();
                        userSessionObj.clearUserSession();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    gASessionObj.clearGASession();
                    userSessionObj.clearUserSession();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dataLogCall = false;
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(contextA);
        requestQueue.add(stringRequest);
        return dataLogCall;
    }
    @Override
    protected void onPostExecute(final Boolean success) {

    }
    @Override
    protected void onCancelled() {
    }
}
