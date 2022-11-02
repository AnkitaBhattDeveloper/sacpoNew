package za.co.sacpo.grant.xCubeLib.asyncCalls;

import android.content.Context;
import android.os.AsyncTask;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.session.OlumsUserSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;

public class TokenUpdate extends AsyncTask<Void, Void, Boolean> {
    private int user_id;
    private String user_token;
    private static final String TAG = TokenUpdate.class.getSimpleName();
    private final String KEY_STATUS="s";
    private String KEY_DATA="m";
    private Boolean dataLogCall =false;
    private OlumsUserSession userSessionObj;
    private OlumsUtilitySession utilSessionObj;
    private Context contextA;
    public TokenUpdate(int uid,String token, Context context) {
        user_id= uid;
        contextA = context;
        user_token = token;
    }
    @Override
    protected void onPreExecute() { }
    @Override
    protected Boolean doInBackground(Void... params) {
        String URL= URLHelper.DOMAIN_BASE_URL+URLHelper.TOKEN_UPDATE;
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put(URLHelper.TOKEN_TO_SERVER_KEY, user_token);
            jsonBody.put("id",Integer.toString(user_id));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(String.valueOf(response));
                    String Status = jsonObject.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        userSessionObj = new OlumsUserSession(contextA);
                        userSessionObj.setToken(user_token);
                    }
                    else{

                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/json; charset=utf-8");
                header.put("Accept","*/*");
                return header;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(contextA);
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
