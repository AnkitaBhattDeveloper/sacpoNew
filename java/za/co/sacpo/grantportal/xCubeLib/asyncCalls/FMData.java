package za.co.sacpo.grantportal.xCubeLib.asyncCalls;

import android.content.Context;
import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsFMSession;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUserSession;

public class FMData extends AsyncTask<Void, Void, Boolean> {
    private String user_token;
    private static final String TAG = FMData.class.getSimpleName();
    private final String KEY_STATUS="s";
    private String KEY_DATA="m";
    private Boolean dataLogCall =false;
    private OlumsFMSession FMSessionObj;
    private OlumsUserSession userSessionObj;
    private Context contextA;
    public FMData(String token, Context context) {
        user_token = token;
        contextA = context;
    }
    @Override
    protected void onPreExecute() { }
    @Override
    protected Boolean doInBackground(Void... params) {
        FMSessionObj = new OlumsFMSession(contextA);
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
                        FMSessionObj.setFMLeadEmployer(dataM.getString("lead_employer_name"));
                        FMSessionObj.setFMLeadEmployerSdl(dataM.getString("lead_employer_sdl"));
                    }
                    else{
                        FMSessionObj.clearFMSession();
                        userSessionObj.clearUserSession();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    FMSessionObj.clearFMSession();
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
